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
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<link rel="stylesheet" type="text/css"
			href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="../common/themes/icon.css">
		<SCRIPT src="PDSugTypeManage.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<%@include file="PDSugTypeManageInit.jsp"%>

	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	
	<body onload="initForm();initElementtype();">
		<form action="./PDSugTypeManageSave.jsp" method=post name=fm target="fraSubmit">
			<table>
				<tr>
					<td class="titleImg">
�ĵ����͹���:
					</td>
				</tr>
			</table>
			<table class=common>

				<tr class=common>
					<td class=title5>
���ʹ��룺
					</td>
					<td class=input5>
						<input class="common" name=ID verify="���ʹ���|NOTNULL&LEN<=10" elementtype="nacessary">
					</td>
					<td class=title5>
����������
					</td>
					<td class=input5>
						<input class="common" name=CONTENT >
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
�������ƣ�
					</td>
					<td class=input5>
						<input class="common" name=TITTLE verify="��������|NOTNUlL&LEN<=40" elementtype="nacessary">
					</td>
					<td class=title5>
��ע��Ϣ��
					</td>
					<td class=input5>
						<input class="common" name=BAK1 >
					</td>
				</tr>
				<tr class=common>
				    <td style='display:none;' class=title5>����ԣ�</td>
				    <td style='display:none;' class=input5>
				       <Input class=codeno name=RELATION  ondblclick="return showCodeList('sugrelatype',[this,RELATIONName],[0,1]);"
							onkeyup="return showCodeListKey('sugrelatype',[this,RELATIONName],[0,1]);"><input
							class=codename name="RELATIONName" readonly="readonly">  
				    </td>
				    <td class=title5>�ļ��У�</td>
				    <td class=input5><input class="common8" name=FILENAME verify="�ļ���|NOTNUlL&LEN<=200" elementtype="nacessary">
				    </td>
				</tr>
			
			</table>
			<input value="��   ��" name="btnEdit" onClick="save()"
				class="cssButton" type="button">
			<input value="��   ��" name="btnSave" onClick="update()"
					class="cssButton" type="button">
			<table>
				<tr>
					<td class="titleImg">
�ĵ������б�:
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
					class="cssButton" type="button">
				<input value="��   ��" name="btnEdit" onClick="returnParent()"
					class="cssButton" type="button">
			</div>
			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LMTypeMsg">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name="TYPE" value="rate" >
		</form>
			<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;">
	</body>
</html>

>
	</body>
</html>

