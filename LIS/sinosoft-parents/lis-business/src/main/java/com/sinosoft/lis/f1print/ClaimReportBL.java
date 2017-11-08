/**
 * <p>Title:需要输入统计项的的清单报表</p>
 * <p>Description: 4张报表</p>
 * <p>claim3：险种赔付状况</p>
 * <p>claim12：核赔人工作量统计</p>
 * <p>claim13：理赔月结案清单（分审核人）</p>
 * <p>claim14：理赔月未结案清单（分审核人）</p>
 * <p>add:</p>
 * <p>claim2：人身险赔付率</p>
 * <p>claim4：理赔经过时间</p>
 * <p>claim8：临时保单出险状况</p>
 * <p>claim9：短险保单出险状况</p>
 * <p>add:</p>
 * <p>claim1：人身险理赔概况</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author guoxiang
 * @version 1.0
 */
package com.sinosoft.lis.f1print;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class ClaimReportBL {
	private static Logger logger = Logger.getLogger(ClaimReportBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mCode = ""; // 统计输入的报表代码

	private String[] mDay = null; // 统计输入的时间项

	private String mManageCom = "";// 统计机构

	private String mManageSql = "";

	private String StatType = "";// 退回率,统计类型

	private String StatTypeName = "";// 退回率,统计类型

	private String CaseType = "";// 案件类型。

	private String CaseResult = "";// 案件结论。

	private String mDefaultOperator = "";// 核陪人

	private String mRiskCode = "";// 险种代码

	private String CaseSection = "";// 案件阶段

	@SuppressWarnings("unused")
	private String CommonDate = "";// 统计时效类型

	public ClaimReportBL() {
	}

	/** 传输数据的公共方法 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINTPAY")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mCode = (String) cInputData.get(0);
		mDay = (String[]) cInputData.get(1);
		mManageCom = (String) cInputData.get(2);
		if ("8699".equals(mManageCom)) {
			mManageSql = "86";
		} else {
			mManageSql = "concat('?mManageCom?' ,'%')";
		}
		StatType = (String) cInputData.get(3);
		StatTypeName = (String) cInputData.get(4);

		// CaseSection = (String)cInputData.get(7);
		// CommonDate = (String)cInputData.get(8);

		mDefaultOperator = (String) cInputData.get(5);
		mRiskCode = (String) cInputData.get(6);

		CaseType = (String) cInputData.get(5);
		CaseResult = (String) cInputData.get(6);

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		logger.debug("统计时间起：" + mDay[0]);
		logger.debug("统计时间止：" + mDay[1]);
		logger.debug("被统计的机构：" + mManageCom);
		logger.debug("操作员：" + mGlobalInput.Operator);
		logger.debug("操作机构：" + mGlobalInput.ManageCom);
		logger.debug("案件阶段：" + CaseSection);

		return true;
	}

	public static String getCounts(SSRS specSSRS, String mManageCom) {
		double tCounts = 0;
		for (int i = 1; i <= specSSRS.MaxRow; i++) {
			if (specSSRS.GetText(i, 1).startsWith(mManageCom)) {
				tCounts++;
			}
		}
		return String.valueOf(tCounts);
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RiskClaimBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	@SuppressWarnings("unchecked")
	private boolean dealData() {
		logger.debug("报表代码类型：" + mCode);

		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ListTable tlistTable = new ListTable();
		String[] strArr = null;
		// 1机构理赔时效
		if (mCode.equals("claim1")) {
			tXmlExport.createDocument("LLClaim1Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim1");

			// 定义列表标题
			String[] Title = new String[8];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";

			logger.debug("---以下 查询列表$*/claim1/ROW/COL内容，并为列表赋值--");
			String strSQL = "select a.mngcom, a.casepaytype, a.endcasedate - a.rgtdate + 1 "
					+ " from LLRegister a where a.mngcom like concat('"+ "?mManageCom?" + "','%')  "
					+ " and a.endcasedate >= to_date('"	+ "?var1?"+ "','yyyy-mm-dd') and a.endcasedate <= to_date('"+ "?var2?"+"','yyyy-mm-dd') order by a.mngcom";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSQL);
			sqlbv1.put("mManageCom", mManageCom);
			sqlbv1.put("var1", mDay[0]);
			sqlbv1.put("var2", mDay[1]);
			logger.debug("机构理赔时效查询语句:" + strSQL);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv1);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			String strSQL_com = "select a.comcode, a.name from ldcom a where char_length(a.comcode) <= 6 and a.comcode like concat('"+ "?mManageCom?" + "','%') order by a.comcode";
			sqlbv2.sql(strSQL_com);
			sqlbv2.put("mManageCom", mManageCom);
			logger.debug("获得管理机构的语句:" + strSQL_com);
			ExeSQL kExeSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kExeSQL.execSQL(sqlbv2);

			for (int i = 1; i <= kSSRS.MaxRow; i++) {
				strArr = new String[8];
				strArr[0] = i + ""; // 序号
				strArr[1] = kSSRS.GetText(i, 1); // 机构代码
				strArr[2] = kSSRS.GetText(i, 2); // 机构名称
				strArr[3] = "0";
				strArr[4] = "0";
				strArr[5] = "0";
				strArr[6] = "0";
				strArr[7] = "0";

				for (int j = 1; j <= tSSRS.MaxRow; j++) {
					// 简易案件
					if (tSSRS.GetText(j, 1).startsWith(kSSRS.GetText(i, 1))
							&& tSSRS.GetText(j, 2).equals("5")) {
						strArr[3] = String
								.valueOf(Long.parseLong(strArr[3]) + 1);// 简易案件数量
						strArr[4] = String.valueOf(Double
								.parseDouble(strArr[4])
								+ Double.parseDouble(tSSRS.GetText(j, 3)));// 简易案件时效总和
					}
					// 一般案件
					else if (tSSRS.GetText(j, 1)
							.startsWith(kSSRS.GetText(i, 1))
							&& !tSSRS.GetText(j, 2).equals("5")) {
						strArr[5] = String
								.valueOf(Long.parseLong(strArr[5]) + 1); // 一般案件数量
						strArr[6] = String.valueOf(Double
								.parseDouble(strArr[6])
								+ Double.parseDouble(tSSRS.GetText(j, 3)));// 一般案件时效总和
					}
				}

				if (Long.parseLong(strArr[3]) + Long.parseLong(strArr[5]) > 0) {
					strArr[7] = new DecimalFormat("0.00").format((Double
							.parseDouble(strArr[4]) + Double
							.parseDouble(strArr[6]))
							/ (Long.parseLong(strArr[3]) + Long
									.parseLong(strArr[5])));// 总理赔时效
				}
				if (Double.parseDouble(strArr[3]) > 0) {
					strArr[4] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[4])
							/ Long.parseLong(strArr[3]));// 简易案件时效
				}
				if (Double.parseDouble(strArr[5]) > 0) {
					strArr[6] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[6])
							/ Long.parseLong(strArr[5]));// 一般案件时效
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);
		}

		// 2机构操作时效
		if (mCode.equals("claim2")) {

			tXmlExport.createDocument("LLClaim2Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim2");

			// 定义列表标题
			String[] Title = new String[10];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			logger.debug("---以下 查询列表$*/claim2/ROW/COL内容，并为列表赋值--");
			String strSQL = " select x.code,x.codename,trunc2((select sum(datediff(to_date(a.outdate,'yyyy-mm-dd'),to_date(b.makedate,'yyyy-mm-dd'))+1)/count (*) "
					+ " from lbmission a, llregister b "
					+ " where a.activityid = '0000005015' "
					+ " and a.missionprop1 = b.rgtno "
					+ " and a.missionprop20= x.code and a.outdate is not null and b.makedate is not null "
					+ " and a.outdate <=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and a.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and b.rgtclass='1'),2) 立案, "
					+ " trunc2((select sum(datediff(to_date(c.outdate,'yyyy-mm-dd'),to_date(d.outdate,'yyyy-mm-dd')) +1)/count(*) "
					+ " from lbmission c,lbmission d where c.missionprop1=d.missionprop1  "
					+ " and c.activityid='0000005035' and d.activityid='0000005015'  "
					+ " and c.MissionProp20 = x.code "
					+ " and c.outdate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and c.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(c.missionprop1,11,2)='05' "
					+ " and not exists (select 1 from LJAGetClaim where FeeOperationType='B' and otherno=c.missionprop1)),2) 审核, "
					+ " trunc2((select sum(datediff(to_date(e.outdate,'yyyy-mm-dd'),to_date(f.outdate,'yyyy-mm-dd')) +1)/count(*) "
					+ " from lbmission e,lbmission f where e.missionprop1=f.missionprop1  "
					+ " and e.activityid='0000005065' and f.activityid='0000005015' "
					+ " and e.MissionProp20= x.code "
					+ " and e.outdate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and e.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(e.missionprop1,11,2)='05' "
					+ " and not exists (select 1 from LJAGetClaim where FeeOperationType='B' and otherno=e.missionprop1)),2) 简易案件, "
					+ " trunc2((select sum(datediff(to_date(h.modifydate,'yyyy-mm-dd'),to_date(g.applydate,'yyyy-mm-dd'))+1)/count(*) "
					+ " from llinqapply g,LLInqConclusion h "
					+ " where  h.inqdept=x.code "
					+ " and g.clmno=h.clmno "
					+ " and g.batno=h.batno "
					+ " and h.modifydate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and h.modifydate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(h.clmno,11,2)='05'),2) 调查, "
					+ " trunc2((select sum(datediff(to_date(c.outdate,'yyyy-mm-dd'),to_date(d.outdate,'yyyy-mm-dd')) +1)/count(*) "
					+ " from lbmission c,lbmission d where c.missionprop1=d.missionprop1  "
					+ " and c.activityid='0000005055' and d.activityid='0000005035' "
					+ " and c.MissionProp20= x.code "
					+ " and c.outdate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and c.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(c.missionprop1,11,2)='05'),2) 签批, "
					+ " trunc2((select sum(datediff(to_date(c.outdate,'yyyy-mm-dd'),to_date(d.outdate,'yyyy-mm-dd')) +1)/count(*) "
					+ " from lbmission c,lbmission d where c.missionprop1=d.missionprop1  "
					+ " and c.activityid='0000005025' and d.activityid='0000005035' "
					+ " and c.MissionProp20= x.code "
					+ " and c.outdate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and c.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(c.missionprop1,11,2)='05'),2) 预付确认, "
					+ " trunc2((select sum(datediff(to_date(c.outdate,'yyyy-mm-dd'),to_date(d.outdate,'yyyy-mm-dd')) +1)/count(*) "
					+ " from lbmission c,lbmission d where c.missionprop1=d.missionprop1  "
					+ " and c.activityid='0000005075' and d.activityid='0000005055' "
					+ " and c.MissionProp20= x.code "
					+ " and c.outdate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and c.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(c.missionprop1,11,2)='05'),2) 结案 "
					+ " from ldcode x "
					+ " where codetype = 'station' "
					+ " and code like "+ mManageSql +"";
			logger.debug("机构操作时效查询语句:" + strSQL);
			sqlbv3.sql(strSQL);
			sqlbv3.put("var1", mDay[0]);
			sqlbv3.put("var2", mDay[1]);
			sqlbv3.put("mManageCom", mManageCom);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv3);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

//			String strSQL_com = "select a.comcode, a.name from ldcom a where length(a.comcode) <= 6 and a.comcode like '"
//					+ mManageCom + "%' order by a.comcode";
//			logger.debug("获得管理机构的语句:" + strSQL_com);
//			ExeSQL kExeSQL = new ExeSQL();
//			SSRS kSSRS = new SSRS();
//			kSSRS = kExeSQL.execSQL(strSQL_com);
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[10];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				strArr[3] = tSSRS.GetText(i, 3); // 立案
				strArr[4] = tSSRS.GetText(i, 4); // 审核
				strArr[5] = tSSRS.GetText(i, 5); // 简易案件
				strArr[6] = tSSRS.GetText(i, 8); // 预付管理
				strArr[7] = tSSRS.GetText(i, 6); // 调查
				strArr[8] = tSSRS.GetText(i, 7); // 签批
				strArr[9] = tSSRS.GetText(i, 9); // 结案
				for (int j = 0; j < strArr.length; j++) {
					if (strArr[j] == null || "null".equals(strArr[j])) {
						strArr[j] = "";
					}
				}

				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		// 3核赔人操作时效
		if (mCode.equals("claim3")) {

			tXmlExport.createDocument("LLClaim3Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim3");

			// 定义列表标题
			String[] Title = new String[11];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			Title[10] = "";
			String optSqlA = " and a.defaultoperator='" + "?mDefaultOperator?"
					+ "' ";
			String optSqlC = " and c.defaultoperator='" + "?mDefaultOperator?"
					+ "' ";
			String optSqlE = " and e.defaultoperator='" + "?mDefaultOperator?"
					+ "' ";
			String optSqlG = " and g.inqper='" + "?mDefaultOperator?" + "' ";
			if ("".equals(mDefaultOperator)) {
				optSqlA = "";
				optSqlC = "";
				optSqlE = "";
				optSqlG = "";
			}
			logger.debug("---以下 查询列表$*/claim3/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			String strSQL = " select x.code,x.codename,trunc2((select sum(datediff(to_date(a.outdate,'yyyy-mm-dd'),to_date(b.makedate,'yyyy-mm-dd'))+1)/count(*) "
					+ " from lbmission a, llregister b "
					+ " where a.activityid = '0000005015' "
					+ " and a.missionprop1 = b.rgtno "
					+ " and a.missionprop20= x.code and a.outdate is not null and b.makedate is not null "
					+ " and a.outdate <=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and a.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ optSqlA
					+ " and b.rgtclass='1'),2) 立案, "
					+ " trunc2((select sum(datediff(to_date(c.outdate,'yyyy-mm-dd'),to_date(d.outdate,'yyyy-mm-dd')) +1)/count(*) "
					+ " from lbmission c,lbmission d where c.missionprop1=d.missionprop1  "
					+ " and c.activityid='0000005035' and d.activityid='0000005015'  "
					+ " and c.MissionProp20 = x.code "
					+ " and c.outdate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and c.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ optSqlC
					+ " and substr(c.missionprop1,11,2)='05' "
					+ " and not exists (select 1 from LJAGetClaim where FeeOperationType='B' and otherno=c.missionprop1)),2) 审核, "
					+ " trunc2((select sum(datediff(to_date(e.outdate,'yyyy-mm-dd'),to_date(f.outdate,'yyyy-mm-dd')) +1)/count(*) "
					+ " from lbmission e,lbmission f where e.missionprop1=f.missionprop1  "
					+ " and e.activityid='0000005065' and f.activityid='0000005015' "
					+ " and e.MissionProp20= x.code "
					+ " and e.outdate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and e.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ optSqlE
					+ " and substr(e.missionprop1,11,2)='05' "
					+ " and not exists (select 1 from LJAGetClaim where FeeOperationType='B' and otherno=e.missionprop1)),2) 简易案件, "
					+ " trunc2((select sum(datediff(to_date(h.modifydate,'yyyy-mm-dd'),to_date(g.applydate,'yyyy-mm-dd'))+1)/count(*) "
					+ " from llinqapply g,LLInqConclusion h "
					+ " where  h.inqdept=x.code "
					+ " and g.clmno=h.clmno "
					+ " and g.batno=h.batno "
					+ " and h.modifydate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and h.modifydate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ optSqlG
					+ " and substr(h.clmno,11,2)='05'),2) 调查, "
					+ " trunc2((select sum(datediff(to_date(c.outdate,'yyyy-mm-dd'),to_date(d.outdate,'yyyy-mm-dd')) +1)/count(*) "
					+ " from lbmission c,lbmission d where c.missionprop1=d.missionprop1  "
					+ " and c.activityid='0000005055' and d.activityid='0000005035' "
					+ " and c.MissionProp20= x.code "
					+ " and c.outdate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and c.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ optSqlC
					+ " and substr(c.missionprop1,11,2)='05'),2) 签批, "
					+ " trunc2((select sum(datediff(to_date(c.outdate,'yyyy-mm-dd'),to_date(d.outdate,'yyyy-mm-dd')) +1)/count(*) "
					+ " from lbmission c,lbmission d where c.missionprop1=d.missionprop1  "
					+ " and c.activityid='0000005025' and d.activityid='0000005035' "
					+ " and c.MissionProp20= x.code "
					+ " and c.outdate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and c.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ optSqlC
					+ " and substr(c.missionprop1,11,2)='05'),2) 预付确认, "
					+ " trunc2((select sum(datediff(to_date(c.outdate,'yyyy-mm-dd'),to_date(d.outdate,'yyyy-mm-dd')) +1)/count(*) "
					+ " from lbmission c,lbmission d where c.missionprop1=d.missionprop1  "
					+ " and c.activityid='0000005075' and d.activityid='0000005055' "
					+ " and c.MissionProp20= x.code "
					+ " and c.outdate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') and c.outdate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ optSqlC
					+ " and substr(c.missionprop1,11,2)='05'),2) 结案 "
					+ " from ldcode x "
					+ " where codetype = 'station' "
					+ " and code like concat('"+ "?mManageSql?" + "','%') ";
			logger.debug("机构操作时效查询语句:" + strSQL);
			sqlbv4.sql(strSQL);
			sqlbv4.put("var1", mDay[0]);
			sqlbv4.put("var2", mDay[1]);
			sqlbv4.put("mManageSql", mManageSql);
			sqlbv4.put("mDefaultOperator", mDefaultOperator);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv4);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

//			String strSQL_com = "select a.comcode, a.name from ldcom a where length(a.comcode) <= 6 and a.comcode like '"
//					+ mManageCom + "%' order by a.comcode";
//			logger.debug("获得管理机构的语句:" + strSQL_com);
//			ExeSQL kExeSQL = new ExeSQL();
//			SSRS kSSRS = new SSRS();
//			kSSRS = kExeSQL.execSQL(strSQL_com);

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[11];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				strArr[3] = ("".equals(mDefaultOperator)) ? "全部"
						: mDefaultOperator; // 核赔人
				strArr[4] = tSSRS.GetText(i, 3); // 立案
				strArr[5] = tSSRS.GetText(i, 4); // 审核
				strArr[6] = tSSRS.GetText(i, 5); // 简易案件
				strArr[7] = tSSRS.GetText(i, 8); // 预付管理
				strArr[8] = tSSRS.GetText(i, 6); // 调查
				strArr[9] = tSSRS.GetText(i, 7); // 签批
				strArr[10] = tSSRS.GetText(i, 9); // 结案

				for (int j = 0; j < strArr.length; j++) {
					if (strArr[j] == null || "null".equals(strArr[j])) {
						strArr[j] = "";
					}
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		// 4理赔调查率
		if (mCode.equals("claim4")) {
			tXmlExport.createDocument("LLClaim4Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim4");

			// 定义列表标题
			String[] Title = new String[8];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";

			logger.debug("---以下 查询列表$*/claim4/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			String strSQL = "select a.mngcom, a.rgtno, "
					+ " (select distinct b.clmno from LLInqApply b where b.clmno = a.rgtno), "
					+ " (select distinct c.clmno from LLInqConclusion c where c.clmno = a.rgtno and c.masflag = '1' and c.batno != '000000') "
					+ " from llregister a where a.mngcom like concat('"+ "?mManageCom?" + "','%') "
					+ " and a.endcasedate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') and a.endcasedate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd') order by a.mngcom";
			sqlbv5.sql(strSQL);
			sqlbv5.put("mManageCom", mManageCom);
			sqlbv5.put("var1", mDay[0]);
			sqlbv5.put("var2", mDay[1]);
			
			logger.debug("理赔调查率查询语句:" + strSQL);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv5);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			String strSQL_com = "select a.comcode, a.name from ldcom a where char_length(a.comcode) <= 6 and a.comcode like concat('"+ "?mManageCom?" + "','%') order by a.comcode";
			sqlbv6.sql(strSQL_com);
			sqlbv6.put("mManageCom", mManageCom);
			logger.debug("获得管理机构的语句:" + strSQL_com);
			ExeSQL kExeSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kExeSQL.execSQL(sqlbv6);

			for (int i = 1; i <= kSSRS.MaxRow; i++) {
				strArr = new String[8];
				strArr[0] = i + ""; // 序号
				strArr[1] = kSSRS.GetText(i, 1); // 机构代码
				strArr[2] = kSSRS.GetText(i, 2); // 机构名称
				strArr[3] = "0";
				strArr[4] = "0";
				strArr[5] = "0";
				strArr[6] = "0.00%";
				strArr[7] = "0.00%";

				for (int j = 1; j <= tSSRS.MaxRow; j++) {
					if (tSSRS.GetText(j, 1).startsWith(kSSRS.GetText(i, 1))) {
						strArr[3] = String
								.valueOf(Long.parseLong(strArr[3]) + 1);// 结案件数
						if (tSSRS.GetText(j, 3) != null
								&& !tSSRS.GetText(j, 3).equals("")) {
							strArr[4] = String.valueOf(Long
									.parseLong(strArr[4]) + 1);// 调查件数
							logger.debug(strArr[4]);
						}
						if (tSSRS.GetText(j, 4) != null
								&& !tSSRS.GetText(j, 4).equals("")) {
							strArr[5] = String.valueOf(Long
									.parseLong(strArr[5]) + 1);// 调查阳性件数
						}
					}
				}

				if (Double.parseDouble(strArr[3]) > 0) {
					strArr[6] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[4])
							* 100 / Long.parseLong(strArr[3]))
							+ "%";// 调查率
				}
				if (Double.parseDouble(strArr[5]) > 0) {
					strArr[7] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[5])
							* 100 / Long.parseLong(strArr[3]))
							+ "%";// 调查阳性率
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);
		}

		// 5调查时效
		if (mCode.equals("claim5")) {
			tXmlExport.createDocument("LLClaim5Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim5");

			// 定义列表标题
			String[] Title = new String[7];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";

			logger.debug("---以下 查询列表$*/claim5/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			String strSQL = "select a.inqdept, a.clmno, a.locflag, a.condate-a.applydate+1 "
					+ " from llinqapply a where exists (select 1 from llinqconclusion b "
					+ " where a.clmno = b.clmno and a.batno = b.batno and b.finiflag = '1') "
					+ " and a.inqdept like concat('"+ "?mManageCom?" + "','%') and a.applydate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') and a.applydate <=to_date('"+ "?var2?"+ "','yyyy-mm-dd') order by a.inqdept";
			sqlbv7.sql(strSQL);
			sqlbv7.put("mManageCom", mManageCom);
			sqlbv7.put("var1", mDay[0]);
			sqlbv7.put("var2", mDay[1]);
			
			logger.debug("理赔调查时效查询语句:" + strSQL);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv7);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			String strSQL_com = "select a.comcode, a.name from ldcom a where char_length(a.comcode) <= 6 and a.comcode like concat('"+ "?mManageCom?" + "','%') order by a.comcode";
			logger.debug("获得管理机构的语句:" + strSQL_com);
			sqlbv8.sql(strSQL_com);
			sqlbv8.put("mManageCom", mManageCom);
			ExeSQL kExeSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kExeSQL.execSQL(sqlbv8);

			for (int i = 1; i <= kSSRS.MaxRow; i++) {
				strArr = new String[7];
				strArr[0] = i + ""; // 序号
				strArr[1] = kSSRS.GetText(i, 1); // 机构代码
				strArr[2] = kSSRS.GetText(i, 2); // 机构名称
				strArr[3] = "0";
				strArr[4] = "0";
				strArr[5] = "0";
				strArr[6] = "0";

				for (int j = 1; j <= tSSRS.MaxRow; j++) {
					if (tSSRS.GetText(j, 1).startsWith(kSSRS.GetText(i, 1))) {
						strArr[3] = String
								.valueOf(Long.parseLong(strArr[3]) + 1);// 调查件数
						strArr[4] = String.valueOf(Long.parseLong(strArr[4])
								+ Long.parseLong(tSSRS.GetText(j, 4)));// 调查时效数量和
						logger.debug(Long.parseLong(tSSRS.GetText(j, 4)));
						logger.debug("strArr[3]=" + strArr[3]);
						logger.debug("strArr[4]=" + strArr[4]);

						if (tSSRS.GetText(j, 3) != null
								&& tSSRS.GetText(j, 3).equals("1")) {
							strArr[5] = String.valueOf(Long
									.parseLong(strArr[5]) + 1);// 异地调查件数
							strArr[6] = String.valueOf(Long
									.parseLong(strArr[6])
									+ Long.parseLong(tSSRS.GetText(j, 4)));// 异地调查时效数量和
						}
					}
				}

				if (Long.parseLong(strArr[3]) > 0) {
					strArr[4] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[4])
							/ Double.parseDouble(strArr[3]));// 调查时效
				}
				if (Long.parseLong(strArr[5]) > 0) {
					strArr[6] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[6])
							/ Double.parseDouble(strArr[5]));// 异地调查时效
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);
		}

		// 6查勘费用统计
		if (mCode.equals("claim6")) {

			tXmlExport.createDocument("LLClaim6Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim6");

			// 定义列表标题
			String[] Title = new String[12];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			Title[10] = "";
			Title[11] = "";

			logger.debug("---以下 查询列表$*/claim6/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			String strSQL = "select code,codename, "
					+ " (select sum(feesum) from LLInqFee "
					+ " where FeeType='01' and  InqDept= a.code and FeeDate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and FeeDate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(clmno,11,2)='05') 交通费, "
					+ " (select sum(feesum) from LLInqFee "
					+ " where feetype='02' and InqDept= a.code and FeeDate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and FeeDate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(clmno,11,2)='05') 医院病历费, "
					+ " (select sum(feesum) from LLInqFee "
					+ " where FeeType='03' and  InqDept= a.code and FeeDate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and FeeDate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(clmno,11,2)='05') 取证手续费, "
					+ " (select sum(feesum) from LLInqFee "
					+ " where FeeType='04' and  InqDept= a.code and FeeDate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and FeeDate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(clmno,11,2)='05') 鉴定费, "
					+ " (select sum(feesum) from LLInqFee "
					+ " where FeeType='05' and  InqDept= a.code and FeeDate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and FeeDate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(clmno,11,2)='05') 委托调查费, "
					+ " (select sum(feesum) from LLInqFee "
					+ " where FeeType='06' and  InqDept= a.code and FeeDate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and FeeDate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(clmno,11,2)='05') 差旅费, "
					+ " (select sum(feesum) from LLInqFee "
					+ " where FeeType='07' and  InqDept= a.code and FeeDate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and FeeDate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(clmno,11,2)='05') 误餐费, "
					+ " (select sum(feesum) from LLInqFee "
					+ " where FeeType='08' and  InqDept= a.code and FeeDate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') " + " and FeeDate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(clmno,11,2)='05') 其他费用, "
					+ " (select sum(feesum) from LLInqFee "
					+ " where InqDept= a.code and FeeDate<=to_date('"+ "?var2?"+ "','yyyy-mm-dd') " + " and FeeDate>=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and substr(clmno,11,2)='05') 总计 " + " from ldcode a "
					+ " where codetype = 'station' " + " and code like concat('"+ "?mManageCom?" + "','%')";
			logger.debug("机构操作时效查询语句:" + strSQL);
			sqlbv9.sql(strSQL);
			sqlbv9.put("var1", mDay[0]);
			sqlbv9.put("var2", mDay[1]);
			sqlbv9.put("mManageCom", mManageCom);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv9);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

//			String strSQL_com = "select a.comcode, a.name from ldcom a where length(a.comcode) <= 6 and a.comcode like '"
//					+ mManageCom + "%' order by a.comcode";
//			logger.debug("获得管理机构的语句:" + strSQL_com);
//			ExeSQL kExeSQL = new ExeSQL();
//			SSRS kSSRS = new SSRS();
//			kSSRS = kExeSQL.execSQL(strSQL_com);

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[12];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				strArr[3] = tSSRS.GetText(i, 3); // 交通费
				strArr[4] = tSSRS.GetText(i, 4); // 医院病历费
				strArr[5] = tSSRS.GetText(i, 5); // 取证手续费
				strArr[6] = tSSRS.GetText(i, 6); // 鉴定费
				strArr[7] = tSSRS.GetText(i, 7); // 委托调查费
				strArr[8] = tSSRS.GetText(i, 8); // 差旅费
				strArr[9] = tSSRS.GetText(i, 9); // 误餐费
				strArr[10] = tSSRS.GetText(i, 10); // 其他费用
				strArr[11] = tSSRS.GetText(i, 11); // 总计

				for (int j = 0; j < strArr.length; j++) {
					if (strArr[j] == null || "null".equals(strArr[j])) {
						strArr[j] = "";
					}
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		// 7赔款和件数
		if (mCode.equals("claim7")) {
			tXmlExport.createDocument("LLClaim7Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim7");

			// 定义列表标题
			String[] Title = new String[7];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";

			logger.debug("---以下 查询列表$*/claim7/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			String strSQL = "select a.clmno, a.mngcom, a.endcaseflag, a.realpay, a.declinepay, a.GiveType, "
					+ " (select distinct b.givetype from LLClaimDetail b where b.clmno = a.clmno and b.givetype='1') givetype2 "
					+ " from LLClaim a where a.mngcom like concat('"+ "?mManageCom?" + "','%') and a.makedate >= to_date('"	+ "?var1?"+ "','yyyy-mm-dd')  and a.makedate <= to_date('"	+ "?var2?"+ "','yyyy-mm-dd') order by a.mngcom";
			logger.debug("赔款和件数查询语句:" + strSQL);
			sqlbv10.sql(strSQL);
			sqlbv10.put("mManageCom", mManageCom);
			sqlbv10.put("var1", mDay[0]);
			sqlbv10.put("var2", mDay[1]);
			
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv10);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			String strSQL_com = "select a.comcode, a.name from ldcom a where char_length(a.comcode) <= 6 and a.comcode like concat('"+ "?mManageCom?" + "','%') order by a.comcode";
			logger.debug("获得管理机构的语句:" + strSQL_com);
			sqlbv11.sql(strSQL_com);
			sqlbv11.put("mManageCom", mManageCom);
			ExeSQL kExeSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kExeSQL.execSQL(sqlbv11);

			for (int i = 1; i <= kSSRS.MaxRow; i++) {
				strArr = new String[9];
				strArr[0] = i + ""; // 序号
				strArr[1] = kSSRS.GetText(i, 1); // 机构代码
				strArr[2] = kSSRS.GetText(i, 2); // 机构名称
				strArr[3] = "0";// 受理案件
				strArr[4] = "0";// 已结案件
				strArr[5] = "0.00";// 赔付金额
				strArr[6] = "0";// 案件层
				strArr[7] = "0";// 保项层
				strArr[8] = "0.00";// 拒付金额

				for (int j = 1; j <= tSSRS.MaxRow; j++) {
					if (tSSRS.GetText(j, 2).startsWith(kSSRS.GetText(i, 1))) {
						strArr[3] = String
								.valueOf(Long.parseLong(strArr[3]) + 1);// 受理案件

						if (tSSRS.GetText(j, 3) != null
								&& tSSRS.GetText(j, 3).equals("1")) {
							strArr[4] = String.valueOf(Long
									.parseLong(strArr[4]) + 1);// 已结案件
						}

						strArr[5] = String.valueOf(Double
								.parseDouble(strArr[5])
								+ Double.parseDouble(tSSRS.GetText(j, 4)));// 赔付金额

						if (tSSRS.GetText(j, 6) != null
								&& tSSRS.GetText(j, 6).equals("1")) {
							strArr[6] = String.valueOf(Long
									.parseLong(strArr[6]) + 1);// 案件层
						}

						if (tSSRS.GetText(j, 6) != null
								&& !tSSRS.GetText(j, 6).equals("1")
								&& tSSRS.GetText(j, 7) != null
								&& tSSRS.GetText(j, 7).equals("1")) {
							strArr[7] = String.valueOf(Long
									.parseLong(strArr[7]) + 1);// 保项层
						}

						strArr[8] = String.valueOf(Double
								.parseDouble(strArr[8])
								+ Double.parseDouble(tSSRS.GetText(j, 5)));// 拒付金额
					}
				}

				strArr[5] = new DecimalFormat("0.00").format(Double
						.parseDouble(strArr[5]));
				strArr[8] = new DecimalFormat("0.00").format(Double
						.parseDouble(strArr[8]));

				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);
		}

		// 8赔款和件数（按销售渠道）
		if (mCode.equals("claim8")) {

			tXmlExport.createDocument("LLClaim8Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim8");

			// 定义列表标题
			String[] Title = new String[11];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			Title[10] = "";
			if ("86".equals(mManageCom)) {
				mManageCom = "86%";
			}
			logger.debug("---以下 查询列表$*/claim8/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			String strSQL = " select a.codename,(select count(*) from llregister where rgtno in (select clmno from llclaimpolicy where contno in  "
					+ " (select contno from lccont where salechnl=a.code))  "
					+ " and rgtclass='1' and rgtdate>=to_date('"+"?var1?"+"','yyyy-mm-dd') and rgtdate<=to_date('"+"?var2?"+"','yyyy-mm-dd') "
					+ " and MngCom like '"+ "?mManageCom?" + "') 受理案件, "
					+ " (select count(rgtno) from llregister where rgtno in (select distinct clmno from llclaimpolicy where contno in  "
					+ " (select contno from lccont where salechnl=a.code)) and clmstate in ('50','60','70') and mngcom like '"+ "?mManageCom?" + "' and RgtClass='1'  "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llregister.rgtno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llregister.rgtno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')) 已结案件, "
					+ " (select count(*) from llclaim where clmno in (select distinct clmno from llclaimpolicy where contno in  "
					+ " (select contno from lccont where salechnl=a.code) and substr(ClmNo,11,2)='05') and givetype='1' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaim.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaim.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaim.clmno) like '"+ "?mManageCom?" + "') 拒付案件案件层, "
					+ " (select sum(realpay) from llclaimdetail where polno in  "
					+ " (select polno from lcpol where contno in (select contno from lccont where salechnl=a.code)) "
					+ " and substr(ClmNo,11,2)='05' and givetype='0' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaimdetail.clmno) like '"+ "?mManageCom?" + "') 赔付金额, "
					+ " (select sum(standpay-realpay) from llclaimdetail where polno in  "
					+ " (select polno from lcpol where contno in (select contno from lccont where salechnl=a.code)) "
					+ " and substr(ClmNo,11,2)='05' and givetype='1' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) >=to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaimdetail.clmno) like '"+ "?mManageCom?" + "') 拒付金额, "
					+ " (select count(*) from llclaimdetail where polno in  "
					+ " (select polno from lcpol where contno in (select contno from lccont where salechnl=a.code)) "
					+ " and substr(ClmNo,11,2)='05' and givetype='1' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) <=to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaimdetail.clmno) like '"+ "?mManageCom?" + "') 拒付案件保项层 "
					+ " from ldcode a "
					+ " where a.codetype ='salechnl' and a.code in ('01','02','03','07','09','10') "
					+ " union select '中介业务', "
					+ " (select count(*) from llregister where rgtno in (select clmno from llclaimpolicy where contno in  "
					+ " (select contno from lccont where salechnl in ('05','06','08','11')))  "
					+ " and rgtclass='1' and rgtdate>=to_date('"+"?var1?"+"','yyyy-mm-dd') and rgtdate<=to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and MngCom like '"+ "?mManageCom?" + "') 受理案件, "
					+ " (select count(rgtno) from llregister where rgtno in (select distinct clmno from llclaimpolicy where contno in  "
					+ " (select contno from lccont where salechnl in ('05','06','08','11'))) and clmstate in ('50','60','70') and mngcom like '"+ "?mManageCom?" + "' and RgtClass='1'  "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llregister.rgtno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llregister.rgtno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')) 已结案件, "
					+ " (select count(*) from llclaim where clmno in (select distinct clmno from llclaimpolicy where contno in  "
					+ " (select contno from lccont where salechnl in ('05','06','08','11')) and substr(ClmNo,11,2)='05') and givetype='1' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaim.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaim.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaim.clmno) like '"+ "?mManageCom?" + "') 拒付案件案件层, "
					+ " (select sum(realpay) from llclaimdetail where polno in  "
					+ " (select polno from lcpol where contno in (select contno from lccont where salechnl in ('05','06','08','11'))) "
					+ " and substr(ClmNo,11,2)='05' and givetype='0' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaimdetail.clmno) like '"+ "?mManageCom?" + "') 赔付金额, "
					+ " (select sum(standpay-realpay) from llclaimdetail where polno in  "
					+ " (select polno from lcpol where contno in (select contno from lccont where salechnl in ('05','06','08','11'))) "
					+ " and substr(ClmNo,11,2)='05' and givetype='1' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaimdetail.clmno) like '"+ "?mManageCom?" + "') 拒付金额, "
					+ " (select count(*) from llclaimdetail where polno in  "
					+ " (select polno from lcpol where contno in (select contno from lccont where salechnl in ('05','06','08','11'))) "
					+ " and substr(ClmNo,11,2)='05' and givetype='1' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')" 
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaimdetail.clmno) like concat('"+ "?mManageCom?" + "','%')) 拒付案件保项层 " + " from dual ";
			logger.debug("机构操作时效查询语句:" + strSQL);
			sqlbv12.sql(strSQL);
			sqlbv12.put("var1", mDay[0]);
			sqlbv12.put("var2", mDay[1]);
			sqlbv12.put("mManageCom", mManageCom);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv12);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

//			String strSQL_com = "select a.comcode, a.name from ldcom a where length(a.comcode) <= 6 and a.comcode like '"
//					+ mManageCom + "%' order by a.comcode";
//			logger.debug("获得管理机构的语句:" + strSQL_com);
//			ExeSQL kExeSQL = new ExeSQL();
//			SSRS kSSRS = new SSRS();
//			kSSRS = kExeSQL.execSQL(strSQL_com);

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[7];
				strArr[0] = tSSRS.GetText(i, 1); // 销售渠道类型
				strArr[1] = tSSRS.GetText(i, 2); // 受理案件
				strArr[2] = tSSRS.GetText(i, 3); // 已结案件
				strArr[3] = tSSRS.GetText(i, 4); // 案件层
				strArr[4] = tSSRS.GetText(i, 5); // 赔付金额
				strArr[5] = tSSRS.GetText(i, 6); // 拒付金额
				strArr[6] = tSSRS.GetText(i, 7); // 保项层

				for (int j = 0; j < strArr.length; j++) {
					if (strArr[j] == null || "null".equals(strArr[j])) {
						strArr[j] = "";
					}
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}
		// 9赔款和件数（按险种分类）
		if (mCode.equals("claim9")) {

			String riskSql = "";
			if ("全部".equals(mRiskCode)) {
				riskSql = "";
			} else {
				riskSql = " where  riskcode='" + "?mRiskCode?" + "' ";
			}

			tXmlExport.createDocument("LLClaim9Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim9");

			// 定义列表标题
			String[] Title = new String[9];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";

			if ("86".equals(mManageCom)) {
				mManageCom = "86%";
			}
			logger.debug("---以下 查询列表$*/claim9/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			String strSQL = "select a.riskcode,a.riskname, "
					+ " (select count(*) from llregister where rgtno in (select clmno from llclaimpolicy where riskcode=a.riskcode)  "
					+ " and rgtclass='1' and rgtdate>=to_date('"+"?var1?"+"','yyyy-mm-dd') and rgtdate<=to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and MngCom like '"
					+ "?mManageCom?"
					+ "') 受理案件, "
					+ " (select count(*) from llregister where rgtno in  "
					+ " (select clmno from  llclaimpolicy where riskcode=a.riskcode) and rgtclass='1' and clmstate in ('50','60','70') "
					+ " and mngcom like '"
					+ "?mManageCom?"
					+ "'  "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llregister.rgtno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llregister.rgtno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')) 已结案件, "
					+ " (select count(*) from llclaim where clmno in (select clmno from llclaimpolicy where riskcode=a.riskcode "
					+ " and substr(ClmNo,11,2)='05') and givetype='1' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaim.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaim.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaim.clmno) like '"
					+ "?mManageCom?"
					+ "') 拒付案件案件层, "
					+ " (select sum(realpay) from llclaimdetail where polno in  "
					+ " (select polno from lcpol where riskcode=a.riskcode) "
					+ " and substr(ClmNo,11,2)='05' and givetype='0' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaimdetail.clmno) like '"
					+ "?mManageCom?"
					+ "') 赔付金额, "
					+ " (select sum(standpay-realpay) from llclaimdetail where polno in  "
					+ " (select polno from lcpol where riskcode=a.riskcode) "
					+ " and substr(ClmNo,11,2)='05' and givetype='1' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaimdetail.clmno) like '"
					+ "?mManageCom?"
					+ "') 拒付金额, "
					+ " (select count(*) from llclaimdetail where polno in  "
					+ " (select polno from lcpol where riskcode=a.riskcode) "
					+ " and substr(ClmNo,11,2)='05' and givetype='1' "
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) >= to_date('"+"?var1?"+"','yyyy-mm-dd')"
					+ " and (select ExamDate from LLClaimUWMain where ExamConclusion='0' and clmno=llclaimdetail.clmno) <= to_date('"+"?var2?"+"','yyyy-mm-dd')"
					+ " and (select mngcom from llregister where rgtno=llclaimdetail.clmno) like concat('"+ "?mManageCom?" + "','%') ) 拒付案件保项层 " + " from lmrisk a " + riskSql;
			logger.debug("机构操作时效查询语句:" + strSQL);
			sqlbv13.sql(strSQL);
			sqlbv13.put("mRiskCode", mRiskCode);
			sqlbv13.put("var1", mDay[0]);
			sqlbv13.put("var2", mDay[1]);
			sqlbv13.put("mManageCom", mManageCom);
			
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv13);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

//			String strSQL_com = "select a.comcode, a.name from ldcom a where length(a.comcode) <= 6 and a.comcode like '"
//					+ mManageCom + "%' order by a.comcode";
//			logger.debug("获得管理机构的语句:" + strSQL_com);
//			ExeSQL kExeSQL = new ExeSQL();
//			SSRS kSSRS = new SSRS();
//			kSSRS = kExeSQL.execSQL(strSQL_com);

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[9];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				strArr[3] = tSSRS.GetText(i, 3); // 受理案件
				strArr[4] = tSSRS.GetText(i, 4); // 已结案件
				strArr[5] = tSSRS.GetText(i, 5); // 赔付金额
				strArr[6] = tSSRS.GetText(i, 6); // 保护层
				strArr[7] = tSSRS.GetText(i, 7); // 案件层
				strArr[8] = tSSRS.GetText(i, 8); // 拒付金额

				for (int j = 0; j < strArr.length; j++) {
					if (strArr[j] == null || "null".equals(strArr[j])) {
						strArr[j] = "";
					}
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		// 10结案率
		if (mCode.equals("claim10")) {
			tXmlExport.createDocument("LLClaim10Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim10");

			// 定义列表标题
			String[] Title = new String[10];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";

			logger.debug("---以下 查询列表$*/claim10/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			String strSQL = "select a.mngcom, a.endcasedate - a.rgtdate + 1 "
					+ " from LLRegister a where a.mngcom like concat('"+ "?mManageCom?" + "','%') " 
					+ " and a.rgtdate >= to_date('" +"?var1?"+ "','yyyy-mm-dd') and a.rgtdate <= to_date('" +"?var2?"+ "','yyyy-mm-dd') order by a.mngcom";
			sqlbv14.sql(strSQL);
			sqlbv14.put("mManageCom", mManageCom);
			sqlbv14.put("var1", mDay[0]);
			sqlbv14.put("var2", mDay[1]);
			logger.debug("结案率查询语句:" + strSQL);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv14);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			String strSQL_com = "select a.comcode, a.name from ldcom a where char_length(a.comcode) <= 6 and a.comcode like concat('"+ "?mManageCom?" + "','%') order by a.comcode";
			logger.debug("获得管理机构的语句:" + strSQL_com);
			sqlbv15.sql(strSQL_com);
			sqlbv15.put("mManageCom", mManageCom);
			ExeSQL kExeSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kExeSQL.execSQL(sqlbv15);

			for (int i = 1; i <= kSSRS.MaxRow; i++) {
				strArr = new String[10];
				strArr[0] = i + ""; // 序号
				strArr[1] = kSSRS.GetText(i, 1); // 机构代码
				strArr[2] = kSSRS.GetText(i, 2); // 机构名称
				strArr[3] = "0";// 受理案件
				strArr[4] = "0";// 5日结案件数
				strArr[5] = "0.00%";// 5日结案率
				strArr[6] = "0";// 10日结案件数
				strArr[7] = "0.00%";// 10日结案率
				strArr[8] = "0";// 30日结案件数
				strArr[9] = "0.00%";// 30日结案率

				for (int j = 1; j <= tSSRS.MaxRow; j++) {
					if (tSSRS.GetText(j, 1).startsWith(kSSRS.GetText(i, 1))) {
						strArr[3] = String
								.valueOf(Long.parseLong(strArr[3]) + 1);// 受理案件

						logger.debug(tSSRS.GetText(j, 2));
						if (tSSRS.GetText(j, 2) != null
								&& !tSSRS.GetText(j, 2).equals("")
								&& !tSSRS.GetText(j, 2).equals("null")
								&& Long.parseLong(tSSRS.GetText(j, 2)) <= 5) {
							strArr[4] = String.valueOf(Long
									.parseLong(strArr[4]) + 1);// 5日结案件数
						}
						if (tSSRS.GetText(j, 2) != null
								&& !tSSRS.GetText(j, 2).equals("")
								&& !tSSRS.GetText(j, 2).equals("null")
								&& Long.parseLong(tSSRS.GetText(j, 2)) <= 10) {
							strArr[6] = String.valueOf(Long
									.parseLong(strArr[6]) + 1);// 10日结案件数
						}
						if (tSSRS.GetText(j, 2) != null
								&& !tSSRS.GetText(j, 2).equals("")
								&& !tSSRS.GetText(j, 2).equals("null")
								&& Long.parseLong(tSSRS.GetText(j, 2)) <= 30) {
							strArr[8] = String.valueOf(Long
									.parseLong(strArr[8]) + 1);// 30日结案件数
						}
					}
				}

				if (Long.parseLong(strArr[4]) > 0) {
					strArr[5] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[4])
							* 100 / Double.parseDouble(strArr[3]))
							+ "%";// 5日结案率
				}
				if (Long.parseLong(strArr[6]) > 0) {
					strArr[7] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[6])
							* 100 / Double.parseDouble(strArr[3]))
							+ "%";// 10日结案率
				}
				if (Long.parseLong(strArr[8]) > 0) {
					strArr[9] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[8])
							* 100 / Double.parseDouble(strArr[3]))
							+ "%";// 30日结案率
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);
		}

		// 11短期出险统计
		if (mCode.equals("claim11")) {
			tXmlExport.createDocument("LLClaim11Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim11");

			// 定义列表标题
			String[] Title = new String[8];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";

			logger.debug("---以下 查询列表$*/claim11/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			String strSQL = "select a.mngcom, a.casepaytype, (select b.givetype from llclaim b where b.clmno = a.rgtno) "
					+ " from LLRegister a where a.mngcom like concat('"+ "?mManageCom?" + "','%')  "
					+ " and a.endcasedate >=to_date('"+"?var1?"+"','yyyy-mm-dd') and a.endcasedate <= to_date('"+"?var2?"+"','yyyy-mm-dd') order by a.mngcom";
			sqlbv16.sql(strSQL);
			sqlbv16.put("mManageCom", mManageCom);
			sqlbv16.put("var1", mDay[0]);
			sqlbv16.put("var2", mDay[1]);
			logger.debug("短期出险统计查询语句:" + strSQL);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv16);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			String strSQL_com = "select a.comcode, a.name from ldcom a where char_length(a.comcode) <= 6 and a.comcode like concat('"+ "?mManageCom?" + "','%') order by a.comcode";
			sqlbv17.sql(strSQL_com);
			sqlbv17.put("mManageCom", mManageCom);
			logger.debug("获得管理机构的语句:" + strSQL_com);
			ExeSQL kExeSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kExeSQL.execSQL(sqlbv17);

			for (int i = 1; i <= kSSRS.MaxRow; i++) {
				strArr = new String[8];
				strArr[0] = i + ""; // 序号
				strArr[1] = kSSRS.GetText(i, 1); // 机构代码
				strArr[2] = kSSRS.GetText(i, 2); // 机构名称
				strArr[3] = "0";// 已结案件
				strArr[4] = "0";// 短期出险件数
				strArr[5] = "0.00%";// 短期出险率
				strArr[6] = "0";// 短期出险拒付件数
				strArr[7] = "0.00%";// 短期出险拒付率

				for (int j = 1; j <= tSSRS.MaxRow; j++) {
					if (tSSRS.GetText(j, 1).startsWith(kSSRS.GetText(i, 1))) {
						strArr[3] = String
								.valueOf(Long.parseLong(strArr[3]) + 1);// 已结案件

						logger.debug(tSSRS.GetText(j, 2));
						if (tSSRS.GetText(j, 2) != null
								&& tSSRS.GetText(j, 2).equals("1")) {// 1-短期给付件
							strArr[4] = String.valueOf(Long
									.parseLong(strArr[4]) + 1);// 短期出险件数
						}

						if (tSSRS.GetText(j, 2) != null
								&& tSSRS.GetText(j, 2).equals("1")
								&& tSSRS.GetText(j, 3) != null
								&& tSSRS.GetText(j, 3).equals("1")) {// 1-拒付
							strArr[6] = String.valueOf(Long
									.parseLong(strArr[6]) + 1);// 短期出险拒付件数
						}
					}
				}

				if (Long.parseLong(strArr[4]) > 0) {
					strArr[5] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[4])
							* 100 / Double.parseDouble(strArr[3]))
							+ "%";// 5日结案率
				}
				if (Long.parseLong(strArr[6]) > 0) {
					strArr[7] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[6])
							* 100 / Double.parseDouble(strArr[4]))
							+ "%";// 10日结案率
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);
		}

		// 12退回率
		if (mCode.equals("claim12")) {
			tXmlExport.createDocument("LLClaim12Report.vts", "printer");
		}

		// 13撤件率
		if (mCode.equals("claim13")) {

			tXmlExport.createDocument("LLClaim13Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim13");

			// 定义列表标题
			String[] Title = new String[11];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			Title[10] = "";

			logger.debug("---以下 查询列表$*/claim13/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			String strSQL = " select code,codename,(select count(*) from llregister where mngcom = a.code and RgtClass = '1' "
					+ " and RgtDate >=to_date('"+ "?var1?"+ "','yyyy-mm-dd') and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd')) 受理案件数, "
					+ " (select count(*) from llregister where mngcom = a.code and clmstate = '70' and RgtClass = '1' "
					+ " and RgtDate >=to_date('"+ "?var1?"+ "','yyyy-mm-dd') and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd') and exists "
					+ " (select 1 from LLClaimUWMain where AuditConclusion = '2' and clmno=llregister.rgtno)) 客户撤案, "
					+ " (select count(*) from llregister where mngcom = a.code and clmstate = '70' and RgtClass = '1' "
					+ " and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and exists (select 1 from LLClaimUWMain where AuditConclusion = '3' and clmno=llregister.rgtno)) 公司撤案, "
					+ " (select count(*) from llregister where mngcom = a.code and clmstate = '70' and RgtClass = '1' "
					+ " and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') and RgtDate <=to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and exists (select 1 from LLClaimUWMain where AuditConclusion in ('2','3') and clmno=llregister.rgtno)) 合计, "
					+ " concat((case (select count(*) from llregister where mngcom = a.code and clmstate = '70' and RgtClass = '1' "
					+ " and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd')) "
					+ " when 0 then 0 else ((select count(*) from llregister where mngcom = a.code "
					+ " and clmstate = '70' and RgtClass = '1' and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and exists (select 1 from LLClaimUWMain where AuditConclusion = '2' and clmno=llregister.rgtno)) / "
					+ " (select count(*) from llregister where mngcom = a.code and RgtClass = '1' and RgtDate >=to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ " and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd'))) end) * 100 , '%') 客户撤案率, "
					+ " concat((case (select count(*) from llregister where mngcom = a.code and clmstate = '70' and RgtClass = '1' "
					+ " and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd')) "
					+ " when 0 then 0 else ((select count(*) from llregister where mngcom = a.code and clmstate = '70' "
					+ "  and RgtClass = '1' and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ " and exists (select 1 from LLClaimUWMain where AuditConclusion = '3' and clmno=llregister.rgtno)) / "
					+ " (select count(*) from llregister where mngcom = a.code and RgtClass = '1' "
					+ "  and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd')  and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd'))) end) * 100 , '%') 公司撤案率, "
					+ " concat(((case (select count(*) from llregister where mngcom = a.code and clmstate = '70' "
					+ " and RgtClass = '1' and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') and RgtDate <=to_date('"+ "?var2?"+ "','yyyy-mm-dd')) "
					+ " when 0 then 0 else ((select count(*)  from llregister  where mngcom = a.code "
					+ "  and clmstate = '70' and RgtClass = '1' and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') "
					+ "  and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd')  and exists "
					+ "  (select 1 from LLClaimUWMain where AuditConclusion = '2' and clmno=llregister.rgtno)) / "
					+ " (select count(*)  from llregister where mngcom = a.code and RgtClass = '1' "
					+ " and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd'))) "
					+ "  end) + (case (select count(*) from llregister where mngcom = a.code and clmstate = '70' "
					+ "  and RgtClass = '1' and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd') and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd')) "
					+ " when 0 then 0 else ((select count(*)  from llregister where mngcom = a.code "
					+ "  and clmstate = '70' and RgtClass = '1' and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd')  and RgtDate <= to_date('"+ "?var2?"+ "','yyyy-mm-dd') "
					+ "   and exists  (select 1 from LLClaimUWMain where AuditConclusion = '3' and clmno=llregister.rgtno)) / "
					+ "  (select count(*) from llregister where mngcom = a.code and RgtClass = '1' and RgtDate >= to_date('"+ "?var1?"+ "','yyyy-mm-dd')  "
					+ "  and RgtDate <=to_date('"+ "?var2?"+ "','yyyy-mm-dd'))) end)) * 100 , '%') 撤案率合计 from ldcode a "
					+ "  where a.codetype = 'station' and a.code like concat('"+ "?mManageCom?" + "','%')  ";
			logger.debug("机构操作时效查询语句:" + strSQL);
			sqlbv18.sql(strSQL);
			sqlbv18.put("var1", mDay[0]);
			sqlbv18.put("var2", mDay[1]);
			sqlbv18.put("mManageCom", mManageCom);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv18);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

//			String strSQL_com = "select a.comcode, a.name from ldcom a where length(a.comcode) <= 6 and a.comcode like '"
//					+ mManageCom + "%' order by a.comcode";
//			logger.debug("获得管理机构的语句:" + strSQL_com);
//			ExeSQL kExeSQL = new ExeSQL();
//			SSRS kSSRS = new SSRS();
//			kSSRS = kExeSQL.execSQL(strSQL_com);

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[11];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				strArr[3] = tSSRS.GetText(i, 3); // 受理案件数
				strArr[4] = tSSRS.GetText(i, 4); // 立案
				strArr[5] = tSSRS.GetText(i, 5); // 客户撤件数
				strArr[6] = tSSRS.GetText(i, 6); // 公司撤件数
				strArr[7] = tSSRS.GetText(i, 7); // 合计
				strArr[8] = tSSRS.GetText(i, 8); // 客户撤件率
				strArr[9] = tSSRS.GetText(i, 9); // 公司撤件率
				strArr[10] = tSSRS.GetText(i, 10); // 合计

				for (int j = 0; j < strArr.length; j++) {
					if (strArr[j] == null || "null".equals(strArr[j])) {
						strArr[j] = "";
					}
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		// 14回退率
		if (mCode.equals("claim14")) {

			tXmlExport.createDocument("LLClaim14Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("claim14");

			// 定义列表标题
			String[] Title = new String[6];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";

			logger.debug("---以下 查询列表$*/claim14/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			String strSQL = "select code,codename, "
					+ " (select count(*) from llregister where clmstate in ('50','60','70') "
					+ " and mngcom=a.code and RgtClass='1' and EndCaseDate>=to_date('"+"?var1?"+ "','yyyy-mm-dd') and EndCaseDate<=to_date('"+"?var2?"+ "','yyyy-mm-dd')) 已结案件数, "
					+ " (select count(distinct clmno) from llcaseback where "
					+ " AffirmDate>=to_date('"+"?var1?"+ "','yyyy-mm-dd') and AffirmDate<=to_date('"+"?var2?"+ "','yyyy-mm-dd')"
					+ " and substr(ClmNo,11,2)='05') 回退案件数, "
					+ " concat((trunc2((case (select count(*) from llregister where clmstate in ('50','60','70') "
					+ " and mngcom=a.code and RgtClass='1' and EndCaseDate>=to_date('"+"?var1?"+ "','yyyy-mm-dd') and EndCaseDate<=to_date('"+"?var2?"+ "','yyyy-mm-dd')) when 0 then 0 else "
					+ " ((select count(distinct clmno) from llcaseback where "
					+ " substr(ClmNo,11,2)='05' and AffirmDate>=to_date('"+"?var1?"+ "','yyyy-mm-dd') and AffirmDate<=to_date('"+"?var2?"+ "','yyyy-mm-dd'))/ "
					+ " (select count(*) from llregister where clmstate in ('50','60','70') "
					+ " and mngcom=a.code and RgtClass='1' and EndCaseDate>=to_date('"+"?var1?"+ "','yyyy-mm-dd') and EndCaseDate<=to_date('"+"?var2?"+ "','yyyy-mm-dd'))) end),4))*100,'%') 回退率 " 
					+ " from ldcode a "
					+ " where a.codetype = 'station' " 
					+ "  and a.code like concat('"+ "?mManageCom?" + "','%')  ";
			logger.debug("机构操作时效查询语句:" + strSQL);
			sqlbv19.sql(strSQL);
			sqlbv19.put("var1", mDay[0]);
			sqlbv19.put("var2", mDay[1]);
			sqlbv19.put("mManageCom", mManageCom);
			
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv19);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

//			String strSQL_com = "select a.comcode, a.name from ldcom a where length(a.comcode) <= 6 and a.comcode like '"
//					+ mManageCom + "%' order by a.comcode";
//			logger.debug("获得管理机构的语句:" + strSQL_com);
//			ExeSQL kExeSQL = new ExeSQL();
//			SSRS kSSRS = new SSRS();
//			kSSRS = kExeSQL.execSQL(strSQL_com);

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[6];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				strArr[3] = tSSRS.GetText(i, 3); // 已结案件数
				strArr[4] = tSSRS.GetText(i, 4); // 回退案件数
				strArr[5] = tSSRS.GetText(i, 5); // 回退率

				for (int j = 0; j < strArr.length; j++) {
					if (strArr[j] == null || "null".equals(strArr[j])) {
						strArr[j] = "";
					}
				}
				tlistTable.add(strArr);
			}

			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		// 1团险理赔月报表
		if (mCode.equals("GrpClaim1")) {
			tXmlExport.createDocument("LLGrpClaim1Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("GrpClaim1");

			// 定义列表标题
			String[] Title = new String[16];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			Title[10] = "";
			Title[11] = "";
			Title[12] = "";
			Title[13] = "";
			Title[14] = "";
			Title[15] = "";

			logger.debug("---以下 查询列表$*/claim1/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			String strSQL = "select a.riskcode, (select riskname from lmrisk where riskcode = a.riskcode), "
					+ " a.grpcontno, a.contno, a.clmno, a.insuredname, "
					+ " (select b.rgtdate from llregister b where b.rgtno = a.clmno) rgtdate, "
					+ " a.endcasedate, (case when a.realpay is not null then a.realpay  else 0 end), a.casepaytype,(select codename from ldcode where codetype='grpcasepaytype' and code=a.casepaytype),"
					+ " (select AuditConclusion from LLClaimUWMain c where c.clmno=a.clmno ) ,"
					+ " ((case (select AuditConclusion from LLClaimUWMain c where c.clmno=a.clmno ) when '1' then '拒付' when '2' then '客户撤案' when '3' then '公司撤案' else '给付' end)) 案件结论,"
					+ " a.cvalidate, (select b.accdate from llcase b where b.caseno=a.clmno and a.insuredno=b.customerno), a.polmngcom, "
					+ " (case (select distinct 1 from ES_DOC_MAIN where doccode=a.clmno) when 1 then '有' else '无' end) ES_DOC_MAIN "
					+ " from LLClaimPolicy a where a.grpcontno != '00000000000000000000' "
					+ " and a.mngcom like concat('"+ "?mManageCom?" + "','%') and a.endcasedate	 >=to_date('"+ "?var1?"+ "','yyyy-mm-dd')  and a.endcasedate <=to_date('"+ "?var2?"+ "','yyyy-mm-dd')";
			logger.debug("团险理赔月报表查询语句:" + strSQL);
			sqlbv20.sql(strSQL);
			sqlbv20.put("mManageCom", mManageCom);
			sqlbv20.put("var1", mDay[0]);
			sqlbv20.put("var2", mDay[1]);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv20);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			double sumPay = 0.0;

			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				strArr = new String[16];
				strArr[0] = j + ""; // 序号
				strArr[1] = tSSRS.GetText(j, 1);
				strArr[2] = tSSRS.GetText(j, 2);
				strArr[3] = tSSRS.GetText(j, 3);
				strArr[4] = tSSRS.GetText(j, 4);
				strArr[5] = tSSRS.GetText(j, 5);
				strArr[6] = tSSRS.GetText(j, 6);
				strArr[7] = tSSRS.GetText(j, 7);
				strArr[8] = tSSRS.GetText(j, 8);
				strArr[9] = tSSRS.GetText(j, 9);
				sumPay = sumPay + Double.parseDouble(tSSRS.GetText(j, 9));

				// 如果是一般给付件,且案件结论为0-通过,则案件类型显示为简易案件
				if (tSSRS.GetText(j, 10).trim().equals("0")
						&& tSSRS.GetText(j, 12).trim().equals("0")) {
					strArr[10] = "简易案件";// 案件类型
				} else {
					strArr[10] = tSSRS.GetText(j, 11);// 案件类型
				}

				strArr[11] = tSSRS.GetText(j, 13);// 给付结论
				strArr[12] = tSSRS.GetText(j, 14);
				strArr[13] = tSSRS.GetText(j, 15);
				strArr[14] = tSSRS.GetText(j, 16);
				strArr[15] = tSSRS.GetText(j, 17);
				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

			sumPay = PubFun.setPrecision(sumPay, "0.00");

			tTextTag.add("SumPay", sumPay); // 总合计金额
		}

		// 2团险理赔险种赔付统计报表
		if (mCode.equals("GrpClaim2")) {
			tXmlExport.createDocument("LLGrpClaim2Report.vts", "printer");
			// 新建一个ListTable的实例
			tlistTable.setName("GrpClaim2");
			String[] Title = new String[9];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";

			logger.debug("casetype:" + CaseType);
			logger.debug("caseRet:" + CaseResult);
			logger.debug("---以下 查询列表$*/claim2/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			String strSQL = "select a.riskcode,(select riskname from lmrisk where riskcode=a.riskcode),a.grpcontno,"
					+ " (select distinct grpname from ldgrp where customerno=b.appntno),"
					+ " a.clmno,"
					+ " realpay,b.cvalidate,"
					+ " b.managecom"
					+ " from llclaimpolicy a,lcpol b,LLClaimUWMain c"
					+ " where a.grpcontno=b.grpcontno"
					+ " and a.polno=b.polno"
					+ " and a.clmno=c.clmno"
					+ " and a.mngcom like concat('"+ "?mManageCom?" + "','%') "
					+ " and a.endcasedate>'"+ "?var1?"+ "' and a.endcasedate <'"+ "?var2?"+ "'"
					+ ReportPubFun.getWherePart("a.casepaytype", ReportPubFun.getParameterStr(CaseType, "?CaseType?"))
					+ ReportPubFun.getWherePart("c.auditconclusion", ReportPubFun.getParameterStr(CaseResult, "?CaseResult?"))
					// + " and a.casepaytype=''"//TODO
					// + " and c.auditconclusion='' "
					+ " and substr(a.clmno,11,2)='06'";
			sqlbv21.sql(strSQL);
			sqlbv21.put("mManageCom", mManageCom);
			sqlbv21.put("var1", mDay[0]);
			sqlbv21.put("var2", mDay[1]);
			sqlbv21.put("CaseType", CaseType);
			sqlbv21.put("CaseResult", CaseResult);
			logger.debug("团险理赔险种赔付统计报表:" + strSQL);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv21);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				strArr = new String[15];
				strArr[0] = j + ""; // 序号
				strArr[1] = tSSRS.GetText(j, 1);
				strArr[2] = tSSRS.GetText(j, 2);
				strArr[3] = tSSRS.GetText(j, 3);
				strArr[4] = tSSRS.GetText(j, 4);
				strArr[5] = tSSRS.GetText(j, 5);
				strArr[6] = tSSRS.GetText(j, 6);
				strArr[7] = tSSRS.GetText(j, 7);
				strArr[8] = tSSRS.GetText(j, 8);

				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		// 3团险理赔未结案件清单报表
		if (mCode.equals("GrpClaim3")) {
			tXmlExport.createDocument("LLGrpClaim3Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("GrpClaim3");

			// 定义列表标题
			String[] Title = new String[15];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			Title[10] = "";
			Title[11] = "";
			Title[12] = "";
			Title[13] = "";
			Title[14] = "";

			logger.debug("---以下 查询列表$*/claim1/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			String strSQL = "select a.clmno, a.grpcontno, a.contno, a.riskcode, "
					+ " (select riskname from lmrisk where riskcode = a.riskcode), a.insuredname, "
					+ " (select b.rgtdate from llregister b where b.rgtno = a.clmno) rgtdate, "
					+ " a.realpay, a.cvalidate, '', a.casepaytype, "
					+ "(select codename from ldcode where codetype='llclaimstate' and code=a.clmstate) clmstate, "
					+ " (case (select distinct 1 from LLInqApply where Clmno = a.clmno) when 1 then '有' else '无' end) LLInqApply, "
					+ " (case (select distinct 1 from ES_DOC_MAIN where doccode = a.clmno) when 1 then '有' else '无' end) ES_DOC_MAIN "
					+ " from LLClaimPolicy a "
					+ " where a.grpcontno != '00000000000000000000' and a.clmstate in ('10', '20', '30', '35', '40') "
					+ " and exists (select 1 from llregister where rgtno=a.clmno and now()-rgtdate >="+ StatType+ ") "
					+ " and a.mngcom like concat('"+ "?mManageCom?" + "','%') and a.MakeDate >= to_date('"	+ "?var1?" + "','yyyy-mm-dd')  and a.MakeDate <=to_date('"	+ "?var2?" + "','yyyy-mm-dd') ";
			logger.debug("团险理赔未结案件清单报表查询语句:" + strSQL);
			sqlbv22.sql(strSQL);
			sqlbv22.put("mManageCom", mManageCom);
			sqlbv22.put("var1", mDay[0]);
			sqlbv22.put("var2", mDay[1]);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv22);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				strArr = new String[15];
				strArr[0] = j + ""; // 序号
				strArr[1] = tSSRS.GetText(j, 1);
				strArr[2] = tSSRS.GetText(j, 2);
				strArr[3] = tSSRS.GetText(j, 3);
				strArr[4] = tSSRS.GetText(j, 4);
				strArr[5] = tSSRS.GetText(j, 5);
				strArr[6] = tSSRS.GetText(j, 6);
				strArr[7] = tSSRS.GetText(j, 7);
				strArr[8] = tSSRS.GetText(j, 8);
				strArr[9] = tSSRS.GetText(j, 9);
				strArr[10] = tSSRS.GetText(j, 10);
				strArr[11] = tSSRS.GetText(j, 11);
				strArr[12] = tSSRS.GetText(j, 12);
				strArr[13] = tSSRS.GetText(j, 13);
				strArr[14] = tSSRS.GetText(j, 14);
				tlistTable.add(strArr);
			}
			tTextTag.add("StatTypeName", StatTypeName); // 统计类型
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);
		}

		// 4不予立案案件清单
		if (mCode.equals("GrpClaim4")) {

			tXmlExport.createDocument("LLGrpClaim4Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("GrpClaim4");

			// 定义列表标题
			String[] Title = new String[15];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			Title[10] = "";
			Title[11] = "";
			Title[12] = "";
			Title[13] = "";

			logger.debug("---以下 查询列表$*/claim4/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			String strSQL = "select a.rgtno,"
					+ " a.grpcontno,"
					+ " b.contno,"
					+ " c.riskcode,"
					+ " (select riskname from lmrisk where riskcode = c.riskcode),"
					+ " d.customername,"
					+ " a.rgtdate,"
					+ " b.cvalidate,"
					+ " f.accidentdate,"
					+ " (select codename from ldcode where codetype = 'llclaimtype' and code = e.reasoncode),"
					+ " a.endcasedate,"
					+ " (select codename from ldcode where codetype = 'llnorgtreason' and code = a.norgtreason),"
					+ " a.remark" + " from llregister a," + " lccont b,"
					+ " llstandpayinfo c," + " llcase d,"
					+ " llreportreason e," + " llreport f"
					+ " where a.grpcontno = b.grpcontno"
					+ " and a.rgtno = c.caseno" + " and a.rgtno = d.caseno"
					+ " and d.customerno = e.customerno"
					+ " and a.rgtno = e.rpno" + " and a.rgtno = f.rptno"
					+ " and b.insuredno = d.customerno"
					+ " and substr(a.rgtno, 11, 2) = '06'"
					+ " and a.mngcom like concat('"+ "?mManageCom?" + "','%') "
					+ " and a.endcasedate > '" + "?var1?" + "'"
					+ " and a.endcasedate < '" + "?var2?" + "'"
					+ " and a.rgtconclusion = '02'"
					+ " order by a.rgtno, c.riskcode, d.customername ";
			logger.debug("不予立案案件清单报表查询语句:" + strSQL);
			sqlbv23.sql(strSQL);
			sqlbv23.put("mManageCom", mManageCom);
			sqlbv23.put("", mDay[0]);
			sqlbv23.put("", mDay[1]);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv23);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				strArr = new String[14];
				strArr[0] = j + ""; // 序号
				strArr[1] = tSSRS.GetText(j, 1);
				strArr[2] = tSSRS.GetText(j, 2);
				strArr[3] = tSSRS.GetText(j, 3);
				strArr[4] = tSSRS.GetText(j, 4);
				strArr[5] = tSSRS.GetText(j, 5);
				strArr[6] = tSSRS.GetText(j, 6);
				strArr[7] = tSSRS.GetText(j, 7);
				strArr[8] = tSSRS.GetText(j, 8);
				strArr[9] = tSSRS.GetText(j, 9);
				strArr[10] = tSSRS.GetText(j, 10);
				strArr[11] = tSSRS.GetText(j, 11);
				strArr[12] = tSSRS.GetText(j, 12);
				strArr[13] = tSSRS.GetText(j, 13);
				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);
		}

		// 5团险理赔回退案件清单报表
		if (mCode.equals("GrpClaim5")) {

			tXmlExport.createDocument("LLGrpClaim5Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("GrpClaim5");

			// 定义列表标题
			String[] Title = new String[18];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			Title[10] = "";
			Title[11] = "";
			Title[12] = "";
			Title[13] = "";
			Title[14] = "";
			Title[15] = "";
			Title[16] = "";
			Title[17] = "";

			logger.debug("---以下 查询列表$*/claim5/ROW/COL内容，并为列表赋值--");
			String strSQL = "";
			logger.debug("团险理赔回退案件清单报表查询语句:" + strSQL);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(strSQL);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				strArr = new String[18];
				strArr[0] = j + ""; // 序号
				strArr[1] = tSSRS.GetText(j, 1);
				strArr[2] = tSSRS.GetText(j, 2);
				strArr[3] = tSSRS.GetText(j, 3);
				strArr[4] = tSSRS.GetText(j, 4);
				strArr[5] = tSSRS.GetText(j, 5);
				strArr[6] = tSSRS.GetText(j, 6);
				strArr[7] = tSSRS.GetText(j, 7);
				strArr[8] = tSSRS.GetText(j, 8);
				strArr[9] = tSSRS.GetText(j, 9);
				strArr[10] = tSSRS.GetText(j, 10);
				strArr[11] = tSSRS.GetText(j, 11);
				strArr[12] = tSSRS.GetText(j, 12);
				strArr[13] = tSSRS.GetText(j, 13);
				strArr[14] = tSSRS.GetText(j, 14);
				strArr[15] = tSSRS.GetText(j, 15);
				strArr[16] = tSSRS.GetText(j, 16);
				strArr[17] = tSSRS.GetText(j, 17);
				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		// 6团险理赔案件时效报表
		if (mCode.equals("GrpClaim6")) {

			tXmlExport.createDocument("LLGrpClaim6Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("GrpClaim6");

			// 定义列表标题
			String[] Title = new String[12];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			Title[10] = "";
			Title[11] = "";

			logger.debug("---以下 查询列表$*/claim6/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			String strSQL = " select comcode," + " name," + " (select count(*)"
					+ "  from llregister a, llclaimuwmain b"
					+ "  where a.rgtno = b.clmno" + "  and b.ExamDate > '"
					+ "?var1?" + "'" + "  and b.ExamDate < '" + "?var2?" + "'"
					+ "  and substr(a.rgtno, 11, 2) = '06'"
					+ "  and a.mngcom like concat('',concat(ldcom.comcode,'%'))"
					+ "  and a.rgtstate = '01') ,"
					+ "  (select (case when sum(b.examdate - a.rgtdate + 1) is not null then sum(b.examdate - a.rgtdate + 1)  else 0 end)"
					+ "   from llregister a, llclaimuwmain b"
					+ "  where a.rgtno = b.clmno" + "  and b.ExamDate > '"
					+ "?var1?" + "'" + "  and b.ExamDate < '" + "?var2?" + "'"
					+ "  and substr(a.rgtno, 11, 2) = '06'"
					+ "  and a.mngcom like concat('',concat(ldcom.comcode,'%'))"
					+ "  and a.rgtstate = '01') ," + "  (select count(*)"
					+ "  from llregister a, llclaimuwmain b"
					+ "  where a.rgtno = b.clmno" + "   and b.ExamDate > '"
					+ "?var1?" + "'" + "   and b.ExamDate < '" + "?var2?" + "'"
					+ "  and substr(a.rgtno, 11, 2) = '06'"
					+ "  and a.mngcom like concat('',concat(ldcom.comcode,'%'))"
					+ "  and a.rgtstate = '11') ,"
					+ "  (select (case when sum(b.examdate - a.rgtdate + 1) is not null then sum(b.examdate - a.rgtdate + 1)  else 0 end)"
					+ "  from llregister a, llclaimuwmain b"
					+ "  where a.rgtno = b.clmno" + "  and b.ExamDate > '"
					+ "?var1?" + "'" + "  and b.ExamDate < '" + "?var2?" + "'"
					+ "  and substr(a.rgtno, 11, 2) = '06'"
					+ "  and a.mngcom like concat('',concat(ldcom.comcode,'%'))"
					+ "  and a.rgtstate = '11') ," + "  (select count(*)"
					+ "   from llregister a, llclaimuwmain b"
					+ "  where a.rgtno = b.clmno" + "  and b.ExamDate > '"
					+ "?var1?" + "'" + "  and b.ExamDate < '" + "?var2?" + "'"
					+ "  and substr(a.rgtno, 11, 2) = '06'"
					+ "  and a.mngcom like concat('',concat(ldcom.comcode,'%'))"
					+ "  and a.rgtstate = '03') ,"
					+ "  (select (case when sum(b.examdate - a.rgtdate + 1) is not null then sum(b.examdate - a.rgtdate + 1)  else 0 end)"
					+ "  from llregister a, llclaimuwmain b"
					+ "  where a.rgtno = b.clmno" + "  and b.ExamDate > '"
					+ "?var1?" + "'" + "   and b.ExamDate < '" + "?var2?" + "'"
					+ "  and substr(a.rgtno, 11, 2) = '06'"
					+ "   and a.mngcom like concat('',concat(ldcom.comcode,'%'))"
					+ "  and a.rgtstate = '03') ," + "  '0' ," + "  '0' ,"
					+ " (select (case when sum(b.examdate - a.rgtdate + 1) is not null then sum(b.examdate - a.rgtdate + 1)  else 0 end)"
					+ "   from llregister a, llclaimuwmain b"
					+ "  where a.rgtno = b.clmno" + "  and b.ExamDate > '"
					+ "?var1?" + "'" + "   and b.ExamDate < '" +"?var2?" + "'"
					+ "   and substr(a.rgtno, 11, 2) = '06'"
					+ "   and a.mngcom like concat('',concat(ldcom.comcode,'%'))"
					+ "   and a.rgtstate in ('03', '11', '01')) "
					+ "  from ldcom"
					+ "  where char_length(comcode) = '6' and comcode like concat('"+ "?mManageCom?" + "','%') " 
					+ "  group by comcode, name";
			logger.debug("团险理赔案件时效报表查询语句:" + strSQL);
			sqlbv24.sql(strSQL);
			sqlbv24.put("var1", mDay[0]);
			sqlbv24.put("var2", mDay[1]);
			sqlbv24.put("mManageCom", mManageCom);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv24);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				strArr = new String[12];
				strArr[0] = j + ""; // 序号
				strArr[1] = tSSRS.GetText(j, 1);
				strArr[2] = tSSRS.GetText(j, 2);
				strArr[3] = tSSRS.GetText(j, 3);
				strArr[4] = tSSRS.GetText(j, 4);
				strArr[5] = tSSRS.GetText(j, 5);
				strArr[6] = tSSRS.GetText(j, 6);
				strArr[7] = tSSRS.GetText(j, 7);
				strArr[8] = tSSRS.GetText(j, 8);
				strArr[9] = tSSRS.GetText(j, 9);
				strArr[10] = tSSRS.GetText(j, 10);
				strArr[11] = tSSRS.GetText(j, 11);

				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		// 7查勘费用统计
		if (mCode.equals("GrpClaim7")) {

			tXmlExport.createDocument("LLGrpClaim7Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("GrpClaim7");

			// 定义列表标题
			String[] Title = new String[12];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";
			Title[10] = "";
			Title[11] = "";

			logger.debug("---以下 查询列表$*/claim7/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			String strSQL = "select code," + " codename,"
					+ "  (select (case when sum(feesum) is not null then sum(feesum)  else 0 end)" + "   from LLInqFee"
					+ "   where FeeType = '01'" + "    and InqDept = a.code"
					+ "   and FeeDate <=to_date('" + "?var2?" + "','yyyy-mm-dd')"
					+ "  and FeeDate >= to_date('" + "?var1?" + "','yyyy-mm-dd')"
					+ "  and substr(clmno, 11, 2) = '06'),"
					+ "  (select (case when sum(feesum) is not null then sum(feesum)  else 0 end)" + "  from LLInqFee"
					+ "  where feetype = '02'" + "   and InqDept = a.code"
					+ "   and FeeDate <= to_date('" + "?var2?" + "','yyyy-mm-dd')"
					+ "  and FeeDate >= to_date('" + "?var1?" + "','yyyy-mm-dd')"
					+ "  and substr(clmno, 11, 2) = '06'),"
					+ "  (select (case when sum(feesum) is not null then sum(feesum)  else 0 end)" + "  from LLInqFee"
					+ "  where FeeType = '03'" + "  and InqDept = a.code"
					+ "   and FeeDate <=to_date('" + "?var2?" + "','yyyy-mm-dd')"
					+ "  and FeeDate >= to_date('" + "?var1?" + "','yyyy-mm-dd')"
					+ "   and substr(clmno, 11, 2) = '06'),"
					+ " (select (case when sum(feesum) is not null then sum(feesum)  else 0 end)" + "  from LLInqFee"
					+ "  where FeeType = '04'" + "  and InqDept = a.code"
					+ "   and FeeDate <=to_date('" + "?var2?" + "','yyyy-mm-dd')"
					+ "  and FeeDate >= to_date('" + "?var1?" + "','yyyy-mm-dd')"
					+ "  and substr(clmno, 11, 2) = '06'),"
					+ " (select (case when sum(feesum) is not null then sum(feesum)  else 0 end)" + "  from LLInqFee"
					+ " where FeeType = '05'" + "  and InqDept = a.code"
					+ "   and FeeDate <=to_date('" + "?var2?" + "','yyyy-mm-dd')"
					+ "  and FeeDate >= to_date('" + "?var1?" + "','yyyy-mm-dd')"
					+ "  and substr(clmno, 11, 2) = '06'),"
					+ " (select (case when sum(feesum) is not null then sum(feesum)  else 0 end)" + "  from LLInqFee"
					+ "  where FeeType = '06'" + "  and InqDept = a.code"
					+ "   and FeeDate <=to_date('" + "?var2?" + "','yyyy-mm-dd')"
					+ "  and FeeDate >=to_date('" + "?var1?" + "','yyyy-mm-dd')"
					+ "  and substr(clmno, 11, 2) = '06'),"
					+ "  (select (case when sum(feesum) is not null then sum(feesum)  else 0 end)" + "  from LLInqFee"
					+ "  where FeeType = '07'" + "  and InqDept = a.code"
					+ "   and FeeDate <=to_date('" + "?var2?" + "','yyyy-mm-dd')"
					+ "  and FeeDate >= to_date('" + "?var1?" + "','yyyy-mm-dd')"
					+ "  and substr(clmno, 11, 2) = '06'),"
					+ "  (select (case when sum(feesum) is not null then sum(feesum)  else 0 end)" + "  from LLInqFee"
					+ "   where FeeType = '08'" + "  and InqDept = a.code"
					+ "   and FeeDate <=to_date('" + "?var2?" + "','yyyy-mm-dd')"
					+ "  and FeeDate >= to_date('" + "?var1?" + "','yyyy-mm-dd') "
					+ "  and substr(clmno, 11, 2) = '06'),"
					+ "  (select (case when sum(feesum) is not null then sum(feesum)  else 0 end)" + "    from LLInqFee"
					+ "  where InqDept = a.code" + "   and FeeDate <= to_date('" + "?var2?" + "','yyyy-mm-dd')" 
					+ "  and FeeDate >= to_date('" + "?var1?" + "','yyyy-mm-dd')"
					+ "  and substr(clmno, 11, 2) = '06')" + "  from ldcode a"
					+ " where codetype = 'station'" + "  and code like concat('"+ "?mManageCom?" + "','%')";
			logger.debug("查勘费用统计报表查询语句:" + strSQL);
			sqlbv25.sql(strSQL);
			sqlbv25.put("var1", mDay[0]);
			sqlbv25.put("var2", mDay[1]);
			sqlbv25.put("mManageCom", mManageCom);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv25);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				strArr = new String[12];
				strArr[0] = j + ""; // 序号
				strArr[1] = tSSRS.GetText(j, 1);
				strArr[2] = tSSRS.GetText(j, 2);
				strArr[3] = tSSRS.GetText(j, 3);
				strArr[4] = tSSRS.GetText(j, 4);
				strArr[5] = tSSRS.GetText(j, 5);
				strArr[6] = tSSRS.GetText(j, 6);
				strArr[7] = tSSRS.GetText(j, 7);
				strArr[8] = tSSRS.GetText(j, 8);
				strArr[9] = tSSRS.GetText(j, 9);
				strArr[10] = tSSRS.GetText(j, 10);
				strArr[11] = tSSRS.GetText(j, 11);

				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}
		if (mCode.equals("EverySection")) {
			tXmlExport.createDocument("LLClaimEverySection.vts",
					"printer");
			tlistTable.setName("EverySection");
			// 定义列表标题
			String[] Title = new String[7];
			Title[0] = "";// 序号
			Title[1] = "";// 机构代码
			Title[2] = "";// 机构名称
			Title[3] = "";// 案件阶段
			Title[4] = "";// 案件所处阶段总时效
			Title[5] = "";// 案件所处阶段总次数
			Title[6] = "";// 案件所处阶段平均处理时效
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			String strSQL = "select distinct(a.comcode),a.name  from ldcom a where char_length(a.comcode)=6 and a.comcode like concat('"+ "?mManageCom?" + "','%') " + "order by a.comcode";
			sqlbv26.sql(strSQL);
			sqlbv26.put("mManageCom", mManageCom);
			logger.debug("各环节时效报表查询语句:" + strSQL);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv26);
			if (tSSRS == null || tSSRS.MaxRow == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			String resultSQL = "";
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			if ("1".equals(CaseSection)) {
				resultSQL = "select mCode 机构代码,'申请' 案件阶段,sum(mTotals)案件所处阶段总时效,sum(mTimes) 案件次数,count(1) 案件件数 from ( "
						+ " select substr(rgtno, 1, 6) mCode, (select count(*) from LDCalendar where commondate >= a.applydate"
						+ " and commondate <= a.accepteddate  and workdateflag = 'Y') mTotals, rgtno ,'1' mTimes "
						+ " from llregister a  where a.applydate >= '"
						+ "?var1?"
						+ "'"
						+ " and a.accepteddate <= '"
						+ "?var2?"
						+ "'"
						+ " and substr(rgtno, 1, 6) like concat('"+ "?mManageCom?" + "','%') ) l group by mCode order by mCode ";

			} else if ("2".equals(CaseSection)) {
				resultSQL = "select mCode 机构代码,'受理' 案件阶段,sum(mTotals)案件所处阶段总时效,sum(mTimes) 案件次数,count(1) 案件件数 from ( "
						+ "select substr(rgtno,1,6) mCode,( case when (select makedate from lbmission where missionprop1 = a.rgtno and activityid = '0000005005') is null "
						+ "then  (select count(*) from LDCalendar where commondate >= a.accepteddate and commondate <= a.makedate and workdateflag = 'Y') "
						+ " else  (select count(*) from LDCalendar where commondate >= a.accepteddate and commondate <=	(select makedate from lbmission "
						+ "where missionprop1 = a.rgtno and activityid = '0000005005') and workdateflag = 'Y') end ) mTotals, "
						+ " rgtno ,'1' mTimes from llregister a  where a.accepteddate >='"
						+ "?var1?"
						+ "' and (a.makedate<='"
						+ "?var2?"
						+ "' "
						+ "or (select makedate from lbmission where missionprop1 = a.rgtno and activityid = '0000005005')<='"
						+ "?var2?"
						+ "'  )"
						+ " and  substr(rgtno,1,6) like concat('"+ "?mManageCom?" + "','%') ) l group by mCode order by mCode";
			} else if ("3".equals(CaseSection)) {
				resultSQL = "select mCode 机构代码,'立案' 案件阶段,sum(mTotals)案件所处阶段总时效,sum(mTimes) 案件次数,count(1) 案件件数 from ( "
						+ "select substr(rgtno,1,6) mCode, (case when (select makedate from lbmission where missionprop1=b.rgtno and activityid='0000005005') is null "
						+ "then (select count(*)  from LDCalendar where commondate >=b.rgtdate and commondate <= b.rgtconfdate and workdateflag = 'Y')"
						+ "else (select count(*)  from LDCalendar where commondate >= (select makedate from lbmission where missionprop1=b.rgtno and activityid='0000005005') "
						+ " and commondate <= b.rgtconfdate and workdateflag = 'Y')  end ) mTotals,"
						+ " rgtno ,(case when (select count(1) from lbmission where missionprop1=b.rgtno and activityid='0000005015') is not null then (select count(1) from lbmission where missionprop1=b.rgtno and activityid='0000005015')  else 1 end) mTimes  from llregister b   "
						+ "where (b.rgtdate >='"
						+ "?var1?"
						+ "' or  (select makedate from lbmission where missionprop1=b.rgtno and activityid='0000005005') >='"
						+ "?var1?"
						+ "' "
						+ ") and b.rgtconfdate<='"
						+ "?var2?"
						+ "' and  substr(rgtno,1,6) like concat('"+ "?mManageCom?" + "','%')) l group by mCode order by mCode";
				// + " and exists(select 1 from lbmission where
				// missionprop1=b.rgtno and activityid='0000005015')";
			} else if ("4".equals(CaseSection)) {
				resultSQL = "select mCode 机构代码,'审核' 案件阶段,sum(mTotals)案件所处阶段总时效,sum(mTimes) 案件次数,count(1) 案件件数 from ( "
						+ "select substr(rgtno,1,6) mCode,(case when (select count(1) from llinqconclusion where clmno=a.rgtno)>0 then ((select count(*)  from LDCalendar "
						+ "where commondate >= a.rgtconfdate  and commondate <= (select max(modifydate) from lbmission where missionprop1=a.rgtno and activityid "
						+ "in ('0000005035','0000005065')) and workdateflag = 'Y')-(select count(*)  from LDCalendar where commondate >= (select min(makedate) from llinqapply "
						+ "where clmno=a.rgtno )  and commondate <= (select max(modifydate) from llinqconclusion where clmno=a.rgtno) and workdateflag = 'Y'))"
						+ "else  (select count(*)  from LDCalendar where commondate >= a.rgtconfdate "
						+ " and commondate <= (select max(modifydate) from lbmission where missionprop1=a.rgtno and activityid in ('0000005035','0000005065')) and workdateflag = 'Y')"
						+ " end)  mTotals,rgtno ,(case when (select count(1) from lbmission where missionprop1=a.rgtno and activityid in ('0000005035','0000005065')) is not null then (select count(1) from lbmission where missionprop1=a.rgtno and activityid in ('0000005035','0000005065'))  else 1 end) mTimes "
						+ " from llregister a  where a.rgtconfdate >='"
						+ "?var1?"
						+ "' and (select max(modifydate) from lbmission where missionprop1=a.rgtno and activityid "
						+ "in ('0000005035','0000005065')) <='"
						+ "?var2?"
						+ "' and substr(rgtno,1,6) like concat('"+ "?mManageCom?" + "','%') ) l group by mCode order by mCode";
				// +" and exists(select 1 from lbmission where
				// missionprop1=a.rgtno and activityid in
				// ('0000005035','0000005065'))";
			} else if ("5".equals(CaseSection)) {
				resultSQL = "select mCode 机构代码,'调查' 案件阶段,sum(mTotals)案件所处阶段总时效,sum(mTimes) 案件次数,count(1) 案件件数 from ( "
						+ "select substr(rgtno,1,6) mCode,(select count(*)  from LDCalendar where commondate >= (select min(makedate) from llinqapply where clmno=a.rgtno )"
						+ "and commondate <= (select max(modifydate) from llinqconclusion where clmno=a.rgtno) and workdateflag = 'Y') mTotals,"
						+ "rgtno ,(case when (select count(1) from lbmission where missionprop1=a.rgtno and activityid='0000005125') is not null then (select count(1) from lbmission where missionprop1=a.rgtno and activityid='0000005125')  else 1 end) mTimes from llregister a  where  "
						+ "(select min(makedate) from llinqapply where clmno=a.rgtno) >='"
						+ "?var1?"
						+ "' and (select max(modifydate) from llinqconclusion where clmno=a.rgtno)<='"
						+ "?var2?"
						+ "' and substr(rgtno,1,6) like concat('"+ "?mManageCom?" + "','%') ) l group by mCode order by mCode";
				// " and exists(select 1 from lbmission where
				// missionprop1=a.rgtno and activityid='0000005165')";
			} else if ("6".equals(CaseSection)) {
				// resultSQL = "select substr(clmno,1,6) 机构代码," +
				// "(select name from ldcom where comcode=substr(a.clmno,1,6))
				// 机构名称," +
				// "'审批' 案件阶段,"+
				// "(select count(*) from LDCalendar where commondate >=
				// auditdate "+
				// " and commondate <= (select max(auditdate) from
				// llclaimuwdetail where clmno=a.clmno and length(mngcom)=4) and
				// workdateflag = 'Y') 案件所处阶段总时效, "+
				// " rgtno 案件号,nvl((select count(1) from lbmission where
				// missionprop1=a.clmno and activityid='0000005055'),1)"+
				// " from llclaimuwmain a where a.makedate >='"+mDay[0]+"' and
				// a.makedate<='"+mDay[1]+"' and substr(clmno,1,6) like
				// '"+mManageCom+"%'"+
				// " and exists(select 1 from lbmission where
				// missionprop1=a.clmno and activityid='0000005055')";
				resultSQL = "select mCode 机构代码,'分公司审批' 案件阶段,sum(mTotals)案件所处阶段总时效,sum(mTimes) 案件次数,count(1) 案件件数 from ( "
						+ "select substr(clmno, 1, 6) mCode,(select count(*)from LDCalendar where commondate >= auditdate and commondate <= (select max(examdate)	from llclaimuwmdetail "
						+ " where clmno = a.clmno and char_length(mngcom) = 4 and ExamConclusion is not null)	 and workdateflag = 'Y') mTotals,"
						+ "rgtno , (case when (select count(1) from llclaimuwmdetail where clmno = a.clmno and char_length(mngcom) = 4 and ExamConclusion is not null) is not null then (select count(1) from llclaimuwmdetail where clmno = a.clmno and char_length(mngcom) = 4 and ExamConclusion is not null)  else 1 end) mTimes "
						+ "from llclaimuwmain a where auditdate>='"
						+ "?var1?"
						+ "'  and a.modifydate<='"
						+ "?var2?"
						+ "'and ExamConclusion is not null and substr(clmno,1,6) like concat('"+ "?mManageCom?" + "','%')"
						+ " and exists(select 1 from llclaimuwmdetail where clmno=a.clmno and char_length(mngcom) = 4 and ExamConclusion is not null)  ) l group by mCode order by mCode";
			} else if ("7".equals(CaseSection)) {
				resultSQL = "select mCode 机构代码,'总公司审批' 案件阶段,sum(mTotals)案件所处阶段总时效,sum(mTimes) 案件次数,count(1) 案件件数 from ( "
						+ "select substr(clmno, 1, 6) mCode, (select count(*) from LDCalendar where commondate >=  (select max(examdate) from llclaimuwmdetail where clmno = a.clmno "
						+ " and char_length(mngcom) = 4 and ExamConclusion is not null) and commondate <= examdate and workdateflag = 'Y') mTotals, rgtno ,"
						+ "(case when (select count(1) from llclaimuwmdetail where clmno = a.clmno and char_length(mngcom) = 2 and ExamConclusion is not null) is not null then (select count(1) from llclaimuwmdetail where clmno = a.clmno and char_length(mngcom) = 2 and ExamConclusion is not null)  else 1 end) mTimes "
						+ "from llclaimuwmain a where  (select max(examdate) from llclaimuwmdetail where clmno = a.clmno and char_length(mngcom) = 4"
						+ " and ExamConclusion is not null) >= '"
						+ "?var1?"
						+ "' and a.examdate <= '"
						+ "?var2?"
						+ "' and ExamConclusion is not null and substr(clmno,1,6) like concat('"+ "?mManageCom?" + "','%') "
						+ "and exists (select 1 from llclaimuwmdetail where clmno = a.clmno and char_length(mngcom) = 2	 and ExamConclusion is not null)  ) l group by mCode order by mCode";
			}
			sqlbv27.sql(resultSQL);
			sqlbv27.put("var1", mDay[0]);
			sqlbv27.put("var2", mDay[1]);
			sqlbv27.put("mManageCom", mManageCom);
			ExeSQL totalSQL = new ExeSQL();
			SSRS totalSSRS = new SSRS();
			totalSSRS = totalSQL.execSQL(sqlbv27);
			if (totalSSRS == null || totalSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			logger.debug("resultSQL" + resultSQL);

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				// strArr = new String[8];
				// strArr[0] = i +""; // 序号
				// strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				// strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				// logger.debug("机构名称"+tSSRS.GetText(i, 2));
				// strArr[3] = "";// 案件阶段
				// strArr[4] = "0";// 案件所处阶段总时效
				// strArr[5] = "0";// 案件所处阶段总次数
				// strArr[6] = "0.00";// 案件所处阶段平均处理时效
				for (int j = 1; j <= totalSSRS.MaxRow; j++) {
					if (tSSRS.GetText(i, 1).equals(totalSSRS.GetText(j, 1))) {
						strArr = new String[8];
						strArr[0] = i + ""; // 序号
						strArr[1] = tSSRS.GetText(i, 1); // 机构代码
						strArr[2] = tSSRS.GetText(i, 2); // 机构名称
						logger.debug("机构名称" + tSSRS.GetText(i, 2));
						strArr[3] = "";// 案件阶段
						strArr[4] = "0";// 案件所处阶段总时效
						strArr[5] = "0";// 案件所处阶段总次数
						strArr[6] = "0.00";// 案件所处阶段平均处理时效
						logger.debug("totalSSRS.GetText(j, 1)"
								+ totalSSRS.GetText(j, 1));
						strArr[3] = totalSSRS.GetText(j, 2);
						strArr[4] = String.valueOf(Long.parseLong(totalSSRS
								.GetText(j, 3))
								+ Long.parseLong(strArr[4]));
						// logger.debug("totalSSRS.GetText(j,
						// 5)"+totalSSRS.GetText(j, 5));
						strArr[5] = String.valueOf(Long.parseLong(totalSSRS
								.GetText(j, 4))
								+ Long.parseLong(strArr[5]));
						// strArr[5] =
						// String.valueOf(Long.parseLong(strArr[5])+1);
						if (Long.parseLong(strArr[5]) > 0) {
							strArr[6] = new DecimalFormat("0.00").format(Double
									.parseDouble(strArr[4])
									/ Double.parseDouble(strArr[5]));
						}
						tlistTable.add(strArr);
					}

				}

				// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组

			}
			tXmlExport.addListTable(tlistTable, Title);
		}

		if (mCode.equals("SimpleClaim1")) {
			logger.debug("document:" + tXmlExport.createDocument("LLClaimSimpleCase.vts",
			"printer"));
			// 新建一个ListTable的实例
			tlistTable.setName("simpleClaim1");
			// 定义列表标题
			String[] Title = new String[8];
			Title[0] = "";// 序号
			Title[1] = "";// 机构名称
			Title[2] = "";// 机构代码
			Title[3] = "";// 简易案件实施件数
			Title[4] = "";// 简易案件件数
			Title[5] = "";// 简易案件实施占比
			Title[6] = "";// 总件数
			Title[7] = "";// 简易案件占比

			logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");

			// //找出86下的所有三级机构
			SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
			String strSQL = "select distinct(a.comcode),a.name  from ldcom a where char_length(a.comcode)=6 and a.comcode like concat('"+ "?mManageCom?" + "','%') " + "order by a.comcode";
			sqlbv34.sql(strSQL);
			sqlbv34.put("mManageCom", mManageCom);
			logger.debug("简易案件三级机构查询语句:" + strSQL);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv34);
			if (tSSRS == null || tSSRS.MaxRow == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
			// 循环86下的所有案件信息
			String strSQL1 = "select distinct a.clmno,c.rgtstate from llclaim a,llclaimuwmain b ,llregister c where a.clmno=b.clmno and a.clmno=c.rgtno and a.realpay>0 and a.realpay<=600 "
					+ "and not exists(select 1 from llinqapply where clmno=a.clmno)"
					+ "and not exists(select 1 from llcuwbatch where caseno=a.clmno)"
					+ "and not exists(select 1 from llclaimdetail where clmno=a.clmno and givetype='1')"
					+ "and a.clmstate='50'and b.examconclusion='0' and b.examdate>='"
					+ "?var1?"
					+ "' and b.examdate<='"
					+ "?var2?"
					+ "' and substr(a.clmno,1,6) like concat('"+ "?mManageCom?" + "','%')";
			sqlbv35.sql(strSQL1);
			sqlbv35.put("var1", mDay[0]);
			sqlbv35.put("vzr2", mDay[1]);
			sqlbv35.put("mManageCom", mManageCom);
			ExeSQL kSSRSSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kSSRSSQL.execSQL(sqlbv35);
			if (kSSRS == null || kSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
			// 得出总件数
			String totalSimSQL = "select distinct a.clmno from llclaim a,llclaimuwmain b  where a.clmno=b.clmno "
					+ "and a.clmstate='50'and b.examconclusion='0' and b.examdate>='"
					+ "?var1?"
					+ "' and b.examdate<='"
					+ "?var2?"
					+ "'"
					+ "and substr(a.clmno,1,6) like concat('"+ "?mManageCom?" + "','%') ";
			sqlbv36.sql(totalSimSQL);
			sqlbv36.put("var1", mDay[0]);
			sqlbv36.put("var2", mDay[1]);
			sqlbv36.put("mManageCom", mManageCom);
			ExeSQL totalSQL = new ExeSQL();
			SSRS totalSSRS = new SSRS();
			totalSSRS = totalSQL.execSQL(sqlbv36);
			if (totalSSRS == null || totalSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			// 循环管理机构
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[8];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				strArr[3] = "0";// 简易案件实施件数
				strArr[4] = "0";// 简易案件件数
				strArr[5] = "0";// 简易案件实施占比
				strArr[6] = "0";// 总件数
				strArr[7] = "0";// 简易案件占比
				long sim1 = 0;
				long sim2 = 0;
				// 循环案件信息
				for (int j = 1; j <= kSSRS.MaxRow; j++) {
					// logger.debug("管理机构------"+kSSRS.GetText(j, 1));
					// logger.debug("strArr[1]----"+strArr[1]);
					if (strArr[1].equals(kSSRS.GetText(j, 1).substring(0, 6))) {
						if ("01".equals(kSSRS.GetText(j, 2))) {
							sim1 += 1;
						}
						sim2 += 1;

					}
				}
				strArr[3] = String.valueOf(sim1);
				strArr[4] = String.valueOf(sim2);

				// 总件数
				long total = 0;
				for (int k = 1; k <= totalSSRS.getMaxRow(); k++) {
					logger.debug("管理机构------"
							+ totalSSRS.GetText(k, 1).substring(0, 6));
					if (strArr[1].equals(totalSSRS.GetText(k, 1)
							.substring(0, 6))) {
						total += 1;
					}
				}
				strArr[6] = String.valueOf(total);
				if (Long.parseLong(strArr[4]) > 0) {
					strArr[5] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[3])
							/ Double.parseDouble(strArr[4]));
				}
				if (Long.parseLong(strArr[6]) > 0) {
					strArr[7] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[3])
							/ Double.parseDouble(strArr[6]));
				}

				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}
		if (mCode.equals("DelayInterest")) {
			tXmlExport.createDocument("DelayInterest.vts", "printer");
			tlistTable.setName("DelayInterest");
			String[] Title = new String[6];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			logger.debug("---以下 查询列表$*/DelayInterest/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
			String strSQL = "select distinct(a.comcode),a.name  from ldcom a where char_length(a.comcode)=6 and a.comcode like concat('"+ "?mManageCom?" + "','%') "
			+ "order by a.comcode";
			sqlbv37.sql(strSQL);
			sqlbv37.put("mManageCom", mManageCom);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv37);
			if (tSSRS == null || tSSRS.MaxRow == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
			String strSQL1 = "select substr(clmno,1,6) 管理机构,count(*) 支付件数,sum(money) 支付金额 "
					+ " from "
					+ "( "
					+ " select a.otherno clmno, sum(a.pay) money "
					+ " from ljagetclaim a "
					+ " where feefinatype = 'YCLX'"
					+ " and exists(select 1 from llclaimuwmain b where clmno=a.otherno and b.examdate >= '"
					+ "?var1?"
					+ "' and b.examdate <= '"
					+ "?var2?"
					+ "')"
					+ " and substr(a.otherno, 1, 6) like concat('"+ "?mManageCom?" + "','%') "
					+ " group by a.otherno "
					+ " ) g"
					+ " group by substr(clmno, 1, 6)";
			sqlbv38.sql(strSQL1);
			sqlbv38.put("var1", mDay[0]);
			sqlbv38.put("var2", mDay[1]);
			sqlbv38.put("mManageCom", mManageCom);
			ExeSQL kExeSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kExeSQL.execSQL(sqlbv38);
			if (kSSRS == null || kSSRS.MaxRow == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[6];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 2); // 机构名称
				strArr[2] = tSSRS.GetText(i, 1); // 机构代码
				logger.debug("机构代码" + tSSRS.GetText(i, 1));
				strArr[3] = "0";// 支付件数
				strArr[4] = "0.00";// 支付金额
				strArr[5] = "0.00";// 件均支付金额
				for (int j = 1; j <= kSSRS.MaxRow; j++) {
					logger.debug("kSSRS.GetText(j, 1))" + kSSRS.GetText(j, 1));
					if (strArr[2].equals(kSSRS.GetText(j, 1))) {
						strArr[3] = String.valueOf(kSSRS.GetText(j, 2));
						strArr[4] = String.valueOf(kSSRS.GetText(j, 3));
						if (Long.parseLong(strArr[3]) > 0) {
							strArr[5] = new DecimalFormat("0.00").format(Double
									.parseDouble(strArr[4])
									/ Double.parseDouble(strArr[3]));
						}
						break;
					}
				}
				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);
		}

		if (mCode.equals("ThirtyDaysEnd")) {
			tXmlExport
					.createDocument("LLClaimThirtyDaysEndCase.vts", "printer");
			tlistTable.setName("ThirtyDaysEnd");

			String[] Title = new String[6];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";

			logger.debug("---以下 查询列表$*/ThirtyDaysEnd/ROW/COL内容，并为列表赋值--");
			// 找出跟统计机构（前端页面）匹配的所有三级机构
			SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
			String strSQL = "select distinct(a.comcode),a.name  from ldcom a where char_length(a.comcode)=6 and a.comcode like concat('"+ "?mManageCom?" + "','%') " + "order by a.comcode";
			sqlbv39.sql(strSQL);
			sqlbv39.put("mManageCom", mManageCom);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv39);
			if (tSSRS == null || tSSRS.MaxRow == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			// 找出30日结案件数
			SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
			String strSQL1 = "select distinct a.clmno from llclaimuwmain  a ,llregister b where a.clmno=b.rgtno "
					+ "and  a.examdate>='"
					+ "?var1?"
					+ "' and a.examdate<='"
					+ "?var2?"
					+ "' and substr(a.clmno,1,6) like concat('"+ "?mManageCom?" + "','%') "
					+ " and examconclusion='0' and b.clmstate='50' and a.examdate-b.rgtdate <= 30";
			sqlbv40.sql(strSQL1);
			sqlbv40.put("var1", mDay[0]);
			sqlbv40.put("var2", mDay[1]);
			sqlbv40.put("mManageCom", mManageCom);
			ExeSQL kSSRSSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kSSRSSQL.execSQL(sqlbv40);
			if (kSSRS == null || kSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			// 结案总件数
			SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
			String strSQL2 = "select distinct a.clmno from llclaimuwmain  a ,llregister b "
					+ "where a.clmno = b.rgtno and "
					+ " a.examdate>='"
					+ "?var1?"
					+ "' and a.examdate<='"
					+ "?var2?"
					+ "' and substr(a.clmno,1,6) like concat('"+ "?mManageCom?" + "','%') "
					+ " and examconclusion='0' and b.clmstate='50'";
			sqlbv41.sql(strSQL2);
			sqlbv41.put("var1", mDay[0]);
			sqlbv41.put("var2", mDay[1]);
			sqlbv41.put("mManageCom", mManageCom);
			ExeSQL totalSQL = new ExeSQL();
			SSRS totalSSRS = new SSRS();
			totalSSRS = totalSQL.execSQL(sqlbv41);
			if (totalSSRS == null || totalSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[6];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				strArr[3] = "0";// 30日结案件数
				strArr[4] = "0";// 结案总件数
				strArr[5] = "0.00%";// 30日结案率

				long sum1 = 0;// 30日结案件数
				long sum2 = 0;// 结案件总数

				// 得出某个三级机构下的30日结案件数
				for (int j = 1; j <= kSSRS.MaxRow; j++) {
					logger.debug("管理机构----" + strArr[1]);
					logger.debug("kSSRS.GetText(j, 1)-----------"
							+ kSSRS.GetText(j, 1));
					logger.debug("kSSRS.GetText(j, 1)-----------"
							+ kSSRS.GetText(j, 1).substring(1, 6));
					if (strArr[1].equals((kSSRS.GetText(j, 1).substring(0, 6)))) {
						sum1 += 1;
					}
				}
				logger.debug("30日总件数" + sum1);
				strArr[3] = String.valueOf(sum1);
				// 得出某个三级机构下的结案总件数
				for (int k = 1; k <= totalSSRS.MaxRow; k++) {
					logger.debug("管理机构----" + strArr[1]);
					logger.debug("kSSRS.GetText(j, 1)-----------"
							+ totalSSRS.GetText(k, 1));
					logger.debug("kSSRS.GetText(j, 1)-----------"
							+ totalSSRS.GetText(k, 1).substring(1, 6));
					if (strArr[1].equals(totalSSRS.GetText(k, 1)
							.substring(0, 6))) {
						sum2 += 1;
					}
				}
				logger.debug("总件数" + sum2);
				strArr[4] = String.valueOf(sum2);

				if (Long.parseLong(strArr[4]) > 0) {
					strArr[5] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[3])
							* 100 / Double.parseDouble(strArr[4]))
							+ "%";
				}

				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		if (mCode.equals("TwoDaysEnd")) {
			tXmlExport.createDocument("LLClaimTwoDaysEndCase.vts", "printer");
			tlistTable.setName("TwoDaysEnd");

			String[] Title = new String[6];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			logger.debug("---以下 查询列表$*/claim8/ROW/COL内容，并为列表赋值--");
			// 找出跟统计机构（前端页面）匹配的所有三级机构
			SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
			String strSQL = "select distinct(a.comcode),a.name  from ldcom a where char_length(a.comcode)=6 and a.comcode like concat('"+ "?mManageCom?" + "','%') " 
			+ "order by a.comcode";
			logger.debug("简易案件三级机构查询语句:" + strSQL);
			sqlbv42.sql(strSQL);
			sqlbv42.put("mManageCom", mManageCom);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv42);
			if (tSSRS == null || tSSRS.MaxRow == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			// 交接完成件数语句
			SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
			String strSQL1 = "select distinct a.missionprop1 案件号,b.Rgtdate 立案号产生日期,b.Accepteddate 交接日期"
					+ " from lbmission a, llregister b "
					+ "where a.missionprop1 = b.rgtno and a.activityid = '0000005015' and a.missionprop1 = b.rgtno and substr(a.missionprop20,1,6) like concat('"+ "?mManageCom?" + "','%')  and "
					+ " b.rgtconfdate  is not null and b.makedate is not null and b.rgtconfdate  >= '"
					+ "?var1?"
					+ "' and b.rgtconfdate  <= '"
					+ "?var2?"
					+ "' and b.rgtclass = '1'"
					+ " Union select Distinct Substr(c.Missionprop1, 1, 6) 案件号,d.rgtdate 立案号产生日期, d.Accepteddate 交接日期 "
					+ " from lwmission c,Llregister d "
					+ " where c.Missionprop1 = d.Rgtno and c.activityid='0000005015' and  Substr(c.Missionprop20, 1, 6) Like concat('"+ "?mManageCom?" + "','%') and "
					+ " d.Rgtconfdate Is Not Null And c.MissionProp2='25' And d.Rgtconfdate >= '"
					+ "?var1?"
					+ "' And d.Rgtconfdate <= '"
					+ "?var2?"
					+ "' And d.Rgtclass = '1'";
			sqlbv43.sql(strSQL1);
			sqlbv43.put("mManageCom", mManageCom);
			sqlbv43.put("var1", mDay[0]);
			sqlbv43.put("var2", mDay[1]);
			ExeSQL kSSRSSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kSSRSSQL.execSQL(sqlbv43);
			if (kSSRS == null || kSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			// 循环管理机构
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[6];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				strArr[3] = "0";// 2个工作日交接完成件数
				strArr[4] = "0";// 交接完成总件数
				strArr[5] = "0.00%";// 2个工作日理赔交接完成率
				long sum = 0;// 总件数
				long sum1 = 0;// 两个工作日件数
				// 循环交接完成结果集
				for (int j = 1; j <= kSSRS.MaxRow; j++) {
					logger.debug("kSSRS.GetText(j, 1)-------"
							+ kSSRS.GetText(j, 1));
					logger.debug("kSSRS.GetText(j, 3)-------"
							+ kSSRS.GetText(j, 3));
					logger.debug("kSSRS.GetText(j, 2)-------"
							+ kSSRS.GetText(j, 2));

					if (strArr[1].equals(kSSRS.GetText(j, 1).substring(0, 6))) {
						// logger.debug("Long.parseLong(strArr[4])+1---"+Long.parseLong(strArr[4])+1);
						sum += 1;
						// 2个工作日完成件数语句
						SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
						String strSQL2 = "select count(*) from LDCalendar"
								+ " where commondate >= '"
								+ "?var1?" + "' and commondate <= '"
								+ "?var2?"
								+ "' and workdateflag = 'Y'";
						sqlbv44.sql(strSQL2);
						sqlbv44.put("var1", kSSRS.GetText(j, 3));
						sqlbv44.put("var2", kSSRS.GetText(j, 2));
						ExeSQL totalSQL = new ExeSQL();
						SSRS totalSSRS = new SSRS();
						totalSSRS = totalSQL.execSQL(sqlbv44);

						if (totalSSRS == null || totalSSRS.MaxRow <= 0) {
							buildError("dealdata", "没有要统计的数据!");
							return false;
						} else if (Integer.parseInt(totalSSRS.GetText(1, 1)) >= 0
								&& Integer.parseInt(totalSSRS.GetText(1, 1)) <= 2) {
							sum1 += 1;
						}

					}
					strArr[3] = String.valueOf(sum1);
					strArr[4] = String.valueOf(sum);

				}

				if (Long.parseLong(strArr[4]) > 0) {
					strArr[5] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[3])
							* 100 / Double.parseDouble(strArr[4]))
							+ "%";
				}

				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}
		if (mCode.equals("FiveDaysEnd")) {
			tXmlExport.createDocument("LLClaimFiveDaysEndCase.vts", "printer");
			tlistTable.setName("FiveDaysEnd");

			String[] Title = new String[6];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			logger.debug("---以下 查询列表$*/claim8/ROW/COL内容，并为列表赋值--");
			// 找出跟统计机构（前端页面）匹配的所有三级机构
			SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
			String strSQL = "select distinct(a.comcode),a.name  from ldcom a where char_length(a.comcode)=6 and a.comcode like concat('"+ "?mManageCom?" + "','%') " + "order by a.comcode";
			logger.debug("简易案件三级机构查询语句:" + strSQL);
			sqlbv45.sql(strSQL);
			sqlbv45.put("mManageCom", mManageCom);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv45);
			if (tSSRS == null || tSSRS.MaxRow == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
			String strSQL1 = "select a.clmno,b.rgtdate 立案号产生日期,a.Examdate 案件签批同意日期 from LLClaimUWMain a,llregister b"
					+ " where a.clmno=b.rgtno And a.ExamCom like concat('"+ "?mManageCom?" + "','%') And a.Examdate <= '"
					+ "?var2?"
					+ "'  And a.Examdate>= '"
					+ "?var1?"
					+ "' And b.Rgtclass = '1' And Not Exists (Select 1 From LLInqApply where clmno=a.clmno)";
			sqlbv46.sql(strSQL1);
			sqlbv46.put("mManageCom", mManageCom);
			sqlbv46.put("var2", mDay[1]);
			sqlbv46.put("var1", mDay[0]);
			ExeSQL kSSRSSQL = new ExeSQL();
			SSRS kSSRS = new SSRS();
			kSSRS = kSSRSSQL.execSQL(sqlbv46);
			if (kSSRS == null || kSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}
			//
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strArr = new String[6];
				strArr[0] = i + ""; // 序号
				strArr[1] = tSSRS.GetText(i, 1); // 机构代码
				strArr[2] = tSSRS.GetText(i, 2); // 机构名称
				strArr[3] = "0";// 5个工作日交接完成件数
				strArr[4] = "0";// 交接完成总件数
				strArr[5] = "0.00%";// 5个工作日理赔交接完成率
				// 循环交接完成结果集
				for (int j = 1; j <= kSSRS.MaxRow; j++) {
					if (strArr[1].equals(kSSRS.GetText(j, 1).substring(0, 6))) {
						strArr[4] = String
								.valueOf(Long.parseLong(strArr[4]) + 1);
						SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
						String strSQL2 = "(select count(*)"
								+ " from LDCalendar" + " where commondate >= '"
								+ "?var1?" + "' and commondate <= '"
								+ "?var2?"
								+ "' and workdateflag = 'Y')";
						sqlbv47.sql(strSQL2);
						sqlbv47.put("var1", kSSRS.GetText(j, 2));
						sqlbv47.put("var2", kSSRS.GetText(j, 3));
						ExeSQL totalSQL = new ExeSQL();
						SSRS totalSSRS = new SSRS();
						totalSSRS = totalSQL.execSQL(sqlbv47);
						if (totalSSRS == null || totalSSRS.getMaxRow() == 0) {
							buildError("dealdata", "没有要统计的数据!");
							return false;
						} else if (Integer.parseInt(totalSSRS.GetText(1, 1)) >= 0
								&& Integer.parseInt(totalSSRS.GetText(1, 1)) <= 5) {
							strArr[3] = String.valueOf((Long
									.parseLong(strArr[3]) + 1));
						}
					}
				}
				if (Long.parseLong(strArr[4]) > 0) {
					strArr[5] = new DecimalFormat("0.00").format(Double
							.parseDouble(strArr[3])
							* 100 / Double.parseDouble(strArr[4]))
							+ "%";
				}

				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		// 8核赔人统计清单
		if (mCode.equals("GrpClaim8")) {

			tXmlExport.createDocument("LLGrpClaim8Report.vts", "printer");

			// 新建一个ListTable的实例
			tlistTable.setName("GrpClaim8");

			// 定义列表标题
			String[] Title = new String[10];
			Title[0] = "";
			Title[1] = "";
			Title[2] = "";
			Title[3] = "";
			Title[4] = "";
			Title[5] = "";
			Title[6] = "";
			Title[7] = "";
			Title[8] = "";
			Title[9] = "";

			logger.debug("---以下 查询列表$*/claim8/ROW/COL内容，并为列表赋值--");
			SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
			String strSQL = "select comcode ,"
					+ " (select codename"
					+ " from ldcode"
					+ "  where codetype = 'station'"
					+ "    and code = a.comcode) ,"
					+ "  usercode ,"
					+ "  (select username from lduser b where b.usercode = a.usercode) ,"
					+ "  '',"
					+ " '',"
					+ " '',"
					+ " jobcategory ,"
					+ "  ''"
					+ "  from llgrpclaimuser a"
					+ " where jobcategory in (select distinct jobcategory from llgrpclaimpopedom)"
					+ "  and comcode like concat('"+ "?mManageCom?" + "','%') ";
			logger.debug("核赔人统计清单报表查询语句:" + strSQL);
			sqlbv48.sql(strSQL);
			sqlbv48.put("mManageCom", mManageCom);
			ExeSQL cusExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = cusExeSQL.execSQL(sqlbv48);
			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				buildError("dealdata", "没有要统计的数据!");
				return false;
			}

			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				strArr = new String[10];
				strArr[0] = j + ""; // 序号
				strArr[1] = tSSRS.GetText(j, 1);
				strArr[2] = tSSRS.GetText(j, 2);
				strArr[3] = tSSRS.GetText(j, 3);
				strArr[4] = tSSRS.GetText(j, 4);
				strArr[5] = tSSRS.GetText(j, 5);
				strArr[6] = tSSRS.GetText(j, 6);
				strArr[7] = tSSRS.GetText(j, 7);
				strArr[8] = tSSRS.GetText(j, 8);
				strArr[9] = tSSRS.GetText(j, 9);

				tlistTable.add(strArr);
			}
			// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
			tXmlExport.addListTable(tlistTable, Title);

		}

		logger.debug("----------开始为单个变量赋值-----------");
		// 给模板中的变量赋值
		tTextTag.add("MngCom", ReportPubFun.getMngName(mManageCom)); // 统计机构
		tTextTag.add("StartDate", mDay[0]); // 统计起期
		tTextTag.add("EndDate", mDay[1]); // 统计止期
		tTextTag.add("Operator", mGlobalInput.Operator); // 统计人
		tTextTag.add("CurrDate", PubFun.getCurrentDate()); // 统计日期
		logger.debug("----------结束为单个变量赋值-----------");

		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		logger.debug("********************************************");
		logger.debug("---将tXmlExport打包，返回前台--");
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	@SuppressWarnings("unused")
	private String getName(String riskType) {
		String Name = "";

		if (riskType.equals("L")) {
			Name = "人寿险";
		}

		if (riskType.equals("H")) {
			Name = "健康险";
		}

		if (riskType.equals("A")) {
			Name = "意外险";
		}

		return Name;
	}

	public static void main(String[] args) {
	}
}
