<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDRequRiskInput.jsp
 //�����ܣ���Ʒ�������ѯ
 //�������ڣ�2009-3-12
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<SCRIPT src="SpeedProgressConvert.js"></SCRIPT>
<%@include file="buttonshow.jsp"%>
<SCRIPT src="PDRequRisk.js"></SCRIPT>
<%@include file="PDRequRiskInit.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<SCRIPT src="PDRiskProcess.js"></SCRIPT>

</head>
<body onload="initForm();initElementtype();">
<form action="./PDRequRiskSave.jsp" method=post name=fm
	target="fraSubmit">

<table>
	<tr>
		<td class="titleImg">�������Ʒ���ִ��룺</td>
	</tr>
</table>


<table class=common>
	<TR class=common>
		<TD class=title5>��Ʒ���ִ���</TD>
		<TD class=input5><input class=common name=RiskCode
			verify="��Ʒ���ִ���|len=5"></TD>
		<TD class=title5>��������</TD>
		<td class=input5>
			<input class="coolDatePicker" dateFormat="short" id="RequDate"  name="RequDate" onClick="laydate({elem:'#RequDate'});" > <span class="icon"><a onClick="laydate({elem: '#RequDate'});"><img 
			src="../common/laydate/skins/default/icon.png" /></a></span>
		</td>
	</TR>
</table>
<input value="��  ѯ"
	onclick="queryDefining( )" class="cssButton" type="button"> <input
	value="��  ��" onclick="ApplyNewRisk( )"
	class="cssButton" type="button"> <br>
<br>
<table>
	<tr>
		<td class="titleImg">�����еĲ�Ʒ��</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span
			id="spanMulline9Grid"> </span></td>
	</tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ"
	TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ"
	TYPE=button onclick="Mulline9GridTurnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ"
	TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> <INPUT
	CLASS=cssbutton VALUE="β  ҳ"
	TYPE=button onclick="Mulline9GridTurnPage.lastPage();"> </BR>
</BR>
<input value="��ʼ����"
	onclick="BeginDefine()" class="cssButton" type="button">
<hr>
<Div id="divQuery" style="display: none">
<table>
	<tr>
		<td class="titleImg">������ȣ�</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span id="RiskStateGrid"></span>
		</td>
	</tr>
</table>
</Div>
</form>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
