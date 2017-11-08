<html>
<%
	//name :CardPlanCollectInput.jsp
	//function :印刷计划汇总
	//Creator :mw
	//date :2009-02-16
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGlobalInput = (GlobalInput) session.getValue("GI");
	String Branch = tGlobalInput.ComCode;
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="CardPlanPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CardPlanPrintInit.jsp"%>
</head>

<body onload="initForm();">
<form action="./CardPlanPrintSave.jsp" method=post name=fm id=fm
	target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divQuery);"></td>
		<td class=titleImg>查询条件</td>
	</tr>
</table>
<Div id="divQuery" style="display: ''">
<div class="maxbox">
<Table class=common>
	<TR class=common>
		<TD class=title5>单证编码</TD>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyCode id=CertifyCode
			ondblclick="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('certifycode', [this,CertifyName],[0,1],null,null,null,1);"><Input
			class=codename name=CertifyName id=CertifyName readonly=true></td>
		<TD class=title5>计划类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PlanType id=PlanType
			verify="计划类型|NOTNULL"
			CodeData="0|^0|预算外^1|一季度^2|二季度^3|三季度^4|四季度^5|新/改版^6|自印单证"
			ondblClick="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
            onMouseDown="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('PlanType',[this,PlanTypeName],[0,1]);"><Input
			class=codename name=PlanTypeName id=PlanTypeName readonly=true></TD>
	</TR>

	<tr class="common">
		<td class="title5">操作人</td>
		<td class="input5"><input class="wid" class="common" readonly
			name="PrintOperator" id="PrintOperator"></td>
		<td class="title5">操作日期</td>
		<td class="input5"><!--<input class="coolDatePicker" readonly
			dateFormat="short" name="PrintDate" verify="经办日期|NotNull">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#PrintDate'});" verify="经办日期|NotNull" dateFormat="short" name=PrintDate id="PrintDate"><span class="icon"><a onClick="laydate({elem: '#PrintDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>
	<tr class="common">
		<td class="title5">登录机构</td>
		<td class="input5"><input class="wid" class="readonly" readonly
			name="managecom" id="managecom"></td>
	</TR>
</Table>
</Div>
</Div>
<input class=cssButton type=button value="待印刷查询" onclick="queryClick()">
<input class=cssButton type=button value="已印刷查询" onclick="queryClick2()">
<input class=cssButton type=button value="清  空" onclick="clearData()">
<!--<a href="javascript:void(0);" class="button" onClick="queryClick();">待印刷查询</a>
<a href="javascript:void(0);" class="button" onClick="queryClick2();">已印刷查询</a>
<a href="javascript:void(0);" class="button" onClick="clearData();">清    空</a>-->

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCardPrintQuery);"></td>
		<td class=titleImg>查询结果列表</td>
	</tr>
</table>
<Div id="divCardPrintQuery" style="display: ''">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanCardPrintQueryGrid">
		</span></td>
	</tr>
</table>
<!--<center><INPUT VALUE="首  页" class=cssButton90 TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="上一页" class=cssButton91
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="下一页"
	class=cssButton92 TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="尾  页" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>-->
</div>

<input class=cssButton type=button name=buttonP value="印  刷" onclick="submitForm()">
<!--<a href="javascript:void(0);" name=buttonP class="button" onClick="submitForm();">印    刷</a>-->
<input type="hidden" name="OperateType" value=""></form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
