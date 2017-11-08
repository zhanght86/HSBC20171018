package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:团单整单删除UI层
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
public class GroupContDeleteUI implements BusinessService{
private static Logger logger = Logger.getLogger(GroupContDeleteUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GroupContDeleteUI() {
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

		GroupContDeleteBL tGroupContDeleteBL = new GroupContDeleteBL();

		if (tGroupContDeleteBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGroupContDeleteBL.mErrors);

			return false;
		} else {
			mResult = tGroupContDeleteBL.getResult();
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
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema(); // 集体保单
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";

		tLCGrpContSchema.setGrpContNo("120110000000015");
		tLCGrpContSchema.setManageCom("86110000");

		VData tVData = new VData();
		tVData.add(tLCGrpContSchema);
		tVData.add(mGlobalInput);

		GroupContDeleteUI tgrlbl = new GroupContDeleteUI();
		tgrlbl.submitData(tVData, "DELETE");

		if (tgrlbl.mErrors.needDealError()) {
			logger.debug(tgrlbl.mErrors.getFirstError());
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
