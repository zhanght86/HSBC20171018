package com.sinosoft.lis.f1print;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
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

public class FaultyReportLisBL {
private static Logger logger = Logger.getLogger(FaultyReportLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String mManageCom;
	private String strIssueDate;
	private String comGrade = "";
	private String strSQL;
	private String mRiskCode;
	private String mSaleChnl;
	private String mAgentCode;
	private String mOperatePos;
	private String mBackObj;
	private String mStartTime;
	private String mEndTime;
	private String mIsRecord;
	
	
	
	Vector finalAeon = new Vector();
	Vector departmentBin = new Vector();
	Vector groupBin = new Vector();
	Vector tryNewWay = new Vector();
	private int sumBills = 0;
	private int areaNo = 0;
	private int sumBillCom = 0;
	private XmlExport newXmlExport = new XmlExport();
	private int getJ = 1;
	private String strAgentGroup = "";
	private GlobalInput mGlobalInput = new GlobalInput();

	public FaultyReportLisBL() {
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

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		mManageCom = (String) cInputData.get(0);
		strIssueDate = (String) cInputData.get(1);
		mRiskCode = (String) cInputData.get(3);
		mSaleChnl = (String) cInputData.get(4);
		mAgentCode = (String) cInputData.get(5);
		mOperatePos = (String) cInputData.get(6);
		mBackObj = (String) cInputData.get(7);
		mStartTime = (String) cInputData.get(8);
		mEndTime = (String) cInputData.get(9);
		mIsRecord = (String) cInputData.get(10);
		
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mManageCom =mGlobalInput.ManageCom;
		}
		// strAgentGroup= (String) cInputData.get(2);
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
		cError.moduleName = "FaultyReportLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		// 选择临时文件读取目录
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("FaultyReportLis.vts", "printer");
		TextTag aTextTag = new TextTag();
		ListTable aListTable = new ListTable();
		aListTable.setName("LIST");

		// 根据传入的管理机构查询中心支公司。
		String strComSQL = "";
		int tComLength = mManageCom.length();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		if (tComLength <= 6) {
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where a.comgrade='03'"
					+ " and a.comcode like concat('"+ "?mManageCom?" + "','%')"
					+ " and a.comcode like concat('"+ "?ComCode?" + "','%')"
					+ " order by a.comcode";
		} else { // 当机构长度大于6位时，该机构为营销服务部，应取其前六位以确定所属中支及分公司。
			strComSQL = "select a.comcode,a.name,a.upcomcode,"
					+ "(select name from ldcom b where b.comcode=a.upcomcode)"
					+ " from ldcom a where a.comgrade='03'"
					+ " and a.comcode like concat('"+ "?mManageCom1?" + "','%')"
					+ " and a.comcode like concat('"+ "?ComCode?" + "','%')" 
					+ " order by a.comcode";
		}
		int dataCount = 0;
		ExeSQL ComExeSQL = new ExeSQL();
		SSRS ComSSRS = new SSRS();
		sqlbv1.sql(strComSQL);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mManageCom1", mManageCom);
		sqlbv1.put("ComCode", this.mGlobalInput.ComCode);
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
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			String strSQL_1 = "select a.prtseq, a.otherno,"
	             + " (select codename from ldcode where codetype ='renoticetype' and code =a.code), "
	             + "  d.issuecont,b.appntname,(select name from laagent where agentcode =a.agentcode),"
	             + "  (select max(riskcode) from lcpol where mainpolno =polno and contno =b.contno),"
	             + " (select codename from ldcode where codetype ='operatepos' and code =d.operatepos),"
	             + "  '',"
	             + "  ''"
	             + " from  loprtmanager a ,lccont b,lcissuepol d where code in ('TB90','TB89','14')"
	             + " and a.otherno=b.prtno and b.contno =d.contno and a.oldprtseq =d.prtseq "
	             + " and d.replyresult is null"
	             +ReportPubFun.getWherePart("a.Makedate", ReportPubFun.getParameterStr(mStartTime, "?mStartTime?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?") , 1)
	             +ReportPubFun.getWherePartLike("a.ManageCom",ReportPubFun.getParameterStr(managecom, "?managecom?") );
	          if(mRiskCode!=null&&!"".equals(mRiskCode)){
				   strSQL_1 =strSQL_1+" and (select max(riskcode) from lcpol where mainpolno=polno and contno =b.contno)='"+"?mRiskCode?"+"'";       
	          }
	          if(mSaleChnl !=null&&!"".equals(mSaleChnl)){
	              strSQL_1 =strSQL_1+ " and b.salechnl='"+"?mSaleChnl?"+"' ";
	          }
	          if(mBackObj!=null&&!"".equals(mBackObj)){
	            if("1".equals(mBackObj)){
	               strSQL_1 =strSQL_1+" and d.backobjtype in ('1','2')";
	            }else{
	               strSQL_1 =strSQL_1+" and d.backobjtype ='"+"?mBackObj?"+"'";
	            }
	          }
	          if(mOperatePos!=null&&!"".equals(mOperatePos)){
	             if("1".equals(mOperatePos)){
	                strSQL_1 =strSQL_1+" and d.operatepos in ('0','1','5')";
	             }else{
	                strSQL_1 =strSQL_1+" and d.operatepos ='6'";
	             }
	          }
	          if(mIsRecord!=null&&!"".equals(mIsRecord)){
	             if("Y".equals(mIsRecord)){
	                strSQL_1 =strSQL_1+" and d.errflag='Y'";
	             }else{
	                strSQL_1 =strSQL_1+"  and (d.errflag='N' or d.errflag is null)";
	             }
	          }
	          if(mAgentCode!=null&&!"".equals(mAgentCode)){
	                strSQL_1 =strSQL_1+" and b.agentcode ='"+"?mAgentCode?"+"'";
	          }
	          sqlbv2.sql(strSQL_1);
	          sqlbv2.put("mStartTime", mStartTime);
	          sqlbv2.put("mEndTime", mEndTime);
	          sqlbv2.put("managecom", managecom);
	          sqlbv2.put("mRiskCode", mRiskCode);
	          sqlbv2.put("mSaleChnl", mSaleChnl);
	          sqlbv2.put("mBackObj", mBackObj);
	          sqlbv2.put("mAgentCode", mAgentCode);
	          
	          SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
	    String strSQL_2 ="union"
	    		 + " select c.prtseq, c.grpcontno, '机构问题件', c.issuecont,"
	    		 + " (select appntname from lccont where contno =c.contno),"
	    		 + " (select (select name from laagent where agentcode =e.agentcode) from lccont e where contno =c.contno),"
	    		 + " (select max(riskcode) from lcpol where mainpolno =polno and contno =c.contno),"
	    		 + " (select codename from ldcode where codetype ='operatepos' and code =c.operatepos),"
	    		 + "  '',''"
	    		 + " from lcissuepol c where 1=1"
	    		 + " and c.prtseq not in (select prtseq from loprtmanager)"
	             +ReportPubFun.getWherePart("c.Makedate", ReportPubFun.getParameterStr(mStartTime, "?mStartTime?"), ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"), 1)
	             +ReportPubFun.getWherePartLike("c.ManageCom", ReportPubFun.getParameterStr(managecom, "?managecom?"));
	    	if(mRiskCode!=null&&!"".equals(mRiskCode)){
				   strSQL_2 =strSQL_2+" and (select max(riskcode) from lcpol where mainpolno=polno and contno =c.contno)='"+"?mRiskCode?"+"'";       
	          }
	    	if(mSaleChnl !=null&&!"".equals(mSaleChnl)){
	              strSQL_2 =strSQL_2+ " and (select salechnl from lccont where contno =c.contno)='"+"?mSaleChnl?"+"' ";
	          }
	    	if(mBackObj!=null&&!"".equals(mBackObj)){
	            if("1".equals(mBackObj)){
	               strSQL_2 =strSQL_2+" and c.backobjtype in ('1','2')";
	            }else{
	               strSQL_2 =strSQL_2+" and c.backobjtype ='"+"?mBackObj?"+"'";
	            }
	          }
	    	if(mOperatePos!=null&&!"".equals(mOperatePos)){
	             if("1".equals(mOperatePos)){
	                strSQL_2 =strSQL_2+" and c.operatepos in ('1','5')";
	             }else{
	                strSQL_2 =strSQL_2+" and c.operatepos ='6'";
	             }
	          }
	    	if(mIsRecord!=null&&!"".equals(mIsRecord)){
	             if("Y".equals(mIsRecord)){
	                strSQL_2 =strSQL_2+" and c.errflag='Y'";
	             }else{
	                strSQL_2 =strSQL_2+" and (c.errflag='N' or c.errflag is null)";
	             }
	          }
	    	if(mAgentCode!=null&&!"".equals(mAgentCode)){
	                strSQL_2 =strSQL_2+" and (select agentcode from lccont where contno=c.contno) ='"+"?mAgentCode?"+"'";
	          }
	    	sqlbv3.sql(strSQL_2);
	    	sqlbv3.put("mStartTime", mStartTime);
	    	sqlbv3.put("mEndTime", mEndTime);
	    	sqlbv3.put("managecom", managecom);
	    	sqlbv3.put("mRiskCode", mRiskCode);
	    	sqlbv3.put("mSaleChnl", mSaleChnl);
	    	sqlbv3.put("mBackObj", mBackObj);
	    	sqlbv3.put("mAgentCode", mAgentCode);
	          //String strSQL =strSQL_1+strSQL_2;
	          logger.debug("sql:"+strSQL);
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
				String[] col1 = new String[11];
				col1[0] = "序号";
				col1[1] = "打印流水号";
				col1[2] = "投保单印刷号";
				col1[3] = "通知书名称";
				col1[4] = "问题件具体内容";
				col1[5] = "投保人姓名";
				col1[6] = "代理人姓名";
				col1[7] = "主险代码";
				col1[8] = "操作位置";
				col1[9] = "签收人";
				col1[10] = "签收日期";
				String[] col2 = new String[11];
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
				aListTable.add(col2);
				aListTable.add(col1);

			}
			for (int j = 1; j <= ListSSRS.MaxRow; j++) {
				String[] cols = new String[11];
				cols[0] = j + "";
				cols[1] = ListSSRS.GetText(j, 1);
				cols[2] = ListSSRS.GetText(j, 2);
				cols[3] = ListSSRS.GetText(j, 3);
				cols[4] = ListSSRS.GetText(j, 4);
				cols[5] = ListSSRS.GetText(j, 5);
				cols[6] = ListSSRS.GetText(j, 6);
				cols[7] = ListSSRS.GetText(j, 7);
				cols[8] = ListSSRS.GetText(j, 8);
				aListTable.add(cols);
			}
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
		}
		String[] col = new String[11];
		xmlexport.addListTable(aListTable, col);
		aTextTag.add("StatiStartDate", StrTool.getChnDate(mStartTime));
		aTextTag.add("StatiEndDate", StrTool.getChnDate(mEndTime));
		aTextTag.add("ManageCom", mManageCom);

		xmlexport.addTextTag(aTextTag);

		mResult.addElement(xmlexport);
		if (dataCount == 0) {
			buildError("getInputData", "没有可以打印的数据");
			return false;
		}
		return true;

	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "8632";
		String tIssueDate = "2005-09-09";
		// String tAgentGroup = "0005";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		// tVData.addElement(tAgentGroup);

		FaultyReportLisUI tFaultyReportLisUI = new FaultyReportLisUI();
		tFaultyReportLisUI.submitData(tVData, "PRINT");
	}
}
