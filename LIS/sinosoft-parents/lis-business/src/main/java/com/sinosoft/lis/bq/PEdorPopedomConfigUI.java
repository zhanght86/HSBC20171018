package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全权限配置
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
 * @CreateDate：2006-01-13
 */
public class PEdorPopedomConfigUI implements BusinessService{
private static Logger logger = Logger.getLogger(PEdorPopedomConfigUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	public PEdorPopedomConfigUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		PEdorPopedomConfigBL tPEdorPopedomConfigBL = new PEdorPopedomConfigBL();

		if (!tPEdorPopedomConfigBL.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorPopedomConfigBL.mErrors);
			mResult.clear();
			return false;
		} else {
			mResult = tPEdorPopedomConfigBL.getResult();
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
		PEdorPopedomConfigUI tPEdorPopedomConfigUI = new PEdorPopedomConfigUI();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "");

		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		if (!tPEdorPopedomConfigUI.submitData(tVData, "")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}

}
