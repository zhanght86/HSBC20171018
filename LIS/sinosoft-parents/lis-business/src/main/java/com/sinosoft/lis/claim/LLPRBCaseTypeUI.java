package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔清单打印：案件类型清单
 * </p>
 * <p>
 * Description: 统计界面：统计机构（某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、
 * 统计时间段、统计口径选项（立案时间、结案时间）、案件类型选项（简易案件、 普通案件、诉讼案件、申诉案件、疑难案件、全部类型） 表
 * 头：报表名称、统计条件、统计日期 数 据 项：赔案号、案件类型、出险人、意外事故发生日期和出险日期、
 * 报案人和联系方式、案件的状态、立案时间、结案日期、金额 排 序：案件类型、选定的统计口径选项、赔案号 表 尾：统计数量和金额
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author
 * @version 1.0
 */

public class LLPRBCaseTypeUI {
private static Logger logger = Logger.getLogger(LLPRBCaseTypeUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	// private String mOperate; /** 数据操作字符串 */

	public LLPRBCaseTypeUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRBCaseTypeBL tLLPRBCaseTypeBL = new LLPRBCaseTypeBL();

		if (tLLPRBCaseTypeBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRBCaseTypeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBCaseTypeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印“案件类型清单”失败！";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRBCaseTypeBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("ClmNo","90000002042");
		// tTransferData.setNameAndValue("CustNo","0000550940");
		// VData tVData = new VData();
		// tVData.add(tTransferData);
		//
		// LLPRBCaseTypeUI tLLPRBCaseTypeUI = new
		// LLPRBCaseTypeUI();
		//
		// tLLPRBCaseTypeUI.submitData(tVData, "");
	}
}
