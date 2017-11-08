/*
 * @(#)PEdorRBDetailBL.java	2005-09-20
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPBonusPolDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEngBonusPolDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccClassFeeDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccFeeDB;
import com.sinosoft.lis.db.LPInsureAccFeeTraceDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOBonusPolSchema;
import com.sinosoft.lis.schema.LOEngBonusPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.lis.vschema.LOEngBonusPolSet;
import com.sinosoft.lis.vschema.LPBonusPolSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEngBonusPolSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全- 退保回退确认生效处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao pst
 * @version：1.0 2.0
 * @CreateDate：2005-09-20,2008-12-09
 */
public class PEdorCTBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorCTBackConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	//public CErrors mErrors = new CErrors();

	private MMap map = new MMap();

	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LCContStateSet mInstLCContStateSet = new LCContStateSet();

	private LCContStateSet mUpdLCContStateSet = new LCContStateSet();

	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorCTBackConfirmBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = // 需要回退的保全项目
			(LPEdorItemSchema) mInputData.getObjectByObjectName(
					"LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("=== 退保回退确认生效 ===");
		Reflections tRef = new Reflections();

		double dContChgPrem = 0.0; // 保单保费更新
		double dContChgAmnt = 0.0; // 保单保额更新
		
		double dContSumPrem = 0.0; // 保单保额更新

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询个人保单信息失败!");
			return false;
		}
		if (tLCContSet == null || tLCContSet.size() < 1) {
			CError.buildErr(this, "没有查到退保的保单!");
			return false;
		}
		LCContSchema tLCContSchema = tLCContSet.get(1);

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		LPContSet tLPContSet = tLPContDB.query();
		if (tLPContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全个人保单信息失败!");
			return false;
		}
		if (tLPContSet != null && tLPContSet.size() > 0) // 合同退保
		{
			tLCContSchema.setAppFlag("1");
			tLCContSchema.setOperator(mGlobalInput.Operator);
			tLCContSchema.setModifyDate(mCurrentDate);
			tLCContSchema.setModifyTime(mCurrentTime);

			if (!updLCContState(mLPEdorItemSchema.getContNo(), "000000",
					"000000","Terminate")) {
				return false;
			}
//			if (!updLCContState(mLPEdorItemSchema.getContNo(), "000000",
//					"000000","Available")) {
//				return false;
//			}
			map.put(tLCContSchema, "UPDATE");
		}

		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		LPPolSet tLPolSet = tLPPolDB.query();
		if (tLPPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全个人保单险种信息失败!");
			return false;
		}
		if (tLPolSet != null && tLPolSet.size() > 0) // 险种退保
		{
			LCPolSet updLCPolSet = new LCPolSet(); // 需要更新的C表险种

			LCPolDB tLCPolDB;
			LCPolSet tLCPolSet;
			LCPolSchema tLCPolSchema;
			for (int i = 1; i <= tLPolSet.size(); i++) {
				tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(tLPolSet.get(i).getPolNo());
				tLCPolSet = tLCPolDB.query();
				if (tLPPolDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询保全个人保单险种信息失败!");
					return false;
				}
				if (tLCPolSet == null || tLCPolSet.size() < 1) {
					CError.buildErr(this, "没有查到退保的保单险种!");
					return false;
				}
				tLCPolSchema = tLCPolSet.get(1);
				tLCPolSchema.setAppFlag("1");
				tLCPolSchema.setOperator(mGlobalInput.Operator);
				tLCPolSchema.setModifyDate(mCurrentDate);
				tLCPolSchema.setModifyTime(mCurrentTime);
				updLCPolSet.add(tLCPolSchema);

				// 回退保单终止状态信息
				if (!updLCContState(tLCPolSchema.getContNo(), tLCPolSchema
						.getInsuredNo(), tLCPolSchema.getPolNo(),"Terminate")) {
					return false;
				}

				// 回退保单终止状态信息
//				if (!updLCContState(tLCPolSchema.getContNo(), tLCPolSchema
//						.getInsuredNo(), tLCPolSchema.getPolNo(),"Available")) {
//					return false;
//				}
				dContChgPrem += tLCPolSchema.getPrem();
				dContChgAmnt += tLCPolSchema.getAmnt();
				dContSumPrem += tLCPolSchema.getSumPrem();

				// 如果有万能险结算，删除结算记录，恢复结算前的结算日期与账户余额
				// 如果万能险退保后系统有结算，则不允许再回退，还是补做结算？
				if (!backInsurAcc(mLPEdorItemSchema.getEdorNo(),
						mLPEdorItemSchema.getEdorType(), tLPolSet.get(i)
								.getPolNo())) {
					return false;
				}
			}
			map.put(updLCPolSet, "UPDATE");
		}else
		{
			CError.buildErr(this, "没有查到退保的保单险种!");
			return false;
		}

		if (dContChgPrem > 0) {
			tLCContSchema.setPrem(tLCContSchema.getPrem() + dContChgPrem);
			tLCContSchema.setAmnt(tLCContSchema.getAmnt() + dContChgAmnt);
			tLCContSchema.setSumPrem(tLCContSchema.getSumPrem() + dContSumPrem);			
			tLCContSchema.setOperator(mGlobalInput.Operator);
			tLCContSchema.setModifyDate(mCurrentDate);
			tLCContSchema.setModifyTime(mCurrentTime);
			map.put(tLCContSchema.getSchema(), "UPDATE");
		}

		// 退保回退，撤销续期重新抽档
//		LJSPayDB tLJSPayDB = new LJSPayDB();
//		tLJSPayDB.setOtherNo(mLPEdorItemSchema.getContNo());
//		tLJSPayDB.setOtherNoType("2");
//		LJSPaySet tLJSPaySet = tLJSPayDB.query();
//		if (tLJSPayDB.mErrors.needDealError()) {
//			CError.buildErr(this, "续期应收信息查询失败!");
//			return false;
//		}
//		if (tLJSPaySet != null && tLJSPaySet.size() > 0) {
//			TransferData tTransferData = new TransferData();
//			tTransferData.setNameAndValue("SubmitFlag", "noSubmit");
//			VData tVData = new VData();
//			tVData.add(tLCContSchema);
//			tVData.add(tTransferData);
//			tVData.add(mGlobalInput);
//
//			IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
//			if (!tIndiDueFeeCancelBL.submitData(tVData, "BQApp")) {
//				mErrors.copyAllErrors(tIndiDueFeeCancelBL.mErrors);
//				return false;
//			}
//			tVData.clear();
//			tVData = tIndiDueFeeCancelBL.getResult();
//			MMap tMMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
//			if (tMMap != null) {
//				map.add(tMMap);
//			}
//		} else {
//			logger.debug("== 没有续期应收数据 保单号：　"
//					+ mLPEdorItemSchema.getContNo() + "==");
//		}

		return true;
	}

	/**
	 * 更新保单状态表
	 */
	private boolean updLCContState(String sContNo, String sInsuredNo,
			String sPolNo,String tStateType) {

		String delSql = " delete from lccontstate where trim(statetype) = '?tStateType?' and trim(state) = '1' "
				+ " and enddate is null and startdate = '?startdate?' and contno = '?sContNo?"+
				// "' and insuredno = '" + sInsuredNo + //数据转换为000000
				"' and polno = '?sPolNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(delSql);
		sqlbv.put("tStateType", tStateType);
		sqlbv.put("startdate", mLPEdorItemSchema.getEdorAppDate());
		sqlbv.put("sContNo", sContNo);
		sqlbv.put("sPolNo", sPolNo);
		map.put(sqlbv, "DELETE");

//		String sEndDate = PubFun.calDate(mLPEdorItemSchema.getEdorAppDate(),
//				-1, "D", null);
//		String sql = " select * from lccontstate where trim(statetype) = '"+tStateType+"'"
//				+ " and enddate = '"
//				+ sEndDate
//				+ "'  and contno = '"
//				+ sContNo
//				+
//				// "' and insuredno = '" + sInsuredNo +
//				"' and polno = '" + sPolNo + "'";
//		logger.debug(sql);
//		LCContStateDB tLCContStateDB = new LCContStateDB();
//		LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(sql);
//		if (tLCContStateDB.mErrors.needDealError()) {
//			CError.buildErr(this, "保单状态查询失败!");
//			return false;
//		}
//		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
//			tLCContStateSet.get(1).setEndDate("");
//			map.put(tLCContStateSet.get(1), "UPDATE");
//		}

		return true;
	}

	/**
	 * 退保万能险结算回退
	 * 
	 * @param sPolNo
	 */
	private boolean backInsurAcc(String sEdorNo, String sEdorType, String sPolNo) {

		Reflections tRef = new Reflections();
		// 查出P表中的结算履历(退保时的结算)
		LCInsureAccTraceSet delLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
		tLPInsureAccTraceDB.setEdorNo(sEdorNo);
		tLPInsureAccTraceDB.setEdorType(sEdorType);
		tLPInsureAccTraceDB.setPolNo(sPolNo);
		LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
		if (tLPInsureAccTraceDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
			return false;
		}
		if (tLPInsureAccTraceSet != null && tLPInsureAccTraceSet.size() > 0) {
			LCInsureAccTraceSchema tLCInsureAccTraceSchema;
			LCInsureAccTraceSet insert_LCInsureAccTraceSet = new LCInsureAccTraceSet();
			for (int k = 1; k <= tLPInsureAccTraceSet.size(); k++) {
				// 删除退保时做的结算记录
				tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				tRef.transFields(tLCInsureAccTraceSchema, tLPInsureAccTraceSet
						.get(k));
				//不能删除累计生息 利息，新增一条累计生息利息  负值，用于冲销结算时被提取的利息记录，
				String trace_finType = BqCalBL.getFinType_HL_SC("HL", tLCInsureAccTraceSchema.getInsuAccNo(), "LX");
				if(tLCInsureAccTraceSchema.getInsuAccNo().equals("000001")
						&&tLCInsureAccTraceSchema.getMoneyType().equals(trace_finType))
				{
					String mLimit = PubFun.getNoLimit(tLCInsureAccTraceSchema.getManageCom());
			        String tLXSerialNo = PubFun1.CreateMaxNo("SERIALNO", mLimit);
			        tLCInsureAccTraceSchema.setSerialNo(tLXSerialNo);
			        //设置一条负值的利息记录，用于冲销结算时被提取的利息记录
			        tLCInsureAccTraceSchema.setMoney(-tLCInsureAccTraceSchema.getMoney());
			        tLCInsureAccTraceSchema.setMakeDate(this.mCurrentDate);
			        tLCInsureAccTraceSchema.setModifyDate(this.mCurrentDate);
			        tLCInsureAccTraceSchema.setModifyTime(this.mCurrentTime);
			        insert_LCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
			        continue;//不用也可以，主键变化了
				}
				delLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
			}
			map.put(insert_LCInsureAccTraceSet, "INSERT");
		}

		LCInsureAccSet updLCInsureAccSet = new LCInsureAccSet();
		LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
		tLPInsureAccDB.setPolNo(sPolNo);
		tLPInsureAccDB.setEdorNo(sEdorNo);
		tLPInsureAccDB.setEdorType(sEdorType);
		LPInsureAccSet updLPInsureAccSet = tLPInsureAccDB.query();
		if (tLPInsureAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保险帐户信息失败!");
			return false;
		}
		if (updLPInsureAccSet != null && updLPInsureAccSet.size() > 0) {

			for (int m = 1; m <= updLPInsureAccSet.size(); m++) {
				LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
				tRef.transFields(tLCInsureAccSchema, updLPInsureAccSet.get(m));
				updLCInsureAccSet.add(tLCInsureAccSchema);
			}
		}

		LCInsureAccClassSet updLCInsureAccClassSet = new LCInsureAccClassSet();
		LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
		tLPInsureAccClassDB.setPolNo(sPolNo);
		tLPInsureAccClassDB.setEdorNo(sEdorNo);
		tLPInsureAccClassDB.setEdorType(sEdorType);
		LPInsureAccClassSet updLPInsureAccClassSet = tLPInsureAccClassDB
				.query();
		if (tLPInsureAccClassDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保险帐户分类信息失败!");
			return false;
		}
		if (updLPInsureAccClassSet != null && updLPInsureAccClassSet.size() > 0) {
			for (int m = 1; m <= updLPInsureAccClassSet.size(); m++) {
				LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
				tRef.transFields(tLCInsureAccClassSchema,
						updLPInsureAccClassSet.get(m));
				updLCInsureAccClassSet.add(tLCInsureAccClassSchema);
			}
		}
		map.put(updLCInsureAccSet, "UPDATE");
		map.put(updLCInsureAccClassSet, "UPDATE");
		map.put(delLCInsureAccTraceSet, "DELETE");

		LCInsureAccFeeTraceSet delLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
		LPInsureAccFeeTraceDB tLPInsureAccFeeTraceDB = new LPInsureAccFeeTraceDB();
		tLPInsureAccFeeTraceDB.setEdorNo(sEdorNo);
		tLPInsureAccFeeTraceDB.setEdorType(sEdorType);
		tLPInsureAccFeeTraceDB.setPolNo(sPolNo);
		LPInsureAccFeeTraceSet tLPInsureAccFeeTraceSet = tLPInsureAccFeeTraceDB
				.query();
		if (tLPInsureAccFeeTraceDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
			return false;
		}
		if (tLPInsureAccFeeTraceSet != null
				&& tLPInsureAccFeeTraceSet.size() > 0) {
			LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema;
			for (int k = 1; k <= tLPInsureAccFeeTraceSet.size(); k++) {
				// 删除退保时做的结算记录
				tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
				tRef.transFields(tLCInsureAccFeeTraceSchema,
						tLPInsureAccFeeTraceSet.get(k));
				delLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
			}
		}

		LCInsureAccFeeSet updLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LPInsureAccFeeDB tLPInsureAccFeeDB = new LPInsureAccFeeDB();
		tLPInsureAccFeeDB.setPolNo(sPolNo);
		tLPInsureAccFeeDB.setEdorNo(sEdorNo);
		tLPInsureAccFeeDB.setEdorType(sEdorType);
		LPInsureAccFeeSet updLPInsureAccFeeSet = tLPInsureAccFeeDB.query();
		if (tLPInsureAccFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保险帐户信息失败!");
			return false;
		}
		if (updLPInsureAccFeeSet != null && updLPInsureAccFeeSet.size() > 0) {

			for (int m = 1; m <= updLPInsureAccFeeSet.size(); m++) {
				LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
				tRef.transFields(tLCInsureAccFeeSchema, updLPInsureAccFeeSet
						.get(m));
				updLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
			}
		}

		LCInsureAccClassFeeSet updLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new LPInsureAccClassFeeDB();
		tLPInsureAccClassFeeDB.setPolNo(sPolNo);
		tLPInsureAccClassFeeDB.setEdorNo(sEdorNo);
		tLPInsureAccClassFeeDB.setEdorType(sEdorType);
		LPInsureAccClassFeeSet updLPInsureAccClassFeeSet = tLPInsureAccClassFeeDB
				.query();
		if (tLPInsureAccClassFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保险帐户分类信息失败!");
			return false;
		}
		if (updLPInsureAccClassFeeSet != null
				&& updLPInsureAccClassFeeSet.size() > 0) {
			for (int m = 1; m <= updLPInsureAccClassFeeSet.size(); m++) {
				LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
				tRef.transFields(tLCInsureAccClassFeeSchema,
						updLPInsureAccClassFeeSet.get(m));
				updLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
			}
		}
		
		map.put(updLCInsureAccFeeSet, "UPDATE");
		map.put(updLCInsureAccClassFeeSet, "UPDATE");
		map.put(delLCInsureAccFeeTraceSet, "DELETE");

		return true;
	}

	/**
	 * 获取退保前原始结算日期
	 * 
	 * @param sPolNo
	 */
	private String getLastBalaDate(String sPolNo) {
		String sql = " select max(paydate) from lcinsureacctrace  where polno = '?sPolNo?' and paydate < (select max(paydate) from lcinsureacctrace  where polno = '?sPolNo?' and trim(moneytype) = 'TB')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sPolNo", sPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sLastBalaDate = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "上次结算日期查询失败!");
			return null;
		}
		if (sLastBalaDate == null || sLastBalaDate.equals("")) {
			CError.buildErr(this, "未查到上次结算日期!");
			return null;
		}
		if (sLastBalaDate.length() > 10) {
			sLastBalaDate = sLastBalaDate.substring(0, 10);
		}
		return sLastBalaDate;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return null;
	}

}
