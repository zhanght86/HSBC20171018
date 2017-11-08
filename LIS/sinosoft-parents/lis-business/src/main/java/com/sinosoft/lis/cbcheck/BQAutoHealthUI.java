package com.sinosoft.lis.cbcheck;

import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class BQAutoHealthUI implements BusinessService{
private static Logger logger = Logger.getLogger(BQAutoHealthUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	
	public BQAutoHealthUI(){
		
	}
	@Override
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	@Override
	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}

	@Override
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		BQAutoHealthBL tBQAutoHealthBL = new BQAutoHealthBL();
		logger.debug("---tBQAutoHealthBL BEGIN---");
		if (tBQAutoHealthBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tBQAutoHealthBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "BQAutoHealthUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			this.mErrors.copyAllErrors(tBQAutoHealthBL.mErrors);
		}
		return true;
	}

}
