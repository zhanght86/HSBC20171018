<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-11-06
 * @direction: 保全申请添加批改项目校验保单密码总界面
 ******************************************************************************/
%>


<html>
<head>
    <title>保单密码校验</title>
</head>
<frameset name="fraMain" framespacing="0" frameborder="no" rows="0,0,0,0,*" cols="*" border="1">
    <!-- 保存客户端变量的区域 -->
    <frame name="VD" src="../common/cvar/CVarData.jsp">
    <!-- 实现服务器交户的区域 -->
    <frame name="EX" src="../common/cvar/CExec.jsp">
    <!-- 提交客户端查询的区域 -->
    <frame name="fraSubmit" src="about:blank" scrolling="no" noresize>
    <!-- 顶部显示标题栏的区域 -->
    <frame name="fraTitle" src="about:blank" scrolling="no" noresize>
    <!-- 显示菜单和交互的区域 -->
    <frameset name="fraSet" framespacing="0" frameborder="no" rows="*" cols="0%,*,0%" border="1">
        <!-- 显示菜单的区域 -->
        <frame name="fraMenu" src="about:blank" scrolling="yes" noresize>
        <!-- 实现交互的区域 -->
        <frame id="fraInterface" name="fraInterface" src="PEdorAppPwdVerifyInput.jsp?EdorAcceptNo=<%=request.getParameter("EdorAcceptNo")%>&OtherNo=<%=request.getParameter("OtherNo")%>&OtherNoType=<%=request.getParameter("OtherNoType")%>&EdorItemAppDate=<%=request.getParameter("EdorItemAppDate")%>&AppType=<%=request.getParameter("AppType")%>" scrolling="auto">
        <!-- 下一步操作区域 -->
        <frame name="fraNext" src="about:blank" scrolling="auto">
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
