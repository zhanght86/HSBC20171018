
<%
//�������ƣ�EdorApproveFrame.jsp
//�����ܣ���ȫ 
//�������ڣ�2005-05-08 15:20:22
//������  ��zhangtao
//���¼�¼��
//�����ˣ�
//��������:
//����ԭ��/����:
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<title>��ȫ����</title>
<script language="javascript">

	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);

function focusMe()
{
  window.focus();
}
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
		<%
			String szSrc = request.getParameter("Interface");	
			szSrc += "?EdorAcceptNo=" + request.getParameter("EdorAcceptNo");	
			szSrc += "&MissionID=" + request.getParameter("MissionID");
			szSrc += "&ActivityID=" + request.getParameter("ActivityID");
			szSrc += "&SubMissionID=" + request.getParameter("SubMissionID");
			szSrc += "&EdorPopedom=" + request.getParameter("EdorPopedom");
			loggerDebug("EdorApproveFrame","szSrc"+szSrc);
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%= szSrc %>">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();>
	</body>
</noframes>
</html>
