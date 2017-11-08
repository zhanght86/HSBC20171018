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

public class ProposalNoRespQueryBL {
private static Logger logger = Logger.getLogger(ProposalNoRespQueryBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	// private GlobalInput mGI = new GlobalInput();//操作员信息
	private TransferData mTransferData = new TransferData(); // 传递非SCHEMA信息

	private String mManageCom; // 管理机构
	private String mStartDate; // 开始日期
	private String mEndDate; // 结束
	private String mPrintDate; // 打印日期
	private String mSaleChnl; // 打印日期
	private String mPrtNo; // 打印日期
	private String mInsuredName; // 打印日期
	private String mAgentCode; // 打印日期
	private String mRiskCode; // 打印日期

	private String mOperator; // 操作员

	private String mScanStartDate; // 开始日期
	private String mScanEndDate; // 结束
	private String mIssueStartDate; // 开始日期
	private String mIssueEndDate; // 结束
	private String mOperatePos; // 操作位置
	
	private String mIssueNoticeType;
	private String mCurrentDate = PubFun.getCurrentDate(); // 当前日期

	private GlobalInput mGlobalInput = new GlobalInput();

	public ProposalNoRespQueryBL() {
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
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl"); 
		//mPrtNo = (String) mTransferData.getValueByName("PrtNo"); 
		mRiskCode = (String) mTransferData.getValueByName("RiskCode"); 
		mAgentCode = (String) mTransferData.getValueByName("AgentCode"); 
		mInsuredName = (String) mTransferData.getValueByName("InsuredName"); 
		//mPrintDate = (String) mTransferData.getValueByName("PrintDate"); // 打印日期

		mScanStartDate = (String) mTransferData.getValueByName("ScanStartDate"); 
		mScanEndDate = (String) mTransferData.getValueByName("ScanEndDate"); 
		mIssueStartDate = (String) mTransferData.getValueByName("IssueStartDate"); 
		mIssueEndDate = (String) mTransferData.getValueByName("IssueEndDate");
		mIssueNoticeType = (String) mTransferData.getValueByName("IssueNoticeType");
		mOperatePos  = (String) mTransferData.getValueByName("OperatePos");
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
		
		if (mIssueStartDate == null || mIssueStartDate.trim().equals("")) {
			buildError("getInputData", "传入的问题件下发开始时间为空。");
			return false;
		}
		if (mIssueEndDate == null || mIssueEndDate.trim().equals("")) {
			buildError("getInputData", "传入的问题件下发结束时间为空。");
			return false;
		}
		
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
		xmlexport.createDocument("ProposalNoRespQuery.vts", "printer");
		TextTag aTextTag = new TextTag();
		ListTable aListTable = new ListTable();
		aListTable.setName("LIST");
		
			String strListSQL = "select X.a, X.b, X.c, X.d, X.e, X.f, X.g,(select codename from ldcode where codetype = 'operatepos' and code = X.o), X.m, X.h, X.i, X.x, X.j, X.l, Y.codename, X.y from ( "
				              + " select a.managecom a,a.prtno b,a.proposalcontno c,a.appntname d,a.insuredname e, "
				              + " (case when (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ ) is not null then (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ )  else 0 end) f, "
				              + " (case when (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/) is not null then (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/)  else 0 end) g, c.OperatePos o, "
				              + " d.agentcode m,(select name from laagent where agentcode=a.agentcode) h,a.salechnl i, a.firsttrialdate x, concat(concat(to_char(b.makedate,'yyyy-mm-dd') , ' ') , b.maketime) j, concat(concat(to_char(c.senddate,'yyyy-mm-dd') , ' ') , c.sendtime) l, '01' k, (to_date(substr(now(), 1, 10), 'YYYY-MM-DD') - c.senddate) y " 
				              + " from lccont a,es_doc_main b,lcissuepol c ,lcpol d" 
				              + " where a.prtno=b.doccode "
				              + " and a.contno =d.contno "
				              + " and c.backobjtype != '1' "
				              + " and b.busstype='TB' "
				              + " and b.subtype='UA001' "
				              + " and a.appflag='0' "
				              + " and a.uwflag not in ('1','2','a') "
				              + " and c.needprint = 'Y' "
				              + " and a.conttype='1' " 
				              + " and a.contno=c.contno "
				              + " and c.standbyflag2='Y' and c.backobjtype='2' "
				              + " and c.state is not null "
				              + " and (c.replyresult is null and c.replydate is null) "
				              + ReportPubFun.getWherePart("a.InsuredName", ReportPubFun.getParameterStr(mInsuredName,"?mInsuredName?"))
				              + ReportPubFun.getWherePart("a.Agentcode", ReportPubFun.getParameterStr(mAgentCode,"?mAgentCode?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode,"?mGlobalInput?"))
							  + ReportPubFun.getWherePart("a.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
							  + ReportPubFun.getWherePart("d.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
							  + ReportPubFun.getWherePart("b.Makedate", ReportPubFun.getParameterStr(mScanStartDate,"?mScanStartDate?"), ReportPubFun.getParameterStr(mScanEndDate,"?mScanEndDate?"), 1)
				              + ReportPubFun.getWherePart("c.Senddate", ReportPubFun.getParameterStr(mIssueStartDate,"?mIssueStartDate?"), ReportPubFun.getParameterStr(mIssueEndDate,"?mIssueEndDate?"), 1)
							  + ReportPubFun.getWherePart("01", ReportPubFun.getParameterStr(this.mIssueNoticeType,"?mIssueNoticeType?"))
				              + ReportPubFun.getWherePart("d.riskcode",ReportPubFun.getParameterStr(mRiskCode,"?mRiskCode?"))
				              + ReportPubFun.getWherePart("c.OperatePos",ReportPubFun.getParameterStr(mOperatePos,"?mOperatePos?"))	
				              + " union "
				              + " select a.managecom a,a.prtno b,a.proposalcontno c,a.appntname d,a.insuredname e, "
				              + " (case when (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ ) is not null then (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ )  else 0 end) f, "
				              + " (case when (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/) is not null then (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/)  else 0 end) g, c.OperatePos o, "
				              + " d.agentcode m,(select name from laagent where agentcode=a.agentcode) h,a.salechnl i, a.firsttrialdate x, concat(concat(to_char(b.makedate,'yyyy-mm-dd') , ' ') , b.maketime) j, concat(concat(to_char(c.senddate,'yyyy-mm-dd') , ' ') , c.sendtime) l, '02' k, (to_date(substr(now(), 1, 10), 'YYYY-MM-DD') - c.senddate) y " 
				              + " from lccont a,es_doc_main b,lcissuepol c,lcpol d " 
				              + " where a.prtno=b.doccode "
				              + " and a.contno=d.contno"
				              + " and c.backobjtype != '1' "
				              + " and b.busstype='TB' "
				              + " and b.subtype='UA001' "
				              + " and a.appflag='0' "
				              + " and a.uwflag not in ('1','2','a') "
				              + " and c.needprint = 'Y' "
				              + " and a.conttype='1' " 
				              + " and a.contno=c.contno "
				              + " and c.standbyflag2='N' and c.backobjtype='2' "
				              + " and c.state is not null "
				              + " and (c.replyresult is null and c.replydate is null) "
				              + ReportPubFun.getWherePart("a.InsuredName", ReportPubFun.getParameterStr(mInsuredName,"?mInsuredName?"))
				              + ReportPubFun.getWherePart("a.Agentcode", ReportPubFun.getParameterStr(mAgentCode,"?mAgentCode?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode,"?mGlobalInput?"))
							  + ReportPubFun.getWherePart("a.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
							  + ReportPubFun.getWherePart("d.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
							  + ReportPubFun.getWherePart("b.Makedate", ReportPubFun.getParameterStr(mScanStartDate,"?mScanStartDate?"), ReportPubFun.getParameterStr(mScanEndDate,"?mScanEndDate?"), 1)
				              + ReportPubFun.getWherePart("c.Senddate", ReportPubFun.getParameterStr(mIssueStartDate,"?mIssueStartDate?"), ReportPubFun.getParameterStr(mIssueEndDate,"?mIssueEndDate?"), 1)
							  + ReportPubFun.getWherePart("02", ReportPubFun.getParameterStr(this.mIssueNoticeType,"?mIssueNoticeType?"))
				              + ReportPubFun.getWherePart("d.riskcode",ReportPubFun.getParameterStr(mRiskCode,"?mRiskCode?"))
				              + ReportPubFun.getWherePart("c.OperatePos",ReportPubFun.getParameterStr(mOperatePos,"?mOperatePos?"))	
				              + " union "
				              + " select a.managecom a,a.prtno b,a.proposalcontno c,a.appntname d,a.insuredname e, "
				              + " (case when (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ ) is not null then (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ )  else 0 end) f, "
				              + " (case when (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/) is not null then (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/)  else 0 end) g, c.OperatePos o, "
				              + " d.agentcode m,(select name from laagent where agentcode=a.agentcode) h,a.salechnl i, a.firsttrialdate x,concat(concat(to_char(b.makedate,'yyyy-mm-dd') , ' ') , b.maketime) j, concat(concat(to_char(c.senddate,'yyyy-mm-dd') , ' ') , c.sendtime) l, '03' k, (to_date(substr(now(), 1, 10), 'YYYY-MM-DD') - c.senddate) y " 
				              + " from lccont a,es_doc_main b,lcissuepol c ,lcpol d" 
				              + " where a.prtno=b.doccode "
				              + " and a.contno=d.contno "
				              + " and c.backobjtype != '1' "
				              + " and b.busstype='TB' "
				              + " and b.subtype='UA001' "
				              + " and a.appflag='0' "
				              + " and a.uwflag not in ('1','2','a') "
				              + " and c.needprint = 'Y' "
				              + " and a.conttype='1' " 
				              + " and a.contno=c.contno "
				              + " and c.backobjtype='3' "
				              + " and ((c.operatepos = '6' and c.state is not null "
							  + " ) or c.operatepos <> '6') "
				              + " and (c.replyresult is null and c.replydate is null) "
				              + ReportPubFun.getWherePart("a.InsuredName", ReportPubFun.getParameterStr(mInsuredName,"?mInsuredName?"))
				              + ReportPubFun.getWherePart("a.Agentcode", ReportPubFun.getParameterStr(mAgentCode,"?mAgentCode?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode,"?mGlobalInput?"))
							  + ReportPubFun.getWherePart("a.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
							  + ReportPubFun.getWherePart("d.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
							  + ReportPubFun.getWherePart("b.Makedate", ReportPubFun.getParameterStr(mScanStartDate,"?mScanStartDate?"), ReportPubFun.getParameterStr(mScanEndDate,"?mScanEndDate?"), 1)
				              + ReportPubFun.getWherePart("c.Senddate", ReportPubFun.getParameterStr(mIssueStartDate,"?mIssueStartDate?"), ReportPubFun.getParameterStr(mIssueEndDate,"?mIssueEndDate?"), 1)
							  + ReportPubFun.getWherePart("03", ReportPubFun.getParameterStr(this.mIssueNoticeType,"?mIssueNoticeType?"))
				              + ReportPubFun.getWherePart("d.riskcode",ReportPubFun.getParameterStr(mRiskCode,"?mRiskCode?"))
				              + ReportPubFun.getWherePart("c.OperatePos",ReportPubFun.getParameterStr(mOperatePos,"?mOperatePos?"))	
				              + " union "
				              + " select a.managecom a,a.prtno b,a.proposalcontno c,a.appntname d,a.insuredname e, "
				              + " (case when (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ ) is not null then (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ )  else 0 end) f, "
				              + " (case when (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/) is not null then (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/)  else 0 end) g, c.OperatePos o, "
				              + " d.agentcode m,(select name from laagent where agentcode=a.agentcode) h,a.salechnl i, a.firsttrialdate x, concat(concat(to_char(b.makedate,'yyyy-mm-dd') , ' ') , b.maketime) j, concat(concat(to_char(c.senddate,'yyyy-mm-dd') , ' ') , c.sendtime) l, '04' k, (to_date(substr(now(), 1, 10), 'YYYY-MM-DD') - c.senddate) y " 
				              + " from lccont a,es_doc_main b,lcissuepol c ,lcpol d" 
				              + " where a.prtno=b.doccode "
				              + " and a.contno=d.contno "
				              + " and c.backobjtype != '1' "
				              + " and b.busstype='TB' "
				              + " and b.subtype='UA001' "
				              + " and a.appflag='0' "
				              + " and a.uwflag not in ('1','2','a') "
				              + " and c.needprint = 'Y' "
				              + " and a.conttype='1' " 
				              + " and a.contno=c.contno "
				              + " and c.backobjtype='4' "
				              + " and ((c.operatepos = '6' and c.state is not null "
							  + " ) or c.operatepos <> '6') "
				              + " and (c.replyresult is null and c.replydate is null) "
				              + ReportPubFun.getWherePart("a.InsuredName", ReportPubFun.getParameterStr(mInsuredName,"?mInsuredName?"))
				              + ReportPubFun.getWherePart("a.Agentcode", ReportPubFun.getParameterStr(mAgentCode,"?mAgentCode?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode,"?mGlobalInput?"))
							  + ReportPubFun.getWherePart("a.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
							  + ReportPubFun.getWherePart("d.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
							  + ReportPubFun.getWherePart("b.Makedate", ReportPubFun.getParameterStr(mScanStartDate,"?mScanStartDate?"), ReportPubFun.getParameterStr(mScanEndDate,"?mScanEndDate?"), 1)
				              + ReportPubFun.getWherePart("c.Senddate", ReportPubFun.getParameterStr(mIssueStartDate,"?mIssueStartDate?"), ReportPubFun.getParameterStr(mIssueEndDate,"?mIssueEndDate?"), 1)
							  + ReportPubFun.getWherePart("04", ReportPubFun.getParameterStr(this.mIssueNoticeType,"?mIssueNoticeType?"))
				              + ReportPubFun.getWherePart("d.riskcode",ReportPubFun.getParameterStr(mRiskCode,"?mRiskCode?"))
				              + ReportPubFun.getWherePart("c.OperatePos",ReportPubFun.getParameterStr(mOperatePos,"?mOperatePos?"))	
				              //排除 05 -返回问题件修改岗 hanbin -2010-05-24
//				              + " union "
//				              + " select a.managecom a,a.prtno b,a.proposalcontno c,a.appntname d,a.insuredname e, "
//				              + " nvl((select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ ),0) f,"
//				              + " nvl((select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/),0) g,c.OperatePos o, "
//				              + " d.agentcode m,(select name from laagent where agentcode=a.agentcode) h,a.salechnl i, a.firsttrialdate x, to_char(b.makedate,'yyyy-mm-dd') || ' ' || b.maketime j, to_char(c.senddate,'yyyy-mm-dd') || ' ' || c.sendtime l, '05' k, (to_date(substr(sysdate, 1, 10), 'YYYY-MM-DD') - c.senddate) y " 
//				              + " from lccont a,es_doc_main b,lcissuepol c,lcpol d " 
//				              + " where a.prtno=b.doccode "
//				              + " and a.contno =d.contno "
//				              + " and b.busstype='TB' "
//				              + " and b.subtype='UA001' "
//				              + " and a.appflag='0' "
//				              + " and a.uwflag not in ('1','2','a') "
//				              + " and a.conttype='1' " 
//				              + " and a.contno=c.contno "
//				              + " and c.standbyflag2='Y' and c.backobjtype='5' "
//				              + " and ((c.operatepos = '6' and c.state is not null "
//							  + " ) or c.operatepos <> '6') "
//				              + " and (c.replyresult is null and c.replydate is null) "
//				              + ReportPubFun.getWherePart("a.InsuredName", mInsuredName)
//				              + ReportPubFun.getWherePart("a.Agentcode", mAgentCode)
//				              + ReportPubFun.getWherePartLike("a.ManageCom", this.mManageCom)
//				              + ReportPubFun.getWherePartLike("a.ManageCom", this.mGlobalInput.ComCode)
//							  + ReportPubFun.getWherePart("a.SaleChnl", mSaleChnl)
//							  + ReportPubFun.getWherePart("d.Makedate", mStartDate, mEndDate, 1)
//							  + ReportPubFun.getWherePart("b.Makedate", mScanStartDate, mScanEndDate, 1)
//				              + ReportPubFun.getWherePart("c.Senddate", mIssueStartDate, mIssueEndDate, 1)
//							  + ReportPubFun.getWherePart("05", this.mIssueNoticeType)
//				              + ReportPubFun.getWherePart("d.riskcode",mRiskCode)
				              + " union "
				              + " select a.managecom a,a.prtno b,a.proposalcontno c,a.appntname d,a.insuredname e, "
				              + " (case when (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ ) is not null then (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ )  else 0 end) f, "
				              + " (case when (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/) is not null then (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/)  else 0 end) g, '6' o, "
				              + " d.agentcode m,(select name from laagent where agentcode=a.agentcode) h,a.salechnl i, a.firsttrialdate x,concat(concat(to_char(b.makedate,'yyyy-mm-dd') , ' ') , b.maketime) j, concat(concat(to_char(c.senddate,'yyyy-mm-dd') , ' ') , c.sendtime) l, '06' k, (to_date(substr(now(), 1, 10), 'YYYY-MM-DD') - c.senddate) y " 
				              + " from lccont a,es_doc_main b,lcrreport c ,lcpol d" 
				              + " where a.prtno=b.doccode "
				              + " and a.contno=d.contno"
				              + " and b.busstype='TB' "
				              + " and b.subtype='UA001' "
				              + " and a.appflag='0' "
				              + " and a.uwflag not in ('1','2','a') "
				              + " and a.conttype='1' " 
				              + " and a.contno=c.contno "
				              + " and c.replyflag is not null and c.replycontente is null "
				              + ReportPubFun.getWherePart("a.InsuredName", ReportPubFun.getParameterStr(mInsuredName,"?mInsuredName?"))
				              + ReportPubFun.getWherePart("a.Agentcode", ReportPubFun.getParameterStr(mAgentCode,"?mAgentCode?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode,"?mGlobalInput?"))
							  + ReportPubFun.getWherePart("a.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
							  + ReportPubFun.getWherePart("d.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
							  + ReportPubFun.getWherePart("b.Makedate", ReportPubFun.getParameterStr(mScanStartDate,"?mScanStartDate?"), ReportPubFun.getParameterStr(mScanEndDate,"?mScanEndDate?"), 1)
				              + ReportPubFun.getWherePart("c.Senddate", ReportPubFun.getParameterStr(mIssueStartDate,"?mIssueStartDate?"), ReportPubFun.getParameterStr(mIssueEndDate,"?mIssueEndDate?"), 1)
							  + ReportPubFun.getWherePart("06", ReportPubFun.getParameterStr(this.mIssueNoticeType,"?mIssueNoticeType?"))
				              + ReportPubFun.getWherePart("d.riskcode",ReportPubFun.getParameterStr(mRiskCode,"?mRiskCode?"))
				              + ReportPubFun.getWherePart("6",ReportPubFun.getParameterStr(mOperatePos,"?mOperatePos?"))	
				              + " union "
				              + " select a.managecom a,a.prtno b,a.proposalcontno c,a.appntname d,a.insuredname e, "
				              + " (case when (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ ) is not null then (select sum(amnt) from lcpol where contno=a.contno and mainpolno=polno /*and uwflag in ('4','9')*/ )  else 0 end) f, "
				              + " (case when (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/) is not null then (select sum(prem) from lcpol where contno=a.contno /*and uwflag in ('4','9')*/)  else 0 end) g, '6' o, "
				              + " d.agentcode m,(select name from laagent where agentcode=a.agentcode) h,a.salechnl i, a.firsttrialdate x, concat(concat(to_char(b.makedate,'yyyy-mm-dd') , ' ') , b.maketime) j, concat(concat(to_char(c.senddate,'yyyy-mm-dd') , ' ') , c.sendtime) l, '07' k, (to_date(substr(now(), 1, 10), 'YYYY-MM-DD') - c.senddate) y " 
				              + " from lccont a,es_doc_main b,lcpenotice c,lcpol d " 
				              + " where a.prtno=b.doccode "
				              + " and a.contno=d.contno"
				              + " and b.busstype='TB' "
				              + " and b.subtype='UA001' "
				              + " and a.appflag='0' "
				              + " and a.uwflag not in ('1','2','a') "
				              + " and a.conttype='1' " 
				              + " and a.contno=c.contno " 
				              + " and c.printflag is not null and c.peresult is null "
				              + ReportPubFun.getWherePart("a.InsuredName", ReportPubFun.getParameterStr(mInsuredName,"?mInsuredName?"))
				              + ReportPubFun.getWherePart("a.Agentcode", ReportPubFun.getParameterStr(mAgentCode,"?mAgentCode?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				              + ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode,"?mGlobalInput?"))
							  + ReportPubFun.getWherePart("a.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
							  + ReportPubFun.getWherePart("d.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
							  + ReportPubFun.getWherePart("b.Makedate", ReportPubFun.getParameterStr(mScanStartDate,"?mScanStartDate?"), ReportPubFun.getParameterStr(mScanEndDate,"?mScanEndDate?"), 1)
				              + ReportPubFun.getWherePart("c.Senddate", ReportPubFun.getParameterStr(mIssueStartDate,"?mIssueStartDate?"), ReportPubFun.getParameterStr(mIssueEndDate,"?mIssueEndDate?"), 1)
							  + ReportPubFun.getWherePart("07", ReportPubFun.getParameterStr(this.mIssueNoticeType,"?mIssueNoticeType?"))
				              + ReportPubFun.getWherePart("d.riskcode",ReportPubFun.getParameterStr(mRiskCode,"?mRiskCode?"))
				              + ReportPubFun.getWherePart("6",ReportPubFun.getParameterStr(mOperatePos,"?mOperatePos?"))	
				              + " ) X,ldcode Y where Y.codetype='issuenoticetype' "
				              + " and X.k=Y.code "
				              + " order by X.l ";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strListSQL);
			sqlbv.put("mInsuredName", mInsuredName);
			sqlbv.put("mAgentCode", mAgentCode);
			sqlbv.put("mManageCom", this.mManageCom);
			sqlbv.put("mGlobalInput", this.mGlobalInput.ComCode);
			sqlbv.put("mSaleChnl", mSaleChnl);
			sqlbv.put("mStartDate", mStartDate);
			sqlbv.put("mEndDate", mEndDate);
			sqlbv.put("mScanStartDate", mScanStartDate);
			sqlbv.put("mScanEndDate", mScanEndDate);
			sqlbv.put("mIssueStartDate", mIssueStartDate);
			sqlbv.put("mIssueEndDate", mIssueEndDate);
			sqlbv.put("mIssueNoticeType", this.mIssueNoticeType);
			sqlbv.put("mRiskCode", mRiskCode);
			sqlbv.put("mOperatePos", mOperatePos);
			
			ExeSQL ListExeSQL = new ExeSQL();
			SSRS ListSSRS = new SSRS();
			ListSSRS = ListExeSQL.execSQL(sqlbv);
			int ListCount = ListSSRS.getMaxRow();
			String[] cols = new String[17];
			for (int j = 1; j <= ListSSRS.MaxRow; j++) {
				cols[0] = j + "";
				for(int n=1;n<=16;n++)
				{
					cols[n] = ListSSRS.GetText(j, n);
				}
				aListTable.add(cols);
				cols = new String[17];
			}
		//String[] col = new String[10];
		aTextTag.add("StartDate", StrTool.getChnDate(mStartDate));
		aTextTag.add("EndDate", StrTool.getChnDate(mEndDate));
		aTextTag.add("ScanStartDate", StrTool.getChnDate(mScanStartDate));
		aTextTag.add("ScanEndDate", StrTool.getChnDate(mScanEndDate));
		aTextTag.add("IssueStartDate", StrTool.getChnDate(mIssueStartDate));
		aTextTag.add("IssueEndDate", StrTool.getChnDate(mIssueEndDate));

		aTextTag.add("PrintDate", PubFun.getCurrentDate());
		//aTextTag.add("FilialeComName", FilialeComName);
		//aTextTag.add("BranchComName", BranchComName);
		xmlexport.createDocument("ProposalNoRespQuery.vts", "printer");
		xmlexport.addListTable(aListTable, cols);

		xmlexport.addTextTag(aTextTag);

		mResult.addElement(xmlexport);
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ProposalNoRespQueryBL";
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
