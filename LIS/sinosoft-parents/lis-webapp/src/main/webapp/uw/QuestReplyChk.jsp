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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
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
  //tongmeng 2009-03-26 add
  //���Ӽ����󷢰�ť
  String tErrorFlagAction = request.getParameter("ErrorFlagAction");
  //У�鴦��
  //���ݴ����
  loggerDebug("QuestReplyChk","tErrorFlagAction:"+tErrorFlagAction);
  	//������Ϣ
  	// Ͷ�����б�
	LCContSet tLCContSet = new LCContSet();
	LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

	String tContNo = request.getParameter("ContNo");
	String tFlag = request.getParameter("HideOperatePos");
	String tBackObj = request.getParameter("Quest");
	String treply = request.getParameter("ReplyResult");
	String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //����������ظ���ϱ�־
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tErrFlag = request.getParameter("ErrorFlag");
	String tErrOperator =tG.Operator;
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
	
	if(tErrorFlagAction==null||tErrorFlagAction.equals("")
			||tErrorFlagAction.equals("null")||!tErrorFlagAction.equals("1"))
	{
	if(QuesFlag.equals("allover"))           //���ִ�С��ظ���ϡ���������ִ�й������ڵ�
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tG );
		
		//TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
		
		String busiName="tbTbWorkFlowUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
				if (tBusinessDelegate.submitData(tVData,"0000001020",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " ������ظ�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " ������ظ��ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " ������ظ�ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
				if("Y".equals(tErrFlag)){
				tLCIssuePolSchema.setErrOperator(tErrOperator);
				tLCIssuePolSchema.setErrMakeDate(PubFun.getCurrentDate());
				tLCIssuePolSchema.setErrMakeTime(PubFun.getCurrentTime());
				}
				
				tLCIssuePolSchema.setErrFlag(tErrFlag);
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
				//QuestReplyChkUI tQuestReplyChkUI   = new QuestReplyChkUI();
				
				String busiName="cbcheckQuestReplyChkUI";
				   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				
				if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " ������ظ���ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " ������ظ��ɹ�! ";
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
	}
	else
	{
		loggerDebug("QuestReplyChk","�޸ļ����󷢱��");
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
			if("Y".equals(tErrFlag)){
				tLCIssuePolSchema.setErrOperator(tErrOperator);
				tLCIssuePolSchema.setErrMakeDate(PubFun.getCurrentDate());
				tLCIssuePolSchema.setErrMakeTime(PubFun.getCurrentTime());
				}
			
			tLCIssuePolSchema.setErrFlag(tErrFlag);
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
			
			String busiName="cbcheckQuestErrFlagChkUI";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			
			//QuestErrFlagChkUI tQuestErrFlagChkUI   = new QuestErrFlagChkUI();
			if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
			{
				int n = tBusinessDelegate.getCErrors().getErrorCount();
				for (int i = 0; i < n; i++)
				Content = " ������ظ���ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			if (FlagStr == "Fail")
			{
			    tError = tBusinessDelegate.getCErrors();
			    if (!tError.needDealError())
			    {                          
			    	Content = " ������ظ��ɹ�! ";
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
