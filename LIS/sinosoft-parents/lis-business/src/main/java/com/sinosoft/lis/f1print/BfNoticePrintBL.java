package com.sinosoft.lis.f1print;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
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
public class BfNoticePrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(BfNoticePrintBL.class);
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
	private String mAppName1 = ""; // 客户姓名
	private String mAppDate = ""; // 申请日期
	private String mEndDate = ""; // 补费止期
	private String mChgItem = ""; // 变更项目
	private String mGetMoney = ""; // 补费小计
	private String mMoney = ""; // 补费合计大写
	private String mSumMoney = ""; // 补费合计小写
	private String mPayMode = ""; // 补费形式
	private String mBankName = ""; // 开户行
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

	public static final String VTS_NAME = "BftzsP000170.vts"; // 模板名称

	public BfNoticePrintBL() {
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
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_EDORACCEPT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BfNoticePrintBL";
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
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保全受理号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 取打印通知书类型
		String tEdorItem = mLOPRTManagerSchema.getCode();
		if (tEdorItem == null || tEdorItem.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tEdorItem.trim().equals(PrintManagerBL.CODE_PEdorPayInfo)) {
			CError tError = new CError();
			tError.moduleName = "BfNoticePrintBL";
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
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据验证失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema = tLPEdorAppDB.getSchema();
		
		mContNo=tLPEdorAppSchema.getOtherNo();

		String sEdorState = tLPEdorAppSchema.getEdorState();
		if (sEdorState != null && sEdorState.trim().equals("0")) {
			CError.buildErr(this, "该保全已确认生效, 不允许打印补费通知书！");
			return false;
		}

		return true;
	}

	// 获取数据库数据
	private boolean getBaseData() {
		ExeSQL tExeSQL = new ExeSQL();
		tXmlExport.createDocument("补费通知书"); // 初始化xml文档
		tXmlExport.addDisplayControl("displayCustomer");
		tXmlExport.addDisplayControl("displayNotice");
		tXmlExport.addDisplayControl("displayTail");

		mOperator = tLPEdorAppSchema.getOperator(); // 经办人
		mAppName1=tLPEdorAppSchema.getEdorAppName();
		// 判断补费方式
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select codename from ldcode where codetype = 'edorgetpayform' and code = '"
				+ "?code?" + "'");
		sqlbv.put("code", tLPEdorAppSchema.getGetForm());
		mPayMode = tExeSQL.getOneValue(sqlbv);
		if ("4".equals(tLPEdorAppSchema.getGetForm())
				|| "7".equals(tLPEdorAppSchema.getGetForm())) {
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("select bankname from ldbank where bankcode = '"
					+ "?bankcode?" + "'");
			sqlbv1.put("bankcode", tLPEdorAppSchema.getBankCode());
			mBankName = tExeSQL.getOneValue(sqlbv1);
			mAccount = tLPEdorAppSchema.getBankAccNo();
			mAccountName = tLPEdorAppSchema.getAccName();
		}else
		{
			tXmlExport.addDisplayControl("displayCash");
		}
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("SELECT GetNoticeNo From LJSPay where OtherNoType = '10' and OtherNo = '"
				+ "?mEdorAcceptNo?" + "'");
		sqlbv2.put("mEdorAcceptNo", mEdorAcceptNo);
		mNoticeNo = tExeSQL.getOneValue(sqlbv2); // 通知书号
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
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		tEdorType = tLPEdorItemSchema.getEdorType();
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("Select edorname from lmedoritem where edorcode = '"
				+ "?tEdorType?" + "' and appobj='I' ");
		sqlbv3.put("tEdorType", tEdorType);
		mChgItem = tExeSQL.getOneValue(sqlbv3); // 变更项目
		
		//累计补退费
		mSumMoney =BqNameFun.getSumEdorContMoney(tLPEdorItemSchema, mContNo);
		if (!"".equals(mSumMoney)) {
			if (Double.parseDouble(mSumMoney) < 0) {
				mSumMoney = BqNameFun.getRound(mSumMoney.substring(1, mSumMoney
						.length()));
			}
			mMoney = PubFun.getChnMoney(Double.parseDouble(mSumMoney)); // 补费合计大写
			mSumMoney = BqNameFun.getRound(mSumMoney); // 补费合计小写
		}

		// 生成-年-月-日格式的日期
		StrTool tSrtTool = new StrTool();
		mSysDate = tSrtTool.getYear() + "年" + tSrtTool.getMonth() + "月"
				+ tSrtTool.getDay() + "日";
		return true;
	}

	private boolean preparePrintData() {
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		// 模版自上而下的元素
		texttag.add("AppntName", tLCContSchema.getAppntName());// 投保人姓名
		texttag.add("InsuredName", tLCContSchema.getInsuredName());// 被保人姓名
		texttag.add("ContNo", mContNo); // 投保人
		texttag.add("PayNoticeNo", mNoticeNo); // 通知书号
		texttag.add("EdorAppNo", mEdorAcceptNo); // 保全受理号
		texttag.add("AppName", mAppName1); // 客户
		texttag.add("AppDate", mAppDate); // 申请日期
		texttag.add("EdorName", mChgItem); // 变更项目

		texttag.add("CapitalReTotalMoney", mMoney); // 补费合计大写
		texttag.add("ReTotalMoney", mSumMoney); // 补费合计小写
		texttag.add("PayForm", mPayMode); // 补费形式
		if ("4".equals(tLPEdorAppSchema.getGetForm())
				|| "7".equals(tLPEdorAppSchema.getGetForm())) {
			texttag.add("BankName", mBankName); // 开户行
			texttag.add("BankAccNo", mAccount); // 帐号
			texttag.add("BankAccName", mAccountName); // 户名
			tXmlExport.addDisplayControl("displayBank");
		}

	       //添加代理人信息
	    BqNameFun.AddBqNoticeAgentInfo(tLCContSchema, texttag);
		texttag.add("PrintDate", mSysDate); // 打印日期
		
		logger.debug("大小" + texttag.size());
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		String uLanguage = PrintTool.getCustomerLanguage(tLCContSchema.getAppntNo());
		if(uLanguage!=null && !"".equals(uLanguage)){
			tXmlExport.setUserLanguage(uLanguage);//用户语言
		}
		tXmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
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
		BfNoticePrintBL tBfNoticePrintBL = new BfNoticePrintBL();
		if (!tBfNoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("\t@> BfNoticePrintBL 打印失败");
		}
	} // function main end
}
