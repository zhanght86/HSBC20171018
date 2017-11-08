/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/*
 * <p>ClassName: LCContF1PUI </p> <p>Description: LCContF1PUI类文件 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database: LIS
 * @CreateDate：2002-11-04
 */
public class LCContF1PUI {
private static Logger logger = Logger.getLogger(LCContF1PUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSchema mLCContSchema = new LCContSchema();

	private String mTemplatePath = null;
	private String mOutXmlPath = null;
	private String prtSeq = "";
	private String startNo = "";
	private String individ = "";

	public LCContF1PUI() {
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
		try {
			if (!cOperate.equals("PRINT") // 正常打印
					&& !cOperate.equals("REPRINT") // 保单遗失补发
					&& !cOperate.equals("PRINTEX")) { // 前台保单打印
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			LCContF1PBL f1pLCContBL = new LCContF1PBL();
			logger.debug("Start LCContF1P UI Submit ...");

			if (!f1pLCContBL.submitData(cInputData, cOperate)) {
				logger.debug("-----------------------------------=====================00");
				if (f1pLCContBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(f1pLCContBL.mErrors);
					return false;
				} else {
					buildError("sbumitData", "f1pLCContBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = f1pLCContBL.getResult();
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submit", "发生异常");
			return false;
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
			vData.add(mLCContSchema);
			vData.add(prtSeq);
			vData.add(startNo);
			vData.add(individ);
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
	private static boolean dealData() {
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
		mLCContSchema.setSchema((LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0));
		prtSeq = (String) cInputData.getObjectByObjectName("String", 0);
		startNo = (String) cInputData.getObjectByObjectName("String", 0, 2);
		individ = (String) cInputData.getObjectByObjectName("String", 0, 3);

		if (mGlobalInput == null) {
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
		cError.moduleName = "LCContF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		LCContF1PUI tLCContF1PUI = new LCContF1PUI();
		// LCContSet tLCContSet = new LCContSet();
		LCContSchema tLCContSchema = new LCContSchema();
		// tLCContSchema.setContNo("230110000000051");
		tLCContSchema.setContNo("230110000003314");
		// tLCContSet.add(tLCContSchema);
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "Kevin";
		VData vData = new VData();
		vData.addElement(tGlobalInput);
		vData.addElement(tLCContSchema);
		vData.addElement("11111");
		vData.addElement("22222");
		tLCContF1PUI.submitData(vData, "PRINT");
	}
}
