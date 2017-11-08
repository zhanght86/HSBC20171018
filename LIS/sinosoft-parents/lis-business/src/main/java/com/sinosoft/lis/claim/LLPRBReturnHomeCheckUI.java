package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔清单打印： 归档核对清单
 * </p>
 * <p>
 * Description: 统计界面：统计机构（某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、立案起止日期
 * 数据项：扫描批次号、赔案号、立案日期、出险人、保单号、审核人 算法：在选择的机构内、指定的起止日期内立案的案件 排序：立案时间、赔案号、保单号
 * 输出到Excel
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author,niuzj,2005-11-07
 * @version 1.0
 */

public class LLPRBReturnHomeCheckUI {
private static Logger logger = Logger.getLogger(LLPRBReturnHomeCheckUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	// private String mOperate; /** 数据操作字符串 */

	public LLPRBReturnHomeCheckUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRBReturnHomeCheckBL tLLPRBReturnHomeCheckBL = new LLPRBReturnHomeCheckBL();

		if (tLLPRBReturnHomeCheckBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRBReturnHomeCheckBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBReturnHomeCheckBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印归档核对清单失败！";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRBReturnHomeCheckBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}
