/*
 * @(#)PersonUnionBL.java	2005-06-01
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPUWMasterMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPCUWSubSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPUWMasterMainSchema;
import com.sinosoft.lis.schema.LPUWSubMainSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LPCUWMasterSet;
import com.sinosoft.lis.vschema.LPCUWSubSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPUWMasterMainSet;
import com.sinosoft.lis.vschema.LPUWSubMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全人工核保-保单层处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-06-01
 */
public class PEdorContManuUWBL {
private static Logger logger = Logger.getLogger(PEdorContManuUWBL.class);
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

	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPContSchema mLPContSchema;
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPPolSet mLPPolSet = new LPPolSet();
	private String mUWFlag = ""; // 核保通过标志
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mPostponeDate = ""; // 延迟时间
	private String mUWIdea = ""; // 核保意见
	private String mUpReportContent = ""; // 上报内容
	private String mNoSubmit = ""; // 不提交标志

	public static String mEDORAPP_UWPASS = "9"; // 保全申请人工核保通过 ？暂不确定编码？
	public static String mEDORAPP_UWSTOP = "1"; // 保全申请人工核不保通过？暂不确定编码？

	// 保全保单核保记录
	private LPCUWMasterSet mLPCUWMasterSet = new LPCUWMasterSet();
	private LPCUWSubSet mLPCUWSubSet = new LPCUWSubSet();
	// 保全批单核保记录
	private LPUWMasterMainSet mLPUWMasterMainSet = new LPUWMasterMainSet();
	private LPUWSubMainSet mLPUWSubMainSet = new LPUWSubMainSet();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	private TransferData mReturnTransferData = new TransferData();

	public PEdorContManuUWBL() {
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

		// //校验核保级别
		// if (!checkUserGrade())
		// {
		// return false;
		// }
		// logger.debug("after checkUserGrade...");

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
		// 查询保全批单信息
		if (!getLPEdorMain()) {
			return false;
		}
		
		//由于保全确认可以强制提交人工核保，所以注释掉下面这段校验
// 校验保全申请是否已经下了人工核保结论
//		if (!checkLPEdorAppUW()) {
//			return false;
//		}

		// 查询保单信息（为创建保全批单核保主信息准备数据）
		if (!getLCCont()) {
			return false;
		}

		// 查询保全保单信息
		if (!getLPCont()) {
			return false;
		}
		if (mLPContSchema != null) {
			// 如果该批单有保单信息变更，则对保单下核保结论
			mLPContSchema.setUWFlag(mUWFlag);
			mLPContSchema.setUWOperator(mGlobalInput.Operator);
			mLPContSchema.setUWDate(mCurrentDate);
			mLPContSchema.setUWTime(mCurrentTime);
			// mLPContSchema.setOperator(mGlobalInput.Operator);
			mLPContSchema.setModifyDate(mCurrentDate);
			mLPContSchema.setModifyTime(mCurrentTime);

			// 生成保全保单核保主表信息
			LPCUWMasterSchema tLPCUWMasterSchema = prepareLPCUWMaster(mLPContSchema);
			if (tLPCUWMasterSchema != null) {
				mLPCUWMasterSet.add(tLPCUWMasterSchema);
			}

			// 生成保全保单核保轨迹信息
			LPCUWSubSchema tLPCUWSubSchema = prepareLPCUWSub(tLPCUWMasterSchema,mLPContSchema);
			if (tLPCUWSubSchema != null) {
				mLPCUWSubSet.add(tLPCUWSubSchema);
			}
			map.put(mLPContSchema, "UPDATE");
			map.put(mLPCUWMasterSet, "DELETE&INSERT");
			map.put(mLPCUWSubSet, "INSERT");
		}

		// <!-- XinYQ modified on 2006-07-11 : 如果是团险复用则去掉险种校验 : BGN -->
		// 校验保单下所有险种核保信息
		if (mLPContSchema != null
				&& mLPContSchema.getGrpContNo().equals("00000000000000000000")) {
			if (!checkPolUW()) {
				return false;
			}
		}
		// <!-- XinYQ modified on 2006-07-11 : 如果是团险复用则去掉险种校验 : END -->

		mLPEdorMainSchema.setUWOperator(mGlobalInput.Operator);
		mLPEdorMainSchema.setUWState(mUWFlag);
		mLPEdorMainSchema.setUWDate(mCurrentDate);
		mLPEdorMainSchema.setUWTime(mCurrentTime);
		// mLPEdorMainSchema.setOperator(mGlobalInput.Operator);
		mLPEdorMainSchema.setModifyDate(mCurrentDate);
		mLPEdorMainSchema.setModifyTime(mCurrentTime);

		// 生成保全批单核保主表信息
		LPUWMasterMainSchema tLPUWMasterMainSchema = prepareLPUWMasterMain(mLPEdorMainSchema);
		if (tLPUWMasterMainSchema != null) {
			mLPUWMasterMainSet.add(tLPUWMasterMainSchema);
		}

		// 生成保全批单核保轨迹信息
		LPUWSubMainSchema tLPUWSubMainSchema = prepareLPUWSubMain(tLPUWMasterMainSchema);
		if (tLPUWSubMainSchema != null) {
			mLPUWSubMainSet.add(tLPUWSubMainSchema);
		}

		map.put(mLPEdorMainSchema, "UPDATE");
		map.put(mLPUWMasterMainSet, "DELETE&INSERT");
		map.put(mLPUWSubMainSet, "INSERT");

		// ===DEL====hangtao===2005-11-06===保全申请核保结论由自动改为手动===BGN=============
		// //****************************************************
		// // 自动判断是否该保全申请下所有保单都已下核保结论，
		// // 返回判断结果，如果该保全申请下所有保单都已下核保结论，
		// // 在jsp中进一步处理
		// //****************************************************
		// if (!autoUWEdorApp())
		// {
		// return false;
		// }
		// ===DEL====hangtao===2005-11-06===保全申请核保结论由自动改为手动===END=============
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

		mLPEdorMainSchema = (LPEdorMainSchema) mInputData
				.getObjectByObjectName("LPEdorMainSchema", 0);

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
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 查询保全批单信息
	 * 
	 * @return boolean
	 */
	private boolean getLPEdorMain() {
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
		tLPEdorMainDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
		tLPEdorMainDB.setContNo(mLPEdorMainSchema.getContNo());
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
		if (tLPEdorMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全批单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPEdorMainSet == null || tLPEdorMainSet.size() != 1) {
			CError tError = new CError();
			tError.errorMessage = "保全批单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorMainSchema = tLPEdorMainSet.get(1);

		return true;
	}

	/**
	 * 查询保单信息
	 * 
	 * @return boolean
	 */
	private boolean getLCCont() {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorMainSchema.getContNo());
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLCContSet == null || tLCContSet.size() != 1) {
			CError tError = new CError();
			tError.errorMessage = "保单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema = tLCContSet.get(1);

		return true;
	}

	/**
	 * 查询保全保单信息
	 * 
	 * @return boolean
	 */
	private boolean getLPCont() {
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
		tLPContDB.setContNo(mLPEdorMainSchema.getContNo());
		LPContSet tLPContSet = tLPContDB.query();
		if (tLPContDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPContDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全保单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPContSet != null && tLPContSet.size() == 1) {
			mLPContSchema = tLPContSet.get(1);
		} else {
			// 返回 null 但并不报错
			mLPContSchema = null;
			CError.buildErr(this, "保全合同信息查询失败！");
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
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
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
		if (sEdorAppUWState == null || sEdorAppUWState.trim().equals("")
				|| sEdorAppUWState.trim().equals("0")
				|| sEdorAppUWState.trim().equals("5")
				|| sEdorAppUWState.trim().equals("S")) // add by pst on
														// 2008-07-09 新增核保上报结论
		{
			return true;
		} else {
			CError tError = new CError();
			tError.errorMessage = "保全申请已经下了核保结论，不能再对保单下核保结论!";
			mErrors.addOneError(tError);
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
				+ " and edorno = '?edorno?' ";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sql);
		sbv1.put("edorno", mLPEdorMainSchema.getEdorNo());
		LPPolDB tLPPolDB = new LPPolDB();
		mLPPolSet = tLPPolDB.executeQuery(sbv1);
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
					CError.buildErr(this, "保单下有险种尚未下核保结论，不能对保单下核保结论!") ;
					return false;
				}
				else if(!sEdorItemUWState.trim().equals("9") && mUWFlag.equals("9"))
				{
					CError.buildErr(this, "保单下有险种有非标准体核保结论，不能对保单下“标准体”核保结论!") ;
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 校验核保级别
	 * 
	 */
	private boolean checkUserGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mGlobalInput.Operator);
		if (!tLDUserDB.getInfo()) {
			mErrors.copyAllErrors(tLDUserDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "无此操作员信息，不能核保!" + "操作员："
					+ mGlobalInput.Operator;
			mErrors.addOneError(tError);
			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		if (tUWPopedom == null || tUWPopedom.trim().equals("")) {
			CError tError = new CError();
			tError.errorMessage = "操作员无核保权限，不能核保!" + "操作员："
					+ mGlobalInput.Operator;
			mErrors.addOneError(tError);
			return false;
		}

		if (mUWFlag.equals("6")) {
			// 如果上报，则不需校验核保级别
			return true;
		}

		String sql = " select * from LPEdorMain " + " where EdorNo = '?EdorNo?'" + " and AppGrade = "
				+ " (select Max(AppGrade) from LPEdorMain "
				+ " where EdorNo = '?EdorNo?')";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("EdorNo", mLPEdorMainSchema.getEdorNo());
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.executeQuery(sqlbv);
		if (tLPEdorMainSet == null || tLPEdorMainSet.size() <= 0) {
			CError tError = new CError();
			tError.errorMessage = "没有核保信息，不能核保!";
			mErrors.addOneError(tError);
			return false;
		} else {
			mAppGrade = tLPEdorMainSet.get(1).getAppGrade();
			if ((mAppGrade == null) || mAppGrade.equals("")) {
				CError tError = new CError();
				tError.errorMessage = "该投保单无核保级别，不能核保!";
				this.mErrors.addOneError(tError);
				return false;
			}

			if (tUWPopedom.compareTo(mAppGrade) < 0) {
				CError tError = new CError();
				tError.errorMessage = "已经提交上级核保，不能核保!";
				mErrors.addOneError(tError);
				return false;
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
	private LPCUWMasterSchema prepareLPCUWMaster(LPContSchema tLPContSchema) {
		int tUWNo = 0;
		LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();

		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setEdorNo(tLPContSchema.getEdorNo());
		tLPCUWMasterDB.setEdorType(tLPContSchema.getEdorType());
		tLPCUWMasterDB.setContNo(tLPContSchema.getContNo());
		LPCUWMasterSet tLPCUWMasterSet = tLPCUWMasterDB.query();
		if (tLPCUWMasterSet == null || tLPCUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPCUWMasterSchema.setEdorNo(tLPContSchema.getEdorNo());
			tLPCUWMasterSchema.setEdorType(tLPContSchema.getEdorType());
			tLPCUWMasterSchema.setGrpContNo(tLPContSchema.getGrpContNo());
			tLPCUWMasterSchema.setContNo(tLPContSchema.getContNo());
			tLPCUWMasterSchema.setProposalContNo(tLPContSchema
					.getProposalContNo());
			tLPCUWMasterSchema.setAutoUWFlag("2");
			tLPCUWMasterSchema.setPassFlag(mUWFlag);
			tLPCUWMasterSchema.setUWIdea(mUWIdea);
			tLPCUWMasterSchema.setUWGrade(mUWPopedom);
			tLPCUWMasterSchema.setAppGrade(mAppGrade);
			tLPCUWMasterSchema.setPostponeDay("");
			tLPCUWMasterSchema.setPostponeDate(mPostponeDate);
			tLPCUWMasterSchema.setState(mUWFlag);
			tLPCUWMasterSchema.setUpReportContent(mUpReportContent);
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
			tLPCUWMasterSchema.setManageCom(mGlobalInput.ManageCom);
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
		tLPCUWMasterSchema.setAppntName(tLPContSchema.getAppntName());
		tLPCUWMasterSchema.setAppntNo(tLPContSchema.getAppntNo());
		tLPCUWMasterSchema.setInsuredName(tLPContSchema.getInsuredName());
		tLPCUWMasterSchema.setInsuredNo(tLPContSchema.getInsuredNo());
		tLPCUWMasterSchema.setAgentCode(tLPContSchema.getAgentCode());
		tLPCUWMasterSchema.setAgentGroup(tLPContSchema.getAgentGroup());
		tLPCUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		return tLPCUWMasterSchema;
	}

	/**
	 * 生成保全保单核保轨迹表信息
	 * 
	 * @return boolean
	 */
	private LPCUWSubSchema prepareLPCUWSub(LPCUWMasterSchema tLPCUWMasterSchema , LPContSchema tLPContSchema) {
		LPCUWSubSchema tLPCUWSubSchema = new LPCUWSubSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLPCUWSubSchema, tLPCUWMasterSchema);
		String sql = "select max(uwno) from lpcuwsub where edorno='?edorno?' and edortype='?edortype?'"
		+ " and contno='?contno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edorno", tLPContSchema.getEdorNo());
		sqlbv.put("edortype", tLPContSchema.getEdorType());
		sqlbv.put("contno", tLPContSchema.getContNo());
		ExeSQL txExeSQL = new ExeSQL();
        int uwno = Integer.parseInt(txExeSQL.getOneValue(sqlbv));
        	
        tLPCUWSubSchema.setUWNo(uwno+1);
		tLPCUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPCUWSubSchema.setMakeDate(mCurrentDate);
		tLPCUWSubSchema.setMakeTime(mCurrentTime);
		tLPCUWSubSchema.setModifyDate(mCurrentDate);
		tLPCUWSubSchema.setModifyTime(mCurrentTime);

		return tLPCUWSubSchema;
	}

	/**
	 * 生成保全批单核保主信息
	 * 
	 * @return boolean
	 */
	private LPUWMasterMainSchema prepareLPUWMasterMain(
			LPEdorMainSchema tLPEdorMainSchema) {
		int tUWNo = 0;
		LPUWMasterMainSchema tLPUWMasterMainSchema = new LPUWMasterMainSchema();

		LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
		tLPUWMasterMainDB.setEdorNo(tLPEdorMainSchema.getEdorNo());
		tLPUWMasterMainDB.setContNo(tLPEdorMainSchema.getContNo());
		LPUWMasterMainSet tLPUWMasterMainSet = tLPUWMasterMainDB.query();
		if (tLPUWMasterMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPUWMasterMainDB.mErrors);
			mErrors.addOneError(new CError("获取保全批单核保主表信息失败！"));
			return null;
		}
		if (tLPUWMasterMainSet == null || tLPUWMasterMainSet.size() <= 0) {
			tUWNo = 1;
			tLPUWMasterMainSchema.setEdorNo(tLPEdorMainSchema.getEdorNo());
			tLPUWMasterMainSchema.setGrpContNo(mLCContSchema.getGrpContNo());
			tLPUWMasterMainSchema.setContNo(mLCContSchema.getContNo());
			tLPUWMasterMainSchema.setProposalContNo(mLCContSchema
					.getProposalContNo());
			tLPUWMasterMainSchema.setUpReportContent("");
			tLPUWMasterMainSchema.setHealthFlag("0");
			tLPUWMasterMainSchema.setQuesFlag("0");
			tLPUWMasterMainSchema.setSpecFlag("0");
			tLPUWMasterMainSchema.setAddPremFlag("0");
			tLPUWMasterMainSchema.setAddPremReason("");
			tLPUWMasterMainSchema.setReportFlag("0");
			tLPUWMasterMainSchema.setPrintFlag("0");
			tLPUWMasterMainSchema.setPrintFlag2("0");
			tLPUWMasterMainSchema.setChangePolFlag("0");
			tLPUWMasterMainSchema.setChangePolReason("");
			tLPUWMasterMainSchema.setSpecReason("");
			tLPUWMasterMainSchema
					.setManageCom(mLPEdorMainSchema.getManageCom());
			tLPUWMasterMainSchema.setMakeDate(PubFun.getCurrentDate());
			tLPUWMasterMainSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPUWMasterMainSet.get(1).getUWNo() + 1;
			tLPUWMasterMainSchema.setSchema(tLPUWMasterMainSet.get(1));
		}
		tLPUWMasterMainSchema.setUWNo(tUWNo);
		tLPUWMasterMainSchema.setAppntName(mLCContSchema.getAppntName());
		tLPUWMasterMainSchema.setAppntNo(mLCContSchema.getAppntNo());
		tLPUWMasterMainSchema.setInsuredName(mLCContSchema.getInsuredName());
		tLPUWMasterMainSchema.setInsuredNo(mLCContSchema.getInsuredNo());
		tLPUWMasterMainSchema.setAgentCode(mLCContSchema.getAgentCode());
		tLPUWMasterMainSchema.setAgentGroup(mLCContSchema.getAgentGroup());
		tLPUWMasterMainSchema.setAutoUWFlag("2");
		tLPUWMasterMainSchema.setPassFlag(mUWFlag);
		tLPUWMasterMainSchema.setPostponeDate(mPostponeDate);
		tLPUWMasterMainSchema.setState(mUWFlag);
		tLPUWMasterMainSchema.setUWIdea(mUWIdea);
		tLPUWMasterMainSchema.setUWGrade(mUWPopedom);
		tLPUWMasterMainSchema.setAppGrade(mAppGrade);
		tLPUWMasterMainSchema.setOperator(mGlobalInput.Operator);
		tLPUWMasterMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWMasterMainSchema.setModifyTime(PubFun.getCurrentTime());

		return tLPUWMasterMainSchema;
	}

	/**
	 * 生成保全批单核保轨迹表信息
	 * 
	 * @return boolean
	 */
	private LPUWSubMainSchema prepareLPUWSubMain(
			LPUWMasterMainSchema tLPUWMasterMainSchema) {
		LPUWSubMainSchema tLPUWSubMainSchema = new LPUWSubMainSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLPUWSubMainSchema, tLPUWMasterMainSchema);
		tLPUWSubMainSchema.setOperator(mGlobalInput.Operator);
		tLPUWSubMainSchema.setMakeDate(mCurrentDate);
		tLPUWSubMainSchema.setMakeTime(mCurrentTime);
		tLPUWSubMainSchema.setModifyDate(mCurrentDate);
		tLPUWSubMainSchema.setModifyTime(mCurrentTime);

		return tLPUWSubMainSchema;
	}

	/**
	 * 自动判断是否该保全申请下所有保单都已下核保结论
	 * 
	 * @return boolean
	 */
	private boolean autoUWEdorApp() {
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
		if (tLPEdorMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全申请批单信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPEdorMainSet == null || tLPEdorMainSet.size() < 1) {
			CError tError = new CError();
			tError.errorMessage = "保全申请下没有批单信息";
			mErrors.addOneError(tError);
			return false;
		}

		LPEdorMainSchema tLPEdorMainSchema;
		String sUWState;
		boolean blHasRefuse = false;
		for (int i = 1; i <= tLPEdorMainSet.size(); i++) {
			tLPEdorMainSchema = tLPEdorMainSet.get(i);
			if (tLPEdorMainSchema.getEdorNo().equals(
					mLPEdorMainSchema.getEdorNo())) {
				continue; // 当前核保批单跳过
			}
			sUWState = tLPEdorMainSchema.getUWState();
			if (sUWState == null || sUWState.trim().equals("")
					|| sUWState.trim().equals("0")
					|| sUWState.trim().equals("5")) {
				// 有批单未下人工核保结论 （0-未核保 5-自动核保未通过）
				mReturnTransferData.setNameAndValue("AutoUWFlag", "false");
				mReturnTransferData.setNameAndValue("AutoUWState", "");
				return true;
			}
			if (sUWState.trim().equals("1") || sUWState.trim().equals("2")) {
				// 如果有拒保或延期的 （1-拒保 2-延期）
				blHasRefuse = true;
				break;
			}
		}

		String sAppUWState;
		if (blHasRefuse) {
			// 有拒保或延期的
			sAppUWState = mEDORAPP_UWSTOP; // 不通过
		} else {
			sAppUWState = mEDORAPP_UWPASS; // 通过
			// 判断险种人工核保结论是否有拒保或延期
			for (int j = 1; j <= mLPPolSet.size(); j++) {
				sUWState = mLPPolSet.get(j).getUWFlag();
				if (sUWState.trim().equals("1") || sUWState.trim().equals("2")) {
					// 如果有拒保或延期的 （1-拒保 2-延期）
					sAppUWState = mEDORAPP_UWSTOP; // 不通过
					break;
				}
			}

			// 判断当前保单核保结论
			if (mUWFlag.trim().equals("1") || mUWFlag.trim().equals("2")) {
				sAppUWState = mEDORAPP_UWSTOP; // 不通过
			}
		}

		mReturnTransferData.setNameAndValue("AutoUWFlag", "true");
		mReturnTransferData.setNameAndValue("AutoUWState", sAppUWState);

		return true;
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
		tG.ManageCom = "86110000";
		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		tLPEdorMainSchema.setEdorNo("410000000000928");
		tLPEdorMainSchema.setContNo("32320000000111");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWFlag", "4");
		tTransferData.setNameAndValue("UWIdea", " test by zhangtao");
		tTransferData.setNameAndValue("PostponeDate", "");
		tTransferData.setNameAndValue("AppUser", "");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		tVData.add(tLPEdorMainSchema);

		PEdorContManuUWBL tPEdorContManuUWBL = new PEdorContManuUWBL();

		if (!tPEdorContManuUWBL.submitData(tVData, "")) {
			logger.debug(tPEdorContManuUWBL.mErrors.getError(0).errorMessage);
		}

	}
}
