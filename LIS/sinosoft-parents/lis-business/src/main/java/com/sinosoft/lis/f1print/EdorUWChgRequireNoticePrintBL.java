package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全核保修改事项索要材料打印类
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
 * @CreateDate：2005-08-23
 */
public class EdorUWChgRequireNoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(EdorUWChgRequireNoticePrintBL.class);
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
	private String mEdorNo; // 批单号
	private String mEdorType; // 批改类型
	private String mContNo; // 合同号
	private String mUWOperator = ""; // 核保师
	private String mMakeDate; // 生成通知书时间
	private String mAgentCode; // 业务员编码
	private String mBank = "";
	private String mRemark = "";
	private String mAppntName = "";
	private String mInsuredName = "";
	private String mSaleChnl = "";
	public static final String VTS_NAME = "EdorUWChange.vts"; // 模板名称

	public EdorUWChgRequireNoticePrintBL() {
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
			CError tError = new CError();
			tError.moduleName = "EdorUWChgRequireNoticePrintBL";
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

		String tNoticeType = mLOPRTManagerSchema.getCode();
		if (tNoticeType == null || tNoticeType.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "EdorUWChgRequireNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tNoticeType.trim().equals("BQ86")) {
			CError tError = new CError();
			tError.moduleName = "EdorUWChgRequireNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误：只能是保全体检通知书！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = mLOPRTManagerSchema.getOtherNo();

		if (mContNo == null || mContNo.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "EdorUWChgRequireNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单号失败！";
			this.mErrors.addOneError(tError);

			return false;
		}
		mEdorNo = mLOPRTManagerSchema.getStandbyFlag1();
		if (mEdorNo == null || mEdorNo.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "EdorUWChgRequireNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取批单号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mEdorType = mLOPRTManagerSchema.getStandbyFlag2();
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "无保单信息！");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorNo(mEdorNo);
		tLPEdorMainDB.setContNo(mContNo);
		return true;
	}

	private boolean getBaseData() {
		LAComDB tLAComDB = new LAComDB();
		tLAComDB.setAgentCom(mLCContSchema.getAgentCom());
		if (tLAComDB.getInfo()) {
			mBank = tLAComDB.getName();
		} else {// Add By QianLy on 2006-09-13
			mBank = "";
		}
		mAppntName = mLCContSchema.getAppntName();
		mInsuredName = mLCContSchema.getInsuredName();
		mRemark = mLOPRTManagerSchema.getRemark();
		mUWOperator = mLOPRTManagerSchema.getReqOperator();
		mAgentCode = mLCContSchema.getAgentCode();
		mMakeDate = mLOPRTManagerSchema.getMakeDate();
		mMakeDate = BqNameFun.getFDate(mMakeDate);
		mSaleChnl = mLCContSchema.getSaleChnl();
		return true;
	}

	private boolean preparePrintData() {
		XmlExportNew xmlExport = new XmlExportNew();
		xmlExport.createDocument("保全核保修改事项索要资料通知书");//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(mLCContSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例

		String[] allArr = BqNameFun.getAllNames(mAgentCode);

		texttag.add("LCCont.ContNo", mContNo);
		texttag.add("ManageComName", allArr[BqNameFun.CENTER_BRANCH]);
		texttag.add("LCCont.AppntName", mAppntName);
		texttag.add("LCCont.InsuredName", mInsuredName);
		texttag.add("LCCUWMaster.ChangePolReason", mRemark);
		texttag.add("AppntName", mAppntName);
		texttag.add("LAAgent.AgentCode", mAgentCode);
		texttag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		texttag.add("LABranchGroup.Name", allArr[BqNameFun.SALE_SERVICE]
				+ allArr[BqNameFun.DEPART]);
		texttag.add("SysDate", mMakeDate);
		texttag.add("LCCont.UWOperator", mUWOperator);
		if (mSaleChnl != null) {
			if (mSaleChnl.trim().equals("3") || mSaleChnl.trim().equals("")) {
				texttag.add("BankNo", mBank);
				texttag.add("AgentGroup", allArr[BqNameFun.DEPART]
						+ allArr[BqNameFun.TEAM]);
			}
		}
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

	public static void main(String[] args) {
		logger.debug("test begin:");
		EdorUWChgRequireNoticePrintBL tEdorUWChgRequireNoticePrintBL = new EdorUWChgRequireNoticePrintBL();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		String tPrtSeq = "810000000564832";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);

		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		if (!tEdorUWChgRequireNoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}
}
