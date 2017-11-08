package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔清单打印：预付未结案清单
 * </p>
 * <p>
 * Description: 统计界面：统计时间段、统计机构（某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）
 * 数据项：赔案号、出险人、意外事故发生日期和出险日期、报案人和联系方式、案件的状态、预付金额 算法：统计在选定条件下（以立案时间统计）的预付未结案件
 * 排序：赔案号
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

public class LLPRBNoEndCasePrepayUI {
private static Logger logger = Logger.getLogger(LLPRBNoEndCasePrepayUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	// private String mOperate; /** 数据操作字符串 */

	public LLPRBNoEndCasePrepayUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRBNoEndCasePrepayBL tLLPRBNoEndCasePrepayBL = new LLPRBNoEndCasePrepayBL();

		if (tLLPRBNoEndCasePrepayBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRBNoEndCasePrepayBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBNoEndCasePrepayBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印“差错明细清单”失败！";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRBNoEndCasePrepayBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}
