<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ page import="java.net.*"%><%@include
	file="../common/jsp/UsrCheck.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>工作流</title>
<%
request.setCharacterEncoding("UTF-8");

String t = request.getParameter("t");
String action = request.getParameter("action");
String src = "about:blank";
if("flow".equals(t)) {
	String flowID = request.getParameter("id");
	String version = request.getParameter("ver");
	String flowName = request.getParameter("name");
	String flowType = request.getParameter("type");
	if(flowID == null || "null".equals(flowID)) flowID="";
	if(version == null || "null".equals(version)) version="";
	if(flowName == null || "null".equals(flowName)) flowName="";
	if(flowType == null || "null".equals(flowType)) flowType="";
	flowName = new String(flowName.getBytes("iso8859-1"),"UTF-8");
	src ="./flowdialog.jsp?action="+action+"&id="+flowID+"&name="+URLEncoder.encode(flowName,"UTF-8")+"&version="+version+"&type="+flowType;
}
else if("node".equals(t)) {
	String flowType = request.getParameter("type");
	String nodeID = request.getParameter("id");
	String nodeName = request.getParameter("name");
	String time = request.getParameter("time");
	if(nodeID == null || "null".equals(nodeID)) nodeID="";
	if(nodeName == null || "null".equals(nodeName)) nodeName="";
	if(time == null || "null".equals(time)) time="";
	if(flowType == null || "null".equals(flowType)) flowType="";
	nodeName = new String(nodeName.getBytes("iso8859-1"),"UTF-8");
	
	src ="./nodedialog.jsp?action="+action+"&id="+nodeID+"&name="+URLEncoder.encode(nodeName,"UTF-8")+"&time="+time+"&type="+flowType;
}
else if("line".equals(t)) {
	String lineID = request.getParameter("id");
	String lineName = request.getParameter("name");
	String pid = request.getParameter("pid");
	String pver = request.getParameter("pver");
	String ftype = request.getParameter("ftype");
	String ctype = request.getParameter("ctype");
	String condition = request.getParameter("condition");
	String condDesc = request.getParameter("desc");
	if(lineID == null || "null".equals(lineID)) lineID="";
	if(lineName == null || "null".equals(lineName)) lineName="";
	if(pid == null || "null".equals(pid)) pid="";
	if(pver == null || "null".equals(pver)) pver="";
	if(ftype == null || "null".equals(ftype)) ftype="";
	if(ctype == null || "null".equals(ctype)) ctype="";
	if(condition == null  || "null".equals(condition) ) condition="";
	if(condDesc == null  || "null".equals(condDesc)) condDesc="";
	
	lineName = new String(lineName.getBytes("iso8859-1"),"UTF-8");
	condition = new String(condition.getBytes("iso8859-1"),"UTF-8");
	condDesc = new String(condDesc.getBytes("iso8859-1"),"UTF-8");
	
	src ="./linedialog.jsp?action="+action+"&id="+lineID+"&name="+URLEncoder.encode(lineName,"UTF-8")+"&pid="+ pid+"&pver="+ pver+"&ftype=" +ftype + "&ctype="+ctype+"&condition="+ URLEncoder.encode(condition,"UTF-8")+"&desc="+ URLEncoder.encode(condDesc,"UTF-8");
}
%>

</head>
<frameset id=fraMain rows="0,0,0,0,*" frameborder="no" border="1"
	framespacing="0" cols="*">
	<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame id=VD name="VD" src="../common/cvar/CVarData.jsp">

	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame id=EX name="EX" src="../common/cvar/CExec.jsp">

	<frame id=fraSubmit name="fraSubmit" scrolling="yes" noresize
		src="about:blank">
	<frame id=fraTitle name="fraTitle" scrolling="no" noresize
		src="about:blank">
	<frameset id=fraSet cols="*" frameborder="yes" border="1"
		framespacing="0" rows="0,0,*,0">
		<!--菜单区域-->
		<frame id=fraMenu name="fraMenu" scrolling="yes" noresize
			src="about:blank">
		<!--交互区域-->
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize
			src="about:blank">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize
			src="<%=src%>">
		<!--下一步页面区域-->
		<frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize
			src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

