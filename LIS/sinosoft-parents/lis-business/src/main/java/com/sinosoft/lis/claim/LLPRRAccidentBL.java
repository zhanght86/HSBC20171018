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
 * Description: 统计报表打印：事故统计--LLPRRAccident.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、机构统计范围选项（总公司、某分公司、某中支）、统计时间段、统计口径选项（事故发生日期、事故在系统中第一次建立日期）
 * 表头：报表名称、统计条件、统计日期 数据项：机构（或用户）、事故数量、赔案数量、未结案赔案数量、未结案事故数量 算法：按照选择的条件 排序：机构（或用户）
 * 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangyang,2005-09-27
 * @version 1.0 modify by zhaorx
 */

public class LLPRRAccidentBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRAccidentBL.class);
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
	private String mLevel = ""; // 统计层面
	private String mStartDate = ""; // 统计起期
	private String mEndDate = ""; // 统计止期
	private String mStatKJ = ""; // 统计口径
	private String mNCLType = ""; // 申请类型

	public LLPRRAccidentBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------统计报表打印：事件统计-----LLPRRAccidentBL测试-----开始----------");

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

		logger.debug("----------统计报表打印：事件统计-----LLPRRAccidentBL测试-----结束----------");
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
		this.mLevel = (String) mTransferData.getValueByName("Level"); // 统计层面
		this.mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		this.mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		this.mStatKJ = (String) mTransferData.getValueByName("StatKJ"); // 统计口径
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
		tXmlExport.createDocument("LLPRRAccident.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("ACCI");

		// 定义列表标题，共5列
		String[] Title = new String[5];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/ACCI/ROW/COL1内容，并为列表赋值--");

		// 用于统计各项数据的合计
		int tAcciAdd1 = 0; // 事故数量
		int tAcciAdd2 = 0; // 赔案数量
		int tAcciAdd3 = 0; // 未结案赔案数量
		int tAcciAdd4 = 0; // 未结案事故数量

		String tMngName = ""; // 机构或用户名称
		String strLevelSql = ""; // 查询统计层面
		if (!mLevel.equals("05")) { // 机构层面选项为非用户
			strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') "
					+ " and comgrade='"
					+ "?level?"
					+ "' "
					+ " order by ComCode ";
		} else { // 用户层面
			strLevelSql = " select usercode,UserName from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%') " + " order by usercode";
		}
		logger.debug("查询统计层面的SQL语句为:" + strLevelSql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", mManageCom);
		sqlbv.put("level", mLevel);
		ExeSQL execLevelSQL = new ExeSQL();
		SSRS ssrsLevel = execLevelSQL.execSQL(sqlbv); // 执行查询语句strLevelSql
		if (ssrsLevel.getMaxRow() != 0) { // 查询有结果
			for (int i = 1; i <= ssrsLevel.getMaxRow(); i++) {
				String mngCode = ssrsLevel.GetText(i, 1);// 机构或用户代码
				tMngName = ssrsLevel.GetText(i, 2);// 机构或用户名称

				String strSql1 = ""; // 事故数量
				String strSql2 = ""; // 赔案数量
				String strSql3 = ""; // 未结案赔案数量
				String strSql4 = ""; // 未结案事故数量
				String tMngcomOrOperator = "";// 机构或用户选择条件
				if (!mLevel.equals("05")) {
					tMngcomOrOperator = " and a.MngCom like concat('" + "?mngCode?"
							+ "','%') ";
				} else {
					tMngcomOrOperator = " and a.Operator = '" + "?mngCode?" + "' ";
				}
				String tTIMEs = " between '" + "?startdate?" + "' and '"
						+ "?enddate?" + "' ";
				// 申请类型判断
				String tApplTypeA = mNCLType.equals("1") ? " and a.rgtobj='1' "
						: " and a.rgtobj='2' ";
				String tApplTypeB = mNCLType.equals("1") ? " and b.rgtobj='1' "
						: " and b.rgtobj='2' ";
				// 用统计口径,再拼SQL语句
				if (mStatKJ.equals("1")) // 统计口径为:事故发生日期
				{
					strSql1 = " select count(*) from LLAccident a "// 事故数量
							+ " where a.AccDate "
							+ tTIMEs
							+ " and a.accno in (select c.caserelano from llcaserela c "
							+ " where exists (select 'Y' from llreport b where b.rptno=c.caseno "
							+ tApplTypeB + ")) "// Modify by zhaorx 2006-09-28
							+ tMngcomOrOperator;
					strSql2 = " select (("// 赔案数量
							+ " select count(*) from llaccident a,llreport b,llcaserela c " // 报案但没有立案的赔案数量
							+ " where 1=1 "
							+ " and a.accno=c.caserelano "
							+ " and b.rptno=c.caseno "
							+ tApplTypeB
							+ " and b.rptno not in (select rgtno from llregister) "
							+ " and a.AccDate "
							+ tTIMEs
							+ " "
							+ tMngcomOrOperator
							+ ") + ("
							+ " select count(*) from llaccident a,llregister b,llcaserela c " // 已经立案的赔案数量
							+ " where 1=1 "
							+ " and a.accno=c.caserelano "
							+ " and b.rgtno=c.caseno "
							+ tApplTypeB
							+ " and a.AccDate " + tTIMEs
							+ " "
							+ tMngcomOrOperator + " )) " + " from dual";
					strSql3 = " select((select count(*) from llaccident b,llregister a,llcaserela c " // 未结案的赔案数量
							+ " where 1=1 "
							+ " and b.accno=c.caserelano "
							+ " and a.rgtno=c.caseno "
							+ tApplTypeA
							+ " and a.ClmState not in ('60','70') "
							+ " and b.AccDate "
							+ tTIMEs
							+ " "
							+ tMngcomOrOperator
							+ " )+( "
							+ " select count(*) from llreport a "
							+ " where a.rptno not in (select a.rgtno from llregister a) "
							+ tApplTypeA
							+ " and a.accidentdate "
							+ tTIMEs
							+ " " + tMngcomOrOperator + " ))" + " from dual ";
					strSql4 = " select (select count(distinct a.accno) from llaccident a,llregister b,llcaserela c " // 未结案的事故数量
							+ " where 1=1 "
							+ " and b.clmstate not in ('60','70') "
							+ " and a.accno=c.caserelano "
							+ " and b.rgtno=c.caseno "
							+ tApplTypeB
							+ " and a.AccDate "
							+ tTIMEs
							+ ""
							+ tMngcomOrOperator
							+ " )+( "
							+ " select count(distinct a.accno) from llaccident a,llreport b,llcaserela c "
							+ " where 1=1 "
							+ " and b.rptno not in (select r.rgtno from llregister r) "
							+ " and a.accno=c.caserelano "
							+ " and b.rptno=c.caseno "
							+ tApplTypeB
							+ " and a.AccDate "
							+ tTIMEs
							+ ""
							+ tMngcomOrOperator + " )" + " from dual ";// Modify
																		// by
																		// zhaorx
																		// 2006-07-31
				} else if (mStatKJ.equals("2")) // 统计口径为:事故在系统中第一次建立日期
				{
					strSql1 = " select count(*) from LLAccident a "// 事故数量
							+ " where a.MakeDate "
							+ tTIMEs
							+ " and a.accno in (select c.caserelano from llcaserela c "
							+ " where exists (select 'Y' from llreport b where b.rptno=c.caseno "
							+ tApplTypeB + ")) "// Modify by zhaorx 2006-10-09
							+ tMngcomOrOperator;
					strSql2 = " select (("// 赔案数量
							+ " select count(*) from llaccident a,llreport b,llcaserela c " // 报案但没有立案的赔案数量
							+ " where 1=1 "
							+ " and a.accno=c.caserelano "
							+ " and b.rptno=c.caseno "
							+ tApplTypeB
							+ " and b.rptno not in (select rgtno from llregister) "
							+ " and a.MakeDate "
							+ tTIMEs
							+ " "
							+ tMngcomOrOperator
							+ " ) + ("
							+ " select count(*) from llaccident a,llregister b,llcaserela c " // 已经立案的赔案数量
							+ " where 1=1 "
							+ " and a.accno=c.caserelano "
							+ " and b.rgtno=c.caseno "
							+ tApplTypeB
							+ " and a.MakeDate " + tTIMEs
							+ " "
							+ tMngcomOrOperator + " )) " + " from dual";
					strSql3 = " select((select count(*) from llaccident b,llregister a,llcaserela c " // 未结案的赔案数量
							+ " where 1=1 "
							+ " and b.accno=c.caserelano "
							+ " and a.rgtno=c.caseno "
							+ tApplTypeA
							+ " and a.ClmState not in ('60','70') "
							+ " and b.MakeDate "
							+ tTIMEs
							+ " "
							+ tMngcomOrOperator
							+ " )+( "
							+ " select count(*) from llreport a "
							+ " where a.rptno not in (select a.rgtno from llregister a) "
							+ tApplTypeA
							+ " and a.MakeDate "
							+ tTIMEs
							+ " "
							+ tMngcomOrOperator + " )) " + " from dual ";
					strSql4 = " select (select count(distinct a.accno) from llaccident a,llregister b,llcaserela c " // 未结案的事故数量
							+ " where 1=1 "
							+ " and b.clmstate not in ('60','70') "
							+ " and a.accno=c.caserelano "
							+ " and b.rgtno=c.caseno "
							+ tApplTypeB
							+ " and a.MakeDate "
							+ tTIMEs
							+ " "
							+ tMngcomOrOperator
							+ ")+("
							+ " select count(distinct a.accno) from llaccident a,llreport b,llcaserela c "
							+ " where 1=1 "
							+ " and b.rptno not in (select r.rgtno from llregister r) "
							+ " and a.accno=c.caserelano "
							+ " and b.rptno=c.caseno "
							+ tApplTypeB
							+ " and a.MakeDate "
							+ tTIMEs
							+ " "
							+ tMngcomOrOperator + " ) " + " from dual ";// Modify
																		// by
																		// zhaorx
																		// 2006-07-31
				}
				logger.debug("最后的查询SQL语句为:" + strSql1);
				logger.debug("最后的查询SQL语句为:" + strSql2);
				logger.debug("最后的查询SQL语句为:" + strSql3);
				logger.debug("最后的查询SQL语句为:" + strSql4);
				ExeSQL strExeSQL = new ExeSQL();
				// SSRS strSSRS = new SSRS();
				// strSSRS = strExeSQL.execSQL(strSql1);
				// //执行最后得到的查询语句strSql1,事故数量
				// ExeSQL cusExeSQL = new ExeSQL();
				// SSRS cusSSRS = new SSRS();
				// cusSSRS = cusExeSQL.execSQL(strSql2);
				// //执行最后得到的查询语句strSql2,赔案数量
				// ExeSQL mExeSQL = new ExeSQL();
				// SSRS mSSRS = new SSRS();
				// mSSRS = mExeSQL.execSQL(strSql3);
				// //执行最后得到的查询语句strSql3,未结案赔案数量
				// ExeSQL tExeSQL = new ExeSQL();
				// SSRS tSSRS = new SSRS();
				// tSSRS = tExeSQL.execSQL(strSql4);
				// //执行最后得到的查询语句strSql4,未结案事故数量
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSql1);
				sqlbv1.put("mngcode", mngCode);
				sqlbv1.put("startdate", mStartDate);
				sqlbv1.put("enddate", mEndDate);
				String tAcciCount = strExeSQL.getOneValue(sqlbv1); // 事故数量
				// if (strSSRS.getMaxRow() != 0)
				// {
				// tAcciCount = strSSRS.GetText(1, 1);
				// }
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(strSql2);
				sqlbv2.put("mngcode", mngCode);
				sqlbv2.put("startdate", mStartDate);
				sqlbv2.put("enddate", mEndDate);
				String tClaimCount = strExeSQL.getOneValue(sqlbv2); // 赔案数量
				// if (cusSSRS.getMaxRow() != 0)
				// {
				// tClaimCount = cusSSRS.GetText(1, 1);
				// }
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(strSql3);
				sqlbv3.put("mngcode", mngCode);
				sqlbv3.put("startdate", mStartDate);
				sqlbv3.put("enddate", mEndDate);
				String tNClaimCount = strExeSQL.getOneValue(sqlbv3); // 未结案的赔案数量
				// if(mSSRS.getMaxRow() != 0)
				// {
				// tNClaimCount = mSSRS.GetText(1,1);
				// }
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(strSql4);
				sqlbv4.put("mngcode", mngCode);
				sqlbv4.put("startdate", mStartDate);
				sqlbv4.put("enddate", mEndDate);
				String tNAcciCount = strExeSQL.getOneValue(sqlbv4); // 未结案事故数量
				// if (tSSRS.getMaxRow() != 0)
				// {
				// tNAcciCount = tSSRS.GetText(1, 1);
				// }
				// 给模板中的每一列赋值
				String[] Stra = new String[6];
				Stra[0] = tMngName; // 机构或用户名称
				Stra[1] = tAcciCount; // 事故数量
				Stra[2] = tClaimCount; // 赔案数量
				Stra[3] = tNClaimCount; // 未结案的赔案数量
				Stra[4] = tNAcciCount; // 未结案的事故数量
				Stra[5] = mngCode; // 机构或用户代码
				tListTable.add(Stra);

				// 计算各项数据的合计
				tAcciAdd1 = tAcciAdd1 + Integer.parseInt(tAcciCount); // 事故数量
				tAcciAdd2 = tAcciAdd2 + Integer.parseInt(tClaimCount); // 赔案数量
				tAcciAdd3 = tAcciAdd3 + Integer.parseInt(tNClaimCount); // 未结案的赔案数量
				tAcciAdd4 = tAcciAdd4 + Integer.parseInt(tNAcciCount); // 未结案的事故数量
			}
		} else // 查询无结果
		{
			CError tError = new CError();
			tError.moduleName = "LLPRRAccidentBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计日期,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("AcciDay", SysDate); // 统计日期: $=/ACCIStaDate$

		// 统计时间段:$=/ACCIStaTimes$
		String tStatTimes = mStartDate + "至" + mEndDate;
		tTextTag.add("AcciStaTimes", tStatTimes);

		// 统计机构名称:$=/ACCIStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("AcciStatSer", tMngCom);

		// 统计层面:$=/ACCIManage$
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
		tTextTag.add("AcciManage", tLevelName);

		// 统计口径:$=/ACCIStatAround$
		String tStatKJName = mStatKJ.equals("1") ? "事故日期" : "事故在系统中第一次建立日期";
		tTextTag.add("AcciStatAround", tStatKJName);

		// 申请类型
		String tApplTypeName = mNCLType.equals("1") ? "个人" : "团体";
		tTextTag.add("AcciApplType", tApplTypeName);

		// 事件数量:$=/AcciAdd1$
		tTextTag.add("AcciAdd1", tAcciAdd1);
		// 赔案数量:$=/AcciAdd2$,格式化取两位小数
		tTextTag.add("AcciAdd2", tAcciAdd2);
		// 未结案赔案数量:$=/AcciAdd3$
		tTextTag.add("AcciAdd3", tAcciAdd3);
		// 未结案事故数量:$=/AcciAdd4$
		tTextTag.add("AcciAdd4", tAcciAdd4);

		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		// /*##################### 后台调试部分，生成临时文件############################*/
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

		tTransferData.setNameAndValue("StartDate", "2005-10-13"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-10-14"); // 统计止期
		tTransferData.setNameAndValue("Level", "01"); // 统计层面
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("StatKJ", "1"); // 统计口径

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRAccidentBL tLLPRRAccidentBL = new LLPRRAccidentBL();
		if (tLLPRRAccidentBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：事故统计---失败----");
		}
		int n = tLLPRRAccidentBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRAccidentBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
