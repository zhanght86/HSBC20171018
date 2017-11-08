package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 万能险终止报告
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @rewrite：pst
 * @version：1.0
 * @CreateDate：2007-11-19
 */
public class ContInvaliNoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(ContInvaliNoticePrintBL.class);
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

	private String mZipCode; // 邮编号
	private String mPostalAddress; // 地址
	private String mAppntName; // 投保人姓名
	private String mRiskName; // 险种名称

	private String mContNo; // 保单号
	private String mPolNo; // 险种号
	private String mBalaDate; // 本次结算日期

	/** 累计借款金额以及利息 */
	private double mSumLoanMoney = 0.0;
	private double mSumPreIntrest = 0.0;
	/** 保单终止日期 */
	private String mInvalidate = "";
	/** 支公司的名称 */
	private String mManageCom = "";

	private String mCurrentDate = ""; // 当前日期

	public static final String VTS_NAME = "ContInvaliNotice.vts"; // 模板名称

	public ContInvaliNoticePrintBL() {
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

		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContInvaliNoticePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}
		// 从数据库获得数据
		if (!dealData()) {
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
		return true;
	}

	// 校验数据
	private boolean checkData() {
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:流水号不能为空！"));
			return false;
		}
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (!tLOPRTManagerDB.getInfo()) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		mContNo = mLOPRTManagerSchema.getOtherNo();
		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContInvaliNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送保单号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mPolNo = mLOPRTManagerSchema.getStandbyFlag1();
		if (mPolNo == null || mPolNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContInvaliNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送险种号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mBalaDate = mLOPRTManagerSchema.getStandbyFlag2();
		if (mBalaDate == null || mBalaDate.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContInvaliNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送结算日期失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mSumPreIntrest = Double.parseDouble(mLOPRTManagerSchema
				.getStandbyFlag4());
		mSumLoanMoney = Double.parseDouble(mLOPRTManagerSchema
				.getStandbyFlag5());
		mSumLoanMoney += mSumPreIntrest;

		// 暂时为结算日
		mInvalidate = mBalaDate;

		return true;
	}

	private boolean dealData() {

		// 查询保单信息
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "ContInvaliNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCContSchema.setSchema(tLCContDB.getSchema());

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolDB.setPolNo(mPolNo);
		if (!tLCPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "ContInvaliNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCPolSchema.setSchema(tLCPolDB.getSchema());
		mAppntName = tLCContSchema.getAppntName(); // 投保人姓名
		if (mAppntName == null || mAppntName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "ContInvaliNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "投保人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mRiskName = getRiskName(tLCPolSchema.getRiskCode());
		mCurrentDate = PubFun.getCurrentDate();
		// 查询投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mContNo);
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "ContInvaliNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询投保人信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCAppntSchema = tLCAppntDB.getSchema();
		String tAddressNo = tLCAppntSchema.getAddressNo(); // 投保人地址号
		String tCustomerNo = tLCAppntSchema.getAppntNo(); // 投保人客户号

		// 查询投保人地址信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		tLCAddressDB.setCustomerNo(tCustomerNo);
		tLCAddressDB.setAddressNo(tAddressNo);
		if (!tLCAddressDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "ContInvaliNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询客户地址失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCAddressSchema = tLCAddressDB.getSchema();
		// 客户邮编
		mZipCode = tLCAddressSchema.getZipCode();
		// 客户地址
		CodeNameQuery tCodeNameQuery = new CodeNameQuery();
		mPostalAddress = tCodeNameQuery.getDistrict(tLCAddressSchema
				.getCounty())
				+ tLCAddressSchema.getPostalAddress();
		if (mPostalAddress == null || mPostalAddress.equals("null")) {
			mPostalAddress = "";
		}
		logger.debug("mPostalAddress:" + mPostalAddress);

		String tComCode = mGlobalInput.ComCode;
		// 获得当前登陆机构的名称
		mManageCom = getManageComName(tComCode);
		
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例

		texttag.add("Post", mZipCode);
		texttag.add("Address", mPostalAddress);
		texttag.add("ContNo", mContNo);
		texttag.add("AppntName", mAppntName);
		texttag.add("RiskName", mRiskName);
		texttag.add("Date", BqNameFun.getFDate(mCurrentDate));
		texttag.add("InValiDate", BqNameFun.getFDate(mInvalidate));
		texttag.add("ManageCom", BqNameFun.getFDate(mManageCom));
	       //添加代理人信息
	     BqNameFun.AddBqNoticeAgentInfo(tLCContSchema, texttag);
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		// tXmlExport.outputDocumentToFile("E:\\", "1234");
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	private boolean preparePrintData() {
		return true;
	}

	/**
	 * 获得当前登陆机构的名称
	 * 
	 * @author PST
	 * @param tComCode
	 * 
	 */

	private String getManageComName(String tComCode) {
		String tComName = "";
		if ("".equals(tComCode)) {
			return "";
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tStr = "select shortname from LDCom where comcode='" + "?tComCode?"
				+ "'";
		sqlbv.sql(tStr);
		sqlbv.put("tComCode", tComCode);
		ExeSQL tExeSQL = new ExeSQL();
		tComName = tExeSQL.getOneValue(sqlbv);
		if ("".equals(tComName)) {
			return "";
		}
		return tComName;

	}

	/**
	 * 根据险种编码查询险种名称
	 * 
	 * @param tRiskCode
	 *            险种编码
	 * @return tRiskName 险种名称
	 * @author PST
	 */
	private String getRiskName(String tRiskCode) {
		String tRiskName = "";
		if ("".equals(tRiskCode)) {
			return "";
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tStr = "select riskshortname from lmrisk where riskcode='"
				+ "?tRiskCode?" + "'";
		sqlbv.sql(tStr);
		sqlbv.put("tRiskCode", tRiskCode);
		ExeSQL tExeSQL = new ExeSQL();
		tRiskName = tExeSQL.getOneValue(sqlbv);
		if ("".equals(tRiskName)) {
			return "";
		}
		return tRiskName;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("test begin:");
		ContInvaliNoticePrintBL tContInvaliNoticePrintBL = new ContInvaliNoticePrintBL();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		String tPrtSeq = "8101100000095";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		if (!tContInvaliNoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}
}
