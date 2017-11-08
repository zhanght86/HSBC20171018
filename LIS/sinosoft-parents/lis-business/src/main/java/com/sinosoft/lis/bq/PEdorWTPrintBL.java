package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 保单退保
 * </p>
 * 
 * <p>
 * Description: 保单退保批单数据生成
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author PST
 * @version 1.0
 */

public class PEdorWTPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorWTPrintBL.class);

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

	private ListTable tWTListTable = new ListTable();

	private LJAGetSet mLJAGetSet = new LJAGetSet();

	/** 保全保单结算计算类 */

	public PEdorWTPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("WT数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("WT数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("准备数据失败!");
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
		if (!mOperate.equals("PRINTWT")) {
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
		xmlexport.addDisplayControl("displayWT");

		ExeSQL exeSQL = new ExeSQL();

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		// 退保信息
		tWTListTable.setName("WT");
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet == null || tLPPolSet.size() == 0) {
			mErrors.addOneError("打印生成数据时，取险种信息失败!");
			return false;
		}

		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LPPolSchema tLPPolSchema = tLPPolSet.get(i);
			if (!dealDataPolNo(tLPPolSchema)) {
				mErrors.addOneError("险种信息打印失败!");
				return false;
			}
		}

		// 账户余额
		String strSQL = " select insuaccbala from lcinsureacc where contno='?contno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		String tAccBala = exeSQL.getOneValue(sqlbv);
		if (!"".equals(tAccBala) && !"0".equals(tAccBala)) {
			xmlexport.addDisplayControl("displayWTAcc");
			mTextTag.add("AccBala", PubFun.getChnMoney(Double
					.parseDouble(tAccBala))
					+ "(￥" + tAccBala + ")");
		}

		// 工本费
		strSQL = "SELECT abs(Sum(GetMoney))" + " FROM LJSGetEndorse"
				+ " where EndorsementNo='?EndorsementNo?'" + " and FeeOperationType='?FeeOperationType?'"
				+ " and ( SubFeeOperationType like concat(concat('%','?SubFeeOperationType?'),'%'))";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("SubFeeOperationType", BqCode.Pay_WorkNoteFee);
		String tGBMoney = exeSQL.getOneValue(sqlbv);
		if (!"".equals(tGBMoney) && !"0".equals(tGBMoney)) {
			xmlexport.addDisplayControl("displayWTGB");
			mTextTag.add("GBMoney", PubFun.getChnMoney(Double
					.parseDouble(tGBMoney))
					+ "(￥" + tGBMoney + ")");
		}

		// 体检费用
		// strSQL = "SELECT abs(Sum(GetMoney))"
		// + " FROM LJSGetEndorse"
		// + " where EndorsementNo='" + mLPEdorItemSchema.getEdorNo () + "'"
		// + " and FeeOperationType='" + mLPEdorItemSchema.getEdorType () + "'"
		// + " and ( SubFeeOperationType like '%" + BqCode.Pay_AutoPayPrem + "%'
		// or SubFeeOperationType like '%" + BqCode.Pay_AutoPayPremInterest +
		// "%')" ;
		// String tSumHL = exeSQL.getOneValue (strSQL) ;
		// if (!"".equals(tSumHL))
		// {
		// xmlexport.addDisplayControl ("displayWTH") ;
		// mTextTag.add
		// ("HMoney",PubFun.getChnMoney(Double.parseDouble(tSumHL))+"(￥"+tSumHL+")")
		// ;
		// }

		// 自垫本金加利息
		strSQL = "SELECT abs(Sum(GetMoney))" + " FROM LJSGetEndorse"
				+ " where EndorsementNo='?EndorsementNo?'" + " and FeeOperationType='?FeeOperationType?'"
				+ " and ( SubFeeOperationType like concat(concat('%','?SubFeeOperationType1?'),'%') or SubFeeOperationType like concat(concat('%','?SubFeeOperationType2?'),'%'))";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("SubFeeOperationType1", BqCode.Pay_AutoPayPrem);
		sqlbv.put("SubFeeOperationType2", BqCode.Pay_AutoPayPremInterest);
		String tSumZDLoan = exeSQL.getOneValue(sqlbv);
		if (!"".equals(tSumZDLoan) && !"0".equals(tSumZDLoan)) {
			xmlexport.addDisplayControl("displayWTZD");
			mTextTag.add("SumLoanZD", PubFun.getChnMoney(Double
					.parseDouble(tSumZDLoan))
					+ "(￥" + tSumZDLoan + ")");
		}

		// 未清偿贷款本金加利息
		strSQL = "SELECT abs(Sum(GetMoney))" + " FROM LJSGetEndorse"
				+ " where EndorsementNo='?EndorsementNo?'" + " and FeeOperationType='?FeeOperationType?'"
				+ " and (SubFeeOperationType like concat(concat('%','?SubFeeOperationType1?'),'%') or SubFeeOperationType like concat(concat('%','?SubFeeOperationType2?'),'%'))";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("SubFeeOperationType1", BqCode.Pay_LoanCorpus);
		sqlbv.put("SubFeeOperationType2", BqCode.Pay_LoanCorpusInterest);
		String SumLoan = exeSQL.getOneValue(sqlbv);
		if (!"".equals(SumLoan) && !"0".equals(SumLoan)) {
			xmlexport.addDisplayControl("displayWTLoan");
			mTextTag.add("SumLoan", PubFun.getChnMoney(Double
					.parseDouble(SumLoan))
					+ "(￥" + SumLoan + ")");
		}

		// 实退金额
		String tSumActGetMoney = BqNameFun.getSumEdorContMoney(
				mLPEdorItemSchema, mLPEdorItemSchema.getContNo());
		if (!"".equals(tSumActGetMoney) && !"0".equals(tSumActGetMoney)) {
			xmlexport.addDisplayControl("displayWTSum");
			mTextTag.add("tSumActGetMoney", PubFun.getChnMoney(Double
					.parseDouble(tSumActGetMoney))
					+ "(￥" + tSumActGetMoney + ")");
		}

		// 如果不是整单退保，才需要显示下期需要交的保费
		if (!"1".equals(mLPEdorItemSchema.getStandbyFlag3())) {
			xmlexport.addDisplayControl("displayWTNext");
			String tData[] = BqNameFun.getContNextPrem(mLPEdorItemSchema,
					mLPEdorItemSchema.getEdorAppDate());
			mTextTag.add("NextPayToDate", BqNameFun.getChDate(tData[0]));// 下一个缴费期
			mTextTag.add("NextPayMoney", PubFun.getChnMoney(Double
					.parseDouble(tData[1]))
					+ "(￥" + tData[1] + ")");// 下期保费
		}

		// xmlexport.addDisplayControl ("displayWTEnd") ;
		// 处理项目收付费信息

		// BqNameFun.AddEdorPayGetInfo( mLPEdorItemSchema,
		// mLPEdorAppSchema, xmlexport,
		// mTextTag);
		BqNameFun.AddEdorGetInfo(mLJAGetSet, mLPEdorAppSchema, xmlexport,
				mTextTag);
		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);
		return true;
	}

	private boolean dealDataPolNo(LPPolSchema tLPPolSchema) { // 险种名称
		LMRiskDB tLMRiskDB = new LMRiskDB();
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
		if (!tLMRiskDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取附加险名称失败!");
			return false;
		}
		tLMRiskSchema = tLMRiskDB.getSchema();
		String Moneyf = BqNameFun.getSumEdorPolMoney(mLPEdorItemSchema,
				tLPPolSchema.getPolNo());
		String[] strCT = new String[3];
		strCT[0] = tLMRiskSchema.getRiskName(); // 险种名称
		strCT[1] = tLMRiskSchema.getRiskCode(); // 险种代码
		if (!"".equals(Moneyf)) {
			strCT[2] = BqNameFun.getRound(Moneyf); // 应退金额
		} else {
			strCT[2] = "0.00"; // 应退金额
		}
		tWTListTable.add(strCT);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		String[] b_strWT = new String[3];
		xmlexport.addListTable(tWTListTable, b_strWT);
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
