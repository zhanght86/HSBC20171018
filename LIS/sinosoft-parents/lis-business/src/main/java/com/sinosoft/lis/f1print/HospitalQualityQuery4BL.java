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
 * Description: 体检品质统计
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

public class HospitalQualityQuery4BL {
private static Logger logger = Logger.getLogger(HospitalQualityQuery4BL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGI = new GlobalInput();
	private String mStartDate; // 统计开始日期
	private String mEndDate; // 统计结束日期	
	private String mHQReportType; // 统计报表类型
	private String mInputManageCom; // 统计机构
	private String mManageComSql; // 管理机构相关SQL
	private String mManageCom;

	public HospitalQualityQuery4BL() {
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
		mManageCom = mGI.ManageCom;
		mStartDate = (String) cInputData.get(0);
		mEndDate = (String) cInputData.get(1);
		mInputManageCom = (String) cInputData.get(2);
		mHQReportType = (String) cInputData.get(3); 
		if (mStartDate == null || mEndDate == null || mHQReportType == null || mInputManageCom == null)
		{
			CError tError = new CError();
			tError.moduleName = "PolAppStatBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "数据传输失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	private boolean PrePareDataHQ04() {
		try {			
			ListTable aListTable = new ListTable();
			aListTable.setName("HQ04");	
			int maxColCount=32;//列数			

			String strArr[] = new String[maxColCount];
			String tSQL_key="";
			String tSQL_0="";
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
			
			mManageComSql = " and exists (select 1 from ldhospital where hospitcode = a.hospitcode and mngcom like concat('"+"?mManageCom?"+"','%') and mngcom like concat('"+"?mInputManageCom?"+"','%')";
			
			
			tSQL_key ="select distinct(hospitcode) from lchospitalqualityrecord a where 1=1 "
					 + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
					 + mManageComSql
					 +" order by hospitcode";
			SQLwithBindVariables sqlbvkey = new SQLwithBindVariables();
			sqlbvkey.sql(tSQL_key);
			sqlbvkey.put("mStartDate", mStartDate);
			sqlbvkey.put("mEndDate", mEndDate);
			sqlbvkey.put("mManageCom", mManageCom);
			sqlbvkey.put("mInputManageCom", mInputManageCom);
			tSQL_0 = "select hospitcode,hospitalname from ldhospital a where exists (select 1 from lchospitalqualityrecord where 1=1" 
			       + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
			       +" and hospitcode = a.hospitcode)"
			       ;
			SQLwithBindVariables sqlbv0 = new SQLwithBindVariables();
			sqlbv0.sql(tSQL_0);
			sqlbv0.put("mStartDate", mStartDate);
			sqlbv0.put("mEndDate", mEndDate);
			tSQL_1 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0101%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL_1);
			sqlbv1.put("mStartDate", mStartDate);
			sqlbv1.put("mEndDate", mEndDate);
			sqlbv1.put("mManageCom", mManageCom);
			sqlbv1.put("mInputManageCom", mInputManageCom);
			tSQL_2 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0102%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL_2);
			sqlbv2.put("mStartDate", mStartDate);
			sqlbv2.put("mEndDate", mEndDate);
			sqlbv2.put("mManageCom", mManageCom);
			sqlbv2.put("mInputManageCom", mInputManageCom);
			tSQL_3 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0103%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSQL_3);
			sqlbv3.put("mStartDate", mStartDate);
			sqlbv3.put("mEndDate", mEndDate);
			sqlbv3.put("mManageCom", mManageCom);
			sqlbv3.put("mInputManageCom", mInputManageCom);
			tSQL_4 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0201%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSQL_4);
			sqlbv4.put("mStartDate", mStartDate);
			sqlbv4.put("mEndDate", mEndDate);
			sqlbv4.put("mManageCom", mManageCom);
			sqlbv4.put("mInputManageCom", mInputManageCom);
			tSQL_5 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0301%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL_5);
			sqlbv5.put("mStartDate", mStartDate);
			sqlbv5.put("mEndDate", mEndDate);
			sqlbv5.put("mManageCom", mManageCom);
			sqlbv5.put("mInputManageCom", mInputManageCom);
			tSQL_6 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0302%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSQL_6);
			sqlbv6.put("mStartDate", mStartDate);
			sqlbv6.put("mEndDate", mEndDate);
			sqlbv6.put("mManageCom", mManageCom);
			sqlbv6.put("mInputManageCom", mInputManageCom);
			tSQL_7 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0303%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tSQL_7);
			sqlbv7.put("mStartDate", mStartDate);
			sqlbv7.put("mEndDate", mEndDate);
			sqlbv7.put("mManageCom", mManageCom);
			sqlbv7.put("mInputManageCom", mInputManageCom);
			tSQL_8 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0401%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tSQL_8);
			sqlbv8.put("mStartDate", mStartDate);
			sqlbv8.put("mEndDate", mEndDate);
			sqlbv8.put("mManageCom", mManageCom);
			sqlbv8.put("mInputManageCom", mInputManageCom);
			tSQL_9 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0402%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tSQL_9);
			sqlbv9.put("mStartDate", mStartDate);
			sqlbv9.put("mEndDate", mEndDate);
			sqlbv9.put("mManageCom", mManageCom);
			sqlbv9.put("mInputManageCom", mInputManageCom);
			tSQL_10 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0403%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tSQL_10);
			sqlbv10.put("mStartDate", mStartDate);
			sqlbv10.put("mEndDate", mEndDate);
			sqlbv10.put("mManageCom", mManageCom);
			sqlbv10.put("mInputManageCom", mInputManageCom);
			tSQL_11 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0404%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tSQL_11);
			sqlbv11.put("mStartDate", mStartDate);
			sqlbv11.put("mEndDate", mEndDate);
			sqlbv11.put("mManageCom", mManageCom);
			sqlbv11.put("mInputManageCom", mInputManageCom);
			tSQL_12 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0405%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tSQL_12);
			sqlbv12.put("mStartDate", mStartDate);
			sqlbv12.put("mEndDate", mEndDate);
			sqlbv12.put("mManageCom", mManageCom);
			sqlbv12.put("mInputManageCom", mInputManageCom);
			tSQL_13 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0501%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tSQL_13);
			sqlbv13.put("mStartDate", mStartDate);
			sqlbv13.put("mEndDate", mEndDate);
			sqlbv13.put("mManageCom", mManageCom);
			sqlbv13.put("mInputManageCom", mInputManageCom);
			tSQL_14 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0502%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(tSQL_14);
			sqlbv14.put("mStartDate", mStartDate);
			sqlbv14.put("mEndDate", mEndDate);
			sqlbv14.put("mManageCom", mManageCom);
			sqlbv14.put("mInputManageCom", mInputManageCom);
			tSQL_15 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0601%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(tSQL_15);
			sqlbv15.put("mStartDate", mStartDate);
			sqlbv15.put("mEndDate", mEndDate);
			sqlbv15.put("mManageCom", mManageCom);
			sqlbv15.put("mInputManageCom", mInputManageCom);
			tSQL_16 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0602%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tSQL_16);
			sqlbv16.put("mStartDate", mStartDate);
			sqlbv16.put("mEndDate", mEndDate);
			sqlbv16.put("mManageCom", mManageCom);
			sqlbv16.put("mInputManageCom", mInputManageCom);
			tSQL_17 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0603%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(tSQL_17);
			sqlbv17.put("mStartDate", mStartDate);
			sqlbv17.put("mEndDate", mEndDate);
			sqlbv17.put("mManageCom", mManageCom);
			sqlbv17.put("mInputManageCom", mInputManageCom);
			tSQL_18 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0604%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(tSQL_18);
			sqlbv18.put("mStartDate", mStartDate);
			sqlbv18.put("mEndDate", mEndDate);
			sqlbv18.put("mManageCom", mManageCom);
			sqlbv18.put("mInputManageCom", mInputManageCom);
			tSQL_19 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0701%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(tSQL_19);
			sqlbv19.put("mStartDate", mStartDate);
			sqlbv19.put("mEndDate", mEndDate);
			sqlbv19.put("mManageCom", mManageCom);
			sqlbv19.put("mInputManageCom", mInputManageCom);
			tSQL_20 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0702%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(tSQL_20);
			sqlbv20.put("mStartDate", mStartDate);
			sqlbv20.put("mEndDate", mEndDate);
			sqlbv20.put("mManageCom", mManageCom);
			sqlbv20.put("mInputManageCom", mInputManageCom);
			tSQL_21 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0703%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.sql(tSQL_21);
			sqlbv21.put("mStartDate", mStartDate);
			sqlbv21.put("mEndDate", mEndDate);
			sqlbv21.put("mManageCom", mManageCom);
			sqlbv21.put("mInputManageCom", mInputManageCom);
			tSQL_22 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0704%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.sql(tSQL_22);
			sqlbv22.put("mStartDate", mStartDate);
			sqlbv22.put("mEndDate", mEndDate);
			sqlbv22.put("mManageCom", mManageCom);
			sqlbv22.put("mInputManageCom", mInputManageCom);
			tSQL_23 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0705%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.sql(tSQL_23);
			sqlbv23.put("mStartDate", mStartDate);
			sqlbv23.put("mEndDate", mEndDate);
			sqlbv23.put("mManageCom", mManageCom);
			sqlbv23.put("mInputManageCom", mInputManageCom);
			tSQL_24 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0801%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql(tSQL_24);
			sqlbv24.put("mStartDate", mStartDate);
			sqlbv24.put("mEndDate", mEndDate);
			sqlbv24.put("mManageCom", mManageCom);
			sqlbv24.put("mInputManageCom", mInputManageCom);
			tSQL_25 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0802%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;	
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			sqlbv25.sql(tSQL_25);
			sqlbv25.put("mStartDate", mStartDate);
			sqlbv25.put("mEndDate", mEndDate);
			sqlbv25.put("mManageCom", mManageCom);
			sqlbv25.put("mInputManageCom", mInputManageCom);
			tSQL_26 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0901%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.sql(tSQL_26);
			sqlbv26.put("mStartDate", mStartDate);
			sqlbv26.put("mEndDate", mEndDate);
			sqlbv26.put("mManageCom", mManageCom);
			sqlbv26.put("mInputManageCom", mInputManageCom);
			tSQL_27 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S1001%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.sql(tSQL_27);
			sqlbv27.put("mStartDate", mStartDate);
			sqlbv27.put("mEndDate", mEndDate);
			sqlbv27.put("mManageCom", mManageCom);
			sqlbv27.put("mInputManageCom", mInputManageCom);
			tSQL_28 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S1002%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
			sqlbv28.sql(tSQL_28);
			sqlbv28.put("mStartDate", mStartDate);
			sqlbv28.put("mEndDate", mEndDate);
			sqlbv28.put("mManageCom", mManageCom);
			sqlbv28.put("mInputManageCom", mInputManageCom);
			tSQL_29 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S1003%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;
			SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
			sqlbv29.sql(tSQL_29);
			sqlbv29.put("mStartDate", mStartDate);
			sqlbv29.put("mEndDate", mEndDate);
			sqlbv29.put("mManageCom", mManageCom);
			sqlbv29.put("mInputManageCom", mInputManageCom);
			tSQL_30 = "select hospitcode ,count(*) from lchospitalqualityrecord a where hospitalquality like '%S0000%'"
				   + ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				   + mManageComSql
				   +" group by hospitcode"
				   ;		
			SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
			sqlbv30.sql(tSQL_30);
			sqlbv30.put("mStartDate", mStartDate);
			sqlbv30.put("mEndDate", mEndDate);
			sqlbv30.put("mManageCom", mManageCom);
			sqlbv30.put("mInputManageCom", mInputManageCom);
				
//				开始调用ZHashReport
			VData tVData = new VData();
			tVData.add(sqlbv0);
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
			ZHashReport tZHashReport = new ZHashReport(sqlbvkey,tVData);
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
			xmlexport.createDocument("HQ04.vts","printer");//最好紧接着就初始化xml文档
		    xmlexport.addListTable(aListTable, strArr);
		      
		    String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("PrintDate", CurrentDate);
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
			sqlbv31.sql("select shortname from ldcom where comcode='"+"?mInputManageCom?"+"'");
			sqlbv31.put("mInputManageCom", mInputManageCom);
			String CH_com = tExeSQL.getOneValue(sqlbv31);
			texttag.add("ManageCom", mInputManageCom+"("+CH_com+")");
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
			if (mHQReportType.equals("HQ04")) 
			{
				if (!PrePareDataHQ04())
					return false;
			}
			else if (mHQReportType.equals("HQ05")) 
			{
				if (!PrePareDataHQ05())
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

	private boolean PrePareDataHQ05() {

		try {			
			
			ExeSQL tExeSQL = new ExeSQL();
//			String tStart = tExeSQL.getOneValue("select substr(add_months(to_date('"+mStartDate+"','yyyy-mm-dd'),-11),0,10) from dual ");
//			String tEnd = tExeSQL.getOneValue("select substr(add_months(to_date('"+mEndDate+"','yyyy-mm-dd'),-11),0,10) from dual ");
			SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
			sqlbv32.sql("select substr(add_months(to_date('"+"?mStartDate?"+"','yyyy-mm-dd'),-12)+1,1,10) from dual ");
			sqlbv32.put("mStartDate", mStartDate);
			String tStart = tExeSQL.getOneValue(sqlbv32);
			SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
			sqlbv33.sql("select substr(add_months(to_date('"+"?mEndDate?"+"','yyyy-mm-dd'),-12)+1,1,10) from dual ");
			sqlbv33.put("mEndDate", mEndDate);
			String tEnd = tExeSQL.getOneValue(sqlbv33);
			
			logger.debug("----"+mStartDate+"------"+tStart);
			logger.debug("----"+mEndDate+"------"+tEnd);
			
			ListTable aListTable = new ListTable();
			aListTable.setName("HQ05");	
			int maxColCount=4;//列数			

			String strArr[] = new String[maxColCount];
			String tSQL_key="";			
			
			SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
			tSQL_key =" select a.hospitcode,a.hospitalname,a.validitydate,( select add_months(validitydate,12)-1 from dual) from ldhospital a "
				     +" where mngcom like concat('"+"?mManageCom?"+"','%') and mngcom like concat('"+"?mInputManageCom?"+"','%')"
					 + ReportPubFun.getWherePart("validitydate", ReportPubFun.getParameterStr(tStart,"?tStart?"), ReportPubFun.getParameterStr(tEnd,"?tEnd?"), 1)
					 +" order by hospitcode";		
			sqlbv34.sql(tSQL_key);
			sqlbv34.put("mManageCom", mManageCom);
			sqlbv34.put("mInputManageCom", mInputManageCom);
			sqlbv34.put("tStart", tStart);
			sqlbv34.put("tEnd", tEnd);
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv34);
			logger.debug("tSSRS.getMaxRow():"+tSSRS.getMaxRow());
			for (int i = 0; i < tSSRS.getMaxRow(); i++) 
			{				
				strArr = new String[maxColCount];
				for(int j=0;j<maxColCount;j++)
				{
					strArr[j] = tSSRS.GetText(i+1, j+1);
				}				
				aListTable.add(strArr);
			}		
			
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("HQ05.vts","printer");//最好紧接着就初始化xml文档
		    xmlexport.addListTable(aListTable, strArr);
		      
		    String CurrentDate = PubFun.getCurrentDate();
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", mStartDate);
			texttag.add("EndDate", mEndDate);
			texttag.add("PrintDate", CurrentDate);			
			SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
			sqlbv35.sql("select shortname from ldcom where comcode='"+"?mInputManageCom?"+"'");
			sqlbv35.put("mInputManageCom", mInputManageCom);
			String CH_com = tExeSQL.getOneValue(sqlbv35);
			texttag.add("ManageCom", mInputManageCom+"("+CH_com+")");
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

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		HospitalQualityQuery4BL tHospitalQualityQuery4BL = new HospitalQualityQuery4BL();
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
		tHospitalQualityQuery4BL.submitData(tVData, "PRINT");
	}
}
