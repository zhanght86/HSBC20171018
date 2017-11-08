package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
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

/**
 * <p>
 * Title:理赔工作流
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
 * @author ZL
 * @version 1.0
 */
public class ClaimWorkFlowBL {
private static Logger logger = Logger.getLogger(ClaimWorkFlowBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往前台提交数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	// 提交数据打包类
	private MMap map = new MMap();

	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();
	
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;

	/** 是否提交标志* */
	private String flag;
	private boolean mFlag = true;

	LWMissionSchema mLWMissionSchema = new LWMissionSchema();

	public ClaimWorkFlowBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("------ClaimWorkFlowBL BEG------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		logger.debug("---ClaimWorkFlowBL After getInputData---");
		//并发控制必须是try/catch/finally
		try {
			//并发控制
			if(!this.PubLock(cOperate))	{
				return false;
			}
			// 数据操作业务处理
			if (!dealData()) {
				return false;
			}
			logger.debug("---ClaimWorkFlowBL After dealData---");

			// 准备给后台的数据
			if (!prepareOutputData()) {
				return false;
			}
			logger.debug("---ClaimWorkFlowBL After prepareOutputData---");

			// 如果置相应的标志位，不提交
			if (mFlag) {

				logger.debug("Start ClaimWorkFlowBL Submit......");

				PubSubmit tPubSubmit = new PubSubmit();
				if (!tPubSubmit.submitData(mInputData, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tPubSubmit.mErrors);
					CError tError = new CError();
					tError.moduleName = "ClaimWorkFlowBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}

				logger.debug("------ClaimWorkFlowBL END------");
			}
		} catch (Exception e) {
			CError.buildErr(this, "理赔工作流引擎错误！");
			e.printStackTrace();
			return false;
		}finally {
			this.mPubLock.unLock();
		}
		return true;
	}
	
	/**
	 * 理赔并发控制
	 * 09-07-09
	 * */
	private boolean PubLock(String cOperate) {
		//  2009-07-09 
		// 并发控制  目前只对理赔二核回收、补打核保通知书、体检通知书进行并发控制
		if(!mOperate.trim().equals("0000005553")&&!mOperate.trim().equals("0000005552")
				&&!mOperate.trim().equals("0000005533")&&!mOperate.trim().equals("0000005532")){
			return true;
		}
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		String tPrtSql = "select missionprop2 from lwmission where missionid='"+"?missionid?"
					+ "' and submissionid='"+"?submissionid?"+"' and activityid='"+"?activityid?"+"'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tPrtSql);
		sqlbv.put("missionid", tMissionID);
		sqlbv.put("submissionid", tSubMissionID);
		sqlbv.put("activityid", mOperate);
		ExeSQL tExeSQL = new ExeSQL();
		String mContNo = tExeSQL.getOneValue(sqlbv);
		if (mContNo == null||"".equals(mContNo)) {
			// @@错误处理
			CError.buildErr(this,"获取业务号失败!");
			return false;
		}
		//获取模块编码
		String tLockModule = getLockNoByActivityid(cOperate);
		if(tLockModule.equals("")) {
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
	 * @return 模块编码
	 * 
	 * 增加并发控制只需在系统中配好相应的模块编码，在本方法中增加相应的activityid 与模块编码即可
	 * 注意：activityid 与相应的模块编码的顺序不能弄错
	 */
	private String getLockNoByActivityid(String tActivityid) {
		String tRes = "";
		if(tActivityid==null||tActivityid.equals("")) {
			return "";
		}
		String[] tActivityFilter = new String[]{"0000005552","0000005553","0000005532","0000005533"};
		String[] tLockModuleFilter = new String[]{"LP0003","LP0004","LP0005","LP0006"};
		for(int i=0;i<tActivityFilter.length;i++) {
			String tTempActivity = tActivityFilter[i];
			if(tTempActivity.equals(tActivityid)) {
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
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
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
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operater失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if ((mManageCom == null) || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
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
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate任务节点编码失败!";
			this.mErrors.addOneError(tError);

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
		// 创建报案初始节点
		if (mOperate.trim().equals("9999999999")) {
			if (!Execute9999999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("9899999999")) {
			// 创建立案起始节点
			if (!Execute9899999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("8999999999")) {
			// 创建呈报起始节点
			if (!Execute8999999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("8899999999")) {
			// 创建调查报告申请起始节点
			if (!Execute8899999999()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("0000005125")||mOperate.trim().equals("0000009125")) {
			logger.debug("准备生成调查任务分配确认节点！！！");
			// 调查管理-调查任务分配确认时的节点（原来用Execute()，不能自动置defaultoperator字段） Add by
			// zhaorx 2006-03-11
			if (!Execute02()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}else if (mOperate.trim().equals("0000005005")) {
			// 报案确认 2008-11-24 zhangzheng 增加在报案确认的工作流扭转前增加的业务处理
			if (!Execute0000005005()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}else if (mOperate.trim().equals("0000005010")) {
			//有扫描立案客户保存
			if (!Execute0000005010()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}else if (mOperate.trim().equals("0000005015")) {
			// 立案确认
			if (!Execute0000005015()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("0000005035")) {
			// 审核确认
			if (!Execute0000005035()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("0000005105")) {
			// 消亡呈报节点
			if (!Execute01()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("0000005145")||mOperate.trim().equals("0000009145")) {
			// 消亡调查过程节点，并根据判断是否生成结论节点
			if (!Execute0000005145()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("0000005165")||mOperate.trim().equals("0000009165")) {
			// 消亡调查结论节点<机构层面>,并判断是否生成下一个节点<赔案层面>
			if (!Execute0000005165()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		else if (mOperate.trim().equals("0000005175")) {
			// 消亡调查结论节点<赔案层面>
			if (!Execute0000005175()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}
		else if (mOperate.trim().equals("0000005055")) {
			String mReject = (String) mTransferData.getValueByName("Reject");
			if("1".equals(mReject))//审批不通过则返回审核
			{
				if (!Execute()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			}
			else
			{
				// 消亡理赔结案节点
				if (!Execute01()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			}			

		}
		else if (mOperate.trim().equals("0000005065")) {
			String mAuditFlag = (String) mTransferData.getValueByName("AuditFlag");
			if("0".equals(mAuditFlag))
			{
				// 消亡理赔简易案件节点
				if (!Execute01()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
			      }
			}
				else
				{
					//如果简易案件退回则返回审核
					if (!Execute()) {
						// @@错误处理
						this.mErrors.copyAllErrors(mActivityOperator.mErrors);
						return false;
				         }
				}
			
		}
		else if (mOperate.trim().equals("0000005075")) {
			// 消亡理赔结案节点
			if (!Execute01()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("Create||ScondUWNode")) {
			// 创建[发起二核节点]
			if (!ExecuteCreateScondUWNode()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else if (mOperate.trim().equals("0000005505")) {
			// Finish||ToClaim--------与赔案有关，核保完成，转向理赔岗，//消亡理赔二核节点
			// Finish||ToWFEdor-------核保完成，转向保全岗，//消亡理赔二核节点，生成“保全工作流二核处理起始节点”
			if (!Execute0000005505()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		else {
			// 执行理赔工作流其他一般节点任务
			if (!Execute()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		return true;
	}

	/**
	 * 执行理赔工作流其他一般节点任务 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute() {
		logger.debug("------Goto Execute()------");
		VData cInputData = new VData();
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

		String tClmNo = (String) mTransferData.getValueByName("RptNo"); // 赔案号
		String tBudgetFlag = (String) mTransferData
				.getValueByName("BudgetFlag"); // 预付标志
		
		try {
			//2010-04-09 简易案件流程调整，简易案件确认则标识该案件结束
			/*if (mOperate.trim().equals("0000005065")){//简易案件确认
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("RptNo", tClmNo);
			tTransferData.setNameAndValue("BudgetFlag", "0"); // 是否预付，传'0'进去是要工作流流转到审批阶段;
			VData mVData = new VData();
			mVData.add(mGlobalInput);
			mVData.add(tTransferData);
			LLBnfGatherBL tLLBnfGatherBL = new LLBnfGatherBL();
			if (!tLLBnfGatherBL.submitData(mVData, "")) {
				logger.debug("汇总受益人信息失败----------------------------");
				this.mErrors.copyAllErrors(tLLBnfGatherBL.mErrors);
				return false;
			} else {
				logger.debug("汇总受益人信息成功----------------------------");
				VData tempVData = new VData();
				tempVData = tLLBnfGatherBL.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					MMap tmap = new MMap();
					tmap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
					map.add(tmap);
				}
			}			
			}
			*/
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					mOperate, mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			// 获得执行节点任务后返回的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					cInputData.add(tempVData);
				}
			}
			
			// 取出服务类中的计算信息
			String tOperator = (String) mTransferData
					.getValueByName("Operator");

			// 产生下一个任务节点
			if (tActivityOperator.CreateNextMissionSpecial(tMissionID,
					tSubMissionID, mOperate, mInputData, tOperator)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					cInputData.add(tempVData);
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
					cInputData.add(tempVData);
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
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行理赔活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}
		mInputData.clear();
		mInputData = cInputData;
		logger.debug("------End Execute()------");
		return true;
	}

	/**
	 * 节点一般处理方法.只执行理赔节点任务,并消亡节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute01() {
		logger.debug("------Goto Execute01()------");
		VData cInputData = new VData();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();

		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");

		if (tMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000000001";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000000001";
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
			// 获得执行节点任务后返回的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					cInputData.add(tempVData);
				}
			}

			// 删除当前节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					mOperate, mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					cInputData.add(tempVData);
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
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000000001";
			tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}
		mInputData.clear();
		mInputData = cInputData;
		logger.debug("------End Execute01()------");
		return true;
	}

	/**
	 * 执行理赔工作流一般节点任务,但对 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute02() {
		logger.debug("------Goto Execute02()------");
		VData cInputData = new VData();
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
			// 获得执行节点任务后返回的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					cInputData.add(tempVData);
				}
			}

			// 取出服务类中的计算信息
			String tOperator = (String) mTransferData
					.getValueByName("Operator");

			// 产生下一个任务节点，并置上默认操作人
			if (tActivityOperator.CreateNextMissionSpecial(tMissionID,
					tSubMissionID, mOperate, mInputData, tOperator)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					cInputData.add(tempVData);
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
					cInputData.add(tempVData);
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
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行理赔活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}
		mInputData.clear();
		mInputData = cInputData;
		logger.debug("------End Execute02()------");
		return true;
	}

	/**
	 * 创建理赔起始任务节点,同时调用业务处理类保存业务数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute9999999999() {
		logger.debug("----------创建理赔起始任务节点 BEGIN----------");
		boolean tReturn = false;
		VData cInputData = new VData();
		VData tVData = new VData();

		// 调用业务逻辑处理类，返回处理完数据
		LLReportBL tLLReportBL = new LLReportBL();
		if (tLLReportBL.submitData(mInputData, mOperate)) {
			VData tempVData1 = new VData();
			tempVData1 = tLLReportBL.getResult();
			cInputData.add(tempVData1);
			// 向前台返回处理完的参数以便查询工作流
			mTransferData = null;
			mTransferData = (TransferData) tempVData1.getObjectByObjectName(
					"TransferData", 0);
			mResult.add(mTransferData);

			// 首先生成节点，由于第一次创建，没有触发服务类，在下面直接调用业务处理类
			ActivityOperator tActivityOperator = new ActivityOperator();
			try {
				// 产生报案确认节点
				if (tActivityOperator.CreateStartMission_NoScan("0000000005",
						"0000005005", tempVData1)) {
					VData tempVData2 = new VData();
					tempVData2 = tActivityOperator.getResult();
					cInputData.add(tempVData2);
					tempVData2 = null;
					tReturn = true;
				} else {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					tReturn = false;
				}
			} catch (Exception ex) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "ClaimWorkFlowBL";
				tError.functionName = "Execute9999999999";
				tError.errorMessage = "工作流引擎工作出现异常!";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}

			tempVData1 = null;
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLReportBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			tReturn = false;
		}

		// 准备提交数据
		mInputData.clear();
		mInputData = cInputData;
		return tReturn;
	}

	/**
	 * 创建理赔起始任务节点,同时调用业务处理类保存业务数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute9899999999() {
		logger.debug("----------创建理赔立案起始任务节点 BEGIN----------");
		boolean tReturn = false;
		VData cInputData = new VData();
		VData tVData = new VData();

		// 调用业务逻辑处理类，返回处理完数据
		LLClaimRegisterBLF tLLClaimRegisterBLF = new LLClaimRegisterBLF();
		if (tLLClaimRegisterBLF.submitData(mInputData, "insert||first")) {
			VData tempVData1 = new VData();
			tempVData1 = tLLClaimRegisterBLF.getResult1();
			cInputData.add(tempVData1);
			// 向前台返回处理完的参数以便查询工作流
			mTransferData = null;
			mTransferData = (TransferData) tempVData1.getObjectByObjectName(
					"TransferData", 0);
			mResult.add(mTransferData);

			// 首先生成节点，由于第一次创建，没有触发服务类，在下面直接调用业务处理类
			ActivityOperator tActivityOperator = new ActivityOperator();
			try {
				// 产生报案确认节点
				if (tActivityOperator.CreateStartMission_NoScan("0000000005",
						"0000005015", tempVData1)) {
					VData tempVData2 = new VData();
					tempVData2 = tActivityOperator.getResult();
					cInputData.add(tempVData2);
					tempVData2 = null;
					tReturn = true;
				} else {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					tReturn = false;
				}
			} catch (Exception ex) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError.buildErr(this, "工作流引擎工作出现异常!");
				tReturn = false;
			}

			tempVData1 = null;
		} else {
			// @@错误处理
			CError.buildErr(this, "工作流引擎工作出现异常!");
			this.mErrors.copyAllErrors(tLLClaimRegisterBLF.mErrors);
			mResult.clear();
			tReturn = false;
		}
		mInputData.clear();
		mInputData = cInputData;
		return tReturn;
	}

	/**
	 * 创建理赔呈报任务节点,同时调用业务处理类保存业务数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute8999999999() {
		logger.debug("------Goto Execute8999999999()------");
		boolean tReturn = false;
		VData cInputData = new VData();
		VData tVData = new VData();

		// 调用业务逻辑处理类，返回处理完数据
		LLSubmitApplyBL tLLSubmitApplyBL = new LLSubmitApplyBL();
		if (tLLSubmitApplyBL.submitData(mInputData, "INSERT")) {
			VData tempVData1 = new VData();
			tempVData1 = tLLSubmitApplyBL.getResult();
			cInputData.add(tempVData1);

			// 首先生成节点，由于第一次创建，没有触发服务类，在下面直接调用业务处理类
			ActivityOperator tActivityOperator = new ActivityOperator();
			try {
				// 产生报案确认节点
				if (tActivityOperator.CreateStartMission_NoScan("0000000005",
						"0000005105", tempVData1)) {
					VData tempVData2 = new VData();
					tempVData2 = tActivityOperator.getResult();
					cInputData.add(tempVData2);
					tempVData2 = null;
					tReturn = true;
				} else {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					tReturn = false;
				}
			} catch (Exception ex) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "ClaimWorkFlowBL";
				tError.functionName = "Execute8999999999";
				tError.errorMessage = "工作流引擎工作出现异常!";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}

			tempVData1 = null;
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLSubmitApplyBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			tReturn = false;
		}
		mInputData.clear();
		mInputData = cInputData;
		logger.debug("------End Execute8999999999()------");
		return tReturn;
	}

	/**
	 * 创建理赔调查报告任务节点,同时调用业务处理类保存业务数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute8899999999() {
		logger.debug("------Goto Execute8899999999()------");
		boolean tReturn = false;
		VData cInputData = new VData();
		VData tVData = new VData();

		// 调用业务逻辑处理类，返回处理完数据
		LLInqApplyBL tLLInqApplyBL = new LLInqApplyBL();
		if (tLLInqApplyBL.submitData(mInputData, "INSERT")) {
			VData tempVData1 = new VData();
			tempVData1 = tLLInqApplyBL.getResult();
			cInputData.add(tempVData1);

			// 首先生成节点，由于第一次创建，没有触发服务类，在下面直接调用业务处理类
			ActivityOperator tActivityOperator = new ActivityOperator();
			try {
				// 产生报案确认节点
				if (tActivityOperator.CreateStartMission("0000000005",
						"0000005125", tempVData1)) {
					VData tempVData2 = new VData();
					tempVData2 = tActivityOperator.getResult();
					cInputData.add(tempVData2);
					tempVData2 = null;
					tReturn = true;
				} else {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					tReturn = false;
				}
			} catch (Exception ex) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "ClaimWorkFlowBL";
				tError.functionName = "Execute8899999999";
				tError.errorMessage = "工作流引擎工作出现异常!";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}

			tempVData1 = null;
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLInqApplyBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			tReturn = false;
		}
		mInputData.clear();
		mInputData = cInputData;
		logger.debug("------End Execute8899999999()------");
		return tReturn;
	}
	
	
	/**
	 * 理赔报案确认节点处理方法:报案确认时对身故的案件自动执行保单挂起
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000005005() {
		
		logger.debug("------Goto Execute0000005005()------");
		VData cInputData = new VData();

		try {
			if (!Execute()) {
				// @@错误处理
				CError.buildErr(this, "工作流引擎执行理赔活动表任务出错!");
				return false;
			}

		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "工作流引擎执行理赔活动表任务异常!");
			return false;
		}

		logger.debug("------End Execute0000005005()------");
		return true;
	}
	
	/**
	 * 理赔有扫描立案，客户信息保存时节点处理方法: 业务处理类保存业务数据，备份有扫描工作流，创建理赔立案工作流 
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000005010() {
		logger.debug("----------创建理赔立案起始任务节点 BEGIN----------");
		boolean tReturn = false;
		VData cInputData = new VData();
		VData tVData = new VData();

		// 调用业务逻辑处理类，返回处理完数据
		LLClaimScanRegisterBLF tLLClaimScanRegisterBLF = new LLClaimScanRegisterBLF();
		if (tLLClaimScanRegisterBLF.submitData(mInputData, "insert||first")) {
			VData tempVData1 = new VData();
			tempVData1 = tLLClaimScanRegisterBLF.getResult();
		
			// 向前台返回处理完的参数以便查询工作流
			mTransferData = null;
			mTransferData = (TransferData) tempVData1.getObjectByObjectName(
					"TransferData", 0);
			mResult.add(mTransferData);
			
			try {
				if (!Execute()) {
					// @@错误处理
					CError.buildErr(this, "工作流引擎执行理赔活动表任务出错!");
					return false;
				}
				else{
					mInputData.add(tempVData1);
				}

			} catch (Exception ex) {
				// @@错误处理
				CError.buildErr(this, "工作流引擎执行理赔活动表任务异常!");
				return false;
			}

			logger.debug("------End Execute0000005005()------");

			// 首先生成节点，由于第一次创建，没有触发服务类，在下面直接调用业务处理类
//			ActivityOperator tActivityOperator = new ActivityOperator();
//			try {
//				// 产生报案确认节点
//				if (tActivityOperator.CreateStartMission_NoScan("0000000005",
//						"0000005015", tempVData1)) {
//					VData tempVData2 = new VData();
//					tempVData2 = tActivityOperator.getResult();
//					cInputData.add(tempVData2);
//					tempVData2 = null;
//					tReturn = true;
//				} else {
//					// @@错误处理
//					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
//					tReturn = false;
//				}
//			} catch (Exception ex) {
//				// @@错误处理
//				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
//				CError.buildErr(this, "工作流引擎工作出现异常!");
//				tReturn = false;
//			}
			tReturn = true;
			tempVData1 = null;
		} else {
			// @@错误处理
			CError.buildErr(this, "工作流引擎工作出现异常!");
			this.mErrors.copyAllErrors(tLLClaimScanRegisterBLF.mErrors);
			mResult.clear();
			tReturn = false;
		}
//		mInputData.clear();
//		mInputData = cInputData;
		return tReturn;
	}

	/**
	 * 理赔立案节点处理方法:1、立案通过时，走正常工作流 理赔立案节点处理方法:2、不予立案时，只执行节点任务,并消亡节点
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000005015() {
		logger.debug("------Goto Execute0000005015()------");
		VData cInputData = new VData();

		String tRgtConclusion = (String) mTransferData
				.getValueByName("RgtConclusion"); // 立案结论

		if (tRgtConclusion == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005015";
			tError.errorMessage = "前台传输数据TransferData中的必要参数RgtConclusion失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			if (tRgtConclusion.equals("01")) {
				logger.debug("-----立案通过------");
				if (!Execute()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			} else if (tRgtConclusion.equals("02")) {
				logger.debug("-----不予立案------");
				if (!Execute01()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005015";
			tError.errorMessage = "工作流引擎执行理赔活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("------End Execute0000005015()------");
		return true;
	}

	/**
	 * 理赔审核节点处理方法:1、只执行审核节点任务,并消亡节点 理赔审核节点处理方法:2、判断是生成下一个节点
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000005035() {
		logger.debug("------Goto Execute0000005035()------");
		VData cInputData = new VData();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		String tClmNo = (String) mTransferData.getValueByName("RptNo"); // 赔案号
		String tBudgetFlag = (String) mTransferData.getValueByName("BudgetFlag"); 
		String tPrepayFlag = (String) mTransferData.getValueByName("PrepayFlag"); // 预付标志
		if (tClmNo == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据赔案号失败!");
			return false;
		}
		try {

			
			// 查询审核结论
			String strSQL = "";
			strSQL = " select AuditConclusion from LLClaimUWMain where "
					+ " ClmNO='" + "?ClmNO?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSQL);
			sqlbv1.put("ClmNO", tClmNo);
			ExeSQL exesql = new ExeSQL();
			String tResult = exesql.getOneValue(sqlbv1);
			logger.debug("审核结论为: " + tResult);

			if (tResult.equals("0") || tResult.equals("1")
					|| tBudgetFlag.equals("1")) {
				if(tResult.equals("0")|| tPrepayFlag.equals("1") || tResult.equals("1"))//如果是给付则进行受益人的处理
				{
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("RptNo", tClmNo);
					tTransferData.setNameAndValue("PrepayFlag", tPrepayFlag); // 是否预付，0-非预付，1-预付
					VData mVData = new VData();
					mVData.add(mGlobalInput);
					mVData.add(tTransferData);
					LLBnfGatherBL tLLBnfGatherBL = new LLBnfGatherBL();
					if (!tLLBnfGatherBL.submitData(mVData, "")) {
						logger.debug("汇总受益人信息失败----------------------------");
						this.mErrors.copyAllErrors(tLLBnfGatherBL.mErrors);
						return false;
					} else {
						logger.debug("汇总受益人信息成功----------------------------");
						VData tempVData = new VData();
						tempVData = tLLBnfGatherBL.getResult();
						if ((tempVData != null) && (tempVData.size() > 0)) {
							MMap tmap = new MMap();
							tmap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
							map.add(tmap);
						}
					}
				}
				// 消亡审核节点，并生成下一个节点
				logger.debug("-----可以生成下一个节点------");
				if (!Execute02()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			} else {
				// 消亡审核节点（不生成下一个节点）
				logger.debug("-----不可以生成下一个节点------");
				if (!Execute01()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005035";
			tError.errorMessage = "工作流引擎执行审核节点任务出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("------End Execute0000005035()------");
		return true;
	}

	/**
	 * 理赔过程录入节点处理方法:1、只执行理赔过程录入节点任务,并消亡节点 理赔过程录入节点处理方法:2、判断是生成下一个节点
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000005145() {
		logger.debug("------Goto Execute0000005145()------");
		VData cInputData = new VData();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		String tClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		String tInqNo = (String) mTransferData.getValueByName("InqNo"); // 调查序号
		String tBatNo = (String) mTransferData.getValueByName("BatNo"); // 调查批次
		String tInqDept = (String) mTransferData.getValueByName("InqDept"); // 调查机构

		if (tClmNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005145";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tInqNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005145";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tBatNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005145";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tInqDept == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005145";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
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
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSQL);
			sqlbv2.put("tClmNo", tClmNo);
			sqlbv2.put("tInqNo", tInqNo);
			sqlbv2.put("tBatNo", tBatNo);
			sqlbv2.put("tInqDept", tInqDept);
			ExeSQL exesql = new ExeSQL();
			String tResult = exesql.getOneValue(sqlbv2);
			logger.debug("-----判断事件是否可以生成下一个节点tResult.length()= "
					+ tResult.length());
			if ((tResult.length() == 0)) {
				// 消亡调查过程录入节点，并生成下一个节点
				logger.debug("-----可以生成下一个节点------");
				if (!Execute()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			} else {
				// 消亡调查过程录入节点（不生成下一个节点）
				logger.debug("-----不可以生成下一个节点------");
				if (!Execute01()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005145";
			tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("------End Execute0000005145()------");
		return true;
	}

	/**
	 * 理赔过程录入节点处理方法:1、执行理赔机构结论录入节点任务,并消亡节点 理赔过程录入节点处理方法:2、判断是生成下一个节点（赔案层面的调查结论）
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000005165() {
		logger.debug("------Goto Execute0000005165()------");
		VData cInputData = new VData();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();
		LLInqConclusionSchema tLLInqConclusionSchema = new LLInqConclusionSchema();
		tLLInqConclusionSchema = (LLInqConclusionSchema) mInputData
				.getObjectByObjectName("LLInqConclusionSchema", 0);

		String tClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");

		if (tClmNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005165";
			tError.errorMessage = "前台传输数据中的赔案号失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tMissionID == null || tSubMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005175";
			tError.errorMessage = "前台传输数据工作流节点信息中的必要参数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		try {
			//

			// 检查是否汇总赔案结论
			LLInqConclusionDB tLLInqConclusionDB = new LLInqConclusionDB();
			LLInqConclusionSet tLLInqConclusionSet = new LLInqConclusionSet();
			String tConNo = tLLInqConclusionSchema.getConNo();
			String strSql = "select * from llinqconclusion where 1=1  "
					+ " and ClmNO='" + "?ClmNO?" + "'" + " and ConNo!='" + "?ConNo?"
					+ "'" + " and FiniFlag='0'  and batno!='000000'"//
					+ " order by clmno";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSql);
			sqlbv3.put("ClmNO", tClmNo);
			sqlbv3.put("ConNo", tConNo);
			tLLInqConclusionSet.set(tLLInqConclusionDB.executeQuery(sqlbv3));
			logger.debug("-----未完成的机构调查结论数目==== "
					+ tLLInqConclusionSet.size());

			// 判断 “赔案层调查结论” 节点是否存在，如果存在同样也不允许生成
			// String strlwmisSql="select * from lwmission where 1=1"
			// +" and processid='0000000005'"
			// +" and activityid='0000005175'"
			// +" and missionprop6='0'"
			// +" and missionprop1='"+tClmNo+"'";
			// LWMissionDB tLWMissionDB=new LWMissionDB();
			// LWMissionSet tLWMissionSet = new LWMissionSet();
			// tLWMissionSet.set(tLWMissionDB.executeQuery(strlwmisSql));
			// logger.debug("-----是否存在 赔案层 结论节点==== "
			// +tLWMissionSet.size());

			if (tLLInqConclusionSet.size() == 0)// && tLWMissionSet.size()==0
			{
				// 消亡机构调查结论录入节点，不生成下一个节点

				logger.debug("-----消亡机构调查结论录入节点，不生成下一个节点------");
				mTransferData.removeByName("transact");
				mTransferData.setNameAndValue("transact", "UPDATE&&INSERT");
				if (!Execute01()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			} else {
				// 消亡机构调查结论录入节点（不生成下一个节点）
				logger.debug("-----不可以生成下一个节点------");
				mTransferData.removeByName("transact");
				mTransferData.setNameAndValue("transact", "UPDATE");
				if (!Execute01()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mActivityOperator.mErrors);
					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005165";
			tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("------End Execute0000005165()------");
		return true;
	}

	/**
	 * 节点一般处理方法.只执行赔案层面调查结论节点任务,并消亡节点 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean Execute0000005175() {
		logger.debug("------Goto Execute0000005175()------");
		VData cInputData = new VData();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();

		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");

		if (tMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005175";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005175";
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
			// 获得执行节点任务后返回的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					cInputData.add(tempVData);
				}
			}

			// 删除当前节点
			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					mOperate, mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					cInputData.add(tempVData);
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
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "Execute0000005175";
			tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}
		mInputData.clear();
		mInputData = cInputData;
		logger.debug("------End Execute0000005175()------");
		return true;
	}

	/*
	 * 创建[发起二核]节点,同时调用业务处理类保存业务数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean ExecuteCreateScondUWNode() {
		logger.debug("------创建[发起二核]节点  Begin------");
		boolean tReturn = false;
		VData cInputData = new VData();
		VData tVData = new VData();
		// //取出“个人理赔合同批次表”中记录，用于建立“体检”等节点
		// LLCUWBatchSet tLLCUWBatchSet = new LLCUWBatchSet();
		// tLLCUWBatchSet = (LLCUWBatchSet)
		// mInputData.getObjectByObjectName("LLCUWBatchSet", 0);
		
		// 调用业务逻辑处理类，返回处理完数据
		LLSecondUWBL tLLSecondUWBL = new LLSecondUWBL();
		if (tLLSecondUWBL.submitData(mInputData, mOperate)) {
			VData tempVData1 = new VData();
			tempVData1 = tLLSecondUWBL.getResult();
			cInputData.add(tempVData1);
			mTransferData = null;
			mTransferData = (TransferData) tempVData1.getObjectByObjectName(
					"TransferData", 0);
			String tClaimRelFlag = (String) mTransferData
					.getValueByName("ClaimRelFlag");
			// 首先生成节点，由于第一次创建，没有触发服务类，在下面直接调用业务处理类
			try {
				// 只有0---相关的合同号 或者 //只有1---无关的合同号--------创建一条任务
				if (tClaimRelFlag.equals("0") || tClaimRelFlag.equals("1")) {
					logger.debug("------创建一条任务,相关标志------"
							+ mTransferData.getValueByName("ClaimRelFlag"));
					// 产生[发起二核]节点
					ActivityOperator tActivityOperator = new ActivityOperator();
					if (tActivityOperator.CreateStartMission("0000000005",
							"0000005505", tempVData1)) {
						VData tempVData2 = new VData();
						tempVData2 = tActivityOperator.getResult();
						cInputData.add(tempVData2);
						tempVData2 = null;
						tReturn = true;
					} else {
						// @@错误处理
						this.mErrors.copyAllErrors(mActivityOperator.mErrors);
						tReturn = false;
					}

				}
				// //“0---相关”“1---无关”都存在，-创建两条任务
				// if (tClaimRelFlag.equals("2"))
				// {
				// logger.debug("------创建两条任务,相关标志------");
				// logger.debug("------创建第一条任务-------------");
				// ActivityOperator tActivityOperator = new ActivityOperator();
				// mTransferData.removeByName("ClaimRelFlag");
				// mTransferData.setNameAndValue("ClaimRelFlag", "0");
				// //产生[发起二核]节点
				// logger.debug("------相关标志------" +
				// mTransferData.
				// getValueByName("ClaimRelFlag"));
				// if (tActivityOperator.CreateStartMission("0000000005",
				// "0000005505", tempVData1)) {
				// VData tempVData2 = new VData();
				// tempVData2 = tActivityOperator.getResult();
				// cInputData.add(tempVData2);
				// tempVData2 = null;
				// tReturn = true;
				// } else {
				// // @@错误处理
				// this.mErrors.copyAllErrors(mActivityOperator.
				// mErrors);
				// tReturn = false;
				// }
				// logger.debug("------创建第二条任务------");
				// ActivityOperator ttActivityOperator = new ActivityOperator();
				// mTransferData.removeByName("ClaimRelFlag");
				// mTransferData.setNameAndValue("ClaimRelFlag", "1");
				// logger.debug("------相关标志------" +
				// mTransferData.
				// getValueByName("ClaimRelFlag"));
				// //产生[发起二核]节点
				// if (ttActivityOperator.CreateStartMission("0000000005",
				// "0000005505", tempVData1)) {
				// VData tempVData3 = new VData();
				// tempVData3 = ttActivityOperator.getResult();
				// cInputData.add(tempVData3);
				// tempVData3 = null;
				// tReturn = true;
				// } else {
				// // @@错误处理
				// this.mErrors.copyAllErrors(mActivityOperator.
				// mErrors);
				// tReturn = false;
				// }
				//
				// }

			} catch (Exception ex) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				CError tError = new CError();
				tError.moduleName = "ClaimWorkFlowBL";
				tError.functionName = "Execute";
				tError.errorMessage = "工作流引擎工作出现异常!";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}

			tempVData1 = null;
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLSecondUWBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			tReturn = false;
		}
		// //为本批次发起二核的每个合同生成一个体检节点
		// VData TJVData = new VData();
		// TJVData = CreateTJNode(tLLCUWBatchSet);
		// if (TJVData != null)
		// {
		// for (int j = 0; j < TJVData.size(); j++)
		// {
		// VData tTJVData = new VData();
		// tTJVData = (VData) TJVData.get(j);
		// cInputData.add(tTJVData);
		// }
		// }
		// else
		// {
		// CError tError = new CError();
		// tError.moduleName = "ClaimWorkFlowBL";
		// tError.functionName = "CreateTJNode";
		// tError.errorMessage = "生成体检节点发生错误!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		mInputData.clear();
		mInputData = cInputData;
		logger.debug("------创建[发起二核]节点  end------");
		return tReturn;
	}

	/*
	 * 发起二次核保，生成二核节点同时生成体检节点 获取本批次下提起二核的所有合同号，然后循环为每个合同生成体检节点》 需要参数
	 * “LLCUWBatchSet” 返回结果 封装好的VData
	 */
	private VData CreateTJNode(LLCUWBatchSet ttLLCUWBatchSet) {
		logger.debug("----发起二核时为每个合同创建体检节点开始---------------");
		VData ttVData = new VData();
		// 获取本批次下提起二核的所有合同号，然后循环为每个合同生成体检节点
		String tBatNo = (String) mTransferData.getValueByName("BatNo");
		String tCasNo = (String) mTransferData.getValueByName("CasNo");
		LLCUWBatchSet tLLCUWBatchSet = new LLCUWBatchSet();
		tLLCUWBatchSet.set(ttLLCUWBatchSet);
		logger.debug("----应创建体检节点数目======" + tLLCUWBatchSet.size());
		for (int i = 1; i <= tLLCUWBatchSet.size(); i++) {
			ActivityOperator ttActivityOperator = new ActivityOperator();
			TransferData tTransferData = new TransferData();
			VData tVData = new VData();
			tTransferData.setNameAndValue("ContNo", tLLCUWBatchSet.get(i)
					.getContNo());
			tTransferData.setNameAndValue("PrtNo", tLLCUWBatchSet.get(i)
					.getContNo());
			tTransferData.setNameAndValue("OldPrtSeq", "");
			tTransferData.setNameAndValue("BatNo", tBatNo);
			tVData.add(tTransferData); //
			tVData.add(mGlobalInput);
			logger.debug("----为合同：" + tLLCUWBatchSet.get(i).getContNo()
					+ "创建体检节点");
			if (ttActivityOperator.CreateStartMission("0000000005",
					"0000001101", tVData)) {
				VData tempVData = new VData();
				tempVData = ttActivityOperator.getResult();
				ttVData.add(tempVData);
				tempVData = null;
			} else {
				ttVData = null;
			}

		}
		logger.debug("----实际创建体检节点数目======" + ttVData.size());
		logger.debug("----发起二核时为每个合同创建体检节点结束---------------");
		return ttVData;
	}

	/*
	 * [理赔人工核保完成]节点,同时调用业务处理类保存业务数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 */
	private boolean Execute0000005505() {
		logger.debug("------Goto Execute()------");
		VData cInputData = new VData();
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
			// 获得执行节点任务后返回的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					cInputData.add(tempVData);
				}
			}
			
			// 取出服务类中的计算信息
			String tOperator = (String) mTransferData
					.getValueByName("Operator");

			// 产生下一个任务节点
			if (tActivityOperator.CreateNextMissionSpecial(tMissionID,
					tSubMissionID, mOperate, mInputData, tOperator)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					cInputData.add(tempVData);
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
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "dealData";
			tError.errorMessage = "工作流引擎执行理赔活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}
		mInputData.clear();
		mInputData = cInputData;
		logger.debug("------End Execute()------");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareOutputData() {
		try {
			// 把所有需要提交的map融合到一个map，统一使用pubsubmit提交
			for (int i = 0; i < mInputData.size(); i++) {
				VData tData = new VData();
				tData = (VData) mInputData.get(i);
				MMap tmap = new MMap();
				tmap = (MMap) tData.getObjectByObjectName("MMap", 0);
				map.add(tmap);
			}
			mInputData.clear();
			mInputData.add(map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public VData getResult() {
		return mResult;
	}
}
