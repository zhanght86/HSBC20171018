<html>
<%
	//name :CertifyDescInput.jsp
	//function :��֤����
	//Creator :mw
	//date :2008-12-29
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGlobalInput = (GlobalInput) session.getValue("GI");
	String Branch = tGlobalInput.ComCode;
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="CertifyDescInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertifyDescInit.jsp"%>
</head>

<body onload="initForm();">
<form action="./CertifyDescSave.jsp" method=post name=fm id=fm
	target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divQuery);"></td>
		<td class=titleImg>��ѯ����</td>
	</tr>
</table>
<Div id="divQuery" style="display: ''">
<div class="maxbox">
<Table class=common>
	<TR class=common>
		<TD class=title5>��֤����</TD>
		<TD class=input5><Input class="wid" class=common name=CertifyCode_1 id=CertifyCode_1></TD>
		<TD class=title5>��֤����</TD>
		<TD class=input5><Input class="wid" class=common name=CertifyName_1 id=CertifyName_1></TD>
	</TR>

	<TR class=common>
		<TD class=title5>��֤����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass_1 id=CertifyClass_1
			CodeData="0|^D|��Ҫ�м۵�֤^B|��Ҫ�հ׵�֤^P|��ͨ��֤"
			ondblClick="showCodeListEx('CertifyClass_1',[this,CertifyClass_1Name],[0,1],null,null,null,1);"
            onMouseDown="showCodeListEx('CertifyClass_1',[this,CertifyClass_1Name],[0,1],null,null,null,1);"
			onkeyup="showCodeListKeyEx('CertifyClass_1',[this,CertifyClass_1Name],[0,1],null,null,null,1);"><Input
			class=codename name=CertifyClass_1Name id=CertifyClass_1Name readonly=true></TD>
		<TD class=title5>��֤ҵ������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass2_1 id=CertifyClass2_1
			CodeData="0|^0|Ͷ����^1|�б���^2|��ȫ��^3|������^4|������^5|����^6|��Ʒ˵����^7|����"
			ondblClick="showCodeListEx('CertifyClass2_1',[this,CertifyClass2_1Name],[0,1]);"
            onMouseDown="showCodeListEx('CertifyClass2_1',[this,CertifyClass2_1Name],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass2_1',[this,CertifyClass2_1Name],[0,1]);"><Input
			class=codename name=CertifyClass2_1Name id=CertifyClass2_1Name readonly=true></TD>
	</TR>

	<TR class=common>
		<TD class=title5>�Ƿ����кŵ�֤</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=HaveNumber_1 id=HaveNumber_1
			CodeData="0|^Y|�кŵ�֤^N|�޺ŵ�֤"
			ondblClick="showCodeListEx('HaveNumber_1',[this,HaveNumber_1Name],[0,1]);"
            onMouseDown="showCodeListEx('HaveNumber_1',[this,HaveNumber_1Name],[0,1]);"
			onkeyup="showCodeListKeyEx('HaveNumber_1',[this,HaveNumber_1Name],[0,1]);"><Input
			class=codename name=HaveNumber_1Name id=HaveNumber_1Name readonly=true></TD>
		<!--  <TD class=title>�Ƿ��м۵�֤</TD>
		<TD class=input><Input class=codeno name=HavePrice_1
			verify="�Ƿ��м�|NOTNULL" CodeData="0|^Y|�м۵�֤^N|���м۵�֤"
			ondblClick="showCodeListEx('HavePrice_1',[this,HavePrice_1Name],[0,1]);"
			onkeyup="showCodeListKeyEx('HavePrice_1',[this,HavePrice_1Name],[0,1]);"><Input
			class=codename name=HavePrice_1Name readonly=true></TD> -->
	</TR>
</Table>
</Div>
</Div>
<input class=cssButton type=button value="��  ѯ" onclick="queryClick()">
<input class=cssButton type=button value="��  ��" onclick="clearData()">


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCertifyDesc);"></td>
		<td class=titleImg>��ѯ����б�</td>
	</tr>
</table>
<Div id="divCertifyDesc" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanCertifyDescGrid">
		</span></td>
	</tr>
</table>
<!--<center><INPUT VALUE="��  ҳ" class=cssButton TYPE=button
	onclick="turnPage.firstPage();"> <INPUT VALUE="��һҳ"
	class=cssButton TYPE=button onclick="turnPage.previousPage();">
<INPUT VALUE="��һҳ" class=cssButton TYPE=button
	onclick="turnPage.nextPage();"> <INPUT VALUE="β  ҳ"
	class=cssButton TYPE=button onclick="turnPage.lastPage();"></center>-->
</div>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLLReport1);"></td>
		<td class=titleImg>��֤������Ϣ</td>
	</tr>
</table>
<Div id="divLLReport1" style="display: ''">
<div class="maxbox">
<Table class=common>
	<TR class=common>
		<TD class=title>��֤����</TD>
		<TD class=input><Input class="wid" class=common name=CertifyCode id=CertifyCode
			verify="��֤����|NOTNULL&NUM&len=6"></TD>
		<TD class=title>��֤����</TD>
		<TD class=input><Input class="wid" class=common name=CertifyName id=CertifyName
			verify="��֤����|NOTNULL"></TD>
	</TR>

	<TR class=common>
		<TD class=title>��֤����</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass id=CertifyClass
			verify="��֤����|NOTNULL" CodeData="0|^D|��Ҫ�м۵�֤^B|��Ҫ�հ׵�֤^P|��ͨ��֤"
			ondblClick="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1],null,null,null,1);"
            onMouseDown="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1],null,null,null,1);"
			onkeyup="showCodeListKeyEx('CertifyClass',[this,CertifyClassName],[0,1],null,null,null,1);"><Input
			class=codename name=CertifyClassName id=CertifyClassName readonly=true></TD>
		<TD class=title>��֤ҵ������</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass2 id=CertifyClass2
			verify="��֤ҵ������|NOTNULL"
			CodeData="0|^0|Ͷ����^1|�б���^2|��ȫ��^3|������^4|������^5|����^6|��Ʒ˵����^7|����"
			ondblClick="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
            onMouseDown="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"><Input
			class=codename name=CertifyClass2Name id=CertifyClass2Name readonly=true></TD>
		<TD class=title>ʹ�ñ�־</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=State id=State
			verify="ʹ�ñ�־|NOTNULL" CodeData="0|^0|����^1|ͣ��"
			ondblClick="showCodeListEx('State',[this,StateName],[0,1]);"
            onMouseDown="showCodeListEx('State',[this,StateName],[0,1]);"
			onkeyup="showCodeListKeyEx('State',[this,StateName],[0,1]);"><Input
			class=codename name=StateName id=StateName readonly=true></TD>
		<!--  <TD class=title>�Ƿ��м۵�֤</TD>
		<TD class=input><Input class=codeno name=HavePrice
			verify="�Ƿ��м�|NOTNULL" CodeData="0|^Y|�м۵�֤^N|���м۵�֤"
			ondblClick="showCodeListEx('HavePrice',[this,HavePriceName],[0,1]);"
			onkeyup="showCodeListKeyEx('HavePrice',[this,HavePriceName],[0,1]);"><Input
			class=codename name=HavePriceName readonly=true></TD> -->
	</TR>

	<TR class=common>
		<TD class=title>�Ƿ��кŵ�֤</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=HaveNumber id=HaveNumber
			verify="�Ƿ��кŵ�֤|NOTNULL" CodeData="0|^Y|�кŵ�֤^N|�޺ŵ�֤"
			ondblClick="showCodeListEx('HaveNumber',[this,HaveNumberName],[0,1]);"
            onMouseDown="showCodeListEx('HaveNumber',[this,HaveNumberName],[0,1]);"
			onkeyup="showCodeListKeyEx('HaveNumber',[this,HaveNumberName],[0,1]);"><Input
			class=codename name=HaveNumberName id=HaveNumberName readonly=true></TD>
		<TD class=title>��֤���볤��</TD>
		<TD class=input><Input class="wid" class=common name=CertifyLength id=CertifyLength
			verify="��֤���볤��|NOTNULL&INT&value>=0&value<=20"></TD>
		<TD class=title>�Ƿ���ձ�־</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=TackBackFlag id=TackBackFlag
			verify="�Ƿ����|NOTNULL" CodeData="0|^Y|��Ҫ����^N|����Ҫ����"
			ondblClick="showCodeListEx('TackBackFlag',[this,TackBackFlagName],[0,1]);"
            onMouseDown="showCodeListEx('TackBackFlag',[this,TackBackFlagName],[0,1]);"
			onkeyup="showCodeListKeyEx('TackBackFlag',[this,TackBackFlagName],[0,1]);"><Input
			class=codename name=TackBackFlagName id=TackBackFlagName readonly=true></TD>
	</TR>

	<TR class=common>
		<TD class=title>�Ƿ���������</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=HaveLimit id=HaveLimit
			CodeData="0|^Y|��������^N|����������"
			ondblClick="showCodeListEx('HaveLimit',[this,HaveLimitName],[0,1]);"
            onMouseDown="showCodeListEx('HaveLimit',[this,HaveLimitName],[0,1]);"
			onkeyup="showCodeListKeyEx('HaveLimit',[this,HaveLimitName],[0,1]);"><Input
			class=codename name=HaveLimitName id=HaveLimitName readonly=true></TD>
		<td class="title">��������</td>
		<td class="input"><input class="wid" class="common" name="MaxUnit1" id="MaxUnit1"
			verify="�������ø�������|INT"></td>
		<td class="title">�Ǹ�������</td>
		<td class="input"><input class="wid" class="common" name="MaxUnit2" id="MaxUnit2"
			verify="�������÷Ǹ�������|INT"></td>
	</TR>

	<TR class=common>
		<TD class=title>�Ƿ�����Чʹ������</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=HaveValidate id=HaveValidate
			CodeData="0|^Y|����Чʹ������^N|����Чʹ������"
			ondblClick="showCodeListEx('HaveValidate',[this,HaveValidateName],[0,1]);"
            onMouseDown="showCodeListEx('HaveValidate',[this,HaveValidateName],[0,1]);"
			onkeyup="showCodeListKeyEx('HaveValidate',[this,HaveValidateName],[0,1]);"><Input
			class=codename name=HaveValidateName id=HaveValidateName readonly=true></TD>
		<td class="title">��������</td>
		<td class="input"><input class="wid" class="common" name="Validate1" id="Validate1"
			verify="��Чʹ�����޸�������|INT"></td>
		<td class="title">�Ǹ�������</td>
		<td class="input"><input class="wid" class="common" name="Validate2" id="Validate2"
			verify="��Чʹ�����޷Ǹ�������|INT"></td>
	</TR>

	<TR class=common>
		<td class="title">��֤����</td>
		<td class="input"><input class="wid" class="common" name="CertifyPrice" id="CertifyPrice"
			verify="��֤����|NUM&LEN<=8"></td>
		<td class="title">��֤��λ</td>
		<td class="input"><input class="wid" class="common" name="Unit" id="Unit"></td>
		<td class="title">ӡˢ������</td>
		<td class="input"><input class="wid" class="common" name="MaxPrintNo" id="MaxPrintNo"></td>
	</TR>

	<TR class=common>
		<Input class=common type=hidden name=OperateType>
	</TR>
</Table>

<Table class=common>
	<TR class=common>
		<TD class=title>ע��</TD></TR>
        <TR class=common>
		<TD colspan="6" style="padding-left:16px" colspan="5"><textarea name="Note" id="Note" cols="195%" rows="4"
			witdh=25% class="common"></textarea></TD>
	</TR>
</table>
</div>
</Div>
<!--<Div id="divShow" style="display: 'none'">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCardRisk);"></td>
		<td class=titleImg>������Ϣ</td>
	</tr>
</table>
</Div>
<Div id="divCardRisk" style="display: 'none'">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanCardRiskGrid">
		</span></td>
	</tr>
</table>
</div>--> 
<input class=cssButton type=button value="��  ��" onclick="submitForm()">
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br />
<br />
<br />
<br />
</body>
</html>
