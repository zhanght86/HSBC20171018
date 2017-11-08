<%@include file="/i18n/language.jsp"%>
<%
	//name :RIFeeRateInput.jsp
	//function :FeeRate Table Define
	//Creator :Zhang Bin
	//date :2008-1-4
%>
<html>
<%@page contentType="text/html;charset=GBK"%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="./RIFeeRateInput.js"></SCRIPT>
<%@include file="./RIFeeRateInit.jsp"%>
</head>
<body onload="initElementtype();initForm()">
	<form action="" name=fm target="fraSubmit" method=post>
		<%@include file="../common/jsp/OperateButton.jsp"%>
		<%@include file="../common/jsp/InputButton.jsp"%>

		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divLRCont1);"></td>
				<td class=titleImg>费率表定义</td>
			</tr>
		</table>

		<Div id="divLRCont1" style="display: ''">
			<br>
			<Table class=common>
				<TR class=common>
					<TD class=title5>费率表编号</TD>
					<TD class=input5><Input class=common name="FeeTableNo"
						id="FeeTableNoId" elementtype=nacessary
						verify="费率表编号|NOTNULL&len<20"></TD>
					<TD class=title5>费率表名称</TD>
					<TD class=input5><Input class=common name="FeeTableName"
						id="FeeTableNameId" elementtype=nacessary
						verify="费率表名称|NOTNULL&len<100"></TD>
				</TR>
				<TR class=common>
					<TD class=title5>
						<Div id="divFeeType" style="display: ''">费率表类型</Div>
					</TD>
					<td class="input5">
						<Div id="divFeeTypeName" style="display: ''">
							<input class=codeno readonly="readonly" name="FeeTableType"
								ondblclick="return showCodeList('rifeetabletype', [this,FeeTableTypeName],[0,1],null,null,null,1);"
								onkeyup="return showCodeListKey('rifeetabletype', [this,FeeTableTypeName],[0,1],null,null,null,1);"
								verify="费率表类型|NOTNULL&code:rifeetabletype"><input class=codename
								name=FeeTableTypeName readonly="readonly"
								elementtype="nacessary">
						</Div>
					</td>
					<TD class=title5>费率表状态</TD>
					<TD class=input5><Input class=codeno readonly="readonly" NAME="State"
						ondblClick="showCodeList('ristate',[this,stateName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('ristate',[this,stateName],[0,1],null,null,null,1);"
						verify="费率表状态|NOTNULL&code:ristate"><input class=codename
						name=stateName readonly="readonly" elementtype=nacessary></TD>
				</TR>
				<TR class=common>

					<TD class=title5>适用方式</TD>
					<TD class=input5><input class=codeno readonly="readonly" name="UseType"
						ondblclick="return showCodeList('riusetype', [this,UseTypeName],[0,1],null,null,null,1);"
						onkeyup="return showCodeListKey('riusetype', [this,UseTypeName],[0,1],null,null,null,1);"
						verify="适用方式|NOTNULL&code:riusetype"><input class=codename
						name=UseTypeName readonly="readonly" elementtype=nacessary>
					</TD>

					<TD class=title5></TD>
					<td class="input5"></td>
				</TR>
			</Table>
		</Div>
		<br>
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divTableClumnDef);"></td>
				<td class=titleImg>费率表字段定义</td>
			</tr>
		</table>

		<Div id="divTableClumnDef" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align:left colSpan=1><span id="spanTableClumnDefGrid"></span>
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
				<td class=titleImg>费率表描述信息</td>
			</tr>
		</table>

		<Div id="divSerialRemark" style="display: ''">
			<TR class=common>
				<TD class=input colspan="6"><textarea name="ReMark" cols="100%"
						rows="3" class="common">
			    </textarea></TD>
			</TR>
		</Div>
		<br>
		<hr>
		<input type="hidden" name="OperateType">
	</form>

	<input class="cssButton" type="button" value="下一步" onclick="nextStep()">

	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
