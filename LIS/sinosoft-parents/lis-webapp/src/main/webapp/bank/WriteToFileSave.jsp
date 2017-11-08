<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：WriteToFileSave.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bank.*"%>
  <%@page import="com.sinosoft.lis.easyscan.*"%> 
  <%@page import="com.sinosoft.service.*"%> 
<%
  loggerDebug("WriteToFileSave","\n\n---WriteToFileSave Start---");
  
  //WriteToFileUI writeToFileUI1 = new WriteToFileUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate(); 
  EasyScanConfig tEasyScan = EasyScanConfig.getInstance();
  
  LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();
  tLYSendToBankSchema.setSerialNo(request.getParameter("serialNo"));

  TransferData transferData1 = new TransferData();
//  transferData1.setNameAndValue("fileName", request.getParameter("FileName"));
  transferData1.setNameAndValue("appPath", application.getRealPath("/"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
	VData inVData = new VData();

  String Content = "";
  String FlagStr = "";
  int flag = -1;
  String clientUrl = (String)session.getValue("ClientURL");
  String serverUrl = request.getParameter("Url");
  
  StrTool tTool = new StrTool();
  int pos = tTool.getPos(serverUrl,"/",3) + 1;
  String httpServer = serverUrl.substring(0,pos);
  loggerDebug("WriteToFileSave","clientUrl " + clientUrl);
  loggerDebug("WriteToFileSave","serverUrl " + serverUrl);
  loggerDebug("WriteToFileSave","httpServer " + httpServer);
  
  StringBuffer fileUrl = new StringBuffer();
  fileUrl.append(httpServer);
  if (tEasyScan.isForward(clientUrl,fileUrl) == true)
  {
	  serverUrl = new String(fileUrl.append(serverUrl.substring(pos)));
  	loggerDebug("WriteToFileSave","Forward success ");
  }
	loggerDebug("WriteToFileSave","serverUrl------ " + serverUrl);
	
	String action = request.getParameter("fmtransact");
  loggerDebug("WriteToFileSave",action);
	if (action.equals("download"))
	{
		flag = 0;
	}
	else
  {
  	flag = 1;
	  loggerDebug("WriteToFileSave","serialNo:" + request.getParameter("serialNo"));
	  loggerDebug("WriteToFileSave","FileName:" + request.getParameter("FileName"));
	  
	  inVData.add(tLYSendToBankSchema);
	  inVData.add(transferData1);
	  inVData.add(tGlobalInput);
	 
	  //if (!writeToFileUI1.submitData(inVData, "WRITE")) {
	  if (! tBusinessDelegate.submitData(inVData,"WRITE","WriteToFileUI")) {  
	    //VData rVData = writeToFileUI1.getResult();
	    VData rVData = tBusinessDelegate.getResult(); 
	    Content = " 处理失败，原因是:" + (String)rVData.get(0);
	  	FlagStr = "Fail";
	  }
	  else {
	    Content = " 处理成功! ";
	  	FlagStr = "Success";
	  }
	
		loggerDebug("WriteToFileSave",Content + "\n" + FlagStr + "\n---WriteToFileSave End---\n\n");
	}

%>                      
<html>
<script language="javascript">
	
	if (<%=flag%> == 1)
	{
		parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=PubFun.changForJavaScript(Content)%>');
	}
	if ("<%=FlagStr%>" != "Fail")
	{
		parent.fraInterface.downAfterSubmit('<%=serverUrl%>',<%=flag%>);
	}
</script>
</html>
