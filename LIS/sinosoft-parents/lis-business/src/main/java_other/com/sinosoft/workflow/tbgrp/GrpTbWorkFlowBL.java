package com.sinosoft.workflow.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.AskPriceInfoDB;
import com.sinosoft.lis.db.AskPriceRiskDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LQRelaInfoDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.AskPriceInfoSchema;
import com.sinosoft.lis.schema.AskPriceRiskSchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LQRelaInfoSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.AskPriceRiskSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
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
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	private TransferData mTransferData = new TransferData();

	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	
	private MMap map = new MMap();
	private VData tVData = new VData();

	public GrpTbWorkFlowBL() {
	}

	
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	
	private String mPrtNo ="";
	private String mAskpriceNo ="";
	private String mAskNo ="";
	private ExeSQL mExeSQL = new ExeSQL();
	
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
		} else if (mOperate.trim().equals("0000002004")) {
			logger.debug("---00000002004");
			if (!Execute0000002004()) {
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

		//基本信息
		mPrtNo = (String) mTransferData.getValueByName("PrtNo"); 
		mAskpriceNo = (String) mTransferData.getValueByName("AskpriceNo"); //询价编码
		mAskNo = (String) mTransferData.getValueByName("AskNo"); //询价版本号
		
		//申请加锁  
		if (!lockNo(mPrtNo)) {
			logger.debug("锁定号码失败!");
			this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
			tLockFlag = false;
			//mPubLock.unLock();
			return false;
		}// 锁定主附险投保单号以及暂收费号码)
		

	    	    
		try {
			
			if( !mAskpriceNo.equals("") && !mAskNo.equals("")  ){
				//添加报价关系表(LQRelaInfo)信息   wangxw 20100906
				LQRelaInfoSchema  tLQRelaInfoSchema = new LQRelaInfoSchema();
			    LQRelaInfoDB tLQRelaInfoDB = new LQRelaInfoDB();
		    	tLQRelaInfoSchema.setAskPriceNo(mAskpriceNo);
		    	tLQRelaInfoSchema.setAskNo(mAskNo);
		    	tLQRelaInfoSchema.setPrtNo(mPrtNo);
		    	tLQRelaInfoSchema.setMakeDate(PubFun.getCurrentDate());
		    	tLQRelaInfoSchema.setMakeTime(PubFun.getCurrentTime());
	
/*
		    	tLQRelaInfoDB.setSchema(tLQRelaInfoSchema);
		    	if( !tLQRelaInfoDB.insert() )
		    	{
		    		this.mErrors.copyAllErrors(tLQRelaInfoDB.mErrors);
		    		return false;
		    	}
*/		    	
				if (tLQRelaInfoSchema != null  ) {
					map.put(tLQRelaInfoSchema, "INSERT");
				}
		    	
		    	//询价 数据转存 承保  wangxw 20100907 
		    	if(  !AskpriceToTb() ) {
					CError tError = new CError();
					tError.moduleName = "GrpTbWorkFlowBL";
					tError.functionName = "AskpriceToTb";
					tError.errorMessage = "询价信息导入出错!";
					this.mErrors.addOneError(tError);
		    		return false;
		    	}
		    	
			}
			
			
			//申请保单加锁 控制并发 add by liuqh
			
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());

			// 产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			if (tActivityOperator.CreateStartMission_NoScan("0000000004",
					"0000002098", mInputData)) {
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
			if (mActivityOperator.CreateStartMission("0000000004",
					"0000002001", mInputData)) {
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
	private boolean Execute0000002004() { // *
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
					"0000002004", mInputData)) {
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
					"0000002004", mInputData)) {
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
				"0000000004", "0000002230", mInputData);

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
	
	// 与报价系统对接，数据转存
	private boolean  AskpriceToTb() {
		
    	AskPriceInfoSchema  tAskPriceInfoSchema = new AskPriceInfoSchema();
    	AskPriceInfoDB  tAskPriceInfoDB = new AskPriceInfoDB();
    	AskPriceRiskSchema  tAskPriceRiskSchema = new AskPriceRiskSchema();
    	AskPriceRiskSet  tAskPriceRiskSet = new AskPriceRiskSet();
    	AskPriceRiskDB  tAskPriceRiskDB = new AskPriceRiskDB();
    	
    	//一. 集体合同信息
    	LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
    	LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema();
    	//二. 投保单位信息      	
    	LDGrpSchema tLDGrpSchema = new LDGrpSchema();
    	LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();
    	//三. 险种信息
    	LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema(); 
    	LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
    	
    	//查询询价基本信息
    	tAskPriceInfoSchema.setAskPriceNo(mAskpriceNo);
    	tAskPriceInfoSchema.setAskNo(mAskNo);
    	tAskPriceInfoDB.setSchema(tAskPriceInfoSchema);
    	if ( !tAskPriceInfoDB.getInfo() )
    	{
    		this.mErrors.copyAllErrors(tAskPriceInfoDB.mErrors);
    		mErrors.addOneError(new CError("无法获取询价基础信息"));
    		return false;
    	}

    	//查询询价险种信息
    	tAskPriceRiskSchema.setAskPriceNo(mAskpriceNo);
    	tAskPriceRiskSchema.setAskNo(mAskNo);
    	//tAskPriceRiskSchema.setRiskRating("10"); 
    	tAskPriceRiskDB.setSchema(tAskPriceRiskSchema);
    	// DB.query() 会生成和 schema 现有字段符合的 set ，需要处理一个报价单多个险种的情况
    	tAskPriceRiskSet = tAskPriceRiskDB.query();
    	
    	//需要处理多个险种的情况
    	int tRiskCount = 0; //记录险种个数
    	String [] tRiskcode = new String[20]; 
    	if ( tAskPriceRiskSet.size() != 0 )
    	{
    		for ( tRiskCount=0; tRiskCount< tAskPriceRiskSet.size(); tRiskCount++  ){
    			tRiskcode[tRiskCount] = tAskPriceRiskSet.get(tRiskCount+1).getRiskCode();
    		}

    	}else
    	{
    		this.mErrors.copyAllErrors(tAskPriceRiskDB.mErrors);
    		mErrors.addOneError(new CError("无法获取询价险种信息"));
    		return false;
    	}
    	

    	
    	//生成客户号
    	String tCustomerNO = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
		if (!tCustomerNO.equals("")) {
			tLCGrpContSchema.setAppntNo(tCustomerNO);	
			tLDGrpSchema.setCustomerNo(tCustomerNO);
			tLCGrpAppntSchema.setCustomerNo(tCustomerNO);
			tLCGrpAddressSchema.setCustomerNo(tCustomerNO);

		} else {
			mErrors.addOneError(new CError("客户号码生成失败"));
			return false;
		}
		
		// agentcode , agentgroup, managecom
		String tAgentCode = tAskPriceInfoDB.getAgentCode();
		String tAgentGroup = "";
		String tManageCom ="";
		if ( tAgentCode != null && !"".equals(tAgentCode) ){
			tLCGrpContSchema.setAgentCode(tAgentCode);
//			tLCGrpPolSchema.setAgentCode(tAgentCode);
			tAgentGroup = mExeSQL.getOneValue("select agentgroup  from laagent where agentcode = '" + tAgentCode +"'"   );
			if ( tAgentCode != null && !"".equals(tAgentCode) ){
				tLCGrpContSchema.setAgentGroup(tAgentGroup);
//				tLCGrpPolSchema.setAgentGroup(tAgentGroup);
			}
	    	tManageCom = mExeSQL.getOneValue("select managecom  from laagent where agentcode = '" + tAgentCode +"'"   );
	    	if ( !tManageCom.equals("") )
	    	{    	
	    		tLCGrpContSchema.setManageCom(tManageCom);
//	    		tLCGrpPolSchema.setManageCom(tManageCom);
	    	}
		}else{
			CError tError = new CError();
			tError.moduleName = "GrpTbWorkFlowBL";
			tError.functionName = "AskpriceToTb()";
			tError.errorMessage = "代理人信息为空!";
			this.mErrors.addOneError(tError);
		}
		
    	//一. 集体合同信息  AskPriceInfo ——> lcgrpcont
    	// 主键对应：lcgrpcont.PRTNO -- tPrtNo
    	tLCGrpContSchema.setPrtNo(mPrtNo);
		//必须字段：
			/** lcgrpcont
			 1. GRPCONTNO：PROPOSALGRPCONTNO、 生成
			 2. APPNTNO：  生成 customerno
			 3. ADDRESSNO：生成
			 4. OPERATOR、MAKEDATE、MAKETIME、MODIFYDATE、MODIFYTIME 
			 5. SaleChnl：askpriceinfo.BranchType2 、 PolApplyDate  、CValiDate   必须先录入，否则更新时会报错  null.equals
			 6. 其他： GRPNAME：  askpriceinfo.UnitName    agentcode,  agentgroup
			*/

    	//1. 生成投保单号  GRPCONTNO , PROPOSALGRPCONTNO
		if (tLCGrpContSchema.getPrtNo() == null
				|| tLCGrpContSchema.getPrtNo().equals("")) {
			String tLimit = PubFun.getNoLimit(tLCGrpContSchema.getManageCom());
			// 生成ProposalGrpContNo
			mPrtNo = PubFun1.CreateMaxNo("GRPCONTNO", tLimit);
			
			if (!mPrtNo.equals("")) {
				tLCGrpContSchema.setProposalGrpContNo(mPrtNo);
				tLCGrpContSchema.setGrpContNo(mPrtNo);
				tLCGrpAppntSchema.setGrpContNo(mPrtNo);
//				tLCGrpPolSchema.setGrpContNo(mPrtNo);
//				tLCGrpPolSchema.setGrpProposalNo(mPrtNo);
			} else {
				mErrors.addOneError(new CError("集体投保单号生成失败"));
				return false;
			}
		} else {
			tLCGrpContSchema.setProposalGrpContNo(mPrtNo);
			tLCGrpContSchema.setGrpContNo(mPrtNo);
			tLCGrpAppntSchema.setGrpContNo(mPrtNo);
//			tLCGrpPolSchema.setGrpContNo(mPrtNo);
//			tLCGrpPolSchema.setGrpProposalNo(mPrtNo);
		}
		//2. AppntNo -- tCustomerNO
    	//3. ADDRESSNO 生成地址号码
		String tAddressNo = "";
		if (tLCGrpContSchema.getAddressNo() == null
				|| tLCGrpContSchema.getAddressNo().trim().equals("")) {
			try {
				//	boolean addressFlag = true;
				
				SSRS tSSRS = new SSRS();
				String sql = "Select Case When max(AddressNo) Is Null Then '0' Else max(AddressNo) End from LCGrpAddress where CustomerNo='"
						+ tCustomerNO + "'";
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sql);
				Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
				int ttNo = firstinteger.intValue() + 1;
				Integer integer = new Integer(ttNo);
				tAddressNo = integer.toString();
				logger.debug("得到的地址码是：" + tAddressNo);
				if (!tAddressNo.equals("")) {
					tLCGrpContSchema.setAddressNo(tAddressNo);
					tLCGrpAppntSchema.setAddressNo(tAddressNo);
					tLCGrpAddressSchema.setAddressNo(tAddressNo);
				} else {
					mErrors.addOneError(new CError("客户地址号码生成失败"));
					return false;
				}
			} catch (Exception e) {
				CError tError = new CError();
				tError.moduleName = "GrpTbWorkFlowBL";
				tError.functionName = "AskpriceToTb";
				tError.errorMessage = "地址码超长,生成号码失败,请先删除原来的超长地址码!";
				this.mErrors.addOneError(tError);
			}
		}
		// 4. OPERATOR 、 MAKEDATE、MAKETIME、MODIFYDATE、MODIFYTIME   pubfun
		//tLCGrpContSchema
		tLCGrpContSchema.setOperator(mGlobalInput.Operator);
		tLCGrpContSchema.setMakeDate(theCurrentDate);
		tLCGrpContSchema.setMakeTime(theCurrentTime);
		tLCGrpContSchema.setModifyDate(theCurrentDate);
		tLCGrpContSchema.setModifyTime(theCurrentTime);
		
		// 5. 状态字段
		tLCGrpContSchema.setAppFlag("0");
		tLCGrpContSchema.setState("0");
		tLCGrpContSchema.setApproveFlag("0");
		tLCGrpContSchema.setUWFlag("0");
		tLCGrpContSchema.setSpecFlag("0");
		tLCGrpContSchema.setContType("2");
		tLCGrpContSchema.setOutPayFlag("1");
		
		//6. 其他信息 GrpName ,AgentCode, agentgroup ,  销售渠道 SaleChnl：askpriceinfo.BranchType2   
		tLCGrpContSchema.setGrpName(tAskPriceInfoDB.getUnitName());    //单位名称 GRPNAME：  askpriceinfo.UnitName 
    	// AgentCode ， agentgroup 已经设置
		tLCGrpContSchema.setSaleChnl(tAskPriceInfoDB.getBranchType2()); 
		tLCGrpContSchema.setPolApplyDate(theCurrentDate); //投保日期
		tLCGrpContSchema.setCValiDate(theCurrentDate); //生效日期			    	



		if (tLCGrpContSchema != null  ) {
			map.put(tLCGrpContSchema, "INSERT");
		}
   		
		
    	//二. 投保单位信息  AskPriceInfo ——> ldgrp （ ldgrp.CUSTOMERNO ）, LCGrpAppnt , LCGrpAddress
		//其他非空字段： 
		/** ldgrp
		 * 1. CUSTOMERNO
		 * 2. GRPNAME -- AskPriceInfo.UnitName
		 * 3. OPERATOR、MAKEDATE、MAKETIME、MODIFYDATE、MODIFYTIME 
		 * */
    	tLDGrpSchema.setGrpName(tAskPriceInfoDB.getUnitName());
    	tLDGrpSchema.setOperator(mGlobalInput.Operator);
    	tLDGrpSchema.setMakeDate(theCurrentDate);
    	tLDGrpSchema.setMakeTime(theCurrentTime);
    	tLDGrpSchema.setModifyDate(theCurrentDate);
    	tLDGrpSchema.setModifyTime(theCurrentTime);    	
    	//其他信息
    	tLDGrpSchema.setBusinessType( tAskPriceInfoDB.getIndustryType() );
		if (tLDGrpSchema != null) {
			map.put(tLDGrpSchema, "INSERT");
		}
		
		/**LCGrpAppnt
		 * 1. GRPCONTNO 、 CUSTOMERNO 已生成并赋值
		 * 2. PRTNO 、name
		 * 3. OPERATOR、MAKEDATE、MAKETIME、MODIFYDATE、MODIFYTIME 
		 */
    	tLCGrpAppntSchema.setPrtNo(mPrtNo);
    	tLCGrpAppntSchema.setOperator(mGlobalInput.Operator);
    	tLCGrpAppntSchema.setMakeDate(theCurrentDate);
    	tLCGrpAppntSchema.setMakeTime(theCurrentTime);
    	tLCGrpAppntSchema.setModifyDate(theCurrentDate);
    	tLCGrpAppntSchema.setModifyTime(theCurrentTime); 
    	//其他信息
    	tLCGrpAppntSchema.setName(tAskPriceInfoDB.getUnitName()); //传值没有问题，页面没有显示
		if (tLCGrpAppntSchema != null) {
			map.put(tLCGrpAppntSchema, "INSERT");
		}
    	/* LCGrpAddress
    	 * 1. CUSTOMERNO 、 ADDRESSNO 已生成并赋值
    	 * 2. OPERATOR、MAKEDATE、MAKETIME、MODIFYDATE、MODIFYTIME 
    	 */
		tLCGrpAddressSchema.setOperator(mGlobalInput.Operator);
		tLCGrpAddressSchema.setMakeDate(theCurrentDate);
		tLCGrpAddressSchema.setMakeTime(theCurrentTime);
		tLCGrpAddressSchema.setModifyDate(theCurrentDate);
		tLCGrpAddressSchema.setModifyTime(theCurrentTime);
    	//其他信息
		if (tLCGrpAddressSchema != null) {
			map.put(tLCGrpAddressSchema, "INSERT");
		}
    	
  	
    	//三. 险种信息
		/**LCGrpPol
		 * 1.GRPPOLNO 
		 * 2. GRPCONTNO GRPPROPOSALNO PRTNO
		 * 3. RISKCODE  存在问题！ 如何获取
		 * 4. OPERATOR、MAKEDATE、MAKETIME、MODIFYDATE、MODIFYTIME 
		 * 5. 其他： CurrentDate //生效日期	 AddressNo
		 */

		int i=0;
		while ( i < tRiskCount ) {
			if( tRiskcode[i] != null && !"".equals(tRiskcode[i]) ){
				tLCGrpPolSchema.setGrpContNo(mPrtNo);
				
				//1. 生成 GRPPOLNO
				String tLimit = PubFun.getNoLimit(tLCGrpContSchema.getManageCom());
				String tGrpPolNo = PubFun1.CreateMaxNo("GrpProposalNo", tLimit);
				tLCGrpPolSchema.setGrpPolNo(tGrpPolNo);
				tLCGrpPolSchema.setGrpProposalNo(tGrpPolNo);
				// 2  GRPCONTNO GRPPROPOSALNO 已经在生成 grpcontno 时赋值 。 
				tLCGrpPolSchema.setPrtNo(mPrtNo);
				tLCGrpPolSchema.setRiskCode(tRiskcode[i]);//tAskPriceRiskSet.get(1)	
				tLCGrpPolSchema.setOperator(mGlobalInput.Operator);
				tLCGrpPolSchema.setMakeDate(theCurrentDate);
				tLCGrpPolSchema.setMakeTime(theCurrentTime);
				tLCGrpPolSchema.setModifyDate(theCurrentDate);
				tLCGrpPolSchema.setModifyTime(theCurrentTime);
				/*其他信息：
				 * 
				 * 查出: agentcode, agentgroup ,tManageCom , agenttype（待定）
				 * 传入：salechnl , grpname
				 * 生成：customerno , addressno , CValiDate 生效日期
				 * 标志位： appflag ,state , approveflag ,  uwflag
				 * 有问题： 与原系统默认值不同 chargefeerate ， currency
				 * 
				 * */
				tLCGrpPolSchema.setAgentCode(tAgentCode);
				tLCGrpPolSchema.setAgentGroup(tAgentGroup);
				tLCGrpPolSchema.setManageCom(tManageCom);
				tLCGrpPolSchema.setAgentType("01"); // （待定）
				
				tLCGrpPolSchema.setSaleChnl(tAskPriceInfoDB.getBranchType2()); 
				tLCGrpPolSchema.setGrpName(tAskPriceInfoDB.getUnitName());
				
				tLCGrpPolSchema.setCustomerNo(tCustomerNO);
				tLCGrpPolSchema.setAddressNo(tAddressNo);
				tLCGrpPolSchema.setCValiDate(theCurrentDate); //生效日期	,录入被保人是必须
				
				tLCGrpPolSchema.setAppFlag("0");
				tLCGrpPolSchema.setState("0");
				tLCGrpPolSchema.setApproveFlag("0");
				tLCGrpPolSchema.setUWFlag("0");
//				tLCGrpPolSchema.setSpecFlag("0"); //系统默认为空

				
				tLCGrpPolSet.add(tLCGrpPolSchema);
				i++;
			}else{
				CError tError = new CError();
				tError.moduleName = "GrpTbWorkFlowBL";
				tError.functionName = "AskpriceToTb";
				tError.errorMessage = "询价险种信息有误，险种代码为空";
				this.mErrors.addOneError(tError);
			}
		}

		
		if (tLCGrpPolSet != null) {
			map.put(tLCGrpPolSet, "INSERT");
		}
    	
    	
    	//四. 保单计划 —— 在投保页面点击“保单计划”时转存
		
				
		//if( true )  return false;
		
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
	
	public static void main(String args[]){
		GrpTbWorkFlowBL tGrpTbWorkFlowBL = new GrpTbWorkFlowBL();
		tGrpTbWorkFlowBL.mPrtNo ="20100305092224";
		tGrpTbWorkFlowBL.mAskpriceNo ="2010000001";
		tGrpTbWorkFlowBL.mAskNo ="0";
		tGrpTbWorkFlowBL.AskpriceToTb();
	}
	
	
}


