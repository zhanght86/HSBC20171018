package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGCUWMasterDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAskTrackMainSchema;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCGCUWSubSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:询价人工核保确认
 * </p>
 * <p>
 * Description:
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

public class AskUWConfirmBL {
private static Logger logger = Logger.getLogger(AskUWConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCGCUWMasterSchema mLCGCUWMasterSchema = new LCGCUWMasterSchema();
	private LCGCUWSubSchema mLCGCUWSubSchema = new LCGCUWSubSchema();
	private LCAskTrackMainSchema mLCAskTrackMainSchema = new LCAskTrackMainSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	private String mGrpContNo;
	private String mUWFlag;

	public AskUWConfirmBL() {
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

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		// 提交数据
		if (cOperate.equals("submit")) {
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "AskUWConfirmBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mLCGrpContSchema, "UPDATE");
		map.put(mLCGCUWMasterSchema, "DELETE&INSERT");
		map.put(mLOPRTManagerSet, "INSERT");
		map.put(mLCAskTrackMainSchema, "DELETE&INSERT");
		map.put(mLCGCUWSubSchema, "INSERT");

		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();

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
			tError.moduleName = "AskUWConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得集体合同号
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		if (mGrpContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中GrpContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCGCUWMasterSchema.setSchema((LCGCUWMasterSchema) cInputData
				.getObjectByObjectName("LCGCUWMasterSchema", 0));
		if (mLCGCUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据LCGCUWMasterSchema失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 准备集体合同表数据
		if (!prepareGrpCont()) {
			return false;
		}

		// 准备集体核保表数据
		if (!prepareGUW()) {
			return false;
		}

		// 准备打印管理表数据
		if (!prepareprint()) {
			return false;
		}

		// 准备业务跟踪主表数据
		if (!preparetrack()) {
			return false;
		}

		return true;
	}

	/**
	 * preparetrack
	 * 
	 * @return boolean
	 */
	private boolean preparetrack() {
		mLCAskTrackMainSchema.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		mLCAskTrackMainSchema.setProposalContNo(mLCGrpContSchema
				.getProposalGrpContNo());
		mLCAskTrackMainSchema.setState("0");
		mLCAskTrackMainSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		mLCAskTrackMainSchema.setAgentGroup(mLCGrpContSchema.getAgentGroup());
		mLCAskTrackMainSchema.setAppntNo(mLCGrpContSchema.getAppntNo());
		mLCAskTrackMainSchema.setOperator(mLCGrpContSchema.getOperator());
		mLCAskTrackMainSchema.setManageCom(mLCGrpContSchema.getManageCom());
		mLCAskTrackMainSchema.setTrackStartDate(PubFun.getCurrentDate());
		mLCAskTrackMainSchema.setTrackStartTime(PubFun.getCurrentTime());
		mLCAskTrackMainSchema.setMakeDate(PubFun.getCurrentDate());
		mLCAskTrackMainSchema.setMakeTime(PubFun.getCurrentTime());
		mLCAskTrackMainSchema.setModifyDate(PubFun.getCurrentDate());
		mLCAskTrackMainSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * prepareGUW
	 * 
	 * @return boolean
	 */
	private boolean prepareGUW() {
		int uwno = 0;
		LCGCUWMasterDB tLCGCUWMasterDB = new LCGCUWMasterDB();
		tLCGCUWMasterDB.setProposalGrpContNo(mGrpContNo);
		if (!tLCGCUWMasterDB.getInfo()) {
			uwno = 1;
		} else {
			uwno = tLCGCUWMasterDB.getUWNo() + 1;
		}
		// 准备核保主表数据
		mLCGCUWMasterSchema.setGrpContNo(mGrpContNo);
		mLCGCUWMasterSchema.setProposalGrpContNo(mGrpContNo);
		mLCGCUWMasterSchema.setUWNo(uwno);
		mLCGCUWMasterSchema.setOperator(mOperator); // 操作员
		mLCGCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mLCGCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		mLCGCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
		mLCGCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());

		// 准备核保子表数据
		mLCGCUWSubSchema.setUWNo(uwno); // 第几次核保
		mLCGCUWSubSchema.setGrpContNo(mLCGCUWMasterSchema.getGrpContNo());
		mLCGCUWSubSchema.setProposalGrpContNo(mLCGCUWMasterSchema
				.getProposalGrpContNo());
		mLCGCUWSubSchema.setAgentCode(mLCGCUWMasterSchema.getAgentCode());
		mLCGCUWSubSchema.setAgentGroup(mLCGCUWMasterSchema.getAgentGroup());
		mLCGCUWSubSchema.setUWGrade(mLCGCUWMasterSchema.getUWGrade()); // 核保级别
		mLCGCUWSubSchema.setAppGrade(mLCGCUWMasterSchema.getAppGrade()); // 申请级别
		mLCGCUWSubSchema.setAutoUWFlag(mLCGCUWMasterSchema.getAutoUWFlag());
		mLCGCUWSubSchema.setState(mLCGCUWMasterSchema.getState());
		mLCGCUWSubSchema.setPassFlag(mLCGCUWMasterSchema.getState());
		mLCGCUWSubSchema.setPostponeDay(mLCGCUWMasterSchema.getPostponeDay());
		mLCGCUWSubSchema.setPostponeDate(mLCGCUWMasterSchema.getPostponeDate());
		mLCGCUWSubSchema.setUpReportContent(mLCGCUWMasterSchema
				.getUpReportContent());
		mLCGCUWSubSchema.setHealthFlag(mLCGCUWMasterSchema.getHealthFlag());
		mLCGCUWSubSchema.setSpecFlag(mLCGCUWMasterSchema.getSpecFlag());
		mLCGCUWSubSchema.setSpecReason(mLCGCUWMasterSchema.getSpecReason());
		mLCGCUWSubSchema.setQuesFlag(mLCGCUWMasterSchema.getQuesFlag());
		mLCGCUWSubSchema.setReportFlag(mLCGCUWMasterSchema.getReportFlag());
		mLCGCUWSubSchema.setChangePolFlag(mLCGCUWMasterSchema
				.getChangePolFlag());
		mLCGCUWSubSchema.setChangePolReason(mLCGCUWMasterSchema
				.getChangePolReason());
		mLCGCUWSubSchema.setAddPremReason(mLCGCUWMasterSchema
				.getAddPremReason());
		mLCGCUWSubSchema.setPrintFlag(mLCGCUWMasterSchema.getPrintFlag());
		mLCGCUWSubSchema.setPrintFlag2(mLCGCUWMasterSchema.getPrintFlag2());
		mLCGCUWSubSchema.setUWIdea(mLCGCUWMasterSchema.getUWIdea());
		mLCGCUWSubSchema.setOperator(mLCGCUWMasterSchema.getOperator()); // 操作员
		mLCGCUWSubSchema.setManageCom(mLCGCUWMasterSchema.getManageCom());
		mLCGCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		mLCGCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		mLCGCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		mLCGCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		logger.debug("mLCGCUWSubSchema:" + mLCGCUWSubSchema.encode());

		return true;
	}

	/**
	 * prepareGrpCont
	 * 
	 * @return boolean
	 */
	private boolean prepareGrpCont() {
		mLCGrpContSchema.setUWFlag(mLCGCUWMasterSchema.getPassFlag());
		mLCGrpContSchema.setUWOperator(mOperator);
		mLCGrpContSchema.setUWDate(PubFun.getCurrentDate());
		mLCGrpContSchema.setUWTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean prepareprint() {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tLOPRTManagerSchema.setOtherNo(mLCGrpContSchema.getGrpContNo());
		tLOPRTManagerSchema.setManageCom(mLCGrpContSchema.getManageCom());
		tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
		mUWFlag = mLCGCUWMasterSchema.getPassFlag();
		switch (mUWFlag.charAt(0)) {
		case '1':
			tLOPRTManagerSchema.setCode(PrintManagerBL.ASK_GRP_DECLINE);
			break;
		case '6':
			tLOPRTManagerSchema.setCode(PrintManagerBL.ASK_GRP_DEFER);
			break;
		case 'a':
			tLOPRTManagerSchema.setCode(PrintManagerBL.Ask_GRP_WITHDRAW);
			break;
		case '4':
		case '9':
			tLOPRTManagerSchema.setCode(PrintManagerBL.ASk_GRP_SUCESS);
			break;
		}
		tLOPRTManagerSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL);
		tLOPRTManagerSchema.setReqOperator(mLCGrpContSchema.getOperator());
		tLOPRTManagerSchema.setReqCom(mLCGrpContSchema.getManageCom());
		tLOPRTManagerSchema.setExeCom(mLCGrpContSchema.getManageCom());
		tLOPRTManagerSchema.setStateFlag("0");

		mLOPRTManagerSet.add(tLOPRTManagerSchema);

		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
