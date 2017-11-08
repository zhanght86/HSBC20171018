/*
 * @(#)ParseGuideIn.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.cardgrp.LCGrpContSignBL;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团单合同签单处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wujs
 * @version 6.0
 */

public class GrpContSignAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GrpContSignAfterInitService.class);
	public GrpContSignAfterInitService() {
	}

	/** 存放结果 */
	private VData mVResult = new VData();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private TransferData mTransferData = new TransferData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

//	 private PubLock mPubLock = new PubLock();
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	
	
	private boolean lockNo(LCGrpContSchema tLCGrpContSchema) {
		// tongmeng 2008-05-22 modify
		// 使用新的加锁逻辑
		// mLockNoSet;
		/*
		 * VData tOnePolLockedNo = new VData(); //一个投保单需要锁定的号码集合
		 * 
		 * tOnePolLockedNo.add(tLCContSchema.getPrtNo()); String tTempFeeSql =
		 * "select distinct tempfeeno from LJTempFee a where " //现金方式,现金支票 + "
		 * otherno= '"+tLCContSchema.getPrtNo()+"' " + " and ConfFlag <>'1' and
		 * EnterAccDate is not null " + " and exists (select '1' from
		 * ljtempfeeclass where paymode in ('1','2','3','4') and
		 * tempfeeno=a.tempfeeno) " ;
		 * 
		 * ExeSQL tExeSQL = new ExeSQL(); SSRS tSSRS = new SSRS(); tSSRS =
		 * tExeSQL.execSQL(tTempFeeSql); for(int i=1;i<=tSSRS.getMaxRow();i++) {
		 * tOnePolLockedNo.add(tSSRS.GetText(i,1)); } String[] tLockNoArray =
		 * new String[tOnePolLockedNo.size()];
		 * tOnePolLockedNo.copyInto(tLockNoArray);
		 * if(!mPubLock.lock(tLockNoArray,"个单签单")) {
		 * logger.debug(tLCContSchema.getPrtNo()+"签单时锁定失败!"); CError
		 * tError = new CError(mPubLock.mErrors.getLastError());
		 * this.mErrors.addOneError(tError); return false; }
		 * mLockNoSet.add(tLockNoArray);
		 */
		if (!mPubLock.lock(tLCGrpContSchema.getPrtNo(), "LC1001")) {
			return false;
		}
		return true;
	}
	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug(">>>>>>submitData");
		boolean tDealFlag = true;
		boolean tLockFlag = true;
		// 将操作数据拷贝到本类中
		// mInputData = (VData) cInputData.clone();
		if (!getInputData(cInputData, cOperate))
			return false;
        //团单控制并发 add by liuqh
		LCGrpContSet mLCGrpContSet = (LCGrpContSet) mInputData.getObjectByObjectName(
				"LCGrpContSet", 0);
		if (mLCGrpContSet.size() <= 0) {
			CError.buildErr(this, "没有传入需要签发的保单!");
			return false;
		} else if (mLCGrpContSet.size() > 1) {
			CError.buildErr(this, "传入需要签发的保单数量大于1");
			return false;
		}
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema.setSchema(mLCGrpContSet.get(1));
		if (!lockNo(tLCGrpContSchema)) {
			logger.debug("锁定号码失败!");
			this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
			tLockFlag = false;
			//mPubLock.unLock();
			return false;
		}// 锁定主附险投保单号以及暂收费号码)
		
		try{
			 LCGrpContSignBL tLCGrpContSignBL = new LCGrpContSignBL();
		boolean tSignResult = tLCGrpContSignBL.submitData(mInputData, null);
		logger.debug("adsfjasdofjaslfdsalfjdslfasd");
		if (!tSignResult) {
			if (!tLCGrpContSignBL.mErrors.needDealError()){
				CError.buildErr(this, "部分签单完成");
			}
			else
				this.mErrors.copyAllErrors(tLCGrpContSignBL.mErrors);
			mTransferData.setNameAndValue("FinishFlag", "0");
			tDealFlag = false;
			// return false;
		} else {

			mTransferData.setNameAndValue("FinishFlag", "1");
		}
		}catch(RuntimeException e)
		{
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
//			 liuqh 2008-09-17 modify
			// 使用新的加锁逻辑
			mPubLock.unLock();
			// 如果需要解锁
			// if (tLockFlag) {
			// for (int i = 0; i < mLockNoSet.size(); i++) {
			// mPubLock.unLock((String[]) mLockNoSet.get(i));
			// }
			// }
		}
		
		this.mVResult.add(mTransferData);
		return tDealFlag;
		// return true;
	}

	public VData getResult() {
		return this.mVResult;
	}

	public TransferData getReturnTransferData() {
		return this.mTransferData;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		this.mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			return false;
		}

		return true;

	}

}
