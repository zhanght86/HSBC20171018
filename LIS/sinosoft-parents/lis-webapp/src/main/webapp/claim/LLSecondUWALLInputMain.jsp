<!--Root="../../" -->
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<title>������κ˱�</title>
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
		  String szSrc = "./LLSecondManuUWInput.jsp";	
		  szSrc += "?CaseNo=" + request.getParameter("CaseNo");	//�ⰸ��
		  szSrc += "&BatNo=" + request.getParameter("BatNo"); //���κ�			    
		  szSrc += "&InsuredNo=" + request.getParameter("InsuredNo");   //�����˿ͻ���    
		  szSrc += "&ClaimRelFlag=" + request.getParameter("ClaimRelFlag");   //�ⰸ��ر�־
		  szSrc += "&Missionid=" + request.getParameter("Missionid");  //����ID 
		  szSrc += "&Submissionid=" + request.getParameter("Submissionid");   //������ID 
		  szSrc += "&Activityid=" + request.getParameter("Activityid");   //�ڵ�� 		  		  		   	    		  
		  loggerDebug("LLSecondUWALLInputMain",szSrc);
		 %>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%= szSrc %>">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
