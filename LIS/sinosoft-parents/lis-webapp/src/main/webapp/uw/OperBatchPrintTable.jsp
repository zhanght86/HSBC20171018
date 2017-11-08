<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
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
	// �������
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
			Content = "��ӡ������ϣ�";
			tAlertFlag = "Succ";
		}
		//tongmeng 2007-10-30 add
		//���tMissionID = "TBJB" ��Ϊ�б�ֱ���½���ϵͳ�Զ����ŵĵ�֤������Ҫ��������Ťת��ֱ�Ӵ�ӡ
		else if ("TBJB".equals(tMissionID) || tCode.equals("BQ84")) {
			loggerDebug("OperBatchPrintTable","TBJB Ĭ�ϴ�ӡ");
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			loggerDebug("OperBatchPrintTable","TBJB ������µ�PrintNo:" + PrintNo);
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
				Content = "���´�ӡ���ݳɹ���";
				tSuccNum++;
				// 	     session.putValue("PrintNo",null );

			}
			loggerDebug("OperBatchPrintTable","TBJB Print:" + FlagStr);
		}
		//xiongzh 2009-2-24 add
		//���tMissionID = "XBJB" ��Ϊ����ֱ���½���ϵͳ�Զ����ŵĵ�֤������Ҫ��������Ťת��ֱ�Ӵ�ӡ
		else if ("XBJB".equals(tMissionID)) {
			loggerDebug("OperBatchPrintTable","XBJB Ĭ�ϴ�ӡ");
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			loggerDebug("OperBatchPrintTable","XBJB ������µ�PrintNo:" + PrintNo);
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
				Content = "���´�ӡ���ݳɹ���";
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
				//׼������
				loggerDebug("OperBatchPrintTable","��ʼ��ӡ��̨����");
				//׼������������Ϣ
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

				//��ӡҵ��Ա֪ͨ��
				if (tCode.equals("14"))
					tOperate = "0000001017";
				//��ӡ�˱�֪ͨ��(��ӡ��)
				if (tCode.equals("TB89"))
					tOperate = "0000001107";
				//�˱�֪ͨ��(�Ǵ�ӡ��)
				if (tCode.equals("TB90"))
					tOperate = "0000001302";
				//��ӡ����֪ͨ��
				if (tCode.equals("04"))
					tOperate = "0000001108";
				//��ӡ���֪ͨ��
				if (tCode.equals("03"))
					tOperate = "0000001106";
				//��ȫ���
				if (tCode.equals("23"))
					tOperate = "0000000302";
				//��ȫ����
				if (tCode.equals("24"))
					tOperate = "0000000312";
				//��ȫ�˱�֪ͨ��
				if (tCode.equals("25"))
					tOperate = "0000000320";
				//��ȫ��������֪ͨ��
				if (tCode.equals("27"))
					tOperate = "0000000351";
				//�����������
				if (tCode.equals("43"))
					tOperate = "0000007007";
				//������������
				if (tCode.equals("44"))
					tOperate = "0000007010";
				//�������˺˱�֪ͨ��
				if (tCode.equals("45"))
					tOperate = "0000007004";

				//	if(tCode == "85")
				//    tOperate = "0000001023";  //��ӡҵ��Ա֪ͨ������ڵ����
				if (tCode.equals("17"))
					tOperate = "0000001230"; //��ӡ�ͻ��ϲ�֪ͨ������ڵ����

				//��ӡ�������֪ͨ��
				if (tCode.equals("LP03")) {
					tOperate = "0000005531";
				}
				//��ӡ����˱�֪ͨ��
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
					tOperate = "0000001280"; //�˱�֪ͨ������ڵ����
				//if(tCode.equals("87")||tCode.equals("88"))
				// tOperate = "0000001108";   //���֪ͨ������ڵ����

				VData tVData = new VData();
				tVData.add(tTransferData);
				tVData.add(tGI);
				loggerDebug("OperBatchPrintTable","tCode:" + tCode + "tOperate:"+ tOperate);
				// ���ݴ���
				TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
				if (!tTbWorkFlowUI.submitData(tVData, tOperate)) //ִ�б�ȫ�˱������������ڵ�0000000004
				{
					int n = tTbWorkFlowUI.mErrors.getErrorCount();
					Content = " ���´�ӡ����ʧ�ܣ�ԭ����: "+ tTbWorkFlowUI.mErrors.getError(0).errorMessage;
					loggerDebug("OperBatchPrintTable",Content);
					tFailNum++;
					FlagStr = "Fail";
				} else {
					tSuccNum++;
					FlagStr = "Succ";
					Content = "���´�ӡ���ݳɹ���";
				}
				loggerDebug("OperBatchPrintTable","Print:" + FlagStr);
			} else if (tCode.trim().equals("bqnotice")) //��ȫ֪ͨ�鼰�վݣ�added by liurx
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
					Content = "���´�ӡ���ݳɹ���";
				}
			} else if (tCode.trim().equals("BQCHECK")) //��ȫ֪ͨ�鼰�վݣ�added by liurx
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
					Content = "���´�ӡ���ݳɹ���";
				}
			} else if (tCode.trim().equals("endorsement")) //��ȫ������added by liurx
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
					Content = "���´�ӡ���ݳɹ���";
				}
			} else if (tCode.trim().equals("appendorsement")) //��ȫ������added by liurx
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
					Content = "���´�ӡ���ݳɹ���";
				}
			} else if (tCode.trim().equals("EdorNameList")) //���屣ȫ�����嵥��added by QianLy
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
					Content = "���´�ӡ���ݳɹ���";
				}
			} else if (tCode.trim().equals("G03")
					|| tCode.trim().equals("G04")) {
				LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
				tLOPRTManagerSchema.setPrtSeq(PrintNo);
				loggerDebug("OperBatchPrintTable","֪ͨ����ˮ���룺" + PrintNo);
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
					Content = "���´�ӡ���ݳɹ���";
					session.putValue("PrintNo", null);
				}
				loggerDebug("OperBatchPrintTable","SysCert:" + FlagStr);
			}

			else if (tCode.equals("54") || tCode == "54") {
				LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
				loggerDebug("OperBatchPrintTable","������µ�PrintNo:" + PrintNo);
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
					Content = "���´�ӡ���ݳɹ���";
				}

			} else {
				loggerDebug("OperBatchPrintTable","Ĭ�ϴ�ӡ");
				LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
				loggerDebug("OperBatchPrintTable","������µ�PrintNo:" + PrintNo);
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
					Content = "���´�ӡ���ݳɹ���";
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
            window.returnValue="��ӡ״̬���³ɹ���<%="" + tSuccNum%> ��;��ӡ״̬����ʧ�ܣ�<%="" + tFailNum%>�ţ����ӡ��˶ԣ�";
        }
        else{
            window.returnValue="��֤���ͳɹ���<%="" + tSuccNum%> ��;��֤����ʧ�ܣ�<%="" + tFailNum%>�ţ����ӡ��˶ԣ�";
        }    
        
        window.close();
    </script>
</html>


