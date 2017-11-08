package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔清单打印： 应付和实付清单
 * </p>
 * <p>
 * Description:
 * 统计界面：统计机构（总公司、某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、应付起止日期、实付起止日期、清单类型（实付清单、应付未付清单）、付费方式（EFT、网上支付、现金等方式、全部方式）
 * 表头：报表名称、统计条件、打印日期
 * 数据项：实付号、赔案号、领款人姓名和身份证、银行、账号、金额、支付方式、审批通过日期、实付日期、审核人代码和姓名、审核结论、案件标识（如出险人死亡需要标识）、受托人、受托人联系电话、受托人区部组（受托人为业务员时）
 * 算法：选定条件统计（应付未付统计为应付期间应付且到实付止期未付的数据） 排序：赔案号、实付号 表尾：统计数量和汇总金额 能输出到Excel
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj,2005-11-15
 * @version 1.0
 */

public class LLPRBDealORFactPayUI {
private static Logger logger = Logger.getLogger(LLPRBDealORFactPayUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	// private String mOperate; /** 数据操作字符串 */

	public LLPRBDealORFactPayUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRBDealORFactPayBL tLLPRBDealORFactPayBL = new LLPRBDealORFactPayBL();

		if (tLLPRBDealORFactPayBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRBDealORFactPayBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBDealORFactPayBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印应付和实付清单失败！";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRBDealORFactPayBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}
