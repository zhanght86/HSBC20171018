package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
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
 * Description: 查勘费用管理表打印
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计时间段（结案时间）、统计口径（审批通过日期和结案日期）、查勘费用类别选项（单个类别，所有类别）、 调查人选项（分调查人、不分调查人）
 * 表头：报表名称、统计条件、统计日期 数据项：机构、调查人、类别代码、类别名称、查勘标准额、实际查勘费用、查勘费用超标与否、超标额度
 * 算法：按照选择的条件统计各项数据 排序：机构 表尾：各项数据的合计
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
public class LLPRRInvestFeeBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRInvestFeeBL.class);
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
	private String mInvFeeType = ""; // 查勘费用类别选项
	private String mIFStAroun = ""; // 统计口径
	private String mIFStArounName = "";
	private String mIFInqPer = ""; // 调查人
	private String mIFInqPerName = "";
	private String mNCLType = ""; // 申请类型

	public LLPRRInvestFeeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-----统计报表打印：查勘费用管理表-----LLPRRInvestFeeBL测试-----开始-----");
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

		logger.debug("-----统计报表打印：查勘费用管理表-----LLPRRInvestFeeBL测试-----结束------");

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
		mInvFeeType = (String) mTransferData.getValueByName("InvFeeType"); // 查勘费用类别选项
		mIFStAroun = (String) mTransferData.getValueByName("IFStAroun"); // 统计口径
		mIFStArounName = (String) mTransferData.getValueByName("IFStArounName");
		mIFInqPer = (String) mTransferData.getValueByName("IFInqPer"); // 调查人
		mIFInqPerName = (String) mTransferData.getValueByName("IFInqPerName");
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
		// 查勘费用类别 mInvFeeType，类别代码、类别名称,同时根据 是否选用“费用类别”来判断是否用该条件
		String InvFeeType = "";
		String InvFeeTypeName = "";
		String InvFeeTypeSQL = "";// 是否选用“费用类别”来判断是否用该条件，用于与后面的查询语句组合，形成完整的SQL语句
		if (mInvFeeType.equals("")) {
			InvFeeType = "00";
			InvFeeTypeName = "所有类别";
			InvFeeTypeSQL = "";
		} else {
			InvFeeType = mInvFeeType;
			InvFeeTypeName = tLLPRTPubFunBL.getLDCode("llinqfeetype",
					mInvFeeType);
			InvFeeTypeSQL = " and a.feetype='" + "?invfeetype?" + "'";
		}

		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
		// 所用模板名称
		xmlexport.createDocument("LLPRRInvestFee.vts", "");

		// 定义列表标题，共9列
		String[] Title = new String[9];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";

		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tlistTable = new ListTable();
		tlistTable.setName("IF");
		// 首先根据 外部传入的参数“机构统计范围---strManageCom”和“机构层面-----strLevel”
		// 查选出所选机构下的处于所选机构级别<机构层面>的所有 机构，用于外层循环
		String strLevelSql = " select comcode,name from ldcom where 1=1 "
				+ " and comcode like concat('" + "?mngcom?" + "','%') "
				+ " and comgrade='" + "?level?" + "' " + " order by comcode ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", mManageCom);
		sqlbv.put("level", mLevel);
		ExeSQL tLevelExeSQL = new ExeSQL();
		SSRS tLevelSSRS = tLevelExeSQL.execSQL(sqlbv);
		int tMaxRow = tLevelSSRS.getMaxRow();
		// 判断是否查询到管理机构,没查到时提示"没有要统计的数据",例:ManageCom=8632,而Level=01
		if (tMaxRow <= 0) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		String tIFStArounTa = mIFStAroun.equals("01") ? " ,llclaimuwmain r where r.clmno = b.rgtno "
				: " where 1=1 ";// 统计口径
		String tIFStArounTi = mIFStAroun.equals("01") ? " and r.examconclusion = '0' and r.examdate "
				: " and b.endcaseflag='1' and b.endcasedate ";
		String tIFInqPerTa = mIFInqPer.equals("01") ? " ,z.inqper from llinqapply z,llinqfee a where z.inqno = a.inqno and z.clmno = a.clmno "
				: " ,'不分调查人' from llinqfee a where 1=1 ";// 调查人选项
		String tIFInqPerLa = mIFInqPer.equals("01") ? " group by z.inqper "
				: "";
		// 申请类型判断
		String tApplType = mNCLType.equals("1") ? " and b.rgtobj='1' "
				: " and b.rgtobj='2' ";
		double tSumStandardInvFee = 0; // 总计------查勘标准额
		double tSumRealInvFee = 0; // 总计------实际查勘费用
		double tSumOverStandardFee = 0; // 总计------超标额度
		String tInvFeeType = InvFeeType; // 查勘费用类别代码
		String tInvFeeTypeName = InvFeeTypeName; // 查勘费用类别代码名称
		String tStandardInvFee = "0"; // 查勘标准额
		String tRealInvFee = "0"; // 实际查勘费用
		String tIsOverStandard = ""; // 查勘费用超标与否
		String tOverStandardFee = "0"; // 超标额度
		String tInqPer = ""; // 调查人

		for (int index = 1; index <= tMaxRow; index++) {
			logger.debug("-------------第 " + index
					+ " 次外层循环开始--------------");
			String tComCode = tLevelSSRS.GetText(index, 1); // 机构代码
			String tComName = tLevelSSRS.GetText(index, 2); // 机构名称
			// 查询该机构的“实际查勘费额度”
			String SQL = " select (case when sum(a.feesum) is null then 0 else sum(a.feesum) end) " + tIFInqPerTa
					+ " and a.clmno in"
					+ " (select b.rgtno from llregister b "
					+ tIFStArounTa
					// + " and b.endcaseflag='1' "
					+ tIFStArounTi + " between '" + "?startdate?" + "' and '"
					+ "?enddate?" + "'" + tApplType + " and b.mngcom like concat('"
					+ "?comcode?" + "','%') )" + InvFeeTypeSQL + tIFInqPerLa;
			logger.debug("*******针对机构**" + tComName
					+ "的查询语句*************");
			logger.debug(SQL);
			logger.debug("*************************************************");
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(SQL);
			sqlbv1.put("startdate", mStartDate);
			sqlbv1.put("enddate", mEndDate);
			sqlbv1.put("comcode", tComCode);
			sqlbv1.put("invfeetype", mInvFeeType);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv1);
			for (int x = 1; x <= tSSRS.getMaxRow(); x++)// 内层循环
			{
				logger.debug("-------------第 " + x
						+ " 次内层循环开始--------------");
				tRealInvFee = tSSRS.GetText(x, 1);// 、实际查勘费用
				tInqPer = tSSRS.GetText(x, 2);// 调查人
				String tInqPerName = "";
				if (mIFInqPer.equals("01"))// 分调查人统计，执行SQL查调查人名字
				{
					String tInqPerNameSQL = " select username from lduser where lduser.usercode='"
							+ "?usercode?" + "' ";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tInqPerNameSQL);
					sqlbv2.put("usercode", tInqPer);
					tInqPerName = tExeSQL.getOneValue(sqlbv2);
				} else// 不分调查人统计
				{
					tInqPerName = tInqPer;
				}
				logger.debug("实际查勘费用===" + tRealInvFee);
				logger.debug("-------------第 " + x
						+ " 次内层循环结束--------------");

				// 定义列表
				// 机构、类别代码、类别名称、查勘标准额、实际查勘费用、查勘费用超标与否、超标额度
				String[] stra = new String[9];
				stra[0] = tComCode; // 机构代码
				stra[1] = tComName; // 机构名称
				stra[2] = tInqPerName; // 调查人
				stra[3] = tInvFeeType; // 类别代码
				stra[4] = tInvFeeTypeName; // 查勘费用类别代码名称
				stra[5] = tIsOverStandard; // 查勘费用超标与否
				stra[6] = tStandardInvFee; // 查勘标准额
				stra[7] = tRealInvFee; // 实际查勘费用
				stra[8] = tOverStandardFee; // 超标额度
				tlistTable.add(stra);

				tSumStandardInvFee = tSumStandardInvFee
						+ Float.parseFloat(tStandardInvFee); // 总计------查勘标准额
				tSumRealInvFee = tSumRealInvFee + Float.parseFloat(tRealInvFee); // 总计------实际查勘费用
				tSumOverStandardFee = tSumOverStandardFee
						+ Float.parseFloat(tOverStandardFee); // 总计------超标额度
				logger.debug("-------------第 " + index
						+ " 次外层循环结束--------------");
			}
		}
		// 格式化 数据，总计中的小数位只能为2位
		tSumStandardInvFee = Arith.round(tSumStandardInvFee, 2); // 总计------查勘标准额
		tSumRealInvFee = Arith.round(tSumRealInvFee, 2); // 总计------实际查勘费用
		tSumOverStandardFee = Arith.round(tSumOverStandardFee, 2); // 总计------超标额度

		logger.debug("*******以上为ListTable的实例准备数据，并为其每列赋值****成功！！！！！！！");

		logger.debug("*******以下为TextTag的实例准备数据，并为其每个变量赋值**********");
		// 机构层面： $=/IFManage$ 统计日期: $=/IFDate$
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
		String IFManage = tLevelName;
		// 查勘费用类别： $=/IFFeeType
		String IFFeeType = InvFeeTypeName;

		// 机构统计范围<机构名称>：$=/IFStatiSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema.setSchema(tLDComDB.getSchema());
		String MngComName = tLDComSchema.getName();
		String IFStatiSer = MngComName;
		// 统计日期: $=/IFDate$ SysDate
		String IFDate = SysDate;
		// 统计时间段：$=/IFDateSection$
		String IFDateSection = mStartDate + "至" + mEndDate;
		// 申请类型: $=/IFApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";

		// 新建一个TextTag的实例
		TextTag tTextTag = new TextTag();
		tTextTag.add("IFManage", IFManage); // 机构层面： $=/IFManage$
		// 统计日期: $=/IFDate$
		tTextTag.add("IFFeeType", IFFeeType); // 查勘费用类别： $=/IFFeeType$
		// 统计时间段：$=/IFDateSection$
		tTextTag.add("IFStatiSer", IFStatiSer); // 机构统计范围：$=/IFStatiSer$
		tTextTag.add("IFDate", IFDate); // 统计日期: $=/IFDate$
		tTextTag.add("IFDateSection", IFDateSection);// 统计时间段：$=/IFDateSection$
		tTextTag.add("IFInqPer", mIFInqPerName); // 调查人：$=/IFInqPer$
		tTextTag.add("IFStAround", mIFStArounName); // 统计口径：$=/IFStAround$
		// 为总计行赋值
		tTextTag.add("IFAdd1", tSumStandardInvFee); // 总计------查勘标准额
		tTextTag.add("IFAdd2", tSumRealInvFee); // 总计------实际查勘费用
		tTextTag.add("IFAdd3", tSumOverStandardFee);// 总计------超标额度
		tTextTag.add("IFApplType", mNCLTypeName); // 申请类型

		xmlexport.addListTable(tlistTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			xmlexport.addTextTag(tTextTag);
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
		tTransferData.setNameAndValue("StartDate", "2005-8-10");
		tTransferData.setNameAndValue("EndDate", "2005-9-10");
		tTransferData.setNameAndValue("Level", "01");// 01 02 03 04 05
		tTransferData.setNameAndValue("ManageCom", "86");
		tTransferData.setNameAndValue("InvFeeType", "");// 01 02
		tTransferData.setNameAndValue("IFStAroun", "01");
		tTransferData.setNameAndValue("IFInqPer", "01");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRInvestFeeBL tLLPRRInvestFeeBL = new LLPRRInvestFeeBL();
		if (tLLPRRInvestFeeBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
	}
}
