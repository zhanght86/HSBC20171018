package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPUWMasterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPCUWSubSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPUWMasterSchema;
import com.sinosoft.lis.schema.LPUWSubSchema;
import com.sinosoft.lis.vschema.LPCUWMasterSet;
import com.sinosoft.lis.vschema.LPCUWSubSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPUWMasterSet;
import com.sinosoft.lis.vschema.LPUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全个单人工核保结论处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZhangRong
 * @version 1.0
 */
public class PEdorItemManuUWBL {
private static Logger logger = Logger.getLogger(PEdorItemManuUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private String mOperator = "";
	private String mUWFlag = ""; // 核保标志
	private String mUWIdea = ""; // 核保结论
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mPostponeDay = ""; // 延迟时间

	private MMap mMap = null;
	private LPEdorMainSet mLPEdorMainSet = null;
	private LPEdorItemSchema mLPEdorItemSchema = null;
	private LPEdorItemSet mLPEdorItemSet = null;

	private LPContSet mLPContSet = null;
	private LPCUWMasterSet mLPCUWMasterSet = null;
	private LPCUWSubSet mLPCUWSubSet = null;

	private LPPolSet mLPPolSet = null;
	private LPUWMasterSet mLPUWMasterSet = null;
	private LPUWSubSet mLPUWSubSet = null;

	String mFlag;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorItemManuUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}

		// 用户权限校验
		if (!checkUserGrade()) {
			return false;
		}

		// 数据操作业务处理)
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) // 数据提交
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "PEdorItemManuUWBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		logger.debug("---updateData PEdorItemManuUWBL LK---");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		LPCUWMasterSchema tLPCUWMasterSchema = (LPCUWMasterSchema) cInputData
				.getObjectByObjectName("LPCUWMasterSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPEdorItemSchema == null || tLPCUWMasterSchema == null
				|| mGlobalInput == null) {
			mErrors.addOneError(new CError("输入信息不完整！"));
			return false;
		}

		mUWFlag = tLPCUWMasterSchema.getPassFlag();
		mUWIdea = tLPCUWMasterSchema.getUWIdea();
		mPostponeDay = tLPCUWMasterSchema.getPostponeDay();
		if (mUWFlag == null || mUWFlag.equals("")) {
			mUWFlag = tLPCUWMasterSchema.getPassFlag();
			if (mUWFlag == null || mUWFlag.equals("")) {
				mErrors.addOneError(new CError("输入信息不完整！"));
				return false;
			}
		}
		mOperator = mGlobalInput.Operator;
		return true;
	}

	/**
	 * 校验核保级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUserGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);
		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorItemManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		if ((tUWPopedom == null) || tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "PEdorItemManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (mLPEdorItemSet == null || mLPEdorItemSet.size() <= 0) {
			return true;
		}

		if (mUWFlag.equals("6")) {
			return true; // 如果上报，则不需校验核保级别
		}

		String sql = "select * from LPEdorMain where EdorNo = '?EdorNo?' and AppGrade = (select Max(AppGrade) from LPEdorMain where EdorNo = '?EdorNo?')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		logger.debug(sql);
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.executeQuery(sqlbv);

		if (tLPEdorMainSet == null || tLPEdorMainSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "PEdorItemManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有核保信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			mAppGrade = tLPEdorMainSet.get(1).getAppGrade();

			if ((mAppGrade == null) || mAppGrade.equals("")) {
				CError tError = new CError();
				tError.moduleName = "PEdorItemManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "该投保单无核保级别，不能核保!";
				this.mErrors.addOneError(tError);

				return false;
			}

			if (tUWPopedom.compareTo(mAppGrade) < 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorItemManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "已经提交上级核保，不能核保!（操作员：" + mOperator + "）";
				this.mErrors.addOneError(tError);

				return false;
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		mLPEdorMainSet = new LPEdorMainSet();
		mLPEdorItemSet = new LPEdorItemSet();
		mLPContSet = new LPContSet();
		mLPPolSet = new LPPolSet();
		mLPUWMasterSet = new LPUWMasterSet();
		mLPUWSubSet = new LPUWSubSet();
		mLPCUWMasterSet = new LPCUWMasterSet();
		mLPCUWSubSet = new LPCUWSubSet();
		mMap = new MMap();
		mResult.clear();

		String sql = "select * from LPEdorItem where UWFlag in ('5','1')"
				+ " and EdorNo='?EdorNo?'"
				+ " order by MakeDate,MakeTime";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		mLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);
		if (mLPEdorItemSet == null || mLPEdorItemSet.size() <= 0) {
			return true;
		}
		int m = mLPEdorItemSet.size();
		for (int i = 1; i <= m; i++) {
			mLPEdorItemSchema = mLPEdorItemSet.get(i);
			if (!"2".equals(mLPEdorItemSchema.getEdorState())) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorItemManuUWBL";
				tError.functionName = "prepareData";
				tError.errorMessage = "批单号:" + mLPEdorItemSchema.getEdorNo()
						+ "有个别项目未申请确认!";
				logger.debug("批单号:" + mLPEdorItemSchema.getEdorNo()
						+ "有个别项目未申请确认!");
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		sql = "select * from LPEdorItem where UWFlag in ('5','1')"
				+ " and EdorNo='?EdorNo?'"
				+ " and EdorType='?EdorType?'"
				+ " and ContNo='?ContNo?'"
				+ " and InsuredNo='?InsuredNo?'"
				+ " and PolNo='?PolNo?'"
				+ " order by MakeDate,MakeTime";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
		sqlbv.put("InsuredNo", mLPEdorItemSchema.getInsuredNo());
		sqlbv.put("PolNo", mLPEdorItemSchema.getPolNo());
		mLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);

		return true;
	}

	/**
	 * 下核保结论
	 */
	private boolean dealData() {
		if (mLPEdorItemSet == null || mLPEdorItemSet.size() <= 0) {
			return true;
		}
		int n = mLPEdorItemSet.size(); // 应该只有一条
		for (int i = 1; i <= n; i++) {
			mLPEdorItemSchema = mLPEdorItemSet.get(i);

			if (!setPolUWState()) // 对个单合同下险种下核保结论
			{
				return false;
			}

			LPContDB tLPContDB = new LPContDB();
			tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
			LPContSet tLPContSet = tLPContDB.query(); // 从P表获取保全个单信息

			if (tLPContDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLPContDB.mErrors);
				mErrors.addOneError(new CError("查询保全个人合同表失败！"));
				return false;
			}

			LPContSchema tLPContSchema = null;
			if (tLPContSet != null && tLPContSet.size() == 1) {
				tLPContSchema = tLPContSet.get(1);
				if (!setContUWState(tLPContSchema)) // 对合同单下核保结论
				{
					return false;
				}
			}

			mLPEdorItemSchema.setUWFlag(mUWFlag);
			mLPEdorItemSchema.setUWDate(PubFun.getCurrentDate());
			mLPEdorItemSchema.setUWTime(PubFun.getCurrentTime());
			mLPEdorItemSchema.setUWOperator(mGlobalInput.Operator);
			mLPEdorItemSchema.setOperator(mOperator);
			mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
			mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		}

		return true;
	}

	private boolean setPolUWState() {
		if (mLPEdorItemSchema.getPolNo() != null
				&& !mLPEdorItemSchema.getPolNo().equals("")
				&& !mLPEdorItemSchema.getPolNo().equals("000000")) { // 如果批改项目指定险种保单号，则对该险种核保，从LPPol表中获取险种信息
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
			LPPolSet tLPPolSet = tLPPolDB.query();

			if (tLPPolSet == null || tLPPolSet.size() != 1) {
				return true;
			}

			LPPolSchema tLPPolSchema = tLPPolSet.get(1);
			tLPPolSchema.setUWFlag(mUWFlag);
			tLPPolSchema.setUWCode(mOperator);
			tLPPolSchema.setUWDate(PubFun.getCurrentDate());
			tLPPolSchema.setUWTime(PubFun.getCurrentTime());
			tLPPolSchema.setOperator(mOperator);
			tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
			mLPPolSet.add(tLPPolSchema);

			LPUWMasterSchema tLPUWMasterSchema = prepareEdorUWMaster(
					mLPEdorItemSchema, tLPPolSchema); // 生成核保主表信息
			prepareEdorUWSub(tLPUWMasterSchema); // 生成核保子表信息
		}
		return true;
	}

	private boolean setContUWState(LPContSchema aLPContSchema) {
		if (aLPContSchema == null) {
			return false;
		}
		aLPContSchema.setUWFlag(mUWFlag);
		aLPContSchema.setUWOperator(mOperator);
		aLPContSchema.setUWDate(PubFun.getCurrentDate());
		aLPContSchema.setUWTime(PubFun.getCurrentTime());
		// aLPContSchema.setOperator(mOperator);
		aLPContSchema.setModifyDate(PubFun.getCurrentDate());
		aLPContSchema.setModifyTime(PubFun.getCurrentTime());
		mLPContSet.add(aLPContSchema);

		LPCUWMasterSchema tLPCUWMasterSchema = prepareEdorCUWMaster(
				mLPEdorItemSchema, aLPContSchema); // 生成合同核保主表信息
		prepareEdorCUWSub(tLPCUWMasterSchema); // 生成合同核保子表信息

		return true;
	}

	private LPUWMasterSchema prepareEdorUWMaster(
			LPEdorItemSchema aLPEdorItemSchema, LPPolSchema aLPPolSchema) // 生成核保主表信息
	{
		int tUWNo = 0;
		LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();

		LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
		tLPUWMasterDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPUWMasterDB.setEdorType(aLPEdorItemSchema.getEdorType());
		tLPUWMasterDB.setPolNo(aLPPolSchema.getPolNo());
		LPUWMasterSet tLPUWMasterSet = tLPUWMasterDB.query();
		if (tLPUWMasterSet == null || tLPUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPUWMasterSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPUWMasterSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			tLPUWMasterSchema.setGrpContNo(aLPPolSchema.getGrpContNo());
			tLPUWMasterSchema.setContNo(aLPPolSchema.getContNo());
			tLPUWMasterSchema.setProposalContNo(aLPPolSchema.getContNo());
			tLPUWMasterSchema.setPolNo(aLPPolSchema.getPolNo());
			tLPUWMasterSchema.setProposalNo(aLPPolSchema.getProposalNo());
			tLPUWMasterSchema.setAutoUWFlag("2");
			tLPUWMasterSchema.setPassFlag(mUWFlag);
			tLPUWMasterSchema.setUWIdea(mUWIdea);
			tLPUWMasterSchema.setUWGrade("A");
			tLPUWMasterSchema.setAppGrade("A");
			tLPUWMasterSchema.setPostponeDay("");
			tLPUWMasterSchema.setPostponeDate(mPostponeDay);
			tLPUWMasterSchema.setState(mUWFlag);
			tLPUWMasterSchema.setUWIdea("");
			tLPUWMasterSchema.setUpReportContent("");
			tLPUWMasterSchema.setHealthFlag("0");
			tLPUWMasterSchema.setQuesFlag("0");
			tLPUWMasterSchema.setSpecFlag("0");
			tLPUWMasterSchema.setAddPremFlag("0");
			tLPUWMasterSchema.setAddPremReason("");
			tLPUWMasterSchema.setReportFlag("0");
			tLPUWMasterSchema.setPrintFlag("0");
			tLPUWMasterSchema.setPrintFlag2("0");
			tLPUWMasterSchema.setChangePolFlag("0");
			tLPUWMasterSchema.setChangePolReason("");
			tLPUWMasterSchema.setSpecReason("");
			tLPUWMasterSchema.setManageCom(aLPEdorItemSchema.getManageCom());
			tLPUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPUWMasterSet.get(1).getUWNo() + 1;
			tLPUWMasterSchema = tLPUWMasterSet.get(1);
			tLPUWMasterSchema.setAutoUWFlag("2");
			tLPUWMasterSchema.setPassFlag(mUWFlag);
			tLPUWMasterSchema.setUWIdea(mUWIdea);
		}
		tLPUWMasterSchema.setUWNo(tUWNo);
		tLPUWMasterSchema.setAppntName(aLPPolSchema.getAppntName());
		tLPUWMasterSchema.setAppntNo(aLPPolSchema.getAppntNo());
		tLPUWMasterSchema.setInsuredName(aLPPolSchema.getInsuredName());
		tLPUWMasterSchema.setInsuredNo(aLPPolSchema.getInsuredNo());
		tLPUWMasterSchema.setAgentCode(aLPPolSchema.getAgentCode());
		tLPUWMasterSchema.setAgentGroup(aLPPolSchema.getAgentGroup());
		tLPUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPUWMasterSet.add(tLPUWMasterSchema);
		return tLPUWMasterSchema;
	}

	private LPUWSubSchema prepareEdorUWSub(LPUWMasterSchema aLPUWMasterSchema) // 生成核保子表
	{
		LPUWSubSchema tLPUWSubSchema = new LPUWSubSchema();

		tLPUWSubSchema.setEdorNo(aLPUWMasterSchema.getEdorNo());
		tLPUWSubSchema.setEdorType(aLPUWMasterSchema.getEdorType());
		tLPUWSubSchema.setGrpContNo(aLPUWMasterSchema.getGrpContNo());
		tLPUWSubSchema.setContNo(aLPUWMasterSchema.getContNo());
		tLPUWSubSchema.setProposalContNo(aLPUWMasterSchema.getProposalContNo());
		tLPUWSubSchema.setPolNo(aLPUWMasterSchema.getPolNo());
		tLPUWSubSchema.setProposalNo(aLPUWMasterSchema.getProposalNo());
		tLPUWSubSchema.setUWNo(aLPUWMasterSchema.getUWNo());
		tLPUWSubSchema.setAppntName(aLPUWMasterSchema.getAppntName());
		tLPUWSubSchema.setAppntNo(aLPUWMasterSchema.getAppntNo());
		tLPUWSubSchema.setInsuredName(aLPUWMasterSchema.getInsuredName());
		tLPUWSubSchema.setInsuredNo(aLPUWMasterSchema.getInsuredNo());
		tLPUWSubSchema.setAgentCode(aLPUWMasterSchema.getAgentCode());
		tLPUWSubSchema.setAgentGroup(aLPUWMasterSchema.getAgentGroup());
		tLPUWSubSchema.setAutoUWFlag(aLPUWMasterSchema.getAutoUWFlag());
		tLPUWSubSchema.setPassFlag(aLPUWMasterSchema.getPassFlag());
		tLPUWSubSchema.setUWIdea(aLPUWMasterSchema.getUWIdea());
		tLPUWSubSchema.setUWGrade(aLPUWMasterSchema.getUWGrade());
		tLPUWSubSchema.setAppGrade(aLPUWMasterSchema.getUWGrade());
		tLPUWSubSchema.setPostponeDay(aLPUWMasterSchema.getPostponeDay());
		tLPUWSubSchema.setPostponeDate(aLPUWMasterSchema.getPostponeDate());
		tLPUWSubSchema.setState(aLPUWMasterSchema.getState());
		tLPUWSubSchema.setUWIdea(aLPUWMasterSchema.getUWIdea());
		tLPUWSubSchema.setUpReportContent(aLPUWMasterSchema
				.getUpReportContent());
		tLPUWSubSchema.setHealthFlag(aLPUWMasterSchema.getHealthFlag());
		tLPUWSubSchema.setQuesFlag(aLPUWMasterSchema.getQuesFlag());
		tLPUWSubSchema.setSpecFlag(aLPUWMasterSchema.getSpecFlag());
		tLPUWSubSchema.setAddPremFlag(aLPUWMasterSchema.getAddPremFlag());
		tLPUWSubSchema.setAddPremReason(aLPUWMasterSchema.getAddPremReason());
		tLPUWSubSchema.setReportFlag(aLPUWMasterSchema.getReportFlag());
		tLPUWSubSchema.setPrintFlag(aLPUWMasterSchema.getPrintFlag());
		tLPUWSubSchema.setPrintFlag2(aLPUWMasterSchema.getPrintFlag2());
		tLPUWSubSchema.setChangePolFlag(aLPUWMasterSchema.getChangePolFlag());
		tLPUWSubSchema.setChangePolReason(aLPUWMasterSchema
				.getChangePolReason());
		tLPUWSubSchema.setSpecReason(aLPUWMasterSchema.getSpecFlag());
		tLPUWSubSchema.setManageCom(aLPUWMasterSchema.getManageCom());
		tLPUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		tLPUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPUWSubSchema.setMakeTime(PubFun.getCurrentTime());

		mLPUWSubSet.add(tLPUWSubSchema);
		return tLPUWSubSchema;
	}

	private LPCUWMasterSchema prepareEdorCUWMaster(
			LPEdorItemSchema aLPEdorItemSchema, LPContSchema aLPContSchema) // 生成合同核保主表信息
	{
		int tUWNo = 0;
		LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();

		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPCUWMasterDB.setEdorType(aLPEdorItemSchema.getEdorType());
		tLPCUWMasterDB.setContNo(aLPContSchema.getContNo());
		LPCUWMasterSet tLPCUWMasterSet = tLPCUWMasterDB.query();
		if (tLPCUWMasterSet == null || tLPCUWMasterSet.size() <= 0) {
			tUWNo = 1;

			tLPCUWMasterSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPCUWMasterSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			tLPCUWMasterSchema.setGrpContNo(aLPContSchema.getGrpContNo());
			tLPCUWMasterSchema.setContNo(aLPContSchema.getContNo());
			tLPCUWMasterSchema.setProposalContNo(aLPContSchema
					.getProposalContNo());
			tLPCUWMasterSchema.setAutoUWFlag("2");
			tLPCUWMasterSchema.setPassFlag(mUWFlag);
			tLPCUWMasterSchema.setUWIdea(mUWIdea);
			tLPCUWMasterSchema.setUWGrade("A");
			tLPCUWMasterSchema.setAppGrade("A");
			tLPCUWMasterSchema.setPostponeDay("");
			tLPCUWMasterSchema.setPostponeDate("");
			tLPCUWMasterSchema.setState(mUWFlag);
			tLPCUWMasterSchema.setUWIdea("");
			tLPCUWMasterSchema.setUpReportContent("");
			tLPCUWMasterSchema.setHealthFlag("0");
			tLPCUWMasterSchema.setQuesFlag("0");
			tLPCUWMasterSchema.setSpecFlag("0");
			tLPCUWMasterSchema.setAddPremFlag("0");
			tLPCUWMasterSchema.setAddPremReason("");
			tLPCUWMasterSchema.setReportFlag("0");
			tLPCUWMasterSchema.setPrintFlag("0");
			tLPCUWMasterSchema.setPrintFlag2("0");
			tLPCUWMasterSchema.setChangePolFlag("0");
			tLPCUWMasterSchema.setChangePolReason("");
			tLPCUWMasterSchema.setSpecReason("");
			tLPCUWMasterSchema.setManageCom(aLPEdorItemSchema.getManageCom());
			tLPCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPCUWMasterSet.get(1).getUWNo() + 1;
			tLPCUWMasterSchema = tLPCUWMasterSet.get(1);
			tLPCUWMasterSchema.setAutoUWFlag("2");
			tLPCUWMasterSchema.setPassFlag(mUWFlag);
			tLPCUWMasterSchema.setUWIdea(mUWIdea);
		}
		tLPCUWMasterSchema.setUWNo(tUWNo);
		tLPCUWMasterSchema.setAppntName(aLPContSchema.getAppntName());
		tLPCUWMasterSchema.setAppntNo(aLPContSchema.getAppntNo());
		tLPCUWMasterSchema.setInsuredName(aLPContSchema.getInsuredName());
		tLPCUWMasterSchema.setInsuredNo(aLPContSchema.getInsuredNo());
		tLPCUWMasterSchema.setAgentCode(aLPContSchema.getAgentCode());
		tLPCUWMasterSchema.setAgentGroup(aLPContSchema.getAgentGroup());
		tLPCUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPCUWMasterSet.add(tLPCUWMasterSchema);
		return tLPCUWMasterSchema;
	}

	private LPCUWSubSchema prepareEdorCUWSub(
			LPCUWMasterSchema aLPCUWMasterSchema) // 生成合同核保子表
	{
		LPCUWSubSchema tLPCUWSubSchema = new LPCUWSubSchema();

		tLPCUWSubSchema.setEdorNo(aLPCUWMasterSchema.getEdorNo());
		tLPCUWSubSchema.setEdorType(aLPCUWMasterSchema.getEdorType());
		tLPCUWSubSchema.setGrpContNo(aLPCUWMasterSchema.getGrpContNo());
		tLPCUWSubSchema.setContNo(aLPCUWMasterSchema.getContNo());
		tLPCUWSubSchema.setProposalContNo(aLPCUWMasterSchema
				.getProposalContNo());
		tLPCUWSubSchema.setUWNo(aLPCUWMasterSchema.getUWNo());
		tLPCUWSubSchema.setAppntName(aLPCUWMasterSchema.getAppntName());
		tLPCUWSubSchema.setAppntNo(aLPCUWMasterSchema.getAppntNo());
		tLPCUWSubSchema.setInsuredName(aLPCUWMasterSchema.getInsuredName());
		tLPCUWSubSchema.setInsuredNo(aLPCUWMasterSchema.getInsuredNo());
		tLPCUWSubSchema.setAgentCode(aLPCUWMasterSchema.getAgentCode());
		tLPCUWSubSchema.setAgentGroup(aLPCUWMasterSchema.getAgentGroup());
		tLPCUWSubSchema.setAutoUWFlag(aLPCUWMasterSchema.getAutoUWFlag());
		tLPCUWSubSchema.setPassFlag(aLPCUWMasterSchema.getPassFlag());
		tLPCUWSubSchema.setUWIdea(aLPCUWMasterSchema.getUWIdea());
		tLPCUWSubSchema.setUWGrade(aLPCUWMasterSchema.getUWGrade());
		tLPCUWSubSchema.setAppGrade(aLPCUWMasterSchema.getUWGrade());
		tLPCUWSubSchema.setPostponeDay(aLPCUWMasterSchema.getPostponeDay());
		tLPCUWSubSchema.setPostponeDate(aLPCUWMasterSchema.getPostponeDate());
		tLPCUWSubSchema.setState(aLPCUWMasterSchema.getState());
		tLPCUWSubSchema.setUWIdea(aLPCUWMasterSchema.getUWIdea());
		tLPCUWSubSchema.setUpReportContent(aLPCUWMasterSchema
				.getUpReportContent());
		tLPCUWSubSchema.setHealthFlag(aLPCUWMasterSchema.getHealthFlag());
		tLPCUWSubSchema.setQuesFlag(aLPCUWMasterSchema.getQuesFlag());
		tLPCUWSubSchema.setSpecFlag(aLPCUWMasterSchema.getSpecFlag());
		tLPCUWSubSchema.setAddPremFlag(aLPCUWMasterSchema.getAddPremFlag());
		tLPCUWSubSchema.setAddPremReason(aLPCUWMasterSchema.getAddPremReason());
		tLPCUWSubSchema.setReportFlag(aLPCUWMasterSchema.getReportFlag());
		tLPCUWSubSchema.setPrintFlag(aLPCUWMasterSchema.getPrintFlag());
		tLPCUWSubSchema.setPrintFlag2(aLPCUWMasterSchema.getPrintFlag2());
		tLPCUWSubSchema.setChangePolFlag(aLPCUWMasterSchema.getChangePolFlag());
		tLPCUWSubSchema.setChangePolReason(aLPCUWMasterSchema
				.getChangePolReason());
		tLPCUWSubSchema.setSpecReason(aLPCUWMasterSchema.getSpecFlag());
		tLPCUWSubSchema.setManageCom(aLPCUWMasterSchema.getManageCom());
		tLPCUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLPCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		tLPCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPCUWSubSchema.setMakeTime(PubFun.getCurrentTime());

		mLPCUWSubSet.add(tLPCUWSubSchema);
		return tLPCUWSubSchema;
	}

	private boolean prepareOutputData() {
		mMap.put(mLPEdorMainSet, "UPDATE");
		mMap.put(mLPEdorItemSet, "UPDATE");
		mMap.put(mLPContSet, "UPDATE");
		mMap.put(mLPPolSet, "UPDATE");
		mMap.put(mLPUWMasterSet, "DELETE&INSERT");
		mMap.put(mLPUWSubSet, "INSERT");
		mMap.put(mLPCUWMasterSet, "DELETE&INSERT");
		mMap.put(mLPCUWSubSet, "INSERT");
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PEdorItemManuUWBL aPEdorItemManuUWBL = new PEdorItemManuUWBL();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();

		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPEdorItemSchema.setEdorNo("410000000000240");
		tLPEdorItemSchema.setContNo("230000000000166");
		tLPEdorItemSchema.setEdorType("IC");

		tLPCUWMasterSchema.setUWIdea("temp test");
		tLPCUWMasterSchema.setPassFlag("9");

		tInputData.addElement(tLPEdorItemSchema);
		tInputData.addElement(tLPCUWMasterSchema);
		tInputData.addElement(tGlobalInput);

		aPEdorItemManuUWBL.submitData(tInputData, "");
		logger.debug(aPEdorItemManuUWBL.mErrors.getErrContent());
	}

}
