package com.sinosoft.workflow.xb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
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

/**
 * <p>
 * Title: 工作流节点任务:新契约人工核保回收核保通知书
 * </p>
 * <p>
 * Description:人工核保核保通知书回收AfterEnd服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class RnewTakeBackSendNoticeAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(RnewTakeBackSendNoticeAfterEndService.class);
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
	private String mOperate;
	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mSubMissionID;
	private Reflections mReflections = new Reflections();
	// 存放需要更新的结点
	private LWMissionSet mLWMissionSet_Update = new LWMissionSet();
	// 存放需要删除的结点
	private LWMissionSet mLWMissionSet_Delete = new LWMissionSet();
	// 存放需要备份的结点
	private LBMissionSet mLBMissionSet_Insert = new LBMissionSet();

	private LWMissionSchema mInitLWMissionSchema = new LWMissionSchema(); // 保全人工核保工作流起始节点
	private LWMissionSet mLWMissionSet = new LWMissionSet();
	// private LWMissionSet mLWMissionSet_Update = new LWMissionSet();
	private LWLockSchema mLWLockSchema = new LWLockSchema();
	/** 工作流任务节点备份表 */
	private LBMissionSet mLBMissionSet = new LBMissionSet();

	public RnewTakeBackSendNoticeAfterEndService() {
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

		if (this.mLBMissionSet_Insert!=null && this.mLBMissionSet_Insert.size()>0)
			map.put(this.mLBMissionSet_Insert, "INSERT");
		if (this.mLWMissionSet_Delete!=null && this.mLWMissionSet_Delete.size()>0)
			map.put(this.mLWMissionSet_Delete, "DELETE");
		if (this.mLWMissionSet_Update!=null && this.mLWMissionSet_Update.size()>0)
			map.put(this.mLWMissionSet_Update, "UPDATE");

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
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setActivityID(mOperate);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintTakeBackSendNoticeAfterEndService";
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
//		if (prepareMission_new() == false) {
//			return false;
//		}

		return true;
	}

	/**
	 * 准备打印信息表
	 * 
	 * @return
	 */
	/**
	 * 准备工作流更新备份信息
	 * 
	 * @return
	 */
	private boolean prepareMission_new() 
	{		
		String tAddFee_Only = this.mTransferData.getValueByName("AddFee_Only") == null ? ""
				: (String) this.mTransferData.getValueByName("AddFee_Only");
		if (tAddFee_Only.equals("")) {
			CError.buildErr(this, "判断只有加费、特约、承保计划变更出错!") ;
			return false;
		} 
		
		/*
		 * 1.若有业务员通知书节点 2.是否有除己之外的核保通知书 3.时否有机构问题件为回复
		 */
		// 查询出同一体检通知书(原打印流水号相同者)待回收的任务节点,并回收
		boolean flag = false;
		String tStr = "Select * from LWMission where MissionID = '"
				+ "?mMissionID?" + "'" + "and ActivityID = '0000007006'"
				+ "and SubMissionID <> '"
				+ "?mSubMissionID?"
				+ "'"
				+ "and MissionProp14 = '"
				+ "?MissionProp14?"
				+ "'"
				+ " union "
				+ "Select * from LWMission where MissionID = '"
				+ "?mMissionID?"
				+ "'"
				+ " and ActivityID = '0000007005'"
				+ " and MissionProp14 = '"
				+ "?MissionProp14?"
				+ "'"
				;
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStr);
		sqlbv1.put("mMissionID", mMissionID);
		sqlbv1.put("mSubMissionID", mSubMissionID);
		sqlbv1.put("MissionProp14", mLWMissionSchema.getMissionProp14());
		LWMissionDB tLWMissionDB = new LWMissionDB();
		mLWMissionSet = tLWMissionDB.executeQuery(sqlbv1);
		if (mLWMissionSet == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "承保工作流起始任务节点查询出错!") ;
			return false;
		}
		if (mLWMissionSet.size() < 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "承保工作流起始任务节点LWMission查询出错!") ;
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

		String tAutoUWFlag = "0";//是否扭转到自核标志

		 // 判段该生调通知书回收后,是否该承保申请已处于人工核保已回复状态.		
		String tStr2 = "Select count(*)"
				+ " from LWMission"
				+ " where 1 = 1 and missionid= '"
				+ "?missionid?" + "' and (activityid in "
				//考虑处于发送核保通知书到自核之间的工作流节点
				+ "(0000007004, 0000007005, 0000007006, 0000007007, "
                + " 0000007008, 0000007009, 0000007010, "
                + " 0000007011, 0000007014) "
                //有直接到自核的工作流节点要判断节点状态不为4
				+ " or (activityid in ( '0000007012',  '0000007013','0000007015') and activitystatus<>'4')) "
				;
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tStr2);
		sqlbv2.put("missionid", this.mMissionID);
		String tReSult = new String();
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
        //回收核保通知书时和其他最后一个节点稍微有点不同，此时判断是否只有两个，即回收和补打核保通知书
		if (tCount == 2) 
		{ // 如果本次发核保通知书只剩现在这个任务未完成，修改核保工作流节点为公共池已回复
			LWMissionDB tLWMissionDB1 = new LWMissionDB();
			tLWMissionDB1.setMissionID(this.mMissionID);
			tLWMissionDB1.setActivityID("0000007001");
			LWMissionSet tLWMissionSet1 = new LWMissionSet(); 
			tLWMissionSet1 = tLWMissionDB1.query();
			if(tLWMissionSet1 == null || tLWMissionSet1.size()!=1)
			{
				CError.buildErr(this, "工作流信息查询失败!") ;
				return false;
			}
			
			LWMissionSchema tLWMissionSchema = tLWMissionSet1.get(1);
			mReflections = new Reflections();
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
			tLWMissionSchema.setLastOperator(mOperater);
			tLWMissionSchema.setMissionProp12("2");//核保状态
			tLWMissionSchema.setMissionProp14(PubFun.getCurrentDate());//最后回复日期
			tLWMissionSchema.setMissionProp15(PubFun.getCurrentTime());
			tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
			tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
			
			mLWMissionSet_Update.add(tLWMissionSchema);
			mLBMissionSet_Insert.add(tLBMissionSchema);
			
		}
		mTransferData.setNameAndValue("AutoUWFlag", tAutoUWFlag);
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
