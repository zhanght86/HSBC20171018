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
	 		var itemcodeStr = '<%=request.getParameter("itemcode")%>';
	 		var calelementStr = '<%=request.getParameter("calelement")%>';
	 		var operator = '<%=request.getParameter("operator")%>';
		</script>
		<SCRIPT src="PDSugIncomeFactor.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<%@include file="PDSugIncomeFactorInit.jsp"%>


	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="init(),initElementtype();">
		<form action="./PDSugIncomeFactorSave.jsp" method=post name=fm target="fraSubmit">
			<table>
				<tr>
					<td class="titleImg">
�����������:
					</td>
				</tr>
			</table>

			<table class=common>
				<tr class=common>
					<td class=title5>
���������:
					</td>
					<td class=input5>
						<input class="common8" name=ITEMCODE verify="���������|NOTNUlL&LEN<=20" elementtype="nacessary">
					</td>
					<td class=title5>
����˳��
					</td>
					<td class=input5>
						<input class="common8" name=CALORDERNO verify="����˳��|NOTNUlL&NUM" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
�������ӣ�
					</td>
					<td class=input5>
						<input class="common8" name=CALELEMENT verify="��������|NOTNUlL&LEN<=20" elementtype="nacessary">
					</td>
					<td class=title5>
��ע˵����
					</td>
					<td class=input5>
						<input class="common8" name=REMARK>
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
�������ԣ�
					</td>
					<td class=input5>
						<Input class=codeno name=ELEMENTPROPERTY ondblclick="return showCodeList('sugeletype',[this,ELEMENTPROPERTYName],[0,1]);" onkeyup="return showCodeListKey('sugeletype',[this,ELEMENTPROPERTYName],[0,1]);"><input class=codename name="ELEMENTPROPERTYName" readonly="readonly">
					</td>

					<td class=title5>
λ�õ�����
					</td>
					<td class=input5>
						<Input class=codeno name=ADJUSTPOSITION ondblclick="return showCodeList('suglocationtype',[this,ADJUSTPOSITIONName],[0,1]);" onkeyup="return showCodeListKey('suglocationtype',[this,ADJUSTPOSITIONName],[0,1]);"><input class=codename name="ADJUSTPOSITIONName" readonly="readonly">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
�������룺
					</td>
					<td class=input5>
						<input class="common" name=VARIABLECODE>
					</td>
					<td class=title5>
						Sqlִ�з�ʽ��
					</td>
					<td class=input5>
						<Input class=codeno name=SQLEXCUTETYPE ondblclick="return showCodeList('sugsqltype',[this,SQLEXCUTETYPEName],[0,1]);" onkeyup="return showCodeListKey('sugsqltype',[this,SQLEXCUTETYPEName],[0,1]);"><input class=codename name="SQLEXCUTETYPEName" readonly="readonly">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
����sql��
					</td>
					<td class=input5 colspan="3">
						<textarea rows="5" cols="75" name=CALSQL
							tip="�������ݿ��LSPol��LSCont��LSAppnt��LSInsured"></textarea>
						<input value="����sql" name="btnEdit" onClick="testSql();"
							class="cssButton" type="button">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
�����ʼ�㣺
					</td>
					<td class=input5>
						<input class="common" name=STARTPOINT>
					</td>
					<td class=title5>
��ʼ�㵥λ��
					</td>
					<td class=input5>
						<Input class=codeno name=STARTPOINTFLAG ondblclick="return showCodeList('sugyearflag',[this,STARTPOINTFLAGName],[0,1]);" onkeyup="return showCodeListKey('sugyearflag',[this,STARTPOINTFLAGName],[0,1]);"><input class=codename name="STARTPOINTFLAGName" readonly="readonly">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
��������㣺
					</td>
					<td class=input5>
						<input class="common" name=ENDPOINT>
					</td>
					<td class=title5>
�����㵥λ��
					</td>
					<td class=input5>
						<Input class=codeno name=ENDPOINTFLAG ondblclick="return showCodeList('sugyearflag',[this,ENDPOINTFLAGName],[0,1]);" onkeyup="return showCodeListKey('sugyearflag',[this,ENDPOINTFLAGName],[0,1]);"><input class=codename name="ENDPOINTFLAGName" readonly="readonly">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
ѭ�����ӳ�ʼֵ��
					</td>
					<td class=input5>
						<input class="common" name=INITVALUE>
					</td>
					<td class=title5>
ѭ�����Ӳ�����
					</td>
					<td class=input5>
						<input class="common" name=STEPVALUE >
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
�����ȷλ����
					</td>
					<td class=input5>
						<input class="common" name=RESULTPRECISION verify="�����ȷλ��|NOTNUlL&NUM" elementtype="nacessary">
					</td>
				</tr>
			</table>


			<div align=left>
				<input value="��  ��" name="btnSave" onClick="save()"
					class="cssButton" type="button">
				<input value="ȡ  ��" name="btnEdit" onClick="cannelFactor()"
					class="cssButton" type="button">
				<input value="���ݱ���" name="btnEdit" onClick="dataTableDef()"
					class="cssButton" type="button">
			</div>
			<input type=hidden name="operator" value=<%=request.getParameter("operator")%> >
			<input type=hidden name="tableName" value="PD_CalcuteElemet">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name="RiskCode" value=<%=request.getParameter("riskcode")%> >
		</form>
		<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;">
	</body>
</html>

