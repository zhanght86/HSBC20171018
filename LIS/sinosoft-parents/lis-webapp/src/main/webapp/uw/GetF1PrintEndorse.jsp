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
//String appPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
String appPath = request.getContextPath();

%>
<body onload="onload()">
	<div id="divInfo" style="display : ''">���ڻ�ȡ��ӡ���ݣ����Ժ�...</div>
	<div id="divDisplay" style="display : none">
		<applet name="F1Book1"  code="com.sinosoft.report.f1report.AppletControl1.class" archive="../f1print/New_F1J9Swing.jar" width="760" height="500">
			<param name="ReportURL" value="<%= appPath+ "/f1print/F1PrintKernelJ1.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis() %>">
			<param name = "DownLoadURL" value = "<%= appPath+ "/f1print/F1DownLoadJ1.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis()+"&RealPath="+request.getParameter("RealPath")%>">
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

	var urlStr="../uw/operPrintTable.jsp?strDate=" + strDate;
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
		//alert("�Ѿ��ɹ����Զ�������ϵͳ��֤��");
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
</script>
