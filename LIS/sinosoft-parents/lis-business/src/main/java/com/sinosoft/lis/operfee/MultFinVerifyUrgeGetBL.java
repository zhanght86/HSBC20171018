package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCUrgeLogDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCUrgeLogSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 续期核销业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Fanym
 * @version 1.0
 */
public class MultFinVerifyUrgeGetBL {
private static Logger logger = Logger.getLogger(MultFinVerifyUrgeGetBL.class);
	private VData mInputData;
	private String mOperate;
	private GlobalInput mGI;
	public int failNum;
	public int Count;
	public CErrors mErrors = new CErrors();

	public MultFinVerifyUrgeGetBL() {
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "000";
		tGI.ComCode = "86";
		tVData.add(tGI);
		MultFinVerifyUrgeGetBL tMultFinVerifyUrgeGetBL = new MultFinVerifyUrgeGetBL();
		tMultFinVerifyUrgeGetBL.submitData(tVData, "Verify");
	}

	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		this.mInputData = (VData) cInputData.clone();

		if (!getInputData(this.mInputData)) {
			return false;
		}

		if (!CheckData()) {
			return false;
		}

		if (!PrepareData()) {
			return false;
		}

		if (cOperate.equalsIgnoreCase("Verify")) {
			String sql = "select TempFeeNo from LJTempFee where "
					+ "TempFeeType = '2' " // 暂交费收据号类型2 ---续期催收交费，录入催收号
					// +"and OtherNoType = '2' " //0---个单交费 ---->个单保单号
					+ "and ConfFlag = '0' " // 核销标志为0
					+ "and EnterAccDate is not null and EnterAccDate<>'3000-01-01'"
					+ " and MakeDate <= '?MakeDate?' and confdate is null"
					+ " and not exists (select payno from ljapay where getnoticeno=LJTempFee.tempfeeno)"
					+ " and policycom like concat('?policycom?','%')";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("MakeDate", PubFun.getCurrentDate());
			sqlbv.put("policycom", mGI.ManageCom);
			// "' and ( not exists " //排除有附加险续保的情况
			// +"(select LCPol.Polno from LCRnewStateLog,LCPol where
			// LCRnewStateLog.PrtNo=LCPol.PrtNo and
			// LCPol.Polno=LJTempFee.OtherNo )" +
			// " )";

			logger.debug(sql); // //////////

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv);

			if (tSSRS == null) {
				Count = failNum = 0;
			} else {
				Count = tSSRS.getMaxRow();
				logger.debug("#################Count: " + Count);

				VData tVData = new VData();
				LJTempFeeSchema tLJTempFeeSchema;
				LJTempFeeDB tLJTempFeeDB;
				LJTempFeeSet tLJTempFeeSet;

				LJSPaySchema tLJSPaySchema;
				LJSPaySet tLJSPaySet;

				VerDuePayFeeQueryUI tVerDuePayFeeQueryUI;
				IndiFinUrgeVerifyUI tIndiFinUrgeVerifyUI;

				String TempFeeNo;
				double tempMoney = 0;
				double sumDueMoney = 0;
				failNum = 0;

				for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
					TempFeeNo = tSSRS.GetText(i, 1);
					tLJTempFeeSchema = new LJTempFeeSchema();
					tLJTempFeeSchema.setTempFeeNo(TempFeeNo);
					tLJTempFeeDB = new LJTempFeeDB();
					tLJTempFeeDB.setSchema(tLJTempFeeSchema);
					tLJTempFeeSet = tLJTempFeeDB.query();
					tLJTempFeeSchema = tLJTempFeeSet.get(1);
					tempMoney = tLJTempFeeSchema.getPayMoney();

					tLJSPaySet = new LJSPaySet();
					tLJSPaySchema = new LJSPaySchema();
					tLJSPaySchema.setGetNoticeNo(TempFeeNo);
					tVData.clear();
					tVData.add(tLJSPaySchema);
					tVerDuePayFeeQueryUI = new VerDuePayFeeQueryUI();

					// if(TempFeeNo.equals("86110020040310001629"))
					// {
					// TempFeeNo = TempFeeNo;
					// }
					// 查询应收总表
					if (!tVerDuePayFeeQueryUI.submitData(tVData, "QUERY")) {
						// 因为数据量大后，会导致堆栈溢出
						// CError.buildErr(this,tVerDuePayFeeQueryUI.mErrors.toString(),mErrors);
						failNum = failNum + 1;

						LCUrgeLogSchema tLCUrgeLogSchema = new LCUrgeLogSchema();
						tLCUrgeLogSchema.setTempFeeNo(TempFeeNo);
						tLCUrgeLogSchema
								.setPolNo(tLJTempFeeSchema.getOtherNo());
						tLCUrgeLogSchema.setMakeDate(PubFun.getCurrentDate());
						tLCUrgeLogSchema.setMakeTime(PubFun.getCurrentTime());
						tLCUrgeLogSchema.setManageCom(mGI.ManageCom);
						tLCUrgeLogSchema.setErrorMessage("应收表查询失败："
								+ tVerDuePayFeeQueryUI.mErrors.getFirstError());
						saveLog(tLCUrgeLogSchema);

						continue;
					} else {
						tVData.clear();
						tVData = tVerDuePayFeeQueryUI.getResult();
						tLJSPaySet.set((LJSPaySet) tVData
								.getObjectByObjectName("LJSPaySet", 0));
						tLJSPaySchema = tLJSPaySet.get(1);
						sumDueMoney = tLJSPaySchema.getSumDuePayMoney();

						// 比较两个金额值，相等则核销
						if (sumDueMoney != tempMoney) {
							// CError.buildErr(this, "应收费和暂交费不一致", mErrors);
							failNum = failNum + 1;

							LCUrgeLogSchema tLCUrgeLogSchema = new LCUrgeLogSchema();
							tLCUrgeLogSchema.setTempFeeNo(TempFeeNo);
							tLCUrgeLogSchema.setPolNo(tLJTempFeeSchema
									.getOtherNo());
							tLCUrgeLogSchema.setMakeDate(PubFun
									.getCurrentDate());
							tLCUrgeLogSchema.setMakeTime(PubFun
									.getCurrentTime());
							tLCUrgeLogSchema.setManageCom(mGI.ManageCom);
							tLCUrgeLogSchema.setErrorMessage("应收费和暂交费不一致");
							saveLog(tLCUrgeLogSchema);

							continue;
						} else {
							tVData.clear();
							tVData.add(tLJTempFeeSchema);
							tVData.add(tLJSPaySchema);
							tVData.add(mGI);
							tIndiFinUrgeVerifyUI = new IndiFinUrgeVerifyUI();
							try {
								if (!tIndiFinUrgeVerifyUI.submitData(tVData,
										"VERIFY")) {
									// CError.buildErr(this,tIndiFinUrgeVerifyUI.mErrors.toString(),mErrors);
									failNum = failNum + 1;
									LCUrgeLogSchema tLCUrgeLogSchema = new LCUrgeLogSchema();
									tLCUrgeLogSchema.setTempFeeNo(TempFeeNo);
									tLCUrgeLogSchema.setPolNo(tLJTempFeeSchema
											.getOtherNo());
									tLCUrgeLogSchema.setMakeDate(PubFun
											.getCurrentDate());
									tLCUrgeLogSchema.setMakeTime(PubFun
											.getCurrentTime());
									tLCUrgeLogSchema
											.setManageCom(mGI.ManageCom);
									tLCUrgeLogSchema
											.setErrorMessage(tIndiFinUrgeVerifyUI.mErrors
													.getFirstError());
									saveLog(tLCUrgeLogSchema);
									continue;
								}
							} catch (Exception ex) {
								failNum = failNum + 1;
								LCUrgeLogSchema tLCUrgeLogSchema = new LCUrgeLogSchema();
								tLCUrgeLogSchema.setTempFeeNo(TempFeeNo);
								tLCUrgeLogSchema.setPolNo(tLJTempFeeSchema
										.getOtherNo());
								tLCUrgeLogSchema.setMakeDate(PubFun
										.getCurrentDate());
								tLCUrgeLogSchema.setMakeTime(PubFun
										.getCurrentTime());
								tLCUrgeLogSchema.setManageCom(mGI.ManageCom);
								tLCUrgeLogSchema.setErrorMessage(ex.toString());
								saveLog(tLCUrgeLogSchema);
								continue;
							}
						}
					}
				}
			}
		}

		logger.debug("#################FailNum: " + failNum);

		return true;
	}

	private boolean getInputData(VData tInputData) {
		// mLJTempFeeSchema = (LJTempFeeSchema)
		// tInputData.getObjectByObjectName("LJTempFeeSchema",0);
		mGI = (GlobalInput) tInputData.getObjectByObjectName("GlobalInput", 0);

		if (mGI == null) {
			CError.buildErr(this, "数据不足!", mErrors);

			return false;
		}

		return true;
	}

	private boolean CheckData() {
		return true;
	}

	private boolean PrepareData() {
		return true;
	}

	private void saveLog(LCUrgeLogSchema aLCUrgeLogSchema) {
		Connection conn = DBConnPool.getConnection();

		if (conn != null) {
			try {
				conn.setAutoCommit(false);

				LCUrgeLogDB tLCUrgeLogDB = new LCUrgeLogDB();
				tLCUrgeLogDB.setTempFeeNo(aLCUrgeLogSchema.getTempFeeNo());
				tLCUrgeLogDB.delete();
				tLCUrgeLogDB = new LCUrgeLogDB();
				tLCUrgeLogDB.setSchema(aLCUrgeLogSchema);
				tLCUrgeLogDB.insert();
				conn.commit();
				conn.close();
			} catch (Exception ex) {
				return;
			}
		}
	}
}
