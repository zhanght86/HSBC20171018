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
  loggerDebug("CustomerQualityManageSave","\n\n---UWCustomerQualitySave Start---");
//  loggerDebug("CustomerQualityManageSave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LDPersonSchema tLDPersonSchema = new LDPersonSchema();
  
  String tCustomerNo = request.getParameter("CustomerNoSel");
  String tCQualityFlag = request.getParameter("CQualityFlag");
  //String tCReasonType = request.getParameter("CReasonType");
  String tReason = request.getParameter("Reason");
  
  tLDPersonSchema.setCustomerNo(tCustomerNo);
  tLDPersonSchema.setBlacklistFlag(tCQualityFlag);
  tLDPersonSchema.setRemark(tReason);
  
  loggerDebug("CustomerQualityManageSave","tCustomerNo:"+tCustomerNo);
  loggerDebug("CustomerQualityManageSave","tCQualityFlag:"+tCQualityFlag);
 // loggerDebug("CustomerQualityManageSave","tCReasonType:"+tCReasonType);
  loggerDebug("CustomerQualityManageSave","tReason:"+tReason);

  VData inVData = new VData();
  inVData.add(tLDPersonSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  //UWQualityManageUI tUWQualityManageUI = new UWQualityManageUI();
  String busiName="cbcheckUWQualityManageUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  if (!tBusinessDelegate.submitData(inVData, "CUSTOMER&&UPDATE",busiName)) {
    VData rVData = tBusinessDelegate.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("CustomerQualityManageSave",Content + "\n" + FlagStr + "\n---UWCustomerQualitySave End---\n\n");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

