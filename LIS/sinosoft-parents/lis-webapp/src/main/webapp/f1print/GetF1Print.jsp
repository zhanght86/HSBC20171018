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
	  //�±��շ�������󣬹����ӡʱ���ӻ����״�֤
	  //����ǹ����ӡ���ñ����棬��ᴫ��EasyPrintFlag=1������Ľ��治�ᴫ
	  loggerDebug("GetF1Print","GetF1Print.jsp ҳ��");
	  String tEasyPrintFlag = "";
	  tEasyPrintFlag = (String)session.getValue("EasyPrintFlag");
	  if(tEasyPrintFlag==null||"".equals(tEasyPrintFlag)){
		  tEasyPrintFlag = "";
	  }
%>
<body onload="onload()">
	<div id="divInfo" style="display : ''">
		���ڻ�ȡ��ӡ���ݣ����Ժ�...
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
		//�����ӡ��Ҫ��������յĵ�֤���벢֧��˫¼У���Ƿ�һ��
		var tStr = "";
		var tStr2 = "";
		if(!(tEasyPrintFlag==null||tEasyPrintFlag=="")){
			tStr=prompt("��������Ҫ���յĵ�֤����","");
			tStr2 = prompt("���ٴ�������Ҫ���յĵ�֤����","");
			if(tStr!=tStr2){
				alert("��������ĵ�֤���벻һ�£�");
				return false;
			}
		}
	   var strDate = Date.parse(new Date());
        var urlStr="../f1print/operPrintTable.jsp?strDate=" + strDate+"&EasyPrintNo="+tStr+"&EasyPrintFlag="+tEasyPrintFlag;  
        //var showInfo=window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
     
     	 if (showInfo=="Nothing")   //����Ҫ���ŵ�ϵͳ��֤
     	 {
     	 	document.all.F1Book1.print();
     	 }
     	 else if (showInfo=="Succ")  //�ɹ�����ϵͳ��֤
     	 {
	     	 document.all.F1Book1.print();
     	 	 alert("�Ѿ��ɹ����Զ�������ϵͳ��֤��");
     	 }
    	 else													//����ϵͳ��֤�������ӡ����
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