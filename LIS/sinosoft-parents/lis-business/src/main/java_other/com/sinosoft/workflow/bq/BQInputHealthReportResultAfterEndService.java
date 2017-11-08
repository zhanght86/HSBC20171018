package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

public class BQInputHealthReportResultAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(BQInputHealthReportResultAfterEndService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
	private Reflections mReflections = new Reflections();

	/** 执行保全工作流特约活动表任务0000001011 */
	/** 工作流任务节点表 */
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();
	private LWMissionSchema mInitLWMissionSchema = new LWMissionSchema(); // 保全人工核保工作流起始节点
	private LWMissionSet mLWMissionSet = new LWMissionSet();
	private LWMissionSet mInitLWMissionSet = new LWMissionSet(); // 保全人工核保工作流起始节点

	/** 初审标志位* */
	private boolean FirstTrialFlaog = false;
	private String mReplyPENoticeFlag = "";
	/** 工作流任务节点备份表 */
	private LBMissionSet mLBMissionSet = new LBMissionSet();
	
	// 存放需要更新的结点
	private LWMissionSet mLWMissionSet_Update = new LWMissionSet();
	// 存放需要删除的结点
	private LWMissionSet mLWMissionSet_Delete = new LWMissionSet();
	// 存放需要备份的结点
	private LBMissionSet mLBMissionSet_Insert = new LBMissionSet();

	public BQInputHealthReportResultAfterEndService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		if (!prepareTransferData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}
	
	private boolean prepareTransferData() {
		//mTransferData.setNameAndValue("ReplyPENoticeFlag", mReplyPENoticeFlag);
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

//		// 添加相关工作流同步执行完毕的任务节点表数据
//		if (mLWMissionSet != null && mLWMissionSet.size() > 0) {
//			map.put(mLWMissionSet, "DELETE");
//		}
//
//		// 添加相关工作流同步执行完毕的任务节点备份表数据
//		if (mLBMissionSet != null && mLBMissionSet.size() > 0) {
//			map.put(mLBMissionSet, "INSERT");
//		}
//
//		// 添加保全工作流起始任务节点表数据
//		if (mInitLWMissionSchema != null) {
//			if (!FirstTrialFlaog) { // added by tuqiang for FirstTrial
//				map.put(mInitLWMissionSchema, "UPDATE");
//			}
//		}
//		
		
//		map.put(mInitLWMissionSet, "UPDATE");
		
//		map.put(this.mLWMissionSet_Update, "UPDATE");
//		map.put(this.mLWMissionSet_Delete, "DELETE");
//		map.put(this.mLBMissionSet_Insert, "INSERT");
		
		if (this.mLBMissionSet_Insert!=null && this.mLBMissionSet_Insert.size()>0)
			map.put(this.mLBMissionSet_Insert, "INSERT");
		if (this.mLWMissionSet_Delete!=null && this.mLWMissionSet_Delete.size()>0)
			map.put(this.mLWMissionSet_Delete, "DELETE");
		if (this.mLWMissionSet_Update!=null && this.mLWMissionSet_Update.size()>0)
			map.put(this.mLWMissionSet_Update, "UPDATE");
		
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 查询工作流当前任务轨迹表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setActivityID(mActivityID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "查询工作流轨迹表LWMission失败!") ;
			return false;
		}
		mLWMissionSchema = tLWMissionSet.get(1);
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PRnewUWTakeBackAutoHealthAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的子任务ID
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		mActivityID = cOperate;
		if (mActivityID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中ActivityID失败!") ;
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保工作流起始节点状态改变
//		if (prepareMission_new() == false) {
//			return false;
//		}

		return true;
	}

	private boolean prepareMission_new() {			
		
		// 删除本次回收节点
		String tStr = "Select * from LWMission where MissionID = '"
				+ "?mMissionID?"
				+ "' and SubMissionID = '"+ "?mSubMissionID?" +"'"
				+ " and ActivityID ='"+ "?ActivityID?" +"' "
				+ " and MissionProp14 = '"
				+ "?MissionProp14?"
				+ "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStr);
		sqlbv1.put("mMissionID",mMissionID);
		sqlbv1.put("mSubMissionID",mSubMissionID);
		sqlbv1.put("ActivityID",this.mActivityID);
		sqlbv1.put("MissionProp14",mLWMissionSchema.getMissionProp14());
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sqlbv1);
		if (tLWMissionSet == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "工作流起始任务节点查询出错!") ;
			return false;
		}
		if (tLWMissionSet.size() < 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "工作流起始任务节点LWMission查询出错!") ;
			return false;
		}
		for (int i = 1; i <= tLWMissionSet.size(); i++) {
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LBMissionSchema tLBMissionSchema = new LBMissionSchema();

			tLWMissionSchema = tLWMissionSet.get(i);
			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			mReflections.transFields(tLBMissionSchema, tLWMissionSchema);
			tLBMissionSchema.setSerialNo(tSerielNo);
			tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tLBMissionSchema.setLastOperator(mOperater);
			tLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
			mLWMissionSet_Delete.add(tLWMissionSchema);
			mLBMissionSet_Insert.add(tLBMissionSchema);
		}

		// 判段该体检通知书回复后,是否该承保申请已处于人工核保已回复状态.		
		tStr = "Select count(*)"
				+ " from LWMission"
				+ " where 1 = 1 and missionid= '"
				+ "?missionid?" + "' and  activityid in(select activityid from lwactivity where functionid like '100203%') " ;
		String tReSult = new String();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tStr);
		sqlbv2.put("missionid",this.mMissionID);
		ExeSQL tExeSQL = new ExeSQL();
		tReSult = tExeSQL.getOneValue(sqlbv2);
		if (tExeSQL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError.buildErr(this, "执行SQL语句：" + tStr + "失败!");
			return false;
		}
		if (tReSult == null || tReSult.equals("")) {
			return false;
		}
		int tCount = 0;
		tCount = Integer.parseInt(tReSult); // 已包括了本次节点及相关同步节点
		if (tCount == 1) 
		{
			// 如果本次发核保通知书只剩现在这个任务未完成，修改核保工作流节点为公共池已回复
			LWMissionDB tLWMissionDB1 = new LWMissionDB();
			String sqlstr="select *from lwmission where missionid='"+"?missionid?"+"' and activityid in(select activityid from lwactivity where functionid='10020004')";
			LWMissionSet tLWMissionSet1 = new LWMissionSet(); 
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sqlstr);
			sqlbv3.put("missionid",this.mMissionID);
			tLWMissionSet1 = tLWMissionDB.executeQuery(sqlbv3);
			
//			tLWMissionSet1 = tLWMissionDB1.query();
			if(tLWMissionSet1 == null || tLWMissionSet1.size()!=1)
			{
				CError.buildErr(this, "工作流信息查询失败!") ;
				return false;
			}
			
			LWMissionSchema tLWMissionSchema = tLWMissionSet1.get(1);
			Reflections mReflections = new Reflections();
			LBMissionSchema tLBMissionSchema = new LBMissionSchema();
			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			mReflections.transFields(tLBMissionSchema,
					tLWMissionSchema);
			tLBMissionSchema.setSerialNo(tSerielNo);
			tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tLBMissionSchema.setLastOperator(mOperater);
			tLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
			
			//tLWMissionSchema.setActivityStatus("3");
			tLWMissionSchema.setDefaultOperator(null);
			//tLWMissionSchema.setLastOperator(mOperater);
			if(tLWMissionSchema.getMissionProp19()!=null 
					&& !tLWMissionSchema.getMissionProp19().equals(""))
				tLWMissionSchema.setMissionProp18("4");//核保状态 --强制人工核保
			else	
				tLWMissionSchema.setMissionProp18("2");//核保状态 --核保已回复
			tLWMissionSchema.setMissionProp9(PubFun.getCurrentDate());//最后回复日期
			tLWMissionSchema.setMissionProp10(PubFun.getCurrentTime());
			tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
			tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
			
			mLWMissionSet_Update.add(tLWMissionSchema);
			mLBMissionSet_Insert.add(tLBMissionSchema);
		}		
		
		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
