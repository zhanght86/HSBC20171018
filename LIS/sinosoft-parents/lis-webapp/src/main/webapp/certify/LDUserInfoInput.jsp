
<%
	//�������ƣ�LDUserInfoInput.jsp
	//�����ܣ���֤��Ա��Ϣ��ѯ
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
<title>��֤����Ա��Ϣ��ѯ</title>
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
<script src="./LDUserInfoInput.js"></script>
<%@include file="LDUserInfoInit.jsp"%>
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
<div class="maxbox1">
<table class="common">
	<tr class="common">
		<TD class=title5>�������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom
			ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
            onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			class="codename" name="ManageComName" id="ManageComName" readonly></TD>
		<td class="title5">ͳ������</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=grade id=grade readonly
			CodeData="0|^2|�ܹ�˾^4|��������^6|��������^8|�ļ�����"
			ondblclick="return showCodeListEx('grade',[this,gradeName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeListEx('grade',[this,gradeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKeyEx('grade',[this,gradeName],[0,1],null,null,null,1);"><input
			class="codename" name="gradeName" id="gradeName" readonly></td>
	</tr>

	<tr class="common">
		<td class="title5">��ֹ����</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="validstartdate">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#validstartdate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=validstartdate id="validstartdate"><span class="icon"><a onClick="laydate({elem: '#validstartdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>

</table>
</div></div>
<!--<input value=" ��  ѯ " type="button" onclick="certifyQuery()"
			class="cssButton">-->
<a href="javascript:void(0);" class="button" onClick="certifyQuery();">��    ѯ</a>


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divUserInfo);"></td>
		<td class=titleImg>����Ա��Ϣ</td>
	</tr>
</table>
<div id="divUserInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td text-align: left colSpan=1><span id="spanUserInfoGrid"></span></td>
	</tr>
</table><center><input VALUE="��  ҳ" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton90"> <input
	VALUE="��һҳ" TYPE="button" onclick="turnPage.previousPage();"
	class="cssButton91"> <input VALUE="��һҳ" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton92"> <input
	VALUE="β  ҳ" TYPE="button" onclick="turnPage.lastPage();"
	class="cssButton93"></center></div><!--<input value="��ӡ�嵥" type="button" onclick="certifyPrint()"
	class="cssButton"></form>-->

<a href="javascript:void(0);" class="button" onClick="certifyPrint();">��ӡ�嵥</a>


<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
