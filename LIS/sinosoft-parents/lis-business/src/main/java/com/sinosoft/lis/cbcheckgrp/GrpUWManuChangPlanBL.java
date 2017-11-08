package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCGCUWSubDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.f1printgrp.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCGCUWSubSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCGCUWMasterSet;
import com.sinosoft.lis.vschema.LCGCUWSubSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
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
public class GrpUWManuChangPlanBL {
private static Logger logger = Logger.getLogger(GrpUWManuChangPlanBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 输入数据的容器 */
	private VData mInputData;

	/** 提交数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;

	// 全局变量
	GlobalInput mGlobalInput = new GlobalInput();

	/** 业务处理相关变量 */
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String mGrpContNo = "";
	private String mChangePolReason = "";

	/** 核保表 */
	private LCGCUWMasterSchema mLCGCUWMasterSchema = null;
	private LCGCUWSubSchema mLCGCUWSubSchema = null;
	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public GrpUWManuChangPlanBL() {
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

		logger.debug("---GrpUWManuChangPlanBL dealData---");

		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---GrpUWManuChangPlanBL prepareOutputData---");

		// 数据提交

		// PubSubmit tSubmit = new PubSubmit();
		//
		// if (!tSubmit.submitData(mResult, ""))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tSubmit.mErrors);
		//
		// CError tError = new CError();
		// tError.moduleName = "GrpUWManuChangPlanBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		//
		// return false;
		// }

		logger.debug("---GrpUWManuChangPlanBL commitData---");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mLCGrpContSet.set((LCGrpContSet) cInputData.getObjectByObjectName(
				"LCGrpContSet", 0));

		if ((mLCGrpContSet != null) && (mLCGrpContSet.size() > 0)) {
			mLCGrpContSchema = mLCGrpContSet.get(1);
			mGrpContNo = mLCGrpContSchema.getGrpContNo();
			mChangePolReason = mLCGrpContSchema.getRemark();

			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(mGrpContNo);

			LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();

			if (tLCGrpContSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpUWManuChangPlanBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "在合同表中无法查到合同号为" + mGrpContNo + " 的合同信息!";
				this.mErrors.addOneError(tError);

				return false;
			}

			mLCGrpContSchema.setSchema(tLCGrpContSet.get(1));
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpUWManuChangPlanBL";
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
		if (peparePrint() == false)
			return false;

		LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
		tLCGCUWMasterDB.setGrpContNo(mGrpContNo);

		LCGCUWMasterSet tLCGCUWMasterSet = tLCGCUWMasterDB.query();

		if ((tLCGCUWMasterSet == null) || (tLCGCUWMasterSet.size() <= 0)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpUWManuChangPlanBL";
			tError.functionName = "dealData";
			tError.errorMessage = "在合同核保主表中无法查到合同号为" + mGrpContNo + " 的核保信息!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCGCUWMasterSchema = tLCGCUWMasterSet.get(1);
		mLCGCUWMasterSchema.setChangePolFlag("1");
		mLCGCUWMasterSchema.setChangePolReason(mChangePolReason);

		LCGCUWSubDB tLCGCUWSubDB = new LCGCUWSubDB();
		tLCGCUWSubDB.setGrpContNo(mGrpContNo);

		LCGCUWSubSet tLCGCUWSubSet = tLCGCUWSubDB.query();
		int nUWNo;

		if ((tLCGCUWSubSet == null) || (tLCGCUWSubSet.size() <= 0)) {
			nUWNo = 1;
		} else {
			nUWNo = tLCGCUWSubSet.size() + 1;
		}

		mLCGCUWSubSchema = new LCGCUWSubSchema();
		mLCGCUWSubSchema.setGrpContNo(mLCGCUWMasterSchema.getGrpContNo());
		mLCGCUWSubSchema.setGrpContNo(mLCGCUWMasterSchema.getGrpContNo());
		mLCGCUWSubSchema.setProposalGrpContNo(mLCGCUWMasterSchema
				.getProposalGrpContNo());
		mLCGCUWSubSchema.setUWNo(nUWNo); // 第几次核保
		mLCGCUWSubSchema.setInsuredNo(mLCGCUWMasterSchema.getInsuredNo());
		mLCGCUWSubSchema.setInsuredName(mLCGCUWMasterSchema.getInsuredName());
		mLCGCUWSubSchema.setAppntNo(mLCGCUWMasterSchema.getAppntNo());
		mLCGCUWSubSchema.setAppntName(mLCGCUWMasterSchema.getAppntName());
		mLCGCUWSubSchema.setAgentCode(mLCGCUWMasterSchema.getAgentCode());
		mLCGCUWSubSchema.setAgentGroup(mLCGCUWMasterSchema.getAgentGroup());
		mLCGCUWSubSchema.setUWGrade(mLCGCUWMasterSchema.getUWGrade()); // 核保级别
		mLCGCUWSubSchema.setAppGrade(mLCGCUWMasterSchema.getAppGrade()); // 申请级别
		mLCGCUWSubSchema.setAutoUWFlag(mLCGCUWMasterSchema.getAutoUWFlag());
		mLCGCUWSubSchema.setState(mLCGCUWMasterSchema.getState());
		mLCGCUWSubSchema.setPassFlag(mLCGCUWMasterSchema.getState());
		mLCGCUWSubSchema.setPostponeDay(mLCGCUWMasterSchema.getPostponeDay());
		mLCGCUWSubSchema.setPostponeDate(mLCGCUWMasterSchema.getPostponeDate());
		mLCGCUWSubSchema.setUpReportContent(mLCGCUWMasterSchema
				.getUpReportContent());
		mLCGCUWSubSchema.setHealthFlag(mLCGCUWMasterSchema.getHealthFlag());
		mLCGCUWSubSchema.setSpecFlag(mLCGCUWMasterSchema.getSpecFlag());
		mLCGCUWSubSchema.setSpecReason(mLCGCUWMasterSchema.getSpecReason());
		mLCGCUWSubSchema.setQuesFlag(mLCGCUWMasterSchema.getQuesFlag());
		mLCGCUWSubSchema.setReportFlag(mLCGCUWMasterSchema.getReportFlag());
		mLCGCUWSubSchema.setChangePolFlag(mLCGCUWMasterSchema
				.getChangePolFlag());
		mLCGCUWSubSchema.setChangePolReason(mLCGCUWMasterSchema
				.getChangePolReason());
		mLCGCUWSubSchema.setAddPremReason(mLCGCUWMasterSchema
				.getAddPremReason());
		mLCGCUWSubSchema.setPrintFlag(mLCGCUWMasterSchema.getPrintFlag());
		mLCGCUWSubSchema.setPrintFlag2(mLCGCUWMasterSchema.getPrintFlag2());
		mLCGCUWSubSchema.setUWIdea(mLCGCUWMasterSchema.getUWIdea());
		mLCGCUWSubSchema.setOperator(mLCGCUWMasterSchema.getOperator()); // 操作员
		mLCGCUWSubSchema.setManageCom(mLCGCUWMasterSchema.getManageCom());
		mLCGCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		mLCGCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		mLCGCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		mLCGCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	private boolean peparePrint() {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("URGEInterval");

		if (tLDSysVarDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "prepareURGE";
			tError.errorMessage = "没有描述催发间隔!";
			this.mErrors.addOneError(tError);
			return false;
		}
		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		logger.debug(tInterval);

		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);
		logger.debug(tDate); // 取预计催办日期

		// 准备打印管理表数据

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
		mLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		mLOPRTManagerSchema.setOtherNo(mGrpContNo);

		mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL); // 团体保单号
		mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_UWREQUIRE); // 团体核保要求通知书
		mLOPRTManagerSchema.setManageCom(mLCGrpContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		mLOPRTManagerSchema.setForMakeDate(tDate);
		return true;

	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mLCGCUWMasterSchema, "UPDATE");
		mMap.put(mLCGCUWSubSchema, "INSERT");
		mMap.put(mLOPRTManagerSchema, "INSERT");
		mResult.add(mMap);
		mResult.add(mLOPRTManagerSchema);

	}
}
