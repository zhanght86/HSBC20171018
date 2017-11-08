package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: PEdorFMPrintBL.java
 * </p>
 * 
 * <p>
 * Description: 万能险部分批单打印
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007-06-01
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zengyg
 * 
 * @version 1.0
 */
public class PEdorOPPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorOPPrintBL.class);

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

	private LJAGetSet mLJAGetSet = new LJAGetSet();

	/** 险种名称 */
	private String tRiskName = "";

	public PEdorOPPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);
		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("OP数据传输失败!");
			return false;
		}
		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("OP数据传入无效!");
			return false;
		}
		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("OP数据处理失败!");
			return false;
		}
		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("OP数据准备失败!");
			return false;
		}
		logger.debug("End preparing the data to print ====>" + mOperate);
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		mLJAGetSet = (LJAGetSet) mInputData.getObjectByObjectName("LJAGetSet",
				0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTOP")) {
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
		xmlexport.addDisplayControl("displayOP");

		/*
		 * 设置displayHead1中的信息
		 */

		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo());// 保单号

		/*
		 * 设置displayOP中的信息
		 */
		mTextTag.add("LCCont.AppntName", tLCContSchema.getAppntName());// 投保人
		mTextTag.add("AppDate", BqNameFun.getFDate(mLPEdorItemSchema
				.getEdorAppDate()));// 保全申请年

		ExeSQL tExeSQL = new ExeSQL();
		// 领取金额

		String strSQL1 = "SELECT abs(case when Sum(GetMoney) is not null then Sum(GetMoney) else 0 end)"
				+ " FROM LJSGetEndorse" + " WHERE EndorsementNo='?EndorsementNo?'"
				+ " and FeeOperationType='?FeeOperationType?' and SubFeeOperationType='?SubFeeOperationType?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL1);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("SubFeeOperationType", BqCode.Get_InvestAccValue);
		String tGetMoney = tExeSQL.getOneValue(sqlbv);
		mTextTag.add("GetMoney", BqNameFun.getRound(tGetMoney)); // 领取金额

		// 手续费
		String tWorkNoteFee = mLPEdorItemSchema.getStandbyFlag1();
		mTextTag.add("WorkNoteFee", tWorkNoteFee); // 手续费
		/**
		 * **********************add by pst on
		 * 20070808**************************
		 */
		// 险种名称
		String tSQL = "select trim(replace(riskname,origriskcode,null)) from lmrisk where riskcode = (select riskcode from lcpol where appflag='1' and mainpolno = polno and contno='?contno?')";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tSQL);
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		tRiskName = tExeSQL.getOneValue(sbv);
		if ("".equals(tRiskName)) {
			CError tError = new CError();
			tError.moduleName = "PEdorOPPrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "险种名称查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mTextTag.add("RiskName", tRiskName); // 余额
		/** ***********************end add****************************** */
		// 帐户余额
		mTextTag.add("InsuAccBala", BqNameFun.getRound(mLPEdorItemSchema
				.getStandbyFlag2())); // 余额
		/** ******************changed end************************* */

		/*
		 * 设置displayTail1中的信息
		 */
		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
		BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema,
				xmlexport, mTextTag);
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
