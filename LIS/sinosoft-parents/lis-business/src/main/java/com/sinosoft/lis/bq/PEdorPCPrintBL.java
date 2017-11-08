package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPContSchema;
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
 * 
 * <p>
 * Description: 交费信息变更（PC）批单打印
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

public class PEdorPCPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorPCPrintBL.class);

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

	public PEdorPCPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("PC数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("PC数据传入无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("PC数据处理失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("PC数据准备失败!");
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
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTPC")) {
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
		xmlexport.addDisplayControl("displayPC");
		xmlexport.addDisplayControl("displayPCTail");

		CodeNameQuery tCodeNameQuery = new CodeNameQuery();
		ExeSQL tExeSQL = new ExeSQL();

		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		if (!"".equals(tLCContSchema.getPayLocation())
				&& tLCContSchema.getPayLocation() != null) {
			mTextTag.add("PCLCCont.PayMode", tCodeNameQuery.getName(
					"paylocation", tLCContSchema.getPayLocation()));
		} else {
			mTextTag.add("PCLCCont.PayMode", "");
		}

		if ("0".equals(tLCContSchema.getPayLocation())) {
			xmlexport.addDisplayControl("displayPCCBank");
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("select bankname from ldbank where bankcode = '?bankcode?'");
			sqlbv.put("bankcode", tLCContSchema.getBankCode());
			mTextTag
					.add(
							"PCLCBank.BankName",
							tExeSQL
									.getOneValue(sqlbv));// 银行名称
			mTextTag.add("PCLCCont.BankAccNo", tLCContSchema.getBankAccNo());// 银行帐号
			mTextTag.add("PCLCCont.AccName", tLCContSchema.getAccName());// 帐户名
			mTextTag.add("PCLCCont.Relation", "本人");// 账户所有人与投保人关系
		}

		LPContDB tLPContDB = new LPContDB();
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());

		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLPContSchema = tLPContDB.getSchema();

		mTextTag.add("PCLPCont.PayMode", tCodeNameQuery.getName("paylocation",
				tLPContSchema.getPayLocation()));
		if ("0".equals(tLPContSchema.getPayLocation())) {
			xmlexport.addDisplayControl("displayPCPBank");
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("select bankname from ldbank where bankcode = '?bankcode?'");
			sqlbv.put("bankcode", tLPContSchema.getBankCode());
			mTextTag
					.add(
							"PCLDBank.BankName",
							tExeSQL
									.getOneValue(sqlbv));// 银行名称
			mTextTag.add("PCLPCont.BankAccNo", tLPContSchema.getBankAccNo());// 银行帐号
			mTextTag.add("PCLPCont.AccName", tLPContSchema.getAccName());// 帐户名
			mTextTag.add("PCLPCont.Relation", "本人");// 账户所有人与投保人关系，由于账号存入的就是投保人的帐户，所以关系是本人
		}
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
