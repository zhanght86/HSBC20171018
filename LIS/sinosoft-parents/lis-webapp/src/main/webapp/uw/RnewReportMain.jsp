<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWReportMain.jsp
//�����ܣ��˱���������¼��
//�������ڣ�2008-10-16 
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<html>
<head>
<title>�˱���������</title>
<script language="javascript">
	var intPageWidth=1000;
	var intPageHeight=700;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
</script>
</head>
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
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./RnewReport.jsp?ProposalNo=<%=request.getParameter("ProposalNo")%>&NoType=<%=request.getParameter("NoType")%>&QueryFlag=<%=request.getParameter("QueryFlag")%>">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>