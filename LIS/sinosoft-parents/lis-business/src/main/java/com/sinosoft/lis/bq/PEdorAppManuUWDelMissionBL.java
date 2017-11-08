/*
 * @(#)PersonUnionBL.java	2005-06-03
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全人工核保-人工核保确认后删除人工核保申请节点处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-06-03
 */
public class PEdorAppManuUWDelMissionBL {
private static Logger logger = Logger.getLogger(PEdorAppManuUWDelMissionBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	private TransferData mTransferData = new TransferData();

	private String mUWFlag = ""; // 核保通过标志
	private String mMissionID;
	private String mEdorAcceptNo;
	private String testAflag = "";//是否执行本类判断条件
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public static String mEDORAPP_UWPASS = "9"; // 保全申请人工核保通过 ？暂不确定编码？
	public static String mEDORAPP_UWSTOP = "1"; // 保全申请人工核不保通过？暂不确定编码？
	public static String mEDORAPP_UWSTOP1 = "3"; // 保全申请人工核不保通过？拒保

	public PEdorAppManuUWDelMissionBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");
		if(testAflag=="2"){
			// 数据操作业务处理
			if (!dealData()) {
				return false;
			}

			logger.debug("after dealData...");

			// 准备提交后台的数据
			if (!prepareOutputData()) {
				return false;
			}

			logger.debug("after prepareOutputData...");

		}
		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.query();
		if (tLPEdorAppDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全申请信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPEdorAppSet == null || tLPEdorAppSet.size() != 1) {
			CError tError = new CError();
			tError.errorMessage = "保全申请信息查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		String sOtherNoType = tLPEdorAppSet.get(1).getOtherNoType();

		String sActivityIds = "'0000000005', '0000000019', '0000000022', '0000001101', "
				+ "'0000001104', '0000001270', '0000000024', '0000001106', "
				+ "'0000001130', '0000001280', '0000000027', '0000000030', "
				+ "'0000001111', '0000001121', '0000001108', '0000001300','0000002101','0000002301', "
				+ "'0000001290', '0000001113', " + "'0000008005','0000000100'"; // 支持团体节点删除

		if ((mUWFlag.equals(mEDORAPP_UWSTOP) || mUWFlag.equals(mEDORAPP_UWSTOP1))
				&& (sOtherNoType.equals("3") || sOtherNoType.equals("4"))) // 核保终止，删除撤销节点
		{
			//sActivityIds += ", '0000000008'"; // 个险撤销节点
			//sActivityIds += ", '0000008008'"; // 团体撤销节点
		}

		String selMission = " select * from lwmission where activityid in "
				+ " ( " + "?sActivityIds?" + ") and missionid = '" + "?mMissionID?"
				+ "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(selMission);
		sqlbv.put("sActivityIds", sActivityIds);
		sqlbv.put("mMissionID", mMissionID);
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet delLWMissionSet = tLWMissionDB.executeQuery(sqlbv);
		if (tLWMissionDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询工作流保全人工核保任务节点失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (delLWMissionSet == null || delLWMissionSet.size() < 1) {
			return true;
		}
		LBMissionSet insertLBMissionSet = new LBMissionSet();
		LBMissionSchema tLBMissionSchema;
		for (int i = 1; i <= delLWMissionSet.size(); i++) {
			LWMissionSchema tLWMissionSchema = delLWMissionSet.get(i);
			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			tLBMissionSchema = new LBMissionSchema();
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLBMissionSchema, tLWMissionSchema);

			tLBMissionSchema.setSerialNo(tSerielNo);
			tLBMissionSchema.setActivityStatus("4"); // 节点任务执行强制移除
			if (tLBMissionSchema.getActivityID().equals("0000000005")
					|| tLBMissionSchema.getActivityID().equals("0000008005")) {
				tLBMissionSchema.setActivityStatus("3"); // 人工核保节点正常执行完成
			}
			tLBMissionSchema.setLastOperator(mGlobalInput.Operator);
			tLBMissionSchema.setOutDate(mCurrentDate);
			tLBMissionSchema.setOutTime(mCurrentTime);
			insertLBMissionSet.add(tLBMissionSchema);
		}

		map.put(delLWMissionSet, "DELETE");

		map.put(insertLBMissionSet, "INSERT");
		if(!sOtherNoType.equals("2")&&!sOtherNoType.equals("4")){
			//add by jiaqiangli 2009-07-15 保全人工核保下结论时，只有不发核保通知书的才进行进行财务金额汇总
			//ConfirmFlag = '1' 发核保通知书 不能汇总，到二核结果确认里头汇总
			//ConfirmFlag <> '1' 下核保结论，汇总
			String tConfirmFlag = (String) mTransferData.getValueByName("ConfirmFlag"); // 核保结论是否下发核保通知书标识

			logger.debug("tConfirmFlag in PEdorAppManuUWDelMissionBL[" + tConfirmFlag + "]");
			
			//增加判断 同时要求是同意的public static String mEDORAPP_UWPASS = "9";才汇总财务金额。
			if (!"1".equals(tConfirmFlag) && mUWFlag.equals(mEDORAPP_UWPASS)) {

				if (!sumLJSGetEndorse()) {
					return false;
				}
				
			}
			//add by jiaqiangli 2009-07-15 保全人工核保下结论时，只有不发核保通知书的才进行进行财务金额汇总
		}
		
		return true;
	}
	
	private boolean sumLJSGetEndorse() {

		TransferData tTransferData = new TransferData();

		VData tVData = new VData();
		tVData.add(mLPEdorAppSchema);
		tVData.add(mGlobalInput);
		LJSGetEndorseTotalBL tLJSGetEndorseTotalBL = new LJSGetEndorseTotalBL();

		if (!tLJSGetEndorseTotalBL.submitData(tVData, "")) {
			mErrors.copyAllErrors(tLJSGetEndorseTotalBL.mErrors);
			mErrors.addOneError(new CError("生成财务应收、应付信息失败!"));
			return false;
		}
		if (tLJSGetEndorseTotalBL.getResult() == null) {
			mErrors.copyAllErrors(tLJSGetEndorseTotalBL.mErrors);
			mErrors.addOneError(new CError("获得财务应收、应付信息失败!"));
			return false;
		}

		VData tResult = tLJSGetEndorseTotalBL.getResult();
		MMap tMMap = (MMap) (tResult.getObjectByObjectName("MMap", 0));
		map.add(tMMap);
		tTransferData = (TransferData) tResult.getObjectByObjectName(
				"TransferData", 0);
		// 此时无需传递付费标志
		// String sNeedPayFlag =
		// (String) tTransferData.getValueByName("NeedPayFlag");
		// mTransferData.setNameAndValue("NeedPayFlag", sNeedPayFlag);

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		testAflag = (String)mTransferData.getValueByName("testAflag");
		if(testAflag=="2"){
			mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
			mUWFlag = (String) mTransferData.getValueByName("UWFlag");
			
			if (getLPEdorApp() == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 查询保全受理信息
	 * 
	 * @return boolean
	 */
	private boolean getLPEdorApp() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppDB.getSchema();
		return true;
	}
	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

}
