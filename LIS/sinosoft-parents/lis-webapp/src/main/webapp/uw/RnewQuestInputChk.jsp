<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestInputChk.jsp
//程序功能：问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.xb.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
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
	//增加是否下发选择
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
	
		Content = "问题件数据录入不完全或传输失败!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("RnewQuestInputChk","失败");
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
		tRnewIssuePolSchema.setNeedPrint(tNeedPrintFlag); //默认为打印
		tRnewIssuePolSchema.setStandbyFlag2(hiddenQuestionPrint); //是否保单打印字段23
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
			tRnewIssuePolSchema.setNeedPrint(tNeedPrintFlag); //默认为打印
			loggerDebug("RnewQuestInputChk","tNeedPrintFlag:"+tNeedPrintFlag);
			tRnewIssuePolSchema.setStandbyFlag2(hiddenQuestionPrint); //是否保单打印字段23
			tRnewIssuePolSchema.setProposalContNo(hiddenProposalContNo);
			tRnewIssuePolSchema.setSerialNo(hiddenQuestionSeq);
			
			String tRadio[] = request.getParameterValues("InpQuestGridSel");  
                         //参数格式=” Inp+MulLine对象名+Sel” 
      String tGridNeedFlag [] = request.getParameterValues("QuestGrid9"); //得到第7列的所有值                   
      for (int index=0; index< tRadio.length;index++)
      {
      	loggerDebug("RnewQuestInputChk","index:"+index);
        if(tRadio[index].equals("1"))
        {
           loggerDebug("RnewQuestInputChk","该行被选中"+tGridNeedFlag[index]);
           //tRnewIssuePolSchema.setNeedPrint(tGridNeedFlag[index]);
           
        }
        if(tRadio[index].equals("0"))
           loggerDebug("RnewQuestInputChk","该行未被选中");
      }

		} 
	
				
		tRnewIssuePolSet.add(tRnewIssuePolSchema);			    
	}
	
	loggerDebug("RnewQuestInputChk","flag:"+flag);
  	if (flag == true)
  	{
		// 准备传输数据 VData
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
		
		
		// 数据传输
		loggerDebug("RnewQuestInputChk","sdfsdf");
		RnewQuestInputChkUI tRnewQuestInputChkUI   = new RnewQuestInputChkUI();
		if (tRnewQuestInputChkUI.submitData(tVData,tQuestionOperator) == false)
		{
			int n = tRnewQuestInputChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 问题件录入失败，原因是: " + tRnewQuestInputChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tRnewQuestInputChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 问题件录入成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 问题件录入失败，原因是:" + tError.getFirstError();
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
