package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 查勘效用统计表打印
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计时间段（结案时间）、查勘原因选项（单个原因，所有原因） 表头：报表名称、统计条件、统计日期
 * 数据项：机构、赔案总数、整案拒付件数、查勘件数、查勘阳性件数、整体拒付率（拒付赔案件数/赔案总数）、
 * 查勘件拒付率（查勘件拒付赔案件数/查勘件赔案总数）、查勘率（查勘件数/赔案总数）、 查勘阳性率（查勘阳性件数/查勘件数） 算法：按照选择的条件统计各项数据
 * 排序：机构 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhaorx,2005-10-04 9:32
 * @version 1.0
 */

public class LLPRRInvestEffectUI {
private static Logger logger = Logger.getLogger(LLPRRInvestEffectUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LLPRRInvestEffectUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRRInvestEffectBL tLLPRRInvestEffectBL = new LLPRRInvestEffectBL();

		if (tLLPRRInvestEffectBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRRInvestEffectBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestEffect";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRRInvestEffectBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}
