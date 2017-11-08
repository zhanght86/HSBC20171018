package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ccvip
 * @version 6.0
 */
public class UWQualityManageUI implements BusinessService{
private static Logger logger = Logger.getLogger(UWQualityManageUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public UWQualityManageUI() {
	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param cInputData
	 *            传入的数据
	 * @param cOperate
	 *            数据操作字符串
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		UWQualityManageBL tUWQualityManageBL = new UWQualityManageBL();

		logger.debug("---UI BEGIN---");
		if (tUWQualityManageBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWQualityManageBL.mErrors);
			return false;
		} else {
			mResult = tUWQualityManageBL.getResult();
		}
		logger.debug(mErrors.toString());
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		UWQualityManageUI tUWQualityManageUI = new UWQualityManageUI();
		LDPersonSchema mLDPersonSchema = new LDPersonSchema();
		mLDPersonSchema.setCustomerNo("");
		mLDPersonSchema.setBlacklistFlag("1");
		mLDPersonSchema.setRemark("dgofbngfgd");
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86110000";
		tGI.Operator = "001";
		VData tInputData = new VData();
		tInputData.add(mLDPersonSchema);
		tInputData.add(tGI);
		if (!tUWQualityManageUI.submitData(tInputData, "UPDATE")) {
			// logger.debug("UWQualityManageui=="+UWQualityManageUI.mErrors.getFirstError());
		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
