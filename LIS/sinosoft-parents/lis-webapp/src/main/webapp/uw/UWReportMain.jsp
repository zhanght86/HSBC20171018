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
	//parent.document.all.ifrm16.height = screen.availHeight;	//add by liuyuxiao 2011-05-23
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
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
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./UWReport.jsp?ContNo=<%=request.getParameter("ContNo")%>&EdorNo=<%=request.getParameter("EdorNo")%>&NoType=<%=request.getParameter("NoType")%>&QueryFlag=<%=request.getParameter("QueryFlag")%>&OperatePos=<%=request.getParameter("OperatePos")%>&ClmNo=<%=request.getParameter("ClmNo") %>">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
