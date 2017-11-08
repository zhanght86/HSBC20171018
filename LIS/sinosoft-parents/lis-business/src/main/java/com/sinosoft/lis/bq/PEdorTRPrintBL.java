package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 保单自垫清偿批单
 * </p>
 * 
 * <p>
 * Description: 数据生成
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author changpeng
 * @version 1.0
 */

public class PEdorTRPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorTRPrintBL.class);

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

	public PEdorTRPrintBL() {

	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("TR数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("TR数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("准备数据失败!");
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
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		mLJAGetSet = (LJAGetSet) mInputData.getObjectByObjectName("LJAGetSet",
				0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTTR")) {
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
		xmlexport.addDisplayControl("displayTR");
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContDB.getSchema();
		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		double tSumLoanZD = 0, tSumMoney = 0;
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());

		tLPEdorItemSet = tLPEdorItemDB.query();

		for (int t = 1; t <= tLPEdorItemSet.size(); t++) {
			LPEdorItemSchema rLPEdorItemSchema = tLPEdorItemSet.get(t);
			tSumLoanZD += Double.parseDouble(rLPEdorItemSchema
					.getStandbyFlag1());

			tSumMoney += rLPEdorItemSchema.getGetMoney();
		}

		// 实际换的本息和
		String tData[] = BqNameFun.getContNextPrem(mLPEdorItemSchema,
				mLPEdorItemSchema.getEdorAppDate());
		mTextTag.add("SumMoneyAndIn", PubFun.getChnMoney(tSumMoney) + "(￥"
				+ tSumMoney + ")"); // 所欠本息和

		mTextTag.add("LeaveMoeny", PubFun.getChnMoney(Double
				.parseDouble(tData[1]))
				+ "(￥" + tData[1] + ")");

		mTextTag.add("SumMoney", PubFun.getChnMoney(tSumMoney) + "(￥"
				+ tSumMoney + ")"); // 本次还款金额

		BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema,
				xmlexport, mTextTag);
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
