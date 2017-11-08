<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ReadFromFileSave.jsp
//程序功能：
//创建日期：2009-9-2 11:10:36
//创建人  ：杨林海
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bank.*"%>
  <%@page import="java.io.*"%>
  <%@page import="com.sinosoft.service.*"%>
	<%@page language="java" import="com.jspsmart.upload.*"%>
  
<%
  loggerDebug("CancelReadFromFileSave","\n\n---ReadFromFileSave Start---");
  
  
	String serialNo = request.getParameterValues("BankGrid3")[0];
	String bankCode = request.getParameterValues("BankGrid1")[0];
	String backReason = request.getParameter("backReason");
	
	loggerDebug("CancelReadFromFileSave","SerialNo="+serialNo);
	loggerDebug("CancelReadFromFileSave","BankCode="+bankCode);
	loggerDebug("CancelReadFromFileSave","backReason="+backReason);
		
	String Content = "";
	String FlagStr = "";

	try	{
	  //CancelReadFromFileUI tCancelReadFromFileUI = new CancelReadFromFileUI();
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();  
    
	  TransferData transferData1 = new TransferData();
  	transferData1.setNameAndValue("serialNo", serialNo);
  	transferData1.setNameAndValue("bankCode", bankCode);
  	transferData1.setNameAndValue("backReason", backReason);
  	GlobalInput tGlobalInput = new GlobalInput(); 
  	tGlobalInput = (GlobalInput)session.getValue("GI");

  	VData inVData = new VData();
  	inVData.add(transferData1);
  	inVData.add(tGlobalInput);
	  
	  //if (!tCancelReadFromFileUI.submitData(inVData, "READ")) {
	  if (!tBusinessDelegate.submitData(inVData,"READ","CancelReadFromFileUI")) {  
    	//Content = " 处理失败，原因是:" + tCancelReadFromFileUI.mErrors.getFirstError();
    	Content = " 处理失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();              
  		FlagStr = "Fail";
  	}
  	else {
    	Content = " 处理成功! ";
    	//if(tCancelReadFromFileUI.mErrors.needDealError()){
	    //	Content+="但"+tCancelReadFromFileUI.mErrors.getFirstError();
	    
	    if(tBusinessDelegate.getCErrors().needDealError()){            
    		    	Content+="但"+tBusinessDelegate.getCErrors().getFirstError();}
  		FlagStr = "Succ";
  	}
		loggerDebug("CancelReadFromFileSave",Content + "\n" + FlagStr + "\n---ReadFromFileSave End---\n\n");
	} 
	catch (Exception e) { 
		e.printStackTrace();
		Content = " 处理失败，原因是:" + e.getMessage();
	  	FlagStr = "Fail";
	}

%>                      

<%@page import="java.io.File"%><html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=PubFun.changForJavaScript(Content)%>');
</script>
</html>
