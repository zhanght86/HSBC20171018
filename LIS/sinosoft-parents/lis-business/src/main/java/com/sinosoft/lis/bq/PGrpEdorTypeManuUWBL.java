package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LPGCUWMasterDB;
import com.sinosoft.lis.db.LPGUWMasterDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGCUWMasterSchema;
import com.sinosoft.lis.schema.LPGCUWSubSchema;
import com.sinosoft.lis.schema.LPGUWMasterSchema;
import com.sinosoft.lis.schema.LPGUWSubSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LPGCUWMasterSet;
import com.sinosoft.lis.vschema.LPGCUWSubSet;
import com.sinosoft.lis.vschema.LPGUWMasterSet;
import com.sinosoft.lis.vschema.LPGUWSubSet;
import com.sinosoft.lis.vschema.LPGrpContSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保全人工核保对团险批改项目下核保结论
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
public class PGrpEdorTypeManuUWBL {
private static Logger logger = Logger.getLogger(PGrpEdorTypeManuUWBL.class);
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

	private MMap mMap = null;
	private LPGrpEdorMainSet mLPGrpEdorMainSet = null;
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = null;
	private LPGrpEdorItemSet mLPGrpEdorItemSet = null;

	private LPGrpContSet mLPGrpContSet = null;
	private LPGCUWMasterSet mLPGCUWMasterSet = null;
	private LPGCUWSubSet mLPGCUWSubSet = null;

	private LPGrpPolSet mLPGrpPolSet = null;
	private LPGUWMasterSet mLPGUWMasterSet = null;
	private LPGUWSubSet mLPGUWSubSet = null;

	String mFlag;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PGrpEdorTypeManuUWBL() {
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
			tError.moduleName = "PGrpEdorTypeManuUWBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		logger.debug("---updateData PGrpEdorTypeManuUWBL LK---");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		LPGCUWMasterSchema tLPGCUWMasterSchema = (LPGCUWMasterSchema) cInputData
				.getObjectByObjectName("LPGCUWMasterSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPGrpEdorItemSchema == null || tLPGCUWMasterSchema == null
				|| mGlobalInput == null) {
			mErrors.addOneError(new CError("输入信息不完整！"));
			return false;
		}

		mUWFlag = mLPGrpEdorItemSchema.getUWFlag();
		mUWIdea = tLPGCUWMasterSchema.getUWIdea();
		if (mUWFlag == null || mUWFlag.equals("")) {
			mUWFlag = tLPGCUWMasterSchema.getPassFlag();
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
		logger.debug("mOperator" + mOperator);

		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PGrpEdorTypeManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		logger.debug("tUWPopedom" + tUWPopedom);

		if ((tUWPopedom == null) || tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "PGrpEdorTypeManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String sql = "select * from LPGrpEdorMain where EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and AppGrade = (select Max(AppGrade) from LPGrpEdorMain where EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "')";
		logger.debug(sql);
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		LPGrpEdorMainSet tLPGrpEdorMainSet = tLPGrpEdorMainDB.executeQuery(sql);

		if (tLPGrpEdorMainSet == null || tLPGrpEdorMainSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "PGrpEdorTypeManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有核保信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			mAppGrade = tLPGrpEdorMainSet.get(1).getAppGrade();

			if ((mAppGrade == null) || mAppGrade.equals("")) {
				CError tError = new CError();
				tError.moduleName = "PGrpEdorTypeManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "该投保单无核保级别，不能核保!";
				this.mErrors.addOneError(tError);

				return false;
			}

			if (tUWPopedom.compareTo(mAppGrade) < 0) {
				CError tError = new CError();
				tError.moduleName = "PGrpEdorTypeManuUWBL";
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
		mLPGrpEdorMainSet = new LPGrpEdorMainSet();
		mLPGrpEdorItemSet = new LPGrpEdorItemSet();
		mLPGrpContSet = new LPGrpContSet();
		mLPGrpPolSet = new LPGrpPolSet();
		mLPGUWMasterSet = new LPGUWMasterSet();
		mLPGUWSubSet = new LPGUWSubSet();
		mLPGCUWMasterSet = new LPGCUWMasterSet();
		mLPGCUWSubSet = new LPGCUWSubSet();
		mMap = new MMap();
		mResult.clear();

		String sql = "select * from LPGrpEdorItem where EdorNo='"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'"
				+ " order by MakeDate,MakeTime";
		logger.debug("-------sql:" + sql);

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		mLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(sql);
		int m = mLPGrpEdorItemSet.size();
		for (int i = 1; i <= m; i++) {
			mLPGrpEdorItemSchema = mLPGrpEdorItemSet.get(i);
			if (!"2".equals(mLPGrpEdorItemSchema.getEdorState())) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PGrpEdorAutoUWBL";
				tError.functionName = "prepareData";
				tError.errorMessage = "批单号:" + mLPGrpEdorItemSchema.getEdorNo()
						+ "有个别项目未申请确认!";
				logger.debug("批单号:" + mLPGrpEdorItemSchema.getEdorNo()
						+ "有个别项目未申请确认!");
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		sql = "select * from LPGrpEdorItem where EdorNo='"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'" + " and EdorType='"
				+ mLPGrpEdorItemSchema.getEdorType() + "'"
				+ " order by MakeDate,MakeTime";
		logger.debug("-------sql:" + sql);
		mLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(sql);

		return true;
	}

	/**
	 * 下核保结论
	 */
	private boolean dealData() {
		int n = mLPGrpEdorItemSet.size();
		for (int i = 1; i <= n; i++) {
			mLPGrpEdorItemSchema = mLPGrpEdorItemSet.get(i);

			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();
			VData tInputData = new VData();

			tLPEdorItemSchema.setEdorAcceptNo(mLPGrpEdorItemSchema
					.getEdorAcceptNo());
			tLPEdorItemSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPEdorItemSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPEdorItemSchema.setUWFlag(mUWFlag);
			tLPCUWMasterSchema.setPassFlag(mUWFlag);
			tLPCUWMasterSchema.setUWIdea(mUWIdea);
			tInputData.add(tLPEdorItemSchema);
			tInputData.add(tLPCUWMasterSchema);
			tInputData.add(mGlobalInput);

			PEdorManuGUWBL tPEdorManuGUWBL = new PEdorManuGUWBL();
			if (!tPEdorManuGUWBL.submitData(tInputData, "")) // 对团单下个单人工核保
			{
				mErrors.copyAllErrors(tPEdorManuGUWBL.mErrors);
				mErrors.addOneError(new CError("对团单下个单人工核保失败！"));
				return false;
			}

			if (!setPolUWState()) // 对个单合同下险种下核保结论
			{
				return false;
			}

			LPGrpContDB tLPGrpContDB = new LPGrpContDB();
			tLPGrpContDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			LPGrpContSet tLPGrpContSet = tLPGrpContDB.query(); // 从P表获取保全个单信息

			if (tLPGrpContDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLPGrpContDB.mErrors);
				mErrors.addOneError(new CError("查询保全个人合同表失败！"));
				return false;
			}

			LPGrpContSchema tLPGrpContSchema = null;
			if (tLPGrpContSet != null && tLPGrpContSet.size() == 1) {
				tLPGrpContSchema = tLPGrpContSet.get(1);
				if (!setContUWState(tLPGrpContSchema)) // 对合同单下核保结论
				{
					return false;
				}
			}

			mLPGrpEdorItemSchema.setUWFlag(mUWFlag);
			mLPGrpEdorItemSchema.setUWDate(PubFun.getCurrentDate());
			mLPGrpEdorItemSchema.setUWTime(PubFun.getCurrentTime());
			mLPGrpEdorItemSchema.setUWOperator(mGlobalInput.Operator);
			mLPGrpEdorItemSchema.setOperator(mOperator);
			mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
			mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		}

		return true;
	}

	private boolean setPolUWState() {
		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		tLPGrpPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPGrpPolSet tLPGrpPolSet = tLPGrpPolDB.query();
		if (tLPGrpPolDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPGrpPolDB.mErrors);
			mErrors.addOneError(new CError("查询批改集体险种错误！"));
			return false;
		}

		int n = 0;
		if (tLPGrpPolSet != null) {
			n = tLPGrpPolSet.size();
		}
		for (int i = 1; i <= n; i++) {
			LPGrpPolSchema tLPGrpPolSchema = tLPGrpPolSet.get(i);
			tLPGrpPolSchema.setUWFlag(mUWFlag);
			tLPGrpPolSchema.setUWOperator(mOperator);
			tLPGrpPolSchema.setUWDate(PubFun.getCurrentDate());
			tLPGrpPolSchema.setUWTime(PubFun.getCurrentTime());
			tLPGrpPolSchema.setOperator(mOperator);
			tLPGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
			mLPGrpPolSet.add(tLPGrpPolSchema);

			LPGUWMasterSchema tLPGUWMasterSchema = prepareEdorUWMaster(
					mLPGrpEdorItemSchema, tLPGrpPolSchema); // 生成核保主表信息
			prepareEdorUWSub(tLPGUWMasterSchema); // 生成核保子表信息
		}

		return true;
	}

	private boolean setContUWState(LPGrpContSchema aLPGrpContSchema) {
		if (aLPGrpContSchema == null) {
			return false;
		}
		aLPGrpContSchema.setUWFlag(mUWFlag);
		aLPGrpContSchema.setUWOperator(mOperator);
		aLPGrpContSchema.setUWDate(PubFun.getCurrentDate());
		aLPGrpContSchema.setUWTime(PubFun.getCurrentTime());
		aLPGrpContSchema.setOperator(mOperator);
		aLPGrpContSchema.setModifyDate(PubFun.getCurrentDate());
		aLPGrpContSchema.setModifyTime(PubFun.getCurrentTime());
		mLPGrpContSet.add(aLPGrpContSchema);

		LPGCUWMasterSchema tLPGCUWMasterSchema = prepareEdorCUWMaster(
				mLPGrpEdorItemSchema, aLPGrpContSchema); // 生成合同核保主表信息
		prepareEdorCUWSub(tLPGCUWMasterSchema); // 生成合同核保子表信息

		return true;
	}

	private LPGUWMasterSchema prepareEdorUWMaster(
			LPGrpEdorItemSchema aLPGrpEdorItemSchema,
			LPGrpPolSchema aLPGrpPolSchema) // 生成核保主表信息
	{
		int tUWNo = 0;
		LPGUWMasterSchema tLPGUWMasterSchema = new LPGUWMasterSchema();

		LPGUWMasterDB tLPGUWMasterDB = new LPGUWMasterDB();
		tLPGUWMasterDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPGUWMasterDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		tLPGUWMasterDB.setGrpPolNo(aLPGrpPolSchema.getGrpPolNo());
		LPGUWMasterSet tLPGUWMasterSet = tLPGUWMasterDB.query();
		if (tLPGUWMasterSet == null || tLPGUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPGUWMasterSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			tLPGUWMasterSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());
			tLPGUWMasterSchema.setGrpContNo(aLPGrpPolSchema.getGrpContNo());
			tLPGUWMasterSchema.setProposalGrpContNo(aLPGrpPolSchema
					.getGrpContNo());
			tLPGUWMasterSchema.setGrpPolNo(aLPGrpPolSchema.getGrpPolNo());
			tLPGUWMasterSchema.setGrpProposalNo(aLPGrpPolSchema
					.getGrpProposalNo());
			tLPGUWMasterSchema.setAutoUWFlag("2");
			tLPGUWMasterSchema.setPassFlag(mUWFlag);
			tLPGUWMasterSchema.setUWIdea(mUWIdea);
			tLPGUWMasterSchema.setUWGrade("A");
			tLPGUWMasterSchema.setAppGrade("A");
			tLPGUWMasterSchema.setPostponeDay("");
			tLPGUWMasterSchema.setPostponeDate("");
			tLPGUWMasterSchema.setState(mUWFlag);
			tLPGUWMasterSchema.setUWIdea("");
			tLPGUWMasterSchema.setUpReportContent("");
			tLPGUWMasterSchema.setHealthFlag("0");
			tLPGUWMasterSchema.setQuesFlag("0");
			tLPGUWMasterSchema.setSpecFlag("0");
			tLPGUWMasterSchema.setAddPremFlag("0");
			tLPGUWMasterSchema.setAddPremReason("");
			tLPGUWMasterSchema.setReportFlag("0");
			tLPGUWMasterSchema.setPrintFlag("0");
			tLPGUWMasterSchema.setPrintFlag2("0");
			tLPGUWMasterSchema.setChangePolFlag("0");
			tLPGUWMasterSchema.setChangePolReason("");
			tLPGUWMasterSchema.setSpecReason("");
			tLPGUWMasterSchema
					.setManageCom(aLPGrpEdorItemSchema.getManageCom());
			tLPGUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPGUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPGUWMasterSet.get(1).getUWNo() + 1;
			tLPGUWMasterSchema = tLPGUWMasterSet.get(1);
			tLPGUWMasterSchema.setAutoUWFlag("2");
			tLPGUWMasterSchema.setPassFlag(mUWFlag);
			tLPGUWMasterSchema.setUWIdea(mUWIdea);
		}
		tLPGUWMasterSchema.setUWNo(tUWNo);
		tLPGUWMasterSchema.setAppntName(aLPGrpPolSchema.getGrpName());
		tLPGUWMasterSchema.setAppntNo(aLPGrpPolSchema.getCustomerNo());
		tLPGUWMasterSchema.setAgentCode(aLPGrpPolSchema.getAgentCode());
		tLPGUWMasterSchema.setAgentGroup(aLPGrpPolSchema.getAgentGroup());
		tLPGUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPGUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPGUWMasterSet.add(tLPGUWMasterSchema);
		return tLPGUWMasterSchema;
	}

	private LPGUWSubSchema prepareEdorUWSub(LPGUWMasterSchema aLPGUWMasterSchema) // 生成核保子表
	{
		LPGUWSubSchema tLPGUWSubSchema = new LPGUWSubSchema();

		tLPGUWSubSchema.setEdorNo(aLPGUWMasterSchema.getEdorNo());
		tLPGUWSubSchema.setEdorType(aLPGUWMasterSchema.getEdorType());
		tLPGUWSubSchema.setGrpContNo(aLPGUWMasterSchema.getGrpContNo());
		tLPGUWSubSchema.setProposalGrpContNo(aLPGUWMasterSchema
				.getProposalGrpContNo());
		tLPGUWSubSchema.setGrpPolNo(aLPGUWMasterSchema.getGrpPolNo());
		tLPGUWSubSchema.setGrpProposalNo(aLPGUWMasterSchema.getGrpProposalNo());
		tLPGUWSubSchema.setUWNo(aLPGUWMasterSchema.getUWNo());
		tLPGUWSubSchema.setAppntName(aLPGUWMasterSchema.getAppntName());
		tLPGUWSubSchema.setAppntNo(aLPGUWMasterSchema.getAppntNo());
		tLPGUWSubSchema.setInsuredName(aLPGUWMasterSchema.getInsuredName());
		tLPGUWSubSchema.setInsuredNo(aLPGUWMasterSchema.getInsuredNo());
		tLPGUWSubSchema.setAgentCode(aLPGUWMasterSchema.getAgentCode());
		tLPGUWSubSchema.setAgentGroup(aLPGUWMasterSchema.getAgentGroup());
		tLPGUWSubSchema.setAutoUWFlag(aLPGUWMasterSchema.getAutoUWFlag());
		tLPGUWSubSchema.setPassFlag(aLPGUWMasterSchema.getPassFlag());
		tLPGUWSubSchema.setUWIdea(aLPGUWMasterSchema.getUWIdea());
		tLPGUWSubSchema.setUWGrade(aLPGUWMasterSchema.getUWGrade());
		tLPGUWSubSchema.setAppGrade(aLPGUWMasterSchema.getUWGrade());
		tLPGUWSubSchema.setPostponeDay(aLPGUWMasterSchema.getPostponeDay());
		tLPGUWSubSchema.setPostponeDate(aLPGUWMasterSchema.getPostponeDate());
		tLPGUWSubSchema.setState(aLPGUWMasterSchema.getState());
		tLPGUWSubSchema.setUWIdea(aLPGUWMasterSchema.getUWIdea());
		tLPGUWSubSchema.setUpReportContent(aLPGUWMasterSchema
				.getUpReportContent());
		tLPGUWSubSchema.setHealthFlag(aLPGUWMasterSchema.getHealthFlag());
		tLPGUWSubSchema.setQuesFlag(aLPGUWMasterSchema.getQuesFlag());
		tLPGUWSubSchema.setSpecFlag(aLPGUWMasterSchema.getSpecFlag());
		tLPGUWSubSchema.setAddPremFlag(aLPGUWMasterSchema.getAddPremFlag());
		tLPGUWSubSchema.setAddPremReason(aLPGUWMasterSchema.getAddPremReason());
		tLPGUWSubSchema.setReportFlag(aLPGUWMasterSchema.getReportFlag());
		tLPGUWSubSchema.setPrintFlag(aLPGUWMasterSchema.getPrintFlag());
		tLPGUWSubSchema.setPrintFlag2(aLPGUWMasterSchema.getPrintFlag2());
		tLPGUWSubSchema.setChangePolFlag(aLPGUWMasterSchema.getChangePolFlag());
		tLPGUWSubSchema.setChangePolReason(aLPGUWMasterSchema
				.getChangePolReason());
		tLPGUWSubSchema.setSpecReason(aLPGUWMasterSchema.getSpecFlag());
		tLPGUWSubSchema.setManageCom(aLPGUWMasterSchema.getManageCom());
		tLPGUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPGUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		tLPGUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPGUWSubSchema.setMakeTime(PubFun.getCurrentTime());

		mLPGUWSubSet.add(tLPGUWSubSchema);
		return tLPGUWSubSchema;
	}

	private LPGCUWMasterSchema prepareEdorCUWMaster(
			LPGrpEdorItemSchema aLPGrpEdorItemSchema,
			LPGrpContSchema aLPGrpContSchema) // 生成合同核保主表信息
	{
		int tUWNo = 0;
		LPGCUWMasterSchema tLPGCUWMasterSchema = new LPGCUWMasterSchema();

		LPGCUWMasterDB tLPGCUWMasterDB = new LPGCUWMasterDB();
		tLPGCUWMasterDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPGCUWMasterDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		tLPGCUWMasterDB.setGrpContNo(aLPGrpContSchema.getGrpContNo());
		LPGCUWMasterSet tLPGCUWMasterSet = tLPGCUWMasterDB.query();
		if (tLPGCUWMasterSet == null || tLPGCUWMasterSet.size() <= 0) {
			tUWNo = 1;

			tLPGCUWMasterSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			tLPGCUWMasterSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());
			tLPGCUWMasterSchema.setGrpContNo(aLPGrpContSchema.getGrpContNo());
			tLPGCUWMasterSchema.setProposalGrpContNo(aLPGrpContSchema
					.getProposalGrpContNo());
			tLPGCUWMasterSchema.setAutoUWFlag("2");
			tLPGCUWMasterSchema.setPassFlag(mUWFlag);
			tLPGCUWMasterSchema.setUWIdea(mUWIdea);
			tLPGCUWMasterSchema.setUWGrade("A");
			tLPGCUWMasterSchema.setAppGrade("A");
			tLPGCUWMasterSchema.setPostponeDay("");
			tLPGCUWMasterSchema.setPostponeDate("");
			tLPGCUWMasterSchema.setState(mUWFlag);
			tLPGCUWMasterSchema.setUWIdea("");
			tLPGCUWMasterSchema.setUpReportContent("");
			tLPGCUWMasterSchema.setHealthFlag("0");
			tLPGCUWMasterSchema.setQuesFlag("0");
			tLPGCUWMasterSchema.setSpecFlag("0");
			tLPGCUWMasterSchema.setAddPremFlag("0");
			tLPGCUWMasterSchema.setAddPremReason("");
			tLPGCUWMasterSchema.setReportFlag("0");
			tLPGCUWMasterSchema.setPrintFlag("0");
			tLPGCUWMasterSchema.setPrintFlag2("0");
			tLPGCUWMasterSchema.setChangePolFlag("0");
			tLPGCUWMasterSchema.setChangePolReason("");
			tLPGCUWMasterSchema.setSpecReason("");
			tLPGCUWMasterSchema.setManageCom(aLPGrpEdorItemSchema
					.getManageCom());
			tLPGCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPGCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPGCUWMasterSet.get(1).getUWNo() + 1;
			tLPGCUWMasterSchema = tLPGCUWMasterSet.get(1);
			tLPGCUWMasterSchema.setAutoUWFlag("2");
			tLPGCUWMasterSchema.setPassFlag(mUWFlag);
			tLPGCUWMasterSchema.setUWIdea(mUWIdea);
		}
		tLPGCUWMasterSchema.setUWNo(tUWNo);
		tLPGCUWMasterSchema.setAppntName(aLPGrpContSchema.getGrpName());
		tLPGCUWMasterSchema.setAppntNo(aLPGrpContSchema.getAppntNo());
		tLPGCUWMasterSchema.setAgentCode(aLPGrpContSchema.getAgentCode());
		tLPGCUWMasterSchema.setAgentGroup(aLPGrpContSchema.getAgentGroup());
		tLPGCUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPGCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPGCUWMasterSet.add(tLPGCUWMasterSchema);
		return tLPGCUWMasterSchema;
	}

	private LPGCUWSubSchema prepareEdorCUWSub(
			LPGCUWMasterSchema aLPGCUWMasterSchema) // 生成合同核保子表
	{
		LPGCUWSubSchema tLPGCUWSubSchema = new LPGCUWSubSchema();

		tLPGCUWSubSchema.setEdorNo(aLPGCUWMasterSchema.getEdorNo());
		tLPGCUWSubSchema.setEdorType(aLPGCUWMasterSchema.getEdorType());
		tLPGCUWSubSchema.setGrpContNo(aLPGCUWMasterSchema.getGrpContNo());
		tLPGCUWSubSchema.setProposalGrpContNo(aLPGCUWMasterSchema
				.getProposalGrpContNo());
		tLPGCUWSubSchema.setUWNo(aLPGCUWMasterSchema.getUWNo());
		tLPGCUWSubSchema.setAppntName(aLPGCUWMasterSchema.getAppntName());
		tLPGCUWSubSchema.setAppntNo(aLPGCUWMasterSchema.getAppntNo());
		tLPGCUWSubSchema.setInsuredName(aLPGCUWMasterSchema.getInsuredName());
		tLPGCUWSubSchema.setInsuredNo(aLPGCUWMasterSchema.getInsuredNo());
		tLPGCUWSubSchema.setAgentCode(aLPGCUWMasterSchema.getAgentCode());
		tLPGCUWSubSchema.setAgentGroup(aLPGCUWMasterSchema.getAgentGroup());
		tLPGCUWSubSchema.setAutoUWFlag(aLPGCUWMasterSchema.getAutoUWFlag());
		tLPGCUWSubSchema.setPassFlag(aLPGCUWMasterSchema.getPassFlag());
		tLPGCUWSubSchema.setUWIdea(aLPGCUWMasterSchema.getUWIdea());
		tLPGCUWSubSchema.setUWGrade(aLPGCUWMasterSchema.getUWGrade());
		tLPGCUWSubSchema.setAppGrade(aLPGCUWMasterSchema.getUWGrade());
		tLPGCUWSubSchema.setPostponeDay(aLPGCUWMasterSchema.getPostponeDay());
		tLPGCUWSubSchema.setPostponeDate(aLPGCUWMasterSchema.getPostponeDate());
		tLPGCUWSubSchema.setState(aLPGCUWMasterSchema.getState());
		tLPGCUWSubSchema.setUWIdea(aLPGCUWMasterSchema.getUWIdea());
		tLPGCUWSubSchema.setUpReportContent(aLPGCUWMasterSchema
				.getUpReportContent());
		tLPGCUWSubSchema.setHealthFlag(aLPGCUWMasterSchema.getHealthFlag());
		tLPGCUWSubSchema.setQuesFlag(aLPGCUWMasterSchema.getQuesFlag());
		tLPGCUWSubSchema.setSpecFlag(aLPGCUWMasterSchema.getSpecFlag());
		tLPGCUWSubSchema.setAddPremFlag(aLPGCUWMasterSchema.getAddPremFlag());
		tLPGCUWSubSchema.setAddPremReason(aLPGCUWMasterSchema
				.getAddPremReason());
		tLPGCUWSubSchema.setReportFlag(aLPGCUWMasterSchema.getReportFlag());
		tLPGCUWSubSchema.setPrintFlag(aLPGCUWMasterSchema.getPrintFlag());
		tLPGCUWSubSchema.setPrintFlag2(aLPGCUWMasterSchema.getPrintFlag2());
		tLPGCUWSubSchema.setChangePolFlag(aLPGCUWMasterSchema
				.getChangePolFlag());
		tLPGCUWSubSchema.setChangePolReason(aLPGCUWMasterSchema
				.getChangePolReason());
		tLPGCUWSubSchema.setSpecReason(aLPGCUWMasterSchema.getSpecFlag());
		tLPGCUWSubSchema.setManageCom(aLPGCUWMasterSchema.getManageCom());
		tLPGCUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPGCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		tLPGCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPGCUWSubSchema.setMakeTime(PubFun.getCurrentTime());

		mLPGCUWSubSet.add(tLPGCUWSubSchema);
		return tLPGCUWSubSchema;
	}

	private boolean prepareOutputData() {
		mMap.put(mLPGrpEdorMainSet, "UPDATE");
		mMap.put(mLPGrpEdorItemSet, "UPDATE");
		mMap.put(mLPGrpContSet, "UPDATE");
		mMap.put(mLPGrpPolSet, "UPDATE");
		mMap.put(mLPGUWMasterSet, "DELETE&INSERT");
		mMap.put(mLPGUWSubSet, "INSERT");
		mMap.put(mLPGCUWMasterSet, "DELETE&INSERT");
		mMap.put(mLPGCUWSubSet, "INSERT");
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

}
