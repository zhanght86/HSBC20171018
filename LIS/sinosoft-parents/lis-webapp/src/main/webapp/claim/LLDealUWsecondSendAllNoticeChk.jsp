<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLDealUWsecondSendAllNoticeChk.jsp
//�����ܣ��ʹ�ӡ����
//�������ڣ�2009-01-15 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  
<%
  //�������
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "����֪ͨ�鷢��ʧ��,ԭ����:";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}

			
  	//������Ϣ
  	// Ͷ�����б�	
	String tCaseNo = request.getParameter("CaseNo");
	
	String tCode = request.getParameter("hiddenNoticeType");
	
	loggerDebug("LLDealUWsecondSendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
    loggerDebug("LLDealUWsecondSendAllNoticeChk","@@@@@@@@@@@@@@@@tSubMissionID:"+tSubMissionID+"################");
	boolean flag = true;
	try
	{
	  	if (flag == true)
	  	{
	  	
	  	//tongmeng 2008-08-12 modify
	  	//Ϊ֧�ֶ����ն౻����,ͬһʱ����ԶԲ�ͬ���ն����ͺ˱�֪ͨ��,
	  	//������������֪ͨ��Ҳ�����﷢��.
	  	String tSQL = "select missionid,SubMissionID from lwmission where missionid='"+tMissionID+"' and activityid='0000000100' ";
	  	
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSQL);
		int errorCount = 0;
		for(int m=1;m<=tSSRS.getMaxRow();m++)
		{
			String tMissionId = tSSRS.GetText(m,1);
			String tSubMissionId = tSSRS.GetText(m,2);
			// ׼���������� VData
			VData tVData = new VData();
			//tVData.add( tLOPRTManagerSchema);
			tVData.add( tG );
			TransferData mTransferData = new TransferData();
			mTransferData.setNameAndValue("CaseNo",tCaseNo);
			mTransferData.setNameAndValue("NoticeType",tCode);
			mTransferData.setNameAndValue("MissionID",tMissionId);
   			mTransferData.setNameAndValue("SubMissionID",tSubMissionId); 
   			mTransferData.setNameAndValue("mSubMissionID",tSubMissionID); 
    
    		tVData.add(mTransferData);
			// ���ݴ���
			EdorWorkFlowUI tEdorWorkFlowUI   = new EdorWorkFlowUI();
			//tongmeng 2007-11-08 modify
			//ͳһ���ź˱�֪ͨ��
			if (tEdorWorkFlowUI.submitData(tVData,"0000000100") == false) //1105
			{
				int n = tEdorWorkFlowUI.mErrors.getErrorCount();
				if(tEdorWorkFlowUI.mErrors.getError(0).errorMessage.equals("û��¼���������û�гб��ƻ������û����Լ��û�мӷѣ����ܷ��˱�֪ͨ��!"))
				{
					errorCount = errorCount + 1;
					if(errorCount == tSSRS.getMaxRow())
					{
						FlagStr = "Fail";
						Content = " ����ʧ��,ԭ����:";
					}
					else
					{
						tEdorWorkFlowUI.mErrors.clearErrors();
						continue;
					}					    
				}
				else
				{			
					FlagStr = "Fail";	
				}
			}
			//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			if (FlagStr == "Fail")
			{
		    	tError = tEdorWorkFlowUI.mErrors;		    
		    	if (!tError.needDealError())
		    	{                     
		    		if (m == tSSRS.getMaxRow())
			    	{                     
			    		Content = " �����ɹ�! ";
			    	}
		    		FlagStr = "Succ";
		    	}
		    	else                                                              
		   		{		    		
		    		int n = tError.getErrorCount();
    				if (n > 0)
    				{
			      	for(int i = 0;i < n;i++)
			      	{			        
			        	Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      	}
					}
		    			FlagStr = "Fail";
		    			break;
		   		}
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
