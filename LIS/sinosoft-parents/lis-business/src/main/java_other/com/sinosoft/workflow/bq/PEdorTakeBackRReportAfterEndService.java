package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPUWMasterMainSchema;
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
 * Title:
 * </p>
 * <p>
 * Description:工作流节点任务:保全人工核保体检通知书回收服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class PEdorTakeBackRReportAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(PEdorTakeBackRReportAfterEndService.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mString;
	/** 业务数据操作字符串 */
	private String mEdorNo;
	private String mPolNo;
	private String mMissionID;
	private String mSubMissionID;
	private Reflections mReflections = new Reflections();

	/** 执行保全工作流特约活动表任务0000000011 */
	/** 保单表 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	/** 保全核保主表 */
	private LPUWMasterMainSchema mLPUWMasterMainSchema = new LPUWMasterMainSchema();
	/** 工作流任务节点表 */
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();
	private LWMissionSchema mInitLWMissionSchema = new LWMissionSchema();// 保全人工核保工作流起始节点
	private LWMissionSet mLWMissionSet = new LWMissionSet();
	/** 工作流任务节点备份表 */
	private LBMissionSet mLBMissionSet = new LBMissionSet();

	public PEdorTakeBackRReportAfterEndService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加相关工作流同步执行完毕的任务节点表数据
		if (mLWMissionSet != null && mLWMissionSet.size() > 0) {
			map.put(mLWMissionSet, "DELETE");
		}

		// 添加相关工作流同步执行完毕的任务节点备份表数据
		if (mLBMissionSet != null && mLBMissionSet.size() > 0) {
			map.put(mLBMissionSet, "INSERT");
		}

		// 添加保全工作流起始任务节点表数据
		if (mInitLWMissionSchema != null) {
			map.put(mInitLWMissionSchema, "UPDATE");
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
		tLWMissionDB.setActivityID("0000000013");
		tLWMissionDB.setSubMissionID(mSubMissionID);
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "checkData";
			tError.errorMessage = "查询工作流轨迹表LWMission失败!";
			this.mErrors.addOneError(tError);
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
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		if (mEdorNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中EdorNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mPolNo = (String) mTransferData.getValueByName("PolNo");
		if (mPolNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中PolNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的子任务ID
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 保全核保工作流起始节点状态改变
		if (prepareMission() == false)
			return false;

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
				+ "?mMissionID?" + "'" + " and ActivityID = '0000000013'"
				+ " and SubMissionID <> '" + "?mSubMissionID?" + "'"
				+ " and MissionProp14 = '"
				+ "?MissionProp14?" + "'" + " union "
				+ "Select * from LWMission where MissionID = '" + "?mMissionID?"
				+ "'" + " and ActivityID = '0000000016'"
				+ " and MissionProp3 = '" +"?MissionProp14?"
				+ "'" + " union "
				+ "Select * from LWMission where MissionID = '" + "?mMissionID?"
				+ "'" + " and ActivityID = '0000000008'"
				+ " and MissionProp8 = '" + "?MissionProp14?"
				+ "'";

		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStr);
		sqlbv1.put("mMissionID",mMissionID);
		sqlbv1.put("mSubMissionID",mSubMissionID);
		sqlbv1.put("MissionProp14",mLWMissionSchema.getMissionProp14());
		LWMissionDB tLWMissionDB = new LWMissionDB();
		mLWMissionSet = tLWMissionDB.executeQuery(sqlbv1);
		if (mLWMissionSet == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "保全工作流起始任务节点查询出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLWMissionSet.size() < 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "保全工作流起始任务节点LWMission查询出错!";
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
			tLBMissionSchema.setActivityStatus("3");// 节点任务执行完毕
			tLBMissionSchema.setLastOperator(mOperater);
			tLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
			mLBMissionSet.add(tLBMissionSchema);
		}

		// 判段该体检通知书回收后,是否该保全申请已处于人工核保已回复状态.
		tStr = "Select count(*) from LWMission where MissionID = '"
				+ "?mMissionID?"
				+ "'"
				+ "and ActivityID in ('0000000011','0000000012','0000000013','0000000006','0000000007','0000000008','0000000014','0000000015','0000000016')";
		String tReSult = new String();
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tStr);
		sqlbv2.put("mMissionID",mMissionID);
		tReSult = tExeSQL.getOneValue(sqlbv2);
		if (tExeSQL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "执行SQL语句：" + tStr + "失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tReSult == null || tReSult.equals("")) {
			return false;
		}

		// 将待打印或待补打的工作流节点删除
		tStr = "Select * from LWMission where MissionID = '" + "?mMissionID?" + "'"
				+ " and ActivityID = '0000000016'" + " and MissionProp3 = '"
				+ "?MissionProp3?" + "'";
		tLWMissionDB = new LWMissionDB();
		LWMissionSet tempLWMissionSet = new LWMissionSet();
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tStr);
		sqlbv3.put("mMissionID",mMissionID);
		sqlbv3.put("MissionProp3",mLWMissionSchema.getMissionProp14());
		tempLWMissionSet = tLWMissionDB.executeQuery(sqlbv3);
		if (mLWMissionSet == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "保全工作流起始任务节点查询出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLWMissionSet.size() < 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "保全工作流起始任务节点LWMission查询出错!";
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
			tLBMissionSchema.setActivityStatus("3");// 节点任务执行完毕
			tLBMissionSchema.setLastOperator(mOperater);
			tLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
			mLBMissionSet.add(tLBMissionSchema);
		}

		int tCount = 0;
		tCount = Integer.parseInt(tReSult);// 已包括了本次节点及相关同步节点
		if (tCount > (mLWMissionSet.size() + 1)) {// 处于核保未回复状态,不用修改保全人工核保的起始节点状
			mInitLWMissionSchema = null;
		} else {
			// 处于核保已回复状态,修改保全人工核保的起始节点状态为已回复
			LWMissionSet tLWMissionSet = new LWMissionSet();
			tStr = "Select * from LWMission where MissionID = '" + "?mMissionID?"
					+ "'" + "and ActivityID = '0000000000'";

			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tStr);
			sqlbv4.put("mMissionID",mMissionID);
			tLWMissionSet = tLWMissionDB.executeQuery(sqlbv4);
			if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "PEdorPrintTakeBackRReportAfterEndService";
				tError.functionName = "prepareMission";
				tError.errorMessage = "查询工作流保全人工核保的起始任务节点失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mInitLWMissionSchema = tLWMissionSet.get(1);
			mInitLWMissionSchema.setActivityStatus("3");

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
