package com.sinosoft.lis.f1print;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class ScanErrorLisBL {
private static Logger logger = Logger.getLogger(ScanErrorLisBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public  CErrors mErrors = new CErrors();
	private TransferData mTransferData = new TransferData();
	private VData mResult = new VData();
	private XmlExport mXmlExport = null;
	private String mManageCom=     ""; 
	private String mScanType=  "";  
	private String mStartDate =     ""; 
	private String mEndDate =        ""; 
	private String mComGrade =     ""; 


	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public  ScanErrorLisBL() {
	}


	/** 传输数据的公共方法 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData)) {
			return false;
		}
		if(!checkData()){
			return false;
		}
		if (!dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		if (mGlobalInput == null) {
			buildError("getInputData", "超时，请重新登陆！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if(mTransferData == null){
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		
		mManageCom = (String)mTransferData.getValueByName("ManageCom");
		mScanType = (String)mTransferData.getValueByName("ScanType");
		mStartDate = (String)mTransferData.getValueByName("StartDate");
		mEndDate = (String)mTransferData.getValueByName("EndDate");
		mComGrade = (String)mTransferData.getValueByName("ComGrade");

		
		
		logger.debug("mManageCom：" + mManageCom);
		logger.debug("mScanType：" + mScanType);
		logger.debug("mStartDate：" + mStartDate);
		logger.debug("mEndDate：" + mEndDate);
		logger.debug("mComGrade："+mComGrade);

		return true;
	}
	private boolean dealData() {
		
		mXmlExport = new XmlExport(); // 新建一个XmlExport的实例
//		mXmlExport.createDocument("CallbackReceipt.vts", "");
		mXmlExport.createDocument("ScanErrorLis.vts", "");
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ListTable tlistTable = new ListTable();
		tlistTable.setName("ScanError");
		String[] strArr = null;
		
		tTextTag.add("ReportPeriod", BqNameFun.getChDate(mStartDate)+ "至" + BqNameFun.getChDate(mEndDate));
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		tTextTag.add("ReportDate", df.format(new Date()));
		mXmlExport.addTextTag(tTextTag);
		
		// 定义列表标题
		String[] Title = new String[7];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		
		 String gradeSql =" X.p ";
		    if(mComGrade != null && mComGrade.length() > 0)
		    {
		    	gradeSql = " substr(X.p,1,"+"?mComGrade?"+") " ;
		    }
			
		    
		    String tScanType_add = " and 1 = 1 ";
		    String tScanType_add1 = " and 1 = 1 ";
		                       
		    if(mScanType != null && mScanType.equals("个险")){
		    	tScanType_add = tScanType_add+" and substr(a.doccode,1,4) = '8611'  ";
		    	tScanType_add1 = tScanType_add1+" and substr(Y.PrtNo,1,4) = '8611' ";
		    }else if(mScanType != null && mScanType.equals("中介")){
		    	tScanType_add = tScanType_add+" and substr(a.doccode,1,4) = '8621' ";
		    	tScanType_add1 = tScanType_add1+" and substr(Y.PrtNo,1,4) = '8621' ";
		    }else if(mScanType != null && mScanType.equals("简易")){
		    	tScanType_add = tScanType_add+" and substr(a.doccode,1,4) = '8616' ";
		    	tScanType_add1 = tScanType_add1+" and substr(Y.PrtNo,1,4) = '8616'  ";
		    }else if(mScanType != null && mScanType.equals("银代")){
		    	tScanType_add = tScanType_add+" and substr(a.doccode,1,4) in ('8615','8625','8635') ";
		    	tScanType_add1 = tScanType_add1+" and substr(Y.PrtNo,1,4) in ('8615','8625','8635') ";
		    }else {
		      
		    }
		    
			String strSQL = "select "+gradeSql+",sum(X.q), sum(X.m), sum(X.n), sum(X.z), (case when  to_char(sum(X.n)) = '0' then concat(sum(X.q), '/0') when  to_char(sum(X.q)) = '0' then '0%' when to_char(sum(X.q)) =  to_char(sum(X.n)) then  '100%' else concat(to_char(round(sum(X.q) / sum(X.n), 4) * 100, 'fm990.999') , '%') end) "
					+ " from (select max(a.managecom) p, "
					+ " doccode t, "
					+ " count(1) m, "
					+ " (case when (select max(1) from es_doc_main where doccode = a.doccode and subtype = 'UA001') is not null then (select max(1) from es_doc_main where doccode = a.doccode and subtype = 'UA001')  else 0 end) n, "
					+ " (count(1) - (case when (select max(1) from es_doc_main where doccode = a.doccode and subtype = 'UA001') is not null then (select max(1) from es_doc_main where doccode = a.doccode and subtype = 'UA001')  else 0 end)) z , "
					+ " 0 q "
					+ " from es_doc_main a "
					+ " where 1=1 "
					+ ReportPubFun.getWherePart("a.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)
		            + tScanType_add
		            + " and a.ManageCom like concat('%',concat('" + "?mManageCom?" + "','%'))"
					+ " and a.subtype in ('UA001', 'UN100', 'UN101', 'UN102', 'UN103', 'UN104', 'UN105', 'UR200', 'UR201', 'UR202', 'UR203', 'UR204', 'UR205', 'UR206', 'UR207', 'UR208', 'UR209', 'UR210', 'UR211', 'UR212', 'UR301')"
					+ " group by doccode "
					+ " union select max((select comcode from lduser where usercode = Y.Oldoperator)) p, prtno t, 0 m, 0 n, 0 z, 1 q "
					+ " from lcdeltrace Y where 1=1 "
					+ ReportPubFun.getWherePart("Y.Makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)
		            + tScanType_add1
		            + " and (select comcode from lduser where usercode = Y.Oldoperator) like concat('%',concat('" + "?mManageCom?" + "','%'))"
					+ " group by prtno) X "
					+ " where 1 = 1 "
					+ " group by "+gradeSql+" "
					+ " order by "+gradeSql+"";
		
		logger.debug("扫描差错率统计报表 :" + strSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mComGrade", mComGrade);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.getMaxRow() == 0) {
			buildError("dealdata", "没有要统计的数据!");
			return false;
		}
		
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strArr = new String[7];
			strArr[0] = i + ""; 			 //序号
			strArr[1] = tSSRS.GetText(i,1);  //管理机构
			strArr[2] = tSSRS.GetText(i,2);  //差错件数
			strArr[3] = tSSRS.GetText(i,3); //扫描总件数
			strArr[4] = tSSRS.GetText(i,4); //投保单扫描件数
			strArr[5] = tSSRS.GetText(i,5); //其他类扫描件数
			strArr[6] = tSSRS.GetText(i,6); //扫描差错率

			tlistTable.add(strArr);
		}
		mXmlExport.addListTable(tlistTable, Title);
		
		mResult.clear();
		mResult.addElement(mXmlExport);
		
		return true;
	}
	/**
	 *  数据校验
	 * @return
	 */
	private boolean checkData(){
		
		if(mManageCom != null && "".equals(mManageCom)){
			buildError("checkData", "管理机构为空！");
			return false;
		}
		if(mStartDate != null && "".equals(mStartDate)){
			buildError("checkData", "开始日期为空！");
			return false;
		}
		if(mEndDate != null && "".equals(mEndDate)){
			buildError("checkData", "结束日期为空！");
			return false;
		}
	    if(mComGrade != null && mComGrade.length() > 0)
	    {
	    	if(Integer.parseInt(mComGrade) < mManageCom.length())
	    	{
	    		buildError("checkData", "请选择"+ mManageCom.length()+ "位及以上的统计粒度！");
				return false;
	    	}
	    }
		return true;
	}
	public VData getResult() {
		return this.mResult;
	}
	
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ScanErrorLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom","86");
		tTransferData.setNameAndValue("AgentCode","");
		tTransferData.setNameAndValue("AgentCom","");
		tTransferData.setNameAndValue("SaleChnl","");
		tTransferData.setNameAndValue("SignDateStart","2010-01-01");
		tTransferData.setNameAndValue("SignDateEnd","2010-05-13");
		tTransferData.setNameAndValue("CleanPolFlag","0");
		tTransferData.setNameAndValue("StatType","19");
//		tTransferData.setNameAndValue("CleanPolFlag","1");
//		tTransferData.setNameAndValue("StatType","15");
		
		
		VData tVData = new VData();
		VData mResult = new VData();
		
		tVData.add(tTransferData);
		tVData.addElement(tG);

		ScanErrorLisBL tScanErrorLisBL = new ScanErrorLisBL();
		if (!tScanErrorLisBL.submitData(tVData, "PRINT")) {
			int n = tScanErrorLisBL.mErrors.getErrorCount();
			}  else {
				logger.debug("数据文件");
		}
	}

}
