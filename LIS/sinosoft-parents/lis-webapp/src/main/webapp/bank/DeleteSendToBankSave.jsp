<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：DeleteSendToBankSave.jsp
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
  
<%
  loggerDebug("DeleteSendToBankSave","\n\n---DeleteSendToBankSave Start---");
  loggerDebug("DeleteSendToBankSave","TempFeeNo:" + request.getParameter("TempFeeNo"));
  loggerDebug("DeleteSendToBankSave","Action:" + request.getParameter("Action"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
  tLJTempFeeClassSchema.setTempFeeNo(request.getParameter("TempFeeNo"));
  LJTempFeeClassSet inLJTempFeeClassSet = new LJTempFeeClassSet();
  inLJTempFeeClassSet.add(tLJTempFeeClassSchema);

  VData inVData = new VData();
  inVData.add(inLJTempFeeClassSet);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  DeleteSendToBankUI DeleteSendToBankUI1 = new DeleteSendToBankUI();

  if (!DeleteSendToBankUI1.submitData(inVData, request.getParameter("Action"))) {
    VData rVData = DeleteSendToBankUI1.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("DeleteSendToBankSave",Content + "\n" + FlagStr + "\n---DeleteSendToBankSave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	parent.fraInterface.easyQueryClick();
</script>
</html>
