package com.sinosoft.lis.bq;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMRiskDB;
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
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 协议退保
 * </p>
 * 
 * <p>
 * Description: 协议退保批单数据生成
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author pst
 * @version 1.0
 */

public class PEdorXTPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorXTPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private VData mInputData = new VData();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ListTable tXTListTable = new ListTable();

	private LJAGetSet mLJAGetSet = new LJAGetSet();

	/** 退保标志，确定是整单退还是只退附加险(0,只退附加险，1 退全部) */
	@SuppressWarnings("unused")
	private String mWTContFLag = "";

	/** 保全保单结算计算类 */

	public PEdorXTPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("XT数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("XT数据打印失败!");
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
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		mLJAGetSet = (LJAGetSet) mInputData.getObjectByObjectName("LJAGetSet",
				0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		if (mLPEdorAppSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTXT")) {
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
		mWTContFLag = mLPEdorItemSchema.getStandbyFlag2();
		return true;
	}

	private boolean dealData() {
		BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displayXT");
		ExeSQL exeSQL = new ExeSQL();

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		// 退保信息
		tXTListTable.setName("XT");
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
		String strSQL = "";
		String tAccBala[][] = BqNameFun.getInsuaccContMoney(mLPEdorItemSchema);
		if (tAccBala != null && tAccBala.length > 0) {
			for (int i = 0; i < tAccBala.length; i++) {

				// 万能险退保金额不需要显示帐户金额，由于退保清算按基本保额应退金额进行计算 Get_BaseCashValue--G006
				// if(tAccBala[i][0]!=null&&tAccBala[i][0].equals("000006"))
				// {
				// xmlexport.addDisplayControl ("displayXTAcc"+tAccBala[i][0]) ;
				// mTextTag.add
				// ("AccBala",PubFun.getChnMoney(Double.parseDouble(tAccBala[i][1]))+"(￥"+tAccBala[i][1]+")")
				// ;
				// //mTextTag.add
				// ("BonusMoney",PubFun.getChnMoney(0.00)+"(￥"+0.00+")") ;
				// }
				//        	
				// else
				if (tAccBala[i][0] != null && tAccBala[i][0].equals("000001"))// 累计生息红利帐户
				{
					xmlexport
							.addDisplayControl("displayXTAcc" + tAccBala[i][0]);
					mTextTag.add("BonusAccHLLJ", PubFun.getChnMoney(Double
							.parseDouble(tAccBala[i][1]))
							+ "(￥" + tAccBala[i][1] + ")");
				}

				else if (tAccBala[i][0] != null
						&& tAccBala[i][0].equals("000007"))// 抵交保费红利帐户
				{
					xmlexport
							.addDisplayControl("displayXTAcc" + tAccBala[i][0]);
					mTextTag.add("BonusAccHLDJ", PubFun.getChnMoney(Double
							.parseDouble(tAccBala[i][1]))
							+ "(￥" + tAccBala[i][1] + ")");
				}

				else if (tAccBala[i][0] != null
						&& tAccBala[i][0].equals("000008"))// 现金红利帐户
				{
					xmlexport
							.addDisplayControl("displayXTAcc" + tAccBala[i][0]);
					mTextTag.add("BonusAccHLXJ", PubFun.getChnMoney(Double
							.parseDouble(tAccBala[i][1]))
							+ "(￥" + tAccBala[i][1] + ")");
				}

				else if (tAccBala[i][0] != null
						&& tAccBala[i][0].equals("000005"))// 生存金累计生息帐户
				{
					xmlexport
							.addDisplayControl("displayXTAcc" + tAccBala[i][0]);
					mTextTag.add("BonusAccHLYF", PubFun.getChnMoney(Double
							.parseDouble(tAccBala[i][1]))
							+ "(￥" + tAccBala[i][1] + ")");
				}

				// else if(tAccBala[i][0].equals("000009"))//满期金帐户
				// {
				// xmlexport.addDisplayControl ("displayXTAcc"+tAccBala[i][0]) ;
				// mTextTag.add
				// ("BonusAccHLEF",PubFun.getChnMoney(Double.parseDouble(tAccBala[i][1]))+"(￥"+tAccBala[i][1]+")")
				// ;
				// }
			}
		}

		// 保单余额
		strSQL = "SELECT abs(Sum(GetMoney))" + " FROM LJSGetEndorse"
				+ " where EndorsementNo='?EndorsementNo?'" + " and FeeOperationType='?FeeOperationType?'"
				+ " and  SubFeeOperationType like concat(concat('%','?SubFeeOperationType?'),'%') ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("SubFeeOperationType", BqCode.Get_Prem);
		String sumYE = exeSQL.getOneValue(sqlbv);
		if (!"".equals(sumYE) && !"0".equals(sumYE)) {
			xmlexport.addDisplayControl("displayXTYE");
			mTextTag.add("SumYE", PubFun.getChnMoney(Double.parseDouble(sumYE))
					+ "(￥" + sumYE + ")");
		}

		// 自垫本金加利息
		strSQL = "SELECT abs(Sum(GetMoney))" + " FROM LJSGetEndorse"
				+ " where EndorsementNo='?EndorsementNo?'" + " and FeeOperationType='?FeeOperationType?'"
				+ " and ( SubFeeOperationType like concat(concat('%','?SubFeeOperationType1?'),'%') or SubFeeOperationType like concat(concat('%','?SubFeeOperationType2?'),'%'))";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv1.put("SubFeeOperationType1", BqCode.Pay_AutoPayPrem);
		sqlbv1.put("SubFeeOperationType2", BqCode.Pay_AutoPayPremInterest);
		String tSumZDLoan = exeSQL.getOneValue(sqlbv1);
		if (!"".equals(tSumZDLoan) && !"0".equals(tSumZDLoan)) {
			xmlexport.addDisplayControl("displayXTZD");
			mTextTag.add("SumLoanZD", PubFun.getChnMoney(Double
					.parseDouble(tSumZDLoan))
					+ "(￥" + tSumZDLoan + ")");
		}

		// 未清偿贷款本金加利息
		strSQL = "SELECT abs(Sum(GetMoney))" + " FROM LJSGetEndorse"
				+ " where EndorsementNo='?EndorsementNo?'" + " and FeeOperationType='?FeeOperationType?'"
				+ " and (SubFeeOperationType like concat(concat('%','?SubFeeOperationType1?'),'%') or SubFeeOperationType like concat(concat('%','?SubFeeOperationType2?'),'%'))";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(strSQL);
		sqlbv2.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv2.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv2.put("SubFeeOperationType1", BqCode.Pay_LoanCorpus);
		sqlbv2.put("SubFeeOperationType2", BqCode.Pay_LoanCorpusInterest);
		String SumLoan = exeSQL.getOneValue(sqlbv2);
		if (!"".equals(SumLoan) && !"0".equals(SumLoan)) {
			xmlexport.addDisplayControl("displayXTLoan");
			mTextTag.add("SumLoan", PubFun.getChnMoney(Double
					.parseDouble(SumLoan))
					+ "(￥" + SumLoan + ")");
		}

		String tSumActGetMoney = BqNameFun.getSumEdorContMoney(
				mLPEdorItemSchema, mLPEdorItemSchema.getContNo());
		if (!"".equals(tSumActGetMoney)) {
			xmlexport.addDisplayControl("displayXTSum");
			mTextTag.add("tSumActGetMoney", PubFun.getChnMoney(Double
					.parseDouble(tSumActGetMoney))
					+ "(￥" + tSumActGetMoney + ")");
		}

		xmlexport.addDisplayControl("displayXTEnd");
		// 处理项目收付费信息

		// BqNameFun.AddEdorPayGetInfo( mLPEdorItemSchema,
		// mLPEdorAppSchema, xmlexport,
		// mTextTag);
		BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema,
				xmlexport, mTextTag);
		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);
		return true;
	}

	private boolean dealDataPolNo(LPPolSchema tLPPolSchema) {
		// 险种名称
		ExeSQL exeSQL = new ExeSQL();
		LMRiskDB tLMRiskDB = new LMRiskDB();
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
		if (!tLMRiskDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取附加险名称失败!");
			return false;
		}
		tLMRiskSchema = tLMRiskDB.getSchema();
		// String Moneyf = BqNameFun.getSumEdorPolMoney(mLPEdorItemSchema,
		// tLPPolSchema.getPolNo()) ;
		String sql = "select sum((case getflag when '0' then -getmoney else getmoney end)) from ljsgetendorse where polno='?polno?' and endorsementno in ("
				+ " select edorno from lpedoritem where edoracceptno='?edoracceptno?') and  SubFeeOperationType in(?SubFeeOperationType?)" + " and polno='?polno?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		ArrayList<String> strArr=new ArrayList<String>();
		strArr.add(BqCode.Get_BaseCashValue);
		strArr.add(BqCode.Get_BonusCashValue);
		sqlbv3.sql(sql);
		sqlbv3.put("polno", tLPPolSchema.getPolNo());
		sqlbv3.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
		sqlbv3.put("SubFeeOperationType", strArr);
		String Moneyf = exeSQL.getOneValue(sqlbv3);
		String[] strCT = new String[3];
		strCT[0] = tLMRiskSchema.getRiskName(); // 险种名称
		strCT[1] = tLMRiskSchema.getRiskCode(); // 险种代码
		if (!"".equals(Moneyf)) {
			strCT[2] = BqNameFun.getRound(Moneyf); // 应退金额
		} else {
			strCT[2] = "0.00"; // 应退金额
		}
		tXTListTable.add(strCT);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		String[] b_strCT = new String[3];
		xmlexport.addListTable(tXTListTable, b_strCT);
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
