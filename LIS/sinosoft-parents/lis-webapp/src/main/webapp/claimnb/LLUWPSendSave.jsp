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
  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  
<%
  //�������
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //У�鴦��
  //���ݴ����	
			
  	//������Ϣ
  	// Ͷ�����б�	
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
  LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
	String tProposalNo = request.getParameter("ContNo");
	String tOtherNoType = "02";
	
	String tCode = request.getParameter("NoticeType");
	String tRemark = request.getParameter("Content");
	loggerDebug("LLUWPSendSave","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	boolean flag = false;
	
	if(tCode.equals("89")||tCode.equals("BQ88"))
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
	    loggerDebug("LLUWPSendSave","============EdorNo:" + tEdorNo);
	    loggerDebug("LLUWPSendSave","============EdorType:" + tEdorType);
	    if(!tEdorType.equals("") && !tEdorNo.equals("")){
	       tLOPRTManagerSchema.setStandbyFlag1(tEdorNo);
	       tLOPRTManagerSchema.setStandbyFlag2(tEdorType);
	    }	    
	    
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
		mTransferData.setNameAndValue("MissionID",tMissionID);
    mTransferData.setNameAndValue("SubMissionID",tSubMissionID); 
    
    if(tCode.equals("89")||tCode.equals("BQ88"))
    {
    mTransferData.setNameAndValue("LCRReportPrtSet",tLCRReportPrtSet);
    }
   
    tVData.add(mTransferData);
		// ���ݴ���
		TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
		if (tTbWorkFlowUI.submitData(tVData,"0000001270") == false)
		{
			int n = tTbWorkFlowUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " ����ʧ��,ԭ����: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
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
