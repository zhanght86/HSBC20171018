package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 赔案状态汇总报表打印
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、 机构统计范围选项（总公司、某分公司、某中支）、统计时间段
 * 表头：报表名称、统计条件、统计日期 数据项：机构（或用户）、报案件数、立案件数、审核件数、审批件数、调查件数、结案的案件数量、
 * 结案的案件数量、给付件数、关闭件数（包括不予立案、客户撤案、公司撤案等）、案件总数
 * 算法：按照选择的条件下分别统计对应的报案日期、立案日期等在统计时间段的数据 排序：机构（或用户） 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhaorx,2005-9-22 10:08
 * @version 1.0
 */

public class LLPRRCaseStateUI {
private static Logger logger = Logger.getLogger(LLPRRCaseStateUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private TransferData mTransferData = new TransferData();
	private String StartDate = "";
	private String EndDate = "";
	// String SaleType="";
	// String RiskCode="";
	private String Level = "";
	private String ManageCom = "";
	private String mNCLType = "";// 申请类型

	public LLPRRCaseStateUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		LLPRRCaseStateBL tLLPRRCaseStateBL = new LLPRRCaseStateBL();

		if (tLLPRRCaseStateBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRRCaseStateBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRRCaseState";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRRCaseStateBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.StartDate = (String) mTransferData.getValueByName("StartDate");
		this.EndDate = (String) mTransferData.getValueByName("EndDate");
		// this.SaleType = (String) mTransferData.getValueByName("SaleType");
		// this.RiskCode = (String) mTransferData.getValueByName("RiskCode");
		this.Level = (String) mTransferData.getValueByName("Level");
		this.ManageCom = (String) mTransferData.getValueByName("ManageCom");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType");

		return true;
	}

	public static void main(String[] args) {

	}
}
