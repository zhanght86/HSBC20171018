<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDRiskAppDefiInput.jsp
 //�����ܣ����ֳб�����
 //�������ڣ�2009-3-12
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

 <SCRIPT src="PDRiskAppDefi.js"></SCRIPT>
 <%@include file="PDRiskAppDefiInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskAppDefiSave.jsp" method=post name=fm target="fraSubmit">

<table class=common>
	<TR class=common>
		<TD class=title5>���ֱ���</TD>
		<TD class=input5>
			<input class=common name=RiskCode readonly="readonly">
		</TD>
		<TD class=title5>��������</TD>
		<TD class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" readonly="readonly">
		</TD>
	</TR>
</Table>		
<table>
  <tr>
    <td class="titleImg" width=85%>���ֳб����Զ���:</td>
    <td>
    	<div align=right>
    		<input value="����" name=save type=button onclick="saveData()" class="cssButton" type="button" ><input value="�޸�" type=button  onclick="update()" class="cssButton" type="button" ><input value="ɾ��" type=button  onclick="del()" class="cssButton" type="button" >
    	</div>
    </td>
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

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMRiskApp">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
