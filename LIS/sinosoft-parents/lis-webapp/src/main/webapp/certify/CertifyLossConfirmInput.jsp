
<%
	// ��ֹIE����ҳ��
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
	//�������ƣ�CertifyLossConfirmInput.jsp
	//�����ܣ���ʧȷ��
	//�������ڣ�2009-1-5
	//������  ��mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="CertifyLossConfirmInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertifyLossConfirmInit.jsp"%>
</head>

<body onload="initForm()" style="behavior:url(#default#clientCaps)"
	id="oClientCaps">
<form action="./CertifyLossConfirmSave.jsp" method="post" name=fm id=fm
	target="fraSubmit">
<table class="common">
	<tr class="common">
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divSendOutInfo);"></td>
		<td class="titleImg">�ѹ�ʧ��ѯ</td>
	</tr>
</table>
<Div  id= "divSendOutInfo" style= "display: ''" class="maxbox1">
<table class="common">
	<tr class="common">
		<td class="title5">��ʧ��</td>
		<td class="input5"><input class="common wid" name="Handler" id="Handler"></td>
		<td class="title5">��ʧ����</td>
		<td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="��������|NOTNULL" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
	</tr>

	<tr class="common">
		<td class="title5">��¼����</td>
		<td class="input5"><input class="readonly wid" readonly name="ComCode" id="ComCode"></td>
	</tr>
</table>
</div>
<input class=cssButton type=button value="�ѹ�ʧ��ѯ" onclick="queryClick()">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCertifyList);"></td>
		<td class=titleImg>��֤�б�</td>
	</tr>
</table>
<div id="divCertifyList">
<table class="common">
	<tr class="common">
		<td text-align: left colSpan=1><span id="spanCertifyList"></span></td>
	</tr>
</table>
<center><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="��һҳ" class=cssButton91
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="��һҳ"
	class=cssButton92 TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>
</div>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLossInfo);"></td>
		<td class=titleImg>��ʧ���˵��</td>
	</tr>
</table>
<div id="divLossInfo" class="maxbox1">
<table class="common">
	<tr class="common">
		<td class="title5">�Ǳ�����</td>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#PublishDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=PublishDate id="PublishDate"><span class="icon"><a onClick="laydate({elem: '#PublishDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font color=red>*</font></td>
		<td class="title5">��ֽ����</td>
		<td class="input5"><input class="common wid" name="Newspaper" id="Newspaper"><font
			color=red>*</font></td>
	</tr>

	<tr class="common">
		<td class="title5">����Ա</td>
		<td class="input5"><input class="readonly wid" readonly name="Operator" id="Operator"></td>
		<td class="title5">��������</td>
		<td class="input5"><input class="readonly wid" readonly
			name="OperateDate" id="OperateDate"></td>
	</tr>

</table>
</div>
<input class="cssButton" type="button" value="��ʧȷ��" onclick="submitForm()">

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
