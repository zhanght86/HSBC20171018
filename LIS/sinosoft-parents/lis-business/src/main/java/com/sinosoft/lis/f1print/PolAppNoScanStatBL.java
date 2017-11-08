package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.vschema.LDComSet;
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

public class PolAppNoScanStatBL {
private static Logger logger = Logger.getLogger(PolAppNoScanStatBL.class);
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
	ArrayList<String> list = new ArrayList<String>();
	private String mManageComSql; // 管理机构相关SQL
	private String mSalechnlName; // 销售渠道名称
	private String mTitle;
	private String mSubTitle = ""; // 子标题
	private int mMaxColCount = 0; // 最大列数
	private String mManageCom;
	DecimalFormat mDecimalFormat = new DecimalFormat("0.0000");

	public PolAppNoScanStatBL() {
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
			
			mManageComSql =  "'?list?')";
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
		if(mSalechnlType.equals("12"))
			mSalechnlSql = " and SaleChnl in ('05','06','08') ";
		else if(mSalechnlType.equals("13"))
			mSalechnlSql = " and SaleChnl in ('01','02','09','10','11') ";
			
		logger.debug("***mSalechnlType: " + mSalechnlType);
		logger.debug("***mSalechnlSql: " + mSalechnlSql);
		logger.debug("***mTitle: " + mTitle);		
		
		tExeSQL = new ExeSQL();
		tSSRS = new SSRS();
		String tReportSQL = "select codealias,comcode,othersign from ldcode where codetype='noscanreporttype' and code= '"
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
	 * 对以下几个报表数据准备（共性：共有四列，分别为：机构代码，机构名称，**件数，**保费） **表示具体的报表，如预收件数和预收保费
	 * 01 各机构无扫描件预收及承保情况表
	 */
	private boolean commPrepareData() {
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
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tsql);
			sqlbv4.put("list", list);
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
				for (int j = 2; j < mMaxColCount; j++) {
					if (j%2 == 0) {
						StringArray[i][j] = "0"; // 件数
					} else {
						StringArray[i][j] = "0.0000"; // 保费
					}
				}
			}

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tSQL = "select substr(managecom,1,"
					+ "?mManageLen?"
					+ ") submanage,count(pol) countPol,sum(prem)/10000 sumprem  from "
					+ "(select distinct(prtno) pol,managecom "
					+ ",(select sum(prem) from lcpol where prtno = lccont.prtno) prem "
					+ "from lccont "
					+ "where GrpcontNo='00000000000000000000' "
					+ " and exists(select '1' from lcpol where prtno=lccont.prtno and mainpolno=polno and renewcount=0) "
					+ " and not exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//无扫描件
					+ mSalechnlSql 
					+ " and (( makedate='"+"?mStartDate?"+"' and maketime >='"+"?mStartTime?"+"') "
					+ " or (makedate='"+"?mEndDate?"+"' and maketime<='"+"?mEndTime?"+"')"
					+ " or (makedate >'"+"?mStartDate?"+"' and makedate<'"+"?mEndDate?"+"'))"
					//+ "and (makedate||' '||maketime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"') "
					//+ "and (makedate||' '||maketime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"') "						
					+ "and managecom >='" + "?managecom0?"
					+ "' " + "and managecom <='"
					+ "?managecom9?" + "' "
					+ tManageComSql
					+ ") group by substr(managecom,1," + "?mManageLen?" + ")";

			logger.debug("*****无扫描预收SQL: " + tSQL);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL);
			sqlbv5.put("mManageLen", mManageLen);
			sqlbv5.put("list", list);
			sqlbv5.put("mSalechnlType", mSalechnlType);
			sqlbv5.put("mStartDate", mStartDate);
			sqlbv5.put("mStartTime", mStartTime);
			sqlbv5.put("mEndDate", mEndDate);
			sqlbv5.put("mEndTime", mEndTime);
			sqlbv5.put("managecom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv5.put("managecom9", PubFun.RCh(mManageCom, "9", 8));
			tSSRS = tExeSQL.execSQL(sqlbv5);
			// 合并数据
			for (m = 1; m <= tSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable.get(tSSRS.GetText(m, 1)))
						.intValue();
				StringArray[tIndex][2] = tSSRS.GetText(m, 2);
				StringArray[tIndex][3] = String.valueOf((Double
						.parseDouble(tSSRS.GetText(m, 3))));
			}
			
			tSSRS = new SSRS();
			tSQL = "select substr(managecom,1,"
					+ "?mManageLen?"
					+ ") submanage,count(pol) countPol,sum(prem)/10000 sumprem  from "
					+ "(select distinct(prtno) pol,managecom "
					+ ",(select sum(prem) from lcpol where prtno = lccont.prtno) prem "
					+ "from lccont "
					+ "where GrpcontNo='00000000000000000000' and appflag in ('1','4')"
					+ " and exists (select '1' from lcpol where prtno=lccont.prtno and mainpolno=polno and renewcount=0 )"
					+ " and not exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//无扫描件
					+ mSalechnlSql 
					+ " and (( signdate='"+"?mStartDate?"+"' and signtime >='"+"?mStartTime?"+"') "
					+ " or (signdate='"+"?mEndDate?"+"' and signtime<='"+"?mEndTime?"+"')"
					+ " or (signdate >'"+"?mStartDate?"+"' and signdate<'"+"?mEndDate?"+"'))"
					//+ " and (signdate||' '||signtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"')"
					//+ " and (signdate||' '||signtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')"
					+ "and managecom >='"
					+ "?managecom0?" + "' "
					+ "and managecom <='" + "?managecom9?"
					+ "' " 
					+ tManageComSql
					+ ") group by substr(managecom,1," + "?mManageLen?"
					+ ")";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSQL);
			sqlbv6.put("mManageLen", mManageLen);
			sqlbv6.put("list", list);
			sqlbv6.put("mSalechnlType", mSalechnlType);
			sqlbv6.put("mStartDate", mStartDate);
			sqlbv6.put("mStartTime", mStartTime);
			sqlbv6.put("mEndDate", mEndDate);
			sqlbv6.put("mEndTime", mEndTime);
			sqlbv6.put("managecom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv6.put("managecom9", PubFun.RCh(mManageCom, "9", 8));
			logger.debug("*****无扫描承保SQL: " + tSQL);
			SSRS tSSRS2 = new SSRS();
			tSSRS2 = tExeSQL.execSQL(sqlbv6);
			// 合并数据
			for (m = 1; m <= tSSRS2.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable.get(tSSRS2.GetText(m, 1)))
						.intValue();
				StringArray[tIndex][4] = tSSRS2.GetText(m, 2);
				StringArray[tIndex][5] = String.valueOf(Double
						.parseDouble(tSSRS2.GetText(m, 3)));
			}

			// 计算合计行各列的值
			for (int j = 2; j < mMaxColCount; j++) {//mMaxColCount=6
				for (int i = 0; i < row - 1; i++) {
					if (j%2 == 0) {
						StringArray[row - 1][j] = String.valueOf(Integer
								.parseInt(StringArray[row - 1][j])
								+ Integer.parseInt(StringArray[i][j]));
					}
					else{
						StringArray[row - 1][j] = String.valueOf(Double
								.parseDouble(StringArray[row - 1][j])
								+ Double.parseDouble(StringArray[i][j]));
						
						StringArray[i][j] = mDecimalFormat.format(Double
								.parseDouble(StringArray[i][j]));
					}
				}

				if (j%2 != 0) {
					StringArray[row - 1][j] = mDecimalFormat.format(Double
							.parseDouble(StringArray[row - 1][j]));
				}
			}

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例

			xmlexport.createDocument("NoScanApp01.vts", "printer");

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
			CError.buildErr(this, "统计"+mTitle+"时发生异常!") ;
			return false;
		}
		return true;
	}	

	/*
	 * 02 机构无扫描件险种承保一览表
	 */
	private boolean PrepareDataApp02() {
		try {			
			String tManageComSql = " ";			
			if("N".equals(mManageType)||"S".equals(mManageType))
			{
				//南区北区 managecom between '8600' and '8699'				
				tManageComSql = " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tSQL = "select riskcode,(select riskname from lmriskapp where riskcode=e.riskcode),countall,premall/10000,flag "
			    + " from "
				+ "(select riskcode,count(distinct prtno) countall,sum(prem) premall "
				+ ",decode((select subriskflag from lmriskapp where riskcode = d.riskcode),'M','1','0') flag "
				+ " from ( "
				+ "select riskcode,prtno,prem " 
				+ "from lcpol a "
				+ "where  a.GrpcontNo='00000000000000000000' and a.appflag in ('1','4') "
				+ mSalechnlSql
				+ "and a.managecom >='"
				+ "?managecom0?"
				+ "' "
				+ "and a.managecom <='"
				+ "?managecom9?"
				+ "' "
				+ tManageComSql
				+ " and not exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001' )"//有扫描件
				+ " and (( a.signdate='"+"?mStartDate?"+"' and a.signtime >='"+"?mStartTime?"+"') "
				+ " or (a.signdate='"+"?mEndDate?"+"' and a.signtime<='"+"?mEndTime?"+"')"
				+ " or (a.signdate >'"+"?mStartDate?"+"' and a.signdate<'"+"?mEndDate?"+"'))"
				//+ " and (a.signdate||' '||a.signtime)>=('" + mStartDate + "'||' '||'"+ mStartTime +"')"
				//+ " and (a.signdate||' '||a.signtime)<=('" + mEndDate + "'||' '||'"+ mEndTime +"')"
				+ " and a.renewcount=0 "									
				+ " ) d "
				+ "group by d.riskcode  " + ") e ";
				if(mRiskCode!=null && !mRiskCode.equals(""))
					tSQL = tSQL + " where e.riskcode='"+ "?mRiskCode?" +"' ";
				
				tSQL = tSQL + " order by flag desc,riskcode  ";	
			logger.debug("*****无扫描险种预收SQL: " + tSQL);
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tSQL);
			sqlbv7.put("mManageLen", mManageLen);
			sqlbv7.put("list", list);
			sqlbv7.put("mSalechnlType", mSalechnlType);
			sqlbv7.put("mStartDate", mStartDate);
			sqlbv7.put("mStartTime", mStartTime);
			sqlbv7.put("mEndDate", mEndDate);
			sqlbv7.put("mEndTime", mEndTime);
			sqlbv7.put("managecom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv7.put("managecom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv7.put("mRiskCode", mRiskCode);
			tSSRS = tExeSQL.execSQL(sqlbv7);
			int row = tSSRS.getMaxRow() + 1;
			String[] strArr = new String[mMaxColCount];
			String StringArray[][] = new String[row][mMaxColCount];
			int m;			
			
			// 合并数据
			for (m = 1; m <= tSSRS.getMaxRow(); m++) {
				for (int j = 0; j < mMaxColCount; j++) {
					StringArray[m - 1][j] = tSSRS.GetText(m , j + 1);
				}
			}	
			
			// 初始化最后合计行
			StringArray[m - 1][0] = "合   计";
			for (int j = 2; j < mMaxColCount; j++) {
				StringArray[m - 1][j] = "0";
			}

			// 计算合计行各列的值
			for (int j = 2; j < mMaxColCount; j++) {
				for (int i = 0; i < row - 1; i++) {
					if (j%2 == 0) {
						StringArray[row - 1][j] = String.valueOf(Integer
								.parseInt(StringArray[row - 1][j])
								+ Integer.parseInt(StringArray[i][j]));
					}
					else{
						StringArray[row - 1][j] = String.valueOf(Double
								.parseDouble(StringArray[row - 1][j])
								+ Double.parseDouble(StringArray[i][j]));
						
						StringArray[i][j] = mDecimalFormat.format(Double
								.parseDouble(StringArray[i][j]));
					}
				}

				if (j%2 != 0) {
					StringArray[row - 1][j] = mDecimalFormat.format(Double
							.parseDouble(StringArray[row - 1][j]));
				}
			}

			TextTag texttag = new TextTag();// 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例

			xmlexport.createDocument("NoScanApp02.vts", "printer");

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
			CError.buildErr(this, "统计"+mTitle+"时发生异常!") ;
			return false;
		}
		return true;
	}

	private boolean dealData() {
		try {
			/**
			 * 对以下几个报表数据准备（共性：共有四列，分别为：机构代码，机构名称，**件数，**保费） **表示具体的报表，如预收件数和预收保费
			 * 01 无扫描件预收及承保情况表  02 无扫描件预收及承保情况表
			 */
			if (mReportType.equals("01")) {
				if (!commPrepareData())
					return false;
			}			
			// 02 无扫描件预收及承保情况表
			else if (mReportType.equals("02")) {
				if (!PrepareDataApp02())
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
