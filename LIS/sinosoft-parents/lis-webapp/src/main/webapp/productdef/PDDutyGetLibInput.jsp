<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDDutyGetLibInput.jsp
 //�����ܣ��������ο�
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
  <SCRIPT src="DictionaryUtilities.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDDutyGetLib.js"></SCRIPT>
 <%@include file="PDDutyGetLibInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDDutyGetLibSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >�������ο��ѯ������</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>���������δ���</td>
		<td class=input5>
			<input name="GetDutyCode2" class=common>
		</td>
		<td class=title5>��������������</td>
		<td class=input5>
			<input name="GetDutyName" class=common>
		</td>	
		<td class=title5>��������</td>
		<td class=input5>
			<input class="code" name="Type" ondblclick="return showCodeList('lloccurreason',[this],[0]);" onkeyup="return showCodeListKey('lloccurreason',[this],[0]);" >
		</td>
	</tr>
	<tr>
		<td class=title5>��������</td>
		<td class=input5>
			<input class="code" name="RiskCode" ondblclick="return showCodeList('pdriskdeployed',[this],[0]);" onkeyup="return showCodeListKey('pdriskdeployed',[this],[0]);" >
		</td>	
		<td class=title5>������������</td>
		<td class=input5>
			<input class="code" name="GetDutyCode" ondblclick="return showCodeList('pdGetDutyCode',[this],[0],null,fm.RiskCode.value,'riskcode');" onkeyup="return showCodeListKey('pdGetDutyCode',[this],[0],null,fm.RiskCode.value,'riskcode');" >
		</td>
	</tr>
</table>

<table>
	<tr>
		<td><input value="��ѯ" type=button  onclick="query()" class="cssButton" type="button" ></td>
		<td><div id='divhiddenParam' style= "display:none;">
				<input value="����" type=button  name=btnReturn onclick="returnParent( )" class="cssButton" type="button" >
			</div>
		</td>
	</tr>
</table>	

<br>
<table>
  <tr>
    <td class="titleImg" >�������ο��ѯ�����</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline11Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��ҳ" TYPE=button onclick="Mulline11GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline11GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline11GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="βҳ" TYPE=button onclick="Mulline11GridTurnPage.lastPage();">
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >�������ι��������</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline10Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��ҳ" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="βҳ" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >���θ������Զ���:</TD><td width=500></td><TD><input value="����" type=button name=btnSave onclick="save()" class="cssButton" type="button" ><input value="�޸�" type=button name=btnEdit onclick="update()" class="cssButton" type="button" ><input value="ɾ��" type=button name=btnDele onclick="del()" class="cssButton" type="button" ></td>
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
<input type=hidden name="tableName" value="PD_LMDutyGet_Lib">
<input type=hidden name="GetDutyCode2RowOfMulLine">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
