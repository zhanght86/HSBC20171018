<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<title> </title>
<script language="javascript">
  var type = "<%=request.getParameter("Type")%>";
</script>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312"></head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" id="VD" src="../common/cvar/CVarData.jsp">
	
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" id="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit" id="fraSubmit" scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle" id="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--菜单区域-->
		<frame name="fraMenu" id="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./ContPolInput.jsp?scantype=scan&ScanFlag=99&LoadFlag=<%=request.getParameter("LoadFlag")%>&polNo=<%=request.getParameter("polNo")%>&prtNo=<%=request.getParameter("prtNo")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&scantype=<%=request.getParameter("scantype")%>&CardFlag=<%=request.getParameter("CardFlag")%>">
    	<!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

