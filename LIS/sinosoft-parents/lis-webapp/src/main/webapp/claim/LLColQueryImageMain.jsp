<%
//**************************************************************************************************
//Name：LLColQueryImageMain.jsp
//Function：理赔扫描件显示
//Author：niuzj
//Date: 2005-08-20 
//Desc: 
//**************************************************************************************************

%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<html>
<head>
<title>理赔扫描件显示 </title>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.moveTo(-1, -1);
	//window.focus();
	
	var initWidth = 0;
  //图片的队列数组
  var pic_name = new Array();
  var pic_place = 0;
  var b_img	= 0;  //放大图片的次数
  var s_img = 0;	//缩小图片的次数
  
</script>

</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,50%,18,*,0">	
		<!--菜单区域--> 
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("claimNo")%>&QueryType=3"> 
		<frame id="fraInterfacechange" name="fraInterfacechange" scrolling="no" noresize src="./ChangeFrame.jsp">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="../bq/ImageQuery.jsp?BussNo=<%=request.getParameter("claimNo")%>&BussType=LP">
    	<!--下一步页面区域-->     
    	<frame id="fraNext2" name="fraNext2" scrolling="auto" src="about:blank">
	</frameset> 
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

