<%
/***************************************************************
 * <p>ProName：LCinsuredBnfMain.jsp</p>
 * <p>Title：人员清单维护中转页</p>
 * <p>Description：人员清单维护中转页</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-25
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>人员清单维护</title>
	<script language="javascript">
		var intPageWidth = screen.availWidth;
		var intPageHeight = screen.availHeight;
		window.resizeTo(intPageWidth,intPageHeight);
		window.focus();
	</script>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
	<!--保存客户端变量区域,该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.html">
	
	<!--保存客户端变量和WebServer实现交互的区域,该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit" scrolling="yes" noresize src="about:blank">
	<frame name="fraTitle" scrolling="no" noresize src="about:blank">
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<%
			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tInsuredNo = request.getParameter("InsuredNo");
			String tContNo = request.getParameter("ContNo");
			String tFlag = request.getParameter("Flag");
			String tSrc = "";

			tSrc = "./LCBnfQueryInput.jsp";
			tSrc += "?GrpPropNo="+ tGrpPropNo +"&InsuredNo="+ tInsuredNo +"&ContNo="+ tContNo+"&Flag="+ tFlag;
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
			<!--下一步页面区域-->
			<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
