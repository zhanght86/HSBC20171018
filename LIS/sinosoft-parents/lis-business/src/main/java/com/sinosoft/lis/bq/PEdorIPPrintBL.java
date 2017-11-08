package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.*;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: 保全追加保费批单
 * </p>
 * <p>
 * Description: 追加保费批单数据生成
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Luzhe
 * @Rewrite QianLy 2007-10-30
 * @version 1.0
 */

public class PEdorIPPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PEdorIPPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	public CErrors mErrors = new CErrors();

	String CurrentDate = PubFun.getCurrentDate();

	// 全局变量
	private VData mInputData = new VData();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ListTable mListTableOut = new ListTable();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private String[] strNSS;

	private boolean isTL = false;// 投连险标志

	public PEdorIPPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("IP数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("IP传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("IP数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("IP准备数据失败!");
			return false;
		}

		return true;
	}

	private boolean getInputData() {

		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPEdorItemSchema == null) {
			mErrors.addOneError("IP得到数据失败!");
			return false;
		}
		return true;
	}

	private boolean checkData() {
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
		String flag = BqNameFun
				.getAnother(sqlbv,
						"LMRiskApp",
						"riskcode in (select riskcode from lcpol where polno = mainpolno and contno = '?ContNo?') and risktype3", "3", "'X'");
		if (flag != null && "X".equals(flag)) {
			isTL = true;
		} else {
			isTL = false;
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

		// xmlexport.createDocument("PrtEndorsement.vts", "IGPrinter");

		/** 要显示的部分 */
		xmlexport.addDisplayControl("displayHead1");
		xmlexport.addDisplayControl("displayTail1");
		BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		/** 批单头部 */
		mTextTag.add("AppntName", tLCContSchema.getAppntName()); // 原投保人姓名
		mTextTag.add("InsuredName", tLCContSchema.getInsuredName()); // 被保人姓名
		mTextTag.add("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo()); // 保全受理号
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo()); // 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号

		mTextTag.add("LCCont.AppntName", tLCContSchema.getAppntName()); //
		mTextTag.add("AppDate", mLPEdorItemSchema.getEdorAppDate()); //

		String AddMoney = "";
		if (isTL) {// 投连取数
			LJSGetEndorseDB kmLJSGetEndorseDB = new LJSGetEndorseDB();
			kmLJSGetEndorseDB.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			kmLJSGetEndorseDB.setFeeOperationType(mLPEdorItemSchema
					.getEdorType());
			LJSGetEndorseSchema kmLJSGetEndorseSchema = new LJSGetEndorseSchema();
			kmLJSGetEndorseSchema = kmLJSGetEndorseDB.query().get(1);
			AddMoney = String.valueOf(kmLJSGetEndorseSchema.getGetMoney());
		} else {// 万能取数
			AddMoney = BqNameFun.getAnother(null,"LPEdorItem", "EdorNo",
					mLPEdorItemSchema.getEdorNo(), "(case when getMoney is not null then getMoney else 0 end)");
		}
		mTextTag.add("AddMoney", AddMoney); // 投连追加保费金额

		/** 投连追加保费 */
		String strSQL = "select distinct(a.insuaccno),b.insuaccname,a.payplancode,c.payplanname,a.Money from lpinsureacctrace a,lmriskinsuacc b,lmriskaccpay c "
				+ " where a.edorno = '?edorno?' "
				+ " and a.insuaccno = b.insuaccno "
				+ " and a.payplancode = c.payplancode";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strSQL);
		sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sbv1);

		if (tSSRS != null || tSSRS.MaxRow > 0) {
			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				mListTableOut.setName("IPout");
				strNSS = new String[6];
				if (isTL) {// 投连取数
					strNSS[0] = tSSRS.GetText(j, 1);
					strNSS[1] = tSSRS.GetText(j, 2);
					strNSS[2] = tSSRS.GetText(j, 3);
					strNSS[4] = tSSRS.GetText(j, 5);

					String SQL = "select InvestRate from LPPerInvestPlan where EdorNo = '?EdorNo?' "
							+ " and PayPlanCode = '?PayPlanCode?' "
							+ " and InsuAccNo = '?InsuAccNo?'";
					SQLwithBindVariables sbv2=new SQLwithBindVariables();
					sbv2.sql(SQL);
					sbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
					sbv2.put("PayPlanCode", strNSS[2]);
					sbv2.put("InsuAccNo", strNSS[0]);
					double money1 = Double.parseDouble(AddMoney)
							* Double.parseDouble(tExeSQL.getOneValue(sbv2));
					strNSS[3] = String.valueOf(Double.parseDouble(tExeSQL
							.getOneValue(sbv2)));
					strNSS[5] = String.valueOf(money1
							- Double.parseDouble(strNSS[4]));
				} else {
					strNSS[0] = tSSRS.GetText(j, 1);
					strNSS[1] = tSSRS.GetText(j, 2);
					strNSS[2] = tSSRS.GetText(j, 3);
					strNSS[3] = "95%";
					strNSS[4] = tSSRS.GetText(j, 5);
					strNSS[5] = String.valueOf(Double.parseDouble(AddMoney)
							- Double.parseDouble(strNSS[4]));
				}
				mListTableOut.add(strNSS);
			}

			xmlexport.addDisplayControl("displayIP");
		}

		// 查询银行信息
		if (isTL) {
			xmlexport.addDisplayControl("displayIPBank");
		} else {
			xmlexport.addDisplayControl("displayBank");
		}

		String QUERTBank = "select * from lpedorapp where edoracceptno = '?edoracceptno?'";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(QUERTBank);
		sbv3.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema = tLPEdorAppDB.executeQuery(sbv3).get(1);
		String QUBankName = "select bankname from ldbank where bankcode = '?bankcode?'";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(QUBankName);
		sbv4.put("bankcode", tLPEdorAppSchema.getBankCode());
		ExeSQL lzExeSQL = new ExeSQL();
		mTextTag.add("BankName", lzExeSQL.getOneValue(sbv4));
		mTextTag.add("AccountNo", tLPEdorAppSchema.getBankAccNo());
		mTextTag.add("AccountName", tLPEdorAppSchema.getAccName());

		/*
		 * 设置displayTail1中的信息
		 */
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
		String[] c_strNSOut = new String[9];
		// String[] c_strNSIn = new String[9];
		xmlexport.addListTable(mListTableOut, c_strNSOut);
		// xmlexport.addListTable(mListTableIn,c_strNSIn);
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		try {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema.setEdorNo("6020071222000002");
			LPEdorItemDB tt = tLPEdorItemSchema.getDB();
			tt.query().get(1); // 不tt.query()可能得到null或则size为0的*Set,此风格代码只能用于调试
			tLPEdorItemSchema.setSchema(tt.query().get(1).getSchema());
			VData tVData = new VData();
			tVData.add(tLPEdorItemSchema);
			PEdorIPPrintBL tBl = new PEdorIPPrintBL();
			tBl.submitData(tVData, "");
			VData rVDta = tBl.getResult();
			XmlExportNew xmlexport = (XmlExportNew) rVDta
					.getObjectByObjectName("XmlExportNew", 0);
			/** 暂时用于调试把信息暂存在文件中，应该放在表lpedorprint的edorinfo字段，日后该之 */
			xmlexport.outputDocumentToFile("D:\\work", "myPG.xml", false);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
