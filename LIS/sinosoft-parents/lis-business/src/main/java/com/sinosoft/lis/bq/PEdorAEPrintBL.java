package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
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

public class PEdorAEPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PEdorAEPrintBL.class);

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

	private XmlExportNew xmlExport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	public PEdorAEPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// logger.debug("Start preparing the data to print ====>" +
		// mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("AE数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("AE数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("准备数据失败!");
			return false;
		}

		// logger.debug("End preparing the data to print ====>" +
		// mOperate);
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		xmlExport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		if (mLPEdorItemSchema == null || xmlExport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTAE")) {
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
		CodeNameQuery tCodeNameQuery = new CodeNameQuery();
		// xmlexport.addDisplayControl("displayHead1");
		BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlExport);
		xmlExport.addDisplayControl("displayAE");
		ExeSQL tExeSQL = new ExeSQL();

		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		mTextTag.add("AELPAppnt.InsuredName", tLCContSchema.getInsuredName());

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCAppntDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取投保人信息失败!");
			return false;
		}
		tLCAppntSchema = tLCAppntDB.getSchema();

		/** **********原投保人的信息****** */
		mTextTag.add("AELPAppnt.AppntName", tLCAppntSchema.getAppntName());

		mTextTag.add("AELPAppnt.AppntBirthDay", tLCAppntSchema
				.getAppntBirthday());
		mTextTag.add("AELPAppnt.AppntSex", tCodeNameQuery.getName("sex",
				tLCAppntSchema.getAppntSex()));
		mTextTag.add("AELPAppnt.IDType", tCodeNameQuery.getName("idtype",
				tLCAppntSchema.getIDType()));
		mTextTag.add("AELPAppnt.IDNo", tLCAppntSchema.getIDNo());
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql("select RelationToInsured from lcappnt a where "
				+ "  a.contno = '"
				+ "?contno?"
				+ "'");
        sqlbv.put("contno", tLCContSchema.getContNo());
		mTextTag
				.add(
						"AELPInsured.RelationToMainInsured",
						tCodeNameQuery
								.getName(
										"relation",
										tExeSQL
												.getOneValue(sqlbv))); // 被保人与投保人关系

		// 新投保人信息
		LPAppntDB tLPAppntDB = new LPAppntDB();
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPAppntDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取投保人信息失败!");
			return false;
		}
		tLPAppntSchema = tLPAppntDB.getSchema();

		mTextTag.add("AELCAppnt.AppntName", tLPAppntSchema.getAppntName());

		mTextTag.add("AELCAppnt.AppntBirthDay", tLPAppntSchema
				.getAppntBirthday());
		mTextTag.add("AELCAppnt.AppntSex", tCodeNameQuery.getName("sex",
				tLPAppntSchema.getAppntSex()));
		mTextTag.add("AELCAppnt.IDType", tCodeNameQuery.getName("idtype",
				tLPAppntSchema.getIDType()));
		mTextTag.add("AELCAppnt.IDNo", tLPAppntSchema.getIDNo());
	    SQLwithBindVariables sbv=new SQLwithBindVariables();
	    sbv.sql("select RelationToInsured from lpappnt b where "
				+ "  contno = '"
				+ "?contno?"
				+ "' and edorno = '"
				+ "?edorno?"
				+ "'");
	    sbv.put("contno", tLCContSchema.getContNo());
	    sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		mTextTag
				.add(
						"AELCInsured.RelationToMainInsured",
						tCodeNameQuery
								.getName(
										"relation",
										tExeSQL
												.getOneValue(sbv))); // 被保人与投保人关系

		String strSQL1 = "SELECT (case when Sum(GetMoney) is not null then Sum(GetMoney) else 0 end)" + " FROM LJSGetEndorse"
				+ " WHERE EndorsementNo='" + "?EndorsementNo?"
				+ "'" + " and FeeOperationType='"
				+ "?FeeOperationType?" + "'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strSQL1);
		sbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sbv1);
		if (tSSRS != null && tSSRS.MaxRow > 0
				&& (!"0".equals(tSSRS.GetText(1, 1)))) {
			mTextTag.add("SumGetMoney", PubFun.getChnMoney(Double
					.parseDouble(tSSRS.GetText(1, 1)))
					+ "(￥" + tSSRS.GetText(1, 1) + ")");

		} else {
			mTextTag.add("SumGetMoney", PubFun.getChnMoney(0.00) + "(￥"
					+ "0.00" + ")");
		}
		String tData[] = BqNameFun.getContNextPrem(mLPEdorItemSchema,
				mLPEdorItemSchema.getEdorAppDate());
		mTextTag.add("PaytoDate", BqNameFun.getChDate(tData[0]));// 交至日期
		mTextTag.add("SumPrem", PubFun
				.getChnMoney(Double.parseDouble(tData[1]))
				+ "(￥" + tData[1] + ")");// 下期应缴保费（应该去算）

		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlExport);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		xmlExport.addTextTag(mTextTag);
		mResult.add(xmlExport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
