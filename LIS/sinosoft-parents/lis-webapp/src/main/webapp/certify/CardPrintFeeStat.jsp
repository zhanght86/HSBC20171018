
<%
	//�������ƣ�CardPrintFeeStat.jsp
	//�����ܣ�����ӡˢ����ͳ�Ʊ���
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
<script src="./CardPrintFeeStat.js"></script>
<%@include file="CardPrintFeeStatInit.jsp"%>
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
		<TD class=title5>���Ż���</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=SendOutCom id=SendOutCom
			verify="���Ż���|NOTNULL"
			ondblclick="return showCodeList('station',[this,SendOutComName],[0,1]);"
            onMouseDown="return showCodeList('station',[this,SendOutComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,SendOutComName],[0,1]);"><input
			class="codename" name="SendOutComName" id="SendOutComName" readonly><font
			color=red>*</font></TD>
		<TD class=title5>���ջ���</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ReceiveCom id=ReceiveCom
			verify="���ջ���|NOTNULL"
			ondblclick="return showCodeList('station',[this,ReceiveComName],[0,1]);"
            onMouseDown="return showCodeList('station',[this,ReceiveComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ReceiveComName],[0,1]);"><input
			class="codename" name="ReceiveComName" id="ReceiveComName" readonly><font
			color=red>*</font></TD>
	</tr>

	<tr class="common">
		<td class="title5">��ʼ����</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="makedateB" verify="��ʼ����|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#makedateB'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=makedateB id="makedateB"><span class="icon"><a onClick="laydate({elem: '#makedateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
		<td class="title5">��ֹ����</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="makedateE" verify="��ֹ����|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#makedateE'});" verify="��ֹ����|NOTNULL" dateFormat="short" name=makedateE id="makedateE"><span class="icon"><a onClick="laydate({elem: '#makedateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
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
		<td class=titleImg>����ӡˢ����ͳ�Ʊ���</td>
	</tr>
</table>
<div id="divCardPrintInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td style="text-align: left" colSpan=1><span id="spanCardPrintInfoGrid"></span></td>
	</tr>
</table>
<!--<center><input VALUE="��  ҳ" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton"> <input
	VALUE="��һҳ" TYPE="button" onclick="turnPage.previousPage();"
	class="cssButton"> <input VALUE="��һҳ" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton"> <input
	VALUE="β  ҳ" TYPE="button" onclick="turnPage.lastPage();"
	class="cssButton"></center>-->
</div>

<!--<input value="��ӡ����" type="button" onclick="certifyPrint()"
			class="cssButton">-->
            <a href="javascript:void(0);" class="button" onClick="certifyPrint();">��ӡ����</a>
<input type="hidden" name="ASendOutCom"> <input type="hidden"
	name="AReceiveCom"></form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br>
</body>
</html>
