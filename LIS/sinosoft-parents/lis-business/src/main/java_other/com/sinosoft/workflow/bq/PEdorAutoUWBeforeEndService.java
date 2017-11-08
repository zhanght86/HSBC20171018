/*
 * @(#)PEdorAutoUWAfterInitService.java	2005-05-03
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.LJSGetEndorseTotalBL;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.BeforeEndService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全申请-自动核保服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class PEdorAutoUWBeforeEndService implements BeforeEndService {
private static Logger logger = Logger.getLogger(PEdorAutoUWBeforeEndService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public PEdorAutoUWBeforeEndService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
//		logger.debug("======PEdorAutoUWAfterInitService======");
//		PGrpEdorAppAutoUWBL tPGrpEdorAppAutoUWBL = new PGrpEdorAppAutoUWBL();
//
//		if (!tPGrpEdorAppAutoUWBL.submitData(cInputData, cOperate)) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tPGrpEdorAppAutoUWBL.mErrors);
//			return false;
//		}
//
//		mResult = tPGrpEdorAppAutoUWBL.getResult();


		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		String tUWState = (String) mTransferData.getValueByName("UWFlag"); // 自动核保结果
		
		logger.debug("=======tUWState=============" + tUWState);
		
		if("9".equals(tUWState))
		{
		
			LPEdorAppSchema tLPEdorAppSchema = (LPEdorAppSchema)cInputData.getObjectByObjectName("LPEdorAppSchema", 0);
			GlobalInput tGlobalInput =(GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0);
			
			LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
			tLPEdorAppDB.setEdorAcceptNo(tLPEdorAppSchema.getEdorAcceptNo());
			if (!tLPEdorAppDB.getInfo()) {
				mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
				CError tError = new CError();
				tError.errorMessage = "查询保全受理失败";
				mErrors.addOneError(tError);
				return false;
			}

			tLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());
			
			VData tVData = new VData();
			tVData.add(tLPEdorAppSchema);
			tVData.add(tGlobalInput);
			LJSGetEndorseTotalBL tLJSGetEndorseTotalBL = new LJSGetEndorseTotalBL();
	
			if (!tLJSGetEndorseTotalBL.submitData(tVData, "")) {
				mErrors.copyAllErrors(tLJSGetEndorseTotalBL.mErrors);
				mErrors.addOneError(new CError("生成财务应收、应付信息失败!"));
				return false;
			}
			if (tLJSGetEndorseTotalBL.getResult() == null) {
				mErrors.copyAllErrors(tLJSGetEndorseTotalBL.mErrors);
				mErrors.addOneError(new CError("获得财务应收、应付信息失败!"));
				return false;
			}
			VData tResult = tLJSGetEndorseTotalBL.getResult();
			MMap tMMap = (MMap) (tResult.getObjectByObjectName("MMap", 0));
			this.mResult.add(tMMap);
		}
		
//		map.add(tMMap);
//		tTransferData = (TransferData) tResult.getObjectByObjectName(
//				"TransferData", 0);
		// 此时无需传递付费标志
		// String sNeedPayFlag =
		// (String) tTransferData.getValueByName("NeedPayFlag");
		// mTransferData.setNameAndValue("NeedPayFlag", sNeedPayFlag);

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
