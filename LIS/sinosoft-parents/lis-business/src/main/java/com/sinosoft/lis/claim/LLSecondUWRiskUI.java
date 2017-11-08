package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLUWMasterSchema;
import com.sinosoft.utility.CErrors;
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
 * @author 张星
 * @version 1.0
 */

public class LLSecondUWRiskUI {
private static Logger logger = Logger.getLogger(LLSecondUWRiskUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;

	public LLSecondUWRiskUI() {
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
		LLSecondUWRiskBL tLLSecondUWRiskBL = new LLSecondUWRiskBL();
		logger.debug("-------- Start!---------");
		if (tLLSecondUWRiskBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLSecondUWRiskBL.mErrors);
			return false;
		}
		mInputData = null;
		logger.debug("-------- End!---------");
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
		LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();
		tLLUWMasterSchema.setCaseNo("90000006710");
		tLLUWMasterSchema.setBatNo("550000000000296");
		tLLUWMasterSchema.setPolNo("110110000029766");
		tLLUWMasterSchema.setPassFlag("9");
		tLLUWMasterSchema.setUWIdea("险种核保结论测试");
		tVData.add(mGlobalInput);
		tVData.add(tLLUWMasterSchema);
		LLSecondUWRiskBL tLLSecondUWRiskBL = new LLSecondUWRiskBL();
		try {
			if (tLLSecondUWRiskBL.submitData(tVData, "")) {

			} else {
				logger.debug("error:"
						+ tLLSecondUWRiskBL.mErrors.getError(0).errorMessage);
			}
		} catch (Exception e) {
			logger.debug("error:" + e);
		}

	}

}
