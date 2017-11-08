package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
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
 * Title: 增加附加险
 * </p>
 * 
 * <p>
 * Description: 数据生成
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author changpeng
 * @version 1.0
 */

public class PEdorNSPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorNSPrintBL.class);

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

	private ListTable tNSListTable = new ListTable();

	private ListTable tNSSListTable = new ListTable();

	// public static final String FormulaOneVTS = "PrtEndorsement.vts";

	public PEdorNSPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("NS数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("NS数据打印失败!");
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
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			logger.debug("准备数据失败");
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTNS")) {
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
		xmlexport.addDisplayControl("displayNS");

		ExeSQL exeSQL = new ExeSQL();
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名
		// 保单增加附加险
		tNSListTable.setName("NS");
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet == null || tLPPolSet.size() == 0) {
			mErrors.addOneError("打印生成数据时，取加保附加险信息失败!");
			return false;
		}
		LPPolSchema tLPPolSchema;

		// 主险名称

		ExeSQL tExeSQL = new ExeSQL();
		String tMainRiskName = BqNameFun.getRiskNameByContNo(tLCContSchema
				.getContNo());
		if ("".equals(tMainRiskName)) {
			mErrors.addOneError("查询主险编码失败!");
			return false;
		}
		mTextTag.add("MainRiskName", tMainRiskName + "-"
				+ BqNameFun.getMainRiskCodeByContNo(tLCContSchema.getContNo())); // 主险编码

		double RPNewPrem = 0; // 本次新增险种交费标准，没有进行续期保费的重算？！
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			tLPPolSchema = new LPPolSchema();
			tLPPolSchema.setSchema(tLPPolSet.get(i));

			// 险种名称
			LMRiskDB tLMRiskDB = new LMRiskDB();
			LMRiskSchema tLMRiskSchema = new LMRiskSchema();
			tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
			if (!tLMRiskDB.getInfo()) {
				mErrors.addOneError("打印生成数据时，取险种名称失败!");
				return false;
			}
			tLMRiskSchema = tLMRiskDB.getSchema();
			String[] strNS = new String[7];
			strNS[0] = tLMRiskSchema.getRiskShortName() + "-"
					+ tLPPolSchema.getRiskCode(); // 险种名称
			if (tLPPolSchema.getInsuYear() > 100) {
				strNS[1] = "终身";
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
				strNS[1] = tLPPolSchema.getInsuYear() + YearFlag;
			}
			// 判断险种销售方式并记录
			String tSql = "SELECT (case when exists(select 'x' from LCDuty b where ContNo='?ContNo?' and PolNo='?PolNo?' and exists(select 'y' from LMDuty where DutyCode=b.DutyCode and AmntFlag='1')) then '1'"
					+ "  else '2'" + "  end)" + "FROM dual";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("ContNo", tLPPolSchema.getContNo());
			sqlbv.put("PolNo", tLPPolSchema.getPolNo());
			SSRS tSSRS = new SSRS();

			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorERDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "获得险种销售方式时产生错误！";
				mErrors.addOneError(tError);
				return false;
			}
			if ("2".equals(tSSRS.GetText(1, 1))) {
				strNS[2] = BqNameFun.getRound(String.valueOf(tLPPolSchema
						.getMult()), "0")
						+ "份"; // 新增份数
			} else {
				strNS[2] = String.valueOf(tLPPolSchema.getAmnt()); // 新增保额
			}

			RPNewPrem += tLPPolSchema.getPrem();
			strNS[3] = String.valueOf(tLPPolSchema.getPayYears()) + "年"; // 缴费年期
			strNS[4] = String.valueOf(tLPPolSchema.getStandPrem()) + "元"; // 保险费
			strNS[5] = BqNameFun.getRound(tLPPolSchema.getPrem()) + "元"; // 本次应缴费

			strNS[6] = tLPPolSchema.getCValiDate(); // 保单生效日期
			tNSListTable.add(strNS);

		}

		String tData[] = BqNameFun.getContNextPrem(mLPEdorItemSchema,
				mLPEdorItemSchema.getEdorAppDate());

		// 本次累计收付费
		String strSQL1 = "SELECT Sum(GetMoney)" + " FROM ljsgetendorse"
				+ " WHERE feeoperationtype='?feeoperationtype?'" + " and endorsementno='?endorsementno?'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(strSQL1);
		sbv.put("feeoperationtype", mLPEdorItemSchema.getEdorType());
		sbv.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		double SumAmount = 0.00;
		String tRSum = exeSQL.getOneValue(sbv);
		if (!"".equals(tRSum)) {
			SumAmount = Double.parseDouble(tRSum);
		}
		mTextTag.add("SumAmount", PubFun.getChnMoney(SumAmount) + "(￥"
				+ SumAmount + ")");

		mTextTag.add("NextPayToDate", BqNameFun.getChDate(tData[0]));// 下一个缴费期
		mTextTag.add("SumPayMoney", PubFun.getChnMoney(Double
				.parseDouble(tData[1]))
				+ "(￥" + tData[1] + ")");

		// 处理项目收付费信息
		// BqNameFun.AddEdorPayGetInfo( mLPEdorItemSchema,
		// mLPEdorAppSchema, xmlexport,
		// mTextTag);

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
		String[] b_strNS = new String[7];
		xmlexport.addListTable(tNSListTable, b_strNS);
		if (tNSSListTable.size() > 0) {
			String[] c_strNS = new String[2];
			xmlexport.addListTable(tNSSListTable, c_strNS);
		}
		xmlexport.addTextTag(mTextTag);
		// xmlexport.outputDocumentToFile("D:\\qly\\XMLDoc\\", "Qlyns");
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
