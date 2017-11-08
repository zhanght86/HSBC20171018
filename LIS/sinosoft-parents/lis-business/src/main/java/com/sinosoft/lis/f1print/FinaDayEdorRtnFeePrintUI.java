package com.sinosoft.lis.f1print;
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
 * Description: 退费日结打印类
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
 * @CreateDate：2005-11-15 Modified By QianLy on 2006-12-05，2006-12-25
 */
public class FinaDayEdorRtnFeePrintUI implements PrintService {
private static Logger logger = Logger.getLogger(FinaDayEdorRtnFeePrintUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	public FinaDayEdorRtnFeePrintUI() {
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
		String finDayType = (String) mTransferData.getValueByName("FinDayType");
		if (finaType.equals(PrintManagerBL.CODE_PEdorFina)) { // 个险日结
			// 15是日结打印界面上的个险日结编码
			if (finDayType.equals("15")) {
				FinaDayEdorRtnFeePrintBL tFinaDayEdorRtnFeePrintBL = new FinaDayEdorRtnFeePrintBL();
				if (!tFinaDayEdorRtnFeePrintBL.submitData(mInputData, cOperate)) {
					// @@错误处理
					this.mErrors
							.copyAllErrors(tFinaDayEdorRtnFeePrintBL.mErrors);
					mResult.clear();
					return false;
				} else {
					mResult = tFinaDayEdorRtnFeePrintBL.getResult();
				}
				// 18是日结打印界面上的个险903日结编码
			} else if (finDayType.equals("18")) {// 个险903日结
				FinaDayEdorRtnFeePrintBL903 tFinaDayEdorRtnFeePrintBL903 = new FinaDayEdorRtnFeePrintBL903();
				if (!tFinaDayEdorRtnFeePrintBL903.submitData(mInputData,
						cOperate)) {
					// @@错误处理
					this.mErrors
							.copyAllErrors(tFinaDayEdorRtnFeePrintBL903.mErrors);
					mResult.clear();
					return false;
				} else {
					mResult = tFinaDayEdorRtnFeePrintBL903.getResult();
				}
			}
		} else if (finaType.equals(PrintManagerBL.CODE_GEdorFina)) {// 团险日结
			GrpFinaDayEdorRtnFeePrintBL tGrpFinaDayEdorRtnFeePrintBL = new GrpFinaDayEdorRtnFeePrintBL();
			if (!tGrpFinaDayEdorRtnFeePrintBL.submitData(mInputData, cOperate)) {
				// @@错误处理
				this.mErrors
						.copyAllErrors(tGrpFinaDayEdorRtnFeePrintBL.mErrors);
				mResult.clear();
				return false;
			} else {
				mResult = tGrpFinaDayEdorRtnFeePrintBL.getResult();
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

	public static void main(String[] args) {
		logger.debug("test begin:");
		FinaDayEdorRtnFeePrintUI tFinaDayEdorRtnFeePrintUI = new FinaDayEdorRtnFeePrintUI();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "");
		tTransferData.setNameAndValue("StartDate", "");
		tTransferData.setNameAndValue("EndDate", "");

		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		if (!tFinaDayEdorRtnFeePrintUI.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}

}
