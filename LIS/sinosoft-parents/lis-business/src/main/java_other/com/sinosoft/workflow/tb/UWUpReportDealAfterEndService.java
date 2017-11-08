package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

/**
 * <p>
 * Title: 工作流节点任务:新契约回收生调通知书
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
 */

public class UWUpReportDealAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(UWUpReportDealAfterEndService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	private LBMissionSchema mLBMissionSchema = new LBMissionSchema();

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
	private LWMissionSchema mInitLWMissionSchema = new LWMissionSchema(); // 新契约人工核保工作流起始节点

	/** 工作流任务节点备份表 */
	//add by lzf 2013-04-02
    private LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
    private LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();   
    //end 

	public UWUpReportDealAfterEndService() {
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

		// 添加新契约工作流起始任务节点表数据	
//		if (mInitLWMissionSchema != null) {
//			map.put(mInitLWMissionSchema, "UPDATE");
//		}
//		
//		if (mLBMissionSchema != null) {
//			map.put(mLBMissionSchema, "INSERT");
//		}

		// modify by lzf 2013-04-02
		if(tLCCUWMasterSchema != null){
			map.put(tLCCUWMasterSchema, "UPDATE");
		}
		if(tLCCUWSubSchema != null){
			map.put(tLCCUWSubSchema, "UPDATE");
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
		//modify by lzf 2013-04-03
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		LWMissionSet tLWMissionSet = new LWMissionSet();
//		tLWMissionDB.setMissionID(mMissionID);
//		tLWMissionDB.setActivityID("0000001140");
//		tLWMissionDB.setSubMissionID(mSubMissionID);
//		tLWMissionSet = tLWMissionDB.query();
//		if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
//			// @@错误处理
//			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
//			CError tError = new CError();
//			tError.moduleName = "PrintTakeBackRReportAfterEndService";
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
			tError.moduleName = "PrintTakeBackRReportAfterEndService";
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
			tError.moduleName = "PrintTakeBackRReportAfterEndService";
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
			tError.moduleName = "PrintTakeBackRReportAfterEndService";
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
			tError.moduleName = "PrintTakeBackRReportAfterEndService";
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
			tError.moduleName = "PrintTakeBackRReportAfterEndService";
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
			tError.moduleName = "PrintTakeBackRReportAfterEndService";
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
			tError.moduleName = "PrintTakeBackRReportAfterEndService";
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

		// 新契约核保工作流起始节点状态改变
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

		// 修改承保人工核保的起始节点状态为未回复
		/*
		 LWMissionDB tLWMissionDB = new LWMissionDB();
		 
		LWMissionSet tLWMissionSet = new LWMissionSet();
		String tStr = "Select * from LWMission where MissionID = '"
				+ mMissionID + "'" + "and ActivityID = '0000001110'";

		tLWMissionSet = tLWMissionDB.executeQuery(tStr);
		if (tLWMissionSet != null && tLWMissionSet.size() == 1) {
			mInitLWMissionSchema = tLWMissionSet.get(1);
			//备份工作流 ln 2009-2-18 add			
			Reflections mReflections = new Reflections();
			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			mReflections.transFields(mLBMissionSchema, mInitLWMissionSchema);
			mLBMissionSchema.setSerialNo(tSerielNo);
			mLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			mLBMissionSchema.setLastOperator(mOperater);
			mLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
			mLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
			
			mInitLWMissionSchema.setActivityStatus("5");
			mInitLWMissionSchema.setMissionProp18("3");
			mInitLWMissionSchema.setMissionProp8(PubFun.getCurrentDate());
			mInitLWMissionSchema.setMissionProp9(PubFun.getCurrentTime());
			// mInitLWMissionSchema.setDefaultOperator(mOperater);

		} else {
			tStr = "Select * from LWMission where MissionID = '" + mMissionID
					+ "'" + "and ActivityID = '0000001061'";
			tLWMissionSet = tLWMissionDB.executeQuery(tStr);
			if (tLWMissionSet != null && tLWMissionSet.size() == 1) {
				mInitLWMissionSchema = tLWMissionSet.get(1);
				mInitLWMissionSchema.setActivityStatus("3");
				// mInitLWMissionSchema.setDefaultOperator(mOperater);
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLWMissionSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWRReportAfterEndService";
				tError.functionName = "prepareMission";
				tError.errorMessage = "查询工作流承保人工核保的起始任务节点失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}*/
		//更新LCCUWMaster表的uwsate字段为3
		LCCUWMasterDB tLCCUWMasterDB=new LCCUWMasterDB();	
		
		tLCCUWMasterDB.setContNo(mContNo);
		if(!tLCCUWMasterDB.getInfo()){
			// @@错误处理
				CError.buildErr(this, "LCCUWMaster表取数失败!");
				return false;
		}		
		tLCCUWMasterSchema=tLCCUWMasterDB.getSchema();
		 tLCCUWMasterSchema.setUWState("3");
		 tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		 tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		 logger.debug("tLCCUWMasterSchema.getUWNo()==="+tLCCUWMasterSchema.getUWNo());
		 //更新LCCUWSub表的uwstate字段为3
		 LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		 tLCCUWSubDB.setContNo(mContNo);
		 tLCCUWSubDB.setUWNo(tLCCUWMasterSchema.getUWNo());
         if(!tLCCUWSubDB.getInfo()){
        	// @@错误处理
 			CError.buildErr(this, "LCCUWMaster表取数失败!");
 			return false;
		   }
         tLCCUWSubSchema= tLCCUWSubDB.getSchema();
		
		 tLCCUWSubSchema.setUWState("3");
		 tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		 tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		
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
