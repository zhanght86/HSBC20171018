package com.sinosoft.lis.bq;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
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
 * Title: 转养老金批单数据生成
 * </p>
 * 
 * <p>
 * Description: 生成转养老金批单打印所需要的数据
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author wangyan
 * 
 * @version 1.0
 */

public class PEdorTAPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorTAPrintBL.class);

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

	private ListTable mListTable = new ListTable();

	public PEdorTAPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("TA数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("TA传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("TA数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("TA准备数据失败!");
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
		if (!mOperate.equals("PRINTTA")) {
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
		xmlexport.addDisplayControl("displayTA");
		xmlexport.addDisplayControl("displayTail1");
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		mTextTag.add("AppntName", tLCContSchema.getAppntName());// 原投保人姓名
		mTextTag.add("InsuredName", tLCContSchema.getInsuredName());// 被保人姓名
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo());// 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo());// 保单号

		// 提取转养老金项目数据
		mListTable.setName("TA");
		String tSql = "Select getdate,getdutycode from lpagetdraw where polno = '?polno?' and edorno = '?edorno?' and edortype = '?edortype?' order by getdate";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("polno", mLPEdorItemSchema.getPolNo());
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("edortype", mLPEdorItemSchema.getEdorType());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			// @@错误处理
			mErrors.addOneError("没有领取信息!");
			return false;
		}
		// LPAGetDrawDB tLPAGetDrawDB = new LPAGetDrawDB();
		// tLPAGetDrawDB.setPolNo(mLPEdorItemSchema.getPolNo());
		// tLPAGetDrawDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// tLPAGetDrawDB.setEdorType(mLPEdorItemSchema.getEdorType());
		// LPAGetDrawSet tLPAGetDrawSet = new LPAGetDrawSet();
		// tLPAGetDrawSet = tLPAGetDrawDB.query();
		// if (tLPAGetDrawSet == null || tLPAGetDrawSet.size() == 0){
		// mErrors.addOneError("打印生成数据时，取险种信息失败!");
		// return false;
		// }

		// LPAGetDrawSchema tLPAGetDrawSchema = new LPAGetDrawSchema();
		for (int j = 1; j <= tSSRS.MaxRow; j++) {
			String[] strIO = new String[3];

			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql("SELECT InsuredBirthday FROM LCPol"
					+ " where PolNo = '?PolNo?'");
			sbv1.put("PolNo", mLPEdorItemSchema.getPolNo());
			String mInsuredBirthday = BqNameFun.delTime(tExeSQL
					.getOneValue(sbv1));
			// String mGetDate = tLPAGetDrawSchema.getGetDate();
			String mGetDate = tSSRS.GetText(j, 1);
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql("select trunc(months_between(to_date('?mGetDate?','yyyy-mm-dd'),to_date('?mInsuredBirthday?','yyyy-mm-dd')) / 12) from dual");
			sbv2.put("mGetDate", mGetDate);
			sbv2.put("mInsuredBirthday", mInsuredBirthday);
			String mAge = tExeSQL
					.getOneValue(sbv2);

			strIO[0] = mAge; // 领取年龄
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql("select GetDutyName from lmdutyget where getdutycode = '?getdutycode?'");
			sbv3.put("getdutycode", tSSRS.GetText(j, 2));
			String mGetName = tExeSQL
					.getOneValue(sbv3);
			strIO[1] = mGetName; // 领取项目
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql("select a.GetMoney from ljagetdraw a, lpget b where a.getdutycode = '?getdutycode?'"
					+ " and a.polno = '?polno?'"
					+ " and a.dutycode = b.dutycode and a.getdutykind = b.getdutykind");
			sbv4.put("getdutycode", tSSRS.GetText(j, 2));
			sbv4.put("polno", mLPEdorItemSchema.getPolNo());
			String mGetAmount = tExeSQL
					.getOneValue(sbv4);
			DecimalFormat df = new DecimalFormat("0.00");
			double d = Double.parseDouble(mGetAmount);
			mGetAmount = df.format(d);
			strIO[2] = mGetAmount; // 领取金额
			mListTable.add(strIO);
		}
		// for (int i = 1; i <= tLPAGetDrawSet.size(); i++){
		// tLPAGetDrawSchema = new LPAGetDrawSchema();
		// tLPAGetDrawSchema.setSchema(tLPAGetDrawSet.get(i));
		// }
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql("select b.Standmoney from lmdutyget a, lpget b where a.GetType2 = '1' and a.Getdutycode = b.GetDutyCode"
				+ " and b.edorno = '?edorno?'"
				+ " and b.edortype = '?edortype?'");
		sbv5.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv5.put("edortype", mLPEdorItemSchema.getEdorType());
		String mStandMoney = tExeSQL
				.getOneValue(sbv5);
		DecimalFormat df = new DecimalFormat("0.00");
		double d = Double.parseDouble(mStandMoney);
		mStandMoney = df.format(d);
		mTextTag.add("TALPGet.StandMoney", mStandMoney);// 核定养老金领取标准

		mTextTag.add("LPEdor.ValiDate", mLPEdorItemSchema.getEdorValiDate());// 生效日
		mTextTag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator()));// 经办人
		mTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPEdorItemSchema.getApproveOperator()));// 复核人

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

		String[] tContListTitle = new String[3];
		xmlexport.addListTable(mListTable, tContListTitle);
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
