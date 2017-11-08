package com.sinosoft.workflow.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title:工作流服务类：团体新契约复核修改
 * </p>
 * <p>
 * Description: 团体复核修改工作流AfterInit服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ modify by zhangxing
 * @version 1.0
 */

public class GrpApproveModifyAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GrpApproveModifyAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mMissionID;
	private String mGrpContNo;
	private String mApproveFlag;
	private String mGrpContSql;
	private String mContSql;
	private String mPolSql;

	public GrpApproveModifyAfterInitService() {
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

		map.put(mGrpContSql, "UPDATE");
		map.put(mContSql, "UPDATE");
		map.put(mPolSql, "UPDATE");

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
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWRReportModifyAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "集体保单" + mGrpContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContSchema.setSchema(tLCGrpContDB);
		// 确保集体投保单下有个人投保单
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select count(*) from LCCont " + "where GrpContNo = '"
				+ mLCGrpContSchema.getProposalGrpContNo() + "'";

		String tStr = "";
		double tCount = -1;
		tStr = tExeSQL.getOneValue(sql);
		if (tStr.trim().equals("")) {
			tCount = 0;
		} else {
			tCount = Double.parseDouble(tStr);
		}

		if (tCount <= 0.0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveBL";
			tError.functionName = "dealData";
			tError.errorMessage = "集体投保单下没有个人投保单，不能进行复核操作!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 确保集体投保单进行复核修改确认操作时,已将所有问题件已回复
		sql = "select count(*) from LCGrpIssuePol "
				+ "where ProposalGrpContNo = '"
				+ mLCGrpContSchema.getProposalGrpContNo()
				+ "' and replyman is null";
		tCount = -1;
		tStr = tExeSQL.getOneValue(sql);
		if (tStr.trim().equals("")) {
			tCount = 0;
		} else {
			tCount = Double.parseDouble(tStr);
		}

		if (tCount >= 1.0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveBL";
			tError.functionName = "dealData";
			tError.errorMessage = "集体投保单下有未回复的操作员的问题件，不能进行复核修改确认操作!";
			this.mErrors.addOneError(tError);
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
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpUWModifyAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpUWModifyAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ProposalApproveAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpUWModifyAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的GrpContNo
		mGrpContNo = (String) mTransferData.getValueByName("ProposalGrpContNo");
		if (mGrpContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpUWModifyAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中GrpContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;

	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		mApproveFlag = "0";
		// 修改险种保单表
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		String tsql = "select * From lcgrpcont where prtno='"
				+ mLCGrpContSchema.getPrtNo()
				+ "' and appflag='0' "
				//and approveflag = '9'"
				;
		tLCGrpContSet = tLCGrpContDB.executeQuery(tsql);
		if (tLCGrpContSet != null && tLCGrpContSet.size() > 0) {
			mPolSql = "update LCPol " + "set " + "ApproveFlag = '"
					+ mApproveFlag + "', " + "ModifyDate = '"
					+ PubFun.getCurrentDate() + "', " + "ModifyTime = '"
					+ PubFun.getCurrentTime() + "' " + "where prtno='"
					+ mLCGrpContSchema.getPrtNo() + "'";

			// 修改合同表
			mContSql = "update LCCont " + "set " + "ApproveFlag = '"
					+ mApproveFlag + "', " + "ModifyDate = '"
					+ PubFun.getCurrentDate() + "', " + "ModifyTime = '"
					+ PubFun.getCurrentTime() + "' " + "where prtno='"
					+ mLCGrpContSchema.getPrtNo() + "'";
			// 修改集体合同表
			mGrpContSql = "update LCGrpCont " + "set  " + "ApproveFlag = '"
					+ mApproveFlag + "', " + "ModifyDate = '"
					+ PubFun.getCurrentDate() + "', " + "ModifyTime = '"
					+ PubFun.getCurrentTime() + "' " + "where prtno='"
					+ mLCGrpContSchema.getPrtNo() + "'";
		} else {
			mGrpContSql = "select 1 from dual";
			mContSql = "select 1 from dual";
			mPolSql = "select 1 from dual";
		}
		return true;

	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("GrpContNo", mGrpContNo);
		mTransferData.setNameAndValue("PrtNo", mLCGrpContSchema.getPrtNo());
		mTransferData.setNameAndValue("AgentCode", mLCGrpContSchema
				.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", mLCGrpContSchema
				.getAgentGroup());
		mTransferData.setNameAndValue("SaleChnl", mLCGrpContSchema
				.getSaleChnl());
		mTransferData.setNameAndValue("ManageCom", mLCGrpContSchema
				.getManageCom());
		mTransferData.setNameAndValue("GrpNo", mLCGrpContSchema.getAppntNo());
		mTransferData.setNameAndValue("GrpName", mLCGrpContSchema.getGrpName());
		mTransferData.setNameAndValue("CValiDate", mLCGrpContSchema
				.getCValiDate());

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
