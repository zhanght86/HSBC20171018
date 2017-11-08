package com.sinosoft.lis.bqgrp;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.bq.EdorPrint;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
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

public class GrpEdorICPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(GrpEdorICPrintBL.class);

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

	public GrpEdorICPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "IC数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "IC数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "IC数据生成失败!");
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
		if (!mOperate.equals("PRINTIC")) {
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
		xmlexport.addDisplayControl("displayIC");

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

		ListTable tICListTable = new ListTable();
		tICListTable.setName("IC");
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
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(tContNo);
			tLCInsuredDB.setInsuredNo(tInsuredNo);
			LCInsuredSchema tLCInsuredSchema = tLCInsuredDB.query().get(1);

			LPInsuredDB tLPInsuredDB = new LPInsuredDB();
			tLPInsuredDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPInsuredDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPInsuredDB.setContNo(tContNo);
			tLPInsuredDB.setInsuredNo(tInsuredNo);
			LPInsuredSchema tLPInsuredSchema = tLPInsuredDB.query().get(1);
			boolean tHasTitle = false;
			if (!StrTool.compareString(tLPInsuredSchema.getSex(),
					tLCInsuredSchema.getSex())
					&& !StrTool.cTrim(tLPInsuredSchema.getSex()).equals("")) {
				String[] strIC = new String[4];
				if (!tHasTitle) {
					strIC[0] = tLPInsuredSchema.getName();
					tHasTitle = true;
				} else {
					strIC[0] = "";
				}
				strIC[1] = "性别";
				String tSexCode = tLCInsuredSchema.getSex();
				String tSex;
				if (tSexCode.equals("0")) {
					tSex = "男";
				} else if (tSexCode.equals("1")) {
					tSex = "女";
				} else {
					tSex = "不详";
				}
				strIC[2] = tSex;

				tSexCode = tLPInsuredSchema.getSex();

				if (tSexCode.equals("0")) {
					tSex = "男";
				} else if (tSexCode.equals("1")) {
					tSex = "女";
				} else {
					tSex = "不详";
				}
				strIC[3] = tSex;
				tICListTable.add(strIC);
			}
			if (!StrTool.compareString(tLPInsuredSchema.getBirthday(),
					tLCInsuredSchema.getBirthday())
					&& !StrTool.cTrim(tLPInsuredSchema.getBirthday())
							.equals("")) {
				String[] strIC = new String[4];
				if (!tHasTitle) {
					strIC[0] = tLPInsuredSchema.getName();
					tHasTitle = true;
				} else {
					strIC[0] = "";
				}
				strIC[1] = "出生日期";
				strIC[2] = tLCInsuredSchema.getBirthday();
				strIC[3] = tLPInsuredSchema.getBirthday();
				tICListTable.add(strIC);
			}
			if (!StrTool.compareString(tLPInsuredSchema.getIDType(),
					tLCInsuredSchema.getIDType())
					&& !StrTool.cTrim(tLPInsuredSchema.getIDType()).equals("")) {
				String[] strIC = new String[4];
				if (!tHasTitle) {
					strIC[0] = tLPInsuredSchema.getName();
					tHasTitle = true;
				} else {
					strIC[0] = "";
				}
				strIC[1] = "证件类型";
				LDCodeDB tLDCodeDB = new LDCodeDB();
				tLDCodeDB.setCodeType("idtype");
				LDCodeSet tLDCodeSet = tLDCodeDB.query();
				for (int k = 1; k <= tLDCodeSet.size(); k++) {
					String tCode = tLDCodeSet.get(k).getCode();
					if (tCode.equals(tLCInsuredSchema.getIDType())) {

						strIC[2] = tLDCodeSet.get(k).getCodeName();
					}

					if (tCode.equals(tLPInsuredSchema.getIDType())) {

						strIC[3] = tLDCodeSet.get(k).getCodeName();
					}

				}
				tICListTable.add(strIC);
			}
			if (!StrTool.compareString(tLPInsuredSchema.getIDNo(),
					tLCInsuredSchema.getIDNo())
					&& !StrTool.cTrim(tLPInsuredSchema.getIDNo()).equals("")) {
				String[] strIC = new String[4];
				if (!tHasTitle) {
					strIC[0] = tLPInsuredSchema.getName();
					tHasTitle = true;
				} else {
					strIC[0] = "";
				}
				strIC[1] = "证件号码";
				strIC[2] = tLCInsuredSchema.getIDNo();
				strIC[3] = tLPInsuredSchema.getIDNo();
				tICListTable.add(strIC);
			}
			if (!StrTool.compareString(tLPInsuredSchema.getOccupationType(),
					tLCInsuredSchema.getOccupationType())
					&& !StrTool.cTrim(tLPInsuredSchema.getOccupationType())
							.equals("")) {
				String[] strIC = new String[4];
				if (!tHasTitle) {
					strIC[0] = tLPInsuredSchema.getName();
					tHasTitle = true;
				} else {
					strIC[0] = "";
				}
				strIC[1] = "职业类别";
				if (tLCInsuredSchema.getOccupationType() != null) {

					try {
						strIC[2] = tLCInsuredSchema.getOccupationType();
					} catch (Exception e) {
						strIC[2] = "";
					}
				} else {
					strIC[2] = "";
				}
				strIC[3] = tLPInsuredSchema.getOccupationType();
				tICListTable.add(strIC);
			}
			if (!StrTool.compareString(tLPInsuredSchema.getOccupationCode(),
					tLCInsuredSchema.getOccupationCode())
					&& !StrTool.cTrim(tLPInsuredSchema.getOccupationCode())
							.equals("")) {
				String[] strIC = new String[4];
				if (!tHasTitle) {
					strIC[0] = tLPInsuredSchema.getName();
					tHasTitle = true;
				} else {
					strIC[0] = "";
				}
				strIC[1] = "职业";
				String tOldCode = tLCInsuredSchema.getOccupationCode();
				String tNewCode = tLPInsuredSchema.getOccupationCode();
				LDOccupationDB tLDOccupationDB = new LDOccupationDB();
				if (tOldCode != null) {
					tLDOccupationDB.setOccupationCode(tOldCode);
					try {
						strIC[2] = tLDOccupationDB.query().get(1)
								.getOccupationName();
					} catch (Exception ex) {
						strIC[2] = "";
					}
				} else {
					strIC[2] = "";
				}
				tLDOccupationDB = new LDOccupationDB();
				tLDOccupationDB.setOccupationCode(tNewCode);
				strIC[3] = tLDOccupationDB.query().get(1).getOccupationName();
				tICListTable.add(strIC);
			}
			if (!StrTool.compareString(tLPInsuredSchema.getSocialInsuFlag(),
					tLCInsuredSchema.getSocialInsuFlag())
					&& !StrTool.cTrim(tLPInsuredSchema.getSocialInsuFlag())
							.equals("")) {
				String[] strIC = new String[4];
				if (!tHasTitle) {
					strIC[0] = tLPInsuredSchema.getName();
					tHasTitle = true;
				} else {
					strIC[0] = "";
				}
				strIC[1] = "社保标记";
				strIC[2] = ("1".equals(tLCInsuredSchema.getSocialInsuFlag())) ? "有"
						: "无";
				strIC[3] = ("1".equals(tLPInsuredSchema.getSocialInsuFlag())) ? "有"
						: "无";
				tICListTable.add(strIC);
			}
		}
		String tSql = " select a.name,decode(b.feefinatype,'TF','退费','BF','交费','其它'),decode(b.getflag,'0',b.getmoney,'1','0','其它'),decode(b.getflag,'1',b.getmoney,'0','0','其它')"
				+ " from lcinsured a, ljsgetendorse b "
				+ " where b.endorsementno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and b.contno = a.contno";
		SSRS rSSRS = new SSRS();
		ExeSQL rExeSQL = new ExeSQL();
		rSSRS = rExeSQL.execSQL(tSql);
		if (rSSRS != null && rSSRS.MaxRow > 0) {
			// 有补退费记录
			ListTable tIMListTable = new ListTable();
			tIMListTable.setName("IM");
			xmlexport.addDisplayControl("displayIM");

			int tArrLen = rSSRS.MaxRow;
			for (int i = 1; i <= tArrLen; i++) {
				String[] strIM = new String[4];
				for (int k = 1; k <= strIM.length; k++) {
					strIM[k - 1] = rSSRS.GetText(i, k);
				}
				tIMListTable.add(strIM);
			}
			String dSum = "";
			tSql = "select nvl(sum(getmoney), 0) from ljsgetendorse where endorsementno = '"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "' and grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo() + "'";
			dSum = rExeSQL.getOneValue(tSql);
			if (dSum == null || dSum.equals("")) {
				CError.buildErr(this, "统计交费失败!");
				return false;
			}
			mTextTag.add("IMSUM", dSum);
			String[] b_strIM = new String[4];
			xmlexport.addListTable(tIMListTable, b_strIM);
		}
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		mLPEdorAppSchema = tLPEdorAppDB.query().get(1);

		mTextTag.add("ICNUM", ContNoList.size());
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
		String[] b_strIC = new String[4];
		xmlexport.addListTable(tICListTable, b_strIC);
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
