package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 加补费日结打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：liurx
 * @version：1.0
 * @CreateDate：2005-11-15 Modified By QianLy on 2006-12-05
 */
public class FinaDayEdorAddFeePrintUI implements PrintService {
private static Logger logger = Logger.getLogger(FinaDayEdorAddFeePrintUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	public FinaDayEdorAddFeePrintUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		TransferData mTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		String finaType = (String) mTransferData.getValueByName("FinaType");
		if (finaType.equals(PrintManagerBL.CODE_PEdorFina)) {
			FinaDayEdorAddFeePrintBL tFinaDayEdorAddFeePrintBL = new FinaDayEdorAddFeePrintBL();
			if (!tFinaDayEdorAddFeePrintBL.submitData(mInputData, cOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tFinaDayEdorAddFeePrintBL.mErrors);
				mResult.clear();
				return false;
			} else {
				mResult = tFinaDayEdorAddFeePrintBL.getResult();
			}
		} else if (finaType.equals(PrintManagerBL.CODE_GEdorFina)) {
			GrpFinaDayEdorAddFeePrintBL tGrpFinaDayEdorAddFeePrintBL = new GrpFinaDayEdorAddFeePrintBL();
			if (!tGrpFinaDayEdorAddFeePrintBL.submitData(mInputData, cOperate)) {
				// @@错误处理
				this.mErrors
						.copyAllErrors(tGrpFinaDayEdorAddFeePrintBL.mErrors);
				mResult.clear();
				return false;
			} else {
				mResult = tGrpFinaDayEdorAddFeePrintBL.getResult();
			}
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
	// public static void main(String[] args)
	// {
	// logger.debug("test begin:");
	// FinaDayEdorAddFeePrintUI tFinaDayEdorAddFeePrintUI = new
	// FinaDayEdorAddFeePrintUI();
	//
	// VData tVData = new VData();
	//
	// GlobalInput tGlobalInput = new GlobalInput();
	// tGlobalInput.ManageCom = "86110000";
	// tGlobalInput.Operator = "001";
	//
	// TransferData tTransferData = new TransferData();
	// tTransferData.setNameAndValue("ManageCom","");
	// tTransferData.setNameAndValue("StartDate","");
	// tTransferData.setNameAndValue("EndDate","");
	//
	// tVData.add(tGlobalInput);
	// tVData.add(tTransferData);
	// if(!tFinaDayEdorAddFeePrintUI.submitData(tVData, "PRINT"))
	// {
	// logger.debug("test failed");
	// }
	// logger.debug("test end");
	// }

}
