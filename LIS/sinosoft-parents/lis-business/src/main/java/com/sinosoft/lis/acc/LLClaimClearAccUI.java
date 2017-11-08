package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;
import com.sinosoft.service.BusinessService;

public class LLClaimClearAccUI implements BusinessService {
private static Logger logger = Logger.getLogger(LLClaimClearAccUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public LLClaimClearAccUI() {

	}

	public boolean submitData(VData cInputData, String operate) {
		LLClaimClearAccBL tLLClaimClearAccBL = new LLClaimClearAccBL();
		tLLClaimClearAccBL.submitData(cInputData, operate);
		if (tLLClaimClearAccBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLLClaimClearAccBL.mErrors);
			return false;
		}

		return true;
	}
	
	public VData getResult() {
		return mResult;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
