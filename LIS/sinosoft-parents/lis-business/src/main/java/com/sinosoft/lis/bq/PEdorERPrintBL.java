package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPRNPolAmntDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPRNPolAmntSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 满期降低保额续保
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

public class PEdorERPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorERPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	// public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private VData mInputData = new VData();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ListTable tERListTable = new ListTable();

	private double RNewPrem;

	private String Prem1;

	public PEdorERPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("ER数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("ER数据打印失败!");
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
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTER")) {
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
		xmlexport.addDisplayControl("displayHead1");
		xmlexport.addDisplayControl("displayER");
		xmlexport.addDisplayControl("displayTail1");
		ExeSQL exeSQL = new ExeSQL();
		RNewPolCalBL tRNewPolCalBL = new RNewPolCalBL();
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		mTextTag.add("AppntName", tLCContSchema.getAppntName()); // 原投保人姓名
		mTextTag.add("InsuredName", tLCContSchema.getInsuredName()); // 被保人姓名
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo()); // 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号
		// 保全减保数据
		tERListTable.setName("ER");
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet == null || tLPPolSet.size() == 0) {
			mErrors.addOneError("打印生成数据时，取险种减保信息失败1!");
			return false;
		}
		LPPolSchema tLPPolSchema;
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			tLPPolSchema = new LPPolSchema();
			tLPPolSchema.setSchema(tLPPolSet.get(i));
			// 险种名称
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolDB.setPolNo(tLPPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.addOneError("打印生成数据时，取保单信息失败2!");
				return false;
			}
			tLCPolSchema = tLCPolDB.getSchema();

			LMRiskDB tLMRiskDB = new LMRiskDB();
			LMRiskSchema tLMRiskSchema = new LMRiskSchema();
			tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());
			if (!tLMRiskDB.getInfo()) {
				mErrors.addOneError("打印生成数据时，取险种名称信息失败!");
				return false;
			}
			tLMRiskSchema = tLMRiskDB.getSchema();
			String LPRNPolAmnt = "0";
			String strSQL = "SELECT Sum(Amnt)" + " FROM LPRNPolAmnt"
					+ " WHERE EdorNo='?EdorNo?'"
					+ " and EdorType='?EdorType?'"
					+ " and ContNo='?ContNo?'"
					+ " and PolNo='?PolNo?'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(strSQL);
			sbv1.put("EdorNo", tLPPolSchema.getEdorNo());
			sbv1.put("EdorType", tLPPolSchema.getEdorType());
			sbv1.put("ContNo", tLPPolSchema.getContNo());	
			sbv1.put("PolNo", tLPPolSchema.getPolNo());
			if (exeSQL.getOneValue(sbv1) != "") {
				LPRNPolAmnt = String.valueOf(exeSQL.getOneValue(sbv1));
			}
			String[] strER = new String[3];
			strER[0] = tLMRiskSchema.getRiskShortName();
			// 判断险种销售方式并记录
			String tSql = "SELECT (case when exists(select 'x' from LCDuty b where ContNo='?ContNo?' and PolNo='?PolNo?' and exists(select 'y' from LMDuty where DutyCode=b.DutyCode and AmntFlag='1')) then '1'"
					+ "  else '2'" + "  end)" + "FROM dual";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(tSql);
			sbv2.put("ContNo", tLCPolSchema.getContNo());
			sbv2.put("PolNo", tLCPolSchema.getPolNo());
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sbv2);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorERDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "获得险种销售方式时产生错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if ("2".equals(tSSRS.GetText(1, 1))) {
				strER[1] = BqNameFun.getRound(tLCPolSchema.getMult(), "0")
						+ "份";
				strER[2] = BqNameFun.getRound(LPRNPolAmnt, "0") + "份";
			} else {
				strER[1] = BqNameFun.getRound(String.valueOf(tLCPolSchema
						.getAmnt()))
						+ "元";
				strER[2] = BqNameFun.getRound(LPRNPolAmnt) + "元";
			}

			tERListTable.add(strER);
			if (!StrTool.compareString(tLPPolSchema.getPolNo(), tLPPolSchema
					.getMainPolNo())) {
				// 附加险算续期
				tRNewPolCalBL = new RNewPolCalBL();
				if (!tRNewPolCalBL.RNPolCalBL(tLPPolSchema)) {
					mErrors.copyAllErrors(tRNewPolCalBL.mErrors);
					mErrors.addOneError("计算续期险种保费失败!");
					return false;
				}
				double aPrem = 0;
				aPrem = tRNewPolCalBL.getRNewPrem();
				if (tLPPolSchema.getAmnt() > 0 && tLCPolSchema.getAmnt() > 0) {
					aPrem = aPrem * tLPPolSchema.getAmnt()
							/ tLCPolSchema.getAmnt();
				} else {
					aPrem = aPrem * tLPPolSchema.getMult()
							/ tLCPolSchema.getMult();
				}
				LPRNPolAmntDB tLPRNPolAmntDB = new LPRNPolAmntDB();
				LPRNPolAmntSet tLPRNPolAmntSet = new LPRNPolAmntSet();
				tLPRNPolAmntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPRNPolAmntDB.setPolNo(tLCPolSchema.getPolNo());
				tLPRNPolAmntSet = tLPRNPolAmntDB.query();
				if (tLPRNPolAmntSet != null && tLPRNPolAmntSet.size() > 0) {
					double AAmnt = 0;
					for (int k = 1; k <= tLPRNPolAmntSet.size(); k++) {
						AAmnt += tLPRNPolAmntSet.get(k).getAmnt();
					}
					// 算加费
					for (int k = 1; k <= tLPRNPolAmntSet.size(); k++) {
						String DutyCode = tLPRNPolAmntSet.get(k).getDutyCode();
						String PolNo = tLPRNPolAmntSet.get(k).getPolNo();
						LCPremDB tLCPremDB = new LCPremDB();
						LCPremSet tLCPremSet = new LCPremSet();
						String Add = "select * from lcprem where polno = '?PolNo?' and dutycode = '?DutyCode?' and payplantype <> '0'";
						SQLwithBindVariables sbv3=new SQLwithBindVariables();
						sbv3.sql(Add);
						sbv3.put("PolNo", PolNo);
						sbv3.put("DutyCode", DutyCode);
						tLCPremSet = tLCPremDB.executeQuery(sbv3);
						if (tLCPremSet != null && tLCPremSet.size() > 0) {
							for (int u = 1; u <= tLCPremSet.size(); u++) {
								double SuperRisk = tLCPremSet.get(u)
										.getSuppRiskScore();
								RNewPrem += aPrem * (SuperRisk / 100)
										* tLPRNPolAmntSet.get(k).getAmnt()
										/ AAmnt;
							}
						}
					}
				}

				RNewPrem += aPrem;
			}
		}
		String StrSQL = "select * from lcpol where polno <> mainpolno and contno = '?contno?' and polno not in (select polno from lppol where edortype = 'ER' and edorno = '?edorno?') and appflag = '1' and rnewflag <> -2";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(StrSQL);
		sbv3.put("contno", mLPEdorItemSchema.getContNo());
		sbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
		LCPolDB iLCPolDB = new LCPolDB();
		LCPolSet iLCPolSet = new LCPolSet();
		iLCPolSet = iLCPolDB.executeQuery(sbv3);
		if (iLCPolSet != null && iLCPolSet.size() > 0) {
			for (int j = 1; j <= iLCPolSet.size(); j++) {
				tRNewPolCalBL = new RNewPolCalBL();
				if (!tRNewPolCalBL.RNPolCalBL(iLCPolSet.get(j))) {
					mErrors.copyAllErrors(tRNewPolCalBL.mErrors);
					mErrors.addOneError("计算续期险种保费失败!");
					return false;
				}
				RNewPrem += tRNewPolCalBL.getRNewPrem();
			}
		}
		String StrSQL1 = "select * from lppol where mainpolno = polno and contno = '?contno?' and edortype = 'ER' and edorno = '?edorno?' and payintv <> 0";
		String StrSQL2 = "select * from lcpol where mainpolno = polno and contno = '?contno?' and payintv <> 0";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(StrSQL1);
		sbv4.put("contno", mLPEdorItemSchema.getContNo());
		sbv4.put("edorno", mLPEdorItemSchema.getEdorNo());
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(StrSQL2);
		sbv5.put("contno", mLPEdorItemSchema.getContNo());
		iLCPolSet.clear();
		iLCPolSet = iLCPolDB.executeQuery(sbv4);
		if (iLCPolSet == null || iLCPolSet.size() < 1) {
			iLCPolSet = iLCPolDB.executeQuery(sbv5);
		}
		if (iLCPolSet != null && iLCPolSet.size() > 0) {
			RNewPrem += iLCPolSet.get(1).getPrem();
		}

		if (RNewPrem > 0) {
			Prem1 = BqNameFun.getRound(String.valueOf(RNewPrem));
		}

		mTextTag.add("StandPay", Prem1); // 续交标准
		// 其它信息
		mTextTag.add("LPEdor.ValiDate", mLPEdorItemSchema.getEdorValiDate());
		mTextTag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator()));
		mTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPEdorItemSchema.getApproveOperator()));

		String[] allArr = BqNameFun.getAllNames(tLCContSchema.getAgentCode());
		mTextTag.add("ManageCom", allArr[BqNameFun.SALE_SERVICE]);
		mTextTag.add("LABranchGroup.Name", allArr[BqNameFun.DEPART]);
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCContSchema.getAgentCode());
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		String[] b_strER = new String[3];
		xmlexport.addListTable(tERListTable, b_strER);
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
