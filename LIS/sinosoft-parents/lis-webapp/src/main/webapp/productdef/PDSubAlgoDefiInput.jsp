<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDSubAlgoDefiInput.jsp
 //�����ܣ����㷨�������
 //�������ڣ�2009-3-17
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
 
 <SCRIPT src="PDSubAlgoDefi.js"></SCRIPT>
 <%@include file="PDSubAlgoDefiInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDSubAlgoDefiSave.jsp" method=post name=fm target="fraSubmit">

<div align=right><input value="����" type=button  onclick="save()" class="cssButton" type="button" >
<input value="�޸�" type=button  onclick="update()" class="cssButton" type="button" >
<input value="ɾ��" type=button  onclick="del()" class="cssButton" type="button" ></div>
<br>
<table class=common>
	<tr class=common>
		<td class=title5>
�㷨����
		</td>
		<td class=common>
			<input class="common" name="AlgoCode" readonly="readonly" >
		</td>
		<td class=title5>
���㷨����
		</td>
		<td class=common>
			<input class=common name="SubAlgoCode" readonly="readonly" >
		</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>
���㷨����
		</td>
		<td class=common>
			<input class=common name=SubAlgoName>
		</td>
		<td class=title5>
���㷨����
		</td>
		<td class=common>
			<input class="codeno" readonly="readonly" name="SubAlgoType" ><input class="codename" name="SubAlgoTypeName" >
		</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>
���㷨���ȼ�
		</td>
		<td class=common>
			<input class=common name= SubAlgoGrade>
		</td>
		<td></td><td></td>
	</tr>
</table>
<br>
<br><input value="���㷨�㷨����" type=button  onclick="button336( )" class="cssButton" type="button" >

<input value="����" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
<input type=hidden name="RiskCode">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
