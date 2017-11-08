package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1printgrp.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.vschema.LBSpecSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
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
 * @author zhangxing
 * @version 1.0
 */

public class UWSpecBL {
private static Logger logger = Logger.getLogger(UWSpecBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// private VData mIputData = new VData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	/** 业务数据操作字符串 */
	private String mPolNo;
	private String mContNo;
	private String mPrtNo;
	private String mInsuredNo;
	private String mSpecReason;
	private String mRemark;
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	/** 保单表 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	/** 核保主表 */
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
	/** 特约表 */
	private LCSpecSchema mLCSpecSchema = new LCSpecSchema();
	private LCSpecSet mLCSpecSet = new LCSpecSet();
	private LBSpecSet mLBSpecSet = new LBSpecSet();

	public UWSpecBL() {
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

		// 进行业务处理
		if (!dealData()) {

			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保特约信息
		if (prepareSpec() == false) {
			return false;
		}
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验保单信息
		LCPolDB tLCPolDB = new LCPolDB();
		logger.debug("mPolNo check " + mPolNo);
		tLCPolDB.setPolNo(mPolNo);
		if (!tLCPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PRnewSpecAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mPolNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCPolSchema.setSchema(tLCPolDB);
		mPrtNo = mLCPolSchema.getPrtNo();

		// // 处于未打印状态的核保通知书在打印队列中只能有一个
		// // 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_UW); // 核保通知书
		tLOPRTManagerDB.setOtherNo(mContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 保单号
		tLOPRTManagerDB.setStandbyFlag2(mLCPolSchema.getPrtNo());
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PRnewUWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PRnewUWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中已有一个处于未打印状态的核保通知书!";
			this.mErrors.addOneError(tError);
			return false;
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
		mLCSpecSchema = (LCSpecSchema) cInputData.getObjectByObjectName(
				"LCSpecSchema", 0);
		mLCUWMasterSchema = (LCUWMasterSchema) cInputData
				.getObjectByObjectName("LCUWMasterSchema", 0);
		mPolNo = (String) cInputData.getObjectByObjectName("String", 0);
		mInputData = cInputData;
		logger.debug("mPolNo" + mPolNo);
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PRnewUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLCUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PRnewUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PRnewUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PRnewUWSpecAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;

		return true;

	}

	/**
	 * 准备特约资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareSpec() {

		// 准备续保核保主表信息
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setProposalNo(mPolNo);
		if (tLCUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PRnewUWSpecAfterInitService";
			tError.functionName = "prepareSpec";
			tError.errorMessage = "无续保批单核保主表信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		String tSpecReason = mLCUWMasterSchema.getSpecReason();
		mLCUWMasterSchema.setSchema(tLCUWMasterDB);
		if (tSpecReason != null && !tSpecReason.trim().equals("")) {
			mLCUWMasterSchema.setSpecReason(tSpecReason);
		} else {
			mLCUWMasterSchema.setSpecReason("");
		}

		if (mLCSpecSchema != null) {
			mLCUWMasterSchema.setSpecFlag("1"); // 特约标识
		} else {
			mLCUWMasterSchema.setSpecFlag("0"); // 特约标识
		}

		mLCUWMasterSchema.setSpecReason(mLCUWMasterSchema.getSpecReason());
		mLCUWMasterSchema.setOperator(mOperater);
		mLCUWMasterSchema.setManageCom(mManageCom);
		mLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		// 准备特约的数据
		if (mLCSpecSchema != null) {
			mLCSpecSchema.setSerialNo(PubFun1.CreateMaxNo("SpecCode", PubFun
					.getNoLimit(mGlobalInput.ComCode)));
			mLCSpecSchema.setGrpContNo(mLCUWMasterSchema.getGrpContNo());
			mLCSpecSchema.setPolNo(mPolNo);
			mLCSpecSchema.setProposalNo(mLCUWMasterSchema.getProposalNo());
			mLCSpecSchema.setPrtFlag("1");
			mLCSpecSchema.setBackupType("");
			mLCSpecSchema.setSpecContent(mLCSpecSchema.getSpecContent());
			mLCSpecSchema.setOperator(mOperater);
			mLCSpecSchema.setMakeDate(mCurrentDate);
			mLCSpecSchema.setMakeTime(mCurrentTime);
			mLCSpecSchema.setModifyDate(mCurrentDate);
			mLCSpecSchema.setModifyTime(mCurrentTime);
		}
		String zx = mLCSpecSchema.getPolNo();
		logger.debug("mpolNo aaa  " + zx);
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加本次续保特约数据
		if (mLCSpecSchema != null) {
			map.put(mLCSpecSchema, "INSERT");
		}

		// 添加续保批单核保主表通知书打印管理表数据
		map.put(mLCUWMasterSchema, "UPDATE");

		mResult.add(map);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
