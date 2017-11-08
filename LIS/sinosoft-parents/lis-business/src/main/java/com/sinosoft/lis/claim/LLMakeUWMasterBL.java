package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LBPOAppntBL;
import com.sinosoft.lis.db.LBPOAppntDB;
import com.sinosoft.lis.db.LBPOContDB;
import com.sinosoft.lis.db.LBPOInsuredDB;
import com.sinosoft.lis.db.LCCUWErrorDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LLCUWBatchDB;
import com.sinosoft.lis.db.LLCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LLCUWMasterDB;
import com.sinosoft.lis.db.LLUWMasterDB;
import com.sinosoft.lis.db.LLUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBPOAppntSchema;
import com.sinosoft.lis.schema.LBPOContSchema;
import com.sinosoft.lis.schema.LBPOInsuredSchema;
import com.sinosoft.lis.schema.LCCUWErrorSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWErrorSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LLCUWSubSchema;
import com.sinosoft.lis.schema.LLCUWMasterSchema;
import com.sinosoft.lis.schema.LLUWMasterSchema;
import com.sinosoft.lis.schema.LLUWSubSchema;
import com.sinosoft.lis.vschema.LBPOAppntSet;
import com.sinosoft.lis.vschema.LBPOCustomerImpartSet;
import com.sinosoft.lis.vschema.LCCUWErrorSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LLCUWBatchSet;
import com.sinosoft.lis.vschema.LLCUWSubSet;
import com.sinosoft.lis.vschema.LLCUWMasterSet;
import com.sinosoft.lis.vschema.LLUWMasterSet;
import com.sinosoft.lis.vschema.LLUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLMakeUWMasterBL {
private static Logger logger = Logger.getLogger(LLMakeUWMasterBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private Reflections ref = new Reflections();

	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap map = new MMap();
	
	private LLCUWMasterSchema mLLCUWMasterSchema = new LLCUWMasterSchema();
	private LLCUWMasterSet mLLCUWMasterSet = new LLCUWMasterSet();
	private LLCUWSubSet mLLCUWSubSet = new LLCUWSubSet();
	private LLUWMasterSet mLLUWMasterSet = new LLUWMasterSet();
	private LLUWSubSet mLLUWSubSet = new LLUWSubSet();
	private LCContDB mLCContDB = new LCContDB();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();

	private String theCurrentTime = PubFun.getCurrentTime();

	/** 业务处理相关变量 */


	private String mContNo = "";
	private String mCaseNo = "";
	private String mBatNo = "";

	// @Constructor
	public LLMakeUWMasterBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in ContBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false) {
			return false;
		}
		logger.debug("---getInputData---");
		if (this.checkData() == false) {
			return false;
		}
		logger.debug("---checkData---");
		// }

		// 根据业务逻辑对数据进行处理
		logger.debug("---dealData start---");
		if (this.dealData() == false) {
			return false;
		}
		logger.debug("---dealData  ended---");
		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

//		if (!tPubSubmit.submitData(mInputData, mOperate)) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
//
//			CError tError = new CError();
//			tError.moduleName = "ContBL";
//			tError.functionName = "submitData";
//			tError.errorMessage = "数据提交失败!";
//
//			this.mErrors.addOneError(tError);
//			return false;
//		}

		logger.debug("---commitData---");
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			// 合同表

			TransferData tTransferData = (TransferData) mInputData
					.getObjectByObjectName("TransferData", 0);
			mCaseNo = (String) tTransferData.getValueByName("CaseNo");
			mContNo = (String) tTransferData.getValueByName("ContNo");
			mBatNo = (String) tTransferData.getValueByName("BatNo");
			if(mCaseNo==null||"".equals(mCaseNo.trim())){
				CError.buildErr(this, "前台传入赔案号失败！");
				return false;
			}
//			if(mContNo==null||"".equals(mContNo.trim())){
//				CError.buildErr(this, "前台传入合同号失败！");
//				return false;
//			}
			if(mBatNo==null||"".equals(mBatNo.trim())){
				CError.buildErr(this, "前台传入批次号失败！");
				return false;
			}
			//获取合同信息
//			mLCContDB.setContNo(mContNo);
//			if(!mLCContDB.getInfo()){
//				CError.buildErr(this, "查询合同信息失败！");
//				return false;
//			}
			
			return true;
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "checkData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;

		}
		
		

	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		LLCUWBatchSet tLLCUWBatchSet = new LLCUWBatchSet();
		LLCUWBatchDB tLLCUWBatchDB = new LLCUWBatchDB();
		tLLCUWBatchDB.setCaseNo(mCaseNo);
		tLLCUWBatchDB.setBatNo(mBatNo);
		tLLCUWBatchSet = tLLCUWBatchDB.query();
		for(int i=1;i<=tLLCUWBatchSet.size();i++){
			
			if(!dealCont(tLLCUWBatchSet.get(i).getContNo())){
				return false;
			}
			
			if(!dealPol(tLLCUWBatchSet.get(i).getContNo())){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 处理LLCUWMaster
	 * 
	 * */
	private boolean dealCont(String tContNo){
		int batchNo=0;
		mLCContDB = new LCContDB();
		mLCContDB.setContNo(tContNo);
		if(!mLCContDB.getInfo()){
			CError.buildErr(this, "查询合同信息失败！");
			return false;
		}
		LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
		LLCUWMasterDB tLLCUWMasterDB = new LLCUWMasterDB();
		tLLCUWMasterDB.setContNo(tContNo);
		tLLCUWMasterDB.setCaseNo(mCaseNo);
//		tLLCUWMasterDB.setBatNo(mBatNo);
		LLCUWMasterSet tLLCUWMasterSet = new LLCUWMasterSet();
		tLLCUWMasterSet = tLLCUWMasterDB.query();
		if (tLLCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = tContNo + "合同核保总表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLLCUWMasterSet.size() == 0) {
			tLLCUWMasterSchema.setContNo(tContNo);
			tLLCUWMasterSchema.setCaseNo(mCaseNo);
			tLLCUWMasterSchema.setGrpContNo(mLCContDB.getGrpContNo());
			tLLCUWMasterSchema.setProposalContNo(mLCContDB
					.getProposalContNo());
			tLLCUWMasterSchema.setBatNo(mBatNo);
			tLLCUWMasterSchema.setUWNo(1);
			tLLCUWMasterSchema.setInsuredNo(mLCContDB.getInsuredNo());
			tLLCUWMasterSchema.setInsuredName(mLCContDB.getInsuredName());
			tLLCUWMasterSchema.setAppntNo(mLCContDB.getAppntNo());
			tLLCUWMasterSchema.setAppntName(mLCContDB.getAgentCode());
			tLLCUWMasterSchema.setAgentGroup(mLCContDB.getAgentGroup());
//			tLLCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
//			tLLCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLLCUWMasterSchema.setPostponeDay("");
			tLLCUWMasterSchema.setPostponeDate("");
			tLLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLLCUWMasterSchema.setState("5");
			tLLCUWMasterSchema.setPassFlag("5");
			tLLCUWMasterSchema.setHealthFlag("0");
			tLLCUWMasterSchema.setSpecFlag("0");
			tLLCUWMasterSchema.setQuesFlag("0");
			tLLCUWMasterSchema.setReportFlag("0");
			tLLCUWMasterSchema.setChangePolFlag("0");
			tLLCUWMasterSchema.setPrintFlag("0");
			tLLCUWMasterSchema.setPrintFlag2("0");
			tLLCUWMasterSchema.setManageCom(mLCContDB.getManageCom());
			tLLCUWMasterSchema.setUWIdea("");
			tLLCUWMasterSchema.setUpReportContent("");
			tLLCUWMasterSchema.setOperator(mGlobalInput.Operator); // 操作员
			tLLCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLLCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			tLLCUWMasterSchema = tLLCUWMasterSet.get(1);
			tLLCUWMasterSchema.setUWNo(tLLCUWMasterSchema.getUWNo() + 1);
			tLLCUWMasterSchema.setBatNo(mBatNo);
			tLLCUWMasterSchema.setState("5");
			tLLCUWMasterSchema.setPassFlag("5");
			tLLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
//			tLLCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
//			tLLCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLLCUWMasterSchema.setOperator(mGlobalInput.Operator); // 操作员
			tLLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		}
//		batchNo=nUWNo+1;
//		mLLCUWMasterSchema.setSchema(tLLCUWMasterSchema);
		mLLCUWMasterSet.add(tLLCUWMasterSchema);
		// 合同核保轨迹表
		LLCUWSubSchema tLLCUWSubSchema = new LLCUWSubSchema();
		LLCUWSubDB tLLCUWSubDB = new LLCUWSubDB();
		tLLCUWSubDB.setContNo(tContNo);
		tLLCUWSubDB.setCaseNo(mCaseNo);
		tLLCUWSubDB.setBatNo(mBatNo);
		LLCUWSubSet tLLCUWSubSet = new LLCUWSubSet();
		tLLCUWSubSet = tLLCUWSubDB.query();
		if (tLLCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = tContNo + "合同核保轨迹表查失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int nUWNo = tLLCUWSubSet.size();
		
		tLLCUWSubSchema.setUWNo(nUWNo+1); // 第几次核保

		tLLCUWSubSchema.setContNo(tLLCUWMasterSchema.getContNo());
		tLLCUWSubSchema.setCaseNo(mCaseNo);
		tLLCUWSubSchema.setGrpContNo(tLLCUWMasterSchema.getGrpContNo());
		tLLCUWSubSchema.setProposalContNo(tLLCUWMasterSchema
				.getProposalContNo());
		tLLCUWSubSchema.setInsuredNo(tLLCUWMasterSchema.getInsuredNo());
		tLLCUWSubSchema.setBatNo(mBatNo);
		tLLCUWSubSchema.setInsuredName(tLLCUWMasterSchema.getInsuredName());
		tLLCUWSubSchema.setAppntNo(tLLCUWMasterSchema.getAppntNo());
		tLLCUWSubSchema.setAppntName(tLLCUWMasterSchema.getAppntName());
		tLLCUWSubSchema.setAgentCode(tLLCUWMasterSchema.getAgentCode());
		tLLCUWSubSchema.setAgentGroup(tLLCUWMasterSchema.getAgentGroup());
		tLLCUWSubSchema.setUWGrade(tLLCUWMasterSchema.getUWGrade()); // 核保级别
		tLLCUWSubSchema.setAppGrade(tLLCUWMasterSchema.getAppGrade()); // 申请级别
		tLLCUWSubSchema.setAutoUWFlag(tLLCUWMasterSchema.getAutoUWFlag());
		tLLCUWSubSchema.setState(tLLCUWMasterSchema.getState());
		tLLCUWSubSchema.setPassFlag(tLLCUWMasterSchema.getState());
		tLLCUWSubSchema.setPostponeDay(tLLCUWMasterSchema.getPostponeDay());
		tLLCUWSubSchema.setPostponeDate(tLLCUWMasterSchema.getPostponeDate());
		tLLCUWSubSchema.setUpReportContent(tLLCUWMasterSchema
				.getUpReportContent());
		tLLCUWSubSchema.setHealthFlag(tLLCUWMasterSchema.getHealthFlag());
		tLLCUWSubSchema.setSpecFlag(tLLCUWMasterSchema.getSpecFlag());
		tLLCUWSubSchema.setSpecReason(tLLCUWMasterSchema.getSpecReason());
		tLLCUWSubSchema.setQuesFlag(tLLCUWMasterSchema.getQuesFlag());
		tLLCUWSubSchema.setReportFlag(tLLCUWMasterSchema.getReportFlag());
		tLLCUWSubSchema.setChangePolFlag(tLLCUWMasterSchema.getChangePolFlag());
		tLLCUWSubSchema.setChangePolReason(tLLCUWMasterSchema
				.getChangePolReason());
		tLLCUWSubSchema.setAddPremReason(tLLCUWMasterSchema.getAddPremReason());
		tLLCUWSubSchema.setPrintFlag(tLLCUWMasterSchema.getPrintFlag());
		tLLCUWSubSchema.setPrintFlag2(tLLCUWMasterSchema.getPrintFlag2());
		tLLCUWSubSchema.setUWIdea(tLLCUWMasterSchema.getUWIdea());
		tLLCUWSubSchema.setOperator(tLLCUWMasterSchema.getOperator()); // 操作员
		tLLCUWSubSchema.setManageCom(tLLCUWMasterSchema.getManageCom());
		tLLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		mLLCUWSubSet.add(tLLCUWSubSchema);

//		tLLCUWMasterSchema.setBatchNo(1);
		return true;
	
	}

	
	/**
	 * 处理LLUWMaster
	 * 
	 * */
	private boolean dealPol(String tContNo){
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(tContNo);
		tLCPolDB.setAppFlag("1");
		tLCPolSet = tLCPolDB.query();
		for(int i=1;i<=tLCPolSet.size();i++){
			String tAddFeeFlag="0";
			int batchNo=0;
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);
			int tuwno = 0;
			LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();
			LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();
			tLLUWMasterDB.setPolNo(tLCPolSchema.getPolNo());
//			tLLUWMasterDB.setBatNo(mBatNo);
			tLLUWMasterDB.setCaseNo(mCaseNo);
			LLUWMasterSet tLLUWMasterSet = new LLUWMasterSet();
			tLLUWMasterSet = tLLUWMasterDB.query();
			//判断是否有加费信息
//			String addFeeSql="select * from lcprem  where polno='"+tLCPolSchema.getPolNo()+"' and payplancode like '000000%%'";
//			SSRS tAddFee = new SSRS();
//			ExeSQL tExeSQL = new ExeSQL();
//			tAddFee = tExeSQL.execSQL(addFeeSql);
//			if(tAddFee.MaxRow>0){
//				tAddFeeFlag="1";
//			}
			int n = tLLUWMasterSet.size();
			if (n == 0) {
				tLLUWMasterSchema.setContNo(tContNo);
				tLLUWMasterSchema.setCaseNo(mCaseNo);
				tLLUWMasterSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLLUWMasterSchema.setPolNo(tLCPolSchema.getPolNo());
				tLLUWMasterSchema.setProposalContNo(mLCContDB.getProposalContNo());
				tLLUWMasterSchema.setProposalNo(tLCPolSchema.getProposalNo());
				tLLUWMasterSchema.setBatNo(mBatNo);
				tLLUWMasterSchema.setUWNo(1);
				tLLUWMasterSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
				tLLUWMasterSchema.setInsuredName(tLCPolSchema.getInsuredName());
				tLLUWMasterSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLLUWMasterSchema.setAppntName(tLCPolSchema.getAppntName());
				tLLUWMasterSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLLUWMasterSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLLUWMasterSchema.setBatNo(mBatNo);
//				tLLUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
//				tLLUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
				tLLUWMasterSchema.setPostponeDay("");
				tLLUWMasterSchema.setPostponeDate("");
				tLLUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
				tLLUWMasterSchema.setState("5");
				tLLUWMasterSchema.setPassFlag("5");
				tLLUWMasterSchema.setHealthFlag("0");
				tLLUWMasterSchema.setSpecFlag("0");
				tLLUWMasterSchema.setQuesFlag("0");
				tLLUWMasterSchema.setReportFlag("0");
				tLLUWMasterSchema.setChangePolFlag("0");
				tLLUWMasterSchema.setPrintFlag("0");
				tLLUWMasterSchema.setManageCom(tLCPolSchema.getManageCom());
				tLLUWMasterSchema.setUWIdea("");
				tLLUWMasterSchema.setUpReportContent("");
				tLLUWMasterSchema.setOperator(mGlobalInput.Operator); // 操作员
				tLLUWMasterSchema.setAddPremFlag("0");
				tLLUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
				tLLUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
				tLLUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
				tLLUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			} else if (n == 1) {
				tLLUWMasterSchema = tLLUWMasterSet.get(1);
	
				tuwno = tLLUWMasterSchema.getUWNo();
				tuwno = tuwno + 1;
	
				tLLUWMasterSchema.setUWNo(tuwno);
				tLLUWMasterSchema.setProposalContNo(mLCContDB.getProposalContNo());
//				tLLUWMasterSchema.setState(mPolPassFlag);
				tLLUWMasterSchema.setPassFlag("5");
				tLLUWMasterSchema.setBatNo(mBatNo);
				tLLUWMasterSchema.setAddPremFlag(tAddFeeFlag);
				tLLUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
				tLLUWMasterSchema.setUWGrade("5"); // 核保级别
				tLLUWMasterSchema.setAppGrade("5"); // 申报级别
				tLLUWMasterSchema.setOperator(mGlobalInput.Operator); // 操作员
				tLLUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
				tLLUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLLUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = "个人核保总表取数据不唯一!";
				this.mErrors.addOneError(tError);
				return false;
			}
	
			// 核保轨迹表 
			LLUWSubSchema tLLUWSubSchema = new LLUWSubSchema();
			LLUWSubDB tLLUWSubDB = new LLUWSubDB();
			//tLLUWSubDB.setPolNo(mOldPolNo);
			String sqlUwno = "select * from lluwsub where polno ='"+ "?polno?" +"' and "
								+" caseno='"+"?caseno?"+"'"
								+" order by uwno desc ";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sqlUwno);
			sqlbv.put("polno", tLCPolSchema.getPolNo());
			sqlbv.put("caseno", mCaseNo);
			LLUWSubSet tLLUWSubSet = new LLUWSubSet();
			tLLUWSubSet = tLLUWSubDB.executeQuery(sqlbv);
	
			int m = tLLUWSubSet.size();
			int uwNo = 0;
			
			if(m>0){
				uwNo=tLLUWSubSet.get(1).getUWNo();
				tLLUWSubSchema.setUWNo(uwNo+1);
			}else{
				tLLUWSubSchema.setUWNo(1); // 第1次核保
			}
	
			tLLUWSubSchema.setContNo(tContNo);
			tLLUWSubSchema.setCaseNo(mCaseNo);
			tLLUWSubSchema.setPolNo(tLCPolSchema.getPolNo());
			tLLUWSubSchema.setGrpContNo(tLLUWMasterSchema.getGrpContNo());
			tLLUWSubSchema.setProposalContNo(tLLUWMasterSchema.getProposalContNo());
			tLLUWSubSchema.setProposalNo(tLLUWMasterSchema.getProposalNo());
			tLLUWSubSchema.setInsuredNo(tLLUWMasterSchema.getInsuredNo());
			tLLUWSubSchema.setBatNo(mBatNo);
			tLLUWSubSchema.setInsuredName(tLLUWMasterSchema.getInsuredName());
			tLLUWSubSchema.setAppntNo(tLLUWMasterSchema.getAppntNo());
			tLLUWSubSchema.setAppntName(tLLUWMasterSchema.getAppntName());
			tLLUWSubSchema.setAgentCode(tLLUWMasterSchema.getAgentCode());
			tLLUWSubSchema.setAgentGroup(tLLUWMasterSchema.getAgentGroup());
			tLLUWSubSchema.setUWGrade(tLLUWMasterSchema.getUWGrade()); // 核保级别
			tLLUWSubSchema.setAppGrade(tLLUWMasterSchema.getAppGrade()); // 申请级别
			tLLUWSubSchema.setAutoUWFlag(tLLUWMasterSchema.getAutoUWFlag());
			tLLUWSubSchema.setState(tLLUWMasterSchema.getState());
			tLLUWSubSchema.setPassFlag(tLLUWMasterSchema.getState());
			tLLUWSubSchema.setPostponeDay(tLLUWMasterSchema.getPostponeDay());
			tLLUWSubSchema.setPostponeDate(tLLUWMasterSchema.getPostponeDate());
			tLLUWSubSchema.setUpReportContent(tLLUWMasterSchema
					.getUpReportContent());
			tLLUWSubSchema.setHealthFlag(tLLUWMasterSchema.getHealthFlag());
			tLLUWSubSchema.setSpecFlag(tLLUWMasterSchema.getSpecFlag());
			tLLUWSubSchema.setSpecReason(tLLUWMasterSchema.getSpecReason());
			tLLUWSubSchema.setQuesFlag(tLLUWMasterSchema.getQuesFlag());
			tLLUWSubSchema.setReportFlag(tLLUWMasterSchema.getReportFlag());
			tLLUWSubSchema.setChangePolFlag(tLLUWMasterSchema.getChangePolFlag());
			tLLUWSubSchema.setChangePolReason(tLLUWMasterSchema
					.getChangePolReason());
			tLLUWSubSchema.setAddPremReason(tLLUWMasterSchema.getAddPremReason());
			tLLUWSubSchema.setPrintFlag(tLLUWMasterSchema.getPrintFlag());
			tLLUWSubSchema.setPrintFlag2(tLLUWMasterSchema.getPrintFlag2());
			tLLUWSubSchema.setUWIdea(tLLUWMasterSchema.getUWIdea());
			tLLUWSubSchema.setOperator(tLLUWMasterSchema.getOperator()); // 操作员
			tLLUWSubSchema.setManageCom(tLLUWMasterSchema.getManageCom());
			tLLUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLLUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLLUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLLUWSubSchema.setModifyTime(PubFun.getCurrentTime());
	
			
			mLLUWSubSet.add(tLLUWSubSchema);
			mLLUWMasterSet.add(tLLUWMasterSchema);
		}
		return true;
	
	}
	
	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mInputData.clear();
		int n = mLLUWMasterSet.size();
//		for (int i = 1; i <= n; i++) {
//			LLUWMasterSchema tLLUWMasterSchema = mLLUWMasterSet.get(i);
			map.put(mLLUWMasterSet, "DELETE&INSERT");
//		}
		map.put(mLLCUWMasterSet, "DELETE&INSERT");
		map.put(mLLCUWSubSet, "INSERT");
		map.put(mLLUWSubSet, "INSERT");
		mInputData.add(map);
		mResult.clear();
		mResult.add(map);
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}
}
