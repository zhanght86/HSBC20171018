<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//�������ƣ�PDRiskInsuAccInput.jsp
		//�����ܣ������˻�����
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
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="buttonshow.jsp"%>
		<SCRIPT src="PDRiskInsuAcc.js"></SCRIPT>
		<%@include file="PDRiskInsuAccInit.jsp"%>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
		<form action="./PDRiskInsuAccSave.jsp" method=post name=fm
			target="fraSubmit">

			<table>
				<tr>
					<td class="titleImg">
���������˻�
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline9Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button
				onclick="Mulline9GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline9GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline9GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button
				onclick="Mulline9GridTurnPage.lastPage();">
			</BR>
			<br>
			<!-- <input id=savabutton4 type=button class=cssbutton value="��֤����¼��"
				onclick="setGuarInteRate()"> -->
			<br>
			</BR>
			<table>
				<tr>
					<td class="titleImg">
�˻����Զ��壺
					</td>
				</tr>
			</table>
			<table class=common>
				<tr>
					
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
			</BR>
			<TD>
				<input id=savabutton1 value="��  ��" type=button onclick="save()"
					class="cssButton" type="button">
				<input id=savabutton2 value="��  ��" type=button onclick="update()"
					class="cssButton" type="button">
				<input id=savabutton3 value="ɾ  ��" type=button onclick="del()" class="cssButton" type="button">
				
				<input id=savabutton4 value="���������ϵɾ��" type=button onclick="delrel()" class="cssButton" type="button">
				<input value="��  ��" type=button onclick="returnParent( )" class="cssButton" type="button">
			</td>
			</BR>
			<br>
			<br>
			<input type=hidden name="RiskCode">
			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LMRiskInsuAcc">
			<input type=hidden name="tableName2" value="PD_LMRiskToAcc">
			<input type=hidden name=IsReadOnly>
		</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
