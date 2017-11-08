/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LCContDelPBL {
private static Logger logger = Logger.getLogger(LCContDelPBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSet mLCContSet = new LCContSet();
	LCContSchema mLCContSchema = new LCContSchema();
	private String mGrpContNo;
	
	public LCContDelPBL() {
	}
	/**
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC1002")) {
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Start LCContDelPBL  Submit ...");

		mInputData = (VData) cInputData.clone();
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
				"LCContSet", 0));
		ContDeleteUI tContDeleteUI = new ContDeleteUI();

		int count = mLCContSet.size();
		logger.debug(count);
		if(count>0){
			mGrpContNo =mLCContSet.get(1).getGrpContNo();
		}

		try {
			if (!lockNo(mGrpContNo)) {
				logger.debug("印刷号：["+mGrpContNo+"]锁定号码失败!");
				this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
				//tLockFlag = false;
				//mPubLock.unLock();
				//continue;
				return false;
			}
			for (int i = 1; i <= count; i++) {
				// LCContSchema mLCContSchema = new LCContSchema();
				mLCContSchema.setSchema(mLCContSet.get(i));
				String sscontno = mLCContSchema.getContNo();
				logger.debug(sscontno);

				cInputData.clear();
				cInputData.add(mGlobalInput);
				cInputData.add(mLCContSchema);

				if (!tContDeleteUI.submitData(cInputData, cOperate)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tContDeleteUI.mErrors);
					return false;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			mPubLock.unLock();
		}
		return true;
	}

}
