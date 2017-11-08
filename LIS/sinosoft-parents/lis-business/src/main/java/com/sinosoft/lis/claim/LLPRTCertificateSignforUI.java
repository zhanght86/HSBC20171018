package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 单证打印：索赔文件签收清单--PCT004 ,SpwjqsqdGrC000100.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author xutao,2005-07-14
 * @version 1.0
 */

public class LLPRTCertificateSignforUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLPRTCertificateSignforUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */

	public LLPRTCertificateSignforUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRTCertificateSignforBL tLLPRTCertificateSignforBL = new LLPRTCertificateSignforBL();

		if (tLLPRTCertificateSignforBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRTCertificateSignforBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimContDealBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRTCertificateSignforBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("ClmNo","90000006000");
		// tTransferData.setNameAndValue("CustNo","0000522620");
		// VData tVData = new VData();
		// tVData.add(tTransferData);
		//
		// LLPRTCertificateSignforUI tLLPRTCertificateSignforUI = new
		// LLPRTCertificateSignforUI();
		//
		// tLLPRTCertificateSignforUI.submitData(tVData, "");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
