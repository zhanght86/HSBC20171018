<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--
   queryflag变量用于区分调用查询页面的位置。
   当从新契约的多业务员录入的multline中进行查询时queryflag='1'。
   其他地方调用queryflag=null

--%>

<html>
<head>
<title>营销员信息查询 </title>
<script language="javascript">
	//var intPageWidth=930;
	//var intPageHeight=700;
	//window.resizeTo(intPageWidth,intPageHeight);
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
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./AccountInfoInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&GrpContNo=<%=request.getParameter("GrpContNo")%>&PrtNo=<%=request.getParameter("PrtNo")%>&SupGrpName=<%=request.getParameter("SupGrpName")%>&SupGrpNo=<%=request.getParameter("SupGrpNo")%>&ManageCom=<%=request.getParameter("ManageCom")%>   &ContType=2&ProposalGrpContNo=<%=request.getParameter("ProposalGrpContNo")%>">
    	<!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
