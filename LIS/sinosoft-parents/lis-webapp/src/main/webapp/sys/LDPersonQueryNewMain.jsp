<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%--
   queryFlag���ã��ж�������Լ¼���������Ǵ�Ͷ����¼����õĲ�ѯ�����Ǳ�����¼����õĲ�ѯ��
   Ͷ����¼�룺queryFlag=queryappnt
   ������¼�룺queryFlag=queryInsured
   
--%>
<%
  String queryFlag = request.getParameter("queryFlag");
%>

<html>
<head>
<title>�ͻ���Ϣ��ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script language="javascript">
	var intPageWidth=730;
	var intPageHeight=600;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
	window.onunload = resetOperation;
  function resetOperation() {
    opener.mOperate = 0;
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
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="../sys/LDPersonQueryNew.jsp?queryFlag=<%=queryFlag%>">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
