<%
/***************************************************************
 * <p>ProName：LCContParamMain.jsp</p>
 * <p>Title：产品参数信息维护中转页</p>
 * <p>Description：产品参数信息维护中转页</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-08
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
	String tBussNo = request.getParameter("BussNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQueryFlag = request.getParameter("QueryFlag");
	String tSrc = "";

	tSrc = "./LCContParamInput.jsp";
	tSrc += "?ObjType=CONT&BussType=NB&ContPlanType="+ tContPlanType +"&BussNo="+ tBussNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID+"&QueryFlag="+tQueryFlag;
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
