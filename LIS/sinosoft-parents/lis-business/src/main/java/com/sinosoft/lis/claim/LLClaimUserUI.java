/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交理赔用户管理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author:quyang
 * @version 1.0
 */
public class LLClaimUserUI {
private static Logger logger = Logger.getLogger(LLClaimUserUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */
	public LLClaimUserUI() {
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
		// 首先将传入的数据和操作符号在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		LLClaimUserBL tLLClaimUserBL = new LLClaimUserBL();
		logger.debug("----------LLClaimUserUI Begin----------");
		
		if (tLLClaimUserBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,原因是:"+tLLClaimUserBL.mErrors.getLastError());
			mResult.clear();
			return false;
		} else {
			mResult = tLLClaimUserBL.getResult();
		}
		logger.debug("----------LLClaimUserUI End----------");

		mInputData = null;
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		// tGI.ManageCom = "86110000";
		tGI.ManageCom = "86";
		tGI.ComCode = "86";
		// String tOperate = "User||UPDATE";
		String transact = "User||DELETE";
		LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
		LLClaimUserUI tLLClaimUserUI = new LLClaimUserUI();
		// 准备后台数据
		tLLClaimUserSchema.setUserCode("VVV");//
		tLLClaimUserSchema.setUserName("VVV");//
		tLLClaimUserSchema.setComCode("8611");//
		tLLClaimUserSchema.setReportFlag("1");// 报案权限
		tLLClaimUserSchema.setRegisterFlag("1");// 立案权限
		tLLClaimUserSchema.setCheckFlag("1");// 审核权限
		tLLClaimUserSchema.setUWFlag("1");// 审批权限
		tLLClaimUserSchema.setSubmitFlag("0");// 呈报权限
		tLLClaimUserSchema.setSurveyFlag("0");// 调查权限
		tLLClaimUserSchema.setPrepayFlag("0");// 预付权限
		tLLClaimUserSchema.setSimpleFlag("0");// 简易案件权限
		tLLClaimUserSchema.setCheckLevel("A01");// 审核级别
		tLLClaimUserSchema.setUWLevel("B01");// 审批级别
		tLLClaimUserSchema.setStateFlag("1");// 有效标识

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLLClaimUserSchema);

		if (!tLLClaimUserUI.submitData(tVData, transact)) {
			Content = "提交LLClaimUserUI失败，原因是: "
					+ tLLClaimUserUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		} else {
			Content = "数据提交成功";
			FlagStr = "Succ";
		}

	}

}
