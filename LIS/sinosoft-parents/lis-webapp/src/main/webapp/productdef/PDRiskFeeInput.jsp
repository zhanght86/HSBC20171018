<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDRiskFeeInput.jsp
 //�����ܣ��˻�����Ѷ���
 //�������ڣ�2009-3-14
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%

 String mRiskCode = request.getParameter("riskcode");
%>

<script>
	 var mJSRiskCode = '<%=mRiskCode%>';
</script>
	
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
  <script src="../common/javascript/MultiCom.js"></script>
   <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDRiskFee.js"></SCRIPT>
 <%@include file="PDRiskFeeInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskFeeSave.jsp" method=post name=fm target="fraSubmit">
<table>
  <tr>
    <td class="titleImg" >�Ѷ�����˻������</td>
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
    <td class="titleImg" >�˻�����Ѷ���</td>
  </tr>
</table>
<table  class= common>
	<tr class= common>
        <TD  class= title>����ѱ���</TD>
        <TD  class= input>
						<Input class=common name=FEECODE verify="����ѱ���|NOTNUlL&LEN<=6" >
        </TD> 
        <TD  class= title>���������</TD>
        <TD  class= input>
						<Input class=common name=FEENAME verify="���������|NOTNUlL" >
						
        <TD  class= title>�����ʻ�����</TD>
        <TD  class= input>
					<Input class="codeno" name=INSUACCNO readonly="readonly" verify="�����˻�����|NOTNUlL" ondblclick="return showCodeList('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>');" onkeyup="return showCodeListKey('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>');"><input class=codename name=INSUACCNOName readonly="readonly">

        </TD> 
        </tr>
        <tr class=common>
        <TD  class= title>����/���������</TD>
        <TD  class= input>
					<Input class=common name=PAYPLANCODE  verify="����/���������|NOTNUlL" elementtype="nacessary" >
					<input type=hidden name=PAYPLANCODEName readonly="readonly" >
        </TD>  

	
        <TD  class= title>��ϵ˵��</TD>
        <TD  class= input>
						<Input class=common name=PAYINSUACCNAME >
        </TD> 
        
        <TD  class= title STYLE="display:none;">����ѷ���</TD>
        <TD  class= input STYLE="display:none;">
					<Input class="codeno" name=FEEKIND value="03" readonly="readonly" verify="����ѷ���|NOTNUlL&CODE:pd_feekind" ondblclick="return showCodeList('pd_feekind',[this,FEEKINDName],[0,1]);" onkeyup="return showCodeListKey('pd_feekind',[this,FEEKINDName],[0,1]);"><input class=codename name=FEEKINDName value="���������" readonly="readonly">
        </TD> 
        </tr> 
        <tr class= common>  
        <TD  class= title>������Ŀ����</TD>
        <TD  class= input>
					<Input class="codeno" name=FEEITEMTYPE readonly="readonly" verify="������Ŀ����|NOTNUlL&CODE:pd_feeitemtype" ondblclick="return showCodeList('pd_feeitemtype',[this,FEEITEMTYPEName],[0,1]);" onkeyup="return showCodeListKey('pd_feeitemtype',[this,FEEITEMTYPEName],[0,1]);"><input class=codename name=FEEITEMTYPEName readonly="readonly">
        </TD> 
        <TD  class= title>
������ȡλ��
        </TD>
        <TD  class= input>
           <Input class="codeno" name=FEETAKEPLACE readonly="readonly" verify="������ȡλ��|NOTNUlL&CODE:pd_feetakeplace" ondblclick="return showCodeList('pd_feetakeplace',[this,FEETAKEPLACEName],[0,1]);" onkeyup="return showCodeListKey('pd_feetakeplace',[this,FEETAKEPLACEName],[0,1]);"><input class=codename name=FEETAKEPLACEName readonly="readonly">
        </TD>
        <TD  class= title></TD>
        <TD  class= input></TD>
         </tr>
         <tr class=common>
         	<TD  class= title>
����Ѽ��㷽ʽ
        </TD>
        <TD  class= input>
           <Input class="codeno" name=FEECALMODE readonly="readonly" verify="����Ѽ��㷽ʽ|NOTNUlL&CODE:pd_feecalmode" ondblclick="return showCodeList('pd_feecalmode',[this,FEECALMODEName],[0,1]);" onkeyup="return showCodeListKey('pd_feecalmode',[this,FEECALMODEName],[0,1]);"><input class=codename name=FEECALMODEName readonly="readonly">
        </TD>
        <TD  class= title STYLE="display:none;">
��������
        </TD>
        <TD  class= input STYLE="display:none;">
           <Input class="codeno" name=FEECALMODETYPE readonly="readonly" value="1" verify="��������|NOTNUlL&CODE:pd_feecalmodetype" ondblclick="return showCodeList('pd_feecalmodetype',[this,FEECALMODETYPEName],[0,1]);" onkeyup="return showCodeListKey('pd_feecalmodetype',[this,FEECALMODETYPEName],[0,1]);"><input class=codename name=FEECALMODETYPEName value="ֱ��ȡֵ" readonly="readonly">
        </TD>
        	<TD  class= title>
�۳����������
        </TD>
        <TD  class= input>
           <Input class="codeno" name=FEEPERIOD readonly="readonly" verify="�۳����������|CODE:pd_feeperiod" ondblclick="return showCodeList('pd_feeperiod',[this,FEEPERIODName],[0,1]);" onkeyup="return showCodeListKey('pd_feeperiod',[this,FEEPERIODName],[0,1]);"><input class=codename name=FEEPERIODName readonly="readonly">
        </TD>
        <TD  class= title>
�۳��������ʼʱ��
        </TD>
       <TD  class= input>
           <Input class="coolDatePicker" name=FEESTARTDATE  verify="�۳��������ʼʱ��|NOTNUlL" dateFormat="short" id="FEESTARTDATE" onClick="laydate({elem:'#FEESTARTDATE'});"><span class="icon"><a onClick="laydate({elem: '#FEESTARTDATE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
         </tr>
         <tr class=common>
    
        <TD  class= title>
����Ѽ��㹫ʽ
        </TD>
        <TD  class= input>
          <input class="common"    name="FEECALCODE" verify="����Ѽ��㹫ʽ|LEN>=6&LEN<=10">
        </TD>
    	</tr>
</table>
<!--�㷨��������ҳ-->
<jsp:include page="CalCodeDefMain.jsp"/>
<hr>


<div align=right id=savabuttonid>
<input value="��  ��" type=button  onclick="initDetail()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="save()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="update()" class="cssButton" type="button" >
<input value="ɾ  ��" type=button  onclick="del()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
</div>
<div align=left id=checkFunc>
<input value="�鿴�㷨����" type=button  onclick="InputCalCodeDefFace2()" class="cssButton" type="button" >
</div>
<br>

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

<!--input value="����Ѽ����㷨����" type=button  onclick="button179( )" class="cssButton" type="button" -->
<br><br>

<br><br>

<input type=hidden name="RiskCode">
<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMRiskFee">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
