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
  <%@page import="com.sinosoft.workflow.claim.*"%>
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
	String tClmNo = request.getParameter("ClmNo");
	String tBatNo = request.getParameter("BatNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	
	loggerDebug("LLRePrintSave","ContNo:"+tContNo);
	loggerDebug("LLRePrintSave","tClmNo:"+tClmNo);
	loggerDebug("LLRePrintSave","tBatNo:"+tBatNo);
	loggerDebug("LLRePrintSave","MissionID:"+tMissionID);
	loggerDebug("LLRePrintSave","SubMissionID:"+tSubMissionID);
	loggerDebug("LLRePrintSave","Code:"+tCode);
	boolean flag = true;
	if (!tContNo.equals("")&& !tMissionID.equals("") && !tSubMissionID.equals(""))
	{
		//׼������������Ϣ
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("ClmNo",tClmNo);
		tTransferData.setNameAndValue("BatNo",tBatNo);
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
	loggerDebug("LLRePrintSave","flag:"+flag);
	loggerDebug("LLRePrintSave","tCode:"+tCode);
try
{
	String tOperate = "";
  	if(!tActivityID.equals(""))
	   {
		// ׼���������� VData				
	      tOperate = tActivityID;
	   }
	    loggerDebug("LLRePrintSave","tOperate"+tOperate);
	      
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tG );
		
		// ���ݴ���
		String busiName="tWorkFlowUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
					Content = "�ύ������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
			   }
			   else
			   {
					Content = "�ύ������ʧ��";
					FlagStr = "Fail";				
			   }
		  }
		  else
		  {
		     	Content = "�����ύ�ɹ�! ";
		      	FlagStr = "Succ";  
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
