<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDRiskDutyFactorInput.jsp
 //�����ܣ�����¼��Ҫ�ض���
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
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDRiskDutyFactor.js"></SCRIPT>
 <%@include file="PDRiskDutyFactorInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskDutyFactorSave.jsp" method=post name=fm target="fraSubmit">

<table class=common>
	<tr class=common>
		<td class=title5>��Ʒ���ִ���</td>
		<td class=input5>
			<input type='hidden' name="RiskCode" readonly="readonly" >
			<input class=common  name="RiskCode1" value = '<%=request.getParameter("riskcode")%>'>
		</td>
		<td class=title5>���ֽɷѱ���</td>
		<td class=input5>
			<input class=common name="PayPlanCode" readonly="readonly" >
		</td>
	</tr>
</table>

<table>
  <tr>
    <td class="titleImg" >��������¼��Ҫ�ض���</td>
  </tr>
</table>
<table  class= common style="display: ''">
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanLMDutyParamGrid" >
     </span> 
      </td>
   </tr>
</table>
<table style="margin-left:600px;display: ''"><input value="����" type=button  onclick="save2(1)" class="cssButton" type="button" >
<input value="�޸�" type=button  onclick="save2(2)" class="cssButton" type="button" >
<input value="ɾ��" type=button  onclick="save2(3)" class="cssButton" type="button" ></table>
<div style="display:none;">
<table  class= common style="display:none;">
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<br>
<table style="margin-left:600px;display:none">
<input value="����" type=button  onclick="save()" class="cssButton" type="button" >
<input value="�޸�" type=button  onclick="update()" class="cssButton" type="button" >
<input value="ɾ��" type=button  onclick="del()" class="cssButton" type="button" >
</table>
</div>

<table style="display:none;">
  <tr>
    <td class="titleImg" >�ѱ�����������¼��Ҫ��</td>
  </tr>
</table>
<table  class= common style="display:none;">
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline10Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��ҳ" TYPE=button onclick="Mulline10GridTurnPage.firstPage();" style="display:none;"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.previousPage();" style="display:none;">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.nextPage();" style="display:none;"> 
<INPUT CLASS=cssbutton VALUE="βҳ" TYPE=button onclick="Mulline10GridTurnPage.lastPage();" style="display:none;">
</tr>
</BR>
<br>
<div id=calFactorSub style="display:none;">
<table>
  <tr>
    <td class="titleImg" >Ҫ��������Ϣ</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline12Grid" >
     </span> 
      </td>
   </tr>
</table>
<table style="margin-left:600px;"> <input value="����" type=button  onclick="saveCalFactorSub()" class="cssButton" type="button" >
<input value="�޸�" type=button  onclick="updateCalFactorSub()" class="cssButton" type="button" >
<input value="ɾ��" type=button  onclick="delCalFactorSub()" class="cssButton" type="button" ></table>

</div>

<br>


<br>

<input type=hidden name="operator">
<input type=hidden name="DutyCode">
<input type=hidden name="tableName" value="PD_LMRiskDutyFactor">
<input type=hidden name=IsReadOnly>
<input type=hidden name=submitFlag>
<input type=hidden name="tableName1" value="ldcode">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
