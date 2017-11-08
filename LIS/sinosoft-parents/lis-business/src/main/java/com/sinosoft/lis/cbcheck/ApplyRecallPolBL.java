package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.LBMissionDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LWCorrespondingDB;
import com.sinosoft.lis.db.LWFieldMapDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LWProcessInstanceDB;
import com.sinosoft.lis.db.LCIndAppSignLogDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCApplyRecallPolSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWFieldMapSchema;
import com.sinosoft.lis.schema.LCIndAppSignLogSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LCApplyRecallPolSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LWFieldMapSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LWProcessInstanceSet;
import com.sinosoft.lis.vschema.LCIndAppSignLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 * @param <ExeSql>
 */

public class ApplyRecallPolBL<ExeSql> {
private static Logger logger = Logger.getLogger(ApplyRecallPolBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private String mOperate;
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();// ln 2008-10-20 add

	private MMap map = new MMap();
	private MMap mLCCUWmap = new MMap();
	private LCApplyRecallPolSchema mLCApplyRecallPolSchema = new LCApplyRecallPolSchema();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String mPrtNo = "";
	private String mContNo = "";
	private String mProposalContNo = "";
	private String mMissionID = "";
	private String mStrMissionID = "";
	private String mUWWithDReason = ""; // 撤单原因 ln 2008-10-20 add
	private String mUWWithDReasonCode = ""; // 撤单原因代码 ln 2009-2-7 add
	private String mContent = "";// 撤单说明 ln 2008-10-20 add
    /** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	// 业务处理相关变量
	LCApplyRecallPolSet mLCApplyRecallPolSet = new LCApplyRecallPolSet();
	LCApplyRecallPolSet outDelLCApplyRecallPolSet = new LCApplyRecallPolSet();
	LCContSchema mLCContSchema = new LCContSchema();

	public ApplyRecallPolBL() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		//ln 2008-10-21 加锁        
		if (!CheckLock(mContNo))
		{
			logger.debug("加锁失败!");
			CError.buildErr(this, "该投保单正在被其他人员操作，请您稍后再试!");
			this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
			return false;
		}
		
		try
		{
			// 进行业务处理
			if (!dealData(mOperate)) {
				return false;
			}
	
			// 准备往后台的数据
			if (!prepareOutputData()) {
				return false;
			}
	
			// 数据提交
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError.buildErr(this, "数据提交失败!");
				return false;
			}
			mInputData = null;				

			String strSql1 = " select * from LCIndAppSignLog where PrtNo = '"+"?PrtNo?"+"' and "
				+ " ErrType='02'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strSql1);
			sqlbv.put("PrtNo", mPrtNo);
			LCIndAppSignLogSet tLCIndAppSignLogSet = new LCIndAppSignLogSet();
			LCIndAppSignLogDB tLCIndAppSignLogDB = new LCIndAppSignLogDB();			
			tLCIndAppSignLogSet = tLCIndAppSignLogDB.executeQuery(sqlbv);
			if (tLCIndAppSignLogSet!=null && tLCIndAppSignLogSet.size() > 0) {
				logger.debug("****************修改此原因的错误日志状态为已完成****************");
				LCIndAppSignLogSchema tLCIndAppSignLogSchema = new LCIndAppSignLogSchema();
				MMap tMMap = new MMap();
				VData tResult = new VData();
				tLCIndAppSignLogSchema.setSchema(tLCIndAppSignLogSet.get(1));
				int SignNo = tLCIndAppSignLogSchema.getSignNo() + 1;
				tLCIndAppSignLogSchema.setSignNo(SignNo);
				tLCIndAppSignLogSchema.setOperator(mGlobalInput.Operator);
				tLCIndAppSignLogSchema.setMakeDate(CurrentDate);
				tLCIndAppSignLogSchema.setMakeTime(CurrentTime);
				tLCIndAppSignLogSchema.setState("01");//撤单失败过但是这次撤单成功
				tMMap.put(tLCIndAppSignLogSchema, "UPDATE");
				
				tResult.add(tMMap);	
				tPubSubmit = new PubSubmit();

				if (!tPubSubmit.submitData(tResult, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tPubSubmit.mErrors);
					CError.buildErr(this, "数据提交失败!");
					logger.debug("*****************end 修改日志失败！***************");
					return false;
				}
				
				logger.debug("*****************end 修改日志成功！***************");
			}			
			
		} catch(Exception e)
		{		
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;			
		} finally {
			//解锁
			// 使用新的加锁逻辑
			mPubLock.unLock();
		}  
			
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	private boolean dealData(String Operate) {
		
//		日志监控，性能监控。 
		PubFun.logPerformance (mGlobalInput,mContNo,"校验投保单"+mContNo+"交费是否银行在途","1");
		if (!checkBackFromBank()) { // 校验是滞有银行在途数据，若有不能做撤单申请。

			return false;
		}
		// 查询合同信息
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setPrtNo(mPrtNo);
		tLCContSet.set(tLCContDB.query());
		if (tLCContDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			CError.buildErr(this, "合同信息查询失败!");
			return false;
		}
		mLCContSchema = tLCContSet.get(1).getSchema();
		if (tLCContSet == null || tLCContSet.size() != 1) {
			CError.buildErr(this,"没有找到该印刷号对应的合同信息，请确认!");
			return false;
		}
		LCContSchema tLCContSchema = tLCContSet.get(1);
		//mContNo = tLCContSchema.getContNo();
		mProposalContNo = tLCContSchema.getProposalContNo();

		// 准备合同核保信息
		if (!prepareContUW(tLCContSchema)) {
			return false;
		}

		// 查询该保单下所有险种保单
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPrtNo(mPrtNo);
		tLCPolSet.set(tLCPolDB.query());
		if (tLCPolSet == null || tLCPolSet.size() == 0) {
			CError.buildErr(this, "没有找到该印刷号对应的险种保单，请确认!");
			return false;
		}

		// 生成保单撤单申请记录
		LCPolSchema aLCPolSchema = new LCPolSchema();
		LCApplyRecallPolSchema tLCApplyRecallPolSchema;
		for (int j = 1; j <= tLCPolSet.size(); j++) {
			aLCPolSchema.setSchema(tLCPolSet.get(j));

			// 准备险种核保信息
			if (!preparePolUW(aLCPolSchema)) {
				return false;
			}

			tLCApplyRecallPolSchema = new LCApplyRecallPolSchema();

			tLCApplyRecallPolSchema.setPrtNo(aLCPolSchema.getPrtNo());
			tLCApplyRecallPolSchema.setProposalNo(aLCPolSchema.getProposalNo());
			tLCApplyRecallPolSchema.setMainPolNo(aLCPolSchema.getMainPolNo());
			tLCApplyRecallPolSchema.setRemark(mLCApplyRecallPolSchema
					.getRemark());
			tLCApplyRecallPolSchema.setApplyType(mLCApplyRecallPolSchema
					.getApplyType()); // Add by
			// Minim
			tLCApplyRecallPolSchema.setInputState("0");
			tLCApplyRecallPolSchema.setOperator(mGlobalInput.Operator);
			tLCApplyRecallPolSchema.setManageCom(mGlobalInput.ManageCom);
			tLCApplyRecallPolSchema.setMakeDate(CurrentDate);
			tLCApplyRecallPolSchema.setMakeTime(CurrentTime);
			tLCApplyRecallPolSchema.setModifyDate(CurrentDate);
			tLCApplyRecallPolSchema.setModifyTime(CurrentTime);
			mLCApplyRecallPolSet.add(tLCApplyRecallPolSchema);

			LCApplyRecallPolSchema outDelLCApplyRecallPolSchema = new LCApplyRecallPolSchema();
			outDelLCApplyRecallPolSchema.setProposalNo(aLCPolSchema
					.getProposalNo());
			outDelLCApplyRecallPolSet.add(outDelLCApplyRecallPolSchema.getDB()
					.query());
		}

		// 提交合同、保单级核保主表、核保子表记录
		mInputData.clear();
		mInputData.add(mLCCUWmap);

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this, "合同、保单级核保主表、核保子表记录提交失败!");
			return false;
		}

		map.put(outDelLCApplyRecallPolSet, "DELETE");
		map.put(mLCApplyRecallPolSet, "INSERT");

		// ====ADD====zhangtao======2005-04-13=================BGN======================
		// 获取工作流任务ID
		if (!getMissionID()) {
			return false;
		}
//		日志监控，性能监控。 
		PubFun.logPerformance (mGlobalInput,mContNo,"开始撤单相关处理","2");
		logger.debug("=======调用核保确认程序做撤单相关处理============BGN==");
		TransferData tTransferData = new TransferData();
		//tTransferData.setNameAndValue("MissionID", mMissionID);
		tTransferData.setNameAndValue("ContNo", mContNo);
		tTransferData.setNameAndValue("PrtNo", mPrtNo);
		tTransferData.setNameAndValue("UWFlag", "a");
		tTransferData.setNameAndValue("UWIdea", mLCApplyRecallPolSchema
				.getRemark());
		tTransferData.setNameAndValue("UWCancelFlag", "UWCancelFlag");
		tTransferData.setNameAndValue("UWUpReport", "0");
		tTransferData.setNameAndValue("WithDReason", mUWWithDReason);
		tTransferData.setNameAndValue("WithDReasonCode", mUWWithDReasonCode);

		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(mGlobalInput);

		UWConfirmBL tUWConfirmBL = new UWConfirmBL();
		if (!tUWConfirmBL.submitData(tVData, "")) {
			mErrors.copyAllErrors(tUWConfirmBL.mErrors);
			CError.buildErr(this,"撤单处理失败!");
			return false;
		}
		VData tResultData = tUWConfirmBL.getResult();
		MMap tMap = (MMap) tResultData.getObjectByObjectName("MMap", 0);
		TransferData tTransferData1 = new TransferData();
		tTransferData1 = (TransferData) tResultData.getObjectByObjectName("TransferData", 0);
		map.add(tMap);
		logger.debug("=======调用核保确认程序做撤单相关处理============END==");

		LCPolSet sLCPolSet = new LCPolSet();
		LCPolDB sLCPolDB = new LCPolDB();
		sLCPolDB.setContNo(mContNo);
		sLCPolSet.set(sLCPolDB.query());
		if (sLCPolSet != null && sLCPolSet.size() > 0) {
			for (int i = 1; i <= sLCPolSet.size(); i++) {
				sLCPolSet.get(i).setUWFlag("a");
			}
			map.put(sLCPolSet, "UPDATE");
		}
		
//		日志监控，性能监控。 
		PubFun.logPerformance (mGlobalInput,mContNo,"发送撤单通知书","3");
		//ln add 2008-10-21 发送撤单通知书
		if(!sendPrint(tTransferData1))
		{			
			return false;
		}
		//ln end add 2008-10-21
		if(Operate!=null && !Operate.equals("") && Operate.equals("Auto"))
		{
			logger.debug("*******----------自动撤单需要保留核保订正工作流节点---------*******");
			// 删除保单工作流节点 [备份]
			LWMissionSet tLWMissionSet = new LWMissionSet();
			LWMissionDB tLWMissionDB = new LWMissionDB();
//			String sql ="select * from lwmission where missionid in "+ mStrMissionID +" and activityid<>'0000001149'";
			String sql ="select * from lwmission where missionid in "+ mStrMissionID +" and activityid not  in (select activityid from lwactivity  where functionid ='10010041')";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql);
			//sqlbv1.put("missionid", mStrMissionID);
			tLWMissionSet.set(tLWMissionDB.executeQuery(sqlbv1));
//			if (tLWMissionDB.mErrors.needDealError() == true) {
			if (tLWMissionSet == null || tLWMissionSet.size()<1) {
				// @@错误处理
				mErrors.copyAllErrors(tLWMissionDB.mErrors);
				CError.buildErr(this,"保单工作流任务查询失败!" + "印刷号：" + mPrtNo + "任务号："
						+ mStrMissionID);
				return false;
			}
			Reflections tReflections = new Reflections();
			LBMissionSet tLBMissionSet = new LBMissionSet();
			LBMissionSchema tLBMissionSchema;
			String tSerielNo = "";
			for (int i = 1; i <= tLWMissionSet.size(); i++) {
				tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);

				tLBMissionSchema = new LBMissionSchema();
				tReflections.transFields(tLBMissionSchema, tLWMissionSet.get(i));

				tLBMissionSchema.setSerialNo(tSerielNo);
				tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
				tLBMissionSchema.setLastOperator(mGlobalInput.Operator);
				tLBMissionSchema.setMakeDate(CurrentDate);
				tLBMissionSchema.setMakeTime(CurrentTime);
				tLBMissionSchema.setModifyDate(CurrentDate);
				tLBMissionSchema.setModifyTime(CurrentTime);
				tLBMissionSet.add(tLBMissionSchema);
			}
			map.put(tLBMissionSet, "INSERT");
			map.put(tLWMissionSet, "DELETE");
			
			return true;
		}
		// 删除保单工作流节点 [备份]
		LWMissionSet tLWMissionSet = new LWMissionSet();
		LWMissionDB tLWMissionDB = new LWMissionDB();
//		tLWMissionDB.setMissionID(mMissionID);
		String sql = "select * from lwmission where missionid in " + mStrMissionID;
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("missionid", mStrMissionID);
		tLWMissionSet.set(tLWMissionDB.executeQuery(sqlbv2));
		if (tLWMissionSet == null || tLWMissionSet.size()<1) {
			// @@错误处理
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError.buildErr(this,"保单工作流任务查询失败!" + "印刷号：" + mPrtNo + "任务号："
					+ mStrMissionID);
			return false;
		}
		Reflections tReflections = new Reflections();
		LBMissionSet tLBMissionSet = new LBMissionSet();
		LBMissionSchema tLBMissionSchema;
		String tSerielNo = "";
		for (int i = 1; i <= tLWMissionSet.size(); i++) {
			tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);

			tLBMissionSchema = new LBMissionSchema();
			tReflections.transFields(tLBMissionSchema, tLWMissionSet.get(i));

			tLBMissionSchema.setSerialNo(tSerielNo);
			tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tLBMissionSchema.setLastOperator(mGlobalInput.Operator);
			tLBMissionSchema.setMakeDate(CurrentDate);
			tLBMissionSchema.setMakeTime(CurrentTime);
			tLBMissionSchema.setModifyDate(CurrentDate);
			tLBMissionSchema.setModifyTime(CurrentTime);
			tLBMissionSet.add(tLBMissionSchema);
		}
		map.put(tLBMissionSet, "INSERT");
		map.put(tLWMissionSet, "DELETE");

//		String delSQL = "DELETE FROM LWMISSION WHERE MISSIONID = '"
//				+ mMissionID + "'";
//		map.put(delSQL, "DELETE");

		// ====ADD====zhangtao======2005-04-13=================END======================
		return true;
	}

	// 从输入数据中得到所有对象
	// 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	private boolean getInputData() {
		mLCApplyRecallPolSchema = (LCApplyRecallPolSchema) mInputData
				.getObjectByObjectName("LCApplyRecallPolSchema", 0);

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mLCApplyRecallPolSchema == null
				|| mLCApplyRecallPolSchema.getPrtNo() == null
				|| mTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "没有得到足够的数据!");
			return false;
		}

		mPrtNo = mLCApplyRecallPolSchema.getPrtNo();
		mUWWithDReason = (String) mTransferData.getValueByName("UWWithDReason");
		mUWWithDReasonCode = (String) mTransferData.getValueByName("UWWithDReasonCode");
		mContent = (String) mTransferData.getValueByName("Content");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null	|| mContNo == "") {
			// @@错误处理
			CError.buildErr(this,"没有得到足够的数据!");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	// 准备往后层输出所需要的数据
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this,"在准备往后层处理所需要的数据时出错:" + ex.toString());
			return false;
		}

		return true;
	}
	
	 /**并发控制
	 * 校验是否可以进行操作
	 */
	private boolean CheckLock(String ContNo) {

		if (!mPubLock.lock(ContNo, "LC0005" )) {
			return false;
		}
		return true;
	}

	/**
	 * 准备合同核保信息
	 */
	private boolean prepareContUW(LCContSchema tLCContSchema) {
		Reflections ref = new Reflections();
		// 合同核保主表
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		tLCCUWMasterSet.set(tLCCUWMasterDB.query());
		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError.buildErr(this,"合同核保总表查询失败!" + "合同号：" + mContNo);
			return false;
		}

		if (tLCCUWMasterSet.size() == 0) {
			tLCCUWMasterSchema.setContNo(mContNo);
			tLCCUWMasterSchema.setGrpContNo(tLCContSchema.getGrpContNo());
			tLCCUWMasterSchema.setProposalContNo(tLCContSchema
					.getProposalContNo());
			tLCCUWMasterSchema.setUWNo(1);
			tLCCUWMasterSchema.setInsuredNo(tLCContSchema.getInsuredNo());
			tLCCUWMasterSchema.setInsuredName(tLCContSchema.getInsuredName());
			tLCCUWMasterSchema.setAppntNo(tLCContSchema.getAppntNo());
			tLCCUWMasterSchema.setAppntName(tLCContSchema.getAppntName());
			tLCCUWMasterSchema.setAgentCode(tLCContSchema.getAgentCode());
			tLCCUWMasterSchema.setAgentGroup(tLCContSchema.getAgentGroup());
			// tLCCUWMasterSchema.setUWGrade(mUWGrade); //核保级别
			// tLCCUWMasterSchema.setAppGrade(mUWGrade); //申报级别
			tLCCUWMasterSchema.setPostponeDay("");
			tLCCUWMasterSchema.setPostponeDate("");
			tLCCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			// tLCCUWMasterSchema.setState(mContPassFlag);
			// tLCCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCCUWMasterSchema.setHealthFlag("0");
			tLCCUWMasterSchema.setSpecFlag("0");
			tLCCUWMasterSchema.setQuesFlag("0");
			tLCCUWMasterSchema.setReportFlag("0");
			tLCCUWMasterSchema.setChangePolFlag("0");
			tLCCUWMasterSchema.setPrintFlag("0");
			tLCCUWMasterSchema.setPrintFlag2("0");
			tLCCUWMasterSchema.setManageCom(tLCContSchema.getManageCom());
			tLCCUWMasterSchema.setUWIdea("");
			tLCCUWMasterSchema.setUpReportContent("");
			tLCCUWMasterSchema.setOperator(mGlobalInput.Operator); // 操作员
			tLCCUWMasterSchema.setMakeDate(CurrentDate);
			tLCCUWMasterSchema.setMakeTime(CurrentTime);
			tLCCUWMasterSchema.setModifyDate(CurrentDate);
			tLCCUWMasterSchema.setModifyTime(CurrentTime);
		} else {
			tLCCUWMasterSchema = tLCCUWMasterSet.get(1);
			tLCCUWMasterSchema.setUWNo(tLCCUWMasterSchema.getUWNo() + 1);
			// tLCCUWMasterSchema.setState(mContPassFlag);
			// tLCCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			// tLCCUWMasterSchema.setUWGrade(mUWGrade); //核保级别
			// tLCCUWMasterSchema.setAppGrade(mUWGrade); //申报级别
			tLCCUWMasterSchema.setOperator(mGlobalInput.Operator); // 操作员
			tLCCUWMasterSchema.setModifyDate(CurrentDate);
			tLCCUWMasterSchema.setModifyTime(CurrentTime);
		}
		tLCCUWMasterSet.clear();
		tLCCUWMasterSet.add(tLCCUWMasterSchema);

		// 合同核保轨迹表
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError.buildErr(this, mContNo + "合同核保轨迹表查失败!");
			return false;
		}

		ref.transFields(tLCCUWSubSchema, tLCCUWMasterSchema);
		int nUWNo = tLCCUWSubSet.size();
		if (nUWNo > 0) {
			tLCCUWSubSchema.setUWNo(++nUWNo); // 第n次核保
		} else {
			tLCCUWSubSchema.setUWNo(1); // 第1次核保
		}

		tLCCUWSubSchema.setOperator(tLCCUWMasterSchema.getOperator()); // 操作员
		tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
		tLCCUWSubSchema.setMakeDate(CurrentDate);
		tLCCUWSubSchema.setMakeTime(CurrentTime);
		tLCCUWSubSchema.setModifyDate(CurrentDate);
		tLCCUWSubSchema.setModifyTime(CurrentTime);

		tLCCUWSubSet.clear();
		tLCCUWSubSet.add(tLCCUWSubSchema);
		mLCCUWmap.put(tLCCUWMasterSet, "DELETE&INSERT");
		mLCCUWmap.put(tLCCUWSubSet, "INSERT");

		return true;
	}

	/**
	 * 准备险种核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePolUW(LCPolSchema tLCPolSchema) {
		int tuwno = 0;
		String mOldPolNo = tLCPolSchema.getPolNo();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setPolNo(mOldPolNo);
		tLCUWMasterSet = tLCUWMasterDB.query();
		if (tLCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError.buildErr(this,mOldPolNo + "个人核保总表取数失败!");
			return false;
		}

		int n = tLCUWMasterSet.size();
		if (n == 0) {
			tLCUWMasterSchema.setContNo(mContNo);
			tLCUWMasterSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCUWMasterSchema.setPolNo(mOldPolNo);
			tLCUWMasterSchema.setProposalContNo(mProposalContNo);
			tLCUWMasterSchema.setProposalNo(tLCPolSchema.getProposalNo());
			tLCUWMasterSchema.setUWNo(1);
			tLCUWMasterSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCUWMasterSchema.setInsuredName(tLCPolSchema.getInsuredName());
			tLCUWMasterSchema.setAppntNo(tLCPolSchema.getAppntNo());
			tLCUWMasterSchema.setAppntName(tLCPolSchema.getAppntName());
			tLCUWMasterSchema.setAgentCode(tLCPolSchema.getAgentCode());
			tLCUWMasterSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
			// tLCUWMasterSchema.setUWGrade(mUWGrade); //核保级别
			// tLCUWMasterSchema.setAppGrade(mUWGrade); //申报级别
			tLCUWMasterSchema.setPostponeDay("");
			tLCUWMasterSchema.setPostponeDate("");
			tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			// tLCUWMasterSchema.setState(mPolPassFlag);
			// tLCUWMasterSchema.setPassFlag(mPolPassFlag);
			tLCUWMasterSchema.setHealthFlag("0");
			tLCUWMasterSchema.setSpecFlag("0");
			tLCUWMasterSchema.setQuesFlag("0");
			tLCUWMasterSchema.setReportFlag("0");
			tLCUWMasterSchema.setChangePolFlag("0");
			tLCUWMasterSchema.setPrintFlag("0");
			tLCUWMasterSchema.setManageCom(tLCPolSchema.getManageCom());
			tLCUWMasterSchema.setUWIdea("");
			tLCUWMasterSchema.setUpReportContent("");
			tLCUWMasterSchema.setOperator(mGlobalInput.Operator); // 操作员
			tLCUWMasterSchema.setMakeDate(CurrentDate);
			tLCUWMasterSchema.setMakeTime(CurrentTime);
			tLCUWMasterSchema.setModifyDate(CurrentDate);
			tLCUWMasterSchema.setModifyTime(CurrentTime);
		} else if (n == 1) {
			tLCUWMasterSchema = tLCUWMasterSet.get(1);

			tuwno = tLCUWMasterSchema.getUWNo();
			tuwno = tuwno + 1;

			tLCUWMasterSchema.setUWNo(tuwno);
			tLCUWMasterSchema.setProposalContNo(mProposalContNo);
			// tLCUWMasterSchema.setState(mPolPassFlag);
			// tLCUWMasterSchema.setPassFlag(mPolPassFlag);
			tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			// tLCUWMasterSchema.setUWGrade(mUWGrade); //核保级别
			// tLCUWMasterSchema.setAppGrade(mUWGrade); //申报级别
			tLCUWMasterSchema.setOperator(mGlobalInput.Operator); // 操作员
			tLCUWMasterSchema.setModifyDate(CurrentDate);
			tLCUWMasterSchema.setModifyTime(CurrentTime);
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError.buildErr(this,mOldPolNo + "个人核保总表取数据不唯一!");
			return false;
		}

		tLCUWMasterSet.clear();
		tLCUWMasterSet.add(tLCUWMasterSchema);

		// 核保轨迹表
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		String tSQL = "select * from lcuwsub where polno='"+"?polno?"+"' order by uwno desc";
		//tLCUWSubDB.setPolNo(mOldPolNo);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL);
		sqlbv3.put("polno", mOldPolNo);
		
		tLCUWSubSet = tLCUWSubDB.executeQuery(sqlbv3);
		if (tLCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
			CError.buildErr(this,mOldPolNo + "个人核保轨迹表查失败!");
			return false;
		}

		Reflections ref = new Reflections();
		ref.transFields(tLCUWSubSchema, tLCUWMasterSchema);
		int m = tLCUWSubSet.size();
		if (m > 0) {
			//tLCUWSubSchema.setUWNo(++m); // 第n次核保
			int tUWNo = tLCUWSubSet.get(1).getUWNo();
			tLCUWSubSchema.setUWNo(++tUWNo); // 第n次核保
		}

		else {
			tLCUWSubSchema.setUWNo(1); // 第1次核保
		}

		tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator());
		tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
		tLCUWSubSchema.setMakeDate(CurrentDate);
		tLCUWSubSchema.setMakeTime(CurrentTime);
		tLCUWSubSchema.setModifyDate(CurrentDate);
		tLCUWSubSchema.setModifyTime(CurrentTime);

		tLCUWSubSet.clear();
		tLCUWSubSet.add(tLCUWSubSchema);
		mLCCUWmap.put(tLCUWMasterSet, "DELETE&INSERT");
		mLCCUWmap.put(tLCUWSubSet, "INSERT");
		return true;
	}

	private boolean getMissionID() {
		LWMissionSet tLWMissionSet = new LWMissionSet();
		LBMissionSet tLBMissionSet = new LBMissionSet();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LBMissionDB tLBMissionDB = new LBMissionDB();

		LWProcessInstanceSet tLWProcessInstanceSet = new LWProcessInstanceSet();
		LWProcessInstanceDB tLWProcessInstanceDB = new LWProcessInstanceDB();
//		tLWProcessInstanceDB.setProcessID("0000000003");
		String sql1 = "select processid from lwcorresponding where busitype='1001'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(sql1);
		ExeSQL tExeSQL = new ExeSQL();
		String tProcessID = tExeSQL.getOneValue(sqlbv8);
		tLWProcessInstanceDB.setProcessID(tProcessID);

		tLWProcessInstanceDB.setStartType("0"); // 根节点
		tLWProcessInstanceSet.set(tLWProcessInstanceDB.query());
		if (tLWProcessInstanceDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLWProcessInstanceDB.mErrors);
			CError.buildErr(this,"工作流过程实例查询失败!");
			return false;
		}
		if (tLWProcessInstanceSet == null || tLWProcessInstanceSet.size() == 0) {
			CError.buildErr(this,"工作流过程实例查询失败!");
			return false;
		}

		// 循环查找该过程所有根节点寻找所有MissionID
		String tMissionID = "";
		int m = 0;
		mStrMissionID = "('0'";
		String tActivityID;
		for (int i = 1; i <= tLWProcessInstanceSet.size(); i++) {
			tActivityID = tLWProcessInstanceSet.get(i).getTransitionStart();

			LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
			LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
			tLWFieldMapDB.setActivityID(tActivityID);
			tLWFieldMapSet.set(tLWFieldMapDB.query());
			if (tLWFieldMapDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLWFieldMapDB.mErrors);
				CError.buildErr(this,"工作流任务字段映射查询失败!");
				return false;
			}

			LWFieldMapSchema tLWFieldMapSchema = new LWFieldMapSchema();
			for (int j = 1; j <= tLWFieldMapSet.size(); j++) {
				tLWFieldMapSchema.setSchema(tLWFieldMapSet.get(j));
				// 找到印刷号影射字段
				if (tLWFieldMapSchema.getSourFieldName().equals("PrtNo")) {
					// 匹配该印刷号在此节点是否有任务
					tLWMissionSet.clear();
					tLWMissionDB = new LWMissionDB();
					tLWMissionDB.setActivityID(tActivityID);
					tLWMissionDB.setV(tLWFieldMapSchema.getDestFieldName(),
							mPrtNo);
					tLWMissionSet.set(tLWMissionDB.query());
					if (tLWMissionDB.mErrors.needDealError() == true) {
						// @@错误处理
						mErrors.copyAllErrors(tLWMissionDB.mErrors);
						CError.buildErr(this,"保单工作流任务ID查询失败!" + "印刷号："
								+ mPrtNo);
						return false;
					}
//					if (tLWMissionSet != null && tLWMissionSet.size() == 1) {
//						mMissionID = tLWMissionSet.get(1).getMissionID();
//						logger.debug("==MissionID==" + mMissionID);
//						return true;
//					}
					for(int n = 1; n<=tLWMissionSet.size(); n++,m++)
					{
						tMissionID = tLWMissionSet.get(n).getMissionID();
						logger.debug("==MissionID==" + tMissionID);
						mStrMissionID = mStrMissionID + ",'"+tMissionID + "'";
					}
					// 查找备份表
//					if (tLWMissionSet == null || tLWMissionSet.size() == 0) {
//						tLBMissionSet.clear();
//						tLBMissionDB = new LBMissionDB();
//						tLBMissionDB.setActivityID(tActivityID);
//						tLBMissionDB.setV(tLWFieldMapSchema.getDestFieldName(),
//								mPrtNo);
//						tLBMissionSet.set(tLBMissionDB.query());
//						if (tLBMissionDB.mErrors.needDealError() == true) {
//							// @@错误处理
//							mErrors.copyAllErrors(tLBMissionDB.mErrors);
//							CError.buildErr(this, "保单工作流任务ID查询失败!" + "印刷号："
//									+ mPrtNo);
//							return false;
//						}
//						if (tLBMissionSet != null && tLBMissionSet.size() == 1) {
//							mMissionID = tLBMissionSet.get(1).getMissionID();
//							logger.debug("==MissionID==" + mMissionID);
//							return true;
//						}
//					}
					tLBMissionSet.clear();
					tLBMissionDB = new LBMissionDB();
					tLBMissionDB.setActivityID(tActivityID);
					tLBMissionDB.setV(tLWFieldMapSchema.getDestFieldName(),
							mPrtNo);
					tLBMissionSet.set(tLBMissionDB.query());
					if (tLBMissionDB.mErrors.needDealError() == true) {
						// @@错误处理
						mErrors.copyAllErrors(tLBMissionDB.mErrors);
						CError.buildErr(this, "保单工作流任务ID查询失败!" + "印刷号："
								+ mPrtNo);
						return false;
					}
					for(int n = 1; n<=tLBMissionSet.size(); n++,m++)
					{
						tMissionID = tLBMissionSet.get(n).getMissionID();
						logger.debug("==MissionID==" + tMissionID);
						mStrMissionID = mStrMissionID + ",'"+tMissionID + "'";
					}
				}
			}
		}
		mStrMissionID = mStrMissionID + ")";
//		if (mMissionID == null || "".equals(mMissionID)) {
//			CError.buildErr(this, "保单工作流任务ID查询失败!" + "印刷号：" + mPrtNo);
//			return false;
//		}
		if (m == 0) {
			CError.buildErr(this, "保单工作流任务ID查询失败!" + "印刷号：" + mPrtNo);
			return false;
		}
		// 如果有任务正处于申请处理中，不能撤单
		tLWMissionSet.clear();
		tLWMissionDB = new LWMissionDB();
//		tLWMissionDB.setMissionID(mMissionID);
		// modify by lzf 2013-04-19
//		String sql = "select * from lwmission where missionid in " + mStrMissionID
//					+ " and (missionprop18 is null or missionprop18<>'6')";
		String sql = "select a.* from lwmission a,lccuwmaster b where a.missionid in " + mStrMissionID
				+" and (a.missionprop1 = b.contno or a.missionprop2=b.contno) and (b.uwstate is null or b.uwstate <>'6' )";
		//end by lzf
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(sql);
		//sqlbv4.put("missionid", mStrMissionID);
		tLWMissionSet.set(tLWMissionDB.executeQuery(sqlbv4));
//		if (tLWMissionDB.mErrors.needDealError() == true) {
		if (tLWMissionSet == null || tLWMissionSet.size()<1) {
			// @@错误处理
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError.buildErr(this, "保单工作流任务查询失败!" + "印刷号：" + mPrtNo);
			return false;
		}
		// 当前处理申请人
		String tDefaultOperator;
		for (int i = 1; i <= tLWMissionSet.size(); i++) {
			tDefaultOperator = tLWMissionSet.get(i).getDefaultOperator();
			if (tDefaultOperator != null && !"".equals(tDefaultOperator)) {
				CError.buildErr(this, "保单工作流任务正处于申请处理中，不能撤单!" + "印刷号：" + mPrtNo
						+ "申请人：" + tDefaultOperator);
				return false;
			}
		}
		return true;
	}

	private boolean checkBackFromBank() {
		String strSql = " select * from ljspay where BankOnTheWayFlag = '1' and "
				+ " exists (select 'x' from lccont where prtno = otherno "
				+ " and prtno = '" + "?prtno?" + "')";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(strSql);
		sqlbv5.put("prtno", mPrtNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv5);
		if (tSSRS.getMaxRow() > 0) {
			CError.buildErr(this, "有银行在途数据，不允许撤单!");
			logger.debug("有银行在途数据，不允许拒保、延期或撤单!");
			
		      //日志监控,过程监控        
	    	PubFun.logTrack(mGlobalInput,mPrtNo,"投保单"+mPrtNo+"交费银行在途，不允许撤单");
	    	
			logger.debug("****************记录此原因的错误日志****************");
			LCIndAppSignLogSchema tLCIndAppSignLogSchema = new LCIndAppSignLogSchema();
			MMap tMMap = new MMap();
			VData tResult = new VData();			

			String strSql1 = " select * from LCIndAppSignLog where PrtNo = '"+"?PrtNo?"+"' and "
				+ " ErrType='02'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(strSql1);
			sqlbv6.put("PrtNo", mPrtNo);
			LCIndAppSignLogSet tLCIndAppSignLogSet = new LCIndAppSignLogSet();
			LCIndAppSignLogDB tLCIndAppSignLogDB = new LCIndAppSignLogDB();			
			tLCIndAppSignLogSet = tLCIndAppSignLogDB.executeQuery(sqlbv6);
			if (tLCIndAppSignLogSet!=null && tLCIndAppSignLogSet.size() > 0) {
				tLCIndAppSignLogSchema.setSchema(tLCIndAppSignLogSet.get(1));
				int SignNo = tLCIndAppSignLogSchema.getSignNo() + 1;
				tLCIndAppSignLogSchema.setSignNo(SignNo);
				tLCIndAppSignLogSchema.setOperator(mGlobalInput.Operator);
				tLCIndAppSignLogSchema.setMakeDate(CurrentDate);
				tLCIndAppSignLogSchema.setMakeTime(CurrentTime);
				tMMap.put(tLCIndAppSignLogSchema, "UPDATE");
			}
			else 
			{
				String Serino = PubFun1.CreateMaxNo("WithDCont", 10);
				tLCIndAppSignLogSchema.setPrtNo(mPrtNo);
				tLCIndAppSignLogSchema.setSignNo(1);
				tLCIndAppSignLogSchema.setSerino(Serino);
				tLCIndAppSignLogSchema.setOperator(mGlobalInput.Operator);
				tLCIndAppSignLogSchema.setMakeDate(CurrentDate);
				tLCIndAppSignLogSchema.setMakeTime(CurrentTime);
				tLCIndAppSignLogSchema.setErrType("02");
				tLCIndAppSignLogSchema.setErrCode("YHZT");
				tLCIndAppSignLogSchema.setReason("有银行在途数据，不允许撤单!");
				tLCIndAppSignLogSchema.setState("00");	
				tMMap.put(tLCIndAppSignLogSchema, "INSERT");
			}
			
			tResult.add(tMMap);	
			PubSubmit tPubSubmit = new PubSubmit();

			if (!tPubSubmit.submitData(tResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError.buildErr(this, "数据提交失败!");
				logger.debug("*****************end 记录日志失败！***************");
				return false;
			}
			
			logger.debug("*****************end 记录日志成功！***************");
			return false;
		}
		return true;
	}
	
	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean sendPrint(TransferData tTransferData1) {
		// 准备发送的通知书数据.
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);// 合同号类型
		tLOPRTManagerSchema.setOtherNo(this.mContNo);// 合同号
		tLOPRTManagerSchema.setStandbyFlag7("TBJB");// 投保拒保类型
		String tGetNoticeNo = tTransferData1.getValueByName("GetNoticeNo") == null ? "NO"
				: (String) tTransferData1.getValueByName("GetNoticeNo");
		logger.debug("@@@@@发送单证时或者给付通知书号：" + tGetNoticeNo);
		tLOPRTManagerSchema.setStandbyFlag6(tGetNoticeNo);
		tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_WITHDRAW);
			
		logger.debug("tLOPRTManagerSchema:"
				+ tLOPRTManagerSchema.getCode());
		VData tInputData = new VData();
		tInputData.add(mGlobalInput);
		tInputData.add(mTransferData);
		tInputData.add(tLOPRTManagerSchema);
		// 加入业务逻辑处理类
		UWSendAllPrintBL tUWSendAllPrintBL = new UWSendAllPrintBL();
		if (!tUWSendAllPrintBL.submitData(tInputData, "")) {
			this.mErrors.copyAllErrors(tUWSendAllPrintBL.getErrors());
			return false;
		} else {
			MMap mMap = (MMap) tUWSendAllPrintBL.getResult().getObjectByObjectName(
					"MMap", 0);
			map.add(mMap);

		}
		return true;
	}
}
