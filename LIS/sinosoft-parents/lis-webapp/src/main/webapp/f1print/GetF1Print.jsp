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
	  String appPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	  //新保险法相关需求，柜面打印时增加回收套打单证
	  //如果是柜面打印调用本界面，则会传入EasyPrintFlag=1，其余的界面不会传
	  loggerDebug("GetF1Print","GetF1Print.jsp 页面");
	  String tEasyPrintFlag = "";
	  tEasyPrintFlag = (String)session.getValue("EasyPrintFlag");
	  if(tEasyPrintFlag==null||"".equals(tEasyPrintFlag)){
		  tEasyPrintFlag = "";
	  }
%>
<body onload="onload()">
	<div id="divInfo" style="display : ''">
		正在获取打印数据，请稍候...
	</div>
	<div id="divDisplay" style="display : none">
		<applet name="F1Book1"  code="com.sinosoft.report.f1report.AppletControl1.class" archive="../f1print/New_F1J9Swing.jar" width="760" height="500">
			<param name="ReportURL" value="<%= appPath+ "/f1print/F1PrintKernel.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis() %>">
			<param name = "DownLoadURL" value = "<%= appPath+ "/f1print/F1DownLoad.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis() %>">
			<param name="IsNeedExcel" value="true">
		</applet>
</div>
<script language="javascript">
//alert(window.screen.availHeight);
document.all.F1Book1.height = window.screen.availHeight-120;
document.all.F1Book1.width  = window.screen.availWidth-25;
var tEasyPrintFlag="<%=tEasyPrintFlag%>";


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
		//柜面打印需要输入待回收的单证号码并支持双录校验是否一致
		var tStr = "";
		var tStr2 = "";
		if(!(tEasyPrintFlag==null||tEasyPrintFlag=="")){
			tStr=prompt("请输入需要回收的单证号码","");
			tStr2 = prompt("请再次输入需要回收的单证号码","");
			if(tStr!=tStr2){
				alert("两次输入的单证号码不一致！");
				return false;
			}
		}
	   var strDate = Date.parse(new Date());
        var urlStr="../f1print/operPrintTable.jsp?strDate=" + strDate+"&EasyPrintNo="+tStr+"&EasyPrintFlag="+tEasyPrintFlag;  
        //var showInfo=window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
     
     	 if (showInfo=="Nothing")   //不需要发放的系统单证
     	 {
     	 	document.all.F1Book1.print();
     	 }
     	 else if (showInfo=="Succ")  //成功发放系统单证
     	 {
	     	 document.all.F1Book1.print();
     	 	 alert("已经成功的自动发放了系统单证！");
     	 }
    	 else													//发放系统单证出错或打印出错
    	 {
    	 	 alert(showInfo);
    	 }
       try
       {
        top.opener.easyQueryClick();
       }
       catch(ex)
       {}
	}
	
	function fnDownLoad()
{
	alert(1);
//	fmDownLoad.submit();
	var relativePath = "<%=  "/f1print/F1PrintKernel.jsp?" + cal.getTimeInMillis() + "=" + cal.getTimeInMillis()%>";
	var win = '..'+relativePath;
//	alert(document.all.F1Book1.getCodeBase());
	alert(2);
}

</script>
</body>
</html>
