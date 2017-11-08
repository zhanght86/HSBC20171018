package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.UWFBCal;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LockTableBL;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LDSysTraceSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LDSysTraceSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 工作流服务类:新契约核保订正
 * </p>
 * <p>
 * Description: 核保订正
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

public class ReManuUWAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(ReManuUWAfterInitService.class);
	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();

	/**
	 * 往界面传输数据的容器
	 */
	private VData mResult = new VData();

	/**
	 * 往工作流引擎中传输数据的容器
	 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	private TransferData mTransferData = new TransferData();

	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	/**
	 * 数据操作字符串
	 */
	private String mOperater;
	private String mManageCom;

	/**
	 * 业务数据操作字符串
	 */
	private String mContNo;
	private String mUWFlag;
	private String mBackUWGrade;
	private String mBackAppGrade;
	private String mOperator;
	private String mUWPopedom; // 核保级别
	private String mAppGrade; // 申请级别
	private String mMissionID;
	private String mSubMissionID;
	private String mUWIdea;
	private String reDistribute;

	/**
	 * 保单表
	 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCPolSet mLCPolSet = new LCPolSet();

	public ReManuUWAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

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

		// map.put("delete from loprtmanager where code = 'BF00' and otherno = '" +
		// mContNo + "'", "DELETE");
		map.put(mLCContSchema, "UPDATE");
		map.put(mLCPolSet, "UPDATE");
		map.put(mLCCUWMasterSet, "UPDATE");
		map.put(mLCCUWSubSet, "INSERT");
		map.put(mLCUWMasterSet, "UPDATE");
		map.put(mLCUWSubSet, "INSERT");
		if(mLOPRTManagerSchema!=null)
			map.put(mLOPRTManagerSchema, "UPDATE");
		
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验核保员级别
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperater);
		logger.debug("mOperate" + mOperater);
		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperater + "）";
			this.mErrors.addOneError(tError);
			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		mUWPopedom = tUWPopedom;
		mAppGrade = mUWPopedom;
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "ReManuUWAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

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
			CError tError = new CError();
			tError.moduleName = "ReManuUWAfterInitService";
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
			tError.moduleName = "ReManuUWAfterInitService";
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
			tError.moduleName = "ReManuUWAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ReManuUWAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 获得当前工作任务的任务ID
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ReManuUWAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的mCont
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ReManuUWAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mUWFlag = (String) mTransferData.getValueByName("UWFlag");
		if (mUWFlag == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ReManuUWAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中UWFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mUWIdea = ((String) mTransferData.getValueByName("UWIdea")).trim();
		if (mUWIdea == null || mUWIdea.equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ReManuUWAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "请录入核保订正原因!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		String tMissionId = (String) this.mTransferData
				.getValueByName("MissionID");
		if (tMissionId == null || tMissionId.equals("")) {
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "获得工作流编码失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		/*
		String tUpdateSQL = "select * from lwmission where missionid='"
				+ tMissionId + "' and activityid='0000001149'";
		LWMissionSet tLWMissionSet = new LWMissionSet();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionSet = tLWMissionDB.executeQuery(tUpdateSQL);
		if (tLWMissionSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询核保订正结点错误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		tLWMissionSchema.setSchema(tLWMissionSet.get(1));
		tLWMissionSchema.setDefaultOperator(mOperater);
		tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setSchema(tLWMissionSchema);
		if (!tLWMissionDB.update()) {
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = tLWMissionDB.mErrors.getFirstError();
			this.mErrors.addOneError(tError);
			return false;
		}
		*/

		mUWFlag = "z"; // 核保订正标志
		// 准备保单的复核标志
		mLCContSchema.setUWFlag(mUWFlag);
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLCContSchema.getContNo());
		mLCPolSet = tLCPolDB.query();
		// 准备险种保单的复核标志
		//tongmeng 2009-07-01 
		//核保订正不修改险种核保结论
		/*
		for (int i = 1; i <= mLCPolSet.size(); i++) {
			mLCPolSet.get(i).setUWFlag(mUWFlag);
		}*/

		// 准备合同复核表数据
		if (!prepareContUW()) {
			return false;
		}

		// 准备险种复核表数据
		if (!prepareAllUW()) {
			return false;
		}
		
		//2008-12-18 ln add
		// 准备把待打印的延期、拒保、撤单通知书打印状态置为已打印状态
		if (!prepareLOPRTManager()) {
			return false;
		}
		
		// 计算是否需要再保
		if (!CheckFB()) {
			reDistribute = "0"; // 不需要临分
		} else {
			reDistribute = "1"; // 需要临分
		}
		return true;
	}

	/**
	 * 计算是否需要临分
	 * 
	 * @return boolean
	 */
	private boolean CheckFB() {
		VData tVData = new VData();
		tVData.add(mLCContSchema);
		UWFBCal tUWFBCal = new UWFBCal();
		if (!tUWFBCal.submitData(tVData, "")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 准备主附险核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareAllUW() {
		mLCUWMasterSet.clear();
		mLCUWSubSet.clear();

		for (int i = 1; i <= mLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();

			tLCPolSchema = mLCPolSet.get(i);

			LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
			LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			tLCUWMasterDB.setPolNo(tLCPolSchema.getPolNo());
			LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
			tLCUWMasterSet = tLCUWMasterDB.query();
			if (tLCUWMasterDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWManuNormChkBL";
				tError.functionName = "prepareAllUW";
				tError.errorMessage = "LCUWMaster表取数失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			int n = tLCUWMasterSet.size();
			logger.debug("该投保单的核保主表当前记录条数:  " + n);
			if (n == 1) {
				tLCUWMasterSchema = tLCUWMasterSet.get(1);

				// 为核保订正回退保存核保级别和核保人
				mBackUWGrade = tLCUWMasterSchema.getUWGrade();
				mBackAppGrade = tLCUWMasterSchema.getAppGrade();
				mOperator = tLCUWMasterSchema.getOperator();

				// tLCUWMasterSchema.setUWNo(tLCUWMasterSchema.getUWNo()+1);核保主表中的UWNo表示该投保单经过几次人工核保(等价于经过几次自动核保次数),而不是人工核保结论(包括核保通知书,上报等)下过几次.所以将其注释.sxy-2003-09-19
				tLCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
				tLCUWMasterSchema.setState(mUWFlag);
				tLCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保
				tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

				// 恢复核保级别和核保员
				tLCUWMasterSchema.setUWGrade(mBackUWGrade);
				tLCUWMasterSchema.setAppGrade(mBackAppGrade);
				tLCUWMasterSchema.setOperator(mOperator);
				// 解锁
				LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
				tLDSysTraceSchema.setPolNo(mContNo);
				tLDSysTraceSchema.setCreatePos("人工核保");
				tLDSysTraceSchema.setPolState("1001");
				LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
				inLDSysTraceSet.add(tLDSysTraceSchema);

				VData tVData = new VData();
				tVData.add(mGlobalInput);
				tVData.add(inLDSysTraceSet);

				LockTableBL LockTableBL1 = new LockTableBL();
				if (!LockTableBL1.submitData(tVData, "DELETE")) {
					logger.debug("解锁失败！");
				}

			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWManuNormChkBL";
				tError.functionName = "prepareAllUW";
				tError.errorMessage = "LCUWMaster表取数据不唯一!";
				this.mErrors.addOneError(tError);
				return false;
			}

			mLCUWMasterSet.add(tLCUWMasterSchema);

			// 核保轨迹表			
			LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
			LCUWSubDB tLCUWSubDB = new LCUWSubDB();
			//tLCUWSubDB.setProposalNo(tLCPolSchema.getPolNo());
			LCUWSubSet tLCUWSubSet = new LCUWSubSet();
			String sql = "select * from lcuwsub where proposalno='"+"?proposalno?"+"' order by uwno desc ";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	        sqlbv1.sql(sql);
	        sqlbv1.put("proposalno", tLCPolSchema.getPolNo());
			tLCUWSubSet = tLCUWSubDB.executeQuery(sqlbv1);
			//if (tLCUWSubDB.mErrors.needDealError()) {
			if(tLCUWSubSet == null || tLCUWSubSet.size()<1){
				// @@错误处理
				//this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
				CError.buildErr(this, "LCUWSub表取数失败!") ;
				return false;
			}

			int m = tLCUWSubSet.get(1).getUWNo();
			logger.debug("subcount=" + m);
			if (m > 0) {
				m++; // 核保次数
				tLCUWSubSchema = new LCUWSubSchema();
				tLCUWSubSchema.setUWNo(m); // 第几次核保
				tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
				tLCUWSubSchema.setContNo(tLCUWMasterSchema.getContNo());
				tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
				tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema
						.getProposalContNo());
				tLCUWSubSchema.setPolNo(tLCUWMasterSchema.getPolNo());
				tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator());
				tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());

				tLCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
				tLCUWSubSchema.setUWGrade(mUWPopedom); // 核保级别
				tLCUWSubSchema.setAppGrade(mAppGrade); // 申请级别
				tLCUWSubSchema.setAutoUWFlag("2");
				tLCUWSubSchema.setState(mUWFlag);
				tLCUWSubSchema.setOperator(mOperater); // 操作员

				tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
				tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
				tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
				tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWManuNormChkBL";
				tError.functionName = "prepareAllUW";
				tError.errorMessage = "LCUWSub表取数失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCUWSubSet.add(tLCUWSubSchema);
		}
		return true;
	}
	
	/**
	 * 准备把待打印的延期、拒保、撤单通知书打印状态置为已打印状态
	 *  输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareLOPRTManager() 
	{		
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		String sql ="select * from LOPRTManager where 1=1 and otherno='"+"?otherno?"+"'"
			+ " and standbyflag7='TBJB' and stateflag='0'  and code in('00','06','09') ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(sql);
        sqlbv2.put("otherno", mLCContSchema.getPrtNo());
		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.executeQuery(sqlbv2);
		logger.debug(sql);
		if(tLOPRTManagerDB.mErrors.needDealError())
		{
			CError.buildErr(this, "LOPRTManager表查询出错！");
			return false;
		}
		if(tLOPRTManagerSet!=null && tLOPRTManagerSet.size()>0)
		{
			mLOPRTManagerSchema = tLOPRTManagerSet.get(1);
			mLOPRTManagerSchema.setStateFlag("1");
		}		
		
		return true;
	}

	/**
	 * 准备主附险核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareContUW() {
		mLCCUWMasterSet.clear();
		mLCCUWSubSet.clear();

		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mLCContSchema.getContNo());
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();
		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "prepareAllUW";
			tError.errorMessage = "LCCUWMaster表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int n = tLCCUWMasterSet.size();
		logger.debug("该投保单的核保主表当前记录条数:  " + n);
		if (n == 1) {
			tLCCUWMasterSchema = tLCCUWMasterSet.get(1);

			// 为核保订正回退保存核保级别和核保人
			mBackUWGrade = tLCCUWMasterSchema.getUWGrade();
			mBackAppGrade = tLCCUWMasterSchema.getAppGrade();
			mOperator = tLCCUWMasterSchema.getOperator();

			// tLCCUWMasterSchema.setUWNo(tLCCUWMasterSchema.getUWNo()+1);核保主表中的UWNo表示该投保单经过几次人工核保(等价于经过几次自动核保次数),而不是人工核保结论(包括核保通知书,上报等)下过几次.所以将其注释.sxy-2003-09-19
			tLCCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCCUWMasterSchema.setState(mUWFlag);
			tLCCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保
			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			tLCCUWMasterSchema.setUWIdea(mUWIdea); // 核保订正原因
			tLCCUWMasterSchema.setUWState("5");

			// 恢复核保级别和核保员
			tLCCUWMasterSchema.setUWGrade(mBackUWGrade);
			tLCCUWMasterSchema.setAppGrade(mBackAppGrade);
			tLCCUWMasterSchema.setOperator(mOperator);
			// 解锁
			LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
			tLDSysTraceSchema.setPolNo(mContNo);
			tLDSysTraceSchema.setCreatePos("人工核保");
			tLDSysTraceSchema.setPolState("1001");
			LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
			inLDSysTraceSet.add(tLDSysTraceSchema);

			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(inLDSysTraceSet);

			LockTableBL LockTableBL1 = new LockTableBL();
			if (!LockTableBL1.submitData(tVData, "DELETE")) {
				logger.debug("解锁失败！");
			}

		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "prepareAllUW";
			tError.errorMessage = "LCCUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCCUWMasterSet.add(tLCCUWMasterSchema);

		// 核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mLCContSchema.getContNo());
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "prepareAllUW";
			tError.errorMessage = "LCCUWSub表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int m = tLCCUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLCCUWSubSchema = new LCCUWSubSchema();
			tLCCUWSubSchema.setUWNo(m); // 第几次核保
			tLCCUWSubSchema.setContNo(tLCCUWMasterSchema.getContNo());
			tLCCUWSubSchema.setGrpContNo(tLCCUWMasterSchema.getGrpContNo());
			tLCCUWSubSchema.setProposalContNo(tLCCUWMasterSchema
					.getProposalContNo());
			tLCCUWSubSchema.setOperator(mOperater);

			tLCCUWSubSchema.setPassFlag(mUWFlag); // 核保意见
			tLCCUWSubSchema.setUWGrade(mUWPopedom); // 核保级别
			tLCCUWSubSchema.setAppGrade(mAppGrade); // 申请级别
			tLCCUWSubSchema.setAutoUWFlag("2");
			tLCCUWSubSchema.setState(mUWFlag);
			tLCCUWSubSchema.setUWIdea(mUWIdea);
			tLCCUWSubSchema.setOperator(mOperater); // 操作员
			tLCCUWSubSchema.setUWState("5");

			tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
			tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "prepareAllUW";
			tError.errorMessage = "LCCUWSub表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCCUWSubSet.add(tLCCUWSubSchema);

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSet tLAAgentSet = new LAAgentSet();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		tLAAgentSet = tLAAgentDB.query();
		if (tLAAgentSet == null || tLAAgentSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReManuUWAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLAAgentSet.get(1).getAgentGroup() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReManuUWAfterInitService";
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
			tError.moduleName = "ReManuUWAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLABranchGroupSet.get(1).getBranchAttr() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReManuUWAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup中展业机构信息丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		logger.debug("agentcode==" + mLCContSchema.getAgentCode());
		//tongmeng 2009-06-17 modify
		//管理机构设置为保单的管理机构
		
		//mTransferData.setNameAndValue("ManageCom", mManageCom);
		mTransferData.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		logger.debug("manageCom=" + mManageCom);
		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		logger.debug("prtNo==" + mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("ContNo", mLCContSchema.getContNo());
		logger.debug("ContNo==" + mLCContSchema.getContNo());
		mTransferData.setNameAndValue("AgentGroup", tLAAgentSet.get(1)
				.getAgentGroup());
		mTransferData
				.setNameAndValue("AgentName", tLAAgentSet.get(1).getName());
		mTransferData.setNameAndValue("AppntCode", mLCContSchema.getAppntNo());
		logger.debug("AppntName = " + mLCContSchema.getAppntName());
		mTransferData
				.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		// 上报类型
		mTransferData.setNameAndValue("ReportType", "0");
		// 分保标志
		mTransferData.setNameAndValue("ReDisMark", reDistribute); // add by
		// yaory
		// 分保标志
		// 1-分保0不分保
		// 承保日期
		mTransferData.setNameAndValue("PolApplyDate", mLCContSchema
				.getPolApplyDate());
		// 复核日期
		mTransferData.setNameAndValue("ApproveDate", mLCContSchema
				.getApproveDate());

		/*String tSql = " select missionprop12,missionprop8 from lwmission where 1 =1 "
				+ " and activityid = '0000001149' and missionid = '"
				+ mMissionID
				+ "'"
				+ " and submissionid = '"
				+ mSubMissionID
				+ "'";
		logger.debug("tSql:" + tSql);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(tSql);
		String tUWAuthority = tSSRS.GetText(1, 1);
		if (tUWAuthority == null || tUWAuthority.equals("")) {
			//tongmeng 2009-02-05 add
			//如果自核通过直接进入核保订正,查询不到核保级别,那么核保级别默认设置为当前核保师的核保级别.
			String tSQL = " select uwpopedom from lduwuser where uwtype='1' and usercode='"+this.mOperater+"' ";
			ExeSQL tempExeSQL = new ExeSQL();
			String tempUWAuthority = "";
			tempUWAuthority = tempExeSQL.getOneValue(tSQL);
			if(tempUWAuthority==null||tempUWAuthority.equals(""))
			{
				//如果没有设置成I
				tUWAuthority = "I";
			}
			else
			{
				tUWAuthority = tempUWAuthority;
			}
			
		}*/
		//2009-06-19 ln modify 由系统计算出核保级别
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSet tAllInsuredSet = new LCInsuredSet();
		tLCInsuredDB.setContNo(mContNo);
		tAllInsuredSet = tLCInsuredDB.query();
		String tUWGrade2="";//由系统计算出的核保级别
		String tempUWGrade="";//临时计算用的核保级别
		for(int a=1;a<=tAllInsuredSet.size();a++){
			tempUWGrade=getUWGrade(tAllInsuredSet.get(a).getInsuredNo());
			if(tUWGrade2.compareTo(tempUWGrade) < 0){
				tUWGrade2=tempUWGrade;
			}
		}
		logger.debug("执行getUWGrade函数由SQL算出的核保级别是："+tUWGrade2);		
		String tUWAuthority=tUWGrade2;//将最高的核保级别返回给服务类
		logger.debug("最终的核保级别是："+tUWAuthority);
		
		//tongmeng 2008-12-17 add
		//记录上次核保操作员工号
		
		mTransferData.setNameAndValue("LastUserCode", this.mTransferData.getValueByName("LastUserCode"));
		mTransferData.setNameAndValue("UWAuthority", tUWAuthority);
		// 核保订正后返回到公共池
		mTransferData.setNameAndValue("UserCode", "0000000000");
		mTransferData.setNameAndValue("DefaultOperator", null);
		// 核保员代码3
		/*
		 * if (tSSRS.GetText(1, 2) == null || tSSRS.GetText(1, 2).equals("")) {
		 * mTransferData.setNameAndValue("UserCode", "0000000000"); } else {
		 * mTransferData.setNameAndValue("UserCode", tSSRS.GetText(1, 2)); }
		 */
		// tongmeng 2007-12-03 add
		// 增加扫描时间和核保状态
		String tScanDate = "";
		tScanDate = PubFun1.getScanDate(mLCContSchema.getPrtNo());
		mTransferData.setNameAndValue("ScanDate", tScanDate);
		
		//SYY BEGIN
		String tUwState = PubFun1.getUwState("10010041", mLCContSchema
				.getContNo(), this.mMissionID, this.mSubMissionID);
		//String tUwState = PubFun1.getUwState("0000001149", mLCContSchema.getContNo(), this.mMissionID, this.mSubMissionID);
		//SYY END
		logger.debug("tUwState:" + tUwState);
		mTransferData.setNameAndValue("Uw_State", tUwState);
		mTransferData.setNameAndValue("StateDate", PubFun.getCurrentDate());
		mTransferData.setNameAndValue("StateTime", PubFun.getCurrentTime());

		return true;
	}
	
	/**
	 * @param 根据数据库中的描述算出核保级别 执行getUWGrade函数
	 * */
	private String getUWGrade(String tInsuredNo){
		String tUWGrade="";
		String tempUWGrade = "";
		try {
			ExeSQL riskSql = new ExeSQL();
			double RiskAmnt1=0;//累计寿险风险保额
			double RiskAmnt2=0;//累积重疾风险保额
			double RiskAmnt4=0;//累积意外风险保额
			double RiskAmnt6=0;		
			//duanyh 2009-03-14 modify
			//使用新的自核风险保额计算规则
			String tsql = "";
			//寿险风险保额
			/*
			   -- tRiskType = 1 寿险风险保额
			   -- tRiskType = 2 重疾险风险保额
               -- tRiskType = 3 医疗险风险保额
               -- tRiskType = 4 意外险风险保额
               -- tRiskType = 12 身故风险保额
               -- tRiskType = 13 寿险体检额度
               -- tRiskType = 14 重疾体检额度
               -- tRiskType = 15 医疗体检额度

			 */
//			tsql = "select healthyamnt2('" + "?healthyamnt2?"
//			+ "','1','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tsql = "select healthyamnt2('" + "?healthyamnt2?" + "','1','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#@d#?,'" + "?healthyamnt2?" + "','1','1') }";
		 }
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tsql);
			sqlbv3.put("healthyamnt2", tInsuredNo);
			String tempAmnt = riskSql.getOneValue(sqlbv3);			
			RiskAmnt1 = parseFloat(tempAmnt);//寿险
			
//			tsql = "select healthyamnt2('" + "?healthyamnt3?"
//			+ "','2','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tsql = "select healthyamnt2('" + "?healthyamnt3?" + "','2','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#@d#?,'" + "?healthyamnt3?" + "','2','1') }";
		 }
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tsql);
			sqlbv4.put("healthyamnt3", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv4);
			RiskAmnt2 = parseFloat(tempAmnt); //重疾
			
//			tsql = "select healthyamnt2('" + "?healthyamnt4?"
//			+ "','4','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tsql = "select healthyamnt2('" + "?healthyamnt4?" + "','4','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#@d#?,'" + "?healthyamnt4?" + "','4','1') }";
		 }
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(tsql);
			sqlbv5.put("healthyamnt4", tInsuredNo);			
			tempAmnt = riskSql.getOneValue(sqlbv5);
			RiskAmnt4 = parseFloat(tempAmnt); //意外			

			RiskAmnt6 = RiskAmnt1+RiskAmnt2+RiskAmnt4;
			logger.debug("RiskAmnt1:"+RiskAmnt1+"  RiskAmnt2:"+RiskAmnt2+" " +
					" RiskAmnt4:"+RiskAmnt4+"  RiskAmnt6:"+RiskAmnt6);
			
			//执行getUWGrade函数，返回核保级别
			/*"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
			改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
           */
			String UWGradeSql = "select trim(case when getUWGrade('1','"+"?trim?"+"','2','"+"?trim1?"+"'," +
					"'4','"+"?trim4?"+"','6','"+"?trim6?"+"') is not null then getUWGrade('1','"+"?trim?"+"','2','"+"?trim1?"+"'," +
					"'4','"+"?trim4?"+"','6','"+"?trim6?"+"') else '1' end) from dual";
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
	        sqlbv6.sql(UWGradeSql);
	        sqlbv6.put("trim", RiskAmnt1);
	        sqlbv6.put("trim1", RiskAmnt2);
	        sqlbv6.put("trim2", RiskAmnt4);
	        sqlbv6.put("trim3", RiskAmnt6);
			tempUWGrade = riskSql.getOneValue(sqlbv6);
			if(tUWGrade.compareTo(tempUWGrade) < 0){
				tUWGrade=tempUWGrade;
			}
		} catch (Exception ex) {
		}
		return tUWGrade;
	}
	
	public float parseFloat(String s) {
		if (s.length() < 0 || s.equals("")) {
			return 0;
		}
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else if (tmp.length() > 0) {
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		return f1;
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
