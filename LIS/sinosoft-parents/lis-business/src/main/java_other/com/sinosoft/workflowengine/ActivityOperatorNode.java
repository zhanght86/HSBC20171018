/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:活动节点任务处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * @version 1.0
 */
package com.sinosoft.workflowengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.ibrms.BOMsqls;
import com.sinosoft.ibrms.bom.BOMConTr;
import com.sinosoft.lis.db.LBMissionDB;
import com.sinosoft.lis.db.LRTemplateDB;
import com.sinosoft.lis.db.LWActivityDB;
import com.sinosoft.lis.db.LWFieldMapDB;
import com.sinosoft.lis.db.LWLockDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LWPrioritysqlDB;
import com.sinosoft.lis.db.LWProcessDB;
import com.sinosoft.lis.db.LWProcessTransDB;
import com.sinosoft.lis.db.LWTransTimeDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LRTemplateSchema;
import com.sinosoft.lis.schema.LWActivitySchema;
import com.sinosoft.lis.schema.LWLockSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.LWPrioritysqlSchema;
import com.sinosoft.lis.schema.LWProcessTransSchema;
import com.sinosoft.lis.schema.LWTransTimeSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWFieldMapSet;
import com.sinosoft.lis.vschema.LWLockSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LWProcessTransSet;
import com.sinosoft.lis.workflowmanage.WorkCalendarService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
//import com.sinosoft.lis.midplat.kernel.parse.ParseInterface;

/**
 * @author Administrator
 * 
 */
public class ActivityOperatorNode {

	private static Logger logger = Logger.getLogger(ActivityOperatorNode.class);
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 活动标志 */
	private String ActivityFlag;
	/** 活动ID */
	private String AcivityID;
	/** 业务类型 */
	private String BusiType;
	/** 功能ID */
	private String FunctionID;
	/** 聚合模式 */
	private String Together;
	/** 流程ID ，一个业务类型可以对应多个ProcessID，但是只有一个ProcessID是当前可用的 */
	private String ProcessID;
	/** 当前节点任务的版本 */
	private String Version;
	/** 当前节点的前驱 */
	private List<String> PriviousNodes = new ArrayList<String>();
	/** 当前节点的后续 */
	private List<String> NextNodes = new ArrayList<String>();
	
	//父亲节点的ActivityID
	private String StartActivityid;
	//父亲节点的StartSubMissionID
	private String StartSubMissionID;
	//父亲节点的StartMissionID
	private String StartMissionID;
	@SuppressWarnings("unused")
	private ActivityOperatorNode() {
		
	}
	
	public ActivityOperatorNode(String ActivityID, String ProcessID,
			String Version, String InStartMissionID,String InStartSubMissionID,String InStartActivityid) {
		this(ActivityID,ProcessID,Version);
		StartActivityid=InStartActivityid;
		StartSubMissionID=InStartSubMissionID;
		StartMissionID = InStartMissionID;
	}

	public ActivityOperatorNode(String ActivityID, String ProcessID,
			String Version) {
		try {
			this.Version = Version;
			this.AcivityID = ActivityID;
			this.ProcessID = ProcessID;
			String tSQL = "select busitype,functionid,together,ActivityFlag from lwactivity where activityid='"
					+ "?ActivityID?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("ActivityID", ActivityID);
			SSRS tSSRS = new SSRS();
			tSSRS = new ExeSQL().execSQL(sqlbv1);
			this.BusiType = tSSRS.GetText(1, 1);
			this.FunctionID = tSSRS.GetText(1, 2);
			this.Together = tSSRS.GetText(1, 3);
			this.ActivityFlag = tSSRS.GetText(1, 4);
			// String tSQLprivious =
			// "select transitionstart from LWProcessTrans where transitionend= '"
			// + ActivityID
			// + "' and version='"
			// + this.Version
			// + "' and processid='" + this.ProcessID + "'";
			// 修改逻辑
			// 前驱需要递归查询
			// String tSQLprivious =
			// "select DISTINCT  a.transitionstart from lwprocesstrans a "
			// + " where a.processid='"+ProcessID+"' "
			// //+ " and a.transitionend = '"+ActivityID+"' "
			// + " and version = '"+this.Version+"' "
			// +
			// " connect by NOCYCLE PRIOR a.transitionstart=  a.transitionend and a.processid='"+ProcessID+"' and version = '"+this.Version+"'  "
			// +
			// " start with a.transitionend = '"+ActivityID+"' and a.processid='"+ProcessID+"' and version = '"+this.Version+"' ";
			/*********** 根据张荣的要求又改为只查询直系前驱父节点 **************/
			String tSQLprivious = "select transitionstart from LWProcessTrans where transitionend= '"
					+ "?ActivityID?"
					+ "' and version='"
					+ "?Version?"
					+ "' and processid='" + "?ProcessID?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQLprivious);
			sqlbv2.put("ActivityID", ActivityID);
			sqlbv2.put("Version", this.Version);
			sqlbv2.put("ProcessID", this.ProcessID);
			SSRS tSSRSprivious = new ExeSQL().execSQL(sqlbv2);
			String privious;
			int priviouscount = tSSRSprivious.getMaxRow();
			for (int i = 0; i < priviouscount; i++) {
				privious = tSSRSprivious.GetText(i + 1, 1);
				this.PriviousNodes.add(privious);
			}
			String tSQLnext = "select transitionend from lwprocesstrans where transitionstart='"
					+ "?ActivityID?"
					+ "' and version='"
					+ "?Version?"
					+ "' and processid='" + "?ProcessID?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSQLnext);
			sqlbv3.put("ActivityID", ActivityID);
			sqlbv3.put("Version", this.Version);
			sqlbv3.put("ProcessID", this.ProcessID);
			SSRS tSSRSnext = new ExeSQL().execSQL(sqlbv3);
			String next;
			int nextcount = tSSRSnext.getMaxRow();
			for (int j = 0; j < nextcount; j++) {
				next = tSSRSnext.GetText(j + 1, 1);
				this.NextNodes.add(next);
			}
		} catch (Exception e) {
			logger.debug("ActivityOperator.java 构造器里发生异常，异常信息："
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 处理节点任务包括（执行当前任务、创建下一节点任务、删除当前任务） WorkFlow.java的操作符是execute，
	 * 
	 * @param String
	 *            MissionID
	 * @param String
	 *            SubMissionID
	 * @param VData
	 *            InputData
	 * @return boolean
	 * @throws Exception
	 */
	public boolean ExecuteMission(String MissionID, String SubMissionID,
			VData InputData) {
		this.mResult.clear();
		if (MissionID == null || MissionID.equals("")) {
			CError.buildErr(this, "执行任务节点，传入的任务ID为空！");
			return false;
		}
		if (SubMissionID == null || SubMissionID.equals("")) {
			CError.buildErr(this, "执行任务节点，传入的子任务ID为空！");
			return false;
		}
		if (("1").equals(this.ActivityFlag) || ("2").equals(this.ActivityFlag)) {
			if (!Execute(MissionID, SubMissionID, InputData)) {
				return false;
			}
			// createnext 时创建后续节点时加了锁，如果出现错误、异常 应解锁。正常情况下是在后续节点处理时解锁
			if (!CreateNext(MissionID, SubMissionID, InputData)) {
				UnusualUnlockMission(MissionID);
				return false;
			}
			// if(!DeleteLastLocal(MissionID,SubMissionID,InputData))
			// {
			// UnusualUnlockMission(MissionID);
			// return false;
			// }
			if (!Delete(MissionID, SubMissionID, InputData, this.AcivityID)) {
				UnusualUnlockMission(MissionID);
				return false;
			}
		} else if (("3").equals(this.ActivityFlag)) {
			if (!Execute(MissionID, SubMissionID, InputData)) {
				return false;
			}
			if (!Delete(MissionID, SubMissionID, InputData, this.AcivityID)) {
				return false;
			}
		} else {
			CError.buildErr(this, "没有此种类型的活动标志！");
			return false;
		}
		return true;
	}

	/**
	 * 非正常解锁——创建时加的锁 如处理节点任务，在Createnext以后的部分报错或者发生异常是会先解锁，然后返回错误原因
	 * 
	 * @param MissionID
	 *            String
	 * @return boolean
	 * 
	 */
	public boolean UnusualUnlockMission(String MissionID) {
		LWLockDB tLWLockDB = null;
		String lockSQL = "select submissionid,lockactivityid from lwlock where 1=1 and missionid='"
				+ "?MissionID?"
				+ "' and processid='"
				+ "?ProcessID?"
				+ "' and locktype='8'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(lockSQL);
		sqlbv4.put("MissionID", MissionID);
		sqlbv4.put("ProcessID", this.ProcessID);
		SSRS tSSRS = new ExeSQL().execSQL(sqlbv4);
		for (int i = 0; i < tSSRS.getMaxRow(); i++) {
			tLWLockDB = new LWLockDB();
			tLWLockDB.setMissionID(MissionID);
			tLWLockDB.setSubMissionID(tSSRS.GetText(i + 1, i + 1));
			tLWLockDB.setLockActivityID(tSSRS.GetText(i + 1, i + 2));
			tLWLockDB.setLockType("8");
			if (tLWLockDB.getInfo()) {
				tLWLockDB.delete();
			}
		}
		return true;
	}

	/**
	 * 创建节点任务
	 * 
	 * @param String
	 *            MissionID
	 * @param String
	 *            SubMissionID
	 * @param VData
	 *            InputData
	 * @return boolean
	 * @throws Exception
	 */
	public boolean Create(String tMissionID, String tSubMissionID,
			VData tInputData) {
		logger.debug("··················ActivityOperatorNode.java  create ");
		// 获取前台数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		if (tMissionID == null || tMissionID.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "创建一个工作流的节点任务,传入的任务MissionIDID为空!");
			return false;
		}
		if (tSubMissionID == null || tSubMissionID.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "创建一个工作流的节点任务,传入的子任务SubMissionIDID为空!");
			return false;
		}
		LWActivityDB tLWActivityDB = new LWActivityDB();
		tLWActivityDB.setActivityID(this.AcivityID);
		if (!tLWActivityDB.getInfo()) {
			CError.buildErr(this, "创建节点任务，查询工作流活动表（LWActivity）表出错!");
			return false;
		}
		LWActivitySchema mLWActivitySchema = tLWActivityDB.getSchema();

		LWMissionSchema LWMissionSchema = new LWMissionSchema();
		LWProcessDB tLWprocessDB = new LWProcessDB();
		tLWprocessDB.setProcessID(this.ProcessID);
		tLWprocessDB.setVERSION(this.Version);
		if (!tLWprocessDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "创建一个工作流任务,查询工作流过程表出错!");
		}
		LWMissionSchema.setMissionID(tMissionID);
		LWMissionSchema.setMainMissionID(tMissionID);
		LWMissionSchema.setSubMissionID(tSubMissionID);
		LWMissionSchema.setProcessID(this.ProcessID);
		LWMissionSchema.setActivityID(this.AcivityID);
		if ("".equals(this.Together) || null == this.Together
				|| "2".equals(this.Together) || "3".equals(this.Together)) {
			LWMissionSchema.setActivityStatus("1");
		} else if (("1".equals(this.Together))) {
			LWMissionSchema.setActivityStatus("9");
			
			//BEGIN 全部聚合，最后流转的工作流ActivityStatus = 1
			int count = 0;
			for (int i = 0; i < this.PriviousNodes.size(); i++) {
				String tSQL = "select 1 from LWMission where missionid='"
						+ "?tMissionID?"
						+ "' and activityid='" + "?activityid?"
						+ "' and processid='" + "?processid?"
						+ "' and version='" + "?version?" + "'";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(tSQL);
				sqlbv5.put("tMissionID", tMissionID);
				sqlbv5.put("activityid", this.PriviousNodes.get(i));
				sqlbv5.put("processid", this.ProcessID);
				sqlbv5.put("version", this.Version);
				SSRS tSSRS = new ExeSQL().execSQL(sqlbv5);
				// 说明前驱未完成,count 加1
				if (tSSRS.MaxRow > 0) {
					count++;
				}
			}
			if(count<=1){
				LWMissionSchema.setActivityStatus("1");
			}
			//END 全部聚合，最后流转的工作流ActivityStatus = 1
		} else {
			// @@错误处理
			CError.buildErr(this, "这种聚合模式没有定义" + this.Together + ",故无法确定任务状态！");
		}
		if (!CreateAction(mLWActivitySchema, tInputData)) {
			return false;
		}
		// LWMissionSchema.setActivityStatus("9");
		// LWMissionSchema.setActivityStatus("1");
		// 0 --
		// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）
		// 1 -- 任务产生完毕待处理，2 -- 处理中，3 -- 处理完成，4 -- 暂停
		// 准备属性字段
		// 校验传入参数
		if (!prepareMissionField(tTransferData, LWMissionSchema,tMissionID)) {
			return false;
		}

		String tBusiType = (String) tTransferData.getValueByName("BusiType");
		String tBusiInDate = (String) tTransferData
				.getValueByName("BusiInDate");
		String tBusiInTime = (String) tTransferData
				.getValueByName("BusiInTime");
		if (tBusiInDate == null || tBusiInDate.equals("")) { // 如果业务进入日期未指定则置为系统当前日期
			tBusiInDate = PubFun.getCurrentDate();
		}
		if (tBusiInTime == null || tBusiInTime.equals("")) { // 如果业务进入时间未指定则置为系统当前时间
			tBusiInTime = PubFun.getCurrentTime();
		}
		LWMissionSchema.setInDate(tBusiInDate);
		LWMissionSchema.setInTime(tBusiInTime);

		String tStandEndDate = "";
		String tStandEndTime = "";
		// 获得timeid
		String tTimeID = tLWprocessDB.getTimeID(); // 时效编码
		// 取得TimeFlag和TransitionTime
		if (tTimeID != null && !tTimeID.equals("")) {
			LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();
			tLWTransTimeDB.setTimeID(tTimeID);
			tLWTransTimeDB.setProcessID(this.ProcessID);
			tLWTransTimeDB.setActivityID(this.AcivityID);
			if (!tLWTransTimeDB.getInfo()) {
			} else {
				LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
				tLWTransTimeSchema = tLWTransTimeDB.getSchema();
				String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); // 时效类型
				String tTransitionTime = tLWTransTimeSchema.getTransitionTime(); // 时效
				// 计算标准结束时间
				TransferData standTransferData = WorkCalendarService
						.getStandEndTimes(tBusiInDate, tBusiInTime, tTimeFlag,
								tTransitionTime);
				if (standTransferData == null) {
					CError.buildErr(this, "计算标准结束时间失败!");
					return false;
				}
				String tCalFlag = (String) standTransferData
						.getValueByName("CalFlag");
				if (tCalFlag.equals("Y")) {
					tStandEndDate = (String) standTransferData
							.getValueByName("StandEndDate");
					tStandEndTime = (String) standTransferData
							.getValueByName("StandEndTime");
				} else {
					tStandEndDate = "";
					tStandEndTime = "";
				}
				LWMissionSchema.setTimeID(tTimeID);
			}
		}
		LWMissionSchema.setStandEndDate(tStandEndDate);
		LWMissionSchema.setStandEndTime(tStandEndTime);
		String tDefaultOperator = (String) tTransferData
				.getValueByName("DefaultOperator");
		if (tDefaultOperator != null && (!tDefaultOperator.equals(""))) { // 如果业务进入日期未指定则置为系统当前日期
			LWMissionSchema.setDefaultOperator(tDefaultOperator);
		}
		LWMissionSchema.setOperateCom(tGlobalInput.ManageCom);
		LWMissionSchema.setLastOperator(tGlobalInput.Operator);
		LWMissionSchema.setCreateOperator(tGlobalInput.Operator);
		LWMissionSchema.setMakeDate(PubFun.getCurrentDate());
		LWMissionSchema.setMakeTime(PubFun.getCurrentTime());
		LWMissionSchema.setModifyDate(LWMissionSchema.getMakeDate());
		LWMissionSchema.setModifyTime(LWMissionSchema.getMakeTime());
		// 版本问题，创建开始节点时Version是查询LWCorresponding得到的，非开始节点时查询LWMission得到的。
		LWMissionSchema.setVERSION(this.Version);
		// 紧急度
		List<String> strPri = new ArrayList<String>();
		strPri = CalculateSQL(tMissionID, tInputData);
		LWMissionSchema.setSQLPriorityID(strPri.get(0));
		LWMissionSchema.setPriorityID(strPri.get(1));

		MMap map = new MMap();
		
		
		//BEGIN  全部聚合，保留最后的LWMission
		System.out.println("******************MissioinID = "+LWMissionSchema.getMissionID());
		System.out.println("******************SubMissioinID = "+LWMissionSchema.getSubMissionID());
		System.out.println("******************ActivityID = "+LWMissionSchema.getActivityID());
		System.out.println("******************MissionProp1 = "+LWMissionSchema.getMissionProp1());
		System.out.println("******************MissionProp2 = "+LWMissionSchema.getMissionProp2());
		if("1".equals(this.Together)){
			map.put(LWMissionSchema, "DELETE&INSERT");
		}else{
			map.put(LWMissionSchema, "INSERT");
		}
		//END 全部聚合，保留最后的LWMission
		
		// VData tVData=new VData();
		// tVData.add(map);
		this.mResult.add(map);
		LWMissionDB tLWMissionDB = LWMissionSchema.getDB();
//		if (!CreateAction(mLWActivitySchema, tInputData)) {
//			return false;
//		}
		LWLockDB newLWLockDB = new LWLockDB();
		newLWLockDB.setLockActivityID(this.AcivityID);
		newLWLockDB.setMissionID(tMissionID);
		newLWLockDB.setSubMissionID(tSubMissionID);
		newLWLockDB.setLockType("8");
		
		//BEGIN 全部聚合，保留最后的LWLock
		LWLockSchema tLWLockSchema = new LWLockSchema();
		tLWLockSchema.setLockActivityID(this.AcivityID);
		tLWLockSchema.setLockStatus("0");
		tLWLockSchema.setLockType("8");
		tLWLockSchema.setMakeDate(PubFun.getCurrentDate());
		tLWLockSchema.setMakeTime(PubFun.getCurrentTime());
		tLWLockSchema.setMissionID(tMissionID);
		tLWLockSchema.setSubMissionID(tSubMissionID);
		tLWLockSchema.setOperator(tGlobalInput.Operator);
		tLWLockSchema.setProcessID(this.ProcessID);
		tLWLockSchema.setTimeOut(0);
		LWLockDB tNewLWLockDB = tLWLockSchema.getDB();
		if("1".equals(this.Together)){//如果是全部聚合，删除旧的LWLock，再插入新的LWLock。相当于LWLock的update
			tNewLWLockDB.delete();
			tNewLWLockDB.insert();
		}
		if (!newLWLockDB.getInfo()) {//如果还没创建LWLock，则创建。否则跳过。
			tNewLWLockDB.insert();
		}
		//END 全部聚合，保留最后的LWLock
		return true;
	}

	/**
	 * 通过lwfieldmap的 描述给lwmission的相关字段赋值
	 * 
	 * @param tTransferData
	 * @param LWMissionSchema
	 * @return
	 */
	private boolean prepareMissionField(TransferData tTransferData,
			LWMissionSchema LWMissionSchema,String MissionID) {
		LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
		tLWFieldMapDB.setActivityID(this.AcivityID);
		LWFieldMapSet tLWFieldMapSet = tLWFieldMapDB.query();
		if (tLWFieldMapSet.size() == 0 && tTransferData == null) {
			// @@错误处理
			CError.buildErr(this,
					"创建一个工作流的节点任务,传入工作流任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!");
			return false;
		}
		for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
			/*****
			 * 升级改造，工作流节点创建时不对属性为"从本节点获取"GetValueType='2'的字段赋值 modify by LIJIAN
			 * at 20130401
			 ****/
			if (tLWFieldMapSet.get(i).getGetValueType() != null
					&& "2".equals(tLWFieldMapSet.get(i).getGetValueType())) {
				continue;
			}
			if (tLWFieldMapSet.get(i).getSourFieldName() == null
					|| tLWFieldMapSet.get(i).getSourFieldName().equals("")) {
				// @@错误处理
				CError.buildErr(this,
						"创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!");
				return false;
			}

			if (tLWFieldMapSet.get(i).getDestFieldName() == null
					|| tLWFieldMapSet.get(i).getDestFieldName().equals("")) {
				// @@错误处理
				CError.buildErr(this,
						"创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录DestFieldName字段描述有误!");
				return false;
			}

			String tMissionProp = tTransferData.getValueByName(tLWFieldMapSet
					.get(i).getSourFieldName()) == null ? ""
					: (String) tTransferData.getValueByName(tLWFieldMapSet.get(
							i).getSourFieldName());
			// if(tMissionProp == null)
			// {
			// // @@错误处理
			// CError.buildErr(this,"创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段信息不足!");
			// return false;
			// }

			if (tMissionProp.equals("")) {
				/*
				 * 0 -- 默认，表示通过SQL语句取数 1 -- 表示通过特殊的类来取数。
				 */
				String tGetValueType = tLWFieldMapSet.get(i).getGetValueType();
				String tGetValue = tLWFieldMapSet.get(i).getGetValue();

				if (tGetValueType != null && tGetValueType.equals("0")) {
					// SQL
					// 0 -- 默认，表示条件为Where子句。
					PubCalculator tPubCalculator = new PubCalculator();
					// 准备计算要素
					Vector tVector = (Vector) tTransferData.getValueNames();
					for (int n = 0; n < tVector.size(); n++) {
						String tName = (String) tVector.get(n);
						String tValue = (String) tTransferData.getValueByName(
								(Object) tName).toString();
						tPubCalculator.addBasicFactor(tName, tValue);
					}
					// 准备计算SQL
					tPubCalculator.setCalSql(tGetValue);
					// 直接执行
					tMissionProp = tPubCalculator.calculate();
				} else if (tGetValueType != null && tGetValueType.equals("1")) {
					// JAVA
					// 暂时不做这个扩充
				}
			}
			
			//BEGIN 继承父亲的遗产
			if(tMissionProp.equals("")){
				ExeSQL tExeSQL = new ExeSQL();
				String sonName = tLWFieldMapSet.get(i).getSourFieldName();
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql("select destFieldName from lwfieldmap where activityid = '"+"?activityid?"+"' and sourfieldname = '"+"?sonName?"+"'");
				sqlbv6.put("activityid", this.StartActivityid);
				sqlbv6.put("sonName", sonName);
				String fatherName = tExeSQL.getOneValue(sqlbv6);
				if(fatherName !=null && !"".equals(fatherName)){
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					sqlbv7.sql("select "+fatherName+" from lwmission where missionid ='"+"?StartMissionID?"+"' and submissionid = '"+"?StartSubMissionID?"+"' and activityid = '"+"?StartActivityid?"+"'");
					sqlbv7.put("StartMissionID", this.StartMissionID);
					sqlbv7.put("StartSubMissionID", this.StartSubMissionID);
					sqlbv7.put("StartActivityid", this.StartActivityid);
					tMissionProp =  tExeSQL.getOneValue(sqlbv7);
				}
			}
			//END 继承父亲的遗产
			String tDestFieldName = tLWFieldMapSet.get(i).getDestFieldName();
			LWMissionSchema.setV(tDestFieldName, tMissionProp);
		}

		return true;
	}

	/**
	 * 更新任务状态，此方法有两个地方调用，一是Create(String,String,VData)
	 * 二是Createnext(String,String,VData)时后续节点任务已经创建
	 * 
	 * @param tMissionID
	 *            String
	 * @param tSubMissionID
	 * @param tInputData
	 *            VData
	 * @return boolean
	 */
	public boolean UpdateStatus(String tMissionID, String tSubMissionID,
			VData tInputData) {
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		if ("".equals(tMissionID) || null == tMissionID) {
			// @@错误处理
			CError.buildErr(this, "更新任务状态，传入的MissionID为空!");
		}
		if ("".equals(tSubMissionID) || null == tSubMissionID) {
			// @@错误处理
			CError.buildErr(this, "更新任务状态，传入的SubMissionID为空!");
		}
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setActivityID(this.AcivityID);
		tLWMissionDB.setMissionID(tMissionID);
		tLWMissionDB.setSubMissionID(tSubMissionID);
		if (!tLWMissionDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "更新任务状态，查询LWMission出错可能是该记录没有创建，但是锁表给该记录加锁了!");
		}
		LWMissionSchema tLWMissionSchema = tLWMissionDB.getSchema();
		MMap map = new MMap();
		// 如果当前节点的聚合模式为空 ，或者为 2——部分执行 ，或者为3——单一执行 时，只更新任务的状态（不可用——>可用）
		if ("".equals(this.Together) || null == this.Together
				|| "" == this.Together || "2".equals(this.Together)
				|| "2" == this.Together || "3".equals(this.Together)
				|| "3" == this.Together) {
			tLWMissionSchema.setActivityStatus("1");// 0 --
													// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）
			// 1 -- 任务产生完毕待处理，2 -- 处理中，3 -- 处理完成，4 -- 暂停
			map.put(tLWMissionSchema, "UPDATE");
			this.mResult.add(map);
		}
		// 如果节点的聚合模式为1——全部执行
		else if ("1".equals(this.Together) || "1" == this.Together) {
			int count = 0;
			for (int i = 0; i < this.PriviousNodes.size(); i++) {
				String tSQL = "select 1 from LWMission where missionid='"
						+ "?missionid?"
						+ "' and activityid='" + "?activityid?"
						+ "' and processid='" + "?processid?"
						+ "' and version='" + "?version?" + "'";
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql(tSQL);
				sqlbv8.put("missionid", tLWMissionSchema.getMissionID());
				sqlbv8.put("activityid", this.PriviousNodes.get(i));
				sqlbv8.put("processid", this.ProcessID);
				sqlbv8.put("version", this.Version);
				SSRS tSSRS = new ExeSQL().execSQL(sqlbv8);
				// 如果结果不为null，说明前驱未完成,count 加1
				if (tSSRS.MaxRow > 0) {
					count++;
				}
			}
			// 此方法一定是由一个前驱任务的createnext 触发的，故一定有一个前驱是未完成的状态
			// ，故如果count>1,说明有未完成的前驱任务，不更新状态
			if (count > 1) {
				return true;
			}
			// 如果count<=1，更新任务状态（不可用——>可用）。
			else if (count <= 1) {
				LWProcessDB tLWprocessDB = new LWProcessDB();
				tLWprocessDB.setProcessID(this.ProcessID);
				tLWprocessDB.setVERSION(this.Version);
				if (!tLWprocessDB.getInfo()) {
					// @@错误处理
					CError.buildErr(this, "创建一个工作流任务,查询工作流过程表出错!");
				}
				String tBusiInDate = (String) tTransferData
						.getValueByName("BusiInDate");
				String tBusiInTime = (String) tTransferData
						.getValueByName("BusiInTime");
				if (tBusiInDate == null || tBusiInDate.equals("")) { // 如果业务进入日期未指定则置为系统当前日期
					tBusiInDate = PubFun.getCurrentDate();
				}
				if (tBusiInTime == null || tBusiInTime.equals("")) { // 如果业务进入时间未指定则置为系统当前时间
					tBusiInTime = PubFun.getCurrentTime();
				}

				// 获得timeid
				String tTimeID = tLWprocessDB.getTimeID(); // 时效编码
				String tStandEndDate = "";
				String tStandEndTime = "";
				// 取得TimeFlag和TransitionTime
				if (tTimeID != null && !tTimeID.equals("")) {

					LWTransTimeDB tLWTransTimeDB = new LWTransTimeDB();

					tLWTransTimeDB.setTimeID(tTimeID);
					tLWTransTimeDB.setProcessID(this.ProcessID);
					tLWTransTimeDB.setActivityID(this.AcivityID);
					if (!tLWTransTimeDB.getInfo()) {
						CError.buildErr(this, "没有找到流程" + this.ProcessID
								+ "定义的时效信息!");
						return false;
					} else {
						LWTransTimeSchema tLWTransTimeSchema = new LWTransTimeSchema();
						tLWTransTimeSchema = tLWTransTimeDB.getSchema();
						String tTimeFlag = tLWTransTimeSchema.getTimeFlag(); // 时效类型
						String tTransitionTime = tLWTransTimeSchema
								.getTransitionTime(); // 时效
						// 计算标准结束时间
						TransferData standTransferData = WorkCalendarService
								.getStandEndTimes(tBusiInDate, tBusiInTime,
										tTimeFlag, tTransitionTime);
						if (standTransferData == null) {
							CError.buildErr(this, "计算标准结束时间失败!");
							return false;
						}
						String tCalFlag = (String) standTransferData
								.getValueByName("CalFlag");
						if (tCalFlag.equals("Y")) {
							tStandEndDate = (String) standTransferData
									.getValueByName("StandEndDate");
							tStandEndTime = (String) standTransferData
									.getValueByName("StandEndTime");
						} else {
							tStandEndDate = "";
							tStandEndTime = "";
						}
					}
				}
				// 更新重新计算的标准结束时间
				tLWMissionSchema.setStandEndDate(tStandEndDate);
				tLWMissionSchema.setStandEndTime(tStandEndTime);
				// 更新三个人员字段
				tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
				tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
				String tDefaultOperator = (String) tTransferData
						.getValueByName("DefaultOperator");
				if (tDefaultOperator != null && (!tDefaultOperator.equals(""))) { // 如果业务进入日期未指定则置为系统当前日期
					tLWMissionSchema.setDefaultOperator(tDefaultOperator);
				}
				// 更新除了标准结束时间以外的六个时间
				tLWMissionSchema.setInDate(tBusiInDate);
				tLWMissionSchema.setInTime(tBusiInTime);
				tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
				tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
				tLWMissionSchema.setModifyDate(tLWMissionSchema.getMakeDate());
				tLWMissionSchema.setModifyTime(tLWMissionSchema.getMakeTime());
				// 最重要的更新 ， 状态
				tLWMissionSchema.setActivityStatus("1"); // 0 --
															// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）
				// 1 -- 任务产生完毕待处理，2 -- 处理中，3 -- 处理完成，4 -- 暂停
				map.put(tLWMissionSchema, "UPDATE");
				// VData tVData=new VData();
				// tVData.add(map);
				this.mResult.add(map);
			}
		} else {
			CError.buildErr(this, "请定义" + this.Together + "这种聚合模式！");
			return false;
		}
		return true;
	}

	/**
	 * 节点任务的紧急度
	 * 
	 * @param tLWMissionDB
	 *            LWMissionDB
	 * @param tInputData
	 *            VData
	 * @param tMIssionID
	 *            String
	 * @return boolean
	 * @throws Exception
	 */
	private List<String> CalculateSQL(String tMissionID, VData tInputData) {
		List<String> trPriority = new ArrayList<String>();
		String SQLpriorityID = "";
		String PriorityID = "";
		boolean pritemp = false;
		LWPrioritysqlDB tLWPrioritysqlDB = new LWPrioritysqlDB();
		tLWPrioritysqlDB.setProcessID(this.ProcessID);
		tLWPrioritysqlDB.setversion(this.Version);
		tLWPrioritysqlDB.setactivityID(this.AcivityID);
		if (tLWPrioritysqlDB.getInfo()) {
			LWPrioritysqlSchema tLWPrioritysqlSchema = tLWPrioritysqlDB
					.getSchema();
			pritemp = this.ExecutePriority(tLWPrioritysqlSchema, tInputData,
					tMissionID);
		}
		if (pritemp == false || !tLWPrioritysqlDB.getInfo()) {
			PriorityID = "4";
			SQLpriorityID = "4";
		} else {
			// 更新SQL优先级
			String SQLPriorityt = tLWPrioritysqlDB.getPriorityID();
			PriorityID = "4";
			SQLpriorityID = SQLPriorityt;
		}
		trPriority.add(0, SQLpriorityID);
		trPriority.add(1, PriorityID);
		return trPriority;
	}

	/**
	 * 创建当前节点的所有满足转移条件的后续节点任务
	 * 
	 * @param MissionID
	 *            String
	 * @param SubMissionID
	 *            String
	 * @param tInputData
	 *            VData
	 * @return boolean
	 * @throws Exception
	 */
	private boolean CreateNext(String MissionID, String SubMissionID,
			VData InputData) {
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionID(MissionID);
		tLWMissionDB.setActivityID(this.AcivityID);
		tLWMissionDB.setSubMissionID(SubMissionID);
		if (!tLWMissionDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "创建一个工作流的下一个任务节点,查询工作流任务轨迹表LWMission出错!");
			return false;
		}

		LWActivityDB tnextLWActivityDB = new LWActivityDB();
		for (int i = 0; i < this.NextNodes.size(); i++) {
			String nextActivityID = this.NextNodes.get(i);
			tnextLWActivityDB.setActivityID(nextActivityID);
			if (!tnextLWActivityDB.getInfo()) {
				// @@错误处理
				CError.buildErr(this, "创建一个工作流的下一个任务节点,根据后续活动节点"
						+ nextActivityID + "查询节点活动表LWActivity表出错！");
				return false;
			}
			LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
			tLWProcessTransDB.setProcessID(this.ProcessID);
			tLWProcessTransDB.setTransitionStart(this.AcivityID);
			tLWProcessTransDB.setTransitionEnd(nextActivityID);
			tLWProcessTransDB.setVersion(this.Version);
			LWProcessTransSet tLWProcessTransSet = tLWProcessTransDB.query();
			// 如果tLWProcessTransSet.size()!=1，则出错
			if (tLWProcessTransSet.size() != 1) {
				// @@错误处理
				CError.buildErr(this,
						"创建下一任务节点时，根据当前节点和后续序列查询工作流过程转移表（LWProcessTrans）出错，有多条记录！");
				return false;
			}
			LWProcessTransSchema tLWProcessTransSchema = tLWProcessTransSet
					.get(1);
			if (CheckTransitionCondition(tLWProcessTransSchema, InputData,
					tLWMissionDB)) {
				// 校验W表已存在该任务节点数量
				int tLWSize = 0;
				int tLBSize = 0;
				LWMissionDB tempLWMissionDB = new LWMissionDB();

				tempLWMissionDB.setMissionID(MissionID);
				tempLWMissionDB.setActivityID(nextActivityID);
				tempLWMissionDB.setProcessID(this.ProcessID);
				tempLWMissionDB.setVERSION(this.Version);
				LWMissionSet tempLWMissionSet = tempLWMissionDB.query();
				if (tempLWMissionSet != null) {
					tLWSize = tempLWMissionSet.size();
				} else {
					// @@错误处理
					CError.buildErr(this, "创建一个工作流的下一节点任务,查询任务轨迹表失败!");
					return false;
				}
				// 校验B表已存在该任务节点数量
				LBMissionDB tempLBMissionDB = new LBMissionDB();
				tempLBMissionDB.setMissionID(MissionID);
				tempLBMissionDB.setActivityID(nextActivityID);
				tempLBMissionDB.setProcessID(this.ProcessID);
				tempLBMissionDB.setVERSION(this.Version);
				LBMissionSet tempLBMissionSet = tempLBMissionDB.query();
				if (tempLBMissionSet != null) {
					tLBSize = tempLBMissionSet.size();
				} else {
					// @@错误处理
					CError.buildErr(this, "创建一个工作流的下一节点任务,查询任务轨迹备份表失败!");
					return false;
				}

				String tSubMissionID = String.valueOf(tLWSize + tLBSize + 1);
				
				//BEGIN 全部聚合时，修改SubMissionID的生成方法
				SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
				sqlbv9.sql("select together from lwactivity where activityid = '"+"?nextActivityID?"+"'");
				sqlbv9.put("nextActivityID", nextActivityID);
				String nextTogether = new ExeSQL().getOneValue(sqlbv9);
				if("1".equals(nextTogether)){
					SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
					sqlbv10.sql("select (case when max(a.submissionid) is not null then max(a.submissionid)  else '0' end) from lbmission a where a.missionid = '"+"?MissionID?"+"' and a.activityid = '"+"?nextActivityID?"+"'");
					sqlbv10.put("MissionID", MissionID);
					sqlbv10.put("nextActivityID", nextActivityID);
					tSubMissionID = new ExeSQL().getOneValue(sqlbv10);
					tSubMissionID = String.valueOf(Integer.parseInt(tSubMissionID) + 1);
				}
				//END 全部聚合时，修改SubMissionID的生成方法
				
				ActivityOperatorNode nextActivityOperatorNode = new ActivityOperatorNode(
						nextActivityID, this.ProcessID, this.Version,MissionID,SubMissionID,AcivityID);
				if (nextActivityOperatorNode.Create(MissionID, tSubMissionID,
						InputData)) {
					// 这里直接add结果（VData类型），造成事务提交时是这样的
					// VData{VData[VData(MMap)]} 可能有问题
					VData nextVData = nextActivityOperatorNode.getResult();
					if (nextVData != null) {
						MMap xmap = (MMap) nextVData.getObjectByObjectName(
								"MMap", 0);
						this.mResult.add(xmap);
					}
				} else {
					this.mErrors
							.copyAllErrors(nextActivityOperatorNode.mErrors);
					return false;
				}
			} else {
				logger.debug("@@@@ 扭转条件不满足，没有创建的后续节点的ID是：" + nextActivityID
						+ "，当前处理的节点是：" + this.AcivityID);
			}
		}
		return true;
	}

	/**
	 * 校验转移条件是否满足，此方法由创建下一节点任务CreatNext（）调用
	 * 
	 * @param tLWProcessInstanceSchema
	 *            LWProcessInstanceSchema 输入的当前 工作流过程实例 数据
	 * @param tInputData
	 *            VData 输入的辅助数据
	 * @return boolean
	 * @throws Exception
	 */
	private boolean CheckTransitionCondition(
			LWProcessTransSchema tLWProcessTransSchema, VData tInputData,
			LWMissionDB tLWMissionDB) {
		// 获取传入数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		if (tLWProcessTransSchema == null) {
			// @@错误处理
			CError.buildErr(this, "tLWProcessInstanceSchema为空");
			return false;
		}
		if (tLWProcessTransSchema.getTransitionCondT() == null
				|| tLWProcessTransSchema.getTransitionCondT().trim().equals("")) {
			// 空----表示没有需要执行的动作,即永真，无条件流转
			return true;
		}
		/***** 升级改造，转移表增加永假判断，用于聚合控制，不会实际发生流转 add by LIJIAN at 20130401 ****/
		else if ("F".equals(tLWProcessTransSchema.getTransitionCondT())) {
			// 永假，用于做聚合控制，不会实际发生流转
			return false;
		} else if (tLWProcessTransSchema.getTransitionCondT().equals("0")) {
			// 0 -- 默认，表示条件为Where子句。
			PubCalculator tPubCalculator = new PubCalculator();
			// 准备计算要素
			Vector tVector = (Vector) tTransferData.getValueNames();
			for (int j = 0; j < tVector.size(); j++) {
				String ttName = (String) tVector.get(j);
			}

			for (int i = 0; i < tVector.size(); i++) {
				String tName = (String) tVector.get(i);
				String tValue = new String("");
				Object tValueObject = tTransferData.getValueByName(tVector
						.get(i));
				if (tValueObject != null) {
					tValue = (String) tValueObject;
				}
				tValueObject = null;
				tPubCalculator.addBasicFactor(tName, tValue);
			}

			// 准备计算SQL
			tPubCalculator.setCalSql(tLWProcessTransSchema.getTransitionCond());
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
							ExeSQL tExeSQL = new ExeSQL();
							String tReSult = tExeSQL.getOneValue(strTemp); // 转移条件满足时一定会有返回值,转移条件SQL只是Select语句
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
								CError.buildErr(this, "执行SQL语句：" + strTemp
										+ "失败!");
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
				} catch (Exception ex) {
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

		} else if (tLWProcessTransSchema.getTransitionCondT().equals("2")) { // 2
																				// --
																				// 表示转移条件为规则引擎。
			String ruleName = tLWProcessTransSchema.getTransitionCond();
			LRTemplateSchema lts = new LRTemplateSchema();
			// lts.setRuleName(ruleid);
			LRTemplateDB ltd = lts.getDB();
			// String RULENAME = ltd.getSQLS(ruleid);
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql("SELECT SQLParameter FROM LRTemplate WHERE   RULENAME= '"
					+ "?ruleName?" + "'");
			sqlbv11.put("ruleName", ruleName);
			String SQLParameter = tExeSQL
					.getOneValue(sqlbv11);
			String[] SQLParameters = SQLParameter.split("//,");
			BOMsqls boms = new BOMsqls();
			BOMConTr bomct = boms.getBom(tLWProcessTransSchema, tInputData,
					tLWMissionDB, SQLParameters);
			Calculator ca = new Calculator();
			ArrayList bom = new ArrayList();
			bom.add(bomct);
			ca.setBOMList(bom);
			ca.setCalCode(ruleName);
			String result = ca.calculate();
			if (result == "0" || result.equals("0")) { // 满足转移条件
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * 分配节点任务 WorkFlowBL.java中操作符为“Apply”
	 * 
	 * @param String
	 *            MissionID
	 * @param String
	 *            SubMissionID
	 * @param VData
	 *            InputData
	 * @return boolean
	 * @throws Exception
	 */
	public boolean Allot(String MissionID, String SubMissionID, VData InputData) {
		this.mResult.clear();
		GlobalInput tGlobalInput = (GlobalInput) InputData
				.getObjectByObjectName("GlobalInput", 0);
		if (MissionID == null || MissionID.trim().equals("")) {
			CError.buildErr(this, "分配任务，传输的节点任务MissionID为空!");
			return false;
		}
		if (SubMissionID == null || SubMissionID.trim().equals("")) {
			CError.buildErr(this, "分配任务，传输的节点子任务SubMissionID为空!");
			return false;
		}
		// if(ActivityID == null ||ActivityID.trim().equals(""))
		// {
		// CError tError = new CError();
		// tError.moduleName = "ActivityOperatorNode";
		// tError.functionName = "Allot";
		// tError.errorMessage = "分配任务，传入的节点任务ActivityID为空!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setActivityID(this.AcivityID);
		tLWMissionDB.setMissionID(MissionID);
		tLWMissionDB.setSubMissionID(SubMissionID);
		if (!tLWMissionDB.getInfo()) {
			CError.buildErr(this, "分配节点任务，查询工作流任务轨迹表（LWMission）表出错!");
			return false;
		}
		LWActivityDB tLWActivityDB = new LWActivityDB();
		tLWActivityDB.setActivityID(this.AcivityID);
		if (!tLWActivityDB.getInfo()) {
			CError.buildErr(this, "分配节点任务，查询活动节点表（LWActivity）表出错!");
			return false;
		}
		LWActivitySchema tLWActivitySchema = tLWActivityDB.getSchema();
		LWMissionSchema tLWMissionSchema = tLWMissionDB.getSchema();
		String mOperator = tGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			CError.buildErr(this, "分配节点任务，传输的公共数据申请人为空!");
			return false;
		}
		if (tLWMissionSchema.getDefaultOperator() != null
				&& !tLWMissionSchema.getDefaultOperator().equals("")) {
			CError.buildErr(this, "该单已经被申请.");
			return false;
		}
		tLWMissionSchema.setDefaultOperator(mOperator);
		tLWMissionSchema.setInDate(PubFun.getCurrentDate());
		tLWMissionSchema.setInTime(PubFun.getCurrentTime());
		tLWMissionSchema.setModifyDate(tLWMissionSchema.getInDate());
		tLWMissionSchema.setModifyTime(tLWMissionSchema.getInTime());

		MMap map = new MMap();
		map.put(tLWMissionSchema, "UPDATE");
		mResult.add(map);
		if (!ApplyAction(tLWActivitySchema, InputData)) {
			return false;
		}
		return true;
	}

	/**
	 * 执行节点任务，做两方面的事情：一、加锁；二、执行当前节点任务。
	 * 
	 * @param String
	 *            MissionID
	 * @param String
	 *            SubMissionID
	 * @param VData
	 *            InputData
	 * @return boolean
	 * 
	 */
	private boolean Execute(String MissionID, String SubMissionID,
			VData InputData) {
		// 查询工作流任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionID(MissionID);
		tLWMissionDB.setSubMissionID(SubMissionID);
		tLWMissionDB.setActivityID(this.AcivityID);
		if (!tLWMissionDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "不满足该节点任务的执行条件. ");
			return false;
		}
		LWMissionSchema tnewLWMissionSchema = tLWMissionDB.getSchema();
		// 校验聚合节点
		if (checkTogether(MissionID, InputData,SubMissionID,this.AcivityID)) {
			// 加锁，
			if (!LockActivity(tnewLWMissionSchema, InputData)) {
				return false;
			}
			// 查询指定活动节点表(工作流活动表)
			LWActivityDB tLWActivityDB = new LWActivityDB();
			tLWActivityDB.setActivityID(this.AcivityID);
			if (!tLWActivityDB.getInfo()) {
				// @@错误处理
				CError.buildErr(this, "执行节点任务,查询工作流活动节点表出错!");
				return false;
			}
			LWActivitySchema tLWActivitySchema = tLWActivityDB.getSchema();
			if (!ExecuteBeforeInitDuty(tLWActivitySchema, InputData)) {
				return false;
			}
			// 执行AfterInit任务
			if (!ExecuteAfterInitDuty(tLWActivitySchema, InputData)) {
				return false;
			}
			// 执行BeforeEnd任务
			if (!ExecuteBeforeEndDuty(tLWActivitySchema, InputData)) {
				return false;
			}
			// 执行AfterEnd任务
			if (!ExecuteAfterEndDuty(tLWActivitySchema, InputData)) {
				return false;
			}
//			LWMissionSchema tLWMissionSchema = tLWMissionDB.getSchema();
//			GlobalInput tGlobalInput = ((GlobalInput) InputData
//					.getObjectByObjectName("GlobalInput", 0));
//			if (tLWMissionSchema.getDefaultOperator() != null
//					&& !tLWMissionSchema.getDefaultOperator().equals(
//							tGlobalInput.Operator)) {
//				tLWMissionSchema.setDefaultOperator(tGlobalInput.Operator);
//				MMap map = new MMap();
//				map.put(tLWMissionSchema, "UPDATE");
//				VData xVData = new VData();
//				xVData.add(map);
//				mResult.add(xVData);
//			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 加锁， 此方法是执行节点任务Execute(String,String,String)调用的，作用:
	 * 防止当前节点任务的后续节点“处理”时可能触发的操作当前节点任务动作， 如后续节点的聚合模式为3——单一执行，则后续节点
	 * 处理时可能触发删除其前驱任务的动作
	 * 
	 * @param LWMissionSchema
	 *            tLWMissionSchema
	 * @param VData
	 *            InputData
	 * @return boolean
	 * 
	 */
	private boolean LockActivity(LWMissionSchema tLWMissionSchema,
			VData tInputData) {
		GlobalInput tGlobalInput = ((GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0));
		LWLockDB tLWLockDB = new LWLockDB();
		tLWLockDB.setLockActivityID(tLWMissionSchema.getActivityID());
		tLWLockDB.setLockType("9");
		tLWLockDB.setMissionID(tLWMissionSchema.getMissionID());
		tLWLockDB.setSubMissionID(tLWMissionSchema.getSubMissionID());
		if (tLWLockDB.getInfo()) {
			return true;

		} else {
			LWLockSchema tLWLockSchema = new LWLockSchema();
			tLWLockSchema.setLockActivityID(tLWMissionSchema.getActivityID());
			// LWLock.LockStatus跟lwmission的节点任务状态字段意义类似，值域一致0 ：-- 任务产生中，1 --
			// 任务产生完毕待处理，2 -- 处理中，3 -- 处理完成，4 -- 暂停
			// 此时任务还没有产生，故状态取0
			tLWLockSchema.setLockStatus("2");
			// 区别业务类 和创建时加的锁，所类型为“9”
			tLWLockSchema.setLockType("9");
			tLWLockSchema.setMakeDate(PubFun.getCurrentDate());
			tLWLockSchema.setMakeTime(PubFun.getCurrentTime());
			tLWLockSchema.setMissionID(tLWMissionSchema.getMissionID());
			tLWLockSchema.setSubMissionID(tLWMissionSchema.getSubMissionID());
			tLWLockSchema.setOperator(tGlobalInput.Operator);
			tLWLockSchema.setProcessID(tLWMissionSchema.getProcessID());
			// 此时加锁是防止同一时间处理的平行节点产生同一个后续节点，timeout没有具体意义。故直接取0;
			tLWLockSchema.setTimeOut(0);
			LWLockDB tNewLWLockDB = tLWLockSchema.getDB();
			// 加锁的动作应该立即执行,
			tNewLWLockDB.insert();
		}
		return true;
	}

	/**
	 * 校验聚合模式 此方法是由执行当前节点任务调用的
	 * 
	 * @param String
	 *            MissionID
	 * @param VData
	 *            InputData
	 * @return boolean
	 * 
	 */
	private boolean checkTogether(String MissionID, VData InputData,String SubMissionID,String ActivityID) {
		LWMissionDB togetherLWMissionDB = new LWMissionDB();
		togetherLWMissionDB.setMissionID(MissionID);
		togetherLWMissionDB.setProcessID(this.ProcessID);
		togetherLWMissionDB.setVERSION(this.Version);
		// 如果当前节点的聚合模式为空，即没有聚合什么也不做
		if ("".equals(this.Together) || null == this.Together) {
			return true;
		}
		// 如果当前节点的聚合模式为1——全部执行，查看其前驱的状态
		else if ("1".equals(this.Together)) {
			String message = "";
			for (int i = 0; i < this.PriviousNodes.size(); i++) {
				// 递归查询前驱结点.

				togetherLWMissionDB.setActivityID(this.PriviousNodes.get(i));
				//
				LWMissionSet togetherLWMissionSet = togetherLWMissionDB.query();
				// 如果前驱任务还有未完成的。给出提示：未完成的任务名和业务属性字段1
				if (togetherLWMissionSet.size() > 0) {
					for (int k = 0; k < togetherLWMissionSet.size(); k++) {
						LWMissionSchema togetherLWMissionSchema = togetherLWMissionSet
								.get(k + 1);
						LWFieldMapDB togetherLWFieldMapDB = new LWFieldMapDB();
						togetherLWFieldMapDB.setActivityID(this.PriviousNodes
								.get(i));
						togetherLWFieldMapDB
								.setDestFieldName(togetherLWMissionSchema
										.getMissionProp1());
						LWFieldMapSet togetherLWFieldMapSet = togetherLWFieldMapDB
								.query();

						String tSQLfunctionid = "select codename from ldcode where codetype='functionid' and code in (select functionid from lwactivity where activityid='"
								+ "?activityid?" + "')";
						SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
						sqlbv12.sql(tSQLfunctionid);
						sqlbv12.put("activityid", this.PriviousNodes.get(i));
						String functionidResult = new ExeSQL()
								.getOneValue(sqlbv12);
						String arry = "功能名: " + functionidResult;
						message = message + arry;
					}
					CError.buildErr(this, "校验聚合模式不通过——还有未执行的前驱任务," + message);
					return false;
				}
			}
			return true;
		}
		// 如果当前节点的聚合模式为2——部分执行，不用做任何处理
		else if ("2".equals(this.Together)) {
			return true;
		}
		// 如果当前节点聚合模式为3——单一执行，删除未完成的前驱的节点任务
		else if ("3".equals(this.Together)) {
			for (int j = 0; j < this.PriviousNodes.size(); j++) {
				togetherLWMissionDB.setActivityID(this.PriviousNodes.get(j));
				LWMissionSet togetherLWMissionSet = togetherLWMissionDB.query();
				
				//BEGIN 单一聚合时，删除LWLock表  主键 MISSIONID, SUBMISSIONID, LOCKACTIVITYID, LOCKTYPE
				MMap m  = new MMap();
				LWLockDB tLWLockDB1 = new LWLockDB();
				tLWLockDB1.setLockActivityID(ActivityID);
				tLWLockDB1.setMissionID(MissionID);
				tLWLockDB1.setSubMissionID(SubMissionID);
				tLWLockDB1.setLockType("8");
				LWLockSet tLWLockSet1 = tLWLockDB1.query();
				m.put(tLWLockSet1, "DELETE");
//				m.put("delete from lwlock where missionid = '"+MissionID+"' " +
//						" and submissionid = '"+SubMissionID+"' " +
//						" and  lockactivityid = '"+ActivityID+"' and locktype = '8'", "DELETE");
				mResult.add(m);
				//END 单一聚合时，删除LWLock表  主键 MISSIONID, SUBMISSIONID, LOCKACTIVITYID, LOCKTYPE
				
				// 有未完成的前驱任务删除之
				if (togetherLWMissionSet.size() > 0) {
					for (int k = 0; k < togetherLWMissionSet.size(); k++) {
						LWMissionSchema priviousSchema = togetherLWMissionSet
								.get(k + 1);
						LWLockDB tLWLockDB = new LWLockDB();
						tLWLockDB.setLockActivityID(priviousSchema
								.getActivityID());
						tLWLockDB.setMissionID(priviousSchema.getMissionID());
						tLWLockDB.setSubMissionID(priviousSchema
								.getSubMissionID());
						LWLockSet tLWLockSet = tLWLockDB.query();
						MMap map = new MMap();
						// 如果没有加锁的话
						if (tLWLockSet.size() == 0) {
							map.put(priviousSchema, "DELETE");
							//BEGIN 备份单一聚合节点删掉的LWMission
							LBMissionSchema mLBMissionSchema = new LBMissionSchema();
							String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
							Reflections tReflections = new Reflections();
							tReflections.transFields(mLBMissionSchema, priviousSchema);
							mLBMissionSchema.setSerialNo(tSerielNo);
							mLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
							TransferData tTransferData = (TransferData) InputData.getObjectByObjectName("TransferData", 0);
							String tBusiOutDate = (String) tTransferData
									.getValueByName("BusiOutDate");
							String tBusiOutTime = (String) tTransferData
									.getValueByName("BusiOutTime");
							if (tBusiOutDate == null || tBusiOutDate.equals("")) {
								tBusiOutDate = PubFun.getCurrentDate();
							}
							if (tBusiOutTime == null || tBusiOutTime.equals("")) {
								tBusiOutTime = PubFun.getCurrentTime();
							}
							mLBMissionSchema.setOutDate(tBusiOutDate);
							mLBMissionSchema.setOutTime(tBusiOutTime);
							GlobalInput tGlobalInput = (GlobalInput) InputData.getObjectByObjectName("GlobalInput", 0);
							mLBMissionSchema.setLastOperator(tGlobalInput.Operator);
							mLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
							mLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
							map.put(mLBMissionSchema, "INSERT");
							//END 备份单一聚合节点删掉的LWMission
							this.mResult.add(map);
						} else {
							for (int i = 1; i <= tLWLockSet.size(); i++) {
								// 如果加的锁是非执行时加的或者业务类型加的。
								if (!("9".equals(tLWLockSet.get(i)
										.getLockType()) || "1"
										.equals(tLWLockSet.get(i).getLockType()))) {
									map.put(priviousSchema, "DELETE");
									//BEGIN 备份单一聚合节点删掉的LWMission
									LBMissionSchema mLBMissionSchema = new LBMissionSchema();
									String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
									Reflections tReflections = new Reflections();
									tReflections.transFields(mLBMissionSchema, priviousSchema);
									mLBMissionSchema.setSerialNo(tSerielNo);
									mLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
									TransferData tTransferData = (TransferData) InputData.getObjectByObjectName("TransferData", 0);
									String tBusiOutDate = (String) tTransferData
											.getValueByName("BusiOutDate");
									String tBusiOutTime = (String) tTransferData
											.getValueByName("BusiOutTime");
									if (tBusiOutDate == null || tBusiOutDate.equals("")) {
										tBusiOutDate = PubFun.getCurrentDate();
									}
									if (tBusiOutTime == null || tBusiOutTime.equals("")) {
										tBusiOutTime = PubFun.getCurrentTime();
									}
									mLBMissionSchema.setOutDate(tBusiOutDate);
									mLBMissionSchema.setOutTime(tBusiOutTime);
									GlobalInput tGlobalInput = (GlobalInput) InputData.getObjectByObjectName("GlobalInput", 0);
									mLBMissionSchema.setLastOperator(tGlobalInput.Operator);
									mLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
									mLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
									map.put(mLBMissionSchema, "INSERT");
									this.mResult.add(map);
								}
							}
						}
					}
				}
			}
			return true;
		} else {
			CError.buildErr(this, "目前不支持这样的聚合模式： " + this.Together
					+ " 请联系开发人员。");
			return false;
		}
	}

	public boolean DeleteLastLocal(String tMissionID, String tSubMissionID,
			VData tInputData) {
		String LastActivityIDSql = "select case when transitionstart = transitionend then transitionstart else '1' end From LWProcessTrans where transitionstart in(select x.transitionstart from LWProcessTrans x where x.transitionend='"
				+ "?AcivityID?"
				+ "'  and x.processid='"
				+ "?ProcessID?"
				+ "') and processid='" + "?ProcessID?" + "'";
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(LastActivityIDSql);
		sqlbv13.put("AcivityID", this.AcivityID);
		sqlbv13.put("ProcessID", this.ProcessID);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv13);
		// 目前processID和BusiType存在一对一的关系。只取（1，1）
		for (int i = 1; i < tSSRS.getMaxRow(); i++) {
			if (tSSRS.GetText(i, 1).equals("1")) {
				break;
			}
			String tLastActivityID = tSSRS.GetText(i, 1);
			if (!Delete(tMissionID, tSubMissionID, tInputData, tLastActivityID)) {
				UnusualUnlockMission(tMissionID);
				return false;
			}

		}
		return true;
	}

	/**
	 * 删除节点任务
	 * 
	 * @param String
	 *            MissionID
	 * @param String
	 *            SubMissionID
	 * @param VData
	 *            InputData
	 * @return boolean
	 * 
	 */
	public boolean Delete(String tMissionID, String tSubMissionID,
			VData tInputData, String ActivityID) {
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		String dFlag = (String) tTransferData
				.getValueByName("DeleteActivity005");
		if (dFlag == null || dFlag.trim().equals("")) {
		} else {
			return true;
		}

		LWActivityDB tLWActivityDB = new LWActivityDB();
		tLWActivityDB.setActivityID(AcivityID);
		if (!tLWActivityDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "执行节点任务,查询活动节点表LWActivity出错!");
			return false;
		}
		LWActivitySchema tLWActivitySchema = tLWActivityDB.getSchema();
		// 查询工作流任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();

		tLWMissionDB.setMissionID(tMissionID);
		tLWMissionDB.setActivityID(AcivityID);
		tLWMissionDB.setSubMissionID(tSubMissionID);

		if (!tLWMissionDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "执行节点任务,查询工作流任务轨迹表LWMission出错!");
			return false;
		}

		LWMissionSchema tLWMissionSchema = tLWMissionDB.getSchema();

		// 准备任务节点备份表数据
		LBMissionSchema mLBMissionSchema = new LBMissionSchema();
		String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);

		Reflections tReflections = new Reflections();
		tReflections.transFields(mLBMissionSchema, tLWMissionSchema);
		/******
		 * 工作流改造，增加更新本工作流操作，根据transfer中是否有fieldmap中的字段描述来更新本工作流 add by LIJIAN at
		 * 20130401
		 *****/
		LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
		tLWFieldMapDB.setActivityID(AcivityID);
		LWFieldMapSet tLWFieldMapSet = tLWFieldMapDB.query();
		if (tLWFieldMapSet != null && tLWFieldMapSet.size() > 0) {
			for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
				String tMissionProp = tTransferData
						.getValueByName(tLWFieldMapSet.get(i)
								.getSourFieldName()) == null ? ""
						: (String) tTransferData.getValueByName(tLWFieldMapSet
								.get(i).getSourFieldName());
				if (tMissionProp != null && !"".equals(tMissionProp)) {
					String tDestFieldName = tLWFieldMapSet.get(i)
							.getDestFieldName();
					mLBMissionSchema.setV(tDestFieldName, tMissionProp);
				}
			}
		}
		/********************************************* end ***************************************************************************/
		mLBMissionSchema.setSerialNo(tSerielNo);
		mLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
		String tBusiOutDate = (String) tTransferData
				.getValueByName("BusiOutDate");
		String tBusiOutTime = (String) tTransferData
				.getValueByName("BusiOutTime");
		if (tBusiOutDate == null || tBusiOutDate.equals("")) {
			tBusiOutDate = PubFun.getCurrentDate();
		}
		if (tBusiOutTime == null || tBusiOutTime.equals("")) {
			tBusiOutTime = PubFun.getCurrentTime();
		}
		mLBMissionSchema.setOutDate(tBusiOutDate);
		mLBMissionSchema.setOutTime(tBusiOutTime);

		mLBMissionSchema.setLastOperator(tGlobalInput.Operator);
		mLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
		mLBMissionSchema.setMakeTime(PubFun.getCurrentTime());

		// 查询工作流锁表数据——此时加的锁是业务类里加的。
		LWLockDB tLWLockDB = new LWLockDB();
		tLWLockDB.setMissionID(tMissionID);
		tLWLockDB.setSubMissionID(tSubMissionID);
		tLWLockDB.setLockActivityID(AcivityID);
		tLWLockDB.setLockType("1");
		LWLockSchema mLWLockSchema = null;
		if (tLWLockDB.getInfo()) {
			mLWLockSchema = tLWLockDB.getSchema();
		}
		// 删除执行节点任务时加的锁。如果已经加了锁的话
		LWLockDB tnewLWLockDB = new LWLockDB();
		tnewLWLockDB.setMissionID(tMissionID);
		tnewLWLockDB.setSubMissionID(tSubMissionID);
		tnewLWLockDB.setLockActivityID(AcivityID);
		tnewLWLockDB.setLockType("9");
		LWLockSchema newLWLockSchema = null;
		if (tnewLWLockDB.getInfo()) {
			newLWLockSchema = tnewLWLockDB.getSchema();
		}
		// 删除创建节点任务时加的锁。如果已经加了锁的话
		LWLockDB tnewcreateLWLockDB = new LWLockDB();
		tnewcreateLWLockDB.setMissionID(tMissionID);
		tnewcreateLWLockDB.setSubMissionID(tSubMissionID);
		tnewcreateLWLockDB.setLockActivityID(AcivityID);
		tnewcreateLWLockDB.setLockType("8");
		LWLockSchema tnewcreateLWLockSchema = null;
		if (tnewcreateLWLockDB.getInfo()) {
			tnewcreateLWLockSchema = tnewcreateLWLockDB.getSchema();
		}
		if (tLWMissionSchema != null && mLBMissionSchema != null) {
			MMap map = new MMap();
			map.put(tLWMissionSchema, "DELETE");
			map.put(mLBMissionSchema, "INSERT");
			if (mLWLockSchema != null) {
				map.put(mLWLockSchema, "DELETE");
			}
			if (newLWLockSchema != null) {
				map.put(newLWLockSchema, "DELETE");
			}
			if (tnewcreateLWLockSchema != null) {
				map.put(tnewcreateLWLockSchema, "DELETE");
			}
			
			this.mResult.add(map);
		} else {
			CError.buildErr(this,
					"删除节点任务，LWMissionSchema或者LBMissionSchema，虽然这个错误发生的几率很小！");
			return false;
		}
		if (!DeleteAction(tLWActivitySchema, tInputData)) {
			return false;
		}
		return true;
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
	public boolean ExecuteBeforeInitDuty(LWActivitySchema tLWActivitySchema,
			VData tInputData) {
		// 获取传入数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		// 校验数据有效性
		if (tLWActivitySchema == null) {
			// @@错误处理
			CError.buildErr(this, "执行节点进入前任务,但没有传入当前任务信息!");
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
			// logger.debug("SQL : " + strSQL);
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
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp); // 完成执行节点任务前的初始任务只是一些Insert||Delete||Update语句,所以无返回数据集合
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
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
				Class<?> tClass = Class.forName(tLWActivitySchema
						.getBeforeInit());
				WorkFlowService tWorkFlowService = (WorkFlowService) tClass
						.newInstance();

				// 准备数据
				String strOperate = tLWActivitySchema.getActivityID();
				tInputData.add(tLWActivitySchema);
				if (!tWorkFlowService.submitData(tInputData, strOperate)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tWorkFlowService.getErrors());
					return false;
				}
				// 保存待提交数据
				VData tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
					MMap xmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
					mResult.add(xmap);
				}
				return true;
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
	public boolean ExecuteAfterInitDuty(LWActivitySchema tLWActivitySchema,
			VData tInputData) {

		// 获取传入数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);

		// 校验数据有效性
		if (tLWActivitySchema == null) {
			// @@错误处理
			CError.buildErr(this, "执行节点进入后任务,但没有传入当前任务信息!");
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
				try {
					String strTemp = "";
					char cTemp = ' ';
					for (int nIndex = 0; nIndex < strSQL.length(); nIndex++) {
						cTemp = strSQL.charAt(nIndex);
						if (cTemp == ';' && !strTemp.equals("")) {
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp); // 完成执行节点任务的核心任务只是一些Insert||Delete||Update语句,所以无返回数据集合
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
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
					return false;
				}

				// 保存待提交数据
				VData tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
					MMap xmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
					if (xmap == null) {
						TransferData hTransferData = (TransferData) tVData
								.getObjectByObjectName("TransferData", 0);
						if (hTransferData != null) {
							// this.mResult.add(hTransferData)
						}
						;
					} else {
						this.mResult.add(xmap);
						TransferData hTransferData = (TransferData) tVData
								.getObjectByObjectName("TransferData", 0);
						if (hTransferData != null) {
							// this.mResult.add(hTransferData);
						}
					}

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
	public boolean ExecuteBeforeEndDuty(LWActivitySchema tLWActivitySchema,
			VData tInputData) {
		// 获取传入数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		// 校验数据有效性
		if (tLWActivitySchema == null) {
			// @@错误处理
			CError.buildErr(this, "执行节点完成前任务,但没有传入当前任务信息!");
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
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp); // 完成执行节点任务的核心任务后的善后任务只是一些Insert||Delete||Update语句,所以无返回数据集合
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
								return false;
							}
							strTemp = "";
						} else {
							strTemp += String.valueOf(cTemp);
						}
					}
					return true;
				} catch (Exception ex) {
					ex.printStackTrace();
					return false;
				}
			}
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
					return false;
				}
				// 保存待提交数据
				VData tVData = new VData();
				tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
					MMap xmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
					this.mResult.add(xmap);
				}
				return true;
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
	public boolean ExecuteAfterEndDuty(LWActivitySchema tLWActivitySchema,
			VData tInputData) {
		// 获取传入数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		// 校验数据有效性
		if (tLWActivitySchema == null) {
			// @@错误处理
			CError.buildErr(this, "执行节点结束后任务,但没有传入当前任务ID信息!");
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
				try {
					String strTemp = "";
					char cTemp = ' ';
					for (int nIndex = 0; nIndex < strSQL.length(); nIndex++) {
						cTemp = strSQL.charAt(nIndex);
						if (cTemp == ';' && !strTemp.equals("")) {
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp); // 完成执行节点任务的核心任务只是一些Insert||Delete||Update语句,所以无返回数据集合
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
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
					return false;
				}
				// 保存待提交数据
				VData tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
					MMap xmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
					this.mResult.add(xmap);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 完成创建动作业务方面的操作
	 * 
	 * @param tLWActivitySchema
	 *            LWActivitySchema
	 * @param tInputData
	 *            VData
	 * @return boolean
	 */
	private boolean CreateAction(LWActivitySchema tLWActivitySchema,
			VData tInputData) {
		// 获取传入数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		if (tLWActivitySchema == null) {
			// @错误描述
			CError.buildErr(this, "执行创建、分配、删除节点任务前动作，传入的LWActivtySchema为空！");
			return false;
		}
		// 取得创建service 处理创建后
		String tCreateActionType = tLWActivitySchema.getCreateActionType();
		// tServiceActionType 为空 ，没有创建的Service
		if (tCreateActionType == null || tCreateActionType.equals("")) {
			return true;
		}
		/**
		 * tCreateActionType = 0 表示创建动作后是一个SQL语句 而且此SQL为Insert||Delete||Update语句
		 * */
		else if (tCreateActionType.equals("0")
				|| (tCreateActionType.compareTo("0") == 0)) {
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
			tPubCalculator.setCalSql(tLWActivitySchema.getCreateAction());
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
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp);
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
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
		}
		/** tCreateActionType = 1 表示分配动作后是一个服务类 */
		else if (tCreateActionType.equals("1")
				|| (tCreateActionType.compareTo("1") == 0)) {
			try {
				Class tClass = Class.forName(tLWActivitySchema
						.getCreateAction());
				WorkFlowService tWorkFlowService = (WorkFlowService) tClass
						.newInstance();
				// 准备数据
				String strOperate = tLWActivitySchema.getActivityID();
				tInputData.add(tLWActivitySchema);
				if (!tWorkFlowService.submitData(tInputData, strOperate)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tWorkFlowService.getErrors());
					return false;
				}
				// 保存待提交数据
				VData tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
//					this.mResult.add(tVData);
					MMap xmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
					this.mResult.add(xmap);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return true;
		
	}

	/**
	 * 分配动作业务接口
	 * 
	 * @param tLWActivitySchema
	 *            LWActivitySchema
	 * @param tInputData
	 *            VData
	 * @return boolean
	 */
	private boolean ApplyAction(LWActivitySchema tLWActivitySchema,
			VData tInputData) {
		if (tLWActivitySchema == null) {
			// @错误描述
			CError.buildErr(this, "执行创建、分配、删除节点任务后动作，传入的LWActivtySchema为空！");
			return false;
		}
		// 获取传入数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);

		// tApplyType 为空 ，没有创建的Service
		if (tLWActivitySchema.getApplyActionType() == null
				|| tLWActivitySchema.getApplyActionType().trim().equals("")) {
			return true;
		}
		/** tApplyType = 0 表示分配动作后是一个SQL语句 */
		else if (tLWActivitySchema.getApplyActionType().equals("0")) {
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
			tPubCalculator.setCalSql(tLWActivitySchema.getApplyAction());
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
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp);
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
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
		}
		/** tApplyType = 1表示分配动作前是一个服务类 */
		else if (tLWActivitySchema.getApplyActionType().equals("1")) {
			try {
				Class tClass = Class
						.forName(tLWActivitySchema.getApplyAction());
				WorkFlowService tWorkFlowService = (WorkFlowService) tClass
						.newInstance();
				// 准备数据
				String strOperate = tLWActivitySchema.getActivityID();
				tInputData.add(tLWActivitySchema);
				if (!tWorkFlowService.submitData(tInputData, strOperate)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tWorkFlowService.getErrors());
					return false;
				}
				// 保存待提交数据
				VData tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
					MMap xmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
					mResult.add(xmap);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除动作业务方面
	 * 
	 * @param tLWActivitySchema
	 *            LWActivitySchema
	 * @param tInputData
	 *            VData
	 * @return boolean
	 */
	private boolean DeleteAction(LWActivitySchema tLWActivitySchema,
			VData tInputData) {
		// 获取传入数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		if (tLWActivitySchema == null) {
			// @错误描述
			CError.buildErr(this, "执行删除动作，传入的LWActivtySchema为空！");
			return false;
		}
		// tServiceActionType 为空 ，没有创建的Service
		if (tLWActivitySchema.getDeleteActionType() == null
				|| tLWActivitySchema.getDeleteActionType().equals("")) {
			return true;
		}
		/**
		 * tDeleteActionType = 0 表示删除动作后是一个SQL语句 而且此SQL为Insert||Delete||Update语句
		 * */
		else if (tLWActivitySchema.getDeleteActionType().equals("0")) {
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
			tPubCalculator.setCalSql(tLWActivitySchema.getDeleteAction());
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
							ExeSQL tExeSQL = new ExeSQL();
							tExeSQL.execUpdateSQL(strTemp);
							if (tExeSQL.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tExeSQL.mErrors);
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
		}
		/** tDeleteActionType = 1 表示分配动作前是一个服务类 */
		else if (tLWActivitySchema.getDeleteActionType().equals("1")) {
			try {
				Class tClass = Class.forName(tLWActivitySchema
						.getDeleteAction());
				WorkFlowService tWorkFlowService = (WorkFlowService) tClass
						.newInstance();
				// 准备数据
				String strOperate = tLWActivitySchema.getActivityID();
				tInputData.add(tLWActivitySchema);
				if (!tWorkFlowService.submitData(tInputData, strOperate)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tWorkFlowService.getErrors());
					return false;
				}
				// 保存待提交数据
				VData tVData = tWorkFlowService.getResult();
				if (tVData != null && tVData.size() > 0) {
					//this.mResult.add(tVData);
					MMap xmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
					this.mResult.add(xmap);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 计算SQL优先级
	 * 
	 * @param tLWActivitySchema
	 *            LWActivitySchema
	 * @param tInputData
	 *            VData
	 * @return boolean
	 * @throws Exception
	 */
	private boolean ExecutePriority(LWPrioritysqlSchema tLWPrioritysqlSchema,
			VData tInputData, String tMissionID) {
		boolean temp = true;

		// 获取传入数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		if (tLWPrioritysqlSchema == null) {
			// @@错误处理
			CError.buildErr(this, "tLWProcessInstanceSchema为空");
			return false;
		}
		if (tLWPrioritysqlSchema.getPrioritySQL() == null
				|| tLWPrioritysqlSchema.getPrioritySQL().trim().equals("")) {
			// 空----表示没有需要执行的动作。
			return true;
		}
		PubCalculator tPubCalculator = new PubCalculator();
		// 准备计算要素
		Vector tVector = (Vector) tTransferData.getValueNames();
		for (int j = 0; j < tVector.size(); j++) {
			String ttName = (String) tVector.get(j);
		}

		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = new String("");
			Object tValueObject = tTransferData.getValueByName(tVector.get(i));
			if (tValueObject != null) {
				tValue = (String) tValueObject;
			}
			tValueObject = null;
			tPubCalculator.addBasicFactor(tName, tValue);
		}

		// 准备计算SQL
		tPubCalculator.setCalSql(tLWPrioritysqlSchema.getPrioritySQL());
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
						ExeSQL tExeSQL = new ExeSQL();
						String tReSult = tExeSQL.getOneValue(strTemp); // 优先级条件满足时一定会有返回值,优先级条件SQL只是Select语句
						if (tExeSQL.mErrors.needDealError()) {
							// @@错误处理
							this.mErrors.copyAllErrors(tExeSQL.mErrors);
							CError.buildErr(this, "执行SQL语句：" + strTemp + "失败!");
							return false;
						}
						if (tReSult == null || tReSult.equals("")) { // 不满足优先级条件
							return false;
						}
						strTemp = "";
					} else {
						strTemp += String.valueOf(cTemp);
					}
				} // end of for
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		return temp;
	}

	public static Logger getLogger() {
		return logger;
	}

	public VData getResult() {
		return mResult;
	}

	public String getVersion() {
		return Version;
	}

	public String getProcessID() {
		return ProcessID;
	}

	public String getActivityFlag() {
		return ActivityFlag;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public String getAcivityID() {
		return AcivityID;
	}

	public String getBusiType() {
		return BusiType;
	}

	public String getFunctionID() {
		return FunctionID;
	}

	public String getTogether() {
		return Together;
	}

	public List<String> getPriviousNodes() {
		return PriviousNodes;
	}

	public List<String> getNextNodes() {
		return NextNodes;
	}
}
