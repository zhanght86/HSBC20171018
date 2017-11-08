package com.sinosoft.lis.bqgrp;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.bq.EdorPrint;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
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

public class GrpEdorBBPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(GrpEdorBBPrintBL.class);

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

	public GrpEdorBBPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "BB数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "BB数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "BB数据生成失败!");
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
		if (!mOperate.equals("PRINTBB")) {
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

	@SuppressWarnings("unchecked")
	private boolean dealData() {
		// xmlexport.createDocument("PrtAppEndorsement.vts", "printer");

		xmlexport.addDisplayControl("displayHead");
		xmlexport.addDisplayControl("displayBB");

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

		ListTable tBBListTable = new ListTable();
		tBBListTable.setName("BB");
		List ContNoList = new ArrayList();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询变更个人保全信息失败!");
			return false;
		}

		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {

			String tContNo = tLPEdorItemSet.get(i).getContNo();
			String tInsuredNo = tLPEdorItemSet.get(i).getInsuredNo();
			if (ContNoList.contains(tContNo)) {
				continue;
			} else {
				ContNoList.add(tContNo);
			}
			// 旧的被保人信息
			String tSQL = "SELECT a.Name as r0," + " f.Mobile as r1,"
					+ " f.EMail as r2," + " a.joincompanydate as r3,"
					+ " a.Salary as r4," + " a.WorkNo as r5,"
					+ " a.BankCode as r6," + " a.BankAccNo as r7"
					+ " FROM LCInsured a"
					+ " left join LDPerson e on a.insuredno = e.CustomerNo"
					+ " left join LCAddress f on a.InsuredNo = f.CustomerNo"
					+ " and a.AddressNo = f.AddressNo" + " WHERE a.ContNo = '"
					+ tContNo + "'" + " and a.InsuredNo = '" + tInsuredNo + "'";

			SSRS rSSRS = new SSRS();
			ExeSQL rExeSQL = new ExeSQL();
			rSSRS = rExeSQL.execSQL(tSQL);
			if (rSSRS == null || rSSRS.MaxRow < 1) {
				CError.buildErr(this, "查询被保人信息记录失败");
				return false;
			}

			String tOldInsuredName = rSSRS.GetText(1, 1);
			String tOldMobile = rSSRS.GetText(1, 2);
			String tOldEmail = rSSRS.GetText(1, 3);
			String tOldJoinCompanyDate = rSSRS.GetText(1, 4);
			String tOldSalary = rSSRS.GetText(1, 5);
			String tOldWorkNo = rSSRS.GetText(1, 6);
			String tOldBankCode = rSSRS.GetText(1, 7);
			String tOldBankAccNo = rSSRS.GetText(1, 8);

			tSQL = " SELECT a.Name as r0,"
					+ " (case"
					+ "  when ((c.CustomerNo is null) or (c.AddressNo is null)) then"
					+ "  f.Mobile"
					+ " else"
					+ "  c.Mobile"
					+ " end) as r1,"
					+ " (case"
					+ "   when ((c.CustomerNo is null) or (c.AddressNo is null)) then"
					+ "   f.EMail" + "  else" + "   c.EMail" + " end) as r2,"
					+ " a.joincompanydate as r3," + " a.Salary as r4,"
					+ " a.WorkNo as r5," + " a.BankCode as r6,"
					+ " a.BankAccNo as r7" + " FROM LPInsured a"
					+ " left join LPPerson b on a.insuredno = b.CustomerNo"
					+ " and a.EdorNo = b.EdorNo"
					+ " and a.EdorType = b.EdorType"
					+ " left join LPAddress c on a.InsuredNo = c.CustomerNo"
					+ " and a.AddressNo = c.AddressNo"
					+ " and a.EdorNo = c.EdorNo"
					+ " and a.EdorType = c.EdorType"
					+ " left join LDPerson e on a.insuredno = e.CustomerNo"
					+ " left join LCAddress f on a.InsuredNo = f.CustomerNo"
					+ " and a.AddressNo = f.AddressNo" + " WHERE a.ContNo = '"
					+ tContNo + "'" + " and a.InsuredNo = '" + tInsuredNo + "'"
					+ " and a.EdorNo = '" + mLPGrpEdorItemSchema.getEdorNo()
					+ "'" + " and a.EdorType = '"
					+ mLPGrpEdorItemSchema.getEdorType() + "'";

			rExeSQL = new ExeSQL();
			rSSRS = rExeSQL.execSQL(tSQL);
			if (rSSRS == null || rSSRS.MaxRow < 1) {
				CError.buildErr(this, "查询本次保全信息记录失败");
				return false;
			}
			// 新的被保险人基本信息
			String tNewInsuredName = rSSRS.GetText(1, 1);
			String tNewMobile = rSSRS.GetText(1, 2);
			String tNewEmail = rSSRS.GetText(1, 3);
			String tNewJoinCompanyDate = rSSRS.GetText(1, 4);
			String tNewSalary = rSSRS.GetText(1, 5);
			String tNewWorkNo = rSSRS.GetText(1, 6);
			String tNewBankCode = rSSRS.GetText(1, 7);
			String tNewBankAccNo = rSSRS.GetText(1, 8);
			boolean tHasTitle = false;
			if (!StrTool.compareString(tNewInsuredName, tOldInsuredName)) {
				String[] strBB = new String[4];
				if (!tHasTitle) {
					strBB[0] = tNewInsuredName;
					tHasTitle = true;
				} else {
					strBB[0] = "";
				}
				strBB[1] = "姓名";

				strBB[2] = tOldInsuredName;

				strBB[3] = tNewInsuredName;

				tBBListTable.add(strBB);
			}
			if (!StrTool.compareString(tNewMobile, tOldMobile)) {
				String[] strBB = new String[4];
				if (!tHasTitle) {
					strBB[0] = tNewInsuredName;
					tHasTitle = true;
				} else {
					strBB[0] = "";
				}
				strBB[1] = "移动电话";
				strBB[2] = tOldMobile;
				strBB[3] = tNewMobile;

				tBBListTable.add(strBB);
			}
			if (!StrTool.compareString(tNewEmail, tOldEmail)) {
				String[] strBB = new String[4];
				if (!tHasTitle) {
					strBB[0] = tNewInsuredName;
					tHasTitle = true;
				} else {
					strBB[0] = "";
				}
				strBB[1] = "电子邮箱";
				strBB[2] = tOldEmail;
				strBB[3] = tNewEmail;

				tBBListTable.add(strBB);
			}
			if (!StrTool
					.compareString(tNewJoinCompanyDate, tOldJoinCompanyDate)) {
				String[] strBB = new String[4];
				if (!tHasTitle) {
					strBB[0] = tNewInsuredName;
					tHasTitle = true;
				} else {
					strBB[0] = "";
				}
				strBB[1] = "入司时间";
				strBB[2] = tOldJoinCompanyDate;
				strBB[3] = tNewJoinCompanyDate;

				tBBListTable.add(strBB);
			}
			if (!StrTool.compareString(tNewSalary, tOldSalary)) {
				String[] strBB = new String[4];
				if (!tHasTitle) {
					strBB[0] = tNewInsuredName;
					tHasTitle = true;
				} else {
					strBB[0] = "";
				}
				strBB[1] = "工资";
				strBB[2] = tOldSalary;
				strBB[3] = tNewSalary;

				tBBListTable.add(strBB);
			}
			if (!StrTool.compareString(tNewWorkNo, tOldWorkNo)) {
				String[] strBB = new String[4];
				if (!tHasTitle) {
					strBB[0] = tNewInsuredName;
					tHasTitle = true;
				} else {
					strBB[0] = "";
				}
				strBB[1] = "工作证号";
				strBB[2] = tOldWorkNo;
				strBB[3] = tNewWorkNo;

				tBBListTable.add(strBB);
			}

			if (!StrTool.compareString(tNewBankCode, tOldBankCode)) {
				String[] strBB = new String[4];
				if (!tHasTitle) {
					strBB[0] = tNewInsuredName;
					tHasTitle = true;
				} else {
					strBB[0] = "";
				}
				strBB[1] = "开户银行";
				LDBankDB tLDBankDB = new LDBankDB();
				if (tOldBankCode == null || "".equals(tOldBankCode)) {
					strBB[2] = "";
				} else {
					tLDBankDB.setBankCode(tOldBankCode);
					LDBankSchema tLDBankSchema = tLDBankDB.query().get(1);
					if (tLDBankSchema != null) {
						strBB[2] = tLDBankSchema.getBankName();
					} else {
						strBB[2] = "";
					}
				}

				if (tNewBankCode == null || "".equals(tNewBankCode)) {
					strBB[3] = "";
				} else {
					tLDBankDB = new LDBankDB();
					tLDBankDB.setBankCode(tNewBankCode);
					LDBankSchema tLDBankSchema = tLDBankDB.query().get(1);
					if (tLDBankSchema != null) {
						strBB[3] = tLDBankSchema.getBankName();
					} else {
						strBB[3] = "";
					}
				}

				tBBListTable.add(strBB);
			}
			// 比较地址.....
			if (!StrTool.compareString(tNewBankAccNo, tOldBankAccNo)) {
				String[] strBB = new String[4];
				if (!tHasTitle) {
					strBB[0] = tNewInsuredName;
					tHasTitle = true;
				} else {
					strBB[0] = "";
				}
				strBB[1] = "银行账户";
				strBB[2] = tOldBankAccNo;
				strBB[3] = tNewBankAccNo;
				tBBListTable.add(strBB);
			}
		}

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		mLPEdorAppSchema = tLPEdorAppDB.query().get(1);

		mTextTag.add("BBNUM", ContNoList.size());
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
		String[] b_strBB = new String[4];
		xmlexport.addListTable(tBBListTable, b_strBB);
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
