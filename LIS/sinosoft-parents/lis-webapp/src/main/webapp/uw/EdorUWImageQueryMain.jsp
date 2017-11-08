<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version 1.00
 * @date 2005-11-01
 ******************************************************************************/
%>


<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <title>保全核保影像资料查询</title>
  <script src="../common/javascript/Common.js" ></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script language="javascript">
  	var intPageWidth = screen.availWidth;
  	var intPageHeight = screen.availHeight;
  	window.resizeTo(intPageWidth, intPageHeight);
  	window.moveTo(-1, -1);
  	//window.focus();

  	var initWidth = 0;
    //图片的队列数组
    var pic_name = new Array();
    var pic_place = 0;
    var b_img	= 0;  //放大图片的次数
    var s_img = 0;	//缩小图片的次数

    window.onunload = afterInput;

    function afterInput()
    {
      try { top.opener.afterInput(); }
      catch(ex) {}
    }

    var mainPolNo = "";
    var mainRisk = "";
    //查询扫描图片的描述
    function queryScanType()
    {
  //    var strSql = " select code1, codename, codealias from ldcode1 where codetype='scaninput'";
      
     var sqlid3="EdorUWImageQuerySql3";
 	 var mySql3=new SqlClass();
 	 mySql3.setResourceName("uw.EdorUWImageQuerySql");
 	 mySql3.setSqlId(sqlid3);//指定使用SQL的id
 	 var strSql = mySql3.getString();
 	 
      var arrResult = easyExecSql(strSql);
      //alert(arrResult);
      return arrResult;
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&ContNo=<%=request.getParameter("ContNo")%>&QueryType=0&DocID<%=request.getParameter("DocID")%>">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./EdorUWImageQuery.jsp?ContNo=<%=request.getParameter("ContNo")%>">
    	<!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	  <br><br><br><br><br>
	  <center><font size="2">对不起，您的浏览器不支持框架网页。</font></center>
	</body>
</noframes>
</html>
