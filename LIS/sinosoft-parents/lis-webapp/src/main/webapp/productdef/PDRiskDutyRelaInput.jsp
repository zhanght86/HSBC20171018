<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDRiskDutyRelaInput.jsp
 //程序功能：险种关联责任
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
<SCRIPT src="DictionaryUtilities.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<SCRIPT src="PDRiskDutyRela.js"></SCRIPT>
<%@include file="PDRiskDutyRelaInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body onload="initForm();initElementtype();">
<form action="./PDRiskDutyRelaSave.jsp" method=post name=fm
	target="fraSubmit">

<table>
	<tr>
		<td class="titleImg">险种缴费责任</td>
	</tr>
</table>
<input type=button class=cssbutton value="修改可选标记" onclick="ModifyPay()"><input
	type=button class=cssbutton value="责任信息定义" onclick="DutyDefi()">
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline9Grid">
		</span></td>
	</tr>
</table>
</BR>
<table>
	<tr>
		<td class="titleImg">缴费责任属性定义：</TD>
		<TD width=200></TD>
		<TD></TD>
		<TD width=200></TD>
		<TD></TD>
		<TD width=200></TD>
		<TD><input value="查询缴费库" onclick="QueryPayLib()"
			class="cssButton" type="button"><input value="新增"
			type=button onclick="Paysave()" class="cssButton" type="button"><input
			value="修  改" type=button onclick="Payupdate()" class="cssButton"
			type="button"><input value="删除" type=button
			onclick="Paydel()" class="cssButton" type="button"></td>
	</tr>
</table>
<table class=common>
	<tr class="common">
		<td class="title">交费编码</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">责任代码</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">交费名称</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
	</tr>
	<tr class="common">
		<td class="title">算法类型</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">算法编码</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">算法模板类型</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
	</tr>
</table>
<table>
	<tr>
		<td class="titleImg">险种缴费间隔</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline10Grid">
		</span></td>
	</tr>
</table>
<table>
	<tr>
		<td class="titleImg">险种给付责任</td>
	</tr>
</table>
<!--input type=button class=cssbutton value="修  改" onclick="ModifyGet()"-->
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline11Grid">
		</span></td>
	</tr>
</table>
</BR>
<table>
	<tr>
		<td class="titleImg">给付责任属性定义：</TD>
		<TD width=200></TD>
		<TD></TD>
		<TD width=200></TD>
		<TD></TD>
		<TD width=200></TD>
		<TD><input value="查询给付库" onclick="QueryGetLib()"
			class="cssButton" type="button"><input value="新增"
			type=button onclick="Getsave()" class="cssButton" type="button"><input
			value="修  改" type=button onclick="Getupdate()" class="cssButton"
			type="button"><input value="删除" type=button
			onclick="Getdel()" class="cssButton" type="button"></td>
	</tr>
</table>
<table class=common>
	<tr class="common">
		<td class="title">给付代码</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">给付名称</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">给付类型</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
	</tr>
		<tr class="common">
		<td class="title">给付责任代码</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">算入保额标记</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title"></td>
		<td class="input"></td>
	</tr>
	<tr class="common">
		<td class="title">算法类型</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">算法编码</td>
		<td class="input"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">算法模板类型</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline12Grid">
		</span></td>
	</tr>
</table>

<input value="上一步" type=button onclick="returnParent( )"
	class="cssButton" type="button"> <br>
<br>
<input type=hidden name="RiskCode"> <input type=hidden
	name="operator"> <input type=hidden name="SubmitMode">
<input type=hidden name="tableName" value="PD_LMDutyPay"> <input
	type=hidden name="tableName2" value="PD_LMDutyGet"> <input
	type=hidden name="tableName3" value="PD_LMDutyPay_Lib"> <input
	type=hidden name="tableName4" value="PD_LMDutyGet_Lib"> <input
	type=hidden name=IsReadOnly> <input type=hidden
	name=payPlanCode2> <input type=hidden name=getDutyCode2>
<input type=hidden name=getDutyOrPayPlanCode2> <input
	type=hidden name=dutyType2></form>

<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
