package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:投保功能类（界面输入）
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

public class ChangePlanUI implements BusinessService{
private static Logger logger = Logger.getLogger(ChangePlanUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 保单 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	public ChangePlanUI() {
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
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("cOperate:" + cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("---End prepareOutputData---");

		logger.debug("Start ChangePlan BL Submit...");
		ChangePlanBL tChangePlanBL = new ChangePlanBL();
		if (tChangePlanBL.submitData(mInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tChangePlanBL.mErrors);
			mResult.clear();
			return false;
		} else {
			mResult = tChangePlanBL.getResult();
		}
		logger.debug("End ChangePlan BL Submit...");

		// 如果有需要处理的错误，则返回
		if (tChangePlanBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tChangePlanBL.mErrors);
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(mGlobalInput);
			mInputData.add(mLCPolSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		try {
			// 全局变量
			// mGlobalInput.setSchema((GlobalInput)mInputData.getObjectByObjectName("GlobalInput",
			// 0));
			// 保单
			mLCPolSchema.setSchema((LCPolSchema) mInputData
					.getObjectByObjectName("LCPolSchema", 0));
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

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

	public static void main(String[] args) {
		ChangePlanUI ChangePlanUI1 = new ChangePlanUI();
		ChangePlanUI tChangePlanUI = new ChangePlanUI();
		String tDay = "1977-03-07";

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "Admin";
		tGlobalInput.ComCode = "asd";
		tGlobalInput.ManageCom = "sdd";

		// 保单信息部分
		LCPolSchema tLCPolSchema = new LCPolSchema();

		// tLCPolSchema.setPolNo("00010120020110000031");
		logger.debug("111");

		// 准备传输数据 VData
		VData tVData = new VData();

		tVData.addElement(tGlobalInput);
		tVData.addElement(tLCPolSchema);

		// 数据传输
		tChangePlanUI = new ChangePlanUI();
		if (!tChangePlanUI.submitData(tVData, "QUERY||CHANGEPLAN")) {
			logger.debug(tChangePlanUI.mErrors.getFirstError());
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
