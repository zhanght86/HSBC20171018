<%
/***************************************************************
 * <p>ProName：LCGrpContESMain.jsp</p>
 * <p>Title：新单管理</p>
 * <p>Description：新单录入（带影像）</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-05-19
 ****************************************************************/
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tContPlanType = request.getParameter("ContPlanType");
	String tMissionID = request.getParameter("MissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tFlag = request.getParameter("Flag");
	String SubType = request.getParameter("SubType");
	String BussNo = request.getParameter("BussNo");
	String BussType = request.getParameter("BussType");
	String ScanFlag = request.getParameter("ScanFlag");
	String tSrc = "";
	tSrc = "./LCGrpContPolInput.jsp";
	tSrc += "?GrpPropNo="+ tGrpPropNo+"&ContPlanType="+tContPlanType
	+"&MissionID="+tMissionID+"&ActivityID="+tActivityID+"&SubMissionID="+tSubMissionID+"&Flag="+tFlag+"&ScanFlag="+ScanFlag;

%>
<html>
<head>
<title>新单管理</title>
<script language="javascript">
	var pic_name = new Array();
	var pic_place = 0;
	var viewMode = 1;
	//当用户关闭一个页面时触发 onunload 事件。
	window.onunload = afterInput;
	function afterInput() {
		try {
			$("body").remove();//释放内存
		}catch(e) {}
	}
</script>

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
