<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
 //�������ƣ�PDRelaBillInput.jsp
 //�����ܣ����θ����˵�����
 //�������ڣ�2009-3-16
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
<SCRIPT src="PDRelaBill.js"></SCRIPT>
<%@include file="PDRelaBillInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
<form action="./PDRelaBillSave.jsp" method=post name=fm
	target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>��Ʒ���ִ���</td>
		<td class=common><input class="common" name="RiskCode" readonly="readonly">
		</td>
	</tr>
</table>
<table>
	<tr>
		<td class="titleImg">�ѹ����˵���</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline10Grid">
		</span></td>
	</tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button
	onclick="Mulline10GridTurnPage.firstPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ" TYPE=button
	onclick="Mulline10GridTurnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ" TYPE=button
	onclick="Mulline10GridTurnPage.nextPage();"> <INPUT
	CLASS=cssbutton VALUE="β  ҳ" TYPE=button
	onclick="Mulline10GridTurnPage.lastPage();"> </BR>
</BR>

<table>
  <tr>
    <td class="titleImg" >�����˵���ϸ</td>
  </tr>
</table>


<table  class= common>
	<tr class= common>
        <TD  class= title5>��������</TD>
        <TD  class= input5>
						<Input class=common readonly="readonly" name=GETDUTYCODE >
        </TD> 
        <TD  class= title5>��������</TD>
        <TD  class= input5>
						<Input class=common readonly="readonly" name=GETDUTYNAME > </TD> 
						
        <TD  class= title5>��������</TD>
          <TD  class= input5>
						<Input class=common readonly="readonly" name=GETDUTYKIND > </TD> 
</tr>
<tr class= common>
	 <TD  class= title5>���ü��㷽ʽ</TD>
        <TD  class= input5>
					<Input class="codeno" name=CLMFEECALTYPE readonly="readonly" verify="���ü��㷽ʽ|NOTNUlL" ondblclick="return showCodeList('pd_feerela',[this,CLMFEECALTYPENAME],[0,1]);" onkeyup="return showCodeListKey('pd_feerela',[this,CLMFEECALTYPENAME],[0,1]);"><input class=codename name=CLMFEECALTYPENAME readonly="readonly"><font
			size=1 color='#ff0000'><b>*</b></font>
        </TD> 
    <TD  class= title5>������ϸ���㹫ʽ</TD>
        <TD  class= input5>
						<Input class=common   name=CLMFEECALCODE verify="�ӷ��㷨|LEN>=6&LEN<=10" >
        </TD> 
     <!-- TD  class= title5>����Ĭ��ֵ</TD>
        <TD  class= input5>
						<Input class=common   name=CLMFEEDEFVALUE verify="����Ĭ��ֵ|num" >
      </TD--> 
      <Input type=hidden    name=CLMFEEDEFVALUE verify="����Ĭ��ֵ|num" >
</tr>
<tr class=common>
	<TD  class= title5>�˵���Ŀ����</TD>
        <TD  class= input5>
					<Input class="codeno" name=CLMFEECODE  verify="�˵���Ŀ����|NOTNUlL" ondblclick="return showCodeList('pd_clmfeecode',[this,CLMFEENAME],[0,1]);" onkeyup="return showCodeListKey('pd_clmfeecode',[this,CLMFEENAME],[0,1]);"><input class=codename name=CLMFEENAME readonly="readonly"><font
			size=1 color='#ff0000'><b>*</b></font>
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

<!--
<table class=common>
	<tr>
		<td class=title5Img>���θ��������˵�����</td>
		<td class=title5Img>���θ���סԺ�˵�����</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline8Grid"></span>
		</td>
		<td style="text-align:left;" colSpan=1><span id="spanMulline9Grid"></span>
		</td>
		<td></td>
	</tr>
</table>
</div>	
<br>
<table class=common>
	<tr class=common>
	<td> 
<div align=left><input value="��  ��" type=button onclick="save()"
	class="cssButton" type="button"> </div>
	</td>
	<td> 
<div align=left><input value="��  ��" type=button onclick="save1()"
	class="cssButton" type="button"></div>
	</td>
		</tr>
</table>
-->
<br>
<input value="��  ��" type=button onclick="returnParent( )"
	class="cssButton" type="button"> <br>
<br>

<input type=hidden name="operator">
<input type=hidden name="bttnflag">
<input type=hidden 	name="tableName" value="PD_LMDutyGetFeeRela">
<input type=hidden name=IsReadOnly></form>

<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
