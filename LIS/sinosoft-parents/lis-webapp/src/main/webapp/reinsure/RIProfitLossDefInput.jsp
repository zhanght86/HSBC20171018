<%@include file="/i18n/language.jsp"%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//�������ƣ�RIProfitLossDefInput.jsp
	//�����ܣ�ӯ��Ӷ����
	//�������ڣ�2011/8/18
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

<SCRIPT src="RIProfitLossDef.js"></SCRIPT>
<%@include file="RIProfitLossDefInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIProfitLossDefSave.jsp" method=post name=fm
		target="fraSubmit">
		<%@include file="../common/jsp/OperateButton.jsp"%>
		<%@include file="../common/jsp/InputButton.jsp"%>
		<br>
		<table>
			<tr>

				<td class=titleImg>ӯ��Ӷ����Ϣ����</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">ӯ��Ӷ�����</td>
				<td class="input5"><input class="common" name="RIProfitNo"
					verify="ӯ��Ӷ�����|NOTNULL&len<20" elementtype=nacessary></td>
				<td class="title5">ӯ��Ӷ������</td>
				<td class="input5"><input class="common" name="RIProfitName"
					verify="ӯ��Ӷ������|NOTNULL" elementtype=nacessary></td>
				<td class="title5">�ֱ���˾</td>
				<td class="input5"><input class="codeno" name="RIcomCode"
					ondblClick="showCodeList('riincompany',[this,RIcomName],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey('riincompany',[this,RIcomName],[0,1],null,null,null,1);"
					verify="�ֱ���˾|NOTNULL"><Input class=codename
					name='RIcomName' elementtype=nacessary></td>
			</tr>
			<tr class="common">
				<td class="title5">�ٱ���ͬ</td>
				<td class="input5"><input class="codeno" name="ContNo"
					ondblClick="showCodeList('ricontno',[this,ContName],[0,1],null,null,null,1,260);"
					onkeyup="showCodeListKey('ricontno',[this,ContName],[0,1],null,null,null,1,260);"
					verify="�ٱ���ͬ|NOTNULL"><Input class=codename name='ContName'
					elementtype=nacessary></td>
				<td class="title5">��������</td>
				<td class="input5"><input class="codeno" name="RelaType"
					ondblclick="return showCodeList('riprorelatype',[this,RelaTypeName],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('riprorelatype',[this,RelaTypeName],[0,1],null,null,null,1);"
					nextcasing=><input class="codename" name="RelaTypeName"
					elementtype=nacessary></td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">����</td>
			</tr>
			<tr class="common">
				<td class="input" colspan=0><textarea class="common"
						name="RIProfitDes" cols="100%" rows="3"></textarea></td>
			</tr>
		</table>
		<br />
		<table>
			<tr>
				<td class=titleImg>����������ϸ</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul9Grid">
				</span></td>
			</tr>
		</table>
		<input type="hidden" name="OperateType">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
