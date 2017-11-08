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
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  loggerDebug("UWCustomerQualitySave","\n\n---UWCustomerQualitySave Start---");
  loggerDebug("UWCustomerQualitySave","PrtNo:" + request.getParameter("PrtNo"));
  loggerDebug("UWCustomerQualitySave","ContNo:" + request.getParameter("ContNo"));
//  loggerDebug("UWCustomerQualitySave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LDPersonSchema tLDPersonSchema = new LDPersonSchema();
  tLDPersonSchema.setCustomerNo(request.getParameter("CustomerNo"));
  tLDPersonSchema.setBlacklistFlag(request.getParameter("BlacklistFlagNo"));
  tLDPersonSchema.setRemark(request.getParameter("Remark"));

  VData inVData = new VData();
  inVData.add(tLDPersonSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  String busiName="cbcheckgrpUWQualityManageUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 // UWQualityManageUI tUWQualityManageUI = new UWQualityManageUI();

  if (!tBusinessDelegate.submitData(inVData, "UPDATE",busiName)) {
    VData rVData = tBusinessDelegate.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("UWCustomerQualitySave",Content + "\n" + FlagStr + "\n---UWCustomerQualitySave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

