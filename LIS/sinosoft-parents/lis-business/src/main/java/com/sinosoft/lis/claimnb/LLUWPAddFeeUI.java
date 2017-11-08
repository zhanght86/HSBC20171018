package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wanzh
 * @version 1.0
 */
public class LLUWPAddFeeUI {
private static Logger logger = Logger.getLogger(LLUWPAddFeeUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	public LLUWPAddFeeUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		LLUWPAddFeeBL tLLUWPAddFeeBL = new LLUWPAddFeeBL();
		logger.debug("Start LLUWPAddFee UI Submit ...");
		if (!tLLUWPAddFeeBL.submitData(cInputData, cOperate)) {
			if (tLLUWPAddFeeBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLLUWPAddFeeBL.mErrors);
				return false;
			} else {
				buildError("submitData", "LLUWPAddFeeBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tLLUWPAddFeeBL.getResult();
			return true;
		}
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "DerferAppF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		LLUWPAddFeeUI tLLUWPAddFeeUI = new LLUWPAddFeeUI();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8100000418248");
		VData tVData = new VData();
		tVData.addElement(tLOPRTManagerSchema);
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86510199";
		tG.Operator = "zkr";
		tVData.addElement(tG);
		tLLUWPAddFeeUI.submitData(tVData, "PRINT");
	}

}
