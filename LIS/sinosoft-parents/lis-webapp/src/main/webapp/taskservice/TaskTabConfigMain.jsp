<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
 //�������ƣ�PD_LMDutyGetClmCalInput.jsp
 //�����ܣ����θ����⸶������㹫ʽ
 //�������ڣ�2009-3-16
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head>
<title>���ֽ��涨��</title>
</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
 <!--����ͻ��˱��������򣬸����������-->
 <frame name="VD" src="../common/cvar/CVarData.html">
 
 <!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
 <frame name="EX" src="../common/cvar/CExec.jsp">
 
 <frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
 <frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
 <frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
  <!--�˵�����-->
  <frame name="fraMenu" scrolling="yes" noresize src="about:blank">
  <!--��������-->
  <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./TaskTabConfig.jsp">
     <!--��һ��ҳ������-->
     <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
 </frameset>
</frameset>
<noframes>
 <body bgcolor="#ffffff">
 </body>
</noframes>
</html>
