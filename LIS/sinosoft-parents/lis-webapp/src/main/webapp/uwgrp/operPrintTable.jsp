<%@page import="com.sinosoft.workflow.tb.TbWorkFlowUI"%>
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.f1printgrp.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
    <%@page import="com.sinosoft.workflow.ask.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>

<%
  GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI");
// �������
   String FlagStr = "";
   String Content = "";
   String PrintNo=(String)session.getValue("PrintNo");
   String tCode = (String)session.getValue("Code");
   String tPrtNo = (String)session.getValue("PrtNo");
   String tContNo = (String)session.getValue("ContNo");
   String tMissionID = (String)session.getValue("MissionID");
   String tSubMissionID = (String)session.getValue("SubMissionID");
   String tGrpContNo = (String)session.getValue("GrpContNo");
   loggerDebug("operPrintTable","PrintNo:"+PrintNo);
   loggerDebug("operPrintTable","put session value,ContNo2:"+tContNo);
   loggerDebug("operPrintTable","tMissionID:"+tMissionID);
   loggerDebug("operPrintTable","tCode:"+tCode);

   if(PrintNo==null || tCode == null)
   {
          FlagStr="Succ";
         Content="��ӡ������ϣ�";
   }
  else
   {
   	 if(tCode.equals("03")|| tCode.equals("BQ90") || tCode.equals("04")||tCode.equals("05")||tCode.equals("14")||tCode.equals("17") || tCode.equals("85")||tCode.equals("00")||tCode.equals("06")||tCode.equals("81")||tCode.equals("82")||tCode.equals("83")||tCode.equals("84")||tCode.equals("86")||tCode.equals("87")||tCode.equals("88")||tCode.equals("89")||tCode.equals("BQ80")||tCode.equals("BQ81")||tCode.equals("BQ82")||tCode.equals("BQ85")||tCode.equals("BQ84")||tCode.equals("BQ86")||tCode.equals("BQ87")||tCode.equals("BQ89")) //edity by yaory
	   {
	   			//׼������
	   			loggerDebug("operPrintTable","��ʼ��ӡ��̨����");
					//׼������������Ϣ
					String tOperate = new String();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("PrtSeq",PrintNo);
					tTransferData.setNameAndValue("Code",tCode) ;
					tTransferData.setNameAndValue("ContNo",tContNo) ;
					tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
					tTransferData.setNameAndValue("MissionID",tMissionID) ;
					tTransferData.setNameAndValue("SubMissionID",tSubMissionID) ;
	  			 if(tCode == "03")
	  			    tOperate = "0000001106"; //��ӡ���֪ͨ������ڵ����
	  			 if(tCode == "04")
	  			    tOperate = "0000001108";  //��ӡ���֪ͨ������ڵ����
	  			 if(tCode == "05")
	  			    tOperate = "0000001107"; //��ӡ�˱�֪ͨ������ڵ����
	  			 if(tCode == "14")
	  			    tOperate = "0000001220";  //��ӡ��Լ���ݱ��֪ͨ������ڵ����
	  			 
	  		//	if(tCode == "85")
	  			//    tOperate = "0000001023";  //��ӡҵ��Ա֪ͨ������ڵ����
	  			if(tCode == "17")
	  			    tOperate = "0000001230";  //��ӡ�ͻ��ϲ�֪ͨ������ڵ����

	  			if(tCode.equals("00")||tCode.equals("06")||tCode.equals("81")||tCode.equals("82")||tCode.equals("83")||tCode.equals("84")||tCode.equals("00")||tCode.equals("86")||tCode.equals("85")||tCode.equals("87")||tCode.equals("88")||tCode.equals("89")||tCode.equals("BQ80")||tCode.equals("BQ81")||tCode.equals("BQ82")||tCode.equals("BQ85")||tCode.equals("BQ84")||tCode.equals("BQ86")||tCode.equals("BQ87")||tCode.equals("BQ89"))
	  			  tOperate = "0000001280"; //�˱�֪ͨ������ڵ����
	  			 //if(tCode.equals("87")||tCode.equals("88"))
	  			  // tOperate = "0000001108";   //���֪ͨ������ڵ����

	  			 VData tVData = new VData();
	  			 tVData.add(tTransferData);
	  			 tVData.add(tGI);

    			   // ���ݴ���
					TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
					if (!tTbWorkFlowUI.submitData(tVData,tOperate)) //ִ�б�ȫ�˱������������ڵ�0000000004
					{
						int n = tTbWorkFlowUI.mErrors.getErrorCount();
						Content = " ���´�ӡ����ʧ�ܣ�ԭ����: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
						loggerDebug("operPrintTable",Content);
						FlagStr = "Fail";
					}
    			   else
    			   {
    			     FlagStr="Succ";
    			     Content="���´�ӡ���ݳɹ���";
    			   }
    			   loggerDebug("operPrintTable","Print:"+FlagStr);
	 }
	 else if(tCode == "61" || tCode == "64"||tCode == "65"||tCode.equals("54")||tCode.equals("99")) //add by yaory
	 {
	   			//׼������
	   			loggerDebug("operPrintTable","��ʼ��ӡ��̨����");
					//׼������������Ϣ
					String tOperate = new String();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("PrtSeq",PrintNo);
					tTransferData.setNameAndValue("Code",tCode) ;
					tTransferData.setNameAndValue("GrpContNo",tGrpContNo) ;
					tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
					tTransferData.setNameAndValue("MissionID",tMissionID) ;
					tTransferData.setNameAndValue("SubMissionID",tSubMissionID) ;
	  			 if(tCode == "61")
	  			    tOperate = "0000006015"; //��ӡѯ��ȷ��֪ͨ������ڵ����
	  			 if(tCode == "64")
	  			    tOperate = "0000006008";  //����ѯ�۲������֪ͨ��
	  			 if(tCode == "65")
	  			    tOperate = "0000006021"; //�ŵ�ѯ�۸���֪ͨ��
	  			 if(tCode == "54") //add by yaory
	  			    tOperate = "0000002220";  //��ӡ�ŵ��������Լ���ݱ��֪ͨ������ڵ����
	  			 if(tCode == "99") //add by yaory
	  			    tOperate = "0000002230";  //��ӡ�ŵ��ͻ��ϲ�����ڵ����
	  			 VData tVData = new VData();
	  			 tVData.add(tTransferData);
	  			 tVData.add(tGI);

    			   // ���ݴ���
					AskWorkFlowUI tAskWorkFlowUI   = new AskWorkFlowUI();
					if (!tAskWorkFlowUI.submitData(tVData,tOperate)) //ִ�б�ȫ�˱������������ڵ�0000000004
					{
						int n = tAskWorkFlowUI.mErrors.getErrorCount();
						Content = " ���´�ӡ����ʧ�ܣ�ԭ����: " + tAskWorkFlowUI.mErrors.getError(0).errorMessage;
						loggerDebug("operPrintTable",Content);
						FlagStr = "Fail";
					}
    			   else
    			   {
    			     FlagStr="Succ";
    			     Content="���´�ӡ���ݳɹ���";
    			   }
    			   loggerDebug("operPrintTable","Print:"+FlagStr);
	 }
	  else if(tCode == "76" || tCode == "75" ) //add by zhangxing
	 {
	   			//׼������
	   			loggerDebug("operPrintTable","��ʼ��ӡ��̨����");
					//׼������������Ϣ
					String tOperate = new String();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("PrtSeq",PrintNo);
					tTransferData.setNameAndValue("Code",tCode) ;
					tTransferData.setNameAndValue("GrpContNo",tGrpContNo) ;
					tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
					tTransferData.setNameAndValue("MissionID",tMissionID) ;
					tTransferData.setNameAndValue("SubMissionID",tSubMissionID) ;
	  			 if(tCode == "76")
	  			 {
	  			    tOperate = "0000002106"; //��ӡѯ��ȷ��֪ͨ������ڵ����
	  			 }
	  			 else if(tCode == "75")
	  			 {
	  			    tOperate = "0000002306";
	  			 }
	  			         
	  			
	  			 VData tVData = new VData();
	  			 tVData.add(tTransferData);
	  			 tVData.add(tGI);

    			   // ���ݴ���
					GrpTbWorkFlowUI tGrpTbWorkFlowUI   = new GrpTbWorkFlowUI();
					if (!tGrpTbWorkFlowUI.submitData(tVData,tOperate)) //ִ�б�ȫ�˱������������ڵ�0000000004
					{
						int n = tGrpTbWorkFlowUI.mErrors.getErrorCount();
						Content = " ���´�ӡ����ʧ�ܣ�ԭ����: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
						loggerDebug("operPrintTable",Content);
						FlagStr = "Fail";
					}
    			   else
    			   {
    			     FlagStr="Succ";
    			     Content="���´�ӡ���ݳɹ���";
    			   }
    			   loggerDebug("operPrintTable","Print:"+FlagStr);
	 }
	 else
     {
	   LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	   loggerDebug("operPrintTable","������µ�PrintNo:"+PrintNo);
	   tLOPRTManagerSchema.setPrtSeq(PrintNo);
	   
	   VData tVData = new VData();
	   tVData.add(tLOPRTManagerSchema);
	   tVData.add(tGI);

       PrintManagerBL tPrintManagerBL = new PrintManagerBL();
       if(!tPrintManagerBL.submitData(tVData,"CONFIRM"))
       {
         FlagStr="Fail:"+tPrintManagerBL.mErrors.getFirstError().toString();
       }
       else
       {
         FlagStr = "Nothing";
         Content="���´�ӡ���ݳɹ���";
// 	     session.putValue("PrintNo",null );

       }
       loggerDebug("operPrintTable","Print:"+FlagStr);

   }
  }


%>
<html>
<script language="javascript">
if("<%=FlagStr%>"=='Fail')
{
alert("�˵�֤���ܷ��ͣ�");
}

window.returnValue="<%=FlagStr%>";

window.close();
</script>
</html>


