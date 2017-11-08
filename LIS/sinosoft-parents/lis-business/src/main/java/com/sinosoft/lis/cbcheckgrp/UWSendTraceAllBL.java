package com.sinosoft.lis.cbcheckgrp;
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
	private LWMissionSchema mUpdateLWMissionSchema = new LWMissionSchema();
	/** 业务数据字符串 */
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
				CError.buildErr(this,"数据提交失败!");
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
			//map.put(this.mUpdateLWMissionSchema, "UPDATE");
			map.put(mNewLCGCUWMasterSchema, "UPDATE");
			map.put(mLCGCUWSubSchema, "INSERT");
			map.put(mLCGUWMasterSet, "UPDATE");
			map.put(mLCGUWSubSet, "INSERT");
			// }
		}
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
		tLDUWUserDB.setUWType("2"); //团险uwtyp=‘2’ modify at 2008-12-17
		if (!tLDUWUserDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"未定义核保师权限!");
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
				CError.buildErr(this,"查询核保主表失败!");
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
				CError.buildErr(this,"查询集体核保主表失败!");
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
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据mOperator失败!");
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据mOperator失败!");
			return false;
		}

		// 获得上报轨迹表数据
		//mLCUWSendTraceSchema = (LCUWSendTraceSchema) mTransferData
				//.getValueByName("LCUWSendTraceSchema");
		  mLCUWSendTraceSchema = (LCUWSendTraceSchema) cInputData.getObjectByObjectName(
	                "LCUWSendTraceSchema", 0);

		if (mLCUWSendTraceSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据mLCUWSendTraceSchema失败!");
			return false;
		}

		// 获得抽检类型标志 1-个险，2-团险
		mOtherNoType = mLCUWSendTraceSchema.getOtherNoType();
		logger.debug("mOtherNoType:" + mOtherNoType);
		if (mOtherNoType == null || mOtherNoType.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输保单类型标志失败!");
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
			CError.buildErr(this,"请选择核保流向！");
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
			CError.buildErr(this,"前台传输业务数据中UWFlag失败!");
			return false;
		}

		// 获得当前工作任务的核保意见
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		if (mUWIdea == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中UWIdea失败!");
			return false;
		}

		
		return true;
	}

	/**
	 * getInputGrpData 获得团险数据
	 * 
	 * @return boolean
	 */
	private boolean getInputGrpData() {


		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		if (mGrpContNo == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中ContNo失败!");
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

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 准备核保业务数据
		if (!prepareUW()) {
			return false;
		}

		// 准备核保轨迹表数据
		if (!prepareSendTrace()) {
			return false;
		}
		
		return true;
	}

	/**
	 * prepareMission 准备工作流轨迹表
	 * 
	 * @return boolean
	 */
	private boolean prepareMission() {
		//modify by lzf 20130425
		String mActivityID =new ExeSQL().getOneValue("select activityid from lwactivity where functionid = '10010028'");
		if (mSendFlag.equals("1")
				|| (mSendFlag.equals("9") && (mYesOrNo.equals("N")))) // 核保上报
		{
			LWMissionDB tLWMissionDB = new LWMissionDB();
			if (mOtherNoType.equals("1")) {
				
//				tLWMissionDB.setActivityID("0000001100"); // 人工核保工作流节点
				tLWMissionDB.setActivityID(mActivityID); // 人工核保工作流节点
				tLWMissionDB.setMissionProp2(mLCUWSendTraceSchema.getOtherNo());
			} else if (mOtherNoType.equals("2")) {
				tLWMissionDB.setActivityID("0000002004"); // 人工核保工作流节点
				tLWMissionDB.setMissionProp1(mLCUWSendTraceSchema.getOtherNo());
			}

			LWMissionSet tLWMissionSet = tLWMissionDB.query();
			if (tLWMissionSet == null || tLWMissionSet.size() <= 0) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCContDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "UWSendTraceAllBL";
				tError.functionName = "checkData";
				tError.errorMessage = "查询核保主表失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLWMissionSchema = tLWMissionSet.get(1);
			if (mYesOrNo.equals("N")) {
				mLWMissionSchema.setDefaultOperator(mUserCode);
			} else {
				mLWMissionSchema.setDefaultOperator(mLDUWUserSchema
						.getUpUserCode());
				mUserCode = mLDUWUserSchema.getUpUserCode();
			}
		}
		// ====ADD=======zhangtao=======2005-04-09==============BGN=====================
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		String tActivityID = "";

		if (mOtherNoType.equals("1")) {
			tActivityID = mActivityID;//modify by lzf
		} else if (mOtherNoType.equals("2")) {
			tActivityID = "0000002004";
		}

		LDCodeSet tLDCodeSet = new LDCodeSet();
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("busitype");
		tLDCodeDB.setOtherSign(tActivityID);
		tLDCodeSet.set(tLDCodeDB.query());
		if (tLDCodeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWSendTraceAllBL";
			tError.functionName = "prepareMission";
			tError.errorMessage = "业务类型查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLDCodeSet != null && tLDCodeSet.size() == 1) {
			// 记录工作时效
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);

			VData timeVData = new VData();
			timeVData.add(tTransferData);
			timeVData.add(mGlobalInput);

			LDContTimeBL tLDContTimeBL = new LDContTimeBL();
			if (tLDContTimeBL.submitData(timeVData, "")) {
				VData tResultData = tLDContTimeBL.getResult();
				timeMap = (MMap) tResultData.getObjectByObjectName("MMap", 0);
			}
		}

		// ====ADD=======zhangtao=======2005-04-09==============END=====================
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
		//获得权限,控制属性和机构.
		String tTempUW = getUpReportAuthAndProp();
		if(tTempUW==null)
		{
			return false;
		}
		String[] tUWStr = PubFun.split(tTempUW,":");
		if(tUWStr.length!=3)
		{
			CError.buildErr(this,"获得上报权限和机构出错!");
			return false;
		}
		//xx:xx:xx  权限:控制属性:上报到机构
		String tTempUWPomEdom = tUWStr[0];
		String tTempCtrlProp = tUWStr[1];
		String tTempComCode = tUWStr[2];
		
		
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
			// 由于不需要指定特定的核保员故在此不存核保员代码
			mLCUWSendTraceSchema.setUpUserCode("");
//			mLCUWSendTraceSchema.setUpUWPopedom(getUpReportPopedom(mOperator,
//					mOtherNoType));
			mLCUWSendTraceSchema.setUpUWPopedom(tTempUWPomEdom);
			mLCUWSendTraceSchema.setManageCom(tTempComCode);
			// mLCUWSendTraceSchema.setDownUWCode(mOperator);
			// mLCUWSendTraceSchema.setDownUWPopedom(getPopedom(mOperator));
		}
		// 超权限
		if (mSendType.equals("2")) {
			mLCUWSendTraceSchema.setSendFlag("1");
			// 由于不需要指定特定的核保员故在此不存核保员代码
			mLCUWSendTraceSchema.setUpUserCode("");
//			mLCUWSendTraceSchema.setUpUWPopedom(getUpReportPopedom(mOperator,
//					mOtherNoType));
			mLCUWSendTraceSchema.setUpUWPopedom(tTempUWPomEdom);
			mLCUWSendTraceSchema.setManageCom(tTempComCode);
			// mLCUWSendTraceSchema.setDownUWCode(mOperator);
			// mLCUWSendTraceSchema.setDownUWPopedom(getPopedom(mOperator));
		}
		// 返回下级
		if (mSendType.equals("4")) {

			mLCUWSendTraceSchema.setSendFlag("1");
			// 由于不需要指定特定的核保员故在此不存核保员代码
			mLCUWSendTraceSchema.setUpUserCode(tUserCode);
			mLCUWSendTraceSchema.setUpUWPopedom(tUWPopedom);
			// mLCUWSendTraceSchema.setDownUWCode(mOperator);
			// mLCUWSendTraceSchema.setDownUWPopedom(getPopedom(mOperator));
		}

		//处理工作流....
		LWMissionSet tLWMissionSet = new LWMissionSet();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		String tSQL_Temp = "select * from lwmission where activityid='0000002004' "
            			 + " and processid='0000000004' and missionprop1='"+this.mGrpContNo+"'";
		tLWMissionSet = tLWMissionDB.executeQuery(tSQL_Temp);
		if(tLWMissionSet.size()<=0)
		{
			CError.buildErr(this,"查询人工核保工作流节点失败!");
			return false;
		}
		tLWMissionSchema = tLWMissionSet.get(1);
		//处理上报
		if (mSendType.equals("1")||mSendType.equals("2")) {
			tLWMissionSchema.setMissionProp17(tTempCtrlProp);
			String tSendComTrace = "";
			tSendComTrace = tLWMissionSchema.getMissionProp18();
			if(tSendComTrace==null||tSendComTrace.equals(""))
			{
				tSendComTrace = this.mGlobalInput.ComCode+":"+ tTempComCode;
			}
			else
			{
				tSendComTrace = tSendComTrace + ":" + tTempComCode;
			}
			tLWMissionSchema.setMissionProp18(tSendComTrace);
		}
		else
		//处理返回下级
		{
			String tSendComTrace = "";
			String tTempComTrace = "";
			tSendComTrace = tLWMissionSchema.getMissionProp18();
			String tCtrlProp = "";
			if(tSendComTrace==null||tSendComTrace.equals(""))
			{
				tCtrlProp = "1";
			}
			else
			{
				if(tSendComTrace.indexOf(":")==-1)
				{
					tTempComTrace = tSendComTrace;
					tSendComTrace = "";
					tCtrlProp = "1";
				}
				else
				{
					tSendComTrace = tSendComTrace.substring(0,tSendComTrace.lastIndexOf(":"));
					tTempComTrace = tSendComTrace.substring(tSendComTrace.lastIndexOf(":")+1);
//					tTempComTrace = tSendComTrace.substring(tSendComTrace.lastIndexOf(":")+1);
					
					if(tTempComTrace==null||tTempComTrace.equals("")||tTempComTrace.length()>=4)
					{
						tCtrlProp = "1";
					}
					else 
					{
						tCtrlProp = "0";
					}
					
				}
				
				//tSendComTrace = tSendComTrace + ":" + tTempComCode;
			}
			tLWMissionSchema.setMissionProp18(tSendComTrace);
			tLWMissionSchema.setMissionProp17(tCtrlProp);
		}
		
		this.mUpdateLWMissionSchema.setSchema(tLWMissionSchema);

		
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
			//this.mErrors.copyAllErrors(tLCGCUWSubDB.mErrors);
			CError.buildErr(this,mGrpContNo + "合同核保轨迹表查失败!");
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
			//this.mErrors.copyAllErrors(tLCGCUWSubDB.mErrors);
			CError.buildErr(this,"集体险种核保轨迹表查失败!");
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
				//this.mErrors.copyAllErrors(tLCGCUWSubDB.mErrors);
				CError.buildErr(this,"集体险种核保轨迹表查失败!");
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
		tLDUWUserDB.setUWType("2"); // yaory //团险uwtype=‘2’ modify at 2008-12-17
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
		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		tLDUWUserDB.setUserCode(tUserCode);
		tLDUWUserDB.setUWType("2");//团险uwtype=‘2’ modify at 2008-12-17
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
		//tongmeng 2009-04-24 modify
		//上报不修改核保级别
		
//		mTransferData.setNameAndValue("UWAuthority", this.mLCUWSendTraceSchema
//				.getUpUWPopedom());
		mTransferData.setNameAndValue("UWAuthority", this.mUpdateLWMissionSchema.getMissionProp12());
		logger.debug("mLCUWSendTraceSchema.getUpUWPopedom():"
				+ this.mLCUWSendTraceSchema.getUpUWPopedom());
		;
		// 返回的核保员代码
		mTransferData.setNameAndValue("UserCode", "");
		mTransferData.setNameAndValue("UWUpReport", "2");
		logger.debug("mLCUWSendTraceSchema.getUpUserCode():"
				+ mLCUWSendTraceSchema.getUpUserCode());
		mTransferData.setNameAndValue("CtrlProp", this.mUpdateLWMissionSchema.getMissionProp17());
		mTransferData.setNameAndValue("SendComTrace", this.mUpdateLWMissionSchema.getMissionProp18());

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
	/**
	 * 获得上报的权限和属性
	 * @return  xx:xx:xx  权限:控制属性:上报到机构
	 */
	private String getUpReportAuthAndProp()
	{
		String tRes = "";
		String tResPopEdom = "";
		String tResProp = "";
		String tResComCode = "";
		String tUWUser = this.mGlobalInput.Operator;//当前核保师
		String tUWComCode = this.mGlobalInput.ComCode;//当前核保师登陆机构
		String tGrpContUWAuth = "";//当前保单的核保权限
		//String mGrpContNo
		String tContManageCom = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL_Auth = "select nvl(MissionProp12,''),MissionProp4,nvl(MissionProp17,'0') from lwmission where activityid='0000002004' "
			             + " and processid='0000000004' and missionprop1='"+this.mGrpContNo+"'";
		SSRS tSSRS_Auth = new SSRS();
		tSSRS_Auth = tExeSQL.execSQL(tSQL_Auth);
		if(tSSRS_Auth.getMaxRow()<=0)
		{
			CError.buildErr(this,"该团单没有处于人工核保节点!");
			return null;
		}
		else
		{
			tGrpContUWAuth = tSSRS_Auth.GetText(1,1);
			tContManageCom = tSSRS_Auth.GetText(1,2);
		}
		//tGrpContUWAuth = tExeSQL.getOneValue(tSQL_Auth);
		if(tGrpContUWAuth==null||tGrpContUWAuth.equals(""))
		{
			tGrpContUWAuth = "B1";
		}
		if(tContManageCom==null||tContManageCom.equals("")||tContManageCom.length()!=8)
		{
			CError.buildErr(this,"团单管理机构错误!");
			return null;
		}
		String tSQL_Prop = "";
		//如果登陆机构是总公司的话,只能查询出来上报的保单,所以,只需要判断当前核保师的上级级别即可.
		SSRS tSSRS_Final = new SSRS();
		if(tUWComCode!=null&&tUWComCode.trim().length()==2)
		{
			tSQL_Prop = "select upuwpopedom,'0' from lduwuser where uwtype='2' and usercode='"+tUWUser+"' ";
			tSSRS_Final = tExeSQL.execSQL(tSQL_Prop);
			if(tSSRS_Final.getMaxRow()>0)
			{
				tResPopEdom = tSSRS_Final.GetText(1,1);
				tResProp = tSSRS_Final.GetText(1,2);
				tResComCode = "86";
			}
			
		}
		else
		{
			//先判断分公司内是否有级别够的核保师,如果有的话,不上报到总公司,没有的话,上报到总公司.
			tSQL_Prop = "select nvl(max(upuwpopedom),''),'1' from lduwuser where usercode in " 
				      + " ( "
				      + " select usercode from lduser where comcode like "
				      + "(select comcode from lduser where usercode='"+tUWComCode+"')||'%' "
				      + " and comcode like '"+tContManageCom.substring(0,4)+"%') and uwtype='2' and uwpopedom is not null and uwpopedom>='"+tGrpContUWAuth+"' "
				      + " and usercode<>'"+tUWComCode+"' ";
			tSSRS_Final = tExeSQL.execSQL(tSQL_Prop);
			if(tSSRS_Final.getMaxRow()>0)
			{
				tResPopEdom = tSSRS_Final.GetText(1,1);
				tResProp = tSSRS_Final.GetText(1,2);
				tResComCode = tUWComCode;
			}
			if(tResPopEdom==null||tResPopEdom.equals(""))
			{
				//分公司没有的话,查询总公司权限
				tSQL_Prop = "select upuwpopedom,'0' from lduwuser where uwtype='2' and usercode='"+tUWUser+"' ";
				tSSRS_Final = tExeSQL.execSQL(tSQL_Prop);
				if(tSSRS_Final.getMaxRow()>0)
				{
					tResPopEdom = tSSRS_Final.GetText(1,1);
					tResProp = tSSRS_Final.GetText(1,2);
					tResComCode = "86";
				}
			}
		}
		// xx:xx:xx  权限:控制属性:上报到机构
		tRes = tResPopEdom+":"+tResProp+":"+tResComCode;
		
		return tRes;
	}
}
