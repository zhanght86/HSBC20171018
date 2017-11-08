package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 案件回退信息更新提交信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: 万泽辉 2005/11/17
 * @version 1.0
 */
public class LLCaseBackUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLCaseBackUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public LLCaseBackUI() {
	}

	public static void main(String[] args) {

		GlobalInput tGI = new GlobalInput();
		LLCaseBackUI tLLCaseBackUI = new LLCaseBackUI();

		tGI.ManageCom = "86";
		tGI.Operator = "001";

		// 打包数据
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("ClmNo", "90000006020");

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(mTransferData);

		tLLCaseBackUI.submitData(tVData, "");
	}
	
	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		
		if(mOperate.equals("1"))
		{
			LLCaseBackBL tLLCaseBackBL = new LLCaseBackBL();
			logger.debug("----------UI BEGIN----------");

			/**
			 * 案件回退保存回退原因，并生成新的赔案号
			 */
			if (tLLCaseBackBL.submitData(mInputData, mOperate) == false) {
				// @@错误处理
				CError.buildErr(this, "保存回退信息失败,"+tLLCaseBackBL.mErrors.getLastError());
				mResult.clear();
				return false;
			} else {
				mResult = null;
				mResult = tLLCaseBackBL.getResult();
			}
			mInputData = null;
		}
		else if(mOperate.equals("2"))
		{
			LLClaimConfirmPassReturnBL tLLClaimConfirmPassReturnBL = new LLClaimConfirmPassReturnBL();
			logger.debug("----------UI BEGIN----------");
			/**
			 * 案件回退是要冲销赔案要抵消赔金
			 */
			if (tLLClaimConfirmPassReturnBL.submitData(mInputData, "") == false) {
				// @@错误处理
				CError.buildErr(this, "案件回退抵消理赔金失败,"+tLLClaimConfirmPassReturnBL.mErrors.getLastError());
				mResult.clear();
				return false;
			} else {
				mResult = null;
				mResult = tLLClaimConfirmPassReturnBL.getResult();
			}
		}
		
		return true;			
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData2(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		LLClaimConfirmPassReturnBL tLLClaimConfirmPassReturnBL = new LLClaimConfirmPassReturnBL();
		logger.debug("----------UI BEGIN----------");
		/**
		 * 案件回退是要冲销赔案要抵消赔金
		 */
		if (tLLClaimConfirmPassReturnBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			CError.buildErr(this, "案件回退抵消理赔金失败,"+tLLClaimConfirmPassReturnBL.mErrors.getLastError());
			mResult.clear();
			return false;
		} else {
			mResult = null;
			mResult = tLLClaimConfirmPassReturnBL.getResult();
		}

		// mInputData = null;
		return true;
	}

	public boolean submitData1(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		LLCaseBackBL tLLCaseBackBL = new LLCaseBackBL();
		logger.debug("----------UI BEGIN----------");

		/**
		 * 案件回退保存回退原因，并生成新的赔案号
		 */
		if (tLLCaseBackBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			CError.buildErr(this, "保存回退信息失败,"+tLLCaseBackBL.mErrors.getLastError());
			mResult.clear();
			return false;
		} else {
			mResult = null;
			mResult = tLLCaseBackBL.getResult();
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
