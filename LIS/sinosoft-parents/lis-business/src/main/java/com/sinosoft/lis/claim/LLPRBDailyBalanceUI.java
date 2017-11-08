package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔清单打印：理赔日结清单
 * </p>
 * <p>
 * Description:财务科目就是理赔日结类型
 * 统计界面：统计机构（总公司、某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、审批通过起止日期、
 * 财务科目选项（单个科目、全部科目）、排序选项（审批通过日期＋赔案号、险种号＋赔案号、科目＋险种＋赔案号） 表头：报表名称、统计条件、统计日期
 * 数据项：赔案号、保单号、出险人、给付险种简称和代码、审核结论、意外事故发生日期和出险日期、审批通过日期、财务科目、
 * 项目名称、给付金额、领款人和身份证、案件标识（如出险人死亡需要标识）、支付方式（现金、银行代付、支票、网银等）. 审核人代码,审核人姓名,实付日期
 * 算法：在选择的机构内、指定的起止日期审批确认的案件。（包括所有审核结论的赔案） 排序：选定的排序 表尾：统计数量和汇总金额 能输出到Excel
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj,2005-11-09
 * @version 1.0
 */

public class LLPRBDailyBalanceUI {
private static Logger logger = Logger.getLogger(LLPRBDailyBalanceUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	// private String mOperate; /** 数据操作字符串 */

	public LLPRBDailyBalanceUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRBDailyBalanceBL tLLPRBDailyBalanceBL = new LLPRBDailyBalanceBL();

		if (tLLPRBDailyBalanceBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRBDailyBalanceBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBDailyBalanceBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印理赔日结清单失败！";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRBDailyBalanceBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}
