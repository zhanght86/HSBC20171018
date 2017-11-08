<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<%@page import="com.sinosoft.print.func.*" %>
<%@page import="com.sinosoft.lis.db.*" %>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/jquery-1.5.1.js"></SCRIPT>
<SCRIPT src="./LPrtPrintTableInput.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

</head>

<body onload="initElementtype();">

<form action="./LPrtPrintTestSave.jsp" method=post name=fm  id="fm"
	target="fraSubmit">
	
<%
String tTempleteID = request.getParameter("TempleteID");
loggerDebug("LPrtPrintTableInput",tTempleteID);
CreateTable tCre = new 	CreateTable();
LPrtTempleteDB tLPrtTempleteDB = new LPrtTempleteDB();
tLPrtTempleteDB.setTempleteID(tTempleteID);
tLPrtTempleteDB.getInfo();
String tTempleteName = tLPrtTempleteDB.getTempleteName();
String tTable = tCre.buildTable(tTempleteID); 	
%>
<br/>
<p align=center><font size =3 ><strong><%=tTempleteName%>测试</strong><font></p>
<hr/>	
<%=tTable%>
<input type="hidden" name = TempleteID value=<%=tTempleteID%>>
<br>
<div>
	<a href="javascript:void(0)" class=button onclick="return testPrint();">打  印</a>
	<a href="javascript:void(0)" class=button onclick="return autoFill();">自动填表</a>
</div>

<!-- <input value="打 印"  type=button class=cssButton   onclick="return testPrint();">
<input value="自动填表"  type=button class=cssButton   onclick="return autoFill();"> -->
</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
