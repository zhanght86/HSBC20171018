<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：PayReturnFromBankSave.jsp
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
  loggerDebug("PayReturnFromBankSave","\n\n---PayReturnFromBankSave Start---");
  
  PayReturnFromBankUI payReturnFromBankUI1 = new PayReturnFromBankUI();

  TransferData transferData1 = new TransferData();
  transferData1.setNameAndValue("totalMoney", request.getParameter("TotalMoney"));
  
  LYReturnFromBankSchema tLYReturnFromBankSchema = new LYReturnFromBankSchema();
  tLYReturnFromBankSchema.setSerialNo(request.getParameter("serialNo"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
    
  VData tVData = new VData();
  tVData.add(transferData1);
  tVData.add(tLYReturnFromBankSchema);
  tVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";

  if (!payReturnFromBankUI1.submitData(tVData, "PAYMONEY")) {
    VData rVData = payReturnFromBankUI1.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("PayReturnFromBankSave",Content + "\n" + FlagStr + "\n---PayReturnFromBankSave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	parent.fraInterface.initQuery();
</script>
</html>
