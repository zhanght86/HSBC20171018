<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--
   queryflag�����������ֵ��ò�ѯҳ���λ�á�
   ��������Լ�Ķ�ҵ��Ա¼���multline�н��в�ѯʱqueryflag='1'��
   �����ط�����queryflag=null
--%>

<html>
<head>
<title>���������Ϣ��ѯ</title>
<script language="javascript">
	window.focus();	
</script>

</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1"
	framespacing="0" cols="*">
	<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">

	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">

	<frame name="fraSubmit" scrolling="yes" noresize src="about:blank">
	<frame name="fraTitle" scrolling="no" noresize src="about:blank">
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1"
		framespacing="0" rows="*">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<frame id="fraInterface" name="fraInterface" scrolling="auto"
			src="./AgentComQueryInput.jsp?ManageCom=<%=request.getParameter("ManageCom")%>">
		<!--��һ��ҳ������-->
		<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
<body bgcolor="#ffffff">
</body>
</noframes>
</html>
