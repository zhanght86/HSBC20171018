package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
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
 * Description: 理赔回退收费通知书打印：-----理赔回退收费通知书打印------LiPeiNotice.vts
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
public class LLCaseBackNoticeUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLCaseBackNoticeUI.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LLCaseBackNoticeUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLCaseBackNoticeBL tLLCaseBackNoticeBL = new LLCaseBackNoticeBL();
		if (tLLCaseBackNoticeBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			mErrors.copyAllErrors(tLLCaseBackNoticeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLCaseBackNoticeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLCaseBackNoticeBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		/** ----------------- 赔案号 、回退原因、通知书号码由外部传入------------- */
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ComCode = "86";
		tGI.ManageCom = "86";

		String transact = "SinglePrt||Print";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNoQ", "90000013677"); // 赔案号
		tTransferData.setNameAndValue("BackReasonQ", "民事申诉调解"); // 回退原因
		try {
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);

			LLCaseBackNoticeUI tLLCaseBackNoticeUI = new LLCaseBackNoticeUI();
			if (!tLLCaseBackNoticeUI.submitData(tVData, "")) {
				Content = "提交失败，原因是: "
						+ tLLCaseBackNoticeUI.mErrors.getError(0).errorMessage;
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

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
