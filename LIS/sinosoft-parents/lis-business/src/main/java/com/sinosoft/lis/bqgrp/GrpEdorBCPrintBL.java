package com.sinosoft.lis.bqgrp;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.bq.EdorPrint;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
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

public class GrpEdorBCPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(GrpEdorBCPrintBL.class);

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

	public GrpEdorBCPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "BC数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "BC数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "BC数据生成失败!");
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
		if (!mOperate.equals("PRINTBC")) {
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
		xmlexport.addDisplayControl("displayBC");

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

		ListTable tBCListTable = new ListTable();
		tBCListTable.setName("BC");

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询变更个人保全信息失败!");
			return false;
		}
		List ContNoList = new ArrayList();

		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {

			String tContNo = tLPEdorItemSet.get(i).getContNo();
			if (ContNoList.contains(tContNo)) {
				continue;
			} else {
				ContNoList.add(tContNo);
			}
			String tInsuredNo = tLPEdorItemSet.get(i).getInsuredNo();

			boolean titleIsAdd = false;
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(tContNo);
			tLCInsuredDB.setInsuredNo(tInsuredNo);
			LCInsuredSchema tLCInsuredSchema = tLCInsuredDB.query().get(1);
			String tInsuredName = tLCInsuredSchema.getName();
			String tIdNo = tLCInsuredSchema.getIDNo();
			if (tIdNo == null) {
				tIdNo = "";
			}
			// 旧的受益人信息
			String tSql = "select f.name,"
					+ " decode(f.sex, '0', '男', '1', '女', '不详'),"
					+ " f.birthday,"
					+ " f.idno,"
					+ " f.bnfgrade,"
					+ " f.bnflot * 100 || '%',(select codename from ldcode where code = f.relationtoinsured and codetype = 'relation'),"
					+ " (select riskname from lmrisk where riskcode = p.riskcode) "
					+ " from lcbnf f, lcpol p " + " where f.polno = p.polno "
					+ " and exists (select 1 from lpedoritem where edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo() + "' and edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "' and polno = p.polno)" + " and p.contno = '" + tContNo
					+ "'";
			SSRS rSSRS = new SSRS();
			ExeSQL rExeSQL = new ExeSQL();
			rSSRS = rExeSQL.execSQL(tSql);
			int tArrLen = 0;
			if (rSSRS == null || rSSRS.MaxRow < 1) {
				tArrLen = 0;
				String[] strBC = new String[11];
				strBC[0] = tInsuredName;
				strBC[1] = tIdNo;
				titleIsAdd = true;
				strBC[2] = "原受益人";
				strBC[3] = "";
				strBC[4] = "";
				strBC[5] = "";
				strBC[6] = "";
				strBC[7] = "";
				strBC[8] = "";
				strBC[9] = "";
				strBC[10] = "";
				tBCListTable.add(strBC);
			} else {
				tArrLen = rSSRS.MaxRow;
			}
			for (int k = 1; k <= tArrLen; k++) {
				String[] strBC = new String[11];
				if (k == 1) {
					strBC[0] = tInsuredName;
					strBC[1] = tIdNo;
					titleIsAdd = true;
				} else {
					strBC[0] = "";
					strBC[1] = "";
				}
				strBC[2] = "原受益人";
				strBC[3] = rSSRS.GetText(k, 1);
				strBC[4] = rSSRS.GetText(k, 2);
				strBC[5] = rSSRS.GetText(k, 3);
				strBC[6] = rSSRS.GetText(k, 4);
				strBC[7] = rSSRS.GetText(k, 5);
				strBC[8] = rSSRS.GetText(k, 6);
				strBC[9] = rSSRS.GetText(k, 7);
				strBC[10] = rSSRS.GetText(k, 8);
				tBCListTable.add(strBC);
			}

			tSql = "select f.name,"
					+ " decode(f.sex, '0', '男', '1', '女', '不详'),"
					+ " f.birthday,"
					+ " f.idno,"
					+ " f.bnfgrade,"
					+ " f.bnflot * 100 || '%',(select codename from ldcode where code = f.relationtoinsured and codetype = 'relation'),"
					+ " (select riskname from lmrisk where riskcode = p.riskcode) "
					+ " from lpbnf f, lcpol p " + " where f.polno = p.polno "
					+ " and p.contno = '" + tContNo + "' and f.edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo() + "' and f.edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType() + "'";
			rSSRS = new SSRS();
			rExeSQL = new ExeSQL();
			rSSRS = rExeSQL.execSQL(tSql);
			tArrLen = 0;
			if (rSSRS == null || rSSRS.MaxRow < 1) {
				tArrLen = 0;
				String[] strBC = new String[11];
				strBC[0] = "";
				strBC[1] = "";
				strBC[2] = "现受益人";
				strBC[3] = "";
				strBC[4] = "";
				strBC[5] = "";
				strBC[6] = "";
				strBC[7] = "";
				strBC[8] = "";
				strBC[9] = "";
				strBC[10] = "";
				tBCListTable.add(strBC);
			} else {
				tArrLen = rSSRS.MaxRow;
			}
			for (int k = 1; k <= tArrLen; k++) {
				String[] strBC = new String[11];
				if (k == 1) {
					if (!titleIsAdd) {
						strBC[0] = tInsuredName;
						strBC[1] = tIdNo;
					} else {
						strBC[0] = "";
						strBC[1] = "";
					}
				} else {
					strBC[0] = "";
					strBC[1] = "";
				}
				strBC[2] = "现受益人";
				strBC[3] = rSSRS.GetText(k, 1);
				strBC[4] = rSSRS.GetText(k, 2);
				strBC[5] = rSSRS.GetText(k, 3);
				strBC[6] = rSSRS.GetText(k, 4);
				strBC[7] = rSSRS.GetText(k, 5);
				strBC[8] = rSSRS.GetText(k, 6);
				strBC[9] = rSSRS.GetText(k, 7);
				strBC[10] = rSSRS.GetText(k, 8);
				tBCListTable.add(strBC);
			}

		}

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		mLPEdorAppSchema = tLPEdorAppDB.query().get(1);

		mTextTag.add("BCNUM", ContNoList.size());
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
		String[] b_strBC = new String[11];
		xmlexport.addListTable(tBCListTable, b_strBC);
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
