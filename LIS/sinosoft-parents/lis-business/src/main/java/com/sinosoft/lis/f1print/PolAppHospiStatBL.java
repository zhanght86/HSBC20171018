package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

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

public class PolAppHospiStatBL {
private static Logger logger = Logger.getLogger(PolAppHospiStatBL.class);
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

	public PolAppHospiStatBL() {
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
			ArrayList<String> list = new ArrayList<String>();
			//南区北区 managecom between '8600' and '8699'				
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
			
			mManageComSql += " '?ManageCom_List?') ";
			this.mManageComSqlSqlbv.put("ManageCom_List", list);
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
				+ "?mSalechnlType ?"
				+ "') ";
		if(mSalechnlType.equals("12"))
			mSalechnlSql = " and SaleChnl in ('05','06','08') ";
		else if(mSalechnlType.equals("13"))
			mSalechnlSql = " and SaleChnl in ('01','02','09','10','11') ";
		this.mSalechnlSqlSqlbv.put("mSalechnlType",mSalechnlType);
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
			SQLwithBindVariables tManageComSqlSqlbv = new SQLwithBindVariables();
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
				tManageComSqlSqlbv.put("mManageLen", mManageLen);
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
						+ "where GrpcontNo='00000000000000000000' "
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ mSalechnlSql 
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno "//有扫描件
						+ "and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "
						+ "and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))) "
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
						+ ",(select sum((case lccont.payintv when '1' then prem/0.09 when '3' then prem/0.27 when '6' then prem/0.52 when '12' then prem end)) from lcpol where contno = lccont.contno) prem "
						+ " from lccont  "
						+ " where GrpcontNo='00000000000000000000' and appflag='1' and payintv>0 "
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno ) "//有扫描件
						+ mSalechnlSql 
						+ " and (concat(concat(signdate,' '),signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
						+ " and (concat(concat(signdate,' '),signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"
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
						+ "where GrpcontNo='00000000000000000000'  and appflag='4' "
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno )"//有扫描件
						+ mSalechnlSql
						+ "and exists(select 1 from lpedoritem where contno=lccont.contno and edortype='WT' and EdorState='0' "
						+ " and edorvalidate>='"+ "?mStartDate?" + "'"
						+ " and edorvalidate<='" + "?mEndDate?" + "'"
						+ ") " + "and managecom >='"
						+ "?mManageCom0?" + "' "
						+ "and managecom <='" + "?mManageCom9?" + "' "
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
						+ "where GrpcontNo='00000000000000000000' and appflag='0' and uwflag='a'  "
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno )"//有扫描件
						+ mSalechnlSql
//						+ "and exists(select 1 from lccuwmaster where ProposalcontNo=lccont.ProposalcontNo and passflag='a' and modifydate>='"
//						+ mStartDate + "' " + "and modifydate<='" + mEndDate
//						+ "') " 
						+ " and (concat(concat(uwdate,' '),uwtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
						+ " and (concat(concat(uwdate,' '),uwtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"
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
						+ " where GrpcontNo='00000000000000000000' and appflag='0' and uwflag='2' " // 延期
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno )"//有扫描件
						+ mSalechnlSql
//						+ "and exists(select 1 from lccuwmaster where ProposalcontNo=lccont.ProposalcontNo and passflag='2' and modifydate>='"
//						+ mStartDate + "' " + "and modifydate<='" + mEndDate
//						+ "') " 
						+ " and (concat(concat(uwdate,' '),uwtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
						+ " and (concat(concat(uwdate,' '),uwtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"
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
						+ " where GrpcontNo='00000000000000000000' and appflag='0' and uwflag='1' " // 拒保
						+ " and exists(select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0) "
						+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno )"//有扫描件
						+ mSalechnlSql
//						+ "and exists(select 1 from lccuwmaster where ProposalcontNo=lccont.ProposalcontNo and passflag='1' and modifydate>='"
//						+ mStartDate + "' " + "and modifydate<='" + mEndDate
//						+ "') " 
						+ " and (concat(concat(uwdate,' '),uwtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
						+ " and (concat(concat(uwdate,' '),uwtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"
						+ " and managecom >='"
						+ "?mManageCom0?" + "' "
						+ " and managecom <='" + "?mManageCom9?" + "' "
						+ tManageComSql
						+ ") group by substr(managecom,1," + "?mManageLen?"
						+ ")";
			}

			logger.debug("*****SQL: " + tSQL);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.equals(this.mSalechnlSqlSqlbv);
			sqlbv5.equals(this.mManageComSqlSqlbv);
			sqlbv5.equals(tManageComSqlSqlbv);
			sqlbv5.sql(tSQL);
			sqlbv5.put("mStartDate", mStartDate);
			sqlbv5.put("mStartTime", mStartTime);
			sqlbv5.put("mEndDate", mEndDate);
			sqlbv5.put("mEndTime", mEndTime);
			sqlbv5.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv5.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv5.put("mManageLen", mManageLen);
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
	private boolean PrepareDataOth03() {
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
					+ " where GrpcontNo='00000000000000000000' and appflag='1' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='1') " //一类职业类别
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " group by substr(managecom,1,"
					+ "?mManageLen?"
					+ ")";
			logger.debug("*****一类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.put(this.mSalechnlSqlSqlbv);
			sqlbv7.put(this.mManageComSqlSqlbv);
			sqlbv7.sql(tStandSQL);
			sqlbv7.put("mManageLen", mManageLen);
			sqlbv7.put("mStartDate", mStartDate);
			sqlbv7.put("mStartTime", mStartTime);
			sqlbv7.put("mEndDate", mEndDate);
			sqlbv7.put("mEndTime", mEndTime);
			sqlbv7.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv7.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv7);
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
				+ " where GrpcontNo='00000000000000000000' and appflag='1' "
				+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
				+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='2') " //职业类别
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
				+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
				+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1,"
				+ "?mManageLen?"
				+ ")";
			logger.debug("*****二类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.put(this.mSalechnlSqlSqlbv);
			sqlbv8.put(this.mManageComSqlSqlbv);
			sqlbv8.sql(tStandSQL);
			sqlbv8.put("mManageLen", mManageLen);
			sqlbv8.put("mStartDate", mStartDate);
			sqlbv8.put("mStartTime", mStartTime);
			sqlbv8.put("mEndDate", mEndDate);
			sqlbv8.put("mEndTime", mEndTime);
			sqlbv8.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv8.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv8);
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
				+ " where GrpcontNo='00000000000000000000' and appflag='1' "
				+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
				+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='3') " //职业类别
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
				+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
				+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1,"
				+ "?mManageLen?"
				+ ")";
			logger.debug("*****三类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.put(this.mSalechnlSqlSqlbv);
			sqlbv9.put(this.mManageComSqlSqlbv);
			sqlbv9.sql(tStandSQL);
			sqlbv9.put("mManageLen", mManageLen);
			sqlbv9.put("mStartDate", mStartDate);
			sqlbv9.put("mStartTime", mStartTime);
			sqlbv9.put("mEndDate", mEndDate);
			sqlbv9.put("mEndTime", mEndTime);
			sqlbv9.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv9.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv9);
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
				+ " where GrpcontNo='00000000000000000000' and appflag='1' "
				+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
				+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='4') " //职业类别
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
				+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
				+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1,"
				+ "?mManageLen?"
				+ ")";
			logger.debug("*****四类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.put(this.mSalechnlSqlSqlbv);
			sqlbv10.put(this.mManageComSqlSqlbv);
			sqlbv10.sql(tStandSQL);
			sqlbv10.put("mManageLen", mManageLen);
			sqlbv10.put("mStartDate", mStartDate);
			sqlbv10.put("mStartTime", mStartTime);
			sqlbv10.put("mEndDate", mEndDate);
			sqlbv10.put("mEndTime", mEndTime);
			sqlbv10.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv10.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv10);
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
				+ " where GrpcontNo='00000000000000000000' and appflag='1' "
				+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
				+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='5') " //职业类别
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
				+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
				+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1,"
				+ "?mManageLen?"
				+ ")";
			logger.debug("*****五类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.put(this.mSalechnlSqlSqlbv);
			sqlbv11.put(this.mManageComSqlSqlbv);
			sqlbv11.sql(tStandSQL);
			sqlbv11.put("mManageLen", mManageLen);
			sqlbv11.put("mStartDate", mStartDate);
			sqlbv11.put("mStartTime", mStartTime);
			sqlbv11.put("mEndDate", mEndDate);
			sqlbv11.put("mEndTime", mEndTime);
			sqlbv11.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv11.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv11);
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
				+ " where GrpcontNo='00000000000000000000' and appflag='1' "
				+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
				+ " and exists(select '1' from lcinsured where contno=a.contno and occupationtype is not null and occupationtype='6') " //职业类别
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
				+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
				+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1,"
				+ "?mManageLen?"
				+ ")";
			logger.debug("*****六类职业类别 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.put(this.mSalechnlSqlSqlbv);
			sqlbv12.put(this.mManageComSqlSqlbv);
			sqlbv12.sql(tStandSQL);
			sqlbv12.put("mManageLen", mManageLen);
			sqlbv12.put("mStartDate", mStartDate);
			sqlbv12.put("mStartTime", mStartTime);
			sqlbv12.put("mEndDate", mEndDate);
			sqlbv12.put("mEndTime", mEndTime);
			sqlbv12.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv12.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv12);
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
	 * 05 体检医院体续签提示表
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
				tManageComSql = " and substr(managecom,1,"+ mManageLen + ") "+ mManageComSql;
			}
			logger.debug("Comcode_SQL: " + tsql);
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.put(this.mManageComSqlSqlbv);
			sqlbv13.sql(tsql);
			sqlbv13.put("mManageLen", mManageLen);
			sqlbv13.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv13.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv13));
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
			tStandSQL = "select substr(managecom,1,"
					+ "?mManageLen?"
					+ ") submanage,count(distinct prtno) countPol "					
					+ "from lccont  "
					+ "where GrpcontNo='00000000000000000000' and paymode is not null and paymode='0'"
					+ " and exists (select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0 )"
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno "//有扫描件
					+ "and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "
					+ "and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))) "
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " group by substr(managecom,1," + "?mManageLen?"
					+ ")";
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.put(this.mSalechnlSqlSqlbv);
			sqlbv14.put(this.mManageComSqlSqlbv);
			sqlbv14.sql(tStandSQL);
			sqlbv14.put("mManageLen", mManageLen);
			sqlbv14.put("mStartDate", mStartDate);
			sqlbv14.put("mStartTime", mStartTime);
			sqlbv14.put("mEndDate", mEndDate);
			sqlbv14.put("mEndTime", mEndTime);
			sqlbv14.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv14.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			SSRS tSecondSSRS = new SSRS();
			String tSecondSQL = "";
			tSecondSQL = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ ") submanage,count(distinct prtno) countPol "					
				+ "from lccont  "
				+ "where GrpcontNo='00000000000000000000' "
				+ " and exists (select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0 )"
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno "//有扫描件
				+ "and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "
				+ "and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))) "
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1," + "?mManageLen?"
				+ ")";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.put(this.mSalechnlSqlSqlbv);
			sqlbv15.put(this.mManageComSqlSqlbv);
			sqlbv15.sql(tSecondSQL);
			sqlbv15.put("mManageLen", mManageLen);
			sqlbv15.put("mStartDate", mStartDate);
			sqlbv15.put("mStartTime", mStartTime);
			sqlbv15.put("mEndDate", mEndDate);
			sqlbv15.put("mEndTime", mEndTime);
			sqlbv15.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv15.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			logger.debug("*****银行转账件数 tStandSQL: " + tStandSQL);
			logger.debug("*****预收件数 tSecondSQL: " + tSecondSQL);
			tStandSSRS = tExeSQL.execSQL(sqlbv14);
			tExeSQL = new ExeSQL();
			tSecondSSRS = tExeSQL.execSQL(sqlbv15);
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
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tsql);
			logger.debug("Comcode_SQL: " + tsql);
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
			LMRiskAppSchema tLMRiskAppSchema;
			tLMRiskAppSet.set(tLMRiskAppDB.executeQuery(sqlbv16));
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
					+ "where GrpcontNo='00000000000000000000'  and a.appflag='1' and polno=mainpolno "
					+ " and renewcount=0 "
					+ " and (payendyear='1' or payendyear='1000') and payendyearflag='Y'"
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " group by riskcode";
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.put(this.mSalechnlSqlSqlbv);
			sqlbv17.put(this.mManageComSqlSqlbv);
			sqlbv17.sql(tStandSQL);
			sqlbv17.put("mManageLen", mManageLen);
			sqlbv17.put("mStartDate", mStartDate);
			sqlbv17.put("mStartTime", mStartTime);
			sqlbv17.put("mEndDate", mEndDate);
			sqlbv17.put("mEndTime", mEndTime);
			sqlbv17.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv17.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			logger.debug("*****趸交/一次交/一年交 tStandSQL: " + tStandSQL);
			tStandSSRS = tExeSQL.execSQL(sqlbv17);
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
				+ "where GrpcontNo='00000000000000000000'  and a.appflag='1' and polno=mainpolno "
				+ " and renewcount=0 "
				+ " and payendyear>'1' and payendyear<='5' and payendyearflag='Y'"
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
				+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
				+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
				+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by riskcode";
			logger.debug("*****1-5（含5） tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.put(this.mSalechnlSqlSqlbv);
			sqlbv18.put(this.mManageComSqlSqlbv);
			sqlbv18.sql(tStandSQL);
			sqlbv18.put("mManageLen", mManageLen);
			sqlbv18.put("mStartDate", mStartDate);
			sqlbv18.put("mStartTime", mStartTime);
			sqlbv18.put("mEndDate", mEndDate);
			sqlbv18.put("mEndTime", mEndTime);
			sqlbv18.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv18.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv18);
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
				+ "where GrpcontNo='00000000000000000000'  and a.appflag='1' and polno=mainpolno "
				+ " and renewcount=0 "
				+ " and payendyear>'5' and payendyear<='10' and payendyearflag='Y'"
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
				+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
				+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
				+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by riskcode";
			logger.debug("*****5-10（含10） tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.put(this.mSalechnlSqlSqlbv);
			sqlbv19.put(this.mManageComSqlSqlbv);
			sqlbv19.sql(tStandSQL);
			sqlbv19.put("mManageLen", mManageLen);
			sqlbv19.put("mStartDate", mStartDate);
			sqlbv19.put("mStartTime", mStartTime);
			sqlbv19.put("mEndDate", mEndDate);
			sqlbv19.put("mEndTime", mEndTime);
			sqlbv19.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv19.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv19);
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
				+ "where GrpcontNo='00000000000000000000'  and a.appflag='1' and polno=mainpolno "
				+ " and renewcount=0 "
				+ " and payendyear>'10' and payendyear<='15' and payendyearflag='Y'"
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
				+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
				+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
				+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by riskcode";
			logger.debug("*****10-15（含15） tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.put(this.mSalechnlSqlSqlbv);
			sqlbv20.put(this.mManageComSqlSqlbv);
			sqlbv20.sql(tStandSQL);
			sqlbv20.put("mManageLen", mManageLen);
			sqlbv20.put("mStartDate", mStartDate);
			sqlbv20.put("mStartTime", mStartTime);
			sqlbv20.put("mEndDate", mEndDate);
			sqlbv20.put("mEndTime", mEndTime);
			sqlbv20.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv20.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv20);
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
				+ "where GrpcontNo='00000000000000000000'  and a.appflag='1' and polno=mainpolno "
				+ " and renewcount=0 "
				+ " and payendyear>'15' and payendyear<='20' and payendyearflag='Y'"
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
				+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
				+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
				+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by riskcode";
			logger.debug("*****15-20（含20） tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.put(this.mSalechnlSqlSqlbv);
			sqlbv21.put(this.mManageComSqlSqlbv);
			sqlbv21.sql(tStandSQL);
			sqlbv21.put("mManageLen", mManageLen);
			sqlbv21.put("mStartDate", mStartDate);
			sqlbv21.put("mStartTime", mStartTime);
			sqlbv21.put("mEndDate", mEndDate);
			sqlbv21.put("mEndTime", mEndTime);
			sqlbv21.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv21.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv21);
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
				+ "where GrpcontNo='00000000000000000000'  and a.appflag='1' and polno=mainpolno "
				+ " and renewcount=0 "
				+ " and payendyear>'20' and payendyearflag='Y'"
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
				+ " and exists (select '1' from LMRiskApp where riskcode=a.riskcode and riskperiod='L' and subriskflag='M')"
				+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
				+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by riskcode";
			logger.debug("*****>20 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.put(this.mSalechnlSqlSqlbv);
			sqlbv22.put(this.mManageComSqlSqlbv);
			sqlbv22.sql(tStandSQL);
			sqlbv22.put("mManageLen", mManageLen);
			sqlbv22.put("mStartDate", mStartDate);
			sqlbv22.put("mStartTime", mStartTime);
			sqlbv22.put("mEndDate", mEndDate);
			sqlbv22.put("mEndTime", mEndTime);
			sqlbv22.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv22.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv22);
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
			tsql = "select * from LDCom where length(trim(ComCode))="
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
			logger.debug("Comcode_SQL: " + tsql);
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.put(this.mManageComSqlSqlbv);
			sqlbv23.sql(tsql);
			sqlbv23.put("mManageLen", mManageLen);
			sqlbv23.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv23.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv23));
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
					+ "where a.GrpcontNo='00000000000000000000' and a.appflag='1' "
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
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno )"//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"								
					+ " ) d "
					+ " group by substr(d.managecom,1," + "?mManageLen?"
					+ ")";						
			logger.debug("tSql: " + tSql);
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.put(this.mSalechnlSqlSqlbv);
			sqlbv24.put(this.mManageComSqlSqlbv);
			sqlbv24.sql(tSql);
			sqlbv24.put("mManageLen", mManageLen);
			sqlbv24.put("mStartDate", mStartDate);
			sqlbv24.put("mStartTime", mStartTime);
			sqlbv24.put("mEndDate", mEndDate);
			sqlbv24.put("mEndTime", mEndTime);
			sqlbv24.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv24.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv24);
			
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
			tsql = "select * from LDCom where length(trim(ComCode))="
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
			logger.debug("Comcode_SQL: " + tsql);
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			sqlbv25.put(this.mManageComSqlSqlbv);
			sqlbv25.sql(tsql);
			sqlbv25.put("mManageLen", mManageLen);
			sqlbv25.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv25.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv25));
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
					+ ") submanage,count(distinct contno) countPol "					
					+ "from lccont  "
					+ "where GrpcontNo='00000000000000000000' "
					+ " and exists (select '1' from lcappnt where contno=lccont.contno and occupationcode is not null and occupationcode='2070201' "
					+ " union all select '1' from lcinsured where contno=lccont.contno and occupationcode is not null and occupationcode='2070201')"
					+ " and exists (select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0 )"
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno "//有扫描件
					+ "and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "
					+ "and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))) "
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " group by substr(managecom,1," + "?mManageLen?"
					+ ")";
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.put(this.mSalechnlSqlSqlbv);
			sqlbv26.put(this.mManageComSqlSqlbv);
			sqlbv26.sql(tStandSQL);
			sqlbv26.put("mManageLen", mManageLen);
			sqlbv26.put("mStartDate", mStartDate);
			sqlbv26.put("mStartTime", mStartTime);
			sqlbv26.put("mEndDate", mEndDate);
			sqlbv26.put("mEndTime", mEndTime);
			sqlbv26.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv26.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			SSRS tSecondSSRS = new SSRS();
			String tSecondSQL = "";
			tSecondSQL = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ ") submanage,count(distinct contno) countPol "					
				+ "from lccont  "
				+ "where GrpcontNo='00000000000000000000' "
				+ " and exists (select '1' from lcpol where contno=lccont.contno and mainpolno=polno and renewcount=0 )"
				+ mSalechnlSql 
				+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno "//有扫描件
				+ "and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "
				+ "and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))) "
				+ "and managecom >='"
				+ "?mManageCom0?" + "' "
				+ "and managecom <='" + "?mManageCom9?"
				+ "' " 
				+ tManageComSql
				+ " group by substr(managecom,1," + "?mManageLen?"
				+ ")";
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.put(this.mSalechnlSqlSqlbv);
			sqlbv27.put(this.mManageComSqlSqlbv);
			sqlbv27.sql(tSecondSQL);
			sqlbv27.put("mManageLen", mManageLen);
			sqlbv27.put("mStartDate", mStartDate);
			sqlbv27.put("mStartTime", mStartTime);
			sqlbv27.put("mEndDate", mEndDate);
			sqlbv27.put("mEndTime", mEndTime);
			sqlbv27.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv27.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			logger.debug("*****自保件数 tStandSQL: " + tStandSQL);
			logger.debug("*****预收件数 tSecondSQL: " + tSecondSQL);
			tStandSSRS = tExeSQL.execSQL(sqlbv26);
			tExeSQL = new ExeSQL();
			tSecondSSRS = tExeSQL.execSQL(sqlbv27);
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
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)=0) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ " and managecom >='"
					+ "?mManageCom0?" + "' "
					+ " and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'1-2' agedes,'2' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=1 and get_age(b.birthday,a.polapplydate)<=2) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))" 
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	    
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'3-5' agedes,'3' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=3 and get_age(b.birthday,a.polapplydate)<=5) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))" 
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	    
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'6-17' agedes,'4' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=6 and get_age(b.birthday,a.polapplydate)<=17) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))" 
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	    
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'18-40' agedes,'5' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=18 and get_age(b.birthday,a.polapplydate)<=40) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))" 
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	    
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'41-50' agedes,'6' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=41 and get_age(b.birthday,a.polapplydate)<=50) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))" 
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	    
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'51-55' agedes,'7' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=51 and get_age(b.birthday,a.polapplydate)<=55) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))" 
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	    
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'56-60' agedes,'8' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=56 and get_age(b.birthday,a.polapplydate)<=60) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))" 
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	    
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql					
					+ " union "
					+ "select count(distinct prtno) countPol,'61及以上' agedes,'9' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='9' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=61) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))" 
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	    
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ ") M order by M.ageind"
					;
			logger.debug("*****标准体 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
			sqlbv28.put(this.mSalechnlSqlSqlbv);
			sqlbv28.put(this.mManageComSqlSqlbv);
			sqlbv28.sql(tStandSQL);
			sqlbv28.put("mStartDate", mStartDate);
			sqlbv28.put("mStartTime", mStartTime);
			sqlbv28.put("mEndDate", mEndDate);
			sqlbv28.put("mEndTime", mEndTime);
			sqlbv28.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv28.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv28);
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
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)=0) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ " and managecom >='"
					+ "?mManageCom0?" + "' "
					+ " and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'1-2' agedes,'2' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=1 and get_age(b.birthday,a.polapplydate)<=2) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'3-5' agedes,'3' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=3 and get_age(b.birthday,a.polapplydate)<=5) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'6-17' agedes,'4' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=6 and get_age(b.birthday,a.polapplydate)<=17) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'18-40' agedes,'5' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=18 and get_age(b.birthday,a.polapplydate)<=40) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'41-50' agedes,'6' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=41 and get_age(b.birthday,a.polapplydate)<=50) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'51-55' agedes,'7' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=51 and get_age(b.birthday,a.polapplydate)<=55) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ " union "
					+ "select count(distinct prtno) countPol,'56-60' agedes,'8' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=56 and get_age(b.birthday,a.polapplydate)<=60) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql					
					+ " union "
					+ "select count(distinct prtno) countPol,'61及以上' agedes,'9' ageind "
					+ " from lccont a "
					+ " where GrpcontNo='00000000000000000000' and appflag='1' and uwflag='4' "
					+ " and exists(select '1' from lcpol where contno=a.contno and mainpolno=polno and renewcount=0) "
					+ " and exists(select '1' from lcinsured b where b.contno=a.contno and b.sequenceno is not null and b.sequenceno in ('1','-1') and get_age(b.birthday,a.polapplydate)>=61) "
					+ mSalechnlSql 
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno) "//有扫描件
					+ " and (concat(concat(a.signdate,' '),a.signtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"'))"
					+ " and (concat(concat(a.signdate,' '),a.signtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))"	
					+ "and managecom >='"
					+ "?mManageCom0?" + "' "
					+ "and managecom <='" + "?mManageCom9?"
					+ "' " 
					+ tManageComSql
					+ ") M order by M.ageind"
					;
			logger.debug("*****次标准体 tStandSQL: " + tStandSQL);
			SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
			sqlbv29.put(this.mSalechnlSqlSqlbv);
			sqlbv29.put(this.mManageComSqlSqlbv);
			sqlbv29.sql(tStandSQL);
			sqlbv29.put("mStartDate", mStartDate);
			sqlbv29.put("mStartTime", mStartTime);
			sqlbv29.put("mEndDate", mEndDate);
			sqlbv29.put("mEndTime", mEndTime);
			sqlbv29.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv29.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tStandSSRS = tExeSQL.execSQL(sqlbv29);
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
					+ "?mManageCom0?"
					+ "' and comcode <='"
					+ "?mManageCom9?" +"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			logger.debug("Comcode_SQL: " + tsql);
			SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
			sqlbv30.put(this.mManageComSqlSqlbv);
			sqlbv30.sql(tsql);
			sqlbv30.put("mManageLen", mManageLen);
			sqlbv30.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv30.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv30));
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
					+ "where "
					+ " exists(select 1 from lcpol where proposalcontno=LCPENotice.proposalcontno "
					+ " and GrpPolNo='00000000000000000000' and renewcount=0 "
					+ " and managecom >='" + "?mManageCom0?"
					+ "' " + "and managecom <='"
					+ "?mManageCom9?" + "' " 
					+ tManageComSql
					+ mSalechnlSql
					+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno )"//有扫描件
					+ ")" 
//					+ " and exists (select 1 from lwmission where activityid='0000001106' and MissionProp2=LCPENotice.contno "
					+ " and exists (select 1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010025') and MissionProp2=LCPENotice.contno "
					+ " and MissionProp3=LCPENotice.prtseq "
					+ " and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发体检时间
					+ " and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))" 
					+ " union all"
//					+ " select 1 from lbmission where activityid='0000001106' and MissionProp2=LCPENotice.contno "
					+ " select 1 from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010025') and MissionProp2=LCPENotice.contno "
					+ " and MissionProp3=LCPENotice.prtseq "
					+ " and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发体检时间
					+ " and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))) "
					+ " group by substr(managecom,1," + "?mManageLen?" + ") ";
			logger.debug("*****HealthSendSql: " + tHealthSendSql);
			SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
			sqlbv31.put(this.mSalechnlSqlSqlbv);
			sqlbv31.put(this.mManageComSqlSqlbv);
			sqlbv31.sql(tHealthSendSql);
			sqlbv31.put("mManageLen", mManageLen);
			sqlbv31.put("mStartDate", mStartDate);
			sqlbv31.put("mStartTime", mStartTime);
			sqlbv31.put("mEndDate", mEndDate);
			sqlbv31.put("mEndTime", mEndTime);
			sqlbv31.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv31.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			// 体检回复件数
			SSRS tSSRS2 = new SSRS();
			String tHealthBackSql = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ "),count(distinct proposalcontno) send from LCPENotice "
				+ "where "
				+ " exists(select 1 from lcpol where proposalcontno=LCPENotice.proposalcontno "
				+ " and GrpPolNo='00000000000000000000' and renewcount=0 "
				+ " and managecom >='" + "?mManageCom0?"
				+ "' " + "and managecom <='"
				+ "?mManageCom9?" + "' " 
				+ tManageComSql
				+ mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno )"//有扫描件
				+ ")" 
				+ " and peresult is not null "
				+ " and exists(select 1 from LCPENoticeReply where proposalcontno=LCPENotice.proposalcontno and prtseq=LCPENotice.prtseq "
				+ " and (concat(concat(modifydate,' '),modifytime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "
				+ " and (concat(concat(modifydate,' '),modifytime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))) "
				+ " group by substr(managecom,1," + "?mManageLen?" + ") ";
			logger.debug("*****HealthBackSql: " + tHealthBackSql);
			SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
			sqlbv32.put(this.mSalechnlSqlSqlbv);
			sqlbv32.put(this.mManageComSqlSqlbv);
			sqlbv32.sql(tHealthBackSql);
			sqlbv32.put("mManageLen", mManageLen);
			sqlbv32.put("mStartDate", mStartDate);
			sqlbv32.put("mStartTime", mStartTime);
			sqlbv32.put("mEndDate", mEndDate);
			sqlbv32.put("mEndTime", mEndTime);
			sqlbv32.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv32.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			// 体检阳性件数
			SSRS tSSRS3 = new SSRS();
			String tHealthQuestSql = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ "),count(distinct proposalcontno) send from LCPENotice "
				+ "where "
				+ " exists(select 1 from lcpol where proposalcontno=LCPENotice.proposalcontno "
				+ " and GrpPolNo='00000000000000000000' and renewcount=0 "
				+ " and managecom >='" + "?mManageCom0?"
				+ "' " + "and managecom <='"
				+ "?mManageCom9?" + "' " 
				+ tManageComSql
				+ mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno )"//有扫描件
				+ ")" 
				+ " and peresult is not null and peresult like '异常%'"
				+ " and exists(select 1 from LCPENoticeReply where proposalcontno=LCPENotice.proposalcontno and prtseq=LCPENotice.prtseq "
				+ " and (concat(concat(modifydate,' '),modifytime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "  
				+ " and (concat(concat(modifydate,' '),modifytime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))) "     
				+ " group by substr(managecom,1," + "?mManageLen?" + ") ";
			logger.debug("*****HealthQuestSql: " + tHealthQuestSql);
			SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
			sqlbv33.put(this.mSalechnlSqlSqlbv);
			sqlbv33.put(this.mManageComSqlSqlbv);
			sqlbv33.sql(tHealthQuestSql);
			sqlbv33.put("mManageLen", mManageLen);
			sqlbv33.put("mStartDate", mStartDate);
			sqlbv33.put("mStartTime", mStartTime);
			sqlbv33.put("mEndDate", mEndDate);
			sqlbv33.put("mEndTime", mEndTime);
			sqlbv33.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv33.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			tSSRS1 = tExeSQL.execSQL(sqlbv31);
			tExeSQL = new ExeSQL();
			tSSRS2 = tExeSQL.execSQL(sqlbv32);
			tExeSQL = new ExeSQL();
			tSSRS3 = tExeSQL.execSQL(sqlbv33);
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
					+ "?mManageCom0?"
					+ "' and comcode <='"
					+ "?mManageCom9?" +"'";
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tsql = tsql + " and comcode " + mManageComSql + " order by comcode";
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			logger.debug("Comcode_SQL: " + tsql);
			SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
			sqlbv34.put(this.mManageComSqlSqlbv);
			sqlbv34.sql(tsql);
			sqlbv34.put("mManageLen", mManageLen);
			sqlbv34.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv34.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv34));
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
				+ " exists(select 1 from lcpol where proposalcontno=LCRReport.proposalcontno "
				+ " and GrpPolNo='00000000000000000000' and renewcount=0 "
				+ " and managecom >='" + "?mManageCom0?"
				+ "' " + "and managecom <='"
				+ "?mManageCom9?" + "' " 
				+ tManageComSql
				+ mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno )"//有扫描件
				+ ")" 
				+ " and exists (select 1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010027') and MissionProp2=LCRReport.contno "
//				+ " and exists (select 1 from lwmission where activityid='0000001108' and MissionProp2=LCRReport.contno "
				+ " and MissionProp3=LCRReport.prtseq "
				+ " and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发生调时间
				+ " and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))" 
				+ " union all"
//				+ " select 1 from lbmission where activityid='0000001108' and MissionProp2=LCRReport.contno "
				+ " select 1 from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010027') and MissionProp2=LCRReport.contno "
				+ " and MissionProp3=LCRReport.prtseq "
				+ " and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发生调时间
				+ " and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))) "
				+ " group by substr(managecom,1," + "?mManageLen?" + ") ";
			logger.debug("*****发放件数SQL: " + tSQL);
			SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
			sqlbv35.put(this.mSalechnlSqlSqlbv);
			sqlbv35.put(this.mManageComSqlSqlbv);
			sqlbv35.sql(tSQL);
			sqlbv35.put("mManageLen", mManageLen);
			sqlbv35.put("mStartDate", mStartDate);
			sqlbv35.put("mStartTime", mStartTime);
			sqlbv35.put("mEndDate", mEndDate);
			sqlbv35.put("mEndTime", mEndTime);
			sqlbv35.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv35.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			//回复件数			
			String tSQLReply = "select substr(managecom,1,"
				+ "?mManageLen?"
				+ "),count(distinct proposalcontno) send from LCRReport "
				+ "where "
				+ " exists(select 1 from lcpol where proposalcontno=LCRReport.proposalcontno "
				+ " and GrpPolNo='00000000000000000000' and renewcount=0 "
				+ " and managecom >='" + "?mManageCom0?"
				+ "' " + "and managecom <='"
				+ "?mManageCom9?" + "' " 
				+ tManageComSql
				+ mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno )"//有扫描件
				+ ")" 
				+ " and replycontente is not null "
				+ " and (concat(concat(replydate,' '),replytime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "
				+ " and (concat(concat(replydate,' '),replytime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"')) "
				+ " group by substr(managecom,1," + "?mManageLen?" + ") ";
			SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
			sqlbv36.put(this.mSalechnlSqlSqlbv);
			sqlbv36.put(this.mManageComSqlSqlbv);
			sqlbv36.sql(tSQLReply);
			sqlbv36.put("mManageLen", mManageLen);
			sqlbv36.put("mStartDate", mStartDate);
			sqlbv36.put("mStartTime", mStartTime);
			sqlbv36.put("mEndDate", mEndDate);
			sqlbv36.put("mEndTime", mEndTime);
			sqlbv36.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv36.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
		    logger.debug("*****回复件数tSQL: " + tSQLReply);
		    SSRS tSSRS = new SSRS();
		    tSSRS = tExeSQL.execSQL(sqlbv35);
		    tExeSQL = new ExeSQL();
			SSRS tSSRS1 = new SSRS();
		    tSSRS1 = tExeSQL.execSQL(sqlbv36);
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
//				+ " ,decode((select nvl(count(*),0) from (select MissionProp2 from lwmission where activityid='0000001108' "
				+ " ,decode((select nvl(count(*),0) from (select MissionProp2 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010027') "
				+ " and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发生调时间
				+ " and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))" 
				+ " union all "
//				+ " select MissionProp2 from lbmission where activityid='0000001108' "
				+ " select MissionProp2 from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010027') "
				+ " and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发生调时间
				+ " and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))" 
//				+ " ) B where B.MissionProp2=LCRReport.contno),0,'',(select to_char(min(A.date1),'yyyy-mm-dd') from (select makedate date1,MissionProp2 from lwmission where activityid='0000001108' "
				+ " ) B where B.MissionProp2=LCRReport.contno),0,'',(select to_char(min(A.date1),'yyyy-mm-dd') from (select makedate date1,MissionProp2 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010027') "
				+ " and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发生调时间
				+ " and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))" 
				+ " union all"
//				+ " select makedate date1,MissionProp2 from lbmission where activityid='0000001108' "
				+ " select makedate date1,MissionProp2 from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010027') "
				+ " and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发生调时间
				+ " and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))" 
				+ " ) A where A.MissionProp2=LCRReport.contno)) senddate "
				+ " , (select nvl(max(replydate),'') from LCRReport d where d.proposalcontno = LCRReport.proposalcontno"
				+ " and (concat(concat(replydate,' '),replytime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发生调时间
				+ " and (concat(concat(replydate,' '),replytime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))" 
				+ " ) replydate "
				+ " ,(select decode(uwflag,null,'',(select codename from ldcode where codetype='uwstate' and code=uwflag)) from lccont where contno=LCRReport.contno "
				+ " and (concat(concat(uwdate,' '),uwtime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保结论时间
				+ " and (concat(concat(uwdate,' '),uwtime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))" 
				+ " ) "
				+ " from LCRReport "
				+ " where "
				+ " exists(select 1 from lcpol where proposalcontno=LCRReport.proposalcontno "
				+ " and GrpPolNo='00000000000000000000' and renewcount=0 "
				+ " and managecom >='" + "?mManageCom0?"
				+ "' " + "and managecom <='"
				+ "?mManageCom9?" + "' " 
				+ tManageComSql
				+ mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno )"//有扫描件
				+ " ) " 
//				+ " and exists (select 1 from lwmission where activityid='0000001108' and MissionProp2=LCRReport.contno "
				+ " and exists (select 1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010027') and MissionProp2=LCRReport.contno "
				+ " and MissionProp3=LCRReport.prtseq "
				+ " and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发生调时间
				+ " and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))" 
				+ " union all"
//				+ " select 1 from lbmission where activityid='0000001108' and MissionProp2=LCRReport.contno "
				+ " select 1 from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010027') and MissionProp2=LCRReport.contno "
				+ " and MissionProp3=LCRReport.prtseq "
				+ " and (concat(concat(makedate,' '),maketime))>=(concat(concat('" + "?mStartDate?" + "',' '),'"+ "?mStartTime?" +"')) "//核保下发生调时间
				+ " and (concat(concat(makedate,' '),maketime))<=(concat(concat('" + "?mEndDate?" + "',' '),'"+ "?mEndTime?" +"'))) "
				+ " order by managecom,proposalcontno ";
			logger.debug("*****生存调查清单SQL: " + tSql);
			SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
			sqlbv37.put(this.mSalechnlSqlSqlbv);
			sqlbv37.put(this.mManageComSqlSqlbv);
			sqlbv37.sql(tSql);
			sqlbv37.put("mManageLen", mManageLen);
			sqlbv37.put("mStartDate", mStartDate);
			sqlbv37.put("mStartTime", mStartTime);
			sqlbv37.put("mEndDate", mEndDate);
			sqlbv37.put("mEndTime", mEndTime);
			sqlbv37.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv37.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv37);
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
					+ "and lcpol.grppolno='00000000000000000000' and lcpol.renewcount=0  "
					+ "and lcuwmaster.passflag in ('1','2') "
					+ mSalechnlSql
					+ "and lcpol.managecom >='"
					+ "?mManageCom0?"
					+ "' "
					+ "and lcpol.managecom <='"
					+ "?mManageCom9?"
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
			SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
			sqlbv38.put(this.mSalechnlSqlSqlbv);
			sqlbv38.sql(sql);
			sqlbv38.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv38.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv38.put("mStartDate", mStartDate);
			sqlbv38.put("mEndDate", mEndDate);
			
			SSRS Back_ssrs = Back_exesql.execSQL(sqlbv38);

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
					+ "and grpcontno='00000000000000000000' "
					+ " and exists (select '1' from lcpol where polno=mainpolno and contno=lccont.contno and renewcount=0)"
					+ "and managecom >='" + "?mManageCom0?"
					+ "' " + "and managecom <='"
					+ "?mManageCom9?" + "' " + mSalechnlSql + ""
					+ "and makedate>='" + "?mStartDate?" + "' " + "and makedate<='"
					+ "?mEndDate?" + "' " + "group by managecom";

			ExeSQL WT_exesql = new ExeSQL();
			logger.debug("-----问题件sql---" + tQuestsql);
			SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
			sqlbv39.put(this.mSalechnlSqlSqlbv);
			sqlbv39.sql(tQuestsql);
			sqlbv39.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv39.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv39.put("mStartDate", mStartDate);
			sqlbv39.put("mEndDate", mEndDate);
			SSRS WT_ssrs = WT_exesql.execSQL(sqlbv39);
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
			// Oth02 承保年龄分布表
			if (mReportType.equals("01")) {
				if (!PrepareDataOth02())
					return false;
			}
			// Oth03 职业分布表
			else if (mReportType.equals("02")) {
				if (!PrepareDataOth02())
					return false;
			}
			// Oth05 银行转账情况表
			else if (mReportType.equals("03")) {
				if (!PrepareDataOth03())
					return false;
			}
			// Oth06 交费期间一览表
			else if (mReportType.equals("04")) {
				if (!PrepareDataOth05())
					return false;
			}
			// 05 体检医院体续签提示表
			else if (mReportType.equals("05")) {
				if (!PrepareDataOth05())
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
