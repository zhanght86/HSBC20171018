<%@include file="../i18n/language.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDAccGuratRateInput.jsp
 //程序功能：保证利率录�? //创建日期�?009-3-17
 //创建�? �? //更新记录�? 更新�?   更新日期     更新原因/内容
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
 
 <SCRIPT src="PDAccGuratRate.js"></SCRIPT>
 <%@include file="PDAccGuratRateInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDAccGuratRateSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >�˻����ʣ�</td>
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
    <td class="titleImg" >�������Զ��壺</TD><TD width=200></TD><TD></TD><TD width=200></TD><TD></TD><TD width=200></TD><TD><input value="����" type=button  onclick="save()" class="cssButton" type="button" ><input value="�޸�" type=button  onclick="update()" class="cssButton" type="button" ><input value="ɾ��" type=button  onclick="del()" class="cssButton" type="button" ></td>
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
<input value="����" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="RiskCode">
<input type=hidden name="InsuAccNo">
<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMAccGuratRate">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
