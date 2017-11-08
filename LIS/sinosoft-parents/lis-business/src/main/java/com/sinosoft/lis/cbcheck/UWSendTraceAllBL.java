package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCGCUWSubDB;
import com.sinosoft.lis.db.LCGUWMasterDB;
import com.sinosoft.lis.db.LCGUWSubDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCUWSendTraceDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.SpotPrepare;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCGCUWSubSchema;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.schema.LCGUWSubSchema;
import com.sinosoft.lis.schema.LCUWSendTraceSchema;
import com.sinosoft.lis.schema.LDSpotUWRateSchema;
import com.sinosoft.lis.schema.LDUWUserSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCGCUWSubSet;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.vschema.LCGUWSubSet;
import com.sinosoft.lis.vschema.LCUWSendTraceSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LDSpotTrackSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Picch_UWGrade;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.LDContTimeBL;

/**
 * <p>
 * Title:核保抽检上报通用程序
 * </p>
 * <p>
 * Description: 核保抽检
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class UWSendTraceAllBL {
private static Logger logger = Logger.getLogger(UWSendTraceAllBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	MMap timeMap = new MMap(); // ADD BY zhangtao at 2005-04-09

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;

	/** 业务逻辑类 */
	private LCUWSendTraceSchema mLCUWSendTraceSchema = new LCUWSendTraceSchema();
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWSubSchema mLCCUWSubSchema = new LCCUWSubSchema();
	private LDUWUserSchema mLDUWUserSchema = new LDUWUserSchema();
	private LDSpotTrackSet mLDSpotTrackSet = new LDSpotTrackSet();
	private LCGUWMasterSchema mLCGUWMasterSchema = new LCGUWMasterSchema();
	private LCGUWSubSchema mLCGUWSubSchema = new LCGUWSubSchema();
	// private LCGCUWMasterSchema mLCGCUWMasterSchema = new
	// LCGCUWMasterSchema();
	private LCGCUWSubSchema mLCGCUWSubSchema = new LCGCUWSubSchema();
	private LDSpotUWRateSchema mLDSpotUWRateSchema = new LDSpotUWRateSchema();
	private LCCUWMasterSchema mNewLCCUWMasterSchema = new LCCUWMasterSchema();
	private LCGCUWMasterSchema mNewLCGCUWMasterSchema = new LCGCUWMasterSchema();
	private LCGUWMasterSet mLCGUWMasterSet = new LCGUWMasterSet();
	private LCGUWSubSet mLCGUWSubSet = new LCGUWSubSet();

	// tongmeng 2007-12-14 add
	// 增加上报承保流程
	private String mUWPopedomCode = "";
	private String mUWPopedomGrade = "";
	private String mMissionId = "";
	private String mSubMissionId = "";
	private LWMissionSchema mUpperLWMissionSchema = new LWMissionSchema();

	private String mOtherNoType = "0"; // 其他编码类型，用于表示是何种类型上报或抽检。
										// 1-个险新契约，2-团险新契约
	private String mSpotFlag = "0"; // 抽检标志，用于表示受否被抽检上，1-抽中，2-未被抽中
	private String mSendFlag = "0"; // 上报标志
	private String mSendType = "0"; // 上报类型标志，0-处理完毕 1-超权限上报 2-疑难案例上报 3-
	private String mYesOrNo; // 是否同意标志
	private String mUserCode; // 用户代码,用于记录需要跳转的用户
	private String mContNo; // 个险投保单号
	private String mGrpContNo; // 团体投保单号

	private String mSugIndUWFlag;
	private String mSugIndUWIdea;
	private String mUWFlag;
	private String mUWIdea;
	private String mUWUpReport;

	//tongmeng 2009-02-07 add
	//逐级上报和回退.
	
	String mFinalUWUser = "";
	//lzf 2013-04-10 add,准备上报和返回下级的核保员编码
	String mUWUSer="";
	String mUWState="";//核保状态
	public UWSendTraceAllBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		if (!prepareTransferData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		// 提交数据
		if (cOperate.equals("submit")) {
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWSendTraceAllBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		if (mOtherNoType.equals("1")) // 个险
		{
			// if (mSendFlag.equals("1") ||
			// (mSendFlag.equals("9") ))
			// {
			map.put(mLCUWSendTraceSchema, "INSERT");
			// map.put(mLWMissionSchema, "UPDATE");
			map.put(mNewLCCUWMasterSchema, "UPDATE");
			map.put(mLCCUWSubSchema, "INSERT");
			// if();
			// }
		} else if (mOtherNoType.equals("2")) // 团险
		{
			// if (mSendFlag.equals("1") ||
			// (mSendFlag.equals("9")))
			// {
			map.put(mLCUWSendTraceSchema, "INSERT");
			// map.put(mLWMissionSchema, "UPDATE");
			map.put(mNewLCGCUWMasterSchema, "UPDATE");
			map.put(mLCGCUWSubSchema, "INSERT");
			map.put(mLCGUWMasterSet, "UPDATE");
			map.put(mLCGUWSubSet, "INSERT");
			// }
		}

		// map.put(mLDSpotTrackSet, "INSERT");
		// map.add(timeMap); // ADD BY zhangtao at 2005-04-09
		// mTransferData.setNameAndValue("SendFlag", mSendFlag);
		// mTransferData.setNameAndValue("UserCode", mUserCode);
		mResult.add(mTransferData);
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// LDSpotUWRateDB tLDSpotUWRateDB = new LDSpotUWRateDB();

		// 校验核保师权限表
		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		tLDUWUserDB.setUserCode(mOperator);
		tLDUWUserDB.setUWType("1");
		if (!tLDUWUserDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWSendTraceAllBL";
			tError.functionName = "checkData";
			tError.errorMessage = "未定义核保师权限!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLDUWUserSchema = tLDUWUserDB.getSchema();

		if (mOtherNoType.equals("1")) {
			// 校验核保主表
			LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
			tLCCUWMasterDB.setContNo(mContNo);
			if (!tLCCUWMasterDB.getInfo()) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCContDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "UWSendTraceAllBL";
				tError.functionName = "checkData";
				tError.errorMessage = "查询核保主表失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mNewLCCUWMasterSchema = tLCCUWMasterDB.getSchema();

			// //校验核保师抽检比例
			// tLDSpotUWRateDB.setUserCode(mOperator);
			// tLDSpotUWRateDB.setUWType("01"); //个险抽检类型为01
			// if (!tLDSpotUWRateDB.getInfo())
			// {
			// // @@错误处理
			// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
			// CError tError = new CError();
			// tError.moduleName = "UWSendTraceAllBL";
			// tError.functionName = "checkData";
			// tError.errorMessage = "核保师个险抽检比例未定义!";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// mLDSpotUWRateSchema = tLDSpotUWRateDB.getSchema();

		} else if (mOtherNoType.equals("2")) {
			LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
			tLCGCUWMasterDB.setProposalGrpContNo(mGrpContNo);
			if (!tLCGCUWMasterDB.getInfo()) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCContDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "UWSendTraceAllBL";
				tError.functionName = "checkData";
				tError.errorMessage = "查询集体核保主表失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mNewLCGCUWMasterSchema = tLCGCUWMasterDB.getSchema();

			// //校验核保师抽检比例
			// tLDSpotUWRateDB.setUserCode(mOperator);
			// tLDSpotUWRateDB.setUWType("02"); //个险抽检类型为01
			// if (!tLDSpotUWRateDB.getInfo())
			// {
			// // @@错误处理
			// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
			// CError tError = new CError();
			// tError.moduleName = "UWSendTraceAllBL";
			// tError.functionName = "checkData";
			// tError.errorMessage = "核保师团险抽检比例未定义!";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// mLDSpotUWRateSchema = tLDSpotUWRateDB.getSchema();

		}

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
//		 核保上报流向
		mUWUpReport = (String) mTransferData.getValueByName("UWUpReport");
		logger.debug("@@@@@mUWUpReport="+mUWUpReport);
		mLCUWSendTraceSchema = (LCUWSendTraceSchema) cInputData
				.getObjectByObjectName("LCUWSendTraceSchema", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWSendTraceAllBL";
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
			tError.moduleName = "UWSendTraceAllBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWSendTraceAllBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得上报轨迹表数据
		// mLCUWSendTraceSchema = (LCUWSendTraceSchema) mTransferData.
		// getValueByName("LCUWSendTraceSchema");
		if (mLCUWSendTraceSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWSendTraceAllBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mLCUWSendTraceSchema失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得抽检类型标志 1-个险，2-团险
		mOtherNoType = mLCUWSendTraceSchema.getOtherNoType();
		logger.debug("mOtherNoType:" + mOtherNoType);
		if (mOtherNoType == null || mOtherNoType.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWSendTraceAllBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输保单类型标志失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获取外部数据时，考虑到程序的通用型，每种类型写一个getInputData方法。
		if (mOtherNoType.equals("1")) // 新契约个险
		{
			if (!getInputPersonData()) // 获得个险数据
			{
				return false;
			}
		} else if (mOtherNoType.equals("2")) // 新契约团险
		{

			if (!getInputGrpData()) // 获得团险数据
			{
				return false;

			}

		}
		// 获得上报轨迹表数据

		this.mSendType = (String) mTransferData.getValueByName("UWUpReport");
		logger.debug("mSendType:" + this.mSendType);
		if (mSendType == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWSendTraceAllBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请选择核保流向！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 建议核保结论
		mSugIndUWFlag = (String) mTransferData.getValueByName("SugIndUWFlag");
		// 建议核保意见
		mSugIndUWIdea = (String) mTransferData.getValueByName("SugIndUWIdea");
		// 获得当前工作任务的核保结论标志
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		if (mUWFlag == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中UWFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的核保意见
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		if (mUWIdea == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中UWIdea失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 获取上报级别和上报核保师
		this.mUWPopedomCode = mTransferData.getValueByName("UWPopedomCode") == null ? ""
				: (String) mTransferData.getValueByName("UWPopedomCode");
		this.mUWPopedomGrade = mTransferData.getValueByName("UWPopedomGrade") == null ? ""
				: (String) mTransferData.getValueByName("UWPopedomGrade");

		// 获取是否同意标志
		// mYesOrNo = mLCUWSendTraceSchema.getYesOrNo();
		// if (mYesOrNo == null || mYesOrNo.length() == 0)
		// {
		// mYesOrNo = "First"; //如果同意标志为空则表示是初次进行上报检查，置为"First"
		// }

		return true;
	}

	/**
	 * getInputGrpData 获得团险数据
	 * 
	 * @return boolean
	 */
	private boolean getInputGrpData() {
		// 获得集体核保主表数据
		// mLCGCUWMasterSchema = (LCGCUWMasterSchema) mTransferData.
		// getValueByName("LCGCUWMasterSchema");
		// if (mLCGCUWMasterSchema == null)
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "UWSendTraceAllBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输集体核保主表数据失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		if (mGrpContNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * getInputPersonData 获得个险数据
	 * 
	 * @return boolean
	 */
	private boolean getInputPersonData() {
		// 获得核保主表数据
		// mLCCUWMasterSchema = (LCCUWMasterSchema) mTransferData.
		// getValueByName("LCCUWMasterSchema");
		// if (mLCCUWMasterSchema == null)
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "UWSendTraceAllBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输核保主表数据失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// 当前个险的投保单号
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mMissionId = (String) mTransferData.getValueByName("MissionID");
		if (mMissionId == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionId失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mSubMissionId = (String) mTransferData.getValueByName("SubMissionID");
		if (mMissionId == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
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
		// if (mYesOrNo.equals("First")) //如果为第一次进入
		// {
		// 校验核保级别，如果核保师级别不够，则上报
		// if (!checkGrade())
		// {
		// return false;
		// }

		// //抽检
		// if (!spotUW())
		// {
		// return false;
		// }

		// 准备核保业务数据
		if (!prepareUW()) {
			return false;
		}

		// 准备核保轨迹表数据
		if (!prepareSendTrace()) {
			return false;
		}
		if (!this.dealUpperTrace()) {
			return false;
		}
		// }
		// else if (mYesOrNo.equals("Y"))
		// {
		// //校验核保级别，如果核保师级别不够，则上报
		// if (!checkGrade())
		// {
		// return false;
		// }
		//
		// //准备核保业务数据
		// if (!prepareUW())
		// {
		// return false;
		// }
		//
		// //准备核保轨迹表数据
		// if (!prepareSendTrace())
		// {
		// return false;
		// }
		// }
		// else if (mYesOrNo.equals("N"))
		// {
		// //准备核保业务数据
		// if (!prepareUW())
		// {
		// return false;
		// }
		//
		// //准备核保轨迹表数据
		// if (!prepareSendTrace())
		// {
		// return false;
		// }
		//
		// //将上报标志置为下报
		// mSendFlag = "9";
		// }

		return true;
	}

	/**
	 * prepareMission 准备工作流轨迹表
	 * 
	 * @return boolean
	 */
	private boolean prepareMission() {
		if (mSendFlag.equals("1")
				|| (mSendFlag.equals("9") && (mYesOrNo.equals("N")))) // 核保上报
		{
//			LWMissionDB tLWMissionDB = new LWMissionDB();
//			if (mOtherNoType.equals("1")) {
//				tLWMissionDB.setActivityID("0000001110"); // 人工核保工作流节点
//				tLWMissionDB.setMissionProp2(mLCUWSendTraceSchema.getOtherNo());
//			} else if (mOtherNoType.equals("2")) {
//				tLWMissionDB.setActivityID("0000002004"); // 人工核保工作流节点
//				tLWMissionDB.setMissionProp1(mLCUWSendTraceSchema.getOtherNo());
//			}
//
//			LWMissionSet tLWMissionSet = tLWMissionDB.query();
//			if (tLWMissionSet == null || tLWMissionSet.size() <= 0) {
//				// @@错误处理
//				// this.mErrors.copyAllErrors( tLCContDB.mErrors );
//				CError tError = new CError();
//				tError.moduleName = "UWSendTraceAllBL";
//				tError.functionName = "checkData";
//				tError.errorMessage = "查询核保主表失败!";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//			mLWMissionSchema = tLWMissionSet.get(1);
			if (mYesOrNo.equals("N")) {
				//mLWMissionSchema.setDefaultOperator(mUserCode);// modify by lzf 
				mTransferData.removeByName("DefaultOperator");
				mTransferData.setNameAndValue("DefaultOperator", mUserCode);
			} else {
//				mLWMissionSchema.setDefaultOperator(mLDUWUserSchema
//						.getUpUserCode());
				mTransferData.removeByName("DefaultOperator");
				mTransferData.setNameAndValue("DefaultOperator", mLDUWUserSchema.getUpUserCode());
				mUserCode = mLDUWUserSchema.getUpUserCode();
			}
		}
		// ====ADD=======zhangtao=======2005-04-09==============BGN=====================
//		String tMissionID = (String) mTransferData.getValueByName("MissionID");
//		String tSubMissionID = (String) mTransferData
//				.getValueByName("SubMissionID");
//		String tActivityID = "";
//
//		if (mOtherNoType.equals("1")) {
//			tActivityID = "0000001110";
//		} else if (mOtherNoType.equals("2")) {
//			tActivityID = "0000002004";
//		}
//
//		LDCodeSet tLDCodeSet = new LDCodeSet();
//		LDCodeDB tLDCodeDB = new LDCodeDB();
//		tLDCodeDB.setCodeType("busitype");
//		tLDCodeDB.setOtherSign(tActivityID);
//		tLDCodeSet.set(tLDCodeDB.query());
//		if (tLDCodeDB.mErrors.needDealError()) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
//			CError tError = new CError();
//			tError.moduleName = "UWSendTraceAllBL";
//			tError.functionName = "prepareMission";
//			tError.errorMessage = "业务类型查询失败!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//		if (tLDCodeSet != null && tLDCodeSet.size() == 1) {
//			// 记录工作时效
//			TransferData tTransferData = new TransferData();
//			tTransferData.setNameAndValue("MissionID", tMissionID);
//			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
//			tTransferData.setNameAndValue("ActivityID", tActivityID);
//
//			VData timeVData = new VData();
//			timeVData.add(tTransferData);
//			timeVData.add(mGlobalInput);
//
//			LDContTimeBL tLDContTimeBL = new LDContTimeBL();
//			if (tLDContTimeBL.submitData(timeVData, "")) {
//				VData tResultData = tLDContTimeBL.getResult();
//				timeMap = (MMap) tResultData.getObjectByObjectName("MMap", 0);
//			}//新工作流中已经有时效的处理可以去掉modify by lzf 
//		}

		// ====ADD=======zhangtao=======2005-04-09==============END=====================
		return true;
	}

	private boolean dealUpperTrace() {
		//--------------------add by lzf 2013-04-10----------------------
		String tSql = "select t.sendtype, t.upusercode,t.operator from LCUWSendTrace t where otherno='"+ "?contno?" +"' order by uwno";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("contno", mContNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if(tSSRS != null && tSSRS.getMaxRow()>0){

		this.mUWUSer = tSSRS.GetText(1, 3)+":"+tSSRS.GetText(1, 2);
		//上报
		if(mSendType.equals("1")){
			for(int i=1;i<tSSRS.MaxRow;i++){
				if(tSSRS.GetText(i+1, 1)=="1" || "1".equals(tSSRS.GetText(i+1, 1))){
					mUWUSer = mUWUSer +":"+ tSSRS.GetText(i+1, 2);
					logger.debug("temp1==="+mUWUSer);
				}
				mUWUSer=mUWUSer;
				if(tSSRS.GetText(i+1, 1)=="4" || "4".equals(tSSRS.GetText(i+1, 1))){
					mUWUSer = mUWUSer.substring(0,mUWUSer.lastIndexOf(":"));
					logger.debug("temp4==="+mUWUSer);
				}		    
			}
			mUWUSer = mUWUSer + ":" + this.mUWPopedomCode;
			logger.debug("temp==="+mUWUSer);
		}
		//返回下级
		if(mSendType.equals("4")){
		for(int i=1;i<tSSRS.MaxRow;i++){
			if(tSSRS.GetText(i+1, 1)=="1" || "1".equals(tSSRS.GetText(i+1, 1))){
				mUWUSer = mUWUSer +":"+ tSSRS.GetText(i+1, 2);
				logger.debug("temp1==="+mUWUSer);
			}
			mUWUSer=mUWUSer;
			if(tSSRS.GetText(i+1, 1)=="4" || "4".equals(tSSRS.GetText(i+1, 1))){
				mUWUSer = mUWUSer.substring(0,mUWUSer.lastIndexOf(":"));
				logger.debug("temp4==="+mUWUSer);
			}	
		}
		 this.mUWUSer = mUWUSer.substring(0, mUWUSer.lastIndexOf(":"));
		 this.mFinalUWUser = mUWUSer.substring(mUWUSer.lastIndexOf(":")+1);
		 logger.debug("temp==="+mUWUSer);
		 logger.debug("mFinalUWUser==="+ mFinalUWUser);
		 
		}
		}else{
			if(mSendType.equals("4")){
				CError.buildErr(this, "不能返回下级，请先上报！");
				return false;
			}
			this.mUWUSer = this.mOperator+":"+this.mUWPopedomCode;
		}
		 String tSql2 = "select uwstate from lccuwmaster where contno='"+ "?contno?" +"'";
		 SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql2);
			sqlbv1.put("contno", mContNo);
		 this.mUWState = tExeSQL.getOneValue(sqlbv1);
		//------------------------end 2013-04-10---------------------------------
//		String tSQL = "select * from lwmission where missionid='"
//				+ this.mMissionId + "'  and activityid='0000001110' ";
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		LWMissionSet tLWMissionSet = new LWMissionSet();
//		tLWMissionSet = tLWMissionDB.executeQuery(tSQL);
//		if (tLWMissionSet.size() <= 0) {
//			CError tError = new CError();
//			tError.moduleName = "UWSendTraceAllBL";
//			tError.functionName = "prepareUW";
//			tError.errorMessage = "查询工作流待人工核保结点出错!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//
//		this.mUpperLWMissionSchema.setSchema(tLWMissionSet.get(1));
//		// 上报承保
//		if (mSendType.equals("1")) {
//			// 增加轨迹
//			if (this.mUpperLWMissionSchema == null
//					|| this.mUpperLWMissionSchema.equals("")) {
//				this.mUpperLWMissionSchema
//						.setMissionProp19(this.mUpperLWMissionSchema
//								.getDefaultOperator());
//			} else {
//				String tMissionProp19 = this.mUpperLWMissionSchema
//						.getMissionProp19() == null ? this.mUpperLWMissionSchema
//						.getDefaultOperator()
//						: this.mUpperLWMissionSchema.getMissionProp19().trim();
//				this.mUpperLWMissionSchema.setMissionProp19(tMissionProp19
//						+ ":" + this.mUWPopedomCode);
//			}
//		} else {
//			// 返回下级
//			// 判断是否可以返回下级 2008-12-24 ln add
//			if(this.mUpperLWMissionSchema.getMissionProp19()==null 
//					|| this.mUpperLWMissionSchema.getMissionProp19().equals("")
//					|| this.mUpperLWMissionSchema.getMissionProp19().indexOf(":")==-1)
//			{
//				CError.buildErr(this, "不能返回下级，请先上报！");
//				return false;
//			}
//			// 2008-12-24 end 
//			this.mUpperLWMissionSchema
//					.setMissionProp20(this.mUpperLWMissionSchema
//							.getMissionProp19().substring(
//									this.mUpperLWMissionSchema
//											.getMissionProp19()
//											.lastIndexOf(":") + 1));
//			//tongmeng 2009-02-07 add
//			//
//			String tLastTrace = this.mUpperLWMissionSchema.getMissionProp19().substring(0,
//					this.mUpperLWMissionSchema.getMissionProp19().lastIndexOf(":"));
//			
//			this.mFinalUWUser = tLastTrace.substring(tLastTrace.lastIndexOf(":") + 1);
//			
//			this.mUpperLWMissionSchema
//					.setMissionProp19(this.mUpperLWMissionSchema
//							.getMissionProp19().substring(
//									0,
//									this.mUpperLWMissionSchema
//											.getMissionProp19()
//											.lastIndexOf(":")));
//		}

		return true;
	}

	/**
	 * prepareSendTrace 准备核保轨迹表数据
	 * 
	 * @return boolean
	 */
	private boolean prepareSendTrace() {
		int uwno = 0;
		String tUserCode = "";
		String tUWPopedom = "";
		LCUWSendTraceDB tLCUWSendTraceDB = new LCUWSendTraceDB();
		tLCUWSendTraceDB.setOtherNo(mLCUWSendTraceSchema.getOtherNo());
		tLCUWSendTraceDB.setOtherNoType(mOtherNoType);
		LCUWSendTraceSet tLCUWSendTraceSet = new LCUWSendTraceSet();
		tLCUWSendTraceSet = tLCUWSendTraceDB.query();

		if (tLCUWSendTraceSet == null || tLCUWSendTraceSet.size() == 0) {
			uwno = 1;
			mLCUWSendTraceSchema.setUWCode(mOperator);
			mLCUWSendTraceSchema.setUWPopedom(getPopedom(mOperator,
					mOtherNoType));
		} else {
			uwno = tLCUWSendTraceSet.size() + 1;
			mUserCode = tLCUWSendTraceSet.get(1).getUWCode();
			mLCUWSendTraceSchema.setUWCode(mUserCode);
			mLCUWSendTraceSchema.setUWPopedom(getPopedom(mUserCode,
					mOtherNoType));
			// 查询原核保员
			tLCUWSendTraceDB.setUWNo(1);
			tLCUWSendTraceDB.getInfo();
			// 原核保员等级
			tUWPopedom = tLCUWSendTraceDB.getUWPopedom();
			// 原核保员代码
			tUserCode = tLCUWSendTraceDB.getUWCode();

		}

		mLCUWSendTraceSchema.setUWNo(uwno);
		mLCUWSendTraceSchema.setOperator(mOperator);
		mLCUWSendTraceSchema.setMakeDate(PubFun.getCurrentDate());
		mLCUWSendTraceSchema.setMakeTime(PubFun.getCurrentTime());
		mLCUWSendTraceSchema.setModifyDate(PubFun.getCurrentDate());
		mLCUWSendTraceSchema.setModifyTime(PubFun.getCurrentTime());
		mLCUWSendTraceSchema.setSendType(mSendType);
		// 根据不同的上报类型决定核保任务的流向。
		// 疑难案例
		if (mSendType.equals("1")) {
			mLCUWSendTraceSchema.setSendFlag("1");

			mLCUWSendTraceSchema.setUpUserCode(this.mUWPopedomCode);
			mLCUWSendTraceSchema.setUpUWPopedom(getUpReportPopedom(
					this.mUWPopedomCode, mOtherNoType));
			// mLCUWSendTraceSchema.setDownUWCode(mOperator);
			// mLCUWSendTraceSchema.setDownUWPopedom(getPopedom(mOperator));
		}
		// 超权限
		/*
		 * if (mSendType.equals("2")) { mLCUWSendTraceSchema.setSendFlag("1");
		 * //由于不需要指定特定的核保员故在此不存核保员代码 mLCUWSendTraceSchema.setUpUserCode("");
		 * mLCUWSendTraceSchema.setUpUWPopedom(getUpReportPopedom(mOperator,mOtherNoType)); //
		 * mLCUWSendTraceSchema.setDownUWCode(mOperator); //
		 * mLCUWSendTraceSchema.setDownUWPopedom(getPopedom(mOperator)); }
		 */
		// 返回下级
		if (mSendType.equals("4")) {
			// String aUpUWPopedom = mLCUWSendTraceSchema.getUpUWPopedom();
			// if(aUpUWPopedom == null || aUpUWPopedom.equals(""))
			// {
			//
			// CError tError = new CError();
			// tError.moduleName = "UWSendTraceAllBL";
			// tError.functionName = "prepareUW";
			// tError.errorMessage = "该保单还没有上报，不能选择下报";
			// this.mErrors.addOneError(tError);
			// return false;
			//
			// }
			mLCUWSendTraceSchema.setSendFlag("1");
			// 由于不需要指定特定的核保员故在此不存核保员代码
			mLCUWSendTraceSchema.setUpUserCode(tUserCode);
			mLCUWSendTraceSchema.setUpUWPopedom(tUWPopedom);
			// tongmeng 2007-12-14 返回下级需要查询出上次的核保师
			// mLCUWSendTraceSchema.setDownUWCode(mOperator);
			// mLCUWSendTraceSchema.setDownUWPopedom(getPopedom(mOperator));
		}

		// if (mLCUWSendTraceSchema.getYesOrNo() != null &&
		// mLCUWSendTraceSchema.getYesOrNo().equals("N"))
		// {
		// mLCUWSendTraceSchema.setSendFlag("1");
		// 由于不需要指定特定的核保员故在此不存核保员代码
		// mLCUWSendTraceSchema.setUpUserCode("");
		// mLCUWSendTraceSchema.setUpUWPopedom(getUpReportPopedom(mOperator));
		// mLCUWSendTraceSchema.setDownUWCode(mOperator);
		// mLCUWSendTraceSchema.setDownUWPopedom(getPopedom(mOperator));
		// }
		// else
		// {
		// mLCUWSendTraceSchema.setSendFlag("1");
		// mLCUWSendTraceSchema.setUpUserCode(mLDUWUserSchema.getUpUserCode());
		// mLCUWSendTraceSchema.setUpUWPopedom(getPopedom(mLDUWUserSchema.
		// getUpUserCode()));
		// mLCUWSendTraceSchema.setDownUWCode(mOperator);
		// mLCUWSendTraceSchema.setDownUWPopedom(getPopedom(mOperator));
		// }

		return true;
	}

	/**
	 * prepareUW 准备核保业务数据
	 * 
	 * @return boolean
	 */
	private boolean prepareUW() {
		if (mOtherNoType.equals("1")) {
			if (!preparePersonUW()) {
				return false;
			}
		} else if (mOtherNoType.equals("2")) {
			if (!prepareGrpUW()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * prepareGrpUW 准备团险数据
	 * 
	 * @return boolean
	 */
	private boolean prepareGrpUW() {
		mNewLCGCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mNewLCGCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		mNewLCGCUWMasterSchema.setOperator(mOperator);
		mNewLCGCUWMasterSchema.setPassFlag(mUWFlag); // 上报
		mNewLCGCUWMasterSchema.setUWIdea(mLCUWSendTraceSchema.getUWIdea());
		mNewLCGCUWMasterSchema.setUWNo(mNewLCGCUWMasterSchema.getUWNo() + 1);

		// 合同核保轨迹表
		LCGCUWSubDB tLCGCUWSubDB = new LCGCUWSubDB();
		tLCGCUWSubDB.setGrpContNo(mNewLCGCUWMasterSchema.getGrpContNo());
		LCGCUWSubSet tLCGCUWSubSet = new LCGCUWSubSet();
		tLCGCUWSubSet = tLCGCUWSubDB.query();
		if (tLCGCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWSendTraceAllBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mGrpContNo + "合同核保轨迹表查失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int nUWNo = tLCGCUWSubSet.size();
		if (nUWNo > 0) {
			mLCGCUWSubSchema.setUWNo(++nUWNo); // 第几次核保
		} else {
			mLCGCUWSubSchema.setUWNo(1); // 第1次核保
		}

		mLCGCUWSubSchema = tLCGCUWSubSet.get(1).getSchema();
		mLCGCUWSubSchema.setUWNo(nUWNo);
		mLCGCUWSubSchema.setPassFlag(mUWFlag);
		mLCGCUWSubSchema.setUWIdea(mLCUWSendTraceSchema.getUWIdea());
		mLCGCUWSubSchema.setOperator(mOperator);
		mLCGCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		mLCGCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		mLCGCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		mLCGCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		LCGUWSubDB tLCGUWSubDB;
		LCGUWSubSchema tLCGUWSubSchema = new LCGUWSubSchema();
		int uwno = 0;
		LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB();
		tLCGUWMasterDB.setGrpContNo(mNewLCGCUWMasterSchema.getGrpContNo());
		mLCGUWMasterSet = tLCGUWMasterDB.query();
		if (mLCGUWMasterSet == null) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWSendTraceAllBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "集体险种核保轨迹表查失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= mLCGUWMasterSet.size(); i++) {
			uwno = mLCGUWMasterSet.get(i).getUWNo(); // 获得核保序号
			mLCGUWMasterSet.get(i).setPassFlag(mUWFlag);
			mLCGUWMasterSet.get(i).setUWIdea(mLCUWSendTraceSchema.getUWIdea());
			mLCGUWMasterSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLCGUWMasterSet.get(i).setUWNo(uwno + 1);
			mLCGUWMasterSet.get(i).setModifyTime(PubFun.getCurrentTime());
			mLCGUWMasterSet.get(i).setOperator(mOperator);

			tLCGUWSubDB = new LCGUWSubDB();
			tLCGUWSubDB.setGrpProposalNo(mLCGUWMasterSet.get(i)
					.getGrpProposalNo());
			tLCGUWSubDB.setGrpPolNo(mLCGUWMasterSet.get(i).getGrpPolNo());
			tLCGUWSubDB.setUWNo(uwno);
			if (!tLCGUWSubDB.getInfo()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGCUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWSendTraceAllBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = "集体险种核保轨迹表查失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCGUWSubSchema = tLCGUWSubDB.getSchema();
			tLCGUWSubSchema.setPassFlag(mUWFlag);
			tLCGUWSubSchema.setUWIdea(mLCUWSendTraceSchema.getUWIdea());
			tLCGUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGUWSubSchema.setUWNo(uwno + 1);
			tLCGUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			tLCGUWSubSchema.setOperator(mOperator);

			mLCGUWSubSet.add(tLCGUWSubSchema);
		}

		return true;
	}

	/**
	 * preparePersonUW 准备个险数据
	 * 
	 * @return boolean
	 */
	private boolean preparePersonUW() {
		// 判断核保员等级，决定上报的等级

		mNewLCCUWMasterSchema.setUWNo(mNewLCCUWMasterSchema.getUWNo() + 1);
		mNewLCCUWMasterSchema.setState(mLCUWSendTraceSchema.getUWFlag());
		mNewLCCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保
		mNewLCCUWMasterSchema.setOperator(mOperator); // 操作员
		mNewLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mNewLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		mNewLCCUWMasterSchema.setPassFlag(mUWFlag); // 上报
		mNewLCCUWMasterSchema.setUWIdea(mUWIdea);
		mNewLCCUWMasterSchema.setSugPassFlag(mLCUWSendTraceSchema.getUWFlag());
		mNewLCCUWMasterSchema.setSugUWIdea(mLCUWSendTraceSchema.getUWIdea());
		mNewLCCUWMasterSchema.setUpReport(mUWUpReport); // 核保流向标志
		// 合同核保轨迹表
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWSendTraceAllBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mContNo + "合同核保轨迹表查失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int nUWNo = tLCCUWSubSet.size();
		if (nUWNo > 0) {
			mLCCUWSubSchema.setUWNo(++nUWNo); // 第几次核保
		} else {
			mLCCUWSubSchema.setUWNo(1); // 第1次核保
		}

		mLCCUWSubSchema.setContNo(mNewLCCUWMasterSchema.getContNo());
		mLCCUWSubSchema.setGrpContNo(mNewLCCUWMasterSchema.getGrpContNo());
		mLCCUWSubSchema.setProposalContNo(mNewLCCUWMasterSchema
				.getProposalContNo());
		mLCCUWSubSchema.setInsuredNo(mNewLCCUWMasterSchema.getInsuredNo());
		mLCCUWSubSchema.setInsuredName(mNewLCCUWMasterSchema.getInsuredName());
		mLCCUWSubSchema.setAppntNo(mNewLCCUWMasterSchema.getAppntNo());
		mLCCUWSubSchema.setAppntName(mNewLCCUWMasterSchema.getAppntName());
		mLCCUWSubSchema.setAgentCode(mNewLCCUWMasterSchema.getAgentCode());
		mLCCUWSubSchema.setAgentGroup(mNewLCCUWMasterSchema.getAgentGroup());
		mLCCUWSubSchema.setUWGrade(mNewLCCUWMasterSchema.getUWGrade()); // 核保级别
		mLCCUWSubSchema.setAppGrade(mNewLCCUWMasterSchema.getAppGrade()); // 申请级别
		mLCCUWSubSchema.setAutoUWFlag(mNewLCCUWMasterSchema.getAutoUWFlag());
		mLCCUWSubSchema.setState(mNewLCCUWMasterSchema.getState());
		mLCCUWSubSchema.setPassFlag(mNewLCCUWMasterSchema.getPassFlag());
		mLCCUWSubSchema.setPostponeDay(mNewLCCUWMasterSchema.getPostponeDay());
		mLCCUWSubSchema
				.setPostponeDate(mNewLCCUWMasterSchema.getPostponeDate());
		mLCCUWSubSchema.setUpReportContent(mNewLCCUWMasterSchema
				.getUpReportContent());
		mLCCUWSubSchema.setHealthFlag(mNewLCCUWMasterSchema.getHealthFlag());
		mLCCUWSubSchema.setSpecFlag(mNewLCCUWMasterSchema.getSpecFlag());
		mLCCUWSubSchema.setSpecReason(mNewLCCUWMasterSchema.getSpecReason());
		mLCCUWSubSchema.setQuesFlag(mNewLCCUWMasterSchema.getQuesFlag());
		mLCCUWSubSchema.setReportFlag(mNewLCCUWMasterSchema.getReportFlag());
		mLCCUWSubSchema.setChangePolFlag(mNewLCCUWMasterSchema
				.getChangePolFlag());
		mLCCUWSubSchema.setChangePolReason(mNewLCCUWMasterSchema
				.getChangePolReason());
		mLCCUWSubSchema.setAddPremReason(mNewLCCUWMasterSchema
				.getAddPremReason());
		mLCCUWSubSchema.setPrintFlag(mNewLCCUWMasterSchema.getPrintFlag());
		mLCCUWSubSchema.setPrintFlag2(mNewLCCUWMasterSchema.getPrintFlag2());
		mLCCUWSubSchema.setUWIdea(mNewLCCUWMasterSchema.getUWIdea());
		mLCCUWSubSchema.setOperator(mNewLCCUWMasterSchema.getOperator()); // 操作员
		mLCCUWSubSchema.setManageCom(mNewLCCUWMasterSchema.getManageCom());
		mLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		mLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		mLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		mLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		mLCCUWSubSchema.setSugPassFlag(mLCUWSendTraceSchema.getUWFlag());
		mLCCUWSubSchema.setSugUWIdea(mLCUWSendTraceSchema.getUWIdea());
		mLCCUWSubSchema.setUpReport(mUWUpReport); // 核保流向标志

		return true;
	}

	/**
	 * spotUW 是否需要抽检
	 * 
	 * @return boolean
	 */
	private boolean spotUW() {
		// 不需要上报则进行抽检
		if (mSendFlag.equals("0")) {
			SpotPrepare tSpotPrepare = new SpotPrepare();
			if (!tSpotPrepare.PrepareData(mLCUWSendTraceSchema.getOtherNo(),
					mOperator, getPopedom(mOperator, "1"), mLDSpotUWRateSchema
							.getUWType(), "1", true)) // 编码规则见pdm
			{
				if (tSpotPrepare.mErrors.needDealError()) {
					this.mErrors.copyAllErrors(tSpotPrepare.mErrors);
				} else {
					mLDSpotTrackSet = tSpotPrepare.getLDSpotTrackSet();
				}
			} else {
				mLDSpotTrackSet = tSpotPrepare.getLDSpotTrackSet();
				mSendFlag = "1"; // 1-上报 0-不上报或下报
				mSendType = "2"; // 1-普通上报 2-抽检上报
			}
			mLCUWSendTraceSchema.setSendType(mSendType);
		}

		return true;
	}

	/**
	 * checkGrade 校验核保级别，如果核保师级别不够，则上报
	 * 
	 * @return boolean
	 */
	private boolean checkGrade() {
		double tAmnt;
		String tGrade;
		Picch_UWGrade tPicch_UWGrade = new Picch_UWGrade();
		// 获取外部数据时，考虑到程序的通用型，每种类型写一个getInputData方法。
		if (mOtherNoType.equals("1")) // 新契约个险
		{
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mNewLCCUWMasterSchema.getContNo());
			if (!tLCContDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "UWSendTraceAllBL";
				tError.functionName = "checkGrade";
				tError.errorMessage = "合同表查失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mLDUWUserSchema.getPopUWFlag() != null
					&& mLDUWUserSchema.getPopUWFlag().equals("1")) {
				mSendFlag = "0"; // 不需要上报
			} else {
				tAmnt = tLCContDB.getAmnt();
				tGrade = tPicch_UWGrade.getUWGrade(tAmnt, "1");

				// 如果保单核保级别大于核保师核保级别，则核保上报
				if (tGrade.compareTo(mLDUWUserSchema.getUWPopedom()) > 0) {
					mSendFlag = "1"; // 上报
					mSendType = "1"; // 普通上报
				} else // 如果不需要上报，且从没上报过，则需要进行抽检
				{
					mSendFlag = "0"; // 不需要上报
				}
			}
		} else if (mOtherNoType.equals("2")) // 新契约团险
		{
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(mNewLCGCUWMasterSchema.getGrpContNo());
			if (!tLCGrpContDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "UWSendTraceAllBL";
				tError.functionName = "checkGrade";
				tError.errorMessage = "集体合同表查失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mLDUWUserSchema.getPopUWFlag() != null
					&& mLDUWUserSchema.getPopUWFlag().equals("1")) {
				mSendFlag = "0"; // 不需要上报
			} else {

				tAmnt = tLCGrpContDB.getPrem();
				tGrade = tPicch_UWGrade.getUWGrade(tAmnt, "1");

				// 如果保单核保级别大于核保师核保级别，则核保上报
				if (tGrade.compareTo(mLDUWUserSchema.getUWPopedom()) > 0) {
					mSendFlag = "1"; // 上报
					mSendType = "1"; // 普通上报
				} else {
					mSendFlag = "0"; // 不需要上报
				}
			}
		}
		return true;
	}

	/**
	 * getPopedom 得到核保师级别
	 * 
	 * @param tUserCode
	 *            String
	 * @return String
	 */
	private String getPopedom(String tUserCode, String tOtherNoType) {
		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		tLDUWUserDB.setUserCode(tUserCode);
		tLDUWUserDB.setUWType(tOtherNoType); // 1-个险，2-团险
		tLDUWUserDB.getInfo();

		return tLDUWUserDB.getUWPopedom();
	}

	/**
	 * 得到上级核保员的级别
	 * 
	 * @param tUserCode
	 *            String
	 * @return String
	 */
	private String getUpReportPopedom(String tUserCode, String tOtherNoType) {
		if (this.mUWPopedomGrade != null && !this.mUWPopedomGrade.equals("")) {
			return this.mUWPopedomGrade;
		}
		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		tLDUWUserDB.setUserCode(tUserCode);
		tLDUWUserDB.setUWType(tOtherNoType);
		tLDUWUserDB.getInfo();
		// 得到下级核保员的权限等级之后，可以判断他的上级核保员的等级
		return tLDUWUserDB.getUpUWPopedom();
	}

	/**
	 * 准备TransferData数据
	 * 
	 * @return boolean
	 */
	private boolean prepareTransferData() {
		// 准备上报的等级
//		mTransferData.setNameAndValue("UWAuthority", this.mLCUWSendTraceSchema
//				.getUpUWPopedom());//上报和返回下级的投保单不改变核保级别2009-2-4
		logger.debug("mLCUWSendTraceSchema.getUpUWPopedom():"
				+ this.mLCUWSendTraceSchema.getUpUWPopedom());
		;
		// 返回的核保员代码
		if (mSendType.equals("1")) {
			//上报承保
		mTransferData.setNameAndValue("UserCode", mLCUWSendTraceSchema
				.getUpUserCode());
		}
		else
		{
			//返回下级
			mTransferData.setNameAndValue("UserCode", this.mFinalUWUser);
		}
//		mTransferData.setNameAndValue("UpperUwUser", this.mUpperLWMissionSchema
//				.getMissionProp19());
//		mTransferData.setNameAndValue("LowerUwUser", this.mUpperLWMissionSchema
//				.getMissionProp20());
//		// 准备核保状态
//		mTransferData.setNameAndValue("Uw_State", this.mUpperLWMissionSchema
//				.getMissionProp18());
		mTransferData.setNameAndValue("UpperUwUser", this.mUWUSer);
		mTransferData.setNameAndValue("LowerUwUser", mOperator);
		// 准备核保状态
		mTransferData.setNameAndValue("Uw_State", this.mUWState);
		logger.debug("mLCUWSendTraceSchema.getUpUserCode():"
				+ mLCUWSendTraceSchema.getUpUserCode());

		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
