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
	 		var id = '<%=request.getParameter("id")%>';
		</script>
		<SCRIPT src="PDSugMultiLanguage.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<%@include file="PDSugMultiLanguageInit.jsp"%>

	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
		<form action="./PDSugMultiLanguageSave.jsp" method=post name=fm target="fraSubmit">

			<table>
				<tr>
					<td class="titleImg">
��ͷ��������:
					</td>
				</tr>
			</table>

			<table class=common>
				<tr class=common>
					<td class=title5>
�������ͣ�
					</td>
					<td class=input5>
						<Input class=codeno name=LANGTYPE ondblclick="return showCodeList('language',[this,LANGTYPEName],[0,1]);" onkeyup="return showCodeListKey('language',[this,LANGTYPEName],[0,1]);"><input class=codename name="LANGTYPEName" readonly="readonly" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
��ͷ����:
					</td>
					<td class=input5>
						<input class="common8" name=NAME verify="��ͷ����|NOTNUlL&LEN<=10" elementtype="nacessary">
					</td>
				</tr>
			</table>
			<div align=left>
				<input value="��   ��" name="btnSave" onClick="save()"
					class="cssButton" type="button">
				<input value="��   ��" name="btnSave" onClick="update()"
					class="cssButton" type="button">
			</div>
			<table>
				<tr>
					<td class="titleImg">
��ͷ�����б�:
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
			<div align=left>
				
				<input value="ɾ   ��" name="btnEdit" onClick="del()"
					class="cssButton" type="button">
				<input value="��   ��" name="btnEdit" onClick="returnParent()"
					class="cssButton" type="button">
			</div>
			<input type=hidden name="operator" >
			<input type=hidden name="tableName" value="PD_LMProfitHeadMsg">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name=ID value=<%=request.getParameter("id")%>>
			<input type=hidden name="RiskCode" value=<%=request.getParameter("riskcode")%>>
		</form>
		<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;">
	</body>
</html>

