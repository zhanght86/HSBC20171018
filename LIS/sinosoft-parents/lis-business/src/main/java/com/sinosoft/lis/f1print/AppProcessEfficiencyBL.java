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

public class AppProcessEfficiencyBL {
private static Logger logger = Logger.getLogger(AppProcessEfficiencyBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public  CErrors mErrors = new CErrors();
	private TransferData mTransferData = new TransferData();
	private VData mResult = new VData();
	private XmlExport mXmlExport = null;
	private String mManageCom=     ""; 
	private String mCleanPolFlag=  ""; 
	private String mSaleChnl =     ""; 
	private String mState =        ""; 
	private String mStartUWDate=   ""; 
	private String mEndUWDate =    ""; 
	private String mComGrade =     ""; 


	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public  AppProcessEfficiencyBL() {
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
		mCleanPolFlag = (String)mTransferData.getValueByName("CleanPolFlag");
		mSaleChnl = (String)mTransferData.getValueByName("SaleChnl");
		mState = (String)mTransferData.getValueByName("State");
		mStartUWDate = (String)mTransferData.getValueByName("StartUWDate");
		mEndUWDate = (String)mTransferData.getValueByName("EndUWDate");
		mComGrade = (String)mTransferData.getValueByName("ComGrade");

	
		logger.debug("mManageCom：" + mManageCom);
		logger.debug("mCleanPolFlag：" + mCleanPolFlag);
		logger.debug("mSaleChnl：" + mSaleChnl);
		logger.debug("mState：" + mState);
		logger.debug("mStartUWDate)：" + mStartUWDate);
		logger.debug("mEndUWDate："+mEndUWDate);
		logger.debug("mComGrade："+mComGrade);

		return true;
	}
	private boolean dealData() {
		
		mXmlExport = new XmlExport(); // 新建一个XmlExport的实例
//		mXmlExport.createDocument("CallbackReceipt.vts", "");
		mXmlExport.createDocument("AppProcessEfficiency.vts", "");
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ListTable tlistTable = new ListTable();
		tlistTable.setName("ApprocEff");
		String[] strArr = null;
		
		tTextTag.add("ReportPeriod", BqNameFun.getChDate(mStartUWDate)+ "至" + BqNameFun.getChDate(mEndUWDate));
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		tTextTag.add("ReportDate", df.format(new Date()));
		mXmlExport.addTextTag(tTextTag);
		
		// 定义列表标题
		String[] Title = new String[4];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		
		   String gradeSql =" X.x ";
		    if(mComGrade != "" && mComGrade.length() > 0)
		    {
		    		gradeSql = " substr(X.x,1,"+"?mComGrade?"+") ";
		    }
		    
		    String addSql_state = " and 1=1 ";
		    if(!mState.equals(""))
		    {
		    	addSql_state = addSql_state + " and a.prtno like concat('86','"+"?mState?"+"') ";
		    }
		    
//		    String cleanSql_1 = " select 1 from lcissuepol where contno = a.contno union  select 1 from lcpenotice where contno = a.contno union select 1 from LCRReport where contno = a.contno union select 1 from lccspec where contno = a.contno  union select 1 from lcprem where payplancode like '000000%' and contno = a.contno union select 1 from lbmission where missionprop1 = a.prtno and activityid = '0000001100' and missionprop18 = '6' ";
		    String cleanSql_1 = " select 1 from lcissuepol where contno = a.contno union  select 1 from lcpenotice where contno = a.contno union select 1 from LCRReport where contno = a.contno union select 1 from lccspec where contno = a.contno  union select 1 from lcprem where payplancode like '000000%' and contno = a.contno union select distinct 1 from lbmission where  missionprop1 = a.prtno and activityid in (select activityid from lwactivity where functionid='10010028') and exists(select 1 from lccuwmaster where contno=a.prtno and uwstate='6') ";
		    String cleanSql_2 = " select 1 from lcissuepol where backobjtype in ('3','4') and  needprint = 'Y' and contno = a.contno ";
//		    String nucleanSql = " select 1 from lcissuepol where backobjtype = '2' and  needprint = 'Y' and contno = a.contno union  select 1 from lcpenotice where contno = a.contno union select 1 from LCRReport where contno = a.contno union select 1 from lccspec where contno = a.contno  union select 1 from lcprem where payplancode like '000000%' and contno = a.contno union select 1 from lbmission where missionprop1 = a.prtno and activityid = '0000001100' and missionprop18 = '6' ";
		    String nucleanSql = " select 1 from lcissuepol where backobjtype = '2' and  needprint = 'Y' and contno = a.contno union  select 1 from lcpenotice where contno = a.contno union select 1 from LCRReport where contno = a.contno union select 1 from lccspec where contno = a.contno  union select 1 from lcprem where payplancode like '000000%' and contno = a.contno union select distinct 1 from lbmission where  missionprop1 = a.prtno and activityid in (select activityid from lwactivity where functionid='10010028') and exists(select 1 from lccuwmaster where contno=a.prtno and uwstate='6') ";
		    String addSql = " and 1=1 ";
		    if(mCleanPolFlag.equals("0") )
		    {
		    	addSql = addSql+ " and  exists("+nucleanSql+") ";
		    }else if(mCleanPolFlag == "1" ){
		    	addSql = addSql+ " and not exists("+cleanSql_1+") ";
		    }else if(mCleanPolFlag == "2" ){
		    	addSql = addSql+ " and not exists("+nucleanSql+") and exists("+cleanSql_2+") ";
		    }else if(mCleanPolFlag == "3" ){
		    	addSql = addSql+ " and not exists("+nucleanSql+") ";
		    }
		    SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		    String strsql=" select "+gradeSql+", sum(x.y), round(sum(X.z)/ sum(x.y), 1) from ( " 
		    		+" select managecom x, " 
		    		+" count(1) y, "
		    		+" sum((select to_date(b.MissionProp6,'yyyy-mm-dd') - (select min(makedate) from es_doc_main where doccode = a.prtno and subtype = 'UA001') from lccont where contno = a.contno and managecom = a.managecom )) z "
		    		+" from lccont a ,lwmission b " 
		    		+" where a.grpcontno = '00000000000000000000' "
		    		+" and a.conttype = '1'  "
		    		+" and a.prtno = b.missionprop1  "
		    		+" and b.activityid in (select activityid from lwactivity where functionid='10010042') "
		    		+" and exists(select 1 from es_doc_main where doccode  = a.prtno and subtype = 'UA001') "
		    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		    		+ ReportPubFun.getWherePart("b.MissionProp6", ReportPubFun.getParameterStr(mStartUWDate, "?mStartUWDate?"),ReportPubFun.getParameterStr(mEndUWDate, "?mEndUWDate?"),1)
		    		+ ReportPubFun.getWherePart("a.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl, "?mSaleChnl?"))
		    		+ ReportPubFun.getWherePartLike("a.managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
		    		+ addSql_state
		    		+ addSql
		    		+" group by managecom "
		    		+"  union "
		    		+" select managecom x, " 
		    		+" count(1) y, "
		    		+" sum((select to_date(b.MissionProp6,'yyyy-mm-dd') - (select min(makedate) from es_doc_main where doccode = a.prtno and subtype = 'UA001') from lccont where contno = a.contno and managecom = a.managecom )) z "
		    		+" from lccont a ,lbmission b " 
		    		+" where a.grpcontno = '00000000000000000000' "
		    		+" and a.conttype = '1'  "
		    		+" and a.prtno = b.missionprop1  "
		    		+" and b.activityid in (select activityid from lwactivity where functionid='10010042') "
		    		+" and exists(select 1 from es_doc_main where doccode  = a.prtno and subtype = 'UA001') "
		    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		    		+ ReportPubFun.getWherePart("b.MissionProp6", ReportPubFun.getParameterStr(mStartUWDate, "?mStartUWDate?"),ReportPubFun.getParameterStr(mEndUWDate, "?mEndUWDate?"),1)
		    		+ ReportPubFun.getWherePart("a.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl, "?mSaleChnl?"))
		    		+ ReportPubFun.getWherePartLike("a.managecom", ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
		    		+ addSql_state
		    		+ addSql
		    		+" group by managecom ) X  "
		    		+"where  1=1 group by "+gradeSql+" ";
		    sqlbv.sql(strsql);
		    sqlbv.put("mComGrade", mComGrade);
		    sqlbv.put("mStartUWDate", mStartUWDate);
		    sqlbv.put("mEndUWDate", mEndUWDate);
		    sqlbv.put("mSaleChnl", mSaleChnl);
		    sqlbv.put("mManageCom", mManageCom);
		    sqlbv.put("mState", mState);
		    
		
		logger.debug("新契约处理时效统计查询语句:" + strsql);
		
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.getMaxRow() == 0) {
			buildError("dealdata", "没有要统计的数据!");
			return false;
		}
		
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strArr = new String[4];
			strArr[0] = i + "";  //序号
			strArr[1] = tSSRS.GetText(i,1);  //管理机构
			strArr[2] = tSSRS.GetText(i,2);  //总件数
			strArr[3] = tSSRS.GetText(i,3); //新契约处理时效（日）

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
		if("".equals(mStartUWDate)){
			buildError("checkData", "核保通过日期（起期）为空！");
			return false;
		}
		if("".equals(mEndUWDate)){
			buildError("checkData", "核保通过日期（止期）为空！");
			return false;
		}
	    if(mComGrade != "" && mComGrade.length() > 0)
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
		cError.moduleName = "AppProcessEfficiencyBL";
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

		AppProcessEfficiencyBL tAppProcessEfficiencyBL = new AppProcessEfficiencyBL();
		if (!tAppProcessEfficiencyBL.submitData(tVData, "PRINT")) {
			int n = tAppProcessEfficiencyBL.mErrors.getErrorCount();
			}  else {
				logger.debug("数据文件");
		}
	}

}
