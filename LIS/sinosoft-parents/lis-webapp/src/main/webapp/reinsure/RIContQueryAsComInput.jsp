<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//程序名称：RIContQueryAsComInput.jsp
	//程序功能：合同查询
	//创建日期：2011-7-10
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<SCRIPT src="RIContQueryAsCom.js"></SCRIPT>
<%@include file="RIContQueryAsComInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="" method="post" name="fm" target="fraSubmit">

		<table>
			<tr>
				<td class="titleImg">查询条件</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">再保公司</td>
				<td class="input5"><input class="codeno" name="ReComCode" verify="再保公司|NOTNULL" 
					ondblclick="return showCodeList('ricompanycode',[this,ReComName],[0,1]);"
					onkeyup="return showCodeListKey('ricompanycode',[this,ReComName],[0,1]);"
					nextcasing=><input class="codename" name="ReComName"
					elementtype=nacessary></td>
				<td class="title5">再保合同</td>
				<td class="input5"><input class="codeno" name="RIContNo"
					ondblclick="return showCodeList('ricontno',[this,RIContName],[0,1]);"
					onkeyup="return showCodeListKey('ricontno',[this,RIContName],[0,1]);"
					nextcasing=><input class="codename" name="RIContName"></td>
				<td class="title5"></td>
				<td class="input5"></td>
			</tr>
		</table>
		<input value="查  询" onclick="queryCout()" class="cssButton"
			type="button"> <input value="清  空" onclick="doreset()"
			class="cssButton" type="button"> <br>
		<table>
			<tr>
				<td class="titleImg">再保合同列表</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul10Grid">
				</span></td>
			</tr>
		</table>
		 <br>
		<table>
			<tr>
				<td class="titleImg">再保方案列表</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul11Grid">
				</span></td>
			</tr>
		</table>
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
