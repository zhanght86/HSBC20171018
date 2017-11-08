package com.sinosoft.productdef;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * 程序名称：累加器账单管理UI
 * 程序功能：累加器账单配置
 * 创建日期：2016-5-22
 * 创建人：王海超
 */
public class PDLDPlanFeeRelaUI implements BusinessService {
private static Logger logger = Logger.getLogger(PDLDPlanFeeRelaUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public CErrors getErrors() {
		return this.mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据提交方法
	 * 
	 * @param cInputData
	 *            传入的数据
	 * @param cOperate
	 *            数据操作字符串
	 * @return: boolean
	 */
	public boolean submitData(VData data, String Operater) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = Operater;
		PDLDPlanFeeRelaBL tPDLDPlanFeeRelaBL =new PDLDPlanFeeRelaBL();
		logger.debug("---PDLCalculatorUI BEGIN---");
		if (tPDLDPlanFeeRelaBL.submitData(data, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPDLDPlanFeeRelaBL.getErrors());
			return false;
		} else {
			mResult = tPDLDPlanFeeRelaBL.getResult();
		}
		logger.debug(mErrors.toString());
		
		return true;
	}

}
