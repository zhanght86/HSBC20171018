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
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class WorkLoadBL {
	private static Logger logger = Logger.getLogger(UWCheckBL.class);
	/** 业务处理相关变量 */
	private static String timeFactor = String
			.valueOf(1.00 - ((double) 110 / 365)); // 时间因子

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String mManageCom = "";
	private String UserCode = "";
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mDefineCode = ""; // 统计输入的报表代码
	private String[] mDay = null; // 统计输入的时间项

	/** 构造函数 */
	public WorkLoadBL() {
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) { // 打印

		// 全局变量
		mDay = (String[]) cInputData.get(0);
		mManageCom = (String) cInputData.get(1);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		// test:
		logger.debug("大小：" + cInputData.size());
		logger.debug("starttime：" + mDay[0]);
		logger.debug("endtime：" + mDay[1]);
		logger.debug("操作员：" + mGlobalInput.Operator);
		logger.debug("操作机构：" + mGlobalInput.ManageCom);


		if (mGlobalInput == null) {
			CError.buildErr(this, "没有得到足够的信息！");

			return false;
		}
		// 最高核保师能查询其它任意核保师的核保统计
			UserCode = (String) cInputData.get(2); // 

		return true;
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

	/** 传输数据的公共方法 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINTGET") && !cOperate.equals("PRINTPAY")) {
			CError.buildErr(this,"不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		if (!getPrintDataPay()) {
			return false;
		}

		return true;
	}

	private boolean getPrintDataPay() {
		logger.debug("报表代码类型：" + mDefineCode);

		String xmlname = "";
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		FinDayTool tFinDayTool = new FinDayTool();
		ListTable tlistTable = new ListTable();
		String[] strArr = null;
		// 工作量统计***************************************************************************************************
			xmlexport.createDocument("WorkLoad.vts", "printer");
			ExeSQL hbExeSQL = new ExeSQL();
			String HB_sql = " select a.createoperator from lwmission a where 1=1" ;// 工作量统计
			HB_sql = HB_sql
					+ ReportPubFun.getWherePartLike("a.OperateCom",
							ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
					+ ReportPubFun.getWherePart("a.MakeDate",ReportPubFun.getParameterStr(mDay[0],"?date1?"), ReportPubFun.getParameterStr(mDay[1],"?date2?"), 1); // 开始结束日期
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(HB_sql);
			sqlbv1.put("mManageCom", mManageCom);
			sqlbv1.put("date1", mDay[0]);
			sqlbv1.put("date2", mDay[1]);
			logger.debug("--------工作量统计---－－－－" + HB_sql);
			SSRS HBSSRS = hbExeSQL.execSQL(sqlbv1);

			ExeSQL zbExeSQL = new ExeSQL();
			String ZB_sql = "select a.createoperator from lwmission a where (case when  a.priorityid > a.SQLPriorityID then a.priorityid else a.SQLPriorityID end)='1'";
			ZB_sql = ZB_sql
					+ ReportPubFun.getWherePartLike("a.OperateCom",	ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
					+ ReportPubFun.getWherePart("a.indate",ReportPubFun.getParameterStr(mDay[0],"?date1?"), ReportPubFun.getParameterStr(mDay[1],"?date2?"), 1); 
			logger.debug("-------------时效超期件统计-----------------------------"+ ZB_sql);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(ZB_sql);
			sqlbv2.put("mManageCom", mManageCom);
			sqlbv2.put("date1", mDay[0]);
			sqlbv2.put("date2", mDay[1]);
			SSRS zbSSRS = zbExeSQL.execSQL(sqlbv2);

			ExeSQL shbExeSQL = new ExeSQL();
			String ShB_sql = "select a.createoperator from lwmission a where (case when  a.priorityid > a.SQLPriorityID then a.priorityid else a.SQLPriorityID end)='2'";
			ShB_sql = ShB_sql
					+ ReportPubFun.getWherePartLike("a.OperateCom",
							ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
					+ ReportPubFun.getWherePart("a.makedate",
							ReportPubFun.getParameterStr(mDay[0],"?date1?"), ReportPubFun.getParameterStr(mDay[1],"?date2?"), 1); // 查询日期范围
			logger.debug("-------------时效紧急件统计-----------------------------"
							+ ShB_sql);
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(ShB_sql);
			sqlbv3.put("mManageCom", mManageCom);
			sqlbv3.put("date1", mDay[0]);
			sqlbv3.put("date2", mDay[1]);
			SSRS shbSSRS = shbExeSQL.execSQL(sqlbv3);

			ExeSQL specExeSQL = new ExeSQL();
			String Spec_sql = " select a.createoperator from lwmission a where (case when  a.priorityid > a.SQLPriorityID then a.priorityid else a.SQLPriorityID end)='3'";			   
				Spec_sql = Spec_sql
						+ ReportPubFun.getWherePartLike("a.OperateCom",
								ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
						+ ReportPubFun.getWherePart("a.makedate",
								ReportPubFun.getParameterStr(mDay[0],"?date1?"), ReportPubFun.getParameterStr(mDay[1],"?date2?"), 1); // 查询日期范围
			logger.debug("-------------时效加快件统计-----------------------------"
							+ Spec_sql);
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(Spec_sql);
			sqlbv4.put("mManageCom", mManageCom);
			sqlbv4.put("date1", mDay[0]);
			sqlbv4.put("date2", mDay[1]);
			SSRS specSSRS = specExeSQL.execSQL(sqlbv4);

			ExeSQL specsecondExeSQL = new ExeSQL();
			String specsecond_sql = " select a.createoperator from lwmission a where (case when  a.priorityid > a.SQLPriorityID then a.priorityid else a.SQLPriorityID end)='4'";	
			   
				specsecond_sql = specsecond_sql
						+ ReportPubFun.getWherePartLike("a.OperateCom",
								ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
						+ ReportPubFun.getWherePart("a.makedate",
								ReportPubFun.getParameterStr(mDay[0],"?date1?"), ReportPubFun.getParameterStr(mDay[1],"?date2?"), 1); // 查询日期范围
			logger.debug("-------------时效标准件统计-----------------------------"
							+ specsecond_sql);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(specsecond_sql);
			sqlbv5.put("mManageCom", mManageCom);
			sqlbv5.put("date1", mDay[0]);
			sqlbv5.put("date2", mDay[1]);
			SSRS specsecondSSRS = specsecondExeSQL.execSQL(sqlbv5);	
			tlistTable.setName("WORKLOAD");
			String strsql = "";
			String HBS_sql = " select distinct usercode from lduser where 1=1";
			if (UserCode == null) {
				UserCode = "";
				strsql = " ";
			} else if (!UserCode.equals("")) {
				strsql = " and usercode='" + "?UserCode?" + "' ";
			}

			HBS_sql = HBS_sql + strsql + " order by usercode ";

			logger.debug("获得所有用户的sql语句是:" + HBS_sql);
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(HBS_sql);
			sqlbv6.put("UserCode", UserCode);
			ExeSQL HBSExeSQL = new ExeSQL();
			SSRS HBSSSRS = new SSRS();
			HBSSSRS = HBSExeSQL.execSQL(sqlbv6);

			if (HBSSSRS.getMaxRow() == 0) {
				CError.buildErr(this, "查询的结果是0");
			}

			for (int i = 1; i <= HBSSSRS.MaxRow; i++) {
				double hbffjc = 0; // 工作量统计
				double zbj = 0; // 超期件统计
				double shbj = 0; // 紧急件统计
				double bzj = 0; // 加快件统计
				double cbzj = 0; // 标准件统计				

				hbffjc += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), HBSSRS,	1); // 工作量统计	
				
				zbj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), zbSSRS, 1); // 超期件统计

				shbj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), shbSSRS, 1); // 紧急件统计
				
				bzj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), specSSRS, 1); // 加快件统计

				cbzj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), specsecondSSRS, 1); // 标准件统计			
							

				strArr = new String[6];
				strArr[0] = ReportPubFun.getLdUserInfo(HBSSSRS.GetText(i, 1))
						.getUserName();
				strArr[1] = new DecimalFormat("0").format(hbffjc); // 工作量统计
				strArr[2] = new DecimalFormat("0").format(zbj); // 超期件统计
				strArr[3] = new DecimalFormat("0").format(shbj); // 紧急件统计
				strArr[4] = new DecimalFormat("0").format(bzj); // 加快件统计
				strArr[5] = new DecimalFormat("0").format(cbzj); // 标准件统计
				tlistTable.add(strArr);

			}
	    strArr = new String[6];
		strArr[0] = "HBGrp";
		strArr[1] = "hbffjc"; // 工作量统计
		strArr[2] = "zbj";  //超期件统计
		strArr[3] = "shbj"; //紧急件统计
		strArr[4] = "bzj"; //加快件统计
		strArr[5] = "cbzj"; // 标准件统计
	    xmlexport.addListTable(tlistTable, strArr);


		String CurrentDate = PubFun.getCurrentDate();
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		String sdate = mDay[0] + "-";
		String StartDate = StrTool.decodeStr(sdate, "-", 1) + "年"
		+ StrTool.decodeStr(sdate, "-", 2) + "月"
		+ StrTool.decodeStr(sdate, "-", 3) + "日";
		String edate = mDay[1] + "-";
		String EndDate = StrTool.decodeStr(edate, "-", 1) + "年"
		+ StrTool.decodeStr(edate, "-", 2) + "月"
		+ StrTool.decodeStr(edate, "-", 3) + "日";
		String cdate = CurrentDate + "-";
		String CurrentDate1 = StrTool.decodeStr(cdate, "-", 1) + "年"
		+ StrTool.decodeStr(cdate, "-", 2) + "月"
		+ StrTool.decodeStr(cdate, "-", 3) + "日";
		String a = "统计人";
		String b = "工作量";
		String c = "超期件";
		String d = "紧急件";
		String e =  "加快件"; 
		String f = "普通件";
		texttag.add("prioritya", a);
		texttag.add("priorityb", b);
		texttag.add("priorityc", c);
		texttag.add("priorityd", d);
		texttag.add("prioritye", e);
		texttag.add("priorityf", f);
		
		texttag.add("StartDate", StartDate);
		texttag.add("EndDate", EndDate);
		texttag.add("mManageCom", ReportPubFun.getMngName(mManageCom));
		texttag.add("Operator", mGlobalInput.Operator);
		texttag.add("time", CurrentDate1);
		logger.debug("大小" + texttag.size());

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

}
