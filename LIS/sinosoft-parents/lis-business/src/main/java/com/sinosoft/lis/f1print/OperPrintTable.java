package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowUI;
import com.sinosoft.workflow.tbgrp.GrpTbWorkFlowUI;
//import com.sinosoft.workflow.ask.AskWorkFlowUI;

public class OperPrintTable {
private static Logger logger = Logger.getLogger(OperPrintTable.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public OperPrintTable() {
	}

	/**
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(TransferData cTransferData) {
		// TransferData tTransferData = (TransferData)
		// cInputData.getObjectByObjectName("TransferData", 0);
		// GlobalInput tGI = new GlobalInput();
		GlobalInput tGI = (GlobalInput) cTransferData.getValueByName("GI");

		// 输出参数
		// String FlagStr = "";
		String Content = null;
		String tContNo = "";
		String tMissionID = "";
		String tSubMissionID = "";

		String PrtSeq = (String) cTransferData.getValueByName("PrtSeq");
		String tCode = (String) cTransferData.getValueByName("Code");
		String tPrtNo = (String) cTransferData.getValueByName("PrtNo");
		String tGrpContNo = (String) cTransferData.getValueByName("GrpContNo");
		String tOperate = null;
		logger.debug("工作流：code=" + tCode);
		if (tCode.equals("03") || tCode.equals("BQ90")) {
			tOperate = "0000001106"; // 打印体检通知书任务节点编码
		} else if (tCode.equals("04")) {
			tOperate = "0000001108"; // 打印面见通知书任务节点编码
		} else if (tCode.equals("05")) {
			tOperate = "0000001107"; // 打印核保通知书任务节点编码
		} else if (tCode.equals("14")) {
			tOperate = "0000001220"; // 打印契约内容变更通知书任务节点编码
		}

		else if (tCode.equals("17")) {
			tOperate = "0000001230"; // 打印客户合并通知书任务节点编码
		}

		else if (tCode.equals("06") || tCode.equals("81") || tCode.equals("82")
				|| tCode.equals("83") || tCode.equals("84")
				|| tCode.equals("00") || tCode.equals("89")
				|| tCode.equals("86") || tCode.equals("85")
				|| tCode.equals("87") || tCode.equals("88")
				|| tCode.equals("BQ80") || tCode.equals("BQ81")
				|| tCode.equals("BQ82") || tCode.equals("BQ85")
				|| tCode.equals("BQ84") || tCode.equals("BQ86")
				|| tCode.equals("BQ87") || tCode.equals("BQ88")
				|| tCode.equals("BQ89")) {
			tOperate = "0000001280"; 
		} else if (tCode.equals("61")) {
			tOperate = "0000006015"; // 打印询价确定通知书任务节点编码
		} else if (tCode.equals("64")) {
			tOperate = "0000006008"; // 团体询价补充材料通知书
		} else if (tCode.equals("65")) {
			tOperate = "0000006021"; // 团单询价跟踪通知书
		} else if (tCode.equals("54")) // add by yaory
		{
			tOperate = "0000002220"; // 打印团单问题件契约内容变更通知书任务节点编码
		} else if (tCode.equals("99")) // add by yaory
		{
			tOperate = "0000002230"; // 打印团单客户合并任务节点编码
		} else if (tCode.equals("76")) {
			tOperate = "0000002106"; // 打印询价确定通知书任务节点编码
		} else if (tCode.equals("75")) {
			tOperate = "0000002306";
		}
		String strSQL = "";
		ExeSQL exeSql = new ExeSQL();
		if (tCode.equals("BQ80") || tCode.equals("BQ81")
				|| tCode.equals("BQ82") || tCode.equals("BQ85")
				|| tCode.equals("BQ84") || tCode.equals("BQ86")
				|| tCode.equals("BQ87") || tCode.equals("BQ88")
				|| tCode.equals("BQ89") || tCode.equals("BQ90")) {
			strSQL = "SELECT  LWMission.MissionProp1,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp2  FROM LWMission  WHERE LWMission.ProcessID = '0000000000' and  LWMission.ActivityID = '"
					+ "?tOperate?"
					+ "'  and LWMission.MissionProp3='"
					+ "?MissionProp3?" + "'"; // ActivityID
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSQL);
			sqlbv1.put("tOperate", tOperate);
			sqlbv1.put("MissionProp3", PrtSeq.trim());
			SSRS testSSRS = exeSql.execSQL(sqlbv1);
			if (testSSRS.MaxRow == 0) {
				buildError("submitData", "OperPrintTable,LWMission中缺少相关数据!");
				return false;
			}
			tPrtNo = testSSRS.GetText(1, 1);
			tContNo = testSSRS.GetText(1, 4);
			tMissionID = testSSRS.GetText(1, 2);
			tSubMissionID = testSSRS.GetText(1, 3);
		} else {
//			strSQL = "SELECT  LWMission.MissionProp1,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp2  FROM LWMission  WHERE LWMission.ProcessID  = '0000000003' and  LWMission.ActivityID = '"
			SQLwithBindVariables sqlbva = new SQLwithBindVariables();
			sqlbva.sql("select processid from LWCORRESPONDING where busitype='1001'");
			String tProcessID = new ExeSQL().getOneValue(sqlbva);
			strSQL = "SELECT  LWMission.MissionProp1,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp2  FROM LWMission  WHERE LWMission.ProcessID  = '"+ "?tProcessID?" +"' and LWMission.ActivityID = '"
		            + "?tOperate?"
					+ "'  and LWMission.MissionProp3='"
					+ "?MissionProp3?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSQL);
			sqlbv2.put("tProcessID", tProcessID);
			sqlbv2.put("tOperate", tOperate);
			sqlbv2.put("MissionProp3", PrtSeq.trim());
			SSRS testSSRS = exeSql.execSQL(sqlbv2);
			if (testSSRS.MaxRow == 0) {
				buildError("submitData", "OperPrintTable,LWMission中缺少相关数据!");
				return false;
			}
			tPrtNo = testSSRS.GetText(1, 1);
			tContNo = testSSRS.GetText(1, 4);
			tMissionID = testSSRS.GetText(1, 2);
			tSubMissionID = testSSRS.GetText(1, 3);
		}
		logger.debug("put session value,ContNo2:" + tContNo);
		logger.debug("tMissionID:" + tMissionID);
		logger.debug("tCode:" + tCode);
		if (PrtSeq == null || tCode == null) {
			// FlagStr = "Succ";
			Content = "打印数据完毕！";
		} else {
			if (tCode.equals("03") || tCode.equals("04") || tCode.equals("05")
					|| tCode.equals("14") || tCode.equals("17")
					|| tCode.equals("85") || tCode.equals("00")
					|| tCode.equals("06") || tCode.equals("81")
					|| tCode.equals("82") || tCode.equals("83")
					|| tCode.equals("84") || tCode.equals("86")
					|| tCode.equals("87") || tCode.equals("88")
					|| tCode.equals("89") || tCode.equals("BQ80")
					|| tCode.equals("BQ81") || tCode.equals("BQ82")
					|| tCode.equals("BQ84") || tCode.equals("BQ85")
					|| tCode.equals("BQ86") || tCode.equals("BQ87")
					|| tCode.equals("BQ88") || tCode.equals("BQ89")
					|| tCode.equals("BQ90")) { // edity by yaory
				// 准备数据
				logger.debug("开始打印后台处理");
				// 准备公共传输信息
				// tOperate = new String();
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("PrtSeq", PrtSeq);
				tTransferData.setNameAndValue("Code", tCode);
				tTransferData.setNameAndValue("ContNo", tContNo);
				tTransferData.setNameAndValue("PrtNo", tPrtNo);
				tTransferData.setNameAndValue("MissionID", tMissionID);
				tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
				if (tCode == "03" || tCode == "BQ90")
					tOperate = "0000001106"; // 打印体检通知书任务节点编码
				if (tCode == "04")
					tOperate = "0000001108"; // 打印面见通知书任务节点编码
				if (tCode == "05")
					tOperate = "0000001107"; // 打印核保通知书任务节点编码
				if (tCode == "14")
					tOperate = "0000001220"; // 打印契约内容变更通知书任务节点编码
				// if(tCode == "85")
				// tOperate = "0000001023"; //打印业务员通知书任务节点编码
				if (tCode == "17")
					tOperate = "0000001230"; // 打印客户合并通知书任务节点编码
				if (tCode.equals("00") || tCode.equals("06")
						|| tCode.equals("81") || tCode.equals("82")
						|| tCode.equals("83") || tCode.equals("84")
						|| tCode.equals("00") || tCode.equals("86")
						|| tCode.equals("85") || tCode.equals("87")
						|| tCode.equals("88") || tCode.equals("89")
						|| tCode.equals("BQ80") || tCode.equals("BQ81")
						|| tCode.equals("BQ82") || tCode.equals("BQ85")
						|| tCode.equals("BQ84") || tCode.equals("BQ86")
						|| tCode.equals("BQ87") || tCode.equals("BQ88")
						|| tCode.equals("BQ89"))
					tOperate = "0000001280";
				// if(tCode.equals("87")||tCode.equals("88"))
				
				VData tVData = new VData();
				tVData.add(tTransferData);
				tVData.add(tGI);
				// 数据传输
				TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
				if (!tTbWorkFlowUI.submitData(tVData, tOperate)) { // 执行保全核保生调工作流节点0000000004
					int n = tTbWorkFlowUI.mErrors.getErrorCount();
					Content = " 更新打印数据失败，原因是: "
							+ tTbWorkFlowUI.mErrors.getError(0).errorMessage;
					logger.debug(Content);
					// FlagStr = "Fail";
					return false;
				} else {
					// FlagStr = "Succ";
					Content = "更新打印数据成功！";
				}
				// logger.debug("Print:" + FlagStr);
			} else if (tCode == "61" || tCode == "64" || tCode == "65"
					|| tCode.equals("54") || tCode.equals("99")) { // add by
																	// yaory
//				// 准备数据
//				logger.debug("开始打印后台处理");
//				// 准备公共传输信息
//				// tOperate = new String();
//				TransferData tTransferData = new TransferData();
//				tTransferData.setNameAndValue("PrtSeq", PrtSeq);
//				tTransferData.setNameAndValue("Code", tCode);
//				// tTransferData.setNameAndValue("GrpContNo", tGrpContNo);
//				tTransferData.setNameAndValue("PrtNo", tPrtNo);
//				tTransferData.setNameAndValue("MissionID", tMissionID);
//				tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
//				if (tCode == "61")
//					tOperate = "0000006015"; // 打印询价确定通知书任务节点编码
//				if (tCode == "64")
//					tOperate = "0000006008"; // 团体询价补充材料通知书
//				if (tCode == "65")
//					tOperate = "0000006021"; // 团单询价跟踪通知书
//				if (tCode == "54") // add by yaory
//					tOperate = "0000002220"; // 打印团单问题件契约内容变更通知书任务节点编码
//				if (tCode == "99") // add by yaory
//					tOperate = "0000002230"; // 打印团单客户合并任务节点编码
//				VData tVData = new VData();
//				tVData.add(tTransferData);
//				tVData.add(tGI);
//				// 数据传输
//				AskWorkFlowUI tAskWorkFlowUI = new AskWorkFlowUI();
//				if (!tAskWorkFlowUI.submitData(tVData, tOperate)) { // 执行保全核保生调工作流节点0000000004
//					int n = tAskWorkFlowUI.mErrors.getErrorCount();
//					Content = " 更新打印数据失败，原因是: "
//							+ tAskWorkFlowUI.mErrors.getError(0).errorMessage;
//					logger.debug(Content);
//					// FlagStr = "Fail";
//					return false;
//				} else {
//					// FlagStr = "Succ";
//					Content = "更新打印数据成功！";
//				}
				// logger.debug("Print:" + FlagStr);
			} else if (tCode == "76" || tCode == "75") { // add by zhangxing
				// 准备数据
				logger.debug("开始打印后台处理");
				// 准备公共传输信息
				// tOperate = new String();
				TransferData tTransferData = new TransferData();
				// tTransferData.setNameAndValue("PrtSeq", PrintNo);
				tTransferData.setNameAndValue("Code", tCode);
				tTransferData.setNameAndValue("ContNo", tContNo);
				tTransferData.setNameAndValue("PrtNo", tPrtNo);
				tTransferData.setNameAndValue("MissionID", tMissionID);
				tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
				if (tCode == "76") {
					tOperate = "0000002106"; // 打印询价确定通知书任务节点编码
				} else if (tCode == "75") {
					tOperate = "0000002306";
				}
				VData tVData = new VData();
				tVData.add(tTransferData);
				tVData.add(tGI);
				// 数据传输
				GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
				if (!tGrpTbWorkFlowUI.submitData(tVData, tOperate)) { // 执行保全核保生调工作流节点0000000004
					int n = tGrpTbWorkFlowUI.mErrors.getErrorCount();
					Content = " 更新打印数据失败，原因是: "
							+ tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
					logger.debug(Content);
					// FlagStr = "Fail";
					return false;
				} else {
					// FlagStr = "Succ";
					Content = "更新打印数据成功！";
				}
				// logger.debug("Print:" + FlagStr);
			}
			// else {
			// LOPRTManagerSchema tLOPRTManagerSchema = new
			// LOPRTManagerSchema();
			// tLOPRTManagerSchema.setPrtSeq(PrtSeq);
			// VData tVData = new VData();
			// tVData.add(tLOPRTManagerSchema);
			// tVData.add(tGI);
			// PrintManagerBL tPrintManagerBL = new PrintManagerBL();
			// if (!tPrintManagerBL.submitData(tVData, "PRINT")) {
			// Content = "Fail:" +
			// tPrintManagerBL.mErrors.getFirstError().toString();
			// return false;
			// }
			// else {
			// //FlagStr = "Nothing";
			// Content = "更新打印数据成功！";
			// // session.putValue("PrintNo",null );
			// }
			// //logger.debug("Print:" + FlagStr);
			// }
		}
		return true;

		// if (PrtSeq == null || tCode == null)
		// {
		//
		// Content = "打印数据完毕！";
		// }
		// else
		// {
		// if (tCode.equals("03") || tCode.equals("04") || tCode.equals("05") ||
		// tCode.equals("14") || tCode.equals("17") || tCode.equals("85") ||
		// tCode.equals("00")
		// || tCode.equals("06") || tCode.equals("81") ||
		// tCode.equals("82") || tCode.equals("83") || tCode.equals("84") ||
		// tCode.equals("86") || tCode.equals("87")
		// || tCode.equals("88") || tCode.equals("BQ80") ||
		// tCode.equals("BQ81") || tCode.equals("BQ82") ||
		// tCode.equals("BQ85") || tCode.equals("BQ84")
		// || tCode.equals("BQ86") || tCode.equals("BQ87") ||
		// tCode.equals("BQ89")) //edit by yaory
		// {
		// //准备数据
		// logger.debug("开始打印后台处理");
		// //准备公共传输信息
		//
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("PrtSeq", PrtSeq);
		// tTransferData.setNameAndValue("Code", tCode);
		// tTransferData.setNameAndValue("ContNo", tContNo);
		// //tTransferData.setNameAndValue("PrtNo", tPrtNo);
		// tTransferData.setNameAndValue("MissionID", tMissionID);
		// tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
		//
		// //if(tCode.equals("87")||tCode.equals("88"))
		
		//
		// VData tVData = new VData();
		// tVData.add(tTransferData);
		// tVData.add(tGI);
		//
		// // 数据传输
		// TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
		// if (!tTbWorkFlowUI.submitData(tVData, tOperate))
		// //执行保全核保生调工作流节点0000000004
		// {
		// //int n = tTbWorkFlowUI.mErrors.getErrorCount();
		// Content = " 更新打印数据失败，原因是: " +
		// tTbWorkFlowUI.mErrors.getError(0).errorMessage;
		// logger.debug(Content);
		// buildError("submitData", Content);
		// return false;
		// }
		//
		// }
		// else if (tCode == "61" || tCode == "64" || tCode == "65" ||
		// tCode.equals("54") || tCode.equals("99")) //add by yaory
		// {
		// //准备数据
		// logger.debug("开始打印后台处理");
		// //准备公共传输信息
		//
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("PrtSeq", PrtSeq);
		// tTransferData.setNameAndValue("Code", tCode);
		// tTransferData.setNameAndValue("GrpContNo", tContNo);
		// //tTransferData.setNameAndValue("PrtNo", tPrtNo);
		// tTransferData.setNameAndValue("MissionID", tMissionID);
		// tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
		//
		// VData tVData = new VData();
		// tVData.add(tTransferData);
		// tVData.add(tGI);
		//
		// // 数据传输
		// AskWorkFlowUI tAskWorkFlowUI = new AskWorkFlowUI();
		// if (!tAskWorkFlowUI.submitData(tVData, tOperate))
		// //执行保全核保生调工作流节点0000000004
		// {
		// //int n = tAskWorkFlowUI.mErrors.getErrorCount();
		// Content = " 更新打印数据失败，原因是: " +
		// tAskWorkFlowUI.mErrors.getError(0).errorMessage;
		// buildError("submitData", Content);
		// return false;
		// }
		//
		// }
		// else if (tCode == "76" || tCode == "75") //add by zhangxing
		// {
		// //准备数据
		// logger.debug("开始打印后台处理");
		// //准备公共传输信息
		//
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("PrtSeq", PrtSeq);
		// tTransferData.setNameAndValue("Code", tCode);
		// tTransferData.setNameAndValue("GrpContNo", tContNo);
		// //tTransferData.setNameAndValue("PrtNo", tPrtNo);
		// tTransferData.setNameAndValue("MissionID", tMissionID);
		// tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
		//
		// VData tVData = new VData();
		// tVData.add(tTransferData);
		// tVData.add(tGI);
		//
		// // 数据传输
		// GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
		// if (!tGrpTbWorkFlowUI.submitData(tVData, tOperate))
		// //执行保全核保生调工作流节点0000000004
		// {
		// // int n = tGrpTbWorkFlowUI.mErrors.getErrorCount();
		// Content = " 更新打印数据失败，原因是: " +
		// tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
		// buildError("submitData", Content);
		// return false;
		// }
		//
		// }
		// else
		// {
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setPrtSeq(PrtSeq);
		// VData tVData = new VData();
		// tVData.add(tLOPRTManagerSchema);
		// tVData.add(tGI);
		//
		// PrintManagerBL tPrintManagerBL = new PrintManagerBL();
		// if (!tPrintManagerBL.submitData(tVData, "PRINT"))
		// {
		// Content = "Fail:" +
		// tPrintManagerBL.mErrors.getFirstError().toString();
		// buildError("submitData", Content);
		// return false;
		// }
		//
		// }
		// }
		// return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "OperPrintTable";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		OperPrintTable operprinttable = new OperPrintTable();
	}
}
