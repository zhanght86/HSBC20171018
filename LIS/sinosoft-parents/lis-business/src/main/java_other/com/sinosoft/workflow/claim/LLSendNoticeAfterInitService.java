package com.sinosoft.workflow.claim;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LLCUWMasterDB;
import com.sinosoft.lis.db.LLCUWSubDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLIssuePolDB;
import com.sinosoft.lis.db.LLUWPENoticeDB;
import com.sinosoft.lis.db.LLUWSpecMasterDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.schema.LLCUWSubSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLUWSpecMasterSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPIndUWMasterSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LLCUWMasterSet;
import com.sinosoft.lis.vschema.LLCUWSubSet;
import com.sinosoft.lis.vschema.LLIssuePolSet;
import com.sinosoft.lis.vschema.LLUWMasterSet;
import com.sinosoft.lis.vschema.LLUWPENoticeSet;
import com.sinosoft.lis.vschema.LLUWSpecMasterSet;
import com.sinosoft.lis.vschema.LLUWSubSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPIndUWMasterSet;
import com.sinosoft.lis.vschema.LPIndUWSubSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class LLSendNoticeAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLSendNoticeAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 核保主表 */
	private LLCUWMasterSet mLLCUWMasterSet = new LLCUWMasterSet();
	private LPIndUWMasterSchema mLPIndUWMasterSchema = new LPIndUWMasterSchema();
	private LPIndUWMasterSet mLPIndUWMasterSet = new LPIndUWMasterSet();
	private LLUWMasterSet mLLUWMasterSet = new LLUWMasterSet();

	/** 核保子表 */
	private LLUWSubSet mLLLUWSubSet = new LLUWSubSet();
	private LPIndUWSubSet mLPIndUWSubSet = new LPIndUWSubSet();
	private LLCUWSubSet mLLCUWSubSet = new LLCUWSubSet();

	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LLIssuePolSet mLLIssuePolSet = new LLIssuePolSet();
	private LLUWSpecMasterSet mLLUWSpecMasterSet = new LLUWSpecMasterSet();// 特约通知书
	// private LLRReportSet mLPRReportSet_org = new LPRReportSet();// 生调通知书
	// private LLRReportSet mLPRReportSet = new LPRReportSet();// 生调通知书
	private LLUWPENoticeSet mLLUWPENoticeSet = new LLUWPENoticeSet();// 体检通知书

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	/** 0005节点的submissionid */
	private String mSubMissionID;//
	/** 0100节点的submissionid */
	private String noticeSubMissionID;
	private String mClmNo;
	private String mBatNo;
	private String mPrtSeq;
	private String mAgentCode;
	private String tManageCom;
	private String mPrtNo;
	private String mSaleChnl;
	private String mAppntName;
	private String mInsuredNo;
	private String mAppntNo;
	private String mPrtSeqUW = "0";
	private String mPrtSeqUW_UnPrint = "0";
	private String mPrtSeqOper = "0";
	private String mPrtSeqCom = "";
//	private String mCustomerNo = "";// 方法对象
//	private String mCustomerNoType = "";// 发放对象类型(A 投保人,业务员,机构问题件,操作员问题件),I(被保人)
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPPolSet mLPPolSet = new LPPolSet();
	private String mUWFlag = ""; // 核保标志
	private String mAddFeeFlag = "0";// 是否存在加费
	private String mSpecFlag = "0";// 是否存在特约
	private String mChangePolFlag = "0";// 是否存在承保计划变更

	/** 工作流扭转标志 */
	private String mUWSendFlag = "";// 发送打印核保通知书标志
	private String mUWSendFlag_UnPrint = "";// 发送不打印核保通知书标志
	private String mSendOperFlag = "";// 发送业务员通知书标志
	private String mQuesOrgFlag = "";// 发送机构问题件标志
	private String mApproveModifyFlag = "";// 发送问题件修改岗标志
	private String mSendReportNoticeFlag = "0";// 发送生调通知书标志
	private String mSendPENoticeFlag = "0";// 是否存在体检录入的标志，0为不存在不需要发放 否则为1
	private String mImpartQuestFlag = "0";// 健康告知问卷

	private LLIssuePolSet mLLIssuePolSet_UWSend = new LLIssuePolSet();// 核保通知书问题件集合
	private LLIssuePolSet mLLIssuePolSet_UWSend_UnPrint = new LLIssuePolSet();// 不打印核保通知书问题件集合
	private LLIssuePolSet mLLIssuePolSet_SendOper = new LLIssuePolSet();// 业务员通知书问题件集合

	private LLIssuePolSet mLLIssuePolSet_QuesOrg = new LLIssuePolSet();// 机构问题件集合
	private LLIssuePolSet mLLIssuePolSet_ApproveModify = new LLIssuePolSet();// 问题件修改岗问题件集合
	private LLClaimSchema mLLClaimSchema=new LLClaimSchema();
	private PubConcurrencyLock mLock = new PubConcurrencyLock();

	private MMap map = new MMap();

	public LLSendNoticeAfterInitService() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		try
		{
			if (!getInputData(cInputData, cOperate))
				return false;
	
			// 对是否能发核保通知书进行校验
			if (!checkData())
				return false;
	
			// 增加并发控制，zy
			String tOperatedNo = mClmNo;//进行发起二核的立案号并发控制
	//		tOperatedNo[1] = "0000005505";	
	
			//进行并发组的控制
			if(!mLock.lock(tOperatedNo, "LP0004", mGlobalInput.Operator))
			{
				CError tError = new CError(mLock.mErrors.getLastError());
				this.mErrors.addOneError(tError);
				return false;
		
			}
			// 进行业务处理
			if (!dealData())
				return false;
	
			// 准备往后台的数据
			if (!prepareOutputData())
				return false;
	
			if (!prepareTransferData())
				return false;
		}
		catch(Exception ex)
		{
			CError.buildErr(this, ex.toString());
			return false;
		}
		finally
		{
			mLock.unLock();
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
		mAddFeeFlag = "0";// 是否存在加费
		mSpecFlag = "0";// 是否存在特约
		mChangePolFlag = "0";// 是否存在特约
		String strSql = "";
		ExeSQL tExeSQL = new ExeSQL();
		// 1 没有未回复的问题件则不能发核保通知书
		// String strSql = "select count(1) from lcissuepol where 1=1"
		// + " and Contno = '"
		// + mContNo
		// + "' "
		// + " and state is null and needprint = 'Y'"
		// // 判断有没有发放给业务员问题件
		// +
		// " and ((backobjtype = '3' and replyresult is null and prtseq is null "
		// + " and '"
		// + this.mCustomerNoType
		// + "'='A' )"
		// // 判断有没有发放给保户的问题件
		// +
		// " or (backobjtype = '2' and replyresult is null and prtseq is null "
		// + " and ((questionobj='"
		// + this.mCustomerNo
		// + "' and questionobjtype<>'0' and '"
		// + this.mCustomerNoType
		// + "'='I') "
		// + " or (questionobj='"
		// + this.mCustomerNo
		// + "' and questionobjtype='0' and '"
		// + this.mCustomerNoType
		// + "'='A'))) "
		// // 判断有没有发放给问题件修改岗的问题件
		// + " or (backobjtype = '1' and replyresult is null and '"
		// + this.mCustomerNoType
		// + "'='A') "
		// // 判断有没有发放给机构的问题件
		// + " or (backobjtype = '4' and replyresult is null and '"
		// + this.mCustomerNoType + "'='A')) ";
		// logger.debug("判断是否有需要下发的问题件 strSql:" + strSql);
		// ExeSQL tExeSQL = new ExeSQL();
		// int rs = Integer.parseInt(tExeSQL.getOneValue(strSql));
		// if (rs > 0) {
		// flag = 1;
		// }

		// LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		// LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		// tLCCUWMasterDB.setContNo(mContNo);
		// if (!tLCCUWMasterDB.getInfo()) {
		// // @@错误处理
		// CError.buildErr(this, "查询核保主表失败!");
		// return false;
		//
		// }
		// tLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
		// 没有承保计划变更，不能发核保通知书
		// if (tLCCUWMasterSchema.getChangePolFlag() != null
		// && tLCCUWMasterSchema.getChangePolFlag().length() > 0
		// && tLCCUWMasterSchema.getChangePolFlag().equals("1")) {
		// flag = 1;
		// }

		// 没有特约，不能发核保通知书
		// LCIndUWMasterDB tLCIndUWMasterDB = new LCIndUWMasterDB();
		// tLCIndUWMasterDB.setContNo(mContNo);
		// tLCIndUWMasterDB.setInsuredNo(this.mCustomerNo);
		// LCIndUWMasterSet tLCIndUWMasterSet = tLCIndUWMasterDB.query();
		// if (tLCIndUWMasterSet == null || tLCIndUWMasterSet.size() <= 0) {
		// // @@错误处理
		// CError.buildErr(this, "查询被保人核保主表失败!");
		// return false;
		// }
		// for (int i = 1; i <= tLCIndUWMasterSet.size(); i++) {
		// if (tLCIndUWMasterSet.get(i).getSpecFlag() != null
		// && tLCIndUWMasterSet.get(i).getSpecFlag().length() > 0
		// && tLCIndUWMasterSet.get(i).getSpecFlag().equals("1")) {
		// flag = 1;
		// }
		// }
		// if (!tLCIndUWMasterDB.getInfo()) {
		// // @@错误处理
		// CError.buildErr(this, "查询被保人核保主表失败!");
		// return false;
		// }
		// LCIndUWMasterSchema tLCIndUWMasterSchema =
		// tLCIndUWMasterDB.getSchema();
		// if (tLCIndUWMasterSchema.getSpecFlag() != null
		// && tLCIndUWMasterSchema.getSpecFlag().length() > 0
		// && tLCIndUWMasterSchema.getSpecFlag().equals("1")) {
		// flag = 1;
		// }

		// 没有加费，不能发核保通知书
		// if (tLCCUWMasterSchema.getAddPremFlag() != null
		// && tLCCUWMasterSchema.getAddPremFlag().length() > 0
		// && tLCCUWMasterSchema.getAddPremFlag().equals("1")) {
		// flag = 1;
		// }

		// 没有承保计划变更，不能发核保通知书
		// LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		// tLCUWMasterDB.setContNo(mContNo);
		// LCUWMasterSet tLCUWMasterSet = tLCUWMasterDB.query();
		// if (tLCUWMasterSet == null || tLCUWMasterSet.size() <= 0) {
		// // @@错误处理
		// CError.buildErr(this, "查询险种核保主表失败!");
		// return false;
		// }
		// for (int i = 1; i <= tLCUWMasterSet.size(); i++) {
		// if (tLCUWMasterSet.get(i).getChangePolFlag() != null
		// && tLCUWMasterSet.get(i).getChangePolFlag().length() > 0
		// && tLCUWMasterSet.get(i).getChangePolFlag().equals("1")) {
		//				
		// flag = 1;
		// mChangePolFlag = "1";
		// break;
		// }
		// }
		// 没有承保计划变更，不能发核保通知书
		// strSql = "select count(1) from LCUWMaster where contno='"
		// + mContNo + "' and ChangePolFlag='1' "
		// + " and exists(select 1 from lcpol a where a.polno=LCUWMaster.polno "
		// + " and insuredno='"+this.mCustomerNo+"' and '"+this.mCustomerNoType+
		// "' ='I')";
		// tExeSQL = new ExeSQL();
		// int rs_change = Integer.parseInt(tExeSQL.getOneValue(strSql));
		// if (rs_change > 0) {
		// flag = 1;
		// mChangePolFlag = "1";
		// }

		// 没有加费，不能发核保通知书
		// strSql = "select count(1) from lluwpremmaster where contno='"
		// + mContNo +
		// "' and payplancode like '000000%%' and batno='"+mBatNo+"'"
		// +
		// " and exists(select 1 from lcpol a where a.polno=lluwpremmaster.polno "
		// + " and insuredno='"+tInsuredNo+"' "
		// // + " and '"+this.mCustomerNoType+"' ='I' "
		// +")";
		tExeSQL = new ExeSQL();
		// int rs_addfee = Integer.parseInt(tExeSQL.getOneValue(strSql));
		// if (rs_addfee > 0) {
		// flag = 1;
		// mAddFeeFlag = "1";
		// }
		// 没有特约，不能发核保通知书
		strSql = "select count(1) from LLUWSpecMaster where contno=" + "'"
				+ "?mContNo?" + "' and  needprint='Y' and batno='" + "?mBatNo?" + "'";
		// + " and (customerno is null or (customerno='"+this.mCustomerNo+"' "
		// +"and '"+this.mCustomerNoType+"' ='I' "
		// +"and prtflag is null))";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSql);
		sqlbv1.put("mContNo", mContNo);
		sqlbv1.put("mBatNo", mBatNo);
		tExeSQL = new ExeSQL();
		int rs_spec = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
		if (rs_spec > 0) {
			flag = 1;
			mSpecFlag = "1";
		}
		// tongmeng 2008-08-12 add
		// 增加对体检和生调的发放校验
		// add 2008-10-06 生调的发放校验
		// String tSQL_exist = "select count(1) from LPRReport where contno='"
		// + mContNo + "' and replyflag is null";
		// logger.debug("判断是否有需要放送的生调通知书 tSQL_exist:" + tSQL_exist);
		// tExeSQL = new ExeSQL();
		// int rs_exist = Integer.parseInt(tExeSQL.getOneValue(tSQL_exist));
		// if (rs_exist > 0) {
		// flag = 1;
		// }

		// 体检的发放校验 add 2008-10-29
		strSql = "select count(1) from lluwpenotice where contno='" + "?mContNo?"
				+ "' and (printflag is null or printflag='') and batno='" + "?mBatNo?" + "'";
		// +"' and customerno='"
		// +this.mCustomerNo+"' and customertype='"+this.mCustomerNoType+"'";
		logger.debug("判断是否有需要放送的体检通知书 strSql:" + strSql);
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strSql);
		sqlbv2.put("mContNo", mContNo);
		sqlbv2.put("mBatNo", mBatNo);
		tExeSQL = new ExeSQL();
		int rs_health = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
		if (rs_health > 0) {
			flag = 1;
		}

		//
		if (flag == 0) {
			// @@错误处理
			// CError.buildErr(this, "没有体检、生调，不能发核保通知书!");
			// return false;

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

		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		if (mClmNo == null) {
			CError.buildErr(this, "前台传输业务数据中ClmNo失败!");
			return false;
		}

		mBatNo = (String) mTransferData.getValueByName("BatNo");
		if (mBatNo == null) {
			CError.buildErr(this, "前台传输业务数据中BatNo失败!");
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
		noticeSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");

		if (mSubMissionID == null) {
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!");
			return false;
		}
		// tongmeng 2008-08-12 add
		// 先查询核保通知书发放对象
//		String tSQL = "select missionprop15,missionprop16 from lwmission where missionid='"
//				+ this.mMissionID
//				+ "' "
//				+ " and activityid='"
//				+ cOperate
//				+ "' and submissionid='" + this.noticeSubMissionID + "'";
//		SSRS tSSRS = new SSRS();
//		tSSRS = (new ExeSQL()).execSQL(tSQL);
//		if (tSSRS.getMaxRow() <= 0) {
//			CError.buildErr(this, "查找发放对象出错!");
//			return false;
//		}
//		this.mCustomerNo = tSSRS.GetText(1, 1);
//		this.mCustomerNoType = tSSRS.GetText(1, 2);

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
		// if("I".equals(mCustomerNoType)){
		// if (prepareIndUW() == false)
		// return false;
		// }
		// 准备险种核保表信息
		// if (preparePolUW() == false)
		// return false;
		// 选择工作流流转活动
		if (chooseActivity_New() == false)
			return false;
		// if (mUWSendFlag.equals("1") || mSendOperFlag.equals("1")||this.)
		// {
		// 准备打印管理表信息
		if (preparePrt() == false)
			return false;
		// tongmeng 2007-11-13 modify
		// 下面处理问题件逻辑有问题,修改在preparePrt进行处理
		// if (prepareIssue() == false)
		// return false;
		// }
		// 06-26 增加判断如果核保、体检通知书都没有则return false
		if ((!mUWSendFlag.equals("1")) && (!mSendPENoticeFlag.equals("1"))) {
			CError.buildErr(this, "没有录入问题件、没有承保计划变更、没有特约、没有加费，不能发核保通知书!");
			// return false;
		} else {
//			if (!dealMission()) {
//				return false;
//			}
			//更新核保状态为通知书未回收
			if(!UpdateUWStat()){
				return false;
			}
		}
		return true;
	}
	private boolean UpdateUWStat(){
		LLClaimDB tLLClaimDB=new LLClaimDB();
		tLLClaimDB.setClmNo(this.mClmNo);
		if(!tLLClaimDB.getInfo()){
			CError.buildErr(this, "查询理赔信息表失败！");
			return false;
		}
		tLLClaimDB.setUWState("3");
		mLLClaimSchema=tLLClaimDB.getSchema();
		return true;
	}
	/**
	 * 将当前核保状态改为3——核保未回复状态
	 * */
	

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
			// mLCIssuePolSet.add(tLCIssuePolSchema);
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
		mAgentCode = tLCContDB.getAgentCode();
		tManageCom = tLCContDB.getManageCom();
		mPrtNo = tLCContDB.getPrtNo();
		mSaleChnl = tLCContDB.getSaleChnl();
		mAppntName = tLCContDB.getAppntName();
		mInsuredNo = tLCContDB.getInsuredNo();
		mLCContSchema.setSchema(tLCContDB.getSchema());
		// mUWFlag = "8"; // 核保订正标志
		// 准备保单的复核标志
		// mLPContSchema.setUWFlag(mUWFlag);
		// 准备险种合同表数据
		// 理赔二核不会更改lccont 和 lcpol表中的uwflag
		// LPPolDB tLPPolDB = new LPPolDB();
		// tLPPolDB.setContNo(mContNo);
		// tLPPolDB.setEdorNo(mEdorNo);
		// tLPPolDB.setEdorType(mEdorType);
		// mLPPolSet = tLPPolDB.query();
		//
		// // 准备险种保单的复核标志
		// for (int i = 1; i < mLPPolSet.size(); i++) {
		// mLPPolSet.get(i).setUWFlag(mUWFlag);
		// }
		return true;
	}

	/**
	 * preparePrt 准备核保主表数据
	 * 
	 * @return boolean
	 */
	private boolean prepareContUW() {
		mLLCUWSubSet.clear();

		LLCUWMasterDB tLLCUWMasterDB = new LLCUWMasterDB();
		// tLLCUWMasterDB.setContNo(mContNo);
		tLLCUWMasterDB.setCaseNo(mClmNo);
		tLLCUWMasterDB.setBatNo(mBatNo);
		// tLLCUWMasterDB.query();
		mLLCUWMasterSet = tLLCUWMasterDB.query();
		for (int i = 1; i <= mLLCUWMasterSet.size(); i++) {
			mLLCUWMasterSet.get(i).setPassFlag(mUWFlag);
		}

		// 每次进行核保相关操作时，向核保轨迹表插一条数据
		LLCUWSubSchema tLLCUWSubSchema = new LLCUWSubSchema();
		LLCUWSubDB tLLCUWSubDB = new LLCUWSubDB();
		// tLLCUWSubDB.setContNo(mContNo);
		tLLCUWSubDB.setCaseNo(mClmNo);
		tLLCUWSubDB.setBatNo(mBatNo);
		LLCUWSubSet tLLCUWSubSet = new LLCUWSubSet();
		tLLCUWSubSet = tLLCUWSubDB.query();
		if (tLLCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			CError.buildErr(this, "LPCUWSub表取数失败!");
			return false;
		}

		int m = tLLCUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLLCUWSubSchema = new LLCUWSubSchema();
			tLLCUWSubSchema.setUWNo(m + tLLCUWSubSet.size()); // 第几次核保
			tLLCUWSubSchema.setContNo(mContNo);
			tLLCUWSubSchema.setGrpContNo("00000000000000000000");
			tLLCUWSubSchema.setProposalContNo(mLLCUWMasterSet.get(1)
					.getProposalContNo());
			tLLCUWSubSchema.setCaseNo(mClmNo);
			tLLCUWSubSchema.setBatNo(mBatNo);
			tLLCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
			tLLCUWSubSchema.setPrintFlag("1");
			tLLCUWSubSchema.setAutoUWFlag(mLLCUWMasterSet.get(1)
					.getAutoUWFlag());
			tLLCUWSubSchema.setState(mLLCUWMasterSet.get(1).getState());
			tLLCUWSubSchema.setOperator(mLLCUWMasterSet.get(1).getOperator()); // 操作员
			tLLCUWSubSchema.setManageCom(mLLCUWMasterSet.get(1).getManageCom());
			tLLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			CError.buildErr(this, "LLCUWSub表取数失败!");
			return false;
		}
		mLLCUWSubSet.add(tLLCUWSubSchema);
		return true;
	}

	/**
	 * preparePrt 准备被保人核保主表数据
	 * 
	 * @return boolean
	 */
//	private boolean prepareIndUW() {
//		mLPIndUWSubSet.clear();
//
//		LPIndUWMasterDB tLPIndUWMasterDB = new LPIndUWMasterDB();
//		tLPIndUWMasterDB.setContNo(mContNo);
//		tLPIndUWMasterDB.setInsuredNo(mCustomerNo);
//		if (!tLPIndUWMasterDB.getInfo()) {
//			// @@错误处理
//			CError.buildErr(this, "LCIndUWMaster表取数失败!");
//			return false;
//		}
//		mLPIndUWMasterSchema.setSchema(tLPIndUWMasterDB);
//		mLPIndUWMasterSchema.setPassFlag(mUWFlag);
//
//		// 每次进行核保相关操作时，向核保轨迹表插一条数据
//		LPIndUWSubSchema tLPIndUWSubSchema = new LPIndUWSubSchema();
//		LPIndUWSubDB tLPIndUWSubDB = new LPIndUWSubDB();
//		tLPIndUWSubDB.setContNo(mContNo);
//		tLPIndUWSubDB.setInsuredNo(mCustomerNo);
//		LPIndUWSubSet tLPIndUWSubSet = new LPIndUWSubSet();
//		tLPIndUWSubSet = tLPIndUWSubDB.query();
//		if (tLPIndUWSubDB.mErrors.needDealError()) {
//			// @@错误处理
//			CError.buildErr(this, "LCIndUWSub表取数失败!");
//			return false;
//		}
//
//		int m = tLPIndUWSubSet.size();
//		logger.debug("subcount=" + m);
//		if (m > 0) {
//			m++; // 核保次数
//			tLPIndUWSubSchema = new LPIndUWSubSchema();
//			tLPIndUWSubSchema.setUWNo(m + tLPIndUWSubSet.size()); // 第几次核保
//			tLPIndUWSubSchema.setContNo(mContNo);
//			tLPIndUWSubSchema.setInsuredNo(mCustomerNo);
//			tLPIndUWSubSchema.setGrpContNo(mLPIndUWMasterSchema.getGrpContNo());
//			tLPIndUWSubSchema.setProposalContNo(mLPIndUWMasterSchema
//					.getProposalContNo());
//			tLPIndUWSubSchema.setPassFlag(mUWFlag); // 核保意见
//			tLPIndUWSubSchema.setPrintFlag("1");
//			tLPIndUWSubSchema.setAutoUWFlag(mLPIndUWMasterSchema
//					.getAutoUWFlag());
//			tLPIndUWSubSchema.setState(mLPIndUWMasterSchema.getState());
//			tLPIndUWSubSchema.setOperator(mLPIndUWMasterSchema.getOperator()); // 操作员
//			tLPIndUWSubSchema.setManageCom(mLPIndUWMasterSchema.getManageCom());
//			tLPIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
//			tLPIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
//			tLPIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
//			tLPIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());
//		} else {
//			// @@错误处理
//			CError.buildErr(this, "LCIndUWSub表取数失败!");
//			return false;
//		}
//		mLPIndUWSubSet.add(tLPIndUWSubSchema);
//		return true;
//	}

	/**
	 * 准备险种核保表信息 输出：如果发生错误则返回false,否则返回true
	 */
	// private boolean preparePolUW() {
	// mLPUWMasterSet.clear();
	// mLPUWSubSet.clear();
	// LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
	// LPPolSchema tLPPolSchema = new LPPolSchema();
	// LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
	// LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
	// LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
	// LCUWSubDB tLCUWSubDB = new LCUWSubDB();
	// LCUWSubSet tLCUWSubSet = new LCUWSubSet();
	//
	// for (int i = 1; i <= mLPPolSet.size(); i++) {
	// tLCUWMasterSchema = new LCUWMasterSchema();
	// tLCUWMasterDB = new LCUWMasterDB();
	// tLPPolSchema = mLPPolSet.get(i);
	// tLCUWMasterDB.setProposalNo(tLPPolSchema.getProposalNo());
	//
	// if (!tLCUWMasterDB.getInfo()) {
	// // @@错误处理
	// CError.buildErr(this, "LCUWMaster表取数失败!");
	// return false;
	// }
	// tLCUWMasterSchema.setSchema(tLCUWMasterDB);
	//
	// // tLCUWMasterSchema.setUWNo(tLCUWMasterSchema.getUWNo()+1);
	// // 核保主表中的UWNo表示该投保单经过几次人工核保
	// // (等价于经过几次自动核保次数),而不是人工核保结论(包括核保通知书,上报等)下过几次.所以将其注释.sxy-2003-09-19
	// tLCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
	// tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
	// tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
	//
	// mLPUWMasterSet.add(tLCUWMasterSchema);
	//
	// // 核保轨迹表
	// tLCUWSubSchema = new LCUWSubSchema();
	// tLCUWSubDB = new LCUWSubDB();
	// tLCUWSubSet = new LCUWSubSet();
	// ///String tSql = "select uwno from lcuwsub where polno='"++"'";
	// tLCUWSubDB.setPolNo(tLPPolSchema.getPolNo());
	// tLCUWSubSet = tLCUWSubDB.query();
	// if (tLCUWSubDB.mErrors.needDealError()) {
	// // @@错误处理
	// CError.buildErr(this, "LCUWSub表取数失败!");
	// return false;
	// }
	// int m = tLCUWSubSet.size();
	// logger.debug("subcount=" + m);
	// if (m > 0) {
	// m++; // 核保次数
	// tLCUWSubSchema = new LCUWSubSchema();
	// tLCUWSubSchema.setUWNo(m+mLPUWSubSet.size()); // 第几次核保
	// tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
	// tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
	// tLCUWSubSchema.setContNo(tLCUWMasterSchema.getContNo());
	// tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema
	// .getProposalContNo());
	// tLCUWSubSchema.setPolNo(tLCUWMasterSchema.getPolNo());
	// tLCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
	// tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
	// tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
	// tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
	// tLCUWSubSchema.setPrintFlag("1");
	// tLCUWSubSchema.setState(mUWFlag);
	// tLCUWSubSchema.setOperator(mOperater); // 操作员
	// tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
	// tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
	// tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
	// tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
	// tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
	// } else {
	// // @@错误处理
	// CError.buildErr(this, "LCUWSub表取数失败!");
	// return false;
	// }
	// mLPUWSubSet.add(tLCUWSubSchema);
	// }
	// return true;
	// }
	private boolean chooseActivity_New(){
		String tsql = "";
		LLIssuePolDB tLLIssuePolDB = new LLIssuePolDB();
		LLIssuePolSet tLLIssuePolSet = new LLIssuePolSet();

		mUWSendFlag = "0"; // 打印核保通知书标志
		mUWSendFlag_UnPrint = "0";
		mSendOperFlag = "0"; // 打印业务员通知书标志
		mQuesOrgFlag = "0"; // 机构问题件标志
		mApproveModifyFlag = "0"; // 复核修改标志
		mSendPENoticeFlag = "0"; // 体检通知书标志
		mSendReportNoticeFlag = "0";// 生调通知书标志
		mImpartQuestFlag = "0";// 健康告知问卷标志

		logger.debug("****************************准备投保人的核保通知书**********************************");

		for (int i = 1; i <= mLLUWMasterSet.size(); i++) {
			if (mLLUWMasterSet.get(i).getSpecFlag() != null
					&& mLLUWMasterSet.get(i).getSpecFlag().equals("1"))
				mUWSendFlag = "1";
		}

		ExeSQL tExeSQL = new ExeSQL();
		String strSql = "";
		// 是否有加费
		strSql = "select count(*) from lluwpremmaster where contno='"
				+ "?mContNo?"
				+ "' and payplancode like '000000%%' and batno='"
				+ "?mBatNo?"
				+ "'"
				+ " and exists(select 1 from lcpol a where a.polno=lluwpremmaster.polno "
				+ " and insuredno='" + "?mInsuredNo?" + "' "
				// + " and '"+this.mCustomerNoType+"' ='I' "
				+ ")";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(strSql);
		sqlbv3.put("mContNo", mContNo);
		sqlbv3.put("mBatNo", mBatNo);
		sqlbv3.put("mInsuredNo", mInsuredNo);
		tExeSQL = new ExeSQL();
		int rs_addfee = Integer.parseInt(tExeSQL.getOneValue(sqlbv3));
		if (rs_addfee > 0) {
			mAddFeeFlag = "1";
			mUWSendFlag = "1";
		}
		// 没有特约，不能发核保通知书
		strSql = "select count(*) from LLUWSpecMaster where contno=" + "'"
				+ "?mContNo?" + "' and  needprint='Y' and batno='" + "?mBatNo?"
				+ "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(strSql);
		sqlbv4.put("mContNo", mContNo);
		sqlbv4.put("mBatNo", mBatNo);
		tExeSQL = new ExeSQL();
		int rs_spec = Integer.parseInt(tExeSQL.getOneValue(sqlbv4));
		if (rs_spec > 0) {
			mSpecFlag = "1";
			mUWSendFlag = "1";
		}

		// 查询需要打印到保单的核保通知书
		tsql = "select * from llissuepol where 1=1"
				+ " and Contno = '"
				+ "?mContNo?"
				+ "' and backobjtype = '2' and (replyresult is null or replyresult='') and needprint = 'Y'"
				+ " and (prtseq is null or prtseq = '')"
	+ " and backobjtype = '2' and (replyresult is null or replyresult='') and needprint = 'Y'"
				+ " and (prtseq is null or prtseq='') "
				+ " and standbyflag2 = 'Y' "
				+ " and clmno='" + "?mClmNo?"
				+ "'" + " and batno='" + "?mBatNo?" + "'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tsql);
		sqlbv5.put("mContNo", mContNo);
		sqlbv5.put("mClmNo", mClmNo);
		sqlbv5.put("mBatNo", mBatNo);
		logger.debug("tSql==" + tsql);
		tLLIssuePolSet = tLLIssuePolDB.executeQuery(sqlbv5);
		if (tLLIssuePolSet.size() > 0) {
			mUWSendFlag = "1";
			this.mLLIssuePolSet_UWSend.add(tLLIssuePolSet);
		}

		// 查询不需要打印到保单的核保通知书
		tsql = "select * from llissuepol where 1=1"
				+ " and Contno = '"
				+ "?mContNo?"
				+ "' and backobjtype = '2' and (replyresult is null or replyresult='') and needprint = 'Y'"
				+ " and (prtseq is null or prtseq='')" + " and standbyflag2 = 'N' "
				+ " and clmno='" + "?mClmNo?"
				+ "'" + " and batno='" + "?mBatNo?" + "'";
		logger.debug("tSql_unprint==" + tsql);
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tsql);
		sqlbv6.put("mContNo", mContNo);
		sqlbv6.put("mClmNo", mClmNo);
		sqlbv6.put("mBatNo", mBatNo);
		tLLIssuePolSet = new LLIssuePolSet();
		tLLIssuePolSet = tLLIssuePolDB.executeQuery(sqlbv6);
		if (tLLIssuePolSet.size() > 0) {
			mUWSendFlag_UnPrint = "1";
			this.mLLIssuePolSet_UWSend_UnPrint.add(tLLIssuePolSet);
		}

		logger.debug("*********endend***************准备投保人的核保通知书结束*****************endendend*****************");
		// 险种有拒保、不予续保结论，发送核保通知书
		String tJBSql = "select 1 from lluwmaster where caseno='" + "?mClmNo?"
				+ "' and batno='" + "?mBatNo?" + "'" + " and passflag in('1','6')";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tJBSql);
		sqlbv7.put("mClmNo", mClmNo);
		sqlbv7.put("mBatNo", mBatNo);
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv7);
		if (tSSRS.MaxRow > 0) {
			mUWSendFlag = "1";
		}

		// ////////////////////////////////////////////////

		String tImpartSql = "select count(*) from llquestionnaire where clmno='"
				+ "?mClmNo?"
				+ "' and batno='"
				+ "?mBatNo?"
				+ "' and contno='"
				+ "?mContNo?" + "'";
		// ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tImpartSql);
		sqlbv8.put("mClmNo", mClmNo);
		sqlbv8.put("mBatNo", mBatNo);
		sqlbv8.put("mContNo", mContNo);
		String tImpartFlag = tExeSQL.getOneValue(sqlbv8);
		logger.debug("判断是否需要发送补充告知说明书：" + tImpartSql);
		if (!tImpartFlag.equals("0")) {
			// mImpartQuestFlag = "1";
		} else {
			mImpartQuestFlag = "0";
		}
		//--被保人相关***********************************************************
		// ****
		logger.debug("************************准备被保人的核保通知书**********************************");
		if (mAddFeeFlag.equals("1") || mSpecFlag.equals("1")
				|| mChangePolFlag.equals("1")) {
			mUWSendFlag = "1";
		}

		logger.debug("************************准备客户的核保通知书--体检**********************************");
		// 查询待发体检 add ln 2008-10-29

	logger.debug("************************准备客户的核保通知书--体检**********************************");
	// 查询待发体检 add ln 2008-10-29
	tsql = "select * from lluwpenotice where 1=1 and Contno = '" + "?mContNo?"
			+ "' and (printflag is null or printflag='') and clmno='" + "?mClmNo?" + "'" + " and batno='" + "?mBatNo?" + "'";
	logger.debug("tSql==" + tsql);
	SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
	sqlbv9.sql(tsql);
	sqlbv9.put("mClmNo", mClmNo);
	sqlbv9.put("mBatNo", mBatNo);
	sqlbv9.put("mContNo", mContNo);
	LLUWPENoticeSet tLLUWPENoticeSet = new LLUWPENoticeSet();
	LLUWPENoticeDB tLLUWPENoticeDB = new LLUWPENoticeDB();
	tLLUWPENoticeSet = tLLUWPENoticeDB.executeQuery(sqlbv9);
	if (tLLUWPENoticeSet.size() > 0) {
		mSendPENoticeFlag = "1";
		mLLUWPENoticeSet.add(tLLUWPENoticeSet);
	}
	logger.debug("*********endend***************准备客户的核保通知书--体检结束*****************endendend*****************");

		return true;
	}
	/**
	 * preparePrt 准备打印表
	 * 
	 * @return boolean
	 */
	private boolean chooseActivity() {
		String tsql = "";
		LLIssuePolDB tLLIssuePolDB = new LLIssuePolDB();
		LLIssuePolSet tLLIssuePolSet = new LLIssuePolSet();

		mUWSendFlag = "0"; // 打印核保通知书标志
		mUWSendFlag_UnPrint = "0";
		mSendOperFlag = "0"; // 打印业务员通知书标志
		mQuesOrgFlag = "0"; // 机构问题件标志
		mApproveModifyFlag = "0"; // 复核修改标志
		mSendPENoticeFlag = "0"; // 体检通知书标志
		mSendReportNoticeFlag = "0";// 生调通知书标志
		mImpartQuestFlag = "0";// 健康告知问卷标志

		// tongmeng 2007-11-28 modify
		// 加费操作改为判断合同核保表
		// tongmeng 2008-08-13 modify
		// 支持对不同对象发送核保通知书
		// --投保人发生调、特约、承保计划变更
//		if (this.mCustomerNoType.endsWith("A")) {
//			logger.debug("****************************准备投保人的核保通知书**********************************");
//
//			for (int i = 1; i <= mLLUWMasterSet.size(); i++) {
//				/*
//				 * if (mLPUWMasterSet.get(i).getAddPremFlag() != null &&
//				 * mLPUWMasterSet.get(i).getAddPremFlag().equals("1"))
//				 * mUWSendFlag = "1";
//				 */
//				if (mLLUWMasterSet.get(i).getSpecFlag() != null
//						&& mLLUWMasterSet.get(i).getSpecFlag().equals("1"))
//					mUWSendFlag = "1";
//			}
//
//			ExeSQL tExeSQL = new ExeSQL();
//			String strSql = "";
//			// 是否有加费
//			strSql = "select count(*) from lluwpremmaster where contno='"
//					+ mContNo
//					+ "' and payplancode like '000000%%' and batno='"
//					+ mBatNo
//					+ "'"
//					+ " and exists(select 1 from lcpol a where a.polno=lluwpremmaster.polno "
//					+ " and insuredno='" + mInsuredNo + "' "
//					// + " and '"+this.mCustomerNoType+"' ='I' "
//					+ ")";
//			tExeSQL = new ExeSQL();
//			int rs_addfee = Integer.parseInt(tExeSQL.getOneValue(strSql));
//			if (rs_addfee > 0) {
//				mAddFeeFlag = "1";
//				mUWSendFlag = "1";
//			}
//			// 没有特约，不能发核保通知书
//			strSql = "select count(*) from LLUWSpecMaster where contno=" + "'"
//					+ mContNo + "' and  needprint='Y' and batno='" + mBatNo
//					+ "'";
//			// + " and (customerno is null or (customerno='"+this.mCustomerNo
//			// +"' and '"+this.mCustomerNoType+"' ='I'"
//			// +" and prtflag is null))";
//			tExeSQL = new ExeSQL();
//			int rs_spec = Integer.parseInt(tExeSQL.getOneValue(strSql));
//			if (rs_spec > 0) {
//				mSpecFlag = "1";
//				mUWSendFlag = "1";
//			}
//
//			// if (mLLCUWMasterSet.getChangePolFlag() != null
//			// && mLPCUWMasterSchema.getChangePolFlag().equals("1")) {
//			// mUWSendFlag = "1";
//			// }
//			// ln 2008-10-08 add
//			// 理赔二核不需要生调
//			// tsql = "select * from LPRReport where contno='"
//			// + mContNo + "' and replyflag is null";
//			// logger.debug("tSql==" + tsql);
//			// LPRReportDB tLPRReportDB = new LPRReportDB();
//			// LPRReportSet tLPRReportSet = new LPRReportSet();
//			// tLPRReportSet = tLPRReportDB.executeQuery(tsql);
//			// if(tLPRReportSet.size()>0) {
//			// mSendReportNoticeFlag = "1";
//			// this.mLPRReportSet_org.add(tLPRReportSet);
//			// }
//			// end add ln 2008-10-08
//			// add ln 2008-12-2
//			// 查询发送给业务员通知书
//			// tsql = "select * from lcissuepol where 1=1" + " and Contno = '"
//			// + mContNo + "' and backobjtype = '3' and prtseq is null"
//			// + " and needprint = 'Y' ";
//			// tLCIssuePolSet = new LCIssuePolSet();
//			// logger.debug("tSql==" + tsql);
//			// tLCIssuePolSet = tLCIssuePolDB.executeQuery(tsql);
//			// if (tLCIssuePolSet.size() > 0) {
//			// mSendOperFlag = "1";
//			// this.mLCIssuePolSet_SendOper.add(tLCIssuePolSet);
//			// }
//			// 查询发送给机构的问题件
//			// tsql = "select * from lcissuepol where 1=1" + " and Contno = '"
//			// + mContNo + "' and backobjtype = '4' and replyresult is null"
//			// + " and needprint = 'Y' ";
//			// tLCIssuePolSet = new LCIssuePolSet();
//			// logger.debug("tSql==" + tsql);
//			// tLCIssuePolSet = tLCIssuePolDB.executeQuery(tsql);
//			// if (tLCIssuePolSet.size() > 0) {
//			// mQuesOrgFlag = "1";
//			// this.mLCIssuePolSet_QuesOrg.add(tLCIssuePolSet);
//			// }
//
//			// backobjtype = '2' 为发送给保户--投保人的核保通知书
//			// 查询需要打印到保单的核保通知书
//			tsql = "select * from llissuepol where 1=1"
//					+ " and Contno = '"
//					+ mContNo
//					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
//					+ " and prtseq is null "
//					+ " and standbyflag2 = 'Y' "
//					// + " and questionobj='"
//					// + this.mCustomerNo
//					// + "'"
//					+ " and questionobjtype='0'" + " and clmno='" + mClmNo
//					+ "'" + " and batno='" + mBatNo + "'";
//			logger.debug("tSql==" + tsql);
//			tLLIssuePolSet = tLLIssuePolDB.executeQuery(tsql);
//			if (tLLIssuePolSet.size() > 0) {
//				mUWSendFlag = "1";
//				this.mLLIssuePolSet_UWSend.add(tLLIssuePolSet);
//			}
//
//			// 查询不需要打印到保单的核保通知书
//			tsql = "select * from llissuepol where 1=1"
//					+ " and Contno = '"
//					+ mContNo
//					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
//					+ " and prtseq is null " + " and standbyflag2 = 'N' "
//					+ " and questionobj='" + this.mCustomerNo
//					+ "' and questionobjtype='0'" + " and clmno='" + mClmNo
//					+ "'" + " and batno='" + mBatNo + "'";
//			logger.debug("tSql_unprint==" + tsql);
//			tLLIssuePolSet = new LLIssuePolSet();
//			tLLIssuePolSet = tLLIssuePolDB.executeQuery(tsql);
//			if (tLLIssuePolSet.size() > 0) {
//				mUWSendFlag_UnPrint = "1";
//				this.mLLIssuePolSet_UWSend_UnPrint.add(tLLIssuePolSet);
//			}
//
//			// 查询是否有不需要创建问题件修改岗节点的因素存在
//			// String ApproveModifyFlag = "0";
//			// tsql = "select 1 from lcissuepol where 1=1"
//			// + " and Contno = '"
//			// + mContNo
//			// +
//			// "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
//			// + " and prtseq is null ";
//			// logger.debug("tSql judge ApproveModifyFlag==" + tsql);
//			// tLCIssuePolSet = new LCIssuePolSet();
//			// tLCIssuePolSet = tLCIssuePolDB.executeQuery(tsql);
//			// if (tLCIssuePolSet.size() > 0) {
//			// ApproveModifyFlag = "1";
//			// }
//			// 查询发送给问题件修改岗的问题件
//			// tsql = "select * from lcissuepol where 1=1"
//			// + " and Contno = '"
//			// + mContNo
//			// +
//			// "' and backobjtype = '1' and state is null and replyresult is null "
//			// + " and needprint = 'Y' ";
//			// logger.debug("tSql==" + tsql);
//			// tLCIssuePolSet = new LCIssuePolSet();
//			// tLCIssuePolSet = tLCIssuePolDB.executeQuery(tsql);
//			// //if (tLCIssuePolSet.size() > 0 && mSendOperFlag.equals("0")
//			// // && mUWSendFlag.equals("0") && mUWSendFlag_UnPrint.equals("0")
//			// && mQuesOrgFlag.equals("0")) {
//			// if (tLCIssuePolSet.size() > 0 && mSendOperFlag.equals("0")
//			// && ApproveModifyFlag.equals("0") && mQuesOrgFlag.equals("0")) {
//			// mApproveModifyFlag = "1";
//			// }
//			logger.debug("*********endend***************准备投保人的核保通知书结束*****************endendend*****************");
//			// 险种有拒保、不予续保结论，发送核保通知书
//			String tJBSql = "select 1 from lluwmaster where caseno='" + mClmNo
//					+ "' and batno='" + mBatNo + "'" + " and passflag in('1','6')";
//			SSRS tSSRS = new SSRS();
//			tSSRS = tExeSQL.execSQL(tJBSql);
//			if (tSSRS.MaxRow > 0) {
//				mUWSendFlag = "1";
//			}
//
//			// ////////////////////////////////////////////////
//
//			String tImpartSql = "select count(*) from llquestionnaire where clmno='"
//					+ mClmNo
//					+ "' and batno='"
//					+ mBatNo
//					+ "' and contno='"
//					+ mContNo + "'";
//			// ExeSQL tExeSQL = new ExeSQL();
//			String tImpartFlag = tExeSQL.getOneValue(tImpartSql);
//			logger.debug("判断是否需要发送补充告知说明书：" + tImpartSql);
//			if (!tImpartFlag.equals("0")) {
//				// mImpartQuestFlag = "1";
//			} else {
//				mImpartQuestFlag = "0";
//			}
//			//--被保人相关***********************************************************
//			// ****
//			logger.debug("************************准备被保人的核保通知书**********************************");
//			if (mAddFeeFlag.equals("1") || mSpecFlag.equals("1")
//					|| mChangePolFlag.equals("1")) {
//				mUWSendFlag = "1";
//			}
//
//			// backobjtype = '2' 为发送给保户的核保通知书
//			// 查询需要打印到保单的核保通知书
//			tsql = "select * from llissuepol where 1=1"
//					+ " and Contno = '"
//					+ mContNo
//					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
//					+ " and prtseq is null " + " and standbyflag2 = 'Y' "
//					+ " and questionobj='" + this.mCustomerNo
//					+ "' and questionobjtype<>'0'" + " and clmno='" + mClmNo
//					+ "'" + " and batno='" + mBatNo + "'";
//			logger.debug("tSql==" + tsql);
//			tLLIssuePolSet = tLLIssuePolDB.executeQuery(tsql);
//			if (tLLIssuePolSet.size() > 0) {
//				mUWSendFlag = "1";
//				this.mLLIssuePolSet_UWSend.add(tLLIssuePolSet);
//			}
//			// 查询不需要打印到保单的核保通知书
//			tsql = "select * from llissuepol where 1=1"
//					+ " and Contno = '"
//					+ mContNo
//					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
//					+ " and prtseq is null " + " and standbyflag2 = 'N' "
//					+ " and questionobj='" + this.mCustomerNo
//					+ "' and questionobjtype<>'0'" + " and clmno='" + mClmNo
//					+ "'" + " and batno='" + mBatNo + "'";
//			logger.debug("tSql_unprint==" + tsql);
//			tLLIssuePolSet = new LLIssuePolSet();
//			tLLIssuePolSet = tLLIssuePolDB.executeQuery(tsql);
//			if (tLLIssuePolSet.size() > 0) {
//				mUWSendFlag_UnPrint = "1";
//				this.mLLIssuePolSet_UWSend_UnPrint.add(tLLIssuePolSet);
//			}
//			logger.debug("*********endend***************准备被保人的核保通知书结束*****************endendend*****************");
//
//			logger.debug("************************准备客户的核保通知书--体检**********************************");
//			// 查询待发体检 add ln 2008-10-29
//
//		} else {
//			String tImpartSql = "select count(*) from llquestionnaire where clmno='"
//					+ mClmNo
//					+ "' and batno='"
//					+ mBatNo
//					+ "' and contno='"
//					+ mContNo + "'";
//			ExeSQL tExeSQL = new ExeSQL();
//			String tImpartFlag = tExeSQL.getOneValue(tImpartSql);
//			logger.debug("判断是否需要发送补充告知说明书：" + tImpartSql);
//			if (!tImpartFlag.equals("0")) {
//				// mImpartQuestFlag = "1";
//			} else {
//				mImpartQuestFlag = "0";
//			}
//			//--被保人相关***********************************************************
//			// ****
//			logger.debug("************************准备被保人的核保通知书**********************************");
//			if (mAddFeeFlag.equals("1") || mSpecFlag.equals("1")
//					|| mChangePolFlag.equals("1")) {
//				mUWSendFlag = "1";
//			}
//
//			// backobjtype = '2' 为发送给保户的核保通知书
//			// 查询需要打印到保单的核保通知书
//			tsql = "select * from llissuepol where 1=1"
//					+ " and Contno = '"
//					+ mContNo
//					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
//					+ " and prtseq is null " + " and standbyflag2 = 'Y' "
//					+ " and questionobj='" + this.mCustomerNo
//					+ "' and questionobjtype<>'0'" + " and clmno='" + mClmNo
//					+ "'" + " and batno='" + mBatNo + "'";
//			logger.debug("tSql==" + tsql);
//			tLLIssuePolSet = tLLIssuePolDB.executeQuery(tsql);
//			if (tLLIssuePolSet.size() > 0) {
//				mUWSendFlag = "1";
//				this.mLLIssuePolSet_UWSend.add(tLLIssuePolSet);
//			}
//			// 查询不需要打印到保单的核保通知书
//			tsql = "select * from llissuepol where 1=1"
//					+ " and Contno = '"
//					+ mContNo
//					+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
//					+ " and prtseq is null " + " and standbyflag2 = 'N' "
//					+ " and questionobj='" + this.mCustomerNo
//					+ "' and questionobjtype<>'0'" + " and clmno='" + mClmNo
//					+ "'" + " and batno='" + mBatNo + "'";
//			logger.debug("tSql_unprint==" + tsql);
//			tLLIssuePolSet = new LLIssuePolSet();
//			tLLIssuePolSet = tLLIssuePolDB.executeQuery(tsql);
//			if (tLLIssuePolSet.size() > 0) {
//				mUWSendFlag_UnPrint = "1";
//				this.mLLIssuePolSet_UWSend_UnPrint.add(tLLIssuePolSet);
//			}
//			logger.debug("*********endend***************准备被保人的核保通知书结束*****************endendend*****************");
//			mUWSendFlag = "0";
//		}

//		logger.debug("************************准备客户的核保通知书--体检**********************************");
//		// 查询待发体检 add ln 2008-10-29
//		tsql = "select * from lluwpenotice where 1=1 and Contno = '" + mContNo
//				+ "' and printflag is null and customerno='" + this.mCustomerNo
//				+ "' and customertype='" + this.mCustomerNoType + "'"
//				+ " and clmno='" + mClmNo + "'" + " and batno='" + mBatNo + "'";
//		logger.debug("tSql==" + tsql);
//		LLUWPENoticeSet tLLUWPENoticeSet = new LLUWPENoticeSet();
//		LLUWPENoticeDB tLLUWPENoticeDB = new LLUWPENoticeDB();
//		tLLUWPENoticeSet = tLLUWPENoticeDB.executeQuery(tsql);
//		if (tLLUWPENoticeSet.size() > 0) {
//			mSendPENoticeFlag = "1";
//			mLLUWPENoticeSet.add(tLLUWPENoticeSet);
//		}
//		logger.debug("*********endend***************准备客户的核保通知书--体检结束*****************endendend*****************");

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
		// tongmeng 2007-11-12 modify
		// 需要重新初始化mLOPRTManagerSchema
		if (mUWSendFlag.equals("1")) {    //需要打印理赔核保通知书
			String tSQL_spec="";
			boolean falg_A = false ,falg_I = false; //如果添加投保人或者被保人
			for (int i = 1; i <= this.mLLIssuePolSet_UWSend.size(); i++) {   //问题件集合需要打印核保
				// 发送核保通知书
				String tempCustomerType="";
				String tLimit = PubFun.getNoLimit(mManageCom);
				// 09-06-08 通知书流水号启用十位通知书流水号
				// mPrtSeqUW = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
				String cLimit = PrintManagerBL.CODE_LPPRINTHB.replaceAll("[^0-9]",
						"");
				mPrtSeqUW = PubFun1.CreateMaxNo("UWPRTSEQ", cLimit);
				logger.debug("---tLimit---" + tLimit);

				// 准备打印管理表数据
				mLOPRTManagerSchema = new LOPRTManagerSchema();
				mLOPRTManagerSchema.setPrtSeq(mPrtSeqUW);
				mLOPRTManagerSchema.setOtherNo(mContNo);
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_LPHB); //理赔核保通知书(打印类
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
				mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqUW);
				mLOPRTManagerSchema.setForMakeDate(tDate);
				mLOPRTManagerSchema.setStandbyFlag4(this.mClmNo);//增加clmno用于回销查询
				mLOPRTManagerSchema.setStandbyFlag5(this.mBatNo);//增加batno
				if("0".equals(mLLIssuePolSet_UWSend.get(i).getQuestionObjType()) && !falg_A){//发送对象是投保人
					falg_A = true;  //确保投保人发送通知书只发送一遍
					mLOPRTManagerSchema.setStandbyFlag6("A");//客户类型			
					mTransferData.setNameAndValue("CustomerNoType_A", "A");//
					mTransferData.setNameAndValue("CustomerNo_A", mLLIssuePolSet_UWSend.get(i).getQuestionObj());//
					mTransferData.setNameAndValue("PrtSeq_A", mPrtSeqUW);//
					mTransferData.setNameAndValue("OldPrtSeq_A", mPrtSeqUW);//
					mTransferData.setNameAndValue("UWSendFlag_A", "1");//
					mLOPRTManagerSchema.setStandbyFlag7(mLLIssuePolSet_UWSend.get(i).getQuestionObj());//客户号
					mTransferData.setNameAndValue("Code", "LP90");//
					mLOPRTManagerSet.add(mLOPRTManagerSchema);
				}else{
					falg_I = true;    //确保被保人通知书只发送一遍
					mLOPRTManagerSchema.setStandbyFlag6("I");//客户类型
					mTransferData.setNameAndValue("CustomerNoType_I", "I");//
					mTransferData.setNameAndValue("CustomerNo_I", mLLIssuePolSet_UWSend.get(i).getQuestionObj());//
					mTransferData.setNameAndValue("PrtSeq_I", mPrtSeqUW);//
					mTransferData.setNameAndValue("OldPrtSeq_I", mPrtSeqUW);//
					mTransferData.setNameAndValue("UWSendFlag_I", "1");//

					mLOPRTManagerSchema.setStandbyFlag7(mLLIssuePolSet_UWSend.get(i).getQuestionObj());//客户号
					mTransferData.setNameAndValue("Code", "LP90");//
					mLOPRTManagerSet.add(mLOPRTManagerSchema);
					tSQL_spec = "select * from lluwspecmaster where contno="
							+ "(select contno from lccont where contno='" + "?mContNo?"
							+ "') and  needprint='Y' " + " and (customerno='"
							+ "?customerno?"
							+ "' and (prtflag is null or prtflag=''))" + " and clmno='" + "?mClmNo?"
							+ "'" + " and batno='" + "?mBatNo?" + "'";
					SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
					sqlbv10.sql(tSQL_spec);
					sqlbv10.put("mContNo", mContNo);
					sqlbv10.put("customerno", mLLIssuePolSet_UWSend.get(i).getQuestionObj() );
					sqlbv10.put("mClmNo", mClmNo);
					sqlbv10.put("mBatNo", mBatNo);
					LLUWSpecMasterDB tLLUWSpecMasterDB = new LLUWSpecMasterDB();
					LLUWSpecMasterSet tLLUWSpecMasterSet = new LLUWSpecMasterSet();
					tLLUWSpecMasterSet = tLLUWSpecMasterDB.executeQuery(sqlbv10);
					for (int j = 1; j <= tLLUWSpecMasterSet.size(); j++) {
						LLUWSpecMasterSchema tempLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
						tempLLUWSpecMasterSchema.setSchema(tLLUWSpecMasterSet.get(j));
						tempLLUWSpecMasterSchema.setPrtSeq(mPrtSeqUW);
						tempLLUWSpecMasterSchema.setPrtFlag("0");
						this.mLLUWSpecMasterSet.add(tempLLUWSpecMasterSchema);
					}
				}
				
				
				

				// tongmeng 2007-12-27 add
				// 特约处理 modify ln 2008-12-3 对合同级特约不置打印流水号，要打在每个被保人核保通知书上
				
				this.mLLIssuePolSet_UWSend.get(i).setPrtSeq(mPrtSeqUW);
				this.mLLIssuePolSet_UWSend.get(i).setState("0");
			}
			
			//添加特约通知书    复合主键pk(clmno,mBatNo,proposalcontno,serialno)  
			tSQL_spec = "select * from lluwspecmaster where contno="
					+ "(select contno from lccont where contno='" + "?mContNo?"
					+ "') and  needprint='Y' " 
					+ " and (prtflag is null or prtflag='')" + " and clmno='" + "?mClmNo?"
					+ "'" + " and batno='" + "?mBatNo?" + "'";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tSQL_spec);
			sqlbv10.put("mContNo", mContNo);
//			sqlbv10.put("customerno", mLLIssuePolSet_UWSend.get(i).getQuestionObj() );
			sqlbv10.put("mClmNo", mClmNo);
			sqlbv10.put("mBatNo", mBatNo);
			LLUWSpecMasterDB Contributing_tLLUWSpecMasterDB = new LLUWSpecMasterDB();
			LLUWSpecMasterSet tLLUWSpecMasterSet = new LLUWSpecMasterSet();
			tLLUWSpecMasterSet = Contributing_tLLUWSpecMasterDB.executeQuery(sqlbv10);
			//这里不需要检查问题件集合因为特约是一个单独的 
			boolean tLLUWSpecMasterSet_falg = false;   //打印管理表只添加一次
			for (int j = 1; j <= tLLUWSpecMasterSet.size(); j++) {
				String tLimit = PubFun.getNoLimit(mManageCom);
				// 09-06-08 通知书流水号启用十位通知书流水号
				String cLimit = PrintManagerBL.CODE_LPPRINTHB.replaceAll("[^0-9]","");
				mPrtSeqUW = PubFun1.CreateMaxNo("UWPRTSEQ", cLimit);
				logger.debug("---tLimit---" + tLimit);
				// 准备打印管理表数据
				mLOPRTManagerSchema = new LOPRTManagerSchema();
				mLOPRTManagerSchema.setPrtSeq(mPrtSeqUW);
				mLOPRTManagerSchema.setOtherNo(mContNo);
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_LPHB); //理赔核保通知书(打印类// )
				mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
				mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
				mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
				mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
				mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
				mLOPRTManagerSchema.setStateFlag("0");
				mLOPRTManagerSchema.setPatchFlag("0");
				mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
				mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
				mLOPRTManagerSchema.setStandbyFlag1(mLCContSchema.getAppntNo()); // 投保人编码
				mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
				mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqUW);
				mLOPRTManagerSchema.setForMakeDate(tDate);
				mLOPRTManagerSchema.setStandbyFlag4(this.mClmNo);//增加clmno用于回销查询
				mLOPRTManagerSchema.setStandbyFlag5(this.mBatNo);//增加batno
				LLUWSpecMasterSchema tempLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
				tempLLUWSpecMasterSchema.setSchema(tLLUWSpecMasterSet.get(j));
				tempLLUWSpecMasterSchema.setPrtSeq(mPrtSeqUW); //打印书流水号
				tempLLUWSpecMasterSchema.setPrtFlag("0");
				this.mLLUWSpecMasterSet.add(tempLLUWSpecMasterSchema);
				if(tLLUWSpecMasterSet_falg){   
					continue;
				}
				mLOPRTManagerSchema.setStandbyFlag6("I");//客户类型
				mLOPRTManagerSchema.setStandbyFlag7(tLLUWSpecMasterSet.get(j).getCustomerNo());//客户号
				mTransferData.setNameAndValue("ContNo",tLLUWSpecMasterSet.get(j).getContNo()); //合同号
				mTransferData.setNameAndValue("PrtSeq_I", mPrtSeqUW);//通知书流水号
				mTransferData.setNameAndValue("Code", "LP90");//通知书代码
				mTransferData.setNameAndValue("ClmNo",tLLUWSpecMasterSet.get(j).getClmNo()); //赔案号
				mTransferData.setNameAndValue("BatNo",tLLUWSpecMasterSet.get(j).getBatNo()); //批次号
				mTransferData.setNameAndValue("CustomerNo_I", tLLUWSpecMasterSet.get(j).getCustomerNo());//客户号
				mTransferData.setNameAndValue("CustomerNoType_I", "I"); //客户类型
				mTransferData.setNameAndValue("OldPrtSeq_I", mPrtSeqUW);//旧体检通知书流水号
				mLOPRTManagerSet.add(mLOPRTManagerSchema); //添加打印管理表
				tLLUWSpecMasterSet_falg = true;
//				mTransferData.setNameAndValue("",""); //印刷号
//				mTransferData.setNameAndValue("",""); //代理人编码
//				mTransferData.setNameAndValue("",""); //合同管理机构
//				mTransferData.setNameAndValue("",""); //合同销售渠道
			}
			
			
			
			if (mLLIssuePolSet_UWSend.size() > 0) {     //问题件集合添加数据
					this.mLLIssuePolSet.add(mLLIssuePolSet_UWSend);
				
			}
			
		
			
//			tSQL_spec = "select * from lluwspecmaster where contno="        
//					+ "(select contno from lccont where contno='" + "?mContNo?"
//					+ "') and  needprint='Y' "
//					+ " and clmno='" + "?mClmNo?" + "'" + " and batno='" + "?mBatNo?"
//					+ "'";
//			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();    //这步不太清楚是干吗的
//			sqlbv11.sql(tSQL_spec);
//			sqlbv11.put("mContNo", mContNo);
//			sqlbv11.put("mClmNo", mClmNo);
//			sqlbv11.put("mBatNo", mBatNo);
//			LLUWSpecMasterDB tLLUWSpecMasterDB = new LLUWSpecMasterDB();
//			LLUWSpecMasterSet sLLUWSpecMasterSet =tLLUWSpecMasterDB.executeQuery(sqlbv11);
//			for (int i = 1; i <= sLLUWSpecMasterSet.size(); i++) {
//				LLUWSpecMasterSchema tempLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
//				tempLLUWSpecMasterSchema.setSchema(sLLUWSpecMasterSet.get(i));
//				tempLLUWSpecMasterSchema.setPrtFlag("0");
//				this.mLLUWSpecMasterSet.add(tempLLUWSpecMasterSchema);
//			}

		}
		if (mUWSendFlag_UnPrint.equals("1")) {
			// 发送核保通知书
			String tLimit = PubFun.getNoLimit(mManageCom);
			mPrtSeqUW_UnPrint = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			logger.debug("---tLimit---" + tLimit);

			// 准备打印管理表数据
			mLOPRTManagerSchema = new LOPRTManagerSchema();
			mLOPRTManagerSchema.setPrtSeq(mPrtSeqUW_UnPrint);
			mLOPRTManagerSchema.setOtherNo(mContNo);
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_LPHB_UnPrint); // 核保通知书
																			// (
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
			mLOPRTManagerSchema.setStandbyFlag4(this.mClmNo);//增加clmno用于回销查询
			mLOPRTManagerSchema.setStandbyFlag5(this.mBatNo);//增加batno
			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqUW_UnPrint);
			mLOPRTManagerSchema.setForMakeDate(tDate);

			mLOPRTManagerSet.add(mLOPRTManagerSchema);
			for (int i = 1; i <= this.mLLIssuePolSet_UWSend_UnPrint.size(); i++) {
				this.mLLIssuePolSet_UWSend_UnPrint.get(i).setPrtSeq(
						mPrtSeqUW_UnPrint);
				this.mLLIssuePolSet_UWSend_UnPrint.get(i).setState("0");
			}
			if (mLLIssuePolSet_UWSend_UnPrint.size() > 0) {
				this.mLLIssuePolSet.add(mLLIssuePolSet_UWSend_UnPrint);
			}

		}
		// 业务员问题件
		// if (mSendOperFlag.equals("1")) {
		// String tLimit = PubFun.getNoLimit(mManageCom);
		// mPrtSeqOper = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		// mLOPRTManagerSchema = new LOPRTManagerSchema();
		// // 准备打印管理表数据
		// mLOPRTManagerSchema.setPrtSeq(mPrtSeqOper);
		// mLOPRTManagerSchema.setOtherNo(mContNo);
		// mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		// mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_AGEN_QUEST); //
		// 业务员通知书
		// mLOPRTManagerSchema.setManageCom(mLPContSchema.getManageCom());
		// mLOPRTManagerSchema.setAgentCode(mLPContSchema.getAgentCode());
		// mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		// mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		// // mLOPRTManagerSchema.setExeCom();
		// // mLOPRTManagerSchema.setExeOperator();
		// mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		// mLOPRTManagerSchema.setStateFlag("0");
		// mLOPRTManagerSchema.setPatchFlag("0");
		// mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		// mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		// // mLOPRTManagerSchema.setDoneDate() ;
		// // mLOPRTManagerSchema.setDoneTime();
		// mLOPRTManagerSchema.setStandbyFlag1(mLPContSchema.getAppntNo()); //
		// 投保人编码
		// mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
		// mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqOper);
		//
		// mLOPRTManagerSet.add(mLOPRTManagerSchema);
		// for (int i = 1; i <= this.mLCIssuePolSet_SendOper.size(); i++) {
		// this.mLCIssuePolSet_SendOper.get(i).setPrtSeq(mPrtSeqOper);
		// this.mLCIssuePolSet_SendOper.get(i).setState("0");
		// }
		// if (mLCIssuePolSet_SendOper.size() > 0) {
		// this.mLCIssuePolSet.add(mLCIssuePolSet_SendOper);
		// }
		// }
		// 判断机构问题件
		// if (this.mQuesOrgFlag.equals("1")) {
		// String tLimit = PubFun.getNoLimit(mManageCom);
		// mPrtSeqCom = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		// for (int i = 1; i <= this.mLCIssuePolSet_QuesOrg.size(); i++) {
		// this.mLCIssuePolSet_QuesOrg.get(i).setState("0");
		// this.mLCIssuePolSet_QuesOrg.get(i).setPrtSeq(mPrtSeqCom);
		//
		// }
		// if (mLCIssuePolSet_QuesOrg.size() > 0) {
		// this.mLCIssuePolSet.add(mLCIssuePolSet_QuesOrg);
		// }
		// }
		// 查询是否有问题件修改岗的问题件,如果有的话,将状态设置为0
		// String tsql = "select * from lcissuepol where 1=1" +
		// " and Contno = '"
		// + mContNo + "' and backobjtype = '1' and state is null ";
		// LCIssuePolSet tempLCIssuePolSet = new LCIssuePolSet();
		// LCIssuePolDB tempLCIssuePolDB = new LCIssuePolDB();
		//
		// tempLCIssuePolSet = tempLCIssuePolDB.executeQuery(tsql);
		// for (int i = 1; i <= tempLCIssuePolSet.size(); i++) {
		// tempLCIssuePolSet.get(i).setState("0");// 设置为已发放
		// tempLCIssuePolSet.get(i).setModifyDate(PubFun.getCurrentDate());
		// tempLCIssuePolSet.get(i).setModifyTime(PubFun.getCurrentTime());
		// this.mLCIssuePolSet.add(tempLCIssuePolSet.get(i));
		// }

		// ln 2008-10-07 add
		// if (mSendReportNoticeFlag.equals("1")) {
		// // 发送生调通知书
		// // 生调通知书处理
		// if(this.mLPRReportSet_org.size()>0) {
		// this.mLPRReportSet_org.get(1).setReplyFlag("0");//已发送
		// this.mLPRReportSet_org.get(1).setModifyDate(PubFun.getCurrentDate());
		// this.mLPRReportSet_org.get(1).setModifyTime(PubFun.getCurrentTime());
		// this.mLPRReportSet.add(mLPRReportSet_org);
		//				
		// // 准备打印管理表数据
		// mLOPRTManagerSchema = new LOPRTManagerSchema();
		//mLOPRTManagerSchema.setPrtSeq(this.mLPRReportSet_org.get(1).getPrtSeq(
		// ));
		// mLOPRTManagerSchema.setOtherNo(mContNo);
		// mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		// mLOPRTManagerSchema.setCode("BQ"+PrintManagerBL.CODE_PE); //
		// 暂定BQ+PrintManagerBL.CODE_PE:BQ03为保全生调
		// mLOPRTManagerSchema.setManageCom(tManageCom);
		// mLOPRTManagerSchema.setAgentCode(mAgentCode);
		// mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		// mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		// // mLOPRTManagerSchema.setExeCom();
		// // mLOPRTManagerSchema.setExeOperator();
		// mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		// mLOPRTManagerSchema.setStateFlag("0");
		// mLOPRTManagerSchema.setPatchFlag("0");
		// mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		// mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		// // mLOPRTManagerSchema.setDoneDate() ;
		// // mLOPRTManagerSchema.setDoneTime();
		// mLOPRTManagerSchema.setStandbyFlag1(mCustomerNo); // 被保险人编码
		// mLOPRTManagerSchema.setStandbyFlag2(mPrtNo);
		// mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
		// mLOPRTManagerSchema.setOldPrtSeq(this.mLPRReportSet_org.get(1).
		// getPrtSeq());
		// mLOPRTManagerSchema.setForMakeDate(tDate);
		//				
		// mLOPRTManagerSet.add(mLOPRTManagerSchema);
		//				
		// mTransferData
		// .setNameAndValue("PrtSeq",
		// this.mLPRReportSet_org.get(1).getPrtSeq());
		// mTransferData
		// .setNameAndValue("SaleChnl", mSaleChnl);
		// }
		// else
		// mSendReportNoticeFlag = "0";
		// }

		// ln 2008-10-29 add
		if (mSendPENoticeFlag.equals("1")) {
			// 发送体检通知书
			for (int i = 1; i <= mLLUWPENoticeSet.size(); i++) {
				// 更新体检表
				mLLUWPENoticeSet.get(i).setPrintFlag("0");// 已发送
				mLLUWPENoticeSet.get(i).setModifyDate(PubFun.getCurrentDate());
				mLLUWPENoticeSet.get(i).setModifyTime(PubFun.getCurrentTime());

				// 准备打印管理表数据
				mLOPRTManagerSchema = new LOPRTManagerSchema();
				mLOPRTManagerSchema.setPrtSeq(mLLUWPENoticeSet.get(i)
						.getPrtSeq());
				mLOPRTManagerSchema.setOtherNo(mContNo);
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_LPPE); // 理赔体检
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
				mLOPRTManagerSchema.setStandbyFlag1(mLLUWPENoticeSet.get(i).getCustomerNo()); // 接收对象编码
				mLOPRTManagerSchema.setStandbyFlag2(mPrtNo);
				mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
				mLOPRTManagerSchema.setStandbyFlag4(this.mClmNo);//增加clmno用于回销查询
				mLOPRTManagerSchema.setStandbyFlag5(this.mBatNo);//增加batno
				mLOPRTManagerSchema.setOldPrtSeq(mLLUWPENoticeSet.get(i)
						.getPrtSeq());
				mLOPRTManagerSchema.setForMakeDate(tDate);

				mLOPRTManagerSet.add(mLOPRTManagerSchema);

				mTransferData.setNameAndValue("PrtSeqPE", mLLUWPENoticeSet.get(
						i).getPrtSeq());
				mTransferData.setNameAndValue("OldPrtSeq", mLLUWPENoticeSet
						.get(i).getPrtSeq());
				mTransferData.setNameAndValue("SaleChnl", mSaleChnl);
			}
		}

		if (mImpartQuestFlag.equals("1")) {
			mLOPRTManagerSchema = new LOPRTManagerSchema();
			String iPrtSeq = PubFun1.CreateMaxNo("ImpartQuest", "20");
			mLOPRTManagerSchema.setPrtSeq(iPrtSeq);
			mLOPRTManagerSchema.setOtherNo(mContNo);
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_LPIQ); // 理赔体检
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
//			mLOPRTManagerSchema.setStandbyFlag1(mCustomerNo); // 接收对象编码
			mLOPRTManagerSchema.setStandbyFlag2(mPrtNo);
			mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
			mLOPRTManagerSchema.setStandbyFlag4(this.mClmNo);//增加clmno用于回销查询
			mLOPRTManagerSchema.setStandbyFlag5(this.mBatNo);//增加batno
			mLOPRTManagerSchema.setOldPrtSeq(iPrtSeq);
			mLOPRTManagerSchema.setForMakeDate(tDate);

			mLOPRTManagerSet.add(mLOPRTManagerSchema);
			mTransferData.setNameAndValue("PrtSeqI", iPrtSeq);
			mTransferData.setNameAndValue("OldPrtSeqI", iPrtSeq);
			mTransferData.setNameAndValue("SaleChnlI", mSaleChnl);
			mTransferData.setNameAndValue("AgentCodeI", mAgentCode);
			mTransferData.setNameAndValue("CodeI", PrintManagerBL.CODE_LPIQ);
			mTransferData.setNameAndValue("ManageComI", tManageCom);
//			mTransferData.setNameAndValue("CustomerNoI", mCustomerNo);
//			mTransferData.setNameAndValue("CustomerNoTypeI", mCustomerNoType);
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
		mTransferData.setNameAndValue("ImpartQuestFlag", mImpartQuestFlag);
		mTransferData.setNameAndValue("ApproveModifyFlag", mApproveModifyFlag);
		if (this.mApproveModifyFlag.equals("1")) {
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
		mTransferData.setNameAndValue("AgentCode", mAgentCode);
		mTransferData.setNameAndValue("AgentGroup", tLAAgentSet.get(1)
				.getAgentGroup());
		mTransferData.setNameAndValue("BranchAttr", tLABranchGroupSet.get(1)
				.getBranchAttr());
		mTransferData.setNameAndValue("ManageCom", tManageCom);
		mTransferData.setNameAndValue("PrtNo", mPrtNo);
		mTransferData.setNameAndValue("PrtSeqUW", mPrtSeqUW);
		mTransferData.setNameAndValue("PrtSeqUW_UnPrint", mPrtSeqUW_UnPrint);
		mTransferData.setNameAndValue("PrtSeqOper", mPrtSeqOper);
		mTransferData.setNameAndValue("PrtSeqCom", mPrtSeqCom);
		mTransferData.setNameAndValue("SendReportNoticeFlag",
				mSendReportNoticeFlag);
		mTransferData.setNameAndValue("SendPENoticeFlag", mSendPENoticeFlag);// 2008
																				// -
																				// 10
																				// -
																				// 29
																				// add
																				// ln
																				// 是否有带发放体检通知书标志
		mTransferData.setNameAndValue("AppntNo", mLCContSchema.getAppntNo());// 2008
																				// -
																				// 10
																				// -
																				// 29
																				// add
																				// ln
																				// 是否有带发放体检通知书标志
		mTransferData.setNameAndValue("SaleChnl", mLCContSchema.getSaleChnl());// 2008
																				// -
																				// 10
																				// -
																				// 29
																				// add
																				// ln
																				// 是否有带发放体检通知书标志

		mTransferData.setNameAndValue("AppntName", mAppntName);
		// mTransferData.setNameAndValue("ApproveDate", mLPContSchema
		// .getApproveDate());
//		mTransferData.setNameAndValue("CustomerNo", this.mCustomerNo);// 打印核保通知书用
//																		// --客户号
//		mTransferData.setNameAndValue("CustomerNoType", this.mCustomerNoType);
		mTransferData.setNameAndValue("SpecFlag", this.mSpecFlag);// 是否有特约标记
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

		// map.put(mLPContSchema, "UPDATE");
		// map.put(mLPPolSet, "UPDATE");
		// map.put(mLPCUWMasterSchema, "UPDATE");
		map.put(mLLIssuePolSet, "UPDATE");
		// map.put(mLPCUWSubSet, "INSERT");
		// map.put(mLPIndUWMasterSchema, "UPDATE");
		// map.put(mLPIndUWSubSet, "INSERT");//新增被保人核保记录 2008-12-5 add ln
		// map.put(mLPUWMasterSet, "UPDATE");
		// map.put(mLPUWSubSet, "INSERT");
		map.put(mLOPRTManagerSet, "INSERT");
		map.put(this.mLLUWSpecMasterSet, "UPDATE");
		// map.put(this.mLPRReportSet, "UPDATE");
		map.put(mLLUWPENoticeSet, "UPDATE");// 修改体检录入表 2008-10-29 add ln
		// 2009-06-29  没有核保、体检，不改lwmission的状态
		if ((!mUWSendFlag.equals("1")) && (!mSendPENoticeFlag.equals("1"))) {
		} else {
			map.put(mLLClaimSchema, "UPDATE");
		}
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
