package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLCUWMasterSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
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
 * Company:
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class LLUWResultCvalidateUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLUWResultCvalidateUI.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	public LLUWResultCvalidateUI() {
	}

	public static void main(String[] args) {
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		boolean flag = true;
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86110000";
		tG.ComCode = "86110000";

		// 接收信息
		LLCUWMasterSchema mLLCUWMasterSchema = new LLCUWMasterSchema();

		mLLCUWMasterSchema.setCaseNo("90000013999");
		mLLCUWMasterSchema.setContNo("NJA10426111000973");
		mLLCUWMasterSchema.setPassFlag("9");

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(mLLCUWMasterSchema);

		tVData.add(tG);

		// 数据传输
		LLUWResultCvalidateUI tLLUWResultCvalidateUI = new LLUWResultCvalidateUI();

		if (!tLLUWResultCvalidateUI.submitData(tVData, "")) {

			int n = tLLUWResultCvalidateUI.mErrors.getErrorCount();
			Content = " 发起二核失败，原因是: "
					+ tLLUWResultCvalidateUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}

	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中

		this.mOperate = cOperate;

		LLUWResultCvalidateBL tLLUWResultCvalidateBL = new LLUWResultCvalidateBL();

		logger.debug("----UI BEGIN---");
		if (tLLUWResultCvalidateBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLUWResultCvalidateBL.mErrors);
			return false;
		}
		logger.debug("----UI END---");
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}

}
