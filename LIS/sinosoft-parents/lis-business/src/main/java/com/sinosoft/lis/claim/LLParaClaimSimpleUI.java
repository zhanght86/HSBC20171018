/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLParaClaimSimpleSchema;
import com.sinosoft.lis.vschema.LLParaClaimSimpleSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLParaClaimSimpleUI {
private static Logger logger = Logger.getLogger(LLParaClaimSimpleUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LLParaClaimSimpleUI() {
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
		// 首先将传入的数据和操作符号在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		LLParaClaimSimpleBL tLLParaClaimSimpleBL = new LLParaClaimSimpleBL();

		logger.debug("----------LLParaClaimSimpleUI Begin----------");
		if (tLLParaClaimSimpleBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLParaClaimSimpleBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLParaClaimSimpleBLUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tLLParaClaimSimpleBL.getResult();
		}
		logger.debug("----------LLParaClaimSimpleUI End----------");

		mInputData = null;
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86";
		tGI.ComCode = "01";

		String tOperate = "Simple||INSERT";

		LLParaClaimSimpleSet tLLParaClaimSimpleSet = new LLParaClaimSimpleSet();
		LLParaClaimSimpleSchema tLLParaClaimSimpleSchema = new LLParaClaimSimpleSchema();
		// 准备后台数据
		tLLParaClaimSimpleSchema.setComCode("C2");
		tLLParaClaimSimpleSchema.setComCodeName("C2医疗");
		tLLParaClaimSimpleSchema.setBaseMin(0);
		tLLParaClaimSimpleSchema.setBaseMax(5000);
		tLLParaClaimSimpleSchema.setStartDate("2005-06-22");
		tLLParaClaimSimpleSchema.setEndDate("2005-06-30");

		tLLParaClaimSimpleSet.add(tLLParaClaimSimpleSchema);

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Operate", tOperate);

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLLParaClaimSimpleSchema);

		tVData.add(tTransferData);

		LLParaClaimSimpleUI tLLParaClaimSimpleUI = new LLParaClaimSimpleUI();
		if (tLLParaClaimSimpleUI.submitData(tVData, tOperate) == false) {
		}

	}
}
