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
/**
 *程序名称：ScanDeleteLisBL.java
 *程序功能：综合打印—承保报表清单--单证删除清单
 *创建日期：2010-06-02
 *创建人  ：hanabin
 *更新记录：  更新人    更新日期     更新原因/内容
 */

public class ScanDeleteLisBL {
private static Logger logger = Logger.getLogger(ScanDeleteLisBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public  CErrors mErrors = new CErrors();
	private TransferData mTransferData = new TransferData();
	private VData mResult = new VData();
	private XmlExport mXmlExport = null;
	private String mManageCom=     ""; 
	private String mStartDate=   ""; 
	private String mEndDate =    ""; 
	private String mPrtNo =     ""; 


	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public  ScanDeleteLisBL() {
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
		mStartDate = (String)mTransferData.getValueByName("StartDate");
		mEndDate = (String)mTransferData.getValueByName("EndDate");
		mPrtNo = (String)mTransferData.getValueByName("PrtNo");

	
		logger.debug("mManageCom：" + mManageCom);
		logger.debug("mStartDate)：" + mStartDate);
		logger.debug("mEndDate："+mStartDate);
		logger.debug("mPrtNo："+mPrtNo);

		return true;
	}
	private boolean dealData() {
		
		mXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		mXmlExport.createDocument("ScanDeleteLis.vts", "");
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ListTable tlistTable = new ListTable();
		tlistTable.setName("ScanDele");
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
		
		
	    
	    
	    String strsql = " select a.managecom,a.prtno,a.makedate,a.maketime,a.operator,a.remark from lcdeltrace a where 1=1"
            +  ReportPubFun.getWherePart("a.Makedate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)
            +  ReportPubFun.getWherePart("a.PrtNo",ReportPubFun.getParameterStr(mPrtNo,"?mPrtNo?"))
            + " and a.ManageCom like concat('%',concat('" + "?mManageCom?" + "','%'))"
            + " and a.ManageCom like concat('%',concat('" + "?mGlobalInput?" + "','%'))"
            + " order by a.MakeDate ";
		logger.debug("单证删除清单:" + strsql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strsql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mPrtNo", mPrtNo);
		sqlbv.put("mManageCom", this.mManageCom);
		sqlbv.put("mGlobalInput", mGlobalInput.ComCode);
		
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.getMaxRow() == 0) {
			buildError("dealdata", "没有要统计的数据!");
			return false;
		}
		
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strArr = new String[7];
			strArr[0] = i + "";  //序号
			strArr[1] = tSSRS.GetText(i,1);  //管理机构
			strArr[2] = tSSRS.GetText(i,2);  //印刷号
			strArr[3] = tSSRS.GetText(i,3);  //删除日期
			strArr[4] = tSSRS.GetText(i,4); //删除时间
			strArr[5] = tSSRS.GetText(i,5); //操作员
			strArr[6] = tSSRS.GetText(i,6); //原因
			

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
		
		if(mPrtNo==null ||"".equals(mPrtNo))
		{
			if(mStartDate==null||"".equals(mStartDate)||mStartDate==null||"".equals(mStartDate))
			{
				buildError("checkData","请录入【印刷号】或【删除起止日期】！");
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
		cError.moduleName = "ScanDeleteLisBL";
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

		ScanDeleteLisBL tScanDeleteLisBL = new ScanDeleteLisBL();
		if (!tScanDeleteLisBL.submitData(tVData, "PRINT")) {
			int n = tScanDeleteLisBL.mErrors.getErrorCount();
			}  else {
				logger.debug("数据文件");
		}
	}

}
