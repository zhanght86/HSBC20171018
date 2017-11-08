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

public class BQTakeBackAutoHealthAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(BQTakeBackAutoHealthAfterEndService.class);
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
	private String mCertifyCode;//单证类型2000是保全体检通知书   2010是保全生调通知书
	private Reflections mReflections = new Reflections();

	/** 执行保全工作流特约活动表任务0000001011 */
	/** 工作流任务节点表 */
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();
	private LWMissionSchema mInitLWMissionSchema = new LWMissionSchema(); // 保全人工核保工作流起始节点
	private LWMissionSet mLWMissionSet = new LWMissionSet();
	private LWMissionSet mInitLWMissionSet = new LWMissionSet();
	private LWMissionSet ttInitLWMissionSet = new LWMissionSet();

	/** 初审标志位* */
	private boolean FirstTrialFlaog = false;
	/** 工作流任务节点备份表 */
	private LBMissionSet mLBMissionSet = new LBMissionSet();

	// 存放需要更新的结点
	private LWMissionSet mLWMissionSet_Update = new LWMissionSet();
	// 存放需要删除的结点
	private LWMissionSet mLWMissionSet_Delete = new LWMissionSet();
	// 存放需要备份的结点
	private LBMissionSet mLBMissionSet_Insert = new LBMissionSet();

	public BQTakeBackAutoHealthAfterEndService() {
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
		//查询是否所有通知书都已经回复 如果已经全部回复，删除工作流补打、回收的节点，将二核状态改为2——核保已回复状态
		
			// 进行业务处理
			if (!dealData()) {
				return false;
			}
	
			// 准备往后台的数据
			if (!prepareOutputData()) {
				return false;
			}
		//}
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
		//		if (mInitLWMissionSet != null) {
		//			if (!FirstTrialFlaog) { // added by tuqiang for FirstTrial
		//				map.put(mInitLWMissionSet, "UPDATE");
		//			}
		//		}

		//		map.put(ttInitLWMissionSet, "UPDATE");

		if (mLWMissionSet_Update != null && mLWMissionSet_Update.size() > 0) {
			map.put(this.mLWMissionSet_Update, "UPDATE");
		}

		if (mLWMissionSet_Delete != null && mLWMissionSet_Delete.size() > 0) {
			map.put(this.mLWMissionSet_Delete, "DELETE");
		}

		if (mLBMissionSet_Insert != null && mLBMissionSet_Insert.size() > 0) {
			map.put(this.mLBMissionSet_Insert, "INSERT");
		}		

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
			CError.buildErr(this, "查询工作流轨迹表LWMission失败!");
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
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理			
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据失败!");
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中ContNo失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}

		// 获得当前工作任务的子任务ID
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!");
			return false;
		}
		
		mCertifyCode = (String) mTransferData.getValueByName("CertifyCode");
		if (mCertifyCode == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中CertifyCode失败!");
			return false;
		}
		
		mActivityID = cOperate;
		if (mActivityID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中Activityid失败!");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 处理体检通知书
		if(mActivityID.equals("0000000303")){
			if (prepareHealthMission_new() == false) {
				return false;
			}
		} else if(mActivityID.equals("0000000314")){//生调通知书
//			if (prepareRReportMission_new() == false) {
//				return false;
//			}
		}
		
//		if(!dealMissionState()){
//			return false;
//		}
		return true;
	}

	/**
	 * 删除保全工作流补打体检通知书任务节点
	 * 
	 * @return
	 */
	private boolean prepareHealthMission_new() {
		//针对可以同时发多个体检通知书
		// 查询是否有本次发出的体检通知书补打工作流
		String tStr = "Select * from LWMission where MissionID = '"
				+ "?mMissionID?" + "' and ActivityID = '"+"?ActivityID?"+"'"
				+ " and MissionProp14 = '"
				+ "?MissionProp14?" + "'";
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStr);
		sqlbv1.put("mMissionID",mMissionID);
		sqlbv1.put("ActivityID",this.mActivityID);
		sqlbv1.put("MissionProp14",mLWMissionSchema.getMissionProp14());
		tLWMissionSet = tLWMissionDB.executeQuery(sqlbv1);
		if (tLWMissionSet == null) {
			// @@错误处理
			CError.buildErr(this, "保全工作流补打体检任务节点查询出错!");
			return false;
		}
		if (tLWMissionSet.size() < 0) {
			// @@错误处理
			CError.buildErr(this, "保全工作流补打体检任务节点LWMission查询出错!");
			return false;
		}

		Reflections mReflections = new Reflections();
		for (int i = 1; i <= tLWMissionSet.size(); i++) {
			LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
			LBMissionSchema tempLBMissionSchema = new LBMissionSchema();

			tempLWMissionSchema = tLWMissionSet.get(i);
			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			mReflections.transFields(tempLBMissionSchema, tempLWMissionSchema);
			tempLBMissionSchema.setSerialNo(tSerielNo);
			//tempLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tempLBMissionSchema.setLastOperator(mOperater);
			tempLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tempLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
			mLBMissionSet_Insert.add(tempLBMissionSchema);
			mLWMissionSet_Delete.add(tempLWMissionSchema);
		}
		return true;
	}
	/**
	 * 删除保全工作流补打生调通知书任务节点
	 * 
	 * */
	private boolean prepareRReportMission_new() {
		// 删除本次回收节点
		String tStr = "Select * from LWMission where MissionID = '"
				+ "?mMissionID?"
				+ "' and SubMissionID = '"+ "?mSubMissionID?" +"'"
				+ " and ActivityID ='"+ "?ActivityID?" +"' "
				+ " and MissionProp14 = '"
				+ "?MissionProp14?"
				+ "'";

		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tStr);
		sqlbv2.put("mMissionID",mMissionID);
		sqlbv2.put("mSubMissionID",mSubMissionID);
		sqlbv2.put("ActivityID",this.mActivityID);
		sqlbv2.put("MissionProp14",mLWMissionSchema.getMissionProp14());
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sqlbv2);
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
			//tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
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
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tStr);
		sqlbv3.put("missionid",this.mMissionID);
		tReSult = tExeSQL.getOneValue(sqlbv3);
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
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(sqlstr);
			sqlbv4.put("missionid",this.mMissionID);
			tLWMissionSet1 =tLWMissionDB1.executeQuery(sqlbv4) ;
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
			//tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tLBMissionSchema.setLastOperator(mOperater);
			tLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
			
			//tLWMissionSchema.setActivityStatus("3");
			if(tLWMissionSchema.getMissionProp19()!=null 
					&& !tLWMissionSchema.getMissionProp19().equals(""))
				tLWMissionSchema.setMissionProp18("4");//核保状态 --强制人工核保
			else	
				tLWMissionSchema.setMissionProp18("2");//核保状态 --核保已回复
			tLWMissionSchema.setMissionProp9(PubFun.getCurrentDate());//最后回复日期
			tLWMissionSchema.setMissionProp10(PubFun.getCurrentTime());
			tLWMissionSchema.setDefaultOperator(null);
			//tLWMissionSchema.setLastOperator(mOperater);
			tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
			tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
			
			mLWMissionSet_Update.add(tLWMissionSchema);
			mLBMissionSet_Insert.add(tLBMissionSchema);
		}	
		
		return true;
	}

	/**
	 * 将保全二核状态置为3——核保已回复状态
	 * 
	 * */
	private boolean dealMissionState(){
		String tSql="select * from LOPRTManager where PrtSeq in" 
			+" (select missionprop14 from lwmission where missionid='"+"?mMissionID?"
			+"' and  activityid in ('0000000302','0000000312','0000000314','0000000303')) and StateFlag<>'2'";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("mMissionID",mMissionID);
		tSSRS = tExeSQL.execSQL(sqlbv5);
		if(tSSRS.getMaxRow()==0){
			// 扭转工作流,删除其他操作数据
			//this.mReplyPENoticeFlag = "0";
			String tSQL_temp = "select * from lwmission where missionid='"
					+"?missionid?" + "' " + " and (activityid<>'0000000305' and activityid<>'0000000005' and activityid<>'0000000006' and activityid<>'0000000008')";
			
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSQL_temp);
			sqlbv6.put("missionid", this.mMissionID);
			LWMissionSet tLWMissionSet = new LWMissionSet();
			LWMissionDB tLWMissionDB = new LWMissionDB();
			tLWMissionSet = tLWMissionDB.executeQuery(sqlbv6);
			Reflections mReflections = new Reflections();
			for (int i = 1; i <= tLWMissionSet.size(); i++) {
				LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
				LBMissionSchema tempLBMissionSchema = new LBMissionSchema();

				tempLWMissionSchema = tLWMissionSet.get(i);
				String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
				mReflections.transFields(tempLBMissionSchema,
						tempLWMissionSchema);
				tempLBMissionSchema.setSerialNo(tSerielNo);
				tempLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
				tempLBMissionSchema.setLastOperator(mOperater);
				tempLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
				tempLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
				this.mLBMissionSet_Insert.add(tempLBMissionSchema);
				this.mLWMissionSet_Delete.add(tempLWMissionSchema);
			}
//		}
			LWMissionDB tempLWMissionDB = new LWMissionDB();
			LWMissionSet tempLWMissionSet = new LWMissionSet();
			LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
			tempLWMissionDB.setActivityID("0000000005");
			tempLWMissionDB.setMissionID(mMissionID);
			tempLWMissionSet = tempLWMissionDB.query();
			if(tempLWMissionSet.size()<1){
				CError.buildErr(this, "查询工作流信息失败！");
				return false;
			}
			tempLWMissionSchema = tempLWMissionSet.get(1);			
			tempLWMissionSchema.setActivityStatus("2");//已回复
			tempLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
			tempLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
			tempLWMissionSchema.setLastOperator(mOperater);
			tempLWMissionSchema.setDefaultOperator("");
			mLWMissionSet_Update.add(tempLWMissionSchema);
		}
		return true;
	}
	/**
	 * 准备打印信息表
	 * 
	 * @return
	 */
	private boolean prepareMission() {
		// 查询出同一体检通知书(原打印流水号相同者)待回收的任务节点,并回收

		String tStr = "Select * from LWMission where MissionID = '"
				+ "?mMissionID?" + "'" + "and ActivityID = '0000001111'"
				+ "and SubMissionID <> '" + "?mSubMissionID?" + "'"
				+ "and MissionProp14 = '" + "?MissionProp14?"
				+ "'" + " union "
				+ "Select * from LWMission where MissionID = '" + "?mMissionID?"
				+ "'" + " and ActivityID = '0000001114'"
				+ " and MissionProp14 = '"
				+ "?MissionProp14?" + "'" + " union "
				+ "Select * from LWMission where MissionID = '" + "?mMissionID?"
				+ "'" + " and ActivityID = '0000001106'"
				+ " and MissionProp14 = '"
				+ "?MissionProp14?" + "'";
		logger.debug("tStr=" + tStr);
		LWMissionDB tLWMissionDB = new LWMissionDB();

		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tStr);
		sqlbv7.put("mMissionID", mMissionID);
		sqlbv7.put("mSubMissionID", mSubMissionID);
		sqlbv7.put("MissionProp14", mLWMissionSchema.getMissionProp14());
		
		mLWMissionSet = tLWMissionDB.executeQuery(sqlbv7);

		if (mLWMissionSet == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "承保工作流起始任务节点查询出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLWMissionSet.size() < 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "承保工作流起始任务节点LWMission查询出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		for (int i = 1; i <= mLWMissionSet.size(); i++) {
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LBMissionSchema tLBMissionSchema = new LBMissionSchema();

			tLWMissionSchema = mLWMissionSet.get(i);
			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			mReflections.transFields(tLBMissionSchema, tLWMissionSchema);
			tLBMissionSchema.setSerialNo(tSerielNo);
			tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tLBMissionSchema.setLastOperator(mOperater);
			tLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
			mLBMissionSet.add(tLBMissionSchema);
		}

		// 判段该体检通知书回收后,是否该承保申请已处于人工核保已回复状态.
		// tStr = "Select count(*) from LWMission where MissionID = '" +
		// mMissionID + "'"
		// + "and ActivityID in
		// ('0000001111','0000001112','0000001113','0000001106','0000001107','0000001108','0000001114','0000001115','0000001116','0000001017','0000001018','0000001019','0000001020','0000001021','0000001002')";
		tStr = "Select count(*)"
				+ " from LWMission"
				+ " where 1 = 1"
				+ " and MissionID = '"
				+ "?mMissionID?"
				+ "'"
				+ " and ActivityID in"
				+ " ('0000001111', '0000001112', '0000001113', '0000001106', '0000001107',"
				+ " '0000001108', '0000001114', '0000001115', '0000001116', '0000001017',"
				+ " '0000001018', '0000001019', '0000001020', '0000001021', '0000001002',"
				+ " '0000001290', '0000001280', '0000001300')";

		String tReSult = new String();
		ExeSQL tExeSQL = new ExeSQL(); 
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tStr);
		sqlbv8.put("mMissionID", mMissionID);
		tReSult = tExeSQL.getOneValue(sqlbv8);
		if (tExeSQL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "执行SQL语句：" + tStr + "失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tReSult == null || tReSult.equals("")) {
			return false;
		}
		int tCount = 0;
		tCount = Integer.parseInt(tReSult); // 已包括了本次节点及相关同步节点
		if (tCount > (mLWMissionSet.size() + 1)) { // 处于核保未回复状态,不用修改承保人工核保的起始节点状
			mInitLWMissionSchema = null;
		} else {
			// 处于核保已回复状态,修改续保人工核保的起始节点状态为已回复
			LWMissionSet tLWMissionSet = new LWMissionSet();
			// tStr = "Select * from LWMission where MissionID = '" + mMissionID
			// +
			// "'" + "and ActivityID in ('0000001100', '0000000005')"
			// + " union select * from lwmission where missionprop1 in "
			// + " (select trim(contno) from lcpenotice where prtseq in"
			// + " (select prtseq from lcpenotice where contno = '" + mContNo +
			// "') and contno <> '" + mContNo + "')"
			// + " and activityid = '0000001100'"
			// ;
			tStr = " Select *"
					+ " from LWMission"
					+ " where MissionID = '"
					+ "?mMissionID?"
					+ "'"
					+ " and ActivityID in ('0000001100', '0000000005','0000005505')"
					+ " and not exists (select 'x'" + " from lwmission"
					+ " where missionid = '" + "?mMissionID?" + "'"
					+ " and ActivityID in ('0000001121'))";

			logger.debug("tStr:" + tStr);
			
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tStr);
			sqlbv9.put("mMissionID", mMissionID);
			tLWMissionSet = tLWMissionDB.executeQuery(sqlbv9);
			if (tLWMissionSet.size() == 0) {
				FirstTrialFlaog = true;
				logger.debug("不再进行更新！！！！！！！！！！！！！！！！");
				// // @@错误处理
				// this.mErrors.copyAllErrors(tExeSQL.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
				// tError.functionName = "prepareMission";
				// tError.errorMessage = "查询工作流续保人工核保的起始任务节点失败!";
				// this.mErrors.addOneError(tError);
				// return false;
			} else {
				logger.debug("需要进行更新！！！！！！！！！！！！！！！！");
				tStr = " Select *"
						+ " from LWMission"
						+ " where MissionID = '"
						+ "?mMissionID?"
						+ "'"
						+ " and ActivityID in ('0000001100', '0000000005','0000005505')"
						+ " and not exists (select 'x'"
						+ " from lwmission"
						+ " where missionid = '"
						+ "?mMissionID?"
						+ "'"
						+ " and ActivityID in ('0000001121'))"

						+ " union select * from lwmission where activityid = '0000001100' and missionid != '"
						+ "?mMissionID?"
						+ "'"
						+ " and missionprop2 in (select trim(contno) from lcpenotice where trim(prtseq) in "
						+ " (select MissionProp3 from lwmission where activityid = '0000001111' and missionid = '"
						+ "?mMissionID?" + "'))";
				
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql(tStr);
				sqlbv10.put("mMissionID", mMissionID);
				tLWMissionSet = tLWMissionDB.executeQuery(sqlbv10);

				for (int j = 1; j <= tLWMissionSet.size(); j++) {
					mInitLWMissionSchema = tLWMissionSet.get(j);
					mInitLWMissionSchema.setActivityStatus("3");
					mInitLWMissionSet.add(mInitLWMissionSchema);
				}

				for (int j = 1; j <= tLWMissionSet.size(); j++) {
					LWMissionSchema ttLWMissionSchema = new LWMissionSchema();
					ttLWMissionSchema = tLWMissionSet.get(j);
					ttLWMissionSchema.setActivityStatus("3");
					ttInitLWMissionSet.add(ttLWMissionSchema);
				}

			}
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
