package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPUWMasterMainDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPUWMasterMainSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 二次核保结论
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
 * @author wangyiyan
 * @version 1.0
 */
public class PEdorAFPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorAFPrintBL.class);

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

	private ListTable tAFListTable = new ListTable();

	public PEdorAFPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("AF数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("AF数据打印失败!");
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
		if (!mOperate.equals("PRINTAF")) {
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
		xmlexport.addDisplayControl("displayAF");
		xmlexport.addDisplayControl("displayTail1");
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		// displayHead1数据
		tLCContSchema = tLCContDB.getSchema();
		mTextTag.add("AppntName", tLCContSchema.getAppntName()); // 原投保人姓名
		mTextTag.add("InsuredName", tLCContSchema.getInsuredName()); // 被保人姓名
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo()); // 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号
		// 核保结论
		ExeSQL tExeSQL = new ExeSQL();
		LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
		tLPUWMasterMainDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPUWMasterMainDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPUWMasterMainDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保全核保信息失败!");
			return false;
		}
		LPUWMasterMainSchema tLPUWMasterMainSchema = new LPUWMasterMainSchema();
		tLPUWMasterMainSchema = tLPUWMasterMainDB.getSchema();
		// 加费承保
		if (tLPUWMasterMainSchema.getPassFlag().equals("3")) {
			xmlexport.addDisplayControl("displayAF1");
			// 健康加费
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql("Select Sum(prem) From lpprem "
					+ "Where edorType='"
					+ "?edorType?"
					+ "' And EdorNo='" + "?EdorNo?"
					+ "' and PayPlanType='1'");
			sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv1.put("edorType", mLPEdorItemSchema.getEdorType());
			mTextTag.add("AddPremH", tExeSQL
					.getOneValue(sbv1));
			// 职业加费
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql("Select Sum(prem) From lpprem "
					+ "Where edorType='"
					+ "?edorType?"
					+ "' And EdorNo='" + "?EdorNo?"
					+ "' and PayPlanType='2'");
			sbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv2.put("edorType", mLPEdorItemSchema.getEdorType());
			mTextTag.add("AddPremO", tExeSQL
					.getOneValue(sbv2));

			// 续期标准保费
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql("Select Sum(prem) From lpprem "
					+ "Where edorType='"
					+ "?edorType?"
					+ "' And EdorNo='" + "?EdorNo?"
					+ "'");
			sbv3.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv3.put("edorType", mLPEdorItemSchema.getEdorType());
			mTextTag.add("Prem", tExeSQL
					.getOneValue(sbv3));
		}
		// 补费信息
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql("Select count(*) From LJSGetEndorse "
				+ "Where FeeOperationType='AF' And endorsementno='"
				+ "?endorsementno?"
				+ "' and SubFeeOperationType like '%P00%'");
        sqlbv.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		String j = tExeSQL.getOneValue(sqlbv);

		if (!"0".equals(j)) {
			xmlexport.addDisplayControl("displayAF2");
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null || tLCPolSet.size() == 0) {
				mErrors.addOneError("打印生成数据时，没有查询到保单险种信息!");
				return false;
			}
			LCPolSchema tLCPolSchema;
			tAFListTable.setName("AF");
			// 查询每个险种的补费项目
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				tLCPolSchema = new LCPolSchema();
				tLCPolSchema.setSchema(tLCPolSet.get(i));
				LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
				LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
				tLJSGetEndorseDB
						.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
				tLJSGetEndorseDB.setFeeOperationType("AF");
				tLJSGetEndorseDB.setPolNo(tLCPolSchema.getPolNo());
				tLJSGetEndorseDB.setRiskCode(tLCPolSchema.getRiskCode());
				tLJSGetEndorseSet = tLJSGetEndorseDB.query();
				LJSGetEndorseSchema tLJSGetEndorseSchema;
				if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size() != 0) {
					String[] strAF = new String[4];
					for (int k = 1; k <= tLJSGetEndorseSet.size(); k++) {
						tLJSGetEndorseSchema = new LJSGetEndorseSchema();
						tLJSGetEndorseSchema
								.setSchema(tLJSGetEndorseSet.get(k));
						LMRiskDB tLMRiskDB = new LMRiskDB();
						tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());
						if (!tLMRiskDB.getInfo()) {
							mErrors.addOneError("打印生成数据时，取险种名称信息失败!");
							return false;
						}
						strAF[0] = tLMRiskDB.getRiskShortName();
						if (tLJSGetEndorseSchema.getSubFeeOperationType()
								.equals(BqCode.Pay_Prem)) {
							strAF[1] = String.valueOf(tLJSGetEndorseSchema
									.getGetMoney());
						}
						if (tLJSGetEndorseSchema.getSubFeeOperationType()
								.equals(BqCode.Pay_InsurAddPremHealth)) {
							strAF[2] = String.valueOf(tLJSGetEndorseSchema
									.getGetMoney());
						}
						if (tLJSGetEndorseSchema.getSubFeeOperationType()
								.equals(BqCode.Pay_InsurAddPremOccupation)) {
							strAF[3] = String.valueOf(tLJSGetEndorseSchema
									.getGetMoney());
						}
					}
					tAFListTable.add(strAF);
				}
			}
		}
		// 特约承保
		if (tLPUWMasterMainSchema.getPassFlag().equals("4")) {
			xmlexport.addDisplayControl("displayAF4");
			mTextTag.add("uwnotice", tLPUWMasterMainSchema.getSpecReason());
		}
		/*
		 * 减额处理 if (tLPUWMasterMainSchema.getPassFlag()=="4") {
		 * xmlexport.addDisplayControl ("displayAF4") ; mTextTag.add
		 * ("Amnt",mLPEdorItemSchema.getChgAmnt()); mTextTag.add
		 * ("Prem",mLPEdorItemSchema.getChgPrem()); }
		 */
		// displayTail1信息
		mTextTag.add("LPEdor.ValiDate", mLPEdorItemSchema.getEdorValiDate());
		mTextTag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator()));
		mTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPEdorItemSchema.getApproveOperator()));

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tLCContSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取业务员信息失败!");
			return false;
		}
		/*
		 * tLAAgentSchema = tLAAgentDB.getSchema () ; LABranchGroupDB
		 * tLABranchGroupDB = new LABranchGroupDB () ; LABranchGroupSchema
		 * tLABranchGroupSchema = new LABranchGroupSchema () ;
		 * tLABranchGroupDB.setAgentGroup (tLAAgentSchema.getAgentGroup ()) ;
		 * 
		 * if (!tLABranchGroupDB.getInfo ()) { CError.buildErr
		 * (this,"打印生成数据时，取业务机构信息失败!") ; return false ; }
		 * 
		 * tLABranchGroupSchema = tLABranchGroupDB.getSchema () ;
		 * tLABranchGroupSchema = tLABranchGroupDB.getSchema () ; String e_sql =
		 * "select name from labranchgroup where agentgroup = '" +
		 * tLABranchGroupSchema.getUpBranch() + "'";
		 * mTextTag.add("ManageCom",tExeSQL.getOneValue("select name from ldcom
		 * where comcode = '" + tLABranchGroupSchema.getManageCom() +
		 * "'"));//营销服务部
		 * mTextTag.add("LABranchGroup.Name",tExeSQL.getOneValue(e_sql));//营业部
		 * 
		 * mTextTag.add("LAAgent.Name",tLAAgentSchema.getName());
		 * mTextTag.add("LAAgent.AgentCode",tLAAgentSchema.getAgentCode());
		 * return true;
		 */
		String tAgentCode = tLCContSchema.getAgentCode();

		String[] allArr = BqNameFun.getAllNames(tAgentCode);

//		String tDistrict = allArr[BqNameFun.DISTRICT]; // 区
//		String tDepart = allArr[BqNameFun.DEPART]; // 部
//		String tTeam = allArr[BqNameFun.TEAM]; // 组
//		String tSaleService = allArr[BqNameFun.SALE_SERVICE]; // 营销服务部
//		String tCom = allArr[BqNameFun.COM]; // *分公司*中心支公司
//		String tAdress = allArr[BqNameFun.ADDRESS]; // 公司地址
//		String tZipCode = allArr[BqNameFun.ZIPCODE]; // 公司邮编
//		String tPhone = allArr[BqNameFun.PHONE]; // 公司电话
//		String tAgentName = allArr[BqNameFun.AGENT_NAME]; // 代理人姓名
//		// 上面tCom中是分公司和中支合在一起的，如果需要分别显示分公司和中支，则可以取下两个数，但不推荐使用：
//		String tFilial = allArr[BqNameFun.FILIAL]; // *分公司
//		String tBranch = allArr[BqNameFun.CENTER_BRANCH]; // *中心支公司
		mTextTag.add("ManageCom", allArr[BqNameFun.SALE_SERVICE]);
		mTextTag.add("LABranchGroup.Name", allArr[BqNameFun.DEPART]);
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCContSchema.getAgentCode());
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成打印数据失败!");
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
