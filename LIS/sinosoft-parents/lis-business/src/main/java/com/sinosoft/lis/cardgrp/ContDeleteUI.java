package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:个单整单删除UI层
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author zhangrong
 * @version 1.0
 */
public class ContDeleteUI {
private static Logger logger = Logger.getLogger(ContDeleteUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public ContDeleteUI() {
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
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		ContDeleteBL tContDeleteBL = new ContDeleteBL();

		if (tContDeleteBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tContDeleteBL.mErrors);

			return false;
		} else {
			mResult = tContDeleteBL.getResult();
		}

		return true;
	}

	/**
	 * 获取从BL层取得的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] agrs) {
		LCContSchema tLCContSchema = new LCContSchema(); // 集体保单
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";

		tLCContSchema.setContNo("240000000001");
		tLCContSchema.setManageCom("86110000");

		VData tVData = new VData();
		tVData.add(tLCContSchema);
		tVData.add(mGlobalInput);

		ContDeleteUI tgrlbl = new ContDeleteUI();
		tgrlbl.submitData(tVData, "DELETE");

		if (tgrlbl.mErrors.needDealError()) {
			logger.debug(tgrlbl.mErrors.getFirstError());
		}
	}
}
