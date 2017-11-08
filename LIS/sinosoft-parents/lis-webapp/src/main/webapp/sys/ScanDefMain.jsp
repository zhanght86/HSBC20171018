<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  String SubType = request.getParameter("SubType");
  String DefPage =  request.getParameter("DefPage");
%>
<html>
<head>
<title>投保扫描件显示 </title>
<script language="javascript">
  var pic_name = new Array();
  var pic_place = 0;
  var viewMode = 1;
  window.onunload = afterInput;
  function afterInput() {
    try {
      top.opener.afterInput();
    }
    catch(e) {}
  }
</script>

</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	<frame name="EX" src="../common/cvar/CExec.jsp">
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,50%,*,0">
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="ScanDef.jsp?SubType=<%=SubType%>">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="<%=DefPage %>?ScanFlag=1&scantype=scan&prtNo=000000&ManageCom=86&LoadFlag=17">
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>

