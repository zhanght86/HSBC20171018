<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLUWLJSPayQueryMain.jsp
//�����ܣ��ӷ�Ӧ����Ϣ��ѯ
//�������ڣ�2006-01-05 
//������  �������
%>

<html>
<head>
<title>�ӷ�Ӧ����Ϣ��ѯ </title>
<script language="javascript">
	var intPageWidth=730;
	var intPageHeight=600;
	window.resizeTo(intPageWidth,intPageHeight);
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
		<% 
		     String tClmNo  = request.getParameter("ClmNo"); //�ⰸ��
		     String tContNo = request.getParameter("ContNo"); //��ͬ��
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LLUWLJSPayQueryInput.jsp?ClmNo=<%=tClmNo%>&ContNo=<%=tContNo%>">
		
		
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
	
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>