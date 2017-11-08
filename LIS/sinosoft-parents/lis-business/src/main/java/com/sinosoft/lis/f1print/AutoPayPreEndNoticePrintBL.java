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
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
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
 * Description: 自垫预终止通知书打印类BL
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
 * @CreateDate：2005-07-30
 */
public class AutoPayPreEndNoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(AutoPayPreEndNoticePrintBL.class);
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

	private String mAppName; // 投保人姓名
	private String mSex; // 投保人性别
	private String mContNo; // 保单号
	private String mPaytoDate; // 续期交费对应日
	private String mAutoPay; // 自垫本息和
	private String mCAutoPay; // 自垫本息和（大写）
	private String mCashValue; // 现金价值净额
	private String mCCashValue; // 现金价值净额（大写）

	private String mMakeDate;
	private String mAgentCode; // 业务员编码
	public static final String VTS_NAME = "ZdhtyzztzsBzyzfbfP000830.vts"; // 模板名称

	public AutoPayPreEndNoticePrintBL() {
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
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
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
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_CONT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mContNo = mLOPRTManagerSchema.getOtherNo();
		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送保单号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 单据类型: 自垫预终止通知书
		String tCode = mLOPRTManagerSchema.getCode();
		if (tCode == null || tCode.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传送单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tCode.trim().equals(PrintManagerBL.CODE_PEdorAPPREEND)) {
			CError tError = new CError();
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
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
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCContSchema.setSchema(tLCContDB.getSchema());
		mAppName = tLCContSchema.getAppntName(); // 投保人姓名
		if (mAppName == null || mAppName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
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
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
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
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
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

		// 续期交费对应日
		// mPaytoDate = mLOPRTManagerSchema.getStandbyFlag1();
		mPaytoDate = getPayToDate(mContNo);
		// mPaytoDate = new FDate().getString(PubFun.calDate(new
		// FDate().getDate(mPaytoDate), 1, "M", null));
		logger.debug("paytodate：" + mPaytoDate);
		mPaytoDate = getFDate(mPaytoDate);
		logger.debug("paytodate：" + mPaytoDate);
		// 取得保单自垫本息和
		String tAutoPay = mLOPRTManagerSchema.getStandbyFlag3();

		mCAutoPay = PubFun.getChnMoney(Double.parseDouble(tAutoPay));
		mAutoPay = BqNameFun.getRound(tAutoPay) + "元";

		double tDouble = Double.parseDouble(mLOPRTManagerSchema
				.getStandbyFlag2())
				- Double.parseDouble(mLOPRTManagerSchema.getStandbyFlag3());
		mCCashValue = PubFun.getChnMoney(tDouble);
		mCashValue = BqNameFun.getRound(tDouble) + "元";
		// if(tDouble < 0)
		// {
		// CError.buildErr(this,"现金价值净额为负！原因可能是：没有及时运行自垫预终止批处理程序");
		// return false;
		// }
		logger.debug(mContNo + "贷款自垫本息和为：" + mAutoPay + "，中文金额为："
				+ mCAutoPay);

		mMakeDate = mLOPRTManagerSchema.getMakeDate();
		mMakeDate = BqNameFun.getFDate(mMakeDate);
		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		// 模版自上而下的元素
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
		texttag.add("MakeDate", mMakeDate);
		texttag.add("Post", mZipCode);
		texttag.add("Address", mPostalAddress);
		texttag.add("Name", mAppName);
		texttag.add("LCAppnt.ContNo", mContNo);
		texttag.add("CapitalMoney", mCAutoPay);
		texttag.add("Money", mAutoPay);
		texttag.add("ValueCapitalMoney", mCCashValue);
		texttag.add("ValueMoney", mCashValue);
		texttag.add("CancelDate", mPaytoDate);

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}

		// tXmlExport.outputDocumentToFile("d:\\", "ZdhtyzztzsBzyzfbfP000830");
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	/**
	 * 通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 * @return
	 * @throws Exception
	 */
	private String getComName(String strComCode) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

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

	private String getPayToDate(String tContNo) {
		String tPayToDate = "";
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String strsql = "select (case when (select paytodate from lcpol where polno = mainpolno and contno = '"	+ "?tContNo?" + "') is not null then (select paytodate from lcpol where polno = mainpolno and contno = '"	+ "?tContNo?" + "')  else " + "to_date('?var1?','yyyy-mm-dd')"+ " end) from dual";
		sqlbv.sql(strsql);
		sqlbv.put("tContNo", tContNo);
		sqlbv.put("var1", mLOPRTManagerSchema.getStandbyFlag1());
		tssrs = texesql.execSQL(sqlbv);
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			tPayToDate = tssrs.GetText(1, 1);
		}
		return tPayToDate;
	}

	public static void main(String[] args) {
		logger.debug("test begin:");
		AutoPayPreEndNoticePrintBL tAutoPayPreEndNoticePrintBL = new AutoPayPreEndNoticePrintBL();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		String tPrtSeq = "810000000263500";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		if (!tAutoPayPreEndNoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}
}
