<!--Root="../../" -->
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorUWManuSpecMain.jsp
//�����ܣ���ȫ�˹��˱��ӷ�
//�������ڣ�2005-07-16 11:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<html>
<head>
<title>�ӷ�¼�� </title>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
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
		   String pContNo=request.getParameter("ContNo");
		   String pMissionID=request.getParameter("MissionID");
		   String pSubMissionID=request.getParameter("SubMissionID");
		   String pEdorNo=request.getParameter("EdorNo");
		   String pEdorType=request.getParameter("EdorType");
           String pInsuredNo=request.getParameter("InsuredNo");
           String pEdorAcceptNo=request.getParameter("EdorAcceptNo");
           
		   String varSrc = "./EdorUWManuAddInput.jsp?"+"&EdorNo="
		                   +pEdorNo+"&ContNo="+pContNo+"&EdorType="+pEdorType+"&MissionID="+pMissionID+"&SubMissionID="
		                   +pSubMissionID+"&InsuredNo="+pInsuredNo+"&EdorAcceptNo="+pEdorAcceptNo;
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=varSrc%>">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
