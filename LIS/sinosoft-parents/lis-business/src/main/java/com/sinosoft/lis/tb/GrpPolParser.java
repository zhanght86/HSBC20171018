/*
 * @(#)GrpPolParser.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredRelatedSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCPremToAccSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 磁盘投保的解析类，从一个XML数据流中解析出团体下被保险人信息， 险种保单，保费项信息，责任项信息，连身被保险人信息，受益人信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Kevin
 * @version 6.0
 * @date 2003-07-09
 */
public class GrpPolParser {
private static Logger logger = Logger.getLogger(GrpPolParser.class);

	// private static String PATH_GROUPCONTNO = "GRPCONTNO";

	// private static String PATH_INSURED = "SUBINSUREDTABLE/ROW";
	private String PATH_ROW = "ROW";
	// 被保险人
	private static String PATH_INSURED = "ROW";
	private static String PATH_Insured_ContId = "ContNo";
	// private static String PATH_CustomerNo = "CustomerNo";
	private static String PATH_CustomerState = "CustomerState";
	private static String PATH_RelationToMainInsured = "RelationToMainInsured";
	private static String PATH_InsuredName = "InsuredName";
	private static String PATH_InsuredSex = "InsuredSex";
	private static String PATH_InsuredBirthday = "InsuredBirthday";
	private static String PATH_InsuredIDType = "InsuredIDType";
	private static String PATH_InsuredIDNo = "InsuredIDNo";
	private static String PATH_NativePlace = "NativePlace";
	private static String PATH_RgtAddress = "RgtAddress";
	private static String PATH_Marriage = "Marriage";
	private static String PATH_Nationality = "Nationality";
	private static String PATH_WorkType = "WorkType";
	private static String PATH_PluralityType = "PluralityType";
	private static String PATH_OccupationType = "OccupationType";
	private static String PATH_OccupationCode = "OccupationCode";
	private static String PATH_PostalAddress = "PostalAddress";
	private static String PATH_InsuredPhone = "InsuredPhone";
	private static String PATH_Mobile = "Mobile";
	private static String PATH_BankCode = "BankCode";
	private static String PATH_BankAccNo = "BankAccNo";
	private static String PATH_AccName = "AccName";
	private static String PATH_JoinCompanyDate = "JoinCompanyDate";
	private static String PATH_Salary = "Salary";
	private static String PATH_SocialInsuNo = "SocialInsuNo";
	private static String PATH_ContPlan = "ContPlan";
	private static String PATH_PolTypeFlag = "PolTypeFlag";
	private static String PATH_InsuredPeoples = "InsuredPeoples";

	// 下面是一些常量定义
	private static String PATH_ID = "ID";

	// private static String PATH_GrpContNo = "GrpContNo";
	private static String PATH_GrpPolNo = "GrpPolNo";
	private static String PATH_POL_ContNo = "PolContNo";
	private static String PATH_RiskCode = "RiskCode";
	private static String PATH_InSuredNo = "InsuredNo";
	private static String PATH_POL_Other_Insured = "OtherInsured";
	private static String PATH_MainRiskCode = "MainRiskCode";
	private static String PATH_HealthCheckFlag = "HealthCheckFlag";
	private static String PATH_OutPayFlag = "OutPayFlag";
	private static String PATH_PayLocation = "PayLocation";

	private static String PATH_LiveGetMode = "LiveGetMode";
	private static String PATH_GetDutyKind = "GetDutyKind";
	private static String PATH_GetIntv = "GetIntv";
	private static String PATH_BonusGetMode = "BonusGetMode";

	private static String PATH_PayRuleCode = "PayRule";
	private static String PATH_AscriptionCode = "AscriptionRule";

	private static String PATH_Mult = "Mult";
	private static String PATH_Prem = "Prem";
	private static String PATH_Amnt = "Amnt";
	private static String PATH_RnewFlag = "RnewFlag";
	private static String PATH_SpecifyValiDate = "SpecifyValiDate";
	private static String PATH_FloatRate = "FloatRate";
	private static String PATH_Remark = "Remark";
	private static String PATH_PremToAmnt = "PremToAmnt";
	private static String PATH_CalRule = "CalRule";
	private static String PATH_GetLimit = "GetLimit";
	private static String PATH_GetRate = "GetRate";
	private static String PATH_StandbyFlag1 = "StandbyFlag1";
	private static String PATH_StandbyFlag2 = "StandbyFlag2";
	private static String PATH_StandbyFlag3 = "StandbyFlag3";
	private static String PATH_SavePolType = "SavePolType";
	private static String PATH_CValiDate = "CValiDate";
	private static String PATH_RiskAddPrem = "RiskAddPrem";
	// 以下不使用
	private static String PATH_PREM = "SUBPREMTABLE/ROW";
	private static String PATH_DutyCode = "DutyCode";
	private static String PATH_PayPlanCode = "PayPlanCode";
	private static String PATH_PayPlanRate = "PayPlanRate";
	private static String PATH_PayPlanPrem = "PayPlanPrem";
	private static String PATH_InsuAccNo = "InsuAccNo";
	private static String PATH_InsuAccRate = "InsuAccRate";
	private static String PATH_ManageFeeRate = "ManageFeeRate";

	private static String PATH_DUTY = "SUBDUTYTABLE/ROW";
	// private static String PATH_DutyCode1 = "DutyCode1";
	private static String PATH_PayIntv = "PayIntv";
	private static String PATH_InsuYear = "InsuYear";
	private static String PATH_InsuYearFlag = "InsuYearFlag";
	private static String PATH_PayEndYear = "PayEndYear";
	private static String PATH_PayEndYearFlag = "PayEndYearFlag";
	private static String PATH_GetYear = "GetYear";
	private static String PATH_GetYearFlag = "GetYearFlag";
	private static String PATH_GetStartType = "GetStartType";
	// 以上不使用
	private static String PATH_BNF = "ROW";
	private static String PATH_BNF_ContId = "ContIdBNF";
	private static String PATH_INSUREDID = "InsuredId";
	private static String PATH_BNF_RiskCode = "RiskCode";
	private static String PATH_BnfType = "BnfType";
	private static String PATH_BnfName = "BnfName";
	private static String PATH_BnfSex = "BnfSex";
	private static String PATH_BnfIDType = "BnfIDType";
	private static String PATH_BnfIDNo = "BnfIDNo";
	private static String PATH_BnfBirthday = "BnfBirthday";
	private static String PATH_RelationToInsured = "RelationToInsured";
	private static String PATH_BnfLot = "BnfLot";
	private static String PATH_BnfGrade = "BnfGrade";

	private static String PATH_INS_RELA = "ROW";
	private static String PATH_INS_RELA_CONTID = "ContIdRELA";
	private static String PATH_INS_RELA_INSUREDID = "InsuredId";
	private static String PATH_INS_RELA_RISKCODE = "RiskCode";
	private static String PATH_INS_RELA_ID = "RelaId";

	public CErrors mErrors = new CErrors();

	public GrpPolParser() {
	}

	/**
	 * 解析被保险人结点
	 * 
	 * @param node
	 *            Node
	 * @return VData
	 */
	public VData parseOneInsuredNode(Node node) {
		VData tReturn = new VData();
		// 得到被保人信息
		LCInsuredSet tLCInsuredSet = getLCInsuredSet(node);
		if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0) {
			return null;
		}

		tReturn.add(tLCInsuredSet);
		return tReturn;
	}

	public VData parseLCPolNode(Node node) {
		NodeList nodeList = null;

		try {
			nodeList = XPathAPI.selectNodeList(node, PATH_ROW);
		} catch (Exception ex) {
			ex.printStackTrace();
			nodeList = null;
			return null;
		}
		VData reData = new VData();
		int nLength = nodeList == null ? 0 : nodeList.getLength();
		// 解析<LCPOLTABLE>下每个<ROW>
		for (int nIndex = 0; nIndex < nLength; nIndex++) {
			VData tData = new VData();
			if (parseOneNode(nodeList.item(nIndex), tData)) {
				reData.add(tData);
			} else {
				return null;
			}
		}
		return reData;
	}

	/**
	 * 解析一个DOM树的节点，在这个节点中，包含了一个团体下个人保单的所有信息。
	 * 
	 * @param node
	 *            Node
	 * @param vReturn
	 *            VData
	 * @return boolean
	 */
	public boolean parseOneNode(Node node, VData vReturn) {
		try {
			if (vReturn == null) {
				buildError("parseOneNode", "存放返回数据的VData是非法的");
				return false;
			}

			// 一些特殊的信息
			TransferData tTransferData = getTransferData(node);

			// 个人险种信息
			LCPolSchema tLCPolSchema = getLCPolSchema(node);
			String id = (String) tTransferData.getValueByName("ID");

			if ("".equals(StrTool.cTrim(tLCPolSchema.getInsuredNo()))) {
				CError.buildErr(this, "保单[" + id + "]没有填写被保险人ID");
				return false;
			}
			if ("".equals(StrTool.cTrim(tLCPolSchema.getContNo()))) {
				CError.buildErr(this, "保单[" + id + "]没有填写合同ID");
				return false;
			}
			if ("".equals(StrTool.cTrim(tLCPolSchema.getRiskCode()))) {
				CError.buildErr(this, "保单[" + id + "]没有填写险种代码");
				return false;
			}
			String PolKey = tLCPolSchema.getContNo() + "-"
					+ tLCPolSchema.getInsuredNo() + "-"
					+ tLCPolSchema.getRiskCode();
			tTransferData.setNameAndValue("PolKey", PolKey);
			tTransferData.setNameAndValue("ContId", tLCPolSchema.getContNo());

			// 得到保费项金额及关联帐户比例
			VData tVData = getPremRelated(node);
			LCPremSet tLCPremSet = (LCPremSet) tVData.getObjectByObjectName(
					"LCPremSet", 0);
			LCPremToAccSet tLCPremToAccSet = (LCPremToAccSet) tVData
					.getObjectByObjectName("LCPremToAccSet", 0);

			// 得到保险责任计算信息
			LCDutySet tLCDutySet = getLCDutySet(node);
			logger.debug("LCDutySet.encode=" + tLCDutySet.encode());

			if (tLCDutySet != null && tLCDutySet.size() == 1) {
				LCDutySchema tDutySchema = tLCDutySet.get(1);
				tDutySchema.setAmnt(tLCPolSchema.getAmnt());
				tDutySchema.setPrem(tLCPolSchema.getPrem());
				tDutySchema.setMult(tLCPolSchema.getMult());

			}

			// 得到被保人信息
			// LCInsuredSet tLCInsuredSet = getLCInsuredSet(node);

			// 得到受益人信息
			// LCBnfSet tLCBnfSet = getLCBnfSet(node);

			// 返回数据
			vReturn.add(tTransferData);
			vReturn.add(tLCPolSchema);
			vReturn.add(tLCPremSet);
			vReturn.add(tLCPremToAccSet);
			vReturn.add(tLCDutySet);
			// vReturn.add(tLCInsuredSet);
			// vReturn.add(tLCBnfSet);

		} catch (Exception ex) {
			ex.printStackTrace();
			String tError = ex.toString();
			this.mErrors.clearErrors();
			this.mErrors.addOneError(tError.replaceAll("\"", ""));
			logger.debug("磁盘导入->GrpPolParser->parseOneNode：XML解析失败");
			return false;
		}

		return true;
	}

	/**
	 * 利用XPathAPI取得某个节点的节点值
	 * 
	 * @param node
	 *            Node
	 * @param strPath
	 *            String
	 * @return String
	 */
	private static String parseNode(Node node, String strPath) {
		String strValue = "";

		try {
			XObject xobj = XPathAPI.eval(node, strPath);
			strValue = xobj == null ? "" : xobj.toString();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strValue;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "GrpPolParser";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 得到一些不好用Schema表示的信息
	 * 
	 * @param node
	 *            Node
	 * @return TransferData
	 */
	private static TransferData getTransferData(Node node) {
		TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("GrpContNo",parseNode(node,PATH_GROUPCONTNO));;
		tTransferData.setNameAndValue("GetDutyKind", parseNode(node,
				PATH_GetDutyKind));
		tTransferData.setNameAndValue("GetIntv", parseNode(node, PATH_GetIntv));
		tTransferData.setNameAndValue("ID", parseNode(node, PATH_InSuredNo)
				+ "-" + parseNode(node, PATH_RiskCode));
		tTransferData.setNameAndValue("SavePolType", parseNode(node,
				PATH_SavePolType));

		return tTransferData;
	}

	/**
	 * 得到个人险种保单的信息
	 * 
	 * @param node
	 *            Node
	 * @return LCPolSchema
	 */
	private static LCPolSchema getLCPolSchema(Node node) {
		LCPolSchema tLCPolSchema = new LCPolSchema();

		tLCPolSchema.setGrpPolNo(parseNode(node, PATH_GrpPolNo));

		tLCPolSchema.setContNo(parseNode(node, PATH_InSuredNo));
		tLCPolSchema.setRiskCode(parseNode(node, PATH_RiskCode));
		tLCPolSchema.setInsuredNo(parseNode(node, PATH_InSuredNo));

		tLCPolSchema.setMainPolNo(parseNode(node, PATH_MainRiskCode));
		logger.debug("主险编码======" + tLCPolSchema.getMainPolNo() + "-"
				+ parseNode(node, PATH_MainRiskCode));
		// //借用StandbyFlag3保存责任代码
		// tLCPolSchema.setStandbyFlag3(parseNode(node,
		// GrpPolParser.
		// PATH_POL_Other_Insured));
		logger.debug("责任===" + tLCPolSchema.getStandbyFlag3());
		logger.debug("责任==="
				+ parseNode(node, GrpPolParser.PATH_POL_Other_Insured));
		// 借用StandbyFlag2保存风险加费
		tLCPolSchema.setStandbyFlag2(parseNode(node,
				GrpPolParser.PATH_RiskAddPrem));
		// tLCPolSchema.setAppFlag(parseNode(node,GrpPolParser.PATH_POL_Other_Insured));

		tLCPolSchema.setHealthCheckFlag(parseNode(node, PATH_HealthCheckFlag));
		tLCPolSchema.setPayLocation(parseNode(node, PATH_PayLocation));

		tLCPolSchema.setLiveGetMode(parseNode(node, PATH_LiveGetMode));
		tLCPolSchema.setBonusGetMode(parseNode(node, PATH_BonusGetMode));

		tLCPolSchema.setPayIntv(parseNode(node, PATH_PayIntv));
		tLCPolSchema.setInsuYear(parseNode(node, PATH_InsuYear));
		tLCPolSchema.setInsuYearFlag(parseNode(node, PATH_InsuYearFlag));
		tLCPolSchema.setPayEndYear(parseNode(node, PATH_PayEndYear));
		tLCPolSchema.setPayEndYearFlag(parseNode(node, PATH_PayEndYearFlag));
		tLCPolSchema.setGetYear(parseNode(node, PATH_GetYear));
		tLCPolSchema.setGetYearFlag(parseNode(node, PATH_GetYearFlag));
		tLCPolSchema.setGetStartType(parseNode(node, PATH_GetStartType));

		tLCPolSchema.setMult(parseNode(node, PATH_Mult));
		tLCPolSchema.setPrem(parseNode(node, PATH_Prem));
		tLCPolSchema.setAmnt(parseNode(node, PATH_Amnt));
		tLCPolSchema.setRnewFlag(parseNode(node, PATH_RnewFlag));
		tLCPolSchema.setSpecifyValiDate(parseNode(node, PATH_SpecifyValiDate));
		tLCPolSchema.setPayRuleCode(parseNode(node, PATH_PayRuleCode));
		tLCPolSchema
				.setAscriptionRuleCode(parseNode(node, PATH_AscriptionCode));
		// 指定生效日期标志 为"1"才设置 生效日期
		if (tLCPolSchema.getSpecifyValiDate().equals("1")) {
			tLCPolSchema.setCValiDate(parseNode(node, PATH_CValiDate));
		}
		tLCPolSchema.setFloatRate(parseNode(node, PATH_FloatRate));
		tLCPolSchema.setRemark(parseNode(node, PATH_Remark));
		// 计算方向
		tLCPolSchema.setPremToAmnt(parseNode(node, PATH_PremToAmnt));
		// 借用 最终核保人编码UWCode 缓存 计算规则
		tLCPolSchema.setUWCode(parseNode(node, PATH_CalRule));
		// tLCPolSchema.setPolTypeFlag(parseNode(node, PATH_PolTypeFlag));

		tLCPolSchema.setStandbyFlag1(parseNode(node, PATH_StandbyFlag1));
		tLCPolSchema.setStandbyFlag2(parseNode(node, PATH_StandbyFlag2));
		tLCPolSchema.setStandbyFlag3(parseNode(node, PATH_StandbyFlag3));// edit
																			// by
																			// yaory
		// tLCPolSchema.setInsuredPeoples(parseNode(node, PATH_InsuredPeoples));

		// 借用[复核状态]缓存 免赔额
		tLCPolSchema.setApproveFlag(parseNode(node, PATH_GetLimit));
		// 借用[核保状态]缓存 赔付比例
		tLCPolSchema.setUWFlag(parseNode(node, PATH_GetRate));

		return tLCPolSchema;
	}

	/**
	 * 连身被保险人信息
	 * 
	 * @param node
	 *            Node
	 * @return SchemaSet
	 */
	public SchemaSet getLCInsuredRelatedSet(Node node) {
		LCInsuredRelatedSet tSet = new LCInsuredRelatedSet();
		NodeList nodeList = null;

		try {
			nodeList = XPathAPI
					.selectNodeList(node, GrpPolParser.PATH_INS_RELA);
		} catch (Exception ex) {
			ex.printStackTrace();
			nodeList = null;
		}

		int nLength = nodeList == null ? 0 : nodeList.getLength();
		// LCBnfSet tLCBnfSet = new LCBnfSet();

		for (int nIndex = 0; nIndex < nLength; nIndex++) {
			Node nodeBnf = nodeList.item(nIndex);
			LCInsuredRelatedSchema tLCInsuredRelatedSchema = new LCInsuredRelatedSchema();

			tLCInsuredRelatedSchema.setMainCustomerNo(parseNode(nodeBnf,
					GrpPolParser.PATH_INS_RELA_INSUREDID));
			// 被保险人序号
			tLCInsuredRelatedSchema.setCustomerNo(parseNode(nodeBnf,
					GrpPolParser.PATH_INS_RELA_ID));
			tLCInsuredRelatedSchema.setOperator(parseNode(nodeBnf,
					GrpPolParser.PATH_INS_RELA_CONTID)); // 借用，保存ContId
			tLCInsuredRelatedSchema.setPolNo(parseNode(nodeBnf,
					PATH_INS_RELA_RISKCODE)); // Riskcode

			tSet.add(tLCInsuredRelatedSchema);
		}

		return tSet;

	}

	/**
	 * 得到跟保费相关的信息:保费项金额以及关联帐户比例
	 * 
	 * @param node
	 *            Node
	 * @return VData
	 */
	private static VData getPremRelated(Node node) {
		NodeList nodeList = null;

		try {
			nodeList = XPathAPI.selectNodeList(node, PATH_PREM);
		} catch (Exception ex) {
			ex.printStackTrace();
			nodeList = null;
		}

		int nLength = nodeList == null ? 0 : nodeList.getLength();

		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
		LCPremSet tLCPremSet = new LCPremSet();

		for (int nIndex = 0; nIndex < nLength; nIndex++) {
			Node nodePrem = nodeList.item(nIndex);
			// 保费项表和客户帐户表的关联表
			LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();

			tLCPremToAccSchema.setDutyCode(parseNode(nodePrem, PATH_DutyCode));
			tLCPremToAccSchema.setPayPlanCode(parseNode(nodePrem,
					PATH_PayPlanCode));
			tLCPremToAccSchema
					.setInsuAccNo(parseNode(nodePrem, PATH_InsuAccNo));
			tLCPremToAccSchema.setRate(parseNode(nodePrem, PATH_InsuAccRate));

			tLCPremToAccSet.add(tLCPremToAccSchema);

			// 保费项表(目前仅对众悦年金有用<保费保额不用计算的>,在承保后台会判断处理)
			LCPremSchema tLCPremSchema = new LCPremSchema();

			tLCPremSchema.setDutyCode(tLCPremToAccSchema.getDutyCode()); // 责任编码
			tLCPremSchema.setPayPlanCode(tLCPremToAccSchema.getPayPlanCode()); // 缴费计划编码
			tLCPremSchema.setStandPrem(parseNode(nodePrem, PATH_Prem));
			if (parseNode(nodePrem, PATH_PayPlanRate) == null
					|| parseNode(nodePrem, PATH_PayPlanRate).equals("")) {
				tLCPremSchema.setRate(1);
			} else {
				tLCPremSchema.setRate(parseNode(nodePrem, PATH_PayPlanRate));
			}
			/*
			 * Lis5.3 upgrade set tLCPremSchema.setManageFeeRate(
			 * parseNode(nodePrem, PATH_ManageFeeRate) );
			 */

			tLCPremSchema.setStandPrem(tLCPremSchema.getStandPrem()
					* tLCPremSchema.getRate()); // 标准保费
			tLCPremSchema.setPrem(tLCPremSchema.getStandPrem()); // 实际保费
			tLCPremSet.add(tLCPremSchema);
		}

		VData tVData = new VData();

		tVData.add(tLCPremToAccSet);
		tVData.add(tLCPremSet);

		return tVData;
	}

	/**
	 * 得到保单责任计算信息
	 * 
	 * @param node
	 *            Node
	 * @return LCDutySet
	 */
	private static LCDutySet getLCDutySet(Node node) {
		NodeList nodeList = null;

		try {
			nodeList = XPathAPI.selectNodeList(node, PATH_DUTY);
		} catch (Exception ex) {
			ex.printStackTrace();
			nodeList = null;
		}

		int nLength = nodeList == null ? 0 : nodeList.getLength();
		LCDutySet tLCDutySet = new LCDutySet();

		for (int nIndex = 0; nIndex < nLength; nIndex++) {
			Node nodeDuty = nodeList.item(nIndex);

			LCDutySchema tLCDutySchema = new LCDutySchema();

			tLCDutySchema.setDutyCode(parseNode(nodeDuty, PATH_DutyCode));
			tLCDutySchema.setPayIntv(parseNode(nodeDuty, PATH_PayIntv));
			tLCDutySchema.setInsuYear(parseNode(nodeDuty, PATH_InsuYear));
			tLCDutySchema
					.setInsuYearFlag(parseNode(nodeDuty, PATH_InsuYearFlag));
			tLCDutySchema.setPayEndYear(parseNode(nodeDuty, PATH_PayEndYear));
			tLCDutySchema.setPayEndYearFlag(parseNode(nodeDuty,
					PATH_PayEndYearFlag));
			tLCDutySchema.setGetYear(parseNode(nodeDuty, PATH_GetYear));
			tLCDutySchema.setGetYearFlag(parseNode(nodeDuty, PATH_GetYearFlag));
			tLCDutySchema
					.setGetStartType(parseNode(nodeDuty, PATH_GetStartType));

			tLCDutySchema.setMult(parseNode(nodeDuty, PATH_Mult));
			tLCDutySchema.setPrem(parseNode(nodeDuty, PATH_Prem));
			tLCDutySchema.setAmnt(parseNode(nodeDuty, PATH_Amnt));
			tLCDutySchema.setFloatRate(parseNode(nodeDuty, PATH_FloatRate));
			// 计算方向
			tLCDutySchema.setPremToAmnt(parseNode(nodeDuty, PATH_PremToAmnt));
			// 计算规则
			tLCDutySchema.setCalRule(parseNode(nodeDuty, PATH_CalRule));
			tLCDutySchema.setGetLimit(parseNode(nodeDuty, PATH_GetLimit));
			tLCDutySchema.setGetRate(parseNode(nodeDuty, PATH_GetRate));
			tLCDutySchema
					.setStandbyFlag1(parseNode(nodeDuty, PATH_StandbyFlag1));
			tLCDutySchema
					.setStandbyFlag2(parseNode(nodeDuty, PATH_StandbyFlag2));
			tLCDutySchema
					.setStandbyFlag3(parseNode(nodeDuty, PATH_StandbyFlag3));

			tLCDutySet.add(tLCDutySchema);
		}

		return tLCDutySet;
	}

	/**
	 * 得到被保人信息
	 * 
	 * @param node
	 *            Node
	 * @return LCInsuredSet
	 */
	public LCInsuredSet getLCInsuredSet(Node node) {
		NodeList nodeList = null;

		try {
			nodeList = XPathAPI.selectNodeList(node, PATH_INSURED);
		} catch (Exception ex) {
			ex.printStackTrace();
			nodeList = null;
			return null;
		}
		try {
			int nLength = nodeList == null ? 0 : nodeList.getLength();
			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			for (int nIndex = 0; nIndex < nLength; nIndex++) {
				Node nodeInsured = nodeList.item(nIndex);
				LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
				// 合同序号,依靠这个序号标示被保险人是否同在一个合同中
				tLCInsuredSchema.setContNo(parseNode(nodeInsured,
						GrpPolParser.PATH_ID));
				tLCInsuredSchema.setCustomerSeqNo(parseNode(nodeInsured,
						GrpPolParser.PATH_ID));
				logger.debug(tLCInsuredSchema.getContNo());
				// tLCInsuredSchema.setInsuredNo(parseNode(nodeInsured,
				// PATH_CustomerNo));
				// logger.debug(tLCInsuredSchema.getInsuredNo());
				// 借用prtNo保存被保人序号
				tLCInsuredSchema.setPrtNo(parseNode(nodeInsured,
						GrpPolParser.PATH_ID));
				logger.debug(tLCInsuredSchema.getPrtNo());
				// 借用InsuredStat保存员工状态
				tLCInsuredSchema.setInsuredStat(parseNode(nodeInsured,
						PATH_CustomerState));
				logger.debug(tLCInsuredSchema.getInsuredStat());
				// 在这需要转换成我们数据库中的数据，界面只有1-本人，2-配偶，3-子女
				String temrelation = parseNode(nodeInsured,
						PATH_RelationToMainInsured);
				String trurelation = "";
				if (temrelation.equals("1")) {
					trurelation = "00";
				} else if (temrelation.equals("2")) {
					trurelation = "07";
				} else {
					trurelation = "22";
				}

				tLCInsuredSchema.setRelationToMainInsured(trurelation);
				logger.debug(tLCInsuredSchema.getRelationToMainInsured());
				// tLCInsuredSchema.setRelationToMainInsured(
				// parseNode(nodeInsured, PATH_InsuredGrade) );
				tLCInsuredSchema.setName(parseNode(nodeInsured,
						PATH_InsuredName));
				logger.debug(tLCInsuredSchema.getName());
				tLCInsuredSchema
						.setSex(parseNode(nodeInsured, PATH_InsuredSex));
				logger.debug(tLCInsuredSchema.getSex());
				tLCInsuredSchema.setBirthday(parseNode(nodeInsured,
						PATH_InsuredBirthday));
				logger.debug(parseNode(nodeInsured, PATH_InsuredBirthday));
				tLCInsuredSchema.setIDType(parseNode(nodeInsured,
						PATH_InsuredIDType));
				logger.debug(tLCInsuredSchema.getIDType());
				tLCInsuredSchema.setIDNo(parseNode(nodeInsured,
						PATH_InsuredIDNo));
				logger.debug(tLCInsuredSchema.getIDNo());
				tLCInsuredSchema.setRgtAddress(parseNode(nodeInsured,
						PATH_RgtAddress));
				logger.debug(tLCInsuredSchema.getRgtAddress());
				tLCInsuredSchema.setMarriage(parseNode(nodeInsured,
						PATH_Marriage));
				logger.debug(tLCInsuredSchema.getMarriage());
				tLCInsuredSchema.setWorkType(parseNode(nodeInsured,
						PATH_WorkType));
				logger.debug(tLCInsuredSchema.getWorkType());
				tLCInsuredSchema.setPluralityType(parseNode(nodeInsured,
						PATH_PluralityType));
				logger.debug(tLCInsuredSchema.getPluralityType());
				tLCInsuredSchema.setOccupationType(parseNode(nodeInsured,
						PATH_OccupationType));
				logger.debug(tLCInsuredSchema.getOccupationType()); // 职业被存储到两个字段上
				tLCInsuredSchema.setOccupationCode(parseNode(nodeInsured,
						PATH_OccupationCode));
				logger.debug(tLCInsuredSchema.getOccupationCode());
				tLCInsuredSchema.setBankCode(parseNode(nodeInsured,
						PATH_BankCode));
				tLCInsuredSchema.setBankAccNo(parseNode(nodeInsured,
						PATH_BankAccNo));
				tLCInsuredSchema
						.setAccName(parseNode(nodeInsured, PATH_AccName));
				tLCInsuredSchema.setSalary(parseNode(nodeInsured,
						GrpPolParser.PATH_Salary));
				tLCInsuredSchema.setSocialInsuNo(parseNode(nodeInsured,
						GrpPolParser.PATH_SocialInsuNo));
				tLCInsuredSchema.setJoinCompanyDate(parseNode(nodeInsured,
						GrpPolParser.PATH_JoinCompanyDate));
				tLCInsuredSchema.setContPlanCode(parseNode(nodeInsured,
						GrpPolParser.PATH_ContPlan));
				// 借用被保人信用等级字段保存 保单类型标记
				tLCInsuredSchema.setCreditGrade(parseNode(nodeInsured,
						PATH_PolTypeFlag));
				// 如果excel文件中为空，则取默认为"0"
				if ("".equals(tLCInsuredSchema.getCreditGrade())) {
					tLCInsuredSchema.setCreditGrade("0");
				}
				tLCInsuredSchema.setInsuredPeoples(1);
				// 借用入机时间字段保存 被保人数目
				tLCInsuredSchema.setMakeTime(parseNode(nodeInsured,
						PATH_InsuredPeoples));
				tLCInsuredSchema.setStature(parseNode(nodeInsured, PATH_Mult)); // 借助此字段暂存保险计划份数

				// 中英,通过身份证算生日性别
				if ("0".equals(StrTool.cTrim(tLCInsuredSchema.getIDType()))) {
					if (""
							.equals(StrTool.cTrim(tLCInsuredSchema
									.getBirthday()))
							|| StrTool.cTrim(tLCInsuredSchema.getBirthday()) == null
							|| "".equals(StrTool.cTrim(tLCInsuredSchema
									.getSex()))
							|| StrTool.cTrim(tLCInsuredSchema.getSex()) == null) {
						String rno = tLCInsuredSchema.getIDNo();
						String tempbirthday = "";
						String tempsex = "";
						if (rno.length() == 18) {
							tempbirthday = rno.substring(6, 10) + "-"
									+ rno.substring(10, 12) + "-"
									+ rno.substring(12, 14);
							int temp = Integer.parseInt(rno.substring(16, 17));
							if (temp % 2 == 0) {
								tempsex = "1";
							} else {
								tempsex = "0";
							}
						}
						if (rno.length() == 15) {
							tempbirthday = "19" + rno.substring(6, 8) + "-"
									+ rno.substring(8, 10) + "-"
									+ rno.substring(10, 12);
							int pem = Integer.parseInt(rno.substring(14, 15));
							if (pem % 2 == 0) {
								tempsex = "1";
							} else {
								tempsex = "0";
							}

						}
						if ("".equals(StrTool.cTrim(tLCInsuredSchema
								.getBirthday()))
								|| StrTool
										.cTrim(tLCInsuredSchema.getBirthday()) == null) {
							tLCInsuredSchema.setBirthday(tempbirthday);
						}
						if ("".equals(StrTool.cTrim(tLCInsuredSchema.getSex()))
								|| StrTool.cTrim(tLCInsuredSchema.getSex()) == null) {
							tLCInsuredSchema.setSex(tempsex);
						}
					}
				}
				tLCInsuredSet.add(tLCInsuredSchema);
			}
			return tLCInsuredSet;
		} catch (Exception ex) {
			ex.printStackTrace();
			String tError = ex.toString();
			this.mErrors.clearErrors();
			this.mErrors.addOneError(tError.replaceAll("\"", ""));
			logger.debug("磁盘导入->GrpPolParser->getLCInsuredSet:xml解析失败");
			return null;
		}
	}

	/**
	 * 得到受益人的信息
	 * 
	 * @param node
	 *            Node
	 * @return LCBnfSet
	 */
	public LCBnfSet getLCBnfSet(Node node) {
		NodeList nodeList = null;

		try {
			nodeList = XPathAPI.selectNodeList(node, PATH_BNF);
		} catch (Exception ex) {
			ex.printStackTrace();
			nodeList = null;
		}
		try {
			int nLength = nodeList == null ? 0 : nodeList.getLength();
			LCBnfSet tLCBnfSet = new LCBnfSet();

			for (int nIndex = 0; nIndex < nLength; nIndex++) {
				Node nodeBnf = nodeList.item(nIndex);
				LCBnfSchema tLCBnfSchema = new LCBnfSchema();

				tLCBnfSchema.setBnfType(parseNode(nodeBnf, PATH_BnfType));
				// 被保险人序号
				tLCBnfSchema.setInsuredNo(parseNode(nodeBnf, PATH_INSUREDID));
				tLCBnfSchema.setContNo(parseNode(nodeBnf,
						GrpPolParser.PATH_INSUREDID));
				// 借用 PolNo 缓存 险种代码
				tLCBnfSchema.setPolNo(parseNode(nodeBnf, PATH_BNF_RiskCode));
				tLCBnfSchema.setName(parseNode(nodeBnf, PATH_BnfName));
				tLCBnfSchema.setSex(parseNode(nodeBnf, PATH_BnfSex));
				tLCBnfSchema.setIDType(parseNode(nodeBnf, PATH_BnfIDType));
				tLCBnfSchema.setIDNo(parseNode(nodeBnf, PATH_BnfIDNo));
				tLCBnfSchema.setBirthday(parseNode(nodeBnf, PATH_BnfBirthday));
				tLCBnfSchema.setRelationToInsured(parseNode(nodeBnf,
						PATH_RelationToInsured));

				tLCBnfSchema.setBnfLot(parseNode(nodeBnf, PATH_BnfLot));
				if (tLCBnfSchema.getBnfLot() == 0) {
					tLCBnfSchema.setBnfLot(1);
				}

				tLCBnfSchema.setBnfGrade(parseNode(nodeBnf, PATH_BnfGrade));
				// 借用BnfNo 缓存 险种ID
				tLCBnfSchema.setBnfNo(parseNode(nodeBnf, PATH_INSUREDID));
				// 中英,通过身份证算生日性别
				if ("0".equals(StrTool.cTrim(tLCBnfSchema.getIDType()))) {
					if ("".equals(StrTool.cTrim(tLCBnfSchema.getBirthday()))
							|| StrTool.cTrim(tLCBnfSchema.getBirthday()) == null
							|| "".equals(StrTool.cTrim(tLCBnfSchema.getSex()))
							|| StrTool.cTrim(tLCBnfSchema.getSex()) == null) {
						String rno = tLCBnfSchema.getIDNo();
						String tempbirthday = "";
						String tempsex = "";
						if (rno.length() == 18) {
							tempbirthday = rno.substring(6, 10) + "-"
									+ rno.substring(10, 12) + "-"
									+ rno.substring(12, 14);
							int temp = Integer.parseInt(rno.substring(16, 17));
							if (temp % 2 == 0) {
								tempsex = "1";
							} else {
								tempsex = "0";
							}
						}
						if (rno.length() == 15) {
							tempbirthday = "19" + rno.substring(6, 8) + "-"
									+ rno.substring(8, 10) + "-"
									+ rno.substring(10, 12);
							int pem = Integer.parseInt(rno.substring(14, 15));
							if (pem % 2 == 0) {
								tempsex = "1";
							} else {
								tempsex = "0";
							}

						}
						if (""
								.equals(StrTool.cTrim(tLCBnfSchema
										.getBirthday()))
								|| StrTool.cTrim(tLCBnfSchema.getBirthday()) == null) {
							tLCBnfSchema.setBirthday(tempbirthday);
						}
						if ("".equals(StrTool.cTrim(tLCBnfSchema.getSex()))
								|| StrTool.cTrim(tLCBnfSchema.getSex()) == null) {
							tLCBnfSchema.setSex(tempsex);
						}
					}
				}
				tLCBnfSet.add(tLCBnfSchema);
			}

			return tLCBnfSet;
		} catch (Exception ex) {
			ex.printStackTrace();
			String tError = ex.toString();
			this.mErrors.clearErrors();
			this.mErrors.addOneError(tError.replaceAll("\"", ""));
			logger.debug("磁盘导入->GrpPolParser->getLCInsuredSet:xml解析失败");
			return null;

		}
	}
}
