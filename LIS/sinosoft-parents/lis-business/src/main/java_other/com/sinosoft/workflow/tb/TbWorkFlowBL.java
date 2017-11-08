package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LWActivityDB;
import com.sinosoft.lis.db.LWFieldMapDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LWProcessDB;
import com.sinosoft.lis.db.LWProcessTransDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LWActivitySet;
import com.sinosoft.lis.vschema.LWFieldMapSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LWProcessSet;
import com.sinosoft.lis.vschema.LWProcessTransSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
public class TbWorkFlowBL {
private static Logger logger = Logger.getLogger(TbWorkFlowBL.class);
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
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
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

	public TbWorkFlowBL() {
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
		logger.debug("---TbWorkFlowBL getInputData---");
		try{
		//tongmeng 2009-04-30 add
		//新契约并发控制
		if(!this.PubLock(cOperate))	
		{
			return false;
		}
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---TbWorkFlowBL dealData---");

		// 准备给后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("---TbWorkFlowBL prepareOutputData---");

		if (mFlag) { // 如果置相应的标志位，不提交
			// 数据提交

			TbWorkFlowBLS tTbWorkFlowBLS = new TbWorkFlowBLS();
			logger.debug("Start TbWorkFlowBL Submit...");

			if (!tTbWorkFlowBLS.submitData(mResult, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tTbWorkFlowBLS.mErrors);
				CError.buildErr(this, "数据提交失败!");
				return false;
			}
		}
		}
		catch(Exception e)
		{
			CError.buildErr(this,e.toString());
			return false;
		}
		finally
		{
			this.mPubLock.unLock();
		}
		
//		logger.debug("判断是否手工自动申请保单！");
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

	private boolean PubLock(String cOperate) {
		// tongmeng 2009-04-16 add
		// 并发控制
		String mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			mContNo = (String) mTransferData.getValueByName("PrtNo");
			if(mContNo==null)
			{
				return false;
			}
		}
		if(mContNo == null)
		{
			CError.buildErr(this,"获取业务号失败!");
			return false;
		}
		String tLockModule = getLockNoByActivityid(cOperate);
		if(tLockModule.equals(""))
		{
			return true;
		}
		if (!this.mPubLock.lock(mContNo, tLockModule)) {
			this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
			return false;
		}
		return true;
	}
	
	/**
	 * 按照任务号分配锁定模块编码
	 * @param tActivityid
	 * @return
	 */
	private String getLockNoByActivityid(String tActivityid)
	{
		String tRes = "";
		if(tActivityid==null||tActivityid.equals(""))
		{
			return "";
		}
		String[] tActivityFilter = new String[]{"0000001001","0000001002",
			"0000001003","0000001089","0000001090","0000001091","0000001094",
			"0000001098","0000001099","0000001105","0000001110","0000001115",
			"0000001116","0000001120","0000001121","0000001134","0000001140",
			"0000001149","0000001301","0000001401","0000001402","0000001403",
			"0000001404","0000001150","0000001121","0000001113","0000001114",
			"0000001120","0000001111","0000001020","0000001112","0000005553",
			"0000005533"};
		String[] tLockModuleFilter = new String[]{"LC0010","LC0011","LC0001",
				"LC0012","LC0013","LC0014","LC0015","LC0016","LC0017","LC0018",
				"LC0019","LC0020","LC0021","LC0022","LC0023","LC0024","LC0025",
				"LC0003","LC0026","LC0027","LC0028","LC0029","LC0030","LC0002",
				"LC0040","LC0041","LC0042","LC0043","LC0044","LC0046","LC0045",
				"LP0004","LP0006"};
		for(int i=0;i<tActivityFilter.length;i++)
		{
			String tTempActivity = tActivityFilter[i];
			if(tTempActivity.equals(tActivityid))
			{
				tRes = tLockModuleFilter[i];
				break;
			}
		}
		
		return tRes;
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
	private boolean dealData() {
		// 承保工作流打印核保通知书活动表
		if (mOperate.trim().equals("7999999999")) {
			if (!Execute7999999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("0000001100")) { // 执行工作流待人工核保活动表任务
			if (!Execute0000001100()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("7599999999")) { // 执行工作流待人工核保活动表任务
			if (!Execute7599999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		else if (mOperate.trim().equals("7899999999")) {
			if (!Execute7899999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("7799999999")) {
			if (!Execute7799999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("7099999999")) { // 执行初审申请节点
			if (!Execute7099999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("9999991061")) {
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
				|| mOperate.trim().equals("0000001002")) {
			if (!Execute0000001113(mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}else if (mOperate.trim().equals("7199999999")) { // 新增双岗录入起点
			if (!Execute7199999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}
		else if (mOperate.trim().equals("7199999910")) { // 新增双岗录入起点
			if (!Execute7199999910()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		else { // 执行承保工作流发核保通知书活动表任务
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
		if (mOperate.equals("0000001110")) {
			tLDCodeDB.setOtherSign("0000001100");
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
			if (mOperate.equals("0000001110")) {
				tTransferData.setNameAndValue("ActivityID", "0000001100");
			} else {
				tTransferData.setNameAndValue("ActivityID", mOperate);
			}
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
		return true;
	}

	/**
	 * 执行承保工作流待人工核保活动表任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000001100() { // *
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
					"0000001100", mInputData)) {
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
			tLWProcessTransDB.setProcessID("0000000003");
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
				if (tActivityOperator.CheckTransitionCondition_Public(tLWProcessTransSet.get(j).getSchema(), mInputData)) {
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
		FirstWorkFlowCheck tFirstWorkFlowCheck = new FirstWorkFlowCheck();

		if (tFirstWorkFlowCheck.submitData(mInputData, "")) {
			tVData = tFirstWorkFlowCheck.getResult();
			mResult.add(tVData);
		} else {
			this.mErrors.copyAllErrors(tFirstWorkFlowCheck.mErrors);
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
	 * 创建初始节点：特殊投保单录入
	 * */
	private boolean Execute7199999910(){
		mResult.clear();
		VData tVData = new VData();
		logger.debug("Excute 78999999910");
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());

			// 产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
//			tLWMissionSchema = tActivityOperator.CreateOneMission("0000000003",
//					"0000001094", mInputData);
			//特殊投保单录入单独处理
			//******************************************************************
			// 获取前台数据
			String tProcessID = "0000000003";
			String tActivityID = "0000001094";
			GlobalInput tGlobalInput = new GlobalInput();
			TransferData tTransferData = new TransferData();
			tGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			tTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);

			// 查询工作流过程表
			LWProcessDB tLWProcessDB = new LWProcessDB();
			LWProcessSet tLWProcessSet = new LWProcessSet();
			tLWProcessDB.setProcessID(tProcessID);
			tLWProcessSet = tLWProcessDB.query();
			if (tLWProcessSet.size() != 1) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError.buildErr(this, "创建一个工作流的指定新起点任务,但传入的工作流编码信息有误!") ;
				return false;
			}			

			// 查询默认工作流起点节点表(工作流活动表)
			LWActivityDB tLWActivityDB = new LWActivityDB();
			LWActivitySet tLWActivitySet = new LWActivitySet();
			tLWActivityDB.setActivityID(tActivityID);
			tLWActivitySet = tLWActivityDB.query();
			if (tLWActivitySet == null || tLWActivitySet.size() != 1) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLWActivitySet.mErrors );
				CError.buildErr(this, "创建一个工作流的指定新起点任务,查询工作流节点表出错!") ;
				return false;
			}

			// 校验传入参数
			LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
			LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
			tLWFieldMapDB.setActivityID(tLWActivitySet.get(1).getActivityID());
			tLWFieldMapSet = tLWFieldMapDB.query();
			if (tLWFieldMapSet.size() != 0 && tTransferData == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError.buildErr(this, "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段个数少于该工作流活动的具体字段映射表中记录条数!") ;
				return false;
			}

			// 产生工作流起点任务,准备数据
			String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID("1");
			tLWMissionSchema.setProcessID(tLWProcessSet.get(1).getProcessID());
			tLWMissionSchema.setActivityID(tLWActivitySet.get(1).getActivityID());
			tLWMissionSchema.setActivityStatus("1"); // 0 --
			// 任务产生中（这个状态适合于一个任务由一系列独立的事务完成后才能提交的业务，如团体保单导入，由于导入需要一定的时间，所以在导入过程中会出现该状态。）1
			// -- 任务产生完毕待处理，2 -- 处理中，3 --
			// 处理完成，4 -- 暂停
			// 准备属性字段
			for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
				if (tLWFieldMapSet.get(i).getSourFieldName() == null
						|| tLWFieldMapSet.get(i).getSourFieldName().equals("")) {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError.buildErr(this, "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!") ;
					return false;
				}

				if (tLWFieldMapSet.get(i).getDestFieldName() == null
						|| tLWFieldMapSet.get(i).getDestFieldName().equals("")) {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError.buildErr(this, "创建一个工作流的指定新起点任务,工作流活动的具体字段映射表中记录SourFieldName字段描述有误!") ;
					return false;
				}
				if (tTransferData.getValueByName(tLWFieldMapSet.get(i)
						.getSourFieldName()) == null) {
					// @@错误处理
					// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
					CError.buildErr(this, "创建一个工作流的指定新起点任务,传入工作流起点任务的属性字段信息不足!") ;
					return false;
				}

				String tMissionProp = (String) tTransferData
						.getValueByName(tLWFieldMapSet.get(i).getSourFieldName());
				String tDestFieldName = tLWFieldMapSet.get(i).getDestFieldName();
				// logger.debug("tMissionProp:"+tMissionProp
				// +"tDestFieldName"+tDestFieldName) ;

				tLWMissionSchema.setV(tDestFieldName, tMissionProp);

			}
			// tLWMissionSchema.setDefaultOperator();
			tLWMissionSchema.setLastOperator(tGlobalInput.Operator);
			tLWMissionSchema.setCreateOperator(tGlobalInput.Operator);
			tLWMissionSchema.setMakeDate(PubFun.getCurrentDate());
			tLWMissionSchema.setMakeTime(PubFun.getCurrentTime());
			tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
			tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());

			logger.debug("ok!:");		
			
			//end 2009-2-9 *****************************************************
			//******************************************************************
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
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("delete from lwmission where missionid='"
				+ "?missionid?"
				+ "' and activityid = '0000001062'");
		sqlbv.put("missionid", tLWMissionSchema.getMissionID());
		map.put(sqlbv, "DELETE"); // 删除以前的节点
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
		if(mFlag){
			tResult.clear();
		}
		return tResult;
	}
}
