<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�BQQuestInputChk.jsp
//�����ܣ������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
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
	LPIssuePolSet tLPIssuePolSet = new LPIssuePolSet();

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
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
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
	
	loggerDebug("BQQuestInputChk","ContNo:"+tContNo);
	loggerDebug("BQQuestInputChk","operatepos:"+tFlag);
	loggerDebug("BQQuestInputChk","backobj:"+tBackObj);
	loggerDebug("BQQuestInputChk","issuetype:"+tQuest);
	loggerDebug("BQQuestInputChk","content"+tContent);
	
	loggerDebug("BQQuestInputChk","tHealthCheck:"+tHealthCheck);
	loggerDebug("BQQuestInputChk","tOccupation"+tOccupation);
	loggerDebug("BQQuestInputChk","tFinanceO"+tFinanceO);
	loggerDebug("BQQuestInputChk","tQuestionnaire"+tQuestionnaire);
	
	loggerDebug("BQQuestInputChk","tMissionID"+tMissionID);
	loggerDebug("BQQuestInputChk","tSubMissionID"+tSubMissionID);
	
	boolean flag = true;
	//int feeCount = tcont.length;

	if (!tQuestionOperator.equals("UPDATE")&&!tQuestionOperator.equals("DELETE")&&("".equals(tContNo) || "".equals(tFlag) || "".equals(tBackObj) || "".equals(tContent.trim()) ))
	{
	   loggerDebug("BQQuestInputChk","fdfdfdfdf");
	
		Content = "���������¼�벻��ȫ����ʧ��!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("BQQuestInputChk","ʧ��");
	}
	
	else
	{
		loggerDebug("BQQuestInputChk","begin");

		LCContSchema tLCContSchema = new LCContSchema();
 			
		tLCContSchema.setContNo( tContNo);
		tLCContSchema.setState(mFlag);		  
			    		
    	tLCContSet.add( tLCContSchema );
	    				
		LPIssuePolSchema tLPIssuePolSchema = new LPIssuePolSchema();
		
		if(tQuestionOperator.equals("INSERT"))
		{
		loggerDebug("BQQuestInputChk","operatepos:"+tFlag);
		tLPIssuePolSchema.setEdorNo(tEdorNo);
		tLPIssuePolSchema.setEdorType(tEdorType);
		tLPIssuePolSchema.setIssueCont(tContent);
		tLPIssuePolSchema.setOperatePos(tFlag);
		tLPIssuePolSchema.setBackObjType(tBackObj);
		tLPIssuePolSchema.setIssueType(tQuest);
		/*tLPIssuePolSchema.setQuestionObjType(tQuestionObjValue);
		tLPIssuePolSchema.setQuestionObj(tQuestionObj);
		tLPIssuePolSchema.setQuestionObjName(tQuestionObjName);*/
		tLPIssuePolSchema.setNeedPrint(tNeedPrintFlag); //Ĭ��Ϊ��ӡ
		//tLPIssuePolSchema.setStandbyFlag2(hiddenQuestionPrint); //�Ƿ񱣵���ӡ�ֶ�23
		}
		else if(tQuestionOperator.equals("DELETE"))
		{
			tLPIssuePolSchema.setEdorNo(tEdorNo);
			tLPIssuePolSchema.setEdorType(tEdorType);
			tLPIssuePolSchema.setProposalContNo(hiddenProposalContNo);
			tLPIssuePolSchema.setSerialNo(hiddenQuestionSeq);
		}
		else if(tQuestionOperator.equals("UPDATE"))
		{
			tLPIssuePolSchema.setEdorNo(tEdorNo);
			tLPIssuePolSchema.setEdorType(tEdorType);
			tLPIssuePolSchema.setIssueCont(tContent);
			tLPIssuePolSchema.setOperatePos(tFlag);
			tLPIssuePolSchema.setBackObjType(tBackObj);
			tLPIssuePolSchema.setIssueType(tQuest);
			/*tLPIssuePolSchema.setQuestionObjType(tQuestionObjValue);
			tLPIssuePolSchema.setQuestionObj(tQuestionObj);
			tLPIssuePolSchema.setQuestionObjName(tQuestionObjName);*/
			tLPIssuePolSchema.setNeedPrint(tNeedPrintFlag); //Ĭ��Ϊ��ӡ
			loggerDebug("BQQuestInputChk","tNeedPrintFlag:"+tNeedPrintFlag);
			//tLPIssuePolSchema.setStandbyFlag2(hiddenQuestionPrint); //�Ƿ񱣵���ӡ�ֶ�23
			tLPIssuePolSchema.setProposalContNo(hiddenProposalContNo);
			tLPIssuePolSchema.setSerialNo(hiddenQuestionSeq);
			
			String tRadio[] = request.getParameterValues("InpQuestGridSel");  
                         //������ʽ=�� Inp+MulLine������+Sel�� 
      String tGridNeedFlag [] = request.getParameterValues("QuestGrid9"); //�õ���7�е�����ֵ                   
      for (int index=0; index< tRadio.length;index++)
      {
      	loggerDebug("BQQuestInputChk","index:"+index);
        if(tRadio[index].equals("1"))
        {
           loggerDebug("BQQuestInputChk","���б�ѡ��"+tGridNeedFlag[index]);
           //tLPIssuePolSchema.setNeedPrint(tGridNeedFlag[index]);
           
        }
        if(tRadio[index].equals("0"))
           loggerDebug("BQQuestInputChk","����δ��ѡ��");
      }

		} 
	
				
		tLPIssuePolSet.add(tLPIssuePolSchema);			    
	}
	
	loggerDebug("BQQuestInputChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		loggerDebug("BQQuestInputChk","contno="+tContNo);

		tTransferData.setNameAndValue("ContNo", tContNo);
		tTransferData.setNameAndValue("EdorNo", tEdorNo);
		tTransferData.setNameAndValue("EdorType", tEdorType);
		tTransferData.setNameAndValue("BusiType", "1002");
		tVData.add( tLPIssuePolSet);
		tVData.add( tTransferData);
		tVData.add( tG );
		
		
		// ���ݴ���
		loggerDebug("BQQuestInputChk","sdfsdf");
		//BQQuestInputChkUI tBQQuestInputChkUI   = new BQQuestInputChkUI();
		 String busiName="BQQuestInputChkUI";
	 	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData(tVData,tQuestionOperator,busiName) == false)
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
