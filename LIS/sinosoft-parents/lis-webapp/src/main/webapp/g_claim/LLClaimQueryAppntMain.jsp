<%
/***************************************************************
 * <p>ProName：LLClaimQueryCustMain.jsp</p>
 * <p>Title：系统用户查询</p>
 * <p>Description：系统用户查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 李肖峰
 * @version  : 8.0
 * @date     : 2014-04-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import = "com.sinosoft.utility.*"%>
<html>
	<head>
	<title>客户信息查询</title>
		<script language="javascript">
			function focusMe() {
				window.focus();
			}
		</script>
	</head>
	<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">	
		<!--保存客户端变量的区域，该区域必须有-->
		<frame name="VD" src="../common/cvar/CVarData.jsp">
		<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
		<frame name="EX" src="../common/cvar/CExec.jsp">
		<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank">
		<frame name="fraTitle"  scrolling="no" noresize src="about:blank">
		<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
			<!--菜单区域-->
			<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		  <!--交互区域-->
		  <%
				StrTool tStrTool = new StrTool();
				String tAppntName = tStrTool.unicodeToGBK(request.getParameter("AppntName"));
				String tContType = tStrTool.unicodeToGBK(request.getParameter("ContType"));
				String tAcceptCom = tStrTool.unicodeToGBK(request.getParameter("AcceptCom"));
		  	String tSrc = "./LLClaimQueryAppntInput.jsp?AppntName="+tAppntName+"&ContType="+tContType+"&AcceptCom="+tAcceptCom;
		  %>
			<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
			<!--下一步页面区域-->
			<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
		</frameset>
	</frameset>
	<noframes>
		<body bgcolor="#ffffff" onblur="focusMe();">
		</body>
	</noframes>
</html>
