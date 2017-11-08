/*
 * @(#)LCParseGuideInUI.java	2005-01-05
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.UWConfirmBL;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LCRReportDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LCRReportSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
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
public class UWConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(UWConfirmAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGCUWMasterSchema mLCGCUWMasterSchema = new LCGCUWMasterSchema();
	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mGrpContNo;
	private String mUWFlag;
	private LCContSchema mLCContSchema;
	private String mStopFlag = "0";
	private String mNewContNo = "0";
	private String mContNo;
	private String mPrtNo;
	private String mUWIdea;
	private String mUWCancelFlag;
	private boolean mBLUWCancelFlag;
	// 呈报到的核保等级
	private String mUWPopedom;
	// 呈报流向
	private String mUWUpReport;
	private String mSugIndUWFlag;
	private String mSugIndUWIdea;

	// 是否发出拒保延期通知书
	private String mNoticeFlag = "0";

	/** 工作流扭转标志 */
	private String mUWSendFlagA = "0";// 发送投保人打印核保通知书标志
	private String mUWSendFlagI1 = "0";// 发送第一被保人打印核保通知书标志
	private String mUWSendFlagI2 = "0";// 发送第二被保人打印核保通知书标志
	private String mUWSendFlagI3 = "0";// 发送第三被保人打印核保通知书标志
	
	private String mUWSendFlag_UnPrintA = "0";// 发送投保人不打印核保通知书标志
	private String mUWSendFlag_UnPrintI1 = "0";// 发送第一被保人不打印核保通知书标志
	private String mUWSendFlag_UnPrintI2 = "0";// 发送第二被保人不打印核保通知书标志
	private String mUWSendFlag_UnPrintI3 = "0";// 发送第三被保人不打印核保通知书标志
	
	private String mSendOperFlag = "0";// 发送业务员通知书标志
	private String mQuesOrgFlag = "0";// 发送机构问题件标志
	private String mApproveModifyFlag = "0";// 发送问题件修改岗标志
	private String mSendReportNoticeFlag = "0";//发送生调通知书标志
	private String mSendPENoticeFlag = "0";//是否存在体检录入的标志，0为不存在不需要发放 否则为1
	
	public UWConfirmAfterInitService() {
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
	public boolean submitData(VData cInputData, String cOperate) {

		mInputData = (VData) cInputData.clone();
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 返回复核的标志,不返回复核才返回复核节点
		String tReturnCheck = StrTool.cTrim((String) mTransferData
				.getValueByName("ReturnCheck"));

		logger.debug("------------------------------------------------------");
		logger.debug("--人工核保服务类，返回复核的标志：["
				+ tReturnCheck
				+ "],执行边的属性值:["
				+ StrTool.cTrim((String) mTransferData
						.getValueByName("ReDisMark")) + "]");
		logger.debug("------------------------------------------------------");
		if (!tReturnCheck.equals("Y")) {
			// 进行业务处理
			if (!dealData()) {
				return false;
			}
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
			CError.buildErr(this,"前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得当前工作任务的保单号
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中ContNo失败!");
			return false;
		}

		// 获得当前工作任务的印刷号
		mPrtNo = (String) mTransferData.getValueByName("PrtNo");
		if (mPrtNo == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中PrtNo失败!");
			return false;
		}

		// 获得当前工作任务的核保标志
		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		if (mUWFlag == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中UWFlag失败!");
			return false;
		}

		// 获得当前工作任务的核保意见
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		if (mUWIdea == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中UWIdea失败!");
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
		LCContDB aLCContDB = new LCContDB();
		aLCContDB.setContNo(mContNo);
		aLCContDB.getInfo();
		mLCContSchema = aLCContDB.getSchema();
		// 建议核保结论
		mSugIndUWFlag = (String) mTransferData.getValueByName("SugIndUWFlag");
		// 建议核保意见
		mSugIndUWIdea = (String) mTransferData.getValueByName("SugIndUWIdea");
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
	private boolean dealData() {
		if("0".equals(mUWUpReport) && "5".equals(mUWFlag)){
			//如果有已下发的核保通知书，只做流转即可
			if(!transUWSend()){
				CError.buildErr(this,"准备核保通知书流转失败!");
				return false;
			}
		}else{
			UWConfirmBL tUWConfirmBL = new UWConfirmBL();

			boolean tResult = tUWConfirmBL.submitData(mInputData, "");
			if (tResult) {
				mMap = (MMap) tUWConfirmBL.getResult().getObjectByObjectName(
						"MMap", 0);
				mTransferData = (TransferData) tUWConfirmBL.getResult()
						.getObjectByObjectName("TransferData", 0);
			} else {
				this.mErrors.copyAllErrors(tUWConfirmBL.getErrors());
				return false;
			}
		}
		return true;
	}

	private boolean transUWSend(){
		String tsql = "";
		ExeSQL tExeSQL = new ExeSQL();
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		LCRReportDB tLCRReportDB = new LCRReportDB();
		LCRReportSet tLCRReportSet = new LCRReportSet();
		LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
		LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
		
		// 承保计划变更
		tsql = "select count(1) from LCUWMaster where contno='"
					+ "?contno?" + "' and ChangePolFlag='1' "
					+ " and exists(select 1 from lcpol a where a.polno=LCUWMaster.polno )";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("contno", mContNo);
		int rs_change = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
		if (rs_change > 0) {
			mTransferData.setNameAndValue("ChangePolFlag", "1");
//			mChangePolFlag = "1";
		}	

		// 加费
				
		tsql = "select count(1) from LCUWMaster where contno='"
			+ "?contno?" + "' and AddPremFlag='1' "
			+ " and exists(select 1 from lcpol a where a.polno=LCUWMaster.polno )";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tsql);
		sqlbv1.put("contno", mContNo);
		int rs_addfee = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
		if (rs_addfee > 0) {
			mTransferData.setNameAndValue("AddFeeFlag", "1");
		}	
		// 特约
		tsql = "select count(1) from lccspec where proposalcontno="
			+ "(select proposalcontno from lccont where contno='" + "?contno?" + "') and  needprint='Y'"
			+ " and (customerno is null or (customerno is not null and prtflag is null))";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tsql);
		sqlbv2.put("contno", mContNo);
		int rs_spec = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
		if (rs_spec > 0) {	
			mTransferData.setNameAndValue("SpecFlag", "1");
//			mSpecFlag = "1";
		}
		
		// 查询待发体检 add ln 2008-10-29
		tsql = "select * from LCPENotice where 1=1 and Contno = '"
				+ "?contno?" + "' and printflag='0'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tsql);
		sqlbv3.put("contno", mContNo);
		logger.debug("tSql==" + tsql);
		tLCPENoticeSet = tLCPENoticeDB.executeQuery(sqlbv3);
		if (tLCPENoticeSet.size() > 0 ) {//体检暂时不区分投被保人
//			for(int i=1;i<=tLCPENoticeSet.size();i++){
//				if("I".equals(tLCPENoticeSet.get(i).getCustomerType())){//被保人体检
//					
//				}
//				if("A".equals(tLCPENoticeSet.get(i).getCustomerType())){//投保人体检
//					
//				}
//			}
			mTransferData.setNameAndValue("PrtSeqPE", tLCPENoticeSet.get(1).getPrtSeq());
			mTransferData.setNameAndValue("OldPrtSeqPE", tLCPENoticeSet.get(1).getPrtSeq());
			
			mSendPENoticeFlag = "1";
					
		}
		//生调
		tsql="select * from LCRReport where contno='"+ "?contno?" + "' and replyflag='0'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tsql);
		sqlbv4.put("contno", mContNo);
		tLCRReportSet = tLCRReportDB.executeQuery(sqlbv4);
		if(tLCRReportSet.size()>0) {	
			mTransferData.setNameAndValue("PrtSeq", tLCRReportSet.get(1).getPrtSeq());
			mTransferData.setNameAndValue("OldPrtSeq", tLCRReportSet.get(1).getPrtSeq());
			mSendReportNoticeFlag = "1";
		}	
		//业务员
		tsql="select * from lcissuepol where 1=1" + " and Contno = '"
				+ "?contno?" + "' and backobjtype = '3' and state='0'"
				+ " and needprint = 'Y' ";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tsql);
		sqlbv5.put("contno", mContNo);
		tLCRReportSet = tLCRReportDB.executeQuery(sqlbv5);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv5);
		if (tLCIssuePolSet.size() > 0) {
			mTransferData.setNameAndValue("PrtSeqOper", tLCIssuePolSet.get(1).getPrtSeq());
			mSendOperFlag = "1";
		}	
		//机构问题件
		tsql="select * from lcissuepol where 1=1" + " and Contno = '"
				+ "?contno?" + "' and backobjtype = '4' and replyresult is null"
				+ " and needprint = 'Y' and state='0' ";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tsql);
		sqlbv6.put("contno", mContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv6);
		if (tLCIssuePolSet.size() > 0) {
			mTransferData.setNameAndValue("PrtSeqCom", tLCIssuePolSet.get(1).getPrtSeq());
			mQuesOrgFlag = "1";
		}
		//保户打印的核保通知书		
		tsql="select * from lcissuepol where 1=1"
				+ " and Contno = '"
				+ "?contno?"
				+ "' and backobjtype = '2' and (replyresult is null or  replyresult = '' ) and needprint = 'Y'"
				+ " and state='0' and standbyflag2 = 'Y' ";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tsql);
		sqlbv7.put("contno", mContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv7);
		if (tLCIssuePolSet.size() > 0) {
			for(int i=1;i<=tLCIssuePolSet.size();i++){
				if("0".equals(tLCIssuePolSet.get(i).getQuestionObjType())){//投保人
					mTransferData.setNameAndValue("PrtSeqUWA", tLCIssuePolSet.get(i).getPrtSeq());
					mTransferData.setNameAndValue("OldPrtSeqA", tLCIssuePolSet.get(i).getPrtSeq());
					mTransferData.setNameAndValue("CustomerNoA", tLCIssuePolSet.get(i).getQuestionObj());
					mTransferData.setNameAndValue("CustomerNoTypeA", "A");
					this.mUWSendFlagA="1";
				}else {//被保人
					tsql="select SequenceNo from lcinsured where contno='"+"?contno?"+"' and insuredno='"+"?insuredno?"+"'";
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					sqlbv8.sql(tsql);
					sqlbv8.put("contno", mContNo);
					sqlbv8.put("insuredno", tLCIssuePolSet.get(i).getQuestionObj());
					String customerseqno=tExeSQL.getOneValue(sqlbv8);
					if("1".equals(customerseqno)){//第一被保人
						mTransferData.setNameAndValue("PrtSeqUWI1", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("OldPrtSeqI1", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("CustomerNoI1", tLCIssuePolSet.get(i).getQuestionObj());
						mTransferData.setNameAndValue("CustomerNoTypeI1", "I");
						this.mUWSendFlagI1="1";
					}
					if("2".equals(customerseqno)){//第二被保人
						mTransferData.setNameAndValue("PrtSeqUWI2", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("OldPrtSeqI2", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("CustomerNoI2", tLCIssuePolSet.get(i).getQuestionObj());
						mTransferData.setNameAndValue("CustomerNoTypeI2", "I");
						this.mUWSendFlagI2="1";
					}
					if("3".equals(customerseqno)){//第三被保人
						mTransferData.setNameAndValue("PrtSeqUWI3", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("OldPrtSeqI3", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("CustomerNoI3", tLCIssuePolSet.get(i).getQuestionObj());
						mTransferData.setNameAndValue("CustomerNoTypeI3", "I");
						this.mUWSendFlagI3="1";
					}
				}
			}
			
		}
		//保户非打印核保通知书
		tsql= "select * from lcissuepol where 1=1"
				+ " and Contno = '"
				+ "?contno?"
				+ "' and backobjtype = '2' and (replyresult is null or  replyresult = '' ) and needprint = 'Y'"
				+ " and state='0' " + " and standbyflag2 = 'N' ";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tsql);
		sqlbv9.put("contno", mContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv9);
		if (tLCIssuePolSet.size() > 0) {
			for(int i=1;i<=tLCIssuePolSet.size();i++){
				if("0".equals(tLCIssuePolSet.get(i).getQuestionObjType())){//投保人
					mTransferData.setNameAndValue("PrtSeqUW_UnPrintA", tLCIssuePolSet.get(i).getPrtSeq());
					mTransferData.setNameAndValue("OldPrtSeq_UnPrintA", tLCIssuePolSet.get(i).getPrtSeq());
					mTransferData.setNameAndValue("CustomerNo_UnPrintA", tLCIssuePolSet.get(i).getQuestionObj());
					mTransferData.setNameAndValue("CustomerNoType_UnPrintA", "A");
					this.mUWSendFlag_UnPrintA="1";
				}else  {//被保人
					tsql="select SequenceNo from lcinsured where contno='"+"?contno?"+"' and insuredno='"+"?insuredno?"+"'";
					SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
					sqlbv10.sql(tsql);
					sqlbv10.put("contno", mContNo);
					sqlbv10.put("insuredno", tLCIssuePolSet.get(i).getQuestionObj());
					String customerseqno=tExeSQL.getOneValue(sqlbv10);
					if("1".equals(customerseqno)){//第一被保人
						mTransferData.setNameAndValue("PrtSeqUW_UnPrintI1", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("OldPrtSeq_UnPrintI1", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("CustomerNo_UnPrintI1", tLCIssuePolSet.get(i).getQuestionObj());
						mTransferData.setNameAndValue("CustomerNoType_UnPrintI1", "I");
						this.mUWSendFlag_UnPrintI1="1";
					}
					if("2".equals(customerseqno)){//第二被保人
						mTransferData.setNameAndValue("PrtSeqUW_UnPrintI2", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("OldPrtSeq_UnPrintI2", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("CustomerNo_UnPrintI2", tLCIssuePolSet.get(i).getQuestionObj());
						mTransferData.setNameAndValue("CustomerNoType_UnPrintI2", "I");
						this.mUWSendFlag_UnPrintI2="1";
					}
					if("3".equals(customerseqno)){//第三被保人
						mTransferData.setNameAndValue("PrtSeqUW_UnPrintI3", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("OldPrtSeq_UnPrintI3", tLCIssuePolSet.get(i).getPrtSeq());
						mTransferData.setNameAndValue("CustomerNo_UnPrintI3", tLCIssuePolSet.get(i).getQuestionObj());
						mTransferData.setNameAndValue("CustomerNoType_UnPrintI3", "I");
						this.mUWSendFlag_UnPrintI3="1";
					}
				}
			}
			
		}
		//问题件修改岗
		tsql = "select * from lcissuepol where 1=1"
				+ " and Contno = '"
				+ "?contno?"
				+ "' and backobjtype = '1' and state='0' and (replyresult is null or  replyresult = '' ) "
				+ " and needprint = 'Y' ";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(tsql);
		sqlbv11.put("contno", mContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv11);
		if (tLCIssuePolSet.size() > 0) {
			this.mApproveModifyFlag="1";
			mTransferData.setNameAndValue("State", "1");
			mTransferData.setNameAndValue("ReplyDate", PubFun.getCurrentDate());
			mTransferData.setNameAndValue("ReplyTime", PubFun.getCurrentTime());	
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
			CError.buildErr(this,"代理人表LAAgent查询失败!");
			return false;
		}

		if (tLAAgentSet.get(1).getAgentGroup() == null) {
			// @@错误处理
			CError.buildErr(this,"代理人表LAAgent中的代理机构数据丢失!");
			return false;
		}
		logger.debug("aaaa");

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
		tLABranchGroupDB.setAgentGroup(tLAAgentSet.get(1).getAgentGroup());
		tLABranchGroupSet = tLABranchGroupDB.query();
		if (tLABranchGroupSet == null || tLABranchGroupSet.size() != 1) {
			// @@错误处理
			CError.buildErr(this,"代理人展业机构表LABranchGroup查询失败!");
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
		mTransferData.setNameAndValue("BranchAttr", tLABranchGroupSet.get(1)
				.getBranchAttr());
		mTransferData
		.setNameAndValue("SaleChnl", mLCContSchema.getSaleChnl());
		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
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
				//&& mTransferData.getValueByName("UWUpReport").equals("0")//上报和返回下级的投保单不改变核保级别2009-2-4
				) {
			//tongmeng 2009-03-10 modify
			//修改取核保级别SQL
			//modify by lzf 2013-04-07
//			String tSql = " select missionprop12 from lwmission where 1 =1 "
//					+ " and activityid = '0000001100' and missionid = '"
//					+ mMissionID + "'" //+ " and submissionid = '"
//					//+ mSubMissionID + "'"
//					+ " order by submissionid desc "
//					;
//			
//			logger.debug("tSql:" + tSql);
//			ExeSQL tExeSQL = new ExeSQL();
//			String tUWAuthority = tExeSQL.getOneValue(tSql);
//			if (tUWAuthority == null || tUWAuthority.equals("")) {
//				CError.buildErr(this,"核保级别查询失败!");
//				return false;
//
//			}
//			mTransferData.setNameAndValue("UWAuthority", tUWAuthority);
			mTransferData.setNameAndValue("UWAuthority", mTransferData.getValueByName("UWAuthority"));
		}
		//end by lzf
		mTransferData.setNameAndValue("ApproveDate", mLCContSchema
				.getApproveDate());

		// 根据核保结论判断是否发出过拒保延期通知书
		// 如果核保结论为拒保，则判断是否已经发送拒保通知书，如果发送过拒保通知书则不生成核保订正节点
		if (mUWFlag != null && (mUWFlag.equals("1") || mUWFlag.equals("2"))) {
			String aSQL = "select *" + " from loprtmanager"
					+ " where code in ('00', '06')" + " and otherno = '"
					+ "?otherno?" + "'";
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(aSQL);
			sqlbv12.put("otherno", mLCContSchema.getPrtNo());
			ExeSQL aExeSQL = new ExeSQL();
			SSRS aSSRS = aExeSQL.execSQL(sqlbv12);
			if (aSSRS.getMaxRow() >= 1) {
				this.mNoticeFlag = "1";
			}

		}
		mTransferData.setNameAndValue("NoticeFlag", mNoticeFlag);
		// tongmeng 2007-10-30 add
		// 下拒保、延期结论时系统自动发放通知书标志
		String UWNoticeFlag = "";
		if (mUWFlag != null && (mUWFlag.equals("1") || mUWFlag.equals("2"))) {
			UWNoticeFlag = "TBJB";
		}
		mTransferData.setNameAndValue("UWNoticeFlag", UWNoticeFlag);
		//增加下发核保通知书流转条件
		mTransferData.setNameAndValue("UWSendFlagA", mUWSendFlagA);
		mTransferData.setNameAndValue("UWSendFlagI1", mUWSendFlagI1);
		mTransferData.setNameAndValue("UWSendFlagI2", mUWSendFlagI2);
		mTransferData.setNameAndValue("UWSendFlagI3", mUWSendFlagI3);
		mTransferData.setNameAndValue("UWSendFlag_UnPrintA", mUWSendFlag_UnPrintA);
		mTransferData.setNameAndValue("UWSendFlag_UnPrintI1", mUWSendFlag_UnPrintI1);
		mTransferData.setNameAndValue("UWSendFlag_UnPrintI2", mUWSendFlag_UnPrintI2);
		mTransferData.setNameAndValue("UWSendFlag_UnPrintI3", mUWSendFlag_UnPrintI3);
		mTransferData.setNameAndValue("SendOperFlag", mSendOperFlag);
		mTransferData.setNameAndValue("QuesOrgFlag", mQuesOrgFlag);
		mTransferData.setNameAndValue("ApproveModifyFlag", mApproveModifyFlag);
		mTransferData.setNameAndValue("SendReportNoticeFlag", mSendReportNoticeFlag);
		mTransferData.setNameAndValue("SendPENoticeFlag", mSendPENoticeFlag);
		

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
