package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PEdorPolManuUWUI implements BusinessService{
	private static Logger logger = Logger.getLogger(PEdorPolManuUWUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	
	public PEdorPolManuUWUI(){
		
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
		PEdorPolManuUWBL tPEdorPolManuUWBL = new PEdorPolManuUWBL();
		logger.debug("---tPEdorPolManuUWBL BEGIN---");
		if (tPEdorPolManuUWBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorPolManuUWBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPolManuUWUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			this.mErrors.copyAllErrors(tPEdorPolManuUWBL.mErrors);
		}
		return true;
	}

}
