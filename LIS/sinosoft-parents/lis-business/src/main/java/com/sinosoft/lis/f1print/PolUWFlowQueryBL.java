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
import com.sinosoft.lis.pubfun.ZHashReport;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.vschema.LDComSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.F1PrintVTS;
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

public class PolUWFlowQueryBL {
private static Logger logger = Logger.getLogger(PolUWFlowQueryBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGI = new GlobalInput();
	private String mStartDate; // 保单生效开始日期
	private String mEndDate; // 保单生效结束日期
	private String mManageType = "2"; // 管理机构的类型，如省级分公司(管理机构为4位,如8611)为2，省级公司下的分公司(861100)为3,营销服务部(86110000)为4
	private String mUWSalechnl; // 销售渠道类型
	private String mUWReportType; // 统计报表类型
	private String mUWSubReportType;//有些报表用同一个模板，除了标题，还有一些地方不能相同
	private int mManageLen; // 记录管理机构的长度 根据管理机构的类型(mManageType)
	// mManageLen=2*mManageType
	private String mSalechnlSql; // 销售渠道相关SQL
	private String mManageComSql; // 管理机构相关SQL
	ArrayList<String> list = new ArrayList<String>();
	private String mSalechnlName; // 销售渠道名称
	private String mTitle;
	private SQLwithBindVariables  tComCodesql = null;
	private String mManageCom;
	private F1PrintVTS tF1PrintVTS;
	private LDComSet mLDComSet = new LDComSet();
	DecimalFormat mDecimalFormat = new DecimalFormat("0.0000");

	public PolUWFlowQueryBL() {
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
		mUWSalechnl = (String) cInputData.get(3);
		mUWReportType = (String) cInputData.get(4);
		if (mStartDate == null || mEndDate == null || mManageType == null
				|| mUWSalechnl == null || mUWReportType == null) {
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
		// 1.校验机构了类型
		try {
			if (
					!"2".equals(mManageType) && 
					!"3".equals(mManageType) && 
					!"n".equals(mManageType) && 
					!"s".equals(mManageType)
					) {
				CError.buildErr(this, "机构类型错误!");
				return false;
			} else {
				if ("2".equals(mManageType)) {
					mTitle = "二级机构";
				}
				if ("3".equals(mManageType)) {
					mTitle = "三级机构";
				}
//			if ("4".equals(mManageType)) {
//				mTitle = "四级机构";
//			}
				if ("n".equals(mManageType)){
					//北区
					mTitle = "北区";
				}
				if ("s".equals(mManageType)){
					//南区
					mTitle = "南区";
				}
				if("2".equals(mManageType)||"3".equals(mManageType)){
				mManageLen = 2 * Integer.parseInt(mManageType);
				}else{
					mManageLen = 4;
				}
				
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = new SSRS();
				if("n".equals(mManageType)||"s".equals(mManageType))
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
//						if(i>1)
//							mManageComSql = mManageComSql + ",";
//						mManageComSql = mManageComSql + "'"+tSSRS.GetText(i, 1)+"'";
					}					
					
					mManageComSql =  "'?list?') ";
				}  
				else
					mManageComSql = " ";
				logger.debug("***mManageComSql: " + mManageComSql);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("***mManageLen: " + mManageLen);

		// 2。校验销售渠道类型
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tSQL = "select codename,codealias from ldcode where codetype='appsalechnl' and code= '"
				+ "?mUWSalechnl?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("mUWSalechnl", mUWSalechnl);
		tSSRS = tExeSQL.execSQL(sqlbv2);
		if (tSSRS == null || tSSRS.getMaxRow() < 1) {
			CError.buildErr(this, "销售渠道类型错误!") ;
			return false;
		}

		logger.debug("***mSalechnl: " + tSSRS.GetText(1, 1) + "   "
				+ tSSRS.GetText(1, 2));
		mSalechnlName = tSSRS.GetText(1, 1);
		mSalechnlSql = " = '"
			//+ splitCat(tSSRS.GetText(1, 2), "/") 
			+ "?mUWSalechnl?"
			+ "' ";
		if(mUWSalechnl.equals("12"))
			mSalechnlSql = " in ('05','06','08') ";
		else if(mUWSalechnl.equals("13"))
			mSalechnlSql = " in ('01','02','09','10','11') ";

		logger.debug("***mSalechnlSql: " + mSalechnlSql);
		logger.debug("***mTitle: " + mTitle);

		return true;
	}

	/**
	 * 对以下几个报表数据准备（共性：共有四列，分别为：机构代码，机构名称，**件数，**保费） **表示具体的报表，如预收件数和预收保费 App01
	 * 机构预收报表 App03 机构犹豫期撤单表 App04 机构期交承保情况表 App05 机构出单前撤单表 App06 机构延期情况表 App07
	 * 机构拒保情况表
	 */
	private boolean PrePareDataUW01() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW01.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			aListTable.setName("LIST");
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL="";
			tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tReportSQL);
			sqlbv3.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv3);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}

			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			//ldcode中描述了数组的大小 模板的列数
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			//区域不同有些查询逻辑也是不同的 采用此种方法原因是 先将本次要统计的管理机构放到hashtable中
            //然后在从数据库中取出不通区域管理机构的数据放入缓存 从而防止多次查询数据库 再通过管理机构为标志提取数据
			String addSQL="";
			String addSQL2="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
				addSQL2=" and b.managecom >= '"
					+ "?mManageCom0?"
					+ "' and b.managecom <= '"
					+ "?mManageCom9?"
					+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
				addSQL2=" and b.managecom >= '"
					+ "?mManageCom0?"
					+ "' and b.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(b.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			
			String strArr[] = null;
			int m; // 管理机构的数量
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			int row = mLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + mLDComSet.size());

			LDComSchema tLDComSchema;
			strArr = new String[maxColCount];
			String StringArray[][] = new String[row][maxColCount];
			Hashtable tHashtable = new Hashtable();
			for (m = 0; m < mLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(mLDComSet.get(m + 1));
				logger.debug(tLDComSchema.getComCode() + ":"
						+ tLDComSchema.getShortName());
				StringArray[m][0] = tLDComSchema.getComCode();
				StringArray[m][1] = tLDComSchema.getShortName();
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			
			DecimalFormat mDecimalFormat = new DecimalFormat("0");
			int hbmult=0;
			double hbprem=0;
			int fgmult=0;
			double fgprem=0;
			int xtmult=0;
			double xtprem=0;
			//String tSQL="";
			String tSQL_HB="";
			String tSQL_FG="";
			String tSQL_XT="";

				tSQL_HB ="select B.x,(select ShortName from ldcom where comcode = B.x),B.y,B.z from ("
					+ "select substr(A.managecom,1,"+"?mManageLen?"+") x"
					+ " ,count(1) y,sum(A.prem) z from "
					+ " (select a.managecom "
					+ " ,(select sum(c.prem) / 10000 from lcpol c where c.contno=a.contno and c.renewcount=0) prem"
					+ " from lccont a where a.conttype = '1'"
					+ ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					+ " and a.salechnl " + mSalechnlSql
					+ addSQL
					+ " and not exists(select 1 from lcapplyrecallpol b where b.prtno = a.prtno)"
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001' )"//有扫描件
					+ " ) A group by substr(A.managecom,1,"+"?mManageLen?"+")"
					+ " ) B "
					;
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSQL_HB);
				sqlbv4.put("mManageLen", mManageLen);
				sqlbv4.put("mStartDate", mStartDate);
				sqlbv4.put("mEndDate", mEndDate);
				sqlbv4.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
				sqlbv4.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
				sqlbv4.put("list", list);
				sqlbv4.put("mUWSalechnl", mUWSalechnl);
				
				tSQL_FG ="select B.x,(select ShortName from ldcom where comcode = B.x),B.y,B.z from ("
					+ "select substr(A.managecom,1,"+"?mManageLen?"+") x"
					+ " ,count(1) y,sum(A.prem) z from "
					+ " (select a.managecom "
					+ " ,(select sum(c.prem) / 10000 from lcpol c where c.contno=b.contno and c.renewcount=0) prem"
					+ " from lcapplyrecallpol a, lccont b where a.prtno = b.prtno "
					+ " and b.conttype = '1' and a.operator <> 'AUTO'"
					+ " and exists (select '1' from es_doc_main where doccode=b.prtno and subtype = 'UA001' )"//有扫描件
					+ ReportPubFun.getWherePart("b.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					+ " and b.salechnl " + mSalechnlSql
					+ addSQL
					+ " ) A group by substr(A.managecom,1,"+"?mManageLen?"+")"
					+ " ) B "
					;
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(tSQL_FG);
				sqlbv5.put("mManageLen", mManageLen);
				sqlbv5.put("mStartDate", mStartDate);
				sqlbv5.put("mEndDate", mEndDate);
				sqlbv5.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
				sqlbv5.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
				sqlbv5.put("list", list);
				sqlbv5.put("mUWSalechnl", mUWSalechnl);
				
				tSQL_XT ="select B.x,(select ShortName from ldcom where comcode = B.x),B.y,B.z from ("
					+ "select substr(A.managecom,1,"+"?mManageLen?"+") x"
					+ " ,count(1) y,sum(A.prem) z from "
					+ " (select b.managecom "
					+ " ,(select sum(c.prem) / 10000 from lcpol c where c.contno=b.contno and c.renewcount=0) prem"
					+ " from lcapplyrecallpol a, lccont b where a.prtno = b.prtno "
					+ " and b.conttype = '1' and a.operator = 'AUTO'"
					+ " and exists (select '1' from es_doc_main where doccode=b.prtno and subtype = 'UA001' )"//有扫描件
					+ ReportPubFun.getWherePart("b.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					+ " and b.salechnl " + mSalechnlSql
					+ addSQL2
					+ " ) A group by substr(A.managecom,1,"+"?mManageLen?"+")"
					+ " ) B "
					;
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(tSQL_XT);
				sqlbv6.put("mManageLen", mManageLen);
				sqlbv6.put("mStartDate", mStartDate);
				sqlbv6.put("mEndDate", mEndDate);
				sqlbv6.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
				sqlbv6.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
				sqlbv6.put("list", list);
				sqlbv6.put("mUWSalechnl", mUWSalechnl);
//				开始调用ZHashReport
			      VData tVData = new VData();
			      //tVData.add(tSQL);
			      tVData.add(sqlbv4);
			      tVData.add(sqlbv5);
			      tVData.add(sqlbv6);
			      ZHashReport tZHashReport = new ZHashReport(tComCodesql,tVData);		           
			      
			      tZHashReport.setColumnType(1,tZHashReport.StringType);//机构名称
			      tZHashReport.setDoformat(false);
			      String[][] tStringResult = tZHashReport.calItem();
			      logger.debug("tStringResult:"+tStringResult.length);
			      int n = 0;
			      n=tStringResult.length;
			      if(n!=0)
			      {
			    	  if(n <= 3000)
				      {
					     n = tStringResult.length;
				      }else
				    	  n=3000;
//			    	  logger.debug("第"+(i+1)+"次有数据！");
			    	  aListTable.setName("UW01");
			    	  
			    	  
 			    	  for (m = 0; m <n; m++) {
			    		  logger.debug("tStringResult[m][0]:"+tStringResult[m][0]);
							int tIndex = ((Integer) tHashtable
									.get(tStringResult[m][0])).intValue();
							StringArray[tIndex][0] = tStringResult[m][0];
							StringArray[tIndex][1] = tStringResult[m][1];
							StringArray[tIndex][2] = mDecimalFormat.format(Double
									.parseDouble(tStringResult[m][2]));
							StringArray[tIndex][3] = 
								String.valueOf(PubFun.round(Double
									.parseDouble(tStringResult[m][3]),4));
							StringArray[tIndex][4] = mDecimalFormat.format(Double
									.parseDouble(tStringResult[m][4]));
							StringArray[tIndex][5] = 
								String.valueOf(PubFun.round(Double
									.parseDouble(tStringResult[m][5]),4));
							StringArray[tIndex][6] = mDecimalFormat.format(Double
									.parseDouble(tStringResult[m][6]));
							StringArray[tIndex][7] = 
								String.valueOf(PubFun.round(Double
									.parseDouble(tStringResult[m][7]),4));
							logger.debug("StringArray[tIndex][3]:"+StringArray[tIndex][3]);
							hbmult = hbmult+Integer.parseInt(StringArray[tIndex][2]);
				        	hbprem = hbprem+Double.parseDouble(StringArray[tIndex][3]);
				        	fgmult = fgmult+Integer.parseInt(StringArray[tIndex][4]);
				        	fgprem = fgprem+Double.parseDouble(StringArray[tIndex][5]);
				        	xtmult = xtmult+Integer.parseInt(StringArray[tIndex][6]);
				        	xtprem = xtprem+Double.parseDouble(StringArray[tIndex][7]);
				        	logger.debug("hbprem:"+hbprem);
						}
			    	  for (int i = 0; i < row; i++) {
							strArr = new String[maxColCount];
							if(StringArray[i][0]!=null){
								strArr = StringArray[i];
								aListTable.add(strArr);
							}
						}
			      }
			strArr =new String[8];
			 strArr[0]="86";
			 strArr[1]="总公司";
			 strArr[2]=String.valueOf(hbmult);
			 strArr[3]=String.valueOf(PubFun.round(Double.parseDouble(String.valueOf(hbprem)),4));
			 strArr[4]=String.valueOf(fgmult);
			 strArr[5]=String.valueOf(PubFun.round(Double.parseDouble(String.valueOf(fgprem)),4));
			 strArr[6]=String.valueOf(xtmult);
			 strArr[7]=String.valueOf(PubFun.round(Double.parseDouble(String.valueOf(xtprem)),4));
			 aListTable.add(strArr);

		      xmlexport.createDocument("UW01.vts","printer");//最好紧接着就初始化xml文档
		      xmlexport.addListTable(aListTable, strArr);
		      
		      String CurrentDate = PubFun.getCurrentDate();
				TextTag texttag = new TextTag(); // 新建一个TextTag的实例
				texttag.add("StartDate", mStartDate);
				texttag.add("EndDate", mEndDate);
				texttag.add("StatisticName", mTitle);
				texttag.add("PrintDate", CurrentDate);
				texttag.add("SaleChnl", mSalechnlName);
				logger.debug("大小" + texttag.size());
		      if (texttag.size()>0)
		        xmlexport.addTextTag(texttag);
		      mResult.addElement(xmlexport);
				mResult.clear();
				mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	private boolean PrePareDataUW05() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW05.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tReportSQL);
			sqlbv7.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv7);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			int m; // 管理机构的数量
			String strArr[] = null;
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			//区域不同有些查询逻辑也是不同的 采用此种方法原因是 先将本次要统计的管理机构放到hashtable中
            //然后在从数据库中取出不通区域管理机构的数据放入缓存 从而防止多次查询数据库 再通过管理机构为标志提取数据
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			
			int row = mLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + mLDComSet.size());

			LDComSchema tLDComSchema;
			Hashtable tHashtable = new Hashtable();
			String StringArray[][] = new String[row][maxColCount];
			for (m = 0; m < mLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(mLDComSet.get(m + 1));
				logger.debug(tLDComSchema.getComCode() + ":"
						+ tLDComSchema.getShortName());
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			
			int count_a=0;
			int count_9=0;
			int count_4=0;
			int count_2=0;
			int count_1=0;
			int count_prem=0;
			int count_spec=0;
			int count_change=0;
			String tKey_CD="";
			String tKey_BZJ="";
			String tKey_CBZ="";
			String tKey_YQ="";
			String tKey_JB="";
			String tKey_JKA="";
			String tKey_TBS="";
			String tKey_BJC="";
//			for (int i=1;i<=mLDComSet.size();i++){
//				String BranchComCode = mLDComSet.get(i).getComCode(); // 中心支公司代码
//				String BranchComName = mLDComSet.get(i).getName(); // 中心支公司名称	
			tKey_CD = " select A.x,(select shortname from ldcom where comcode=A.x),A.y from "
				    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
				    + " where conttype='1' and uwflag = 'a'"
				    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
				    + " and exists (select 1 from lcpenotice b where b.contno = a.contno and b.printflag is not null)"
				    + ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				    + " and a.salechnl " + mSalechnlSql
				    + addSQL
				    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
				    ;
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tKey_CD);
			sqlbv8.put("mManageLen", mManageLen);
			sqlbv8.put("mStartDate", mStartDate);
			sqlbv8.put("mEndDate", mEndDate);
			sqlbv8.put("mUWSalechnl", mUWSalechnl);
			sqlbv8.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv8.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv8.put("list", list);
			
			tKey_BZJ = " select A.x,(select shortname from ldcom where comcode=A.x),A.y from "
				    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
				    + " where conttype='1' and uwflag = '9'"
				    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
				    + " and exists (select 1 from lcpenotice b where b.contno = a.contno and b.printflag is not null)"
				    + ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				    + " and a.salechnl " + mSalechnlSql
				    + addSQL
				    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
				    ;
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tKey_BZJ);
			sqlbv9.put("mManageLen", mManageLen);
			sqlbv9.put("mStartDate", mStartDate);
			sqlbv9.put("mEndDate", mEndDate);
			sqlbv9.put("mUWSalechnl", mUWSalechnl);
			sqlbv9.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv9.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv9.put("list", list);
			tKey_CBZ = " select A.x,(select shortname from ldcom where comcode=A.x),A.y from "
				    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
				    + " where conttype='1' and uwflag = '4'"
				    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
				    + " and exists (select 1 from lcpenotice b where b.contno = a.contno and b.printflag is not null)"
				    + ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				    + " and a.salechnl " + mSalechnlSql
				    + addSQL
				    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
				    ;
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tKey_CBZ);
			sqlbv10.put("mManageLen", mManageLen);
			sqlbv10.put("mStartDate", mStartDate);
			sqlbv10.put("mEndDate", mEndDate);
			sqlbv10.put("mUWSalechnl", mUWSalechnl);
			sqlbv10.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv10.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv10.put("list", list);
			tKey_YQ = " select A.x,(select shortname from ldcom where comcode=A.x),A.y from "
				    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
				    + " where conttype='1' and uwflag = '2'"
				    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
				    + " and exists (select 1 from lcpenotice b where b.contno = a.contno and b.printflag is not null)"
				    + ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				    + " and a.salechnl " + mSalechnlSql
				    + addSQL
				    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
				    ;
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tKey_YQ);
			sqlbv11.put("mManageLen", mManageLen);
			sqlbv11.put("mStartDate", mStartDate);
			sqlbv11.put("mEndDate", mEndDate);
			sqlbv11.put("mUWSalechnl", mUWSalechnl);
			sqlbv11.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv11.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv11.put("list", list);
			tKey_JB = " select A.x,(select shortname from ldcom where comcode=A.x),A.y from "
				    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
				    + " where conttype='1' and uwflag = '1'"
				    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
				    + " and exists (select 1 from lcpenotice b where b.contno = a.contno and b.printflag is not null)"
				    + ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				    + " and a.salechnl " + mSalechnlSql
				    + addSQL
				    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
				    ;
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tKey_JB);
			sqlbv12.put("mManageLen", mManageLen);
			sqlbv12.put("mStartDate", mStartDate);
			sqlbv12.put("mEndDate", mEndDate);
			sqlbv12.put("mUWSalechnl", mUWSalechnl);
			sqlbv12.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv12.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv12.put("list", list);
			tKey_JKA = " select A.x,(select shortname from ldcom where comcode=A.x),A.y from "
				    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
				    + " where conttype='1' and uwflag = '4'"
				    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
				    + " and exists (select 1 from lcpenotice b where b.contno = a.contno and b.printflag is not null) "
				    + " and exists (select 1 from lcuwsub d where d.contno = a.contno and d.AddPremFlag is not null and d.AddPremFlag<>'0') "//下发过加费
				    + ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				    + " and a.salechnl " + mSalechnlSql
				    + addSQL
				    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
				    ;
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tKey_JKA);
			sqlbv13.put("mManageLen", mManageLen);
			sqlbv13.put("mStartDate", mStartDate);
			sqlbv13.put("mEndDate", mEndDate);
			sqlbv13.put("mUWSalechnl", mUWSalechnl);
			sqlbv13.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv13.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv13.put("list", list);
			tKey_TBS = " select A.x,(select shortname from ldcom where comcode=A.x),A.y from "
				    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
				    + " where conttype='1' and uwflag = '4'"
				    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
				    + " and exists (select 1 from lcpenotice b where b.contno = a.contno and b.printflag is not null) "
				    + " and exists (select 1 from lccspec d where d.contno = a.contno and d.prtflag is null "
				    	+ " and d.spectype in(select code from ldcode where 1 = 1 and codetype = 'healthspcetemp')) "//有健康特约下发
				    + ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				    + " and a.salechnl " + mSalechnlSql
				    + addSQL
				    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
				    ;
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(tKey_TBS);
			sqlbv14.put("mManageLen", mManageLen);
			sqlbv14.put("mStartDate", mStartDate);
			sqlbv14.put("mEndDate", mEndDate);
			sqlbv14.put("mUWSalechnl", mUWSalechnl);
			sqlbv14.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv14.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv14.put("list", list);
			tKey_BJC = " select A.x,(select shortname from ldcom where comcode=A.x),A.y from "
				    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
				    + " where conttype='1' and uwflag = '4'"
				    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
				    + " and exists (select 1 from lcpenotice b where b.contno = a.contno and b.printflag is not null) "
				    + " and exists (select 1 from lcuwsub d where d.contno = a.contno and d.changepolflag is not null and d.changepolflag<>'0') "//下发过承保计划变更
				    + " and agentcode not like '%999999' "
				    + ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				    + " and a.salechnl " + mSalechnlSql
				    + addSQL
				    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
				    ;
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(tKey_BJC);
			sqlbv15.put("mManageLen", mManageLen);
			sqlbv15.put("mStartDate", mStartDate);
			sqlbv15.put("mEndDate", mEndDate);
			sqlbv15.put("mUWSalechnl", mUWSalechnl);
			sqlbv15.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv15.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv15.put("list", list);
			VData tVData = new VData();
		      //tVData.add(tSQL);
		      tVData.add(sqlbv8);
		      tVData.add(sqlbv9);
		      tVData.add(sqlbv10);
		      tVData.add(sqlbv11);
		      tVData.add(sqlbv12);
		      tVData.add(sqlbv13);
		      tVData.add(sqlbv14);
		      tVData.add(sqlbv15);
		      ZHashReport tZHashReport = new ZHashReport(tComCodesql,tVData);
		      tZHashReport.setColumnType(1,tZHashReport.StringType);//机构名称
		      tZHashReport.setDoformat(false);
		      String[][] tStringResult = tZHashReport.calItem();
		      logger.debug("tStringResult:"+tStringResult.length);
		      DecimalFormat mDecimalFormat = new DecimalFormat("0");
		      int n = 0;
		      n=tStringResult.length;
		      if(n!=0)
		      {
		    	  if(n <= 3000)
			      {
				     n = tStringResult.length;
			      }else
			    	  n=3000;
//		    	  logger.debug("第"+(i+1)+"次有数据！");
		    	  aListTable.setName("UW05");
		    	  
		    	  
			    	  for (m = 0; m <n; m++) {
		    		  logger.debug("tStringResult[m][0]:"+tStringResult[m][0]);
						int tIndex = ((Integer) tHashtable
								.get(tStringResult[m][0])).intValue();
						StringArray[tIndex][0] = tStringResult[m][0];
						StringArray[tIndex][1] = tStringResult[m][1];
						StringArray[tIndex][2] = mDecimalFormat.format(Double.parseDouble(tStringResult[m][2]));
						StringArray[tIndex][3] = mDecimalFormat.format(Double.parseDouble(tStringResult[m][3]));
//							mDecimalFormat.format(Double
//								.parseDouble(tStringResult[m][3]));
						StringArray[tIndex][4] = mDecimalFormat.format(Double.parseDouble(tStringResult[m][4]));
						StringArray[tIndex][5] = mDecimalFormat.format(Double.parseDouble(tStringResult[m][5]));
//							mDecimalFormat.format(Double
//								.parseDouble(tStringResult[m][5]));
						StringArray[tIndex][6] = mDecimalFormat.format(Double.parseDouble(tStringResult[m][6]));
						StringArray[tIndex][7] = mDecimalFormat.format(Double.parseDouble(tStringResult[m][7]));
						StringArray[tIndex][8] = mDecimalFormat.format(Double.parseDouble(tStringResult[m][8]));
						StringArray[tIndex][9] = mDecimalFormat.format(Double.parseDouble(tStringResult[m][9]));
//							mDecimalFormat.format(Double
//								.parseDouble(tStringResult[m][7]));
						logger.debug("StringArray[tIndex][3]:"+StringArray[tIndex][2]);
						count_a=count_a+Integer.parseInt(StringArray[tIndex][2]);
						count_4=count_4+Integer.parseInt(StringArray[tIndex][3]);
						count_9=count_9+Integer.parseInt(StringArray[tIndex][4]);
						count_2=count_2+Integer.parseInt(StringArray[tIndex][5]);
						count_1=count_1+Integer.parseInt(StringArray[tIndex][6]);
						count_prem=count_prem+Integer.parseInt(StringArray[tIndex][7]);
						count_spec=count_spec+Integer.parseInt(StringArray[tIndex][8]);
						count_change=count_change+Integer.parseInt(StringArray[tIndex][9]);
					}
		    	  for (int i = 0; i < row; i++) {
						strArr = new String[maxColCount];
						if(StringArray[i][0]!=null){
							strArr = StringArray[i];
							aListTable.add(strArr);
						}
					}
		      }
			strArr = new String[10];
			strArr[0] = "86";
			strArr[1] = "总公司";
			strArr[2] = String.valueOf(count_a);
			strArr[3] = String.valueOf(count_4);
			strArr[4] = String.valueOf(count_9);
			strArr[5] = String.valueOf(count_2);
			strArr[6] = String.valueOf(count_1);
			strArr[7] = String.valueOf(count_prem);
			strArr[8] = String.valueOf(count_spec);
			strArr[9] = String.valueOf(count_change);
			aListTable.add(strArr);
			
			xmlexport.addListTable(aListTable, strArr);
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	private boolean PrePareDataUW06() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW06.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tReportSQL);
			sqlbv16.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv16);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			int m; // 管理机构的数量
			String strArr[] = null;
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			//区域不同有些查询逻辑也是不同的 采用此种方法原因是 先将本次要统计的管理机构放到hashtable中
            //然后在从数据库中取出不通区域管理机构的数据放入缓存 从而防止多次查询数据库 再通过管理机构为标志提取数据
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' "
				+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			
			int row = mLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + mLDComSet.size());

			LDComSchema tLDComSchema;
			Hashtable tHashtable = new Hashtable();
			String StringArray[][] = new String[row][maxColCount];
			for (m = 0; m < mLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(mLDComSet.get(m + 1));
				logger.debug(tLDComSchema.getComCode() + ":"
						+ tLDComSchema.getShortName());
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			
			DecimalFormat mDecimalFormat = new DecimalFormat("0");
			int count_reply=0;
			int count_ssend=0;
			String tKey_HF="";
			String tKey_SPE="";				
				
				tKey_HF = " select A.x,(select ShortName from ldcom where comcode=A.x),A.y from "
			    	+ "  (select substr(a.managecom,1,"+"?mManageLen?"+") x, count(distinct proposalcontno) y from lcpenotice a where "
			    	+ " petimes='1' and exists(select 1 from lcpol where contno=a.contno "
					+ " and conttype='1' and renewcount=0 "
					+ " and salechnl " + mSalechnlSql
					+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' )"//有扫描件
					+ ")" 
					+ " and peresult is not null "
					+ " and exists(select 1 from LCPENoticeReply c where c.proposalcontno=a.proposalcontno and c.prtseq=a.prtseq "
					+ ReportPubFun.getWherePart("c.modifydate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					+ " )"
			    	+ addSQL
			    	+ " group by substr(a.managecom,1,"+"?mManageLen?"+")) A "
			    	;
				SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				sqlbv17.sql(tKey_HF);
				sqlbv17.put("mManageLen", mManageLen);
				sqlbv17.put("mStartDate", mStartDate);
				sqlbv17.put("mEndDate", mEndDate);
				sqlbv17.put("mUWSalechnl", mUWSalechnl);
				sqlbv17.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
				sqlbv17.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
				sqlbv17.put("list", list);
				tKey_SPE = " select A.x,(select ShortName from ldcom where comcode=A.x),A.y from "
			    	+ "  (select substr(a.managecom,1,"+"?mManageLen?"+") x, count(distinct proposalcontno) y from lcpenotice a where "
			    	+ " petimes>'1' and exists(select 1 from lcpol where contno=a.contno "
					+ " and conttype='1' and renewcount=0 "
					+ " and salechnl " + mSalechnlSql
					+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' )"//有扫描件
					+ ")" 
					+ " and exists (select 1 from loprtmanager c where c.prtseq=a.prtseq "
					+ ReportPubFun.getWherePart("c.makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					+ " )"
			    	+ addSQL
			    	+ " group by substr(a.managecom,1,"+"?mManageLen?"+")) A "
			    	;				
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql(tKey_SPE);
				sqlbv18.put("mManageLen", mManageLen);
				sqlbv18.put("mStartDate", mStartDate);
				sqlbv18.put("mEndDate", mEndDate);
				sqlbv18.put("mUWSalechnl", mUWSalechnl);
				sqlbv18.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
				sqlbv18.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
				sqlbv18.put("list", list);
				VData tVData = new VData();
			      //tVData.add(tSQL);
			      tVData.add(sqlbv17);
			      tVData.add(sqlbv18);
			      ZHashReport tZHashReport = new ZHashReport(tComCodesql,tVData);
			      tZHashReport.setColumnType(1,tZHashReport.StringType);//机构名称
			      tZHashReport.setDoformat(false);
			      String[][] tStringResult = tZHashReport.calItem();
			      logger.debug("tStringResult:"+tStringResult.length);
			      int n = 0;
			      n=tStringResult.length;
			      if(n!=0)
			      {
			    	  if(n <= 3000)
				      {
					     n = tStringResult.length;
				      }else
				    	  n=3000;
//			    	  logger.debug("第"+(i+1)+"次有数据！");
			    	  aListTable.setName("UW06");
			    	  
			    	  
				    	  for (m = 0; m <n; m++) {
			    		  logger.debug("tStringResult[m][0]:"+tStringResult[m][0]);
							int tIndex = ((Integer) tHashtable
									.get(tStringResult[m][0])).intValue();
							StringArray[tIndex][0] = tStringResult[m][0];
							StringArray[tIndex][1] = tStringResult[m][1];
							StringArray[tIndex][2] = mDecimalFormat.format(Double.parseDouble(tStringResult[m][2]));
							StringArray[tIndex][3] = mDecimalFormat.format(Double.parseDouble(tStringResult[m][3]));
							logger.debug("StringArray[tIndex][3]:"+StringArray[tIndex][2]);
							count_reply=count_reply+Integer.parseInt(StringArray[tIndex][2]);
							count_ssend=count_ssend+Integer.parseInt(StringArray[tIndex][3]);
						}
				    	  for (int i = 0; i < row; i++) {
								strArr = new String[maxColCount];
								if(StringArray[i][0]!=null){
									strArr = StringArray[i];
									aListTable.add(strArr);
								}
							}
			      }
			strArr = new String[4];
			strArr[0] = "86";
			strArr[1] = "总公司";
			strArr[2] = String.valueOf(count_reply);
			strArr[3] = String.valueOf(count_ssend);
			aListTable.add(strArr);
			xmlexport.addListTable(aListTable, strArr);
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	
	private boolean PrePareDataUW08() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW08.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(tReportSQL);
			sqlbv19.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv19);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			int m; // 管理机构的数量
			String strArr[] = null;
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			//区域不同有些查询逻辑也是不同的 采用此种方法原因是 先将本次要统计的管理机构放到hashtable中
            //然后在从数据库中取出不通区域管理机构的数据放入缓存 从而防止多次查询数据库 再通过管理机构为标志提取数据
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			
			int row = mLDComSet.size();
			logger.debug("分公司管理机构数目：" + mLDComSet.size());

			LDComSchema tLDComSchema;
			Hashtable tHashtable = new Hashtable();
			String StringArray[][] = new String[row][maxColCount];
			for (m = 0; m < mLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(mLDComSet.get(m + 1));
				logger.debug(tLDComSchema.getComCode() + ":"
						+ tLDComSchema.getShortName());
				StringArray[m][0] = tLDComSchema.getComCode();
				StringArray[m][1] = tLDComSchema.getShortName();
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			int reason_1=0;
			int reason_2=0;
			int reason_3=0;
			int reason_4=0;
			int reason_5=0;
			int reason_6=0;
			String tSQL="";
			//得到所有原因
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql("select Code, CodeName, CodeAlias, ComCode, OtherSign from ldcode where 1 = 1 and codetype = 'rreportreason' order by Code");
			tSSRS = tExeSQL.execSQL(sqlbv20);
			for(int i=0;i<row;i++)
				for(int j=0;j<tSSRS.MaxRow;j++){				 
					 StringArray[i][j+2] = "0";
				 }			
				tSQL =" select substr(a.managecom,1,"+"?mManageLen?"+"),a.rreportreason,count(1) from lcrreport a "
					+ " where replyflag is not null "
					+ " and exists (select 1 from lccont b where b.conttype ='1' and b.contno = a.contno"
					+ " and b.salechnl " + mSalechnlSql
					+ " and exists (select '1' from es_doc_main where doccode=b.prtno and subtype = 'UA001' )"//有扫描件
					+ ")"
					+ " and exists (select 1 from loprtmanager c where c.prtseq=a.prtseq "
					+ ReportPubFun.getWherePart("c.makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					+ " )"
					+ addSQL
					+ " group by substr(a.managecom,1,"+"?mManageLen?"+"),a.rreportreason";
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql(tSQL);
				sqlbv21.put("mManageLen", mManageLen);
				sqlbv21.put("mStartDate", mStartDate);
				sqlbv21.put("mEndDate", mEndDate);
				sqlbv21.put("mUWSalechnl", mUWSalechnl);
				sqlbv21.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
				sqlbv21.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
				sqlbv21.put("list", list);
				
				ExeSQL ListExeSQL = new ExeSQL();
				SSRS ListSSRS = new SSRS();
				ListSSRS = ListExeSQL.execSQL(sqlbv21);
				int num=0;
				aListTable.setName("UW08");
				for (int j = 1; j <= ListSSRS.getMaxRow(); j++) {
					strArr = new String[8];
					logger.debug("ListSSRS.GetText(j, 1):"+ListSSRS.GetText(j, 1));
					int tIndex = ((Integer) tHashtable
							.get(ListSSRS.GetText(j, 1))).intValue();
					for(int i=1;i<=tSSRS.MaxRow;i++){
					 if(tSSRS.GetText(i, 1).equals(ListSSRS.GetText(j, 2))){
						 StringArray[tIndex][i+1] =ListSSRS.GetText(j, 3);
					 }
					}
				}
				
				for (int i = 0; i < row; i++) {
					strArr = new String[maxColCount];
					if(StringArray[i][0]!=null){//循环所有记录 可能有为空的行
						strArr = StringArray[i];
						aListTable.add(strArr);
					}
				}
				for (int j=0;j< row;j++){
					if(StringArray[j][0]!=null){
						reason_1 = reason_1+Integer.parseInt(StringArray[j][2]);
						reason_2 = reason_2+Integer.parseInt(StringArray[j][3]);
						reason_3 = reason_3+Integer.parseInt(StringArray[j][4]);
						reason_4 = reason_4+Integer.parseInt(StringArray[j][5]);
						reason_5 = reason_5+Integer.parseInt(StringArray[j][6]);
						reason_6 = reason_6+Integer.parseInt(StringArray[j][7]);
					}
				}
//			}
			strArr = new String[8];
			strArr[0] = "86";
			strArr[1] = "总公司";
			strArr[2] = String.valueOf(reason_1);
			strArr[3] = String.valueOf(reason_2);
			strArr[4] = String.valueOf(reason_3);
			strArr[5] = String.valueOf(reason_4);
			strArr[6] = String.valueOf(reason_5);
			strArr[7] = String.valueOf(reason_6);
			aListTable.add(strArr);
			xmlexport.addListTable(aListTable, strArr);
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	private boolean PrePareDataUW09() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW09.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.sql(tReportSQL);
			sqlbv22.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv22);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			int m; // 管理机构的数量
			String strArr[] = null;
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			//区域不同有些查询逻辑也是不同的 采用此种方法原因是 先将本次要统计的管理机构放到hashtable中
            //然后在从数据库中取出不通区域管理机构的数据放入缓存 从而防止多次查询数据库 再通过管理机构为标志提取数据
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and b.managecom >= '"
				+ "?mManageCom0?"
				+ "' and b.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(b.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}

			int YX=0;
			int LH=0;
			int HG=0;
			int NHG=0;
			
			String tSQL="";
//			for (int i=1;i<=tLDComSet.size();i++){
//				String BranchComCode = tLDComSet.get(i).getComCode(); // 中心支公司代码
//				String BranchComName = tLDComSet.get(i).getName(); // 中心支公司名称
				tSQL ="select A.operator,sum(A.m),sum(A.n),sum(A.p),sum(A.q) from ("
					  + " select a.customerno operator,(case conclusion when '优秀' then 1 else 0 end) m,(case conclusion when '良好' then 1 else 0 end) n"
					  + " ,(case conclusion when '合格' then 1 else 0 end) p,(case conclusion when '不合格' then 1 else 0 end) q from "
					  + " LCScoreRReport a where conclusion is not null "
					  + ReportPubFun.getWherePart("a.makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1) 
					  + " and exists (select 1 from lccont b where b.conttype ='1' and b.proposalcontno = a.proposalcontno"
						+ " and b.salechnl " + mSalechnlSql
						+ addSQL
						+ " and exists (select '1' from es_doc_main where doccode=b.prtno and subtype = 'UA001' )"//有扫描件
					  + ") )A"
					  + " group by A.operator order by A.operator"
					  ;				
				SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
				sqlbv23.sql(tSQL);
				sqlbv23.put("mStartDate", mStartDate);
				sqlbv23.put("mEndDate", mEndDate);
				sqlbv23.put("mUWSalechnl", mUWSalechnl);
				sqlbv23.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
				sqlbv23.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
				sqlbv23.put("mManageLen", mManageLen);
				sqlbv23.put("list", list);
				ExeSQL ListExeSQL = new ExeSQL();
				SSRS ListSSRS = new SSRS();
				ListSSRS = ListExeSQL.execSQL(sqlbv23);
				if (ListSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "查询的结果是0");
				}
				aListTable.setName("UW09");				
				
				for (int j = 1; j <= ListSSRS.getMaxRow(); j++) {
					strArr =new String [5];
					strArr[0] = ListSSRS.GetText(j, 1);					
					strArr[1] = ListSSRS.GetText(j, 2);
					strArr[2] = ListSSRS.GetText(j, 3);
					strArr[3] = ListSSRS.GetText(j, 4);
					strArr[4] = ListSSRS.GetText(j, 5);
					
					YX=YX+Integer.parseInt(strArr[1]);
					LH=LH+Integer.parseInt(strArr[2]);
					HG=HG+Integer.parseInt(strArr[3]);
					NHG=NHG+Integer.parseInt(strArr[4]);
					aListTable.add(strArr);
			}
			aListTable.setName("UW09");
			strArr =new String [5];
			strArr[0]="合计";
			strArr[1]=String.valueOf(YX);
			strArr[2]=String.valueOf(LH);
			strArr[3]=String.valueOf(HG);
			strArr[4]=String.valueOf(NHG);
			aListTable.add(strArr);
			xmlexport.addListTable(aListTable, strArr);
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	
	private boolean PrePareDataUW10() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW10.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql(tReportSQL);
			sqlbv24.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv24);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			int m; // 管理机构的数量
			String tsql = "";
			String strArr[] = null;
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			//区域不同有些查询逻辑也是不同的 采用此种方法原因是 先将本次要统计的管理机构放到hashtable中
            //然后在从数据库中取出不通区域管理机构的数据放入缓存 从而防止多次查询数据库 再通过管理机构为标志提取数据
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and b.managecom >= '"
				+ "?mManageCom0?"
				+ "' and b.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and b.managecom >= '"
					+ "?mManageCom0?"
					+ "' and b.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(b.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			//计算原因合计数目
			int reason_1=0;
			int reason_2=0;
			int reason_3=0;
			int reason_4=0;
			int reason_5=0;
			int reason_6=0;
			int reason_7=0;
			int reason_8=0;
			int reason_9=0;
			int reason_10=0;
			int reason_11=0;
			int reason_12=0;
			int reason_13=0;
			int reason_14=0;
			String tSQL="";

				tSQL ="select A.operator,sum(A.m),sum(A.n),sum(A.o),sum(A.p),sum(A.q),sum(A.r),sum(A.s),sum(A.t) from (" 
					+ " select a.customerno operator"
					+ ",(case assessitem when 'SScore1' then 1 else 0 end) m"
					+ ",(case assessitem when 'SScore2' then 1 else 0 end) n"
					+ ",(case assessitem when 'SScore3' then 1 else 0 end) o"
					+ ",(case assessitem when 'SScore4' then 1 else 0 end) p"
					+ ",(case assessitem when 'SScore5' then 1 else 0 end) q"
					+ ",(case assessitem when 'SScore6' then 1 else 0 end) r"
					+ ",(case assessitem when 'SScore7' then 1 else 0 end) s"
					+ ",(case assessitem when 'SScore8' then 1 else 0 end) t"
					+ " from lcscorerreportsub a "
					+ " where a.assessitem like 'SScore%' and a.score>0 "
					+ReportPubFun.getWherePart("a.makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1) 
					+ " and exists (select 1 from lccont b where b.conttype ='1' and b.proposalcontno = a.proposalcontno"
						+ " and b.salechnl " + mSalechnlSql
						+ addSQL
						+ " and exists (select '1' from es_doc_main where doccode=b.prtno and subtype = 'UA001' )"//有扫描件
					+ ") )A"
					+" group by operator order by operator "
					;
				SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
				sqlbv25.sql(tSQL);
				sqlbv25.put("mStartDate", mStartDate);
				sqlbv25.put("mEndDate", mEndDate);
				sqlbv25.put("mUWSalechnl", mUWSalechnl);
				sqlbv25.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
				sqlbv25.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
				sqlbv25.put("mManageLen", mManageLen);
				sqlbv25.put("list", list);
				ExeSQL ListExeSQL = new ExeSQL();
				SSRS ListSSRS = new SSRS();
				ListSSRS = ListExeSQL.execSQL(sqlbv25);
				aListTable.setName("UW10");
				int num=0;
				boolean exists=false;//				 
				for(int i=1;i<=ListSSRS.getMaxRow();i++)
				{
					strArr = new String [15];
					strArr[0] = ListSSRS.GetText(i, 1);
					strArr[1]= ListSSRS.GetText(i, 2);
					strArr[2]= ListSSRS.GetText(i, 3);
					strArr[3]= ListSSRS.GetText(i, 4);
					strArr[4]= ListSSRS.GetText(i, 5);
					strArr[5]= ListSSRS.GetText(i, 6);
					strArr[6]= ListSSRS.GetText(i, 7);
					strArr[7]= ListSSRS.GetText(i, 8);
					strArr[8]= "0";
					strArr[9]= "0";
					strArr[10]= "0";
					strArr[11]= "0";
					strArr[12]= "0";
					strArr[13]= "0";
					strArr[14]= "0";
					reason_1=reason_1+Integer.parseInt(strArr[1]);
					reason_2=reason_2+Integer.parseInt(strArr[2]);
					reason_3=reason_3+Integer.parseInt(strArr[3]);
					reason_4=reason_4+Integer.parseInt(strArr[4]);
					reason_5=reason_5+Integer.parseInt(strArr[5]);
					reason_6=reason_6+Integer.parseInt(strArr[6]);
					reason_7=reason_7+Integer.parseInt(strArr[7]);
					reason_8=reason_8+Integer.parseInt(strArr[8]);
					reason_9=reason_9+Integer.parseInt(strArr[9]);
					reason_10=reason_10+Integer.parseInt(strArr[10]);
					reason_11=reason_11+Integer.parseInt(strArr[11]);
					reason_12=reason_12+Integer.parseInt(strArr[12]);
					reason_13=reason_13+Integer.parseInt(strArr[13]);
					reason_14=reason_14+Integer.parseInt(strArr[14]);
					aListTable.add(strArr);
				}
			
			aListTable.setName("UW10");
			strArr = new String [15];
			strArr[0]="合计";
			strArr[1]=String.valueOf(reason_1);
			strArr[2]=String.valueOf(reason_2);
			strArr[3]=String.valueOf(reason_3);
			strArr[4]=String.valueOf(reason_4);
			strArr[5]=String.valueOf(reason_5);
			strArr[6]=String.valueOf(reason_6);
			strArr[7]=String.valueOf(reason_7);
			strArr[8]=String.valueOf(reason_8);
			strArr[9]=String.valueOf(reason_9);
			strArr[10]=String.valueOf(reason_10);
			strArr[11]=String.valueOf(reason_11);
			strArr[12]=String.valueOf(reason_12);
			strArr[13]=String.valueOf(reason_13);
			strArr[14]=String.valueOf(reason_14);
			//aListTable.add(strArr);
			xmlexport.addListTable(aListTable, strArr);
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	
	private boolean PrePareDataUW11_12() {
		try {
			XmlExport xmlexport = new XmlExport();
			ListTable aListTable = new ListTable();
			String tPassFlag="";
			if(mUWReportType.equals("UW11")){
				//延期查询
				tPassFlag="2";
				xmlexport.createDocument("UW11.vts", "printer");
			}else if (mUWReportType.equals("UW12")){
				tPassFlag="1";
				xmlexport.createDocument("UW12.vts", "printer");
			}else{
				CError.buildErr(this, "没有得到报表类型’!");
				return false;
			}
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.sql(tReportSQL);
			sqlbv26.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv26);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
	              //管理机构为所有管理机构
					addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
					//管理机构为南北区在ldcode表中描述的管理机构 
					addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			
			String tsql = "";
			String strArr[] = null;
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			SSRS reason_SSRS = new SSRS();
			ExeSQL ListExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.sql("select code,codename from ldcode where codetype ='uwderefreason' order by code");
			reason_SSRS =ListExeSQL.execSQL(sqlbv27);
			Hashtable tHashtable1 = new Hashtable();
			for (int m = 0; m < reason_SSRS.getMaxRow(); m++) {
				tHashtable1.put(reason_SSRS.GetText(m+1, 2), new Integer(m));
			}
			
			LDComSchema tLDComSchema;
			Hashtable tHashtable = new Hashtable();
			String StringArray[][] = new String[mLDComSet.size()+1][reason_SSRS.getMaxRow()+3];
			//初始化
			for (int m = 0; m < mLDComSet.size()+1; m++) {
				for (int n = 2; n < reason_SSRS.getMaxRow()+3; n++) {
					StringArray[m][n] = "0";
				}
			}
			for (int m = 0; m < mLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(mLDComSet.get(m + 1));
				StringArray[m][0] = tLDComSchema.getComCode();
				StringArray[m][1] = tLDComSchema.getShortName();
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			StringArray[mLDComSet.size()][0] = "86";
			StringArray[mLDComSet.size()][1] = "总公司";			
			
			String tSQL="";
			tSQL = " select substr(a.managecom,1,"+"?mManageLen?"+"),commonreason,count(1) "
				+ " from lccuwmaster a where "
				+ " a.passflag='"+"?tPassFlag?"+"' "
				+ " and exists(select 1 from lccont where contno=a.contno "
				+ " and conttype='1' and appflag='0' "
				+ ReportPubFun.getWherePart("uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1) 
				+ " and salechnl " + mSalechnlSql
				+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
				+ ")" 					
				+ addSQL					
				+" group by substr(a.managecom,1,"+"?mManageLen?"+"),commonreason"
				;		
			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
			sqlbv28.sql(tSQL);
			sqlbv28.put("tPassFlag", tPassFlag);
			sqlbv28.put("mStartDate", mStartDate);
			sqlbv28.put("mEndDate", mEndDate);
			sqlbv28.put("mUWSalechnl", mUWSalechnl);
			sqlbv28.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv28.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv28.put("mManageLen", mManageLen);
			sqlbv28.put("list", list);
			SSRS ListSSRS = new SSRS();
			ListSSRS = ListExeSQL.execSQL(sqlbv28);
			for(int i=0; i<ListSSRS.getMaxRow(); i++)
			{
				int tIndex1 = ((Integer) tHashtable
						.get(ListSSRS.GetText(i+1, 1))).intValue();//机构代码
				int tIndex2 = ((Integer) tHashtable1
						.get(ListSSRS.GetText(i+1, 2))).intValue();//拒保原因代码
				StringArray[tIndex1][tIndex2+2] = ListSSRS.GetText(i+1, 3);
				StringArray[tIndex1][reason_SSRS.getMaxRow()+2] = String.valueOf(Integer.parseInt(StringArray[tIndex1][reason_SSRS.getMaxRow()+2]) + Integer.parseInt(ListSSRS.GetText(i+1, 3)));
				StringArray[mLDComSet.size()][tIndex2+2] = String.valueOf(Integer.parseInt(StringArray[mLDComSet.size()][tIndex2+2]) + Integer.parseInt(ListSSRS.GetText(i+1, 3)));
			}

			for (int i = 0; i < mLDComSet.size(); i++) {
				strArr = new String[reason_SSRS.getMaxRow()+3];
				if(StringArray[i][0]!=null){
					strArr = StringArray[i];
					aListTable.add(strArr);
				}
			}

			strArr = new String[reason_SSRS.getMaxRow()+3];
			aListTable.setName("UW11");
			int total = 0;
			for (int i=0;i<reason_SSRS.getMaxRow();i++){
				total = total+Integer.parseInt(StringArray[mLDComSet.size()][i+2]);
			}
			StringArray[mLDComSet.size()][reason_SSRS.getMaxRow()+2]=String.valueOf(total);
			strArr = StringArray[mLDComSet.size()];
			aListTable.add(strArr);			
			
			xmlexport.addListTable(aListTable, strArr);
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	
	private boolean PrePareDataUW13_14() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW13.vts", "printer");
			ListTable aListTable = new ListTable();
			String tPassFlag="";
			if(mUWReportType.equals("UW13")){
				//延期查询
				tPassFlag="2";
			}else if (mUWReportType.equals("UW14")){
				tPassFlag="1";
				
			}else{
				CError.buildErr(this, "没有得到报表类型’!");
				return false;
			}
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
			sqlbv29.sql(tReportSQL);
			sqlbv29.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv29);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			String tsql = "";
			String strArr[] = null;			
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			int agerange_1=0;
			int agerange_2=0;
			int agerange_3=0;
			int agerange_4=0;
			int agerange_5=0;
			int agerange_6=0;
			int agerange_7=0;
			int agerange_8=0;
			int agerangesum=0;
			//得到要统计的年龄段
			SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
			sqlbv30.sql("select code,codename from ldcode where codetype ='deferage'");
			tSSRS=tExeSQL.execSQL(sqlbv30);
			String tSQL="";
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
	              //管理机构为所有管理机构
					addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
					//管理机构为南北区在ldcode表中描述的管理机构 
					addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			LDComSchema tLDComSchema;
			Hashtable tHashtable = new Hashtable();
			String StringArray[][] = new String[mLDComSet.size()+1][tSSRS.getMaxRow()+3];
			//初始化
			for (int m = 0; m < mLDComSet.size()+1; m++) {
				for (int n = 2; n < tSSRS.getMaxRow()+3; n++) {
					StringArray[m][n] = "0";
				}
			}
			for (int m = 0; m < mLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(mLDComSet.get(m + 1));
				StringArray[m][0] = tLDComSchema.getComCode();
				StringArray[m][1] = tLDComSchema.getShortName();
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			StringArray[mLDComSet.size()][0] = "86";
			StringArray[mLDComSet.size()][1] = "总公司";
			for (int n=1;n<=tSSRS.getMaxRow();n++){
					//统计每个机构下 每个年龄段的人数
				String tAgeRange = tSSRS.GetText(n, 1);
				String Age[] = tAgeRange.split("-");
				int tMinAge = Integer.parseInt(Age[0]);
				int tMaxAge = Integer.parseInt(Age[1]);
					tSQL = " select substr(a.managecom,1,"+"?mManageLen?"+"),count(1) from lccont a where "
						+ " conttype='1' and a.uwflag='"+"?tPassFlag?"+"' "
						+ ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1) 
						+ " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
						+ " and salechnl " + mSalechnlSql
						+ " and exists(select 1 from lcinsured b where b.contno=a.contno and sequenceno in ('-1','1') "
							+ " and to_number(substr(now(), 1, 4) - substr(b.birthday, 1, 4)) between "
							+"?tMinAge?"
							+" and "
							+"?tMaxAge?"
						+ " )"
						+ addSQL
				    	+ " group by substr(a.managecom,1,"+"?mManageLen?"+") "
						;
					SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
					sqlbv31.sql(tSQL);
					sqlbv31.put("tPassFlag", tPassFlag);
					sqlbv31.put("mStartDate", mStartDate);
					sqlbv31.put("mEndDate", mEndDate);
					sqlbv31.put("mUWSalechnl", mUWSalechnl);
					sqlbv31.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
					sqlbv31.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
					sqlbv31.put("mManageLen", mManageLen);
					sqlbv31.put("list", list);
					sqlbv31.put("tMinAge", tMinAge);
					sqlbv31.put("tMaxAge", tMaxAge);
				ExeSQL ListExeSQL = new ExeSQL();
				SSRS ListSSRS = new SSRS();
				ListSSRS = ListExeSQL.execSQL(sqlbv31);
				for(int i=0; i<ListSSRS.getMaxRow(); i++)
				{
					int tIndex = ((Integer) tHashtable
						.get(ListSSRS.GetText(i+1, 1))).intValue();
					StringArray[tIndex][n+1] = ListSSRS.GetText(i+1, 2);
					StringArray[tIndex][tSSRS.getMaxRow()+2] = String.valueOf(Integer.parseInt(StringArray[tIndex][tSSRS.getMaxRow()+2]) + Integer.parseInt(ListSSRS.GetText(i+1, 2)));
					StringArray[mLDComSet.size()][n+1] = String.valueOf(Integer.parseInt(StringArray[mLDComSet.size()][n+1]) + Integer.parseInt(ListSSRS.GetText(i+1, 2)));
				}
				
				//logger.debug("strArr[Max]"+strArr[tSSRS.getMaxRow()+2]);
				
			}

			for (int i = 0; i < mLDComSet.size(); i++) {
				strArr = new String[tSSRS.getMaxRow()+3];
				if(StringArray[i][0]!=null){
					strArr = StringArray[i];
					aListTable.add(strArr);
				}
			}

			strArr = new String[tSSRS.getMaxRow()+3];
			aListTable.setName("UW13");
			strArr = StringArray[mLDComSet.size()];
			aListTable.add(strArr);			
			
			xmlexport.addListTable(aListTable, strArr);
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}

	/**
	 * 延期/拒保职业分布
	 * */
	private boolean PrePareDataUW15() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW15.vts", "printer");
			ListTable aListTable = new ListTable();
			String tPassFlag="";
			if(!mUWReportType.equals("UW15")){
				CError.buildErr(this, "没有得到报表类型’!");
				return false;
			}
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
			sqlbv32.sql(tReportSQL);
			sqlbv32.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv32);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			int yljb=0,ylyq=0,eljb=0,elyq=0,slyq=0,sljb=0;
			int silyq=0,siljb=0,wlyq=0,wljb=0,llyq=0,lljb=0;
			String tyqsql1 = "select a.occupationtype, b.uwflag, count(1) from lcinsured a, lccont b"
							+ " where a.contno = b.contno"
							+ " and b.conttype = '1' and b.uwflag in ('1', '2') "
							+ " and a.occupationtype in ('1', '2', '3', '4', '5', '6') and a.sequenceno in ('-1','1')"
							;
							
			SSRS tempSSRS = new SSRS();
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			tyqsql1 = tyqsql1
					+ " and salechnl " + mSalechnlSql
					+ " and exists (select '1' from es_doc_main d where d.doccode=b.prtno and d.subtype = 'UA001' )"//有扫描件
					+ ReportPubFun.getWherePart("b.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					+ addSQL
					+ " group by a.occupationtype, b.uwflag";
			
			SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
			sqlbv33.sql(tyqsql1);
			sqlbv33.put("mUWSalechnl", mUWSalechnl);
			sqlbv33.put("mStartDate", mStartDate);
			sqlbv33.put("mEndDate", mEndDate);
			sqlbv33.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv33.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv33.put("mManageLen", mManageLen);
			sqlbv33.put("list", list);
			tempSSRS = tExeSQL.execSQL(sqlbv33);
			if(tempSSRS.getMaxRow()>0){
				for(int i=1;i<=tempSSRS.getMaxRow();i++){
					if("1".equals(tempSSRS.GetText(i, 1))){
						if("1".equals(tempSSRS.GetText(i, 2))){
							yljb = Integer.parseInt(tempSSRS.GetText(i, 3));
						}else{
							ylyq = Integer.parseInt(tempSSRS.GetText(i, 3));
						}
					}
					if("2".equals(tempSSRS.GetText(i, 1))){
						if("1".equals(tempSSRS.GetText(i, 2))){
							eljb = Integer.parseInt(tempSSRS.GetText(i, 3));
						}else{
							elyq = Integer.parseInt(tempSSRS.GetText(i, 3));
						}
					}
					if("3".equals(tempSSRS.GetText(i, 1))){
						if("1".equals(tempSSRS.GetText(i, 2))){
							sljb = Integer.parseInt(tempSSRS.GetText(i, 3));
						}else{
							slyq = Integer.parseInt(tempSSRS.GetText(i, 3));
						}
					}
					if("4".equals(tempSSRS.GetText(i, 1))){
						if("1".equals(tempSSRS.GetText(i, 2))){
							siljb = Integer.parseInt(tempSSRS.GetText(i, 3));
						}else{
							silyq = Integer.parseInt(tempSSRS.GetText(i, 3));
						}
					}
					if("5".equals(tempSSRS.GetText(i, 1))){
						if("1".equals(tempSSRS.GetText(i, 2))){
							wljb = Integer.parseInt(tempSSRS.GetText(i, 3));
						}else{
							wlyq = Integer.parseInt(tempSSRS.GetText(i, 3));
						}
					}
					if("6".equals(tempSSRS.GetText(i, 1))){
						if("1".equals(tempSSRS.GetText(i, 2))){
							lljb = Integer.parseInt(tempSSRS.GetText(i, 3));
						}else{
							llyq = Integer.parseInt(tempSSRS.GetText(i, 3));
						}
					}
				}
			}
			String strArr[] = null;
			int hjyq=0;
			int hjjb=0;
			hjyq = ylyq+elyq+slyq+silyq+wlyq+llyq;
			hjjb = yljb+eljb+sljb+siljb+wljb+lljb;
			
			logger.debug("Comcode_SQL: " + tyqsql1);
			
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			texttag.add("ylyq", ylyq);
			texttag.add("yljb", yljb);
			texttag.add("elyq", elyq);
			texttag.add("eljb", eljb);
			texttag.add("slyq", slyq);
			texttag.add("sljb", sljb);
			texttag.add("silyq", silyq);
			texttag.add("siljb", siljb);
			texttag.add("wlyq", wlyq);
			texttag.add("wljb", wljb);
			texttag.add("llyq", llyq);
			texttag.add("lljb", lljb);
			texttag.add("hjyq", hjyq);
			texttag.add("hjjb", hjjb);
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			tExeSQL=null;
			tempSSRS=null;
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	/**
	 * 延期保费清单
	 * */
	private boolean PrePareDataUW16() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW16.vts", "printer");
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
			sqlbv34.sql(tReportSQL);
			sqlbv34.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv34);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			String tsql = "";
			String strArr[] = null;
			tsql = "select a.prtno,a.agentcode,(select name from laagent b where b.agentcode = a.agentcode),"
				+ " (select sum(c.prem)/10000 from lcpol c where c.renewcount='0' and c.contno = a.contno)"
				+ " from lccont a where conttype='1'";				
			if(mUWReportType.equals("UW16")){
				tsql = tsql + " and a.uwflag = '2'";
			}else if(mUWReportType.equals("UW17")){
				tsql = tsql + " and a.uwflag = '1'";
			}
			
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			logger.debug("Comcode_SQL: " + tsql);
			tsql = tsql 
				+ " and salechnl " + mSalechnlSql
				+ " and exists (select '1' from es_doc_main d where d.doccode=a.prtno and d.subtype = 'UA001' )"//有扫描件
				+ ReportPubFun.getWherePart("a.uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				+ addSQL;
			SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
			sqlbv35.sql(tsql);
			sqlbv35.put("mUWSalechnl", mUWSalechnl);
			sqlbv35.put("mStartDate", mStartDate);
			sqlbv35.put("mEndDate", mEndDate);
			sqlbv35.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv35.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv35.put("mManageLen", mManageLen);
			sqlbv35.put("list", list);
			SSRS tempSSRS = new SSRS();
			tempSSRS = tExeSQL.execSQL(sqlbv35);

			ListTable alistTable = new ListTable();
			alistTable.setName("UW16");

			for (int i = 1; i <= tempSSRS.getMaxRow(); i++) {
				strArr = new String[10];
				strArr[0] = i+"";
				strArr[1] = "";
				strArr[2] = tempSSRS.GetText(i, 1);
				strArr[3] = tempSSRS.GetText(i, 2);
				strArr[4] = tempSSRS.GetText(i, 3);
				strArr[5] = tempSSRS.GetText(i, 4);
				
				alistTable.add(strArr);
			}
			if(tempSSRS.getMaxRow()>0){
				xmlexport.addListTable(alistTable, strArr);
			}
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	/**
	 * 机构延期拒保险种明细表
	 * */
	private boolean PrePareDataUW18() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW18.vts", "printer");
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
			sqlbv36.sql(tReportSQL);
			sqlbv36.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv36);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			String tsql = "";
			String strArr[] = null;				
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and managecom >= '"
				+ "?mManageCom0?"
				+ "' and managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			tsql = " select A.x,(select riskname from lmriskapp where riskcode=A.x) riskname"
				+ " ,decode((select subriskflag from lmriskapp where riskcode = A.x),'M','1','0') flag "
				+ " from ("
				+ " select riskcode x"						
				+ " from lcpol where renewcount=0 "				
				+ " and exists ("
				+ " select 1 from lccont where prtno=lcpol.prtno and appflag='0' and salechnl " + mSalechnlSql 
				+ " and conttype='1' and uwflag in ('1','2')"
				+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
				+ addSQL
				+ ReportPubFun.getWherePart("uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				+ ") group by riskcode) A order by flag ,A.x";
			logger.debug("Risk_SQL: " + tsql);
			SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
			sqlbv37.sql(tsql);
			sqlbv37.put("mUWSalechnl", mUWSalechnl);
			sqlbv37.put("mStartDate", mStartDate);
			sqlbv37.put("mEndDate", mEndDate);
			sqlbv37.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv37.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv37.put("mManageLen", mManageLen);
			sqlbv37.put("list", list);
			SSRS tempSSRS = new SSRS();
			tempSSRS = tExeSQL.execSQL(sqlbv37);
			tsql = " select riskcode , count(distinct(contno))	"	
				+ " from lcpol where renewcount=0 "				
				+ " and exists ("
				+ " select 1 from lccont where prtno=lcpol.prtno and appflag='0' "
				+ " and salechnl " + mSalechnlSql 
				+ " and conttype='1' and uwflag = '2' "	
				+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
				+ addSQL
				+ ReportPubFun.getWherePart("uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				+ ") group by riskcode";
			logger.debug("Del_SQL: " + tsql);
			SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
			sqlbv38.sql(tsql);
			sqlbv38.put("mUWSalechnl", mUWSalechnl);
			sqlbv38.put("mStartDate", mStartDate);
			sqlbv38.put("mEndDate", mEndDate);
			sqlbv38.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv38.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv38.put("mManageLen", mManageLen);
			sqlbv38.put("list", list);
			SSRS tempSSRS1 = new SSRS();
			tempSSRS1 = tExeSQL.execSQL(sqlbv38);	
			tsql = " select riskcode , count(distinct(contno))	"	
				+ " from lcpol where renewcount=0 "				
				+ " and exists ("
				+ " select 1 from lccont where prtno=lcpol.prtno and appflag='0' "
				+ " and salechnl " + mSalechnlSql 
				+ " and conttype='1' and uwflag = '1' "	
				+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
				+ addSQL
				+ ReportPubFun.getWherePart("uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				+ ") group by riskcode";
			logger.debug("Ref_SQL: " + tsql);
			SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
			sqlbv39.sql(tsql);
			sqlbv39.put("mUWSalechnl", mUWSalechnl);
			sqlbv39.put("mStartDate", mStartDate);
			sqlbv39.put("mEndDate", mEndDate);
			sqlbv39.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv39.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv39.put("mManageLen", mManageLen);
			sqlbv39.put("list", list);
			SSRS tempSSRS2 = new SSRS();
			tempSSRS2 = tExeSQL.execSQL(sqlbv39);
			
			int row = tempSSRS.getMaxRow();
			String StringArray[][] = new String[row][4];
			Hashtable tHashtable = new Hashtable();
			for (int m = 0; m < tempSSRS.getMaxRow(); m++) {				
				StringArray[m][0] = tempSSRS.GetText(m+1, 1);
				StringArray[m][1] = tempSSRS.GetText(m+1, 2);
				StringArray[m][2] = "0";
				StringArray[m][3] = "0";
				tHashtable.put(tempSSRS.GetText(m+1, 1), new Integer(m));
			}

			ListTable alistTable = new ListTable();
			alistTable.setName("UW18");
			int totalyq=0;
			int totaljb=0;
			for (int i = 0; i < tempSSRS1.getMaxRow(); i++) {
				int tIndex = ((Integer) tHashtable
						.get(tempSSRS1.GetText(i+1, 1))).intValue();
				StringArray[tIndex][2] = tempSSRS1.GetText(i+1, 2);
				totalyq = totalyq + Integer.parseInt(tempSSRS1.GetText(i+1, 2));
			}
			for (int i = 0; i < tempSSRS2.getMaxRow(); i++) {
				int tIndex = ((Integer) tHashtable
						.get(tempSSRS2.GetText(i+1, 1))).intValue();
				StringArray[tIndex][3] = tempSSRS2.GetText(i+1, 2);
				totaljb = totaljb + Integer.parseInt(tempSSRS2.GetText(i+1, 2));
			}
			for (int i = 0; i < row; i++) {
				strArr = new String[4];
				strArr = StringArray[i];
				alistTable.add(strArr);
			}
			if(tempSSRS.getMaxRow()>0){
				xmlexport.addListTable(alistTable, strArr);
			}
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			texttag.add("totalyq", totalyq);
			texttag.add("totaljb", totaljb);
			
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	/**
	 * 机构延期拒保险种明细表
	 * */
	private boolean PrePareDataUW19() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW19.vts", "printer");
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
			sqlbv40.sql(tReportSQL);
			sqlbv40.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv40);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			String tsql = "";
			String strArr[] = null;
			
			
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
				//管理机构为所有管理机构
				addSQL=" and managecom >= '"
					+ "?mManageCom0?"
					+ "' and managecom <= '"
					+ "?mManageCom9?"
					+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and managecom >= '"
					+ "?mManageCom0?"
					+ "' and managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			tsql = " select (case when sum(Z.A) is not null then sum(Z.A)  else 0 end),(case when sum(Z.B) is not null then sum(Z.B)  else 0 end),(case when sum(Z.C) is not null then sum(Z.C)  else 0 end),(case when sum(Z.D) is not null then sum(Z.D)  else 0 end),(case when sum(Z.E) is not null then sum(Z.E)  else 0 end),(case when sum(Z.F) is not null then sum(Z.F)  else 0 end),(case when sum(Z.G) is not null then sum(Z.G)  else 0 end)"   
				+ " from ("
				+ " select (case when suppriskscore = '50' then 1 else  0 end) A,(case when suppriskscore = '75' then 1 else  0 end) B,"
				+ " (case when suppriskscore = '100' then 1 else  0 end) C, (case when suppriskscore = '125' then 1 else  0 end) D,"
				+ " (case when suppriskscore = '150' then 1 else  0 end) E,(case when suppriskscore = '200' then 1 else  0 end) F,"
				+ " (case when suppriskscore >= '201' then 1 else  0 end) G"
				+ " from lcprem where "
				+ " payplancode like '000000%' and addfeedirect='01' "
				+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				+ " and exists ("
				+ " select 1 from lccont where contno=lcprem.contno "
				+ " and salechnl " + mSalechnlSql 
				+ " and conttype='1' "	
				+ " and exists (select '1' from es_doc_main where doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
				+  addSQL
				+ " )) Z ";
//			tsql = tsql + addSQL;
//			tsql = tsql + " and makedate>='"+mStartDate+"' and makedate<='"+mEndDate+"'";
//			tsql = tsql + ") ) A group by A.x";
			logger.debug("Comcode_SQL: " + tsql);
			SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
			sqlbv41.sql(tsql);
			sqlbv41.put("mUWSalechnl", mUWSalechnl);
			sqlbv41.put("mStartDate", mStartDate);
			sqlbv41.put("mEndDate", mEndDate);
			sqlbv41.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv41.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv41.put("mManageLen", mManageLen);
			sqlbv41.put("list", list);
			SSRS tempSSRS = new SSRS();
			tempSSRS = tExeSQL.execSQL(sqlbv41);
			
//			ListTable alistTable = new ListTable();
//			alistTable.setName("UW19");
//			int totalyq=0;
//			int totaljb=0;
//			for (int i = 1; i <= tempSSRS.getMaxRow(); i++) {
//				strArr = new String[10];
//				strArr[0] = tempSSRS.GetText(i, 1);
//				strArr[1] = tempSSRS.GetText(i, 2);
//				strArr[2] = tempSSRS.GetText(i, 3);
//				strArr[3] = tempSSRS.GetText(i, 4);
//				totalyq = totalyq + Integer.parseInt(tempSSRS.GetText(i, 3));
//				totaljb = totaljb + Integer.parseInt(tempSSRS.GetText(i, 4));
//				alistTable.add(strArr);
//			}
//			if(tempSSRS.getMaxRow()>0){
//				xmlexport.addListTable(alistTable, strArr);
//			}
			int point1=0,point2=0,point3=0,point4=0,point5=0,point6=0,point7=0,total=0;
			if(tempSSRS.getMaxRow()>0){
				point1 = Integer.parseInt(tempSSRS.GetText(1, 1));
				point2 = Integer.parseInt(tempSSRS.GetText(1, 2));
				point3 = Integer.parseInt(tempSSRS.GetText(1, 3));
				point4 = Integer.parseInt(tempSSRS.GetText(1, 4));
				point5 = Integer.parseInt(tempSSRS.GetText(1, 5));
				point6 = Integer.parseInt(tempSSRS.GetText(1, 6));
				point7 = Integer.parseInt(tempSSRS.GetText(1, 7));
				total = point1+point2+point3+point4+point5+point6+point7;
			}
			
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			texttag.add("point1", point1);
			texttag.add("point2", point2);
			texttag.add("point3", point3);
			texttag.add("point4", point4);
			texttag.add("point5", point5);
			texttag.add("point6", point6);
			texttag.add("point7", point7);
			texttag.add("total", total);
			
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	/**
	 * 次标准体分析表
	 * */
	private boolean PrePareDataUW20() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW20.vts", "printer");
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
			sqlbv42.sql(tReportSQL);
			sqlbv42.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv42);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			String tsql = "";
			String strArr[] = null;
			ListTable aListTable = new ListTable();
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			int row = mLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + mLDComSet.size());

			LDComSchema tLDComSchema;
			Hashtable tHashtable = new Hashtable();
			String StringArray[][] = new String[row][7];
			for (int m = 0; m < mLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(mLDComSet.get(m + 1));
				StringArray[m][0] = tLDComSchema.getComCode();
				StringArray[m][1] = tLDComSchema.getShortName();
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
				//管理机构为所有管理机构
				addSQL=" and managecom >= '"
					+ "?mManageCom0?"
					+ "' and managecom <= '"
					+ "?mManageCom9?"
					+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and managecom >= '"
					+ "?mManageCom0?"
					+ "' and managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}			
			
			tsql = " select (case when count(distinct(prtno)) is not null then count(distinct(prtno))  else 0 end) y from lccont a "
			    + " where conttype='1' and uwflag = '4' and appflag='1' "
			    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
			    + " and exists (select 1 from lcprem b where b.contno = a.contno and b.payplancode like '000000%' and b.payplantype='02')"//有职业加费
			    + ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			    + " and a.salechnl " + mSalechnlSql
			    + addSQL
			    ;
			logger.debug("JobAdd_SQL: " + tsql);
			SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
			sqlbv43.sql(tsql);
			sqlbv43.put("mUWSalechnl", mUWSalechnl);
			sqlbv43.put("mStartDate", mStartDate);
			sqlbv43.put("mEndDate", mEndDate);
			sqlbv43.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv43.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv43.put("mManageLen", mManageLen);
			sqlbv43.put("list", list);
			SSRS tempSSRS1 = new SSRS();
			tempSSRS1 = tExeSQL.execSQL(sqlbv43);
			
			tsql = " select (case when count(distinct(prtno)) is not null then count(distinct(prtno))  else 0 end) y from lccont a "
			    + " where conttype='1' and uwflag = '4' and appflag='1' "
			    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
			    + " and exists (select 1 from lcprem b where b.contno = a.contno and b.payplancode like '000000%' and b.payplantype='01')"//有健康加费
			    + ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			    + " and a.salechnl " + mSalechnlSql
			    + addSQL
			    ;
			logger.debug("HealthAdd_SQL: " + tsql);
			SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
			sqlbv44.sql(tsql);
			sqlbv44.put("mUWSalechnl", mUWSalechnl);
			sqlbv44.put("mStartDate", mStartDate);
			sqlbv44.put("mEndDate", mEndDate);
			sqlbv44.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv44.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv44.put("mManageLen", mManageLen);
			sqlbv44.put("list", list);
			SSRS tempSSRS2 = new SSRS();
			tempSSRS2 = tExeSQL.execSQL(sqlbv44);
			
			tsql = " select (case when count(distinct(prtno)) is not null then count(distinct(prtno))  else 0 end) y from lccont a "
			    + " where conttype='1' and uwflag = '4' and appflag='1' "
			    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
			    + " and exists (select 1 from lccspec b where b.contno = a.contno)"//有职业加费
			    + ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			    + " and a.salechnl " + mSalechnlSql
			    + addSQL
			    ;
			SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
			sqlbv45.sql(tsql);
			sqlbv45.put("mUWSalechnl", mUWSalechnl);
			sqlbv45.put("mStartDate", mStartDate);
			sqlbv45.put("mEndDate", mEndDate);
			sqlbv45.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv45.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv45.put("mManageLen", mManageLen);
			sqlbv45.put("list", list);
			logger.debug("Spec_SQL: " + tsql);
			SSRS tempSSRS3 = new SSRS();
			tempSSRS3 = tExeSQL.execSQL(sqlbv45);
			
			tsql = " select (case when count(distinct(prtno)) is not null then count(distinct(prtno))  else 0 end) y from lccont a "
			    + " where conttype='1' and uwflag = '4' and appflag='1' "
			    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
			    + " and exists (select 1 from lcuwsub b where b.contno = a.contno and b.changepolflag is not null and b.changepolflag<>'0')"//有承保计划变更
			    + ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			    + " and a.salechnl " + mSalechnlSql
			    + addSQL
			    ;
			SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
			sqlbv46.sql(tsql);
			sqlbv46.put("mUWSalechnl", mUWSalechnl);
			sqlbv46.put("mStartDate", mStartDate);
			sqlbv46.put("mEndDate", mEndDate);
			sqlbv46.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv46.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv46.put("mManageLen", mManageLen);
			sqlbv46.put("list", list);
			logger.debug("ChangePol_SQL: " + tsql);
			SSRS tempSSRS4 = new SSRS();
			tempSSRS4 = tExeSQL.execSQL(sqlbv46);
			
			tsql = " select (case when count(distinct(prtno)) is not null then count(distinct(prtno))  else 0 end) y from lccont a "
			    + " where conttype='1' and uwflag = '4' and appflag='1' "
			    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
			    + " and exists (select 1 from lcprem b where b.contno = a.contno and b.payplancode like '000000%' and b.payplantype='03')"//有其他加费
			    + ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			    + " and a.salechnl " + mSalechnlSql
			    + addSQL
			    ;
			SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
			sqlbv47.sql(tsql);
			sqlbv47.put("mUWSalechnl", mUWSalechnl);
			sqlbv47.put("mStartDate", mStartDate);
			sqlbv47.put("mEndDate", mEndDate);
			sqlbv47.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv47.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv47.put("mManageLen", mManageLen);
			sqlbv47.put("list", list);
			logger.debug("OtherAdd_SQL: " + tsql);
			SSRS tempSSRS5 = new SSRS();
			tempSSRS5 = tExeSQL.execSQL(sqlbv47);	
			
			String zyjf="0", jkjf="0",tbyd="0",jhbg="0",qtjf="0";
			if(tempSSRS1.getMaxRow()>0)
				zyjf = tempSSRS1.GetText(1, 1);
			if(tempSSRS2.getMaxRow()>0)
				jkjf = tempSSRS2.GetText(1, 1);
			if(tempSSRS3.getMaxRow()>0)
				tbyd = tempSSRS3.GetText(1, 1);
			if(tempSSRS4.getMaxRow()>0)
				jhbg = tempSSRS4.GetText(1, 1);
			if(tempSSRS5.getMaxRow()>0)
				qtjf = tempSSRS5.GetText(1, 1);
			
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);			
			texttag.add("zyjf", zyjf);
			texttag.add("jkjf", jkjf);
			texttag.add("tbyd", tbyd);
			texttag.add("jhbg", jhbg);
			texttag.add("qtjf", qtjf);
			
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	/**
	 * 次标准体分析表
	 * */
	private boolean PrePareDataUW201() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW20.vts", "printer");
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
			sqlbv48.sql(tReportSQL);
			sqlbv48.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv48);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			String tsql = "";
			String strArr[] = null;
			ListTable aListTable = new ListTable();
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			int row = mLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + mLDComSet.size());

			LDComSchema tLDComSchema;
			Hashtable tHashtable = new Hashtable();
			String StringArray[][] = new String[row][7];
			for (int m = 0; m < mLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(mLDComSet.get(m + 1));
				StringArray[m][0] = tLDComSchema.getComCode();
				StringArray[m][1] = tLDComSchema.getShortName();
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
				//管理机构为所有管理机构
				addSQL=" and managecom >= '"
					+ "?mManageCom0?"
					+ "' and managecom <= '"
					+ "?mManageCom9?"
					+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and managecom >= '"
					+ "?mManageCom0?"
					+ "' and managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}			
			
			tsql = " select A.x,A.y from "
			    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
			    + " where conttype='1' and uwflag = '4' and appflag='1' "
			    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
			    + " and exists (select 1 from lcprem b where b.contno = a.contno and b.payplancode like '000000%' and b.payplantype='02')"//有职业加费
			    + ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			    + " and a.salechnl " + mSalechnlSql
			    + addSQL
			    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
			    ;
			SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
			sqlbv49.sql(tsql);
			sqlbv49.put("mUWSalechnl", mUWSalechnl);
			sqlbv49.put("mStartDate", mStartDate);
			sqlbv49.put("mEndDate", mEndDate);
			sqlbv49.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv49.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv49.put("mManageLen", mManageLen);
			sqlbv49.put("list", list);
			logger.debug("JobAdd_SQL: " + tsql);
			SSRS tempSSRS1 = new SSRS();
			tempSSRS1 = tExeSQL.execSQL(sqlbv49);
			
			tsql = " select A.x,A.y from "
			    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
			    + " where conttype='1' and uwflag = '4' and appflag='1' "
			    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
			    + " and exists (select 1 from lcprem b where b.contno = a.contno and b.payplancode like '000000%' and b.payplantype='01')"//有健康加费
			    + ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			    + " and a.salechnl " + mSalechnlSql
			    + addSQL
			    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
			    ;
			logger.debug("HealthAdd_SQL: " + tsql);
			SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
			sqlbv50.sql(tsql);
			sqlbv50.put("mUWSalechnl", mUWSalechnl);
			sqlbv50.put("mStartDate", mStartDate);
			sqlbv50.put("mEndDate", mEndDate);
			sqlbv50.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv50.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv50.put("mManageLen", mManageLen);
			sqlbv50.put("list", list);
			SSRS tempSSRS2 = new SSRS();
			tempSSRS2 = tExeSQL.execSQL(sqlbv50);
			
			tsql = " select A.x,A.y from "
			    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
			    + " where conttype='1' and uwflag = '4' and appflag='1' "
			    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
			    + " and exists (select 1 from lccspec b where b.contno = a.contno)"//有职业加费
			    + ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			    + " and a.salechnl " + mSalechnlSql
			    + addSQL
			    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
			    ;
			logger.debug("Spec_SQL: " + tsql);
			SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
			sqlbv51.sql(tsql);
			sqlbv51.put("mUWSalechnl", mUWSalechnl);
			sqlbv51.put("mStartDate", mStartDate);
			sqlbv51.put("mEndDate", mEndDate);
			sqlbv51.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv51.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv51.put("mManageLen", mManageLen);
			sqlbv51.put("list", list);
			SSRS tempSSRS3 = new SSRS();
			tempSSRS3 = tExeSQL.execSQL(sqlbv51);
			
			tsql = " select A.x,A.y from "
			    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
			    + " where conttype='1' and uwflag = '4' and appflag='1' "
			    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
			    + " and exists (select 1 from lcuwsub b where b.contno = a.contno and b.changepolflag is not null and b.changepolflag<>'0')"//有承保计划变更
			    + ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			    + " and a.salechnl " + mSalechnlSql
			    + addSQL
			    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
			    ;
			logger.debug("ChangePol_SQL: " + tsql);
			SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
			sqlbv52.sql(tsql);
			sqlbv52.put("mUWSalechnl", mUWSalechnl);
			sqlbv52.put("mStartDate", mStartDate);
			sqlbv52.put("mEndDate", mEndDate);
			sqlbv52.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv52.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv52.put("mManageLen", mManageLen);
			sqlbv52.put("list", list);
			SSRS tempSSRS4 = new SSRS();
			tempSSRS4 = tExeSQL.execSQL(sqlbv52);
			
			tsql = " select A.x,A.y from "
			    + " (select substr(a.managecom,1,"+"?mManageLen?"+") x,count(distinct(prtno)) y from lccont a "
			    + " where conttype='1' and uwflag = '4' and appflag='1' "
			    + " and exists (select '1' from es_doc_main c where c.doccode=a.prtno and c.subtype = 'UA001' )"//有扫描件
			    + " and exists (select 1 from lcprem b where b.contno = a.contno and b.payplancode like '000000%' and b.payplantype='03')"//有其他加费
			    + ReportPubFun.getWherePart("a.signdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			    + " and a.salechnl " + mSalechnlSql
			    + addSQL
			    + " group by substr(a.managecom,1,"+"?mManageLen?"+")) A"
			    ;
			SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
			sqlbv53.sql(tsql);
			sqlbv53.put("mUWSalechnl", mUWSalechnl);
			sqlbv53.put("mStartDate", mStartDate);
			sqlbv53.put("mEndDate", mEndDate);
			sqlbv53.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv53.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv53.put("mManageLen", mManageLen);
			sqlbv53.put("list", list);
			logger.debug("OtherAdd_SQL: " + tsql);
			SSRS tempSSRS5 = new SSRS();
			tempSSRS5 = tExeSQL.execSQL(sqlbv53);	
			
			DecimalFormat mDecimalFormat = new DecimalFormat("0");
			int zyjf=0,jkjf=0,tbyd=0,jhbg=0,qtjf=0;
			for(int i=0; i<tempSSRS1.getMaxRow(); i++){
				int tIndex = ((Integer) tHashtable
						.get(tempSSRS1.GetText(i+1, 1))).intValue();
				StringArray[tIndex][2] = mDecimalFormat.format(tempSSRS1.GetText(i+1, 2));
				zyjf = zyjf + Integer.parseInt(StringArray[tIndex][2]);
			}
			for(int i=0; i<tempSSRS2.getMaxRow(); i++){
				int tIndex = ((Integer) tHashtable
						.get(tempSSRS2.GetText(i+1, 1))).intValue();
				StringArray[tIndex][3] = mDecimalFormat.format(tempSSRS2.GetText(i+1, 2));
				jkjf = jkjf + Integer.parseInt(StringArray[tIndex][3]);
			}
			for(int i=0; i<tempSSRS3.getMaxRow(); i++){
				int tIndex = ((Integer) tHashtable
						.get(tempSSRS3.GetText(i+1, 1))).intValue();
				StringArray[tIndex][4] = mDecimalFormat.format(tempSSRS3.GetText(i+1, 2));
				tbyd = tbyd + Integer.parseInt(StringArray[tIndex][4]);
			}
			for(int i=0; i<tempSSRS4.getMaxRow(); i++){
				int tIndex = ((Integer) tHashtable
						.get(tempSSRS4.GetText(i+1, 1))).intValue();
				StringArray[tIndex][5] = mDecimalFormat.format(tempSSRS4.GetText(i+1, 2));
				jhbg = jhbg + Integer.parseInt(StringArray[tIndex][5]);
			}
			for(int i=0; i<tempSSRS5.getMaxRow(); i++){
				int tIndex = ((Integer) tHashtable
						.get(tempSSRS5.GetText(i+1, 1))).intValue();
				StringArray[tIndex][6] = mDecimalFormat.format(tempSSRS5.GetText(i+1, 2));
				qtjf = qtjf + Integer.parseInt(StringArray[tIndex][6]);
			}
			
			for (int i = 0; i < 7; i++) {
				strArr = new String[7];
				if(StringArray[i][0]!=null){//循环所有记录 可能有为空的行
					strArr = StringArray[i];
					aListTable.add(strArr);
				}
			}			
			aListTable.setName("UW20");
			strArr = new String[7];
			strArr[0] = "86";
			strArr[1] = "总公司";
			strArr[2] = String.valueOf(zyjf);
			strArr[3] = String.valueOf(jkjf);
			strArr[4] = String.valueOf(tbyd);
			strArr[5] = String.valueOf(jhbg);
			strArr[6] = String.valueOf(qtjf);		
		
			aListTable.add(strArr);
			xmlexport.addListTable(aListTable, strArr);			
			
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}	
	
	/*
	 * UW02 撤单原因一览表
	 */
	private boolean PrePareDataUW02() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW02.vts", "printer");
			ListTable aListTable = new ListTable();
			
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
			sqlbv54.sql(tReportSQL);
			sqlbv54.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv54);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}
			
			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);
			
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
	              //管理机构为所有管理机构
					addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
					//管理机构为南北区在ldcode表中描述的管理机构 
					addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			
			String tsql = "";
			String strArr[] = null;
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			ExeSQL ListExeSQL = new ExeSQL();

			String tSQL="";
			tSQL = "select B.x,(select codename from ldcode where codetype ='uwwithdreason' and code=B.x), B.z from ("
				+ " select x,count(1) z "
				+ " from ("
				+ " select decode(commonreasoncode,'99','09',commonreasoncode) x,contno"
				+ " from lccuwmaster a where "
				+ " a.passflag='a' "
				+ " and exists(select 1 from lccont where contno=a.contno "
				+ " and conttype='1' "
				+ ReportPubFun.getWherePart("uwdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1) 
				+ " and salechnl " + mSalechnlSql
				//+ " and exists (select '1' from es_doc_main where busstype='TB' and doccode=lccont.prtno and subtype = 'UA001' )"//有扫描件
				+ ")" 					
				+ addSQL
				+ " )A "
				+" group by x ) B order by B.z desc"
				;		
			SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
			sqlbv55.sql(tSQL);
			sqlbv55.put("mUWSalechnl", mUWSalechnl);
			sqlbv55.put("mStartDate", mStartDate);
			sqlbv55.put("mEndDate", mEndDate);
			sqlbv55.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv55.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv55.put("mManageLen", mManageLen);
			sqlbv55.put("list", list);
			SSRS ListSSRS = new SSRS();
			ListSSRS = ListExeSQL.execSQL(sqlbv55);
			int SumNum = 0;
			String StringArray[][] = new String[ListSSRS.getMaxRow()][3];
			for(int i=0; i<ListSSRS.getMaxRow(); i++)
			{
				StringArray[i][0] = ListSSRS.GetText(i+1, 2);
				StringArray[i][1] = ListSSRS.GetText(i+1, 3);				
				SumNum = SumNum + Integer.parseInt(StringArray[i][1]);
			}

			for (int i = 0; i < ListSSRS.getMaxRow(); i++) {
				strArr = new String[3];
				if(StringArray[i][0]!=null){
					if(SumNum == 0)
						StringArray[i][2] = "0";
					else
						StringArray[i][2] = String.valueOf(PubFun.round(Double.parseDouble(StringArray[i][1])/SumNum,4));					
					strArr = StringArray[i];
					aListTable.add(strArr);
				}
			}

			strArr = new String[3];
			aListTable.setName("UW02");
			strArr[0] = "合  计";
			strArr[1] = String.valueOf(SumNum);
			strArr[2] = " 1 ";
			aListTable.add(strArr);			
			
			xmlexport.addListTable(aListTable, strArr);
			String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
			
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}

	/*
	 * App08 各险种承保情况表
	 */
	private boolean PrepareDataUW03() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW03.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
					+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
			sqlbv56.sql(tReportSQL);
			sqlbv56.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv56);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}

			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			logger.debug("***mTitle: " + mTitle);
			tExeSQL =new ExeSQL();
			tSSRS =new SSRS();
			int dataCount = 0;
			String tsql = "";
			int m; // 管理机构的数量
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			//区域不同有些查询逻辑也是不同的 采用此种方法原因是 先将本次要统计的管理机构放到hashtable中
            //然后在从数据库中取出不通区域管理机构的数据放入缓存 从而防止多次查询数据库 再通过管理机构为标志提取数据
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			int row = mLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + mLDComSet.size());

			LDComSchema tLDComSchema;
			Hashtable tHashtable = new Hashtable();
			for (m = 0; m < mLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(mLDComSet.get(m + 1));
				logger.debug(tLDComSchema.getComCode() + ":"
						+ tLDComSchema.getShortName());
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
			sqlbv57.sql("select code,codename from ldcode where codetype ='penoticeage'");
			tSSRS = tExeSQL.execSQL(sqlbv57);
			dataCount = tSSRS.MaxRow;
			String tSQL="";
			String strArr[][] = new String [dataCount][4];
			String strArry[] =new String [4];
			int PESend =0; //合计
			int PEResult =0;
			int PEAbnormal =0;
			int TS =0;   //每个机构在不通的地方的合计
			int TR =0;
			int TP =0;
			for(int i=1;i<=dataCount;i++){
				//得到统计的年龄范围
				String tAge[] = tSSRS.GetText(i, 1).split("-");
				int tMinAge = Integer.parseInt(tAge[0]);
				int tMaxAge = Integer.parseInt(tAge[1]);
				String tAgeRange =tSSRS.GetText(i, 1);
     		    tSQL = "  select (select count(distinct proposalcontno) from lcpenotice  a where 1=1 "
     		    	+ " and exists (select 1 from loprtmanager where prtseq=a.prtseq "
     		    	+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					+ " )"     		       
     		       + addSQL
     		       + " and exists (select 1 from lccont b,es_doc_main c where b.contno=a.contno and conttype='1'"
	     		       + " and c.doccode=b.prtno and c.subtype = 'UA001'"//有扫描件
	     		       + " and salechnl " + mSalechnlSql
     		       + ")"
     			   + "  and exists (select 1 from ldperson  where to_number(substr(now(), 1, 4)) - to_number(substr(birthday, 1, 4))  "
     			   + "  between "+"?tMinAge?"+" and "+"?tMaxAge?"+" and customerno = a.customerno) "
     			   + " and exists (select 1 from lcappnt where appntno=a.customerno union select 1 from lcinsured where insuredno =a.customerno and sequenceno in ('-1','1'))), "
     			   //回复
     			   + " (select count(distinct proposalcontno) from lcpenotice  a where peresult is not null "
     			   + ReportPubFun.getWherePart("modifydate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
     			   + addSQL
     			   + " and exists (select 1 from lccont b,es_doc_main c where b.contno=a.contno and conttype='1'"
	    		       + " and c.doccode=b.prtno and c.subtype = 'UA001'"//有扫描件
	    		       + " and salechnl " + mSalechnlSql
	    		   + ")"
     			   + " and exists (select 1 from ldperson where to_number(substr(now(), 1, 4)) -to_number(substr(birthday, 1, 4)) "
     			   + " between "+"?tMinAge?"+" and "+"?tMaxAge?"+" and customerno = a.customerno) "
     			   + " and exists (select 1 from lcappnt where appntno=a.customerno union select 1 from lcinsured where insuredno =a.customerno and sequenceno in ('-1','1'))), "
     			   //阳性
     			   + " (select count(distinct proposalcontno) from LCPENotice  a where peresult is not null and peresult like '异常%' "
     			   + ReportPubFun.getWherePart("modifydate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
     			   + addSQL
     			   + " and exists (select 1 from lcappnt where appntno=a.customerno union select 1 from lcinsured where insuredno =a.customerno and sequenceno in ('-1','1')) "
     			   + " and exists (select 1 from lccont b,es_doc_main c where b.contno=a.contno and conttype='1'"
	    		       + " and c.doccode=b.prtno and c.subtype = 'UA001'"//有扫描件
	    		       + " and salechnl " + mSalechnlSql
			       + ")"
     			   + " and exists (select 1 from ldperson  where to_number(substr(now(), 1, 4)) -to_number(substr(birthday, 1, 4)) "
     			   + " between "+"?tMinAge?"+" and "+"?tMaxAge?"+" and customerno = a.customerno)) from dual "
     			   ;
     		    SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
	   			sqlbv58.sql(tSQL);
	   			sqlbv58.put("mUWSalechnl", mUWSalechnl);
	   			sqlbv58.put("mStartDate", mStartDate);
	   			sqlbv58.put("mEndDate", mEndDate);
	   			sqlbv58.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
	   			sqlbv58.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
	   			sqlbv58.put("mManageLen", mManageLen);
	   			sqlbv58.put("list", list);
	   			sqlbv58.put("tMinAge", tMinAge);
	   			sqlbv58.put("tMaxAge", tMaxAge);
				ExeSQL ListExeSQL = new ExeSQL();
				SSRS ListSSRS = new SSRS();
				ListSSRS = ListExeSQL.execSQL(sqlbv58);
				if (ListSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "查询的结果是0");
				}
				aListTable.setName("UW03");
				for (int j = 1; j <= ListSSRS.getMaxRow(); j++) {
				    strArr[i-1][0] = tAgeRange; 
				    strArr[i-1][1] = ListSSRS.GetText(j, 1); //体检发放件数
					strArr[i-1][2] = ListSSRS.GetText(j, 2); //体检回复件数
					strArr[i-1][3] = ListSSRS.GetText(j, 3); //体检阳性件数
				}
			}
			//计算合计
			for(int i=0;i<strArr.length;i++){
				PESend =PESend+Integer.parseInt(strArr[i][1]);
				PEResult=PEResult+Integer.parseInt(strArr[i][2]);
				PEAbnormal=PEAbnormal+Integer.parseInt(strArr[i][3]);
			}
				//转换
			for(int n=0;n<dataCount;n++){
				strArry =new String [4];
				strArry = strArr[n];
				aListTable.add(strArry);
			}
			strArry = new String[4];
			strArry[0]="合 计";
			strArry[1]= String.valueOf(PESend);
			strArry[2]= String.valueOf(PEResult);
			strArry[3]= String.valueOf(PEAbnormal);
			aListTable.add(strArry);
			xmlexport.addListTable(aListTable, strArry);
			
				String CurrentDate = PubFun.getCurrentDate();
				TextTag texttag = new TextTag(); // 新建一个TextTag的实例
				texttag.add("StartDate", mStartDate);
				texttag.add("EndDate", mEndDate);
				texttag.add("StatisticName", mTitle);
				texttag.add("PrintDate", CurrentDate);
				texttag.add("SaleChnl", mSalechnlName);
				logger.debug("大小" + texttag.size());

				if (texttag.size() > 0) {
					xmlexport.addTextTag(texttag);
				}
				mResult.clear();
				mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常UW03！") ;
			return false;
		}
		return true;
	}

	private boolean GetManageComSet(){
		//查询不通统计区域要统计的管理机构范围
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		LDComDB tLDComDB = new LDComDB();
		LDComSet tLDComSet = new LDComSet();
		String tSql = "";
		String tSqlAdd = "";
		String Codesql = "select comcode,ShortName ";
		tSql = "select * ";
		tSqlAdd = " from LDCom where char_length(trim(ComCode))="
				+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
				+ "?mManageLen0?"
				+ "' and comcode <='"
				+ "?mManageLen9?"
				+ "' ";
		if("n".equals(mManageType)||"s".equals(mManageType))
		{
			//南区北区 managecom between '8600' and '8699'				
			tSqlAdd = tSqlAdd + " and comcode " + mManageComSql ;
		}
		tSqlAdd = tSqlAdd + " order by comcode";
		tSql = tSql + tSqlAdd;
		Codesql = Codesql + tSqlAdd;
		tComCodesql.sql(Codesql);
		tComCodesql.put("mManageLen", mManageLen);
		tComCodesql.put("mManageLen0", PubFun.RCh(mManageCom, "0", mManageLen));
		tComCodesql.put("mManageLen9", PubFun.RCh(mManageCom, "9", mManageLen));
		tComCodesql.put("list", list);
		logger.debug("Comcode_SQL: " + tSql);
		LDComSchema tLDComSchema;
		SQLwithBindVariables sqlbv59 = new SQLwithBindVariables();
		sqlbv59.sql(tSql);
		sqlbv59.put("mManageLen", mManageLen);
		sqlbv59.put("mManageLen0", PubFun.RCh(mManageCom, "0", mManageLen));
		sqlbv59.put("mManageLen9", PubFun.RCh(mManageCom, "9", mManageLen));
		sqlbv59.put("list", list);
		tLDComSet.set(tLDComDB.executeQuery(sqlbv59));
		if (tLDComDB.mErrors.needDealError() == true) {
			CError.buildErr(this, "没有PolUWFlowQueryBL没有查询到统计区域");
			return false;
		  }   
		
		mLDComSet.add(tLDComSet);
		return true;
		
	}
	/*
	 * App09 机构体检状况一览表
	 */
	private boolean PrePareDataUW04_07() {
		try {
			int petimes=0;
			if(mUWReportType.equals("UW04")){
				//第一次体检
				petimes=1;
			}else if(mUWReportType.equals("UW07")){
				//第二次体检
				petimes=2;
			}
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW04.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
					+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
			sqlbv60.sql(tReportSQL);
			sqlbv60.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv60);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}

			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
//			ldcode中描述了数组的大小 模板的列数
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);

			String strArr[] = null;
			int m; // 管理机构的数量
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			//区域不同有些查询逻辑也是不同的 采用此种方法原因是 先将本次要统计的管理机构放到hashtable中
            //然后在从数据库中取出不通区域管理机构的数据放入缓存 从而防止多次查询数据库 再通过管理机构为标志提取数据
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
					+ "?mManageCom0?"
					+ "' and a.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}
			
			int row = mLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + mLDComSet.size());

			LDComSchema tLDComSchema;
			Hashtable tHashtable = new Hashtable();
			for (m = 0; m < mLDComSet.size(); m++) {
				tLDComSchema = new LDComSchema();
				tLDComSchema.setSchema(mLDComSet.get(m + 1));
				logger.debug(tLDComSchema.getComCode() + ":"
						+ tLDComSchema.getShortName());
				tHashtable.put(tLDComSchema.getComCode(), new Integer(m));
			}
			int dataCount = 0;
			int Reason0 =0;
			int Reason1 =0;
			int Reason2 =0;
			int Reason3 =0;
			int Reason4 =0;
			int Reason5 =0;
			int Reason6 =0;
			int Reason7 =0;
			int Reason8 =0;
			int Reason9 =0;
			int Reason10 =0;
			int Reason11 =0;
			int Reason12 =0;
			int Reason13 =0;
			int Reason14 =0;
			int Reason15 =0;
			int Reason16 =0;
			int Reason17 =0;
			String tSQL="";
			//得到所有原因
			SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
			sqlbv61.sql("select Code, CodeName, CodeAlias, ComCode, OtherSign from ldcode where 1 = 1 and codetype = 'pereason' order by Code");
			tSSRS = tExeSQL.execSQL(sqlbv61);
				
//			tSQL = "select A.x,(select ShortName from ldcom where comcode = A.x),a.pereason,y from ("
//				+ " select substr(a.managecom,1,"+mManageLen+") x,a.pereason,count(distinct a.proposalcontno)y "
//		    	+ " from lcpenotice a where petimes = '"+petimes+"'"
//		    	+ " and exists(select 1 from lcpol where contno=a.contno "
//				+ " and conttype='1' and renewcount=0 "
//				+ " and salechnl " + mSalechnlSql
//				+ " and exists (select '1' from es_doc_main where doccode=lcpol.prtno and subtype = 'UA001' )"//有扫描件
//				+ ")" 
//				+ " and exists (select 1 from loprtmanager c where c.prtseq=a.prtseq "
//				+ ReportPubFun.getWherePart("c.makedate", mStartDate, mEndDate, 1)
//				+ " )"
//		    	+ addSQL
//		    	+ " group by substr(a.managecom,1,"+mManageLen+"),a.pereason,a.proposalcontno )A"
//		    	;     	   

				tSQL = "select A.x,(select ShortName from ldcom where comcode = A.x),"
//						+ " A.z,"
						+ " sum(A.aa),sum(A.ab),sum(A.ac),sum(A.ad),sum(A.ae),"
						+ " sum(A.af),sum(A.ag),sum(A.ah),sum(A.ai),sum(A.aj),sum(A.ak),"
						+ " sum(A.al),sum(A.am),sum(A.an),sum(A.ao),sum(A.ap),sum(A.aq),"
						+ " sum(A.ar)from (select substr(a.managecom, 1, "+"?mManageLen?"+") x,"
//						+ " a.pereason z, "
						+ " sum((case pereason when '01' then 1 else 0 end)) aa,"
						+ " sum((case pereason when '02' then 1 else 0 end)) ab,"
						+ " sum((case pereason when '03' then 1 else 0 end)) ac,"
						+ " sum((case pereason when '04' then 1 else 0 end)) ad,"
						+ " sum((case pereason when '05' then 1 else 0 end)) ae,"
						+ " sum((case pereason when '06' then 1 else 0 end)) af,"
						+ " sum((case pereason when '07' then 1 else 0 end)) ag,"
						+ " sum((case pereason when '08' then 1 else 0 end)) ah,"
						+ " sum((case pereason when '09' then 1 else 0 end)) ai,"
						+ " sum((case pereason when '10' then 1 else 0 end)) aj,"
						+ " sum((case pereason when '11' then 1 else 0 end)) ak,"
						+ " sum((case pereason when '12' then 1 else 0 end)) al,"
						+ " sum((case pereason when '13' then 1 else 0 end)) am,"
						+ " sum((case pereason when '14' then 1 else 0 end)) an,"
						+ " sum((case pereason when '15' then 1 else 0 end)) ao,"
						+ " sum((case pereason when '16' then 1 else 0 end)) ap,"
						+ " sum((case pereason when '17' then 1 else 0 end)) aq,"
						+ " sum((case pereason when '18' then 1 else 0 end)) ar"
						+ " from lcpenotice a where petimes = '"+"?petimes?"+"' and exists"
						+ " (select 1 from lcpol where contno = a.contno"
						+ " and conttype = '1' and renewcount = 0 and salechnl "+ mSalechnlSql
						+ " and exists (select '1' from es_doc_main where doccode = lcpol.prtno"
						+ " and subtype = 'UA001')) and exists (select 1 from loprtmanager c"
						+ " where c.prtseq = a.prtseq "
						+ ReportPubFun.getWherePart("c.makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
						+ " )"
						+ addSQL
						+ " group by substr(a.managecom, 1, "+"?mManageLen?"+")"
//						+", a.pereason"
						+ " ) A"
						+ " group by A.x order by A.x";
				 	SQLwithBindVariables sqlbv62 = new SQLwithBindVariables();
		   			sqlbv62.sql(tSQL);
		   			sqlbv62.put("petimes", petimes);
		   			sqlbv62.put("mUWSalechnl", mUWSalechnl);
		   			sqlbv62.put("mStartDate", mStartDate);
		   			sqlbv62.put("mEndDate", mEndDate);
		   			sqlbv62.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
		   			sqlbv62.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
		   			sqlbv62.put("mManageLen", mManageLen);
		   			sqlbv62.put("list", list);
				ExeSQL ListExeSQL = new ExeSQL();
				SSRS ListSSRS = new SSRS();
				ListSSRS = ListExeSQL.execSQL(sqlbv62);
				if (ListSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "查询的结果是0");
				}
				strArr = new String[maxColCount];
				String StringArray[][] = new String[ListSSRS.getMaxRow()+1][ListSSRS.getMaxCol()+1];
				int num=0;
				for(int j=1;j<=ListSSRS.MaxRow;j++){
//					int tIndex = ((Integer) tHashtable
//							.get(ListSSRS.GetText(j, 1))).intValue();
					StringArray[j-1][0] = ListSSRS.GetText(j, 1);
					StringArray[j-1][1] = ListSSRS.GetText(j, 2);
					StringArray[j-1][2] = ListSSRS.GetText(j, 3);
					StringArray[j-1][3] = ListSSRS.GetText(j, 4);
					StringArray[j-1][4] = ListSSRS.GetText(j, 5);
					StringArray[j-1][5] = ListSSRS.GetText(j, 6);
					StringArray[j-1][6] = ListSSRS.GetText(j, 7);
					StringArray[j-1][7] = ListSSRS.GetText(j, 8);
					StringArray[j-1][8] = ListSSRS.GetText(j, 9);
					StringArray[j-1][9] = ListSSRS.GetText(j, 10);
					StringArray[j-1][10] = ListSSRS.GetText(j, 11);
					StringArray[j-1][11] = ListSSRS.GetText(j, 12);
					StringArray[j-1][12] = ListSSRS.GetText(j, 13);
					StringArray[j-1][13] = ListSSRS.GetText(j, 14);
					StringArray[j-1][14] = ListSSRS.GetText(j, 15);
					StringArray[j-1][15] = ListSSRS.GetText(j, 16);
					StringArray[j-1][16] = ListSSRS.GetText(j, 17);
					StringArray[j-1][17] = ListSSRS.GetText(j, 18);
					StringArray[j-1][18] = ListSSRS.GetText(j, 19);
					StringArray[j-1][19] = ListSSRS.GetText(j, 20);
					//logger.debug("ListSSRS.GetText(j, 21):  "+ListSSRS.GetText(j, 21));
//				 for(int i=1;i<=tSSRS.MaxRow;i++){
//					 if(tSSRS.GetText(i, 1).equals(ListSSRS.GetText(j, 3))){
//						 StringArray[tIndex][i+1] =ListSSRS.GetText(j, 4);
//					 }else
//						 if(StringArray[tIndex][i+1]==null){
//							 StringArray[tIndex][i+1]= String.valueOf(num+0);
//							}
//					}
				}
				for (int i = 0; i < ListSSRS.getMaxRow(); i++) {
					strArr = new String[ListSSRS.getMaxCol()+1];
//					if(StringArray[i][0]!=null){//循环所有记录 可能有为空的行
					strArr = StringArray[i];
					aListTable.add(strArr);
//					}
				}
				for (int j=0;j< ListSSRS.getMaxRow();j++){
					if(StringArray[j][0]!=null){
						Reason0 = Reason0+Integer.parseInt(StringArray[j][2]);
						Reason1 = Reason1+Integer.parseInt(StringArray[j][3]);
						Reason2 = Reason2+Integer.parseInt(StringArray[j][4]);
						Reason3 = Reason3+Integer.parseInt(StringArray[j][5]);
						Reason4 = Reason4+Integer.parseInt(StringArray[j][6]);
						Reason5 = Reason5+Integer.parseInt(StringArray[j][7]);
						Reason6 = Reason6+Integer.parseInt(StringArray[j][8]);
						Reason7 = Reason7+Integer.parseInt(StringArray[j][9]);
						Reason8 = Reason8+Integer.parseInt(StringArray[j][10]);
						Reason9 = Reason9+Integer.parseInt(StringArray[j][11]);
						Reason10 = Reason10+Integer.parseInt(StringArray[j][12]);
						Reason11 = Reason11+Integer.parseInt(StringArray[j][13]);
						Reason12 = Reason12+Integer.parseInt(StringArray[j][14]);
						Reason13 = Reason13+Integer.parseInt(StringArray[j][15]);
						Reason14 = Reason14+Integer.parseInt(StringArray[j][16]);
						Reason15 = Reason15+Integer.parseInt(StringArray[j][17]);
						Reason16 = Reason16+Integer.parseInt(StringArray[j][18]);
						Reason17 = Reason17+Integer.parseInt(StringArray[j][19]);
					}
				}
				aListTable.setName("UW04");
			strArr = new String[20];
			strArr[0] = "86";
			strArr[1] = "总公司";
			strArr[2] = String.valueOf(Reason0);
			strArr[3] = String.valueOf(Reason1);
			strArr[4] = String.valueOf(Reason2);
			strArr[5] = String.valueOf(Reason3);
			strArr[6] = String.valueOf(Reason4);
			strArr[7] = String.valueOf(Reason5);
			strArr[8] = String.valueOf(Reason6);
			strArr[9] = String.valueOf(Reason7);
			strArr[10] = String.valueOf(Reason8);
			strArr[11] = String.valueOf(Reason9);
			strArr[12] = String.valueOf(Reason10);
			strArr[13] = String.valueOf(Reason11);
			strArr[14] = String.valueOf(Reason12);
			strArr[15] = String.valueOf(Reason13);
			strArr[16] = String.valueOf(Reason14);
			strArr[17] = String.valueOf(Reason15);
			strArr[18] = String.valueOf(Reason16);
			strArr[19] = String.valueOf(Reason17);
			
			aListTable.add(strArr);
			xmlexport.addListTable(aListTable, strArr);
				String CurrentDate = PubFun.getCurrentDate();
				TextTag texttag = new TextTag(); // 新建一个TextTag的实例
				texttag.add("StartDate", mStartDate);
				texttag.add("EndDate", mEndDate);
				texttag.add("StatisticName", mTitle);
				texttag.add("PrintDate", CurrentDate);
				texttag.add("SaleChnl", mSalechnlName);
				logger.debug("大小" + texttag.size());

				if (texttag.size() > 0) {
					xmlexport.addTextTag(texttag);
				}
				mResult.clear();
				mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常！") ;
			return false;
		}
		return true;
	}

	/*
	 * App10 机构契约调查一览表
	 */
	private boolean PrepareDataApp10() {
		try {
			int maxColCount = 0; // 最大列数
			String subTitle = ""; // 子标题
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign from ldcode where codetype='appreporttype' and code= '"
					+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv63 = new SQLwithBindVariables();
			sqlbv63.sql(tReportSQL);
			sqlbv63.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv63);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError tError = new CError();
				tError.moduleName = "PolAppStatBL";
				tError.functionName = "subDealData";
				tError.errorMessage = "报表类型错误!";
				this.mErrors.addOneError(tError);
				return false;
			}

			mTitle = mTitle + tSSRS.GetText(1, 1); // 报表名称
			subTitle = tSSRS.GetText(1, 2); //
			maxColCount = Integer.parseInt(tSSRS.GetText(1, 3));
			logger.debug("***mTitle: " + mTitle);
			logger.debug("***subTitle: " + subTitle);
			logger.debug("***maxColCount: " + maxColCount);

			int m; // 管理机构的数量
			String tsql = "";
			String strArr[] = null;
			tsql = "select * from LDCom where char_length(trim(ComCode))="
					+ "?mManageLen?" + " and comcode<>'8699' and comcode >='"
					+ "?mManageCom0?"
					+ "' and comcode <='"
					+ "?mManageCom9?"
					+ "' order by comcode";
			SQLwithBindVariables sqlbv64 = new SQLwithBindVariables();
			sqlbv64.sql(tsql);
			sqlbv64.put("mManageLen", mManageLen);
			sqlbv64.put("mManageCom0", PubFun.RCh(mManageCom, "0", mManageLen));
			sqlbv64.put("mManageCom9", PubFun.RCh(mManageCom, "9", mManageLen));
			logger.debug("Comcode_SQL: " + tsql);
			LDComDB tLDComDB = new LDComDB();
			LDComSet tLDComSet = new LDComSet();
			LDComSchema tLDComSchema;
			tLDComSet.set(tLDComDB.executeQuery(sqlbv64));
			if (tLDComDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLDComDB.mErrors);
				CError cError = new CError();
				cError.moduleName = "PolAppStatBL";
				cError.functionName = "dealData";
				cError.errorMessage = "PolAppStatBL在读取数据库时发生错误";
				mErrors.addOneError(cError);
				return false;
			}
			int row = tLDComSet.size() + 1;
			logger.debug("分公司管理机构数目：" + tLDComSet.size());

			strArr = new String[maxColCount];
			String StringArray[][] = new String[row][maxColCount];
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
					StringArray[i][j] = "0"; // 件数
				}
			}

			tExeSQL = new ExeSQL();
			tSSRS = new SSRS();
			String tSQL = "";

			// App10 机构契约调查一览表

			tSQL = "select substr(managecom,1,"
					+ "?mManageLen?"
					+ "),sum(send),sum(back) from ( "
					+ " select A.managecom ,count(distinct A.send) send,0 back from ("
					+ "select (select substr(managecom,1,6) from lccont where contno=LCRReport.contno) managecom, "
					+ " contno send from LCRReport "
					+ "where GrpcontNo='00000000000000000000' "
					+ "and exists(select 1 from lcpol where proposalcontno=LCRReport.proposalcontno and renewcount=0 "
					+ "and managecom >='" + "?mManageCom0?"
					+ "' " + "and managecom <='"
					+ "?mManageCom9?" + "' " + mSalechnlSql
					+ ")" + "and makedate>='" + "?mStartDate?" + "' "
					+ "and makedate<='" + "?mEndDate?" + "' "
					+ ") A group by A.managecom "
					+ ")group by substr(managecom,1," + "?mManageLen?" + ") ";
			logger.debug("*****SQL: " + tSQL);
			SQLwithBindVariables sqlbv65 = new SQLwithBindVariables();
			sqlbv65.sql(tSQL);
			sqlbv65.put("mManageLen", mManageLen);
			sqlbv65.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv65.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv65.put("mStartDate", mStartDate);
			sqlbv65.put("mEndDate", mEndDate);
			sqlbv65.put("mUWSalechnl", mUWSalechnl);
			tSSRS = tExeSQL.execSQL(sqlbv65);
			// 合并数据
			for (m = 1; m <= tSSRS.getMaxRow(); m++) {
				int tIndex = ((Integer) tHashtable.get(tSSRS.GetText(m, 1)))
						.intValue();
				StringArray[tIndex][2] = tSSRS.GetText(m, 2);
				StringArray[tIndex][3] = tSSRS.GetText(m, 3);
			}

			// 计算合计行各列的值
			for (int j = 2; j < maxColCount; j++) {
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
				strArr = new String[maxColCount];
				strArr = StringArray[i];
				alistTable.add(strArr);
			}

			xmlexport.addDisplayControl("displayinfo");
			xmlexport.addListTable(alistTable, strArr);
			texttag.add("SysDate", PubFun.getCurrentDate());
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("Title", mTitle);
			// texttag.add("SubTitle",subTitle);
			texttag.add("SalechnlName", mSalechnlName);

			if (texttag.size() > 0)
				xmlexport.addTextTag(texttag);
			// xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.errorMessage = "统计时发生异常App10！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/*
	 * App11 延期拒保分析表
	 */
	private boolean PrepareDataApp11() {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign from ldcode where codetype='appreporttype' and code= '"
					+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv66 = new SQLwithBindVariables();
			sqlbv66.sql(tReportSQL);
			sqlbv66.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv66);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError tError = new CError();
				tError.moduleName = "PolAppStatBL";
				tError.functionName = "subDealData";
				tError.errorMessage = "报表类型错误!";
				this.mErrors.addOneError(tError);
				return false;
			}

			mTitle = tSSRS.GetText(1, 1); // 报表名称

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
			SQLwithBindVariables sqlbv67 = new SQLwithBindVariables();
			sqlbv67.sql(sql);
			sqlbv67.put("mUWSalechnl", mUWSalechnl);
			sqlbv67.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv67.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv67.put("mStartDate", mStartDate);
			sqlbv67.put("mEndDate", mEndDate);
			SSRS Back_ssrs = Back_exesql.execSQL(sqlbv67);

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
			tError.errorMessage = "统计时发生异常App11！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/*
	 * App12 机构问题件统计表
	 */
	private boolean PrepareDataApp12() {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL = "select codealias,comcode,othersign from ldcode where codetype='appreporttype' and code= '"
					+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv68 = new SQLwithBindVariables();
			sqlbv68.sql(tReportSQL);
			sqlbv68.put("mUWReportType", mUWReportType);			
			tSSRS = tExeSQL.execSQL(sqlbv68);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError tError = new CError();
				tError.moduleName = "PolAppStatBL";
				tError.functionName = "subDealData";
				tError.errorMessage = "报表类型错误!";
				this.mErrors.addOneError(tError);
				return false;
			}

			mTitle = tSSRS.GetText(1, 1); // 报表名称

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
			SQLwithBindVariables sqlbv69 = new SQLwithBindVariables();
			sqlbv69.sql(tQuestsql);
			sqlbv69.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv69.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv69.put("mStartDate", mStartDate);
			sqlbv69.put("mEndDate", mEndDate);
			sqlbv69.put("mUWSalechnl", mUWSalechnl);
			SSRS WT_ssrs = WT_exesql.execSQL(sqlbv69);
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
			CError.buildErr(this, "统计时发生异常App12！") ;
			return false;
		}
		return true;
	}

	private boolean dealData() {
		try {
			/**
			 * 对以下几个报表数据准备（共性：共有四列，分别为：机构代码，机构名称，**件数，**保费） **表示具体的报表，如预收件数和预收保费
			 * App01 机构预收报表 App03 机构犹豫期撤单表 App04 机构期交承保情况表 App05 机构出单前撤单表 App06
			 * 机构延期情况表 App07 机构拒保情况表
			 */
			if (mUWReportType.equals("UW01") 
					) {
				if (!PrePareDataUW01())
					return false;
			}
			// UW02 撤单原因一览表
			else if (mUWReportType.equals("UW02")) {
				if (!PrePareDataUW02())
					return false;
			}
			// Uw03 体检年龄分布表
			else if (mUWReportType.equals("UW03")) {
				if (!PrepareDataUW03())
					return false;
			}
			// UW04 体检原因一览表 UW07 二次体检原因表
			else if (mUWReportType.equals("UW04")||mUWReportType.equals("UW07")) {
 				if (!PrePareDataUW04_07())
					return false;
			}
			//UW05 体检转归状况一览表
			else if (mUWReportType.equals("UW05")) {
				if (!PrePareDataUW05())
					return false;
			}
			// UW06 二次体检情况表 
			else if (mUWReportType.equals("UW06")) {
				if (!PrePareDataUW06())
					return false;
			}
			// UW08 生调原因一览表
			else if (mUWReportType.equals("UW08")) {
				if (!PrePareDataUW08())
					return false;
			}
			// UW09 生调考评表
			else if (mUWReportType.equals("UW09")) {
				if (!PrePareDataUW09())
					return false;
			}
			// UW10 生调扣分项目表
			else if (mUWReportType.equals("UW10")) {
				if (!PrePareDataUW10())
					return false;
			}
			// UW11 延期原因一览表 UW12 拒保原因一览表
			else if (mUWReportType.equals("UW11")||mUWReportType.equals("UW12")) {
				if (!PrePareDataUW11_12())
					return false;
			}
			// UW13 延期年龄一览表 UW14 拒保年龄一览表
			else if (mUWReportType.equals("UW13")||mUWReportType.equals("UW14")) {
				if (!PrePareDataUW13_14())
					return false;
			}
			//延期/拒保职业分布表
			else if (mUWReportType.equals("UW15")) {
				if (!PrePareDataUW15())
					return false;
			}
			//延期保费清单
			else if (mUWReportType.equals("UW16")||mUWReportType.equals("UW17")) {
				if (!PrePareDataUW16())
					return false;
			}
			//机构延期拒保险种明细表
			else if (mUWReportType.equals("UW18")) {
				if (!PrePareDataUW18())
					return false;
			}
			else if (mUWReportType.equals("UW19")) {
				if (!PrePareDataUW19())
					return false;
			}
			else if (mUWReportType.equals("UW20")) {
				if (!PrePareDataUW20())
					return false;
			}
			else if (mUWReportType.equals("App10")) {
				if (!PrepareDataApp10())
					return false;
			}
			// App11 延期拒保分析表
			else if (mUWReportType.equals("App11")) {
				if (!PrepareDataApp11())
					return false;
			}
			// App12 机构问题件统计表
			else if (mUWReportType.equals("App12")) {
				if (!PrepareDataApp12())
					return false;
			}
		    // UW21 商业因素承保保单明细表
			else if	(mUWReportType.equals("UW21") 
					) {
				if (!PrePareDataUW21())
					return false;
			}
		    // UW22 再保件明细表
			else if	(mUWReportType.equals("UW22") 
					) {
				if (!PrePareDataUW22())
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
	
	private boolean PrePareDataUW21() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW21.vts", "printer");
			ListTable aListTable = new ListTable();
			aListTable.setName("LIST");
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL="";
			tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv70 = new SQLwithBindVariables();
			sqlbv70.sql(tReportSQL);
			sqlbv70.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv70);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}

			mTitle = mTitle + tSSRS.GetText(1, 4); // 报表名称
			logger.debug("***mTitle: " + mTitle);

			String strArr[] = null;
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and a.managecom >= '"
				+ "?mManageCom0?"
				+ "' and a.managecom <= '"
				+ "?mManageCom9?"
				+ "' "
				+ " and substr(a.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}

			logger.debug("分公司管理机构数目：" + mLDComSet.size());

			String tSQL_key="";

			tSQL_key ="select substr(a.managecom,1,"+"?mManageLen?"+") ,(select name from ldcom where comcode=substr(a.managecom,1,"+"?mManageLen?"+")), "
					+ " prtno ,prem from lccont a where conttype ='1' and appflag='1' and BussFlag = 'Y'"
					+ ReportPubFun.getWherePart("a.signdate", mStartDate, mEndDate, 1)
					+ addSQL
					+ " and a.salechnl " + mSalechnlSql
					+ " and exists (select '1' from es_doc_main where doccode=a.prtno and subtype = 'UA001' )"//有扫描件
     			   	+ "order by substr(a.managecom,1,"+"?mManageLen?"+")"
					;
			SQLwithBindVariables sqlbv71 = new SQLwithBindVariables();
			sqlbv71.sql(tSQL_key);
			sqlbv71.put("mManageLen", mManageLen);
			sqlbv71.put("mStartDate", mStartDate);
			sqlbv71.put("mEndDate", mEndDate);
			sqlbv71.put("mUWSalechnl", mUWSalechnl);
			sqlbv71.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv71.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv71.put("list", list);
			SSRS tStringResult = tExeSQL.execSQL(sqlbv71);
			aListTable.setName("UW21");

			String StringArray[][] = new String[tStringResult.getMaxRow()][5];

			double hbmult = 0;
			DecimalFormat mDecimalFormat = new DecimalFormat("0.00");
			for (int i = 1; i <= tStringResult.getMaxRow(); i++) 
			{
				StringArray[i-1][0] = String.valueOf(i);
				StringArray[i-1][1] = tStringResult.GetText(i, 1);
				StringArray[i-1][2] = tStringResult.GetText(i, 2);
				StringArray[i-1][3] = tStringResult.GetText(i, 3);
				StringArray[i-1][4] = mDecimalFormat.format(Double.parseDouble(tStringResult.GetText(i, 4)));	
				hbmult = hbmult+Double.parseDouble(tStringResult.GetText(i, 4));
				strArr = new String[5];
				strArr = StringArray[i-1];
				aListTable.add(strArr);
			}

			strArr =new String[8];
			strArr[0]="合计";
			strArr[1]="86";
			strArr[2]="总公司";
			strArr[3]="";
			strArr[4]=mDecimalFormat.format(hbmult);			
			aListTable.add(strArr);

		    xmlexport.createDocument("UW21.vts","printer");//最好紧接着就初始化xml文档
		    xmlexport.addListTable(aListTable, strArr);
		      
		    String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
		    if (texttag.size()>0)
		        xmlexport.addTextTag(texttag);
		    mResult.addElement(xmlexport);
				mResult.clear();
				mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}
	
	private boolean PrePareDataUW22() {
		try {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("UW22.vts", "printer");
			ListTable aListTable = new ListTable();
			aListTable.setName("LIST");
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tReportSQL="";
			tReportSQL = "select codealias,comcode,othersign,codename from ldcode where codetype='uwreporttype' and code= '"
				+ "?mUWReportType?" + "'";
			SQLwithBindVariables sqlbv72 = new SQLwithBindVariables();
			sqlbv72.sql(tReportSQL);
			sqlbv72.put("mUWReportType", mUWReportType);
			tSSRS = tExeSQL.execSQL(sqlbv72);
			if (tSSRS == null || tSSRS.getMaxRow() < 1) {
				CError.buildErr(this, "报表类型错误!") ;
				return false;
			}

			mTitle = tSSRS.GetText(1, 4); // 报表名称
			logger.debug("***mTitle: " + mTitle);

			String strArr[] = new String[9];
			if(!GetManageComSet()){
				CError.buildErr(this, "获取统计区域错误！");
				return false;
			}

			logger.debug("分公司管理机构数目：" + mLDComSet.size());
			String addSQL="";
			if("2".equals(mManageType)||"3".equals(mManageType)){
              //管理机构为所有管理机构
				addSQL=" and b.managecom >= '"
				+ "?mManageCom0?"
				+ "' and b.managecom <= '"
				+ "?mManageCom9?"
				+ "' ";
			}
			if("n".equals(mManageType)||"s".equals(mManageType)){
				//管理机构为南北区在ldcode表中描述的管理机构 
				addSQL=" and b.managecom >= '"
					+ "?mManageCom0?"
					+ "' and b.managecom <= '"
					+ "?mManageCom9?"
					+ "' "
					+ " and substr(b.managecom,1,"+ "?mManageLen?" + ") "+ mManageComSql;
			}

			String tSQL_key="";

			tSQL_key =" select substr(b.managecom,1,"+"?mManageLen?"+") ,(select name from ldcom where comcode=substr(b.managecom,1,"+"?mManageLen?"+")), "
					+ " b.prtno,b.prem,a.ReportReason,a.MakeDate,a.ModifyDate,b.uwflag from LCReinsureReport a, lccont b where a.contno = b.prtno"
					+ ReportPubFun.getWherePart("a.MakeDate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					+ addSQL
					+ " and b.salechnl " + mSalechnlSql
					+ " and exists (select '1' from es_doc_main where doccode=b.prtno and subtype = 'UA001' )"//有扫描件
     			   	+" order by substr(b.managecom,1,"+"?mManageLen?"+"),a.MakeDate"
					;
			SQLwithBindVariables sqlbv73 = new SQLwithBindVariables();
			sqlbv73.sql(tSQL_key);
			sqlbv73.put("mManageLen", mManageLen);
			sqlbv73.put("mStartDate", mStartDate);
			sqlbv73.put("mEndDate", mEndDate);
			sqlbv73.put("mUWSalechnl", mUWSalechnl);
			sqlbv73.put("mManageCom0", PubFun.RCh(mManageCom, "0", 8));
			sqlbv73.put("mManageCom9", PubFun.RCh(mManageCom, "9", 8));
			sqlbv73.put("list", list);
			SSRS tStringResult = tExeSQL.execSQL(sqlbv73);
			aListTable.setName("UW22");

			String StringArray[][] = new String[tStringResult.getMaxRow()][9];

			DecimalFormat mDecimalFormat = new DecimalFormat("0.00");
			for (int i = 1; i <= tStringResult.getMaxRow(); i++) 
			{
				StringArray[i-1][0] = String.valueOf(i);
				StringArray[i-1][1] = tStringResult.GetText(i, 1);
				StringArray[i-1][2] = tStringResult.GetText(i, 2);
				StringArray[i-1][3] = tStringResult.GetText(i, 3);
				StringArray[i-1][4] = mDecimalFormat.format(Double.parseDouble(tStringResult.GetText(i, 4)));	
				StringArray[i-1][5] = tStringResult.GetText(i, 5);
				StringArray[i-1][6] = tStringResult.GetText(i, 6);
				StringArray[i-1][7] = tStringResult.GetText(i, 7);
				StringArray[i-1][8] = tStringResult.GetText(i, 8);
				strArr = new String[9];
				strArr = StringArray[i-1];
				aListTable.add(strArr);
			}

		    xmlexport.createDocument("UW22.vts","printer");//最好紧接着就初始化xml文档
		    xmlexport.addListTable(aListTable, strArr);
		      
		    String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("StatisticName", mTitle);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SaleChnl", mSalechnlName);
			logger.debug("大小" + texttag.size());
		    if (texttag.size()>0)
		        xmlexport.addTextTag(texttag);
		    mResult.addElement(xmlexport);
				mResult.clear();
				mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "统计时发生异常comm！") ;
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		PolUWFlowQueryBL tPolUWFlowQueryBL = new PolUWFlowQueryBL();
		VData tVData = new VData();
		VData mResult = new VData();
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";
		tG.ComCode = "86";
		String StartDate = "2005-1-1";
		String EndDate = "2009-2-6";
		String ManageType = "4";
		String SalechnlType = "02";
		String ReportType = "UW11";
		tVData.addElement(StartDate);
		tVData.addElement(EndDate);
		tVData.addElement(ManageType);
		tVData.addElement(SalechnlType);
		tVData.addElement(ReportType);
		tVData.addElement(tG);
		tPolUWFlowQueryBL.submitData(tVData, "PRINT");
	}
}
