<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：PaySendToBankSave.jsp
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
  <%@page import="com.sinosoft.service.*"%>
  
<%
  loggerDebug("PaySendToBankSave","\n\n---PaySendToBankSave Start---");
  
  //PaySendToBankUI petSendToBankUI1 = new PaySendToBankUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  TransferData transferData1 = new TransferData();
  transferData1.setNameAndValue("startDate", request.getParameter("StartDate"));
  transferData1.setNameAndValue("endDate", request.getParameter("EndDate"));
  transferData1.setNameAndValue("bankCode", request.getParameter("BankCode"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");

  VData tVData = new VData();
  tVData.add(transferData1);
  tVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";

  //if (!petSendToBankUI1.submitData(tVData, "PAYMONEY")) {
  if(!tBusinessDelegate.submitData(tVData,"PAYMONEY","PaySendToBankUI")){
    //VData rVData = petSendToBankUI1.getResult();
    VData rVData = tBusinessDelegate.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("PaySendToBankSave",Content + "\n" + FlagStr + "\n---PaySendToBankSave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
