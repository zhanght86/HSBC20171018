package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: LLUWPBXUBUI.java
 * </p>
 * 
 * <p>
 * Description: 二核不续保通知书打印类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wanzh 2005/12/12
 * @version 1.0
 */
public class LLUWPBXBUI {
private static Logger logger = Logger.getLogger(LLUWPBXBUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public LLUWPBXBUI() {
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

		LLUWPBXBBL tLLUWPBXBBL = new LLUWPBXBBL();
		logger.debug("Start LLUWPBXBUI UI Submit ...");
		if (!tLLUWPBXBBL.submitData(cInputData, cOperate)) {
			if (tLLUWPBXBBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLLUWPBXBBL.mErrors);
				return false;
			} else {
				buildError("submitData", "LLUWPBXBBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tLLUWPBXBBL.getResult();
			return true;
		}
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
		cError.moduleName = "LLUWPBXBUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		LLUWPBXBUI tLLUWPBXBUI = new LLUWPBXBUI();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8100000251839");
		VData tVData = new VData();
		tVData.addElement(tLOPRTManagerSchema);
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86110000";
		tG.Operator = "001";
		tVData.addElement(tG);
		tLLUWPBXBUI.submitData(tVData, "PRINT");
	}
}
