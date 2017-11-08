package com.sinosoft.lis.f1print;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全撤销通知书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：pst
 * @version：1.0, 1.1
 * @CreateDate：2005-08-05, 2006-08-23,2009-02-14
 */
public class BqCancelNoticePrintBL implements PrintService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger
			.getLogger(BqCancelNoticePrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	@SuppressWarnings("unused")
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	@SuppressWarnings("unused")
	private String mOperator = ""; // 经办人

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LCContSchema mLCContSchema = new LCContSchema();

	// 基本的标签信息

	/** 险种编码 */
	@SuppressWarnings("unused")
	private String mRiskCode;

	/** 合同号 */
	private String mContNo;

	/** 邮政编码 */
	private String mPost;

	/** 地址 */
	private String mAddress;

	/** 投保人姓名 */
	private String mName;

	/** 声明TextTag的实例 */
	TextTag texttag = new TextTag();

	public BqCancelNoticePrintBL() {
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
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		/** 获得标签数据 */
		if (!getBaseData()) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
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
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);

			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_CONT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tNoticeType = mLOPRTManagerSchema.getCode();
		if (tNoticeType == null || tNoticeType.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tNoticeType.trim().equals(PrintManagerBL.CODE_PEdorCancel)) {
			CError tError = new CError();
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误：保全撤销通知书！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = mLOPRTManagerSchema.getOtherNo();

		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单险种号失败！";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/** 获得标签数据 */
	private boolean getBaseData() {
		mOperator = mLOPRTManagerSchema.getReqOperator(); // 经办人

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLOPRTManagerSchema.getStandbyFlag1());
		tLPEdorItemDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		LPEdorItemSchema tLPEdorItemSchema = tLPEdorItemDB.query().get(1);

		if (tLPEdorItemSchema == null) {
			CError tError = new CError();
			tError.moduleName = "BqRefuseNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "获取数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCContDB tLCContDB = new LCContDB();

		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		// 查询投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mContNo);
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "查询投保人信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCAppntSchema = tLCAppntDB.getSchema();
		mName = tLCAppntSchema.getAppntName();
		// logger.debug("投保人姓名："+mName);
		if (mName == null || mName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "投保人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询投保人地址及保单信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tSQLAD = "select * from lcaddress l "
				+ " where l.customerno =(select c.appntno from lcappnt c where c.contno ='"
				+ "?mContNo?" + "') " + " and l.addressno ='"
				+ "?addressno?" + "'";
		sqlbv.sql(tSQLAD);
		sqlbv.put("mContNo", mContNo);
		sqlbv.put("addressno", tLCAppntSchema.getAddressNo());
		tLCAddressSchema = tLCAddressDB.executeQuery(sqlbv).get(1);
		if (tLCAddressSchema == null) {
			CError tError = new CError();
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "投保人地址信息为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mPost = tLCAddressSchema.getZipCode();

		mAddress = tLCAddressSchema.getPostalAddress();
		if (mAddress == null || mAddress.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "BqCancelNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "投保人地址为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		texttag.add("Post", mPost);
		texttag.add("Address", mAddress);
		texttag.add("AppntName", mName);
		texttag.add("ContNo", mContNo);
		texttag.add("Branch", BqNameFun.getComM(mContNo.substring(0, 4)));
		// texttag.add("Operator", CodeNameQuery.getOperator(mOperator)); // 经办人
		if (!"".equals(mLOPRTManagerSchema.getStandbyFlag2())
				&& mLOPRTManagerSchema.getStandbyFlag2() != null) {
			texttag.add("CancelReason", BqNameFun.getCodeName(
					mLOPRTManagerSchema.getStandbyFlag2().substring(0, 3),
					"edorcancelsreason")); //   
		} else {
			texttag.add("CancelReason", "");
		}

		texttag.add("EdorType", BqNameFun.getEdorName(tLPEdorItemSchema)); // 
		// 添加代理人信息
		BqNameFun.AddBqNoticeAgentInfo(mLCContSchema, texttag);
		texttag.add("PrintDate", BqNameFun.getChDate(PubFun.getCurrentDate()));

		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean preparePrintData() {
		String printName = "保全撤销通知书";
		XmlExportNew xmlExport = new XmlExportNew();
		xmlExport.createDocument(printName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(mLCContSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) {
			xmlExport.setUserLanguage(uLanguage);//用户语言
		}
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(xmlExport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "bq";
		mGlobalInput.ManageCom = "86";
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerDB.setOtherNo("86130020080219000222");
		tLOPRTManagerDB.setCode("BQ01");
		tLOPRTManagerSchema = tLOPRTManagerDB.query().get(1);
		VData tVData = new VData();
		tVData.add(tLOPRTManagerSchema);
		tVData.add(mGlobalInput);
		BqCancelNoticePrintBL tBqCancelNoticePrintBL = new BqCancelNoticePrintBL();
		tBqCancelNoticePrintBL.submitData(tVData, "PRINT");
		tBqCancelNoticePrintBL = null;

	}
}
