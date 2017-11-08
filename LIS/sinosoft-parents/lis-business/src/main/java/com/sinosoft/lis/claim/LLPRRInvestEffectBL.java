package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.Arith;
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
 * Title:
 * </p>
 * <p>
 * Description: 查勘效用统计表打印
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计时间段（结案时间）、查勘原因选项（单个原因，所有原因） 表头：报表名称、统计条件、统计日期
 * 数据项：机构、赔案总数、整案拒付件数、查勘件数、查勘阳性件数、整体拒付率（拒付赔案件数/赔案总数）、
 * 查勘件拒付率（查勘件拒付赔案件数/查勘件赔案总数）、查勘率（查勘件数/赔案总数）、 查勘阳性率（查勘阳性件数/查勘件数） 算法：按照选择的条件统计各项数据
 * 排序：机构 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhaorx,2005-10-04 9:32
 * @version 1.0
 */
public class LLPRRInvestEffectBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRInvestEffectBL.class);
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

	private GlobalInput mG = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String mStartDate = ""; // 开始日期
	private String mEndDate = ""; // 结束日期
	private String mLevel = ""; // 机构层面选项
	private String mManageCom = ""; // 机构统计范围选项
	private String mInvReason = ""; // 查勘原因选项
	private String mIEStAroun = ""; // 统计口径
	private String mIEStArounName = "";
	private String mNCLType = "";// 申请类型

	public LLPRRInvestEffectBL() {
	}

	// 主函数
	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("StartDate", "2005-10-01"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-11-01"); // 统计止期
		tTransferData.setNameAndValue("Level", "01"); // 统计层面
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("IEStAroun", "02"); // 统计口径
		tTransferData.setNameAndValue("InvReason", ""); // 勘查原因

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRInvestEffectBL tLLPRRInvestEffectBL = new LLPRRInvestEffectBL();
		if (tLLPRRInvestEffectBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印---失败----");
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-----统计报表打印：查勘效用统计表-----LLPRRInvestEffectBL测试-----开始-----");
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

		logger.debug("-----统计报表打印：查勘效用统计表-----LLPRRInvestEffectBL测试-----结束------");

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
		mInputData = cInputData;

		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		mLevel = (String) mTransferData.getValueByName("Level"); // 统计层面
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		mInvReason = (String) mTransferData.getValueByName("InvReason"); // 查勘原因选项
		mIEStAroun = (String) mTransferData.getValueByName("IEStAroun"); // 统计口径
		mIEStArounName = (String) mTransferData.getValueByName("IEStArounName");
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		return true;
	}

	/*
	 * 校验检查外部传入的数据 @param cInputData VData @return boolean
	 */
	private boolean checkInputData() {
		if (mG == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取用户登录信息失败!";
			mErrors.addOneError(tError);
			return false;
		}

		if (mStartDate == null || mEndDate == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取“统计起期”或 “统计止期”信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mManageCom == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取统计机构信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mLevel == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取统计层面信息失败!";
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 获取当前系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		logger.debug(SysDate);
		// 新建一个XmlExport的实例
		XmlExport xmlexport = new XmlExport();
		// 所用模板名称
		xmlexport.createDocument("LLPRRInvestEffect.vts", "");
		logger.debug("*******以下为ListTable的实例准备数据，并为其每列赋值**********");
		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tlistTable = new ListTable();
		tlistTable.setName("IE");

		// 定义列表标题，共10列
		String[] Title = new String[10];
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
		// 统计口径选项
		String tIEStArounTa = mIEStAroun.equals("01") ? " ,llclaimuwmain z where z.clmno = t.rgtno "
				: " where 1=1 ";// 统计口径表字段
		String tIEStArounTi = mIEStAroun.equals("01") ? " and z.examdate "
				: " and t.endcasedate ";// 统计口径时间字段
		String tEndCaseFlag = mIEStAroun.equals("01") ? " and z.ExamConclusion='0' "
				: " and t.endcaseflag='1' ";
		// 判断是否选择了“查勘原因”，如果选择则使用该条件，未选择不用<生成SQL语句>
		// 用于与后面的查询语句组合，形成完整的SQL语句
		String strInvReasonSQL = "";
		if (!mInvReason.equals("")) {
			strInvReasonSQL = " and clmno in (select distinct clmno from llinqapply p where p.inqrcode='"
					+ "?invreason?"+ "')";
		}

		// 首先根据 外部传入的参数“机构统计范围---mManageCom”和“机构层面-----mLevel”
		// 查选出所选机构下的处于所选机构级别<机构层面>的所有 机构，用于外层循环
		String strLevelSql = " select comcode,name from ldcom where 1=1 "
				+ " and comcode like concat('" + "?mngcom?" + "','%') "
				+ " and comgrade='" + "?level?" + "'" + " order by comcode ";
		logger.debug("**************************************************");
		logger.debug("****外层查询语句====" + strLevelSql);
		logger.debug("**************************************************");
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strLevelSql);
		sqlbv1.put("mngcom", mManageCom);
		sqlbv1.put("level", mLevel);
		ExeSQL tLevelExeSQL = new ExeSQL();
		SSRS tLevelSSRS = new SSRS();
		tLevelSSRS = tLevelExeSQL.execSQL(sqlbv1);
		int tMaxRow = tLevelSSRS.getMaxRow();
		if (tMaxRow == 0) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "dealData";
			tError.errorMessage = "机构" + mManageCom + "下级别为" + mLevel
					+ "的机构数目为:" + tMaxRow;
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("**************************************************");
		logger.debug("机构" + mManageCom + "下级别为" + mLevel + "的机构数目为==="
				+ tMaxRow);
		logger.debug("**************************************************");

		int tAllClmSum = 0; // 总计--------赔案总数
		int tReGiveClmSum = 0; // 总计--------整案拒付赔案总数
		int tInvClmSum = 0; // 总计--------有过调查的赔案总数
		int tInvMsClmSum = 0; // 总计--------有过调查结论为阳性赔案总数
		int tInvReGiveClmSum = 0; // 总计--------有过调查且整案拒付赔案总数
		String tApplType = mNCLType.equals("1") ? " and t.rgtobj='1' "
				: " and t.rgtobj='2' ";

		for (int index = 1; index <= tMaxRow; index++) {
			logger.debug("-------------第 " + index
					+ " 次循环开始--------------");
			String tComCode = tLevelSSRS.GetText(index, 1);// 机构代码
			String tComName = tLevelSSRS.GetText(index, 2);// 机构名称
			String SQL = " select ( "
			/** ****************查询机构的在指定时间段内结案的<赔案结案总数>*** */
			+ " select count(1) from llregister t "
					+ tIEStArounTa
					+ " and t.mngcom like concat('"
					+ "?comcode?"
					+ "','%') "
					+ tEndCaseFlag
					+ tApplType
					+ tIEStArounTi
					+ " between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "' "
					+ " ) "
					+ " ,("
					/** ****************查询机构的在指定时间段内结案的<整案拒付赔案总数>*** */
					+ " select count(1) from llclaim a,llregister t "
					+ tIEStArounTa
					+ " and a.clmno =t.rgtno"
					+ " and a.givetype='1' and t.mngcom like concat('"
					+ "?comcode?"
					+ "','%') "
					+ tEndCaseFlag
					+ tApplType
					+ tIEStArounTi
					+ " between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "'"
					+ " )  "
					+ " ,("
					/** ****************查询机构的在指定时间段内结案的<有过调查的赔案总数>*** */
					+ " select count(1) from llregister t "
					+ tIEStArounTa
					+ " and t.rgtno in (select distinct clmno from llinqconclusion where colflag='0' "
					+ strInvReasonSQL
					+ ")"
					+ " and t.mngcom like concat('"
					+ "?comcode?"
					+ "','%') "
					+ tEndCaseFlag
					+ tApplType
					+ tIEStArounTi
					+ " between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "'"
					+ " )  "
					+ " ,("
					/** ****************查询机构的在指定时间段内结案的<有过调查结论为阳性赔案总数>*** */
					+ " select count(1) from llregister t "
					+ tIEStArounTa
					+ " and t.rgtno in (select distinct clmno from llinqconclusion where colflag='0' and masflag='1' "
					+ strInvReasonSQL
					+ ")"
					+ " and t.mngcom like concat('"
					+ "?comcode?"
					+ "','%') "
					+ tEndCaseFlag
					+ tApplType
					+ tIEStArounTi
					+ " between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "'"
					+ " ) "
					+ " ,("
					/** ****************查询机构的在指定时间段内结案的<有过调查且整案拒付赔案总数>*** */
					+ " select count(1) from llclaim a,llregister t "
					+ tIEStArounTa
					+ " and a.clmno =t.rgtno "
					+ " and t.rgtno in (select distinct clmno from llinqconclusion where colflag='0' "
					+ strInvReasonSQL + ")"
					+ " and a.givetype='1' and t.mngcom like concat('" + "?comcode?"
					+ "','%') " + tEndCaseFlag + tApplType + tIEStArounTi
					+ " between '" + "?startdate?" + "' and '" + "?enddate?" + "'"
					+ " ) " + " from dual ";
			logger.debug("*******针对机构**" + tComName
					+ "的查询语句*************");
			logger.debug(SQL);
			logger.debug("*************************************************");
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(SQL);
			sqlbv2.put("comcode", tComCode);
			sqlbv2.put("startdate", mStartDate);
			sqlbv2.put("enddate", mEndDate);
			sqlbv2.put("invreason", mInvReason);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv2);
			int tAllClmNum = Integer.parseInt(tSSRS.GetText(1, 1));// 赔案总数
			int tReGiveClmNum = Integer.parseInt(tSSRS.GetText(1, 2));// 整案拒付赔案总数
			int tInvClmNum = Integer.parseInt(tSSRS.GetText(1, 3));// 有过调查的赔案总数
			int tInvMsClmNum = Integer.parseInt(tSSRS.GetText(1, 4));// 有过调查结论为阳性赔案总数
			int tInvReGiveClmNum = Integer.parseInt(tSSRS.GetText(1, 5));// 有过调查且整案拒付赔案总数

			double tReGiveRate = 0;
			double tInvReGiveRate = 0;
			double tInvRate = 0;
			double tInvMsRate = 0;
			if (tAllClmNum > 0) {
				tReGiveRate = Arith.div(tReGiveClmNum, tAllClmNum, 4); // 整体拒付率（拒付赔案件数/赔案总数）
				tInvRate = Arith.div(tInvClmNum, tAllClmNum, 4);// 查勘率（查勘件数/赔案总数）
			}
			if (tInvClmNum > 0) {
				tInvReGiveRate = Arith.div(tInvReGiveClmNum, tInvClmNum, 4);// 查勘件拒付率（查勘件拒付赔案件数/查勘件赔案总数）
				tInvMsRate = Arith.div(tInvMsClmNum, tInvClmNum, 4);// 查勘阳性率（查勘阳性件数/查勘件数）
			}
			// if (tAllClmNum > 0) {
			// tInvRate = Arith.div(tInvClmNum,tAllClmNum,4);//查勘率（查勘件数/赔案总数）
			// }
			// if (tInvClmNum > 0) {
			// tInvMsRate =
			// Arith.div(tInvMsClmNum,tInvClmNum,4);//查勘阳性率（查勘阳性件数/查勘件数）
			// }

			// 定义列表标题
			String[] stra = new String[10];
			stra[0] = tComName;// 机构代码
			stra[1] = String.valueOf(tAllClmNum);// 赔案总数
			stra[2] = String.valueOf(tReGiveClmNum);// 整案拒付赔案总数
			stra[3] = String.valueOf(tInvClmNum);// 查勘件数
			stra[4] = String.valueOf(tInvMsClmNum);// 查勘阳性件数
			stra[5] = String.valueOf(tReGiveRate);// 整体拒付率
			stra[6] = String.valueOf(tInvReGiveRate);// 查勘件拒付率
			stra[7] = String.valueOf(tInvRate);// 查勘率
			stra[8] = String.valueOf(tInvMsRate);// 查勘阳性率
			stra[9] = tComCode;// 机构名称

			tAllClmSum = tAllClmSum + tAllClmNum; // 总计--------赔案总数
			tReGiveClmSum = tReGiveClmSum + tReGiveClmNum; // 总计--------整案拒付赔案总数
			tInvClmSum = tInvClmSum + tInvClmNum; // 总计--------有过调查的赔案总数
			tInvMsClmSum = tInvMsClmSum + tInvMsClmNum; // 总计--------有过调查结论为阳性赔案总数
			tInvReGiveClmSum = tInvReGiveClmSum + tInvReGiveClmNum; // 总计--------有过调查且整案拒付赔案总数

			tlistTable.add(stra);

			logger.debug("-------------第 " + index
					+ " 次循环结束--------------");
			logger.debug("*************************************************");
		}

		// 以下计算总计一行的数据
		double tReGiveSumRate = 0;
		double tInvReGiveSumRate = 0;
		double tInvSumRate = 0;
		double tInvMsSumRate = 0;

		if (tAllClmSum > 0) {
			tReGiveSumRate = Arith.div(tReGiveClmSum, tAllClmSum, 4); // 总计--------整体拒付率（拒付赔案件数/赔案总数）
			tInvSumRate = Arith.div(tInvClmSum, tAllClmSum, 4); // 总计--------查勘率（查勘件数/赔案总数）
		}
		if (tInvClmSum > 0) {
			tInvReGiveSumRate = Arith.div(tInvReGiveClmSum, tInvClmSum, 4); // 总计--------查勘件拒付率（查勘件拒付赔案件数/查勘件赔案总数）
			tInvMsSumRate = Arith.div(tInvMsClmSum, tInvClmSum, 4); // 总计--------查勘阳性率（查勘阳性件数/查勘件数）
		}
		// if (tAllClmSum > 0) {
		// tInvSumRate = Arith.div(tInvClmSum, tAllClmSum, 4); //
		// 总计--------查勘率（查勘件数/赔案总数）
		// }
		// if (tInvClmSum > 0) {
		// tInvMsSumRate = Arith.div(tInvMsClmSum, tInvClmSum, 4); //
		// 总计--------查勘阳性率（查勘阳性件数/查勘件数）
		// }
		// 各项数据的合计:
		String IEAdd1 = String.valueOf(tAllClmSum); // 赔案总数
		String IEAdd2 = String.valueOf(tReGiveClmSum); // 整案拒付赔案总数
		String IEAdd3 = String.valueOf(tInvClmSum); // 查勘件数
		String IEAdd4 = String.valueOf(tInvMsClmSum); // 查勘阳性件数
		String IEAdd5 = String.valueOf(tReGiveSumRate); // 整体拒付率
		String IEAdd6 = String.valueOf(tInvReGiveSumRate); // 查勘件拒付率
		String IEAdd7 = String.valueOf(tInvSumRate); // 查勘率
		String IEAdd8 = String.valueOf(tInvMsSumRate); // 查勘阳性率

		logger.debug("*******以上为ListTable的实例准备数据，并为其每列赋值****成功！！！！！！！");

		logger.debug("*******以下为TextTag的实例准备数据，并为其每个变量赋值**********");
		// 机构层面： $=/IEManage$ 统计日期: $=/IEDate$
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
		// 查勘原因： $=/IEReason$
		String strInvReason = mInvReason.equals("") ? " 所有原因 " : " "
				+ tLLPRTPubFunBL.getLDCode("llinqreason", mInvReason) + " ";
		// 机构统计范围<机构名称>：$=/IEStatiSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema.setSchema(tLDComDB.getSchema());
		String MngComName = tLDComSchema.getName();
		// 统计时间段：$=/IEDateSection$
		String IEDateSection = mStartDate + "至" + mEndDate;
		// 申请类型: $=/CSApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";

		// 新建一个TextTag的实例
		TextTag tTextTag = new TextTag();
		tTextTag.add("IEManage", tLevelName);// 机构层面： $=/IEManage$
		tTextTag.add("IEReason", strInvReason);// 查勘原因： $=/IEReason$
		tTextTag.add("IEStatiSer", MngComName);// 机构统计范围：$=/IEStatiSer$
		tTextTag.add("IEDate", SysDate);// 统计日期: $=/IEDate$
		tTextTag.add("IEDateSection", IEDateSection);// 统计时间段：$=/IEDateSection$
		tTextTag.add("IEStAround", mIEStArounName);// 统计口径$=/IEStAround$
		tTextTag.add("IEApplType", mNCLTypeName);// 申请类型
		// 为总计行赋值
		tTextTag.add("IEAdd1", IEAdd1);
		tTextTag.add("IEAdd2", IEAdd2);
		tTextTag.add("IEAdd3", IEAdd3);
		tTextTag.add("IEAdd4", IEAdd4);
		tTextTag.add("IEAdd5", IEAdd5);
		tTextTag.add("IEAdd6", IEAdd6);
		tTextTag.add("IEAdd7", IEAdd7);
		tTextTag.add("IEAdd8", IEAdd8);

		logger.debug("*******以上为TextTag的实例准备数据，并为其变量赋值****成功！！！！！！！");

		logger.debug("****以下为XmlExport的实例 添加一个列表和动态文本标签的数组***");
		// 添加一个列表，参数为ListTable和动态列表的表头数组
		xmlexport.addListTable(tlistTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			xmlexport.addTextTag(tTextTag);
		}
		logger.debug("***************************************************");
		logger.debug("---以下 将XmlExport打包，返回前台--");

		mResult.clear();
		mResult.add(xmlexport);
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
}
