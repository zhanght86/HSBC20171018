<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--Root="../../" -->
<%
//程序名称：EdorManuUWInsuredMain.jsp
//程序功能：被保人核保信息主界面
//创建日期：2005-06-20 17:58:36
//创建人  ：liurongxiao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<head>
<title>人工核保-被保人信息 </title>
</head>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
	
</script>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,0,*,0">
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="about:blank">
		<%
		String pContNo=request.getParameter("ContNo");
		String pMissionID=request.getParameter("MissionID");
		String pSubMissionID=request.getParameter("SubMissionID");
		String pEdorNo=request.getParameter("EdorNo");
		String pInsuredNo=request.getParameter("InsuredNo");
		String pEdorAcceptNo= request.getParameter("EdorAcceptNo");
		String pOtherNo = request.getParameter("OtherNo");
        String pOtherNoType = request.getParameter("OtherNoType");
        String pEdorAppName = request.getParameter("EdorAppName");
        String pApptype = request.getParameter("Apptype");
        String pManageCom = request.getParameter("ManageCom");
        String pPrtNo = request.getParameter("PrtNo");
        String pEdorType = request.getParameter("EdorType");
		String varSrc = "./EdorManuUWInsuredInput.jsp?"+"InsuredNo="+pInsuredNo+"&EdorNo="
		                   +pEdorNo+"&ContNo="+pContNo+"&MissionID="+pMissionID+"&SubMissionID="
		                   +pSubMissionID+"&EdorAcceptNo="+pEdorAcceptNo+"&OtherNo="+pOtherNo
		                   +"&OtherNoType="+pOtherNoType+"&EdorAppName="+pEdorAppName+"&Apptype="
		                   +pApptype+"&ManageCom="+pManageCom+"&PrtNo="+pPrtNo+"&EdorType="+pEdorType;
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="<%=varSrc%>">
    <!--下一步页面区域-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

