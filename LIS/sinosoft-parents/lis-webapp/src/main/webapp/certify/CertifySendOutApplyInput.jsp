
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
	//�������ƣ�CertifySendOutApplyInput.jsp
	//�����ܣ���������
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
<SCRIPT src="CertifySendOutApplyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertifySendOutApplyInit.jsp"%>
</head>

<body onload="initForm()" style="behavior:url(#default#clientCaps)"
	id="oClientCaps">
<form action="./CertifySendOutApplySave.jsp" method="post" name=fm id=fm
	target="fraSubmit">

<table class="common">
	<tr class="common">
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divSendOutInfo);"></td>
		<td class="titleImg">����������Ϣ</td>
	</tr>
</table>

<Div  id= "divSendOutInfo" style= "display: ''" class="maxbox1">
<table class="common">
	<TR class=common>
		<TD class=title5>������Դ</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Department id=Department
			CodeData="0|^1|�Ӳ��ŷ���^2|����������^3|�Ͳ����޹�"
			ondblClick="showCodeListEx('Department',[this,DepartmentName],[0,1]);"
            onClick="showCodeListEx('Department',[this,DepartmentName],[0,1]);"
			onkeyup="showCodeListKeyEx('Department',[this,DepartmentName],[0,1]);"><Input
			class=codename name=DepartmentName id=DepartmentName readonly=true></TD>
		<TD class=title5>���ű���</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DepartmentNo id=DepartmentNo
			CodeData="0|^01|����ҵ��^02|���б��ղ�^03|��Ԫ���۲�^04|������Ŀ��^05|���Ѳ�^06|��ѵ��^07|�г���^08|�ͻ�����(�ֹ�˾Ϊ��Ӫ��)^09|����^10|ҵ�������"
			ondblClick="showCodeListEx('DepartmentNo',[this,DepartmentNoName],[0,1]);"
            onClick="showCodeListEx('DepartmentNo',[this,DepartmentNoName],[0,1]);"
			onkeyup="showCodeListKeyEx('DepartmentNo',[this,DepartmentNoName],[0,1]);"><Input
			class=codename name=DepartmentNoName id=DepartmentNoName readonly=true></TD>
	</TR>

	<tr class="common">
		<td class="title5">������</td>
		<td class="input5" nowrap=true><input class="common wid"
			name="SendOutCom" id="SendOutCom" verify="������|NOTNULL"><font color=red>*</font></td>
		<td class="title5">������</td>
		<td class="input5" nowrap=true><input class="common wid"
			name="ReceiveCom" id="ReceiveCom" verify="������|NOTNULL"><font color=red>*</font></td>
	</tr>

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

	<tr class="common">
		<td class="title5">��¼����</td>
		<td class="input5"><input class="readonly wid" readonly name="ComCode"  id="ComCode"></td>
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
<div id="divCertifyList">
<table class="common">
	<tr class="common">
		<td text-align: left colSpan=1><span id="spanCertifyList"></span></td>
	</tr>
</table>
<Table class=common>
	<TR>
		<TD class=title5>����ԭ��<font color=red>*</font></TD>
		<TD colspan="3"><textarea class="common" name="Reason"
			verify="����ԭ��|NOTNULL" cols="151%" rows="4" witdh=25%></textarea></TD>
	</TR>
</table>
</div>

<input name="btnOp" style="display:none" class="cssButton" type="button" value="��������"
	onclick="submitForm()">
    <a href="javascript:void(0);" name="btnOp" class="button" onClick="submitForm();">��������</a>
    </form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>