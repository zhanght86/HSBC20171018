/*
 * @(#)GEdorAcceptAppConfirmAfterInitService.java	2005-10-10
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.GEdorAcceptAppConfirmBL;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保全申请确认服务类
 */
public class GEdorAcceptAppConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GEdorAcceptAppConfirmAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public GEdorAcceptAppConfirmAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		GEdorAcceptAppConfirmBL tGEdorAcceptAppConfirmBL = new GEdorAcceptAppConfirmBL();

		if (!tGEdorAcceptAppConfirmBL.submitData(cInputData,
				"INSERT||GEDORAPPCONFIRM")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGEdorAcceptAppConfirmBL.mErrors);
			return false;
		}

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		String sMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);

		MMap tMap = new MMap();
		String sql1 =" INSERT INTO LBMISSION " + " SELECT '" + "?tSerielNo?"
				+ "', T.* FROM LWMISSION T "
				+ "  WHERE  T.ActivityID IN( '0000008001', '0000008002') "
				+ "    AND  T.MISSIONID = '" + "?sMissionID?" + "'" ;
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql1);
		sqlbv1.put("tSerielNo",tSerielNo);
		sqlbv1.put("sMissionID",sMissionID);
		tMap.put(sqlbv1, "INSERT");

		String sql2 = " DELETE  FROM LWMISSION "
				+ "  WHERE  ActivityID IN( '0000008001', '0000008002') "
				+ "    AND  MISSIONID = '" + "?sMissionID?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql2);
		sqlbv2.put("sMissionID",sMissionID);
		tMap.put(sqlbv2, "DELETE");

		mResult = tGEdorAcceptAppConfirmBL.getResult();

		MMap mMap = (MMap) mResult.getObjectByObjectName("MMap", 0);
		mMap.add(tMap);

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
