<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDCheckRuleInput.jsp
 //�����ܣ���������
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
 
 <SCRIPT src="PDCheckRule.js"></SCRIPT>
 <%@include file="PDCheckRuleInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDCheckRuleSave.jsp" method=post name=fm target="fraSubmit">

<div align=right>
	<input value="����" name="btnSave" onclick="save()" class="cssButton" type="button" >
	<input value="�޸�"  onclick="update()" class="cssButton" type="button" >
	<input value="��ѯ"  onclick="query()" class="cssButton" type="button" >
	<input value="ɾ��"  onclick="del()" class="cssButton" type="button" >
	<input value="����"  onclick="initForm()" class="cssButton" type="button" >
</div>
<table>
  <tr>
    <td class="titleImg" >У�������ϸ��Ϣ��</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>������</td>
		<td class=input5>
			<INPUT class=common name="CheckRuleCode" readonly="readonly">
		</td>
		<td class=title5>��������</td>
		<td class=input5>
			<INPUT class=common name="CheckRuleName" >
		</td>
		<td class=title5>���</td>
		<td class=input5>		 	 
			<input class="code" name="Type" verify="���|NotNull" ondblclick="return showCodeList('pdcheckruletype',[this],[0]);" onkeyup="return showCodeListKey('pdcheckruletype',[this],[0]);" elementtype=nacessary>
		</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>�����㷨</td>
		<td class=common>
			<textarea rows="5" cols="60" name="Algo" elementtype=nacessary></textarea>
		</td>
	</tr>
	<tr class=common>
		<td class=title5>��ע</td>
		<td class=common>
			<textarea rows="3" cols="60" name="Description"></textarea>
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >У������ѯ�����</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��ҳ" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="βҳ" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_CheckRule_Lib">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
