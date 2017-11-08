/*
 * @(#)PEdorAutoUWAfterInitService.java	2005-05-10
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.PEdorApproveBL;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全复核服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author sinosoft
 * @version 1.0
 */
public class PEdorApproveAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(PEdorApproveAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public PEdorApproveAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("======PEdorApproveAfterInitService======");

		PEdorApproveBL tPEdorApproveBL = new PEdorApproveBL();

		if (!tPEdorApproveBL.submitData(cInputData, "EDOR||APPROVE")) {
			mErrors.copyAllErrors(tPEdorApproveBL.mErrors);
			return false;
		}
		String mApproveFlag = (String) mTransferData.getValueByName("ApproveFlag");
		mTransferData =(TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		mResult = tPEdorApproveBL.getResult();
		// =====DEL===zhangtao=====2005-11-29=========复核抽检功能去除=========BGN=========
		// //调用抽检规则程序，得到是否抽检结论
		// PEdorApproveCheckBL tPEdorApproveCheckBL = new PEdorApproveCheckBL();
		//
		// if (!tPEdorApproveCheckBL.submitData(cInputData, ""))
		// {
		// mErrors.copyAllErrors(tPEdorApproveCheckBL.mErrors);
		// return false;
		// }
		// String sNeedCheckFlag = "";
		// String tMissionID = "";
		// String tSubMissionID = "";
		// try
		// {
		// mTransferData =
		// (TransferData)
		// cInputData.getObjectByObjectName("TransferData", 0);
		// //获得当前任务ID
		// tMissionID =
		// (String) mTransferData.getValueByName("MissionID");
		// //获得当前任务ID
		// tSubMissionID =
		// (String) mTransferData.getValueByName("SubMissionID");
		// //获得抽检标志
		// sNeedCheckFlag =
		// (String)
		// mTransferData.getValueByName("CheckFlag");
		// logger.debug("--------- sNeedCheckFlag at
		// PEdorApproveAfterInitService -------------------" + sNeedCheckFlag);
		// }
		// catch (Exception ex)
		// {
		// sNeedCheckFlag = "Y"; //出现异常直接抽检
		// }
		//
		// if (sNeedCheckFlag == null)
		// {
		//
		// }
		// else if (sNeedCheckFlag.equals("Y")) //需要抽检
		// {
		// //取出抽检需要提交的操作
		// mResult = tPEdorApproveCheckBL.getResult();
		//
		// MMap tMap = new MMap();
		// //更新保全复核节点的抽检状态为抽检中
		// String sUpdLwmission = " update lwmission set missionprop13 = '0' " +
		// " where activityid = '0000000007' " +
		// " and submissionid = '" + tSubMissionID +
		// "' and missionid = '" + tMissionID + "'";
		// tMap.put(sUpdLwmission, "UPDATE");
		//
		// MMap mMap = (MMap) mResult.getObjectByObjectName("MMap", 0);
		// mMap.add(tMap);
		// }
		// else if (sNeedCheckFlag.equals("N")) //不需要抽检，继续执行复核
		// {
		// PEdorApproveBL tPEdorApproveBL = new PEdorApproveBL();
		//
		// if (!tPEdorApproveBL.submitData(cInputData, "EDOR||APPROVE"))
		// {
		// mErrors.copyAllErrors(tPEdorApproveBL.mErrors);
		// return false;
		// }
		//
		// mResult = tPEdorApproveBL.getResult();
		// }
		// =====DEL===zhangtao=====2005-11-29=========复核抽检功能去除=========END=========
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
