<html>
<%
	//**************************************************************************************************/
	//Name��StandPayInfoList.jsp
	//Function������__Ԥ�����
	//Author��pd
	//Date: 2005-11-1
	//Desc:
	//**************************************************************************************************/
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<SCRIPT src="../common/javascript/Common.js" type="text/javascript"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js" type="text/javascript"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js" type="text/javascript"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"
	type="text/javascript"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js" type="text/javascript"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js" type="text/javascript"></SCRIPT>
<SCRIPT src="StandPayInfoList.js" type="text/javascript"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<%@include file="StandPayInfoListInit.jsp"%>
</head>

<body onload="initForm();">
<form action="./LLAffixListSave.jsp" method=post name=fm
	target="fraSubmit">
<Table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,spanLDPerson);"></TD>
		<TD class=titleImg>��������Ϣ</TD>
	</TR>
</Table>
<span id="spanLDPerson" style="display: ''">
<table class=common>
	<TR class=common>
		<TD class=title>���屣����</TD>
		<TD class=input><Input class="readonly" readonly name=GrpContNo>
		</TD>
		<TD class=title>�����</TD>
		<TD class=input><Input class="readonly" readonly name=CaseNo></TD>

	</TR>
</table>
</span>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,AppAffixList);"></td>
		<td class=titleImg>Ԥ�������Ϣ</td>
	</tr>
</table>
<Div id="AppAffixList" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanAffixGrid"> </span></td>
	</tr>
</table>
</div>

<hr>
<input class=cssButton type=button value=" ��  �� "
	onclick="StandPaySave()"> <input class=cssButton type=button
	value=" ��  �� " onclick="top.close()"> <input type=hidden
	id="fmtransact" name="fmtransact"> <!-- <Input type=hidden	class="readonly" readonly name=CaseNo> -->
<Input type=hidden class="readonly" name=RiskCode> <Input
	type=hidden class="readonly" readonly name=customerName> <Input
	type=hidden class="readonly" readonly name=customerNo> <span
	id="spanCode" style="display: none; position:absolute; slategray"></span></form>
</body>
</html>
