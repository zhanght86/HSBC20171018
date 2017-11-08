<%@include file="/i18n/language.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--Root="../../" -->
<html>
<head>
<title>再保合同查询</title>
<script type="text/javascript">
/*
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
*/
function focusMe()
{
  window.focus();
}
</script>
</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="0ing="yes" noresize src="about:blank">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src='./RIContQuery.jsp?RIContNo=<%=request.getParameter("RIContNo")%>&PageFlag=<%=request.getParameter("PageFlag")%>'>
		
<%
		System.out.println("zb_ReContCode: "+request.getParameter("Serial"));
%>

    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();">
	</body>
</noframes>
</html>
