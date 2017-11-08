package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
public class LLUnHangUpUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLUnHangUpUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public LLUnHangUpUI() {
	}

	public static void main(String[] args) {

		// LCContSchema tLCContSchema = new LCContSchema(); //个人保单表
		//
		// GlobalInput tGI = new GlobalInput();
		// LLLcContSuspendUI tLLLcContSuspendUI = new LLLcContSuspendUI();
		//
		// String transact = "update";//获取操作insert||update
		// String isReportExist = "false";//是否为新增事件，是false，否true
		// tGI.ManageCom="86";
		// tGI.Operator="001";
		// //获取保单挂起页面信息
		// tLCContSchema.setContNo("130110000000753");//合同号
		// tLCContSchema.setState("保全挂起");//合同号
		//
		// VData tVData = new VData();
		// tVData.add(tGI);
		// tVData.add(isReportExist);
		// tVData.add(tLCContSchema);
		// tLLLcContSuspendUI.submitData(tVData,transact);
		//
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
		this.mOperate = cOperate;

		LLUnHangUpBL tLLUnHangUpBL = new LLUnHangUpBL();

		logger.debug("----------UI BEGIN----------");
		if (tLLUnHangUpBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLUnHangUpBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLLcContSuspendUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tLLUnHangUpBL.getResult();
		}
		logger.debug("----------UI---submitData--数据提交成功----------");
		mInputData = null;
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
