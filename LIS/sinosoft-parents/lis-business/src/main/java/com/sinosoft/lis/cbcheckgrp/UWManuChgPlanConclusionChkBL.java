package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1printgrp.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 承保计划变更结论录入
 * </p>
 * <p>
 * Description: 承保计划变更结论录入
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZhangRong
 * @version 1.0
 */
public class UWManuChgPlanConclusionChkBL {
private static Logger logger = Logger.getLogger(UWManuChgPlanConclusionChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 输入数据的容器 */
	private VData mInputData;

	/** 提交数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mContNo = "";
	private String mChangePolReason = "";

	/** 核保表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = null;
	private LCCUWSubSchema mLCCUWSubSchema = null;
	private GlobalInput mGlobalInput = new GlobalInput();

	LOPRTManagerSchema mLOPrtManagerSchema = new LOPRTManagerSchema();
	UWPrintSendBL mUWPrintSendBL = new UWPrintSendBL();
	/** 打印处理函数* */
	private PrintManagerBL tPrintManagerBL = new PrintManagerBL();

	public UWManuChgPlanConclusionChkBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中，此类做数据提交使用
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		logger.debug("---UWManuChgPlanConclusionChkBL calling getInputData---");

		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("---UWManuChgPlanConclusionChkBL dealData---");

		// 数据操作业务处理
		if (!dealData()) {
			CError tError = new CError();
			tError.moduleName = "UWManuChgPlanConclusionChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---UWManuChgPlanConclusionChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---UWManuChgPlanConclusionChkBL prepareOutputData---");

		// 数据提交
		mResult.add(mMap);

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuChgPlanConclusionChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---UWManuChgPlanConclusionChkBL commitData---");

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		logger.debug(mGlobalInput.ComCode);

		mLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
				"LCContSet", 0));

		if ((mLCContSet != null) && (mLCContSet.size() > 0)) {
			mLCContSchema = mLCContSet.get(1);
			mContNo = mLCContSchema.getContNo();
			mChangePolReason = mLCContSchema.getRemark();

			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mContNo);

			LCContSet tLCContSet = tLCContDB.query();

			if (tLCContSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "UWManuChgPlanConclusionChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "在合同表中无法查到合同号为" + mContNo + " 的合同信息!";
				this.mErrors.addOneError(tError);

				return false;
			}

			mLCContSchema.setSchema(tLCContSet.get(1));
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuChgPlanConclusionChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入合同信息!";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		if (prepareUW() == false) {
			return false;
		}

		mLOPrtManagerSchema = mUWPrintSendBL.preparePrint(mContNo,
				tPrintManagerBL.CODE_UWCHANG, mGlobalInput);

		return true;
	}

	private boolean prepareUW() {
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);

		LCCUWMasterSet tLCCUWMasterSet = tLCCUWMasterDB.query();

		if ((tLCCUWMasterSet == null) || (tLCCUWMasterSet.size() <= 0)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuChgPlanConclusionChkBL";
			tError.functionName = "dealData";
			tError.errorMessage = "在合同核保主表中无法查到合同号为" + mContNo + " 的核保信息!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCCUWMasterSchema = tLCCUWMasterSet.get(1);
		mLCCUWMasterSchema.setChangePolFlag("1");
		mLCCUWMasterSchema.setChangePolReason(mChangePolReason);

		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);

		LCCUWSubSet tLCCUWSubSet = tLCCUWSubDB.query();
		int nUWNo;

		if ((tLCCUWSubSet == null) || (tLCCUWSubSet.size() <= 0)) {
			nUWNo = 1;
		} else {
			nUWNo = tLCCUWSubSet.size() + 1;
		}

		mLCCUWSubSchema = new LCCUWSubSchema();
		mLCCUWSubSchema.setContNo(mLCCUWMasterSchema.getContNo());
		mLCCUWSubSchema.setGrpContNo(mLCCUWMasterSchema.getGrpContNo());
		mLCCUWSubSchema.setProposalContNo(mLCCUWMasterSchema
				.getProposalContNo());
		mLCCUWSubSchema.setUWNo(nUWNo); // 第几次核保
		mLCCUWSubSchema.setInsuredNo(mLCCUWMasterSchema.getInsuredNo());
		mLCCUWSubSchema.setInsuredName(mLCCUWMasterSchema.getInsuredName());
		mLCCUWSubSchema.setAppntNo(mLCCUWMasterSchema.getAppntNo());
		mLCCUWSubSchema.setAppntName(mLCCUWMasterSchema.getAppntName());
		mLCCUWSubSchema.setAgentCode(mLCCUWMasterSchema.getAgentCode());
		mLCCUWSubSchema.setAgentGroup(mLCCUWMasterSchema.getAgentGroup());
		mLCCUWSubSchema.setUWGrade(mLCCUWMasterSchema.getUWGrade()); // 核保级别
		mLCCUWSubSchema.setAppGrade(mLCCUWMasterSchema.getAppGrade()); // 申请级别
		mLCCUWSubSchema.setAutoUWFlag(mLCCUWMasterSchema.getAutoUWFlag());
		mLCCUWSubSchema.setState(mLCCUWMasterSchema.getState());
		mLCCUWSubSchema.setPassFlag(mLCCUWMasterSchema.getState());
		mLCCUWSubSchema.setPostponeDay(mLCCUWMasterSchema.getPostponeDay());
		mLCCUWSubSchema.setPostponeDate(mLCCUWMasterSchema.getPostponeDate());
		mLCCUWSubSchema.setUpReportContent(mLCCUWMasterSchema
				.getUpReportContent());
		mLCCUWSubSchema.setHealthFlag(mLCCUWMasterSchema.getHealthFlag());
		mLCCUWSubSchema.setSpecFlag(mLCCUWMasterSchema.getSpecFlag());
		mLCCUWSubSchema.setSpecReason(mLCCUWMasterSchema.getSpecReason());
		mLCCUWSubSchema.setQuesFlag(mLCCUWMasterSchema.getQuesFlag());
		mLCCUWSubSchema.setReportFlag(mLCCUWMasterSchema.getReportFlag());
		mLCCUWSubSchema.setChangePolFlag(mLCCUWMasterSchema.getChangePolFlag());
		mLCCUWSubSchema.setChangePolReason(mLCCUWMasterSchema
				.getChangePolReason());
		mLCCUWSubSchema.setAddPremReason(mLCCUWMasterSchema.getAddPremReason());
		mLCCUWSubSchema.setPrintFlag(mLCCUWMasterSchema.getPrintFlag());
		mLCCUWSubSchema.setPrintFlag2(mLCCUWMasterSchema.getPrintFlag2());
		mLCCUWSubSchema.setUWIdea(mLCCUWMasterSchema.getUWIdea());
		mLCCUWSubSchema.setOperator(mLCCUWMasterSchema.getOperator()); // 操作员
		mLCCUWSubSchema.setManageCom(mLCCUWMasterSchema.getManageCom());
		mLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		mLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		mLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		mLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mLCCUWMasterSchema, "UPDATE");
		mMap.put(mLCCUWSubSchema, "INSERT");
		// mMap.put(mLOPrtManagerSchema,"INSERT");
	}
}
