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

public class ProposalIndQueryLisBL {
private static Logger logger = Logger.getLogger(ProposalIndQueryLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	// private GlobalInput mGI = new GlobalInput();//操作员信息
	private TransferData mTransferData = new TransferData(); // 传递非SCHEMA信息

	private String mManageCom; // 管理机构
	private String mStartDate; // 开始日期
	private String mEndDate; // 结束
	private String mPrintDate; // 打印日期
	private String mContNo; // 打印日期
	private String mPrtNo; // 打印日期
	private String mInsuredName; // 打印日期
	private String mAppntName; // 打印日期
	private String mInsuredNo; // 打印日期
	//private String mRiskCode; // 打印日期

	private String mOperator; // 操作员

	private String mCurrentDate = PubFun.getCurrentDate(); // 当前日期

	private GlobalInput mGlobalInput = new GlobalInput();

	public ProposalIndQueryLisBL() {
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
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 起始日期
		mContNo = (String) mTransferData.getValueByName("ContNo"); // 终止日期
		//mSaleChnl = (String) mTransferData.getValueByName("SaleChnl"); 
		mPrtNo = (String) mTransferData.getValueByName("PrtNo"); 
		mInsuredNo = (String) mTransferData.getValueByName("InsuredNo"); 
		//mAgentCode = (String) mTransferData.getValueByName("AgentCode"); 
		mInsuredName = (String) mTransferData.getValueByName("InsuredName"); 
		mAppntName = (String) mTransferData.getValueByName("AppntName"); 
		//mPrintDate = (String) mTransferData.getValueByName("PrintDate"); // 打印日期

		if (mManageCom == null || mManageCom.trim().equals("")) {
			mManageCom = this.mGlobalInput.ManageCom;
		}
		if (mStartDate == null || mStartDate.trim().equals("")) {
			buildError("getInputData", "传入的起始日期信息为空。");
			return false;
		}
		if (mEndDate == null || mEndDate.trim().equals("")) {
			buildError("getInputData", "传入的终止日期信息为空。");
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
		xmlexport.createDocument("ProposalIndQueryLis.vts", "printer");
		TextTag aTextTag = new TextTag();
		ListTable aListTable = new ListTable();
		aListTable.setName("LIST");

		// 根据传入的管理机构查询中心支公司。
		String strComSQL = "";
		int tComLength = mManageCom.length();
		//2009-12-04  此处应修改为
		if (mGlobalInput.ComCode.length()>6&&tComLength <= 6) {
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where "
					+ " a.comgrade='03' and "
					+ " a.comcode like concat('" + "?mManageCom?" + "','%')"
					+ " and a.comcode like concat('"+ "?mGlobalInput1?" + "','%')" 
					+ " order by a.comcode";
		} 
		if(mGlobalInput.ComCode.length()>6&&tComLength>6){ // 当机构长度大于6位时，该机构为营销服务部，应取其前六位以确定所属中支及分公司。
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where a.comgrade='03'"
					+ " and a.comcode like concat('" + "?mManageCom1?" + "','%')"
					+ " and a.comcode like concat('"+ "?mGlobalInput1?" + "','%')" 
					+ " order by a.comcode";
		}
		if(mGlobalInput.ComCode.length()<=6){
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
				+ "(select name from ldcom b where b.comcode=a.upcomcode)"
				+ " from ldcom a where "
				+ " a.comgrade='03' and "
				+ " a.comcode like concat('" + "?mManageCom1?" + "','%')"
				+ " and a.comcode like concat('" + "?mGlobalInput2?" + "','%')"
				+ " order by a.comcode";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strComSQL);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mGlobalInput1", mGlobalInput.ComCode.substring(0,6));
		sqlbv1.put("mManageCom1", mManageCom.substring(0, 6));
		sqlbv1.put("mGlobalInput2", mGlobalInput.ComCode);
		int dataCount = 0;
		ExeSQL ComExeSQL = new ExeSQL();
		SSRS ComSSRS = new SSRS();
		ComSSRS = ComExeSQL.execSQL(sqlbv1);
		int Count_ComSSRS = ComSSRS.getMaxRow();
		String managecom = mManageCom;
		if (Count_ComSSRS <= 0) {
			return false;
		}
		for (int i = 1; i <= Count_ComSSRS; i++) {
			String BranchComCode = ComSSRS.GetText(i, 1); // 中心支公司代码
			String BranchComName = ComSSRS.GetText(i, 2); // 中心支公司名称
			String FilialeComCode = ComSSRS.GetText(i, 3);
			; // 分公司代码
			String FilialeComName = ComSSRS.GetText(i, 4);
			; // 分公司名称
			if (tComLength <= 6) {
				managecom = BranchComCode;
			}
			String strListSQL =" select a.PrtNo ,'' ,a.salechnl ,a.managecom ,a.insuredname ,a.appntname ,"
		           + " (select Name from LAAgent where agentcode = a.agentcode) ,a.Polapplydate ,"
		           + " (select (case when min(concat(concat(to_char(c.makedate, 'yyyy-mm-dd') , ' ') , c.maketime)) is null then '无扫描件' else min(concat(concat(to_char(c.makedate, 'yyyy-mm-dd') , ' ') , c.maketime)) end)"
		           + " from es_doc_main c	where c.doccode = (a.prtno)) ," 
//	           	   + " nvl((select max(outdate) from lbmission where processid = '0000000003' and activityid = '0000001099' and missionprop1=a.prtno),"
                   + " (case when (select max(outdate) from lbmission where  activityid in (select activityid from lwactivity  where functionid ='10010002') and missionprop1=a.prtno) is not null then (select max(outdate) from lbmission where  activityid in (select activityid from lwactivity  where functionid ='10010002') and missionprop1=a.prtno)  else (select max(makedate) from lccont where prtno =a.prtno) end), "           
//	               + " (select max(outdate) from lbmission where processid = '0000000003' and activityid = '0000001090' and missionprop1=a.prtno),"
                   + " (select max(outdate) from lbmission where activityid in (select activityid from lwactivity  where functionid ='10010016') and missionprop1=a.prtno),"
	               + " (select min(makedate) from lbmission where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = a.prtno),"
//	               + " (select min(makedate) from lbmission where processid = '0000000003' and activityid = '0000001100' and missionprop1 = a.prtno),"
	               + " (select max(makedate) from lcissuepol where contno =a.contno and replyman is not null),"
	               + " a.uwdate ,"
	               + " (select to_char(max(EnterAccDate),'yyyy-mm-dd') from LJTempfee where  (otherno=a.contno or otherno=a.prtno)) , "
//	               + " (select max(outdate) from lbmission where processid = '0000000003' and activityid = '0000001150' and missionprop1=a.prtno),"
                   + " (select max(outdate) from lbmission where activityid in (select activityid from lwactivity  where functionid ='10010042') and missionprop1=a.prtno),"
                   + " (select concat(concat(to_char(f.makedate,'yyyy-mm-dd'),' '),f.maketime) from LCContPrint f where f.contno=a.contno) ,"
	               + " (case a.Appflag when 1 then '已签单' else '未签单' end) "
	               + "  from lccont a  where a.conttype='1' and a.appflag in( '0','1') "
				              +ReportPubFun.getWherePart("a.PrtNo", ReportPubFun.getParameterStr(mPrtNo,"?mPrtNo?"))
				              +ReportPubFun.getWherePart("a.InsuredName",ReportPubFun.getParameterStr(mInsuredName,"?mInsuredName?") )
				              +ReportPubFun.getWherePart("a.AppntName", ReportPubFun.getParameterStr(mAppntName,"?mAppntName?"))
				              +ReportPubFun.getWherePart("a.InsuredNo", ReportPubFun.getParameterStr(mInsuredNo,"?mInsuredNo?"))
				              +ReportPubFun.getWherePart("a.ContNo", ReportPubFun.getParameterStr(mContNo,"?mContNo?"))
				              +ReportPubFun.getWherePart("a.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				              //+ReportPubFun.getWherePart("b.SaleChnl", mSaleChnl)
				              //+ReportPubFun.getWherePart("b.AgentCode", mAgentCode)
				              //+ReportPubFun.getWherePart("a.Makedate", mStartDate, mEndDate, 1)
				              +ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(managecom,"?managecom?"))
				               +ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				    + " order by a.prtno,a.makedate";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strListSQL);
			sqlbv2.put("mPrtNo", mPrtNo);
			sqlbv2.put("mInsuredName", mInsuredName);
			sqlbv2.put("mAppntName", mAppntName);
			sqlbv2.put("mInsuredNo", mInsuredNo);
			sqlbv2.put("mContNo", mContNo);
			sqlbv2.put("mStartDate", mStartDate);
			sqlbv2.put("mEndDate", mEndDate);
			sqlbv2.put("managecom", managecom);
			sqlbv2.put("mManageCom", this.mManageCom);
			
			ExeSQL ListExeSQL = new ExeSQL();
			SSRS ListSSRS = new SSRS();
			ListSSRS = ListExeSQL.execSQL(sqlbv2);
			int ListCount = ListSSRS.getMaxRow();
			if (ListCount <= 0) {
				logger.debug("中支公司：" + BranchComCode + "("
						+ BranchComName + ")下无数据。");
				continue;
			} else {
				dataCount++;
				String[] col1 = new String[18];
				col1[0] = "序号";
				col1[1] = "印刷号";
				//col1[2] = "险种名称";
				col1[2] = "销售渠道";
				col1[3] = "管理机构";
				col1[4] = "被保险人姓名";
				col1[5] = "投保人姓名";
				col1[6] = "业务员姓名";
				col1[7] = "投保申请日期";
				col1[8] = "扫描日期";
				col1[9] = "录入完成时间";
				col1[10] = "异常件处理完毕时间";
				col1[11] = "首次人工核保时间";
				col1[12] = "问题件回复时间";
				col1[13] = "核保结论时间";
				col1[14] = "保费到账时间";
				col1[15] = "签单时间";
				col1[16] = "保单打印时间";
				col1[17] = "签单状态";
				String[] col2 = new String[18];
				col2[0] = "分公司:";
				col2[1] = "" + FilialeComName;
				col2[2] = "中心支公司: ";
				col2[3] = BranchComName;
				col2[4] = "";
				col2[5] = "";
				col2[6] = "";
				col2[7] = "";
				col2[8] = "";
				col2[9] = "";
				col2[10] = "";
				col2[11] = "";
				col2[12] = "";
				col2[13] = "";
				col2[14] = "";
				col2[15] = "";
				col2[16] = "";
				col2[17] = "";
				//col2[18] = "";
				aListTable.add(col2);
				aListTable.add(col1);

			}
			for (int j = 1; j <= ListSSRS.MaxRow; j++) {
				String[] cols = new String[18];
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
				cols[11] = ListSSRS.GetText(j, 12);
				cols[12] = ListSSRS.GetText(j, 13);
				cols[13] = ListSSRS.GetText(j, 14);
				cols[14] = ListSSRS.GetText(j, 15);
				cols[15] = ListSSRS.GetText(j, 16);
				cols[16] = ListSSRS.GetText(j, 17);
				cols[17] = ListSSRS.GetText(j, 18);
				//cols[18] = ListSSRS.GetText(j, 18);
				aListTable.add(cols);
			}
			String[] col5 = new String[18];
			col5[0] = "";
			col5[1] = "";
			col5[2] = "";
			col5[3] = "";
			col5[4] = "";
			col5[5] = "";
			col5[6] = "";
			col5[7] = "";
			col5[8] = "";
			col5[9] = "";
			col5[10] = "";
			col5[11] = "";
			col5[12] = "";
			col5[13] = "";
			col5[14] = "";
			col5[15] = "";
			col5[16] = "";
			col5[17] = "";
			//col5[18] = "";
			aListTable.add(col5);
		}
		String[] col = new String[18];
		xmlexport.addListTable(aListTable, col);
		aTextTag.add("StatiStartDate", StrTool.getChnDate(mStartDate));
		aTextTag.add("StatiEndDate", StrTool.getChnDate(mEndDate));

		xmlexport.addTextTag(aTextTag);

		mResult.addElement(xmlexport);
		if (dataCount == 0) {
			buildError("getInputData", "没有可以打印的数据");
			return false;
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
