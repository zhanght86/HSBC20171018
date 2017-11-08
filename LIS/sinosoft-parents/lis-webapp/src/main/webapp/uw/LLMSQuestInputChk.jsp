<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=UTF-8" %>
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
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
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
	LLIssuePolSet tLLIssuePolSet = new LLIssuePolSet();

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
	String tBatNo = request.getParameter("BatNo");
	String tCaseNo = request.getParameter("CaseNo");
	String tImpartQuestFlag = request.getParameter("ImpartQuest");
	
	System.out.println("ContNo:"+tContNo);
	System.out.println("operatepos:"+tFlag);
	System.out.println("backobj:"+tBackObj);
	System.out.println("issuetype:"+tQuest);
	System.out.println("content"+tContent);
	
	System.out.println("tHealthCheck:"+tHealthCheck);
	System.out.println("tOccupation"+tOccupation);
	System.out.println("tFinanceO"+tFinanceO);
	System.out.println("tQuestionnaire"+tQuestionnaire);
	
	System.out.println("tMissionID"+tMissionID);
	System.out.println("tSubMissionID"+tSubMissionID);
	System.out.println("tActivityID"+tActivityID);
	
	boolean flag = true;
	//int feeCount = tcont.length;

	if (!tQuestionOperator.equals("UPDATE")&&!tQuestionOperator.equals("DELETE")&&("".equals(tContNo) || "".equals(tFlag) || "".equals(tBackObj) || "".equals(tContent.trim()) || "".equals(tQuest.trim())))
	{
	   System.out.println("fdfdfdfdf");
	
		Content = ""+bundle.getString(/*问题件数据录入不完全或传输失败!*/"M0000056014")+"";
		FlagStr = "Fail";
		flag = false;
		System.out.println("失败");
	}
	
	else
	{
		System.out.println("begin");

		LCContSchema tLCContSchema = new LCContSchema();
 			
		tLCContSchema.setContNo( tContNo);
		tLCContSchema.setState(mFlag);		  
			    		
    	tLCContSet.add( tLCContSchema );
	    				
		LLIssuePolSchema tLLIssuePolSchema = new LLIssuePolSchema();
		
		if(tQuestionOperator.equals("INSERT"))
		{
		System.out.println("operatepos:"+tFlag);
		tLLIssuePolSchema.setIssueCont(tContent);
		tLLIssuePolSchema.setBatNo(tBatNo);
		tLLIssuePolSchema.setClmNo(tCaseNo);
		tLLIssuePolSchema.setOperatePos(tFlag);
		tLLIssuePolSchema.setBackObjType(hiddenBackObj);
		tLLIssuePolSchema.setIssueType(tQuest);
		tLLIssuePolSchema.setQuestionObjType(tQuestionObjValue);
		tLLIssuePolSchema.setQuestionObj(tQuestionObj);
		tLLIssuePolSchema.setQuestionObjName(tQuestionObjName);
		tLLIssuePolSchema.setNeedPrint(tNeedPrintFlag); //默认为打印
		tLLIssuePolSchema.setStandbyFlag2(hiddenQuestionPrint); //是否保单打印字段23
		}
		else if(tQuestionOperator.equals("DELETE"))
		{
			tLLIssuePolSchema.setProposalContNo(hiddenProposalContNo);
			tLLIssuePolSchema.setSerialNo(hiddenQuestionSeq);
			tLLIssuePolSchema.setBatNo(tBatNo);
			tLLIssuePolSchema.setClmNo(tCaseNo);
		}
		else if(tQuestionOperator.equals("UPDATE"))
		{
			tLLIssuePolSchema.setBatNo(tBatNo);
			tLLIssuePolSchema.setClmNo(tCaseNo);
			tLLIssuePolSchema.setIssueCont(tContent);
			tLLIssuePolSchema.setOperatePos("4");
			tLLIssuePolSchema.setBackObjType(hiddenBackObj);
			tLLIssuePolSchema.setIssueType(tQuest);
			tLLIssuePolSchema.setQuestionObjType(tQuestionObjValue);
			tLLIssuePolSchema.setQuestionObj(tQuestionObj);
			tLLIssuePolSchema.setQuestionObjName(tQuestionObjName);
			tLLIssuePolSchema.setNeedPrint(tNeedPrintFlag); //默认为打印
			System.out.println("tNeedPrintFlag:"+tNeedPrintFlag);
			tLLIssuePolSchema.setStandbyFlag2(hiddenQuestionPrint); //是否保单打印字段23
			tLLIssuePolSchema.setProposalContNo(hiddenProposalContNo);
			tLLIssuePolSchema.setSerialNo(hiddenQuestionSeq);
			
			String tRadio[] = request.getParameterValues("InpQuestGridSel");  
                         //参数格式=” Inp+MulLine对象名+Sel” 
      String tGridNeedFlag [] = request.getParameterValues("QuestGrid9"); //得到第7列的所有值                   
      for (int index=0; index< tRadio.length;index++)
      {
      	System.out.println("index:"+index);
        if(tRadio[index].equals("1"))
        {
           System.out.println("该行被选中"+tGridNeedFlag[index]);
           //tLCIssuePolSchema.setNeedPrint(tGridNeedFlag[index]);
           
        }
        if(tRadio[index].equals("0"))
           System.out.println("该行未被选中");
      }

		} 
	
				
		tLLIssuePolSet.add(tLLIssuePolSchema);			    
	}
	
	System.out.println("flag:"+flag);
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		System.out.println("contno="+tContNo);

		tTransferData.setNameAndValue("MissionID", tMissionID);
		tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
		tTransferData.setNameAndValue("ActivityID", tActivityID);
		tTransferData.setNameAndValue("QuesionnaireH",tHealthCheck);
		tTransferData.setNameAndValue("QuesionnaireFO",tFinanceO);
		tTransferData.setNameAndValue("QuestionnaireO",tOccupation);
		tTransferData.setNameAndValue("Questionnaire",tQuestionnaire);
		tTransferData.setNameAndValue("BatNo",tBatNo);
		tTransferData.setNameAndValue("CaseNo",tCaseNo);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("ImpartQuestFlag",tImpartQuestFlag);

		
		tVData.add( tLCContSet );
		tVData.add( tLLIssuePolSet);
		tVData.add( tG );
		tVData.add(tTransferData);
		
		
		// 数据传输
		System.out.println("sdfsdf");
		/*LLQuestInputChkUI tLLQuestInputChkUI   = new LLQuestInputChkUI();
		if (tLLQuestInputChkUI.submitData(tVData,tQuestionOperator) == false)
		{
			int n = tLLQuestInputChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 问题件录入失败，原因是: " + tLLQuestInputChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tLLQuestInputChkUI.mErrors;
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
		}*/
		String busiName="LLQuestInputChkUI";
		  String mDescType=""+bundle.getString(/*问题件录入*/"M0000053987")+"";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tVData,tQuestionOperator,busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = mDescType+""+bundle.getString(/*失败，原因是:*/"M0000049796")+"" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content = mDescType+""+bundle.getString(/*失败*/"M0000090251")+"";
						FlagStr = "Fail";				
				   }
			  }
			  else
			  {
			     	Content = mDescType+""+bundle.getString(/*成功!*/"M0000055210")+" ";
			      	FlagStr = "Succ";  
			  }
	} 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
