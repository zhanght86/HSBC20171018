package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
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
 * Title: 理赔清单打印：案件类型清单------------LLPRBCaseType.vts
 * </p>
 * <p>
 * Description: 统计界面：统计机构（某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、
 * 统计时间段、统计口径选项（立案时间、结案时间）、案件类型选项（简易案件、 普通案件、诉讼案件、申诉案件、疑难案件、全部类型） 表
 * 头：报表名称、统计条件、统计日期 数 据 项：赔案号、案件类型、出险人、意外事故发生日期和出险日期、
 * 报案人和联系方式、案件的状态、立案时间、结案日期、金额 排 序：案件类型、选定的统计口径选项、赔案号 表 尾：统计数量和金额
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author modify by wanzh 2005/11/18
 * @version 1.0
 */

public class LLPRBCaseTypeBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBCaseTypeBL.class);

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
	private String mCaseType = ""; // 案件类行
	private String mStatiCal = ""; // 统计口径
	private String mNCLType = ""; // 申请类型

	public LLPRBCaseTypeBL() {
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
		tTransferData.setNameAndValue("StartDate", "2004-8-1"); // 开始日期
		tTransferData.setNameAndValue("EndDate", "2006-8-3"); // 结束日期
		tTransferData.setNameAndValue("StatiCode", "86"); // 统计机构
		tTransferData.setNameAndValue("CaseType", "01"); // 案件类型
		tTransferData.setNameAndValue("StatiCal", "0"); // 统计口径

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBCaseTypeBL tLLPRBCaseTypeBL = new LLPRBCaseTypeBL();
		if (tLLPRBCaseTypeBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：案件类型清单出错---------------");
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----理赔清单打印：案件类型清单开始---------LLPRBCaseTypeBL---");
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
		logger.debug("-----理赔清单打印：案件类型清单结束----LLPRBCaseTypeBL---");

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

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 开始日期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 结束日期
		mStatiCode = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
		mCaseType = (String) mTransferData.getValueByName("CaseType"); // 案件类型
		mStatiCal = (String) mTransferData.getValueByName("StatiCal"); // 统计口径
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
		tXmlExport.createDocument("LLPRBCaseType.vts", "");

		// 申请类型判断条件
		String tApplType = mNCLType.trim().equals("1") ? " and t.rgtobj='1' "
				: " and t.rgtobj='2' ";
		// 拼写 Sql语句，查询 符合条件的 赔案
		String llregisterSQL = " select t.RgtNo ,t.mngcom from llregister t where  1=1 "
				+ " and t.mngcom like concat('" + "?staticode?" + "','%') ";
		// 案件类型判断
		String tRgtState = mCaseType.equals("") ? " " // 传入案件类型为空－－－选择“全部类型”
				: " and t.rgtstate ='" + "?casetype?" + "' ";// 传入案件类型不为空
		if (mStatiCal.equals("0"))// 传入统计口径为"0----立案时间"
		{
			llregisterSQL = llregisterSQL + " and t.rgtdate between '"
					+ "?startdate?" + "' and '" + "?enddate?" + "' " + tRgtState
					+ tApplType + " order by t.rgtstate,t.rgtdate,t.rgtno ";
		} else if (mStatiCal.equals("1"))// 传入统计口径为"1----结案时间"
		{
			llregisterSQL = llregisterSQL + " and  t.endcasedate between '"
					+ "?startdate?" + "' and '" + "?enddate?" + "'" + tRgtState
					+ tApplType + " order by t.rgtstate,t.endcasedate,t.rgtno ";
		} else// 传入统计口径为"2----审批通过日期"
		{
			llregisterSQL = " select t.RgtNo ,t.mngcom from llregister t,llclaimuwmain m "
					+ " where t.rgtno = m.clmno and  m.examdate between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "'"
					+ tRgtState
					+ " and t.mngcom like concat('"
					+ "?staticode?"
					+ "','%') "
					+ tApplType
					+ " order by t.rgtstate,m.examdate,t.rgtno ";
		}
		logger.debug("**llregisterSQL===" + llregisterSQL);
		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/CASETY/ROW/COL内容，并为列表赋值--");
		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("CASETY");
		// 定义列表标题
		String[] Title = new String[13];
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
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(llregisterSQL);
		sqlbv.put("staticode", mStatiCode);
		sqlbv.put("casetype", mCaseType);
		sqlbv.put("startdate", mStartDate);
		sqlbv.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);

		// 统计数量----记录数量
		int tStatCount = 0; // 用来统计符合条件的记录的数量
		double tStatMoney = 0;// 总金额
		// 以下循环处理每一条记录
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			logger.debug("-------------第 " + i + " 次循环开始----------------");

			String tClmNo = tSSRS.GetText(i, 1); // 赔案号
			String tMngComCode = tSSRS.GetText(i, 2); // 机构代码

			// 机构名称
			LDComSchema tLDComSchema = new LDComSchema();
			LDComDB tLDComDB = new LDComDB();
			tLDComDB.setComCode(tMngComCode);
			tLDComDB.getInfo();
			tLDComSchema = tLDComDB.getSchema();
			String tMngComName = tLDComSchema.getName();

			// 案件类型
			LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
			tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(tClmNo);
			String tRgtStateCode = tLLRegisterSchema.getRgtState();
			mCaseType = tLLPRTPubFunBL.getLDCode("llrgttype", tRgtStateCode);// 将代码转化成具体名称

			// 出险人
			String tCustomerName = "";
			LLCaseDB tLLCaseDB = new LLCaseDB();
			tLLCaseDB.setCaseNo(tClmNo);
			LLCaseSet tLLCaseSet = new LLCaseSet();
			tLLCaseSet = tLLCaseDB.query();
			for (int k = 1; k <= tLLCaseSet.size(); k++) {
				String tSql = "select a.name from ldperson a where "
						+ "a.customerno = '"
						+ "?customerno?" + "'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSql);
				sqlbv1.put("customerno", tLLCaseSet.get(k).getCustomerNo());
				ExeSQL tExeSQL = new ExeSQL();
				String ttCustomerName = tExeSQL.getOneValue(sqlbv1);
				if (k == 1) {
					tCustomerName = ttCustomerName;
				} else // 如果出险人不唯一
				{
					tCustomerName = tCustomerName + " " + ttCustomerName;
				}
			}

			// 事故日期
			LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
			tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(tClmNo);
			String tAccDate = tLLAccidentSchema.getAccDate();

			// 出险日期
			String tAccidentDate = tLLRegisterSchema.getAccidentDate();

			// 取得报案人和联系方式
			String tSQLRptor = " select rptorname,rptorphone from llreport where RptNo = '"
					+ "?RptNo?" + "' and RgtFlag != '30' ";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQLRptor);
			sqlbv2.put("RptNo", tClmNo);
			SSRS tRptorSSRS = new SSRS();
			tRptorSSRS = cusExeSQL.execSQL(sqlbv2);
			String tRptorName = "";
			String tRptorPhone = "";
			if (tRptorSSRS.getMaxRow() > 0) {
				tRptorName = tRptorSSRS.GetText(1, 1);
				tRptorPhone = tRptorSSRS.GetText(1, 2);
			}

			// 案件状态 llclaimstate
			String tClmStateCode = tLLRegisterSchema.getClmState();
			String tClmState = tLLPRTPubFunBL.getLDCode("llclaimstate",
					tClmStateCode);

			// 立案时间
			String tRgtDate = tLLRegisterSchema.getRgtDate();

			// 结案日期
			String tEndCaseDate = tLLRegisterSchema.getEndCaseDate();

			// 金额
			LLClaimSchema tllclaimSchema = new LLClaimSchema();
			LLClaimDB tLLClaimDB = new LLClaimDB();
			tLLClaimDB.setClmNo(tClmNo);
			tLLClaimDB.getInfo();
			tllclaimSchema = tLLClaimDB.getSchema();
			double tRealPay = tllclaimSchema.getRealPay();

			String[] stra = new String[13];
			stra[0] = tClmNo; // 赔案号
			stra[1] = mCaseType; // 案件类型
			stra[2] = tCustomerName; // 出险人
			stra[3] = tAccDate; // 事故日期
			stra[4] = tAccidentDate; // 出险日期
			stra[5] = tRptorName; // 报案人
			stra[6] = tRptorPhone; // 报案人联系方式
			stra[7] = tClmState; // 案件状态
			stra[8] = tRgtDate; // 立案时间
			stra[9] = tEndCaseDate; // 结案日期
			stra[10] = String.valueOf(tRealPay);// 金额
			stra[11] = tMngComCode; // 机构代码
			stra[12] = tMngComName; // 机构名称

			tListTable.add(stra);
			logger.debug("-------------第 " + i + " 次循环结束----------------");
			tStatCount = tStatCount + 1; // 记录数量加1
			tStatMoney = tStatMoney + tRealPay; // 总金额
		}

		switch (Integer.parseInt(mStatiCal)) {
		case 0:
			mStatiCal = "立案时间";
			break;
		case 1:
			mStatiCal = "结案时间";
			break;
		case 2:
			mStatiCal = "审批通过时间";
			break;
		default:
			mStatiCal = "";
			break;
		}
		logger.debug("********************************************");
		logger.debug("----------以下 查询，并为 单个变量赋值-----------");
		// 统计时间-------系统时间---------------------------------------------
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 管理机构名称
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mStatiCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		String tCaseType = mCaseType.equals("") ? "全部类型" : "" + mCaseType + "";

		// 申请类型
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		String tStatCondition = "统计机构:" + tMngCom + " 统计时间段:" + mStartDate
				+ "至" + mEndDate + " 统计口径:" + mStatiCal + " 案件类型:" + tCaseType
				+ " 申请类型:" + tApplTypeName;// 统计条件
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tTextTag.add("StatCondition", tStatCondition); // 统计条件：$/=StatCondition$
		tTextTag.add("StatDate", SysDate); // 统计时间：$=/StatDate$
		tTextTag.add("StatCount", tStatCount); // 统计数量：$=/StatCount$
		tTextTag.add("StatMoney", tStatMoney);// 统计金额：$=/StatMoney$

		logger.debug("********************************************");
		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");
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

	// 错误处理函数
	public CErrors getErrors() {
		return mErrors;
	}

	// 打包数据---用于向前台返回
	public VData getResult() {
		return mResult;
	}
}
