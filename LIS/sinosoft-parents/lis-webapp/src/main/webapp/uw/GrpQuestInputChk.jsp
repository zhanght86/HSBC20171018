<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpQuestInputChk.jsp
//�����ܣ����������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
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
	LCPolSet tLCPolSet = new LCPolSet();
	LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

	String tProposalNo = request.getParameter("ProposalNoHide");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("BackObj");
	String tQuest = request.getParameter("Quest");
	String tContent = request.getParameter("Content");
	
	//String tcont[] = request.getParameterValues("QuestGrid3");
	//String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("GrpQuestInputChk","polno:"+tProposalNo);
	loggerDebug("GrpQuestInputChk","operatepos:"+tFlag);
	loggerDebug("GrpQuestInputChk","backobj:"+tBackObj);
	loggerDebug("GrpQuestInputChk","issuetype:"+tQuest);
	
	boolean flag = true;
	//int feeCount = tcont.length;

	if (tProposalNo.equals("") || tFlag.equals("") || tBackObj.equals("") || tContent.equals("") || tQuest.equals(""))
	{
		Content = "����¼�벻��ȫ����ʧ��!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("GrpQuestInputChk","ʧ��");
	}
	else
	{
		loggerDebug("GrpQuestInputChk","begin");

		LCPolSchema tLCPolSchema = new LCPolSchema();
 			
		tLCPolSchema.setPolNo( tProposalNo);
		tLCPolSchema.setProposalNo(tProposalNo);
	    		
    		tLCPolSet.add( tLCPolSchema );
	    				
		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
		
		loggerDebug("GrpQuestInputChk","operatepos:"+tFlag);
		tLCIssuePolSchema.setIssueCont(tContent);
		tLCIssuePolSchema.setOperatePos(tFlag);
		tLCIssuePolSchema.setBackObjType(tBackObj);
		tLCIssuePolSchema.setIssueType(tQuest);
			
		tLCIssuePolSet.add(tLCIssuePolSchema);			    
	}
	
	loggerDebug("GrpQuestInputChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCPolSet );
		tVData.add( tLCIssuePolSet);
		tVData.add( tG );
		
		// ���ݴ���
		//QuestInputChkUI tQuestInputChkUI   = new QuestInputChkUI();
		 String busiName="cbcheckQuestInputChkUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
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
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
