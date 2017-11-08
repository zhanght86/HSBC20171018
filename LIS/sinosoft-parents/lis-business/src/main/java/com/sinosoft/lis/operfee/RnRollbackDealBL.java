package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLClaimUpdatePolNoBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LPEdorItemDB;
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
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
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
 * 续期核销后的冲正回退
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
public class RnRollbackDealBL {
private static Logger logger = Logger.getLogger(RnRollbackDealBL.class);
	// 得到全局的变量
	private TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData mResult = new VData();
	private MMap map = new MMap();
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	// 涉及到的表
	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
	private LJAPayPersonSet nLJAPayPersonSet = new LJAPayPersonSet();
	private LJAPaySet mLJAPaySet = new LJAPaySet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCDutySet dLCDutySet = new LCDutySet();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSet dLCPolSet = new LCPolSet();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCPremSet dLCPremSet = new LCPremSet();
	private LCGetSet mLCGetSet = new LCGetSet();
	private LCGetSet dLCGetSet = new LCGetSet();
	private LJAGetSet mLJAGetSet = new LJAGetSet();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private RNHangUp mRNHangUp = new RNHangUp(mGlobalInput);

	private String mOperator = "";
	private String mGetNoticeNo = "";
	private String mContNo = "";
	private String mBankCode = "";
	private String mAccNo = "";
	private String mAccName = "";
	private String mPayMode = "";

	public RnRollbackDealBL() {
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
		mBankCode = (String) mTransferData.getValueByName("BankCode");
		mAccNo = (String) mTransferData.getValueByName("AccNo");
		mAccName = (String) mTransferData.getValueByName("AccName");
		mPayMode = (String) mTransferData.getValueByName("PayMode");

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

		if (mPayMode == null || mPayMode.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请选择回退的退费方式";
			this.mErrors.addOneError(tError);

			return false;
		} else if (mPayMode.equals("4")) {
			if (mAccNo == null || mAccNo.equals("") || mAccName == null
					|| mAccName.equals("") || mBankCode == null
					|| mBankCode.equals("")) {
				CError tError = new CError();
				tError.moduleName = "XQChargeRoolBackBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "缺少银行代付的必要帐户信息（银行代码、账号、账户名）";
				this.mErrors.addOneError(tError);
				return false;
			}
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

		// 校验是否核销转实收
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String t1Sql = "";
		String tSql = "select * from ljapayperson where getnoticeno='?mGetNoticeNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("mGetNoticeNo", mGetNoticeNo);

		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		mLJAPayPersonSet = tLJAPayPersonDB.executeQuery(sqlbv);

		tSql = "select * from ljapay where getnoticeno='?mGetNoticeNo?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("mGetNoticeNo", mGetNoticeNo);
		LJAPayDB tLJAPayDB = new LJAPayDB();
		mLJAPaySet = tLJAPayDB.executeQuery(sqlbv1);

		if (mLJAPayPersonSet.size() == 0 || mLJAPaySet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "没有找到该笔实收的收费纪录!";
			this.mErrors.addOneError(tError);
			return false;

		}

		String jSql = "select * from ljtempfee where otherno='?mContNo?' and tempfeetype='2' and enteraccdate is not null and enteraccdate<>'3000-1-1' and confdate is null";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(jSql);
		sqlbv2.put("mContNo", mContNo);
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv2);
		if (tLJTempFeeSet.size() > 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "存在未核销的暂收费记录,请先进行暂收费退费!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 校验是否有本年度抽档应手存在
		String Sql = "select * from ljspay where othernotype in ('2','3') and otherno='?mContNo?' "; // and startpaydate>=(select max(curpaytodate) from
		// ljapayperson where getnoticeno='" + mGetNoticeNo
		// + "')" ;
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(Sql);
		sqlbv3.put("mContNo", mContNo);
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = new LJSPaySet();
		tLJSPaySet = tLJSPayDB.executeQuery(sqlbv3);
		if (tLJSPaySet.size() > 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "有应收数据存在，要回退本期保费请先撤销抽档";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 比较保全操作日期，如果保全生效日大于等于核销日期需要做保全回退
		t1Sql = "select * " + " from lpedoritem "
				+ " where edortype not in ('AC','PC','RB') "
				+ " and contno = '?mContNo?' "
				+ " and lpedoritem.approvedate >='?approvedate?' "
				+ " and EdorState='0'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(t1Sql);
		sqlbv4.put("mContNo", mContNo);
		sqlbv4.put("approvedate", mLJAPayPersonSet.get(1).getConfDate());
		logger.debug("EdorSql:::::::::::" + t1Sql);
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv4);

		if (tLPEdorItemSet.size() > 0) {
			for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
				LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
				tLPEdorItemSchema = tLPEdorItemSet.get(i);
				String RBSql = "select * from LPEdorItem where standbyflag1='?standbyflag1?' and edortype='RB' and EdorState='0'";
				SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql(RBSql);
				sqlbv5.put("standbyflag1", tLPEdorItemSchema.getEdorAcceptNo());
				LPEdorItemDB rLPEdorItemDB = new LPEdorItemDB();
				LPEdorItemSet rLPEdorItemSet = new LPEdorItemSet();
				rLPEdorItemSet = rLPEdorItemDB.executeQuery(sqlbv5);
				if (rLPEdorItemSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该保单做过的保全项目"
							+ tLPEdorItemSchema.getEdorType() + "还未回退，不能进行续期回退";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
		}
		// 比较理赔操作日期，如果理赔立案日期大于等于核销日期需要做理赔回退
		t1Sql = "select * from llclaimpolicy a, llregister b where b.rgtno = a.rgtno and a.contno = '?mContNo?' and b.rgtdate >='?rgtdate?' ";
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
		// 如果已经做过付费不能续期回退
		tSql = "select * from ljaget where getnoticeno='?mGetNoticeNo?' and othernotype='9'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("mGetNoticeNo", mGetNoticeNo);
		logger.debug("校验是否回退:::::::::" + tSql);
		LJAGetDB tLJAGetDB = new LJAGetDB();
		LJAGetSet tLJAGetSet = new LJAGetSet();
		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv7);
		if (tLJAGetSet.size() > 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = "该期保费已经回退，因此无法做此操作!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 新增判断险种状态，对失效和终止不能做回退
		String tempsql = "Select lcp.contno, lcp.polno, lcp.riskcode, lcp.appflag,lcc.contno,lcc.statetype,lcc.state,lcc.statereason"
				+ " From Lcpol lcp, lccontstate lcc "
				+ " Where Exists (Select 'X' From Ljapayperson "
				+ " Where Polno = lcp.Polno "
				+ " and riskcode = lcp.riskcode "
				+ " and getnoticeno = '?mGetNoticeNo?') "
				+ " And lcp.contno = '?mContNo?' "
				+ " and lcp.polno=lcc.polno "
				+ " and ((lcc.statetype in ('Terminate','Available') and lcc.state='1' and lcc.enddate is null) or lcp.appflag='4') ";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(tempsql);
		sqlbv8.put("mGetNoticeNo", mGetNoticeNo);
		sqlbv8.put("mContNo", mContNo);
		ExeSQL exeSql = new ExeSQL();
		SSRS tzuSSRS = new SSRS();
		tzuSSRS = exeSql.execSQL(sqlbv8);
		if (tzuSSRS.MaxNumber > 0) {
			CError tError = new CError();
			tError.moduleName = "XQChargeRoolBackBL";
			tError.functionName = "dealData";
			tError.errorMessage = " 主险或者附加险有失效或者终止记录，因此无法做此操作!";
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
		logger.debug("===============================进入新的续期回退处理======================");
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
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("contno", mLCContSchema.getContNo());
		sqlbv9.put("mGetNoticeNo", mGetNoticeNo);

		logger.debug("查询险种=====================" + tSql);
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.executeQuery(sqlbv9);

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
				SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
				sqlbv10.sql(DutySql);
				sqlbv10.put("polno", tLCPolSchema.getPolNo());
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySet tLCDutySet = new LCDutySet();
				tLCDutySet = tLCDutyDB.executeQuery(sqlbv10);
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
					SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
					sqlbv11.sql(PremSql);
					sqlbv11.put("polno", tLCDutySchema.getPolNo());
					sqlbv11.put("dutycode", tLCDutySchema.getDutyCode());

					LCPremDB tLCPremDB = new LCPremDB();
					LCPremSet tLCPremSet = new LCPremSet();
					tLCPremSet = tLCPremDB.executeQuery(sqlbv11);
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
				SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
				sqlbv12.sql(OldSql);
				sqlbv12.put("mContNo", mContNo);
				sqlbv12.put("riskcode", tLCPolSchema.getRiskCode());
				sqlbv12.put("enddate", tLCPolSchema.getCValiDate());

				logger.debug("@@@@@@@@@@@@@@@@" + OldSql);

				LCPolDB oLCPolDB = new LCPolDB();
				LCPolSet oLCPolSet = new LCPolSet();
				oLCPolSet = oLCPolDB.executeQuery(sqlbv12);
				LCPolSchema oLCPolSchema = new LCPolSchema();
				oLCPolSchema = oLCPolSet.get(1);

				String AppPolNo = tLCPolSchema.getPolNo();
				String NotAppPolNo = oLCPolSchema.getPolNo();

				/*------------------------------------------------------
				 * 理赔换号
				 *------------------------------------------------------
				 * */

				logger.debug("------------------开始调用理赔换号程序-------------------");
				logger.debug("NewPolNo=================" + AppPolNo);
				logger.debug("OldPolNo=================" + NotAppPolNo);
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("NewPolNo", AppPolNo);
				tTransferData.setNameAndValue("OldPolNo", NotAppPolNo);
				VData tVData = new VData();
				tVData.addElement(tTransferData);
				LLClaimUpdatePolNoBL tLLClaimUpdatePolNoBL = new LLClaimUpdatePolNoBL();
				if (!tLLClaimUpdatePolNoBL.submitData(tVData, "UpDate")) {
					this.mErrors.copyAllErrors(tLLClaimUpdatePolNoBL.mErrors);
					CError tError = new CError();
					tError.moduleName = "LLClaimUpdatePolNoBL";
					tError.functionName = "submitData";
					tError.errorMessage = "更新理赔表失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				map.add(tLLClaimUpdatePolNoBL.getMMap());

				logger.debug("------------------理赔换号结束-----------------");
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

				dLCPolSet.add(tLCPolSchema);
				mLCPolSet.add(oLCPolSchema);

				// 责任换号
				String DutySql = "select * from lcduty where polno='?AppPolNo?'";
				SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
				sqlbv13.sql(DutySql);
				sqlbv13.put("AppPolNo", AppPolNo);
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySet tLCDutySet = new LCDutySet();
				tLCDutySet = tLCDutyDB.executeQuery(sqlbv13);
				if (tLCDutySet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "未找到该保单保险责任!";
					this.mErrors.addOneError(tError);
					return false;
				}

				DutySql = "select * from lcduty where polno='?NotAppPolNo?'";
				SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
				sqlbv14.sql(DutySql);
				sqlbv14.put("NotAppPolNo", NotAppPolNo);
				LCDutyDB oLCDutyDB = new LCDutyDB();
				LCDutySet oLCDutySet = new LCDutySet();
				oLCDutySet = oLCDutyDB.executeQuery(sqlbv14);

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

				dLCDutySet.add(tLCDutySet);
				mLCDutySet.add(oLCDutySet);

				// 保费换号

				String PremSql = "select * from lcprem where polno='?NotAppPolNo?'";
				SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
				sqlbv15.sql(PremSql);
				sqlbv15.put("NotAppPolNo", NotAppPolNo);
				LCPremDB oLCPremDB = new LCPremDB();
				LCPremSet oLCPremSet = new LCPremSet();
				oLCPremSet = oLCPremDB.executeQuery(sqlbv15);

				if (oLCPremSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "未找到该保单保险责任!";
					this.mErrors.addOneError(tError);
					return false;
				}
				LCPremSet tLCPremSet = new LCPremSet();
				for (int n = 1; n <= oLCPremSet.size(); n++) {
					String tPremSql = "select * from lcprem where polno='?AppPolNo?' and payplancode = '?payplancode?'";
					SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
					sqlbv16.sql(tPremSql);
					sqlbv16.put("AppPolNo", AppPolNo);
					sqlbv16.put("payplancode", oLCPremSet.get(n).getPayPlanCode());
					LCPremDB tLCPremDB = new LCPremDB();
					LCPremSet nLCPremSet = new LCPremSet();
					nLCPremSet = tLCPremDB.executeQuery(sqlbv16);
					tLCPremSet.add(nLCPremSet);
				}

				if (tLCPremSet.size() == 0) {
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
					tError.errorMessage = "该保单保费记录对应的历史数据被修改!";
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
				dLCPremSet.add(tLCPremSet);
				mLCPremSet.add(oLCPremSet);

				// 责任换号
				String GetSql = "select * from lcget where polno='?AppPolNo?'";
				SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
				sqlbv17.sql(GetSql);
				sqlbv17.put("AppPolNo", AppPolNo);
				logger.debug("==========================" + GetSql);
				LCGetDB tLCGetDB = new LCGetDB();
				LCGetSet tLCGetSet = new LCGetSet();
				tLCGetSet = tLCGetDB.executeQuery(sqlbv17);
				if (tLCGetSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "XQChargeRoolBackBL";
					tError.functionName = "dealData";
					tError.errorMessage = "未找到该保单保险责任!";
					this.mErrors.addOneError(tError);
					return false;
				}

				GetSql = "select * from lcget where polno='?NotAppPolNo?'";
				SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
				sqlbv18.sql(GetSql);
				sqlbv18.put("NotAppPolNo", NotAppPolNo);
				logger.debug("==========================" + GetSql);
				LCGetDB oLCGetDB = new LCGetDB();
				LCGetSet oLCGetSet = new LCGetSet();
				oLCGetSet = oLCGetDB.executeQuery(sqlbv18);

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

				dLCGetSet.add(tLCGetSet);
				mLCGetSet.add(oLCGetSet);
			}
			// End Pol
		}

		// 生成实付数据
		LJAPaySchema tLJAPaySchema = mLJAPaySet.get(1);
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		tLJAGetSchema.setActuGetNo(nNo);
		tLJAGetSchema.setOtherNo(tLJAPaySchema.getOtherNo());
		tLJAGetSchema.setOtherNoType("9");
		tLJAGetSchema.setPayMode(mPayMode);
		tLJAGetSchema.setBankOnTheWayFlag("0");
		tLJAGetSchema.setBankSuccFlag("");
		tLJAGetSchema.setSendBankCount(0);
		tLJAGetSchema.setManageCom(tLJAPaySchema.getManageCom());
		tLJAGetSchema.setAgentCom(tLJAPaySchema.getAgentCom());
		tLJAGetSchema.setAgentType(tLJAPaySchema.getAgentType());
		tLJAGetSchema.setAgentCode(tLJAPaySchema.getAgentCode());
		tLJAGetSchema.setAgentGroup(tLJAPaySchema.getAgentGroup());
		tLJAGetSchema.setAccName(mAccName);
		tLJAGetSchema.setStartGetDate(CurrentDate);
		tLJAGetSchema.setShouldDate(CurrentDate);
		tLJAGetSchema.setAppntNo(tLJAPaySchema.getAppntNo());
		tLJAGetSchema.setSaleChnl("");
		tLJAGetSchema.setBankCode(mBankCode);
		tLJAGetSchema.setBankAccNo(mAccNo);

		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(tLJAPaySchema.getAppntNo());
		tLDPersonDB.getInfo();

		tLJAGetSchema.setDrawer(tLDPersonDB.getName());
		tLJAGetSchema.setDrawerID(tLDPersonDB.getIDNo());
		tLJAGetSchema.setGetNoticeNo(tLJAPaySchema.getGetNoticeNo());
		tLJAGetSchema.setOperator(mGlobalInput.Operator);
		tLJAGetSchema.setMakeDate(CurrentDate);
		tLJAGetSchema.setModifyDate(CurrentDate);
		tLJAGetSchema.setMakeTime(CurrentTime);
		tLJAGetSchema.setModifyTime(CurrentTime);

		double Sum = 0;
		double Tax = 0;
		double TaxAmount = 0;
		double NetAmount = 0;
		LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
		TaxCalculator.calBySchemaSet(mLJAPayPersonSet);
		for (int t = 1; t <= mLJAPayPersonSet.size(); t++) {

			tLJAPayPersonSchema = mLJAPayPersonSet.get(t);
			Sum = Sum + tLJAPayPersonSchema.getSumActuPayMoney();
			Tax =mLJAPayPersonSet.get(t).getTax();
			TaxAmount += tLJAPayPersonSchema.getTaxAmount();
			NetAmount +=tLJAPayPersonSchema.getNetAmount();
			tLJAPayPersonSchema.setSumActuPayMoney(-tLJAPayPersonSchema.getSumActuPayMoney());
			tLJAPayPersonSchema.setTaxAmount(-tLJAPayPersonSchema.getTaxAmount());
			tLJAPayPersonSchema.setNetAmount(tLJAPayPersonSchema.getNetAmount());
			tLJAPayPersonSchema.setEnterAccDate("");
			tLJAPayPersonSchema.setConfDate("");
			tLJAPayPersonSchema.setMakeDate(CurrentDate);
			tLJAPayPersonSchema.setModifyDate(CurrentDate);
			tLJAPayPersonSchema.setMakeTime(CurrentTime);
			tLJAPayPersonSchema.setModifyTime(CurrentTime);
			tLJAPayPersonSchema.setOperator(mGlobalInput.Operator);
			tLJAPayPersonSchema.setPayNo(nNo);
			nLJAPayPersonSet.add(tLJAPayPersonSchema);
		}
		tLJAGetSchema.setSumGetMoney(Sum);
		tLJAGetSchema.setTax(Tax);
		tLJAGetSchema.setTaxAmount(TaxAmount);
		tLJAGetSchema.setNetAmount(NetAmount);
		mLJAGetSet.add(tLJAGetSchema);
		return true;
	}

	private boolean prepareOutputData() {
		map.put(nLJAPayPersonSet, "INSERT");
		map.put(mLJAGetSet, "INSERT");
		map.put(mLCPremSet, "UPDATE");
		map.put(mLCPolSet, "UPDATE");
		map.put(mLCDutySet, "UPDATE");
		map.put(mLCGetSet, "UPDATE");

		map.put(dLCPremSet, "DELETE");
		map.put(dLCPolSet, "DELETE");
		map.put(dLCDutySet, "DELETE");
		map.put(dLCGetSet, "DELETE");

		map.put(mLCContSchema, "UPDATE");
		mResult.add(map);
		return true;
	}

	public static void main(String arg[]) {
		TransferData tTransferData = new TransferData();
		GlobalInput mGlobalInput = new GlobalInput();
		VData tVData = new VData();
		VData mResult = new VData();
		String GetNo = "3100000779991";
		String Contno = "JNK50526181000029";
		tTransferData.setNameAndValue("GetnoticeNo", GetNo);
		tTransferData.setNameAndValue("ContNo", Contno);
		tTransferData.setNameAndValue("PayMode", "7");
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "86";
		mGlobalInput.ManageCom = "86";
		tVData.addElement(mGlobalInput);
		tVData.addElement(tTransferData);
		RnRollbackDealBL tRnRollbackDealBL = new RnRollbackDealBL();
		if (!tRnRollbackDealBL.submitData(tVData, "")) {
			logger.debug("false!!!");
		}
	}
}
