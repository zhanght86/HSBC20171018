package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 万能险账户信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zy
 * @version 1.1
 */
public class OtoFAccUI {
private static Logger logger = Logger.getLogger(OtoFAccUI.class);

	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public OtoFAccUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("--- OtoFUI Begin ---");
		try {
			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData))
				return false;

			// 准备传往后台的数据
			OtoFAccBL tOtoFAccBL = new OtoFAccBL();
			if (!tOtoFAccBL.submitData(cInputData, cOperate)) {
				if (tOtoFAccBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tOtoFAccBL.mErrors);
					return false;
				} else {
					buildError("sbumitData", "OtoFAccBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				this.mResult.clear();
				this.mResult = tOtoFAccBL.getResult();
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submit", "发生异常");
			return false;
		}
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "OtoFAccUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
