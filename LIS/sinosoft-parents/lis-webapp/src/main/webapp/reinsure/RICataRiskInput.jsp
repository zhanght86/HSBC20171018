<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//�������ƣ�RICataRiskInput.jsp
	//�����ܣ����ֱ���
	//�������ڣ�2011-6-29
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

<SCRIPT src="RICataRisk.js"></SCRIPT>
<%@include file="RICataRiskInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RICataRiskSave.jsp" method=post name=fm
		target="fraSubmit">

		<table>
			<tr>
				<td class="titleImg">���ַ���¼��</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">���ַ���</td>
				<td class="input5"><input class="common" name="RateFee" verify="���ַ���|NOTNULL&NUM" elementtype=nacessary></td>
				<td class="title5">ά������</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="ά������|NOTNULL"
					dateFormat="short" name="MakeDate" id="MakeDate">		<span class="icon"><a onClick="laydate({elem: '#MakeDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<td class="title5"></td>
				<td class="input5"></td>
			</tr>
		</table>
		<input value="��  ��" onclick="button134( )" class="cssButton"
			type="button"> <input value="ɾ  ��" onclick="deleteMess()"
			class="cssButton" type="button"> <br>
		<table>
			<tr>
				<td class="titleImg">���ַ��ʲ�ѯ����</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">ά������</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});"
					dateFormat="short" name="MakeDateB" id="MakeDateB">		<span class="icon"><a onClick="laydate({elem: '#MakeDateB'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<td class="title5"></td>
				<td class="input5"></td>
				<td class="title5"></td>
				<td class="input5"></td>
			</tr>
		</table>
		<input value="��  ѯ" onclick="button135( )" class="cssButton"
			type="button"> <br>
		<table>
			<tr>
				<td class="titleImg">���ַ����б�</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul13Grid">
				</span></td>
			</tr>
		</table>
		<table>
			<tr>
				<td class="titleImg">ͳ������</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">��������</td>
				<td class="input5"><input class=codeno readonly="readonly" name="tableType"
					verify="��������|NOTNULL"
					ondblclick="return showCodeList('ricatareporttype', [this,tableTypeName],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('ricatareporttype', [this,tableTypeName],[0,1],null,null,null,1);"><input
					class=codename name=tableTypeName readonly="readonly"
					elementtype=nacessary></td>
				<td class="title5">��ֹ����</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});"
					dateFormat="short" name="EndDate" id="EndDate">		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<td class="title5"></td>
				<td class="input5"></td>
			</tr>
		</table>
		<input value="ͳ  ��" onclick="button136()" class="cssButton"
			type="button"> <input type="hidden" name="OperateType">
		<input type="hidden" name="SerialNo"> <br>

	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
