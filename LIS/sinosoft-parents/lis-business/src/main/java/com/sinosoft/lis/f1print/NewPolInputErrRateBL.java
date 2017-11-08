package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

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

public class NewPolInputErrRateBL {
private static Logger logger = Logger.getLogger(NewPolInputErrRateBL.class);
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
	private String mOperatorNo; // 打印日期
	private String mInputType; // 打印日期

	private String mOperator; // 操作员

	private String mCurrentDate = PubFun.getCurrentDate(); // 当前日期

	private GlobalInput mGlobalInput = new GlobalInput();

	public NewPolInputErrRateBL() {
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
		mInputType = (String) mTransferData.getValueByName("InputType"); 
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
		xmlexport.createDocument("NewPolInputErrRate.vts", "printer");
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
					+ " and a.comcode like concat('" + "?mManageCom?" + "','%')"
					+ " and a.comcode like concat('" + "?mGlobalInput?" + "','%')"
					+ " order by a.comcode";
		} else { // 当机构长度大于6位时，该机构为营销服务部，应取其前六位以确定所属中支及分公司。
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where a.comgrade='03'"
					+ " and a.comcode like concat('" +"?mManageCom1?"+"','%')"
					+ " and a.comcode like concat('"+ "?mGlobalInput1?"
					+ "','%')" + " order by a.comcode";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strComSQL);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mGlobalInput", mGlobalInput.ComCode);
		sqlbv1.put("mManageCom1", mManageCom.substring(0, 6));
		sqlbv1.put("mGlobalInput1", mGlobalInput.ComCode.substring(0,6));
		int dataCount = 0;
		ExeSQL ComExeSQL = new ExeSQL();
		SSRS ComSSRS = new SSRS();
		ComSSRS = ComExeSQL.execSQL(sqlbv1);
		int Count_ComSSRS = ComSSRS.getMaxRow();
		String managecom = mManageCom;
		if (Count_ComSSRS <= 0) {
			return false;
		}
//		for (int i = 1; i <= Count_ComSSRS; i++) {
//			String BranchComCode = ComSSRS.GetText(i, 1); // 中心支公司代码
//			String BranchComName = ComSSRS.GetText(i, 2); // 中心支公司名称
//			String FilialeComCode = ComSSRS.GetText(i, 3);
//			; // 分公司代码
//			String FilialeComName = ComSSRS.GetText(i, 4);
//			; // 分公司名称
//			if (tComLength <= 6) {
//				managecom = BranchComCode;
//			}
			String tInputType[]=new String[2];
			if(mInputType==null||"".equals(mInputType)){
				tInputType[0]="1";
				tInputType[1]="2";
			}else{
			    tInputType[0]=mInputType;
			}
			int j=0;
			ExeSQL tExeSQL =new ExeSQL();
			SSRS tSSRSO =new SSRS();
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("select distinct operator from lbpodatadetailerror where 1=1"
			 		   + ReportPubFun.getWherePart("operator", ReportPubFun.getParameterStr(mOperatorNo, "?mOperatorNo?"))
			 		   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"), ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"), 1)
			           +" order by operator");
			sqlbv2.put("mOperatorNo", mOperatorNo);
			sqlbv2.put("mStartDate", mStartDate);
			sqlbv2.put("mEndDate", mEndDate);
			tSSRSO =tExeSQL.execSQL(sqlbv2);
			for(int m=1;m<=tSSRSO.MaxRow;m++){
				String tOperatorNo =tSSRSO.GetText(m, 1);
				for(int n=0;n<tInputType.length;n++){
					j=j+1;
					dataCount=dataCount+1;
					String ttInputType =tInputType[n];
					if(ttInputType!=null&&!"".equals(ttInputType)){
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql("select username from lduser where usercode ='"+"?tOperatorNo?"+"'");
						sqlbv3.put("tOperatorNo", tOperatorNo);
						String tOperatorName =tExeSQL.getOneValue(sqlbv3);
						String tPOLSQL=" select substr(bussno, 1, 4) , count(1) from lbpodatadetailerror a"
							+" where a.errorcount = '"+"?ttInputType?"+"' and substr(bussno, 1, 4) in ('8611', '8621', '8615', '8625', '8635', '8616')"
							+" and operator ='"+"?tOperatorNo?"+"' and exists (select 1 from lccont where prtno =a.bussno   "
							+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
							+" )"
							+ReportPubFun.getWherePart("a.makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
							+" group by substr(bussno, 1, 4)";
						SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
						sqlbv4.sql(tPOLSQL);
						sqlbv4.put("ttInputType", ttInputType);
						sqlbv4.put("tOperatorNo", tOperatorNo);
						sqlbv4.put("mManageCom", mManageCom);
						sqlbv4.put("mStartDate", mStartDate);
						sqlbv4.put("mEndDate", mEndDate);
						SSRS tSSRSPOL =new SSRS();
						tSSRSPOL =tExeSQL.execSQL(sqlbv4);
						
						SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
						sqlbv5.sql(" select count(1) from lbpodatadetailerror a where a.errorcount = '"+"?ttInputType?"+"' and substr(bussno, 1, 4) in"
								+" ('8611', '8621', '8615', '8625', '8635', '8616') and operator='"+"?tOperatorNo?"+"' and errorflag ='1' and exists (select 1 from lccont where prtno =a.bussno "
								+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
								+" )"
								+ReportPubFun.getWherePart("a.makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
								+" ");
						sqlbv5.put("ttInputType", ttInputType);
						sqlbv5.put("tOperatorNo", tOperatorNo);
						sqlbv5.put("mManageCom", mManageCom);
						sqlbv5.put("mStartDate", mStartDate);
						sqlbv5.put("mEndDate", mEndDate);
						String tERRCount =tExeSQL.getOneValue(sqlbv5);
						
						SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
						sqlbv6.sql(" select count(distinct bussno) from lbpodatadetailerror a where a.errorcount = '"+"?ttInputType?"+"' and substr(bussno, 1, 4) in"
								+" ('8611', '8621', '8615', '8625', '8635', '8616') and operator='"+"?tOperatorNo?"+"' and errorflag ='1' and exists (select 1 from lccont where prtno =a.bussno "
								+ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
								+" )"
								+ReportPubFun.getWherePart("a.makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
								+" ");
						sqlbv6.put("ttInputType", ttInputType);
						sqlbv6.put("tOperatorNo", tOperatorNo);
						sqlbv6.put("mManageCom", mManageCom);
						sqlbv6.put("mStartDate", mStartDate);
						sqlbv6.put("mEndDate", mEndDate);
						String tERRPolCount =tExeSQL.getOneValue(sqlbv6);
						
						String[] cols = new String[11];
						cols[0] = j + "";
						cols[1] = tOperatorNo;
						cols[2] = tOperatorName;
						//有没有数据先置成0
						cols[4]="0";
						cols[5]="0";
						cols[6]="0";
						cols[7]="0";
						for(int k=1;k<=tSSRSPOL.MaxRow;k++){
							if("8611".equals(tSSRSPOL.GetText(k, 1))){
								cols[4] = String.valueOf(Integer.parseInt(cols[4])+Integer.parseInt(tSSRSPOL.GetText(k, 2)));
							}
							if("8621".equals(tSSRSPOL.GetText(k, 1))){
								cols[5] = String.valueOf(Integer.parseInt(cols[4])+Integer.parseInt(tSSRSPOL.GetText(k, 2)));
							}
							if("8615".equals(tSSRSPOL.GetText(k, 1))
									||"8625".equals(tSSRSPOL.GetText(k, 1))
									||"8635".equals(tSSRSPOL.GetText(k, 1))){
								cols[6] = String.valueOf(Integer.parseInt(cols[4])+Integer.parseInt(tSSRSPOL.GetText(k, 2)));
							}
							if("8616".equals(tSSRSPOL.GetText(k, 1))){
								cols[7] = String.valueOf(Integer.parseInt(cols[4])+Integer.parseInt(tSSRSPOL.GetText(k, 2)));
							}
						}  	
						cols[3] = String.valueOf(Integer.parseInt(cols[4])+Integer.parseInt(cols[5])+Integer.parseInt(cols[6])+Integer.parseInt(cols[7]));
						
						cols[8] = tERRCount;
						if(Integer.parseInt(cols[3])==0){
							cols[9]="-";
						}else{
							DecimalFormat mDecimalFormat = new DecimalFormat();
							cols[9] = mDecimalFormat.format((Integer.parseInt(tERRPolCount)/Integer.parseInt(cols[3]))*100)+"%";
						}
						if("1".equals(ttInputType)){
							cols[10] = "一码录入";
						}else if("2".equals(ttInputType)){
							cols[10] = "二码录入";
						}
						aListTable.add(cols);
					}
						}
				}
//			}
//			String strListSQL ="  select a.prtno,(select riskname from lmriskapp where  riskcode = a.riskcode), "
//				              +" a.appntname,a.insuredname,a.amnt,"
//				              +" (select prem from lccont where contno = a.contno),"
//				              +" a.agentcode,a.managecom"
//				              +" from lcpol a, es_doc_main b, lccont c "
//				              +" where mainpolno = polno and a.prtno = b.doccode and b.doccode = c.prtno and b.subtype ='UA001' and a.appflag = '0' and c.conttype = '1' "
//				              +ReportPubFun.getWherePart("b.PrtNo", mPrtNo)
//				              +ReportPubFun.getWherePart("b.Makedate", mStartDate, mEndDate, 1)
//				              +ReportPubFun.getWherePartLike("b.ManageCom", managecom)
//				              ;
//			logger.debug("统计类型："+mScanType);
//			if(mScanType !=null){
//				if(mScanType.equals("个险")){
//					strListSQL = strListSQL+" and substr(b.doccode,0,4) ='8611' order by a.prtno";
//				}else if(mScanType.equals("中介")){
//					strListSQL = strListSQL+" and substr(b.doccode,0,4) ='8621' order by a.prtno";
//				}else if(mScanType.equals("简易")){
//					strListSQL = strListSQL+" and substr(b.doccode,0,4) ='8616' order by a.prtno";
//				}else if(mScanType.equals("银代")){
//					strListSQL = strListSQL+" and substr(b.doccode,0,4) in ('8615','8625','8635') order by a.prtno";
//				}else{
//					strListSQL = strListSQL+" order by a.prtno ";
//				}
//			}
//
//			ExeSQL ListExeSQL = new ExeSQL();
//			SSRS ListSSRS = new SSRS();
//			ListSSRS = ListExeSQL.execSQL(strListSQL);
//			int ListCount = ListSSRS.getMaxRow();
//			if (ListCount <= 0) {
//				logger.debug("中支公司：" + BranchComCode + "("
//						+ BranchComName + ")下无数据。");
//				continue;
//			} else {
//				dataCount++;
//				String[] col1 = new String[9];
//				col1[0] = "序号";
//				col1[1] = "印刷号";
//				col1[2] = "险种名称";
//				col1[3] = "投保人";
//				col1[4] = "被保人";
//				col1[5] = "主险保额";
//				col1[6] = "保费";
//				col1[7] = "业务员";
//				col1[8] = "管理机构";
//				String[] col2 = new String[9];
//				col2[0] = "分公司:";
//				col2[1] = "" + FilialeComName;
//				col2[2] = "中心支公司: ";
//				col2[3] = BranchComName;
//				col2[4] = "";
//				col2[5] = "";
//				col2[6] = "";
//				col2[7] = "";
//				col2[8] = "";
//				aListTable.add(col2);
//				aListTable.add(col1);
//
//			}
//			for (int j = 1; j <= ListSSRS.MaxRow; j++) {
//				String[] cols = new String[9];
//				cols[0] = j + "";
//				cols[1] = ListSSRS.GetText(j, 1);
//				cols[2] = ListSSRS.GetText(j, 2);
//				cols[3] = ListSSRS.GetText(j, 3);
//				cols[4] = ListSSRS.GetText(j, 4);
//				cols[5] = ListSSRS.GetText(j, 5);
//				cols[6] = ListSSRS.GetText(j, 6);
//				cols[7] = ListSSRS.GetText(j, 7);
//				cols[8] = ListSSRS.GetText(j, 8);
//				aListTable.add(cols);
//			}
			String[] col5 = new String[11];
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
			aListTable.add(col5);
			
		String[] col = new String[1];
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
