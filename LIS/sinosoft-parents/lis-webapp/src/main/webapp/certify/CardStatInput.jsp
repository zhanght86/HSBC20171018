
<%
	//�������ƣ�CardStatInputInit.jsp
	//�����ܣ���֤�ۺϱ���
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
<title>��֤�ۺϱ���</title>
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
<script src="./CardStatInput.js"></script>
<%@include file="CardStatInit.jsp"%>
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
		<TD class=title5>�������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom
			verify="�������|NOTNULL"
			ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
            onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			class="codename" name="ManageComName" id="ManageComName" readonly><font
			color=red>*</font></TD>
		<!--<td class="title">ͳ������</td>
		<td class=input><Input class="codeno" name=grade readonly
			CodeData="0|^2|�ܹ�˾^4|��������^6|��������^8|�ļ�����"
			ondblclick="return showCodeListEx('grade',[this,gradeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKeyEx('grade',[this,gradeName],[0,1],null,null,null,1);"><input
			class="codename" name="gradeName" readonly></td>-->
	</tr>

	<TR class=common>
		<TD class=title5>��֤����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass id=CertifyClass
			CodeData="0|^D|��Ҫ�м۵�֤^B|��Ҫ�հ׵�֤^P|��ͨ��֤"
			ondblClick="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
            onMouseDown="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass_1',[this,CertifyClassName],[0,1]);"><Input
			class=codename name=CertifyClassName id=CertifyClassName readonly=true></TD>
		<TD class=title5>��֤����</TD>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('certifycode', [this,CertifyName],[0,1],null,null,null,1);"><Input
			class=codename name=CertifyName id=CertifyName readonly=true></td>
	</TR>

	<tr class="common">
		<td class="title5">��ʼ����</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" verify="��ʼ����|NOTNULL" name="makedateB">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#makedateB'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=makedateB id="makedateB"><span class="icon"><a onClick="laydate({elem: '#makedateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
		<td class="title5">��ֹ����</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" verify="��ֹ����|NOTNULL" name="makedateE">-->
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
		<td class=titleImg>��֤�ۺϱ���</td>
	</tr>
</table>
<div id="divCardPrintInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td text-align: left colSpan=1><span id="spanCardPrintInfoGrid"></span></td>
	</tr>
</table></div>
<center><input VALUE="��  ҳ" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton90"> <input
	VALUE="��һҳ" TYPE="button" onclick="turnPage.previousPage();"
	class="cssButton91"> <input VALUE="��һҳ" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton92"> <input
	VALUE="β  ҳ" TYPE="button" onclick="turnPage.lastPage();"
	class="cssButton93"></center>

<!--<table>
	<tr>
		<td><input value="��ӡ����" type="button" onclick="certifyPrint()"
			class="cssButton"></td>
	</tr>
</table>-->

<a href="javascript:void(0);" class="button" onClick="certifyPrint();">��ӡ����</a><br><br>
<table class="common">
	<tr class="common">
		<td text-align: left colSpan=1><span id="spanCardPrintInfo2Grid"></span></td>
	</tr>
</table></div>
<center><input VALUE="��  ҳ" TYPE="button"
	onclick="turnPage2.firstPage();" class="cssButton90"> <input
	VALUE="��һҳ" TYPE="button" onclick="turnPage2.previousPage();"
	class="cssButton91"> <input VALUE="��һҳ" TYPE="button"
	onclick="turnPage2.nextPage();" class="cssButton92"> <input
	VALUE="β  ҳ" TYPE="button" onclick="turnPage2.lastPage();"
	class="cssButton93"></center>


<!--<input value="��ӡ����" type="button" onclick="certifyPrint2()"
			class="cssButton">-->
<a href="javascript:void(0);" class="button" onClick="certifyPrint2();">��ӡ����</a>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
