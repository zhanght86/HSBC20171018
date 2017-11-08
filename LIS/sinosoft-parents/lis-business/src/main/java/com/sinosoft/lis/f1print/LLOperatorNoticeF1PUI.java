package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LLOperatorNoticeF1PUI {
private static Logger logger = Logger.getLogger(LLOperatorNoticeF1PUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public LLOperatorNoticeF1PUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug(cOperate);
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
				&& !cOperate.equals("PRINT2")&&!cOperate.equals("PRINTdis")
				&& !cOperate.equals("PRINTwai")&&!cOperate.equals("PRINTepi")
				&& !cOperate.equals("PRINTbre")&&!cOperate.equals("PRINTche")
				&& !cOperate.equals("PRINTtum")&&!cOperate.equals("PRINTbab")
				&& !cOperate.equals("PRINTabr")&&!cOperate.equals("PRINTpar")
				&& !cOperate.equals("PRINTpar")&&!cOperate.equals("PRINTaf1")
				&& !cOperate.equals("PRINTaf2")&&!cOperate.equals("PRINTali")
				&& !cOperate.equals("PRINTcsi")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备传往后台的数据
		VData vData = new VData();

		if (!prepareOutputData(vData)) {
			return false;
		}

		LLOperatorNoticeF1PBL tLLOperatorNoticeF1PBL = new LLOperatorNoticeF1PBL();
		logger.debug("Start OperatorNoticeF1PUI Submit ...");

		if (!tLLOperatorNoticeF1PBL.submitData(vData, cOperate)) {
			if (tLLOperatorNoticeF1PBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLLOperatorNoticeF1PBL.mErrors);
				return false;
			} else {
				buildError("submitData",
						"OperatorNoticeF1PBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tLLOperatorNoticeF1PBL.getResult();
			return true;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mGlobalInput);
			vData.add(mLOPRTManagerSchema);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "OperatorNoticeF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public CErrors getErrors() {
		return mErrors;
	}

	//
	public static void main(String[] args) {
		OperatorNoticeF1PUI tOperatorNoticeF1PUI = new OperatorNoticeF1PUI();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("810000000014523");
		VData tVData = new VData();
		tVData.addElement(tLOPRTManagerSchema);
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86110000";
		tG.Operator = "001";
		tVData.addElement(tG);
		tOperatorNoticeF1PUI.submitData(tVData, "CONFIRM1");
	}

}
