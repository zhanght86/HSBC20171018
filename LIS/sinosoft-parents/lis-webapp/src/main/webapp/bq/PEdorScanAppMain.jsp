<%@page contentType="text/html;charset=GBK" %>
<!--Root="../../" -->

<html>
<head>
<title>保全扫描件显示 </title>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<script language="javascript">
	
	//var intPageWidth=screen.availWidth;
	//var intPageHeight=screeWidth,intPageHeight);
	//window.en.availHeight;
	//window.resizeTo(intPagmoveTo(-1, -1);
	//window.focus();
	
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.moveTo(-1, -1);
	
	var initWidth = 0;
  //图片的队列数组
  var pic_name = new Array();
  var pic_place = 0;
  var b_img	= 0;  //放大图片的次数
  var s_img = 0;	//缩小图片的次数
  
  var viewMode = 1;//1，图在上，2，分屏，3，浮动Mode
  
  window.onunload = afterInput;
  
  function afterInput() {
    try {
      top.opener.afterInput();
    }
    catch(e) {}
  }
  
  var mainPolNo = "";
  var mainRisk = "";
  //查询扫描图片的描述
  function queryScanType() {
    //var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    var strSql="";
    var sqlid="ContInputScanMainSql1";
		var mySql=new SqlClass();
		mySql.setResourceName("app.ScanContInputSql"); //指定使用的properties文件名
    mySql.setSqlId(sqlid); //指定使用的Sql的id
    mySql.addSubPara("1");
		strSql=mySql.getString();
    return easyExecSql(strSql);
  }  
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
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,50%,*,0">
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("EdorAcceptNo")%>&BussNoType=31&BussType=BQ&EdorAcceptNo=<%=request.getParameter("EdorAcceptNo")%>&QueryType=3">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./PEdorAppInput.jsp?ScanFlag=<%=request.getParameter("ScanFlag")%>&EdorAcceptNo=<%=request.getParameter("EdorAcceptNo")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=0000000001&LoadFlag=<%=request.getParameter("LoadFlag")%>">
    <!--下一步页面区域-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
