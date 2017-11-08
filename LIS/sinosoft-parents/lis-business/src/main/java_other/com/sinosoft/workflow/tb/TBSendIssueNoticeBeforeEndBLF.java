package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

/**
 * <p>Title: 新契约外部问题件发放 </p>
 * <p>Description:在复核,问题件修改,外包抽检件或者外包异常件处理完毕之后,判断是否直接下发问题件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SinoSoft</p>
 * @author tongmeng
 * @version 1.0
 */

import java.util.Date;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.BeforeEndService;

public class TBSendIssueNoticeBeforeEndBLF implements BeforeEndService {
private static Logger logger = Logger.getLogger(TBSendIssueNoticeBeforeEndBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 核保主表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();

	/** 核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();

	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCIssuePolSet mLCIssuePolSet = new LCIssuePolSet();
	private LCSpecSet mLCSpecSet = new LCSpecSet();// 特约通知数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mPrtSeq;
	private String mPrtSeqUW = "0";
	private String mPrtSeqUW_UnPrint = "0";
	private String mPrtSeqOper = "0";
	private String mPrtSeqCom = "";

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	private String mUWFlag = ""; // 核保标志

	/** 工作流扭转标志 */
	private String mUWSendFlag = "";// 发送打印核保通知书标志
	private String mUWSendFlag_UnPrint = "";// 发送不打印核保通知书标志
	private String mSendOperFlag = "";// 发送业务员通知书标志
	private String mQuesOrgFlag = "";// 发送机构问题件标志
	private String mApproveModifyFlag = "";// 发送问题件修改岗标志
	private String mState = "";// 发送问题件修改岗标志

	private LCIssuePolSet mLCIssuePolSet_UWSend = new LCIssuePolSet();// 核保通知书问题件集合
	private LCIssuePolSet mLCIssuePolSet_SendOper = new LCIssuePolSet();// 业务员通知书问题件集合

	private LCIssuePolSet mLCIssuePolSet_QuesOrg = new LCIssuePolSet();// 机构问题件集合
	private LCIssuePolSet mLCIssuePolSet_ApproveModify = new LCIssuePolSet();// 问题件修改岗问题件集合

	public TBSendIssueNoticeBeforeEndBLF() {
	}

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
		// 没有未回复的问题件则不能发核保通知书
		//tongmeng 2009-05-18 modify
		//所有问题件都支持是否下发标记
		String strSql = "select count(1) from lcissuepol where 1=1"
				+ " and Contno = '"
				+ "?Contno?"
				+ "' "
				+ " and state is null "
				// 判断有没有发放给业务员问题件
				+ " and ((backobjtype = '3' and replyresult is null and needprint = 'Y' and prtseq is null) "
				// 判断有没有发放给保户的问题件
				+ " or (backobjtype = '2' and replyresult is null and needprint = 'Y' and prtseq is null) "
				// 判断有没有发放给问题件修改岗的问题件
				+ " or (backobjtype = '1' and replyresult is null and needprint = 'Y' ) "
				// 判断有没有发放给机构的问题件
				+ " or (backobjtype = '4' and replyresult is null and needprint = 'Y' )) ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("Contno", mContNo);
		ExeSQL tExeSQL = new ExeSQL();
		int rs = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
		if (rs > 0) {
			flag = 1;
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
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据失败!");
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中ContNo失败!");
			return false;
		}

		// 获得业务核保通知数据

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 准备险种信息
		if (prepareCont() == false)
			return false;
		// 选择工作流流转活动
		if (chooseActivity() == false)
			return false;
		// 准备打印管理表信息
		if (preparePrt() == false)
			return false;

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
			tLCCUWSubSchema.setUWNo(m); // 第几次核保
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
				this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWSendNoticeAfterInitService";
				tError.functionName = "prepareAllUW";
				tError.errorMessage = "LCUWMaster表取数失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCUWMasterSchema.setSchema(tLCUWMasterDB);

			// tLCUWMasterSchema.setUWNo(tLCUWMasterSchema.getUWNo()+1);核保主表中的UWNo表示该投保单经过几次人工核保(等价于经过几次自动核保次数),而不是人工核保结论(包括核保通知书,上报等)下过几次.所以将其注释.sxy-2003-09-19
			tLCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

			mLCUWMasterSet.add(tLCUWMasterSchema);

			// 核保轨迹表
			tLCUWSubSchema = new LCUWSubSchema();
			tLCUWSubDB = new LCUWSubDB();
			tLCUWSubSet = new LCUWSubSet();
			tLCUWSubDB.setContNo(tLCPolSchema.getContNo());
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
				tLCUWSubSchema.setUWNo(m); // 第几次核保
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
		mSendOperFlag = "0"; // 打印业务员通知书标志
		mQuesOrgFlag = "0"; // 机构问题件标志
		mApproveModifyFlag = "0"; // 复核修改标志
		//mState = "0"; // 复核修改标志 标志是否有出问题件修改除外的其他返回对象
		//tongmeng 20090414 modify
		//1-问题件已回复,2问题件未回复
		mState = "1";

		// backobjtype = '2' 为发送给保户的核保通知书
		// 查询
		tsql = "select * from lcissuepol where 1=1"
				+ " and Contno = '"
				+ "?Contno?"
				+ "' and backobjtype = '2' and replyresult is null and needprint = 'Y'"
				+ " and prtseq is null ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tsql);
		sqlbv1.put("Contno",mContNo);
		logger.debug("tSql==" + tsql);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv1);
		if (tLCIssuePolSet.size() > 0) {
			mUWSendFlag = "1";
			// 只要有发放给保户的问题件,就不做通知书发放动作.
			mSendOperFlag = "0";
			mQuesOrgFlag = "0";
			mApproveModifyFlag = "0";
			return true;
		}

		// 查询发送给业务员通知书
		tsql = "select * from lcissuepol where 1=1" + " and Contno = '"
				+ "?Contno?" + "' and backobjtype = '3' and prtseq is null"
				+ " and needprint = 'Y' ";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tsql);
		sqlbv2.put("Contno", mContNo);
		tLCIssuePolSet = new LCIssuePolSet();
		logger.debug("tSql==" + tsql);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv2);
		if (tLCIssuePolSet.size() > 0) {
//			mSendOperFlag = "1";
//			this.mLCIssuePolSet_SendOper.add(tLCIssuePolSet);
		}
		// 查询发送给机构的问题件
		
		tsql = "select * from lcissuepol where 1=1" + " and Contno = '"
				+ "?Contno?" + "' and backobjtype = '4' and replyresult is null and prtseq is null"
				+ " and needprint = 'Y' ";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tsql);
		sqlbv3.put("Contno", mContNo);
		tLCIssuePolSet = new LCIssuePolSet();
		logger.debug("tSql==" + tsql);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv3);
		if(tLCIssuePolSet.size()>0){
			mQuesOrgFlag = "1";
			this.mLCIssuePolSet_QuesOrg.add(tLCIssuePolSet);
		}
		// 查询发送给问题件修改岗的问题件
		//tongmeng 2009-05-18 modify
		//所有问题件都支持是否下发
		tsql = "select * from lcissuepol where 1=1"
				+ " and Contno = '"
				+ "?Contno?"
				+ "' and backobjtype = '1' and state is null and replyresult is null "
				+ " and needprint = 'Y' ";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tsql);
		sqlbv4.put("Contno", mContNo);
		logger.debug("tSql==" + tsql);
		tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv4);
//		if (tLCIssuePolSet.size() > 0 && mSendOperFlag.equals("0")
//				&& mUWSendFlag.equals("0") && mQuesOrgFlag.equals("0")) {
//			mApproveModifyFlag = "1";
//		}
		if(tLCIssuePolSet.size()>0&&!mUWSendFlag.equals("1")&&!mApproveModifyFlag.equals("1"))
		{
			mApproveModifyFlag = "1";
			if(mSendOperFlag.equals("0")&&mQuesOrgFlag.equals("0"))
			{
				mState = "1";
			}
		}
		//现改为只要包含操作员问题件情况下都为问题件修改建立节点 add by liuqh 2008-10-27
//		if (tLCIssuePolSet.size() > 0) {
//			}
		//建立问题件修改节点，但是当包含业务员和管理机构时，其问题件修改不能操作 
//		if(mSendOperFlag.equals("1")||mQuesOrgFlag.equals("1")){
		
		//SYY BEGIN 复核完毕发机构问题件时，不同时产生问题件修改岗工作流
		
//		if(mQuesOrgFlag.equals("1")){
//			mApproveModifyFlag ="1";
//			mState = "2";
//		}
		//SYY END
		
		//2010-4-20  保监会服务规范需求  业务员通知书由核保下发
		mSendOperFlag="0";
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
			CError.buildErr(this, "没有描述催发间隔!");
			return false;
		}
		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);
		// tongmeng 2007-11-12 modify
		// 需要重新初始化mLOPRTManagerSchema
		// 业务员问题件
		if (mSendOperFlag.equals("1")) {
			//String tLimit = PubFun.getNoLimit(mManageCom);
			//mPrtSeqOper = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
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
			}
			if (mLCIssuePolSet_SendOper.size() > 0) {
				this.mLCIssuePolSet.add(mLCIssuePolSet_SendOper);
			}
		}
		// 判断机构问题件
		if (this.mQuesOrgFlag.equals("1")) {
			String tLimit = PubFun.getNoLimit(mManageCom);
			//mPrtSeqCom = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			mPrtSeqCom = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_ISSUE); 
			for (int i = 1; i <= this.mLCIssuePolSet_QuesOrg.size(); i++) {
				this.mLCIssuePolSet_QuesOrg.get(i).setState("0");
				this.mLCIssuePolSet_QuesOrg.get(i).setPrtSeq(mPrtSeqCom);
				this.mLCIssuePolSet_QuesOrg.get(i).setSendDate(PubFun.getCurrentDate());
				this.mLCIssuePolSet_QuesOrg.get(i).setSendTime(PubFun.getCurrentTime());
				

			}
			if (mLCIssuePolSet_QuesOrg.size() > 0) {
				this.mLCIssuePolSet.add(mLCIssuePolSet_QuesOrg);
			}
		}
		// 查询是否有问题件修改岗的问题件,如果有的话,将状态设置为0
		// and needprint = 'Y' 
		//tongmeng 2009-05-18 modify
		//所有问题件都支持是否下发
		String tsql = "select * from lcissuepol where 1=1" + " and Contno = '"
				+ "?Contno?" + "' and backobjtype = '1' and state is null and needprint = 'Y' ";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tsql);
		sqlbv5.put("Contno", mContNo);
		LCIssuePolSet tempLCIssuePolSet = new LCIssuePolSet();
		LCIssuePolDB tempLCIssuePolDB = new LCIssuePolDB();

		tempLCIssuePolSet = tempLCIssuePolDB.executeQuery(sqlbv5);
		for (int i = 1; i <= tempLCIssuePolSet.size(); i++) {
			tempLCIssuePolSet.get(i).setState("0");// 设置为已发放
			tempLCIssuePolSet.get(i).setModifyDate(PubFun.getCurrentDate());
			tempLCIssuePolSet.get(i).setModifyTime(PubFun.getCurrentTime());
			tempLCIssuePolSet.get(i).setSendDate(PubFun.getCurrentDate());
			tempLCIssuePolSet.get(i).setSendTime(PubFun.getCurrentTime());
			this.mLCIssuePolSet.add(tempLCIssuePolSet.get(i));
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
		// mTransferData.setNameAndValue("UWSendFlag_UnPrint",
		// mUWSendFlag_UnPrint); //打印核保通知书非打印类标志
		mTransferData.setNameAndValue("QuesOrgFlag", mQuesOrgFlag);
		mTransferData.setNameAndValue("ApproveModifyFlag", mApproveModifyFlag);
		//mTransferData.setNameAndValue("State", mState);//判断问题件是否能够操作 add by liuqh 2008-10-27
		// 如果需要发放问题件,那么将IssueFlag设置为空
		if (mSendOperFlag.equals("1") || mQuesOrgFlag.equals("1")
				|| mApproveModifyFlag.equals("1")) {
			mTransferData.removeByName("IssueFlag");
			mTransferData.setNameAndValue("IssueFlag", "");

			// 问题件修改完毕调用时,需要先清除OtherNoticeFlag,之后重新设置.
			mTransferData.removeByName("OtherNoticeFlag");
			mTransferData.setNameAndValue("OtherNoticeFlag", "");
			// OtherNoticeFlag
		} else {
			mTransferData.setNameAndValue("IssueFlag", "0");
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
		/*
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
				*/
		mTransferData.removeByName("ManageCom");
		mTransferData.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("PrtSeqUW", mPrtSeqUW);
		mTransferData.setNameAndValue("PrtSeqUW_UnPrint", mPrtSeqUW_UnPrint);
		mTransferData.setNameAndValue("PrtSeqOper", mPrtSeqOper);
		mTransferData.setNameAndValue("PrtSeqCom", mPrtSeqCom);
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		mTransferData.setNameAndValue("ApproveDate", mLCContSchema
				.getApproveDate());
		mTransferData.removeByName("State");
		mTransferData.setNameAndValue("State",this.mState);
		if(this.mState.equals("1"))
		{
			mTransferData.setNameAndValue("ReplyDate",PubFun.getCurrentDate());
			mTransferData.setNameAndValue("ReplyTime",PubFun.getCurrentTime());
		}
		
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

		// map.put(mLCContSchema, "UPDATE");
		// map.put(mLCPolSet, "UPDATE");
		// map.put(mLCCUWMasterSchema, "UPDATE");
		map.put(mLCIssuePolSet, "UPDATE");
		// map.put(mLCCUWSubSet, "INSERT");
		// map.put(mLCUWMasterSet, "UPDATE");
		// map.put(mLCUWSubSet, "INSERT");
		map.put(mLOPRTManagerSet, "INSERT");
		// map.put(this.mLCSpecSet, "UPDATE");
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
