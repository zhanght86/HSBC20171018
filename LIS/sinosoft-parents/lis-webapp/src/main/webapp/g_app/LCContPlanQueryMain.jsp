<%
/***************************************************************
 * <p>ProName：LCContPlanQueryMain.jsp</p>
 * <p>Title：方案明细查询</p>
 * <p>Description：方案明细查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<script language="javascript">
function focusMe() {
	window.focus();
}
</script>
<head>
<%
			
	String tContPlanType = request.getParameter("ContPlanType");
	String tPolicyNo = request.getParameter("PolicyNo");
	String tPrtNo = request.getParameter("GrpPropNo");
	String tSrc = "";
	tSrc = "./LCContPlanTradInput.jsp";
	tSrc += "?ContPlanType="+ tContPlanType +"&PrtNo="+tPrtNo+"&GrpPropNo="+ tPolicyNo+"&QueryFlag=1";
%>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
	
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
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
		<!--下一步页面区域-->
		<frame name="fraNext" src="about:blank" scrolling="auto">
	</frameset>
	
</frameset>

<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();">
	</body>
</noframes>

</html>
