package com.sinosoft.workflow.cardgrp;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.cbcheckgrp.UWSendTraceAllBL;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGCUWErrorDB;
import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCGCUWSubDB;
import com.sinosoft.lis.db.LCGUWMasterDB;
import com.sinosoft.lis.db.LCGUWSubDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGCUWErrorSchema;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCGCUWSubSchema;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.schema.LCGUWSubSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LDUWUserSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGCUWErrorSet;
import com.sinosoft.lis.vschema.LCGCUWMasterSet;
import com.sinosoft.lis.vschema.LCGCUWSubSet;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.vschema.LCGUWSubSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LDUWUserSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 工作流服务类:团体新契约人工核保
 * </p>
 * <p>
 * Description: 人工核保工作流AfterInit服务类
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

public class GrpUWManuNormChkAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GrpUWManuNormChkAfterInitService.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	private VData mInputData;
	/** 业务处理相关变量 */
	private String mGrpContNo = "";
	private String mPolNo = "";
	private String mUWFlag = ""; // 核保标志
	private Date mvalidate = null;
	private String mUWIdea = ""; // 核保结论
	private int mpostday; // 延长天数
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mUWUpReport;// 上报标志
	private String cooperate = "";// 合作方
	private String riskgrade = "";// 风险级别
	private String appcontract = "";// 附加协议
	private String mMissionID;
	private String mSubMissionID;
	private MMap mMap;

	/** 团体合同表 */
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCGrpContSet mAllLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	/** 团体保单表 */
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LCGrpPolSet mAllLCGrpPolSet = new LCGrpPolSet();

	/** 合同表 */
	private LCContSet mLCContSet = new LCContSet();
	private LCContSet mAllLCContSet = new LCContSet();
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 保单表 */
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	/** 保费项表 */
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCPremSet mAllLCPremSet = new LCPremSet();
	private LCPremSet mmLCPremSet = new LCPremSet();

	/** 特别约定表 */
	private LCSpecSet mLCSpecSet = new LCSpecSet();
	private LCSpecSet mAllLCSpecSet = new LCSpecSet();

	/** 核保主表 */
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSet mAllLCUWMasterSet = new LCUWMasterSet();
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();
	private LCGUWMasterSet mLCGUWMasterSet = new LCGUWMasterSet();
	private LCGUWMasterSet mAllLCGUWMasterSet = new LCGUWMasterSet();
	private LCGCUWMasterSet mLCGCUWMasterSet = new LCGCUWMasterSet();
	private LCGCUWMasterSet mAllLCGCUWMasterSet = new LCGCUWMasterSet();

	/** 核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();
	private LCCUWSubSet mAllLCCUWSubSet = new LCCUWSubSet();
	private LCGUWSubSet mLCGUWSubSet = new LCGUWSubSet();
	private LCGUWSubSet mAllLCGUWSubSet = new LCGUWSubSet();
	private LCGCUWSubSet mLCGCUWSubSet = new LCGCUWSubSet();
	private LCGCUWSubSet mAllLCGCUWSubSet = new LCGCUWSubSet();

	/** 数据操作字符串 */
	private String mOperator;

	public GrpUWManuNormChkAfterInitService() {
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
		if (!getInputData(cInputData))
			return false;

		logger.debug("---GrpUWManuNormChkBL getInputData OK---");
		// mod by heyq 20041220 由于核保是可以再录入险种，所以去掉对是否复核通过的校验
		// 校验数据
		// if (!checkApprove(mLCGrpContSchema))
		// {
		// return false;
		// }

		// 判断是不是整单已经确认过
		if (!checkUWGrpPol(mLCGrpContSchema)) {
			return false;
		}
		// 如果不是上报则进行正常处理
		if (this.mUWUpReport.equals("0")) {
			// 判断核保级别
			if (!checkUWGrade()) {
				return false;
			}

			/*
			 * //如果发核保通知书校验是不是主险 if(!checkMain()) { return false; }
			 */
			// commented by zhr 2004.11
			// 判断个单是不是全部通过(当核保结论为正常通过或通融承保时,要确保该团体单下的所有个单均已通过核保)
			if (!mUWFlag.equals("1") && !mUWFlag.equals("6")
					&& !mUWFlag.equals("a") && !mUWFlag.equals("7")
					&& !mUWFlag.equals("8")) {
				if (!checkUWPol(mLCGrpContSchema)) {
					return false;
				}
			}
			// add by zhangxing 校验浮动费率的权限
			// if(!checkFloatRate())
			// {
			// return false;
			// }
			// add by zhangxing 校验特殊职业的权限
			// if(!checkSpecJob())
			// {
			// return false;
			// }
			// add by zhangxing 校验最小年龄的权限
			// if(!checkOverage())
			// {
			// return false;
			// }
			// add by zhangxing 校验每个人的最低保额的权限
			// if(!checkLowestAmnt())
			// {
			// return false;
			// }

			if (!dealData()) {
				return false;
			}

			logger.debug("dealData successful!");
		} else {
			// 如果是上报则进行上报处理
			UWSendTraceAllBL tUWSendTraceAllBL = new UWSendTraceAllBL();

			boolean tResult = tUWSendTraceAllBL.submitData(mInputData, "");
			if (tResult) {
				mMap = (MMap) tUWSendTraceAllBL.getResult()
						.getObjectByObjectName("MMap", 0);
				mMap.put("update lcgrpcont set ConferNo='" + cooperate
						+ "',SignReportNo='" + riskgrade + "',AgentConferNo='"
						+ appcontract + "' where grpcontno='" + mGrpContNo
						+ "'", "UPDATE");
				mTransferData = (TransferData) tUWSendTraceAllBL.getResult()
						.getObjectByObjectName("TransferData", 0);

			} else {
				this.mErrors.copyAllErrors(tUWSendTraceAllBL.getErrors());
			}

		}
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
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (dealOnePol() == false)
			return false;

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		mLCGrpContSchema.setUWFlag(mUWFlag);
		mLCGrpContSchema.setUWOperator(mOperator);
		mLCGrpContSchema.setUWDate(PubFun.getCurrentDate());
		mLCGrpContSchema.setOperator(mOperator);
		mLCGrpContSchema.setModifyDate(PubFun.getCurrentDate());
		mLCGrpContSchema.setModifyTime(PubFun.getCurrentTime());
		// **********增加三条，暂时使用自己认为未使用的字段，如果以后遇到冲突会即使调整
		mLCGrpContSchema.setConferNo(cooperate);
		mLCGrpContSchema.setSignReportNo(riskgrade);
		mLCGrpContSchema.setAgentConferNo(appcontract);

		mLCGrpContSet.clear();
		mLCGrpContSet.add(mLCGrpContSchema);

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mGrpContNo);
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
		if (tLCGrpPolSet == null || tLCGrpPolSet.size() <= 0) {
			// @@错误处理
			//this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
			CError.buildErr(this,"集体险种保单查询失败!");
			return false;
		}
		int n = tLCGrpPolSet.size();
		int i = 0;
		LCGrpPolSchema tLCGrpPolSchema;
		for (i = 1; i <= n; i++) {
			tLCGrpPolSchema = tLCGrpPolSet.get(i);
			tLCGrpPolSchema.setUWFlag(mUWFlag);
			tLCGrpPolSchema.setUWOperator(mOperator);
			tLCGrpPolSchema.setUWDate(PubFun.getCurrentDate());
			tLCGrpPolSchema.setOperator(mOperator);
			tLCGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
			mLCGrpPolSet.add(tLCGrpPolSchema);
		}

		LCGCUWMasterSchema tLCGCUWMasterSchema = new LCGCUWMasterSchema();
		LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
		tLCGCUWMasterDB.setGrpContNo(mGrpContNo);
		LCGCUWMasterSet tLCGCUWMasterSet = new LCGCUWMasterSet();
		tLCGCUWMasterSet = tLCGCUWMasterDB.query();
		if (tLCGCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			//this.mErrors.copyAllErrors(tLCGCUWMasterDB.mErrors);
			CError.buildErr(this,"集体核保总表取数失败!");
			return false;
		}

		n = tLCGCUWMasterSet.size();

		logger.debug("mastercount=" + n);

		if (n == 1) {
			tLCGCUWMasterSchema = tLCGCUWMasterSet.get(1);
			int uwno = tLCGCUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCGCUWMasterSchema.setUWNo(uwno);

			tLCGCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCGCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保
			tLCGCUWMasterSchema.setUWGrade(mUWPopedom);
			tLCGCUWMasterSchema.setAppGrade(mAppGrade);
			tLCGCUWMasterSchema.setState(mUWFlag);
			tLCGCUWMasterSchema.setUWIdea(mUWIdea);
			tLCGCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCGCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "集体核保总表取数据不唯一!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCGCUWMasterSet.clear();
		mLCGCUWMasterSet.add(tLCGCUWMasterSchema);

		// 核保轨迹表
		LCGCUWSubSchema tLCGCUWSubSchema = new LCGCUWSubSchema();
		LCGCUWSubDB tLCGCUWSubDB = new LCGCUWSubDB();
		tLCGCUWSubDB.setGrpContNo(mGrpContNo);
		LCGCUWSubSet tLCGCUWSubSet = new LCGCUWSubSet();
		tLCGCUWSubSet = tLCGCUWSubDB.query();
		if (tLCGCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBl";
			tError.functionName = "prepareUW";
			tError.errorMessage = "集体核保轨迹表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int m = tLCGCUWSubSet.size();
		if (m >= 0) {
			m++; // 核保次数
			tLCGCUWSubSchema = new LCGCUWSubSchema(); // tLCGCUWSubSet.get(1);

			tLCGCUWSubSchema.setUWNo(m); // 第几次核保
			tLCGCUWSubSchema.setGrpContNo(tLCGCUWMasterSchema.getGrpContNo());
			tLCGCUWSubSchema.setProposalGrpContNo(tLCGCUWMasterSchema
					.getProposalGrpContNo());
			tLCGCUWSubSchema.setAgentCode(tLCGCUWMasterSchema.getAgentCode());
			tLCGCUWSubSchema.setAgentGroup(tLCGCUWMasterSchema.getAgentGroup());
			tLCGCUWSubSchema.setUWGrade(tLCGCUWMasterSchema.getUWGrade()); // 核保级别
			tLCGCUWSubSchema.setAppGrade(tLCGCUWMasterSchema.getAppGrade()); // 申请级别
			tLCGCUWSubSchema.setAutoUWFlag(tLCGCUWMasterSchema.getAutoUWFlag());
			tLCGCUWSubSchema.setState(tLCGCUWMasterSchema.getState());
			tLCGCUWSubSchema.setPassFlag(tLCGCUWMasterSchema.getState());
			tLCGCUWSubSchema.setPostponeDay(tLCGCUWMasterSchema
					.getPostponeDay());
			tLCGCUWSubSchema.setPostponeDate(tLCGCUWMasterSchema
					.getPostponeDate());
			tLCGCUWSubSchema.setUpReportContent(tLCGCUWMasterSchema
					.getUpReportContent());
			tLCGCUWSubSchema.setHealthFlag(tLCGCUWMasterSchema.getHealthFlag());
			tLCGCUWSubSchema.setSpecFlag(tLCGCUWMasterSchema.getSpecFlag());
			tLCGCUWSubSchema.setSpecReason(tLCGCUWMasterSchema.getSpecReason());
			tLCGCUWSubSchema.setQuesFlag(tLCGCUWMasterSchema.getQuesFlag());
			tLCGCUWSubSchema.setReportFlag(tLCGCUWMasterSchema.getReportFlag());
			tLCGCUWSubSchema.setChangePolFlag(tLCGCUWMasterSchema
					.getChangePolFlag());
			tLCGCUWSubSchema.setChangePolReason(tLCGCUWMasterSchema
					.getChangePolReason());
			tLCGCUWSubSchema.setAddPremReason(tLCGCUWMasterSchema
					.getAddPremReason());
			tLCGCUWSubSchema.setPrintFlag(tLCGCUWMasterSchema.getPrintFlag());
			tLCGCUWSubSchema.setPrintFlag2(tLCGCUWMasterSchema.getPrintFlag2());
			tLCGCUWSubSchema.setUWIdea(tLCGCUWMasterSchema.getUWIdea());
			tLCGCUWSubSchema.setOperator(tLCGCUWMasterSchema.getOperator()); // 操作员
			tLCGCUWSubSchema.setManageCom(tLCGCUWMasterSchema.getManageCom());
			tLCGCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCGCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCGCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "集体核保轨迹表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGCUWSubSet.clear();
		mLCGCUWSubSet.add(tLCGCUWSubSchema);

		LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
		LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB();
		tLCGUWMasterDB.setGrpContNo(mGrpContNo);
		LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
		tLCGUWMasterSet = tLCGUWMasterDB.query();
		if (tLCGUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "集体核保总表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		n = tLCGUWMasterSet.size();
		logger.debug("mastercount=" + n);

		if (n <= 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "集体核保总表取数据不唯一!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGUWMasterSet.clear();
		mLCGUWSubSet.clear();
		for (i = 1; i <= n; i++) {
			tLCGUWMasterSchema = tLCGUWMasterSet.get(i);
			int uwno = tLCGUWMasterSet.get(i).getUWNo();
			uwno++;
			logger.debug("uwno==" + uwno);
			tLCGUWMasterSchema.setUWNo(uwno);
			tLCGUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCGUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保
			tLCGUWMasterSchema.setUWGrade(mUWPopedom);
			tLCGUWMasterSchema.setAppGrade(mAppGrade);
			tLCGUWMasterSchema.setState(mUWFlag);
			tLCGUWMasterSchema.setUWIdea(mUWIdea);
			tLCGUWMasterSchema.setOperator(mOperator); // 操作员
			tLCGUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			mLCGUWMasterSet.add(tLCGUWMasterSchema);

			// 核保轨迹表
			LCGUWSubSchema tLCGUWSubSchema = new LCGUWSubSchema();
			LCGUWSubDB tLCGUWSubDB = new LCGUWSubDB();
			tLCGUWSubDB.setGrpPolNo(tLCGUWMasterSchema.getGrpPolNo());
			LCGUWSubSet tLCGUWSubSet = new LCGUWSubSet();
			tLCGUWSubSet = tLCGUWSubDB.query();
			if (tLCGUWSubDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpUWManuNormChkBl";
				tError.functionName = "prepareUW";
				tError.errorMessage = "集体核保轨迹表查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			m = tLCGUWSubSet.size();
			if (m >= 0) {
				m++; // 核保次数
				tLCGUWSubSchema = new LCGUWSubSchema(); // tLCGUWSubSet.get(1);

				tLCGUWSubSchema.setUWNo(m); // 第几次核保
				tLCGUWSubSchema.setGrpContNo(tLCGUWMasterSchema.getGrpContNo());
				tLCGUWSubSchema.setGrpPolNo(tLCGUWMasterSchema.getGrpPolNo());
				tLCGUWSubSchema.setProposalGrpContNo(tLCGUWMasterSchema
						.getProposalGrpContNo());
				tLCGUWSubSchema.setGrpProposalNo(tLCGUWMasterSchema
						.getGrpProposalNo());
				tLCGUWSubSchema.setAgentCode(tLCGUWMasterSchema.getAgentCode());
				tLCGUWSubSchema.setAgentGroup(tLCGUWMasterSchema
						.getAgentGroup());
				tLCGUWSubSchema.setUWGrade(tLCGUWMasterSchema.getUWGrade()); // 核保级别
				tLCGUWSubSchema.setAppGrade(tLCGUWMasterSchema.getAppGrade()); // 申请级别
				tLCGUWSubSchema.setAutoUWFlag(tLCGUWMasterSchema
						.getAutoUWFlag());
				tLCGUWSubSchema.setState(tLCGUWMasterSchema.getState());
				tLCGUWSubSchema.setPassFlag(tLCGUWMasterSchema.getState());
				tLCGUWSubSchema.setPostponeDay(tLCGUWMasterSchema
						.getPostponeDay());
				tLCGUWSubSchema.setPostponeDate(tLCGUWMasterSchema
						.getPostponeDate());
				tLCGUWSubSchema.setUpReportContent(tLCGUWMasterSchema
						.getUpReportContent());
				tLCGUWSubSchema.setHealthFlag(tLCGUWMasterSchema
						.getHealthFlag());
				tLCGUWSubSchema.setSpecFlag(tLCGUWMasterSchema.getSpecFlag());
				tLCGUWSubSchema.setSpecReason(tLCGUWMasterSchema
						.getSpecReason());
				tLCGUWSubSchema.setQuesFlag(tLCGUWMasterSchema.getQuesFlag());
				tLCGUWSubSchema.setReportFlag(tLCGUWMasterSchema
						.getReportFlag());
				tLCGUWSubSchema.setChangePolFlag(tLCGUWMasterSchema
						.getChangePolFlag());
				tLCGUWSubSchema.setChangePolReason(tLCGUWMasterSchema
						.getChangePolReason());
				tLCGUWSubSchema.setAddPremReason(tLCGUWMasterSchema
						.getAddPremReason());
				tLCGUWSubSchema.setPrintFlag(tLCGUWMasterSchema.getPrintFlag());
				tLCGUWSubSchema.setPrintFlag2(tLCGUWMasterSchema
						.getPrintFlag2());
				tLCGUWSubSchema.setUWIdea(tLCGUWMasterSchema.getUWIdea());
				tLCGUWSubSchema.setOperator(tLCGUWMasterSchema.getOperator()); // 操作员
				tLCGUWSubSchema.setManageCom(tLCGUWMasterSchema.getManageCom());
				tLCGUWSubSchema.setMakeDate(PubFun.getCurrentDate());
				tLCGUWSubSchema.setMakeTime(PubFun.getCurrentTime());
				tLCGUWSubSchema.setModifyDate(PubFun.getCurrentDate());
				tLCGUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpUWManuNormChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = "集体核保轨迹表查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCGUWSubSet.add(tLCGUWSubSchema);
		}

		// 上级核保
		if (mUWFlag.equals("6")) {
			uplevel();
		}

		if (mUWFlag.equals("1") || mUWFlag.equals("6") || mUWFlag.equals("a")) {
			// 保单
			if (preparePol() == false) {
				return false;
			}

			// 核保信息
			if (prepareUW() == false) {
				return false;
			}

		}

		if (mUWFlag.equals("2")) {
			TimeAccept();
		}

		if (mUWFlag.equals("3")) {
			CondAccept();

			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremSet.set(mmLCPremSet);
			mAllLCPremSet.add(tLCPremSet);

			LCSpecSet tLCSpecSet = new LCSpecSet();
			tLCSpecSet.set(mLCSpecSet);
			mAllLCSpecSet.add(tLCSpecSet);
		}

		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet.set(mLCPolSet);
		mAllLCPolSet.add(tLCPolSet);

		LCContSet tLCContSet = new LCContSet();
		tLCContSet.set(mLCContSet);
		mAllLCContSet.add(tLCContSet);

		LCGrpPolSet tLCGrpPolSet1 = new LCGrpPolSet();
		tLCGrpPolSet1.set(mLCGrpPolSet);
		mAllLCGrpPolSet.add(tLCGrpPolSet1);

		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		tLCGrpContSet.set(mLCGrpContSet);
		mAllLCGrpContSet.add(tLCGrpContSet);

		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet.set(mLCUWMasterSet);
		mAllLCUWMasterSet.add(tLCUWMasterSet);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet.set(mLCCUWMasterSet);
		mAllLCCUWMasterSet.add(tLCCUWMasterSet);

		LCGUWMasterSet tLCGUWMasterSet1 = new LCGUWMasterSet();
		tLCGUWMasterSet1.set(mLCGUWMasterSet);
		mAllLCGUWMasterSet.add(tLCGUWMasterSet1);

		LCGCUWMasterSet tLCGCUWMasterSet1 = new LCGCUWMasterSet();
		tLCGCUWMasterSet1.set(mLCGCUWMasterSet);
		mAllLCGCUWMasterSet.add(tLCGCUWMasterSet1);

		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		tLCUWSubSet.set(mLCUWSubSet);
		mAllLCUWSubSet.add(tLCUWSubSet);

		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet.set(mLCCUWSubSet);
		mAllLCCUWSubSet.add(tLCCUWSubSet);

		LCGUWSubSet tLCGUWSubSet1 = new LCGUWSubSet();
		tLCGUWSubSet1.set(mLCGUWSubSet);
		mAllLCGUWSubSet.add(tLCGUWSubSet1);

		LCGCUWSubSet tLCGCUWSubSet1 = new LCGCUWSubSet();
		tLCGCUWSubSet1.set(mLCGCUWSubSet);
		mAllLCGCUWSubSet.add(tLCGCUWSubSet1);

		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean TimeAccept() {
		Date temp;
		String temp1 = "D";
		Date temp2;

		FDate tFDate = new FDate();
		temp = null;
		temp2 = tFDate.getDate(mLCPolSchema.getCValiDate());

		mvalidate = PubFun.calDate(temp2, mpostday, temp1, temp);

		logger.debug("---TimeAccept -- 延期 ---");
		mLCPolSchema.setCValiDate(mvalidate);
		logger.debug("---mvalidate---" + mvalidate);

		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareUW() {
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setGrpContNo(mGrpContNo);
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();
		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "核保总表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int n = tLCCUWMasterSet.size();
		int i = 0;
		logger.debug("mastercount=" + n);

		if (n <= 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "合同单核保总表取数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (i = 1; i <= n; i++) {
			tLCCUWMasterSchema = tLCCUWMasterSet.get(i);
			int uwno = tLCCUWMasterSet.get(i).getUWNo();
			uwno++;
			tLCCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保
			tLCCUWMasterSchema.setUWGrade(mUWPopedom);
			tLCCUWMasterSchema.setAppGrade(mAppGrade);
			tLCCUWMasterSchema.setUWNo(uwno);
			tLCCUWMasterSchema.setState(mUWFlag);
			tLCCUWMasterSchema.setUWIdea(mUWIdea);
			tLCCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

			mLCCUWMasterSet.clear();
			mLCCUWMasterSet.add(tLCCUWMasterSchema);

			// 核保轨迹表
			LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
			LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
			tLCCUWSubDB.setContNo(tLCCUWMasterSchema.getContNo());
			LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
			tLCCUWSubSet = tLCCUWSubDB.query();
			if (tLCCUWSubDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpUWManuNormChkBl";
				tError.functionName = "prepareUW";
				tError.errorMessage = "核保轨迹表查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			int m = tLCCUWSubSet.size();
			if (m >= 0) {
				m++; // 核保次数
				tLCCUWSubSchema = new LCCUWSubSchema(); // tLCCUWSubSet.get(1);

				tLCCUWSubSchema.setUWNo(m); // 第几次核保
				tLCCUWSubSchema.setContNo(tLCCUWMasterSchema.getContNo());
				tLCCUWSubSchema.setGrpContNo(tLCCUWMasterSchema.getGrpContNo());
				tLCCUWSubSchema.setProposalContNo(tLCCUWMasterSchema
						.getProposalContNo());
				tLCCUWSubSchema.setInsuredNo(tLCCUWMasterSchema.getInsuredNo());
				tLCCUWSubSchema.setInsuredName(tLCCUWMasterSchema
						.getInsuredName());
				tLCCUWSubSchema.setAppntNo(tLCCUWMasterSchema.getAppntNo());
				tLCCUWSubSchema.setAppntName(tLCCUWMasterSchema.getAppntName());
				tLCCUWSubSchema.setAgentCode(tLCCUWMasterSchema.getAgentCode());
				tLCCUWSubSchema.setAgentGroup(tLCCUWMasterSchema
						.getAgentGroup());
				tLCCUWSubSchema.setUWGrade(tLCCUWMasterSchema.getUWGrade()); // 核保级别
				tLCCUWSubSchema.setAppGrade(tLCCUWMasterSchema.getAppGrade()); // 申请级别
				tLCCUWSubSchema.setAutoUWFlag(tLCCUWMasterSchema
						.getAutoUWFlag());
				tLCCUWSubSchema.setState(tLCCUWMasterSchema.getState());
				tLCCUWSubSchema.setPassFlag(tLCCUWMasterSchema.getState());
				tLCCUWSubSchema.setPostponeDay(tLCCUWMasterSchema
						.getPostponeDay());
				tLCCUWSubSchema.setPostponeDate(tLCCUWMasterSchema
						.getPostponeDate());
				tLCCUWSubSchema.setUpReportContent(tLCCUWMasterSchema
						.getUpReportContent());
				tLCCUWSubSchema.setHealthFlag(tLCCUWMasterSchema
						.getHealthFlag());
				tLCCUWSubSchema.setSpecFlag(tLCCUWMasterSchema.getSpecFlag());
				tLCCUWSubSchema.setSpecReason(tLCCUWMasterSchema
						.getSpecReason());
				tLCCUWSubSchema.setQuesFlag(tLCCUWMasterSchema.getQuesFlag());
				tLCCUWSubSchema.setReportFlag(tLCCUWMasterSchema
						.getReportFlag());
				tLCCUWSubSchema.setChangePolFlag(tLCCUWMasterSchema
						.getChangePolFlag());
				tLCCUWSubSchema.setChangePolReason(tLCCUWMasterSchema
						.getChangePolReason());
				tLCCUWSubSchema.setAddPremReason(tLCCUWMasterSchema
						.getAddPremReason());
				tLCCUWSubSchema.setPrintFlag(tLCCUWMasterSchema.getPrintFlag());
				tLCCUWSubSchema.setPrintFlag2(tLCCUWMasterSchema
						.getPrintFlag2());
				tLCCUWSubSchema.setUWIdea(tLCCUWMasterSchema.getUWIdea());
				tLCCUWSubSchema.setOperator(tLCCUWMasterSchema.getOperator()); // 操作员
				tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
				tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
				tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
				tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
				tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpUWManuNormChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = "核保轨迹表查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCCUWSubSet.clear();
			mLCCUWSubSet.add(tLCCUWSubSchema);
		}

		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setGrpContNo(mGrpContNo);
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet = tLCUWMasterDB.query();
		if (tLCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "核保总表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		n = tLCUWMasterSet.size();
		i = 0;
		logger.debug("mastercount=" + n);

		if (n <= 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "核保总表取数据不唯一!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCUWMasterSet.clear();
		mLCUWSubSet.clear();
		for (i = 1; i <= n; i++) {
			tLCUWMasterSchema = tLCUWMasterSet.get(i);
			int uwno = tLCUWMasterSet.get(i).getUWNo();
			uwno++;
			tLCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保
			tLCUWMasterSchema.setUWGrade(mUWPopedom);
			tLCUWMasterSchema.setAppGrade(mAppGrade);
			tLCUWMasterSchema.setUWNo(uwno);
			tLCUWMasterSchema.setState(mUWFlag);
			tLCUWMasterSchema.setUWIdea(mUWIdea);
			tLCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			mLCUWMasterSet.add(tLCUWMasterSchema);

			// 核保轨迹表
			LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
			LCUWSubDB tLCUWSubDB = new LCUWSubDB();
			tLCUWSubDB.setPolNo(tLCUWMasterSchema.getPolNo());
			LCUWSubSet tLCUWSubSet = new LCUWSubSet();
			tLCUWSubSet = tLCUWSubDB.query();
			if (tLCUWSubDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpUWManuNormChkBl";
				tError.functionName = "prepareUW";
				tError.errorMessage = "核保轨迹表查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			int m = tLCUWSubSet.size();
			if (m >= 0) {
				m++; // 核保次数
				tLCUWSubSchema = new LCUWSubSchema(); // tLCUWSubSet.get(1);

				tLCUWSubSchema.setUWNo(m); // 第几次核保
				tLCUWSubSchema.setContNo(tLCUWMasterSchema.getContNo());
				tLCUWSubSchema.setPolNo(tLCUWMasterSchema.getPolNo());
				tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
				tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema
						.getProposalContNo());
				tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
				tLCUWSubSchema.setInsuredNo(tLCUWMasterSchema.getInsuredNo());
				tLCUWSubSchema.setInsuredName(tLCUWMasterSchema
						.getInsuredName());
				tLCUWSubSchema.setAppntNo(tLCUWMasterSchema.getAppntNo());
				tLCUWSubSchema.setAppntName(tLCUWMasterSchema.getAppntName());
				tLCUWSubSchema.setAgentCode(tLCUWMasterSchema.getAgentCode());
				tLCUWSubSchema.setAgentGroup(tLCUWMasterSchema.getAgentGroup());
				tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
				tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
				tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
				tLCUWSubSchema.setState(tLCUWMasterSchema.getState());
				tLCUWSubSchema.setPassFlag(tLCUWMasterSchema.getState());
				tLCUWSubSchema.setPostponeDay(tLCUWMasterSchema
						.getPostponeDay());
				tLCUWSubSchema.setPostponeDate(tLCUWMasterSchema
						.getPostponeDate());
				tLCUWSubSchema.setUpReportContent(tLCUWMasterSchema
						.getUpReportContent());
				tLCUWSubSchema.setHealthFlag(tLCUWMasterSchema.getHealthFlag());
				tLCUWSubSchema.setSpecFlag(tLCUWMasterSchema.getSpecFlag());
				tLCUWSubSchema.setSpecReason(tLCUWMasterSchema.getSpecReason());
				tLCUWSubSchema.setQuesFlag(tLCUWMasterSchema.getQuesFlag());
				tLCUWSubSchema.setReportFlag(tLCUWMasterSchema.getReportFlag());
				tLCUWSubSchema.setChangePolFlag(tLCUWMasterSchema
						.getChangePolFlag());
				tLCUWSubSchema.setChangePolReason(tLCUWMasterSchema
						.getChangePolReason());
				tLCUWSubSchema.setAddPremReason(tLCUWMasterSchema
						.getAddPremReason());
				tLCUWSubSchema.setPrintFlag(tLCUWMasterSchema.getPrintFlag());
				tLCUWSubSchema.setPrintFlag2(tLCUWMasterSchema.getPrintFlag2());
				tLCUWSubSchema.setUWIdea(tLCUWMasterSchema.getUWIdea());
				tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator()); // 操作员
				tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
				tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
				tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
				tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpUWManuNormChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = "核保轨迹表查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCUWSubSet.add(tLCUWSubSchema);
		}

		return true;
	}

	/**
	 * 准备团体保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePol() {
		/*
		 * if(mUWFlag.equals("7"))//(sxy-add
		 * 2003-08-19)-判断下核保通知书时该团体单下的主险团体单下是否有问题件针对各种问题件置该团体单的状态 {
		 * //发放问题件时,只有操作员的问题件,该团体单将处于复核修改处(即置ApproveFlag=1) String tStrSql =
		 * "select * from lcissuepol where proposalno='" + mmGrpContNo +"'" + "
		 * and replyman is null " + " and backobjtype='1' " + " and (select
		 * count(*) from lcissuepol where proposalno='" +mmGrpContNo+"' and
		 * replyman is null and backobjtype not in ('1'))=0 "; LCIssuePolDB
		 * tLCIssuePolDB = new LCIssuePolDB(); LCIssuePolSet tLCIssuePolSet =
		 * new LCIssuePolSet(); tLCIssuePolSet =
		 * tLCIssuePolDB.executeQuery(tStrSql); if(tLCIssuePolSet.size()>0) {
		 * mLCGrpContSchema.setApproveFlag("1"); }
		 * 
		 * //发放问题件时,不只有操作员的问题件,该团体单将处于问题修改或管理中心处(即置ApproveFlag=2) tStrSql =
		 * "select * from lcissuepol where proposalno='" + mmGrpContNo +"'" + "
		 * and replyman is null " + " and (select count(*) from lcissuepol where
		 * proposalno='" + mmGrpContNo +"' and replyman is null and backobjtype
		 * not in ('1'))>0 "; tLCIssuePolSet =
		 * tLCIssuePolDB.executeQuery(tStrSql); if(tLCIssuePolSet.size()>0) {
		 * mLCGrpContSchema.setApproveFlag("2"); } }
		 */

		if (mUWFlag.equals("1") || mUWFlag.equals("6") || mUWFlag.equals("a")
				|| mUWFlag.equals("7") || mUWFlag.equals("8")) {
			preparePPol();
		}

		return true;
	}

	/**
	 * 个人保单
	 * 
	 * @return
	 */
	private boolean preparePPol() {
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();

		tLCContDB.setGrpContNo(mGrpContNo);
		tLCContSet = tLCContDB.query();
		int n = 0;
		int i = 0;
		if (tLCContSet != null) {
			n = tLCContSet.size();
			LCContSchema tLCContSchema = null;
			for (i = 1; i <= n; i++) {
				tLCContSchema = tLCContSet.get(i);
				tLCContSchema.setUWFlag(mUWFlag);
				tLCContSchema.setUWOperator(mOperator);
				tLCContSchema.setUWDate(PubFun.getCurrentDate());
				tLCContSchema.setOperator(mOperator);
				tLCContSchema.setModifyDate(PubFun.getCurrentDate());
				tLCContSchema.setModifyTime(PubFun.getCurrentTime());
				mLCContSet.add(tLCContSchema);
			}
		}

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		tLCPolSet = tLCPolDB.query();

		if (tLCPolSet.size() > 0) {
			n = tLCPolSet.size();
			LCPolSchema tLCPolSchema = null;
			for (i = 1; i <= n; i++) {
				tLCPolSchema = tLCPolSet.get(i);
				tLCPolSchema.setUWFlag(mUWFlag);
				tLCPolSchema.setUWCode(mOperator);
				tLCPolSchema.setUWDate(PubFun.getCurrentDate());
				tLCPolSchema.setOperator(mOperator);
				tLCPolSchema.setModifyDate(PubFun.getCurrentDate());
				tLCPolSchema.setModifyTime(PubFun.getCurrentTime());
				mLCPolSet.add(tLCPolSchema);
			}
		}
		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean CondAccept() {
		int n;
		int i;
		int max;
		String sql;
		String specno = "";

		logger.debug("---CondAccept---");

		n = 0;
		n = mLCSpecSet.size();
		if (n > 0) {
			LCSpecSchema tLCSpecSchema = new LCSpecSchema();
			LCSpecDB tLCSpecDB = new LCSpecDB();

			sql = "select * from LCSpec where specno = (select max(specno) from LCSpec where 1 = 1)";
			LCSpecSet tLCSpecSet = tLCSpecDB.executeQuery(sql);
			tLCSpecSchema = tLCSpecSet.get(1);

			// 生成流水号码
			// specno = tLCSpecSchema.getSpecNo();
			// max = String.valueOf(specno);
			// max = max + 1;
			// specno =

			tLCSpecSchema = mLCSpecSet.get(1);

			// tLCSpecSchema.setSpecNo(PubFun1.CreateMaxNo("SpecNo",PubFun.getNoLimit(mGlobalInput.ComCode)));
			tLCSpecSchema.setPolNo(mPolNo);
			logger.debug("specpolno=" + mPolNo);
			// tLCSpecSchema.setPolType("1");
			tLCSpecSchema.setEndorsementNo("");
			tLCSpecSchema.setSpecType("");
			tLCSpecSchema.setSpecCode("");
			// tLCSpecSchema.setSpecContent();
			tLCSpecSchema.setPrtFlag("1");
			tLCSpecSchema.setBackupType("");
			tLCSpecSchema.setOperator(mOperator);
			tLCSpecSchema.setMakeDate(PubFun.getCurrentDate());
			tLCSpecSchema.setMakeTime(PubFun.getCurrentTime());
			tLCSpecSchema.setModifyDate(PubFun.getCurrentDate());
			tLCSpecSchema.setModifyTime(PubFun.getCurrentTime());

			mLCSpecSet.clear();
			mLCSpecSet.add(tLCSpecSchema);
		}

		if ((n = mLCPremSet.size()) > 0) {
			logger.debug("premsize=" + n);
			for (i = 1; i < n; i++)
				;
			{
				LCPremSchema ttLCPremSchema = mLCPremSet.get(i);
				LCPremSchema tLCPremSchema = new LCPremSchema();
				LCPremDB tLCPremDB = new LCPremDB();
				double tPrem;

				tLCPremDB.setPolNo(mLCPolSchema.getPolNo());
				tLCPremDB.setDutyCode(tLCPremSchema.getDutyCode());

				LCPremSet tLCPremSet = tLCPremDB.query();
				tLCPremSchema = tLCPremSet.get(1);

				sql = "select * from lcprem where payplancode = (select max(payplancode) from lcprem where payplancode like '000000%') and polno = "
						+ mLCPolSchema.getPolNo().trim();
				LCPremSet ttLCPremSet = tLCPremDB.executeQuery(sql);
				String tPayPlanCode = "";
				String PayPlanCode = "";

				if (ttLCPremSet.size() > 0) {
					LCPremSchema tttLCPremSchema = ttLCPremSet.get(1);

					// 生成流水号码

					PayPlanCode = tttLCPremSchema.getPayPlanCode();

					if (PayPlanCode.length() > 0) {
						int j = 0;
						max = Integer.parseInt(PayPlanCode);
						max = max + 1;
						PayPlanCode = String.valueOf(max);
						for (j = PayPlanCode.length(); j < 8; j++) {
							PayPlanCode = "0" + PayPlanCode;
						}
					}
				} else {
					PayPlanCode = "00000001";
				}

				logger.debug("payplancode" + PayPlanCode);
				// 保单总保费
				tPrem = mLCPolSchema.getPrem() + ttLCPremSchema.getPrem();
				// tLCPremSchema.setPolNo(mLCPolSchema.getPolNo());
				// tLCPremSchema.setDutyCode(mmaxDutyCode);
				tLCPremSchema.setPayPlanCode(PayPlanCode);
				// tLCPremSchema.setGrpContNo(mLCPolSchema.get);
				// tLCPremSchema.setPayPlanType();
				// tLCPremSchema.setPayTimes();
				// tLCPremSchema.setPayIntv();
				// tLCPremSchema.setMult();
				// tLCPremSchema.setStandPrem();
				tLCPremSchema.setPrem(ttLCPremSchema.getPrem());
				// tLCPremSchema.setSumPrem();
				// tLCPremSchema.setRate();
				tLCPremSchema.setPayStartDate(ttLCPremSchema.getPayStartDate());
				tLCPremSchema.setPayEndDate(ttLCPremSchema.getPayEndDate());
				// tLCPremSchema.setPaytoDate();
				// tLCPremSchema.setState();
				// tLCPremSchema.setBankCode();
				// tLCPremSchema.setBankAccNo();
				// tLCPremSchema.setAppntNo();
				// tLCPremSchema.setAppntType("1"); //投保人类型
				tLCPremSchema.setModifyDate(PubFun.getCurrentDate());
				tLCPremSchema.setModifyTime(PubFun.getCurrentTime());

				mmLCPremSet.add(tLCPremSchema);

				// 更新保单数据
				mLCPolSchema.setPrem(tPrem);

			}
		}
		return true;
	}

	/**
	 * 待上级核保 输出：如果发生错误则返回false,否则返回true
	 */
	private void uplevel() {
		LCGCUWErrorDB tLCGCUWErrorDB = new LCGCUWErrorDB();
		LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
		LCGCUWMasterSchema tLCGCUWMasterSchema = new LCGCUWMasterSchema();

		tLCGCUWErrorDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		tLCGCUWMasterDB.setProposalGrpContNo(mLCGrpContSchema.getGrpContNo());

		if (tLCGCUWMasterDB.getInfo() == true) {
			tLCGCUWMasterSchema = tLCGCUWMasterDB.getSchema();
		}

		String tcurrgrade = "";

		if (tLCGCUWMasterSchema.getAppGrade() == null) {
			tcurrgrade = "A";
		} else {
			tcurrgrade = tLCGCUWMasterSchema.getAppGrade();
		}

		String tpolno = mLCGrpContSchema.getGrpContNo();
		String tsql = "select * from LCGCUWerror where GrpContNo = '"
				+ tpolno.trim()
				+ "' and uwno = (select max(uwno) from LCGCUWerror where GrpContNo = '"
				+ tpolno.trim() + "')";
		LCGCUWErrorSet tLCGCUWErrorSet = tLCGCUWErrorDB.executeQuery(tsql);

		int errno = tLCGCUWErrorSet.size();
		if (errno > 0) {
			for (int i = 1; i <= errno; i++) {
				LCGCUWErrorSchema tLCGCUWErrorSchema = new LCGCUWErrorSchema();
				tLCGCUWErrorSchema = tLCGCUWErrorSet.get(i);
				String terrgrade = tLCGCUWErrorSchema.getUWGrade();
				if (terrgrade.compareTo(tcurrgrade) > 0) {
					tcurrgrade = terrgrade;
				}
			}
		}

		mAppGrade = tcurrgrade;

		// 与当前核保员级别校验
		if ((mUWPopedom.compareTo(mAppGrade) >= 0 && mUWPopedom.compareTo("L") < 0)) {
			char temp[];
			char tempgrade;
			temp = mUWPopedom.toCharArray();
			tempgrade = (char) ((int) temp[0] + 1);
			logger.debug("上报级别:" + tempgrade);
			mAppGrade = String.valueOf(tempgrade);
		}
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mOperator = mGlobalInput.Operator;

		// 取投保单
		mLCGrpContSet.set((LCGrpContSet) cInputData.getObjectByObjectName(
				"LCGrpContSet", 0));
		// 核保上报流向
		mUWUpReport = (String) mTransferData.getValueByName("UWUpReport");
		cooperate = (String) mTransferData.getValueByName("cooperate");
		riskgrade = (String) mTransferData.getValueByName("riskgrade");
		appcontract = (String) mTransferData.getValueByName("appcontract");
		if (mUWUpReport == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中UWUpReport失败!");
			return false;
		}
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中MissionID失败!");
			return false;
		}

		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		if (mSubMissionID == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中MissionID失败!");
			return false;
		}

		int n = mLCGrpContSet.size();
		if (n == 1) {
			LCGrpContSchema tLCGrpContSchema = mLCGrpContSet.get(1);
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();

			// 校验是不是以下核保结论
			// if (mUWFlag == null)
			// {
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "GrpUWManuNormChkBL";
			// tError.functionName = "getInputData";
			// tError.errorMessage = "没有选择核保结论";
			// this.mErrors.addOneError(tError);
			// return false;
			// }

			// if (mUWFlag.equals(""))
			// {
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "GrpUWManuNormChkBL";
			// tError.functionName = "getInputData";
			// tError.errorMessage = "没有选择核保结论";
			// this.mErrors.addOneError(tError);
			// return false;
			// }

			tLCGrpContDB.setGrpContNo(mGrpContNo);
			if (tLCGrpContDB.getInfo() == false) {
				// @@错误处理
				//this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
				CError.buildErr(this,mGrpContNo + "集体合同单查询失败!");
				return false;
			} else {
				tLCGrpContSchema.setSchema(tLCGrpContDB);
				mLCGrpContSchema.setSchema(tLCGrpContDB);
				// mLCGrpContSet.add(tLCGrpContSchema);
			}
			/*
			 * if (mUWFlag.equals("3")) {
			 * mLCPremSet.set((LCPremSet)cInputData.getObjectByObjectName("LCPremSet",0));
			 * n = mLCPremSet.size(); if (n > 0) { }
			 * 
			 * mLCSpecSet.set((LCSpecSet)cInputData.getObjectByObjectName("LCSpecSet",0));
			 * n = mLCSpecSet.size(); if (n == 1) { }
			 * 
			 * if (getflag.equals("false")) { CError tError = new CError();
			 * tError.moduleName = "GrpUWManuNormChkBL"; tError.functionName =
			 * "getInputData"; tError.errorMessage = "条件承保数据传输失败"; this.mErrors
			 * .addOneError(tError); return false; } }
			 */

			LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
			tLCGCUWMasterDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
			tLCGCUWMasterDB.setProposalGrpContNo(tLCGrpContSchema
					.getGrpContNo());
			logger.debug("--BL--Master--"
					+ tLCGrpContSchema.getGrpContNo());
			if (tLCGCUWMasterDB.getInfo() == false) {
				// @@错误处理
				//this.mErrors.copyAllErrors(tLCGCUWMasterDB.mErrors);
				CError.buildErr(this,mGrpContNo + "集体核保总表查询失败!");
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.add(mMap);
		map.put(mAllLCPolSet, "UPDATE");
		map.put(mAllLCContSet, "UPDATE");
		map.put(mAllLCGrpContSet, "UPDATE");
		map.put(mAllLCGrpPolSet, "UPDATE");
		map.put(mAllLCUWMasterSet, "DELETE&INSERT");
		map.put(mAllLCUWSubSet, "INSERT");
		map.put(mAllLCCUWMasterSet, "DELETE&INSERT");
		map.put(mAllLCCUWSubSet, "INSERT");
		map.put(mAllLCGUWMasterSet, "DELETE&INSERT");
		map.put(mAllLCGUWSubSet, "INSERT");
		map.put(mAllLCGCUWMasterSet, "DELETE&INSERT");
		map.put(mAllLCGCUWSubSet, "INSERT");
		mResult.add(map);
		return true;
	}

	/**
	 * 校验投保单是否复核 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkApprove(LCGrpContSchema tLCGrpContSchema) {
		if (!tLCGrpContSchema.getApproveFlag().equals("9")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpUWManuNormChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号："
					+ tLCGrpContSchema.getGrpContNo().trim() + "）";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	// modify by zhangxing
	private boolean checkUWGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);

		if (!tLDUserDB.getInfo()) {
			CError.buildErr(this,"无此操作员信息，不能核保!（操作员：" + mOperator + "）");
			return false;
		}

		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		String sql = "select * From lduwuser where usercode='" + mOperator
				+ "' and uwtype='2' ";
		LDUWUserSet tLDUWUserSet = new LDUWUserSet();
		tLDUWUserSet = tLDUWUserDB.executeQuery(sql);
		LDUWUserSchema tLDUWUserSchema = new LDUWUserSchema();
		if(tLDUWUserSet.size()<=0)
		{
			CError.buildErr(this,"核保师权限查询失败!");
			return false;
		}
		else
		{
			tLDUWUserSchema = tLDUWUserSet.get(1);
		}
		String tOpAuth = tLDUWUserSchema.getUWPopedom();
		if(tOpAuth==null||tOpAuth.equals(""))
		{
			CError.buildErr(this,"核保师没有核保权限!");
			return false;
		}
		//tongmeng 2009-03-31 add
		//校验核保师是否有权限进行核保
		String tSQL_Auth = "select decode(count(*),0,0,1) from lwmission where "
			             + " missionprop1='"+mGrpContNo+"' and activityid='0000011004' "
			             + " and MissionProp12 is not null and MissionProp12>='"+tOpAuth+"' ";
		logger.debug("tSQL_Auth:"+tSQL_Auth);
		ExeSQL tExeSQL = new ExeSQL();
		String tValue = tExeSQL.getOneValue(tSQL_Auth);
		if(tValue==null||tValue.equals(""))
		{
			CError.buildErr(this,"判断核保师是否有权限核保出错!");
			return false;
		}
		if(Integer.parseInt(tValue)>0)
		{
			CError.buildErr(this,"核保权限不足,请选择上报操作!");
			return false;
		}
	
		return true;
	}

	private float parseFloat(String s) {
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else if (tmp.length() > 0) {
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		// logger.debug("tmp:"+tmp+" f1:"+f1);
		return f1;
	}

	/**
	 * 校验团体保单下个单是不是全部通过核保 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWPol(LCGrpContSchema tLCGrpContSchema) {
		//增加对lcgrpcont级别的查询校验
		boolean GrpPolPassFlag=true ;
		boolean ContPassFlag=true;
		LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB();
		tLCGUWMasterDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());

		LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
		tLCGUWMasterSet = tLCGUWMasterDB.query();
		
		String tSQL_cont = "select decode(count(*),0,0,1) from lccont where "
			             + " grpcontno='"+tLCGrpContSchema.getGrpContNo()+"'"
			             + " and uwflag in ('0','2','5','6','8') and conttype='2'";
		ExeSQL tExeSQL = new ExeSQL();
		String tValue = "";
		tValue = tExeSQL.getOneValue(tSQL_cont);
		if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>0)
		{
			CError.buildErr(this, "集体单下有未通过核保的个人投保单!");
			return false;
		}
		String tSQL_pol = "select decode(count(*),0,0,1) from lcpol where "
            + " grpcontno='"+tLCGrpContSchema.getGrpContNo()+"'"
            + " and uwflag in ('0','2','5','6','8') and conttype='2'";
		tValue = tExeSQL.getOneValue(tSQL_pol);
		if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>0)
		{
			CError.buildErr(this, "集体单下有未通过核保的个人投保单的险种!");
			return false;
		}
		/*
		
		int n = tLCGUWMasterSet.size();
		if (n > 0) {
			for (int i = 1; i <= n; i++) {
				LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
				tLCGUWMasterSchema = tLCGUWMasterSet.get(i);

				String tUWFlag = tLCGUWMasterSchema.getPassFlag();
				if (tUWFlag.equals("0") || tUWFlag.equals("2")
						|| tUWFlag.equals("5") || tUWFlag.equals("6")
						|| tUWFlag.equals("8")) {
					GrpPolPassFlag=false;
				}
			}
		}
		//LCGrpCont
		LCUWMasterDB tLCUWMasterDB =new LCUWMasterDB();
		LCUWMasterSet tLCUWMasterSet=new LCUWMasterSet();
		
		tLCUWMasterDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
		tLCUWMasterSet =tLCUWMasterDB.query();
		
		int m = tLCUWMasterSet.size();
		if (m > 0) {
			for (int i = 1; i <= n; i++) {
				LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
				tLCUWMasterSchema = tLCUWMasterSet.get(i);

				String tUWFlag = tLCUWMasterSchema.getPassFlag();
				if (tUWFlag.equals("0") || tUWFlag.equals("2")
						|| tUWFlag.equals("5") || tUWFlag.equals("6")
						|| tUWFlag.equals("8")) {
					ContPassFlag=false;
				}
			}
		}
		if(!(ContPassFlag&&GrpPolPassFlag)){
			CError.buildErr(this, "集体单下有未通过核保的个人投保单!");
			return false;
		}
		*/
		return true;
	}

	/**
	 * 校验团体保单下个单是不是全部通过核保 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWGrpPol(LCGrpContSchema tLCGrpContSchema) {
		// LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
		// tLCGCUWMasterDB.setGrpContNo(mGrpContNo);
		//
		// LCGCUWMasterSet tLCGCUWMasterSet = new LCGCUWMasterSet();
		// tLCGCUWMasterSet = tLCGCUWMasterDB.query();
		//
		// int n = tLCGCUWMasterSet.size();
		// if (n > 0)
		// {
		// for (int i = 1; i <= n; i++)
		// {
		// LCGCUWMasterSchema tLCGCUWMasterSchema = new LCGCUWMasterSchema();
		// tLCGCUWMasterSchema = tLCGCUWMasterSet.get(i);
		//
		// String tUWFlag = tLCGCUWMasterSchema.getPassFlag();
		// if ((tUWFlag.equals("4") || tUWFlag.equals("9"))&&
		// mUWUpReport.equals("0"))
		// {
		// CError tError = new CError();
		// tError.moduleName = "GrpUWManuNormChkBL";
		// tError.functionName = "checkUWGrpPol";
		// tError.errorMessage = "已经整单确认!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		// }
		return true;
	}

	/**
	 * 校验团体该核保师浮动费率的权限 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkFloatRate() {
		// LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		// tLCGrpPolDB.setGrpContNo(mGrpContNo);
		// LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		// tLCGrpPolSet = tLCGrpPolDB.query();
		// double MinFloatRate = 0.0;
		// double aFloatRate = 0.0;
		// for(int i = 1; i<=tLCGrpPolSet.size(); i++)
		// {
		// String tFloatRate = tLCGrpPolSet.get(i).getStandbyFlag1();
		// if(tFloatRate== null || tFloatRate.equals(""))
		// {
		// String tSql = " select floatRate from lcpol where 1 = 1 "
		// + " and grppolno = '"+tLCGrpPolSet.get(i).getGrpPolNo()+"'";
		// ExeSQL tExeSQL = new ExeSQL();
		// SSRS tSSRS = new SSRS();
		// tSSRS = tExeSQL.execSQL(tSql);
		// for (int a = 1; a <= tSSRS.getMaxRow();a++)
		// {
		// if (tSSRS.GetText(a,1).equals("0")) {
		// aFloatRate = 1.0;
		// }
		// else {
		// aFloatRate = Double.parseDouble(tSSRS.GetText(a,1));
		// }
		// }
		// double templateFloatRate = aFloatRate ;
		// if(aFloatRate < templateFloatRate)
		// {
		// MinFloatRate = aFloatRate;
		// }
		// else
		// {
		// MinFloatRate = templateFloatRate;
		// }
		//
		// }
		// else
		// {
		// aFloatRate = Double.parseDouble(tFloatRate);
		// double templateFloatRate = aFloatRate ;
		// if(aFloatRate < templateFloatRate)
		// {
		// MinFloatRate = aFloatRate;
		// }
		// else
		// {
		// MinFloatRate = templateFloatRate;
		// }
		// }
		// LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		// tLDUWUserDB.setUserCode(mOperator);
		// tLDUWUserDB.setUWType("1");
		// if (!tLDUWUserDB.getInfo())
		// {
		// CError tError = new CError();
		// tError.moduleName = "LCInsuredBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "此核保员没有设置核保权限!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// double tUWRate = tLDUWUserDB.getUWRate();
		//
		// if ( (MinFloatRate < tUWRate))
		// {
		// CError tError = new CError();
		// tError.moduleName = "LCInsuredBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "浮动费费率调整过大，超过该核保师的权限范围,请选择上报!";
		// this.mErrors.addOneError(tError);
		// return false;
		//
		// }

		// }

		return true;
	}

	/**
	 * 校验团体该核保师特殊职业的权限 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkSpecJob() {
		String tSql = " select distinct 1 from lcinsured where 1 =1 "
				+ " and grpcontno = '" + mGrpContNo + "'"
				+ " and occupationtype = 't'";
		ExeSQL tExeSQL = new ExeSQL();
		String ss = tExeSQL.getOneValue(tSql);

		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		tLDUWUserDB.setUserCode(mOperator);
		// tLDUWUserDB.setUWType("2");
		if (!tLDUWUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "该核保员没有设置核保权限!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tSpceJob = tLDUWUserDB.getSpecJob();
		if (tSpceJob != null && ss.equals("1") && tSpceJob.equals("0")) {
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "被保险人中有t类职业，超过该核保师的权限范围,请选择上报!";
			this.mErrors.addOneError(tError);
			return false;

		}

		return true;
	}

	/**
	 * 校验团体该核保师年龄范围的权限 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkOverage() {
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mGrpContNo);
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		tLCGrpPolSet = tLCGrpPolDB.query();

		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			String tRiskCode = tLCGrpPolSet.get(i).getRiskCode();
			String tSql = " select maxinsuredage,mininsuredage from lmriskapp where 1 = 1 "
					+ " and riskcode = '" + tRiskCode + "'";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(tSql);
			// 最大年龄
			String maxInsuredAge = tSSRS.GetText(1, 1);
			double maxAge = Double.parseDouble(maxInsuredAge);
			// 最小年龄
			String minInsuredAge = tSSRS.GetText(1, 2);
			double minAge = Double.parseDouble(minInsuredAge);

			String aSql = " select max(InsuredAppAge),min(InsuredAppAge) from lcpol where 1 =1 "
					+ " and grpcontno = '" + mGrpContNo + "'";
			tSSRS = tExeSQL.execSQL(aSql);
			// 最大年龄
			String maxInsuredAge1 = tSSRS.GetText(1, 1);
			double maxAge1 = Double.parseDouble(maxInsuredAge1);
			// 最小年龄
			String minInsuredAge1 = tSSRS.GetText(1, 2);
			double minAge1 = Double.parseDouble(minInsuredAge1);

			LDUWUserDB tLDUWUserDB = new LDUWUserDB();
			tLDUWUserDB.setUserCode(mOperator);
			tLDUWUserDB.setUWType("2");
			if (!tLDUWUserDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "LCInsuredBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询LDUWUser表失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			String OverAge = tLDUWUserDB.getOverAge();

			if (OverAge != null && (maxAge1 > maxAge) && (minAge1 < minAge)
					&& (OverAge.equals("0"))) {
				CError tError = new CError();
				tError.moduleName = "LCInsuredBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "被保险人年龄差过规定范围，该核保时没有权限,请选择上报!";
				this.mErrors.addOneError(tError);
				return false;
			}

		}

		return true;
	}

	/**
	 * 校验团体该核保师最低保额的权限 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkLowestAmnt() {
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setGrpContNo(mGrpContNo);
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.query();

		for (int i = 1; i <= tLCPolSet.size(); i++) {
			String tRiskCode = tLCPolSet.get(i).getRiskCode();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i).getSchema();
			String tSql = " select calcode from lmcalmode where 1 =1 "
					+ " and calcode like 'UWA%' and riskcode ='" + tRiskCode
					+ "'" + " and type = '8'";
			ExeSQL tExeSQL = new ExeSQL();
			String mCalCode = tExeSQL.getOneValue(tSql);

			CalBase mCalBase = new CalBase();
			mCalBase.setGet(tLCPolSchema.getAmnt());
			mCalBase.setJob(tLCPolSchema.getOccupationType());
			mCalBase.setPolNo(tLCPolSchema.getPolNo());

			Calculator mCalculator = new Calculator();
			mCalculator.setCalCode(mCalCode);
			// 增加基本要素
			mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
			mCalculator.addBasicFactor("Job", mCalBase.getJob());
			mCalculator.addBasicFactor("Get", mCalBase.getGet());

			String tStr = mCalculator.calculate();

			LDUWUserDB tLDUWUserDB = new LDUWUserDB();
			tLDUWUserDB.setUserCode(mOperator);
			tLDUWUserDB.setUWType("2");
			if (!tLDUWUserDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "LCInsuredBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询LDUWUser表失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			String LowsetAmnt = tLDUWUserDB.getLowestAmnt();

			if (LowsetAmnt != null && tStr != null && !(tStr.equals(""))
					&& !(tStr.equals("0")) && LowsetAmnt.equals("0")) {
				CError tError = new CError();
				tError.moduleName = "LCInsuredBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "被保险最低保额超过规定范围，该核保时没有权限,请选择上报!";
				this.mErrors.addOneError(tError);
				return false;

			}
		}
		return true;

	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("UWFlag", mLCGrpContSchema.getUWFlag());
		logger.debug("UWFlag==" + mLCGrpContSchema.getUWFlag());
		mTransferData.setNameAndValue("GrpContNo", mGrpContNo);
		logger.debug("GrpContNo==" + mLCGrpContSchema.getGrpContNo());
		mTransferData.setNameAndValue("PrtNo", mLCGrpContSchema.getPrtNo());
		logger.debug("PrtNo==" + mLCGrpContSchema.getPrtNo());
		mTransferData.setNameAndValue("AgentCode", mLCGrpContSchema
				.getAgentCode());
		logger.debug("AgentCode==" + mLCGrpContSchema.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", mLCGrpContSchema
				.getAgentGroup());
		logger.debug("AgentGroup==" + mLCGrpContSchema.getAgentGroup());
		mTransferData.setNameAndValue("SaleChnl", mLCGrpContSchema
				.getSaleChnl());
		logger.debug("SaleChnl==" + mLCGrpContSchema.getSaleChnl());
		mTransferData.setNameAndValue("ManageCom", mLCGrpContSchema
				.getManageCom());
		logger.debug("ManageCom==" + mLCGrpContSchema.getManageCom());
		mTransferData.setNameAndValue("GrpNo", mLCGrpContSchema.getAppntNo());
		logger.debug("GrpNo==" + mLCGrpContSchema.getAppntNo());
		mTransferData.setNameAndValue("GrpName", mLCGrpContSchema.getGrpName());
		logger.debug("GrpName==" + mLCGrpContSchema.getGrpName());
		mTransferData.setNameAndValue("CValiDate", mLCGrpContSchema
				.getCValiDate());
		logger.debug("CValiDate==" + mLCGrpContSchema.getCValiDate());
		mTransferData.setNameAndValue("UWDate", mLCGrpContSchema.getUWDate());

		mTransferData.setNameAndValue("UWTime", PubFun.getCurrentTime());

		mTransferData.setNameAndValue("ReDisMark", "0"); // 分保标志 1-分保0不分保

		mTransferData.setNameAndValue("ReportType", mTransferData
				.getValueByName("UWUpReport"));

		mTransferData.setNameAndValue("UWUpReport", mTransferData
				.getValueByName("UWUpReport"));
		logger.debug("核保流向=="
				+ mTransferData.getValueByName("UWUpReport"));

		String tSql = " select missionprop12 from lwmission where 1 =1 "
				+ " and activityid = '0000011004' and missionid = '"
				+ mMissionID + "'" + " and submissionid = '" + mSubMissionID
				+ "'";
		logger.debug("tSql:" + tSql);
		ExeSQL tExeSQL = new ExeSQL();
		String tUWAuthority = tExeSQL.getOneValue(tSql);
		if (tUWAuthority == null || tUWAuthority.equals("")) {
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "核保师级别查询失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		mTransferData.setNameAndValue("UWAuthority", tUWAuthority);
		mTransferData.setNameAndValue("PolApplyDate", mLCGrpContSchema
				.getPolApplyDate());
		mTransferData.setNameAndValue("UserCode", mOperator);
		logger.debug("UWAuthority==" + tUWAuthority);
		mTransferData.setNameAndValue("ApproveDate", mLCGrpContSchema
				.getApproveDate());
		logger.debug("ApproveDate==" + mLCGrpContSchema.getApproveDate());

		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
