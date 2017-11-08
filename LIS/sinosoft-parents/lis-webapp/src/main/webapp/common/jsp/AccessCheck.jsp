<!--
*******************************************************
* 程序名称：AccessCheck.jsp
* 程序功能：页面访问校验页面
* 创建日期：2002-11-25
* 更新记录：  更新人    更新日期     更新原因/内容
*******************************************************
-->
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
try
{

	   StringBuffer strURL = request.getRequestURL();

	  GlobalInput tG1 = (GlobalInput)session.getValue("GI");
	  String  userCode = tG1.Operator;

		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("UserCode", userCode);
    mTransferData.setNameAndValue("strURL", strURL.toString());
    
    //VData
    VData tVData = new VData();
    tVData.add(tG1);
    tVData.add(mTransferData);	
	
   String sOperate = ""; 
   String busiName="Access";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

	 boolean canAccess = tBusinessDelegate.submitData(tVData,sOperate,busiName);
	 if (!canAccess)
	 {
	  	out.println("您无权访问此网页");
		  return;
	 }
}
catch(Exception exception){
	String ContentErr = " exception:请您重新登录！";
	loggerDebug("AccessCheck",ContentErr);
	out.println("网页出错，请您重新登录");
%>
<script language=javascript>
	top.window.location ="../indexlis.jsp";
</script>
<%
	return;
}
%>
