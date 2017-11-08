package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 修改生效日期
 * </p>
 * <p>
 * Description: 在人工核保时，修改保单生效日期
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class UWSpecInputUI {
private static Logger logger = Logger.getLogger(UWSpecInputUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */

	private String mManageCom;
	private String mOperate;

	public UWSpecInputUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		VData vData = new VData();
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("cOperate:" + cOperate);

		UWSpecInputBL tUWSpecInputBL = new UWSpecInputBL();
		if (!tUWSpecInputBL.submitData(mInputData, cOperate)) {
			if (tUWSpecInputBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tUWSpecInputBL.mErrors);
				return false;
			} else {
				buildError("submitData", "UWSpecInputBI发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tUWSpecInputBL.getResult();
			return true;
		}

	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
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

	public static void main(String arg[]) {
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setContNo("20051013036202");
		tLCContSchema.setCValiDate("2010-1-10");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86110000";

		VData tVData = new VData();

		tVData.addElement(tG);
		tVData.addElement(tLCContSchema);
		ChangeCvalidateUI tChangeCvalidateUI = new ChangeCvalidateUI();
		tChangeCvalidateUI.submitData(tVData, "");

	}

}
