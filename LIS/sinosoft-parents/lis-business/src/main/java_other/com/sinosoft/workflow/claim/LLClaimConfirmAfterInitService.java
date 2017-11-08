package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 审批确认服务类
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

public class LLClaimConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLClaimConfirmAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 提交数据容器 */
	private MMap map = new MMap();
	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	/** 数据操作字符串 */
	private String mOperater;// 操作员
	private String mManageCom;
	private String mOperate;// 操作类型
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
	private String mClmNo = "";
	private String mReject = "";
	private String mModifyRgtState = "";// 案件类型修改
	private LLClaimPubFunBL tLLClaimPubFunBL= null;

	public LLClaimConfirmAfterInitService() {
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

		// 校验传入数据
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");

		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 分公司的审批人拥有上报和退回案件的权利，所以只有审批人选择审批通过的时候才进行审批案件权限的校验
		if (mReject.equals("0")) {
			
//			// 校验当前操作员是否有审批该案件的权限
//			LWMissionDB tLWMissionDB = new LWMissionDB();
//			tLWMissionDB.setActivityID(mActivityID);
//			tLWMissionDB.setMissionID(mMissionID);
//			tLWMissionDB.setSubMissionID(mSubMissionID);
//
//			tLWMissionDB.getInfo();
//			if (tLWMissionDB.mErrors.needDealError() == true) {
//				// @@错误处理
//				CError.buildErr(this, "案件上报重新查询案件的核赔级别失败");
//				return false;
//			}
//
//			logger.debug("赔案号为" + mClmNo + "的案件的核赔级别是"
//					+ tLWMissionDB.getMissionProp10());
//
//			LLClaimUserDB tLLClaimUserDB = new LLClaimUserDB();
//			logger.debug("当前操作员是" + mOperater);
//			tLLClaimUserDB.setUserCode(mOperater);
//			tLLClaimUserDB.getInfo();
//			if (tLLClaimUserDB.mErrors.needDealError() == true) {
//				// @@错误处理
//				CError.buildErr(this, "查询当前操作员的审批级别失败");
//				return false;
//			}
//
//			logger.debug(mOperater + "审批级别是"
//					+ tLLClaimUserDB.getUWLevel());
//
//			// 校验当前操作员是否有审批本案件的权限
//			if (tLWMissionDB.getMissionProp10().compareTo(
//					tLLClaimUserDB.getUWLevel()) > 0) {
//				// @@错误处理
//				CError.buildErr(this, "操作员" + mOperater
//						+ "不具备审批本案件的权限，必须上报交给总公司的审批人员处理!");
//				return false;
//			}
			
			// 权限校验
			tLLClaimPubFunBL = new LLClaimPubFunBL();
			if(!tLLClaimPubFunBL.getCheckPopedom(mClmNo, mOperater)){
				
				// @@错误处理
				tLLClaimPubFunBL=null;
				CError.buildErr(this, "操作员"+mOperater+"不具备核赔通过权限,需上报给上一级核赔员审批!");
				return false;
			}
			
			tLLClaimPubFunBL=null;
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
		mClmNo = (String) mTransferData.getValueByName("RptNo");
		mReject = (String) mTransferData.getValueByName("Reject");
		mModifyRgtState = (String) mTransferData
				.getValueByName("ModifyRgtState");
		mInputData = cInputData;
		mOperate = cOperate;

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据操作员失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		logger.debug("LLClaimConfirmReportBackBL获得的MIssionID="
				+ mMissionID);
		if (mMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据MissionID失败!");
			return false;
		}

		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		logger.debug("LLClaimConfirmReportBackBL获得的SubMIssionID="
				+ mSubMissionID);
		if (mSubMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据SubMissionID失败!");
			return false;
		}

		mActivityID = (String) mTransferData.getValueByName("ActivityID");
		logger.debug("LLClaimConfirmReportBackBL获得的ActivityID="
				+ mActivityID);
		if (mActivityID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据ActivityID失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------Service dealData BEGIN----------");
		boolean tReturn = false;

		// ----------------------------------------------------------------------BEG
		// 功能：处理业务层面数据
		// 处理：1 审批结论保存
		// 2 审批不通过时向案件核赔履历表中写入数据
		// 3 赔案状态更改
		// ----------------------------------------------------------------------
		LLClaimConfirmBL tLLClaimConfirmBL = new LLClaimConfirmBL();
		if (!tLLClaimConfirmBL.submitData(mInputData, "INSERT")) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,"+tLLClaimConfirmBL.mErrors.getLastError());
			mResult.clear();
			mInputData = null;
			return false;
		} else {
			VData tVDate = new VData();
			tVDate = tLLClaimConfirmBL.getResult();
			logger.debug("-----start Service getData from BL");
			MMap tMap = new MMap();
			tMap = (MMap) tVDate.getObjectByObjectName("MMap", 0);
			map.add(tMap);
			tReturn = true;
		}
		// ----------------------------------------------------------------------END

		// ----------------------------------------------------------------------BEG
		// 功能：根据审批结论分别提交处理
		// 处理：1 审批不通过时,为审核节点增加"来自"属性,分为B审批进入审核因为赔案金额为正数，
		// 2 审批通过时,处理财务数据，详细见[功能设计--审批--根据结论的处理方式.doc]
		// ----------------------------------------------------------------------
		if (mReject.equals("1")) {
			// 审批不通过
			String strSQL1 = "";
			strSQL1 = " select (RealPay + BalancePay - BeforePay) from LLClaim where "
					+ " ClmNO='" + "?ClmNO?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strSQL1);
			sqlbv.put("ClmNO", mClmNo);
			ExeSQL exesql = new ExeSQL();
			String tPay = exesql.getOneValue(sqlbv);
			Double tDouble = new Double(tPay);
			double tConfirmPay = tDouble.doubleValue();
			
			// 为公共传输数据集合中添加工作流下一节点属性字段数据
			mTransferData.removeByName("RptorState");
			mTransferData.setNameAndValue("RptorState", "30");
			mTransferData.setNameAndValue("ComeWhere", "C");

			// 更改重置保单结算和合同处理状态,赔案状态为审核
			String sql1 = " update LLRegister set ClmState = '30' where"
					+ " RgtNo = '" + "?ClmNO?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql1);
			sqlbv1.put("ClmNO", mClmNo);
			map.put(sqlbv1, "UPDATE");

			String tSql2 = "update llclaim set conbalflag = '0',ConDealFlag = '0',ClmState = '30' where "
					+ " clmno = '" + "?ClmNO?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSql2);
			sqlbv2.put("ClmNO", mClmNo);
			map.put(sqlbv2, "UPDATE");

			String tSql3 = "update llclaimpolicy set ClmState = '30' where "
					+ " clmno = '" + "?ClmNO?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSql3);
			sqlbv3.put("ClmNO", mClmNo);
			map.put(sqlbv3, "UPDATE");
			
			
			ExeSQL tExeSQL=new ExeSQL();
			String sql="select AuditPopedom from llclaim where clmno='"+"?ClmNO?"+"'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(sql);
			sqlbv4.put("ClmNO", mClmNo);
			String AuditPopedom=tExeSQL.getOneValue(sqlbv4);
			logger.debug("案件"+mClmNo+"的审核权限是"+AuditPopedom);
			mTransferData.removeByName("Popedom");
			mTransferData.setNameAndValue("Popedom", AuditPopedom);
			
			tExeSQL=null;
			sql=null;
		}else if("2".equals(mReject)){
			/**************工作流升级，合并审批上报功能********************/
			//重新确定新的审批人
			LLClaimPopedomSetBL tLLClaimPopedomSetBL = new LLClaimPopedomSetBL();
			String approvalOperator = tLLClaimPopedomSetBL.PubConfirmNewPopedom(mClmNo,mGlobalInput.Operator);
			
			if (approvalOperator == null || approvalOperator.equals("")) {
				
				// @@错误处理
				CError.buildErr(this, "查询新的审批人失败!");
				return false;
			}
			logger.debug("重新确定审批赔案号为" + mClmNo + "的案件的的总公司的审批人员是"
				+ approvalOperator);
			//初始化审批默认操作员
			mTransferData.removeByName("DefaultOperator");
			mTransferData.setNameAndValue("DefaultOperator", approvalOperator);
		} 
		else 
		{
			
			/**
			 * 2008-12-16 zhangzheng 
			 
			 */
			
			/*
			// 审批通过
			LLClaimConfirmPassBL tLLClaimConfirmPassBL = new LLClaimConfirmPassBL();
			if (!tLLClaimConfirmPassBL.submitData(mInputData, "")) {
				// @@错误处理
				CError.buildErr(this, "审批通过处理失败,"+tLLClaimConfirmPassBL.mErrors.getLastError());
				mResult.clear();
				mInputData = null;
				return false;
			} else {
				VData tVDate = new VData();
				tVDate = tLLClaimConfirmPassBL.getResult();
				logger.debug("-----start Service getData from BL");
				MMap tMap = new MMap();
				tMap = (MMap) tVDate.getObjectByObjectName("MMap", 0);
				map.add(tMap);
				tReturn = true;
			}
			*/

			// 更改赔案状态为结案,只是结案日期和时间
			String sql1 = " update LLRegister set ClmState = '50',EndCaseFlag = '1',EndCaseDate='"+ "?CurrentDate?"
					+ "',ModifyDate='"+ "?CurrentDate?"+ "',ModifyTime='"+ "?CurrentTime?"+ "'"
					+ " where RgtNo = '"+ "?ClmNO?" + "'";
			logger.debug("更改llregister的赔案状态，结案标志和结案日期的sql=" + sql1);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(sql1);
			sqlbv5.put("CurrentDate", CurrentDate);
			sqlbv5.put("CurrentTime", CurrentTime);
			sqlbv5.put("ClmNO", mClmNo);

			String sql2 = " update llclaim set ClmState = '50',EndCaseFlag = '1',EndCaseDate='"
					+ "?CurrentDate?" + "',ModifyDate='" + "?CurrentDate?"
					+ "',ModifyTime='" + "?CurrentTime?" + "'" + " where clmno = '"
					+ "?ClmNO?" + "'";
			logger.debug("更改llclaim的赔案状态和结案日期的sql=" + sql2);
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(sql2);
			sqlbv6.put("CurrentDate", CurrentDate);
			sqlbv6.put("CurrentTime", CurrentTime);
			sqlbv6.put("ClmNO", mClmNo);
			
			String sql3 = " update llclaimPolicy set EndCaseDate='"
				+ "?CurrentDate?" + "',ModifyDate='" + "?CurrentDate?"
				+ "',ModifyTime='" + "?CurrentTime?" + "'" + " where clmno = '"
				+ "?ClmNO?" + "'";
			logger.debug("更改llclaim的赔案状态和结案日期的sql=" + sql2);
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(sql3);
			sqlbv7.put("CurrentDate", CurrentDate);
			sqlbv7.put("CurrentTime", CurrentTime);
			sqlbv7.put("ClmNO", mClmNo);
			
			map.put(sqlbv5, "UPDATE");
			map.put(sqlbv6, "UPDATE");
			map.put(sqlbv7, "UPDATE");

			/*
			// 解除保单挂起
			LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
			if (!tLLLcContReleaseBL.submitData(mInputData, "")) {
				// @@错误处理
				CError.buildErr(this, "审批通过接触保单挂起失败,"+tLLLcContReleaseBL.mErrors.getLastError());
				return false;
			} else {
				VData tempVData = new VData();
				tempVData = tLLLcContReleaseBL.getResult();
				MMap tMap = new MMap();
				tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				map.add(tMap);
			}

			// 更改死亡标志
			if (!dealDeath()) {
				return false;
			}
			*/

			// 为公共传输数据集合中添加工作流下一节点属性字段数据,结案要回到立案机构
//			String sql4 = " select MngCom from LLRegister where" + " RgtNo = '"
//					+ mClmNo + "'";
//			ExeSQL tExeSQL = new ExeSQL();
//			String tMngCom = tExeSQL.getOneValue(sql4);
//			
//			if (tMngCom != null) {
//				mTransferData.removeByName("MngCom");
//				mTransferData.setNameAndValue("MngCom", tMngCom);
//			}
			//2010-4-08 理赔调整流程，案件审批通过则案件结束，相应的延滞利息、财务需要进行处理，进行保单解挂
			//延滞利息处理,由于财务处理需要包含延滞利息的处理，所以延滞利息处理需要提交数据库
			LLClaimYZLXBL tClaimYZLXBL=new LLClaimYZLXBL();
	  		VData tVData=new VData();
			TransferData tTransferData =new TransferData();
			tTransferData.setNameAndValue("ClmNo",mClmNo);
			tTransferData.setNameAndValue("ClmState", "50");
			tTransferData.setNameAndValue("EndCaseDate",CurrentDate);
			tVData.add(tTransferData);
		    tVData.add(mGlobalInput);
		    if (!tClaimYZLXBL.submitData(tVData,"YZLX_compute"))
		    {
				CError.buildErr(this, "计算延滞利息失败，原因为,"+tClaimYZLXBL.mErrors.getError(0).errorMessage);
				return false;
		    }
		    
			// 保单结算,合同处理等生效,并生成财务数据
			LLClaimConfirmPassBL tLLClaimConfirmPassBL = new LLClaimConfirmPassBL();
	  		tVData=new VData();
			tTransferData =new TransferData();
			tTransferData.setNameAndValue("RptNo",mClmNo);
			tVData.add(tTransferData);
		    tVData.add(mGlobalInput);
			if (!tLLClaimConfirmPassBL.submitData(tVData, "")) {
				// @@错误处理
				CError.buildErr(this, "财务处理失败，原因为"+tLLClaimConfirmPassBL.mErrors.getLastError());
				mResult.clear();
				mInputData = null;
				return false;
			} 
			else
			{
				VData tempVData = new VData();
				tempVData = tLLClaimConfirmPassBL.getResult();
				MMap mMMap = new MMap();
				mMMap =(MMap)tempVData.getObjectByObjectName("MMap", 0);
				map.add(mMMap);
				mMMap=null;
			}
			
			
			// 解除保单挂起
			LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
			if (!tLLLcContReleaseBL.submitData(mInputData, "")) {
				// @@错误处理
				CError.buildErr(this, "解除保单挂起失败,"+tLLLcContReleaseBL.mErrors.getLastError());
				return false;
			} else {
				VData tempVData = new VData();
				tempVData = tLLLcContReleaseBL.getResult();
				MMap tMap = new MMap();
				tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				map.add(tMap);
				tMap=null;
			}

			// 更改死亡标志
			if (!dealDeath()) {
				return false;
			}

		}
		// 案件类型修改Modify by zhaorx 2006-04-17
		String tSQLF = " update llregister set rgtstate='" + "?rgtstate?"
				+ "' where " + " rgtno='" + "?rgtno?" + "'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSQLF);
		sqlbv8.put("rgtstate", mModifyRgtState);
		sqlbv8.put("rgtno", mClmNo);
		map.put(sqlbv8, "UPDATE");
		// ----------------------------------------------------------------------END
		return tReturn;
	}

	/**
	 * 针对死亡案件更改死亡日期和标志 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean dealDeath() {
		LLCaseDB tLLCaseDB = new LLCaseDB();
		tLLCaseDB.setCaseNo(mClmNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.query();
		if (tLLCaseSet == null && tLLCaseSet.size() < 1) {
			CError.buildErr(this, "查询分案信息失败!");
			return false;
		}
		for (int i = 1; i <= tLLCaseSet.size(); i++) {
			String tCNo = tLLCaseSet.get(i).getCustomerNo();
			LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
			tLLAppClaimReasonDB.setCaseNo(mClmNo);
			tLLAppClaimReasonDB.setRgtNo(mClmNo);
			tLLAppClaimReasonDB.setCustomerNo(tCNo);
			LLAppClaimReasonSet tLLAppClaimReasonSet = tLLAppClaimReasonDB
					.query();
			if (tLLAppClaimReasonSet == null && tLLAppClaimReasonSet.size() < 1) {
				CError.buildErr(this, "查询赔案理赔类型信息失败!");
				return false;
			}
			for (int j = 1; j <= tLLAppClaimReasonSet.size(); j++) {
				String tCode = tLLAppClaimReasonSet.get(j).getReasonCode()
						.substring(1, 3);
				if (tCode.equals("02")) // 死亡
				{
					// 更改立案分案表
					String sql3 = " update llcase set DieFlag = '1',"
							+ " DeathDate = AccDate where" + " CaseNo = '"
							+ "?mClmNo?" + "'" + " and CustomerNo = '" + "?tCNo?" + "'";
					SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
					sqlbv9.sql(sql3);
					sqlbv9.put("mClmNo", mClmNo);
					sqlbv9.put("tCNo", tCNo);
					map.put(sqlbv9, "UPDATE");
					// 更改报案分案表
					String sql4 = " update LLSubReport set DieFlag = '1',"
							+ " DieDate = AccDate where" + " SubRptNo = '"
							+ "?mClmNo?" + "'" + " and CustomerNo = '" + "?tCNo?" + "'";
					SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
					sqlbv10.sql(sql4);
					sqlbv10.put("mClmNo", mClmNo);
					sqlbv10.put("tCNo", tCNo);
					map.put(sqlbv10, "UPDATE");
					// 更改客户表
					String sql5 = " update LDPerson set DeathDate = to_date('"
							+ "?accdate?"
							+ "','yyyy-mm-dd') where" + " CustomerNo = '"
							+ "?tCNo?" + "'";
					SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
					sqlbv11.sql(sql5);
					sqlbv11.put("accdate", tLLCaseSet.get(i).getAccDate());
					sqlbv11.put("tCNo", tCNo);
					map.put(sqlbv11, "UPDATE");

					break;
				}
			}
		}
		return true;
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 准备数据，测试权限部分
		LLClaimConfirmAfterInitService mLLClaimConfirmAfterInitService = new LLClaimConfirmAfterInitService();

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("RptNo", "90000000003");
		tTransferData.setNameAndValue("MngCom", "86");
		tTransferData.setNameAndValue("MissionID", "10000000000000000023");
		tTransferData.setNameAndValue("SubMissionID", "1");
		tTransferData.setNameAndValue("ActivityID", "0000005055");

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ComCode = "8611";
		tGlobalInput.ManageCom = "8611";
		tGlobalInput.Operator = "lp";

		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tGlobalInput);

		// 调用submit方法,开始测试
		mLLClaimConfirmAfterInitService.submitData(tVData, "REPORT");
	}

}
