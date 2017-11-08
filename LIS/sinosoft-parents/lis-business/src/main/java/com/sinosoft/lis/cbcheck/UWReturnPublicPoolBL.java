package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWLockDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWLockSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 人工核保返回公共池
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 1.0
 */

public class UWReturnPublicPoolBL {
private static Logger logger = Logger.getLogger(UWReturnPublicPoolBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;

	public UWReturnPublicPoolBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(mInputData, mOperate)) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "ICaseCureBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * dealData 处理数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		// 清空工作池
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setActivityID(mActivityID);
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		if (!tLWMissionDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流任务查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLWMissionSchema.setSchema(tLWMissionDB.getSchema());

		tLWMissionSchema.setLastOperator(tLWMissionSchema.getDefaultOperator());

		tLWMissionSchema.setDefaultOperator(null);
		tLWMissionSchema.setMissionProp14(null);

		// 删除工作流锁表
		LWLockSchema tLWLockSchema = new LWLockSchema();
		LWLockDB tLWLockDB = new LWLockDB();
		tLWLockDB.setLockActivityID(mActivityID);
		tLWLockDB.setMissionID(mMissionID);
		tLWLockDB.setSubMissionID(mSubMissionID);
		tLWLockDB.setLockType("1");
		tLWLockDB.getInfo();
		if (tLWLockDB != null) {
			tLWLockSchema = tLWLockDB.getSchema();
		}
		map.put(tLWLockSchema, "DELETE");
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select * from lwmission where activityid='" + "?activityid?"
				+ "' and missionid='" + "?missionid?" + "' and submissionid='"
				+ "?submissionid?" + "' and DefaultOperator is null");
		sqlbv.put("activityid", mActivityID);
		sqlbv.put("missionid", mMissionID);
		sqlbv.put("submissionid", mSubMissionID);
		map.put(sqlbv, "SELECT");
		map.put(tLWMissionSchema, "UPDATE");

		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * getInputData 得到前台传输的数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
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
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mManageCom = mGlobalInput.Operator;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null || mMissionID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null || mSubMissionID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mActivityID = (String) mTransferData.getValueByName("ActivityID");
		if (mActivityID == null || mActivityID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWApplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据ActivityID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

}
