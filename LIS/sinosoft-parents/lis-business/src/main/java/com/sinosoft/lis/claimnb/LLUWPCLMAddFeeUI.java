package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 打印补费通知书 --LLUWPCLMAddFeeBL.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj,2006-01-24
 * @version 1.0
 */

public class LLUWPCLMAddFeeUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLUWPCLMAddFeeUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private TransferData mTransferData = new TransferData();
	private String mClmNo = ""; // 赔案号
	private String mWebPath = ""; // 模板路径
	private String mNoticeNo = ""; // 通知书号
	private String mContNo = ""; // 合同号
	private String mDoutyCode = ""; // 责任编码
	private String mPayPlanCode = ""; // 交费计划编码
	private String mPolNO = ""; // 保单号

	public LLUWPCLMAddFeeUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		LLUWPCLMAddFeeBL tLLUWPCLMAddFeeBL = new LLUWPCLMAddFeeBL();
		if (tLLUWPCLMAddFeeBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLUWPCLMAddFeeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLUWPCLMAddFeeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLUWPCLMAddFeeBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mNoticeNo = (String) mTransferData.getValueByName("NoticeNo");
		this.mClmNo = (String) mTransferData.getValueByName("ClmNo");
		this.mContNo = (String) mTransferData.getValueByName("ContNo");
		this.mDoutyCode = (String) mTransferData.getValueByName("DoutyCode");
		this.mPayPlanCode = (String) mTransferData
				.getValueByName("PayPlayCode");
		this.mPolNO = (String) mTransferData.getValueByName("PolNo");

		logger.debug("######------合同号------" + this.mContNo);
		logger.debug("######------通知书号------" + this.mNoticeNo);
		logger.debug("######------赔案号------" + this.mClmNo);
		logger.debug("######------责任编码------" + this.mDoutyCode);
		logger.debug("######------交费计划编码------" + this.mPayPlanCode);
		logger.debug("######------保单号------" + this.mPolNO);

		return true;
	}

	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", "90000016053");
		tTransferData.setNameAndValue("NoticeNo", "CLMUW0000000005");
		VData tVData = new VData();
		tVData.add(tTransferData);

		LLUWPCLMAddFeeUI tLLUWPCLMAddFeeUI = new LLUWPCLMAddFeeUI();
		tLLUWPCLMAddFeeUI.submitData(tVData, "");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
