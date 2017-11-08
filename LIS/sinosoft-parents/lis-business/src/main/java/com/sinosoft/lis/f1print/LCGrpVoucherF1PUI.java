/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * LCGrpContF1PUI
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 保单xml文件生成UI部分
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */
public class LCGrpVoucherF1PUI {
private static Logger logger = Logger.getLogger(LCGrpVoucherF1PUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	public LCGrpVoucherF1PUI() {
	}

	/**
	 * 数据处理主函数
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINT") && !cOperate.equals("REPRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 进行业务处理
			if (!dealData()) {
				return false;
			}

			LCGrpVoucherF1PBL tLCGrpVoucherF1PBL = new LCGrpVoucherF1PBL();
			logger.debug("Start LCGrpContF1PUI Submit ...");

			if (!tLCGrpVoucherF1PBL.submitData(cInputData, cOperate)) {
				if (tLCGrpVoucherF1PBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tLCGrpVoucherF1PBL.mErrors);
					return false;
				} else {
					buildError("sbumitData", "LCGrpContF1PBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tLCGrpVoucherF1PBL.getResult();
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submit", "发生异常");
			return false;
		}
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理
	 * 
	 * @return boolean 如果在处理过程中出错，则返回false,否则返回true
	 */
	private static boolean dealData() {
		return true;
	}

	/**
	 * 返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 出错处理
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCGrpContF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		LCGrpVoucherF1PUI tLCGrpVoucherF1PUI = new LCGrpVoucherF1PUI();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", "240210000000056 ");
		tTransferData.setNameAndValue("PolNo", "210210000000197");
		VData vData = new VData();
		vData.addElement(tG);
		vData.addElement(tTransferData);
		if (!tLCGrpVoucherF1PUI.submitData(vData, "PRINT")) {
			logger.debug(tLCGrpVoucherF1PUI.mErrors.getFirstError());
			logger.debug("fail to get print data");
		}
	}
}
