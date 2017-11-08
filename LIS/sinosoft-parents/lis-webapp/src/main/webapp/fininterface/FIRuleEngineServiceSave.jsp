<%
//程序名称 :FIRuleEngineServiceSave.jsp
//
%>

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.lis.fininterface.checkdata.*" %>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("FIRuleEngineServiceSave","1开始执行Save页面");
  //FIRuleEngineService1 mFIRuleEngineService1 = new FIRuleEngineService1();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //获得用户信息
  CErrors tError = null;
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String Operator = "difference";


  
  VData tVData = new VData();
  try
  {
  	tVData.clear();
		tVData.addElement(tG);
		tVData.addElement(request.getParameter("StartDate"));
		tVData.addElement(request.getParameter("EndDate"));
		tVData.addElement(request.getParameter("VersionNo"));
		tVData.addElement(request.getParameter("callpoint"));
		tVData.addElement(request.getParameter("StartDay"));
		tVData.addElement(request.getParameter("EndDay"));
    uiBusinessDelegate.submitData(tVData,Operator,"FIRuleEngineService1");
  }
  catch(Exception ex)
  {
    Content = "失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
     tError = uiBusinessDelegate.getCErrors();
    	   if (!tError.needDealError())
    		{     
    				Content = "操作已成功!"+tError.getFirstError();
    				FlagStr = "Succ";
    		}
   	  else
    		{
    			Content = " 操作失败，原因是:" + tError.getFirstError();
    			FlagStr = "Fail";
    		}
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
