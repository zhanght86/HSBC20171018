<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDPrvRepealAppInput.jsp
 //�����ܣ������ֹ����
 //�������ڣ�
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
 
 <SCRIPT src="PDPrvRepealApp.js"></SCRIPT>
 <%@include file="PDPrvRepealAppInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDPrvRepealAppSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >�������Ʒ���ִ��룺</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>�������</td>
		<td class=input5>
			<Input class=codeNo name=RiskCode ondblclick="return showCodeList('pdriskdefing',[this,RiskName],[0,1],null,fm.all('Type').value,fm.all('Type').value,1);" 
				onkeyup="return showCodeListKey('pdriskdefing',[this,RiskName],[0,1],null,fm.all('Type').value,null,1);"><input class=codename name=RiskName>		
		</td>
		<td class=title5>��������</td>
		<td class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" >
		</td>
	</tr>
	<tr class=common>
		<td class=common>
			<input value="��ѯ" type=button  onclick="queryDefing()" class="cssButton" type="button" >
		</td>
	</tr>
</table>

<table>
  <tr>
    <td class="titleImg" >������Ϣ��</td>
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
<input value="��ֹ����" type=button  onclick="beginDefine()" class="cssButton" type="button" >
<br><br>
<input type=hidden name="Type">
<input type=hidden name="operator">
<input type=hidden name="tableName" value="LMRisk">
<input type=hidden name=MissionID>
<input type=hidden name=SubMissionID>
<input type=hidden name=ActivityID>


</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
