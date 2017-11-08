package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDOccupationSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 职业类别变更
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: mingsheng
 * </p>
 * 
 * @author pst
 * @version 1.0
 */

public class PEdorIOPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorIOPrintBL.class);

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

	private ListTable tPlistTable = new ListTable();

	private LJAGetSet mLJAGetSet = new LJAGetSet();

	public PEdorIOPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("IO数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("IO数据打印失败!");
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
		mLJAGetSet = (LJAGetSet) mInputData.getObjectByObjectName("LJAGetSet",
				0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTIO")) {
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
		xmlexport.addDisplayControl("displayIO");

		LPContDB tLPContDB = new LPContDB();
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLPContSchema = tLPContDB.getSchema();

		mTextTag.add("InsuredName", tLPContSchema.getInsuredName());
		mTextTag.add("InsuredNo", tLPContSchema.getInsuredNo());
		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		// 申请人的姓名

		if (!getAllPolSchema(tLPContSchema)) {
			mErrors.addOneError("传入其他险种数据无效!");
			return false;
		}
		String tGetMoney = BqNameFun.getSumGetMoney(mLPEdorItemSchema,
				mLPEdorItemSchema.getContNo());
		if (!"".equals(tGetMoney)) {
			xmlexport.addDisplayControl("displayIOGet");
		}
		BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema,
				xmlexport, mTextTag);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);
		return true;
	}

	// 其他险种数据提取
	private boolean getAllPolSchema(LPContSchema tLPContSchema) {

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPInsuredDB.setEdorNo(tLPContSchema.getEdorNo());
		tLPInsuredDB.setEdorType(tLPContSchema.getEdorType());
		tLPInsuredDB.setContNo(tLPContSchema.getContNo());
		tLPInsuredDB.setInsuredNo(tLPContSchema.getInsuredNo());
		if (!tLPInsuredDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取变更后职业信息失败1!");
			return false;
		}
		tLPInsuredSchema = tLPInsuredDB.getSchema();
		LDOccupationDB tLDOccupationDB = new LDOccupationDB();
		LDOccupationSchema tLDOccupationSchema = new LDOccupationSchema();
		tLDOccupationDB.setOccupationCode(tLPInsuredSchema.getOccupationCode());
		if (!tLDOccupationDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取变更后职业信息失败2!");
			return false;
		}
		tLDOccupationSchema = tLDOccupationDB.getSchema();

		mTextTag.add("IOLPInsured.OccupationType", tLDOccupationSchema
				.getOccupationCode()
				+ "(" + tLDOccupationSchema.getOccupationName() + ")");// 变更后职业类别
		mTextTag.add("IOLPInsured.WorkType", tLDOccupationSchema.getWorkName());// 变更后职业

		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		tLCInsuredDB.setContNo(tLPContSchema.getContNo());
		tLCInsuredDB.setInsuredNo(tLPContSchema.getInsuredNo());
		if (!tLCInsuredDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取变更后职业信息失败1!");
			return false;
		}
		tLCInsuredSchema = tLCInsuredDB.getSchema();

		if (tLCInsuredSchema.getOccupationCode() != null) {
			tLDOccupationDB.setOccupationCode(tLCInsuredSchema
					.getOccupationCode());
			if (!tLDOccupationDB.getInfo()) {
				mErrors.addOneError("打印生成数据时，取变更后职业信息失败2!");
				return false;
			}
			tLDOccupationSchema = tLDOccupationDB.getSchema();

			mTextTag.add("IOLCInsured.OccupationType", tLDOccupationSchema
					.getOccupationCode()
					+ "(" + tLDOccupationSchema.getOccupationName() + ")");// 变更后职业类别
			mTextTag.add("IOLCInsured.WorkType", tLDOccupationSchema
					.getWorkName());// 变更后职业
		}
		// modify by jiaqiangli 2009-04-24 被保人职业有可能为空
		else {
			mTextTag.add("IOLCInsured.OccupationType", "");// 变更后职业类别
			mTextTag.add("IOLCInsured.WorkType", "");// 变更后职业
		}

		LCPolDB tLCPolDB = new LCPolDB();

		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(tLPContSchema.getContNo());
		tLCPolDB.setAppFlag("1");
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet == null || tLCPolSet.size() < 1) {
			mErrors.addOneError("打印生成数据时，取险种信息失败!");
			return false;
		}
		String[] strArr = null;
		tlistTable.setName("IOC");
		for (int k = 1; k <= tLCPolSet.size(); k++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(k);
			strArr = new String[4];
			strArr[0] = BqNameFun.getRiskShortName(tLCPolSchema.getRiskCode())
					+ "--" + tLCPolSchema.getRiskCode();
			if (tLCPolSchema.getPayIntv() == 0) {
				strArr[1] = "趸缴";
			} else {
				strArr[1] = tLCPolSchema.getPayYears() + "年";
			}
			if (tLCPolSchema.getInsuYear() > 100) {
				strArr[2] = "终身";
			} else {
				String YearFlag = "";
				if ("A".equals(tLCPolSchema.getInsuYearFlag())) {
					YearFlag = "岁";
				}
				if ("Y".equals(tLCPolSchema.getInsuYearFlag())) {
					YearFlag = "年";
				}
				if ("M".equals(tLCPolSchema.getInsuYearFlag())) {
					YearFlag = "月";
				}
				if ("D".equals(tLCPolSchema.getInsuYearFlag())) {
					YearFlag = "天";
				}
				strArr[2] = tLCPolSchema.getInsuYear() + YearFlag;
			}
			strArr[3] = tLCPolSchema.getPrem() + "元";
			tlistTable.add(strArr);
		}

		LPPolDB tLPPolDB = new LPPolDB();

		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setContNo(tLPContSchema.getContNo());
		tLPPolDB.setEdorNo(tLPContSchema.getEdorNo());
		tLPPolDB.setEdorType(tLPContSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet == null || tLPPolSet.size() < 1) {
			mErrors.addOneError("打印生成数据时，取险种信息失败!");
			return false;
		}
		tPlistTable.setName("IOP");
		for (int k = 1; k <= tLPPolSet.size(); k++) {
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLPPolSchema = tLPPolSet.get(k);
			strArr = new String[4];
			strArr[0] = BqNameFun.getRiskShortName(tLPPolSchema.getRiskCode())
					+ "--" + tLPPolSchema.getRiskCode();
			if (tLPPolSchema.getPayIntv() == 0) {
				strArr[1] = "趸缴";
			} else {
				strArr[1] = tLPPolSchema.getPayYears() + "年";
			}
			if (tLPPolSchema.getInsuYear() > 100) {
				strArr[2] = "终身";
			} else {
				String YearFlag = "";
				if ("A".equals(tLPPolSchema.getInsuYearFlag())) {
					YearFlag = "岁";
				}
				if ("Y".equals(tLPPolSchema.getInsuYearFlag())) {
					YearFlag = "年";
				}
				if ("M".equals(tLPPolSchema.getInsuYearFlag())) {
					YearFlag = "月";
				}
				if ("D".equals(tLPPolSchema.getInsuYearFlag())) {
					YearFlag = "天";
				}
				strArr[2] = tLPPolSchema.getInsuYear() + YearFlag;
			}

			strArr[3] = tLPPolSchema.getPrem() + "元";
			tPlistTable.add(strArr);
		}

		xmlexport.addListTable(tPlistTable, strArr);
		xmlexport.addListTable(tlistTable, strArr);

		// String tSQL="select * from lj"
		// 财务信息如何处理？
		String tTemp = BqNameFun.NVL(BqNameFun.getSumPayMoney(
				mLPEdorItemSchema, tLPContSchema.getContNo()), "0.00");
		mTextTag.add("SumAmount", PubFun.getChnMoney(Double.parseDouble(tTemp))
				+ "(￥" + tTemp + ")");// 累计补费
		String tData[] = BqNameFun.getContNextPrem(mLPEdorItemSchema,
				mLPEdorItemSchema.getEdorAppDate());
		mTextTag.add("NextPayToDate", BqNameFun.getChDate(tData[0]));// 下一个缴费期
		mTextTag.add("NextPayMoney", PubFun.getChnMoney(Double
				.parseDouble(tData[1]))
				+ "(￥" + tData[1] + ")");// 下期保费
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
