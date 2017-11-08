<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<title>团单分保资料录入</title>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.jsp" noresize>

	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp" noresize>

	<frame name="fraSubmit"  scrolling="yes"  src="about:blank" noresize>
	<frame name="fraTitle"  scrolling="no"  src="about:blank" noresize>
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0">
		<!--菜单区域-->
		<frameset name="fraMenuMain" rows="30,*" frameborder="no" border="1" framespacing="0">
			<frame id="fraMenuTop" name="fraQuick" scrolling="no" src="about:blank" noresize>
			<frame id="fraMenu" name="fraMenu" scrolling="auto" src="about:blank" noresize>
		</frameset>

		<!--交互区域-->
		<frameset name="fraTalk" rows="0,*" frameborder="no" border="1" framespacing="0">
			<frame id="fraQuick" name="fraQuick" scrolling="no" src="about:blank" noresize>
			<frameset name="fraTalkCol" frameborder="no" border="1" framespacing="0" cols="0, *">
			  <frame id="fraPic" name="fraPic" scrolling="auto" src="about:blank" noresize>
			  <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./distributeQuery.jsp?GrpContNo=<%=request.getParameter("GrpContNo")%>">
			</frameset>
		</frameset>

    <!--下一步页面区域-->
    <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank" noresize>
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
