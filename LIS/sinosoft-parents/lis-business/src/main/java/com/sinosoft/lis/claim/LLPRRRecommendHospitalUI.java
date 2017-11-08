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
 * Description: 定点医院归类统计表打印
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计时间段（医院录入日期）、定点医院类别选项（单个类别、全部类别） 表头：报表名称、统计条件、统计日期
 * 数据项：机构、类别代码、类别名称、定点医院数量和数量占比 算法：按照选择的条件统计各项数据 排序：机构 表尾：各项数据的合计
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

public class LLPRRRecommendHospitalUI {
private static Logger logger = Logger.getLogger(LLPRRRecommendHospitalUI.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private TransferData mTransferData = new TransferData();
	String StartDate = "";
	String EndDate = "";
	String Level = "";
	String ManageCom = "";
	String HosAtti = ""; // 医院等级

	public LLPRRRecommendHospitalUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		LLPRRRecommendHospitalBL tLLPRRRecommendHospitalBL = new LLPRRRecommendHospitalBL();

		if (tLLPRRRecommendHospitalBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRRRecommendHospitalBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRRRecommendHospital";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRRRecommendHospitalBL.getResult();
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
		this.Level = (String) mTransferData.getValueByName("Level");
		this.ManageCom = (String) mTransferData.getValueByName("ManageCom");
		this.HosAtti = (String) mTransferData.getValueByName("HosAtti"); // 医院等级

		return true;
	}

	public static void main(String[] args) {
	}
}
