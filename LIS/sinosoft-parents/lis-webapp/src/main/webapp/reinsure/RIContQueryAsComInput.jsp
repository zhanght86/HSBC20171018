<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//�������ƣ�RIContQueryAsComInput.jsp
	//�����ܣ���ͬ��ѯ
	//�������ڣ�2011-7-10
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<SCRIPT src="RIContQueryAsCom.js"></SCRIPT>
<%@include file="RIContQueryAsComInit.jsp"%>
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
				<td class="title5">�ٱ���˾</td>
				<td class="input5"><input class="codeno" name="ReComCode" verify="�ٱ���˾|NOTNULL" 
					ondblclick="return showCodeList('ricompanycode',[this,ReComName],[0,1]);"
					onkeyup="return showCodeListKey('ricompanycode',[this,ReComName],[0,1]);"
					nextcasing=><input class="codename" name="ReComName"
					elementtype=nacessary></td>
				<td class="title5">�ٱ���ͬ</td>
				<td class="input5"><input class="codeno" name="RIContNo"
					ondblclick="return showCodeList('ricontno',[this,RIContName],[0,1]);"
					onkeyup="return showCodeListKey('ricontno',[this,RIContName],[0,1]);"
					nextcasing=><input class="codename" name="RIContName"></td>
				<td class="title5"></td>
				<td class="input5"></td>
			</tr>
		</table>
		<input value="��  ѯ" onclick="queryCout()" class="cssButton"
			type="button"> <input value="��  ��" onclick="doreset()"
			class="cssButton" type="button"> <br>
		<table>
			<tr>
				<td class="titleImg">�ٱ���ͬ�б�</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul10Grid">
				</span></td>
			</tr>
		</table>
		 <br>
		<table>
			<tr>
				<td class="titleImg">�ٱ������б�</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul11Grid">
				</span></td>
			</tr>
		</table>
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
