package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:
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
 * @author Lizhuo
 * @version 1.0
 */
public class EdorUWPartBL implements PrintService {
private static Logger logger = Logger.getLogger(EdorUWPartBL.class);

	public EdorUWPartBL() {
	}

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 输入的查询sql语句
	private String mAgentCode = "";
	private String mBank; // 银行及储蓄所
	
	private String mContNo;
	private String mSaleChnl;
	private String mAgentCom;
	private String mAppntName;
	private String mInsuredName;
	private String mManageCom;
	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LPContSchema mLPContSchema = new LPContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			buildError("getInputData", "没有得到足够的信息:印刷号不能为空！");
			return false;
		}
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {
		// 根据印刷号查询打印队列中的纪录
		String prtSeq = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(prtSeq);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		logger.debug(mLOPRTManagerSchema.getCode());
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		tLPContDB.setEdorNo(mLOPRTManagerSchema.getStandbyFlag1());
		tLPContDB.setEdorType(mLOPRTManagerSchema.getStandbyFlag2());
		if (!tLPContDB.getInfo()) {
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			if(!tLCContDB.getInfo()){
				CError.buildErr(this, "查询合同信息失败！");
				return false;
			}
			mAgentCode = tLCContDB.getAgentCode();
			mContNo = tLCContDB.getContNo();
			mSaleChnl = tLCContDB.getSaleChnl();
			mAgentCom = tLCContDB.getAgentCom();
			mAppntName = tLCContDB.getAppntName();
			mInsuredName = tLCContDB.getInsuredNo();
			mManageCom  = tLCContDB.getManageCom();
		}else{
			mLPContSchema = tLPContDB.getSchema();
			mAgentCode = mLPContSchema.getAgentCode();
			mContNo = mLPContSchema.getContNo();
			mSaleChnl = mLPContSchema.getSaleChnl();
			mAgentCom = mLPContSchema.getAgentCom();
			mAppntName = mLPContSchema.getAppntName();
			mInsuredName = mLPContSchema.getInsuredNo();
			mManageCom  = mLPContSchema.getManageCom();
		}

		// LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		// tLCCUWMasterDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		// String ContNO = mLOPRTManagerSchema.getOtherNo();
		// logger.debug("ContNO=" + ContNO);
		//
		// if (!tLCCUWMasterDB.getInfo()) {
		// mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
		// buildError("outputXML", "在取得LCCUWMasterDB的数据时发生错误");
		// return false;
		// }
		// mLCCUWMasterSchema = tLCCUWMasterDB.getSchema();

		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mContNo);
		if (tLCAppntDB.getInfo() == false) {
			mErrors.copyAllErrors(tLCAppntDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
			return false;
		}

		// String[] RiskTitle = new String[5];
		//
		// RiskTitle[0] = "Type";
		// RiskTitle[1] = "RiskCode";
		// RiskTitle[2] = "RiskName";
		// RiskTitle[3] = "Prem";
		// RiskTitle[4] = "Amnt";

		// ListTable tRiskListTable = new ListTable();
		// String strRisk[] = null;
		// tRiskListTable.setName("RISK");
		// LCPolDB tLCPolDB = new LCPolDB();
		// LCPolSet tLCPolSet = new LCPolSet();
		// tLCPolDB.setContNo(mLPContSchema.getContNo());
		// tLCPolSet = tLCPolDB.query();
		// double SumPrem = 0;
		// for (int j = 1; j <= tLCPolSet.size(); j++) {
		// strRisk = new String[5];
		// mLCPolSchema = tLCPolSet.get(j).getSchema();
		// if (mLCPolSchema.getUWFlag().equals("a")) {
		// continue;
		// }
		// if (mLCPolSchema.getPolNo().equals(mLCPolSchema.getMainPolNo())) {
		// strRisk[0] = "主 险:";
		// } else {
		// strRisk[0] = "附加险:";
		// }
		//
		// strRisk[1] = mLCPolSchema.getRiskCode();
		// strRisk[2] = getRiskName(mLCPolSchema.getRiskCode());
		// strRisk[3] = "￥" + Double.toString(mLCPolSchema.getAmnt());
		// strRisk[4] = "应缴保费￥" + new Double(mLCPolSchema.getPrem()).toString();
		// SumPrem = SumPrem + mLCPolSchema.getPrem();
		// tRiskListTable.add(strRisk);
		//
		// }

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		String printName = "";
		if (mLOPRTManagerSchema.getCode().equals("BQ84")) {
			printName = "保全核保拒绝保全通知书";
		}
		if (mLOPRTManagerSchema.getCode().equals("BQ85")) {
			printName = "保全核保延期通知书";
		}
		if (mLOPRTManagerSchema.getCode().equals("BQ87")) {
			printName = "保全核保拒保延期附加险通知书";
		}
		if (mLOPRTManagerSchema.getCode().equals("BQ89")) {
			printName = "保全核保不同意通知书";
		}
		XmlExportNew xmlExport = new XmlExportNew();// 新建一个XmlExport的实例
		xmlExport.createDocument(printName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, prtSeq);//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLPContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		if (mSaleChnl.equals("3")) {
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			mLAComDB.setAgentCom(mAgentCom);
			// Modified By QianLy on 2006-09-13 --------
			if (mLAComDB.getInfo()) {
				mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息
				mBank = mLAComSchema.getName(); // 代理机构
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
			texttag.add("LCCont.BankCode", mBank); // 代理机构
			texttag.add("BankNo", mBank); // 代理机构

			texttag.add("LCCont.AgentType", tLABranchGroupDB.getName()); // 业务分布及业务组.
			texttag.add("AgentGroup", tLABranchGroupDB.getName()); // 业务分布及业务组.

		}

		texttag.add("LCCont.ContNo", mContNo);
		texttag.add("LCPol.MainPolNo", mContNo);

		texttag.add("LCCont.AppntName", mAppntName);
		texttag.add("AppntName", mAppntName);
		texttag.add("LCCont.InsuredName", mInsuredName);
		texttag.add("LCCont.Managecom",
				getComName(mManageCom));
		texttag.add("ManageComName", getComName(mManageCom
				.trim().substring(0, 6)));
		// texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
		String[] allArr = BqNameFun.getAllNames(mAgentCode);
		// logger.debug("Service" + allArr[BqNameFun.SALE_SERVICE]);
		// logger.debug("Service" + allArr[BqNameFun.DEPART]);
		texttag.add("LABranchGroup.Name", allArr[BqNameFun.SALE_SERVICE]
				+ allArr[BqNameFun.DEPART]);
		texttag.add("LAAgent.Name", mLAAgentSchema.getName());
		texttag.add("LCCont.AgentCode", mLAAgentSchema.getAgentCode());
		texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
		// logger.debug("UWOperator:" +
		// CodeNameQuery.getOperator(mLOPRTManagerSchema.getReqOperator()));
		texttag.add("LCCont.UWOperator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("LCContl.UWOperator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("DealContent", mLOPRTManagerSchema.getRemark());
		// texttag.add("SumPrem", SumPrem);
		texttag.add("Res", mLOPRTManagerSchema.getRemark());

		texttag.add("SysDate", SysDate);
		texttag.add("LCCUWMaster.ChangePolReason", mLOPRTManagerSchema
				.getRemark());
		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlExport);
		// logger.debug("xmlexport=" + xmlexport);
		return true;
	}

	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得Ldcom的数据时发生错误");
		}
		return tLDComDB.getName();
	}

	public static void main(String[] args) {
		EdorUWPartBL edoruwpartbl = new EdorUWPartBL();
	}
}
