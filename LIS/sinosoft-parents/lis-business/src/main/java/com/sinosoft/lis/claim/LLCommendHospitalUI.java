/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLCommendHospitalSchema;
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
public class LLCommendHospitalUI {
private static Logger logger = Logger.getLogger(LLCommendHospitalUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */
	public LLCommendHospitalUI() {
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

		LLCommendHospitalBL tLLCommendHospitalBL = new LLCommendHospitalBL();
		logger.debug("----------LLCommendHospitalUI Begin----------");
		if (tLLCommendHospitalBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLCommendHospitalBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLCommendHospitalBLUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tLLCommendHospitalBL.getResult();
		}
		logger.debug("----------LLCommendHospitalUI End----------");

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
		String transact = "Hospital||DELETE";
		LLCommendHospitalSchema tLLCommendHospitalSchema = new LLCommendHospitalSchema();
		LLCommendHospitalUI tLLCommendHospitalUI = new LLCommendHospitalUI();
		// 准备后台数据
		tLLCommendHospitalSchema.setConsultNo("999");// 咨询通知号码==医院代码
		tLLCommendHospitalSchema.setHospitalCode("999");// 医院代码
		tLLCommendHospitalSchema.setHospitalName("医院管理数据调试二");// 医院名称
		tLLCommendHospitalSchema.setHosAtti("12");// 医院等级
		tLLCommendHospitalSchema.setConFlag("0");// 定点标志
		tLLCommendHospitalSchema.setAppFlag("0");// 残疾鉴定资质标志
		tLLCommendHospitalSchema.setHosState("0");// 医院状态

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLLCommendHospitalSchema);

		if (!tLLCommendHospitalUI.submitData(tVData, transact)) {
			Content = "提交LLCommendHospitalUI失败，原因是: "
					+ tLLCommendHospitalUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		} else {
			Content = "数据提交成功";
			FlagStr = "Succ";
		}

	}

}
