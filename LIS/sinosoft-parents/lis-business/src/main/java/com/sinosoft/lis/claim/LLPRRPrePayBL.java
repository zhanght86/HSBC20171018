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
 * Description: 统计报表打印：预付统计--LLPRRPrePay.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、机构统计范围选项（总公司、某分公司、某中支）、统计时间段
 * 表头：报表名称、统计条件、统计日期 数据项：机构（或用户）、案件数量、预付次数、预付金额 算法：按照选择的条件统计各项预付数据 排序：机构（或用户）
 * 表尾：各项数据的合计
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

public class LLPRRPrePayBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRPrePayBL.class);
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
	private String mNCLType = "";// 申请类型

	public LLPRRPrePayBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------统计报表打印：预付统计-----LLPRRPrePayBL测试-----开始----------");

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

		logger.debug("----------统计报表打印：预付统计-----LLPRRPrePayBL测试-----结束----------");
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
		this.strLevel = (String) mTransferData.getValueByName("Level"); // 统计层面
		this.strStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		this.strEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
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
		tXmlExport.createDocument("LLPRRPrePay.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("PP");

		// 定义列表标题，共4列
		String[] Title = new String[4];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/PP/ROW/COL内容，并为列表赋值--");

		// 用于统计各项数据合计的变量
		int tPPAdd1 = 0; // 总案件数量
		int tPPAdd2 = 0; // 总预付次数
		double tPPAdd3 = 0; // 总预付金额

		String tMngName = ""; // 机构或用户名称
		String strLevelSql = ""; // 查询统计层面
		if (!strLevel.equals("05")) { // 机构层面选项为非用户
			strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') "
					+ " and comgrade = '"
					+ "?level?"
					+ "' " + " order by ComCode ";
		} else { // 用户层面
			strLevelSql = " select usercode,UserName from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%') " + " order by usercode";
		}
		logger.debug("查询统计层面的SQL语句为:" + strLevelSql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", strManageCom);
		sqlbv.put("level", strLevel);
		ExeSQL execLevelSQL = new ExeSQL();
		SSRS ssrsLevel = execLevelSQL.execSQL(sqlbv); // 执行查询语句strLevelSql
		if (ssrsLevel.getMaxRow() != 0) { // 查询有结果
			// 业务类型判断llregister.rgtobj：1-个险 2-团险（共享池选项不是待支付SQL中判断条件）
			String tApplType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where a.clmno = llregister.rgtno and llregister.rgtobj = '1' ) "
					: " and exists (select 'X' from llregister where a.clmno = llregister.rgtno and llregister.rgtobj = '2' ) ";
			for (int i = 1; i <= ssrsLevel.getMaxRow(); i++) {
				String mngCode = ssrsLevel.GetText(i, 1); // 机构或用户代码
				tMngName = ssrsLevel.GetText(i, 2); // 机构或用户名称

				String strSql = " select count(*),(case when sum(a.prepaysum) is null then 0 else sum(a.prepaysum) end),count(a.prepaysum) from llprepayclaim a,ljagetclaim b "
						+ " where 1=1 "
						+ " and a.clmno=b.otherno "
						+ " and b.othernotype='5' " // 其它号码类型为赔案号
						+ " and b.feeoperationtype='B' " // 业务类型为预付
						+ " and b.OPConfirmDate between '"
						+ "?startdate?"
						+ "' and '" + "?enddate?" + "'" + tApplType;
				// 用统计层面,拼SQL语句
				if (!strLevel.equals("05"))// 统计层面:非用户层面
				{
					strSql = strSql + " and a.MngCom like concat('" + "?mngcode?" + "','%') ";
				} else {
					strSql = strSql + " and a.Operator = '" + "?mngcode?" + "' ";
				}
				logger.debug("最后的查询SQL语句为:" + strSql);
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSql);
				sqlbv1.put("mngcode", mngCode);
				sqlbv1.put("startdate", strStartDate);		
				sqlbv1.put("enddate", strEndDate);		
				ExeSQL strExeSQL = new ExeSQL();
				SSRS strSSRS = new SSRS();
				strSSRS = strExeSQL.execSQL(sqlbv1);
				String tYFCS = ""; // 预付次数
				String tCaseCount = ""; // 预付案件数量
				String ttCaseSum = ""; // 预付案件金额
				for (int j = 1; j <= strSSRS.getMaxRow(); j++) {
					tCaseCount = strSSRS.GetText(j, 1);
					ttCaseSum = strSSRS.GetText(j, 2);
					tYFCS = strSSRS.GetText(j, 3);// 取预付次数
				}
				double tCaseSum = Double.parseDouble(ttCaseSum);

				// String tYFCS = ""; //预付次数,现在只有1次
				// if(ttCaseSum.equals("0"))
				// {
				// tYFCS="0";
				// }
				// else
				// {
				// tYFCS="1";
				// }

				// 给模板中的每一列赋值
				String[] Stra = new String[5];
				Stra[0] = tMngName; // 机构或用户名称
				Stra[1] = tCaseCount; // 预付案件数量
				Stra[2] = tYFCS; // 预付次数
				Stra[3] = String.valueOf(tCaseSum); // 预付案件金额
				Stra[4] = mngCode; // 机构或用户代码
				tListTable.add(Stra);

				// 计算各项数据的合计
				tPPAdd1 = tPPAdd1 + Integer.parseInt(tCaseCount); // 总预付案件数量
				tPPAdd2 = tPPAdd2 + Integer.parseInt(tYFCS); // 总预付次数
				tPPAdd3 = tPPAdd3 + tCaseSum; // 总预付金额
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
		tTextTag.add("PPStaDate", SysDate); // 统计日期: $=/PPStaDate$

		// 统计时间段:$=/PPStaTimes$
		String tStatTimes = strStartDate + "至" + strEndDate;
		tTextTag.add("PPStaTimes", tStatTimes);

		// 统计机构名称:$=/PPStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("PPStaSer", tMngCom);

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
			tLevelName = "用户";
			break;
		default:
			tLevelName = "";
			break;
		}
		tTextTag.add("PPManage", tLevelName);
		// 申请类型: $=/PPApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		tTextTag.add("PPApplType", mNCLTypeName);

		// tTextTag.add("StatCount", tStatCount); //统计数量：$=/StatCount$

		// 总预付案件数:$=/PPAdd1$
		tTextTag.add("PPAdd1", tPPAdd1);
		// 总预付次数:$=/PPAdd2$
		tTextTag.add("PPAdd2", tPPAdd2);
		// 总预付金额:$=/PPAdd3$
		tTextTag.add("PPAdd3", new DecimalFormat("0.00").format(tPPAdd3));

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
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("Level", "05"); // 机构层面
		tTransferData.setNameAndValue("StartDate", "2005-10-01"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-12-01"); // 统计止期

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRPrePayBL tLLPRRPrePayBL = new LLPRRPrePayBL();
		if (tLLPRRPrePayBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：预付统计---失败----");
		}
		int n = tLLPRRPrePayBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRPrePayBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}

}
