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
  
<%
  loggerDebug("GrpCustomerQualityManageSave","\n\n---UWCustomerQualitySave Start---");
//  loggerDebug("GrpCustomerQualityManageSave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LDPersonSchema tLDPersonSchema = new LDPersonSchema();
  LDGrpSchema tLDGrpSchema=new LDGrpSchema();
  
  String tCustomerNo = request.getParameter("CustomerNoSel");
  String tCQualityFlag = request.getParameter("CQualityFlag");
  //String tCReasonType = request.getParameter("CReasonType");
  String tReason = request.getParameter("Reason");
  
  //tLDPersonSchema.setCustomerNo(tCustomerNo);
  //tLDPersonSchema.setBlacklistFlag(tCQualityFlag);
  //tLDPersonSchema.setRemark(tReason);
  tLDGrpSchema.setCustomerNo(tCustomerNo);
  tLDGrpSchema.setBlacklistFlag(tCQualityFlag);
  tLDGrpSchema.setBlackListReason("00"); //没有编码 暂时用00 也许以后会增加
  tLDGrpSchema.setRemark(tReason);
  
  loggerDebug("GrpCustomerQualityManageSave","tCustomerNo:"+tCustomerNo);
  loggerDebug("GrpCustomerQualityManageSave","tCQualityFlag:"+tCQualityFlag);
 // loggerDebug("GrpCustomerQualityManageSave","tCReasonType:"+tCReasonType);
  loggerDebug("GrpCustomerQualityManageSave","tReason:"+tReason);

  VData inVData = new VData();
  inVData.add(tLDGrpSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  UWQualityManageUI tUWQualityManageUI = new UWQualityManageUI();

  if (!tUWQualityManageUI.submitData(inVData, "CUSTOMER||QUALITY")) {
    VData rVData = tUWQualityManageUI.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("GrpCustomerQualityManageSave",Content + "\n" + FlagStr + "\n---UWCustomerQualitySave End---\n\n");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

