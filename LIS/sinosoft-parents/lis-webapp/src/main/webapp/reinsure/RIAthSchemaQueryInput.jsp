<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//�������ƣ�RIAthSchemaQueryInput.jsp
	//�����ܣ��㷨������ѯ
	//�������ڣ�2011/6/17
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

<SCRIPT src="RIAthSchemaQuery.js"></SCRIPT>
<%@include file="RIAthSchemaQueryInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIAthSchemaQuerySave.jsp" method=post name=fm
		target="fraSubmit">

		<table>
			<tr>
				<td class="titleImg">��ѯ����</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">��������</td>
				<td class="input5"><input class="codeno" name="RISolType"
					ondblclick="return showCodeList('risolutiontype',[this,ArithmeticDefType],[0,1]);"
					onkeyup="return showCodeListKey('risolutiontype',[this,ArithmeticDefType],[0,1]);"
					nextcasing=><input class="codename"
					name="ArithmeticDefType"></td>
				<td class="title5">��������</td>
				<td class="input5"><input class="common" name="ArithmeticDefID"></td>
				<td class="title5">��������</td>
				<td class="input5"><input class="common"
					name="ArithmeticDefName"></td>

			</tr>
		</table>
		<br> <input value="��  ѯ" onclick="button106( )" class="cssButton"
			type="button"> <input value="��  ��" onclick="button105( )"
			class="cssButton" type="button"> <br>
		<br>
		<table>
			<tr>
				<td class="titleImg">�㷨�����б�</td>
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
