<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<%
 //�������ƣ�PDRateCashValueInput.jsp
 //�����ܣ����ݱ���ֽ��ֵ����
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
 <SCRIPT src="PDRateCashValue.js"></SCRIPT>
 <%@include file="PDRateCashValueInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRateCashValueSave.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>���ִ���</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>�ɷ����α���</td>
		<td class=input5>
			<input class=common name="PayCode" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >��ѡ�����ݱ�����Ҫ�أ�</td>
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
</BR>
<input value="��  ��" type=button  onclick="addRate( )" class="cssButton" type="button" >
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >��ѡ������ݱ�Ҫ�أ�</td>
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
</BR>
<table class=common>
	<tr class=common>
		<td class=title5>���ݱ�����</td>
		<td class=input5>
			<input class=common name="DataTBLName" >
		</td>
		<td class=title5>����С�����λ��</td>
		<td class=input5>
			<Input class=codeNo name=PremDataType ondblclick="return showCodeList('pdpremdatatype',[this,PremDataTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('pdpremdatatype',[this,PremDataTypeName],[0,1],null,null,null,1);"><input class=codename name=PremDataTypeName readonly="readonly" elementtype="nacessary">
		</td>
	</tr>
</table>
<input value="�������ݱ�" type=button  onclick="createRateTable( )" class="cssButton" type="button" >
<br></BR>
<table>
  <tr>
    <td class="titleImg" >�������ݱ���Ϣ</TD><!--TD width=200></TD><TD></TD><TD width=200></TD><TD><input value="��������ҳ��" type=button  onclick="button310( )" class="cssButton" type="button" ></td-->
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
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline11GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline11GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline11GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline11GridTurnPage.lastPage();">
</BR>
<input value="ɾ�����ݱ�" type=button  onclick="deleteRateTable( )" class="cssButton" type="button" >
<!-- <input value="���ݱ�ģ������" type=button  onclick="downloadRateTable( )" class="cssButton" type="button" > -->
<br><br>

<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline15Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline15GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline15GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline15GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline15GridTurnPage.lastPage();">
</BR>

<input type=hidden name=newTableName>
<input type=hidden name=operator>
<input type=hidden name="tableName" value="Pd_Scheratecalfactor_Lib">
<input type=hidden name=IsReadOnly>
</form>

<form name=uploadForm enctype="multipart/form-data" method=post action="PDImportExcelSave.jsp" target="fraSubmit">
<table>
  <tr>
    <td class="titleImg" >���ݱ���&nbsp;<Input type="file" style="background-color: #F7F7F7;border: 1px #799AE1 solid;height: 20px;width: 180px;"  width="100%" name=FileName
			value="sdf"><input value="����" type=button  onclick="ImportExcel( )" class="cssButton" type="button" ></td>
  </tr>
</table>
<input type=hidden name=IsReadOnly>
</form>

<form name=dealUpload method=post action="PDImportExcelSave.jsp" target="fraSubmit">
<input type=hidden name=targetFileName >
<input type=hidden name=IsUpload>
<input type=hidden name=newTableName>
<input type=hidden name=IsReadOnly>
</form>

<!--form name=fm2 action="./PDRateCashValueSave.jsp" target="fraSubmit" method=post>
<table>
  <tr>
    <td class="titleImg" >�����ֽ��ֵ����Գ����գ�</td>
  </tr>
</table>
�ֽ��ֵ���ѡҪ��
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline12Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline12GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline12GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline12GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline12GridTurnPage.lastPage();">
<br>
<input value="��  ��" type=button  onclick="addCV( )" class="cssButton" type="button" >
<br><br>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline13Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline13GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline13GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline13GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline13GridTurnPage.lastPage();">
<br><br>
�ֽ��ֵ��������<Input class=codeNo name=CashValueDataType ondblclick="return showCodeList('pdcashvaluedatatype',[this,CashValueDataTypeName],[0,1],null,null,null,1);" 
onkeyup="return showCodeListKey('pdcashvaluedatatype',[this,CashValueDataTypeName],[0,1],null,null,null,1);"><input class=codename 
name=CashValueDataTypeName readonly="readonly" >
<input value="�����ֽ��ֵ���" type=button  onclick="createCashValue( )" class="cssButton" type="button" >
<br>
<table>
  <tr>
    <td class="titleImg" >�����ֽ��ֵ����Ϣ</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline14Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline14GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline14GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline14GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline14GridTurnPage.lastPage();">
<br><br>
<input value="ɾ���ֽ��ֵ��" type=button  onclick="deleteCVTable( )" class="cssButton" type="button" >
<input value="�ֽ��ֵ��ģ������" type=button  onclick="downloadCVTable( )" class="cssButton" type="button" >
<br><br>
<input type=hidden name="tableName" value="Pd_Scheratecalfactor_Lib">
<input type=hidden name=newTableName>
<input type=hidden name="operator">
<input type=hidden name="RiskCode">
<input type=hidden name=IsReadOnly>
</form>

<form name=uploadCV method=post target=fraSubmit enctype="multipart/form-data" action="PDImportExcelSave.jsp">
<table>
  <tr>
    <td class="titleImg" >�ֽ��ֵ����<Input type="file" width="100%" name=FileName style="background-color: #F7F7F7;border: 1px #799AE1 solid;height: 20px;width: 180px;"
			value=""><input value="����" type=button  onclick="ImportCVExcel( )" class="cssButton" type="button" ></td>
  </tr>
</table>
<input value="��  ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>
<input type=hidden name=newTableName>
<input type=hidden name="operator">
<input type=hidden name=IsReadOnly>
</form-->

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>