<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//程序名称：RICataRiskBillInput.jsp
	//程序功能：巨灾账单
	//创建日期：2011-6-29
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

<SCRIPT src="RICataRiskBill.js"></SCRIPT>
<%@include file="RICataRiskBillInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RICataRiskSave.jsp" method=post name=fm
		target="fraSubmit">
		
			<div style="width: 200">
			<table class="common">
				<tr class="common">
					<td class="common"><img src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divBillPrint);"></td>
					<td class="titleImg">待打印账单</td>
				</tr>
			</table>
		</div>
		<table class="common">
			<tr class="common">
				<td class="title5">账单类型</td>
				<td class="input5"><input class=codeno readonly="readonly" name="tableType"
					verify="账单类型|NOTNULL"
					ondblclick="return showCodeList('ricatabilltype', [this,tableTypeName],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('ricatabilltype', [this,tableTypeName],[0,1],null,null,null,1);"><input
					class=codename name=tableTypeName readonly="readonly"
					elementtype=nacessary></td>
				<td class="title5">幣種</td>
				<td class="input5"><input class="codeno" name="Currency"
					ondblClick="showCodeList('currency',[this,CurrencyName],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey('currency',[this,CurrencyName],[0,1],null,null,null,1);"
					verify="幣種|NOTNULL"><Input class=codename
					name='CurrencyName' elementtype=nacessary></td>

</tr>			<tr class="common">
				<td class="title5">终止日期</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});"
					dateFormat="short" name="EndDate" id="EndDate">
		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">
		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<td class="title5"></td>
				<td class="input5"></td>
			</tr>
		</table>
		<hr>
		<input value="打 印" onclick="button136()" class="cssButton"
			type="button"> <input type="hidden" name="OperateType">
		<input type="hidden" name="SerialNo"> <br>

	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
</body>
</html>
