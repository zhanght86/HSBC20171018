<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDDutyPayAddFeeInput.jsp
 //�����ܣ��������μӷѶ���
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
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDDutyPayAddFee.js"></SCRIPT>
 <%@include file="PDDutyPayAddFeeInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./PDDutyPayAddFeeSave.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
		
		<td class=title5>���ֽɷѱ���</td>
		<td class=input5>
			<input class=common name="PayPlanCode" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >�ѱ����������μӷ�����</td>
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

<table>
  <tr>
    <td class="titleImg" >�������μӷѶ���</td>
  </tr>
</table>
		
		
		
		
		
		
<table  class= common>
	<tr class= common>
        <TD  class= title>���ֱ���</TD>
        <TD  class= input>
			<Input class=common readonly="readonly" name=RISKCODE >
        </TD> 
        <TD  class= title>���δ���</TD>
        <TD  class= input>
						<Input class=common readonly="readonly" name=DUTYCODE >
						
        <TD  class= title>�ӷ����</TD>
        <TD  class= input>
					<Input class="codeno" name=ADDFEETYPE readonly="readonly" verify="�ӷ����|NOTNUlL" ondblclick="return showCodeList('pd_addfeetype',[this,ADDFEETYPEName],[0,1]);" onkeyup="return showCodeListKey('pd_addfeetype',[this,ADDFEETYPEName],[0,1]);"><input class=codename name=ADDFEETYPEName readonly="readonly">
        <font color=red>*</font>
        </TD> 
        </tr>
        <tr class= common>
        <TD  class= title>�ӷѷ�ʽ</TD>
        <TD  class= input>
					<Input class="codeno" name=ADDFEEOBJECT readonly="readonly" verify="�ӷѷ�ʽ|NOTNUlL" ondblclick="return showCodeList('pd_addfeeobject',[this,ADDFEEOBJECTName],[0,1]);" onkeyup="return showCodeListKey('pd_addfeeobject',[this,ADDFEEOBJECTName],[0,1]);"><input class=codename name=ADDFEEOBJECTName readonly="readonly">
          <font color=red>*</font>
        </TD> 


	 <TD  class= title STYLE="display:none;">
�ӷ��������ֵ
	 </TD>
        <TD  class= input STYLE="display:none;">
		<Input class=input5   name=ADDPOINTLIMIT  >
        </TD> 
    <TD  class= title>�ӷ��㷨</TD>
        <TD  class= input>
		<Input class=common   name=ADDFEECALCODE verify="�ӷ��㷨|LEN>=6&LEN<=10" >
        </TD> 
</tr>
</table>
<!--�㷨��������ҳ-->
<jsp:include page="CalCodeDefMain.jsp"/>
<hr>

<div align=left id=savabuttonid>
<input value="��  ��" type=button  onclick="initDetail()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="save()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="update()" class="cssButton" type="button" >
<input value="ɾ  ��" type=button  onclick="del()" class="cssButton" type="button" >
<input value="�ӷѷ��ʱ���" type=button  onclick="button136( )" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
</div>
<div align=left id=checkFunc>
<input value="�鿴�㷨����" type=button  onclick="InputCalCodeDefFace2()" class="cssButton" type="button" >
</div>

<!--table  class= common>
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
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline9GridTurnPage.lastPage();"-->
</BR></BR>

<!--input value="�ӷ��㷨����" type=button  onclick="button135( )" class="cssButton" type="button" -->

<br><br>

<br><br>

<input type=hidden name="operator">
<!--input type=hidden name="DutyCode"-->
<input type=hidden name="tableName" value="PD_LMDutyPayAddFee">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
