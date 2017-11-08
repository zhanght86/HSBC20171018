<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestReplyChk.jsp
//程序功能：问题件回复
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
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
	//String tFlag = request.getParameter("Flag");
	//String tBackObj = request.getParameter("Quest");
	//String treply = request.getParameter("ReplyResult");
	//String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //机构问题件回复完毕标志
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContent = "";
	loggerDebug("QuestMissionChk","tMissionID="+tMissionID);
	loggerDebug("QuestMissionChk","tSubMissionID="+tSubMissionID);
	
	//String tcont[] = request.getParameterValues("QuestGrid3");
	//String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("QuestMissionChk","contno:"+tContNo);
	//loggerDebug("QuestMissionChk","flag:"+tFlag);
//	loggerDebug("QuestMissionChk","reply:"+treply);
	//loggerDebug("QuestMissionChk","issuetype:"+tBackObj);
	//loggerDebug("QuestMissionChk","tSerialNo:"+tSerialNo);
	//add by lzf 2013-03-07
	String tActivityID = request.getParameter("ActivityID");
	
	loggerDebug("QuestMissionChk","tActivityID:"+tActivityID);
	boolean flag = true;
	
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("ActivityID",tActivityID);
		
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tG );
		
		//TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
		//String busiName="tbTbWorkFlowUI";
		   String busiName = "WorkFlowUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   
				//if (tBusinessDelegate.submitData(tVData,"0000001020",busiName) == false)
				if (tBusinessDelegate.submitData(tVData,"execute",busiName) == false)	//end by lzf 2013-03-07
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " 机构问题件回复成功 ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 机构问题件失败，原因是:" + tError.getFirstError();
				    	FlagStr = "Fail";
				    }
				  }		
%>                      
<html>
<script language="javascript">
	top.opener.initForm();
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
