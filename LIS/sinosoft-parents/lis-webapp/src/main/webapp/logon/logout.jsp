<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//******************************************************
// �������ƣ�Logout.jsp
// ������:��
// ��������ˣ�DingZhong
// ����������ڣ�2002-12-22
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
//����ϵͳ���ҵ��ͬʱ֧��
//top.window.navigate(top.window.location);
</script>  
