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
 * @author 刘岩松
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class NewPolQueryBL {
private static Logger logger = Logger.getLogger(NewPolQueryBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	/**
	 * 管理机构
	 */
	private String strMngCom;
	/**
	 * 分公司名称
	 */
	private String mFenName;
	/**
	 * 中支名称
	 */
	private String mZhongName;

	/**
	 * 起始日期
	 */
	private String strScanStartDate;
	/**
	 * 终止日期
	 */
	private String strScanEndDate;
	/**
	 * 业务类型 TB－新契约 LP－理赔 BQ－保全
	 */
	private String strBusiType;
	/**
	 * 单证类型 新契约：00-全部；01-投保书类；02-通知书类；03-其他类
	 */
	private String strBusiPaperType;
	/**
	 * 扫描批次号
	 */
	//保单类型
	private String strScanType;
	/**
	 * 签单起始日期
	 */
	private String strSignStartDate;
	/**
	 * 签单终止日期
	 */
	private String strSignEndDate;
	/**
	 * 销售渠道
	 */
	private String strSaleChnl;
	
	private String strScanBatchNo;
	private String strSQL;
	private GlobalInput mGlobalInput = new GlobalInput();
	// private String strNoticeType;
	private TransferData mTransferData = new TransferData();

	public NewPolQueryBL() {
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
		if (!dealData()) {
			return false;
		}

		return true;
	}

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		// strMngCom = (String) cInputData.get(0);
		// strScanDate = (String) cInputData.get(1);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		strMngCom = (String) mTransferData.getValueByName("ManageCom");
		strScanStartDate = (String) mTransferData
				.getValueByName("ScanStartDate");
		strScanEndDate = (String) mTransferData.getValueByName("ScanEndDate");
		strSaleChnl = (String) mTransferData.getValueByName("SaleChnl");
		strSignStartDate = (String) mTransferData.getValueByName("SignStartDate");
		strSignEndDate = (String) mTransferData.getValueByName("SignEndDate");
		strScanType = (String) mTransferData.getValueByName("ScanType");
		
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if(strMngCom==null||"".equals(strMngCom)){
			strMngCom=mGlobalInput.ManageCom;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/***************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ScanLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("NewPolQuery.vts", "printer");
		TextTag aTextTag = new TextTag();
		ListTable aListTable = new ListTable();
		aListTable.setName("LIST");
		// 根据传入的管理机构查询中心支公司。
		String strComSQL = "";
		int tComLength = strMngCom.length();
		if (tComLength <= 6) {
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where a.comgrade='03'"
					+ " and a.comcode like concat('" + "?strMngCom?" + "','%')"
					+ " and a.comcode like concat('" + "?mGlobalInput?" + "','%')"
					+ " order by a.comcode";
		} else { // 当机构长度大于6位时，该机构为营销服务部，应取其前六位以确定所属中支及分公司。
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where a.comgrade='03'"
					+ " and a.comcode like concat('" + "?strMngCom1?" +"','%')"
					+ " and a.comcode like concat('"+ "?mGlobalInput?"
					+ "','%')" + " order by a.comcode";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strComSQL);
		sqlbv1.put("strMngCom", strMngCom);
		sqlbv1.put("mGlobalInput", this.mGlobalInput.ComCode);
		sqlbv1.put("strMngCom1", strMngCom);
		int dataCount = 0;
		ExeSQL ComExeSQL = new ExeSQL();
		SSRS ComSSRS = new SSRS();
		ComSSRS = ComExeSQL.execSQL(sqlbv1);
		int Count_ComSSRS = ComSSRS.getMaxRow();
		String managecom = strMngCom;
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
			String strListSQL="";
			logger.debug("统计类型："+strScanType);
			logger.debug("strSignStartDate："+strSignStartDate);
			logger.debug("strSignEndDate："+strSignEndDate);
			logger.debug("strSaleChnl："+strSaleChnl);
			if(strScanType !=null&&!"".equals(strScanType)){
				if("卡单".equals(strScanType)){
					strListSQL=" select a.prtno, a.contno, a.managecom, "
						+"  (select riskname from lmriskapp where riskcode =a.riskcode), a.appntname, a.insuredname,a.amnt,"
						+" b.prem,a.agentcode,a.signdate,(select name from lacom where agentcom =a.agentcom )"
						+" from lcpol a,lccont b where a.contno =b.contno and b.cardflag ='4' and b.conttype ='1' and a.appflag in ('1','4')"
						+ReportPubFun.getWherePart("a.signDate", ReportPubFun.getParameterStr(strSignStartDate,"?strSignStartDate?"), ReportPubFun.getParameterStr(strSignEndDate,"?strSignEndDate?"), 1)
						+" and a.riskcode in (select riskcode from lmriskapp where subriskflag='M')"
						+ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(managecom,"?managecom?"));
	                if(!strScanStartDate.equals("")&&strScanStartDate!=null)
						strListSQL+=" and a.makedate>='"+"?strScanStartDate?"+"' ";
					if(!strScanEndDate.equals("")&&strScanEndDate!=null)
						strListSQL+=" and a.makedate<='"+"?strScanEndDate?"+"' ";
	                if(!strSaleChnl.equals("")&&strSaleChnl!=null)
	                	strListSQL+=" and a.salechnl>="+"?strSaleChnl?"+" ";   
	                strListSQL+=" order by a.signdate";
	                }else {
					strListSQL ="  select a.prtno, a.contno, a.managecom, "
						+" (select riskname from lmriskapp where riskcode =a.riskcode), a.appntname, a.insuredname,a.amnt,"
						+" b.prem,a.agentcode,a.signdate,(select name from lacom where agentcom =a.agentcom )"
						+" from lcpol a,lccont b,es_doc_main c where a.contno =b.contno and c.subtype ='UA001' and b.prtno =c.doccode and b.conttype ='1' and a.appflag in ('1','4')"
						+ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(strSignStartDate,"?strSignStartDate?"), ReportPubFun.getParameterStr(strSignEndDate,"?strSignEndDate?"), 1)
						+" and a.riskcode in (select riskcode from lmriskapp where subriskflag='M')"
						+ReportPubFun.getWherePartLike("b.ManageCom", ReportPubFun.getParameterStr(managecom,"?managecom?"));
	                if(!strScanStartDate.equals("")&&strScanStartDate!=null)
						strListSQL+=" and c.makedate>='"+"?strScanStartDate?"+"' ";
					if(!strScanEndDate.equals("")&&strScanEndDate!=null)
						strListSQL+=" and c.makedate<='"+"?strScanEndDate?"+"' ";
	                if(!strSaleChnl.equals("")&&strSaleChnl!=null)
	                	strListSQL+=" and a.salechnl>="+"?strSaleChnl?"+" ";   
	                if("个险".equals(strScanType)){
						strListSQL =strListSQL +" and substr(c.doccode,1,4)='8611'   ";
					}else if("中介".equals(strScanType)){
						strListSQL =strListSQL +" and substr(c.doccode,1,4)='8621'   ";
					}else if("简易".equals(strScanType)){
						strListSQL =strListSQL +" and substr(c.doccode,1,4)='8616'   ";
					}else if("银代".equals(strScanType)){
						strListSQL =strListSQL +" and substr(c.doccode,1,4) in ('8615','8625','8635')  ";
					}
	                strListSQL+=" order by a.signdate";
				}
			}else{
				strListSQL ="  select a.prtno, a.contno, a.managecom, "
					+" (select riskname from lmriskapp where riskcode =a.riskcode), a.appntname, a.insuredname,a.amnt,"
					+" b.prem,a.agentcode,a.signdate,(select name from lacom where agentcom =a.agentcom )"
					+" from lcpol a,lccont b,es_doc_main c where a.contno =b.contno and b.prtno =c.doccode and c.subtype ='UA001' and b.conttype ='1' and a.appflag in ('1','4')"
					+ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(strSignStartDate,"?strSignStartDate?"), ReportPubFun.getParameterStr(strSignEndDate,"?strSignEndDate?"), 1)
					+" and a.riskcode in (select riskcode from lmriskapp where subriskflag='M')"
					+ReportPubFun.getWherePartLike("b.ManageCom", ReportPubFun.getParameterStr(managecom,"?managecom?"));
                if(!strScanStartDate.equals("")&&strScanStartDate!=null)
					strListSQL+=" and c.makedate>='"+"?strScanStartDate?"+"' ";
				if(!strScanEndDate.equals("")&&strScanEndDate!=null)
					strListSQL+=" and c.makedate<='"+"?strScanEndDate?"+"' ";
                if(!strSaleChnl.equals("")&&strSaleChnl!=null)
                	strListSQL+=" and a.salechnl>="+"?strSaleChnl?"+" ";     
                strListSQL= strListSQL+"union"
					+" select a.prtno, a.contno, a.managecom, "
					+" (select riskname from lmriskapp where riskcode =a.riskcode), a.appntname, a.insuredname,a.amnt,"
					+" b.prem,a.agentcode,a.signdate,(select name from lacom where agentcom =a.agentcom )"
					+" from lcpol a,lccont b where a.contno =b.contno and b.cardflag ='4' and b.conttype ='1' and a.appflag in ('1','4')"
					+ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(strSignStartDate,"?strSignStartDate?"), ReportPubFun.getParameterStr(strSignEndDate,"?strSignEndDate?"), 1)
					+" and a.riskcode in (select riskcode from lmriskapp where subriskflag='M')"
					+ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(managecom,"?managecom?"));
                if(!strScanStartDate.equals("")&&strScanStartDate!=null)
					strListSQL+=" and a.makedate>='"+"?strScanStartDate?"+"' ";
				if(!strScanEndDate.equals("")&&strScanEndDate!=null)
					strListSQL+=" and a.makedate<='"+"?strScanEndDate?"+"' ";
                if(!strSaleChnl.equals("")&&strSaleChnl!=null)
                	strListSQL+=" and a.salechnl>="+"?strSaleChnl?"+" ";
                strListSQL+=" order by signdate";
				
			}
//				if(strScanType.equals("个险")){
//					strListSQL = strListSQL+" and substr(b.doccode,0,4) ='8611'  ";
//				}else if(strScanType.equals("中介")){
//					strListSQL = strListSQL+" and substr(b.doccode,0,4) ='8621'  ";
//				}else if(strScanType.equals("简易")){
//					strListSQL = strListSQL+" and substr(b.doccode,0,4) ='8616'  ";
//				}else if(strScanType.equals("银代")){
//					strListSQL = strListSQL+" and substr(b.doccode,0,4) in ('8615','8625','8635')  ";
//				}else{
//					strListSQL = strListSQL+"   ";
//				}
//			}
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strListSQL);
			sqlbv2.put("strSignStartDate", strSignStartDate);
			sqlbv2.put("strSignEndDate", strSignEndDate);
			sqlbv2.put("managecom", managecom);
			sqlbv2.put("strScanStartDate", strScanStartDate);
			sqlbv2.put("strScanEndDate", strScanEndDate);
			sqlbv2.put("strSaleChnl", strSaleChnl);
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
				String[] col1 = new String[12];
				col1[0] = "序号";
				col1[1] = "印刷号";
				col1[2] = "保单号";
				col1[3] = "管理机构";
				col1[4] = "险种名称";
				col1[5] = "投保人";
				col1[6] = "被保人";
				col1[7] = "保险金额";
				col1[8] = "总保费";
				col1[9] = "业务员工号";
				col1[10]= "签单日期";
				col1[11]= "中介机构名称";
				String[] col2 = new String[12];
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
				aListTable.add(col2);
				aListTable.add(col1);

			}
			for (int j = 1; j <= ListSSRS.MaxRow; j++) {
				String[] cols = new String[12];
				cols[0] = j + "";
				cols[1] = ListSSRS.GetText(j, 1);
				cols[2] = ListSSRS.GetText(j, 2);
				cols[3] = ListSSRS.GetText(j, 3);
				cols[4] = ListSSRS.GetText(j, 4);
				cols[5] = ListSSRS.GetText(j, 5);
				cols[6] = ListSSRS.GetText(j, 6);
				cols[7] = ListSSRS.GetText(j, 7);
				cols[8] = ListSSRS.GetText(j, 8);
				cols[9] = ListSSRS.GetText(j, 9);
				cols[10] = ListSSRS.GetText(j, 10);
				cols[11] = ListSSRS.GetText(j, 11);
				aListTable.add(cols);
			}
			String[] col5 = new String[12];
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
			aListTable.add(col5);
		}
		String[] col = new String[10];
		xmlexport.addListTable(aListTable, col);
		aTextTag.add("StatiStartDate", StrTool.getChnDate(strScanStartDate));
		aTextTag.add("StatiEndDate", StrTool.getChnDate(strScanEndDate));
		aTextTag.add("PrintDate", PubFun.getCurrentDate());

		xmlexport.addTextTag(aTextTag);

		mResult.addElement(xmlexport);
		if (dataCount == 0) {
			buildError("getInputData", "没有可以打印的数据");
			return false;
		}
		return true;

	}

	private boolean dealData() {

		if (!getPrintData()) {
			return false;
		}
		return true;

	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "86110000";
		String tIssueDate = "2005-08-05";
		String tAgentGroup = "0003003003";
		String tNoticeType = "UA011";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tAgentGroup);
		tVData.addElement(tNoticeType);

		ScanLisUI tScanLisUI = new ScanLisUI();
		tScanLisUI.submitData(tVData, "PRINT");

	}
}
