package com.sinosoft.lis.claim;
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
 * 
 * <p>
 * Description: 单证打印：PCT020-----理赔查勘费用调查单------LpchkfyBxdC000230.vts
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author ccc
 * @version 1.0
 */
public class LLPRTInteInqPayUI {
private static Logger logger = Logger.getLogger(LLPRTInteInqPayUI.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LLPRTInteInqPayUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRTInteInqPayBL tLLPRTInteInqPayBL = new LLPRTInteInqPayBL();
		if (tLLPRTInteInqPayBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			mErrors.copyAllErrors(tLLPRTInteInqPayBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRTInteInqPayBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRTInteInqPayBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		/** ----------------- 赔案号 、出险人客户号、调查序号 由外部传入------------- */
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ComCode = "86";
		tGI.ManageCom = "86";

		String transact = "SinglePrt||Print";
		// String tPrtCode=tLLParaPrintSchema.getPrtCode();
		// String使用TransferData打包后提交-----用于传送 赔案号、客户号
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", "90000014662"); // 赔案号
		tTransferData.setNameAndValue("CustNo", "000433541"); // 客户号
		tTransferData.setNameAndValue("InqNo", "0000010663"); // 调查序号
		tTransferData.setNameAndValue("Payee", "李玉林");// 查勘人
		try {
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);

			LLPRTInteInqPayUI tLLPRTInteInqPayUI = new LLPRTInteInqPayUI();
			if (!tLLPRTInteInqPayUI.submitData(tVData, "")) {
				Content = "提交失败，原因是: "
						+ tLLPRTInteInqPayUI.mErrors.getError(0).errorMessage;
				FlagStr = "Fail";
			} else {
				Content = "数据提交成功";
				FlagStr = "Succ";
			}

		} catch (Exception ex) {
			Content = "数据提交失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}

	}

}
