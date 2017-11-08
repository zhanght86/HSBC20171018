package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.*;
import com.sinosoft.lis.claimgrp.LLClaimPubFunBL;
import com.sinosoft.lis.db.LLAppClaimReasonDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.*                                                                                                                                                                                                             ;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 简易案件确认服务类
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

public class LLClaimSimpleAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLClaimSimpleAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

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
	private Reflections ref = null;
	private LLRegisterSchema mLLRegisterSchema=null;//立案信息
    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mClmNo = "";
	private String mAuditFlag = "";
	private String mMaxLevel = ""; // 最高权限
	private String mStandpay = "";
	private String mAdjpay = "";

	public LLClaimSimpleAfterInitService() {
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
		mAuditFlag = (String) mTransferData.getValueByName("AuditFlag");
		mInputData = cInputData;
		mOperate = cOperate;

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输公共数据Operate失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据!");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------Service dealData BEGIN----------");

		// 处理业务操作
		// LLClaimSimpleBL tLLClaimSimpleBL = new LLClaimSimpleBL();
		// if (!tLLClaimSimpleBL.submitData(mInputData,"UPDATE"))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLLClaimSimpleBL.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "LLClaimSimpleAfterInitService";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors .addOneError(tError);
		// mResult.clear();
		// mInputData = null;
		// tReturn = false;
		// }
		// else
		// {
		// mInputData = null;
		// mInputData = tLLClaimSimpleBL.getResult();
		// logger.debug("-----start Service getData from BL");
		// map = (MMap) mInputData.getObjectByObjectName("MMap", 0);
		// tReturn = true;
		// }
		//简易案件确认先进行财务应付数据处理
		TransferData sTransferData = new TransferData();
		sTransferData.setNameAndValue("RptNo", mClmNo);
		sTransferData.setNameAndValue("BudgetFlag", "0"); // 是否预付，传'0'进去是要工作流流转到审批阶段;
		VData mVData = new VData();
		mVData.add(mGlobalInput);
		mVData.add(sTransferData);
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

		
		/**
		 * ---------------------------------------------------------------------BEG
		 * 功能：根据简易案件结论为结论分别提交处理 
		 * 处理：1 简易案件结论为退回时,为审核节点增加"来自"属性,分为 
		 *           B简易案件结论为进入,审核因为赔案金额为正数，
		 *           C审批进入审核因为赔案金额为负数,
		 *           D来自简易案件 
		 *       2 简易案件结论为给付时,处理财务数据，详细见同审批
		 *       3 计算审核权限
		 * --------------------------------------------------------------------
		 */
		if (mAuditFlag.equals("1")) {
			//先进行保单挂起操作
			LLLcContHangUpBL tLLLcContHangUpBL = new LLLcContHangUpBL();
			if (!tLLLcContHangUpBL.submitData(mInputData, "")) {
				// @@错误处理
				CError.buildErr(this, "保单挂起处理失败,"+tLLLcContHangUpBL.mErrors.getFirstError());
				return false;
			} else {
				logger.debug("-----start Service getData from LLLcContHangUpBL");
				VData tempVData = new VData();
				MMap tMap = new MMap();
				tempVData = tLLLcContHangUpBL.getResult();
				tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				map.add(tMap);
			}
			// 简易案件结论为不通过,工作流流入审核,赔案状态已经为审核,无需更改
			// 为公共传输数据集合中添加工作流下一节点属性字段数据
			mTransferData.setNameAndValue("ComeWhere", "D");
			mTransferData.removeByName("RptorState");
			mTransferData.setNameAndValue("RptorState", "30");

			// No.1.1 准备权限计算所需参数
			String tSelectSql1 = "";// 理算金额
			tSelectSql1 = "select case when a.realpay is not null then a.realpay else 0 end from llclaim a where "
					+ "a.clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSelectSql1);
			sqlbv.put("clmno", mClmNo);
			ExeSQL tExeSQL1 = new ExeSQL();
			mStandpay = tExeSQL1.getOneValue(sqlbv);
			if (mStandpay == null) {
				// @@错误处理
				CError.buildErr(this, "查询理算金额失败!");
				return false;
			}

			String tSelectSql2 = "";// 调整后金额
			tSelectSql2 = "select CASE WHEN b.beadjsum IS NOT NULL THEN b.beadjsum ELSE 0 END from llregister b where "
					+ "b.rgtno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSelectSql2);
			sqlbv1.put("clmno", mClmNo);
			ExeSQL tExeSQL2 = new ExeSQL();
			mAdjpay = tExeSQL2.getOneValue(sqlbv1);
			if (mAdjpay == null) {
				// @@错误处理
				CError.buildErr(this, "查询调整后金额失败!");
				return false;
			}

			mTransferData.setNameAndValue("standpay", mStandpay);
			mTransferData.setNameAndValue("adjpay", mAdjpay);


			// No.1.3 计算审核权限
			LLClaimPopedomSetBL tLLClaimPopedomSetBL = new LLClaimPopedomSetBL();
			if (!tLLClaimPopedomSetBL.submitData(mInputData, "")) {
				// @@错误处理
				CError.buildErr(this, "计算审核权限失败!");
				return false;
			} else {
				logger.debug("-----start Service getData from LLClaimPopedomSetBL");
				VData tVData = new VData();
				MMap tMap = new MMap();
				tVData = tLLClaimPopedomSetBL.getResult();
				tMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
				TransferData tTransferData = new TransferData();
				tTransferData = (TransferData) tVData.getObjectByObjectName(
						"TransferData", 0);
				mMaxLevel = (String) tTransferData.getValueByName("Popedom");
				// 为公共传输数据集合中添加工作流下一节点属性字段数据
				mTransferData.setNameAndValue("Popedom", mMaxLevel); // 审核模块权限
				map.add(tMap);
			}

			// No.1.3 更改案件状态为普通案件
			String sql2 = " update LLRegister set RgtState = '11' where "
					+ " RgtNo = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql2);
			sqlbv2.put("clmno", mClmNo);
			map.put(sqlbv2, "UPDATE");
			//更新审核权限字段，以便后续审批不通过时使用
			String sql3 = " update llclaim set AuditPopedom = '"+mMaxLevel+"' where"
					+ " clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sql3);
			sqlbv3.put("clmno", mClmNo);
			map.put(sqlbv3, "UPDATE");
		} 
		else {
			
			/**
			 * 2008-12-16 zhangzheng 
			 
			 */

			
			/*
			// No.2.1 简易案件结论为给付,同审批处理
			LLClaimConfirmPassBL tLLClaimConfirmPassBL = new LLClaimConfirmPassBL();
			if (!tLLClaimConfirmPassBL.submitData(mInputData, "")) {
				// @@错误处理
				CError.buildErr(this, "数据提交失败!");
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
			}
			*/
			
			//2009-04-30 zhangzheng 增加对是否符合简易案件条件的校验
			// No.3： 赔案的案件的理赔类型必须是医疗类,且仅有医疗类，否则不符合条件,按正常案件处理
			// ----------------------------------------------------------------------
			
			LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
			LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet();
			tLLAppClaimReasonDB.setCaseNo(mClmNo);
			tLLAppClaimReasonSet = tLLAppClaimReasonDB.query();
			if (tLLAppClaimReasonSet == null || tLLAppClaimReasonSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this, "查询理赔类型信息失败,"+tLLAppClaimReasonDB.mErrors.getLastError());
				return false;
			}
			
			boolean tIsNo = false;
			for (int j = 1; j <= tLLAppClaimReasonSet.size(); j++) {
				String tReasonCode = tLLAppClaimReasonSet.get(j).getReasonCode()
						.substring(1, 3);
				if (!tReasonCode.equals("00")) {
					tIsNo = true;
					break;
				}
			}
			
			if (tIsNo) {
				
				CError.buildErr(this, "理赔类型不是只有医疗类,不能简易案件确认!");
				return false;
			}
			
			// ----------------------------------------------------------------------END
			// ----------------------------------------------------------------------BEG
			// No.4： 系统用“给付金额调整”字段数值与系统设置的简易案件标准匹配判断，小于等于该
			// 标准的案件，被判定为简易案件。
			// 所需变量：mClmNo
			// 更新变量：
			// ----------------------------------------------------------------------
			String tComCode = ""; // 登陆机构6位
			if (mGlobalInput.ComCode.length() == 8) {
				tComCode = mGlobalInput.ComCode.substring(0, 6);
			} else if (mGlobalInput.ComCode.length() == 6) {
				tComCode = mGlobalInput.ComCode.substring(0, 6);
			} else if (mGlobalInput.ComCode.length() == 4) {
				tComCode = mGlobalInput.ComCode.substring(0, 4);
			}

			ExeSQL tExeSQL = new ExeSQL();
			String tSelectSql2 = "";// 调整后金额
			tSelectSql2 = "select (case when b.realpay is not null then b.realpay else 0 end) from llclaim b where "
					+ "b.clmno = '" + "?mClmNo?" + "'";
			SQLwithBindVariables sqlbva = new SQLwithBindVariables();
			sqlbva.sql(tSelectSql2);
			sqlbva.put("mClmNo", mClmNo);
			mAdjpay = tExeSQL.getOneValue(sqlbva);
			if (mAdjpay == null) {
				// @@错误处理
				CError.buildErr(this, "查询调整后金额失败!");
				return false;
			}
			
			int tCount = 0;

			String tSelectSql3 = "select count(1) from LLParaClaimSimple where 1=1 "
					+ " and comcode = '" + "?comcode?" + "'"
					+ " and basemin < " + "?basemin?" + " and basemax >= " + "?basemax?";
			
			logger.debug("--查询机构的简易案件处理金额sql:"+tSelectSql3);
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSelectSql3);
			sqlbv4.put("comcode", tComCode);
			sqlbv4.put("basemin", Double.parseDouble(mAdjpay));
			sqlbv4.put("basemax", Double.parseDouble(mAdjpay));
			tCount=Integer.parseInt(tExeSQL.getOneValue(sqlbv4));
			
			if (tCount==0) {				
				CError.buildErr(this, "案件金额不符合简易案件标准,不能简易案件确认!");
				return false;
			}
			
			// ----------------------------------------------------------------------END
			// ----------------------------------------------------------------------BEG
			// No.5： 从当前立案日期起向前计算，此出险人一年内不能有超过三个结案的简易案件
			// 所需变量：mClmNo,mRgtDate
			// 更新变量：
			// ----------------------------------------------------------------------
			LLCaseDB tLLCaseDB = new LLCaseDB();
			LLCaseSet tLLCaseSet = new LLCaseSet();
			tLLCaseDB.setCaseNo(mClmNo);
			tLLCaseSet = tLLCaseDB.query();
			if (tLLCaseSet == null || tLLCaseSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this, "查询分案信息失败,"+tLLCaseDB.mErrors.getLastError());
				return false;
			}
			
			mLLRegisterSchema = mLLClaimPubFunBL.getLLRegister(mClmNo);
			
			for (int j = 1; j <= tLLCaseSet.size(); j++) {
				
				tCount=0;

	            String tYearString = mLLRegisterSchema.getRgtDate().substring(0, 4);
	            int tYearInt = Integer.parseInt(tYearString) - 1;
	            String mLastDate = String.valueOf(tYearInt)+mLLRegisterSchema.getRgtDate().substring(4)+" 00:00:00";//立案日期往前推一年

				logger.debug("立案日期:"+mLLRegisterSchema.getRgtDate()+",上一年的立案日期:"+mLastDate);
				String tSelectSql4=" select count(*) from llregister"
				        +" where exists(select 1 from llcase where llcase.rgtno=llregister.rgtno and  customerno ='"+"?customerno?"+"')"
				        +" and Rgtno!='"+"?Rgtno?"+"'"
				        +" and RgtDate >=to_date('?date1?','yyyy-mm-dd hh24:mi:ss')"
				        +" and RgtDate <=to_date(concat('?date2?',' 00:00:00'),'yyyy-mm-dd hh24:mi:ss')"
				        +" and RgtState='01'";
				
				logger.debug("--判断在立案日期追溯一年内有至少三次的理赔案件时本次案件不能立为简易案件的SQL:"+tSelectSql3);
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(tSelectSql4);
				sqlbv5.put("customerno", tLLCaseSet.get(j).getCustomerNo());
				sqlbv5.put("Rgtno", tLLCaseSet.get(j).getCaseNo());
				sqlbv5.put("date1", mLastDate);
				sqlbv5.put("date2", mLLRegisterSchema.getRgtDate());
				tCount=Integer.parseInt(tExeSQL.getOneValue(sqlbv5));
			
				if (tCount >= 3) {
					CError.buildErr(this, "从当前立案日期起向前计算一年内已经存在三个结案的简易案件,不能执行简易案件确认!");
					return false;
				}

				tSelectSql3=null;
				mLastDate=null;
				tYearString=null;
			}
			
			//由于理赔简易案件确认后则标识案件结束，所以财务应付数据需要事先进行提交
			VData sVData = new VData();
			sVData.add(map);
			PubSubmit ps = new PubSubmit();
			if(!ps.submitData(sVData))
			{
				CError.buildErr(this, "进行受益人财务数据处理失败");
				return false;
			}
			map= new MMap();
			// No.2.2 更改赔案状态为结案,置结案日期,结案标志,案件给付类型
			String sql1 = " update LLRegister set ClmState = '50',endCaseDate='"+"?CurrentDate?"+"'"+",endcaseflag='1',casePayType='5' where"
					+ " RgtNo = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(sql1);
			sqlbv6.put("clmno", mClmNo);
			sqlbv6.put("CurrentDate", CurrentDate);
			map.put(sqlbv6, "UPDATE");
			
			String sql2 = " update llclaim set ClmState = '50',endCaseDate='"+"?CurrentDate?"+"'"+",endcaseflag='1',casePayType='5' where"
					+ " clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(sql2);
			sqlbv7.put("clmno", mClmNo);
			sqlbv7.put("CurrentDate", CurrentDate);
			map.put(sqlbv7, "UPDATE");
			
			String sql3 = " update llclaimpolicy set ClmState = '50',endCaseDate='"+"?CurrentDate?"+"',casePayType='5' where"
				    + " clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(sql3);
			sqlbv8.put("clmno", mClmNo);
			sqlbv8.put("CurrentDate", CurrentDate);
			map.put(sqlbv8, "UPDATE");

			// No.2.3
			// 向案件核赔表中插入一条记录-------------------------------------周磊--2005-9-2
			// 15:41
			LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
			tLLClaimUWMainSchema.setClmNo(mClmNo);
			tLLClaimUWMainSchema.setRgtNo(mClmNo);
			tLLClaimUWMainSchema.setCaseNo(mClmNo);
			tLLClaimUWMainSchema.setAuditConclusion("0"); // 审核结论
			tLLClaimUWMainSchema.setAuditPer(mGlobalInput.Operator);
			tLLClaimUWMainSchema.setAuditDate(CurrentDate);
			tLLClaimUWMainSchema.setExamConclusion("0"); // 审批结论
			tLLClaimUWMainSchema.setExamDate(CurrentDate);
			tLLClaimUWMainSchema.setExamPer(mGlobalInput.Operator);
			tLLClaimUWMainSchema.setOperator(mGlobalInput.Operator);
			tLLClaimUWMainSchema.setMngCom(mGlobalInput.ManageCom);
			tLLClaimUWMainSchema.setExamCom(mGlobalInput.ManageCom);
			tLLClaimUWMainSchema.setMakeDate(CurrentDate);
			tLLClaimUWMainSchema.setMakeTime(CurrentTime);
			tLLClaimUWMainSchema.setModifyDate(CurrentDate);
			tLLClaimUWMainSchema.setModifyTime(CurrentTime);
			map.put(tLLClaimUWMainSchema, "DELETE&INSERT");
			
			//同步轨迹
			LLClaimUWMDetailSchema tLLClaimUWMDetailSchema =new LLClaimUWMDetailSchema();
	            
	        //查询LLClaimUWMDetail核赔次数
			String strSQL = "";
			strSQL = " select Max(ClmUWNo/1) from LLClaimUWMDetail where "
			       + " ClmNO='" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(strSQL);
			sqlbv9.put("clmno", tLLClaimUWMainSchema.getClmNo());
			ExeSQL exesql = new ExeSQL();
			String tMaxNo = exesql.getOneValue(sqlbv9);
			if (tMaxNo.length() == 0) {
					tMaxNo = "1";
			} 
			else {
					int tInt = Integer.parseInt(tMaxNo);
					tInt = tInt + 1;
					tMaxNo = String.valueOf(tInt);
			}
				
        	ref = new Reflections();
			ref.transFields(tLLClaimUWMDetailSchema, tLLClaimUWMainSchema);
			tLLClaimUWMDetailSchema.setClmUWNo(tMaxNo);	
			// 打包提交数据
			map.put(tLLClaimUWMDetailSchema, "INSERT");
			

			// 解除保单挂起
//			LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
//			if (!tLLLcContReleaseBL.submitData(mInputData, "")) {
//				// @@错误处理
//				CError.buildErr(this, "解除保单挂起失败,"+tLLLcContReleaseBL.mErrors.getLastError());
//				return false;
//			} else {
//				VData tempVData = new VData();
//				tempVData = tLLLcContReleaseBL.getResult();
//				MMap tMap = new MMap();
//				tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
//				map.add(tMap);
//			}

			// 为公共传输数据集合中添加工作流下一节点属性字段数据,结案要回到立案机构
//			String sql4 = " select MngCom from LLRegister where" + " RgtNo = '"
//					+ mClmNo + "'";
//			String tMngCom = tExeSQL.getOneValue(sql4);
//			if (tMngCom != null) {
//				mTransferData.removeByName("MngCom");
//				mTransferData.setNameAndValue("MngCom", tMngCom);
//			}
			//2010-4-09 理赔调整流程，案件审批通过则案件结束，相应的延滞利息、财务需要进行处理，进行保单解挂
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
		}
		// ----------------------------------------------------------------------END

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

}
