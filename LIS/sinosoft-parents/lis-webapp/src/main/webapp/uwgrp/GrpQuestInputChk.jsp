<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpQuestInputChk.jsp
//程序功能：团体问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
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
		Content = "数据录入不完全或传输失败!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("GrpQuestInputChk","失败");
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
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCPolSet );
		tVData.add( tLCIssuePolSet);
		tVData.add( tG );
		
		// 数据传输
		QuestInputChkUI tQuestInputChkUI   = new QuestInputChkUI();
		if (tQuestInputChkUI.submitData(tVData,"INSERT") == false)
		{
			int n = tQuestInputChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 自动核保失败，原因是: " + tQuestInputChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tQuestInputChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 人工核保成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 人工核保失败，原因是:" + tError.getFirstError();
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
