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
	
	loggerDebug("MSQuestInputChk","ContNo:"+tContNo);
	loggerDebug("MSQuestInputChk","operatepos:"+tFlag);
	loggerDebug("MSQuestInputChk","backobj:"+tBackObj);
	loggerDebug("MSQuestInputChk","issuetype:"+tQuest);
	loggerDebug("MSQuestInputChk","content"+tContent);
	
	loggerDebug("MSQuestInputChk","tHealthCheck:"+tHealthCheck);
	loggerDebug("MSQuestInputChk","tOccupation"+tOccupation);
	loggerDebug("MSQuestInputChk","tFinanceO"+tFinanceO);
	loggerDebug("MSQuestInputChk","tQuestionnaire"+tQuestionnaire);
	
	loggerDebug("MSQuestInputChk","tMissionID"+tMissionID);
	loggerDebug("MSQuestInputChk","tSubMissionID"+tSubMissionID);
	loggerDebug("MSQuestInputChk","tActivityID"+tActivityID);
	
	boolean flag = true;
	//int feeCount = tcont.length;

	if (!tQuestionOperator.equals("UPDATE")&&!tQuestionOperator.equals("DELETE")&&("".equals(tContNo) || "".equals(tFlag) || "".equals(tBackObj) || "".equals(tContent.trim()) || "".equals(tQuest.trim())))
	{
	   loggerDebug("MSQuestInputChk","fdfdfdfdf");
	
		Content = "���������¼�벻��ȫ����ʧ��!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("MSQuestInputChk","ʧ��");
	}
	
	else
	{
		loggerDebug("MSQuestInputChk","begin");

		LCContSchema tLCContSchema = new LCContSchema();
 			
		tLCContSchema.setContNo( tContNo);
		tLCContSchema.setState(mFlag);		  
			    		
    	tLCContSet.add( tLCContSchema );
	    				
		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
		
		if(tQuestionOperator.equals("INSERT"))
		{
		loggerDebug("MSQuestInputChk","operatepos:"+tFlag);
		tLCIssuePolSchema.setIssueCont(tContent);
		tLCIssuePolSchema.setOperatePos(tFlag);
		tLCIssuePolSchema.setBackObjType(hiddenBackObj);
		tLCIssuePolSchema.setIssueType(tQuest);
		tLCIssuePolSchema.setQuestionObjType(tQuestionObjValue);
			tLCIssuePolSchema.setQuestionObj(tQuestionObj);
		tLCIssuePolSchema.setQuestionObjName(tQuestionObjName);
		tLCIssuePolSchema.setNeedPrint(tNeedPrintFlag);//Ĭ��Ϊ��ӡ
		tLCIssuePolSchema.setStandbyFlag2(hiddenQuestionPrint);//�Ƿ񱣵���ӡ�ֶ�23
		}
		else if(tQuestionOperator.equals("DELETE"))
		{
			tLCIssuePolSchema.setProposalContNo(hiddenProposalContNo);
			tLCIssuePolSchema.setSerialNo(hiddenQuestionSeq);
		}
		else if(tQuestionOperator.equals("UPDATE"))
		{
			tLCIssuePolSchema.setIssueCont(tContent);
			tLCIssuePolSchema.setOperatePos(tFlag);
			tLCIssuePolSchema.setBackObjType(hiddenBackObj);
			tLCIssuePolSchema.setIssueType(tQuest);
			tLCIssuePolSchema.setQuestionObjType(tQuestionObjValue);
			tLCIssuePolSchema.setQuestionObj(tQuestionObj);
			tLCIssuePolSchema.setQuestionObjName(tQuestionObjName);
			tLCIssuePolSchema.setNeedPrint(tNeedPrintFlag);//Ĭ��Ϊ��ӡ
			loggerDebug("MSQuestInputChk","tNeedPrintFlag:"+tNeedPrintFlag);
			tLCIssuePolSchema.setStandbyFlag2(hiddenQuestionPrint);//�Ƿ񱣵���ӡ�ֶ�23
			tLCIssuePolSchema.setProposalContNo(hiddenProposalContNo);
			tLCIssuePolSchema.setSerialNo(hiddenQuestionSeq);
			
			String tRadio[] = request.getParameterValues("InpQuestGridSel");  
                         //������ʽ=�� Inp+MulLine������+Sel�� 
      String tGridNeedFlag [] = request.getParameterValues("QuestGrid9"); //�õ���7�е�����ֵ                   
      for (int index=0; index< tRadio.length;index++)
      {
      	loggerDebug("MSQuestInputChk","index:"+index);
        if(tRadio[index].equals("1"))
        {
           loggerDebug("MSQuestInputChk","���б�ѡ��"+tGridNeedFlag[index]);
           //tLCIssuePolSchema.setNeedPrint(tGridNeedFlag[index]);
           
        }
        if(tRadio[index].equals("0"))
           loggerDebug("MSQuestInputChk","����δ��ѡ��");
      }

		} 
	
				
		tLCIssuePolSet.add(tLCIssuePolSchema);			    
	}
	
	loggerDebug("MSQuestInputChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		loggerDebug("MSQuestInputChk","contno="+tContNo);

		tTransferData.setNameAndValue("MissionID", tMissionID);
		tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
		tTransferData.setNameAndValue("ActivityID", tActivityID);
		tTransferData.setNameAndValue("QuesionnaireH",tHealthCheck);
		tTransferData.setNameAndValue("QuesionnaireFO",tFinanceO);
		tTransferData.setNameAndValue("QuestionnaireO",tOccupation);
		tTransferData.setNameAndValue("Questionnaire",tQuestionnaire);

		
		tVData.add( tLCContSet );
		tVData.add( tLCIssuePolSet);
		tVData.add( tG );
		tVData.add(tTransferData);
		
		
		// ���ݴ���
		loggerDebug("MSQuestInputChk","sdfsdf");
		//QuestInputChkUI tQuestInputChkUI   = new QuestInputChkUI();
		String busiName="cbcheckQuestInputChkUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        
		if (tBusinessDelegate.submitData(tVData,tQuestionOperator,busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �����¼��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr.equals("Fail"))
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
