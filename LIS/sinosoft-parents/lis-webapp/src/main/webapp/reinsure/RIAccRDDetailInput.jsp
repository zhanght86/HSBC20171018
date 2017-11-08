<%@include file="/i18n/language.jsp"%>
<html>
<%
	//name :ReComManage.jsp
	//function :ReComManage
	//Creator :
	//date :2006-08-14
%>
<%@page contentType="text/html;charset=GBK"%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<%@include file="./RIAccRDDetailInit.jsp"%>
<SCRIPT src="RIAccRDDetail.js"></SCRIPT>
</head>
<body onload="initElementtype();initForm();">
	<form action="" method="post" name="fm" target="fraSubmit">
		<table>
			<tr>
				<td class="titleImg">关联产品责任</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanRIAccRiskDutyGrid">
				</span></td>
			</tr>
		</table>
		<div align=left>
			<input VALUE="录入新险种" TYPE="button" onclick="resetAcc();"
				class="cssButton"> <input value="关  闭" type="button"
				class="cssButton" onclick="closePage()">
		</div>
		<hr>
		</hr>
		<Table class=common>
			<tr>
				<td class="titleImg">关联产品责任</td>
			</tr>
			<TR class=common>
				<TD class=title5>分出责任编码</TD>
				<TD class=input5><input class="common" name="AccumulateDefNO"
					type="text" readonly="readonly" /></TD>
				<TD class=title5>险种</TD>
				<td class="input5"><input class="codeno" name="AssociatedCode"
					ondblclick="return showCodeList('rilmrisk',[this,AssociatedName],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('rilmrisk',[this,AssociatedName],[0,1],null,null,null,1);"
					verify="险种|NOTNULL&CODE:rilmrisk" nextcasing=><input class="codename"
					name="AssociatedName" elementtype=nacessary></td>
					</tr>
			<TR class=common>
				<TD class=title5>责任项</TD>
				<td class="input5"><input class="codeno" name="GetDutyCode"
					ondblclick="return showCodeList('rilmduty',[this,GetDutyName],[0,1],null,fm.AssociatedCode.value,'riskcode',1);"
					onkeyup="return showCodeListKey('rilmduty',[this,GetDutyName],[0,1],null,null,null,1);"
					verify="责任项|NOTNULL&CODE:rilmduty" nextcasing=><input class="codename"
					name="GetDutyName" elementtype=nacessary></td>
				<TD>
				<td class=title5></td>
				<td class="input5"></td>
				</TD>

			</tr>
		</table>
		<Div id="divEdit1" style="display: ''">
			<input class="cssButton" type="button" value="保  存"
				onclick="addClick()"> <input class="cssButton" type="button"
				value="修  改" onclick="updateClick()" style="display:none;">
			<input class="cssButton" type="button" value="删  除"
				onclick="deleteClick()">
		</Div>
		<input class="common" name="OperateType" type="hidden" />
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
