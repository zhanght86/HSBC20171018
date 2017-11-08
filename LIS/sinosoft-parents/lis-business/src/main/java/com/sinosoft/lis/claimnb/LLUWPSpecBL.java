package com.sinosoft.lis.claimnb;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.f1print.PrintTool;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LABranchGroupSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 理赔核保特约通知书打印-----------SpecPrint.vts
 * </p>
 * <p>
 * Description ：打印理赔二核的特约通知书
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author wanzh 2005/11/29
 * @version 1.0
 */

public class LLUWPSpecBL implements PrintService {
	private static Logger logger = Logger.getLogger(LLUWPSpecBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private VData mInputData = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public LLUWPSpecBL() {
	}

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
		if ((!cOperate.equals("PRINT")) && (!cOperate.equals("CONFIRM"))) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		mResult.clear();
		if (!checkData()) {
			return false;
		}
		// 准备所有要打印的数据
		if (!dealData()) {
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		if (!pubSubmit()) {
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
	private boolean getInputData(VData cInputData, String cOperate) {
		// 全局变量
		mInputData = cInputData;
		mLOPRTManagerSchema = (LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0);
		return true;
	}

	/**
	 * 校验得到的数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		if (mLOPRTManagerSchema == null) {
			buildError("mLOPRTManagerSchema", "没有得到打印信息！");
			return false;
		}
		return true;
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	private boolean dealData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.1 根据印刷号查询打印队列中的纪录
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(PrtNo);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.2 开始获取特约的保单号和节点号
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		ExeSQL tExeSQL4 = new ExeSQL();
		SSRS tSSRS4 = new SSRS();
		String tSql4 = " select otherno,standbyflag2 from loprtmanager where prtseq = '"
				+ "?PrtNo?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql4);
		sqlbv.put("PrtNo", PrtNo);
		tSSRS4 = tExeSQL4.execSQL(sqlbv);
		if (tSSRS4.getMaxRow() == 0) {
			return false;
		}
		String tContNo = tSSRS4.GetText(1, 1); // 保单号
		String tClmNo = tSSRS4.GetText(1, 2); // 赔案号

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.4 开始获取特约保单的相关信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql1 = "";
		ExeSQL tExeSQL1 = new ExeSQL();
		SSRS tSSRS1 = new SSRS();
		tSql1 = " select proposalcontno,managecom,appntname,insuredname,uwoperator,agentgroup,agentcode "
				+ " from lccont where contno = '" + "?contno?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql1);
		sqlbv1.put("contno", tContNo);
		tSSRS1 = tExeSQL1.execSQL(sqlbv1);
		if (tSSRS1.getMaxRow() == 0) {
			return false;
		}
		@SuppressWarnings("unused")
		String tProposalContNo = tSSRS1.GetText(1, 1); // 总投保单号
		String tManageComCode = tSSRS1.GetText(1, 2); // 获得机构代码
		String tAppntName = tSSRS1.GetText(1, 3); // 获取投保人姓名
		String tInsuredName = tSSRS1.GetText(1, 4); // 获取出险人姓名
		String tUwOperator = tSSRS1.GetText(1, 5); // 获取核保人
		String tAgentGroup = tSSRS1.GetText(1, 6); // 业务分部及业务组
		String tAgentCode = tSSRS1.GetText(1, 7); // 业务员代码

		ExeSQL tExeSQLw = new ExeSQL();
		SSRS tSsrsw = new SSRS();
		String tSqlw = "select  BranchCode"
				+ " from LAAgent where AgentGroup = '" + "?AgentGroup?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSqlw);
		sqlbv2.put("AgentGroup", tAgentGroup);
		tSsrsw = tExeSQLw.execSQL(sqlbv2);
		if (tSsrsw.getMaxRow() == 0) {
			return false;
		}

		// 管理机构名称
		if (tManageComCode.length() > 6) {
			tManageComCode = tManageComCode.substring(0, 6);
		}
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(tManageComCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tManageCom = tLDComSchema.getName();
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.5 开始获取特约保单的销售服务部以及营业部名称
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql2 = "";
		ExeSQL tExeSQL2 = new ExeSQL();
		SSRS tSSRS2 = new SSRS();
		tSql2 = "select name from labranchgroup  where agentgroup = '"
				+ "?agentgroup?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql2);
		sqlbv3.put("agentgroup", tAgentGroup);
		tSSRS2 = tExeSQL2.execSQL(sqlbv3);
		if (tSSRS2.getMaxRow() == 0) {
			return false;
		}
		@SuppressWarnings("unused")
		String tBranchName = tSSRS2.GetText(1, 1); // 营销服务部及营业部
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.6 开始获取特约保单的业务员姓名
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql3 = "";
		ExeSQL tExeSQL3 = new ExeSQL();
		SSRS tSSRS3 = new SSRS();
		tSql3 = "select name from laagent  where agentcode = '" + "?agentcode?"
				+ "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql3);
		sqlbv4.put("agentcode", tAgentCode);
		tSSRS3 = tExeSQL3.execSQL(sqlbv4);
		if (tSSRS3.getMaxRow() == 0) {
			return false;
		}
		String tAgentName = tSSRS3.GetText(1, 1); // 营业员姓名

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.7 开始获取特约内容
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tSql = " select speccontent from lluwspecmaster where clmno='"
				+ "?clmno?" + "' and contno ='" + "?contno?" + "'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("clmno", tClmNo);
		sqlbv5.put("contno", tContNo);
		tSSRS = tExeSQL.execSQL(sqlbv5);
		String tSpecContent = "";
		int tMaxRow = tSSRS.MaxRow;
		for (int i = 1; i <= tMaxRow; i++) {
			String tContent = tSSRS.GetText(i, 1); // 得到特约内容
			tSpecContent += i + " : " + tContent + "\n";
		}
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
		// xmlexport.createDocument("LLUWPSpec.vts", "");
		xmlexport.createDocument("理赔核保特约通知书打印");
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
		ExeSQL tempExeSQL = new ExeSQL();
		// LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		String tSqlww = "select riskcode from lcpol where contno = '" + "?contno?"
				+ "' and polno = mainpolno";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSqlww);
		sqlbv6.put("contno", tContNo);
		SSRS PolSSRS = new SSRS();
		PolSSRS = tempExeSQL.execSQL(sqlbv6);
		if (PolSSRS != null) {
			mLMRiskAppDB.setRiskCode(PolSSRS.GetText(1, 1));
			if (!mLMRiskAppDB.getInfo()) {
				buildError("outputXML", "查询LMRiskApp表出错！");
				return false;
			}
		} else {
			buildError("outputXML", "查询合同险种信息出错!");
			return false;
		}

		if (mLMRiskAppDB.getRiskProp().equals("Y")) {
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();
			ExeSQL tempExeSQLw = new ExeSQL();
			String tSqlwl = "select agentcom,agentgroup from lccont where contno = '"
					+ "?contno?" + "'";
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tSqlwl);
			sqlbv7.put("contno", tContNo);
			SSRS PolSSRSw = new SSRS();
			PolSSRSw = tempExeSQLw.execSQL(sqlbv7);
			mLAComDB.setAgentCom(PolSSRSw.GetText(1, 1));
			// String AA=PolSSRSw.GetText(1, 2);
			tLABranchGroupDB.setAgentGroup(PolSSRSw.GetText(1, 2));

			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
				buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
				return false;
			}

			// mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
			if (!mLAComDB.getInfo()) {
				mErrors.copyAllErrors(mLAComDB.mErrors);
				buildError("outputXML", "在取得LACom的数据时发生错误");
				return false;
			}
			mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息
			tLABranchGroupSchema = tLABranchGroupDB.getSchema(); // 保存业务分组代码信息

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.8 开始准备特约打印的数据
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			texttag.add("BankNo", mLAComSchema.getName());
			texttag.add("AgentGroup", tLABranchGroupSchema.getName());
		}
//		PrintTool.setBarCode(xmlexport, "UN027");
		PrintTool.setBarCode(xmlexport, PrtNo);
//		texttag.add("BarCode1", "UN027"); // 2006-09-06 周磊 修改通知书类别
//		texttag
//				.add(
//						"BarCodeParam1",
//						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
//		texttag
//				.add(
//						"BarCodeParam2",
//						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		// texttag.add("AgentGroup", tAgentGroup);
		texttag.add("BarCode2", PrtNo);
		texttag.add("LCCont.ContNo", tContNo);
		texttag.add("LCCont.Managecom", tManageCom);
		texttag.add("LCCont.AppntName", tAppntName);
		texttag.add("AppntName", tAppntName);
		texttag.add("LCCont.InsuredName", tInsuredName);
		// texttag.add("LABranchGroup.Name", tBranchName);
		texttag.add("LABranchGroup.Name", getComName(tManageComCode) + " "
				+ getUpComName(tSsrsw.GetText(1, 1)));
		texttag.add("LAAgent.Name", tAgentName);
		texttag.add("LAAgent.AgentCode", tAgentCode);
		texttag.add("LCContl.UWOperator", tUwOperator);
		texttag.add("SpecStipulation", tSpecContent);
		texttag.add("SysDate", SysDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	private boolean prepareOutputData() {
		return true;
	}

	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			this.mErrors.addOneError("数据提交失败,原因"
					+ tPubSubmit.mErrors.getError(0).errorMessage);
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
		cError.moduleName = "LLUWPSpecBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
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

	public String getUpComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return ttLABranchGroupDB.getName();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8105100166116");

		TransferData tTransferData = new TransferData();

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		tVData.add(tLOPRTManagerSchema);

		LLUWPSpecBL tLLUWPSpecBL = new LLUWPSpecBL();
		String tOperate = "PRINT";
		if (tLLUWPSpecBL.submitData(tVData, tOperate) == false) {
			logger.debug("----------理赔核保通知书打印出错---------------");
		}

	}
}
