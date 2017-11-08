package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LWLockDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LWLockSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 核保任务调配
 * </p>
 * 
 * <p>
 * Description: 将以被核保师申请的任务重新分配给其他核保师或退回任务池
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HWM
 * @version 1.0
 */
public class UWMissionAssignBL {
private static Logger logger = Logger.getLogger(UWMissionAssignBL.class);
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
	private String mApplyType = "";
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
	private String mDefaultOperator;
	private String mUWPopedom = "";

	public UWMissionAssignBL() {
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
			tError.moduleName = "UWMissionAssignBL";
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
		String tManageCom = ""; // 保单所属机构
		String tUWGrade = ""; // 保单核保级别
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setActivityID(mActivityID);
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		if (!tLWMissionDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWMissionAssignBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流任务查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
//		if (mActivityID != null && mActivityID.equals("0000001100")) {
//			tManageCom = tLWMissionDB.getMissionProp10();
//			tUWGrade = tLWMissionDB.getMissionProp12().trim();
//			mApplyType = "xqy"; // 新契约
//		}
		if (mActivityID != null) {
			String sql ="select functionid from lwactivity where activityid ='"+"?activityid?"+"'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("activityid", mActivityID);
			String tFunctionID = new ExeSQL().getOneValue(sqlbv);
			if(tFunctionID.equals("10010028")){
			tManageCom = tLWMissionDB.getMissionProp10();
			tUWGrade = tLWMissionDB.getMissionProp12().trim();
			mApplyType = "xqy"; // 新契约
			}
		}
		else if (mActivityID != null && mActivityID.equals("0000002004")) {
			tManageCom = tLWMissionDB.getMissionProp4();
			tUWGrade = tLWMissionDB.getMissionProp12().trim();
			mApplyType = "xqy";
		} else if (mActivityID != null
				&& (mActivityID.equals("0000000005") || mActivityID
						.equals("0000008005"))) {
			tManageCom = tLWMissionDB.getMissionProp7();
			mApplyType = "bq";
		} else if (mActivityID != null && mActivityID.equals("0000005505")) {
			tManageCom = tLWMissionDB.getMissionProp20();
			mApplyType = "lp";
		}
		if (mDefaultOperator != null && !mDefaultOperator.equals("")) { // 调配到核保师校验
			if (!checkUWUser(tManageCom)) { // 校验核保师核保类别、所属机构
				return false;
			}
			if ((tUWGrade != null) && (!tUWGrade.equals(""))) { // 校验核保师核保权限
				logger.debug("核保权限校验");
				if (mUWPopedom.compareTo(tUWGrade) < 0) {
					CError tError = new CError();
					tError.moduleName = "UWMissionAssignBL";
					tError.functionName = "dealData";
					tError.errorMessage = "核保师核保权限小于保单核保权限！";
					mErrors.addOneError(tError);
					return false;
				}
			}
		}
		tLWMissionSchema.setSchema(tLWMissionDB.getSchema());
		{

			tLWMissionSchema.setLastOperator(mOperator);
		}
		tLWMissionSchema.setDefaultOperator(mDefaultOperator);
		tLWMissionSchema.setInDate(PubFun.getCurrentDate());
		tLWMissionSchema.setInTime(PubFun.getCurrentTime());
		tLWMissionSchema.setModifyDate(tLWMissionSchema.getInDate());
		tLWMissionSchema.setModifyTime(tLWMissionSchema.getInTime());
//		if (mActivityID != null && mActivityID.equals("0000001100")) {
//			tLWMissionSchema.setMissionProp14(mDefaultOperator);
//		}
		if (mActivityID != null) {
			String sql ="select functionid from lwactivity where activityid ='"+"?activityid?"+"'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("activityid", mActivityID);
			String mActivityID = new ExeSQL().getOneValue(sqlbv1);

			if(mActivityID.equals("10010028")){
			tLWMissionSchema.setMissionProp14(mDefaultOperator);
			}
		}

		map.put(tLWMissionSchema, "UPDATE");

		if (!deleteExistsDate()) {
			return false;
		}
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
			tError.moduleName = "UWMissionAssignBL";
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
			tError.moduleName = "UWMissionAssignBL";
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
			tError.moduleName = "UWMissionAssignBL";
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
			tError.moduleName = "UWMissionAssignBL";
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
			tError.moduleName = "UWMissionAssignBL";
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
			tError.moduleName = "UWMissionAssignBL";
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
			tError.moduleName = "UWMissionAssignBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据ActivityID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mDefaultOperator = (String) mTransferData
				.getValueByName("DefaultOperator");

		logger.debug("== UWMissionAssignBL == mMissionID ===="
				+ mActivityID);
		logger.debug("== UWMissionAssignBL == mSubMissionID ="
				+ mActivityID);
		logger.debug("== UWMissionAssignBL == mActivityID ==="
				+ mActivityID);
		logger.debug("== UWMissionAssignBL == mDefaultOperator ==="
				+ mDefaultOperator);
		logger.debug("== UWMissionAssignBL == mOperator ====="
				+ mOperator);
		logger.debug("== UWMissionAssignBL == mManageCom ==="
				+ mManageCom);

		return true;
	}

	public boolean checkUWUser(String ManageCom) {
		// 校验要调配到的核保师是否属于本公司
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mDefaultOperator);
		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWMissionAssignBL";
			tError.functionName = "dealData";
			tError.errorMessage = "该核保师不存在!";
			this.mErrors.addOneError(tError);
			return false;

		}
		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		tLDUWUserDB.setUserCode(mDefaultOperator);
		if (mActivityID != null
				&& (mActivityID.equals("0000000005")
						|| mActivityID.equals("0000001100") || mActivityID
						.equals("0000005505"))) {
			tLDUWUserDB.setUWType("1"); // 个险
		} else if (mActivityID != null
				&& (mActivityID.equals("0000008005") || mActivityID
						.equals("0000002004"))) {
			tLDUWUserDB.setUWType("2"); // 团险
		}
		if (!tLDUWUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWMissionAssignBL";
			tError.functionName = "dealData";
			tError.errorMessage = "核保师核保类别不对!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mUWPopedom = tLDUWUserDB.getUWPopedom().trim();
		if (ManageCom.indexOf(tLDUserDB.getComCode()) == -1) {
			CError tError = new CError();
			tError.moduleName = "UWMissionAssignBL";
			tError.functionName = "dealData";
			tError.errorMessage = "要调配到的要核保师不是本公司或总公司职员，不能进行分配操作!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public boolean deleteExistsDate() {
		if (mDefaultOperator != null && !mDefaultOperator.equals("")) {
			return true;
		}
		// 若要退回到共享工作池，则先删除申请时保存到数据库的数据,以备再次申请时重新插入数据
		if (mApplyType.equals("xqy") || mApplyType.equals("bq")) {
			LWLockDB tLWLockDB = new LWLockDB();
			LWLockSet tLWLockSet = new LWLockSet();
			tLWLockDB.setMissionID(mMissionID);
			tLWLockDB.setSubMissionID(mSubMissionID);
			tLWLockDB.setLockActivityID(mActivityID);
			tLWLockDB.setLockType("1");
			tLWLockSet = tLWLockDB.query();
			if (tLWLockSet.size() > 0) {
				map.put(tLWLockSet, "DELETE");
			}
		}
		if (mApplyType.equals("lp")) {
			String sql = "select * from lwmission where missionid ='"
					+ "?missionid?" + "' and submissionid='" + "?submissionid?"
					+ "' and "
//					+ "activityid in ('0000001101','0000001104','0000001270') ";
					+ "activityid in (select activityid from lwactivity  where functionid in('10010022','10010023','10010049')) ";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("missionid", mMissionID);
			sqlbv2.put("submissionid", mSubMissionID);
			LWMissionDB tLWMissionDB = new LWMissionDB();
			LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sqlbv2);
			if (tLWMissionSet.size() > 0) {
				map.put(tLWMissionSet, "DELETE");
			}
		}
		return true;

	}
}
