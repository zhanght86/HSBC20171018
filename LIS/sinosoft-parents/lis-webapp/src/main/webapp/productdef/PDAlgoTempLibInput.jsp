<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDAlgoTempLibInput.jsp
 //�����ܣ��㷨ģ���
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
 
 <SCRIPT src="PDAlgoTempLib.js"></SCRIPT>
 <%@include file="PDAlgoTempLibInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDAlgoTempLibSave.jsp" method=post name=fm target="fraSubmit">

<div align=right>
	<input value="����" name="btnSave" onclick="save()" class="cssButton" type="button" >
	<input value="�޸�" name="btnEdit" onclick="update()" class="cssButton" type="button" >
	<input value="��ѯ" name="btnQuery" onclick="query()" class="cssButton" type="button" >
	<input value="ɾ��" name="btnDelete" onclick="del()" class="cssButton" type="button" >
</div>
<br>
<table>
  <tr>
    <td class="titleImg" >�㷨ģ���</td>
  </tr>
</table>
<table  class=common>
	<tr class=common>
	  <td class=title5>�㷨ģ�����</td>
	  <td class=input5>
	  	<input class=common8 verify="�㷨ģ�����|NotNull&len<21" name="AlgoTempCode" elementtype=nacessary>
	  </td>
	  <td class=title5>�㷨ģ������</td>
	  <td class=input5>
	  	<input class=common8 name="AlgoTempName">
	  </td>
	  <td class=title5>�㷨ģ������</td>
	  <td class=input5>
	  	<input class="codeno" verify="�㷨ģ������|NotNull" name="AlgoTempType" ondblclick="return showCodeList('algotemptype',[this,AlgoTempTypeName],[0,1]);" onkeyup="return showCodeListKey('algotemptype',[this,AlgoTempTypeName],[0,1]);" ><input class="codename" name="AlgoTempTypeName" elementtype=nacessary>
	  </td>
	</tr>
</table>
<table  class=common>
	<tr class=common>
		<td class=title5>�㷨ģ������</td>
		<td class=common8>
			<textarea rows="3" cols="50" name="AlgoTempContent" verify="�㷨ģ������|NotNull" elementtype=nacessary></textarea>
			</td>
	</tr>
	<tr class=common>
		<td class=title5>�㷨ģ������</td>
		<td class=common8>
			<textarea rows="3" cols="50" name="AlgoTempDescription"></textarea>
		</td>
	</tr>
</table>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_AlgoTempLib">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
