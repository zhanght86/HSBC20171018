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
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
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
 * Description: 月报补充报表
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

public class PolAppOtherStatBL {
private static Logger logger = Logger.getLogger(PolAppOtherStatBL.class);
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
	private SQLwithBindVariables mSalechnlSqlSqlbv = new SQLwithBindVariables();
	private String mManageComSql; // 管理机构相关SQL
	private SQLwithBindVariables mManageComSqlSqlbv = new SQLwithBindVariables();
	private String mSalechnlName; // 销售渠道名称
	private String mTitle;
	private String mSubTitle = ""; // 子标题
	private int mMaxColCount = 0; // 最大列数
	private String mManageCom;
	DecimalFormat mDecimalFormat = new DecimalFormat("0.0000");

	public PolAppOtherStatBL() {
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
			ArrayList<String> list = new ArrayList<String>();
			mManageComSql = " in(";
			tSSRS = new SSRS();
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("select code from ldcode where codetype ='regionmanagecom' and othersign='"+"?othersign?"+"'");
			sqlbv1.put("othersign", mManageType.toLowerCase());
			tSSRS = tExeSQL.execSQL(sqlbv1);
			for(int i=1;i<=tSSRS.getMaxRow();i++)
			{
				list.add(tSSRS.GetText(i, 1));
//				if(i>1)
//					mManageComSql = mManageComSql + ",";
//				mManageComSql = mManageComSql + "'"+tSSRS.GetText(i, 1)+"'";
			}					
			
			mManageComSql = mManageComSql + "'?list?') ";
			this.mManageComSqlSqlbv.put("list", list);
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
		sqlbv2.put("mSalechnlType", mSalechnlType);
		tSSRS = tExeSQL.execSQL(sqlbv2);
		if (tSSRS == null || tSSRS.getMaxRow() < 1) {
			CError.buildErr(this, "销售渠道类型错误!") ;
			return false;
		}

		logger.debug("***mSalechnl: " + tSSRS.GetText(1, 1) + "   "
				+ tSSRS.GetText(1, 2));
		mSalechnlName = tSSRS.GetText(1, 1);
		mSalechnlSql = " and SaleChnl in ('"
				//+ splitCat(tSSRS.GetText(1, 2), "/") 
				+ "?mSalechnlType?"
				+ "') ";
		this.mSalechnlSqlSqlbv.put("mSalechnlType", mSalechnlType);
		if(mSalechnlType.equals("12"))
			mSalechnlSql = " and SaleChnl in ('05','06','08') ";
		else if(mSalechnlType.equals("13"))
			mSalechnlSql = " and SaleChnl in ('01','02','09','10','11') ";
			
		logger.debug("***mSalechnlType: " + mSalechnlType);
		logger.debug("***mSalechnlSql: " + mSalechnlSql);
		logger.debug("***mTitle: " + mTitle);		
		
		tExeSQL = new ExeSQL();
		tSSRS = new SSRS();
		String tReportSQL = "select codealias,comcode,othersign from ldcode where codetype='othreporttype' and code= '"
				+ "?mReportType?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tReportSQL);
		sqlbv3.put("mReportType", mReportType);
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
					+ "?mManageCom0?"
					+ "' and comcode <='"
					+ "?mManageCom9?" +"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.put(this.mManageComSqlSqlbv);
			sqlbv4.sql(tsql);
			sqlbv4.put("mManageLen", mManageLen);
			sqlbv4.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv4.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			logger.debug("Comcode_SQL: " + tsql);
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
						+ ",(select sum(prem) from lcpol where contno = lccont.contno) prem "
						+ "from lccont "
						+ "where conttype='1' "
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ mSalechnlSql 
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' "//有扫描件
						+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
//						+ "and makedate>='" + mStartDate + "' "
//						+ "and makedate<='" + mEndDate + "'"						
						+ "and managecom >='" + "?mManageCom0?"
						+ "' " + "and managecom <='"
						+ "?mManageCom9?" + "' "
						+ tManageComSql
						+ ") group by substr(managecom,1," + "?mManageLen?" + ")";
			}			

			// App05 机构期交承保情况表
			if (mReportType.equals("App05")) {
				tSQL = "select substr(managecom,1,"
						+ "?mManageLen?"
						+ ") submanage,count(distinct contno) countPol,sum(prem)/10000 sumprem  from "
						+ "(select managecom,contno"
						+ ",(select sum(decode(lccont.payintv,'1',prem/0.09,'3',prem/0.27,'6',prem/0.52,'12',prem)) from lcpol where contno = lccont.contno) prem "
						+ " from lccont  "
						+ " where conttype='1' and appflag='1' and payintv>0 "
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' ) "//有扫描件
						+ mSalechnlSql 
						+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
						+ " and managecom >='" + "?mManageCom0?"
						+ "' " + "and managecom <='"
						+ "?mManageCom9?" + "' "
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
						+ "where conttype='1'  and appflag='4' "
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
						+ mSalechnlSql
						+ "and exists(select 1 from lpedoritem where contno=lccont.contno and edortype='WT' and EdorState='0' "
						+ " and edorvalidate>='"+ "?mStartDate?" + "'"
						+ " and edorvalidate<='" + "?mEndDate?" + "'"
						+ ") " + "and managecom >='"
						+ "?mManageCom0?" + "' "
						+ "and managecom <='" + "?mManageCom9?"+ "' "
						+ tManageComSql
						+ ") a group by substr(managecom,1," + "?mManageLen?"
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
						+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
						+ " and managecom >='"
						+ "?mManageCom0?" + "' "
						+ " and managecom <='" + "?mManageCom9?" + "' " 
						+ tManageComSql
						+ ") group by substr(managecom,1," + "?mManageLen?"
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
						+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
						+ " and managecom >='"
						+ "?mManageCom0?" + "' "
						+ " and managecom <='" + "?mManageCom9?"	+ "' "
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
						+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
						+ " and managecom >='"
						+ "?mManageCom0?" + "' "
						+ " and managecom <='" + "?mManageCom9?" + "' "
						+ tManageComSql
						+ ") group by substr(managecom,1," + "?mManageLen?"
						+ ")";
			}

			logger.debug("*****SQL: " + tSQL);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.put(this.mSalechnlSqlSqlbv);
			sqlbv5.put(this.mManageComSqlSqlbv);
			sqlbv5.sql(tSQL);
			sqlbv5.put("mManageLen", mManageLen);
			sqlbv5.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv5.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv5.put("mStartDate", mStartDate);
			sqlbv5.put("mStartTime", mStartTime);
			sqlbv5.put("mEndDate", mEndDate);
			sqlbv5.put("mEndTime", mEndTime);
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
	 * Oth03 职业分布表
	 */
	private boolean PrepareDataOth01() {
		try {		
			int m; // 管理机构的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LDCom where char_length(trim(ComCode))="
				+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
				+ "?mManageCom0?"
				+ "' and comcode <='"
				+ "?mManageCom9?"+"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.put(this.mManageComSqlSqlbv);
			sqlbv6.sql(tsql);
			sqlbv6.put("mManageLen", mManageLen);
			sqlbv6.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv6.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			logger.debug("Comcode_SQL: " + tsql);
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv6));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLDComSet.size()+1 ;
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
			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("AppOther01.vts", "printer");
			ListTable alistTable = new ListTable();
			alistTable.setName("INFO");
			String str[][] = new String[tLDComSet.size()+1][14];
			
			
			SSRS tSSRS =new SSRS();
			ExeSQL tExeSQL =new ExeSQL();
//			tSSRS =tExeSQL.execSQL("select codename,code from ldcode where codetype ='highamnt' order by code");
			for(int n=1;n<=tLDComSet.size();n++){
				String tManageCom =tLDComSet.get(n).getComCode();
				String tComName =tLDComSet.get(n).getName();
				String col[]=new String[14];
				col[0]=tManageCom ;
				col[1]=tComName;
				int sum =0;
				String tSQL =" select (case when sum(A.a) is not null then sum(A.a)  else 0 end),(case when sum(A.b) is not null then sum(A.b)  else 0 end),(case when sum(A.c) is not null then sum(A.c)  else 0 end),(case when sum(A.d) is not null then sum(A.d)  else 0 end),(case when sum(A.e) is not null then sum(A.e)  else 0 end),"
					        +" (case when sum(A.f) is not null then sum(A.f)  else 0 end),(case when sum(A.g) is not null then sum(A.g)  else 0 end),(case when sum(A.h) is not null then sum(A.h)  else 0 end),(case when sum(A.i) is not null then sum(A.i)  else 0 end),(case when sum(A.j) is not null then sum(A.j)  else 0 end),(case when sum(A.l) is not null then sum(A.l)  else 0 end),"
					        +" (case when sum(A.a)+sum(A.b)+sum(A.c)+sum(A.d)+sum(A.e)+sum(A.f)+sum(A.g)+sum(A.h)+sum(A.i)+sum(A.j)+sum(A.l) is not null then sum(A.a)+sum(A.b)+sum(A.c)+sum(A.d)+sum(A.e)+sum(A.f)+sum(A.g)+sum(A.h)+sum(A.i)+sum(A.j)+sum(A.l)  else 0 end) from ("
					        +" select (case when amnt>0 and amnt<100000 then 1 else 0 end) a,"
					        +" (case when amnt>100000 and amnt<200000 then 1 else 0 end) b,"
					        +" (case when amnt>200000 and amnt<400000 then 1 else 0 end) c,"
					        +" (case when amnt>400000 and amnt<600000 then 1 else 0 end) d,"
					        +" (case when amnt>600000 and amnt<1000000 then 1 else 0 end) e,"
					        +" (case when amnt>1000000 and amnt<1200000 then 1 else 0 end) f,"
					        +" (case when amnt>1200000 and amnt<1500000 then 1 else 0 end) g,"
					        +" (case when amnt>1500000 and amnt<2000000 then 1 else 0 end) h,"
					        +" (case when amnt>2000000 and amnt<3000000 then 1 else 0 end) i,"
					        +" (case when amnt>3000000 and amnt<5000000 then 1 else 0 end) j,"
					        +" (case when amnt>5000000 and amnt<9999999999 then 1 else 0 end) l"
					        +" from lccont a where conttype ='1' "
					        +" and exists (select 1 from es_doc_main where doccode =a.prtno and subtype='UA001')"
					        + PubFun.DateTimeBetween("makedate","maketime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
//					        +ReportPubFun.getWherePart("salechnl", mSalechnlType)
					        +ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom,"?tManageCom?"))	
					        + mSalechnlSql
					        +" ) A"
					;
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.put(this.mSalechnlSqlSqlbv);
				sqlbv7.sql(tSQL);
				sqlbv7.put("mStartDate", mStartDate);
				sqlbv7.put("mStartTime", mStartTime);
				sqlbv7.put("mEndDate", mEndDate);
				sqlbv7.put("mEndTime", mEndTime);
				sqlbv7.put("tManageCom", tManageCom);
				tSSRS=tExeSQL.execSQL(sqlbv7);
				for(int j=1;j<=tSSRS.MaxCol;j++){
					col[j+1]=tSSRS.GetText(1, j);
				}
				str[n-1]= col;
//				alistTable.add(col);
//				for(int j=1;j<=tSSRS.MaxRow;j++){
//					String amnt[]=tSSRS.GetText(j, 1).split("-");
//					String minamnt ="";
//					String maxamnt ="";
//					String tBW="0000";
//					if(amnt.length>1){
//						minamnt =amnt[0]+tBW;
//						maxamnt =amnt[1]+tBW;
//					}else{
//						logger.debug("保额范围取值错误！");
//						return false;
//					}
//					String amntSQL =" select count(1) from lccont where 1=1 "
//						           +ReportPubFun.getWherePartLike("managecom", tManageCom)
//						           +ReportPubFun.getWherePart("amnt", minamnt, maxamnt, 1)
//						           +ReportPubFun.getWherePart("makedate", mStartDate, mEndDate, 1)
//						           +ReportPubFun.getWherePart("salechnl", mSalechnlType);
//					String tCount=tExeSQL.getOneValue(amntSQL);
//					sum=sum+Integer.parseInt(tCount);
//					col[j+1]=tCount;
//				}
//				col[13]=String.valueOf(sum);
//				alistTable.add(col);
			}
			//累计总公司
			str[tLDComSet.size()][0]="合     计";
			str[tLDComSet.size()][1]="总公司";
			for(int j=2;j<14;j++){
				int sumCol = 0;
				for(int i=0;i<tLDComSet.size();i++){
					sumCol = sumCol+Integer.parseInt(str[i][j]);
				}
				str[tLDComSet.size()][j] = String.valueOf(sumCol);
			}
			for(int i=0;i<=tLDComSet.size();i++){
				alistTable.add(str[i]);
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
			CError.buildErr(this, "统计时发生异常Oth01！") ;
			return false;
		}
		return true;
	}
	private boolean PrepareDataOth04() {
		try {		
			int m; // 管理机构的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LDCom where char_length(trim(ComCode))="
				+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
				+ "?mManageCom0?"
				+ "' and comcode <='"
				+"?mManageCom9?"+"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.put(this.mManageComSqlSqlbv);
			sqlbv8.sql(tsql);
			sqlbv8.put("mManageLen", mManageLen);
			sqlbv8.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv8.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			logger.debug("Comcode_SQL: " + tsql);
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv8));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLDComSet.size()+1 ;
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
			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("AppOther04.vts", "printer");
			ListTable alistTable = new ListTable();
			alistTable.setName("INFO");
			
			
			SSRS tSSRS =new SSRS();
			ExeSQL tExeSQL =new ExeSQL();
//			tSSRS =tExeSQL.execSQL("select codename,code from ldcode where codetype ='highamnt' order by code");
			for(int n=1;n<=tLDComSet.size();n++){
				String tManageCom =tLDComSet.get(n).getComCode();
				String tComName =tLDComSet.get(n).getName();
				String col[]=new String[14];
				col[0]=tManageCom ;
				col[1]=tComName;
				int sum =0;
				String tSQL =" select (case when sum(A.a) is not null then sum(A.a)  else 0 end),(case when sum(A.b) is not null then sum(A.b)  else 0 end),(case when sum(A.c) is not null then sum(A.c)  else 0 end),(case when sum(A.d) is not null then sum(A.d)  else 0 end),(case when sum(A.e) is not null then sum(A.e)  else 0 end),"
					+" (case when sum(A.f) is not null then sum(A.f)  else 0 end),(case when sum(A.g) is not null then sum(A.g)  else 0 end),(case when sum(A.h) is not null then sum(A.h)  else 0 end),(case when sum(A.i) is not null then sum(A.i)  else 0 end),(case when sum(A.j) is not null then sum(A.j)  else 0 end),(case when sum(A.l) is not null then sum(A.l)  else 0 end),"
					+" (case when sum(A.a)+sum(A.b)+sum(A.c)+sum(A.d)+sum(A.e)+sum(A.f)+sum(A.g)+sum(A.h)+sum(A.i)+sum(A.j)+sum(A.l) is not null then sum(A.a)+sum(A.b)+sum(A.c)+sum(A.d)+sum(A.e)+sum(A.f)+sum(A.g)+sum(A.h)+sum(A.i)+sum(A.j)+sum(A.l)  else 0 end) from ("
					+" select (case when amnt>0 and amnt<100000 then 1 else 0 end) a,"
					+" (case when amnt>100000 and amnt<200000 then 1 else 0 end) b,"
					+" (case when amnt>200000 and amnt<400000 then 1 else 0 end) c,"
					+" (case when amnt>400000 and amnt<600000 then 1 else 0 end) d,"
					+" (case when amnt>600000 and amnt<1000000 then 1 else 0 end) e,"
					+" (case when amnt>1000000 and amnt<1200000 then 1 else 0 end) f,"
					+" (case when amnt>1200000 and amnt<1500000 then 1 else 0 end) g,"
					+" (case when amnt>1500000 and amnt<2000000 then 1 else 0 end) h,"
					+" (case when amnt>2000000 and amnt<3000000 then 1 else 0 end) i,"
					+" (case when amnt>3000000 and amnt<5000000 then 1 else 0 end) j,"
					+" (case when amnt>5000000 and amnt<9999999999 then 1 else 0 end) l"
					+" from lccont a where conttype ='1' "
					+" and exists (select 1 from es_doc_main where doccode =a.prtno and subtype='UA001')"
			        + PubFun.DateTimeBetween("makedate","maketime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
//			        +ReportPubFun.getWherePart("salechnl", mSalechnlType)
			        +ReportPubFun.getWherePartLike("managecom", ReportPubFun.getParameterStr(tManageCom,"?tManageCom?"))	
					+ mSalechnlSql						
					+" ) A"
					;
				SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
				sqlbv9.put(this.mSalechnlSqlSqlbv);
				sqlbv9.sql(tSQL);
				sqlbv9.put("mStartDate", mStartDate);
				sqlbv9.put("mStartTime", mStartTime);
				sqlbv9.put("mEndDate", mEndDate);
				sqlbv9.put("mEndTime", mEndTime);
				sqlbv9.put("tManageCom", tManageCom);
				tSSRS=tExeSQL.execSQL(sqlbv9);
				for(int j=1;j<=tSSRS.MaxCol;j++){
					col[j+1]=tSSRS.GetText(1, j);
				}
				alistTable.add(col);
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
			CError.buildErr(this, "统计时发生异常Oth01！") ;
			return false;
		}
		return true;
	}
	private boolean PrepareDataOth03() {
		try {		
			int m; // 管理机构的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LDCom where length(trim(ComCode))="
					+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
					+ "?mManageCom0?"
					+ "' and comcode <='"
					+ "?mManageCom9?"+"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.put(this.mManageComSqlSqlbv);
			sqlbv10.sql(tsql);
			sqlbv10.put("mManageLen", mManageLen);
			sqlbv10.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv10.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			logger.debug("Comcode_SQL: " + tsql);
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv10));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLDComSet.size()+1 ;
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

			// 初始化二维数组中的值
			for (int i = 0; i < row; i++) {
				for (int j = 2; j < mMaxColCount; j++) {
						StringArray[i][j] = "0"; // 件数					
				}
			}

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tStandSSRS = new SSRS();
			String tStandSQL = "";
			tStandSQL = "select substr(managecom,1,"
					+ "?mManageLen?"
					+ ") submanage,count(distinct prtno) countPol "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='1') " //一类职业类别
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " group by substr(managecom,1,"
					+ "?mManageLen?"
					+ ")";
			logger.debug("*****一类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.put(this.mSalechnlSqlSqlbv);
			sqlbv11.put(this.mManageComSqlSqlbv);
			sqlbv11.sql(tStandSQL);
			sqlbv11.put("mManageLen", mManageLen);
			sqlbv11.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv11.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv11.put("mStartDate", mStartDate);
			sqlbv11.put("mStartTime", mStartTime);
			sqlbv11.put("mEndDate", mEndDate);
			sqlbv11.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv11);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][2] = tStandSSRS.GetText(m, 2);
			}
			
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ ") submanage,count(distinct prtno) countPol "
				+ " from lccont a "
				+ " where conttype='1' and appflag='1' "
				+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
				+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='2') " //职业类别
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1,"
				+ "?mManageLen?"
				+ ")";
			logger.debug("*****二类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.put(this.mSalechnlSqlSqlbv);
			sqlbv12.put(this.mManageComSqlSqlbv);
			sqlbv12.sql(tStandSQL);
			sqlbv12.put("mManageLen", mManageLen);
			sqlbv12.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv12.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv12.put("mStartDate", mStartDate);
			sqlbv12.put("mStartTime", mStartTime);
			sqlbv12.put("mEndDate", mEndDate);
			sqlbv12.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv12);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][3] = tStandSSRS.GetText(m, 2);
			}
			
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ ") submanage,count(distinct prtno) countPol "
				+ " from lccont a "
				+ " where conttype='1' and appflag='1' "
				+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
				+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='3') " //职业类别
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1,"
				+ "?mManageLen?"
				+ ")";
			logger.debug("*****三类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.put(this.mSalechnlSqlSqlbv);
			sqlbv13.put(this.mManageComSqlSqlbv);
			sqlbv13.sql(tStandSQL);
			sqlbv13.put("mManageLen", mManageLen);
			sqlbv13.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv13.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv13.put("mStartDate", mStartDate);
			sqlbv13.put("mStartTime", mStartTime);
			sqlbv13.put("mEndDate", mEndDate);
			sqlbv13.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv13);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][4] = tStandSSRS.GetText(m, 2);
			}			
						
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ ") submanage,count(distinct prtno) countPol "
				+ " from lccont a "
				+ " where conttype='1' and appflag='1' "
				+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
				+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='4') " //职业类别
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1,"
				+ "?mManageLen?"
				+ ")";
			logger.debug("*****四类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.put(this.mSalechnlSqlSqlbv);
			sqlbv14.put(this.mManageComSqlSqlbv);
			sqlbv14.sql(tStandSQL);
			sqlbv14.put("mManageLen", mManageLen);
			sqlbv14.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv14.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv14.put("mStartDate", mStartDate);
			sqlbv14.put("mStartTime", mStartTime);
			sqlbv14.put("mEndDate", mEndDate);
			sqlbv14.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv14);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][5] = tStandSSRS.GetText(m, 2);
			}
			
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ ") submanage,count(distinct prtno) countPol "
				+ " from lccont a "
				+ " where conttype='1' and appflag='1' "
				+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
				+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='5') " //职业类别
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom0?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1,"
				+ "?mManageLen?"
				+ ")";
			logger.debug("*****五类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.put(this.mSalechnlSqlSqlbv);
			sqlbv15.put(this.mManageComSqlSqlbv);
			sqlbv15.sql(tStandSQL);
			sqlbv15.put("mManageLen", mManageLen);
			sqlbv15.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv15.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv15.put("mStartDate", mStartDate);
			sqlbv15.put("mStartTime", mStartTime);
			sqlbv15.put("mEndDate", mEndDate);
			sqlbv15.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv15);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][6] = tStandSSRS.GetText(m, 2);
			}
			
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ ") submanage,count(distinct prtno) countPol "
				+ " from lccont a "
				+ " where conttype='1' and appflag='1' "
				+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
				+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='6') " //职业类别
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1,"
				+ "?mManageLen?"
				+ ")";
			logger.debug("*****六类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.put(this.mSalechnlSqlSqlbv);
			sqlbv16.put(this.mManageComSqlSqlbv);
			sqlbv16.sql(tStandSQL);
			sqlbv16.put("mManageLen", mManageLen);
			sqlbv16.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv16.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv16.put("mStartDate", mStartDate);
			sqlbv16.put("mStartTime", mStartTime);
			sqlbv16.put("mEndDate", mEndDate);
			sqlbv16.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv16);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][7] = tStandSSRS.GetText(m, 2);
			}
			
			// 初始化最后合计行
			StringArray[row - 1][0] = "  合  计  ";
			StringArray[row - 1][1] = " 总 公 司 ";
			for (int j = 2; j < mMaxColCount; j++) {
				StringArray[row - 1][j] = "0";
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
			xmlexport.createDocument("AppOther03.vts", "printer");
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
			CError.buildErr(this, "统计时发生异常Oth03！") ;
			return false;
		}
		return true;
	}

	/*
	 * Oth05 银行转账情况表
	 */
	private boolean PrepareDataOth05() {
		try {
			int m; // 管理机构的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LDCom where char_length(trim(ComCode))="
					+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
					+ "?mManageCom0?"
					+ "' and comcode <='"
					+ "?mManageCom9?"+"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.put(this.mManageComSqlSqlbv);
			sqlbv17.sql(tsql);
			sqlbv17.put("mManageLen", mManageLen);
			sqlbv17.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv17.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			logger.debug("Comcode_SQL: " + tsql);
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv17));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLDComSet.size() ;
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

			// 初始化二维数组中的值
			for (int i = 0; i < row; i++) {
				for (int j = 2; j < mMaxColCount; j++) {
						StringArray[i][j] = "0"; // 件数					
				}
			}

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tStandSSRS = new SSRS();
			String tStandSQL = "";
//			tStandSQL = "select substr(managecom,1,"
//					+ mManageLen
//					+ ") submanage,count(distinct prtno) countPol "					
//					+ "from lccont  "
//					+ "where conttype='1' and paymode is not null and paymode='0'"
//					+ " and exists (select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0 )"
//					+ mSalechnlSql 
//					+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' "//有扫描件
//					+ PubFun.DateTimeBetween("makedate","maketime",mStartDate,mStartTime,mEndDate,mEndTime)
//					+ ") "					
//					+ "and managecom >='"
//					+ PubFun.RCh(mManageCom, "0", 8) + "' "
//					+ "and managecom <='" + PubFun.RCh(mManageCom, "9", 8)
//					+ "' " 
//					+ tManageComSql
//					+ " group by substr(managecom,1," + mManageLen
//					+ ")";
			//modify by duanyh 2009-08-05 调效
			tStandSQL = "select substr(managecom, 1, "+"?mManageLen?"+") submanage, count(distinct prtno) countPol from lcpol"
                     +" where conttype = '1' and mainpolno = polno and renewcount = 0 "
                     + mSalechnlSql			
	                 +" and exists (select 1 from lccont where contno = lcpol.contno "
//	                 +" and paymode is not null and paymode = '0'"
	                 +" and (PayLocation = '0' or newpaymode='0')"
	                 +")	"		
	                 +" and exists (select '1' from es_doc_main where doccode = lcpol.prtno and subtype = 'UA001'"
	                 + PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
		             +")"
		             + "and managecom >='"
					 + "?mManageCom0?" + "' "
					 + "and managecom <='" + "?mManageCom9?"
					 + "' " 
					 + tManageComSql
					 + " group by substr(managecom,1," + "?mManageLen?"
					 + ")";
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.put(this.mSalechnlSqlSqlbv);
			sqlbv18.put(this.mManageComSqlSqlbv);
			sqlbv18.sql(tStandSQL);
			sqlbv18.put("mManageLen", mManageLen);
			sqlbv18.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv18.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv18.put("mStartDate", mStartDate);
			sqlbv18.put("mStartTime", mStartTime);
			sqlbv18.put("mEndDate", mEndDate);
			sqlbv18.put("mEndTime", mEndTime);
			SSRS tSecondSSRS = new SSRS();
			String tSecondSQL = "";
//			tSecondSQL = "select substr(managecom,1,"
//				+ mManageLen
//				+ ") submanage,count(distinct prtno) countPol "					
//				+ "from lccont  "
//				+ "where conttype='1' "
//				+ " and exists (select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0 )"
//				+ mSalechnlSql 
//				+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' "//有扫描件
//				+ PubFun.DateTimeBetween("makedate","maketime",mStartDate,mStartTime,mEndDate,mEndTime)
//				+ ") "
//				
//				+ "and managecom >='"
//				+ PubFun.RCh(mManageCom, "0", 8) + "' "
//				+ "and managecom <='" + PubFun.RCh(mManageCom, "9", 8)
//				+ "' " 
//				+ tManageComSql
//				+ " group by substr(managecom,1," + mManageLen
//				+ ")";
			tSecondSQL = "select substr(managecom, 1, "+"?mManageLen?"+") submanage, count(distinct prtno) countPol	from lcpol"
                       +" where conttype = '1' and mainpolno = polno and renewcount = 0"
                       + mSalechnlSql	
                       +" and exists (select '1' from es_doc_main where doccode = lcpol.prtno and subtype = 'UA001'"
                       + PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
  		               +")"
  		               + "and managecom >='"
  					   + "?mManageCom0?" + "' "
  					   + "and managecom <='" + "?mManageCom9?"
  					   + "' " 
  					   + tManageComSql
  					   + " group by substr(managecom,1," + "?mManageLen?"
  					   + ")";
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.put(this.mSalechnlSqlSqlbv);
			sqlbv19.put(this.mManageComSqlSqlbv);
			sqlbv19.sql(tSecondSQL);
			sqlbv19.put("mManageLen", mManageLen);
			sqlbv19.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv19.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv19.put("mStartDate", mStartDate);
			sqlbv19.put("mStartTime", mStartTime);
			sqlbv19.put("mEndDate", mEndDate);
			sqlbv19.put("mEndTime", mEndTime);
			logger.debug("*****银行转账件数 tStandSQL: " + tStandSQL);
			logger.debug("*****预收件数 tSecondSQL: " + tSecondSQL);
			tStandSSRS = tExeSQL.execSQL(sqlbv18);
			tExeSQL = new ExeSQL();
			tSecondSSRS = tExeSQL.execSQL(sqlbv19);
			// 合并银行转账件数
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][2] = tStandSSRS.GetText(m, 2);
			}
			// 合并预收件数
			for (int n = 1; n <= tSecondSSRS.getMaxRow(); n++) {
				int tIndex = ((Integer) tHashtable.get(tSecondSSRS
						.GetText(n, 1))).intValue();
				// logger.debug("index: "+tIndex);
				StringArray[tIndex][3] = tSecondSSRS.GetText(n, 2);
				// 计算银行转账率
				if(Double.parseDouble(StringArray[tIndex][3])!=0)
					StringArray[tIndex][4] = mDecimalFormat.format(Double
							.parseDouble(StringArray[tIndex][2])/Double
							.parseDouble(StringArray[tIndex][3]));
			}			

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例

			xmlexport.createDocument("AppOther05.vts", "printer");

			ListTable alistTable = new ListTable();
			alistTable.setName("INFO");
			for (int i = 0; i < row; i++) {
				alistTable.add(StringArray[i]);
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
			CError.buildErr(this, "统计时发生异常Oth05！") ;
			return false;
		}
		return true;
	}
	
	/*
	 * Oth06 交费期间一览表
	 */
	private boolean PrepareDataOth06() {
		try {		
			int m; // 险种编码的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LMRiskApp where riskperiod='L' and subriskflag='M' order by riskcode";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(tsql);
			logger.debug("Comcode_SQL: " + tsql);
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
			LMRiskAppSchema tLMRiskAppSchema;
			tLMRiskAppSet.set(tLMRiskAppDB.executeQuery(sqlbv20));
			if (tLMRiskAppDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLMRiskAppSet.size()+1 ;
			logger.debug("险种数目：" + tLMRiskAppSet.size());

			strArr = new String[mMaxColCount];
			String StringArray[][] = new String[row][mMaxColCount];
			Hashtable tHashtable = new Hashtable();
			for (m = 0; m < tLMRiskAppSet.size(); m++) {
				tLMRiskAppSchema = new LMRiskAppSchema();
				tLMRiskAppSchema.setSchema(tLMRiskAppSet.get(m + 1));
				logger.debug(tLMRiskAppSchema.getRiskCode() + ":"
						+ tLMRiskAppSchema.getRiskName());
				StringArray[m][0] = tLMRiskAppSchema.getRiskCode();
				StringArray[m][1] = tLMRiskAppSchema.getRiskName();
				tHashtable.put(tLMRiskAppSchema.getRiskCode(), new Integer(m));
			}

			// 初始化二维数组中的值
			for (int i = 0; i < row; i++) {
				for (int j = 2; j < mMaxColCount; j++) {
						StringArray[i][j] = "0"; // 件数					
				}
			}

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tStandSSRS = new SSRS();
			String tStandSQL = "";
			tStandSQL = "select riskcode,count(*) countPol "					
					+ "from lcpol a "
					+ "where conttype='1'  and a.appflag in ('1','4') and polno=mainpolno "
					+ " and renewcount=0 "
					+ " and (payyears='1' or payintv='0') "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " group by riskcode";
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.put(this.mSalechnlSqlSqlbv);
			sqlbv21.put(this.mManageComSqlSqlbv);
			sqlbv21.sql(tStandSQL);
			sqlbv21.put("mManageLen", mManageLen);
			sqlbv21.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv21.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv21.put("mStartDate", mStartDate);
			sqlbv21.put("mStartTime", mStartTime);
			sqlbv21.put("mEndDate", mEndDate);
			sqlbv21.put("mEndTime", mEndTime);
			logger.debug("*****趸交/一次交/一年交 tStandSQL: " + tStandSQL);
			tStandSSRS = tExeSQL.execSQL(sqlbv21);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][2] = tStandSSRS.GetText(m, 2);
			}
			
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select riskcode,count(*) countPol "					
				+ "from lcpol a "
				+ "where conttype='1'  and a.appflag in ('1','4') and polno=mainpolno "
				+ " and renewcount=0 "
				+ " and payyears>'1' and payyears<='5' and payintv<>'0' "
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
				+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by riskcode";
			logger.debug("*****1-5（含5） tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.put(this.mSalechnlSqlSqlbv);
			sqlbv22.put(this.mManageComSqlSqlbv);
			sqlbv22.sql(tStandSQL);
			sqlbv22.put("mManageLen", mManageLen);
			sqlbv22.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv22.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv22.put("mStartDate", mStartDate);
			sqlbv22.put("mStartTime", mStartTime);
			sqlbv22.put("mEndDate", mEndDate);
			sqlbv22.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv22);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][3] = tStandSSRS.GetText(m, 2);
			}
			
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select riskcode,count(*) countPol "					
				+ "from lcpol a "
				+ "where conttype='1'  and a.appflag in ('1','4') and polno=mainpolno "
				+ " and renewcount=0 "
				+ " and payyears>'5' and payyears<='10' and payintv<>'0' "
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
				+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by riskcode";
			logger.debug("*****5-10（含10） tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.put(this.mSalechnlSqlSqlbv);
			sqlbv23.put(this.mManageComSqlSqlbv);
			sqlbv23.sql(tStandSQL);
			sqlbv23.put("mManageLen", mManageLen);
			sqlbv23.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv23.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv23.put("mStartDate", mStartDate);
			sqlbv23.put("mStartTime", mStartTime);
			sqlbv23.put("mEndDate", mEndDate);
			sqlbv23.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv23);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][4] = tStandSSRS.GetText(m, 2);
			}			
						
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select riskcode,count(*) countPol "					
				+ "from lcpol a "
				+ "where conttype='1'  and a.appflag in ('1','4') and polno=mainpolno "
				+ " and renewcount=0 "
				+ " and payyears>'10' and payyears<='15' and payintv<>'0' "
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
				+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by riskcode";
			logger.debug("*****10-15（含15） tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.put(this.mSalechnlSqlSqlbv);
			sqlbv24.put(this.mManageComSqlSqlbv);
			sqlbv24.sql(tStandSQL);
			sqlbv24.put("mManageLen", mManageLen);
			sqlbv24.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv24.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv24.put("mStartDate", mStartDate);
			sqlbv24.put("mStartTime", mStartTime);
			sqlbv24.put("mEndDate", mEndDate);
			sqlbv24.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv24);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][5] = tStandSSRS.GetText(m, 2);
			}
			
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select riskcode,count(*) countPol "					
				+ "from lcpol a "
				+ "where conttype='1'  and a.appflag in ('1','4') and polno=mainpolno "
				+ " and renewcount=0 "
				+ " and payyears>'15' and payyears<='20' and payintv<>'0' "
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
				+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by riskcode";
			logger.debug("*****15-20（含20） tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			sqlbv25.put(this.mSalechnlSqlSqlbv);
			sqlbv25.put(this.mManageComSqlSqlbv);
			sqlbv25.sql(tStandSQL);
			sqlbv25.put("mManageLen", mManageLen);
			sqlbv25.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv25.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv25.put("mStartDate", mStartDate);
			sqlbv25.put("mStartTime", mStartTime);
			sqlbv25.put("mEndDate", mEndDate);
			sqlbv25.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv25);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][6] = tStandSSRS.GetText(m, 2);
			}
			
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select riskcode,count(*) countPol "					
				+ "from lcpol a "
				+ "where conttype='1'  and a.appflag in ('1','4') and polno=mainpolno "
				+ " and renewcount=0 "
				+ " and payyears>'20' and payintv<>'0' "
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
				+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
				+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by riskcode";
			logger.debug("*****>20 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.put(this.mSalechnlSqlSqlbv);
			sqlbv26.put(this.mManageComSqlSqlbv);
			sqlbv26.sql(tStandSQL);
			sqlbv26.put("mManageLen", mManageLen);
			sqlbv26.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv26.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv26.put("mStartDate", mStartDate);
			sqlbv26.put("mStartTime", mStartTime);
			sqlbv26.put("mEndDate", mEndDate);
			sqlbv26.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv26);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][7] = tStandSSRS.GetText(m, 2);
			}
			
			// 初始化最后合计行
			StringArray[row - 1][0] = "  合  计  ";
			StringArray[row - 1][1] = "  ";
			for (int j = 2; j < mMaxColCount; j++) {
				StringArray[row - 1][j] = "0";
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
			xmlexport.createDocument("AppOther06.vts", "printer");
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
			CError.buildErr(this, "统计时发生异常Oth06！") ;
			return false;
		}
		return true;
	}

	/*
	 * Oth07 员工单统计表
	 */
	private boolean PrepareDataOth07() {
		try {		
			int m; // 管理机构的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LDCom where char_length(trim(ComCode))="
					+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
					+ "?mManageCom0?"
					+ "' and comcode <='"
					+ "?mManageCom9?"+"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.put(this.mManageComSqlSqlbv);
			sqlbv27.sql(tsql);
			sqlbv27.put("mManageLen", mManageLen);
			sqlbv27.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv27.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			logger.debug("Comcode_SQL: " + tsql);
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv27));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLDComSet.size()+1 ;
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

			// 初始化二维数组中的值
			for (int i = 0; i < row; i++) {
						StringArray[i][2] = "0"; // 件数	
						StringArray[i][3] = "0.0000"; // 保费	
			}
			String tSql = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ ") submanage,count(prtno)"
				    + ",(sum(premall)/10000) "
					+ " from ( "
					+ " select distinct(prtno) prtno,managecom,(select sum(prem) from lcpol where prtno = a.prtno ) premall " 
					+ "from lccont a "
					+ "where a.conttype='1' and a.appflag='1' "
					+ " and a.agentcode like '%999999' "
					+ " and exists (select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0 ) "
					+ mSalechnlSql
					+ "and a.managecom >='"
					+ "?mManageCom0?"
					+ "' "
					+ "and a.managecom <='"
					+ "?mManageCom9?"
					+ "' "
					+ tManageComSql
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001')"//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ " ) d "
					+ " group by substr(d.managecom,1," + "?mManageLen?"
					+ ")";						
			logger.debug("tSql: " + tSql);
			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
			sqlbv28.put(this.mSalechnlSqlSqlbv);
			sqlbv28.put(this.mManageComSqlSqlbv);
			sqlbv28.sql(tSql);
			sqlbv28.put("mManageLen", mManageLen);
			sqlbv28.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv28.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv28.put("mStartDate", mStartDate);
			sqlbv28.put("mStartTime", mStartTime);
			sqlbv28.put("mEndDate", mEndDate);
			sqlbv28.put("mEndTime", mEndTime);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv28);
			
			// 合并银行转账件数
			for (m = 1; m <= tSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][2] = tSSRS.GetText(m, 2);
				StringArray[tIndex][3] = tSSRS.GetText(m, 3);
			}
			
			// 初始化最后合计行
			StringArray[row - 1][0] = "  8  6  ";
			StringArray[row - 1][1] = " 总 公 司 ";
			for (int j = 2; j < mMaxColCount; j++) {
				StringArray[row - 1][j] = "0";
			}

			// 计算合计行各列的值
			for (int j = 2; j < mMaxColCount; j++) {
				for (int i = 0; i < row - 1; i++) {
					if (j == 2) {
						StringArray[row - 1][j] = String.valueOf(Integer
								.parseInt(StringArray[row - 1][j])
								+ Integer.parseInt(StringArray[i][j]));
					} else {
						StringArray[row - 1][j] = String.valueOf(Double
								.parseDouble(StringArray[row - 1][j])
								+ Double.parseDouble(StringArray[i][j]));
					}
				}
				if (j > 2) {
					StringArray[row - 1][j] = mDecimalFormat.format(Double
							.parseDouble(StringArray[row - 1][j]));
				}
			}	
			for (int i = 0; i < row - 1; i++) {				
					StringArray[i][3] = mDecimalFormat.format(Double
							.parseDouble(StringArray[i][3]));
			}

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("AppOther07.vts", "printer");
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
			CError.buildErr(this, "统计时发生异常Oth07！") ;
			return false;
		}
		return true;
	}
	
	/*
	 * Oth08 自保件情况表
	 */
	private boolean PrepareDataOth08() {
		try {		
			int m; // 管理机构的数量
			String tsql = "";
			String tManageComSql = " ";
			String strArr[] = null;
			tsql = "select * from LDCom where char_length(trim(ComCode))="
					+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
					+ "?mManageCom0?"
					+ "' and comcode <='"
					+ "?mManageCom9?"+"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
			sqlbv29.put(this.mManageComSqlSqlbv);
			sqlbv29.sql(tsql);
			sqlbv29.put("mManageLen", mManageLen);
			sqlbv29.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv29.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			logger.debug("Comcode_SQL: " + tsql);
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv29));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError.buildErr(this, "PolAppStatBL在读取数据库时发生错误") ;
				return false;
			}
			int row = tLDComSet.size()+1 ;
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

			// 初始化二维数组中的值
			for (int i = 0; i < row; i++) {
				for (int j = 2; j < mMaxColCount; j++) {
						StringArray[i][j] = "0"; // 件数					
				}
			}

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tStandSSRS = new SSRS();
			String tStandSQL = "";
//			modify by duanyh 2009-08-05 调效
//			tStandSQL = "select substr(managecom,1,"
//					+ mManageLen
//					+ ") submanage,count(distinct contno) countPol "					
//					+ "from lccont  "
//					+ "where conttype='1' "
//					+ " and exists (select '1' from lcappnt where contno=lccont.contno and occupationcode is not null and occupationcode='2070201' "
//					+ " union all select '1' from lcinsured where contno=lccont.contno and occupationcode is not null and occupationcode='2070201')"
//					+ " and exists (select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0 )"
//					+ mSalechnlSql 
//					+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' "//有扫描件
//					+ PubFun.DateTimeBetween("makedate","maketime",mStartDate,mStartTime,mEndDate,mEndTime)
//					+ ") "
//					+ "and managecom >='"
//					+ PubFun.RCh(mManageCom, "0", 8) + "' "
//					+ "and managecom <='" + PubFun.RCh(mManageCom, "9", 8)
//					+ "' " 
//					+ tManageComSql
//					+ " group by substr(managecom,1," + mManageLen
//					+ ")";
			tStandSQL = "select substr(managecom, 1, "+"?mManageLen?"+") submanage, count(distinct prtno) countPol from lcpol"
                      +" where conttype = '1' and mainpolno = polno and renewcount = 0 "
                      +" and exists (select '1' from lcappnt where contno = lcpol.contno and occupationcode = '2070201'"
                      +" union select '1' from lcinsured where contno = lcpol.contno and occupationcode = '2070201')"
                      + mSalechnlSql 
                      + " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' "//有扫描件
  					  + PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
  					  + ") "
  					  + "and managecom >='"
  					  + "?mManageCom0?" + "' "
  					  + "and managecom <='" + "?mManageCom0?"
  					  + "' " 
  					  + tManageComSql
  					  + " group by substr(managecom,1," + "?mManageLen?"
  					  + ")";
			SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
			sqlbv30.put(this.mSalechnlSqlSqlbv);
			sqlbv30.put(this.mManageComSqlSqlbv);
			sqlbv30.sql(tStandSQL);
			sqlbv30.put("mManageLen", mManageLen);
			sqlbv30.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv30.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv30.put("mStartDate", mStartDate);
			sqlbv30.put("mStartTime", mStartTime);
			sqlbv30.put("mEndDate", mEndDate);
			sqlbv30.put("mEndTime", mEndTime);
			SSRS tSecondSSRS = new SSRS();
			String tSecondSQL = "";
//			tSecondSQL = "select substr(managecom,1,"
//				+ mManageLen
//				+ ") submanage,count(distinct contno) countPol "					
//				+ "from lccont  "
//				+ "where conttype='1' "
//				+ " and exists (select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0 )"
//				+ mSalechnlSql 
//				+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' "//有扫描件
//				+ PubFun.DateTimeBetween("makedate","maketime",mStartDate,mStartTime,mEndDate,mEndTime)
//				+ ") "
//				+ "and managecom >='"
//				+ PubFun.RCh(mManageCom, "0", 8) + "' "
//				+ "and managecom <='" + PubFun.RCh(mManageCom, "9", 8)
//				+ "' " 
//				+ tManageComSql
//				+ " group by substr(managecom,1," + mManageLen
//				+ ")";
			tSecondSQL = "select substr(managecom, 1, "+"?mManageLen?"+") submanage, count(distinct prtno) countPol	from lcpol"
                       +" where conttype = '1' and mainpolno = polno and renewcount = 0"
                       + mSalechnlSql 
                       + " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' "//有扫描件
				       + PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
				       + ") "
				       + "and managecom >='"
				       + "?mManageCom0?" + "' "
				       + "and managecom <='" + "?mManageCom9?"
				       + "' " 
				       + tManageComSql
				       + " group by substr(managecom,1," + "?mManageLen?"
				       + ")";
			SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
			sqlbv31.put(this.mSalechnlSqlSqlbv);
			sqlbv31.put(this.mManageComSqlSqlbv);
			sqlbv31.sql(tSecondSQL);
			sqlbv31.put("mManageLen", mManageLen);
			sqlbv31.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv31.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv31.put("mStartDate", mStartDate);
			sqlbv31.put("mStartTime", mStartTime);
			sqlbv31.put("mEndDate", mEndDate);
			sqlbv31.put("mEndTime", mEndTime);
			logger.debug("*****自保件数 tStandSQL: " + tStandSQL);
			logger.debug("*****预收件数 tSecondSQL: " + tSecondSQL);
			tStandSSRS = tExeSQL.execSQL(sqlbv30);
			tExeSQL = new ExeSQL();
			tSecondSSRS = tExeSQL.execSQL(sqlbv31);
			// 合并自保件数
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable
						.get(tStandSSRS.GetText(m, 1))).intValue();
				StringArray[tIndex][2] = tStandSSRS.GetText(m, 2);
			}
			// 合并预收件数
			for (int n = 1; n <= tSecondSSRS.getMaxRow(); n++) {
				int tIndex = ((Integer) tHashtable.get(tSecondSSRS
						.GetText(n, 1))).intValue();
				// logger.debug("index: "+tIndex);
				StringArray[tIndex][3] = tSecondSSRS.GetText(n, 2);
				// 计算自保件率
				if(Double.parseDouble(StringArray[tIndex][3])!=0)
					StringArray[tIndex][4] = String.valueOf(Double
							.parseDouble(StringArray[tIndex][2])/Double
							.parseDouble(StringArray[tIndex][3]));
			}
			
			// 初始化最后合计行
			StringArray[row - 1][0] = "  8  6  ";
			StringArray[row - 1][1] = " 总 公 司 ";
			for (int j = 2; j < mMaxColCount; j++) {
				StringArray[row - 1][j] = "0";
			}

			// 计算合计行各列的值
			for (int j = 2; j < 4; j++) {
				if (j < 4) {
					for (int i = 0; i < row - 1; i++) {					
						StringArray[row - 1][j] = String.valueOf(Integer
								.parseInt(StringArray[row - 1][j])
								+ Integer.parseInt(StringArray[i][j]));
					}
				}
				else if (j == 4) {
					// 计算自保件率
					if(Double.parseDouble(StringArray[row - 1][3])!=0)
						StringArray[row - 1][4] = mDecimalFormat.format(Double
								.parseDouble(StringArray[row - 1][2])/Double
								.parseDouble(StringArray[row - 1][3]));
				}
			}	
			for (int i = 0; i < row - 1; i++) {				
					StringArray[i][4] = mDecimalFormat.format(Double
							.parseDouble(StringArray[i][4]));
			}

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("AppOther08.vts", "printer");
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
			CError.buildErr(this, "统计时发生异常Oth08！") ;
			return false;
		}
		return true;
	}

	/*
	 *Oth02 承保年龄段分布表
	 */
	private boolean PrepareDataOth02() {
		try {		
			int m; // 管理机构的数量
			String tManageComSql = " ";
			String strArr[] = null;			
			int row = 9+1 ;
			logger.debug("年龄段数目：" + 9);

			strArr = new String[mMaxColCount];
			String StringArray[][] = new String[row][mMaxColCount];

			// 初始化二维数组中的值
			for (int i = 0; i < row; i++) {
				for (int j = 1; j < mMaxColCount; j++) {
						StringArray[i][j] = "0"; // 件数					
				}
			}

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tStandSSRS = new SSRS();
			String tStandSQL = "";
			tStandSQL = "select ageind,agedes,countPol from ("
				    + " select count(distinct prtno) countPol,'0' agedes,'1' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)=0) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ " and managecom >='"
					+ "?mManageCom0?" + "' "
					+ " and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'1-2' agedes,'2' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=1 and get_age(b.birthday,a.polapplydate)<=2) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'3-5' agedes,'3' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=3 and get_age(b.birthday,a.polapplydate)<=5) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'6-17' agedes,'4' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=6 and get_age(b.birthday,a.polapplydate)<=17) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'18-40' agedes,'5' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=18 and get_age(b.birthday,a.polapplydate)<=40) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'41-50' agedes,'6' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=41 and get_age(b.birthday,a.polapplydate)<=50) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'51-55' agedes,'7' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=51 and get_age(b.birthday,a.polapplydate)<=55) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'56-60' agedes,'8' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=56 and get_age(b.birthday,a.polapplydate)<=60) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql					
					+ " union "
					+ "select count(distinct prtno) countPol,'61及以上' agedes,'9' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=61) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ ") M order by M.ageind"
					;
			logger.debug("*****标准体 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
			sqlbv32.put(this.mSalechnlSqlSqlbv);
			sqlbv32.sql(tStandSQL);
			sqlbv32.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv32.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv32.put("mStartDate", mStartDate);
			sqlbv32.put("mStartTime", mStartTime);
			sqlbv32.put("mEndDate", mEndDate);
			sqlbv32.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv32);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				StringArray[m-1][0] = tStandSSRS.GetText(m, 2);
				StringArray[m-1][1] = tStandSSRS.GetText(m, 3);
			}
			
			tStandSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tStandSQL = "select ageind,agedes,countPol from ("
				    + " select count(distinct prtno) countPol,'0' agedes,'1' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)=0) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ " and managecom >='"
					+ "?mManageCom0?" + "' "
					+ " and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'1-2' agedes,'2' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=1 and get_age(b.birthday,a.polapplydate)<=2) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'3-5' agedes,'3' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=3 and get_age(b.birthday,a.polapplydate)<=5) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'6-17' agedes,'4' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=6 and get_age(b.birthday,a.polapplydate)<=17) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'18-40' agedes,'5' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=18 and get_age(b.birthday,a.polapplydate)<=40) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'41-50' agedes,'6' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=41 and get_age(b.birthday,a.polapplydate)<=50) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'51-55' agedes,'7' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=51 and get_age(b.birthday,a.polapplydate)<=55) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'56-60' agedes,'8' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=56 and get_age(b.birthday,a.polapplydate)<=60) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql					
					+ " union "
					+ "select count(distinct prtno) countPol,'61及以上' agedes,'9' ageind "
					+ " from lccont a "
					+ " where conttype='1' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=61) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001') "//有扫描件
					+ PubFun.DateTimeBetween("uwdate","uwtime",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mStartTime,"?mStartTime?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),ReportPubFun.getParameterStr(mEndTime,"?mEndTime?"))
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ ") M order by M.ageind"
					;
			logger.debug("*****次标准体 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
			sqlbv33.put(this.mSalechnlSqlSqlbv);
			sqlbv33.sql(tStandSQL);
			sqlbv33.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv33.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv33.put("mStartDate", mStartDate);
			sqlbv33.put("mStartTime", mStartTime);
			sqlbv33.put("mEndDate", mEndDate);
			sqlbv33.put("mEndTime", mEndTime);
			tStandSSRS = tExeSQL.execSQL(sqlbv33);
			// 合并
			for (m = 1; m <= tStandSSRS.getMaxRow(); m++) {
				StringArray[m-1][2] = tStandSSRS.GetText(m, 3);
			}			
			
			// 初始化最后合计行
			StringArray[row - 1][0] = "  合  计  ";
			StringArray[row - 1][1] = "  ";
			for (int j = 1; j < mMaxColCount; j++) {
				StringArray[row - 1][j] = "0";
			}

			// 计算合计行各列的值
			for (int j = 1; j < mMaxColCount; j++) {
				for (int i = 0; i < row - 1; i++) {					
						StringArray[row - 1][j] = String.valueOf(Integer
								.parseInt(StringArray[row - 1][j])
								+ Integer.parseInt(StringArray[i][j]));
				}
			}	

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			xmlexport.createDocument("AppOther02.vts", "printer");
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
			CError.buildErr(this, "统计时发生异常Oth02！") ;
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
			if (mReportType.equals("Oth01") ) {
				if (!PrepareDataOth01())
					return false;
			}else			
			if ( mReportType.equals("Oth04")) {
				if (!PrepareDataOth04())
					return false;
			}			
//			if ( mReportType.equals("Oth04")) {
//				if (!commPrepareData())
//					return false;
//			}			
			// Oth02 承保年龄分布表
			else if (mReportType.equals("Oth02")) {
				if (!PrepareDataOth02())
					return false;
			}
			// Oth03 职业分布表
			else if (mReportType.equals("Oth03")) {
				if (!PrepareDataOth03())
					return false;
			}
			// Oth05 银行转账情况表
			else if (mReportType.equals("Oth05")) {
				if (!PrepareDataOth05())
					return false;
			}
			// Oth06 交费期间一览表
			else if (mReportType.equals("Oth06")) {
				if (!PrepareDataOth06())
					return false;
			}
			// Oth07 员工单统计表
			else if (mReportType.equals("Oth07")) {
				if (!PrepareDataOth07())
					return false;
			}
			// Oth08 自保件情况表
			else if (mReportType.equals("Oth08")) {
				if (!PrepareDataOth08())
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
		PolAppOtherStatBL PolAppOtherStatBL1 = new PolAppOtherStatBL();
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
		PolAppOtherStatBL1.submitData(tVData, "PRINT");
	}
}
