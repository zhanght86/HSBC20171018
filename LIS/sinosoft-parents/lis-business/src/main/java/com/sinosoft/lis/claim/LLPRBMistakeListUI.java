package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔清单打印：差错明细清单
 * </p>
 * <p>
 * Description: 统计界面：统计时间段、统计机构（某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、
 * 审批不通过原因选项（每个原因、所有原因） 表头：报表名称、统计条件、统计日期 数据项： 赔案号、接报案人、立案人、 审核人（同一审核流程中的最后审核人）、
 * 审批人（同一审批流程中的最后审批人）、 审批日期、审批不通过次数、审批不通过原因
 * 算法：统计在选定条件下审批不通过的案件（按照审批日期统计），同一不通过原因多次不通过按照一条记录处理 排序：赔案号、审批日期 表尾：统计数量
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

public class LLPRBMistakeListUI {
private static Logger logger = Logger.getLogger(LLPRBMistakeListUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	// private String mOperate; /** 数据操作字符串 */

	public LLPRBMistakeListUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRBMistakeListBL tLLPRBMistakeListBL = new LLPRBMistakeListBL();

		if (tLLPRBMistakeListBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRBMistakeListBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBMistakeListBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印“差错明细清单”失败！";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRBMistakeListBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}
