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
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构	
</script>
<head>
<title>科目余额查询</title>

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
<!--登录画面表格-->
<form name=fm target=fraSubmit method=post"><input type=hidden
	name=strSQLValue><input type=hidden
	name=strSQLValueErr>
	<input type=hidden	name=RuleDealBatchNo>
	
<table>
	<tr>
		<td class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divFCDay);"></td>
		<td class=titleImg>请输入查询条件</td>
	</tr>
</table>
<Div id= "divFCDay" style= "display: ''"><div class="maxbox1">
<table class=common align=center>
	<TR class=common>

	<!-- 开始时间  结束时间 -->
	<TR class=common>
		<TD class=title5>起始日期</TD>
		<TD class=input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期|date" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
		<TD class=title5>终止日期</TD>
		<TD class=input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="终止日期|notnull&date" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
	</TR>
	
</table>
</div>
</div>
<!--<Input class=cssButton type=Button value="查  询"
			onclick="submitForm()">-->
            <a href="javascript:void(0);" class="button" onClick="submitForm();">查    询</a><br><br>
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
			value="导出Excel" onclick="printFinFeeToExcel();"></TD>
	</TR>
</table>--><br>
<a href="javascript:void(0);" class="button" onClick="printFinFeeToExcel();">导出Excel</a>
</Div>
<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</Form>
</body>
</html>
