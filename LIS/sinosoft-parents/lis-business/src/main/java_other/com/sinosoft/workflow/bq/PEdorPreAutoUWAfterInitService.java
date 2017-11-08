package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.PGrpEdorAppPreAutoUWBL;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全申请-自动初次核保服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class PEdorPreAutoUWAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(PEdorPreAutoUWAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public PEdorPreAutoUWAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("======PEdorPreAutoUWAfterInitService======");
		PGrpEdorAppPreAutoUWBL tPGrpEdorAppPreAutoUWBL = new PGrpEdorAppPreAutoUWBL();

		if (!tPGrpEdorAppPreAutoUWBL.submitData(cInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPGrpEdorAppPreAutoUWBL.mErrors);
			return false;
		}

		mResult = tPGrpEdorAppPreAutoUWBL.getResult();

		TransferData mNTransferData = (TransferData) mResult
				.getObjectByObjectName("TransferData", 0);

		String tPreUWState = (String) mNTransferData
				.getValueByName("PreUWFlag"); // 自动初次核保结果
		String tPreUWGrade = (String) mNTransferData
				.getValueByName("PreUWGrade"); // 自动初次核保级别

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mTransferData.setNameAndValue("PreUWFlag", tPreUWState);
		mTransferData.setNameAndValue("PreUWGrade", tPreUWGrade);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
