package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCUWMasterSchema;
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
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class ManuUWRiskSaveUI {
private static Logger logger = Logger.getLogger(ManuUWRiskSaveUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;

	public ManuUWRiskSaveUI() {
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
		this.mOperate = cOperate;

		ManuUWRiskSaveBL tManuUWRiskSaveBL = new ManuUWRiskSaveBL();

		logger.debug("--------DisDesb Start!---------");
		tManuUWRiskSaveBL.submitData(cInputData, cOperate);
		logger.debug("--------DisDesb End!---------");

		// 如果有需要处理的错误，则返回
		if (tManuUWRiskSaveBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tManuUWRiskSaveBL.mErrors);
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		mInputData = null;

		return true;
	}

	/**
	 * 返回结果方法
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput mGlobalInput = new GlobalInput();

		/** 全局变量 */
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "86";
		mGlobalInput.ManageCom = "86";

		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		tLCUWMasterSchema.setProposalNo("110110000020073");
		tLCUWMasterSchema.setPolNo("110110000020073");
		tLCUWMasterSchema.setPassFlag("E");
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("AddPrem", "200");
		mTransferData.setNameAndValue("SpecReason", "adfsdfad");
		tVData.add(mGlobalInput);
		tVData.add(tLCUWMasterSchema);
		tVData.add(mTransferData);
		ManuUWRiskSaveUI tManuUWRiskSaveUI = new ManuUWRiskSaveUI();
		try {
			if (tManuUWRiskSaveUI.submitData(tVData, "")) {

			} else {
				logger.debug("error:"
						+ tManuUWRiskSaveUI.mErrors.getError(0).errorMessage);
			}
		} catch (Exception e) {
			logger.debug("error:" + e);
		}

	}

}
