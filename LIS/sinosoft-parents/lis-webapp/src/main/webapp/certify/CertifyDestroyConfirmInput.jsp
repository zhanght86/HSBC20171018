
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
	//�������ƣ�CertifyDestroyConfirmInput.jsp
	//�����ܣ�����ȷ��
	//�������ڣ�2009-2-4
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
<SCRIPT src="CertifyDestroyConfirmInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertifyDestroyConfirmInit.jsp"%>
</head>

<body onload="initForm()" style="behavior:url(#default#clientCaps)"
	id="oClientCaps">
<form action="./CertifyDestroyConfirmSave.jsp" method="post" name=fm id=fm
	target="fraSubmit">

<table class="common">
	<tr class="common">
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divSendOutInfo);"></td>
		<td class="titleImg">���ύ��ѯ</td>
	</tr>
</table>
<Div  id= "divSendOutInfo" style= "display: ''" class="maxbox1">
<table class="common">
	<tr class="common">
		<td class="title5">�����ύ��</td>
		<td class="input5"><input class="common wid" name="Handler" id="Handler"></td>
		<td class="title5">�ύ����</td>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="��������|NOTNULL" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>

	<tr class="common">
		<td class="title5">����Ա</td>
		<td class="input5"><input class="readonly wid" readonly
			name="Operator" id="Operator"></td>
		<td class="title5">��������</td>
		<td class="input5"><input class="readonly wid" readonly
			name="OperateDate" id="OperateDate"></td>
	</tr>

	<tr class="common">
		<td class="title5">��¼����</td>
		<td class="input5"><input class="readonly wid" readonly name="ComCode" id="ComCode"></td>
	  <td CLASS=title5>������� </td>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="MngCom" id="MngCom" ondblclick="return showCodeList('station',[this,MngComName],[0,1],null,StrSql,1,1);"
        onclick="return showCodeList('station',[this,MngComName],[0,1],null,StrSql,1,1);"
			onkeyup="return showCodeListKey('station',[this,MngComName],[0,1],null,StrSql,1,1);"><input	class=codename name="MngComName" id="MngComName" readonly=true></TD>
	</tr>
</table>
</div>
<input class=cssButton type=button value="���ύ��ѯ" onclick="queryClick()">
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
		<td style="text-align: left" colSpan=1><span id="spanCertifyList"></span></td>
	</tr>
</table>
<center><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="��һҳ" class=cssButton91
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="��һҳ"
	class=cssButton92 TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>
</div>

<input style="display:none" name="btnOp" class="cssButton" type="button" value="����ȷ��" onclick="submitForm()">
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
var StrSql = "1 and  comcode like #"+<%=strComCode%>+"%#";
</script>
