package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.db.LLReportDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: 理赔清单打印：事故赔案对应清单LLPRBAccidentToCase.vts
 * </p>
 * <p>
 * Description: 统计界面：统计时间段、统计机构（某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）
 * 表头：报表名称、统计条件、统计日期 数据项：事故号、出险人、事故日期和出险日期、赔案号、赔案状态、案件类型、金额
 * 算法：在选定的机构范围内，以事故日期，出险日期或审批通过日期统计在一定的时间段内的事故及其相关赔案 排序：事故号、赔案号 表尾：统计事故和赔案数量
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author
 * @version 1.0
 */

public class LLPRBAccidentToCaseBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBAccidentToCaseBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();
	private GlobalInput mG = new GlobalInput();
	private String mStartDate = ""; // 开始日期
	private String mEndDate = ""; // 结束日期
	private String mStatiCode = ""; // 统计机构
	private String mStatiItem = ""; // 统计口径 ---以事故日期，出险日期或审批通过日期统计
	private String mNCLType = ""; // 申请类型

	public LLPRBAccidentToCaseBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 主函数------主要用于 设置数据，调试程序入口
	 * 
	 * @param:
	 * @return: 数据处理后信息
	 */
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2005-8-1");// 开始日期
		tTransferData.setNameAndValue("EndDate", "2005-8-31"); // 结束日期
		tTransferData.setNameAndValue("StatiCode", "86");// 统计机构
		tTransferData.setNameAndValue("StatiItem", "");// 统计口径

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBAccidentToCaseBL tLLPRBAccidentToCaseBL = new LLPRBAccidentToCaseBL();
		if (tLLPRBAccidentToCaseBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：事故赔案对应清单出错---------------");
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----理赔清单打印：事故赔案对应清单开始---------LLPRBAccidentToCaseBL---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验检查外部传入的数据
		if (!checkInputData()) {
			return false;
		}
		// 处理业务数据
		if (!dealData()) {
			return false;
		}
		logger.debug("-----理赔清单打印：事故赔案对应清单结束----LLPRBAccidentToCaseBL---");

		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;// 得到外部传入的数据,将数据备份到本类中

		mG = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 开始日期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 结束日期
		mStatiCode = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
		mStatiItem = (String) mTransferData.getValueByName("StatiItem"); // 统计口径
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		if (mStatiCode == null || mStatiCode.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLPRBAccidentToCaseBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取“--统计机构--”信息发生错误!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mStartDate.equals("") || mEndDate.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLPRBAccidentToCaseBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取“开始时间或结束时间”信息发生错误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLPRBAccidentToCase.vts", "");

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/PRENOEND/ROW/COL内容，并为列表赋值--");
		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("ACCCLM");
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

		// 管理机构名称
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mStatiCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tStatiCodeName = tLDComSchema.getName();

		// 查询在选定的机构范围内，以事故日期或出险日期统计在一定的时间段内的事故《事故号》
		String tQuerySQL = "";// 查询语句
		String tAccSum = "";// 事故数量，可能有一个事故对应多个赔案的情况
		String tClmSum = "";// 赔案数量
		String tStatCondition = "";// 统计条件
		String applType = ""; // 申请类型
		if (mNCLType.trim().equals("1")) // 申请类型Modify by zhaorx 2006-10-13
		{
			applType = " and Exists (Select 'X' From llreport Where llreport.rptno = a.caseno And llreport.Rgtobj = '1')";
		} else {
			applType = " and Exists (Select 'X' From llreport Where llreport.rptno = a.caseno And llreport.Rgtobj = '2')";
		}

		switch (Integer.parseInt(mStatiItem))// ----查询事故表<LLAccident>,分案事件关联<LLCaseRela>
		{
		case 0:// 1.以事故日期统计查询 符合条件的所有“事故号+赔案号”
			tQuerySQL = " select a.caserelano,a.caseno from  llcaserela a where 1=1 "// 查询语句，查事件号和陪案号
					+ " and a.caserelano in "
					+ " (select accno from llaccident where mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and accdate between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "' )"
					+ applType
					+ " order by caserelano,caseno ";
			tAccSum = " select count(distinct a.caserelano) from llcaserela a where 1=1 "// 事故数量
					+ " and a.caserelano in "
					+ " (select accno from llaccident where mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and accdate between '"
					+ "?startdate?"
					+ "' and '" + "?enddate?" + "' )" + applType;
			tClmSum = " select count(distinct a.caseno) from llcaserela a where 1=1 "// 赔案数量
					+ " and a.caserelano in "
					+ " (select accno from llaccident where mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and accdate between '"
					+ "?startdate?"
					+ "' and '" + "?enddate?" + "' )" + applType;
			tStatCondition = tStatiCodeName + "  事故日期在" + mStartDate + "至"
					+ mEndDate + "的事故及其相关赔案";
			break;
		case 1:// 2.以出险日期统计查询 符合条件的所有“事故号+赔案号” ----查询
			tQuerySQL = " select a.caserelano,a.caseno from llcaserela a where 1=1 "
					+ " and a.caseno in "
					+ " ( select rptno from llreport where mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and accidentdate between '"
					+ "?startdate?" + "' and '" + "?enddate?" + "' )" + applType;
			tAccSum = " select count(distinct a.caserelano) from llcaserela a where 1=1 "
					+ " and a.caseno in "
					+ " ( select rptno from llreport where mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and accidentdate between '"
					+ "?startdate?" + "' and '" + "?enddate?" + "' )" + applType;
			tClmSum = " select count(distinct a.caseno) from llcaserela a where 1=1 "
					+ " and a.caseno in "
					+ " ( select rptno from llreport where mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and accidentdate between '"
					+ "?startdate?" + "' and '" + "?enddate?" + "' )" + applType;
			tStatCondition = tStatiCodeName + "  出险日期在" + mStartDate + "至"
					+ mEndDate + "的事故及其相关赔案";
			break;// Modify by zhaorx 2006-10-17
		case 2:// 2.以审批通过日期统计查询 符合条件的所有“事故号+赔案号” ----查询
			tQuerySQL = " select a.caserelano,a.caseno from llcaserela a where 1=1 "
					+ " and a.caseno in "
					+ " (select clmno from llclaimuwmain where mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and examdate between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "' and examconclusion = '0' )"
					+ applType
					+ " order by caserelano,caseno ";
			tAccSum = " select count(distinct a.caserelano) from llcaserela a where 1=1 "
					+ " and a.caseno in "
					+ " (select clmno from llclaimuwmain where mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and examdate between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "' and examconclusion = '0' )" + applType;
			tClmSum = " select count(distinct a.caseno) from llcaserela a where 1=1 "
					+ " and a.caseno in "
					+ " (select clmno from llclaimuwmain where mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and examdate between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "' and examconclusion = '0' )" + applType;
			tStatCondition = tStatiCodeName + "  审批通过日期在" + mStartDate + "至"
					+ mEndDate + "的事故及其相关赔案";
			break;
		default:
			break;
		}

		logger.debug("---查询------------符合条件的所有“事故号+赔案号-------------");
		logger.debug(tQuerySQL);
		logger.debug("-------------------------------------------------------");
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tQuerySQL);
		sqlbv.put("staticode", mStatiCode);
		sqlbv.put("startdate", mStartDate);
		sqlbv.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		int tMaxRow = tSSRS.MaxRow;
		/** ******************************以下开始循取值************************** */

		for (int i = 1; i <= tMaxRow; i++) {
			logger.debug("------第" + i
					+ "次循环开始----------------------------");

			String tAccNo = tSSRS.GetText(i, 1);// 事故号
			String tClmNo = tSSRS.GetText(i, 2);// 赔案号

			// 得到事件信息---/* 事故日期*/
			LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
			tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(tClmNo);
			String tAccDate = tLLAccidentSchema.getAccDate();
			/** 事故发生日期 */

			/* 出险人----如有多个出险人 则 拼成一个 字串 */
			String tCustomer = "";
			String tCustomerNoSQL = " select customerno from llsubreport where llsubreport.subrptno in "
					+ " (select caseno from llcaserela where caserelano = '"
					+ "?accno?" + "') ";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tCustomerNoSQL);
			sqlbv1.put("accno", tAccNo);
			SSRS tSSRSNo = new SSRS();
			tSSRSNo = cusExeSQL.execSQL(sqlbv1);
			for (int j = 1; j <= tSSRSNo.getMaxRow(); j++) {
				String tCustomerNo = tSSRSNo.GetText(1, j);
				// 出险人姓名--------------------------------------------------------------
				String tSql = " select a.name from ldperson a where "
						+ " a.customerno = '" + "?customerno?" + "'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSql);
				sqlbv2.put("customerno", tCustomerNo);
				ExeSQL tExeSQL = new ExeSQL();
				String tCustomerName = tExeSQL.getOneValue(sqlbv2);
				if (j == 1) {
					tCustomer = tCustomerName;
				} else {
					tCustomer = tCustomer + " " + tCustomerName;
				}
			}

			// 出险日期
			String tAccDateSQL = " select AccidentDate from llreport where rptno in (select llcaserela.caseno "
					+ " from llcaserela where caserelano = '" + "?accno?" + "') ";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tAccDateSQL);
			sqlbv3.put("accno", tAccNo);
			ExeSQL tExeDateSQL = new ExeSQL();
			String tAccidentDate = tExeDateSQL.getOneValue(sqlbv3);
			tAccidentDate = tAccidentDate.substring(0, 10);

			/** ********赔案状态************** */
			String tClaimState = getClaimState(tClmNo);

			/** ********案件类型************** */
			LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
			tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(tClmNo);
			String tRgtStateCode = tLLRegisterSchema.getRgtState();
			String tRgtState = tLLPRTPubFunBL.getLDCode("llrgttype",
					tRgtStateCode);
			/** ********金额************** */
			LLClaimSchema tLLClaimSchema = new LLClaimSchema();
			LLClaimDB tLLClaimDB = new LLClaimDB();
			tLLClaimDB.setClmNo(tClmNo);
			tLLClaimDB.getInfo();
			tLLClaimSchema.setSchema(tLLClaimDB.getSchema());
			String tSumMoney = String.valueOf(tLLClaimSchema.getRealPay());

			// \u7D66数组赋值，然后Add到listable里
			String[] Stra = new String[10];
			Stra[0] = mStatiCode; // 机构代码
			Stra[1] = tStatiCodeName; // 机构名称
			Stra[2] = tAccNo; // 事故号
			Stra[3] = tCustomer; // 出险人
			Stra[4] = tAccDate; // 事故日期
			Stra[5] = tAccidentDate; // 出险日期
			Stra[6] = tClmNo; // 赔案号
			Stra[7] = tClaimState; // 赔案状态
			Stra[8] = tRgtState; // 案件类型
			Stra[9] = tSumMoney; // 金额
			tListTable.add(Stra);
			logger.debug("------第" + i
					+ "次循环结束----------------------------");
		}

		logger.debug("********************************************");
		logger.debug("----------以下 查询，并为 单个变量赋值-----------");
		// 统计时间-------系统时间---------------------------------------------
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 "; // 申请类型
		tStatCondition = tStatCondition + "  申请类型：" + tApplTypeName;

		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tTextTag.add("StatCondition", tStatCondition);// 统计条件：$/=StatCondition$
		tTextTag.add("StatDate", SysDate);// 统计时间：$=/StatDate$
		ExeSQL tExeAccSumSQL = new ExeSQL();// 统计事故和赔案数量
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tAccSum);
		sqlbv4.put("staticode", mStatiCode);
		sqlbv4.put("startdate", mStartDate);
		sqlbv4.put("enddate", mEndDate);
		String tAccSumNo = tExeAccSumSQL.getOneValue(sqlbv4);// 事故数量
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tClmSum);
		sqlbv5.put("staticode", mStatiCode);
		sqlbv5.put("startdate", mStartDate);
		sqlbv5.put("enddate", mEndDate);
		String tClmSumNo = tExeAccSumSQL.getOneValue(sqlbv5);// 赔案数量
		tTextTag.add("StatCount", tAccSumNo);// 事故数量：$=/StatCount$
		tTextTag.add("StatClmCount", tClmSumNo);// 赔案数量：$=/StatClmCount$

		logger.debug("********************************************");
		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath="D:/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"new.vts";
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);

		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	}

	/* 查询赔案状态，需要参数：赔案号 ，返回 赔案状态 */
	private String getClaimState(String pClmNo) {
		String ClaimState = "";// 赔案状态

		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();// 立案表
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(pClmNo);
		tLLRegisterDB.getInfo();
		tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
		if (tLLRegisterSchema.getRgtNo() != null
				&& tLLRegisterSchema.getOperator() != null) {
			String tClmState = tLLRegisterSchema.getClmState();
			ClaimState = tLLPRTPubFunBL.getLDCode("llclaimstate", tClmState);
		} else {
			LLReportSchema tLLReportSchema = new LLReportSchema();// 报案表
			LLReportDB tLLReportDB = new LLReportDB();
			tLLReportDB.setRptNo(pClmNo);
			tLLReportDB.getInfo();
			tLLReportSchema.setSchema(tLLReportDB.getSchema());
			// if(tLLReportSchema!=null)
			if (tLLReportSchema.getRptNo() != null
					&& tLLReportSchema.getOperator() != null) {
				ClaimState = "报案";
			} else {
				CError tError = new CError();
				tError.moduleName = "LLPRBAccidentToCaseBL";
				tError.functionName = "getClaimState";
				tError.errorMessage = "获取赔案信息发生错误!";
				this.mErrors.addOneError(tError);
				return null;
			}
		}
		return ClaimState;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		return true;
	}

	// 错误处理函数
	public CErrors getErrors() {
		return mErrors;
	}

	// 打包数据---用于向前台返回
	public VData getResult() {
		return mResult;
	}

	private void jbInit() throws Exception {
	}
}
