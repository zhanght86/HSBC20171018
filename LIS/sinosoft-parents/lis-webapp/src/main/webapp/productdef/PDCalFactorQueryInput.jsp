<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDCalFactorQueryInput.jsp
 //�����ܣ�����Ҫ����ʾ��ѯ����
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
 
 <SCRIPT src="PDCalFactorQuery.js"></SCRIPT>
 <%@include file="PDCalFactorQueryInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDCalFactorQuerySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >��Ʒ�㷨Ҫ�ز�ѯ������</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>Ҫ�����</td>
		<td class=input5>
			<input class="codeno" name="FactorType" readonly="readonly" ondblclick="return showCodeList('pdfactortype',[this,FactorTypeName],[0,1]);" onkeyup="return showCodeListKey('pdfactortype',[this,FactorTypeName],[0,1]);" ><input class="codename" name="FactorTypeName" >
		</td>
		<td class=title5>����</td>
		<td class=input5>
			<input class="codeno" name="Kind" readonly="readonly" ondblclick="return showCodeList('pdproperty',[this,PropertyName],[0,1]);" onkeyup="return showCodeListKey('pdproperty',[this,PropertyName],[0,1]);" ><input class="codename" name="PropertyName" >
		</td>	
	</tr>
	<tr class=common>
		<td class=title5>ģ��</td>
		<td class=input5>
			<input class="codeno" name="Module" readonly="readonly" ondblclick="return showCodeList('pdmoduletype',[this,ModuleName],[0,1]);" onkeyup="return showCodeListKey('pdmoduletype',[this,ModuleName],[0,1]);" ><input class="codename" name="ModuleName" >
		</td>
		<td class=title5>Ҫ�ش���</td>
		<td class=input5>
			<input class=common name="FactorCode" >
		</td>
	</tr>
	<tr class=common>
		<td class=title5 style="display: none">Ҫ��ֵ��������</td>
		<td class=input5 style="display: none">
			<input class=common name="FactorValType">
		</td>
		<td class=title5>Ҫ�غ���˵��</td>
		<td class=input5>
			<input class=common name="FactorDesc">
		</td>
	</tr>
</table>
<input value="��ѯ" type=button  onclick="query()" class="cssButton" type="button" >
<input value="����" type=button  onclick="returnParent( )" class="cssButton" type="button" >
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

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
<input type=hidden name="selectTable">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
