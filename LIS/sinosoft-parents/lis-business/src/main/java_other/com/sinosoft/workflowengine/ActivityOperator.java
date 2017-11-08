package com.sinosoft.workflowengine;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBMissionDB;
import com.sinosoft.lis.db.LWActivityDB;
import com.sinosoft.lis.db.LWFieldMapDB;
import com.sinosoft.lis.db.LWLockDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LWProcessDB;
import com.sinosoft.lis.db.LWProcessTransDB;
import com.sinosoft.lis.db.LWTransTimeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LWActivitySchema;
import com.sinosoft.lis.schema.LWLockSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.LWProcessSchema;
import com.sinosoft.lis.schema.LWProcessTransSchema;
import com.sinosoft.lis.schema.LWTransTimeSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWActivitySet;
import com.sinosoft.lis.vschema.LWFieldMapSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LWProcessSet;
import com.sinosoft.lis.vschema.LWProcessTransSet;
import com.sinosoft.lis.workflowmanage.WorkCalendarService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:工作流公共处理接口类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author sxy
 * @version 1.0
 */

public class ActivityOperator {
private static Logger logger = Logger.getLogger(ActivityOperator.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private Reflections mReflections = new Reflections();

	// public LWProcessSchema mLWProcessSchema=new LWProcessSchema();
	// public LWLockSchema mLWLockSchema=new LWLockSchema();

	private ActivityOperator mActivityOperator[];

	public static void main(String[] args) {

		VData tVData = new VData();
		GlobalInput mGlobalInput = new GlobalInput();
		TransferData mTransferData = new TransferData();

		/** 全局变量 */
		mGlobalInput.Operator = "Admin";
		mGlobalInput.ComCode = "asd";
		mGlobalInput.ManageCom = "sdd";
		/** 传递变量 */
		mTransferData.setNameAndValue("MissionID", "00000000000000000001");
		mTransferData.setNameAndValue("Default2", "1");
		/** 总变量 */
		tVData.add(mGlobalInput);
		tVData.add(mTransferData);

		ActivityOperator tActivityOperator = new ActivityOperator();
		// 测试用例1:ok
		// LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		// tLWMissionSchema =
		// tActivityOperator.CreateStartMission("0000000000","0000000000",tVData);
		// 测试用例2:ok
		// LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		// tLWMissionSchema =
		// tActivityOperator.CreateStartMission("0000000000",tVData);

		// //测试用例3:转移条件为SQL ok
		// LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		// try{
		// if(
		// tActivityOperator.CreateNextMission("00000000000000000001","","0000000000","0000000001",tVData))
		// {
		// tLWMissionSchema =
		// (LWMissionSchema)tActivityOperator.getResult().get(0) ;
		// }
		// }
		// catch (Exception ex) {
		// ex.printStackTrace();
		// }

		// //测试用例4:转移条件为CLASS ok
		LWMissionSet tLWMissionSet = new LWMissionSet();
		try {
			if (tActivityOperator.CreateNextMission("00000000000000000001", "",
					"0000000000", tVData)) {
				tLWMissionSet = (LWMissionSet) tActivityOperator.getResult()
						.get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// 测试用例5:执行工作节点任务
		// LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		// try{
		// if(tActivityOperator.ExecuteMission("0000000000",tVData))
		// {
		// VData tResult = new VData();
		// tResult = tActivityOperator.getResult() ;
		// }
		// }
		// catch (Exception ex) {
		// ex.printStackTrace();
		// }

		int i = 1;
		// tActivityOperator.ActivityFinished() ;
	}

	public ActivityOperator() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean ActivityFinished() {
		// 此处调用Finished后的相关工作流内部逻辑和操作
		// 1。调用BeforeEnd函数，进行相关条件判断,输入参数为
		// 1）当前的任务轨迹的结构
		// 2）当前锁的相关信息
		// 3）相关工作流的描述数据（这些数据由于是描述的，考虑通过缓存类来提高效率）
		if (BeforeEnd()) {
			if (AfterEnd()) {
				TransferAllActivity();
			} else {
			}
		} else {
			return false;
		}
		return true;
	}

	public boolean AfterInit() {

		return true;
	}

	public boolean TransferAllActivity() {
		boolean tAuto = true;
		// 此处应该是一个循环
		for (int i = 0; i < 10; i++) {
			mActivityOperator[0].AfterInit();
			// 如果是需要自动执行的活动，则执行下面的代码。
			if (tAuto == true) {
				// mActivityOperator[0].ExecBL() ;
				mActivityOperator[0].ActivityFinished();
			}
		}
		return true;
	}

	/**
	 * 在A任务结束的时候，调用下面的逻辑（在同一个事务中）//暂时取消
	 * 
	 * @return boolean
	 */
	public boolean AfterEnd() {
		// 1。结束A，释放锁（任务A.Finished开始）
		// （如果有的话）
		// 2。复制产生B，B2的数据
		// 3。产生锁B（任务B.AfterInit）,B2（任务B2.AfterInit）
		return true;
	}

	/**
	 * 任务结束前需要执行的代码
	 * 
	 * @return boolean
	 */
	public boolean BeforeEnd() {
		// 初始化所有符合条件的后续活动的结构，此处应该是一个循环
		mActivityOperator[0] = new ActivityOperator();

		return true;
	}

	/**
	 * 创建一个工作流的新起点任务 在该方法中需要完成2件事情： 1。创建一个工作流新的起点任务
	 * 2。对该新创建的任务加上“任务A.Finished开始”锁(暂时取消) 执行完成后，产生一个VData的输出，为BLS的保存做准备。--YT
	 * 
	 * @param tProcessID
	 *            String
	 * @param tInputData
	 *            VData
	 * @return LWMissionSchema
	 */
	// public LWMissionSchema CreateStartMission(String tProcessID,
	// VData tInputData)
	// {
	// //获取前台数据
	// GlobalInput tGlobalInput = new GlobalInput();
	// TransferData tTransferData = new TransferData();
	// tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
	// "GlobalInput", 0);
	// tTransferData = (TransferData) tInputData.getObjectByObjectName(
	// "TransferData", 0);
	//
	// LWMissionSchema tLWMissionSchema = new LWMissionSchema();
	//
	// //校验数据有效性
	// if (tProcessID == null)
	// {
	// // @@错误处理
	// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
	// CError tError = new CError();
	// tError.moduleName = "ActivityOperator";
	// tError.functionName = "CreateNewMission";
	// tError.errorMessage = "创建一个工作流的默认新起点任务,但没有传入工作流编码信息!";
	// this.mErrors.addOneError(tError);
	// return null;
	// }
	//
	// //查询工作流过程表
	// LWProcessDB tLWProcessDB = new LWProcessDB();
	// LWProcessSet tLWProcessSet = new LWProcessSet();
	// tLWProcessDB.setProcessID(tProcessID);
	// tLWProcessSet = tLWProcessDB.query();
	// if (tLWProcessSet.size() != 1)
	// {
	// // @@错误处理
	// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
	// CError tError = new CError();
	// tError.moduleName = "ActivityOperator";
	// tError.functionName = "CreateNewMission";
	// tError.errorMessage = "创建一个工作流的默认新起点任务,但传入的工作流编码信息有误!";
	// this.mErrors.addOneError(tError);
	// return null;
	// }
	//
	// //查询默认工作流起点的流转表(工作流过程实例表)
	// LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
	// LWProcessTransSet tLWProcessTransSet = new LWProcessTransSet();
	// tLWProcessTransDB.setProcessID(tLWProcessSet.get(1).getProcessID());
	// tLWProcessTransDB.setStartType("0"); //0为默认起点标识
	// tLWProcessTransSet = tLWProcessTransDB.query();
	// if (tLWProcessTransSet.size() == 0)
	// {
	// // @@错误处理
	// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
	// CError tError = new CError();
	// tError.moduleName = "ActivityOperator";
	// tError.functionName = "CreateNewMission";
	// tError.errorMessage = "创建一个工作流的默认新起点任务,但默认工作流起点不唯一,查询工作流流转表出错!";
	// this.mErrors.addOneError(tError);
	// return null;
	// }
	//
	// //查询默认工作流起点节点表(工作流活动表)
	// LWActivityDB tLWActivityDB = new LWActivityDB();
	// LWActivitySet tLWActivitySet = new LWActivitySet();
	// tLWActivityDB.setActivityID(tLWProcessTransSet.get(1).
	// getTransitionStart());
	// tLWActivitySet = tLWActivityDB.query();
	// if (tLWActivitySet.size() != 1)
	// {
	// // @@错误处理
	// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
	// CError tError = new CError();
	// tError.moduleName = "ActivityOperator";
	// tError.functionName = "CreateNewMission";
	// tError.errorMessage = "创建一个工作流的默认新起点任务,查询工作流节点表出错!";
	// this.mErrors.addOneError(tError);
	// return null;
	// }
	//
	// //校验传入参数
	// LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
	// LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
	// tLWFieldMapDB.setActivityID(tLWActivitySet.get(1).getActivityID());
	// tLWFieldMapSet = tLWFieldMapDB.query();
	// if (tLWFieldMapSet.size() != 0 && tTransferData == null)
	// {
	// // @@错误处理
	// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
	// CError tError = new CError();
	// tError.moduleName = "ActivityOperator";
	// tError.functionName = "CreateNewMission";
	// tError.errorMessage =
	// "创建一个工作流的默认新起点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!";
	// this.mErrors.addOneError(tError);
	// return null;
	// }
	//
	// //产生工作流起点任务,准备数据
	// String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
	// tLWMissionSchema.setMissionID(tMissionID);
	// tLWMissionSchema.setSubMissionID("1");
	// tLWMissionSchema.setProcessID(tLWProcessSet.get(1).getProcessID());
	// tLWMissionSchema.setActivityID(tLWActivitySet.get(1).getActivityID());
	// tLWMissionSchema.setActivityStatus("1"); //0 --
	// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
	// -- 任务产生完毕待处理，2 -- 处理中，3 -- 处理完成，4 -- 暂停
	// //准备属性字段
	// for (int i = 1; i <= tLWFieldMapSet.size(); i++)
	// {
	// if (tLWFieldMapSet.get(i).getSourFieldName() == null ||
	// tLWFieldMapSet.get(i).getSourFieldName().equals(""))
	// {
	// // @@错误处理
	// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
	// CError tError = new CError();
	// tError.moduleName = "ActivityOperator";
	// tError.functionName = "CreateNewMission";
	// tError.errorMessage =
	// "创建一个工作流的默认新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
	// this.mErrors.addOneError(tError);
	// return null;
	// }
	// if (tLWFieldMapSet.get(i).getDestFieldName() == null ||
	// tLWFieldMapSet.get(i).getDestFieldName().equals(""))
	// {
	// // @@错误处理
	// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
	// CError tError = new CError();
	// tError.moduleName = "ActivityOperator";
	// tError.functionName = "CreateNewMission";
	// tError.errorMessage =
	// "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
	// this.mErrors.addOneError(tError);
	// return null;
	// }
	// if (tTransferData.getValueByName(tLWFieldMapSet.get(i).
	// getSourFieldName()) == null)
	// {
	// // @@错误处理
	// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
	// CError tError = new CError();
	// tError.moduleName = "ActivityOperator";
	// tError.functionName = "CreateNewMission";
	// tError.errorMessage = "创建一个工作流的默认新起点任务,传入工作流起点任务的属性字段信息不足!";
	// this.mErrors.addOneError(tError);
	// return null;
	// }
	//
	// String tMissionProp = (String) tTransferData.getValueByName(
	// tLWFieldMapSet.get(i).getSourFieldName());
	// String tDestFieldName = tLWFieldMapSet.get(i).getDestFieldName();
	// tLWMissionSchema.setV(tDestFieldName, tMissionProp);
	//
	// }
	// //tLWMissionSchema.setDefaultOperator();
	// tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
	// tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
	// tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
	// tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
	// tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
	// tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
	//
	// //NewLWMissionSchema.setSchema(tLWMissionSchema);
	// return tLWMissionSchema;
	// }
	public LWMissionSet CreateStartMission(String tProcessID,
			String[] tActivityId, VData tInputData) {
		// 获取前台数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);
		LWMissionSet tLWMissionSet = new LWMissionSet();
		logger.debug("--ComCode--" + mGlobalInput.ComCode);
		logger.debug("--Operator--" + mGlobalInput.Operator);
		logger.debug("--ManageCom--" + mGlobalInput.ManageCom);

		// 校验数据有效性
		if (tProcessID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的默认新起点任务,但没有传入工作流编码信息!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 查询工作流过程表
		LWProcessDB tLWProcessDB = new LWProcessDB();
		LWProcessSet tLWProcessSet = new LWProcessSet();
		tLWProcessDB.setProcessID(tProcessID);
		tLWProcessSet = tLWProcessDB.query();
		if (tLWProcessSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的默认新起点任务,但传入的工作流编码信息有误!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 查询默认工作流起点的流转表(工作流过程实例表)
		LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
		LWProcessTransSet tLWProcessTransSet = new LWProcessTransSet();
		tLWProcessTransDB.setProcessID(tLWProcessSet.get(1).getProcessID());
		tLWProcessTransDB.setStartType("0"); // 0为默认起点标识
		tLWProcessTransSet = tLWProcessTransDB.query();
		if (tLWProcessTransSet.size() == 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的默认新起点任务,但默认工作流起点不唯一,查询工作流流转表出错!";
			this.mErrors.addOneError(tError);
			return null;
		}

		String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
		// for(int index = 0;index<tLWProcessTransSet.size();index++){
		for (int index = 0; index < tActivityId.length; index++) {
			// 查询默认工作流起点节点表(工作流活动表)
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LWActivityDB tLWActivityDB = new LWActivityDB();
			LWActivitySet tLWActivitySet = new LWActivitySet();
			// tLWActivityDB.setActivityID(tLWProcessTransSet.get(index +
			// 1).getTransitionStart());
			tLWActivityDB.setActivityID(tActivityId[index]);
			tLWActivitySet = tLWActivityDB.query();
			if (tLWActivitySet.size() != 1) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的默认新起点任务,查询工作流节点表出错!";
				this.mErrors.addOneError(tError);
				return null;
			}

			// 校验传入参数
			LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
			LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
			tLWFieldMapDB.setActivityID(tLWActivitySet.get(1).getActivityID());
			tLWFieldMapSet = tLWFieldMapDB.query();
			if (tLWFieldMapSet.size() != 0 && tTransferData == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的默认新起点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!";
				this.mErrors.addOneError(tError);
				return null;
			}

			// 产生工作流起点任务,准备数据
			// String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
			tLWMissionSchema.setMissionID(tMissionID);
//////////////////////////////////工作流升级///////////////////////////////////			
			tLWMissionSchema.setMainMissionID(tMissionID);
//////////////////////////////////////////////////////////////////////////////
			tLWMissionSchema.setSubMissionID("1");
			tLWMissionSchema.setProcessID(tLWProcessSet.get(1).getProcessID());
			tLWMissionSchema.setActivityID(tLWActivitySet.get(1)
					.getActivityID());
			tLWMissionSchema.setActivityStatus("1"); // 0 --
			// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
			// -- 任务产生完毕待处理，2 -- 处理中，3
			// -- 处理完成，4 -- 暂停
			// 准备属性字段
			for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
				if (tLWFieldMapSet.get(i).getSourFieldName() == null
						|| tLWFieldMapSet.get(i).getSourFieldName().equals("")) {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "创建一个工作流的默认新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
					this.mErrors.addOneError(tError);
					return null;
				}
				if (tLWFieldMapSet.get(i).getDestFieldName() == null
						|| tLWFieldMapSet.get(i).getDestFieldName().equals("")) {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
					this.mErrors.addOneError(tError);
					return null;
				}
				if (tTransferData.getValueByName(tLWFieldMapSet.get(i)
						.getSourFieldName()) == null) {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "创建一个工作流的默认新起点任务,传入工作流起点任务的属性字段信息不足!";
					this.mErrors.addOneError(tError);
					return null;
				}

				String tMissionProp = (String) tTransferData
						.getValueByName(tLWFieldMapSet.get(i)
								.getSourFieldName());
				String tDestFieldName = tLWFieldMapSet.get(i)
						.getDestFieldName();
				logger.debug(tDestFieldName + ":" + tMissionProp);
				tLWMissionSchema.setV(tDestFieldName, tMissionProp);

			}
//////////////////////////////////工作流升级///////////////////////////////////
			String tBusiType = (String) tTransferData.getValueByName("BusiType");
			String tBusiInDate = (String) tTransferData.getValueByName("BusiInDate");
			String tBusiInTime = (String) tTransferData.getValueByName("BusiInTime");
			if (tBusiInDate == null || tBusiInDate.equals(""))
			{ //如果业务进入日期未指定则置为系统当前日期
				tBusiInDate = PubFun.getCurrentDate();
			}
			if (tBusiInTime == null || tBusiInTime.equals(""))
			{ //如果业务进入时间未指定则置为系统当前时间
				tBusiInTime = PubFun.getCurrentTime();
			}
			tLWMissionSchema.setInDate(tBusiInDate); 
			tLWMissionSchema.setInTime(tBusiInTime); 
			
			String tStandEndDate = "";
			String tStandEndTime = "";
			//获得timeid
			String tTimeID = tLWProcessSet.get(1).getTimeID(); //时效编码
			//取得TimeFlag和TransitionTime
			if(tTimeID!=null&&!tTimeID.equals(""))
			{
				LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();
				tLWTransTimeDB.setTimeID(tTimeID);
				tLWTransTimeDB.setProcessID(tLWMissionSchema.getProcessID());
				tLWTransTimeDB.setActivityID(tLWMissionSchema.getActivityID());
				if (!tLWTransTimeDB.getInfo())
				{
//					CError tError = new CError();
//					tError.moduleName = "ActivityOperator";
//					tError.functionName = "CreateNewMission";
//					tError.errorMessage = "没有找到流程" + tProcessID + "定义的时效信息!";
//					this.mErrors.addOneError(tError);
//					return null;
				}
				else
				{
					LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
					tLWTransTimeSchema = tLWTransTimeDB.getSchema();
					String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); //时效类型
					String tTransitionTime = tLWTransTimeSchema.getTransitionTime(); //时效
					//计算标准结束时间
					TransferData standTransferData = WorkCalendarService.
							getStandEndTimes(tBusiInDate, tBusiInTime, tTimeFlag, tTransitionTime);
					if (standTransferData == null)
					{
						CError tError = new CError();
						tError.moduleName = "ActivityOperator";
						tError.functionName = "CreateNewMission";
						tError.errorMessage = "计算标准结束时间失败!";
						this.mErrors.addOneError(tError);
						return null;
					}
					String tCalFlag = (String) standTransferData.getValueByName("CalFlag");
					if (tCalFlag.equals("Y"))
					{
						tStandEndDate = (String) standTransferData.getValueByName("StandEndDate");
						tStandEndTime = (String) standTransferData.getValueByName("StandEndTime");
					}
					else
					{
						tStandEndDate = "";
						tStandEndTime = "";
					}
					tLWMissionSchema.setTimeID(tTimeID);
				}
			}
			tLWMissionSchema.setStandEndDate(tStandEndDate);
			tLWMissionSchema.setStandEndTime(tStandEndTime);
			String tDefaultOperator = (String) tTransferData.getValueByName("DefaultOperator");
			if (tDefaultOperator != null && (!tDefaultOperator.equals("")))
			{ //如果业务进入日期未指定则置为系统当前日期
				tLWMissionSchema.setDefaultOperator(tDefaultOperator);
			}
//////////////////////////////////////////////////////////////////////////////
			tLWMissionSchema.setOperateCom(tGlobalInput.ManageCom);
			tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
			tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
			tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
			tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
			tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
			tLWMissionSet.add(tLWMissionSchema);
		}
		logger.debug("---tLWMissionSet.size()---" + tLWMissionSet.size());
		return tLWMissionSet;
	}

	/**
	 * 创建一个工作流的指定起点任务 在该方法中需要完成2件事情： 1。创建一个工作流新的起点任务
	 * 2。对该新创建的任务加上“任务A.Finished开始”锁(暂时取消) 执行完成后，产生一个VData的输出，为BLS的保存做准备。--YT
	 * 
	 * @param tProcessID
	 *            String
	 * @param tActivityID
	 *            String
	 * @param tInputData
	 *            VData
	 * @return boolean
	 */
	public boolean CreateStartMission(String tProcessID, String tActivityID,
			VData tInputData) {
		// 获取前台数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);

		LWMissionSchema tLWMissionSchema = new LWMissionSchema();

		// 校验数据有效性
		if (tProcessID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但没有传入工作流编码信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tActivityID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但没有传入工作流活动节点信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询工作流过程表
		LWProcessDB tLWProcessDB = new LWProcessDB();
		LWProcessSet tLWProcessSet = new LWProcessSet();
		tLWProcessDB.setProcessID(tProcessID);
		tLWProcessSet = tLWProcessDB.query();
		if (tLWProcessSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但传入的工作流编码信息有误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 查询指定工作流起点的流转表(工作流过程实例表)
		LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
		LWProcessTransSet tLWProcessTransSet = new LWProcessTransSet();
		String tLWProcessTransSQL = new String();
		tLWProcessTransSQL = "select * from LWProcessTrans where "
				+ " ProcessID ='" + "?ProcessID?" + "'"
				+ " and TransitionStart = '" + "?tActivityID?" + "'"
				+ " and StartType <>'2'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tLWProcessTransSQL);
		sqlbv1.put("ProcessID", tLWProcessSet.get(1).getProcessID());
		sqlbv1.put("tActivityID", tActivityID);
		tLWProcessTransSet = tLWProcessTransDB
				.executeQuery(sqlbv1);
		logger.debug("sql=" + tLWProcessTransSQL);
		if (tLWProcessTransSet == null || tLWProcessTransSet.size() == 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,查询工作流流转表出错!";
			this.mErrors.addOneError(tError);
			return false;

		}

		// 查询默认工作流起点节点表(工作流活动表)
		LWActivityDB tLWActivityDB = new LWActivityDB();
		LWActivitySet tLWActivitySet = new LWActivitySet();
		tLWActivityDB.setActivityID(tActivityID);
		tLWActivitySet = tLWActivityDB.query();
		if (tLWActivitySet == null || tLWActivitySet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLWActivitySet.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,查询工作流节点表出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 校验传入参数
		LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
		LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
		tLWFieldMapDB.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWFieldMapSet = tLWFieldMapDB.query();
		if (tLWFieldMapSet.size() == 0 && tTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 产生工作流起点任务,准备数据
		String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
		tLWMissionSchema.setMissionID(tMissionID);
//////////////////////////////////工作流升级///////////////////////////////////		
		tLWMissionSchema.setMainMissionID(tMissionID);
//////////////////////////////////////////////////////////////////////////////		
		tLWMissionSchema.setSubMissionID("1");
		tLWMissionSchema.setProcessID(tLWProcessSet.get(1).getProcessID());
		tLWMissionSchema.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWMissionSchema.setActivityStatus("1"); // 0 --
		// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
		// -- 任务产生完毕待处理，2 -- 处理中，3 --
		// 处理完成，4 -- 暂停
		// 准备属性字段
		for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
			if (tLWFieldMapSet.get(i).getSourFieldName() == null
					|| tLWFieldMapSet.get(i).getSourFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return false;
			}

			if (tLWFieldMapSet.get(i).getDestFieldName() == null
					|| tLWFieldMapSet.get(i).getDestFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tTransferData.getValueByName(tLWFieldMapSet.get(i)
					.getSourFieldName()) == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段信息不足!";
				this.mErrors.addOneError(tError);
				return false;
			}

			String tMissionProp = (String) tTransferData
					.getValueByName(tLWFieldMapSet.get(i).getSourFieldName());
			String tDestFieldName = tLWFieldMapSet.get(i).getDestFieldName();
			// logger.debug("tMissionProp:"+tMissionProp
			// +"tDestFieldName"+tDestFieldName) ;

			tLWMissionSchema.setV(tDestFieldName, tMissionProp);

		}
//////////////////////////////////工作流升级///////////////////////////////////
		String tBusiType = (String) tTransferData.getValueByName("BusiType");
		String tBusiInDate = (String) tTransferData.getValueByName("BusiInDate");
		String tBusiInTime = (String) tTransferData.getValueByName("BusiInTime");
		if (tBusiInDate == null || tBusiInDate.equals(""))
		{ //如果业务进入日期未指定则置为系统当前日期
			tBusiInDate = PubFun.getCurrentDate();
		}
		if (tBusiInTime == null || tBusiInTime.equals(""))
		{ //如果业务进入时间未指定则置为系统当前时间
			tBusiInTime = PubFun.getCurrentTime();
		}
		tLWMissionSchema.setInDate(tBusiInDate); 
		tLWMissionSchema.setInTime(tBusiInTime); 
		
		String tStandEndDate = "";
		String tStandEndTime = "";
		//获得timeid
		String tTimeID = tLWProcessSet.get(1).getTimeID(); //时效编码
		//取得TimeFlag和TransitionTime
		if(tTimeID!=null&&!tTimeID.equals(""))
		{
			LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();
			tLWTransTimeDB.setTimeID(tTimeID);
			tLWTransTimeDB.setProcessID(tLWMissionSchema.getProcessID());
			tLWTransTimeDB.setActivityID(tLWMissionSchema.getActivityID());
			if (!tLWTransTimeDB.getInfo())
			{
//				CError tError = new CError();
//				tError.moduleName = "ActivityOperator";
//				tError.functionName = "CreateNewMission";
//				tError.errorMessage = "没有找到流程" + tProcessID + "定义的时效信息!";
//				this.mErrors.addOneError(tError);
//				return false;
			}
			else
			{
				LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
				tLWTransTimeSchema = tLWTransTimeDB.getSchema();
				String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); //时效类型
				String tTransitionTime = tLWTransTimeSchema.getTransitionTime(); //时效
				//计算标准结束时间
				TransferData standTransferData = WorkCalendarService.
						getStandEndTimes(tBusiInDate, tBusiInTime, tTimeFlag, tTransitionTime);
				if (standTransferData == null)
				{
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "计算标准结束时间失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				String tCalFlag = (String) standTransferData.getValueByName("CalFlag");
				if (tCalFlag.equals("Y"))
				{
					tStandEndDate = (String) standTransferData.getValueByName("StandEndDate");
					tStandEndTime = (String) standTransferData.getValueByName("StandEndTime");
				}
				else
				{
					tStandEndDate = "";
					tStandEndTime = "";
				}
				tLWMissionSchema.setTimeID(tTimeID);
			}
		}
		tLWMissionSchema.setStandEndDate(tStandEndDate);
		tLWMissionSchema.setStandEndTime(tStandEndTime);
		String tDefaultOperator = (String) tTransferData.getValueByName("DefaultOperator");
		if (tDefaultOperator != null && (!tDefaultOperator.equals("")))
		{ //如果业务进入日期未指定则置为系统当前日期
			tLWMissionSchema.setDefaultOperator(tDefaultOperator);
		}
//////////////////////////////////////////////////////////////////////////////
		tLWMissionSchema.setOperateCom(tGlobalInput.ManageCom);
		tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
		tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
		tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
		tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
		tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());

		// NewLWMissionSchema.setSchema(tLWMissionSchema);
		mResult.clear();
		if (tLWMissionSchema != null) {
			MMap map = new MMap();
			map.put(tLWMissionSchema, "INSERT");
			mResult.add(map);
		}
		logger.debug("Mission.size()---" + mResult.size());
		return true;
	}

	/**
	 * 创建工作流起点：扫描双岗录入
	 * */
	public LWMissionSchema CreateOneMissionForDS(String tProcessID,
			String tActivityID, VData tInputData) {
//		 获取前台数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);

		LWMissionSchema tLWMissionSchema = new LWMissionSchema();

		// 校验数据有效性
		if (tProcessID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但没有传入工作流编码信息!";
			this.mErrors.addOneError(tError);
			return null;
		}
		if (tActivityID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但没有传入工作流活动节点信息!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 查询工作流过程表
		LWProcessDB tLWProcessDB = new LWProcessDB();
		LWProcessSet tLWProcessSet = new LWProcessSet();
		tLWProcessDB.setProcessID(tProcessID);
		tLWProcessSet = tLWProcessDB.query();
		if (tLWProcessSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但传入的工作流编码信息有误!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 查询指定工作流起点的流转表(工作流过程实例表)
		LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
		LWProcessTransSet tLWProcessTransSet = new LWProcessTransSet();
		String tLWProcessTransSQL = new String();
		tLWProcessTransSQL = "select * from LWProcessTrans where "
				+ " ProcessID ='" + "?ProcessID?" + "'"
				+ " and TransitionStart = '" + "?tActivityID?" + "'"
				+ " and StartType <>'2'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tLWProcessTransSQL);
		sqlbv2.put("ProcessID", tLWProcessSet.get(1).getProcessID());
		sqlbv2.put("tActivityID", tActivityID);
		tLWProcessTransSet = tLWProcessTransDB
				.executeQuery(sqlbv2);
		logger.debug("sql=" + tLWProcessTransSQL);
		if (tLWProcessTransSet == null || tLWProcessTransSet.size() == 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,查询工作流流转表出错!";
			this.mErrors.addOneError(tError);
			return null;

		}

		// 查询默认工作流起点节点表(工作流活动表)
		LWActivityDB tLWActivityDB = new LWActivityDB();
		LWActivitySet tLWActivitySet = new LWActivitySet();
		tLWActivityDB.setActivityID(tActivityID);
		tLWActivitySet = tLWActivityDB.query();
		if (tLWActivitySet == null || tLWActivitySet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLWActivitySet.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,查询工作流节点表出错!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 校验传入参数
		LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
		LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
		tLWFieldMapDB.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWFieldMapSet = tLWFieldMapDB.query();
		if (tLWFieldMapSet.size() != 0 && tTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 产生工作流起点任务,准备数据
		String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
		tLWMissionSchema.setMissionID(tMissionID);
//////////////////////////////////工作流升级///////////////////////////////////		
		tLWMissionSchema.setMainMissionID(tMissionID);
//////////////////////////////////////////////////////////////////////////////			
		tLWMissionSchema.setSubMissionID("1");
		tLWMissionSchema.setProcessID(tLWProcessSet.get(1).getProcessID());
		tLWMissionSchema.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWMissionSchema.setActivityStatus("1"); // 0 --
		// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
		// -- 任务产生完毕待处理，2 -- 处理中，3 --
		// 处理完成，4 -- 暂停
		tLWMissionSchema.setMissionProp6("0");//录入次数为0
		//tLWMissionSchema.setMissionProp9(tGlobalInput.ManageCom);
		// 准备属性字段
		for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
			if (tLWFieldMapSet.get(i).getSourFieldName() == null
					|| tLWFieldMapSet.get(i).getSourFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return null;
			}

			if (tLWFieldMapSet.get(i).getDestFieldName() == null
					|| tLWFieldMapSet.get(i).getDestFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return null;
			}
			if (tTransferData.getValueByName(tLWFieldMapSet.get(i)
					.getSourFieldName()) == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段信息不足!";
				this.mErrors.addOneError(tError);
				return null;
			}

			String tMissionProp = (String) tTransferData
					.getValueByName(tLWFieldMapSet.get(i).getSourFieldName());
			String tDestFieldName = tLWFieldMapSet.get(i).getDestFieldName();
			// logger.debug("tMissionProp:"+tMissionProp
			// +"tDestFieldName"+tDestFieldName) ;

			tLWMissionSchema.setV(tDestFieldName, tMissionProp);

		}
//////////////////////////////////工作流升级///////////////////////////////////
		String tBusiType = (String) tTransferData.getValueByName("BusiType");
		String tBusiInDate = (String) tTransferData.getValueByName("BusiInDate");
		String tBusiInTime = (String) tTransferData.getValueByName("BusiInTime");
		if (tBusiInDate == null || tBusiInDate.equals(""))
		{ //如果业务进入日期未指定则置为系统当前日期
			tBusiInDate = PubFun.getCurrentDate();
		}
		if (tBusiInTime == null || tBusiInTime.equals(""))
		{ //如果业务进入时间未指定则置为系统当前时间
			tBusiInTime = PubFun.getCurrentTime();
		}
		tLWMissionSchema.setInDate(tBusiInDate); 
		tLWMissionSchema.setInTime(tBusiInTime); 
		
		String tStandEndDate = "";
		String tStandEndTime = "";
		//获得timeid
		String tTimeID = tLWProcessSet.get(1).getTimeID(); //时效编码
		//取得TimeFlag和TransitionTime
		if(tTimeID!=null&&!tTimeID.equals(""))
		{
			LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();
			tLWTransTimeDB.setTimeID(tTimeID);
			tLWTransTimeDB.setProcessID(tLWMissionSchema.getProcessID());
			tLWTransTimeDB.setActivityID(tLWMissionSchema.getActivityID());
			if (!tLWTransTimeDB.getInfo())
			{
//				CError tError = new CError();
//				tError.moduleName = "ActivityOperator";
//				tError.functionName = "CreateNewMission";
//				tError.errorMessage = "没有找到流程" + tProcessID + "定义的时效信息!";
//				this.mErrors.addOneError(tError);
//				return null;
			}
			else
			{
				LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
				tLWTransTimeSchema = tLWTransTimeDB.getSchema();
				String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); //时效类型
				String tTransitionTime = tLWTransTimeSchema.getTransitionTime(); //时效
				//计算标准结束时间
				TransferData standTransferData = WorkCalendarService.
						getStandEndTimes(tBusiInDate, tBusiInTime, tTimeFlag, tTransitionTime);
				if (standTransferData == null)
				{
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "计算标准结束时间失败!";
					this.mErrors.addOneError(tError);
					return null;
				}
				String tCalFlag = (String) standTransferData.getValueByName("CalFlag");
				if (tCalFlag.equals("Y"))
				{
					tStandEndDate = (String) standTransferData.getValueByName("StandEndDate");
					tStandEndTime = (String) standTransferData.getValueByName("StandEndTime");
				}
				else
				{
					tStandEndDate = "";
					tStandEndTime = "";
				}
				tLWMissionSchema.setTimeID(tTimeID);
			}
		}
		tLWMissionSchema.setStandEndDate(tStandEndDate);
		tLWMissionSchema.setStandEndTime(tStandEndTime);
		String tDefaultOperator = (String) tTransferData.getValueByName("DefaultOperator");
		if (tDefaultOperator != null && (!tDefaultOperator.equals("")))
		{ //如果业务进入日期未指定则置为系统当前日期
			tLWMissionSchema.setDefaultOperator(tDefaultOperator);
		}
//////////////////////////////////////////////////////////////////////////////
		tLWMissionSchema.setOperateCom(tGlobalInput.ManageCom);
		tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
		tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
		tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
		tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
		tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());

		logger.debug("ok!:");
		return tLWMissionSchema;
	}
	
	// ====ADD=====zhangtao=====2005-04-22=================BGN=====================
	/**
	 * 创建一个工作流的指定起点任务[无扫描录入申请] 在该方法中需要完成3件事情： 1.创建一个工作流新的起点任务
	 * 2.对该新创建的任务加上“任务A.Finished开始”锁(暂时取消) 3.设置该任务的默认处理人为当前申请人，记录开始进入时间
	 * 执行完成后，产生一个VData的输出，为BLS的保存做准备。--YT
	 * 
	 * @param tProcessID
	 *            String
	 * @param tActivityID
	 *            String
	 * @param tInputData
	 *            VData
	 * @return boolean
	 */
	public boolean CreateStartMission_NoScan(String tProcessID,
			String tActivityID, VData tInputData) {
		// 获取前台数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);

		LWMissionSchema tLWMissionSchema = new LWMissionSchema();

		// 校验数据有效性
		if (tProcessID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但没有传入工作流编码信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tActivityID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但没有传入工作流活动节点信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询工作流过程表
		LWProcessDB tLWProcessDB = new LWProcessDB();
		LWProcessSet tLWProcessSet = new LWProcessSet();
		tLWProcessDB.setProcessID(tProcessID);
		tLWProcessSet = tLWProcessDB.query();
		if (tLWProcessSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但传入的工作流编码信息有误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 查询指定工作流起点的流转表(工作流过程实例表)
		LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
		LWProcessTransSet tLWProcessTransSet = new LWProcessTransSet();
		String tLWProcessTransSQL = new String();
		tLWProcessTransSQL = "select * from LWProcessTrans where "
				+ " ProcessID ='" + "?ProcessID?" + "'"
				+ " and TransitionStart = '" + "?tActivityID?" + "'"
				+ " and StartType <>'2'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tLWProcessTransSQL);
		sqlbv3.put("ProcessID", tLWProcessSet.get(1).getProcessID());
		sqlbv3.put("tActivityID", tActivityID);
		tLWProcessTransSet = tLWProcessTransDB
				.executeQuery(sqlbv3);
		logger.debug("sql=" + tLWProcessTransSQL);
		if (tLWProcessTransSet == null || tLWProcessTransSet.size() == 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,查询工作流流转表出错!";
			this.mErrors.addOneError(tError);
			return false;

		}

		// 查询默认工作流起点节点表(工作流活动表)
		LWActivityDB tLWActivityDB = new LWActivityDB();
		LWActivitySet tLWActivitySet = new LWActivitySet();
		tLWActivityDB.setActivityID(tActivityID);
		tLWActivitySet = tLWActivityDB.query();
		if (tLWActivitySet == null || tLWActivitySet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLWActivitySet.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,查询工作流节点表出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 校验传入参数
		LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
		LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
		tLWFieldMapDB.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWFieldMapSet = tLWFieldMapDB.query();
		if (tLWFieldMapSet.size() == 0 && tTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 产生工作流起点任务,准备数据
		String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
		tLWMissionSchema.setMissionID(tMissionID);
//////////////////////////////////工作流升级///////////////////////////////////		
		tLWMissionSchema.setMainMissionID(tMissionID);
//////////////////////////////////////////////////////////////////////////////		
		tLWMissionSchema.setSubMissionID("1");
		tLWMissionSchema.setProcessID(tLWProcessSet.get(1).getProcessID());
		tLWMissionSchema.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWMissionSchema.setActivityStatus("1"); // 0 --
		tLWMissionSchema.setVERSION("0");
		// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
		// -- 任务产生完毕待处理，2 -- 处理中，3 --
		// 处理完成，4 -- 暂停
		// 准备属性字段
		for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
			if (tLWFieldMapSet.get(i).getSourFieldName() == null
					|| tLWFieldMapSet.get(i).getSourFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return false;
			}

			if (tLWFieldMapSet.get(i).getDestFieldName() == null
					|| tLWFieldMapSet.get(i).getDestFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tTransferData.getValueByName(tLWFieldMapSet.get(i)
					.getSourFieldName()) == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段信息不足!";
				this.mErrors.addOneError(tError);
				return false;
			}

			String tMissionProp = (String) tTransferData
					.getValueByName(tLWFieldMapSet.get(i).getSourFieldName());
			String tDestFieldName = tLWFieldMapSet.get(i).getDestFieldName();
			 logger.debug("tMissionProp:"+tMissionProp
			 +"tDestFieldName"+tDestFieldName) ;

			tLWMissionSchema.setV(tDestFieldName, tMissionProp);

		}
//////////////////////////////////工作流升级///////////////////////////////////
		String tBusiType = (String) tTransferData.getValueByName("BusiType");
		String tBusiInDate = (String) tTransferData.getValueByName("BusiInDate");
		String tBusiInTime = (String) tTransferData.getValueByName("BusiInTime");
		if (tBusiInDate == null || tBusiInDate.equals(""))
		{ //如果业务进入日期未指定则置为系统当前日期
			tBusiInDate = PubFun.getCurrentDate();
		}
		if (tBusiInTime == null || tBusiInTime.equals(""))
		{ //如果业务进入时间未指定则置为系统当前时间
			tBusiInTime = PubFun.getCurrentTime();
		}
		tLWMissionSchema.setInDate(tBusiInDate); 
		tLWMissionSchema.setInTime(tBusiInTime); 
		
		String tStandEndDate = "";
		String tStandEndTime = "";
		//获得timeid
		String tTimeID = tLWProcessSet.get(1).getTimeID(); //时效编码
		//取得TimeFlag和TransitionTime
		if(tTimeID!=null&&!tTimeID.equals(""))
		{
			LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();
			tLWTransTimeDB.setTimeID(tTimeID);
			tLWTransTimeDB.setProcessID(tLWMissionSchema.getProcessID());
			tLWTransTimeDB.setActivityID(tLWMissionSchema.getActivityID());
			if (!tLWTransTimeDB.getInfo())
			{
//				CError tError = new CError();
//				tError.moduleName = "ActivityOperator";
//				tError.functionName = "CreateNewMission";
//				tError.errorMessage = "没有找到流程" + tProcessID + "定义的时效信息!";
//				this.mErrors.addOneError(tError);
//				return null;
			}
			else
			{
				LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
				tLWTransTimeSchema = tLWTransTimeDB.getSchema();
				String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); //时效类型
				String tTransitionTime = tLWTransTimeSchema.getTransitionTime(); //时效
				//计算标准结束时间
				TransferData standTransferData = WorkCalendarService.
						getStandEndTimes(tBusiInDate, tBusiInTime, tTimeFlag, tTransitionTime);
				if (standTransferData == null)
				{
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "计算标准结束时间失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				String tCalFlag = (String) standTransferData.getValueByName("CalFlag");
				if (tCalFlag.equals("Y"))
				{
					tStandEndDate = (String) standTransferData.getValueByName("StandEndDate");
					tStandEndTime = (String) standTransferData.getValueByName("StandEndTime");
				}
				else
				{
					tStandEndDate = "";
					tStandEndTime = "";
				}
				tLWMissionSchema.setTimeID(tTimeID);
			}
		}
		tLWMissionSchema.setStandEndDate(tStandEndDate);
		tLWMissionSchema.setStandEndTime(tStandEndTime);
		String tDefaultOperator = (String) tTransferData.getValueByName("DefaultOperator");
		if (tDefaultOperator != null && (!tDefaultOperator.equals("")))
		{ //如果业务进入日期未指定则置为系统当前日期
			tLWMissionSchema.setDefaultOperator(tDefaultOperator);
		}
//////////////////////////////////////////////////////////////////////////////
		tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
		tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
		tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
		tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
		tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
		tLWMissionSchema.setDefaultOperator(tGlobalInput.Operator);
		tLWMissionSchema.setInDate(tLWMissionSchema.getMakeDate());
		tLWMissionSchema.setInTime(tLWMissionSchema.getMakeTime());
		// NewLWMissionSchema.setSchema(tLWMissionSchema);
		mResult.clear();
		if (tLWMissionSchema != null) {
			MMap map = new MMap();

			if (tActivityID != null && tActivityID.equals("0000001098")) {
				String tSQL = "select * from lccont where prtno='"
						+ "?prtno?" + "'";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSQL);
				sqlbv4.put("prtno", tLWMissionSchema.getMissionProp1());
				map.put(sqlbv4, "SELECT");
				String bSQL = "select * from lwmission where activityid in ('0000001098','0000001099') and missionprop1 = '"
						+ "?missionprop1?" + "' ";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(bSQL);
				sqlbv5.put("missionprop1", tLWMissionSchema.getMissionProp1());
				map.put(sqlbv5, "SELECT");
			}
			map.put(tLWMissionSchema, "INSERT");
			mResult.add(map);
		}
		logger.debug("Mission.size()---" + mResult.size());
		return true;
	}

	// ====ADD=====zhangtao=====2005-04-22=================END=====================
	/**
	 * 创建一个工作流的指定起点任务 在该方法中需要完成2件事情： 1。创建一个工作流新的起点任务
	 * 2。对该新创建的任务加上“任务A.Finished开始”锁(暂时取消) 执行完成后，产生一个VData的输出，为BLS的保存做准备。--YT
	 * 
	 * @param tProcessID
	 *            String
	 * @param tActivityID
	 *            String
	 * @param tInputData
	 *            VData
	 * @return LWMissionSchema
	 */
	public LWMissionSchema CreateOneMission(String tProcessID,
			String tActivityID, VData tInputData) {
		// 获取前台数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);

		LWMissionSchema tLWMissionSchema = new LWMissionSchema();

		// 校验数据有效性
		if (tProcessID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但没有传入工作流编码信息!";
			this.mErrors.addOneError(tError);
			return null;
		}
		if (tActivityID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但没有传入工作流活动节点信息!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 查询工作流过程表
		LWProcessDB tLWProcessDB = new LWProcessDB();
		LWProcessSet tLWProcessSet = new LWProcessSet();
		tLWProcessDB.setProcessID(tProcessID);
		tLWProcessSet = tLWProcessDB.query();
		if (tLWProcessSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但传入的工作流编码信息有误!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 查询指定工作流起点的流转表(工作流过程实例表)
		LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
		LWProcessTransSet tLWProcessTransSet = new LWProcessTransSet();
		String tLWProcessTransSQL = new String();
		tLWProcessTransSQL = "select * from LWProcessTrans where "
				+ " ProcessID ='" + "?ProcessID?" + "'"
				+ " and TransitionStart = '" + "?tActivityID?" + "'"
				+ " and StartType <>'2'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tLWProcessTransSQL);
		sqlbv6.put("ProcessID", tLWProcessSet.get(1).getProcessID());
		sqlbv6.put("tActivityID", tActivityID);
		tLWProcessTransSet = tLWProcessTransDB
				.executeQuery(sqlbv6);
		logger.debug("sql=" + tLWProcessTransSQL);
		if (tLWProcessTransSet == null || tLWProcessTransSet.size() == 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,查询工作流流转表出错!";
			this.mErrors.addOneError(tError);
			return null;

		}

		// 查询默认工作流起点节点表(工作流活动表)
		LWActivityDB tLWActivityDB = new LWActivityDB();
		LWActivitySet tLWActivitySet = new LWActivitySet();
		tLWActivityDB.setActivityID(tActivityID);
		tLWActivitySet = tLWActivityDB.query();
		if (tLWActivitySet == null || tLWActivitySet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLWActivitySet.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,查询工作流节点表出错!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 校验传入参数
		LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
		LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
		tLWFieldMapDB.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWFieldMapSet = tLWFieldMapDB.query();
		if (tLWFieldMapSet.size() != 0 && tTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 产生工作流起点任务,准备数据
		String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
		tLWMissionSchema.setMissionID(tMissionID);
//////////////////////////////////工作流升级///////////////////////////////////		
		tLWMissionSchema.setMainMissionID(tMissionID);
//////////////////////////////////////////////////////////////////////////////				
		tLWMissionSchema.setSubMissionID("1");
		tLWMissionSchema.setProcessID(tLWProcessSet.get(1).getProcessID());
		tLWMissionSchema.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWMissionSchema.setActivityStatus("1"); // 0 --
		// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
		// -- 任务产生完毕待处理，2 -- 处理中，3 --
		// 处理完成，4 -- 暂停
		// 准备属性字段
		for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
			if (tLWFieldMapSet.get(i).getSourFieldName() == null
					|| tLWFieldMapSet.get(i).getSourFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return null;
			}

			if (tLWFieldMapSet.get(i).getDestFieldName() == null
					|| tLWFieldMapSet.get(i).getDestFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return null;
			}
			if (tTransferData.getValueByName(tLWFieldMapSet.get(i)
					.getSourFieldName()) == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段信息不足!";
				this.mErrors.addOneError(tError);
				return null;
			}

			String tMissionProp = (String) tTransferData
					.getValueByName(tLWFieldMapSet.get(i).getSourFieldName());
			String tDestFieldName = tLWFieldMapSet.get(i).getDestFieldName();
			// logger.debug("tMissionProp:"+tMissionProp
			// +"tDestFieldName"+tDestFieldName) ;

			tLWMissionSchema.setV(tDestFieldName, tMissionProp);

		}
//////////////////////////////////工作流升级///////////////////////////////////
		String tBusiType = (String) tTransferData.getValueByName("BusiType");
		String tBusiInDate = (String) tTransferData.getValueByName("BusiInDate");
		String tBusiInTime = (String) tTransferData.getValueByName("BusiInTime");
		if (tBusiInDate == null || tBusiInDate.equals(""))
		{ //如果业务进入日期未指定则置为系统当前日期
			tBusiInDate = PubFun.getCurrentDate();
		}
		if (tBusiInTime == null || tBusiInTime.equals(""))
		{ //如果业务进入时间未指定则置为系统当前时间
			tBusiInTime = PubFun.getCurrentTime();
		}
		tLWMissionSchema.setInDate(tBusiInDate); 
		tLWMissionSchema.setInTime(tBusiInTime); 
		
		String tStandEndDate = "";
		String tStandEndTime = "";
		//获得timeid
		String tTimeID = tLWProcessSet.get(1).getTimeID(); //时效编码
		//取得TimeFlag和TransitionTime
		if(tTimeID!=null&&!tTimeID.equals(""))
		{
			LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();
			tLWTransTimeDB.setTimeID(tTimeID);
			tLWTransTimeDB.setProcessID(tLWMissionSchema.getProcessID());
			tLWTransTimeDB.setActivityID(tLWMissionSchema.getActivityID());
			if (!tLWTransTimeDB.getInfo())
			{
//				CError tError = new CError();
//				tError.moduleName = "ActivityOperator";
//				tError.functionName = "CreateNewMission";
//				tError.errorMessage = "没有找到流程" + tProcessID + "定义的时效信息!";
//				this.mErrors.addOneError(tError);
//				return null;
			}
			else
			{
				LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
				tLWTransTimeSchema = tLWTransTimeDB.getSchema();
				String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); //时效类型
				String tTransitionTime = tLWTransTimeSchema.getTransitionTime(); //时效
				//计算标准结束时间
				TransferData standTransferData = WorkCalendarService.
						getStandEndTimes(tBusiInDate, tBusiInTime, tTimeFlag, tTransitionTime);
				if (standTransferData == null)
				{
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "计算标准结束时间失败!";
					this.mErrors.addOneError(tError);
					return null;
				}
				String tCalFlag = (String) standTransferData.getValueByName("CalFlag");
				if (tCalFlag.equals("Y"))
				{
					tStandEndDate = (String) standTransferData.getValueByName("StandEndDate");
					tStandEndTime = (String) standTransferData.getValueByName("StandEndTime");
				}
				else
				{
					tStandEndDate = "";
					tStandEndTime = "";
				}
				tLWMissionSchema.setTimeID(tTimeID);
			}
		}
		tLWMissionSchema.setStandEndDate(tStandEndDate);
		tLWMissionSchema.setStandEndTime(tStandEndTime);
		String tDefaultOperator = (String) tTransferData.getValueByName("DefaultOperator");
		if (tDefaultOperator != null && (!tDefaultOperator.equals("")))
		{ //如果业务进入日期未指定则置为系统当前日期
			tLWMissionSchema.setDefaultOperator(tDefaultOperator);
		}
//////////////////////////////////////////////////////////////////////////////
		tLWMissionSchema.setOperateCom(tGlobalInput.ManageCom);
		tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
		tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
		tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
		tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
		tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());

		logger.debug("ok!:");
		return tLWMissionSchema;
	}

	/**
	 * 创建一个相对当前节点的指定的下一节点任务
	 * 
	 * @param tMissionID
	 *            String 输入的当前 任务ID 数据
	 * @param tSourActivityID
	 *            String
	 * @param tDestActivityID
	 *            String
	 * @param tInputData
	 *            VData 输入的辅助数据
	 * @param tStr
	 *            String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean CreateNextMission(String tMissionID, String tSourActivityID,
			String tDestActivityID, VData tInputData, String tStr)
			throws Exception {
		// 获取前台数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);

		LWMissionSchema tLWMissionSchema = new LWMissionSchema();

		// 校验数据有效性
		if (tMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一任务节点,但没有传入工作流当前任务ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tDestActivityID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一任务节点,但没有传入指定活动的ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询工作流任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(tMissionID);
		tLWMissionDB.setActivityID(tSourActivityID);
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流任务轨迹表LWMission出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询指定活动节点表(工作流活动表)
		LWActivityDB tLWActivityDB = new LWActivityDB();
		LWActivitySet tLWActivitySet = new LWActivitySet();
		tLWActivityDB.setActivityID(tDestActivityID);
		tLWActivitySet = tLWActivityDB.query();
		if (tLWActivitySet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流活动节点表出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询工作流过程实例表
		LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
		LWProcessTransSet tLWProcessTransSet = new LWProcessTransSet();
		tLWProcessTransDB.setProcessID(tLWMissionSet.get(1).getProcessID());
		tLWProcessTransDB.setTransitionStart(tSourActivityID);
		tLWProcessTransDB.setTransitionEnd(tDestActivityID);
		tLWProcessTransSet = tLWProcessTransDB.query();
		if (tLWProcessTransSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流流转表出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 校验指定的转移是否满足转移条件
		if (!CheckTransitionCondition(tLWProcessTransSet.get(1), tInputData)) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一个任务节点,指定的转移不满足转移条件!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 校验传入参数
		LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
		LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
		tLWFieldMapDB.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWFieldMapSet = tLWFieldMapDB.query();
		if (tLWFieldMapSet.size() != 0 && tTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一节点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 产生工作流起点任务,准备数据
		// String tMissionID = PubFun1.CreateMaxNo( "MissionID", 20 );
		tLWMissionSchema.setMissionID(tMissionID);
//////////////////////////////////工作流升级///////////////////////////////////		
		tLWMissionSchema.setMainMissionID(tLWMissionSet.get(1).getMainMissionID());
//////////////////////////////////////////////////////////////////////////////			
		tLWMissionSchema.setSubMissionID("0");
		tLWMissionSchema.setProcessID(tLWMissionSet.get(1).getProcessID());
		tLWMissionSchema.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWMissionSchema.setActivityStatus("1"); // 0 --
		// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
		// -- 任务产生完毕待处理，2 -- 处理中，3 --
		// 处理完成，4 -- 暂停
		// 准备属性字段
		for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
			if (tLWFieldMapSet.get(i).getSourFieldName() == null
					|| tLWFieldMapSet.get(i).getSourFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的下一个任务节点,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLWFieldMapSet.get(i).getDestFieldName() == null
					|| tLWFieldMapSet.get(i).getDestFieldName().trim().equals(
							"")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的下一个任务节点,工作流活动的具体字段映射表中记录DestFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tTransferData.getValueByName(tLWFieldMapSet.get(i)
					.getSourFieldName()) == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的下一个任务节点,传入工作流起点任务的属性字段信息不足!";
				this.mErrors.addOneError(tError);
				return false;
			}

			String tMissionProp = (String) tTransferData
					.getValueByName(tLWFieldMapSet.get(i).getSourFieldName());
			String tDestFieldName = tLWFieldMapSet.get(i).getDestFieldName();
			tLWMissionSchema.setV(tDestFieldName, tMissionProp);

		}
//////////////////////////////////工作流升级///////////////////////////////////
		String tBusiType = (String) tTransferData.getValueByName("BusiType");
		String tBusiInDate = (String) tTransferData.getValueByName("BusiInDate");
		String tBusiInTime = (String) tTransferData.getValueByName("BusiInTime");
		if (tBusiInDate == null || tBusiInDate.equals(""))
		{ //如果业务进入日期未指定则置为系统当前日期
			tBusiInDate = PubFun.getCurrentDate();
		}
		if (tBusiInTime == null || tBusiInTime.equals(""))
		{ //如果业务进入时间未指定则置为系统当前时间
			tBusiInTime = PubFun.getCurrentTime();
		}
		tLWMissionSchema.setInDate(tBusiInDate); 
		tLWMissionSchema.setInTime(tBusiInTime); 
		
		String tStandEndDate = "";
		String tStandEndTime = "";
		//获得timeid
		LWProcessDB tLWProcessDB = new LWProcessDB();//
		tLWProcessDB.setProcessID(tLWMissionSchema.getProcessID());
		if (!tLWProcessDB.getInfo())
		{
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "没有找到流程" + tLWMissionSchema.getProcessID() + "定义的时效编码!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LWProcessSchema tLWProcessSchema = tLWProcessDB.getSchema();
		String tTimeID = tLWProcessSchema.getTimeID(); //时效编码
		
		//取得TimeFlag和TransitionTime
		if(tTimeID!=null&&!tTimeID.equals(""))
		{
			LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();
			tLWTransTimeDB.setTimeID(tTimeID);
			tLWTransTimeDB.setProcessID(tLWMissionSchema.getProcessID());
			tLWTransTimeDB.setActivityID(tLWMissionSchema.getActivityID());
			if (!tLWTransTimeDB.getInfo())
			{
//				CError tError = new CError();
//				tError.moduleName = "ActivityOperator";
//				tError.functionName = "CreateNewMission";
//				tError.errorMessage = "没有找到流程" + tLWMissionSchema.getProcessID() + "定义的时效信息!";
//				this.mErrors.addOneError(tError);
//				return false;
			}
			else
			{
				LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
				tLWTransTimeSchema = tLWTransTimeDB.getSchema();
				String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); //时效类型
				String tTransitionTime = tLWTransTimeSchema.getTransitionTime(); //时效
				//计算标准结束时间
				TransferData standTransferData = WorkCalendarService.
						getStandEndTimes(tBusiInDate, tBusiInTime, tTimeFlag, tTransitionTime);
				if (standTransferData == null)
				{
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "计算标准结束时间失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				String tCalFlag = (String) standTransferData.getValueByName("CalFlag");
				if (tCalFlag.equals("Y"))
				{
					tStandEndDate = (String) standTransferData.getValueByName("StandEndDate");
					tStandEndTime = (String) standTransferData.getValueByName("StandEndTime");
				}
				else
				{
					tStandEndDate = "";
					tStandEndTime = "";
				}
				tLWMissionSchema.setTimeID(tTimeID);
			}
		}
		tLWMissionSchema.setStandEndDate(tStandEndDate);
		tLWMissionSchema.setStandEndTime(tStandEndTime);
		String tDefaultOperator = (String) tTransferData.getValueByName("DefaultOperator");
		if (tDefaultOperator != null && (!tDefaultOperator.equals("")))
		{ //如果业务进入日期未指定则置为系统当前日期
			tLWMissionSchema.setDefaultOperator(tDefaultOperator);
		}
//////////////////////////////////////////////////////////////////////////////		
		
		tLWMissionSchema.setOperateCom(tGlobalInput.ManageCom);
		tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
		tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
		tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
		tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
		tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());

		mResult.clear();
		mResult.add(tLWMissionSchema);
		return true;
	}

	/**
	 * 创建一个相对当前节点的所有满足转移条件的下一节点任务
	 * 
	 * @param tMissionID
	 *            String
	 * @param tSubMissionID
	 *            String
	 * @param tSourActivityID
	 *            String
	 * @param tInputData
	 *            VData
	 * @return boolean
	 * @throws Exception
	 */
	public boolean CreateNextMission(String tMissionID, String tSubMissionID,
			String tSourActivityID, VData tInputData) throws Exception {
		// 获取前台数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);

		LWMissionSet tNewLWMissionResultSet = new LWMissionSet(); // 新产生的任务节点
		// LWMissionSet tOldLWMissionResultSet = new LWMissionSet();//旧的任务节点
		// 校验数据有效性
		if (tMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一任务节点,但没有传入工作流当前任务ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSourActivityID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一任务节点,但没有传入工作流当前活动ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询工作流任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(tMissionID);
		tLWMissionDB.setActivityID(tSourActivityID);
		tLWMissionDB.setSubMissionID(tSubMissionID);
		tLWMissionSet = tLWMissionDB.query();

		if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流任务轨迹表LWMission出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询工作流过程实例表
		LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
		LWProcessTransSet tLWProcessTransSet = new LWProcessTransSet();
		tLWProcessTransDB.setProcessID(tLWMissionSet.get(1).getProcessID());
		tLWProcessTransDB.setTransitionStart(tLWMissionSet.get(1)
				.getActivityID());
		tLWProcessTransSet = tLWProcessTransDB.query();
		if (tLWProcessTransSet == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流流转表出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLWProcessTransSet.size() < 1) {
			return true;
		}

		// 准备所有下一节点任务的数据
		for (int j = 1; j <= tLWProcessTransSet.size(); j++) {
			// 查询指定活动节点表(工作流活动表)
			String tActivityID = tLWProcessTransSet.get(j)
					.getTransitionEnd();
			if (tActivityID == null || tActivityID.trim().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNextMission";
				tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流转移节点表ActivityID数据出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LWActivityDB tLWActivityDB = new LWActivityDB();
			LWActivitySet tLWActivitySet = new LWActivitySet();
			tLWActivityDB.setActivityID(tActivityID);
			tLWActivitySet = tLWActivityDB.query();
			if (tLWActivitySet == null || tLWActivitySet.size() != 1) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNextMission";
				tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流活动节点表出错!";
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("-------------------in");
			// 校验指定的转移是否满足转移条件
			if (CheckTransitionCondition(tLWProcessTransSet.get(j),
					tInputData)) {

				// 校验W表已存在该任务节点数量
				int tLWSize = 0;
				int tLBSize = 0;
				LWMissionDB tempLWMissionDB = new LWMissionDB();
				LWMissionSet tempLWMissionSet = new LWMissionSet();
				tempLWMissionDB.setMissionID(tMissionID);
				tempLWMissionDB.setActivityID(tActivityID);
				tempLWMissionSet = tempLWMissionDB.query();
				if (tempLWMissionSet != null) {
					tLWSize = tempLWMissionSet.size();
				} else {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNextMission";
					tError.errorMessage = "创建一个工作流的下一节点任务,查询任务轨迹表失败!";
					this.mErrors.addOneError(tError);
					return false;

				}
				// //校验B表已存在该任务节点数量
				LBMissionDB tempLBMissionDB = new LBMissionDB();
				LBMissionSet tempLBMissionSet = new LBMissionSet();
				tempLBMissionDB.setMissionID(tMissionID);
				tempLBMissionDB.setActivityID(tActivityID);
				tempLBMissionSet = tempLBMissionDB.query();
				if (tempLBMissionSet != null) {
					tLBSize = tempLBMissionSet.size();
				} else {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNextMission";
					tError.errorMessage = "创建一个工作流的下一节点任务,查询任务轨迹备份表失败!";
					this.mErrors.addOneError(tError);
					return false;

				}

				// 该任务节点是首次产生
				LWMissionSchema tLWMissionSchema = new LWMissionSchema();
				// 校验传入参数
				LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
				LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
				tLWFieldMapDB.setActivityID(tLWActivitySet.get(1)
						.getActivityID());
				tLWFieldMapSet = tLWFieldMapDB.query();
				if (tLWFieldMapSet == null
						|| (tLWFieldMapSet.size() != 0 && tTransferData == null)) {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNextMission";
					tError.errorMessage = "创建一个工作流的下一节点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!";
					this.mErrors.addOneError(tError);
					return false;
				}

				// 产生工作流起点任务,准备数据
				tLWMissionSchema.setMissionID(tMissionID);
//////////////////////////////////工作流升级///////////////////////////////////		
				tLWMissionSchema.setMainMissionID(tLWMissionSet.get(1).getMainMissionID());
//////////////////////////////////////////////////////////////////////////////							
				tLWMissionSchema.setSubMissionID(String.valueOf(tLWSize
						+ tLBSize + 1));
				tLWMissionSchema.setProcessID(tLWMissionSet.get(1)
						.getProcessID());
				tLWMissionSchema.setActivityID(tActivityID);
				tLWMissionSchema.setActivityStatus("1"); // 0 --
				// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
				// -- 任务产生完毕待处理，2 --
				// 处理中，3 -- 处理完成，4 --
				// 暂停
				// 准备属性字段
				for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
					if (tLWFieldMapSet.get(i).getSourFieldName() == null
							|| tLWFieldMapSet.get(i).getSourFieldName().equals(
									"")) {
						// @@错误处理
						// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
						CError tError = new CError();
						tError.moduleName = "ActivityOperator";
						tError.functionName = "CreateNewMission";
						tError.errorMessage = "创建一个工作流的下一个任务节点,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
						this.mErrors.addOneError(tError);
						return false;
					}
					if (tLWFieldMapSet.get(i).getDestFieldName() == null
							|| tLWFieldMapSet.get(i).getDestFieldName().trim()
									.equals("")) {
						// @@错误处理
						// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
						CError tError = new CError();
						tError.moduleName = "ActivityOperator";
						tError.functionName = "CreateNewMission";
						tError.errorMessage = "创建一个工作流的下一个任务节点,工作流活动的具体字段映射表中记录DestFieldName字段描述有误!";
						this.mErrors.addOneError(tError);
						return false;
					}

					// if(
					// tTransferData.getValueByName(tLWFieldMapSet.get(i).getSourFieldName())
					// == null )
					// {
					// // @@错误处理
					// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					// CError tError = new CError();
					// tError.moduleName = "ActivityOperator";
					// tError.functionName = "CreateNewMission";
					// tError.errorMessage =
					// "创建一个工作流的下一个任务节点,传入工作流起点任务的属性字段信息不足!";
					// this.mErrors.addOneError(tError) ;
					// return false;
					// }

					String tMissionProp = (String) tTransferData
							.getValueByName(tLWFieldMapSet.get(i)
									.getSourFieldName());
					String tDestFieldName = tLWFieldMapSet.get(i)
							.getDestFieldName();
					tLWMissionSchema.setV(tDestFieldName, tMissionProp);

				}
//////////////////////////////////工作流升级///////////////////////////////////
				String tBusiType = (String) tTransferData.getValueByName("BusiType");
				String tBusiInDate = (String) tTransferData.getValueByName("BusiInDate");
				String tBusiInTime = (String) tTransferData.getValueByName("BusiInTime");
				if (tBusiInDate == null || tBusiInDate.equals(""))
				{ //如果业务进入日期未指定则置为系统当前日期
					tBusiInDate = PubFun.getCurrentDate();
				}
				if (tBusiInTime == null || tBusiInTime.equals(""))
				{ //如果业务进入时间未指定则置为系统当前时间
					tBusiInTime = PubFun.getCurrentTime();
				}
				tLWMissionSchema.setInDate(tBusiInDate); 
				tLWMissionSchema.setInTime(tBusiInTime); 
				
				String tStandEndDate = "";
				String tStandEndTime = "";
				//获得timeid
				LWProcessDB tLWProcessDB = new LWProcessDB();//
				tLWProcessDB.setProcessID(tLWMissionSchema.getProcessID());
				tLWProcessDB.setVERSION(tLWMissionSchema.getVERSION()==null?"0":tLWMissionSchema.getVERSION());
				if (!tLWProcessDB.getInfo())
				{
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "没有找到流程" + tLWMissionSchema.getProcessID() + "定义的时效编码!";
					this.mErrors.addOneError(tError);
					return false;
				}
				LWProcessSchema tLWProcessSchema = tLWProcessDB.getSchema();
				String tTimeID = tLWProcessSchema.getTimeID(); //时效编码
				
				//取得TimeFlag和TransitionTime
				if(tTimeID!=null&&!tTimeID.equals(""))
				{
					LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();
					tLWTransTimeDB.setTimeID(tTimeID);
					tLWTransTimeDB.setProcessID(tLWMissionSchema.getProcessID());
					tLWTransTimeDB.setActivityID(tLWMissionSchema.getActivityID());
					if (!tLWTransTimeDB.getInfo())
					{
//						CError tError = new CError();
//						tError.moduleName = "ActivityOperator";
//						tError.functionName = "CreateNewMission";
//						tError.errorMessage = "没有找到流程" + tLWMissionSchema.getProcessID() + "定义的时效信息!";
//						this.mErrors.addOneError(tError);
//						return false;
					}
					else
					{
						LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
						tLWTransTimeSchema = tLWTransTimeDB.getSchema();
						String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); //时效类型
						String tTransitionTime = tLWTransTimeSchema.getTransitionTime(); //时效
						//计算标准结束时间
						TransferData standTransferData = WorkCalendarService.
								getStandEndTimes(tBusiInDate, tBusiInTime, tTimeFlag, tTransitionTime);
						if (standTransferData == null)
						{
							CError tError = new CError();
							tError.moduleName = "ActivityOperator";
							tError.functionName = "CreateNewMission";
							tError.errorMessage = "计算标准结束时间失败!";
							this.mErrors.addOneError(tError);
							return false;
						}
						String tCalFlag = (String) standTransferData.getValueByName("CalFlag");
						if (tCalFlag.equals("Y"))
						{
							tStandEndDate = (String) standTransferData.getValueByName("StandEndDate");
							tStandEndTime = (String) standTransferData.getValueByName("StandEndTime");
						}
						else
						{
							tStandEndDate = "";
							tStandEndTime = "";
						}
						tLWMissionSchema.setTimeID(tTimeID);
					}
				}
				tLWMissionSchema.setStandEndDate(tStandEndDate);
				tLWMissionSchema.setStandEndTime(tStandEndTime);
				String tDefaultOperator = (String) tTransferData.getValueByName("DefaultOperator");
				if (tDefaultOperator != null && (!tDefaultOperator.equals("")))
				{ //如果业务进入日期未指定则置为系统当前日期
					tLWMissionSchema.setDefaultOperator(tDefaultOperator);
				}
//////////////////////////////////////////////////////////////////////////////
				tLWMissionSchema.setOperateCom(tGlobalInput.ManageCom);				
				tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
				tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
				tLWMissionSchema.setInDate(PubFun.getCurrentDate());
				tLWMissionSchema.setInTime(PubFun.getCurrentTime());
				tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
				tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
				tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
				tLWMissionSchema.setVERSION(tLWProcessSchema.getVERSION());
				tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());

				tNewLWMissionResultSet.add(tLWMissionSchema);
				tLWMissionSchema = null;

			}

		}

		mResult.clear();
		MMap map = new MMap();

		if (tNewLWMissionResultSet != null && tNewLWMissionResultSet.size() > 0) {
			map.put(tNewLWMissionResultSet, "INSERT");
		}
		if (map != null && map.keySet().size() > 0) {
			mResult.add(map);
		}

		return true;
	}

	/**
	 * 创建一个相对当前节点的所有满足转移条件的下一节点任务，并直接置给指定操作人
	 * 
	 * @param tMissionID
	 *            String
	 * @param tSubMissionID
	 *            String
	 * @param tSourActivityID
	 *            String
	 * @param tInputData
	 *            VData
	 * @param tOperator
	 *            String 需要指定的操作人
	 * @return boolean
	 * @throws Exception
	 * @version 为满足理赔特定节点需要直接指定操作人的功能 2005-11-10 (周磊)
	 */
	public boolean CreateNextMissionSpecial(String tMissionID,
			String tSubMissionID, String tSourActivityID, VData tInputData,
			String tOperator) throws Exception {
		// 获取前台数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);

		LWMissionSet tNewLWMissionResultSet = new LWMissionSet(); // 新产生的任务节点
		// LWMissionSet tOldLWMissionResultSet = new LWMissionSet();//旧的任务节点
		// 校验数据有效性
		if (tMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一任务节点,但没有传入工作流当前任务ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSourActivityID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一任务节点,但没有传入工作流当前活动ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询工作流任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(tMissionID);
		tLWMissionDB.setActivityID(tSourActivityID);
		tLWMissionDB.setSubMissionID(tSubMissionID);
		tLWMissionSet = tLWMissionDB.query();

		if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流任务轨迹表LWMission出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询工作流过程实例表
		LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
		LWProcessTransSet tLWProcessTransSet = new LWProcessTransSet();
		tLWProcessTransDB.setProcessID(tLWMissionSet.get(1).getProcessID());
		tLWProcessTransDB.setTransitionStart(tLWMissionSet.get(1)
				.getActivityID());
		tLWProcessTransSet = tLWProcessTransDB.query();
		if (tLWProcessTransSet == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNextMission";
			tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流流转表出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLWProcessTransSet.size() < 1) {
			return true;
		}

		// 准备所有下一节点任务的数据
		for (int j = 1; j <= tLWProcessTransSet.size(); j++) {
			// 查询指定活动节点表(工作流活动表)
			String tActivityID = tLWProcessTransSet.get(j)
					.getTransitionEnd();
			if (tActivityID == null || tActivityID.trim().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNextMission";
				tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流转移节点表ActivityID数据出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LWActivityDB tLWActivityDB = new LWActivityDB();
			LWActivitySet tLWActivitySet = new LWActivitySet();
			tLWActivityDB.setActivityID(tActivityID);
			tLWActivitySet = tLWActivityDB.query();
			if (tLWActivitySet == null || tLWActivitySet.size() != 1) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNextMission";
				tError.errorMessage = "创建一个工作流的下一个任务节点,查询工作流活动节点表出错!";
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("-------------------in");
			// 校验指定的转移是否满足转移条件
			if (CheckTransitionCondition(tLWProcessTransSet.get(j),
					tInputData)) {

				// 校验W表已存在该任务节点数量
				int tLWSize = 0;
				int tLBSize = 0;
				LWMissionDB tempLWMissionDB = new LWMissionDB();
				LWMissionSet tempLWMissionSet = new LWMissionSet();
				tempLWMissionDB.setMissionID(tMissionID);
				tempLWMissionDB.setActivityID(tActivityID);
				tempLWMissionSet = tempLWMissionDB.query();
				if (tempLWMissionSet != null) {
					tLWSize = tempLWMissionSet.size();
				} else {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNextMission";
					tError.errorMessage = "创建一个工作流的下一节点任务,查询任务轨迹表失败!";
					this.mErrors.addOneError(tError);
					return false;

				}
				// //校验B表已存在该任务节点数量
				LBMissionDB tempLBMissionDB = new LBMissionDB();
				LBMissionSet tempLBMissionSet = new LBMissionSet();
				tempLBMissionDB.setMissionID(tMissionID);
				tempLBMissionDB.setActivityID(tActivityID);
				tempLBMissionSet = tempLBMissionDB.query();
				if (tempLBMissionSet != null) {
					tLBSize = tempLBMissionSet.size();
				} else {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNextMission";
					tError.errorMessage = "创建一个工作流的下一节点任务,查询任务轨迹备份表失败!";
					this.mErrors.addOneError(tError);
					return false;

				}

				// 该任务节点是首次产生
				LWMissionSchema tLWMissionSchema = new LWMissionSchema();
				// 校验传入参数
				LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
				LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
				tLWFieldMapDB.setActivityID(tLWActivitySet.get(1)
						.getActivityID());
				tLWFieldMapSet = tLWFieldMapDB.query();
				if (tLWFieldMapSet == null
						|| (tLWFieldMapSet.size() != 0 && tTransferData == null)) {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNextMission";
					tError.errorMessage = "创建一个工作流的下一节点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!";
					this.mErrors.addOneError(tError);
					return false;
				}

				// 产生工作流起点任务,准备数据
				tLWMissionSchema.setMissionID(tMissionID);
//////////////////////////////////工作流升级///////////////////////////////////		
				tLWMissionSchema.setMainMissionID(tLWMissionSet.get(1).getMainMissionID());
//////////////////////////////////////////////////////////////////////////////							
				tLWMissionSchema.setSubMissionID(String.valueOf(tLWSize
						+ tLBSize + 1));
				tLWMissionSchema.setProcessID(tLWMissionSet.get(1)
						.getProcessID());
				tLWMissionSchema.setActivityID(tActivityID);
				tLWMissionSchema.setActivityStatus("1"); // 0 --
				// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
				// -- 任务产生完毕待处理，2 --
				// 处理中，3 -- 处理完成，4 --
				// 暂停
				// 准备属性字段
				for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
					if (tLWFieldMapSet.get(i).getSourFieldName() == null
							|| tLWFieldMapSet.get(i).getSourFieldName().equals(
									"")) {
						// @@错误处理
						// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
						CError tError = new CError();
						tError.moduleName = "ActivityOperator";
						tError.functionName = "CreateNewMission";
						tError.errorMessage = "创建一个工作流的下一个任务节点,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
						this.mErrors.addOneError(tError);
						return false;
					}
					if (tLWFieldMapSet.get(i).getDestFieldName() == null
							|| tLWFieldMapSet.get(i).getDestFieldName().trim()
									.equals("")) {
						// @@错误处理
						// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
						CError tError = new CError();
						tError.moduleName = "ActivityOperator";
						tError.functionName = "CreateNewMission";
						tError.errorMessage = "创建一个工作流的下一个任务节点,工作流活动的具体字段映射表中记录DestFieldName字段描述有误!";
						this.mErrors.addOneError(tError);
						return false;
					}

					// if(
					// tTransferData.getValueByName(tLWFieldMapSet.get(i).getSourFieldName())
					// == null )
					// {
					// // @@错误处理
					// //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					// CError tError = new CError();
					// tError.moduleName = "ActivityOperator";
					// tError.functionName = "CreateNewMission";
					// tError.errorMessage =
					// "创建一个工作流的下一个任务节点,传入工作流起点任务的属性字段信息不足!";
					// this.mErrors.addOneError(tError) ;
					// return false;
					// }

					String tMissionProp = (String) tTransferData
							.getValueByName(tLWFieldMapSet.get(i)
									.getSourFieldName());
					String tDestFieldName = tLWFieldMapSet.get(i)
							.getDestFieldName();
					tLWMissionSchema.setV(tDestFieldName, tMissionProp);

				}
//////////////////////////////////工作流升级///////////////////////////////////
				String tBusiType = (String) tTransferData.getValueByName("BusiType");
				String tBusiInDate = (String) tTransferData.getValueByName("BusiInDate");
				String tBusiInTime = (String) tTransferData.getValueByName("BusiInTime");
				if (tBusiInDate == null || tBusiInDate.equals(""))
				{ //如果业务进入日期未指定则置为系统当前日期
					tBusiInDate = PubFun.getCurrentDate();
				}
				if (tBusiInTime == null || tBusiInTime.equals(""))
				{ //如果业务进入时间未指定则置为系统当前时间
					tBusiInTime = PubFun.getCurrentTime();
				}
				tLWMissionSchema.setInDate(tBusiInDate); 
				tLWMissionSchema.setInTime(tBusiInTime); 
				
				String tStandEndDate = "";
				String tStandEndTime = "";
				//获得timeid
				LWProcessDB tLWProcessDB = new LWProcessDB();//
				tLWProcessDB.setProcessID(tLWMissionSchema.getProcessID());
				tLWProcessDB.setVERSION("0");
				if (!tLWProcessDB.getInfo())
				{
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "没有找到流程" + tLWMissionSchema.getProcessID() + "定义的时效编码!";
					this.mErrors.addOneError(tError);
					return false;
				}
				LWProcessSchema tLWProcessSchema = tLWProcessDB.getSchema();
				String tTimeID = tLWProcessSchema.getTimeID(); //时效编码
				
				//取得TimeFlag和TransitionTime
				if(tTimeID!=null&&!tTimeID.equals(""))
				{
					LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();
					tLWTransTimeDB.setTimeID(tTimeID);
					tLWTransTimeDB.setProcessID(tLWMissionSchema.getProcessID());
					tLWTransTimeDB.setActivityID(tLWMissionSchema.getActivityID());
					if (!tLWTransTimeDB.getInfo())
					{
//						CError tError = new CError();
//						tError.moduleName = "ActivityOperator";
//						tError.functionName = "CreateNewMission";
//						tError.errorMessage = "没有找到流程" + tLWMissionSchema.getProcessID() + "定义的时效信息!";
//						this.mErrors.addOneError(tError);
//						return false;
					}
					else
					{
						LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
						tLWTransTimeSchema = tLWTransTimeDB.getSchema();
						String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); //时效类型
						String tTransitionTime = tLWTransTimeSchema.getTransitionTime(); //时效
						//计算标准结束时间
						TransferData standTransferData = WorkCalendarService.
								getStandEndTimes(tBusiInDate, tBusiInTime, tTimeFlag, tTransitionTime);
						if (standTransferData == null)
						{
							CError tError = new CError();
							tError.moduleName = "ActivityOperator";
							tError.functionName = "CreateNewMission";
							tError.errorMessage = "计算标准结束时间失败!";
							this.mErrors.addOneError(tError);
							return false;
						}
						String tCalFlag = (String) standTransferData.getValueByName("CalFlag");
						if (tCalFlag.equals("Y"))
						{
							tStandEndDate = (String) standTransferData.getValueByName("StandEndDate");
							tStandEndTime = (String) standTransferData.getValueByName("StandEndTime");
						}
						else
						{
							tStandEndDate = "";
							tStandEndTime = "";
						}
						tLWMissionSchema.setTimeID(tTimeID);
					}
				}
				tLWMissionSchema.setStandEndDate(tStandEndDate);
				tLWMissionSchema.setStandEndTime(tStandEndTime);
				String tDefaultOperator = (String) tTransferData.getValueByName("DefaultOperator");
				if (tDefaultOperator != null && (!tDefaultOperator.equals("")))
				{ //如果业务进入日期未指定则置为系统当前日期
					tLWMissionSchema.setDefaultOperator(tDefaultOperator);
				}
//////////////////////////////////////////////////////////////////////////////
				tLWMissionSchema.setOperateCom(tGlobalInput.ManageCom);				
				tLWMissionSchema.setDefaultOperator(tOperator);
				tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
				tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
				tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
				tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
				tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
				tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());

				tNewLWMissionResultSet.add(tLWMissionSchema);
				tLWMissionSchema = null;

			}

		}

		mResult.clear();
		MMap map = new MMap();

		if (tNewLWMissionResultSet != null && tNewLWMissionResultSet.size() > 0) {
			map.put(tNewLWMissionResultSet, "INSERT");
		}
		if (map != null && map.keySet().size() > 0) {
			mResult.add(map);
		}

		return true;
	}

	/**
	 * 校验转移条件是否满足
	 * 
	 * @param tLWProcessTransSchema
	 *            LWProcessTransSchema 输入的当前 工作流过程实例 数据
	 * @param tInputData
	 *            VData 输入的辅助数据
	 * @return boolean
	 * @throws Exception
	 */
	private boolean CheckTransitionCondition(
			LWProcessTransSchema tLWProcessTransSchema, VData tInputData)
			throws Exception {
		// 获取传入数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);
		// Connection conn = DBConnPool.getConnection();
		logger.debug("---------123");
		if (tLWProcessTransSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CheckTransitionCondition";
			tError.errorMessage = "tLWProcessTransSchema为空";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("---------3333123"
				+ tLWProcessTransSchema.getTransitionCondT());

		if (tLWProcessTransSchema.getTransitionCondT() == null
				|| tLWProcessTransSchema.getTransitionCondT().trim().equals(
						"")) {

			// 空----表示没有需要执行的动作。
			return true;
		} else if (tLWProcessTransSchema.getTransitionCondT().equals("0")) {
			// 0 -- 默认，表示条件为Where子句。
			logger.debug("---------aaaaa");
			PubCalculator tPubCalculator = new PubCalculator();
			// 准备计算要素
			Vector tVector = (Vector) tTransferData.getValueNames();
			for (int j = 0; j < tVector.size(); j++) {
				String ttName = (String) tVector.get(j);
				logger.debug("ttName=" + ttName);

			}

			logger.debug("i===" + tVector.size());
			for (int i = 0; i < tVector.size(); i++) {
				String tName = (String) tVector.get(i);

				// <!-- XinYQ modified on 2006-07-11 : 避免由于未传入对象和值而导致空指针异常 : BGN
				// -->
				// OLD: String tValue =
				// (String)tTransferData.getValueByName((Object)tName).toString();
				String tValue = new String("");
				Object tValueObject = tTransferData.getValueByName(tVector
						.get(i));
				if (tValueObject != null) {
					tValue = (String) tValueObject;
				}
				tValueObject = null;
				// <!-- XinYQ modified on 2006-07-11 : 避免由于未传入对象和值而导致空指针异常 : END
				// -->

				// logger.debug("tName=" + tName);
				// logger.debug("tValue=" + tValue);
				tPubCalculator.addBasicFactor(tName, tValue);
			}
			logger.debug("---------bbbbbb="
					+ tLWProcessTransSchema.getTransitionCond());
			// 准备计算SQL
			tPubCalculator.setCalSql(tLWProcessTransSchema
					.getTransitionCond());
			String strSQL = tPubCalculator.calculate();
			logger.debug("SQL : " + strSQL);
			if (strSQL == null || strSQL.trim().equals("")) {

				return true;
			} else {
				// 分解条件语句,逐条执行
				// Statement stmt = conn.createStatement();
				try {
					String strTemp = "";
					char cTemp = ' ';
					for (int nIndex = 0; nIndex < strSQL.length(); nIndex++) {
						cTemp = strSQL.charAt(nIndex);
						if (cTemp == ';' && !strTemp.equals("")) {
							logger.debug(strTemp);
							String tReSult = new String();
							ExeSQL tExeSQL = new ExeSQL();
							tReSult = tExeSQL.getOneValue(strTemp); // 转移条件满足时一定会有返回值,转移条件SQL只是Select语句
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
								CError tError = new CError();
								tError.moduleName = "ActivityOperator";
								tError.functionName = "CheckTransitionCondition";
								tError.errorMessage = "执行SQL语句：" + strTemp
										+ "失败!";
								this.mErrors.addOneError(tError);
								return false;
							}
							if (tReSult == null || tReSult.equals("")) { // 不满足转移条件
								return false;
							}
							strTemp = "";
						} else {
							strTemp += String.valueOf(cTemp);
						}
					} // end of for
					logger.debug("strTemp=" + strTemp);
					// stmt.close();
				} catch (Exception ex) {
					// stmt.close();
					ex.printStackTrace();
					return false;
				}
			}
			return true;
		} else if (tLWProcessTransSchema.getTransitionCondT().equals("1")) {
			// 1 -- 表示转移条件为一个特殊的类。
			try {
				Class tClass = Class.forName(tLWProcessTransSchema
						.getTransitionCond());
				TransCondService tTransCondService = (TransCondService) tClass
						.newInstance();

				// 准备数据
				String strOperate = "TransCondService";
				tInputData.add(tLWProcessTransSchema);
				if (!tTransCondService.submitData(tInputData, strOperate)) {
					return false;
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}

		}
		// if(!conn.isClosed())
		// {
		// conn.close();
		// }
		return true;
	}

	/**
	 * 执行节点任务
	 * 
	 * @param tMissionID
	 *            String
	 * @param tSubMissionID
	 *            String
	 * @param tActivityID
	 *            String
	 * @param tInputData
	 *            VData
	 * @return boolean
	 * @throws Exception
	 */
	public boolean ExecuteMission(String tMissionID, String tSubMissionID,
			String tActivityID, VData tInputData) throws Exception {
		mResult.clear();
		// 校验数据有效性
		if (tMissionID == null || tMissionID.equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,但没有传入当前任务ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null || tSubMissionID.equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,但没有传入当前任务子ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tActivityID == null || tActivityID.equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,但没有传入当前任务活动ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询工作流任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(tMissionID);
		tLWMissionDB.setSubMissionID(tSubMissionID);
		tLWMissionDB.setActivityID(tActivityID);
		tLWMissionSet = tLWMissionDB.query();

		if (tLWMissionSet == null || tLWMissionSet.size() == 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "不满足该节点任务的执行条件. ";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLWMissionSet.size() > 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,查询工作流任务轨迹表LWMission出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询指定活动节点表(工作流活动表)
		LWActivityDB tLWActivityDB = new LWActivityDB();
		LWActivitySet tLWActivitySet = new LWActivitySet();
		tLWActivityDB.setActivityID(tLWMissionSet.get(1).getActivityID());
		tLWActivitySet = tLWActivityDB.query();
		if (tLWActivitySet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,查询工作流活动节点表出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LWActivitySchema tLWActivitySchema = new LWActivitySchema();
		tLWActivitySchema.setSchema(tLWActivitySet.get(1));

		// 执行BeforeInit任务
		if (!ExecuteBeforeInitDuty(tLWActivitySchema, tInputData)) {

			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,执行BeforeInit任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行AfterInit任务
		if (!ExecuteAfterInitDuty(tLWActivitySchema, tInputData)) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,执行AfterInit任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行BeforeEnd任务
		if (!ExecuteBeforeEndDuty(tLWActivitySchema, tInputData)) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,执行BeforeEnd任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行AfterEnd任务
		if (!ExecuteAfterEndDuty(tLWActivitySchema, tInputData)) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,执行AfterEnd任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行虚拟节点任务
	 * 
	 * @param tLWMissionSchema
	 *            LWMissionSchema
	 * @param tInputData
	 *            VData
	 * @return boolean
	 * @throws Exception
	 */
	public boolean ExecuteDummyMission(LWMissionSchema tLWMissionSchema,
			VData tInputData) throws Exception {
		mResult.clear();
		// 校验数据有效性
		// 查询工作流任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setSchema(tLWMissionSchema);
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet.size() > 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行虚拟节点任务,查询工作流任务轨迹表LWMission出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询指定活动节点表(工作流活动表)
		LWActivityDB tLWActivityDB = new LWActivityDB();
		LWActivitySet tLWActivitySet = new LWActivitySet();
		tLWActivityDB.setActivityID(tLWMissionSchema.getActivityID());
		tLWActivitySet = tLWActivityDB.query();
		if (tLWActivitySet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行虚拟节点任务,查询工作流活动节点表出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LWActivitySchema tLWActivitySchema = new LWActivitySchema();
		tLWActivitySchema.setSchema(tLWActivitySet.get(1));

		// 执行BeforeInit任务
		if (!ExecuteBeforeInitDuty(tLWActivitySchema, tInputData)) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行虚拟节点任务,执行BeforeInit任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行AfterInit任务
		if (!ExecuteAfterInitDuty(tLWActivitySchema, tInputData)) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行虚拟节点任务,执行AfterInit任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行BeforeEnd任务
		if (!ExecuteBeforeEndDuty(tLWActivitySchema, tInputData)) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行虚拟节点任务,执行BeforeEnd任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行AfterEnd任务
		if (!ExecuteAfterEndDuty(tLWActivitySchema, tInputData)) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行虚拟节点任务,执行AfterEnd任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 删除节点任务
	 * 
	 * @param tMissionID
	 *            String
	 * @param tSubMissionID
	 *            String
	 * @param tActivityID
	 *            String
	 * @param tInputData
	 *            VData
	 * @return boolean
	 */
	public boolean DeleteMission(String tMissionID, String tSubMissionID,
			String tActivityID, VData tInputData) { // throws Exception
		
		TransferData tTransferData = (TransferData) tInputData.getObjectByObjectName("TransferData", 0);
		// 校验数据有效性
		GlobalInput tGlobalInput = new GlobalInput();
		if (tMissionID == null || tMissionID.equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,但没有传入当前工作流ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null || tSubMissionID.equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,但没有传入当前工作流子ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 校验数据有效性
		if (tActivityID == null || tActivityID.equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,但没有传入当前任务ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询工作流任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(tMissionID);
		tLWMissionDB.setActivityID(tActivityID);
		tLWMissionDB.setSubMissionID(tSubMissionID);
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,查询工作流任务轨迹表LWMission出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		tLWMissionSchema = tLWMissionSet.get(1);

		// 准备任务节点备份表数据
		LBMissionSchema mLBMissionSchema = new LBMissionSchema();
		LWMissionSchema mLWMissionSchema = new LWMissionSchema();
		String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);

		mReflections.transFields(mLBMissionSchema, tLWMissionSchema);
		mLBMissionSchema.setSerialNo(tSerielNo);
		mLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
//////////////////////////////////工作流升级///////////////////////////////////
		String tBusiOutDate = (String) tTransferData.getValueByName("BusiOutDate");
		String tBusiOutTime = (String) tTransferData.getValueByName("BusiOutTime");
		if (tBusiOutDate == null || tBusiOutDate.equals(""))
		{
			tBusiOutDate = PubFun.getCurrentDate();
		}
		if (tBusiOutTime == null || tBusiOutTime.equals(""))
		{
			tBusiOutTime = PubFun.getCurrentTime();
		}
		mLBMissionSchema.setOutDate(tBusiOutDate);
		mLBMissionSchema.setOutTime(tBusiOutTime);
//////////////////////////////////////////////////////////////////////////////		
		mLBMissionSchema.setLastOperator(tGlobalInput.Operator);
	    mLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
	    mLBMissionSchema.setMakeTime(PubFun.getCurrentTime());


		// 查询工作流锁表数据
		LWLockDB tLWLockDB = new LWLockDB();
		tLWLockDB.setMissionID(tMissionID);
		tLWLockDB.setSubMissionID(tSubMissionID);
		tLWLockDB.setLockActivityID(tActivityID);
		tLWLockDB.setLockType("1");
		tLWLockDB.getInfo();
		LWLockSchema mLWLockSchema = tLWLockDB.getSchema();

		if (tLWMissionSchema != null && mLBMissionSchema != null) {
			mResult.clear();
			MMap map = new MMap();
			map.put(tLWMissionSchema, "DELETE");
			map.put(mLBMissionSchema, "INSERT");
			if (mLWLockSchema != null) {
				map.put(mLWLockSchema, "DELETE");

			}

			mResult.add(map);
			return true;
		} else {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,查询工作流任务轨迹表LWMission出错!";
			this.mErrors.addOneError(tError);
			return false;

		}

	}

	/**
	 * 转存节点任务
	 * 
	 * @param tMissionID
	 *            String
	 * @param tInputData
	 *            VData
	 * @return LBMissionSchema
	 * @throws Exception
	 */
	public LBMissionSchema TranSaveMission(String tMissionID, VData tInputData)
			throws Exception {
		// mResult.clear();
		// 校验数据有效性
		if (tMissionID == null || tMissionID.equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,但没有传入当前任务ID信息!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 查询工作流任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(tMissionID);
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,查询工作流任务轨迹表LWMission出错!";
			this.mErrors.addOneError(tError);
			return null;
		}

		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		tLWMissionSchema.setSchema(tLWMissionSet.get(1));
		LBMissionSchema tLBMissionSchema = new LBMissionSchema();
		Reflections tReflections = new Reflections();
		tReflections.transFields(tLBMissionSchema, tLWMissionSchema);

		String tSerialNo = PubFun1.CreateMaxNo("MissionBID", 10);
		tLBMissionSchema.setSerialNo(tSerialNo);
		return tLBMissionSchema;
	}

	/**
	 * 转存虚拟节点任务
	 * 
	 * @param tLWMissionSchema
	 *            LWMissionSchema
	 * @param tInputData
	 *            VData
	 * @return LBMissionSchema
	 * @throws Exception
	 */
	public LBMissionSchema TranSaveDummyMission(
			LWMissionSchema tLWMissionSchema, VData tInputData)
			throws Exception {
		// 校验数据有效性
		// 查询工作流虚拟任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(tLWMissionSchema.getMissionID());
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet.size() > 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "TranSaveDummyMission";
			tError.errorMessage = "转存虚拟节点任务,查询工作流任务轨迹表LWMission出错!";
			this.mErrors.addOneError(tError);
			return null;
		}

		LBMissionSchema tLBMissionSchema = new LBMissionSchema();
		Reflections tReflections = new Reflections();
		tReflections.transFields(tLBMissionSchema, tLWMissionSchema);

		String tSerialNo = PubFun1.CreateMaxNo("MissionBID", 10);
		tLBMissionSchema.setSerialNo(tSerialNo);
		return tLBMissionSchema;
	}

	/**
	 * 完成执行节点任务前的初始任务
	 * 
	 * @param tLWActivitySchema
	 *            LWActivitySchema
	 * @param tInputData
	 *            VData
	 * @return boolean
	 * @throws Exception
	 */
	private boolean ExecuteBeforeInitDuty(LWActivitySchema tLWActivitySchema,
			VData tInputData) throws Exception {
		// 获取传入数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);

		// 校验数据有效性
		if (tLWActivitySchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,但没有传入当前任务ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行BeforeInit任务
		if (tLWActivitySchema.getBeforeInitType() == null
				|| tLWActivitySchema.getBeforeInitType().trim().equals("")) {
			// 空----表示没有需要执行的动作。
			return true;
		} else if (tLWActivitySchema.getBeforeInitType().equals("0")) {
			// 0 -- 默认，表示条件为Where子句。
			PubCalculator tPubCalculator = new PubCalculator();
			// 准备计算要素
			Vector tVector = (Vector) tTransferData.getValueNames();
			for (int i = 0; i < tVector.size(); i++) {
				String tName = (String) tVector.get(i);
				String tValue = (String) tTransferData.getValueByName(
						(Object) tName).toString();
				tPubCalculator.addBasicFactor(tName, tValue);
			}
			// 准备计算SQL
			tPubCalculator.setCalSql(tLWActivitySchema.getBeforeInit());
			String strSQL = tPubCalculator.calculate();
			logger.debug("SQL : " + strSQL);
			if (strSQL == null || strSQL.trim().equals("")) {
				return true;
			} else {
				// 分解条件语句,逐条执行
				try {
					String strTemp = "";
					char cTemp = ' ';
					for (int nIndex = 0; nIndex < strSQL.length(); nIndex++) {
						cTemp = strSQL.charAt(nIndex);
						if (cTemp == ';' && !strTemp.equals("")) {
							logger.debug(strTemp);
							String tReSult = new String();
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp); // 完成执行节点任务前的初始任务只是一些Insert||Delete||Update语句,所以无返回数据集合
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								// this.mErrors.copyAllErrors(tExeSQL.mErrors);
								CError tError = new CError();
								tError.moduleName = "ActivityOperator";
								tError.functionName = "ExecuteBeforeInitDuty";
								tError.errorMessage = "执行SQL语句：" + strTemp
										+ "失败!";
								this.mErrors.addOneError(tError);
								return false;
							}
							strTemp = "";
						} else {
							strTemp += String.valueOf(cTemp);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					return false;
				}
			}
			return true;
		} else if (tLWActivitySchema.getBeforeInitType().equals("1")) {
			// 1 -- 表示转移条件为一个特殊的类。
			try {
				Class tClass = Class.forName(tLWActivitySchema.getBeforeInit());
				WorkFlowService tWorkFlowService = (WorkFlowService) tClass
						.newInstance();

				// 准备数据
				String strOperate = tLWActivitySchema.getActivityID();
				tInputData.add(tLWActivitySchema);
				if (!tWorkFlowService.submitData(tInputData, strOperate)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tWorkFlowService.getErrors());
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "ExecuteBeforeInitDuty";

					tError.errorMessage = "执行执行节点任务BeforeInit失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				// 保存待提交数据
				VData tVData = new VData();
				tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
					mResult.add(tVData);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}

		return true;
	}

	/**
	 * 完成执行节点任务的核心任务
	 * 
	 * @param tLWActivitySchema
	 *            LWActivitySchema
	 * @param tInputData
	 *            VData
	 * @return boolean
	 * @throws Exception
	 */
	private boolean ExecuteAfterInitDuty(LWActivitySchema tLWActivitySchema,
			VData tInputData) throws Exception {
		// 获取传入数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);

		// 校验数据有效性
		if (tLWActivitySchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,但没有传入当前任务ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行AfterInit任务
		if (tLWActivitySchema.getAfterInitType() == null
				|| tLWActivitySchema.getAfterInitType().trim().equals("")) {
			// 空----表示没有需要执行的动作。
			return true;
		} else if (tLWActivitySchema.getAfterInitType().equals("0")) {
			// 0 -- 默认，表示条件为Where子句。
			PubCalculator tPubCalculator = new PubCalculator();
			// 准备计算要素
			Vector tVector = (Vector) tTransferData.getValueNames();
			for (int i = 0; i < tVector.size(); i++) {
				String tName = (String) tVector.get(i);
				String tValue = (String) tTransferData.getValueByName(
						(Object) tName).toString();
				tPubCalculator.addBasicFactor(tName, tValue);
			}
			// 准备计算SQL
			tPubCalculator.setCalSql(tLWActivitySchema.getAfterInit());
			String strSQL = tPubCalculator.calculate();
			logger.debug("SQL : " + strSQL);
			if (strSQL == null || strSQL.trim().equals("")) {
				return true;
			} else {
				// 分解条件语句,逐条执行
				// Statement stmt = conn.createStatement();
				try {
					String strTemp = "";
					char cTemp = ' ';
					for (int nIndex = 0; nIndex < strSQL.length(); nIndex++) {
						cTemp = strSQL.charAt(nIndex);
						if (cTemp == ';' && !strTemp.equals("")) {
							logger.debug(strTemp);
							String tReSult = new String();
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp); // 完成执行节点任务的核心任务只是一些Insert||Delete||Update语句,所以无返回数据集合
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
								CError tError = new CError();
								tError.moduleName = "ActivityOperator";
								tError.functionName = "ExecuteAfterInitDuty";
								tError.errorMessage = "执行SQL语句：" + strTemp
										+ "失败!";
								this.mErrors.addOneError(tError);
								return false;
							}
							strTemp = "";
						} else {
							strTemp += String.valueOf(cTemp);
						}
					}
					// stmt.close();
				} catch (Exception ex) {
					// stmt.close();
					ex.printStackTrace();
					return false;
				}
			}
			return true;
		} else if (tLWActivitySchema.getAfterInitType().equals("1")) {
			// 1 -- 表示转移条件为一个特殊的类。
			try {
				Class tClass = Class.forName(tLWActivitySchema.getAfterInit());
				WorkFlowService tWorkFlowService = (WorkFlowService) tClass
						.newInstance();

				// 准备数据
				String strOperate = tLWActivitySchema.getActivityID();
				tInputData.add(tLWActivitySchema);
				if (!tWorkFlowService.submitData(tInputData, strOperate)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tWorkFlowService.getErrors());
					// CError tError =new CError();
					// tError.moduleName="ActivityOperator";
					// tError.functionName="ExecuteAfterInitDuty";
					// tError.errorMessage="执行执行节点任务AfterInit失败!";
					// this.mErrors .addOneError(tError) ;
					return false;
				}

				// 保存待提交数据
				VData tVData = new VData();
				tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
					mResult.add(tVData);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}

		return true;
	}

	/**
	 * 完成执行节点任务的核心任务后的善后任务
	 * 
	 * @param tLWActivitySchema
	 *            LWActivitySchema
	 * @param tInputData
	 *            VData
	 * @return boolean
	 * @throws Exception
	 */
	private boolean ExecuteBeforeEndDuty(LWActivitySchema tLWActivitySchema,
			VData tInputData) throws Exception {
		// 获取传入数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);
		// 校验数据有效性
		if (tLWActivitySchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteBeforeEndDuty";
			tError.errorMessage = "执行节点任务,但没有传入当前任务信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行BeforeEnd任务
		if (tLWActivitySchema.getBeforeEndType() == null
				|| tLWActivitySchema.getBeforeEndType().trim().equals("")) {
			// 空----表示没有需要执行的动作。
			return true;
		} else if (tLWActivitySchema.getBeforeEndType().equals("0")) {
			// 0 -- 默认，表示条件为Where子句。
			PubCalculator tPubCalculator = new PubCalculator();
			// 准备计算要素
			Vector tVector = (Vector) tTransferData.getValueNames();
			for (int i = 0; i < tVector.size(); i++) {
				String tName = (String) tVector.get(i);
				String tValue = (String) tTransferData.getValueByName(
						(Object) tName).toString();
				tPubCalculator.addBasicFactor(tName, tValue);
			}
			// 准备计算SQL
			tPubCalculator.setCalSql(tLWActivitySchema.getBeforeEnd());
			String strSQL = tPubCalculator.calculate();
			logger.debug("SQL : " + strSQL);
			if (strSQL == null || strSQL.trim().equals("")) {
				return true;
			} else {
				// 分解条件语句,逐条执行
				try {
					String strTemp = "";
					char cTemp = ' ';
					for (int nIndex = 0; nIndex < strSQL.length(); nIndex++) {
						cTemp = strSQL.charAt(nIndex);
						if (cTemp == ';' && !strTemp.equals("")) {
							logger.debug(strTemp);
							String tReSult = new String();
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp); // 完成执行节点任务的核心任务后的善后任务只是一些Insert||Delete||Update语句,所以无返回数据集合
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								// this.mErrors.copyAllErrors(tExeSQL.mErrors);
								CError tError = new CError();
								tError.moduleName = "ActivityOperator";
								tError.functionName = "ExecuteBeforeEndDuty";
								tError.errorMessage = "执行SQL语句：" + strTemp
										+ "失败!";
								this.mErrors.addOneError(tError);
								return false;
							}
							strTemp = "";
						} else {
							strTemp += String.valueOf(cTemp);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					return false;
				}
			}
			return true;
		} else if (tLWActivitySchema.getBeforeEndType().equals("1")) {
			// 1 -- 表示转移条件为一个特殊的类。
			try {
				Class tClass = Class.forName(tLWActivitySchema.getBeforeEnd());
				WorkFlowService tWorkFlowService = (WorkFlowService) tClass
						.newInstance();

				// 准备数据
				String strOperate = tLWActivitySchema.getActivityID();
				tInputData.add(tLWActivitySchema);
				if (!tWorkFlowService.submitData(tInputData, strOperate)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tWorkFlowService.getErrors());
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "ExecuteBeforeEndDuty";
					tError.errorMessage = "执行执行节点任务BeforeEnd失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				// 保存待提交数据
				VData tVData = new VData();
				tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
					mResult.add(tVData);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 完成执行节点任务后的其他任务
	 * 
	 * @param tLWActivitySchema
	 *            LWActivitySchema
	 * @param tInputData
	 *            VData
	 * @return boolean
	 * @throws Exception
	 */
	private boolean ExecuteAfterEndDuty(LWActivitySchema tLWActivitySchema,
			VData tInputData) throws Exception {
		// 获取传入数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);
		// 校验数据有效性
		if (tLWActivitySchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "执行节点任务,但没有传入当前任务ID信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行AfterEnd任务
		if (tLWActivitySchema.getAfterEndType() == null
				|| tLWActivitySchema.getAfterEndType().trim().equals("")) {
			// 空----表示没有需要执行的动作。
			return true;
		} else if (tLWActivitySchema.getAfterEndType().equals("0")) {
			// 0 -- 默认，表示条件为Where子句。
			PubCalculator tPubCalculator = new PubCalculator();
			// 准备计算要素
			Vector tVector = (Vector) tTransferData.getValueNames();
			for (int i = 0; i < tVector.size(); i++) {
				String tName = (String) tVector.get(i);
				String tValue = (String) tTransferData.getValueByName(
						(Object) tName).toString();
				tPubCalculator.addBasicFactor(tName, tValue);
			}
			// 准备计算SQL
			tPubCalculator.setCalSql(tLWActivitySchema.getAfterInit());
			String strSQL = tPubCalculator.calculate();
			logger.debug("SQL : " + strSQL);
			if (strSQL == null || strSQL.trim().equals("")) {
				return true;
			} else {
				// 分解条件语句,逐条执行
				// Statement stmt = conn.createStatement();
				try {
					String strTemp = "";
					char cTemp = ' ';
					for (int nIndex = 0; nIndex < strSQL.length(); nIndex++) {
						cTemp = strSQL.charAt(nIndex);
						if (cTemp == ';' && !strTemp.equals("")) {
							logger.debug(strTemp);
							String tReSult = new String();
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp); // 完成执行节点任务的核心任务只是一些Insert||Delete||Update语句,所以无返回数据集合
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
								CError tError = new CError();
								tError.moduleName = "ActivityOperator";
								tError.functionName = "ExecuteAfterInitDuty";
								tError.errorMessage = "执行SQL语句：" + strTemp
										+ "失败!";
								this.mErrors.addOneError(tError);
								return false;
							}
							strTemp = "";
						} else {
							strTemp += String.valueOf(cTemp);
						}
					}
					// stmt.close();
				} catch (Exception ex) {
					// stmt.close();
					ex.printStackTrace();
					return false;
				}
			}
			return true;
		} else if (tLWActivitySchema.getAfterEndType().equals("1")) {
			// 1 -- 表示转移条件为一个特殊的类。
			try {
				Class tClass = Class.forName(tLWActivitySchema.getAfterEnd());
				WorkFlowService tWorkFlowService = (WorkFlowService) tClass
						.newInstance();

				// 准备数据
				String strOperate = tLWActivitySchema.getActivityID();
				tInputData.add(tLWActivitySchema);
				if (!tWorkFlowService.submitData(tInputData, strOperate)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tWorkFlowService.getErrors());
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "ExecuteAfterEndDuty";
					tError.errorMessage = "执行执行节点任务AfterEnd失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				// 保存待提交数据
				VData tVData = new VData();
				tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
					mResult.add(tVData);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/*
	 * 创建一个工作流的中的任意节点 在该方法中需要完成2件事情： 1。创建一个工作流新的起点任务
	 * 2。对该新创建的任务加上“任务A.Finished开始”锁(暂时取消) 执行完成后，产生一个VData的输出，为BLS的保存做准备。--YT
	 * @param tProcessID String @param tActivityID String @param tInputData
	 * VData @return LWMissionSchema @add by tuqiang
	 */
	public LWMissionSchema CreateOnlyOneMission(String tProcessID,
			String tActivityID, VData tInputData) {
		// 获取前台数据
		GlobalInput tGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tGlobalInput = (GlobalInput) tInputData.getObjectByObjectName(
				"GlobalInput", 0);
		tTransferData = (TransferData) tInputData.getObjectByObjectName(
				"TransferData", 0);
		String tmissionid = (String) tTransferData.getValueByName("MissionID");
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();

		LWMissionDB mLWMissionDB = new LWMissionDB();
		LWMissionSet mLWMissionSet = new LWMissionSet();

		mLWMissionDB.setMissionID(tmissionid);
		mLWMissionSet = mLWMissionDB.query();
		if (mLWMissionSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "工作流节点查询失败!";
			this.mErrors.addOneError(tError);
			return null;
		}
		mLWMissionDB.setActivityID(tActivityID);
		mLWMissionSet = mLWMissionDB.query();
		if (mLWMissionSet.size() != 0) {
			return null;
		}
		// 校验数据有效性
		if (tProcessID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但没有传入工作流编码信息!";
			this.mErrors.addOneError(tError);
			return null;
		}
		if (tActivityID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但没有传入工作流活动节点信息!";
			this.mErrors.addOneError(tError);
			return null;
		}
		// 查询工作流过程表
		LWProcessDB tLWProcessDB = new LWProcessDB();
		LWProcessSet tLWProcessSet = new LWProcessSet();
		tLWProcessDB.setProcessID(tProcessID);
		tLWProcessSet = tLWProcessDB.query();
		if (tLWProcessSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,但传入的工作流编码信息有误!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 查询默认工作流起点节点表(工作流活动表)
		LWActivityDB tLWActivityDB = new LWActivityDB();
		LWActivitySet tLWActivitySet = new LWActivitySet();
		tLWActivityDB.setActivityID(tActivityID);
		tLWActivitySet = tLWActivityDB.query();
		if (tLWActivitySet == null || tLWActivitySet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLWActivitySet.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,查询工作流节点表出错!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 校验传入参数
		LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
		LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
		tLWFieldMapDB.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWFieldMapSet = tLWFieldMapDB.query();
		if (tLWFieldMapSet.size() != 0 && tTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 产生工作流起点任务,准备数据
		tLWMissionSchema.setMissionID(tmissionid);
//////////////////////////////////工作流升级///////////////////////////////////		
		tLWMissionSchema.setMainMissionID(tmissionid);
//////////////////////////////////////////////////////////////////////////////				
		
		tLWMissionSchema.setSubMissionID("1");
		tLWMissionSchema.setProcessID(tLWProcessSet.get(1).getProcessID());
		tLWMissionSchema.setActivityID(tLWActivitySet.get(1).getActivityID());
		tLWMissionSchema.setActivityStatus("1"); // 0 --
		// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
		// -- 任务产生完毕待处理，2 -- 处理中，3 --
		// 处理完成，4 -- 暂停
		// 准备属性字段
		for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
			if (tLWFieldMapSet.get(i).getSourFieldName() == null
					|| tLWFieldMapSet.get(i).getSourFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return null;
			}

			if (tLWFieldMapSet.get(i).getDestFieldName() == null
					|| tLWFieldMapSet.get(i).getDestFieldName().equals("")) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!";
				this.mErrors.addOneError(tError);
				return null;
			}

			if (tTransferData.getValueByName(tLWFieldMapSet.get(i)
					.getSourFieldName()) == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "ActivityOperator";
				tError.functionName = "CreateNewMission";
				tError.errorMessage = "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段信息不足!";
				this.mErrors.addOneError(tError);
				return null;
			}

			String tMissionProp = (String) tTransferData
					.getValueByName(tLWFieldMapSet.get(i).getSourFieldName());
			String tDestFieldName = tLWFieldMapSet.get(i).getDestFieldName();
			// logger.debug("tMissionProp:"+tMissionProp
			// +"tDestFieldName"+tDestFieldName) ;

			tLWMissionSchema.setV(tDestFieldName, tMissionProp);

		}
//////////////////////////////////工作流升级///////////////////////////////////
		String tBusiType = (String) tTransferData.getValueByName("BusiType");
		String tBusiInDate = (String) tTransferData.getValueByName("BusiInDate");
		String tBusiInTime = (String) tTransferData.getValueByName("BusiInTime");
		if (tBusiInDate == null || tBusiInDate.equals(""))
		{ //如果业务进入日期未指定则置为系统当前日期
			tBusiInDate = PubFun.getCurrentDate();
		}
		if (tBusiInTime == null || tBusiInTime.equals(""))
		{ //如果业务进入时间未指定则置为系统当前时间
			tBusiInTime = PubFun.getCurrentTime();
		}
		tLWMissionSchema.setInDate(tBusiInDate); 
		tLWMissionSchema.setInTime(tBusiInTime); 
		
		String tStandEndDate = "";
		String tStandEndTime = "";
		//获得timeid
		tLWProcessDB = new LWProcessDB();//
		tLWProcessDB.setProcessID(tLWMissionSchema.getProcessID());
		if (!tLWProcessDB.getInfo())
		{
			CError tError = new CError();
			tError.moduleName = "ActivityOperator";
			tError.functionName = "CreateNewMission";
			tError.errorMessage = "没有找到流程" + tLWMissionSchema.getProcessID() + "定义的时效编码!";
			this.mErrors.addOneError(tError);
			return null;
		}
		LWProcessSchema tLWProcessSchema = tLWProcessDB.getSchema();
		String tTimeID = tLWProcessSchema.getTimeID(); //时效编码
		
		//取得TimeFlag和TransitionTime
		if(tTimeID!=null&&!tTimeID.equals(""))
		{
			LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();
			tLWTransTimeDB.setTimeID(tTimeID);
			tLWTransTimeDB.setProcessID(tLWMissionSchema.getProcessID());
			tLWTransTimeDB.setActivityID(tLWMissionSchema.getActivityID());
			if (!tLWTransTimeDB.getInfo())
			{
//				CError tError = new CError();
//				tError.moduleName = "ActivityOperator";
//				tError.functionName = "CreateNewMission";
//				tError.errorMessage = "没有找到流程" + tLWMissionSchema.getProcessID() + "定义的时效信息!";
//				this.mErrors.addOneError(tError);
//				return null;
			}
			else
			{
				LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
				tLWTransTimeSchema = tLWTransTimeDB.getSchema();
				String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); //时效类型
				String tTransitionTime = tLWTransTimeSchema.getTransitionTime(); //时效
				//计算标准结束时间
				TransferData standTransferData = WorkCalendarService.
						getStandEndTimes(tBusiInDate, tBusiInTime, tTimeFlag, tTransitionTime);
				if (standTransferData == null)
				{
					CError tError = new CError();
					tError.moduleName = "ActivityOperator";
					tError.functionName = "CreateNewMission";
					tError.errorMessage = "计算标准结束时间失败!";
					this.mErrors.addOneError(tError);
					return null;
				}
				String tCalFlag = (String) standTransferData.getValueByName("CalFlag");
				if (tCalFlag.equals("Y"))
				{
					tStandEndDate = (String) standTransferData.getValueByName("StandEndDate");
					tStandEndTime = (String) standTransferData.getValueByName("StandEndTime");
				}
				else
				{
					tStandEndDate = "";
					tStandEndTime = "";
				}
				tLWMissionSchema.setTimeID(tTimeID);
			}
		}
		tLWMissionSchema.setStandEndDate(tStandEndDate);
		tLWMissionSchema.setStandEndTime(tStandEndTime);
		String tDefaultOperator = (String) tTransferData.getValueByName("DefaultOperator");
		if (tDefaultOperator != null && (!tDefaultOperator.equals("")))
		{ //如果业务进入日期未指定则置为系统当前日期
			tLWMissionSchema.setDefaultOperator(tDefaultOperator);
		}
//////////////////////////////////////////////////////////////////////////////
		tLWMissionSchema.setOperateCom(tGlobalInput.ManageCom);
		tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
		tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
		tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
		tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
		tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());

		logger.debug("ok!:");
		return tLWMissionSchema;
	}

	public boolean CheckTransitionCondition_Public(
			LWProcessTransSchema tLWProcessTransSchema, VData tInputData)
			throws Exception {
		return this.CheckTransitionCondition(tLWProcessTransSchema,
				tInputData);
	}

	private void jbInit() throws Exception {
	}

}
