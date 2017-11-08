package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 打印收据
 * </p>
 * 统计界面：统计机构，统计起期，统计止期
 * 
 * <p>
 * Copyright : Copyright (c) 2002
 * </p>
 * <p>
 * Company :
 * </p>
 * 
 * @author : wl,2006-10-24
 * @version : 1.0
 */

public class LJCertifyTempFeeChargeUI {
private static Logger logger = Logger.getLogger(LJCertifyTempFeeChargeUI.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private GlobalInput mG = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String mGetNoticeNo = ""; // 收费收据号
	private String mAgID = ""; // 证件号码

	public LJCertifyTempFeeChargeUI() {
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mGetNoticeNo = (String) mTransferData.getValueByName("GetNoticeNo"); // 收费收据号
		mAgID = (String) mTransferData.getValueByName("IDNo"); // 证件号码
		logger.debug("mAgID=" + mAgID);
		return true;
	}

	/*
	 * 校验检查外部传入的数据 @param cInputData VData @return boolean
	 */
	private boolean checkInputData() {
		if (mG == null) {
			CError tError = new CError();
			tError.moduleName = "LJCertifyTempFeeChargeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取用户登录信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		// if (mGetNoticeNo == null)
		// {
		// CError tError = new CError();
		// tError.moduleName = "LJCertifyTempFeeChargeBL";
		// tError.functionName = "checkInputData";
		// tError.errorMessage = "获取收费收据号信息失败!";
		// mErrors.addOneError(tError);
		// return false;
		// }
		// if (mAgID == null)
		// {
		// CError tError = new CError();
		// tError.moduleName = "LJCertifyTempFeeChargeBL";
		// tError.functionName = "checkInputData";
		// tError.errorMessage = "获取证件号码信息失败!";
		// mErrors.addOneError(tError);
		// return false;
		// }
		return true;
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验检查外部传入的数据
		if (!checkInputData()) {
			return false;
		}

		LJCertifyTempFeeChargeBL tLJCertifyTempFeeChargeBL = new LJCertifyTempFeeChargeBL();
		if (tLJCertifyTempFeeChargeBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJCertifyTempFeeChargeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "tLLCertifyDayCheckPBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLJCertifyTempFeeChargeBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
	}
}
