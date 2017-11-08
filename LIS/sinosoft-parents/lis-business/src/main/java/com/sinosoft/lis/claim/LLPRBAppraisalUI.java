/**
 * <p>Title: </p>
 * <p>Description: 理赔鉴定提示清单打印</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author niuzj 20050831
 * @version 1.0
 */

package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LLPRBAppraisalUI {
private static Logger logger = Logger.getLogger(LLPRBAppraisalUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public LLPRBAppraisalUI() // 构造函数
	{
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		LLPRBAppraisalBL tLLPRBAppraisalBL = new LLPRBAppraisalBL();

		if (!tLLPRBAppraisalBL.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRBAppraisalBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBAppraisalUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tLLPRBAppraisalBL.getResult();
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	// test
	public static void main(String[] args) {
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("ClmNo","90000002042");
		// tTransferData.setNameAndValue("CustNo","0000550940");
		// VData tVData = new VData();
		// tVData.add(tTransferData);
		//
		// LLPRTMedBillPerUI tLLPRTMedBillPerUI = new
		// LLPRTMedBillPerUI();
		//
		// tLLPRTMedBillPerUI.submitData(tVData, "");
	}

}
