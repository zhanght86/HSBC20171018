<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDClaimDefiInput.jsp
 //�����ܣ����θ����������
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
  <SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDClaimDefi.js"></SCRIPT>
 <%@include file="PDClaimDefiInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDClaimDefiSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >���θ������Զ���</td>
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
<input value="�����㷨����" type=button onclick="button224( )" class="cssButton" type="button" >
<input value="���θ�������" type=button id="btnClmAlive" name="btnGetAlive" onclick="button225( )" class="cssButton" type="button" >
<input value="���θ����⸶" type=button  onclick="button226( )" class="cssButton" type="button" >
<br><br>
<!--input value="�⸶���ƶ���" type=button  onclick="button227( )" class="cssButton" type="button" >
<input value="�⸶���ö���" type=button  onclick="button228( )" class="cssButton" type="button" >
<input value="�⸶ʱ�ڶ���" type=button  onclick="button230( )" class="cssButton" type="button" >
<br><br>
<input value="�����˻�����" type=button  onclick="button231( )" class="cssButton" type="button" >
<input value="�����⸶����" type=button  onclick="button232( )" class="cssButton" type="button" >
<br><br-->
<input value="����" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>
<input type=hidden name="RiskCode">
<input type=hidden name="GetDutyCode">
<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMDutyGet">
<input type=hidden name=IsReadOnly>
<input type=hidden name=PageNo value=131>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
