<%
/***************************************************************
 * <p>ProName：LLClaimPreinvestInputMain.jsp</p>
 * <p>Title：调查申请</p>
 * <p>Description：调查申请</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-04-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
	<title>调查申请</title>
	<script language="javascript">
		function focusMe(){
			window.focus();
		}
	</script>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.html">
	
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">

	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,100%,*,0">	
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<%
			String szSrc = "./LLClaimPreinvestInput.jsp";  
			szSrc += "?GrpRgtNo=" + request.getParameter("GrpRgtNo");  //团体案件号
			szSrc += "&RgtNo=" + request.getParameter("RgtNo"); //案件号  
			szSrc += "&custNo=" + request.getParameter("custNo"); //出险人编码       
			szSrc += "&surveyType=" + request.getParameter("surveyType"); //调查类型
			szSrc += "&initPhase=" + request.getParameter("initPhase"); //提起阶段
			szSrc += "&AppntNo=" + request.getParameter("AppntNo"); //投保人号码
			szSrc += "&ManageCom=" + request.getParameter("ManageCom"); //受理机构
			szSrc += "&Mode=" + request.getParameter("Mode"); //受理机构
     %>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%= szSrc %>">
	<!--下一步页面区域-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
