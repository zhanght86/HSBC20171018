package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 万能险结算状态报告
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
public class InsuAccMonthPrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(InsuAccMonthPrintBL.class);
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
	private String mAppntNo; // 投保人客户号
	private String mRiskName; // 险种名称
	private String mInsuredName; // 被保人姓名
	private String mCValiDate; // 保单生效日期

	private String mContNo; // 保单号
	private String mPolNo; // 险种号
	private String mBalaDate; // 本次结算日期

	/** 结算利率 */
	private String mRate;
	/** 上次结转金额 */
	private String mLastAccBala = "";
	/** 本月收入合计 */
	private double mSumGet = 0.0;
	/** 本月收入支出 */
	private double mSumPay = 0.0;
	/** 累计借款金额以及利息 */
	private double mSumLoanMoney = 0.0;
	private double mSumPreIntrest = 0.0;
	/** 还款日期 */
	private String mSPayDate = "";

	public InsuAccMonthPrintBL() {
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
			tError.moduleName = "InsuAccMonthPrintBL";
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
			tError.moduleName = "InsuAccMonthPrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送保单号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mPolNo = mLOPRTManagerSchema.getStandbyFlag1();
		if (mPolNo == null || mPolNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InsuAccMonthPrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送险种号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mBalaDate = mLOPRTManagerSchema.getStandbyFlag2();
		if (mBalaDate == null || mBalaDate.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InsuAccMonthPrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送结算日期失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mRate = mLOPRTManagerSchema.getStandbyFlag3();
		if (mBalaDate == null || mBalaDate.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InsuAccMonthPrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送结算日期失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLOPRTManagerSchema.getStandbyFlag4() == null
				|| mBalaDate.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InsuAccMonthPrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据传送失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mSumPreIntrest = Double.parseDouble(mLOPRTManagerSchema
				.getStandbyFlag4());
		if (mLOPRTManagerSchema.getStandbyFlag5() == null
				|| mLOPRTManagerSchema.getStandbyFlag5().trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InsuAccMonthPrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据传送失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mSumLoanMoney = Double.parseDouble(mLOPRTManagerSchema
				.getStandbyFlag5());
		mSumLoanMoney += mSumPreIntrest;
		// mLastAccBala=Double.parseDouble(mLOPRTManagerSchema.getStandbyFlag6());
		if (mLOPRTManagerSchema.getStandbyFlag7() == null
				|| mLOPRTManagerSchema.getStandbyFlag7().trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InsuAccMonthPrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据传送失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLastAccBala = mLOPRTManagerSchema.getStandbyFlag7();
		return true;
	}

	private boolean dealData() {

		// 查询保单信息
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "InsuAccMonthPrintBL";
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
			tError.moduleName = "InsuAccMonthPrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCPolSchema.setSchema(tLCPolDB.getSchema());
		mAppntName = tLCContSchema.getAppntName(); // 投保人姓名
		mAppntNo = tLCContSchema.getAppntNo();
		if (mAppntName == null || mAppntName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "InsuAccMonthPrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "投保人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInsuredName = tLCContSchema.getInsuredName();
		mCValiDate = tLCPolSchema.getCValiDate();
		mRiskName = getRiskName(tLCPolSchema.getRiskCode());
		// 查询投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mContNo);
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "InsuAccMonthPrintBL";
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
			tError.moduleName = "InsuAccMonthPrintBL";
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

		return true;
	}

	private boolean preparePrintData() {
		String tStartDate = PubFun.calDate(mBalaDate, -1, "M", "");
		// 判断是否是首个月度及如：CValiDate=2007-9-27 则此时的结算日为2007-10-1
		// 那么此时的 tStartDate为生效日(CValiDate)，如不是首个月度则tStartDate为本次结算日前一月的对应日
		String tFlag = isFirstMonth(mBalaDate);
		if ("Y".equals(tFlag)) {
			tStartDate = PubFun.calDate(mCValiDate, -1, "D", "");
		}
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tSQL = "select * from lcinsureacctrace a where a.polno = '"
				+ "?mPolNo?" + "' and " + " a.paydate <= '" + "?mBalaDate?"
				+ "' and a.paydate>'" + "?tStartDate?"
				+ "' order by paydate,feecode asc";
		logger.debug(tSQL);
		sqlbv1.sql(tSQL);
		sqlbv1.put("mPolNo", mPolNo);
		sqlbv1.put("mBalaDate", mBalaDate);
		sqlbv1.put("tStartDate", tStartDate);
		tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv1);
		if (tLCInsureAccTraceSet.size() < 1 || tLCInsureAccTraceSet == null) {
			CError tError = new CError();
			tError.moduleName = "InsuAccMonthPrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询帐户轨迹失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String[] tContListTitle = new String[6];
		for (int iTitle = 0; iTitle < 6; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}
		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);
			if (tLCInsureAccTraceSchema.getMoney() > 0) {
				mSumGet += tLCInsureAccTraceSchema.getMoney(); // 获得累计收入
			} else {
				mSumPay += tLCInsureAccTraceSchema.getMoney(); // 获得累计支出
			}
			strLine = new String[6];
			logger.debug("**********"
					+ tLCInsureAccTraceSchema.getPayDate());
			strLine[0] = BqNameFun.getFDate(tLCInsureAccTraceSchema
					.getPayDate());
			strLine[1] = mRate;
			strLine[2] = getMoneyName(tLCInsureAccTraceSchema.getMoneyType());
			strLine[3] = getAccGet(tLCInsureAccTraceSchema.getMoney());
			strLine[4] = getAccPay(tLCInsureAccTraceSchema.getMoney());
			strLine[5] = getAccAmnt(tLCInsureAccTraceSchema);
			tContListTable.add(strLine);

		}
		String nStartDate = "";
		if ("Y".equals(tFlag)) {
			nStartDate = mCValiDate;
		} else {
			nStartDate = tStartDate;
		}

		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String tSql = "select sum(money) from lcinsureacctrace a where a.polno = '"
				+ "?mPolNo?"
				+ "' and "
				+ " a.paydate <= '"
				+ "?nStartDate?"
				+ "' and a.paydate>='" + "?mCValiDate?" + "'";
		logger.debug(tSql);
		sqlbv2.sql(tSql);
		sqlbv2.put("mPolNo", mPolNo);
		sqlbv2.put("nStartDate", nStartDate);
		sqlbv2.put("mCValiDate", mCValiDate);
		mLastAccBala = tExeSQL.getOneValue(sqlbv2);

		String printName = "万能险个人帐户对帐单（月）";
		XmlExportNew xmlExport = new XmlExportNew();
		xmlExport.createDocument(printName); // 初始化xml文档
		String uLanguage = PrintTool.getCustomerLanguage(mAppntNo);
		if(uLanguage!=null && !"".equals(uLanguage)){
			xmlExport.setUserLanguage(uLanguage);
		}
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例

		texttag.add("Post", mZipCode);
		texttag.add("Address", mPostalAddress);
		texttag.add("ContNo", mContNo);
		texttag.add("AppntName", mAppntName);
		texttag.add("RiskName", mRiskName);
		texttag.add("InsuredName", mInsuredName);
		texttag.add("CValiDate", BqNameFun.getFDate(mCValiDate));
		texttag.add("BalaDate", BqNameFun.getFDate(mBalaDate));
		texttag.add("LastAccBala", mLastAccBala);
		texttag.add("SumGet", BqNameFun.getRound(mSumGet));
		texttag.add("SumPay", BqNameFun.getRound(-mSumPay));

		// 如果保单借款，则需要显示如下信息
		if (mSumLoanMoney > 0.0) {
			texttag.add("SumLoanMoney", BqNameFun.getRound(mSumLoanMoney));
			texttag.add("SPayDate", BqNameFun.getFDate(mSPayDate));
			xmlExport.addDisplayControl("displayLoan");
		}

		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		xmlExport.addListTable(tContListTable, tContListTitle);
		mResult.clear();
		mResult.addElement(xmlExport);
		return true;
	}

	/**
	 * 判断是否是首年计算
	 * 
	 * @author PST
	 * @param tBalaDate
	 *            此时的结算日
	 */
	private String isFirstMonth(String tBalaDate) {

		String tFlag = "N";
		int tIntv = 0;
		tBalaDate = PubFun.calDate(tBalaDate, -1, "M", "");
		tIntv = PubFun.calInterval(mCValiDate, tBalaDate, "D");
		if (tIntv <= 0) {
			tFlag = "Y";
		}
		return tFlag;

	}

	/**
	 * 获取本月帐户收入
	 * 
	 * @param tMoney
	 * @return
	 * @author PST
	 */
	private String getAccGet(double tMoney) {
		String tAccGet = "";
		if (tMoney > 0) {
			tAccGet = Double.toString(tMoney);
			return tAccGet = BqNameFun.getRoundMoney(tAccGet);

		} else {
			return "0.00";
		}

	}

	/**
	 * 获取本月帐户支出
	 * 
	 * @param tMoney
	 * @return
	 * @author PST
	 */
	private String getAccPay(double tMoney) {
		String tAccPay = "";
		if (tMoney < 0) {
			tAccPay = Double.toString(-tMoney);
			return tAccPay = BqNameFun.getRoundMoney(tAccPay);
		} else {
			return "0.00";
		}

	}

	/**
	 * 获得即时保额，保额为此时的帐户价值两倍
	 * 
	 * @param tLCInsureAccTraceSchema
	 * @return
	 * @author PST
	 */
	private String getAccAmnt(LCInsureAccTraceSchema tLCInsureAccTraceSchema) {
		String tAccAmnt = "";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String tSQL = "select sum(money) from LCInsureAccTrace where polno='"
				+ "?mPolNo?" + "' and paydate>='" + "?mCValiDate?" + "' and paydate<='"
				+ "?paydate?" + "'";
		sqlbv3.sql(tSQL);
		sqlbv3.put("mPolNo", mPolNo);
		sqlbv3.put("mCValiDate", mCValiDate);
		sqlbv3.put("paydate", tLCInsureAccTraceSchema.getPayDate());
		ExeSQL tExeSQL = new ExeSQL();
		tAccAmnt = tExeSQL.getOneValue(sqlbv3);
		if ("".equals(tAccAmnt)) {
			return "0.00";
		}
		return tAccAmnt = BqNameFun.getRoundMoney(tAccAmnt);
	}

	// 保单状态显示需求尚未明确，暂时这样
	private String getState(String tContNo, String tPolNo) {
		String tStatement = "承保";
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		String strsql = "select case when appflag = '0' then '未承保' else (case when appflag = '4' then '终止' else '承保' end) end from lcpol where polno = '"
				+ "?tPolNo?" + "'";
		sqlbv4.sql(strsql);
		sqlbv4.put("tPolNo", tPolNo);
		tssrs = texesql.execSQL(sqlbv4);
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			tStatement = tssrs.GetText(1, 1);
		}
		return tStatement;
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
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		String tStr = "select riskshortname from lmrisk where riskcode='"
				+ "?tRiskCode?" + "'";
		sqlbv5.sql(tStr);
		sqlbv5.put("tRiskCode", tRiskCode);
		ExeSQL tExeSQL = new ExeSQL();
		tRiskName = tExeSQL.getOneValue(sqlbv5);
		if ("".equals(tRiskName)) {
			return "";
		}
		return tRiskName;
	}

	/**
	 * 根据金额类型查询金额名称
	 * 
	 * @param tMoneyType
	 *            金额类型
	 * @return tMoneyName 金额名称
	 * @author PST
	 */
	private String getMoneyName(String tMoneyType) {
		String tMoneyName = "";
		if ("".equals(tMoneyType)) {
			return "";
		}
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		String tStr = "select codename from ldcode where codetype='finfeetype' and code='"
				+ "?tMoneyType?" + "'";
		sqlbv6.sql(tStr);
		sqlbv6.put("tMoneyType", tMoneyType);
		ExeSQL tExeSQL = new ExeSQL();
		tMoneyName = tExeSQL.getOneValue(sqlbv6);
		if ("".equals(tMoneyName)) {
			return "";
		}
		return tMoneyName;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("test begin:");
		InsuAccMonthPrintBL tInsuAccMonthPrintBL = new InsuAccMonthPrintBL();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		String tPrtSeq = "8101100000095";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		if (!tInsuAccMonthPrintBL.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}
}
