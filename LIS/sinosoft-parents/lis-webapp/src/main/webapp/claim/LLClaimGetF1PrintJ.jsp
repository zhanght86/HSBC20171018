<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.report.f1report.*"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<head>
<title>Sinosoft</title>
<style type="text/css">
<!--### 打印返回后，用于读取生成的临时文件，预览打印页面（内容）###-->
<!--
body 
{
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
	String appPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	loggerDebug("LLClaimGetF1PrintJ","---------appPath=="+appPath);
%>
<body onload="onload()">
	<div id="divInfo" style="display : ''">
		正在获取打印数据，请稍候...
	</div>
	<div id="divDisplay" style="display : none">
		<applet name="F1Book1"  code="com.sinosoft.report.f1report.AppletControl1.class" archive="../f1print/New_F1J9Swing.jar" width="760" height="500">
			<param name="ReportURL" value="<%= appPath+ "/f1print/F1PrintKernelJ1.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis() %>">
			<param name = "DownLoadURL" value = "<%= appPath+ "/f1print/F1DownLoadJ1.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis()+"&RealPath="+request.getParameter("RealPath")%>">
			<param name="IsNeedExcel" value="true">
		</applet>
	</div>
	<form name=fmDownLoad action="../f1print/F1DownLoadJ1.jsp">
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

	//打印预览界面上“”打印触发函数----
	function fnPrint()
	{
   		var urlStr="./LLClaimoperPrintTable.jsp" ;
		//var showInfo=window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
   	 	document.all.F1Book1.print();
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

