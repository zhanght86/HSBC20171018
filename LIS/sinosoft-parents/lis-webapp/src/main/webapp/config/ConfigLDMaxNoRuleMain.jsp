<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
 //�������ƣ�PDAlgoDefiInput.jsp
 //�����ܣ��㷨�������
 //�������ڣ�2009-3-17
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
  
%>
<head>
<title>�Ŷι�������</title>
<script language="javascript">
	
	/*var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.moveTo(-1, -1);
	window.resizeTo(intPageWidth,intPageHeight);
	//window.focus();*/
	window.onunload = afterInput;

function afterInput() {
	//alert('close');
	try {
	
}
	catch(e) {}
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
  <frame id="fraInterface" name="fraInterface" scrolling="no" src="./ConfigLDMaxNoRule.jsp?NoCode=<%=request.getParameter("NoCode")%>&LimitType=<%=request.getParameter("LimitType")%>&StartDate=<%=request.getParameter("StartDate")%>">
     <!--��һ��ҳ������-->
     <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
 </frameset>
</frameset>
<noframes>
 <body bgcolor="#ffffff">
 </body>
</noframes>
</html>

