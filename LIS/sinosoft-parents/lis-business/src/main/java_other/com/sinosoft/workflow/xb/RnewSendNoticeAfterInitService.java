package com.sinosoft.workflow.xb;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.RnewIndUWMasterDB;
import com.sinosoft.lis.db.RnewIndUWSubDB;
import com.sinosoft.lis.db.RnewIssuePolDB;
import com.sinosoft.lis.db.RnewPENoticeDB;
import com.sinosoft.lis.db.RnewRReportDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.RnewIndUWMasterSchema;
import com.sinosoft.lis.schema.RnewIndUWSubSchema;
import com.sinosoft.lis.schema.RnewIssuePolSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.RnewIndUWSubSet;
import com.sinosoft.lis.vschema.RnewIssuePolSet;
import com.sinosoft.lis.vschema.RnewPENoticeSet;
import com.sinosoft.lis.vschema.RnewRReportSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class RnewSendNoticeAfterInitService implements AfterInitService{
private static Logger logger = Logger.getLogger(RnewSendNoticeAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 核保主表 */
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();

	/** 核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	
	private RnewIndUWSubSet mRnewIndUWSubSet = new RnewIndUWSubSet();
	
	private RnewIndUWMasterSchema mRnewIndUWMasterSchema = new RnewIndUWMasterSchema();

	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private RnewIssuePolSet mRnewIssuePolSet = new RnewIssuePolSet();
	private LCCSpecSet mLCCSpecSet = new LCCSpecSet();// 特约通知书
	private RnewRReportSet mRnewRReportSet_org = new RnewRReportSet();// 生调通知书
	private RnewRReportSet mRnewRReportSet = new RnewRReportSet();// 生调通知书
	private RnewPENoticeSet mRnewPENoticeSet = new RnewPENoticeSet();// 体检通知书
	
	//险种信息
	private String mRiskCode="";
	private String mProposalNo="";

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	/** 0005节点的submissionid*/
	private String mSubMissionID;//
	/** 0100节点的submissionid*/
	private String noticeSubMissionID;
	private String mPrtSeq;
	private String mAgentCode;
	private String tManageCom;
	private String mPrtNo;
	private String mSaleChnl;
	private String mAppntName;
	private String mPrtSeqUW = "0";
	private String mPrtSeqUW_UnPrint = "0";
	private String mPrtSeqOper = "0";	
	private String mPrtSeqCom = "";
	private String mCustomerNo = "";// 方法对象
	private String mCustomerNoType = "";// 发放对象类型(A 投保人,业务员,机构问题件,操作员问题件),I(被保人)
	/** 保单表 */
	private LPContSchema mLPContSchema = new LPContSchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	private String mUWFlag = ""; // 核保标志
	private String mQuestFlag = "0";//是否有问题件,续保二核只有客户问题件
	private String mAddFeeFlag = "0";//是否存在加费
	private String mSpecFlag = "0";//是否存在特约
	private String mChangePolFlag = "0";//是否存在承保计划变更

	/** 工作流扭转标志 */
	private String mUWSendFlag = "";// 发送打印核保通知书标志
	private String mUWSendFlag_UnPrint = "";// 发送不打印核保通知书标志
	private String mSendOperFlag = "";// 发送业务员通知书标志
	private String mQuesOrgFlag = "0"; //发送机构问题件标志
	private String mApproveModifyFlag = "";// 发送问题件修改岗标志
	private String mSendReportNoticeFlag = "0";//发送生调通知书标志
	private String mSendPENoticeFlag = "0";//是否存在体检录入的标志，0为不存在不需要发放 否则为1

	private RnewIssuePolSet mRnewIssuePolSet_UWSend = new RnewIssuePolSet();// 核保通知书问题件集合
	private RnewIssuePolSet mRnewIssuePolSet_UWSend_UnPrint = new RnewIssuePolSet();// 不打印核保通知书问题件集合
	private RnewIssuePolSet mRnewIssuePolSet_SendOper = new RnewIssuePolSet();// 业务员通知书问题件集合

	private RnewIssuePolSet mRnewIssuePolSet_QuesOrg = new RnewIssuePolSet();// 机构问题件集合
	private RnewIssuePolSet mRnewIssuePolSet_ApproveModify = new RnewIssuePolSet();// 问题件修改岗问题件集合
	private LWMissionSet mLWMissionSet = new LWMissionSet();
	
	private String testRnewFlag = "";//是否执行本类判断条件
	
	private MMap map = new MMap();

	public RnewSendNoticeAfterInitService() {
		
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
		if(testRnewFlag=="1"){
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
		}
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
		String strSql="";
		ExeSQL tExeSQL = new ExeSQL();
		
//		 1 没有未回复的问题件则不能发核保通知书
		 strSql = "select count(1) from Rnewissuepol where 1=1"
				+ " and Contno = '"+ "?mContNo?" + "' "
				+ " and state is null and needprint = 'Y'"
				// 判断有没有发放给业务员问题件
				+ " and ("
				// 判断有没有发放给保户的问题件
				+ " (backobjtype = '2' and replyresult is null and prtseq is null) "
				//+ " and ((questionobj='"
				//+ this.mCustomerNo
				//+ "' and questionobjtype<>'0' and '"
				//+ this.mCustomerNoType
				//+ "'='I') "
				//+ " or (questionobj='"
				//+ this.mCustomerNo
				//+ "' and questionobjtype='0' and '"
				//+ this.mCustomerNoType
				//+ "'='A'))) " 
				+" or (backobjtype = '4' and replyresult is null ))";
			   // +"  and '"+ this.mCustomerNoType + "'='A')) ";
		logger.debug("判断是否有需要下发的问题件 strSql:" + strSql);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSql);
		sqlbv1.put("mContNo", mContNo);
		int rs = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
		if (rs > 0) {
			flag = 1;
		}
		
//		 没有承保计划变更，不能发核保通知书
		strSql = "select count(1) from LCUWMaster where contno='"
			+ "?mContNo?" + "' and ChangePolFlag='1' "
			+ " and exists(select 1 from lcpol a where a.polno=LCUWMaster.polno )";
		  //  + " and insuredno='"+this.mCustomerNo+"' and '"+this.mCustomerNoType+"' ='I')";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strSql);
		sqlbv2.put("mContNo", mContNo);
		tExeSQL = new ExeSQL();
		int rs_change = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
		if (rs_change > 0) {		    
			flag = 1;
			mChangePolFlag = "1";
		}	

		// 没有加费，不能发核保通知书
		//判断是否有投保单的加费
		strSql = "select count(1) from lcprem where contno='"
			+ "?mContNo?" + "' and payplancode like '000000%%' "
			+ " and exists(select 1 from lcpol a where a.polno=lcprem.polno and a.appflag='9')";
		    //+ " and insuredno='"+this.mCustomerNo+"' and '"+this.mCustomerNoType+"' ='I')";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(strSql);
		sqlbv3.put("mContNo", mContNo);
		tExeSQL = new ExeSQL();
		int rs_addfee = Integer.parseInt(tExeSQL.getOneValue(sqlbv3));
		if (rs_addfee > 0) {		    
			flag = 1;
			mAddFeeFlag = "1";
		}	
		// 没有特约，不能发核保通知书
		strSql = "select count(1) from lccspec where proposalcontno="
			+ "(select proposalcontno from lccont where contno='" + "?mContNo?" + "') and  needprint='Y' and prtflag is null";
			//+ " and (customerno is null or (customerno='"+this.mCustomerNo+"' and '"+this.mCustomerNoType+"' ='I' and prtflag is null))";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(strSql);
		sqlbv4.put("mContNo", mContNo);
		tExeSQL = new ExeSQL();
		int rs_spec = Integer.parseInt(tExeSQL.getOneValue(sqlbv4));
		if (rs_spec > 0) {		    
			flag = 1;
			mSpecFlag = "1";
		}
		// tongmeng 2008-08-12 add
		// 增加对体检和生调的发放校验
		//add 2008-10-06 生调的发放校验
		String tSQL_exist = "select count(1) from RnewRReport where contno='"
			+ "?mContNo?" + "' and replyflag is null";			
		logger.debug("判断是否有需要放送的生调通知书 tSQL_exist:" + tSQL_exist);
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSQL_exist);
		sqlbv5.put("mContNo", mContNo);
		tExeSQL = new ExeSQL();
		int rs_exist = Integer.parseInt(tExeSQL.getOneValue(sqlbv5));
		if (rs_exist > 0) {		    
			flag = 1;
		}	
		
		//体检的发放校验 add 2008-10-29
//		strSql = "select count(1) from RnewPENotice where contno='"
//			+ mContNo + "' and printflag is null and customerno='"+this.mCustomerNo+"' and CustomerType='"+this.mCustomerNoType+"'";			
		strSql = "select count(1) from RnewPENotice where contno='"
			+ "?mContNo?" + "' and printflag is null";			
		logger.debug("判断是否有需要放送的体检通知书 strSql:" + strSql);
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(strSql);
		sqlbv6.put("mContNo", mContNo);
		tExeSQL = new ExeSQL();
		int rs_health = Integer.parseInt(tExeSQL.getOneValue(sqlbv6));
		if (rs_health > 0) {
			flag = 1;
		}
		
		//
		if (flag == 0) {
			// @@错误处理
			//暂时这样处理
			//mUWSendFlag = "N";
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
		testRnewFlag = (String)mTransferData.getValueByName("testRnewFlag");
		if (testRnewFlag == null || testRnewFlag.trim().equals("")) {
			CError.buildErr(this, "前台传输全局公共数据testRnewFlag失败!");
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
		
		// 获得当前工作任务的任务IDR
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		noticeSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		
		if (mSubMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!");
			return false;
		}
		// tongmeng 2008-08-12 add
		// 先查询核保通知书发放对象
		String tSQL = "select missionprop15,missionprop16 from lwmission where missionid='"
				+ "?missionid?" + "' " + " and activityid='" + "?cOperate?" + "' and submissionid='"+ "?submissionid?" +"'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSQL);
		sqlbv7.put("missionid", this.mMissionID);
		sqlbv7.put("cOperate", cOperate);
		sqlbv7.put("submissionid", this.mSubMissionID);
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
		if (preparePol() == false)
			return false;
		
		if (this.mCustomerNoType=="I" && prepareIndUW() == false)
			return false;
		// 选择工作流流转活动
		if (chooseActivity() == false)
			return false;
        //准备合同核保表信息
		if (prepareContUW() == false)
			return false;
		// if (mUWSendFlag.equals("1") || mSendOperFlag.equals("1")||this.)
		//{
			// 准备打印管理表信息
		if (preparePrt() == false)
			return false;
			// tongmeng 2007-11-13 modify
			// 下面处理问题件逻辑有问题,修改在preparePrt进行处理
			// if (prepareIssue() == false)
			// return false;
		//}
		//此处不用产生发核保通知书的节点
//		if(!dealMission()){
//			return false;
//		}
		return true;
	}
	/** 
	 * 将当前核保状态改为2——核保未回复状态
	 * */
	private boolean dealMission(){
		
		//将起始任务操作员置空
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(mMissionID);
		//tLWMissionDB.setSubMissionID(mSubMissionID);
		tLWMissionDB.setActivityID("0000007001");
		tLWMissionSet=tLWMissionDB.query();
		if(tLWMissionSet.size()!=1){
//		if(!tLWMissionDB.getInfo()){
			CError.buildErr(this, "查询LWMission失败！");
			return false;
		}
		LWMissionSchema mLWMissionSchema = new LWMissionSchema();
		mLWMissionSchema = tLWMissionSet.get(1);
		mLWMissionSchema.setActivityStatus("2");
		mLWMissionSchema.setMissionProp12("3");//置为核保未回复状态
		mLWMissionSchema.setDefaultOperator("");//发完核保通知书返回共享池中并置为2状态
		mLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		mLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
		mLWMissionSet.add(mLWMissionSchema);
		//同时需要将同保单号下其他任务的defaultoperator置空
		LWMissionSet tLWMissionSet2 = new LWMissionSet();
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(" select * from LWMission a where a.ActivityID='0000007001' and a.missionprop2='"+"?missionprop2?"+"' and a.missionid<>'"+"?mMissionID?"+"' ");
		sqlbv8.put("missionprop2", mLWMissionSchema.getMissionProp2());
		sqlbv8.put("mMissionID", mMissionID);
		tLWMissionSet2 = tLWMissionDB.executeQuery(sqlbv8);
		for(int x=1;x<=tLWMissionSet2.size();x++)
		{
			LWMissionSchema xLWMissionSchema = new LWMissionSchema();
			xLWMissionSchema = tLWMissionSet2.get(x);
			xLWMissionSchema.setDefaultOperator("");
			xLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
			xLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
			mLWMissionSet.add(xLWMissionSchema);
		}
		//给发送通知书任务置上操作员
		LWMissionSchema xLWMissionSchema = new LWMissionSchema();
		LWMissionDB xLWMissionDB = new LWMissionDB();
		LWMissionSet xLWMissionSet = new LWMissionSet();
		xLWMissionDB.setMissionID(mMissionID);
		//tLWMissionDB.setSubMissionID(mSubMissionID);
		xLWMissionDB.setActivityID("0000007002");
		xLWMissionSet=xLWMissionDB.query();
		if(xLWMissionSet.size()==0){
//		if(!tLWMissionDB.getInfo()){
			CError.buildErr(this, "查询LWMission失败！");
			return false;
		}
		for(int i=1;i<=xLWMissionSet.size();i++)
		{
			xLWMissionSchema = xLWMissionSet.get(i);
			xLWMissionSchema.setDefaultOperator(this.mGlobalInput.Operator);
			xLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
			xLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
			mLWMissionSet.add(xLWMissionSchema);
		}
		
		return true;
	}
	
	/**
	 * prepareIssue 准备RnewIssuePol表数据，生成prtseq
	 * 
	 * @return boolean
	 */
	private boolean prepareIssue() {
		RnewIssuePolSet tRnewIssuePolSet = new RnewIssuePolSet();
		RnewIssuePolDB tRnewIssuePolDB = new RnewIssuePolDB();
		RnewIssuePolSchema tRnewIssuePolSchema = new RnewIssuePolSchema();
		tRnewIssuePolDB.setContNo(mContNo);
		tRnewIssuePolSet = tRnewIssuePolDB.query();

		for (int i = 1; i <= tRnewIssuePolSet.size(); i++) {
			tRnewIssuePolSchema = new RnewIssuePolSchema();
			tRnewIssuePolSchema = tRnewIssuePolSet.get(i);
			if (tRnewIssuePolSchema.getPrtSeq() == ""
					|| tRnewIssuePolSchema.getPrtSeq() == null) {
				if (tRnewIssuePolSchema.getBackObjType().equals("2"))
					tRnewIssuePolSchema.setPrtSeq(mPrtSeqOper);
				else
					tRnewIssuePolSchema.setPrtSeq(mPrtSeqUW);
			}
			mRnewIssuePolSet.add(tRnewIssuePolSchema);
		}

		return true;
	}

	/**
	 * preparePrt 准备合同表数据,并置UWFlag=8,表示生成核保通知书
	 * 
	 * @return boolean
	 */
	private boolean preparePol() {
		//先根据missionID取得riskcode
		//续保二核不需要对合同级做处理
		ExeSQL tExeSQL = new ExeSQL();
		mRiskCode="";
		mProposalNo="";
		String Date_sql="select MissionProp4,MissionProp3 from lwmission where missionid='"+"?missionid?"+"' "
         +" and activityid=(select activityid from lwactivity where functionId = '10047001') ";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(Date_sql);
		sqlbv9.put("missionid", this.mMissionID);
		SSRS xSSRS = new SSRS();
		xSSRS = tExeSQL.execSQL(sqlbv9);
		mRiskCode = xSSRS.GetText(1, 1);
		mProposalNo = xSSRS.GetText(1, 2);
		// 准备险种表数据
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mContNo);
		tLCPolDB.setRiskCode(mRiskCode);
		
		if (tLCPolDB.query().size()>0) 
		{
			mAgentCode = tLCPolDB.query().get(1).getAgentCode();
			tManageCom = tLCPolDB.query().get(1).getManageCom();
			mPrtNo = tLCPolDB.query().get(1).getPrtNo();
			mSaleChnl = tLCPolDB.query().get(1).getSaleChnl();
			mAppntName = tLCPolDB.query().get(1).getAppntName();
		}
		else
		{
			CError.buildErr(this, "查询保单号"+mContNo+"下险种"+mRiskCode+"失败!");
			return false;
		}
		mUWFlag = "8"; // 核保订正标志
		// 准备保单的复核标志
		//mLPContSchema.setUWFlag(mUWFlag);
		// 准备险种合同表数据
		LCPolDB xLCPolDB = new LCPolDB();
		xLCPolDB.setContNo(mContNo);
		xLCPolDB.setUWFlag("5");  //查询带二核的保单记录
		mLCPolSet = xLCPolDB.query();
		// 准备险种保单的复核标志
		for (int i = 1; i < mLCPolSet.size(); i++) 
		{
			mLCPolSet.get(i).setUWFlag(mUWFlag);
		}
		return true;
	}
	
	/**
	 * preparePrt 准备被保人核保主表数据
	 * 
	 * @return boolean
	 */
	private boolean prepareIndUW() {
		mRnewIndUWSubSet.clear();

		RnewIndUWMasterDB tRnewIndUWMasterDB = new RnewIndUWMasterDB();
		tRnewIndUWMasterDB.setContNo(mContNo);
		tRnewIndUWMasterDB.setInsuredNo(mCustomerNo);
		if (!tRnewIndUWMasterDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "RnewIndUWMaster表取数失败!");
			return false;
		}
		mRnewIndUWMasterSchema.setSchema(tRnewIndUWMasterDB);
		mRnewIndUWMasterSchema.setPassFlag(mUWFlag);

		// 每次进行核保相关操作时，向核保轨迹表插一条数据
		RnewIndUWSubSchema tRnewIndUWSubSchema = new RnewIndUWSubSchema();
		RnewIndUWSubDB tRnewIndUWSubDB = new RnewIndUWSubDB();
		tRnewIndUWSubDB.setContNo(mContNo);
		tRnewIndUWSubDB.setInsuredNo(mCustomerNo);
		RnewIndUWSubSet tRnewIndUWSubSet = new RnewIndUWSubSet();
		tRnewIndUWSubSet = tRnewIndUWSubDB.query();
		if (tRnewIndUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			CError.buildErr(this, "RnewIndUWSub表取数失败!");
			return false;
		}

		int m = tRnewIndUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tRnewIndUWSubSchema = new RnewIndUWSubSchema();
			tRnewIndUWSubSchema.setUWNo(m+mRnewIndUWSubSet.size()); // 第几次核保
			tRnewIndUWSubSchema.setContNo(mContNo);
			tRnewIndUWSubSchema.setInsuredNo(mCustomerNo);
			tRnewIndUWSubSchema.setGrpContNo(mRnewIndUWMasterSchema.getGrpContNo());
			tRnewIndUWSubSchema.setProposalContNo(mRnewIndUWMasterSchema
					.getProposalContNo());
			tRnewIndUWSubSchema.setPassFlag(mUWFlag); // 核保意见
			tRnewIndUWSubSchema.setPrintFlag("1");
			tRnewIndUWSubSchema.setAutoUWFlag(mRnewIndUWMasterSchema.getAutoUWFlag());
			tRnewIndUWSubSchema.setState(mRnewIndUWMasterSchema.getState());
			tRnewIndUWSubSchema.setOperator(mRnewIndUWMasterSchema.getOperator()); // 操作员
			tRnewIndUWSubSchema.setManageCom(mRnewIndUWMasterSchema.getManageCom());
			tRnewIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tRnewIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tRnewIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tRnewIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			CError.buildErr(this, "RnewIndUWSub表取数失败!");
			return false;
		}
		mRnewIndUWSubSet.add(tRnewIndUWSubSchema);
		return true;
	}
	/**
	 * preparePrt 准备核保主表数据
	 * 
	 * @return boolean
	 */
	private boolean prepareContUW()
	{
		mLCUWSubSet.clear();

		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setContNo(mContNo);
		tLCUWMasterDB.setProposalNo(this.mProposalNo);
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet=tLCUWMasterDB.query();
		if (tLCUWMasterSet.size()==0) 
		{
			// @@错误处理
			CError.buildErr(this, "LPCUWMaster表取数失败!");
			return false;
		}
		for(int i=1;i<=tLCUWMasterSet.size();i++)
		{
			
		    mLCUWMasterSchema.setSchema(tLCUWMasterSet.get(i));
		    mLCUWMasterSchema.setPassFlag(mUWFlag);
		    mLCUWMasterSchema.setOperator(mOperater); //置上核保师工号
		    if (mSendReportNoticeFlag.equals("1")) 
		    {
		    	mLCUWMasterSchema.setReportFlag("1");
		    }
		    if (mSendPENoticeFlag.equals("1")) 
		    {
		    	mLCUWMasterSchema.setHealthFlag("1");
		    }
		    if (mUWSendFlag.equals("1")) 
		    {
		    	mLCUWMasterSchema.setPrintFlag("1");
		    }
		    if(mAddFeeFlag.equals("1"))
		    {
		    	mLCUWMasterSchema.setAddPremFlag("1");
		    }
		    if(mSpecFlag.equals("1"))
		    {
		    	mLCUWMasterSchema.setSpecFlag("1");
		    }
		    if(mQuestFlag.equals("1"))
		    {
		    	mLCUWMasterSchema.setQuesFlag("1");
		    }

		  // 每次进行核保相关操作时，向核保轨迹表插一条数据
			LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
			LCUWSubDB tLCUWSubDB = new LCUWSubDB();
			tLCUWSubDB.setContNo(mContNo);
			tLCUWSubDB.setPolNo(mLCUWMasterSchema.getPolNo());
			LCUWSubSet tLCUWSubSet = new LCUWSubSet();
			tLCUWSubSet = tLCUWSubDB.query();
			if (tLCUWSubDB.mErrors.needDealError()) {
				// @@错误处理
				CError.buildErr(this, "LPCUWSub表取数失败!");
				return false;
			}
	
			int m = tLCUWSubSet.size();
			logger.debug("subcount=" + m);
			if (m > 0) 
			{
				m++; // 核保次数		
				tLCUWSubSchema = new LCUWSubSchema();
				tLCUWSubSchema.setUWNo(m+mLCUWSubSet.size()); // 第几次核保
				tLCUWSubSchema.setGrpContNo(mLCUWMasterSchema.getGrpContNo());
				tLCUWSubSchema.setProposalNo(mLCUWMasterSchema.getProposalNo());
				tLCUWSubSchema.setContNo(mLCUWMasterSchema.getContNo());
				tLCUWSubSchema.setProposalContNo(mLCUWMasterSchema
						.getProposalContNo());
				tLCUWSubSchema.setPolNo(mLCUWMasterSchema.getPolNo());
				tLCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
				tLCUWSubSchema.setUWGrade(mLCUWMasterSchema.getUWGrade()); // 核保级别
				tLCUWSubSchema.setAppGrade(mLCUWMasterSchema.getAppGrade()); // 申请级别
				tLCUWSubSchema.setAutoUWFlag(mLCUWMasterSchema.getAutoUWFlag());
				tLCUWSubSchema.setPrintFlag("1");
				tLCUWSubSchema.setState(mUWFlag);
				tLCUWSubSchema.setOperator(mOperater); // 操作员
				tLCUWSubSchema.setManageCom(mLCUWMasterSchema.getManageCom());
				tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
				tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
				tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			} else 
			{
				// @@错误处理
				CError.buildErr(this, "LCUWSub表取数失败!");
				return false;
			}
			mLCUWSubSet.add(tLCUWSubSchema);
			mLCUWMasterSet.add(mLCUWMasterSchema);
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
		RnewIssuePolDB tRnewIssuePolDB = new RnewIssuePolDB();
		RnewIssuePolSet tRnewIssuePolSet = new RnewIssuePolSet();
		RnewIssuePolSet tRnewIssuePolSet2 = new RnewIssuePolSet();

		//mUWSendFlag = "0"; // 打印核保通知书标志
		mUWSendFlag_UnPrint = "0"; 
		mSendOperFlag = "0"; // 打印业务员通知书标志
		mQuestFlag = "0"; // 问题件标志
		mApproveModifyFlag = "0"; // 复核修改标志
		mSendPENoticeFlag = "0"; //体检通知书标志
		mSendReportNoticeFlag = "0";//生调通知书标志

		// tongmeng 2007-11-28 modify
		// 加费操作改为判断合同核保表
		// tongmeng 2008-08-13 modify
		// 支持对不同对象发送核保通知书
		//--投保人发生调、特约、承保计划变更
		//if (this.mCustomerNoType.endsWith("A")) {
			logger.debug("****************************准备投保人的核保通知书**********************************");
			
			// ln 2008-10-08 add
			tsql = "select * from RnewRReport where contno='"
				+ "?mContNo?" + "' and replyflag is null";	
			logger.debug("tSql==" + tsql);
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tsql);
			sqlbv10.put("mContNo", mContNo);
			RnewRReportDB tRnewRReportDB = new RnewRReportDB();
			RnewRReportSet tRnewRReportSet = new RnewRReportSet();
			tRnewRReportSet = tRnewRReportDB.executeQuery(sqlbv10);
			if(tRnewRReportSet.size()>0) {			    
				mSendReportNoticeFlag = "1";
				this.mRnewRReportSet_org.add(tRnewRReportSet);
			}	

//			 backobjtype = '2' 为发送给保户--投保人的核保通知书
			// 查询需要打印到保单的核保通知书
			tsql = "select * from Rnewissuepol where 1=1"
					+ " and Contno = '"
					+ "?mContNo?"
					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
					+ " and prtseq is null and questionobj='"
					+ "?questionobj?"
					+ "' and questionobjtype='0'";
			logger.debug("tSql==" + tsql);
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tsql);
			sqlbv11.put("mContNo", mContNo);
			sqlbv11.put("questionobj", this.mCustomerNo);
			tRnewIssuePolSet = tRnewIssuePolDB.executeQuery(sqlbv11);
			if (tRnewIssuePolSet.size() > 0) {
				mQuestFlag = "1";
				mUWSendFlag = "1";
				this.mRnewIssuePolSet_UWSend.add(tRnewIssuePolSet);
			}
            // 查询发送给机构的问题件
			tsql = "select * from Rnewissuepol where 1=1" 
			+ " and Contno = '"+ "?mContNo?"
			+ "' and backobjtype = '4' and replyresult is null"
		    + " and needprint = 'Y' ";
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tsql);
			sqlbv12.put("mContNo", mContNo);
			tRnewIssuePolSet2 = new RnewIssuePolSet();
			logger.debug("tSql==" + tsql);
			tRnewIssuePolSet2 = tRnewIssuePolDB.executeQuery(sqlbv12);
			if (tRnewIssuePolSet2.size() > 0) {
				mQuesOrgFlag = "1";
				this.mRnewIssuePolSet_QuesOrg.add(tRnewIssuePolSet2);
			}
	
			logger.debug("*********endend***************准备投保人的核保通知书结束*****************endendend*****************");
			
		//}
		//else
		//{
			//--被保人相关***************************************************************
			logger.debug("************************准备被保人的核保通知书**********************************");
			if (mAddFeeFlag.equals("1")
					|| mSpecFlag.equals("1")
					|| mChangePolFlag.equals("1")) {
				mUWSendFlag = "1";
			}
			
			// backobjtype = '2' 为发送给保户的核保通知书
			// 查询需要打印到保单的核保通知书
			tsql = "select * from Rnewissuepol where 1=1"
					+ " and Contno = '"
					+ "?mContNo?"
					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
					+ " and prtseq is null " 
					+ " and questionobj='"
					+ "?questionobj?"
					+ "' and questionobjtype<>'0'";
			logger.debug("tSql==" + tsql);
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tsql);
			sqlbv13.put("mContNo", mContNo);
			sqlbv13.put("questionobj", this.mCustomerNo);
			tRnewIssuePolSet = tRnewIssuePolDB.executeQuery(sqlbv13);
			if (tRnewIssuePolSet.size() > 0) {
				mQuestFlag = "1";
				mUWSendFlag = "1";
				this.mRnewIssuePolSet_UWSend.add(tRnewIssuePolSet);
			}
			logger.debug("*********endend***************准备被保人的核保通知书结束*****************endendend*****************");
	//	}
				
		logger.debug("************************准备客户的核保通知书--体检**********************************");
		// 查询待发体检 add ln 2008-10-29
//		tsql = "select * from RnewPENotice where 1=1 and Contno = '"
//				+ mContNo + "' and printflag is null and customerno='"+this.mCustomerNo+"' and customertype='"+this.mCustomerNoType+"'";
		tsql = "select * from RnewPENotice where 1=1 and Contno = '"
				+ "?mContNo?" + "' and printflag is null ";
		logger.debug("tSql==" + tsql);
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(tsql);
		sqlbv14.put("mContNo", mContNo);
		RnewPENoticeSet tRnewPENoticeSet = new RnewPENoticeSet();
		RnewPENoticeDB tRnewPENoticeDB = new RnewPENoticeDB();
		tRnewPENoticeSet = tRnewPENoticeDB.executeQuery(sqlbv14);
		if (tRnewPENoticeSet.size() > 0 ) {
			mSendPENoticeFlag = "1";
			mRnewPENoticeSet.add(tRnewPENoticeSet);
		}
		logger.debug("*********endend***************准备客户的核保通知书--体检结束*****************endendend*****************");			
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
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "prepareURGE";
			tError.errorMessage = "没有描述催发间隔!";
			this.mErrors.addOneError(tError);
			return false;
		}
		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);

		
		// ln 2008-10-07 add
		if (mSendReportNoticeFlag.equals("1")) {
			// 发送生调通知书	
			// 生调通知书处理			
			if(this.mRnewRReportSet_org.size()>0) {				
				this.mRnewRReportSet_org.get(1).setReplyFlag("0");//已发送
				this.mRnewRReportSet_org.get(1).setModifyDate(PubFun.getCurrentDate());
				this.mRnewRReportSet_org.get(1).setModifyTime(PubFun.getCurrentTime());
				this.mRnewRReportSet.add(mRnewRReportSet_org);
				
				// 准备打印管理表数据		
				mLOPRTManagerSchema = new LOPRTManagerSchema();
				mLOPRTManagerSchema.setPrtSeq(this.mRnewRReportSet_org.get(1).getPrtSeq());
				mLOPRTManagerSchema.setOtherNo(mContNo);
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PRnewMEET); 
				mLOPRTManagerSchema.setManageCom(tManageCom);
				mLOPRTManagerSchema.setAgentCode(mAgentCode);
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
				mLOPRTManagerSchema.setStandbyFlag2(mPrtNo);  
				mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
				mLOPRTManagerSchema.setOldPrtSeq(this.mRnewRReportSet_org.get(1).getPrtSeq());
				mLOPRTManagerSchema.setForMakeDate(tDate);
				
				mLOPRTManagerSet.add(mLOPRTManagerSchema);		
				
				mTransferData
				.setNameAndValue("PrtSeq", this.mRnewRReportSet_org.get(1).getPrtSeq());
				mTransferData
				.setNameAndValue("SaleChnl", mSaleChnl);
			}
			else
				mSendReportNoticeFlag = "0";
		}
		
		// ln 2008-10-29 add
		if (mSendPENoticeFlag.equals("1")) {
			// 发送体检通知书				
			for (int i = 1; i <= mRnewPENoticeSet.size(); i++) {
				//更新体检表
				mRnewPENoticeSet.get(i).setPrintFlag("0");//已发送
				mRnewPENoticeSet.get(i).setModifyDate(PubFun.getCurrentDate());
				mRnewPENoticeSet.get(i).setModifyTime(PubFun.getCurrentTime());
				
				// 准备打印管理表数据		
				mLOPRTManagerSchema = new LOPRTManagerSchema();
				mLOPRTManagerSchema.setPrtSeq(mRnewPENoticeSet.get(i).getPrtSeq());
				mLOPRTManagerSchema.setOtherNo(mContNo);
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PRnewPE); // 续保体检
				mLOPRTManagerSchema.setManageCom(tManageCom);
				mLOPRTManagerSchema.setAgentCode(mAgentCode);
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
				mLOPRTManagerSchema.setStandbyFlag1(mRnewPENoticeSet.get(i).getCustomerNo()); // 接收对象编码
				mLOPRTManagerSchema.setStandbyFlag2(mPrtNo);  
				mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
				mLOPRTManagerSchema.setOldPrtSeq(mRnewPENoticeSet.get(i).getPrtSeq());
				mLOPRTManagerSchema.setForMakeDate(tDate);
				
				mLOPRTManagerSet.add(mLOPRTManagerSchema);		
				
				mTransferData
				.setNameAndValue("PrtSeqPE", mRnewPENoticeSet.get(i).getPrtSeq());
				mTransferData
				.setNameAndValue("OldPrtSeq", mRnewPENoticeSet.get(i).getPrtSeq());
				mTransferData
				.setNameAndValue("SaleChnl", mSaleChnl);
			}			
		}
		if (mUWSendFlag.equals("1")) {
			// 发送核保通知书				
			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
			mPrtSeqUW = "";
			//mPrtSeqUW = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
			mPrtSeqUW = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_PRnewUW);
			// 准备打印管理表数据		
			mLOPRTManagerSchema = new LOPRTManagerSchema();
			mLOPRTManagerSchema.setPrtSeq(mPrtSeqUW);
			mLOPRTManagerSchema.setOtherNo(mContNo);
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mLOPRTManagerSchema.setCode("45"); // 续保核保通知书		
			mLOPRTManagerSchema.setManageCom(tManageCom);
			mLOPRTManagerSchema.setAgentCode(mAgentCode);
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
			mLOPRTManagerSchema.setStandbyFlag2(mPrtNo);  
			mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqUW);
			mLOPRTManagerSchema.setForMakeDate(tDate);
			
			mLOPRTManagerSet.add(mLOPRTManagerSchema);	
			
			for (int i = 1; i <= this.mRnewIssuePolSet_UWSend.size(); i++) {
				this.mRnewIssuePolSet_UWSend.get(i).setPrtSeq(mPrtSeqUW);
				this.mRnewIssuePolSet_UWSend.get(i).setState("0");
			}
			if (mRnewIssuePolSet_UWSend.size() > 0) {
				this.mRnewIssuePolSet.add(mRnewIssuePolSet_UWSend);
			}
			
            //tongmeng 2007-12-27 add
			// 特约处理 modify ln 2008-12-3 对合同级特约不置打印流水号，要打在每个被保人核保通知书上
			String tSQL_spec = "select * from lccspec where proposalcontno="
				+ "(select proposalcontno from lccont where contno='" + "?mContNo?" + "' and  needprint='Y' "
				//+ " and (customerno='"+this.mCustomerNo+"' and '"+this.mCustomerNoType+"' ='I' and prtflag is null)";
				+ " and prtflag is null)";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(tSQL_spec);
			sqlbv15.put("mContNo", mContNo);
			LCCSpecDB tLCCSpecDB = new LCCSpecDB();
			LCCSpecSet tLCCSpecSet = new LCCSpecSet();
			tLCCSpecSet = tLCCSpecDB.executeQuery(sqlbv15);
			for (int i = 1; i <= tLCCSpecSet.size(); i++) {
				LCCSpecSchema tempLCCSpecSchema = new LCCSpecSchema();
				tempLCCSpecSchema.setSchema(tLCCSpecSet.get(i));
				tempLCCSpecSchema.setPrtSeq(mPrtSeqUW);
				tempLCCSpecSchema.setPrtFlag("0");
				this.mLCCSpecSet.add(tempLCCSpecSchema);
			}	
			tSQL_spec = "select * from lccspec where proposalcontno="
				+ "(select proposalcontno from lccont where contno='" + "?mContNo?" + "') and  needprint='Y' "
				+ " and customerno is null";
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tSQL_spec);
			sqlbv16.put("mContNo", mContNo);
			tLCCSpecDB = new LCCSpecDB();
			tLCCSpecSet = new LCCSpecSet();
			tLCCSpecSet = tLCCSpecDB.executeQuery(sqlbv16);
			for (int i = 1; i <= tLCCSpecSet.size(); i++) {
				LCCSpecSchema tempLCCSpecSchema = new LCCSpecSchema();
				tempLCCSpecSchema.setSchema(tLCCSpecSet.get(i));
				tempLCCSpecSchema.setPrtFlag("0");
				this.mLCCSpecSet.add(tempLCCSpecSchema);
			}
		}
       // 判断机构问题件
		if (this.mQuesOrgFlag.equals("1")) {
			//String tLimit = PubFun.getNoLimit(mManageCom);
			mPrtSeqCom = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_PRnewOrg);
			for (int i = 1; i <= this.mRnewIssuePolSet_QuesOrg.size(); i++) {
				this.mRnewIssuePolSet_QuesOrg.get(i).setState("0");
				this.mRnewIssuePolSet_QuesOrg.get(i).setPrtSeq(mPrtSeqCom);

			}
			if (mRnewIssuePolSet_QuesOrg.size() > 0) {
				this.mRnewIssuePolSet.add(mRnewIssuePolSet_QuesOrg);
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
		}
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSet tLAAgentSet = new LAAgentSet();
		tLAAgentDB.setAgentCode(mAgentCode);
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
				.setNameAndValue("AgentCode", mAgentCode);
		mTransferData.setNameAndValue("AgentGroup", tLAAgentSet.get(1)
				.getAgentGroup());
		mTransferData.setNameAndValue("BranchAttr", tLABranchGroupSet.get(1)
				.getBranchAttr());
		mTransferData
				.setNameAndValue("ManageCom", tManageCom);
		mTransferData.setNameAndValue("ContNo", mContNo);
		mTransferData.setNameAndValue("PrtNo", mPrtNo);
		mTransferData.setNameAndValue("PrtSeqUW", mPrtSeqUW);
		mTransferData.setNameAndValue("PrtSeqUW_UnPrint", mPrtSeqUW_UnPrint);
		mTransferData.setNameAndValue("PrtSeqOper", mPrtSeqOper);
		mTransferData.setNameAndValue("PrtSeqCom", mPrtSeqCom);
		mTransferData.setNameAndValue("SendReportNoticeFlag", mSendReportNoticeFlag);
		mTransferData.setNameAndValue("SendPENoticeFlag", mSendPENoticeFlag);//2008-10-29 add ln 是否有带发放体检通知书标志
		mTransferData
				.setNameAndValue("AppntName", mAppntName);
//		mTransferData.setNameAndValue("ApproveDate", mLPContSchema
//				.getApproveDate());
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

		//map.put(mLPContSchema, "UPDATE");
		map.put(mLCPolSet, "UPDATE");
		map.put(mLCUWMasterSchema, "UPDATE");
		map.put(mRnewIssuePolSet, "UPDATE");
		map.put(mRnewIndUWMasterSchema, "UPDATE");
		map.put(mRnewIndUWSubSet, "INSERT");//新增被保人核保记录 2008-12-5 add ln
		map.put(mLCUWMasterSet, "UPDATE");
		map.put(mLCUWSubSet, "INSERT");
		map.put(mLOPRTManagerSet, "INSERT");
		map.put(this.mLCCSpecSet, "UPDATE");
		map.put(this.mRnewRReportSet, "UPDATE");
		map.put(mRnewPENoticeSet, "UPDATE");//修改体检录入表 2008-10-29 add ln
		map.put(mLWMissionSet, "UPDATE");
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
