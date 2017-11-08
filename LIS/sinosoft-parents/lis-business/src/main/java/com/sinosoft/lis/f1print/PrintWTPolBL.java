package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: PrintWTPolBL.java
 * </p>
 * <p>
 * Description: 撤单率统计
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

public class PrintWTPolBL {
private static Logger logger = Logger.getLogger(PrintWTPolBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mStartDate = ""; // 统计开始时间
	private String mEndDate = ""; // 统计结束日期
	private String mManageCom = ""; // 统计机构
	private String mStatType = ""; // 统计粒度 (4-二级机构 6-三级机构)
	private String mSaleChnl = ""; // 销售渠道

	public PrintWTPolBL() {
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

		mStartDate = (String) tTransferData.getValueByName("StartDate");
		mEndDate = (String) tTransferData.getValueByName("EndDate");
		mManageCom = (String) tTransferData.getValueByName("ManageCom");
		mStatType = (String) tTransferData.getValueByName("StatType");
		mSaleChnl = (String) tTransferData.getValueByName("SaleChnl");

		if (mStartDate.equals("") || mEndDate.equals("")
				|| mManageCom.equals("") || mStatType.equals("")) {
			CError.buildErr(this, "没有得到足够的查询信息！");
			return false;
		}

		return true;
	}

	/**
	 * 获取打印数据
	 */
	private boolean getPrintData() {
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("PrintWTPolRate.vts", "");

		ListTable tlistTable = new ListTable();
		tlistTable.setName("INFO");

		String strSaleChnl = "";
		if (mSaleChnl != null && !mSaleChnl.equals("")) {
			strSaleChnl = " and exists (select 1 from lcpol where salechnl='"
					+ "?mSaleChnl?" + "' and polno=a.polno)";
		}

		// 指标项：机构代码
		String sql = "select comcode from ldcom " + "where comcode like concat('"
				+ "?mManageCom?" + "','%') and comcode not in('8699') "
				+ "and char_length(trim(comcode))=" + "?mStatType?" + "order by comcode";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mStatType", mStatType);
		String asql = "select substr(managecom,1,"
				+ "?mStatType?"
				+ "),count(distinct endorsementno) "
				+ "From ljagetendorse a where feeoperationtype='WT' AND feefinatype='TF'"
				+ strSaleChnl
				+ " and managecom like concat('?mManageCom?','%') "
				+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				+ " group by substr(managecom,1," + "?mStatType?" + ")";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(asql);
		sqlbv2.put("mManageCom", mManageCom);
		sqlbv2.put("mStatType", mStatType);
		sqlbv2.put("mSaleChnl", mSaleChnl);
		sqlbv2.put("mStartDate", mStartDate);
		sqlbv2.put("mEndDate", mEndDate);
		String bsql = "select substr(managecom,1,"
				+ "?mStatType?"
				+ "),sum(getmoney) "
				+ "From ljagetendorse a where feeoperationtype='WT' AND feefinatype='TF'"
				+ strSaleChnl
				+ "and managecom like concat('?mManageCom?','%') "
				+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"), 1)
				+ " group by substr(managecom,1," + "?mStatType?" + ")";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(bsql);
		sqlbv3.put("mManageCom", mManageCom);
		sqlbv3.put("mStatType", mStatType);
		sqlbv3.put("mSaleChnl", mSaleChnl);
		sqlbv3.put("mStartDate", mStartDate);
		sqlbv3.put("mEndDate", mEndDate);
		String csql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			csql = "select /*+index(a,LACOMMISION_IDX_TMAKEDATE)*/  substr(managecom,1,"
					+ "?mStatType?"
					+ "),count(distinct p14)"
					+ " from lacommision a where managecom like concat('?mManageCom?','%') "
					+ ReportPubFun.getWherePart("tmakedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)
					+ " and grppolno='00000000000000000000' and mainpolno=polno "
					+ "and (flag=0 or (flag=1 and transmoney>=0)) and paycount=1"
					+ strSaleChnl
					+ "group by substr(managecom,1," + "?mStatType?" + ")";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			csql = "select  substr(managecom,1,"
					+ "?mStatType?"
					+ "),count(distinct p14)"
					+ " from lacommision a where managecom like concat('?mManageCom?','%')"
					+ ReportPubFun.getWherePart("tmakedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)
					+ " and grppolno='00000000000000000000' and mainpolno=polno "
					+ "and (flag=0 or (flag=1 and transmoney>=0)) and paycount=1"
					+ strSaleChnl
					+ "group by substr(managecom,1," + "?mStatType?" + ")";
		}
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(csql);
		sqlbv4.put("mManageCom", mManageCom);
		sqlbv4.put("mStatType", mStatType);
		sqlbv4.put("mSaleChnl", mSaleChnl);
		sqlbv4.put("mStartDate", mStartDate);
		sqlbv4.put("mEndDate", mEndDate);
		String dsql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			dsql = "select /*+index(a,LACOMMISION_IDX_TMAKEDATE)*/  substr(managecom,1,"
					+ "?mStatType?"
					+ "),sum(transmoney)"
					+ " from lacommision a where managecom like concat('?mManageCom?','%')"
					+ ReportPubFun.getWherePart("tmakedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)
					+ " and grppolno='00000000000000000000' and mainpolno=polno "
					+ "and (flag=0 or (flag=1 and transmoney>=0)) and paycount=1"
					+ strSaleChnl
					+ "group by substr(managecom,1," + "?mStatType?" + ")";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			dsql = "select substr(managecom,1,"
					+ "?mStatType?"
					+ "),sum(transmoney)"
					+ " from lacommision a where managecom like concat('?mManageCom?','%')"
					+ ReportPubFun.getWherePart("tmakedate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"), ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)
					+ " and grppolno='00000000000000000000' and mainpolno=polno "
					+ "and (flag=0 or (flag=1 and transmoney>=0)) and paycount=1"
					+ strSaleChnl
					+ "group by substr(managecom,1," + "?mStatType?" + ")";
		}
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(dsql);
		sqlbv5.put("mManageCom", mManageCom);
		sqlbv5.put("mStatType", mStatType);
		sqlbv5.put("mSaleChnl", mSaleChnl);
		sqlbv5.put("mStartDate", mStartDate);
		sqlbv5.put("mEndDate", mEndDate);
		VData tVData = new VData();
		tVData.add(sqlbv1);
		tVData.add(sqlbv2);
		tVData.add(sqlbv3);
		tVData.add(sqlbv4);
		tVData.add(sqlbv5);

		HashReport tHashReport = new HashReport(sql, tVData, 5, "Double");
		String[][] arrRate = tHashReport.calReportItem();
		String[] strArr;
		double[] tTotal = { 0, 0, 0, 0, 0 };
		for (int i = 0; i < arrRate.length; i++) {
			strArr = new String[7];
			strArr[0] = ReportPubFun.getMngName(arrRate[i][0]); // 机构
			strArr[1] = Double.valueOf(arrRate[i][1]).intValue() + ""; // 撤单件数
			strArr[2] = Double.valueOf(arrRate[i][3]).intValue() + ""; // 承保件数
			if (strArr[1].equals("0")) {
				strArr[3] = "0%";
			} else {
				strArr[3] = ReportPubFun.functionJD(Double
						.parseDouble(arrRate[i][1])
						* 100
						/ (Double.parseDouble(arrRate[i][1]) + Double
								.parseDouble(arrRate[i][3])), "0.00")
						+ "%"; // 件数占比
			}
			strArr[4] = ReportPubFun.functionJD(Double
					.parseDouble(arrRate[i][2]), "0.00"); // 撤单保费
			strArr[5] = ReportPubFun.functionJD(Double
					.parseDouble(arrRate[i][4]), "0.00"); // 承保保费
			if (strArr[4].equals("0.00")) {
				strArr[6] = "0%";
			} else {
				strArr[6] = ReportPubFun.functionJD(Double
						.parseDouble(arrRate[i][2])
						* 100
						/ (Double.parseDouble(arrRate[i][2]) + Double
								.parseDouble(arrRate[i][4])), "0.00")
						+ "%"; // 金额占比
			}
			tlistTable.add(strArr);

			// 计算合计值
			tTotal[1] += Double.parseDouble(arrRate[i][1]);
			tTotal[2] += Double.parseDouble(arrRate[i][2]);
			tTotal[3] += Double.parseDouble(arrRate[i][3]);
			tTotal[4] += Double.parseDouble(arrRate[i][4]);
		}
		String[] tArr = new String[7];
		tArr[0] = "合计"; // "机构名称";
		tArr[1] = ReportPubFun.functionJD(tTotal[1], "0"); // "撤件件数";
		tArr[2] = ReportPubFun.functionJD(tTotal[3], "0"); // "承保件数";
		tArr[3] = ReportPubFun.functionJD(tTotal[1] * 100
				/ (tTotal[1] + tTotal[3]), "0.00")
				+ "%"; // "件数占比";
		tArr[4] = ReportPubFun.functionJD(tTotal[2], "0.00"); // "撤件保费";
		tArr[5] = ReportPubFun.functionJD(tTotal[4], "0.00"); // "承保保费";
		tArr[6] = ReportPubFun.functionJD(tTotal[2] * 100
				/ (tTotal[2] + tTotal[4]), "0.00")
				+ "%"; // "金额占比";

		tlistTable.add(tArr);

		tArr = new String[7];
		tArr[0] = ""; // "机构名称";
		tArr[1] = ""; // "撤件件数";
		tArr[2] = ""; // "承保件数";
		tArr[3] = ""; // "件数占比";
		tArr[4] = ""; // "撤件保费";
		tArr[5] = ""; // "承保保费";
		tArr[6] = ""; // "金额占比";
		xmlexport.addListTable(tlistTable, tArr);

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("date", PubFun.getCurrentDate());
		logger.debug("大小" + texttag.size());
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("e:\\","PrintWTPolRate.xml");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/* 测试 */
	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ComCode = "86";
		tGlobalInput.ManageCom = "8613";
		tGlobalInput.Operator = "DEBUG";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2006-01-01");
		tTransferData.setNameAndValue("EndDate", "2006-05-31");
		tTransferData.setNameAndValue("ManageCom", "8613");
		tTransferData.setNameAndValue("StatType", "6");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);

		PrintWTPolBL tPrintWTPolBL = new PrintWTPolBL();
		tPrintWTPolBL.submitData(tVData, "PRINT");
	}
}
