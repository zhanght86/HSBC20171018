<%@include file="/i18n/language.jsp"%>
<html>
<%
	//name :ReComManageInput.jsp
	//function :ReComManage
	//Creator :
	//date :2007-08-14
%>
<%@page contentType="text/html;charset=GBK"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src="./RIComManageInput.js"></SCRIPT>
<%@include file="./RIComManageInit.jsp"%>
</head>
<body onload="initElementtype();initForm()">
	<form action="./RIComManageSave.jsp" method=post name=fm
		target="fraSubmit">
		<%@include file="../common/jsp/OperateButton.jsp"%>
		<%@include file="../common/jsp/InputButton.jsp"%>
		<Div id="divLLReport1" style="display: ''">
			<br>
			<Div id=divTable1 style="display: ''">
				<Table class=common>
					<TR class=common>
						<TD class=title5>公司代码</TD>
						<TD class=input5><Input class=common name=ReComCode
							id="ReComCodeId" elementtype=nacessary
							verify="公司代码|NOTNULL&len<20"></TD>
						<TD class=title5>公司名称</TD>
						<TD class=input5><Input class=common name=ReComName
							id="ReComNameId" elementtype=nacessary
							verify="公司名称|NOTNULL&len<60"></TD>
					</TR>
					<TR>
						<TD class=title5>公司类型</TD>
						<TD class=input5><input class=codeno
							name="ComType" verify="公司类型|NOTNULL&CODE:ricomtype"
							ondblclick="return showCodeList('ricomtype', [this,ComTypeName],[0,1],null,null,null,1);"
							onkeyup="return showCodeListKey('ricomtype', [this,ComTypeName],[0,1],null,null,null,1);"><input
							class=codename name=ComTypeName readonly="readonly"
							elementtype=nacessary></TD>
						<TD class=title5>邮政编码</TD>
						<TD class=input5><Input class=common name=PostalCode>
						</TD><tr>
						<td text-align:right class="title5">传真</td>
						<td class="input5"><Input class=common name=FaxNo></td>
						<TD class=title5>公司地址</TD>
						<TD class=input colspan=3><Input class=common name=Address
							style="width: 96%"></TD>
					</TR>
<!--						<td class="title5"></td>-->
<!--						<td class="input5"></td>-->
<!--						<td class="title5"></td>-->
<!--						<td class="input5"></td>-->
					</tr>
				</Table>
			</Div>

			<Div id=divTable2 style="display:none;">
				<Table class=common>
					<TR>
						<TD class=title5></TD>
						<TD class=input colspan=3></TD>
					</TR>
				</Table>
			</Div>
			<br>

			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divCertifyType);"></td>
					<td class=titleImg>公司联系人</td>
				</tr>
			</table>

			<Div id="divCertifyType" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align:left colSpan=1><span id="spanRelateGrid"></span>
						</td>
					</tr>
				</table>
			</div>
			<br>
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divSerialRemark);">
					</td>
					<td class=titleImg>备注信息</td>
				</tr>
			</table>

			<Div id="divSerialRemark" style="display: ''">
				<TR class=common>
					<TD class=input colspan="6"><textarea name="Info" cols="100%"
							rows="3" class="common">
			    </textarea></TD>
				</TR>
			</Div>

			<input type="hidden" name="OperateType">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
