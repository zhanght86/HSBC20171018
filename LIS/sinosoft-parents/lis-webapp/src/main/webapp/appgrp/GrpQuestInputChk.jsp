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
	LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();

	String tGrpContNo = request.getParameter("GrpContNoHide");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("BackObj");
	String tQuest = request.getParameter("Quest");
	String tContent = request.getParameter("Content");
	
	//String tcont[] = request.getParameterValues("QuestGrid3");
	//String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("GrpQuestInputChk","grpcontno:"+tGrpContNo);
	loggerDebug("GrpQuestInputChk","operatepos:"+tFlag);
	loggerDebug("GrpQuestInputChk","backobj:"+tBackObj);
	loggerDebug("GrpQuestInputChk","issuetype:"+tQuest);
	loggerDebug("GrpQuestInputChk","content:"+tContent);
	
	boolean flag = true;
	//int feeCount = tcont.length;

	if (tGrpContNo.equals("") || tFlag.equals("") || tBackObj.equals("") || tContent.equals("") || tQuest.equals(""))
	{
		Content = "数据录入不完全或传输失败!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("GrpQuestInputChk","失败");
	}
	else
	{
		loggerDebug("GrpQuestInputChk","begin");

		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
 			
		tLCGrpContSchema.setGrpContNo(tGrpContNo);
	    		
    	tLCGrpContSet.add( tLCGrpContSchema );
	    				
		LCGrpIssuePolSchema tLCGrpIssuePolSchema = new LCGrpIssuePolSchema();
		
		loggerDebug("GrpQuestInputChk","operatepos:"+tFlag);
		tLCGrpIssuePolSchema.setIssueCont(tContent);
		tLCGrpIssuePolSchema.setOperatePos(tFlag);
		tLCGrpIssuePolSchema.setBackObjType(tBackObj);
		tLCGrpIssuePolSchema.setIssueType(tQuest);
			
		tLCGrpIssuePolSet.add(tLCGrpIssuePolSchema);			    
	}
	
	loggerDebug("GrpQuestInputChk","flag:"+flag);
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCGrpContSet );
		tVData.add( tLCGrpIssuePolSet);
		tVData.add( tG );
		
		// 数据传输
		String busiName="cbcheckgrpGrpQuestInputChkUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//GrpQuestInputChkUI tGrpQuestInputChkUI   = new GrpQuestInputChkUI();
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " 操作成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 操作失败，原因是:" + tError.getFirstError();
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
