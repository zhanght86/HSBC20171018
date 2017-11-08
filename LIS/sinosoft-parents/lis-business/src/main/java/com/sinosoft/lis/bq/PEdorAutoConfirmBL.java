/*
 * @(#)PEdorValidBL.java	2005-08-23
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.bq.EdorWorkFlowUI;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-保全交费后自动确认处理类
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
 * @CreateDate：2005-08-23
 */
public class PEdorAutoConfirmBL {
private static Logger logger = Logger.getLogger(PEdorAutoConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorAutoConfirmBL(GlobalInput tGlobalInput) {
		mGlobalInput = tGlobalInput;
	}

	/**
	 * 调用保全确认处理
	 * 
	 * @param sEdorAcceptNo
	 */
	public boolean AutoConfirm(String sEdorAcceptNo) {
		/* 个人保全、团体保全标志 false为个人保全，true为团体保全 */
		boolean PGrpFlag = false;

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(sEdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			CError.buildErr(this, "查询保全申请总表失败!");
			return false;
		}
		if ("2".equals(tLPEdorAppDB.getOtherNoType())
				|| "4".equals(tLPEdorAppDB.getOtherNoType())) {
			PGrpFlag = true;
		}

		// 根据保全受理号查询保全确认工作流任务
		String strSQL = " select missionid, submissionid, ActivityID from lwmission "
				+ " where activityid in ('0000000009','0000008009') "
				+ " and trim(missionprop1) = '?sEdorAcceptNo?' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("sEdorAcceptNo", sEdorAcceptNo);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全确认工作流任务查询失败!");
			return false;
		}
		if (tSSRS == null || tSSRS.getMaxRow() < 1) {
			CError.buildErr(this, "未查到保全确认相关任务!");
			return false;
		}

		String sTemplatePath = "NoPath";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", sEdorAcceptNo);
		tTransferData.setNameAndValue("MissionID", tSSRS.GetText(1, 1));
		tTransferData.setNameAndValue("SubMissionID", tSSRS.GetText(1, 2));
		tTransferData.setNameAndValue("ActivityID", tSSRS.GetText(1, 3));
		tTransferData.setNameAndValue("TemplatePath", sTemplatePath);
		VData tVData = new VData();
		tVData.add(mGlobalInput);
		tVData.add(tTransferData);

		EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
		if (!tEdorWorkFlowUI.submitData(tVData, tSSRS.GetText(1, 3))) {
			logger.debug("保全确认失败!");
			mErrors.copyAllErrors(tEdorWorkFlowUI.mErrors);
			return false;
		} else {
			logger.debug("保全确认成功!");
			if (PGrpFlag) {// 团体保全生效
				GEdorValidBL tGEdorValidBL = new GEdorValidBL();
				if (!tGEdorValidBL.submitData(tVData, "")) {
					logger.debug("保全确认生效失败");
					mErrors.copyAllErrors(tGEdorValidBL.mErrors);
					return false;
				} else {
					logger.debug("保全确认生效成功");
				}
			} else {// 个人保全生效
				PEdorValidBL tPEdorValidBL = new PEdorValidBL();
				if (!tPEdorValidBL.submitData(tVData, "")) {
					logger.debug("保全确认生效失败");
					mErrors.copyAllErrors(tPEdorValidBL.mErrors);
					return false;
				} else {
					logger.debug("保全确认生效成功");
				}
			}
		}
		return true;
	}
}
