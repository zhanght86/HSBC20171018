
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--Root="../../" -->
<html>
<head>
<title>�������ݴ���</title>
<script language="javascript">

function focusMe()
{
  window.focus();
}
</script>
</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="0ing="yes" noresize src="about:blank">
		<!--��������-->%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--�˵�����-->
		<frame name="fraMenu" scroll
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./FIRuleDealErrLogInput.jsp?RuleDealBatchNo='<%=request.getParameter("RuleDealBatchNo")%>'&DataSourceBatchNo='<%=request.getParameter("DataSourceBatchNo")%>'&RulePlanID='<%=request.getParameter("RulePlanID")%>'">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();>
	</body>
</noframes>
</html>

