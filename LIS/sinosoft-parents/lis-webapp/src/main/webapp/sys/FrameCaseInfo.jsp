<%
//name��FrameCaseInfo.jsp(�ۺϲ�ѯ��������ϸ��ѯ)
//function��show temporary pol info
//create date :2003-04-090
//operator :������
%>
<!--Root="../../" -->
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<title>������Ϣ</title>
<script language="javascript">
function focusMe()
{
  window.focus();
}
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
			String szSrc = request.getParameter("Interface");
			szSrc += "?RptNo=" + request.getParameter("RptNo");
			szSrc += "&RgtNo=" + request.getParameter("RgtNo");
			szSrc += "&InsuredName=" + StrTool.unicodeToGBK(request.getParameter("InsuredName"));
			szSrc += "&Type=" + StrTool.unicodeToGBK(request.getParameter("Type"));
     	szSrc += "&PolNo="+request.getParameter("PolNo");
     loggerDebug("FrameCaseInfo","aaaaaaaaaa"+szSrc); 
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
