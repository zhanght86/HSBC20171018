<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestReplyChk.jsp
//�����ܣ�������ظ�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="com.sinosoft.service.*" %>
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
  
  //У�鴦��
  //���ݴ����
  
  	//������Ϣ
  	// Ͷ�����б�
	LPIssuePolSet tLPIssuePolSet = new LPIssuePolSet();

	String tEdorNo = request.getParameter("EdorNo");
	String tContNo = request.getParameter("ContNo");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("Quest");
	String treply = request.getParameter("ReplyResult");
	String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //����������ظ���ϱ�־
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tContent = "";
	loggerDebug("BQQuestReplyChk","tMissionID="+tMissionID);
	loggerDebug("BQQuestReplyChk","tSubMissionID="+tSubMissionID);
	 String ErrorMessage = "";
	String tcont[] = request.getParameterValues("QuestGrid3");
	String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("BQQuestReplyChk","edorno:"+tEdorNo);
	loggerDebug("BQQuestReplyChk","contno:"+tContNo);
	loggerDebug("BQQuestReplyChk","flag:"+tFlag);
	loggerDebug("BQQuestReplyChk","reply:"+treply);
	loggerDebug("BQQuestReplyChk","issuetype:"+tBackObj);
	loggerDebug("BQQuestReplyChk","tSerialNo:"+tSerialNo);
	boolean flag = true;
	if(QuesFlag.equals("allover"))           //���ִ�С��ظ���ϡ���������ִ�й������ڵ�
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorNo",tEdorNo);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("ActivityID",tActivityID);
		tTransferData.setNameAndValue("BusiType", "1002");
		VData tVData = new VData();
	//������ظ����֮��ۺϵ��˹��˱����۽ڵ�	
		  EdorManuDataDeal tEdorManuDataDeal=new EdorManuDataDeal();
		  if(!tEdorManuDataDeal.submitData(tTransferData))
		  {
			  Content = "����ʧ�ܣ�ԭ����:" + tEdorManuDataDeal.getErrors().getFirstError();
		      FlagStr = "Fail";
		  }
		  tTransferData=tEdorManuDataDeal.getTransferData();
		
		tVData.add(tTransferData);
		tVData.add( tG );
  	//String busiName="tWorkFlowUI";
  	String busiName="WorkFlowUI";
  	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
           ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
				   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "����ʧ��";
				   FlagStr = "Fail";				
				}		
	 
	 }
	else
	 {
				  Content ="����ɹ���";
		    	FlagStr = "Succ";	
	 }	 

	 
	 
	 
	 }
	else
	{
			if (tSerialNo.equals("")||tEdorNo.equals("")||tContNo.equals("") || tFlag.equals("") || treply.equals("") || tBackObj.equals(""))
			{
				Content = "����¼�벻��ȫ����ʧ��!";
				FlagStr = "Fail";
				flag = false;
				loggerDebug("BQQuestReplyChk","ʧ��");
			}
			else
			{
				loggerDebug("BQQuestReplyChk","begin");	
  		
					
				LPIssuePolSchema tLPIssuePolSchema = new LPIssuePolSchema();
				
				//tLCIssuePolSchema.setIssueCont(tContent);
				tLPIssuePolSchema.setEdorNo(tEdorNo);				
				tLPIssuePolSchema.setOperatePos(tFlag);
				tLPIssuePolSchema.setIssueType(tBackObj);
				tLPIssuePolSchema.setReplyResult(treply);
				tLPIssuePolSchema.setSerialNo(tSerialNo);
				tLPIssuePolSet.add(tLPIssuePolSchema);			    
				
			}
			
			loggerDebug("BQQuestReplyChk","flag:"+flag);
  			if (flag == true)
  			{
				// ׼���������� VData
				VData tVData = new VData();
				loggerDebug("BQQuestReplyChk","ContNo="+tContNo);
				tVData.add( tLPIssuePolSet);
				tVData.add( tG );
				
				// ���ݴ���
				//BQQuestReplyChkUI tBQQuestReplyChkUI   = new BQQuestReplyChkUI();
				String busiName="BQQuestReplyChkUI";
  	 			 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " ����������ظ�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " �˹��˱��ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " �˹��˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
				    	FlagStr = "Fail";
				    }
				}
			} 
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
