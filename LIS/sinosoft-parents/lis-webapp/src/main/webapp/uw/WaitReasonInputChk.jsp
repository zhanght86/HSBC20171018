<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PRnewUWManuRReportChk.jsp
//�����ܣ��б��˹��˱��˱��ȴ�ԭ��¼��
//�������ڣ�2008-09-28 11:10:36
//������  ��ln
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
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}

  	//������Ϣ
  	// Ͷ�����б� 
	TransferData tTransferData = new TransferData();
	String tContNo = request.getParameter("ContNoH"); //���ȴ�֪ͨ��ظ��ĺ�ͬ��
	String tMissionID = request.getParameter("MissionIDH");
	String tSubMissionID = request.getParameter("SubMissionIDH");
	String tWaitReason = request.getParameter("WaitReason");
	String tContent = request.getParameter("Content");
	String tUniteNo = request.getParameter("UniteNo");
		
	boolean flag = true;	
	

		if (!tContNo.equals("")&& !tMissionID.equals(""))		{		
			
			//׼������������Ϣ
			tTransferData.setNameAndValue("WaitReason",tWaitReason);
			tTransferData.setNameAndValue("Content",tContent);
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("ContNo",tContNo);
			tTransferData.setNameAndValue("UniteNo",tUniteNo);
			
		}
		else
		{
			flag = false;
			Content = "���ݲ�����!";
		}	
		loggerDebug("WaitReasonInputChk","flag:"+flag);
		try
		{
		  	if (flag == true)
		  	{
				// ׼���������� VData
				VData tVData = new VData();
				tVData.add( tTransferData);
				tVData.add( tG );
				
				// ���ݴ���
				WaitReasonInputUI tWaitReasonInputUI   = new WaitReasonInputUI();
				if (!tWaitReasonInputUI.submitData(tVData,"")) //ִ�гб��˱��˱��ȴ�����
				{
					int n = tWaitReasonInputUI.mErrors.getErrorCount();
					loggerDebug("WaitReasonInputChk","ErrorCount:"+n ) ;
					Content = " ����Լ�˱��ȴ�����ʧ�ܣ�ԭ����: " + tWaitReasonInputUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tWaitReasonInputUI.mErrors;
				    if (!tError.needDealError())
				    {                          
				    	Content = " ����Լ�˱��ȴ�����ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " ����Լ�˱��ȴ�����ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
