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
  <%@page import="com.sinosoft.lis.xb.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
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
	RnewIssuePolSet tRnewIssuePolSet = new RnewIssuePolSet();

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
	//tongmeng 2007-11-08 add
	//�����Ƿ��·�ѡ��
	String tNeedPrintFlag = request.getParameter("NeedPrintFlag");	
	
	String hiddenBackObj =  request.getParameter("hiddenBackObj");
	String hiddenQuestionPrint =  request.getParameter("hiddenQuestionPrint");
	
	String mFlag = "2";
	String tQuestionOperator =  request.getParameter("hiddenQuestionOperator");
	String hiddenProposalContNo = request.getParameter("hiddenProposalContNo");
	String hiddenQuestionSeq = request.getParameter("hiddenQuestionSeq");
	//String tcont[] = request.getParameterValues("QuestGrid3");
	//String tChk[] = request.getParameterValues("InpQuestGridChk");
	String tQuestionnaire = request.getParameter("Questionnaire");
	String tHealthCheck = request.getParameter("HealthCheck");
	String tOccupation = request.getParameter("Occupation");
	String tFinanceO = request.getParameter("FinanceO");
	
	loggerDebug("RnewQuestInputChk","ContNo:"+tContNo);
	loggerDebug("RnewQuestInputChk","operatepos:"+tFlag);
	loggerDebug("RnewQuestInputChk","backobj:"+tBackObj);
	loggerDebug("RnewQuestInputChk","issuetype:"+tQuest);
	loggerDebug("RnewQuestInputChk","content"+tContent);
	
	loggerDebug("RnewQuestInputChk","tHealthCheck:"+tHealthCheck);
	loggerDebug("RnewQuestInputChk","tOccupation"+tOccupation);
	loggerDebug("RnewQuestInputChk","tFinanceO"+tFinanceO);
	loggerDebug("RnewQuestInputChk","tQuestionnaire"+tQuestionnaire);
	
	loggerDebug("RnewQuestInputChk","tMissionID"+tMissionID);
	loggerDebug("RnewQuestInputChk","tSubMissionID"+tSubMissionID);
	loggerDebug("RnewQuestInputChk","tActivityID"+tActivityID);
	
	boolean flag = true;
	//int feeCount = tcont.length;

	if (!tQuestionOperator.equals("UPDATE")&&!tQuestionOperator.equals("DELETE")&&("".equals(tContNo) || "".equals(tFlag) || "".equals(tBackObj) || "".equals(tContent.trim()) || "".equals(tQuest.trim())))
	{
	   loggerDebug("RnewQuestInputChk","fdfdfdfdf");
	
		Content = "���������¼�벻��ȫ����ʧ��!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("RnewQuestInputChk","ʧ��");
	}
	
	else
	{
		loggerDebug("RnewQuestInputChk","begin");

		LCContSchema tLCContSchema = new LCContSchema();
 			
		tLCContSchema.setContNo( tContNo);
		tLCContSchema.setState(mFlag);		  
			    		
    	tLCContSet.add( tLCContSchema );
	    				
		RnewIssuePolSchema tRnewIssuePolSchema = new RnewIssuePolSchema();
		
		if(tQuestionOperator.equals("INSERT"))
		{
		loggerDebug("RnewQuestInputChk","operatepos:"+tFlag);
		tRnewIssuePolSchema.setIssueCont(tContent);
		tRnewIssuePolSchema.setOperatePos(tFlag);
		tRnewIssuePolSchema.setBackObjType(tBackObj);
		tRnewIssuePolSchema.setIssueType(tQuest);
		tRnewIssuePolSchema.setQuestionObjType(tQuestionObjValue);
			tRnewIssuePolSchema.setQuestionObj(tQuestionObj);
		tRnewIssuePolSchema.setQuestionObjName(tQuestionObjName);
		tRnewIssuePolSchema.setNeedPrint(tNeedPrintFlag); //Ĭ��Ϊ��ӡ
		tRnewIssuePolSchema.setStandbyFlag2(hiddenQuestionPrint); //�Ƿ񱣵���ӡ�ֶ�23
		}
		else if(tQuestionOperator.equals("DELETE"))
		{
			tRnewIssuePolSchema.setProposalContNo(hiddenProposalContNo);
			tRnewIssuePolSchema.setSerialNo(hiddenQuestionSeq);
		}
		else if(tQuestionOperator.equals("UPDATE"))
		{
			tRnewIssuePolSchema.setIssueCont(tContent);
			tRnewIssuePolSchema.setOperatePos(tFlag);
			tRnewIssuePolSchema.setBackObjType(tBackObj);
			tRnewIssuePolSchema.setIssueType(tQuest);
			tRnewIssuePolSchema.setQuestionObjType(tQuestionObjValue);
			tRnewIssuePolSchema.setQuestionObj(tQuestionObj);
			tRnewIssuePolSchema.setQuestionObjName(tQuestionObjName);
			tRnewIssuePolSchema.setNeedPrint(tNeedPrintFlag); //Ĭ��Ϊ��ӡ
			loggerDebug("RnewQuestInputChk","tNeedPrintFlag:"+tNeedPrintFlag);
			tRnewIssuePolSchema.setStandbyFlag2(hiddenQuestionPrint); //�Ƿ񱣵���ӡ�ֶ�23
			tRnewIssuePolSchema.setProposalContNo(hiddenProposalContNo);
			tRnewIssuePolSchema.setSerialNo(hiddenQuestionSeq);
			
			String tRadio[] = request.getParameterValues("InpQuestGridSel");  
                         //������ʽ=�� Inp+MulLine������+Sel�� 
      String tGridNeedFlag [] = request.getParameterValues("QuestGrid9"); //�õ���7�е�����ֵ                   
      for (int index=0; index< tRadio.length;index++)
      {
      	loggerDebug("RnewQuestInputChk","index:"+index);
        if(tRadio[index].equals("1"))
        {
           loggerDebug("RnewQuestInputChk","���б�ѡ��"+tGridNeedFlag[index]);
           //tRnewIssuePolSchema.setNeedPrint(tGridNeedFlag[index]);
           
        }
        if(tRadio[index].equals("0"))
           loggerDebug("RnewQuestInputChk","����δ��ѡ��");
      }

		} 
	
				
		tRnewIssuePolSet.add(tRnewIssuePolSchema);			    
	}
	
	loggerDebug("RnewQuestInputChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		loggerDebug("RnewQuestInputChk","contno="+tContNo);

		tTransferData.setNameAndValue("MissionID", tMissionID);
		tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
		tTransferData.setNameAndValue("ActivityID", tActivityID);
		tTransferData.setNameAndValue("QuesionnaireH",tHealthCheck);
		tTransferData.setNameAndValue("QuesionnaireFO",tFinanceO);
		tTransferData.setNameAndValue("QuestionnaireO",tOccupation);
		tTransferData.setNameAndValue("Questionnaire",tQuestionnaire);

		
		tVData.add( tLCContSet );
		tVData.add( tRnewIssuePolSet);
		tVData.add( tG );
		tVData.add(tTransferData);
		
		
		// ���ݴ���
		loggerDebug("RnewQuestInputChk","sdfsdf");
		RnewQuestInputChkUI tRnewQuestInputChkUI   = new RnewQuestInputChkUI();
		if (tRnewQuestInputChkUI.submitData(tVData,tQuestionOperator) == false)
		{
			int n = tRnewQuestInputChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �����¼��ʧ�ܣ�ԭ����: " + tRnewQuestInputChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tRnewQuestInputChkUI.mErrors;
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
