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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
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
	LCContSet tLCContSet = new LCContSet();
	LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

	String tContNo = request.getParameter("ContNo");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("Quest");
	String treply = request.getParameter("ReplyResult");
	String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //����������ظ���ϱ�־
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContent = "";
	loggerDebug("QuestReplyChk","tMissionID="+tMissionID);
	loggerDebug("QuestReplyChk","tSubMissionID="+tSubMissionID);
	
	String tcont[] = request.getParameterValues("QuestGrid3");
	String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("QuestReplyChk","contno:"+tContNo);
	loggerDebug("QuestReplyChk","flag:"+tFlag);
	loggerDebug("QuestReplyChk","reply:"+treply);
	loggerDebug("QuestReplyChk","issuetype:"+tBackObj);
	loggerDebug("QuestReplyChk","tSerialNo:"+tSerialNo);
	boolean flag = true;
	if(QuesFlag.equals("allover"))           //���ִ�С��ظ���ϡ���������ִ�й������ڵ�
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tG );
		
		TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
				if (tTbWorkFlowUI.submitData(tVData,"0000001020") == false)
				{
					int n = tTbWorkFlowUI.mErrors.getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " ����ҵ��Ա֪ͨ��ʧ�ܣ�ԭ����: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tTbWorkFlowUI.mErrors;
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
	else
	{
			if (tSerialNo.equals("")||tContNo.equals("") || tFlag.equals("") || treply.equals("") || tBackObj.equals(""))
			{
				Content = "����¼�벻��ȫ����ʧ��!";
				FlagStr = "Fail";
				flag = false;
				loggerDebug("QuestReplyChk","ʧ��");
			}
			else
			{
				loggerDebug("QuestReplyChk","begin");
  		
				LCContSchema tLCContSchema = new LCContSchema();
 					
				tLCContSchema.setContNo( tContNo);
  		
  		  		tLCContSet.add( tLCContSchema );
			    		
  		
					
				LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
				
				//tLCIssuePolSchema.setIssueCont(tContent);
				tLCIssuePolSchema.setOperatePos(tFlag);
				tLCIssuePolSchema.setIssueType(tBackObj);
				tLCIssuePolSchema.setReplyResult(treply);
				tLCIssuePolSchema.setSerialNo(tSerialNo);
					
				tLCIssuePolSet.add(tLCIssuePolSchema);			    
				
			}
			
			loggerDebug("QuestReplyChk","flag:"+flag);
  			if (flag == true)
  			{
				// ׼���������� VData
				VData tVData = new VData();
				loggerDebug("QuestReplyChk","ContNo="+tLCContSet.get(1).getContNo());
				tVData.add( tLCContSet );
				tVData.add( tLCIssuePolSet);
				tVData.add( tG );
				
				// ���ݴ���
				QuestReplyChkUI tQuestReplyChkUI   = new QuestReplyChkUI();
				if (tQuestReplyChkUI.submitData(tVData,"INSERT") == false)
				{
					int n = tQuestReplyChkUI.mErrors.getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tQuestReplyChkUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tQuestReplyChkUI.mErrors;
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
