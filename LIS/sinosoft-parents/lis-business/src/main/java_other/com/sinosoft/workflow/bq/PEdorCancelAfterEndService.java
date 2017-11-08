/*
 * @(#)PEdorAutoUWAfterInitService.java 2005-05-09
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
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.*;
import com.sinosoft.workflowengine.AfterEndService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-个人申请撤销服务类
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
public class PEdorCancelAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(PEdorCancelAfterEndService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
    private ExeSQL tExeSQL = new ExeSQL();
	public PEdorCancelAfterEndService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("======PEdorCancelAfterEndService======");

		GlobalInput mGlobalInput = (GlobalInput) cInputData
		.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		//增加客户层保全撤销处理
		LPEdorAppSchema xLPEdorAppSchema = new LPEdorAppSchema();
		xLPEdorAppSchema = (LPEdorAppSchema)cInputData.getObjectByObjectName("LPEdorAppSchema",0);
		String first_MissionID = (String) mTransferData.getValueByName("MissionID");  //界面传入初始任务号
		String mActivityID = "0000000008"; //保全工作流撤销
		
		String mCustomerBQFlag = "0"; //0-非客户层；1-客户层保全
		String sql1="select EdorAcceptNo from lpedorapp where othernotype='1' and EdorAcceptNo ='"+"??"+"'";
		String xEdorAcceptNo="";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql1);
		sqlbv1.put("EdorAcceptNo",xLPEdorAppSchema.getEdorAcceptNo());
		xEdorAcceptNo = tExeSQL.getOneValue(sqlbv1);
		if(xEdorAcceptNo!=null&&!xEdorAcceptNo.equals(""))//客户层保全
		{
			mCustomerBQFlag="1";		
            //前台保证传进来的一定是客户层保全原始受理号
			String mEdorAcceptNo = xEdorAcceptNo;  //原始受理号
			
			String customerBQ_mission="select missionid,submissionid,missionprop1,(select edorstate from lpedorapp where EdorAcceptNo=missionprop1 ) from lwmission l "
			+" where activityid = '"+"?mActivityID?"+"' and processid = '0000000000' and defaultoperator is not null "
            +" and missionprop1 in(select icedoracceptno from lpconttempinfo x where x.edoracceptno='"+"?mEdorAcceptNo?"+"' union select '"+"?mEdorAcceptNo?"+"' from dual )"
	        +" and missionprop7 like concat(?value1?,'%') order by MakeDate, MakeTime ";
			
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(customerBQ_mission);
			sqlbv2.put("mActivityID",mActivityID);
			sqlbv2.put("mEdorAcceptNo",mEdorAcceptNo);
			sqlbv2.put("value1",mGlobalInput.ManageCom);
			SSRS BQMissionData = new SSRS();
			BQMissionData = tExeSQL.execSQL(sqlbv2);
			if(BQMissionData.MaxRow==0)
			{
			    logger.debug("撤销失败，原因是:客户层保全查询所有子任务失败!");
				return false;
			}
			MMap mMap = new MMap();
			mTransferData.setNameAndValue("NullOprator", "1");
			for(int i=1;i<=BQMissionData.MaxRow;i++)
			{
				TransferData bTransferData = new TransferData();
				bTransferData = mTransferData;
				
				bTransferData.removeByName("MissionID");
				bTransferData.removeByName("SubMissionID");
				bTransferData.removeByName("EdorAcceptNo");
				
				LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
				tLPEdorAppSchema.setEdorAcceptNo(BQMissionData.GetText(i, 3));
				tLPEdorAppSchema.setEdorState(BQMissionData.GetText(i, 4));

				bTransferData.setNameAndValue("MissionID",BQMissionData.GetText(i, 1));
				bTransferData.setNameAndValue("SubMissionID",BQMissionData.GetText(i, 2));
				bTransferData.setNameAndValue("EdorAcceptNo", BQMissionData.GetText(i, 3));
				
				VData tVData = new VData();
				tVData.addElement(mGlobalInput);
		     	tVData.addElement(tLPEdorAppSchema);
				tVData.addElement(bTransferData);
                //调用执行个人保全申请撤销逻辑
				PGrpEdorCancelBL tPGrpEdorCancelBL = new PGrpEdorCancelBL();
				if (!tPGrpEdorCancelBL.submitData(tVData, "I&EDORAPP")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tPGrpEdorCancelBL.mErrors);
					return false;
				}
				mResult = tPGrpEdorCancelBL.getResult();
				MMap sub_Map = (MMap) mResult.getObjectByObjectName("MMap", 0);
				mMap.add(sub_Map);
			}

			//前台传入的missionid的0000000008节点工作流由公共工作流模块里的deletemission处理，而客户层保全下其他保单的所有节点都删除
			String strSQL = " SELECT * FROM LWMISSION a"
				+ " WHERE MISSIONID in (select distinct b.missionid from LWMISSION b where b.missionprop1 in(select icedoracceptno from lpconttempinfo x where x.edoracceptno='"+"?mEdorAcceptNo?"+"')" 
				+" and b.missionid<>'"+"?first_MissionID?"+"')"
				+" union select * from lwmission a where MISSIONID='"+"?first_MissionID?"+"'"
				+ " AND ACTIVITYID NOT IN ('0000000008')";

			LWMissionDB tLWMissionDB = new LWMissionDB();
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSQL);
			sqlbv3.put("mEdorAcceptNo",mEdorAcceptNo);
			sqlbv3.put("first_MissionID",first_MissionID);
			LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sqlbv3);
			LWMissionSchema tLWMissionSchema;
			LBMissionSchema tLBMissionSchema;
			LBMissionSet tLBMissionSet = new LBMissionSet();
			Reflections ref = new Reflections();
			String CurrentDate = PubFun.getCurrentDate();
			String CurrentTime = PubFun.getCurrentTime();
			if (tLWMissionSet != null && tLWMissionSet.size() > 0) {
				for (int i = 1; i <= tLWMissionSet.size(); i++) {
					tLWMissionSchema = tLWMissionSet.get(i);
					tLBMissionSchema = new LBMissionSchema(); // XinYQ modified on
																// 2005-12-26
					ref.transFields(tLBMissionSchema, tLWMissionSchema);
					tLBMissionSchema.setSerialNo(PubFun1.CreateMaxNo(
							"MissionSerielNo", 10));
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
			
			tMap.put(tLWMissionSet, "DELETE");
			tMap.put(tLBMissionSet, "INSERT");
			mMap.add(tMap);
			
			mResult.clear();
			mResult.add(mMap);
		}
		else
		{
			// 调用执行个人保全申请撤销逻辑
			PGrpEdorCancelBL tPGrpEdorCancelBL = new PGrpEdorCancelBL();
			if (!tPGrpEdorCancelBL.submitData(cInputData, "I&EDORAPP")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPGrpEdorCancelBL.mErrors);
				return false;
			}
	
			// 备份并删除该保全申请的所有工作流节点任务
			// 获得当前任务ID
			String tMissionID = (String) mTransferData.getValueByName("MissionID");
	
			String strSQL = " SELECT * FROM LWMISSION "
					+ " WHERE MISSIONID = '"
					+ "?tMissionID?"
					+ "' "
					+ " AND ACTIVITYID NOT IN ('0000000008', '0000000098', '0000000000')";
	
			LWMissionDB tLWMissionDB = new LWMissionDB();
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(strSQL);
			sqlbv4.put("tMissionID",tMissionID);
			LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sqlbv4);
			LWMissionSchema tLWMissionSchema;
			LBMissionSchema tLBMissionSchema;
			LBMissionSet tLBMissionSet = new LBMissionSet();
			Reflections ref = new Reflections();
			String CurrentDate = PubFun.getCurrentDate();
			String CurrentTime = PubFun.getCurrentTime();
			if (tLWMissionSet != null && tLWMissionSet.size() > 0) {
				for (int i = 1; i <= tLWMissionSet.size(); i++) {
					tLWMissionSchema = tLWMissionSet.get(i);
					tLBMissionSchema = new LBMissionSchema(); // XinYQ modified on
																// 2005-12-26
					ref.transFields(tLBMissionSchema, tLWMissionSchema);
					tLBMissionSchema.setSerialNo(PubFun1.CreateMaxNo(
							"MissionSerielNo", 10));
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
					+ " AND ACTIVITYID NOT IN ('0000000008', '0000000000')";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(strSQL);
			sqlbv5.put("tMissionID",tMissionID);
			tMap.put(sqlbv5, "DELETE");
	
			mResult = tPGrpEdorCancelBL.getResult();
			MMap mMap = (MMap) mResult.getObjectByObjectName("MMap", 0);
			mMap.add(tMap);
		}
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
