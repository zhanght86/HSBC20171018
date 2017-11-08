<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.report.f1report.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<html>
<head><title>我的第一个JavaApplet程序</title></head>
<body> 
<p>
<%
java.util.Calendar  cal = new java.util.GregorianCalendar();
//String appPath = "";  //request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
String appPath = request.getContextPath();
String PrintNo = request.getParameter("PrintNo");


%>

<applet code=com.sinosoft.lis.reinsure.exercise.HelloWorld
width=300
height=200>
</applet>

</body>
</html> 

