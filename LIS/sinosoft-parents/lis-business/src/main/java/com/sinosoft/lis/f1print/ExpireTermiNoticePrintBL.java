package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 满期终止通知书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：liurx
 * @version：1.0
 * @CreateDate：2005-08-03
 */
public class ExpireTermiNoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(ExpireTermiNoticePrintBL.class);
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

	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCContStateSchema mLCContStateSchema = new LCContStateSchema();

	private String mPolNo; // 保单险种号
	private String mContNo; // 合同号
	private String mEndDate; // 满期终止时间
	private String mMakeDate; // 系统时间
	private String mZipCode; // 邮政编码
	private String mAddress; // 地址
	private String mAppntName; // 投保人姓名

	private String mAgentCode; // 业务员编码
	public static final String VTS_NAME = "MqzztzsP000270.vts"; // 模板名称

	public ExpireTermiNoticePrintBL() {
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
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
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
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);

			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_INDPOL)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tNoticeType = mLOPRTManagerSchema.getCode();
		if (tNoticeType == null || tNoticeType.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tNoticeType.trim()
				.equals(PrintManagerBL.CODE_PEdorExpireTerminate)) {
			CError tError = new CError();
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误：只能是满期终止通知书！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mPolNo = mLOPRTManagerSchema.getOtherNo();

		if (mPolNo == null || mPolNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单险种号失败！";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo);
		if (!tLCPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "无保单数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCPolSchema = tLCPolDB.getSchema();
		mEndDate = mLCPolSchema.getEndDate();
		if (mEndDate == null || mEndDate.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "EndDate为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mEndDate = getFDate(mEndDate);
		mContNo = mLCPolSchema.getContNo();
		if (mContNo == null || mContNo.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "InvaliPreNoticePrintBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "合同号为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean getBaseData() {
		// 查询投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mContNo);
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "查询投保人信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCAppntSchema = tLCAppntDB.getSchema();
		String tAddressNo = tLCAppntSchema.getAddressNo(); // 投保人地址号
		String tCustomerNo = tLCAppntSchema.getAppntNo(); // 投保人客户号
		mAppntName = tLCAppntSchema.getAppntName(); // 投保人姓名
		if (mAppntName == null || mAppntName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "投保人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tSex = tLCAppntSchema.getAppntSex(); // 投保人性别
		if (tSex.trim().equals("0")) {
			mAppntName = mAppntName.trim() + "先生";
		} else if (tSex.trim().equals("1")) {
			mAppntName = mAppntName.trim() + "女士";
		} else {
			mAppntName = mAppntName.trim() + "女士/先生";
		}

		// 查询投保人地址信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		tLCAddressDB.setCustomerNo(tCustomerNo);
		tLCAddressDB.setAddressNo(tAddressNo);
		if (!tLCAddressDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "ExpireTermiNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "查询客户地址失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCAddressSchema = tLCAddressDB.getSchema();
		mZipCode = tLCAddressSchema.getZipCode(); // 客户邮编
		CodeNameQuery tCodeNameQuery = new CodeNameQuery();
		mAddress = tCodeNameQuery.getDistrict(tLCAddressSchema.getCounty())
				+ tLCAddressSchema.getPostalAddress(); // 客户地址

		mAgentCode = mLCPolSchema.getAgentCode();

		mMakeDate = mLOPRTManagerSchema.getMakeDate();
		mMakeDate = BqNameFun.getFDate(mMakeDate);
		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例

		String[] allArr = BqNameFun.getAllNames(mAgentCode);
		texttag.add("Post", mZipCode);
		texttag.add("Address", mAddress);
		texttag.add("AppntName", mAppntName);
		texttag.add("LCAppnt.ContNo", mContNo);
		texttag.add("StopDate", mEndDate);
		texttag.add("MakeDate", mMakeDate);
		texttag.add("AgentCode", mAgentCode);
		texttag.add("District", allArr[BqNameFun.DISTRICT]);
		texttag.add("Depart", allArr[BqNameFun.DEPART]);
		texttag.add("Team", allArr[BqNameFun.TEAM]);
		texttag.add("AgentName", allArr[BqNameFun.AGENT_NAME]);
		texttag.add("ManageComName", allArr[BqNameFun.COM]);
		texttag.add("SaleService", allArr[BqNameFun.SALE_SERVICE]);
		texttag.add("CompanyAddress", allArr[BqNameFun.ADDRESS]);
		texttag.add("CompanyPost", allArr[BqNameFun.ZIPCODE]);

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}

		// tXmlExport.outputDocumentToFile("d:\\", "MqzztzsP000270"); //测试用
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;

	}

	/**
	 * 得到通过机构代码得到机构名称
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
			tCalendar.setTime(fDate.getDate(mDate));
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
}
