<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>����ϸ����Ϣ��ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
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
      <%
         String szSrc = request.getParameter("Interface");
                szSrc += "?accidentDetailName=" + StrTool.unicodeToGBK(request.getParameter("accidentDetailName"));
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
