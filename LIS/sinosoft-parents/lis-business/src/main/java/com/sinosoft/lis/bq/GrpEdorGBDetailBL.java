/*
 * @(#)GrpEdorGBDetailBL.java      Apr 17, 2006
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
 * Title: 团体保全操作 －－ 领取年龄变更
 * </p>
 * <p>
 * Description: 团体领取年龄变更保全明细处理ＢＬ层
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：lizhuo
 * @CreateDate: Apr 17, 2006
 * @version：1.0
 */
public class GrpEdorGBDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorGBDetailBL.class);
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
		PEdorGBDetailBL tPEdorGBDetailBL = new PEdorGBDetailBL();
		if (!tPEdorGBDetailBL.submitData(mInputData, mOperate)) {
			mErrors.copyAllErrors(tPEdorGBDetailBL.mErrors);
			mErrors.addOneError("被保人领取年龄变更失败!");
			return false;
		}
		mResult.clear();
		MMap map = (MMap) tPEdorGBDetailBL.getResult().getObjectByObjectName(
				"MMap", 0);
		if (map != null) {
			mMap.add(map);
		}
		mLPGrpEdorItemSchema.setEdorState("3");
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");
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
			tError.moduleName = "GrpEdorGBDetailBL";
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
