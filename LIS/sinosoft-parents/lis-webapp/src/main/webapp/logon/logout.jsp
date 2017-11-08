<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//******************************************************
// 程序名称：Logout.jsp
// 程序功能:：
// 最近更新人：DingZhong
// 最近更新日期：2002-12-22
//******************************************************
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.lis.logon.logoutUI"%>
<%
session.invalidate();
//loggerDebug("logout","start logout");
//loggerDebug("logout","start clear data...");
try {
	GlobalInput tG1 = new GlobalInput();
	tG1 = (GlobalInput)session.getValue("GI");
//	loggerDebug("logout",tG1.ComCode);
	//VData inputData = new VData();
	//inputData.addElement(tG1);
	//logoutUI tlogoutUI = new logoutUI(); 
//	tlogoutUI.submitData(inputData,"LogOutProcess");
//	loggerDebug("logout","completed clear data");
}
catch (Exception exception)
{
	loggerDebug("logout","Log out error ...");
}
%>
<script language=javascript>
try {
window.parent.frames["fraMenu"].destroy();

}
catch (ex) {
}
session = null;
top.window.location ="../indexlis.jsp";
//核心系统与兼业需同时支持
//top.window.navigate(top.window.location);
</script>  
