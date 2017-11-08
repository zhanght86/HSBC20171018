/*
 * @(#)PersonUnionBL.java	2005-05-31
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPUWMasterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPUWMasterSchema;
import com.sinosoft.lis.schema.LPUWSubSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPUWMasterSet;
import com.sinosoft.lis.vschema.LPUWSubSet;
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
 * Description: 保全人工核保-险种层处理类
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
 * @CreateDate：2005-05-31
 */
public class PEdorPolManuUWBL {
private static Logger logger = Logger.getLogger(PEdorPolManuUWBL.class);
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
	/** 传输数据 */
	private TransferData mTransferData = new TransferData();
	private LPPolSchema mLPPolSchema = new LPPolSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	// 保全险种核保记录
	private LPUWMasterSet mLPUWMasterSet = new LPUWMasterSet();
	private LPUWSubSet mLPUWSubSet = new LPUWSubSet();

	private String mEdorAcceptNo = "";

	private String mUWFlag = ""; // 核保通过标志
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mPostponeDate = "";// 延迟时间
	private String mUWIdea = ""; // 核保意见
	private String mUpReportContent = ""; // 上报内容
	private String mNoSubmit = ""; // 不提交标志

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorPolManuUWBL() {
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

		// ===del===zhangtao===2006-11-3===经与李月沟通，去掉此项校验============BGN====================
		// //校验险种所在批单核保信息
		// if (!checkLPEdorMainUW())
		// {
		// return false;
		// }
		// logger.debug("after checkLPEdorMainUW...");
		// ===del===zhangtao===2006-11-3===经与李月沟通，去掉此项校验============END====================

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
		// 查询保全险种信息
		if (!getLPPol()) {
			return false;
		}

		// 查询保全项目信息
		if (!getLPEdorItem()) {
			return false;
		}
		if (mLPEdorItemSchema != null) {
			mLPEdorItemSchema.setUWFlag(mUWFlag);
			mLPEdorItemSchema.setUWDate(mCurrentDate);
			mLPEdorItemSchema.setUWTime(mCurrentTime);
			mLPEdorItemSchema.setUWOperator(mGlobalInput.Operator);
			// mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
			mLPEdorItemSchema.setModifyDate(mCurrentDate);
			mLPEdorItemSchema.setModifyTime(mCurrentTime);

			map.put(mLPEdorItemSchema, "UPDATE");
		}

		mLPPolSchema.setUWFlag(mUWFlag);
		mLPPolSchema.setUWCode(mGlobalInput.Operator);
		mLPPolSchema.setUWDate(mCurrentDate);
		mLPPolSchema.setUWTime(mCurrentTime);
		mLPPolSchema.setOperator(mGlobalInput.Operator);
		mLPPolSchema.setModifyDate(mCurrentDate);
		mLPPolSchema.setModifyTime(mCurrentTime);

		// 生成保全险种核保主表信息
		LPUWMasterSchema tLPUWMasterSchema = prepareLPUWMaster(mLPPolSchema);
		if (tLPUWMasterSchema != null) {
			mLPUWMasterSet.add(tLPUWMasterSchema);
		}

		// 生成保全险种核保子表信息
		LPUWSubSchema tLPUWSubSchema = prepareLPUWSub(tLPUWMasterSchema);
		if (tLPUWSubSchema != null) {
			mLPUWSubSet.add(tLPUWSubSchema);
		}

		map.put(mLPPolSchema, "UPDATE");
		map.put(mLPUWMasterSet, "DELETE&INSERT");
		map.put(mLPUWSubSet, "INSERT");
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
		mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName(
				"LPPolSchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		mPostponeDate = (String) mTransferData.getValueByName("PostponeDate");
		mNoSubmit = (String) mTransferData.getValueByName("NoSubmit");// 不提交标志

		if (mUWFlag == null || mUWFlag.trim().equals("")) {
			CError tError = new CError();
			tError.errorMessage = "前台传输数据mUWFlag失败!";
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
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 查询保全项目信息
	 * 
	 * @return boolean
	 */
	private boolean getLPEdorItem() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
		tLPEdorItemDB.setEdorNo(mLPPolSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPPolSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPPolSchema.getContNo());
//		tLPEdorItemDB.setPolNo(mLPPolSchema.getPolNo());
//		tLPEdorItemDB.setInsuredNo(mLPPolSchema.getInsuredNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全项目信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPEdorItemSet != null && tLPEdorItemSet.size() == 1) {
			mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		} else {
			// 返回 null 但并不报错
			mLPEdorItemSchema = null;
		}

		return true;
	}

	/**
	 * 查询保全险种信息
	 * 
	 * @return boolean
	 */
	private boolean getLPPol() {
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPPolSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPPolSchema.getEdorType());
		tLPPolDB.setPolNo(mLPPolSchema.getPolNo());
		if (!tLPPolDB.getInfo()) {
			CError tError = new CError();
			tError.errorMessage = "查询保全险种信息出错:";
			mErrors.addOneError(tError);
			return false;
		}
		mLPPolSchema.setSchema(tLPPolDB.getSchema());

		return true;
	}

	/**
	 * 校验保全批单核保信息
	 * 
	 * @return boolean
	 */
	private boolean checkLPEdorMainUW() {
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mEdorAcceptNo);
		tLPEdorMainDB.setEdorNo(mLPPolSchema.getEdorNo());
		tLPEdorMainDB.setContNo(mLPPolSchema.getContNo());
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

		String sEdorMainUWState = tLPEdorMainSet.get(1).getUWState();

		if (sEdorMainUWState == null || sEdorMainUWState.trim().equals("")
				|| sEdorMainUWState.trim().equals("0")
				|| sEdorMainUWState.trim().equals("5")) {
			// 险种所在批单尚未下人工核保结论
			return true;
		} else {
			// 险种所在批单已经下人工核保结论
			CError tError = new CError();
			tError.errorMessage = "险种所在保单已经下人工核保结论，不允许再给险种下核保结论!";
			mErrors.addOneError(tError);
			return false;
		}
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
		sqlbv.put("EdorNo", mLPPolSchema.getEdorNo());

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
	 * 生成保全险种核保主表信息
	 * 
	 * @return boolean
	 */
	private LPUWMasterSchema prepareLPUWMaster(LPPolSchema tLPPolSchema) {
		int tUWNo = 0; // 核保顺序号
		LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();

		LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
		tLPUWMasterDB.setEdorNo(tLPPolSchema.getEdorNo());
		tLPUWMasterDB.setEdorType(tLPPolSchema.getEdorType());
		tLPUWMasterDB.setPolNo(tLPPolSchema.getPolNo());
		LPUWMasterSet tLPUWMasterSet = tLPUWMasterDB.query();
		if (tLPUWMasterSet == null || tLPUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPUWMasterSchema.setEdorNo(tLPPolSchema.getEdorNo());
			tLPUWMasterSchema.setEdorType(tLPPolSchema.getEdorType());
			tLPUWMasterSchema.setGrpContNo(tLPPolSchema.getGrpContNo());
			tLPUWMasterSchema.setContNo(tLPPolSchema.getContNo());
			tLPUWMasterSchema.setProposalContNo(tLPPolSchema.getContNo());
			tLPUWMasterSchema.setPolNo(tLPPolSchema.getPolNo());
			tLPUWMasterSchema.setProposalNo(tLPPolSchema.getProposalNo());
			tLPUWMasterSchema.setAutoUWFlag("2");
			tLPUWMasterSchema.setPassFlag(mUWFlag);
			tLPUWMasterSchema.setUWIdea(mUWIdea);
			tLPUWMasterSchema.setUWGrade("A"); // ？？
			tLPUWMasterSchema.setAppGrade("A"); // ？？
			tLPUWMasterSchema.setPostponeDay("");// ？？
			tLPUWMasterSchema.setPostponeDate(mPostponeDate);
			tLPUWMasterSchema.setState(mUWFlag);
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
			tLPUWMasterSchema.setManageCom(mGlobalInput.ManageCom);
			tLPUWMasterSchema.setMakeDate(mCurrentDate);
			tLPUWMasterSchema.setMakeTime(mCurrentTime);
		} else {
			tUWNo = tLPUWMasterSet.get(1).getUWNo() + 1;
			tLPUWMasterSchema = tLPUWMasterSet.get(1);
			tLPUWMasterSchema.setAutoUWFlag("2");
			tLPUWMasterSchema.setPassFlag(mUWFlag);
			tLPUWMasterSchema.setUWIdea(mUWIdea);
		}
		tLPUWMasterSchema.setUWNo(tUWNo);
		tLPUWMasterSchema.setAppntName(tLPPolSchema.getAppntName());
		tLPUWMasterSchema.setAppntNo(tLPPolSchema.getAppntNo());
		tLPUWMasterSchema.setInsuredName(tLPPolSchema.getInsuredName());
		tLPUWMasterSchema.setInsuredNo(tLPPolSchema.getInsuredNo());
		tLPUWMasterSchema.setAgentCode(tLPPolSchema.getAgentCode());
		tLPUWMasterSchema.setAgentGroup(tLPPolSchema.getAgentGroup());
		tLPUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPUWMasterSchema.setModifyDate(mCurrentDate);
		tLPUWMasterSchema.setModifyTime(mCurrentTime);

		return tLPUWMasterSchema;
	}

	/**
	 * 生成保全险种核保轨迹表信息
	 * 
	 * @return boolean
	 */
	private LPUWSubSchema prepareLPUWSub(LPUWMasterSchema tLPUWMasterSchema) {
		LPUWSubSchema tLPUWSubSchema = new LPUWSubSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLPUWSubSchema, tLPUWMasterSchema);
		tLPUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPUWSubSchema.setMakeDate(mCurrentDate);
		tLPUWSubSchema.setMakeTime(mCurrentTime);
		tLPUWSubSchema.setModifyDate(mCurrentDate);
		tLPUWSubSchema.setModifyTime(mCurrentTime);

		return tLPUWSubSchema;
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

		LPPolSchema mLPPolSchema = new LPPolSchema();
		mLPPolSchema.setEdorNo("410000000000902");
		mLPPolSchema.setEdorType("FM");
		mLPPolSchema.setContNo("230110000000520");
		mLPPolSchema.setPolNo("210110000001052");
		mLPPolSchema.setInsuredNo("0000497650");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWFlag", "4");
		tTransferData.setNameAndValue("UWIdea", " test by zhangtao");
		tTransferData.setNameAndValue("PostponeDate", "");
		tTransferData.setNameAndValue("AppUser", "");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		tVData.add(mLPPolSchema);

		PEdorPolManuUWBL tPEdorPolManuUWBL = new PEdorPolManuUWBL();

		if (!tPEdorPolManuUWBL.submitData(tVData, "")) {
			logger.debug(tPEdorPolManuUWBL.mErrors.getError(0).errorMessage);
		}

	}

}
