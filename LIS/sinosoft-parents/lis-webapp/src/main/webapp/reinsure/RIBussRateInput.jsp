<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//�������ƣ�RIBussRateInput.jsp
	//�����ܣ�����ά��
	//�������ڣ�2011-6-27
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
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

<SCRIPT src="RIBussRate.js"></SCRIPT>
<%@include file="RIBussRateInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIBussRateSave.jsp" method=post name=fm
		target="fraSubmit">

		<table>
			<tr>
				<td class="titleImg"><%="ҵ�����ά��"%></td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">��  ��</td>
				<td class="input5"><input class="codeno" name="Currency"
					ondblclick="return showCodeList('currency', [this,CurrencyName],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('currency', [this,CurrencyName],[0,1],null,null,null,1);"
					nextcasing='' verify="�ŷN|NOTNULL&code:currency"><input
					class="codename" name="CurrencyName" elementtype=nacessary readonly="readonly">
				</td>
				<td class="title5">����ֵ</td>
				<td class="input5"><input class="common" name="ExchangeRate"
					verify="����ֵ|NOTNULL&NUM&LEN<17" elementtype=nacessary></td>
				<td class="title5"></td>
				<td class="input5"></td>
			<tr class="common">
				<td class="title5">��Ч����</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});"
					dateFormat="short" name="StartDate"  id="StartDate">		<span class="icon"><a onClick="laydate({elem: '#StartDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<td class="title5">ʧЧ����</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});"
					dateFormat="short" name="EndDate" id="EndDate">		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<td class="title5"></td>
				<td class="input5"></td>
			</tr>
			</tr>
		</table>
		<input value="��  ��" onclick="button131()" class="cssButton"
			type="button"> <br> <br>
		<table>
			<tr>
				<td class="titleImg">ҵ����ʲ�ѯ</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">��  ��</td>
				<td class="input5"><input class="codeno" name="CurrencyQuery"
					ondblclick="return showCodeList('currency', [this,CurrencyNameQuery],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('currency', [this,CurrencyNameQuery],[0,1],null,null,null,1);"
					nextcasing="" verify="�ŷN|CODE:currency"><input class="codename"
					name="CurrencyNameQuery" >
				</td>
				<td class="title5">��Ч����</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#StartDateQuery'});"
					dateFormat="short" name="StartDateQuery" id="StartDateQuery">		<span class="icon"><a onClick="laydate({elem: '#StartDateQuery'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<td class="title5"></td>
				<td class="input5"></td>
			</tr>
		</table>
		<input value="��  ѯ" onclick="button132()" class="cssButton"
			type="button">&nbsp;&nbsp; <input value="ɾ  ��"
			onclick="button133()" class="cssButton" type="button"> <br>
		<table>
			<tr>
				<td class="titleImg">ҵ����ʹ켣</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul11Grid">
				</span>
				</td>
			</tr>
		</table>
		<input type="hidden" name="OperateType" />
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
