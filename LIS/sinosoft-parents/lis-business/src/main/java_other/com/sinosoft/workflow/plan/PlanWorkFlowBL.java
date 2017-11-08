package com.sinosoft.workflow.plan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class PlanWorkFlowBL {
private static Logger logger = Logger.getLogger(PlanWorkFlowBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private MMap mMMap = new MMap();

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
	private String mOperate;

	public PlanWorkFlowBL() {
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

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		// 准备给后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) { // 数据提交
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			this.buildError("submitData", "数据提交失败!");
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
		mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if ((mOperater == null) || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if ((mManageCom == null) || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mOperate = cOperate;
		if ((mOperate == null) || mOperate.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate任务节点编码失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (mOperate.trim().equals("0000002800")) {
			// 产品组合申请
			if (!Execute0000002800()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else {
			if (!Execute()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		return true;
	}

	/**
	 * 产生产品组合工作流的第一个节点
	 * 
	 * @return boolean
	 */
	private boolean Execute0000002800() {
		mResult.clear();
		ActivityOperator tActivityOperator = new ActivityOperator();
		try {
			// 产生第一个节点
			if (tActivityOperator.CreateStartMission("0000000004",
					"0000002801", mInputData)) {
				mGlobalInput = ((GlobalInput) mInputData.getObjectByObjectName(
						"GlobalInput", 0));
				VData tempVData = new VData();
				MMap tMap = new MMap();
				tempVData = tActivityOperator.getResult();
				tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				mResult.add(tMap);
				tempVData = null;
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);

			String tMsg = ex.toString();
			tMsg = tMsg.replaceAll("\"", "");
			this.buildError("Execute0000002800", "工作流处理异常：" + tMsg);

			return false;
		}
		return true;
	}

	/**
	 * 执行承保工作流待人工核保活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute() {
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();

		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		if (tMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskWorkFlowBL";
			tError.functionName = "Execute0000000000";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskWorkFlowBL";
			tError.functionName = "Execute0000000000";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					mOperate, mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);

				return false;
			}
			// 获得执行承保工作流待人工核保活动表任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					MMap tMap = (MMap) tempVData.getObjectByObjectName("MMap",
							0);
					mMMap.add(tMap);
				}
			}

			// 产生执行完承保工作流待人工核保活动表任务后的任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					mOperate, mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					MMap tMap = (MMap) tempVData.getObjectByObjectName("MMap",
							0);
					mMMap.add(tMap);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);

				return false;
			}

			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					mOperate, mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					MMap tMap = (MMap) tempVData.getObjectByObjectName("MMap",
							0);
					mMMap.add(tMap);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			mResult.add(mMMap);
		} catch (Exception ex) {
			// @@错误处理

			this.mErrors.copyAllErrors(mActivityOperator.mErrors);

			CError tError = new CError();
			tError.moduleName = "AskWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// */
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareOutputData() {
		// mInputData.add( mGlobalInput );
		return true;
	}

	private void buildError(String cFunName, String cMsg) {
		CError tError = new CError();
		tError.moduleName = "AskWorkFlowBL";
		tError.functionName = cFunName;
		tError.errorMessage = cMsg;
		this.mErrors.addOneError(tError);
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "8621";
		tG.Operator = "001";
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();

		// String tManageCom = "8621";
		// String tContPlanCode = PubFun1.CreateMaxNo("CONTPLANCODE",
		// tManageCom);
		//
		// //prepare data for workflow

		// tTransferData.setNameAndValue("ContPlanCode", tContPlanCode);
		// tTransferData.setNameAndValue("ContPlanName", "测试");
		// tTransferData.setNameAndValue("ManageCom", tManageCom);
		// tTransferData.setNameAndValue("InputDate", PubFun.getCurrentDate());
		// tTransferData.setNameAndValue("Operator", tG.Operator);

		tTransferData.setNameAndValue("ContPlanCode", "21000039");
		tTransferData.setNameAndValue("MissionID", "00000000000000001619");
		tTransferData.setNameAndValue("SubMissionID", "1");
		tTransferData.setNameAndValue("ActivityID", "0000002803");
		tTransferData.setNameAndValue("ApproveFlag", "9");

		tVData.add(tTransferData);
		tVData.add(tG);

		try {
			PlanWorkFlowBL tPlanWorkFlowBL = new PlanWorkFlowBL();
			if (tPlanWorkFlowBL.submitData(tVData, "0000002803") == false) {
				logger.debug("" + tPlanWorkFlowBL.mErrors.getFirstError());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug(ex.toString());
		}

	}
}
