package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIndUWMasterDB;
import com.sinosoft.lis.db.LCIndUWSubDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIndUWMasterSchema;
import com.sinosoft.lis.schema.LCIndUWSubSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.vschema.LCIndUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 被保人核保
 * </p>
 * <p>
 * Description: 在人工核保时为被保人下核保结论
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

public class LCInsuredUWBL {
private static Logger logger = Logger.getLogger(LCInsuredUWBL.class);

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

	/** 业务处理类 */
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	private LCIndUWMasterSchema mLCIndUWMasterSchema = new LCIndUWMasterSchema();
	private LCIndUWMasterSchema mLCIndUWMasterSchemaNew = new LCIndUWMasterSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCIndUWSubSchema mLCIndUWSubSchema = new LCIndUWSubSchema();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;

	/** 业务数据 */
	private String mContNo;
	private String mInsuredNo;

	public LCInsuredUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

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
				tError.moduleName = "LCInsuredUWBL";
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

		map.put(mLCIndUWMasterSchemaNew, "DELETE&INSERT");
		map.put(mLCIndUWSubSchema, "INSERT");
		map.put(mLCInsuredSchema, "UPDATE");

		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "checkData";
			tError.errorMessage = "查询合同信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mContNo);
		tLCInsuredDB.setInsuredNo(mInsuredNo);
		if (!tLCInsuredDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "checkData";
			tError.errorMessage = "查询被保人信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCInsuredSchema = tLCInsuredDB.getSchema();

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
			tError.moduleName = "LCInsuredBL";
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
			tError.moduleName = "LCInsuredBL";
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
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前ContNo
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前InsuredNo
		mInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
		if (mInsuredNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中InsuredNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得核保结论
		mLCIndUWMasterSchema = (LCIndUWMasterSchema) mTransferData
				.getValueByName("LCIndUWMasterSchema");
		if (mLCIndUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中LCIndUWMasterSchema失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (!prepareInsured()) {
			return false;
		}

		if (!prepareIndUW()) {
			return false;
		}

		return true;
	}

	/**
	 * prepareIndUW
	 * 
	 * @return boolean
	 */
	private boolean prepareIndUW() {
		LCIndUWMasterDB tLCIndUWMasterDB = new LCIndUWMasterDB();
		tLCIndUWMasterDB.setContNo(mContNo);
		tLCIndUWMasterDB.setInsuredNo(mInsuredNo);

		if (tLCIndUWMasterDB.getInfo()) {
			// 准备被保人核保主表数据
			mLCIndUWMasterSchemaNew = tLCIndUWMasterDB.getSchema();
			int uwno = mLCIndUWMasterSchemaNew.getUWNo();
			mLCIndUWMasterSchemaNew.setPassFlag(mLCIndUWMasterSchema
					.getPassFlag());
			mLCIndUWMasterSchemaNew.setUWIdea(mLCIndUWMasterSchema.getUWIdea());
			mLCIndUWMasterSchemaNew.setUWNo(uwno + 1);
			mLCIndUWMasterSchemaNew.setSugPassFlag(mLCIndUWMasterSchema
					.getSugPassFlag());
			mLCIndUWMasterSchemaNew.setSugUWIdea(mLCIndUWMasterSchema
					.getSugUWIdea());

			// 准备被保人核保子表数据
			LCIndUWSubDB tLCIndUWSubDB = new LCIndUWSubDB();
			tLCIndUWSubDB.setContNo(mContNo);
			LCIndUWSubSet tLCIndUWSubSet = new LCIndUWSubSet();

			tLCIndUWSubSet = tLCIndUWSubDB.query();
			mLCIndUWSubSchema = tLCIndUWSubSet.get(1);
			mLCIndUWSubSchema.setPassFlag(mLCIndUWMasterSchema.getPassFlag());
			mLCIndUWSubSchema.setUWIdea(mLCIndUWMasterSchema.getUWIdea());
			mLCIndUWSubSchema.setSugPassFlag(mLCIndUWMasterSchema
					.getSugPassFlag());
			mLCIndUWSubSchema.setSugUWIdea(mLCIndUWMasterSchema.getSugUWIdea());
		} else {
			// 准备被保人核保主表数据
			mLCIndUWMasterSchemaNew.setContNo(mContNo);
			mLCIndUWMasterSchemaNew.setGrpContNo(mLCInsuredSchema
					.getGrpContNo());
			mLCIndUWMasterSchemaNew.setProposalContNo(mLCInsuredSchema
					.getContNo());
			mLCIndUWMasterSchemaNew.setUWNo(1);
			mLCIndUWMasterSchemaNew.setInsuredNo(mLCInsuredSchema
					.getInsuredNo());
			mLCIndUWMasterSchemaNew.setInsuredName(mLCInsuredSchema.getName());
			mLCIndUWMasterSchemaNew.setAppntNo(mLCInsuredSchema.getAppntNo());
			mLCIndUWMasterSchemaNew.setAppntName(mLCContSchema.getAppntName());
			mLCIndUWMasterSchemaNew.setAgentCode(mLCContSchema.getAgentCode());
			mLCIndUWMasterSchemaNew
					.setAgentGroup(mLCContSchema.getAgentGroup());
			mLCIndUWMasterSchemaNew.setUWGrade(""); // 核保级别
			mLCIndUWMasterSchemaNew.setAppGrade(""); // 申报级别
			mLCIndUWMasterSchemaNew.setPostponeDay("");
			mLCIndUWMasterSchemaNew.setPostponeDate("");
			mLCIndUWMasterSchemaNew.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			mLCIndUWMasterSchemaNew
					.setState(mLCIndUWMasterSchema.getPassFlag());
			mLCIndUWMasterSchemaNew.setPassFlag(mLCIndUWMasterSchema
					.getPassFlag());
			mLCIndUWMasterSchemaNew.setHealthFlag("0");
			mLCIndUWMasterSchemaNew.setSpecFlag("0");
			mLCIndUWMasterSchemaNew.setQuesFlag("0");
			mLCIndUWMasterSchemaNew.setReportFlag("0");
			mLCIndUWMasterSchemaNew.setChangePolFlag("0");
			mLCIndUWMasterSchemaNew.setPrintFlag("0");
			mLCIndUWMasterSchemaNew.setPrintFlag2("0");
			mLCIndUWMasterSchemaNew.setManageCom(mLCInsuredSchema
					.getManageCom());
			mLCIndUWMasterSchemaNew.setUWIdea(mLCIndUWMasterSchema.getUWIdea());
			mLCIndUWMasterSchemaNew.setUpReportContent("");
			mLCIndUWMasterSchemaNew.setOperator(mOperator); // 操作员
			mLCIndUWMasterSchemaNew.setMakeDate(PubFun.getCurrentDate());
			mLCIndUWMasterSchemaNew.setMakeTime(PubFun.getCurrentTime());
			mLCIndUWMasterSchemaNew.setModifyDate(PubFun.getCurrentDate());
			mLCIndUWMasterSchemaNew.setModifyTime(PubFun.getCurrentTime());
			mLCIndUWMasterSchemaNew.setSugPassFlag(mLCIndUWMasterSchema
					.getSugPassFlag());
			mLCIndUWMasterSchemaNew.setSugUWIdea(mLCIndUWMasterSchema
					.getSugUWIdea());

			// 准备被保人核保子表数据
			mLCIndUWSubSchema.setContNo(mContNo);
			mLCIndUWSubSchema.setGrpContNo(mLCInsuredSchema.getGrpContNo());
			mLCIndUWSubSchema.setProposalContNo(mLCInsuredSchema.getContNo());
			mLCIndUWSubSchema.setUWNo(1);
			mLCIndUWSubSchema.setInsuredNo(mLCInsuredSchema.getInsuredNo());
			mLCIndUWSubSchema.setInsuredName(mLCInsuredSchema.getName());
			mLCIndUWSubSchema.setAppntNo(mLCInsuredSchema.getAppntNo());
			mLCIndUWSubSchema.setAppntName(mLCContSchema.getAppntName());
			mLCIndUWSubSchema.setAgentCode(mLCContSchema.getAgentCode());
			mLCIndUWSubSchema.setAgentGroup(mLCContSchema.getAgentGroup());
			mLCIndUWSubSchema.setUWGrade(""); // 核保级别
			mLCIndUWSubSchema.setAppGrade(""); // 申报级别
			mLCIndUWSubSchema.setPostponeDay("");
			mLCIndUWSubSchema.setPostponeDate("");
			mLCIndUWSubSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			mLCIndUWSubSchema.setState(mLCIndUWMasterSchema.getPassFlag());
			mLCIndUWSubSchema.setPassFlag(mLCIndUWMasterSchema.getPassFlag());
			mLCIndUWSubSchema.setHealthFlag("0");
			mLCIndUWSubSchema.setSpecFlag("0");
			mLCIndUWSubSchema.setQuesFlag("0");
			mLCIndUWSubSchema.setReportFlag("0");
			mLCIndUWSubSchema.setChangePolFlag("0");
			mLCIndUWSubSchema.setPrintFlag("0");
			mLCIndUWSubSchema.setPrintFlag2("0");
			mLCIndUWSubSchema.setManageCom(mLCInsuredSchema.getManageCom());
			mLCIndUWSubSchema.setUWIdea(mLCIndUWMasterSchema.getUWIdea());
			mLCIndUWSubSchema.setUpReportContent("");
			mLCIndUWSubSchema.setOperator(mOperator); // 操作员
			mLCIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			mLCIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			mLCIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			mLCIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			mLCIndUWSubSchema.setSugPassFlag(mLCIndUWMasterSchema
					.getSugPassFlag());
			mLCIndUWSubSchema.setSugUWIdea(mLCIndUWMasterSchema.getSugUWIdea());
		}
		return true;
	}

	/**
	 * prepareInsured
	 * 
	 * @return boolean
	 */
	private boolean prepareInsured() {
		mLCInsuredSchema.setUWFlag(mLCIndUWMasterSchema.getPassFlag());
		mLCInsuredSchema.setUWCode(mOperator);
		mLCInsuredSchema.setUWDate(PubFun.getCurrentDate());
		mLCInsuredSchema.setUWTime(PubFun.getCurrentTime());

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
