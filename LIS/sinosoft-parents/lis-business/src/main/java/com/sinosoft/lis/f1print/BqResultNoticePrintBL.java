package com.sinosoft.lis.f1print;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 补费通知书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：wangyan,pst
 * @version：1.0 2.0
 * @CreateDate：2005-07-30, 2009-02-03
 */
public class BqResultNoticePrintBL implements PrintService {
	private static Logger logger = Logger
			.getLogger(BqResultNoticePrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private XmlExportNew tXmlExport = new XmlExportNew();

	private LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();

	private String mEdorAcceptNo = ""; // 保全受理号

	private String mContNo = ""; // 保单号

	private String tEdorNo = ""; // 批单号

	private String tEdorType = ""; // 批改类型

	private String mCustomerNo = ""; // 客户号

	private String mAppName = ""; // 投保人姓名

	private String mSex = ""; // 投保人性别

	private String mInsuredName = ""; // 被保人姓名

	private String mNoticeNo = ""; // 通知书号

	private String mDLName = ""; // 客户姓名

	private String mAppDate = ""; // 申请日期

	private String mEndDate = ""; // 补费止期

	private String mChgItem = ""; // 变更项目

	private String mGetMoney = ""; // 补费小计

	private String mMoney = ""; // 补费合计大写

	private double mSumMoney = 0.00; // 补费合计小写

	private String mPayMode = ""; // 补费形式

	private String mBankAccNo = ""; // 开户行

	private String mAccount = ""; // 帐号

	private String mAccountName = ""; // 户名

	private String mOperator = ""; // 经办人

	private String mAppr = ""; // 复核人

	private String mManageComName = ""; // 机构名称

	private String mSysDate = ""; // 系统时间

	private String mManageCom = ""; // 营销服务部

	private String mBranchGroup = ""; // 营业部

	private String mAgentName = ""; // 代理人姓名

	private String mAgentCode = ""; // 代理人代码

	private String mCompanyAddress = ""; // 公司地址

	private String mCompanyPost = ""; // 公司邮编

	private String mSubCode = "";

	private String mAmnt = "";

	private double mTotal = 0;

	ExeSQL tExeSQL = new ExeSQL();

	public BqResultNoticePrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验操作
		if (!checkData()) {
			return false;
		}

		// 从数据库获得数据
		if (!getBaseData()) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:打印流水号不能为空！"));
			return false;
		}
		// 取打印通知书记录
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (!tLOPRTManagerDB.getInfo()) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		String mNoType = mLOPRTManagerSchema.getOtherNoType();
		if (mNoType == null || mNoType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqResultNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_EDORACCEPT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqResultNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 取保全申请号
		mEdorAcceptNo = mLOPRTManagerSchema.getOtherNo();
		if (mEdorAcceptNo == null || mEdorAcceptNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqResultNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保全受理号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 取打印通知书类型
		String tEdorItem = mLOPRTManagerSchema.getCode();
		if (tEdorItem == null || tEdorItem.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "BqResultNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tEdorItem.trim().equals(PrintManagerBL.CODE_PEdorResult)) {
			CError tError = new CError();
			tError.moduleName = "BqResultNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "BqResultNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据验证失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema = tLPEdorAppDB.getSchema();

		mContNo = tLPEdorAppSchema.getOtherNo();

		return true;
	}

	// 获取数据库数据
	private boolean getBaseData() {
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		mAppName = tLCContSchema.getAppntName();
		String tPrintName = "保全办理结果通知书";
		tXmlExport.createDocument(tPrintName);//初始化数据存储类
		PrintTool.setBarCode(tXmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCContSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			tXmlExport.setUserLanguage(uLanguage);
		tXmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		mOperator = tLPEdorAppSchema.getOperator(); // 经办人
		mDLName = tLPEdorAppSchema.getBehalfName();

		String mAppDate1 = tLPEdorAppSchema.getEdorAppDate(); // 申请日期
		FDate fDate = new FDate();
		GregorianCalendar tCalendar = new GregorianCalendar();
		int tYear = 0;
		int tMonth = 0;
		int tDay = 0;
		if (!"".equals(mAppDate1)) {
			tCalendar.setTime(fDate.getDate(mAppDate1));
			tYear = tCalendar.get(Calendar.YEAR);
			tMonth = tCalendar.get(Calendar.MONTH) + 1;
			tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			mAppDate = tYear + "年" + tMonth + "月" + tDay + "日";
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);

		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqResultNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		tEdorType = tLPEdorItemSchema.getEdorType();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("Select edorname from lmedoritem where edorcode = '"
				+ "?tEdorType?" + "' and appobj='I' ");
		sqlbv1.put("tEdorType", tEdorType);
		mChgItem = tExeSQL.getOneValue(sqlbv1); // 变更项目

		// 累计补退费
		mSumMoney = Double.parseDouble(mLOPRTManagerSchema.getStandbyFlag4());

		mPayMode = mLOPRTManagerSchema.getStandbyFlag2();
		if ("转帐".equals(mPayMode)) {
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("select concat(concat(substr(a.standbyflag3,1,char_length(standbyflag3)-8),'****'), "
					+ "  substr(a.standbyflag3,char_length(standbyflag3)-3,4))  from loprtmanager a "
					+ " where prtseq='"
					+ "?prtseq?" + "'");
			sqlbv2.put("prtseq", mLOPRTManagerSchema.getPrtSeq());
			String mBankNo = tExeSQL.getOneValue(sqlbv2);
			mBankAccNo = "退费帐号：" + mBankNo;
		}

		// 生成-年-月-日格式的日期
		mSysDate = new SimpleDateFormat("yyyy年mm月dd日").format(new Date());
		return true;
	}

	private boolean preparePrintData() {
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		// 模版自上而下的元素
		texttag.add("AppntName", mAppName);// 投保人姓名
		texttag.add("ContNo", mContNo); // 保单号
		texttag.add("PrintNo", mLOPRTManagerSchema.getPrtSeq()); // 通知书号
		texttag.add("EdorAppNo", mEdorAcceptNo); // 保全受理号
		texttag.add("DLName", mDLName); // 代理人
		texttag.add("EdorAppDate", mAppDate); // 申请日期
		texttag.add("EdorType", mChgItem); // 变更项目
		texttag.add("TFMoney", mSumMoney); // 退费金额
		texttag.add("TFType", mPayMode); // 退费方式
		texttag.add("BankNo", mBankAccNo); // 银行帐号

		// 添加服务人员信息
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String tsql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tsql = "select agentcode from lcpol  where contno='"
					+ "?mContNo?" + "' and polno=mainpolno and rownum=1 ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tsql = "select agentcode from lcpol  where contno='"
					+ "?mContNo?" + "' and polno=mainpolno  limit 0,1 ";
		}
		sqlbv3.sql(tsql);
		sqlbv3.put("mContNo", mContNo);
		String AgentCode = tExeSQL.getOneValue(sqlbv3);
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql("select name from laagent where agentcode='"
				+ "?AgentCode?" + "'");
		sqlbv4.put("AgentCode", AgentCode);
		String AgentName = tExeSQL.getOneValue(sqlbv4);
		texttag.add("OperatorCode", AgentCode); // 操作员代码
		texttag.add("OperatorName", AgentName); // 服务人员
		// 添加子公司名称
		String subname = "";
		if (tLPEdorAppSchema.getManageCom().equals("86")) {
			subname = "总公司";
		} else {
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql("select shortname from ldcom where comcode='"
					+ "?comcode?"
					+ "'");
			sqlbv5.put("comcode", tLPEdorAppSchema.getManageCom().substring(0, 4));
			subname = tExeSQL.getOneValue(sqlbv5);
		}
		texttag.add("submanagecom", subname);
		texttag.add("PrintDate", mSysDate);

		logger.debug("大小" + texttag.size());
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(tXmlExport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 测试主程序业务方法
	 * 
	 * @param String[]
	 */
	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("86000020080819000077");
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		BqResultNoticePrintBL tBqResultNoticePrintBL = new BqResultNoticePrintBL();
		if (!tBqResultNoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("\t@> BqResultNoticePrintBL 打印失败");
		}
	} // function main end
}
