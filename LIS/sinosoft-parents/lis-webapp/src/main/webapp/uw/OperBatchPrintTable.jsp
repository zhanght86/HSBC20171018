<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.workflow.ask.*"%>
<%@page import="com.sinosoft.workflowengine.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>

<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	// 输出参数
	String FlagStr = "";
	String Content = "";
	int tSuccNum = 0;
	int tFailNum = 0;
	String tNeedWorkFlow = "0";
	String[] bPrintNo = (String[]) session.getValue("PrintNo");
	String tCode = (String) session.getValue("Code");
	String[] bPrtNo = (String[]) session.getValue("PrtNo");
	String[] bContNo = (String[]) session.getValue("ContNo");
	String[] bGrpContNo = (String[]) session.getValue("GrpContNo");

	String[] bMissionID = (String[]) session.getValue("MissionID");
	String[] bSubMissionID = (String[]) session.getValue("SubMissionID");
	String[] b11GrpContNo = (String[]) session.getValue("11GrpContNo");
	String[] bEdorNo = (String[]) session.getValue("EdorNo");
	String[] bEdorType = (String[]) session.getValue("EdorType");
	String[] bClmNo = (String[]) session.getValue("ClmNo");
	String[] bBatNo = (String[]) session.getValue("BatNo");

	String tAlertFlag = "";

	loggerDebug("OperBatchPrintTable","/***********************/");
	loggerDebug("OperBatchPrintTable","/***********************/");
	loggerDebug("OperBatchPrintTable","PrintNo:" + bPrintNo.length);
	loggerDebug("OperBatchPrintTable","tCode:" + tCode + "HOHO");
	loggerDebug("OperBatchPrintTable","tPrtNo:" + bPrtNo.length);
	loggerDebug("OperBatchPrintTable","tContNo:" + bContNo.length);
//	loggerDebug("OperBatchPrintTable","tMissionID:" + bMissionID.length);
//	loggerDebug("OperBatchPrintTable","tSubMissionID:" + bSubMissionID.length);
//	loggerDebug("OperBatchPrintTable","put session value,tGrpContNo:"+ bGrpContNo.length);

	for (int i = 0; i < bPrintNo.length; i++) {
		String PrintNo = bPrintNo[i];
		String tPrtNo =  bPrtNo[i];
		String tContNo =  bContNo[i];
		String tMissionID = (bMissionID==null)?null:bMissionID[i];
		String tSubMissionID =  (bSubMissionID==null)?null:bSubMissionID[i];
//		String tGrpContNo = (String) bGrpContNo[i];
//		String t11GrpContNo = (String) b11GrpContNo[i];
		String tEdorNo =(bEdorNo==null)?null: bEdorNo[i];
		String tEdorType = (bEdorType==null)?null: bEdorType[i];
		String tClmNo = (bClmNo==null)?null: bClmNo[i];
		String tBatNo = (bBatNo==null)?null: bBatNo[i];
		
		


		if (PrintNo == null || tCode == null || "".equals(tCode)) {
			FlagStr = "Succ";
			tSuccNum++;
			Content = "打印数据完毕！";
			tAlertFlag = "Succ";
		}
		//tongmeng 2007-10-30 add
		//如果tMissionID = "TBJB" 则为承保直接下结论系统自动发放的单证，不需要做工作流扭转。直接打印
		else if ("TBJB".equals(tMissionID) || tCode.equals("BQ84")) {
			loggerDebug("OperBatchPrintTable","TBJB 默认打印");
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			loggerDebug("OperBatchPrintTable","TBJB 进入更新的PrintNo:" + PrintNo);
			tLOPRTManagerSchema.setPrtSeq(PrintNo);

			VData tVData = new VData();
			tVData.add(tLOPRTManagerSchema);
			tVData.add(tGI);

			PrintManagerBL tPrintManagerBL = new PrintManagerBL();
			if (!tPrintManagerBL.submitData(tVData, "CONFIRM")) {
				tFailNum++;
				FlagStr = "Fail:"+ tPrintManagerBL.mErrors.getFirstError().toString();
			} else {
				FlagStr = "Nothing";
				Content = "更新打印数据成功！";
				tSuccNum++;
				// 	     session.putValue("PrintNo",null );

			}
			loggerDebug("OperBatchPrintTable","TBJB Print:" + FlagStr);
		}
		//xiongzh 2009-2-24 add
		//如果tMissionID = "XBJB" 则为续保直接下结论系统自动发放的单证，不需要做工作流扭转。直接打印
		else if ("XBJB".equals(tMissionID)) {
			loggerDebug("OperBatchPrintTable","XBJB 默认打印");
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			loggerDebug("OperBatchPrintTable","XBJB 进入更新的PrintNo:" + PrintNo);
			tLOPRTManagerSchema.setPrtSeq(PrintNo);

			VData tVData = new VData();
			tVData.add(tLOPRTManagerSchema);
			tVData.add(tGI);

			RnewRefusePUI tRnewRefusePUI = new RnewRefusePUI();
			if (!tRnewRefusePUI.submitData(tVData, "PRINT")) {
				tFailNum++;
				FlagStr = "Fail:"+ tRnewRefusePUI.mErrors.getFirstError().toString();
			} else {
				tFailNum++;
				FlagStr = "Nothing";
				Content = "更新打印数据成功！";
				// 	     session.putValue("PrintNo",null );

			}
			loggerDebug("OperBatchPrintTable","XBJB Print:" + FlagStr);
		} else {
			if (tCode.equals("TB90") || tCode.equals("TB89")
					|| tCode.equals("14") || tCode.equals("03")
					|| tCode.equals("LP03") || tCode.equals("23")
					|| tCode.equals("04") || tCode.equals("LP04")
					|| tCode.equals("05") || tCode.equals("14")
					|| tCode.equals("17") || tCode.equals("85")
					|| tCode.equals("00") || tCode.equals("06")
					|| tCode.equals("81") || tCode.equals("LP81")
					|| tCode.equals("82") || tCode.equals("83")
					|| tCode.equals("84") || tCode.equals("86")
					|| tCode.equals("87") || tCode.equals("88")
					|| tCode.equals("89") || tCode.equals("LP89")
					|| tCode.equals("BQ80") || tCode.equals("BQ81")
					|| tCode.equals("BQ82") || tCode.equals("BQ85")
					|| tCode.equals("BQ86") || tCode.equals("BQ87")
					|| tCode.equals("BQ89") || tCode.equals("BQ88")
					|| tCode.equals("24") || tCode.equals("25")
					|| tCode.equals("27") || tCode.equals("43")
					|| tCode.equals("44") || tCode.equals("45")
					|| tCode.equals("LP90") || tCode.equals("LP99")) //edity by yaory
			{
				//准备数据
				loggerDebug("OperBatchPrintTable","开始打印后台处理");
				//准备公共传输信息
				String tOperate = new String();
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("PrtSeq", PrintNo);
				tTransferData.setNameAndValue("Code", tCode);
				tTransferData.setNameAndValue("ContNo", tContNo);
				tTransferData.setNameAndValue("PrtNo", tPrtNo);
				tTransferData.setNameAndValue("MissionID", tMissionID);
				tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
				tTransferData.setNameAndValue("EdorNo", tEdorNo);
				tTransferData.setNameAndValue("EdorType", tEdorType);
				tTransferData.setNameAndValue("ClmNo", tClmNo);
				tTransferData.setNameAndValue("BatNo", tBatNo);

				//打印业务员通知书
				if (tCode.equals("14"))
					tOperate = "0000001017";
				//打印核保通知书(打印类)
				if (tCode.equals("TB89"))
					tOperate = "0000001107";
				//核保通知书(非打印类)
				if (tCode.equals("TB90"))
					tOperate = "0000001302";
				//打印生调通知书
				if (tCode.equals("04"))
					tOperate = "0000001108";
				//打印体检通知书
				if (tCode.equals("03"))
					tOperate = "0000001106";
				//保全体检
				if (tCode.equals("23"))
					tOperate = "0000000302";
				//保全生调
				if (tCode.equals("24"))
					tOperate = "0000000312";
				//保全核保通知书
				if (tCode.equals("25"))
					tOperate = "0000000320";
				//保全补充资料通知书
				if (tCode.equals("27"))
					tOperate = "0000000351";
				//续保二核体检
				if (tCode.equals("43"))
					tOperate = "0000007007";
				//续保二核生调
				if (tCode.equals("44"))
					tOperate = "0000007010";
				//续保二核核保通知书
				if (tCode.equals("45"))
					tOperate = "0000007004";

				//	if(tCode == "85")
				//    tOperate = "0000001023";  //打印业务员通知书任务节点编码
				if (tCode.equals("17"))
					tOperate = "0000001230"; //打印客户合并通知书任务节点编码

				//打印理赔体检通知书
				if (tCode.equals("LP03")) {
					tOperate = "0000005531";
				}
				//打印理赔核保通知书
				if (tCode.equals("LP90")) {
					tOperate = "0000005551";
				}
				if (tCode.equals("LP99")) {
					tOperate = "0000005561";
				}
				if (tCode.equals("00") || tCode.equals("06")
						|| tCode.equals("81") || tCode.equals("LP81")
						|| tCode.equals("82") || tCode.equals("83")
						|| tCode.equals("84") || tCode.equals("00")
						|| tCode.equals("86") || tCode.equals("85")
						|| tCode.equals("87") || tCode.equals("88")
						|| tCode.equals("89") || tCode.equals("LP89")
						|| tCode.equals("BQ80") || tCode.equals("BQ81")
						|| tCode.equals("BQ82") || tCode.equals("BQ85")
						|| tCode.equals("BQ84") || tCode.equals("BQ86")
						|| tCode.equals("BQ87") || tCode.equals("BQ89")
						|| tCode.equals("BQ88"))
					tOperate = "0000001280"; //核保通知书任务节点编码
				//if(tCode.equals("87")||tCode.equals("88"))
				// tOperate = "0000001108";   //面见通知书任务节点编码

				VData tVData = new VData();
				tVData.add(tTransferData);
				tVData.add(tGI);
				loggerDebug("OperBatchPrintTable","tCode:" + tCode + "tOperate:"+ tOperate);
				// 数据传输
				TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
				if (!tTbWorkFlowUI.submitData(tVData, tOperate)) //执行保全核保生调工作流节点0000000004
				{
					int n = tTbWorkFlowUI.mErrors.getErrorCount();
					Content = " 更新打印数据失败，原因是: "+ tTbWorkFlowUI.mErrors.getError(0).errorMessage;
					loggerDebug("OperBatchPrintTable",Content);
					tFailNum++;
					FlagStr = "Fail";
				} else {
					tSuccNum++;
					FlagStr = "Succ";
					Content = "更新打印数据成功！";
				}
				loggerDebug("OperBatchPrintTable","Print:" + FlagStr);
			} else if (tCode.trim().equals("bqnotice")) //保全通知书及收据，added by liurx
			{
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("OtherNo", PrintNo);
				VData tVData = new VData();
				tVData.add(tTransferData);
				tVData.add(tGI);

				PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
				if (!tPrtUpdateBL.submitData(tVData, "NOTICE")) {
					tFailNum++;
					FlagStr = "Fail:"+ tPrtUpdateBL.mErrors.getFirstError().toString();
				} else {
					tSuccNum++;
					FlagStr = "Succ";
					Content = "更新打印数据成功！";
				}
			} else if (tCode.trim().equals("BQCHECK")) //保全通知书及收据，added by liurx
			{
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("SendOutCom", String.valueOf(session.getValue("ssManageCom")));
				tTransferData.setNameAndValue("ReceiveCom", String.valueOf(session.getValue("ssAgentCode")));
				tTransferData.setNameAndValue("CertifyCode",(String) session.getValue("certifycode"));
				tTransferData.setNameAndValue("StartNo", String.valueOf(session.getValue("ChequNo")));
				tTransferData.setNameAndValue("EndNo", String.valueOf(session.getValue("ChequNo")));
				tTransferData.setNameAndValue("Handler", tGI.Operator);
				tTransferData.setNameAndValue("OtherNo", PrintNo);
				VData tVData = new VData();
				tVData.add(tTransferData);
				tVData.add(tGI);

				PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
				if (!tPrtUpdateBL.submitData(tVData, "BQCHECK")) {
					tFailNum++;
					FlagStr = "Fail:"
							+ tPrtUpdateBL.mErrors.getFirstError()
									.toString();
				} else {
					tSuccNum++;
					FlagStr = "Succ";
					Content = "更新打印数据成功！";
				}
			} else if (tCode.trim().equals("endorsement")) //保全批单，added by liurx
			{
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("OtherNo", PrintNo);
				VData tVData = new VData();
				tVData.add(tTransferData);
				tVData.add(tGI);

				PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
				if (!tPrtUpdateBL.submitData(tVData, "EDORNO")) {
					tFailNum++;
					FlagStr = "Fail:"+ tPrtUpdateBL.mErrors.getFirstError().toString();
				} else {
					tSuccNum++;
					FlagStr = "Succ";
					Content = "更新打印数据成功！";
				}
			} else if (tCode.trim().equals("appendorsement")) //保全批单，added by liurx
			{
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("OtherNo", PrintNo);
				VData tVData = new VData();
				tVData.add(tTransferData);
				tVData.add(tGI);

				PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
				if (!tPrtUpdateBL.submitData(tVData, "EDORACCEPTNO")) {
					tFailNum++;
					FlagStr = "Fail:"+ tPrtUpdateBL.mErrors.getFirstError().toString();
				} else {
					tSuccNum++;
					FlagStr = "Succ";
					Content = "更新打印数据成功！";
				}
			} else if (tCode.trim().equals("EdorNameList")) //团体保全人名清单，added by QianLy
			{
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("OtherNo", PrintNo);
				VData tVData = new VData();
				tVData.add(tTransferData);
				tVData.add(tGI);

				PrtUpdateBL tPrtUpdateBL = new PrtUpdateBL();
				if (!tPrtUpdateBL.submitData(tVData, "GRPEDORNO")) {
					tFailNum++;
					FlagStr = "Fail:"+ tPrtUpdateBL.mErrors.getFirstError().toString();
				} else {
					tSuccNum++;
					FlagStr = "Succ";
					Content = "更新打印数据成功！";
				}
			} else if (tCode.trim().equals("G03")
					|| tCode.trim().equals("G04")) {
				LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
				tLOPRTManagerSchema.setPrtSeq(PrintNo);
				loggerDebug("OperBatchPrintTable","通知书流水号码：" + PrintNo);
				VData tVData1 = new VData();
				tVData1.add(tLOPRTManagerSchema);
				tVData1.add(tGI);

				PrintSendOutSysCertBL tPrintSendOutSysCertBL = new PrintSendOutSysCertBL();
				if (!tPrintSendOutSysCertBL.submitData(tVData1, "")) {
					tFailNum++;
					FlagStr = "Fail:"+ tPrintSendOutSysCertBL.mErrors.getFirstError().toString();
				} else {
					tSuccNum++;
					//FlagStr1=tAutoSysCertSendOutBL.getResult();
					FlagStr = "Succ";
					Content = "更新打印数据成功！";
					session.putValue("PrintNo", null);
				}
				loggerDebug("OperBatchPrintTable","SysCert:" + FlagStr);
			}

			else if (tCode.equals("54") || tCode == "54") {
				LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
				loggerDebug("OperBatchPrintTable","进入更新的PrintNo:" + PrintNo);
				tLOPRTManagerSchema.setPrtSeq(PrintNo);

				VData tVData = new VData();
				tVData.add(tLOPRTManagerSchema);
				tVData.add(tGI);

				PrtDealBL tPrtDealBL = new PrtDealBL();
				if (!tPrtDealBL.submitData(tVData, "CONFIRM")) {
					tSuccNum++;
					FlagStr = "Fail:"+ tPrtDealBL.mErrors.getFirstError().toString();
				} else {
					tSuccNum++;
					FlagStr = "Succ";
					Content = "更新打印数据成功！";
				}

			} else {
				loggerDebug("OperBatchPrintTable","默认打印");
				LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
				loggerDebug("OperBatchPrintTable","进入更新的PrintNo:" + PrintNo);
				tLOPRTManagerSchema.setPrtSeq(PrintNo);

				VData tVData = new VData();
				tVData.add(tLOPRTManagerSchema);
				tVData.add(tGI);

				PrintManagerBL tPrintManagerBL = new PrintManagerBL();
				if (!tPrintManagerBL.submitData(tVData, "CONFIRM")) {
					tFailNum++;
					FlagStr = "Fail:"+ tPrintManagerBL.mErrors.getFirstError().toString();
				} else {
					tSuccNum++;
					FlagStr = "Nothing";
					Content = "更新打印数据成功！";
					// 	     session.putValue("PrintNo",null );

				}
				loggerDebug("OperBatchPrintTable","Print:" + FlagStr);

			}

		}

	}
%>
<html>
<script language="javascript">
        var tNeedWorkFlow = '<%=tNeedWorkFlow%>';
        if(tNeedWorkFlow!=null&&tNeedWorkFlow!="null"&&tNeedWorkFlow=="0"){
            window.returnValue="打印状态更新成功：<%="" + tSuccNum%> 张;打印状态更新失败：<%="" + tFailNum%>张，请打印后核对！";
        }
        else{
            window.returnValue="单证发送成功：<%="" + tSuccNum%> 张;单证发放失败：<%="" + tFailNum%>张，请打印后核对！";
        }    
        
        window.close();
    </script>
</html>


