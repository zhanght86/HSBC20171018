<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PlanWorkFlowSave.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>

  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%

  CErrors tError = null;
  String Content = "";
  String FlagStr = "";
  String tOperate=request.getParameter("hideOperate");
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ProcessID",request.getParameter("ProcessID"));
	tTransferData.setNameAndValue("Version",request.getParameter("Version"));
	

  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(tTransferData);
  BusinessDelegate tBusinessDelegate; 
  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(tVData, tOperate, "ProcessDefUI"))
  {
	  Content = " 删除失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
	 	FlagStr = "Fail";
	}
	else
	{
		Content = " 删除成功! ";
	  FlagStr = "Succ";
  }  
 

  %>
  <html>
<script language="javascript">
 parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>
