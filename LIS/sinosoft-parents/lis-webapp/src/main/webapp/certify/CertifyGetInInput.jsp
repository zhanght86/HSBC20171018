
<%
	// ��ֹIE����ҳ��
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%
	//�������ƣ���֤��� CertifyGetInInput.jsp
	//�����ܣ���֤ӡˢ��or��֤���ŵ��¼�����ʱ����Ҫ���ջ�������֤������
	//�������ڣ�2009-01-04
	//������  ��mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="CertifyGetInInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertifyGetInInit.jsp"%>
</head>

<body onload="initForm()" style="behavior:url(#default#clientCaps)"
	id="oClientCaps">
<form action="./CertifyGetInSave.jsp" method="post" name=fm id=fm
	target="fraSubmit">
<table class="common">
	<tr class="common">
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divSendOutInfo);"></td>
		<td class="titleImg">��ѯ����</td>
	</tr>
</table>
<Div  id= "divSendOutInfo" style= "display: ''" class="maxbox1">
<table class="common">
	<TR class=common>
		<td class="title5">��֤����</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
            onclick="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('certifycode', [this,CertifyName],[0,1],null,null,null,1);"><Input
			class=codename name=CertifyName id=CertifyName readonly=true></td>
		<td class="title5">��¼����</td>
		<td class="input5"><input class="readonly wid" readonly
			name="managecom" id="managecom"></td>
	</TR>

	<TR class=common>
		<TD class=title5>��֤����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass id=CertifyClass
			CodeData="0|^D|��Ҫ�м۵�֤^B|��Ҫ�հ׵�֤^P|��ͨ��֤"
			ondblClick="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
            onClick="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass',[this,CertifyClassName],[0,1]);"><Input
			class=codename name=CertifyClassName id=CertifyClassName readonly=true></TD>
		<TD class=title5>��֤ҵ������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass2 id=CertifyClass2
			CodeData="0|^0|Ͷ����^1|�б���^2|��ȫ��^3|������^4|������^5|����^6|��Ʒ˵����^7|����"
			ondblClick="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
            onClick="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"><Input
			class=codename name=CertifyClass2Name id=CertifyClass2Name readonly=true></TD>
	</TR>

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

<!--<input class=cssButton type=button value="������ѯ" onclick="queryClick()">-->
<a href="javascript:void(0);" class="button" onClick="queryClick();">������ѯ</a>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCertifyList);"></td>
		<td class=titleImg>����ⵥ֤�б�</td>
	</tr>
</table>
<div id="divCertifyList">
<table class="common">
	<tr class="common">
		<td style="text-align: left" colSpan=1><span id="spanCertifyListGrid"></span></td>
	</tr>
</table>
<center><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="��һҳ" class=cssButton91
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="��һҳ"
	class=cssButton92 TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>
</div>

<!--<input name="btnOp1" class=cssButton type=button value="ȷ�����"
	onclick="submitForm()"> <input name="btnOp2" class=cssButton
	type=button value="�ܾ����" onclick="cancelGetIn()"> --><input
	type=hidden name="operateFlag">
    <a href="javascript:void(0);" name="btnOp1" class="button" onClick="submitForm();">ȷ�����</a>
    <a href="javascript:void(0);" name="btnOp2" class="button" onClick="cancelGetIn();">�ܾ����</a>
    </form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>

</body>
</html>
