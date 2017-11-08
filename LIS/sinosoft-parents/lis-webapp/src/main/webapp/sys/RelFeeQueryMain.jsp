<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	String tInsuredName = "";
	String tAppntName  = "";
	try
	{
		tInsuredName = request.getParameter("InsuredName");
		tInsuredName = new String(tInsuredName.getBytes("ISO-8859-1"), "GBK");
		tAppntName  = request.getParameter("AppntName");
		tAppntName = new String(tAppntName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tAppntName  = "";
		tInsuredName = "";
	}
%>
<html>
<head>
<title>综合查询-个人保单-交费查询 </title>
<script language="javascript">
//	var intPageWidth=930;
//	var intPageHeight=700;
//	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
	
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
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="../sys/AllFeeQueryPDetail.jsp?ContNo=<%=request.getParameter("ContNo")%>&PolNo=<%=request.getParameter("PolNo")%>&RiskCode=<%=request.getParameter("RiskCode")%>&InsuredName=<%=tInsuredName%>&AppntName=<%=tAppntName%>&IsCancelPolFlag=<%=request.getParameter("IsCancelPolFlag")%>">
    	<!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
