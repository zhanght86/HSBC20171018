<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="RIFeeInfoQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="RIFeeInfoQueryInit.jsp"%>
</head>

<body onload="initElementtype();initForm();">
	<form method=post name=fm target="fraSubmit">

		<Table class=common border=0 width=100%>
			<tr>
				<td class=titleImg align=center>请输入查询条件：</td>
			</tr>
		</table>
		<table class=common align=center>
			<TR class=common>
				<TD class=title5>费率表编号</TD>
				<TD class=input5>
					<!--<Input class= common name=QureyFeeCode >--> <Input class=codeno
					name=QureyFeeCode
					ondblclick="return showCodeList('rifeerate',[this,QureyFeeCodeName],[0,1]);"
					onkeyup="return showCodeListKey('rifeerate',[this,QureyFeeCodeName],[0,1]);"><input
					class=codename name="QureyFeeCodeName" readonly="readonly"
					elementtype=nacessary>
				</TD>
				<TD class=title5>费率表批次编号</TD>
				<TD class=input5><input class=codeno name=QureyFeeBatchNo
					ondblclick="return showCodeList('rifeebatch',[this,QureyFeeBatchName],[0,1],null,[fm.QureyFeeCode.value],['feetableno'],1,'500');"
					onkeyup="return findFeecode();"
					onkeyup="return showCodeListKey('rifeebatch',[this,QureyFeeBatchName],[0,1],null,[fm.QureyFeeCode.value],['feetableno'],1,'500');"><input
					class=codename name=QureyFeeBatchName readonly="readonly"
					elementtype=nacessary></TD>
			</TR>
		</Table>
		<br> <INPUT VALUE="查  询" class=cssbutton TYPE=button
			onclick="easyQueryClick();"> &nbsp;&nbsp; <INPUT VALUE="重  置"
			class=cssbutton TYPE=button onclick="resetInput();">

		<Div id="divFeeInfo" style="display:none;" align=left>
			<Table>
				<TR>
					<TD class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divFeeGrid);">
					</TD>
					<TD class=titleImg>费率表信息</TD>
				</TR>
			</Table>
		</Div>
		<Div id="divFeeGrid" style="display:none;" align=center>
			<Table class=common>
				<TR class=common>
					<td style="text-align:left;" colSpan=1><span id="spanFeeGrid"></span>
					</TD>
				</TR>
			</Table>
		</Div>
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>

</html>
