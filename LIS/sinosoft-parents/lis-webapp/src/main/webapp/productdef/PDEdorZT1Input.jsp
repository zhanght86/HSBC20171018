<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDEdorZT1Input.jsp
 //�����ܣ������˱��������㷨����
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
 <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDEdorZT1.js"></SCRIPT>
 <%@include file="PDEdorZT1Init.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDEdorZT1Save.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>���ֱ���</td>
		<td class=input5>
			<input class="common" name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>��������</td>
		<td class=input5>
			<input class="common" name="RiskCodeName" readonly="readonly" >
		</td>
	</tr>
	<tr class=common>
		<td class=title5>��ȫ��Ŀ����</td>
		<td class=input5>
			<input class="common" name="EdorType" readonly="readonly" >
		</td>
		<td class=title5>��ȫ��Ŀ����</td>
		<td class=input5>
			<input class="common" name="EdorTypeName" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >��Ʒ������Ϣ:</td>
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
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >�˱���������</td>
</table>
<div align=right id=savabuttonid><input value="��  ��" type=button  onclick="save()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="update()" class="cssButton" type="button" >
<input value="ɾ  ��" type=button  onclick="del()" class="cssButton" type="button" ></td>
</div>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >�ѱ����˱���������</td>
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
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline11GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline11GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline11GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline11GridTurnPage.lastPage();">
</BR></BR>
<input id=savabutton1 value="�㷨����" type=button  onclick="button275( )" class="cssButton" type="button" >
<input id=savabutton2 value="�˱������޸�" type=button  onclick="ModifyZtduty( )" class="cssButton" type="button" >
<br><br>
<input value="��  ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br>
<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMEdorZT1">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
