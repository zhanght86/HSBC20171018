package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔清单打印：事故赔案对应清单---------.vts
 * </p>
 * <p>
 * Description: 统计界面：统计时间段、统计机构（某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）
 * 表头：报表名称、统计条件、统计日期 数据项：事故号、出险人、意外事故发生日期和出险日期、赔案号、赔案状态、案件类型、金额
 * 算法：在选定的机构范围内，以意外事故发生日期或出险日期统计在一定的时间段内的事故及其相关赔案 排序：事故号、赔案号 表尾：统计事故和赔案数量
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

public class LLPRBAccidentToCaseUI {
private static Logger logger = Logger.getLogger(LLPRBAccidentToCaseUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	// private String mOperate; /** 数据操作字符串 */

	public LLPRBAccidentToCaseUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRBAccidentToCaseBL tLLPRBAccidentToCaseBL = new LLPRBAccidentToCaseBL();

		if (tLLPRBAccidentToCaseBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRBAccidentToCaseBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBAccidentToCaseBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印“差错明细清单”失败！";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRBAccidentToCaseBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}
