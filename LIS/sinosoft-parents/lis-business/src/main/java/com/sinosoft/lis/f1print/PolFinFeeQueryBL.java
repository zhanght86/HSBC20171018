package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */
import com.sinosoft.lis.pubfun.*;
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

public class PolFinFeeQueryBL {
private static Logger logger = Logger.getLogger(PolFinFeeQueryBL.class);
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

	private String mCurrentDate = PubFun.getCurrentDate(); // 当前日期

	private GlobalInput mGlobalInput = new GlobalInput();

	public PolFinFeeQueryBL() {
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
		//mRiskCode = (String) mTransferData.getValueByName("RiskCode"); 
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
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("PolFinFeeQuery.vts", "printer");
		TextTag aTextTag = new TextTag();
		ListTable aListTable = new ListTable();
		aListTable.setName("LIST");

		// 根据传入的管理机构查询中心支公司。
		String strComSQL = "";
		int tComLength = mManageCom.length();
		if (tComLength <= 6) {
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where a.comgrade='03'"
					+ " and a.comcode like concat('" +"?mManageCom?" + "','%')"
					+ " and a.comcode like concat('" + "?mGlobalInput1?" + "','%')"
					+ " order by a.comcode";
		} else { // 当机构长度大于6位时，该机构为营销服务部，应取其前六位以确定所属中支及分公司。
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where a.comgrade='03'"
					+ " and a.comcode like concat('" +"?mManageCom1?"+"','%')"
					+ " and a.comcode like concat('"+ "?mGlobalInput2?"
					+ "','%')" + " order by a.comcode";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strComSQL);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mGlobalInput1", mGlobalInput.ComCode);
		sqlbv1.put("mManageCom1", mManageCom.substring(0, 6));
		sqlbv1.put("mGlobalInput2", mGlobalInput.ComCode.substring(0, 6));
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
			String strListSQL =" select a.prtno,(select riskname from lmriskapp where  riskcode = a.riskcode),a.appntname,a.insuredname,"
				              +" a.amnt,c.prem,b.makedate,b.maketime,a.agentcode,a.managecom ,c.firsttrialdate from lcpol a, es_doc_main b, lccont c"
				              +" where a.prtno = b.doccode and a.contno = c.contno "
				              //+" and a.polno=a.mainpolno"//09-11-02 查询所有险种
				              +" and b.subtype ='UA001'"
				              +" and b.BussType='TB' and a.appflag = '0' and c.conttype = '1'  "
				              +" and c.uwflag not in ('1','2','a')"
				              +ReportPubFun.getWherePart("a.PrtNo", ReportPubFun.getParameterStr(mPrtNo,"?mPrtNo?"))
				              +ReportPubFun.getWherePart("b.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				              +ReportPubFun.getWherePartLike("b.ManageCom", ReportPubFun.getParameterStr(managecom,"?managecom?"))
				              +" and not exists(select 1 from ljtempfee where otherno=a.prtno and tempfeetype='1'"
				              //+" and confflag='0'"  09-10-30去掉这个条件  只要财务录入就不查询
				              +" and riskcode=a.riskcode"//09-11-02  已交费的险种不会被查询出来
				              +")";
			logger.debug("统计类型："+mScanType);
			if(mScanType !=null){
				if(mScanType.equals("个险")){
					strListSQL = strListSQL+" and substr(b.doccode,1,4) ='8611' order by c.firsttrialdate ";
				}else if(mScanType.equals("中介")){
					strListSQL = strListSQL+" and substr(b.doccode,1,4) ='8621' order by c.firsttrialdate ";
				}else if(mScanType.equals("简易")){
					strListSQL = strListSQL+" and substr(b.doccode,1,4) ='8616' order by c.firsttrialdate ";
				}else if(mScanType.equals("银代")){
					strListSQL = strListSQL+" and substr(b.doccode,1,4) in ('8615','8625','8635') order by c.firsttrialdate ";
				}else{
					strListSQL = strListSQL+" order by c.firsttrialdate ";
				}
			}
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strListSQL);
			sqlbv2.put("mPrtNo", mPrtNo);
			sqlbv2.put("mStartDate", mStartDate);
			sqlbv2.put("mEndDate", mEndDate);
			sqlbv2.put("managecom", managecom);
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
				col1[2] = "险种名称";
				col1[3] = "投保人";
				col1[4] = "被保人";
				col1[5] = "主险保额";
				col1[6] = "保费";
				col1[7] = "扫描日期";
				col1[8] = "扫描时间";
				col1[9] = "业务员";
				col1[10] = "管理机构";
				col1[11] = "初审日期";
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
		String[] col = new String[12];
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
