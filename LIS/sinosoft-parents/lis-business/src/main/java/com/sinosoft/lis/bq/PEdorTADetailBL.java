package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPGetBL;
import com.sinosoft.lis.db.LJAGetDrawDB;
import com.sinosoft.lis.db.LPAGetDrawDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPAGetDrawSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LPAGetDrawSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:保全
 * </p>
 * <p>
 * Description:转养老金BL层
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorTADetailBL {
private static Logger logger = Logger.getLogger(PEdorTADetailBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 增加的养老金金额 */
	private String strSumAmnt;
	/** 转入总金额，相当于变化的总保费 */
	private double mSumPrem = 0d;
	// 准备校验规则所需要的计算要素
	private BqCalBase mBqCalBase = new BqCalBase();
	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private List mBomList = new ArrayList();
	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();
	private LPAGetDrawSet mLPAGetDrawSet = new LPAGetDrawSet();
	private LPDutySchema mLPDutySchema = new LPDutySchema();
	private LPGetSchema mLPGetSchema = new LPGetSchema();
	private LPPolSchema mLPPolSchema = new LPPolSchema();
	private LPContSchema mLPContSchema = new LPContSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private Reflections mRef = new Reflections();

	public PEdorTADetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	// 获得增加的养老金金额，可在保全操作完毕后由BLF层加入mResult
	public String getSumAmnt() {
		return this.strSumAmnt;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPAGetDrawSet = (LPAGetDrawSet) mInputData.getObjectByObjectName(
					"LPAGetDrawSet", 0);
			mLPDutySchema = (LPDutySchema) mInputData.getObjectByObjectName(
					"LPDutySchema", 0);
			mLPGetSchema = (LPGetSchema) mInputData.getObjectByObjectName(
					"LPGetSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			if (mLPAGetDrawSet == null || mLPDutySchema == null
					|| mGlobalInput == null || mLPGetSchema == null
					|| mLPEdorItemSchema == null) {
				return false;
			}

			// 获得mLPEdorItemSchema的其它信息
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
			if (!tLPEdorItemDB.getInfo()) {
				logger.debug("---Error:在个险保全项目表中无匹配记录！---");
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorTADetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "获得个人保全项目记录时产生错误!";
				this.mErrors.addOneError(tError);
				return false;
			}
			this.mLPEdorItemSchema = tLPEdorItemDB.getSchema();

			// 取得实付表完整记录信息
			LPAGetDrawSet tLPAGetDrawSet = new LPAGetDrawSet();
			LPAGetDrawSchema tLPAGetDrawSchema = null;
			for (int i = 1; i <= mLPAGetDrawSet.size(); i++) {
				tLPAGetDrawSchema = new LPAGetDrawSchema();
				LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
				tLJAGetDrawDB
						.setActuGetNo(mLPAGetDrawSet.get(i).getActuGetNo());
				tLJAGetDrawDB.setPolNo(mLPAGetDrawSet.get(i).getPolNo());
				tLJAGetDrawDB.setDutyCode(mLPAGetDrawSet.get(i).getDutyCode());
				tLJAGetDrawDB.setGetDutyCode(mLPAGetDrawSet.get(i)
						.getGetDutyCode());
				tLJAGetDrawDB.setGetDutyKind(mLPAGetDrawSet.get(i)
						.getGetDutyKind());
				if (tLJAGetDrawDB.getInfo()) {
					mRef.transFields(tLPAGetDrawSchema, tLJAGetDrawDB
							.getSchema());
				}
				tLPAGetDrawSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPAGetDrawSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPAGetDrawSet.add(tLPAGetDrawSchema);
			}
			this.mLPAGetDrawSet.clear();
			this.mLPAGetDrawSet.set(tLPAGetDrawSet);

			// 录入需更新记录主要信息，后面查询最新记录数据时用
			this.mLPPolSchema.setEdorNo(mLPGetSchema.getEdorNo());
			this.mLPPolSchema.setEdorType(mLPGetSchema.getEdorType());
			this.mLPPolSchema.setPolNo(mLPGetSchema.getPolNo());
			this.mLPContSchema.setEdorNo(mLPGetSchema.getEdorNo());
			this.mLPContSchema.setEdorType(mLPGetSchema.getEdorType());
			this.mLPContSchema.setContNo(mLPGetSchema.getContNo());
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLPEdorItemDB.getInfo()) {
			mErrors.addOneError(new CError("查询批改项目信息失败!"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		if (mLPEdorItemSchema.getEdorState().trim().equals("2")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorTADetailBLF";
			tError.functionName = "checkData";
			tError.errorMessage = "该保全已经申请确认不能修改!";
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();

		// 查询财务类型
		String strFeeFinaType = "";
		// strFeeFinaType = this.queryFeeFinaType();

		// 先在LPGet标记一下用户选了哪几条记录（领取方式GetMode置成5），通过BL选出最新信息，
		// 等到保全确认时再更新对应的LJAGetDraw表中的记录，并把C和P表记录互换
		LPGetBL tLPGetBL = new LPGetBL();
		LPGetSchema tLPGetSchema = null;
		LPAGetDrawSchema tLPAGetDrawSchema = null;

		// LPGetSet tLPGetSet = new LPGetSet();
		// for (int i=1;i<=mLPAGetDrawSet.size();i++)
		// {
		// tLPAGetDrawSchema = new LPAGetDrawSchema();
		// tLPAGetDrawSchema = mLPAGetDrawSet.get(i);
		// //后面的操作：1.在LPGet表中查找对应用户选择的记录，如果查到，则将此记录对应的领取方式（GetMode）置成5。
		// // 2.如果没有查到，从LCGet里查出对应的最新信息，再将对应的领取方式（GetMode）置成5并在LPGet表中插入此条新记录。
		// tLPGetSchema = new LPGetSchema();
		// tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// tLPGetSchema.setPolNo(tLPAGetDrawSchema.getPolNo());
		// tLPGetSchema.setDutyCode(tLPAGetDrawSchema.getDutyCode());
		// tLPGetSchema.setGetDutyCode(tLPAGetDrawSchema.getGetDutyCode());
		//
		// if (!tLPGetBL.queryLastLPGet(this.mLPEdorItemSchema,tLPGetSchema))
		// {
		// logger.debug("---Error:未找到实付项对应的领取信息！---");
		// return false;
		// }
		// tLPGetSchema = new LPGetSchema();
		// tLPGetSchema = tLPGetBL.getSchema();
		// tLPGetSchema.setGetMode("5");
		// tLPGetSchema.setOperator(this.mGlobalInput.Operator);
		// tLPGetSchema.setModifyDate(strCurrentDate);
		// tLPGetSchema.setModifyTime(strCurrentTime);
		// tLPGetSet.add(tLPGetSchema);
		// }
		// mMap.put(tLPGetSet,"DELETE&INSERT");

		// 删除保全实付表P表中原来用户选择要转的领取项记录
		LPAGetDrawDB iLPAGetDrawDB = new LPAGetDrawDB();
		LPAGetDrawSet iLPAGetDrawSet = new LPAGetDrawSet();
		iLPAGetDrawDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		iLPAGetDrawDB.setEdorType(mLPEdorItemSchema.getEdorType());
		iLPAGetDrawSet = iLPAGetDrawDB.query();
		mMap.put(iLPAGetDrawSet, "DELETE");

		// 计算保额（转入的养老金金额）
		strSumAmnt = this.calAmnt();
		if (strSumAmnt == null) {
			return false;
		}

		// 用第二种转养老金计算方法，不需要获得新的责任编码
		// Commented by Wuhao
		// 获得新的责任编码
		// String strNewDutyCode = this.getNewDutyCode();
		// if (strNewDutyCode == null)
		// {
		// return false;
		// }
		// 获得要插入LPDuty表的最新信息
		// LPDutyBL tLPDutyBL = new LPDutyBL();
		// if
		// (!tLPDutyBL.queryLastLPDuty(this.mLPEdorItemSchema,this.mLPDutySchema))
		// {
		// return false;
		// }
		// LPDutySchema tLPDutySchema = new LPDutySchema();
		// tLPDutySchema = tLPDutyBL.getSchema();
		// //更新保全相关信息
		// tLPDutySchema.setAmnt(strSumAmnt);
		// tLPDutySchema.setDutyCode(strNewDutyCode);
		// tLPDutySchema.setStandPrem(0);
		// tLPDutySchema.setPrem(0);
		// tLPDutySchema.setSumPrem(0);
		// tLPDutySchema.setRiskAmnt("");
		// tLPDutySchema.setOperator(this.mGlobalInput.Operator);
		// tLPDutySchema.setMakeDate(strCurrentDate);
		// tLPDutySchema.setMakeTime(strCurrentTime);
		// tLPDutySchema.setModifyDate(strCurrentDate);
		// tLPDutySchema.setModifyTime(strCurrentTime);
		// mMap.put(tLPDutySchema,"DELETE&INSERT");

		// 转养老金以后，更新实付表P表（财务类型、业务类型等）
		LPAGetDrawSet tLPAGetDrawSet = new LPAGetDrawSet();
		for (int i = 1; i <= mLPAGetDrawSet.size(); i++) {
			tLPAGetDrawSchema = new LPAGetDrawSchema();
			tLPAGetDrawSchema.setSchema(mLPAGetDrawSet.get(i));
			tLPAGetDrawSchema.setConfDate(mLPEdorItemSchema.getEdorValiDate()); // 财务确认日期
			tLPAGetDrawSchema.setEnterAccDate(mLPEdorItemSchema
					.getEdorValiDate()); // 到帐日期
			tLPAGetDrawSchema.setFeeOperationType("TA"); // 补/退费业务类型
			// tLPAGetDrawSchema.setFeeFinaType(strFeeFinaType); //补/退费财务类型
			tLPAGetDrawSchema.setOperator(this.mGlobalInput.Operator);
			tLPAGetDrawSchema.setModifyDate(strCurrentDate);
			tLPAGetDrawSchema.setModifyTime(strCurrentTime);
			tLPAGetDrawSet.add(tLPAGetDrawSchema);
		}
		mMap.put(tLPAGetDrawSet, "DELETE&INSERT");

		// 获得要插入LPGet表的最新信息
		tLPGetBL = new LPGetBL();
		if (!tLPGetBL.queryLastLPGet(this.mLPEdorItemSchema, this.mLPGetSchema)) {
			CError.buildErr(this, "没有查到领取表中养老金的领取信息！");
			return false;
		}
		tLPGetSchema = new LPGetSchema();
		tLPGetSchema = tLPGetBL.getSchema();
		tLPGetSchema.setStandMoney(tLPGetSchema.getStandMoney()
				+ Double.parseDouble(strSumAmnt));
		tLPGetSchema.setActuGet(tLPGetSchema.getActuGet()
				+ Double.parseDouble(strSumAmnt));
		tLPGetSchema.setOperator(this.mGlobalInput.Operator);
		tLPGetSchema.setModifyDate(strCurrentDate);
		tLPGetSchema.setModifyTime(strCurrentTime);
		mMap.put(tLPGetSchema, "DELETE&INSERT");

		// 保费保额都不需要改动，所以后面三张表都不需要改动
		/*
		 * //准备责任表更新数据 LPDutyBL tLPDutyBL= new LPDutyBL(); if
		 * (!tLPDutyBL.queryLastLPDuty(this.mLPEdorItemSchema,this.mLPDutySchema)) {
		 * CError.buildErr(this, "没有查到责任表中信息！"); return false; } LPDutySchema
		 * tLPDutySchema = new LPDutySchema(); tLPDutySchema =
		 * tLPDutyBL.getSchema();
		 * //tLPDutySchema.setAmnt(tLPDutySchema.getAmnt()+Double.parseDouble(strSumAmnt));
		 * //不是更新保额，是保费 tLPDutySchema.setPrem(tLPDutySchema.getPrem() +
		 * mSumPrem); tLPDutySchema.setOperator(this.mGlobalInput.Operator);
		 * tLPDutySchema.setModifyDate(strCurrentDate);
		 * tLPDutySchema.setModifyTime(strCurrentTime);
		 * mMap.put(tLPDutySchema,"DELETE&INSERT");
		 * 
		 * //准备险种表更新数据 LPPolBL tLPPolBL = new LPPolBL();
		 * mLPPolSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
		 * mLPPolSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
		 * mLPPolSchema.setPolNo(this.mLPEdorItemSchema.getPolNo());
		 * mLPPolSchema.setContNo(this.mLPEdorItemSchema.getContNo()); if
		 * (!tLPPolBL.queryLastLPPol(this.mLPEdorItemSchema,this.mLPPolSchema)) {
		 * return false; } LPPolSchema tLPPolSchema = new LPPolSchema();
		 * tLPPolSchema = tLPPolBL.getSchema();
		 * //tLPPolSchema.setAmnt(tLPPolSchema.getAmnt()+Double.parseDouble(strSumAmnt));
		 * //不是更新保额，是保费 tLPPolSchema.setPrem(tLPPolSchema.getPrem() + mSumPrem);
		 * tLPPolSchema.setOperator(this.mGlobalInput.Operator);
		 * tLPPolSchema.setModifyDate(strCurrentDate);
		 * tLPPolSchema.setModifyTime(strCurrentTime);
		 * mMap.put(tLPPolSchema,"DELETE&INSERT");
		 * 
		 * //准备保单表更新数据 LPContBL tLPContBL = new LPContBL();
		 * mLPContSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
		 * mLPContSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
		 * mLPContSchema.setContNo(this.mLPEdorItemSchema.getContNo()); if
		 * (!tLPContBL.queryLastLPCont(this.mLPEdorItemSchema,this.mLPContSchema)) {
		 * return false; } LPContSchema tLPContSchema = new LPContSchema();
		 * tLPContSchema = tLPContBL.getSchema();
		 * //tLPContSchema.setAmnt(tLPContSchema.getAmnt()+Double.parseDouble(strSumAmnt));
		 * //不是更新保额，是保费 tLPContSchema.setPrem(tLPContSchema.getPrem() +
		 * mSumPrem); tLPContSchema.setOperator(this.mGlobalInput.Operator);
		 * tLPContSchema.setModifyDate(strCurrentDate);
		 * tLPContSchema.setModifyTime(strCurrentTime);
		 * mMap.put(tLPContSchema,"DELETE&INSERT");
		 */

		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setChgAmnt(strSumAmnt);
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);
		mMap.put(mLPEdorItemSchema, "DELETE&INSERT");

		// 准备校验规则所需要的计算要素
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		double tSumLoan = 0.0;
		if (tBqPolBalBL.calContLoanAndAutoPay(mLPEdorItemSchema.getContNo(),
				mLPEdorItemSchema.getEdorValiDate())) {
			tSumLoan = tBqPolBalBL.getCalResult();
		}
		mBqCalBase.setContSumLoan(tSumLoan);
		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			// mResult.add(strSumAmnt);
			mResult.add(mMap);
			mResult.add(mBqCalBase);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorTADetailBLF";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 养老金保额计算函数，通过描述好的算法去计算
	 * 
	 * @return
	 */
	public String calAmnt() {
		StringBuffer sb = new StringBuffer();
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		// Calculator mCalculator = new Calculator();
		Calculator mCalculator = null;
		String tStr;
		double sumAmnt = 0;
		this.mSumPrem = 0d;
		try {
			// 计算转入的养老金金额总和（领取金额总和）
			for (int i = 1; i <= this.mLPAGetDrawSet.size(); i++) {
				LPAGetDrawSchema tempLPAGetDrawSchema = new LPAGetDrawSchema();
				tempLPAGetDrawSchema = this.mLPAGetDrawSet.get(i);

				// 先从“保全项目基本算法表”中查出算法对应的编码
				sb
						.append("SELECT CalCode"
								+ " FROM LMEdorCal"
								+ " WHERE RiskCode in (SELECT RiskCode FROM LcPol WHERE PolNo ='?PolNo?')"
								+ " and DutyCode='?DutyCode?'"
								+ " and CalType='TA' and EdorType='TA'");
				// 进行查询
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(sb.toString());
				sqlbv.put("PolNo", tempLPAGetDrawSchema.getPolNo());
				sqlbv.put("DutyCode", tempLPAGetDrawSchema.getDutyCode());
				tSSRS = tExeSQL.execSQL(sqlbv);
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				if (tSSRS == null) {
					// @@错误处理
					logger.debug("---Error:没有匹配的算法对应编码!---");
					return null;
				}
				BqCalBase mBqCalBase = new BqCalBase();
				mBqCalBase.setGetMoney(tempLPAGetDrawSchema.getGetMoney());
				mBqCalBase.setPolNo(tempLPAGetDrawSchema.getPolNo());
				mBqCalBase.setDutyCode(tempLPAGetDrawSchema.getDutyCode());
				mBqCalBase.setGetDutyCode(tempLPAGetDrawSchema.getGetDutyCode());
				mBqCalBase.setInsuredNo(tempLPAGetDrawSchema.getInsuredNo());
				mCalculator = new Calculator();
				mCalculator.setCalCode(tSSRS.GetText(1, 1));
				// 增加基本要素
				mSumPrem += tempLPAGetDrawSchema.getGetMoney();
				mCalculator.addBasicFactor("GetMoney", String
						.valueOf(tempLPAGetDrawSchema.getGetMoney()));
				mCalculator.addBasicFactor("PolNo", tempLPAGetDrawSchema
						.getPolNo());
				mCalculator.addBasicFactor("DutyCode", tempLPAGetDrawSchema
						.getDutyCode());
				mCalculator.addBasicFactor("GetDutyCode", tempLPAGetDrawSchema
						.getGetDutyCode());
				mCalculator.addBasicFactor("InsuredNo", tempLPAGetDrawSchema
						.getInsuredNo());

				// 清空字符串缓冲区
				sb.replace(0, sb.length(), "");
				sb.append("SELECT trunc(months_between(a.GetDate,b.InsuredBirthday)/12)"
								+ " FROM LJAGetDraw a,LCPol b WHERE"
								+ " a.ActuGetNo='?ActuGetNo?'"
								+ " and a.PolNo='?PolNo?'"
								+ " and a.DutyCode='?DutyCode?'"
								+ " and a.GetDutyKind='?GetDutyKind?'"
								+ " and a.GetDutyCode='?GetDutyCode?' and b.PolNo=a.PolNo");
				// 进行查询
				sqlbv.sql(sb.toString());
				sqlbv.put("ActuGetNo", tempLPAGetDrawSchema.getActuGetNo());
				sqlbv.put("PolNo", tempLPAGetDrawSchema.getPolNo());
				sqlbv.put("DutyCode", tempLPAGetDrawSchema.getDutyCode());
				sqlbv.put("GetDutyKind", tempLPAGetDrawSchema.getGetDutyKind());
				sqlbv.put("GetDutyCode", tempLPAGetDrawSchema.getGetDutyCode());
				tSSRS = tExeSQL.execSQL(sqlbv);
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				if (tSSRS == null) {
					// @@错误处理
					logger.debug("---Error:没有查到被保人领取年龄!---");
					return null;
				}
				mCalculator.addBasicFactor("InsuredGetYear", tSSRS
						.GetText(1, 1));
				mBqCalBase.setGetYear(Integer.valueOf(tSSRS.GetText(1, 1)));
				mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
				mBomList=mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				mCalculator.setBOMList(mBomList);
				tStr = mCalculator.calculate();
				if (tStr == null || tStr.trim().equals("")) {
					logger.debug("---Error:转养老金计算错误!---");
					return null;
				}
				sumAmnt = sumAmnt + Double.parseDouble(tStr);

				// 清空字符串缓冲区
				sb.replace(0, sb.length(), "");
			}
			return String.valueOf(sumAmnt);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorTADetailBL";
			tError.functionName = "calPrem";
			tError.errorMessage = "从“保全项目基本算法表”中查算法对应编码时产生错误!";
			this.mErrors.addOneError(tError);
			return null;
		}
	}

	/**
	 * 查询“补/退费财务类型”
	 * 
	 * @return String
	 */
	private String queryFeeFinaType() {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = null;
		StringBuffer tSB = new StringBuffer();
		String strFeeFinaType = "";
		// 组织SQL语句
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		tSB.append("SELECT CodeName" + " FROM ((select '1' as flag,CodeName"
				+ " from LDCode1"
				+ " where CodeType='TA' and Code='HK' and Code1='?Code1?')" + " union"
				+ " (select '2' as flag,CodeName" + " from LDCode1"
				+ " where CodeType='TA' and Code='HK' and Code1='000000'))"
				+ " WHERE rownum=1" + " ORDER BY flag");
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSB.append("SELECT CodeName" + " FROM ((select '1' as flag,CodeName"
					+ " from LDCode1"
					+ " where CodeType='TA' and Code='HK' and Code1='?Code1?')" + " union"
					+ " (select '2' as flag,CodeName" + " from LDCode1"
					+ " where CodeType='TA' and Code='HK' and Code1='000000')) g"
					+ " ORDER BY flag" + " limit 0,1" );	
		}
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSB.toString());
		sqlbv.put("Code1", mLPAGetDrawSet.get(1).getRiskCode());
		try {
			// 查询
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			if (tSSRS == null && tSSRS.MaxRow <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorTRDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询还款的“补/退费财务类型”时产生错误！";
				this.mErrors.addOneError(tError);
				return null;
			}
			strFeeFinaType = tSSRS.GetText(1, 1);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorTADetailBL";
			tError.functionName = "getNewDutyCode";
			tError.errorMessage = "获得新的责任编码时产生错误!";
			this.mErrors.addOneError(tError);
			return null;
		}
		return strFeeFinaType;
	}

	/**
	 * 获得新的责任编码 目标表：LPDuty,LCDuty
	 * 算法是：找出PolNo相同的所有DutyCode，再找出前6位与原DutyCode相同的所有DutyCode，再把最大的DutyCode加一，即为结果
	 * 
	 * @return
	 */
	/*
	 * private String getNewDutyCode() { String strPolNo =
	 * this.mLPDutySchema.getPolNo(); String strOldDutyCode =
	 * this.mLPGetSchema.getDutyCode(); if (strOldDutyCode == null ||
	 * strOldDutyCode.trim().equals("") || strOldDutyCode.trim().length()<6) {
	 * logger.debug("---Error:获得新的责任编码时，原责任编码错误！---"); return null; }
	 * strOldDutyCode = strOldDutyCode.trim().substring(0,6); String
	 * tSql="SELECT max(X)" + " FROM (select max(DutyCode) as X from LPDuty
	 * where PolNo='" + strPolNo + "' and DutyCode like '" + strOldDutyCode +
	 * "%'" + " union select max(DutyCode) as X from LCDuty where PolNo='" +
	 * strPolNo + "' and DutyCode like '" + strOldDutyCode + "%')"; ExeSQL
	 * tExeSQL = new ExeSQL(); SSRS tSSRS = new SSRS(); //进行查询 try { tSSRS =
	 * tExeSQL.execSQL(tSql); this.mErrors.copyAllErrors(tExeSQL.mErrors); if
	 * (tSSRS == null) { // @@错误处理
	 * logger.debug("---Error:获得新的责任编码时，未查询到最大编码！---"); return null; }
	 * String strNewDutyCode = tSSRS.GetText(1, 1); if (strNewDutyCode == null ||
	 * strNewDutyCode.trim().equals("") || strNewDutyCode.trim().length() < 6) { //
	 * @@错误处理 logger.debug("---Error:获得新的责任编码时，查询到的最大编码错误！---"); return
	 * null; } strNewDutyCode = strNewDutyCode.trim(); if
	 * (strNewDutyCode.length() == 6) { strNewDutyCode = strNewDutyCode + "01"; }
	 * else { strNewDutyCode = strNewDutyCode.substring(0,6) +
	 * String.valueOf(Integer.parseInt(strNewDutyCode.substring(6,strNewDutyCode.length()))+1); }
	 * return strNewDutyCode; } catch(Exception e) { // @@错误处理 CError tError =
	 * new CError(); tError.moduleName = "PEdorTADetailBL"; tError.functionName =
	 * "getNewDutyCode"; tError.errorMessage = "获得新的责任编码时产生错误!";
	 * this.mErrors.addOneError(tError); return null; } }
	 */
}
