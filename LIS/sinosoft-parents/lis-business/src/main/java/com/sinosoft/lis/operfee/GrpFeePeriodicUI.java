package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author yangyf
 * @version 1.0
 */

public class GrpFeePeriodicUI {
private static Logger logger = Logger.getLogger(GrpFeePeriodicUI.class);

	// 业务处理相关变量
	private VData mInputData = new VData();

	public CErrors mErrors = new CErrors();

	private VData mResult;

	private VData m1Result;

	public GrpFeePeriodicUI() {
	}

	public static void main(String arg[]) {
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "862100";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2007-05-01");
		tTransferData.setNameAndValue("EndDate", "2007-05-29");
		IndiDueFeePartUI IndiDueFeePartUI1 = new IndiDueFeePartUI();

		tTransferData.setNameAndValue("GrpContNo", "240210000000039");
		VData tv = new VData();
		tv.add(tTransferData);
		tv.add(tGI);
		logger.debug("准备好了数据");
		if (IndiDueFeePartUI1.submitData(tv, "")) {
			logger.debug("个单批处理催收完成");
		} else {
			logger.debug("个单批处理催收信息提示：");
		}

	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("判断是否单张抽挡，还是部分抽挡");
		TransferData tTransferData = new TransferData();
		tTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		String tGrpCont = (String) tTransferData.getValueByName("GrpContNo");
		printStr("开始续期抽挡" + tGrpCont);
		logger.debug(tGrpCont);
		if (tGrpCont != null && !tGrpCont.equals("null")
				&& !tGrpCont.equals("")) {
			mInputData = (VData) cInputData.clone();
			GrpFeePeriodicSingBL tGrpFeePeriodicSingBL = new GrpFeePeriodicSingBL();
			logger.debug("Start IndiDueFee UI Submit...");
			tGrpFeePeriodicSingBL.submitData(mInputData, "sing");
			mResult = tGrpFeePeriodicSingBL.getResult();
			// / m1Result = tGrpFeePeriodicSingBL.getLCResult();
			logger.debug("End IndiDueFee UI Submit...");

			mInputData = null;
			// 如果有需要处理的错误，则返回
			if (tGrpFeePeriodicSingBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tGrpFeePeriodicSingBL.mErrors);
				return false;
			}
			logger.debug("error num=" + mErrors.getErrorCount());
			return true;
		} else {
			// mInputData = (VData) cInputData.clone();
			// RnDealBLF tRnDealBLF = new RnDealBLF();
			// logger.debug("Start IndiDueFee UI Submit...");
			// tRnDealBLF.submitData(mInputData, cOperate);
			// mResult = tRnDealBLF.getResult();
			// m1Result = tRnDealBLF.getLCResult();
			// logger.debug("End IndiDueFee UI Submit...");
			//
			// mInputData = null;
			// // 如果有需要处理的错误，则返回
			// if (tRnDealBLF.mErrors.needDealError())
			// {
			// this.mErrors.copyAllErrors(tRnDealBLF.mErrors);
			// return false;
			// }
			// logger.debug("error num=" + mErrors.getErrorCount());
			return true;
		}
	}

	public VData getLCResult() {
		return m1Result;
	}

	// 输出当前操作
	public void printStr(String str) {
		logger.debug("++++++++++++++++++++++++++++++++++++++++++");
		logger.debug("            " + str);
		logger.debug("++++++++++++++++++++++++++++++++++++++++++");

	}

}
