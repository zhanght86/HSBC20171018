package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolOtherSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolOtherSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class GrpCarParser {
private static Logger logger = Logger.getLogger(GrpCarParser.class);
	private String PATH_ROW = "ROW";
	// 车辆信息
	private static String PATH_CAR = "ROW";
	private static String PATH_CARNO = "CarNo";
	private static String PATH_SHELFNO = "ShelfNo";
	private static String PATH_SEATCOUNT = "SeatCount";
	private static String PATH_TRAFFICTYPE = "TrafficType";

	// 下面是一些常量定义
	private static String PATH_ID = "ID";

	private static String PATH_POL = "ROW";
	private static String PATH_RISKCODE = "RiskCode";
	private static String PATH_PREM = "Prem";
	private static String PATH_AMNT = "Amnt";
	private static String PATH_MAINRISKCODE = "MainRiskCode";
	private static String PATH_MULT = "Mult";
	private static String PATH_REMARK = "Remark";

	public CErrors mErrors = new CErrors();

	public GrpCarParser() {
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
			nodeList = XPathAPI.selectNodeList(node, PATH_CAR);
		} catch (Exception ex) {
			ex.printStackTrace();
			nodeList = null;
			return null;
		}

		int nLength = nodeList == null ? 0 : nodeList.getLength();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		for (int nIndex = 0; nIndex < nLength; nIndex++) {
			Node nodeCar = nodeList.item(nIndex);
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			// 合同序号,依靠这个序号标示被保险人是否同在一个合同中
			tLCInsuredSchema
					.setContNo(parseNode(nodeCar, GrpCarParser.PATH_ID));
			tLCInsuredSchema.setPrtNo(parseNode(nodeCar, GrpCarParser.PATH_ID));
			tLCInsuredSchema.setName("无名单");
			tLCInsuredSchema.setRelationToMainInsured("00");
			tLCInsuredSchema.setInsuredPeoples(parseNode(nodeCar,
					GrpCarParser.PATH_SEATCOUNT));
			tLCInsuredSchema.setBirthday("1979-01-01");
			tLCInsuredSchema.setOccupationType("1");
			tLCInsuredSet.add(tLCInsuredSchema);
		}

		return tLCInsuredSet; // 可以直接写入数据库的 add by yaory
	}

	/**
	 * 得到车辆信息
	 * 
	 * @param node
	 *            Node
	 * @return LCInsuredSet
	 */
	public LCPolOtherSet getLCPolOtherSet(Node node) {
		NodeList nodeList = null;

		try {
			nodeList = XPathAPI.selectNodeList(node, PATH_CAR);
		} catch (Exception ex) {
			ex.printStackTrace();
			nodeList = null;
			return null;
		}

		int nLength = nodeList == null ? 0 : nodeList.getLength();
		LCPolOtherSet tLCPolOtherSet = new LCPolOtherSet();
		for (int tIndex = 0; tIndex < nLength; tIndex++) {
			Node nodeCar = nodeList.item(0);
			LCPolOtherSchema tLCPolOtherSchema = new LCPolOtherSchema();
			// 合同序号,依靠这个序号标示被保险人是否同在一个合同中
			tLCPolOtherSchema
					.setContNo(parseNode(nodeCar, GrpCarParser.PATH_ID));
			tLCPolOtherSchema
					.setP1(parseNode(nodeCar, GrpCarParser.PATH_CARNO));
			tLCPolOtherSchema.setP2(parseNode(nodeCar,
					GrpCarParser.PATH_SHELFNO));
			tLCPolOtherSchema.setP3(parseNode(nodeCar,
					GrpCarParser.PATH_SEATCOUNT));
			tLCPolOtherSchema.setP4(parseNode(nodeCar,
					GrpCarParser.PATH_TRAFFICTYPE));
			tLCPolOtherSet.add(tLCPolOtherSchema);
		}
		return tLCPolOtherSet;
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

			// 返回数据
			vReturn.add(tTransferData);
			vReturn.add(tLCPolSchema);

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
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

		tLCPolSchema.setContNo(parseNode(node, PATH_ID));
		tLCPolSchema.setRiskCode(parseNode(node, PATH_RISKCODE));
		tLCPolSchema.setInsuredNo(parseNode(node, PATH_ID));

		tLCPolSchema.setMainPolNo(parseNode(node, PATH_MAINRISKCODE));
		logger.debug("主险编码======" + tLCPolSchema.getMainPolNo() + "-"
				+ parseNode(node, PATH_MAINRISKCODE));

		tLCPolSchema.setMult(parseNode(node, PATH_MULT));
		tLCPolSchema.setPrem(parseNode(node, PATH_PREM));
		tLCPolSchema.setAmnt(parseNode(node, PATH_AMNT));
		tLCPolSchema.setRemark(parseNode(node, PATH_REMARK));

		return tLCPolSchema;
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
		// tTransferData.setNameAndValue("GetDutyKind",
		// parseNode(node, PATH_GetDutyKind));
		// tTransferData.setNameAndValue("GetIntv", parseNode(node,
		// PATH_GetIntv));
		tTransferData.setNameAndValue("ID", parseNode(node, PATH_ID) + "-"
				+ parseNode(node, PATH_RISKCODE));
		// tTransferData.setNameAndValue("SavePolType",
		// parseNode(node, PATH_SavePolType));

		return tTransferData;
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

		cError.moduleName = "GrpCarParser";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		GrpCarParser grpcarparser = new GrpCarParser();
	}
}
