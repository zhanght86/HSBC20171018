/*
 * @(#)PersonUnionBL.java	2005-04-22
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.tb;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWNotePadSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 新契约-工作流记事本处理类
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
 * @CreateDate：2005-04-22
 */
public class WorkFlowNotePadBL {
	private static Logger logger = Logger.getLogger(WorkFlowNotePadBL.class);
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
	private LWNotePadSchema mLWNotePadSchema = new LWNotePadSchema();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public WorkFlowNotePadBL() {
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

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {

		if (mOperate.equals("NOTEPAD|INSERT")) {
			String sActivityID = mLWNotePadSchema.getActivityID();
			logger.debug("*****sActivityID" + sActivityID);
			/*
			if (sActivityID == null || sActivityID.trim().equals("")
					|| sActivityID.trim().equals("null")) {
				String strSQL = "select * from lwmission where missionid='"
						+ mLWNotePadSchema.getMissionID()
						+ "' and submissionid ='"
						+ mLWNotePadSchema.getSubMissionID()
						+ "'"
						+ "and activityid in"
						+ "('0000001098','0000001099','0000001001','0000001002','0000001003','0000001100','0000001149','0000001150','0000002098','0000002099','0000002001','0000002002','0000002003','0000002004','0000002005','0000002006')";
				LWNotePadSet tLWNotePadSet = new LWNotePadSet();
				LWNotePadDB tLWNotePadDB = new LWNotePadDB();
				tLWNotePadSet = tLWNotePadDB.executeQuery(strSQL);
				if (tLWNotePadSet.size() > 0) {
					mLWNotePadSchema.setActivityID(tLWNotePadSet.get(1)
							.getActivityID());
				}
			}
			*/
			String tSerialNo = PubFun1.CreateMaxNo("LCNotePad", 20);
			mLWNotePadSchema.setSerialNo(tSerialNo);
			mLWNotePadSchema.setOperator(mGlobalInput.Operator);
			mLWNotePadSchema.setManageCom(mGlobalInput.ManageCom);
			mLWNotePadSchema.setMakeDate(mCurrentDate);
			mLWNotePadSchema.setMakeTime(mCurrentTime);
			mLWNotePadSchema.setModifyDate(mCurrentDate);
			mLWNotePadSchema.setModifyTime(mCurrentTime);

			String functionId = getFunctionId(mLWNotePadSchema.getActivityID());
			mLWNotePadSchema.setFunctionID(functionId);

			map.put(mLWNotePadSchema, "INSERT");
		}
		return true;
	}

	private String getFunctionId(String activityID) {
		ExeSQL exe = new ExeSQL();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select functionid from lwactivity where activityid='"
				+ "?activityID?" + "'");
		sqlbv.put("activityID", activityID);
		return exe.getOneValue(sqlbv);
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mLWNotePadSchema.setSchema((LWNotePadSchema) mInputData
				.getObjectByObjectName("LWNotePadSchema", 0));

		String sMissionID = mLWNotePadSchema.getMissionID();
		logger.debug("lengh=" + mLWNotePadSchema.getNotePadCont().length());
		if (mLWNotePadSchema.getNotePadCont().length() > 800) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入记事本内容过长!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (sMissionID == null || sMissionID.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String sSubMissionID = mLWNotePadSchema.getSubMissionID();
		if (sSubMissionID == null || sSubMissionID.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// String sActivityID = mLWNotePadSchema.getActivityID();
		// logger.debug("*****sActivityID"+sActivityID);
		// if (sActivityID == null || sActivityID.trim().equals("") )
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "WorkFlowNotePadBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输数据ActivityID失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		String sOtherNo = mLWNotePadSchema.getOtherNo();
		if (sOtherNo == null || sOtherNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据OtherNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String sOtherNoType = mLWNotePadSchema.getOtherNoType();
		if (sOtherNoType == null || sOtherNoType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据OtherNoType失败!";
			this.mErrors.addOneError(tError);
			return false;
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
			mInputData.clear();
			mInputData.add(map);
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
	 * 返回处理结果
	 * 
	 * @return: VData
	 */

	public VData getResult() {
		return mResult;
	}

}
