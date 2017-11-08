/**
 * <p>Title:录入外包</p>
 * <p>Description: 1张报表</p>
 * <p>bq1：录入外包异常件工作量统计</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author JL
 * @version 1.0
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ZHashReport;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BPOOperatorWorkQueryBL {
private static Logger logger = Logger.getLogger(BPOOperatorWorkQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mStartDate = ""; // 统计开始时间
	private String mEndDate = ""; // 统计结束日期
	private String mManageCom = ""; // 统计机构
	private String mStatType = ""; // 统计粒度 (4-二级机构 6-三级机构)

	public BPOOperatorWorkQueryBL() {
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

		if (mStartDate.equals("") || mEndDate.equals("")) {
			CError.buildErr(this, "没有得到足够的查询信息！");
			return false;
		}

		return true;
	}

	/**
	 * 获取打印数据
	 */
	private boolean getPrintData() {
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		//加入客户关联工作量统计
//		String keysql = "select defaultoperator from lbmission where activityid in ('0000001090','0000001091','0000001002','0000001404') "
		String keysql = "select defaultoperator from lbmission where activityid in ('0000001090','0000001091','0000001002','0000001404') "
				+ "and outdate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "'";
		sqlbv.sql(keysql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
//		String sql1 = "select defaultoperator, count(*) from lbmission where activityid in ('0000001090','0000001091','0000001002','0000001404') and outdate between '"
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String sql1 = "select defaultoperator, count(*) from lbmission where activityid in (select activityid from lwactivity  where functionid in('10010016','10010017','10010004','10010056')) and outdate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' group by defaultoperator";
		sqlbv1.sql(sql1);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String sql2 = "select defaultoperator, "
				+ "       sum((case substr(missionprop2,3,2) when '11' then 1 else 0 end)) , "
				+ "       sum((case substr(missionprop2,3,2) when '15' then 1 when '35' then 1 else 0 end)), "
				+ "       sum((case substr(missionprop2,3,2) when '21' then 1 else 0 end))  , "
				+ "       sum((case substr(missionprop2,3,2) when '16' then 1 else 0 end)) , "
				+ "       sum((case substr(missionprop2,3,2) when '25' then 1 else 0 end)) "
//				+ "       from lbmission where activityid='0000001090' "
				+ "       from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010016') "
				+ "and outdate between '" + "?mStartDate?" + "' and '" + "?mEndDate?"
				+ "'" + "       group by defaultoperator";
		sqlbv2.sql(sql2);
		sqlbv2.put("mStartDate", mStartDate);
		sqlbv2.put("mEndDate", mEndDate);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String sql3 = "select defaultoperator, "
				+ "       sum((case substr(missionprop2,3,2) when '11' then 1 else 0 end)) , "
				+ "       sum((case substr(missionprop2,3,2) when '15' then 1 when '35' then 1 else 0 end)), "
				+ "       sum((case substr(missionprop2,3,2) when '21' then 1 else 0 end))  , "
				+ "       sum((case substr(missionprop2,3,2) when '16' then 1 else 0 end)) , "
				+ "       sum((case substr(missionprop2,3,2) when '25' then 1 else 0 end)) "
//				+ "       from lbmission where activityid='0000001091' "
				+ "       from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010017') "
				+ "and outdate between '" + "?mStartDate?" + "' and '" + "?mEndDate?"
				+ "'" + "       group by defaultoperator";
		sqlbv3.sql(sql3);
		sqlbv3.put("mStartDate", mStartDate);
		sqlbv3.put("mEndDate", mEndDate);
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		String sql4 = "select defaultoperator, "
				+ "       sum((case substr(missionprop2,3,2) when '11' then 1 else 0 end)) , "
				+ "       sum((case substr(missionprop2,3,2) when '15' then 1 when '35' then 1 else 0 end)), "
				+ "       sum((case substr(missionprop2,3,2) when '21' then 1 else 0 end))  , "
				+ "       sum((case substr(missionprop2,3,2) when '16' then 1 else 0 end)) , "
				+ "       sum((case substr(missionprop2,3,2) when '25' then 1 else 0 end)) "
//				+ "       from lbmission where activityid='0000001002' "
				+ "       from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010004') "
				+ "and outdate between '" + "?mStartDate?" + "' and '" + "?mEndDate?"
				+ "'" + "       group by defaultoperator";
		sqlbv4.sql(sql4);
		sqlbv4.put("mStartDate", mStartDate);
		sqlbv4.put("mEndDate", mEndDate);
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		String sql5 = "select defaultoperator, "
			+ "       sum(1) "
//			+ "       from lbmission where activityid='0000001404' "
			+ "       from lbmission where activityid  in (select activityid from lwactivity  where functionid ='10010056') "
			+ "and outdate between '" + "?mStartDate?" + "' and '" + "?mEndDate?"
			+ "'" + "       group by defaultoperator";
		sqlbv5.sql(sql5);
		sqlbv5.put("mStartDate", mStartDate);
		sqlbv5.put("mEndDate", mEndDate);
		ZHashReport rep = new ZHashReport(sqlbv);
		rep.addSql(sqlbv1);
		rep.addSql(sqlbv2);
		rep.addSql(sqlbv3);
		rep.addSql(sqlbv4);
		rep.addSql(sqlbv5);
		for (int i = 1; i <= 17; i++) {
			rep.setColumnType(i, rep.IntType);
		}
		rep.setSumColumn(true);
		rep.addValue("StartDate", mStartDate);
		rep.addValue("EndDate", mEndDate);
		rep.addValue("date", PubFun.getCurrentDate());

		mResult.clear();
		mResult.addElement(rep.createXml("BPOOperatorWorkQuery.vts"));

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
		tTransferData.setNameAndValue("StartDate", "2007-09-01");
		tTransferData.setNameAndValue("EndDate", "2007-09-30");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);

		BPOOperatorWorkQueryBL tBPOOperatorWorkQueryBL = new BPOOperatorWorkQueryBL();
		tBPOOperatorWorkQueryBL.submitData(tVData, "PRINT");
	}
}
