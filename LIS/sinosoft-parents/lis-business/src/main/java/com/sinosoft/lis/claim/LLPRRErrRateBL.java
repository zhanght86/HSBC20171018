package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
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
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 统计报表打印：差错率统计--LLPRRErrRate.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、审核用户）、机构统计范围选项（总公司、某分公司、某中支）、统计时间段
 * 表头：报表名称、统计条件、统计日期 数据项：机构（或用户）、各种审批不通过原因的审批不通过案件数量、审批不通过案件总数 算法：按照选择的条件统计各项数据
 * 排序：机构（或用户） 表尾：各项数据的合计 注：同一案件同一原因多次不通过计算为一件
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author niuzj,2005-09-26
 * @version 1.0
 */

public class LLPRRErrRateBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRErrRateBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String CurrentTime = PubFun.getCurrentTime(); // 系统时间

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String strManageCom = ""; // 统计机构
	private String strLevel = ""; // 统计层面
	private String strStartDate = ""; // 统计起期
	private String strEndDate = ""; // 统计止期
	// private String strNoPassRea = ""; //审批不通过原因
	private String mNCLType = ""; // 申请类型

	public LLPRRErrRateBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------统计报表打印：差错率统计-----LLPRRErrRateBL测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("----------统计报表打印：差错率统计-----LLPRRErrRateBL测试-----结束----------");
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
		this.mInputData = cInputData;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.strManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		this.strLevel = (String) mTransferData.getValueByName("Levell"); // 统计层面
		this.strStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		this.strEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		// 审批不通过原因 this.strNoPassRea = (String)
		// mTransferData.getValueByName("NoPassRea");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLPRRErrRate.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("ER");

		// 定义列表标题，共15列
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
		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/ER/ROW/COL内容，并为列表赋值--");

		// 为了计算最后的各项数据总和
		int tERAdd1 = 0;
		int tERAdd2 = 0;
		int tERAdd3 = 0;
		int tERAdd4 = 0;
		int tERAdd5 = 0;
		int tERAdd6 = 0;
		int tERAdd7 = 0;
		int tERAdd8 = 0;
		int tERAdd9 = 0;
		int tERAdd10 = 0;
		int tERAdd11 = 0;
		int tERAdd12 = 0;
		int tERAdd13 = 0;
		int tERAdd14 = 0;

		String applType = ""; // 申请类型
		if (mNCLType.trim().equals("1")) // 申请类型
		{
			applType = " and Exists (Select 'X' From Llregister Where Llregister.Rgtno = a.Clmno And Llregister.Rgtobj = '1')";
		} else {
			applType = " and Exists (Select 'X' From Llregister Where Llregister.Rgtno = a.Clmno And Llregister.Rgtobj = '2')";
		}

		// 先查询出审批不通过原因
		String strSql1 = " select code,codename from ldcode " + " where 1=1 "
				+ " and codetype='llexamnopassreason' " + " order by code ";
		logger.debug("查询审批不通过原因的SQL语句为:" + strSql1);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSql1);
		ExeSQL exeSQL1 = new ExeSQL();
		SSRS ssrsl = exeSQL1.execSQL(sqlbv);

		// 再查询出管理机构或审核用户
		String strLevelSql = ""; // 查询统计层面
		if (!strLevel.equals("05")) { // 机构层面选项为非用户
			strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') "
					+ " and comgrade='"
					+ "?level?"
					+ "' " + " order by ComCode ";
		} else { // 用户层面
			strLevelSql = " select usercode,UserName from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%') " + " and checkflag='1' " // 审核用户
					+ " order by usercode";
		}
		logger.debug("查询统计层面的SQL语句为:" + strLevelSql);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strLevelSql);
		sqlbv1.put("mngcom", strManageCom);
		sqlbv1.put("level", strLevel);
		ExeSQL execLevelSQL = new ExeSQL();
		SSRS ssrsLevel = execLevelSQL.execSQL(sqlbv1); // 执行查询语句strLevelSql
		if (ssrsLevel.getMaxRow() != 0) {
			for (int i = 1; i <= ssrsLevel.getMaxRow(); i++) // 以机构或用户为外循环
			{
				String mngCode = ssrsLevel.GetText(i, 1); // 机构或用户代码
				String tMngName = ssrsLevel.GetText(i, 2); // 机构或用户名称
				String strSql = " select count(distinct clmno) from llclaimuwmdetail a "
						+ " where 1=1 "
						+ " and a.ExamConclusion='1' " // 审批不通过
						+ applType
						+ " and a.ExamNoPassReason is not null " // 审批不通过原因不为空
						+ " and a.ExamDate between '"
						+ "?startdate?"
						+ "' and '" + "?enddate?" + "' ";
				// 用统计层面,拼SQL语句
				if (!strLevel.equals("05"))// 统计层面:非用户层面
				{
					strSql = strSql + " and a.MngCom like concat('" + "?mngCode?" + "','%') ";
				} else {
					strSql = strSql + " and a.auditper = '" + "?mngCode?" + "' ";
				}

				String[] Stra = new String[16]; // 给模板中的每一列赋值
				Stra[0] = tMngName; // 机构或用户名称

				String strSql2 = "";
				int tZS = 0;
				String tNoPassRea = ""; // 审批不通过原因
				for (int j = 1; j <= ssrsl.getMaxRow(); j++) // 以审批不通过原因为内循环
				{
					tNoPassRea = ssrsl.GetText(j, 1);
					strSql2 = strSql + " and a.ExamNoPassReason='" + "?nopassrea?"
							+ "' ";

					logger.debug("最后的查询SQL语句为:" + strSql);
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(strSql2);
					sqlbv2.put("startdate", strStartDate);
					sqlbv2.put("enddate", strEndDate);
					sqlbv2.put("mngCode", mngCode);
					sqlbv2.put("nopassrea", tNoPassRea);
					ExeSQL strExeSQL = new ExeSQL();
					SSRS strSSRS = new SSRS();
					strSSRS = strExeSQL.execSQL(sqlbv2);

					Stra[j] = strSSRS.GetText(1, 1); // 依次给2到14列赋值

					// 审批不通过案件总数
					tZS = tZS + Integer.parseInt(Stra[j]);
				}

				Stra[14] = String.valueOf(tZS); // 审批不通过案件总数
				Stra[15] = mngCode; // 机构（或用户）代码
				tListTable.add(Stra);

				// 计算各项数据的合计
				tERAdd1 = tERAdd1 + Integer.parseInt(Stra[1]); // 审批不通过原因1对应的案件总数
				tERAdd2 = tERAdd2 + Integer.parseInt(Stra[2]); // 审批不通过原因2对应的案件总数
				tERAdd3 = tERAdd3 + Integer.parseInt(Stra[3]); // 审批不通过原因3对应的案件总数
				tERAdd4 = tERAdd4 + Integer.parseInt(Stra[4]); // 审批不通过原因4对应的案件总数
				tERAdd5 = tERAdd5 + Integer.parseInt(Stra[5]); // 审批不通过原因5对应的案件总数
				tERAdd6 = tERAdd6 + Integer.parseInt(Stra[6]); // 审批不通过原因6对应的案件总数
				tERAdd7 = tERAdd7 + Integer.parseInt(Stra[7]); // 审批不通过原因7对应的案件总数
				tERAdd8 = tERAdd8 + Integer.parseInt(Stra[8]); // 审批不通过原因8对应的案件总数
				tERAdd9 = tERAdd9 + Integer.parseInt(Stra[9]); // 审批不通过原因9对应的案件总数
				tERAdd10 = tERAdd10 + Integer.parseInt(Stra[10]); // 审批不通过原因10对应的案件总数
				tERAdd11 = tERAdd11 + Integer.parseInt(Stra[11]); // 审批不通过原因11对应的案件总数
				tERAdd12 = tERAdd12 + Integer.parseInt(Stra[12]); // 审批不通过原因12对应的案件总数
				tERAdd13 = tERAdd13 + Integer.parseInt(Stra[13]); // 审批不通过原因13对应的案件总数
				tERAdd14 = tERAdd14 + tZS;
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRRClaimConclusionBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计日期,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("ERStaDate", SysDate); // 统计日期: $=/PPStaDate$

		// 统计时间段:$=/PPStaTimes$
		String tStatTimes = strStartDate + "至" + strEndDate;
		tTextTag.add("ERStaTimes", tStatTimes);
		// 申请类型:$=ApplType/$
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 "; // 申请类型
		tTextTag.add("ApplType", tApplTypeName); // 申请类型

		// 统计机构名称:$=/PPStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("ERStaSer", tMngCom);

		// 统计层面:$=/PPManage$
		String tLevelName = "";
		switch (Integer.parseInt(strLevel)) {
		case 1:
			tLevelName = "总公司";
			break;
		case 2:
			tLevelName = "分公司";
			break;
		case 3:
			tLevelName = "中支公司";
			break;
		case 4:
			tLevelName = "支公司";
			break;
		case 5:
			tLevelName = "审核用户";
			break;
		default:
			tLevelName = "";
			break;
		}
		tTextTag.add("ERManage", tLevelName);

		// 统计各种数据的和
		tTextTag.add("ERAdd1", tERAdd1);
		tTextTag.add("ERAdd2", tERAdd2);
		tTextTag.add("ERAdd3", tERAdd3);
		tTextTag.add("ERAdd4", tERAdd4);
		tTextTag.add("ERAdd5", tERAdd5);
		tTextTag.add("ERAdd6", tERAdd6);
		tTextTag.add("ERAdd7", tERAdd7);
		tTextTag.add("ERAdd8", tERAdd8);
		tTextTag.add("ERAdd9", tERAdd9);
		tTextTag.add("ERAdd10", tERAdd10);
		tTextTag.add("ERAdd11", tERAdd11);
		tTextTag.add("ERAdd12", tERAdd12);
		tTextTag.add("ERAdd13", tERAdd13);
		tTextTag.add("ERAdd14", tERAdd14);

		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		/* ##################### 后台调试部分，生成临时文件############################ */
		// String strTemplatePath="E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"test.vts"; //生成临时文件名
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);
		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");

		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	}

	// 错误处理
	public CErrors getErrors() {
		return mErrors;
	}

	// 得到结果集
	public VData getResult() {
		return mResult;
	}

	// 主函数,test
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "8632"); // 统计机构
		tTransferData.setNameAndValue("Levell", "03"); // 机构层面
		tTransferData.setNameAndValue("StartDate", "2005-1-1"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-9-23"); // 统计止期
		// tTransferData.setNameAndValue("NoPassRea", ""); //审批不通过原因

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRErrRateBL tLLPRRErrRateBL = new LLPRRErrRateBL();
		if (tLLPRRErrRateBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：差错率统计---失败----");
		}
		int n = tLLPRRErrRateBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRErrRateBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
