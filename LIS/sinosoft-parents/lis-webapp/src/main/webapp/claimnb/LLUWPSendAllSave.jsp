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
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.lis.claimnb.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  
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
  
  
  	// Ͷ�����б�	
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
    String tProposalNo = request.getParameter("ContNo");
	String tOtherNoType = "02";
	
	String tCode      = request.getParameter("NoticeType");
	String tRemark    = request.getParameter("Content");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
	String tClmNo     = request.getParameter("ClmNo");
	String tContNo    = request.getParameter("ContNo");
	loggerDebug("LLUWPSendAllSave","�����õ��ⰸ�ţ�����"+tClmNo);
	loggerDebug("LLUWPSendAllSave","�����õ������ţ�����"+tContNo);
	
	boolean flag = false;

	if(tCode.equals("LP89"))
 	{
  
	String tSerialNo[] = request.getParameterValues("QuestionTypeGridNo");
	String tLCRReportPrtCode[] = request.getParameterValues("QuestionTypeGrid1");
  int ChkCount = 0;
	if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}

     	for(int i=0;i<ChkCount;i++)
			{
			 	LCRReportPrtSchema tLCRReportPrtSchema = new LCRReportPrtSchema();
		    tLCRReportPrtSchema.setAskCode(tLCRReportPrtCode[i]);
				tLCRReportPrtSet.add(tLCRReportPrtSchema);
			}
	}	
	
	if (!tProposalNo.equals("")&&!tOtherNoType.equals("")&&!tCode.equals(""))
	{
 	    tLOPRTManagerSchema.setOtherNo( tProposalNo);
	    tLOPRTManagerSchema.setOtherNoType(tOtherNoType);
	    tLOPRTManagerSchema.setCode(tCode);
	    tLOPRTManagerSchema.setRemark(tRemark);    
	    tLOPRTManagerSchema.setStandbyFlag1(tMissionID);  
	    tLOPRTManagerSchema.setStandbyFlag2(tClmNo);  
	    flag = true;
	}
	else
	{
	    FlagStr = "Fail";
	    Content = "���봫��ʧ��!";
	}
	
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLOPRTManagerSchema);
		tVData.add( tG );
    TransferData mTransferData = new TransferData();
 
    if(tCode.equals("LP81"))
    {
    mTransferData.setNameAndValue("MissionID",tMissionID);
    mTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
    tVData.add(mTransferData);
    }
   
    if(tCode.equals("LP89"))
    {
    mTransferData.setNameAndValue("MissionID",tMissionID);
    mTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
    mTransferData.setNameAndValue("LCRReportPrtSet",tLCRReportPrtSet);
    tVData.add(mTransferData);
    }
		
		TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
		LLUWPNoticeUI tLLUWPNoticeUI   = new LLUWPNoticeUI();		
		// ���ݴ���
    if(tCode.equals("LP89")||tCode.equals("LP81"))
    {

		  if (tTbWorkFlowUI.submitData(tVData,"0000001270") == false)
	   	{
			int n = tTbWorkFlowUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " ����ʧ��,ԭ����: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		  }
    }		
		
		else
		{
	   	if (tLLUWPNoticeUI.submitData(tVData,"INSERT") == false)
		  {
			int n = tLLUWPNoticeUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " ����ʧ��,ԭ����: " + tLLUWPNoticeUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		  }
		}
		
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
			
			if(tCode.equals("LP89")||tCode.equals("LP81"))
			{
		   	tError = tTbWorkFlowUI.mErrors;		    
		    if (!tError.needDealError())
		    {                     
		    	Content = " �����ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                              
		    {
		    	Content = " ����ʧ��,ԭ����:";
		    	int n = tError.getErrorCount();
    			if (n > 0)
    			{
			      for(int i = 0;i < n;i++)
			      {			        
			        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      }
			    }
		    	FlagStr = "Fail";
		    }				
				
				}
			else
		   { 
		   	tError = tLLUWPNoticeUI.mErrors;		    
		    if (!tError.needDealError())
		    {                     
		    	Content = " �����ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                              
		    {
		    	Content = " ����ʧ��,ԭ����:";
		    	int n = tError.getErrorCount();
    			if (n > 0)
    			{
			      for(int i = 0;i < n;i++)
			      {			        
			        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      }
			    }
		    	FlagStr = "Fail";
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
