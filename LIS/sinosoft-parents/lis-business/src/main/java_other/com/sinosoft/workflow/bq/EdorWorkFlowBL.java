package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed ReWrite ZhangRong,FanX
 * @version 1.0
 */

public class EdorWorkFlowBL {
private static Logger logger = Logger.getLogger(EdorWorkFlowBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();
	/** 数据操作字符串 */
	private String mOperater;
	/** 往前台传输数据的容器 */
	private VData tResult = new VData();
	private String mManageCom;
	private String mOperate;
	private MMap map = new MMap();
	private boolean mFlag = true;

	public EdorWorkFlowBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 得到外部传入的数据,将数据备份到本类中
		mInputData = cInputData;
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
		// 数据提交
		logger.debug("---TbWorkFlowBL prepareOutputData---");

		if (mFlag) { // 如果置相应的标志位，不提交
			// 数据提交
			// EdorWorkFlowBLS tEdorWorkFlowBLS = new EdorWorkFlowBLS();
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tResult, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "tPubSubmit";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
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
		mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;
		if (mOperate == null || mOperate.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
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
		// 创建起始任务节点(保全申请级)

		if (mOperate.trim().equals("9999999998")) {
			if (!Execute9999999998()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;

		}
		// 保全工作流打印核保通知书活动表
		if (mOperate.trim().equals("9999999999")) {
			if (!Execute9999999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;

		}
		
		// add by jiaqiangli 2009-03-26 
		// 团单单保全试算
		if (mOperate.trim().equals("8888888882")) {
			if (!Execute8888888882()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;

		}
		
		// 个单保全试算
		if (mOperate.trim().equals("9999999992")) {
			if (!Execute9999999992()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;

		}
		// 执行保全工作流待人工核保活动表任务
		else if (mOperate.trim().equals("0000000000")) {
			if (!Execute0000000000()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;

		}
		// 执行保全工作流扫描申请活动表任务
		else if (mOperate.trim().equals("0000000001")) {
			if (!Execute0000000001()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;
		}
		// 执行保全工作流无扫描申请活动表任务
		else if (mOperate.trim().equals("0000000002")) {
			if (!Execute0000000002()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			return true;

		}
		// 执行保全工作流保全试算任务 add by jiaqiangli 2009-03-26 团单保全试算
		else if (mOperate.trim().equals("0000008092")) {
			if (!Execute0000008092()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			return true;

		}
		// 执行保全工作流保全试算任务
		else if (mOperate.trim().equals("0000000092")) {
			if (!Execute0000000092()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			return true;

		} else if (mOperate.trim().equals("0000000005")) {
			if (!Execute0000000005()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;
		}
		// else if (mOperate.trim().equals("0000000007"))
		// {
		// if (!Execute0000000007())
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(mActivityOperator.mErrors);
		// return false;
		// }
		// return true;
		// }
		// 执行保全工作流回收体检通知书活动表任务
		else if (mOperate.trim().equals("0000000011")) {
			if (!Execute0000000011()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;

		}

		// 保全工作流打印核保通知书活动表
		else if (mOperate.trim().equals("0000000012")) {
			if (!Execute0000000012()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;
		}
		// 保全工作流回收打印生调通知书活动表
		else if (mOperate.trim().equals("0000000013")) {
			if (!Execute0000000013()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;
		}
		// 执行保全工作流补打体检通知书活动表任务
		else if (mOperate.trim().equals("0000000014")) {
			if (!Execute0000000014()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;

		}

		// 保全工作流补打核保通知书活动表
		else if (mOperate.trim().equals("0000000015")) {
			if (!Execute0000000015()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;

		}

		// 保全工作流补打生调通知书活动表
		else if (mOperate.trim().equals("0000000016")) {
			if (!Execute0000000016()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;
		}
		// 保全工作批单申请起始活动表
		else if (mOperate.trim().equals("0000000017")) {
			if (!Execute0000000017()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;
		} else if (mOperate.trim().equals("0000000019")) {
			if (!Execute0000000019()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				logger.debug("class信息: EdorWorkFloeBL -->执行Execute0000000019()出错");
				return false;
			}
			return true;

		} else if (mOperate.trim().equals("0000000022")) {
			if (!Execute0000000022()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				logger.debug("class信息: EdorWorkFloeBL -->执行Execute0000000022()出错");
				return false;
			}
			return true;
		}

		// <!-- XinYQ added on 2006-01-16 : 保全抽检处理 : BGN -->
		else if (mOperate.trim().equals("0000000081")) {
			if (!Execute0000000081()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				logger.debug("class信息: EdorWorkFloeBL --> 执行 Execute0000000022() 出错");
				return false;
			}
			return true;
		}
		// <!-- XinYQ added on 2006-01-16 : 保全抽检处理 : END -->

		// 团单保全创建起始节点
		else if (mOperate.trim().equals("8888888888")) {
			if (!Execute8888888888()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			return true;

		}

		// 执行团体保全工作流无扫描申请活动表任务
		else if (mOperate.trim().equals("0000008001")) {
			if (!Execute0000008001()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			return true;

		}

		// 执行团体保全工作流无扫描申请活动表任务
		else if (mOperate.trim().equals("0000008002")) {
			if (!Execute0000008002()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			return true;

		} else {
			if (!Execute()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			return true;
		}
	}

	/**
	 * 执行保全工作流待人工核保活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000000() { // *
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000000";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000000";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000000", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			// 获得执行保全工作流待人工核保活动表任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					for (int j = 0; j < tempVData.size(); j++) {
						mResult.add(tempVData.get(i)); // 取出Map值
					}
				}
			}

			// 产生执行完保全工作流待人工核保活动表任务后的任务节点

			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000000", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}

			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行保全工作流待人工核保活动表任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		} // */

		return true;

	}

	/**
	 * 执行保全工作流发体检通知书活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000001() {
		mResult.clear();
		VData tVData = new VData();
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		if (tMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000001", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			// 获得执行发放体检通知书任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行保全人工核保处体检通知书录入任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					mResult.add(tempVData); // 取出Map值
				}
			}

			// 产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
			ActivityOperator tActivityOperator = new ActivityOperator();
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000001", mInputData)) {
				VData tmpVData = new VData();
				tmpVData = tActivityOperator.getResult();
				if (tmpVData != null && tmpVData.size() > 0) {
					mResult.add(tmpVData);
					tmpVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行保全人工核保处体检通知书录入任务后,产生下一工作任务节点出错!";
				this.mErrors.addOneError(tError);
				return false;

			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流无扫描申请活动表任务 不删除上一节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000002() {
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		if (tMissionID == null || tMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null || tSubMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 执行保全工作流无扫描申请任务
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000002", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			// 获得执行保全工作流无扫描申请任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					mResult.add(tempVData); // 取出Map值
				}
			}

			// 产生执行人工核保处加费录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000002", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// <!-- XinYQ added on 2006-01-16 : 保全抽检处理 : BGN -->
	/*
	 * 执行保全抽检处理任务 如果抽检结论为"合格"则不删除上一节点 @param null @return boolean
	 */
	private boolean Execute0000000081() {
		// 接收数据变量
		String sMissionID = (String) mTransferData.getValueByName("MissionID");
		String sSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		String sCheckFlag = (String) mTransferData.getValueByName("CheckFlag");
		// 检查工作流节点
		if (sMissionID == null || sMissionID.trim().equals("")
				|| sSubMissionID == null || sSubMissionID.trim().equals("")) {
			CError.buildErr(this, "无法获取工作流任务节点信息！");
			logger.debug("\t@> EdorWorkFlowBL.Execute0000000081() : MissionID, SubMissionID == null ！");
			return false;
		}
		// 检查抽检结论
		if (sCheckFlag == null || sCheckFlag.trim().equals("")) {
			CError.buildErr(this, "无法获取抽检结论信息！");
			logger.debug("\t@> EdorWorkFlowBL.Execute0000000081() : CheckFlag == null ！");
			return false;
		} else
			sCheckFlag = sCheckFlag.trim();
		// 先清空结果数据容器
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		try {
			// 执行当前节点任务
			if (!mActivityOperator.ExecuteMission(sMissionID, sSubMissionID,
					mOperate, mInputData)) {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					mResult.add(tempVData);
				}
			}
			// 由描述去控制是否创建下一节点
			if (tActivityOperator.CreateNextMission(sMissionID, sSubMissionID,
					mOperate, mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			// 选择性删除当前 Mission
			if (sCheckFlag.equals("1")) {
				tActivityOperator = new ActivityOperator();
				if (tActivityOperator.DeleteMission(sMissionID, sSubMissionID,
						mOperate, mInputData)) {
					VData tempVData = new VData();
					tempVData = tActivityOperator.getResult();
					if ((tempVData != null) && (tempVData.size() > 0)) {
						mResult.add(tempVData);
						tempVData = null;
					}
				} else {
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			} // sCheckFlag.equals("1")
			else {
				String UpdateSQL = "update LWMission "
						+ "set ActivityStatus = '2' " + "where MissionID = '"
						+ "?sMissionID?" + "' " + "and SubMissionID = '"
						+ "?sSubMissionID?" + "' "
						+ "and ActivityID = '0000000081'";
				MMap tMap = new MMap();
				
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(UpdateSQL);
				sqlbv1.put("sMissionID",sMissionID);
				sqlbv1.put("sSubMissionID",sSubMissionID);
				tMap.put(sqlbv1, "UPDATE");
				VData tempVData = new VData();
				tempVData.add(tMap);
				mResult.add(tempVData);
				// 垃圾处理
				tMap = null;
				tempVData = null;
			} // sCheckFlag.equals("2")
		} catch (Exception ex) {
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError.buildErr(this, "工作流引擎执行保全抽检活动表任务出错！");
			return false;
		}
		// 返回当前分支处理结果
		return true;
	}

	// <!-- XinYQ added on 2006-01-16 : 保全抽检处理 : END -->

	/**
	 * add by jiaqiangli 2009-03-26 团单保全试算
	 * 执行保全工作流保全试算任务 不删除上一节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000008092() {
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		if (tMissionID == null || tMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null || tSubMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 执行保全工作流无扫描申请任务
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000008092", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			// 获得执行保全工作流无扫描申请任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					mResult.add(tempVData); // 取出Map值
				}
			}

			// 产生执行人工核保处加费录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000008092", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}
	
	/**
	 * 执行保全工作流保全试算任务 不删除上一节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000092() {
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		if (tMissionID == null || tMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null || tSubMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 执行保全工作流无扫描申请任务
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000092", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			// 获得执行保全工作流无扫描申请任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					mResult.add(tempVData); // 取出Map值
				}
			}

			// 产生执行人工核保处加费录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000092", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流人工核保处特约录入活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	/*
	 * private boolean Execute0000000003() { mResult.clear(); VData tVData = new
	 * VData(); ActivityOperator tActivityOperator = new ActivityOperator();
	 * //获得当前工作任务的任务ID String tMissionID = (String)
	 * mTransferData.getValueByName("MissionID"); String tSubMissionID =
	 * (String) mTransferData.getValueByName( "SubMissionID"); if (tMissionID ==
	 * null) { // @@错误处理 //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
	 * CError tError = new CError(); tError.moduleName = "EdorWorkFlowBL";
	 * tError.functionName = "dealData"; tError.errorMessage =
	 * "保单数据前台传输TransferData中的必要参数MissionID失败!";
	 * this.mErrors.addOneError(tError); return false; }
	 * 
	 * if (tSubMissionID == null) { // @@错误处理 //this.mErrors.copyAllErrors(
	 * tLCPolDB.mErrors ); CError tError = new CError(); tError.moduleName =
	 * "EdorWorkFlowBL"; tError.functionName = "dealData"; tError.errorMessage =
	 * "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
	 * this.mErrors.addOneError(tError); return false; }
	 * 
	 * //执行人工核保处特约活动表 try { if (!mActivityOperator.ExecuteMission(tMissionID,
	 * tSubMissionID, "0000000003", mInputData)) { // @@错误处理
	 * this.mErrors.copyAllErrors(mActivityOperator.mErrors); return false; }
	 * 
	 * //获得执行人工核保处特约录入任务的结果 tVData = mActivityOperator.getResult(); if (tVData ==
	 * null) { // @@错误处理 this.mErrors.copyAllErrors(mActivityOperator.mErrors);
	 * CError tError = new CError(); tError.moduleName = "EdorWorkFlowBL";
	 * tError.functionName = "dealData"; tError.errorMessage =
	 * "工作流引擎执行保全工作流人工核保处特约录入任务出错!"; this.mErrors.addOneError(tError); return
	 * false; }
	 * 
	 * //mResult.add(tVData); if (tVData != null) { for (int i = 0; i <
	 * tVData.size(); i++) { VData tempVData = new VData(); tempVData = (VData)
	 * tVData.get(i); mResult.add(tempVData); //取出Map值 } }
	 * 
	 * //产生执行人工核保处特约录入任务后等待发放核保通知书任务节点 if
	 * (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
	 * "0000000003", mInputData)) { VData tempVData = new VData(); tempVData =
	 * tActivityOperator.getResult(); if (tempVData != null && tempVData.size() >
	 * 0) { mResult.add(tempVData); tempVData = null; } } else {
	 * this.mErrors.copyAllErrors(mActivityOperator.mErrors); return false;
	 *  }
	 *  } catch (Exception ex) { // @@错误处理
	 * this.mErrors.copyAllErrors(mActivityOperator.mErrors); CError tError =
	 * new CError(); tError.moduleName = "EdorWorkFlowBL"; tError.functionName =
	 * "dealData"; tError.errorMessage = "工作流引擎工作出现异常!";
	 * this.mErrors.addOneError(tError); return false; }
	 * 
	 * return true; }
	 */
	/**
	 * 执行保全工作流发发生调通知书活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000004() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行人工核保处特约活动表
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000004", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行人工核保处发生调通知书任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行人工核保处特约录入任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行保全工作流人工核保处发生调通知书任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					// for(int j=0;j< tempVData.size() ;j++)
					// {
					mResult.add(tempVData); // 取出Map值
					// }
				}
			}

			// 产生执行人工核保处特约录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000004", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage =
				// "工作流引擎执行保全人工核保处发生调通知书任务后,产生下一工作任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

			// 删除发生调通知书任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					"0000000004", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行保全人工核保处发生调通知书任务后,删除该任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流发核保通知书活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000005() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000005";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000005";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000005", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			// 获得执行保全工作流待人工核保活动表任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					for (int j = 0; j < tempVData.size(); j++) {
						mResult.add(tempVData.get(i)); // 取出Map值
					}
				}
			}

			// 产生执行完保全工作流待人工核保活动表任务后的任务节点

			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000005", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}

			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行保全工作流待人工核保活动表任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		} // */

		return true;

	}

	/**
	 * 执行保全工作流打印体检通知书活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000006() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行人工核保处打印体检通知书
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000006", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行人工核保处打印体检通知书任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行人工核保处特约录入任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行保全工作流人工核保处打印体检通知书任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					// for(int j=0;j< tempVData.size() ;j++)
					// {
					mResult.add(tempVData); // 取出Map值
					// }
				}
			}

			// 产生执行人工核保处特约录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000006", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage =
				// "工作流引擎执行保全人工核保处打印体检通知书任务后,产生下一工作任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

			// 删除发核保通知书任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					"0000000006", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行保全人工核保处打印体检通知书任务后,删除该任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流保全复核 根据抽检结果控制是否删除本节点
	 */
	private boolean Execute0000000007() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000000";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
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
					mResult.add(tempVData);
				}
			}

			// 产生执行完承保工作流待人工核保活动表任务后的任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					mOperate, mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);

				return false;
			}

			String sNeedCheckFlag = "";
			try {
				TransferData checkTransferData = (TransferData) mInputData
						.getObjectByObjectName("TransferData", 0);

				// 获得抽检抽检标志
				sNeedCheckFlag = (String) checkTransferData
						.getValueByName("CheckFlag");
				logger.debug("--------- sNeedCheckFlag at EdorWorkFlowBL -------------------"
								+ sNeedCheckFlag);
			} catch (Exception ex) {
				sNeedCheckFlag = "Y"; // 出现异常直接抽检
			}
			if (!sNeedCheckFlag.equals("Y")) // 需要抽检，则不删除本节点
			{
				tActivityOperator = new ActivityOperator();
				if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
						mOperate, mInputData)) {
					VData tempVData = new VData();
					tempVData = tActivityOperator.getResult();
					if ((tempVData != null) && (tempVData.size() > 0)) {
						mResult.add(tempVData);
						tempVData = null;
					}
				} else {
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);

					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理

			this.mErrors.copyAllErrors(mActivityOperator.mErrors);

			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 执行保全工作流打印面见通知书活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000008() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行打印面见通知书活动表
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000008", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行执行打印面见通知书活动任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行人工核保处打印面见通知书活动任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行打印面见通知书活动任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					// for(int j=0;j< tempVData.size() ;j++)
					// {
					mResult.add(tempVData); // 取出Map值
					// }
				}
			}

			// 产生执行人工核保处特约录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000008", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage =
				// "工作流引擎执行保全人工核保处发生调通知书任务后,产生下一工作任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

			// 删除发核保通知书任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					"0000000008", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行保全人工核保处发核保通知书任务后,删除该任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流核保确认活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000010() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行人工核保处特约活动表
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000010", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行人工保全工作流核保确认任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行人工核保处特约录入任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行保全工作流人工保全工作流核保确认任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					// for(int j=0;j< tempVData.size() ;j++)
					// {
					mResult.add(tempVData); // 取出Map值
					// }
				}
			}

			// 产生执行人工核保处特约录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000010", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎保全工作流人工保全工作流核保确认任务后获得下一个工作节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 删除发核保通知书任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					"0000000010", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行保全人工核保处发核保通知书任务后,删除该任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流体检通知书回收活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000011() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 执行人工核保处特约活动表
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000011", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行人工保全工作流体检通知书回收任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行人工核保处特约录入任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行保全工作流人工保全工作流体检通知书回收任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					// for(int j=0;j< tempVData.size() ;j++)
					// {
					mResult.add(tempVData); // 取出Map值
					// }
				}
			}

			// 产生执行人工核保处特约录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000011", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage =
				// "工作流引擎保全工作流人工保全工作流体检通知书回收任务后获得下一个工作节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 删除发核保通知书任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					"0000000011", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行保全人工核保处发核保通知书任务后,删除该任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流核保通知书回收活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000012() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 执行人工核保处特约活动表
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000012", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行人工保全工作流核保通知书回收任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行人工核保处特约录入任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行保全工作流人工保全工作流核保通知书回收任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					// for(int j=0;j< tempVData.size() ;j++)
					// {
					mResult.add(tempVData); // 取出Map值
					// }
				}
			}

			// 产生执行人工核保处特约录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000012", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage =
				// "工作流引擎保全工作流人工保全工作流核保通知书回收任务后获得下一个工作节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 删除发核保通知书任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					"0000000012", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行保全人工核保处发核保通知书任务后,删除该任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流回复面见通知书活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000013() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行回复面见通知书活动表
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000013", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行执行回复面见通知书活动任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行人工核保处回复面见通知书活动任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行打印面见通知书活动任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					// for(int j=0;j< tempVData.size() ;j++)
					// {
					mResult.add(tempVData); // 取出Map值
					// }
				}
			}

			// 产生执行人工核保处特约录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000013", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage =
				// "工作流引擎执行保全人工核保处回复生调通知书任务后,产生下一工作任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

			// 删除发核保通知书任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					"0000000013", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行保全人工核保处回复生调通知书任务后,删除该任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流补打体检通知书活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000014() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行人工核保处打印体检通知书
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000014", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行人工核保处打印体检通知书任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行人工核保处特约录入任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行保全工作流人工核保处打印体检通知书任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					// for(int j=0;j< tempVData.size() ;j++)
					// {
					mResult.add(tempVData); // 取出Map值
					// }
				}
			}

			// 产生执行人工核保处特约录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000014", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage =
				// "工作流引擎执行保全人工核保处打印体检通知书任务后,产生下一工作任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

			// 删除发核保通知书任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					"0000000014", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行保全人工核保处打印体检通知书任务后,删除该任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流补打核保通知书活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000015() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 执行人工核保处特约活动表
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000015", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行执行补打核保通知书活动任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行人工核保处打印核保通知书活动任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行补打核保通知书活动任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					// for(int j=0;j< tempVData.size() ;j++)
					// {
					mResult.add(tempVData); // 取出Map值
					// }
				}
			}

			// 产生执行人工核保处特约录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000015", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage =
				// "工作流引擎执行保全人工核保处补打核保通知书任务后,产生下一工作任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

			// 删除发核保通知书任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					"0000000015", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行保全人工核保处发核保通知书任务后,删除该任务节点出错!";
				// this.mErrors .addOneError(tError) ;
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流补打面见通知书活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000000016() {
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行打印面见通知书活动表
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000016", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "EdorWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "工作流引擎执行执行补打面见通知书活动任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行人工核保处打印面见通知书活动任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行补打面见通知书活动任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					// for(int j=0;j< tempVData.size() ;j++)
					// {
					mResult.add(tempVData); // 取出Map值
					// }
				}
			}

			// 产生执行人工核保处特约录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000016", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;

			}

			// 删除发核保通知书任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					"0000000016", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean Execute0000000017() { // *
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000017";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000017";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000017", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			// 获得执行保全工作流待人工核保活动表任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					for (int j = 0; j < tempVData.size(); j++) {
						mResult.add(tempVData.get(i)); // 取出Map值
					}
				}
			}

			// 产生执行完保全工作流待人工核保活动表任务后的任务节点

			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000017", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}

			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行保全工作流待人工核保活动表任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		} // */

		return true;

	}

	/**
	 * 保全工作流申请级体检通知录入
	 * 
	 * @return boolean
	 */
	private boolean Execute0000000019() { // *
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000019";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000019";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("class信息：EdorWorkFlowBL --->MissionID=" + tMissionID);
		logger.debug("class信息：EdorWorkFlowBL --->SubMissionID="
				+ tSubMissionID);
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000019", mInputData)) {
				logger.debug("class信息：EdorWorkFlowBL-->Result = false");
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			} else {
				logger.debug("class信息：EdorWorkFlowBL-->Result = true");
			}

			// 获得执行保全工作流体检通知录入活动表任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {

				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					logger.debug("tempVData.size()=" + tempVData.size());
					mResult.add(tempVData);
				}
			}

			// 产生执行完保全工作流体检通知录入活动表任务后的任务节点

			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000019", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				logger.debug("after create next mission.....");
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}

			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行保全工作流体检通知录入保活动表任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		} // */

		return true;

	}

	/**
	 * 保全工作流申请级生调通知录入
	 * 
	 * @return boolean
	 */
	private boolean Execute0000000022() { // *
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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000022";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000022";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("class信息：EdorWorkFlowBL --->MissionID=" + tMissionID);
		logger.debug("class信息：EdorWorkFlowBL --->SubMissionID="
				+ tSubMissionID);
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000000022", mInputData)) {
				logger.debug("class信息：EdorWorkFlowBL-->Result = false");
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			} else {
				logger.debug("class信息：EdorWorkFlowBL-->Result = true");
			}

			// 获得执行保全工作流生调通知录入活动表任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {

				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					logger.debug("tempVData.size()=" + tempVData.size());
					mResult.add(tempVData);
				}
			}

			// 产生执行完保全工作流生调通知录入活动表任务后的任务节点

			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000000022", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				logger.debug("after create next mission.....");
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}

			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行保全工作流生调通知录入保活动表任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		} // */

		return true;

	}

	/**
	 * 创建起始任务节点(保全申请级) 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute9999999998() {
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		try {
			// 产生执行保全申请起始任务节点和核保结论节点
			logger.debug("come to Execute99999999999999()...");
			if (tActivityOperator.CreateStartMission("0000000000",
					"0000000017", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				mResult.add(tempVData);
				tempVData = null;
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute9999999998";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 创建起始任务节点（个单保全） 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute9999999999() {
		logger.debug("start execute9999999999...");
		mResult.clear();
		ActivityOperator tActivityOperator = new ActivityOperator();
		try {
			// ---
			String strLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String strEdorAcceptNo = PubFun1.CreateMaxNo("EdorAcceptNo",
					strLimit);

			if (StrTool.compareString(strEdorAcceptNo, "")) {
				CError.buildErr(this, "生成保全申请号错误！");
				return false;
			} else {
				mTransferData.setNameAndValue("EdorAcceptNo", strEdorAcceptNo);
				mTransferData.setNameAndValue("ManageCom",
						mGlobalInput.ManageCom);
				mTransferData.setNameAndValue("AppntName", "");
				mTransferData.setNameAndValue("PaytoDate", "");
				mTransferData.setNameAndValue("OtherNo", "");
				mTransferData.setNameAndValue("OtherNoType", "");
				mTransferData.setNameAndValue("EdorAppName", "");
				mTransferData.setNameAndValue("Apptype", "");
				mTransferData.setNameAndValue("EdorAppDate", "");
				mTransferData.setNameAndValue("DefaultOperator", "");
			}

			if (tActivityOperator.CreateStartMission_NoScan("0000000000",
					"0000000002", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				tempVData.add(mTransferData);
				mResult.add(tempVData);
				tempVData = null;

			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "CreateStartMission";
				tError.errorMessage = "工作流引擎工作出现异常!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute9999999999";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * add by jiaqiangli 2009-03-26
	 * 创建起始任务节点（团单单保全试算） 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute8888888882() {
		logger.debug("start execute8888888882...");
		mResult.clear();
		ActivityOperator tActivityOperator = new ActivityOperator();
		try {
			// ---
			String strLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String strEdorTestAcceptNo = PubFun1.CreateMaxNo("EDORTESTNO",strLimit);

			if (StrTool.compareString(strEdorTestAcceptNo, "")) {
				CError.buildErr(this, "生成保全试算流水号错误!");
				return false;
			} 
			else {
				mTransferData.setNameAndValue("EdorAcceptNo",strEdorTestAcceptNo);
				mTransferData.setNameAndValue("ManageCom",mGlobalInput.ManageCom);
				mTransferData.setNameAndValue("AppntName", "");
				mTransferData.setNameAndValue("PaytoDate", "");
				mTransferData.setNameAndValue("OtherNo", "");
				mTransferData.setNameAndValue("OtherNoType", "");
				mTransferData.setNameAndValue("EdorAppName", "");
				mTransferData.setNameAndValue("Apptype", "");
				mTransferData.setNameAndValue("EdorAppDate", "");
			}

			if (tActivityOperator.CreateStartMission_NoScan("0000000000","0000008092", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				tempVData.add(mTransferData);
				mResult.add(tempVData);
				tempVData = null;

			} 
			else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "CreateStartMission";
				tError.errorMessage = "工作流引擎工作出现异常!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} 
		catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute9999999999";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	/**
	 * 创建起始任务节点（个单保全试算） 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute9999999992() {
		logger.debug("start execute9999999992...");
		mResult.clear();
		ActivityOperator tActivityOperator = new ActivityOperator();
		try {
			// ---
			String strLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String strEdorTestAcceptNo = PubFun1.CreateMaxNo("EDORTESTNO",
					strLimit);

			if (StrTool.compareString(strEdorTestAcceptNo, "")) {
				CError.buildErr(this, "生成保全试算流水号错误!");
				return false;
			} else {
				mTransferData.setNameAndValue("EdorAcceptNo",
						strEdorTestAcceptNo);
				mTransferData.setNameAndValue("ManageCom",
						mGlobalInput.ManageCom);
				mTransferData.setNameAndValue("AppntName", "");
				mTransferData.setNameAndValue("PaytoDate", "");
				mTransferData.setNameAndValue("OtherNo", "");
				mTransferData.setNameAndValue("OtherNoType", "");
				mTransferData.setNameAndValue("EdorAppName", "");
				mTransferData.setNameAndValue("Apptype", "");
				mTransferData.setNameAndValue("EdorAppDate", "");
			}

			if (tActivityOperator.CreateStartMission_NoScan("0000000000",
					"0000000092", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				tempVData.add(mTransferData);
				mResult.add(tempVData);
				tempVData = null;

			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "CreateStartMission";
				tError.errorMessage = "工作流引擎工作出现异常!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute9999999999";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 创建起始任务节点（团单保全） 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute8888888888() {
		logger.debug("start execute8888888888...");
		mResult.clear();
		ActivityOperator tActivityOperator = new ActivityOperator();
		try {
			// ---
			String strLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			//团险的受理号修改为：43
			String strEdorAcceptNo = PubFun1.CreateMaxNo("EDORGRPAPPNO",
					strLimit);

			if (StrTool.compareString(strEdorAcceptNo, "")) {
				CError.buildErr(this, "生成保全申请号错误！");
				return false;
			} else {
				mTransferData.setNameAndValue("EdorAcceptNo", strEdorAcceptNo);
				mTransferData.setNameAndValue("ManageCom",
						mGlobalInput.ManageCom);
				mTransferData.setNameAndValue("AppntName", "");
				mTransferData.setNameAndValue("PaytoDate", "");
				mTransferData.setNameAndValue("OtherNo", "");
				mTransferData.setNameAndValue("OtherNoType", "");
				mTransferData.setNameAndValue("EdorAppName", "");
				mTransferData.setNameAndValue("Apptype", "");
				mTransferData.setNameAndValue("EdorAppDate", "");
			}

			if (tActivityOperator.CreateStartMission_NoScan("0000000000",
					"0000008002", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				tempVData.add(mTransferData);
				mResult.add(tempVData);
				tempVData = null;

			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "CreateStartMission";
				tError.errorMessage = "工作流引擎工作出现异常!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute8888888888";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 执行保全工作流无扫描申请活动表任务 不删除上一节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000008002() {
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		if (tMissionID == null || tMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null || tSubMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 执行保全工作流无扫描申请任务
		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000008002", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			// 获得执行保全工作流无扫描申请任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					mResult.add(tempVData); // 取出Map值
				}
			}

			// 产生执行人工核保处加费录入任务后等待发放核保通知书任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000008002", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;

			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全工作流发体检通知书活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000008001() {
		mResult.clear();
		VData tVData = new VData();
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		if (tMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单数据前台传输TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000008001", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			// 获得执行发放体检通知书任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行保全人工核保处体检通知书录入任务出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// mResult.add(tVData);
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					mResult.add(tempVData); // 取出Map值
				}
			}

			// 产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
			ActivityOperator tActivityOperator = new ActivityOperator();
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000008001", mInputData)) {
				VData tmpVData = new VData();
				tmpVData = tActivityOperator.getResult();
				if (tmpVData != null && tmpVData.size() > 0) {
					mResult.add(tmpVData);
					tmpVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "EdorWorkFlowBL";
				tError.functionName = "dealData";
				tError.errorMessage = "工作流引擎执行保全人工核保处体检通知书录入任务后,产生下一工作任务节点出错!";
				this.mErrors.addOneError(tError);
				return false;

			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

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
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "Execute0000000000";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
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
					mResult.add(tempVData);
				}
			}

			// 产生执行完承保工作流待人工核保活动表任务后的任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					mOperate, mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					mResult.add(tempVData);
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
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);

				return false;
			}
		} catch (Exception ex) {
			// @@错误处理

			this.mErrors.copyAllErrors(mActivityOperator.mErrors);

			CError tError = new CError();
			tError.moduleName = "EdorWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareOutputData() {
		try {
			MMap tmap = new MMap();
			LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
			for (int i = 0; i < mResult.size(); i++) {
				VData tData = new VData();
				tData = (VData) mResult.get(i);
				MMap map = (MMap) tData.getObjectByObjectName("MMap", 0);
				tmap.add(map);
			}
			mResult.add(tLPEdorAppSchema);
			
			mResult.clear();
			mResult.add(mTransferData);
			tResult.add(tmap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "XinYQ";
		tG.ManageCom = "86";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("MissionID", "00000000000000149945");
		tTransferData.setNameAndValue("SubMissionID", "1");

		// LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		// tLPEdorAppSchema.setEdorAcceptNo("86000000000788"); //保全受理号
		// tLPEdorAppSchema.setOtherNo("230110000002303"); //申请号码
		// tLPEdorAppSchema.setOtherNoType("3"); //申请号码类型
		// tLPEdorAppSchema.setEdorAppName("王怡"); //申请人名称
		// tLPEdorAppSchema.setAppType("1"); //申请方式
		// tLPEdorAppSchema.setEdorAppDate("2005-07-20"); //批改申请日期
		// tLPEdorAppSchema.setBankCode("");
		// tLPEdorAppSchema.setBankAccNo("");
		// tLPEdorAppSchema.setAccName("");
		// tLPEdorAppSchema.setManageCom(tG.ManageCom); //管理机构
		// tLPEdorAppSchema.setEdorState("3");

		// 创建保全确认、复核修改节点的数据元素
		tTransferData.setNameAndValue("EdorAcceptNo", "6120060111000069");
		// tTransferData.setNameAndValue("OtherNo", "230110000003016");
		// tTransferData.setNameAndValue("OtherNoType", "3");
		// tTransferData.setNameAndValue("EdorAppName", "Jier");
		// tTransferData.setNameAndValue("Apptype", "1");
		// tTransferData.setNameAndValue("ManageCom", tG.ManageCom);
		// tTransferData.setNameAndValue("EdorAppDate", "2005-07-20");
		tTransferData.setNameAndValue("CheckFlag", "2");
		tTransferData.setNameAndValue("CheckRemark", "XinYQ 测试 0000000081");
		// tTransferData.setNameAndValue("ApproveFlag", "1");
		// tTransferData.setNameAndValue("ApproveContent", "test");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		// tVData.add(tLPEdorAppSchema);

		EdorWorkFlowBL tEdorWorkFlowBL = new EdorWorkFlowBL();

		if (!tEdorWorkFlowBL.submitData(tVData, "0000000081")) {
			logger.debug(tEdorWorkFlowBL.mErrors.getError(0).errorMessage);
		}
	}
}
