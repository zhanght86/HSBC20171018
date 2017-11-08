package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:
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
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class PEdorDAPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorDAPrintBL.class);

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

	private ListTable tNSSListTable = new ListTable();
	
	private String[] strNSS;

	public PEdorDAPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

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
		if (!mOperate.equals("PRINTDA")) {
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
		xmlexport.addDisplayControl("displayHead1");
		xmlexport.addDisplayControl("displayDA");
		xmlexport.addDisplayControl("displayTail1");
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

		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();
		double aRNPrem = 0;
		aRNPrem = tLPPolSet.get(1).getPrem();
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		String StrSQL = "select * from lcpol where mainpolno <> polno and appflag = '1' and mainpolno = '?mainpolno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(StrSQL);
		sqlbv.put("mainpolno", mLPEdorItemSchema.getPolNo());
		logger.debug(StrSQL);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema = tLCPolSet.get(i);
				RNewPolCalBL tRNewPolCalBL = new RNewPolCalBL();
				if (!tRNewPolCalBL.RNPolCalBL(tLCPolSchema)) {
					mErrors.copyAllErrors(tRNewPolCalBL.mErrors);
					mErrors.addOneError("计算附加险续期失败!");
					return false;
				}
				aRNPrem += tRNewPolCalBL.getRNewPrem();
			}
		}

		mTextTag.add("DAStart", tLPPolSet.get(1).getPaytoDate());
		mTextTag.add("DAEnd", tLPPolSet.get(1).getPayEndDate());
		mTextTag.add("DARNPrem", aRNPrem);
		mTextTag.add("LPEdor.ValiDate", mLPEdorItemSchema.getEdorValiDate());
		String[] allArr = BqNameFun.getAllNames(tLCContSchema.getAgentCode());
		mTextTag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator()));
		mTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPEdorItemSchema.getApproveOperator()));
		mTextTag.add("ManageCom", allArr[BqNameFun.SALE_SERVICE]);
		mTextTag.add("LABranchGroup.Name", allArr[BqNameFun.DEPART]);
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCContSchema.getAgentCode());

		// 核保特约
		String strSQL = "select polno from LPPol where contno = '?contno?' and edorno = '?edorno?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strSQL);
		sbv1.put("contno", mLPEdorItemSchema.getContNo());
		sbv1.put("", mLPEdorItemSchema.getEdorNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sbv1);
		if (tSSRS != null || tSSRS.MaxRow > 0) {
			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				tNSSListTable.setName("NSS");

				strNSS = new String[2];
				SQLwithBindVariables sbv2=new SQLwithBindVariables();
				sbv2.sql("select riskshortname from lppol a, lmrisk b where a.polno = '?polno?' and a.riskcode = b.riskcode");
				sbv2.put("?polno?", tSSRS.GetText(j, 1));
				strNSS[0] = tExeSQL
						.getOneValue(sbv2);
				SQLwithBindVariables sbv3=new SQLwithBindVariables();
				sbv3.sql(" select speccontent from LPSpec where 1=1 "
						+ " and SerialNo = (select max(SerialNo) from lpspec where polno = '?polno?' and edorno = '?edorno?')");
				sbv3.put("polno", tSSRS.GetText(j, 1));
				sbv3.put("edorno", mLPEdorItemSchema.getEdorAppNo());
				strNSS[1] = tExeSQL
						.getOneValue(sbv3);
				if (!"".equals(strNSS[1])) {
					tNSSListTable.add(strNSS);
					xmlexport.addDisplayControl("displayDASpec");
				}
			}
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {

		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		String[] c_strNS = new String[2];
		xmlexport.addListTable(tNSSListTable, c_strNS);
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
