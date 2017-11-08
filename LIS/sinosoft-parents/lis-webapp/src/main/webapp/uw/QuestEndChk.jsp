<%@page contentType="text/html;charset=GBK"%> 
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestEndChk.jsp
//程序功能：问题件查询
//创建日期：2005-03-25 11:10:36
//创建人  ：tuqiang
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
<%

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  
  String No = request.getParameter("HideSerialNo");
  String ReplyResult = request.getParameter("ReplyResult");
  String Contno = request.getParameter("ContNoHide");
  
  
  VData inVData = new VData();
  inVData.add(tGlobalInput);
  inVData.add(No);
  inVData.add(ReplyResult);
  inVData.add(Contno);
  String Content = "";
  String FlagStr = "";
  
  UWQuestionBackUI tUWRequestBackUI = new UWQuestionBackUI();

  if (!tUWRequestBackUI.submitData(inVData, "")) {
    VData rVData = tUWRequestBackUI.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }
%> 
<html>
<script language="javascript">
  try { parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>"); } catch(e) {}
</script>
</html>
