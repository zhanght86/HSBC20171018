/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LCContPrintTraceSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/*
 * <p>ClassName: LCContF1PUI </p> <p>Description: LCContF1PUI类文件 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database: LIS
 * @CreateDate：2002-11-04
 */
public class ReLCContF1PUI implements BusinessService {
private static Logger logger = Logger.getLogger(ReLCContF1PUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

    private PubSubmit tPubSubmit =new PubSubmit();
	public ReLCContF1PUI() {
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
			ReLCContF1PBL tReLCContF1PBL = new ReLCContF1PBL();
			logger.debug("Start ReLCContF1P UI Submit ...");

			if (!tReLCContF1PBL.submitData(cInputData, cOperate)) {
				if (tReLCContF1PBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tReLCContF1PBL.mErrors);
					return false;
				} else {
					buildError("sbumitData", "tReLCContF1PBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tReLCContF1PBL.getResult();
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
		// ReLCContF1PUI tReLCContF1PUI = new ReLCContF1PUI();
		// LCContSet tLCPolSet = new LCContSet();
		// LCContSchema tLCContSchema = new LCContSchema();
		// tLCContSchema.setContNo("230110000000070");
		// tLCPolSet.add(tLCContSchema);
		// VData vData = new VData();
		// vData.addElement(new GlobalInput());
		// vData.addElement(tLCPolSet);
		// if (!tReLCContF1PUI.submitData(vData, "PRINT"))
		// {
		// logger.debug("fail to get print data");
		// }
		// else
		// {
		// vData = tReLCContF1PUI.getResult();
		// }
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
