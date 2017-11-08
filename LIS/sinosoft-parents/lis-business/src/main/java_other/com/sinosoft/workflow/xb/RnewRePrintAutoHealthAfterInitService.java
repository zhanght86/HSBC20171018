package com.sinosoft.workflow.xb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:工作流服务类:新契约人工核保补打体检通知书
 * </p>
 * <p>
 * Description: 补打体检通知书工作流AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class RnewRePrintAutoHealthAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(RnewRePrintAutoHealthAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;

	/** 工作流引擎 */
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
	
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	private LWMissionSet mLWMissionDeleteSet = new LWMissionSet();
	private LBMissionSet mLBMissionInsertSet = new LBMissionSet();
	
	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public RnewRePrintAutoHealthAfterInitService() {
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

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start SysUWNoticeBL Submit...");

		// mResult.clear();
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		// 校验是否有要补打的体检通知书
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq());
		tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			CError tError = new CError();
			tError.moduleName = "RePrintAutoHealthAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "体检通知书" + mLOPRTManagerSchema.getPrtSeq()
					+ "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 1) {
			CError tError = new CError();
			tError.moduleName = "RePrintAutoHealthAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "体检通知书" + mLOPRTManagerSchema.getPrtSeq()
					+ "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLOPRTManagerSchema = tLOPRTManagerSet.get(1);
		if (!mLOPRTManagerSchema.getCode().equals("43")) {
			CError tError = new CError();
			tError.moduleName = "RePrintAutoHealthAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "体检通知书" + mLOPRTManagerSchema.getPrtSeq()
					+ "体检标识信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLOPRTManagerSchema.getStateFlag() == null
				|| !mLOPRTManagerSchema.getStateFlag().equals("1")) {
			CError tError = new CError();
			tError.moduleName = "RePrintAutoHealthAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "体检通知书" + mLOPRTManagerSchema.getPrtSeq()
					+ "标识处于未打印或已回收状态，不允许进行补打!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 打印队列
		if (preparePrint() == false)
			return false;
		// 准备需要删除的数据
		//已经修改为永假判断，所以不需要再手动进行操作cc
//		if (!this.dealLwmission()) {
//			return false;
//		}

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
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据失败!") ;
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据Operate失败!") ;
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!") ;
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据失败!") ;
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中ContNo失败!") ;
			return false;
		}		
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}
		
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!");
			return false;
		}
		
		mActivityID = cOperate;
		if (mActivityID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中Activityid失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mLOPRTManagerSchema = (LOPRTManagerSchema) mTransferData
				.getValueByName("LOPRTManagerSchema");
		if (mLOPRTManagerSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中LOPRTManagerSchema失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLOPRTManagerSchema.getPrtSeq() == null
				|| mLOPRTManagerSchema.getPrtSeq().trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中LOPRTManagerSchema中的打印流水号失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean preparePrint() {
		if (mLOPRTManagerSchema.getPatchFlag() != null
				&& mLOPRTManagerSchema.getPatchFlag().equals("1")) {
			mLOPRTManagerSchema
					.setOldPrtSeq(mLOPRTManagerSchema.getOldPrtSeq());
		} else {
			mLOPRTManagerSchema.setOldPrtSeq(mLOPRTManagerSchema.getPrtSeq());
		} // 记录最初的打印流水号

		mLOPRTManagerSchema.setPatchFlag("1");
		mLOPRTManagerSchema.setStateFlag("0");

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setPrtSeq(PubFun1.CreateMaxNo("PRTSEQNO",
				strNoLimit));

		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		return true;
	}
	/**
	 * 准备需要删除的工作流数据
	 * 
	 * @return
	 */
	private boolean dealLwmission() {
		Reflections mReflections = new Reflections();
		String tOperate = "";//与补打节点并行的工作流节点，如回收通知书节点
		String tSQL = "select * from lwmission where missionid='"
				+ "?missionid?" + "' " 
				+ " and activityid=(select  ActivityID from Lwactivity a where Functionid = '0000007009') ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("missionid", this.mMissionID);
		LWMissionSet tempLWMissionSet = new LWMissionSet();
		LWMissionDB tempLWMissionDB = new LWMissionDB();
		tempLWMissionSet = tempLWMissionDB.executeQuery(sqlbv);
		for (int i = 1; i <= tempLWMissionSet.size(); i++) {
			LWMissionSchema tempLWMissionSchema = new LWMissionSchema();
			LBMissionSchema tempLBMissionSchema = new LBMissionSchema();
			tempLWMissionSchema.setSchema(tempLWMissionSet.get(i));

			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			mReflections.transFields(tempLBMissionSchema, tempLWMissionSchema);
			tempLBMissionSchema.setSerialNo(tSerielNo);
			//tempLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tempLBMissionSchema.setLastOperator(mOperater);
			tempLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tempLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
			this.mLBMissionInsertSet.add(tempLBMissionSchema);
			this.mLWMissionDeleteSet.add(tempLWMissionSchema);
		}
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSet tLAAgentSet = new LAAgentSet();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		tLAAgentSet = tLAAgentDB.query();
		if (tLAAgentSet == null || tLAAgentSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLAAgentSet.get(1).getAgentGroup() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent中的代理机构数据丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
		tLABranchGroupDB.setAgentGroup(tLAAgentSet.get(1).getAgentGroup());
		tLABranchGroupSet = tLABranchGroupDB.query();
		if (tLABranchGroupSet == null || tLABranchGroupSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLABranchGroupSet.get(1).getBranchAttr() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup中展业机构信息丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mTransferData
		         .setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
        mTransferData
		         .setNameAndValue("PrtSeqPE", mLOPRTManagerSchema.getPrtSeq());
        mTransferData.setNameAndValue("OldPrtSeqPE", mLOPRTManagerSchema
		         .getOldPrtSeq());
		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", tLAAgentSet.get(1)
				.getAgentGroup());
		mTransferData.setNameAndValue("BranchAttr", tLABranchGroupSet.get(1)
				.getBranchAttr());
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("SaleChnl", mLCContSchema.getSaleChnl());
		return true;
	}

	/**
	 * 向工作流引擎提交数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加体检通知书打印管理表数据
		map.put(mLOPRTManagerSchema, "INSERT");

        //添加相关工作流同步执行完毕的任务节点表数据
		if (mLWMissionDeleteSet != null && mLWMissionDeleteSet.size() > 0) {
			map.put(mLWMissionDeleteSet, "DELETE");
		}

		// 添加相关工作流同步执行完毕的任务节点备份表数据
		if (mLBMissionInsertSet != null && mLBMissionInsertSet.size() > 0) {
			map.put(mLBMissionInsertSet, "INSERT");
		}
		
		mResult.add(map);
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

	public static void main(String[] args) {
		// SysUWNoticeBL sysUWNoticeBL1 = new SysUWNoticeBL();
	}
}
