<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--Root="../../" -->
<%
//�������ƣ�EdorManuUWInsuredMain.jsp
//�����ܣ������˺˱���Ϣ������
//�������ڣ�2005-06-20 17:58:36
//������  ��liurongxiao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
<title>�˹��˱�-��������Ϣ </title>
</head>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
</script>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,0,*,0">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="about:blank">
		<%
		String pContNo=request.getParameter("ContNo");
		String pMissionID=request.getParameter("MissionID");
		String pSubMissionID=request.getParameter("SubMissionID");
		String pEdorNo=request.getParameter("EdorNo");
		String pInsuredNo=request.getParameter("InsuredNo");
		String pEdorAcceptNo= request.getParameter("EdorAcceptNo");
		String pOtherNo = request.getParameter("OtherNo");
        String pOtherNoType = request.getParameter("OtherNoType");
        String pEdorAppName = request.getParameter("EdorAppName");
        String pApptype = request.getParameter("Apptype");
        String pManageCom = request.getParameter("ManageCom");
        String pPrtNo = request.getParameter("PrtNo");
        String pEdorType = request.getParameter("EdorType");
		String varSrc = "./EdorManuUWInsuredInput.jsp?"+"InsuredNo="+pInsuredNo+"&EdorNo="
		                   +pEdorNo+"&ContNo="+pContNo+"&MissionID="+pMissionID+"&SubMissionID="
		                   +pSubMissionID+"&EdorAcceptNo="+pEdorAcceptNo+"&OtherNo="+pOtherNo
		                   +"&OtherNoType="+pOtherNoType+"&EdorAppName="+pEdorAppName+"&Apptype="
		                   +pApptype+"&ManageCom="+pManageCom+"&PrtNo="+pPrtNo+"&EdorType="+pEdorType;
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="<%=varSrc%>">
    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

