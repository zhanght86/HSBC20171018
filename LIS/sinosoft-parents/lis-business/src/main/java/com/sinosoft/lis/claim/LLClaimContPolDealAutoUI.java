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
 * Description: 合同终止处理的计算
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

public class LLClaimContPolDealAutoUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLClaimContPolDealAutoUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LLClaimContPolDealAutoUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLClaimContPolDealAutoBL tLLClaimContPolDealAutoBL = new LLClaimContPolDealAutoBL();

		if (tLLClaimContPolDealAutoBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,原因是"+tLLClaimContPolDealAutoBL.mErrors.getLastError());
			mInputData.clear();
			return false;
		} else {
			mResult = tLLClaimContPolDealAutoBL.getResult();
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 计算
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimCalFinalBL tLLClaimCalFinalBL = new LLClaimCalFinalBL();
		if (tLLClaimCalFinalBL.submitData(cInputData, "") == false) {
			mInputData.clear();
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
