package com.sinosoft.workflow.ask;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.AskUWConfirmBL;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 工作流服务类模版
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author heyq
 * @version 1.0
 */

public class AskUWConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(AskUWConfirmAfterInitService.class);

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
	private LCGCUWMasterSchema mLCGCUWMasterSchema = new LCGCUWMasterSchema();

	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mGrpContNo;
	private String mUWFlag;

	public AskUWConfirmAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		mInputData = (VData) cInputData.clone();
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.add(mMap);

		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		ExeSQL tExeSQL = new ExeSQL();
		String sSql = "select uwpopedom from lduwuser where uwtype='2' and usercode='"
				+ "?mOperater?" + "' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sSql);
		sqlbv1.put("mOperater",mOperater);
		String tUwpopedom = tExeSQL.getOneValue(sqlbv1);
		if (tUwpopedom == null || tUwpopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "核保师级别查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 如果正常，校验核保师权限
		if ("9".equals(mUWFlag)) {
			// 查询该任务核保级别
			String tSql = " select missionprop12 from lwmission where 1 =1 "
					+ " and activityid = '0000006004' and missionid = '"
					+ "?mMissionID?" + "'" + " and submissionid = '"
					+ "?mSubMissionID?" + "'";
			logger.debug("tSql:" + tSql);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("mMissionID",mMissionID);
			sqlbv2.put("mSubMissionID",mSubMissionID);
			String tUWAuthority = tExeSQL.getOneValue(sqlbv2);
			if (tUWAuthority == null || tUWAuthority.equals("")) {
				CError tError = new CError();
				tError.moduleName = "UWRReportAfterInitService";
				tError.functionName = "prepareTransferData";
				tError.errorMessage = "核保任务级别查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			if (tUwpopedom.trim().compareTo(tUWAuthority.trim()) < 0) {
				CError tError = new CError();
				tError.moduleName = "GrpUWManuNormChkBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "核保员级别不够，请录入核保意见，申请待上级核保。";
				this.mErrors.addOneError(tError);
				return false;
			}
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
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmAfterInitService";
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
			tError.moduleName = "AskUWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;
		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmAfterInitService";
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
			tError.moduleName = "AskUWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当集体合同号
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		if (mGrpContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中GrpContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 得到核保结论标志
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		if (mUWFlag == null) {
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中UWFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCGCUWMasterSchema.setSchema((LCGCUWMasterSchema) cInputData
				.getObjectByObjectName("LCGCUWMasterSchema", 0));
		if (mLCGCUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据LCGCUWMasterSchema失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 根据mGrpContNo查询信息
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "查询集体合同信息失败，请确认是否录入正确!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();

		if (mLCGCUWMasterSchema.getPassFlag().equals("1")) {
			mUWFlag = "5"; // 工作流流转标志
		} else if (mLCGCUWMasterSchema.getPassFlag().equals("4")
				|| mLCGCUWMasterSchema.getPassFlag().equals("9")) {
			mUWFlag = "9";
		}

		AskUWConfirmBL tAskUWConfirmBL = new AskUWConfirmBL();
		boolean tResult = tAskUWConfirmBL.submitData(mInputData, "");
		if (tResult) {
			mMap = (MMap) tAskUWConfirmBL.getResult().getObjectByObjectName(
					"MMap", 0);
		} else {
			this.mErrors.copyAllErrors(tAskUWConfirmBL.getErrors());
		}

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		//
		// 查询该任务核保级别
		String tSql = " select missionprop12 from lwmission where 1 =1 "
				+ " and activityid = '0000006004' and missionid = '"
				+ "?mMissionID?" + "'" + " and submissionid = '" + "?mSubMissionID?"
				+ "'";
		logger.debug("tSql:" + tSql);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("mMissionID",mMissionID);
		sqlbv3.put("mSubMissionID",mSubMissionID);
		ExeSQL tExeSQL = new ExeSQL();
		String tUWAuthority = tExeSQL.getOneValue(sqlbv3);
		if (tUWAuthority == null || tUWAuthority.equals("")) {
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "核保任务级别查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mTransferData.setNameAndValue("GrpContNo", mLCGrpContSchema
				.getGrpContNo());
		mTransferData.setNameAndValue("PrtNo", mLCGrpContSchema.getPrtNo());
		mTransferData.setNameAndValue("SaleChnl", mLCGrpContSchema
				.getSaleChnl());
		mTransferData.setNameAndValue("ManageCom", mLCGrpContSchema
				.getManageCom());
		mTransferData.setNameAndValue("AgentCode", mLCGrpContSchema
				.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", mLCGrpContSchema
				.getAgentGroup());
		mTransferData.setNameAndValue("GrpName", mLCGrpContSchema.getGrpName());
		mTransferData.setNameAndValue("GrpNo", mLCGrpContSchema.getAppntNo());
		mTransferData.setNameAndValue("CValiDate", mLCGrpContSchema
				.getCValiDate());
		mTransferData.setNameAndValue("UWAuthority", tUWAuthority);
		if (mUWFlag != null && mUWFlag.equals("6"))// 超权限上报
		{
			mTransferData.setNameAndValue("ReportType", "2");
		} else {
			mTransferData.setNameAndValue("ReportType", "0");
		}
		mTransferData.setNameAndValue("UWFlag", mUWFlag);

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
