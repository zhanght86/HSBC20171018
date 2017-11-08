package com.sinosoft.workflow.xb;
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
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

/**
 * <p>
 * Title: 工作流节点任务:新契约回收生调通知书 改为生调回复
 * </p>
 * <p>
 * Description: 新契约人工核保生调通知书回收AfterEnd服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 *   更新人：ln    更新日期：2008-10-31   更新原因/内容：根据新核保要求进行修改
 */

public class RnewInputRReportResultAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(RnewInputRReportResultAfterEndService.class);
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
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mSubMissionID;
	private Reflections mReflections = new Reflections();

	/** 工作流任务节点表 */
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();
	private LWMissionSchema mInitLWMissionSchema = new LWMissionSchema();

	private LWMissionSet mLWMissionSet = new LWMissionSet();
	private LWMissionSet mInitLWMissionSet = new LWMissionSet();

	/** 工作流任务节点备份表 */
	private LBMissionSet mLBMissionSet = new LBMissionSet();

	// tongmeng 2007-12-20 add
	// 增加生调处理之后的结点
	// 存放需要更新的结点
	private LWMissionSet mLWMissionSet_Update = new LWMissionSet();
	// 存放需要删除的结点
	private LWMissionSet mLWMissionSet_Delete = new LWMissionSet();
	// 存放需要备份的结点
	private LBMissionSet mLBMissionSet_Insert = new LBMissionSet();
	private String mOtherNoticeFlag = "";

	public RnewInputRReportResultAfterEndService() {
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

		// 校验是否可以查询到工作流轨迹表
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
		mTransferData.setNameAndValue("OtherNoticeFlag", mOtherNoticeFlag);
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		/*
		 * //添加相关工作流同步执行完毕的任务节点表数据 if (mLWMissionSet != null &&
		 * mLWMissionSet.size() > 0) { map.put(mLWMissionSet, "DELETE"); }
		 * 
		 * //添加相关工作流同步执行完毕的任务节点备份表数据 if (mLBMissionSet != null &&
		 * mLBMissionSet.size() > 0) { map.put(mLBMissionSet, "INSERT"); }
		 * 
		 * //添加新契约工作流起始任务节点表数据 if (mInitLWMissionSet != null) {
		 * map.put(mInitLWMissionSet, "UPDATE"); }
		 */

		map.put(this.mLWMissionSet_Update, "UPDATE");
		map.put(this.mLWMissionSet_Delete, "DELETE");
		map.put(this.mLBMissionSet_Insert, "INSERT");
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
		tLWMissionDB.setActivityID("0000007014");
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
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据失败!");
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中ContNo失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}

		// 获得当前工作任务的子任务ID
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 新契约核保工作流起始节点状态改变
		/*
		 * if (prepareMission() == false) { return false; }
		 */
		// tongmeng 2007-12-20 修改为新的逻辑
//		if (prepareMission_new() == false) {
//			return false;
//		}

		return true;

	}

	/**
	 * 对后续工作流结点的处理
	 * 
	 * @return
	 */
	private boolean prepareMission_new() {
		//一次只发一个生调通知数
		// 查询生调通知书补打工作流
		String tStr = "Select * from LWMission where MissionID = '"
				+ "?mMissionID?" + "' and ActivityID = '0000007011'"
				+ " and MissionProp14 = '"
				+ "?MissionProp14?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStr);
		sqlbv1.put("mMissionID", mMissionID);
		sqlbv1.put("MissionProp14", mLWMissionSchema.getMissionProp14());
		LWMissionDB tLWMissionDB = new LWMissionDB();
		mLWMissionSet = tLWMissionDB.executeQuery(sqlbv1);
		if (mLWMissionSet == null) {
			// @@错误处理
			CError.buildErr(this, "承保工作流补打生调任务节点查询出错!");
			return false;
		}
		if (mLWMissionSet.size() < 0) {
			// @@错误处理
			CError.buildErr(this, "承保工作流补打生调任务节点LWMission查询出错!");
			return false;
		}

		Reflections mReflections = new Reflections();
		for (int i = 1; i <= mLWMissionSet.size(); i++) {
			LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
			LBMissionSchema tempLBMissionSchema = new LBMissionSchema();

			tempLWMissionSchema = mLWMissionSet.get(i);
			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			mReflections.transFields(tempLBMissionSchema, tempLWMissionSchema);
			tempLBMissionSchema.setSerialNo(tSerielNo);
			tempLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tempLBMissionSchema.setLastOperator(mOperater);
			tempLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tempLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
			mLBMissionSet_Insert.add(tempLBMissionSchema);
			mLWMissionSet_Delete.add(tempLWMissionSchema);
		}
		return true;
	}

	/**
	 * 准备打印信息表
	 * 
	 * @return
	 */
	private boolean prepareMission() {

		// 查询出同一体检通知书(原打印流水号相同者)待回收的任务节点(以及同一通知书相关的补打,打印工作节点),并回收
		boolean flag = false;
		String tStr = "Select * from LWMission where MissionID = '"
				+ "?mMissionID?" + "'" + " and ActivityID = '0000001113'"
				+ " and MissionProp14 = '"
				+ "?MissionProp14?" + "'"

				+ " union " + "Select * from LWMission where MissionID = '"
				+ "?mMissionID?" + "'" + " and ActivityID = '0000001116'"
				+ " and MissionProp14 = '"
				+ "?MissionProp14?" + "'" + " union "
				+ "Select * from LWMission where MissionID = '" +"?mMissionID?"
				+ "'" + " and ActivityID = '0000001108'"
				+ " and MissionProp14 = '"
				+ "?MissionProp14?" + "'";
		logger.debug(tStr);
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tStr);
		sqlbv2.put("mMissionID", mMissionID);
		sqlbv2.put("MissionProp14", mLWMissionSchema.getMissionProp14());
		LWMissionDB tLWMissionDB = new LWMissionDB();
		mLWMissionSet = tLWMissionDB.executeQuery(sqlbv2);
		if (mLWMissionSet == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintTakeBackRReportAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "新契约工作流起始任务节点查询出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLWMissionSet.size() < 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintTakeBackRReportAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "新契约工作流起始任务节点LWMission查询出错!";
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

		// tStr = "Select count(*) from LWMission where MissionID = '" +
		// mMissionID + "'"
		// + "and ActivityID in
		// ('0000001111','0000001112','0000001113','0000001106','0000001107','0000001108','0000001114','0000001115','0000001116','0000001017','0000001018','0000001019','0000001020','0000001021','0000001002','0000001120','0000001130')";
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
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tStr);
		sqlbv3.put("mMissionID", mMissionID);
		String tReSult = new String();
		ExeSQL tExeSQL = new ExeSQL();
		tReSult = tExeSQL.getOneValue(sqlbv3);
		if (tExeSQL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PrintTakeBackRReportAfterEndService";
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
		if (tCount > (mLWMissionSet.size() + 1)) { // 处于核保未回复状态,不用修改新契约人工核保的起始节点状
			mInitLWMissionSchema = null;
		} else {
			// 处于核保已回复状态,修改新契约人工核保的起始节点状态为已回复
			// 在回收和回复后将核保节点置为已回复
			LWMissionSet tLWMissionSet = new LWMissionSet();
			// tStr = "Select * from LWMission where MissionID = '" + mMissionID
			// +
			// "'"
			// + "and ActivityID in ('0000001100', '0000000005') and ActivityID
			// not in ('0000001120')";
			tStr = " Select *"
					+ " from LWMission"
					+ " where MissionID = '"
					+ "?mMissionID?"
					+ "'"
					+ " and ActivityID in ('0000001100', '0000000005','0000005505')"
					+ " and not exists (select 'x'" + " from lwmission"
					+ " where missionid = '" + "?mMissionID?" + "'"
					+ " and ActivityID in ('0000001120'))";

			logger.debug("tStr=" + tStr);
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tStr);
			sqlbv4.put("mMissionID", mMissionID);
			tLWMissionSet = tLWMissionDB.executeQuery(sqlbv4);
			if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
				// @@错误处理
				// this.mErrors.copyAllErrors(tExeSQL.mErrors);
				// CError tError = new CError();
				// tError.moduleName =
				// "PRnewPrintTakeBackRReportAfterEndService";
				// tError.functionName = "prepareMission";
				// tError.errorMessage = "查询工作流新契约人工核保的起始任务节点失败!";
				// this.mErrors.addOneError(tError);
				// return false;
			}
			for (int i = 0; i < tLWMissionSet.size(); i++) {
				mInitLWMissionSchema = tLWMissionSet.get(i + 1);
				mInitLWMissionSchema.setActivityStatus("3");
				mInitLWMissionSet.add(mInitLWMissionSchema);
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
