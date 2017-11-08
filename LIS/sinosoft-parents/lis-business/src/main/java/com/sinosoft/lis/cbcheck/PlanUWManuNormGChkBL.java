package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPlanUWMasterDB;
import com.sinosoft.lis.db.LCPlanUWSubDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPlanUWMasterSchema;
import com.sinosoft.lis.schema.LCPlanUWSubSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPlanUWMasterSet;
import com.sinosoft.lis.vschema.LCPlanUWSubSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保个人人工核保部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class PlanUWManuNormGChkBL {
private static Logger logger = Logger.getLogger(PlanUWManuNormGChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	/** 数据操作字符串 */
	private String mOperator; // 操作员

	/** 业务处理相关变量 */
	private String mGrpContNo = "";
	private String mContNo = "";
	private String mUWFlag = ""; // 核保标志
	private String mUWIdea = ""; // 核保结论
	private String mContPlanCode = "";
	private String mPlanType = "";
	private String mInsuredNo = "";
	private String mUPUWCode = "";
	private String mAppGrade = ""; // 上报级别
	private String mUWPopedom = "";// 集体层下结论，该个单是否处理
	private String mFlagPos = ""; // 下结论位置：'0' 个单层 '1'总单层面
	private boolean isDeal = true; // 团单层下结论时，该个单是否处理标志
	private LCPlanUWMasterSet mLCPlanUWMasterSet = new LCPlanUWMasterSet();
	private LCPlanUWSubSet mLCPlanUWSubSet = new LCPlanUWSubSet();
	private LCContSchema mLCContSchema = new LCContSchema();

	private double mSumPlanPrem = 0; // 计划下总保费

	private GlobalInput mGlobalInput = new GlobalInput();

	public PlanUWManuNormGChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 得到外部传入的数据,将数据备份到本类中
		logger.debug("---UWManuNormGChkBL getInputData Begin ---");

		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}
		if (!isDeal) {
			// 无需处理则直接返回
			return true;
		}
		// 数据操作业务处理
		logger.debug("数据操作业务处理" + mUWFlag);

		if (!dealData()) {
			return false;
		}

		logger.debug("---UWManuNormGChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();
		logger.debug("---UWManuNormGChkBL prepareOutputData---");

		// 数据提交
		PubSubmit tSubmit = new PubSubmit();
		if (!cOperate.equals("submit")) {

			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "UWManuNormGChkBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);

				return false;
			}
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			buildError("getInputData", "获取前台传输TransferData失败");
			return false;
		}
		mOperator = mGlobalInput.Operator;

		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mUWFlag = (String) mTransferData.getValueByName("UWState");
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		mContPlanCode = (String) mTransferData.getValueByName("ContPlanCode");
		mPlanType = (String) mTransferData.getValueByName("PlanType");
		mInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
		mFlagPos = (String) mTransferData.getValueByName("FlagPos");
		if (mGrpContNo == null || mGrpContNo.equals("")) {
			buildError("getInputData", "获取前台传输公共数据GrpContNo失败");
		}
		if (mContNo == null || mContNo.equals("")) {
			buildError("getInputData", "获取前台传输公共数据ContNo失败");
		}
		if (mUWFlag == null || mUWFlag.equals("")) {
			buildError("getInputData", "获取前台传输公共数据UWFlag失败");
		}
		if (mUWIdea == null || mUWIdea.equals("")) {
			buildError("getInputData", "获取前台传输公共数据UWIdea失败");
		}
		if (mContPlanCode == null || mContPlanCode.equals("")) {
			buildError("getInputData", "获取前台传输公共数据ContPlanCode失败");
		}
		if (mPlanType == null || mPlanType.equals("")) {
			buildError("getInputData", "获取前台传输公共数据PlanType失败");
		}
		if (mInsuredNo == null || mInsuredNo.equals("")) {
			buildError("getInputData", "获取前台传输公共数据InsuredNo失败");
		}
		if (mFlagPos == null || mFlagPos.equals("")) {
			mFlagPos = "0";
		}
		return true;
	}

	private boolean checkData() {
		// 校验合同单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);

		LCContSet tLCContSet = tLCContDB.query();

		if ((tLCContSet == null) || (tLCContSet.size() <= 0)) {
			CError tError = new CError();
			tError.moduleName = "UWGrpManuAddChkBL";
			tError.functionName = "preparePol";
			tError.errorMessage = "合同" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		mLCContSchema = tLCContSet.get(1);
		// 校验被保人产品组合信息

		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		tLCInsuredDB.setContNo(mContNo);
		tLCInsuredDB.setInsuredNo(mInsuredNo);
		tLCInsuredDB.setContPlanCode(mContPlanCode);
		tLCInsuredSet = tLCInsuredDB.query();
		if (tLCInsuredSet.size() != 1) {
			buildError("checkData", "查询保单下被保人信息失败");
		}

		// 校验团单信息
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);

		LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();

		if ((tLCGrpContSet == null) || (tLCGrpContSet.size() <= 0)) {
			CError tError = new CError();
			tError.moduleName = "UWGrpManuAddChkBL";
			tError.functionName = "preparePol";
			tError.errorMessage = "合同" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		if (!checkUWGrade()) {
			return false;
		}
		ExeSQL tExeSQL = new ExeSQL();
		if (mFlagPos.equals("1")) {
			// 在总单层下结论
			if (!mUWFlag.equals("1")) {
				String Sql = "select passflag from lcplanuwmaster where contno='"
						+ "?contno?"
						+ "' and contplancode='"
						+ "?contplancode?"
						+ "' and plantype='"
						+ "?plantype?"
						+ "' and insuredno='"
						+ "?insuredno?" + "'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(Sql);
				sqlbv.put("contno", mContNo);
				sqlbv.put("contplancode", mContPlanCode);
				sqlbv.put("plantype", mPlanType);
				sqlbv.put("insuredno", mInsuredNo);
				String tUWFlag = tExeSQL.getOneValue(sqlbv);
				if (tUWFlag.equals("0") || tUWFlag.equals("5")
						|| tUWFlag.equals("z")) {
					mUWFlag = "9";
				} else {
					isDeal = false; // 若个单层已经下过结论，则直接返回不用处理
					return true;
				}
			}
		} else {
			String Gsql = "select passflag from lcgplanuwmaster where grpcontno='"
					+ "?grpcontno?"
					+ "' and contplancode='"
					+ "?contplancode?"
					+ "' and plantype='" + "?plantype?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(Gsql);
			sqlbv1.put("grpcontno", mGrpContNo);
			sqlbv1.put("contplancode", mContPlanCode);
			sqlbv1.put("plantype", mPlanType);
			String tGUWFlag = tExeSQL.getOneValue(sqlbv1); // 判断
			if (tGUWFlag != null && tGUWFlag.equals("1")) {
				buildError("checkDate", "总单层该计划已拒保，个单层不能再下核保结论");
			}

		}

		return true;
	}

	private boolean checkUWGrade() {
		// 核保师级别校验
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);
		logger.debug("mOperator" + mOperator);

		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		mUWPopedom = tUWPopedom;
		mAppGrade = mUWPopedom;
		logger.debug("tUWPopedom" + tUWPopedom);
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("begin dealData()");
		if (!dealPlanPol()) // 处理该保单下该计划下的各险种
		{
			return false;
		}
		if (preparePlanUW() == false) // 处理产品组合核保结果
		{
			return false;
		}
		if (prepareContUW() == false) // 处理保单层结果
		{
			return false;
		}
		logger.debug("---dealData end---");
		return true;
	}

	// 保单下该产品组合险种处理
	private boolean dealPlanPol() {
		LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
		LCContPlanRiskSet tLCContPlanRiskSet = new LCContPlanRiskSet();
		tLCContPlanRiskDB.setGrpContNo(mGrpContNo);
		tLCContPlanRiskDB.setContPlanCode(mContPlanCode);
		tLCContPlanRiskDB.setPlanType(mPlanType);
		tLCContPlanRiskSet = tLCContPlanRiskDB.query(); // 计划下所有险种信息
		if (tLCContPlanRiskSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "dealOnePol";
			tError.errorMessage = "查询计划[" + mContPlanCode + "]下险种信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLCContPlanRiskSet.size(); i++) {
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolDB.setContNo(mContNo);
			tLCPolDB.setInsuredNo(mInsuredNo);
			tLCPolDB.setRiskCode(tLCContPlanRiskSet.get(i).getRiskCode());
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet.size() != 1) {
				return false;
			}
			if (!dealOnePol(tLCPolSet.get(1))) { // 计划下所有险种处理
				return false;
			}
		}
		return true;
	}

	private boolean dealOnePol(LCPolSchema tLCPolSchema) {
		// 处理险种保单核保结果
		LCPolSchema tPolSchema = new LCPolSchema();
		tPolSchema.setSchema(tLCPolSchema);
		tPolSchema.setUWFlag(mUWFlag);
		tPolSchema.setRemark(mUWIdea);
		tPolSchema.setModifyDate(PubFun.getCurrentDate());
		tPolSchema.setModifyTime(PubFun.getCurrentTime());
		mSumPlanPrem = mSumPlanPrem + tPolSchema.getPrem(); // 保费累加
		// 处理险种层面核保结果
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		tLCUWMasterDB.setContNo(mContNo);
		tLCUWMasterDB.setPolNo(tPolSchema.getPolNo());
		tLCUWMasterSet = tLCUWMasterDB.query();
		if (tLCUWMasterSet.size() != 1) {
			return false;
		}
		tLCUWMasterSchema.setSchema(tLCUWMasterSet.get(1));
		tLCUWMasterSchema.setUWNo(tLCUWMasterSchema.getUWNo() + 1);
		tLCUWMasterSchema.setAppGrade(mAppGrade);
		tLCUWMasterSchema.setPassFlag(mUWFlag);
		tLCUWMasterSchema.setUWIdea(mUWIdea);
		tLCUWMasterSchema.setState(mUWFlag);
		tLCUWMasterSchema.setAutoUWFlag("2");
		tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
		tLCUWSubDB.setGrpContNo(mGrpContNo);
		tLCUWSubDB.setContNo(mContNo);
		tLCUWSubDB.setPolNo(tPolSchema.getPolNo());
		tLCUWSubSet = tLCUWSubDB.query();
		int m = tLCUWSubSet.size();
		if (m > 0) {
			m++; // 核保次数
			tLCUWSubSchema = new LCUWSubSchema();
			tLCUWSubSchema.setUWNo(m); // 第几次核保
			tLCUWSubSchema.setContNo(tLCUWMasterSchema.getContNo());
			tLCUWSubSchema.setPolNo(tLCUWMasterSchema.getPolNo());
			tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
			tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema
					.getProposalContNo());
			tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
			tLCUWSubSchema.setInsuredNo(tLCUWMasterSchema.getInsuredNo());
			tLCUWSubSchema.setInsuredName(tLCUWMasterSchema.getInsuredName());
			tLCUWSubSchema.setAppntNo(tLCUWMasterSchema.getAppntNo());
			tLCUWSubSchema.setAppntName(tLCUWMasterSchema.getAppntName());
			tLCUWSubSchema.setAgentCode(tLCUWMasterSchema.getAgentCode());
			tLCUWSubSchema.setAgentGroup(tLCUWMasterSchema.getAgentGroup());
			tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
			tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
			tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
			tLCUWSubSchema.setState(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPassFlag(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPostponeDay(tLCUWMasterSchema.getPostponeDay());
			tLCUWSubSchema.setPostponeDate(tLCUWMasterSchema.getPostponeDate());
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
			return false;
		}
		mMap.put(tLCPolSchema, "UPDATE");
		mMap.put(tLCUWMasterSchema, "DELETE&INSERT");
		mMap.put(tLCUWSubSchema, "INSERT");

		return true;
	}

	/**
	 * 产品组合核保结论处理 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePlanUW() {
		LCPlanUWMasterSchema tLCPlanUWMasterSchema = new LCPlanUWMasterSchema();
		LCPlanUWMasterDB tLCPlanUWMasterDB = new LCPlanUWMasterDB();
		tLCPlanUWMasterDB.setGrpContNo(mGrpContNo);
		tLCPlanUWMasterDB.setContNo(mContNo);
		tLCPlanUWMasterDB.setInsuredNo(mInsuredNo);
		tLCPlanUWMasterDB.setContPlanCode(mContPlanCode);
		tLCPlanUWMasterDB.setPlanType(mPlanType);

		LCPlanUWMasterSet tLCPlanUWMasterSet = new LCPlanUWMasterSet();
		tLCPlanUWMasterSet = tLCPlanUWMasterDB.query();

		if (tLCPlanUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPlanUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCPlanUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int n = tLCPlanUWMasterSet.size();
		logger.debug("该投保单的核保主表记录条数:   " + n);

		if (n == 1) {
			tLCPlanUWMasterSchema = tLCPlanUWMasterSet.get(1);
			// 人工核保后uwno加一
			int uwno = tLCPlanUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCPlanUWMasterSchema.setUWNo(uwno);

			tLCPlanUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCPlanUWMasterSchema.setState(mUWFlag);
			tLCPlanUWMasterSchema.setUWIdea(mUWIdea);
			tLCPlanUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保

			tLCPlanUWMasterSchema.setAppGrade(mAppGrade);

			if (mUWFlag.equals("6")) {
				tLCPlanUWMasterSchema.setOperator(mUPUWCode); // 上报核保指定核保师功能的实现借助将当前核保师改为待核保师方式实现
			} else {
				tLCPlanUWMasterSchema.setOperator(mOperator);
			}
			// 操作员

			tLCPlanUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCPlanUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPlanUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCPlanUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCPlanUWMasterSet.clear();
		mLCPlanUWMasterSet.add(tLCPlanUWMasterSchema);
		mMap.put(mLCPlanUWMasterSet, "DELETE&INSERT");
		// 核保轨迹表
		LCPlanUWSubSchema tLCPlanUWSubSchema = new LCPlanUWSubSchema();
		LCPlanUWSubDB tLCPlanUWSubDB = new LCPlanUWSubDB();
		tLCPlanUWSubDB.setGrpContNo(mGrpContNo);
		tLCPlanUWSubDB.setContPlanCode(mContPlanCode);
		tLCPlanUWSubDB.setPlanType(mPlanType);

		LCPlanUWSubSet tLCPlanUWSubSet = new LCPlanUWSubSet();
		tLCPlanUWSubSet = tLCPlanUWSubDB.query();

		if (tLCPlanUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPlanUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int m = tLCPlanUWSubSet.size();
		logger.debug("subcount=" + m);

		if (m > 0) {
			m++; // 核保次数
			tLCPlanUWSubSchema = new LCPlanUWSubSchema();
			tLCPlanUWSubSchema.setUWNo(m); // 第几次核保
			tLCPlanUWSubSchema.setGrpContNo(mGrpContNo);
			tLCPlanUWSubSchema.setContNo(mContNo);
			tLCPlanUWSubSchema.setContPlanCode(mContPlanCode);
			tLCPlanUWSubSchema.setPlanType(mPlanType);
			tLCPlanUWSubSchema.setInsuredNo(mInsuredNo);
			tLCPlanUWSubSchema.setProposalContNo(tLCPlanUWMasterSchema
					.getProposalContNo());
			tLCPlanUWSubSchema.setInsuredName(tLCPlanUWMasterSchema
					.getInsuredName());
			tLCPlanUWSubSchema.setAppntNo(tLCPlanUWMasterSchema.getAppntNo());
			tLCPlanUWSubSchema.setAppntName(tLCPlanUWMasterSchema
					.getAppntName());
			tLCPlanUWSubSchema.setAgentCode(tLCPlanUWMasterSchema
					.getAgentCode());
			tLCPlanUWSubSchema.setAgentGroup(tLCPlanUWMasterSchema
					.getAgentGroup());
			tLCPlanUWSubSchema.setAutoUWFlag("2");
			tLCPlanUWSubSchema.setPassFlag(tLCPlanUWMasterSchema.getPassFlag());
			tLCPlanUWSubSchema.setState(tLCPlanUWMasterSchema.getState());
			tLCPlanUWSubSchema.setUWGrade(tLCPlanUWMasterSchema.getUWGrade());
			tLCPlanUWSubSchema.setAppGrade(tLCPlanUWMasterSchema.getAppGrade());
			tLCPlanUWSubSchema.setHealthFlag(tLCPlanUWMasterSchema
					.getHealthFlag());
			tLCPlanUWSubSchema.setSpecFlag(tLCPlanUWMasterSchema.getSpecFlag());
			tLCPlanUWSubSchema.setQuesFlag(tLCPlanUWMasterSchema.getQuesFlag());
			tLCPlanUWSubSchema.setReportFlag(tLCPlanUWMasterSchema
					.getReportFlag());
			tLCPlanUWSubSchema.setChangePolFlag(tLCPlanUWMasterSchema
					.getChangePolFlag());
			tLCPlanUWSubSchema.setPrintFlag(tLCPlanUWMasterSchema
					.getPrintFlag());
			tLCPlanUWSubSchema.setOperator(tLCPlanUWMasterSchema.getOperator()); // 操作员
			tLCPlanUWSubSchema.setMakeDate(tLCPlanUWMasterSchema.getMakeDate());
			tLCPlanUWSubSchema.setMakeTime(tLCPlanUWMasterSchema.getMakeTime());
			tLCPlanUWSubSchema.setModifyDate(tLCPlanUWMasterSchema
					.getModifyDate());
			tLCPlanUWSubSchema.setModifyTime(tLCPlanUWMasterSchema
					.getModifyTime());

		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPlanUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCPlanUWSubSet.clear();
		mLCPlanUWSubSet.add(tLCPlanUWSubSchema);
		mMap.put(mLCPlanUWSubSet, "INSERT");
		return true;

	}

	/*
	 * 保单层核保结果处理
	 */

	private boolean prepareContUW() {
		String tContPassFlag = "";
		tContPassFlag = mUWFlag;
		if (!mUWFlag.equals("1") && !mUWFlag.equals("9")) {
			tContPassFlag = "4";
		}
		mLCContSchema.setUWFlag(tContPassFlag);
		mLCContSchema.setUWOperator(mOperator);
		mLCContSchema.setUWDate(PubFun.getCurrentDate());
		mLCContSchema.setUWTime(PubFun.getCurrentTime());
		mLCContSchema.setOperator(mOperator);
		mLCContSchema.setModifyDate(PubFun.getCurrentDate());
		mLCContSchema.setModifyTime(PubFun.getCurrentTime());

		if (mUWFlag.equals("1")) {
			// 拒保，保单应减去产品组合相应保费
			// tLCContSchema.setPrem(tLCContSchema.getPrem() - mSumPlanPrem);
		}
		mMap.put(mLCContSchema, "UPDATE");
		prepareSingleUWMaster(mContNo, tContPassFlag);

		return true;
	}

	private boolean prepareSingleUWMaster(String tContNo, String tUWFlag) {
		int m = 0;
		int n = 0;
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(tContNo);
		tLCCUWMasterDB.setProposalContNo(tContNo);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();

		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		n = tLCCUWMasterSet.size();
		logger.debug("该投保单的核保主表记录条数:   " + n);

		if (n == 1) {
			tLCCUWMasterSchema = tLCCUWMasterSet.get(1);
			// 人工核保后uwno加一 add by heyq 2005-1-3
			int uwno = tLCCUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCCUWMasterSchema.setUWNo(uwno);
			tLCCUWMasterSchema.setPassFlag(tUWFlag); // 通过标志
			tLCCUWMasterSchema.setState(tUWFlag);
			tLCCUWMasterSchema.setUWIdea(mUWIdea);
			tLCCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保

			// tLCCUWMasterSchema.setUWGrade(mUWPopedom);
			tLCCUWMasterSchema.setAppGrade(mAppGrade);

			if (mUWFlag.equals("6")) {
				tLCCUWMasterSchema.setOperator(mUPUWCode); // 上报核保指定核保师功能的实现借助将当前核保师改为待核保师方式实现
			} else {
				tLCCUWMasterSchema.setOperator(mOperator);
			}
			// 操作员

			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

			if (mUWFlag.equals("3")) {
				if (tLCCUWMasterSchema.getPrintFlag().equals("1")) {
					CError tError = new CError();
					tError.moduleName = "UWManuNormGChkBL";
					tError.functionName = "prepareUW";
					tError.errorMessage = "已经发核保通知不可录入!";
					this.mErrors.addOneError(tError);

					return false;
				}

				tLCCUWMasterSchema.setSpecFlag("1");
			}
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(tContNo);

		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();

		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		m = tLCCUWSubSet.size();
		logger.debug("subcount=" + m);

		if (m > 0) {
			m++; // 核保次数
			tLCCUWSubSchema = new LCCUWSubSchema();
			tLCCUWSubSchema.setUWNo(m); // 第几次核保
			tLCCUWSubSchema.setContNo(tLCCUWMasterSchema.getContNo());
			tLCCUWSubSchema.setGrpContNo(tLCCUWMasterSchema.getGrpContNo());
			tLCCUWSubSchema.setProposalContNo(tLCCUWMasterSchema
					.getProposalContNo());
			tLCCUWSubSchema.setInsuredNo(tLCCUWMasterSchema.getInsuredNo());
			tLCCUWSubSchema.setInsuredName(tLCCUWMasterSchema.getInsuredName());
			tLCCUWSubSchema.setAppntNo(tLCCUWMasterSchema.getAppntNo());
			tLCCUWSubSchema.setAppntName(tLCCUWMasterSchema.getAppntName());
			tLCCUWSubSchema.setAgentCode(tLCCUWMasterSchema.getAgentCode());
			tLCCUWSubSchema.setAgentGroup(tLCCUWMasterSchema.getAgentGroup());
			tLCCUWSubSchema.setUWGrade(tLCCUWMasterSchema.getUWGrade()); // 核保级别
			tLCCUWSubSchema.setAppGrade(tLCCUWMasterSchema.getAppGrade()); // 申请级别
			tLCCUWSubSchema.setAutoUWFlag(tLCCUWMasterSchema.getAutoUWFlag());
			tLCCUWSubSchema.setState(tLCCUWMasterSchema.getState());
			tLCCUWSubSchema.setPassFlag(tLCCUWMasterSchema.getState());
			tLCCUWSubSchema.setPostponeDay(tLCCUWMasterSchema.getPostponeDay());
			tLCCUWSubSchema.setPostponeDate(tLCCUWMasterSchema
					.getPostponeDate());
			tLCCUWSubSchema.setUpReportContent(tLCCUWMasterSchema
					.getUpReportContent());
			tLCCUWSubSchema.setHealthFlag(tLCCUWMasterSchema.getHealthFlag());
			tLCCUWSubSchema.setSpecFlag(tLCCUWMasterSchema.getSpecFlag());
			tLCCUWSubSchema.setSpecReason(tLCCUWMasterSchema.getSpecReason());
			tLCCUWSubSchema.setQuesFlag(tLCCUWMasterSchema.getQuesFlag());
			tLCCUWSubSchema.setReportFlag(tLCCUWMasterSchema.getReportFlag());
			tLCCUWSubSchema.setChangePolFlag(tLCCUWMasterSchema
					.getChangePolFlag());
			tLCCUWSubSchema.setChangePolReason(tLCCUWMasterSchema
					.getChangePolReason());
			tLCCUWSubSchema.setAddPremReason(tLCCUWMasterSchema
					.getAddPremReason());
			tLCCUWSubSchema.setPrintFlag(tLCCUWMasterSchema.getPrintFlag());
			tLCCUWSubSchema.setPrintFlag2(tLCCUWMasterSchema.getPrintFlag2());
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
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mMap.put(tLCCUWMasterSchema, "DELETE&INSERT");
		mMap.put(tLCCUWSubSchema, "INSERT");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mResult.clear();
		mResult.add(mMap);
	}

	// 返回结果集
	public VData getResult() {
		return mResult;
	}

	/**
	 * 错误处理
	 * 
	 * @param cFunc
	 *            String 出错函数名称
	 * @param cErrMsg
	 *            String 出错信息
	 */
	private void buildError(String cFunc, String cErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PlanUWManuNormGChkBL";
		cError.functionName = cFunc;
		cError.errorMessage = cErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String args[]) {
		GlobalInput tb = new GlobalInput();
		tb.ManageCom = "86210000";
		tb.ComCode = "8621000";
		tb.Operator = "001";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", "48795000007851");
		tTransferData.setNameAndValue("ContNo", "130210000012483");
		tTransferData.setNameAndValue("InsuredNo", "0014731270");
		tTransferData.setNameAndValue("ContPlanCode", "21000105");
		tTransferData.setNameAndValue("PlanType", "6");
		tTransferData.setNameAndValue("UWState", "j");
		tTransferData.setNameAndValue("UWIdea", "你是少数人地继有协工");
		VData tVData = new VData();
		tVData.add(tb);
		tVData.add(tTransferData);
		PlanUWManuNormGChkUI tGrpPlanUWManuNormGChkUI = new PlanUWManuNormGChkUI();
		if (!tGrpPlanUWManuNormGChkUI.submitData(tVData, "")) {
			logger.debug(tGrpPlanUWManuNormGChkUI.mErrors);
		}

	}

}
