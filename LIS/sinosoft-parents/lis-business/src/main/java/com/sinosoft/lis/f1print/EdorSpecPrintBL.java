package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPContSchema;
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
 * Description: 保全核保特别约定通知书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：wangyan
 * @version：1.0
 * @CreateDate：2005-08-23
 */
public class EdorSpecPrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(EdorSpecPrintBL.class);
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

	private String mContNo; // 保单号
	private String mManageName; // 保单归属地
	private String mAppName; // 投保人姓名
	private String mInsuredName; // 被保人姓名
	private String mBank; // 银行及储蓄所
	private String mGroup; // 业务分部及业务组
	private String mDep; // 营销服务部及营业部
	private String mAgentCode; // 代理人代码
	private String mAgent; // 代理人姓名
	private String mSex; // 申请人性别
	private String mAppntNo; // 投保人客户号
	private String mContent; // 特别约定
	private String mApprove; // 核保员
	private String mSysDate; // 系统时间

	private String mEdorNo; // 批单号
	private String mEdorType; // 批改类型

	public EdorSpecPrintBL() {
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
			tError.moduleName = "BdsxNoticePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
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
		// 传入对象为空？
		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		// 打印流水号（通知书号）为空？
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:流水号不能为空！"));
			return false;
		}
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		// 打印记录为空？
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
			tError.moduleName = "EdorSpecPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_CONT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorSpecPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mContNo = mLOPRTManagerSchema.getOtherNo();
		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorSpecPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传送保单号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mEdorNo = mLOPRTManagerSchema.getStandbyFlag1();
		mEdorType = mLOPRTManagerSchema.getStandbyFlag2();

		String tEdorItem = mLOPRTManagerSchema.getCode();
		if (tEdorItem == null || tEdorItem.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "EdorSpecPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传送单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tEdorItem.trim().equals("BQ80")) {
			CError tError = new CError();
			tError.moduleName = "EdorSpecPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传送单据类型错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean getBaseData() {
		ExeSQL tExeSQL = new ExeSQL();
		// 查询保单信息
		LPContDB tLPContDB = new LPContDB();
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContDB.setContNo(mContNo);
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setEdorType(mEdorType);
		if (!tLPContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "EdorSpecPrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "查询保单批改信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLPContSchema = tLPContDB.getSchema();
		// String mManageCom1 = tLPContSchema.getManageCom();
		// mManageName = getComName(mManageCom1); //机构名称

		mAppName = tLPContSchema.getAppntName(); // 投保人姓名
		mInsuredName = tLPContSchema.getInsuredName();
		mAppntNo = tLPContSchema.getAppntNo();
		if (mAppName == null || mAppName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "EdorSpecPrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "申请人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String[] allArr = BqNameFun.getAllNames(tLPContSchema.getAgentCode());
		if (tLPContSchema.getSaleChnl().equals("3")) {
			LAComSchema tLAComSchema = new LAComSchema();
			LAComDB tLAComDB = new LAComDB();
			tLAComDB.setAgentCom(tLPContSchema.getAgentCom());
			// Modified By QianLy on 2006-09-13 --------
			if (tLAComDB.getInfo()) {
				tLAComSchema = tLAComDB.getSchema(); // 保存银行代码信息
				mBank = tLAComSchema.getName(); // 代理机构
				// CError tError = new CError();
				// tError.moduleName = "EdorAddChargePrintBL";
				// tError.functionName = "getBaseData";
				// tError.errorMessage = "在取得LACom的数据时发生错误!";
				// this.mErrors.addOneError(tError);
				// return false;
			} else {
				mBank = "";
			}
			// ------------

			mGroup = allArr[BqNameFun.DEPART] + allArr[BqNameFun.TEAM]; // 业务分部及业务组.
		}
		// mManageName = allArr[BqNameFun.SALE_SERVICE];
		mManageName = allArr[BqNameFun.CENTER_BRANCH]; // 中支
		mDep = allArr[BqNameFun.SALE_SERVICE] + allArr[BqNameFun.DEPART]; // 营销服务部及营业部
		mAgent = allArr[BqNameFun.AGENT_NAME]; // 业务人姓名
		mAgentCode = tLPContSchema.getAgentCode(); // 业务员编号
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select speccontent from LPSpec where ContNo='"
				+ "?ContNo?"
				+ "' and serialno in (select max(serialno) from lpspec  where ContNo='"
				+ "?ContNo?" + "')");
		sqlbv.put("ContNo", mLOPRTManagerSchema.getOtherNo());
		mContent = tExeSQL.getOneValue(sqlbv);
		mApprove = tLPContSchema.getUWOperator(); // 核保人
		mSysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日"; // 日期
		return true;
	}

	private boolean preparePrintData() {
		XmlExportNew xmlExport = new XmlExportNew();
//		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
		xmlExport.createDocument("保全核保特别约定通知书"); // 初始化xml文档
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(mAppntNo);
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		// 模版自上而下的元素
		texttag.add("LCCont.ContNo", mContNo); // 保单号
		texttag.add("ManageComName", mManageName); // 保单归属地
		texttag.add("LCCont.AppntName", mAppName); // 投保人姓名
		texttag.add("LCCont.InsuredName", mInsuredName); // 被保人姓名
		texttag.add("BankNo", mBank); // 银行及储蓄所
		texttag.add("AgentGroup", mGroup); // 营业分部及业务组
		texttag.add("LABranchGroup.Name", mDep); // 营销服务部及营业部
		texttag.add("LAAgent.Name", mAgent); // 业务员姓名
		texttag.add("LAAgent.AgentCode", mAgentCode); // 业务员编号
		texttag.add("AppntName", mAppName); // 申请人姓名
		texttag.add("LPCSpec.SpecContent", mContent); // 核保特约
		texttag.add("LCContl.UWOperator", mLOPRTManagerSchema.getReqOperator()); // 核保人
		texttag.add("SysDate", mSysDate); // 日期

		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}

		mResult.clear();
		mResult.addElement(xmlExport);
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

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
