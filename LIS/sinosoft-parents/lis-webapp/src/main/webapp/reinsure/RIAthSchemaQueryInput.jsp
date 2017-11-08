<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//程序名称：RIAthSchemaQueryInput.jsp
	//程序功能：算法方案查询
	//创建日期：2011/6/17
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<SCRIPT src="RIAthSchemaQuery.js"></SCRIPT>
<%@include file="RIAthSchemaQueryInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIAthSchemaQuerySave.jsp" method=post name=fm
		target="fraSubmit">

		<table>
			<tr>
				<td class="titleImg">查询条件</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">方案类型</td>
				<td class="input5"><input class="codeno" name="RISolType"
					ondblclick="return showCodeList('risolutiontype',[this,ArithmeticDefType],[0,1]);"
					onkeyup="return showCodeListKey('risolutiontype',[this,ArithmeticDefType],[0,1]);"
					nextcasing=><input class="codename"
					name="ArithmeticDefType"></td>
				<td class="title5">方案编码</td>
				<td class="input5"><input class="common" name="ArithmeticDefID"></td>
				<td class="title5">方案名称</td>
				<td class="input5"><input class="common"
					name="ArithmeticDefName"></td>

			</tr>
		</table>
		<br> <input value="查  询" onclick="button106( )" class="cssButton"
			type="button"> <input value="返  回" onclick="button105( )"
			class="cssButton" type="button"> <br>
		<br>
		<table>
			<tr>
				<td class="titleImg">算法方案列表</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul10Grid">
				</span></td>
			</tr>
		</table>
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
