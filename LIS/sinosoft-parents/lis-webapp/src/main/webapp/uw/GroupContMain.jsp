<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupUWMain.jsp
//�����ܣ������˹��˱�
//�������ڣ�
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
<title>�ŵ��¸�����ϸ��ѯ</title>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0">
<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.jsp" noresize>

	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp" noresize>

	<frame name="fraSubmit"  scrolling="yes"  src="about:blank" noresize>
	<frame name="fraTitle"  scrolling="no"  src="about:blank" noresize>
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0">
		<!--�˵�����-->
		<frameset name="fraMenuMain" rows="30,*" frameborder="no" border="1" framespacing="0">
			<frame id="fraMenuTop" name="fraQuick" scrolling="no" src="about:blank" noresize>
			<frame id="fraMenu" name="fraMenu" scrolling="auto" src="about:blank" noresize>
		</frameset>

		<!--��������-->
		<frameset name="fraTalk" rows="0,*" frameborder="no" border="1" framespacing="0">
			<frame id="fraQuick" name="fraQuick" scrolling="no" src="about:blank" noresize>
			<frameset name="fraTalkCol" frameborder="no" border="1" framespacing="0" cols="0, *">
			  <frame id="fraPic" name="fraPic" scrolling="auto" src="about:blank" noresize>
			  <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./GroupContQuery.jsp?GrpContNo=<%=request.getParameter("GrpContNo")%>">
			</frameset>
		</frameset>

    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank" noresize>
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
