package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPGCUWMasterDB;
import com.sinosoft.lis.db.LPGUWMasterMainDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LPGCUWMasterSchema;
import com.sinosoft.lis.schema.LPGCUWSubSchema;
import com.sinosoft.lis.schema.LPGUWMasterMainSchema;
import com.sinosoft.lis.schema.LPGUWSubMainSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LPGCUWMasterSet;
import com.sinosoft.lis.vschema.LPGCUWSubSet;
import com.sinosoft.lis.vschema.LPGUWMasterMainSet;
import com.sinosoft.lis.vschema.LPGUWSubMainSet;
import com.sinosoft.lis.vschema.LPGrpContSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全人工核保-团体保单层处理类
 * </p>
 */
public class GEdorGrpContManuUWBL {
private static Logger logger = Logger.getLogger(GEdorGrpContManuUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private LPGrpEdorItemSet mLPGrpEdorItemSet;
	private LPGrpContSchema mLPGrpContSchema;
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LPPolSet mLPPolSet = new LPPolSet();
	private String mUWFlag = ""; // 核保通过标志
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mPostponeDate = ""; // 延迟时间
	private String mUWIdea = ""; // 核保意见
	private String mUpReportContent = ""; // 上报内容
	private String mNoSubmit = ""; // 不提交标志
	private String mUWType = ""; // 核保通过标志

	public static String mEDORAPP_UWPASS = "9"; // 保全申请人工核保通过 ？暂不确定编码？
	public static String mEDORAPP_UWSTOP = "1"; // 保全申请人工核不保通过？暂不确定编码？

	// 保全保单核保记录
	private LPGCUWMasterSet mLPGCUWMasterSet = new LPGCUWMasterSet();
	private LPGCUWSubSet mLPGCUWSubSet = new LPGCUWSubSet();
	// 保全批单核保记录
	private LPGUWMasterMainSet mLPGUWMasterMainSet = new LPGUWMasterMainSet();
	private LPGUWSubMainSet mLPGUWSubMainSet = new LPGUWSubMainSet();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	private TransferData mReturnTransferData = new TransferData();

	public GEdorGrpContManuUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}
		logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

		if (mNoSubmit != null && mNoSubmit.equals("1")) {
			return true;
		}

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.errorMessage = "数据提交失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		// 查询团体保全批单信息
		if (!getLPGrpEdorMain()) {
			return false;
		}

		// 查询团体保全项目信息
		if (!getLPGrpEdorItem()) {
			return false;
		}

		// 校验保全申请是否已经下了人工核保结论
		/*if (!checkLPEdorAppUW()) {
			return false;
		}*/

		// //校验保单下所有险种核保信息
		// if (!checkPolUW())
		// {
		// return false;
		// }

		// 查询保单信息（为创建保全批单核保主信息准备数据）
		if (!getLCGrpCont()) {
			return false;
		}

		// 查询保全保单信息
		if (!getLPGrpCont()) {
			return false;
		}
		if (mLPGrpContSchema != null) {
			// 如果该批单有保单信息变更，则对保单下核保结论
			mLPGrpContSchema.setUWFlag(mUWFlag);
			mLPGrpContSchema.setUWOperator(mGlobalInput.Operator);
			mLPGrpContSchema.setUWDate(mCurrentDate);
			mLPGrpContSchema.setUWTime(mCurrentTime);
			mLPGrpContSchema.setModifyDate(mCurrentDate);
			mLPGrpContSchema.setModifyTime(mCurrentTime);

			// 生成保全保单核保主表信息
			LPGCUWMasterSchema tLPGCUWMasterSchema = prepareLPGCUWMaster(mLPGrpContSchema);
			if (tLPGCUWMasterSchema != null) {
				mLPGCUWMasterSet.add(tLPGCUWMasterSchema);
			}

			// 生成保全保单核保轨迹信息
			LPGCUWSubSchema tLPGCUWSubSchema = prepareLPGCUWSub(tLPGCUWMasterSchema);
			if (tLPGCUWSubSchema != null) {
				mLPGCUWSubSet.add(tLPGCUWSubSchema);
			}
			map.put(mLPGrpContSchema, "UPDATE");
			map.put(mLPGCUWMasterSet, "DELETE&INSERT");
			map.put(mLPGCUWSubSet, "INSERT");
		}

		mLPGrpEdorMainSchema.setUWOperator(mGlobalInput.Operator);
		mLPGrpEdorMainSchema.setUWState(mUWFlag);
		mLPGrpEdorMainSchema.setUWDate(mCurrentDate);
		mLPGrpEdorMainSchema.setUWTime(mCurrentTime);
		mLPGrpEdorMainSchema.setModifyDate(mCurrentDate);
		mLPGrpEdorMainSchema.setModifyTime(mCurrentTime);

		// 生成保全批单核保主表信息
		LPGUWMasterMainSchema tLPGUWMasterMainSchema = prepareLPUWMasterMain(mLPGrpEdorMainSchema);
		if (tLPGUWMasterMainSchema != null) {
			mLPGUWMasterMainSet.add(tLPGUWMasterMainSchema);
		}

		// 生成保全批单核保轨迹信息
		LPGUWSubMainSchema tLPGUWSubMainSchema = prepareLPUWSubMain(tLPGUWMasterMainSchema);
		if (tLPGUWSubMainSchema != null) {
			mLPGUWSubMainSet.add(tLPGUWSubMainSchema);
		}

		map.put(mLPGrpEdorMainSchema, "UPDATE");
		map.put(mLPGUWMasterMainSet, "DELETE&INSERT");
		map.put(mLPGUWSubMainSet, "INSERT");

		LPGrpEdorItemSchema tLPGrpEdorItemSchema;
		for (int i = 1; i <= mLPGrpEdorItemSet.size(); i++) {
			tLPGrpEdorItemSchema = mLPGrpEdorItemSet.get(i);
			tLPGrpEdorItemSchema.setUWOperator(mGlobalInput.Operator);
			tLPGrpEdorItemSchema.setUWFlag(mUWFlag);
			tLPGrpEdorItemSchema.setUWDate(mCurrentDate);
			tLPGrpEdorItemSchema.setUWTime(mCurrentTime);
			tLPGrpEdorItemSchema.setModifyDate(mCurrentDate);
			tLPGrpEdorItemSchema.setModifyTime(mCurrentTime);
		}
		map.put(mLPGrpEdorItemSet, "UPDATE");

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mLPGrpEdorMainSchema = (LPGrpEdorMainSchema) mInputData
				.getObjectByObjectName("LPGrpEdorMainSchema", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		mPostponeDate = (String) mTransferData.getValueByName("PostponeDate");
		mNoSubmit = (String) mTransferData.getValueByName("NoSubmit");// 不提交标志

		if (mUWFlag == null || mUWFlag.trim().equals("")) {
			CError tError = new CError();
			tError.errorMessage = "前台传输数据UWFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.clear();
			mResult.add(map);
			mResult.add(mReturnTransferData);
		} catch (Exception ex) {
			CError.buildErr(this, "在准备往后层处理所需要的数据时出");
			return false;
		}

		return true;
	}

	/**
	 * 查询团体保全项目信息
	 * 
	 * @return boolean
	 */
	private boolean getLPGrpEdorMain() {
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB
				.setEdorAcceptNo(mLPGrpEdorMainSchema.getEdorAcceptNo());
		// tLPGrpEdorMainDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
		tLPGrpEdorMainDB.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
		LPGrpEdorMainSet tLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
		if (tLPGrpEdorMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPGrpEdorMainDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全团体批单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPGrpEdorMainSet == null || tLPGrpEdorMainSet.size() != 1) {
			CError tError = new CError();
			tError.errorMessage = "保全批单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		mLPGrpEdorMainSchema = tLPGrpEdorMainSet.get(1);

		return true;
	}

	/**
	 * 查询团体保全项目信息
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getLPGrpEdorItem() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorMainSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
		mLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			mErrors.addOneError(new CError("查询批改项目信息失败!"));
			return false;
		}
		if (mLPGrpEdorItemSet == null || mLPGrpEdorItemSet.size() < 1) {
			CError.buildErr(this, "没有批改项目信息!");
			return false;
		}
		return true;
	}

	/**
	 * 查询保单信息
	 * 
	 * @return boolean
	 */
	private boolean getLCGrpCont() {
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
		LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();
		if (tLCGrpContDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "团体保单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLCGrpContSet == null || tLCGrpContSet.size() != 1) {
			CError tError = new CError();
			tError.errorMessage = "团体保单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContSchema = tLCGrpContSet.get(1);

		return true;
	}

	/**
	 * 查询保全保单信息
	 * 
	 * @return boolean
	 */
	private boolean getLPGrpCont() {
		LPGrpContDB tLPGrpContDB = new LPGrpContDB();
		tLPGrpContDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
		tLPGrpContDB.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
		LPGrpContSet tLPGrpContSet = tLPGrpContDB.query();
		if (tLPGrpContDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPGrpContDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全保单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPGrpContSet != null && tLPGrpContSet.size() == 1) {
			mLPGrpContSchema = tLPGrpContSet.get(1);
		} else {
			// 返回 null 但并不报错
			mLPGrpContSchema = null;
		}
		return true;
	}

	/**
	 * 校验保全申请是否已经下了人工核保结论
	 * 
	 * @return boolean
	 */
	private boolean checkLPEdorAppUW() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPGrpEdorMainSchema.getEdorAcceptNo());
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.query();
		if (tLPEdorAppDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全申请信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPEdorAppSet == null || tLPEdorAppSet.size() != 1) {
			CError tError = new CError();
			tError.errorMessage = "保全申请信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		String sEdorAppUWState = tLPEdorAppSet.get(1).getUWState();
		logger.debug("----------------------------" + sEdorAppUWState);
		if (sEdorAppUWState == null || sEdorAppUWState.trim().equals("")
				|| sEdorAppUWState.trim().equals("0")
				|| sEdorAppUWState.trim().equals("5")) {
			return true;
		} else {
			CError.buildErr(this, "保全申请已经下了核保结论，不能再对团体保单下核保结论!");
			return false;
		}
	}

	/**
	 * 校验保全批单下所有险种核保信息
	 * 
	 * @return boolean
	 */
	private boolean checkPolUW() {
		String sql = " select * from lppol where appflag <> '4' "
				+ " and edorno = '" + "?edorno?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edorno", mLPGrpEdorMainSchema.getEdorNo());
		LPPolDB tLPPolDB = new LPPolDB();
		mLPPolSet = tLPPolDB.executeQuery(sqlbv);
		if (tLPPolDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPPolDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (mLPPolSet != null && mLPPolSet.size() > 0) {
			String sEdorItemUWState;
			for (int i = 1; i <= mLPPolSet.size(); i++) {
				sEdorItemUWState = mLPPolSet.get(i).getUWFlag();
				if (sEdorItemUWState == null
						|| sEdorItemUWState.trim().equals("")
						|| sEdorItemUWState.trim().equals("0")
						|| sEdorItemUWState.trim().equals("5")) {
					CError tError = new CError();
					tError.errorMessage = "保单下有险种尚未下核保结论，不能对保单下核保结论!";
					mErrors.addOneError(tError);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 生成保全保单核保主表信息
	 * 
	 * @param tLPContSchema
	 * @return LPCUWMasterSchema
	 */
	private LPGCUWMasterSchema prepareLPGCUWMaster(
			LPGrpContSchema tLPGrpContSchema) {
		int tUWNo = 0;
		LPGCUWMasterSchema rLPGCUWMasterSchema = new LPGCUWMasterSchema();

		LPGCUWMasterDB tLPGCUWMasterDB = new LPGCUWMasterDB();
		tLPGCUWMasterDB.setEdorNo(tLPGrpContSchema.getEdorNo());
		tLPGCUWMasterDB.setEdorType(tLPGrpContSchema.getEdorType());
		tLPGCUWMasterDB.setGrpContNo(tLPGrpContSchema.getGrpContNo());
		LPGCUWMasterSet tLPGCUWMasterSet = tLPGCUWMasterDB.query();
		if (tLPGCUWMasterSet == null || tLPGCUWMasterSet.size() <= 0) {
			tUWNo = 1;
			rLPGCUWMasterSchema.setEdorNo(tLPGrpContSchema.getEdorNo());
			rLPGCUWMasterSchema.setEdorType(tLPGrpContSchema.getEdorType());
			rLPGCUWMasterSchema.setGrpContNo(tLPGrpContSchema.getGrpContNo());
			rLPGCUWMasterSchema.setGrpContNo(tLPGrpContSchema.getGrpContNo());
			rLPGCUWMasterSchema.setProposalGrpContNo(tLPGrpContSchema
					.getProposalGrpContNo());
			rLPGCUWMasterSchema.setAutoUWFlag("2");
			rLPGCUWMasterSchema.setPassFlag(mUWFlag);
			rLPGCUWMasterSchema.setUWIdea(mUWIdea);
			rLPGCUWMasterSchema.setUWGrade(mUWPopedom);
			rLPGCUWMasterSchema.setAppGrade(mAppGrade);
			rLPGCUWMasterSchema.setPostponeDay("");
			rLPGCUWMasterSchema.setPostponeDate(mPostponeDate);
			rLPGCUWMasterSchema.setState(mUWFlag);
			rLPGCUWMasterSchema.setUpReportContent(mUpReportContent);
			rLPGCUWMasterSchema.setHealthFlag("0");
			rLPGCUWMasterSchema.setQuesFlag("0");
			rLPGCUWMasterSchema.setSpecFlag("0");
			rLPGCUWMasterSchema.setAddPremFlag("0");
			rLPGCUWMasterSchema.setAddPremReason("");
			rLPGCUWMasterSchema.setReportFlag("0");
			rLPGCUWMasterSchema.setPrintFlag("0");
			rLPGCUWMasterSchema.setPrintFlag2("0");
			rLPGCUWMasterSchema.setChangePolFlag("0");
			rLPGCUWMasterSchema.setChangePolReason("");
			rLPGCUWMasterSchema.setSpecReason("");
			rLPGCUWMasterSchema.setManageCom(mGlobalInput.ManageCom);
			rLPGCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			rLPGCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPGCUWMasterSet.get(1).getUWNo() + 1;
			rLPGCUWMasterSchema = tLPGCUWMasterSet.get(1);
			rLPGCUWMasterSchema.setAutoUWFlag("2");
			rLPGCUWMasterSchema.setPassFlag(mUWFlag);
			rLPGCUWMasterSchema.setUWIdea(mUWIdea);
		}
		rLPGCUWMasterSchema.setUWNo(tUWNo);
		rLPGCUWMasterSchema.setAppntName(tLPGrpContSchema.getGrpName());
		rLPGCUWMasterSchema.setAppntNo(tLPGrpContSchema.getAppntNo());
		rLPGCUWMasterSchema.setInsuredName("");
		rLPGCUWMasterSchema.setInsuredNo("");
		rLPGCUWMasterSchema.setAgentCode(tLPGrpContSchema.getAgentCode());
		rLPGCUWMasterSchema.setAgentGroup(tLPGrpContSchema.getAgentGroup());
		rLPGCUWMasterSchema.setOperator(mGlobalInput.Operator);
		rLPGCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		rLPGCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		return rLPGCUWMasterSchema;
	}

	/**
	 * 生成保全保单核保轨迹表信息
	 * 
	 * @return boolean
	 */
	private LPGCUWSubSchema prepareLPGCUWSub(
			LPGCUWMasterSchema tLPGCUWMasterSchema) {
		LPGCUWSubSchema rLPCUWSubSchema = new LPGCUWSubSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(rLPCUWSubSchema, tLPGCUWMasterSchema);
		rLPCUWSubSchema.setOperator(mGlobalInput.Operator);
		rLPCUWSubSchema.setMakeDate(mCurrentDate);
		rLPCUWSubSchema.setMakeTime(mCurrentTime);
		rLPCUWSubSchema.setModifyDate(mCurrentDate);
		rLPCUWSubSchema.setModifyTime(mCurrentTime);

		return rLPCUWSubSchema;
	}

	/**
	 * 生成保全批单核保主信息
	 * 
	 * @return boolean
	 */
	private LPGUWMasterMainSchema prepareLPUWMasterMain(
			LPGrpEdorMainSchema tLPGrpEdorMainSchema) {
		int tUWNo = 0;
		LPGUWMasterMainSchema rLPGUWMasterMainSchema = new LPGUWMasterMainSchema();

		LPGUWMasterMainDB tLPGUWMasterMainDB = new LPGUWMasterMainDB();
		tLPGUWMasterMainDB.setEdorNo(tLPGrpEdorMainSchema.getEdorNo());
		tLPGUWMasterMainDB.setGrpContNo(tLPGrpEdorMainSchema.getGrpContNo());
		LPGUWMasterMainSet tLPGUWMasterMainSet = tLPGUWMasterMainDB.query();
		if (tLPGUWMasterMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPGUWMasterMainDB.mErrors);
			mErrors.addOneError(new CError("获取保全批单核保主表信息失败！"));
			return null;
		}
		if (tLPGUWMasterMainSet == null || tLPGUWMasterMainSet.size() <= 0) {
			tUWNo = 1;
			rLPGUWMasterMainSchema.setEdorNo(tLPGrpEdorMainSchema.getEdorNo());
			rLPGUWMasterMainSchema.setGrpContNo(tLPGrpEdorMainSchema
					.getGrpContNo());
			rLPGUWMasterMainSchema.setProposalGrpContNo(mLCGrpContSchema
					.getProposalGrpContNo());
			rLPGUWMasterMainSchema.setUpReportContent("");
			rLPGUWMasterMainSchema.setHealthFlag("0");
			rLPGUWMasterMainSchema.setQuesFlag("0");
			rLPGUWMasterMainSchema.setSpecFlag("0");
			rLPGUWMasterMainSchema.setAddPremFlag("0");
			rLPGUWMasterMainSchema.setAddPremReason("");
			rLPGUWMasterMainSchema.setReportFlag("0");
			rLPGUWMasterMainSchema.setPrintFlag("0");
			rLPGUWMasterMainSchema.setPrintFlag2("0");
			rLPGUWMasterMainSchema.setChangePolFlag("0");
			rLPGUWMasterMainSchema.setChangePolReason("");
			rLPGUWMasterMainSchema.setSpecReason("");
			rLPGUWMasterMainSchema.setManageCom(tLPGrpEdorMainSchema
					.getManageCom());
			rLPGUWMasterMainSchema.setMakeDate(PubFun.getCurrentDate());
			rLPGUWMasterMainSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPGUWMasterMainSet.get(1).getUWNo() + 1;
			rLPGUWMasterMainSchema.setSchema(tLPGUWMasterMainSet.get(1));
		}
		rLPGUWMasterMainSchema.setUWNo(tUWNo);
		rLPGUWMasterMainSchema.setAppntName(mLCGrpContSchema.getGrpName());
		rLPGUWMasterMainSchema.setAppntNo(mLCGrpContSchema.getAppntNo());
		rLPGUWMasterMainSchema.setInsuredName("");
		rLPGUWMasterMainSchema.setInsuredNo("");
		rLPGUWMasterMainSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		rLPGUWMasterMainSchema.setAgentGroup(mLCGrpContSchema.getAgentGroup());
		rLPGUWMasterMainSchema.setAutoUWFlag("2");
		rLPGUWMasterMainSchema.setPassFlag(mUWFlag);
		rLPGUWMasterMainSchema.setPostponeDate(mPostponeDate);
		rLPGUWMasterMainSchema.setState(mUWFlag);
		rLPGUWMasterMainSchema.setUWIdea(mUWIdea);
		rLPGUWMasterMainSchema.setUWGrade(mUWPopedom);
		rLPGUWMasterMainSchema.setAppGrade(mAppGrade);
		rLPGUWMasterMainSchema.setOperator(mGlobalInput.Operator);
		rLPGUWMasterMainSchema.setModifyDate(PubFun.getCurrentDate());
		rLPGUWMasterMainSchema.setModifyTime(PubFun.getCurrentTime());

		return rLPGUWMasterMainSchema;
	}

	/**
	 * 生成保全批单核保轨迹表信息
	 * 
	 * @return boolean
	 */
	private LPGUWSubMainSchema prepareLPUWSubMain(
			LPGUWMasterMainSchema tLPGUWMasterMainSchema) {
		LPGUWSubMainSchema rLPGUWSubMainSchema = new LPGUWSubMainSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(rLPGUWSubMainSchema, tLPGUWMasterMainSchema);
		rLPGUWSubMainSchema.setOperator(mGlobalInput.Operator);
		rLPGUWSubMainSchema.setMakeDate(mCurrentDate);
		rLPGUWSubMainSchema.setMakeTime(mCurrentTime);
		rLPGUWSubMainSchema.setModifyDate(mCurrentDate);
		rLPGUWSubMainSchema.setModifyTime(mCurrentTime);

		return rLPGUWSubMainSchema;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86";
		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		tLPGrpEdorMainSchema.setEdorAcceptNo("6120060523000002");
		tLPGrpEdorMainSchema.setGrpContNo("240210000000008");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWFlag", "4");
		tTransferData.setNameAndValue("UWIdea", " test by zhangtao");
		tTransferData.setNameAndValue("PostponeDate", "");
		tTransferData.setNameAndValue("AppUser", "");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		tVData.add(tLPGrpEdorMainSchema);

		GEdorGrpContManuUWBL tGEdorGrpContManuUWBL = new GEdorGrpContManuUWBL();

		if (!tGEdorGrpContManuUWBL.submitData(tVData, "")) {
			logger.debug(tGEdorGrpContManuUWBL.mErrors.getError(0).errorMessage);
		}

	}
}
