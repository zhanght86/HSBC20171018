package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
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
 * Description: 案件类型统计表打印
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计时间段、统计层面选项（赔案、保项）、统计口径选项（报案日期、立案日期、审核日期、审批日期、
 * 调查日期、结案日期、实付(给付)日期）,险种选项（分险种统计、不分险种统计） 表头：报表名称、统计条件、统计日期
 * 数据项：机构（或用户）、险种，普通、投诉、诉讼、疑难案件、简易案件件数和案件总数、普通、投诉、
 * 诉讼、疑难案件、简易案件涉及金额和案件总金额（审核完成前统计预估金额） 算法：按照选择的条件统计各项数据 排序：机构（或用户） 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZHaoRx，2005-9-26 21:35
 * @version 1.0
 */
public class LLPRRCaseTypeBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRCaseTypeBL.class);
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
	private String mStartDate = ""; // 开始日期
	private String mEndDate = ""; // 结束日期
	private String mLevel = ""; // 机构层面选项
	private String mLevelName = "";
	private String mManageCom = ""; // 机构统计范围选项
	private String mManageComName = "";
	private String mStatSer = ""; // 统计层面
	private String mStatSerName = "";
	private String mStatAround = ""; // 统计口径
	private String mStatAroundName = "";
	private String mCTRiskCho = ""; // 险种选项
	private String mCTRiskChoName = "";
	private String mNCLType = ""; // 申请类型

	public LLPRRCaseTypeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-----统计报表打印：案件类型统计报表-----LLPRRCaseTypeBL测试-----开始-----");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 处理业务数据
		if (!dealData()) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("-----统计报表打印：案件类型统计报表-----LLPRRCaseTypeBL测试-----结束------");

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

		this.mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		this.mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		this.mLevel = (String) mTransferData.getValueByName("Level"); // 机构层面
		this.mLevelName = (String) mTransferData.getValueByName("LevelName");
		this.mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 机构统计范围
		this.mManageComName = (String) mTransferData
				.getValueByName("ManageComName");
		this.mStatSer = (String) mTransferData.getValueByName("StatSer"); // 统计层面
		this.mStatSerName = (String) mTransferData
				.getValueByName("StatSerName");
		this.mStatAround = (String) mTransferData.getValueByName("StatAround"); // 统计口径
		this.mStatAroundName = (String) mTransferData
				.getValueByName("StatAroundName");
		this.mCTRiskCho = (String) mTransferData.getValueByName("CTRiskCho"); // 险种选项
		this.mCTRiskChoName = (String) mTransferData
				.getValueByName("CTRiskChoName");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType");// 申请类型

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 新建一个TextTag的实例
		TextTag tTextTag = new TextTag();
		// 新建一个XmlExport的实例
		XmlExport tXmlExport = new XmlExport();
		// 所用模板名称
		tXmlExport.createDocument("LLPRRCaseType.vts", "");
		// 为列表的每行（15列）新声明一个数组，并为其赋值，然后添加到列表中去
		String[] CTarr = new String[15];
		for (int i = 1; i <= 15; i++) {
			CTarr[i - 1] = "";
		}
		String[] CTarrAdd = new String[12];// 求各项合计
		for (int z = 1; z <= 12; z++) {
			CTarrAdd[z - 1] = "0";
		}
		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tListTable = new ListTable();
		tListTable.setName("CT");
		ListTable tListTableAdd = new ListTable();
		tListTableAdd.setName("CTAdd");
		String strLevelSql = "";
		if (!mLevel.equals("05"))// 机构层面选项为非用户
		{
			strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and trim(comgrade)='"
					+ "?level?"
					+ "' order by ComCode ";
		} else {
			strLevelSql = " select usercode,UserName from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%') order by usercode ";
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", mManageCom);
		sqlbv.put("level", mLevel);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		// 判断是否查询到管理机构,没查到时提示"没有要统计的数据",例:ManageCom=8632,而Level=01
		if (tSSRS.getMaxRow() <= 0) {
			CError tError = new CError();
			tError.moduleName = "LLPRRCaseTypeBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		String tTIMEs = " between '" + "?startdate?" + "' and '" + "?enddate?" + "' ";// 时间条件字段
		// 险种选项_赔案层面字段
		// String tRiskChoPATa = mCTRiskCho.equals("01") ? " ,llclaimpolicy z
		// where a.rgtno = z.clmno and z.riskcode = x.riskcode " : " where 1=1
		// ";
		String tRiskChoPATa = mCTRiskCho.equals("01") ? " where exists (select 'X' from llclaimpolicy z where a.rgtno = z.clmno and z.riskcode = x.riskcode) "
				: " where 1=1 ";
		String tRiskChoPALa = mCTRiskCho.equals("01") ? " from llclaimpolicy x group by x.riskcode "
				: " from dual ";
		String tRiskChoPANa = mCTRiskCho.equals("01") ? " ,substr(x.riskcode,3,3) "
				: " ,'不分险种' ";
		// 险种选项_保项层面字段
		String tRiskChoBXTa = mCTRiskCho.equals("01") ? " where r.riskcode = c.riskcode "
				: " where 1=1 ";
		String tRiskChoBXLa = mCTRiskCho.equals("01") ? " from llclaimdetail r group by r.riskcode "
				: " from dual ";
		String tRiskChoBXNa = mCTRiskCho.equals("01") ? " ,substr(r.riskcode,3,3) "
				: " ,'不分险种' ";
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			logger.debug("-------------第 " + i + " 次外层循环开始--------------");
			String tMngcode = tSSRS.GetText(i, 1);
			String tMngname = tSSRS.GetText(i, 2);

			// 机构或操作人限制条件
			String tMng_Operator = "";
			if (!mLevel.equals("05")) {
				switch (Integer.parseInt(mStatAround)) {
				case 1:
					tMng_Operator = " and b.mngcom like concat('" + "?mngcode?" + "','%') ";
					break;
				case 2:
					tMng_Operator = " and a.mngcom like concat('" + "?mngcode?" + "','%') ";
					break;
				case 3:
					tMng_Operator = " and b.mngcom like concat('" + "?mngcode?" + "','%') ";
					break;
				case 4:
					tMng_Operator = " and b.mngcom like concat('" + "?mngcode?" + "','%') ";
					break;
				case 5:
					tMng_Operator = " and b.InqDept like concat('" + "?mngcode?" + "','%') ";
					break;
				case 6:
					tMng_Operator = " and a.mngcom like concat('" + "?mngcode?" + "','%') ";
					break;
				case 7:
					tMng_Operator = " and a.mngcom like concat('" + "?mngcode?" + "','%') ";
					break;
				default:
					tMng_Operator = "";
					break;
				}
			} else {
				switch (Integer.parseInt(mStatAround)) {
				case 1:
					tMng_Operator = " and b.operator = '" + "?mngcode?" + "' ";
					break;
				case 2:
					tMng_Operator = " and a.operator = '" + "?mngcode?" + "' ";
					break;
				case 3:
					tMng_Operator = " and b.operator = '" + "?mngcode?" + "' ";
					break;
				case 4:
					tMng_Operator = " and b.operator = '" + "?mngcode?" + "' ";
					break;
				case 5:
					tMng_Operator = " and b.operator = '" + "?mngcode?" + "' ";
					break;
				case 6:
					tMng_Operator = " and a.operator = '" + "?mngcode?" + "' ";
					break;
				case 7:
					tMng_Operator = " and a.operator = '" + "?mngcode?" + "' ";
					break;
				default:
					tMng_Operator = "";
					break;
				}
			}

			// 统计层面分为赔案层面和保项层面,此处假设为赔案层面进入(案件类型查取llregister表,金额取自llclaim表)
			String strSQLPC = "";// 赔案层面_统计数量字段
			String strSQLPM = "";// 赔案层面_统计金额字段
			String strSQLPS = "";// 赔案层面_处理申诉案件金额字段
			String strSQLBC = "";// 保项层面_统计数量字段
			String strSQLBM = "";// 保项层面_统计金额字段
			String strSQLBS = "";// 保项层面_处理申诉案件金额字段
			// ***赔案层面分险种统计时，对于一个赔案涉及多个险种的情况，汇总时要求只统计为一件，此时，执行不分险种统计，其余条件相同时的SQL，求和***
			String PCRiskSum = "";// 赔案层面，分险种统计，案件数量汇总求和。
			String PMRiskSum = "";// 赔案层面，分险种统计，案件金额汇总求和
			String PSRiskSum = "";// 赔案层面，分险种统计，涉及到申诉案件金额汇总求和
			// 申请类型判断
			String tApplTypea = mNCLType.equals("1") ? " and a.rgtobj='1' "
					: " and a.rgtobj='2' ";// llregister a
			String tApplTypeb = mNCLType.equals("1") ? " and b.rgtobj='1' "
					: " and b.rgtobj='2' ";// llreport b
			if (mStatSer.equals("01")) {
				switch (Integer.parseInt(mStatAround)) {
				case 1:// 统计口径有七种,报案日期为其中之一
					strSQLPC = " select count(*) from llregister a,llreport b, llclaim c "
							+ tRiskChoPATa
							+ " and a.rgtno = c.clmno "
							+ " and b.rptno = a.rgtno "
							+ " and b.rptdate "
							+ tTIMEs + tMng_Operator + tApplTypea + tApplTypeb;
					strSQLPM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llreport b, llclaim c "
							+ tRiskChoPATa
							+ " and a.rgtno = c.clmno "
							+ " and b.rptno = a.rgtno "
							+ " and b.rptdate "
							+ tTIMEs + tMng_Operator + tApplTypea + tApplTypeb;
					PCRiskSum = " select count(*) from llregister a,llreport b, llclaim c where 1=1 "
							+ " and a.rgtno = c.clmno "
							+ " and b.rptno = a.rgtno "
							+ " and b.rptdate "
							+ tTIMEs + tMng_Operator + tApplTypea + tApplTypeb;
					PMRiskSum = " select (case when sum(c.realpay) is nul 0 then sum(c.realpay) end) from llregister a,llreport b, llclaim c where 1=1"
							+ " and a.rgtno = c.clmno "
							+ " and b.rptno = a.rgtno "
							+ " and b.rptdate "
							+ tTIMEs + tMng_Operator + tApplTypea + tApplTypeb;
					break;
				case 2:// 统计口径为立案日期(之二)
					strSQLPC = " select count(*) from llregister a, llclaim b "
							+ tRiskChoPATa + " and a.rgtno = b.rgtno "
							+ " and a.rgtdate " + tTIMEs + tMng_Operator
							+ tApplTypea;
					strSQLPM = " select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a, llclaim b "
							+ tRiskChoPATa
							+ " and a.rgtno = b.rgtno "
							+ " and a.rgtdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					PCRiskSum = " select count(*) from llregister a, llclaim b where 1=1 "
							+ " and a.rgtno = b.rgtno "
							+ " and a.rgtdate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					PMRiskSum = " select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a, llclaim b where 1=1 "
							+ " and a.rgtno = b.rgtno "
							+ " and a.rgtdate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					break;
				case 3:// 统计口径为审核日期(之三)
					strSQLPC = " select count(*) from llregister a,llclaimuwmain b, llclaim c "
							+ tRiskChoPATa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.auditdate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					strSQLPM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaimuwmain b, llclaim c "
							+ tRiskChoPATa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.auditdate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					PCRiskSum = " select count(*) from llregister a,llclaimuwmain b, llclaim c where 1=1"
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.auditdate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					PMRiskSum = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaimuwmain b, llclaim c where 1=1 "
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.auditdate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					break;
				case 4:// 统计口径为审批日期(之四)
					strSQLPC = " select count(*) from llregister a,llclaimuwmain b, llclaim c "
							+ tRiskChoPATa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					strSQLPM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaimuwmain b, llclaim c "
							+ tRiskChoPATa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					strSQLPS = "select( select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaimuwmain b, llclaim c "
							+ tRiskChoPATa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ " ) -( select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a, llclaimuwmain b, llclaim c "
							+ tRiskChoPATa// 原赔案对应金额和
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and a.rgtno in (select llappeal.caseno from llappeal where llappeal.appealno in "// 对应原赔案号
							+ " (select a.rgtno from llregister a, llclaimuwmain b, llclaim c where 1 = 1 " // 符合条件的已结案申诉案件号
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and a.clmstate = '60' "// 申诉案件在统计条件内，且已结案
							+ " and b.ExamDate "
							+ tTIMEs
							+ " and a.rgtstate = '13' "// 申诉案件
							+ tMng_Operator
							+ tApplTypea
							+ "))"
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea + ") from dual ";
					PCRiskSum = " select count(*) from llregister a,llclaimuwmain b, llclaim c where 1=1 "
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					PMRiskSum = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaimuwmain b, llclaim c where 1=1 "
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					PSRiskSum = "select( select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaimuwmain b, llclaim c where 1=1 "
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ " ) -( select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a, llclaimuwmain b, llclaim c where 1=1 " // 原赔案对应金额和
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and a.rgtno in (select llappeal.caseno from llappeal where llappeal.appealno in "// 对应原赔案号
							+ " (select a.rgtno from llregister a, llclaimuwmain b, llclaim c where 1 = 1 " // 符合条件的已结案申诉案件号
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and a.clmstate = '60' "// 申诉案件在统计条件内，且已结案
							+ " and b.ExamDate "
							+ tTIMEs
							+ " and a.rgtstate = '13' "// 申诉案件
							+ tMng_Operator
							+ tApplTypea
							+ "))"
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea + ") from dual ";
					break;
				// 求出在统计条件内，已结案的申诉案件对应的原案件（原案件要符合统计条件）涉及金额和，从总金额（原案件金额+已结案的申诉案件金额）中减去这部分，下同。
				// 建议后来人不要轻易将这部分注释去掉，它有助于理解相关SQL
				// tShensuP4 = " select sum(nvl(c.realpay,0)) from llregister a,
				// llclaimuwmain b, llclaim c where 1 = 1 "//原赔案对应金额和
				// + " and b.clmno = c.clmno "
				// + " and a.rgtno = b.clmno "
				// + " and a.rgtno in (select llappeal.caseno from llappeal
				// where llappeal.appealno in "//对应原赔案号
				// + " (select a.rgtno from llregister a, llclaimuwmain b,
				// llclaim c where 1 = 1 " //符合条件的已结案申诉案件号
				// + " and b.clmno = c.clmno "
				// + " and a.rgtno = b.clmno "
				// + " and a.clmstate = '60' "//申诉案件在统计条件内，且已结案
				// + " and b.ExamDate " +tTIMEs
				// + " and a.rgtstate = '13' "//申诉案件
				// + tMng_Operator + "))"
				// + " and b.ExamDate " +tTIMEs
				// + tMng_Operator;
				case 5:// 统计口径为调查日期(之五)
					strSQLPC = " select count(*) from llregister a,llInqApply b, llclaim c "
							+ tRiskChoPATa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and b.applydate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					strSQLPM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llInqApply b, llclaim c "
							+ tRiskChoPATa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and b.applydate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					PCRiskSum = " select count(*) from llregister a,llInqApply b, llclaim c where 1=1 "
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and b.applydate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					PMRiskSum = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llInqApply b, llclaim c where 1=1 "
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and b.applydate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					break;
				case 6:// 统计口径为结案日期(之六)
					strSQLPC = " select count(*) from llregister a,llclaim b "
							+ tRiskChoPATa + " and a.rgtno=b.clmno "
							+ " and b.clmstate = '60' " + " and b.EndCaseDate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					strSQLPM = " select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a,llclaim b "
							+ tRiskChoPATa
							+ " and a.rgtno=b.clmno "
							+ " and b.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					strSQLPS = " select ( select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a,llclaim b "
							+ tRiskChoPATa
							+ " and a.rgtno=b.clmno "
							+ " and b.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ " ) - (  select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a,llclaim b "
							+ tRiskChoPATa
							+ " and a.rgtno=b.clmno "
							+ " and b.clmstate = '60' "
							+ " and a.rgtno in (select llappeal.caseno from llappeal where llappeal.appealno in "
							+ " (select a.rgtno from llregister a,llclaim b where 1 = 1 "
							+ " and a.rgtno=b.clmno "
							+ " and b.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs
							+ " and a.rgtstate = '13' "
							+ tMng_Operator
							+ tApplTypea
							+ "))"
							+ " and b.EndCaseDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea + " ) from dual ";
					PCRiskSum = " select count(*) from llregister a,llclaim b where 1=1 "
							+ " and a.rgtno=b.clmno "
							+ " and b.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					PMRiskSum = " select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a,llclaim b where 1=1 "
							+ " and a.rgtno=b.clmno "
							+ " and b.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					PSRiskSum = " select ( select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a,llclaim b where 1=1 "
							+ " and a.rgtno=b.clmno "
							+ " and b.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ " ) - (  select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a,llclaim b where 1=1 "
							+ " and a.rgtno=b.clmno "
							+ " and b.clmstate = '60' "
							+ " and a.rgtno in (select llappeal.caseno from llappeal where llappeal.appealno in "
							+ " (select a.rgtno from llregister a,llclaim b where 1 = 1 "
							+ " and a.rgtno=b.clmno "
							+ " and b.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs
							+ " and a.rgtstate = '13' "
							+ tMng_Operator
							+ tApplTypea
							+ "))"
							+ " and b.EndCaseDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea + " ) from dual ";
					break;
				case 7:// 统计口径为给付日期(之七)
					strSQLPC = " select count(*) from llregister a,llclaim b,ljaget c "
							+ tRiskChoPATa
							+ " and a.rgtno=b.clmno "
							+ " and c.otherno=b.clmno "
							+ " and c.othernotype='5' "
							+ " and c.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					strSQLPM = " select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a,llclaim b,ljaget c "
							+ tRiskChoPATa
							+ " and a.rgtno=b.clmno "
							+ " and c.otherno=b.clmno "
							+ " and c.othernotype='5' "
							+ " and c.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					strSQLPS = " select (select (case when sum(b.realpay) is  null then 0 else sum(b.realpay) end) from llregister a,llclaim b,ljaget c "
							+ tRiskChoPATa
							+ " and a.rgtno=b.clmno "
							+ " and c.otherno=b.clmno "
							+ " and c.othernotype='5' "
							+ " and c.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ ")-( select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a,llclaim b,ljaget c "
							+ tRiskChoPATa
							+ " and a.rgtno=b.clmno "
							+ " and c.otherno=b.clmno "
							+ " and c.othernotype='5' "
							+ " and a.rgtno in (select llappeal.caseno from llappeal where llappeal.appealno in "
							+ " (select a.rgtno from llregister a,llclaim b,ljaget c where 1 = 1 "
							+ " and a.rgtno=b.clmno "
							+ " and c.otherno=b.clmno "
							+ " and c.othernotype='5' "
							+ " and a.rgtstate = '13' "
							+ " and c.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ ")) "
							+ " and c.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ ") from dual ";
					PCRiskSum = " select count(*) from llregister a,llclaim b,ljaget c where 1=1 "
							+ " and a.rgtno=b.clmno "
							+ " and c.otherno=b.clmno "
							+ " and c.othernotype='5' "
							+ " and c.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					PMRiskSum = " select (case when sum(b.realpay) is null then 0 sum(b.realpay) end) from llregister a,llclaim b,ljaget c where 1=1 "
							+ " and a.rgtno=b.clmno "
							+ " and c.otherno=b.clmno "
							+ " and c.othernotype='5' "
							+ " and c.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					PSRiskSum = " select (select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a,llclaim b,ljaget c where 1=1 "
							+ " and a.rgtno=b.clmno "
							+ " and c.otherno=b.clmno "
							+ " and c.othernotype='5' "
							+ " and c.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ ")-( select (case when sum(b.realpay) is null then 0 else sum(b.realpay) end) from llregister a,llclaim b,ljaget c where 1=1 "
							+ " and a.rgtno=b.clmno "
							+ " and c.otherno=b.clmno "
							+ " and c.othernotype='5' "
							+ " and a.rgtno in (select llappeal.caseno from llappeal where llappeal.appealno in "
							+ " (select a.rgtno from llregister a,llclaim b,ljaget c where 1 = 1 "
							+ " and a.rgtno=b.clmno "
							+ " and c.otherno=b.clmno "
							+ " and c.othernotype='5' "
							+ " and a.rgtstate = '13' "
							+ " and c.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ ")) "
							+ " and c.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ ") from dual ";
					break;
				default:
					strSQLPC = "";
					strSQLPM = "";
					strSQLPS = "";
					PCRiskSum = "";
					PMRiskSum = "";
					PSRiskSum = "";
					break;
				}
			}
			// 统计层面为保项层面,案件类型查取llregister表,金额取自llclaimdetail表
			else {
				switch (Integer.parseInt(mStatAround)) {
				case 1:// 统计口径为报案日期(之一)
					strSQLBC = " select count(*) from llregister a,llreport b, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno = c.clmno "
							+ " and b.rptno = a.rgtno "
							+ " and b.rptdate "
							+ tTIMEs + tMng_Operator + tApplTypea + tApplTypeb;
					strSQLBM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llreport b, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno = c.clmno "
							+ " and b.rptno = a.rgtno "
							+ " and b.rptdate "
							+ tTIMEs + tMng_Operator + tApplTypea + tApplTypeb;
					break;
				case 2:// 统计口径为立案日期(之二)
					strSQLBC = " select count(*) from llregister a, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno = c.clmno "
							+ " and a.rgtdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					strSQLBM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno = c.clmno "
							+ " and a.rgtdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					break;
				case 3:// 统计口径为审核日期(之三)
					strSQLBC = " select count(*) from llregister a,llclaimuwmain b, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and b.auditdate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					strSQLBM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaimuwmain b, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and b.auditdate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					break;
				case 4:// 统计口径为审批日期(之四)
					strSQLBC = " select count(*) from llregister a,llclaimuwmain b, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					strSQLBM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaimuwmain b, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					strSQLBS = " select (select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaimuwmain b, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ ")-( select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaimuwmain b, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and a.rgtno in (select llappeal.caseno from llappeal where llappeal.appealno in "
							+ " (select a.rgtno from llregister a,llclaimuwmain b, llclaimdetail c where 1 = 1 "
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno = b.clmno "
							+ " and b.examconclusion = '0' "
							+ " and a.clmstate = '60' " // 申诉案件在统计条件内，且已结案
							+ " and b.ExamDate "
							+ tTIMEs
							+ " and a.rgtstate = '13' " // 申诉案件
							+ tMng_Operator
							+ tApplTypea
							+ ")) "
							+ " and b.ExamDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea + ") from dual ";
					break;
				case 5:// 统计口径为调查日期(之五)
					strSQLBC = " select count(*) from llregister a,llInqApply b, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and b.applydate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					strSQLBM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llInqApply b, llclaimdetail c "
							+ tRiskChoBXTa
							+ " and b.clmno = c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and b.applydate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					break;
				case 6:// 统计口径为结案日期(之六)
					strSQLBC = " select count(*) from llregister a,llclaim b,llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno=c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and a.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					strSQLBM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaim b,llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno=c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and a.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs + tMng_Operator + tApplTypea;
					strSQLBS = " select (select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaim b,llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno=c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and a.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ ")-( select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,llclaim b,llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno=c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and a.clmstate = '60' "
							+ " and a.rgtno in (select llappeal.caseno from llappeal where llappeal.appealno in "
							+ " (select a.rgtno from llregister a,llclaim b,llclaimdetail c where 1 = 1 "
							+ " and a.rgtno=c.clmno "
							+ " and a.rgtno=b.clmno "
							+ " and a.clmstate = '60' "
							+ " and b.EndCaseDate "
							+ tTIMEs
							+ " and a.rgtstate = '13' "
							+ tMng_Operator
							+ tApplTypea
							+ ")) "
							+ " and b.EndCaseDate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea + ") from dual ";
					break;
				case 7:// 统计口径为给付日期(之七)
					strSQLBC = " select count(*) from llregister a,ljaget b,llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno=c.clmno "
							+ " and b.otherno=c.clmno "
							+ " and b.othernotype='5' "
							+ " and b.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					strSQLBM = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,ljaget b,llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno=c.clmno "
							+ " and b.otherno=c.clmno "
							+ " and b.othernotype='5' "
							+ " and b.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea;
					strSQLBS = " select (select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,ljaget b,llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno=c.clmno "
							+ " and b.otherno=c.clmno "
							+ " and b.othernotype='5' "
							+ " and b.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ ")-( select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llregister a,ljaget b,llclaimdetail c "
							+ tRiskChoBXTa
							+ " and a.rgtno=c.clmno "
							+ " and b.otherno=c.clmno "
							+ " and b.othernotype='5' "
							+ " and a.rgtno in (select llappeal.caseno from llappeal where llappeal.appealno in "
							+ " (select a.rgtno from llregister a,llclaimdetail c,ljaget b where 1 = 1 "
							+ " and a.rgtno=c.clmno "
							+ " and b.otherno=c.clmno "
							+ " and b.othernotype='5' "
							+ " and b.enteraccdate "
							+ tTIMEs
							+ " and a.rgtstate = '13' "
							+ tMng_Operator
							+ tApplTypea
							+ ")) "
							+ " and b.enteraccdate "
							+ tTIMEs
							+ tMng_Operator
							+ tApplTypea
							+ ") from dual ";
					break;
				default:
					strSQLPC = "";
					strSQLPM = "";
					strSQLPS = "";
					break;
				}
			}
			String[] Argtstate = new String[5];// 定义一个数组，作为统计各个数据项的必要条件，对于求总数单独处理
			Argtstate[0] = " and a.rgtstate = '01' ";// 简易案件
			Argtstate[1] = " and a.rgtstate = '11' ";// 普通案件
			Argtstate[2] = " and a.rgtstate = '12' ";// 诉讼案件
			Argtstate[3] = " and a.rgtstate = '13' ";// 投诉案件
			Argtstate[4] = " and a.rgtstate = '14' ";// 疑难案件
			String tPartSQLPC = "";// 中间变量_赔案层面求案件数量
			String tPartSQLPM = "";// 中间变量_赔案层面求金额
			String tPartSQLBC = "";// 中间变量_保项层面求案件数量
			String tPartSQLBM = "";// 中间变量_保项层面求金额
			String tPublicSQLPA = "";// 中间变量_赔案层面最后总SQL
			String tPublicSQLBX = "";// 中间变量_保项层面最后总SQL
			String PartPCRiskSum = "";// 中间变量_赔案层面，分险种统计，案件数量求和
			String PartPMRiskSum = "";// 中间变量_赔案层面，分险种统计，案件金额求和
			String PublicRiskSum = "";// 中间变量_赔案层面，分险种统计，最后总SQL
			for (int x = 1; x <= 5; x++)// 通过循环，拼写中间部分SQL
			{
				tPartSQLPC = tPartSQLPC + ",(" + strSQLPC + Argtstate[x - 1]
						+ ")";// 赔案层面
				tPartSQLPM = tPartSQLPM + ",(" + strSQLPM + Argtstate[x - 1]
						+ ")";// 赔案层面
				tPartSQLBC = tPartSQLBC + ",(" + strSQLBC + Argtstate[x - 1]
						+ ")";// 保项层面
				tPartSQLBM = tPartSQLBM + ",(" + strSQLBM + Argtstate[x - 1]
						+ ")";// 保项层面
				PartPCRiskSum = PartPCRiskSum + ",(" + PCRiskSum
						+ Argtstate[x - 1] + ")";// 赔案层面，分险种统计，案件数量求和
				PartPMRiskSum = PartPMRiskSum + ",(" + PMRiskSum
						+ Argtstate[x - 1] + ")";// 赔案层面，分险种统计，案件金额求和
			}
			switch (Integer.parseInt(mStatAround)) {
			case 4:
				strSQLPM = "";
				strSQLBM = "";
				PMRiskSum = "";
				break;// 对于涉及到申诉案件的，总金额用strSQLPS单独处理，此时置strSQLPM或strSQLBM为空
			case 6:
				strSQLPM = "";
				strSQLBM = "";
				PMRiskSum = "";
				break;// 赔案层面，分险种统计涉及到申诉案件时，置PMRiskSum为空
			case 7:
				strSQLPM = "";
				strSQLBM = "";
				PMRiskSum = "";
				break;
			default:
				break;
			}
			tPublicSQLPA = " select '" + "?mngcode?" + "','" + "?mngname?"
					+ "'"
					+ tRiskChoPANa// 赔案层面统计最后总SQL
					+ tPartSQLPC + ",(" + strSQLPC + ")" + tPartSQLPM + ",("
					+ strSQLPM + strSQLPS + ")" + tRiskChoPALa;
			tPublicSQLBX = " select '" + "?mngcode?" + "','" + "?mngname?"
					+ "'"
					+ tRiskChoBXNa// 保项层面统计最后总SQL
					+ tPartSQLBC + ",(" + strSQLBC + ")" + tPartSQLBM + ",("
					+ strSQLBM + strSQLBS + ")" + tRiskChoBXLa;
			PublicRiskSum = " select '','','' " // 赔案层面，分险种统计，案件数量和金额求和
					+ PartPCRiskSum + ",(" + PCRiskSum + ")"
					+ PartPMRiskSum
					+ ",(" + PMRiskSum + PSRiskSum + ") from dual ";
			String tPubilcSQL = mStatSer.equals("01") ? " " + tPublicSQLPA
					+ " " : " " + tPublicSQLBX + " ";// 判断将要执行哪个SQL语句

			ExeSQL tExePublicSQL = new ExeSQL();
			SSRS tPublicSSRS = new SSRS();
			SSRS tRiskSumSSRS = new SSRS();
			if (mStatSer.equals("01") && mCTRiskCho.equals("01"))// 赔案层面，分险种统计时执行
			{
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(PublicRiskSum);
				sqlbv1.put("mngcode", tMngcode);
				sqlbv1.put("mngname", tMngname);
				sqlbv1.put("startdate", mStartDate);
				sqlbv1.put("enddate", mEndDate);
				tRiskSumSSRS = tExePublicSQL.execSQL(sqlbv1);
			}
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tPubilcSQL);
			sqlbv2.put("mngcode", tMngcode);
			sqlbv2.put("mngname", tMngname);
			sqlbv2.put("startdate", mStartDate);
			sqlbv2.put("enddate", mEndDate);
			tPublicSSRS = tExePublicSQL.execSQL(sqlbv2);
			int tMaxRow = tPublicSSRS.getMaxRow();
			for (int x = 1; x <= tMaxRow; x++) {
				logger.debug("-------------第 " + x
						+ " 次内层循环开始-----------");
				CTarr = new String[15];
				for (int k = 1; k <= 15; k++)// 为列表赋值
				{
					CTarr[k - 1] = tPublicSSRS.GetText(x, k);
				}
				if (mStatSer.equals("02") || mCTRiskCho.equals("02"))// 保项层面，或赔案层面不分险种统计时求各项数据和
				{
					for (int r = 1; r <= 12; r++) { // 求各项合计
						double a = Double.parseDouble(CTarr[r + 2]);
						double b = Double.parseDouble(CTarrAdd[r - 1]);
						CTarrAdd[r - 1] = String.valueOf(a + b);
					}
				}
				logger.debug("-------------第 " + x
						+ " 次内层循环结束-----------");
				logger.debug("*********************************************");
				tListTable.add(CTarr);
			}
			if ((mStatSer.equals("01")) && (mCTRiskCho.equals("01"))
					&& (mLevel.equals("05")))// 赔案层面，分险种统计，机构层面为用户时求和
			{
				for (int j = 1; j <= 12; j++) {
					double c = 0;
					c = Double.parseDouble(tRiskSumSSRS.GetText(1, (j + 3)));
					CTarrAdd[j - 1] = String.valueOf(c
							+ Double.parseDouble(CTarrAdd[j - 1])); // 针对多用户循环的付值
				}
			}
			if ((mStatSer.equals("01")) && (mCTRiskCho.equals("01"))
					&& (!mLevel.equals("05")))// 赔案层面，分险种统计，机构层面为非用户时求和
			{
				for (int j = 1; j <= 12; j++) {
					double e = 0;// Modify by zhaorx 2006-10-10
					e = Double.parseDouble(tRiskSumSSRS.GetText(1, (j + 3)));
					CTarrAdd[j - 1] = String.valueOf(e
							+ Double.parseDouble(CTarrAdd[j - 1])); // 循环累加求和
				}
			}
			logger.debug("-------------第 " + i + " 次外层循环结束--------------");
			logger.debug("*************************************************");
		}
		tListTableAdd.add(CTarrAdd);

		// 机构层面选项 $=/CTManage$
		tTextTag.add("CTManage", mLevelName);
		// 统计层面 $=/CTStat$
		tTextTag.add("CTStat", mStatSerName);
		// 统计口径 $=/CTStatAround$
		tTextTag.add("CTStatAround", mStatAroundName);
		// 统计时间段 $=/CTTimes$
		String tTimes = mStartDate + "至" + mEndDate;
		tTextTag.add("CTTimes", tTimes);
		// 机构统计范围 $=/CTStatSer$
		tTextTag.add("CTStatSer", mManageComName);
		// 险种选项：$=/CTRiskChoice$
		tTextTag.add("CTRiskChoice", mCTRiskChoName);
		// 统计时间,默认为系统时间 $=/CTDay$
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("CTDay", SysDate);
		// 申请类型: $=/CSApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		tTextTag.add("CTApplType", mNCLTypeName);
		// 定义列表标题，共15列
		String[] Title = new String[15];
		tXmlExport.addListTable(tListTable, Title);
		tXmlExport.addListTable(tListTableAdd, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath = "E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName = strTemplatePath + "ZHRX-CT-Test0929.vts";
		// //生成临时文件名
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);

		logger.debug("********************************************");
		logger.debug("---以下 将XmlExport打包，返回前台--");

		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("tXmlExport=" + tXmlExport);
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

	public CErrors getErrors() {
		return mErrors;
	}

	// 得到结果集
	public VData getResult() {
		return mResult;
	}

	// 主函数
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2005-11-27");
		tTransferData.setNameAndValue("EndDate", "2005-12-12");
		tTransferData.setNameAndValue("Level", "05"); // 机构层面 01 02 03 04 05
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("StatSer", "01"); // 统计层面 01 02
		tTransferData.setNameAndValue("StatAround", "03"); // 统计口径 01 02 03 04
															// 05 06 07
		tTransferData.setNameAndValue("CTRiskCho", "01"); // 险种选项 01 02

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRCaseTypeBL tLLPRRCaseTypeBL = new LLPRRCaseTypeBL();
		if (tLLPRRCaseTypeBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
		int n = tLLPRRCaseTypeBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRCaseTypeBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
