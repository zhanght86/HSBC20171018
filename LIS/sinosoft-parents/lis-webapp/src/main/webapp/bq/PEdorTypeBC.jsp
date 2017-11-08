<%
/*
  受益人资料变更--框架
  create : 0 孙  迪 2005-3-29 15:25
  modify : 1 
*/
%>
<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<html>
<head>
<title>受益人变更 </title>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.screenleft=0;
	window.screentop=0;
	window.focus();
</script>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	<frame name="EX" src="../common/cvar/CExec.jsp">
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./PEdorTypeBCInput.jsp">
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();>
	</body>
</noframes>
</html>