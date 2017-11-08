package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 领取年龄变更
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
 * </p>
 * 
 * @author changpeng
 * @version 1.0
 */

public class PEdorGBPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorGBPrintBL.class);

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

	public PEdorGBPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("GB数据传输失败!");
			return false;
		}
		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("GB数据打印失败!");
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
		if (!mOperate.equals("PRINTGB")) {
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
		// BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema,
		// xmlexport);
		xmlexport.addDisplayControl("displayGB");
		xmlexport.addDisplayControl("displayTail1");
		ExeSQL exeSQL = new ExeSQL();
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
		// 领取年龄信息
		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSchema tLPGetSchema = new LPGetSchema();
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPGetDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPGetSet = tLPGetDB.query();
		if (tLPGetSet.size() < 1) {
			mErrors.addOneError("打印生成数据时，取领取年龄信息!");
			return false;
		}
		tLPGetSchema.setSchema(tLPGetSet.get(1));
		mTextTag.add("LCGet.StandMoney", tLPGetSchema.getStandMoney());
		// 计算原领取年龄
		String strSQL = "SELECT distinct trunc(Months_Between(b.GetStartDate,a.InsuredBirthday)/12)"
				+ " FROM LcPol a,LCGet b,LMDutyGet c"
				+ " WHERE a.PolNo='?PolNo?'"
				+ " and b.PolNo='?PolNo?'"
				+ " and b.GetDutyCode=c.GetDutyCode" + " and c.GetType2='1'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("PolNo", mLPEdorItemSchema.getPolNo());
		
		String ChgBeforAge = "";
		if (exeSQL.getOneValue(sqlbv) != "") {
			ChgBeforAge = exeSQL.getOneValue(sqlbv);
		}
		// 变更后领取年龄
		strSQL = "SELECT distinct trunc(Months_Between(b.GetStartDate,a.InsuredBirthday)/12)"
				+ " FROM LcPol a,LPGet b,LMDutyGet c"
				+ " WHERE a.PolNo='?PolNo?'"
				+ " and b.PolNo='?PolNo?'"
				+ " and b.EdorNo='?EdorNo?'"
				+ " and b.EdorType='?EdorType?'"
				+ " and b.GetDutyCode=c.GetDutyCode" + " and c.GetType2='1'";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("PolNo", mLPEdorItemSchema.getPolNo());
		sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());
		String ChgAfterAge = "";
		if (exeSQL.getOneValue(sqlbv) != "") {
			ChgAfterAge = exeSQL.getOneValue(sqlbv);
		}
		String StandMoney = "0.00";
		StandMoney = BqNameFun.getRound(String.valueOf(tLPGetSchema
				.getStandMoney()));

		mTextTag.add("ChgBeforAge", ChgBeforAge);
		mTextTag.add("ChgAfterAge", ChgAfterAge);
		mTextTag.add("GBLCGet.StandMoney", StandMoney);

		mTextTag.add("LPEdor.ValiDate", mLPEdorItemSchema.getEdorValiDate());// 生效日
		mTextTag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator()));// 经办人
		mTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPEdorItemSchema.getApproveOperator()));// 复核人
		/**
		 * LAAgentDB tLAAgentDB = new LAAgentDB(); LAAgentSchema tLAAgentSchema =
		 * new LAAgentSchema();
		 * tLAAgentDB.setAgentCode(tLCContSchema.getAgentCode());
		 * if(!tLAAgentDB.getInfo()){ mErrors.addOneError("打印生成数据时，取业务机构信息失败!"); }
		 * tLAAgentSchema = tLAAgentDB.getSchema(); LABranchGroupDB
		 * tLABranchGroupDB = new LABranchGroupDB(); LABranchGroupSchema
		 * tLABranchGroupSchema = new LABranchGroupSchema();
		 * tLABranchGroupDB.setAgentGroup(tLAAgentSchema.getAgentGroup());
		 * if(!tLABranchGroupDB.getInfo()){
		 * CError.buildErr(this,"打印生成数据时，取业务机构信息失败!"); } tLABranchGroupSchema =
		 * tLABranchGroupDB.getSchema();
		 * mTextTag.add("ManageCom",tExeSQL.getOneValue("select name from ldcom
		 * where comcode = '" + tLABranchGroupSchema.getManageCom() + "'"));
		 * String e_sql = "select name from labranchgroup where agentgroup = '" +
		 * tLABranchGroupSchema.getUpBranch()+ "'"; String tLABranchGroup =
		 * tExeSQL.getOneValue(e_sql);
		 * mTextTag.add("LABranchGroup.Name",tLABranchGroup);
		 * mTextTag.add("LAAgent.Name",tLAAgentSchema.getName());//业务员姓名
		 * mTextTag.add("LAAgent.AgentCode",tLAAgentSchema.getAgentCode());//业务员编码
		 * return true;
		 */
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
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
