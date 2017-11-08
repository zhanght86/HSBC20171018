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
 * Description: 保全工作绩效统计打印类
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
 * @CreateDate：2005-12-03
 */
public class EdorWorkAchievePrintUI implements PrintService {
private static Logger logger = Logger.getLogger(EdorWorkAchievePrintUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	public EdorWorkAchievePrintUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		EdorWorkAchievePrintBL tEdorWorkAchievePrintBL = new EdorWorkAchievePrintBL();

		if (!tEdorWorkAchievePrintBL.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tEdorWorkAchievePrintBL.mErrors);
			mResult.clear();
			return false;
		} else {
			mResult = tEdorWorkAchievePrintBL.getResult();
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
		EdorWorkAchievePrintUI tEdorWorkAchievePrintUI = new EdorWorkAchievePrintUI();

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
		if (!tEdorWorkAchievePrintUI.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}

}
