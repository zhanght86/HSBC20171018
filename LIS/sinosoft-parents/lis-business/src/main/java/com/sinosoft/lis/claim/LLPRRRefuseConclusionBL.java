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
 * Description: 统计报表打印：拒付结论分析--LLPRRRefuseConclusion.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、机构统计范围选项（总公司、
 * 某分公司、某中支）、统计口径（审批通过日期和结案日期）,统计时间段、案件类型选项（单个案件类型、所有案件类型）、
 * 理赔类型选项（单个理赔类型、所有理赔类型）、统计层面为保项层面,险种选项（分险种、不分险种） 表头：报表名称、统计条件、统计日期
 * 数据项：机构（或用户）、险种,拒付、拒付 and 主险或附险责任终止、拒付 and主险或附险责任终止 and 退还保单现金价值、
 * 拒付and主险或附险责任终止 and 退还保费的拒赔数量、所占权重 算法：按照选择的条件统计各项数据 排序：机构（或用户） 表尾：各项数据的合计
 * ***统计口径为结案时间***
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author niuzj,zhaorx 2005-10-10
 * @version 1.0
 */

public class LLPRRRefuseConclusionBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRRefuseConclusionBL.class);
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
	private String mCaseType = ""; // 案件类型
	private String mClaimType = ""; // 理赔类型
	private String mRCStAroun = ""; // 统计口径
	private String mRCStArounName = "";
	private String mRCRiskChan = ""; // 险种选项
	private String mRCRiskChanName = "";
	private String mNCLType = ""; // 申请类型

	public LLPRRRefuseConclusionBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-----统计报表打印：拒付结论分析---LLPRRRefuseConclusionBL测试---开始-----");
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
		logger.debug("-----统计报表打印：拒付结论分析---LLPRRRefuseConclusionBL测试---结束-----");
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
		this.mCaseType = (String) mTransferData.getValueByName("CaseType"); // 案件类型
		this.mClaimType = (String) mTransferData.getValueByName("ClaimType"); // 理赔类型
		this.mRCStAroun = (String) mTransferData.getValueByName("RCStAroun"); // 统计口径
		this.mRCStArounName = (String) mTransferData
				.getValueByName("RCStArounName");
		this.mRCRiskChan = (String) mTransferData.getValueByName("RCRiskChan"); // 险种选项
		this.mRCRiskChanName = (String) mTransferData
				.getValueByName("RCRiskChanName");
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
		tXmlExport.createDocument("LLPRRRefuseConclusion.vts", ""); // 所用模板名称
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("RC");

		// 定义列表标题，共10列
		String[] Title = new String[10];
		Title[0] = "";

		int tRCAdd1 = 0;
		int tRCAdd2 = 0;
		int tRCAdd4 = 0;
		int tRCAdd6 = 0;
		double tRCAdd3 = 0;
		double tRCAdd5 = 0;
		double tRCAdd7 = 0;
		String tLevelSql = "";
		if (!mLevel.equals("05"))// 机构层面选项为非用户
		{
			tLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
					+ "?mngcom?" + "','%') and trim(comgrade)='" + "?level?"
					+ "' order by ComCode ";
		} else {
			tLevelSql = " select usercode,UserName from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%') order by usercode ";
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tLevelSql);
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
		// 定义以下for循环中用到的字符串
		String tRCStArounTa = mRCStAroun.equals("01") ? " llclaimuwmain e,llregister a "
				: " llregister a ";// 统计口径表字段
		String tRCStArounTi = mRCStAroun.equals("01") ? " and e.ExamConclusion='0' and e.examdate "
				: " and a.endcaseflag='1' and a.endcasedate ";// 统计口径时间字段
		String tRCStArounCo = mRCStAroun.equals("01") ? " e.clmno = a.rgtno and "
				: "  ";// 统计口径表关联字段and d.clmno = d.clmno a.rgtno = d.clmno
		String tRiskChSQL = mRCRiskChan.equals("01") ? " ,substr(f.riskcode, 3, 3) from llclaimdetail f group by f.riskcode "
				: " ,'不分险种' from dual ";// 险种选项条件_SQL中结尾部分
		String tRiskChCSQL = mRCRiskChan.equals("01") ? " and b.riskcode = f.riskcode "
				: "";// 险种选项条件_中间连接子字符串
		// 申请类型判断
		String tApplType = mNCLType.equals("1") ? " a.rgtobj='1' and "
				: " a.rgtobj='2' and ";
		String tXPublicSQL = " select count(1) from "
				+ tRCStArounTa
				+ ", llclaimdetail b  "// , llclaim d
				+ " where " + tRCStArounCo + tApplType
				+ " a.rgtno = b.clmno  "
				+ tRiskChCSQL// d.clmno
				+ tRCStArounTi + " between '" + "?startdate?" + "' and '"
				+ "?enddate?" + "' " + " and b.givetype='1' ";// 子SQL-X字符串
		String tZPublicSQL = " select count(1) from "
				+ tRCStArounTa
				+ ", llclaimdetail b, llcontstate c "// ,llclaim d
				+ " where "
				+ tRCStArounCo
				+ tApplType
				+ " a.rgtno = c.clmno and a.rgtno = b.clmno and b.polno = c.polno "
				+ tRiskChCSQL// d.clmno
				+ tRCStArounTi + " between '" + "?startdate?" + "' and '"
				+ "?enddate?" + "' " + " and b.givetype='1' ";// 子SQL-Z字符串
		String tRgtStateSQL = mCaseType.equals("") ? "" : " and a.rgtstate='"
				+ "?casetype?" + "'"; // 案件类型选项条件
		String tGetDutyKindSQL = mClaimType.equals("") ? ""
				: " and b.getdutykind='" + "?claimtype?" + "'"; // 理赔类型选项条件

		for (int index = 1; index <= tSSRS.getMaxRow(); index++) {
			logger.debug("-------------第 " + index
					+ " 次外层循环开始--------------");
			String magcode = tSSRS.GetText(index, 1);
			String magname = tSSRS.GetText(index, 2);
			String tMng_Operat = mLevel.equals("05") ? " and a.operator = '"
					+ magcode + "' " : " and a.mngcom like concat('" + "?magcode?" + "','%') ";// 机构或用户条件
			String tPPublicSQL = " select "// 公共SQL字符串
					+ " (" + tXPublicSQL
					+ tRgtStateSQL
					+ tGetDutyKindSQL
					+ tMng_Operat
					+ " ), "// 拒付总数
					+ " (" + tZPublicSQL + " and c.dealstate='D01' "
					+ tRgtStateSQL + tGetDutyKindSQL
					+ tMng_Operat
					+ "), " // 解除合同退还保费
					+ " (" + tZPublicSQL + " and c.dealstate='D02' "
					+ tRgtStateSQL + tGetDutyKindSQL + tMng_Operat
					+ "), " // 解除合同退还现金价值
					+ " (" + tZPublicSQL + " and c.dealstate in ('D03','D04') "
					+ tRgtStateSQL + tGetDutyKindSQL + tMng_Operat + ") " // 解除合同不退费
																			// 和
																			// 合同终止
					+ tRiskChSQL;
			logger.debug("" + tPPublicSQL + "");
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tPPublicSQL);
			sqlbv1.put("magcode", magcode);
			sqlbv1.put("claimtype", mClaimType);
			sqlbv1.put("casetype", mCaseType);
			sqlbv1.put("startdate", mStartDate);
			sqlbv1.put("enddate", mEndDate);
			ExeSQL ExePPublicSQL = new ExeSQL();
			SSRS tPPub = new SSRS();
			tPPub = ExePPublicSQL.execSQL(sqlbv1);
			for (int j = 1; j <= tPPub.getMaxRow(); j++) {
				logger.debug("-------------第 " + j
						+ " 次内层循环开始--------------");
				String tRFCIADD = tPPub.GetText(j, 1);
				String tRFCID01 = tPPub.GetText(j, 2);
				String tRFCID02 = tPPub.GetText(j, 3);
				String tRFCID34 = tPPub.GetText(j, 4);
				String tRiskCode = tPPub.GetText(j, 5);
				String tDRFCID01 = "0.00";
				String tDRFCID02 = "0.00";
				String tDRFCID34 = "0.00";
				if (!tRFCIADD.equals("0"))// 判断拒付数（分母）是否为零
				{
					tDRFCID01 = new DecimalFormat("0.00").format(Double
							.parseDouble(tRFCID01)
							/ Double.parseDouble(tRFCIADD));// 求各项权重并取两位小数
					tDRFCID02 = new DecimalFormat("0.00").format(Double
							.parseDouble(tRFCID02)
							/ Double.parseDouble(tRFCIADD));
					tDRFCID34 = new DecimalFormat("0.00").format(Double
							.parseDouble(tRFCID34)
							/ Double.parseDouble(tRFCIADD));
				}

				String[] stra = new String[10];
				stra[0] = tRiskCode; // 险种
				stra[1] = tRFCIADD; // 拒付数量
				stra[2] = tRFCID34; // 拒付and主险或附险责任终止数量（D03，D04）
				stra[3] = tDRFCID34; // 拒付and主险或附险责任终止数量权重
				stra[4] = tRFCID02; // 拒付and主险或附险责任终止and退还保单现金价值数量（D02）
				stra[5] = tDRFCID02; // 拒付and主险或附险责任终止and退还保单现金价值数量权重
				stra[6] = tRFCID01; // 拒付and主险或附险责任终止and退还保费数量（D01）
				stra[7] = tDRFCID01; // 拒付and主险或附险责任终止and退还保费数量权重
				stra[8] = magcode; // 机构（或用户）代码
				stra[9] = magname; // 机构（或用户）名字
				tListTable.add(stra);

				tRCAdd1 = tRCAdd1 + Integer.parseInt(tRFCIADD);
				tRCAdd2 = tRCAdd2 + Integer.parseInt(tRFCID34);
				tRCAdd4 = tRCAdd4 + Integer.parseInt(tRFCID02);
				tRCAdd6 = tRCAdd6 + Integer.parseInt(tRFCID01);
				logger.debug("内层SQL = " + tPPublicSQL + "");
				logger.debug("-------------第 " + j
						+ " 次内层循环结束--------------");
			}
			logger.debug("-------------第 " + index
					+ " 次外层循环结束--------------");
			logger.debug("********************************************");
			logger.debug("---以下 查询列表$*/RFC/ROW/COL1内容，并为列表赋值--");
		}

		// 求各项数据合计一行中所对应的各项权重
		String sRCAdd3 = "0.00";
		String sRCAdd5 = "0.00";
		String sRCAdd7 = "0.00";
		if (tRCAdd1 != 0)// 判断拒付总数（分母）是否为零
		{
			// 相除得到总权重
			tRCAdd3 = (double) tRCAdd2 / (double) tRCAdd1;
			tRCAdd5 = (double) tRCAdd4 / tRCAdd1;
			tRCAdd7 = (double) tRCAdd6 / tRCAdd1;
			sRCAdd3 = new DecimalFormat("0.00").format(tRCAdd3);// 取两位小数
			sRCAdd5 = new DecimalFormat("0.00").format(tRCAdd5);
			sRCAdd7 = new DecimalFormat("0.00").format(tRCAdd7);
		}
		tTextTag.add("RCAdd1", tRCAdd1); // 拒付数量
		tTextTag.add("RCAdd2", tRCAdd2); // 拒付and主险或附险责任终止数量（D03，D04）
		tTextTag.add("RCAdd3", sRCAdd3); // 拒付and主险或附险责任终止数量权重
		tTextTag.add("RCAdd4", tRCAdd4); // 拒付and主险或附险责任终止and退还保单现金价值数量（D02）
		tTextTag.add("RCAdd5", sRCAdd5); // 拒付and主险或附险责任终止and退还保单现金价值数量权重
		tTextTag.add("RCAdd6", tRCAdd6); // 拒付and主险或附险责任终止and退还保费数量（D01）
		tTextTag.add("RCAdd7", sRCAdd7); // 拒付and主险或附险责任终止and退还保费数量权重

		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 案件类型 $=/RCAJType$
		String mmCaseType = mCaseType.equals("") ? "全部类型" : " "
				+ tLLPRTPubFunBL.getLDCode("llrgttype", mCaseType) + " ";
		tTextTag.add("RCAJType", mmCaseType);

		// 理赔类型 $=/RCLPType$
		String mmClaimType = mClaimType.equals("") ? "全部类型" : " "
				+ tLLPRTPubFunBL.getLDCode("llclaimtype", mClaimType) + " ";
		tTextTag.add("RCLPType", mmClaimType);

		// 机构层面选项 $=/RCManage$
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
		tTextTag.add("RCManage", tLevelName);

		// 统计时间段 $=/RCStaTimes$
		String tTimes = mStartDate + "至" + mEndDate;
		tTextTag.add("RCStaTimes", tTimes);

		// 统计口径：$=/RCStAround$
		tTextTag.add("RCStAround", mRCStArounName);

		// 险种选项： $=/RCRiskChance$
		tTextTag.add("RCRiskChance", mRCRiskChanName);

		// 机构统计范围 $=/RCStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("RCStatSer", tMngCom);

		// 申请类型
		String tApplTypeName = mNCLType.equals("1") ? "个人" : "团体";
		tTextTag.add("RCApplType", tApplTypeName);

		// 统计时间,默认为系统时间$=/RCDay$
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("RCDay", SysDate);

		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath = "E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName = strTemplatePath + "ZHRX--RC--Test1011.vts";
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

		tTransferData.setNameAndValue("StartDate", "2005-10-01"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-10-30"); // 统计止期
		tTransferData.setNameAndValue("Level", "01"); // 统计机构层面01 02 03 04 05
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("RCStAroun", "01"); // 统计口径01 02
		tTransferData.setNameAndValue("RCRiskChan", "01"); // 险种选项01 02
		tTransferData.setNameAndValue("CaseType", ""); // 案件类型 null 01 11 12 13
														// 14
		tTransferData.setNameAndValue("ClaimType", ""); // 理赔类型 null 100 101 200
														// ....

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRRefuseConclusionBL tLLPRRRefuseConclusionBL = new LLPRRRefuseConclusionBL();
		if (tLLPRRRefuseConclusionBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：拒付结论分析---失败----");
		}
		int n = tLLPRRRefuseConclusionBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRRefuseConclusionBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
