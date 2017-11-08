/*
 * @(#)GrpEdorPTDetailBL.java      May 22, 2006
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全处理－－减保操作（选人）
 * </p>
 * <p>
 * Description: 团体保全减保保全明细处理BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：lizhuo
 * @CreateDate: May 22, 2006
 * @version：1.0
 */
public class GrpEdorPTDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorPTDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局业务数据变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	public boolean submitData(VData aInputData, String aOperator) {
		mInputData = (VData) aInputData.clone();
		mOperate = aOperator;

		if (!getInputData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;
	}

	private boolean dealData() {
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		PEdorPTDetailBL tPEdorPTDetailBL = new PEdorPTDetailBL();
		if (!tPEdorPTDetailBL.submitData(mInputData, mOperate)) {
			mErrors.copyAllErrors(tPEdorPTDetailBL.mErrors);
			mErrors.addOneError("被保人领取年龄变更失败!");
			return false;
		}
		mResult.clear();
		MMap map = (MMap) tPEdorPTDetailBL.getResult().getObjectByObjectName(
				"MMap", 0);
		if (map != null) {
			mMap.add(map);
		}
		mLPGrpEdorItemSchema.setEdorState("3");
		mLPGrpEdorItemSchema.setModifyDate(sCurrentDate);
		mLPGrpEdorItemSchema.setModifyTime(sCurrentTime);
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		// 更新 LPGrpEdorItem : XinYQ added on 2006-11-24
		String UpdateSQL = new String("");
		UpdateSQL = "update LPGrpEdorItem " + "set GetMoney = "
				+ "(select nvl(sum(GetMoney), 0) " + "from LPEdorItem "
				+ "where 1 = 1 " + "and EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ "and EdorType = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ "and GrpContNo = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "'), "
				+ "ModifyDate = '"
				+ sCurrentDate
				+ "', "
				+ "ModifyTime = '"
				+ sCurrentTime
				+ "' "
				+ "where 1 = 1 "
				+ "and EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ "and EdorType = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ "and GrpContNo = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'";
		// logger.debug(UpdateSQL);
		mMap.put(UpdateSQL, "UPDATE");

		mResult.add(mMap);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorPTDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError.buildErr(this, "接收数据不完整!");
			return false;
		}

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(tLPGrpEdorItemDB.getEdorNo());
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询团体保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);
		return true;
	}
}
