<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--Root="../../" -->
<html>
<head>
<title>������ѯ </title>
<script language="javascript">
	var intPageWidth=930;
	var intPageHeight=700;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
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
if(request.getParameter("BankFlag")!=null&&(request.getParameter("BankFlag").equals("1")||request.getParameter("BankFlag").equals("01"))){
%>		
		
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="../app/ContInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&ContNo=<%=request.getParameter("ContNo")%>">
<%
}
else if(request.getParameter("BankFlag")!=null&&request.getParameter("BankFlag").equals("3")){
%>   	
    
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="../app/BankContInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&ContNo=<%=request.getParameter("ContNo")%>">
<%
}
else if(request.getParameter("BankFlag")!=null&&request.getParameter("BankFlag").equals("5"))
{
%>   	
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="../app/DirectContInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&ContNo=<%=request.getParameter("ContNo")%>">
<%
}
else {
%>   	
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="../app/CardContInput.jsp?BankFlag=<%=request.getParameter("BankFlag")%>&prtNo=<%=request.getParameter("prtNo")%>">
<%
}
%> 
   	
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
