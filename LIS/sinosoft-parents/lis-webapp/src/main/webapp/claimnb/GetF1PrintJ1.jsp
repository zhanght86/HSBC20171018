<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.report.f1report.*"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<head>
<title>Sinosoft</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head> 
<%
java.util.Calendar  cal = new java.util.GregorianCalendar();
//String appPath = "";  //request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
String appPath = request.getContextPath();
String PrintNo = request.getParameter("PrintNo");

loggerDebug("GetF1PrintJ1","appPath:"+appPath);

%>
<body onload="onload()">
	<div id="divInfo" style="display : ''">���ڻ�ȡ��ӡ���ݣ����Ժ�...</div>
	<div id="divDisplay" style="display : none">
		<OBJECT name="F1Book1" classid = "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" codebase = "http://java.sun.com/products/plugin/1.4/jinstall-1_4-windows-i586.cab#Version=1,4,1,mn" WIDTH = "100%" HEIGHT = "100%">
			<PARAM NAME = CODE VALUE = "com.sinosoft.report.f1report.AppletControl.class" >
			<PARAM NAME = CODEBASE VALUE = "." >
			<PARAM NAME = ARCHIVE VALUE = "../f1print/New_F1J9Swing.jar" >
			<PARAM NAME = "type" VALUE = "application/x-java-applet;version=1.4">
			<param name = "ReportURL" value = "<%= appPath+ "/f1print/F1PrintKernelJ1.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis()+"&RealPath="+request.getParameter("RealPath")%>">
			<param name = "DownLoadURL" value = "<%= appPath+ "/f1print/F1DownLoadJ1.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis()+"&RealPath="+request.getParameter("RealPath")%>">
			<param name = "IsNeedExcel" value = "true">
			<COMMENT>
				<EMBED type="application/x-java-applet;jpi-version=1.4.1" CODE="com.sinosoft.report.f1report.AppletControl.class" CODEBASE="." ARCHIVE="../f1print/New_F1J9Swing.jar" WIDTH="100%" HEIGHT="100%" pluginspage="http://java.sun.com/j2se/1.4.1/download.html">
					<NOEMBED>Could not find a plugin supported by your browser. Please download Sun's Java Plugin 1.4.1</noembed>
				</EMBED>
			</COMMENT>
		</OBJECT> 
	</div>
	<form name=fmDownLoad action="F1DownLoadJ1.jsp">
	<input type=hidden name=RealPath value="<%=request.getParameter("RealPath")%>" >
	</form>
	
	<div style="display:none">
		<iframe name="ifDown" id="ifDown" src="about:blank"></iframe>
	</div>
</body>
</html>
<script language="javascript">
//alert(window.screen.availHeight);
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
	var urlStr="./operPrintTable.jsp?strDate=" + strDate+"&PrintNo="+PrintNo;
	//var showInfo=window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if (showInfo=="Nothing")
	{
		//����Ҫ���ŵ�ϵͳ��֤
		document.all.F1Book1.print();
	}
	else if (showInfo=="Succ")
	{
		//�ɹ�����ϵͳ��֤
		document.all.F1Book1.print();
		alert("�Ѿ��ɹ����Զ�������ϵͳ��֤��");
	}
	else
	{
		//����ϵͳ��֤������ӡ����
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
	
function fnDownLoad()
{
	alert(1);
//	fmDownLoad.submit();
	var relativePath = "<%=  "/f1print/F1PrintKernelJ1.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis()+"&RealPath="+request.getParameter("RealPath")%>";
	var win = '..'+relativePath;
	alert(document.all.F1Book1.getCodeBase());
	alert(2);
}


</script>
