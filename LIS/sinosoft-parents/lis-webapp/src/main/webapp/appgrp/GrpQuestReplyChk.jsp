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
	LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();

	String tGrpContNo = request.getParameter("GrpContNo");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("Quest");
	String treply = request.getParameter("ReplyResult");
	String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //����������ظ���ϱ�־
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContent = "";
	loggerDebug("GrpQuestReplyChk","tMissionID="+tMissionID);
	loggerDebug("GrpQuestReplyChk","tSubMissionID="+tSubMissionID);
	
	String tcont[] = request.getParameterValues("QuestGrid3");
	String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("GrpQuestReplyChk","GrpContNo:"+tGrpContNo);
	loggerDebug("GrpQuestReplyChk","flag:"+tFlag);
	loggerDebug("GrpQuestReplyChk","reply:"+treply);
	loggerDebug("GrpQuestReplyChk","issuetype:"+tBackObj);
	loggerDebug("GrpQuestReplyChk","tSerialNo:"+tSerialNo);
	boolean flag = true;
/*	if(QuesFlag.equals("allover"))           //���ִ�С��ظ���ϡ���������ִ�й������ڵ�
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tG );
		 String busiName="tbTbWorkFlowUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
				if (tBusinessDelegate.submitData(tVData,"0000001020",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " ����ҵ��Ա֪ͨ��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
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
	else
*/	{
			if (tSerialNo.equals("")||tGrpContNo.equals("") || tFlag.equals("") || treply.equals("") || tBackObj.equals(""))
			{
				Content = "����¼�벻��ȫ����ʧ��!";
				FlagStr = "Fail";
				flag = false;
				loggerDebug("GrpQuestReplyChk","ʧ��");
			}
			else
			{
				loggerDebug("GrpQuestReplyChk","begin");
  		
				LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
 					
				tLCGrpContSchema.setGrpContNo( tGrpContNo);
  		
  		  		tLCGrpContSet.add( tLCGrpContSchema );
			    		
  		
					
				LCGrpIssuePolSchema tLCGrpIssuePolSchema = new LCGrpIssuePolSchema();
				
				//tLCGrpIssuePolSchema.setIssueCont(tContent);
				tLCGrpIssuePolSchema.setOperatePos(tFlag);
				tLCGrpIssuePolSchema.setIssueType(tBackObj);
				tLCGrpIssuePolSchema.setReplyResult(treply);
				tLCGrpIssuePolSchema.setSerialNo(tSerialNo);
					
				tLCGrpIssuePolSet.add(tLCGrpIssuePolSchema);			    
				
			}
			
			loggerDebug("GrpQuestReplyChk","flag:"+flag);
  			if (flag == true)
  			{
				// ׼���������� VData
				VData tVData = new VData();
				loggerDebug("GrpQuestReplyChk","GrpContNo="+tLCGrpContSet.get(1).getGrpContNo());
				tVData.add( tLCGrpContSet );
				tVData.add( tLCGrpIssuePolSet);
				tVData.add( tG );
				
				// ���ݴ���
				 String busiName="cbcheckgrpGrpQuestReplyChkUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				//GrpQuestReplyChkUI tGrpQuestReplyChkUI   = new GrpQuestReplyChkUI();
				if (tBusinessDelegate.submitData(tVData,"UPDATE",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " ������ظ�ʧ�ܣ�ԭ����: " +tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " ������ظ��˹��˱��ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " ������ظ��˹��˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
