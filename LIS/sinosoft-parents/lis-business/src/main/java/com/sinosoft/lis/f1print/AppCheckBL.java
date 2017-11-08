package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class AppCheckBL {
private static Logger logger = Logger.getLogger(AppCheckBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	// private GlobalInput mGI = new GlobalInput();//操作员信息
	private TransferData mTransferData = new TransferData(); // 传递非SCHEMA信息

	private String mManageCom; // 管理机构
	private String mStartDate; // 开始日期
	private String mEndDate; // 结束
	private String mPrintDate; // 打印日期
	private String mScanType; // 打印日期
	private String mPrtNo; // 打印日期
	private String mInsuredName; // 打印日期
	private String mAgentCode; // 打印日期
	private String mRiskCode; // 打印日期

	private String mOperator; // 操作员
	private String mOperatorNo; // 操作员
	private String mAppCode; // 操作员
	private String mBPOID; // 操作员

	private String mCurrentDate = PubFun.getCurrentDate(); // 当前日期

	private GlobalInput mGlobalInput = new GlobalInput();

	public AppCheckBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			buildError("getInputData", "获取传入的操作员登录信息失败。");
			return false;
		}

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			buildError("getInputData", "获取传入的管理机构及日期信息失败。");
			return false;
		}

		mOperator = mGlobalInput.Operator;
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 管理机构
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 起始日期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 终止日期
		mScanType = (String) mTransferData.getValueByName("ScanType"); 
		mPrtNo = (String) mTransferData.getValueByName("PrtNo"); 
		mOperatorNo = (String) mTransferData.getValueByName("OperatorNo"); 
		mAppCode = (String) mTransferData.getValueByName("AppCode"); 
		mBPOID = (String) mTransferData.getValueByName("BPOID"); 
		mRiskCode = (String) mTransferData.getValueByName("RiskCode"); 
		//mAgentCode = (String) mTransferData.getValueByName("AgentCode"); 
		//mInsuredName = (String) mTransferData.getValueByName("InsuredName"); 
		//mPrintDate = (String) mTransferData.getValueByName("PrintDate"); // 打印日期

		if (mManageCom == null || mManageCom.trim().equals("")) {
			mManageCom =mGlobalInput.ManageCom;
		}
//		if (mStartDate == null || mStartDate.trim().equals("")) {
//			buildError("getInputData", "传入的起始日期信息为空。");
//			return false;
//		}
//		if (mEndDate == null || mEndDate.trim().equals("")) {
//			buildError("getInputData", "传入的终止日期信息为空。");
//			return false;
//		}
		if (mPrintDate == null || mPrintDate.trim().equals("")) {
			mPrintDate = mCurrentDate;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private boolean getPrintData() {
		//契约工作量统计
		if("app2".equals(mAppCode)){
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("AppWorkAmountSatic.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			aListTable.setName("LIST");
			
			// 根据传入的管理机构查询中心支公司。
//			String strComSQL = "";
//			int tComLength = mManageCom.length();
//			if (tComLength <= 6) {
//				strComSQL = "select a.comcode,a.name,a.upcomcode,"
//					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
//					+ " from ldcom a where a.comgrade='03'"
//					+ " and a.comcode like '" + mManageCom + "%'"
//					+ " order by a.comcode";
//			} else { // 当机构长度大于6位时，该机构为营销服务部，应取其前六位以确定所属中支及分公司。
//				strComSQL = "select a.comcode,a.name,a.upcomcode,"
//					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
//					+ " from ldcom a where a.comgrade='03'"
//					+ " and a.comcode like '" + mManageCom.substring(0, 6)
//					+ "%'" + " order by a.comcode";
//			}
			int dataCount = 0;
			int all= 0;
			int YC_11=0;
			int YC_21=0;
			int YC_35=0;
			int YC_16=0;
			int CJ_11=0;
			int CJ_21=0;
			int CJ_35=0;
			int CJ_16=0;
			int WT_11=0;
			int WT_21=0;
			int WT_35=0;
			int WT_16=0;
			int KH_all=0;
			ExeSQL ComExeSQL = new ExeSQL();
			SSRS ComSSRS = new SSRS();
//			ComSSRS = ComExeSQL.execSQL(strComSQL);
//			int Count_ComSSRS = ComSSRS.getMaxRow();
//			String managecom = mManageCom;
//			if (Count_ComSSRS <= 0) {
//				return false;
//			}
			
			//循环所有三个岗位的不同的操作员
			//新加客户关联岗位
			SSRS SOperator =new SSRS();
//			String strOperator =" select distinct defaultoperator from lbmission where activityid ='0000001002' and defaultoperator is not null "
			String strOperator =" select distinct defaultoperator from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010004') and defaultoperator is not null "
				+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(mOperatorNo, "?mOperatorNo?"))
				+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
				+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
				+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
				+"union"
//				+" select distinct defaultoperator from lbmission where activityid in ('0000001091','0000001090') and defaultoperator is not null "		
				+" select distinct defaultoperator from lbmission where activityid in (select activityid from lwactivity  where functionid in('10010016','10010017')) and defaultoperator is not null "
				+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(mOperatorNo, "?mOperatorNo?"))
				+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
				+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
				+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
				+"union"
//				+" select distinct defaultoperator from lbmission where activityid ='0000001404' and defaultoperator is not null "		
				+" select distinct defaultoperator from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010056') and defaultoperator is not null "
				+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(mOperatorNo, "?mOperatorNo?"))
				+ReportPubFun.getWherePartLike("MissionProp9", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
				+ReportPubFun.getWherePartLike("MissionProp9", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
				+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
				+" order by defaultoperator";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strOperator);
			sqlbv1.put("mOperatorNo", mOperatorNo);
			sqlbv1.put("mManageCom", mManageCom);
			sqlbv1.put("mGlobalInput", mGlobalInput.ComCode);
			sqlbv1.put("mStartDate", mStartDate);
			sqlbv1.put("mEndDate", mEndDate);
			SOperator =ComExeSQL.execSQL(sqlbv1);
			for (int i = 1; i <= SOperator.getMaxRow(); i++) {
				String tOperator = SOperator.GetText(i, 1); // 操作员代码
				String strListSQL =" select "
//					+" (select count(1) a from lbmission where activityid ='0000001090' and substr(missionprop1,0,4)='8611'"
					+" (select count(1) a from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010016') and substr(missionprop1,1,4)='8611'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) b from lbmission where activityid ='0000001090'  and substr(missionprop1,0,4) in ('8615','8625','8635')"
					+"), (select count(1) b from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010016')  and substr(missionprop1,1,4) in ('8615','8625','8635')"
					
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) c from lbmission where activityid ='0000001090'  and substr(missionprop1,0,4)='8621'"
					+"), (select count(1) c from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010016')  and substr(missionprop1,1,4)='8621'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) d from lbmission where activityid ='0000001090'  and substr(missionprop1,0,4)='8616'"
					+"), (select count(1) d from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010016')  and substr(missionprop1,1,4)='8616'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) a from lbmission where activityid ='0000001091' and substr(missionprop1,0,4)='8611'"
					+"), (select count(1) a from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010017') and substr(missionprop1,1,4)='8611'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) b from lbmission where activityid ='0000001091'  and substr(missionprop1,0,4) in ('8615','8625','8635')"
					+"), (select count(1) b from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010017')  and substr(missionprop1,1,4) in ('8615','8625','8635')"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) c from lbmission where activityid ='0000001091'  and substr(missionprop1,0,4)='8621'"
					+"), (select count(1) c from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010017')  and substr(missionprop1,1,4)='8621'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) d from lbmission where activityid ='0000001091'  and substr(missionprop1,0,4)='8616'"
					+"), (select count(1) d from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010017')  and substr(missionprop1,1,4)='8616'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) a from lbmission where activityid ='0000001002'  and substr(missionprop1,0,4)='8611'"
					+"), (select count(1) a from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010004')  and substr(missionprop1,1,4)='8611'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) b from lbmission where activityid ='0000001002'  and substr(missionprop1,0,4) in ('8615','8625','8635')"
					+"), (select count(1) b from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010004')  and substr(missionprop1,1,4) in ('8615','8625','8635')"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) c from lbmission where activityid ='0000001002'  and substr(missionprop1,0,4)='8621'"
					+"), (select count(1) c from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010004')  and substr(missionprop1,1,4)='8621'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) d from lbmission where activityid ='0000001002'  and substr(missionprop1,0,4)='8616'"
					+"), (select count(1) d from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010004')  and substr(missionprop1,1,4)='8616'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))	
					+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) d from lbmission where activityid ='0000001404' "
					+"), (select count(1) d from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010056') "
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))	
					+ReportPubFun.getWherePartLike("MissionProp9", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("MissionProp9", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+") from dual";
				logger.debug("tSQL :"+strListSQL);
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(strListSQL);
				sqlbv2.put("tOperator", tOperator);
				sqlbv2.put("mManageCom", mManageCom);
				sqlbv2.put("mGlobalInput", mGlobalInput.ComCode);
				sqlbv2.put("mStartDate", mStartDate);
				sqlbv2.put("mEndDate", mEndDate);
				
				ExeSQL ListExeSQL = new ExeSQL();
				SSRS ListSSRS = new SSRS();
				ListSSRS = ListExeSQL.execSQL(sqlbv2);
				int ListCount = ListSSRS.getMaxRow();
				dataCount=dataCount+ListCount;
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql("select count(1) from lbmission where"
//						+" activityid ='0000001002' and defaultoperator='"+tOperator+"' "
						+" activityid  in (select activityid from lwactivity  where functionid ='10010004') and defaultoperator='"+"?tOperator?"+"' "
						+" and substr(missionprop1,1,4) in ('8611','8621','8616','8615','8625','8635') "
						+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
						+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
						+ReportPubFun.getWherePartLike("MissionProp5", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?")));
				sqlbv3.put("tOperator", tOperator);
				sqlbv3.put("mManageCom", mManageCom);
				sqlbv3.put("mGlobalInput", mGlobalInput.ComCode);
				sqlbv3.put("mStartDate", mStartDate);
				sqlbv3.put("mEndDate", mEndDate);
				String tSumOperatorCount1 =ComExeSQL.getOneValue(sqlbv3);
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql("select count(1) from lbmission where"
//						+" activityid in ('0000001090','0000001091') and defaultoperator='"+tOperator+"' "
						+" activityid in (select activityid from lwactivity  where functionid in('10010016','10010017')) and defaultoperator='"+"?tOperator?"+"' "
						+" and substr(missionprop1,1,4) in ('8611','8621','8616','8615','8625','8635') "
						+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
						+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
						+ReportPubFun.getWherePartLike("MissionProp13", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?")));
				sqlbv4.put("tOperator", tOperator);
				sqlbv4.put("mManageCom", mManageCom);
				sqlbv4.put("mGlobalInput", mGlobalInput.ComCode);
				sqlbv4.put("mStartDate", mStartDate);
				sqlbv4.put("mEndDate", mEndDate);
				String tSumOperatorCount2 =ComExeSQL.getOneValue(sqlbv4);
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql("select count(1) from lbmission where"
//						+" activityid ='0000001404' and defaultoperator='"+tOperator+"' "
						+" activityid  in (select activityid from lwactivity  where functionid ='10010056') and defaultoperator='"+"?tOperator?"+"' "
						+ReportPubFun.getWherePart("outdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
						+ReportPubFun.getWherePartLike("MissionProp9", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
						+ReportPubFun.getWherePartLike("MissionProp9", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?")));
				sqlbv5.put("tOperator", tOperator);
				sqlbv5.put("mManageCom", mManageCom);
				sqlbv5.put("mGlobalInput", mGlobalInput.ComCode);
				sqlbv5.put("mStartDate", mStartDate);
				sqlbv5.put("mEndDate", mEndDate);
				String tSumOperatorCount3 =ComExeSQL.getOneValue(sqlbv5);
				String tSumOperatorCount=String.valueOf(Integer.parseInt(tSumOperatorCount1)+Integer.parseInt(tSumOperatorCount2)+Integer.parseInt(tSumOperatorCount3));
				for (int j = 1; j <= ListSSRS.MaxRow; j++) {
					String[] cols = new String[16];
					cols[0] = i + "";
					cols[1] = tOperator;
					cols[2] = tSumOperatorCount;
					cols[3] = ListSSRS.GetText(j, 1);
					cols[4] = ListSSRS.GetText(j, 2);
					cols[5] = ListSSRS.GetText(j, 3);
					cols[6] = ListSSRS.GetText(j, 4);
					cols[7] = ListSSRS.GetText(j, 5);
					cols[8] = ListSSRS.GetText(j, 6);
					cols[9] = ListSSRS.GetText(j, 7);
					cols[10] = ListSSRS.GetText(j, 8);
					cols[11] = ListSSRS.GetText(j, 9);
					cols[12] = ListSSRS.GetText(j, 10);
					cols[13] = ListSSRS.GetText(j, 11);
					cols[14] = ListSSRS.GetText(j, 12);
					cols[15] = ListSSRS.GetText(j, 13);
					aListTable.add(cols);
					all =all+Integer.parseInt(tSumOperatorCount);
					YC_11=YC_11+Integer.parseInt(ListSSRS.GetText(j, 1));
					YC_21=YC_21+Integer.parseInt(ListSSRS.GetText(j, 2));
					YC_35=YC_35+Integer.parseInt(ListSSRS.GetText(j, 3));
					YC_16=YC_16+Integer.parseInt(ListSSRS.GetText(j, 4));
					CJ_11=CJ_11+Integer.parseInt(ListSSRS.GetText(j, 5));
					CJ_21=CJ_21+Integer.parseInt(ListSSRS.GetText(j, 6));
					CJ_35=CJ_35+Integer.parseInt(ListSSRS.GetText(j, 7));
					CJ_16=CJ_16+Integer.parseInt(ListSSRS.GetText(j, 8));
					WT_11=WT_11+Integer.parseInt(ListSSRS.GetText(j, 9));
					WT_21=WT_21+Integer.parseInt(ListSSRS.GetText(j, 10));
					WT_35=WT_35+Integer.parseInt(ListSSRS.GetText(j, 11));
					WT_16=WT_16+Integer.parseInt(ListSSRS.GetText(j, 12));
					KH_all=KH_all+Integer.parseInt(ListSSRS.GetText(j, 13));
				}
			}
			String[] coll =new String [16];
			coll[0]="";
			coll[1]="合计:";
			coll[2]=String.valueOf(all);
			coll[3]=String.valueOf(YC_11);
			coll[4]=String.valueOf(YC_21);
			coll[5]=String.valueOf(YC_35);
			coll[6]=String.valueOf(YC_16);
			coll[7]=String.valueOf(CJ_11);
			coll[8]=String.valueOf(CJ_21);
			coll[9]=String.valueOf(CJ_35);
			coll[10]=String.valueOf(CJ_16);
			coll[11]=String.valueOf(WT_11);
			coll[12]=String.valueOf(WT_21);
			coll[13]=String.valueOf(WT_35);
			coll[14]=String.valueOf(WT_16);
			coll[15]=String.valueOf(KH_all);
			aListTable.add(coll);
			
			String[] col = new String[16];
			xmlexport.addListTable(aListTable, col);
			aTextTag.add("StatiStartDate", StrTool.getChnDate(mStartDate));
			aTextTag.add("StatiEndDate", StrTool.getChnDate(mEndDate));
			aTextTag.add("PrintDate", PubFun.getCurrentDate());
			
			xmlexport.addTextTag(aTextTag);
			
			mResult.addElement(xmlexport);
			if (dataCount == 0) {
				buildError("getInputData", "没有可以打印的数据");
				return false;
			}
		}
		//初审扫描工作量统计
		if("app1".equals(mAppCode)){
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("FirstTrialScanCount.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			aListTable.setName("LIST");
			
			// 根据传入的管理机构查询中心支公司。
//			String strComSQL = "";
//			int tComLength = mManageCom.length();
//			if (tComLength <= 6) {
//				strComSQL = "select a.comcode,a.name,a.upcomcode,"
//					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
//					+ " from ldcom a where a.comgrade='03'"
//					+ " and a.comcode like '" + mManageCom + "%'"
//					+ " order by a.comcode";
//			} else { // 当机构长度大于6位时，该机构为营销服务部，应取其前六位以确定所属中支及分公司。
//				strComSQL = "select a.comcode,a.name,a.upcomcode,"
//					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
//					+ " from ldcom a where a.comgrade='03'"
//					+ " and a.comcode like '" + mManageCom.substring(0, 6)
//					+ "%'" + " order by a.comcode";
//			}
			int dataCount = 0;
			int all= 0;
			int YC_11=0;
			int YC_21=0;
			int YC_35=0;
			int YC_16=0;
			int CJ_11=0;
			int CJ_21=0;
			int CJ_35=0;
			int CJ_16=0;
			int WT_11=0;
			int WT_21=0;
			int WT_35=0;
			int WT_16=0;
			ExeSQL ComExeSQL = new ExeSQL();
			SSRS ComSSRS = new SSRS();
//			ComSSRS = ComExeSQL.execSQL(strComSQL);
//			int Count_ComSSRS = ComSSRS.getMaxRow();
//			String managecom = mManageCom;
//			if (Count_ComSSRS <= 0) {
//				return false;
//			}
			
			//循环所有三个岗位的不同的操作员
			SSRS SOperator =new SSRS();
			String strOperator =" select distinct scanoperator from es_doc_main where busstype like 'TB%'"
				+ReportPubFun.getWherePart("scanoperator", ReportPubFun.getParameterStr(mOperatorNo, "?mOperatorNo?"))
				+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
				+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
				+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
				+" union"
//				+" select distinct defaultoperator from lwmission where activityid ='0000001020' and defaultoperator is not null"
				+" select distinct defaultoperator from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010010') and defaultoperator is not null"
				+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(mOperatorNo, "?mOperatorNo?"))
				+" and missionprop1 in (select prtno from lcpol where 1=1 "
				+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
				+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
				+" )"
				+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
				+" order by scanoperator";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(strOperator);
			sqlbv6.put("mOperatorNo", mOperatorNo);
			sqlbv6.put("mManageCom", mManageCom);
			sqlbv6.put("mGlobalInput", mGlobalInput.ComCode);
			sqlbv6.put("mStartDate", mStartDate);
			sqlbv6.put("mEndDate", mEndDate);
			SOperator =ComExeSQL.execSQL(sqlbv6);
			for (int i = 1; i <= SOperator.getMaxRow(); i++) {
				String tOperator = SOperator.GetText(i, 1); // 操作员代码
				String strListSQL =" select "
					+" (select count(1)  from es_doc_main where subtype in (select subtype from es_doc_def where busstype ='TB') and substr(doccode,1,4)='8611'"
					+ReportPubFun.getWherePart("scanoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"), (select count(1)  from es_doc_main where subtype in (select subtype from es_doc_def where busstype ='TB') and substr(doccode,1,4)='8621'"
					+ReportPubFun.getWherePart("scanoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"), (select count(1)  from es_doc_main where subtype in (select subtype from es_doc_def where busstype ='TB') and substr(doccode,1,4) in ('861','8625','8635')"
					+ReportPubFun.getWherePart("scanoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"), (select count(1)  from es_doc_main where subtype in (select subtype from es_doc_def where busstype ='TB') and substr(doccode,1,4)='8616'"
					+ReportPubFun.getWherePart("scanoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"), (select count(1)  from es_doc_main where subtype in (select subtype from es_doc_def where busstype ='TB4') "
					+ReportPubFun.getWherePart("scanoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"), (select count(1)  from es_doc_main where subtype in (select subtype from es_doc_def where busstype ='TB1')"
					+ReportPubFun.getWherePart("scanoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"), (select count(1)  from es_doc_main where subtype in (select subtype from es_doc_def where busstype ='TB2')"
					+ReportPubFun.getWherePart("scanoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"), (select count(1)  from es_doc_main where subtype in (select subtype from es_doc_def where busstype ='TB3')"
					+ReportPubFun.getWherePart("scanoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), ( select count(1) from lwmission where activityid ='0000001020'  and substr(missionprop1,0,4)='8611'"
					+"), ( select count(1) from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010010')  and substr(missionprop1,1,4)='8611'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+" and missionprop1 in (select prtno from lcpol where 1=1 "
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) from lwmission where activityid ='0000001020'  and substr(missionprop1,0,4)='8621'"
					+"), (select count(1) from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010010')  and substr(missionprop1,1,4)='8621'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+" and missionprop1 in (select prtno from lcpol where 1=1 "
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) from lwmission where activityid ='0000001020'  and substr(missionprop1,0,4) in ('8615','8625','8635')"
					+"), (select count(1) from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010010')  and substr(missionprop1,1,4) in ('8615','8625','8635')"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+" and missionprop1 in (select prtno from lcpol where 1=1 "
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
//					+"), (select count(1) from lwmission where activityid ='0000001020'  and substr(missionprop1,0,4)='8616'"
					+"), (select count(1) from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010010')  and substr(missionprop1,1,4)='8616'"
					+ReportPubFun.getWherePart("defaultoperator", ReportPubFun.getParameterStr(tOperator, "?tOperator?"))
					+" and missionprop1 in (select prtno from lcpol where 1=1 "
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+") from dual";
				logger.debug("tSQL :"+strListSQL);
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(strListSQL);
				sqlbv7.put("tOperator", tOperator);
				sqlbv7.put("mManageCom", mManageCom);
				sqlbv7.put("mGlobalInput", mGlobalInput.ComCode);
				sqlbv7.put("mStartDate", mStartDate);
				sqlbv7.put("mEndDate", mEndDate);
				ExeSQL ListExeSQL = new ExeSQL();
				SSRS ListSSRS = new SSRS();
				ListSSRS = ListExeSQL.execSQL(sqlbv7);
				int ListCount = ListSSRS.getMaxRow();
				dataCount=dataCount+ListCount;
				int tSumOperatorCount=0;
				for(int l=1;l<=ListSSRS.getMaxCol();l++){
					tSumOperatorCount=tSumOperatorCount+Integer.parseInt(ListSSRS.GetText(1, l));
				}
//				String tSumOperatorCount =ComExeSQL.getOneValue("select count(1) from lwmission where"
//						+" activityid in ('0000001090','0000001091','0000001002') and defaultoperator='"+tOperator+"' "
//						+" and substr(missionprop1,0,4) in ('8611','8621','8616','8615','8625','8635') "
//						+ReportPubFun.getWherePart("Makedate", mStartDate, mEndDate, 1));
				for (int j = 1; j <= ListSSRS.MaxRow; j++) {
					String[] cols = new String[15];
					cols[0] = i + "";
					cols[1] = tOperator;
					cols[2] = String.valueOf(tSumOperatorCount);
					cols[3] = ListSSRS.GetText(j, 1);
					cols[4] = ListSSRS.GetText(j, 2);
					cols[5] = ListSSRS.GetText(j, 3);
					cols[6] = ListSSRS.GetText(j, 4);
					cols[7] = ListSSRS.GetText(j, 5);
					cols[8] = ListSSRS.GetText(j, 6);
					cols[9] = ListSSRS.GetText(j, 7);
					cols[10] = ListSSRS.GetText(j, 8);
					cols[11] = ListSSRS.GetText(j, 9);
					cols[12] = ListSSRS.GetText(j, 10);
					cols[13] = ListSSRS.GetText(j, 11);
					cols[14] = ListSSRS.GetText(j, 12);
					aListTable.add(cols);
					all =all+tSumOperatorCount;
					YC_11=YC_11+Integer.parseInt(ListSSRS.GetText(j, 1));
					YC_21=YC_21+Integer.parseInt(ListSSRS.GetText(j, 2));
					YC_35=YC_35+Integer.parseInt(ListSSRS.GetText(j, 3));
					YC_16=YC_16+Integer.parseInt(ListSSRS.GetText(j, 4));
					CJ_11=CJ_11+Integer.parseInt(ListSSRS.GetText(j, 5));
					CJ_21=CJ_21+Integer.parseInt(ListSSRS.GetText(j, 6));
					CJ_35=CJ_35+Integer.parseInt(ListSSRS.GetText(j, 7));
					CJ_16=CJ_16+Integer.parseInt(ListSSRS.GetText(j, 8));
					WT_11=WT_11+Integer.parseInt(ListSSRS.GetText(j, 9));
					WT_21=WT_21+Integer.parseInt(ListSSRS.GetText(j, 10));
					WT_35=WT_35+Integer.parseInt(ListSSRS.GetText(j, 11));
					WT_16=WT_16+Integer.parseInt(ListSSRS.GetText(j, 12));
				}
			}
			String[] coll =new String [15];
			coll[0]="";
			coll[1]="合计:";
			coll[2]=String.valueOf(all);
			coll[3]=String.valueOf(YC_11);
			coll[4]=String.valueOf(YC_21);
			coll[5]=String.valueOf(YC_35);
			coll[6]=String.valueOf(YC_16);
			coll[7]=String.valueOf(CJ_11);
			coll[8]=String.valueOf(CJ_21);
			coll[9]=String.valueOf(CJ_35);
			coll[10]=String.valueOf(CJ_16);
			coll[11]=String.valueOf(WT_11);
			coll[12]=String.valueOf(WT_21);
			coll[13]=String.valueOf(WT_35);
			coll[14]=String.valueOf(WT_16);
			aListTable.add(coll);
			
			String[] col = new String[15];
			xmlexport.addListTable(aListTable, col);
			aTextTag.add("StatiStartDate", StrTool.getChnDate(mStartDate));
			aTextTag.add("StatiEndDate", StrTool.getChnDate(mEndDate));
			aTextTag.add("PrintDate", PubFun.getCurrentDate());
			
			xmlexport.addTextTag(aTextTag);
			
			mResult.addElement(xmlexport);
			if (dataCount == 0) {
				buildError("getInputData", "没有可以打印的数据");
				return false;
			}
		}
		if("app3".equals(mAppCode)){
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("BPOWorkAmount.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			aListTable.setName("LIST");
			
			// 根据传入的管理机构查询中心支公司。
//			String strComSQL = "";
//			int tComLength = mManageCom.length();
//			if (tComLength <= 6) {
//				strComSQL = "select a.comcode,a.name,a.upcomcode,"
//					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
//					+ " from ldcom a where a.comgrade='03'"
//					+ " and a.comcode like '" + mManageCom + "%'"
//					+ " order by a.comcode";
//			} else { // 当机构长度大于6位时，该机构为营销服务部，应取其前六位以确定所属中支及分公司。
//				strComSQL = "select a.comcode,a.name,a.upcomcode,"
//					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
//					+ " from ldcom a where a.comgrade='03'"
//					+ " and a.comcode like '" + mManageCom.substring(0, 6)
//					+ "%'" + " order by a.comcode";
//			}
			int dataCount = 0;
			//以下命名不准确 结果正确
			int all= 0;
			int YC_11=0;
			int YC_21=0;
			int YC_35=0;
			int YC_16=0;
			int CJ_11=0;
			int CJ_21=0;
			int CJ_35=0;
			int CJ_16=0;
			int WT_11=0;
			int WT_21=0;
			int WT_35=0;
			int WT_16=0;
			int QJ_11=0;
			int QJ_21=0;
			int QJ_35=0;
			int QJ_16=0;
			ExeSQL ComExeSQL = new ExeSQL();
			SSRS ComSSRS = new SSRS();
//			ComSSRS = ComExeSQL.execSQL(strComSQL);
//			int Count_ComSSRS = ComSSRS.getMaxRow();
//			String managecom = mManageCom;
//			if (Count_ComSSRS <= 0) {
//				return false;
//			}
			
			//循环所有三个岗位的不同的操作员
			SSRS SOperator =new SSRS();
			String strOperator ="  select distinct bpoid from bpoallotrate where allottype='TB'"
				+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
				+" order by bpoid";
			String tRiskSQL ="";
			if(mRiskCode!=null&&!"".equals(mRiskCode)){
				tRiskSQL =" and exists (select 1 from lcpol where prtno =a.missionprop1 and riskcode ='"+"?mRiskCode?"+"')";
			}
			int c=0;
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(strOperator);
			sqlbv8.put("mBPOID", mBPOID);
			SSRS tSSRS =new SSRS();
			ExeSQL tExeSQL =new ExeSQL();
			SOperator =tExeSQL.execSQL(sqlbv8);
			for(int m=1;m<=SOperator.getMaxRow();m++){
				String tBPOID =SOperator.GetText(m, 1);
				SSRS SManageCom =new SSRS();
				String strManageCom ="select managecom from bpoallotrate where allottype ='TB' and bpoid in (select distinct bpoid from bpoallotrate where allottype='TB' "
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(tBPOID, "?tBPOID?"))
					+") and ('"+"?mManageCom?"+"' like concat(managecom,'%') or managecom like concat('"+"?mManageCom?"+"','%')) order by managecom";
				SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
				sqlbv9.sql(strManageCom);
				sqlbv9.put("tBPOID", tBPOID);
				sqlbv9.put("mManageCom", mManageCom);
				SManageCom =ComExeSQL.execSQL(sqlbv9);
			for (int i = 1; i <= SManageCom.getMaxRow(); i++) {
				c++;//序列号
				String tManageCom = SManageCom.GetText(i, 1); // 外包四位管理机构
				//tSSRS =tExeSQL.execSQL("select managecom from bpoallotrate where allottype ='TB' and bpoid='"+tBPOID+"' and mManageCom like managecom");
				String strListSQL1 =" select "
//					+" (select count(1) from lwmission a where activityid ='0000001089' and trim(missionprop1) like '8611%'"
					+" (select count(1) from lwmission a where activityid  in (select activityid from lwactivity  where functionid ='10010006') and trim(missionprop1) like '8611%'"
					+" and exists (select 1 from es_doc_main where doccode =a.missionprop1 "
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" and  substr(managecom,1,4)  in (select managecom from bpoallotrate where 1=1 "
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(tBPOID, "?tBPOID?"))
					+" ) "
					+" )"
					+tRiskSQL
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"),"
//					+" (select count(1) from lwmission a where activityid ='0000001089' and (missionprop1 like '8615%' or missionprop1 like '8625%' or missionprop1 like '8635%')"
					+" (select count(1) from lwmission a where activityid  in (select activityid from lwactivity  where functionid ='10010006') and (missionprop1 like '8615%' or missionprop1 like '8625%' or missionprop1 like '8635%')"
					+" and exists (select 1 from es_doc_main where doccode =a.missionprop1 "
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" and  substr(managecom,1,4)  in (select managecom from bpoallotrate where 1=1 "
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(tBPOID, "?tBPOID?"))
					+" ) "
					+" )"
					+tRiskSQL
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"),"
//					+" (select count(1) from lwmission a where activityid ='0000001089' and trim(missionprop1) like '8621%'"
					+" (select count(1) from lwmission a where activityid  in (select activityid from lwactivity  where functionid ='10010006') and trim(missionprop1) like '8621%'"
					+" and exists (select 1 from es_doc_main where doccode =a.missionprop1  "
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" and substr(managecom,1,4)  in (select managecom from bpoallotrate where 1=1 "
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(tBPOID, "?tBPOID?"))
					+" ) "
					+" )"
					+tRiskSQL
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"),"
//					+" (select count(1) from lwmission a where activityid ='0000001089' and trim(missionprop1) like '8616%'"
					+" (select count(1) from lwmission a where activityid  in (select activityid from lwactivity  where functionid ='10010006') and trim(missionprop1) like '8616%'"
					+" and exists (select 1 from es_doc_main where doccode =a.missionprop1 "
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" and substr(managecom,1,4)  in (select managecom from bpoallotrate where 1=1"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(tBPOID, "?tBPOID?"))
					+" ) "
					+" )"
					+tRiskSQL
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+")"
					+" from dual";

					SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
					sqlbv10.sql(strListSQL1);
					sqlbv10.put("mRiskCode", mRiskCode);
					sqlbv10.put("tManageCom", tManageCom);
					sqlbv10.put("mGlobalInput", this.mGlobalInput.ComCode);
					sqlbv10.put("tBPOID", tBPOID);
					sqlbv10.put("mStartDate", mStartDate);
					sqlbv10.put("mEndDate", mEndDate);
					


				String strListSQL11 =" select "
//					+" (select count(1) from lbmission a where activityid ='0000001089' and trim(missionprop1) like '8611%'"
					+" (select count(1) from lbmission a where activityid  in (select activityid from lwactivity  where functionid ='10010006') and trim(missionprop1) like '8611%'"
					+" and exists (select 1 from es_doc_main where doccode =a.missionprop1 "
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" and substr(managecom,1,4)  in (select managecom from bpoallotrate where 1=1 "
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(tBPOID, "?tBPOID?"))
					+" ) "
					+" )"
					+tRiskSQL
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"),"
//					+" (select count(1) from lbmission a where activityid ='0000001089' and (missionprop1 like '8615%' or missionprop1 like '8625%' or missionprop1 like '8635%')"
					+" (select count(1) from lbmission a where activityid  in (select activityid from lwactivity  where functionid ='10010006') and (missionprop1 like '8615%' or missionprop1 like '8625%' or missionprop1 like '8635%')"
					+" and exists (select 1 from es_doc_main where doccode =a.missionprop1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" and substr(managecom,1,4)  in (select managecom from bpoallotrate where 1=1 "
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(tBPOID, "?tBPOID?"))
					+" ) "
					+" )"
					+tRiskSQL
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"),"
//					+" (select count(1) from lbmission a where activityid ='0000001089' and trim(missionprop1) like '8621%'"
					+" (select count(1) from lbmission a where activityid  in (select activityid from lwactivity  where functionid ='10010006') and trim(missionprop1) like '8621%'"
					+" and exists (select 1 from es_doc_main where doccode =a.missionprop1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" and substr(managecom,1,4)  in (select managecom from bpoallotrate where 1=1"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(tBPOID, "?tBPOID?"))
					+" ) "
					+" )"
					+tRiskSQL
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+"),"
//					+" (select count(1) from lbmission a where activityid ='0000001089' and trim(missionprop1) like '8616%'"
					+" (select count(1) from lbmission a where activityid  in (select activityid from lwactivity  where functionid ='10010006') and trim(missionprop1) like '8616%'"
					+" and exists (select 1 from es_doc_main where doccode =a.missionprop1 "
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" and substr(managecom,1,4)  in (select managecom from bpoallotrate where 1=1 "
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(tBPOID, "?tBPOID?"))
					+" ) "
					+" )"
					+tRiskSQL
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+")"
					+" from dual";
				SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
				sqlbv11.sql(strListSQL11);
				sqlbv11.put("mRiskCode", mRiskCode);
				sqlbv11.put("tManageCom", tManageCom);
				sqlbv11.put("mGlobalInput", this.mGlobalInput.ComCode);
				sqlbv11.put("tBPOID", tBPOID);
				sqlbv11.put("mStartDate", mStartDate);
				sqlbv11.put("mEndDate", mEndDate);
				
		String strListSQL2=" select "
					+" (select count(1) from bpomissionstate where dealtype in ('02','03','04') and bussno like '8611%'"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+"), "
					+" (select count(1) from bpomissionstate where dealtype in ('02','03','04') and (bussno like '8615%' or bussno like '8625%' or bussno like '8635%')"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+"), "
					+" (select count(1) from bpomissionstate where dealtype in ('02','03','04') and bussno like '8621%'"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+"), "
					+" (select count(1) from bpomissionstate where dealtype in ('02','03','04') and bussno like '8616%'"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+")"
					+" from dual";
				SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
				sqlbv12.sql(strListSQL2);
				sqlbv12.put("mRiskCode", mRiskCode);
				sqlbv12.put("tManageCom", tManageCom);
				sqlbv12.put("mGlobalInput", this.mGlobalInput.ComCode);
				sqlbv12.put("mBPOID", mBPOID);
				sqlbv12.put("mStartDate", mStartDate);
				sqlbv12.put("mEndDate", mEndDate);
		String strListSQL3="select "
					+" (select count(1) from bpomissionstate where dealtype ='01' and bussno like '8611%'"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+"), "
					+" (select count(1) from bpomissionstate where dealtype ='01' and (bussno like '8615%' or bussno like '8625%' or bussno like '8635%')"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+"), "
					+" (select count(1) from bpomissionstate where dealtype ='01' and bussno like '8621%'"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+"), "
					+" (select count(1) from bpomissionstate where dealtype ='01' and bussno like '8616%'"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+") "
					+" from dual";
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(strListSQL3);
		sqlbv13.put("mRiskCode", mRiskCode);
		sqlbv13.put("tManageCom", tManageCom);
		sqlbv13.put("mGlobalInput", this.mGlobalInput.ComCode);
		sqlbv13.put("mBPOID", mBPOID);
		sqlbv13.put("mStartDate", mStartDate);
		sqlbv13.put("mEndDate", mEndDate);
		String strListSQL4="select "
					+" (select count(1) from bpomissionstate where dealtype ='00' and bussno like '8611%'"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+"), "
					+" (select count(1) from bpomissionstate where dealtype ='00' and (bussno like '8615%' or bussno like '8625%' or bussno like '8635%')"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+"), "
					+" (select count(1) from bpomissionstate where dealtype ='00' and bussno like '8621%'"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+"), "
					+" (select count(1) from bpomissionstate where dealtype ='00' and bussno like '8616%'"
					+" and bussnotype ='TB'"
					+ReportPubFun.getWherePart("bpoid", ReportPubFun.getParameterStr(mBPOID, "?mBPOID?"))
					+ReportPubFun.getWherePart("riskcode", ReportPubFun.getParameterStr(mRiskCode, "?mRiskCode?"))
					+ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
					+" and bussno in (select prtno from lcpol where 1=1"
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom, "?tManageCom?"))
					+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode, "?mGlobalInput?"))
					+" )"
					+") "
					+" from dual";
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(strListSQL4);
		sqlbv14.put("mRiskCode", mRiskCode);
		sqlbv14.put("tManageCom", tManageCom);
		sqlbv14.put("mGlobalInput", this.mGlobalInput.ComCode);
		sqlbv14.put("mBPOID", mBPOID);
		sqlbv14.put("mStartDate", mStartDate);
		sqlbv14.put("mEndDate", mEndDate);
				logger.debug("tSQL1 :"+strListSQL1);
				logger.debug("tSQL2 :"+strListSQL2);
				logger.debug("tSQL3 :"+strListSQL3);
				logger.debug("tSQL4 :"+strListSQL4);
				
				ExeSQL ListExeSQL = new ExeSQL();
				SSRS ListSSRS1 = new SSRS();
				SSRS ListSSRS11 = new SSRS();
				SSRS ListSSRS2 = new SSRS();
				SSRS ListSSRS3 = new SSRS();
				SSRS ListSSRS4 = new SSRS();
				ListSSRS1 = ListExeSQL.execSQL(sqlbv10);
				ListSSRS11 = ListExeSQL.execSQL(sqlbv11);
				ListSSRS2 = ListExeSQL.execSQL(sqlbv12);
				ListSSRS3 = ListExeSQL.execSQL(sqlbv13);
				ListSSRS4 = ListExeSQL.execSQL(sqlbv14);
				//String tManageCom =ListExeSQL.getOneValue("select managecom from bpoallotrate where allottype ='TB' and bpoid='"+tBPOID+"'");
				SQLwithBindVariables tBPONamesqlbv = new SQLwithBindVariables();
				tBPONamesqlbv.sql("select remark from  bpoallotrate where allottype='TB' and bpoid ='"+"?tBPOID?"+"'");
				tBPONamesqlbv.put("tBPOID", tBPOID);
				String tBPOName =ListExeSQL.getOneValue(tBPONamesqlbv);
				int ListCount = ListSSRS1.getMaxRow()+
								ListSSRS2.getMaxRow()+
								ListSSRS3.getMaxRow()+
								ListSSRS4.getMaxRow()+
								ListSSRS11.getMaxRow();
				dataCount=dataCount+ListCount;
				int tSumOperatorCount=0;
				for(int l=1;l<=ListSSRS1.getMaxCol();l++){
					tSumOperatorCount=tSumOperatorCount+Integer.parseInt(ListSSRS1.GetText(1, l));
				}
				for(int l=1;l<=ListSSRS2.getMaxCol();l++){
					tSumOperatorCount=tSumOperatorCount+Integer.parseInt(ListSSRS2.GetText(1, l));
				}
				for(int l=1;l<=ListSSRS3.getMaxCol();l++){
					tSumOperatorCount=tSumOperatorCount+Integer.parseInt(ListSSRS3.GetText(1, l));
				}
				for(int l=1;l<=ListSSRS4.getMaxCol();l++){
					tSumOperatorCount=tSumOperatorCount+Integer.parseInt(ListSSRS4.GetText(1, l));
				}
//				for(int l=1;l<=ListSSRS11.getMaxCol();l++){
//					tSumOperatorCount=tSumOperatorCount+Integer.parseInt(ListSSRS11.GetText(1, l));
//				}
//				String tSumOperatorCount =ComExeSQL.getOneValue("select count(1) from lwmission where"
//						+" activityid in ('0000001090','0000001091','0000001002') and defaultoperator='"+tOperator+"' "
//						+" and substr(missionprop1,0,4) in ('8611','8621','8616','8615','8625','8635') "
//						+ReportPubFun.getWherePart("Makedate", mStartDate, mEndDate, 1));
				//for (int j = 1; j <= ListSSRS.MaxRow; j++) {
					String[] cols = new String[20];
					cols[0] = c + "";
					cols[1] = tBPOName;
					cols[2] = tManageCom;
					cols[3] = String.valueOf(tSumOperatorCount);
					if(ListSSRS1.getMaxRow()>0){
						cols[4] = String.valueOf(Integer.parseInt(ListSSRS1.GetText(1, 1))+Integer.parseInt(ListSSRS11.GetText(1, 1)));
						cols[5] = String.valueOf(Integer.parseInt(ListSSRS1.GetText(1, 2))+Integer.parseInt(ListSSRS11.GetText(1, 2)));
						cols[6] = String.valueOf(Integer.parseInt(ListSSRS1.GetText(1, 3))+Integer.parseInt(ListSSRS11.GetText(1, 3)));
						cols[7] = String.valueOf(Integer.parseInt(ListSSRS1.GetText(1, 4))+Integer.parseInt(ListSSRS11.GetText(1, 4)));
						YC_11=YC_11+Integer.parseInt(cols[4]);
						YC_21=YC_21+Integer.parseInt(cols[5]);
						YC_35=YC_35+Integer.parseInt(cols[6]);
						YC_16=YC_16+Integer.parseInt(cols[7]);
					}
					if(ListSSRS2.getMaxRow()>0){
						cols[8] = ListSSRS2.GetText(1, 1);
						cols[9] = ListSSRS2.GetText(1, 2);
						cols[10] = ListSSRS2.GetText(1, 3);
						cols[11] = ListSSRS2.GetText(1, 4);
						CJ_11=CJ_11+Integer.parseInt(ListSSRS2.GetText(1, 1));
						CJ_21=CJ_21+Integer.parseInt(ListSSRS2.GetText(1, 2));
						CJ_35=CJ_35+Integer.parseInt(ListSSRS2.GetText(1, 3));
						CJ_16=CJ_16+Integer.parseInt(ListSSRS2.GetText(1, 4));
					}
					if(ListSSRS3.getMaxRow()>0){
						cols[12] = ListSSRS3.GetText(1, 1);
						cols[13] = ListSSRS3.GetText(1, 2);
						cols[14] = ListSSRS3.GetText(1, 3);
						cols[15] = ListSSRS3.GetText(1, 4);
						WT_11=WT_11+Integer.parseInt(ListSSRS3.GetText(1, 1));
						WT_21=WT_21+Integer.parseInt(ListSSRS3.GetText(1, 2));
						WT_35=WT_35+Integer.parseInt(ListSSRS3.GetText(1, 3));
						WT_16=WT_16+Integer.parseInt(ListSSRS3.GetText(1, 4));
					}
					if(ListSSRS4.getMaxRow()>0){
						cols[16] = ListSSRS4.GetText(1, 1);
						cols[17] = ListSSRS4.GetText(1, 2);
						cols[18] = ListSSRS4.GetText(1, 3);
						cols[19] = ListSSRS4.GetText(1, 4);
						QJ_11=QJ_11+Integer.parseInt(ListSSRS4.GetText(1, 1));
						QJ_21=QJ_21+Integer.parseInt(ListSSRS4.GetText(1, 2));
						QJ_35=QJ_35+Integer.parseInt(ListSSRS4.GetText(1, 3));
						QJ_16=QJ_16+Integer.parseInt(ListSSRS4.GetText(1, 4));
					}
					aListTable.add(cols);
					all =all+tSumOperatorCount;
				//}
			}
		}
			String[] coll =new String [20];
			coll[0]="";
			coll[1]="";
			coll[2]="合计:";
			coll[3]=String.valueOf(all);
			coll[4]=String.valueOf(YC_11);
			coll[5]=String.valueOf(YC_21);
			coll[6]=String.valueOf(YC_35);
			coll[7]=String.valueOf(YC_16);
			coll[8]=String.valueOf(CJ_11);
			coll[9]=String.valueOf(CJ_21);
			coll[10]=String.valueOf(CJ_35);
			coll[11]=String.valueOf(CJ_16);
			coll[12]=String.valueOf(WT_11);
			coll[13]=String.valueOf(WT_21);
			coll[14]=String.valueOf(WT_35);
			coll[15]=String.valueOf(WT_16);
			coll[16]=String.valueOf(QJ_11);
			coll[17]=String.valueOf(QJ_21);
			coll[18]=String.valueOf(QJ_35);
			coll[19]=String.valueOf(QJ_16);
			aListTable.add(coll);
			
			String[] col = new String[20];
			xmlexport.addListTable(aListTable, col);
			aTextTag.add("StatiStartDate", StrTool.getChnDate(mStartDate));
			aTextTag.add("StatiEndDate", StrTool.getChnDate(mEndDate));
			aTextTag.add("PrintDate", PubFun.getCurrentDate());
			
			xmlexport.addTextTag(aTextTag);
			
			mResult.addElement(xmlexport);
			if (dataCount == 0) {
				buildError("getInputData", "没有可以打印的数据");
				return false;
			}
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpNoticeListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		// GlobalInput tGI = new GlobalInput();
		// tGI.ComCode="86";
		// tGI.ManageCom="86";
		// tGI.Operator="001";
		//
		// //传递非SCHEMA信息
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("StartDate", "2006-12-01");
		// tTransferData.setNameAndValue("EndDate", "2007-01-04");
		// tTransferData.setNameAndValue("ManageCom", "8621");
		// tTransferData.setNameAndValue("PrintDate", "2007-01-04");
		//
		// VData tVData = new VData();
		// tVData.addElement(tGI);
		// tVData.addElement(tTransferData);
		// GrpNoticeListBL tGrpNoticeListUI = new GrpNoticeListBL();
		// tGrpNoticeListUI.submitData(tVData,"PRINT");

	}
}
