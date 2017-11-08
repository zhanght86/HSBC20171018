/*
 * @(#)GrpEdorGMDetailBL.java      Apr 7, 2006
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全操作 -- 领取方式变更
 * </p>
 * <p>
 * Description: 团体保全领取方式变更保全明细BL层
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：lizhuo
 * @CreateDate: Apr 7, 2006
 * @version：1.0
 */
public class GrpEdorGMDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorGMDetailBL.class);
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
	private LPGetSet mLPGetSet = new LPGetSet();
	private TransferData mTransferData = new TransferData();

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
			mLPGetSet = (LPGetSet) mInputData.getObjectByObjectName("LPGetSet",
					0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);// add by liuxiaosong 2006-12-25
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorZTDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLPGetSet == null || mLPGrpEdorItemSchema == null
				|| mGlobalInput == null || mLPGetSet.size() < 1) {
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

	private boolean dealData() {
		// add by liuxiaosong
		if ("GM||PERSON".equals(mOperate) || "G&Detail".equals(mOperate)) {
			// if("GM||PERSON".equals(mOperate)){//del by liuxiaosong 2006-12-25
			String ContNo = mLPGetSet.get(1).getContNo();
			String PolNo = mLPGetSet.get(1).getPolNo();
			if ("".equals(PolNo) || PolNo == null || "".equals(ContNo)
					|| ContNo == null) {
				CError.buildErr(this, "传入数据不完整!");
				return false;
			}
			logger.debug("团险GM，复用个险GM后台程序");
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
			tLPEdorItemDB.setContNo(ContNo);
			tLPEdorItemDB.setPolNo(PolNo);
			tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema
					.getEdorAcceptNo());
			tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPEdorItemSet = tLPEdorItemDB.query();
			logger.debug("=======================GM-》tLPEdorItemDB=============="
							+ tLPEdorItemSet.size());
			if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
				CError.buildErr(this, "查询个人保全项目信息失败!");
				return false;
			}
			mInputData.clear();
			mInputData.add(tLPEdorItemSet.get(1));
			mInputData.add(mLPGetSet.get(1));
			mInputData.add(mGlobalInput);
			mInputData.add(mTransferData);
			PEdorGMDetailBL tPEdorGMDetailBL = new PEdorGMDetailBL();
			if (!tPEdorGMDetailBL.submitData(mInputData, "UPDATE||MAIN")) {
				mErrors.copyAllErrors(tPEdorGMDetailBL.mErrors);
				return false;
			}

			mResult.clear();
			mResult = tPEdorGMDetailBL.getResult();
		}
		return true;
	}

}
