<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDBaseFieldQueryInput.jsp
 //�����ܣ�������Ϣ�ֶβ�ѯ
 //�������ڣ�2009-3-18
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
 
 <SCRIPT src="PDBaseFieldQuery.js"></SCRIPT>
 <%@include file="PDBaseFieldQueryInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDBaseFieldQuerySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >��Ʒ�����ֶβ�ѯ��</td>
  </tr>
</table>
<table class=common>
	<TR class=common>
		<TD class=title5>��Ʒ��������</TD>
		<TD class=input5><input class="codeno" name="TableCode" ondblclick="return showCodeList('pdtablename',[this,TableName],[0,1]);" onkeyup="return showCodeListKey('pdtablename',[this,TableName],[0,1]);" readonly="readonly" verify="��Ʒ��������|notnull"><input class='codename' name = 'TableName' elementtype=nacessary verify="��Ʒ��������|notnull&len<=20">
		</TD>	
		<TD class=title5>��Ʒ�����ֶ���</TD>
		<TD class=input5><input class="codeno" name="FieldCode" ondblclick="return showCodeList('pdfieldname',[this,FieldName],[0,1],null,fm.all('TableCode').value,'tablecode');" onkeyup="return showCodeListKey('pdfieldname',[this,FieldName],[0,1],null,fm.all('TableCode').value,'tablecode');" readonly="readonly"><input class='codename' name = 'FieldName' verify="��Ʒ�����ֶ���|len<=20">
		</TD>	
	</TR>
	<TR class=common>
		<TD class=title5>�ֶ�ֵ��������</TD>
		<TD class=input5><input class="code" name="FieldType" ondblclick="return showCodeList('pdfieldtype',[this],[0],null,fm.all('TableCode').value,'tablecode');" onkeyup="return showCodeListKey(null,[this],[0],null,fm.all('TableCode').value,'tablecode');">
		</TD>
		<TD class=title5>�Ƿ���ʾ</TD>
		<TD class=input5><input class="codeno" name="IsDisplayCode" CodeData="0|^0|����ʾ^1|��ʾ" ondblclick="return showCodeListEx('pd_',[this,IsDisplayName],[0,1]);" onkeyup="return showCodeListKeyEx('pd_',[this,IsDisplayName],[0,1]);"><input class='codename' name = 'IsDisplayName' readonly="readonly">
		</TD>	
	</TR>
</table>
<table class=common>
	<TR class=common>
		<TD class=title5>�ٷ��ֶ�����</TD>
    <TD><textarea rows="3" cols="90" name="OfficialDesc"></textarea>
    </TD>
	</TR>   
	<TR class=common>
		<TD class=title5>ҵ����Ա��ע</TD>
    <TD><textarea rows="3" cols="90" name="BusiDesc"></textarea>
    </TD>
	</TR>
</table>
<input value="��ѯ" onclick="query()" class="cssButton" type="button" >
<br><br>
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
<input value="����" onclick="returnParent()" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
