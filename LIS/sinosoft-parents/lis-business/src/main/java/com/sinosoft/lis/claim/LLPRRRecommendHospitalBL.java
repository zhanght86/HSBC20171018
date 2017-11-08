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
 * Title:
 * </p>
 * <p>
 * Description: 定点医院归类统计表打印
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计时间段（医院录入日期）、定点医院类别选项（单个类别、全部类别） 表头：报表名称、统计条件、统计日期
 * 数据项：机构、类别代码、类别名称、定点医院数量和数量占比 算法：按照选择的条件统计各项数据 排序：机构 表尾：各项数据的合计
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
public class LLPRRRecommendHospitalBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRRecommendHospitalBL.class);
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
	private String mHosAtti = ""; // 医院等级

	public LLPRRRecommendHospitalBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-----统计报表打印：定点医院归类统计表-----LLPRRRecommendHospitalBL测试-----开始-----");
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
		logger.debug("-----统计报表打印：定点医院归类统计表-----LLPRRRecommendHospitalBL测试-----结束------");

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
		this.mHosAtti = (String) mTransferData.getValueByName("HosAtti"); // 医院等级代码
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 新建一个TextTag的实例
		TextTag tTextTag = new TextTag();
		// 新建一个XmlExport的实例
		XmlExport xmlexport = new XmlExport();
		// 所用模板名称appoint to a special output .vts file
		xmlexport.createDocument("LLPRRRecommendHospital.vts", "");
		// 定义列表标题，共5列
		String[] Title = new String[6];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";

		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tListTable = new ListTable();
		tListTable.setName("RH");

		int tRHAdd1 = 0; // 定点医院数量
		double tRHAdd2 = 0; // 定点医院数量占比
		String tCodeName = ""; // 医院等级名称
		String strLevelSql = ""; // 确定机构层面选项和机构统计范围选项的Sql

		if (!mLevel.equals("05")) // 机构层面选项为非用户
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
			tError.moduleName = "LLPRRRecommendHospital";
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

			String tPublicSQL = ""; // 公用的SQL
			String tZpublicSQL = " select count(*) from LLCommendHospital b where ";// 子字符串
			String tTIMEs = " between '" + "?startdate?" + "' and '" + "?enddate?"
					+ "' ";// 时间字符串
			if (!mLevel.equals("05"))// 非用户
			{
				if (mHosAtti.equals("") || mHosAtti.equals("null")
						|| mHosAtti == null)// 全部等级统计统计
				{
					tPublicSQL = " select a.hosatti,"
							+ " (select b.codename from ldcode b where b.codetype = 'llhosgrade' and a.hosatti = b.code ), "
							+ " count(*), " + " (" + tZpublicSQL
							+ " b.conflag = '0' and b.makedate " + tTIMEs
							+ " and b.mngcom like concat('" + "?magcode?" + "','%')) "
							+ " from LLCommendHospital a "
							+ " where a.makedate" + tTIMEs + " "
							+ " and a.mngcom like concat('" + "?magcode?" + "','%')"
							+ " group by hosatti";
				} else // 单个等级统计
				{
					tPublicSQL = " select a.hosatti, "
							+ " (select b.codename from ldcode b where b.codetype = 'llhosgrade' and b.code=a.hosatti ), "
							+ " count(*), " + " (" + tZpublicSQL
							+ " b.makedate " + tTIMEs + " and b.hosatti='"
							+ "?hosatti?"
							+ "' and b.conflag = '0' and b.mngcom like concat('"
							+ "?magcode?" + "','%')) " + " from LLCommendHospital a "
							+ " where a.makedate " + tTIMEs + " "
							+ " and a.mngcom like concat('" + "?magcode?" + "','%')"
							+ " and a.hosatti='" + "?hosatti?" + "'"
							+ " group by hosatti";
				}
			} else // 用户
			{
				if (mHosAtti.equals(""))// 全部等级统计统计
				{
					tPublicSQL = " select a.hosatti, "
							+ " (select b.codename from ldcode b where b.codetype = 'llhosgrade' and a.hosatti = b.code ), "
							+ " count(*), " + " (" + tZpublicSQL
							+ " b.conflag = '0' and b.makedate " + tTIMEs
							+ " and b.mngcom like concat('" + "?mngcom?" + "','%') ) "
							+ " from LLCommendHospital a "
							+ " where a.makedate" + tTIMEs + " "
							+ " and a.operator='" + "?magcode?" + "'"
							+ " group by hosatti ";
				} else// 单个等级统计
				{
					tPublicSQL = " select a.hosatti, "
							+ " (select b.codename from ldcode b where b.codetype = 'llhosgrade' and b.code=a.hosatti ), "
							+ " count(*), " + " (" + tZpublicSQL
							+ " b.makedate " + tTIMEs + " and b.hosatti='"
							+ "?hosatti?"
							+ "' and b.conflag = '0' and b.mngcom like concat('"
							+ "?mngcom?" + "','%')) "
							+ " from LLCommendHospital a "
							+ " where a.makedate " + tTIMEs + " "
							+ " and a.operator='" + "?magcode?" + "'"
							+ " and a.hosatti='" + "?hosatti?" + "'"
							+ " group by hosatti ";
				}
			}
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tPublicSQL);
			sqlbv1.put("magcode", magcode);
			sqlbv1.put("mngcom", mManageCom);
			sqlbv1.put("hosatti", mHosAtti);
			sqlbv1.put("startdate", mStartDate);
			sqlbv1.put("enddate", mEndDate);
			ExeSQL ExePublicSQL = new ExeSQL();
			SSRS tEPS = new SSRS();
			tEPS = ExePublicSQL.execSQL(sqlbv1);

			for (int j = 1; j <= tEPS.getMaxRow(); j++) {
				logger.debug("----------------第 " + j
						+ " 次内层循环开始--------------");
				String tHosatti = tEPS.GetText(j, 1); // 等级代码
				tCodeName = tEPS.GetText(j, 2); // 医院等级名
				String sDDA = tEPS.GetText(j, 3); // 定点医院数量
				String sDDAFDA = tEPS.GetText(j, 4); // 定点和非定点医院数量

				double douDDA = 0;
				double douDDAFDA = 0;
				double douPes = 0;
				String tdouPes = "";
				// 将String型转换为Double型
				douDDAFDA = Double.parseDouble(sDDAFDA);
				douDDA = Double.parseDouble(sDDA);
				if (sDDAFDA.equals("0")) {
					douDDA = 0;
					tdouPes = "0.000";
				} else {
					douPes = douDDA / douDDAFDA;
					tdouPes = new DecimalFormat("0.000").format(douPes);
				}

				// 定义列表标题
				String[] stra = new String[6];
				stra[0] = magname; // 机构或用户名字
				stra[1] = tHosatti; // 等级代码
				stra[2] = tCodeName; // 等级名称
				stra[3] = sDDA; // 定点医院数量
				stra[4] = tdouPes; // 定点医院数量占比
				stra[5] = magcode; // 机构或用户代码
				tListTable.add(stra);

				// 定点医院数量
				tRHAdd1 = tRHAdd1 + Integer.parseInt(sDDA);
				// 定点医院数量占比
				tRHAdd2 = tRHAdd2 + douPes;
				logger.debug("-------------第 " + j
						+ " 次内层循环结束------------");
				logger.debug("**************************************************");
			}
			logger.debug("-------------第 " + index
					+ " 次外层循环结束------------");
			logger.debug("******************************************************");
		}

		// 定点医院数量和定点医院数量占比的合计
		tTextTag.add("RHAdd1", tRHAdd1);
		tTextTag.add("RHAdd2", tRHAdd2);
		// 向模板传医院等级名称的一个字符串,医院等级$=/RHHosType$
		String tRHHosType = mHosAtti.equals("") ? "全部等级" : " " + tCodeName
				+ " ";
		tTextTag.add("RHHosType", tRHHosType);
		// 机构层面选项 $=/RHManage$
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
		tTextTag.add("RHManage", tLevelName);
		// 统计时间段 $=/RHStaTimes$
		String tTimes = mStartDate + "至" + mEndDate;
		tTextTag.add("RHStaTimes", tTimes);
		// 机构统计范围 $=/RHStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("RHStatSer", tMngCom);
		// 统计时间,默认为系统时间$=/RHDay$
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("RHDay", SysDate);

		xmlexport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			xmlexport.addTextTag(tTextTag);
		}

		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath = "E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName = strTemplatePath + "ZHRX-RH-Test1008.vts";
		// //生成临时文件名
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(xmlexport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);

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
		tTransferData.setNameAndValue("StartDate", "2004-01-01");
		tTransferData.setNameAndValue("EndDate", "2005-11-21");
		tTransferData.setNameAndValue("Level", "05");// 01 02 03 04 05
		tTransferData.setNameAndValue("ManageCom", "86");
		tTransferData.setNameAndValue("HosAtti", "");// 11 12 13 21 22 23 31
														// 32 33 41 42 null

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRRecommendHospitalBL tLLPRRRecommendHospitalBL = new LLPRRRecommendHospitalBL();
		if (tLLPRRRecommendHospitalBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
		int n = tLLPRRRecommendHospitalBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content
					+ "原因是: "
					+ tLLPRRRecommendHospitalBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
