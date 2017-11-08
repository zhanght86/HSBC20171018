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
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: PolAppStatBL.java
 * </p>
 * <p>
 * Description: 承保报表统计
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Fuqx
 * @version 1.0
 */

public class PolAppTimeStatBL {
private static Logger logger = Logger.getLogger(PolAppTimeStatBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGI = new GlobalInput();
	private String mStartDate; // 保单生效开始日期
	private String mEndDate; // 保单生效结束日期
	private String mStartTime; // 保单生效开始时间
	private String mEndTime; // 保单生效结束时间
	private String mRiskCode; // 险种编码
	private String mReportType; // 统计报表类型
	private String mTitle;
	private String mSubTitle = ""; // 子标题
	private int mMaxColCount = 0; // 最大列数
	private String mManageCom;
	DecimalFormat mDecimalFormat = new DecimalFormat("0.0000");

	public PolAppTimeStatBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate))
			return false;

		if (!checkData())
			return false;

		if (!dealData())
			return false;

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			CError.buildErr(this, "不支持的操作类型!") ;
			return false;
		}
		mGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		if (mGI == null || mGI.ManageCom == null) {
			CError.buildErr(this, "管理机构为空!") ;
			return false;
		}
		mManageCom = mGI.ManageCom;
		mStartDate = (String) cInputData.get(0);
		mEndDate = (String) cInputData.get(1);
		mReportType = (String) cInputData.get(2);
		mStartTime = (String) cInputData.get(3);
		mEndTime = (String) cInputData.get(4);
		if (mStartDate == null || mEndDate == null
				|| mReportType == null || mStartTime == null || mEndTime == null) {
			CError.buildErr(this, "数据传输失败!") ;
			return false;
		}
		return true;
	}

	// 用tSpitStr分割字符串tOriginStr，
	private String splitCat(String tOriginStr, String tSpitStr) {
		String tResultStr = "";
		// 传入为空时，直接返回空字符串
		if (tOriginStr != null && !"".equals(tOriginStr)) {
			if (tOriginStr.indexOf(tSpitStr) != -1) {
				String[] tBackArray = tOriginStr.split(tSpitStr);
				tResultStr = tBackArray[0];
				for (int i = 1; i < tBackArray.length; i++) {
					tResultStr = tResultStr + "','" + tBackArray[i]; // 要返回01','02','03
				}
			} else {
				tResultStr = tOriginStr;
			}
		}
		logger.debug("*****splitCat_tResultStr: " + tResultStr);
		return tResultStr;
	}

	private boolean checkData() {	
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tReportSQL = "select codealias,comcode,othersign from ldcode where codetype='timreporttype' and code= '"
				+ "?mReportType?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tReportSQL);
		sqlbv1.put("mReportType", mReportType);
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS == null || tSSRS.getMaxRow() < 1) {
			CError.buildErr(this, "报表类型错误!") ;
			return false;
		}

		mTitle = tSSRS.GetText(1, 1); // 报表名称
		mSubTitle = tSSRS.GetText(1, 2); //
		mMaxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
		logger.debug("***mTitle: " + mTitle);
		logger.debug("***mSubTitle: " + mSubTitle);
		logger.debug("***mMaxColCount: " + mMaxColCount);

		return true;
	}	

	/*
	 * Tim01 业务处理时效表
	 */
	private boolean PrepareDataTim01() {
		try {			
//			String tSql = "select ind ,indname,prtnum, yy  "
//				    + " from ("
//						+ " select count(prt) prtnum,sum(xx)  yy,'1' ind,	'清洁件' indname "//清洁件
//						+ " from (select distinct (prtno) prt,(select to_date(b.makedate) - to_date(max(makedate)) from es_doc_main where doccode = a.prtno"
//						+ " and busstype='TB' and subtype = 'UA001') xx " 
//						+ " from lccont a,lccuwmaster b "
//						+ " where  a.conttype='1' "
//						+ " and a.contno=b.contno "
//						//+ " and a.grpcontno='00000000000000000000'"
//						+ " and a.managecom >='"
//						+ PubFun.RCh(mManageCom, "0", 8)
//						+ "' "
//						+ " and a.managecom <='"
//						+ PubFun.RCh(mManageCom, "9", 8)
//						+ "' "
//						+ " and uwflag in ('1','2','4','9','a') "
//						+ " and not exists (select '1' from lccuwsub where passflag='8' and contno=a.contno ) "//未发过核保通知书
//						+ " and not exists (select '1' from lwmission where activityid='0000001134' and missionprop2=a.contno "
//						+ " union all select '1' from lbmission where activityid='0000001134' and processid='0000000003' and missionprop2=a.contno) "//未曾呈报再保
//						+ " and not exists (select '1' from lwmission where activityid='0000001100' and missionprop2=a.contno and missionprop18='6' "
//						+ " union all select '1' from lbmission where activityid='0000001100' and processid='0000000003' and missionprop2=a.contno and missionprop18='6')"//未曾核保等待
//						+ " and exists (select '1' from es_doc_main where doccode=a.prtno and busstype='TB' and subtype = 'UA001') "//有扫描件
//						//+ " and (uwdate||' '||uwtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"') "//核保日期
//						//+ " and (uwdate||' '||uwtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"') "
//						//tongmeng 2009-11-16 modify
//						//核保日期修改为取核保通过日期
//						+ PubFun.DateTimeBetween("b.modifydate","b.modifytime",mStartDate,mStartTime,mEndDate,mEndTime) //核保日期
//						+ " and exists(select '1' from lcpol where contno=a.contno"
//						+ " and grpcontno='00000000000000000000'"
//						+ " and mainpolno=polno and renewcount=0)) "
//						+ " union "//体检件
//						+ " select count(prt) prtnum,sum(xx)  yy,'1' ind,	'体检件' indname "
//						+ " from (select distinct (prtno) prt,(select to_date(b.makedate) - to_date(max(makedate)) from es_doc_main where doccode = a.prtno"
//						+ " and busstype='TB'"
//						+ " and subtype = 'UA001') xx " 
//						+ " from lccont a,lccuwmaster b "
//						+ " where  a.conttype='1' "
//						//+ " and grpcontno='00000000000000000000'"
//						+ " and a.contno=b.contno "
//						+ " and a.managecom >='"
//						+ PubFun.RCh(mManageCom, "0", 8)
//						+ "' "
//						+ " and a.managecom <='"
//						+ PubFun.RCh(mManageCom, "9", 8)
//						+ "' "
//						+ " and uwflag in ('1','2','4','9','a') "
//						+ " and exists (select '1' from lcpenotice where printflag='0' and contno=a.contno ) "//有发送过体检件					
//						+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
////						+ " and (uwdate||' '||uwtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"') "//核保日期
////						+ " and (uwdate||' '||uwtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"') "
//						+ PubFun.DateTimeBetween("b.modifydate","b.modifytime",mStartDate,mStartTime,mEndDate,mEndTime) //核保日期
//						+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno"
//						+ " and grpcontno='00000000000000000000'"
//						+ " and renewcount=0)) "
//						+ " union "//生调件
//						+ " select count(prt) prtnum,sum(xx)  yy,'1' ind,	'生调件' indname "
//						+ " from (select distinct (prtno) prt,(select to_date(b.makedate) - to_date(max(makedate)) from es_doc_main where doccode = a.prtno"
//						+ " and busstype='TB'"
//						+ " and subtype = 'UA001') xx " 
//						+ " from lccont a,lccuwmaster b "
//						+ " where  a.conttype='1' "
//						//+ " and grpcontno='00000000000000000000'"
//						+ " and a.contno=b.contno "
//						+ " and a.managecom >='"
//						+ PubFun.RCh(mManageCom, "0", 8)
//						+ "' "
//						+ " and a.managecom <='"
//						+ PubFun.RCh(mManageCom, "9", 8)
//						+ "' "
//						+ " and uwflag in ('1','2','4','9','a') "
//						+ " and exists (select '1' from lcrreport where replyflag='0' and contno=a.contno ) "//有发送过生调件
//						+ " and exists (select '1' from es_doc_main where doccode=a.prtno "
//						+ " and busstype='TB'"
//						+ " and subtype = 'UA001') "//有扫描件
//						+ PubFun.DateTimeBetween("b.modifydate","b.modifytime",mStartDate,mStartTime,mEndDate,mEndTime) //核保日期
//						+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno"
//						+ " and grpcontno='00000000000000000000'"
//						+" and renewcount=0)) "
//					+ " ) d "
//					+ "order by d.ind ";
			String tSql = "select '1' aa, '清洁件' bb, count(prtno) cc, sum(DD) dd"
						+ " from (select ((select max(makedate) from lcuwsub where contno = a.contno) -"
						+ " (select max(makedate) from es_doc_main where doccode = a.prtno"
						+ " and subtype = 'UA001')) DD,prtno from lccont a where exists"
						+ " (select 1 from es_doc_main b where a.prtno = doccode"
						+ " and (('"+"?mStartDate?"+"' = '"+"?mEndDate?"+"' and b.maketime > '"+"?mStartTime?"+"' and"
						+ " b.maketime <= '"+"?mEndTime?"+"') or ('"+"?mStartDate?"+"' < '"+"?mEndDate?"+"' and"
			            + " ((b.makedate > '"+"?mStartDate?"+"' and b.makedate < '"+"?mEndDate?"+"') or"
			            + " (b.makedate = '"+"?mStartDate?"+"' and b.maketime >= '"+"?mStartTime?"+"') or"
			            + " (b.makedate = '"+"?mEndDate?"+"' and b.maketime <= '"+"?mEndTime?"+"'))))"
						+ " and subtype = 'UA001') and not exists (select 1"
						+ " from lcissuepol where contno = a.contno and needprint = 'Y')"
						+ " and not exists (select 1 from lcpenotice where contno = a.contno)"
						+ " and not exists (select 1 from lcrreport where contno = a.contno)"
						+ " and not exists (select 1 from lccspec where contno = a.contno)"
						+ " and not exists (select 1 from lcprem where payplancode like '000000%'"
						+ " and contno = a.contno)"
						+ " and a.uwflag='9'"
						+ ") t"
						+ " union all"
						+ " select '1' aa, '体检件' bb, count(prtno) cc, sum(DD) dd"
						+ " from (select ((select max(makedate) from lcuwsub where contno = a.contno) -"
						+ " (select max(makedate) from es_doc_main where doccode = a.prtno"
						+ " and subtype = 'UA001')) DD,prtno from lccont a where exists"
						+ " (select 1 from es_doc_main b where a.prtno = doccode"
						+ " and (('"+"?mStartDate?"+"' = '"+"?mEndDate?"+"' and b.maketime > '"+"?mStartTime?"+"' and"
						+ " b.maketime <= '"+"?mEndTime?"+"') or ('"+"?mStartDate?"+"' < '"+"?mEndDate?"+"' and"
			            + " ((b.makedate > '"+"?mStartDate?"+"' and b.makedate < '"+"?mEndDate?"+"') or"
			            + " (b.makedate = '"+"?mStartDate?"+"' and b.maketime >= '"+"?mStartTime?"+"') or"
			            + " (b.makedate = '"+"?mEndDate?"+"' and b.maketime <= '"+"?mEndTime?"+"'))))"
						+ " and subtype = 'UA001') and not exists (select 1 from lcissuepol"
						+ " where contno = a.contno and needprint = 'Y')"
						+ " and exists (select 1 from lcpenotice where contno = a.contno)"
						+ " and not exists (select 1 from lcrreport where contno = a.contno)"
						+ " and not exists (select 1 from lccspec where contno = a.contno)"
						+ " and not exists (select 1 from lcprem where payplancode like '000000%'"
						+ " and contno = a.contno)"
						+ " and a.uwflag in ('4','9')) tt"
						+ " union all"
						+ " select '1' aa, '生调件' bb, count(prtno) cc, sum(DD) dd"
						+ " from (select ((select max(makedate) from lcuwsub where contno = a.contno) -"
						+ " (select max(makedate) from es_doc_main where doccode = a.prtno"
						+ " and subtype = 'UA001')) DD,prtno from lccont a where exists"
						+ " (select 1 from es_doc_main b where a.prtno = doccode"
						+ " and (('"+"?mStartDate?"+"' = '"+"?mEndDate?"+"' and b.maketime > '"+"?mStartTime?"+"' and"
						+ " b.maketime <= '"+"?mEndTime?"+"') or ('"+"?mStartDate?"+"' < '"+"?mEndDate?"+"' and"
			            + " ((b.makedate > '"+"?mStartDate?"+"' and b.makedate < '"+"?mEndDate?"+"') or"
			            + " (b.makedate = '"+"?mStartDate?"+"' and b.maketime >= '"+"?mStartTime?"+"') or"
			            + " (b.makedate = '"+"?mEndDate?"+"' and b.maketime <= '"+"?mEndTime?"+"'))))"
						+ " and subtype = 'UA001') and not exists (select 1 from lcissuepol"
						+ " where contno = a.contno and needprint = 'Y') and not exists"
						+ " (select 1 from lcpenotice where contno = a.contno)"
						+ " and exists (select 1 from lcrreport where contno = a.contno)"
						+ " and not exists (select 1 from lccspec where contno = a.contno)"
						+ " and not exists (select 1 from lcprem where payplancode like '000000%'"
						+ " and contno = a.contno)"
						+ " and uwflag in ('4','9')) l";
			logger.debug("tSql: " + tSql);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("mStartDate", mStartDate);
			sqlbv2.put("mEndDate", mEndDate);
			sqlbv2.put("mEndTime", mEndTime);
			sqlbv2.put("mStartTime", mStartTime);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv2);
			int row = tSSRS.getMaxRow();
			String[] strArr = new String[mMaxColCount];
			String StringArray[][] = new String[row][mMaxColCount];
			int m;
			for (m = 0; m < tSSRS.getMaxRow(); m++) {
					StringArray[m][0] = tSSRS.GetText(m + 1, 2);
					if(Integer.parseInt(tSSRS.GetText(m + 1, 3))!=0)
						StringArray[m][1] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(m + 1, 4))* 0.6849/Integer.parseInt(tSSRS.GetText(m + 1, 3)));
					else
						StringArray[m][1] = "0.0000";
			}			

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("AppTim01.vts", "printer");
			ListTable alistTable = new ListTable();
			alistTable.setName("INFO");

			for (int i = 0; i < row; i++) {
				strArr = new String[mMaxColCount];
				strArr = StringArray[i];
				alistTable.add(strArr);
			}

			xmlexport.addDisplayControl("displayinfo");
			xmlexport.addListTable(alistTable, strArr);
			texttag.add("SysDate", PubFun.getCurrentDate());
			texttag.add("StartDate", mStartDate+" "+mStartTime);
			texttag.add("EndDate", mEndDate+" "+mEndTime);
			texttag.add("Title", mTitle);
			texttag.add("SubTitle", mSubTitle);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常Tim01！") ;
			return false;
		}
		return true;
	}

	/*
	 * Tim02 机构体检状况一览表
	 */
	private boolean PrepareDataTim02() {
		try {
			String tSql =" select managecom,(select shortname from ldcom where comcode=d.managecom) name,count(prt),sum(xx) "
				+ " from "
				+ " (select distinct (prtno) prt,(select datediff(to_date(min(outdate),'yyyy-mm-dd'),to_date(min(indate),'yyyy-mm-dd')) from lbmission where activityid in (select activityid from lwactivity where functionid='10010005') and missionprop1=a.contno) xx,managecom " 
					+ " from lccont a "
					+ " where  a.conttype='1' "
					+ " and a.managecom >='"
					+ "?mManageCom0?"
					+ "' "
					+ " and a.managecom <='"
					+ "?mManageCom9?"
					+ "' "
					+ " and exists (select min(outdate) from lbmission where activityid in(select activityid from lwactivity where functionid='10010005') and missionprop1=a.contno "
//					+ " and (outdate || ' ' || outtime) >=('" + mStartDate + "'||' '||'"+ mStartTime +"') "//第一次核保动作日期
//					+ " and (outdate || ' ' || outtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"'))"//有第一次核保动作 备份自核工作流的最小时间
					+ PubFun.DateTimeBetween("outdate","outtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?")) //第一次核保动作日期
					+ ") "
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件					
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "					
				+ " ) d group by d.managecom ";						
		logger.debug("tSql: " + tSql);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
		sqlbv3.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
		sqlbv3.put("mStartDate", mStartDate);
		sqlbv3.put("mStartTime", mStartTime);
		sqlbv3.put("mEndDate", mEndDate);
		sqlbv3.put("mEndTime", mEndTime);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv3);
		int row = tSSRS.getMaxRow();
		String[] strArr = new String[mMaxColCount];
		String StringArray[][] = new String[row][mMaxColCount];
		int m;
		for (m = 0; m < tSSRS.getMaxRow(); m++) {
				StringArray[m][0] = tSSRS.GetText(m + 1, 1);
				StringArray[m][1] = tSSRS.GetText(m + 1, 2);
				if(Integer.parseInt(tSSRS.GetText(m + 1, 3))!=0)
					StringArray[m][2] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(m + 1, 4))* 0.6849 * 250 / 365 /Integer.parseInt(tSSRS.GetText(m + 1, 3)));
				else
					StringArray[m][2] = "0.0000";
		}			

		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
		xmlexport.createDocument("AppTim02.vts", "printer");
		ListTable alistTable = new ListTable();
		alistTable.setName("INFO");

		for (int i = 0; i < row; i++) {
			strArr = new String[mMaxColCount];
			strArr = StringArray[i];
			alistTable.add(strArr);
		}

		xmlexport.addDisplayControl("displayinfo");
		xmlexport.addListTable(alistTable, strArr);
		texttag.add("SysDate", PubFun.getCurrentDate());
		texttag.add("StartDate", mStartDate+" "+mStartTime);
		texttag.add("EndDate", mEndDate+" "+mEndTime);
		texttag.add("Title", mTitle);
		texttag.add("SubTitle", mSubTitle);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常Tim02！") ;
			return false;
		}
		return true;
	}	

	private boolean dealData() {
		try {
			/**
			 * 对以下几个报表数据准备
			 */			
			// Tim01 各险种预收报表
			if (mReportType.equals("Tim01")) {
				if (!PrepareDataTim01())
					return false;
			}
			// Tim02 机构承保情况表
			else if (mReportType.equals("Tim02")) {
				if (!PrepareDataTim02())
					return false;
			}			
			else {
				CError.buildErr(this, "报表类型错误！");
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常！") ;
			return false;

		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		PolAppTimeStatBL PolAppTimeStatBL1 = new PolAppTimeStatBL();
		VData tVData = new VData();
		VData mResult = new VData();
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";
		tG.ComCode = "86";
		String StartDate = "2005-1-1";
		String EndDate = "2005-1-31";
		String ManageType = "3";
		String SalechnlType = "2";
		String ReportType = "1";
		tVData.addElement(StartDate);
		tVData.addElement(EndDate);
		tVData.addElement(ReportType);
		tVData.addElement(tG);
		PolAppTimeStatBL1.submitData(tVData, "PRINT");
	}
}
