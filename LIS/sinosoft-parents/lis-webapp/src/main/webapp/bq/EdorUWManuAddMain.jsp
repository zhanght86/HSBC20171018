<!--Root="../../" -->
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorUWManuSpecMain.jsp
//程序功能：保全人工核保加费
//创建日期：2005-07-16 11:10:36
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%>

<html>
<head>
<title>加费录入 </title>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
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
		   String pContNo=request.getParameter("ContNo");
		   String pMissionID=request.getParameter("MissionID");
		   String pSubMissionID=request.getParameter("SubMissionID");
		   String pEdorNo=request.getParameter("EdorNo");
		   String pEdorType=request.getParameter("EdorType");
           String pInsuredNo=request.getParameter("InsuredNo");
           String pEdorAcceptNo=request.getParameter("EdorAcceptNo");
           
		   String varSrc = "./EdorUWManuAddInput.jsp?"+"&EdorNo="
		                   +pEdorNo+"&ContNo="+pContNo+"&EdorType="+pEdorType+"&MissionID="+pMissionID+"&SubMissionID="
		                   +pSubMissionID+"&InsuredNo="+pInsuredNo+"&EdorAcceptNo="+pEdorAcceptNo;
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=varSrc%>">
    	<!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
