package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.bq.EdorCalZT;
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
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Copyright: Copyright (c) 2006 Sinosoft, Co.,Ltd. All Rights Reserved
 * </p>
 * <p>
 * Company: 中科软科技股份有限公司
 * </p>
 * 
 * @todo: P005010万能险结算状态报告书（903）打印后台
 * @author: liuxiaosong<liuxs@sinosoft.com.cn>
 * @version:1.0
 */
public class InsuAccNoticePrint903BL implements PrintService {
private static Logger logger = Logger.getLogger(InsuAccNoticePrint903BL.class);
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
	private String mSex; // 投保人性别
	private String mName; // 投保人姓名，带称谓
	private String mInsuredName; // 被保人姓名
	private String mCValiDate; // 保单生效日期
	private String mRptDate; // 报告日期
	private String mAgentCode; // 业务员编码

	private String mContState = ""; // 保单状态
	private String mPayMoney = ""; // 客户缴费金额(分节输出的字符串)
	private String mDeductMoney = ""; // 初始扣除费用(分节输出的字符串)
	private String mValue = ""; // 保单价值(分节输出的字符串)

	private String mContNo; // 保单号
	private String mPolNo; // 险种号
	private String mBalaDate; // 本次结算日期

	private String mCurrentDate; // 当前日期

	public static final String VTS_NAME = "InsuAccNoticePrint903.vts"; // 模板名称

	public InsuAccNoticePrint903BL() {
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
			tError.moduleName = "InsuAccNoticePrintBL";
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
			tError.moduleName = "InsuAccNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送保单号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mPolNo = mLOPRTManagerSchema.getStandbyFlag1();
		if (mPolNo == null || mPolNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InsuAccNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送险种号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mBalaDate = mLOPRTManagerSchema.getStandbyFlag2();
		if (mBalaDate == null || mBalaDate.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InsuAccNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送结算日期失败！";
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
			tError.moduleName = "InsuAccNoticePrintBL";
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
			tError.moduleName = "InsuAccNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCPolSchema.setSchema(tLCPolDB.getSchema());
		mAppntName = tLCContSchema.getAppntName(); // 投保人姓名
		mName = mAppntName;
		if (mAppntName == null || mAppntName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "InsuAccNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "投保人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mSex = tLCContSchema.getAppntSex(); // 投保人性别
		if (mSex.trim().equals("0")) {
			mName = mAppntName.trim() + "先生";
		} else if (mSex.trim().equals("1")) {
			mName = mAppntName.trim() + "女士";
		} else {
			mName = mAppntName.trim() + "女士/先生";
		}
		mInsuredName = tLCContSchema.getInsuredName();
		mCValiDate = tLCPolSchema.getCValiDate();
		mRptDate = mLOPRTManagerSchema.getMakeDate();
		mCurrentDate = PubFun.getCurrentDate();
		// 查询投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mContNo);
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "InsuAccNoticePrintBL";
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
			tError.moduleName = "InsuAccNoticePrintBL";
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
		mAgentCode = tLCContSchema.getAgentCode();
		mContState = getState(mContNo, mPolNo);
		double tPrem = tLCPolSchema.getPrem();
		mPayMoney = BqNameFun.getPartRound(tPrem); // 客户缴费金额
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String strsql = "select (case when sum(money) is not null then sum(money)  else 0 end) from lcinsureacctrace where polno = '"
				+ "?mPolNo?"
				+ "' and moneytype = 'BF' and paydate = '"
				+ "?mCValiDate?" + "'";
		sqlbv1.sql(strsql);
		sqlbv1.put("mPolNo", mPolNo);
		sqlbv1.put("mCValiDate", mCValiDate);
		tssrs = texesql.execSQL(sqlbv1);
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			double tDeductMoney = tPrem
					- Double.parseDouble(tssrs.GetText(1, 1));
			mDeductMoney = BqNameFun.getPartRound(tDeductMoney); // 初始扣除费用
		}
		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String strsql = "select a.paydate ,(case when a.rate is not null then a.rate  else 0 end) ,a.base,a.riskcode," // 日期，利率，保单期初价值,险种编码
				+ " sum(a.money)+a.base," // 期末保单价值=各个费用之和+期初值
				+ " sum(case when a.moneytype='LX' and a.feecode = '000000' then a.money else 0 end)," // --利息
				+ " sum(case when a.moneytype='GL' and a.feecode like '___002' then a.money else 0 end)," // 保单管理费
				+ " sum(case when a.moneytype='GL' and a.feecode like '___003' then a.money else 0 end)," // 身故风险保费
				+ " sum(case when a.moneytype='GL' and a.feecode like '___004' then a.money else 0 end) " // 保单持续奖金
				+ " from (select paydate,feecode,moneytype,money,(select riskname from lmrisk where riskcode=lc.riskcode) riskcode,"
				+ " (case when (select sum(money) from lcinsureacctrace where polno = lc.polno and paydate < lc.paydate) is not null then (select sum(money) from lcinsureacctrace where polno = lc.polno and paydate < lc.paydate)  else 0 end) base,"
				+ " ((case when (select rate from lminsuaccrate "
				+ " where insuaccno = lc.insuaccno and baladate = lc.paydate"
				+ " and rateintv = 'Y' and riskcode = lc.riskcode) is not null then (select rate from lminsuaccrate "
				+ " where insuaccno = lc.insuaccno and baladate = lc.paydate"
				+ " and rateintv = 'Y' and riskcode = lc.riskcode)  else (select rate from LMAccGuratRate g"
				+ " where g.riskcode = lc.riskcode"
				+ " and g.insuaccno = lc.insuaccno"
				+ " and g.ratestartdate <= lc.paydate"
				+ " and g.rateenddate >= lc.paydate) end)) * 100 rate "
				+ " from lcinsureacctrace lc" + " where polno = '"
				+ "?mPolNo?"
				+ "'" + " and moneytype <> 'BF' and paydate <= '"
				+ "?mBalaDate?"
				+ "'";
		if (BqNameFun.getYear(mBalaDate) - BqNameFun.getYear(mCValiDate) == 1) {
			// 如果是第一年，比如12月28日生效，12月31日会有一次结算，当次年12月31日打印报告书时，应有13条记录
			strsql += " and paydate > add_months('"
					+ "?mBalaDate?"
					+ "', -13)) a"
					+ " group by a.paydate, a.rate, a.base ,a.riskcode order by a.paydate";
		} else {
			strsql += " and paydate > add_months('"
					+ "?mBalaDate?"
					+ "', -12)) a"
					+ " group by a.paydate, a.rate, a.base ,a.riskcode order by a.paydate";
		}
		// logger.debug("__________________\n" + strsql);
		sqlbv2.sql(strsql);
		sqlbv2.put("mPolNo", mPolNo);
		sqlbv2.put("mBalaDate", mBalaDate);
		tssrs = texesql.execSQL(sqlbv2);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "查询失败！");
			return false;
		}
		String[] tContListTitle = new String[9];
		for (int iTitle = 0; iTitle < 9; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}
		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		double tEndCashValue = 0;
		EdorCalZT tEdorCalZT = null;
		double sumEndValue = 0;
		int nInterval = PubFun.calInterval(mCValiDate, mBalaDate, "Y");
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[9];

			// 最后一期的期末保单价值为保单最终价值
			if (i == tssrs.getMaxRow()) {
				sumEndValue = Double.parseDouble(tssrs.GetText(i, 5));
			}

			// tEdorCalZT = new EdorCalZT(mGlobalInput);
			// //计算保单现金价值
			// tEndCashValue = tEdorCalZT.getActuMoney(tssrs.GetText(i, 4),
			// Double.parseDouble(tssrs.GetText(i, 5)), nInterval,
			// "CalFee");
			// if (tEndCashValue == -1)
			// {
			// mErrors.copyAllErrors(tEdorCalZT.mErrors);
			// return false;
			// }

			// 组织一行打印记录
			strLine[0] = tssrs.GetText(i, 1); // 结算日期
			strLine[1] = BqNameFun.getPartRound(Double.parseDouble(tssrs
					.GetText(i, 3))); // 期初保单价值
			strLine[2] = BqNameFun.getPartRound(Double.parseDouble(tssrs
					.GetText(i, 2))); // 利率
			strLine[3] = BqNameFun.getPartRound(Double.parseDouble(tssrs
					.GetText(i, 6))); // 利息
			strLine[4] = BqNameFun.getPartRound(Double.parseDouble(tssrs
					.GetText(i, 7))); // 保单管理费
			strLine[5] = BqNameFun.getPartRound(Double.parseDouble(tssrs
					.GetText(i, 8))); // 身故风险
			strLine[6] = BqNameFun.getPartRound(Double.parseDouble(tssrs
					.GetText(i, 9))); // 保单持续奖金
			strLine[7] = "0.00";// 贷款金额
			strLine[8] = BqNameFun.getPartRound(Double.parseDouble(tssrs
					.GetText(i, 5))); // 期末保单价值
			// strLine[8] = "" + tEndCashValue; //保单现金价值,903险种的保单价值和现金价值相等
			tContListTable.add(strLine);
		}

		// 险种名称
		String tRiskName = tssrs.GetText(1, 4);

		// 保单最终价值，前面的查询中，BASE中不包括mBalaDate，在此将AD调整加入
		double tFinalValue = sumEndValue;
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		strsql = "select (case when money is not null then money  else 0 end) from lcinsureacctrace where polno = '"
				+ "?mPolNo?" + "' and moneytype = 'AD' and paydate = '" + "?mBalaDate?"
				+ "'";
		sqlbv3.sql(strsql);
		sqlbv3.put("mPolNo", mPolNo);
		sqlbv3.put("mBalaDate", mBalaDate);
		tssrs = texesql.execSQL(sqlbv3);
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			tFinalValue += Double.parseDouble(tssrs.GetText(1, 1));
		}
		mValue = BqNameFun.getPartRound(tFinalValue);
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		String[] allArr = BqNameFun.getAllNames(mAgentCode);

		texttag.add("District", allArr[BqNameFun.DISTRICT]);
		texttag.add("Depart", allArr[BqNameFun.DEPART]);
		texttag.add("Team", allArr[BqNameFun.TEAM]);
		texttag.add("AgentName", allArr[BqNameFun.AGENT_NAME]);
		texttag.add("CorAddress", allArr[BqNameFun.ADDRESS]);
		texttag.add("Post", allArr[BqNameFun.ZIPCODE]);
		texttag.add("ZipCode", mZipCode);
		texttag.add("Address", mPostalAddress);
		texttag.add("ContNo", mContNo);
		texttag.add("AppntName", mAppntName);
		texttag.add("Name", mName);
		texttag.add("InsuredName", mInsuredName);
		texttag.add("CValiDate", BqNameFun.getFDate(mCValiDate));
		texttag.add("RptDate", BqNameFun.getFDate(mRptDate));
		texttag.add("PrintDate", BqNameFun.getFDate(mCurrentDate));

		texttag.add("ContState", mContState);
		texttag.add("PayMoney", mPayMoney);
		texttag.add("DeductMoney", mDeductMoney);
		texttag.add("FinalValue", mValue);
		texttag.add("RiskName", tRiskName);
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, tContListTitle);
		// tXmlExport.outputDocumentToFile("d:\\", "ZdhtzztzsBzyzfbfP000840");
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
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

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("test begin:");
		InsuAccNoticePrint903BL tInsuAccNoticePrintBL = new InsuAccNoticePrint903BL();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		String tPrtSeq = "8102102175877";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		if (!tInsuAccNoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");

	}
}
