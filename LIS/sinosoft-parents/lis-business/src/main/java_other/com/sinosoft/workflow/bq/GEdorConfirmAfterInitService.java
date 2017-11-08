/*
 * @(#)PEdorAutoUWAfterInitService.java 2005-05-08
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 *
 * <p>Title: Web业务系统</p>
 * <p>Description: 团体保全确认生效服务类</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Sinosoft</p>
 * @author Lizhuo
 * @version 1.0
 */

package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.GEdorValidBL;
import com.sinosoft.lis.bq.PEdorConfirmBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class GEdorConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GEdorConfirmAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public GEdorConfirmAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("======GEdorConfirmAfterInitService======");
		
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		
		String tEdorAcceptNo = (String)mTransferData.getValueByName("EdorAcceptNo");
		String sql = "select a.managecom from lcgrpcont a ,lpedorapp b where a.grpcontno=b.otherno and b.edoracceptno='"
			+ "?tEdorAcceptNo?" +"' ";
		ExeSQL tExeSQL = new ExeSQL();
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("tEdorAcceptNo",tEdorAcceptNo);
		String managecom = tExeSQL.getOneValue(sqlbv1);
		if(managecom.equals(""))
		{
			this.mErrors.addOneError("管理机构获取失败！");
			return false;
		}
		String strLimit = PubFun.getNoLimit(managecom);		
		String strEdorConfNo = PubFun1.CreateMaxNo("EDORGRPNO",
				strLimit);
		mTransferData.setNameAndValue("EdorConfNo", strEdorConfNo);
		
		PEdorConfirmBL tPEdorConfirmBL = new PEdorConfirmBL();

		if (!tPEdorConfirmBL.submitData(cInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorConfirmBL.mErrors);
			return false;
		}
		mResult = tPEdorConfirmBL.getResult();
		MMap tMMap  = (MMap)tPEdorConfirmBL.getResult().getObjectByObjectName("MMap", 0);
		LJAPaySet tLJAPaySet = tPEdorConfirmBL.getLJAPaySet();
		cInputData.add(tLJAPaySet);
		GEdorValidBL tGEdorValidBL = new GEdorValidBL();
		if(!tGEdorValidBL.submitData(cInputData, ""))
		{
			this.mErrors.copyAllErrors(tGEdorValidBL.mErrors);
			return false;
		}
		tMMap.add((MMap)tGEdorValidBL.getResult().getObjectByObjectName("MMap", 0));

		// <!-- XinYQ added on 2006-01-09 : 准备抽检工作流必要数据 : BGN -->
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
//		String sMissionID = (String) mTransferData.getValueByName("MissionID");
//		String sSubMissionID = (String) mTransferData
//		.getValueByName("SubMissionID");
//		String sEdorAcceptNo = (String) mTransferData
//		.getValueByName("EdorAcceptNo");
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		LWMissionSet tLWMissionSet = new LWMissionSet();
//		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
//		tLWMissionDB.setMissionID(sMissionID);
//		tLWMissionDB.setSubMissionID(sSubMissionID);
//		if (sEdorAcceptNo != null && !sEdorAcceptNo.trim().equals(""))
//		tLWMissionDB.setMissionProp1(sEdorAcceptNo);
//		tLWMissionDB.setActivityID("0000008009");
//		try {
//		tLWMissionSet = tLWMissionDB.query();
//		tLWMissionSchema = tLWMissionSet.get(1);
//		} catch (Exception ex) {
//		System.out
//		.println("\t@> PEdorConfirmAfterInitService.submitData() : 执行 SQL 查询出错");
//		System.out
//		.println("\t                                             : 错误原因 : "
//		+ tLWMissionDB.mErrors.getFirstError());
//		CError.buildErr(this, "查询保全确认信息失败！");
//		return false;
//		}
//		// 字段映射
//		// select * from LWFieldMap where ActivityID = '0000000080'
//		// 业务处理
//		// mTransferData.setNameAndValue("EdorAcceptNo", sEdorAcceptNo); //已存在
//		// select * from LWFieldMap where ActivityID = '0000000009'
//		if (tLWMissionSchema.getMissionProp2() != null
//		&& !tLWMissionSchema.getMissionProp2().trim().equals(""))
//		mTransferData.setNameAndValue("OtherNo", tLWMissionSchema
//		.getMissionProp2());
//		if (tLWMissionSchema.getMissionProp3() != null
//		&& !tLWMissionSchema.getMissionProp3().trim().equals(""))
//		mTransferData.setNameAndValue("OtherNoType", tLWMissionSchema
//		.getMissionProp3());
//		if (tLWMissionSchema.getMissionProp4() != null
//		&& !tLWMissionSchema.getMissionProp4().trim().equals(""))
//		mTransferData.setNameAndValue("EdorAppName", tLWMissionSchema
//		.getMissionProp4());
//		if (tLWMissionSchema.getMissionProp5() != null
//		&& !tLWMissionSchema.getMissionProp5().trim().equals(""))
//		mTransferData.setNameAndValue("AppType", tLWMissionSchema
//		.getMissionProp5());
//		if (tLWMissionSchema.getMissionProp7() != null
//		&& !tLWMissionSchema.getMissionProp7().trim().equals(""))
//		mTransferData.setNameAndValue("ManageCom", tLWMissionSchema
//		.getMissionProp7());
//		if (tLWMissionSchema.getMissionProp11() != null
//		&& !tLWMissionSchema.getMissionProp11().trim().equals(""))
//		mTransferData.setNameAndValue("AppntName", tLWMissionSchema
//		.getMissionProp11());
//		if (tLWMissionSchema.getMissionProp12() != null
//		&& !tLWMissionSchema.getMissionProp12().trim().equals(""))
//		mTransferData.setNameAndValue("PayToDate", tLWMissionSchema
//		.getMissionProp12());
//		// 下面三个在生效的时候再赋值
//		// mTransferData.setNameAndValue("EdorType", "");
//		// mTransferData.setNameAndValue("GetMoney", "");
//		// mTransferData.setNameAndValue("EdorValiDate", "");
//		// 垃圾处理
//		tLWMissionDB = null;
//		tLWMissionSet = null;
//		tLWMissionSchema = null;
//		// <!-- XinYQ added on 2006-01-09 : 准备抽检工作流必要数据 : END -->

//		// <!-- XinYQ added on 2006-02-21 : 新契约差错率统计的数据 : BGN -->
//		String QuerySQL = "select ProposalContNo " + "from LCCont "
//		+ "where ContNo in " + "(select ContNo " + "from LPEdorItem "
//		+ "where EdorAcceptNo = '" + sEdorAcceptNo + "' " + ") ";
//		ExeSQL tExeSQL = new ExeSQL();
//		SSRS tSSRS = new SSRS();
//		try {
//		tSSRS = tExeSQL.execSQL(QuerySQL);
//		} catch (Exception ex) {
//		CError.buildErr(this, "差错率统计查询投保单号码失败！");
//		// return false;
//		}
//		if (tSSRS.MaxRow > 0) {
//		try {
//		GlobalInput tGlobalInput = new GlobalInput();
//		tGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
//		"GlobalInput", 0);
//		TransferData tTransferData = new TransferData();
//		tTransferData.setNameAndValue("MissionID", sMissionID);
//		tTransferData.setNameAndValue("SubMissionID", sSubMissionID);
//		tTransferData.setNameAndValue("ActivityID", "0000002001"); // 新契约要求写死
//		LCIssuePolSchema tLCIssuePolSchema;
//		VData tVData;
//		ErrorRateReportBL tErrorRateReportBL;
//		for (int i = 1; i <= tSSRS.MaxRow; i++) {
//		tLCIssuePolSchema = new LCIssuePolSchema();
//		tLCIssuePolSchema.setProposalContNo(tSSRS.GetText(i, 1));
//		tLCIssuePolSchema.setIssueCont("内部转办");
//		tLCIssuePolSchema.setIssueType("01"); // 随便存的
//		tLCIssuePolSchema.setSerialNo("001"); // 随便存的
//		tVData = new VData();
//		tVData.add(tGlobalInput);
//		tVData.add(tTransferData);
//		tVData.add(tLCIssuePolSchema);
//		tErrorRateReportBL = new ErrorRateReportBL();
//		if (!tErrorRateReportBL.submitData(tVData, "submit")) // ErrorRateReportBL
//		// 写死了
//		// Operation
//		{
//		mErrors.copyAllErrors(tErrorRateReportBL.mErrors);
//		CError.buildErr(this, "工作流确认时执行新契约差错率统计失败！");
//		break;
//		}
//		}
//		tGlobalInput = null;
//		tTransferData = null;
//		tLCIssuePolSchema = null;
//		tVData = null;
//		tErrorRateReportBL = null;
//		} catch (Exception ex) {
//		}
//		}
//		tSSRS = null;
//		tExeSQL = null;
		// <!-- XinYQ added on 2006-02-21 : 新契约差错率统计的数据 : BGN -->

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
