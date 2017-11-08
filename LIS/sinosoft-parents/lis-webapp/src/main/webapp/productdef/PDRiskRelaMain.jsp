<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html>
<%
 //程序名称：PDRiskRelaInput.jsp
 //程序功能：主附险组合定义
 //创建日期：2009-3-14
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>
<head>
<title>主附险组合定义</title>
<script type="text/javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.moveTo(-1, -1);
	window.resizeTo(intPageWidth,intPageHeight);
	window.onunload = afterInput;

function afterInput() {
	try {
	top.opener.afterInput();
	}
	catch(e) {}
} 
window.focus();
</script>

<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
 <!--保存客户端变量的区域，该区域必须有-->
 <frame name="VD" src="../common/cvar/CVarData.html">
 
 <!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
 <frame name="EX" src="../common/cvar/CExec.jsp">
 
 <frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
 <frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
 <frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
  <!--菜单区域-->
  <frame name="fraMenu" scrolling="yes" noresize src="about:blank">
  <!--交互区域-->
  <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./PDRiskRelaInput.jsp?riskcode=<%=request.getParameter("riskcode")%>">
     <!--下一步页面区域-->
     <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
 </frameset>
</frameset>
<noframes>
 <body bgcolor="#ffffff">
 </body>
</noframes>
</html>

