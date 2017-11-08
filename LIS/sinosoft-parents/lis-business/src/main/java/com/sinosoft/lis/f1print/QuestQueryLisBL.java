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

public class QuestQueryLisBL {
private static Logger logger = Logger.getLogger(QuestQueryLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	// private GlobalInput mGI = new GlobalInput();//操作员信息
	private TransferData mTransferData = new TransferData(); // 传递非SCHEMA信息

	private String mManageCom; // 管理机构
	private String mStartDate; // 开始日期
	private String mEndDate; // 结束
	private String mPolStartDate; // 开始日期
	private String mPolEndDate; // 结束
	private String mPrintDate; // 打印日期
	private String mOperatePos; 
	private String mPrtNo;
	private String mBackObj; 
	private String mBackOperator; 
	private String mIfReply; 
	private String mIssueType; 
	private String mPrtSeq; 
	private String mPrintType; 
	private String mErrorFlag;

	private String mOperator; // 操作员

	private String mCurrentDate = PubFun.getCurrentDate(); // 当前日期

	private GlobalInput mGlobalInput = new GlobalInput();

	public QuestQueryLisBL() {
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
		mPolStartDate = (String) mTransferData.getValueByName("PolStartDate"); // 起始日期
		mPolEndDate = (String) mTransferData.getValueByName("PolEndDate"); // 终止日期
		mOperatePos = (String) mTransferData.getValueByName("OperatePos"); 
		mPrtNo = (String) mTransferData.getValueByName("PrtNo"); 
		mBackObj = (String) mTransferData.getValueByName("BackObj"); 
		mBackOperator = (String) mTransferData.getValueByName("BackOperator"); 
		mIssueType = (String) mTransferData.getValueByName("IssueType"); 
		mIfReply = (String) mTransferData.getValueByName("IfReply"); 
		mPrtSeq = (String) mTransferData.getValueByName("PrtSeq"); 
		mPrintType = (String) mTransferData.getValueByName("PrintType"); 
		mErrorFlag = (String) mTransferData.getValueByName("ErrorFlag"); 
		//mPrintDate = (String) mTransferData.getValueByName("PrintDate"); // 打印日期

		if (mManageCom == null || mManageCom.trim().equals("")) {
			mManageCom = this.mGlobalInput.ManageCom;
		}
		if (mPrintType == null || mPrintType.trim().equals("")) {
			CError.buildErr(this, "查询方式不确定！");
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
//		if (mPrintDate == null || mPrintDate.trim().equals("")) {
//			mPrintDate = mCurrentDate;
//		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private boolean getPrintData() {
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("QuestQueryLis.vts", "printer");
		TextTag aTextTag = new TextTag();
		ListTable aListTable = new ListTable();
		aListTable.setName("LIST");
		String BranchComCode="";
		String BranchComName="";
		String FilialeComCode="";
		String FilialeComName="";
		String ReplySql ="";
		if(mIfReply!=null&&!mIfReply.equals("")){
			  if(mIfReply.endsWith("N")){
				  ReplySql =" and ReplyMan is null order by makedate";
			  }else if(mIfReply.equals("Y")){
				  ReplySql =" and ReplyMan is not null order by makedate";
			  }else{
				  CError.buildErr(this, "“是否回复”处录入错误！");
				  return false;
			  }
		  }else{
			  ReplySql = " order by makedate";
		  }

		// 根据传入的管理机构查询中心支公司。
			String strListSQL="";
			if(mPrintType.equals("QUEST||PRINT")){
			strListSQL =" select a.proposalcontno, a.issuetype, a.issuecont, a.replyresult,a.operator, "
				              +" a.makedate,(select codename from ldcode  where 1=1and codetype = 'operatepos'"
				              +" and code =  trim(a.OperatePos)), b.codename,"
				              +" ( case"
				              +" when (a.backobjtype = '2' or a.backobjtype = '3') "
				              +" then (case needprint when 'Y' then '下发' else '不下发' end)"
				              +" else (case needprint when 'Y' then '下发' else '不下发' end)"
//				              +" else '下发'"
				              +" end),a.senddate,a.replydate,a.errflag,(select signname from lccont where contno=a.contno) "
				              + " from lcissuepol a, ldcode b,lccont c"
				              +" where 1 = 1  and a.standbyflag2 = b.othersign and a.contno=c.contno and a.backobjtype = trim(b.comcode)"
				              +" and b.codetype = 'backobj'"
				              +ReportPubFun.getWherePart("proposalcontno", ReportPubFun.getParameterStr(mPrtNo,"?mPrtNo?"))
				              +ReportPubFun.getWherePart("b.code", ReportPubFun.getParameterStr(mBackObj,"?mBackObj?"))
				              +ReportPubFun.getWherePart("operater", ReportPubFun.getParameterStr(mBackOperator,"?mBackOperator?"))
				              +ReportPubFun.getWherePart("operatepos", ReportPubFun.getParameterStr(mOperatePos,"?mOperatePos?"))
				              +ReportPubFun.getWherePart("IssueType", ReportPubFun.getParameterStr(mIssueType,"?mIssueType?"))
				              +ReportPubFun.getWherePart("a.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				              +ReportPubFun.getWherePart("c.Makedate", ReportPubFun.getParameterStr(mPolStartDate,"?mPolStartDate?"), ReportPubFun.getParameterStr(mPolEndDate,"?mPolEndDate?"), 1)
				              +ReportPubFun.getWherePartLike("IsueManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				              +ReportPubFun.getWherePartLike("IsueManageCom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode,"?mGlobalInput?"))
				              ;
				if(!(mErrorFlag==null||mErrorFlag.equals(""))){
					strListSQL =strListSQL + " and exists (select 1 from ldcodemod where code =a.issuetype and recordquest='"+"?mErrorFlag?"+"')";
				}
				strListSQL = strListSQL +ReplySql;
			    
			}else{
				//通过核保通知书流水号查询
//				if(mPrtSeq==null||mPrtSeq.equals("")){
//					CError.buildErr(this, "没有得到核保通知书流水号,请确认!");
//					return false;
//				}
				strListSQL = " select proposalcontno, issuetype, issuecont, replyresult, operator,makedate,"
					        + " OperatePos, BackObjType, (case needprint when 'Y' then '下发' else '不下发' end) "
					        + ",a.senddate,a.replydate,a.errflag,(select signname from lccont where contno=a.contno) "
					        + " from lcissuepol a where 1=1"
					        +ReportPubFun.getWherePartLike("IsueManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
					        +ReportPubFun.getWherePartLike("IsueManageCom", ReportPubFun.getParameterStr(this.mGlobalInput.ComCode,"?mGlobalInput?"))
					        +ReportPubFun.getWherePart("Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					        +" and proposalcontno in (select otherno from loprtmanager  "
					        +" where othernotype = '02' and code in ('TB89', 'TB90'))"
					        +" and backObjtype = '2' and needprint = 'Y' "
					        +ReportPubFun.getWherePart("prtseq", ReportPubFun.getParameterStr(mPrtSeq,"?mPrtSeq?"))
					        +" order by makedate"
					         ;
			}
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strListSQL);
			sqlbv1.put("mPrtNo", mPrtNo);
			sqlbv1.put("mBackObj", mBackObj);
			sqlbv1.put("mBackOperator", mBackOperator);
			sqlbv1.put("mOperatePos", mOperatePos);
			sqlbv1.put("mIssueType", mIssueType);
			sqlbv1.put("mStartDate", mStartDate);
			sqlbv1.put("mEndDate", mEndDate);
			sqlbv1.put("mPolStartDate", mPolStartDate);
			sqlbv1.put("mPolEndDate", mPolEndDate);
			sqlbv1.put("mManageCom", this.mManageCom);
			sqlbv1.put("mGlobalInput", this.mGlobalInput.ComCode);
			sqlbv1.put("mPrtSeq", mPrtSeq);			
			sqlbv1.put("mErrorFlag", mErrorFlag);
			ExeSQL ListExeSQL = new ExeSQL();
			SSRS ListSSRS = new SSRS();
			ListSSRS = ListExeSQL.execSQL(sqlbv1);
			String[] cols = new String[14];
			for (int j = 1; j <= ListSSRS.MaxRow; j++) {
				cols[0] = j + "";
				for(int n=1;n<=13;n++)
				{
					cols[n] = ListSSRS.GetText(j, n);
				}
				aListTable.add(cols);
				cols = new String[14];
			}
		aTextTag.add("StatiStartDate", StrTool.getChnDate(mStartDate));
		aTextTag.add("StatiEndDate", StrTool.getChnDate(mEndDate));
		aTextTag.add("PrintDate", PubFun.getCurrentDate());
		//aTextTag.add("FilialeComName", FilialeComName);
		//aTextTag.add("BranchComName", BranchComName);

		xmlexport.addTextTag(aTextTag);
		xmlexport.addListTable(aListTable, cols);
		mResult.addElement(xmlexport);

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
