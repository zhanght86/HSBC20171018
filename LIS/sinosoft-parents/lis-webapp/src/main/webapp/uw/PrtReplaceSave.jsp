
<%
//程序名称：PrtReplaceSave.jsp
//程序功能：保单修改
//创建日期：2006-07-18 11:10:36
//创建人  ：ck
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //输出参数
  	CErrors tError = null;
  	String FlagStr = "Fail";
  	String Content = "";
	TransferData tTransferData=new TransferData();
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

  	if(tG == null) {
		out.println("session has expired");
		return;
	}		
	tTransferData.setNameAndValue("OldPrtNo",request.getParameter("OldPrtNoHide"));
	tTransferData.setNameAndValue("NewPrtNo",request.getParameter("NewPrtNo"));	     	     	     	

	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.add( tTransferData );
	tVData.add( tG );

	// 数据传输
	PrtReplaceUI tPrtReplaceUI   = new PrtReplaceUI();	
	loggerDebug("PrtReplaceSave","before PrtReplaceUI");
	if (tPrtReplaceUI.submitData(tVData,"PrtReplace") == false)
	{
		int n = tPrtReplaceUI.mErrors.getErrorCount();
		for (int i = 0; i < n; i++)
		Content = " 印刷号替换失败，原因是: " + tPrtReplaceUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}
	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "Fail")
	{
	    tError = tPrtReplaceUI.mErrors;
		if (!tError.needDealError())
		{
			Content = " 印刷号替换成功! ";
		    	FlagStr = "Succ";
		}
		else                                                                           
		{
			Content = " 印刷号替换失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
