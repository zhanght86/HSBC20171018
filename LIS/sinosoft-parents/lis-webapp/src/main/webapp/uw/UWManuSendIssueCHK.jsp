<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuRReportChk.jsp
//�����ܣ�����Լ�˹��˱����ͺ˱�֪ͨ�鱨��¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //�������
  CErrors tError = null;
 String FlagStr = "Fail";
 String Content = "";
  GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}

  	//������Ϣ
	TransferData tTransferData = new TransferData();
	String tContNo = request.getParameter("ContNo");
	String tPrtNo = request.getParameter("PrtNoHide");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubNoticeMissionID");	
	loggerDebug("UWManuSendIssueCHK","Contno:"+tContNo);
	loggerDebug("UWManuSendIssueCHK","flag:"+tPrtNo);
	loggerDebug("UWManuSendIssueCHK","flag:"+tMissionID);
	loggerDebug("UWManuSendIssueCHK","tSubMissionID:"+tSubMissionID);
	boolean flag = true;
	if (!tContNo.equals("")&&!tPrtNo.equals("")&& !tMissionID.equals(""))
	{
		//׼������������Ϣ
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
		tTransferData.setNameAndValue("MissionID",tMissionID);	
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
	
	}
	else
	{
		flag = false;
		Content = "���ݲ�����!";
	}	
	loggerDebug("UWManuSendIssueCHK","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tG );
		
		// ���ݴ���
		TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
		if (!tTbWorkFlowUI.submitData(tVData,"0000001022")) //ִ�б�ȫ�˱������������ڵ�0000000004
		{
			int n = tTbWorkFlowUI.mErrors.getErrorCount();
			Content = " ����Լ�˹��˷��ͺ˱�֪ͨ��ʧ�ܣ�ԭ����: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tTbWorkFlowUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " ����Լ�˹��˱����ͺ˱�֪ͨ��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ����Լ�˹��˱����ͺ˱�֪ͨ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		     }
		}
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+".��ʾ���쳣��ֹ!";
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
