<%
//name：FrameCaseInfo.jsp(综合查询的理赔明细查询)
//function：show temporary pol info
//create date :2003-04-090
//operator :刘岩松
%>
<!--Root="../../" -->
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<title>理赔信息</title>
<script language="javascript">
function focusMe()
{
  window.focus();
}
</script>
</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
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
			szSrc += "?RptNo=" + request.getParameter("RptNo");
			szSrc += "&RgtNo=" + request.getParameter("RgtNo");
			szSrc += "&InsuredName=" + StrTool.unicodeToGBK(request.getParameter("InsuredName"));
			szSrc += "&Type=" + StrTool.unicodeToGBK(request.getParameter("Type"));
     	szSrc += "&PolNo="+request.getParameter("PolNo");
     loggerDebug("FrameCaseInfo","aaaaaaaaaa"+szSrc); 
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
