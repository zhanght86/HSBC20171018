
<%
	//�������ƣ�CertifyInfoInput.jsp 
	//�����ܣ���ѯ��֤�켣��Ϣ
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
<script src="./CertifyTrackInfoInput.js"></script>
<%@include file="CertifyTrackInfoInit.jsp"%>
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
			verify="��֤����|NOTNULL"
			ondblclick="return showCodeList('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"><input
			class=codename name=CertifyCodeName id=CertifyCodeName readonly><font color=red>*</font></td>
		<td class="title5">��֤״̬</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=StateFlag id=StateFlag
			CodeData="0|^1|�����^2|�����^3|�ѷ���δ����^4|�Զ�����^5|�ֹ�����^6|ʹ������^7|ͣ������^8|����^9|��ʧ^10|��ʧ^11|����"
			ondblclick="return showCodeListEx('StateFlag',[this,StateFlagName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeListEx('StateFlag',[this,StateFlagName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKeyEx('StateFlag',[this,StateFlagName],[0,1],null,null,null,1);"><input
			class="codename" name="StateFlagName" id="StateFlagName" readonly></td>
	</tr>

	<tr class="common">
		<td class="title5">��ʼ��</td>
		<td class="input5"><input class="wid" class="common" name="StartNo" id="StartNo"
			verify="��ʼ��|NOTNULL"><font color=red>*</font></td>
		<td class="title5">��ֹ��</td>
		<td class="input5"><input class="wid" class="common" name="EndNo" id="EndNo"
			verify="��ֹ��|NOTNULL"><font color=red>*</font></td>
	</tr>

	<tr class="common">
	<tr class="common">
		<td class="title5">������</td>
		<td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom"></td>
		<td class="title5">������</td>
		<td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom"></td>
	</tr>


	<tr class="common">
		<td class="title5">����Ա</td>
		<td class="input5"><input class="wid" class="common" name="Operator" id="Operator"></td>
		<td class="title5">������</td>
		<td class="input5"><input class="wid" class="common" name="Handler" id="Handler"></td>
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
</table>
</div>
</div>
<!--<table>
	<tr>
		<td><input value="��֤�켣��ѯ" type="button"
			onclick="certifyTrackQuery()" class="cssButton"></td>
		<td><input value=" ��    ӡ " type="button"
			onclick="certifyPrint()" class="cssButton"></td>
	</tr>
</table>-->
<a href="javascript:void(0);" class="button" onClick="certifyTrackQuery();">��֤�켣��ѯ</a>
<a href="javascript:void(0);" class="button" onClick="certifyPrint();">��    ӡ</a>

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
		<td text-align: left colSpan=1><span id="spanCardInfoGrid"></span></td>
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

</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
