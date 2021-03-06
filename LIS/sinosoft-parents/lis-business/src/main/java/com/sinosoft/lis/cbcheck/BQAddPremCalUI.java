package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LMDutyPayAddFeeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class BQAddPremCalUI {
private static Logger logger = Logger.getLogger(BQAddPremCalUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */

	private String mManageCom;
	private String mOperate;

	public BQAddPremCalUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		VData vData = new VData();
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("cOperate:" + cOperate);

		BQAddPremCalBL tBQAddPremCalBL = new BQAddPremCalBL();
		if (!tBQAddPremCalBL.submitData(mInputData, cOperate)) {
			if (tBQAddPremCalBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tBQAddPremCalBL.mErrors);
				return false;
			} else {
				buildError("submitData", "BodyCheckPrintBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tBQAddPremCalBL.getResult();
			return true;
		}

	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "DerferAppF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String arg[]) {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setPolNo("110110000038005");
		tLCPolSchema.setInsuredNo("0000559920");
		tLCPolSchema.setRiskCode("00108002");
		LCPremSchema tLCPremSchema = new LCPremSchema();
		tLCPremSchema.setSuppRiskScore(100);
		LMDutyPayAddFeeSchema tLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
		tLMDutyPayAddFeeSchema.setAddFeeObject("00");
		tLMDutyPayAddFeeSchema.setAddFeeType("01");
		// tLMDutyPayAddFeeSchema.setRiskCode("00141000");
		LCDutySchema tLCDutySchema = new LCDutySchema();
		tLCDutySchema.setDutyCode("108201");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86110000";

		VData tVData = new VData();

		tVData.addElement(tG);
		tVData.addElement(tLCPolSchema);
		tVData.addElement(tLCPremSchema);
		tVData.addElement(tLMDutyPayAddFeeSchema);
		tVData.addElement(tLCDutySchema);
		AddPremCalUI tAddPremCalUI = new AddPremCalUI();
		tAddPremCalUI.submitData(tVData, "");

	}

}
