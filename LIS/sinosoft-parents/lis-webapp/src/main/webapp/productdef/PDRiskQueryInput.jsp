<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDRequRiskInput.jsp
 //程序功能：产品申请与查询
 //创建日期：2009-3-12
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<SCRIPT src="PDRiskQuery.js"></SCRIPT>
<%@include file="PDRiskQueryInit.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
</head>
<body onload="initForm();initElementtype();">
<form action="./PDRequRiskSave.jsp" method=post name=fm
	target="fraSubmit">

<table>
	<tr>
		<td class="titleImg">请输入产品险种代码：</td>
	</tr>
</table>
<table class=common>
	<TR class=common>
		<TD class=title5>产品险种代码</TD>
		<TD class=input5><input class=common name=RiskCode
			verify="产品险种代码|len<=7"></TD>
		<td class=title5></td>
		<td class=input5></td>
	</tr>
	<tr class=common>
		<TD class=title5>申请日期起期</TD>
		<TD class=input5><input class="coolDatePicker" dateFormat="short"
			name=beginDate veryfy="申请日期起期|notnull"
			onClick="laydate({elem: '#beginDate'});" id="beginDate"><span
			class="icon"><a onClick="laydate({elem: '#beginDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		<TD class=title5>申请日期止期</TD>
		<TD class=input5><input class="coolDatePicker" dateFormat="short"
			name=endDate verify="申请日期止期|notnull"
			onClick="laydate({elem: '#endDate'});" id="endDate"><span
			class="icon"><a onClick="laydate({elem: '#endDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
	</TR>
</table>
<input value="查询"
	onclick="onLineRiskQuery()" class="cssButton" type="button"> <br>
<br>

<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span
			id="spanMulline9Grid"> </span></td>
	</tr>
</table>
<INPUT CLASS=cssbutton VALUE="首页"
	TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> <INPUT
	CLASS=cssbutton VALUE="上一页"
	TYPE=button onclick="Mulline9GridTurnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="下一页"
	TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> <INPUT
	CLASS=cssbutton VALUE="尾页"
	TYPE=button onclick="Mulline9GridTurnPage.lastPage();"> </BR>
</BR>


</br>

<input value="返回" type=button
	onclick="backToParent()" class="cssButton" type="button"></form>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
