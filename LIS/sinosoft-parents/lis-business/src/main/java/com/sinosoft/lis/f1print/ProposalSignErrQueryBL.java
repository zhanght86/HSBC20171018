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

public class ProposalSignErrQueryBL {
private static Logger logger = Logger.getLogger(ProposalSignErrQueryBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	// private GlobalInput mGI = new GlobalInput();//操作员信息
	private TransferData mTransferData = new TransferData(); // 传递非SCHEMA信息

	private String mManageCom; // 管理机构
	private String mStartDate; // 开始日期
	private String mEndDate; // 结束
	private String mScanStartDate; // 开始日期
	private String mScanEndDate; // 结束
	private String mSignStartDate; // 开始日期
	private String mSignEndDate; // 结束
	private String mPrintDate; 
	private String mSaleChnl; 
	private String mPrtNo; 
	private String mInsuredName; 
	private String mAgentCode; 
	private String mRiskCode; 

	private String mOperator; // 操作员

	private String mCurrentDate = PubFun.getCurrentDate(); // 当前日期

	private GlobalInput mGlobalInput = new GlobalInput();

	public ProposalSignErrQueryBL() {
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
		mScanStartDate = (String) mTransferData.getValueByName("ScanStartDate"); // 起始日期
		mScanEndDate = (String) mTransferData.getValueByName("ScanEndDate"); // 终止日期
		mSignStartDate = (String) mTransferData.getValueByName("SignStartDate"); // 起始日期
		mSignEndDate = (String) mTransferData.getValueByName("SignEndDate"); // 终止日期
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl"); 
		//mPrtNo = (String) mTransferData.getValueByName("PrtNo"); 
		mRiskCode = (String) mTransferData.getValueByName("RiskCode"); 
		mAgentCode = (String) mTransferData.getValueByName("AgentCode"); 
		mInsuredName = (String) mTransferData.getValueByName("InsuredName"); 
		//mPrintDate = (String) mTransferData.getValueByName("PrintDate"); // 打印日期

		if (mManageCom == null || mManageCom.trim().equals("")) {
			buildError("getInputData", "传入的管理机构信息为空。");
			return false;
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
		XmlExport xmlexport = new XmlExport();
		TextTag aTextTag = new TextTag();
		ListTable aListTable = new ListTable();
		aListTable.setName("LIST");

			String strListSQL = "";
			if((this.mScanStartDate!=null&&!this.mScanStartDate.equals(""))
					||(this.mScanEndDate!=null&&!this.mScanEndDate.equals("")))
			{
				strListSQL = " select  b.PrtNo ,'',"
						   + "  b.appntname , b.insuredname , "
						   + " (case when (select sum(amnt) from lcpol where contno=b.contno and mainpolno=polno and uwflag in ('4','9') and appflag='0') is not null then (select sum(amnt) from lcpol where contno=b.contno and mainpolno=polno and uwflag in ('4','9') and appflag='0')  else 0 end), "
						   + " (case when (select sum(prem) from lcpol where contno=b.contno and uwflag in ('4','9') and appflag='0') is not null then (select sum(prem) from lcpol where contno=b.contno and uwflag in ('4','9') and appflag='0')  else 0 end), "
			    //+ " b.amnt, b.prem, "
						   + " (select Name from LAAgent where agentcode = b.agentcode),"
						   + " a.signno, a.MakeDate ,replace(a.reason,b.prtno,'') , b.managecom "
						   + " from lcindappsignlog a,lccont b,es_doc_main c "
						   + " where a.prtno = b.prtno and b.prtno = c.doccode and "
						   + " b.appflag='0' "
						   + " and b.uwflag in ('4','9') "
						   + " and a.errtype='01' "
						   + " and c.busstype='TB' "
						   + " and c.subtype='UA001' "
						   //+ " and a.makedate = (select max(makedate) from lcindappsignlog where prtno =a.prtno)"
			    		  //+ReportPubFun.getWherePart("b.PrtNo", mPrtNo)
						   +ReportPubFun.getWherePart("b.InsuredName", ReportPubFun.getParameterStr(mInsuredName,"?mInsuredName?"))
			    	//	  +ReportPubFun.getWherePart("d.RiskCode", mRiskCode)
						   +ReportPubFun.getWherePart("b.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
						   +ReportPubFun.getWherePart("b.AgentCode", ReportPubFun.getParameterStr(mAgentCode,"?mAgentCode?"))
						   +ReportPubFun.getWherePart("c.Makedate", ReportPubFun.getParameterStr(mScanStartDate,"?mScanStartDate?"), ReportPubFun.getParameterStr(mScanEndDate,"?mScanEndDate?"), 1)
						   +ReportPubFun.getWherePart("a.Makedate", ReportPubFun.getParameterStr(mSignStartDate,"?mSignStartDate?"), ReportPubFun.getParameterStr(mSignEndDate,"?mSignEndDate?"), 1)
						   +ReportPubFun.getWherePartLike("b.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
						   +ReportPubFun.getWherePartLike("b.ManageCom", ReportPubFun.getParameterStr(mGlobalInput.ManageCom,"?mGlobalInput?"))
			    		  ;
						if(mRiskCode!=null&&!mRiskCode.equals(""))
						{
							strListSQL = strListSQL + " and exists (select '1' from lcpol where riskcode='"+"?mRiskCode?"+"' "
										+ " and contno=b.contno ) ";
						}
						
						strListSQL = strListSQL + " order by c.makedate,b.prtno";
			}
			else
			{
				 strListSQL =" select b.PrtNo ,'', "
				       + " b.appntname ,b.insuredname , "
				       + " (case when (select sum(amnt) from lcpol where contno=b.contno and mainpolno=polno and uwflag in ('4','9') and appflag='0') is not null then (select sum(amnt) from lcpol where contno=b.contno and mainpolno=polno and uwflag in ('4','9') and appflag='0')  else 0 end), "
				       + " (case when (select sum(prem) from lcpol where contno=b.contno and uwflag in ('4','9') and appflag='0') is not null then (select sum(prem) from lcpol where contno=b.contno and uwflag in ('4','9') and appflag='0')  else 0 end), "
				       //+ " b.amnt, b.prem, "
				       + " (select Name from LAAgent where agentcode = b.agentcode), "
				       + " a.signno, a.MakeDate MakeDate,replace(a.reason,b.prtno,'') errinfo,b.managecom "
				       + " from LCIndAppSignLog a, lccont b "
				       + "  where a.prtno = b.prtno "
				       + " and b.appflag = '0'  "
				       + " and b.uwflag in ('4','9') "
				       + " and a.errtype='01' "
				       //+ "  and a.makedate = (select max(makedate) from lcindappsignlog where prtno =a.prtno)"
					   +ReportPubFun.getWherePart("b.InsuredName", ReportPubFun.getParameterStr(mInsuredName,"?mInsuredName?"))
					   +ReportPubFun.getWherePart("b.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
					   +ReportPubFun.getWherePart("b.AgentCode", ReportPubFun.getParameterStr(mAgentCode,"?mAgentCode?"))
					   +ReportPubFun.getWherePart("b.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					   +ReportPubFun.getWherePart("a.Makedate", ReportPubFun.getParameterStr(mSignStartDate,"?mSignStartDate?"), ReportPubFun.getParameterStr(mSignEndDate,"?mSignEndDate?"), 1)
					   +ReportPubFun.getWherePartLike("b.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"));
				 if(mRiskCode!=null&&!mRiskCode.equals(""))
				 {
					 	strListSQL = strListSQL + " and exists (select '1' from lcpol where riskcode='"+"?mRiskCode?"+"' "
					 				+ " and contno=b.contno ) ";
				 }
				 strListSQL = strListSQL + " order by b.makedate,b.prtno ";

			}
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strListSQL);
			sqlbv.put("mInsuredName", mInsuredName);
			sqlbv.put("mSaleChnl", mSaleChnl);
			sqlbv.put("mAgentCode", mAgentCode);
			sqlbv.put("mScanStartDate", mScanStartDate);
			sqlbv.put("mScanEndDate", mScanEndDate);
			sqlbv.put("mSignStartDate", mSignStartDate);
			sqlbv.put("mSignEndDate", mSignEndDate);
			sqlbv.put("mManageCom", this.mManageCom);
			sqlbv.put("mGlobalInput", mGlobalInput.ManageCom);
			sqlbv.put("mStartDate", mStartDate);
			sqlbv.put("mEndDate", mEndDate);
			sqlbv.put("mRiskCode", mRiskCode);
			
			logger.debug("strListSQL:"+strListSQL);
			ExeSQL ListExeSQL = new ExeSQL();
			SSRS ListSSRS = new SSRS();
			ListSSRS = ListExeSQL.execSQL(sqlbv);
			int ListCount = ListSSRS.getMaxRow();
			String[] cols = new String[12];
			for (int j = 1; j <= ListSSRS.MaxRow; j++) {
				cols[0] = j + "";
				cols[1] = ListSSRS.GetText(j, 1);
				cols[2] = ListSSRS.GetText(j, 3);
				cols[3] = ListSSRS.GetText(j, 4);
				cols[4] = ListSSRS.GetText(j, 5);
				cols[5] = ListSSRS.GetText(j, 6);
				cols[6] = ListSSRS.GetText(j, 7);
				cols[7] = ListSSRS.GetText(j, 8);
				cols[8] = ListSSRS.GetText(j, 9);
				cols[9] = ListSSRS.GetText(j, 10);
				cols[10] = ListSSRS.GetText(j, 11);
				//cols[11] = ListSSRS.GetText(j, 11);
				aListTable.add(cols);
				cols = new String[11];
			}
		//String[] col = new String[10];
		aTextTag.add("StartDate", StrTool.getChnDate(mStartDate));
		aTextTag.add("EndDate", StrTool.getChnDate(mEndDate));
		aTextTag.add("ScanStartDate", StrTool.getChnDate(mScanStartDate));
		aTextTag.add("ScanEndDate", StrTool.getChnDate(mScanEndDate));
		aTextTag.add("SignStartDate", StrTool.getChnDate(mSignStartDate));
		aTextTag.add("SignEndDate", StrTool.getChnDate(mSignEndDate));

		aTextTag.add("PrintDate", PubFun.getCurrentDate());
		//aTextTag.add("FilialeComName", FilialeComName);
		//aTextTag.add("BranchComName", BranchComName);
		xmlexport.createDocument("ProposalSignErrQuery.vts", "printer");
		xmlexport.addListTable(aListTable, cols);

		xmlexport.addTextTag(aTextTag);

		mResult.addElement(xmlexport);
//		if (dataCount == 0) {
//			buildError("getInputData", "没有可以打印的数据");
//			return false;
//		}
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
