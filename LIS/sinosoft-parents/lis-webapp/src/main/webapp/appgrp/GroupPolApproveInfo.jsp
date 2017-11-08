<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<title> </title>
<script language="javascript">
  var type = "<%=request.getParameter("Type")%>";

	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.moveTo(-1, -1);
	//window.focus();
	
	var initWidth = 0;
  //图片的队列数组
  var pic_name = new Array();
  var pic_place = 0;
  var b_img	= 0;  //放大图片的次数
  var s_img = 0;	//缩小图片的次数
</script>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312"></head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<%
	  String tv=request.getParameter("LoadFlag");
	  if(tv.equals("18") || tv.equals("16"))
	  {
	%>
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,0,*,0">
	<%
	 }else{
	%>
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,35%,*,0">
	<%
	 }
	%>
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("polNo")%>&BussNoType=12&BussType=TB&SubType=<%=request.getParameter("SubType")%>"">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./ContPolInput.jsp?scantype=scan&ScanFlag=99&LoadFlag=<%=request.getParameter("LoadFlag")%>&polNo=<%=request.getParameter("polNo")%>&prtNo=<%=request.getParameter("prtNo")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&scantype=<%=request.getParameter("scantype")%>&GrpContNo=<%=request.getParameter("GrpContNo")%>">
		<!-- <frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./ContPolInput.jsp?scantype=scan&ScanFlag=99&LoadFlag=<%=request.getParameter("LoadFlag")%>&polNo=<%=request.getParameter("polNo")%>&PrtNo=<%=request.getParameter("GrpPrtNo")%>&prtNo=<%=request.getParameter("prtNo")%>&vContNo=<%=request.getParameter("vContNo")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>"> -->
    <!--下一步页面区域-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

