<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.report.f1report.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
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
	java.util.Calendar cal = new java.util.GregorianCalendar();
	//String appPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	String appPath = request.getContextPath();
		loggerDebug("GetF1BatchPrint","appPath="+appPath);
	//获得session里的xmlprint
	XmlExport[] tXmlExport = (XmlExport[]) session.getAttribute("xel");
%>
<body onload="onload()">
<div id="divInfo" style="display: ''">正在获取打印数据，请稍候...</div>
<div id="divDisplay" style="display: none"><!----> 
<applet name="F1Book1" code="com.sinosoft.report.f1report.AppletControl2.class" archive="../f1print/New_F1J9Swing.jar" width="760" height="500">
	<param name="PrintCount" value="<%=""+tXmlExport.length%>">
	<%
		for (int i = 0; i < tXmlExport.length; i++) {

	%>

	<param name='<%= "ReportURL" + i %>' value="<%= appPath+ "/f1print/F1BatchPrintKernel.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis() %>&idx=<%=""+i%>">
	
	<param name='<%= "DownLoadURL" + i %>'	value="<%= appPath+ "/f1print/F1DownLoad.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis()+"&RealPath="+request.getParameter("RealPath")%>">
	<param name="IsNeedExcel" value="false">
	<%
		}
%>
	 </applet>
</div>
</body>
</html>
<script language="javascript">
document.all.F1Book1.height = 1150; //高度为固定值，目前支持的最低分辨率为800X600
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
      
	//var urlStr="../uw/operPrintTable.jsp?strDate=" + strDate;
        var urlStr="../uw/OperBatchPrintTable.jsp?strDate=" + strDate;
	
        //var showInfo=window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        
        alert(showInfo);
        
        document.all.F1Book1.print();
        
        /**
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
        **/ 
	try
	{
		top.opener.easyQueryClick();
	}
	catch(ex)
	{
	}
}
</script>
