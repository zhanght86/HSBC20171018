<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//�������ƣ�RIDataModifyInput.jsp
	//�����ܣ�ҵ�����ݵ���
	//�������ڣ�2011-07-30
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

<SCRIPT src="RIDataModify.js"></SCRIPT>
<%@include file="RIDataModifyInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIDataModifySave.jsp" method=post name=fm
		target="fraSubmit">

		<table>
			<tr>
				<td class="titleImg">��ѯ����</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">�ٱ���ͬ</td>
				<td class="input5"><input class="codeno" name="RIContNo" verify="�ٱ���ͬ|code:ricontno"
					ondblclick="return showCodeList('ricontno',[this,RIContName],[0,1]);"
					onkeyup="return showCodeListKey('ricontno',[this,RIContName],[0,1]);"
					nextcasing=><input class="codename" name="RIContName"></td>
				<td class="title5">�������˿ͻ�����</td>
				<td class="input5"><input class="common" name="InsuredNo">
				</td>
</tr>
			<tr class="common">
				<td class="title5">�ֳ�����</td>
				<td class="input5"><input class="codeno" name="RIAccNo" verify="�ֳ�����|code:riaccmucode"
					ondblclick="return showCodeList('riaccmucode',[this,RIAccName],[0,1]);"
					onkeyup="return showCodeListKey('riaccmucode',[this,RIAccName],[0,1]);"
					nextcasing=><input class="codename" name="RIAccName"></td>

			</tr>
			<tr class="common">
				<td class="title5">����</td>
				<td class="input5"><input class="codeno" name="RiskCode" verify="����|code:rilmrisk"
					ondblclick="return showCodeList('rilmrisk',[this,RiskName],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('rilmrisk',[this,RiskName],[0,1],null,null,null,1);" 
					nextcasing=><input class="codename" name="RiskName">
				</td>
				<td class="title5">������</td>
				<td class="input5"><input class="codeno" name="DutyCode" verify="������|code:rilmduty"
					ondblclick="return showCodeList('rilmduty',[this,DutyName],[0,1],null,fm.RiskCode.value,'riskcode',1);"
					onkeyup="return showCodeListKey('rilmduty',[this,DutyName],[0,1],null,fm.RiskCode.value,'riskcode',1);"
					nextcasing=><input class="codename" name="DutyName">
				</td>
			</tr>
		</table>
		<input value="��  ѯ" onclick="button136( )" class="cssButton"
			type="button"> <br>
		<table>
			<tr>
				<td class="titleImg">��ѯ���</td>
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
		<table>
			<tr>
				<td class="titleImg">ҵ�����ݵ���</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="input5"><table class=common>
						<tr class=common>
							<td style="text-align:left;" colSpan=1><span id="spanMul13Grid">
							</span></td>
						</tr>
					</table></td>
			</tr>
		</table>
		<input value="��  ��" onclick="button137( )" class="cssButton"
			type="button"> <br> <input type="hidden"
			name="OperateType" />
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
