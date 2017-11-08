/*
 * @(#)PEdorRBDetailBL.java 2005-09-20 Copyright 2005 Sinosoft Co. Ltd. All rights reserved. All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全- 保全回退明细保存提交类
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
public class PEdorRBDetailBL {
private static Logger logger = Logger.getLogger(PEdorRBDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSet bLPEdorItemSet = new LPEdorItemSet();

	// 准备校验规则所需要的计算要素
	private BqCalBase mBqCalBase = new BqCalBase();

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorRBDetailBL() {
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
			bLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
					"LPEdorItemSet", 0); // 需要回退的保全项目
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
		// 查询批改项目信息
		if (!queryData()) {
			return false;
		}

		// 删除上次保存过的数据
		if (!delLastData()) {
			return false;
		}

		double dGetMoney = 0.0;
		// double dGetInterest = 0.0;
		// 合计出需要回退的保全补退费金额
		String sEdorType = "";
		for (int i = 1; i <= bLPEdorItemSet.size(); i++) {
			// 考虑金额合计直接取LPEdorItem还是从批改补退费（个人交费）表中合计？
			// 从补退费总表中合计 zhangtao 2005-09-28
			// dGetMoney -= bLPEdorItemSet.get(i).getGetMoney(); //跟原保全项目取反
			// dGetInterest -= bLPEdorItemSet.get(i).getGetInterest();
			// //跟原保全项目取反

			String tEdorType = bLPEdorItemSet.get(i).getEdorType();
			if (i == 1) {
				sEdorType = tEdorType;
			} else {
				sEdorType += "|" + tEdorType;
			}

			LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
			tLPEdorItemSet.add(bLPEdorItemSet.get(i));
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(mLPEdorItemSchema);
			tVData.add(tLPEdorItemSet);
			EdorBackBL tEdorBackBL = new EdorBackBL();
			if (!tEdorBackBL.submitData(tVData, "Detail")) {
				mErrors.copyAllErrors(tEdorBackBL.mErrors);
				return false;
			} else {
				VData rVData = tEdorBackBL.getResult();
				MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					CError.buildErr(this, "获取保全项目" + tEdorType
							+ "的保全回退明细结果时失败!");
					return false;
				} else {
					map.add(tMap);
				}
				dGetMoney += tEdorBackBL.getGetMoney();
			}
		}

		// 更新批改项目状态信息
		mLPEdorItemSchema.setGetMoney(dGetMoney);
		mLPEdorItemSchema.setGetInterest(0);
		mLPEdorItemSchema.setStandbyFlag1(bLPEdorItemSet.get(1)
				.getEdorAcceptNo());
		mLPEdorItemSchema.setStandbyFlag3(sEdorType); // 被回退的保全项目
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setModifyDate(mCurrentDate);
		mLPEdorItemSchema.setModifyTime(mCurrentTime);
		map.put(mLPEdorItemSchema, "UPDATE");
		return true;
	}

	/**
	 * 查询批改项目信息
	 * 
	 * @return boolean
	 */
	private boolean queryData() {
		String sEdorReasonCode = mLPEdorItemSchema.getEdorReasonCode();
		String sEdorReason = mLPEdorItemSchema.getEdorReason();

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			CError.buildErr(this, "查询批改项目信息失败！");
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		mLPEdorItemSchema.setEdorReasonCode(sEdorReasonCode);
		mLPEdorItemSchema.setEdorReason(sEdorReason);

		tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(bLPEdorItemSet.get(1).getEdorAcceptNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorState("0");
		bLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			CError.buildErr(this, "查询回退批改项目信息失败！");
			return false;
		}

		return true;
	}

	/**
	 * 删除上次保存过的数据
	 * 
	 * @return boolean
	 */
	private boolean delLastData() {

		// 清除P表中上次保存过的数据
		String edorno = mLPEdorItemSchema.getEdorNo();
		String edortype = mLPEdorItemSchema.getEdorType();

		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(" delete from LJSGetEndorse " + " where EndorsementNo='?edorno?' and FeeOperationType='?edortype?'");
		sqlbv.put("edorno", edorno);
		sqlbv.put("edortype", edortype);
		map.put(sqlbv,"DELETE");

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
		mResult.add(mTransferData);
		mResult.add(mBqCalBase);
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

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @return String
	 */
	public String getOperation() {
		return mOperate;
	}
}
