package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.pubfun.ZHashReport;
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
 * Description: 体检品质明细表
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author duanyh
 * @version 1.0
 */

public class HospitalQualityQuery1BL {
private static Logger logger = Logger.getLogger(HospitalQualityQuery1BL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGI = new GlobalInput();
	private String mStartDate; // 统计开始日期
	private String mEndDate; // 统计结束日期
	private String mAppSalechnl; // 销售渠道
	private String mHQReportType; // 统计报表类型
	private String mSalechnlName;	 

	public HospitalQualityQuery1BL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate))
			return false;

		if (!dealData())
			return false;

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			CError tError = new CError();
			tError.moduleName = "PolAppStatBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "不支持的操作类型!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		if (mGI == null || mGI.ManageCom == null) {
			CError tError = new CError();
			tError.moduleName = "PolAppStatBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "管理机构为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mStartDate = (String) cInputData.get(0);
		mEndDate = (String) cInputData.get(1);
		mAppSalechnl = (String) cInputData.get(2);
		mHQReportType = (String) cInputData.get(3); 
		if (mStartDate == null || mEndDate == null || mHQReportType == null || mAppSalechnl == null)
		{
			CError tError = new CError();
			tError.moduleName = "PolAppStatBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "数据传输失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tSQL = "select codename,codealias from ldcode where codetype='appsalechnl' and code= '"
				+ "?mAppSalechnl?" + "'";
		sqlbv.sql(tSQL);
		sqlbv.put("mAppSalechnl", mAppSalechnl);
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.getMaxRow() < 1) {
			CError.buildErr(this, "销售渠道类型错误!") ;
			return false;
		}

		logger.debug("***mSalechnl: " + tSSRS.GetText(1, 1) + "   "
				+ tSSRS.GetText(1, 2));
		mSalechnlName = tSSRS.GetText(1, 1);		
		return true;
	}

	private boolean PrePareDataHQ01() {
		try {			
			ListTable aListTable = new ListTable();
			aListTable.setName("HQ01");	
			int maxColCount=31;//列数
			String strArr[] = new String[maxColCount];
			String tSQL_key="";
			String tSQL_1="";
			String tSQL_2="";
			String tSQL_3="";
			String tSQL_4="";
			String tSQL_5="";
			String tSQL_6="";
			String tSQL_7="";
			String tSQL_8="";
			String tSQL_9="";
			String tSQL_10="";
			String tSQL_11="";
			String tSQL_12="";
			String tSQL_13="";
			String tSQL_14="";
			String tSQL_15="";
			String tSQL_16="";
			String tSQL_17="";
			String tSQL_18="";
			String tSQL_19="";
			String tSQL_20="";
			String tSQL_21="";
			String tSQL_22="";
			String tSQL_23="";
			String tSQL_24="";
			String tSQL_25="";
			String tSQL_26="";
			String tSQL_27="";
			String tSQL_28="";
			String tSQL_29="";
			String tSQL_30="";
			
			String tSalechnlSql = "";
			if(!mAppSalechnl.equals(""))
			{
				tSalechnlSql = " and exists (select 1 from lccont where prtno=a.contno and salechnl='"+"?mAppSalechnl?"+"')";
				if(mAppSalechnl.equals("12"))
					tSalechnlSql = " and exists (select 1 from lccont where prtno=a.contno and salechnl in ('05','06','08'))";
				else if(mAppSalechnl.equals("13"))
					tSalechnlSql = " and exists (select 1 from lccont where prtno=a.contno and salechnl in ('01','02','09','10','11'))";
			}
			SQLwithBindVariables sqlbv0 = new SQLwithBindVariables();
			tSQL_key ="select distinct(contno) from lchospitalqualityrecord a where 1=1 "
					 + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					 + tSalechnlSql
					 + " order by contno";
			sqlbv0.sql(tSQL_key);
			sqlbv0.put("mAppSalechnl", mAppSalechnl);
			sqlbv0.put("mStartDate", mStartDate);
			sqlbv0.put("mEndDate", mEndDate);
			tSQL_1 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0101%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL_1);
			sqlbv1.put("mAppSalechnl", mAppSalechnl);
			sqlbv1.put("mStartDate", mStartDate);
			sqlbv1.put("mEndDate", mEndDate);
			tSQL_2 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0102%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL_2);
			sqlbv2.put("mAppSalechnl", mAppSalechnl);
			sqlbv2.put("mStartDate", mStartDate);
			sqlbv2.put("mEndDate", mEndDate);
			tSQL_3 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0103%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSQL_3);
			sqlbv3.put("mAppSalechnl", mAppSalechnl);
			sqlbv3.put("mStartDate", mStartDate);
			sqlbv3.put("mEndDate", mEndDate);
			tSQL_4 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0201%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSQL_4);
			sqlbv4.put("mAppSalechnl", mAppSalechnl);
			sqlbv4.put("mStartDate", mStartDate);
			sqlbv4.put("mEndDate", mEndDate);
			tSQL_5 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0301%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL_5);
			sqlbv5.put("mAppSalechnl", mAppSalechnl);
			sqlbv5.put("mStartDate", mStartDate);
			sqlbv5.put("mEndDate", mEndDate);
			tSQL_6 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0302%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSQL_6);
			sqlbv6.put("mAppSalechnl", mAppSalechnl);
			sqlbv6.put("mStartDate", mStartDate);
			sqlbv6.put("mEndDate", mEndDate);
			tSQL_7 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0303%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tSQL_7);
			sqlbv7.put("mAppSalechnl", mAppSalechnl);
			sqlbv7.put("mStartDate", mStartDate);
			sqlbv7.put("mEndDate", mEndDate);
			tSQL_8 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0401%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tSQL_8);
			sqlbv8.put("mAppSalechnl", mAppSalechnl);
			sqlbv8.put("mStartDate", mStartDate);
			sqlbv8.put("mEndDate", mEndDate);
			tSQL_9 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0402%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tSQL_9);
			sqlbv9.put("mAppSalechnl", mAppSalechnl);
			sqlbv9.put("mStartDate", mStartDate);
			sqlbv9.put("mEndDate", mEndDate);
			tSQL_10 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0403%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tSQL_10);
			sqlbv10.put("mAppSalechnl", mAppSalechnl);
			sqlbv10.put("mStartDate", mStartDate);
			sqlbv10.put("mEndDate", mEndDate);
			tSQL_11 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0404%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tSQL_11);
			sqlbv11.put("mAppSalechnl", mAppSalechnl);
			sqlbv11.put("mStartDate", mStartDate);
			sqlbv11.put("mEndDate", mEndDate);
			tSQL_12 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0405%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tSQL_12);
			sqlbv12.put("mAppSalechnl", mAppSalechnl);
			sqlbv12.put("mStartDate", mStartDate);
			sqlbv12.put("mEndDate", mEndDate);
			tSQL_13 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0501%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tSQL_13);
			sqlbv13.put("mAppSalechnl", mAppSalechnl);
			sqlbv13.put("mStartDate", mStartDate);
			sqlbv13.put("mEndDate", mEndDate);
			tSQL_14 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0502%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(tSQL_14);
			sqlbv14.put("mAppSalechnl", mAppSalechnl);
			sqlbv14.put("mStartDate", mStartDate);
			sqlbv14.put("mEndDate", mEndDate);
			tSQL_15 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0601%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(tSQL_15);
			sqlbv15.put("mAppSalechnl", mAppSalechnl);
			sqlbv15.put("mStartDate", mStartDate);
			sqlbv15.put("mEndDate", mEndDate);
			tSQL_16 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0602%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tSQL_16);
			sqlbv16.put("mAppSalechnl", mAppSalechnl);
			sqlbv16.put("mStartDate", mStartDate);
			sqlbv16.put("mEndDate", mEndDate);
			tSQL_17 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0603%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(tSQL_17);
			sqlbv17.put("mAppSalechnl", mAppSalechnl);
			sqlbv17.put("mStartDate", mStartDate);
			sqlbv17.put("mEndDate", mEndDate);
			tSQL_18 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0604%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(tSQL_18);
			sqlbv18.put("mAppSalechnl", mAppSalechnl);
			sqlbv18.put("mStartDate", mStartDate);
			sqlbv18.put("mEndDate", mEndDate);
			tSQL_19 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0701%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(tSQL_19);
			sqlbv19.put("mAppSalechnl", mAppSalechnl);
			sqlbv19.put("mStartDate", mStartDate);
			sqlbv19.put("mEndDate", mEndDate);
			tSQL_20 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0702%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(tSQL_20);
			sqlbv20.put("mAppSalechnl", mAppSalechnl);
			sqlbv20.put("mStartDate", mStartDate);
			sqlbv20.put("mEndDate", mEndDate);
			tSQL_21 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0703%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.sql(tSQL_21);
			sqlbv21.put("mAppSalechnl", mAppSalechnl);
			sqlbv21.put("mStartDate", mStartDate);
			sqlbv21.put("mEndDate", mEndDate);
			tSQL_22 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0704%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.sql(tSQL_22);
			sqlbv22.put("mAppSalechnl", mAppSalechnl);
			sqlbv22.put("mStartDate", mStartDate);
			sqlbv22.put("mEndDate", mEndDate);
			tSQL_23 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0705%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.sql(tSQL_23);
			sqlbv23.put("mAppSalechnl", mAppSalechnl);
			sqlbv23.put("mStartDate", mStartDate);
			sqlbv23.put("mEndDate", mEndDate);
			tSQL_24 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0801%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql(tSQL_24);
			sqlbv24.put("mAppSalechnl", mAppSalechnl);
			sqlbv24.put("mStartDate", mStartDate);
			sqlbv24.put("mEndDate", mEndDate);
			tSQL_25 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0802%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;		
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			sqlbv25.sql(tSQL_25);
			sqlbv25.put("mAppSalechnl", mAppSalechnl);
			sqlbv25.put("mStartDate", mStartDate);
			sqlbv25.put("mEndDate", mEndDate);
			tSQL_26 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0901%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.sql(tSQL_26);
			sqlbv26.put("mAppSalechnl", mAppSalechnl);
			sqlbv26.put("mStartDate", mStartDate);
			sqlbv26.put("mEndDate", mEndDate);
			tSQL_27 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S1001%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.sql(tSQL_27);
			sqlbv27.put("mAppSalechnl", mAppSalechnl);
			sqlbv27.put("mStartDate", mStartDate);
			sqlbv27.put("mEndDate", mEndDate);
			tSQL_28 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S1002%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
			sqlbv28.sql(tSQL_28);
			sqlbv28.put("mAppSalechnl", mAppSalechnl);
			sqlbv28.put("mStartDate", mStartDate);
			sqlbv28.put("mEndDate", mEndDate);
			tSQL_29 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S1003%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;
			SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
			sqlbv29.sql(tSQL_29);
			sqlbv29.put("mAppSalechnl", mAppSalechnl);
			sqlbv29.put("mStartDate", mStartDate);
			sqlbv29.put("mEndDate", mEndDate);
			tSQL_30 = "select contno ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0000%'"
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + tSalechnlSql
				   +" group by contno"
				   ;		
			SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
			sqlbv30.sql(tSQL_30);
			sqlbv30.put("mAppSalechnl", mAppSalechnl);
			sqlbv30.put("mStartDate", mStartDate);
			sqlbv30.put("mEndDate", mEndDate);
				
//				开始调用ZHashReport
			VData tVData = new VData();
			tVData.add(sqlbv1);
			tVData.add(sqlbv2);
			tVData.add(sqlbv3);
			tVData.add(sqlbv4);
			tVData.add(sqlbv5);
			tVData.add(sqlbv6);
			tVData.add(sqlbv7);
			tVData.add(sqlbv8);
			tVData.add(sqlbv9);
			tVData.add(sqlbv10);
			tVData.add(sqlbv11);
			tVData.add(sqlbv12);
			tVData.add(sqlbv13);
			tVData.add(sqlbv14);
			tVData.add(sqlbv15);
			tVData.add(sqlbv16);
			tVData.add(sqlbv17);
			tVData.add(sqlbv18);
			tVData.add(sqlbv19);
			tVData.add(sqlbv20);
			tVData.add(sqlbv21);
			tVData.add(sqlbv22);
			tVData.add(sqlbv23);
			tVData.add(sqlbv24);
			tVData.add(sqlbv25);
			tVData.add(sqlbv26);
			tVData.add(sqlbv27);
			tVData.add(sqlbv28);
			tVData.add(sqlbv29);
			tVData.add(sqlbv30);
			ZHashReport tZHashReport = new ZHashReport(sqlbv0,tVData);
			for(int k=0;k<maxColCount;k++)
			{
				tZHashReport.setColumnType(k,tZHashReport.StringType);
			}			
			tZHashReport.setDoformat(false);
			String[][] tStringResult = tZHashReport.calItem();
			logger.debug("tStringResult:"+tStringResult.length);
			for (int i = 0; i < tStringResult.length; i++) 
			{
				strArr = new String[maxColCount];
				for(int j=0;j<maxColCount;j++)
				{
					strArr[j] = tStringResult[i][j];
				}
				aListTable.add(strArr);
			}		
			
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("HQ01.vts","printer");//最好紧接着就初始化xml文档
		    xmlexport.addListTable(aListTable, strArr);
		      
		    String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("PrintDate", CurrentDate);
			texttag.add("SalechnlName", mSalechnlName);
			logger.debug("大小" + texttag.size());
		    if (texttag.size()>0)
		        xmlexport.addTextTag(texttag);
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.errorMessage = "统计时发生异常comm！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	private boolean dealData() {
		try {
			/**
			 * HQ01 体检品质明细表
			 * HQ02 体检品质问题统计表
			 * HQ03 核保师体检品质问题统计表
			 * HQ04 体检医院体检品质问题统计表
			 * HQ05 体检医院体续签提示表
			 */
			if (mHQReportType.equals("HQ01")) 
			{
				if (!PrePareDataHQ01())
					return false;
			}
			else {
				CError tError = new CError("报表类型错误！");
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.errorMessage = "统计时发生异常！";
			this.mErrors.addOneError(tError);
			return false;

		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		HospitalQualityQuery1BL tHospitalQualityQuery1BL = new HospitalQualityQuery1BL();
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
		tHospitalQualityQuery1BL.submitData(tVData, "PRINT");
	}
}
