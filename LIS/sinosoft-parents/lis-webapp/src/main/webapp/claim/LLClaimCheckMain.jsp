<html>
<%@page contentType="text/html;charset=GBK"%>
<head>
	<title>Claim Check</title>
	<script type="text/javascript">
		function focusMe(){
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
        <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LLClaimCheckInput.jsp?RgtNo=<%=request.getParameter("RgtNo")%>&ContNo=<%=request.getParameter("ContNo")%>&CustomerNo=<%=request.getParameter("CustomerNo")%>">
        <!--下一步页面区域-->
        <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
    </frameset>
</frameset>
<noframes>
    <body bgcolor="#ffffff" onblur="focusMe();">
    </body>
</noframes>
</html>