

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RIItemCalUI implements BusinessService {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private GlobalInput globalInput = new GlobalInput();

	public RIItemCalUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		RIItemCalBL tRIItemCalBL = new RIItemCalBL();
		if (!tRIItemCalBL.submitData(cInputData, mOperate)) {
			if (tRIItemCalBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tRIItemCalBL.mErrors);
			} else {
				buildError("submitData", "RIItemCalUI发生错误，但是没有提供详细信息！");
			}
			return false;
		}
		mResult = tRIItemCalBL.getResult();
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIItemCalUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	public static void main(String[] args) {
	}
}
