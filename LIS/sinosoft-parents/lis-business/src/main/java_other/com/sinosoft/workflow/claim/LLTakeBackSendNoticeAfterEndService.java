package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LWLockSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

public class LLTakeBackSendNoticeAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(LLTakeBackSendNoticeAfterEndService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String Operater;
	private String mManageCom;
	private String mOperate;
	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mSubMissionID;
	private String mCodeType;//通知书类型
	private String mCertifyNo;//通知书号
	private Reflections mReflections = new Reflections();

	private LWMissionSchema mInitLWMissionSchema = new LWMissionSchema(); // 理赔二核节点
	private LWMissionSet mDelLWMissionSet = new LWMissionSet();
	// private LWMissionSet mLWMissionSet_Update = new LWMissionSet();
	private LWLockSchema mLWLockSchema = new LWLockSchema();
	/** 工作流任务节点备份表 */
	private LBMissionSet mLBMissionSet = new LBMissionSet();
	private LLClaimSchema mLLClaimSchema=null;

	public LLTakeBackSendNoticeAfterEndService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
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

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		if(this.mLLClaimSchema!=null){
			map.put(mLLClaimSchema, "UPDATE");
		}
//		// 添加相关工作流同步执行完毕的任务节点表数据
//		if (mDelLWMissionSet != null && mDelLWMissionSet.size() > 0) {
//			map.put(mDelLWMissionSet, "DELETE");
//		}
//
//		// 添加相关工作流同步执行完毕的任务节点备份表数据
//		if (mLBMissionSet != null && mLBMissionSet.size() > 0) {
//			map.put(mLBMissionSet, "INSERT");
//		}
//
//		// 添加保全工作流起始任务节点表数据
//		if (mInitLWMissionSchema != null) {
//			map.put(mInitLWMissionSchema, "UPDATE");
//		}
//		if (this.mLWLockSchema != null) {
//			map.put(mLWLockSchema, "DELETE");
//		}

		mResult.add(map);
		return true;
	}

	/** 工作流任务节点表 */
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 查询工作流当前任务轨迹表
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		LWMissionSet tLWMissionSet = new LWMissionSet();
//		tLWMissionDB.setMissionID(mMissionID);
//		tLWMissionDB.setActivityID(mOperate);
//		tLWMissionDB.setSubMissionID(mSubMissionID);
//		tLWMissionSet = tLWMissionDB.query();
//		if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
//			// @@错误处理
//			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
//			CError tError = new CError();
//			tError.moduleName = "PrintTakeBackSendNoticeAfterEndService";
//			tError.functionName = "checkData";
//			tError.errorMessage = "查询工作流轨迹表LWMission失败!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//		mLWMissionSchema = tLWMissionSet.get(1);
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
			tError.moduleName = "PrintTakeBackSendNoticeAfterEndService";
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
			tError.moduleName = "PrintTakeBackSendNoticeAfterEndService";
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
			tError.moduleName = "PrintTakeBackSendNoticeAfterEndService";
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
			tError.moduleName = "PrintTakeBackSendNoticeAfterEndService";
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
			tError.moduleName = "PrintTakeBackSendNoticeAfterEndService";
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
			tError.moduleName = "PrintTakeBackSendNoticeAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintTakeBackSendNoticeAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mCodeType=(String)mTransferData.getValueByName("CodeType");
		if(mCodeType==null){
			CError.buildErr(this, "前台传输业务数据中CodeType失败!") ;
			return false;
		}
		mCertifyNo=(String)mTransferData.getValueByName("CertifyNo");
		if(mCertifyNo==null){
			CError.buildErr(this, "前台传输业务数据中CertifyNo失败!") ;
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 承保核保工作流起始节点状态改变
//		if (prepareMission() == false) {
//			return false;
//		}
		if (prepareMission_new() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 准备打印信息表
	 * 
	 * @return
	 */
//	private boolean prepareMission() {
////		/*
////		 * 1.若有业务员通知书节点 2.是否有除己之外的核保通知书 3.时否有机构问题件为回复
////		 */
////		// 查询出同一体检通知书(原打印流水号相同者)待回收的任务节点,并回收
////		boolean flag = false;
////		String tStr = "Select * from LWMission where MissionID = '"
////				+ mMissionID + "'" + "and ActivityID = '0000001112'"
////				+ "and SubMissionID <> '"
////				+ mSubMissionID
////				+ "'"
////				+ "and MissionProp14 = '"
////				+ mLWMissionSchema.getMissionProp14()
////				+ "'"
////				+ " union "
////				+ "Select * from LWMission where MissionID = '"
////				+ mMissionID
////				+ "'"
////				+ " and ActivityID = '0000001115'"
////				+ " and MissionProp14 = '"
////				+ mLWMissionSchema.getMissionProp14()
////				+ "'"
////				+ " union "
////				+ "Select * from LWMission where MissionID = '"
////				+ mMissionID
////				+ "'"
////				+ " and ActivityID = '0000001107'"
////				+ " and MissionProp14 = '"
////				+ mLWMissionSchema.getMissionProp14()
////				+ "'"
////				// tongmeng 2007-11-22 modify
////				// 删除补打核保通知书(非打印类)数据
////				+ " union " + "Select * from LWMission where MissionID = '"
////				+ mMissionID + "'"
////				+ " and ActivityID = '0000001301'"
////				+ " and MissionProp14 = '"
////				+ mLWMissionSchema.getMissionProp14()
////				+ "'"
////				// 删除补打业务员通知书
////				+ " union " + "Select * from LWMission where MissionID = '"
////				+ mMissionID + "'" + " and ActivityID = '0000001018'"
////				+ " and MissionProp14 = '"
////				+ mLWMissionSchema.getMissionProp14() + "'";
////
////		LWMissionDB tLWMissionDB = new LWMissionDB();
////		mLWMissionSet = tLWMissionDB.executeQuery(tStr);
////		if (mLWMissionSet == null) {
////			// @@错误处理
////			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
////			CError tError = new CError();
////			tError.moduleName = "PrintTakeBackAutoHealthAfterEndService";
////			tError.functionName = "prepareMission";
////			tError.errorMessage = "承保工作流起始任务节点查询出错!";
////			this.mErrors.addOneError(tError);
////			return false;
////		}
////		if (mLWMissionSet.size() < 0) {
////			// @@错误处理
////			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
////			CError tError = new CError();
////			tError.moduleName = "PrintTakeBackAutoHealthAfterEndService";
////			tError.functionName = "prepareMission";
////			tError.errorMessage = "承保工作流起始任务节点LWMission查询出错!";
////			this.mErrors.addOneError(tError);
////			return false;
////		}
////
////		for (int i = 1; i <= mLWMissionSet.size(); i++) {
////			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
////			LBMissionSchema tLBMissionSchema = new LBMissionSchema();
////
////			tLWMissionSchema = mLWMissionSet.get(i);
////			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
////			mReflections.transFields(tLBMissionSchema, tLWMissionSchema);
////			tLBMissionSchema.setSerialNo(tSerielNo);
////			tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
////			tLBMissionSchema.setLastOperator(mOperater);
////			tLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
////			tLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
////			mLBMissionSet.add(tLBMissionSchema);
////		}
////
////		// 判段该核保通知书回收后,是否该承保申请已处于人工核保已回复状态.
////		// tongmeng 2007-11-22 modify
////		// 增加核保通知书(非打印类)补打结点
////		tStr = "Select count(*) from LWMission where MissionID = '"
////				+ mMissionID
////				+ "'"
////				+ " and ActivityID in ('0000001111','0000001112','0000001113','0000001106','0000001107','0000001108','0000001114','0000001115','0000001301','0000001116','0000001017','0000001018','0000001019','0000001020','0000001021','0000001002')";
////		String tReSult = new String();
////		ExeSQL tExeSQL = new ExeSQL();
////		tReSult = tExeSQL.getOneValue(tStr);
////		if (tExeSQL.mErrors.needDealError()) {
////			// @@错误处理
////			this.mErrors.copyAllErrors(tExeSQL.mErrors);
////			CError tError = new CError();
////			tError.moduleName = "PrintTakeBackAutoHealthAfterEndService";
////			tError.functionName = "prepareMission";
////			tError.errorMessage = "执行SQL语句：" + tStr + "失败!";
////			this.mErrors.addOneError(tError);
////			return false;
////		}
////		if (tReSult == null || tReSult.equals("")) {
////			return false;
////		}
////
////		int tCount = 0;
////		tCount = Integer.parseInt(tReSult); // 已包括了本次节点及相关同步节点
////		if (tCount > (mLWMissionSet.size() + 1)) { // 处于核保未回复状态,不用修改承保人工核保的起始节点状
////			mInitLWMissionSchema = null;
////		} else {
////			// 处于核保已回复状态,修改承保人工核保的起始节点状态为已回复
////			LWMissionSet tLWMissionSet = new LWMissionSet();
////			tStr = "Select * from LWMission where MissionID = '" + mMissionID
////					+ "'" + "and ActivityID = '0000001100'";
////
////			tLWMissionSet = tLWMissionDB.executeQuery(tStr);
////			if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
////				tStr = "Select * from LWMission where MissionID = '"
////						+ mMissionID + "'" + "and ActivityID = '0000000005'";
////				tLWMissionSet = tLWMissionDB.executeQuery(tStr);
////				if (tLWMissionSet != null && tLWMissionSet.size() == 1) {
////					mInitLWMissionSchema = tLWMissionSet.get(1);
////					mInitLWMissionSchema.setActivityStatus("2");
////					mInitLWMissionSchema.setDefaultOperator(mOperater);
////				}
////
////			}
////			if (tLWMissionSet != null && tLWMissionSet.size() == 1) {
////				mInitLWMissionSchema = tLWMissionSet.get(1);
////				mInitLWMissionSchema.setActivityStatus("3");
////			}
////
////		}
////		// tongmeng 2007-11-28 modify
////		// 如果只有加费的核保通知书的话,需要清除defalutoperator 字段.并且解锁
//////		String tAddFee_Only = this.mTransferData.getValueByName("AddFee_Only") == null ? ""
//////				: (String) this.mTransferData.getValueByName("AddFee_Only");
//////		if (tAddFee_Only.equals("")) {
//////			this.mErrors.copyAllErrors(tExeSQL.mErrors);
//////			CError tError = new CError();
//////			tError.moduleName = "PrintTakeBackAutoHealthAfterEndService";
//////			tError.functionName = "prepareMission";
//////			tError.errorMessage = "判断只有加费出错!";
//////			this.mErrors.addOneError(tError);
//////			return false;
//////		} else if (tAddFee_Only.equals("1")) {
//////			if (mInitLWMissionSchema == null) {
//////				LWMissionDB tempLWMissionDB = new LWMissionDB();
//////				tempLWMissionDB.setMissionID(this.mMissionID);
//////				tempLWMissionDB.setActivityID("0000001100");
//////				mInitLWMissionSchema = tempLWMissionDB.query().get(1);
//////			}
//////			mInitLWMissionSchema.setDefaultOperator(null);
//////			mInitLWMissionSchema.setMissionProp14(null);
//////			mInitLWMissionSchema.setMissionProp18("3");// 其他核保已回复
//////			LWLockDB tLWLockDB = new LWLockDB();
//////			tLWLockDB.setMissionID(this.mMissionID);
//////			tLWLockDB.setLockActivityID("0000001100");
//////			tLWLockDB.setSubMissionID(mInitLWMissionSchema.getSubMissionID());
//////			tLWLockDB.setLockType("1");
//////			tLWLockDB.getInfo();
//////			if (tLWLockDB != null) {
//////				this.mLWLockSchema.setSchema(tLWLockDB.getSchema());
//////			}
//////
//////		}
//
//		
//		return true;
//	}
	
	/**
	 * 准备工作流更新备份信息
	 * 
	 * @return
	 */
	private boolean prepareMission_new() {		
//		String tAddFee_Only = this.mTransferData.getValueByName("AddFee_Only") == null ? ""
//				: (String) this.mTransferData.getValueByName("AddFee_Only");
//		if (tAddFee_Only.equals("")) {
//			CError.buildErr(this, "判断只有加费、特约、承保计划变更出错!") ;
//			return false;
//		} 
//		
//		/*
//		 * 1.若有业务员通知书节点 2.是否有除己之外的核保通知书 3.时否有机构问题件为回复
//		 */
//		// 查询出同一体检通知书(原打印流水号相同者)待回收的任务节点,并回收
//		boolean flag = false;
//		String tStr = "Select * from LWMission where MissionID = '"
//				+ mMissionID + "'" + "and ActivityID = '0000001112'"
//				+ "and SubMissionID <> '"
//				+ mSubMissionID
//				+ "'"
//				+ "and MissionProp14 = '"
//				+ mLWMissionSchema.getMissionProp14()
//				+ "'"
//				+ " union "
//				+ "Select * from LWMission where MissionID = '"
//				+ mMissionID
//				+ "'"
//				+ " and ActivityID = '0000001115'"
//				+ " and MissionProp14 = '"
//				+ mLWMissionSchema.getMissionProp14()
//				+ "'"
//				+ " union "
//				+ "Select * from LWMission where MissionID = '"
//				+ mMissionID
//				+ "'"
//				+ " and ActivityID = '0000001107'"
//				+ " and MissionProp14 = '"
//				+ mLWMissionSchema.getMissionProp14()
//				+ "'"
//				// tongmeng 2007-11-22 modify
//				// 删除补打核保通知书(非打印类)数据
//				+ " union " + "Select * from LWMission where MissionID = '"
//				+ mMissionID + "'"
//				+ " and ActivityID = '0000001301'"
//				+ " and MissionProp14 = '"
//				+ mLWMissionSchema.getMissionProp14()
//				+ "'"
//				// 删除补打业务员通知书
//				+ " union " + "Select * from LWMission where MissionID = '"
//				+ mMissionID + "'" + " and ActivityID = '0000001018'"
//				+ " and MissionProp14 = '"
//				+ mLWMissionSchema.getMissionProp14() + "'";
//
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		mLWMissionSet = tLWMissionDB.executeQuery(tStr);
//		if (mLWMissionSet == null) {
//			// @@错误处理
//			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
//			CError.buildErr(this, "承保工作流起始任务节点查询出错!") ;
//			return false;
//		}
//		if (mLWMissionSet.size() < 0) {
//			// @@错误处理
//			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
//			CError.buildErr(this, "承保工作流起始任务节点LWMission查询出错!") ;
//			return false;
//		}
//
//		for (int i = 1; i <= mLWMissionSet.size(); i++) {
//			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
//			LBMissionSchema tLBMissionSchema = new LBMissionSchema();
//
//			tLWMissionSchema = mLWMissionSet.get(i);
//			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
//			mReflections.transFields(tLBMissionSchema, tLWMissionSchema);
//			tLBMissionSchema.setSerialNo(tSerielNo);
//			tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
//			tLBMissionSchema.setLastOperator(mOperater);
//			tLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
//			tLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
//			mLBMissionSet.add(tLBMissionSchema);
//		}
//
//		String tAutoUWFlag = "0";//是否扭转到自核标志
//
//		// 判段该体检通知书回复后,是否该承保申请已处于人工核保已回复状态.		
//		tStr = "Select count(*)"
//				+ " from LWMission"
//				+ " where 1 = 1 and missionid= '"
//				+ this.mMissionID + "' and (activityid in "
//				//考虑处于发送核保通知书到自核之间的工作流节点
//				+ "(0000001108, 0000001120, 0000001116, 0000001108, "
//                + " 0000001302, 0000001112, 0000001301, "
//                + " 0000001107, 0000001112, 0000001115, "
//                + " 0000001020, "
//                + " 0000001017, 0000001018, 0000001019, "
//                + " 0000001106, 0000001111, 0000001114) "
//                //有直接到自核的工作流节点要判断节点状态不为4
//				+ " or (activityid in ('0000001002', '0000001113', '0000001404', '0000001121') and activitystatus<>'4')) "
//				;
//		String tReSult = new String();
//		ExeSQL tExeSQL = new ExeSQL();
//		tReSult = tExeSQL.getOneValue(tStr);
//		if (tExeSQL.mErrors.needDealError()) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tExeSQL.mErrors);
//			CError.buildErr(this, "执行SQL语句：" + tStr + "失败!");
//			return false;
//		}
//		if (tReSult == null || tReSult.equals("")) {
//			return false;
//		}
//		int tCount = 0;
//		tCount = Integer.parseInt(tReSult); // 已包括了本次节点及相关同步节点
//		if (tCount == 2 && tAddFee_Only.equals("1")) 
//		{
//			// 如果本次回收仅有加费等 ,且这次是回收的最后一个通知书（包括补打节点）
//			// 扭转工作流到自核,删除其他操作数据
//			tAutoUWFlag = "1";
//			String tSQL_temp = "select * from lwmission where missionid='"
//					+ this.mMissionID + "' " + " and activityid<>'0000001112' ";
//			LWMissionSet tLWMissionSet = new LWMissionSet();
//			tLWMissionDB = new LWMissionDB();
//			tLWMissionSet = tLWMissionDB.executeQuery(tSQL_temp);
//			Reflections mReflections = new Reflections();
//			for (int i = 1; i <= tLWMissionSet.size(); i++) {
//				LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
//				LBMissionSchema tempLBMissionSchema = new LBMissionSchema();
//
//				tempLWMissionSchema = tLWMissionSet.get(i);
//				String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
//				mReflections.transFields(tempLBMissionSchema,
//						tempLWMissionSchema);
//				tempLBMissionSchema.setSerialNo(tSerielNo);
//				tempLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
//				tempLBMissionSchema.setLastOperator(mOperater);
//				tempLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
//				tempLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
//				this.mLBMissionSet.add(tempLBMissionSchema);
//				this.mLWMissionSet.add(tempLWMissionSchema);
//			}
//		}
//		
//		mTransferData.setNameAndValue("AutoUWFlag", tAutoUWFlag);
		
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		tLWMissionDB.setMissionID(mMissionID);
//		tLWMissionDB.setActivityID("0000000321");
//		mDelLWMissionSet=tLWMissionDB.query();
//		if(mDelLWMissionSet.size()>1){
//			for (int i = 1; i <= mDelLWMissionSet.size(); i++) {
//				LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
//				LBMissionSchema tempLBMissionSchema = new LBMissionSchema();
//
//				tempLWMissionSchema = mDelLWMissionSet.get(i);
//				String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
//				mReflections.transFields(tempLBMissionSchema,
//						tempLWMissionSchema);
//				tempLBMissionSchema.setSerialNo(tSerielNo);
//				tempLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
//				tempLBMissionSchema.setLastOperator(mOperater);
//				tempLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
//				tempLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
//				this.mLBMissionSet.add(tempLBMissionSchema);
//			}
//		}
		//查询是否是最后回销的通知书，现在需要回销的通知书只有两种：核保通知书、体检通知书
		ExeSQL tExeSql = new ExeSQL();
		String UWCount;
		String PECount;
		String tSql="";
		LOPRTManagerDB tLOPrtManagerDB=new LOPRTManagerDB();
		tLOPrtManagerDB.setPrtSeq(this.mCertifyNo);
		if(!tLOPrtManagerDB.getInfo()){
			CError.buildErr(this, "查询打印管理表失败!");
			return false;
		}
		if("LP90".equals(this.mCodeType)){//如果是核保通知书，检查本次理赔、批次中是否还有没回收的其他核保、体检
			tSql="select count(*) from llissuepol where clmno='"+"?clmno?"+"' and batno='"+"?batno?"+"' and prtseq<>'"+"?prtseq?"+"' and State<>'2'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("clmno", tLOPrtManagerDB.getStandbyFlag4());
			sqlbv1.put("batno", tLOPrtManagerDB.getStandbyFlag5());
			sqlbv1.put("prtseq", tLOPrtManagerDB.getOldPrtSeq());
			UWCount=tExeSql.getOneValue(sqlbv1);
			tSql="select count(*) from  LLUWPENotice where clmno='"+"?clmno?"+"' and batno='"+"?batno?"+"' and PEResult is null ";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("clmno", tLOPrtManagerDB.getStandbyFlag4());
			sqlbv2.put("batno", tLOPrtManagerDB.getStandbyFlag5());
			PECount=tExeSql.getOneValue(sqlbv2);
		}else{//体检通知书
			tSql="select count(*) from llissuepol where clmno='"+"?clmno?"+"' and batno='"+"?batno?"+"' and State<>'2'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSql);
			sqlbv3.put("clmno", tLOPrtManagerDB.getStandbyFlag4());
			sqlbv3.put("batno", tLOPrtManagerDB.getStandbyFlag5());
			UWCount=tExeSql.getOneValue(sqlbv3);
			tSql="select count(*) from  LLUWPENotice where clmno='"+"?clmno?"+"' and batno='"+"?batno?"+"' and PEResult is null  and prtseq<>'"+"?mCertifyNo?"+"'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSql);
			sqlbv4.put("clmno", tLOPrtManagerDB.getStandbyFlag4());
			sqlbv4.put("batno", tLOPrtManagerDB.getStandbyFlag5());
			sqlbv4.put("mCertifyNo", this.mCertifyNo);
			PECount=tExeSql.getOneValue(sqlbv4);
		}
		if("0".equals(PECount) && "0".equals(UWCount)){
			//更新核保状态为2，已回收
			LLClaimDB tLLClaimDB=new LLClaimDB();
			tLLClaimDB.setClmNo(tLOPrtManagerDB.getStandbyFlag4());
			if(!tLLClaimDB.getInfo()){
				CError.buildErr(this, "查询理赔信息表失败!");
				return false;
			}
			tLLClaimDB.setUWState("2");
			mLLClaimSchema=tLLClaimDB.getSchema();
		}
		//查询是否所有的通知书都已经打印并且回收完毕
		
//		if(mOperate.equals("0000005553")){
//			 tSql="  select * from lwmission where missionid='"+mMissionID+"' and activityid in "
//						+" ('0000005531','0000005533','0000005534','0000005551','0000005561','0000005563')"
//						+" union "
//						+" select * from lwmission where missionid='"+mMissionID+"' and activityid='0000005553'"
//						+" and submissionid<>'"+mSubMissionID+"'";
//		}
//		if(mOperate.equals("0000005563")){
//			tSql="  select * from lwmission where missionid='"+mMissionID+"' and activityid in "
//						+" ('0000005531','0000005553','0000005533','0000005551','0000005561','0000005534')"
//						+" union "
//						+" select * from lwmission where missionid='"+mMissionID+"' and activityid='0000005563'"
//						+" and submissionid<>'"+mSubMissionID+"'";
//		}
//		if((!mOperate.equals("0000005563"))&&(!mOperate.equals("0000005553"))){
//			tSql="  select * from lwmission where missionid='"+mMissionID+"' and activityid in "
//						+" ('0000005531','0000005553','0000005533','0000005551','0000005561','0000005563')"
//						+" union "
//						+" select * from lwmission where missionid='"+mMissionID+"' and activityid='0000005534'"
//						+" and submissionid<>'"+mSubMissionID+"'";
//		}
//		
//		SSRS tSSRS = new SSRS();
//		tSSRS=tExeSql.execSQL(tSql);
//		//如果所有的通知书都已经打印并且回收完毕，删除多余的节点并将二核状态更改为2-通知书已回复
//		//步骤1-删除多余的节点
//		if(tSSRS.MaxRow==0){
//			LWMissionDB tLWMissionDB = new LWMissionDB();
//			String tDelMisSql="select * from lwmission where missionid='"+mMissionID+"' "
//				+"and activityid in ('0000005532','0000005552','0000005562')";
//			mDelLWMissionSet=tLWMissionDB.executeQuery(tDelMisSql);
//			if(mDelLWMissionSet.size()>=1){
//				for (int i = 1; i <= mDelLWMissionSet.size(); i++) {
//					LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
//					LBMissionSchema tempLBMissionSchema = new LBMissionSchema();
//	
//					tempLWMissionSchema = mDelLWMissionSet.get(i);
//					String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
//					mReflections.transFields(tempLBMissionSchema,
//							tempLWMissionSchema);
//					tempLBMissionSchema.setSerialNo(tSerielNo);
//					tempLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
//					tempLBMissionSchema.setLastOperator(mOperater);
//					tempLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
//					tempLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
//					this.mLBMissionSet.add(tempLBMissionSchema);
//				}
//			}
//			//步骤2-将二核状态更改为2-通知书已回复
//			LWMissionDB ttLWMissionDB = new LWMissionDB();
//			LWMissionSet tLWMissionSet = new LWMissionSet();
//			ttLWMissionDB.setMissionID(mMissionID);
//			ttLWMissionDB.setActivityID("0000005505");
//			tLWMissionSet=ttLWMissionDB.query();
//			if(tLWMissionSet.size()!=1){
//				CError.buildErr(this, "查询理赔二核工作流节点失败！");
//				return false;
//			}
//			mInitLWMissionSchema = tLWMissionSet.get(1);
//			mInitLWMissionSchema.setActivityStatus("2");
//		}
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
