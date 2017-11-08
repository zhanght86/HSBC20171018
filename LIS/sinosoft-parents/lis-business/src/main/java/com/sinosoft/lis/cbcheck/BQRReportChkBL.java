package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPRReportDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPCUWSubSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPRReportSchema;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.vschema.LPRReportItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

public class BQRReportChkBL {
private static Logger logger = Logger.getLogger(BQRReportChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mPrtSeqOld;
	private String mOperate;

	/** 业务数据操作字符串 */
	private String mContNo;
	
	private String mEdorType;
	
	private String mEdorNo;

	private String mCustomerNo;
	private String mAppntNo;
	private String mAppntName;
	private String mGrpContNo;
	private String mProposalContNo;

	private String mInsuredNo;

	private String mName;

	private String mMissionID;

	String mPrtSeq = "";

	/** 保单表 */
	private LPContSchema mLPContSchema = new LPContSchema();

	/** 核保操作轨迹表 */
	private LPCUWSubSchema mLPCUWSubSchema = new LPCUWSubSchema();

	// /** 核保主表 */
	private LPCUWMasterSchema mLPCUWMasterSchema = new LPCUWMasterSchema();

	/** 生存调查表 */
	private LPRReportSchema mLPRReportSchema = new LPRReportSchema();
	private LPRReportSchema mLPRReportSchemaOld = new LPRReportSchema();

	private LPRReportItemSet mLPRReportItemSet = new LPRReportItemSet();

	private LPRReportItemSet mNewLPRReportItemSet = new LPRReportItemSet();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public BQRReportChkBL() {
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

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
	
		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError.buildErr(this, "数据提交失败!");		
			return false;
		}

		logger.debug("After UWRReportChkBL SubmitData...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		
		// 保存生调数据
		if (mOperate.equals("UPDATE")) {
			if(mLPRReportSchemaOld != null)
				map.put(mLPRReportSchemaOld, "UPDATE");
		}
		else if(mOperate.equals("INSERT")){
		    if (mLPRReportSchema != null) {
				map.put(mLPRReportSchema, "INSERT");
				// 添加续保批单核保主表通知书打印管理表数据
				map.put(mLPCUWMasterSchema, "UPDATE");
		    }		
		}
		
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
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mContNo);
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setEdorType(mEdorType);
		if (!tLPContDB.getInfo()) {
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mContNo);
			if(!tLCContDB.getInfo()){
				CError.buildErr(this, "查询合同信息失败！");
				return false;
			}
			mAppntName = tLCContDB.getAppntName();
			mAppntNo = tLCContDB.getAppntNo();
			mProposalContNo  = tLCContDB.getProposalContNo();
			mGrpContNo = tLCContDB.getGrpContNo();
		}else{
			mLPContSchema.setSchema(tLPContDB);
			mAppntName = mLPContSchema.getAppntName();
			mAppntNo = mLPContSchema.getAppntNo();
			mProposalContNo  = mLPContSchema.getProposalContNo();
			mGrpContNo = mLPContSchema.getGrpContNo();
		}

		// //校验保单被保人编码信息
		// mInsuredNo = mLPContSchema.getInsuredNo();
		// if (mInsuredNo == null)
		// {
		// CError tError = new CError();
		// tError.moduleName = "UWRReportAfterInitService";
		// tError.functionName = "checkData";
		// tError.errorMessage = "保单" + mContNo + "的被保人编码信息查询失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		// //校验续保批单核保主表
		// //校验保单信息
		// LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		// tLCCUWMasterDB.setContNo(mContNo);
		// if (!tLCCUWMasterDB.getInfo())
		// {
		// CError tError = new CError();
		// tError.moduleName = "UWRReportAfterInitService";
		// tError.functionName = "checkData";
		// tError.errorMessage = "保单" + mContNo + "续保批单核保主表信息查询失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// mLPCUWMasterSchema.setSchema(tLCCUWMasterDB);

		// 处于未打印状态的核保通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
//		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
//
//		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_MEET); // 生调通知书
//		tLOPRTManagerDB.setOtherNo(mContNo);
//		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
//
//		tLOPRTManagerDB.setStateFlag("0"); // 通知书未打印
//
//		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
//		if (tLOPRTManagerSet == null) {
//			// @@错误处理
//			CError tError = new CError();
//			tError.moduleName = "UWRReportAfterInitService";
//			tError.functionName = "preparePrint";
//			tError.errorMessage = "查询打印管理表信息出错!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}

		String ss = " select distinct 1 from loprtmanager where 1=1"
				+ " and otherno = '" + "?mContNo?" + "'" + " and standbyflag1 = '"
				+ "?mCustomerNo?" + "'" + " and StateFlag in ('0','1')"
				+ " and code = '04'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(ss);
		sqlbv.put("mContNo", mContNo);
		sqlbv.put("mCustomerNo", mCustomerNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tSqlResult = tExeSQL.getOneValue(sqlbv);
		logger.debug("ss:" + ss);
		logger.debug("tSqlResult:" + tSqlResult);
		if (tSqlResult.equals("1")) {
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "该保单下的客户已经在生调打印队列中，不能再次发送!";
			this.mErrors.addOneError(tError);
			return false;

		}

		// 取生调人姓名
		// LDPersonDB tLDPersonDB = new LDPersonDB();
		// tLDPersonDB.setCustomerNo(mCustomerNo);
		// if (!tLDPersonDB.getInfo())
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "UWAutoHealthAfterInitService";
		// tError.functionName = "prepareHealth";
		// tError.errorMessage = "取被体检客户姓名失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// mName = tLDPersonDB.getName();

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mOperate = "INSERT";
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
			tError.moduleName = "UWRReportAfterInitService";
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
			tError.moduleName = "UWRReportAfterInitService";
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
			tError.moduleName = "UWRReportAfterInitService";
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
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		mPrtSeqOld = (String) mTransferData.getValueByName("PrtSeq");

		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		if (mEdorType == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中EdorType失败！");
			return false;
		}
		if (mEdorNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中mEdorNo失败！");
			return false;
		}

		mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		if (mCustomerNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中CustomerNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务生调通知数据
		mLPRReportSchema = (LPRReportSchema) mTransferData
				.getValueByName("LPRReportSchema");
		if (mLPRReportSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输获得业务生调数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		/*
		 * mLCRReportItemSet = (LCRReportItemSet) mTransferData
		 * .getValueByName("LCRReportItemSet"); if (mLCRReportItemSet == null ||
		 * mLCRReportItemSet.size() <= 0) { // @@错误处理
		 * //this.mErrors.copyAllErrors( tLCContDB.mErrors ); CError tError =
		 * new CError(); tError.moduleName = "UWRReportAfterInitService";
		 * tError.functionName = "getInputData"; tError.errorMessage =
		 * "前台传输获得业务生调项目内容数据失败!"; this.mErrors.addOneError(tError); return
		 * false; }
		 */

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		//String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		mPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_PEdorMEET);
		
		if(mPrtSeqOld!=null && !mPrtSeqOld.equals("null") && !mPrtSeqOld.equals(""))
		{
			logger.debug("***************已有生调录入，将进行修改*************");
			mOperate = "UPDATE";
			// 修改核保生调信息
			if (updateReport() == false) {
				return false;
			}
		}
		else
		{
			logger.debug("***************没有生调录入，将进行新增*************");
			// 核保生调信息
			if (prepareReport() == false) {
				return false;
			}	
		}	
		
		return true;

	}
	
	/**
	 * 准备生调资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean updateReport() {		
		LPRReportDB tLPRReportDB = new LPRReportDB();
		tLPRReportDB.setEdorNo(mEdorNo);
		tLPRReportDB.setContNo(mContNo);
		tLPRReportDB.setPrtSeq(mPrtSeqOld);
		if (tLPRReportDB.getInfo() == false) {
			// @@错误处理
			CError.buildErr(this, "查询生调表失败!");
			return false;
		}

		mLPRReportSchemaOld = tLPRReportDB.getSchema();		
		mLPRReportSchemaOld.setContente(mLPRReportSchema.getContente());
		mLPRReportSchemaOld.setRReportReason(mLPRReportSchema.getRReportReason());
		mLPRReportSchemaOld.setOperator(mOperater);
		mLPRReportSchemaOld.setModifyDate(PubFun.getCurrentDate());
		mLPRReportSchemaOld.setModifyTime(PubFun.getCurrentTime());

		// //准备核保主表信息
		/*
		 * for (int i = 1; i <= mLCRReportItemSet.size(); i++) {
		 * tLCRReportItemSchema = new LCRReportItemSchema();
		 * 
		 * tLCRReportItemSchema.setContNo(mLCContSchema.getContNo());
		 * tLCRReportItemSchema.setGrpContNo(mLCContSchema.getGrpContNo());
		 * tLCRReportItemSchema.setProposalContNo(mLCContSchema
		 * .getProposalContNo()); tLCRReportItemSchema.setPrtSeq(mPrtSeq);
		 * tLCRReportItemSchema.setRReportItemCode(mLCRReportItemSet.get(i)
		 * .getRReportItemCode());
		 * tLCRReportItemSchema.setRReportItemName(mLCRReportItemSet.get(i)
		 * .getRReportItemName());
		 * tLCRReportItemSchema.setRemark(mLCRReportItemSet.get(i).getRemark());
		 * tLCRReportItemSchema.setModifyDate(PubFun.getCurrentDate());
		 * tLCRReportItemSchema.setModifyTime(PubFun.getCurrentTime());
		 * 
		 * mNewLCRReportItemSet.add(tLCRReportItemSchema); }
		 */
		return true;
	}

	/**
	 * 准备生调资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareReport() {
		// 准备续保核保主表信息
		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setContNo(mContNo);
		tLPCUWMasterDB.setEdorNo(mEdorNo);
		tLPCUWMasterDB.setEdorType(mEdorType);
		if (tLPCUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError.buildErr(this, "查询核保主表失败!");
			return false;
		}

		mLPCUWMasterSchema.setSchema(tLPCUWMasterDB);
		mLPCUWMasterSchema.setReportFlag("1");// 发起生调

		mLPRReportSchema.setPrtSeq(mPrtSeq);
		mLPRReportSchema.setContNo(mContNo);
		mLPRReportSchema.setGrpContNo(mGrpContNo);
		mLPRReportSchema.setProposalContNo(mProposalContNo);
		mLPRReportSchema.setAppntNo(mAppntNo);
		mLPRReportSchema.setAppntName(mAppntName);
		mLPRReportSchema.setCustomerNo(mCustomerNo);

		mLPRReportSchema.setManageCom(mManageCom);
		mLPRReportSchema.setReplyContente("");
		mLPRReportSchema.setContente(mLPRReportSchema.getContente());
		mLPRReportSchema.setRemark(mLPRReportSchema.getRemark());
		mLPRReportSchema.setRReportReason(mLPRReportSchema.getRReportReason());
		mLPRReportSchema.setName(mLPRReportSchema.getName());
//		mLPRReportSchema.setReplyFlag("0");
		mLPRReportSchema.setOperator(mOperater);
		mLPRReportSchema.setMakeDate(PubFun.getCurrentDate());
		mLPRReportSchema.setMakeTime(PubFun.getCurrentTime());
		mLPRReportSchema.setReplyOperator("");
		mLPRReportSchema.setReplyDate("");
		mLPRReportSchema.setReplyTime("");
		mLPRReportSchema.setModifyDate(PubFun.getCurrentDate());
		mLPRReportSchema.setModifyTime(PubFun.getCurrentTime());

		// //准备核保主表信息
		/*
		 * for (int i = 1; i <= mLCRReportItemSet.size(); i++) {
		 * tLCRReportItemSchema = new LCRReportItemSchema();
		 * 
		 * tLCRReportItemSchema.setContNo(mLPContSchema.getContNo());
		 * tLCRReportItemSchema.setGrpContNo(mLPContSchema.getGrpContNo());
		 * tLCRReportItemSchema.setProposalContNo(mLPContSchema
		 * .getProposalContNo()); tLCRReportItemSchema.setPrtSeq(mPrtSeq);
		 * tLCRReportItemSchema.setRReportItemCode(mLCRReportItemSet.get(i)
		 * .getRReportItemCode());
		 * tLCRReportItemSchema.setRReportItemName(mLCRReportItemSet.get(i)
		 * .getRReportItemName());
		 * tLCRReportItemSchema.setRemark(mLCRReportItemSet.get(i).getRemark());
		 * tLCRReportItemSchema.setModifyDate(PubFun.getCurrentDate());
		 * tLCRReportItemSchema.setModifyTime(PubFun.getCurrentTime());
		 * 
		 * mNewLCRReportItemSet.add(tLCRReportItemSchema); }
		 */
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
