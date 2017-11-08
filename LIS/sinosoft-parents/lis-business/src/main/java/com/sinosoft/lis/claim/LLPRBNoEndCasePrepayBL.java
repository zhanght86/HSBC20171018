package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.vschema.LLCaseSet;
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
 * Title: 理赔清单打印：预付未结案清单---------LLPRBNoEndCasePrepay.vts
 * </p>
 * <p>
 * Description: 统计界面：统计时间段、统计机构（某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）
 * 数据项：赔案号、出险人、意外事故发生日期和出险日期、报案人和联系方式、案件的状态、预付金额 算法：统计在选定条件下（以立案时间统计）的预付未结案件
 * 排序：赔案号
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author modify by wanzh 2005/11/16
 * @version 1.0
 */

public class LLPRBNoEndCasePrepayBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBNoEndCasePrepayBL.class);

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
	private String mStatiCode = ""; // 机构日期
	private String mExamNoPass = ""; // 审批不通过原因
	private String mNCLType = ""; // 申请类型

	// private String mMngCom = "";
	public LLPRBNoEndCasePrepayBL() {

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
		tTransferData.setNameAndValue("StartDate", "2005-8-6");// 开始日期
		tTransferData.setNameAndValue("EndDate", "2005-8-22"); // 结束日期
		tTransferData.setNameAndValue("StatiCode", "");// 统计机构

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBNoEndCasePrepayBL tLLPRBNoEndCasePrepayBL = new LLPRBNoEndCasePrepayBL();
		if (tLLPRBNoEndCasePrepayBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：预付未结案清单出错---------------");
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----理赔清单打印：预付未结案清单开始---------LLPRBNoEndCasePrepayBL---");
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
		logger.debug("-----理赔清单打印：预付未结案清单结束----LLPRBNoEndCasePrepayBL---");

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
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLPRBNoEndCasePrepay.vts", "");

		// 统计时间---[系统时间]
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 统计条件---将 传入条件 拼成字符串
		String tStatCondition = "";//
		// 统计数量
		String tStatCount = "0";
		// 统计金额：$=/StatSum$
		String tStatSum = "0";
		double tSumBeforePay = 0;
		logger.debug("---以下 查询列表$*/PRENOEND/ROW/COL内容，并为列表赋值--");
		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("PRENOEND");
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
		// 是否每个赔案号一条记录 或 每个赔案号--每个出险人 一条记录《待定》
		// 查询 统计 在选定条件下（以立案时间统计）的预付未结案件
		// String tllregisterSQL = " select
		// a.rgtno,a.clmstate,a.rgtdate,b.beforepay,c.auditper,d.username
		// ,a.mngcom"
		// +" from llregister a,llclaim b ,llclaimuwmain c,lduser d where 1=1 "
		// +" and a.rgtno=b.clmno and b.clmno = c.clmno and c.auditper =
		// d.usercode "
		// +" and a.rgtno in(select distinct clmno from llclaimdetail where
		// PrepayFlag='1')"
		// +" and (a.rgtdate between to_date('"+mStartDate+"','yyyy-mm-dd') and
		// to_date('"+mEndDate+"','yyyy-mm-dd')) "
		// +" and a.clmstate not in('50','60','70') and a.mngcom like
		// '"+mStatiCode+"%'"
		// +" order by a.rgtno";
		String tApplType = mNCLType.trim().equals("1") ? " and a.rgtobj='1' "
				: " and a.rgtobj='2' ";
		String tllregisterSQL = " select a.rgtno,a.clmstate,a.rgtdate,b.beforepay ,a.mngcom from llregister a,llclaim b "
				+ " where 1=1"
				+ tApplType
				+ " and a.rgtno=b.clmno"
				+ " and a.rgtno in(select distinct clmno from llclaimdetail where PrepayFlag='1')"
				+ " and (a.rgtdate between '"
				+ "?startdate?"
				+ "' and '"
				+ "?enddate?"
				+ "') "
				+ " and a.clmstate not in('50','60','70') and a.mngcom like concat('"
				+ "?staticode?" + "','%')" + " order by a.rgtno";

		logger.debug("----查询语句---" + tllregisterSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tllregisterSQL);
		sqlbv.put("startdate", mStartDate);
		sqlbv.put("enddate", mEndDate);
		sqlbv.put("staticode", mStatiCode);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		int tMaxRow = tSSRS.MaxRow;
		// 统计数量
		tStatCount = String.valueOf(tMaxRow);

		for (int i = 1; i <= tMaxRow; i++) {
			logger.debug("-------------第 " + i + " 次循环开始----------------");
			String tClmNo = tSSRS.GetText(i, 1);// 赔案号
			String tClmStateCode = tSSRS.GetText(i, 2);// 案件的状态代码
			String tBeforePay = tSSRS.GetText(i, 4);// 预付金额
			String tMngComCode = tSSRS.GetText(i, 5);// 机构代码

			String userSql = " select a.auditper,b.username from llclaimuwmain a,lduser b "
					+ " where a.auditper = b.usercode and a.clmno = '"
					+ "?clmno?"
					+ "' ";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(userSql);
			sqlbv1.put("clmno", tClmNo);
			ExeSQL uExeSQL = new ExeSQL();
			SSRS uSSRS = new SSRS();
			uSSRS = uExeSQL.execSQL(sqlbv1);
			String tUserName = "";
			String tAuditPer = "";
			int tRow = uSSRS.MaxRow;
			if (tRow > 0) {
				tUserName = uSSRS.GetText(1, 1);// 审核人代码
				tAuditPer = uSSRS.GetText(1, 2);// 审核人姓名
			}

			// 根据 赔案状态代码 查询出 汉字信息
			String tClmState = tLLPRTPubFunBL.getLDCode("llclaimstate",
					tClmStateCode);

			// 出险人----如有多个出险人 则 拼成一个 字串
			String tCustomer = "";

			// 得到事件信息
			LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
			tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(tClmNo);
			String tAccDate = tLLAccidentSchema.getAccDate();
			/** 事故发生日期 */

			// 出险日期，报案人和联系方式
			LLReportSchema tLLReportSchema = new LLReportSchema();
			tLLReportSchema = tLLPRTPubFunBL.getLLReport(tClmNo);
			String tAccidentDate = tLLReportSchema.getAccidentDate();
			String tRptorSQL = " select rptorname,rptorphone from llreport where RptNo = '"
					+ "?clmno?" + "' and RgtFlag != '30' ";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tRptorSQL);
			sqlbv2.put("clmno", tClmNo);
			SSRS tRptorSSRS = new SSRS();
			tRptorSSRS = cusExeSQL.execSQL(sqlbv2);
			String tRptorName = "";
			String tRptorPhone = "";
			if (tRptorSSRS.getMaxRow() > 0) {
				tRptorName = tRptorSSRS.GetText(1, 1);
				tRptorPhone = tRptorSSRS.GetText(1, 2);
			}
			// 得到机构名称
			LDComSchema tLDComSchema = new LDComSchema();
			LDComDB tLDComDB = new LDComDB();
			tLDComDB.setComCode(tMngComCode);
			tLDComDB.getInfo();
			tLDComSchema = tLDComDB.getSchema();
			String tMngComName = tLDComSchema.getName();

			// 查询出险人--------LLCase(立案分案)
			LLCaseDB tLLCaseDB = new LLCaseDB();
			tLLCaseDB.setCaseNo(tClmNo);
			LLCaseSet tLLCaseSet = new LLCaseSet();
			tLLCaseSet.set(tLLCaseDB.query());
			for (int j = 1; j <= tLLCaseSet.size(); j++) {
				String tCaseNo = tLLCaseSet.get(j).getCaseNo();// 立案分案(赔案号)
				String tCustomerNo = tLLCaseSet.get(j).getCustomerNo();// 出险人客户号
				// 出险人姓名--------------------------------------------------------------
				String tSql = " select a.name from ldperson a where "
						+ " a.customerno = '" + "?customerno?" + "'";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tSql);
				sqlbv3.put("customerno", tCustomerNo);
				ExeSQL tExeSQL = new ExeSQL();
				String tCustomerName = tExeSQL.getOneValue(sqlbv3);
				if (j == 1) {
					tCustomer = tCustomerName;
				} else {
					tCustomer = tCustomer + "，" + tCustomerName;
				}
			}
			// \u7D66数组赋值，然后Add到listable里
			String[] Stra = new String[12];
			Stra[0] = tMngComCode; // 机构代码
			Stra[1] = tMngComName; // 机构名称
			Stra[2] = tClmNo; // 赔案号
			Stra[3] = tCustomer; // 出险人
			Stra[4] = tAccDate; // 事故发生日期
			Stra[5] = tAccidentDate;// 出险日期
			Stra[6] = tRptorName; // 报案人姓名
			Stra[7] = tRptorPhone; // 报案人电话
			Stra[8] = tClmState; // 案件的状态
			Stra[9] = tBeforePay; // 预付金额
			Stra[10] = tAuditPer; // 审核人代码
			Stra[11] = tUserName; // 审核人姓名
			tListTable.add(Stra);

			tSumBeforePay = tSumBeforePay + Double.parseDouble(tBeforePay);
			logger.debug("-------------第 " + i + " 次循环结束----------------");
		}
		tStatSum = String.valueOf(tSumBeforePay);

		logger.debug("********************************************");
		logger.debug("----------以下 查询，并为 单个变量赋值-----------");

		// 管理机构名称
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mStatiCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		tStatCondition = "统计机构:" + tMngCom + " 统计时间段:" + mStartDate + "至"
				+ mEndDate + " 申请类型：" + mNCLTypeName;

		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tTextTag.add("StatCondition", tStatCondition);// 统计条件：$/=StatCondition$
		tTextTag.add("StatDate", SysDate);// 统计时间：$=/StatDate$
		tTextTag.add("StatCount", tStatCount);// 统计数量：$=/StatCount$
		tTextTag.add("StatSum", tStatSum);// 统计金额：$=/StatSum$

		logger.debug("********************************************");
		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		/* ##################### 后台调试部分，生成临时文件############################ */
		logger.debug("---以下 将tXmlExport打包，返回前台--");
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			return false;
		}
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

}
