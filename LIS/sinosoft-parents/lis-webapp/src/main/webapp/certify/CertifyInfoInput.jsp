
<%
	//�������ƣ�CertifyInfoInput.jsp 
	//�����ܣ���ѯ��֤��״̬
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
<script src="./CertifyInfoInput.js"></script>
<%@include file="CertifyInfoInit.jsp"%>
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
		<td class="title5">��֤����</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"><input
			class=codename name=CertifyCodeName id=CertifyCodeName readonly></td>
		<td class="title5">��֤״̬</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=StateFlag id=StateFlag
			CodeData="0|^1|�����^2|�����^3|�ѷ���δ����^4|�Զ�����^5|�ֹ�����^6|ʹ������^7|ͣ������^8|����^9|��ʧ^10|��ʧ^11|����^12|�ѽ���^13|������^14|�Ѻ���"
			ondblclick="return showCodeListEx('StateFlag',[this,StateFlagName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeListEx('StateFlag',[this,StateFlagName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKeyEx('StateFlag',[this,StateFlagName],[0,1],null,null,null,1);"><input
			class="codename" name="StateFlagName" id="StateFlagName" readonly></td>
	</tr>

	<tr class="common">
		<td class="title5">��ʼ��</td>
		<td class="input5"><input class="wid" class="common" name="StartNo" id="StartNo"></td>
		<td class="title5">��ֹ��</td>
		<td class="input5"><input class="wid" class="common" name="EndNo" id="EndNo"></td>
	</tr>

	<tr class="common">
		<td class="title5">������</td>
		<td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom"></td>
		<td class="title5">������</td>
		<td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom"></td>
	</tr>

	<tr class="common">
		<td class="title5">��ʼ����</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="MakeDateB" verify="��ʼ����|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=MakeDateB id="MakeDateB"><span class="icon"><a onClick="laydate({elem: '#MakeDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
		<td class="title5">��������</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="MakeDateE" verify="��������|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateE'});" verify="��������|NOTNULL" dateFormat="short" name=MakeDateE id="MakeDateE"><span class="icon"><a onClick="laydate({elem: '#MakeDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
	</tr>

	<tr class="common" style="display: none">
		<td class="title5">�������㵥��</td>
		<td class="input5"><input class="wid" class="common" name="TakeBackNo" id="TakeBackNo"></td>
		<TD class=title5>��¼����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom
			ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
            onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			class="codename" name="ManageComName" id="ManageComName" readonly></TD>
	</tr>
</table>
</div>
</div>
<input value="��֤״̬��ѯ" type="button" onclick="certifyQuery()" class="cssButton">
<input value=" ��    ӡ " type="button" onclick="certifyPrint()" class="cssButton">-->



<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divCardInfo);"></td>
		<td class=titleImg>��֤��Ϣ</td>
	</tr>
</table>
<div id="divCardInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td style="text-align: left" colSpan=1><span id="spanCardInfoGrid"></span></td>
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

<Div id="divSum" style="display: none">
<div class="maxbox1">
<table>
	<tr>
		<td class="title5">��֤����</td>
		<td class="input5"><input class="wid" class="common" name="SumCount"></td>
		<td class="title5"></td>
		<td class="input5"></td>
	</tr>
</table>
</Div>
</Div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
