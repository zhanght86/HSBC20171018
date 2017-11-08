package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class CashCheckUI implements PrintService {
private static Logger logger = Logger.getLogger(CashCheckUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData vData = new VData();
	private TransferData mTransferData = new TransferData();

	// private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	// private LOPRTManagerSchema mLOPRTManagerSchema = new
	// LOPRTManagerSchema();

	public CashCheckUI() {
	}

	public static void main(String[] args) { // String tAppntNo = "0000498010";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDay", "2004-07-01");
		tTransferData.setNameAndValue("EndDay", "2005-07-01");
		tTransferData.setNameAndValue("ManageCom", "86110000");
		GlobalInput g = new GlobalInput();
		VData tVData = new VData();
		// LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// LOPRTManagerSchema tLOPRTManagerSchema1 = new LOPRTManagerSchema();
		// LOPRTManagerSchema tLOPRTManagerSchema2 = new LOPRTManagerSchema();
		// LOPRTManagerSchema tLOPRTManagerSchema3 = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setPrtSeq("810110000000851");
		// tLOPRTManagerSchema1.setPrtSeq("810110000000866");
		// tLOPRTManagerSchema2.setPrtSeq("810110000000867");
		// tLOPRTManagerSchema3.setPrtSeq("810110000000851");

		// mLOPRTManagerSet.add(tLOPRTManagerSchema);
		// mLOPRTManagerSet.add(tLOPRTManagerSchema1);
		// mLOPRTManagerSet.add(tLOPRTManagerSchema2);
		// mLOPRTManagerSet.add(tLOPRTManagerSchema3);

		tVData.addElement(tTransferData);
		tVData.add(tG);
		CashCheckUI u = new CashCheckUI();
		u.submitData(tVData, "PRINT");
		VData result = new VData();
		result = u.getResult();

	}

	/**
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("cOperate:" + cOperate);
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
				&& !cOperate.equals("BATCHCONFIRM")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		CashCheckBL tCashCheckBL = new CashCheckBL();
		logger.debug("Start ConFeeF1P UI Submit ...");

		if (!tCashCheckBL.submitData(cInputData, "PRINT")) {
			logger.debug("真的不好意思，我没有得到任何的值");
			if (tCashCheckBL.mErrors.needDealError()) {
				logger.debug("你要调用我就好，我就可以把我的真挚给你");
				mErrors.copyAllErrors(tCashCheckBL.mErrors);
				return false;
			} else {
				logger.debug("倒霉的孩子，看来你是调用的我");
				buildError("submitData", "ConFeeF1PBL发生错误，但是没有提供详细的出错信息");
				return false;
			}

		} else {
			mResult = tCashCheckBL.getResult();
			return true;
		}

	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	// private boolean prepareOutputData(VData vData)
	// {
	// try
	// {
	// vData.clear();
	// vData.add(mGlobalInput);
	// vData.add(mTransferData);
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	// buildError("prepareOutputData", "发生异常");
	// return false;
	// }
	// return true;
	// }
	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	// private boolean dealData()
	// {
	// return true;
	// }
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// mLJSPaySchema.setSchema((LJSPaySchema)cInputData.getObjectByObjectName("LJSPaySchema",0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "ConFeeF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
