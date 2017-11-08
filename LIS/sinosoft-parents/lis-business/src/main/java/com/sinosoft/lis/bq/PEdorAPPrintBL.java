package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: PEdorPCPrintBL.java
 * </p>
 * <p>
 * Description: 保费自垫申请、终止（AP）批单打印
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wangyan
 * @version 1.0
 */

public class PEdorAPPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PEdorAPPrintBL.class);

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


	public PEdorAPPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("AP数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("AP数据传入无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("PC数据处理失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("AP数据准备失败!");
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTAP")) {
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
		xmlexport.addDisplayControl("displayAP");

		// mTextTag.add ("AppName",mLPEdorAppSchema.getEdorAppName())
		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 申请人的姓名

		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql("select AutoPayFlag from lpcont where edorno = '"
				+ "?edorno?" + "' and edortype = '"
				+ "?edortype?" + "' and contno = '"
				+ "?contno?" + "'");
		sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		String tAutoPayFlag = tExeSQL
				.getOneValue(sbv);
		if ("1".equals(tAutoPayFlag)) {
			String mAutoPayName = BqNameFun.getCodeName(tAutoPayFlag,
					"autopaytype");
			mTextTag.add("AutoPayName", mAutoPayName);
			mTextTag.add("PreAutoPayName", BqNameFun.getCodeName("0",
					"autopaytype"));
		} else if ("0".equals(tAutoPayFlag)) {
			String mAutoPayName = BqNameFun.getCodeName(tAutoPayFlag,
					"autopaytype");
			mTextTag.add("AutoPayName", mAutoPayName);
			mTextTag.add("PreAutoPayName", BqNameFun.getCodeName("1",
					"autopaytype"));
		} else {
			mErrors.addOneError("打印生成数据时，自垫标志位数据有误!");
			return false;
		}

		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
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
