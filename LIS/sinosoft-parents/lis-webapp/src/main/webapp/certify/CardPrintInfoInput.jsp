
<%
	//�������ƣ�CardPrintInfoInput.jsp
	//�����ܣ���֤ӡˢ��ѯ
	//�������ڣ� 2009-02-18
	//������  �� mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<title>��֤��Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="../common/css/Project.css" rel="stylesheet" type="text/css">
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
<script src="../common/javascript/Common.js"></script>
<script src="../common/cvar/CCodeOperate.js"></script>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<script src="../common/javascript/EasyQuery.js"></script>
<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
<script src="../common/javascript/VerifyInput.js"></script>
<script src="./CertifyCommon.js"></script>
<script src="./CardPrintInfoInput.js"></script>
<%@include file="CardPrintInfoInit.jsp"%>
</head>

<body onload="initForm();">
<form method=post name=fm id=fm target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divQueryCondition);"></td>
		<td class=titleImg>��ѯ����</td>
	</tr>
</table>
<div id="divQueryCondition" style="display: ''">
<div class="maxbox">
<table class="common">
	<tr class="common">
		<TD class=title>ӡˢ����</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom
			ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
            onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			class="codename" name="ManageComName" id="ManageComName" readonly><font
			color=red>*</font></TD>
		<TD class=title>�ƻ�����</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PlanType id=PlanType
			CodeData="0|^0|Ԥ����^1|һ����^2|������^3|������^4|�ļ���^5|��/�İ�^6|��ӡ��֤"
			ondblClick="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
            onMouseDown="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('PlanType',[this,PlanTypeName],[0,1]);"><Input
			class=codename name=PlanTypeName id=PlanTypeName readonly=true></TD>
		<td class="title">ӡˢ״̬</td>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=State id=State
			verify="�ƻ�����|NOTNULL" CodeData="0|^1|��ӡˢ^2|�����"
			ondblClick="showCodeListEx('State',[this,StateName],[0,1],null,null,null,1);"
            onMouseDown="showCodeListEx('State',[this,StateName],[0,1],null,null,null,1);"
			onkeyup="showCodeListKeyEx('State',[this,StateName],[0,1],null,null,null,1);"><Input
			class=codename name=StateName id=StateName readonly=true></TD>
	</tr>

	<TR class=common>
		<td class="title">��֤����</td>
		<td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"><input
			class=codename name=CertifyName id=CertifyName readonly></td>
		<TD class=title>��֤����</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass id=CertifyClass
			CodeData="0|^D|��Ҫ�м۵�֤^B|��Ҫ�հ׵�֤^P|��ͨ��֤"
			ondblClick="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
            onMouseDown="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass',[this,CertifyClassName],[0,1]);"><Input
			class=codename name=CertifyClassName id=CertifyClassName readonly=true></TD>
		<TD class=title>��֤ҵ������</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass2 id=CertifyClass2
			CodeData="0|^0|Ͷ����^1|�б���^2|��ȫ��^3|������^4|������^5|����^6|��Ʒ˵����^7|����"
			ondblClick="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
            onMouseDown="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"><Input
			class=codename name=CertifyClass2Name id=CertifyClass2Name readonly=true></TD>
	</TR>

	<tr class="common">
		<td class="title">ӡˢ���ڣ���ʼ��</td>
		<td class="input"><!--<input class="coolDatePicker"
			dateFormat="short" name="MakeDateB" verify="������ڣ���ʼ��|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});" verify="������ڣ���ʼ��|NOTNULL" dateFormat="short" name=MakeDateB id="MakeDateB"><span class="icon"><a onClick="laydate({elem: '#MakeDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
		<td class="title">ӡˢ���ڣ�������</td>
		<td class="input"><!--<input class="coolDatePicker"
			dateFormat="short" name="MakeDateE" verify="������ڣ�������|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateE'});" verify="������ڣ�������|NOTNULL" dateFormat="short" name=MakeDateE id="MakeDateE"><span class="icon"><a onClick="laydate({elem: '#MakeDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
		<td class="title">����Ա</td>
		<td class="input"><input class="wid" class="common" name="Operator" id="Operator"></td>
	</tr>
</table>
</div></div>
<!--<input value="��  ѯ" type="button" onclick="certifyQuery()"
			class="cssButton">-->

<a href="javascript:void(0);" class="button" onClick="certifyQuery();">��    ѯ</a>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divCardPrintInfo);"></td>
		<td class=titleImg>ӡˢ��Ϣ</td>
	</tr>
</table>
<div id="divCardPrintInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td style="text-align: left" colSpan=1><span id="spanCardPrintInfoGrid"></span></td>
	</tr>
</table>
<center><input VALUE="��  ҳ" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton90"> <input
	VALUE="��һҳ" TYPE="button" onclick="turnPage.previousPage();"
	class="cssButton91"> <input VALUE="��һҳ" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton92"> <input
	VALUE="β  ҳ" TYPE="button" onclick="turnPage.lastPage();"
	class="cssButton93"></center></div>


</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
