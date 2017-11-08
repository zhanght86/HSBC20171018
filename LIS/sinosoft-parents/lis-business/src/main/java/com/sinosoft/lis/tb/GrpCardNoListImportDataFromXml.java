package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LACommisionDetailSchema;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * ClassName: GrpCardNoListImportDataFromXml
 * </p>
 * <p>
 * Description: 用于无名单卡单导入时此类将一个包含几个保单信息的xml文件转换成对应的schema ,set等信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author:
 * @version: 1.0
 * @date: 2007-04-01
 */
public class GrpCardNoListImportDataFromXml {
private static Logger logger = Logger.getLogger(GrpCardNoListImportDataFromXml.class);
	public GrpCardNoListImportDataFromXml() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 一次导入的所有卡单的集合
	private List cardList;
	// 目前所得到的结点在cardList中的位置
	private int curIndex = -1;
	/***************************************************************************
	 * 每次得到一张保单的全部数据 (包括
	 * 投保人的LDPreson,投保人的LCAddress,LCCont,LCAppnt,,LCInsured,LCPol,LCBnf)
	 */
	private VData oneCard;

	TransferData mTransferData = new TransferData();
	private Element curCardElement;
	private boolean ISPlanRisk = false;
	private String mMult = "";

	/***************************************************************************
	 * 
	 **************************************************************************/
	public GrpCardNoListImportDataFromXml(String fileName) throws Exception {
		File file = new File(fileName);
		if (!file.exists()) {
			throw new Exception("解析后的文件不存在!");
		}
		try {
			DOMBuilder db = new DOMBuilder();
			Document doc = db.build(new FileInputStream(fileName));
			Element eleRoot = doc.getRootElement();
			cardList = eleRoot.getChildren("ROW");
		} catch (Exception ex) {
			throw new Exception(
					"GrpCardNoListImportDataFromXml-->GrpCardNoListImportDataFromXml()"
							+ ex.getMessage());
		}
	}

	/** 一次性获取所有导入保单集合* */
	public List getCardList() {
		return cardList;
	}

	/** 一次性获取所有导入保单的数量* */
	public int getCardListSize() {
		return cardList.size();
	}

	/***************************************************************************
	 * 每次解析cardList中的一个包含一张卡单的接点,把投保人的LDPresonSchema,投保人的LCAddressSchema
	 * LCContSchema,LCAppntSchema,,LCInsuredSet,LCPolSet,LCBnfSet放到一个VData中返回
	 **************************************************************************/
	public VData genOneCard(int tCurIndex) {
		// 先把this.oneCard置为null
		this.oneCard = new VData();
		mMult = "";
		ISPlanRisk = false;
		// 取到末尾，返回null值
		if (tCurIndex >= cardList.size() || cardList.get(tCurIndex) == null) {
			return null;
		}
		Element curCardElement = (Element) this.cardList.get(tCurIndex);

		// 获取保单信息
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setSchema(getOneLCCont(curCardElement));
		oneCard.add(tLCContSchema);

		// 获取投保人客户表信息
		LDPersonSchema personSchema = new LDPersonSchema();
		personSchema.setSchema(getOneLDPerson(curCardElement));
		oneCard.add(personSchema);

		// 获取投保人客户地址信息表
		LCAddressSchema addressSchema = new LCAddressSchema();
		addressSchema.setSchema(getOneLCAddress(curCardElement));
		oneCard.add(addressSchema);
		// 获取投保人表
		LCAppntSchema appntSchema = new LCAppntSchema();
		appntSchema.setSchema(getOneLCAppnt(curCardElement));
		oneCard.add(appntSchema);

		// 获取被保人，因为被保人可能是多人，所以获得set集合
		LCInsuredSchema insuredSchema = new LCInsuredSchema();
		insuredSchema.setSchema(getOneLCInsured(curCardElement));
		oneCard.add(insuredSchema);
		// 获取险种信息
		LCPolSchema polSchema = new LCPolSchema();
		polSchema.setSchema(getLCPolSchema(curCardElement));
		oneCard.add(polSchema);

		LCAccountSchema accountSchema = new LCAccountSchema();
		oneCard.add(accountSchema);

		LCCustomerImpartSet customerImpartSet = new LCCustomerImpartSet();
		oneCard.add(customerImpartSet);

		LACommisionDetailSet commisionDetailSet = new LACommisionDetailSet();
		commisionDetailSet.set(getLACommisionDetailSet(curCardElement));
		oneCard.add(commisionDetailSet);
		mTransferData.removeByName("ContNo");
		mTransferData.setNameAndValue("ContNo", tLCContSchema.getContNo());
		mTransferData.setNameAndValue("SavePolType", "0"); // 保全保存标记，默认为0，标识非保全
		mTransferData.setNameAndValue("GrpNo", "00000000000000000000");
		mTransferData.setNameAndValue("GrpName", addressSchema.getGrpName());
		mTransferData.setNameAndValue("FamilyType", "0"); // 家庭单标记
		mTransferData.setNameAndValue("ContType", "2"); // 团单，个人单标记
		mTransferData.setNameAndValue("PolTypeFlag", "1"); // 无名单标记 //无名单标记
		// 0--个人单，1--无名单，2
		// ---公共帐户
		mTransferData.setNameAndValue("InsuredPeoples", "0"); // 被保险人人数
		mTransferData.setNameAndValue("InsuredAppAge", ""); // 被保险人年龄
		mTransferData.setNameAndValue("SequenceNo", "00"); // 内部客户号<>
		mTransferData.setNameAndValue("GetDutyKind", null);
		mTransferData.setNameAndValue("samePersonFlag", "1"); // 投保人同被保人标志
		mTransferData.setNameAndValue("deleteAccNo", "1");
		mTransferData.setNameAndValue("ChangePlanFlag", "1");
		mTransferData.removeByName("Mult");
		mTransferData.setNameAndValue("Mult", mMult); // 套餐险种
		mTransferData.setNameAndValue("mark", "card");
		if (!ISPlanRisk) {
			mTransferData.removeByName("ISPlanRisk");
			mTransferData.setNameAndValue("ISPlanRisk", "N");
		} else {
			mTransferData.removeByName("ISPlanRisk");
			mTransferData.setNameAndValue("ISPlanRisk", "Y");
		}
		oneCard.add(mTransferData);

		return oneCard;
	}

	// 填充oneCard中所需的LACommisionDetailSet
	private static LACommisionDetailSet getLACommisionDetailSet(Element tElement) {
		LACommisionDetailSet commisionDetailSet = new LACommisionDetailSet();
		LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		tLACommisionDetailSchema.setGrpContNo(contElement
				.getChildText("CardNo"));
		tLACommisionDetailSchema.setAgentCode(contElement
				.getChildText("AgentCode"));
		String tagentcode = tLACommisionDetailSchema.getAgentCode();

		String strSQL = "select agentgroup from LAAgent where agentcode='"
				+ tagentcode + "'";
		ExeSQL exeSQL = new ExeSQL();
		// SSRS ssrs = exeSQL.execSQL(strSQL);
		String ss = exeSQL.getOneValue(strSQL);
		if (ss != null) {
			tLACommisionDetailSchema.setAgentGroup(ss);
		}

		// 情况一：交叉销售个险业务员佣金分配比率为1，相关的团体业务员后续处理；
		// 情况二：直团销售则团体业务员佣金分配比率为1
		tLACommisionDetailSchema.setBusiRate(1);
		tLACommisionDetailSchema.setPolType("0"); // 0-正常单 1-孤儿单
		commisionDetailSet.add(tLACommisionDetailSchema);
		return commisionDetailSet;
	}

	// 填充oneCard中所需的LCContSchema
	private static LCContSchema getOneLCCont(Element tElement) {
		LCContSchema contSchema = new LCContSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		// 卡单标志
		contSchema.setGrpContNo("00000000000000000000");
		contSchema.setCardFlag("3");
		contSchema.setPrtNo(contElement.getChildText("CardNo"));
		contSchema.setContNo(contElement.getChildText("CardNo"));
		contSchema.setProposalContNo(contElement.getChildText("CardNo"));
		contSchema.setAgentCode(contElement.getChildText("AgentCode"));
		// contSchema.setAgentGroup("010000001041");
		contSchema.setSellType("01"); // 销售方式：业务员
		contSchema.setContType("2"); // 团单
		contSchema.setPolType("1"); // 无名单
		contSchema.setForceUWFlag("0"); // 不强制人工核保
		contSchema.setForceUWReason(contElement.getChildText("CardNo"));
		// contElement.getChildText("CardNo") 保险凭证号码，目前不知道存哪
		String tPolApplyDate = contElement.getChildText("PolApplyDate");
		if (tPolApplyDate == null || tPolApplyDate.equals("")) {
			contSchema.setPolApplyDate(PubFun.getCurrentDate()); // 生效日初始化成投保日
		} else {
			contSchema.setPolApplyDate(tPolApplyDate); // 生效日初始化成投保日
		}
		// contElement.getChildText("NeedActived")是否需要激活，目前不知道存哪
		contSchema.setAppntName(contElement.getChildText("无名单"));
		return contSchema;
	}

	// 填充oneCard中所需的LDPersonSchema
	private static LDPersonSchema getOneLDPerson(Element tElement) {
		LDPersonSchema personSchema = new LDPersonSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		personSchema.setName(contElement.getChildText("无名单"));
		return personSchema;

	}

	// 填充oneCard中所需的LCAddressSchema
	private static LCAddressSchema getOneLCAddress(Element tElement) {
		LCAddressSchema addressSchema = new LCAddressSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		return addressSchema;
	}

	// 填充oneCard中所需的LCAppntSchema
	private static LCAppntSchema getOneLCAppnt(Element tElement) {
		LCAppntSchema appntSchema = new LCAppntSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		appntSchema.setContNo(contElement.getChildText("CardNo"));
		appntSchema.setPrtNo(contElement.getChildText("CardNo"));
		appntSchema.setAppntName(contElement.getChildText("无名单"));
		return appntSchema;
	}

	// 填充oneCard中所需的LCInsuredSchema
	private LCInsuredSchema getOneLCInsured(Element tElement) {
		LCInsuredSchema insuredSchema = new LCInsuredSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		insuredSchema.setContNo(contElement.getChildText("CardNo"));
		insuredSchema.setPrtNo(contElement.getChildText("CardNo"));
		insuredSchema.setInsuredStat(contElement.getChildText("InsuredStat"));// 激活标志
		// insuredSchema.setInsuredStat("1");//激活标志
		insuredSchema.setContPlanCode(contElement.getChildText("ContPlanCode"));
		insuredSchema.setRelationToMainInsured("00");
		if (insuredSchema.getContPlanCode().equals("")
				|| insuredSchema.getContPlanCode() == null) {
			ISPlanRisk = false;
		} else {
			ISPlanRisk = true;
			mMult = contElement.getChildText("Mult"); // 套餐险种
		}
		return insuredSchema;
	}

	// 填充oneCard中所需的LCPolSet
	private LCPolSchema getLCPolSchema(Element tElement) {
		LCPolSchema polSchema = new LCPolSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		polSchema.setGrpContNo("000000000000000000");
		polSchema.setContNo(contElement.getChildText("CardNo"));
		polSchema.setPrtNo(contElement.getChildText("CardNo"));
		polSchema.setProposalNo(contElement.getChildText("CardNo"));
		polSchema.setSaleChnl("2");
		polSchema.setAgentCode(contElement.getChildText("AgentCode"));
		// polSchema.setAgentGroup("010000001041");
		polSchema.setOccupationType("1");
		polSchema.setInsuredPeoples("1"); // 被保人人数
		polSchema.setPolTypeFlag("1"); // 保单类型标记
		// polSchema.setMainPolNo(contElement.getChildText("MainRiskCode"));
		polSchema.setPrtNo(contElement.getChildText("CardNo"));
		polSchema.setAppntName(contElement.getChildText("无名单"));

		String PolApplyDate = contElement.getChildText("PolApplyDate");
		if (PolApplyDate == null || PolApplyDate.equals("")) {
			polSchema.setCValiDate(PubFun.getCurrentDate()); // 生效日初始化成投保日
			polSchema.setPolApplyDate(PubFun.getCurrentDate());
		} else {
			polSchema.setCValiDate(PolApplyDate); // 生效日初始化成投保日
			polSchema.setPolApplyDate(PolApplyDate);
		}
		String sPolApplyDate = polSchema.getCValiDate();
		logger.debug("投保日期=" + sPolApplyDate);
		polSchema.setRnewFlag("-1");
		polSchema.setSpecifyValiDate("N");
		polSchema.setRiskCode(contElement.getChildText("RiskCode"));
		polSchema.setMult(contElement.getChildText("Mult"));
		polSchema.setPrem(contElement.getChildText("Prem"));
		polSchema.setAmnt(contElement.getChildText("Amnt"));
		return polSchema;
	}

	private void jbInit() throws Exception {
	}

}
