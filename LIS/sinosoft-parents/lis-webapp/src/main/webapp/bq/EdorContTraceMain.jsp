<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorContTraceMain.jsp
//�����ܣ���ȫ��ͬ�˱��켣��ѯ  
//�������ڣ�2005-07-13 10:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/���� 
%>
<html> 
<head>
<title>��ȫ��ͬ�˱��켣</title> 
<script language="javascript">
	var intPageWidth=1000;
	var intPageHeight=700;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
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
		   String pContNo=request.getParameter("ContNo");
		   String pEdorNo=request.getParameter("EdorNo");
          
		   String varSrc = "./EdorContTraceInput.jsp?"
		                     +"&EdorNo="+pEdorNo+"&ContNo="+pContNo;
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=varSrc%>">     
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank"> 
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>  
