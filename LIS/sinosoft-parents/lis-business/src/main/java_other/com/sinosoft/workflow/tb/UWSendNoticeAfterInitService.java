package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.cbcheck.MSManagerBL;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIndUWMasterDB;
import com.sinosoft.lis.db.LCIndUWSubDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCRReportDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIndUWMasterSchema;
import com.sinosoft.lis.schema.LCIndUWSubSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCIndUWMasterSet;
import com.sinosoft.lis.vschema.LCIndUWSubSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCRReportSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
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
 * Title: 工作流节点任务:新契约发核保通知书
 * </p>
 * <p>
 * Description: 发核保通知书工作流AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class UWSendNoticeAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(UWSendNoticeAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 核保主表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();
	private LCIndUWMasterSchema mLCIndUWMasterSchema = new LCIndUWMasterSchema();
	private LCIndUWMasterSet mLCIndUWMasterSet = new LCIndUWMasterSet();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();

	/** 核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCIndUWSubSet mLCIndUWSubSet = new LCIndUWSubSet();
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();

	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCIssuePolSet mLCIssuePolSet = new LCIssuePolSet();
	private LCCSpecSet mLCCSpecSet = new LCCSpecSet();// 特约通知书
	private LCRReportSet mLCRReportSet_org = new LCRReportSet();// 生调通知书
	private LCRReportSet mLCRReportSet = new LCRReportSet();// 生调通知书
	private LCPENoticeSet mLCPENoticeSet = new LCPENoticeSet();// 体检通知书

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mSubMissionID;
	private String mPrtSeq;
	private String mPrtSeqUW = "0";
	private String mPrtSeqUW_UnPrint = "0";
	private String mPrtSeqOper = "0";	
	private String mPrtSeqCom = "";
	private String mCustomerNo = "";// 方法对象
	private String mCustomerNoType = "";// 发放对象类型(A 投保人,业务员,机构问题件,操作员问题件),I(被保人)
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	private String mUWFlag = ""; // 核保标志
	private String mAddFeeFlag = "0";//是否存在加费
	private String mSpecFlag = "0";//是否存在特约
	private String mChangePolFlag = "0";//是否存在承保计划变更

	/** 工作流扭转标志 */
	private String mUWSendFlag = "";// 发送打印核保通知书标志
	private String mUWSendFlag_UnPrint = "";// 发送不打印核保通知书标志
	private String mSendOperFlag = "";// 发送业务员通知书标志
	private String mQuesOrgFlag = "";// 发送机构问题件标志
	private String mApproveModifyFlag = "";// 发送问题件修改岗标志
	private String mSendReportNoticeFlag = "0";//发送生调通知书标志
	private String mSendPENoticeFlag = "0";//是否存在体检录入的标志，0为不存在不需要发放 否则为1

	private LCIssuePolSet mLCIssuePolSet_UWSend = new LCIssuePolSet();// 核保通知书问题件集合
	private LCIssuePolSet mLCIssuePolSet_UWSend_UnPrint = new LCIssuePolSet();// 不打印核保通知书问题件集合
	private LCIssuePolSet mLCIssuePolSet_SendOper = new LCIssuePolSet();// 业务员通知书问题件集合

	private LCIssuePolSet mLCIssuePolSet_QuesOrg = new LCIssuePolSet();// 机构问题件集合
	private LCIssuePolSet mLCIssuePolSet_ApproveModify = new LCIssuePolSet();// 问题件修改岗问题件集合
	private MMap agentMSmap   = new MMap(); //发送业务员短信集合
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public UWSendNoticeAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 对是否能发核保通知书进行校验
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		if (!prepareTransferData())
			return false;
		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		int flag = 0;
		mAddFeeFlag = "0";//是否存在加费
		mSpecFlag = "0";//是否存在特约
		mChangePolFlag = "0";//是否存在特约
		
		// 1 没有未回复的问题件则不能发核保通知书
		String strSql = "select count(1) from lcissuepol where 1=1"
				+ " and Contno = '"
				+ "?n1?"
				+ "' "
				+ " and state is null and needprint = 'Y'"
				// 判断有没有发放给业务员问题件
				+ " and ((backobjtype = '3' and replyresult is null and prtseq is null "
				+ " and '"
				+ "?n2?"
				+ "'='A' )"
				// 判断有没有发放给保户的问题件
				+ " or (backobjtype = '2' and replyresult is null and prtseq is null "
				+ " and ((questionobj='"
				+ "?n3?"
				+ "' and questionobjtype<>'0' and '"
				+ "?n4?"
				+ "'='I') "
				+ " or (questionobj='"
				+ "?n5?"
				+ "' and questionobjtype='0' and '"
				+ "?n6?"
				+ "'='A'))) "
				// 判断有没有发放给问题件修改岗的问题件
				+ " or (backobjtype = '1' and replyresult is null and '"
				+ "?n7?"
				+ "'='A') "
				// 判断有没有发放给机构的问题件
				+ " or (backobjtype = '4' and replyresult is null and '"
				+ "?n8?" + "'='A')) ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(strSql);
		sqlbv1.put("n1", mContNo);
		sqlbv1.put("n2", this.mCustomerNoType);
		sqlbv1.put("n3", this.mCustomerNo);
		sqlbv1.put("n4", this.mCustomerNoType);
		sqlbv1.put("n5", this.mCustomerNo);
		sqlbv1.put("n6", this.mCustomerNoType);
		sqlbv1.put("n7", this.mCustomerNoType);
		sqlbv1.put("n8", this.mCustomerNoType);

		logger.debug("判断是否有需要下发的问题件 strSql:" + strSql);
		ExeSQL tExeSQL = new ExeSQL();
		int rs = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
		if (rs > 0) {
			flag = 1;
		}

//		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
//		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
//		tLCCUWMasterDB.setContNo(mContNo);
//		if (!tLCCUWMasterDB.getInfo()) {
//			// @@错误处理
//			CError.buildErr(this, "查询核保主表失败!");
//			return false;
//
//		}
//		tLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
		// 没有承保计划变更，不能发核保通知书
//		if (tLCCUWMasterSchema.getChangePolFlag() != null
//				&& tLCCUWMasterSchema.getChangePolFlag().length() > 0
//				&& tLCCUWMasterSchema.getChangePolFlag().equals("1")) {
//			flag = 1;
//		}

		// 没有特约，不能发核保通知书
//		LCIndUWMasterDB tLCIndUWMasterDB = new LCIndUWMasterDB();
//		tLCIndUWMasterDB.setContNo(mContNo);
//		tLCIndUWMasterDB.setInsuredNo(this.mCustomerNo);
//		LCIndUWMasterSet tLCIndUWMasterSet = tLCIndUWMasterDB.query();
//		if (tLCIndUWMasterSet == null || tLCIndUWMasterSet.size() <= 0) {
//			// @@错误处理
//			CError.buildErr(this, "查询被保人核保主表失败!");
//			return false;
//		}
//		for (int i = 1; i <= tLCIndUWMasterSet.size(); i++) {
//			if (tLCIndUWMasterSet.get(i).getSpecFlag() != null
//					&& tLCIndUWMasterSet.get(i).getSpecFlag().length() > 0
//					&& tLCIndUWMasterSet.get(i).getSpecFlag().equals("1")) {
//				flag = 1;
//			}
//		}
//		if (!tLCIndUWMasterDB.getInfo()) {
//			// @@错误处理
//			CError.buildErr(this, "查询被保人核保主表失败!");
//			return false;
//		}
//		LCIndUWMasterSchema tLCIndUWMasterSchema = tLCIndUWMasterDB.getSchema();
//		if (tLCIndUWMasterSchema.getSpecFlag() != null
//				&& tLCIndUWMasterSchema.getSpecFlag().length() > 0
//				&& tLCIndUWMasterSchema.getSpecFlag().equals("1")) {
//			flag = 1;
//		}

		// 没有加费，不能发核保通知书
//		if (tLCCUWMasterSchema.getAddPremFlag() != null
//				&& tLCCUWMasterSchema.getAddPremFlag().length() > 0
//				&& tLCCUWMasterSchema.getAddPremFlag().equals("1")) {
//			flag = 1;
//		}
		
		// 没有承保计划变更，不能发核保通知书
//		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
//		tLCUWMasterDB.setContNo(mContNo);
//		LCUWMasterSet tLCUWMasterSet = tLCUWMasterDB.query();
//		if (tLCUWMasterSet == null || tLCUWMasterSet.size() <= 0) {
//			// @@错误处理
//			CError.buildErr(this, "查询险种核保主表失败!");
//			return false;
//		}
//		for (int i = 1; i <= tLCUWMasterSet.size(); i++) {
//			if (tLCUWMasterSet.get(i).getChangePolFlag() != null
//					&& tLCUWMasterSet.get(i).getChangePolFlag().length() > 0
//					&& tLCUWMasterSet.get(i).getChangePolFlag().equals("1")) {
//				
//				flag = 1;
//				mChangePolFlag = "1";
//				break;
//			}
//		}
		// 没有承保计划变更，不能发核保通知书
		strSql = "select count(1) from LCUWMaster where contno='"
			+ "?n11?" + "' and ChangePolFlag='1' "
			+ " and exists(select 1 from lcpol a where a.polno=LCUWMaster.polno "
		    + " and insuredno='"+"?n13?"+"' and '"+"?n12?"+"' ='I')";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(strSql);
		sqlbv2.put("n11", mContNo);
		sqlbv2.put("n13", this.mCustomerNo);
		sqlbv2.put("n12", this.mCustomerNoType);
		tExeSQL = new ExeSQL();
		int rs_change = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
		if (rs_change > 0) {		    
			flag = 1;
			mChangePolFlag = "1";
		}	

		// 没有加费，不能发核保通知书
		/*strSql = "select count(1) from lcprem where contno='"
			+ mContNo + "' and payplancode like '000000%%' "
			+ " and exists(select 1 from lcpol a where a.polno=lcprem.polno "
		    + " and insuredno='"+this.mCustomerNo+"' and '"+this.mCustomerNoType+"' ='I')";*/
		strSql = "select count(1) from LCUWMaster where contno='"
			+ "?q1?" + "' and AddPremFlag='1' "
			+ " and exists(select 1 from lcpol a where a.polno=LCUWMaster.polno "
		    + " and insuredno='"+"?q2?"+"' and '"+"?q3?"+"' ='I')";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(strSql);
		sqlbv3.put("q1", mContNo);
		sqlbv3.put("q2", this.mCustomerNo);
		sqlbv3.put("q3", this.mCustomerNoType);
		tExeSQL = new ExeSQL();
		int rs_addfee = Integer.parseInt(tExeSQL.getOneValue(sqlbv3));
		if (rs_addfee > 0) {		    
			flag = 1;
			mAddFeeFlag = "1";
		}	
		// 没有特约，不能发核保通知书
		strSql = "select count(1) from lccspec where proposalcontno="
			+ "(select proposalcontno from lccont where contno='" + "?w1?" + "') and  needprint='Y'"
			+ " and (customerno is null or (customerno='"+"?w2?"+"' and '"+"?w3?"+"' ='I' and prtflag is null))";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(strSql);
		sqlbv4.put("w1", mContNo);
		sqlbv4.put("w2", this.mCustomerNo);
		sqlbv4.put("w3", this.mCustomerNoType);
		tExeSQL = new ExeSQL();
		int rs_spec = Integer.parseInt(tExeSQL.getOneValue(sqlbv4));
		if (rs_spec > 0) {		    
			flag = 1;
			mSpecFlag = "1";
		}
		// tongmeng 2008-08-12 add
		// 增加对体检和生调的发放校验
		//add 2008-10-06 生调的发放校验
		String tSQL_exist = "select count(1) from LCRReport where contno='"
			+ "?r1?" + "' and replyflag is null";	
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(tSQL_exist);
		sqlbv5.put("r1", mContNo);
		logger.debug("判断是否有需要放送的生调通知书 tSQL_exist:" + tSQL_exist);
		tExeSQL = new ExeSQL();
		int rs_exist = Integer.parseInt(tExeSQL.getOneValue(sqlbv5));
		if (rs_exist > 0) {		    
			flag = 1;
		}	
		
		//体检的发放校验 add 2008-10-29
		strSql = "select count(1) from LCPENotice where contno='"
			+ "?r1?" + "' and printflag is null and customerno='"+"?r2?"+"' and customertype='"+"?r3?"+"'";			
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(tSQL_exist);
		sqlbv6.put("r1", mContNo);
		sqlbv6.put("r2", this.mCustomerNo);
		sqlbv6.put("r3", this.mCustomerNoType);
		logger.debug("判断是否有需要放送的体检通知书 strSql:" + strSql);
		tExeSQL = new ExeSQL();
		int rs_health = Integer.parseInt(tExeSQL.getOneValue(sqlbv6));
		if (rs_health > 0) {		    
			flag = 1;
		}
		
		//
		if (flag == 0) {
			// @@错误处理
			CError.buildErr(this, "没有录入问题件、没有承保计划变更、没有特约、没有加费，不能发核保通知书!");
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
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			CError.buildErr(this, "前台传输业务数据失败!");
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			CError.buildErr(this, "前台传输业务数据中ContNo失败!");
			return false;
		}

		// 获得业务核保通知数据

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}
		
		// 获得当前工作任务的任务ID
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!");
			return false;
		}
		// tongmeng 2008-08-12 add
		// 先查询核保通知书发放对象
		String tSQL = "select missionprop15,missionprop16 from lwmission where missionid='"
				+ "?t1?" + "' " + " and activityid='" + "?t2?" + "' and submissionid='"+ "?t3?" +"'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(tSQL);
		sqlbv7.put("t1", this.mMissionID);
		sqlbv7.put("t2", cOperate);
		sqlbv7.put("t3", this.mSubMissionID);
		SSRS tSSRS = new SSRS();
		tSSRS = (new ExeSQL()).execSQL(sqlbv7);
		if (tSSRS.getMaxRow() <= 0) {
			CError.buildErr(this, "查找发放对象出错!");
			return false;
		}
		this.mCustomerNo = tSSRS.GetText(1, 1);
		this.mCustomerNoType = tSSRS.GetText(1, 2);

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 准备险种信息
		if (prepareCont() == false)
			return false;
		// 准备合同核保表信息
		if (prepareContUW() == false)
			return false;
		// 准备被保人核保表信息
		if (this.mCustomerNoType=="I" && prepareIndUW() == false)
			return false;
		// 准备险种核保表信息
		//if (preparePolUW() == false)
		//	return false;
		// 选择工作流流转活动
		if (chooseActivity() == false)
			return false;
		// if (mUWSendFlag.equals("1") || mSendOperFlag.equals("1")||this.)
		{
			// 准备打印管理表信息
			if (preparePrt() == false)
				return false;
			// tongmeng 2007-11-13 modify
			// 下面处理问题件逻辑有问题,修改在preparePrt进行处理
			// if (prepareIssue() == false)
			// return false;
		}
		return true;
	}

	/**
	 * prepareIssue 准备lcissuepol表数据，生成prtseq
	 * 
	 * @return boolean
	 */
	private boolean prepareIssue() {
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
		tLCIssuePolDB.setContNo(mContNo);
		tLCIssuePolSet = tLCIssuePolDB.query();

		for (int i = 1; i <= tLCIssuePolSet.size(); i++) {
			tLCIssuePolSchema = new LCIssuePolSchema();
			tLCIssuePolSchema = tLCIssuePolSet.get(i);
			if (tLCIssuePolSchema.getPrtSeq() == ""
					|| tLCIssuePolSchema.getPrtSeq() == null) {
				if (tLCIssuePolSchema.getBackObjType().equals("2"))
					tLCIssuePolSchema.setPrtSeq(mPrtSeqOper);
				else
					tLCIssuePolSchema.setPrtSeq(mPrtSeqUW);
			}
			mLCIssuePolSet.add(tLCIssuePolSchema);
		}

		return true;
	}

	/**
	 * preparePrt 准备合同表数据,并置UWFlag=8,表示生成核保通知书
	 * 
	 * @return boolean
	 */
	private boolean prepareCont() {
		// 准备合同表数据
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "合同" + mContNo + "信息查询失败!");
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		mUWFlag = "8"; // 核保订正标志
		// 准备保单的复核标志
		mLCContSchema.setUWFlag(mUWFlag);
		// 准备险种合同表数据
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLCContSchema.getContNo());
		mLCPolSet = tLCPolDB.query();

		// 准备险种保单的复核标志
		for (int i = 1; i < mLCPolSet.size(); i++) {
			mLCPolSet.get(i).setUWFlag(mUWFlag);
		}
		return true;
	}

	/**
	 * preparePrt 准备核保主表数据
	 * 
	 * @return boolean
	 */
	private boolean prepareContUW() {
		mLCCUWSubSet.clear();

		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		if (!tLCCUWMasterDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "LCCUWMaster表取数失败!");
			return false;
		}
		mLCCUWMasterSchema.setSchema(tLCCUWMasterDB);
		mLCCUWMasterSchema.setPassFlag(mUWFlag);
		mLCCUWMasterSchema.setOperator(mOperater);
		mLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		// 每次进行核保相关操作时，向核保轨迹表插一条数据
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			CError.buildErr(this, "LCCUWSub表取数失败!");
			return false;
		}

		int m = tLCCUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLCCUWSubSchema = new LCCUWSubSchema();
			tLCCUWSubSchema.setUWNo(m+mLCCUWSubSet.size()); // 第几次核保
			tLCCUWSubSchema.setContNo(mContNo);
			tLCCUWSubSchema.setGrpContNo(mLCCUWMasterSchema.getGrpContNo());
			tLCCUWSubSchema.setProposalContNo(mLCCUWMasterSchema
					.getProposalContNo());
			tLCCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
			tLCCUWSubSchema.setPrintFlag("1");
			tLCCUWSubSchema.setAutoUWFlag(mLCCUWMasterSchema.getAutoUWFlag());
			tLCCUWSubSchema.setState(mLCCUWMasterSchema.getState());
			tLCCUWSubSchema.setOperator(mLCCUWMasterSchema.getOperator()); // 操作员
			tLCCUWSubSchema.setManageCom(mLCCUWMasterSchema.getManageCom());
			tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			CError.buildErr(this, "LCCUWSub表取数失败!");
			return false;
		}
		mLCCUWSubSet.add(tLCCUWSubSchema);
		return true;
	}
	
	/**
	 * preparePrt 准备被保人核保主表数据
	 * 
	 * @return boolean
	 */
	private boolean prepareIndUW() {
		mLCIndUWSubSet.clear();

		LCIndUWMasterDB tLCIndUWMasterDB = new LCIndUWMasterDB();
		tLCIndUWMasterDB.setContNo(mContNo);
		tLCIndUWMasterDB.setInsuredNo(mCustomerNo);
		if (!tLCIndUWMasterDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "LCIndUWMaster表取数失败!");
			return false;
		}
		mLCIndUWMasterSchema.setSchema(tLCIndUWMasterDB);
		mLCIndUWMasterSchema.setPassFlag(mUWFlag);
		mLCIndUWMasterSchema.setOperator(mOperater);
		mLCIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mLCIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		// 每次进行核保相关操作时，向核保轨迹表插一条数据
		LCIndUWSubSchema tLCIndUWSubSchema = new LCIndUWSubSchema();
		LCIndUWSubDB tLCIndUWSubDB = new LCIndUWSubDB();
		tLCIndUWSubDB.setContNo(mContNo);
		tLCIndUWSubDB.setInsuredNo(mCustomerNo);
		LCIndUWSubSet tLCIndUWSubSet = new LCIndUWSubSet();
		tLCIndUWSubSet = tLCIndUWSubDB.query();
		if (tLCIndUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			CError.buildErr(this, "LCIndUWSub表取数失败!");
			return false;
		}

		int m = tLCIndUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLCIndUWSubSchema = new LCIndUWSubSchema();
			tLCIndUWSubSchema.setUWNo(m+mLCIndUWSubSet.size()); // 第几次核保
			tLCIndUWSubSchema.setContNo(mContNo);
			tLCIndUWSubSchema.setInsuredNo(mCustomerNo);
			tLCIndUWSubSchema.setGrpContNo(mLCIndUWMasterSchema.getGrpContNo());
			tLCIndUWSubSchema.setProposalContNo(mLCIndUWMasterSchema
					.getProposalContNo());
			tLCIndUWSubSchema.setPassFlag(mUWFlag); // 核保意见
			tLCIndUWSubSchema.setPrintFlag("1");
			tLCIndUWSubSchema.setAutoUWFlag(mLCIndUWMasterSchema.getAutoUWFlag());
			tLCIndUWSubSchema.setState(mLCIndUWMasterSchema.getState());
			tLCIndUWSubSchema.setOperator(mLCIndUWMasterSchema.getOperator()); // 操作员
			tLCIndUWSubSchema.setManageCom(mLCIndUWMasterSchema.getManageCom());
			tLCIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			CError.buildErr(this, "LCIndUWSub表取数失败!");
			return false;
		}
		mLCIndUWSubSet.add(tLCIndUWSubSchema);
		return true;
	}

	/**
	 * 准备险种核保表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePolUW() {
		mLCUWMasterSet.clear();
		mLCUWSubSet.clear();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();

		for (int i = 1; i <= mLCPolSet.size(); i++) {
			tLCUWMasterSchema = new LCUWMasterSchema();
			tLCUWMasterDB = new LCUWMasterDB();
			tLCPolSchema = mLCPolSet.get(i);
			tLCUWMasterDB.setProposalNo(tLCPolSchema.getProposalNo());

			if (!tLCUWMasterDB.getInfo()) {
				// @@错误处理
				CError.buildErr(this, "LCUWMaster表取数失败!");
				return false;
			}
			tLCUWMasterSchema.setSchema(tLCUWMasterDB);

			// tLCUWMasterSchema.setUWNo(tLCUWMasterSchema.getUWNo()+1);
			// 核保主表中的UWNo表示该投保单经过几次人工核保
			// (等价于经过几次自动核保次数),而不是人工核保结论(包括核保通知书,上报等)下过几次.所以将其注释.sxy-2003-09-19
			tLCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

			mLCUWMasterSet.add(tLCUWMasterSchema);

			// 核保轨迹表
			tLCUWSubSchema = new LCUWSubSchema();
			tLCUWSubDB = new LCUWSubDB();
			tLCUWSubSet = new LCUWSubSet();
			///String tSql = "select uwno from lcuwsub where polno='"++"'";
			tLCUWSubDB.setPolNo(tLCPolSchema.getPolNo());
			tLCUWSubSet = tLCUWSubDB.query();
			if (tLCUWSubDB.mErrors.needDealError()) {
				// @@错误处理
				CError.buildErr(this, "LCUWSub表取数失败!");
				return false;
			}
			int m = tLCUWSubSet.size();
			logger.debug("subcount=" + m);
			if (m > 0) {
				m++; // 核保次数
				tLCUWSubSchema = new LCUWSubSchema();
				tLCUWSubSchema.setUWNo(m+mLCUWSubSet.size()); // 第几次核保
				tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
				tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
				tLCUWSubSchema.setContNo(tLCUWMasterSchema.getContNo());
				tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema
						.getProposalContNo());
				tLCUWSubSchema.setPolNo(tLCUWMasterSchema.getPolNo());
				tLCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
				tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
				tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
				tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
				tLCUWSubSchema.setPrintFlag("1");
				tLCUWSubSchema.setState(mUWFlag);
				tLCUWSubSchema.setOperator(mOperater); // 操作员
				tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
				tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
				tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
				tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			} else {
				// @@错误处理
				CError.buildErr(this, "LCUWSub表取数失败!");
				return false;
			}
			mLCUWSubSet.add(tLCUWSubSchema);
		}
		return true;
	}

	/**
	 * preparePrt 准备打印表
	 * 
	 * @return boolean
	 */
	private boolean chooseActivity() {
		String tsql = "";
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

		mUWSendFlag = "0"; // 打印核保通知书标志
		mUWSendFlag_UnPrint = "0"; 
		mSendOperFlag = "0"; // 打印业务员通知书标志
		mQuesOrgFlag = "0"; // 机构问题件标志
		mApproveModifyFlag = "0"; // 复核修改标志
		mSendPENoticeFlag = "0"; //体检通知书标志
		mSendReportNoticeFlag = "0";//生调通知书标志

		// tongmeng 2007-11-28 modify
		// 加费操作改为判断合同核保表
		// tongmeng 2008-08-13 modify
		// 支持对不同对象发送核保通知书
		//--投保人发生调、特约、承保计划变更
		if (this.mCustomerNoType.endsWith("A")) {
			logger.debug("****************************准备投保人的核保通知书**********************************");
			
//			for (int i = 1; i <= mLCUWMasterSet.size(); i++) {
//				/*
//				 * if (mLCUWMasterSet.get(i).getAddPremFlag() != null &&
//				 * mLCUWMasterSet.get(i).getAddPremFlag().equals("1"))
//				 * mUWSendFlag = "1";
//				 */
//				if (mLCUWMasterSet.get(i).getSpecFlag() != null
//						&& mLCUWMasterSet.get(i).getSpecFlag().equals("1"))
//					mUWSendFlag = "1";
//			}
//			if (mLCCUWMasterSchema.getChangePolFlag() != null
//					&& mLCCUWMasterSchema.getChangePolFlag().equals("1")) {
//				mUWSendFlag = "1";
//			}
			// ln 2008-10-08 add
			tsql = "select * from LCRReport where contno='"
				+ "?y1?" + "' and replyflag is null";	
			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
			sqlbv7.sql(tsql);
			sqlbv7.put("y1", mContNo);
			logger.debug("tSql==" + tsql);
			LCRReportDB tLCRReportDB = new LCRReportDB();
			LCRReportSet tLCRReportSet = new LCRReportSet();
			tLCRReportSet = tLCRReportDB.executeQuery(sqlbv7);
			if(tLCRReportSet.size()>0) {			    
				mSendReportNoticeFlag = "1";
				this.mLCRReportSet_org.add(tLCRReportSet);
			}	
			//end add ln 2008-10-08
			//add ln 2008-12-2
			// 查询发送给业务员通知书
			tsql = "select * from lcissuepol where 1=1" + " and Contno = '"
					+ "?u1?" + "' and backobjtype = '3' and prtseq is null"
					+ " and needprint = 'Y' ";
			SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
			sqlbv8.sql(tsql);
			sqlbv8.put("u1", mContNo);
			tLCIssuePolSet = new LCIssuePolSet();
			logger.debug("tSql==" + tsql);
			tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv8);
			if (tLCIssuePolSet.size() > 0) {
				mSendOperFlag = "1";
				this.mLCIssuePolSet_SendOper.add(tLCIssuePolSet);
			}
			// 查询发送给机构的问题件
			tsql = "select * from lcissuepol where 1=1" + " and Contno = '"
					+ "?i1?" + "' and backobjtype = '4' and replyresult is null"
					+ " and needprint = 'Y' ";
			SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
			sqlbv9.sql(tsql);
			sqlbv9.put("i1", mContNo);
			tLCIssuePolSet = new LCIssuePolSet();
			logger.debug("tSql==" + tsql);
			tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv9);
			if (tLCIssuePolSet.size() > 0) {
				mQuesOrgFlag = "1";
				this.mLCIssuePolSet_QuesOrg.add(tLCIssuePolSet);
			}
			
			// backobjtype = '2' 为发送给保户--投保人的核保通知书
			// 查询需要打印到保单的核保通知书
			tsql = "select * from lcissuepol where 1=1"
					+ " and Contno = '"
					+ "?o1?"
					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
					+ " and prtseq is null " + " and standbyflag2 = 'Y' "
					+ " and questionobj='"
					+ "?o2?"
					+ "' and questionobjtype='0'";
			SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
			sqlbv10.sql(tsql);
			sqlbv10.put("o1", mContNo);
			sqlbv10.put("o2", this.mCustomerNo);

			logger.debug("tSql==" + tsql);
			tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv10);
			if (tLCIssuePolSet.size() > 0) {
				mUWSendFlag = "1";
				this.mLCIssuePolSet_UWSend.add(tLCIssuePolSet);
			}
			
			// 查询不需要打印到保单的核保通知书
			tsql = "select * from lcissuepol where 1=1"
					+ " and Contno = '"
					+ "?a1?"
					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
					+ " and prtseq is null " + " and standbyflag2 = 'N' "
					+ " and questionobj='"
					+ "?a2?"
					+ "' and questionobjtype='0'";
			SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
			sqlbv11.sql(tsql);
			sqlbv11.put("a1", mContNo);
			sqlbv11.put("a2", this.mCustomerNo);
			logger.debug("tSql_unprint==" + tsql);
			tLCIssuePolSet = new LCIssuePolSet();
			tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv11);
			if (tLCIssuePolSet.size() > 0) {
				mUWSendFlag_UnPrint = "1";
				this.mLCIssuePolSet_UWSend_UnPrint.add(tLCIssuePolSet);
			}
			
			// 查询是否有不需要创建问题件修改岗节点的因素存在
			String ApproveModifyFlag = "0";
			tsql = "select 1 from lcissuepol where 1=1"
					+ " and Contno = '"
					+ "?s1?"
					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
					+ " and prtseq is null ";	
			SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
			sqlbv12.sql(tsql);
			sqlbv12.put("s1", mContNo);
			logger.debug("tSql judge ApproveModifyFlag==" + tsql);
			tLCIssuePolSet = new LCIssuePolSet();
			tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv12);
			if (tLCIssuePolSet.size() > 0) {
				ApproveModifyFlag = "1";
			}
			// 查询发送给问题件修改岗的问题件
			tsql = "select * from lcissuepol where 1=1"
					+ " and Contno = '"
					+ "?d1?"
					+ "' and backobjtype = '1' and state is null and replyresult is null "
					+ " and needprint = 'Y' ";
			SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
			sqlbv13.sql(tsql);
			sqlbv13.put("d1", mContNo);
			logger.debug("tSql==" + tsql);
			tLCIssuePolSet = new LCIssuePolSet();
			tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv13);
			//if (tLCIssuePolSet.size() > 0 && mSendOperFlag.equals("0")
			//		&& mUWSendFlag.equals("0") && mUWSendFlag_UnPrint.equals("0") && mQuesOrgFlag.equals("0")) {
			if (tLCIssuePolSet.size() > 0 && mSendOperFlag.equals("0")
							&& ApproveModifyFlag.equals("0") && mQuesOrgFlag.equals("0")) {
				mApproveModifyFlag = "1";
			}		
			logger.debug("*********endend***************准备投保人的核保通知书结束*****************endendend*****************");
			
		}
		else
		{
			//--被保人相关***************************************************************
			logger.debug("************************准备被保人的核保通知书**********************************");
			if (mAddFeeFlag.equals("1")
					|| mSpecFlag.equals("1")
					|| mChangePolFlag.equals("1")) {
				mUWSendFlag = "1";
			}
			
			// backobjtype = '2' 为发送给保户的核保通知书
			// 查询需要打印到保单的核保通知书
			tsql = "select * from lcissuepol where 1=1"
					+ " and Contno = '"
					+ "?d3?"
					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
					+ " and prtseq is null " + " and standbyflag2 = 'Y' "
					+ " and questionobj='"
					+ "?d4?"
					+ "' and questionobjtype<>'0'";
			SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
			sqlbv14.sql(tsql);
			sqlbv14.put("d3", mContNo);
			sqlbv14.put("d4", this.mCustomerNo);

			logger.debug("tSql==" + tsql);
			tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv14);
			if (tLCIssuePolSet.size() > 0) {
				mUWSendFlag = "1";
				this.mLCIssuePolSet_UWSend.add(tLCIssuePolSet);
			}
			// 查询不需要打印到保单的核保通知书
			tsql = "select * from lcissuepol where 1=1"
					+ " and Contno = '"
					+ "?d5?"
					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
					+ " and prtseq is null " + " and standbyflag2 = 'N' "
					+ " and questionobj='"
					+ "?d6?"
					+ "' and questionobjtype<>'0'";
			SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
			sqlbv15.sql(tsql);
			sqlbv15.put("d5", mContNo);
			sqlbv15.put("d6", this.mCustomerNo);
			logger.debug("tSql_unprint==" + tsql);
			tLCIssuePolSet = new LCIssuePolSet();
			tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv15);
			if (tLCIssuePolSet.size() > 0) {
				mUWSendFlag_UnPrint = "1";
				this.mLCIssuePolSet_UWSend_UnPrint.add(tLCIssuePolSet);
			}
			logger.debug("*********endend***************准备被保人的核保通知书结束*****************endendend*****************");
		}
				
		logger.debug("************************准备客户的核保通知书--体检**********************************");
		// 查询待发体检 add ln 2008-10-29
		tsql = "select * from LCPENotice where 1=1 and Contno = '"
				+ "?d8?" + "' and printflag is null and customerno='"+"?d8?"+"' and customertype='"+"?d9?"+"'";
		logger.debug("tSql==" + tsql);
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		sqlbv16.sql(tsql);
		sqlbv16.put("d7", mContNo);
		sqlbv16.put("d8", this.mCustomerNo);
		sqlbv16.put("d9", this.mCustomerNoType);
		LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
		LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
		tLCPENoticeSet = tLCPENoticeDB.executeQuery(sqlbv16);
		if (tLCPENoticeSet.size() > 0 ) {
			mSendPENoticeFlag = "1";
			mLCPENoticeSet.add(tLCPENoticeSet);
		}
		logger.debug("*********endend***************准备客户的核保通知书--体检结束*****************endendend*****************");
		
		//发送业务员短信
		//对销售渠道为02-个人代理、10-收展业务的数据按规则提取数据导入短信平台，其他渠道不做提取 hanbin 2010-04-29
		if("02".equals(mLCContSchema.getSaleChnl()) || "10".equals(mLCContSchema.getSaleChnl())){
			if("1".equals(mSendPENoticeFlag)||"1".equals(mSendReportNoticeFlag) ||"1".equals(mUWSendFlag) ||"1".equals(mUWSendFlag_UnPrint)){
				
				MSManagerBL tMSManagerBL = new MSManagerBL();
				VData tVData = new VData();
				TransferData tTransferData = new TransferData();
				if("1".equals(mSendPENoticeFlag)){
					tTransferData.setNameAndValue("MSType", "03");				
				}else if("1".equals(mSendReportNoticeFlag)){
					tTransferData.setNameAndValue("MSType", "04");			
				}else{
					tTransferData.setNameAndValue("MSType", "02");	
				}
				tVData.add(mLCContSchema);
				tVData.add(mGlobalInput);
				tVData.add(tTransferData);
				if(!tMSManagerBL.submitData(tVData, "")){
					mErrors.copyAllErrors(tMSManagerBL.getErrors());
					return false;
				}else{
					agentMSmap = (MMap) tMSManagerBL.getResult().getObjectByObjectName("MMap", 0);
					logger.debug("*********end***************获取发送业务员短信 完毕*****************end*****************");
				}
			}
		}

		
		
		return true;
	}

	/**
	 * preparePrt 准备打印表
	 * 
	 * @return boolean
	 */
	private boolean preparePrt() {
		// 处于未打印状态的通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		// 生成给付通知书号

		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("URGEInterval");

		if (tLDSysVarDB.getInfo() == false) {
			CError.buildErr(this, "没有描述催发间隔!") ;
			return false;
		}
		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);
		// tongmeng 2007-11-12 modify
		// 需要重新初始化mLOPRTManagerSchema
		if (mUWSendFlag.equals("1")) {
			// 发送核保通知书
			//String tLimit = PubFun.getNoLimit(mManageCom);
			String cLimit = PrintManagerBL.CODE_UW.replaceAll("[^0-9]", "");
			logger.debug("---cLimit---" + cLimit);
			mPrtSeqUW = PubFun1.CreateMaxNo("UWPRTSEQ", cLimit);
			//logger.debug("---tLimit---" + tLimit);

			// 准备打印管理表数据
			mLOPRTManagerSchema = new LOPRTManagerSchema();
			mLOPRTManagerSchema.setPrtSeq(mPrtSeqUW);
			mLOPRTManagerSchema.setOtherNo(mContNo);
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_UW); // 核保(打印类)
			mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
			mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
			mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			// mLOPRTManagerSchema.setExeCom();
			// mLOPRTManagerSchema.setExeOperator();
			mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSchema.setPatchFlag("0");
			mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			// mLOPRTManagerSchema.setDoneDate() ;
			// mLOPRTManagerSchema.setDoneTime();
			mLOPRTManagerSchema.setStandbyFlag1(mLCContSchema.getAppntNo()); // 投保人编码
			mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqUW);
			mLOPRTManagerSchema.setForMakeDate(tDate);

			mLOPRTManagerSet.add(mLOPRTManagerSchema);
			for (int i = 1; i <= this.mLCIssuePolSet_UWSend.size(); i++) {
				this.mLCIssuePolSet_UWSend.get(i).setPrtSeq(mPrtSeqUW);
				this.mLCIssuePolSet_UWSend.get(i).setState("0");
				//tongmeng 2009-04-08 add
				//记录下发时间
				this.mLCIssuePolSet_UWSend.get(i).setSendDate(this.mCurrentDate);
				this.mLCIssuePolSet_UWSend.get(i).setSendTime(this.mCurrentTime);
			}
			if (mLCIssuePolSet_UWSend.size() > 0) {
				this.mLCIssuePolSet.add(mLCIssuePolSet_UWSend);
			}

			// tongmeng 2007-12-27 add
			// 特约处理 modify ln 2008-12-3 对合同级特约不置打印流水号，要打在每个被保人核保通知书上
			String tSQL_spec = "select * from lccspec where proposalcontno="
				+ "(select proposalcontno from lccont where contno='" + "?z1?" + "') and  needprint='Y' "
				+ " and (customerno='"+"?z2?"+"' and '"+"?z3?"+"' ='I' and prtflag is null)";
			SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
			sqlbv17.sql(tSQL_spec);
			sqlbv17.put("z1", mContNo);
			sqlbv17.put("z2", this.mCustomerNo);
			sqlbv17.put("z3", this.mCustomerNoType);
			LCCSpecDB tLCCSpecDB = new LCCSpecDB();
			LCCSpecSet tLCCSpecSet = new LCCSpecSet();
			tLCCSpecSet = tLCCSpecDB.executeQuery(sqlbv17);
			for (int i = 1; i <= tLCCSpecSet.size(); i++) {
				LCCSpecSchema tempLCCSpecSchema = new LCCSpecSchema();
				tempLCCSpecSchema.setSchema(tLCCSpecSet.get(i));
				tempLCCSpecSchema.setPrtSeq(mPrtSeqUW);
				tempLCCSpecSchema.setPrtFlag("0");
				this.mLCCSpecSet.add(tempLCCSpecSchema);
			}	
			tSQL_spec = "select * from lccspec where proposalcontno="
				+ "(select proposalcontno from lccont where contno='" + "?f1?" + "') and  needprint='Y' "
				+ " and customerno is null";
			SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
			sqlbv18.sql(tSQL_spec);
			sqlbv18.put("f1", mContNo);
			tLCCSpecDB = new LCCSpecDB();
			tLCCSpecSet = new LCCSpecSet();
			tLCCSpecSet = tLCCSpecDB.executeQuery(sqlbv18);
			for (int i = 1; i <= tLCCSpecSet.size(); i++) {
				LCCSpecSchema tempLCCSpecSchema = new LCCSpecSchema();
				tempLCCSpecSchema.setSchema(tLCCSpecSet.get(i));
				tempLCCSpecSchema.setPrtFlag("0");
				this.mLCCSpecSet.add(tempLCCSpecSchema);
			}
			
		}
		if (mUWSendFlag_UnPrint.equals("1")) {
			// 发送核保通知书
			//String tLimit = PubFun.getNoLimit(mManageCom);
			String cLimit = PrintManagerBL.CODE_UW_UnPrint.replaceAll("[^0-9]", "");
			logger.debug("---cLimit---" + cLimit);
			mPrtSeqUW_UnPrint = PubFun1.CreateMaxNo("UWPRTSEQ", cLimit);
			//logger.debug("---tLimit---" + tLimit);

			// 准备打印管理表数据
			mLOPRTManagerSchema = new LOPRTManagerSchema();
			mLOPRTManagerSchema.setPrtSeq(mPrtSeqUW_UnPrint);
			mLOPRTManagerSchema.setOtherNo(mContNo);
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_UW_UnPrint); //核保通知书(
																			// 非打印类
																			// )
			mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
			mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
			mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			// mLOPRTManagerSchema.setExeCom();
			// mLOPRTManagerSchema.setExeOperator();
			mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSchema.setPatchFlag("0");
			mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			// mLOPRTManagerSchema.setDoneDate() ;
			// mLOPRTManagerSchema.setDoneTime();
			mLOPRTManagerSchema.setStandbyFlag1(mLCContSchema.getAppntNo()); // 投保人编码
			mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqUW_UnPrint);
			mLOPRTManagerSchema.setForMakeDate(tDate);

			mLOPRTManagerSet.add(mLOPRTManagerSchema);
			for (int i = 1; i <= this.mLCIssuePolSet_UWSend_UnPrint.size(); i++) {
				this.mLCIssuePolSet_UWSend_UnPrint.get(i).setPrtSeq(
						mPrtSeqUW_UnPrint);
				this.mLCIssuePolSet_UWSend_UnPrint.get(i).setState("0");
				this.mLCIssuePolSet_UWSend_UnPrint.get(i).setSendDate(this.mCurrentDate);
				this.mLCIssuePolSet_UWSend_UnPrint.get(i).setSendTime(this.mCurrentTime);
			}
			if (mLCIssuePolSet_UWSend_UnPrint.size() > 0) {
				this.mLCIssuePolSet.add(mLCIssuePolSet_UWSend_UnPrint);
			}

		}
		// 业务员问题件
		if (mSendOperFlag.equals("1")) {
			//String tLimit = PubFun.getNoLimit(mManageCom);
			mPrtSeqOper = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_AGEN_QUEST);
			mLOPRTManagerSchema = new LOPRTManagerSchema();
			// 准备打印管理表数据
			mLOPRTManagerSchema.setPrtSeq(mPrtSeqOper);
			mLOPRTManagerSchema.setOtherNo(mContNo);
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_AGEN_QUEST); // 业务员通知书
			mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
			mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
			mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			// mLOPRTManagerSchema.setExeCom();
			// mLOPRTManagerSchema.setExeOperator();
			mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSchema.setPatchFlag("0");
			mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			// mLOPRTManagerSchema.setDoneDate() ;
			// mLOPRTManagerSchema.setDoneTime();
			mLOPRTManagerSchema.setStandbyFlag1(mLCContSchema.getAppntNo()); // 投保人编码
			mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqOper);

			mLOPRTManagerSet.add(mLOPRTManagerSchema);
			for (int i = 1; i <= this.mLCIssuePolSet_SendOper.size(); i++) {
				this.mLCIssuePolSet_SendOper.get(i).setPrtSeq(mPrtSeqOper);
				this.mLCIssuePolSet_SendOper.get(i).setState("0");
				this.mLCIssuePolSet_SendOper.get(i).setSendDate(this.mCurrentDate);
				this.mLCIssuePolSet_SendOper.get(i).setSendTime(this.mCurrentTime);
			}
			if (mLCIssuePolSet_SendOper.size() > 0) {
				this.mLCIssuePolSet.add(mLCIssuePolSet_SendOper);
			}
		}
		// 判断机构问题件
		if (this.mQuesOrgFlag.equals("1")) {
			//String tLimit = PubFun.getNoLimit(mManageCom);
			mPrtSeqCom = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_ISSUE);
			for (int i = 1; i <= this.mLCIssuePolSet_QuesOrg.size(); i++) {
				this.mLCIssuePolSet_QuesOrg.get(i).setState("0");
				this.mLCIssuePolSet_QuesOrg.get(i).setPrtSeq(mPrtSeqCom);
				this.mLCIssuePolSet_QuesOrg.get(i).setSendDate(this.mCurrentDate);
				this.mLCIssuePolSet_QuesOrg.get(i).setSendTime(this.mCurrentTime);

			}
			if (mLCIssuePolSet_QuesOrg.size() > 0) {
				this.mLCIssuePolSet.add(mLCIssuePolSet_QuesOrg);
			}
		}
		// 查询是否有问题件修改岗的问题件,如果有的话,将状态设置为0
		//tongmeng 2009-05-12 modify
		//needprint 
		String tsql = "select * from lcissuepol where 1=1" + " and Contno = '"
				+ "?f2?" + "' and backobjtype = '1' and state is null "
				+ " and needprint='Y' ";
		SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
		sqlbv19.sql(tsql);
		sqlbv19.put("f2", mContNo);
		LCIssuePolSet tempLCIssuePolSet = new LCIssuePolSet();
		LCIssuePolDB tempLCIssuePolDB = new LCIssuePolDB();
		
		tempLCIssuePolSet = tempLCIssuePolDB.executeQuery(sqlbv19);
		for (int i = 1; i <= tempLCIssuePolSet.size(); i++) {
			tempLCIssuePolSet.get(i).setState("0");// 设置为已发放
			tempLCIssuePolSet.get(i).setModifyDate(PubFun.getCurrentDate());
			tempLCIssuePolSet.get(i).setModifyTime(PubFun.getCurrentTime());
			tempLCIssuePolSet.get(i).setSendDate(this.mCurrentDate);
			tempLCIssuePolSet.get(i).setSendTime(this.mCurrentTime);
			this.mLCIssuePolSet.add(tempLCIssuePolSet.get(i));
		}	
		
		// ln 2008-10-07 add
		if (mSendReportNoticeFlag.equals("1")) {
			// 发送生调通知书	
			// 生调通知书处理			
			if(this.mLCRReportSet_org.size()>0) {				
				this.mLCRReportSet_org.get(1).setReplyFlag("0");//已发送
				this.mLCRReportSet_org.get(1).setModifyDate(PubFun.getCurrentDate());
				this.mLCRReportSet_org.get(1).setModifyTime(PubFun.getCurrentTime());
				this.mLCRReportSet_org.get(1).setSendDate(this.mCurrentDate);
				this.mLCRReportSet_org.get(1).setSendTime(this.mCurrentTime);
				this.mLCRReportSet.add(mLCRReportSet_org);
				
				// 准备打印管理表数据		
				mLOPRTManagerSchema = new LOPRTManagerSchema();
				mLOPRTManagerSchema.setPrtSeq(this.mLCRReportSet_org.get(1).getPrtSeq());
				mLOPRTManagerSchema.setOtherNo(mContNo);
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_MEET); // 生调
				mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
				mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
				mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
				mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
				// mLOPRTManagerSchema.setExeCom();
				// mLOPRTManagerSchema.setExeOperator();
				mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
				mLOPRTManagerSchema.setStateFlag("0");
				mLOPRTManagerSchema.setPatchFlag("0");
				mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
				mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
				// mLOPRTManagerSchema.setDoneDate() ;
				// mLOPRTManagerSchema.setDoneTime();
				mLOPRTManagerSchema.setStandbyFlag1(mCustomerNo); // 被保险人编码
				mLOPRTManagerSchema.setStandbyFlag2(mLCContSchema.getPrtNo());  
				mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
				mLOPRTManagerSchema.setOldPrtSeq(this.mLCRReportSet_org.get(1).getPrtSeq());
				mLOPRTManagerSchema.setForMakeDate(tDate);
				
				mLOPRTManagerSet.add(mLOPRTManagerSchema);		
				
				mTransferData
				.setNameAndValue("PrtSeq", this.mLCRReportSet_org.get(1).getPrtSeq());
				mTransferData
				.setNameAndValue("SaleChnl", mLCContSchema.getSaleChnl());
			}
			else
				mSendReportNoticeFlag = "0";
		}
		
		// ln 2008-10-29 add
		if (mSendPENoticeFlag.equals("1")) {
			// 发送体检通知书				
			for (int i = 1; i <= mLCPENoticeSet.size(); i++) {
				//更新体检表
				mLCPENoticeSet.get(i).setPrintFlag("0");//已发送
				mLCPENoticeSet.get(i).setModifyDate(PubFun.getCurrentDate());
				mLCPENoticeSet.get(i).setModifyTime(PubFun.getCurrentTime());
				mLCPENoticeSet.get(i).setSendDate(this.mCurrentDate);
				mLCPENoticeSet.get(i).setSendTime(this.mCurrentTime);
				// 准备打印管理表数据		
				mLOPRTManagerSchema = new LOPRTManagerSchema();
				mLOPRTManagerSchema.setPrtSeq(mLCPENoticeSet.get(i).getPrtSeq());
				mLOPRTManagerSchema.setOtherNo(mContNo);
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PE); // 生调
				mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
				mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
				mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
				mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
				// mLOPRTManagerSchema.setExeCom();
				// mLOPRTManagerSchema.setExeOperator();
				mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
				mLOPRTManagerSchema.setStateFlag("0");
				mLOPRTManagerSchema.setPatchFlag("0");
				mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
				mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
				// mLOPRTManagerSchema.setDoneDate() ;
				// mLOPRTManagerSchema.setDoneTime();
				mLOPRTManagerSchema.setStandbyFlag1(mCustomerNo); // 接收对象编码
				mLOPRTManagerSchema.setStandbyFlag2(mLCContSchema.getPrtNo());  
				mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
				mLOPRTManagerSchema.setOldPrtSeq(mLCPENoticeSet.get(i).getPrtSeq());
				mLOPRTManagerSchema.setForMakeDate(tDate);
				
				mLOPRTManagerSet.add(mLOPRTManagerSchema);		
				
				mTransferData
				.setNameAndValue("PrtSeqPE", mLCPENoticeSet.get(i).getPrtSeq());
				mTransferData
				.setNameAndValue("OldPrtSeq", mLCPENoticeSet.get(i).getPrtSeq());
				mTransferData
				.setNameAndValue("SaleChnl", mLCContSchema.getSaleChnl());
			}			
		}
		
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("SendOperFlag", mSendOperFlag);

		mTransferData.setNameAndValue("UWSendFlag", mUWSendFlag); // 打印核保通知书标志
		mTransferData
				.setNameAndValue("UWSendFlag_UnPrint", mUWSendFlag_UnPrint); // 打印核保通知书非打印类标志
		mTransferData.setNameAndValue("QuesOrgFlag", mQuesOrgFlag);
		mTransferData.setNameAndValue("ApproveModifyFlag", mApproveModifyFlag);
		if(this.mApproveModifyFlag.equals("1"))
		{
			mTransferData.setNameAndValue("State", "1");
			//tongmeng 2009-04-14 add
			//如果是问题件已回复,记录回复日期和时间
			mTransferData.setNameAndValue("ReplyDate", PubFun.getCurrentDate());
			mTransferData.setNameAndValue("ReplyTime", PubFun.getCurrentTime());	
		}
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSet tLAAgentSet = new LAAgentSet();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		tLAAgentSet = tLAAgentDB.query();
		if (tLAAgentSet == null || tLAAgentSet.size() != 1) {
			// @@错误处理
			CError.buildErr(this, "代理人表LAAgent查询失败!");
			return false;
		}

		if (tLAAgentSet.get(1).getAgentGroup() == null) {
			// @@错误处理
			CError.buildErr(this, "代理人表LAAgent中的代理机构数据丢失!");
			return false;
		}

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
		tLABranchGroupDB.setAgentGroup(tLAAgentSet.get(1).getAgentGroup());
		tLABranchGroupSet = tLABranchGroupDB.query();
		if (tLABranchGroupSet == null || tLABranchGroupSet.size() != 1) {
			// @@错误处理
			CError.buildErr(this, "代理人展业机构表LABranchGroup查询失败!");
			return false;
		}

		if (tLABranchGroupSet.get(1).getBranchAttr() == null) {
			// @@错误处理
			CError.buildErr(this, "代理人展业机构表LABranchGroup中展业机构信息丢失!");
			return false;
		}
		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", tLAAgentSet.get(1)
				.getAgentGroup());
		mTransferData.setNameAndValue("BranchAttr", tLABranchGroupSet.get(1)
				.getBranchAttr());
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("PrtSeqUW", mPrtSeqUW);
		mTransferData.setNameAndValue("PrtSeqUW_UnPrint", mPrtSeqUW_UnPrint);
		mTransferData.setNameAndValue("PrtSeqOper", mPrtSeqOper);
		mTransferData.setNameAndValue("PrtSeqCom", mPrtSeqCom);
		mTransferData.setNameAndValue("SendReportNoticeFlag", mSendReportNoticeFlag);
		mTransferData.setNameAndValue("SendPENoticeFlag", mSendPENoticeFlag);//2008-10-29 add ln 是否有带发放体检通知书标志
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		mTransferData.setNameAndValue("ApproveDate", mLCContSchema
				.getApproveDate());
		mTransferData
		.setNameAndValue("CustomerNo", this.mCustomerNo);//打印核保通知书用--客户号
		mTransferData
		.setNameAndValue("CustomerNoType", this.mCustomerNoType);
		mTransferData.setNameAndValue("SpecFlag", this.mSpecFlag);//是否有特约标记
		mTransferData.setNameAndValue("ChangePolFlag", this.mChangePolFlag);
		mTransferData.setNameAndValue("AddFeeFlag", this.mAddFeeFlag);

		return true;
	}

	/**
	 * 向工作流引擎提交数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		//map.put(mLCContSchema, "UPDATE");
		//map.put(mLCPolSet, "UPDATE");
		map.put(mLCCUWMasterSchema, "UPDATE");
		map.put(mLCIssuePolSet, "UPDATE");
		map.put(mLCCUWSubSet, "INSERT");
		map.put(mLCIndUWMasterSchema, "UPDATE");
		map.put(mLCIndUWSubSet, "INSERT");//新增被保人核保记录 2008-12-5 add ln
		map.put(mLCUWMasterSet, "UPDATE");
		map.put(mLCUWSubSet, "INSERT");
		map.put(mLOPRTManagerSet, "INSERT");
		map.put(this.mLCCSpecSet, "UPDATE");
		map.put(this.mLCRReportSet, "UPDATE");
		map.put(mLCPENoticeSet, "UPDATE");//修改体检录入表 2008-10-29 add ln
		map.add(agentMSmap); //新增发送业务员短信 2010-04-12 hanbin 
		mResult.add(map);
		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
