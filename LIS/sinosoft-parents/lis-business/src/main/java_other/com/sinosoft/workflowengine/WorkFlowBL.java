package com.sinosoft.workflowengine;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class WorkFlowBL {
	private static Logger logger = Logger.getLogger(WorkFlowBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();;
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput;
	private TransferData mTransferData;
	/** 数据操作字符串 */
	private String mOperator;
	/** 往前台传输数据的容器 */
	private VData tResult;
	private String mManageCom;
	private String mOperate;
	private String mPersionOperate;
	private String dFlag="";
	private boolean mFlag = true;

	public WorkFlowBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		// 准备给后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		// 数据提交
		if (mFlag) {
			// 数据提交
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tResult, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "tPubSubmit";
				// tError.functionName = "submitData";
				// tError.errorMessage = "数据提交失败!";
				// this.mErrors.addOneError(tError);
				System.out.println("ss");
				return false;
			}
		}

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;
		mGlobalInput = ((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mPersionOperate = mGlobalInput.Operator;
		if (mPersionOperate == null || mPersionOperate.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operator失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!");
			return false;
		}
		mOperate = cOperate;
		if (mOperate == null || mOperate.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate任务节点编码失败!");
			return false;
		}
		 dFlag = (String) mTransferData.getValueByName("DelActivyID005");
		if(dFlag == null || dFlag.trim().equals(""))
		{
			return true;
		}
		return true;
	}

	private String[] getStartIDbyBusiType(String tBusiType,String tFunctionID) {
		String[] tActivityID;
		String tSQL_Activityid = "select activityid from lwactivity  where busitype='"
				+ "?tBusiType?" + "' and ActivityFlag = '1' ";
		if(tFunctionID!=null&&!tFunctionID.equals(""))
		{
			tSQL_Activityid = tSQL_Activityid + " and functionid='"+"?tFunctionID?"+"' ";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQL_Activityid);
		sqlbv1.put("tBusiType", tBusiType);
		sqlbv1.put("tFunctionID", tFunctionID);
		SSRS tSSRS = new SSRS();
		tSSRS = new ExeSQL().execSQL(sqlbv1);
		if (tSSRS.getMaxRow() <= 0) {
			return null;
		}
		tActivityID = new String[tSSRS.getMaxRow()];
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tActivityID[i - 1] = tSSRS.GetText(i, 1);
		}

		return tActivityID;
	}

	private boolean dealData() {
		// tongmeng 2011-05-03 modify
		// 接口通过 MissionID,SubMissionID 和 ActivityID 进行传递.
		String[] tActivityIDArr = null;
		String ActivityID = (String) mTransferData.getValueByName("ActivityID");
		String FunctionID = (String) mTransferData.getValueByName("FunctionID");
		boolean multStartFlag = false;
		// 如果ActivityID为空,认为是初始节点,需要通过BusiType进行获取.
		if (ActivityID == null || ActivityID.equals("")) {
			// if(!mOperate.equals("create"))
			if (!mOperate.equalsIgnoreCase("create")) {
				CError.buildErr(this, "传入的业务数据与操作符关系错误!");
				return false;
			}
			String tBusiType = (String) mTransferData
					.getValueByName("BusiType");
			tActivityIDArr = getStartIDbyBusiType(tBusiType,FunctionID);
			if (tActivityIDArr == null) {
				CError.buildErr(this, "根据业务类型查询工作流活动节点失败!");
				return false;
			}
			if (tActivityIDArr.length > 1) {
				multStartFlag = true;
			}
		} else {
			// 增加了申请“apply”暂时把这个校验去掉
			// if(!mOperate.equals("execute"))
			// {
			// CError.buildErr(this,"传入的业务数据与操作符关系错误!");
			// return false;
			// }
			tActivityIDArr = new String[1];
			tActivityIDArr[0] = ActivityID;
		}

		for (int i = 0; i < tActivityIDArr.length; i++) {
			ActivityID = tActivityIDArr[i];
			// String
			// FunctionID=(String)mTransferData.getValueByName("FunctionID");
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("select busitype ,activityid ,activityflag,functionid from lwactivity where activityid='"
					+ "?ActivityID?" + "'");
			sqlbv2.put("ActivityID", ActivityID);
			SSRS tSSRS = tExeSQL.execSQL(sqlbv2);
			if (tSSRS == null || tSSRS.MaxRow == 0) {
				CError tError = new CError();
				tError.moduleName = "WorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询工作流节点失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			String BusiType = tSSRS.GetText(1, 1);
			// mTransferData.removeByName("BusiType");
			// mTransferData.setNameAndValue("BusiType", BusiType);//注意这个地方
			// String ActivityID=tSSRS.GetText(1, 2);
			String ActivityFlag = tSSRS.GetText(1, 3);
			FunctionID = tSSRS.GetText(1, 4);
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql("select processid from LWCorresponding where busitype='"
					+ "?BusiType?" + "'");
			sqlbv3.put("BusiType", BusiType);
			String ProcessID = tExeSQL.getOneValue(sqlbv3);
			// zhaojiawei
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql("select version from LWCorresponding where busitype='"
					+ "?BusiType?" + "'");
			sqlbv4.put("BusiType", BusiType);
			String Version = tExeSQL.getOneValue(sqlbv4);
			if (ProcessID == null || ProcessID.equals("")) {
				CError tError = new CError();
				tError.moduleName = "WorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询工作流流程失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// tongmeng 2011-05-03 modify
			// 有些数值需要默认设置
			mTransferData.removeByName("BusiType");
			mTransferData.removeByName("ActivityID");
			mTransferData.removeByName("FunctionID");

			mTransferData.setNameAndValue("BusiType", BusiType);
			mTransferData.setNameAndValue("FunctionID", FunctionID);
			mTransferData.setNameAndValue("ActivityID", ActivityID);

			String MissionID = (String) mTransferData
					.getValueByName("MissionID");
			String SubMissionID = (String) mTransferData
					.getValueByName("SubMissionID");
			// 如果任务ID为空则是创建开始节点
			if (MissionID == null || MissionID.equals("")) {
				if (!CreateStartMission(ProcessID, ActivityID, mInputData,
						Version)) {
					return false;
				}
			} else {
				String verSQLPV = "select lwm.processid,lwm.version from lwmission lwm where lwm.missionid='"
						+ "?MissionID?"
						+ "' and lwm.submissionid='"
						+ "?SubMissionID?"
						+ "' and lwm.activityid='" + "?ActivityID?" + "'";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(verSQLPV);
				sqlbv5.put("MissionID", MissionID);
				sqlbv5.put("SubMissionID", SubMissionID);
				sqlbv5.put("ActivityID", ActivityID);
				SSRS pvSSRS = new SSRS();
				pvSSRS = new ExeSQL().execSQL(sqlbv5);
				String tProcessIDcurrent = pvSSRS.GetText(1, 1);
				String tVersioncurrent = pvSSRS.GetText(1, 2);
				if (mOperate.equalsIgnoreCase("apply")) {
					ActivityOperatorNode tExecActivityOperatorNode = new ActivityOperatorNode(
							ActivityID, tProcessIDcurrent, tVersioncurrent);
					if (tExecActivityOperatorNode.Allot(MissionID,
							SubMissionID, mInputData)) {
						VData tVData = tExecActivityOperatorNode.getResult();
						if (tVData != null) {
							mResult.add(tVData);
						}
					} else {
						this.mErrors.copyAllErrors(tExecActivityOperatorNode
								.getErrors());
						return false;
					}
				} else {
					ActivityOperatorNode tExecActivityOperatorNode = new ActivityOperatorNode(
							ActivityID, tProcessIDcurrent, tVersioncurrent);

					if (tExecActivityOperatorNode.ExecuteMission(MissionID,
							SubMissionID, mInputData)) {
						VData tVData = tExecActivityOperatorNode.getResult();
						if (tVData != null) {
//							VData xVData=(VData)tVData.get(0);
//							MMap map = (MMap) tVData.getObjectByObjectName("MMap", i);
							mResult.add(tVData);
						}
					} else {
						// @@错误处理
						this.mErrors
								.copyAllErrors(tExecActivityOperatorNode.mErrors);
						return false;
					}
				}
			}

		}
		// 对于多起点的情况需要特殊处理,把多个lwmission 的missionid统一

		if (multStartFlag) {
			String tMissionID = "";
			for (int i = 0; i < mResult.size(); i++) {
				MMap map = new MMap();
				map = (MMap) ((VData) mResult.getObjectByObjectName("VData", i))
						.get(0);
				LWMissionSchema tLWMissionSchema = new LWMissionSchema();
				tLWMissionSchema = (LWMissionSchema) map.getObjectByObjectName(
						"LWMissionSchema", 0);
				if (i == 0) {
					tMissionID = tLWMissionSchema.getMissionID();
				} else {
					tLWMissionSchema.setMissionID(tMissionID);
					map = new MMap();
					map.put(tLWMissionSchema, "INSERT");
					VData tVData = new VData();
					tVData.add(map);
					mResult.remove(i);
					mResult.add(tVData);
				}
			}
		}
		return true;
	}

	public boolean CreateStartMission(String ProcessID, String ActivityID,
			VData InputData, String Version) {
		String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
		String tSubMissionID = "1";
		ActivityOperatorNode tstartActivityOperatorNode = new ActivityOperatorNode(
				ActivityID, ProcessID, Version);
		if (tstartActivityOperatorNode.Create(tMissionID, tSubMissionID,
				InputData)) {
			VData tVData = tstartActivityOperatorNode.getResult();
			if (tVData != null) {
				mResult.add(tVData);
			}
		} else { // @@错误处理
			this.mErrors.copyAllErrors(tstartActivityOperatorNode.mErrors);
			return false;
		}
		return true;
	}

	private boolean prepareOutputData() {
		
		try {
			MMap tmap = new MMap();
			for (int i = 0; i < mResult.size(); i++) {
				VData tData = (VData) mResult.get(i);
				for (int n = 0; n < tData.size(); n++) {
					MMap map = (MMap) tData.getObjectByObjectName("MMap", n);
					tmap.add(map);
				}
			}

			
//			VData tData = (VData) mResult.getObjectByObjectName("VData", 0);
//			for (int i = 0; i < tData.size(); i++) {
////				VData hData = (VData) tData.get(i);
//				MMap map = (MMap) tData.getObjectByObjectName("MMap", i);
//				tmap.add(map);
//			}
			tResult = new VData();
			tResult.add(tmap);
			mResult.clear();
			mResult.add(mTransferData);
			//System.out.println("ss");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput mGlobalInput = new GlobalInput();
		TransferData mTransferData = new TransferData();

		/** 全局变量 */
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "86";
		mGlobalInput.ManageCom = "86";
		WorkFlowUI tWorkFlowUI = new WorkFlowUI();
		// 起始节点测试
		/*
		 * //业务类型 mTransferData.setNameAndValue("BusiType","3003"); //其他数据
		 * mTransferData.setNameAndValue("PrtNo","20110503000008");
		 * tVData.add(mGlobalInput); tVData.add(mTransferData); WorkFlowUI
		 * tWorkFlowUI = new WorkFlowUI(); if(!tWorkFlowUI.submitData(tVData,
		 * "create")) {
		 * logger.debug("tWorkFlowUI:"+tWorkFlowUI.mErrors.getFirstError()); }
		 */

		// 过程中节点测试
		// 活动节点ID
//		mTransferData.setNameAndValue("ActivityID", "30030004");
//		// MissionID
//		mTransferData.setNameAndValue("MissionID", "00000000000000921600");
//		// SubMissionID
//		mTransferData.setNameAndValue("SubMissionID", "1");
//		// 其他数据
//		mTransferData.setNameAndValue("PrtNo", "20110503000008");
//		tVData.add(mGlobalInput);
//		tVData.add(mTransferData);
//		
//		if (!tWorkFlowUI.submitData(tVData, "execute")) {
//			logger.debug("tWorkFlowUI:" + tWorkFlowUI.mErrors.getFirstError());
//		}
		//3003
		mTransferData.setNameAndValue("BusiType", "3003");
		mTransferData.setNameAndValue("ActivityID", "30030004");
//		// MissionID
		mTransferData.setNameAndValue("MissionID", "00000000000000931352");
//		// SubMissionID
		mTransferData.setNameAndValue("SubMissionID", "1");
		
		//00000000000000931339
		
		tVData.add(mGlobalInput);
		tVData.add(mTransferData);
		if(!tWorkFlowUI.submitData(tVData, "execute"))
		{
			System.out.println(tWorkFlowUI.getErrors().getFirstError());
		}
		//tWorkFlowUI.submitData(tVData, "create");
		
		
	}
}
