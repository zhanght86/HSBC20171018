/*
 * 创建日期 2006-2-20
 */
package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class NBWMissionReassignBL {
private static Logger logger = Logger.getLogger(NBWMissionReassignBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	public VData mResult;

	/** 数据操作字符串 */
	private String mOperate;
	private MMap map = new MMap();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();

	public NBWMissionReassignBL() {

	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LWMissionReassignBL Begin----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
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

		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LWMissionReassignBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLWMissionSchema = (LWMissionSchema) mInputData.getObjectByObjectName(
				"LWMissionSchema", 0);
		return checkData();
	}

	/**
	 * 检查数据的合理性
	 */
	private boolean checkData() {
		if (mInputData == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NBWMissionReassignBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NBWMissionReassignBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NBWMissionReassignBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输操作符失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLWMissionSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NBWMissionReassignBL";
			tError.functionName = "checkData";
			tError.errorMessage = "接受的Schema信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (mOperate.equals("User||UPDATE")) {
			if (!MissionUpdate()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 进行任务修改
	 * 
	 * @return
	 */
	private boolean MissionUpdate() {

		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		LWMissionDB tLWMissionDB = new LWMissionDB();

		tLWMissionDB.setMissionID(mLWMissionSchema.getMissionID());
		tLWMissionDB.setSubMissionID(mLWMissionSchema.getSubMissionID());
		tLWMissionDB.setActivityID(mLWMissionSchema.getActivityID());

		if (!tLWMissionDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "NBWMissionAssignBL";
			tError.functionName = "UserUpdate";
			tError.errorMessage = "工作流任务查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			tLWMissionSchema.setSchema(tLWMissionDB.getSchema());
			tLWMissionSchema.setInDate(mCurrentDate);
			tLWMissionSchema.setInTime(mCurrentTime);
			tLWMissionSchema.setDefaultOperator(mLWMissionSchema
					.getDefaultOperator());
			map.put(tLWMissionSchema, "UPDATE");
			return true;
		}
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			// mResult.add(mLLAccidentSchema);//供前台界面使用
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NBWMissionReassignBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 提供返回前台界面的数据
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
	}
}
