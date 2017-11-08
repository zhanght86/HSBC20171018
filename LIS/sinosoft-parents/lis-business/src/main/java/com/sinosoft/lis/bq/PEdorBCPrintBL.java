package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 受益人变更批单数据生成
 * </p>
 * 
 * <p>
 * Description: 生成保全项目受益人变更的批单数据
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

public class PEdorBCPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorBCPrintBL.class);

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

	/** 保全现在受益人的信息 */
	private ListTable tBCListTable = new ListTable();

	/** 保存原受益人的信息 */
	private ListTable tPreBCListTable = new ListTable();

	public PEdorBCPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("BC数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("BC数据打印失败!");
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
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTBC")) {
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

	@SuppressWarnings("static-access")
	private boolean dealData() {
		// xmlexport.addDisplayControl("displayHead1");
		BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displayBC");
		xmlexport.addDisplayControl("displayPreBC");
		xmlexport.addDisplayControl("displaySubBC");

		// mTextTag.add ("AppName",mLPEdorAppSchema.getEdorAppName())
		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "SELECT insuredno,SequenceNo,name from LCInsured where Contno = '"
				+ "?Contno?" + "' order by SequenceNo";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("Contno", mLPEdorItemSchema.getContNo());
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorBCPrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "本保单下没有被保人信息";
			this.mErrors.addOneError(tError);
			return false;
		}
		// MS只有单被保人
		mTextTag.add("InsuredName", tSSRS.GetText(1, 3));// 被保人姓名
		for (int j = 1; j <= tSSRS.MaxRow; j++) {
			// 新受益人信息
			tBCListTable.setName("BC");
			LPBnfDB tLPBnfDB = new LPBnfDB();
			LPBnfSet tLPBnfSet = new LPBnfSet();
			String tBnfSql = "SELECT * from LPBnf where InsuredNo='"
					+ "?InsuredNo?" + "' and Contno = '"
					+ "?Contno?" + "' and edorno='"
					+ "?edorno?" + "' and edortype='"
					+ "?edortype?"
					+ "' order by  Bnftype, BnfGrade,BnfLot";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(tBnfSql);
			sbv.put("InsuredNo", tSSRS.GetText(j, 1));
			sbv.put("Contno", mLPEdorItemSchema.getContNo());
			sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv.put("edortype", mLPEdorItemSchema.getEdorType());
			tLPBnfSet = tLPBnfDB.executeQuery(sbv);
			// 受益性质 受益顺序 受益比例 受益人姓名 证件号码 出生日期 住址及邮编
			if (!(tLPBnfSet == null || tLPBnfSet.size() == 0)) {
				LPBnfSchema tLPBnfSchema;
				for (int i = 1; i <= tLPBnfSet.size(); i++) {
					tLPBnfSchema = new LPBnfSchema();
					tLPBnfSchema.setSchema(tLPBnfSet.get(i));
					String[] strBC = new String[7];
					strBC[0] = BqNameFun.getCodeName(tLPBnfSchema.getBnfType(),
							"BnfType");
					strBC[1] = tLPBnfSchema.getBnfGrade();
					strBC[2] = String.valueOf(tLPBnfSchema.getBnfLot());
					strBC[3] = tLPBnfSchema.getName();
					strBC[4] = /* BqNameFun.getCodeName(tLPBnfSchema.getIDType(),"IDType")+": "+ */tLPBnfSchema
							.getIDNo();
					strBC[5] = tLPBnfSchema.getBirthday();
					if (("".equals(tLPBnfSchema.getPostalAddress()) || tLPBnfSchema
							.getPostalAddress() == null)
							&& ("".equals(tLPBnfSchema.getZipCode()) || tLPBnfSchema
									.getZipCode() == null)) {
						strBC[6] = "";
					} else {
						strBC[6] = BqNameFun.NVL(tLPBnfSchema
								.getPostalAddress(), "")
								+ BqNameFun.NVL(tLPBnfSchema.getZipCode(), "");
					}

					tBCListTable.add(strBC);
				}
			}
			// 原受益人信息
			LCBnfDB tLCBnfDB = new LCBnfDB();
			LCBnfSet tLCBnfSet = new LCBnfSet();
			String tBnfCSql = "SELECT * from LCBnf where InsuredNo='"
					+ "?InsuredNo?" + "' and Contno = '"
					+ "?Contno?"
					+ "' order by  Bnftype, BnfGrade,BnfLot";
            SQLwithBindVariables sbv1=new SQLwithBindVariables();
            sbv1.sql(tBnfCSql);
            sbv1.put("InsuredNo", tSSRS.GetText(j, 1));
            sbv1.put("Contno", mLPEdorItemSchema.getContNo());
			tLCBnfSet = tLCBnfDB.executeQuery(sbv1);
			if (!(tLCBnfSet == null || tLCBnfSet.size() == 0)) {
				tPreBCListTable.setName("PreBC");
				LCBnfSchema tLCBnfSchema;
				for (int i = 1; i <= tLCBnfSet.size(); i++) {
					tLCBnfSchema = new LCBnfSchema();
					tLCBnfSchema.setSchema(tLCBnfSet.get(i));
					String[] strPreBC = new String[7];
					strPreBC[0] = BqNameFun.getCodeName(tLCBnfSchema
							.getBnfType(), "BnfType");
					strPreBC[1] = tLCBnfSchema.getBnfGrade();
					strPreBC[2] = String.valueOf(tLCBnfSchema.getBnfLot());
					strPreBC[3] = tLCBnfSchema.getName();
					strPreBC[4] = /* BqNameFun.getCodeName(tLCBnfSchema.getIDType(),"IDType")+": "+ */tLCBnfSchema
							.getIDNo();
					strPreBC[5] = tLCBnfSchema.getBirthday();
					if (("".equals(tLCBnfSchema.getZipCode()) || tLCBnfSchema
							.getZipCode() == null)
							&& ("".equals(tLCBnfSchema.getPostalAddress()) || tLCBnfSchema
									.getPostalAddress() == null)) {
						strPreBC[6] = "";
					} else {
						strPreBC[6] = BqNameFun.NVL(tLCBnfSchema
								.getPostalAddress(), "")
								+ BqNameFun.NVL(tLCBnfSchema.getZipCode(), "");
					}
					tPreBCListTable.add(strPreBC);
				}
			} else {
				tPreBCListTable.setName("PreBC");
				String[] strPreBC = new String[7];
				strPreBC[0] = "";
				strPreBC[1] = "";
				strPreBC[2] = "";
				strPreBC[3] = "";
				strPreBC[4] = "";
				strPreBC[5] = "";
				strPreBC[6] = "";
				tPreBCListTable.add(strPreBC);
			}
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
		String[] b_strBC = new String[7];
		xmlexport.addListTable(tBCListTable, b_strBC);

		String[] b_strPreBC = new String[7];
		xmlexport.addListTable(tPreBCListTable, b_strPreBC);

		xmlexport.addTextTag(mTextTag);
		// myDocument = xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
