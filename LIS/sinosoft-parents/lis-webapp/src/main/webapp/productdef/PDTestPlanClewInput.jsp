<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDTestPointClewInput.jsp
 //�����ܣ�����Ҫ����ʾ
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
 
 <SCRIPT src="PDTestPlanClew.js"></SCRIPT>
 <%@include file="PDTestPlanClewInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDTestPlanClewSave.jsp" method=post name=fm target="fraSubmit">

<div align=right>
	<input value="����" onclick="save()" class="cssButton" type="button" >
	<input value="�޸�" onclick="update()" class="cssButton" type="button" >
	<input value="��ѯ" onclick="queryMulline9Grid()" class="cssButton" type="button" >
	<input value="ɾ��" onclick="del()" class="cssButton" type="button" >
</div>
<br>
<table>
  <tr>
    <td class="titleImg" >��Ʒ���Է�������</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<TD class=title5>���Լƻ�����</TD>
		<TD class=input5>
			<input class="codeno" id='plankind' name="planKind" ondblclick="return showCodeList('planKind',[this,planKindName],[0,1]);" onkeyup="return showCodeListKey('planKind',[this,planKindName],[0,1]);" readonly="readonly" verify="���Լƻ�����|notnull"><input class='codename' name='planKindName' elementtype=nacessary verify="���Լƻ�����|notnull" readonly="readonly" >
		</TD>	
		<TD class=title5 style="display:none;" id='riskKindTitle'>���ַ���</TD>
		<TD class=input5 style="display:none;" id='riskKindContent'>
			<input class="codeno" name="riskkind" ondblclick="return showCodeList('riskkind',[this,riskKindName],[0,1]);" onkeyup="return showCodeListKey('riskkind',[this,riskKindName],[0,1]);" readonly="readonly"><input class='codename' name = 'riskKindName' readonly="readonly">
		</TD>
		<TD ></TD>
		<TD ></TD>	
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>����Ҫ��</td>
		<td class=common>
		<textarea name=ClewContent cols=60 rows=3 elementtype=nacessary></textarea>
		</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>��ע</td>
		<td class=common>
		<textarea name=Remark cols=60 rows=3></textarea>
		</td>
	</tr>
</table>
<br>

<input type=hidden name="operator">
<input type=hidden name="clewcontentcode">
<input type=hidden name="tTableName" value="PD_TestPlanClew_Lib">

<table>
  <tr>
    <td class="titleImg" >��ѯ���</td>
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

</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
pan>
</body>
</html>
