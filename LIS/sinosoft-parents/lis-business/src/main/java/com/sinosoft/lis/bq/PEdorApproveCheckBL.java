/*
 * @(#)PEdorApproveCheckBL.java	2005-11-18
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorCheckDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorCheckSchema;
import com.sinosoft.lis.vschema.LPEdorCheckSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全复核抽检业务逻辑
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class PEdorApproveCheckBL {
private static Logger logger = Logger.getLogger(PEdorApproveCheckBL.class);
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
	private TransferData mTransferData = new TransferData();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	/** 传输数据 */
	private String mApproveFlag;
	private String mApproveContent;
	private String mEdorAcceptNo;
	private String mMissionID;
	private String mSubMissionID;

	private int mCheckTimes;

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorApproveCheckBL() {
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

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		// 获得保全受理号
		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		if (mEdorAcceptNo == null || mEdorAcceptNo.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中EdorAcceptNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得复核结论
		mApproveFlag = (String) mTransferData.getValueByName("ApproveFlag");
		if (mApproveFlag == null || mApproveFlag.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ApproveFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得复核意见
		mApproveContent = (String) mTransferData
				.getValueByName("ApproveContent");

		// 获得任务号
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null || mMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得子任务号
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null || mSubMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		// ******************************************************************
		// 由于需求变动，保全抽检需要在保全确认生效后做。
		// 虽然保全复核处不需要抽检，但是暂时保留该功能，只是将抽捡比例设为0
		// ******************************************************************

		// if (!getLPEdorApp())
		// {
		// return false;
		// }
		//
		// //执行抽检规则
		// String sCheckFlag = getCheckFlag();
		// if (sCheckFlag == null)
		// {
		// return false;
		// }
		String sCheckFlag = "N";
		if (sCheckFlag.equals("Y")) // 需要抽检
		{

			// 生成保全抽检轨迹表记录
			LPEdorCheckSchema insertLPEdorCheckSchema = new LPEdorCheckSchema();
			insertLPEdorCheckSchema.setEdorAcceptNo(mEdorAcceptNo);
			insertLPEdorCheckSchema.setCheckTimes(mCheckTimes);
			insertLPEdorCheckSchema.setApproveOperator(mGlobalInput.Operator);
			insertLPEdorCheckSchema.setApproveFlag(mApproveFlag);
			insertLPEdorCheckSchema.setApproveContent(mApproveContent);
			insertLPEdorCheckSchema.setApproveDate(mCurrentDate);
			insertLPEdorCheckSchema.setApproveTime(mCurrentTime);
			insertLPEdorCheckSchema.setCheckFlag("0"); // 抽检中
			insertLPEdorCheckSchema.setOperator(mGlobalInput.Operator);
			insertLPEdorCheckSchema.setMakeDate(mCurrentDate);
			insertLPEdorCheckSchema.setMakeTime(mCurrentTime);
			insertLPEdorCheckSchema.setModifyDate(mCurrentDate);
			insertLPEdorCheckSchema.setModifyTime(mCurrentTime);

			map.put(insertLPEdorCheckSchema, "INSERT");

		}

		mTransferData.setNameAndValue("CheckFlag", sCheckFlag); // 是否抽检标志

		logger.debug("--- sNeedCheckFlag at PEdorApproveCheckBL ---"
				+ sCheckFlag);

		return true;
	}

	/**
	 * 查询保全受理信息
	 * 
	 * @return: boolean
	 */
	private boolean getLPEdorApp() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理信息查询失败!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());
		return true;
	}

	/**
	 * 执行抽检规则
	 * 
	 * @return String
	 */
	private String getCheckFlag() {
		if (mApproveFlag.equals("2")) {
			return "N"; // 复核修改不需要抽检
		}

		String sql = " select * from lpedorcheck where edoracceptno = '?mEdorAcceptNo?' order by checktimes desc ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
		LPEdorCheckDB tLPEdorCheckDB = new LPEdorCheckDB();
		LPEdorCheckSet tLPEdorCheckSet = tLPEdorCheckDB.executeQuery(sqlbv);
		if (tLPEdorCheckDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全抽检记录查询失败!");
			return null;
		}
		if (tLPEdorCheckSet != null && tLPEdorCheckSet.size() > 0) {
			mCheckTimes = tLPEdorCheckSet.get(1).getCheckTimes() + 1;
			if (tLPEdorCheckSet.get(1).getCheckFlag().equals("0")) // 抽检中
			{
				CError.buildErr(this, "保全处于抽检中,请等待抽检结果!");
				return null;
			} else if (tLPEdorCheckSet.get(1).getCheckFlag().equals("1")) // 上次抽检通过
			{
				// 本次复核结论不允许再做修改
				if (!tLPEdorCheckSet.get(1).getApproveFlag().equals(
						mApproveFlag)) {
					CError.buildErr(this, "保全抽检通过，不允许修改复核结论");
					return null;
				} else {
					return "N"; // 本次不需要再抽检
				}
			} else if (tLPEdorCheckSet.get(1).getCheckFlag().equals("2")) // 上次抽检不通过
			{
				return "Y"; // 本次必须再抽检
			}
		} else {
			mCheckTimes = 1; // 第一次抽检
		}

		int iLength = mEdorAcceptNo.length();
		String sRandom = mEdorAcceptNo.substring(iLength - 1, iLength);
		int iRandom = 0;
		try {
			iRandom = Integer.parseInt(sRandom);
		} catch (Exception ex) {
			return "N";
		}
		String sCheckFlag = "";
		if ((iRandom % 2) == 0) {
			sCheckFlag = "Y";
		} else {
			sCheckFlag = "N";
		}
		return sCheckFlag;
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
			mResult.add(mTransferData);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorApproveBL";
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

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", "6120050906000054");
		tTransferData.setNameAndValue("ApproveFlag", "1");
		tTransferData.setNameAndValue("ApproveContent", "zhangtao test");
		tTransferData.setNameAndValue("MissionID", "00000000000000035840");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		PEdorApproveCheckBL tPEdorApproveCheckBL = new PEdorApproveCheckBL();

		if (!tPEdorApproveCheckBL.submitData(tVData, "")) {
			// logger.debug(tPEdorApproveBL.mErrors.getError(0).errorMessage);
		}

	}

}
