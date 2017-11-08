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
 * Description: 理赔报表打印：赔案状态汇总表--LLPRRCaseStateBL.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、 机构统计范围选项（总公司、某分公司、某中支）、统计时间段
 * 表头：报表名称、统计条件、统计日期 数据项：机构（或用户）、报案件数、立案件数、审核件数、审批件数、调查件数、结案的案件数量、
 * 结案的案件数量、给付件数、关闭件数（包括不予立案、客户撤案、公司撤案等）、案件总数
 * 算法：按照选择的条件下分别统计对应的报案日期、立案日期等在统计时间段的数据 排序：机构（或用户） 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhaorx，2005-9-22 10:08 modify by wanzh 2005/11/21
 * @version 1.0
 */
public class LLPRRCaseStateBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRCaseStateBL.class);
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
	private String mManageCom = ""; // 机构统计范围选项
	private String mNCLType = ""; // 申请类型

	public LLPRRCaseStateBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-----统计报表打印：赔案状态汇总表-----LLPRRCaseStateBL测试-----开始-----");
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
		logger.debug("-----统计报表打印：赔案状态汇总表-----LLPRRCaseStateBL测试-----结束------");

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
		this.mLevel = (String) mTransferData.getValueByName("Level"); // 统计层面
		this.mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		this.mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 新建一个TextTag的实例
		TextTag texttag = new TextTag();
		// 新建一个XmlExport的实例
		XmlExport xmlexport = new XmlExport();
		// 所用模板名称
		xmlexport.createDocument("LLPRRCaseState.vts", "");
		// 定义列表标题，共10列
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
		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tlistTable = new ListTable();
		tlistTable.setName("CS");

		String strSql1 = ""; // 报案件数llreport--rptdate
		String strSql2 = ""; // 立案件数llregister--rgtdate
		String strSql3 = ""; // 审核件数llclaimuwmain--auditdate
		String strSql4 = ""; // 审批件数llclaimuwmain--ExamDate
		String strSql5 = ""; // 调查件数llInqApply--applydate
		String strSql6 = ""; // 结案的案件数量llclaim--clmstate = '60'--EndCaseDate
		String strSql7 = ""; // 给付件数llclaim,ljaget--a.clmno=b.otherno--b.othernotype='5'--b.enteraccdate
		String strSql8 = ""; // 关闭件数llregister--clmstate = '70'--EndCaseDate
		String strSql9 = ""; // 案件总数
		String strSql10 = ""; // 拒付件数

		int tCSAdd1 = 0; // 报案总件数
		int tCSAdd2 = 0; // 立案总件数
		int tCSAdd3 = 0; // 审核总件数
		int tCSAdd4 = 0; // 审批总件数
		int tCSAdd5 = 0; // 调查总件数
		int tCSAdd6 = 0; // 结案案总件数量
		int tCSAdd7 = 0; // 给付总件数
		int tCSAdd8 = 0; // 关闭总件数
		int tCSAdd9 = 0; // 所有案件总数
		int tCSAdd10 = 0; // 拒付总件数

		// int strSql9 = 0; //案件总数
		// 申请类型判断
		String tApplType128 = "";// Sql1,2中报,立案,关闭件数条件
		String tApplTypeOther = "";

		String tTIMEs = " between '" + "?startdate?" + "' and '" + "?enddate?" + "' ";// 统计时间段
		String strLevelSql = "";
		if (!mLevel.equals("05"))// 机构层面选项为非用户
		{
			strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and comgrade='"
					+ "?level?"
					+ "' order by ComCode ";
		} else {
			strLevelSql = " select usercode,UserName from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%') order by usercode ";
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom",mManageCom);
		sqlbv.put("level",mLevel);
		sqlbv.put("startdate",mStartDate);
		sqlbv.put("enddate",mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		// 判断是否查询到管理机构,没查到时提示"没有要统计的数据",例:ManageCom=8632,而Level=01
		if (tSSRS.getMaxRow() <= 0) {
			CError tError = new CError();
			tError.moduleName = "LLPRRCaseStateBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		ExeSQL tExeSQL = new ExeSQL();
		for (int index = 1; index <= tSSRS.getMaxRow(); index++) {
			logger.debug("-------------第 " + index
					+ " 次循环开始--------------");
			String magcode = tSSRS.GetText(index, 1);
			String magname = tSSRS.GetText(index, 2);
			String tMngcomorOperator = mLevel.equals("05") ? " and a.Operator = '"
					+ "?magcode?" + "' "
					: " and a.mngcom like concat('" + "?magcode?" + "','%') ";// 机构或用户条件
			String tMngcomorOperatorSH = mLevel.equals("05") ? " and a.auditper = '"
					+ "?magcode?" + "' "
					: " and a.mngcom like concat('" + "?magcode?" + "','%') ";// 机构或用户条件(审核)
			String tMngcomorOperatorSP = mLevel.equals("05") ? " and a.examper = '"
					+ "?magcode?" + "' "
					: " and a.mngcom like concat('" + "?magcode?" + "','%') ";// 机构或用户条件(审批)
			String tMngcomorOperatorDC = mLevel.equals("05") ? " and a.InqPer = '"
					+ "?magcode?" + "' "
					: " and a.InqDept like concat('" + "?magcode?" + "','%') ";// 机构或用户条件(调查)
			// 申请类型判断
			tApplType128 = mNCLType.trim().equals("1") ? " and a.rgtobj='1' "
					: " and a.rgtobj='2' ";// Sql1,2中报,立案,关闭件数条件
			tApplTypeOther = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=a.clmno and llregister.rgtobj='1') "
					: " and exists (select 'X' from llregister where llregister.rgtno=a.clmno and llregister.rgtobj='2') ";

			// 报案件数
			strSql1 = " select count(*) from llreport a where 1=1 "
					+ tApplType128 + " and a.rptdate " + tTIMEs
					+ tMngcomorOperator;
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSql1);
			sqlbv1.put("magcode", magcode);
			String tstrSql1 = tExeSQL.getOneValue(sqlbv1);
			// 立案件数
			strSql2 = " select count(*) from llregister a where 1=1 "
					+ tApplType128 + " and a.rgtdate " + tTIMEs
					+ tMngcomorOperator;
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSql2);
			sqlbv2.put("magcode", magcode);
			String tstrSql2 = tExeSQL.getOneValue(sqlbv2);
			// 审核件数
			strSql3 = " select count(*) from llclaimuwmain a where 1=1 "
					+ tApplTypeOther + " and a.auditdate " + tTIMEs
					+ tMngcomorOperatorSH;
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSql3);
			sqlbv3.put("magcode", magcode);
			String tstrSql3 = tExeSQL.getOneValue(sqlbv3);
			// 审批件数
			strSql4 = " select count(*) from llclaimuwmain a where 1=1 "
					+ tApplTypeOther + " and a.ExamDate " + tTIMEs
					+ tMngcomorOperatorSP;
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(strSql4);
			sqlbv4.put("magcode", magcode);
			String tstrSql4 = tExeSQL.getOneValue(sqlbv4);
			// 调查件数
			strSql5 = " select count(distinct clmno) from llInqApply a where 1=1 "
					+ tApplTypeOther
					+ " and a.applydate "
					+ tTIMEs
					+ tMngcomorOperatorDC;
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(strSql5);
			sqlbv5.put("magcode", magcode);
			String tstrSql5 = tExeSQL.getOneValue(sqlbv5);
			// 结案的案件数量llclaim--clmstate = '60'--EndCaseDate
			strSql6 = " select count(*) from llclaim a where 1=1 "
					+ tApplTypeOther + " and a.clmstate = '60' "
					+ " and a.EndCaseDate " + tTIMEs + tMngcomorOperator;
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(strSql6);
			sqlbv6.put("magcode", magcode);
			String tstrSql6 = tExeSQL.getOneValue(sqlbv6);
			// 给付件数llclaim,ljaget--a.clmno=b.otherno--b.othernotype='5'--b.enteraccdate
			strSql7 = " select count(*) from llclaim a,ljaget b where 1=1 "
					+ tApplTypeOther + " and a.givetype = '0' "
					+ " and a.clmno = b.otherno " + " and b.othernotype='5' "
					+ " and b.enteraccdate " + tTIMEs + tMngcomorOperator;
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(strSql7);
			sqlbv7.put("magcode", magcode);
			String tstrSql7 = tExeSQL.getOneValue(sqlbv7);

			// 拒付件数
			strSql10 = " select count(*) from llclaim a where 1 = 1 "
					+ tApplTypeOther + " and a.givetype = '1' "
					+ " and a.endcasedate " + tTIMEs + tMngcomorOperator;
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(strSql10);
			sqlbv8.put("magcode", magcode);
			String tstrSql10 = tExeSQL.getOneValue(sqlbv8);

			// 关闭件数llregister--clmstate = '70'--EndCaseDate
			strSql8 = " select count(*) from llregister a where 1=1 "
					+ tApplType128 + " and a.clmstate = '70' "
					+ " and a.EndCaseDate " + tTIMEs + tMngcomorOperator;
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(strSql8);
			sqlbv9.put("magcode", magcode);
			String tstrSql8 = tExeSQL.getOneValue(sqlbv9);
			// 案件总数
			if (!mLevel.equals("05")) {
				strSql9 = " select a.clmno from llclaimuwmain a where ((a.auditdate "
						+ tTIMEs
						+ " and a.mngcom like concat('"
						+ "?magcode?"
						+ "','%')) "// 审核件数与审批件数和
						+ " or (a.ExamDate "
						+ tTIMEs
						+ " and a.mngcom like concat('"
						+ "?magcode?"
						+ "','%'))) "
						+ tApplTypeOther
						+ " union select a.rgtno from llregister a where ((a.rgtdate "
						+ tTIMEs
						+ " and a.mngcom like concat('"
						+ "?magcode?"
						+ "','%')) "// 立案件数与关闭件数和
						+ " or (a.EndCaseDate "
						+ tTIMEs
						+ " and a.clmstate = '70' and a.mngcom like concat('"
						+ "?magcode?"
						+ "','%'))) "
						+ tApplType128
						+ " union select a.rptno from llreport a where a.rptdate "
						+ tTIMEs// 报案件数
						+ " and a.mngcom like concat('"
						+ "?magcode?"
						+ "','%') "
						+ tApplType128
						+ " union select distinct a.clmno from llInqApply a where a.applydate "
						+ tTIMEs// 调查件数
						+ " and a.InqDept like concat('"
						+ "?magcode?"
						+ "','%') "
						+ tApplTypeOther
						+ " union select a.clmno from llclaim a,ljaget b where a.givetype = '0' and a.clmno = b.otherno "// 给付件数
						+ " and b.othernotype='5' and b.enteraccdate "
						+ tTIMEs
						+ " and a.mngcom like concat('"
						+ "?magcode?"
						+ "','%') "
						+ tApplTypeOther
						+ " union select a.clmno from llclaim a where a.givetype = '1' "// 拒付件数
						+ " and a.endcasedate "
						+ tTIMEs
						+ " and a.mngcom like concat('"
						+ "?magcode?"
						+ "','%') "
						+ tApplTypeOther
						+ " union select a.clmno from llclaim a where a.clmstate = '60' and a.EndCaseDate "
						+ tTIMEs// 结案的案件数量
						+ " and a.mngcom like concat('"
						+ "?magcode?"
						+ "','%') "
						+ tApplTypeOther;
			} else {
				strSql9 = " select a.clmno from llclaimuwmain a where ((a.auditdate "
						+ tTIMEs
						+ " and a.auditper = '"
						+ "?magcode?"
						+ "') "// 审核件数与审批件数和
						+ " or (a.ExamDate "
						+ tTIMEs
						+ " and a.examper = '"
						+ "?magcode?"
						+ "')) "
						+ tApplTypeOther
						+ " union select a.rgtno from llregister a where ((a.rgtdate "
						+ tTIMEs
						+ " and a.Operator = '"
						+ "?magcode?"
						+ "') "// 立案件数与关闭件数和
						+ " or (a.EndCaseDate "
						+ tTIMEs
						+ " and a.clmstate = '70' and a.Operator = '"
						+ "?magcode?"
						+ "')) "
						+ tApplType128
						+ " union select a.rptno from llreport a where a.rptdate "
						+ tTIMEs// 报案件数
						+ " and a.Operator = '"
						+ "?magcode?"
						+ "' "
						+ tApplType128
						+ " union select distinct a.clmno from llInqApply a where a.applydate "
						+ tTIMEs// 调查件数
						+ " and a.InqPer = '"
						+ "?magcode?"
						+ "' "
						+ tApplTypeOther
						+ " union select a.clmno from llclaim a,ljaget b where a.givetype = '0' and a.clmno = b.otherno "// 给付件数
						+ " and b.othernotype='5' and b.enteraccdate "
						+ tTIMEs
						+ " and a.Operator = '"
						+ "?magcode?"
						+ "' "
						+ tApplTypeOther
						+ " union select a.clmno from llclaim a where a.givetype = '1' "// 拒付件数
						+ " and a.endcasedate "
						+ tTIMEs
						+ " and a.Operator = '"
						+ "?magcode?"
						+ "' "
						+ tApplTypeOther
						+ " union select a.clmno from llclaim a where a.clmstate = '60' and a.EndCaseDate "
						+ tTIMEs// 结案的案件数量
						+ " and a.Operator = '"
						+ "?magcode?"
						+ "'  "
						+ tApplTypeOther;
			}
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(strSql9);
			sqlbv10.put("magcode", magcode);
			sqlbv10.put("startdate",mStartDate);
			sqlbv10.put("enddate",mEndDate);
			SSRS tSSRS9 = new SSRS();
			tSSRS9 = tExeSQL.execSQL(sqlbv10);
			int tstrSql9 = tSSRS9.getMaxRow();
			// 定义列表标题
			String[] stra = new String[12];
			stra[0] = magname; // 机构(或用户)
			stra[1] = tstrSql1; // 报案件数
			stra[2] = tstrSql2; // 立案件数
			stra[3] = tstrSql3; // 审核件数
			stra[4] = tstrSql4; // 审批件数
			stra[5] = tstrSql5; // 调查件数
			stra[6] = tstrSql6; // 结案的案件数量
			stra[7] = tstrSql7; // 给付件数
			stra[8] = tstrSql8; // 关闭件数
			stra[9] = String.valueOf(tstrSql9); // 案件总数
			stra[10] = magcode; // 机构（或用户）代码
			stra[11] = tstrSql10; // 拒付件数
			tlistTable.add(stra);

			// 报案总件数
			tCSAdd1 = tCSAdd1 + Integer.parseInt(tstrSql1);
			// 立案总件数
			tCSAdd2 = tCSAdd2 + Integer.parseInt(tstrSql2);
			// 审核总件数
			tCSAdd3 = tCSAdd3 + Integer.parseInt(tstrSql3);
			// 审批总件数
			tCSAdd4 = tCSAdd4 + Integer.parseInt(tstrSql4);
			// 调查总件数
			tCSAdd5 = tCSAdd5 + Integer.parseInt(tstrSql5);
			// 结案案总件数量
			tCSAdd6 = tCSAdd6 + Integer.parseInt(tstrSql6);
			// 给付总件数
			tCSAdd7 = tCSAdd7 + Integer.parseInt(tstrSql7);
			// 拒付总件数
			tCSAdd10 = tCSAdd10 + Integer.parseInt(tstrSql10);
			// 关闭总件数
			tCSAdd8 = tCSAdd8 + Integer.parseInt(tstrSql8);
			// //所有案件总数
			// tCSAdd9 = tCSAdd9 + tstrSql9;

			logger.debug("-------------第 " + index
					+ " 次循环结束--------------");
			logger.debug("*************************************************");
		}

		// 案件总数(右下角的最终求和)
		logger.debug("-------------开始执行右下角求和的SQL-----------------");
		String tRightDown = "";
		String a = mLevel.substring(1, 2);
		if (!mLevel.equals("05")) {
			tRightDown = " select a.clmno from llclaimuwmain a where ((a.auditdate "
					+ tTIMEs
					+ " and substr(a.mngcom,1,2*"
					+ "?num?"
					+ ") in (select trim(ComCode) from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and comgrade='"
					+ "?level?"
					+ "')) "// 审核件数与审批件数和
					+ " or (a.ExamDate "
					+ tTIMEs
					+ " and substr(a.mngcom,1,2*"
					+ "?num?"
					+ ") in (select trim(ComCode) from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and comgrade='"
					+ "?level?"
					+ "'))) "
					+ tApplTypeOther
					+ " union select a.rgtno from llregister a where ((a.rgtdate "
					+ tTIMEs
					+ " and substr(a.mngcom,1,2*"
					+ "?num?"
					+ ") in (select trim(ComCode) from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and comgrade='"
					+ "?level?"
					+ "')) "// 立案件数与关闭件数和
					+ " or (a.EndCaseDate "
					+ tTIMEs
					+ " and a.clmstate = '70' and substr(a.mngcom,1,2*"
					+ "?num?"
					+ ") in (select trim(ComCode) from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and comgrade='"
					+ "?level?"
					+ "'))) "
					+ tApplType128
					+ " union select a.rptno from llreport a where a.rptdate "
					+ tTIMEs// 报案件数
					+ " and substr(a.mngcom,1,2*"
					+ "?num?"
					+ ") in (select trim(ComCode) from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and comgrade='"
					+ "?level?"
					+ "') "
					+ tApplType128
					+ " union select distinct a.clmno from llInqApply a where a.applydate "
					+ tTIMEs// 调查件数
					+ " and substr(a.InqDept,1,2*"
					+ "?num?"
					+ ") in (select trim(ComCode) from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and comgrade='"
					+ "?level?"
					+ "') "
					+ tApplTypeOther
					+ " union select a.clmno from llclaim a,ljaget b where a.givetype = '0' and a.clmno = b.otherno "// 给付件数
					+ " and b.othernotype='5' and b.enteraccdate "
					+ tTIMEs
					+ " and substr(a.mngcom,1,2*"
					+ "?num?"
					+ ") in (select trim(ComCode) from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and comgrade='"
					+ "?level?"
					+ "') "
					+ tApplTypeOther
					+ " union select a.clmno from llclaim a where a.givetype = '1' "// 拒付件数
					+ " and a.endcasedate "
					+ tTIMEs
					+ " and substr(a.mngcom,1,2*"
					+ "?num?"
					+ ") in (select trim(ComCode) from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and comgrade='"
					+ "?level?"
					+ "') "
					+ tApplTypeOther
					+ " union select a.clmno from llclaim a where a.clmstate = '60' and a.EndCaseDate "
					+ tTIMEs// 结案的案件数量
					+ " and substr(a.mngcom,1,2*"
					+ "?num?"
					+ ") in (select trim(ComCode) from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and comgrade='"
					+ "?level?"
					+ "') "
					+ tApplTypeOther;
		} else {
			tRightDown = " select a.clmno from llclaimuwmain a where ((a.auditdate "
					+ tTIMEs
					+ " and a.auditper in (select usercode from llclaimuser where ComCode like concat('"
					+ mManageCom
					+ "','%'))) "// 审核件数与审批件数和
					+ " or (a.ExamDate "
					+ tTIMEs
					+ " and a.examper in (select usercode from llclaimuser where ComCode like concat('"
					+ "?mngcom?"
					+ "','%')))) "
					+ tApplTypeOther
					+ " union select a.rgtno from llregister a where ((a.rgtdate "
					+ tTIMEs
					+ " and a.Operator in (select usercode from llclaimuser where ComCode like concat('"
					+ "?mngcom?"
					+ "','%'))) "// 立案件数与关闭件数和
					+ " or (a.EndCaseDate "
					+ tTIMEs
					+ " and a.clmstate = '70' and a.Operator in (select usercode from llclaimuser where ComCode like concat('"
					+ "?mngcom?"
					+ "','%')))) "
					+ tApplType128
					+ " union select a.rptno from llreport a where a.rptdate "
					+ tTIMEs// 报案件数
					+ " and a.Operator in (select usercode from llclaimuser where ComCode like concat('"
					+ "?mngcom?"
					+ "','%')) "
					+ tApplType128
					+ " union select distinct a.clmno from llInqApply a where a.applydate "
					+ tTIMEs// 调查件数
					+ " and a.InqPer in (select usercode from llclaimuser where ComCode like concat('"
					+ "?mngcom?"
					+ "','%')) "
					+ tApplTypeOther
					+ " union select a.clmno from llclaim a,ljaget b where a.givetype = '0' and a.clmno = b.otherno "// 给付件数
					+ " and b.othernotype='5' and b.enteraccdate "
					+ tTIMEs
					+ " and a.Operator in (select usercode from llclaimuser where ComCode like concat('"
					+ "?mngcom?"
					+ "','%')) "
					+ tApplTypeOther
					+ " union select a.clmno from llclaim a where a.givetype = '1' "// 拒付件数
					+ " and a.endcasedate "
					+ tTIMEs
					+ " and a.Operator in (select usercode from llclaimuser where ComCode like concat('"
					+ "?mngcom?"
					+ "','%')) "
					+ tApplTypeOther
					+ " union select a.clmno from llclaim a where a.clmstate = '60' and a.EndCaseDate "
					+ tTIMEs// 结案的案件数量
					+ " and a.Operator in (select usercode from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%')) " + tApplTypeOther;
		}
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(tRightDown);
		sqlbv11.put("num", a);
		sqlbv11.put("mngcom", mManageCom);
		sqlbv11.put("level", mLevel);
		sqlbv11.put("startdate",mStartDate);
		sqlbv11.put("enddate",mEndDate);
		SSRS tSSRSRD = new SSRS();
		tSSRSRD = tExeSQL.execSQL(sqlbv11);
		tCSAdd9 = tSSRSRD.getMaxRow();
		logger.debug("----------------右下角求和SQL执行结束！---------------");
		texttag.add("CSAdd1", tCSAdd1); // 报案总件数
		texttag.add("CSAdd2", tCSAdd2); // 立案总件数
		texttag.add("CSAdd3", tCSAdd3); // 审核总件数
		texttag.add("CSAdd4", tCSAdd4); // 审批总件数
		texttag.add("CSAdd5", tCSAdd5); // 调查总件数
		texttag.add("CSAdd6", tCSAdd6); // 结案案总件数量
		texttag.add("CSAdd7", tCSAdd7); // 给付总件数
		texttag.add("CSAdd8", tCSAdd8); // 关闭总件数
		texttag.add("CSAdd9", tCSAdd9); // 所有案件总数
		texttag.add("CSAdd10", tCSAdd10);// 拒付总件数

		// 机构层面选项 $=/CSManage$
		String tLevelName = "";
		switch (Integer.parseInt(mLevel)) {
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
			tLevelName = "用户";
			break;
		default:
			tLevelName = "";
			break;
		}
		texttag.add("CSManage", tLevelName);
		// 统计时间段 $=/CSStatTimes$
		String tTimes = mStartDate + "至" + mEndDate;
		texttag.add("CSStatTimes", tTimes);
		// 机构统计范围 $=/CSStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		texttag.add("CSStatSer", tMngCom);
		// 统计时间,默认为系统时间$=/CSDay$
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		texttag.add("CSDay", SysDate);
		// 申请类型: $=/CSApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		texttag.add("CSApplType", mNCLTypeName);

		xmlexport.addListTable(tlistTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		logger.debug("********************************************");
		logger.debug("---以下 将XmlExport打包，返回前台--");

		mResult.clear();
		mResult.addElement(xmlexport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + xmlexport);

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
		tTransferData.setNameAndValue("StartDate", "2008-05-01");
		tTransferData.setNameAndValue("EndDate", "2008-05-01");
		tTransferData.setNameAndValue("Level", "02");// 01 02 03 04 05
		tTransferData.setNameAndValue("ManageCom", "8621");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRCaseStateBL tLLPRRCaseStateBL = new LLPRRCaseStateBL();
		if (tLLPRRCaseStateBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
		int n = tLLPRRCaseStateBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRCaseStateBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
