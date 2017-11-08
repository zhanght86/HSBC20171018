<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	String tAccDate = request.getParameter("AccDate"); //事故发生日期
	String tCustomerNo = request.getParameter("CustomerNo"); //出险人编码
	//	String tAccType = request.getParameter("AccType"); //出险原因
%>
<title>事件信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<script src="./LLAccidentQuery.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="LLAccidentQueryInit.jsp"%>
</head>

<body onload="initForm();">
<br>
<form name=fm target=fraSubmit method=post>
<Table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLLAccident);"></TD>
		<TD class=titleImg>事件信息</TD>
	</TR>
</Table>

<Div id="divLLAccident" style="display: ''" align=center>
<Table class=common>
	<TR class=common>
		<TD text-align: left colSpan=1><span id="spanLLAccidentGrid"></span>
		</TD>
	</TR>
</Table>
<table>
	<tr>
		<td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
		<td><INPUT class=cssButton VALUE="上一页" TYPE=button	 onclick="turnPage.previousPage();"></td>
		<td><INPUT class=cssButton VALUE="下一页" TYPE=button	 onclick="turnPage.nextPage();"></td>
		<td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
	</tr>
</table>
</Div>

<INPUT class=cssButton VALUE="返  回" TYPE=button
	onclick="returnParent();"> <span id="spanCode"
	style="display: none; position:absolute; slategray"></span></Form>
</body>
</html>
