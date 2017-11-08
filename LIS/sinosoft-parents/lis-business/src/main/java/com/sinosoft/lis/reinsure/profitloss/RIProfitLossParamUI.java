

package com.sinosoft.lis.reinsure.profitloss;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.TransferData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class RIProfitLossParamUI {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 返回前台的信息 */
	private VData mResult = new VData();

	public RIProfitLossParamUI() {
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		RIProfitLossParamBL tRIProfitLossParamBL = new RIProfitLossParamBL();
		if (!tRIProfitLossParamBL.submitData(cInputData, cOperate)) {
			this.mErrors.copyAllErrors(tRIProfitLossParamBL.mErrors);
			return false;
		}
		return true;
	}

	/**
	 * 获取后台返回的信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {

		RIProfitLossParamUI tRIImptMngUI = new RIProfitLossParamUI();
		VData cInputData = new VData();
		GlobalInput globalInput = new GlobalInput();
		globalInput.ManageCom = "8611";
		globalInput.Operator = "001";
		TransferData tTransferData = new TransferData();
		tRIImptMngUI.submitData(cInputData, "");
	}

}
