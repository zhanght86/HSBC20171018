package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保险计划变更附加险中止退费处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class ChangePlanSubWithdrawUI {
private static Logger logger = Logger.getLogger(ChangePlanSubWithdrawUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public ChangePlanSubWithdrawUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---ChangePlanSubWithdraw BL BEGIN---");
		ChangePlanSubWithdrawBL tChangePlanSubWithdrawBL = new ChangePlanSubWithdrawBL();

		if (tChangePlanSubWithdrawBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tChangePlanSubWithdrawBL.mErrors);
			mResult.clear();
			return false;
		} else {
			mResult = tChangePlanSubWithdrawBL.getResult();
		}
		logger.debug("---ChangePlanSubWithdraw BL END---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 主方法，测试用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ChangePlanSubWithdrawUI changePlanSubWithdrawUI1 = new ChangePlanSubWithdrawUI();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";

		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setRiskCode("121801");
		tLJTempFeeSchema.setOtherNo("2003041701");
		// 借助TempFeeNo来传递保单号
		tLJTempFeeSchema.setTempFeeNo("86110020030110003986");

		VData tVData = new VData();
		tVData.add(tLJTempFeeSchema);
		tVData.add(tGlobalInput);

		if (!changePlanSubWithdrawUI1.submitData(tVData, "INSERT||MAIN")) {
			logger.debug("Submit Failed! "
							+ changePlanSubWithdrawUI1.mErrors.getError(0).errorMessage);
		} else {
			logger.debug("Submit Succed!");
		}
	}
}
