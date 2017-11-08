package com.sinosoft.lis.bqgrp;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.bq.EdorPrint;
import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPGrpAddressDB;
import com.sinosoft.lis.db.LPGrpAppntDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpAppntSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LPGrpAddressSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ListTable;
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

public class GrpEdorACPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(GrpEdorACPrintBL.class);

	// 全局变量
	private VData mResult = new VData();

	public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private VData mInputData = new VData();

	private TextTag mTextTag = new TextTag();

	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private XmlExportNew xmlexport = new XmlExportNew();

	public GrpEdorACPrintBL() {
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
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPGrpEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTAC")) {
			return false;
		}
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemSet.size() < 1) {
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));

		return true;
	}

	private boolean dealData() {
		// xmlexport.createDocument("PrtAppEndorsement.vts", "printer");

		xmlexport.addDisplayControl("displayHead");
		xmlexport.addDisplayControl("displayAC");

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();

		mTextTag.add("GrpName", tLCGrpContSchema.getGrpName()); // 单位名称
		if (mLPEdorAppSchema != null) {
			mTextTag.add("EdorNo", mLPEdorAppSchema.getEdorConfNo()); // 团体批单
		} else {
			mTextTag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
		}
		mTextTag.add("EdorAcceptNo", mLPGrpEdorItemSchema.getEdorAcceptNo()); // 保全受理号
		mTextTag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		mTextTag.add("EdorAppDate", mLPGrpEdorItemSchema.getEdorAppDate()); // 团体保单号
		LPGrpContDB tLPGrpContDB = new LPGrpContDB();
		tLPGrpContDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
		tLPGrpContDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		if (!tLPGrpContDB.getInfo()) {
			this.mErrors.copyAllErrors(tLPGrpContDB.mErrors);

			return false;
		}
		LPGrpContSchema tLPGrpContSchema = tLPGrpContDB.getSchema();

		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		tLCGrpAppntDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
		if (!tLCGrpAppntDB.getInfo()) {
			this.mErrors.copyAllErrors(tLCGrpAppntDB.mErrors);

			return false;
		}
		LCGrpAppntSchema tLCGrpAppntSchema = tLCGrpAppntDB.getSchema();// 变更前的投保人资料
		LPGrpAppntDB tLPGrpAppntDB = new LPGrpAppntDB();
		tLPGrpAppntDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpAppntDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpAppntDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
		if (!tLPGrpAppntDB.getInfo()) {
			this.mErrors.copyAllErrors(tLCGrpAppntDB.mErrors);

			return false;
		}

		LPGrpAppntSchema tLPGrpAppntSchema = tLPGrpAppntDB.getSchema();// 变更后的投保人资料

		LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
		tLCGrpAddressDB.setCustomerNo(tLCGrpAppntSchema.getCustomerNo());
		tLCGrpAddressDB.setAddressNo(tLCGrpAppntSchema.getAddressNo());
		LCGrpAddressSchema tLCGrpAddressSchema = tLCGrpAddressDB.query().get(1);

		LPGrpAddressDB tLPGrpAddressDB = new LPGrpAddressDB();
		LPGrpAddressSet tLPGrpAddressSet = new LPGrpAddressSet();
		tLPGrpAddressDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpAddressDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpAddressDB.setCustomerNo(tLPGrpAppntSchema.getCustomerNo());
		tLPGrpAddressDB.setAddressNo(tLPGrpAppntSchema.getAddressNo());
		tLPGrpAddressSet = tLPGrpAddressDB.query();
		if (tLPGrpAddressDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLPGrpAddressDB.mErrors);
			return false;
		}

		ListTable tACListTable = new ListTable();
		tACListTable.setName("AC");
		if (!StrTool.compareString(tLPGrpContSchema.getGrpName(),
				tLCGrpContSchema.getGrpName())
				&& !StrTool.cTrim(tLPGrpContSchema.getGrpName()).equals("")) {
			String[] strAC = new String[3];
			strAC[0] = "单位名称";
			strAC[1] = tLCGrpContSchema.getGrpName();
			strAC[2] = tLPGrpContSchema.getGrpName();
			tACListTable.add(strAC);
		}
		if (!StrTool.compareString(tLPGrpContSchema.getGrpNature(),
				tLCGrpContSchema.getGrpNature())
				&& !StrTool.cTrim(tLPGrpContSchema.getGrpNature()).equals("")) {
			String[] strAC = new String[3];
			strAC[0] = "单位性质";
			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCodeType("grpnature");
			LDCodeSet tLDCodeSet = tLDCodeDB.query();
			for (int i = 1; i <= tLDCodeSet.size(); i++) {
				String tCode = tLDCodeSet.get(i).getCode();
				if (tCode.equals(tLCGrpContSchema.getGrpNature())) {

					strAC[1] = tLDCodeSet.get(i).getCodeName();
				}

				if (tCode.equals(tLPGrpContSchema.getGrpNature())) {

					strAC[2] = tLDCodeSet.get(i).getCodeName();
				}

			}

			tACListTable.add(strAC);
		}
		if (!StrTool.compareString(tLPGrpContSchema.getBusinessType(),
				tLCGrpContSchema.getBusinessType())
				&& !StrTool.cTrim(tLPGrpContSchema.getBusinessType())
						.equals("")) {
			String[] strAC = new String[3];
			strAC[0] = "行业类别";
			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCodeType("businesstype");
			LDCodeSet tLDCodeSet = tLDCodeDB.query();
			for (int i = 1; i <= tLDCodeSet.size(); i++) {
				String tCode = tLDCodeSet.get(i).getCode();
				if (tCode.equals(tLCGrpContSchema.getBusinessType())) {

					strAC[1] = tLDCodeSet.get(i).getCodeName();
				}

				if (tCode.equals(tLPGrpContSchema.getBusinessType())) {

					strAC[2] = tLDCodeSet.get(i).getCodeName();
				}

			}
			tACListTable.add(strAC);
		}
		if (!StrTool.compareString(String
				.valueOf(tLPGrpContSchema.getPeoples()), String
				.valueOf(tLCGrpContSchema.getPeoples()))) {
			String[] strAC = new String[3];
			strAC[0] = "总人数";
			strAC[1] = String.valueOf(tLCGrpContSchema.getPeoples());
			strAC[2] = String.valueOf(tLPGrpContSchema.getPeoples());
			tACListTable.add(strAC);
		}
		if (!StrTool.compareString(tLPGrpContSchema.getPhone(),
				tLCGrpContSchema.getPhone())
				&& !StrTool.cTrim(tLPGrpContSchema.getPhone()).equals("")) {
			String[] strAC = new String[3];
			strAC[0] = "总机";
			strAC[1] = tLCGrpContSchema.getPhone();
			strAC[2] = tLPGrpContSchema.getPhone();
			tACListTable.add(strAC);
		}
		if (!StrTool.compareString(tLPGrpContSchema.getFax(), tLCGrpContSchema
				.getFax())
				&& !StrTool.cTrim(tLPGrpContSchema.getFax()).equals("")) {
			String[] strAC = new String[3];
			strAC[0] = "传真";
			strAC[1] = tLCGrpContSchema.getFax();
			strAC[2] = tLPGrpContSchema.getFax();
			tACListTable.add(strAC);
		}
		if (!StrTool.compareString(tLPGrpContSchema.getFoundDate(),
				tLCGrpContSchema.getFoundDate())
				&& !StrTool.cTrim(tLPGrpContSchema.getFoundDate()).equals("")) {
			String[] strAC = new String[3];
			strAC[0] = "成立时间";
			strAC[1] = tLCGrpContSchema.getFoundDate();
			strAC[2] = tLPGrpContSchema.getFoundDate();
			tACListTable.add(strAC);
		}
		if (!StrTool.compareString(tLPGrpContSchema.getGetFlag(),
				tLCGrpContSchema.getGetFlag())
				&& !StrTool.cTrim(tLPGrpContSchema.getGetFlag()).equals("")) {
			String[] strAC = new String[3];

			strAC[0] = "付款方式";
			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCodeType("paymode");
			LDCodeSet tLDCodeSet = tLDCodeDB.query();
			for (int i = 1; i <= tLDCodeSet.size(); i++) {
				String tCode = tLDCodeSet.get(i).getCode();
				if (tCode.equals(tLCGrpContSchema.getGetFlag())) {

					strAC[1] = tLDCodeSet.get(i).getCodeName();
				}

				if (tCode.equals(tLPGrpContSchema.getGetFlag())) {

					strAC[2] = tLDCodeSet.get(i).getCodeName();
				}

			}
			tACListTable.add(strAC);
		}
		if (!StrTool.compareString(tLPGrpContSchema.getBankCode(),
				tLCGrpContSchema.getBankCode())
				&& !StrTool.cTrim(tLPGrpContSchema.getBankCode()).equals("")) {
			String[] strAC = new String[3];

			strAC[0] = "开户银行";

			LDBankDB tLDBankDB = new LDBankDB();
			if (tLCGrpContSchema.getBankCode() == null
					|| "".equals(tLCGrpContSchema.getBankCode())) {

			} else {

				tLDBankDB.setBankCode(tLCGrpContSchema.getBankCode());
				LDBankSchema tLDBankSchema = tLDBankDB.query().get(1);
				if (tLDBankSchema != null) {
					strAC[1] = tLDBankSchema.getBankName();
				} else {
					strAC[1] = "";
				}
			}
			tLDBankDB = new LDBankDB();
			tLDBankDB.setBankCode(tLPGrpContSchema.getBankCode());
			LDBankSchema tLDBankSchema = tLDBankDB.query().get(1);
			strAC[2] = tLDBankSchema.getBankName();

			tACListTable.add(strAC);
		}

		if (!StrTool.compareString(tLPGrpContSchema.getBankAccNo(),
				tLCGrpContSchema.getBankAccNo())) {
			String[] strAC = new String[3];

			strAC[0] = "账号";

			strAC[1] = tLCGrpContSchema.getBankAccNo();
			if (tLPGrpContSchema.getBankAccNo() != null) {
				strAC[2] = tLPGrpContSchema.getBankAccNo();
			} else {
				strAC[2] = "";
			}
			tACListTable.add(strAC);
		}

		if (tLPGrpAddressSet.size() > 0) {

			LPGrpAddressSchema tLPGrpAddressSchema = tLPGrpAddressSet.get(1);

			if (!StrTool.compareString(tLPGrpAddressSchema.getGrpAddress(),
					tLCGrpAddressSchema.getGrpAddress())
					&& !StrTool.cTrim(tLPGrpAddressSchema.getGrpAddress())
							.equals("")) {
				String[] strAC = new String[3];

				strAC[0] = "单位地址";

				strAC[1] = tLCGrpAddressSchema.getGrpAddress();

				strAC[2] = tLPGrpAddressSchema.getGrpAddress();

				tACListTable.add(strAC);
			}
			if (!StrTool.compareString(tLPGrpAddressSchema.getGrpZipCode(),
					tLCGrpAddressSchema.getGrpZipCode())
					&& !StrTool.cTrim(tLPGrpAddressSchema.getGrpZipCode())
							.equals("")) {
				String[] strAC = new String[3];

				strAC[0] = "邮政编码";

				strAC[1] = tLCGrpAddressSchema.getGrpZipCode();

				strAC[2] = tLPGrpAddressSchema.getGrpZipCode();

				tACListTable.add(strAC);
			}
			if (!StrTool.compareString(tLPGrpAddressSchema.getLinkMan1(),
					tLCGrpAddressSchema.getLinkMan1())
					&& !StrTool.cTrim(tLPGrpAddressSchema.getLinkMan1())
							.equals("")) {
				String[] strAC = new String[3];

				strAC[0] = "联系人一姓名";

				strAC[1] = tLCGrpAddressSchema.getLinkMan1();

				strAC[2] = tLPGrpAddressSchema.getLinkMan1();

				tACListTable.add(strAC);
			}
			if (!StrTool.compareString(tLPGrpAddressSchema.getPhone1(),
					tLCGrpAddressSchema.getPhone1())
					&& !StrTool.cTrim(tLPGrpAddressSchema.getPhone1()).equals(
							"")) {
				String[] strAC = new String[3];

				strAC[0] = "联系人一电话";

				strAC[1] = tLCGrpAddressSchema.getPhone1();

				strAC[2] = tLPGrpAddressSchema.getPhone1();

				tACListTable.add(strAC);
			}
			if (!StrTool.compareString(tLPGrpAddressSchema.getE_Mail1(),
					tLCGrpAddressSchema.getE_Mail1())
					&& !StrTool.cTrim(tLPGrpAddressSchema.getE_Mail1()).equals(
							"")) {
				String[] strAC = new String[3];

				strAC[0] = "联系人一Email";

				strAC[1] = tLCGrpAddressSchema.getE_Mail1();

				strAC[2] = tLPGrpAddressSchema.getE_Mail1();

				tACListTable.add(strAC);
			}
			if (!StrTool.compareString(tLPGrpAddressSchema.getLinkMan2(),
					tLCGrpAddressSchema.getLinkMan2())
					&& !StrTool.cTrim(tLPGrpAddressSchema.getLinkMan2())
							.equals("")) {
				String[] strAC = new String[3];

				strAC[0] = "联系人二姓名";

				strAC[1] = tLCGrpAddressSchema.getLinkMan2();

				strAC[2] = tLPGrpAddressSchema.getLinkMan2();

				tACListTable.add(strAC);
			}
			if (!StrTool.compareString(tLPGrpAddressSchema.getPhone2(),
					tLCGrpAddressSchema.getPhone2())
					&& !StrTool.cTrim(tLPGrpAddressSchema.getPhone2()).equals(
							"")) {
				String[] strAC = new String[3];

				strAC[0] = "联系人二电话";

				strAC[1] = tLCGrpAddressSchema.getPhone2();

				strAC[2] = tLPGrpAddressSchema.getPhone2();

				tACListTable.add(strAC);
			}
			if (!StrTool.compareString(tLPGrpAddressSchema.getE_Mail2(),
					tLCGrpAddressSchema.getE_Mail2())
					&& !StrTool.cTrim(tLPGrpAddressSchema.getE_Mail2()).equals(
							"")) {
				String[] strAC = new String[3];

				strAC[0] = "联系人二Email";

				strAC[1] = tLCGrpAddressSchema.getE_Mail2();

				strAC[2] = tLPGrpAddressSchema.getE_Mail2();

				tACListTable.add(strAC);
			}
		}
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		mLPEdorAppSchema = tLPEdorAppDB.query().get(1);
		String tUserCode = mLPEdorAppSchema.getOperator();
		mTextTag.add("EdorAppName", CodeNameQuery.getOperator(tUserCode));
		String tApproveOperator = mLPEdorAppSchema.getApproveOperator();
		String tApproveName = "";
		if (tApproveOperator == null || "".equals(tApproveOperator)) {
			tApproveName = "";
		} else {
			tApproveName = CodeNameQuery.getOperator(tApproveOperator);
		}
		mTextTag.add("ApproveOperator", tApproveName);
		mTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		mTextTag.add("ApproveDate", mLPEdorAppSchema.getApproveDate());
		mTextTag.add("Operator", mLPGrpEdorItemSchema.getOperator());
		String[] b_strAC = new String[3];
		xmlexport.addListTable(tACListTable, b_strAC);
		xmlexport.addTextTag(mTextTag);
		xmlexport.addDisplayControl("displayVD");
		xmlexport.addDisplayControl("displayTail");
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			return false;
		}
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
