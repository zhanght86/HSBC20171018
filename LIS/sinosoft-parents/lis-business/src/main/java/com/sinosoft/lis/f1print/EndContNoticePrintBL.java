package com.sinosoft.lis.f1print;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 公司解约通知书打印类BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @rewrite：liurx
 * @version：1.0
 * @CreateDate：2005-08-04
 */
public class EndContNoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(EndContNoticePrintBL.class);
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
	/** 保全申请信息 */
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private String mZipCode; // 邮编号
	private String mPostalAddress; // 地址

	private String mAppName; // 投保人姓名
	private String mSex; // 投保人性别

	private String mAgentCode; // 代理人代码
	private String mGroup; // 营销服务部
	private String mContNo; // 保单号
	private String mMoney; // 退保金
	private String mCMoney; // 退保金（大写）
	private String mMakeDate; // 系统时间
	private boolean displayBank = false;
	private String mBankName; //
	private String mAccountName; //
	private String mName; //
	private String mReason; //
	public static final String VTS_NAME = "GsjytzsP001580.vts"; // 模板名称

	public EndContNoticePrintBL() {
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
			tError.moduleName = "EndContNoticePrintBL";
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
		String mNoType = mLOPRTManagerSchema.getOtherNoType();
		if (mNoType == null || mNoType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EndContNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_CONT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EndContNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mContNo = mLOPRTManagerSchema.getOtherNo();
		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EndContNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送保单号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tCode = mLOPRTManagerSchema.getCode();
		if (tCode == null || tCode.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "EndContNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tCode.trim().equals(PrintManagerBL.CODE_PEdorEndCont)) {
			CError tError = new CError();
			tError.moduleName = "EndContNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送单据类型错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean dealData() {

		// 查询保单信息
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "EndContNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCContSchema.setSchema(tLCContDB.getSchema());
		mAppName = tLCContSchema.getAppntName(); // 投保人姓名
		if (mAppName == null || mAppName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "EndContNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "投保人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mSex = tLCContSchema.getAppntSex(); // 投保人性别
		if (mSex.trim().equals("0")) {
			mAppName = mAppName.trim() + "先生";
		} else if (mSex.trim().equals("1")) {
			mAppName = mAppName.trim() + "女士";
		} else {
			mAppName = mAppName.trim() + "女士/先生";
		}

		// 查询投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mContNo);
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "EndContNoticePrintBL";
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
			tError.moduleName = "EndContNoticePrintBL";
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

		mAgentCode = tLCContSchema.getAgentCode();

		mMakeDate = mLOPRTManagerSchema.getMakeDate();
		mMakeDate = BqNameFun.getFDate(mMakeDate);

		String tEdorAcceptNo = mLOPRTManagerSchema.getStandbyFlag1();
		if (tEdorAcceptNo == null || tEdorAcceptNo.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "EndContNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "未传入足够数据：打印管理表中保全受理号为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取得退保金
		String tMoney = mLOPRTManagerSchema.getStandbyFlag2();
		double value = Double.parseDouble(tMoney);
		value = Math.abs(value);
		mCMoney = PubFun.getChnMoney(value);
		mMoney = BqNameFun.getRound(value);

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(tEdorAcceptNo);
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.query();
		if (tLPEdorAppSet == null || tLPEdorAppSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema.setSchema(tLPEdorAppSet.get(1));
		String tBankCode = mLPEdorAppSchema.getBankCode();
		mAccountName = mLPEdorAppSchema.getBankAccNo();
		mName = mLPEdorAppSchema.getAccName();
		if (tBankCode != null && !tBankCode.trim().equals("")
				&& mAccountName != null && !mAccountName.trim().equals("")
				&& mName != null && !mName.trim().equals("")) {
			displayBank = true;
			mBankName = getCodeName(tBankCode, "bank");
		} else {
			displayBank = false;
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String strSql = "select trim(edorreasoncode) from lpedoritem "
				+ " where edoracceptno = '" + "?tEdorAcceptNo?" + "' "
				+ " and contno = '" + "?mContNo?" + "' and edortype = 'EA'";
		sqlbv.sql(strSql);
		sqlbv.put("tEdorAcceptNo", tEdorAcceptNo);
		sqlbv.put("mContNo", mContNo);
		SSRS tssrs = new SSRS();
		ExeSQL q_exesql = new ExeSQL();
		tssrs = q_exesql.execSQL(sqlbv);
		if (tssrs == null || tssrs.getMaxRow() < 1) {
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "无保全批改数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tReasonCode = tssrs.GetText(1, 1);
		if (tReasonCode == null || tReasonCode.trim().equals("")) {
			CError.buildErr(this, "公司解约原因为空！");
			return false;
		}
		// mReason = getCodeName(tReasonCode,"cancelreason");
		mReason = getEAReason(tReasonCode, "cancelreason");
		if (mReason != null && !mReason.trim().equals("")) {
			mReason = "因" + mReason + "，";
		}

		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		String[] allArr = BqNameFun.getAllNames(mAgentCode);
		texttag.add("AgentCode", mAgentCode);
		texttag.add("District", allArr[BqNameFun.DISTRICT]);
		texttag.add("Depart", allArr[BqNameFun.DEPART]);
		texttag.add("Team", allArr[BqNameFun.TEAM]);
		texttag.add("AgentName", allArr[BqNameFun.AGENT_NAME]);
		texttag.add("ManageComName", allArr[BqNameFun.COM]);
		texttag.add("SaleService", allArr[BqNameFun.SALE_SERVICE]);
		texttag.add("CompanyAddress", allArr[BqNameFun.ADDRESS]);
		texttag.add("CompanyPost", allArr[BqNameFun.ZIPCODE]);
		texttag.add("Post", mZipCode);
		texttag.add("Address", mPostalAddress);
		texttag.add("Name", mAppName);
		texttag.add("LCAppnt.ContNo", mContNo);
		texttag.add("Reason", mReason);
		texttag.add("CapitalMoney", mCMoney);
		texttag.add("Money", mMoney);
		if (displayBank) {
			texttag.add("bankName", mBankName);
			texttag.add("accountName", mAccountName);
			texttag.add("name", mName);
			tXmlExport.addDisplayControl("displayAppWork");
		} else {
			tXmlExport.addDisplayControl("displayAppWork2");
		}
		texttag.add("LPAppnt.Operator", CodeNameQuery
				.getOperator(mLPEdorAppSchema.getOperator())); // 经办人
		texttag.add("MakeDate", mMakeDate);

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}

		// tXmlExport.outputDocumentToFile("d:\\", "GsjytzsP001580");
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	public String getEAReason(String tCode, String tCodeType) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(tCode);
		tLDCodeDB.setCodeType(tCodeType);

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return "";
		}
		return tLDCodeDB.getCodeAlias();

	}

	/**
	 * 通过代码得到名称
	 * 
	 * @param tCode,tCodeType
	 * @return
	 * @throws Exception
	 */
	private String getCodeName(String tCode, String tCodeType) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(tCode);
		tLDCodeDB.setCodeType(tCodeType);

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return "";
		}
		return tLDCodeDB.getCodeName();
	}

	// 得到年、月、日格式的日期
	private String getFDate(String mDate) {
		String mFDate = "";
		if (mDate != null && !mDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(mDate.trim()));
			int tYear = tCalendar.get(Calendar.YEAR);
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			mFDate = String.valueOf(tYear) + "年" + String.valueOf(tMonth) + "月"
					+ String.valueOf(tDay) + "日";
		}
		return mFDate;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("test begin:");
		EndContNoticePrintBL tEndContNoticePrintBL = new EndContNoticePrintBL();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		String tPrtSeq = "810000000263500";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		if (!tEndContNoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}
}
