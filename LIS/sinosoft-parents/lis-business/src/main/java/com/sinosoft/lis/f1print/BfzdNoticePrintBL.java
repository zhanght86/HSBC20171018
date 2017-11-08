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
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保费自垫通知书
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * 
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：pst
 * @version：1.0,2.0 能够垫够整期，则不显示垫交天数和失效日期
 * @CreateDate：2009-02-14
 */

public class BfzdNoticePrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(BfzdNoticePrintBL.class);

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

	private LCContSchema mLCContSchema = new LCContSchema();

	// 基本的标签信息

	/** 险种编码 */
	private String mRiskCode;

	/** 保单印刷号 */
	private String mPrtNo;

	/** 分红年度 */
	private String mBonusYear;

	/** 分红方式 */
	private String mBonusGetMode;

	/** 合同号 */
	private String mContNo;

	/** 邮政编码 */
	private String mPost;

	/** 地址 */
	private String mAddress;

	/** 投保人姓名 */
	private String mName;

	/** 被保人姓名 */
	private String mInsureName;

	/** 生效日 */
	private String mCValiDate;

	/** 本期末保险金额 */
	private String mAmnt;

	/** 当天 */
	private String mDay;

	/** 本次结算利率类型 */
	private String mRateIntv;

	/** 月结算日 */
	private String mBalaDate;

	/** 应交保费 */
	private String tSPolPrem = "";

	/** 现金价值 */
	private String tSPolCV = "";

	/** 垫交保费 */
	private String tAutoPayPrem = "";

	/** 垫交天数 */
	private String tPolLoanToDays = "";

	/** 剩余现价 */
	private String tLeaveCV = "";

	/** 余额可垫天数 */
	private String tLeaveCvDays = "";

	/** 保单失效日期，即垫至日期的下一天（估算） */
	private String tInvaliDate = "";

	/** 保单自垫日期 */
	private String tLoanDate = "";

	/** 保单自垫批次号 */
	private String tLoanNo = "";

	/** 声明TextTag的实例 */
	TextTag texttag = new TextTag();

	/** 声明ListTable的实例 */
	private ListTable tBonusListTable = new ListTable();

	private ExeSQL tExeSQL = new ExeSQL();

	public BfzdNoticePrintBL() {
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
			tError.moduleName = "BfzdNoticePrintBL";
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
		/** 获取表格数据 */
		if (!getLisTableData()) {
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
			tError.moduleName = "BfzdNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);

			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_CONT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BfzdNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tNoticeType = mLOPRTManagerSchema.getCode();
		if (tNoticeType == null || tNoticeType.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "BfzdNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tNoticeType.trim().equals(PrintManagerBL.CODE_PEdorAPPRE)) {
			CError tError = new CError();
			tError.moduleName = "BfzdNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误：保费自垫通知书！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = mLOPRTManagerSchema.getOtherNo();

		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BfzdNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单险种号失败！";
			this.mErrors.addOneError(tError);

			return false;
		}
		/** 应交保费 */
		tSPolPrem = mLOPRTManagerSchema.getStandbyFlag1();
		// tSPolPrem=tSPolPrem.substring(0, tSPolPrem.lastIndexOf("|"));

		/** 现金价值 */
		tSPolCV = mLOPRTManagerSchema.getStandbyFlag2();
		// tSPolCV=tSPolCV.substring(0, tSPolCV.lastIndexOf("|"));

		/** 垫交保费 */
		tAutoPayPrem = mLOPRTManagerSchema.getStandbyFlag3();
		// tAutoPayPrem=tAutoPayPrem.substring(0,
		// tAutoPayPrem.lastIndexOf("|"));

		/** 垫交天数 */
		tPolLoanToDays = mLOPRTManagerSchema.getStandbyFlag4();
		// tPolLoanToDays=tPolLoanToDays.substring(0,
		// tPolLoanToDays.lastIndexOf("|"));

		/** 剩余现价 */
		tLeaveCV = mLOPRTManagerSchema.getStandbyFlag5();
		// tLeaveCV=tLeaveCV.substring(0, tLeaveCV.lastIndexOf("|"));

		/** 余额可垫天数 */
		// tLeaveCvDays=mLOPRTManagerSchema.getStandbyFlag6();
		// tLeaveCvDays=tLeaveCvDays.substring(0,
		// tLeaveCvDays.lastIndexOf("|"));
		/** 保单失效日期，即垫至日期的下一天（估算） */
		tInvaliDate = mLOPRTManagerSchema.getStandbyFlag7();
		// tInvaliDate=tInvaliDate.substring(0, tInvaliDate.lastIndexOf("|"));

		tLoanNo = mLOPRTManagerSchema.getRemark();

		return true;
	}

	/** 获得标签数据 */
	private boolean getBaseData() {

		LCContDB tLCContDB = new LCContDB();

		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "BfzdNoticePrintBL";
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
			tError.moduleName = "BfzdNoticePrintBL";
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
			tError.moduleName = "BfzdNoticePrintBL";
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
			tError.moduleName = "BfzdNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "投保人地址信息为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mPost = tLCAddressSchema.getZipCode();

		mAddress = tLCAddressSchema.getPostalAddress();
		if (mAddress == null || mAddress.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "BfzdNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "投保人地址为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tSQL = "select loandate from loloan where edorno='" + "?tLoanNo?"
				+ "' and contno='" + "?mContNo?" + "'";
		sqlbv1.sql(tSQL);
		sqlbv1.put("tLoanNo", tLoanNo);
		sqlbv1.put("mContNo", mContNo);
		tLoanDate = tExeSQL.getOneValue(sqlbv1);
		if ("".equals(tLoanDate)) {
			CError tError = new CError();
			tError.moduleName = "BfzdNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "保单自垫起始日期出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		texttag.add("Post", mPost);
		texttag.add("Address", mAddress);
		texttag.add("AppntName", mName);
		texttag.add("ContNo", mContNo);
		texttag.add("Branch", BqNameFun.getComM(mContNo.substring(0, 4)));
		texttag.add("CvaliDate", mLCContSchema.getCValiDate());
		texttag.add("AutoPayDate", BqNameFun.getChDate(tLoanDate.substring(0,
				10)));
		texttag.add("PrintDate", BqNameFun.getChDate(PubFun.getCurrentDate()));

		// 添加代理人信息
		BqNameFun.AddBqNoticeAgentInfo(mLCContSchema, texttag);
		return true;
	}

	/** 获取表格数据 */

	private boolean getLisTableData() {

		tBonusListTable.setName("HL");

		// 将数据放入ListTable
		String strIO[] = null;

		// 险种名称 应交保费（元） 现金价值（元） 垫交保费（元） 垫交天数 当前现价余额 余额可垫天数 保单失效 日期
		// 为了自垫通知书准备数据，而字段又不够，采取拼串的方式记录即 PolNo+"-"+Data,将多张保单的数据存放在保单，用“|” 隔开
		// 打印管理表的附属字段
		strIO = new String[7];
		strIO[0] = BqNameFun.getRiskNameByContNo(mLCContSchema.getContNo());
		strIO[1] = BqNameFun.getRound(tSPolPrem);
		strIO[2] = BqNameFun.getRound(tSPolCV);
		strIO[3] = BqNameFun.getRound(tAutoPayPrem);
		if (tPolLoanToDays != null && !"0".equals(tPolLoanToDays)) {
			strIO[4] = (tPolLoanToDays);
		} else {
			strIO[4] = "---";
		}
		if (tLeaveCV != null && !"0".equals(tLeaveCV)) {
			strIO[5] = BqNameFun.getRound(tLeaveCV);
		} else {
			strIO[5] = "0.00";
		}

		if (tInvaliDate != null && !"".equals(tInvaliDate)) {
			strIO[6] = tInvaliDate;
		} else {
			strIO[6] = "---";
		}
		tBonusListTable.add(strIO);

		return true;
	}

	private boolean preparePrintData() {
		XmlExportNew xmlExport = new XmlExportNew();
		xmlExport.createDocument("保费自垫通知书"); // 初始化xml文档
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(mLCContSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		if (tBonusListTable.size() > 0) {
			String b_strIO[] = new String[7];
			xmlExport.addListTable(tBonusListTable, b_strIO);
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
		BfzdNoticePrintBL tBfzdNoticePrintBL = new BfzdNoticePrintBL();
		tBfzdNoticePrintBL.submitData(tVData, "PRINT");
		tBfzdNoticePrintBL = null;

	}

}
