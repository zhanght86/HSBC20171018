/*
 * @(#)GrpPolParser.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.PubFun;
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
import com.sinosoft.lis.vschema.LDOccupationSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
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
	//private static String PATH_CustomerNo = "CustomerNo";
	private static String PATH_MainInsuredNo = "MainInsuredNo";//主被保人公司内部序号
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
	private static String PATH_ContCValiDate = "ContCValiDate";
	
	// 增加个人地址处理
	private static String PATH_Mobile = "Mobile";
	private static String PATH_EMail = "EMail";
	private static String PATH_BankCode = "BankCode";
	private static String PATH_BankAccNo = "BankAccNo";
	private static String PATH_AccName = "AccName";
	private static String PATH_JoinCompanyDate = "JoinCompanyDate";
	private static String PATH_Salary = "Salary";
	private static String PATH_ContPlan = "ContPlan";
	private static String PATH_PolTypeFlag = "PolTypeFlag";
	private static String PATH_InsuredPeoples = "InsuredPeoples";
	private static String PATH_CertifyCode = "CertifyCode";//add by liuqh
	private static String PATH_StartCode = "StartCode";
	private static String PATH_EndCode = "EndCode";
	//private static String PATH_MainInsuredNo = "MainInsuredNo";//主被保人公司内部序号
	
	// 下面是一些常量定义
	private static String PATH_ID = "ID";

	// private static String PATH_GrpContNo = "GrpContNo";
	private static String PATH_GrpPolNo = "GrpPolNo";
	private static String PATH_POL_ContNo = "PolContNo";
	private static String PATH_RiskCode = "RiskCode";
	private static String PATH_InSuredNo = "InsuredNo";
	private static String PATH_POL_Other_Insured = "OtherInsured";
	private static String PATH_MainPolNo = "MainPolNo";
	private static String PATH_HealthCheckFlag = "HealthCheckFlag";
	private static String PATH_OutPayFlag = "OutPayFlag";
	private static String PATH_PayLocation = "PayLocation";

	private static String PATH_LiveGetMode = "LiveGetMode";
	private static String PATH_GetDutyKind = "GetDutyKind";
	private static String PATH_GetIntv = "GetIntv";
	private static String PATH_BonusGetMode = "BonusGetMode";
	// 增加缴费规则与归属规则
	private static String PATH_PolAscriptionRule = "PolAscriptionRule";
	private static String PATH_PolPayRuleCode = "PolPayRuleCode";
	private static String mdutygetkind = "DutyGetDutyKind";
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
	
	private static String PATH_Cal_StandbyFlag1 = "CalStandbyFlag1";//241801或241802保存时会录入保存计划A、B&0、1;
    //用于计算保费.参考5.3strandbyflag1
	private static String PATH_Cal_StandbyFlag2 = "CalStandbyFlag2";//241801或241802保存时会录入录入连带人数
		//用于计算保费.参考5.3strandbyflag2
	//add by liuqh 2009-02-11 
    private static String PATH_RelationToRisk = "RelationToRisk";
    //用于区分连带被保人连带到主被保人的险种 add by liuqh 2009-03-05
    
    //MS乘客综合意外伤害保险
    private static String PATH_TakeDate ="TakeDate";
    private static String PATH_TakeTime ="TakeTime";
    private static String PATH_AirNo ="AirNo";
    private static String PATH_TicketNo ="TicketNo";
    private static String PATH_SeatNo ="SeatNo";

	private static String PATH_PREM = "SUBPREMTABLE/ROW";
	private static String PATH_DutyCode = "DutyCode";
	private static String PATH_PayPlanCode = "PayPlanCode";
	private static String PATH_PayPlanRate = "PayPlanRate";
	private static String PATH_PayPlanPrem = "PayPlanPrem";
	private static String PATH_InsuAccNo = "InsuAccNo";
	private static String PATH_InsuAccRate = "InsuAccRate";
	private static String PATH_ManageFeeRate = "ManageFeeRate";
	// 增加缴费规则与归属规则
	private static String PATH_AscriptionRuleCode = "AscriptionRuleCode";
	private static String PATH_PayRuleCode = "PayRuleCode";
	private static String PATH_DutyBonusGetMode = "DuytBonusGetMode";
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
//	private static String PATH_INS_RELA_CONTID = "ContIdRELA";
//	private static String PATH_INS_RELA_INSUREDID = "InsuredId";
//	private static String PATH_INS_RELA_RISKCODE = "RiskCode";
//	private static String PATH_INS_RELA_ID = "RelaId";
	public CErrors mErrors = new CErrors();
	private static String mContCValidate="";

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
		if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0)
			return null;

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
			if (parseOneNode(nodeList.item(nIndex), tData)) // 获取责任与保费项
			{
				reData.add(tData);
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

			// if (tLCDutySet != null && tLCDutySet.size() == 1)
			// {
			// LCDutySchema tDutySchema = tLCDutySet.get(1);
			// tDutySchema.setAmnt(tLCPolSchema.getAmnt());
			// tDutySchema.setPrem(tLCPolSchema.getPrem());
			// tDutySchema.setMult(tLCPolSchema.getMult());
			//
			// }

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
		tTransferData.setNameAndValue("ID", parseNode(node, PATH_ID));
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

		tLCPolSchema.setGrpPolNo(StrTool.cTrim(parseNode(node, PATH_GrpPolNo)));

		tLCPolSchema.setContNo(StrTool.cTrim(parseNode(node, GrpPolParser.PATH_POL_ContNo)));
		tLCPolSchema.setRiskCode(StrTool.cTrim(parseNode(node, PATH_RiskCode)));
		// tLCPolSchema.setInsuredNo(parseNode(node, PATH_InSuredNo));
		tLCPolSchema
				.setInsuredNo(StrTool.cTrim(parseNode(node, GrpPolParser.PATH_POL_ContNo)));
		tLCPolSchema.setMainPolNo(StrTool.cTrim(parseNode(node, PATH_MainPolNo)));
		// 借用AppFlag保存连身被保险人
		tLCPolSchema.setAppFlag(StrTool.cTrim(parseNode(node,GrpPolParser.PATH_POL_Other_Insured)));

		tLCPolSchema.setHealthCheckFlag(StrTool.cTrim(parseNode(node, PATH_HealthCheckFlag)));
		tLCPolSchema.setPayLocation(StrTool.cTrim(parseNode(node, PATH_PayLocation)));

		tLCPolSchema.setLiveGetMode(StrTool.cTrim(parseNode(node, PATH_LiveGetMode)));
		tLCPolSchema.setBonusGetMode(StrTool.cTrim(parseNode(node, PATH_BonusGetMode)));

		tLCPolSchema.setPayIntv(StrTool.cTrim(parseNode(node, PATH_PayIntv)));
		tLCPolSchema.setInsuYear(StrTool.cTrim(parseNode(node, PATH_InsuYear)));
		tLCPolSchema.setInsuYearFlag(StrTool.cTrim(parseNode(node, PATH_InsuYearFlag)));
		tLCPolSchema.setPayEndYear(StrTool.cTrim(parseNode(node, PATH_PayEndYear)));
		tLCPolSchema.setPayEndYearFlag(StrTool.cTrim(parseNode(node, PATH_PayEndYearFlag)));
		tLCPolSchema.setGetYear(StrTool.cTrim(parseNode(node, PATH_GetYear)));
		tLCPolSchema.setGetYearFlag(StrTool.cTrim(parseNode(node, PATH_GetYearFlag)));
		tLCPolSchema.setGetStartType(StrTool.cTrim(parseNode(node, PATH_GetStartType)));

		tLCPolSchema.setMult(StrTool.cTrim(parseNode(node, PATH_Mult)));
		tLCPolSchema.setPrem(StrTool.cTrim(parseNode(node, PATH_Prem)));
		tLCPolSchema.setAmnt(StrTool.cTrim(parseNode(node, PATH_Amnt)));
		//211802MS乘客综合意外伤害保险
		tLCPolSchema.setTakeDate(StrTool.cTrim(parseNode(node,PATH_TakeDate)));
		tLCPolSchema.setTakeTime(StrTool.cTrim(parseNode(node,PATH_TakeTime)));
		tLCPolSchema.setAirNo(StrTool.cTrim(parseNode(node,PATH_AirNo)));
		tLCPolSchema.setTicketNo(StrTool.cTrim(parseNode(node,PATH_TicketNo)));
		tLCPolSchema.setSeatNo(StrTool.cTrim(parseNode(node,PATH_SeatNo)));
		
		tLCPolSchema.setRnewFlag(StrTool.cTrim(parseNode(node, PATH_RnewFlag)));
		tLCPolSchema.setSpecifyValiDate(StrTool.cTrim(parseNode(node, PATH_SpecifyValiDate)));
		// 指定生效日期标志 为"1"才设置 生效日期
		if (tLCPolSchema.getSpecifyValiDate().equals("Y")) {
			tLCPolSchema.setCValiDate(StrTool.cTrim(parseNode(node, PATH_CValiDate)));
		}else{
		}
		//只要被保人sheet页中的cvalidate有值就可以
		if(mContCValidate!=null&&!"".equals(mContCValidate)){
			tLCPolSchema.setCValiDate(mContCValidate);
			tLCPolSchema.setSpecifyValiDate("Y");
		}
		tLCPolSchema.setFloatRate(StrTool.cTrim(parseNode(node, PATH_FloatRate)));
		tLCPolSchema.setRemark(StrTool.cTrim(parseNode(node, PATH_Remark)));
		// 计算方向
		tLCPolSchema.setPremToAmnt(StrTool.cTrim(parseNode(node, PATH_PremToAmnt)));
		// 借用 最终核保人编码UWCode 缓存 计算规则
		tLCPolSchema.setUWCode(StrTool.cTrim(parseNode(node, PATH_CalRule)));
		// tLCPolSchema.setPolTypeFlag(parseNode(node, PATH_PolTypeFlag));

		//由于险种页面的standbyflag1、2、3隐藏,不会录入;暂时保存241801或241802的计划或是否连带和人数 
		tLCPolSchema.setStandbyFlag1(StrTool.cTrim(parseNode(node, PATH_StandbyFlag1)));
		tLCPolSchema.setStandbyFlag2(StrTool.cTrim(parseNode(node, PATH_StandbyFlag2)));
		tLCPolSchema.setStandbyFlag3(StrTool.cTrim(parseNode(node, PATH_StandbyFlag3)));
		// tLCPolSchema.setInsuredPeoples(parseNode(node, PATH_InsuredPeoples));
		// 增加缴费规则与归属规则
		tLCPolSchema.setAscriptionRuleCode(StrTool.cTrim(parseNode(node,PATH_PolAscriptionRule)));
		tLCPolSchema.setPayRuleCode(StrTool.cTrim(parseNode(node, PATH_PolPayRuleCode)));
		// 借用[复核状态]缓存 免赔额
		tLCPolSchema.setApproveFlag(StrTool.cTrim(parseNode(node, PATH_GetLimit)));
		// 借用[核保状态]缓存 赔付比例
		tLCPolSchema.setUWFlag(StrTool.cTrim(parseNode(node, PATH_GetRate)));

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
		//VData tVData=new VData();
		//TransferData tTransferData=new TransferData();
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

//		for (int nIndex = 0; nIndex < nLength; nIndex++) {
//			Node nodeBnf = nodeList.item(nIndex);
//			LCInsuredRelatedSchema tLCInsuredRelatedSchema = new LCInsuredRelatedSchema();
//
//			tLCInsuredRelatedSchema.setMainCustomerNo(parseNode(nodeBnf,
//					GrpPolParser.PATH_INS_RELA_INSUREDID));
//			// 被保险人序号
//			tLCInsuredRelatedSchema.setCustomerNo(parseNode(nodeBnf,
//					GrpPolParser.PATH_INS_RELA_ID));
//			tLCInsuredRelatedSchema.setOperator(parseNode(nodeBnf,
//					GrpPolParser.PATH_INS_RELA_CONTID)); // 借用，保存ContId
//			tLCInsuredRelatedSchema.setPolNo(parseNode(nodeBnf,
//					PATH_INS_RELA_RISKCODE)); // Riskcode
//
//			tSet.add(tLCInsuredRelatedSchema);
//		}
		//modify by liuqh 2009-02-09 磁盘导入支持连带被保险人
		for (int nIndex = 0; nIndex < nLength; nIndex++) {
            Node nodeLCInsuRela = nodeList.item(nIndex);
            LCInsuredRelatedSchema tLCInsuredRelatedSchema = new LCInsuredRelatedSchema();

            tLCInsuredRelatedSchema.setPolNo(parseNode(nodeLCInsuRela, PATH_ID));//保单险种号码
            tLCInsuredRelatedSchema.setCustomerNo(parseNode(nodeLCInsuRela, PATH_ID));//客户号码
            tLCInsuredRelatedSchema.setMainCustomerNo(parseNode(nodeLCInsuRela, PATH_MainInsuredNo));//主被保人客户号
            tLCInsuredRelatedSchema.setRelationToInsured(parseNode(nodeLCInsuRela, PATH_RelationToMainInsured));//与主被保人关系
            tLCInsuredRelatedSchema.setAddressNo(parseNode(nodeLCInsuRela, PATH_RgtAddress));//客户地址编码
            tLCInsuredRelatedSchema.setName(parseNode(nodeLCInsuRela, PATH_InsuredName));//姓名
            tLCInsuredRelatedSchema.setSex(parseNode(nodeLCInsuRela, PATH_InsuredSex));//性别
            tLCInsuredRelatedSchema.setBirthday(parseNode(nodeLCInsuRela, PATH_InsuredBirthday));//生日
            tLCInsuredRelatedSchema.setIDNo(parseNode(nodeLCInsuRela, PATH_InsuredIDNo));//证件号码
            tLCInsuredRelatedSchema.setIDType(parseNode(nodeLCInsuRela, PATH_InsuredIDType));//证件类型
            tLCInsuredRelatedSchema.setOperator(parseNode(nodeLCInsuRela, PATH_ID));//操作员
            tLCInsuredRelatedSchema.setMakeDate(parseNode(nodeLCInsuRela, PATH_ID));//入机日期
            tLCInsuredRelatedSchema.setMakeTime(parseNode(nodeLCInsuRela, PATH_ID));//入机时间
            tLCInsuredRelatedSchema.setModifyDate(parseNode(nodeLCInsuRela, PATH_ID));//修改日期
            //tLCInsuredRelatedSchema.setModifyTime(parseNode(nodeLCInsuRela, PATH_ID));//修改时间
            //暂时存放要连带的险种
            tLCInsuredRelatedSchema.setModifyTime(parseNode(nodeLCInsuRela, PATH_RelationToRisk));
            tLCInsuredRelatedSchema.setSequenceNo(parseNode(nodeLCInsuRela, PATH_ID));//公司内部序号
            //tTransferData.setNameAndValue("RelationToRisk", parseNode(nodeLCInsuRela, PATH_RelationToRisk));
            tSet.add(tLCInsuredRelatedSchema);
        }

		//tVData.add(tSet);
		//tVData.add(tTransferData);
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
			tLCPremSchema.setStandPrem(parseNode(nodePrem, PATH_PayPlanPrem));
			tLCPremSchema.setRate(parseNode(nodePrem, PATH_PayPlanRate));
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
			tLCDutySchema.setGetIntv(parseNode(nodeDuty, PATH_GetIntv));
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
			// 增加缴费规则与归属规则
			tLCDutySchema.setAscriptionRuleCode(parseNode(nodeDuty,
					PATH_AscriptionRuleCode));
			tLCDutySchema.setPayRuleCode(parseNode(nodeDuty, PATH_PayRuleCode));
			String t = parseNode(nodeDuty, PATH_DutyBonusGetMode);
			if (t == null || t.equals("")) {
				tLCDutySchema.setBonusGetMode("1");
			} else {
				tLCDutySchema.setBonusGetMode(t);
			}
			logger.debug(parseNode(node, mdutygetkind));
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

		int nLength = nodeList == null ? 0 : nodeList.getLength();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		for (int nIndex = 0; nIndex < nLength; nIndex++) {
			Node nodeInsured = nodeList.item(nIndex);
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			// 合同序号,依靠这个序号标示被保险人是否同在一个合同中
			// tLCInsuredSchema.setContNo(parseNode(nodeInsured,
			// GrpPolParser.
			// PATH_Insured_ContId));
			// tLCInsuredSchema.setWorkNo(parseNode(nodeInsured,
			// GrpPolParser.
			// PATH_Insured_ContId)
			// );
			tLCInsuredSchema.setContNo(parseNode(nodeInsured,
					GrpPolParser.PATH_ID));
			logger.debug(tLCInsuredSchema.getContNo());
			tLCInsuredSchema.setWorkNo(parseNode(nodeInsured,
					GrpPolParser.PATH_ID));

//			tLCInsuredSchema.setInsuredNo(parseNode(nodeInsured,
//					PATH_CustomerNo));
			// 借用prtNo保存被保人序号
			tLCInsuredSchema.setPrtNo(parseNode(nodeInsured,
					GrpPolParser.PATH_ID));
			// 借用InsuredStat保存员工状态
			String tCustomerState = parseNode(nodeInsured, PATH_CustomerState);
			// 目前学历这个字段没有用处，借用这个字段
			if (tCustomerState == null || tCustomerState.equals("")) {
				tCustomerState = "0";
			}
			tLCInsuredSchema.setInsuredStat(tCustomerState);
			tLCInsuredSchema.setDegree(tCustomerState);
			tLCInsuredSchema.setRelationToMainInsured(parseNode(nodeInsured,
					PATH_RelationToMainInsured));

			// tLCInsuredSchema.setRelationToMainInsured( parseNode(nodeInsured,
			// PATH_InsuredGrade) );
			tLCInsuredSchema.setName(parseNode(nodeInsured, PATH_InsuredName));
			tLCInsuredSchema.setSex(parseNode(nodeInsured, PATH_InsuredSex));
			tLCInsuredSchema.setBirthday(parseNode(nodeInsured,
					PATH_InsuredBirthday));
			tLCInsuredSchema.setIDType(parseNode(nodeInsured,
					PATH_InsuredIDType));
			//tongmeng 2009-03-23 modify
			//借用modifydate记录个单生效日期
			tLCInsuredSchema.setModifyDate(parseNode(nodeInsured,
					PATH_ContCValiDate));
			mContCValidate =tLCInsuredSchema.getModifyDate();
			
			if (parseNode(nodeInsured, PATH_InsuredIDType).equals("0")) {
				tLCInsuredSchema.setIDNo(parseNode(nodeInsured,
						PATH_InsuredIDNo).trim().toUpperCase());
			} else {
				tLCInsuredSchema.setIDNo(parseNode(nodeInsured,
						PATH_InsuredIDNo).trim());
			}
			if (parseNode(nodeInsured, PATH_InsuredIDType).equals("0")
					&& tLCInsuredSchema.getIDNo() != null
					&& tLCInsuredSchema.getIDNo().length() == 15) {
//				tLCInsuredSchema.setIDNo(PubFun.TransID(tLCInsuredSchema
//						.getIDNo()));
				tLCInsuredSchema.setIDNo(tLCInsuredSchema
						.getIDNo());
			}
			// added by wanglei
			// 借用这个字段保存邮件
			tLCInsuredSchema.setRgtAddress(parseNode(nodeInsured, PATH_EMail));//

			tLCInsuredSchema.setExecuteCom(parseNode(nodeInsured,
					PATH_RgtAddress));
			tLCInsuredSchema.setMarriage(parseNode(nodeInsured, PATH_Marriage));
			// 借用使用，保存电话
			tLCInsuredSchema.setWorkType(parseNode(nodeInsured, PATH_Mobile));
			tLCInsuredSchema.setPluralityType(parseNode(nodeInsured,
					PATH_PluralityType));
			// tLCInsuredSchema.setOccupationType(tLCInsuredSchema.getWorkType());
			if (parseNode(nodeInsured, PATH_OccupationType) != null
					&& !parseNode(nodeInsured, PATH_OccupationType).equals("")) {
				String tSql = "select codename from ldcode where codetype = 'occupationtype' and code='"
						+ parseNode(nodeInsured, PATH_OccupationType) + "'";
				SSRS tSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(tSql);
				if (tSSRS == null || tSSRS.MaxRow == 0) {
					CError tError = new CError();
					tError.errorMessage = "被保险人职业类别输入不符合数据库规定的取值范围，请查阅后重新填写！";
					mErrors.addOneError(tError);
					return null;
				}
				LDOccupationDB tLDOccupationDB = new LDOccupationDB();
				tLDOccupationDB.setOccupationType(parseNode(nodeInsured, PATH_OccupationType));
				tLDOccupationDB.setOccupationCode(parseNode(nodeInsured, PATH_OccupationCode));
				LDOccupationSet tLDOccupationSet = tLDOccupationDB.query();
				if(tLDOccupationSet == null || tLDOccupationSet.size()<1){
					CError tError = new CError();
					tError.errorMessage = "被保险人职业类别与编码输入不符合数据库规定的取值范围，请查阅后重新填写！";
					mErrors.addOneError(tError);
					return null;
				}
			}
			tLCInsuredSchema.setOccupationCode(parseNode(nodeInsured,
					PATH_OccupationCode));
			tLCInsuredSchema.setOccupationType(parseNode(nodeInsured,
					PATH_OccupationType));
			// tLCInsuredSchema.setOccupationCode(parseNode(nodeInsured,
			// tLCInsuredSchema.getWorkType()));
//			tLCInsuredSchema.setOccupationCode(parseNode(nodeInsured,
//					PATH_WorkType));
			tLCInsuredSchema.setBankCode(parseNode(nodeInsured, PATH_BankCode));
			tLCInsuredSchema
					.setBankAccNo(parseNode(nodeInsured, PATH_BankAccNo));
			tLCInsuredSchema.setAccName(parseNode(nodeInsured, PATH_AccName));
			tLCInsuredSchema.setSalary(parseNode(nodeInsured,
					GrpPolParser.PATH_Salary));

			tLCInsuredSchema.setJoinCompanyDate(parseNode(nodeInsured,
					GrpPolParser.PATH_JoinCompanyDate));
			tLCInsuredSchema.setContPlanCode(parseNode(nodeInsured,
					GrpPolParser.PATH_ContPlan).trim());
			// 借用被保人信用等级字段保存 保单类型标记
			tLCInsuredSchema.setCreditGrade(parseNode(nodeInsured,
					PATH_PolTypeFlag));
			// 如果excel文件中为空，则取默认为"0"
			if ("".equals(tLCInsuredSchema.getCreditGrade())) {
				tLCInsuredSchema.setCreditGrade("0");
			}
			// 借用入机时间字段保存 被保人数目
			tLCInsuredSchema.setMakeTime(parseNode(nodeInsured,
					PATH_InsuredPeoples));
			tLCInsuredSchema.setCertifyCode(parseNode(nodeInsured,
					PATH_CertifyCode));
			tLCInsuredSchema.setStartCode(parseNode(nodeInsured,
					PATH_StartCode));
			tLCInsuredSchema.setEndCode(parseNode(nodeInsured,
					PATH_EndCode));
			//add by liuqh 2009-02-09 
			tLCInsuredSchema.setSequenceNo(parseNode(nodeInsured, PATH_ID));//公司内部序号
            logger.debug("-----> GrpPolParser-588-被保人公司内部序号为:"+tLCInsuredSchema.getSequenceNo());
            //***********************
			// 中英,通过身份证算生日性别
			if ("0".equals(StrTool.cTrim(tLCInsuredSchema.getIDType()))) {
				String rno = tLCInsuredSchema.getIDNo().trim();
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
				tLCInsuredSchema.setBirthday(tempbirthday);
				tLCInsuredSchema.setSex(tempsex);
			}
			if (tLCInsuredSchema.getBirthday() == null) {
				FDate fdate = new FDate();
				tLCInsuredSchema.setBirthday(fdate.getDate("1900-01-01"));
			}
			// 增加电话号码与邮件，作为客户信息准备

			tLCInsuredSet.add(tLCInsuredSchema);
		}

		return tLCInsuredSet;
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

		int nLength = nodeList == null ? 0 : nodeList.getLength();
		LCBnfSet tLCBnfSet = new LCBnfSet();

		for (int nIndex = 0; nIndex < nLength; nIndex++) {
			Node nodeBnf = nodeList.item(nIndex);
			LCBnfSchema tLCBnfSchema = new LCBnfSchema();

			tLCBnfSchema.setBnfType(parseNode(nodeBnf, PATH_BnfType));
			// 被保险人序号
			tLCBnfSchema.setInsuredNo(parseNode(nodeBnf, PATH_INSUREDID));
			tLCBnfSchema.setContNo(parseNode(nodeBnf,
					GrpPolParser.PATH_BNF_ContId));
			// 借用 PolNo 缓存 险种代码
			tLCBnfSchema.setPolNo(parseNode(nodeBnf, PATH_BNF_RiskCode));
			tLCBnfSchema.setName(parseNode(nodeBnf, PATH_BnfName));
			tLCBnfSchema.setSex(parseNode(nodeBnf, PATH_BnfSex));
			tLCBnfSchema.setIDType(parseNode(nodeBnf, PATH_BnfIDType));
			if (parseNode(nodeBnf, PATH_BnfIDType).equals("0")) {
				tLCBnfSchema.setIDNo(parseNode(nodeBnf, PATH_BnfIDNo)
						.toUpperCase());
			} else {
				tLCBnfSchema.setIDNo(parseNode(nodeBnf, PATH_BnfIDNo));
			}

			tLCBnfSchema.setBirthday(parseNode(nodeBnf, PATH_BnfBirthday));
			tLCBnfSchema.setRelationToInsured(parseNode(nodeBnf,
					PATH_RelationToInsured));
			tLCBnfSchema.setBnfLot(parseNode(nodeBnf, PATH_BnfLot));
			tLCBnfSchema.setBnfGrade(parseNode(nodeBnf, PATH_BnfGrade));
			// 借用BnfNo 缓存 险种ID
			//tLCBnfSchema.setBnfNo(parseNode(nodeBnf, PATH_ID));
			tLCBnfSchema.setBnfNo(parseNode(nodeBnf, PATH_BNF_ContId));
			tLCBnfSet.add(tLCBnfSchema);
		}

		return tLCBnfSet;
	}
}
