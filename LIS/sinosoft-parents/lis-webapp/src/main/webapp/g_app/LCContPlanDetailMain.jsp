<%
/***************************************************************
 * <p>ProName��LCGrpContPolMain.jsp</p>
 * <p>Title���µ�¼��</p>
 * <p>Description���µ�¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-05-05
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
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tContPlanType = request.getParameter("ContPlanType");
	String tMissionID = request.getParameter("MissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tFlag = request.getParameter("Flag");
	String tQueryFlag = request.getParameter("QueryFlag");
	
	String tSrc = "";
	tSrc = "./LCContPlanDetailInput.jsp";
	tSrc += "?GrpPropNo="+ tGrpPropNo+ "&ContPlanType="+ tContPlanType + "&MissionID="+tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Flag="+tFlag+"&QueryFlag="+tQueryFlag;
%>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
	
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
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
		<!--��һ��ҳ������-->
		<frame name="fraNext" src="about:blank" scrolling="auto">
	</frameset>
	
</frameset>

<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();">
	</body>
</noframes>

</html>
