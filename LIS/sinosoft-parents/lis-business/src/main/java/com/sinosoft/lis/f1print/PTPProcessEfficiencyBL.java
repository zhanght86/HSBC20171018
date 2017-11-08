package com.sinosoft.lis.f1print;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LDCodeSchema;
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

public class PTPProcessEfficiencyBL {
private static Logger logger = Logger.getLogger(PTPProcessEfficiencyBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public  CErrors mErrors = new CErrors();
	private TransferData mTransferData = new TransferData();
	private VData mResult = new VData();
	private XmlExport mXmlExport = null;
	private String mManageCom=     ""; 
	private String mStatType =  ""; 
	private String mSaleChnl =     ""; 
	private String mState =        ""; 
	private String mStartDate=   ""; 
	private String mEndDate =    ""; 
	private String mComGrade =     ""; 


	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public  PTPProcessEfficiencyBL() {
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
		mStatType = (String)mTransferData.getValueByName("StatType");
		mSaleChnl = (String)mTransferData.getValueByName("SaleChnl");
		mState = (String)mTransferData.getValueByName("State");
		mStartDate = (String)mTransferData.getValueByName("StartDate");
		mEndDate = (String)mTransferData.getValueByName("EndDate");
		mComGrade = (String)mTransferData.getValueByName("ComGrade");

	
		logger.debug("mManageCom：" + mManageCom);
		logger.debug("mStatType：" + mStatType);
		logger.debug("mSaleChnl：" + mSaleChnl);
		logger.debug("mState：" + mState);
		logger.debug("mStartDate)：" + mStartDate);
		logger.debug("mEndDate："+mStartDate);
		logger.debug("mComGrade："+mComGrade);

		return true;
	}
	private boolean dealData() {
		
		mXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		mXmlExport.createDocument("PTPProcessEfficiency.vts", "");
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ListTable tlistTable = new ListTable();
		tlistTable.setName("PTPProc");
		String[] strArr = null;
		
		tTextTag.add("ReportPeriod", BqNameFun.getChDate(mStartDate)+ "至" + BqNameFun.getChDate(mEndDate));
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		tTextTag.add("ReportDate", df.format(new Date()));
		
		LDCodeDB tLDCodeDB = new LDCodeDB();
 		LDCodeSchema tLDCodeSchema = new LDCodeSchema();
 		tLDCodeDB.setCodeType("stattype");
 		tLDCodeDB.setCode(this.mStatType);
 		if(!tLDCodeDB.getInfo()){
 			this.buildError("dealData", "统计类型不存在！");
 			return false;
 		}
 		tLDCodeSchema = tLDCodeDB.getSchema();
 		tTextTag.add("StatType",tLDCodeSchema.getCodeName());
 		
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
	    	gradeSql = " substr(X.x,1,"+"?mComGrade?"+") " ;
	    }
	    
	    String addSql_state = " and 1=1 ";
	    if(!"".equals(mState))
	    {
	    	addSql_state = addSql_state + " and a.prtno like concat('%',concat(concat('86','?mState?'),'%'))";
	    }
	    
	    String strsql = "";
	     if("A".equals(mStatType))
	    {
	    	 strsql =" select "+gradeSql+", sum(X.y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(a.firsttrialdate - a.polapplydate) z "
		 		+" from lccont a  " 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+ ReportPubFun.getWherePart("a.firsttrialdate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else if("B".equals(mStatType))
	     {
	    	 //初审日期—扫描日期
	    	 strsql=" select "+gradeSql+", sum(X .y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(b.makedate - a.firsttrialdate) z "
		 		+" from lccont a  , es_doc_main b" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and a.prtno = b.doccode "
		 		+" and b.subtype =  'UA001' "
		 		+ReportPubFun.getWherePart("b.makedate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else if("C".equals(mStatType))
	     {
	    	 //扫描日期—录入日期（直接导入系统件）(疑问是否一定是在LCCont中存在的？)
	    	 strsql=" select "+gradeSql+", sum(X .y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(a.makedate - b.makedate) z "
		 		+" from lccont a  , es_doc_main b" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and a.prtno = b.doccode "
		 		+" and b.subtype =  'UA001' "
//		 		+" and not exists(select 1 from lbmission where missionprop1 = a.prtno  and activityid in ('0000001090','0000001091')) "
		 		+" and not exists(select 1 from lbmission where missionprop1 = a.prtno  and activityid in (select activityid from lwactivity  where functionid in('10010016','10010017'))) "
		 		+ReportPubFun.getWherePart("a.makedate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else if("D".equals(mStatType))
	     {
	    	 //扫描日期—录入日期（经过人工处理件） (疑问是否一定是在LCCont中存在的？)
	    	 strsql=" select "+gradeSql+", sum(X .y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(a.makedate - b.makedate) z "
		 		+" from lccont a  , es_doc_main b" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and a.prtno = b.doccode "
		 		+" and b.subtype =  'UA001' "
//		 		+" and  exists(select 1 from lbmission where missionprop1 = a.prtno  and activityid in ('0000001090','0000001091')) "
		 		+" and  exists(select 1 from lbmission where missionprop1 = a.prtno  and activityid in (select activityid from lwactivity  where functionid in('10010016','10010017'))) "
		 		+ ReportPubFun.getWherePart("a.makedate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else if("E".equals(mStatType))
	     {
	    	 //录入日期—最后一次核保结论日期（清洁件）
	    	 strsql=" select "+gradeSql+", sum(X .y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(to_date(b.MissionProp6,'yyyy-mm-dd') - a.makedate) z "
		 		+" from lccont a  , lbmission b" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and a.prtno = b.missionprop1 "
//		 		+" and b.Activityid  = '0000001150' "
		 		+" and b.Activityid in (select activityid from lwactivity  where functionid ='10010042') "
		 		+" and not exists (select 1 from lcpenotice where contno = a.contno) "
		 		+" and not exists (select 1 from lcrreport where contno = a.contno) "
		 		+" and not exists (select 1 from lccspec where contno = a.contno) "
		 		+" and not exists (select 1 from lcprem where payplancode like '000000%'  and contno = a.contno) "
		 		+" and not exists (select 1 from lcissuepol where  contno = a.contno and backobjtype = '2') "
		 		+ ReportPubFun.getWherePart("b.MissionProp6",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom " 
		 		+" union "
		 		+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(to_date(b.MissionProp6,'yyyy-mm-dd') - a.makedate) z "
		 		+" from lccont a  , lwmission b" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and a.prtno = b.missionprop1 "
//		 		+" and b.Activityid  = '0000001150' "
		 		+" and b.Activityid  in (select activityid from lwactivity  where functionid ='10010042') "
		 		+" and not exists (select 1 from lcpenotice where contno = a.contno) "
		 		+" and not exists (select 1 from lcrreport where contno = a.contno) "
		 		+" and not exists (select 1 from lccspec where contno = a.contno) "
		 		+" and not exists (select 1 from lcprem where payplancode like '000000%'  and contno = a.contno) "
		 		+" and not exists (select 1 from lcissuepol where  contno = a.contno and backobjtype = '2') "
		 		+ ReportPubFun.getWherePart("b.MissionProp6",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom " 
		 		+" ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else if("F".equals(mStatType))
	     {
	    	 //录入日期—核保结论日期（非清洁件）
	    	 strsql=" select "+gradeSql+", sum(X .y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(to_date(b.MissionProp6,'yyyy-mm-dd') - a.makedate) z "
		 		+" from lccont a  , lbmission b" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and a.prtno = b.missionprop1 "
//		 		+" and b.Activityid  = '0000001150' "
		 		+" and b.Activityid  in (select activityid from lwactivity  where functionid ='10010042') "
		 		+" and (exists (select 1 from lcissuepol where contno = a.contno and needprint = 'Y' and backobjtype = '2')  "
		 		+"  or exists (select 1 from lcpenotice where contno = a.contno) "
		 		+"  or exists (select 1 from lcrreport where contno = a.contno) "
		 		+"  or exists (select 1 from lccspec where contno = a.contno   ) "
		 		+"  or exists (select 1 from lcprem where payplancode like '000000%' and contno = a.contno)) "
		 		+ ReportPubFun.getWherePart("b.MissionProp6",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom " 
		 		+" union "
		 		+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(to_date(b.MissionProp6,'yyyy-mm-dd') - a.makedate) z "
		 		+" from lccont a  , lwmission b" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and a.prtno = b.missionprop1 "
//		 		+" and b.Activityid  = '0000001150' "
		 		+" and b.Activityid  in (select activityid from lwactivity  where functionid ='10010042') "
		 		+" and (exists (select 1 from lcissuepol where contno = a.contno and needprint = 'Y' and backobjtype = '2')  "
		 		+"  or exists (select 1 from lcpenotice where contno = a.contno) "
		 		+"  or exists (select 1 from lcrreport where contno = a.contno) "
		 		+"  or exists (select 1 from lccspec where contno = a.contno   ) "
		 		+"  or exists (select 1 from lcprem where payplancode like '000000%' and contno = a.contno)) "
		 		+ ReportPubFun.getWherePart("b.MissionProp6",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom " 
		 		+" ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else if("G".equals(mStatType))
	     {
	    	 //最后一次核保结论日期—保单签发日期
	    	 strsql=" select "+gradeSql+", sum(X .y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum( a.SignDate -to_date(b.MissionProp6,'yyyy-mm-dd') ) z "
		 		+" from lccont a  , lbmission b" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and a.prtno = b.missionprop1 "
//		 		+" and b.Activityid  = '0000001150' "
		 		+" and b.Activityid  in (select activityid from lwactivity  where functionid ='10010042') "
		 		+ ReportPubFun.getWherePart("a.SignDate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom " 
		 		+" ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else if("H".equals(mStatType))
	     {
	    	 //保单签发日期—保单打印日期
	    	 strsql=" select "+gradeSql+", sum(X .y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(b.makedate - a.SignDate) z "
		 		+" from lccont a  , lccontprint b" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and a.contno = b.contno "
		 		+ ReportPubFun.getWherePart("b.makedate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else if("I".equals(mStatType))
	     {
	    	 //保单打印日期—回执签收日期
	    	 strsql=" select "+gradeSql+", sum(X .y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(a.customgetpoldate - b.makedate) z "
		 		+" from lccont a  ,lccontprint b " 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.contno = b.contno  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+ ReportPubFun.getWherePart("a.customgetpoldate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else if("J".equals(mStatType))
	     {
	    	 //回执签收日期—回执入机日期
	    	 strsql=" select "+gradeSql+", sum(X .y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select managecom x, " 
		 		+" count(1) y, "
		 		+" sum(a.getpoldate - a.customgetpoldate) z "
		 		+" from lccont a  " 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+ ReportPubFun.getWherePart("a.getpoldate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by managecom ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else if("K".equals(mStatType))
	     {
	    	 //通知书下发日期—通知书打印日期(有疑问不知道逻辑是否正确！)
	    	 strsql=" select "+gradeSql+", sum(X .y), round(sum(X.z) / sum(X.y), 1) from ( " 
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(c.donedate - b.senddate) z "
		 		+" from lccont a  , lcissuepol b , loprtmanager c" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.contno = b.contno "
		 		+" and b.prtseq = c.prtseq "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and exists (select 1 from lcissuepol where contno = a.contno and needprint = 'Y' and backobjtype = '2')  "
		 		+ ReportPubFun.getWherePart("c.donedate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom " 
		 		+" union "
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(c.donedate - b.senddate) z "
		 		+" from lccont a  , lcpenotice b , loprtmanager c" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.contno = b.contno "
		 		+" and b.prtseq = c.prtseq "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and exists (select 1 from lcpenotice where contno = a.contno) "
		 		+ ReportPubFun.getWherePart("c.donedate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom " 
		 		+" union "
				+" select a.managecom x, " 
		 		+" count(1) y, "
		 		+" sum(c.donedate - b.senddate) z "
		 		+" from lccont a  , lcrreport b , loprtmanager c" 
		 		+" where a.grpcontno = '00000000000000000000' "
		 		+" and a.conttype = '1'  "
		 		+" and a.contno = b.contno "
		 		+" and b.prtseq = c.prtseq "
		 		+" and a.selltype != '08' "
	    		+" and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		 		+" and exists (select 1 from lcrreport where contno = a.contno) "
		 		+ ReportPubFun.getWherePart("c.donedate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)	
		 		+ ReportPubFun.getWherePart("a.SaleChnl",ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
		 		+ ReportPubFun.getWherePartLike("a.managecom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		 		+ addSql_state
		 		+" group by a.managecom " 
		 		+" ) X  "
		 		+"where  1=1 group by "+gradeSql+" ";
	     }else{
	    	 logger.debug("统计类型错误！");
	    	 buildError("dealdata", "统计类型错误:" + this.mStatType);
	    	 return false;
	     }
		
		logger.debug("点对点平均时效统计报表:" + strsql);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strsql);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		sqlbv1.put("mState", mState);
		sqlbv1.put("mComGrade", mComGrade);
		sqlbv1.put("mSaleChnl", mSaleChnl);
		sqlbv1.put("mManageCom", mManageCom);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv1);
		if (tSSRS == null || tSSRS.getMaxRow() == 0) {
			buildError("dealdata", "没有要统计的数据!");
			return false;
		}
		
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strArr = new String[4];
			strArr[0] = i + "";  //序号
			strArr[1] = tSSRS.GetText(i,1);  //管理机构
			strArr[2] = tSSRS.GetText(i,2);  //总件数
			strArr[3] = tSSRS.GetText(i,3); //平均时效（日）

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
		
		if(mManageCom == null ||"".equals(mManageCom)){
			buildError("checkData", "管理机构为空！");
			return false;
		}
		if(mStartDate == null ||"".equals(mStartDate)){
			buildError("checkData", "开始日期为空！");
			return false;
		}
		if(mEndDate == null ||"".equals(mEndDate)){
			buildError("checkData", "结束日期为空！");
			return false;
		}
		if(mStatType == null || "".equals(this.mStatType)){
			buildError("checkData", "统计类型为空!");
			return false;
		}
		
	    if(mComGrade != null && "".equals(mComGrade) && mComGrade.length() > 0)
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
		cError.moduleName = "PTPProcessEfficiencyBL";
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

		PTPProcessEfficiencyBL tPTPProcessEfficiencyBL = new PTPProcessEfficiencyBL();
		if (!tPTPProcessEfficiencyBL.submitData(tVData, "PRINT")) {
			int n = tPTPProcessEfficiencyBL.mErrors.getErrorCount();
			}  else {
				logger.debug("数据文件");
		}
	}

}
