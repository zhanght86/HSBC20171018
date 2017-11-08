/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/*
 * <p>ClassName: LCGrpContF1PUI </p> <p>Description: LCGrpContF1PUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database:
 * LIS @CreateDate：2002-11-04
 */
public class ReLCGrpContF1PUI {
private static Logger logger = Logger.getLogger(ReLCGrpContF1PUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
    private PubSubmit tPubSubmit =new PubSubmit();

	public ReLCGrpContF1PUI() {
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
			if (!cOperate.equals("PRINT") && !cOperate.equals("CONFIRM")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}
			ReLCGrpContF1PBL tReLCGrpContF1PBL = new ReLCGrpContF1PBL();
			logger.debug("Start ReLCGrpContF1P UI Submit ...");

			if (!tReLCGrpContF1PBL.submitData(cInputData, cOperate)) {
				if (tReLCGrpContF1PBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tReLCGrpContF1PBL.mErrors);
					return false;
				} else {
					buildError("sbumitData",
							"tReLCGrpContF1PBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tReLCGrpContF1PBL.getResult();
				// 数据提交
				if (!tPubSubmit.submitData(mResult, "")) {
					// @@错误处理
					mErrors.copyAllErrors(tPubSubmit.mErrors);
					CError.buildErr(this, "数据提交失败");
					return false;
				}
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
			vData.add(mLCGrpContSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
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
		mLCGrpContSet.set((LCGrpContSet) cInputData.getObjectByObjectName(
				"LCGrpContSet", 0));

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

		cError.moduleName = "LCGrpContF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		// ReLCGrpContF1PUI tReLCGrpContF1PUI = new ReLCGrpContF1PUI();
		// LCGrpContSet tLCPolSet = new LCGrpContSet();
		// LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		// tLCGrpContSchema.setGrpContNo("240110000000028");
		// tLCPolSet.add(tLCGrpContSchema);
		// VData vData = new VData();
		// vData.addElement(new GlobalInput());
		// vData.addElement(tLCPolSet);
		// if (!tReLCGrpContF1PUI.submitData(vData, "PRINT"))
		// {
		// logger.debug("fail to get print data");
		// }
		// else
		// {
		// vData = tReLCGrpContF1PUI.getResult();
		// }
	}
}
