<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<%
	//�������ƣ�RIDataRevertInput.jsp
	//�����ܣ����ݻع�
	//�������ڣ�2011-07-30
	//������  ��dukang
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

<SCRIPT src="RIDataRevert.js"></SCRIPT>
<%@include file="RIDataRevertInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIDataRevertSave.jsp" method=post name=fm
		target="fraSubmit">

		<table>
			<tr>
				<td class="titleImg">��ѯ����</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">�ֳ�����</td>
				<td class="input5"><input class="codeno" name="AccumulateDefNO"
					verify="�ֳ�����|NOTNULL&code:riaccmucode"
					ondblclick="return showCodeList('riaccmucode',[this,AccumulateDefName],[0,1]);"
					onkeyup="return showCodeListKey('riaccmucode',[this,AccumulateDefName],[0,1]);"><input
					class="codename" name="AccumulateDefName" elementtype=nacessary>
				</td>
				<TD class=title5>�������˺���</TD>
				<TD class=input5><Input class=common name="InsuredNo"></TD>
</tr>
			<tr class="common">
				<td class="title5">��ʼ����</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});"
					name="StartDate"  id="StartDate">
		<span class="icon"><a onClick="laydate({elem: '#StartDate'});">
		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
			</tr>
		</table>
		<input value="��  ѯ" onclick="button130()" class="cssButton"
			type="button"> <br>
		<table>
			<tr>
				<td class="titleImg">��������</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="input5"><table class=common>
						<tr class=common>
							<td style="text-align:left;" colSpan=1><span id="spanMul11Grid">
							</span></td>
						</tr>
					</table></td>
			</tr>
		</table>
		<input type="hidden" name="OperateType" /> 
		<input value="���ݻع�" onclick="button131( )" class="cssButton" type="button">
		<br>
		<input type="hidden" name="RiskCode" value="">
		<input type="hidden" name="StartDate1" value="">
		<input type="hidden" name="EndDate1" value="">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
