
<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sinosoft   
 * @version  : 1.00
 * @date     : 2006-10-30
 * @direction: 核心业务管理系统主界面
 ******************************************************************************/
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  
    <title>人寿保险核心业务系统</title>
    <link href="static/css/imdock.css" rel="stylesheet" />
    <script language="JavaScript">
    <!--
        //判断页面是否初始化完成
        var achieveEX = false;
        var achieveVD = false;
      	var intPageWidth=screen.availWidth;
	      var intPageHeight=screen.availHeight;
	      //alert("intPageWidth:"+intPageWidth+"!!"+"intPageHeight:"+intPageHeight);
	      window.resizeTo(intPageWidth,intPageHeight);
	      window.focus();
	
        //设置主窗口属性, 用于代码选择功能查找主窗口, 缓存数据
        window.name = "Lis";
        try
        {
            //window.resizeTo(screen.availWidth, screen.availHeight);
        }
        catch (ex) {}
        window.focus();
    -->
    </script>
<style>body {

font: tahoma,verdana,arial,helvetica,sans-serif;

font-size: 12px; margin-top:0px;

SCROLLBAR-FACE-COLOR: #97CBFF; SCROLLBAR-HIGHLIGHT-COLOR:#fff; 

SCROLLBAR-SHADOW-COLOR:#97CBFF; SCROLLBAR-DARKSHADOW-COLOR:#819FD3; 

SCROLLBAR-3DLIGHT-COLOR:#819FD3; SCROLLBAR-ARROW-COLOR:#3F65AD;

SCROLLBAR-TRACK-COLOR: #E4E4E4;

}</style>
</head>
<frameset id="fraMain" framespacing="0" frameborder="no" rows="0,0,0,0,0,*" cols="*" border="1">
    <!-- 保存客户端变量的区域 -->
    <frame id="VD" name="VD" src="common/cvar/CVarData.jsp"  >
    <!-- 实现服务器交户的区域 -->
    <frame id="EX" name="EX" src="common/cvar/CExec.jsp"  >
    <!-- 提交客户端查询的区域 -->
    <frame id="fraSubmit" name="fraSubmit" src="about:blank" scrolling="no"  >
    <!-- 顶部显示标题栏的区域 -->
    <frame id="fraTitle" name="fraTitle" src="logon/Title.jsp" scrolling="no"  >
 	<!-- 新增head区域 -->
	<frame id="head" name="head" src="" scrolling="no"  >
    <!-- 显示菜单和交互的区域 -->
    <frameset id="fraSetColor" framespacing="0" frameborder="no" rows="*" cols="0,*" border="0" > 
	    <frame id="fraLeftColor" name="fraLeftColor" src="" scrolling="no">
	    <frameset id="fraSet" framespacing="0" frameborder="no" rows="*" cols="0%,*,0%,0%" border="1" >
		    <!-- 显示菜单的区域 -->
		    <frameset id="fraMenuMain" framespacing="0" frameborder="no" rows="5,*" border="1">
		        <frame id="fraMenuTop" name="fraMenuTop" src="logon/menutop.jsp" scrolling="no" >
		        <frame id="fraMenu" name="fraMenu" src="about:blank" scrolling="no"  >
		    </frameset>
		    <!-- 实现交互的区域 -->
		    <frameset id="fraTalk" framespacing="0" frameborder="no" rows="0,*" border="1">
		        <frame id="fraQuick" name="fraQuick" src="logon/quick.jsp" scrolling="no"  >
		        <frameset id="fraTalkCol" framespacing="0" frameborder="no" cols="0,*" border="1">
		            <frame id="fraPic" name="fraPic" src="about:blank" scrolling="auto"  >
		            <frame id="fraInterface" name="fraInterface" src="logon/mainNew.jsp" scrolling="auto">
		        </frameset>
		    </frameset>
		    
		    <!--聊天窗口的区域-->
		    <frameset id="fraChatMain" framespacing="0" frameborder="no" rows="*" border="1">
		        <frame id="fraChat" name="fraChat" src="static/chat.html" scrolling="no" >
		    </frameset>
		    
	        <!-- 下一步操作区域 -->
	        <frame id="fraNext" name="fraNext" src="about:blank" scrolling="auto"  >
	    </frameset>
	    <!--<frame id="fraRightColor" name="fraRightColor" src="" scrolling="no">-->
    </frameset>
   
</frameset>
<noframes>
    <body bgcolor="#FFFFFF">
        <br><br><br><br><br>
        <center>
            <font size="2">对不起，您的浏览器不支持框架网页。</font>
            <br><br>
            <font size="2">请使用 IE 5.0 或其以上浏览本系统。</font>
        </center>
    </body>
</noframes>


</html>
