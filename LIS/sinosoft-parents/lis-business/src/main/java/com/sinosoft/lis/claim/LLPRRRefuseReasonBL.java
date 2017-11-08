package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

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
 * Description: 统计报表打印：拒付原因分析--LLPRRRefuseReason.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、机构统计范围选项（总公司、
 * 某分公司、某中支）、统计时间段、统计层面选项（赔案、保项）、案件类型选项（单个案件类型、 所有案件类型）、理赔类型选项（单个理赔类型、所有理赔类型）
 * 表头：报表名称、统计条件、统计日期 数据项：机构（或用户）、不构成赔偿条件、不如实告知、不在有效保障期内、非保险合同所载明的标的、
 * 非保险责任内损失、合同无效、免除责任、其他、索赔单证不齐备或无效、 索赔人不具有索赔权和伪造虚假单证或虚构保险事故的拒付数
 * 算法：按照选择的条件统计各项数据 排序：机构（或用户） 表尾：各项数据的合计 注：需要核赔处确认统计口径、统计层面
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author niuzj,zhaorx 2005-10-04
 * @version 1.0
 */

public class LLPRRRefuseReasonBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRRefuseReasonBL.class);
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

	private String mManageCom = ""; // 统计机构
	private String mLevel = ""; // 统计机构层面
	private String mStartDate = ""; // 统计起期
	private String mEndDate = ""; // 统计止期
	private String mStatLev = ""; // 统计层面
	private String mCaseType = ""; // 案件类型
	private String mClaimType = ""; // 理赔类型
	private String mRRStAroun = ""; // 统计口径
	private String mRRStArounName = "";
	private String mNCLType = ""; // 生请类型

	public LLPRRRefuseReasonBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------统计报表打印：拒付原因分析-----LLPRRRefuseReasonBL测试-----开始----------");
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
		logger.debug("----------统计报表打印：拒付原因分析-----LLPRRRefuseReasonBL测试-----结束----------");
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

		this.mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		this.mLevel = (String) mTransferData.getValueByName("Level"); // 统计机构层面
		this.mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		this.mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		this.mStatLev = (String) mTransferData.getValueByName("StatLev"); // 统计层面
		this.mCaseType = (String) mTransferData.getValueByName("CaseType"); // 案件类型
		this.mClaimType = (String) mTransferData.getValueByName("ClaimType"); // 理赔类型
		this.mRRStAroun = (String) mTransferData.getValueByName("RRStAroun"); // 统计口径
		this.mRRStArounName = (String) mTransferData
				.getValueByName("RRStArounName"); // 统计口径名
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
		tXmlExport.createDocument("LLPRRRefuseReason.vts", ""); // 所用模板名称
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("RR");
		ListTable tlistTableSum = new ListTable();// 用来求和的数组
		tlistTableSum.setName("RRAdd");
		// 求和数组
		String[] RRAdd = new String[22];
		for (int z = 0; z <= 21; z++) {
			RRAdd[z] = "0.00";
		}
		// 求和中间变量
		double tCenterV1 = 0.00;
		double tCenterV2 = 0.00;

		// llregister~RgtState(案件类型)--llappclaimreason~ReasonCode(赔案--理赔类型)--llclaimuwmain~auditnopassreason(拒付原因)
		// llregister~RgtState(案件类型)--llclaimdetail~GetDutyKind(保项--理赔类型)--llclaimdetail~GiveReason(拒付原因)

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
			tError.moduleName = "LLPRRCaseStateBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		for (int index = 1; index <= tSSRS.getMaxRow(); index++) {
			logger.debug("-------------第 " + index
					+ " 次外层循环开始--------------");
			String magcode = tSSRS.GetText(index, 1);
			String magname = tSSRS.GetText(index, 2);
			// 定义列表标题
			String[] stra = new String[24];
			for (int r = 0; r <= 23; r++) {
				stra[r] = "0";
			}
			stra[0] = magcode; // 机构（或用户）代码
			stra[1] = magname; // 机构(或用户)名

			// 拒付原因代码所对应的含义：
			// 01非保险合同所载明的标的 02不在有效保障期内 03不如实告知 04合同无效
			// 05不构成赔偿条件 06非保险责任内损失 07免除责任 08索赔单证不齐备或无效
			// 09索赔人不具有索赔权 10保险事故或理赔单证不真实 11其它（null）

			// 时间字符串
			String tTIMEs = " between '" + "?startdate?" + "' and '" + "?enddate?"
					+ "' ";
			String taRgtstate = mCaseType.equals("") ? " "
					: " and a.rgtstate ='" + "?casetype?" + "' ";// 案件类型
			String tcReasoncode = mClaimType.equals("") ? " "
					: " and c.reasoncode ='" + "?claimtype?" + "' ";// 理赔类型（赔案层面）
			String tbGetdutykind = mClaimType.equals("") ? " "
					: " and b.getdutykind = '" + "?claimtype?" + "' ";// 理赔类型（保项层面）
			String tRRStArounPA = mRRStAroun.equals("01") ? " where b.examdate "
					+ tTIMEs + " "
					: " where a.endcasedate " + tTIMEs + " ";// 统计口径条件（赔案层面）
			String tRRStArounBX = mRRStAroun.equals("01") ? " ,llclaimuwmain z where z.clmno=a.rgtno and a.rgtno=b.clmno and z.examdate "
					+ tTIMEs + " "
					: " where a.rgtno=b.clmno and a.endcasedate " + tTIMEs
							+ " ";// 统计口径条件（保项条件）
			String tClmState = mRRStAroun.equals("01") ? " and a.clmstate in ('50','60') "
					: " and a.clmstate='60' ";// 案件状态条件
			// 申请类型判断
			String tApplType = mNCLType.equals("1") ? " and a.rgtobj='1' "
					: " and a.rgtobj='2' ";
			String tPublicSQL = "";
			// 赔案层面
			if (mStatLev.equals("01")) {
				if (!mLevel.equals("05")) {
					tPublicSQL = " select b.auditnopassreason,count(distinct a.rgtno),(case when sum(d.declinepay) is null then 0 else sum(d.declinepay) end) "
							+ " from llregister a,llclaimuwmain b,llappclaimreason c,llclaim d "
							+ tRRStArounPA
							+ " and b.clmno=a.rgtno "
							+ " and b.clmno=c.rgtno "
							+ " and b.clmno=d.clmno "
							+ " and d.givetype='1' "
							+ tClmState
							+ tApplType
							+ taRgtstate
							+ tcReasoncode
							+ " and a.mngcom like concat('"
							+ "?magcode?"
							+ "','%')"
							+ " group by b.auditnopassreason ";
				} else {
					tPublicSQL = " select b.auditnopassreason,count(distinct a.rgtno),(case when sum(d.declinepay) is null then 0 else sum(d.declinepay) end) "
							+ " from llregister a,llclaimuwmain b,llappclaimreason c,llclaim d "
							+ tRRStArounPA
							+ " and b.clmno=a.rgtno "
							+ " and b.clmno=c.rgtno "
							+ " and b.clmno=d.clmno "
							+ " and d.givetype='1' "
							+ tClmState
							+ tApplType
							+ taRgtstate
							+ tcReasoncode
							+ " and a.Operator ='"
							+ "?magcode?" + "'" + " group by b.auditnopassreason ";
				}
			}
			// 保项层面
			else {
				if (!mLevel.equals("05")) {
					tPublicSQL = " select b.givereason,count(distinct a.rgtno),(case when sum(b.realpay) is null then 0 else sum(b.realpay) end) "
							+ " from llregister a,llclaimdetail b "
							+ tRRStArounBX
							+ tClmState
							+ tApplType
							+ " and b.givetype='1' "
							+ taRgtstate
							+ tbGetdutykind
							+ " and a.mngcom like concat('"
							+ "?magcode?"
							+ "','%')" + " group by b.givereason ";
				} else {
					tPublicSQL = " select b.givereason,count(distinct a.rgtno),(case when sum(b.realpay) is null then 0 else sum(b.realpay) end)"
							+ " from llregister a,llclaimdetail b "
							+ tRRStArounBX
							+ " and b.givetype='1' "
							+ tClmState
							+ tApplType
							+ taRgtstate
							+ tbGetdutykind
							+ " and a.Operator ='"
							+ "?magcode?"
							+ "'"
							+ " group by b.givereason ";
				}
			}
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tPublicSQL);
			sqlbv1.put("magcode", magcode);
			sqlbv1.put("claimtype", mClaimType);
			sqlbv1.put("casetype", mCaseType);
			sqlbv1.put("startdate", mStartDate);
			sqlbv1.put("enddate", mEndDate);
			ExeSQL ExePublicSQL = new ExeSQL();
			SSRS tSSR = new SSRS();
			tSSR = ExePublicSQL.execSQL(sqlbv1);
			for (int j = 1; j <= tSSR.getMaxRow(); j++) {
				logger.debug("-------------第 " + j
						+ " 次内层循环开始--------------");
				logger.debug("执行的SQL：" + tPublicSQL + "");
				String tENPR = tSSR.GetText(j, 1);
				String tENPRC = tSSR.GetText(j, 2);
				String tENPRM = tSSR.GetText(j, 3);
				if (!tENPR.equals("")) {
					int t = Integer.parseInt(tENPR);
					stra[2 * t] = tENPRC;
					stra[2 * t + 1] = tENPRM;
					// 求各项数据和
					tCenterV1 = Double.parseDouble(RRAdd[2 * t - 2])
							+ Double.parseDouble(tENPRC);
					tCenterV2 = Double.parseDouble(RRAdd[2 * t - 1])
							+ Double.parseDouble(tENPRM);
					RRAdd[2 * t - 2] = new DecimalFormat("0.00")
							.format(tCenterV1);
					RRAdd[2 * t - 1] = new DecimalFormat("0.00")
							.format(tCenterV2);
				}
				logger.debug("-------------第 " + j
						+ " 次内层循环结束--------------");
			}

			tListTable.add(stra);

			logger.debug("-------------第 " + index
					+ " 次外层循环结束--------------");
			logger.debug("********************************************");
			logger.debug("---以下 查询列表$*/RR/ROW/COL1内容，并为列表赋值--");
		}
		tlistTableSum.add(RRAdd);
		// 统计层面$=/RRStatic$
		String mmStatLev = mStatLev.equals("01") ? " 赔案层面 " : " 保项层面 ";
		tTextTag.add("RRStatic", mmStatLev);

		// 统计口径： $=/RRStAroun$
		tTextTag.add("RRStAroun", mRRStArounName);

		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 案件类型 $=/RRAJType$
		String mmCaseType = mCaseType.equals("") ? "全部类型" : " "
				+ tLLPRTPubFunBL.getLDCode("llrgttype", mCaseType) + " ";
		tTextTag.add("RRAJType", mmCaseType);

		// 理赔类型 $=/RRLPType$
		String mmClaimType = mClaimType.equals("") ? " 全部类型 " : " "
				+ tLLPRTPubFunBL.getLDCode("llclaimtype", mClaimType) + " ";
		tTextTag.add("RRLPType", mmClaimType);

		// 机构层面选项 $=/RRManage$
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
		tTextTag.add("RRManage", tLevelName);

		// 统计时间段 $=/RRStaTimes$
		String tTimes = mStartDate + "至" + mEndDate;
		tTextTag.add("RRStaTimes", tTimes);

		// 机构统计范围 $=/RRStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("RRStatSer", tMngCom);

		// 申请类型判断
		String tApplTypeName = mNCLType.equals("1") ? "个人" : "团体";
		tTextTag.add("RRApplType", tApplTypeName);

		// 统计时间,默认为系统时间$=/RRDay$
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("RRDay", SysDate);
		// 定义列表标题，共24列
		String[] Title = new String[1];
		Title[0] = "";

		tXmlExport.addListTable(tListTable, Title);
		tXmlExport.addListTable(tlistTableSum, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath = "E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName = strTemplatePath + "ZHRX--RR--Test1010.vts";
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

		tTransferData.setNameAndValue("StartDate", "2005-01-01"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-11-01"); // 统计止期
		tTransferData.setNameAndValue("Level", "02"); // 统计机构层面--01 02 03 04
														// 05
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("StatLev", "01"); // 统计层面--01 02
		tTransferData.setNameAndValue("CaseType", ""); // 案件类型--11
		tTransferData.setNameAndValue("ClaimType", ""); // 理赔类型--100 102 103 200
		tTransferData.setNameAndValue("RRStAroun", "01"); // 统计口径--01 02

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRRefuseReasonBL tLLPRRRefuseReasonBL = new LLPRRRefuseReasonBL();
		if (tLLPRRRefuseReasonBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：拒付原因分析---失败----");
		}
		int n = tLLPRRRefuseReasonBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRRefuseReasonBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
