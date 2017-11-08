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
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class PEdorPMPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorPMPrintBL.class);

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

	public PEdorPMPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("FM数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("FM数据传入无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("FM数据处理失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("FM数据准备失败!");
			return false;
		}

		logger.debug("End preparing the data to print ====>" + mOperate);
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);

		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
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
		if (!mOperate.equals("PRINTPM")) {
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
		xmlexport.addDisplayControl("displayPM");

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select distinct (case a.payintv when 1 then '月交' when 3 then '季交' when 6 then '半年交' when 12 then '年交' end) from lcpol a where contno = '?contno?' and appflag = '1' and payintv > 0 and payintv <> 12 and polno in (select polno from lppol where contno = '?contno?' and edortype = '?edortype?' and edorno = '?edorno?') ");
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		String tOldPayIntv = tExeSQL
				.getOneValue(sqlbv);
		// 填充原保单信息
		mTextTag.add("OldPayIntv", tOldPayIntv);

		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql("select distinct (case a.payintv when 1 then '月交' when 3 then '季交' when 6 then '半年交' when 12 then '年交' end) from lppol a where contno = '?contno?' and edortype = '?edortype?' and edorno = '?edorno?' ");
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		sbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		String tNewPayIntv = tExeSQL
				.getOneValue(sbv);
		mTextTag.add("NewPayIntv", tNewPayIntv);
		;

		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("select (case when sum(getMoney) is not null then sum(getMoney) else 0 end) from ljsgetendorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and ContNo = '?ContNo?' and SubFeeOperationType like concat('?SubFeeOperationType?','%')");
		sbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv1.put("SubFeeOperationType", BqCode.Pay_Prem);
		String tBJPrem = tExeSQL
				.getOneValue(sbv1);

		// 确定应补保费金额时才显示
		if (Double.parseDouble(tBJPrem) > 0) {
			xmlexport.addDisplayControl("displayPMBJ");
			mTextTag.add("SumPayPrem", PubFun.getChnMoney(Double
					.parseDouble(tBJPrem))
					+ "（￥" + tBJPrem + "）");
			mTextTag.add("AllPayPrem", PubFun.getChnMoney(Double
					.parseDouble(tBJPrem))
					+ "（￥" + tBJPrem + "）");
		}

		SSRS tBJSSRS = new SSRS();
		String tNextPayPremSQL = "select paytodate,sum(prem) from lppol a where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' group by paytodate";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(tNextPayPremSQL);
		sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv2.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
		tBJSSRS = tExeSQL.execSQL(sbv2);
		double tNextPayPrem = Double.parseDouble(tBJSSRS.GetText(1, 2));
		logger.debug("tNextPayPrem" + tNextPayPrem);
		xmlexport.addDisplayControl("displayPMNT");
		mTextTag.add("PayToDate", tBJSSRS.GetText(1, 1));
		mTextTag.add("NextPayPrem", PubFun.getChnMoney(tNextPayPrem) + "(￥"
				+ tNextPayPrem + ")");

		// BqNameFun.AddEdorPayGetInfo(mLPEdorItemSchema,mLPEdorAppSchema,
		// xmlexport,mTextTag);
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
