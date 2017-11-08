<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.report.f1report.*"%>
<%@ page import="java.io.*"%><%@ 
page import="com.sinosoft.print.show.*"%><%@ 
page import="com.sinosoft.lis.pubfun.*"%><%@ 
page import="com.sinosoft.utility.*"%>
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

TransferData tTransferData = (TransferData)session.getValue("TransferData");
if(tTransferData == null) tTransferData = new TransferData();
String tArrStr = (String) tTransferData.getValueByName("BarCodeStr");
loggerDebug("VtsPrint",tArrStr);
String appPath = request.getContextPath();
String tPicType = "true";
%>
	<body onload="onload()">
		<div id="divInfo" style="display : ''">正在获取打印数据，请稍候...</div>
		<div id="divDisplay" style="display : none">
			<applet name="F1Book1" id=F1Book1  code="com.sinosoft.report.f1report.AppletControl1.class" archive="../print/vtsapplet.jar" width="760" height="500">
				<param name="ReportURL" value="<%= appPath+ "/print/VtsPrintKernel.jsp;jsessionid="+request.getSession().getId()+"?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis()+"&PicType="+tPicType%>">
				<param name="DownLoadURL" value="<%= appPath+ "/print/VtsDownLoad.jsp;jsessionid="+request.getSession().getId()+"?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis() %>">
				<param name="IsNeedExcel" value="true">
				<param name="BarCode" value="<%=tArrStr%>">
				<param name="PicType" value="<%=tPicType%>">
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

	var urlStr="../uw/operPrintTable.jsp?strDate=" + strDate;
	//var showInfo=window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	
	try
	{
		top.opener.easyQueryClick();
	}
	catch(ex)
	{}
}
function afterSubmit(FlagStr){
	showInfo.close();
	if (FlagStr=="Nothing")
	{
		//不需要发放的系统单证
		//document.all.F1Book1.print();
	}
	else if (FlagStr=="Succ")
	{
		//成功发放系统单证
		//document.all.F1Book1.print();
		alert("已经成功的自动发放了系统单证！");
	}
	else
	{
		//发放系统单证出错或打印出错
		alert(FlagStr);
	}
}
</script>
