package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class XZlisUI {
private static Logger logger = Logger.getLogger(XZlisUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public XZlisUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINT")) {
				CError.buildErr(this, "不支持的操作字符串!");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			// 进行业务处理
			if (!dealData()) {
				return false;
			}

			XZlisBL tXZlisBL = new XZlisBL();
			logger.debug("Start tXZlisBL Submit ...");
			if (!tXZlisBL.submitData(cInputData, cOperate)) {
				if (tXZlisBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tXZlisBL.mErrors);
					return false;
				} else {
					CError.buildErr(this, "XZlisBL，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tXZlisBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		}
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		if (tTransferData == null || mGlobalInput == null) {
			CError.buildErr(this, "传入后台的参数缺少！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
