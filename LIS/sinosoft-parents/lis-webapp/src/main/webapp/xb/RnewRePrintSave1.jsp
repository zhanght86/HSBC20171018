<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PEdorReprintSava.jsp
//�����ܣ���ȫ�˹��˱�����֪ͨ��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
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
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	String tPrtSeq = request.getParameter("PrtSeq");
	String tCode = request.getParameter("Code");
	String tContNo = request.getParameter("ContNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	
	loggerDebug("RnewRePrintSave1","ContNo:"+tContNo);
	loggerDebug("RnewRePrintSave1","MissionID:"+tMissionID);
	loggerDebug("RnewRePrintSave1","SubMissionID:"+tSubMissionID);
	loggerDebug("RnewRePrintSave1","Code:"+tCode);
	boolean flag = true;
	if (!tContNo.equals("")&& !tMissionID.equals("") && !tSubMissionID.equals(""))
	{
		//׼������������Ϣ
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("Code",tCode);
		tTransferData.setNameAndValue("MissionID",tMissionID);	
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("ActivityID",tActivityID);
		tTransferData.setNameAndValue("LOPRTManagerSchema",tLOPRTManagerSchema);
	}
	else
	{
		flag = false;
		Content = "���ݲ�����!";
	}	
	loggerDebug("RnewRePrintSave1","flag:"+flag);
	loggerDebug("RnewRePrintSave1","tCode:"+tCode);
try
{
	String tOperate = "";
  	if(!tActivityID.equals(""))
	   {
		// ׼���������� VData				
	      tOperate = tActivityID;
	   }
	    loggerDebug("RnewRePrintSave1","tOperate"+tOperate);
	      
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tG );
		
		// ���ݴ���
		 String busiName="WorkFlowUI";
		 BusinessDelegate  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
     	System.out.println("��ʼִ�У�tBusinessDelegate");
     	if(!tBusinessDelegate.submitData(tVData,"execute",busiName)){
     		int n = tBusinessDelegate.getCErrors().getErrorCount();
     		Content = " ����֪ͨ�������ύʧ�ܣ�ԭ����: " +tBusinessDelegate.getCErrors().getError(0).errorMessage;
     		FlagStr = "Fail";
     	}
		/*
		EdorWorkFlowUI tEdorWorkFlowUI   = new EdorWorkFlowUI();
		if (!tEdorWorkFlowUI.submitData(tVData,tOperate))
		{
			int n = tEdorWorkFlowUI.mErrors.getErrorCount();
			Content = " ����֪ͨ�������ύʧ�ܣ�ԭ����: " + tEdorWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		*/
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = "����֪ͨ�������ύ�ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ����֪ͨ�������ύʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
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
