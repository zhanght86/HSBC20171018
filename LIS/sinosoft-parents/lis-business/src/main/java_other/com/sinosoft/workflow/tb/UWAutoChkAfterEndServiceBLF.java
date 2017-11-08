package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LWLockDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LWLockSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

/**
 * <p>
 * Title: 工作流节点任务:自动核保
 * </p>
 * <p>
 * Description: 自动核保工作流AfterEnd服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ln 2008-10-14
 * @version 1.0
 */

public class UWAutoChkAfterEndServiceBLF implements AfterEndService {
private static Logger logger = Logger.getLogger(UWAutoChkAfterEndServiceBLF.class);
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

	private LWMissionSet mLWMissionSet = new LWMissionSet();
	private Reflections mReflections = new Reflections();
	/** 工作流任务节点备份表 */
	private LBMissionSet mLBMissionSet = new LBMissionSet();
	private String updateSql1,updateSql2;
	private SQLwithBindVariables sqlbv01 = new SQLwithBindVariables();
	private SQLwithBindVariables sqlbv02 = new SQLwithBindVariables();
	public UWAutoChkAfterEndServiceBLF() {
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

		// 进行业务处理
		if (!dealData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

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

		// 获得业务体检通知数据

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
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
		String state = (String)mTransferData.getValueByName("Uw_State");
		String DefaultOperator = (String)mTransferData.getValueByName("DefaultOperator");
		// 修改所有因此投保单而核保等待的投保单核保状态改为3状态		
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		String tUniteNo = mContNo;
		
		//SYY BEGIN
		updateSql1 = "update lccuwmaster set uwstate = '"+"?uwstate?"+"' where contno = '"+"?contno?"+"'";
		sqlbv01.sql(updateSql1);
		sqlbv01.put("uwstate", state);
		sqlbv01.put("contno", mContNo);
		updateSql2 = "update LCCUWSub set uwstate = '"+"?uwstate?"+"' where contno = '"+"?contno?"+"'";
		sqlbv02.sql(updateSql1);
		sqlbv02.put("uwstate", state);
		sqlbv02.put("contno", mContNo);
		//SYY END
		
		/* 暂时不支持并单处理，待工作流升级完单独处理  2013-04-01
		tLWMissionDB = new LWMissionDB();
		tLWMissionSet = new LWMissionSet();
		String tStr = "Select * from LWMission where MissionProp21 is not null and MissionProp21 = '"
				+ tUniteNo + "'" + "and ActivityID = '0000001110'";
		logger.debug("tStr==" + tStr);
		tLWMissionSet = tLWMissionDB.executeQuery(tStr);
		if (tLWMissionSet != null && tLWMissionSet.size() >0) {	
			for(int i = 1 ;i<=tLWMissionSet.size();i++){				
				LWMissionSchema tLWMissionSchema = new LWMissionSchema();
				LBMissionSchema tLBMissionSchema = new LBMissionSchema();
				mReflections = new Reflections();
				tLWMissionSchema = tLWMissionSet.get(i);
				
				//备份
				 String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
				 mReflections.transFields(tLBMissionSchema,tLWMissionSchema);
				 tLBMissionSchema.setSerialNo(tSerielNo);
				 tLBMissionSchema.setLastOperator(mOperater);
				 tLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
				 tLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
				 mLBMissionSet.add(tLBMissionSchema) ;
				 //update
				 //tLWMissionSchema.setMissionProp18(state);//与回复后的投保单保持状态一致 2009-2-13 ln add
				 tLWMissionSchema.setMissionProp21("");
				 tLWMissionSchema.setMissionProp22("");
				 tLWMissionSchema.setMissionProp23("");
				 tLWMissionSchema.setDefaultOperator(DefaultOperator);
				 tLWMissionSchema.setMissionProp14("0000000000");
				 tLWMissionSchema.setLastOperator(mGlobalInput.Operator);
				 tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
				 tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());			
				 mLWMissionSet.add(tLWMissionSchema);
		   }
		}		
		*/
		return true;
	}

	/**
	 * 准备
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加相关工作流同步执行完毕的任务节点表数据
		if (mLWMissionSet != null && mLWMissionSet.size() > 0) {
			map.put(mLWMissionSet, "UPDATE");
		}
		
		map.put(sqlbv01, "UPDATE");
		map.put(sqlbv02, "UPDATE");
		
		// 添加相关工作流同步执行完毕的任务节点备份表数据
		if (mLBMissionSet != null && mLBMissionSet.size() > 0) {
			map.put(mLBMissionSet, "INSERT");
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
}
