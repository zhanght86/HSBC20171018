<!--Root="../../" -->
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<%
//程序名称：UWManuHealthMain.jsp
//程序功能：续保人工核保体检资料录入
//创建日期：2005-01-09 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<head>
<title>新契约生调结果查询</title>
<script language="javascript">
	var intPageWidth=730;
	var intPageHeight=600;
	window.resizeTo(intPageWidth,intPageHeight);
	//window.focus();	
	
		var initWidth = 0;
  //图片的队列数组
  var pic_name = new Array();
  var pic_place = 0;
  var b_img	= 0;  //放大图片的次数
  var s_img = 0;	//缩小图片的次数
  
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
  //  var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    
       var sqlid10="BQRReportQuerySql10";
	   var mySql10=new SqlClass();
	   mySql10.setResourceName("uw.BQRReportQuerySql");
	   mySql10.setSqlId(sqlid10);//指定使用SQL的id
	   var strSql = mySql10.getString();
    
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("ContNo")%>&BussNoType=11&BussType=TB&SubType=TB1016">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./BQRReportQuery.jsp?ContNo=<%=request.getParameter("ContNo")%>&EdorNo=<%=request.getParameter("EdorNo")%>&PrtSeq=<%=request.getParameter("PrtSeq")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&Flag=<%=request.getParameter("Flag")%>&QueryFlag=<%=request.getParameter("QueryFlag")%>">    <!--下一步页面区域-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">  
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

