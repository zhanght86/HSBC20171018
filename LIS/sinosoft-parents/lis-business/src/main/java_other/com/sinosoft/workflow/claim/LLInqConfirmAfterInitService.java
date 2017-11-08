package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLInqConfirmBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 呈报处理信息提交服务类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */

public class LLInqConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLInqConfirmAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 提交数据容器 */
	private MMap map = new MMap();
	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String transact;

	private String InqConfirm="";
	
	public LLInqConfirmAfterInitService() {
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
		logger.debug("LLInqConfirmAfterInitService----getInputData successful!");

		// 校验传入数据
		if (!checkData())
			return false;
		logger.debug("LLInqConfirmAfterInitService----Start dealData...");
		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("LLInqConfirmAfterInitService----dealData successful!");
		// 为工作流下一节点属性字段准备数据
		// if (!prepareTransferData())
		// return false;
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		logger.debug("LLInqConfirmAfterInitService----Start  Submit...");
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		logger.debug("----------LLInqConfirmAfterInitService checkData BEGIN----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		logger.debug("----------LLInqConfirmAfterInitService getInputData BEGIN----------");

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mInputData = cInputData;
		mOperate = cOperate;

		String tClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		String tInqNo = (String) mTransferData.getValueByName("InqNo"); // 调查序号
		String tBatNo = (String) mTransferData.getValueByName("BatNo"); // 调查批次
		String tInqDept = (String) mTransferData.getValueByName("InqDept"); // 调查机构

		if (tClmNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tInqNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tBatNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tInqDept == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLInqConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mSubMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		String strSQL = ""; // 用于判断是否可以生成下一个节点(调查结论)
		strSQL = "select ClmNO,InqNo,BatNo,CustomerNo,Customername,VIPFlag,InqDept,InitPhase,InqRCode,InqItem from llInqapply"
				+ " where ClmNO='"
				+ "?tClmNo?"
				+ "'"
				+ " and InqNo!='"
				+ "?tInqNo?"
				+ "'"
				+ " and BatNo='"
				+ "?tBatNo?"
				+ "'"
				+ " and InqDept='" + "?tInqDept?" + "'" + " and InqState='0'";
		logger.debug("--strSQL=" + strSQL);
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("tClmNo",tClmNo);
		sqlbv1.put("tInqNo",tInqNo);
		sqlbv1.put("tBatNo",tBatNo);
		sqlbv1.put("tInqDept",tInqDept);
		ExeSQL exesql = new ExeSQL();
		String tResult = exesql.getOneValue(sqlbv1);
		logger.debug("-----判断事件是否可以生成下一个节点tResult.length()= "
				+ tResult.length());
		if ((tResult.length() == 0)) {
			// 消亡调查过程录入节点，并生成下一个节点
			logger.debug("-----可以生成下一个节点------");
			InqConfirm ="1";
			mTransferData.removeByName("InqConfirm");
			mTransferData.setNameAndValue("InqConfirm", InqConfirm);
			
		} else {
			// 消亡调查过程录入节点（不生成下一个节点）
			logger.debug("-----不可以生成下一个节点------");
			InqConfirm ="0";
			mTransferData.removeByName("InqConfirm");
			mTransferData.setNameAndValue("InqConfirm", InqConfirm);
		}
	

		return true;
	}

	/**
	 * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------LLInqConfirmAfterInitService dealData BEGIN----------");
		boolean tReturn = false;
		transact = (String) mTransferData.getValueByName("transact"); // 传送动作
		logger.debug("----------LLInqConfirmAfterInitService transact----------"
						+ transact);

		LLInqConfirmBL tLLInqConfirmBL = new LLInqConfirmBL();
		if (!tLLInqConfirmBL.submitData(mInputData, transact)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLInqConfirmBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLInqConfirmAfterInitService";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			mInputData = null;
			tReturn = false;
		} else {
			mInputData = null;
			mInputData = tLLInqConfirmBL.getResult();
			logger.debug("-----start LLInqConfirmAfterInitService getData from BL");
			map = (MMap) mInputData.getObjectByObjectName("MMap", 0);
			tReturn = true;
		}
		return tReturn;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
