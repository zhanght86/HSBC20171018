/*
 * @(#)GEdorGrpPolManuUWBL.java	2006-06-21
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPGUWMasterDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPGUWMasterSchema;
import com.sinosoft.lis.schema.LPGUWSubSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LPGUWMasterSet;
import com.sinosoft.lis.vschema.LPGUWSubSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.service.BusinessService;
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
 * Description: 保全人工核保-团体险种层处理类
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
 * @CreateDate：2006-06-21
 */
public class GEdorGrpPolManuUWBL implements BusinessService{
private static Logger logger = Logger.getLogger(GEdorGrpPolManuUWBL.class);
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
	private LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
	private LPPolSet mLPPolSet = new LPPolSet();

	// 保全险种核保记录
	private LPGUWMasterSet mLPGUWMasterSet = new LPGUWMasterSet();
	private LPGUWSubSet mLPGUWSubSet = new LPGUWSubSet();

	private String mEdorAcceptNo = "";

	private String mUWFlag = ""; // 核保通过标志
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mPostponeDate = "";// 延迟时间
	private String mUWIdea = ""; // 核保意见
	private String mUpReportContent = ""; // 上报内容

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public GEdorGrpPolManuUWBL() {
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
		mResult.clear();
		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		// 查询保全团体险种信息
		if (!getLPGrpPol()) {
			return false;
		}

		mLPGrpPolSchema.setUWFlag(mUWFlag);
		mLPGrpPolSchema.setUWOperator(mGlobalInput.Operator);
		mLPGrpPolSchema.setUWDate(mCurrentDate);
		mLPGrpPolSchema.setUWTime(mCurrentTime);
		mLPGrpPolSchema.setModifyDate(mCurrentDate);
		mLPGrpPolSchema.setModifyTime(mCurrentTime);

		// 生成保全团体险种核保主表信息
		LPGUWMasterSchema tLPGUWMasterSchema = prepareLPGUWMaster(mLPGrpPolSchema);
		if (tLPGUWMasterSchema != null) {
			mLPGUWMasterSet.add(tLPGUWMasterSchema);
		}

		// 生成保全团体险种核保子表信息
		LPGUWSubSchema tLPGUWSubSchema = prepareLPGUWSub(tLPGUWMasterSchema);
		if (tLPGUWSubSchema != null) {
			mLPGUWSubSet.add(tLPGUWSubSchema);
		}

		map.put(mLPGrpPolSchema, "UPDATE");
		map.put(mLPGUWMasterSet, "DELETE&INSERT");
		map.put(mLPGUWSubSet, "INSERT");

		// 处理该团体险种下的所有个人险种 add by zhangtao 2006-11-4
		// 查询团体险种下未核保的个人险种，统一置值
		if (!getLPPol()) {
			return false;
		}

		String sPolUWFlag = "";
		if (mUWFlag.equals("1")) {
			// 如果团体险种层的核保结论为拒保时，系统自动批量赋予所有被保险人的险种结论为拒保
			sPolUWFlag = mUWFlag;
		} else {
			// 如果团体险种层的核保结论为除拒保外的其他核保结论，系统都自动批量赋予其他被保险人的险种结论为标准承保
			sPolUWFlag = "9";
		}

		for (int i = 1; i <= mLPPolSet.size(); i++) {
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("UWFlag", sPolUWFlag);
			tTransferData.setNameAndValue("UWIdea", mUWIdea);
			tTransferData.setNameAndValue("PostponeDate", mPostponeDate);
			tTransferData.setNameAndValue("NoSubmit", "1");

			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);
			tVData.add(mLPPolSet.get(i));

			PEdorPolManuUWBL tPEdorPolManuUWBL = new PEdorPolManuUWBL();

			if (!tPEdorPolManuUWBL.submitData(tVData, "")) {
				mErrors.copyAllErrors(tPEdorPolManuUWBL.mErrors);
				return false;
			}

			map.add((MMap) tPEdorPolManuUWBL.getResult().getObjectByObjectName(
					"MMap", 0));

		}

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
		mLPGrpPolSchema = (LPGrpPolSchema) mInputData.getObjectByObjectName(
				"LPGrpPolSchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		mPostponeDate = (String) mTransferData.getValueByName("PostponeDate");

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
	 * 查询保全险种信息
	 * 
	 * @return boolean
	 */
	private boolean getLPGrpPol() {
		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		tLPGrpPolDB.setEdorNo(mLPGrpPolSchema.getEdorNo());
		tLPGrpPolDB.setEdorType(mLPGrpPolSchema.getEdorType());
		tLPGrpPolDB.setGrpPolNo(mLPGrpPolSchema.getGrpPolNo());
		if (!tLPGrpPolDB.getInfo()) {
			CError tError = new CError();
			tError.errorMessage = "查询保全险种信息出错:";
			mErrors.addOneError(tError);
			return false;
		}
		mLPGrpPolSchema.setSchema(tLPGrpPolDB.getSchema());

		return true;
	}

	/**
	 * 查询保全个人险种信息
	 * 
	 * @return boolean
	 */
	private boolean getLPPol() {
		String sql = " select * from lppol where 1=1 " + " and edorno = '"
				+ "?edorno?" + "' and edortype = '"
				+ "?edortype?" + "' and grppolno = '"
				+ "?grppolno?" + "' ";// 如果团体险种层的核保结论为拒保时，系统自动批量赋予所有被保险人的险种结论为拒保
		if (!mUWFlag.equals("1")) {
			// 如果团体险种层的核保结论为除拒保外的其他核保结论，系统都自动批量赋予其他被保险人的险种结论为标准承保
			sql += "and (uwflag is null or uwflag in ('0','5', '9'))";
		}
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edorno", mLPGrpPolSchema.getEdorNo());
		sqlbv.put("edortype", mLPGrpPolSchema.getEdorType());
		sqlbv.put("grppolno", mLPGrpPolSchema.getGrpPolNo());
		LPPolDB tLPPolDB = new LPPolDB();
		mLPPolSet = tLPPolDB.executeQuery(sqlbv);
		if (tLPPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "个人险种信息查询失败");
			return false;
		}
		return true;
	}

	/**
	 * 生成保全险种核保主表信息
	 * 
	 * @return boolean
	 */
	private LPGUWMasterSchema prepareLPGUWMaster(LPGrpPolSchema tLPGrpPolSchema) {
		int tUWNo = 0; // 核保顺序号
		LPGUWMasterSchema tLPGUWMasterSchema = new LPGUWMasterSchema();

		LPGUWMasterDB tLPGUWMasterDB = new LPGUWMasterDB();
		tLPGUWMasterDB.setEdorNo(tLPGrpPolSchema.getEdorNo());
		tLPGUWMasterDB.setEdorType(tLPGrpPolSchema.getEdorType());
		tLPGUWMasterDB.setGrpPolNo(tLPGrpPolSchema.getGrpPolNo());
		LPGUWMasterSet tLPGUWMasterSet = tLPGUWMasterDB.query();
		if (tLPGUWMasterSet == null || tLPGUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPGUWMasterSchema.setEdorNo(tLPGrpPolSchema.getEdorNo());
			tLPGUWMasterSchema.setEdorType(tLPGrpPolSchema.getEdorType());
			tLPGUWMasterSchema.setGrpContNo(tLPGrpPolSchema.getGrpContNo());
			tLPGUWMasterSchema.setProposalGrpContNo(tLPGrpPolSchema
					.getGrpContNo());
			tLPGUWMasterSchema.setGrpPolNo(tLPGrpPolSchema.getGrpPolNo());
			// added by liuzhao 2007-08-13 GrpProposalNo should not null
			tLPGUWMasterSchema.setGrpProposalNo(tLPGrpPolSchema
					.getGrpProposalNo());
			// end here
			tLPGUWMasterSchema.setAutoUWFlag("2");
			tLPGUWMasterSchema.setPassFlag(mUWFlag);
			tLPGUWMasterSchema.setUWIdea(mUWIdea);
			tLPGUWMasterSchema.setUWGrade("A"); // ？？
			tLPGUWMasterSchema.setAppGrade("A"); // ？？
			tLPGUWMasterSchema.setPostponeDay("");// ？？
			tLPGUWMasterSchema.setPostponeDate(mPostponeDate);
			tLPGUWMasterSchema.setState(mUWFlag);
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
			tLPGUWMasterSchema.setManageCom(mGlobalInput.ManageCom);
			tLPGUWMasterSchema.setMakeDate(mCurrentDate);
			tLPGUWMasterSchema.setMakeTime(mCurrentTime);
		} else {
			tUWNo = tLPGUWMasterSet.get(1).getUWNo() + 1;
			tLPGUWMasterSchema = tLPGUWMasterSet.get(1);
			tLPGUWMasterSchema.setAutoUWFlag("2");
			tLPGUWMasterSchema.setPassFlag(mUWFlag);
			tLPGUWMasterSchema.setUWIdea(mUWIdea);
		}
		tLPGUWMasterSchema.setUWNo(tUWNo);
		tLPGUWMasterSchema.setAppntName(tLPGrpPolSchema.getGrpName());
		tLPGUWMasterSchema.setAppntNo(tLPGrpPolSchema.getCustomerNo());
		tLPGUWMasterSchema.setInsuredName("");
		tLPGUWMasterSchema.setInsuredNo("");
		tLPGUWMasterSchema.setAgentCode(tLPGrpPolSchema.getAgentCode());
		tLPGUWMasterSchema.setAgentGroup(tLPGrpPolSchema.getAgentGroup());
		tLPGUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPGUWMasterSchema.setModifyDate(mCurrentDate);
		tLPGUWMasterSchema.setModifyTime(mCurrentTime);

		return tLPGUWMasterSchema;
	}

	/**
	 * 生成保全险种核保轨迹表信息
	 * 
	 * @return boolean
	 */
	private LPGUWSubSchema prepareLPGUWSub(LPGUWMasterSchema tLPGUWMasterSchema) {
		LPGUWSubSchema rLPGUWSubSchema = new LPGUWSubSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(rLPGUWSubSchema, tLPGUWMasterSchema);
		rLPGUWSubSchema.setOperator(mGlobalInput.Operator);
		rLPGUWSubSchema.setMakeDate(mCurrentDate);
		rLPGUWSubSchema.setMakeTime(mCurrentTime);
		rLPGUWSubSchema.setModifyDate(mCurrentDate);
		rLPGUWSubSchema.setModifyTime(mCurrentTime);

		return rLPGUWSubSchema;
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

		LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
		mLPGrpPolSchema.setEdorNo("6020070719005320");
		mLPGrpPolSchema.setEdorType("NI");
		mLPGrpPolSchema.setGrpContNo("880468445410");
		mLPGrpPolSchema.setGrpPolNo("220270000000161");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", "6120070719004766");
		tTransferData.setNameAndValue("UWFlag", "q");
		tTransferData.setNameAndValue("UWIdea", " test by zhangtao");
		tTransferData.setNameAndValue("PostponeDate", "");
		tTransferData.setNameAndValue("AppUser", "");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		tVData.add(mLPGrpPolSchema);

		GEdorGrpPolManuUWBL tGEdorGrpPolManuUWBL = new GEdorGrpPolManuUWBL();

		if (!tGEdorGrpPolManuUWBL.submitData(tVData, "")) {
			logger.debug(tGEdorGrpPolManuUWBL.mErrors.getError(0).errorMessage);
		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

}
