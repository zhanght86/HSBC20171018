<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDUMInput.jsp
 //�����ܣ����ֺ˱�������
 //�������ڣ�2009-3-14
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
<link rel="stylesheet" type="text/css"
	href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
<%@include file="buttonshow.jsp"%>
<SCRIPT src="PDUM.js"></SCRIPT>
<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.easyui.min.js"></script>
<%@include file="PDUMInit.jsp"%>
<%
	String tType = request.getParameter("Type");
	String tEdorType = request.getParameter("EdorType");
	System.out.println("tType:"+tType+":tEdorType:"+tEdorType);
%>

<script>
	var mType = '<%=tType%>';
	var mEdorType = '<%=tEdorType%>';
</script>
</head>
<body onload="initForm();initElementtype();">
<form action="./PDUMSave.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>���ִ���</td>
		<td class=input5><input class=common name="RiskCode"
			readonly="readonly"></td>
		<!--td class=title5>��������</td>
		<td class=input5>
			<input class=common name="RiskName" readonly="readonly" >
		</td-->
	</tr>
</table>
<table>
	<tr>
		<td class="titleImg">���к˱�����</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span
			id="spanMulline10Grid"> </span></td>
	</tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ"
	TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ"
	TYPE=button onclick="Mulline10GridTurnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ"
	TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> <INPUT
	CLASS=cssbutton VALUE="β  ҳ"
	TYPE=button onclick="Mulline10GridTurnPage.lastPage();"> </BR>
</BR>
<!--input value="�����㷨����" type=button  onclick="button163( )" class="cssButton" type="button" -->

<table>
	<tr>
		<td class="titleImg">�˱�������:</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<!--TD  class= title5>���ֱ���</TD>
        <TD  class= input5>
            <Input class="codeno" name=RiskCode readonly="readonly" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName readonly="readonly">
        </TD-->
		<TD class=title5>�˱�����</TD>
		<TD class=input5><Input class=common name=UWCODE
			verify="�˱�����|NOTNUlL&LEN<=10" 
			elementtype="nacessary"></TD>
		<TD class=title5>������������</TD>
		<TD class=input5><Input class="codeno" name=RELAPOLTYPE
			readonly="readonly"
			verify="������������|NOTNUlL"
			ondblclick="return showCodeList('pd_relapoltype',[this,RELAPOLTYPEName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);"
			onkeyup="return showCodeListKey('pd_relapoltype',[this,RELAPOLTYPEName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);"><input
			class=codename name=RELAPOLTYPEName readonly="readonly"
			elementtype="nacessary"></TD>
	</tr>
	<tr class=common>
		<TD class=title5>�˱�˳���</TD>
		<TD class=input5><input class="common" name="UWORDER"
			verify="�˱�˳���|NOTNUlL&NUM"
			elementtype="nacessary"></TD>
		<TD class=title5>��ʾ��Ϣ</TD>
		<TD class=input5><input class="common" name="REMARK"
			verify="��ʾ��Ϣ|NOTNUlL"
			elementtype="nacessary"></TD>
	</tr>
	<tr class=common>
		<TD class=title5>�㷨����</TD>
		<TD class=input5><input class="common" name="CALCODE"
			verify="�㷨����|LEN>=6"></TD>
		<TD class=title5>����ϵͳ</TD>
		<TD class=input5><Input class=codeno name=STANDBYFLAG1
			readonly="readonly"
			verify="����ϵͳ|NOTNUlL"
			ondblclick="return showCodeList('pd_systype',[this,STANDBYFLAG1NAME],[0,1]);"
			onkeyup="return showCodeListKey('pd_systype',[this,STANDBYFLAG1NAME],[0,1]);"><input
			class=codename name="STANDBYFLAG1NAME" readonly="readonly"
			elementtype="nacessary"></TD>
	</tr>
	<tr class=common>
		<TD class=title5>�˱�����
		</TD>
		<TD class=input5><Input class="codeno" name=UWTYPE
			readonly="readonly"
			verify="�˱�����|NOTNUlL"
			ondblclick="return showCodeList('pd_uwtype',[this,UWTYPEName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);"
			onkeyup="return showCodeListKey('pd_uwtype',[this,UWTYPEName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);"><input
			class=codename name=UWTYPEName readonly="readonly"
			elementtype="nacessary"></TD>
		<TD class=title5>ҵ��ģ��</TD>
		<TD class=input5><input class="codeno" name=STANDBYFLAG2
			ondblclick="return showCodeList('ibrmsbusinessrule',[this,STANDBYFLAG2NAME],[0,1]);"
			onkeyup="return showCodeListKey('ibrmsbusinessrule',[this,STANDBYFLAG2NAME],[0,1]);"><input
			class="codename" name=STANDBYFLAG2NAME readonly="readonly">
		</TD>
	</tr>
</table>
<!--�㷨��������ҳ--> <jsp:include page="CalCodeDefMain.jsp?RuleType=0" />

<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span
			id="spanMulline9Grid"> </span></td>
	</tr>
</table>
<br>
<div align=left id=savabuttonid><input
	value="��  ��" type=button
	onclick="initForm()" class="cssButton" type="button"> <input
	value="��  ��" type=button
	onclick="save()" class="cssButton" type="button"> <input
	value="��  ��" type=button
	onclick="update()" class="cssButton" type="button"> <input
	value="ɾ  ��" type=button
	onclick="del()" class="cssButton" type="button"></div>
<div align=left id=checkFunc><input
	value="�鿴��������" type=button
	onclick="InputCalCodeDefFace2()" class="cssButton" type="button">
</div>

<input value="���±�" type=hidden
	onclick="button164( )" class="cssButton" type="button"> <input
	value="�������ѯ" type=hidden
	onclick="button165( )" class="cssButton" type="button"> <input
	value="��  ��" type=button
	onclick="returnParent( )" class="cssButton" type="button"> <br>
<br>

<input type=hidden name="operator"> <input type=hidden
	name="tableName" value="PD_LMUW"> <input type=hidden
	name=IsReadOnly> <input type=hidden name=VALIFLAG> <!-- 
<input type=hidden name=STANDBYFLAG2>
<input type=hidden name=STANDBYFLAG2Name>
<input type=hidden name=STANDBYFLAG2NAME>
 --></form>

<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
