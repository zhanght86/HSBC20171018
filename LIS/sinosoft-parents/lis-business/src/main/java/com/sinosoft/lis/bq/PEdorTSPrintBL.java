package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 保全项目投连万能明细
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ck
 * @version 1.0
 */

public class PEdorTSPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorTSPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private VData mInputData = new VData();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	public PEdorTSPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);
		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("RE数据传输失败!");
			return false;
		}
		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}
		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("RE数据打印失败!");
			return false;
		}
		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("准备数据失败!");
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
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTTS")) {
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
		xmlexport.addDisplayControl("displayTS");

		ExeSQL tExeSQL = new ExeSQL();
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		/*
		 * 设置displayHead1中的信息
		 */
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo()); // 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号
		mTextTag.add("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());// 保全受理号
		mTextTag.add("LCCont.AppntName", tLCContSchema.getAppntName());// 投保人
		mTextTag.add("AppDate", BqNameFun.getFDate(mLPEdorItemSchema
				.getEdorAppDate()));// 保全申请年

		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet == null || tLPPolSet.size() < 1) {
			mErrors.addOneError("查询险种信息失败!");
			return false;
		}
		tLPPolSchema = tLPPolSet.get(1);
		// 险种名称
		// RiskName = BqNameFun.getPrintRiskName(tLPPolSchema.getRiskCode());
		// mTextTag.add("RiskName", RiskName);

		// tTSListTable.setName("TS");
		// String tSql = "select * from LJEndorseDetail where EndorsementNo='" +
		// mLPEdorItemSchema.getEdorNo() + "'"
		// + " and FeeOperationType='" + mLPEdorItemSchema.getEdorType() + "'
		// order by paytodate";
		// LJEndorseDetailSet tLJEndorseDetailSet = new LJEndorseDetailSet();
		// LJEndorseDetailDB tLJEndorseDetailDB = new LJEndorseDetailDB();
		// tLJEndorseDetailSet = tLJEndorseDetailDB.executeQuery(tSql);
		// String[] strTS;
		// for (int i = 1; i <= tLJEndorseDetailSet.size(); i++)
		// {
		// LJEndorseDetailSchema tLJEndorseDetailSchema = new
		// LJEndorseDetailSchema();
		// tLJEndorseDetailSchema = tLJEndorseDetailSet.get(i);
		// strTS = new String[4];
		// strTS[0] = tLJEndorseDetailSchema.getPaytoDate();// 应缴日期
		// strTS[1] = String.valueOf(tLJEndorseDetailSchema.getInterval());//
		// 保费年度
		// strTS[2] = String.valueOf(tLJEndorseDetailSchema.getInitFee());//
		// 应缴费用
		// strTS[3] = "初始费用";
		// tTSListTable.add(strTS);
		// }

		mTextTag.add("CurPayToDate", BqNameFun.getFDate(tLPPolSchema
				.getPaytoDate()));

		// 补退费合计
		String ReTotalMoney = "0.00";
		String tSql = "SELECT (case when Sum(GetMoney) is not null then Sum(GetMoney) else 0 end)" + " FROM LJSGetEndorse"
				+ " WHERE EndorsementNo='?EndorsementNo?'" + " and FeeOperationType='?FeeOperationType?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		ReTotalMoney = tExeSQL.getOneValue(sqlbv);
		mTextTag.add("SumActuPayMoney", BqNameFun.getRound(Double
				.parseDouble(ReTotalMoney)));
		/*
		 * if () { xmlexport.addDisplayControl("displayTS01"); }
		 */

		// 银行帐户信息
		xmlexport.addDisplayControl("displayBank");
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取银行信息失败!");
			return false;
		}
		tLPEdorAppSchema = tLPEdorAppDB.getSchema();
		if (tLPEdorAppSchema.getBankCode() != null
				&& !"".equals(tLPEdorAppSchema.getBankCode())) {
			mTextTag.add("BankName", BqNameFun.getAnother(null,"LDBank", "bankcode",
					tLPEdorAppSchema.getBankCode(), "bankname"));
			mTextTag.add("AccountNo", tLPEdorAppSchema.getBankAccNo());
			mTextTag.add("AccountName", tLPEdorAppSchema.getAccName());
		} else {
			mTextTag.add("AccountNo", tLCContSchema.getBankAccNo());
			mTextTag.add("AccountName", tLCContSchema.getAccName());
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
		// String[] c_strNS = new String[4];
		// xmlexport.addListTable(tTSListTable, c_strNS);

		xmlexport.addTextTag(mTextTag);
		// xmlexport.outputDocumentToFile("d:\\", "PEdorTSPrint"); // 测试用
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
