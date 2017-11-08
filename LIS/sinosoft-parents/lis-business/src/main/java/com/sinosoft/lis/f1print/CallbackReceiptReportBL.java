package com.sinosoft.lis.f1print;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class CallbackReceiptReportBL {
private static Logger logger = Logger.getLogger(CallbackReceiptReportBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public  CErrors mErrors = new CErrors();
	private TransferData mTransferData = new TransferData();
	private VData mResult = new VData();
	private XmlExport mXmlExport = null;
	private String mManageCom = "";
	private String mAgentCode ="";
	private String mAgentCom = "";
	private String mSaleChnl = "";
	private String mSignDateStart = "";
	private String mSignDateEnd = "";
	private String mCleanPolFlag = "";
	private String mStatType= "";
	private String mComGrade = "";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public  CallbackReceiptReportBL() {
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
		mManageCom =    (String)mTransferData.getValueByName("ManageCom");
		mAgentCode =    (String)mTransferData.getValueByName("AgentCode");
		mAgentCom=      (String)mTransferData.getValueByName("AgentCom");
		mSaleChnl=      (String)mTransferData.getValueByName("SaleChnl");
		mSignDateStart= (String)mTransferData.getValueByName("SignDateStart");
		mSignDateEnd=   (String)mTransferData.getValueByName("SignDateEnd");
		mCleanPolFlag=  (String)mTransferData.getValueByName("CleanPolFlag");
		mStatType=      (String)mTransferData.getValueByName("StatType");
		mComGrade=     (String)mTransferData.getValueByName("ComGrade");
	
		logger.debug("管理机构：" + mManageCom);
		logger.debug("业务员代码：" + mAgentCode);
		logger.debug("代理机构：" + mAgentCom);
		logger.debug("销售渠道：" + mSaleChnl);
		logger.debug("保单签发日期(起期)：" + mSignDateStart);
		logger.debug("保单签发日期(止期)："+mSignDateEnd);
		logger.debug("是否清洁件："+mCleanPolFlag);
		logger.debug("统计类型："+mStatType);
		logger.debug("统计粒度："+mComGrade);

		return true;
	}
	private boolean dealData() {
		
		mXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		mXmlExport.createDocument("CallbackReceipt.vts", "");
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ListTable tlistTable = new ListTable();
		tlistTable.setName("ReceiptReport");
		String[] strArr = null;
		if("15".equals(mStatType)){
			tTextTag.add("ReportType", "回执15日核销");
		}else if("19".equals(mStatType)){
			tTextTag.add("ReportType", "回执19日核销");
		}else{
			tTextTag.add("ReportType", "回执已核销");
		}
		
		tTextTag.add("ReportPeriod", BqNameFun.getChDate(mSignDateStart)+ "至" + BqNameFun.getChDate(mSignDateEnd));
		tTextTag.add("ReportCom", BqNameFun.getComName(mManageCom));
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		tTextTag.add("ReportDate", df.format(new Date()));
		mXmlExport.addTextTag(tTextTag);
		
		// 定义列表标题
		String[] Title = new String[5];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		
		String ComGradeSql = " a.managecom ";
		if(mComGrade != null && mComGrade.length() > 0)
		{
			 ComGradeSql = " substr(a.managecom,1,"+"?mComGrade?"+") ";
		}
//		String cleanSql = " select 1 from lcissuepol where backobjtype = '2' and  needprint = 'Y' and contno = a.contno union  select 1 from lcpenotice where contno = a.contno union select 1 from LCRReport where contno = a.contno union select 1 from lccspec where contno = a.contno  union select 1 from lcprem where payplancode like '000000%' and contno = a.contno union select 1 from lbmission where missionprop1 = a.prtno and activityid = '0000001100' and missionprop18 = '6' ";
//		String replySql = " nullif(greatest(nvl((select max(lpt.replydate)  from lcrreport lpt Where lpt.contno = a.contno), '2000-01-01'), nvl((Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid = '0000001111'), '2000-01-01'), nvl((Select max(makedate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid = '0000001100'and missionprop18 = '6'), '2000-01-01'),nvl((Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid = '0000001112'), '2000-01-01')),date'2000-01-01') " ;
		String cleanSql = " select 1 from lcissuepol where backobjtype = '2' and  needprint = 'Y' and contno = a.contno union  select 1 from lcpenotice where contno = a.contno union select 1 from LCRReport where contno = a.contno union select 1 from lccspec where contno = a.contno  union select 1 from lcprem where payplancode like '000000%' and contno = a.contno union select distinct 1 from lbmission where  missionprop1 = a.prtno and activityid in (select activityid from lwactivity where functionid='10010028') and exists(select 1 from lccuwmaster where contno=a.prtno and uwstate='6') ";
		String replySql = " nullif(greatest((case when (select max(lpt.replydate)  from lcrreport lpt Where lpt.contno = a.contno) is not null then (select max(lpt.replydate)  from lcrreport lpt Where lpt.contno = a.contno)  else '2000-01-01' end),(case when (Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid in (select activityid from lwactivity where functionid='10010029')) is not null then (Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid in (select activityid from lwactivity where functionid='10010029'))  else '2000-01-01' end), (case when (Select max(makedate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid  in (select activityid from lwactivity where functionid='10010028') and exists(select 1 from lccuwmaster where contno=a.prtno and uwstate='6')) is not null then (Select max(makedate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid  in (select activityid from lwactivity where functionid='10010028') and exists(select 1 from lccuwmaster where contno=a.prtno and uwstate='6'))  else '2000-01-01' end),(case when (Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid in(select activityid from lwactivity where functionid='10010030')) is not null then (Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid in(select activityid from lwactivity where functionid='10010030'))  else '2000-01-01' end),to_date('2000-01-01','yyyy-mm-dd') ) " ;

		String addSql =  " and 1=1 ";
	    String addWhereSql =  " and 1= 1 ";
	    
	    if("0".equals(mCleanPolFlag))
		{//非清洁件
	    		
	    		addWhereSql = addWhereSql + " and (exists ("+cleanSql+"))  ";
	    		
//	    		addSql = addSql + " and (exists (select 1 from lcissuepol where contno = a.contno and needprint = 'Y' and backobjtype = '2') or exists  (select 1 from lcpenotice where contno = a.contno) or exists (select 1 from lcrreport where contno = a.contno))  ";
	    		addSql = addSql + "   ";

			if("19".equals(mStatType))
			{//回执19日核销率
				addSql = addSql + " and getpoldate -  "+replySql+" <= 19  " ;
			}else if("15".equals(mStatType))
			{//回执15日核销率
				addSql = addSql + " and getpoldate -  "+replySql+" <= 15   " ;
			}else{
				addSql = addSql + "  ";
			}
			
		}else if("1".equals(mCleanPolFlag))
		{//清洁件
			addWhereSql = addWhereSql + " and (not exists ("+cleanSql+"))  ";
			
//			addSql = addSql + " and (not exists (select 1 from lcissuepol where contno = a.contno and needprint = 'Y' and backobjtype = '2') and not exists  (select 1 from lcpenotice where contno = a.contno) and not exists (select 1 from lcrreport where contno = a.contno))  ";
			addSql = addSql + "  ";
			if("19".equals(mStatType))
			{//回执19日核销率
				addSql = addSql + " and getpoldate - a.firsttrialdate <= 19  ";
			}else if("15".equals(mStatType))
			{//回执15日核销率
				addSql = addSql + " and getpoldate -  a.firsttrialdate  <= 15 " ;
			}else{
				addSql = addSql + " ";
			}
		}else
		{
			if("19".equals(mStatType))
			{
				addSql = addSql + " and ((1=(case ("+cleanSql+") when  1  then 1 else 0 end) and getpoldate -  "+replySql+" <= 19 ) or (0 = (case ("+cleanSql+") when  1  then 1 else 0 end) and  getpoldate - a.firsttrialdate <= 19))  ";
			}else if("15".equals(mStatType))
			{
				addSql = addSql + " and ((1=(case ("+cleanSql+") when  1  then 1 else 0 end) and getpoldate -  "+replySql+" <= 15 ) or (0 = (case ("+cleanSql+") when  1  then 1 else 0 end) and  getpoldate - a.firsttrialdate <= 15))  ";
			}else
			{
				addSql = addSql   +" " ;
			}
		}
	    SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tSql = "select "+ComGradeSql+", " +
				" sum((select count(1) from lccont where  contno = a.contno  and  getpoldate is null )), " +
				" sum((select count(1) from lccont where  contno = a.contno  and getpoldate is not null "+addSql+")), " +
				" count(1), " +
				" sum((select count(1) from lccont where  contno = a.contno  and getpoldate is not null "+addSql+"))/count(1)* 100  " +
				" from lccont a " +
				" where a.appflag = '1' " +
				" and a.conttype = '1' " +
				" and not exists (select 1  from lcpol where contno = a.contno and riskcode in ('141812', '311603', '111603')) " +
				" and a.selltype != '08' "+ 
				//" and substr(a.PrtNo,0,4) != '8616' "+ 
				" and a.signdate >= '"+"?mSignDateStart?"+"'   " +
				" and a.signdate <= '"+"?mSignDateEnd?"+"'   " +
				" and a.ManageCom like concat('"+"?mManageCom?"+"','%')   " +
				" and a.ManageCom like concat('"+"?ComCode?"+"','%')   " +
				addWhereSql +
				ReportPubFun.getWherePart("a.AgentCode", ReportPubFun.getParameterStr(mAgentCode, "?mAgentCode?")) + 
				ReportPubFun.getWherePart("a.AgentCom", ReportPubFun.getParameterStr(mAgentCom, "?mAgentCom?")) + 
				ReportPubFun.getWherePart("a.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl, "?mSaleChnl?")) + 
				" group by "+ComGradeSql+" order by "+ComGradeSql+" ";
		sqlbv.sql(tSql);
		sqlbv.put("mSignDateStart", mSignDateStart);
		sqlbv.put("mSignDateEnd", mSignDateEnd);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("ComCode", mGlobalInput.ComCode);
		sqlbv.put("mAgentCode", mAgentCode);
		sqlbv.put("mAgentCom", mAgentCom);
		sqlbv.put("mSaleChnl", mSaleChnl);
		sqlbv.put("mComGrade", mComGrade);
		logger.debug("机构操作时效查询语句:" + tSql);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.getMaxRow() == 0) {
			buildError("dealdata", "没有要统计的数据!");
			return false;
		}
		
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strArr = new String[5];
			strArr[0] = tSSRS.GetText(i, 1);  //机构代码
			strArr[1] = tSSRS.GetText(i, 2);  //未核销回执数量
			strArr[2] = tSSRS.GetText(i, 3);  //已核销回执数量
			strArr[3] =  tSSRS.GetText(i, 4); //签发保单数量
			strArr[4] = String.valueOf(PubFun.round(Double.parseDouble(tSSRS.GetText(i, 5)), 2)) + "%";  //回执核销率

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
		
		if("".equals(mManageCom)){
			buildError("checkData", "管理机构为空！");
			return false;
		}
		if("".equals(mSignDateStart)){
			buildError("checkData", "保单签发日期(起期)为空！");
			return false;
		}
		if("".equals(mSignDateEnd)){
			buildError("checkData", "保单签发日期(止期)为空！");
			return false;
		}
		 if(mComGrade != null && mComGrade.length() > 0)
		    {
		    	if(Integer.parseInt(mComGrade) < mManageCom.length())
		    	{
		    		buildError("checkData", "请选择"+ mManageCom.length()+"及以上的统计粒度！");
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
		cError.moduleName = "CallbackReceiptReportBL";
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

		CallbackReceiptReportBL tCallbackReceiptReportBL = new CallbackReceiptReportBL();
		if (!tCallbackReceiptReportBL.submitData(tVData, "PRINT")) {
			int n = tCallbackReceiptReportBL.mErrors.getErrorCount();
			}  else {
				logger.debug("数据文件");
		}
	}

}
