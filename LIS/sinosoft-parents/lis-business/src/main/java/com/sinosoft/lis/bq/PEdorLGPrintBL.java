package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: PEdorDBPrintBL.java
 * </p>
 * 
 * <p>
 * Description: 生存金领取批单打印
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
 * @author pst
 * 
 * @version 1.0
 */
public class PEdorLGPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorLGPrintBL.class);

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

	private ListTable tlistTable = new ListTable();

	private LJAGetSet mLJAGetSet = new LJAGetSet();

	public PEdorLGPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);
		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("LG数据传输失败!");
			return false;
		}
		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("LG数据传入无效!");
			return false;
		}
		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("LG数据处理失败!");
			return false;
		}
		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("LG数据准备失败!");
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
		if (!mOperate.equals("PRINTLG")) {
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
		xmlexport.addDisplayControl("displayLG");

		tlistTable.setName("LG");

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		ExeSQL tExeSQL = new ExeSQL();
		// 领取金额

		String strSQL1 = "SELECT riskcode,feefinatype,getmoney,polno"
				+ " FROM LJSGetEndorse" + " WHERE EndorsementNo='?EndorsementNo?'"
				+ " and FeeOperationType='?FeeOperationType?' and getflag='1'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strSQL1);
		sbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		SSRS tSSRS = tExeSQL.execSQL(sbv1);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			mErrors.addOneError("取保单领取信息失败保单信息失败!");
			return false;
		} else {
			for (int k = 1; k <= tSSRS.MaxRow; k++) {
				String rStrArray[] = new String[3];
				rStrArray[0] = BqNameFun.getRiskShortName(tSSRS.GetText(k, 1))
						+ "--" + tSSRS.GetText(k, 1);
				String sql = " select a.codealias from ldcode1 a where  codetype='LG' and code='?code?' ";
				SQLwithBindVariables sbv2=new SQLwithBindVariables();
				sbv2.sql(sql);
				sbv2.put("code", tSSRS.GetText(k, 2));
				// rStrArray[1] = BqCalBL.getFinType("LG", tSSRS.GetText(k, 2),
				// tSSRS.GetText(k, 4));
				rStrArray[1] = tExeSQL.getOneValue(sbv2);
				rStrArray[2] = tSSRS.GetText(k, 3);
				tlistTable.add(rStrArray);
			}
		}
		String strArr[] = new String[3];
		xmlexport.addListTable(tlistTable, strArr);
		String tTemp = BqNameFun.NVL(BqNameFun.getSumGetMoney(
				mLPEdorItemSchema, mLPEdorItemSchema.getContNo()), "0.00");
		mTextTag.add("SumLQ", PubFun.getChnMoney(Double.parseDouble(tTemp))
				+ "(￥" + tTemp + ")");//

		String strSQL2 = "SELECT sum(getmoney)" + " FROM LJSGetEndorse"
				+ " WHERE EndorsementNo='?EndorsementNo?'" + " and FeeOperationType='?FeeOperationType?' and getflag='0'";
        SQLwithBindVariables sbv3=new SQLwithBindVariables();
        sbv3.sql(strSQL2);
        sbv3.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
        sbv3.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		String LoanMoney = tExeSQL.getOneValue(sbv3);
		if (!LoanMoney.equals("")) {
			mTextTag.add("LoanMoney", "保单借款本息合计："
					+ PubFun.getChnMoney(Double.parseDouble(LoanMoney)) + "(￥"
					+ BqNameFun.getRound(LoanMoney) + ")");
		}

		String strSQL3 = "SELECT sum((case getflag when '0' then -getmoney else getmoney end))"
				+ " FROM LJSGetEndorse" + " WHERE EndorsementNo='?EndorsementNo?'"
				+ " and FeeOperationType='?FeeOperationType?'";
        SQLwithBindVariables sbv4=new SQLwithBindVariables();
        sbv4.sql(strSQL3);
        sbv4.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
        sbv4.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		String SumMoney = tExeSQL.getOneValue(sbv4);
		if (!SumMoney.equals("")) {
			mTextTag.add("SumMoney", PubFun.getChnMoney(Double
					.parseDouble(SumMoney))
					+ "(￥" + BqNameFun.getRound(SumMoney) + ")");
		}

		BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema,
				xmlexport, mTextTag);

		/*
		 * 设置displayTail1中的信息
		 */
		// 保全确认时显示保全生效日
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
