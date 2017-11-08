/*
 * @(#)LCParseGuideInUI.java	2005-01-05
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.xb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCRnewStateHistoryDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.RnewIndUWMasterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCRnewStateHistorySchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.RnewIndUWMasterSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:承保-核保确认调用
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class RnewUWConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(RnewUWConfirmAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
	private RnewIndUWMasterSchema mRnewIndUWMasterSchema = new RnewIndUWMasterSchema();
	private MMap mMap = new MMap();
    
	private String currentdate = PubFun.getCurrentDate();
    private String currenttime = PubFun.getCurrentTime();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mGrpContNo;
	private String mUWFlag;
	private LCContSchema mLCContSchema;
	private LCPolSchema mLCPolSchema;
	private LCRnewStateHistorySchema mLCRnewStateHistorySchema;
	private LCRnewStateHistorySchema DELLCRnewStateHistorySchema = new LCRnewStateHistorySchema();
	private LCRnewStateHistorySchema UPDATELCRnewStateHistorySchema = new LCRnewStateHistorySchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolDB mLCPolDB;
	private String mStopFlag = "0";
	private String mNewContNo = "0";
	private String mContNo;
	private String mPrtNo;
	private String mUWIdea;
	private String mUWCancelFlag;
	private boolean mBLUWCancelFlag;
	private String mCvaliDate;
	// 呈报到的核保等级
	private String mUWPopedom;
	// 呈报流向
	private String mUWUpReport;
	private String mSugIndUWFlag;
	private String mSugIndUWIdea;

	// 是否发出拒保延期通知书
	private String mNoticeFlag = "0";

	public RnewUWConfirmAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cOperate
	 *            输入操作
	 * @param cInputData
	 *            输入数据
	 * @return boolean
	 * 
	 */
	public boolean submitData(VData cInputData, String cOperate) 
	{

		mInputData = (VData) cInputData.clone();
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}
		logger.debug("------------------------------------------------------");
		// 进行业务处理
		if (!dealData())
		{
				return false;
		}
		logger.debug("dealData successful!");

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		return true;

	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		map.put(this.mLCPolSet, "UPDATE");
		map.put(this.mLCUWMasterSchema, "UPDATE");
		map.put(this.mRnewIndUWMasterSchema, "UPDATE");	
		map.put(this.DELLCRnewStateHistorySchema, "DELETE");
		map.put(this.UPDATELCRnewStateHistorySchema, "UPDATE");
		map.add(mMap);

		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的保单号
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if(!tLCContDB.getInfo())
		{
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "查找lccont记录失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		this.mLCContSchema=tLCContDB.getSchema();
		
		// 获得当前工作任务的印刷号
		mPrtNo = (String) mTransferData.getValueByName("PrtNo");
		if (mPrtNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中PrtNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的核保标志
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		if (mUWFlag == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中UWFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的核保意见
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		if (mUWIdea == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中UWIdea失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 核保/撤单区分标志
		mUWCancelFlag = (String) mTransferData.getValueByName("UWCancelFlag");
		if (mUWCancelFlag != null && mUWCancelFlag.equals("UWCancelFlag")) {
			mBLUWCancelFlag = true;
		}
		// 呈报到的核保员等级
		mUWPopedom = (String) mTransferData.getValueByName("UWPopedom");
		// 呈报流向
		mUWUpReport = (String) mTransferData.getValueByName("UWUpReport");
		//	
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 * 
	 */
	private boolean dealData() 
	{
		//首先修改lcpol中投保单的核保状态
		String date_sql="  select x.MissionProp3 ,x.MissionProp4,x.MissionProp9 from lwmission x where x.activityid='0000007001' " +
		 "and x.missionid='"+"?mMissionID?"+"' and x.submissionid='"+"?mSubMissionID?"+"' ";  
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(date_sql);
		sqlbv1.put("mMissionID", this.mMissionID);
		sqlbv1.put("mSubMissionID", this.mSubMissionID);
		SSRS xSSRS = new SSRS();
		ExeSQL xExeSQL = new ExeSQL();
		xSSRS = xExeSQL.execSQL(sqlbv1);
		String xProposalNo=xSSRS.GetText(1, 1);
		String xRiskCode=xSSRS.GetText(1, 2);
		String xInsuredNo=xSSRS.GetText(1, 3); //被保人号码
		mLCPolDB = new LCPolDB();
		mLCPolDB.setProposalNo(xProposalNo);
		mLCPolSchema= new LCPolSchema();
		mLCPolSchema = mLCPolDB.query().get(1);
		mLCPolSchema.setUWFlag(this.mUWFlag);
		mLCPolSchema.setModifyDate(this.currentdate);
		mLCPolSchema.setModifyTime(this.currenttime);
		mLCPolSchema.setOperator(this.mGlobalInput.Operator);
		if(mUWFlag.equals("1"))
		{
			mLCPolSchema.setRnewFlag("-2");
		}
		//取出投保单生效日期，后续生成拒保通知书时作为一个信息字段存储
		mCvaliDate = mLCPolSchema.getCValiDate();
		mLCPolSet.add(mLCPolSchema);
		//如果是拒保的，修改其lcpol中appflag='1'的记录的rnewflag，将其置为-2
		if(mUWFlag.equals("1"))
		{
			mLCPolDB = new LCPolDB();
			mLCPolDB.setContNo(this.mContNo);
			mLCPolDB.setAppFlag("1");
			mLCPolDB.setRiskCode(xRiskCode);
			mLCPolSchema= new LCPolSchema();
			mLCPolSchema = mLCPolDB.query().get(1);
			mLCPolSchema.setRnewFlag("-2");
			mLCPolSchema.setModifyDate(this.currentdate);
			mLCPolSchema.setModifyTime(this.currenttime);
			mLCPolSchema.setOperator(this.mGlobalInput.Operator);
			mLCPolSet.add(mLCPolSchema);	
		}
		//然后是lcuwmaster表
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setProposalNo(xProposalNo);
		if(tLCUWMasterDB.query().size()==0)
		{
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "LCUWMaster表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCUWMasterSchema.setSchema(tLCUWMasterDB.query().get(1));
		mLCUWMasterSchema.setUWIdea(this.mUWIdea);
		mLCUWMasterSchema.setPassFlag(this.mUWFlag);
		mLCUWMasterSchema.setState(this.mUWFlag);
		mLCUWMasterSchema.setModifyDate(this.currentdate);
		mLCUWMasterSchema.setModifyTime(this.currenttime);
		mLCUWMasterSchema.setOperator(this.mGlobalInput.Operator);

        //然后是RnewIndUWMaster表
		RnewIndUWMasterDB tRnewIndUWMasterDB = new RnewIndUWMasterDB();
		tRnewIndUWMasterDB.setContNo(mContNo);
		tRnewIndUWMasterDB.setInsuredNo(xInsuredNo);
		if(!tRnewIndUWMasterDB.getInfo())
		{
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "RnewIndUWMaster表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		mRnewIndUWMasterSchema = new RnewIndUWMasterSchema();
		mRnewIndUWMasterSchema = tRnewIndUWMasterDB.getSchema();
		
		mRnewIndUWMasterSchema.setUWIdea(this.mUWIdea);
		mRnewIndUWMasterSchema.setPassFlag(this.mUWFlag);
		mRnewIndUWMasterSchema.setState(this.mUWFlag);
		mRnewIndUWMasterSchema.setModifyDate(this.currentdate);
		mRnewIndUWMasterSchema.setModifyTime(this.currenttime);
		mRnewIndUWMasterSchema.setOperator(this.mGlobalInput.Operator);
		
		//最后是修改续保轨迹表LCRnewStateHistory
		LCRnewStateHistoryDB tLCRnewStateHistoryDB = new LCRnewStateHistoryDB();
		tLCRnewStateHistoryDB.setContNo(this.mContNo);
		tLCRnewStateHistoryDB.setRiskCode(xRiskCode);
		tLCRnewStateHistoryDB.setState("2");
		if(tLCRnewStateHistoryDB.query().size()==0)
		{
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "LCRnewStateHistory表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCRnewStateHistorySchema=tLCRnewStateHistoryDB.query().get(1);
		if(mUWFlag.equals("1"))
		{
		  //删除该险种的续保轨迹记录
			DELLCRnewStateHistorySchema.setSchema(mLCRnewStateHistorySchema);
		}
		else
		{			
			mLCRnewStateHistorySchema.setState("3");
			mLCRnewStateHistorySchema.setModifyDate(this.currentdate);
			UPDATELCRnewStateHistorySchema.setSchema(mLCRnewStateHistorySchema);
		}
		

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return boolean
	 */
	private boolean prepareTransferData() {
		logger.debug("In PrepareTransferData");
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSet tLAAgentSet = new LAAgentSet();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		tLAAgentSet.set(tLAAgentDB.query());
		if (tLAAgentSet == null || tLAAgentSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLAAgentSet.get(1).getAgentGroup() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent中的代理机构数据丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("aaaa");

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
		tLABranchGroupDB.setAgentGroup(tLAAgentSet.get(1).getAgentGroup());
		tLABranchGroupSet = tLABranchGroupDB.query();
		if (tLABranchGroupSet == null || tLABranchGroupSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLABranchGroupSet.get(1).getBranchAttr() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup中展业机构信息丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("bbb");

		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		mTransferData
		.setNameAndValue("CvaliDate", mCvaliDate);
		
		mTransferData.setNameAndValue("AgentGroup", tLAAgentSet.get(1)
				.getAgentGroup());
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("ContNo", mLCContSchema.getContNo());

		mTransferData.setNameAndValue("AppntCode", mLCContSchema.getAppntNo());
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		mTransferData.setNameAndValue("UWDate", PubFun.getCurrentDate());
		mTransferData.setNameAndValue("StopFlag", mStopFlag);
		mTransferData.setNameAndValue("NewContNo", mNewContNo);
		mTransferData.setNameAndValue("ReDisMark", "0"); // add by yaory 分保标志
															// 1-分保0不分保

		mTransferData.setNameAndValue("AppntNo", mLCContSchema.getAppntNo()); // add
																				// by
																				// xutao
																				// 人工核保返回复核
		mTransferData.setNameAndValue("Operator", mOperater); // add by xutao
																// 人工核保返回复核
		mTransferData.setNameAndValue("MakeDate", PubFun.getCurrentDate()); // add
																			// by
																			// xutao
																			// 人工核保返回复核

		/**
		 * 传入核保员代码
		 */
		mTransferData.setNameAndValue("UWUser", mOperater);

		logger.debug("ccc");
		// 对于上报的任务，工作流会再流转到人工核保，为人工核保节点准备数据
		// mTransferData.setNameAndValue("UWPopedom", mUWPopedom);
		// mTransferData.setNameAndValue("UWUpReport",mUWUpReport);
		// 0000001100节点上的上报类型
		mTransferData.setNameAndValue("ReportType", mTransferData
				.getValueByName("UWUpReport"));
		mTransferData.setNameAndValue("ProposalContNo", mLCContSchema
				.getProposalContNo());
		if (mTransferData.getValueByName("UWUpReport") != null
				&& mTransferData.getValueByName("UWUpReport").equals("0")) {
			String tSql = " select missionprop12 from lwmission where 1 =1 "
					+ " and activityid = '0000001100' and missionid = '"
					+ "?mMissionID?" + "'" + " and submissionid = '"
					+ "?mSubMissionID?" + "'";
			logger.debug("tSql:" + tSql);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("mMissionID", mMissionID);
			sqlbv2.put("mSubMissionID", mSubMissionID);
			ExeSQL tExeSQL = new ExeSQL();
			String tUWAuthority = tExeSQL.getOneValue(sqlbv2);
			if (tUWAuthority == null || tUWAuthority.equals("")) {
				CError tError = new CError();
				tError.moduleName = "UWRReportAfterInitService";
				tError.functionName = "prepareTransferData";
				tError.errorMessage = "核保师级别查询失败!";
				this.mErrors.addOneError(tError);
				return false;

			}
			mTransferData.setNameAndValue("UWAuthority", tUWAuthority);
		}
		mTransferData.setNameAndValue("ApproveDate", mLCContSchema
				.getApproveDate());

		// 根据核保结论判断是否发出过拒保延期通知书
		// 如果核保结论为拒保，则判断是否已经发送拒保通知书，如果发送过拒保通知书则不生成核保订正节点
		if (mUWFlag != null && (mUWFlag.equals("1") || mUWFlag.equals("2"))) {
			String aSQL = "select *" + " from loprtmanager"
					+ " where code in ('RE00', '06')" + " and otherno = '"
					+ "?otherno?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(aSQL);
			sqlbv3.put("otherno", mLCContSchema.getContNo());
			ExeSQL aExeSQL = new ExeSQL();
			SSRS aSSRS = aExeSQL.execSQL(sqlbv3);
			if (aSSRS.getMaxRow() >= 1) {
				this.mNoticeFlag = "1";
			}

		}
		mTransferData.setNameAndValue("NoticeFlag", mNoticeFlag);
		// tongmeng 2007-10-30 add
		// 下拒保、延期结论时系统自动发放通知书标志
		String UWNoticeFlag = "";
		if (mUWFlag != null && (mUWFlag.equals("1") || mUWFlag.equals("2"))) {
			UWNoticeFlag = "XBJB";
		}
		mTransferData.setNameAndValue("UWNoticeFlag", UWNoticeFlag);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
