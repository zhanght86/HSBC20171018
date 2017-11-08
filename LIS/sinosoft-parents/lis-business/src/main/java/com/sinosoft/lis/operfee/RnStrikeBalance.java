package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class RnStrikeBalance {
private static Logger logger = Logger.getLogger(RnStrikeBalance.class);
	// 得到全局的变量
	private TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData mResult = new VData();
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	// 涉及到的表
	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
	private LJAPayPersonSet nLJAPayPersonSet = new LJAPayPersonSet();
	private LJAPaySet mLJAPaySet = new LJAPaySet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCGetSet mLCGetSet = new LCGetSet();
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeSet OutLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
	private LJTempFeeClassSet OutLJTempFeeClassSet = new LJTempFeeClassSet();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private RNHangUp mRNHangUp = new RNHangUp(mGlobalInput);

	private String mOperator = "";
	private String mGetNoticeNo = "";
	private String mContNo = "";

	public RnStrikeBalance() {
	}

	public boolean submitData(VData cInputData, String cOperate) {

		this.mOperator = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		logger.debug("---End getInputData---");
		// 检验该保单是否被挂起(没有被挂起),是否被核销(应被核销)
		if (!checkDate()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "TempFeeBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mGetNoticeNo = (String) mTransferData.getValueByName("GetnoticeNo");
		mContNo = (String) mTransferData.getValueByName("ContNo");

		if (mContNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "合同号传入为空，请您确认!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (mGetNoticeNo == null || mGetNoticeNo.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "未得到本次交费收据号,无法确定唯一交费";
			this.mErrors.addOneError(tError);

			return false;
		}

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	private boolean checkDate() {
		// 查询暂交费信息
		String FinSql = "select * from ljtempfee where tempfeeno='?mGetNoticeNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(FinSql);
		sqlbv.put("mGetNoticeNo", mGetNoticeNo);
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		mLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);
		if (mLJTempFeeSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询不到所要冲正的暂收费信息";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 校验是否有本年度抽档应手存在
		String Sql = "select * from ljspay where othernotype in ('2','3') and otherno='?mContNo?' and startpaydate>=(select max(curpaytodate) from ljapayperson where getnoticeno='?mGetNoticeNo?')";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(Sql);
		sqlbv1.put("mContNo", mContNo);
		sqlbv1.put("mGetNoticeNo", mGetNoticeNo);
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = new LJSPaySet();
		tLJSPaySet = tLJSPayDB.executeQuery(sqlbv1);
		if (tLJSPaySet.size() > 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "有缴费对应日大于本次回退的应收数据存在，要回退本期保费请先撤销抽档";
			this.mErrors.addOneError(tError);
			return false;
		}
		FinSql = "select * from ljtempfeeclass where tempfeeno='?mGetNoticeNo?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(FinSql);
		sqlbv2.put("mGetNoticeNo", mGetNoticeNo);
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		mLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv2);
		if (mLJTempFeeClassSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询不到所要冲正的暂收费信息";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 校验是否该合同号是否被挂起
		// LCContHangUpStateSchema tLCContHangUpStateSchema = new
		// LCContHangUpStateSchema();
		if (!mRNHangUp.checkHangUP(mContNo)) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "该保单已被其他操作所挂起，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 校验是否核销
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String t1Sql = "";
		String tSql = "select * from ljapayperson where getnoticeno='?mGetNoticeNo?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("mGetNoticeNo", mGetNoticeNo);

		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		mLJAPayPersonSet = tLJAPayPersonDB.executeQuery(sqlbv3);

		tSql = "select * from ljapay where getnoticeno='?mGetNoticeNo?'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("mGetNoticeNo", mGetNoticeNo);
		LJAPayDB tLJAPayDB = new LJAPayDB();
		mLJAPaySet = tLJAPayDB.executeQuery(sqlbv4);

		if (mLJAPayPersonSet.size() == 0 || mLJAPaySet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "没有找到该笔实收的收费纪录!";
			this.mErrors.addOneError(tError);
			return false;

		} // endortype = NS 代表新加附加险
		t1Sql = "select * from lpedoritem where edortype <>'AC' and edortype <>'PC' and edortype <>'RB' and edorstate = '0' and contno = '?mContNo?' and lpedoritem.edorvalidate >'?edorvalidate?' ";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(t1Sql);
		sqlbv5.put("mContNo", mContNo);
		sqlbv5.put("edorvalidate", mLJAPayPersonSet.get(1).getConfDate());
		tSSRS = tExeSQL.execSQL(sqlbv5);
		if (tSSRS.getMaxRow() != 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "该保单做过保全，因此无法做此操作!";
			this.mErrors.addOneError(tError);
			return false;
		}
		t1Sql = "select * from llclaimpolicy a, llregister b where b.rgtno = a.rgtno and a.contno = '?mContNo?' and b.rgtdate >'?rgtdate?' ";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(t1Sql);
		sqlbv6.put("mContNo", mContNo);
		sqlbv6.put("rgtdate", mLJAPayPersonSet.get(1).getConfDate());
		tSSRS = tExeSQL.execSQL(sqlbv6);
		if (tSSRS.getMaxRow() != 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "该保单做过理赔无法，因此无法做此操作!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean dealData() {
		// 生成实付号
		String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		String nNo = PubFun1.CreateMaxNo("GETNO", tLimit);
		logger.debug("本次退费实付号码::::::::::::::" + nNo);
		logger.debug("本次处理保单:::::::::::::::::"
				+ mLJAPaySet.get(1).getOtherNo());
		logger.debug("本次处理总金额:::::::::::::::"
				+ mLJAPaySet.get(1).getSumActuPayMoney());
		logger.debug("======================================================================");
		logger.debug("===============================进入新的续期冲正处理======================");
		logger.debug("======================================================================");
		/** @todo 1.进入保单层 */
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		tLCContDB.getInfo();
		mLCContSchema = tLCContDB.getSchema();
		mLCContSchema.setSumPrem(tLCContDB.getSumPrem()
				- mLJAPaySet.get(1).getSumActuPayMoney());
		mLCContSchema.setModifyDate(CurrentDate);
		mLCContSchema.setModifyTime(CurrentTime);

		/** @todo 2.进入险种层 */
		String tSql = "select * from lcpol where contno='?contno?' and appflag='1'"
				+ " and polno in (select polno from ljapayperson where getnoticeno = '?mGetNoticeNo?')";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("contno", mLCContSchema.getContNo());
		sqlbv7.put("mGetNoticeNo", mGetNoticeNo);

		logger.debug("查询险种=====================" + tSql);
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.executeQuery(sqlbv7);

		for (int i = 1; i <= tLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);

			// 处理续期业务(期缴)
			if (tLCPolSchema.getRnewFlag() != -1
					&& tLCPolSchema.getPayIntv() > 0) {
				// 修改总保费
				tLCPolSchema.setSumPrem(tLCPolSchema.getSumPrem()
						- tLCPolSchema.getPrem());
				// 修改交费对应日
				for (int t = 1; t <= mLJAPayPersonSet.size(); t++) {
					if (mLJAPayPersonSet.get(t).getPolNo().equals(
							tLCPolSchema.getPolNo())) {
						tLCPolSchema.setPaytoDate(mLJAPayPersonSet.get(t)
								.getLastPayToDate());
						logger.debug("新交费对应日============="
								+ tLCPolSchema.getPaytoDate());
						continue;
					}
				}
				tLCPolSchema.setModifyDate(CurrentDate);
				tLCPolSchema.setModifyTime(CurrentTime);
				mLCPolSet.add(tLCPolSchema);
				// 处理责任
				String DutySql = "select * from lcduty where polno='?polno?'";
				SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
				sqlbv8.sql(DutySql);
				sqlbv8.put("polno", tLCPolSchema.getPolNo());
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySet tLCDutySet = new LCDutySet();
				tLCDutySet = tLCDutyDB.executeQuery(sqlbv8);
				if (tLCDutySet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "未找到该保单保险责任!";
					this.mErrors.addOneError(tError);
					return false;
				}

				for (int d = 1; d <= tLCDutySet.size(); d++) {
					LCDutySchema tLCDutySchema = new LCDutySchema();
					tLCDutySchema = tLCDutySet.get(d);
					tLCDutySchema.setSumPrem(tLCDutySchema.getSumPrem()
							- tLCDutySchema.getPrem());
					for (int t = 1; t <= mLJAPayPersonSet.size(); t++) {
						if (mLJAPayPersonSet.get(t).getPolNo().equals(
								tLCDutySchema.getPolNo())) {
							tLCDutySchema.setPaytoDate(mLJAPayPersonSet.get(t)
									.getLastPayToDate());
							continue;
						}
					}
					tLCDutySchema.setModifyDate(CurrentDate);
					tLCDutySchema.setModifyTime(CurrentTime);
					mLCDutySet.add(tLCDutySchema);
					// 处理保费层
					String PremSql = "Select * from lcprem where polno = '?polno?' and dutycode='?dutycode?' and UrgePayFlag = 'Y'";
					SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
					sqlbv9.sql(PremSql);
					sqlbv9.put("polno", tLCDutySchema.getPolNo());
					sqlbv9.put("dutycode", tLCDutySchema.getDutyCode());

					LCPremDB tLCPremDB = new LCPremDB();
					LCPremSet tLCPremSet = new LCPremSet();
					tLCPremSet = tLCPremDB.executeQuery(sqlbv9);
					if (tLCPremSet.size() == 0) {
						CError tError = new CError();
						tError.moduleName = "XQChargeRoolBackBL";
						tError.functionName = "dealData";
						tError.errorMessage = "未找到该保单保险保费!";
						this.mErrors.addOneError(tError);
						return false;
					}
					for (int p = 1; p <= tLCPremSet.size(); p++) {
						LCPremSchema tLCPremSchema = new LCPremSchema();
						tLCPremSchema = tLCPremSet.get(p);
						for (int t = 1; t <= mLJAPayPersonSet.size(); t++) {
							LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
							tLJAPayPersonSchema = mLJAPayPersonSet.get(t);
							if (tLJAPayPersonSchema.getPolNo().equals(
									tLCPremSchema.getPolNo())
									&& tLJAPayPersonSchema
											.getDutyCode()
											.equals(tLCPremSchema.getDutyCode())
									&& tLJAPayPersonSchema.getPayPlanCode()
											.equals(
													tLCPremSchema
															.getPayPlanCode())) {
								tLCPremSchema.setPayTimes(tLCPremSchema
										.getPayTimes() - 1);
								tLCPremSchema.setPaytoDate(tLJAPayPersonSchema
										.getLastPayToDate());
								tLCPremSchema.setSumPrem(tLCPremSchema
										.getSumPrem()
										- tLJAPayPersonSchema
												.getSumDuePayMoney());
								mLCPremSet.add(tLCPremSchema);
								continue;
							}
						}
					} // End Prem
				} // End Duty
			}
			/*
			 * 自动续保换号
			 */
			else {
				// tLCPolSchema
				String OldSql = "select * from lcpol where contno='?mContNo?' and riskcode='?riskcode?' and appflag='4' and enddate='?enddate?'";
				SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
				sqlbv10.sql(OldSql);
				sqlbv10.put("mContNo", mContNo);
				sqlbv10.put("riskcode", tLCPolSchema.getRiskCode());
				sqlbv10.put("enddate", tLCPolSchema.getCValiDate());

				logger.debug("@@@@@@@@@@@@@@@@" + OldSql);

				LCPolDB oLCPolDB = new LCPolDB();
				LCPolSet oLCPolSet = new LCPolSet();
				oLCPolSet = oLCPolDB.executeQuery(sqlbv10);
				LCPolSchema oLCPolSchema = new LCPolSchema();
				oLCPolSchema = oLCPolSet.get(1);

				String AppPolNo = tLCPolSchema.getPolNo();
				String NotAppPolNo = oLCPolSchema.getPolNo();

				// 修改目前的状态
				tLCPolSchema.setPolNo(oLCPolSchema.getPolNo());
				tLCPolSchema.setAppFlag("4");
				tLCPolSchema.setModifyDate(CurrentDate);
				tLCPolSchema.setModifyTime(CurrentTime);
				//
				oLCPolSchema.setPolNo(AppPolNo);
				oLCPolSchema.setAppFlag("1");
				oLCPolSchema.setModifyDate(CurrentDate);
				oLCPolSchema.setModifyTime(CurrentTime);

				mLCPolSet.add(tLCPolSchema);
				mLCPolSet.add(oLCPolSchema);

				// 责任换号
				String DutySql = "select * from lcduty where polno='?AppPolNo?'";
				SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
				sqlbv11.sql(DutySql);
				sqlbv11.put("AppPolNo", AppPolNo);
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySet tLCDutySet = new LCDutySet();
				tLCDutySet = tLCDutyDB.executeQuery(sqlbv11);
				if (tLCDutySet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "未找到该保单保险责任!";
					this.mErrors.addOneError(tError);
					return false;
				}

				DutySql = "select * from lcduty where polno='?NotAppPolNo?'";
				SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
				sqlbv12.sql(DutySql);
				sqlbv12.put("NotAppPolNo", NotAppPolNo);
				LCDutyDB oLCDutyDB = new LCDutyDB();
				LCDutySet oLCDutySet = new LCDutySet();
				oLCDutySet = oLCDutyDB.executeQuery(sqlbv12);

				if (oLCDutySet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "未找到该保单保险责任!";
					this.mErrors.addOneError(tError);
					return false;
				}
				if (tLCDutySet.size() != oLCDutySet.size()) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该保单保险责任对应的历史数据被修改!";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int t = 1; t <= tLCDutySet.size(); t++) {
					LCDutySchema tLCDutySchema = new LCDutySchema();
					tLCDutySchema = tLCDutySet.get(t);

					for (int a = 1; a <= oLCDutySet.size(); a++) {
						if (tLCDutySchema.getContNo().equals(
								oLCDutySet.get(a).getContNo())
								&& tLCDutySchema.getDutyCode().equals(
										oLCDutySet.get(a).getDutyCode())) {
							tLCDutySchema.setPolNo(NotAppPolNo);
							oLCDutySet.get(a).setPolNo(AppPolNo);
						}
					}
				}

				mLCDutySet.add(tLCDutySet);
				mLCDutySet.add(oLCDutySet);

				// 保费换号
				String PremSql = "select * from lcprem where polno='?AppPolNo?'";
				SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
				sqlbv13.sql(PremSql);
				sqlbv13.put("AppPolNo", AppPolNo);
				LCPremDB tLCPremDB = new LCPremDB();
				LCPremSet tLCPremSet = new LCPremSet();
				tLCPremSet = tLCPremDB.executeQuery(sqlbv13);
				if (tLCPremSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "未找到该保单保险责任!";
					this.mErrors.addOneError(tError);
					return false;
				}

				PremSql = "select * from lcprem where polno='?NotAppPolNo?'";
				SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
				sqlbv14.sql(PremSql);
				sqlbv14.put("NotAppPolNo", NotAppPolNo);
				LCPremDB oLCPremDB = new LCPremDB();
				LCPremSet oLCPremSet = new LCPremSet();
				oLCPremSet = oLCPremDB.executeQuery(sqlbv14);

				if (oLCPremSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "未找到该保单保险责任!";
					this.mErrors.addOneError(tError);
					return false;
				}

				if (tLCPremSet.size() != oLCPremSet.size()) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该保单保险责任对应的历史数据被修改!";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int t = 1; t <= tLCPremSet.size(); t++) {
					LCPremSchema tLCPremSchema = new LCPremSchema();
					tLCPremSchema = tLCPremSet.get(t);
					for (int a = 1; a <= oLCPremSet.size(); a++) {
						if (tLCPremSchema.getContNo().equals(
								oLCPremSet.get(a).getContNo())
								&& tLCPremSchema.getDutyCode().equals(
										oLCPremSet.get(a).getDutyCode())
								&& tLCPremSchema.getPayPlanCode().equals(
										oLCPremSet.get(a).getPayPlanCode())) {
							tLCPremSchema.setPolNo(NotAppPolNo);
							oLCPremSet.get(a).setPolNo(AppPolNo);
						}
					}
				}
				mLCPremSet.add(tLCPremSet);
				mLCPremSet.add(oLCPremSet);

				// 责任换号
				String GetSql = "select * from lcget where polno='?AppPolNo?'";
				logger.debug("==========================" + GetSql);
				SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
				sqlbv15.sql(GetSql);
				sqlbv15.put("AppPolNo", AppPolNo);
				LCGetDB tLCGetDB = new LCGetDB();
				LCGetSet tLCGetSet = new LCGetSet();
				tLCGetSet = tLCGetDB.executeQuery(sqlbv15);
				if (tLCGetSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "未找到该保单保险责任!";
					this.mErrors.addOneError(tError);
					return false;
				}

				GetSql = "select * from lcget where polno='?NotAppPolNo?'";
				logger.debug("==========================" + GetSql);
				SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
				sqlbv16.sql(GetSql);
				sqlbv16.put("NotAppPolNo", NotAppPolNo);
				LCGetDB oLCGetDB = new LCGetDB();
				LCGetSet oLCGetSet = new LCGetSet();
				oLCGetSet = oLCGetDB.executeQuery(sqlbv16);

				if (oLCGetSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "未找到该保单保险责任!";
					this.mErrors.addOneError(tError);
					return false;
				}

				if (tLCGetSet.size() != oLCGetSet.size()) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该保单保险责任对应的历史数据被修改!";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int t = 1; t <= tLCGetSet.size(); t++) {
					LCGetSchema tLCGetSchema = new LCGetSchema();
					tLCGetSchema = tLCGetSet.get(t);

					for (int a = 1; a <= oLCGetSet.size(); a++) {
						if (tLCGetSchema.getContNo().equals(
								oLCGetSet.get(a).getContNo())
								&& tLCGetSchema.getDutyCode().equals(
										oLCGetSet.get(a).getDutyCode())
								&& tLCGetSchema.getGetDutyCode().equals(
										oLCGetSet.get(a).getGetDutyCode())) {
							tLCGetSchema.setPolNo(NotAppPolNo);
							oLCGetSet.get(a).setPolNo(AppPolNo);
						}
					}
				}

				mLCGetSet.add(tLCGetSet);
				mLCGetSet.add(oLCGetSet);

			}
			// End Pol
		}

		for (int i = 1; i <= mLJAPaySet.size(); i++) {
			LJAPaySchema tLJAPaySchema = new LJAPaySchema();
			tLJAPaySchema = mLJAPaySet.get(i);
			tLJAPaySchema.setSumActuPayMoney(-tLJAPaySchema.getSumActuPayMoney());
			tLJAPaySchema.setTaxAmount(-tLJAPaySchema.getTaxAmount());
			tLJAPaySchema.setNetAmount(-tLJAPaySchema.getNetAmount());
			tLJAPaySchema.setPayNo(nNo);
			tLJAPaySchema.setEnterAccDate(CurrentDate);
			tLJAPaySchema.setConfDate(CurrentDate);
			tLJAPaySchema.setMakeDate(CurrentDate);
			tLJAPaySchema.setModifyDate(CurrentDate);
			tLJAPaySchema.setMakeTime(CurrentTime);
			tLJAPaySchema.setModifyTime(CurrentTime);
			tLJAPaySchema.setOperator(mGlobalInput.Operator);
			mLJAPaySet.set(i, tLJAPaySchema);
		}

		for (int t = 1; t <= mLJAPayPersonSet.size(); t++) {
			LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
			tLJAPayPersonSchema = mLJAPayPersonSet.get(t);
			tLJAPayPersonSchema.setSumActuPayMoney(-tLJAPayPersonSchema
					.getSumActuPayMoney());
			tLJAPayPersonSchema.setTax(tLJAPayPersonSchema.getTax());
			tLJAPayPersonSchema.setTaxAmount(-tLJAPayPersonSchema.getTaxAmount());
			tLJAPayPersonSchema.setNetAmount(-tLJAPayPersonSchema.getNetAmount());
			tLJAPayPersonSchema.setEnterAccDate(CurrentDate);
			tLJAPayPersonSchema.setConfDate(CurrentDate);
			tLJAPayPersonSchema.setMakeDate(CurrentDate);
			tLJAPayPersonSchema.setModifyDate(CurrentDate);
			tLJAPayPersonSchema.setMakeTime(CurrentTime);
			tLJAPayPersonSchema.setModifyTime(CurrentTime);
			tLJAPayPersonSchema.setOperator(mGlobalInput.Operator);
			// tLJAPayPersonSchema.setGetNoticeNo(nNo);
			tLJAPayPersonSchema.setPayNo(nNo);
			nLJAPayPersonSet.add(tLJAPayPersonSchema);
		}
		// 财务冲正
		for (int i = 1; i <= mLJTempFeeSet.size(); i++) {
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			tLJTempFeeSchema = mLJTempFeeSet.get(i); // nNo
			tLJTempFeeSchema.setTempFeeNo(nNo);
			tLJTempFeeSchema.setEnterAccDate(CurrentDate);
			tLJTempFeeSchema.setConfDate(CurrentDate);
			tLJTempFeeSchema.setMakeDate(CurrentDate);
			tLJTempFeeSchema.setModifyDate(CurrentDate);
			tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
			tLJTempFeeSchema.setMakeTime(CurrentTime);
			tLJTempFeeSchema.setModifyTime(CurrentTime);
			tLJTempFeeSchema.setPayMoney(-tLJTempFeeSchema.getPayMoney());
			tLJTempFeeSchema.setTempFeeType("C");
			OutLJTempFeeSet.add(tLJTempFeeSchema);
		}
		for (int i = 1; i <= mLJTempFeeClassSet.size(); i++) {
			LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
			tLJTempFeeClassSchema = mLJTempFeeClassSet.get(i);
			tLJTempFeeClassSchema.setTempFeeNo(nNo);
			tLJTempFeeClassSchema.setEnterAccDate(CurrentDate);
			tLJTempFeeClassSchema.setConfDate(CurrentDate);
			tLJTempFeeClassSchema.setMakeDate(CurrentDate);
			tLJTempFeeClassSchema.setModifyDate(CurrentDate);
			tLJTempFeeClassSchema.setOperator(mGlobalInput.Operator);
			tLJTempFeeClassSchema.setMakeTime(CurrentTime);
			tLJTempFeeClassSchema.setModifyTime(CurrentTime);
			tLJTempFeeClassSchema.setPayMoney(-tLJTempFeeClassSchema
					.getPayMoney());
			OutLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		}
		return true;
	}

	private boolean prepareOutputData() {
		MMap map = new MMap();
		TaxCalculator.calBySchemaSet(nLJAPayPersonSet);
		map.put(nLJAPayPersonSet, "INSERT");
		map.put(mLJAPaySet, "INSERT");
		map.put(OutLJTempFeeSet, "INSERT");
		map.put(OutLJTempFeeClassSet, "INSERT");
		map.put(mLCPremSet, "UPDATE");
		map.put(mLCPolSet, "UPDATE");
		map.put(mLCDutySet, "UPDATE");
		map.put(mLCGetSet, "UPDATE");
		map.put(mLCContSchema, "UPDATE");
		mResult.add(map);
		return true;
	}

}
