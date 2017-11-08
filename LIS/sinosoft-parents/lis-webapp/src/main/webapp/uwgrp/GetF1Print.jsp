<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.report.f1report.*"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<head>
<title>Sinosoft</title>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
</head>
<%
java.util.Calendar  cal = new java.util.GregorianCalendar();
String appPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<body onload="onload()">
	<div id="divInfo" style="display : ''">正在获取打印数据，请稍候...</div>
	<div id="divDisplay" style="display : none">
		<applet name="F1Book1"  code="com.sinosoft.report.f1report.AppletControl1.class" archive="New_F1J9Swing.jar" width="760" height="500">
			<param name="ReportURL" value="<%= appPath+ "/uw/F1PrintKernel.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis() %>">
			<param name="IsNeedExcel" value="true">
		</applet>
	</div>
</body>
</html>
<script language="javascript">
document.all.F1Book1.height = window.screen.availHeight-120;
document.all.F1Book1.width  = window.screen.availWidth-25;

function onload()
{
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	var curTime;
	window.resizeTo(intPageWidth,intPageHeight);
	window.screentop=-1;
	window.screenleft=-1;
	window.focus();
	divDisplay.style.display = "";
	divInfo.style.display = "none";
}

function fnPrint()
{


	var strDate = Date.parse(new Date());

	var urlStr="./operPrintTable.jsp?strDate=" + strDate;
	
	var showInfo=window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	if (showInfo=="Nothing")
	{
		//不需要发放的系统单证
		document.all.F1Book1.print();
	}
	else if (showInfo=="Succ")
	{
		//成功发放系统单证
		document.all.F1Book1.print();
		alert("已经成功的自动发放了系统单证！");
	}
	else
	{
		//发放系统单证出错或打印出错
		alert(showInfo);
	}
	try
	{
		top.opener.easyQueryClick();
	}
	catch(ex)
	{
	}
}
</script>
