<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDRiskDutyRelaInput.jsp
 //�����ܣ����ֹ�������
 //�������ڣ�2009-3-12
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
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
		<td class="titleImg">���ֽɷ�����</td>
	</tr>
</table>
<input type=button class=cssbutton value="�޸Ŀ�ѡ���" onclick="ModifyPay()"><input
	type=button class=cssbutton value="������Ϣ����" onclick="DutyDefi()">
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline9Grid">
		</span></td>
	</tr>
</table>
</BR>
<table>
	<tr>
		<td class="titleImg">�ɷ��������Զ��壺</TD>
		<TD width=200></TD>
		<TD></TD>
		<TD width=200></TD>
		<TD></TD>
		<TD width=200></TD>
		<TD><input value="��ѯ�ɷѿ�" onclick="QueryPayLib()"
			class="cssButton" type="button"><input value="����"
			type=button onclick="Paysave()" class="cssButton" type="button"><input
			value="��  ��" type=button onclick="Payupdate()" class="cssButton"
			type="button"><input value="ɾ��" type=button
			onclick="Paydel()" class="cssButton" type="button"></td>
	</tr>
</table>
<table class=common>
	<tr class="common">
		<td class="title">���ѱ���</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">���δ���</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">��������</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
	</tr>
	<tr class="common">
		<td class="title">�㷨����</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">�㷨����</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">�㷨ģ������</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
	</tr>
</table>
<table>
	<tr>
		<td class="titleImg">���ֽɷѼ��</td>
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
		<td class="titleImg">���ָ�������</td>
	</tr>
</table>
<!--input type=button class=cssbutton value="��  ��" onclick="ModifyGet()"-->
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline11Grid">
		</span></td>
	</tr>
</table>
</BR>
<table>
	<tr>
		<td class="titleImg">�����������Զ��壺</TD>
		<TD width=200></TD>
		<TD></TD>
		<TD width=200></TD>
		<TD></TD>
		<TD width=200></TD>
		<TD><input value="��ѯ������" onclick="QueryGetLib()"
			class="cssButton" type="button"><input value="����"
			type=button onclick="Getsave()" class="cssButton" type="button"><input
			value="��  ��" type=button onclick="Getupdate()" class="cssButton"
			type="button"><input value="ɾ��" type=button
			onclick="Getdel()" class="cssButton" type="button"></td>
	</tr>
</table>
<table class=common>
	<tr class="common">
		<td class="title">��������</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">��������</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">��������</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
	</tr>
		<tr class="common">
		<td class="title">�������δ���</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">���뱣����</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title"></td>
		<td class="input"></td>
	</tr>
	<tr class="common">
		<td class="title">�㷨����</td>
		<td class="input"><input class="codeNo" name=""
			ondblclick="return showCodeList(null,[this],[0]);"
			onkeyup="return showCodeListKey(null,[this],[0]);"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">�㷨����</td>
		<td class="input"><input
			class="codename" name="" elementtype="nacessary"
			verify="this|NOTNULL"></td>
		<td class="title">�㷨ģ������</td>
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

<input value="��һ��" type=button onclick="returnParent( )"
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
