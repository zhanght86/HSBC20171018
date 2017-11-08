package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LLClaimUWMDetailDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.vschema.LLClaimUWMDetailSet;
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
 * Title: 理赔清单打印：差错明细清单------------LLPRBMistakeList.vts
 * </p>
 * <p>
 * Description: 统计界面：统计时间段、统计机构（某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、
 * 审批不通过原因选项（每个原因、所有原因） 表头：报表名称、统计条件、统计日期 数据项： 赔案号、接报案人、立案人、 审核人（同一审核流程中的最后审核人）、
 * 审批人（同一审批流程中的最后审批人）、 审批日期、审批不通过次数、审批不通过原因
 * 算法：统计在选定条件下审批不通过的案件（按照审批日期统计），同一不通过原因多次不通过按照一条记录处理 排序：赔案号、审批日期 表尾：统计数量
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

public class LLPRBMistakeListBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBMistakeListBL.class);

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
	private String mExamNoPass = ""; // 审批不通过原因
	private String mNCLType = ""; // 申请类型

	public LLPRBMistakeListBL() {
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
		tTransferData.setNameAndValue("StartDate", "2006-2-1");// 开始日期
		tTransferData.setNameAndValue("EndDate", "2006-2-23"); // 结束日期
		tTransferData.setNameAndValue("StatiCode", "862100");// 统计机构
		tTransferData.setNameAndValue("ExamNoPass", "");// 审批不通过原因
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBMistakeListBL tLLPRBMistakeListBL = new LLPRBMistakeListBL();
		if (tLLPRBMistakeListBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：差错明细清单出错---------------");
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-----理赔清单打印：差错明细清单开始---------LLPRBMistakeListBL---");
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
		logger.debug("-----理赔清单打印：差错明细清单结束----LLPRBMistakeListBL---");

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
		mExamNoPass = (String) mTransferData.getValueByName("ExamNoPass"); // 审批不通过原因
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
		tXmlExport.createDocument("LLPRBMistakeList.vts", "");

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/ERRORLIST/ROW/COL内容，并为列表赋值--");
		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("ERRORLIST");
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

		// 首先根据条件《按照审批日期、机构 统计》 ,排序：赔案号，
		// 查询出 赔案号---同一不通过原因（可能多次，只作为一条处理）---最新审批日期
		// 同时得到 《统计数量》
		String tclaimSql = "";
		String applType = "";
		if (mNCLType.trim().equals("1")) // 申请类型
		{
			applType = " and Exists (Select 'X' From Llregister Where Llregister.Rgtno = a.Clmno And Llregister.Rgtobj = '1')";
		} else {
			applType = " and Exists (Select 'X' From Llregister Where Llregister.Rgtno = a.Clmno And Llregister.Rgtobj = '2')";
		}
		if (mExamNoPass == null || mExamNoPass.equals("")) {
			tclaimSql = "select distinct a.clmno,a.examnopassreason,max(a.examdate) "
					+ " from llclaimuwmdetail a where 1=1 "
					+ " and a.examconclusion='1' and a.examnopassreason is not null"
					+ " and a.mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and a.examdate between '"
					+ "?startcode?"
					+ "' and '"
					+ "?enddate?"
					+ "' "
					+ applType
					+ " group by a.clmno,a.examconclusion,a.examnopassreason"
					+ " order by a.clmno,max(a.examdate)";
		} else {
			tclaimSql = "select distinct a.clmno,a.examnopassreason,max(a.examdate) "
					+ " from llclaimuwmdetail a where 1=1 "
					+ " and a.examconclusion='1' and a.examnopassreason='"
					+ "?examnopass?"
					+ "'"
					+ " and a.mngcom like concat('"
					+ "?staticode?"
					+ "','%')"
					+ " and a.examdate between '"
					+ "?startcode?"
					+ "' and '"
					+ "?enddate?"
					+ "' "
					+ applType
					+ " group by a.clmno,a.examconclusion,a.examnopassreason"
					+ " order by a.clmno,max(a.examdate)";
		}
		logger.debug("---tclaimSql==" + tclaimSql); 
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tclaimSql);
		sqlbv.put("staticode", mStatiCode);
		sqlbv.put("examnopass", mExamNoPass);
		sqlbv.put("startcode", mStartDate);
		sqlbv.put("enddate", mEndDate);
		
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);

		// 统计数量----记录数量
		String tStatCount = "0";

		// 以下循环处理每一个 赔案号---同一不通过原因
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			logger.debug("-------------第 " + i + " 次循环开始----------------");
			String tClmNo = tSSRS.GetText(i, 1);// 赔案号
			String tExamNoPassReasonCode = tSSRS.GetText(i, 2);// 审批不通过原因<代码>
			String tExamDate = tSSRS.GetText(i, 3);// 审批日期(最新)
			// 以下 根据“赔案号”查询 ---“接报案人、立案人”
			LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
			LLReportSchema tLLReportSchema = new LLReportSchema();
			tLLReportSchema = tLLPRTPubFunBL.getLLReport(tClmNo);
			tLLClaimUserSchema = tLLPRTPubFunBL.getLLClaimUser(tLLReportSchema
					.getOperator());
			String tReceiver = tLLClaimUserSchema.getUserName();// 接报案人

			LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
			tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(tClmNo);
			tLLClaimUserSchema = null;
			tLLClaimUserSchema = tLLPRTPubFunBL
					.getLLClaimUser(tLLRegisterSchema.getOperator());
			String tRgtName = tLLClaimUserSchema.getUserName();// 立案人

			// 以下 根据“审批不通过原因代码”查询“审批不通过原因”
			String tExamNoPassReason = tLLPRTPubFunBL.getLDCode(
					"llexamnopassreason", tExamNoPassReasonCode);

			// 以下查询-----审核人、审批人、审批不通过次数（因为同一原因）
			LLClaimUWMDetailDB ttLLClaimUWMDetailDB = new LLClaimUWMDetailDB();
			LLClaimUWMDetailSet ttLLClaimUWMDetailSet = new LLClaimUWMDetailSet();
			String tSql = "select * from llclaimuwmdetail where 1=1"
					+ " and clmno='" + "?clmno?" + "' "
					+ " and examnopassreason='" + "?code?" + "' "
					+ " and (examdate >='" + "?startdate?" + "' and examdate<='"
					+ "?enddate?" + "') " + " order by clmno,examdate desc";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("clmno", tClmNo);
			sqlbv1.put("code", tExamNoPassReasonCode);
			sqlbv1.put("startdate", mStartDate);
			sqlbv1.put("enddate", mEndDate);
			ttLLClaimUWMDetailSet.set(ttLLClaimUWMDetailDB.executeQuery(sqlbv1));
			String tAuditPer = ttLLClaimUWMDetailSet.get(1).getAuditPer();// 审核人（同一审核流程中的最后审核人）
			String tExamPer = ttLLClaimUWMDetailSet.get(1).getExamPer();// 审批人（同一审批流程中的最后审批人
			LLClaimUserSchema tLLClaimUserAu = new LLClaimUserSchema();// 将审核人和审批人代码汉化
			tLLClaimUserAu = tLLPRTPubFunBL.getLLClaimUser(tAuditPer);
			String UserNameAu = tLLClaimUserAu.getUserName();// 审核人名字
			LLClaimUserSchema tLLClaimUserEx = new LLClaimUserSchema();
			tLLClaimUserEx = tLLPRTPubFunBL.getLLClaimUser(tExamPer);
			String UserNameEx = tLLClaimUserEx.getUserName();// 审批人名字
			String tExamNoPassNo = String.valueOf(ttLLClaimUWMDetailSet.size());// 审批不通过次数
			// Modify by zhaorx 2006-03-08（同一不通过原因多次不通过按照一条记录处理去，前面已经group
			// by,这里当然是每个原因一条记录了，我是充分相信同志们的啊）
			// String tExamNoPassNo="1";//审批不通过次数Modify by zhaorx 2006-10-13
			int nNoPassNoA = 0;
			nNoPassNoA = Integer.parseInt(tStatCount)
					+ Integer.parseInt(tExamNoPassNo);
			tStatCount = String.valueOf(nNoPassNoA);

			// 机构名称
			LDComSchema tLDComSchema = new LDComSchema();
			LDComDB tLDComDB = new LDComDB();
			tLDComDB.setComCode(mStatiCode);
			tLDComDB.getInfo();
			tLDComSchema = tLDComDB.getSchema();
			String tMngComName = tLDComSchema.getName();

			String[] stra = new String[10];
			stra[0] = mStatiCode; // 机构代码
			stra[1] = tMngComName; // 机构名称
			stra[2] = tClmNo; // 赔案号
			stra[3] = tReceiver; // 接报案人
			stra[4] = tRgtName; // 立案人
			stra[5] = UserNameAu; // 审核人（同一审核流程中的最后审核人）
			stra[6] = UserNameEx; // 审批人（同一审批流程中的最后审批人）
			stra[7] = tExamDate; // 审批日期
			stra[8] = tExamNoPassNo; // 审批不通过次数
			stra[9] = tExamNoPassReason; // 审批不通过原因
			tListTable.add(stra);
			logger.debug("-------------第 " + i + " 次循环结束----------------");
		}

		logger.debug("********************************************");
		logger.debug("----------以下 查询，并为 单个变量赋值-----------");
		// 统计时间-------系统时间---------------------------------------------
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 统计机构名称
		String tStatiSQL = "select name from ldcom where comcode='"
				+ "?comcode?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStatiSQL);
		sqlbv1.put("comcode", mStatiCode);
		ExeSQL tStatiExeSQL = new ExeSQL();
		SSRS tStatiSSRS = new SSRS();
		tStatiSSRS = tStatiExeSQL.execSQL(sqlbv1);
		String tStatiCodeName = tStatiSSRS.GetText(1, 1);
		// 审批日期在 传入的 起止日期之间
		String ttExamDate = "审批日期：" + mStartDate + "至" + mEndDate;
		// 审批不通过原因
		String tExamNoPass = "";
		if (mExamNoPass == null || mExamNoPass.equals("")) {
			tExamNoPass = "所有原因";
		} else {
			tExamNoPass = tLLPRTPubFunBL.getLDCode("llexamnopassreason",
					mExamNoPass);
		}
		tExamNoPass = "审批不通过原因：" + tExamNoPass;

		// 统计条件:$/=StatCondition$
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 "; // 申请类型
		String tStatCondition = "统计机构：" + tStatiCodeName + " / " + ttExamDate
				+ " / " + tExamNoPass + " / 申请类型:" + tApplTypeName;
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tTextTag.add("StatCondition", tStatCondition); // 统计条件：$/=StatCondition$
		tTextTag.add("StatDate", SysDate);// 统计时间：$=/StatDate$-------当前日期
		tTextTag.add("StatCount", tStatCount);// 统计数量：$=/StatCount$

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
