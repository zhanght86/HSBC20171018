package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ccvip
 * @version 1.0
 */
public class GrpUWChangePrintUI implements PrintService {
private static Logger logger = Logger.getLogger(GrpUWChangePrintUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public GrpUWChangePrintUI() {
	}

	/**
	 * getErrors
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return null;
	}

	/**
	 * getResult
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * submitData
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug(cOperate);
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
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

		GrpUWChangePrintBL tGrpUWChangePrintBL = new GrpUWChangePrintBL();
		logger.debug("Start DANF1PNewUI Submit ...");
		if (!tGrpUWChangePrintBL.submitData(vData, cOperate)) {
			if (tGrpUWChangePrintBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tGrpUWChangePrintBL.mErrors);
				return false;
			} else {
				buildError("submitData", "DANF1PNewBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tGrpUWChangePrintBL.getResult();
			return true;
		}

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "DANF1PNewUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

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

	public static void main(String[] args) {
		GrpUWChangePrintUI tGrpUWChangePrintUI = new GrpUWChangePrintUI();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("810000000002170");
		VData tVData = new VData();
		tVData.addElement(tLOPRTManagerSchema);
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86110000";
		tG.Operator = "001";
		tVData.addElement(tG);
		tGrpUWChangePrintUI.submitData(tVData, "PRINT");
	}
}
