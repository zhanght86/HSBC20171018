/*
 * @(#)ParseGuideIn.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 合同签单处理类
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
import com.sinosoft.lis.db.LCIndAppSignLogDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIndAppSignLogSchema;
import com.sinosoft.lis.tb.LCContSignBL;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIndAppSignLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class ProposalSignAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(ProposalSignAfterInitService.class);
	/** 存放结果 */
	private VData mVResult = new VData();

	/** 记录锁定的号码 */
	private VData mLockNoSet = new VData();
	/** 公共锁定号码类 */
	// private PubLock mPubLock = new PubLock();
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	
	private VData mResult = new VData();// 保存提交数据库的数据
	private MMap mMMap = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug(">>>>>>submitData");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		boolean tDealFlag = true;
		boolean tLockFlag = true;
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		LCContSet mLCContSet = (LCContSet) mInputData.getObjectByObjectName(
				"LCContSet", 0);
		if (mLCContSet.size() <= 0) {
			CError.buildErr(this, "没有传入需要签发的保单!");
			return false;
		} else if (mLCContSet.size() > 1) {
			CError.buildErr(this, "传入需要签发的保单数量大于1");
			return false;
		}

		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setSchema(mLCContSet.get(1));
		// tongmeng 2008-01-15 add 增加锁表防止并发
		//tongmeng 2009-04-30 modify
		//签单在TbWorkFlowBL中控制并发
		/*if (!lockNo(tLCContSchema)) {
			logger.debug("锁定号码失败!");
			this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
			tLockFlag = false;
			return false;
		} */

		try {
			LCContSignBL tLCContSignBL = new LCContSignBL();
			boolean tSignResult = tLCContSignBL.submitData(mInputData, null);
			if (!tSignResult) {
				if (!tLCContSignBL.mErrors.needDealError())
					CError.buildErr(this, "签单没有通过");
				tDealFlag = false;
			}

			if (tLCContSignBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tLCContSignBL.mErrors);
				tDealFlag = false;
			}
			this.mVResult = tLCContSignBL.mVResult;
			if (!tDealFlag) {
				// 如果有问题,需要记录错误信息
				makeAppSignLog(tLCContSchema.getPrtNo()); // 记录签单错误日志
			}
			// 解锁
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// tongmeng 2008-05-22 modify
			// 使用新的加锁逻辑
			//tongmeng 2009-04-30 modify
			//签单在TbWorkFlowBL中控制并发
		//	mPubLock.unLock();
		}

		return tDealFlag;
	}

	public ProposalSignAfterInitService() {

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

	private boolean getInputData() {

		return true;

	}

	/**
	 * 对tLCPolSet中的数据相关号码锁定 输出：如果锁定不成功，则返回false,否则返回true
	 */
	private boolean lockNo(LCContSchema tLCContSchema) {
		// tongmeng 2008-05-22 modify
		// 使用新的加锁逻辑
		if (!mPubLock.lock(tLCContSchema.getPrtNo(), "LC0002")) {
			return false;
		}
		return true;
	}

	/**
	 * 记录个单签单失败原因
	 * 
	 * @param tContNo
	 *            投保单号
	 */

	public boolean makeAppSignLog(String tPrtNo) {
		int n = this.mErrors.getErrorCount();
		logger.debug("n==" + n);
		String strErr = "";
		String strErrCode ="";
		for (int t = 0; t <= n - 1; t++) {
			strErr += (t + 1) + ": " + this.mErrors.getError(t).errorMessage;
			strErrCode +=  this.mErrors.getError(t).errorNo+",";
		}
		logger.debug("strErr:" + strErr);

		LCIndAppSignLogDB tLCIndAppSignLogDB = new LCIndAppSignLogDB();
		LCIndAppSignLogSet tLCIndAppSignLogSet = new LCIndAppSignLogSet();
		LCIndAppSignLogSchema tLCIndAppSignLogSchema = new LCIndAppSignLogSchema();

		String mSerialNo = PubFun1.CreateMaxNo("SerialNo", 20);
		String tSQL = "select * from LCIndAppSignLog where PrtNo='" + "?PrtNo?"
				+ "' order by SignNo desc ";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	        sqlbv1.sql(tSQL);
	        sqlbv1.put("PrtNo", tPrtNo);
		tLCIndAppSignLogSet = tLCIndAppSignLogDB.executeQuery(sqlbv1);
		logger.debug("****tLCIndAppSignLogSet.size:  "
				+ tLCIndAppSignLogSet.size());
		if (tLCIndAppSignLogSet == null || tLCIndAppSignLogSet.size() == 0) {
			tLCIndAppSignLogSchema.setPrtNo(tPrtNo);
			tLCIndAppSignLogSchema.setSignNo(1);
			tLCIndAppSignLogSchema.setMakeDate(PubFun.getCurrentDate());
			tLCIndAppSignLogSchema.setMakeTime(PubFun.getCurrentTime());
			tLCIndAppSignLogSchema.setOperator(mGlobalInput.Operator);
			tLCIndAppSignLogSchema.setErrType("01");  //add by liuqh 2008-10-25
			tLCIndAppSignLogSchema.setErrCode(strErrCode);  //add by liuqh 2008-10-25 以便判断是否自动订正
			tLCIndAppSignLogSchema.setState("00");  //add by liuqh 2008-10-25
			tLCIndAppSignLogSchema.setReason(strErr); // 如果签单错误，系统会记录错误并中断处理，故跳出后出错原因肯定是错误类中的最后一个原因
		} else {
			tLCIndAppSignLogSchema.setPrtNo(tPrtNo);
			tLCIndAppSignLogSchema.setSignNo(tLCIndAppSignLogSet.get(1)
					.getSignNo() + 1);
			tLCIndAppSignLogSchema.setMakeDate(PubFun.getCurrentDate());
			tLCIndAppSignLogSchema.setMakeTime(PubFun.getCurrentTime());
			tLCIndAppSignLogSchema.setOperator(mGlobalInput.Operator);
			tLCIndAppSignLogSchema.setErrType("01");
			tLCIndAppSignLogSchema.setErrCode(strErrCode);
			tLCIndAppSignLogSchema.setState("00");
			tLCIndAppSignLogSchema.setReason(strErr); // 如果签单错误，系统会记录错误并中断处理，故跳出后出错原因肯定是错误类中的最后一个原因
//			使每条错误在记录中唯一
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql("delete from lcindappsignlog where prtno='"+"?prtno?"+"' and errtype= '01' and signno<='"+"?signno?"+"'");
			sqlbv2.put("prtno", tPrtNo);
			sqlbv2.put("signno", tLCIndAppSignLogSet.get(1)
					.getSignNo());
			mMMap.put(sqlbv2, "DELETE");
		}
		// 设置流水号
		tLCIndAppSignLogSchema.setSerino(mSerialNo);

		
		// 定期自动删除签单错误日志（当保单已经签单，并且错误日志产生的时间大于7天）
		// 以后通过程序另行删除  modify by liuqh 2008-10-24
//		String str2 = "delete from LCIndAppSignLog a where makedate<=sysdate+7 and exists(select 1 from lccont where PrtNo=a.PrtNo and appflag='1')";
//		tMMap.put(str2, "DELETE");
		
		mMMap.put(tLCIndAppSignLogSchema, "INSERT");
		mResult.add(mMMap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, ""))
			return false;
		return true;
	}

}
