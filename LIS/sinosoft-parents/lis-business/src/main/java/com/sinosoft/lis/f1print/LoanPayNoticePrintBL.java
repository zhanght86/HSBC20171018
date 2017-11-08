package com.sinosoft.lis.f1print;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 保单质押贷款还款通知书
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author PST
 * @version 1.0,
 */

public class LoanPayNoticePrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(LoanPayNoticePrintBL.class);

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

	/** 保单号 */
	private String mContNo; // 保单号

	/** 支公司的名称 */
	@SuppressWarnings("unused")
	private String mManageCom = "";

	/** 保单累计借款金额 */
	private String mSumMoney = "";

	@SuppressWarnings("unused")
	private String mCurrentDate = "";

	/** 保单借款日期 */
	private String mLoanDate = "";

	public static final String VTS_NAME = "ContLoanPayNotice.vts";

	public LoanPayNoticePrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
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
			mErrors.addOneError(new CError("没有得到足够的信息:流水号不能为空！"));
			return false;
		}
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		String mNoType = mLOPRTManagerSchema.getOtherNoType();

		if (mNoType == null || mNoType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_CONT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = mLOPRTManagerSchema.getOtherNo();

		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保全受理号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mSumMoney = mLOPRTManagerSchema.getStandbyFlag1();
		if (mSumMoney == null || mSumMoney.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单累计借款本息金额失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLoanDate = mLOPRTManagerSchema.getStandbyFlag2();
		if (mLoanDate == null || mLoanDate.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单借款日期失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean checkData() {
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean dealData() {
		// 查询保单信息
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCContSchema.setSchema(tLCContDB.getSchema());

		mAppntName = tLCContSchema.getAppntName(); // 投保人姓名
		if (mAppntName == null || mAppntName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "投保人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mRiskName = BqNameFun.getRiskNameByContNo(tLCContSchema.getContNo());

		mCurrentDate = PubFun.getCurrentDate();
		// 查询投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mContNo);
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
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
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询客户地址失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCAddressSchema = tLCAddressDB.getSchema();
		// 客户邮编
		mZipCode = tLCAddressSchema.getZipCode();

		mPostalAddress = tLCAddressSchema.getPostalAddress();
		if (mPostalAddress == null || mPostalAddress.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "投保人地址为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("mPostalAddress:" + mPostalAddress);
		
		String printName = "保单还款通知书";
		XmlExportNew xmlExport = new XmlExportNew();
		xmlExport.createDocument(printName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCAppntSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage))
			xmlExport.setUserLanguage(uLanguage);//用户语言
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		texttag.add("Post", mZipCode);
		texttag.add("Address", mPostalAddress);
		texttag.add("ContNo", mContNo);
		texttag.add("AppntName", mAppntName);
		texttag.add("RiskName", mRiskName);
		texttag.add("LoanDate", mLoanDate);
		texttag.add("SumMoney", mSumMoney);
		texttag.add("LoanDay", "六个月");
		texttag.add("LoantoDate", PubFun.calDate(mLoanDate, 6, "M", ""));
		texttag.add("ManageCom", BqNameFun.getComM(mContNo.substring(0, 4)));
		texttag.add("Date", BqNameFun.getChDate(PubFun.getCurrentDate()));
		// 添加代理人信息
		BqNameFun.AddBqNoticeAgentInfo(tLCContSchema, texttag);
		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(xmlExport);

		return true;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "lee";
		LOPRTManagerSchema tt = new LOPRTManagerSchema();
		LOPRTManagerDB ttt = new LOPRTManagerDB();
		ttt.setPrtSeq("8100000065042");
		ttt.getInfo();
		tt.setSchema(ttt.getSchema());
		VData td = new VData();
		LoanPayNoticePrintBL gt = new LoanPayNoticePrintBL();
		td.add(tG);
		td.add(tt);
		gt.submitData(td, "PRINT");

	}
}
