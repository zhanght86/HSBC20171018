<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDDutyPayInput.jsp
 //�����ܣ����νɷѶ���
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
 
 <SCRIPT src="PDDutyPay.js"></SCRIPT>
 <%@include file="PDDutyPayInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDDutyPaySave.jsp" method=post name=fm target="fraSubmit">
<br>
<TABLE  class="common" >
<tr class="common">
<td class="title">��Ʒ���ֱ���</td><td class="input"><input class="readonly" name="RiskCode" readonly="readonly" ></td>
<td class="title">�ɷ����δ���</td><td class="input"><input class="readonly" name="PayPlanCode" readonly="readonly"  ></td>  
</tr>
</TABLE>
<table>
  <tr>
    <td class="titleImg" >���νɷ����Զ���: </td>
  </tr>
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
<input value="�㷨����" type=button  onclick="button122( )" class="cssButton" type="button" >
<br><br>
<input value="����" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>
<input type=hidden name="operator">
<input type=hidden name="tableName" value="pd_LMDutyPay">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
