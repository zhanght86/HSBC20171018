<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//�������ƣ�RIAccRDQueryInput.jsp
	//�����ܣ��ֳ����ζ���-��Ϣ��ѯ
	//�������ڣ�2011/6/16
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

<SCRIPT src="RIAccRDQuery.js"></SCRIPT>
<%@include file="RIAccRDQueryInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="" method="post" name="fm" target="fraSubmit">
		<table>
			<tr>
				<td class="titleImg">��ѯ����</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">�ֳ����α���</td>
				<td class="input5"><input class="common" name="AccumulateDefNO"></td>
				<td class="title5">�ֳ���������</td>
				<td class="input5"><input class="common"
					name="AccumulateDefName"></td>
			</tr><tr class="common">
				<td class="title5">�ֳ�����״̬</td>
				<td class="input5"><input class="codeno" name="RIAccState"
					ondblclick="return showCodeList('ristate',[this,RIAccStateName],[0,1]);"
					onkeyup="return showCodeListKey('ristate',[this,RIAccStateName],[0,1]);"
					nextcasing=><input class="codename" name="RIAccStateName"></td></tr>
		</table>
		<br> <input value="��  ѯ" onclick="AccumulateQuery()"
			class="cssButton" type="button"> <input value="��  ��"
			onclick="ReturnData()" class="cssButton" type="button"> <br>
		<table>
			<tr>
				<td class="titleImg">�ֳ������б�</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanRIAccumulateGrid">
				</span></td>
			</tr>
		</table>
		<input type="hidden" name="PageFlag"> <input type="hidden" name="ReContCode1">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
