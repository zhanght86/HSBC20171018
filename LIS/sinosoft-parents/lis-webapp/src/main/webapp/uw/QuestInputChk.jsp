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
  <%@page import="com.sinosoft.lis.tb.*"%>
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
	
		Content = "问题件数据录入不完全或传输失败!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("QuestInputChk","失败");
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
		// 准备传输数据 VData
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
		
		
		// 数据传输
		loggerDebug("QuestInputChk","sdfsdf");
		
		String busiName="cbcheckQuestInputChkUI";
	    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   
		//QuestInputChkUI tQuestInputChkUI   = new QuestInputChkUI();
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 问题件录入失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
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
