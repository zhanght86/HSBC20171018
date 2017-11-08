package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGPlanUWMasterDB;
import com.sinosoft.lis.db.LCGPlanUWSubDB;
import com.sinosoft.lis.db.LCGUWMasterDB;
import com.sinosoft.lis.db.LCGUWSubDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDUWGradeDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGPlanUWMasterSchema;
import com.sinosoft.lis.schema.LCGPlanUWSubSchema;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.schema.LCGUWSubSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCGPlanUWMasterSet;
import com.sinosoft.lis.vschema.LCGPlanUWSubSet;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.vschema.LCGUWSubSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LDUWGradeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class GrpPlanUWManuNormGChkBL {
private static Logger logger = Logger.getLogger(GrpPlanUWManuNormGChkBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	// 核保主表
	private LCGPlanUWMasterSet mLCGPlanUWMasterSet = new LCGPlanUWMasterSet();
	// 集体核保轨迹表
	private LCGPlanUWSubSet mLCGPlanUWSubSet = new LCGPlanUWSubSet();
	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mUWFlag = ""; // 核保标志
	private String mUWIdea = ""; // 核保结论
	private String mGrpContNo = ""; // 团单号
	private String mContPlanCode = ""; // 计划编码
	private String mPlanType = ""; // 计划类别
	private String mAppGrade = ""; // 上报级别
	private String mUPUWCode = "";

	public GrpPlanUWManuNormGChkBL() {
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

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		// MMap map = new MMap();
		mMap.put(mLCGPlanUWMasterSet, "DELETE&INSERT");
		mMap.put(mLCGPlanUWSubSet, "INSERT");
		mResult.clear();
		mResult.add(mMap);
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
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		mOperator = mGlobalInput.Operator;
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		mContPlanCode = (String) mTransferData.getValueByName("ContPlanCode");
		mPlanType = (String) mTransferData.getValueByName("PlanType");
		mUWFlag = (String) mTransferData.getValueByName("GPlanUWState");
		mUWIdea = (String) mTransferData.getValueByName("GPlanUWIdea");
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (!checkUWGrade()) {
			return false;
		}
		return true;
	}

	/**
	 * 校验该核保师是否有权限下次核保结论 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);
		logger.debug("mOperator" + mOperator);

		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		mAppGrade = tUWPopedom;

		LDUWGradeDB tLDUWGradeDB = new LDUWGradeDB();
		tLDUWGradeDB.setUserCode(mOperator);
		tLDUWGradeDB.setUWState(mUWFlag);
		tLDUWGradeDB.setUWType("2");
		LDUWGradeSet tLDUWGradeSet = new LDUWGradeSet();
		tLDUWGradeSet = tLDUWGradeDB.query();
		logger.debug("tLDUWGradeSet.size():" + tLDUWGradeSet.size());
		if (tLDUWGradeSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "checkData";
			tError.errorMessage = "核保师:" + mOperator + "没有权限下" + mUWFlag
					+ "核保结论!";
			this.mErrors.addOneError(tError);
			return false;

		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (dealSingleUW() == false) {
			// 个单层计划核保处理
			return false;
		}
		if (dealPlanPol() == false) {
			// 处理产品组合下的险种信息
			return false;
		}
		// 产品组合核保信息
		if (preparePlanUW() == false) {
			return false;
		}

		return true;
	}

	private boolean dealSingleUW() {
		// 处理个单层核保结果
		String sql = "select contno,insuredno from lcinsured where grpcontno='"
				+ mGrpContNo + "' and contplancode='" + mContPlanCode + "'";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sql);
		if (tSSRS.getMaxRow() == 0) {
			return false;
		}
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			String tContNo = tSSRS.GetText(i, 1);
			String tInsuredNo = tSSRS.GetText(i, 2);
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GrpContNo", mGrpContNo);
			tTransferData.setNameAndValue("ContNo", tContNo);
			tTransferData.setNameAndValue("InsuredNo", tInsuredNo);
			tTransferData.setNameAndValue("ContPlanCode", mContPlanCode);
			tTransferData.setNameAndValue("PlanType", mPlanType);
			tTransferData.setNameAndValue("UWState", mUWFlag);
			tTransferData.setNameAndValue("UWIdea", mUWIdea);
			tTransferData.setNameAndValue("FlagPos", "1");
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);
			PlanUWManuNormGChkBL tGrpPlanUWManuNormGChkBL = new PlanUWManuNormGChkBL();
			if (!tGrpPlanUWManuNormGChkBL.submitData(tVData, "")) {
				mErrors.copyAllErrors(tGrpPlanUWManuNormGChkBL.mErrors);
			}

		}
		return true;
	}

	// 处理产品组合下的险种信息
	private boolean dealPlanPol() {
		LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
		LCContPlanRiskSet tLCContPlanRiskSet = new LCContPlanRiskSet();
		tLCContPlanRiskDB.setGrpContNo(mGrpContNo);
		tLCContPlanRiskDB.setContPlanCode(mContPlanCode);
		tLCContPlanRiskDB.setPlanType(mPlanType);
		tLCContPlanRiskSet = tLCContPlanRiskDB.query();
		if (tLCContPlanRiskSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "dealOnePol";
			tError.errorMessage = "查询计划[" + mContPlanCode + "]下险种信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLCContPlanRiskSet.size(); i++) {
			if (!dealOnePol(tLCContPlanRiskSet.get(i).getRiskCode())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 处理产品组合下的险种
	 */
	private boolean dealOnePol(String tRiskCode) {
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		tLCGrpPolDB.setGrpContNo(mGrpContNo);
		tLCGrpPolDB.setRiskCode(tRiskCode);
		tLCGrpPolSet = tLCGrpPolDB.query();
		if (tLCGrpPolSet.size() != 1) {
			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "dealOnePol";
			tError.errorMessage = "查询团单下险种[" + tRiskCode + "]失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		tLCGrpPolSchema = tLCGrpPolSet.get(1);
		tLCGrpPolSchema.setUWFlag(mUWFlag);
		tLCGrpPolSchema.setUWOperator(mOperator);
		tLCGrpPolSchema.setUWDate(PubFun.getCurrentDate());
		tLCGrpPolSchema.setUWTime(PubFun.getCurrentTime());
		tLCGrpPolSchema.setOperator(mOperator);
		tLCGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
		tLCGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
		tLCGrpPolSchema.setUWDate(PubFun.getCurrentDate());

		LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
		LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB();
		tLCGUWMasterDB.setGrpPolNo(tLCGrpPolSet.get(1).getGrpPolNo());

		LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
		tLCGUWMasterSet = tLCGUWMasterDB.query();

		if (tLCGUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int n = tLCGUWMasterSet.size();
		logger.debug("该投保单的核保主表记录条数:   " + n);

		if (n == 1) {
			tLCGUWMasterSchema = tLCGUWMasterSet.get(1);
			// 人工核保后uwno加一
			int uwno = tLCGUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCGUWMasterSchema.setUWNo(uwno);

			tLCGUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCGUWMasterSchema.setState(mUWFlag);
			tLCGUWMasterSchema.setUWIdea(mUWIdea);
			tLCGUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保

			tLCGUWMasterSchema.setAppGrade(mAppGrade);

			if (mUWFlag.equals("6")) {
				tLCGUWMasterSchema.setOperator(mUPUWCode); // 上报核保指定核保师功能的实现借助将当前核保师改为待核保师方式实现
			} else {
				tLCGUWMasterSchema.setOperator(mOperator);
			}
			// 操作员

			tLCGUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);

			return false;
		}
		// 核保轨迹表
		LCGUWSubSchema tLCGUWSubSchema = new LCGUWSubSchema();
		LCGUWSubDB tLCGUWSubDB = new LCGUWSubDB();
		tLCGUWSubDB.setGrpPolNo(tLCGrpPolSet.get(1).getGrpPolNo());

		LCGUWSubSet tLCGUWSubSet = new LCGUWSubSet();
		tLCGUWSubSet = tLCGUWSubDB.query();

		if (tLCGUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int m = tLCGUWSubSet.size();
		logger.debug("subcount=" + m);

		if (m > 0) {
			m++; // 核保次数
			tLCGUWSubSchema = new LCGUWSubSchema();
			tLCGUWSubSchema.setUWNo(m); // 第几次核保
			tLCGUWSubSchema.setGrpPolNo(tLCGUWMasterSchema.getGrpPolNo());
			tLCGUWSubSchema.setGrpProposalNo(tLCGUWMasterSchema
					.getGrpProposalNo());
			tLCGUWSubSchema.setGrpContNo(tLCGUWMasterSchema.getGrpContNo());
			tLCGUWSubSchema.setProposalGrpContNo(tLCGUWMasterSchema
					.getGrpContNo());
			tLCGUWSubSchema.setInsuredNo(tLCGUWMasterSchema.getInsuredNo());
			tLCGUWSubSchema.setInsuredName(tLCGUWMasterSchema.getInsuredName());
			tLCGUWSubSchema.setAppntNo(tLCGUWMasterSchema.getAppntNo());
			tLCGUWSubSchema.setAppntName(tLCGUWMasterSchema.getAppntName());
			tLCGUWSubSchema.setAgentCode(tLCGUWMasterSchema.getAgentCode());
			tLCGUWSubSchema.setAgentGroup(tLCGUWMasterSchema.getAgentGroup());
			tLCGUWSubSchema.setUWGrade(tLCGUWMasterSchema.getUWGrade()); // 核保级别
			tLCGUWSubSchema.setAppGrade(tLCGUWMasterSchema.getAppGrade()); // 申请级别
			tLCGUWSubSchema.setAutoUWFlag(tLCGUWMasterSchema.getAutoUWFlag());
			tLCGUWSubSchema.setState(tLCGUWMasterSchema.getState());
			tLCGUWSubSchema.setPassFlag(tLCGUWMasterSchema.getState());
			tLCGUWSubSchema.setPostponeDay(tLCGUWMasterSchema.getPostponeDay());
			tLCGUWSubSchema.setPostponeDate(tLCGUWMasterSchema
					.getPostponeDate());
			tLCGUWSubSchema.setUpReportContent(tLCGUWMasterSchema
					.getUpReportContent());
			tLCGUWSubSchema.setHealthFlag(tLCGUWMasterSchema.getHealthFlag());
			tLCGUWSubSchema.setSpecFlag(tLCGUWMasterSchema.getSpecFlag());
			tLCGUWSubSchema.setSpecReason(tLCGUWMasterSchema.getSpecReason());
			tLCGUWSubSchema.setQuesFlag(tLCGUWMasterSchema.getQuesFlag());
			tLCGUWSubSchema.setReportFlag(tLCGUWMasterSchema.getReportFlag());
			tLCGUWSubSchema.setChangePolFlag(tLCGUWMasterSchema
					.getChangePolFlag());
			tLCGUWSubSchema.setChangePolReason(tLCGUWMasterSchema
					.getChangePolReason());
			tLCGUWSubSchema.setAddPremReason(tLCGUWMasterSchema
					.getAddPremReason());
			tLCGUWSubSchema.setPrintFlag(tLCGUWMasterSchema.getPrintFlag());
			tLCGUWSubSchema.setPrintFlag2(tLCGUWMasterSchema.getPrintFlag2());
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
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		mMap.put(tLCGrpPolSchema, "UPDATE");
		mMap.put(tLCGUWMasterSchema, "DELETE&INSERT");
		mMap.put(tLCGUWSubSchema, "INSERT");

		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePlanUW() {

		// 产品组合集体核保结果处理
		LCGPlanUWMasterSchema tLCGPlanUWMasterSchema = new LCGPlanUWMasterSchema();
		LCGPlanUWMasterDB tLCGPlanUWMasterDB = new LCGPlanUWMasterDB();
		tLCGPlanUWMasterDB.setGrpContNo(mGrpContNo);
		tLCGPlanUWMasterDB.setContPlanCode(mContPlanCode);
		tLCGPlanUWMasterDB.setPlanType(mPlanType);

		LCGPlanUWMasterSet tLCGPlanUWMasterSet = new LCGPlanUWMasterSet();
		tLCGPlanUWMasterSet = tLCGPlanUWMasterDB.query();

		if (tLCGPlanUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGPlanUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGPlanUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int n = tLCGPlanUWMasterSet.size();
		logger.debug("该投保单的核保主表记录条数:   " + n);

		if (n == 1) {
			tLCGPlanUWMasterSchema = tLCGPlanUWMasterSet.get(1);
			// 人工核保后uwno加一
			int uwno = tLCGPlanUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCGPlanUWMasterSchema.setUWNo(uwno);

			tLCGPlanUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCGPlanUWMasterSchema.setState(mUWFlag);
			tLCGPlanUWMasterSchema.setUWIdea(mUWIdea);
			tLCGPlanUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保

			tLCGPlanUWMasterSchema.setAppGrade(mAppGrade);

			if (mUWFlag.equals("6")) {
				tLCGPlanUWMasterSchema.setOperator(mUPUWCode); // 上报核保指定核保师功能的实现借助将当前核保师改为待核保师方式实现
			} else {
				tLCGPlanUWMasterSchema.setOperator(mOperator);
			}
			// 操作员

			tLCGPlanUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGPlanUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGPlanUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCGPlanUWMasterSet.clear();
		mLCGPlanUWMasterSet.add(tLCGPlanUWMasterSchema);

		// 核保轨迹表
		LCGPlanUWSubSchema tLCGPlanUWSubSchema = new LCGPlanUWSubSchema();
		LCGPlanUWSubDB tLCGPlanUWSubDB = new LCGPlanUWSubDB();
		tLCGPlanUWSubDB.setGrpContNo(mGrpContNo);
		tLCGPlanUWSubDB.setContPlanCode(mContPlanCode);
		tLCGPlanUWSubDB.setPlanType(mPlanType);

		LCGPlanUWSubSet tLCGPlanUWSubSet = new LCGPlanUWSubSet();
		tLCGPlanUWSubSet = tLCGPlanUWSubDB.query();

		if (tLCGPlanUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGPlanUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int m = tLCGPlanUWSubSet.size();
		logger.debug("subcount=" + m);

		if (m > 0) {
			m++; // 核保次数
			tLCGPlanUWSubSchema = new LCGPlanUWSubSchema();
			tLCGPlanUWSubSchema.setUWNo(m); // 第几次核保
			tLCGPlanUWSubSchema.setGrpContNo(tLCGPlanUWMasterSchema
					.getGrpContNo());
			tLCGPlanUWSubSchema.setProposalGrpContNo(tLCGPlanUWMasterSchema
					.getProposalGrpContNo());
			tLCGPlanUWSubSchema.setContPlanCode(mContPlanCode);
			tLCGPlanUWSubSchema.setPlanType(mPlanType);
			tLCGPlanUWSubSchema.setInsuredNo(tLCGPlanUWMasterSchema
					.getInsuredNo());
			tLCGPlanUWSubSchema.setInsuredName(tLCGPlanUWMasterSchema
					.getInsuredName());
			tLCGPlanUWSubSchema.setAppntNo(tLCGPlanUWMasterSchema.getAppntNo());
			tLCGPlanUWSubSchema.setAppntName(tLCGPlanUWMasterSchema
					.getAppntName());
			tLCGPlanUWSubSchema.setAgentCode(tLCGPlanUWMasterSchema
					.getAgentCode());
			tLCGPlanUWSubSchema.setAgentGroup(tLCGPlanUWMasterSchema
					.getAgentGroup());
			tLCGPlanUWSubSchema.setAutoUWFlag("2");
			tLCGPlanUWSubSchema.setPassFlag(tLCGPlanUWMasterSchema
					.getPassFlag());
			tLCGPlanUWSubSchema.setState(tLCGPlanUWMasterSchema.getState());
			tLCGPlanUWSubSchema.setUWGrade(tLCGPlanUWMasterSchema.getUWGrade());
			tLCGPlanUWSubSchema.setAppGrade(tLCGPlanUWMasterSchema
					.getAppGrade());
			tLCGPlanUWSubSchema.setHealthFlag(tLCGPlanUWMasterSchema
					.getHealthFlag());
			tLCGPlanUWSubSchema.setSpecFlag(tLCGPlanUWMasterSchema
					.getSpecFlag());
			tLCGPlanUWSubSchema.setQuesFlag(tLCGPlanUWMasterSchema
					.getQuesFlag());
			tLCGPlanUWSubSchema.setReportFlag(tLCGPlanUWMasterSchema
					.getReportFlag());
			tLCGPlanUWSubSchema.setChangePolFlag(tLCGPlanUWMasterSchema
					.getChangePolFlag());
			tLCGPlanUWSubSchema.setPrintFlag(tLCGPlanUWMasterSchema
					.getPrintFlag());
			tLCGPlanUWSubSchema.setOperator(tLCGPlanUWMasterSchema
					.getOperator()); // 操作员
			tLCGPlanUWSubSchema.setMakeDate(tLCGPlanUWMasterSchema
					.getMakeDate());
			tLCGPlanUWSubSchema.setMakeTime(tLCGPlanUWMasterSchema
					.getMakeTime());
			tLCGPlanUWSubSchema.setModifyDate(tLCGPlanUWMasterSchema
					.getModifyDate());
			tLCGPlanUWSubSchema.setModifyTime(tLCGPlanUWMasterSchema
					.getModifyTime());

		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGPlanUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpPlanUWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCGPlanUWSubSet.clear();
		mLCGPlanUWSubSet.add(tLCGPlanUWSubSchema);

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

	public static void main(String args[]) {
		GlobalInput tb = new GlobalInput();
		tb.ManageCom = "86210000";
		tb.ComCode = "8621000";
		tb.Operator = "001";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", "48795000006982");
		tTransferData.setNameAndValue("ContPlanCode", "86000097");
		tTransferData.setNameAndValue("PlanType", "6");
		tTransferData.setNameAndValue("GPlanUWState", "9");
		tTransferData.setNameAndValue("GPlanUWIdea", "你是少数人地继有协工");
		VData tVData = new VData();
		tVData.add(tb);
		tVData.add(tTransferData);
		GrpPlanUWManuNormGChkUI tGrpPlanUWManuNormGChkUI = new GrpPlanUWManuNormGChkUI();
		if (!tGrpPlanUWManuNormGChkUI.submitData(tVData, "")) {
			logger.debug(tGrpPlanUWManuNormGChkUI.mErrors);
		}

	}

}
