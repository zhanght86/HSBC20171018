package com.sinosoft.workflow.xb;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LWActivityDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LWProcessTransDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LWActivitySet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LWProcessTransSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

/**
 * <p>
 * Title: 新契约工作流
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
public class RnewWorkFlowBL {
private static Logger logger = Logger.getLogger(RnewWorkFlowBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往前台传输数据的容器 */
	private VData tResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private HttpServletRequest httprequest;

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;

	/** 是否提交标志* */
	private String flag;
	private boolean mFlag = true;

	public RnewWorkFlowBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		this.mInputData = (VData) cInputData.clone();
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		logger.debug("---RnewWorkFlowBL getInputData---");
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---RnewWorkFlowBL dealData---");

		// 准备给后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("---RnewWorkFlowBL prepareOutputData---");

		if (mFlag) { // 如果置相应的标志位，不提交
			// 数据提交

			RnewWorkFlowBLS tRnewWorkFlowBLS = new RnewWorkFlowBLS();
			logger.debug("Start RnewWorkFlowBLS Submit...");

			if (!tRnewWorkFlowBLS.submitData(mResult, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tRnewWorkFlowBLS.mErrors);
				CError.buildErr(this, "数据提交失败!");
				return false;
			}
		}
		
		logger.debug("判断是否手工自动申请保单！");
		//tongmeng 2008-10-28 add
		//暂时注释掉此处.
		/*
		SelApplyService tSelApplyService = new SelApplyService();
		if(!tSelApplyService.submitData(mInputData, mOperate))
		{
//			 @@错误处理
//			this.mErrors.copyAllErrors(tSelApplyService.mErrors);
//			CError.buildErr(this, "自动获取保单失败!");
//			return false;			
		}*/

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
		httprequest = (HttpServletRequest) cInputData.getObjectByObjectName(
				"HttpRequest", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if ((mOperater == null) || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");

			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if ((mManageCom == null) || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据ManageCom失败!");

			return false;
		}

		mOperate = cOperate;
		if ((mOperate == null) || mOperate.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据Operate任务节点编码失败!");

			return false;
		}
		flag = (String) mTransferData.getValueByName("flag");
		if (flag != null) {
			if (flag.equals("N")) {
				mFlag = false;
			}
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() 
	{
		logger.debug("mOperate="+mOperate);
		// 承保工作流打印核保通知书活动表
		if (mOperate.trim().equals("7999999999")) 
		{
			if (!Execute7999999999()) 
			{
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} 
		else if (mOperate.trim().equals("0000007001")) 
		{ // 执行工作流待人工核保活动表任务
			if (!Execute0000007001()) 
			{
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} 
		else if (mOperate.trim().equals("7599999999")) 
		{ // 执行工作流待人工核保活动表任务
			if (!Execute7599999999())
			{
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		else if (mOperate.trim().equals("7899999999")) 
		{
			if (!Execute7899999999()) 
			{
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} 
		else if (mOperate.trim().equals("7799999999")) 
		{
			if (!Execute7799999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} 
		else if (mOperate.trim().equals("7099999999"))
		{ // 执行初审申请节点
			if (!Execute7099999999()) 
			{
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} 
		else if (mOperate.trim().equals("9999991061")) 
		{
			if (!Execute9999991061()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}
		// tongmeng 2007-12-20 add
		// 对于新契约生调回收和问题件修改做特殊处理
		// 0000001113 0000001002
		else if (mOperate.trim().equals("0000001113")
				|| mOperate.trim().equals("0000001002")) 
		{
			if (!Execute0000001113(mOperate)) 
			{
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}
		else if (mOperate.trim().equals("7199999999")) 
		{ // 新增双岗录入起点
			if (!Execute7199999999()) 
			{
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		else 
		{ // 执行承保工作流发核保通知书活动表任务
			if (!Execute())
			{
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
			CError.buildErr(this, "前台传输数据TransferData中的必要参数MissionID失败!");
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据TransferData中的必要参数SubMissionID失败!");
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
			CError.buildErr(this, "工作流引擎执行新契约活动表任务出错!");
			return false;
		}
		// ====ADD=======zhangtao=======2005-04-09==============BGN=====================
		LDCodeSet tLDCodeSet = new LDCodeSet();
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("busitype");
		// 该判断 针对人工核保而言
		if (mOperate.equals("0000007003")) {
			tLDCodeDB.setOtherSign("0000007001");
		} else {
			tLDCodeDB.setOtherSign(mOperate);
		}
		tLDCodeSet.set(tLDCodeDB.query());
		if (tLDCodeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
			CError.buildErr(this, "业务类型查询失败!");
			return false;
		}
		if (tLDCodeSet != null && tLDCodeSet.size() == 1) {
			// 记录工作时效
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			// 该判断 针对人工核保而言
			if (mOperate.equals("0000007003")) {
				tTransferData.setNameAndValue("ActivityID", "0000007001");
			} else {
				tTransferData.setNameAndValue("ActivityID", mOperate);
			}
			VData timeVData = new VData();
			timeVData.add(tTransferData);
			timeVData.add(mGlobalInput);

			RLDContTimeBL tRLDContTimeBL = new RLDContTimeBL();
			if (tRLDContTimeBL.submitData(timeVData, "")) {
				VData tResultData = tRLDContTimeBL.getResult();
				MMap timeMap = (MMap) tResultData.getObjectByObjectName("MMap",
						0);
				timeVData.clear();
				timeVData.add(timeMap);
				mResult.add(timeVData);
			}
		}
		// ====ADD=======zhangtao=======2005-04-09==============END=====================
		return true;
	}

	/**
	 * 执行承保工作流待人工核保活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000007001() { // *
		// tongmeng 2008-08-06 modify
		// 支持多被保人的核保,体检,生调通知书的发放.
		// 在初始化人工核保界面时,按照公共对象和合同下的被保人个数初始化
		// 0000001105 工作流新契约发核保通知书活动表 的数据
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		if (tMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据TransferData中的必要参数MissionID失败!");
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据TransferData中的必要参数SubMissionID失败!");
			return false;
		}
		try {
			mResult.clear();
			VData tVData = new VData();
			ActivityOperator tActivityOperator = new ActivityOperator();

			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					"0000007001", mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			// // 获得执行承保工作流待人工核保活动表任务的结果
			// tVData = mActivityOperator.getResult();
			// if (tVData != null) {
			// for (int i = 0; i < tVData.size(); i++) {
			// VData tempVData = new VData();
			// tempVData = (VData) tVData.get(i);
			// for (int j = 0; j < tempVData.size(); j++) {
			// mResult.add(tempVData.get(i)); // 取出Map值
			// }
			// }
			// }

			// 产生执行完承保工作流待人工核保活动表任务后的任务节点

			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					"0000007001", mInputData)) {
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
			CError.buildErr(this, "工作流引擎执行承保工作流待人工核保活动表任务出错!");
			return false;
		}
		return true;

	}

	/**
	 * 新契约问题件修改和生调回收使用
	 * 
	 * @return
	 */
	private boolean Execute0000001113(String tOperator) {
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		if (tMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据TransferData中的必要参数MissionID失败!");
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据TransferData中的必要参数SubMissionID失败!");
			return false;
		}

		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					tOperator, mInputData)) {
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
					tempVData = null;
				}
			}

			// 产生执行完承保工作流待人工核保活动表任务后的任务节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					tOperator, mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if (tempVData != null && tempVData.size() > 0) {
					mResult.add(tempVData);
					tempVData = null;
				}

			}

			boolean tCreatFlag = false;
			// ///////////////////////////////
			// 查询工作流过程实例表
			LWProcessTransDB tLWProcessTransDB = new LWProcessTransDB();
			LWProcessTransSet tLWProcessTransSet = new LWProcessTransSet();
//			tLWProcessTransDB.setProcessID("0000000003");
			//因为processid是动态变化的 2013-4-24 lzf
			String sql1 = "select processid from lwcorresponding where busitype='1001'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql1);
			ExeSQL tExeSQL = new ExeSQL();
			String tProcessID = tExeSQL.getOneValue(sqlbv);
			tLWProcessTransDB.setProcessID(tProcessID);

			tLWProcessTransDB.setTransitionStart(tOperator);
			tLWProcessTransSet = tLWProcessTransDB.query();
			if (tLWProcessTransSet == null) {
				// @@错误处理
				CError.buildErr(this, "创建一个工作流的下一个任务节点,查询工作流流转表出错!");
				return false;
			}

			if (tLWProcessTransSet.size() < 1) {
				return true;
			}
			tActivityOperator = new ActivityOperator();
			// 准备所有下一节点任务的数据
			for (int j = 1; j <= tLWProcessTransSet.size(); j++) {
				// 查询指定活动节点表(工作流活动表)
				String tActivityID = tLWProcessTransSet.get(j)
						.getTransitionEnd();
				if (tActivityID == null || tActivityID.trim().equals("")) {
					// @@错误处理
					CError.buildErr(this,
							"创建一个工作流的下一个任务节点,查询工作流转移节点表ActivityID数据出错!");
					return false;
				}

				LWActivityDB tLWActivityDB = new LWActivityDB();
				LWActivitySet tLWActivitySet = new LWActivitySet();
				tLWActivityDB.setActivityID(tActivityID);
				tLWActivitySet = tLWActivityDB.query();
				if (tLWActivitySet == null || tLWActivitySet.size() != 1) {
					// @@错误处理
					CError.buildErr(this, "创建一个工作流的下一个任务节点,查询工作流活动节点表出错!");
					return false;
				}
				logger.debug("-------------------in");
				// 校验指定的转移是否满足转移条件
				if (tActivityOperator.CheckTransitionCondition_Public(
						tLWProcessTransSet.get(j), mInputData)) {
					tCreatFlag = true;
				}

			}
			// ///////////////////////////////
			// 根据tCreatFlag 确定是否该删除结点
			if (tCreatFlag) {
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
					this.mErrors.copyAllErrors(tActivityOperator.mErrors);

					return false;
				}
			}

		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError.buildErr(this, "工作流引擎执行承保工作流待人工核保活动表任务出错!");
			return false;
		}

		return true;

	}

	/**
	 * 创建起始任务节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute7999999999() {
		mResult.clear();
		VData tVData = new VData();
		// 团单录入完毕校验
		RFirstWorkFlowCheck tRFirstWorkFlowCheck = new RFirstWorkFlowCheck();

		if (tRFirstWorkFlowCheck.submitData(mInputData, "")) {
			tVData = tRFirstWorkFlowCheck.getResult();
			mResult.add(tVData);
		} else {
			this.mErrors.copyAllErrors(tRFirstWorkFlowCheck.mErrors);
			return false;
		}

		ActivityOperator tActivityOperator = new ActivityOperator();
		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());

			// 产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			if (tActivityOperator.CreateStartMission("0000000003",
					"0000001001", mInputData)) {
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
			CError.buildErr(this, "工作流引擎工作出现异常!");
			return false;
		}

		return true;
	}

	/**
	 * 创建起始任务节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute7599999999() {
		mResult.clear();
		VData tVData = new VData();
		logger.debug("Execute 7599999999");
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());

			// 产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			if (tActivityOperator.CreateStartMission("0000000003",
					"0000001061", mInputData)) {
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
			CError.buildErr(this, "工作流引擎工作出现异常!");
			return false;
		}
		return true;

	}

	/**
	 * 创建起始任务节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute7899999999() {
		mResult.clear();
		VData tVData = new VData();
		logger.debug("Excute 78999999999");
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());

			// 产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			tLWMissionSchema = tActivityOperator.CreateOneMission("0000000003",
					"0000001099", mInputData);
			logger.debug("prtno ==" + tLWMissionSchema.getMissionProp1());
			MMap map = new MMap();
			map.put(tLWMissionSchema, "INSERT");
			tVData.add(map);
			mResult.add(tVData);
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError.buildErr(this, "工作流引擎工作出现异常!");
			return false;
		}

		return true;
	}

	/**
	 * 创建起始任务节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute7799999999() {
		mResult.clear();
		VData tVData = new VData();
		logger.debug("Excute 77999999999");
		ActivityOperator tActivityOperator = new ActivityOperator();
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());
			if (CheckFirstTrial()) {
				return true;
			}
			if (tActivityOperator.CreateStartMission_NoScan("0000000003",
					"0000001098", mInputData)) {
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
			CError.buildErr(this, "工作流引擎工作出现异常!");
			return false;
		}

		return true;
	}

	/**
	 * 创建初审起始任务节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute7099999999() {
		mResult.clear();
		VData tVData = new VData();
		logger.debug("Excute Execute7099999999");
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());

			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			if (tActivityOperator.CreateStartMission("0000000003",
					"0000001061", mInputData)) {
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
			CError.buildErr(this, "工作流引擎工作出现异常!");
			return false;
		}
		return true;
	}

	/**
	 * 创建初始节点：扫描双岗录入
	 * */
	private boolean Execute7199999999(){
		mResult.clear();
		VData tVData = new VData();
		logger.debug("Excute 7199999999");
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());

			// 产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			tLWMissionSchema = tActivityOperator.CreateOneMissionForDS("0000000003",
					"0000001401", mInputData);
			logger.debug("prtno ==" + tLWMissionSchema.getMissionProp1());
			MMap map = new MMap();
			map.put(tLWMissionSchema, "INSERT");
			tVData.add(map);
			mResult.add(tVData);
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError.buildErr(this, "工作流引擎工作出现异常!");
			return false;
		}
		return true;
	}
	
	/**
	 * 自动初始化体检,生调节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute9999991061() {
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		if (tMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据TransferData中的必要参数MissionID失败!");
			return false;
		}
		if (tSubMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据TransferData中的必要参数SubMissionID失败!");
			return false;
		}
		LWMissionSchema t1LWMissionSchema = new LWMissionSchema();
		LWMissionSchema t2LWMissionSchema = new LWMissionSchema();

		t1LWMissionSchema = tActivityOperator.CreateOnlyOneMission(
				"0000000003", "0000001101", mInputData);
		t2LWMissionSchema = tActivityOperator.CreateOnlyOneMission(
				"0000000003", "0000001104", mInputData);

		MMap map = new MMap();
		if (t1LWMissionSchema != null) {
			map.put(t1LWMissionSchema, "INSERT");
		}
		if (t1LWMissionSchema != null) {
			map.put(t2LWMissionSchema, "INSERT");
		}
		tVData.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "")) {
			CError.buildErr(this, "数据库提交失败!");
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
			for (int i = 0; i < mResult.size(); i++) {
				VData tData = new VData();
				tData = (VData) mResult.get(i);
				MMap map = (MMap) tData.getObjectByObjectName("MMap", 0);
				tmap.add(map);
			}
			tResult.add(tmap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断是否有过初审
	 */
	private boolean CheckFirstTrial() {

		VData tVData = new VData();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setActivityID("0000001062");
		tLWMissionDB.setProcessID("0000000003");
		tLWMissionDB.setMissionProp1((String) mTransferData
				.getValueByName("PrtNo"));
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet.size() == 0) {
			return false;
		}
		MMap map = new MMap();

		tLWMissionSchema = tLWMissionSet.get(1);
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("delete from lwmission where missionid='"
				+ "?missionid?"
				+ "' and activityid = '0000001062'");
		sqlbv2.put("missionid", tLWMissionSchema.getMissionID());
		map.put(sqlbv2, "DELETE"); // 删除以前的节点
		tLWMissionSchema.setActivityID("0000001098");
		tLWMissionSchema.setLastOperator(mGlobalInput.Operator);
		tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
		tLWMissionSchema.setMissionProp2(PubFun.getCurrentDate());
		map.put(tLWMissionSchema, "INSERT"); // 生成新的节点
		tVData.add(map);
		mResult.add(tVData);
		return true;
	}

	public VData getResult() {
		return tResult;
	}
}
