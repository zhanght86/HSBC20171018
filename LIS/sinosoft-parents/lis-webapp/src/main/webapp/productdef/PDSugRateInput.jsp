<%@include file="../i18n/language.jsp"%>


	<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//�������ƣ�PDDutyGetAliveInput.jsp
		//�����ܣ����θ�������
		//�������ڣ�2009-3-16
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
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<link rel="stylesheet" type="text/css"
			href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="../common/themes/icon.css">
		<script type="text/javascript">
	 		var riskcode = '<%=request.getParameter("riskcode")%>';
		</script>
		<SCRIPT src="PDSugRate.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<%@include file="PDSugRateInit.jsp"%>

	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
		<form action="./PDSugRateSave.jsp" method=post name=fm target="fraSubmit">
			<table>
				<tr>
					<td class="titleImg">
���ʲ�������:
					</td>
				</tr>
			</table>
			<table class=common>

				<tr class=common>
					<td class=title5>
�������ͣ�
					</td>
					<td class=input5>
						<Input class=codeno name=TYPE readonly="readonly"  verify="��������|NOTNUlL" ondblclick="return showMyCodeList('rate',[this,TYPEName],[0,1]);" onkeyup="return showMyCodeListKey('rate',[this,TYPEName],[0,1]);"><input class=codename name="TYPEName" readonly="readonly" elementtype="nacessary">
						<input value="�������͹���" name="btnEdit" onClick="rateType()"
							class="cssButton" type="button"  style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
���֣�
					</td>
					<td class=input5>
						<Input class=codeno name=CURRENCY readonly="readonly"  verify="����|NOTNUlL" ondblclick="return showCodeList('sa_currency',[this,CURRENCYName],[0,1]);" onkeyup="return showCodeListKey('sa_currency',[this,CURRENCYName],[0,1]);"><input class=codename name="CURRENCYName" readonly="readonly" elementtype="nacessary">
					</td>
				</tr>

				<tr class=common>
					<td class=title5>
��ʼ���ڣ�
					</td>
					<td class=input5>
						<input class="multiDatePicker"  verify="��ʼ����|NOTNUlL" name=STARTDATE dateFormat="short" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
�������ڣ�
					</td>
					<td class=input5>
						<input class="multiDatePicker"  verify="��������|NOTNUlL" name=ENDDATE dateFormat="short" elementtype="nacessary">
					</td>

				</tr>
				<tr class=common>
					<td class=title5>
���ʣ�
					</td>
					<td class=input5>
						<input class="common" name=RATE verify="����|NOTNUlL&NUM" elementtype="nacessary">
					</td>

				</tr>
			</table>
			<input value="��   ��" name="btnEdit" onClick="save()"
				class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
			<input value="��   ��" name="btnSave" onClick="update()"
					class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
			<table>
				<tr>
					<td class="titleImg">
���ʲ����б�:
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline10Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button
				onclick="Mulline10GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline10GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline10GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button
				onclick="Mulline10GridTurnPage.lastPage();">
			<br>
			<div align=left>
				<input value="ɾ   ��" name="btnEdit" onClick="del()"
					class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
				<input value="��   ��" name="btnEdit" onClick="returnParent()"
					class="cssButton" type="button">
			</div>
			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LDRate">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name="RiskCode" value=<%=request.getParameter("riskcode")%>>
		</form>
			<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;">
	</body>
</html>

