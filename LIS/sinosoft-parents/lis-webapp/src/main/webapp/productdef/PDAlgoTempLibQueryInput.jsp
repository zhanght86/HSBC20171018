<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDAlgoTempLibQueryInput.jsp
 //�����ܣ��㷨ģ����ѯ����
 //�������ڣ�2009-3-18
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
 
 	<SCRIPT src="PDAlgoTempLibQuery.js"></SCRIPT>
 	<%@include file="PDAlgoTempLibQueryInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDAlgoTempLibQuerySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >��Ʒ�㷨Ҫ�ز�ѯ������</td>
  </tr>
</table>
<table  class=common>
	<tr class=common>
		<td class=title5>�㷨ģ�����</td>
		<td class=input5>
			<input class="common8" name="AlogTempCode" >
		</td>
		<td class=title5>�㷨ģ������</td>
		<td class=input5>
			<input class="common8" name="AlogTempName">
		</td>
	</tr>
	<tr class=common>
		<td class=title5>�㷨ģ������</td>
		<td class=input5>
			<input class="code" name="Type" ondblclick="return showCodeList('algotemptype',[this],[0]);" onkeyup="return showCodeListKey('algotemptype',[this],[0]);">
		</td>
	
	
		<td class=title5>�㷨ģ������</td>
		<td class=input5>
			<input class="common" name="Content" >
		</td>
	<tr class = common>
		<td class=title5>�㷨ģ�屸ע</td>
		<td class=input5>
			<input class="common" name="Description" >
		</td>
	</tr>
</table>

<input value="��ѯ" onclick="query()" class="cssButton" type="button">
<input value="����" onclick="retResult( )" class="cssButton" type="button">
<br><br>
<table>
  <tr>
    <td class="titleImg" >��ѯ���</td>
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

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
