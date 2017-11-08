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

public class QuestReplyReportBL {
private static Logger logger = Logger.getLogger(QuestReplyReportBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public  CErrors mErrors = new CErrors();
	private TransferData mTransferData = new TransferData();
	private VData mResult = new VData();
	private XmlExport mXmlExport = null;
	private String mManageCom =    "";
	private String mIssueNoticeType =   "";
	private String mState=      "";
	private String mSaleChnl=      "";
	private String mStartReplyDate= "";
	private String mEndReplyDate=   "";
	private String mComGrade=  "";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public  QuestReplyReportBL() {
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
		mIssueNoticeType =    (String)mTransferData.getValueByName("IssueNoticeType");
		mState=      (String)mTransferData.getValueByName("mState");
		mSaleChnl=      (String)mTransferData.getValueByName("SaleChnl");
		mStartReplyDate= (String)mTransferData.getValueByName("StartReplyDate");
		mEndReplyDate=   (String)mTransferData.getValueByName("EndReplyDate");
		mComGrade=  (String)mTransferData.getValueByName("ComGrade");
	
		logger.debug("管理机构：" + mManageCom);
		logger.debug("问题件类型：" + mIssueNoticeType);
		logger.debug("保单类型：" + mState);
		logger.debug("销售渠道：" + mSaleChnl);
		logger.debug("问题件回复开始日期：" + mStartReplyDate);
		logger.debug("问题件回复结束日期："+mEndReplyDate);
		logger.debug("统计粒度："+mComGrade);

		return true;
	}
	private boolean dealData() {
		
		mXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		mXmlExport.createDocument("QuestReplyReport.vts", "");
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ListTable tlistTable = new ListTable();
		tlistTable.setName("QuestReply");
		String[] strArr = null;
		
		tTextTag.add("ReportPeriod", BqNameFun.getChDate(mStartReplyDate)+ "至" + BqNameFun.getChDate(mEndReplyDate));
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		tTextTag.add("ReportDate", df.format(new Date()));
		mXmlExport.addTextTag(tTextTag);
		
		// 定义列表标题
		String[] Title = new String[3];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		
		   String gradeSql =" X.managecom ";
		    if(mComGrade != null && mComGrade.length() > 0)
		    {
		    	gradeSql = " substr(X.managecom,1,"+"?mComGrade?"+") ";
		    }
		    
		    String addSql_state = " and 1=1 ";
		    if(mState!= null && !mState.equals(""))
		    {
		    	addSql_state = addSql_state + " and a.prtno like concat(concat('86','?mState?'),'%') ";
		    }
		    
		    String IssueNoticeType_sql =" and  1=1 ";
		    if("01".equals(mIssueNoticeType)){
		    	IssueNoticeType_sql = IssueNoticeType_sql + " and b.backobjtype = '2' and b.standbyflag2 = 'Y' " ;
		    }else if("02".equals(mIssueNoticeType)){
		    	IssueNoticeType_sql = IssueNoticeType_sql + " and b.backobjtype = '2' and b.standbyflag2 = 'N' " ;
		    }else if("03".equals(mIssueNoticeType)){
		    	IssueNoticeType_sql = IssueNoticeType_sql + " and b.backobjtype = '3' " ;
		    }else if("04".equals(mIssueNoticeType)){
		    	IssueNoticeType_sql = IssueNoticeType_sql + " and b.backobjtype = '4' " ;
		    }
		    
		    
		String strsql = " select "+gradeSql+", (case when sum(sumdate)/sum(sumcount) is not null then sum(sumdate)/sum(sumcount)  else '0' end)"
				+" from ( select a.managecom managecom,sum(b.replydate - b.senddate) sumdate,count(1) sumcount "
				+" from lccont a, lcissuepol b where a.contno = b.contno and b.backobjtype != '1' " 
				+" and a.selltype != '08' "
				+" and not exists (select 1  from lcpol where contno = a.contno and riskcode in ('141812', '311603', '111603')) "
				+ ReportPubFun.getWherePartLike("a.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				+ IssueNoticeType_sql
				+ addSql_state
				+ ReportPubFun.getWherePart("a.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
				+ ReportPubFun.getWherePart("b.replydate", ReportPubFun.getParameterStr(mStartReplyDate,"?mStartReplyDate?"), ReportPubFun.getParameterStr(mEndReplyDate,"?mEndReplyDate?"), 1)
				+" group by a.managecom "
				+" union "
				+" select aa.managecom managecom,sum(bb.replydate - bb.senddate) sumdate,count(1) sumcount "
				+" from lccont aa ,lcrreport bb where aa.contno = bb.contno "
				+" and aa.selltype != '08' "
				+" and not exists (select 1  from lcpol where contno = aa.contno and riskcode in ('141812', '311603', '111603')) "
				+ ReportPubFun.getWherePartLike("aa.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				+ ReportPubFun.getWherePartLike("06", ReportPubFun.getParameterStr(this.mIssueNoticeType,"?mIssueNoticeType?"))
				+ addSql_state
				+ ReportPubFun.getWherePart("aa.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
				+ ReportPubFun.getWherePart("bb.replydate", ReportPubFun.getParameterStr(mStartReplyDate,"?mStartReplyDate?"), ReportPubFun.getParameterStr(mEndReplyDate,"?mEndReplyDate?"), 1)
				+" group by aa.managecom "
				+" union "
				+" select aaa.managecom managecom," 
//				+" sum((select max(to_date(to_char(outdate,'yyyy-mm-dd'),'yyyy-mm-dd')) from lbmission where missionprop1 =aaa.prtno and activityid = '0000001111') - bbb.senddate) sumdate,"
//				+" sum((select max(outdate) from lbmission where missionprop1 =aaa.prtno and activityid = '0000001111') - bbb.senddate) sumdate,"
				+" sum((select max(outdate) from lbmission where missionprop1 =aaa.prtno and activityid  in (select activityid from lwactivity  where functionid ='10010029')) - bbb.senddate) sumdate,"
				+" count(1) sumcount "
				+" from lccont aaa ,lcpenotice bbb where aaa.contno = bbb.contno "
				+" and aaa.selltype != '08' "
				+" and not exists (select 1  from lcpol where contno = aaa.contno and riskcode in ('141812', '311603', '111603')) "
//				+" and (exists(select 1 from lbmission where missionprop1 = aaa.prtno and activityid = '0000001111'" 
				+" and (exists(select 1 from lbmission where missionprop1 = aaa.prtno and activityid  in (select activityid from lwactivity  where functionid ='10010029') " 
				+ ReportPubFun.getWherePart("lbmission.outdate", ReportPubFun.getParameterStr(mStartReplyDate,"?mStartReplyDate?"), ReportPubFun.getParameterStr(mEndReplyDate,"?mEndReplyDate?"), 1)
				+")) "
				+ ReportPubFun.getWherePartLike("aaa.ManageCom", ReportPubFun.getParameterStr(this.mManageCom,"?mManageCom?"))
				+ ReportPubFun.getWherePartLike("07", ReportPubFun.getParameterStr(this.mIssueNoticeType,"?mIssueNoticeType?"))
				+ addSql_state
				+ ReportPubFun.getWherePart("aaa.SaleChnl", ReportPubFun.getParameterStr(mSaleChnl,"?mSaleChnl?"))
				+" group by aaa.managecom"
				+" ) X group by "+gradeSql+" ";
		
		logger.debug("新契约处理时效统计查询语句:" + strsql);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strsql);
		sqlbv1.put("mManageCom", this.mManageCom);
		sqlbv1.put("mSaleChnl", mSaleChnl);
		sqlbv1.put("mStartReplyDate", mStartReplyDate);
		sqlbv1.put("mEndReplyDate", mEndReplyDate);
		sqlbv1.put("mIssueNoticeType", this.mIssueNoticeType);
		sqlbv1.put("mState", mState);
		sqlbv1.put("mComGrade", mComGrade);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv1);
		if (tSSRS == null || tSSRS.MaxRow == 0) {
			buildError("dealdata", "没有要统计的数据!");
			return false;
		}
		
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strArr = new String[3];
			strArr[0] = i + "";  //序号
			strArr[1] = tSSRS.GetText(i,1);  //管理机构
			strArr[2] = String.valueOf(PubFun.round(Double.parseDouble(tSSRS.GetText(i,2)), 1));  //问题件平均回复时效（日）

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
		if("".equals(mStartReplyDate)){
			buildError("checkData", "问题件回复开始日期为空！");
			return false;
		}
		if("".equals(mEndReplyDate)){
			buildError("checkData", "问题件回复结束日期为空！");
			return false;
		}
		
		return true;
	}
	public VData getResult() {
		return this.mResult;
	}
	
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "tQuestReplyReportBL";
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
		tTransferData.setNameAndValue("mState","");
		tTransferData.setNameAndValue("SaleChnl","");
		tTransferData.setNameAndValue("StartReplyDate","2010-05-30");
		tTransferData.setNameAndValue("EndReplyDate","2010-06-04");
		tTransferData.setNameAndValue("ComGrade","");
//		tTransferData.setNameAndValue("CleanPolFlag","1");
//		tTransferData.setNameAndValue("StatType","15");
		
		
		VData tVData = new VData();
		VData mResult = new VData();
		
		tVData.add(tTransferData);
		tVData.addElement(tG);

		QuestReplyReportBL tQuestReplyReportBL = new QuestReplyReportBL();
		if (!tQuestReplyReportBL.submitData(tVData, "PRINT")) {
			int n = tQuestReplyReportBL.mErrors.getErrorCount();
			}  else {
				logger.debug("数据文件");
		}
	}

}
