<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--
   queryflag�����������ֵ��ò�ѯҳ���λ�á�
   ��������Լ�Ķ�ҵ��Ա¼���multline�н��в�ѯʱqueryflag='1'��
   �����ط�����queryflag=null

--%>

<html>
<head>
<title>�ͻ�ǿ�ƹ��� </title>
<script language="javascript">
	//var intPageWidth=930;
	//var intPageHeight=700;
	//window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
	
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
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./CustomerForceUnionInput.jsp?ManageCom=<%=request.getParameter("ManageCom")%>&customerno=<%=request.getParameter("customerno")%>&prtno=<%=request.getParameter("prtno")%>">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
