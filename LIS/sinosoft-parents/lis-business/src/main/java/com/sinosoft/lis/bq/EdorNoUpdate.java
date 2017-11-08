package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全删除业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lh modified by Alex
 * @version 1.0
 */

// 功能：个人保单一个保全项目更新批单号处理
// 入口参数：个单的保单号、批单号和批改类型
// 出口参数：各个表单要更新的记录数据
public class EdorNoUpdate {
private static Logger logger = Logger.getLogger(EdorNoUpdate.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 将原来的updateSql都放入map后作为返回结果，以后在PubSubmit中作数据库操作----Alex */
	MMap map = new MMap();

	/** 数据操作字符串 */
	private String mOperate;
	private String mNewEdorno;
	private String mLastEdorno;
	private String mManageCom;
	private boolean isGrpEdor;// true--团单，false--个单

	public EdorNoUpdate() {
		mLastEdorno = "";
		mManageCom = "";
		this.isGrpEdor = true;
	}

	public EdorNoUpdate(String strOldEdorNo, String strManageCom, boolean isGrp) {
		mLastEdorno = strOldEdorNo;
		mManageCom = strManageCom;
		this.isGrpEdor = isGrp;
	}

	public String getNewEdorno() {
		return mNewEdorno;
	}

	public void setNewEdorno(String strNewEdorno) {
		mNewEdorno = strNewEdorno;
	}

	private boolean updateAsscociateTable() {

		String strLimit = PubFun.getNoLimit(mManageCom);
		if (isGrpEdor) {
			mNewEdorno = PubFun1.CreateMaxNo("EDORGRPNO", strLimit);
		} else {
			mNewEdorno = PubFun1.CreateMaxNo("EDORNO", strLimit);
		}

		// if (mLastEdorno.charAt(12) == '1') { //41为个单申请，只有此时才需要更换批单号
		// String strLimit = PubFun.getNoLimit(mManageCom);
		// mNewEdorno = PubFun1.CreateMaxNo("EDORNO", strLimit);
		// } else if (mLastEdorno.charAt(12) == '3') { //43为团单申请，只有此时才需要更换批单号
		// String strLimit = PubFun.getNoLimit(mManageCom);
		// mNewEdorno = PubFun1.CreateMaxNo("EDORGRPNO", strLimit);
		// } else {
		// String strLimit = PubFun.getNoLimit(mManageCom);
		// mNewEdorno = PubFun1.CreateMaxNo("OTHEREDORNO", strLimit);
		// }
		if (mNewEdorno == null || mNewEdorno.equals("")) {
			CError.buildErr(this, "生成新批单号失败!");
			return false;
		}

		String updateSql;

		String[] asscociateTable_EdorNo = { "LPGeneral", "LPGrpCont", "LPCont",
				"LPGeneralToRisk", "LPPol", "LPGrpAppnt", "LPGrpPol",
				"LPGetToAcc", "LPInsureAcc", "LPPremToAcc", "LPPrem", "LPGet",
				"LPDuty", "LPPrem_1", "LPInsureAccClass", "LPInsureAccTrace",
				"LPContPlanDutyParam", "LPContPlanRisk", "LPContPlan",
				"LPAppnt", "LPCustomerImpart", "LPInsuredRelated", "LPBnf",
				"LPInsured", "LPCustomerImpartParams", "LPInsureAccFee",
				"LPInsureAccClassFee", "LPContPlanFactory", "LPContPlanParam",
				"LPGrpFee", "LPGrpFeeParam", "LPGrpEdorMain", "LPGrpEdorItem",
				"LPEdorItem", "LPEdorMain", "LPMove", "LPEdorPrint",
				"LPAccMove", "LPAppntTrace", "LPLoan", "LPReturnLoan",
				"LPEdorPrint2","LPGUWError", "LPGUWMaster",
				"LPCUWError", "LPCUWMaster", "LPCUWSub", "LPUWError",
				"LPUWMaster", "LPUWSub", "LPPENoticeItem", "LPPENotice",
				"LPGCUWError", "LPGCUWMaster", "LPGCUWSub", "LPGUWSub",
				"LPSpec", "LPIssuePol", "LPGrpIssuePol", "LPCGrpSpec",
				"LPCSpec", "LPRReport", "LPAccount", "LPPerson", "LPAddress",
				"LPGrpAddress", "LPGrp", "LBGrpCont", "LBGeneral", "LBCont",
				"LBGeneralToRisk", "LBPol", "LBGrpAppnt", "LBGrpPol",
				"LBInsureAcc", "LBGetToAcc", "LBPremToAcc", "LBPrem", "LBGet",
				"LBDuty", "LBPrem_1", "LBInsureAccClass", "LBInsureAccTrace",
				"LBContPlanRisk", "LBContPlanDutyParam", "LBContPlan",
				"LBAppnt", "LBCustomerImpart", "LBInsuredRelated", "LBBnf",
				"LBGrpAddress", "LBInsured", "LBCustomerImpartParams",
				"LBInsureAccFee", "LBInsureAccClassFee", "LBContPlanFactory",
				"LBContPlanParam", "LBGrpFeeParam", "LBGrpFee", "LBAccount",
				"LPPayRuleFactory", "LPPayRuleParams", "LBPayRuleFactory",
				"LBPayRuleParams", "LPContState" };
		String[] asscociateTable_EndorsementNo = { "LCSpec", "LJAPayGrp",
				"LJSGetEndorse", "LJAGetEndorse", "LCCGrpSpec", "LCCSpec",
				"LOBSpec", "LOBCGrpSpec", "LOBCSpec", "LPSpec", "LPCGrpSpec",
				"LPCSpec" };
		String[] asscociateTable_OtherNo = { "LJFIGet", "LJAGet",
				"LJABonusGet", "LJAGetClaim", "LJAGetOther" };
		String[] asscociateTable_IncomeNo = { "LJAPay" };

		// 根据相关表名和字段更新批单号
		for (int i = 0; i < asscociateTable_EdorNo.length; i++) {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			updateSql = "update " + asscociateTable_EdorNo[i]
					+ " set EdorNo = '" + "?mNewEdorno?" + "' where EdorNo = '"
					+ "?mLastEdorno?" + "'";
			sqlbv.sql(updateSql);
			sqlbv.put("mNewEdorno", mNewEdorno);
			sqlbv.put("mLastEdorno", mLastEdorno);
			map.put(sqlbv, "UPDATE");
		}
		for (int i = 0; i < asscociateTable_EndorsementNo.length; i++) {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			updateSql = "update " + asscociateTable_EndorsementNo[i]
					+ " set EndorsementNo = '" + "?mNewEdorno?"
					+ "' where EndorsementNo = '" + "?mLastEdorno?" + "'";
			sqlbv.sql(updateSql);
			sqlbv.put("mNewEdorno", mNewEdorno);
			sqlbv.put("mLastEdorno", mLastEdorno);
			map.put(sqlbv, "UPDATE");
		}
		for (int i = 0; i < asscociateTable_OtherNo.length; i++) {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			updateSql = "update " + asscociateTable_OtherNo[i]
					+ " set OtherNo = '" + "?mNewEdorno?" + "' where OtherNo = '"
					+ "?mLastEdorno?" + "' and OtherNoType='3'";
			sqlbv.sql(updateSql);
			sqlbv.put("mNewEdorno", mNewEdorno);
			sqlbv.put("mLastEdorno", mLastEdorno);
			map.put(sqlbv, "UPDATE");
		}
		for (int i = 0; i < asscociateTable_IncomeNo.length; i++) {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			updateSql = "update " + asscociateTable_IncomeNo[i]
					+ " set IncomeNo = '" + "?mNewEdorno?" + "' where IncomeNo = '"
					+ "?mLastEdorno?" + "' and IncomeType='3'";
			sqlbv.sql(updateSql);
			sqlbv.put("mNewEdorno", mNewEdorno);
			sqlbv.put("mLastEdorno", mLastEdorno);
			map.put(sqlbv, "UPDATE");
		}

		mResult.add(map);
		return true;
	}

	public boolean updateEdorNo() {
		if (mLastEdorno.equals("")) {
			this.buildError("updateEdorNo", "没有制订新批单号！");
			return false;
		}

		if (!updateAsscociateTable()) {
			this.buildError("updateEdorNo", "取更新关联表失败！");
			return false;
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "EdorNoUpdate";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		EdorNoUpdate tEdorNoUpdate = new EdorNoUpdate("410110000000070", "86",
				true);
		tEdorNoUpdate.updateEdorNo();
		VData vdata = tEdorNoUpdate.getResult();
		PubSubmit ps = new PubSubmit();
		if (ps.submitData(vdata, "")) {
			logger.debug("succeed in pubsubmit");
		}
	}
}
