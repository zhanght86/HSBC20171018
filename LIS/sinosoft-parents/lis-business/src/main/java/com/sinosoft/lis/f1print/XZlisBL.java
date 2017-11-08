package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class XZlisBL {
private static Logger logger = Logger.getLogger(XZlisBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String strInsuredSex = "";

	private String strRiskCode = "";

	private String strPayEndYear = "";

	private String strInsuredYear = "";

	private String strPolSum = "";

	private String strBonusYear = "";

	private String strPolNo = "";

	private String strPayEndYearFlag = "";
	
	private String strCvaliDate ="";
	
	private String strPayIntv ="";
	
	private String strStartAge ="";
	
	private String strEndAge ="";
	
	public XZlisBL() {
	}

	/** 传输数据的公共方法 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			CError.buildErr(this, "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}

		if (!getPrintData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		if (tTransferData == null || mGlobalInput == null) {
			CError.buildErr(this, "缺少传入后台的参数！");
			return false;
		}

		strInsuredSex = (String) tTransferData.getValueByName("sex");
		strRiskCode = (String) tTransferData.getValueByName("RiskCode");
		strPayEndYear = (String) tTransferData.getValueByName("PayEndYear");
		strInsuredYear = (String) tTransferData.getValueByName("InsuredYear");
		strPolNo = (String) tTransferData.getValueByName("PolNo");
		strPolSum = (String) tTransferData.getValueByName("PolSum");
		strBonusYear = (String) tTransferData.getValueByName("BonusYear");
		strPayEndYearFlag = (String) tTransferData
				.getValueByName("PayEndYearFlag");
		strCvaliDate = (String) tTransferData.getValueByName("CvaliDate");
		strPayIntv = (String) tTransferData.getValueByName("PayIntv");
		strStartAge = (String) tTransferData.getValueByName("StartAge");
		strEndAge = (String) tTransferData.getValueByName("EndAge");

		return true;
	}

	/**
	 * 获取打印数据
	 */
	private boolean getPrintData() {

		ListTable alistTable = new ListTable();
		alistTable.setName("DATA");
		String KeySql="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			KeySql = "select rownum,a.polno,appntname,a.riskcode,(case insuredsex when 0 then '男' when 1 then '女' else '不详' end),insuredappage,cvalidate,"
				+ " (fiscalyear-substr(cvalidate,1,4)+1),payyears,insuyear, (select (case when sum(Amnt) is not null then sum(Amnt)  else 0 end) from lcduty where polno=a.polno and  char_length(trim(dutycode))=6 ),"
				+ " (select (case when sum(StandPrem) is not null then sum(StandPrem)  else 0 end) from lcduty where polno=a.polno and  char_length(trim(dutycode))=6 ), bonusmoney, bonuscoef, (select riskname from lmriskapp where riskcode = a.riskcode)," 
				+ " (case payintv when 0 then '趸交' when 12 then '年交' else to_char(payintv) end),a.getyear"
				+ " from lcpol a,lobonuspol b"
				+ " where  a.polno=b.polno"
				+ " and a.grppolno = '00000000000000000000' "
				+ " and appflag = '1'"
				+ ReportPubFun.getWherePart("a.polno", ReportPubFun.getParameterStr(strPolNo,"?strPolNo?"))
				+ ReportPubFun.getWherePart("rownum", "", ReportPubFun.getParameterStr(strPolSum,"?strPolSum?"), 1)
				+ ReportPubFun.getWherePart("b.fiscalyear", ReportPubFun.getParameterStr(strBonusYear,"?strBonusYear?"))
				+ ReportPubFun.getWherePart("a.RiskCode", ReportPubFun.getParameterStr(strRiskCode,"?strRiskCode?"))
				+ ReportPubFun.getWherePart("InsuredSex", ReportPubFun.getParameterStr(strInsuredSex,"?strInsuredSex?"))
				+ ReportPubFun.getWherePart("InsuredAppAge","",ReportPubFun.getParameterStr(strEndAge,"?strEndAge?"), 1)
				+ ReportPubFun.getWherePart("InsuredAppAge",ReportPubFun.getParameterStr(strStartAge,"?strStartAge?"),"",1)
				+ ReportPubFun.getWherePart("PayEndYear", ReportPubFun.getParameterStr(strPayEndYear,"?strPayEndYear?"))
				+ ReportPubFun.getWherePart("InsuYear", ReportPubFun.getParameterStr(strInsuredYear,"?strInsuredYear?"))
				+ ReportPubFun.getWherePart("PayEndYearFlag", ReportPubFun.getParameterStr(strPayEndYearFlag,"?strPayEndYearFlag?"))
				+ ReportPubFun.getWherePartLike("CvaliDate", ReportPubFun.getParameterStr(strCvaliDate,"?strCvaliDate?"))
				+ ReportPubFun.getWherePart("a.PayIntv", ReportPubFun.getParameterStr(strPayIntv,"?strPayIntv?"))
				+ " order by rownum,polno";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			KeySql = "select (@rownum := @rownum + 1) AS rownum,polno,appntname,riskcode,insuredsex,insuredappage,cvalidate,year,payyears,insuyear,Amnt,StandPrem, bonusmoney, bonuscoef,riskname,payintv,getyear from (select @rownum:=0,a.polno,appntname,a.riskcode,(case insuredsex when 0 then '男' when 1 then '女' else '不详' end) insuredsex,insuredappage,cvalidate,"
					+ " (fiscalyear-substr(cvalidate,1,4)+1) year,payyears,insuyear, (select (case when sum(Amnt) is not null then sum(Amnt)  else 0 end) from lcduty where polno=a.polno and  char_length(trim(dutycode))=6 ) Amnt,"
					+ " (select (case when sum(StandPrem) is not null then sum(StandPrem)  else 0 end) from lcduty where polno=a.polno and  char_length(trim(dutycode))=6 ) StandPrem, bonusmoney, bonuscoef, (select riskname from lmriskapp where riskcode = a.riskcode) riskname," 
					+ " (case payintv when 0 then '趸交' when 12 then '年交' else payintv end) payintv,a.getyear"
					+ " from lcpol a,lobonuspol b"
					+ " where  a.polno=b.polno"
					+ " and a.grppolno = '00000000000000000000' "
					+ " and appflag = '1'"
					+ ReportPubFun.getWherePart("a.polno", ReportPubFun.getParameterStr(strPolNo,"?strPolNo?"))
					+ ReportPubFun.getWherePart("b.fiscalyear", ReportPubFun.getParameterStr(strBonusYear,"?strBonusYear?"))
					+ ReportPubFun.getWherePart("a.RiskCode", ReportPubFun.getParameterStr(strRiskCode,"?strRiskCode?"))
					+ ReportPubFun.getWherePart("InsuredSex", ReportPubFun.getParameterStr(strInsuredSex,"?strInsuredSex?"))
					+ ReportPubFun.getWherePart("InsuredAppAge","",ReportPubFun.getParameterStr(strEndAge,"?strEndAge?"), 1)
					+ ReportPubFun.getWherePart("InsuredAppAge",ReportPubFun.getParameterStr(strStartAge,"?strStartAge?"),"",1)
					+ ReportPubFun.getWherePart("PayEndYear", ReportPubFun.getParameterStr(strPayEndYear,"?strPayEndYear?"))
					+ ReportPubFun.getWherePart("InsuYear", ReportPubFun.getParameterStr(strInsuredYear,"?strInsuredYear?"))
					+ ReportPubFun.getWherePart("PayEndYearFlag", ReportPubFun.getParameterStr(strPayEndYearFlag,"?strPayEndYearFlag?"))
					+ ReportPubFun.getWherePartLike("CvaliDate", ReportPubFun.getParameterStr(strCvaliDate,"?strCvaliDate?"))
					+ ReportPubFun.getWherePart("a.PayIntv", ReportPubFun.getParameterStr(strPayIntv,"?strPayIntv?"))
					+ " ) t order by rownum,polno ";
			if ((strPolSum == null) || strPolSum.equals("")) {
				KeySql=KeySql+"";
			}else{
				KeySql=KeySql+"limit 0,?strPolSum?";
			}
			
		}
		logger.debug("sql: " + KeySql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(KeySql);
		sqlbv.put("strPolNo", strPolNo);
		sqlbv.put("strPolSum", strPolSum);
		sqlbv.put("strBonusYear", strBonusYear);
		sqlbv.put("strRiskCode", strRiskCode);
		sqlbv.put("strInsuredSex", strInsuredSex);
		sqlbv.put("strEndAge", strEndAge);
		sqlbv.put("strStartAge", strStartAge);
		sqlbv.put("strPayEndYear", strPayEndYear);
		sqlbv.put("strInsuredYear", strInsuredYear);
		sqlbv.put("strPayEndYearFlag", strPayEndYearFlag);
		sqlbv.put("strCvaliDate", strCvaliDate);
		sqlbv.put("strPayIntv", strPayIntv);
		
		ZHashReport rep = new ZHashReport(sqlbv);

		rep.setColumnType(1, rep.StringType);
		rep.setColumnType(2, rep.StringType);
		rep.setColumnType(3, rep.StringType);
		rep.setColumnType(4, rep.StringType);
		rep.setColumnType(5, rep.StringType);
		rep.setColumnType(6, rep.StringType);
		rep.setColumnType(7, rep.StringType);
		rep.setColumnType(8, rep.StringType);
		rep.setColumnType(9, rep.StringType);
		rep.setColumnType(10, rep.StringType);
		rep.setColumnType(11, rep.StringType);
		rep.setColumnType(12, rep.StringType);
		rep.setColumnType(13, rep.StringType);
		rep.setColumnType(14, rep.StringType);
		rep.setColumnType(15, rep.StringType);
		rep.setColumnType(16, rep.StringType);
		rep.setColumnType(17, rep.StringType);
		
		rep.addValue("Operator", mGlobalInput.Operator);
		rep.addValue("MakeDate", PubFun.getCurrentDate());

		mResult.clear();
		mResult.addElement(rep.createXml("XZlis.vts"));

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

}
