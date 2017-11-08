<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：UWCustomerQualitySave.jsp
//程序功能：
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：    更新人      更新日期      更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%
  loggerDebug("AgentQualityManageSave","\n\n---UWCustomerQualitySave Start---");
  loggerDebug("AgentQualityManageSave","PrtNo:" + request.getParameter("PrtNo"));
  loggerDebug("AgentQualityManageSave","ContNo:" + request.getParameter("ContNo"));
//  loggerDebug("AgentQualityManageSave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LATreeSchema tLATreeSchema =new LATreeSchema();
  
  String tAgentCode = request.getParameter("AgentCodeSel");
  String tQualityState = request.getParameter("QualityState");
  String tReasonType = request.getParameter("ReasonType");
  String tReason = request.getParameter("Reason");
  
  tLATreeSchema.setAgentCode(tAgentCode);
  tLATreeSchema.setBlackLisFlag(tQualityState);
  tLATreeSchema.setReasonType(tReasonType);
  tLATreeSchema.setReason(tReason);
  
  loggerDebug("AgentQualityManageSave","tAgentCode:"+tAgentCode);
  loggerDebug("AgentQualityManageSave","tQualityFlag:"+tQualityState);
  loggerDebug("AgentQualityManageSave","tReasonType:"+tReasonType);
  loggerDebug("AgentQualityManageSave","tReason:"+tReason);

  VData inVData = new VData();
  inVData.add(tLATreeSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  //UWQualityManageUI tUWQualityManageUI = new UWQualityManageUI();
 String busiName="cbcheckUWQualityManageUI";
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //if (!tUWQualityManageUI.submitData(inVData, "AGENT&&UPDATE")) {
if (!tBusinessDelegate.submitData(inVData, "AGENT&&UPDATE",busiName)) {
    VData rVData = tBusinessDelegate.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("AgentQualityManageSave",Content + "\n" + FlagStr + "\n---UWAgentQualitySave End---\n\n");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

