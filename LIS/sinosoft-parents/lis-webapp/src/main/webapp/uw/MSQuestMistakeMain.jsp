
<%
//�������ƣ�MSQuestMistakeMain.jsp(�ְ�������ϸ�Ĵ���)
//�����ܣ�����Լ���������±��鿴
//�������ڣ�2009-01-12 09:01
//���¼�¼��
//�����ˣ�
//��������:
//����ԭ��/����:
%>
<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<title>���������¼</title>
<script language="javascript">
	//parent.document.all.ifrm5.height = screen.availHeight;	//add by liuyuxiao 2011-05-23
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
			//szSrc += "&PrtNo=" + request.getParameter("PrtNo");
			//szSrc += "&NoType=" + request.getParameter("NoType");
			//szSrc += "&UnSaveFlag=" + request.getParameter("UnSaveFlag");
			//szSrc += "&QueryFlag=" + request.getParameter("QueryFlag");
			szSrc += "&otherFlag=" + request.getParameter("otherFlag");
			loggerDebug("MSQuestMistakeMain","szSrc=========="+szSrc);
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
