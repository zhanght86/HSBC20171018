<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDPublRuleInput.jsp
 //�����ܣ��㷨�������
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
 
 <SCRIPT src="PDPublRule.js"></SCRIPT>
 <%@include file="PDPublRuleInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDPublRuleSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >�ѱ���������㷨</td>
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
</BR>
<div align=right><input value="����" type=button  onclick="save()" class="cssButton" type="button" >
<input value="�޸�" type=button  onclick="update()" class="cssButton" type="button" >
<input value="ɾ��" type=button  onclick="del()" class="cssButton" type="button" ></div>
<br>
�㷨����
<input class="common" name="AlgoCode" ><font color=red>*</font>
���ֱ���
<input class="readonly" name="RiskCode" readonly="readonly" value='000000' ><BR>
�㷨����
<input class="codeno" name="AlgoType" value = 'U'  readonly="readonly" ><input class="codename" name="AlgoTypeName" value ='�˱�'' readonly="readonly" > 
�㷨����
<input class="common" name=AlgoDesc>
<table width=100%>
  <tr>
    <td class="titleImg" >�㷨����<input value="��ѯ�㷨ģ��" type=button  onclick="queryAlgoTemp( )" class="cssButton" type="button" ></td>
  </tr>
  <tr width=100%>
  	<td width=100%><textarea rows=3 cols=100 name=AlgoContent ></textarea><font color=red>*</font></td>
  </tr>
</table>
<table>
  <tr>
    <td class="titleImg" >�㷨���û���Ҫ�أ�<input value="��ѯ" type=button  onclick="query()" class="cssButton" type="button" ></td>
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
<input value="ɾ��Ҫ��" type=button  onclick="button330( )" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >����Զ������㷨</td>
  </tr>
</table>
���㷨����<input type=common name=SubAlgoCode><font color=red>*</font>
���㷨����<input type=common name=SubAlgoName><font color=red>*</font>
<input value="���" type=button  onclick="add( )" class="cssButton" type="button" >
<br>
<table>
  <tr>
    <td class="titleImg" >���㷨˵��</td>
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
<input value="���㷨���Զ���" type=button  onclick="subAlgoDefi()" class="cssButton" type="button" >
<input value="����" type=button  onclick="test()" class="cssButton" type="button" >
<br><br>
<input value="����" type=hidden  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
