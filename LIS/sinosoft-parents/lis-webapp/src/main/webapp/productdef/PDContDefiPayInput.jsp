<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDContDefiEntryInput.jsp
 //�����ܣ���Լ��Ϣ�������
 //�������ڣ�2009-3-13
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
  <SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
  <script src="PDCommonJS.js"></script>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDContDefiPay.js"></SCRIPT>
 <%@include file="PDContDefiPayInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDContDefiEntrySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >��Ʒ������Ϣ��</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>��Ʒ���ִ���</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>��������</td>
		<td class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >��Ʒ������Ϣ��</td>
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
<table>
  <tr>
    <td class="titleImg" >���β㼶����</td>
  </tr>
</table>
<input value="���ʱ���" type=button  onclick="button100( )" class="cssButton" type="button" >
<!--  
<input value="�������νɷ��㷨" type=button onclick="button101( )" class="cssButton" type="button" >
<input value="�ɷ��ֶο��ƶ���" type=button id="btnPayFieldCtrl" onclick="button102( )" class="cssButton" type="button" >
<input value="�����ֶο��ƶ���" type=button id="btnGetFieldCtrl" onclick="button103( )" class="cssButton" type="button" >
<input value="�������μӷѶ���" type=button  onclick="button104( )" class="cssButton" type="button" >
-->
<input value="����¼��Ҫ�ض���" type=button  onclick="button105( )" class="cssButton" type="button" >
<br><br>
<input value="��һ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="IsDealWorkflow">
<input type=hidden name="tableName" value="LMRisk">
<input type=hidden name="SimpleContPara">
<input type=hidden name=MissionID>
<input type=hidden name=SubMissionID>
<input type=hidden name=ActivityID>
<input type=hidden name=IsReadOnly>
<input type=hidden name=PageNo value=101>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
