<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
	//�������ƣ���֤����
	//�����ܣ���֤����
	//�������ڣ�2002-10-08
	//������  ��kevin
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="CertifyBlankOutInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertifyBlankOutInit.jsp"%>
</head>

<body onload="initForm()" style="behavior:url(#default#clientCaps)"
	id="oClientCaps">
<form action="./CertifyBlankOutSave.jsp" method=post name=fm id=fm
	target="fraSubmit">


<table class="common">
	<tr class="common">
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divOtherInfo);"></td>
		<td class="titleImg">������Ϣ</td>
	</tr>
</table>


<Div  id= "divOtherInfo" style= "display: ''" class="maxbox1">
<table class="common">
	<tr class="common">
		<td class="title5">������</td>
		<td class="input5"><input class="common wid" name="Handler" id="Handler"></td>
		<td class="title5">��������</td>
		<td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
	</tr>

	<tr class="common">
		<td class="title5">����Ա</td>
		<td class="input5"><input class="readonly wid" readonly
			name="Operator" id="Operator"></td>
		<td class="title5">��������</td>
		<td class="input5"><input class="readonly wid" readonly
			name="OperateDate" id="OperateDate"></td>
	</tr>
</table>
</div>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCertifyList);"></td>
		<td class=titleImg>��֤�б�</td>
	</tr>
</table>
<Div id="divCertifyList" style="display: ''">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanCertifyList"></span></td>
	</tr>
</table>
</div>

<!--<input class="cssButton" type="Button" value="���ϵ�֤"
	onclick="submitForm()">-->
    <br>
    <a href="javascript:void(0);" class="button" onClick="submitForm();">���ϵ�֤</a>
     <input type=hidden name="chkPrt"
	type="checkbox" ��ӡ�嵥></form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
