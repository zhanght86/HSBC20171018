<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ManuUWAllChk.jsp
//�����ܣ�����˱�
//�������ڣ�2005-01-19 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.workflow.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) 
  {
		loggerDebug("ProposalApproveChk","session has expired");
		return;
  }
     
 	// Ͷ�����б�

  //String sApplyType = request.getParameter("ApplyType");
  String sMissionID = request.getParameter("MissionID");
  String sSubMissionID = request.getParameter("SubMissionID");
  String sActivityID = request.getParameter("ActivityID");
  String sPrtNo = request.getParameter("PrtNo1");
  loggerDebug("ProposalApproveChk","== sMissionID == " + sMissionID);    	   
  loggerDebug("ProposalApproveChk","== sSubMissionID == " + sSubMissionID); 
  loggerDebug("ProposalApproveChk","== sActivityID == " + sActivityID); 
  TransferData mTransferData = new TransferData();
  //mTransferData.setNameAndValue("ApplyType", sApplyType);	
  mTransferData.setNameAndValue("MissionID", sMissionID);
  mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
  mTransferData.setNameAndValue("ActivityID", sActivityID);
  mTransferData.setNameAndValue("PrtNo",sPrtNo);
  
  try{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( mTransferData );
		tVData.add( tG );
		
		// ���ݴ���
		UWApplyUI tUWApplyUI = new UWApplyUI();
		if (tUWApplyUI.submitData(tVData,"") == false)
		{
			int n = tUWApplyUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			{
			  loggerDebug("ProposalApproveChk","Error: "+tUWApplyUI.mErrors.getError(i).errorMessage);
			  Content = " ����ʧ�ܣ�ԭ����: " + tUWApplyUI.mErrors.getError(0).errorMessage;
			}
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tUWApplyUI.mErrors;
		    loggerDebug("ProposalApproveChk","tError.getErrorCount:"+tError.getErrorCount());
		    if (!tError.needDealError())
		    {                          
		    	Content = " ����ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ����ʧ�ܣ�ԭ����:";
		    	int n = tError.getErrorCount();
    			if (n > 0)
    			{
			      for(int i = 0;i < n;i++)
			      {
			        //tError = tErrors.getError(i);
			        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      }
					}
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
