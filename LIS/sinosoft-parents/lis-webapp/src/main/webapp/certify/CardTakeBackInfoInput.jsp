
<%
	//�������ƣ�CardNotTakeBackInfoInput.jsp
	//�����ܣ��м۵�֤�����嵥
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
<script src="./CardTakeBackInfoInput.js"></script>
<%@include file="CardTakeBackInfoInit.jsp"%>
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
<div id="divQueryCondition" style="display: ''" class="maxbox">
<table class="common">
	<tr class="common">
		<TD class=title5>�������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom
			verify="�������|NOTNULL"
			ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
            onclick="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			class="codename" name="ManageComName" id="ManageComName" readonly><font
			color=red>*</font></TD>
            <td class="title5">��֤����</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
            onclick="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"><input
			class=codename name=CertifyName id=CertifyName readonly></td>
	</tr>


	<tr class="common">
		
		<TD class=title5>�Ƿ���Ч����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=INinvalidate id=INinvalidate
			CodeData="0|^Y|��Ч����^N|��Ч����"
			ondblClick="showCodeListEx('INinvalidate',[this,INinvalidateName],[0,1]);"
            onclick="showCodeListEx('INinvalidate',[this,INinvalidateName],[0,1]);"
			onkeyup="showCodeListKeyEx('INinvalidate',[this,INinvalidateName],[0,1]);"><Input
			class=codename name=INinvalidateName id=INinvalidateName readonly=true></TD>
            <TD class=title5>��֤����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass id=CertifyClass
			CodeData="0|^D|��Ҫ�м۵�֤^B|��Ҫ�հ׵�֤"
			ondblClick="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
            onclick="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass',[this,CertifyClassName],[0,1]);"><Input
			class=codename name=CertifyClassName id=CertifyClassName readonly=true></TD>
	</tr>

	<TR class=common>
		
		<TD class=title5>��֤ҵ������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass2 id=CertifyClass2
			CodeData="0|^0|Ͷ����^1|�б���^2|��ȫ��^3|������^4|������^5|����^6|��Ʒ˵����^7|����"
			ondblClick="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
            onclick="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"><Input
			class=codename name=CertifyClass2Name id=CertifyClass2Name readonly=true></TD>
            <td class="title5">������</td>
		<td class="input5"><input class="common wid" name="SendOutCom" id="SendOutCom"></td>
	</TR>

	<tr class="common">
		
		<td class="title5">������</td>
		<td class="input5"><input class="common wid" name="ReceiveCom" id="ReceiveCom"></td>
        <td class="title5">��ʼ����</td>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=MakeDateB id="MakeDateB"><span class="icon"><a onClick="laydate({elem: '#MakeDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
	</tr>

	<tr class="common">
		
		<td class="title5">��ֹ����</td>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateE'});" verify="��ֹ����|NOTNULL" dateFormat="short" name=MakeDateE id="MakeDateE"><span class="icon"><a onClick="laydate({elem: '#MakeDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
	</tr>
</table>
</div>
<input value="��  ѯ" type="button" onclick="certifyQuery()" class="cssButton">

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divCardSendOutInfo);"></td>
		<td class=titleImg>��ѯ���</td>
	</tr>
</table>
<div id="divCardSendOutInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td style="text-align: left" colSpan=1><span id="spanCardSendOutInfoGrid"></span></td>
	</tr>
</table>
<center><input VALUE="��  ҳ" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton90"> <input
	VALUE="��һҳ" TYPE="button" onclick="turnPage.previousPage();"
	class="cssButton91"> <input VALUE="��һҳ" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton92"> <input
	VALUE="β  ҳ" TYPE="button" onclick="turnPage.lastPage();"
	class="cssButton93"></center>
</div>
<input value="��ӡ�嵥" type="button" onclick="certifyPrint()" class="cssButton">
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
