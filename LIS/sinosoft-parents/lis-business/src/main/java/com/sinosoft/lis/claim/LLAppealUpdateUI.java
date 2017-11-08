/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 申请申诉信息更新提交信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */
public class LLAppealUpdateUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLAppealUpdateUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public LLAppealUpdateUI() {
	}

	public static void main(String[] args) {

		GlobalInput tGI = new GlobalInput();
		LLAppealUpdateUI tLLAppealUpdateUI = new LLAppealUpdateUI();

		tGI.ManageCom = "86";
		tGI.Operator = "001";

		// 打包数据
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("ClaimNo", "90000006020");

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(mTransferData);

		tLLAppealUpdateUI.submitData(tVData, "");
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

		LLAppealUpdateBL tLLAppealUpdateBL = new LLAppealUpdateBL();

		logger.debug("----------UI BEGIN----------");
		if (tLLAppealUpdateBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLAppealUpdateBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLAppealUpdateUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tLLAppealUpdateBL.getResult();
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
