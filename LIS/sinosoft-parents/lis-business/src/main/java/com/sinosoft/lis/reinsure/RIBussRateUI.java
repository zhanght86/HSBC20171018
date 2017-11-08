

/**
 * <p>ClassName: RIBussRateUI.java </p>
 * <p>Description: 费率维护 </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Sinosoft </p>
 * @Database:
 * @CreateDate：2011/6/17
 */
package com.sinosoft.lis.reinsure;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RIBussRateUI implements BusinessService{

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量

	public RIBussRateUI() {

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		RIBussRateBL tRIBussRateBL = new RIBussRateBL();
		tRIBussRateBL.submitData(cInputData, mOperate);
		// 如果有需要处理的错误，则返回
		if (tRIBussRateBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tRIBussRateBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBussRateUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}
}
