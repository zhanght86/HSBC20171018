package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:投保人基本信息变更批单数据生成
 * </p>
 * 
 * <p>
 * Description:生成保全项目被保险人基本信息变更的批单数据
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
 * @author wangyan
 * 
 * @version 1.0
 */

public class PEdorACPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(PEdorACPrintBL.class);

	// 公共数据
	private VData mResult = new VData();
	//public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;
	private VData mInputData = new VData();
	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private XmlExportNew xmlExport = new XmlExportNew();
	private LPEdorAppSchema mLPEdorAppSchema =new LPEdorAppSchema();
	private TextTag mTextTag = new TextTag();
	private ExeSQL tExeSQL = new ExeSQL();

	public PEdorACPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "AC数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "AC数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "AC数据生成失败!");
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		xmlExport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlExport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTAC")) {
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() < 1) {
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	private boolean dealData() {
		// xmlexport.createDocument("PrtAppEndorsement.vts", "printer");

		xmlExport.addDisplayControl("displayHead1");
		xmlExport.addDisplayControl("displayAC");

		// 保全操作保单信息
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select GrpName from ldperson where customerno = '"
				+ "?customerno?" + "'");
		sqlbv.put("customerno", tLCContSchema.getAppntNo());
		String mWork = tExeSQL
				.getOneValue(sqlbv);
    
		mTextTag.add("AppntName", tLCContSchema.getAppntName());// 投保人姓名
		mTextTag.add("InsuredName", tLCContSchema.getInsuredName());// 被保人姓名
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo());// 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo());// 保单号

		// 投保人新信息
		LPAppntDB tLPAppntDB = new LPAppntDB();
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPAppntDB.getInfo()) {
			logger.debug("打印生成数据时，取投保人新信息失败!");
			return false;
		}
		tLPAppntSchema = tLPAppntDB.getSchema();

		// 投保人新地址信息
		LPAddressDB tLPAddressDB = new LPAddressDB();
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		tLPAddressDB.setAddressNo(Integer.parseInt(tLPAppntSchema
				.getAddressNo()));
		tLPAddressDB.setCustomerNo(tLPAppntSchema.getAppntNo());
		tLPAddressDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAddressDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPAddressDB.getInfo()) {
			logger.debug("打印生成数据时，取新地址信息失败!");
			return false;
		}
		tLPAddressSchema = tLPAddressDB.getSchema();

		// 投保人旧信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCAppntDB.getInfo()) {
			logger.debug("打印生成数据时，取投保人旧信息失败!");
			return false;
		}
		tLCAppntSchema = tLCAppntDB.getSchema();

		// 投保人旧地址信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		tLCAddressDB.setAddressNo(Integer.parseInt(tLCAppntSchema
				.getAddressNo()));
		tLCAddressDB.setCustomerNo(tLCAppntSchema.getAppntNo());
		if (!tLCAddressDB.getInfo()) {
			logger.debug("打印生成数据时，取旧地址信息失败!");
			return false;
		}
		tLCAddressSchema = tLCAddressDB.getSchema();

		LPContDB tLPContDB = new LPContDB();
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			logger.debug("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLPContSchema = tLPContDB.getSchema();

		// 投保人基本信息变更数据提取
		CodeNameQuery tCodeNameQuery = new CodeNameQuery();

		SQLwithBindVariables sbsbv=new SQLwithBindVariables();
		sbsbv.sql("select relationtoappnt from lcinsured where insuredno = '"
				+ "?insuredno?"
				+ "' and contno = '"
				+ "?contno?" + "'");
		sbsbv.put("insuredno", tLCContSchema.getInsuredNo());
		sbsbv.put("contno", tLCContSchema.getContNo());
		String sb = tExeSQL
				.getOneValue(sbsbv);
		SQLwithBindVariables stsbv=new SQLwithBindVariables();
		stsbv.sql("select relationtoappnt from lpinsured where insuredno = '"
				+ "?insuredno?"
				+ "' and contno = '"
				+ "?contno?"
				+ "' and edorno = '"
				+ "?edorno?" + "'");
		stsbv.put("insuredno", tLCContSchema.getInsuredNo());
		stsbv.put("contno", tLCContSchema.getContNo());
		stsbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		String st = tExeSQL
				.getOneValue(stsbv);
		if (!sb.equals(st)) {
			mTextTag.add("ACLPInsured.RelationToMainInsured", tCodeNameQuery
					.getName("relation", st));// 被保人与投保人关系
			xmlExport.addDisplayControl("displayACRelation");
		}
		if (!StrTool.compareString(tLPAddressSchema.getMobile(),
				tLCAddressSchema.getMobile())
				&& !StrTool.cTrim(tLPAddressSchema.getMobile()).equals("")) {
			mTextTag.add("ACLPAddress.Mobile", tLPAddressSchema.getMobile()); // 移动电话号码
			xmlExport.addDisplayControl("displayACMobile");
		}
		if (!StrTool.compareString(tLPAddressSchema.getCompanyPhone(),
				tLCAddressSchema.getCompanyPhone())
				&& !StrTool.cTrim(tLPAddressSchema.getCompanyPhone())
						.equals("")) {
			mTextTag.add("ACLPAddress.CompanyPhone", tLPAddressSchema
					.getCompanyPhone()); // 办公电话号码
			xmlExport.addDisplayControl("displayACCompanyPhone");
		}
		if (!StrTool.compareString(tLPAddressSchema.getHomePhone(),
				tLCAddressSchema.getHomePhone())
				&& !StrTool.cTrim(tLPAddressSchema.getHomePhone()).equals("")) {
			mTextTag.add("ACLPAddress.HomePhone", tLPAddressSchema
					.getHomePhone()); // 住宅电话号码
			xmlExport.addDisplayControl("displayACHomePhone");
		}
		if (!StrTool.compareString(tLPAddressSchema.getFax(), tLCAddressSchema
				.getFax())
				&& !StrTool.cTrim(tLPAddressSchema.getFax()).equals("")) {
			mTextTag.add("ACLPAddress.Fax", tLPAddressSchema.getFax());// 传真号码
			xmlExport.addDisplayControl("displayACFax");
		}
		if (!StrTool.compareString(tLPAddressSchema.getEMail(),
				tLCAddressSchema.getEMail())
				&& !StrTool.cTrim(tLPAddressSchema.getEMail()).equals("")) {
			mTextTag.add("ACLPAddress.EMail", tLPAddressSchema.getEMail());// 电子邮件
			xmlExport.addDisplayControl("displayACEMail");
		}
		if (!StrTool.compareString(mWork, tLPAddressSchema.getGrpName())
				&& !StrTool.cTrim(tLPAddressSchema.getGrpName()).equals("")) {
			mTextTag.add("ACLPAddress.GrpName", tLPAddressSchema.getGrpName());// 工作单位
			xmlExport.addDisplayControl("displayACGrpName");
		}
		if ((!StrTool.compareString(tLPAddressSchema.getPostalAddress(),
				tLCAddressSchema.getPostalAddress())
				|| !StrTool.compareString(tLPAddressSchema.getCounty(),
						tLCAddressSchema.getCounty()) || !StrTool
				.compareString(tLPAddressSchema.getProvince(), tLCAddressSchema
						.getProvince()))
				&& !StrTool.cTrim(tLPAddressSchema.getPostalAddress()).equals(
						"")) {
//			String mProvince = tCodeNameQuery.getProvince(tLPAddressSchema
//					.getProvince());
//			String mCity = tCodeNameQuery.getCity(tLPAddressSchema.getCity());
			String mDistrict = tCodeNameQuery.getDistrict(tLPAddressSchema
					.getCounty());
			mTextTag.add("ACLPAddress.PostalAddress", mDistrict
					+ tLPAddressSchema.getPostalAddress());// 联系地址
			xmlExport.addDisplayControl("displayACPostalAddress");
		}
		if (!StrTool.compareString(tLPAddressSchema.getZipCode(),
				tLCAddressSchema.getZipCode())
				&& !StrTool.cTrim(tLPAddressSchema.getZipCode()).equals("")) {
			mTextTag.add("ACLPAddress.ZipCode", tLPAddressSchema.getZipCode());// 邮编
			xmlExport.addDisplayControl("displayACZipCode");
		}
		boolean BankFlag = false;
		if (StrTool.compareString("0", tLPContSchema.getPayLocation())
				&& !StrTool.compareString("0", tLCContSchema.getPayLocation()))
			BankFlag = true;
		if ((!StrTool.compareString(tLPContSchema.getBankCode(), tLCContSchema
				.getBankCode()) && !StrTool.cTrim(tLPContSchema.getBankCode())
				.equals(""))
				|| BankFlag == true) {
			SQLwithBindVariables smsbv=new SQLwithBindVariables();
			smsbv.sql("select bankname from ldbank where bankcode = '"
					+ "?bankcode?" + "'");
			smsbv.put("bankcode", tLPContSchema.getBankCode());
			String sm = tExeSQL
					.getOneValue(smsbv);
			mTextTag.add("ACLDBank.BankName", sm);// 银行名称
			xmlExport.addDisplayControl("displayACBankName");
		}
		if ((!StrTool.compareString(tLPContSchema.getBankAccNo(), tLCContSchema
				.getBankAccNo()) && !StrTool
				.cTrim(tLPContSchema.getBankAccNo()).equals(""))
				|| BankFlag == true) {
			mTextTag.add("ACLPAppnt.BankAccNo", tLPContSchema.getBankAccNo());// 银行帐号
			xmlExport.addDisplayControl("displayACBankAccNo");
		}
		if ((!StrTool.compareString(tLPContSchema.getAccName(), tLCContSchema
				.getAccName()) && !StrTool.cTrim(tLPContSchema.getAccName())
				.equals(""))
				|| BankFlag == true) {
			mTextTag.add("ACLPAppnt.AccName", tLPContSchema.getAccName());// 户名
			xmlExport.addDisplayControl("displayACAccName");
		}
		if (!StrTool.compareString(tLPAppntSchema.getLicenseType(),
				tLCAppntSchema.getLicenseType())) {
			String mLicenseType = tCodeNameQuery.getName("licensetype",
					tLPAppntSchema.getLicenseType());
			mTextTag.add("ACLPAppnt.LicenseType", mLicenseType);// 驾照类型
			xmlExport.addDisplayControl("displayACLicenseType");
		}
		if (!StrTool.compareString(tLPAppntSchema.getOccupationType(),
				tLCAppntSchema.getOccupationType())) {
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql("select OccupationType from LDOccupation where OccupationCode = '"
					+ "?OccupationCode?" + "'");
			sbv.put("OccupationCode", tLPAppntSchema.getOccupationCode());
			
			mTextTag
					.add(
							"ACLPAppnt.OccupationType",
							tExeSQL
									.getOneValue(sbv));// 职业类型
			xmlExport.addDisplayControl("displayACOccupationType");
		}
		if (!StrTool.compareString(tLPAppntSchema.getOccupationCode(),
				tLCAppntSchema.getOccupationCode())) {
			mTextTag.add("ACLPAppnt.OccupationCode", tLPAppntSchema
					.getOccupationCode());// 职业代码
			xmlExport.addDisplayControl("displayACOccupationCode");
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql("select OccupationName from LDOccupation where OccupationCode = '"
					+ "?OccupationCode?" + "'");
			sbv.put("OccupationCode", tLPAppntSchema.getOccupationCode());
			mTextTag
					.add(
							"ACLPAppnt.WorkType",
							tExeSQL
									.getOneValue(sbv));// 职业
			xmlExport.addDisplayControl("displayACWorkType");
		}

	    BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,mLPEdorAppSchema,xmlExport);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			return false;
		}
		xmlExport.addTextTag(mTextTag);
		mResult.add(xmlExport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
