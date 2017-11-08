<%
/***************************************************************
 * <p>ProName：ScanPagesQueryMainInput.jsp</p>
 * <p>Title：影像件查询</p>
 * <p>Description：影像件查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 刘锦祥
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%
  String tBussNo = request.getParameter("BussNo");
  String tBussType = request.getParameter("BussType");
%>
<html>
<head>
<title>影像件查询 </title>
<script language="javascript">
	var intPageWidth=100%;
	var intPageHeight=100%;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
	window.onunload = resetOperation;
	function resetOperation() {
    	opener.mOperate = 0;
  	}
</script>

</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.html">
	
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="../g_easyscan/ScanPagesQueryInput.jsp?BussNo=<%=tBussNo%>&BussType=<%=tBussType%>">
    	<!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>

