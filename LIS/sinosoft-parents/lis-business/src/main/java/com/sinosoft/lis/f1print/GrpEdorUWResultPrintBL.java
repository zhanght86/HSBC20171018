package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPGUWMasterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPGUWMasterSchema;
import com.sinosoft.lis.vschema.LPGUWMasterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/*******************************************************************************
 * <p>
 * Title:团单保全核保通知书打印
 * </p>
 * <p>
 * Description: 团体保全-人工核保-核保通知书打印-打印数据处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006 Sinosoft, Co.,Ltd. All Rights Reserved
 * </p>
 * <p>
 * Company: 中科软科技股份有限公司
 * </p>
 * <p>
 * WebSite: http://www.sinosoft.com.cn
 * </p>
 * 
 * @todo : 处理并生成打印数据
 * @author : liuxiaosong (liuxs@sinosoft.com.cn)
 * @version : 1.00
 * @date : 2006-11-03
 *       <p>
 *       更新信息： 更新人 更新日期 更改内容/原因
 ******************************************************************************/

public class GrpEdorUWResultPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(GrpEdorUWResultPrintBL.class);
	// ===============全局变量======================================================
	public CErrors mErrors = new CErrors(); // 错误处理类
	private VData mResult = new VData(); // 输出处理结果，供高层模块使用
	public TextTag texttag = new TextTag(); // TextTag的实例
	private XmlExportNew xmlExport = new XmlExportNew(); // XmlExport的实例
	private GlobalInput mGlobalInput = new GlobalInput();
	// ---------------SQL条件用变量-------------------------------------------------
	boolean PolFlag = false; // 判断有没有个人核保结论
	private String mAgentCode = ""; // 代理人编码
	// ----------------业务相关变量--------------------------------------------------
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LPGUWMasterSchema mLPGUWMasterSchema = new LPGUWMasterSchema();

	// ----------------------------------------------------------------------------

	/***************************************************************************
	 * 本类默认构造函数
	 **************************************************************************/
	public GrpEdorUWResultPrintBL() {
	}

	/***************************************************************************
	 * 高层类调用本类入口方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 **************************************************************************/
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
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

	/***************************************************************************
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 **************************************************************************/
	private boolean getInputData(VData cInputData) {
		// 获取打印队列记录
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "获取打印队列时出错：打印队列为空！");
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			buildError("getInputData", "没有得到足够的信息:印刷号不能为空！");
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * 返回处理信息给高层
	 * 
	 * @return VData
	 **************************************************************************/
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

	/***************************************************************************
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 **************************************************************************/
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpEdroUWResultPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * 打印数据处理
	 * 
	 * @return boolean
	 **************************************************************************/
	private boolean getPrintData() {
		// 根据打印流水号查询打印队列中的纪录-----------------------------------------
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq()); // 打印流水号
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorUWResultPrintBL";
			tError.functionName = "getPrintData";
			tError.errorMessage = "在取得打印队列中数据时发生错误!";
			mErrors.addOneError(tError);
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		// ----------------------------------------------------------------------

		// 取得团单信息------------------------------------------------------------
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLOPRTManagerSchema.getOtherNo());
		if (!tLCGrpContDB.getInfo()) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorUWResultPrintBL";
			tError.functionName = "getPrintData";
			tError.errorMessage = "在取得LCGrpCont的数据时发生错误!";
			mErrors.addOneError(tError);

			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();
		// ----------------------------------------------------------------------

		// 获得代理人信息----------------------------------------------------------
		mAgentCode = mLCGrpContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorUWResultPrintBL";
			tError.functionName = "getPrintData";
			tError.errorMessage = "在取得LAAgent的数据时发生错误";
			mErrors.addOneError(tError);
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema();
		// 获得代理人组别
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup()); // 代理人展业机构代码
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorUWResultPrintBL";
			tError.functionName = "getPrintData";
			tError.errorMessage = "在取得LAAgentBranch的数据时发生错误";
			mErrors.addOneError(tError);
			return false;
		}
		// ----------------------------------------------------------------------

		// 核保管理-》获得：险种编码&核保结论&核保意见=================================
		LPGUWMasterDB tLPGUWMasterDB = new LPGUWMasterDB();
		// 根据批单号、批改类型、团单号 取得，该号在loprtmanager中以standbyflag1字段存储
		tLPGUWMasterDB.setEdorNo(mLOPRTManagerSchema.getStandbyFlag1());
		tLPGUWMasterDB.setEdorType(mLOPRTManagerSchema.getStandbyFlag2());
		tLPGUWMasterDB.setGrpContNo(mLOPRTManagerSchema.getOtherNo());
		// tLPGUWMasterDB.setEdorNo("6020060404000025");
		LPGUWMasterSet tLPGUWMasterSet = new LPGUWMasterSet();
		tLPGUWMasterSet = tLPGUWMasterDB.query();

		logger.debug("开始组装数据");

		// 创建显示核保结论数据容器，循环处理完毕后，将结果加入到xmlexport中-------------
		// f1print只支持二维格式的打印，因此将界面需要打印的数据二维化，固定说明语句拆分为列
		// 打印模板格式为：“对于 $=/PolNo$ 险种所涉及的本次保全变更，核保结论为：$=/Result$”
		// “具体意见为：”
		// “$=/idea$”
		ListTable tListContentTable = new ListTable(); // 使用该容器对该团单下所有险种进行循环处理
		tListContentTable.setName("PolList");
		String strResult[] = new String[1]; // 核保结果，包括说明文字以及相关数据
		String strTitle[] = new String[1]; // 标题
		strTitle[0] = "result";
		// strTitle[1] = "xianzhong";
		// strTitle[2] = "jielun0";
		// strTitle[3] = "jielun";
		// strTitle[4] = "yijian0";
		// strTitle[5] = "yijian";
		// ----------------------------------------------------------------------

		int temp = tLPGUWMasterSet.size();
		logger.debug("生成容器");
		for (int i = 1; i <= temp; i++) {
			strResult = new String[1];
			mLPGUWMasterSchema = tLPGUWMasterSet.get(i).getSchema();
			strResult[0] = "对于" + getRiskName(mLPGUWMasterSchema.getGrpPolNo())
					+ "所涉及的本次保全变更，核保结论为："
					+ getEdorResultName(mLPGUWMasterSchema.getPassFlag());
			logger.debug(strResult[0]);
			String uwIdea = mLPGUWMasterSchema.getUWIdea();
			// 如果具体意见为空则不出现具体意见字样
			if (!(uwIdea == null || uwIdea.equals(""))) {
				strResult[0] = strResult[0] + "\n具体意见为：" + uwIdea;

			}
			tListContentTable.add(strResult); // 将此次循环所得一条险种核保结论加入
		}
		// ======================================================================
		logger.debug("组织其他信息");
		// 创建打印模板，组织打印数据================================================

		xmlExport.createDocument("团单保全核保结论通知书"); // 创建打印模板
		PrintTool.setBarCode(xmlExport, "UN071");
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());
		String uLanguage = PrintTool.getCustomerLanguage(tLCGrpContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		texttag.add("LCCont.ProposalContNo", mLCGrpContSchema.getGrpContNo()); // 团单号
		texttag.add("LCCont.AppntName", mLCGrpContSchema.getGrpName()); // 投保单位名称
		texttag.add("AgentGroup", tLABranchGroupDB.getName()); // 业务部门
		texttag.add("LAAgent.Name", mLAAgentSchema.getName()); // 业务员姓名
		texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编号
		texttag.add("ManageComName",
				getComName(mLCGrpContSchema.getManageCom())); // 管理机构
		texttag.add("LCCont.UWOperator", mLCGrpContSchema.getUWOperator()); // 核保员
		texttag.add("SysDate", PubFun.getCurrentDate()); // 系统时间

		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		if (tListContentTable.size() > 0) {
			xmlExport.addListTable(tListContentTable, strTitle);
		}
		xmlExport.addDisplayControl("displayTitel");
		xmlExport.addDisplayControl("displayResult");

		mResult.addElement(xmlExport);
		return true;
	} 

	/**
	 * 辅助函数：获得管理机构名称
	 * 
	 * @param strComCode
	 *            String
	 * @return String
	 */
	private String getComName(String strComCode) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return null;

		}
		return tLDCodeDB.getCodeName();
	}

	/**
	 * 辅助函数，获得险种名称
	 * 
	 * @param strRiskCode
	 *            String
	 * @return String
	 */
	private String getRiskName(String grpPolNo) {
		ExeSQL tExeSQL = new ExeSQL();
		String tGetRiskNameSQL = "select riskname from lmrisk where riskcode in "
				+ "(select riskcode from lpgrppol where grppolno = '"
				+ grpPolNo + "')";
		String riskName = tExeSQL.getOneValue(tGetRiskNameSQL);
		return riskName;
	}

	/**
	 * 辅助函数，根据保全状态 edorstate,获得核保结论名
	 * 
	 * @param edorResultCode
	 *            String
	 * @return String
	 */
	private String getEdorResultName(String state) {
		// select * from ldcode where codetype ='gedorgrppoluw'/*团单保全人工核保结论*/
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL = "select codename from ldcode where codetype ='gedorgrppoluw' and code='"
				+ state + "'";
		String tResult = tExeSQL.getOneValue(tSQL);
		return tResult;
	}

}
