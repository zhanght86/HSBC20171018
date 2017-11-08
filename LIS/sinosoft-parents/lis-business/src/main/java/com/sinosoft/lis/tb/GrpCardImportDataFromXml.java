package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;

import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * ClassName: GrpCardImportDataFromXml
 * </p>
 * <p>
 * Description: 用于卡单导入时此类将一个包含几个保单信息的xml文件转换成对应的schema ,set等信息
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
public class GrpCardImportDataFromXml {
private static Logger logger = Logger.getLogger(GrpCardImportDataFromXml.class);
	// 一次导入的所有卡单的集合
	private List cardList;
	// 目前所得到的结点在cardList中的位置
	private int curIndex = -1;
	/***************************************************************************
	 * 每次得到一张保单的全部数据 (包括
	 * 投保人的LDPreson,投保人的LCAddress,LCCont,LCAppnt,,LCInsured,LCPol,LCBnf)
	 */
	private VData oneCard;
	private TransferData mTransferData = new TransferData();
	// 当前操作的卡单的element
	private Element curCardElement;

	/***************************************************************************
	 * 
	 **************************************************************************/
	public GrpCardImportDataFromXml(String fileName) throws Exception {
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
					"GrpCardImportDataFromXml-->GrpCardImportDataFromXml()"
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
		// LDPersonSchema personSchema = new LDPersonSchema();
		// personSchema.setSchema(getOneLDPerson(curCardElement));
		// oneCard.add(personSchema);

		// 获取投保人客户地址信息表
		LCAddressSchema addressSchema = new LCAddressSchema();
		addressSchema.setSchema(getOneLCAddress(curCardElement));
		oneCard.add(addressSchema);
		// 获取投保人表
		LCAppntSchema appntSchema = new LCAppntSchema();
		appntSchema.setSchema(getOneLCAppnt(curCardElement));
		oneCard.add(appntSchema);

		// 获取被保人，因为被保人可能是多人，所以获得set集合
		LCInsuredSet insuredSet = new LCInsuredSet();
		insuredSet.set(getLCInsuredSet(curCardElement));
		oneCard.add(insuredSet);
		// 获取险种信息
		LCPolSet polSet = new LCPolSet();
		polSet.set(getLCPolSet(curCardElement));
		oneCard.add(polSet);
		// 获取受益人信息
		LCBnfSet bnfSet = new LCBnfSet();
		bnfSet.set(getLCBnfSet(curCardElement));
		oneCard.add(bnfSet);

		mTransferData = new TransferData();
		mTransferData.setNameAndValue("ContNo", tLCContSchema.getContNo());
		mTransferData.setNameAndValue("SavePolType", "0"); // 保全保存标记，默认为0，标识非保全
		mTransferData.setNameAndValue("GrpNo", "");
		mTransferData.setNameAndValue("GrpName", addressSchema.getGrpName());
		mTransferData.setNameAndValue("FamilyType", "0"); // 家庭单标记
		mTransferData.setNameAndValue("ContType", "2"); // 团单，个人单标记
		mTransferData.setNameAndValue("PolTypeFlag", "1"); // 无名单标记
		mTransferData.setNameAndValue("InsuredPeoples", "1"); // 被保险人人数
		mTransferData.setNameAndValue("InsuredAppAge", ""); // 被保险人年龄
		mTransferData.setNameAndValue("SequenceNo", ""); // 内部客户号<>
		mTransferData.setNameAndValue("GetDutyKind", null);
		mTransferData.setNameAndValue("samePersonFlag", "0"); // 投保人同被保人标志
		mTransferData.setNameAndValue("deleteAccNo", "1");
		mTransferData.setNameAndValue("ChangePlanFlag", "1");
		mTransferData.setNameAndValue("mark", "card");
		mTransferData.setNameAndValue("ISPlanRisk", "N"); // 套餐险种
		mTransferData.setNameAndValue("Mult", ""); // 套餐险种
		// 判断产品组合
		String strContPlan = getContPlan(insuredSet);
		if (strContPlan != null && strContPlan.indexOf("-") != -1) {
			String ContPlanCode = StrTool.cTrim(strContPlan.substring(0,
					strContPlan.indexOf("-")));
			String ContPlanMult = StrTool.cTrim(strContPlan.substring(
					strContPlan.indexOf("-") + 1, strContPlan.length()));
			if (!("").equals(ContPlanCode) && !("").equals(ContPlanMult)) {
				mTransferData.removeByName("ISPlanRisk");
				mTransferData.setNameAndValue("ISPlanRisk", "Y"); // 套餐险种
				mTransferData.removeByName("Mult");
				mTransferData.setNameAndValue("Mult", ContPlanMult); // 套餐险种
				mTransferData.removeByName("ContPlanCode");
				mTransferData.setNameAndValue("ContPlanCode", ContPlanCode); // 套餐险种
			}
		}
		oneCard.add(mTransferData);

		return oneCard;
	}

	// 填充oneCard中所需的LCContSchema
	private static LCContSchema getOneLCCont(Element tElement) {
		LCContSchema contSchema = new LCContSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		// 卡单标志
		contSchema.setCardFlag("3");
		contSchema.setPrtNo(contElement.getChildText("CardNo"));
		contSchema.setContNo(contElement.getChildText("CardNo"));
		contSchema.setProposalContNo(contElement.getChildText("CardNo"));
		contSchema.setAgentCode(contElement.getChildText("AgentCode"));
		// contSchema.setSaleChnl(contElement.getChildText("SaleChnl"));
		contSchema.setSellType(contElement.getChildText("SaleChnl"));
		contSchema.setPolApplyDate(contElement.getChildText("PolApplyDate"));
		// contElement.getChildText("NeedActived")是否需要激活，目前不知道存哪

		contSchema.setAppntName(contElement.getChildText("AppntName"));
		contSchema.setAppntSex(contElement.getChildText("AppntSex"));
		contSchema.setAppntBirthday(contElement.getChildText("AppntBirthday"));
		contSchema.setAppntIDType(contElement.getChildText("AppntIDType"));
		contSchema.setAppntIDNo(contElement.getChildText("AppntIDNo"));
		contSchema.setForceUWReason(contElement.getChildText("ContNo"));// 存储保单号
		return contSchema;
	}

	// 填充oneCard中所需的LDPersonSchema
	private static LDPersonSchema getOneLDPerson(Element tElement) {
		LDPersonSchema personSchema = new LDPersonSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		personSchema.setName(contElement.getChildText("AppntName"));
		personSchema.setSex(contElement.getChildText("AppntSex"));
		personSchema.setBirthday(contElement.getChildText("AppntBirthday"));
		personSchema.setIDType(contElement.getChildText("AppntIDType"));
		personSchema.setIDNo(contElement.getChildText("AppntIDNo"));
		personSchema.setGrpName(contElement.getChildText("WorkCompany"));
		personSchema.setOccupationType(contElement
				.getChildText("OccupationType"));
		personSchema.setOccupationCode(contElement
				.getChildText("OccupationCode"));

		return personSchema;

	}

	// 填充oneCard中所需的LCAddressSchema
	private static LCAddressSchema getOneLCAddress(Element tElement) {
		LCAddressSchema addressSchema = new LCAddressSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		addressSchema.setGrpName(contElement.getChildText("WorkCompany"));
		addressSchema.setPostalAddress(contElement
				.getChildText("PostalAddress"));
		addressSchema.setZipCode(contElement.getChildText("ZipCode"));
		addressSchema.setPhone(contElement.getChildText("Phone"));
		return addressSchema;
	}

	// 填充oneCard中所需的LCAppntSchema
	private static LCAppntSchema getOneLCAppnt(Element tElement) {
		LCAppntSchema appntSchema = new LCAppntSchema();
		Element contElement = tElement.getChild("CONTTABLE");
		appntSchema.setContNo(contElement.getChildText("CardNo"));
		appntSchema.setPrtNo(contElement.getChildText("CardNo"));
		appntSchema.setAppntName(contElement.getChildText("AppntName"));
		appntSchema.setAppntSex(contElement.getChildText("AppntSex"));
		appntSchema.setAppntBirthday(contElement.getChildText("AppntBirthday"));
		appntSchema.setIDType(contElement.getChildText("AppntIDType"));
		appntSchema.setIDNo(contElement.getChildText("AppntIDNo"));
		appntSchema.setOccupationType(contElement
				.getChildText("OccupationType"));
		appntSchema.setOccupationCode(contElement
				.getChildText("OccupationCode"));
		return appntSchema;
	}

	// 填充oneCard中所需的LCInsuredSet
	private static LCInsuredSet getLCInsuredSet(Element tElement) {

		// 先取得激活标志
		Element ttContElement = tElement.getChild("CONTTABLE");
		String needActived = ttContElement.getChildText("NeedActived");

		LCInsuredSet insuredSet = new LCInsuredSet();
		Element insuredSetElement = tElement.getChild("INSUREDTABLE");

		List insuredList = insuredSetElement.getChildren("ROW");
		int insuredNum = insuredList.size();
		for (int i = 0; i < insuredNum; i++) {
			Element oneInsured = (Element) insuredList.get(i);
			insuredSet.add(getOneInsured(oneInsured, needActived));
		}
		return insuredSet;
	}

	// 获取产品组合代码,及份数
	private static String getContPlan(LCInsuredSet tLCInsuredSet) {
		String strContPlan = null;
		if (tLCInsuredSet == null || tLCInsuredSet.size() == 0) {
			return null;
		}
		for (int i = 1; i <= tLCInsuredSet.size(); i++) {
			// 把主被保人放到数据包里,准备进行计算
			if (("1").equals(tLCInsuredSet.get(i).getSequenceNo())
					&& ("00").equals(tLCInsuredSet.get(i)
							.getRelationToMainInsured())) {
				if (tLCInsuredSet.get(i).getContPlanCode() != null) {
					strContPlan = tLCInsuredSet.get(i).getContPlanCode().trim();
				}
			}
		}
		return strContPlan;
	}

	// 由一个结点得到一个被保人的LCInsuredSchema
	private static LCInsuredSchema getOneInsured(Element oneInsured,
			String needActived) {
		LCInsuredSchema insured = new LCInsuredSchema();

		// 保存序号,等待确认
		insured.setContNo(oneInsured.getChildText("CardNo"));
		insured.setPrtNo(oneInsured.getChildText("CardNo"));
		insured.setSequenceNo(oneInsured.getChildText("SequenceNo"));
		insured.setRelationToAppnt(oneInsured.getChildText("RelationToAppnt"));
		insured.setRelationToMainInsured(oneInsured
				.getChildText("RelationToMainInsured"));
		insured.setName(oneInsured.getChildText("InsuredName"));
		insured.setSex(oneInsured.getChildText("InsuredSex"));
		insured.setBirthday(oneInsured.getChildText("InsuredBirthday"));
		insured.setIDType(oneInsured.getChildText("InsuredIDType"));
		insured.setIDNo(oneInsured.getChildText("InsuredIDNo"));
		insured.setInsuredStat(needActived);
		// 将目前产品组合 代码份数组合在一起，用“#”，有 insured表ContPlanCode存储，在后面处理拆开
		if (!oneInsured.getChildText("ContPlanCodeMult").trim().equals("")
				&& !oneInsured.getChildText("ContPlanCode").trim().equals("")) {
			// insured.setContPlanCode(oneInsured.getChildText("ContPlanCode"));
			String strContPlan = oneInsured.getChildText("ContPlanCode") + "-"
					+ oneInsured.getChildText("ContPlanCodeMult");
			insured.setContPlanCode(strContPlan);
		}
		insured.setOccupationType(oneInsured
				.getChildText("InsuredOccupationType"));
		insured.setOccupationCode(oneInsured
				.getChildText("InsuredOccupationCode"));
		return insured;
	}

	// 填充oneCard中所需的LCPolSet
	private static LCPolSet getLCPolSet(Element tElement) {
		LCPolSet polSet = new LCPolSet();
		Element polSetElement = tElement.getChild("LCPOLTABLE");
		List polList = polSetElement.getChildren("ROW");
		int polNum = polList.size();
		for (int i = 0; i < polNum; i++) {
			Element onePol = (Element) polList.get(i);
			polSet.add(getOnePol(onePol));
		}
		return polSet;
	}

	private static LCPolSchema getOnePol(Element onePol) {
		LCPolSchema pol = new LCPolSchema();

		pol.setPrtNo(onePol.getChildText("CardNo"));
		pol.setContNo(onePol.getChildText("CardNo"));
		pol.setRiskCode(onePol.getChildText("RiskCode"));
		// 主险编码,暂时存在 mainpolno中,在后面处理用用生成的 mainpolno替换
		pol.setMainPolNo(onePol.getChildText("MainRiskCode"));
		pol.setMult(onePol.getChildText("Mult"));
		pol.setPrem(onePol.getChildText("Prem"));
		pol.setAmnt(onePol.getChildText("Amnt"));
		pol.setRemark(onePol.getChildText("Remark"));

		return pol;
	}

	// 填充oneCard中所需的LCBnfSet
	private static LCBnfSet getLCBnfSet(Element tElement) {
		LCBnfSet bnfSet = new LCBnfSet();
		Element bnfSetElement = tElement.getChild("BNFTABLE");
		List bnfList = bnfSetElement.getChildren("ROW");
		int bnfNum = bnfList.size();
		for (int i = 0; i < bnfNum; i++) {
			Element oneBnf = (Element) bnfList.get(i);
			bnfSet.add(getOneBnf(oneBnf));
		}
		return bnfSet;
	}

	private static LCBnfSchema getOneBnf(Element oneBnf) {
		LCBnfSchema bnf = new LCBnfSchema();
		bnf.setContNo(oneBnf.getChildText("CardNo"));
		bnf.setName(oneBnf.getChildText("BnfName"));
		bnf.setRelationToInsured(oneBnf.getChildText("RelationToInsured"));
		// 改字段为受益人所属被保人序号，后边用生成的 insuredno 替换
		bnf.setInsuredNo(oneBnf.getChildText("InsuredNo"));
		bnf.setSex(oneBnf.getChildText("BnfSex"));
		bnf.setIDType(oneBnf.getChildText("BnfIDType"));
		bnf.setIDNo(oneBnf.getChildText("BnfIDNo"));
		bnf.setBirthday(oneBnf.getChildText("BnfBirthday"));
		bnf.setBnfNo(oneBnf.getChildText("BnfNo"));
		bnf.setBnfGrade(oneBnf.getChildText("BnfNo"));
		bnf.setBnfLot(oneBnf.getChildText("BnfLot"));
		return bnf;
	}

}
