package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:承保单整单删除UI层
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author chenrong
 * @version 1.0
 */
public class ProposalDeleteUI {
private static Logger logger = Logger.getLogger(ProposalDeleteUI.class);

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public ProposalDeleteUI() {

	}

	public static void main(String[] args) {
		ProposalDeleteUI tProposalDeleteUI = new ProposalDeleteUI();
		LCContSchema tLCContSchema = new LCContSchema(); // 集体保单
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ManageCom = "8621";
		mGlobalInput.Operator = "001";

		tLCContSchema.setContNo("BJ010522651011001");
		tLCContSchema.setAppFlag("1");

		VData tVData = new VData();
		tVData.add(tLCContSchema);
		tVData.add(mGlobalInput);

		if (!tProposalDeleteUI.submitData(tVData, "")) {
			logger.debug(tProposalDeleteUI.mErrors.getFirstError());
		}
	}

	/**
	 * 不执行任何操作，只传递数据给下一层
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		ProposalDeleteBL tProposalDeleteBL = new ProposalDeleteBL();

		if (!tProposalDeleteBL.submitData(cInputData, cOperate)) {
			// 错误处理
			this.mErrors.copyAllErrors(tProposalDeleteBL.mErrors);
			return false;
		} else {
			// 获取BL层的结果
			mResult = tProposalDeleteBL.getResult();
		}
		return true;
	}

	/**
	 * 返回从BL层得到的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

}
