<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDScheRateCalFactorInput.jsp
 //�����ܣ����ʱ�Ҫ�ؿ�
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
 
 <SCRIPT src="PDScheRateCalFactor.js"></SCRIPT>
 <%@include file="PDScheRateCalFactorInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDScheRateCalFactorSave.jsp" method=post name=fm target="fraSubmit">

<div align=right>
	<input value="����" name="btnSave" onclick="save()" class="cssButton" type="button" >
	<input value="�޸�" onclick="update()" class="cssButton" type="button" >
	<input value="��ѯ" onclick="query()" class="cssButton" type="button" >
	<input value="ɾ��" onclick="del()" class="cssButton" type="button" >
	<input value="����" onclick="initForm()" class="cssButton" type="button" >
</div>
<br>
<table>
  <tr>
    <td class="titleImg" >���ʱ�Ҫ�ؿ⽨�裺</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>Ҫ������</td>
		<td class=input5>
		<input class=common name="FactorName">
		</td>
		<td class=title5>Ҫ�ش���</td>
		<td class=input5>
		<input class=common name="FactorCode" verify="Ҫ�ش���|NotNull" elementtype=nacessary>
		</td>
	</tr>
	<tr class=common>
		<td class=title5>Ҫ����������</td>
		<td class=input5>
		<input class=common name="FactorDataType" verify="Ҫ����������|NotNull" elementtype=nacessary>
		</td>
		<td class=title5>Ҫ������</td>
		<td class=input5>
		<input class="code" name="FactorType" verify="Ҫ������|NotNull"  ondblclick="return showCodeList('pdfactorproperty',[this],[0]);" onkeyup="return showCodeListKey('pdfactorproperty',[this],[0]);" elementtype=nacessary>
		</td>
	</tr>
	
</table>

<table>
  <tr>
    <td class="titleImg" >���ʱ�Ҫ�ز�ѯ���:</td>
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
<input type=hidden name="tableName" value="PD_ScheRateCalFactor_Lib">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
