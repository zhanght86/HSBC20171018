<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDProdAudiDetailInput.jsp
 //�����ܣ���Ʒ�����ϸ
 //�������ڣ�2009-3-18
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
 session.setAttribute("LoadFlagButton","0"); 
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
 
 <SCRIPT src="PDProdAudiDetail.js"></SCRIPT>
 <%@include file="PDProdAudiDetailInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDProdAudiDetailSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >���ֽɷ�����</td>
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
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >���ָ�������</td>
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
<input value="�鿴��Ʒ��������" type=button  onclick="button391( )" class="cssButton" type="button" >
<input value="�鿴��Լ����" type=button  onclick="button392( )" class="cssButton" type="button" >
<input value="�鿴��ȫ����" type=button  onclick="button393( )" class="cssButton" type="button" >
<input value="�鿴���ⶨ��" type=button  onclick="button394( )" class="cssButton" type="button" >
<input value="�鿴����ᱨ����" onclick="button395( )" class="cssButton" type="hidden" >
<input value="�鿴���۶���"  onclick="button396( )" class="cssButton" type="hidden" >
<!--<input value="�鿴�������Ϣ" onclick="button395()" class="cssButton" type="button">-->
<br><br>
<input value="���±�" onclick="button397( )" class="cssButton" type="hidden" >
<input value="�������ѯ" type=button  onclick="IssueQuery( )" class="cssButton" type="button" >
<input value="�����¼��" type=button  onclick="IssueInput( )" class="cssButton" type="button" >
<br><br>
<input value="������" type=button  onclick="btnAuditDone( )" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
<input type=hidden name="RiskCode">
<input type=hidden name=MissionID>
<input type=hidden name=SubMissionID>
<input type=hidden name=ActivityID>
<input type=hidden name=PdFlag>
<input type=hidden name=RequDate>
<input type=hidden name=IsReadOnly>
<input type=hidden name=RiskName>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
