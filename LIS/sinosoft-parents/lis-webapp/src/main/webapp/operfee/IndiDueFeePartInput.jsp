<html>
<%
	//程序名称：IndiDueFeePartInput.jsp
	//程序功能：
	//创建日期：2005-07-5
	//创建人：
	//更新日期：
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="IndiDueFeePartInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
</head>

<%@include file="IndiDueFeePartInit.jsp"%>
<body onload="initForm();">
<form name=fm id="fm" action=./IndiDueFeePartInputQuery.jsp target=fraSubmit
	method=post>
<table class=common>
	<tr class=common>
		<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
		</td>
		<td class=titleImg>请输入催收条件</td>
	</tr>
	</table>
	<div class="maxbox1">
	<div  id= "divFCDay" style= "display: ''"> 
	<table class=common>
	<tr class=common>
		<TD class=title5>管理机构：</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=ManageCom id="ManageCom"
			verify="管理机构|code:station"
			onclick="return showCodeList('Station',[this,ManageComName],[0,1]);"
			ondblclick="return showCodeList('Station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);"><input
			class=codename name=ManageComName id="ManageComName" readonly=true elementtype=nacessary>
		</TD>
		<TD class=title5>保单号码：</TD>
		<TD class=input5><Input class="common wid" name=ContNo id="ContNo"></TD>
	</tr>

	<!--  <tr class=common>
		<TD class=title>开始日期:</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short"
			name=StartDate></TD>
		<TD class=title>终止日期:</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short"
			name=EndDate></TD>
	</tr>-->

	<tr class=common>
		<TD class=title5>险种代码：</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=RiskCode id="RiskCode"
			onclick="return showCodeList('riskcode',[this,polName],[0,1]);"
			ondblclick="return showCodeList('riskcode',[this,polName],[0,1]);"
			onkeyup="return showCodeListKey('riskcode',[this,polName],[0,1]);"><input
			class=codename name=polName id="polName" readonly=true></TD>
		<TD class=title5>业务员</TD>
		<td class="input5" width="25%"><Input class="common wid" name=AgentCode id="AgentCode">
			<a href="javascript:void(0)" class=button onclick="queryAgent();">查  询</a>
		<!-- <input class=cssButton type="button" value="查  询" style="width:60"
			onclick="queryAgent()"> --></td>
	</tr>

	<tr class="common">
		<td class="title5">交费形式:</td>
		<td class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="SecPayMode" id="SecPayMode"
			verify="交费形|NOTNUlL&CODE:continuepaymode" verifyorder="1"
			onclick="return showCodeList('continuepaymode',[this,PayModeName],[0,1]);"
			ondblclick="return showCodeList('continuepaymode',[this,PayModeName],[0,1]);"
			onkeyup="return showCodeListKey('continuepaymode',[this,PayModeName],[0,1]);"><input
			class="codename" name="PayModeName" id="PayModeName" readonly="readonly"
			elementtype=nacessary></td>
		<TD class=title5>销售渠道:</TD>
		<TD class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name="ContType" id="ContType"
			onclick="return showCodeList('salechnl', [this,ContTypeName],[0,1])"
			ondblclick="return showCodeList('salechnl', [this,ContTypeName],[0,1])"
			onkeyup="return showCodeListKey('salechnl', [this,ContTypeName],[0,1])"><input
			class=codename name=ContTypeName id="ContTypeName" readonly=true></TD>
	</tr>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="PartSingle();">开始批量催收</a>
<!-- <INPUT VALUE="开始批量催收" class=cssButton name=magan TYPE=button
			onclick="PartSingle()"> -->
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
