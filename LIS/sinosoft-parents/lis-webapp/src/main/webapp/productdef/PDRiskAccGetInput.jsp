<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDRiskAccGetInput.jsp
 //�����ܣ������˻�����
 //�������ڣ�2009-3-16
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
    String mRiskCode =request.getParameter("riskcode");
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
  <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDRiskAccGet.js"></SCRIPT>
 <%@include file="PDRiskAccGetInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskAccGetSave.jsp" method=post name=fm target="fraSubmit">
<table>
  <tr>
    <td class="titleImg" >�����µ��˻���ϸ</td>
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
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >�ѱ���������˻�����</td>
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
<!--input value="�ʻ�����Ѷ���" type=button  onclick="button184( )" class="cssButton" type="button" -->
<br><br>

<table>
  <tr>
    <td class="titleImg" >�����˻���������</td>
  </tr>
</table> 
<table  class= common>
	<tr class= common>
		<TD  class= title>���ֱ���</TD>
        <TD  class= input>
						<Input class=readonly readonly="readonly"   name=RISKCODE  >
        </TD> 
        <TD  class= title>��������</TD>
         <TD  class= input>
					<Input class="codeno" name=GETDUTYCODE readonly="readonly" verify="��������|NOTNUlL" ondblclick="return showCodeList('pd_getdutycode',[this,GETDUTYNAME],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);" onkeyup="return showCodeListKey('pd_getdutycode',[this,GETDUTYNAME],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"><input class=codename name=GETDUTYNAME readonly="readonly">

        </TD> 
        <TD  class= title>�����ʻ�����</TD>
        <TD  class= input>
					<Input class="codeno" name=INSUACCNO readonly="readonly" verify="�ӷ�����|NOTNUlL" ondblclick="return showCodeList('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);" onkeyup="return showCodeListKey('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"><input class=codename name=INSUACCNOName readonly="readonly">

        </TD> 
         <TD  class= title STYLE="display:none;">Ĭ�ϱ���</TD>
        <TD  class= input STYLE="display:none;">
						<Input class=common verify="Ĭ�ϱ���|num"  name=DEFAULTRATE  value="">
        </TD> 
    </tr>
    <tr class= common>
        <TD  class= title STYLE="display:none;">�Ƿ���Ҫ¼��</TD>
        <TD  class= input STYLE="display:none;">
					<Input class="codeno" name=NEEDINPUT readonly="readonly" verify="�Ƿ���Ҫ¼��|NOTNUlL" value="0" ondblclick="return showCodeList('pd_zeroone',[this,NEEDINPUTName],[0,1]);" onkeyup="return showCodeListKey('pd_zeroone',[this,NEEDINPUTName],[0,1]);"><input class=codename name=NEEDINPUTName value="0" readonly="readonly">

        </TD> 
        <TD  class= title STYLE="display:none;">ת���˻�ʱ���㷨����</TD>
        <TD  class= input STYLE="display:none;">
						<Input class=common   name=CALCODEMONEY >
        </TD> 
        <TD  class= title STYLE="display:none;">������</TD>
        <TD  class= input STYLE="display:none;">
			<Input class="codeno" name=DEALDIRECTION readonly="readonly" value="1"
				ondblclick="return showCodeList('pd_zeroone',[this,DEALDIRECTIONName],[0,1]);" 
				onkeyup="return showCodeListKey('pd_zeroone',[this,DEALDIRECTIONName],[0,1]);"><input class=codename name=DEALDIRECTIONName value="1" readonly="readonly">
        </TD> 

	<TD  class= title STYLE="display:none;">�˻�ת�������־</TD>
        <TD  class= input STYLE="display:none;">
			<Input class="codeno" name=CALFLAG readonly="readonly" value="0" ondblclick="return showCodeList('pd_zeroone',[this,CALFLAGName],[0,1]);" onkeyup="return showCodeListKey('pd_zeroone',[this,CALFLAGName],[0,1]);"><input class=codename value="0" name=CALFLAGName readonly="readonly">
        </TD> 
  	</tr>
	<tr class= common>
   <TD  class= title STYLE="display:none;">�˻�����λ��</TD>
        <TD  class= input STYLE="display:none;">
					<Input class="codeno" name=ACCCREATEPOS value="1" readonly="readonly" ondblclick="return showCodeList('pd_acccreatepos',[this,ACCCREATEPOSName],[0,1]);" onkeyup="return showCodeListKey('pd_acccreatepos',[this,ACCCREATEPOSName],[0,1]);"><input class=codename name=ACCCREATEPOSName  value="Ͷ����¼��ʱ����" readonly="readonly">
        </TD> 
        
	
	</tr> 
</table>



<div align=left id=savabuttonid>
<input value="��  ��" type=button  onclick="initDetail()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="save()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="update()" class="cssButton" type="button" >
<input value="ɾ  ��" type=button  onclick="del()" class="cssButton" type="button" ></div>
 
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
</BR></BR>

<input value="��  ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMRiskAccGet">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
