<!-- FileName:QueryForPayFee.jsp  -->
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<script>		
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����	
</script>
<head>
<title>��Ŀ����ѯ</title>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<script src="./FIRuleErrorLog.js"></script>
<%@include file="./FIRuleErrorLogInit.jsp"%>

</head>
<body onload="initForm();">
<!--��¼������-->
<form name=fm target=fraSubmit method=post"><input type=hidden
	name=strSQLValue><input type=hidden
	name=strSQLValueErr>
	<input type=hidden	name=RuleDealBatchNo>
	
<table>
	<tr>
		<td class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divFCDay);"></td>
		<td class=titleImg>�������ѯ����</td>
	</tr>
</table>
<Div id= "divFCDay" style= "display: ''"><div class="maxbox1">
<table class=common align=center>
	<TR class=common>

	<!-- ��ʼʱ��  ����ʱ�� -->
	<TR class=common>
		<TD class=title5>��ʼ����</TD>
		<TD class=input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|date" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
		<TD class=title5>��ֹ����</TD>
		<TD class=input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��ֹ����|notnull&date" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
	</TR>
	
</table>
</div>
</div>
<!--<Input class=cssButton type=Button value="��  ѯ"
			onclick="submitForm()">-->
            <a href="javascript:void(0);" class="button" onClick="submitForm();">��    ѯ</a><br><br>
<Div id="Frame2" style="display: ''">
<Table class=common>
	<tr>
		<td text-align: left colSpan=1><span id="spanErrLogGrid">
		</span></td>
	</tr>
</Table>

<!--<table>
	<TR class=input>
		<TD class=common><input type=button class=cssButton
			value="����Excel" onclick="printFinFeeToExcel();"></TD>
	</TR>
</table>--><br>
<a href="javascript:void(0);" class="button" onClick="printFinFeeToExcel();">����Excel</a>
</Div>
<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</Form>
</body>
</html>
