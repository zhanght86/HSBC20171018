package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:工作流节点任务:新契约保存生调录入
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ln
 * @version 1.0
 */

public class RnewRReportChkBL {
private static Logger logger = Logger.getLogger(RnewRReportChkBL.class);
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

	/** 业务数据操作字符串 */
	private String mContNo;

	private String mCustomerNo;

	private String mInsuredNo;

	private String mName;

	private String mMissionID;

	String mPrtSeq = "";

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 核保操作轨迹表 */
	private LCCUWSubSchema mLCCUWSubSchema = new LCCUWSubSchema();

	// /** 核保主表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	/** 生存调查表 */
	private RnewRReportSchema mRnewRReportSchema = new RnewRReportSchema();

	private RnewRReportItemSet mRnewRReportItemSet = new RnewRReportItemSet();

	private RnewRReportItemSet mNewRnewRReportItemSet = new RnewRReportItemSet();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public RnewRReportChkBL() {
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

		logger.debug("After RnewRReportChkBL SubmitData...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加生调数据
		if (mRnewRReportSchema != null) {
			map.put(mRnewRReportSchema, "INSERT");
		}		
		// 添加续保批单核保主表通知书打印管理表数据
		//map.put(mLCCUWMasterSchema, "UPDATE");
		
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
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		String ss = " select distinct 1 from loprtmanager where 1=1"
				+ " and otherno = '?mContNo?'" + " and standbyflag1 = '?mCustomerNo?'" + " and StateFlag in ('0','1')"
				+ " and code = '44'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(ss);
		sqlbv.put("mContNo", mContNo);
		sqlbv.put("mCustomerNo", mCustomerNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tSqlResult = tExeSQL.getOneValue(sqlbv);
		logger.debug("ss:" + ss);
		logger.debug("tSqlResult:" + tSqlResult);
		if (tSqlResult.equals("1")) {
			CError tError = new CError();
			tError.moduleName = "RnewRReportAfterInitService";
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
			tError.moduleName = "RnewRReportAfterInitService";
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
			tError.moduleName = "RnewRReportAfterInitService";
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
			tError.moduleName = "RnewRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		if (mCustomerNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "RnewRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中CustomerNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务生调通知数据
		mRnewRReportSchema = (RnewRReportSchema) mTransferData
				.getValueByName("RnewRReportSchema");
		if (mRnewRReportSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "RnewRReportAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输获得业务生调数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		/*
		 * mRnewRReportItemSet = (RnewRReportItemSet) mTransferData
		 * .getValueByName("RnewRReportItemSet"); if (mRnewRReportItemSet == null ||
		 * mRnewRReportItemSet.size() <= 0) { // @@错误处理
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
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		//mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
		mPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_PRnewMEET);

		// 核保生调信息
		if (prepareReport() == false) {
			return false;
		}		
		return true;

	}

	/**
	 * 准备生调资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareReport() {
		
		mRnewRReportSchema.setPrtSeq(mPrtSeq);
		mRnewRReportSchema.setContNo(mLCContSchema.getContNo());
		mRnewRReportSchema.setGrpContNo(mLCContSchema.getGrpContNo());
		mRnewRReportSchema.setProposalContNo(mLCContSchema.getProposalContNo());
		mRnewRReportSchema.setAppntNo(mLCContSchema.getAppntNo());
		mRnewRReportSchema.setAppntName(mLCContSchema.getAppntName());
		mRnewRReportSchema.setCustomerNo(mCustomerNo);

		mRnewRReportSchema.setManageCom(mManageCom);
		mRnewRReportSchema.setReplyContente("");
		mRnewRReportSchema.setContente(mRnewRReportSchema.getContente());
		mRnewRReportSchema.setRemark(mRnewRReportSchema.getRemark());
		mRnewRReportSchema.setRReportReason(mRnewRReportSchema.getRReportReason());
		mRnewRReportSchema.setName(mRnewRReportSchema.getName());
//		mRnewRReportSchema.setReplyFlag("0");
		mRnewRReportSchema.setOperator(mOperater);
		mRnewRReportSchema.setMakeDate(PubFun.getCurrentDate());
		mRnewRReportSchema.setMakeTime(PubFun.getCurrentTime());
		mRnewRReportSchema.setReplyOperator("");
		mRnewRReportSchema.setReplyDate("");
		mRnewRReportSchema.setReplyTime("");
		mRnewRReportSchema.setModifyDate(PubFun.getCurrentDate());
		mRnewRReportSchema.setModifyTime(PubFun.getCurrentTime());

		// //准备核保主表信息
		/*
		 * for (int i = 1; i <= mRnewRReportItemSet.size(); i++) {
		 * tRnewRReportItemSchema = new RnewRReportItemSchema();
		 * 
		 * tRnewRReportItemSchema.setContNo(mLCContSchema.getContNo());
		 * tRnewRReportItemSchema.setGrpContNo(mLCContSchema.getGrpContNo());
		 * tRnewRReportItemSchema.setProposalContNo(mLCContSchema
		 * .getProposalContNo()); tRnewRReportItemSchema.setPrtSeq(mPrtSeq);
		 * tRnewRReportItemSchema.setRReportItemCode(mRnewRReportItemSet.get(i)
		 * .getRReportItemCode());
		 * tRnewRReportItemSchema.setRReportItemName(mRnewRReportItemSet.get(i)
		 * .getRReportItemName());
		 * tRnewRReportItemSchema.setRemark(mRnewRReportItemSet.get(i).getRemark());
		 * tRnewRReportItemSchema.setModifyDate(PubFun.getCurrentDate());
		 * tRnewRReportItemSchema.setModifyTime(PubFun.getCurrentTime());
		 * 
		 * mNewRnewRReportItemSet.add(tRnewRReportItemSchema); }
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
