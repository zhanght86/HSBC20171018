/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
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
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class Zhixiao5UI {
private static Logger logger = Logger.getLogger(Zhixiao5UI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// private LOPRTManagerSchema mLOPRTManagerSchema = new
	// LOPRTManagerSchema();
	private TransferData mTransferData = new TransferData();

	public Zhixiao5UI() {
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
	public boolean submitData(VData cInputData, String cOperate) { // logger.debug("dfjdlfjdfd");
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
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
		// 准备传往后台的数据
		VData vData = new VData();
		if (!prepareOutputData(vData)) {
			return false;
		}
		Zhixiao5BL tZhixiao5BL = new Zhixiao5BL();
		logger.debug("Start Zhixiao5BL Submit ...");
		if (!tZhixiao5BL.submitData(vData, cOperate)) {
			if (tZhixiao5BL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tZhixiao5BL.mErrors);
				return false;
			} else {
				buildError("submitData", "Zhixiao5BL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tZhixiao5BL.getResult();
			return true;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mGlobalInput);
			vData.add(mTransferData);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));
		if (mTransferData == null) {
			buildError("getInputData", "mTransferData不对！");
			return false;
		}
		return true;
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
		cError.moduleName = "tZhixiao5UI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 130110000014113 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		Zhixiao5UI tZhixiao5UI = new Zhixiao5UI();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2006-01-01");
		tTransferData.setNameAndValue("EndDate", "2006-01-13");
		tTransferData.setNameAndValue("ManageCom", "862100");
		tTransferData.setNameAndValue("Salechnl", "1");

		VData tVData = new VData();

		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "865101";
		tG.Operator = "001";
		tVData.addElement(tG);
		tVData.addElement(tTransferData);
		tZhixiao5UI.submitData(tVData, "PRINT");
	}
}
