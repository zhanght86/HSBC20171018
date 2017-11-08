package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LLSubReportDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.vschema.LLSubReportSet;
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
 * Description: 理赔鉴定提示清单打印，模板为LLPRBAppraisal.vts
 * </p>
 * 统计界面：统计时间段、提示时效（如120、150天）、统计机构选项（某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）
 * 表头：报表名称、统计条件、统计日期
 * 数据项：赔案号、出险人、意外事故发生日期和出险日期、报案人和联系方式、接案人、报案时间、理赔类型标识（重疾、高残、伤残3个标识）
 * 算法：统计选择的机构在指定时间段内报案且超过指定提示时效未立案的重疾、高残、伤残类案件 排序：报案时间、赔案号 表尾：统计数量（记录数量）
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj 20050831 modify by wanzh 2005/11/16
 * @version 1.0
 */

public class LLPRBAppraisalBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBAppraisalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	// 向后台提交,后面传输数据的容器
	private VData mInputData = new VData();
	// 返回前台的数据包,往界面传输数据的容器
	private VData mResult = new VData();
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();

	private String mComCode = ""; // 统计机构
	private String mStartDate = ""; // 开始日期
	private String mEndDate = ""; // 结束日期
	private String mClueData = ""; // 提示时效
	private String mCurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String mCurrentTime = PubFun.getCurrentTime(); // 系统时间
	private String tMngCom = "";
	private String mNCLType = ""; // 申请类型

	public LLPRBAppraisalBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------鉴定提示清单LLPRBAppraisalBL打印测试开始----------");
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
		// 准备输出数据
		if (!prepareOutputData()) {
			return false;
		}
		// 提交数据
		if (!pubSubmit()) {
			return false;
		}

		logger.debug("-----------鉴定提示清单LLPRBAppraisalBL打印测试结束----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;// 得到外部传入的数据,将数据备份到本类中

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 开始日期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 结束日期
		mComCode = (String) mTransferData.getValueByName("ComCode"); // 统计机构
		mClueData = (String) mTransferData.getValueByName("ClueData"); // 提示时效
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLPRBAppraisal.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		// 定义列表标题，共9列
		String[] Title = new String[16];
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
		Title[15] = "";

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/CHEPRO/ROW/COL内容，并为列表赋值--");

		// reasoncode=104 ，意外大病 reasoncode=204 ，疾病大病
		// reasoncode=103 ，意外高残 reasoncode=203 ，疾病高残
		// reasoncode=101 ，意外伤残 reasoncode=201 ，疾病伤残
		// RgtFlag＝10，为未立案标识
		/**
		 * 下面SQL语句用来查询符合条件的赔案号,但由于打印该清单要求的算法比较苛刻, 故在SQL语句中少了对"指定提示时效"的判断
		 */
		String applType = mNCLType.trim().equals("1") ? " and rgtobj='1' "
				: " and rgtobj='2' "; // 申请类型
		String mClmNoSQL = "select RptNo,RptDate ,mngcom from llreport where 1=1 "
				+ " and rptno in (select distinct rptno from llreportreason where reasoncode in('104','204','103','203','101','201') ) "
				+ " and (RptDate between '"
				+ "?startdate?"
				+ "' and  '"
				+ "?enddate?"
				+ "')"
				+ " and RgtFlag='10' and mngcom like concat('"
				+ "?comcode?" + "','%')" + applType // 申请类型
				+ " order by RptDate,RptNo";

		logger.debug("----------查询赔案号语句为-----------" + mClmNoSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(mClmNoSQL);
		sqlbv.put("startdate", mStartDate);
		sqlbv.put("enddate", mEndDate);
		sqlbv.put("comcode", mComCode);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tListTable = new ListTable();
		tListTable.setName("CHEPRO");

		int tStatCount = 0; // 用来统计符合条件的记录的数量

		// 循环处理每一条打印记录
		for (int i = 1; i <= tSSRS.MaxRow; i++) // 此时的记录有一部分为不符合条件的,因为没判断指定提示时效
		{
			logger.debug("-------------第 " + i + " 次循环开始----------------");
			String tClmNo = tSSRS.GetText(i, 1); // 赔案号
			LLReportSchema tLLReportSchema = new LLReportSchema();
			tLLReportSchema = tLLPRTPubFunBL.getLLReport(tClmNo);
			tMngCom = tSSRS.GetText(i, 3);
			// 得到意外事故发生日期,by niuzj 2005-09-21
			LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
			tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(tClmNo);
			String ttAccDate = tLLAccidentSchema.getAccDate();

			// 计算意外事故发生日期与系统日期之间的间隔天数
			int tcluedata = PubFun.calInterval(ttAccDate, mCurrentDate, "D");

			// 转化提示时效为整型
			int ttcluedata = (int) Double
					.parseDouble(String.valueOf(mClueData));
			// 比较,看是否超过提示时效
			// ==============BY zl 2005-9-22 20:02 增加等于========================
			if (tcluedata >= ttcluedata) // 此时的记录都是符合条件的,给模板中的每一列赋值
			{
				// 从报案分案表查询 出险人
				LLSubReportDB tLLSubReportDB = new LLSubReportDB();
				LLSubReportSet tLLSubReportSet = new LLSubReportSet();
				tLLSubReportDB.setSubRptNo(tClmNo);
				tLLSubReportSet.set(tLLSubReportDB.query());
				// 由于LLSubReport表的主键有两个,但上面只用一个主键查询,故查询记录可能不唯一
				for (int j = 1; j <= tLLSubReportSet.size(); j++) {
					LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema();
					tLLSubReportSchema = tLLSubReportSet.get(j);

					// 分报案号==赔案号
					String tSubRptNo = tLLSubReportSchema.getSubRptNo();
					// 出险人客户号
					String tCustomerNo = tLLSubReportSchema.getCustomerNo();
					// 出险人姓名--------------------------------------------------------------
					String tSql = "select a.name from ldperson a where "
							+ "a.customerno = '" + "?customerno?" + "'";
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(tSql);
					sqlbv1.put("customerno", tCustomerNo);
					ExeSQL tExeSQL = new ExeSQL();
					String tCustomerName = tExeSQL.getOneValue(sqlbv1);

					// 查询服务人员信息
					String agentSQL = "select distinct agentcode ,agentgroup,name,mobile "
							+ " from laagent where agentcode in (select distinct"
							+ " trim(agentcode)  from lcpol  where "
							+ " insuredno = '" + "?insuredno?" + "')";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables() ;
					sqlbv2.sql(agentSQL);
					sqlbv2.put("insuredno", tCustomerNo);
					ExeSQL currExeSQL = new ExeSQL();
					SSRS currSSRS = new SSRS();
					currSSRS = currExeSQL.execSQL(sqlbv2);
					String tAgentCode = "";
					String tAgentGroup = "";
					String tName = "";
					String tMobile = "";
					if (currSSRS.getMaxRow() > 0) {
						tAgentCode = currSSRS.GetText(1, 1);
						tAgentGroup = currSSRS.GetText(1, 2);
						tName = currSSRS.GetText(1, 3);
						tMobile = currSSRS.GetText(1, 4);
					}

					String tAccDate = ttAccDate;

					// -从报案表获取--出险日期、报案人和联系方式、接案人、报案时间----------------
					String tAccidentDate = tLLReportSchema.getAccidentDate();// 出险日期
					String tRptorName = tLLReportSchema.getRptorName();// 报案人
					String tRptorPhone = tLLReportSchema.getRptorPhone();// 联系方式
					String tRptDate = tLLReportSchema.getRptDate();// 报案时间
					String ttoperator = tLLReportSchema.getOperator();// 接案人,默认为操作员
					String tRptorAddress = tLLReportSchema.getRptorAddress();// 报案人地址

					String tClaimType = tLLPRTPubFunBL.getSLLReportReason(
							tClmNo, tCustomerNo);

					// 接案人姓名
					LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
					tLLClaimUserSchema = tLLPRTPubFunBL
							.getLLClaimUser(ttoperator);
					String toperator = tLLClaimUserSchema.getUserName();

					// 管理机构名称
					LDComSchema tLDComSchema = new LDComSchema();
					LDComDB tLDComDB = new LDComDB();
					tLDComDB.setComCode(tMngCom);
					tLDComDB.getInfo();
					tLDComSchema = tLDComDB.getSchema();
					String tMngComName = tLDComSchema.getName();

					// 为模板的每一列赋值
					String[] stra = new String[16];
					stra[0] = tMngCom; // 机构代码
					stra[1] = tMngComName; // 机构名称
					stra[2] = tSubRptNo; // 赔案号
					stra[3] = tCustomerName; // 出险人
					stra[4] = tAccDate; // 事故日期
					stra[5] = tAccidentDate; // 出险日期
					stra[6] = tRptorName; // 报案人
					stra[7] = tRptorPhone; // 报案人联系方式
					stra[8] = tRptorAddress; // 报案人地址
					stra[9] = tAgentCode; // 服务人代码
					stra[10] = tAgentGroup; // 服务人员组别
					stra[11] = tName; // 服务人姓名
					stra[12] = tMobile; // 服务人员电话
					stra[13] = toperator; // 接案人
					stra[14] = tRptDate; // 报案时间
					stra[15] = tClaimType; // 理赔类型标识
					tListTable.add(stra);
					tStatCount = tStatCount + 1; // 记录数量加1
					logger.debug("-------------第 " + i
							+ " 次循环结束----------------");
				}

			}

		}

		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计时间,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// 管理机构名称
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mComCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 "; // 申请类型
		String tStatCondition = "统计机构:" + tMngCom + "/统计时间段:" + mStartDate
				+ "至" + mEndDate + "/提示时效:" + mClueData + "天/申请类型:"
				+ tApplTypeName; // 统计条件
		tTextTag.add("StatCondition", tStatCondition); // 统计条件：$/=StatCondition$
		tTextTag.add("StatDate", SysDate); // 统计日期：$=/StatDate$
		tTextTag.add("StatCount", tStatCount); // 统计数量：$=/StatCount$

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
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBAppraisalBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			//
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	// test
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ComCode", "86");// 统计机构
		tTransferData.setNameAndValue("StartDate", "2005-1-1");// 开始日期
		tTransferData.setNameAndValue("EndDate", "2014-9-19"); // 结束日期
		tTransferData.setNameAndValue("ClueData", "30");// 提示时效

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBAppraisalBL tLLPRBAppraisalBL = new LLPRBAppraisalBL();
		if (tLLPRBAppraisalBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：鉴定提示清单出错---------------");
		}
	}

}
