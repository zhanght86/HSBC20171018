package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:保单加密的批单数据生成
 * </p>
 * <p>
 * Description:生成保全项目:保单效力确认
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */

public class PEdorBSPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorBSPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	// public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private VData mInputData = new VData();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ExeSQL tExeSQL = new ExeSQL();

	public PEdorBSPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "BS数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			CError.buildErr(this, "BS数据校验失败!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "BS数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "BS数据生成失败!");
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}

		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTBS")) {
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
		// xmlexport.addDisplayControl("displayHead1");
		BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);

		/*
		 * 设置displayHead1中的信息
		 */
		// BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema,mTextTag); ; //申请人的姓名
		mTextTag.add("AppName", "    兹经" + mLPEdorAppSchema.getEdorAppName()
				+ "申请,本公司审核同意对" + mLPEdorAppSchema.getOtherNo());//

		if ("3".equals(mLPEdorItemSchema.getStandbyFlag1())) {
			xmlexport.addDisplayControl("displayBSAdd");
		} else {
			xmlexport.addDisplayControl("displayBS");
		}
		/*
		 * 设置displayBS中的信息
		 */
		String tAppntCertifyCard = "";
		String tStandByFlag3 = mLPEdorItemSchema.getStandbyFlag3();
		//
		tStandByFlag3 = tStandByFlag3.substring(0, 54);

		String tStr[] = tStandByFlag3.split("\\$");

		String tSignCard = tStr[0];
		String tSubSignCard[] = tSignCard.split("\\\\");
		for (int k = 0; k < tSubSignCard.length; k++) {
			String tTempStr = tSubSignCard[k];

			if ("1".equals(tTempStr.substring(3))) {
				tAppntCertifyCard += BqNameFun.getCodeName(String
						.valueOf(k + 1), "bscertifytype")
						+ "/";
			}
		}

		String tInsureSQL = "select name,insuredno from lcinsured where contno='"
				+ "?contno?" + "'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tInsureSQL);
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		SSRS tInsuredSSRS = tExeSQL.execSQL(sbv);

		String tAppntSQL = "select appntname,appntno from lcappnt where contno='"
				+ "?contno?" + "'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(tAppntSQL);
		sbv1.put("contno", mLPEdorItemSchema.getContNo());
		SSRS tAppntSSRS = tExeSQL.execSQL(sbv1);

		String tAppntAndInsuredName = "";
		String tSignObj = tStr[1];
		String tSubSignObj[] = tSignObj.split("\\\\");

		for (int k = 0; k < tSubSignObj.length; k++) {
			String tTempStr = tSubSignObj[k];

			if ("1".equals(tTempStr.substring(3))) {
				String tTempCStr = "";
				if ((k + 1) == 1) {
					tTempCStr = BqNameFun.getCodeName(String.valueOf(k + 1),
							"bssignobj")
							+ tAppntSSRS.GetText(1, 1)
							+ "("
							+ tAppntSSRS.GetText(1, 2) + ")/";
					if (!"3".equals(mLPEdorItemSchema.getStandbyFlag1())) {
						xmlexport.addDisplayControl("displayBSApp");
					}

				} else if ((k + 1) == 2) {
					tTempCStr = BqNameFun.getCodeName(String.valueOf(k + 1),
							"bssignobj")
							+ tInsuredSSRS.GetText(1, 1)
							+ "("
							+ tInsuredSSRS.GetText(1, 2) + ")/";
					if (!"3".equals(mLPEdorItemSchema.getStandbyFlag1())) {
						xmlexport.addDisplayControl("displayBSIns");
					}

				} else if ((k + 1) == 3) {
					tTempCStr = "被保险人" + tInsuredSSRS.GetText(1, 1) + "("
							+ tInsuredSSRS.GetText(1, 2) + ")的法定监护人/";
					if (!"3".equals(mLPEdorItemSchema.getStandbyFlag1())) {
						xmlexport.addDisplayControl("displayBSCer");
					}

				}
				tAppntAndInsuredName += tTempCStr;
			}

		}
		logger.debug("字符串:" + tStandByFlag3);

		String tDate = mLPEdorItemSchema.getEdorValiDate();
		tDate = BqNameFun.getFDate(tDate);

		if (tAppntAndInsuredName.length() > 0) {
			tAppntAndInsuredName = tAppntAndInsuredName.substring(0,
					tAppntAndInsuredName.lastIndexOf("/"));
		}
		if (tAppntCertifyCard.length() > 0) {
			tAppntCertifyCard = tAppntCertifyCard.substring(0,
					tAppntCertifyCard.lastIndexOf("/"));
		}
		mTextTag.add("AppntAndInsuredName", tAppntAndInsuredName);
		mTextTag.add("AppntCertifyCard", tAppntCertifyCard);

		mTextTag.add("InsuredName", tInsuredSSRS.GetText(1, 1));
		mTextTag.add("AppntName", tAppntSSRS.GetText(1, 1));

		/*
		 * 设置displayTail1中的信息
		 */
		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			return false;
		}
		xmlexport.addTextTag(mTextTag);
		xmlexport.addTextTag(mTextTag);
		// xmlexport.outputDocumentToFile("d:\\", "PEdorSCPrint"); // 测试用
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
