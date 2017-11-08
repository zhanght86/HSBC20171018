package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.pubfun.ZHashReport;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.vschema.LDComSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
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

public class PolAppStatBL {
private static Logger logger = Logger.getLogger(PolAppStatBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGI = new GlobalInput();
	private String mStartDate; // 保单生效开始日期
	private String mEndDate; // 保单生效结束日期
	private String mStartTime; // 保单生效开始时间
	private String mEndTime; // 保单生效结束时间
	private String mRiskCode; // 险种编码
	private String mManageType = "2"; // 管理机构的类型，如省级分公司(管理机构为4位,如8611)为2，省级公司下的分公司(861100)为3,营销服务部(86110000)为4
	private String mSalechnlType; // 销售渠道类型
	private String mReportType; // 统计报表类型
	private int mManageLen; // 记录管理机构的长度 根据管理机构的类型(mManageType)
	// mManageLen=2*mManageType
	private String mSalechnlSql; // 销售渠道相关SQL
	private String mManageComSql; // 管理机构相关SQL
	private String mSalechnlName; // 销售渠道名称
	private String mTitle;
	private String mSubTitle = ""; // 子标题
	private int mMaxColCount = 0; // 最大列数
	private String mManageCom;
	DecimalFormat mDecimalFormat = new DecimalFormat("0.0000");
	private ArrayList<String> listArray1 =new ArrayList<String>();  //绑定变量用
	public PolAppStatBL() {
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
		mManageType = (String) cInputData.get(2);
		mSalechnlType = (String) cInputData.get(3);
		mReportType = (String) cInputData.get(4);
		mStartTime = (String) cInputData.get(5);
		mEndTime = (String) cInputData.get(6);
		mRiskCode = (String) cInputData.get(7);
		if (mStartDate == null || mEndDate == null || mManageType == null
				|| mSalechnlType == null || mReportType == null || mStartTime == null || mEndTime == null || mRiskCode == null) {
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
		// 1.校验机构类型
		if (!"2".equals(mManageType) && !"3".equals(mManageType)
				&& !"4".equals(mManageType)&& !"S".equals(mManageType)&& !"N".equals(mManageType)) {
			CError.buildErr(this, "机构类型错误!") ;
			return false;
		} else {			
			if ("2".equals(mManageType)) {
				mTitle = "二级机构";
				mManageLen = 2 * Integer.parseInt(mManageType);
			}
			else if ("3".equals(mManageType)) {
				mTitle = "三级机构";
				mManageLen = 2 * Integer.parseInt(mManageType);
			}
			else if ("4".equals(mManageType)) {
				mTitle = "四级机构";
				mManageLen = 2 * Integer.parseInt(mManageType);
			}
			else if ("S".equals(mManageType)) {
				mTitle = "南区";
				mManageLen = 4;
			}
			else if ("N".equals(mManageType)) {
				mTitle = "北区";
				mManageLen = 4;
			}			
		}
		logger.debug("***mManageLen: " + mManageLen);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		if("N".equals(mManageType)||"S".equals(mManageType))
		{
			//南区北区 managecom between '8600' and '8699'	
			mManageComSql = " in(";
			tSSRS = new SSRS();
			String sql1 ="select code from ldcode where codetype ='regionmanagecom' and othersign='"+"?othersign?"+"'" ;
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql1);
			sqlbv1.put("othersign",mManageType.toLowerCase());
			tSSRS = tExeSQL.execSQL(sqlbv1);
			for(int i=1;i<=tSSRS.getMaxRow();i++)
			{
				listArray1.add(tSSRS.GetText(i, 1));
//				if(i>1)
//					mManageComSql = mManageComSql + ",";
//				mManageComSql = mManageComSql + "'"+tSSRS.GetText(i, 1)+"'";
			}					
			
			mManageComSql = mManageComSql + "'?listArray1?') ";
		}  
		else
			mManageComSql = " ";
		
		logger.debug("***mManageComSql: " + mManageComSql);

		// 2。校验销售渠道类型
		tExeSQL = new ExeSQL();
		tSSRS = new SSRS();
		String tSQL = "select codename,codealias from ldcode where codetype='appsalechnl' and code= '"
				+ "?mSalechnlType?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("mSalechnlType",mSalechnlType);
		tSSRS = tExeSQL.execSQL(sqlbv2);
		if (tSSRS == null || tSSRS.getMaxRow() < 1) {
			CError.buildErr(this, "销售渠道类型错误!") ;
			return false;
		}

		logger.debug("***mSalechnl: " + tSSRS.GetText(1, 1) + "   "
				+ tSSRS.GetText(1, 2));
		mSalechnlName = tSSRS.GetText(1, 1);
		mSalechnlSql = " and SaleChnl = '"
				//+ splitCat(tSSRS.GetText(1, 2), "/") 
				+ "?mSalechnlType?" 
				+ "' ";
		if(mSalechnlType.equals("12"))
			mSalechnlSql = " and SaleChnl in ('05','06','08') ";
		else if(mSalechnlType.equals("13"))
			mSalechnlSql = " and SaleChnl in ('01','02','09','10','11') ";
			
		logger.debug("***mSalechnlType: " + mSalechnlType);
		logger.debug("***mSalechnlSql: " + mSalechnlSql);
		logger.debug("***mTitle: " + mTitle);		
		
		tExeSQL = new ExeSQL();
		tSSRS = new SSRS();
		String tReportSQL = "select codealias,comcode,othersign from ldcode where codetype='appreporttype' and code= '"
				+ "?mReportType?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tReportSQL);
		sqlbv3.put("mReportType",mReportType);
		tSSRS = tExeSQL.execSQL(sqlbv3);
		if (tSSRS == null || tSSRS.getMaxRow() < 1) {
			CError.buildErr(this, "报表类型错误!") ;
			return false;
		}

		mTitle = mTitle + tSSRS.GetText(1, 1); // 报表名称
		mSubTitle = tSSRS.GetText(1, 2); //
		mMaxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
		logger.debug("***mTitle: " + mTitle);
		logger.debug("***mSubTitle: " + mSubTitle);
		logger.debug("***mMaxColCount: " + mMaxColCount);

		return true;
	}

	/**
	 * 对以下几个报表数据准备（共性：共有四列，分别为：机构代码，机构名称，**件数，**保费） **表示具体的报表，如预收件数和预收保费 App01
	 * 机构预收报表 App01 机构期交承保情况表 App05 机构犹豫期撤单表 App06 机构出单前撤单表 App07 机构延期情况表 App08
	 * 机构拒保情况表 App09 
	 */
	private boolean commPrepareData() {
		try {			
			int m; // 管理机构的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LDCom where char_length(trim(ComCode))="
					+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
					+ "?value1?"
					+ "' and comcode <='"
					+ "?value2?"+"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
//			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
//			sqlbv.put(mManageComSqlsqlbv);
//			sqlbv.sql(tsql);
//			sqlbv.put("", values);
			logger.debug("Comcode_SQL: " + tsql);
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tsql);
			sqlbv4.put("mManageLen",mManageLen);
			sqlbv4.put("value1",PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv4.put("value2",PubFun.RCh(mManageCom, "9", mManageLen));
			sqlbv4.put("listArray1",listArray1);
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv4));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + tLDComSet.size());

			strArr = new String[mMaxColCount];
			String StringArray[][] = new String[row][mMaxColCount];
			Hashtable tHashtable = new Hashtable();
			for (m = 0; m < tLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(tLDComSet.get(m + 1));
				logger.debug(tLDComSchema.getComCode() + ":"
						+ tLDComSchema.getShortName());
				StringArray[m][0] = tLDComSchema.getComCode();
				StringArray[m][1] = tLDComSchema.getShortName();
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			StringArray[m][0] = "  8  6  ";
			StringArray[m][1] = " 总 公 司 ";

			// 初始化二维数组中的值
			for (int i = 0; i < row; i++) {
				for (int j = 2; j < 4; j++) {
					if (j == 2) {
						StringArray[i][j] = "0"; // 件数
					} else {
						StringArray[i][j] = "0.0000"; // 保费
					}
				}
			}

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tSQL = "";

			// App01 机构预收报表
			if (mReportType.equals("App01")) {
				tSQL = "select substr(managecom,1,"
						+ "?mManageLen?"
						+ ") submanage,count(distinct contno) countPol,sum(prem)/10000 sumprem  from "
						+ "(select managecom,contno "
						+ ",(select sum(prem) from lcpol where contno = lccont.contno and renewcount=0) prem "
						+ "from lccont "
						+ "where conttype='1' "  
						//+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ mSalechnlSql 
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and busstype='TB' and subtype = 'UA001' "//有扫描件
//						+ " and (( makedate='"+mStartDate+"' and maketime >='"+mStartTime+"') "
//						+ " or (makedate='"+mEndDate+"' and maketime<='"+mEndTime+"')"
//						+ " or (makedate >'"+mStartDate+"' and makedate<'"+mEndDate+"'))"
						+ PubFun.DateTimeBetween("makedate","maketime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
						//+ "and (makedate||' '||maketime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"') "
						//+ "and (makedate||' '||maketime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')"
						+ " ) "
//						+ "and makedate>='" + mStartDate + "' "
//						+ "and makedate<='" + mEndDate + "'"						
						+ "and managecom >='" + "?value2?"
						+ "' " + "and managecom <='"
						+ "?value1?" + "' "
						+ tManageComSql
						+ ") group by substr(managecom,1," + "?mManageLen?" + ")";
			}			

			// App05 机构期交承保情况表
			if (mReportType.equals("App05")) {
				tSQL = "select substr(managecom,1,"
						+ "?mManageLen?"
						+ ") submanage,count(distinct contno) countPol,sum(prem)/10000 sumprem  from "
						+ "(select managecom,contno"
						+ ",(select sum(( case lccont.payintv when '1' then prem/0.09 when '3' then prem/0.27 when '6' then prem/0.52 when '12' then prem end)) from lcpol where contno = lccont.contno) prem "
						+ " from lccont  "
						+ " where conttype='1' and appflag='1' and payintv>0 "
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' ) "//有扫描件
						+ mSalechnlSql 
//						+ " and (( signdate='"+mStartDate+"' and signtime >='"+mStartTime+"') "
//						+ " or (signdate='"+mEndDate+"' and signtime<='"+mEndTime+"')"
//						+ " or (signdate >'"+mStartDate+"' and signtime<'"+mEndDate+"'))"
						+ PubFun.DateTimeBetween("signdate","signtime",ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mStartTime, "?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
						//+ " and (signdate||' '||signtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"')"
						//+ " and (signdate||' '||signtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')"
						+ " and managecom >='" + "?values2?"
						+ "' " + "and managecom <='"
						+ "?value1?" + "' "
						+ tManageComSql
						+ ") group by substr(managecom,1," + "?mManageLen?" + ")";
			}
			
			// App06 机构犹豫期撤单表
			if (mReportType.equals("App06")) {
				tSQL = "select substr(managecom,1,"
						+ "?mManageLen?"
						+ ") submanage,count(distinct contno) countPol,sum(prem)/10000 sumprem  from "
						+ "(select managecom,contno"
						+ ",(select sum(prem) from lcpol where contno = lccont.contno) prem "
						+ "from lccont "
						+ "where conttype='1' and appflag='4' "
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
						+ mSalechnlSql
						+ "and exists(select 1 from lpedoritem m where contno=lccont.contno and edortype='WT' and EdorState='0' "
						+ " and exists (select 1 from lpedormain n where n.edorno=m.edorno and n.contno=m.contno "
						+ " and n.confdate >= '"+ "?mStartDate?" + "'"
						+ " and n.confdate <= '" + "?mEndDate?" + "')"
						//+ " and edorvalidate>='"+ mStartDate + "'"
						//+ " and edorvalidate<='" + mEndDate + "'"
						+ ") " + "and managecom >='"
						+ "?value2?" + "' "
						+ "and managecom <='" + "?value1?"+ "' "
						+ tManageComSql
						+ ") group by substr(managecom,1," + "?mManageLen?"
						+ ")";
			}
			// App07 机构出单前撤单表
			if (mReportType.equals("App07")) {
				tSQL = "select substr(managecom,1,"
						+ "?mManageLen?"
						+ ") submanage,count(distinct contno) countPol,sum(prem)/10000 sumprem  from "
						+ "(select managecom,contno"
						+ ",(select sum(prem) from lcpol where contno = lccont.contno) prem "
						+ "from lccont "
						+ "where conttype='1' and appflag='0' and uwflag='a'  "
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
						+ mSalechnlSql
//						+ "and exists(select 1 from lccuwmaster where ProposalcontNo=lccont.ProposalcontNo and passflag='a' and modifydate>='"
//						+ mStartDate + "' " + "and modifydate<='" + mEndDate
//						+ "') " 
						+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mStartTime, "?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
						//+ " and (uwdate||' '||uwtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"')"
						//+ " and (uwdate||' '||uwtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')"
						+ " and managecom >='"
						+ "?value2?" + "' "
						+ " and managecom <='" + "?value1?" + "' " 
						+ tManageComSql
						+ ") group by substr(managecom,1," + mManageLen
						+ ")";
			}
			
			// App08 机构延期情况表
			if (mReportType.equals("App08")) {
				tSQL = "select substr(managecom,1,"
						+ "?mManageLen?"
						+ ") submanage,count(distinct contno) countPol,sum(prem)/10000 sumprem  from "
						+ "(select managecom,contno"
						+ ",(select sum(prem) from lcpol where contno = lccont.contno) prem "
						+ " from lccont "
						+ " where conttype='1' and appflag='0' and uwflag='2' " // 延期
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
						+ mSalechnlSql
//						+ "and exists(select 1 from lccuwmaster where ProposalcontNo=lccont.ProposalcontNo and passflag='2' and modifydate>='"
//						+ mStartDate + "' " + "and modifydate<='" + mEndDate
//						+ "') " 
						+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mStartTime, "?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
						//+ " and (uwdate||' '||uwtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"')"
						//+ " and (uwdate||' '||uwtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')"
						+ " and managecom >='"
						+ "?value2?" + "' "
						+ " and managecom <='" + "?value1?"	+ "' "
						+ tManageComSql
						+ ") group by substr(managecom,1," + "?mManageLen?"
						+ ")";
			}
			
			// App09 机构拒保情况表
			if (mReportType.equals("App09")) {
				tSQL = "select substr(managecom,1,"
						+ "?mManageLen?"
						+ ") submanage,count(distinct contno) countPol,sum(prem)/10000 sumprem  from "
						+ "(select managecom,contno"
						+ ",(select sum(prem) from lcpol where contno = lccont.contno) prem "
						+ " from lccont "
						+ " where conttype='1' and appflag='0' and uwflag='1' " // 拒保
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
						+ mSalechnlSql
//						+ "and exists(select 1 from lccuwmaster where ProposalcontNo=lccont.ProposalcontNo and passflag='1' and modifydate>='"
//						+ mStartDate + "' " + "and modifydate<='" + mEndDate
//						+ "') " 
						+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mStartTime, "?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
						//+ " and (uwdate||' '||uwtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"')"
						//+ " and (uwdate||' '||uwtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')"
						+ " and managecom >='"
						+ "?value2?" + "' "
						+ " and managecom <='" + "?value1?" + "' "
						+ tManageComSql
						+ ") group by substr(managecom,1," + "?mManageLen?"
						+ ")";
			}

			logger.debug("*****SQL: " + tSQL);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL);
			sqlbv5.put("mManageLen",mManageLen); //tManageComSql
			sqlbv5.put("listArray1",listArray1);  //tManageComSql
			sqlbv5.put("mStartDate",mStartDate);
			sqlbv5.put("mStartTime",mStartTime);
			sqlbv5.put("mEndDate",mEndDate);
			sqlbv5.put("mEndTime",mEndTime);
			sqlbv5.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv5.put("value1",PubFun.RCh(mManageCom, "9", 8));
			sqlbv5.put("value2",PubFun.RCh(mManageCom, "0", 8));

			tSSRS = tExeSQL.execSQL(sqlbv5);
			// 合并数据
			for (m = 1; m <= tSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable.get(tSSRS.GetText(m, 1)))
						.intValue();
				StringArray[tIndex][2] = tSSRS.GetText(m, 2);
				StringArray[tIndex][3] = mDecimalFormat.format(Double
						.parseDouble(tSSRS.GetText(m, 3)));
			}

			// 计算合计行各列的值
			for (int j = 2; j < mMaxColCount; j++) {
				for (int i = 0; i < row - 1; i++) {
					if (j == 2) {
						StringArray[row - 1][j] = String.valueOf(Integer
								.parseInt(StringArray[row - 1][j])
								+ Integer.parseInt(StringArray[i][j]));
					}
					if (j == 3) {
						StringArray[row - 1][j] = String.valueOf(Double
								.parseDouble(StringArray[row - 1][j])
								+ Double.parseDouble(StringArray[i][j]));
					}
				}

				if (j == 3) {
					StringArray[row - 1][j] = mDecimalFormat.format(Double
							.parseDouble(StringArray[row - 1][j]));
				}
			}

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例

			xmlexport.createDocument("App01.vts", "printer");

			ListTable alistTable = new ListTable();
			alistTable.setName("INFO");

			for (int i = 0; i < row; i++) {
				strArr = new String[4];
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
			texttag.add("SalechnlName", mSalechnlName);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常"+mReportType+"!") ;
			return false;
		}
		return true;
	}

	/*
	 * App03 机构承保统计表
	 */
	private boolean PrepareDataApp03() {
		try {
			int m; // 管理机构的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LDCom where char_length(trim(ComCode))="
					+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
					+ "?valule2?"
					+ "' and comcode <='"
					+ "?value1?"+"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			logger.debug("Comcode_SQL: " + tsql);
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tsql);
			sqlbv6.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv6.put("listArray1",listArray1);  //tManageComSql;
			sqlbv6.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv6.put("value1",PubFun.RCh(mManageCom, "9", mManageLen));
			sqlbv6.put("value2",PubFun.RCh(mManageCom, "0", mManageLen));
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv6));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + tLDComSet.size());

			strArr = new String[mMaxColCount];
			String StringArray[][] = new String[row][mMaxColCount];
			Hashtable tHashtable = new Hashtable();
			for (m = 0; m < tLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(tLDComSet.get(m + 1));
				logger.debug(tLDComSchema.getComCode() + ":"
						+ tLDComSchema.getShortName());
				StringArray[m][0] = tLDComSchema.getComCode();
				StringArray[m][1] = tLDComSchema.getShortName();
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			StringArray[m][0] = "  8  6  ";
			StringArray[m][1] = " 总 公 司 ";

			// 初始化二维数组中的值
			for (int i = 0; i < row; i++) {
				for (int j = 2; j < mMaxColCount; j++) {
					if (j % 2 == 0) {
						StringArray[i][j] = "0"; // 件数
					} else {
						StringArray[i][j] = "0.0000"; // 保费
					}
				}
			}

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tStandSSRS = new SSRS();
			String tStandSQL = "";
			tStandSQL = "select substr(managecom,1,"
					+ "?mManageLen?"
					+ ") submanage,count(distinct mainpolno) countPol,sum(prem)/10000 sumprem  from "
					+ "(select b.managecom, b.mainpolno "
					+ " ,(select sum(prem) from lcpol a where a.mainpolno=b.mainpolno and a.renewcount=0)prem "
					+ " from lcpol b "
					+ " where b.mainpolno=b.polno "
					+ " and b.conttype='1' and b.renewcount=0 and b.appflag in ('1','4') "
					+ " and exists (select 1 from lccont c where c.contno=b.contno and c.uwflag='9') "								
					+ " and exists (select '1' from es_doc_main where doccode=b.prtno and subtype = 'UA001' )"//有扫描件
					+ mSalechnlSql 
					+ PubFun.DateTimeBetween("b.signdate","b.signtime",ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mStartTime, "?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
					//+ " and (signdate||' '||signtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"')"
					//+ " and (signdate||' '||signtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')"
					+ "and managecom >='"
					+ "?value1?" + "' "
					+ "and managecom <='" + "?value2?"
					+ "' " 
					+ tManageComSql
					+ ") group by substr(managecom,1," + "?mManageLen?"	+ ")";
			SSRS tSecondSSRS = new SSRS();
			String tSecondSQL = "";
			tSecondSQL = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ ") submanage,count(distinct mainpolno) countPol,sum(prem)/10000 sumprem  from "
				+ "(select b.managecom, b.mainpolno "
				+ " ,(select sum(prem) from lcpol a where a.mainpolno=b.mainpolno and a.renewcount=0)prem "
				+ " from lcpol b "
				+ " where b.mainpolno=b.polno "
				+ " and b.conttype='1' and b.renewcount=0 and b.appflag in ('1','4') "
				+ " and exists (select 1 from lccont c where c.contno=b.contno and c.uwflag='4') "								
				+ " and exists (select '1' from es_doc_main where doccode=b.prtno and subtype = 'UA001' )"//有扫描件
				+ mSalechnlSql 
				+ PubFun.DateTimeBetween("b.signdate","b.signtime",ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mStartTime, "?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
				//+ " and (signdate||' '||signtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"')"
				//+ " and (signdate||' '||signtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')"
				+ "and managecom >='"
				+ "?value1?" + "' "
				+ "and managecom <='" + "?value2?"
				+ "' " 
				+ tManageComSql
				+ ") group by substr(managecom,1," + "?mManageLen?"	+ ")";
			logger.debug("*****标准件 tStandSQL: " + tStandSQL);
			logger.debug("*****次标准件 tSecondSQL: " + tSecondSQL);
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tStandSQL);
			sqlbv7.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv7.put("listArray1",listArray1);  //tManageComSql;
			sqlbv7.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv7.put("mStartDate",mStartDate);
			sqlbv7.put("mStartTime",mStartTime);
			sqlbv7.put("mEndDate",mEndDate);
			sqlbv7.put("mEndTime",mEndTime);
			sqlbv7.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv7.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			tStandSSRS = tExeSQL.execSQL(sqlbv7);
			
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tSecondSQL);
			sqlbv8.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv8.put("listArray1",listArray1);  //tManageComSql;
			sqlbv8.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv8.put("mStartDate",mStartDate);
			sqlbv8.put("mStartTime",mStartTime);
			sqlbv8.put("mEndDate",mEndDate);
			sqlbv8.put("mEndTime",mEndTime);
			sqlbv8.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv8.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			tExeSQL = new ExeSQL();
			tSecondSSRS = tExeSQL.execSQL(sqlbv8);
			// 合并标准件数据
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][2] = tStandSSRS.GetText(m, 2);
				StringArray[tIndex][3] = tStandSSRS.GetText(m, 3);
			}
			// 合并次标准件数据
			for (int n = 1; n <= tSecondSSRS.getMaxRow(); n++) {
				int tIndex = ((Integer) tHashtable.get(tSecondSSRS
						.GetText(n, 1))).intValue();
				// logger.debug("index: "+tIndex);
				StringArray[tIndex][4] = tSecondSSRS.GetText(n, 2);
				StringArray[tIndex][5] = tSecondSSRS.GetText(n, 3);
			}

			// 计算合计行各列的值
			for (int j = 2; j < mMaxColCount; j++) {
				for (int i = 0; i < row - 1; i++) {
					if (j % 2 == 0) {
						StringArray[row - 1][j] = String.valueOf(Integer
								.parseInt(StringArray[row - 1][j])
								+ Integer.parseInt(StringArray[i][j]));
					} else {
						StringArray[row - 1][j] = String.valueOf(Double
								.parseDouble(StringArray[row - 1][j])
								+ Double.parseDouble(StringArray[i][j]));
						StringArray[i][j] = mDecimalFormat.format(Double
								.parseDouble(StringArray[i][j]));
					}
				}
				if (j % 2 != 0) {
					StringArray[row - 1][j] = mDecimalFormat.format(Double
							.parseDouble(StringArray[row - 1][j]));
				}
			}

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例

			xmlexport.createDocument("App03.vts", "printer");

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
			texttag.add("SalechnlName", mSalechnlName);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常App03！") ;
			return false;
		}
		return true;
	}

	/*
	 * App04 各险种承保情况表
	 */
	private boolean PrepareDataApp04() {
		try {		
			String tManageComSql = " ";			
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			/*String tSql = "select (select riskname from lmriskapp where riskcode=e.riskcode),riskcode,countall,premall/10000"
				    + ",decode(countall,'0','0',premall/10000/countall) "
				    + " from "
					+ "(select riskcode,count(distinct mainpolno) countall,sum(prem) premall "
					+ ",decode((select subriskflag from lmriskapp where riskcode = d.riskcode),'M','1','0') flag "
					+ " from ( "
					+ "select riskcode,mainpolno,prem " 
					+ "from lcpol a "
					+ "where a.conttype='1' and a.appflag='1' "
					+ mSalechnlSql
					+ "and a.managecom >='"
					+ PubFun.RCh(mManageCom, "0", 8)
					+ "' "
					+ "and a.managecom <='"
					+ PubFun.RCh(mManageCom, "9", 8)
					+ "' "
					+ tManageComSql
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001' )"//有扫描件
					+ PubFun.DateTimeBetween("a.signdate","a.signtime",mStartDate,mStartTime,mEndDate,mEndTime)
					//+ " and (a.signdate||' '||a.signtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"')"
					//+ " and (a.signdate||' '||a.signtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')"
					+ " and a.renewcount=0 "									
					+ " ) d "
					+ "group by d.riskcode  " + ") e ";*/
			String tSql = "select (select riskname from lmriskapp where riskcode=e.riskcode),riskcode,countall,premall/10000"
				    + ",( case countall when '0' then '0' else premall/10000/countall end) "
				    + " from "
					+ "(select riskcode,count(distinct mainpolno) countall,sum(prem) premall "
					+ ",( case (select subriskflag from lmriskapp where riskcode = d.riskcode) when 'M' then '1' else '0' end) flag "
					+ " from ( "
					+ "select riskcode,mainpolno,prem " 
					+ "from lcpol a "
					+ "where a.renewcount = 0 "
					+ " and exists(select 1 from lcpol b where "
				        + " b.contno=a.contno "
				        + " and b.mainpolno=b.polno "
				        + " and b.renewcount = 0 "
				        + " and b.conttype='1' "
				        + " and b.appflag in( '1','4') "
				        + mSalechnlSql
						+ "and b.managecom >='"
						+ "?value1?"
						+ "' "
						+ "and b.managecom <='"
						+ "?value2?"
						+ "' "
			            + " and exists (select '1' from es_doc_main where doccode=b.prtno and subtype = 'UA001' )"//有扫描件
						+ PubFun.DateTimeBetween("b.signdate","b.signtime",ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mStartTime, "?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
			        + ")) d "
					+ "group by d.riskcode  " + ") e ";
			if(mRiskCode!=null && !mRiskCode.equals(""))
				tSql = tSql + " where e.riskcode='"+ "?mRiskCode?" +"' ";
			
			tSql = tSql + " order by flag desc,riskcode  ";				
			logger.debug("tSql: " + tSql);
			
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tSql);
			sqlbv9.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv9.put("listArray1",listArray1);  //tManageComSql;
			sqlbv9.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv9.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv9.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			sqlbv9.put("mStartDate",mStartDate);
			sqlbv9.put("mStartTime",mStartTime);
			sqlbv9.put("mEndDate",mEndDate);
			sqlbv9.put("mEndTime",mEndTime);
			sqlbv9.put("mRiskCode", mRiskCode);

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv9);
			int row = tSSRS.getMaxRow() + 1;
			String[] strArr = new String[5];
			String StringArray[][] = new String[row][5];
			int m;
			for (m = 0; m < tSSRS.getMaxRow(); m++) {
				for (int j = 0; j < 5; j++) {
					StringArray[m][j] = tSSRS.GetText(m + 1, j + 1);
//					if (j > 2) {
//						StringArray[m][j] = mDecimalFormat.format(Double
//								.parseDouble(StringArray[m][j]));
//					}
				}
			}
			// 初始化最后合计行
			StringArray[m][0] = "合   计";
			for (int j = 2; j < 5; j++) {
				StringArray[m][j] = "0";
			}

			// 计算合计行各列的值
			for (int j = 2; j < 4; j++) {
				for (int i = 0; i < row - 1; i++) {
					if (j == 2) {
						StringArray[row - 1][j] = String.valueOf(Integer
								.parseInt(StringArray[row - 1][j])
								+ Integer.parseInt(StringArray[i][j]));
					} else {
						StringArray[row - 1][j] = String.valueOf(Double
								.parseDouble(StringArray[row - 1][j])
								+ Double.parseDouble(StringArray[i][j]));
						StringArray[i][j] = mDecimalFormat.format(Double
								.parseDouble(StringArray[i][j]));
					}
				}
				if (j > 2) {
					StringArray[row - 1][j] = mDecimalFormat.format(Double
							.parseDouble(StringArray[row - 1][j]));
				}
			}	
			for (int i = 0; i < row - 1; i++) {				
					StringArray[i][4] = mDecimalFormat.format(Double
							.parseDouble(StringArray[i][4]));
			}
			if(Integer.parseInt(StringArray[row - 1][2])!=0)
				StringArray[row - 1][4] = mDecimalFormat.format(Double
					.parseDouble(StringArray[row - 1][3])/Integer.parseInt(StringArray[row - 1][2]));
			else
				StringArray[row - 1][4] = "";

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("App04.vts", "printer");
			ListTable alistTable = new ListTable();
			alistTable.setName("INFO");

			for (int i = 0; i < row; i++) {
				strArr = new String[5];
				strArr = StringArray[i];
				alistTable.add(strArr);
			}

			xmlexport.addDisplayControl("displayinfo");
			xmlexport.addListTable(alistTable, strArr);
			texttag.add("SysDate", PubFun.getCurrentDate());
			texttag.add("StartDate", mStartDate+" "+mStartTime);
			texttag.add("EndDate", mEndDate+" "+mEndTime);
			texttag.add("Title", mTitle);
			texttag.add("SalechnlName", mSalechnlName);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常App04！") ;
			return false;
		}
		return true;
	}
	

	/*
	 * App02 各险种预收情况表
	 */
	private boolean PrepareDataApp02() {
		try {			
			String tManageComSql = " ";			
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			
			String tSql = "select (select riskname from lmriskapp where riskcode=e.riskcode),riskcode,countall,premall/10000"
				    + " from "
					+ "(select riskcode,count(distinct mainpolno) countall,sum(prem) premall "
					+ ",( case (select subriskflag from lmriskapp where riskcode = d.riskcode) when 'M' then '1' else '0' end) flag "
					+ " from ( "
					+ "select riskcode,mainpolno,prem " 
					+ "from lcpol a "
					+ "where  a.conttype='1' "
					+ mSalechnlSql
					+ "and a.managecom >='"
					+ "?value1?"
					+ "' "
					+ "and a.managecom <='"
					+ "?value2?"
					+ "' "
					+ tManageComSql
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001' "//有扫描件
					+ PubFun.DateTimeBetween("makedate","maketime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
					+ ")"
					//+ "and (makedate||' '||maketime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"') "
					//+ "and (makedate||' '||maketime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')) "
					+ " and a.renewcount=0 "									
					+ " ) d "
					+ "group by d.riskcode  " + ") e ";
			if(mRiskCode!=null && !mRiskCode.equals(""))
				tSql = tSql + " where e.riskcode='"+ "?mRiskCode?" +"' ";
			
			tSql = tSql + " order by flag desc,riskcode  ";				
			logger.debug("tSql: " + tSql);
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tSql);
			sqlbv10.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv10.put("listArray1",listArray1);  //tManageComSql;
			sqlbv10.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv10.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv10.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			sqlbv10.put("mStartDate",mStartDate);
			sqlbv10.put("mStartTime",mStartTime);
			sqlbv10.put("mEndDate",mEndDate);
			sqlbv10.put("mEndTime",mEndTime);
			sqlbv10.put("mRiskCode",mRiskCode);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv10);
			int row = tSSRS.getMaxRow() + 1;
			String[] strArr = new String[4];
			String StringArray[][] = new String[row][4];
			int m;
			for (m = 0; m < tSSRS.getMaxRow(); m++) {
				for (int j = 0; j < 4; j++) {
					StringArray[m][j] = tSSRS.GetText(m + 1, j + 1);
//					if (j > 2) {
//						StringArray[m][j] = mDecimalFormat.format(Double
//								.parseDouble(StringArray[m][j]));
//					}
				}
			}
			// 初始化最后合计行
			StringArray[m][0] = "合   计";
			for (int j = 2; j < 4; j++) {
				StringArray[m][j] = "0";
			}

			// 计算合计行各列的值
			for (int j = 2; j < 4; j++) {
				for (int i = 0; i < row - 1; i++) {
					if (j == 2) {
						StringArray[row - 1][j] = String.valueOf(Integer
								.parseInt(StringArray[row - 1][j])
								+ Integer.parseInt(StringArray[i][j]));
					} else {
						StringArray[row - 1][j] = String.valueOf(Double
								.parseDouble(StringArray[row - 1][j])
								+ Double.parseDouble(StringArray[i][j]));
						StringArray[i][j] = mDecimalFormat.format(Double
								.parseDouble(StringArray[i][j]));
					}
				}
				if (j > 2) {
					StringArray[row - 1][j] = mDecimalFormat.format(Double
							.parseDouble(StringArray[row - 1][j]));
				}
			}			

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("App02.vts", "printer");
			ListTable alistTable = new ListTable();
			alistTable.setName("INFO");

			for (int i = 0; i < row; i++) {
				strArr = new String[4];
				strArr = StringArray[i];
				alistTable.add(strArr);
			}

			xmlexport.addDisplayControl("displayinfo");
			xmlexport.addListTable(alistTable, strArr);
			texttag.add("SysDate", PubFun.getCurrentDate());
			texttag.add("StartDate", mStartDate+" "+mStartTime);
			texttag.add("EndDate", mEndDate+" "+mEndTime);
			texttag.add("Title", mTitle);
			texttag.add("SalechnlName", mSalechnlName);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常App02！") ;
			return false;
		}
		return true;
	}

	/*
	 * App10 机构体检状况一览表
	 */
	private boolean PrepareDataApp10() {
		try {
			int m; // 管理机构的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LDCom where char_length(trim(ComCode))="
					+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
					+ "?value1?"
					+ "' and comcode <='"
					+ "?value2?"+"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			logger.debug("Comcode_SQL: " + tsql);
			
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tsql);
			sqlbv11.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv11.put("listArray1",listArray1);  //tManageComSql;
			sqlbv11.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv11.put("value1",PubFun.RCh(mManageCom, "0", mManageLen)); 
			sqlbv11.put("value2",PubFun.RCh(mManageCom, "9", mManageLen)); 
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv11));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + tLDComSet.size());

			strArr = new String[mMaxColCount];
			String StringArray[][] = new String[row][mMaxColCount];
			Hashtable tHashtable = new Hashtable();
			for (m = 0; m < tLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(tLDComSet.get(m + 1));
				logger.debug(tLDComSchema.getComCode() + ":"
						+ tLDComSchema.getShortName());
				StringArray[m][0] = tLDComSchema.getComCode();
				StringArray[m][1] = tLDComSchema.getShortName();
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			StringArray[m][0] = "  8  6  ";
			StringArray[m][1] = " 总 公 司 ";

			// 初始化二维数组中的值
			for (int i = 0; i < row; i++) {
				for (int j = 2; j < mMaxColCount; j++) {
						StringArray[i][j] = "0"; // 件数
				}
			}
		
			ExeSQL tExeSQL = new ExeSQL();			
			// 体检发放件数
			SSRS tSSRS1 = new SSRS();
			String tHealthSendSql = "select substr(managecom,1,"
					+ "?mManageLen?"
					+ "),count(distinct proposalcontno) send from LCPENotice "
					+ "where petimes='1' "
					+ " and exists(select 1 from lcpol where contno=LCPENotice.contno "
					+ " and conttype='1' and renewcount=0 "
					+ " and managecom >='" + "?value1?"
					+ "' " + "and managecom <='"
					+ "?value2?" + "' " 
					+ tManageComSql
					+ mSalechnlSql
					+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' )"//有扫描件
					+ ")" 
					/*+ " and exists (select 1 from lwmission where activityid='0000001106' and MissionProp2=LCPENotice.contno "
					+ " and MissionProp3=LCPENotice.prtseq "
					+ PubFun.DateTimeBetween("makedate","maketime",mStartDate,mStartTime,mEndDate,mEndTime)
					//+ " and (makedate||' '||maketime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"') "//核保下发体检时间
					//+ " and (makedate||' '||maketime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')" 
					+ " union all"
					+ " select 1 from lbmission where activityid='0000001106' and MissionProp2=LCPENotice.contno "
					+ " and MissionProp3=LCPENotice.prtseq "
					+ PubFun.DateTimeBetween("makedate","maketime",mStartDate,mStartTime,mEndDate,mEndTime)
					+ " )"
					*/
					+ " and exists (select 1 from loprtmanager where prtseq=LCPENotice.prtseq "
					+ PubFun.DateTimeBetween("makedate","maketime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
					+ " )"
					//+ " and (makedate||' '||maketime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"') "//核保下发体检时间
					//+ " and (makedate||' '||maketime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')) "
					+ " group by substr(managecom,1," + "?mManageLen?" + ") ";
			logger.debug("*****HealthSendSql: " + tHealthSendSql);
			
			// 体检回复件数
			SSRS tSSRS2 = new SSRS();
			String tHealthBackSql = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ "),count(distinct proposalcontno) send from LCPENotice "
				+ "where petimes='1' "
				+ " and exists(select 1 from lcpol where contno=LCPENotice.contno "
				+ " and conttype='1' and renewcount=0 "
				+ " and managecom >='" + "?value1?"
				+ "' " + "and managecom <='"
				+ "?value2?" + "' " 
				+ tManageComSql
				+ mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' )"//有扫描件
				+ ")" 
				+ " and peresult is not null "
				+ " and exists(select 1 from LCPENoticeReply where proposalcontno=LCPENotice.proposalcontno and prtseq=LCPENotice.prtseq "
				+ PubFun.DateTimeBetween("modifydate","modifytime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
				+ " )"
				//+ " and (modifydate||' '||modifytime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"') "
				//+ " and (modifydate||' '||modifytime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')) "
				+ " group by substr(managecom,1," + "?mManageLen?" + ") ";
			logger.debug("*****HealthBackSql: " + tHealthBackSql);
			
			// 体检阳性件数
			SSRS tSSRS3 = new SSRS();
			String tHealthQuestSql = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ "),count(distinct proposalcontno) send from LCPENotice "
				+ "where petimes='1' "
				+ " and exists(select 1 from lcpol where contno=LCPENotice.contno "
				+ " and conttype='1' and renewcount=0 "
				+ " and managecom >='" + "?value1?"
				+ "' " + "and managecom <='"
				+ "?value2?" + "' " 
				+ tManageComSql
				+ mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' )"//有扫描件
				+ ")" 
				+ " and peresult is not null and peresult like '异常%'"
				+ " and exists(select 1 from LCPENoticeReply where proposalcontno=LCPENotice.proposalcontno and prtseq=LCPENotice.prtseq "
				+ PubFun.DateTimeBetween("modifydate","modifytime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
				+ " )"
				//+ " and (modifydate||' '||modifytime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"') "
				//+ " and (modifydate||' '||modifytime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')) "
				+ " group by substr(managecom,1," + "?mManageLen?" + ") ";
			
			logger.debug("*****HealthQuestSql: " + tHealthQuestSql);
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tHealthSendSql);
			sqlbv12.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv12.put("listArray1",listArray1);  //tManageComSql;
			sqlbv12.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv12.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv12.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			sqlbv12.put("mStartDate",mStartDate);
			sqlbv12.put("mStartTime",mStartTime);
			sqlbv12.put("mEndDate",mEndDate);
			sqlbv12.put("mEndTime",mEndTime);
			tSSRS1 = tExeSQL.execSQL(sqlbv12);
			tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tHealthBackSql);
			sqlbv13.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv13.put("listArray1",listArray1);  //tManageComSql;
			sqlbv13.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv13.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv13.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			sqlbv13.put("mStartDate",mStartDate);
			sqlbv13.put("mStartTime",mStartTime);
			sqlbv13.put("mEndDate",mEndDate);
			sqlbv13.put("mEndTime",mEndTime);
			tSSRS2 = tExeSQL.execSQL(sqlbv13);
			tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(tHealthQuestSql);
			sqlbv14.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv14.put("listArray1",listArray1);  //tManageComSql;
			sqlbv14.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv14.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv14.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			sqlbv14.put("mStartDate",mStartDate);
			sqlbv14.put("mStartTime",mStartTime);
			sqlbv14.put("mEndDate",mEndDate);
			sqlbv14.put("mEndTime",mEndTime);
			tSSRS3 = tExeSQL.execSQL(sqlbv14);
			// 合并体检发放件数
			for (m = 1; m <= tSSRS1.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tSSRS1.GetText(m, 1))).intValue();
				StringArray[tIndex][2] = tSSRS1.GetText(m, 2);
			}
			// 合并体检回复件数
			for (m = 1; m <= tSSRS2.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tSSRS2.GetText(m, 1))).intValue();
				StringArray[tIndex][3] = tSSRS2.GetText(m, 2);
			}
			// 合并体检阳性件数
			for (m = 1; m <= tSSRS3.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tSSRS3.GetText(m, 1))).intValue();
				StringArray[tIndex][4] = tSSRS3.GetText(m, 2);
			}

			// 计算合计行各列的值
			for (int j = 2; j < mMaxColCount; j++) {
				for (int i = 0; i < row - 1; i++) {
						StringArray[row - 1][j] = String.valueOf(Integer
								.parseInt(StringArray[row - 1][j])
								+ Integer.parseInt(StringArray[i][j]));
					
				}				
			}
			
			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例

			xmlexport.createDocument("App10.vts", "printer");

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
			texttag.add("SalechnlName", mSalechnlName);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常App10！") ;
			return false;
		}
		return true;
	}

	/*
	 * App11 机构契约调查一览表
	 */
	private boolean PrepareDataApp11() {
		try {
			int m; // 管理机构的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LDCom where char_length(trim(ComCode))="
					+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
					+ "?value1?"
					+ "' and comcode <='"
					+ "?value2?"+"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(tsql);
			sqlbv15.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv15.put("listArray1",listArray1); //mSalechnlSql
			sqlbv15.put("value1",PubFun.RCh(mManageCom, "0", mManageLen)); 
			sqlbv15.put("value2",PubFun.RCh(mManageCom, "9", mManageLen)); 
			logger.debug("Comcode_SQL: " + tsql);
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv15));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + tLDComSet.size());

			strArr = new String[mMaxColCount];
			String StringArray[][] = new String[row][mMaxColCount];
			Hashtable tHashtable = new Hashtable();
			for (m = 0; m < tLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(tLDComSet.get(m + 1));
				logger.debug(tLDComSchema.getComCode() + ":"
						+ tLDComSchema.getShortName());
				StringArray[m][0] = tLDComSchema.getComCode();
				StringArray[m][1] = tLDComSchema.getShortName();
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			StringArray[m][0] = "  8  6  ";
			StringArray[m][1] = " 总 公 司 ";

			// 初始化二维数组中的值
			for (int i = 0; i < row; i++) {
				for (int j = 2; j < mMaxColCount; j++) {
						StringArray[i][j] = "0"; // 件数
				}
			}

			ExeSQL tExeSQL = new ExeSQL();			
			//发放件数
			String tSQL = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ "),count(distinct proposalcontno) send from LCRReport "
				+ "where "
				+ " exists(select 1 from lcpol where contno=LCRReport.contno "
				+ " and conttype='1' and renewcount=0 "
				+ " and managecom >='" + "?value1?"
				+ "' " + "and managecom <='"
				+ "?value2?" + "' " 
				+ tManageComSql
				+ mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' )"//有扫描件
				+ ")" 
				+ " and exists (select 1 from loprtmanager where prtseq=LCRReport.prtseq "
				+ PubFun.DateTimeBetween("makedate","maketime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
				+ " )"
				+ " group by substr(managecom,1," + "?mManageLen?" + ") ";
			logger.debug("*****发放件数SQL: " + tSQL);
			
			//回复件数			
			String tSQLReply = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ "),count(distinct proposalcontno) send from LCRReport "
				+ "where "
				+ " exists(select 1 from lcpol where contno=LCRReport.contno "
				+ " and conttype='1' and renewcount=0 "
				+ " and managecom >='" +"?value1?"
				+ "' " + "and managecom <='"
				+ "?value2?" + "' " 
				+ tManageComSql
				+ mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' )"//有扫描件
				+ ")" 
				+ " and replycontente is not null and (GrpPrtSeq is null or GrpPrtSeq<>'AUTO') "
				+ PubFun.DateTimeBetween("replydate","replytime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
				//+ " and (replydate||' '||replytime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"') "
				//+ " and (replydate||' '||replytime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"') "
				+ " group by substr(managecom,1," + "?mManageLen?" + ") ";
			  
		    logger.debug("*****回复件数tSQL: " + tSQLReply);
		    SSRS tSSRS = new SSRS();
		    SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tSQL);
			sqlbv16.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv16.put("listArray1",listArray1);  //tManageComSql;
			sqlbv16.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv16.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv16.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			sqlbv16.put("mStartDate",mStartDate);
			sqlbv16.put("mStartTime",mStartTime);
			sqlbv16.put("mEndDate",mEndDate);
			sqlbv16.put("mEndTime",mEndTime);
		    tSSRS = tExeSQL.execSQL(sqlbv16);
		    tExeSQL = new ExeSQL();
			SSRS tSSRS1 = new SSRS();
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(tSQLReply);
			sqlbv17.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv17.put("listArray1",listArray1);  //tManageComSql;
			sqlbv17.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv17.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv17.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			sqlbv17.put("mStartDate",mStartDate);
			sqlbv17.put("mStartTime",mStartTime);
			sqlbv17.put("mEndDate",mEndDate);
			sqlbv17.put("mEndTime",mEndTime);
		    tSSRS1 = tExeSQL.execSQL(sqlbv17);
			// 合并数据
			for (m = 1; m <= tSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable.get(tSSRS.GetText(m, 1)))
						.intValue();
				StringArray[tIndex][2] = tSSRS.GetText(m, 2);
			}
			for (m = 1; m <= tSSRS1.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable.get(tSSRS1.GetText(m, 1)))
						.intValue();
				StringArray[tIndex][3] = tSSRS1.GetText(m, 2);
			}

			// 计算合计行各列的值
			for (int j = 2; j < mMaxColCount; j++) {
				for (int i = 0; i < row - 1; i++) {
					StringArray[row - 1][j] = String.valueOf(Integer
							.parseInt(StringArray[row - 1][j])
							+ Integer.parseInt(StringArray[i][j]));
				}
			}

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("App11.vts", "printer");
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
			mSubTitle = "契调发放及回复情况";
			texttag.add("SubTitle",mSubTitle);
			texttag.add("SalechnlName", mSalechnlName);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常App11！") ;
			return false;
		}
		return true;
	}
	
	/*
	 * App12 生存调查清单  待修改
	 */
	private boolean PrepareDataApp12() {
		try {			
			String tManageComSql = " ";			
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			
			String tSql = "select distinct proposalcontno,managecom "
				+ " ,(select min(makedate) from loprtmanager where prtseq = LCRReport.prtseq) senddate"
				+ " , (select (case when max(replydate) is not null then max(replydate) else '' end) from LCRReport d where d.proposalcontno = LCRReport.proposalcontno"
				+ PubFun.DateTimeBetween("replydate","replytime",mStartDate,mStartTime,mEndDate,mEndTime)
				+ " ) replydate "
				+ " ,(select (case when uwflag is null then '' else (select codename from ldcode where codetype='uwstate' and code=uwflag) end) from lccont where contno=LCRReport.contno "
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
				+ " ) "
				+ " from LCRReport "
				+ " where "
				+ " exists(select 1 from lcpol where contno=LCRReport.contno "
				+ " and conttype='1' and renewcount=0 "
				+ " and managecom >='" + "?value1?"
				+ "' " + "and managecom <='"
				+ "?value2?" + "' " 
				+ tManageComSql
				+ mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' )"//有扫描件
				+ " ) " 
				+ " and exists (select 1 from loprtmanager where prtseq=LCRReport.prtseq "
				+ PubFun.DateTimeBetween("makedate","maketime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),ReportPubFun.getParameterStr(mEndTime, "?mEndTime?"))
				+ " )"
				+ " order by managecom,proposalcontno ";
			logger.debug("*****生存调查清单SQL: " + tSql);
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(tSql);
			sqlbv18.put("mManageLen",mManageLen);  //tManageComSql;
			sqlbv18.put("listArray1",listArray1);  //tManageComSql;
			sqlbv18.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv18.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv18.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			sqlbv18.put("mStartDate",mStartDate);
			sqlbv18.put("mStartTime",mStartTime);
			sqlbv18.put("mEndDate",mEndDate);
			sqlbv18.put("mEndTime",mEndTime);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv18);
			int row = tSSRS.getMaxRow() ;//不用汇总
			String[] strArr = new String[mMaxColCount];
			String StringArray[][] = new String[row][mMaxColCount];
			int m;
			for (m = 0; m < tSSRS.getMaxRow(); m++) {				
				for (int j = 0; j < mMaxColCount; j++) {
					if(j ==0)
						StringArray[m][j] = String.valueOf(m + 1);
					else					
						StringArray[m][j] = tSSRS.GetText(m + 1, j);
				}
			}					

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("App12.vts", "printer");
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
			texttag.add("SalechnlName", mSalechnlName);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常App12！") ;
			return false;
		}
		return true;
	}

	/*
	 * App21 延期拒保分析表
	 */
	private boolean PrepareDataApp21() {
		try {
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			ListTable tlistTable = new ListTable();
			String[] strArr = null;
			// 拒保，延期件统计报表
			xmlexport.createDocument("App11.vts", "printer");

			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.query();
			String sql = "select lcpol.prtno,lcpol.proposalno,lcpol.riskcode,lcpol.prem,lcpol.amnt, "
					+ "lcuwmaster.AgentCode,lcuwmaster.UWIdea,lcuwmaster.passflag, "
					+ "lcpol.insuredsex,lcpol.insuredappage,lcpol.InsuredNo,lcpol.ManageCom, "
					+ "(select d.riskcode from lcpol d where d.polno=lcpol.mainpolno) mainriskcode, "
					+ "(select c.SubRiskFlag from  lmriskapp c where c.riskcode=lcpol.riskcode ) SubRiskFlag "
					+ "from lcpol,lcuwmaster "
					+ "where lcpol.proposalno=lcuwmaster.proposalno "
					+ "and lcpol.conttype='1' and lcpol.renewcount=0  "
					+ "and lcuwmaster.passflag in ('1','2') "
					+ mSalechnlSql
					+ "and lcpol.managecom >='"
					+ "?value1?"
					+ "' "
					+ "and lcpol.managecom <='"
					+ "?value2?"
					+ "' "
					+ "and lcuwmaster.modifydate>='"
					+ "?mStartDate?"
					+ "' "
					+ "and lcuwmaster.modifydate<='"
					+ "?mEndDate?"
					+ "' "
					+ "order by lcuwmaster.passflag,lcpol.managecom,mainriskcode,lcpol.prtno,SubRiskFlag";
			ExeSQL Back_exesql = new ExeSQL();
			logger.debug("sql---" + sql);

			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(sql);
			sqlbv19.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv19.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv19.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			sqlbv19.put("mStartDate",mStartDate);
			sqlbv19.put("mEndDate",mEndDate);
			SSRS Back_ssrs = Back_exesql.execSQL(sqlbv19);

			logger.debug("******查询的相关信息的条数是" + Back_ssrs.getMaxRow());

			String CompairPrtNO = "";
			int CompairNO = 1;

			for (int j = 1; j <= Back_ssrs.getMaxRow(); j++) {
				strArr = new String[13];
				strArr[0] = String.valueOf(CompairNO);
				strArr[1] = Back_ssrs.GetText(j, 1); // 印刷号

				if (CompairPrtNO.equals(Back_ssrs.GetText(j, 1))) {
					CompairNO--;
					strArr[0] = String.valueOf(CompairNO);
				}

				strArr[2] = ReportPubFun.getRiskName(Back_ssrs.GetText(j, 3),
						tLMRiskAppSet); // 险种
				strArr[3] = Back_ssrs.GetText(j, 5); // 保额
				strArr[4] = Back_ssrs.GetText(j, 4); // 保费
				strArr[5] = ReportPubFun.getSexName(Back_ssrs.GetText(j, 9)); // 性别
				strArr[6] = Back_ssrs.GetText(j, 10); // 年龄
				strArr[7] = ReportPubFun.getLdPersonInfo(
						Back_ssrs.GetText(j, 11), "0").getWorkType(); // 职业
				strArr[8] = ReportPubFun.getLdPersonInfo(
						Back_ssrs.GetText(j, 11), "0").getOccupationCode(); // 职业代码
				strArr[9] = Back_ssrs.GetText(j, 12); // 分公司
				strArr[10] = ReportPubFun.getTypeName(Back_ssrs.GetText(j, 8)); // 核保结论
				// 延/拒类型
				strArr[11] = Back_ssrs.GetText(j, 7); // 原因
				strArr[12] = Back_ssrs.GetText(j, 6); // 代理人号
				CompairPrtNO = strArr[1];
				CompairNO++;
				tlistTable.add(strArr);
			}

			tlistTable.setName("uw3");
			strArr = new String[13];
			strArr[0] = "no";
			strArr[2] = "riskcode";
			strArr[3] = "amnt";
			strArr[4] = "prem";
			strArr[5] = "sex";
			strArr[6] = "age";
			strArr[7] = "work";
			strArr[8] = "occupetype";
			strArr[9] = "mng";
			strArr[10] = "passflag";
			strArr[11] = "cause";
			strArr[12] = "agentcode";
			xmlexport.addListTable(tlistTable, strArr);

			texttag.add("SysDate", PubFun.getCurrentDate());
			texttag.add("StartDate", mStartDate+" "+mStartTime);
			texttag.add("EndDate", mEndDate+" "+mEndTime);
			texttag.add("Title", mTitle);
			texttag.add("SalechnlName", mSalechnlName);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);

			mResult.clear();
			mResult.addElement(xmlexport);

		} catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.errorMessage = "统计时发生异常App11！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/*
	 * App22 机构问题件统计表
	 */
	private boolean PrepareDataApp22() {
		try {

			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			ListTable tlistTable = new ListTable();
			String[] strArr = null;
			xmlexport.createDocument("App12.vts", "printer");

			String tQuestsql = "select  managecom,count(contno), "
					+ " sum((select count(distinct contno) from lcissuepol  a where a.backobjtype in ('2','3') and a.operatepos in ('1','5') and a.NeedPrint in('Y','P') and a.proposalcontno=lccont.proposalcontno  ) ),"
					+ " sum((select count(proposalcontno) from lcissuepol  a where a.backobjtype in ('2','3') and a.operatepos in ('1','5') and a.NeedPrint in('Y','P') and a.proposalcontno=lccont.proposalcontno ) )"
					+ " from lccont  where 1=1 "
					+ "and conttype='1' "
					+ " and exists (select '1' from lcpol where polno=mainpolno and contno=lccont.contno and renewcount=0)"
					+ "and managecom >='" +"?value1?"
					+ "' " + "and managecom <='"
					+"?value2?" + "' " + mSalechnlSql + ""
					+ "and makedate>='" + "?mStartDate?" + "' " + "and makedate<='"
					+ "?mEndDate?" + "' " + "group by managecom";

			ExeSQL WT_exesql = new ExeSQL();
			logger.debug("-----问题件sql---" + tQuestsql);
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(tQuestsql);
			sqlbv19.put("mSalechnlType",mSalechnlType); //mSalechnlSql
			sqlbv19.put("value1",PubFun.RCh(mManageCom, "0", 8)); 
			sqlbv19.put("value2",PubFun.RCh(mManageCom, "9", 8)); 
			sqlbv19.put("mStartDate",mStartDate);
			sqlbv19.put("mEndDate",mEndDate);
			SSRS WT_ssrs = WT_exesql.execSQL(sqlbv19);
			logger.debug("******问题件条数： " + WT_ssrs.getMaxRow());

			tlistTable.setName("uw9");
			double ysjs = 0.0;
			double wtjs = 0.0;
			double wtts = 0.0;

			for (int j = 1; j <= WT_ssrs.getMaxRow(); j++) {
				strArr = new String[6];
				strArr[0] = WT_ssrs.GetText(j, 1); // 管理机构
				strArr[1] = WT_ssrs.GetText(j, 2); // 预收件数
				strArr[2] = WT_ssrs.GetText(j, 3); // 问题件数
				strArr[3] = WT_ssrs.GetText(j, 4); // 问题条数
				strArr[4] = ReportPubFun.functionDivision(strArr[3], strArr[2],
						"0.0"); // 件均问题数
				strArr[5] = ReportPubFun.functionDivision(strArr[2], strArr[1],
						"0.0000"); // 问题件占比
				tlistTable.add(strArr);
				ysjs = ysjs + ReportPubFun.functionDouble(strArr[1]);
				wtjs = wtjs + ReportPubFun.functionDouble(strArr[2]);
				wtts = wtts + ReportPubFun.functionDouble(strArr[3]);
			}

			strArr = new String[6];
			strArr[0] = "合计";
			strArr[1] = new DecimalFormat("0").format(ysjs);
			strArr[2] = new DecimalFormat("0").format(wtjs);
			strArr[3] = new DecimalFormat("0").format(wtts);
			strArr[4] = ReportPubFun.functionDivision(strArr[3], strArr[2],
					"0.0");
			strArr[5] = ReportPubFun.functionDivision(strArr[2], strArr[1],
					"0.0");
			tlistTable.add(strArr);
			strArr = new String[6];
			strArr[0] = "mng";
			strArr[1] = "ys_js";
			strArr[2] = "wt_js";
			strArr[3] = "wt_ts";
			strArr[4] = "jj_ts";
			strArr[5] = "wt_zb";
			xmlexport.addListTable(tlistTable, strArr);
			texttag.add("SysDate", PubFun.getCurrentDate());
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("Title", mTitle);
			texttag.add("SalechnlName", mSalechnlName);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.errorMessage = "统计时发生异常App12！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean dealData() {
		try {
			/**
			 * 对以下几个报表数据准备（共性：共有四列，分别为：机构代码，机构名称，**件数，**保费） **表示具体的报表，如预收件数和预收保费
			 * App01 机构预收报表 App05 机构期交承保情况表 App06 机构犹豫期撤单表 App07 机构出单前撤单表 
			 * App08 机构延期情况表 App09 机构拒保情况表
			 */
			if (mReportType.equals("App01") || mReportType.equals("App05")
					|| mReportType.equals("App06")
					|| mReportType.equals("App07")
					|| mReportType.equals("App08")
					|| mReportType.equals("App09")) {
				if (!commPrepareData())
					return false;
			}			
			// App02 各险种预收报表
			else if (mReportType.equals("App02")) {
				if (!PrepareDataApp02())
					return false;
			}
			// App03 机构承保情况表
			else if (mReportType.equals("App03")) {
				if (!PrepareDataApp03())
					return false;
			}
			// App04 各险种承保情况表
			else if (mReportType.equals("App04")) {
				if (!PrepareDataApp04())
					return false;
			}
			// App10 机构体检状况一览表
			else if (mReportType.equals("App10")) {
				if (!PrepareDataApp10())
					return false;
			}
			// App11 机构契约调查一览表
			else if (mReportType.equals("App11")) {
				if (!PrepareDataApp11())
					return false;
			}
			// App12 生存调查清单
			else if (mReportType.equals("App12")) {
				if (!PrepareDataApp12())
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
		PolAppStatBL PolAppStatBL1 = new PolAppStatBL();
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
		tVData.addElement(ManageType);
		tVData.addElement(SalechnlType);
		tVData.addElement(ReportType);
		tVData.addElement(tG);
		PolAppStatBL1.submitData(tVData, "PRINT");
	}
}
