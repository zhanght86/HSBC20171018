<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDRequRiskInput.jsp
 //�����ܣ���Ʒ�������ѯ
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

<SCRIPT src="PDRiskQuery.js"></SCRIPT>
<%@include file="PDRiskQueryInit.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
</head>
<body onload="initForm();initElementtype();">
<form action="./PDRequRiskSave.jsp" method=post name=fm
	target="fraSubmit">

<table>
	<tr>
		<td class="titleImg">�������Ʒ���ִ��룺</td>
	</tr>
</table>
<table class=common>
	<TR class=common>
		<TD class=title5>��Ʒ���ִ���</TD>
		<TD class=input5><input class=common name=RiskCode
			verify="��Ʒ���ִ���|len<=7"></TD>
		<td class=title5></td>
		<td class=input5></td>
	</tr>
	<tr class=common>
		<TD class=title5>������������</TD>
		<TD class=input5><input class="coolDatePicker" dateFormat="short"
			name=beginDate veryfy="������������|notnull"
			onClick="laydate({elem: '#beginDate'});" id="beginDate"><span
			class="icon"><a onClick="laydate({elem: '#beginDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		<TD class=title5>��������ֹ��</TD>
		<TD class=input5><input class="coolDatePicker" dateFormat="short"
			name=endDate verify="��������ֹ��|notnull"
			onClick="laydate({elem: '#endDate'});" id="endDate"><span
			class="icon"><a onClick="laydate({elem: '#endDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
	</TR>
</table>
<input value="��ѯ"
	onclick="onLineRiskQuery()" class="cssButton" type="button"> <br>
<br>

<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span
			id="spanMulline9Grid"> </span></td>
	</tr>
</table>
<INPUT CLASS=cssbutton VALUE="��ҳ"
	TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ"
	TYPE=button onclick="Mulline9GridTurnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ"
	TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> <INPUT
	CLASS=cssbutton VALUE="βҳ"
	TYPE=button onclick="Mulline9GridTurnPage.lastPage();"> </BR>
</BR>


</br>

<input value="����" type=button
	onclick="backToParent()" class="cssButton" type="button"></form>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
