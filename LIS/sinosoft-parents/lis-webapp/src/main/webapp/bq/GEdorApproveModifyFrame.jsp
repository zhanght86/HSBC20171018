
<%
//程序名称：PEdorApproveModifyFrame.jsp
//程序功能：个单保全复核修改
//创建日期：2005-11-10 11:01
//创建人  ：zhangtao
//更新记录：
//更新人：
//更新日期:
//更新原因/内容:
%>
<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<title>团单保全复核修改</title>
<script language="javascript">

	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);

function focusMe()
{
  window.focus();
}
</script>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
    
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">

	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<%
			String szSrc = request.getParameter("Interface");	
			szSrc += "?EdorAcceptNo=" + request.getParameter("EdorAcceptNo");	
			szSrc += "&MissionID=" + request.getParameter("MissionID");
			szSrc += "&SubMissionID=" + request.getParameter("SubMissionID");
			szSrc += "&LoadFlag=" + request.getParameter("LoadFlag");
			szSrc += "&OtherNo=" + request.getParameter("OtherNo");
			loggerDebug("GEdorApproveModifyFrame",szSrc);
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%= szSrc %>">
    	<!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();>
	</body>
</noframes>
</html>
