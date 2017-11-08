

package com.sinosoft.lis.reinsure.impdata;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.vschema.RIAccumulateDefSet;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 再保数据导入用户接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @ zhangbin
 * 
 * @version 1.0
 */

public class RIImptMngUI {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 返回前台的信息 */
	private VData mResult = new VData();

	public RIImptMngUI() {
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
		RIImptMngBL tRIImptMngBL = new RIImptMngBL();
		if (!tRIImptMngBL.submitData(cInputData, cOperate)) {
			this.mErrors.copyAllErrors(tRIImptMngBL.mErrors);
			return false;
		}
		mResult = tRIImptMngBL.getResult();
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

		RIImptMngUI tRIImptMngUI = new RIImptMngUI();
		VData cInputData = new VData();
		GlobalInput globalInput = new GlobalInput();
		globalInput.ManageCom = "8611";
		globalInput.Operator = "001";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EndDate", "2008-11-21");
		RIAccumulateDefSet cRIAccumulateDefSet = new RIAccumulateDefSet();
		RIAccumulateDefSchema cRIAccumulateDefSchema = new RIAccumulateDefSchema();
		cRIAccumulateDefSchema.setAccumulateDefName("ILBL");
		cRIAccumulateDefSchema.setAccumulateDefNO("L000000009");
		cRIAccumulateDefSet.add(cRIAccumulateDefSchema);

		cInputData.add(globalInput);
		cInputData.add(cRIAccumulateDefSet);
		cInputData.add(tTransferData);

		tRIImptMngUI.submitData(cInputData, "21");

	}

}
