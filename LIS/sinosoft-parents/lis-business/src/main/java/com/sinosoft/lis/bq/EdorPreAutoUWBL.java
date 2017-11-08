package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskSortDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPEdorUWErrorSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMRiskSortSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPEdorUWErrorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个单保全自动初次核保类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class EdorPreAutoUWBL {
private static Logger logger = Logger.getLogger(EdorPreAutoUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 入口标志 */
	private String mContType = "";
	private String mPreUWGrade = "A";
	private String mUWContGrade = "A";
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mUWFinaResult = null;
	private MMap mPrintMap = null;
	private LPEdorMainSchema mLPEdorMainSchema = null;
	private LPEdorMainSet mLPEdorMainSet = null;
	private LPEdorItemSchema mLPEdorItemSchema = null;
	private LPEdorItemSet mLPEdorItemSet = null;
	private Boolean mBomListFlag=false;
	private List mBomList = new ArrayList();
	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();
	private LPEdorUWErrorSet mLPEdorUWErrorSet = new LPEdorUWErrorSet();

	String mFlag;
	String mPayPrintParams = "";
	boolean isEdorAppLevel = false; // 是否在保全申请级做自动核保的标识

	public EdorPreAutoUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!prepareData()) { // 数据准备操作（preparedata())
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PreUWFlag", mLPEdorMainSchema
				.getApproveFlag());
		tTransferData.setNameAndValue("PreUWGrade", mLPEdorMainSchema
				.getApproveGrade());
		tTransferData.setNameAndValue("PayPrintParams", mPayPrintParams);
		mResult.add(tTransferData);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 得到保单号（避免依靠前台)
		mLPEdorMainSchema = (LPEdorMainSchema) cInputData
				.getObjectByObjectName("LPEdorMainSchema", 0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mContType = (String) cInputData.getObjectByObjectName("String", 0);
		if (mContType == null || mContType.equals("")) {
			mContType = "I";
		}

		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
		tLPEdorMainDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
		tLPEdorMainDB.setContNo(mLPEdorMainSchema.getContNo());
		mLPEdorMainSet = tLPEdorMainDB.query();
		if (mLPEdorMainSet == null || mLPEdorMainSet.size() != 1) {
			mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			mErrors.addOneError(new CError("查询批单信息失败！"));
			return false;
		}
		mLPEdorMainSchema = mLPEdorMainSet.get(1);
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		mLPEdorMainSet = new LPEdorMainSet();
		mLPEdorItemSet = new LPEdorItemSet();
		mMap = new MMap();
		mResult.clear();

		String sql = "select * from LPEdorItem where (UWFlag in ('0','5') or UWFlag is null)"
				+ " and EdorAcceptNo = '"
				+ "?EdorAcceptNo?"
				+ "'"
				+ " and EdorNo='"
				+ "?EdorNo?"
				+ "'"
				+ " and ContNo='"
				+ "?ContNo?"
				+ "'"
				+ " order by MakeDate,MakeTime";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("EdorAcceptNo", mLPEdorMainSchema.getEdorAcceptNo());
        sqlbv.put("EdorNo", mLPEdorMainSchema.getEdorNo());
        sqlbv.put("ContNo", mLPEdorMainSchema.getContNo());
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		mLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);
		int m = mLPEdorItemSet.size();
		for (int i = 1; i <= m; i++) {
			mLPEdorItemSchema = mLPEdorItemSet.get(i);
			if (!"2".equals(mLPEdorItemSchema.getEdorState())) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PGrpEdorPreAutoUWBL";
				tError.functionName = "prepareData";
				tError.errorMessage = "批单号:" + mLPEdorMainSchema.getEdorNo()
						+ "有个别项目未申请确认!";
				logger.debug("批单号:" + mLPEdorMainSchema.getEdorNo()
						+ "有个别项目未申请确认!");
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;

	}

	private boolean dealData() {
		String aFlag = "9";
		mPreUWGrade = "";
		mLPEdorMainSchema.setApproveFlag("9"); // 预设置初次核保结论为为通过

		int n = mLPEdorItemSet.size();
		for (int i = 1; i <= n; i++) {
			mLPEdorItemSchema = mLPEdorItemSet.get(i);
			mLPEdorItemSchema.setApproveFlag("9"); // 预设置核保结论为为通过

			aFlag = getAutoUWState(); // 对个单合同下险种进行核保
			if (aFlag.equals("0")) { // 核保过程出现异常
				return false;
			}
			if (!aFlag.equals("9")) {
				mLPEdorItemSchema.setApproveFlag("5"); // 设置初核核保结论
				mLPEdorMainSchema.setApproveFlag("5"); // 如果有一个保全项目初核不通过，要走审批
			}

			if (mPreUWGrade == null || "".equals(mPreUWGrade)) {
				mPreUWGrade = mUWContGrade;
			} else {
				if (mPreUWGrade != null
						&& mPreUWGrade.compareTo(mUWContGrade) < 0) {
					mPreUWGrade = mUWContGrade; // 保存当前最大初次核保级别
				}
			}

			mLPEdorItemSchema.setApproveGrade(mPreUWGrade);
			mLPEdorItemSchema.setApproveDate(PubFun.getCurrentDate());
			mLPEdorItemSchema.setApproveTime(PubFun.getCurrentTime());
			mLPEdorItemSchema.setApproveOperator(mGlobalInput.Operator);
			mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
			mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
			mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		}

		mLPEdorMainSchema.setApproveDate(PubFun.getCurrentDate());
		mLPEdorMainSchema.setApproveTime(PubFun.getCurrentTime());
		mLPEdorMainSchema.setApproveGrade(mPreUWGrade);
		mLPEdorMainSchema.setApproveOperator(mGlobalInput.Operator);
		mLPEdorMainSchema.setOperator(mGlobalInput.Operator);
		mLPEdorMainSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorMainSchema.setModifyTime(PubFun.getCurrentTime());
		mLPEdorMainSet.add(mLPEdorMainSchema);

		return true;
	}

	private String getAutoUWState() {

		String Sql, tCalCode;		
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String tUWState = "9";
		String tUWGrade = "";
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = new LMUWSet();

		// 项目级别的核保规则
		if (mContType != null && mContType.equals("I")) {
			//add by jiaqiangli 2009-03-11 MS需要增加到险种层 目前只有NS描述了riskcode <> '000000'初次核保
			Sql = "select * from LMUW where riskcode in (select '000000' from dual union select distinct riskcode from lppol where edorno='"+"?edorno?"+"' and edortype='"+"?edortype?"+"' and contno='"+"?contno?"+"') "
					+ " and RelaPolType='CS'" + " and UWType='"
					+ "?UWType?"
					+ "' order by riskcode ,UWorder"; // 取得个单险种核保规则
			sqlbv.sql(Sql);
			sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
			sqlbv.put("edortype", mLPEdorItemSchema.getEdorType());
			sqlbv.put("contno", mLPEdorItemSchema.getContNo());
			sqlbv.put("UWType", mLPEdorItemSchema.getEdorType());
		} else if (mContType != null && mContType.equals("G")) {
			Sql = "select * from LMUW where ( riskcode='000000') "
					+ " and RelaPolType='GCS'" + " and UWType='"
					+ "?UWType?"
					+ "' order by riskcode ,UWorder"; // 取得团单险种核保规则
			sqlbv.sql(Sql);
			sqlbv.put("UWType", mLPEdorItemSchema.getEdorType());
		} else {
			CError tError = new CError();
			tError.moduleName = "EdorPreAutoUWBL";
			tError.functionName = "getAutoUWState";
			tError.errorMessage = "保全自动初次核保的（团单、个单）类别传输错误!";
			this.mErrors.addOneError(tError);
			return "0";
		}

		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setOperator(mLPEdorItemSchema.getOperator());
		tBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		tBqCalBase.setPolNo(mLPEdorItemSchema.getPolNo());
		tBqCalBase.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tBqCalBase.setEdorType(mLPEdorItemSchema.getEdorType());
		//add by jiaqiangli 2009-03-26 审批规则里要用到insuredno
		//modify by jiaqiangli 2009-03-26 这个取法有问题，有可能存的是6个0，已在下面做修改
		//tBqCalBase.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		//add by jiaqiangli 2009-03-11 保全确认之前两个值是相等的
		tBqCalBase.setEdorAppDate(mLPEdorItemSchema.getEdorValiDate());
		tBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
		
		//add by jiaqiangli 2009-03-11 增加计算因子
		String tMainPolSQL = "select * from lcpol where contno='"+"?contno?"+"' and polno=mainpolno ";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tMainPolSQL);
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sbv);
		tBqCalBase.setAppntNo(tLCPolSet.get(1).getAppntNo());
		
		//tBqCalBase.setGetMoney(mLPEdorItemSchema.getGetMoney());
		//comment by jiaqiangli 2009-02-01 金额的取法修改 see also in EdorApproveSave.jsp
		ExeSQL tExeSQL = new ExeSQL();
		String tGetMoneySQL = "select (case when sum(getmoney*(case getflag when '1' then 1 else -1 end)) is not null then sum(getmoney*(case getflag when '1' then 1 else -1 end)) else 0 end) from ljsgetendorse where endorsementno in "
				+ "(select edorno from lpedoritem where edoracceptno='"
				+ "?edoracceptno?"
				+ "') and feeoperationtype = '"
				+ "?feeoperationtype?"
				+ "' and contno = '"
				+ "?contno?" + "' ";
		SQLwithBindVariables sqlbvt=new SQLwithBindVariables();
		sqlbvt.sql(tGetMoneySQL);
		sqlbvt.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
		sqlbvt.put("feeoperationtype", mLPEdorItemSchema.getEdorType());
		sqlbvt.put("contno", mLPEdorItemSchema.getContNo());
		double tGetMoney = Double.parseDouble(tExeSQL.getOneValue(sqlbvt));
		tBqCalBase.setGetMoney(tGetMoney);
		
		//add by jiaqiangli 2009-03-26 接上修改InsuredNo
		SQLwithBindVariables sbvs=new SQLwithBindVariables();
		sbvs.sql("select INSUREDNO from lcinsured where contno = '"+"?contno?"+"' ");
		sbvs.put("contno", mLPEdorItemSchema.getContNo());
		tBqCalBase.setInsuredNo(tExeSQL.getOneValue(sbvs));

		//add by jiaqiangli 2009-03-02 SumDangerAmnt
//		LMRiskSortDB tLMRiskSortDB = new LMRiskSortDB();
//		tLMRiskSortDB.setRiskCode(tLCPolSchema.getRiskCode());
//		tLMRiskSortDB.setRiskSortType("2");
//		LMRiskSortSet tLMRiskSortSet = tLMRiskSortDB.query();
//		if (tLMRiskSortSet != null && tLMRiskSortSet.size() > 0) {
//			try {
//				tBqCalBase.setSumDangerAmnt(Double.parseDouble(tExeSQL
//						.getOneValue("select HEALTHYAMNT2('"
//								+ tLCPolSchema.getInsuredNo() + "', '"
//								+ tLCPolSchema.getRiskCode() + "',  '"
//								+ tLMRiskSortSet.get(1).getRiskSortValue()
//								+ "') from dual"))); // 风险保额
//				tBqCalBase.setSumInvaliAmnt(Double.parseDouble(tExeSQL
//						.getOneValue("select INVALIDHEALTHYAMNT('"
//								+ tLCPolSchema.getInsuredNo() + "', '"
//								+ tLCPolSchema.getRiskCode() + "',  '"
//								+ tLMRiskSortSet.get(1).getRiskSortValue()
//								+ "') from dual"))); // 失效保单风险保额
//			} 
//			catch (Exception ex) {
//				CError.buildErr(this, "计算风险保额失败！");
//				// return "0";
//			}
//
//		}
		//add by jiaqiangli 2009-03-02 SumDangerAmnt
		
		tLMUWSet = tLMUWDB.executeQuery(sqlbv);

		logger.debug("tLMUWSet size : " + tLMUWSet.size());
		for (int i = 1; i <= tLMUWSet.size(); i++) {
			LMUWSchema tLMUWSchema = tLMUWSet.get(i);

			tCalCode = tLMUWSchema.getCalCode(); // 得到计算编码

			if (!calUWEndorse(tBqCalBase, tCalCode)) { // 核保计算, 如果核保不通过
				// tLMUWUnpassSet.add(tLMUWSchema);

				if (!prepareEdorUWError(tLMUWSchema, mLPEdorItemSchema)) {
					CError tError = new CError();
					tError.moduleName = "EdorPreAutoUWBL";
					tError.functionName = "getAutoUWState";
					tError.errorMessage = "保全自动初次核保的（团单、个单）类别传输错误!";
					this.mErrors.addOneError(tError);
					continue;
				}
				if (tUWGrade == null || tUWGrade.equals("")) {
					tUWGrade = tLMUWSchema.getUWGrade();
				} else {
					if (tUWGrade != null && tLMUWSchema.getUWGrade() != null
							&& tUWGrade.compareTo(tLMUWSchema.getUWGrade()) < 0) {
						tUWGrade = tLMUWSchema.getUWGrade(); // 保存当前保全项目的最大初次核保级别
					}
				}

				tUWState = "5";
			}
		}
		mUWContGrade = tUWGrade;
		return tUWState;
	}

	/**
	 * 判断自动核保是否通过
	 * 
	 * @return
	 */
	private boolean calUWEndorse(BqCalBase aBqCalBase, String aCalCode) {
		ExeSQL xExeSQL = new ExeSQL();
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(aCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Interval", aBqCalBase.getInterval());
		mCalculator.addBasicFactor("GetMoney", aBqCalBase.getGetMoney());
		mCalculator.addBasicFactor("Get", aBqCalBase.getGet());
		mCalculator.addBasicFactor("Mult", aBqCalBase.getMult());
		mCalculator.addBasicFactor("Prem", aBqCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", aBqCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetIntv", aBqCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", aBqCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", aBqCalBase.getSex());
		mCalculator.addBasicFactor("Job", aBqCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", aBqCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", aBqCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", aBqCalBase.getGetYear());
		mCalculator.addBasicFactor("Years", aBqCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("CValiDate", "");
		mCalculator.addBasicFactor("Count", aBqCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("AddRate", aBqCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", aBqCalBase.getGDuty());
		mCalculator.addBasicFactor("EdorNo", aBqCalBase.getEdorNo());
		mCalculator.addBasicFactor("EdorType", aBqCalBase.getEdorType());
		mCalculator.addBasicFactor("GrpContNo", aBqCalBase.getGrpContNo());
		mCalculator.addBasicFactor("GrpPolNo", aBqCalBase.getGrpPolNo());
		mCalculator.addBasicFactor("ContNo", aBqCalBase.getContNo());
		mCalculator.addBasicFactor("PolNo", aBqCalBase.getPolNo());
		mCalculator.addBasicFactor("InsuredNo", aBqCalBase.getInsuredNo());
		mCalculator.addBasicFactor("AppntNo", aBqCalBase.getAppntNo());
		mCalculator.addBasicFactor("SumDangerAmnt", aBqCalBase
				.getSumDangerAmnt());
		mCalculator.addBasicFactor("SumInvaliAmnt", aBqCalBase
				.getSumInvaliAmnt());
		mCalculator.addBasicFactor("EdorAppDate", aBqCalBase.getEdorAppDate());
		mCalculator
				.addBasicFactor("EdorValiDate", aBqCalBase.getEdorValiDate());
		mCalculator.addBasicFactor("RiskCode", aBqCalBase.getRiskCode());
		//add by jiaqiangli 2009-01-25 审批权限的自动初次核保与申请人的保全权限有关
		mCalculator.addBasicFactor("Operator", aBqCalBase.getOperator());
		//add by xiongzh 2009-10-20 加入客户号，便于客户层保全处理
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql("select otherno from lpedorapp where EdorAcceptNo = '"+"?EdorAcceptNo?"+"' ");
		sbv.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
		mCalculator.addBasicFactor("CustomerNo", xExeSQL.getOneValue(sbv));
		if (!prepareBOMList(aBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return false;
		}
		mCalculator.setBOMList(this.mBomList);// 添加BOMList
		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr == null || tStr.trim().equals("") || tStr.trim().equals("0")) {
			return true;
		} else {
			return false;
		}

	}
	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList(BqCalBase aBqCalBase) {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQEdorBL.setBqCalBase(aBqCalBase);
				this.mBomList = this.mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}

	private boolean prepareOutputData() {
		mMap.put(mLPEdorMainSet, "UPDATE");
		mMap.put(mLPEdorItemSet, "UPDATE");
		if (mLPEdorUWErrorSet.size() > 0) {
			mMap.put(mLPEdorUWErrorSet, "INSERT");
		}

		mResult.clear();
		mResult.add(mMap);

		return true;
	}

	private boolean prepareEdorUWError(LMUWSchema tLMUWSchema,
			LPEdorItemSchema tLPEdorItemSchema) { // 生成核保错误信息
		LPEdorUWErrorSchema tLPUWErrorSchemaTemp = new LPEdorUWErrorSchema();

		String strNoLimit = PubFun.getNoLimit(tLPEdorItemSchema.getManageCom());
		String aSerialNo = PubFun1.CreateMaxNo("SN", strNoLimit); // 生成打印流水号
		tLPUWErrorSchemaTemp.setSerialNo(aSerialNo);
		tLPUWErrorSchemaTemp.setEdorAcceptNo(tLPEdorItemSchema
				.getEdorAcceptNo());
		tLPUWErrorSchemaTemp.setEdorNo(tLPEdorItemSchema.getEdorNo());
		tLPUWErrorSchemaTemp.setEdorType(tLPEdorItemSchema.getEdorType());
		tLPUWErrorSchemaTemp.setGrpContNo(tLPEdorItemSchema.getGrpContNo());
		tLPUWErrorSchemaTemp.setContNo(tLPEdorItemSchema.getContNo());
		tLPUWErrorSchemaTemp.setPolNo(tLPEdorItemSchema.getPolNo());
		tLPUWErrorSchemaTemp.setUWRuleCode(tLMUWSchema.getCalCode());
		tLPUWErrorSchemaTemp.setUWError(tLMUWSchema.getRemark());
		tLPUWErrorSchemaTemp.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
		tLPUWErrorSchemaTemp.setManageCom(tLPEdorItemSchema.getManageCom());
		tLPUWErrorSchemaTemp.setModifyDate(PubFun.getCurrentDate());
		tLPUWErrorSchemaTemp.setModifyTime(PubFun.getCurrentTime());
		tLPUWErrorSchemaTemp.setMakeDate(PubFun.getCurrentDate());
		tLPUWErrorSchemaTemp.setMakeTime(PubFun.getCurrentTime());
		tLPUWErrorSchemaTemp.setOperator(mGlobalInput.Operator);
		mLPEdorUWErrorSet.add(tLPUWErrorSchemaTemp);
		return true;
	}

}
