<%@include file="../i18n/language.jsp"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html>
<%
 //�������ƣ�PDUMInput.jsp
 //�����ܣ����ֺ˱�������
 //�������ڣ�2009-3-14
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head>
<title>�ĵ����͹���</title>
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
  <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./PDSugTypeManageInput.jsp?riskcode=<%=request.getParameter("riskcode")%>&Type=<%=request.getParameter("Type")%>&EdorType=<%=request.getParameter("EdorType")%>">
     <!--��һ��ҳ������-->
     <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
 </frameset>
</frameset>
<noframes>
 <body bgcolor="#ffffff">
 </body>
</noframes>
</html>


 bgcolor="#ffffff">
 </body>
</noframes>
</html>


