<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestInputChk.jsp
//�����ܣ������¼��
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
	LCContSet tLCContSet = new LCContSet();
	LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

	String tContNo = request.getParameter("ContNo");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("BackObj");
	String tQuest = request.getParameter("Quest");
	String tContent = request.getParameter("Content");
	String tQuestionObj = request.getParameter("CustomerNo");
	String tQuestionObjName = request.getParameter("CustomerName");

	String tQuestionObjValue = request.getParameter("QuestionObj");
	
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	
	String mFlag = "2";
	
	//String tcont[] = request.getParameterValues("QuestGrid3");
	//String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("QuestInputChk","ContNo:"+tContNo);
	loggerDebug("QuestInputChk","operatepos:"+tFlag);
	loggerDebug("QuestInputChk","backobj:"+tBackObj);
	loggerDebug("QuestInputChk","issuetype:"+tQuest);
	loggerDebug("QuestInputChk","content"+tContent);
	
	loggerDebug("QuestInputChk","tMissionID"+tMissionID);
	loggerDebug("QuestInputChk","tSubMissionID"+tSubMissionID);
	loggerDebug("QuestInputChk","tActivityID"+tActivityID);
	
	boolean flag = true;
	//int feeCount = tcont.length;

	if ("".equals(tContNo) || "".equals(tFlag) || "".equals(tBackObj) || "".equals(tContent.trim()) || "".equals(tQuest.trim()))
	{
	   loggerDebug("QuestInputChk","fdfdfdfdf");
	
		Content = "���������¼�벻��ȫ����ʧ��!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("QuestInputChk","ʧ��");
	}
	
	else
	{
		loggerDebug("QuestInputChk","begin");

		LCContSchema tLCContSchema = new LCContSchema();
 			
		tLCContSchema.setContNo( tContNo);
		tLCContSchema.setState(mFlag);		  
			    		
    	tLCContSet.add( tLCContSchema );
	    				
		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
		
		loggerDebug("QuestInputChk","operatepos:"+tFlag);
		tLCIssuePolSchema.setIssueCont(tContent);
		tLCIssuePolSchema.setOperatePos(tFlag);
		tLCIssuePolSchema.setBackObjType(tBackObj);
		tLCIssuePolSchema.setIssueType(tQuest);
		tLCIssuePolSchema.setQuestionObjType(tQuestionObjValue);
			tLCIssuePolSchema.setQuestionObj(tQuestionObj);
		tLCIssuePolSchema.setQuestionObjName(tQuestionObjName);
				
		tLCIssuePolSet.add(tLCIssuePolSchema);			    
	}
	
	loggerDebug("QuestInputChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		loggerDebug("QuestInputChk","contno="+tContNo);

		tTransferData.setNameAndValue("MissionID", tMissionID);
		tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
		tTransferData.setNameAndValue("ActivityID", tActivityID);

		
		tVData.add( tLCContSet );
		tVData.add( tLCIssuePolSet);
		tVData.add( tG );
		tVData.add(tTransferData);
		
		
		// ���ݴ���
		loggerDebug("QuestInputChk","sdfsdf");
		
		String busiName="cbcheckQuestInputChkUI";
	    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   
		//QuestInputChkUI tQuestInputChkUI   = new QuestInputChkUI();
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �����¼��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " �����¼��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " �����¼��ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
