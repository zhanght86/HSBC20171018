/*
 * @(#)PEdorRBConfirmBL.java	2005-09-20
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全- 保全回退确认生效处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-09-20
 */
public class PEdorRBConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorRBConfirmBL.class);
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
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	// 需要回退的保全项目
	private LPEdorItemSet bLPEdorItemSet = new LPEdorItemSet();

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorRBConfirmBL() {
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
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null
				|| bLPEdorItemSet == null) {
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
		// 查询回退批改项目信息
		if (!queryData()) {
			return false;
		}

		// 更新批改状态
		if (!updState()) {
			return false;
		}

		for (int i = 1; i <= bLPEdorItemSet.size(); i++) {
			String tEdorType = bLPEdorItemSet.get(i).getEdorType();

			LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
			tLPEdorItemSet.add(bLPEdorItemSet.get(i));
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(mLPEdorItemSchema);
			tVData.add(tLPEdorItemSet);
			EdorBackBL tEdorBackBL = new EdorBackBL();
			if (!tEdorBackBL.submitData(tVData, "Confirm")) {
				mErrors.copyAllErrors(tEdorBackBL.mErrors);
				return false;
			} else {
				VData rVData = tEdorBackBL.getResult();
				MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					CError.buildErr(this, "获取保全项目" + tEdorType
							+ "的保全回退生效结果时失败!");
					return false;
				} else {
					map.add(tMap);
				}
			}
		}

		// //更新保全项目状态为确认生效
		// mLPEdorItemSchema.setEdorState("0");
		// mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		// mLPEdorItemSchema.setModifyDate(mCurrentDate);
		// mLPEdorItemSchema.setModifyTime(mCurrentTime);
		//
		// map.put(mLPEdorItemSchema, "UPDATE");
		return true;
	}

	/**
	 * 查询回退批改项目信息
	 * 
	 * @return boolean
	 */
	private boolean queryData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getStandbyFlag1());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		bLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			CError.buildErr(this, "查询回退批改项目信息失败！");
			return false;
		}
		return true;
	}

	/**
	 * 统一更新保全受理、保全申请批单、保全项目的 批改状态为回退状态[b]
	 * 
	 * @return boolean
	 */
	private boolean updState() {
		String tEdorAcceptNo = mLPEdorItemSchema.getStandbyFlag1();
		String wherePartItem = "where EdorAcceptNo='?tEdorAcceptNo?' and contno = '?contno?'";
		String wherePartApp = "where EdorAcceptNo='?tEdorAcceptNo?' and otherno = '?contno?'";
		StringBuffer sbSQL = new StringBuffer();

		// 保全项目
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem set EdorState = 'b', ").append(
				" Operator = '").append("?Operator?").append(
				"', ModifyDate = '").append("?mCurrentDate?").append(
				"', ModifyTime = '").append("?mCurrentTime?").append("' ").append(
				wherePartItem);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("Operator", mGlobalInput.Operator);
		sbv1.put("mCurrentDate", mCurrentDate);
		sbv1.put("mCurrentTime", mCurrentTime);
		sbv1.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv1.put("contno", mLPEdorItemSchema.getContNo());
		map.put(sbv1, "UPDATE");

		// 保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorMain set EdorState = 'b', ").append(
				" Operator = '").append("?Operator?").append(
				"', ModifyDate = '").append("?mCurrentDate?").append(
				"', ModifyTime = '").append("?mCurrentTime?").append("' ").append(
				wherePartItem);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("Operator", mGlobalInput.Operator);
		sbv2.put("mCurrentDate", mCurrentDate);
		sbv2.put("mCurrentTime", mCurrentTime);
		sbv2.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
		map.put(sbv2, "UPDATE");

		// 保全申请
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp set EdorState = 'b', ").append(
				" Operator = '").append("?Operator?").append(
				"', ModifyDate = '").append("?mCurrentDate?").append(
				"', ModifyTime = '").append("?mCurrentTime?").append("' ").append(
				wherePartApp);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("Operator", mGlobalInput.Operator);
		sbv3.put("mCurrentDate", mCurrentDate);
		sbv3.put("mCurrentTime", mCurrentTime);
		sbv3.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv3.put("contno", mLPEdorItemSchema.getContNo());
		map.put(sbv3, "UPDATE");

		return true;
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

}
