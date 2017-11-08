package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
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
 * Description: 分红险领取批单打印
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
public class PEdorDBPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorDBPrintBL.class);

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

	public PEdorDBPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);
		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("DB数据传输失败!");
			return false;
		}
		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("DB数据传入无效!");
			return false;
		}
		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("DB数据处理失败!");
			return false;
		}
		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("DB数据准备失败!");
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
		if (!mOperate.equals("PRINTDB")) {
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
		xmlexport.addDisplayControl("displayDB");

		tlistTable.setName("DB");

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		ExeSQL tExeSQL = new ExeSQL();
		// 领取金额
		double tSumMoney = 0;
		String strSQL1 = "SELECT riskcode,SubFeeOperationType,getmoney,polno"
				+ " FROM LJSGetEndorse" + " WHERE EndorsementNo='?EndorsementNo?'"
				+ " and FeeOperationType='?FeeOperationType?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL1);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			mErrors.addOneError("取保单领取信息失败保单信息失败!");
			return false;
		} else {
			for (int k = 1; k <= tSSRS.MaxRow; k++) {
				String rStrArray[] = new String[3];
				rStrArray[0] = BqNameFun.getRiskShortName(tSSRS.GetText(k, 1))
						+ "--" + tSSRS.GetText(k, 1);
				rStrArray[1] = getCodeName(tSSRS.GetText(k, 2), "BQSubFeeType");
				rStrArray[2] = tSSRS.GetText(k, 3);
				tSumMoney += Double.parseDouble(tSSRS.GetText(k, 3));
				tlistTable.add(rStrArray);
			}
		}
		String strArr[] = new String[3];
		xmlexport.addListTable(tlistTable, strArr);
		mTextTag.add("SumGetMoney", PubFun.getChnMoney(PubFun.round(tSumMoney,
				2))
				+ "(￥" + PubFun.round(tSumMoney, 2) + ")");
		// if("4".equals(mLPEdorAppSchema.getPayForm()))
		// {
		// xmlexport.addDisplayControl("displayDBAdd");
		// mTextTag.add ("BankName",mLPEdorAppSchema.getEdorAppName()) ;
		// mTextTag.add ("AccName",mLPEdorAppSchema.getAccName()) ;
		// mTextTag.add ("AccNo",mLPEdorAppSchema.getBankAccNo()) ;
		// }
		/*
		 * 设置displayTail1中的信息
		 */
		// 保全确认时显示保全生效日
		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
		BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema,
				xmlexport, mTextTag);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);

		return true;
	}

	/**
	 * 通过代码、代码类型到ldcode表中得到其名称
	 * 
	 * @param tCode,tCodeType
	 * @return String
	 */
	public static String getCodeName(String tCode, String tCodeType) {
		if (tCodeType == null || tCodeType.trim().equals("")) {
			return "";
		}
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(tCode);
		tLDCodeDB.setCodeType(tCodeType);

		if (!tLDCodeDB.getInfo()) {
			return "";
		}
		return tLDCodeDB.getCodeName();
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
