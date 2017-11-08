package com.sinosoft.lis.bq;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBContDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.db.LMEdorItemDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPEdorPrint3Schema;
import com.sinosoft.lis.schema.LPEdorPrintSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.vdb.LPAddressDBSet;
import com.sinosoft.lis.vdb.LPEdorItemDBSet;
import com.sinosoft.lis.vdb.LPInsuredDBSet;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LMCalModeSet;
import com.sinosoft.lis.vschema.LMEdorItemSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class PrtEndorsementBL {
	private static Logger logger = Logger.getLogger(PrtEndorsementBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例

	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 取得的保单号码
	@SuppressWarnings("unused")
	private String mPolNo = "";

	// 输入的查询sql语句
	@SuppressWarnings("unused")
	private String msql = "";

	// 批单号
	private String mEdorNo = "";

	private String mContNo = "";

	// 业务处理相关变量
	private TextTag textTag = new TextTag(); // 新建一个TextTag的实例

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private ListTable tlistTable;

	private MMap mMap = new MMap();

	public PrtEndorsementBL(String aEdorNo) {
		mEdorNo = aEdorNo;
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据，将数据备份到本类中
		logger.debug("start getinputdata");

		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("End getInputData");

		if (!this.dealData()) {
			return false;
		}

		logger.debug("End dealData");

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	@SuppressWarnings("unchecked")
	private boolean dealData() {
		logger.debug("\n\nStart Write Print Data\n\n");

		boolean tFlag = false;

		xmlexport.createDocument("PrtEndorsementApp.vts", "printer"); // 最好紧接着就初始化xml文档
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorNo(mEdorNo);
		LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
		tLPEdorMainSet.set(tLPEdorMainDB.query());
		if (tLPEdorMainSet.size() == 0) {
			buildError("dealData", "在LPEdorMain中无相关批单号的数据");
			return false;
		}
		LPEdorMainSchema tLPEdorMainSchema = tLPEdorMainSet.get(1);

		mContNo = tLPEdorMainSchema.getContNo(); // 取得保全记录的合同号

		// 查询个险保全项目表中批单号为mEdorNo的记录
		String sql = "select * from LPEdorItem where edorno='?mEdorNo?' order by MakeDate,MakeTime";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mEdorNo", mEdorNo);
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemDBSet();
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);
		if (tLPEdorItemSet.size() == 0) {
			buildError("dealData", "保全项目表中无相关批单号的数据");
			return false;
		}

		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			LBContDB tLBContDB = new LBContDB();
			tLBContDB.setContNo(mContNo);
			if (!tLBContDB.getInfo()) {
				return false;
			}
			Reflections aReflections = new Reflections();
			aReflections.transFields(tLCContSchema, tLBContDB.getSchema());
		} else {
			tLCContSchema.setSchema(tLCContDB.getSchema());
		}
		xmlexport.addDisplayControl("displayHead");
		textTag.add("EdorNo", tLPEdorMainSchema.getEdorNo());
		textTag.add("ContNo", tLPEdorMainSchema.getContNo());
		textTag.add("AppDate", tLPEdorMainSchema.getEdorAppDate());
		textTag.add("AppntName", tLCContSchema.getAppntName());

		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			// if (i == 1)
			// {
			// xmlexport.addDisplayControl("displayHead");
			// textTag.add("EdorNo", tLPEdorItemSchema.getEdorNo());
			// textTag.add("ContNo", tLPEdorItemSchema.getGrpContNo());
			// textTag.add("AppDate", tLPEdorItemSchema.getEdorAppDate());
			// textTag.add("AppntName", tLCContSchema.getAppntName());
			// }

			if (tLPEdorItemSchema.getEdorType().equals("BB")) { // 投保人变更
				xmlexport.addDisplayControl("displayBB");
				if (!this.getDetailBB(tLPEdorItemSchema)) {
					return false;
				}
				tFlag = true;
			} else if (tLPEdorItemSchema.getEdorType().equals("IC")) { // 被保人重要信息变更
				xmlexport.addDisplayControl("displayIC");
				if (!this.getDetailIC(tLPEdorItemSchema)) {
					return false;
				}
				tFlag = true;
			} else if (tLPEdorItemSchema.getEdorType().equals("LR")) { // 遗失补发
				xmlexport.addDisplayControl("displayLR");
				if (!this.getDetailLR(tLPEdorItemSchema)) {
					return false;
				}
				tFlag = true;
			} else if (tLPEdorItemSchema.getEdorType().equals("WT")) { // 被保人重要信息变更
				xmlexport.addDisplayControl("displayWT");
				if (!this.getDetailWT(tLPEdorItemSchema)) {
					return false;
				}
				tFlag = true;
			}

			else if (tLPEdorItemSchema.getEdorType().equals("CT")) { // 中止合同
				xmlexport.addDisplayControl("displayCT");
				if (!this.getDetailCT(tLPEdorItemSchema)) {
					return false;
				}
				tFlag = true;
			}

			else { // 对没有的保全项目类型生成空打印数据
				if (!this.getDetailForBlankType(tLPEdorItemSchema)) {
					return false;
				}
				tFlag = true;
			}
		}

		// 打印补退费明细表
		getPayGetDetails(tLPEdorItemSet);

		if (!tFlag) {
			buildError("dealData", "发生一个未知错误使主批单不能打印！");
			return false;
			// return true;
		}

		if (textTag.size() > 0) {
			xmlexport.addTextTag(textTag);
		}

		mResult.clear();

		// 生成主打印批单schema
		LPEdorPrintSchema tLPEdorPrintSchemaMain = new LPEdorPrintSchema();
		tLPEdorPrintSchemaMain.setEdorNo(mEdorNo);
		tLPEdorPrintSchemaMain.setManageCom(mGlobalInput.ManageCom);
		tLPEdorPrintSchemaMain.setPrtFlag("N");
		tLPEdorPrintSchemaMain.setPrtTimes(0);
		tLPEdorPrintSchemaMain.setMakeDate(PubFun.getCurrentDate());
		tLPEdorPrintSchemaMain.setMakeTime(PubFun.getCurrentTime());
		tLPEdorPrintSchemaMain.setOperator(mGlobalInput.Operator);
		tLPEdorPrintSchemaMain.setModifyDate(PubFun.getCurrentDate());
		tLPEdorPrintSchemaMain.setModifyTime(PubFun.getCurrentTime());
		InputStream ins = xmlexport.getInputStream();
		// xmlexport.outputDocumentToFile("c:\\", "xmlexport");
		tLPEdorPrintSchemaMain.setEdorInfo(ins);
		mMap.put(tLPEdorPrintSchemaMain, "BLOBINSERT");

		mResult.addElement(mMap);
		try {
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	private String getFeeFinaName(String strFeeFinaType) {
		logger.debug(strFeeFinaType);

		LDCode1DB tLDCode1DB = new LDCode1DB();
		tLDCode1DB.setCode(strFeeFinaType);

		LDCode1Set tLDCode1Set = tLDCode1DB.query();

		if ((tLDCode1Set != null) && (tLDCode1Set.size() > 0)) {
			return tLDCode1Set.get(1).getCodeAlias();
		} else {
			return strFeeFinaType;
		}
	}

	private boolean getPayGetDetails(LPEdorItemSet tLPEdorItemSet) {
		logger.debug("start getPayGetDetail...");

		ListTable listTable = new ListTable();
		boolean bIsExport = false;
		double grossGetMoney = 0.0;

		try {
			for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
				LPEdorItemSchema tLPEdorItemSchema = tLPEdorItemSet.get(i);
				LJSGetEndorseDB tLJSGetEndorsementDB = new LJSGetEndorseDB();

				String strSql1 = " select * from ljsgetendorse where endorsementno = '?endorsementno?' and FeeOperationType = '?FeeOperationType?' order by polno, feefinatype";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(strSql1);
				sqlbv1.put("endorsementno", tLPEdorItemSchema.getEdorNo());
				sqlbv1.put("FeeOperationType", tLPEdorItemSchema.getEdorType());
				logger.debug("strSql1 :" + strSql1);

				LJSGetEndorseSet tLJSGetEndorsementSet = tLJSGetEndorsementDB
						.executeQuery(sqlbv1);

				String[] strGetPayDetail = null;

				for (int j = 1; j <= tLJSGetEndorsementSet.size(); j++) {
					double getMoney = 0.0;
					double payMoney = 0.0;

					if (tLJSGetEndorsementSet.get(j).getGetFlag()
							.compareTo("0") == 0) {
						payMoney = tLJSGetEndorsementSet.get(j).getGetMoney();
						grossGetMoney -= payMoney;
					} else {
						getMoney = tLJSGetEndorsementSet.get(j).getGetMoney();
						grossGetMoney += getMoney;
					}

					if ((getMoney == 0.0) && (payMoney == 0.0)) {
						continue;
					}

					bIsExport = true;
					strGetPayDetail = new String[5];

					String strSql = " select * from lmriskapp where riskcode = '?riskcode?'";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(strSql);
					sqlbv2.put("riskcode", tLJSGetEndorsementSet.get(j).getRiskCode());
					LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
					String strRiskName = tLMRiskAppDB.executeQuery(sqlbv2).get(
							1).getRiskName();
					strGetPayDetail[0] = strRiskName;

					String edortype = tLJSGetEndorsementSet.get(j)
							.getFeeOperationType().trim();
					LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();

					tLMEdorItemDB.setEdorCode(edortype);
					LMEdorItemSet tItemSet = tLMEdorItemDB.query();
					String edorname = "unknow";
					if (tItemSet == null || tItemSet.size() == 0) {
						edorname = edortype;
					} else {
						edorname = tItemSet.get(1).getEdorName();
					}

					strGetPayDetail[1] = edorname;
					strGetPayDetail[2] = getFeeFinaName(tLJSGetEndorsementSet
							.get(j).getFeeFinaType());
					strGetPayDetail[3] = String.valueOf(payMoney);
					strGetPayDetail[4] = String.valueOf(getMoney);
					listTable.add(strGetPayDetail);
				}
			}
		} catch (Exception e) {
			return false;
		}

		if (!bIsExport) {
			return false;
		}

		// 设置列表名字和字段名
		String[] strGetPayHeadInfo = new String[5];
		strGetPayHeadInfo[0] = "RiskCode";
		strGetPayHeadInfo[1] = "EdorType";
		strGetPayHeadInfo[2] = "FeefinaType";
		strGetPayHeadInfo[3] = "PayMoney";
		strGetPayHeadInfo[4] = "GetMoney";
		listTable.setName("FeeDetail");

		xmlexport.addDisplayControl("displayFeeDetail");

		String strGetMoneyInfo;

		if (grossGetMoney > 0.0) {
			strGetMoneyInfo = "应付合计："
					+ new DecimalFormat("0.00").format(grossGetMoney); // String.valueOf(grossGetMoney)
		} else {
			strGetMoneyInfo = "应交合计："
					+ new DecimalFormat("0.00").format(-grossGetMoney); // String.valueOf(-grossGetMoney)
		}

		textTag.add("FeeSum", strGetMoneyInfo);
		xmlexport.addListTable(listTable, strGetPayHeadInfo);

		return true;
	}

	// 被保人基本信息变更
	private boolean getDetailBB(LPEdorItemSchema aLPEdorItemSchema) {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(aLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemDBSet();
		tLPEdorItemSet.set(tLPEdorItemDB.query());
		int n = tLPEdorItemSet.size();
		for (int i = 1; i <= n; i++) {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			// 取（保全个单被保人表）和（被保人表）的相关记录，生称相应schema
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
			tLCInsuredDB.setContNo(tLPEdorItemSchema.getContNo());
			if (!tLCInsuredDB.getInfo()) {
				buildError("getDetailBB", "LCInsured中无相关记录");

			} else {
				tLCInsuredSchema = tLCInsuredDB.getSchema();
			}
			LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();
			tLPInsuredDB.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPInsuredDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsuredDB.setContNo(tLPEdorItemSchema.getContNo());
			tLPInsuredDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
			LPInsuredSet tLPInsuredSet = new LPInsuredDBSet();
			tLPInsuredSet.set(tLPInsuredDB.query());
			if (tLPInsuredSet.size() == 0) {
				buildError("getDetailBB", "保全个单被保人表LPInsured中无相关客户号的纪录");
			} else {
				tLPInsuredSchema = tLPInsuredSet.get(1);
			}
			// 取（保全个单地址表）和（个单地址表）的相关记录，生称相应schema
			LCAddressSchema tLCAddressSchema = new LCAddressSchema();
			LCAddressDB tLCAddressDB = new LCAddressDB();
			tLCAddressDB.setCustomerNo(tLCInsuredSchema.getInsuredNo());
			tLCAddressDB.setAddressNo(tLCInsuredSchema.getAddressNo());
			if (!tLCAddressDB.getInfo()) {
				buildError("getDetailBB", "团体客户地址表LCGrpAddress中无相关集体合同号的记录");
			} else {
				tLCAddressSchema = tLCAddressDB.getSchema();
			}

			LPAddressSchema tLPAddressSchema = new LPAddressSchema();
			LPAddressDB tLPAddressDB = new LPAddressDB();
			tLPAddressDB.setCustomerNo(tLPEdorItemSchema.getInsuredNo());
			tLPAddressDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPAddressDB.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPAddressDB.setAddressNo(tLPInsuredSchema.getAddressNo());
			LPAddressSet tLPAddressSet = new LPAddressDBSet();
			tLPAddressSet.set(tLPAddressDB.query());
			if (tLPAddressSet.size() == 0) { // 可能修改后的地址用的是原来LCAddress中的地址，所以在LPGrpAddress中没有记录
				// 尝试从LCAddress中取出此地址记录
				tLCAddressDB = new LCAddressDB();
				tLCAddressDB.setCustomerNo(tLCInsuredSchema.getInsuredNo());
				tLCAddressDB.setAddressNo(tLCInsuredSchema.getAddressNo());
				if (!tLCAddressDB.getInfo()) {
					buildError("getDetailBB",
							"保全团体客户地址表LPGrpAddress和LCAddress中无相关集体合同号的记录");
				} else {
					tLCAddressSchema = tLCAddressDB.getSchema();
					Reflections aReflections = new Reflections();
					aReflections
							.transFields(tLPAddressSchema, tLCAddressSchema);
				}
			} else {
				tLPAddressSchema = tLPAddressSet.get(1);
			}

			String[] strArr = new String[3];
			tlistTable = new ListTable(); // 设置循环的listTable
			tlistTable.setName("BB");

			// 比较证件类型是否变化
			if (!StrTool.cTrim(tLCInsuredSchema.getIDType()).equals(
					StrTool.cTrim(tLPInsuredSchema.getIDType()))) {
				strArr[0] = "证件类型";
				strArr[1] = StrTool.cTrim(tLCInsuredSchema.getIDType());
				strArr[2] = StrTool.cTrim(tLPInsuredSchema.getIDType());
				tlistTable.add(strArr);
			}

			// 比较证件号码是否变化
			if (!StrTool.cTrim(tLCInsuredSchema.getIDNo()).equals(
					StrTool.cTrim(tLPInsuredSchema.getIDNo()))) {
				strArr[0] = "证件号码";
				strArr[1] = StrTool.cTrim(tLCInsuredSchema.getIDNo());
				strArr[2] = StrTool.cTrim(tLPInsuredSchema.getIDNo());
				tlistTable.add(strArr);
			}

			// 比较家庭地址是否变化
			if (!StrTool.cTrim(tLCAddressSchema.getHomeAddress()).equals(
					StrTool.cTrim(tLPAddressSchema.getHomeAddress()))) {
				strArr[0] = "家庭地址";
				strArr[1] = StrTool.cTrim(tLCAddressSchema.getHomeAddress());
				strArr[2] = StrTool.cTrim(tLPAddressSchema.getHomeAddress());
				tlistTable.add(strArr);
			}

			// 比较通讯地址是否变化
			if (!StrTool.cTrim(tLCAddressSchema.getPostalAddress()).equals(
					StrTool.cTrim(tLPAddressSchema.getPostalAddress()))) {
				strArr[0] = "通讯地址";
				strArr[1] = StrTool.cTrim(tLCAddressSchema.getPostalAddress());
				strArr[2] = StrTool.cTrim(tLPAddressSchema.getPostalAddress());
				tlistTable.add(strArr);
			}

			// 比较邮编是否变化
			if (!StrTool.cTrim(tLCAddressSchema.getZipCode()).equals(
					StrTool.cTrim(tLPAddressSchema.getZipCode()))) {
				strArr[0] = "邮编";
				strArr[1] = StrTool.cTrim(tLCAddressSchema.getZipCode());
				strArr[2] = StrTool.cTrim(tLPAddressSchema.getZipCode());
				tlistTable.add(strArr);
			}

			// 比较电话是否变化
			if (!StrTool.cTrim(tLCAddressSchema.getPhone()).equals(
					StrTool.cTrim(tLPAddressSchema.getPhone()))) {
				strArr[0] = "电话";
				strArr[1] = StrTool.cTrim(tLCAddressSchema.getPhone());
				strArr[2] = StrTool.cTrim(tLPAddressSchema.getPhone());
				tlistTable.add(strArr);
			}

			// 比较手机号是否变化
			if (!StrTool.cTrim(tLCAddressSchema.getMobile()).equals(
					StrTool.cTrim(tLPAddressSchema.getMobile()))) {
				strArr[0] = "手机号";
				strArr[1] = StrTool.cTrim(tLCAddressSchema.getMobile());
				strArr[2] = StrTool.cTrim(tLPAddressSchema.getMobile());
				tlistTable.add(strArr);
			}

			String[] strArr1 = new String[3];
			strArr1[0] = "变更项目";
			strArr1[1] = "变更前";
			strArr1[2] = "变更后";

			textTag.add("BB_EdorValiDate", aLPEdorItemSchema.getEdorValiDate());
			xmlexport.addListTable(tlistTable, strArr1);

		}
		return true;
	}

	// 保单遗失补发
	private boolean getDetailLR(LPEdorItemSchema aLPEdorItemSchema) {
		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(aLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError(new CError("相关的集体保单表中没有记录！"));
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		textTag.add("LR_ContNo", aLPEdorItemSchema.getContNo());
		textTag.add("LR_EdorValiDate", aLPEdorItemSchema.getEdorValiDate());
		textTag.add("LR_LostTimes", tLCContSchema.getLostTimes() + 1);
		textTag.add("LR_GetMoney", aLPEdorItemSchema.getGetMoney());

		return true;
	}

	// 被保险人重要资料变更
	private boolean getDetailWT(LPEdorItemSchema aLPEdorItemSchema) {
		textTag.add("WT_ContNo", aLPEdorItemSchema.getContNo());
		textTag.add("WT_EdorValiDate", aLPEdorItemSchema.getEdorValiDate());
		double getMoney = aLPEdorItemSchema.getGetMoney();
		if (getMoney > 0) {
			textTag.add("WT_GetMoney", "投保人共计缴纳保险费"
					+ aLPEdorItemSchema.getGetMoney() + "元");
		} else {
			textTag.add("WT_GetMoney", "共计退还投保人保险费"
					+ String.valueOf(-aLPEdorItemSchema.getGetMoney()) + "元");
		}
		return true;
	}

	// 团单合同终止
	private boolean getDetailCT(LPEdorItemSchema aLPEdorItemSchema) {
		textTag.add("CT_ContNo", aLPEdorItemSchema.getContNo());
		textTag.add("CT_EdorValiDate", aLPEdorItemSchema.getEdorValiDate());
		textTag.add("CT_GetMoney", aLPEdorItemSchema.getGetMoney());

		return true;
	}

	// 被保险人重要资料变更
	private boolean getDetailIC(LPEdorItemSchema aLPEdorItemSchema) {
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPInsuredDB.setEdorType(aLPEdorItemSchema.getEdorType());
		LPInsuredSet tLPInsuredSet = tLPInsuredDB.query();

		if (tLPInsuredSet != null || tLPInsuredSet.size() > 0) {

			tlistTable = new ListTable(); // 设置循环的listTable
			tlistTable.setName("IC");
			for (int i = 0; i < tLPInsuredSet.size(); i++) {
				String[] strArr = new String[6];
				strArr[0] = tLPInsuredSet.get(i + 1).getInsuredNo();
				strArr[1] = tLPInsuredSet.get(i + 1).getName();
				strArr[2] = tLPInsuredSet.get(i + 1).getSex();
				strArr[3] = tLPInsuredSet.get(i + 1).getBirthday();
				strArr[4] = tLPInsuredSet.get(i + 1).getIDType();
				strArr[5] = tLPInsuredSet.get(i + 1).getIDNo();
				tlistTable.add(strArr);

			}

			String[] strArr1 = new String[6];
			strArr1[0] = "被保险人客户号";
			strArr1[1] = "被保险人姓名";
			strArr1[2] = "性别";
			strArr1[3] = "出生日期";
			strArr1[4] = "证件类型";
			strArr1[5] = "证件号码";

			textTag.add("IC_EdorValiDate", aLPEdorItemSchema.getEdorValiDate());
			double getMoney = aLPEdorItemSchema.getGetMoney();
			if (getMoney > 0) {
				textTag.add("IC_GetMoney", "投保人共计缴纳保险费"
						+ aLPEdorItemSchema.getGetMoney() + "元");
			} else {
				textTag.add("IC_GetMoney", "共计退费"
						+ aLPEdorItemSchema.getGetMoney() + "元");
			}
			xmlexport.addListTable(tlistTable, strArr1);
		}

		return true;
	}

	/**
	 * 生成保全变更后的现金价值表
	 * 
	 * @param tLPEdorMainSchema
	 * @return
	 */
	@SuppressWarnings( { "unused", "unchecked" })
	private boolean setCashXml(LPEdorItemSchema tLPEdorItemSchema) {
		try {
			logger.debug("\n\nStart Set Cash Value");

			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("PrtEndorseCashValue.vts", "printer"); // 最好紧接着就初始化xml文档

			xmlexport.addDisplayControl("displayHead");

			TextTag textTag = new TextTag(); // 新建一个TextTag的实例
			textTag.add("EdorNo", tLPEdorItemSchema.getEdorNo());
			textTag.add("PolNo", tLPEdorItemSchema.getPolNo());
			textTag.add("AppDate", tLPEdorItemSchema.getEdorAppDate());
			textTag.add("InsuredNo", tLPEdorItemSchema.getInsuredNo());

			// textTag.add("AppntName",tLCPolSchema.getAppntName());
			textTag.add("ConfirmOperator", mGlobalInput.Operator);

			xmlexport.addDisplayControl("displayTail");

			xmlexport.addDisplayControl("displayIC");

			// 得到现金价值的算法描述
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPolDB.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPPolDB.setPolNo(tLPEdorItemSchema.getPolNo());

			if (!tLPPolDB.getInfo()) {
				buildError("setCashXml", "获取保全保单数据失败");
				return false;
			}

			LMCalModeDB tLMCalModeDB = new LMCalModeDB();
			tLMCalModeDB.setRiskCode(tLPPolDB.getRiskCode());
			tLMCalModeDB.setType("X");

			LMCalModeSet tLMCalModeSet = tLMCalModeDB.query();

			if (tLMCalModeSet.size() != 1) {
				buildError("setCashXml", "现金价值的算法描述有错");
				return false;
			}

			// 解析得到的SQL语句
			String strSQL = tLMCalModeSet.get(1).getCalSQL();

			// 这个险种不需要取现金价值的数据
			if (!strSQL.equals("")) {
				Calculator calculator = new Calculator();

				// 设置基本的计算参数
				calculator.addBasicFactor("InsuredSex", tLPPolDB
						.getInsuredSex());
				calculator.addBasicFactor("InsuredAppAge", String
						.valueOf(tLPPolDB.getInsuredAppAge()));
				calculator.addBasicFactor("PayIntv", String.valueOf(tLPPolDB
						.getPayIntv()));
				calculator.addBasicFactor("PayEndYear", String.valueOf(tLPPolDB
						.getPayEndYear()));
				calculator.addBasicFactor("PayEndYearFlag", String
						.valueOf(tLPPolDB.getPayEndYearFlag()));
				calculator.addBasicFactor("PayYears", String.valueOf(tLPPolDB
						.getPayYears()));
				calculator.addBasicFactor("InsuYear", String.valueOf(tLPPolDB
						.getInsuYear()));
				calculator.addBasicFactor("Prem", String.valueOf(tLPPolDB
						.getPrem()));
				calculator.addBasicFactor("Amnt", String.valueOf(tLPPolDB
						.getAmnt()));

				strSQL = PubFun1.getSQL(strSQL, calculator);
				logger.debug(strSQL);

				Connection conn = DBConnPool.getConnection();

				if (null == conn) {
					throw new Exception("连接数据库失败");
				}

				Statement stmt = null;
				ResultSet rs = null;

				// 设置List的名字，用于循环显示时定位，一定要与模板中一致
				ListTable tlistTable;

				try {
					stmt = conn.createStatement();

					// 获取最大记录数
					String strTemp = strSQL;
					strSQL = strSQL.toUpperCase();
					strSQL = "SELECT COUNT(*) "
							+ strSQL.substring(strSQL.indexOf("FROM"));

					rs = stmt.executeQuery(strSQL);
					rs.next();

					int totalCount = rs.getInt(1);
					logger.debug("totalCount: " + totalCount);

					int averageCount = ((totalCount - 3) / 5) + 1;
					int col = 0;
					int nCount = 1;
					String[] strArr = null;

					// 获取现价数据，并按照年度排序
					strSQL = strTemp + " order by curyear ";

					rs = stmt.executeQuery(strSQL);
					tlistTable = new ListTable();
					tlistTable.setName("IC0");

					while (rs.next()) {
						if (nCount == 1) {
							textTag.add("ICYear1", (new DecimalFormat("0.00"))
									.format(Double.valueOf(rs.getString(2)
											.trim())));
							nCount++;

							continue;
						} else if (nCount == 2) {
							textTag.add("ICYear2", (new DecimalFormat("0.00"))
									.format(Double.valueOf(rs.getString(2)
											.trim())));
							nCount++;

							continue;
						} else if (nCount == 3) {
							nCount++;

							continue;
						}

						strArr = new String[2];
						strArr[0] = rs.getString(1).trim();
						strArr[1] = (new DecimalFormat("0.00")).format(Double
								.valueOf(rs.getString(2).trim()));

						// 增加一行
						tlistTable.add(strArr);

						nCount++;

						if (((nCount - 3) > ((col + 1) * averageCount))
								&& (col < 4)) {
							col = col + 1;

							String[] strArrTitle = { "保险单年度末", "现金价值" };
							int blankLineNum = 20 - averageCount;

							for (int i = 0; i < blankLineNum; i++) {
								strArr = new String[2];
								strArr[0] = " ";
								strArr[1] = " ";

								// 增加一行
								tlistTable.add(strArr);
							}

							xmlexport.addListTable(tlistTable, strArrTitle);

							tlistTable = new ListTable();
							tlistTable.setName("IC" + col);
						}
					}

					String[] strArrTitle = { "保险单年度末", "现金价值" };
					xmlexport.addListTable(tlistTable, strArrTitle);
					logger.debug("nCount: " + nCount);

					// 加入现金价值记录的总的条数
					// xmlDataset.addDataObject(new XMLDataTag("CashValueCount",
					// nCount));
					rs.close();
					stmt.close();
					conn.close();
				} catch (Exception ex) {
					if (null != rs) {
						rs.close();
					}

					stmt.close();

					try {
						conn.close();
					} catch (Exception e) {
					}

				}

				// 显示险种名称
				LMRiskDB tLMRiskDB = new LMRiskDB();
				tLMRiskDB.setRiskCode(tLPPolDB.getRiskCode());

				if (!tLMRiskDB.getInfo()) {
					textTag.add("ICRiskName", tLMRiskDB.getRiskName());
				} else {
					textTag.add("ICRiskName", tLMRiskDB.getRiskName());

				}
				xmlexport.addTextTag(textTag);
				LPEdorPrint3Schema tLPEdorPrint3Schema = new LPEdorPrint3Schema();
				tLPEdorPrint3Schema.setEdorNo(mEdorNo);
				tLPEdorPrint3Schema.setEdorType("1");
				tLPEdorPrint3Schema.setManageCom(mGlobalInput.ManageCom);
				tLPEdorPrint3Schema.setPrtFlag("N");
				tLPEdorPrint3Schema.setPrtTimes(0);
				tLPEdorPrint3Schema.setMakeDate(PubFun.getCurrentDate());
				tLPEdorPrint3Schema.setMakeTime(PubFun.getCurrentTime());
				tLPEdorPrint3Schema.setOperator(mGlobalInput.Operator);
				tLPEdorPrint3Schema.setModifyDate(PubFun.getCurrentDate());
				tLPEdorPrint3Schema.setModifyTime(PubFun.getCurrentTime());
				InputStream ins = xmlexport.getInputStream();
				// xmlexport.outputDocumentToFile("c:\\", "CashValue");
				tLPEdorPrint3Schema.setEdorInfo(ins);
				mMap.put(tLPEdorPrint3Schema, "BLOBINSERT");

				mResult.addElement(mMap);
				ins.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorConfirmBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败! " + e.getMessage();
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	// 被保人资料变更的数据采集
	// private boolean getDetailBB(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
	// tLPInsuredSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPInsuredSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPInsuredSchema.setCustomerNo(aLPEdorMainSchema.getInsuredNo());
	// tLPInsuredSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPInsuredDB tLPInsuredDB = new LPInsuredDB();
	// tLPInsuredDB.setSchema(tLPInsuredSchema);
	//
	// if (!tLPInsuredDB.getInfo())
	// {
	// return false;
	// }
	//
	// tLPInsuredSchema = tLPInsuredDB.getSchema();
	//
	// LPInsuredBL tLPInsuredBL = new LPInsuredBL();
	// tLPInsuredBL.queryLastLPInsured(aLPEdorMainSchema, tLPInsuredSchema);
	//
	// // LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
	// // LCInsuredDB tLCInsuredDB = new LCInsuredDB();
	// // tLCInsuredDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// // tLCInsuredDB.setCustomerNo(aLPEdorMainSchema.getInsuredNo());
	// //
	// // if (!tLCInsuredDB.getInfo())
	// // {
	// // LBInsuredDB tLBInsuredDB = new LBInsuredDB();
	// // tLBInsuredDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// // tLBInsuredDB.setCustomerNo(aLPEdorMainSchema.getInsuredNo());
	// // tLBInsuredDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// // if (!tLBInsuredDB.getInfo())
	// // return false;
	// // Reflections aReflections = new Reflections();
	// // aReflections.transFields(tLCInsuredSchema,tLBInsuredDB.getSchema());
	// // }
	// // else
	// // {
	// // tLCInsuredSchema.setSchema(tLCInsuredDB.getSchema());
	// // }
	// //比较被保人名字是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getName()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getName())))
	// {
	// textTag.add("BBOldInsuredName", tLPInsuredBL.getName());
	// textTag.add("BBNewInsuredName", tLPInsuredSchema.getName());
	// }
	// else
	// {
	// textTag.add("BBOldInsuredName", "----");
	// textTag.add("BBNewInsuredName", "----");
	// }
	//
	// //比较被保人电话是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getPhone()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getPhone())))
	// {
	// textTag.add("BBOldPhone", tLPInsuredBL.getPhone());
	// textTag.add("BBNewPhone", tLPInsuredSchema.getPhone());
	// }
	// else
	// {
	// textTag.add("BBOldPhone", "----");
	// textTag.add("BBNewPhone", "----");
	// }
	//
	// //比较被保人邮编是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getZipCode()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getZipCode())))
	// {
	// textTag.add("BBOldZipCode", tLPInsuredBL.getZipCode());
	// textTag.add("BBNewZipCode", tLPInsuredSchema.getZipCode());
	// }
	// else
	// {
	// textTag.add("BBOldZipCode", "----");
	// textTag.add("BBNewZipCode", "----");
	// }
	//
	// //比较被保人家庭住址是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getHomeAddress()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getHomeAddress())))
	// {
	// textTag.add("BBOldHomeAddress", tLPInsuredBL.getHomeAddress());
	// textTag.add("BBNewHomeAddress", tLPInsuredSchema.getHomeAddress());
	// }
	// else
	// {
	// textTag.add("BBOldHomeAddress", "----");
	// textTag.add("BBNewHomeAddress", "----");
	// }
	//
	// //比较被保人通讯地址是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getPostalAddress()).equals(StrTool.
	// cTrim(
	// tLPInsuredSchema.getPostalAddress())))
	// {
	// textTag.add("BBOldPostalAddress", tLPInsuredBL.getPostalAddress());
	// textTag.add("BBNewPostalAddress",
	// tLPInsuredSchema.getPostalAddress());
	// }
	// else
	// {
	// textTag.add("BBOldPostalAddress", "----");
	// textTag.add("BBNewPostalAddress", "----");
	// }
	//
	// //比较被保人民族是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getNationality()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getNationality())))
	// {
	// textTag.add("BBOldNationality", tLPInsuredBL.getNationality());
	// textTag.add("BBNewNationality", tLPInsuredSchema.getNationality());
	// }
	// else
	// {
	// textTag.add("BBOldNationality", "----");
	// textTag.add("BBNewNationality", "----");
	// }
	//
	// //比较被保人婚姻状况是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getMarriage()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getMarriage())))
	// {
	// textTag.add("BBOldMarriage", tLPInsuredBL.getMarriage());
	// textTag.add("BBNewMarriage", tLPInsuredSchema.getMarriage());
	// }
	// else
	// {
	// textTag.add("BBOldMarriage", "----");
	// textTag.add("BBNewMarriage", "----");
	// }
	//
	// //比较被保人IC卡号是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getICNo()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getICNo())))
	// {
	// textTag.add("BBOldICNo", tLPInsuredBL.getICNo());
	// textTag.add("BBNewICNo", tLPInsuredSchema.getICNo());
	// }
	// else
	// {
	// textTag.add("BBOldICNo", "----");
	// textTag.add("BBNewICNo", "----");
	// }
	//
	// //比较被保人手机是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getMobile()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getMobile())))
	// {
	// textTag.add("BBOldMobile", tLPInsuredBL.getMobile());
	// textTag.add("BBNewMobile", tLPInsuredSchema.getMobile());
	// }
	// else
	// {
	// textTag.add("BBOldMobile", "----");
	// textTag.add("BBNewMobile", "----");
	// }
	//
	// //比较被保人邮件地址是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getEMail()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getEMail())))
	// {
	// textTag.add("BBOldEMail", tLPInsuredBL.getEMail());
	// textTag.add("BBNewEMail", tLPInsuredSchema.getEMail());
	// }
	// else
	// {
	// textTag.add("BBOldEMail", "----");
	// textTag.add("BBNewEMail", "----");
	// }
	//
	// //比较被保人身高是否变化
	// if (tLPInsuredBL.getStature() != tLPInsuredSchema.getStature())
	// {
	// textTag.add("BBOldStature", tLPInsuredBL.getStature());
	// textTag.add("BBNewStature", tLPInsuredSchema.getStature());
	// }
	// else
	// {
	// textTag.add("BBOldStature", "----");
	// textTag.add("BBNewStature", "----");
	// }
	//
	// //比较被保人体重是否变化
	// if (tLPInsuredBL.getAvoirdupois() != tLPInsuredSchema.getAvoirdupois())
	// {
	// textTag.add("BBOldAvoirdupois", tLPInsuredBL.getAvoirdupois());
	// textTag.add("BBNewAvoirdupois", tLPInsuredSchema.getAvoirdupois());
	// }
	// else
	// {
	// textTag.add("BBOldAvoirdupois", "----");
	// textTag.add("BBNewAvoirdupois", "----");
	// }
	//
	// //比较被保人证件类型是否变化
	// LDCodeDB OldLDCodeDB = new LDCodeDB();
	// OldLDCodeDB.setCodeType("idtype");
	// OldLDCodeDB.setCode(tLPInsuredBL.getIDType());
	// OldLDCodeDB.getInfo();
	//
	// // if (!OldLDCodeDB.getInfo())
	// // return false;
	// LDCodeDB NewLDCodeDB = new LDCodeDB();
	// NewLDCodeDB.setCodeType("idtype");
	// NewLDCodeDB.setCode(tLPInsuredSchema.getIDType());
	// NewLDCodeDB.getInfo();
	//
	// // if (!NewLDCodeDB.getInfo())
	// // return false;
	// if (!StrTool.cTrim(OldLDCodeDB.getCodeName()).equals(StrTool.cTrim(
	// NewLDCodeDB.getCodeName())))
	// {
	// textTag.add("BBOldIDType", OldLDCodeDB.getCodeName());
	// textTag.add("BBNewIDType", NewLDCodeDB.getCodeName());
	// }
	// else
	// {
	// textTag.add("BBOldIDType", "----");
	// textTag.add("BBNewIDType", "----");
	// }
	//
	// //比较被保人证件号码是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getIDNo()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getIDNo())))
	// {
	// textTag.add("BBOldIDNo", tLPInsuredBL.getIDNo());
	// textTag.add("BBNewIDNo", tLPInsuredSchema.getIDNo());
	// }
	// else
	// {
	// textTag.add("BBOldIDNo", "----");
	// textTag.add("BBNewIDNo", "----");
	// }
	//
	// //比较被保人单位名称是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getGrpName()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getGrpName())))
	// {
	// textTag.add("BBOldGrpName", tLPInsuredBL.getGrpName());
	// textTag.add("BBNewGrpName", tLPInsuredSchema.getGrpName());
	// }
	// else
	// {
	// textTag.add("BBOldGrpName", "----");
	// textTag.add("BBNewGrpName", "----");
	// }
	//
	// textTag.add("BBEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// //textTag.add("BBHandler",aLPEdorMainSchema.getOperator());
	// return true;
	// }
	//
	// //被保人重要资料变更的数据采集
	// private boolean getDetailIC(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
	// tLPInsuredSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPInsuredSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPInsuredSchema.setCustomerNo(aLPEdorMainSchema.getInsuredNo());
	// tLPInsuredSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPInsuredDB tLPInsuredDB = new LPInsuredDB();
	// tLPInsuredDB.setSchema(tLPInsuredSchema);
	//
	// if (!tLPInsuredDB.getInfo())
	// {
	// return false;
	// }
	//
	// tLPInsuredSchema = tLPInsuredDB.getSchema();
	//
	// LPInsuredBL tLPInsuredBL = new LPInsuredBL();
	// tLPInsuredBL.queryLastLPInsured(aLPEdorMainSchema, tLPInsuredSchema);
	//
	// // LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
	// // tLJSGetEndorseDB.setEndorsementNo(aLPEdorMainSchema.getEdorNo());
	// // tLJSGetEndorseDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// // tLJSGetEndorseDB.setFeeOperationType(aLPEdorMainSchema.getEdorType());
	// String sql =
	// "select sum(getmoney*(0-GetFlag)+getmoney*(1-GetFlag)) from LJSgetendorse
	// where endorsementno='" +
	// aLPEdorMainSchema.getEdorNo() + "' and FeeOperationType='" +
	// aLPEdorMainSchema.getEdorType() + "'";
	//
	// ExeSQL aExeSQL = new ExeSQL();
	// String tReturn = aExeSQL.getOneValue(sql);
	//
	// double GetMoney = 0;
	//
	// if ((tReturn == null) || StrTool.cTrim(tReturn).equals(""))
	// {
	// GetMoney = 0;
	// }
	// else
	// {
	// GetMoney = Double.parseDouble(tReturn);
	// }
	//
	// if (GetMoney == 0)
	// {
	// textTag.add("Information", "保费不变");
	// }
	// else if (GetMoney < 0)
	// {
	// GetMoney = 0 - GetMoney;
	// textTag.add("Information", "并退保费" + GetMoney + "元");
	// }
	// else if (GetMoney > 0)
	// {
	// String BFsql = sql + " and FeeFinaType='BF'";
	// aExeSQL = new ExeSQL();
	// tReturn = aExeSQL.getOneValue(BFsql);
	//
	// double BFGetMoney = 0;
	//
	// if ((tReturn == null) || StrTool.cTrim(tReturn).equals(""))
	// {
	// BFGetMoney = 0;
	// }
	// else
	// {
	// BFGetMoney = Double.parseDouble(tReturn);
	// }
	//
	// String LXsql = sql + " and FeeFinaType='LX'";
	// aExeSQL = new ExeSQL();
	// tReturn = aExeSQL.getOneValue(LXsql);
	//
	// double LXGetMoney = 0;
	//
	// if ((tReturn == null) || StrTool.cTrim(tReturn).equals(""))
	// {
	// LXGetMoney = 0;
	// }
	// else
	// {
	// LXGetMoney = Double.parseDouble(tReturn);
	// }
	//
	// textTag.add("Information",
	// "并补交保费" + BFGetMoney + "元，补交利息" + LXGetMoney + "元");
	// }
	//
	// //比较被保人性别是否变化
	// LDCodeDB OldSexLDCodeDB = new LDCodeDB();
	// OldSexLDCodeDB.setCodeType("sex");
	// OldSexLDCodeDB.setCode(tLPInsuredBL.getSex());
	// OldSexLDCodeDB.getInfo();
	//
	// LDCodeDB NewSexLDCodeDB = new LDCodeDB();
	// NewSexLDCodeDB.setCodeType("sex");
	// NewSexLDCodeDB.setCode(tLPInsuredSchema.getSex());
	// NewSexLDCodeDB.getInfo();
	//
	// if (!StrTool.cTrim(OldSexLDCodeDB.getCodeName()).equals(StrTool.cTrim(
	// NewSexLDCodeDB.getCodeName())))
	// {
	// textTag.add("ICOldSex", OldSexLDCodeDB.getCodeName());
	// textTag.add("ICNewSex", NewSexLDCodeDB.getCodeName());
	// }
	// else
	// {
	// textTag.add("ICOldSex", "----");
	// textTag.add("ICNewSex", "----");
	// }
	//
	// //比较被保人出生日期是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getBirthday()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getBirthday())))
	// {
	// textTag.add("ICOldBirthday", tLPInsuredBL.getBirthday());
	// textTag.add("ICNewBirthday", tLPInsuredSchema.getBirthday());
	// }
	// else
	// {
	// textTag.add("ICOldBirthday", "----");
	// textTag.add("ICNewBirthday", "----");
	// }
	//
	// //比较被保人证件类型是否变化
	// LDCodeDB OldIDLDCodeDB = new LDCodeDB();
	// OldIDLDCodeDB.setCodeType("idtype");
	// OldIDLDCodeDB.setCode(tLPInsuredBL.getIDType());
	// OldIDLDCodeDB.getInfo();
	//
	// LDCodeDB NewIDLDCodeDB = new LDCodeDB();
	// NewIDLDCodeDB.setCodeType("idtype");
	// NewIDLDCodeDB.setCode(tLPInsuredSchema.getIDType());
	// NewIDLDCodeDB.getInfo();
	//
	// if (!StrTool.cTrim(OldIDLDCodeDB.getCodeName()).equals(StrTool.cTrim(
	// NewIDLDCodeDB.getCodeName())))
	// {
	// textTag.add("ICOldIDType", OldIDLDCodeDB.getCodeName());
	// textTag.add("ICNewIDType", NewIDLDCodeDB.getCodeName());
	// }
	// else
	// {
	// textTag.add("ICOldIDType", "----");
	// textTag.add("ICNewIDType", "----");
	// }
	//
	// //比较被保人证件号码是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getIDNo()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getIDNo())))
	// {
	// textTag.add("ICOldIDNo", tLPInsuredBL.getIDNo());
	// textTag.add("ICNewIDNo", tLPInsuredSchema.getIDNo());
	// }
	// else
	// {
	// textTag.add("ICOldIDNo", "----");
	// textTag.add("ICNewIDNo", "----");
	// }
	//
	// //比较被保人职业代码是否变化
	// LDOccupationDB OldLDOccupationDB = new LDOccupationDB();
	// OldLDOccupationDB.setOccupationCode(tLPInsuredBL.getOccupationCode());
	// OldLDOccupationDB.getInfo();
	//
	// // LDCodeDB OldLDCodeDB = new LDCodeDB();
	// // OldLDCodeDB.setCodeType("occupationcode");
	// // OldLDCodeDB.setCode(tLPInsuredBL.getOccupationCode());
	// // OldLDCodeDB.getInfo();
	// //return false;
	// LDOccupationDB NewLDOccupationDB = new LDOccupationDB();
	// NewLDOccupationDB.setOccupationCode(tLPInsuredSchema.getOccupationCode());
	// NewLDOccupationDB.getInfo();
	//
	// // LDCodeDB NewLDCodeDB = new LDCodeDB();
	// // NewLDCodeDB.setCodeType("occupationcode");
	// // NewLDCodeDB.setCode(tLPInsuredSchema.getOccupationCode());
	// // NewLDCodeDB.getInfo();
	// // if (!NewLDCodeDB.getInfo())
	// // return false;
	// if (!StrTool.cTrim(OldLDOccupationDB.getOccupationName()).equals(
	// StrTool.cTrim(
	// NewLDOccupationDB.getOccupationName())))
	// {
	// textTag.add("ICOldOccupationCode",
	// OldLDOccupationDB.getOccupationName());
	// textTag.add("ICNewOccupationCode",
	// NewLDOccupationDB.getOccupationName());
	// }
	// else
	// {
	// textTag.add("ICOldOccupationCode", "----");
	// textTag.add("ICNewOccupationCode", "----");
	// }
	//
	// //比较被保人籍贯是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getNativePlace()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getNativePlace())))
	// {
	// textTag.add("ICOldNativePlace", tLPInsuredBL.getNativePlace());
	// textTag.add("ICNewNativePlace", tLPInsuredSchema.getNativePlace());
	// }
	// else
	// {
	// textTag.add("ICOldNativePlace", "----");
	// textTag.add("ICNewNativePlace", "----");
	// }
	//
	// //比较被保人婚姻状况是否变化
	// if (!StrTool.cTrim(tLPInsuredBL.getMarriage()).equals(StrTool.cTrim(
	// tLPInsuredSchema.getMarriage())))
	// {
	// textTag.add("ICOldMarriage", tLPInsuredBL.getMarriage());
	// textTag.add("ICNewMarriage", tLPInsuredSchema.getMarriage());
	// }
	// else
	// {
	// textTag.add("ICOldMarriage", "----");
	// textTag.add("ICNewMarriage", "----");
	// }
	//
	// textTag.add("ICEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// //textTag.add("BBHandler",aLPEdorMainSchema.getOperator());
	// //判断是否显示现金价值变更的通知
	// LJSGetEndorseDB tLJSGetEndorsementDB = new LJSGetEndorseDB();
	// String strSql1 = " select * from ljsgetendorse where endorsementno = '" +
	// aLPEdorMainSchema.getEdorNo() +
	// "' and FeeOperationType = '" +
	// aLPEdorMainSchema.getEdorType() +
	// "' order by polno, feefinatype";
	// logger.debug("strSql1 :" + strSql1);
	//
	// LJSGetEndorseSet tLJSGetEndorsementSet = tLJSGetEndorsementDB.
	// executeQuery(strSql1);
	//
	// if (tLJSGetEndorsementSet.size() > 0)
	// {
	// String AddSentence = "本合同原现金价值表作废，新现金价值表自" +
	// aLPEdorMainSchema.getEdorValiDate() + "日开始生效。";
	// textTag.add("ICAddSentence", AddSentence);
	// }
	// else
	// {
	// textTag.add("ICAddSentence", "");
	// }
	//
	// //获取所有险种信息
	// LPPolDB tempLPPolDB = new LPPolDB();
	// tempLPPolDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tempLPPolDB.setEdorType(aLPEdorMainSchema.getEdorType());
	// LPPolSet tempLPPolSet = tempLPPolDB.query();
	//
	// LPEdorMainSchema tLPEdorMainSchema;
	//
	// for (int i = 0; i < tempLPPolSet.size(); i++)
	// {
	// tLPEdorMainSchema = new LPEdorMainSchema();
	// tLPEdorMainSchema.setSchema(aLPEdorMainSchema);
	// tLPEdorMainSchema.setPolNo(tempLPPolSet.get(i + 1).getPolNo());
	//
	// //获取保全保单表数据
	// LPPolDB tLPPolDB = new LPPolDB();
	// tLPPolDB.setEdorNo(tLPEdorMainSchema.getEdorNo());
	// tLPPolDB.setPolNo(tLPEdorMainSchema.getPolNo());
	// tLPPolDB.setEdorType(tLPEdorMainSchema.getEdorType());
	//
	// if (!tLPPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// //获取该保全之前的保单表数据
	// // LCPolDB tLCPolDB = new LCPolDB();
	// // tLCPolDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// // if (!tLCPolDB.getInfo()) return false;
	// LPPolBL tLPPolBL = new LPPolBL();
	//
	// if (!tLPPolBL.queryLastLPPol(tLPEdorMainSchema, tLPPolDB.getSchema()))
	// {
	// return false;
	// }
	//
	// //从险种定义表获取险种名称
	// LMRiskDB tLMRiskDB = new LMRiskDB();
	// tLMRiskDB.setRiskCode(tLPPolBL.getRiskCode());
	//
	// if (!tLMRiskDB.getInfo())
	// {
	// return false;
	// }
	//
	// if ((tLPPolBL.getPrem() == tLPPolDB.getPrem()) &&
	// (tLPPolBL.getAmnt() == tLPPolDB.getAmnt()))
	// {
	// continue;
	// }
	//
	// xmlexport.addDisplayControl("displayICRisk" + i);
	// textTag.add("ICRisk" + i, tLMRiskDB.getRiskName());
	//
	// textTag.add("ICOldPrem" + i,
	// new DecimalFormat("0.00").format(tLPPolBL.getPrem()) +
	// " 元");
	// textTag.add("ICOldAmnt" + i,
	// new DecimalFormat("0.00").format(tLPPolBL.getAmnt()) +
	// " 元");
	//
	// textTag.add("ICNewPrem" + i,
	// new DecimalFormat("0.00").format(tLPPolDB.getPrem()) +
	// " 元");
	// textTag.add("ICNewAmnt" + i,
	// new DecimalFormat("0.00").format(tLPPolDB.getAmnt()) +
	// " 元");
	// }
	//
	// return true;
	// }
	//
	// //投保人资料变更的数据采集
	// private boolean getDetailAC(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPAppntIndSchema tLPAppntIndSchema = new LPAppntIndSchema();
	// tLPAppntIndSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPAppntIndSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPAppntIndSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPAppntIndDB tLPAppntIndDB = new LPAppntIndDB();
	// tLPAppntIndDB.setSchema(tLPAppntIndSchema);
	//
	// LPAppntIndSet tLPAppntIndSet = new LPAppntIndSet();
	// tLPAppntIndSet.set(tLPAppntIndDB.query());
	//
	// if (tLPAppntIndSet.size() == 0)
	// {
	// return false;
	// }
	// else
	// {
	// tLPAppntIndSchema = tLPAppntIndSet.get(1);
	// }
	//
	// LPAppntIndBL tLPAppntIndBL = new LPAppntIndBL();
	// tLPAppntIndBL.queryLastLPAppntInd(aLPEdorMainSchema, tLPAppntIndSchema);
	//
	// //比较投保人姓名是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getName()).equals(StrTool.cTrim(
	// tLPAppntIndSchema.getName())))
	// {
	// textTag.add("ACOldName", tLPAppntIndBL.getName());
	// textTag.add("ACNewName", tLPAppntIndSchema.getName());
	// }
	// else
	// {
	// textTag.add("ACOldName", "----");
	// textTag.add("ACNewName", "----");
	// }
	//
	// //比较Email是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getEMail()).equals(StrTool.cTrim(
	// tLPAppntIndSchema.getEMail())))
	// {
	// textTag.add("ACOldMail", tLPAppntIndBL.getEMail());
	// textTag.add("ACNewMail", tLPAppntIndSchema.getEMail());
	// }
	// else
	// {
	// textTag.add("ACOldMail", "----");
	// textTag.add("ACNewMail", "----");
	// }
	//
	// //比较投保人性别是否变化
	// LDCodeDB OldSexLDCodeDB = new LDCodeDB();
	// OldSexLDCodeDB.setCodeType("sex");
	// OldSexLDCodeDB.setCode(tLPAppntIndBL.getSex());
	// OldSexLDCodeDB.getInfo();
	//
	// LDCodeDB NewSexLDCodeDB = new LDCodeDB();
	// NewSexLDCodeDB.setCodeType("sex");
	// NewSexLDCodeDB.setCode(tLPAppntIndSchema.getSex());
	// NewSexLDCodeDB.getInfo();
	//
	// if (!StrTool.cTrim(OldSexLDCodeDB.getCodeName()).equals(StrTool.cTrim(
	// NewSexLDCodeDB.getCodeName())))
	// {
	// textTag.add("ACOldSex", OldSexLDCodeDB.getCodeName());
	// textTag.add("ACNewSex", NewSexLDCodeDB.getCodeName());
	// }
	// else
	// {
	// textTag.add("ACOldSex", "----");
	// textTag.add("ACNewSex", "----");
	// }
	//
	// //比较投保人出生年月是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getBirthday()).equals(StrTool.cTrim(
	// tLPAppntIndSchema.getBirthday())))
	// {
	// textTag.add("ACOldBirthday", tLPAppntIndBL.getBirthday());
	// textTag.add("ACNewBirthday", tLPAppntIndSchema.getBirthday());
	// }
	// else
	// {
	// textTag.add("ACOldBirthday", "----");
	// textTag.add("ACNewBirthday", "----");
	// }
	//
	// //比较证件类型
	// LDCodeDB OldLDCodeDB = new LDCodeDB();
	// OldLDCodeDB.setCodeType("idtype");
	// OldLDCodeDB.setCode(tLPAppntIndBL.getIDType());
	// OldLDCodeDB.getInfo();
	//
	// //return false;
	// LDCodeDB NewLDCodeDB = new LDCodeDB();
	// NewLDCodeDB.setCodeType("idtype");
	// NewLDCodeDB.setCode(tLPAppntIndSchema.getIDType());
	// NewLDCodeDB.getInfo();
	//
	// // if (!NewLDCodeDB.getInfo())
	// // return false;
	// if (!StrTool.cTrim(OldLDCodeDB.getCodeName()).equals(StrTool.cTrim(
	// NewLDCodeDB.getCodeName())))
	// {
	// textTag.add("ACOldIDType", OldLDCodeDB.getCodeName());
	// textTag.add("ACNewIDType", NewLDCodeDB.getCodeName());
	// }
	// else
	// {
	// textTag.add("ACOldIDType", "----");
	// textTag.add("ACNewIDType", "----");
	// }
	//
	// //比较投保人证件号码是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getIDNo()).equals(StrTool.cTrim(
	// tLPAppntIndSchema.getIDNo())))
	// {
	// textTag.add("ACOldIDNo", tLPAppntIndBL.getIDNo());
	// textTag.add("ACNewIDNo", tLPAppntIndSchema.getIDNo());
	// }
	// else
	// {
	// textTag.add("ACOldIDNo", "----");
	// textTag.add("ACNewIDNo", "----");
	// }
	//
	// //比较投保人与被保险人关系是否变化
	// LDCodeDB OldRelLDCodeDB = new LDCodeDB();
	// OldRelLDCodeDB.setCodeType("relation");
	// OldRelLDCodeDB.setCode(tLPAppntIndBL.getRelationToInsured());
	// OldRelLDCodeDB.getInfo();
	//
	// LDCodeDB NewRelLDCodeDB = new LDCodeDB();
	// NewRelLDCodeDB.setCodeType("relation");
	// NewRelLDCodeDB.setCode(tLPAppntIndSchema.getRelationToInsured());
	// NewRelLDCodeDB.getInfo();
	//
	// if (!StrTool.cTrim(OldRelLDCodeDB.getCodeName()).equals(StrTool.cTrim(
	// NewRelLDCodeDB.getCodeName())))
	// {
	// textTag.add("ACOldRelationToInsured", OldRelLDCodeDB.getCodeName());
	// textTag.add("ACNewRelationToInsured", NewRelLDCodeDB.getCodeName());
	// }
	// else
	// {
	// textTag.add("ACOldRelationToInsured", "----");
	// textTag.add("ACNewRelationToInsured", "----");
	// }
	//
	// //比较投保人电话是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getPhone()).equals(StrTool.cTrim(
	// tLPAppntIndSchema.getPhone())))
	// {
	// textTag.add("ACOldPhone", tLPAppntIndBL.getPhone());
	// textTag.add("ACNewPhone", tLPAppntIndSchema.getPhone());
	// }
	// else
	// {
	// textTag.add("ACOldPhone", "----");
	// textTag.add("ACNewPhone", "----");
	// }
	//
	// //比较投保人电话2是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getPhone2()).equals(StrTool.cTrim(
	// tLPAppntIndSchema.getPhone2())))
	// {
	// textTag.add("ACOldPhone2", tLPAppntIndBL.getPhone2());
	// textTag.add("ACNewPhone2", tLPAppntIndSchema.getPhone2());
	// }
	// else
	// {
	// textTag.add("ACOldPhone2", "----");
	// textTag.add("ACNewPhone2", "----");
	// }
	//
	// //比较投保人手机号码是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getMobile()).equals(StrTool.cTrim(
	// tLPAppntIndSchema.getMobile())))
	// {
	// textTag.add("ACOldMobile", tLPAppntIndBL.getMobile());
	// textTag.add("ACNewMobile", tLPAppntIndSchema.getMobile());
	// }
	// else
	// {
	// textTag.add("ACOldMobile", "----");
	// textTag.add("ACNewMobile", "----");
	// }
	//
	// //比较投保人邮编是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getZipCode()).equals(StrTool.cTrim(
	// tLPAppntIndSchema.getZipCode())))
	// {
	// textTag.add("ACOldZipCode", tLPAppntIndBL.getZipCode());
	// textTag.add("ACNewZipCode", tLPAppntIndSchema.getZipCode());
	// }
	// else
	// {
	// textTag.add("ACOldZipCode", "----");
	// textTag.add("ACNewZipCode", "----");
	// }
	//
	// //比较投保人通讯地址是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getPostalAddress()).equals(StrTool.
	// cTrim(
	// tLPAppntIndSchema.getPostalAddress())))
	// {
	// textTag.add("ACOldPostalAddress", tLPAppntIndBL.getPostalAddress());
	// textTag.add("ACNewPostalAddress",
	// tLPAppntIndSchema.getPostalAddress());
	// }
	// else
	// {
	// textTag.add("ACOldPostalAddress", "----");
	// textTag.add("ACNewPostalAddress", "----");
	// }
	//
	// //比较投保人职业类别是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getOccupationCode()).equals(StrTool.
	// cTrim(
	// tLPAppntIndSchema.getOccupationCode())))
	// {
	// textTag.add("ACOldOccupationCode", tLPAppntIndBL.getOccupationCode());
	// textTag.add("ACNewOccupationCode",
	// tLPAppntIndSchema.getOccupationCode());
	// }
	// else
	// {
	// textTag.add("ACOldOccupationCode", "----");
	// textTag.add("ACNewOccupationCode", "----");
	// }
	//
	// //比较投保人工作单位是否变化
	// if (!StrTool.cTrim(tLPAppntIndBL.getGrpName()).equals(StrTool.cTrim(
	// tLPAppntIndSchema.getGrpName())))
	// {
	// textTag.add("ACOldGrpName", tLPAppntIndBL.getGrpName());
	// textTag.add("ACNewGrpName", tLPAppntIndSchema.getGrpName());
	// }
	// else
	// {
	// textTag.add("ACOldGrpName", "----");
	// textTag.add("ACNewGrpName", "----");
	// }
	//
	// textTag.add("ACEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// return true;
	// }
	//
	// //交费年期变更
	// private boolean getDetailPY(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// //变更后信息
	// LPPolSchema tLPPolSchema = new LPPolSchema();
	// tLPPolSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPPolSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPPolSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPPolDB tLPPolDB = new LPPolDB();
	// tLPPolDB.setSchema(tLPPolSchema);
	//
	// if (!tLPPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// tLPPolSchema = tLPPolDB.getSchema();
	//
	// //变更前最新的信息
	// LPPolBL tLPPolBL = new LPPolBL();
	// tLPPolBL.queryLastLPPol(aLPEdorMainSchema, tLPPolSchema);
	//
	// //批改补退费信息
	// String sql =
	// "select sum(getmoney*(0-GetFlag)+getmoney*(1-GetFlag)) from LJSgetendorse
	// where endorsementno='" +
	// aLPEdorMainSchema.getEdorNo() + "' and FeeOperationType='" +
	// aLPEdorMainSchema.getEdorType() + "'";
	//
	// ExeSQL aExeSQL = new ExeSQL();
	// String tReturn = aExeSQL.getOneValue(sql);
	//
	// double GetMoney = 0;
	//
	// if ((tReturn == null) || StrTool.cTrim(tReturn).equals(""))
	// {
	// GetMoney = 0;
	// }
	// else
	// {
	// GetMoney = Double.parseDouble(tReturn);
	// }
	//
	// if (GetMoney >= 0)
	// {
	// textTag.add("BTMoney", "应补交费用" + GetMoney + "元，");
	// }
	// else
	// {
	// GetMoney = 0 - GetMoney;
	// textTag.add("BTMoney", "并退还费用" + GetMoney + "元");
	// }
	//
	// LMRiskDB tLMRiskDB = new LMRiskDB();
	// tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
	// tLMRiskDB.getInfo();
	//
	// textTag.add("PYBYear", tLPPolBL.getPayYears());
	// textTag.add("PYAYear", tLPPolSchema.getPayYears());
	// textTag.add("PYBMoney", tLPPolBL.getPrem());
	// textTag.add("PYAMoney", tLPPolSchema.getPrem());
	// textTag.add("PYRisk", tLMRiskDB.getRiskName());
	//
	// tLPPolBL = new LPPolBL();
	//
	// //取得本险种主附险的信息
	// double tSumPrem = 0;
	// LPPolSet tLPPolSet = new LPPolSet();
	// tLPPolSet = tLPPolBL.queryAllLPPol(aLPEdorMainSchema);
	//
	// for (int i = 1; i <= tLPPolSet.size(); i++)
	// {
	// LPPolSchema ttLPPolSchema = new LPPolSchema();
	// ttLPPolSchema = tLPPolSet.get(i);
	// tSumPrem = tSumPrem + ttLPPolSchema.getPrem();
	// }
	//
	// textTag.add("SumMoney", tSumPrem);
	// textTag.add("AppntName", tLPPolBL.getAppntName());
	// textTag.add("InsuredName", tLPPolBL.getInsuredName());
	// textTag.add("PolNo", tLPPolBL.getPolNo());
	//
	// // String pattern="yyyy年MM月dd日";
	// // SimpleDateFormat df = new SimpleDateFormat(pattern);
	// // String tString = df.format(aLPEdorMainSchema.getEdorValiDate());
	// String tString = aLPEdorMainSchema.getEdorValiDate();
	// textTag.add("PYDate", tString);
	// textTag.add("ValiDate", tString);
	// textTag.add("NoteDate", tString);
	// logger.debug("print data add end");
	//
	// return true;
	// }
	//
	// //给付间隔变更的数据采集
	// private boolean getDetailGC(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// String[] strArr = null;
	//
	// //查询出本次CA所涉及的个人账户
	// LPGetDB tLPGetDB = new LPGetDB();
	// LPGetSet tLPGetSet = new LPGetSet();
	// String strsql;
	// strsql = "select * from LPGet where EdorNo ='" +
	// aLPEdorMainSchema.getEdorNo() + "' and EdorType = '" +
	// aLPEdorMainSchema.getEdorType() + "'";
	// logger.debug(strsql);
	// tLPGetSet = tLPGetDB.executeQuery(strsql);
	//
	// if (tLPGetSet.size() == 0)
	// {
	// return false;
	// }
	// else
	// {
	// LPGetSchema aLPGetSchema;
	//
	// tlistTable = new ListTable();
	// tlistTable.setName("GC");
	//
	// for (int i = 1; i <= tLPGetSet.size(); i++)
	// {
	// aLPGetSchema = new LPGetSchema();
	// aLPGetSchema = tLPGetSet.get(i);
	// logger.debug("aLPGetSchema: " + aLPGetSchema.encode());
	//
	// //查询对应的LC表的信息
	// LCGetDB tLCGetDB = new LCGetDB();
	// tLCGetDB.setPolNo(aLPGetSchema.getPolNo());
	// tLCGetDB.setDutyCode(aLPGetSchema.getDutyCode());
	// tLCGetDB.setGetDutyCode(aLPGetSchema.getGetDutyCode());
	//
	// if (!tLCGetDB.getInfo())
	// {
	// return false;
	// }
	//
	// LCGetSchema tLCGetSchema = tLCGetDB.getSchema();
	// strArr = new String[6];
	// strArr[0] = aLPGetSchema.getDutyCode(); //责任编码
	// strArr[1] = aLPGetSchema.getGetDutyCode(); //给付责任编码
	//
	// if ((tLCGetSchema.getGetDutyKind() == null) ||
	// tLCGetSchema.getGetDutyKind().equals(""))
	// {
	// strArr[2] = ""; //原给付类型
	// }
	// else
	// {
	// String sql1 =
	// "select * from LMDutyGetAlive where GetDutyKind = '" +
	// tLCGetSchema.getGetDutyKind() +
	// "' and GetDutyCode in" +
	// "( select GetDutyCode from LMDutyGetRela where dutycode in " +
	// "( select dutycode from LMRiskDuty where riskcode in " +
	// "( select riskcode from lcpol where polno = '" +
	// aLPEdorMainSchema.getPolNo() + "'" + "))) ";
	//
	// logger.debug(sql1);
	//
	// LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
	// LMDutyGetAliveSet tLMDutyGetAliveSet = tLMDutyGetAliveDB.
	// executeQuery(sql1);
	// logger.debug(tLMDutyGetAliveSet.get(1).getGetDutyName());
	// strArr[2] = tLMDutyGetAliveSet.get(1).getGetDutyName(); //原给付类型
	// }
	//
	// LDCodeDB OldLDCodeDB = new LDCodeDB();
	// OldLDCodeDB.setCodeType("getintv");
	// OldLDCodeDB.setCode(String.valueOf(tLCGetSchema.getGetIntv()));
	//
	// if (!OldLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// strArr[3] = OldLDCodeDB.getCodeName(); //原给付间隔
	//
	// if ((aLPGetSchema.getGetDutyKind() == null) ||
	// aLPGetSchema.getGetDutyKind().equals(""))
	// {
	// strArr[4] = ""; //现给付类型
	// }
	// else
	// {
	// String sql1 =
	// "select * from LMDutyGetAlive where GetDutyKind = '" +
	// aLPGetSchema.getGetDutyKind() +
	// "' and GetDutyCode in" +
	// "( select GetDutyCode from LMDutyGetRela where dutycode in " +
	// "( select dutycode from LMRiskDuty where riskcode in " +
	// "( select riskcode from lcpol where polno = '" +
	// aLPEdorMainSchema.getPolNo() + "'" + "))) ";
	//
	// logger.debug(sql1);
	//
	// LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
	// LMDutyGetAliveSet tLMDutyGetAliveSet = tLMDutyGetAliveDB.
	// executeQuery(sql1);
	// logger.debug(tLMDutyGetAliveSet.get(1).getGetDutyName());
	// strArr[4] = tLMDutyGetAliveSet.get(1).getGetDutyName(); //现给付类型
	// }
	//
	// LDCodeDB NewLDCodeDB = new LDCodeDB();
	// NewLDCodeDB.setCodeType("getintv");
	// NewLDCodeDB.setCode(String.valueOf(aLPGetSchema.getGetIntv()));
	//
	// if (!NewLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// strArr[5] = NewLDCodeDB.getCodeName(); //现给付间隔
	//
	// tlistTable.add(strArr);
	// }
	//
	// strArr = new String[6];
	// strArr[0] = "责任编码";
	// strArr[1] = "给付责任编码";
	// strArr[2] = "原给付类型";
	// strArr[3] = "原给付间隔";
	// strArr[4] = "现给付类型";
	// strArr[5] = "现给付间隔";
	// }
	//
	// textTag.add("GCEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	// myDocument = xmlexport.addListTable(tlistTable, strArr);
	//
	// return true;
	// }
	//
	// //领取年龄变更的数据采集
	// private boolean getDetailYC(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// String[] strArr = null;
	//
	// //查询出本次CA所涉及的个人账户
	// LPDutyDB tLPDutyDB = new LPDutyDB();
	// LPDutySet tLPDutySet = new LPDutySet();
	// String sql;
	// sql = "select * from LPDuty where EdorNo ='" +
	// aLPEdorMainSchema.getEdorNo() + "' and EdorType = '" +
	// aLPEdorMainSchema.getEdorType() + "'";
	// logger.debug(sql);
	// tLPDutySet = tLPDutyDB.executeQuery(sql);
	//
	// if (tLPDutySet.size() == 0)
	// {
	// return false;
	// }
	// else
	// {
	// LPDutySchema aLPDutySchema;
	// tlistTable = new ListTable();
	// tlistTable.setName("YC");
	//
	// for (int i = 1; i <= tLPDutySet.size(); i++)
	// {
	// aLPDutySchema = new LPDutySchema();
	// aLPDutySchema = tLPDutySet.get(i);
	// logger.debug("aLPGetSchema: " + aLPDutySchema.encode());
	//
	// //查询对应的LC表的信息
	// LCDutyDB tLCDutyDB = new LCDutyDB();
	// tLCDutyDB.setPolNo(aLPDutySchema.getPolNo());
	// tLCDutyDB.setDutyCode(aLPDutySchema.getDutyCode());
	//
	// if (!tLCDutyDB.getInfo())
	// {
	// return false;
	// }
	//
	// LCDutySchema tLCDutySchema = tLCDutyDB.getSchema();
	//
	// String newFlag;
	// String oldFlag;
	// LDCodeDB tLDCodeDB = new LDCodeDB();
	// tLDCodeDB.setCodeType("getyearflag");
	// tLDCodeDB.setCode(tLCDutySchema.getGetYearFlag());
	//
	// if (tLDCodeDB.getInfo())
	// {
	// LDCodeSchema tLDCodeSchema = tLDCodeDB.getSchema();
	// oldFlag = tLDCodeSchema.getCodeName();
	// }
	// else
	// {
	// oldFlag = tLCDutySchema.getGetYearFlag();
	// }
	//
	// tLDCodeDB = new LDCodeDB();
	// tLDCodeDB.setCodeType("getyearflag");
	// tLDCodeDB.setCode(aLPDutySchema.getGetYearFlag());
	//
	// if (tLDCodeDB.getInfo())
	// {
	// LDCodeSchema tLDCodeSchema = tLDCodeDB.getSchema();
	// newFlag = tLDCodeSchema.getCodeName();
	// }
	// else
	// {
	// newFlag = tLCDutySchema.getGetYearFlag();
	// }
	//
	// strArr = new String[6];
	// strArr[1] = aLPDutySchema.getDutyCode(); //责任编码
	// strArr[2] = String.valueOf(tLCDutySchema.getGetYear()); //原领取年龄
	// strArr[3] = oldFlag; //原领取年龄标志
	// strArr[4] = String.valueOf(aLPDutySchema.getGetYear()); //现领取年龄
	// strArr[5] = newFlag; //现领取年龄标志
	// tlistTable.add(strArr);
	// }
	//
	// strArr = new String[5];
	// strArr[0] = "责任编码";
	// strArr[1] = "原领取年龄";
	// strArr[2] = "原领取间隔";
	// strArr[3] = "现领取年龄";
	// strArr[4] = "现领取间隔";
	// }
	//
	// textTag.add("YCEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	// myDocument = xmlexport.addListTable(tlistTable, strArr);
	//
	// return true;
	// }
	//
	// //受益人资料变更的数据采集
	// private boolean getDetailBC(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPBnfSchema tLPBnfSchema = new LPBnfSchema();
	// tLPBnfSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPBnfSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPBnfSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPBnfDB tLPBnfDB = new LPBnfDB();
	// tLPBnfDB.setSchema(tLPBnfSchema);
	//
	// LPBnfSet tLPBnfSet = new LPBnfSet();
	// tLPBnfSet.set(tLPBnfDB.query());
	//
	// int nBnfSetSize;
	// nBnfSetSize = tLPBnfSet.size();
	// logger.debug("==> nBnfSetSize=" + nBnfSetSize);
	//
	// LPBnfBL tOldLPBnfBL = new LPBnfBL();
	// LPBnfSet tOldLPBnfSet = new LPBnfSet();
	// tOldLPBnfSet = tOldLPBnfBL.queryLastLPBnf(aLPEdorMainSchema);
	//
	// int nOldBnfSetSize;
	// nOldBnfSetSize = tOldLPBnfSet.size();
	// logger.debug("==> nOldBnfSetSize=" + nOldBnfSetSize);
	//
	// if ((0 == nBnfSetSize) && (0 == nOldBnfSetSize))
	// {
	// return false;
	// }
	// else
	// {
	// logger.debug("Begin to Make Printing Data");
	//
	// LPBnfSchema aLPBnfSchema;
	// LPBnfSchema aOldLPBnfSchema;
	//
	// //LPBnfBL tLPBnfBL;
	// tlistTable = new ListTable();
	// tlistTable.setName("BC");
	//
	// int nMax;
	// nMax = (nBnfSetSize > nOldBnfSetSize) ? nBnfSetSize :
	// nOldBnfSetSize;
	// logger.debug("==> nMax=" + nMax);
	//
	// for (int i = 1; i <= nMax; i++)
	// {
	// aLPBnfSchema = new LPBnfSchema();
	// aLPBnfSchema = tLPBnfSet.get(i);
	// aOldLPBnfSchema = new LPBnfSchema();
	// aOldLPBnfSchema = tOldLPBnfSet.get(i);
	//
	// strArr = new String[16];
	//
	// if (aOldLPBnfSchema != null)
	// {
	// logger.debug("aOldLPBnfSchema: " +
	// aOldLPBnfSchema.encode());
	//
	// if ((aOldLPBnfSchema.getBnfType() == null) ||
	// aOldLPBnfSchema.getBnfType().equals(""))
	// {
	// strArr[0] = "----";
	// }
	// else if (aOldLPBnfSchema.getBnfType().equals("0"))
	// {
	// strArr[0] = "生存受益人";
	// }
	// else if (aOldLPBnfSchema.getBnfType().equals("1"))
	// {
	// strArr[0] = "身故受益人";
	// }
	//
	// strArr[1] = aOldLPBnfSchema.getName();
	// strArr[2] = String.valueOf((int) (aOldLPBnfSchema.getBnfLot() *
	// 100)) +
	// "%";
	//
	// LDCodeDB OldRelLDCodeDB = new LDCodeDB();
	// OldRelLDCodeDB.setCodeType("relation");
	// OldRelLDCodeDB.setCode(aOldLPBnfSchema.getRelationToInsured());
	// OldRelLDCodeDB.getInfo();
	// strArr[3] = OldRelLDCodeDB.getCodeName();
	//
	// LDCodeDB OldLDCodeDB = new LDCodeDB();
	// OldLDCodeDB.setCodeType("idtype");
	// OldLDCodeDB.setCode(aOldLPBnfSchema.getIDType());
	// OldLDCodeDB.getInfo();
	// strArr[4] = OldLDCodeDB.getCodeName();
	// strArr[5] = aOldLPBnfSchema.getIDNo();
	// strArr[12] = aOldLPBnfSchema.getBirthday();
	// strArr[14] = aOldLPBnfSchema.getBnfGrade();
	// }
	// else
	// {
	// logger.debug("aOldLPBnfSchema is null");
	//
	// int j;
	//
	// for (j = 0; j < 6; j++)
	// {
	// strArr[j] = "";
	// }
	//
	// strArr[12] = "";
	// strArr[12] = "";
	// }
	//
	// if (aLPBnfSchema != null)
	// {
	// logger.debug("aLPBnfSchema: " +
	// aLPBnfSchema.encode());
	//
	// if ((aLPBnfSchema.getBnfType() == null) ||
	// aLPBnfSchema.getBnfType().equals(""))
	// {
	// //continue;
	// strArr[6] = "----";
	// }
	// else if (aLPBnfSchema.getBnfType().equals("0"))
	// {
	// strArr[6] = "生存受益人";
	// }
	// else if (aLPBnfSchema.getBnfType().equals("1"))
	// {
	// strArr[6] = "身故受益人";
	// }
	//
	// strArr[7] = aLPBnfSchema.getName();
	// strArr[8] = String.valueOf((int) (aLPBnfSchema.getBnfLot() *
	// 100)) +
	// "%";
	//
	// LDCodeDB NewRelLDCodeDB = new LDCodeDB();
	// NewRelLDCodeDB.setCodeType("relation");
	// NewRelLDCodeDB.setCode(aLPBnfSchema.getRelationToInsured());
	// NewRelLDCodeDB.getInfo();
	// strArr[9] = NewRelLDCodeDB.getCodeName();
	//
	// LDCodeDB NewLDCodeDB = new LDCodeDB();
	// NewLDCodeDB.setCodeType("idtype");
	// NewLDCodeDB.setCode(aLPBnfSchema.getIDType());
	// NewLDCodeDB.getInfo();
	// strArr[10] = NewLDCodeDB.getCodeName();
	// strArr[11] = aLPBnfSchema.getIDNo();
	// strArr[13] = aLPBnfSchema.getBirthday();
	// strArr[15] = aLPBnfSchema.getBnfGrade();
	// }
	// else
	// {
	// logger.debug("aLPBnfSchema is null");
	//
	// int j;
	//
	// for (j = 6; j < 12; j++)
	// {
	// strArr[j] = "";
	// }
	//
	// strArr[13] = "";
	// strArr[15] = "";
	// }
	//
	// tlistTable.add(strArr);
	// }
	//
	// strArr = new String[16];
	// strArr[0] = "原受益种类";
	// strArr[1] = "原姓名";
	// strArr[2] = "原受益比例";
	// strArr[3] = "原与被保险人关系";
	// strArr[4] = "原证件类型";
	// strArr[5] = "原证件号码";
	// strArr[6] = "现受益种类";
	// strArr[7] = "现姓名";
	// strArr[8] = "现受益比例";
	// strArr[9] = "现与被保险人关系";
	// strArr[10] = "现证件类型";
	// strArr[11] = "现证件号码";
	//
	// strArr[12] = "原出生日期";
	// strArr[13] = "现出生日期";
	// strArr[14] = "原受益顺序";
	// strArr[15] = "现受益顺序";
	// }
	//
	// textTag.add("BCEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	// myDocument = xmlexport.addListTable(tlistTable, strArr);
	//
	// return true;
	// }
	//
	// //特约变更数据采集
	// private boolean getDetailSC(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// logger.debug("==>PrtEndorsementBL : Begin Get DetailSC");
	//
	// LPSpecBL oldLPSpecBL = new LPSpecBL();
	// LPSpecBL newLPSpecBL = new LPSpecBL();
	//
	// oldLPSpecBL.queryOldLPSpec(aLPEdorMainSchema);
	// newLPSpecBL.queryNewLPSpec(aLPEdorMainSchema);
	//
	// //取得新旧特约内容
	// String strOldSpec = "";
	// String strNewSpec = "";
	//
	// if (oldLPSpecBL.mLPSpecSchema != null)
	// {
	// strOldSpec = oldLPSpecBL.mLPSpecSchema.getSpecContent();
	// }
	//
	// if (newLPSpecBL.mLPSpecSchema != null)
	// {
	// strNewSpec = newLPSpecBL.mLPSpecSchema.getSpecContent();
	// }
	//
	// //比较投保单位特约是否变化
	// if (!StrTool.cTrim(strOldSpec).equals(StrTool.cTrim(strNewSpec)))
	// {
	// textTag.add("SCOldSpec", strOldSpec);
	// textTag.add("SCNewSpec", strNewSpec);
	// }
	// else
	// {
	// textTag.add("SCOldSpec", "----");
	// textTag.add("SCNewSpec", "----");
	// }
	//
	// //取得险种代码
	// LCPolSchema tLCPolSchema = new LCPolSchema();
	// LCPolDB tLCPolDB = new LCPolDB();
	// LCPolSet tLCPolSet = new LCPolSet();
	// tLCPolSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLCPolDB.setSchema(tLCPolSchema);
	// tLCPolSet = tLCPolDB.query();
	//
	// if (tLCPolSet.size() != 1)
	// {
	// CError.buildErr(this, "LCPol表数据错误!");
	//
	// return false;
	// }
	//
	// tLCPolSchema = tLCPolSet.get(1);
	//
	// //取得险种名称
	// LMRiskSchema tLMRiskSchema = new LMRiskSchema();
	// LMRiskDB tLMRiskDB = new LMRiskDB();
	// LMRiskSet tLMRiskSet = new LMRiskSet();
	// tLMRiskSchema.setRiskCode(tLCPolSchema.getRiskCode());
	// tLMRiskDB.setSchema(tLMRiskSchema);
	// tLMRiskSet = tLMRiskDB.query();
	//
	// if (tLMRiskSet.size() == 1)
	// {
	// tLMRiskSchema = tLMRiskSet.get(1);
	// }
	// else
	// {
	// CError.buildErr(this, "取险种名称出错!");
	//
	// return false;
	// }
	//
	// textTag.add("RiskName", tLMRiskSchema.getRiskName());
	//
	// //取得有效日期
	// textTag.add("SCEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// return true;
	// }
	//
	// //补充告知信息变更数据采集
	// private boolean getDetailAI(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
	// tLPUWMasterMainDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPUWMasterMainDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
	//
	// LPCustomerImpartDB tLPCustomerImpartDB = new LPCustomerImpartDB();
	// tLPCustomerImpartDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPCustomerImpartDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
	//
	// LPCustomerImpartSet tLPCustomerImpartSet = tLPCustomerImpartDB.query();
	//
	// if (tLPCustomerImpartSet.size() == 0)
	// {
	// logger.debug("补充告知信息变更数据采集LPUWMasterMainDB 查找失败");
	// }
	//
	// tlistTable = new ListTable();
	// tlistTable.setName("AI");
	//
	// for (int i = 0; i < tLPCustomerImpartSet.size(); i++)
	// {
	// LPCustomerImpartSchema tLPCustomerImpartSchema =
	// tLPCustomerImpartSet.get(i +
	// 1);
	//
	// strArr = new String[3];
	//
	// //告知类型
	// LDCodeDB tLDCodeDB = new LDCodeDB();
	// tLDCodeDB.setCodeType("impartver");
	// tLDCodeDB.setCode(tLPCustomerImpartSchema.getImpartVer());
	//
	// if (!tLDCodeDB.getInfo())
	// {
	// CError.buildErr(this, "");
	//
	// return false;
	// }
	//
	// strArr[0] = tLDCodeDB.getCodeName();
	//
	// //告知客户
	// tLDCodeDB.setCodeType("customertype");
	// tLDCodeDB.setCode(tLPCustomerImpartSchema.getCustomerNoType());
	//
	// if (!tLDCodeDB.getInfo())
	// {
	// CError.buildErr(this, "");
	//
	// return false;
	// }
	//
	// strArr[1] = tLDCodeDB.getCodeName();
	//
	// //告知内容
	// strArr[2] = tLPCustomerImpartSchema.getImpartContent();
	//
	// //增加一行
	// tlistTable.add(strArr);
	// }
	//
	// String[] strArr =
	// {
	// "告知类型", "告知客户", "告知内容"};
	// myDocument = xmlexport.addListTable(tlistTable, strArr);
	//
	// // if (!tLPUWMasterMainDB.getInfo())
	// // {
	// // logger.debug("补充告知信息变更数据采集LPUWMasterMainDB 查找失败");
	// // }
	// //
	// // if (tLPUWMasterMainDB.getState().equals("9") ||
	// // tLPUWMasterMainDB.getState().equals("4"))
	// // {
	// // textTag.add("AIUWSentence", "该项目变更不影响核保结论，该保单约定内容不变，保险合同继续有效。");
	// // }
	// // else
	// // {
	// // // textTag.add("AIUWSentence","该保单谢绝继续投保，请投保人尽快办理退保手续。");
	// // textTag.add("AIUWSentence", "该项目变更不影响核保结论，该保单内容不变，保险合同继续有效。");
	// // }
	// //取得保单有效日期
	// LCPolSchema tLCPolSchema = new LCPolSchema();
	// LCPolDB tLCPolDB = new LCPolDB();
	// LCPolSet tLCPolSet = new LCPolSet();
	// tLCPolDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLCPolSet = tLCPolDB.query();
	//
	// if (tLCPolSet.size() > 0)
	// {
	// tLCPolSchema = tLCPolSet.get(1);
	// textTag.add("AICValiDate", tLCPolSchema.getCValiDate());
	// }
	// else
	// {
	// // @@错误处理
	// CError.buildErr(this, "保单不存在,无法取得保单有效日期");
	//
	// return false;
	// }
	//
	// textTag.add("AIEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// return true;
	// }
	//
	// //被保险人退保（犹豫期退保、过失退保）数据采集
	// private boolean getDetailZT(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
	//
	// String sql =
	// "select sum(getmoney) from LJSgetendorse where endorsementno='" +
	// aLPEdorMainSchema.getEdorNo() + "' and FeeOperationType='" +
	// aLPEdorMainSchema.getEdorType() + "'";
	//
	// String aSql = sql + " and FeeFinaType in ('TF','TB')";
	// ExeSQL aExeSQL = new ExeSQL();
	// String tReturn = aExeSQL.getOneValue(aSql);
	//
	// double aGetMoney = 0;
	// double bGetMoney = 0;
	//
	// if ((tReturn == null) || StrTool.cTrim(tReturn).equals(""))
	// {
	// aGetMoney = 0;
	// }
	// else
	// {
	// aGetMoney = Double.parseDouble(tReturn);
	// }
	//
	// String bSql = sql + " and FeeFinaType='EY'";
	//
	// tReturn = aExeSQL.getOneValue(bSql);
	//
	// if ((tReturn == null) || StrTool.cTrim(tReturn).equals(""))
	// {
	// bGetMoney = 0;
	// }
	// else
	// {
	// bGetMoney = Double.parseDouble(tReturn);
	// }
	//
	// double GetMoney = 0;
	// GetMoney = aGetMoney + bGetMoney;
	//
	// if (aLPEdorMainSchema.getEdorType().equals("ZT"))
	// {
	// textTag.add("ZTTFMoney", aGetMoney);
	// textTag.add("ZTEYMoney", bGetMoney);
	// textTag.add("ZTMoney", GetMoney);
	// textTag.add("ZTEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	// textTag.add("ZTHandler", aLPEdorMainSchema.getOperator());
	// }
	// else if (aLPEdorMainSchema.getEdorType().equals("WT"))
	// {
	// textTag.add("WTTFMoney", aGetMoney);
	// textTag.add("WTEYMoney", bGetMoney);
	// textTag.add("WTMoney", GetMoney);
	// textTag.add("WTEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	// textTag.add("WTHandler", aLPEdorMainSchema.getOperator());
	// }
	// else if (aLPEdorMainSchema.getEdorType().equals("GT"))
	// {
	// textTag.add("GTTFMoney", aGetMoney);
	// textTag.add("GTEYMoney", bGetMoney);
	// textTag.add("GTMoney", GetMoney);
	// textTag.add("GTEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	// textTag.add("GTHandler", aLPEdorMainSchema.getOperator());
	// }
	//
	// return true;
	// }
	//
	// //红利领取方式变更的数据采集
	// private boolean getDetailBD(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPPolSchema tLPPolSchema = new LPPolSchema();
	// tLPPolSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPPolSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPPolSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPPolDB tLPPolDB = new LPPolDB();
	// tLPPolDB.setSchema(tLPPolSchema);
	//
	// if (!tLPPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// tLPPolSchema = tLPPolDB.getSchema();
	//
	// LPPolBL tLPPolBL = new LPPolBL();
	// tLPPolBL.queryLastLPPol(aLPEdorMainSchema, tLPPolSchema);
	//
	// LDCodeSchema OldLDCodeSchema = new LDCodeSchema();
	//
	// if ((tLPPolBL.getPayLocation() == null) ||
	// tLPPolBL.getPayLocation().equals(""))
	// {
	// OldLDCodeSchema.setCodeName("");
	// }
	// else
	// {
	// LDCodeDB OldLDCodeDB = new LDCodeDB();
	// OldLDCodeDB.setCodeType("livegetmode");
	// OldLDCodeDB.setCode(tLPPolBL.getBonusGetMode());
	//
	// if (!OldLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// OldLDCodeSchema.setSchema(OldLDCodeDB);
	// }
	//
	// LDCodeDB NewLDCodeDB = new LDCodeDB();
	// NewLDCodeDB.setCodeType("livegetmode");
	// NewLDCodeDB.setCode(tLPPolSchema.getBonusGetMode());
	//
	// if (!NewLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// //比较红利领取方式是否变化
	// if (!StrTool.cTrim(OldLDCodeSchema.getCodeName()).equals(StrTool.cTrim(
	// NewLDCodeDB.getCodeName())))
	// {
	// textTag.add("BDOldBonusGetMode", OldLDCodeSchema.getCodeName());
	// textTag.add("BDNewBonusGetMode", NewLDCodeDB.getCodeName());
	// }
	// else
	// {
	// textTag.add("BDOldBonusGetMode", "----");
	// textTag.add("BDNewBonusGetMode", "----");
	// }
	//
	// //*******yangzhao**
	// String trueDate;
	// trueDate = PubFun.calDate(tLPPolBL.getCValiDate(), 1, "Y",
	// tLPPolBL.getCValiDate());
	// logger.debug("QQQQQQQQQQQQQQQQ" + trueDate);
	//
	// //*****************
	// textTag.add("BDEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// return true;
	// }
	//
	// //***********yangzhao*********RP
	// //续保方式变更的数据采集
	// private boolean getDetailRP(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPPolSchema tLPPolSchema = new LPPolSchema();
	// tLPPolSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPPolSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPPolSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPPolDB tLPPolDB = new LPPolDB();
	// tLPPolDB.setSchema(tLPPolSchema);
	//
	// if (!tLPPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// tLPPolSchema = tLPPolDB.getSchema();
	//
	// LPPolBL tLPPolBL = new LPPolBL();
	// tLPPolBL.queryLastLPPol(aLPEdorMainSchema, tLPPolSchema);
	//
	// LDCodeSchema OldLDCodeSchema = new LDCodeSchema();
	//
	// if ((tLPPolBL.getPayLocation() == null) ||
	// tLPPolBL.getPayLocation().equals(""))
	// {
	// OldLDCodeSchema.setCodeName("");
	// }
	// else
	// {
	// LDCodeDB OldLDCodeDB = new LDCodeDB();
	// OldLDCodeDB.setCodeType("rnewflag");
	// OldLDCodeDB.setCode(String.valueOf(tLPPolBL.getRnewFlag()));
	//
	// if (!OldLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// OldLDCodeSchema.setSchema(OldLDCodeDB);
	// }
	//
	// LDCodeDB NewLDCodeDB = new LDCodeDB();
	// NewLDCodeDB.setCodeType("rnewflag");
	// NewLDCodeDB.setCode(String.valueOf(tLPPolSchema.getRnewFlag()));
	//
	// if (!NewLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// //比较续保方式是否变化
	// if (!StrTool.cTrim(OldLDCodeSchema.getCodeName()).equals(StrTool.cTrim(
	// NewLDCodeDB.getCodeName())))
	// {
	// textTag.add("RPOldRnewFlag", OldLDCodeSchema.getCodeName());
	// textTag.add("RPNewRnewFlag", NewLDCodeDB.getCodeName());
	// }
	// else
	// {
	// textTag.add("RPOldRnewFlag", "----");
	// textTag.add("RPNewRnewFlag", "----");
	// }
	//
	// //************yangzhao****
	// String strSql =
	// "select * from lmriskapp where riskcode in (select riskcode from lcpol
	// where
	// polno = '" +
	// aLPEdorMainSchema.getPolNo() + "')";
	// logger.debug(strSql);
	//
	// LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
	// LMRiskAppSet tSet = tLMRiskAppDB.executeQuery(strSql);
	// logger.debug("险种：" + tSet.get(1).getRiskName());
	// textTag.add("RPRiskName", tSet.get(1).getRiskName());
	//
	// //************************
	// textTag.add("RPEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// return true;
	// }
	//
	// //******************************
	// //收费方式（帐号）变更的数据采集
	// private boolean getDetailCC(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPPolSchema tLPPolSchema = new LPPolSchema();
	// tLPPolSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPPolSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPPolSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPPolDB tLPPolDB = new LPPolDB();
	// tLPPolDB.setSchema(tLPPolSchema);
	//
	// if (!tLPPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// tLPPolSchema = tLPPolDB.getSchema();
	//
	// LPPolBL tLPPolBL = new LPPolBL();
	// tLPPolBL.queryLastLPPol(aLPEdorMainSchema, tLPPolSchema);
	//
	// LDCodeSchema OldLDCodeSchema = new LDCodeSchema();
	//
	// if ((tLPPolBL.getPayLocation() == null) ||
	// tLPPolBL.getPayLocation().equals(""))
	// {
	// OldLDCodeSchema.setCodeName("");
	// }
	// else
	// {
	// LDCodeDB OldLDCodeDB = new LDCodeDB();
	// OldLDCodeDB.setCodeType("paylocation");
	// OldLDCodeDB.setCode(tLPPolBL.getPayLocation());
	//
	// if (!OldLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// OldLDCodeSchema.setSchema(OldLDCodeDB);
	// }
	//
	// LDCodeDB NewLDCodeDB = new LDCodeDB();
	// NewLDCodeDB.setCodeType("paylocation");
	// NewLDCodeDB.setCode(tLPPolSchema.getPayLocation());
	//
	// if (!NewLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// //比较收费方式是否变化
	// if (!StrTool.cTrim(OldLDCodeSchema.getCodeName()).equals(StrTool.cTrim(
	// NewLDCodeDB.getCodeName())))
	// {
	// textTag.add("CCOldPayLocation", OldLDCodeSchema.getCodeName());
	// textTag.add("CCNewPayLocation", NewLDCodeDB.getCodeName());
	// }
	// else
	// {
	// textTag.add("CCOldPayLocation", "----");
	// textTag.add("CCNewPayLocation", "----");
	// }
	//
	// //比较开户银行是否变化
	// LDCodeSchema tOldLDCodeSchema = new LDCodeSchema();
	//
	// if ((tLPPolBL.getBankCode() == null) ||
	// tLPPolBL.getBankCode().equals(""))
	// {
	// tOldLDCodeSchema.setCodeName("");
	// }
	// else
	// {
	// LDCodeDB tOldLDCodeDB = new LDCodeDB();
	// tOldLDCodeDB.setCodeType("bank");
	// tOldLDCodeDB.setCode(tLPPolBL.getBankCode());
	//
	// if (!tOldLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// tOldLDCodeSchema.setSchema(tOldLDCodeDB);
	// }
	//
	// LDCodeSchema tNewLDCodeSchema = new LDCodeSchema();
	//
	// if ((tLPPolSchema.getBankCode() == null) ||
	// tLPPolSchema.getBankCode().equals(""))
	// {
	// tNewLDCodeSchema.setCodeName("");
	// }
	// else
	// {
	// LDCodeDB tNewLDCodeDB = new LDCodeDB();
	// tNewLDCodeDB.setCodeType("bank");
	// tNewLDCodeDB.setCode(tLPPolSchema.getBankCode());
	//
	// if (!tNewLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// tNewLDCodeSchema.setSchema(tNewLDCodeDB);
	// }
	//
	// if (!StrTool.cTrim(tOldLDCodeSchema.getCodeName()).equals(StrTool.
	// cTrim(
	// tNewLDCodeSchema.getCodeName())))
	// {
	// textTag.add("CCOldBankName", tOldLDCodeSchema.getCodeName());
	// textTag.add("CCNewBankName", tNewLDCodeSchema.getCodeName());
	// }
	// else
	// {
	// textTag.add("CCOldBankName", "----");
	// textTag.add("CCNewBankName", "----");
	// }
	//
	// // //比较银行编码是否变化
	// // if
	// (!StrTool.cTrim(tLPPolBL.getBankCode()).equals(StrTool.cTrim(tLPPolSchema.getBankCode())))
	// // {
	// // textTag.add("OldBankCode",tLPPolBL.getBankCode());
	// // textTag.add("NewBankCode",tLPPolSchema.getBankCode());
	// // }
	// // else
	// // {
	// // textTag.add("OldBankCode","----");
	// // textTag.add("NewBankCode","----");
	// // }
	// //比较银行帐号是否变化
	// if (!StrTool.cTrim(tLPPolBL.getBankAccNo()).equals(StrTool.cTrim(
	// tLPPolSchema.getBankAccNo())))
	// {
	// textTag.add("CCOldBankAccNo", tLPPolBL.getBankAccNo());
	// textTag.add("CCNewBankAccNo", tLPPolSchema.getBankAccNo());
	// }
	// else
	// {
	// textTag.add("CCOldBankAccNo", "----");
	// textTag.add("CCNewBankAccNo", "----");
	// }
	//
	// //比较银行帐户是否变化
	// if (!StrTool.cTrim(tLPPolBL.getAccName()).equals(StrTool.cTrim(
	// tLPPolSchema.getAccName())))
	// {
	// textTag.add("CCOldAccName", tLPPolBL.getAccName());
	// textTag.add("CCNewAccName", tLPPolSchema.getAccName());
	// }
	// else
	// {
	// textTag.add("CCOldAccName", "----");
	// textTag.add("CCNewAccName", "----");
	// }
	//
	// textTag.add("CCEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// return true;
	// }
	//
	// //保单遗失补发的数据采集
	// private boolean getDetailLR(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// String tsql;
	// String SumGetMoney;
	// tsql = "select sum(GetMoney) from LJSGetEndorse where EndorsementNo='" +
	// aLPEdorMainSchema.getEdorNo() + "' and PolNo='" +
	// aLPEdorMainSchema.getPolNo() + "' and FeeOperationType='" +
	// aLPEdorMainSchema.getEdorType() + "'";
	//
	// ExeSQL tExeSQL = new ExeSQL();
	// SumGetMoney = tExeSQL.getOneValue(tsql);
	// textTag.add("LRGetMoney", SumGetMoney);
	// textTag.add("LREdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// //获取补打次数
	// LPPolBL tLPPolBL = new LPPolBL();
	// tLPPolBL.queryLPPol(aLPEdorMainSchema);
	// logger.debug("Last LostTimes: " + tLPPolBL.getLostTimes());
	//
	// textTag.add("LRCount", (tLPPolBL.getLostTimes() + 1));
	//
	// return true;
	// }
	//
	// //更换投保人的数据采集
	// private boolean getDetailIA(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPAppntTraceSchema tLPAppntTraceSchema = new LPAppntTraceSchema();
	// tLPAppntTraceSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPAppntTraceSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPAppntTraceSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	// tLPAppntTraceSchema.setPolType("1");
	//
	// LPAppntTraceDB tLPAppntTraceDB = new LPAppntTraceDB();
	// tLPAppntTraceDB.setSchema(tLPAppntTraceSchema);
	//
	// LPAppntTraceSet tLPAppntTraceSet = new LPAppntTraceSet();
	// tLPAppntTraceSet.set(tLPAppntTraceDB.query());
	//
	// if (tLPAppntTraceSet.size() == 0)
	// {
	// return false;
	// }
	// else
	// {
	// LPAppntTraceSchema aLPAppntTraceSchema;
	// LDPersonDB OldLDPersonDB;
	// LDPersonDB NewLDPersonDB;
	// LPAppntIndDB OldLPAppntIndDB;
	// LPAppntIndDB NewLPAppntIndDB;
	//
	// // tlistTable = new ListTable();
	// // tlistTable.setName("IA");
	// for (int i = 1; i <= tLPAppntTraceSet.size(); i++)
	// {
	// aLPAppntTraceSchema = new LPAppntTraceSchema();
	// aLPAppntTraceSchema = tLPAppntTraceSet.get(i);
	// OldLDPersonDB = new LDPersonDB();
	// NewLDPersonDB = new LDPersonDB();
	// OldLDPersonDB.setCustomerNo(aLPAppntTraceSchema.getOldAppntNo());
	// NewLDPersonDB.setCustomerNo(aLPAppntTraceSchema.getNewAppntNo());
	//
	// if (!OldLDPersonDB.getInfo())
	// {
	// return false;
	// }
	//
	// if (!NewLDPersonDB.getInfo())
	// {
	// return false;
	// }
	//
	// // OldLPAppntIndDB = new LPAppntIndDB();
	// NewLPAppntIndDB = new LPAppntIndDB();
	//
	// // OldLPAppntIndDB.setEdorNo(aLPAppntTraceSchema.getEdorNo());
	// // OldLPAppntIndDB.setPolNo(aLPAppntTraceSchema.getPolNo());
	// // OldLPAppntIndDB.setEdorType(aLPAppntTraceSchema.getEdorType());
	// // OldLPAppntIndDB.setCustomerNo(aLPAppntTraceSchema.getOldAppntNo());
	// NewLPAppntIndDB.setEdorNo(aLPAppntTraceSchema.getEdorNo());
	// NewLPAppntIndDB.setPolNo(aLPAppntTraceSchema.getPolNo());
	// NewLPAppntIndDB.setEdorType(aLPAppntTraceSchema.getEdorType());
	// NewLPAppntIndDB.setCustomerNo(aLPAppntTraceSchema.getNewAppntNo());
	//
	// // if (!OldLPAppntIndDB.getInfo())//注意
	// // return false; //注意
	// if (!NewLPAppntIndDB.getInfo())
	// {
	// return false;
	// }
	//
	// LPAppntIndSchema NewLPAppntIndSchema = new LPAppntIndSchema();
	//
	// // NewLPAppntIndSchema = OldLPAppntIndDB.getSchema();
	// NewLPAppntIndSchema.setEdorNo(aLPAppntTraceSchema.getEdorNo());
	// NewLPAppntIndSchema.setPolNo(aLPAppntTraceSchema.getPolNo());
	// NewLPAppntIndSchema.setEdorType(aLPAppntTraceSchema.getEdorType());
	// NewLPAppntIndSchema.setCustomerNo(aLPAppntTraceSchema.
	// getOldAppntNo());
	//
	// LPAppntIndBL OldLPAppntIndBL = new LPAppntIndBL();
	// OldLPAppntIndBL.queryLastLPAppntInd(aLPEdorMainSchema,
	// NewLPAppntIndSchema);
	//
	// LDCodeDB OldLDCodeDB = new LDCodeDB();
	// OldLDCodeDB.setCodeType("relation");
	// OldLDCodeDB.setCode(OldLPAppntIndBL.getRelationToInsured());
	//
	// OldLDCodeDB.getInfo();
	//
	// // if (!OldLDCodeDB.getInfo())
	// // return false;
	// LDCodeDB NewLDCodeDB = new LDCodeDB();
	// NewLDCodeDB.setCodeType("relation");
	// NewLDCodeDB.setCode(NewLPAppntIndDB.getRelationToInsured());
	//
	// NewLDCodeDB.getInfo();
	//
	// // if (!NewLDCodeDB.getInfo())
	// // return false;
	// // strArr = new String[4];
	// // if
	// (!StrTool.cTrim(aLPAppntTraceSchema.getOldAppntNo()).equals(StrTool.cTrim(aLPAppntTraceSchema.getNewAppntNo())))
	// // {
	// // strArr[1]=aLPAppntTraceSchema.getOldAppntNo();
	// // strArr[3]=aLPAppntTraceSchema.getNewAppntNo();
	// // strArr[0]=OldLDPersonDB.getName();
	// // strArr[2]=NewLDPersonDB.getName();
	// // }
	// // else
	// // {
	// // strArr[0]="----";
	// // strArr[1]="----";
	// // strArr[2]="----";
	// // strArr[3]="----";
	// // }
	// // tlistTable.add(strArr);
	// //Modify by Minim for use NewLPAppntIndDB substitute NewLDPersonDB
	// //取得新旧投保人客户号
	// textTag.add("IAOldCustomerNo", OldLDPersonDB.getCustomerNo());
	// textTag.add("IANewCustomerNo", NewLPAppntIndDB.getCustomerNo());
	//
	// //取得新旧投保人姓名
	// textTag.add("IAOldName", OldLDPersonDB.getName());
	// textTag.add("IANewName", NewLPAppntIndDB.getName());
	//
	// //取得新旧投保人性别
	// LDCodeDB OldSexLDCodeDB = new LDCodeDB();
	// OldSexLDCodeDB.setCodeType("sex");
	// OldSexLDCodeDB.setCode(OldLDPersonDB.getSex());
	// OldSexLDCodeDB.getInfo();
	//
	// LDCodeDB NewSexLDCodeDB = new LDCodeDB();
	// NewSexLDCodeDB.setCodeType("sex");
	// NewSexLDCodeDB.setCode(NewLPAppntIndDB.getSex());
	// NewSexLDCodeDB.getInfo();
	//
	// // if (!NewLDCodeDB.getInfo())
	// // return false;
	// textTag.add("IAOldSex", OldSexLDCodeDB.getCodeName());
	// textTag.add("IANewSex", NewSexLDCodeDB.getCodeName());
	//
	// //取得新旧投保人出生年月
	// textTag.add("IAOldBirthday", OldLDPersonDB.getBirthday());
	// textTag.add("IANewBirthday", NewLPAppntIndDB.getBirthday());
	//
	// //取得新旧投保人证件号码
	// textTag.add("IAOldIDNo", OldLDPersonDB.getIDNo());
	// textTag.add("IANewIDNo", NewLPAppntIndDB.getIDNo());
	//
	// //取得新旧投保人与被保险人关系
	// textTag.add("IAOldRelationToInsured", OldLDCodeDB.getCodeName());
	// textTag.add("IANewRelationToInsured", NewLDCodeDB.getCodeName());
	//
	// //取得新旧投保人电话
	// textTag.add("IAOldPhone", OldLDPersonDB.getPhone());
	// textTag.add("IANewPhone", NewLPAppntIndDB.getPhone());
	//
	// //取得新旧投保人手机号码
	// textTag.add("IAOldMobile", OldLDPersonDB.getMobile());
	// textTag.add("IANewMobile", NewLPAppntIndDB.getMobile());
	//
	// //取得新旧投保人邮编
	// textTag.add("IAOldZipCode", OldLDPersonDB.getZipCode());
	// textTag.add("IANewZipCode", NewLPAppntIndDB.getZipCode());
	//
	// //取得新旧投保人通讯地址
	// textTag.add("IAOldPostalAddress",
	// OldLDPersonDB.getPostalAddress());
	// textTag.add("IANewPostalAddress",
	// NewLPAppntIndDB.getPostalAddress());
	// }
	//
	// // strArr = new String[4];
	// // strArr[0] = "原姓名";
	// // strArr[1] = "原客户号";
	// // strArr[2] = "现姓名";
	// // strArr[3] = "现客户号";
	// }
	//
	// textTag.add("IAEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// // myDocument = xmlexport.addListTable(tlistTable, strArr);
	// return true;
	// }
	//
	// //复效、还款、还垫交保费数据采集
	// private boolean getDetailRE(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
	//
	// String sql =
	// "select sum(getmoney) from LJSgetendorse where endorsementno='" +
	// aLPEdorMainSchema.getEdorNo() + "' and PolNo='" +
	// aLPEdorMainSchema.getPolNo() + "' and FeeOperationType='" +
	// aLPEdorMainSchema.getEdorType() + "'";
	//
	// String aSql = "";
	//
	// if (aLPEdorMainSchema.getEdorType().equals("RF"))
	// {
	// aSql = sql + " and FeeFinaType='HK'";
	// }
	// else if (aLPEdorMainSchema.getEdorType().equals("TR"))
	// {
	// aSql = sql + " and FeeFinaType='HD'";
	// }
	// else
	// {
	// aSql = sql + " and FeeFinaType='BF'";
	// }
	//
	// ExeSQL aExeSQL = new ExeSQL();
	// String tReturn = aExeSQL.getOneValue(aSql);
	//
	// double BFGetMoney = 0;
	// double LXGetMoney = 0;
	//
	// if ((tReturn == null) || StrTool.cTrim(tReturn).equals(""))
	// {
	// BFGetMoney = 0;
	// }
	// else
	// {
	// BFGetMoney = Double.parseDouble(tReturn);
	// }
	//
	// String bSql = sql + " and FeeFinaType='LX'";
	//
	// tReturn = aExeSQL.getOneValue(bSql);
	//
	// if ((tReturn == null) || StrTool.cTrim(tReturn).equals(""))
	// {
	// LXGetMoney = 0;
	// }
	// else
	// {
	// LXGetMoney = Double.parseDouble(tReturn);
	// }
	//
	// double SumGetMoney = 0;
	// SumGetMoney = BFGetMoney + LXGetMoney;
	//
	// if (aLPEdorMainSchema.getEdorType().equals("RE"))
	// {
	// textTag.add("REBFGetMoney", BFGetMoney);
	// textTag.add("RELXGetMoney", LXGetMoney);
	// textTag.add("RESumGetMoney", SumGetMoney);
	// textTag.add("REEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	// }
	// else if (aLPEdorMainSchema.getEdorType().equals("RF"))
	// {
	// textTag.add("RFBFGetMoney", BFGetMoney);
	// textTag.add("RFLXGetMoney", LXGetMoney);
	// textTag.add("RFSumGetMoney", SumGetMoney);
	// textTag.add("RFEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// LOLoanDB tLOLoanDB = new LOLoanDB();
	// tLOLoanDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLOLoanDB.setPayOffFlag("0");
	//
	// LOLoanSet tLOLoanSet = tLOLoanDB.query();
	//
	// double leaveMoney = 0;
	//
	// for (int i = 0; i < tLOLoanSet.size(); i++)
	// {
	// leaveMoney = leaveMoney +
	// tLOLoanSet.get(i + 1).getLeaveMoney();
	// leaveMoney = PubFun.setPrecision(leaveMoney, "0.00");
	// }
	//
	// leaveMoney = PubFun.setPrecision((leaveMoney - BFGetMoney), "0.00");
	// logger.debug("leaveMoney: " + leaveMoney);
	//
	// if (leaveMoney != 0)
	// {
	// xmlexport.addDisplayControl("displayRFLeave");
	//
	// // String s = "本次还款总额为 " + SumGetMoney + " 元，还有借款本金 " + leaveMoney + "
	// 元（利息另计）未还清。";
	// textTag.add("RFTotalMoney", SumGetMoney);
	// textTag.add("RFLeaveMoney", leaveMoney);
	// }
	// }
	// else if (aLPEdorMainSchema.getEdorType().equals("TR"))
	// {
	// textTag.add("TRBFGetMoney", BFGetMoney);
	// textTag.add("TRLXGetMoney", LXGetMoney);
	// textTag.add("TRSumGetMoney", SumGetMoney);
	// textTag.add("TREdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	// }
	//
	// return true;
	// }
	//
	// //部分退保、借款数据采集
	// private boolean getDetailPT(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// /*
	// logger.debug("------------PT Detail add contents--------------");
	// // logger.debug("RiskCode :" + aLPEdorMainSchema.getRiskcode());
	// String strSql = "select * from lmriskapp where riskcode in (select
	// riskcode
	// from lcpol where polno = '" + aLPEdorMainSchema.getPolNo() + "')";
	// logger.debug(strSql);
	// LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
	// LMRiskAppSet tSet = tLMRiskAppDB.executeQuery(strSql);
	// logger.debug("险种：" + tSet.get(1).getRiskName());
	// LPPolBL tLastLPPolBL = new LPPolBL();
	// LPPolBL tLPPolBL = new LPPolBL();
	// LPPolSchema nullSchema = new LPPolSchema();
	// tLastLPPolBL.queryLastLPPol(aLPEdorMainSchema,nullSchema);
	// tLPPolBL.queryLPPol(aLPEdorMainSchema);
	// logger.debug("原保险金额 :" + tLastLPPolBL.getAmnt());
	// logger.debug("原保费：" + tLastLPPolBL.getPrem());
	// logger.debug("保险金额 :" + tLPPolBL.getAmnt());
	// logger.debug("保费：" + tLPPolBL.getPrem());
	//
	// */
	// LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
	//
	// String sql =
	// "select sum(getmoney) from LJSgetendorse where endorsementno='" +
	// aLPEdorMainSchema.getEdorNo() + "' and PolNo='" +
	// aLPEdorMainSchema.getPolNo() + "' and FeeOperationType='" +
	// aLPEdorMainSchema.getEdorType() + "'";
	//
	// ExeSQL aExeSQL = new ExeSQL();
	// String tReturn = aExeSQL.getOneValue(sql);
	//
	// double GetMoney = 0;
	// double GetMoneySL = 0;
	//
	// if ((tReturn == null) || StrTool.cTrim(tReturn).equals(""))
	// {
	// GetMoney = 0;
	// }
	// else
	// {
	// GetMoney = Double.parseDouble(tReturn);
	// }
	//
	// if (aLPEdorMainSchema.getEdorType().equals("PT"))
	// {
	// textTag.add("PTMoney", GetMoney);
	// textTag.add("PTEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// logger.debug(
	// "------------PT Detail add contents--------------");
	//
	// // logger.debug("RiskCode :" + aLPEdorMainSchema.getRiskcode());
	// String strSql =
	// "select * from lmriskapp where riskcode in (select riskcode from lcpol
	// where
	// polno = '" +
	// aLPEdorMainSchema.getPolNo() + "')";
	// logger.debug(strSql);
	//
	// LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
	// LMRiskAppSet tSet = tLMRiskAppDB.executeQuery(strSql);
	// logger.debug("险种：" + tSet.get(1).getRiskName());
	// textTag.add("PTRiskName", tSet.get(1).getRiskName());
	//
	// LPPolBL tLastLPPolBL = new LPPolBL();
	// LPPolBL tLPPolBL = new LPPolBL();
	// LPPolSchema nullSchema = new LPPolSchema();
	// tLastLPPolBL.queryLastLPPol(aLPEdorMainSchema, nullSchema);
	// tLPPolBL.queryLPPol(aLPEdorMainSchema);
	// logger.debug("原保险金额 :" + tLastLPPolBL.getAmnt());
	// logger.debug("原保费：" + tLastLPPolBL.getPrem());
	// logger.debug("保险金额 :" + tLPPolBL.getAmnt());
	// logger.debug("保费：" + tLPPolBL.getPrem());
	// textTag.add("PTOldAmnt", tLastLPPolBL.getAmnt() + "元");
	// textTag.add("PTOldPrem", tLastLPPolBL.getPrem() + "元");
	// textTag.add("PTNewAmnt", tLPPolBL.getAmnt() + "元");
	// textTag.add("PTNewPrem", tLPPolBL.getPrem() + "元");
	//
	// LPPolSet tAllPolSet = tLPPolBL.queryAllLPPol2(aLPEdorMainSchema);
	// double allprem = 0.0;
	//
	// for (int i = 1; i <= tAllPolSet.size(); i++)
	// {
	// //加入判断附加险是否失效 add by JL at 204-8-26
	// if (!tAllPolSet.get(i).getPolState().equals("02020100"))
	// {
	// //保单不失效
	// logger.debug("保单保费：" + tAllPolSet.get(i).getPrem());
	// allprem += tAllPolSet.get(i).getPrem();
	// }
	// else
	// {
	// logger.debug("保单" + tAllPolSet.get(i).getPolNo() +
	// "失效");
	// }
	// }
	//
	// textTag.add("PTPremSum", allprem + "元");
	// }
	// else if (aLPEdorMainSchema.getEdorType().equals("LF"))
	// {
	// String sqlLF =
	// "select sum(getmoney) from LJSgetendorse where endorsementno='" +
	// aLPEdorMainSchema.getEdorNo() + "' and PolNo='" +
	// aLPEdorMainSchema.getPolNo() + "' and FeeOperationType='" +
	// aLPEdorMainSchema.getEdorType() +
	// "' and FeeFinaType='YHSL'";
	//
	// tReturn = aExeSQL.getOneValue(sqlLF);
	//
	// if ((tReturn == null) || StrTool.cTrim(tReturn).equals(""))
	// {
	// GetMoneySL = 0;
	// }
	// else
	// {
	// GetMoneySL = Double.parseDouble(tReturn);
	// }
	//
	// textTag.add("LFCashValue", EdorCalZT.getCashValue(aLPEdorMainSchema));
	// textTag.add("LFBorrowMoney", GetMoney - GetMoneySL);
	// textTag.add("LFInterestDate",
	// PubFun.calDate(aLPEdorMainSchema.getEdorValiDate(), 1,
	// "D",
	// aLPEdorMainSchema.getEdorValiDate()));
	// textTag.add("LFTax", "0");
	// textTag.add("LFMoney", GetMoney - (GetMoneySL * 2));
	//
	// // textTag.add("LFEdorValiDate",aLPEdorMainSchema.getEdorValiDate());
	// }
	//
	// return true;
	// }
	//
	// //新增附加险
	// private boolean getDetailNS(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// logger.debug("Start Print NS");
	//
	// LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
	// tLPEdorMainDB.setSchema(aLPEdorMainSchema);
	//
	// LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
	//
	// if (tLPEdorMainSet.size() == 0)
	// {
	// return false;
	// }
	//
	// tlistTable = new ListTable();
	// tlistTable.setName("NS");
	//
	// //申请确认时进行打印
	// if (tLPEdorMainSet.get(1).getEdorState().equals("2"))
	// {
	// //从批改补退费表中获取新增的保单号
	// // LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
	// // tLJSGetEndorseDB.setEndorsementNo(aLPEdorMainSchema.getEdorNo());
	// // tLJSGetEndorseDB.setFeeOperationType(aLPEdorMainSchema.getEdorType());
	// // LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();
	// //
	// // for (int i=0; i<tLJSGetEndorseSet.size(); i++) {
	// //获取新增的保单
	// LCPolDB tLCPolDB2 = new LCPolDB();
	// String strSql = "select * from lcpol where mainpolno='" +
	// aLPEdorMainSchema.getPolNo() + "' and appflag='2'";
	// LCPolSet tLCPolSet = tLCPolDB2.executeQuery(strSql);
	//
	// for (int j = 0; j < tLCPolSet.size(); j++)
	// {
	// LCPolDB tLCPolDB = new LCPolDB();
	// tLCPolDB.setSchema(tLCPolSet.get(j + 1).getSchema());
	//
	// //从保单表获取险种信息
	// // LCPolDB tLCPolDB = new LCPolDB();
	// // tLCPolDB.setPolNo(tLJSGetEndorseSet.get(i+1).getPolNo());
	// // if (!tLCPolDB.getInfo()) return false;
	// // logger.debug("Line" + i + ": " + tLCPolDB.getRiskCode());
	// strArr = new String[5];
	// strArr[0] = tLCPolDB.getRiskCode();
	// strArr[2] = String.valueOf(tLCPolDB.getAmnt());
	// strArr[3] = String.valueOf(tLCPolDB.getPrem());
	//
	// if (tLCPolDB.getInsuYearFlag().equals("A"))
	// {
	// if (tLCPolDB.getInsuYear() == 1000)
	// {
	// strArr[4] = "终身";
	// }
	// else
	// {
	// strArr[4] = tLCPolDB.getInsuYear() + "岁";
	// }
	// }
	// else
	// {
	// strArr[4] = tLCPolDB.getInsuYear() + "年";
	// }
	//
	// //从险种定义表获取险种名称
	// LMRiskDB tLMRiskDB = new LMRiskDB();
	// tLMRiskDB.setRiskCode(tLCPolDB.getRiskCode());
	//
	// if (!tLMRiskDB.getInfo())
	// {
	// return false;
	// }
	//
	// strArr[1] = tLMRiskDB.getRiskName();
	//
	// //增加一行
	// tlistTable.add(strArr);
	// }
	// }
	// //保全确认时进行打印
	// else if (tLPEdorMainSet.get(1).getEdorState().equals("0"))
	// {
	// }
	//
	// String[] strArr =
	// {
	// "险种代码", "险种名称", "保额", "保费", "保险期间"};
	// myDocument = xmlexport.addListTable(tlistTable, strArr);
	//
	// textTag.add("NSEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// return true;
	// }
	//
	// //交费间隔变更的数据采集
	// private boolean getDetailPC(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// //获取原保单表数据
	// LCPolDB tLCPolDB = new LCPolDB();
	// tLCPolDB.setPolNo(aLPEdorMainSchema.getPolNo());
	//
	// if (!tLCPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// switch (tLCPolDB.getPayIntv())
	// {
	// case 1:
	// textTag.add("PCOldPayIntv", SysConst.PayIntvMonth);
	//
	// break;
	//
	// case 3:
	// textTag.add("PCOldPayIntv", SysConst.PayIntvQuarter);
	//
	// break;
	//
	// case 6:
	// textTag.add("PCOldPayIntv", SysConst.PayIntvHalfYear);
	//
	// break;
	//
	// case 12:
	// textTag.add("PCOldPayIntv", SysConst.PayIntvYear);
	//
	// break;
	// }
	//
	// //获取保全保单表数据
	// LPPolDB tLPPolDB = new LPPolDB();
	// tLPPolDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPPolDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPPolDB.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// if (!tLPPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// switch (tLPPolDB.getPayIntv())
	// {
	// case 1:
	// textTag.add("PCNewPayIntv", SysConst.PayIntvMonth);
	//
	// break;
	//
	// case 3:
	// textTag.add("PCNewPayIntv", SysConst.PayIntvQuarter);
	//
	// break;
	//
	// case 6:
	// textTag.add("PCNewPayIntv", SysConst.PayIntvHalfYear);
	//
	// break;
	//
	// case 12:
	// textTag.add("PCNewPayIntv", SysConst.PayIntvYear);
	//
	// break;
	// }
	//
	// //如果变更为银行划帐方式，显示银行账户信息
	// if (!tLCPolDB.getPayLocation().equals("0") &&
	// tLPPolDB.getPayLocation().equals("0"))
	// {
	// xmlexport.addDisplayControl("displayPCPayLocation");
	//
	// LDCodeDB tOldLDCodeDB = new LDCodeDB();
	// tOldLDCodeDB.setCodeType("bank");
	// tOldLDCodeDB.setCode(tLPPolDB.getBankCode());
	//
	// if (!tOldLDCodeDB.getInfo())
	// {
	// return false;
	// }
	//
	// textTag.add("PCNewBankName", tOldLDCodeDB.getCodeName());
	// textTag.add("PCNewBankAccNo", tLPPolDB.getBankAccNo());
	// textTag.add("PCNewAccName", tLPPolDB.getAccName());
	// }
	//
	// textTag.add("PCEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// //获取所有险种信息
	// LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
	// tLPEdorMainDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPEdorMainDB.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
	//
	// for (int i = 0; i < tLPEdorMainSet.size(); i++)
	// {
	// aLPEdorMainSchema = tLPEdorMainSet.get(i + 1);
	//
	// //获取原保单表数据
	// tLCPolDB = new LCPolDB();
	// tLCPolDB.setPolNo(aLPEdorMainSchema.getPolNo());
	//
	// if (!tLCPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// //从险种定义表获取险种名称
	// LMRiskDB tLMRiskDB = new LMRiskDB();
	// tLMRiskDB.setRiskCode(tLCPolDB.getRiskCode());
	//
	// if (!tLMRiskDB.getInfo())
	// {
	// return false;
	// }
	//
	// //获取保全保单表数据
	// tLPPolDB = new LPPolDB();
	// tLPPolDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPPolDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPPolDB.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// if (!tLPPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// if ((tLCPolDB.getPrem() == tLPPolDB.getPrem()) &&
	// (tLCPolDB.getAmnt() == tLPPolDB.getAmnt()))
	// {
	// continue;
	// }
	//
	// xmlexport.addDisplayControl("displayPCRisk" + i);
	// textTag.add("PCRisk" + i, tLMRiskDB.getRiskName());
	//
	// textTag.add("PCOldPrem" + i,
	// new DecimalFormat("0.00").format(tLCPolDB.getPrem()) +
	// " 元");
	// textTag.add("PCOldAmnt" + i,
	// new DecimalFormat("0.00").format(tLCPolDB.getAmnt()) +
	// " 元");
	//
	// textTag.add("PCNewPrem" + i,
	// new DecimalFormat("0.00").format(tLPPolDB.getPrem()) +
	// " 元");
	// textTag.add("PCNewAmnt" + i,
	// new DecimalFormat("0.00").format(tLPPolDB.getAmnt()) +
	// " 元");
	// }
	//
	// // if (aLPEdorMainSchema.getEdorState().equals("2")) {
	// // LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
	// // tLJSGetEndorseDB.setEndorsementNo(aLPEdorMainSchema.getEdorNo());
	// // tLJSGetEndorseDB.setFeeOperationType(aLPEdorMainSchema.getEdorType());
	// // tLJSGetEndorseDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// // LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();
	// // if (tLJSGetEndorseSet.size() > 0) {
	// // textTag.add("PCMoney", tLJSGetEndorseSet.get(1).getGetMoney());
	// // }
	// // else {
	// // textTag.add("PCMoney", "0");
	// // }
	// // }
	// // else if (aLPEdorMainSchema.getEdorState().equals("0")) {
	// //
	// // }
	// return true;
	// }
	//
	// //职业类别变更的数据采集
	// private boolean getDetailIO(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// //获取被保人信息
	// LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
	// tLPInsuredSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPInsuredSchema.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLPInsuredSchema.setCustomerNo(aLPEdorMainSchema.getInsuredNo());
	// tLPInsuredSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPInsuredDB tLPInsuredDB = new LPInsuredDB();
	// tLPInsuredDB.setSchema(tLPInsuredSchema);
	//
	// if (!tLPInsuredDB.getInfo())
	// {
	// return false;
	// }
	//
	// tLPInsuredSchema = tLPInsuredDB.getSchema();
	//
	// LPInsuredBL tLPInsuredBL = new LPInsuredBL();
	// tLPInsuredBL.queryLastLPInsured(aLPEdorMainSchema, tLPInsuredSchema);
	//
	// //比较被保人职业代码是否变化
	// LDOccupationDB OldLDOccupationDB = new LDOccupationDB();
	// OldLDOccupationDB.setOccupationCode(tLPInsuredBL.getOccupationCode());
	// OldLDOccupationDB.getInfo();
	//
	// LDOccupationDB NewLDOccupationDB = new LDOccupationDB();
	// NewLDOccupationDB.setOccupationCode(tLPInsuredSchema.getOccupationCode());
	// NewLDOccupationDB.getInfo();
	//
	// textTag.add("IOOldOccupationCode",
	// OldLDOccupationDB.getOccupationName());
	// textTag.add("IONewOccupationCode",
	// NewLDOccupationDB.getOccupationName());
	//
	// textTag.add("IOOldOccupationType",
	// OldLDOccupationDB.getOccupationType());
	// textTag.add("IONewOccupationType",
	// NewLDOccupationDB.getOccupationType());
	//
	// textTag.add("IOEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// //获取所有险种信息
	// //Modify by JL at 2004-11-10,
	// //为了在生成打印数据时使之适应不生成附加险的批改主表数据的情况
	// LPPolDB tLPPolDB = new LPPolDB();
	// tLPPolDB.setMainPolNo(aLPEdorMainSchema.getPolNo());
	// tLPPolDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPPolDB.setEdorType(aLPEdorMainSchema.getEdorType());
	// LPPolSet tLPPolSet = new LPPolSet();
	// tLPPolSet = tLPPolDB.query();
	// //LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
	// //tLPEdorMainDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// //tLPEdorMainDB.setEdorType(aLPEdorMainSchema.getEdorType());
	// //LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
	// LPPolSchema tLPPolSchema = new LPPolSchema();
	// for (int i = 0; i < tLPPolSet.size(); i++)
	// {
	// //aLPEdorMainSchema = tLPEdorMainSet.get(i + 1);
	// tLPPolSchema = tLPPolSet.get(i + 1);
	// //获取原保单表数据
	// LCPolDB tLCPolDB = new LCPolDB();
	// tLCPolDB.setPolNo(tLPPolSchema.getPolNo());
	//
	// if (!tLCPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// //从险种定义表获取险种名称
	// LMRiskDB tLMRiskDB = new LMRiskDB();
	// tLMRiskDB.setRiskCode(tLCPolDB.getRiskCode());
	//
	// if (!tLMRiskDB.getInfo())
	// {
	// return false;
	// }
	//
	// //获取保全保单表数据
	// //LPPolBL tLPPolBL = new LPPolBL();
	// //if (!tLPPolBL.queryLPPol(aLPEdorMainSchema)) {
	// // return false;
	// //}
	//
	// if ((tLCPolDB.getPrem() == tLPPolSchema.getPrem()) &&
	// (tLCPolDB.getAmnt() == tLPPolSchema.getAmnt()))
	// {
	// continue;
	// }
	//
	// xmlexport.addDisplayControl("displayIORisk" + i);
	// textTag.add("IORisk" + i, tLMRiskDB.getRiskName());
	//
	// textTag.add("IOOldPrem" + i,
	// new DecimalFormat("0.00").format(tLCPolDB.getPrem()) +
	// " 元");
	// textTag.add("IOOldAmnt" + i,
	// new DecimalFormat("0.00").format(tLCPolDB.getAmnt()) +
	// " 元");
	//
	// textTag.add("IONewPrem" + i,
	// new DecimalFormat("0.00").format(tLPPolSchema.getPrem()) +
	// " 元");
	// textTag.add("IONewAmnt" + i,
	// new DecimalFormat("0.00").format(tLPPolSchema.getAmnt()) +
	// " 元");
	// }
	//
	// return true;
	// }
	//
	// //保单迁出变更的数据采集
	// private boolean getDetailMO(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPMoveSchema tLPMoveSchema = new LPMoveSchema();
	// tLPMoveSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
	// tLPMoveSchema.setPolNoOld(aLPEdorMainSchema.getPolNo());
	// tLPMoveSchema.setEdorType(aLPEdorMainSchema.getEdorType());
	//
	// LPMoveDB tLPMoveDB = new LPMoveDB();
	// tLPMoveDB.setSchema(tLPMoveSchema);
	//
	// if (!tLPMoveDB.getInfo())
	// {
	// return false;
	// }
	//
	// textTag.add("MOOldManageCom", tLPMoveDB.getManageComOld());
	// textTag.add("MOOldManageName", tLPMoveDB.getOldCoName());
	//
	// textTag.add("MONewManageCom", tLPMoveDB.getManageComNew());
	// textTag.add("MONewManageName", tLPMoveDB.getNewCoName());
	//
	// textTag.add("MOEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// return true;
	// }
	//
	// //保单迁出变更的数据采集
	// private boolean getDetailMI(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LPMoveDB tLPMoveDB = new LPMoveDB();
	// String strSql = "select * from lpmove where polnoold='" +
	// aLPEdorMainSchema.getPolNo() +
	// "' and edortype='MO' and indate is null and outdate is not null";
	// logger.debug("strSql: " + strSql);
	//
	// LPMoveSet tLPMoveSet = tLPMoveDB.executeQuery(strSql);
	//
	// if (tLPMoveSet.size() == 0)
	// {
	// return false;
	// }
	//
	// LPMoveSchema tLPMoveSchema = tLPMoveSet.get(1);
	//
	// textTag.add("MIOldManageCom", tLPMoveSchema.getManageComOld());
	// textTag.add("MIOldManageName", tLPMoveSchema.getOldCoName());
	//
	// textTag.add("MINewManageCom", tLPMoveSchema.getManageComNew());
	// textTag.add("MINewManageName", tLPMoveSchema.getNewCoName());
	//
	// textTag.add("MIEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// return true;
	// }
	//
	// //附加险增额变更的数据采集
	// private boolean getDetailSA(LPEdorMainSchema aLPEdorMainSchema)
	// {
	// LCPolDB tLCPolDB = new LCPolDB();
	// tLCPolDB.setPolNo(aLPEdorMainSchema.getPolNo());
	// tLCPolDB.getInfo();
	//
	// LPPolBL tLPPolBL = new LPPolBL();
	// tLPPolBL.queryLPPol(aLPEdorMainSchema);
	//
	// textTag.add("SAOldPrem", tLCPolDB.getPrem());
	// textTag.add("SANewPrem", tLPPolBL.getPrem());
	//
	// textTag.add("SAOldAmnt", tLCPolDB.getAmnt());
	// textTag.add("SANewAmnt", tLPPolBL.getAmnt());
	//
	// textTag.add("SAOldMult", tLCPolDB.getMult());
	// textTag.add("SANewMult", tLPPolBL.getMult());
	//
	// textTag.add("SAEdorValiDate", aLPEdorMainSchema.getEdorValiDate());
	//
	// return true;
	// }

	// 对没有的保全项目类型生成空打迎数据
	private boolean getDetailForBlankType(LPEdorItemSchema aLPEdorItemSchema) {
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PrtEndorsementBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		PrtEndorsementBL tPrtGrpEndorsementBL = new PrtEndorsementBL(
				"410000000000093");
		VData tVData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		tVData.addElement(tGlobalInput);
		tPrtGrpEndorsementBL.submitData(tVData, "");
		VData vdata = tPrtGrpEndorsementBL.getResult();
		PubSubmit ps = new PubSubmit();
		if (ps.submitData(vdata, "")) {
			logger.debug("succeed in pubsubmit");
		}
	}
}
