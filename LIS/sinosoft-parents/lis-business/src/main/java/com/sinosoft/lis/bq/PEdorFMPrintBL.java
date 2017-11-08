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

public class PEdorFMPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorFMPrintBL.class);

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

	private ListTable mListTable = new ListTable();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private LJAGetSet mLJAGetSet = new LJAGetSet();

	public PEdorFMPrintBL() {
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
		if (!mOperate.equals("PRINTFM")) {
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
		xmlexport.addDisplayControl("displayFM");

		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo());
		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select concat(concat((select riskname from lmriskapp where riskcode=a.riskcode),'--'),a.riskcode),a.payyears,concat(a.insuyear,(case a.insuyearflag when 'A' then '岁' when 'Y' then '年' when 'M' then '月' when 'D' then '日' else '年' end)),a.standprem from lcpol a where contno = '?contno?' and appflag = '1' and payintv > 0 and polno in (select polno from lppol where contno = '?contno?' and edortype = '?edortype?' and edorno = '?edorno?') ");
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		SSRS tSSRS = tExeSQL
				.execSQL(sqlbv);
		// 填充原保单信息
		mListTable.setName("FM1");
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			String[] strTR = new String[4];
			strTR[0] = tSSRS.GetText(i, 1);
			strTR[1] = tSSRS.GetText(i, 2);
			if (Integer.parseInt(tSSRS.GetText(i, 3).substring(0,
					tSSRS.GetText(i, 3).length() - 1)) > 100) {
				strTR[2] = "终身";
			} else {
				strTR[2] = tSSRS.GetText(i, 3);
			}
			strTR[3] = "￥" + tSSRS.GetText(i, 4);
			mListTable.add(strTR);
		}
		String[] b_strTR = new String[4];
		xmlexport.addListTable(mListTable, b_strTR);

		mListTable = new ListTable();

		// 填充交费年期变更后的保单信息
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("select concat(concat((select riskname from lmriskapp where riskcode=a.riskcode),'--'),a.riskcode),a.payyears,concat(a.insuyear,(case a.insuyearflag when 'A' then '岁' when 'Y' then '年' when 'M' then '月' when 'D' then '日' else '年' end)),"
				+ "a.standprem,(select (case when sum(getMoney) is not null then sum(getMoney) else 0 end) from ljsgetendorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and ContNo = '?ContNo?' and SubFeeOperationType like concat('?SubFeeOperationType?','%') and polno=a.polno),(select (case when sum(getMoney) is not null then sum(getMoney) else 0 end) from ljsgetendorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and ContNo = '?ContNo?' and SubFeeOperationType like concat('?SubFeeOperationType2?','%') and polno=a.polno) from lppol a where contno = '?ContNo?' and edortype = '?FeeOperationType?' and edorno = '?EndorsementNo?' ");
		sbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv1.put("SubFeeOperationType1", BqCode.Pay_Prem);
		sbv1.put("SubFeeOperationType2", BqCode.Pay_PremInterest);
		tSSRS = tExeSQL
				.execSQL(sbv1);
 
		mListTable.setName("FM2");
		String[] strTR = new String[6];
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			strTR = new String[6];
			strTR[0] = tSSRS.GetText(i, 1);
			strTR[1] = tSSRS.GetText(i, 2);
			if (Integer.parseInt(tSSRS.GetText(i, 3).substring(0,
					tSSRS.GetText(i, 3).length() - 1)) > 100) {
				strTR[2] = "终身";
			} else {
				strTR[2] = tSSRS.GetText(i, 3);
			}
			strTR[3] = "￥" + tSSRS.GetText(i, 4);
			strTR[4] = "￥" + tSSRS.GetText(i, 5);
			strTR[5] = "￥" + tSSRS.GetText(i, 6);
			mListTable.add(strTR);
		}
		b_strTR = new String[6];
		xmlexport.addListTable(mListTable, b_strTR);

		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("select (case when sum(getMoney) is not null then sum(getMoney) else 0 end) from ljsgetendorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and ContNo = '?ContNo?' ");
		sbv2.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sbv2.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
		String tBJPrem = tExeSQL
				.getOneValue(sbv2);
		mTextTag.add("SumPayPrem", PubFun.getChnMoney(Double
				.parseDouble(tBJPrem))
				+ "（￥" + tBJPrem + "）");

		BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema,
				xmlexport, mTextTag);
		// BqNameFun.AddEdorPayGetInfo(mLPEdorItemSchema,mLPEdorAppSchema,
		// xmlexport,mTextTag);
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
