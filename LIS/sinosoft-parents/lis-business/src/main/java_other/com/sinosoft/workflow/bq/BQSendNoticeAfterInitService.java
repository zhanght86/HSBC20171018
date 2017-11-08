package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIndUWMasterDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LPCSpecDB;
import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPCUWSubDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPIndUWMasterDB;
import com.sinosoft.lis.db.LPIndUWSubDB;
import com.sinosoft.lis.db.LPIssuePolDB;
import com.sinosoft.lis.db.LPPENoticeDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPRReportDB;
import com.sinosoft.lis.db.LPUWMasterDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPCUWSubSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPIndUWMasterSchema;
import com.sinosoft.lis.schema.LPIndUWSubSchema;
import com.sinosoft.lis.schema.LPIssuePolSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPUWMasterSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCIndUWMasterSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPCUWSubSet;
import com.sinosoft.lis.vschema.LPIndUWMasterSet;
import com.sinosoft.lis.vschema.LPIndUWSubSet;
import com.sinosoft.lis.vschema.LPIssuePolSet;
import com.sinosoft.lis.vschema.LPPENoticeSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPRReportSet;
import com.sinosoft.lis.vschema.LPUWMasterSet;
import com.sinosoft.lis.vschema.LPUWSubSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class BQSendNoticeAfterInitService implements AfterInitService{
private static Logger logger = Logger.getLogger(BQSendNoticeAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 核保主表 */
	private LPCUWMasterSchema mLPCUWMasterSchema = new LPCUWMasterSchema();
	private LPIndUWMasterSchema mLPIndUWMasterSchema = new LPIndUWMasterSchema();
	private LPIndUWMasterSet mLPIndUWMasterSet = new LPIndUWMasterSet();
	private LPUWMasterSet mLPUWMasterSet = new LPUWMasterSet();

	/** 核保子表 */
	private LPUWSubSet mLPUWSubSet = new LPUWSubSet();
	private LPIndUWSubSet mLPIndUWSubSet = new LPIndUWSubSet();
	private LPCUWSubSet mLPCUWSubSet = new LPCUWSubSet();

	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LPIssuePolSet mLPIssuePolSet = new LPIssuePolSet();
	private LPCSpecSet mLPCSpecSet = new LPCSpecSet();// 特约通知书
	private LPRReportSet mLPRReportSet_org = new LPRReportSet();// 生调通知书
	private LPRReportSet mLPRReportSet = new LPRReportSet();// 生调通知书
	private LPPENoticeSet mLPPENoticeSet = new LPPENoticeSet();// 体检通知书

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
	private String mEdorNo;
	private String mEdorType;
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
	private String mEdorAcceptNo = "";// 保全受理号
	/** 保单表 */
	private LPContSchema mLPContSchema = new LPContSchema();
	private LPPolSet mLPPolSet = new LPPolSet();
	private String mUWFlag = ""; // 核保标志
	private String mAddFeeFlag = "0";//是否存在加费
	private String mSpecFlag = "0";//是否存在特约
	private String mChangePolFlag = "0";//是否存在承保计划变更

	/** 工作流扭转标志 */
	private String mUWSendFlag = "";// 发送打印核保通知书标志
	private String mUWSendFlag_UnPrint = "";// 发送不打印核保通知书标志
	//private String mSendOperFlag = "";// 发送业务员通知书标志
	private String mQuesOrgFlag = "";// 发送机构问题件标志
	private String mApproveModifyFlag = "";// 发送问题件修改岗标志
	private String mSendReportNoticeFlag = "0";//发送生调通知书标志
	private String mSendPENoticeFlag = "0";//是否存在体检录入的标志，0为不存在不需要发放 否则为1
	private String testAflag = "";//是否执行本类判断条件
	private LPIssuePolSet mLPIssuePolSet_UWSend = new LPIssuePolSet();// 核保通知书问题件集合
	private LPIssuePolSet mLPIssuePolSet_UWSend_UnPrint = new LPIssuePolSet();// 不打印核保通知书问题件集合
	//private LPIssuePolSet mLPIssuePolSet_SendOper = new LPIssuePolSet();// 业务员通知书问题件集合

	private LPIssuePolSet mLPIssuePolSet_QuesOrg = new LPIssuePolSet();// 机构问题件集合
	//private LPIssuePolSet mLPIssuePolSet_ApproveModify = new LPIssuePolSet();// 问题件修改岗问题件集合
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();
	private LBMissionSchema mLBMissionSchema = new LBMissionSchema();
	
	private MMap map = new MMap();

	public BQSendNoticeAfterInitService() {
		
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
		if(testAflag=="1"){
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
		// 1 没有未回复的问题件则不能发核保通知书
		strSql = "select count(1) from LPIssuePol where 1=1"
			    + " and EdorNo = '"+ "?mEdorNo?" +"' "
				+ " and Contno = '"+ "?mContNo?" + "' "
				+ " and (state is null or state='') and needprint = 'Y'"
				+ " and ("
				// 判断有没有发放给保户的问题件
				+ " (backobjtype = '2' and (replyresult is null or replyresult='') and (prtseq is null or prtseq='') ) "
				// 判断有没有发放给机构的问题件
				+ " or (backobjtype = '4' and (replyresult is null or replyresult='') )"
				+ ") ";
		logger.debug("判断是否有需要下发的问题件 strSql:" + strSql);
		tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSql);
		sqlbv1.put("mEdorNo",mEdorNo);
		sqlbv1.put("mContNo",mContNo);
		int rs = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
		if (rs > 0) {
			flag = 1;
		}
		
		//-------------------------------------------------------------------------		
		logger.debug("判断是否有需要放送的核保通知书");
		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();
		tLPCUWMasterDB.setContNo(mContNo);
		tLPCUWMasterDB.setEdorNo(mEdorNo);
		tLPCUWMasterDB.setEdorType(mEdorType);
		if (!tLPCUWMasterDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "查询核保主表失败!");
			return false;

		}
		tLPCUWMasterSchema = tLPCUWMasterDB.getSchema();
		// 没有承保计划变更，不能发核保通知书
		if (tLPCUWMasterSchema.getChangePolFlag() != null
				&& tLPCUWMasterSchema.getChangePolFlag().length() > 0
				&& tLPCUWMasterSchema.getChangePolFlag().equals("1")) {
			flag = 1;
		}
		// 没有加费，不能发核保通知书
		if (tLPCUWMasterSchema.getAddPremFlag() != null
				&& tLPCUWMasterSchema.getAddPremFlag().length() > 0
				&& tLPCUWMasterSchema.getAddPremFlag().equals("1")) {
			flag = 1;
		}
		
		String addSql = "select count(1) from lpprem where contno='"
				+ "?mContNo?" + "' and payplancode like '000000%%'";
		ExeSQL addExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(addSql);
		sqlbv2.put("mContNo",mContNo);
		int rs_addfee = Integer.parseInt(addExeSQL.getOneValue(sqlbv2));	
		LPCUWMasterDB ttLPCUWMasterDB = new LPCUWMasterDB();
		LPCUWMasterSchema ttLPCUWMasterSchema = new LPCUWMasterSchema();
		ttLPCUWMasterDB.setContNo(mContNo);
		ttLPCUWMasterDB.setEdorNo(mEdorNo);
		ttLPCUWMasterDB.setEdorType(mEdorType);
		if (!ttLPCUWMasterDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "查询核保主表失败!");
			return false;

		}
		ttLPCUWMasterSchema = ttLPCUWMasterDB.getSchema();
		if (rs_addfee>0&&("".equals(ttLPCUWMasterSchema.getSugPassFlag())||ttLPCUWMasterSchema.getSugPassFlag()==null)){
			flag = 1;
		}

		// 没有特约，不能发核保通知书
		LPIndUWMasterDB tLPIndUWMasterDB = new LPIndUWMasterDB();
		tLPIndUWMasterDB.setContNo(mContNo);
		tLPIndUWMasterDB.setEdorNo(mEdorNo);
		tLPIndUWMasterDB.setEdorType(mEdorType);
		tLPIndUWMasterDB.setInsuredNo(this.mCustomerNo);
		LPIndUWMasterSet tLPIndUWMasterSet = tLPIndUWMasterDB.query();
		if (tLPIndUWMasterSet == null || tLPIndUWMasterSet.size() <= 0) {
			// @@错误处理
			CError.buildErr(this, "查询被保人核保主表失败!");
			return false;
		}
		for (int i = 1; i <= tLPIndUWMasterSet.size(); i++) {
			if (tLPIndUWMasterSet.get(i).getSpecFlag() != null
					&& tLPIndUWMasterSet.get(i).getSpecFlag().length() > 0
					&& tLPIndUWMasterSet.get(i).getSpecFlag().equals("1")) {
				flag = 1;
				break;
			}
		}
		//没有承保计划变更，不能发核保通知书 险种层判断
		LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
		tLPUWMasterDB.setContNo(mContNo);
		tLPUWMasterDB.setEdorNo(mEdorNo);
		tLPUWMasterDB.setEdorType(mEdorType);
		LPUWMasterSet tLPUWMasterSet = tLPUWMasterDB.query();
		if (tLPUWMasterSet == null || tLPUWMasterSet.size() <= 0) {
			// @@错误处理
			CError.buildErr(this, "查询险种核保主表失败!");
			return false;
		}
		for (int i = 1; i <= tLPUWMasterSet.size(); i++) {
			if (tLPUWMasterSet.get(i).getChangePolFlag() != null
					&& tLPUWMasterSet.get(i).getChangePolFlag().length() > 0
					&& tLPUWMasterSet.get(i).getChangePolFlag().equals("1")) {
				
				flag = 1;
				mChangePolFlag = "1";
				break;
			}
		}
	
		// 没有承保计划变更，不能发核保通知书
//		strSql = "select count(1) from LCUWMaster where contno='"
//			+ mContNo + "' and ChangePolFlag='1' "
//			+ " and exists(select 1 from lcpol a where a.polno=LCUWMaster.polno "
//		    + " and insuredno='"+this.mCustomerNo+"' and '"+this.mCustomerNoType+"' ='I')";
//		tExeSQL = new ExeSQL();
//		int rs_change = Integer.parseInt(tExeSQL.getOneValue(strSql));
//		if (rs_change > 0) {		    
//			flag = 1;
//			mChangePolFlag = "1";
//		}	

		// 没有加费，不能发核保通知书
//		strSql = "select count(1) from lcprem where contno='"
//			+ mContNo + "' and payplancode like '000000%%' "
//			+ " and exists(select 1 from lcpol a where a.polno=lcprem.polno "
//		    + " and insuredno='"+this.mCustomerNo+"' and '"+this.mCustomerNoType+"' ='I')";
//		tExeSQL = new ExeSQL();
//		int rs_addfee = Integer.parseInt(tExeSQL.getOneValue(strSql));
//		if (rs_addfee > 0) {		    
//			flag = 1;
//			mAddFeeFlag = "1";
//		}	
		// 没有特约，不能发核保通知书
//		strSql = "select count(1) from lccspec where proposalcontno="
//			+ "(select proposalcontno from lccont where contno='" + mContNo + "') and  needprint='Y'"
//			+ " and (customerno is null or (customerno='"+this.mCustomerNo+"' and '"+this.mCustomerNoType+"' ='I' and prtflag is null))";
//		tExeSQL = new ExeSQL();
//		int rs_spec = Integer.parseInt(tExeSQL.getOneValue(strSql));
//		if (rs_spec > 0) {		    
//			flag = 1;
//			mSpecFlag = "1";
//		}
		// tongmeng 2008-08-12 add
		// 增加对体检和生调的发放校验
		//add 2008-10-06 生调的发放校验
		String tSQL_exist = "select count(1) from LPRReport where contno='"
			+ "?mContNo?" + "' and EdorNo = '"+ "?mEdorNo?" +"' and (replyflag is null or replyflag='')";			
		logger.debug("判断是否有需要放送的生调通知书 tSQL_exist:" + tSQL_exist);
		tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL_exist);
		sqlbv3.put("mContNo",mContNo);
		sqlbv3.put("mEdorNo",mEdorNo);
		int rs_exist = Integer.parseInt(tExeSQL.getOneValue(sqlbv3));
		if (rs_exist > 0) {		    
			flag = 1;
		}	
		
		//体检的发放校验 add 2008-10-29
		strSql = "select count(1) from LPPENotice where contno='"
			+ "?mContNo?" + "' and EdorNo = '"+ "?mEdorNo?" +"' and (printflag is null or printflag='') and customerno='"+"?customerno?"+"' ";			
		logger.debug("判断是否有需要放送的体检通知书 strSql:" + strSql);
		tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(strSql);
		sqlbv4.put("mContNo",mContNo);
		sqlbv4.put("mEdorNo",mEdorNo);
		sqlbv4.put("customerno",this.mCustomerNo);
		int rs_health = Integer.parseInt(tExeSQL.getOneValue(sqlbv4));
		if (rs_health > 0) {
			flag = 1;
		}
		
		//
		if (flag == 0) {
			// @@错误处理
			CError.buildErr(this, "没有需要下发的问题件、体检、生调、核保通知书，不能下发通知书!");
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
		
		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		if (mEdorNo == null) {
			CError.buildErr(this, "前台传输业务数据中EDorNo失败!");
			return false;
		}
		
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType == null) {
			CError.buildErr(this, "前台传输业务数据中EDorType失败!");
			return false;
		}

		// 获得业务核保通知数据

		testAflag = (String)mTransferData.getValueByName("testAflag");
		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}
		
		// 获得当前工作任务的任务ID
		//mSubMissionID = (String) mTransferData.getValueByName("mSubMissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		logger.debug(mSubMissionID);
		noticeSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		logger.debug(noticeSubMissionID+"--=--");
		if (mSubMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!");
			return false;
		}
		// tongmeng 2008-08-12 add
		// 先查询核保通知书发放对象
//		String tSQL = "select missionprop15,missionprop16 from lwmission where missionid='"
//				+ this.mMissionID + "' " + " and activityid='" + cOperate + "' and submissionid='"+ this.noticeSubMissionID +"'";
		String tSQL =  " select max(insuredno),'I' from lpcont a where edorno='"+"?mEdorNo?"+"' and contno='"+"?mContNo?"+"'";
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSQL);
		sqlbv5.put("mEdorNo",mEdorNo);
		sqlbv5.put("mContNo",mContNo);
		tSSRS = (new ExeSQL()).execSQL(sqlbv5);
		if (tSSRS.getMaxRow() <= 0) {
			CError.buildErr(this, "查找发放对象出错!");
			return false;
		}
		this.mCustomerNo = tSSRS.GetText(1, 1);
		this.mCustomerNoType = tSSRS.GetText(1, 2);
		
		//查找人工核保工作流保全受理号 2009-05-15 ln add
		tSQL = "select max(Edoracceptno)  from lpedormain where edorno='"
				+ "?edorno?" + "' " + " and contno='"+"?mContNo?"+"' ";
		tSSRS = new SSRS();
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSQL);
		sqlbv6.put("edorno",this.mEdorNo);
		sqlbv6.put("mContNo",mContNo);
		tSSRS = (new ExeSQL()).execSQL(sqlbv6);
		if (tSSRS.getMaxRow() <= 0) {
			CError.buildErr(this, "查找保全人工核保工作流出错!");
			return false;
		}
		this.mEdorAcceptNo = tSSRS.GetText(1, 1);

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
//		if("I".equals(mCustomerNoType)){
//			if (prepareIndUW() == false)
//				return false;
//		}
		// 准备险种核保表信息
		//if (preparePolUW() == false)
		//	return false;
		// 选择工作流流转活动
		if (chooseActivity() == false)
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
        //工作流改造注释，以前下发通知书后工作还停留在核保共享池，当前已修改成不在核保工作池中
	    //所有通知书回复后聚合到核保共享池
//		if(!dealMission()){
//			return false;
//		}
		
		return true;
	}
	/** 
	 * 将当前核保状态改为2——核保未回复状态
	 * */
	private boolean dealMission(){
		//
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(mMissionID);
			//tLWMissionDB.setSubMissionID(mSubMissionID);
		String sql="select * from lwmission where MissionID='"+"?mMissionID?"+"' and  activityid in(select activityid from lwactivity where functionid='10020004')";
//		tLWMissionDB.setActivityID("0000000005");
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(sql);
		sqlbv7.put("mMissionID",mMissionID);
		tLWMissionSet=tLWMissionDB.executeQuery(sqlbv7);
		//if(tLWMissionSet.size()!=1){
//		if(!tLWMissionDB.getInfo()){
//			CError.buildErr(this, "查询LWMission失败！");
//			return false;
//		}
		mLWMissionSchema = tLWMissionSet.get(1);
		//备份
		String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
		Reflections mReflections = new Reflections();
		mReflections.transFields(mLBMissionSchema,
				mLWMissionSchema);
		mLBMissionSchema.setSerialNo(tSerielNo);
		mLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
		mLBMissionSchema.setLastOperator(mOperater);
		mLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
		mLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
		
		mLWMissionSchema.setActivityStatus("2");
		mLWMissionSchema.setMissionProp18("3");//核保状态 --核保未回复
			//mLWMissionSchema.setMissionProp9(PubFun.getCurrentDate());//最后回复日期
			//mLWMissionSchema.setMissionProp10(PubFun.getCurrentTime());
		mLWMissionSchema.setDefaultOperator("");//发完核保通知书返回共享池中并置为2状态
		mLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		mLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
		return true;

	}
	
	/**
	 * prepareIssue 准备LPIssuePol表数据，生成prtseq
	 * 
	 * @return boolean
	 */
	private boolean prepareIssue() {
		LPIssuePolSet tLPIssuePolSet = new LPIssuePolSet();
		LPIssuePolDB tLPIssuePolDB = new LPIssuePolDB();
		LPIssuePolSchema tLPIssuePolSchema = new LPIssuePolSchema();
		tLPIssuePolDB.setContNo(mContNo);
		tLPIssuePolSet = tLPIssuePolDB.query();

		for (int i = 1; i <= tLPIssuePolSet.size(); i++) {
			tLPIssuePolSchema = new LPIssuePolSchema();
			tLPIssuePolSchema = tLPIssuePolSet.get(i);
			if (tLPIssuePolSchema.getPrtSeq() == ""
					|| tLPIssuePolSchema.getPrtSeq() == null) {
				if (tLPIssuePolSchema.getBackObjType().equals("2"))
					tLPIssuePolSchema.setPrtSeq(mPrtSeqOper);
				else
					tLPIssuePolSchema.setPrtSeq(mPrtSeqUW);
			}
			mLPIssuePolSet.add(tLPIssuePolSchema);
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
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mContNo);
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setEdorType(mEdorType);
		
		if (!tLPContDB.getInfo()) {
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mContNo);
			if(!tLCContDB.getInfo()){
				CError.buildErr(this, "合同" + mContNo + "信息查询失败!");
				return false;
			}
			mAgentCode = tLCContDB.getAgentCode();
			tManageCom = tLCContDB.getManageCom();
			mPrtNo = tLCContDB.getPrtNo();
			mSaleChnl = tLCContDB.getSaleChnl();
			mAppntName = tLCContDB.getAppntName();
		}else{
			mLPContSchema.setSchema(tLPContDB);
			mAgentCode = mLPContSchema.getAgentCode();
			tManageCom = mLPContSchema.getManageCom();
			mPrtNo = mLPContSchema.getPrtNo();
			mSaleChnl = mLPContSchema.getSaleChnl();
			mAppntName = mLPContSchema.getAppntName();
		}
		mUWFlag = "8"; // 发核保通知书标志
		// 准备保单的复核标志
		//mLPContSchema.setUWFlag(mUWFlag);
		// 准备险种合同表数据
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setContNo(mContNo);
		tLPPolDB.setEdorNo(mEdorNo);
		tLPPolDB.setEdorType(mEdorType);
		mLPPolSet = tLPPolDB.query();

		// 准备险种保单的复核标志
		for (int i = 1; i < mLPPolSet.size(); i++) {
			mLPPolSet.get(i).setUWFlag(mUWFlag);
		}
		return true;
	}

	/**
	 * preparePrt 准备核保主表数据
	 * 
	 * @return boolean
	 */
	private boolean prepareContUW() {
		mLPCUWSubSet.clear();

		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setContNo(mContNo);
		tLPCUWMasterDB.setEdorNo(mEdorNo);
		tLPCUWMasterDB.setEdorType(mEdorType);
		if (!tLPCUWMasterDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "LPCUWMaster表取数失败!");
			return false;
		}
		mLPCUWMasterSchema.setSchema(tLPCUWMasterDB);
		mLPCUWMasterSchema.setPassFlag(mUWFlag);

		// 每次进行核保相关操作时，向核保轨迹表插一条数据
		LPCUWSubSchema tLPCUWSubSchema = new LPCUWSubSchema();
		LPCUWSubDB tLPCUWSubDB = new LPCUWSubDB();
		tLPCUWSubDB.setContNo(mContNo);
		tLPCUWSubDB.setEdorNo(mEdorNo);
		tLPCUWSubDB.setEdorType(mEdorType);
		LPCUWSubSet tLPCUWSubSet = new LPCUWSubSet();
		tLPCUWSubSet = tLPCUWSubDB.query();
		if (tLPCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			CError.buildErr(this, "LPCUWSub表取数失败!");
			return false;
		}

		int m = tLPCUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLPCUWSubSchema = new LPCUWSubSchema();
			tLPCUWSubSchema.setUWNo(m+mLPCUWSubSet.size()); // 第几次核保
			tLPCUWSubSchema.setContNo(mContNo);
			tLPCUWSubSchema.setGrpContNo(mLPCUWMasterSchema.getGrpContNo());
			tLPCUWSubSchema.setProposalContNo(mLPCUWMasterSchema
					.getProposalContNo());
			tLPCUWSubSchema.setEdorNo(mEdorNo);
			tLPCUWSubSchema.setEdorType(mEdorType);
			tLPCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
			tLPCUWSubSchema.setPrintFlag("1");
			tLPCUWSubSchema.setAutoUWFlag(mLPCUWMasterSchema.getAutoUWFlag());
			tLPCUWSubSchema.setState(mLPCUWMasterSchema.getState());
			tLPCUWSubSchema.setOperator(mLPCUWMasterSchema.getOperator()); // 操作员
			tLPCUWSubSchema.setManageCom(mLPCUWMasterSchema.getManageCom());
			tLPCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLPCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLPCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLPCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			CError.buildErr(this, "LCCUWSub表取数失败!");
			return false;
		}
		mLPCUWSubSet.add(tLPCUWSubSchema);
		return true;
	}
	
	/**
	 * preparePrt 准备被保人核保主表数据
	 * 
	 * @return boolean
	 */
	private boolean prepareIndUW() {
		mLPIndUWSubSet.clear();

		LPIndUWMasterDB tLPIndUWMasterDB = new LPIndUWMasterDB();
		tLPIndUWMasterDB.setContNo(mContNo);
		tLPIndUWMasterDB.setInsuredNo(mCustomerNo);
		if (!tLPIndUWMasterDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "LCIndUWMaster表取数失败!");
			return false;
		}
		mLPIndUWMasterSchema.setSchema(tLPIndUWMasterDB);
		mLPIndUWMasterSchema.setPassFlag(mUWFlag);

		// 每次进行核保相关操作时，向核保轨迹表插一条数据
		LPIndUWSubSchema tLPIndUWSubSchema = new LPIndUWSubSchema();
		LPIndUWSubDB tLPIndUWSubDB = new LPIndUWSubDB();
		tLPIndUWSubDB.setContNo(mContNo);
		tLPIndUWSubDB.setInsuredNo(mCustomerNo);
		LPIndUWSubSet tLPIndUWSubSet = new LPIndUWSubSet();
		tLPIndUWSubSet = tLPIndUWSubDB.query();
		if (tLPIndUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			CError.buildErr(this, "LCIndUWSub表取数失败!");
			return false;
		}

		int m = tLPIndUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLPIndUWSubSchema = new LPIndUWSubSchema();
			tLPIndUWSubSchema.setUWNo(m+tLPIndUWSubSet.size()); // 第几次核保
			tLPIndUWSubSchema.setContNo(mContNo);
			tLPIndUWSubSchema.setInsuredNo(mCustomerNo);
			tLPIndUWSubSchema.setGrpContNo(mLPIndUWMasterSchema.getGrpContNo());
			tLPIndUWSubSchema.setProposalContNo(mLPIndUWMasterSchema
					.getProposalContNo());
			tLPIndUWSubSchema.setPassFlag(mUWFlag); // 核保意见
			tLPIndUWSubSchema.setPrintFlag("1");
			tLPIndUWSubSchema.setAutoUWFlag(mLPIndUWMasterSchema.getAutoUWFlag());
			tLPIndUWSubSchema.setState(mLPIndUWMasterSchema.getState());
			tLPIndUWSubSchema.setOperator(mLPIndUWMasterSchema.getOperator()); // 操作员
			tLPIndUWSubSchema.setManageCom(mLPIndUWMasterSchema.getManageCom());
			tLPIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLPIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLPIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLPIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			CError.buildErr(this, "LCIndUWSub表取数失败!");
			return false;
		}
		mLPIndUWSubSet.add(tLPIndUWSubSchema);
		return true;
	}

	/**
	 * 准备险种核保表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePolUW() {
		mLPUWMasterSet.clear();
		mLPUWSubSet.clear();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();

		for (int i = 1; i <= mLPPolSet.size(); i++) {
			tLCUWMasterSchema = new LCUWMasterSchema();
			tLCUWMasterDB = new LCUWMasterDB();
			tLPPolSchema = mLPPolSet.get(i);
			tLCUWMasterDB.setProposalNo(tLPPolSchema.getProposalNo());

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

			mLPUWMasterSet.add(tLCUWMasterSchema);

			// 核保轨迹表
			tLCUWSubSchema = new LCUWSubSchema();
			tLCUWSubDB = new LCUWSubDB();
			tLCUWSubSet = new LCUWSubSet();
			///String tSql = "select uwno from lcuwsub where polno='"++"'";
			tLCUWSubDB.setPolNo(tLPPolSchema.getPolNo());
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
				tLCUWSubSchema.setUWNo(m+mLPUWSubSet.size()); // 第几次核保
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
			mLPUWSubSet.add(tLCUWSubSchema);
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
		LPIssuePolDB tLPIssuePolDB = new LPIssuePolDB();
		LPIssuePolSet tLPIssuePolSet = new LPIssuePolSet();

		mUWSendFlag = "0"; // 打印核保通知书标志
		mUWSendFlag_UnPrint = "0"; 
		//mSendOperFlag = "0"; // 打印业务员通知书标志
		mQuesOrgFlag = "0"; // 机构问题件标志
		//mApproveModifyFlag = "0"; // 复核修改标志
		mSendPENoticeFlag = "0"; //体检通知书标志
		mSendReportNoticeFlag = "0";//生调通知书标志

		// tongmeng 2007-11-28 modify
		// 加费操作改为判断合同核保表
		// tongmeng 2008-08-13 modify
		// 支持对不同对象发送核保通知书
		//--投保人发生调、特约、承保计划变更
//		if (this.mCustomerNoType.endsWith("A")) {
			logger.debug("****************************准备投保人的核保通知书**********************************");
			
//			for (int i = 1; i <= mLPUWMasterSet.size(); i++) {
//				/*
//				 * if (mLPUWMasterSet.get(i).getAddPremFlag() != null &&
//				 * mLPUWMasterSet.get(i).getAddPremFlag().equals("1"))
//				 * mUWSendFlag = "1";
//				 */
//				if (mLPUWMasterSet.get(i).getSpecFlag() != null
//						&& mLPUWMasterSet.get(i).getSpecFlag().equals("1"))
//					mUWSendFlag = "1";
//			}
//			if (mLPCUWMasterSchema.getChangePolFlag() != null
//					&& mLPCUWMasterSchema.getChangePolFlag().equals("1")) {
//				mUWSendFlag = "1";
//			}
			//核保通知书的校验 ,险种层面有次标准承保结论的需要发放核保通知书
//			String uwSql = "select count(1) from LPPol where contno='"
//				+ mContNo + "' and EdorNo = '"+ mEdorNo +"' and uwflag='4' ";
//			logger.debug("判断是否有需要放送的核保通知书 strSql:" + uwSql);
//			ExeSQL tExeSQL = new ExeSQL();
//			int rs_uwflag = Integer.parseInt(tExeSQL.getOneValue(uwSql));
//			if (rs_uwflag > 0) {
//				mUWSendFlag="1";
//			}
			//加费下发
			String addSql = "select count(1) from lpprem where contno='"
					+ "?mContNo?" + "' and payplancode like '000000%%'";
			ExeSQL addExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(addSql);
			sqlbv8.put("mContNo",mContNo);
			int rs_addfee = Integer.parseInt(addExeSQL.getOneValue(sqlbv8));	
			LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
			LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();
			tLPCUWMasterDB.setContNo(mContNo);
			tLPCUWMasterDB.setEdorNo(mEdorNo);
			tLPCUWMasterDB.setEdorType(mEdorType);
			if (!tLPCUWMasterDB.getInfo()) {
				// @@错误处理
				CError.buildErr(this, "查询核保主表失败!");
				return false;

			}
			tLPCUWMasterSchema = tLPCUWMasterDB.getSchema();
			if (rs_addfee>0&&("".equals(tLPCUWMasterSchema.getSugPassFlag())||tLPCUWMasterSchema.getSugPassFlag()==null)){
				mUWSendFlag="1";
				this.mLPCUWMasterSchema.setSugPassFlag("0");
			}
			//特约下发
			String speSql = "select count(1) from lpcspec where contno = '"+"?mContNo?"+"' and (prtflag is null or prtflag='') and needprint='Y'";
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(speSql);
			sqlbv9.put("mContNo",mContNo);
			int rs_speflag = Integer.parseInt(tExeSQL.getOneValue(sqlbv9));
			if (rs_speflag > 0) {
				mUWSendFlag="1";
			}
			
			// ln 2008-10-08 add
			tsql = "select * from LPRReport where edorno='"+ "?mEdorNo?" +"' and contno='"
				+ "?mContNo?" + "' and (replyflag is null or replyflag='')";	
			logger.debug("tSql==" + tsql);
			
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tsql);
			sqlbv10.put("mEdorNo",mEdorNo);
			sqlbv10.put("mContNo",mContNo);
			LPRReportDB tLPRReportDB = new LPRReportDB();
			LPRReportSet tLPRReportSet = new LPRReportSet();
			tLPRReportSet = tLPRReportDB.executeQuery(sqlbv10);
			if(tLPRReportSet.size()>0) {			    
				mSendReportNoticeFlag = "1";
				this.mLPRReportSet_org.add(tLPRReportSet);
			}	
			//end add ln 2008-10-08
			//add ln 2008-12-2
			// 查询发送给业务员通知书
//			tsql = "select * from LPIssuePol where 1=1" + " and Contno = '"
//					+ mContNo + "' and backobjtype = '3' and prtseq is null"
//					+ " and needprint = 'Y' ";
//			tLPIssuePolSet = new LPIssuePolSet();
//			logger.debug("tSql==" + tsql);
//			tLPIssuePolSet = tLPIssuePolDB.executeQuery(tsql);
//			if (tLPIssuePolSet.size() > 0) {
//				mSendOperFlag = "1";
//				this.mLPIssuePolSet_SendOper.add(tLPIssuePolSet);
//			} 
			// 查询发送给机构的问题件
			tsql = "select * from LPIssuePol where 1=1" 
			+ " and edorno='"+ "?mEdorNo?" +"' and Contno = '"+ "?mContNo?" 
			+ "' and backobjtype = '4' and (replyresult is null or replyresult='') and (prtseq is null or prtseq='')"
					+ " and needprint = 'Y' ";
			tLPIssuePolSet = new LPIssuePolSet();
			logger.debug("tSql==" + tsql);
			
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tsql);
			sqlbv11.put("mEdorNo",mEdorNo);
			sqlbv11.put("mContNo",mContNo);
			tLPIssuePolSet = tLPIssuePolDB.executeQuery(sqlbv11);
			if (tLPIssuePolSet.size() > 0) {
				mQuesOrgFlag = "1";
				this.mLPIssuePolSet_QuesOrg.add(tLPIssuePolSet);
			}
			
			// backobjtype = '2' 为发送给客户的核保通知书
			tsql = "select * from LPIssuePol where 1=1"
				    + " and edorno='"+ "?mEdorNo?" +"' and Contno = '"+ "?mContNo?"
					+ "' and backobjtype = '2' and (replyresult is null or replyresult='') and needprint = 'Y'"
					+ " and (prtseq is null or prtseq='')";
			logger.debug("tSql==" + tsql);
			
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tsql);
			sqlbv12.put("mEdorNo",mEdorNo);
			sqlbv12.put("mContNo",mContNo);
			tLPIssuePolSet = tLPIssuePolDB.executeQuery(sqlbv12);
			if (tLPIssuePolSet.size() > 0) {
				mUWSendFlag = "1";
				this.mLPIssuePolSet_UWSend.add(tLPIssuePolSet);
//				mQuesOrgFlag = "1";
//				this.mLPIssuePolSet_QuesOrg.add(tLPIssuePolSet);
				
			}
//			 查询不需要打印到保单的核保通知书
//			tsql = "select * from LPIssuePol where 1=1"
//					+ " and Contno = '"
//					+ mContNo
//					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
//					+ " and prtseq is null " + " and standbyflag2 = 'N' "
//					+ " and questionobj='"
//					+ this.mCustomerNo
//					+ "' and questionobjtype='0'";
//			logger.debug("tSql_unprint==" + tsql);
//			tLPIssuePolSet = new LPIssuePolSet();
//			tLPIssuePolSet = tLPIssuePolDB.executeQuery(tsql);
//			if (tLPIssuePolSet.size() > 0) {
//				mUWSendFlag_UnPrint = "1";
//				this.mLPIssuePolSet_UWSend_UnPrint.add(tLPIssuePolSet);
//			}
			
			// 查询是否有不需要创建问题件修改岗节点的因素存在
//			String ApproveModifyFlag = "0";
//			tsql = "select 1 from LPIssuePol where 1=1"
//					+ " and Contno = '"
//					+ mContNo
//					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
//					+ " and prtseq is null ";	       			        
//			logger.debug("tSql judge ApproveModifyFlag==" + tsql);
//			tLPIssuePolSet = new LPIssuePolSet();
//			tLPIssuePolSet = tLPIssuePolDB.executeQuery(tsql);
//			if (tLPIssuePolSet.size() > 0) {
//				ApproveModifyFlag = "1";
//			}
			// 查询发送给问题件修改岗的问题件
//			tsql = "select * from LPIssuePol where 1=1"
//					+ " and Contno = '"
//					+ mContNo
//					+ "' and backobjtype = '1' and state is null and replyresult is null "
//					+ " and needprint = 'Y' ";
//			logger.debug("tSql==" + tsql);
//			tLPIssuePolSet = new LPIssuePolSet();
//			tLPIssuePolSet = tLPIssuePolDB.executeQuery(tsql);
//			//if (tLPIssuePolSet.size() > 0 && mSendOperFlag.equals("0")
//			//		&& mUWSendFlag.equals("0") && mUWSendFlag_UnPrint.equals("0") && mQuesOrgFlag.equals("0")) {
//			if (tLPIssuePolSet.size() > 0 && mSendOperFlag.equals("0")
//							&& ApproveModifyFlag.equals("0") && mQuesOrgFlag.equals("0")) {
//				mApproveModifyFlag = "1";
//			}		
		 	
			logger.debug("*********endend***************准备投保人的核保通知书结束*****************endendend*****************");
			
//		}
//		else
//		{
//			//--被保人相关***************************************************************
//			logger.debug("************************准备被保人的核保通知书**********************************");
// 
////			}
//			logger.debug("*********endend***************准备被保人的核保通知书结束*****************endendend*****************");
//		}
				
		logger.debug("************************准备客户的核保通知书--体检**********************************");
		// 查询待发体检 add ln 2008-10-29
		tsql = "select * from LPPENotice where 1=1 and edorno='"+ "?mEdorNo?" +"' and Contno = '"
				+ "?mContNo?" + "' and (printflag is null or printflag='')";
		logger.debug("tSql==" + tsql);
		LPPENoticeSet tLPPENoticeSet = new LPPENoticeSet();
		LPPENoticeDB tLPPENoticeDB = new LPPENoticeDB();
		
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(tsql);
		sqlbv13.put("mEdorNo",mEdorNo);
		sqlbv13.put("mContNo",mContNo);
		tLPPENoticeSet = tLPPENoticeDB.executeQuery(sqlbv13);
		if (tLPPENoticeSet.size() > 0 ) {
			mSendPENoticeFlag = "1";
			mLPPENoticeSet.add(tLPPENoticeSet);
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
			mPrtSeqUW = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_PEdorSend);
			//logger.debug("---tLimit---" + tLimit);

			// 准备打印管理表数据
			mLOPRTManagerSchema = new LOPRTManagerSchema();
			mLOPRTManagerSchema.setPrtSeq(mPrtSeqUW);
			mLOPRTManagerSchema.setOtherNo(mContNo);
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorSend); // 核保(打印类)
			mLOPRTManagerSchema.setManageCom(mLPContSchema.getManageCom());
			mLOPRTManagerSchema.setAgentCode(mLPContSchema.getAgentCode());
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
//			mLOPRTManagerSchema.setStandbyFlag1(mLPContSchema.getAppntNo()); // 投保人编码
//			mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
//			mLOPRTManagerSchema.setStandbyFlag4(mEdorAcceptNo);
			mLOPRTManagerSchema.setStandbyFlag1(mEdorNo);
			mLOPRTManagerSchema.setStandbyFlag4(mEdorAcceptNo);
			mLOPRTManagerSchema.setStandbyFlag7(mEdorType);
			
			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqUW);
			mLOPRTManagerSchema.setForMakeDate(tDate);

			mLOPRTManagerSet.add(mLOPRTManagerSchema);
			for (int i = 1; i <= this.mLPIssuePolSet_UWSend.size(); i++) {
				this.mLPIssuePolSet_UWSend.get(i).setPrtSeq(mPrtSeqUW);
				this.mLPIssuePolSet_UWSend.get(i).setState("0");
			}
			if (mLPIssuePolSet_UWSend.size() > 0) {
				this.mLPIssuePolSet.add(mLPIssuePolSet_UWSend);
			}
			
			
			String tSQL_spec="select * from lpcspec where contno = '"+"?mContNo?"+"' and prtflag is null and needprint='Y'";
			LPCSpecDB tLPCSpecDB = new LPCSpecDB();
			LPCSpecSet tLPCSpecSet = new LPCSpecSet();
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(tSQL_spec);
			sqlbv14.put("mContNo",mContNo);
			tLPCSpecSet = tLPCSpecDB.executeQuery(sqlbv14);
			for (int i = 1; i <= tLPCSpecSet.size(); i++) {
				LPCSpecSchema tempLPCSpecSchema = new LPCSpecSchema();				
				tempLPCSpecSchema.setSchema(tLPCSpecSet.get(i));
				tempLPCSpecSchema.setPrtSeq(mPrtSeqUW);
				tempLPCSpecSchema.setPrtFlag("0");
				this.mLPCSpecSet.add(tempLPCSpecSchema);
			}	
			
			mTransferData.setNameAndValue("OldPrtSeq", mPrtSeqUW);
			mTransferData.removeByName("Code");// 前面置了一次空值，此处移除重新添加
			mTransferData.setNameAndValue("Code", PrintManagerBL.CODE_PEdorSend);

//			// tongmeng 2007-12-27 add
//			// 特约处理 modify ln 2008-12-3 对合同级特约不置打印流水号，要打在每个被保人核保通知书上
//			String tSQL_spec = "select * from lccspec where proposalcontno="
//				+ "(select proposalcontno from lccont where contno='" + mContNo + "') and  needprint='Y' "
//				+ " and (customerno='"+this.mCustomerNo+"' and '"+this.mCustomerNoType+"' ='I' and prtflag is null)";
//			LCCSpecDB tLCCSpecDB = new LCCSpecDB();
//			LCCSpecSet tLCCSpecSet = new LCCSpecSet();
//			tLCCSpecSet = tLCCSpecDB.executeQuery(tSQL_spec);
//			for (int i = 1; i <= tLCCSpecSet.size(); i++) {
//				LCCSpecSchema tempLCCSpecSchema = new LCCSpecSchema();
//				tempLCCSpecSchema.setSchema(tLCCSpecSet.get(i));
//				tempLCCSpecSchema.setPrtSeq(mPrtSeqUW);
//				tempLCCSpecSchema.setPrtFlag("0");
//				this.mLCCSpecSet.add(tempLCCSpecSchema);
//			}	
//			tSQL_spec = "select * from lccspec where proposalcontno="
//				+ "(select proposalcontno from lccont where contno='" + mContNo + "') and  needprint='Y' "
//				+ " and customerno is null";
//			tLCCSpecDB = new LCCSpecDB();
//			tLCCSpecSet = new LCCSpecSet();
//			tLCCSpecSet = tLCCSpecDB.executeQuery(tSQL_spec);
//			for (int i = 1; i <= tLCCSpecSet.size(); i++) {
//				LCCSpecSchema tempLCCSpecSchema = new LCCSpecSchema();
//				tempLCCSpecSchema.setSchema(tLCCSpecSet.get(i));
//				tempLCCSpecSchema.setPrtFlag("0");
//				this.mLCCSpecSet.add(tempLCCSpecSchema);
//			}
			
		}
//		if (mUWSendFlag_UnPrint.equals("1")) {
//			// 发送核保通知书
//			String tLimit = PubFun.getNoLimit(mManageCom);
//			mPrtSeqUW_UnPrint = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
//			logger.debug("---tLimit---" + tLimit);
//
//			// 准备打印管理表数据
//			mLOPRTManagerSchema = new LOPRTManagerSchema();
//			mLOPRTManagerSchema.setPrtSeq(mPrtSeqUW_UnPrint);
//			mLOPRTManagerSchema.setOtherNo(mContNo);
//			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
//			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_UW_UnPrint); //核保通知书(
//																			// 非打印类
//																			// )
//			mLOPRTManagerSchema.setManageCom(mLPContSchema.getManageCom());
//			mLOPRTManagerSchema.setAgentCode(mLPContSchema.getAgentCode());
//			mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
//			mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
//			// mLOPRTManagerSchema.setExeCom();
//			// mLOPRTManagerSchema.setExeOperator();
//			mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
//			mLOPRTManagerSchema.setStateFlag("0");
//			mLOPRTManagerSchema.setPatchFlag("0");
//			mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
//			mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
//			// mLOPRTManagerSchema.setDoneDate() ;
//			// mLOPRTManagerSchema.setDoneTime();
//			mLOPRTManagerSchema.setStandbyFlag1(mLPContSchema.getAppntNo()); // 投保人编码
//			mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
//			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqUW_UnPrint);
//			mLOPRTManagerSchema.setForMakeDate(tDate);
//
//			mLOPRTManagerSet.add(mLOPRTManagerSchema);
//			for (int i = 1; i <= this.mLPIssuePolSet_UWSend_UnPrint.size(); i++) {
//				this.mLPIssuePolSet_UWSend_UnPrint.get(i).setPrtSeq(
//						mPrtSeqUW_UnPrint);
//				this.mLPIssuePolSet_UWSend_UnPrint.get(i).setState("0");
//			}
//			if (mLPIssuePolSet_UWSend_UnPrint.size() > 0) {
//				this.mLPIssuePolSet.add(mLPIssuePolSet_UWSend_UnPrint);
//			}
//
//		}
		// 业务员问题件
//		if (mSendOperFlag.equals("1")) {
//			String tLimit = PubFun.getNoLimit(mManageCom);
//			mPrtSeqOper = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
//			mLOPRTManagerSchema = new LOPRTManagerSchema();
//			// 准备打印管理表数据
//			mLOPRTManagerSchema.setPrtSeq(mPrtSeqOper);
//			mLOPRTManagerSchema.setOtherNo(mContNo);
//			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
//			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_AGEN_QUEST); // 业务员通知书
//			mLOPRTManagerSchema.setManageCom(mLPContSchema.getManageCom());
//			mLOPRTManagerSchema.setAgentCode(mLPContSchema.getAgentCode());
//			mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
//			mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
//			// mLOPRTManagerSchema.setExeCom();
//			// mLOPRTManagerSchema.setExeOperator();
//			mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
//			mLOPRTManagerSchema.setStateFlag("0");
//			mLOPRTManagerSchema.setPatchFlag("0");
//			mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
//			mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
//			// mLOPRTManagerSchema.setDoneDate() ;
//			// mLOPRTManagerSchema.setDoneTime();
//			mLOPRTManagerSchema.setStandbyFlag1(mLPContSchema.getAppntNo()); // 投保人编码
//			mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
//			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqOper);
//
//			mLOPRTManagerSet.add(mLOPRTManagerSchema);
//			for (int i = 1; i <= this.mLPIssuePolSet_SendOper.size(); i++) {
//				this.mLPIssuePolSet_SendOper.get(i).setPrtSeq(mPrtSeqOper);
//				this.mLPIssuePolSet_SendOper.get(i).setState("0");
//			}
//			if (mLPIssuePolSet_SendOper.size() > 0) {
//				this.mLPIssuePolSet.add(mLPIssuePolSet_SendOper);
//			}
//		}
		// 判断机构问题件
		if (this.mQuesOrgFlag.equals("1")) {
			//String tLimit = PubFun.getNoLimit(mManageCom);
			mPrtSeqCom = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_PEdorOrg);
			for (int i = 1; i <= this.mLPIssuePolSet_QuesOrg.size(); i++) {
				this.mLPIssuePolSet_QuesOrg.get(i).setState("0");
				this.mLPIssuePolSet_QuesOrg.get(i).setPrtSeq(mPrtSeqCom);

			}
			if (mLPIssuePolSet_QuesOrg.size() > 0) {
				this.mLPIssuePolSet.add(mLPIssuePolSet_QuesOrg);
			}
		}
//		// 查询是否有问题件修改岗的问题件,如果有的话,将状态设置为0
//		String tsql = "select * from LPIssuePol where 1=1" + " and Contno = '"
//				+ mContNo + "' and backobjtype = '1' and state is null ";
//		LPIssuePolSet tempLPIssuePolSet = new LPIssuePolSet();
//		LPIssuePolDB tempLPIssuePolDB = new LPIssuePolDB();
//
//		tempLPIssuePolSet = tempLPIssuePolDB.executeQuery(tsql);
//		for (int i = 1; i <= tempLPIssuePolSet.size(); i++) {
//			tempLPIssuePolSet.get(i).setState("0");// 设置为已发放
//			tempLPIssuePolSet.get(i).setModifyDate(PubFun.getCurrentDate());
//			tempLPIssuePolSet.get(i).setModifyTime(PubFun.getCurrentTime());
//			this.mLPIssuePolSet.add(tempLPIssuePolSet.get(i));
//		}	
		
		// ln 2008-10-07 add
		if (mSendReportNoticeFlag.equals("1")) {
			// 发送生调通知书	
			// 生调通知书处理			
			if(this.mLPRReportSet_org.size()>0) {				
				this.mLPRReportSet_org.get(1).setReplyFlag("0");//已发送
				this.mLPRReportSet_org.get(1).setModifyDate(PubFun.getCurrentDate());
				this.mLPRReportSet_org.get(1).setModifyTime(PubFun.getCurrentTime());
				this.mLPRReportSet.add(mLPRReportSet_org);
				
				// 准备打印管理表数据		
				mLOPRTManagerSchema = new LOPRTManagerSchema();
				mLOPRTManagerSchema.setPrtSeq(this.mLPRReportSet_org.get(1).getPrtSeq());
				mLOPRTManagerSchema.setOtherNo(mContNo);
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorMEET); // 暂定BQ+PrintManagerBL.CODE_PE:BQ03为保全生调
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
				mLOPRTManagerSchema.setStandbyFlag4(mEdorAcceptNo);
				mLOPRTManagerSchema.setOldPrtSeq(this.mLPRReportSet_org.get(1).getPrtSeq());
				mLOPRTManagerSchema.setForMakeDate(tDate);
				
				mLOPRTManagerSet.add(mLOPRTManagerSchema);		
				
				mTransferData
				.setNameAndValue("PrtSeqRR", this.mLPRReportSet_org.get(1).getPrtSeq());
				mTransferData
				.setNameAndValue("OldPrtSeqRR", this.mLPRReportSet_org.get(1).getPrtSeq());
				mTransferData
				.setNameAndValue("SaleChnl", mSaleChnl);
			}
			else
				mSendReportNoticeFlag = "0";
		}
		
		// ln 2008-10-29 add
		if (mSendPENoticeFlag.equals("1")) {
			// 发送体检通知书				
			for (int i = 1; i <= mLPPENoticeSet.size(); i++) {
				//更新体检表
				mLPPENoticeSet.get(i).setPrintFlag("0");//已发送
				mLPPENoticeSet.get(i).setModifyDate(PubFun.getCurrentDate());
				mLPPENoticeSet.get(i).setModifyTime(PubFun.getCurrentTime());
				
				// 准备打印管理表数据		
				mLOPRTManagerSchema = new LOPRTManagerSchema();
				mLOPRTManagerSchema.setPrtSeq(mLPPENoticeSet.get(i).getPrtSeq());
				mLOPRTManagerSchema.setOtherNo(mContNo);
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorPE); // 保全体检
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
				mLOPRTManagerSchema.setStandbyFlag4(mEdorAcceptNo);
				mLOPRTManagerSchema.setOldPrtSeq(mLPPENoticeSet.get(i).getPrtSeq());
				mLOPRTManagerSchema.setForMakeDate(tDate);
				
				mLOPRTManagerSet.add(mLOPRTManagerSchema);		
				
				mTransferData
				.setNameAndValue("PrtSeqPE", mLPPENoticeSet.get(i).getPrtSeq());
				mTransferData
				.setNameAndValue("OldPrtSeqPE", mLPPENoticeSet.get(i).getPrtSeq());
				mTransferData
				.setNameAndValue("SaleChnl", mSaleChnl);
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
		//mTransferData.setNameAndValue("SendOperFlag", mSendOperFlag);

		mTransferData.setNameAndValue("UWSendFlag", mUWSendFlag); // 打印核保通知书标志
		mTransferData.setNameAndValue("UWSendFlag_UnPrint", mUWSendFlag_UnPrint); // 打印核保通知书非打印类标志
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
		mTransferData.setNameAndValue("PrtNo", mPrtNo);
		mTransferData.setNameAndValue("PrtSeq", mPrtSeqUW);
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
		mTransferData.setNameAndValue("CustomerNoType", this.mCustomerNoType);
		mTransferData.setNameAndValue("SpecFlag", this.mSpecFlag);//是否有特约标记
		mTransferData.setNameAndValue("ChangePolFlag", this.mChangePolFlag);
		mTransferData.setNameAndValue("AddFeeFlag", this.mAddFeeFlag);
		mTransferData.setNameAndValue("EdorNo", this.mEdorNo);
		mTransferData.setNameAndValue("EdorType", this.mEdorType);

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
		
		//map.put(mLPPolSet, "UPDATE");	
		map.put(mLPIndUWMasterSchema, "UPDATE");
		map.put(mLPIndUWSubSet, "INSERT");//新增被保人核保记录 2008-12-5 add ln
		map.put(mLPUWMasterSet, "UPDATE");
		map.put(mLPUWSubSet, "INSERT");
		map.put(mLPCUWMasterSchema, "UPDATE");
		map.put(mLPCUWSubSet, "INSERT");
		map.put(mLPIssuePolSet, "UPDATE");
		map.put(mLOPRTManagerSet, "INSERT");
		map.put(this.mLPCSpecSet, "UPDATE");
		map.put(this.mLPRReportSet, "UPDATE");
		map.put(mLPPENoticeSet, "UPDATE");//修改体检录入表 2008-10-29 add ln
//		map.put(mLBMissionSchema, "INSERT");
//		map.put(mLWMissionSchema, "UPDATE");
		
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



