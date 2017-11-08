package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class BQMeetF1PUI implements BusinessService{
private static Logger logger = Logger.getLogger(BQMeetF1PUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private String tWebPath = "";

	public BQMeetF1PUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug(cOperate);
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
				&& !cOperate.equals("PRINT1") && !cOperate.equals("PRINT2")
				&& !cOperate.equals("PRINT3") && !cOperate.equals("PRINT4")
				&& !cOperate.equals("PRINTa") && !cOperate.equals("PRINTb")
				&& !cOperate.equals("PRINTc") && !cOperate.equals("PRINTd")
				&& !cOperate.equals("PRINTe") && !cOperate.equals("PRINTf")
				&& !cOperate.equals("PRINTg") && !cOperate.equals("PRINTh")
				&& !cOperate.equals("PRINTi") && !cOperate.equals("PRINTj")
				&& !cOperate.equals("PRINTk") && !cOperate.equals("PRINTl")
				&& !cOperate.equals("PRINTm") && !cOperate.equals("PRINTn")
				&& !cOperate.equals("PRINTo") && !cOperate.equals("PRINTp")
				&& !cOperate.equals("PRINTq") && !cOperate.equals("PRINTr")
				&& !cOperate.equals("PRINTs") && !cOperate.equals("PRINTt")) {
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

		BQMeetF1PBL tBQMeetF1PBL = new BQMeetF1PBL();
		logger.debug("Start BQMeetF1PBL UI Submit ...");

		if (!tBQMeetF1PBL.submitData(vData, cOperate)) {
			if (tBQMeetF1PBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tBQMeetF1PBL.mErrors);
				return false;
			} else {
				buildError("submitData", "BQMeetF1PBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tBQMeetF1PBL.getResult();
			return true;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mGlobalInput);
			vData.add(mLOPRTManagerSchema);
			vData.add(tWebPath);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		tWebPath = (String) cInputData.getObjectByObjectName("String", 0);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "MeetF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8102100000108");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86110000";

		VData tVData = new VData();

		tVData.addElement(tG);
		tVData.addElement(tLOPRTManagerSchema);

		MeetF1PUI UI = new MeetF1PUI();
		UI.submitData(tVData, "PRINTj");
	}
}
