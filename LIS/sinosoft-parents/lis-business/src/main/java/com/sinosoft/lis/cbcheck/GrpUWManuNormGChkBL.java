package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGUWMasterDB;
import com.sinosoft.lis.db.LCGUWSubDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDUWGradeDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.schema.LCGUWSubSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.vschema.LCGUWSubSet;
import com.sinosoft.lis.vschema.LDUWGradeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团单在人工核保时为集体险种下核保结论
 * </p>
 * <p>
 * Description: 团单在人工核保时为集体险种下核保结论
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */

public class GrpUWManuNormGChkBL {
private static Logger logger = Logger.getLogger(GrpUWManuNormGChkBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 集体保单险种表
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();

	// 核保主表
	private LCGUWMasterSet mLCGUWMasterSet = new LCGUWMasterSet();
	// 集体核保轨迹表
	private LCGUWSubSet mLCGUWSubSet = new LCGUWSubSet();
	private MMap mMap;

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mUWFlag = ""; // 核保标志
	private String mUWIdea = ""; // 核保结论
	private String mGrpPolNo = ""; // 集体险种保单号
	private String mAppGrade = ""; // 上报级别
	private String mUPUWCode = "";

	public GrpUWManuNormGChkBL() {
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
		MMap map = new MMap();
		map.put(mLCGrpPolSchema, "UPDATE");
		map.put(mLCGUWMasterSet, "DELETE&INSERT");
		map.put(mLCGUWSubSet, "INSERT");
		map.add(this.mMap);
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验该投保单是否已复核过,是则可进行核保,否则不能进行
		if (!checkApprove()) {
			return false;
		}
		if (!checkUWGrade()) {
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
		String tStandbyFlag2 = "";
		String tStandbyFlag3 = "";
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpPolDB.mErrors );
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
			// this.mErrors.copyAllErrors( tLCGrpPolDB.mErrors );
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
			// this.mErrors.copyAllErrors( tLCGrpPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 取投保单

		mLCGrpPolSchema.setSchema((LCGrpPolSchema) cInputData
				.getObjectByObjectName("LCGrpPolSchema", 0));

		mUWFlag = mLCGrpPolSchema.getUWFlag();
		mGrpPolNo = mLCGrpPolSchema.getGrpPolNo();
		mUWIdea = mLCGrpPolSchema.getRemark();

		if (mLCGrpPolSchema.getStandbyFlag2() != null
				&& !"".equals(mLCGrpPolSchema.getStandbyFlag2())) {
			tStandbyFlag2 = mLCGrpPolSchema.getStandbyFlag2();
		}
		if (mLCGrpPolSchema.getStandbyFlag3() != null
				&& !"".equals(mLCGrpPolSchema.getStandbyFlag3())) {
			tStandbyFlag3 = mLCGrpPolSchema.getStandbyFlag3();
		}

		// 校验是不是以下核保结论
		if ((mUWFlag == null) || mUWFlag.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有选择核保结论";
			this.mErrors.addOneError(tError);

			return false;
		}

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(mGrpPolNo);
		if (tLCGrpPolDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = mGrpPolNo + "投保单查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			mLCGrpPolSchema.setSchema(tLCGrpPolDB);
		}

		if (tStandbyFlag3 != null && !"".equals(tStandbyFlag3)) {
			mLCGrpPolSchema.setStandbyFlag3(tStandbyFlag3);
		}
		if (tStandbyFlag2 != null && !"".equals(tStandbyFlag2)) {
			mLCGrpPolSchema.setStandbyFlag2(tStandbyFlag2);
		}

		// -----------------------------Beg
		// 检验个单是否核保通过
		// add by:chenrong
		// date:2006.5.29
		// if (!mUWFlag.equals("1")){
		// String tSQL = "";
		// ExeSQL tExeSQL = new ExeSQL();
		// tSQL = " select distinct 1 from lcpol where 1=1"
		// + " and GrpPolNo = '" + mLCGrpPolSchema.getGrpPolNo() + "'"
		// + " and uwflag not in('j','t','9','x')";
		// String tNotUW = tExeSQL.getOneValue(tSQL);
		// if (tNotUW.equals("1")) {
		// CError tError = new CError();
		// tError.moduleName = "UWAutoHealthAfterInitService";
		// tError.functionName = "checkData";
		// tError.errorMessage = "集体险种单" + mLCGrpPolSchema.getGrpPolNo() +
		// "下存在核保未通过的个单,请查看!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		// ----------------------------End

		return true;
	}

	/**
	 * 校验投保单是否复核 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkApprove() {
		if ((mLCGrpPolSchema.getApproveCode() == null)
				|| mLCGrpPolSchema.getApproveDate().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号：" + mGrpPolNo.trim()
					+ "）";
			this.mErrors.addOneError(tError);

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
			tError.moduleName = "UWManuNormGChkBL";
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
		if (preparePol() == false) {
			return false;
		}

		// 核保信息
		if (prepareUW() == false) {
			return false;
		}

		mMap = new MMap();
		// 如果该集体险种保单的结论为拒保，那么减掉保费
		if (mUWFlag.equals("1")) {
			String tSQL = "update lcgrpcont set prem=(select sum(prem) from lcpol "
					+ "where grpcontno='"
					+ mLCGrpPolSchema.getGrpContNo()
					+ "' and riskcode<>'"
					+ mLCGrpPolSchema.getRiskCode()
					+ "' and uwflag<>'1')"
					+ ",operator='"
					+ mOperator
					+ "',modifydate='"
					+ PubFun.getCurrentDate()
					+ "',modifytime='"
					+ PubFun.getCurrentTime()
					+ "' where grpcontno='"
					+ mLCGrpPolSchema.getGrpContNo()
					+ "'";
			mMap.put(tSQL, "UPDATE");
		}

		VData tVData = new VData();
		VData tResult = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", mLCGrpPolSchema
				.getGrpContNo());
		tTransferData
				.setNameAndValue("RiskCode", mLCGrpPolSchema.getRiskCode());
		tTransferData.setNameAndValue("UWFlag", mUWFlag);
		tTransferData.setNameAndValue("UWIdea", mUWIdea);
		tTransferData.setNameAndValue("ContType", "2");
		tTransferData.setNameAndValue("AppGrade", mAppGrade);
		tTransferData.setNameAndValue("UPUWCode", mUPUWCode);
		tTransferData.setNameAndValue("PosFlag", "0"); // 下核保结论位置

		tVData.add(tTransferData);
		tVData.add(mGlobalInput);
		// 对个单层面的险种保单下核保结论
		UWManuNormPolChkBL tUWManuNormPolChkBL = new UWManuNormPolChkBL();
		if (!tUWManuNormPolChkBL.submitData(tVData, "")) {
			this.mErrors.copyAllErrors(tUWManuNormPolChkBL.mErrors);
			return false;
		}
		tResult = tUWManuNormPolChkBL.getResult();
		mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));

		// 对个单层面的保单下核保结论
		UWManuNormContChkBL tUWManuNormContChkBL = new UWManuNormContChkBL();
		if (!tUWManuNormContChkBL.submitData(tVData, "")) {
			this.mErrors.copyAllErrors(tUWManuNormContChkBL.mErrors);
			return false;
		}
		tResult = tUWManuNormContChkBL.getResult();
		mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));

		return true;
	}

	/**
	 * 准备保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePol() {
		mLCGrpPolSchema.setUWFlag(mUWFlag);
		mLCGrpPolSchema.setUWOperator(mOperator);
		mLCGrpPolSchema.setUWDate(PubFun.getCurrentDate());
		mLCGrpPolSchema.setUWTime(PubFun.getCurrentTime());
		mLCGrpPolSchema.setOperator(mOperator);
		mLCGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
		mLCGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
		mLCGrpPolSchema.setUWDate(PubFun.getCurrentDate());

		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareUW() {
		LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
		LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB();
		tLCGUWMasterDB.setGrpPolNo(mGrpPolNo);

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

			// tLCGUWMasterSchema.setUWGrade(mUWPopedom);
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

		mLCGUWMasterSet.clear();
		mLCGUWMasterSet.add(tLCGUWMasterSchema);

		// 核保轨迹表
		LCGUWSubSchema tLCGUWSubSchema = new LCGUWSubSchema();
		LCGUWSubDB tLCGUWSubDB = new LCGUWSubDB();
		tLCGUWSubDB.setGrpPolNo(mGrpPolNo);

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
			tLCGUWSubSchema.setGrpPolNo(mGrpPolNo);
			tLCGUWSubSchema.setGrpProposalNo(mGrpPolNo);
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

		mLCGUWSubSet.clear();
		mLCGUWSubSet.add(tLCGUWSubSchema);

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

	public static void main(String[] args) {
		GrpUWManuNormGChkBL tGrpUWManuNormGChkBL = new GrpUWManuNormGChkBL();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "8621";
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		tLCGrpPolSchema.setGrpPolNo("120210000001255");
		tLCGrpPolSchema.setUWFlag("9");
		tLCGrpPolSchema.setRemark("12");
		tLCGrpPolSchema.setStandbyFlag2("");
		tLCGrpPolSchema.setStandbyFlag3("");

		VData tVData = new VData();
		tVData.add(tLCGrpPolSchema);
		tVData.add(tG);
		if (!tGrpUWManuNormGChkBL.submitData(tVData, "")) {
			logger.debug(tGrpUWManuNormGChkBL.mErrors.getFirstError());
		}

	}

}
