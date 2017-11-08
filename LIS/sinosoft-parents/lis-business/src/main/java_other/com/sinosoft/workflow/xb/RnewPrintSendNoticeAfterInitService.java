package com.sinosoft.workflow.xb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LMCertifySubDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.RnewIssuePolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.RnewIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:工作流节点任务:打印核保通知书
 * </p>
 * <p>
 * Description:续保人工核保发送核保通知书服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class RnewPrintSendNoticeAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(RnewPrintSendNoticeAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 核保主表 */
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mPrtSeq;
	private String mCode;
	private boolean mPatchFlag;
	private boolean mAutoSysCertSendOutFlag = true;

	private String mOpearte = "";
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	// tongmeng 2007-11-26 add
	// 增加对问题件的状态字段的维护
	private RnewIssuePolSet mRnewIssuePolSet = new RnewIssuePolSet();
	private LCSpecSet mLCSpecSet = new LCSpecSet();// 特约通知书
	/** 单证发放表 */
	private LZSysCertifySchema mLZSysCertifySchema = new LZSysCertifySchema();
	private LWMissionSet mLWMissionSet = new LWMissionSet();

	public RnewPrintSendNoticeAfterInitService() {
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
		this.mOpearte = cOperate;
		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");

		// mResult.clear();
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mLWMissionSet, "UPDATE");
		// 添加核保通知书打印管理表数据
		map.put(mLOPRTManagerSchema, "UPDATE");

		map.put(mLCUWMasterSet, "UPDATE");

		// 添加续保核保通知书自动发放表数据
		if (mAutoSysCertSendOutFlag == true) {
			map.put(mLZSysCertifySchema, "INSERT");
		}
		map.put(this.mRnewIssuePolSet, "UPDATE");
		map.put(this.mLCSpecSet, "UPDATE");
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PrintSendNoticeAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(mCode); // 核保通知书
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		tLOPRTManagerDB.setOtherNo(mContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PrintSendNoticeAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PrintSendNoticeAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中没有处于未打印状态的核保通知书!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLOPRTManagerSchema = tLOPRTManagerSet.get(1);
		if (mLOPRTManagerSchema.getPatchFlag() == null)
			mPatchFlag = false;
		else if (mLOPRTManagerSchema.getPatchFlag().equals("0"))
			mPatchFlag = false;
		else if (mLOPRTManagerSchema.getPatchFlag().equals("1"))
			mPatchFlag = true;
		// // 查找打印服务
		// LDCodeDB tLDCodeDB = new LDCodeDB();
		// tLDCodeDB.setCode( mCode);//打印核保通知书
		// tLDCodeDB.setCodeType("print_service");
		// tLDCodeDB.setOtherSign("0") ;
		// LDCodeSet tLDCodeSet = new LDCodeSet();
		// tLDCodeSet = tLDCodeDB.query() ;
		// if( tLDCodeSet.size() !=1 )
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "PrintSendNoticeAfterInitService";
		// tError.functionName = "preparePrint";
		// tError.errorMessage = "在LDCode中打印服务记录丢失!";
		// this.mErrors.addOneError(tError) ;
		// return false;
		// }
		String tSQL = "select * from RnewIssuePol where contno='" + "?contno?"
				+ "' and prtseq='" + "?mPrtSeq?" + "' ";
		if (mCode.equals("45")) {
			// 打印类核保通知书
			tSQL = tSQL + " and standbyflag2='Y' ";
		} else if (mCode.equals("TB90")) {
			// 打印类核保通知书
			tSQL = tSQL + " and standbyflag2='N' ";
		}
		RnewIssuePolDB tRnewIssuePolDB = new RnewIssuePolDB();
		// RnewIssuePolSet tRnewIssuePolSet = new RnewIssuePolSet();
		// tRnewIssuePolSet =
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		sqlbv1.put("contno", this.mContNo);
		sqlbv1.put("mPrtSeq", mPrtSeq);
		this.mRnewIssuePolSet.add(tRnewIssuePolDB.executeQuery(sqlbv1));
		// 增加对特约的维护
		if (mCode.equals("45")) {
			String tSQL_spec = "select * from lcspec where contno='"
					+ "?contno?" + "' and prtseq='"
					+ "?prtseq?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL_spec);
			sqlbv2.put("contno", this.mContNo);
			sqlbv2.put("prtseq", mLOPRTManagerSchema.getPrtSeq());
			LCSpecDB tLCSpecDB = new LCSpecDB();
			LCSpecSet tLCSpecSet = new LCSpecSet();
			tLCSpecSet = tLCSpecDB.executeQuery(sqlbv2);
			for (int i = 1; i <= tLCSpecSet.size(); i++) {
				LCSpecSchema tempLCSpecSchema = new LCSpecSchema();
				tempLCSpecSchema.setSchema(tLCSpecSet.get(i));
				this.mLCSpecSet.add(tempLCSpecSchema);
			}
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
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintSendNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintSendNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWSendNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintSendNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintSendNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintSendNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务处理数据
		mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
		if (mPrtSeq == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintSendNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务处理数据
		mCode = (String) mTransferData.getValueByName("Code");
		if (mCode == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PrintSendNoticeAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保生调信息
		if (preparePrintSendNotice() == false)
			return false;
		// 准备险种核保表
		if (preparePolUW() == false)
			return false;
		// 打印队列
		if (preparePrint() == false)
			return false;

		// 发放系统单证打印队列
		if (prepareAutoSysCertSendOut() == false)
			return false;
        //处理工作流信息
//		if(!dealMission())
//			return false;
		return true;
	}
	private boolean dealMission(){
		//给发送通知书任务置上操作员
		LWMissionSchema xLWMissionSchema = new LWMissionSchema();
		LWMissionDB xLWMissionDB = new LWMissionDB();
		LWMissionSet xLWMissionSet = new LWMissionSet();
		xLWMissionDB.setMissionID(mMissionID);
		//tLWMissionDB.setSubMissionID(mSubMissionID);
		xLWMissionDB.setActivityID("0000007004");
		xLWMissionSet=xLWMissionDB.query();
		if(xLWMissionSet.size()!=1){
//			if(!tLWMissionDB.getInfo()){
			CError.buildErr(this, "查询LWMission失败！");
			return false;
		}
		xLWMissionSchema = xLWMissionSet.get(1);
		xLWMissionSchema.setDefaultOperator(this.mGlobalInput.Operator);
		xLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		xLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
		mLWMissionSet.add(xLWMissionSchema);
		return true;
	}
	/**
	 * 准备险种核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePolUW() {
		// 准备险种合同表数据
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mContNo);
		tLCPolDB.setAppFlag("9");
		tLCPolDB.setRnewFlag("-1");
		mLCPolSet = tLCPolDB.query();

		mLCUWMasterSet.clear();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();

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
			if("1".equals(tLCUWMasterSchema.getAddPremFlag()))//如果有加费，则在打印核保通知书后将其加费打印标志修改为已发送，已打印，未回复
			{
				tLCUWMasterSchema.setAddPremFlag("2");
			}
			if("1".equals(tLCUWMasterSchema.getSpecFlag()))//如果有特约，则在打印核保通知书后将其打印标志修改为已发送，已打印，未回复
			{
			    tLCUWMasterSchema.setSpecFlag("2");
			}
			if("1".equals(tLCUWMasterSchema.getQuesFlag()))//如果有客户问题件，则在打印核保通知书后将其打印标志修改为已发送，已打印，未回复
			{
			    tLCUWMasterSchema.setQuesFlag("2");
			}
			
			tLCUWMasterSchema.setPrintFlag("2");//修改核保通知书打印标记，已打印，未回复

			mLCUWMasterSet.add(tLCUWMasterSchema);
		}
		return true;
	}

	/**
	 * 准备核保资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePrintSendNotice() {

		// //准备核保主表信息

		if (mLOPRTManagerSchema.getPatchFlag() == null)
			mPatchFlag = false;
		// tongmeng 2007-11-19 注释掉此处
		// 在check方法中已经做过处理了.
		/*
		 * else if (mLOPRTManagerSchema.getPatchFlag().equals("0")) mPatchFlag =
		 * false; else if (mLOPRTManagerSchema.getPatchFlag().equals("1"))
		 * mPatchFlag = true;
		 */
		return true;
	}

	/**
	 * 准备打印信息表
	 * 
	 * @return
	 */
	private boolean preparePrint() {
		// 准备打印管理表数据
		mLOPRTManagerSchema.setStateFlag("1");
		mLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setDoneTime(PubFun.getCurrentTime());
		mLOPRTManagerSchema.setExeCom(mManageCom);
		mLOPRTManagerSchema.setExeOperator(mOperater);

		// tongmeng 2007-11-26 add
		// 增加对问题件状态字段的维护
		for (int i = 1; i <= this.mRnewIssuePolSet.size(); i++) {
			mRnewIssuePolSet.get(i).setState("1");// 打印完毕
			mRnewIssuePolSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mRnewIssuePolSet.get(i).setModifyTime(PubFun.getCurrentTime());
		}
		// tongmeng 2007-12-27 add
		// 增加对特约的维护
		for (int i = 1; i <= this.mLCSpecSet.size(); i++) {
			mLCSpecSet.get(i).setPrtFlag("1");// 打印完毕
			mLCSpecSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLCSpecSet.get(i).setModifyTime(PubFun.getCurrentTime());
		}
		return true;
	}

	/**
	 * 准备核保资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareAutoSysCertSendOut() {
		// 判断打印过的单据是否需要自动发放
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("syscertifycode");
		tLDCodeDB.setCode(mCode);
		if (!tLDCodeDB.getInfo()) {
			mAutoSysCertSendOutFlag = false;
			return true;
		}

		LMCertifySubDB tLMCertifySubDB = new LMCertifySubDB();
		tLMCertifySubDB.setCertifyCode(tLDCodeDB.getCodeName());
		if (!tLMCertifySubDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMCertifySubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "AutoSysCertSendOutBL";
			tError.functionName = "JudgeAutoSend";
			tError.errorMessage = "查询信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tflag = tLMCertifySubDB.getAutoSend();
		if (StrTool.cTrim(tflag).equals("") || StrTool.cTrim(tflag).equals("N")) {
			mAutoSysCertSendOutFlag = false;
			return true;
		}

		// //准备自动发放表信息
		if (mAutoSysCertSendOutFlag == true) { // 需要自动发放，则更新数据状态
			LZSysCertifySchema tLZSysCertifyschema = new LZSysCertifySchema();
			tLZSysCertifyschema.setCertifyCode(tLDCodeDB.getCodeName());
			tLZSysCertifyschema.setCertifyNo(mPrtSeq);
			tLZSysCertifyschema.setSendOutCom("A" + mGlobalInput.ManageCom);
			tLZSysCertifyschema.setReceiveCom("D"
					+ mLOPRTManagerSchema.getAgentCode());
			logger.debug("D" + mLOPRTManagerSchema.getAgentCode());
			tLZSysCertifyschema.setHandler("SYS");
			tLZSysCertifyschema.setStateFlag("0");
			tLZSysCertifyschema.setHandleDate(PubFun.getCurrentDate());
			tLZSysCertifyschema.setTakeBackNo(PubFun1.CreateMaxNo("TAKEBACKNO",
					PubFun.getNoLimit(mManageCom.substring(1))));
			tLZSysCertifyschema.setSendNo(PubFun1.CreateMaxNo("TAKEBACKNO",
					PubFun.getNoLimit(mManageCom.substring(1))));
			tLZSysCertifyschema.setOperator(mOperater);
			tLZSysCertifyschema.setMakeDate(PubFun.getCurrentDate());
			tLZSysCertifyschema.setMakeTime(PubFun.getCurrentTime());
			tLZSysCertifyschema.setModifyDate(PubFun.getCurrentDate());
			tLZSysCertifyschema.setModifyTime(PubFun.getCurrentTime());
			mLZSysCertifySchema = tLZSysCertifyschema;
		}
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {

		mTransferData.setNameAndValue("CertifyCode", mLZSysCertifySchema
				.getCertifyCode());
		mTransferData.setNameAndValue("ValidDate", mLZSysCertifySchema
				.getValidDate());
		mTransferData.setNameAndValue("SendOutCom", mLZSysCertifySchema
				.getSendOutCom());
		mTransferData.setNameAndValue("ReceiveCom", mLZSysCertifySchema
				.getReceiveCom());
		mTransferData.setNameAndValue("Handler", mLZSysCertifySchema
				.getHandler());
		mTransferData.setNameAndValue("HandleDate", mLZSysCertifySchema
				.getHandleDate());
		mTransferData.setNameAndValue("Operator", mLZSysCertifySchema
				.getOperator());
		mTransferData.setNameAndValue("MakeDate", mLZSysCertifySchema
				.getMakeDate());
		mTransferData
				.setNameAndValue("SendNo", mLZSysCertifySchema.getSendNo());
		mTransferData.setNameAndValue("TakeBackNo", mLZSysCertifySchema
				.getTakeBackNo());
		// tongmeng 2007-11-22 modify
		// 增加通知书类型,在核保通知书回收的时候使用
		if (this.mOpearte.equals("0000007004")) {
			mTransferData.setNameAndValue("CodeType", "45");// 核保通知书打印类
		} 

		// 为补打核保通知书节点准备属性数据
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSet tLAAgentSet = new LAAgentSet();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		tLAAgentSet = tLAAgentDB.query();
		if (tLAAgentSet == null || tLAAgentSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLAAgentSet.get(1).getAgentGroup() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent中的代理机构数据丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
		tLABranchGroupDB.setAgentGroup(tLAAgentSet.get(1).getAgentGroup());
		tLABranchGroupSet = tLABranchGroupDB.query();
		if (tLABranchGroupSet == null || tLABranchGroupSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLABranchGroupSet.get(1).getBranchAttr() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup中展业机构信息丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mPatchFlag == true) {
			mTransferData.setNameAndValue("OldPrtSeq", mLOPRTManagerSchema
					.getOldPrtSeq());
		} else {
			mTransferData.setNameAndValue("OldPrtSeq", mLOPRTManagerSchema
					.getPrtSeq());
		}
		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		mTransferData.setNameAndValue("Code", PrintManagerBL.CODE_UW);
		mTransferData.setNameAndValue("DoneDate", mLOPRTManagerSchema
				.getDoneDate());
		mTransferData.setNameAndValue("ManageCom", mLOPRTManagerSchema
				.getManageCom());
		mTransferData.setNameAndValue("ExeOperator", mLOPRTManagerSchema
				.getExeOperator());

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
