<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWSendPrintChk.jsp
//�����ܣ��ʹ�ӡ����
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.xb.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
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
  //tongmeng 2007-11-09 modify
  //�޸�Ϊͳһ���ź˱�֪ͨ��,һ��������ԼΪ��,�����ٶ��������ô˹��ܳ������޸�.
  //У�鴦��
  //���ݴ����	
			
  	//������Ϣ
  	// Ͷ�����б�	
	String tContNo = request.getParameter("ContNo");
	String tOtherNoType = "02";
	String mtestRnewFlag = "1";
	String tCode = request.getParameter("hiddenNoticeType");
	
	loggerDebug("RnewSendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
  loggerDebug("RnewSendAllNoticeChk","@@@@@@@@@@@@@@@@tSubMissionID:"+tSubMissionID+"################");
	boolean flag = true;
try
{
  	if (flag == true)
  	{
  	
  	//tongmeng 2008-08-12 modify
  	//Ϊ֧�ֶ����ն౻����,ͬһʱ����ԶԲ�ͬ���ն����ͺ˱�֪ͨ��,
  	//������������֪ͨ��Ҳ�����﷢��.
  	//String tSQL = "select missionid,SubMissionID from lwmission where missionid='"+tMissionID+"' and activityid='0000007002'";
  	String mSQL = "select missionid,SubMissionID from lwmission where missionid='"+tMissionID+"' and activityid=(select activityid from lwactivity  where functionId = '10047001')";
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(mSQL);
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
			mTransferData.setNameAndValue("ContNo",tContNo);
			mTransferData.setNameAndValue("NoticeType",tCode);
			mTransferData.setNameAndValue("MissionID",tMissionId);
   			mTransferData.setNameAndValue("SubMissionID",tSubMissionId); 
   		 	mTransferData.setNameAndValue("BusiType", "1004"); 
   		 	mTransferData.setNameAndValue("testRnewFlag", mtestRnewFlag); 
    		tVData.add(mTransferData);
    		//String busiName="RnewWorkFlowUI";
    		String busiName="WorkFlowUI";
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            if(!tBusinessDelegate.submitData(tVData,"create",busiName))
            {
				int n = tBusinessDelegate.getCErrors().getErrorCount();
				if(tBusinessDelegate.getCErrors().getError(0).errorMessage.equals("û��¼���������û�гб��ƻ������û����Լ��û�мӷѣ����ܷ��˱�֪ͨ��!"))
				{
					errorCount = errorCount + 1;
					if(errorCount == tSSRS.getMaxRow())
					{
						FlagStr = "Fail";
						Content = " ����ʧ��,ԭ����:";
					}
					else
					{
						tBusinessDelegate.getCErrors().clearErrors();
						continue;
					}					    
				}
				else
				{			
					FlagStr = "Fail";	
				}
			}
    		/*
			// ���ݴ���
			RnewWorkFlowUI tRnewWorkFlowUI   = new RnewWorkFlowUI();
			//tongmeng 2007-11-08 modify
			//ͳһ���ź˱�֪ͨ��
			if (tRnewWorkFlowUI.submitData(tVData,"0000007002") == false)
			{
				int n = tRnewWorkFlowUI.mErrors.getErrorCount();
				if(tRnewWorkFlowUI.mErrors.getError(0).errorMessage.equals("û��¼���������û�гб��ƻ������û����Լ��û�мӷѣ����ܷ��˱�֪ͨ��!"))
				{
					errorCount = errorCount + 1;
					if(errorCount == tSSRS.getMaxRow())
					{
						FlagStr = "Fail";
						Content = " ����ʧ��,ԭ����:";
					}
					else
					{
						tRnewWorkFlowUI.mErrors.clearErrors();
						continue;
					}					    
				}
				else
				{			
					FlagStr = "Fail";	
				}
			}
			*/
			//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			if (FlagStr == "Fail")
			{
		    	tError = tBusinessDelegate.getCErrors();		    
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
	parent.fraInterface.afterSubmitSendNotice("<%=FlagStr%>","<%=Content%>");
	
</script>
</html>
