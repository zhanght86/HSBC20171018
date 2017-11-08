<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLUWLJSPayQueryMain.jsp
//程序功能：加费应收信息查询
//创建日期：2006-01-05 
//创建人  ：万泽辉
%>

<html>
<head>
<title>加费应收信息查询 </title>
<script language="javascript">
	var intPageWidth=730;
	var intPageHeight=600;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
</script>
</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->

	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<% 
		     String tClmNo  = request.getParameter("ClmNo"); //赔案号
		     String tContNo = request.getParameter("ContNo"); //合同号
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LLUWLJSPayQueryInput.jsp?ClmNo=<%=tClmNo%>&ContNo=<%=tContNo%>">
		
		
    	<!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
	
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
