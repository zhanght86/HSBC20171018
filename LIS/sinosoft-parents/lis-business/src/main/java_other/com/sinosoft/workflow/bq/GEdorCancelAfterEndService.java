/*
 * @(#)GEdorCancelAfterEndService.java	2005-10-17
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.PGrpEdorCancelBL;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-团体申请撤销服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class GEdorCancelAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(GEdorCancelAfterEndService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public GEdorCancelAfterEndService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("======GEdorCancelAfterEndService======");

		// 调用执行保全申请撤销逻辑
		PGrpEdorCancelBL tPGrpEdorCancelBL = new PGrpEdorCancelBL();
		if (!tPGrpEdorCancelBL.submitData(cInputData, "G&EDORAPP")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPGrpEdorCancelBL.mErrors);
			return false;
		}

		// 备份并删除该保全申请的所有工作流节点任务
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		GlobalInput mGlobalInput = (GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0);
		// 获得当前任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");

		String strSQL = " SELECT * FROM LWMISSION " + " WHERE MISSIONID = '"
				+ "?tMissionID?" + "' "
				+ " AND ACTIVITYID NOT IN ('0000008008', '0000008000')";

		LWMissionDB tLWMissionDB = new LWMissionDB();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("tMissionID",tMissionID);
		LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sqlbv1);
		LWMissionSchema tLWMissionSchema;
		LBMissionSchema tLBMissionSchema = new LBMissionSchema();
		LBMissionSet tLBMissionSet = new LBMissionSet();
		Reflections ref = new Reflections();
		String tSerielNo = "";
		String CurrentDate = PubFun.getCurrentDate();
		String CurrentTime = PubFun.getCurrentTime();
		if (tLWMissionSet != null && tLWMissionSet.size() > 0) {
			for (int i = 1; i <= tLWMissionSet.size(); i++) {
				tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);

				tLWMissionSchema = tLWMissionSet.get(i);
				ref.transFields(tLBMissionSchema, tLWMissionSchema);
				tLBMissionSchema.setSerialNo(tSerielNo);
				tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
				tLBMissionSchema.setLastOperator(mGlobalInput.Operator);
				tLBMissionSchema.setMakeDate(CurrentDate);
				tLBMissionSchema.setMakeTime(CurrentTime);
				tLBMissionSchema.setModifyDate(CurrentDate);
				tLBMissionSchema.setModifyTime(CurrentTime);

				tLBMissionSet.add(tLBMissionSchema);
			}
		}

		MMap tMap = new MMap();
		// tMap.put(tLBMissionSet, "INSERT");

		strSQL = " DELETE FROM LWMISSION " + " WHERE MISSIONID = '"
				+ "?tMissionID?" + "' "
				+ " AND ACTIVITYID NOT IN ('0000008008', '0000008000')";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strSQL);
		sqlbv2.put("tMissionID",tMissionID);
		tMap.put(sqlbv2, "DELETE");

		mResult = tPGrpEdorCancelBL.getResult();
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
