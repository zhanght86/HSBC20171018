package com.sinosoft.workflow.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.LDContTimeBL;
import com.sinosoft.workflowengine.ActivityOperator;

/**
 * <p>
 * Title:
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
 * @author HYQ modify by zhangxing 2005-05-17
 * @version 1.0
 */
public class GrpTbWorkFlowBL {
private static Logger logger = Logger.getLogger(GrpTbWorkFlowBL.class);
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
	private String mManageCom;
	private String mOperate;

	public GrpTbWorkFlowBL() {
	}

	
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		
		if (!mPubLock.lock(tPrtNo, "LW0002")) {
			return false;
		}
		return true;
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

		logger.debug("---GrpTbWorkFlowBL dealData---");

		// 准备给后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("---GrpTbWorkFlowBL prepareOutputData---");

		// 数据提交
		GrpTbWorkFlowBLS tGrpTbWorkFlowBLS = new GrpTbWorkFlowBLS();
		logger.debug("Start GrpTbWorkFlowBL Submit...");

		if (!tGrpTbWorkFlowBLS.submitData(mResult, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpTbWorkFlowBLS.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpTbWorkFlowBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---GrpTbWorkFlowBLS commitData End ---");

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
			tError.moduleName = "GrpTbWorkFlowBL";
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
			tError.moduleName = "GrpTbWorkFlowBL";
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
			tError.moduleName = "GrpTbWorkFlowBL";
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
			tError.moduleName = "GrpTbWorkFlowBL";
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
		// 承保工作流打印核保通知书活动表
		if (mOperate.trim().equals("6999999999")) {
			if (!Execute6999999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "GrpTbWorkFlowBL";
				// tError.functionName = "dealData";
				// tError.errorMessage = "产生承保工作流待人工核保活动表起始任务失败!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}
		} else if (mOperate.trim().equals("7699999999")) {
			if (!Execute7699999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;

			}
			return true;
		} else if (mOperate.trim().equals("0000011004")) {
			logger.debug("---00000011004");
			if (!Execute0000011004()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;

			}
			return true;
		}

		else if (mOperate.trim().equals("9999992230")) {
			if (!Execute9999992230()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		else {
			if (!Execute()) {
				// @@错误处理
				// this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		return true;
	}

	/**
	 * 执行承保工作流待人工核保活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute() { // *
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
			tError.moduleName = "GrpTbWorkFlowBL";
			tError.functionName = "Execute0000000000";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpTbWorkFlowBL";
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
			tError.moduleName = "GrpTbWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// ====ADD=======zhangtao=======2005-04-09==============BGN=====================
		LDCodeSet tLDCodeSet = new LDCodeSet();
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("busitype");
		tLDCodeDB.setOtherSign(mOperate);
		tLDCodeSet.set(tLDCodeDB.query());
		if (tLDCodeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpTbWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "业务类型查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLDCodeSet != null && tLDCodeSet.size() == 1) {
			// 记录工作时效
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", mOperate);

			VData timeVData = new VData();
			timeVData.add(tTransferData);
			timeVData.add(mGlobalInput);

			LDContTimeBL tLDContTimeBL = new LDContTimeBL();
			if (tLDContTimeBL.submitData(timeVData, "")) {
				VData tResultData = tLDContTimeBL.getResult();
				MMap timeMap = (MMap) tResultData.getObjectByObjectName("MMap",
						0);
				timeVData.clear();
				timeVData.add(timeMap);
				mResult.add(timeVData);

			}
		}
		// ====ADD=======zhangtao=======2005-04-09==============END=====================
		// */
		return true;
	}

	private boolean Execute7699999999() {
		mResult.clear();
		boolean tLockFlag = true;
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		
		//申请加锁 
		String tPrtNo = (String) mTransferData.getValueByName("PrtNo"); 
		
		if (!lockNo(tPrtNo)) {
			logger.debug("锁定号码失败!");
			this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
			tLockFlag = false;
			//mPubLock.unLock();
			return false;
		}// 锁定主附险投保单号以及暂收费号码)
		try {
			//申请保单加锁 控制并发 add by liuqh
			
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());

			// 产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			if (tActivityOperator.CreateStartMission_NoScan("0000000011",
					"0000011098", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				mResult.add(tempVData);
				tempVData = null;
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "TbWorkFlowBL";
				// tError.functionName = "Execute9999999999";
				// tError.errorMessage = "工作流引擎工作出现异常!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "TbWorkFlowBL";
			tError.functionName = "Execute7899999999";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}finally{
//			 liuqh 2008-09-17 modify
			// 使用新的加锁逻辑
			mPubLock.unLock();
		}

		return true;
	}

	/**
	 * 执行承保工作流待人工核保活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000001100() { // *
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
			tError.moduleName = "GrpTbWorkFlowBL";
			tError.functionName = "Execute0000000100";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpTbWorkFlowBL";
			tError.functionName = "Execute0000001100";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000001100", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "GrpTbWorkFlowBL";
				// tError.functionName = "Execute0000000000";
				// tError.errorMessage = "工作流引擎执行承保工作流待人工核保活动表任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行承保工作流待人工核保活动表任务的结果
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
			// 产生执行完承保工作流待人工核保活动表任务后的任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000001100", mInputData)) {
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
			tError.moduleName = "GrpTbWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行承保工作流待人工核保活动表任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		} // */
		return true;
	}

	/**
	 * 创建起始任务节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute6999999999() {
		mResult.clear();
		VData tVData = new VData();

		// 团单录入完毕校验
		GrpFirstWorkFlowCheck tGrpFirstWorkFlowCheck = new GrpFirstWorkFlowCheck();

		if (tGrpFirstWorkFlowCheck.submitData(mInputData, "")) {
			tVData = tGrpFirstWorkFlowCheck.getResult();
			mResult.add(tVData);
		} else {
			this.mErrors.copyAllErrors(tGrpFirstWorkFlowCheck.mErrors);
			return false;
		}
		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());

			// 产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			if (mActivityOperator.CreateStartMission("0000000011",
					"0000011001", mInputData)) {
				VData tempVData = new VData();
				tempVData = mActivityOperator.getResult();
				mResult.add(tempVData);
				tempVData = null;
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "GrpTbWorkFlowBL";
				// tError.functionName = "Execute9999999999";
				// tError.errorMessage = "工作流引擎工作出现异常!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpTbWorkFlowBL";
			tError.functionName = "Execute7999999999";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行承保工作流待人工核保活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000011004() { // *
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
			tError.moduleName = "TbWorkFlowBL";
			tError.functionName = "Execute0000000100";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "TbWorkFlowBL";
			tError.functionName = "Execute0000001100";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000011004", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "TbWorkFlowBL";
				// tError.functionName = "Execute0000000000";
				// tError.errorMessage = "工作流引擎执行承保工作流待人工核保活动表任务出错!";
				// this.mErrors .addOneError(tError) ;
				return false;
			}

			// 获得执行承保工作流待人工核保活动表任务的结果
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

			// 产生执行完承保工作流待人工核保活动表任务后的任务节点

			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000011004", mInputData)) {
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
			tError.moduleName = "TbWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行承保工作流待人工核保活动表任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		} // */

		return true;

	}

	/**
	 * 自动初始化团单新契约客户合并节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute9999992230() {
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		String tGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		GlobalInput mGlobalInput = (GlobalInput) mTransferData
				.getValueByName("mGlobalInput");
		logger.debug(mGlobalInput.ComCode);
		if (tMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskWorkFlowBL";
			tError.functionName = "Execute0000006004";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tSubMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskWorkFlowBL";
			tError.functionName = "Execute0000006004";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LWMissionSchema t1LWMissionSchema = new LWMissionSchema();

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);

		String mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
		tLOPRTManagerSchema.setPrtSeq(mPrtSeq);

		tLOPRTManagerSchema.setOtherNo(tGrpContNo);
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL); // 保单号
		tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_UINT); // 打印类型
		tLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom); // 管理机构

		tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		tLOPRTManagerSchema.setStateFlag("0");
		tLOPRTManagerSchema.setPatchFlag("0");
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		tLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);

		mTransferData.setNameAndValue("PrtSeq2", mPrtSeq);
		mTransferData.setNameAndValue("OldPrtSeq2", mPrtSeq);

		t1LWMissionSchema = tActivityOperator.CreateOnlyOneMission(
				"0000000011", "0000011230", mInputData);

		MMap map = new MMap();

		if (t1LWMissionSchema != null && tLOPRTManagerSchema != null) {
			map.put(t1LWMissionSchema, "INSERT");
			map.put(tLOPRTManagerSchema, "INSERT");
		}

		tVData.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "")) {
			CError tError = new CError();
			tError.moduleName = "UWSendTraceBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据库提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareOutputData() {
		// mInputData.add( mGlobalInput );
		return true;
	}
	
}

