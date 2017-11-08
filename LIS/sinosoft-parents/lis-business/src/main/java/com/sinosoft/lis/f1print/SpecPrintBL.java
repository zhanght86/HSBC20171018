package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * LABranchGroup.Name
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft<S/p>
 * 
 * @author ccvip
 * @version 1.0
 */
public class SpecPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(SpecPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String mAgentCode = "";

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LCSpecSchema mLCSpecSchema = new LCSpecSchema();

	public SpecPrintBL() {
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
		cError.moduleName = "SpecPrintBL";
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
		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		logger.debug("PrtNo=" + PrtNo);
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(PrtNo);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		ExeSQL tempExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();

		String ttSql = "select speccontent from LCSpec where ContNo='?ContNo?'and prtseq ='"+ "?prtseq?" + "'";
		// "' and serialno in (select max(serialno) from lcspec where ContNo='"
		// +
		// mLOPRTManagerSchema.getOtherNo() + "'and prtseq ='" +
		// mLOPRTManagerSchema.getOldPrtSeq() + "')";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(ttSql);
		sqlbv1.put("ContNo", mLOPRTManagerSchema.getOtherNo());
		sqlbv1.put("prtseq", mLOPRTManagerSchema.getOldPrtSeq());
		tSSRS = tempExeSQL.execSQL(sqlbv1);
		if (tSSRS.getMaxRow() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWSendALLPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "在取得特别约定信息时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		String currContent = "";
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			if (i == 1) {
				currContent = tSSRS.GetText(i, 1);
			} else {
				currContent = currContent + "\n" + tSSRS.GetText(i, 1);
			}
		}
		// /**
		// * 如果上次已经发过一次特约通知书，那么这次的特约通知书上必须把上次发的特约通知书上的
		// * 内容给去掉
		// *
		// */
		// String preSql = "select speccontent from LCSpec where ContNo='" +
		// mLOPRTManagerSchema.getOtherNo() + "'and prtseq <>'" +
		// mLOPRTManagerSchema.getOldPrtSeq() +
		// "' and serialno in (select max(serialno) from lcspec where ContNo='"
		// +
		// mLOPRTManagerSchema.getOtherNo() + "'and prtseq <>'" +
		// mLOPRTManagerSchema.getOldPrtSeq() + "')";
		// SSRS preSSRS = new SSRS();
		// String preContent = "";
		// preSSRS = tempExeSQL.execSQL(preSql);
		// if (preSSRS.getMaxRow()>0)
		// {
		// preContent = preSSRS.GetText(1,1);
		// }
		//
		// int subLength = preContent.trim().length();
		// String content = currContent.substring(subLength);
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		String ContNo = mLOPRTManagerSchema.getOtherNo();
		logger.debug("ContNo=" + ContNo);
		int m, i;
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		SSRS ttSSRS = new SSRS();

		ttSql = "select codename from ldcode where Codetype='station'"
				+ " and code ='" + "?code?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(ttSql);
		sqlbv2.put("code", mLCContSchema.getManageCom());
		ttSSRS = tempExeSQL.execSQL(sqlbv2);
		if (ttSSRS.getMaxRow() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWSendALLPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "在取得管理机构信息时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String Station = ttSSRS.GetText(1, 1);

		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLCContSchema.getContNo());
		if (tLCAppntDB.getInfo() == false) {
			mErrors.copyAllErrors(tLCAppntDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mAgentCode = mLCContSchema.getAgentCode();
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
		String AgentGroup = mLAAgentSchema.getAgentGroup();
		logger.debug("AgentGroup=" + AgentGroup);
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
			return false;
		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("SpecPrint.vts", "");
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		LCPolSchema mLCPolSchema = new LCPolSchema();
		LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
		String tSql = "select riskcode from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("contno", mLCContSchema.getContNo());
		SSRS PolSSRS = new SSRS();
		PolSSRS = tempExeSQL.execSQL(sqlbv3);
		if (PolSSRS != null) {
			mLMRiskAppDB.setRiskCode(PolSSRS.GetText(1, 1));
			if (!mLMRiskAppDB.getInfo()) {
				buildError("outputXML", "查询LMRiskApp表出错！");
				return false;
			}
		} else {
			buildError("outputXML", "查询合同险种信息出错！");
			return false;
		}

		if (mLMRiskAppDB.getRiskProp().equals("Y")) {
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
			if (!mLAComDB.getInfo()) {
				mErrors.copyAllErrors(mLAComDB.mErrors);
				buildError("outputXML", "在取得LACom的数据时发生错误");
				return false;
			}
			mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息

			texttag.add("BankNo", mLAComSchema.getName()); // 代理机构
			texttag.add("AgentGroup", tLABranchGroupDB.getName()); // 业务分布及业务组.
		}

		/**
		 * ==========================================================================
		 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
		 * LABranchGroup.Name：取8位编码的营业销售部
		 * ==========================================================================
		 */
		// 中支机构名称
		String tManageComCode = mLCContSchema.getManageCom();
		String ggManageCom = "";
		if (mLCContSchema.getManageCom().length() > 6)
			tManageComCode = mLCContSchema.getManageCom().substring(0, 6);
		String tManageComName = "";
		try {
			tManageComName = getComName(tManageComCode);
			ggManageCom = getComName(mLCContSchema.getManageCom());
		} catch (Exception ex) {
		}
		;
		texttag.add("BarCode1", "UN015");
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam2",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("LCCont.ProposalContNo", mLCContSchema.getContNo());
		texttag.add("LCCont.Managecom", tManageComName);
		texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
		texttag.add("AppntName", mLCContSchema.getAppntName());
		texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
		texttag.add("LABranchGroup.Name", ggManageCom + " "
				+ getUpComName(mLAAgentSchema.getBranchCode()));
		texttag.add("LAAgent.Name", mLAAgentSchema.getName());
		texttag.add("LAAgent.AgentCode", mLCContSchema.getAgentCode());
		texttag.add("LCContl.UWOperator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("SpecStipulation", currContent);
		String Operator = mLCContSchema.getOperator();
		logger.debug("Operator=" + Operator);
		texttag.add("SysDate", SysDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// /xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		logger.debug("xmlexport=" + xmlexport);
		return true;
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

	private String getComName(String strComCode) throws Exception {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			throw new Exception("在取得LDCom的数据时发生错误");
		}
		return tLDComDB.getName();
	}
}
