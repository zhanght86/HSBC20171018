package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 公司解约批单
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

public class PEdorEAPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(PEdorEAPrintBL.class);

	// 公共数据
	private VData mResult = new VData();
	//public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;
	private VData mInputData = new VData();
	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private XmlExportNew xmlexport = new XmlExportNew();
	private TextTag mTextTag = new TextTag();

	public PEdorEAPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("EA数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("EA数据打印失败!");
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
		if (!mOperate.equals("PRINTEA")) {
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
		// xmlexport.addDisplayControl ("displayHead1") ;
		xmlexport.addDisplayControl("displayEA");
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
		ExeSQL tExeSQL = new ExeSQL();
		// 公司解约信息
		// 基本保额应退金额
		// 得到主险保单号
		int num = 0;
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("select mainpolno from lcpol where contno = '?contno?' and mainpolno=polno");
		sbv1.put("contno", mLPEdorItemSchema.getContNo());
		String mainPolNo = tExeSQL
				.getOneValue(sbv1);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("select riskcode from lcpol where contno = '?contno?' and mainpolno=polno");
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
		String mRiskCode = tExeSQL
				.getOneValue(sbv2);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql("select riskshortname from lmrisk where riskcode = '?mRiskCode?'");
		sbv3.put("mRiskCode", mRiskCode);
		String mRiskName = tExeSQL
				.getOneValue(sbv3);

		LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		// tLJSGetEndorseDB.setContNo(mLPEdorItemSchema.getContNo());
		tLJSGetEndorseDB.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		// 查询批改补退费表（应收/应付）记录
		tLJSGetEndorseSet = tLJSGetEndorseDB.query();
		if (tLJSGetEndorseSet == null || tLJSGetEndorseSet.size() == 0) {

			// 以下是修改批改补退费无记录的解约 20051111
			LPPolDB tLPPolDB = new LPPolDB();
			LPPolSet tLPPolSet = new LPPolSet();
			tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPPolDB.setEdorType("EA");
			tLPPolSet = tLPPolDB.query();
			LPPolSchema tLPPolSchema;
			if (tLPPolSet != null || tLPPolSet.size() != 0) {
				int j = 1;
				for (int i = 1; i <= tLPPolSet.size(); i++) {
					tLPPolSchema = new LPPolSchema();
					tLPPolSchema.setSchema(tLPPolSet.get(i));
					LMRiskDB tLMRiskDB = new LMRiskDB();
					tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
					if (!tLMRiskDB.getInfo()) {
						mErrors.addOneError("打印生成数据时，取险种名称信息失败!");
						return false;
					}
					if (!tLPPolSchema.getPolNo().equals(
							tLPPolSchema.getMainPolNo())) {
						xmlexport.addDisplayControl("displayEA"
								+ String.valueOf(j + 3));
						mTextTag.add("SubRisk" + String.valueOf(j) + "Money",
								"0.00");
						mTextTag.add("SubRisk" + String.valueOf(j), tLMRiskDB
								.getRiskShortName());
						j = j + 1;
					} else {
						xmlexport.addDisplayControl("displayEA1");
						mTextTag.add("Risk1", tLMRiskDB.getRiskShortName());
						mTextTag.add("Risk2", "基本险");
						mTextTag.add("BaseRiskMoney", "0.00");
					}
				}
			}
			// 修改结束20051111
		} else {
			// 对该批单对应的每个应付记录做处理
			LJSGetEndorseSchema tLJSGetEndorseSchema;
			int subRiskNum = 1; // 附加险序号
			for (int i = 1; i <= tLJSGetEndorseSet.size(); i++) {
				tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				tLJSGetEndorseSchema.setSchema(tLJSGetEndorseSet.get(i));
				// 如果是主险应付记录
				if (mainPolNo != null
						&& mainPolNo.equals(tLJSGetEndorseSchema.getPolNo())) {
					if (tLJSGetEndorseSchema.getSubFeeOperationType().equals(
							BqCode.Get_BaseCashValue)
							|| StrTool.compareString(mLPEdorItemSchema
									.getStandbyFlag1(), "1"))
					// 判断是否犹豫期
					{
						//
						xmlexport.addDisplayControl("displayEA1");
						double d = tLJSGetEndorseSchema.getGetMoney();
						// double d2 = d ;
						// if ("00902000".equals (mRiskCode))
						// {
						/*
						 * if (!"1".equals (mLPEdorItemSchema.getStandbyFlag1 ())) {
						 * EdorCalZT tEdorCalZT = new EdorCalZT () ; d =
						 * tEdorCalZT.getActuMoney (mRiskCode,d,"UnCalFee") ; if ((d -
						 * d2) != 0) { String mFee = BqNameFun.getRound (String.valueOf
						 * (d - d2)) ; mTextTag.add ("Fee",BqNameFun.getRound (mFee)) ;
						 * xmlexport.addDisplayControl ("displayEA17") ; } }
						 * mTextTag.add ("Fee",BqNameFun.getRound (mFee)) ;
						 * xmlexport.addDisplayControl ("displayEA17") ; }
						 */

						String mMoney1 = BqNameFun.getRound(String.valueOf(d));
						if (d < 0) {
							mMoney1 = mMoney1.substring(1, mMoney1.length());
						}
						mTextTag.add("Risk1", mRiskName);
						mTextTag.add("Risk2", "基本险");
						mTextTag.add("BaseRiskMoney", BqNameFun
								.getRound(mMoney1));
						// }
					}
					// 手续费
					if (tLJSGetEndorseSchema.getSubFeeOperationType().equals(
							BqCode.Pay_TBFee)) {
						xmlexport.addDisplayControl("displayEA17");
						double d = tLJSGetEndorseSchema.getGetMoney();
						String mFee = BqNameFun.getRound(String.valueOf(d));
						if (d < 0) {
							mFee = mFee.substring(1, mFee.length());
						}
						mTextTag.add("Fee", BqNameFun.getRound(mFee));
						num = num + 1;
					}

					if (tLJSGetEndorseSchema.getSubFeeOperationType().equals(
							BqCode.Get_FinaBonus)) { // 终了红利
						xmlexport.addDisplayControl("displayEA3");
						double d = tLJSGetEndorseSchema.getGetMoney();
						String mMoney2 = BqNameFun.getRound(String.valueOf(d));
						if (d < 0) {
							mMoney2 = mMoney2.substring(1, mMoney2.length());
						}
						mTextTag.add("FinaBonusMoney", BqNameFun
								.getRound(mMoney2));
					}
					if (tLJSGetEndorseSchema.getSubFeeOperationType().equals(
							BqCode.Get_BonusCashValue)) { // 红利保额现金价值
						xmlexport.addDisplayControl("displayEA2");
						double d = tLJSGetEndorseSchema.getGetMoney();
						String mMoney3 = BqNameFun.getRound(String.valueOf(d));
						if (d < 0) {
							mMoney3 = mMoney3.substring(1, mMoney3.length());
						}
						mTextTag.add("BonusMoney", BqNameFun.getRound(mMoney3));
					}
					if (tLJSGetEndorseSchema.getSubFeeOperationType().equals(
							BqCode.Get_AddPremHealth)) { // 健康加费应退金额
						xmlexport.addDisplayControl("displayEA15");
						double d = tLJSGetEndorseSchema.getGetMoney();
						String mMoney4 = BqNameFun.getRound(String.valueOf(d));
						if (d < 0) {
							mMoney4 = mMoney4.substring(1, mMoney4.length());
						}
						mTextTag.add("AddPremHealth", BqNameFun
								.getRound(mMoney4));
					}
					if (tLJSGetEndorseSchema.getSubFeeOperationType().equals(
							BqCode.Get_AddPremOccupation)) { // 职业加费应退金额
						xmlexport.addDisplayControl("displayEA16");
						double d = tLJSGetEndorseSchema.getGetMoney();
						String mMoney5 = BqNameFun.getRound(String.valueOf(d));
						if (d < 0) {
							mMoney5 = mMoney5.substring(1, mMoney5.length());
						}
						mTextTag.add("AddPremOccupation", BqNameFun
								.getRound(mMoney5));
					}

					/*
					 * if (tLJSGetEndorseSchema.getSubFeeOperationType ().equals (
					 * "P012")) { //相关扣费 xmlexport.addDisplayControl ("displayEA9") ;
					 * mTextTag.add ("Note",tLJSGetEndorseSchema.getGetMoney ()) ; }
					 */
				} else {
					xmlexport.addDisplayControl("displayEA"
							+ String.valueOf(subRiskNum + 3));
					String d = BqNameFun.getRound(tLJSGetEndorseSchema
							.getGetMoney());
					if (tLJSGetEndorseSchema.getGetMoney() < 0) {
						d = d.substring(1, d.length());
					}
					mTextTag.add("SubRisk" + String.valueOf(subRiskNum)
							+ "Money", BqNameFun.getRound(d));
					LMRiskDB tLMRiskDB = new LMRiskDB();
					tLMRiskDB.setRiskCode(tLJSGetEndorseSchema.getRiskCode());
					if (!tLMRiskDB.getInfo()) {
						mErrors.addOneError("打印生成数据时，取险种名称信息失败!");
						return false;
					}
					mTextTag.add("SubRisk" + String.valueOf(subRiskNum),
							tLMRiskDB.getRiskShortName());

					subRiskNum = subRiskNum + 1;
				}
			}
			// 以下是修改批改补退费无记录的解约20051111
			LPPolDB tLPPolDB = new LPPolDB();
			LPPolSet tLPPolSet = new LPPolSet();
			tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPPolDB.setEdorType("EA");
			tLPPolSet = tLPPolDB.query();
			LPPolSchema tLPPolSchema;
			if (tLPPolSet != null || tLPPolSet.size() != 0) {
				for (int i = 1; i <= tLPPolSet.size(); i++) {
					tLPPolSchema = new LPPolSchema();
					tLPPolSchema.setSchema(tLPPolSet.get(i));
					SQLwithBindVariables sbv=new SQLwithBindVariables();
					sbv.sql("Select polno From LJSGetEndorse Where  contno='?contno?' And EndorsementNo='?EndorsementNo?' And polno='?polno?'");
					sbv.put("contno", tLPPolSchema.getContNo());
					sbv.put("EndorsementNo", tLPPolSchema.getEdorNo());
					sbv.put("polno", tLPPolSchema.getPolNo());
					String tPolNo = tExeSQL
							.getOneValue(sbv);
					if (tPolNo == null) {
						if (tLPPolSchema.getPolNo().equals(
								tLPPolSchema.getMainPolNo())) {
							xmlexport.addDisplayControl("displayEA1");
							mTextTag.add("BaseRiskMoney", "0.00");
						} else {
							LMRiskDB tLMRiskDB = new LMRiskDB();
							tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
							if (!tLMRiskDB.getInfo()) {
								mErrors.addOneError("打印生成数据时，取险种名称信息失败!");
								return false;
							}
							xmlexport.addDisplayControl("displayEA"
									+ String.valueOf(subRiskNum + 3));
							mTextTag.add("SubRisk" + String.valueOf(subRiskNum)
									+ "Money", "0.00");
							mTextTag.add(
									"SubRisk" + String.valueOf(subRiskNum),
									tLMRiskDB.getRiskShortName());
							subRiskNum = subRiskNum + 1;
						}
					}
				}
			}
			// 修改结束20051111
		}
 
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql("Select abs(Sum(getmoney)) From LJSGetEndorse "
				+ "Where FeeOperationType='EA' And SubFeeOperationType like concat('%',concat('?SubFeeOperationType?','%')) And endorsementno='?endorsementno?'");
		sbv4.put("SubFeeOperationType", BqCode.Get_MorePrem);
		sbv4.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		String prem = tExeSQL
				.getOneValue(sbv4);
		if (prem != null && prem != "") {
			xmlexport.addDisplayControl("displayEA8");
			mTextTag.add("Prem", BqNameFun.getRound(prem));
		}
		// 自垫本金和利息，查询批改补退费表（应收/付）记录
		// 应收自垫本金
        
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql("Select abs(Sum(getmoney)) From LJSGetEndorse "
				+ "Where FeeOperationType='EA' And SubFeeOperationType like concat('%',concat('?SubFeeOperationType?','%')) And endorsementno='?endorsementno?'");
		sbv5.put("SubFeeOperationType", BqCode.Get_AutoPayPrem);
		sbv5.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		String apay = tExeSQL
				.getOneValue(sbv5);
		if (apay != null && apay != "") {
			xmlexport.addDisplayControl("displayEA10");
			mTextTag.add("APay", BqNameFun.getRound(apay));
			num = num + 1;
		}
		// 应收自垫利息
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql("Select abs(Sum(getmoney)) From LJSGetEndorse "
				+ "Where FeeOperationType='EA' And SubFeeOperationType like concat('%',concat('?SubFeeOperationType?','%')) And endorsementno='?endorsementno?'");
		sbv6.put("SubFeeOperationType", BqCode.Get_AutoPayPremInterest);
		sbv6.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		String apayi = tExeSQL
				.getOneValue(sbv6);
		if (apayi != null && apayi != "") {
			xmlexport.addDisplayControl("displayEA11");
			mTextTag.add("APayI", BqNameFun.getRound(apayi));
			num = num + 1;
		}
		// 质押贷款本金和利息，查询批改补退费表（应收/应付）记录里该合同的所有RF的应收记录
		// 应收保单质押贷款本金
		SQLwithBindVariables sbv7=new SQLwithBindVariables();
		sbv7.sql("Select abs(Sum(getmoney)) From LJSGetEndorse "
				+ "Where FeeOperationType='EA' And SubFeeOperationType like concat('%',concat('?SubFeeOperationType?','%')) And endorsementno='?endorsementno?'");
		sbv7.put("SubFeeOperationType", BqCode.Get_LoanCorpus);
		sbv7.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		String loan = tExeSQL
				.getOneValue(sbv7);
		if (loan != null && loan != "") {
			xmlexport.addDisplayControl("displayEA12");
			mTextTag.add("Loan", BqNameFun.getRound(loan));
			num = num + 1;
		}
		// 应收保单质押贷款利息
		SQLwithBindVariables sbv8=new SQLwithBindVariables();
		sbv8.sql("Select abs(Sum(getmoney)) From LJSGetEndorse "
				+ "Where FeeOperationType='EA' And SubFeeOperationType like concat('%',concat('?SubFeeOperationType?','%')) And endorsementno='?endorsementno?'");
		sbv8.put("SubFeeOperationType", BqCode.Get_LoanCorpusInterest);
		sbv8.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		String loani = tExeSQL
				.getOneValue(sbv8);
		if (loani != null && loani != "") {
			xmlexport.addDisplayControl("displayEA13");
			mTextTag.add("LoanI", BqNameFun.getRound(loani));
			num = num + 1;
		}
		SQLwithBindVariables sbv9=new SQLwithBindVariables();
		sbv9.sql("Select abs(Sum(getmoney)) From LJSGetEndorse "
				+ "Where FeeOperationType='EA' And endorsementno='?endorsementno?'");
		sbv9.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		String realMoney = tExeSQL
				.getOneValue(sbv9);
		if (realMoney != null && realMoney != "") {
			xmlexport.addDisplayControl("displayEA14");
			mTextTag.add("RealMoney", BqNameFun.getRound(realMoney));
		}
		if (num != 0) {
			xmlexport.addDisplayControl("displayEA9");
		}
		// 解约原因
		mTextTag.add("cancelreason", mLPEdorItemSchema.getEdorReason());
		// 其它信息
		mTextTag.add("LPEdor.ValiDate", mLPEdorItemSchema.getEdorValiDate()); // 在bq包下
		// tCodeNameQuery.getOperator(mLPEdorItemSchema.getOperator ());
		mTextTag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator()));
		mTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPEdorItemSchema.getApproveOperator()));

		LAAgentDB tLAAgentDB = new LAAgentDB();
		// LAAgentSchema tLAAgentSchema = new LAAgentSchema () ;
		tLAAgentDB.setAgentCode(tLCContSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取业务员信息失败!");
			return false;
		}
		/*
		 * tLAAgentSchema = tLAAgentDB.getSchema () ; LABranchGroupDB
		 * tLABranchGroupDB = new LABranchGroupDB () ; LABranchGroupSchema
		 * tLABranchGroupSchema = new LABranchGroupSchema () ;
		 * tLABranchGroupDB.setAgentGroup (tLAAgentSchema.getAgentGroup ()) ; if
		 * (!tLABranchGroupDB.getInfo ()) { CError.buildErr
		 * (this,"打印生成数据时，取业务机构信息失败!") ; return false ; } tLABranchGroupSchema =
		 * tLABranchGroupDB.getSchema () ; tLABranchGroupSchema =
		 * tLABranchGroupDB.getSchema () ; String e_sql = "select name from
		 * labranchgroup where agentgroup = '" + tLABranchGroupSchema.getUpBranch () +
		 * "'" ; mTextTag.add ("ManageCom", tExeSQL.getOneValue ("select name from
		 * ldcom where comcode = '" + tLABranchGroupSchema.getManageCom () + "'")) ;
		 * //营销服务部 mTextTag.add ("LABranchGroup.Name",tExeSQL.getOneValue (e_sql)) ;
		 * //营业部
		 * 
		 * mTextTag.add ("LAAgent.Name",tLAAgentSchema.getName ()) ; mTextTag.add
		 * ("LAAgent.AgentCode",tLAAgentSchema.getAgentCode ()) ; return true ;
		 */
		String tAgentCode = tLCContSchema.getAgentCode();

		String[] allArr = BqNameFun.getAllNames(tAgentCode);
		/*
		 * String tDistrict = allArr[BqNameFun.DISTRICT] ; //区 String tDepart =
		 * allArr[BqNameFun.DEPART] ; //部 String tTeam = allArr[BqNameFun.TEAM] ; //组
		 * String tSaleService = allArr[BqNameFun.SALE_SERVICE] ; //营销服务部 String tCom =
		 * allArr[BqNameFun.COM] ; //*分公司*中心支公司 String tAdress =
		 * allArr[BqNameFun.ADDRESS] ; //公司地址 String tZipCode =
		 * allArr[BqNameFun.ZIPCODE] ; //公司邮编 String tPhone = allArr[BqNameFun.PHONE] ;
		 * //公司电话 String tAgentName = allArr[BqNameFun.AGENT_NAME] ; //代理人姓名 //
		 * 上面tCom中是分公司和中支合在一起的，如果需要分别显示分公司和中支，则可以取下两个数，但不推荐使用： String tFilial =
		 * allArr[BqNameFun.FILIAL] ; //*分公司 String tBranch =
		 * allArr[BqNameFun.CENTER_BRANCH] ; //*中心支公司
		 */
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
