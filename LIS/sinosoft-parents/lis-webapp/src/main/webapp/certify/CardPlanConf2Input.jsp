<html>
<%
	//name :CertifyDescInput.jsp
	//function :单证定义
	//Creator :mw
	//date :2008-12-29
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
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="CardPlanConf2.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CardPlanConf2Init.jsp"%>
</head>

<body onload="initForm();">
<form action="./CardPlanConfSave.jsp" method=post name=fm id=fm
	target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divQuery);"></td>
		<td class=titleImg>待提交计划查询</td>
	</tr>
</table>
<Div id="divQuery" style="display: ''">
<div class="maxbox">
<Table class=common>
	<TR class=common>
		<TD class=title5>计划标识</TD>
		<TD class=input5><Input class="wid" class=common name=PlanID id=PlanID></TD>
		<TD class=title5>计划状态</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PlanState id=PlanState
			CodeData="0|^A|申请状态^C|确认状态^R|回复状态"
			ondblClick="showCodeListEx('PlanState',[this,PlanStateName],[0,1]);"
            onMouseDown="showCodeListEx('PlanState',[this,PlanStateName],[0,1]);"
			onkeyup="showCodeListKeyEx('PlanState',[this,PlanStateName],[0,1]);"><Input
			class=codename name=PlanStateName id=PlanStateName readonly=true></TD>
	</TR>

	<TR class=common>
		<TD class=title5>单证编码</TD>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('certifycode', [this,CertifyName],[0,1],null,null,null,1);"><Input
			class=codename name=CertifyName id=CertifyName readonly=true></td>
		<TD class=title5>计划类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PlanType id=PlanType
			CodeData="0|^0|预算外^1|一季度^2|二季度^3|三季度^4|四季度^5|新/改版^6|自印单证"
			ondblClick="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
            onMouseDown="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('PlanType',[this,PlanTypeName],[0,1]);"><Input
			class=codename name=PlanTypeName id=PlanTypeName readonly=true></TD>
	</TR>

	<tr class="common">
		<td class="title5">操作人</td>
		<td class="input5"><input class="wid" class="common" readonly
			name="AppOperator" id="AppOperator"></td>
		<td class="title5">操作日期</td>
		<td class="input5"><!--<input class="coolDatePicker" readonly
			dateFormat="short" name="MakeDate">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>
	<tr class="common">
		<td class="title5">登录机构</td>
		<td class="input5"><input class="wid" class="readonly" readonly
			name="managecom" id="managecom"></td>
	</TR>
</Table>
</div></Div>
<!--<input class=cssButton type=button value="查  询" onclick="queryClick()">
<input class=cssButton type=button value="清  空" onclick="clearData()">-->
<a href="javascript:void(0);" class="button" onClick="queryClick();">查    询</a>
<a href="javascript:void(0);" class="button" onClick="clearData();">清    空</a>


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCardPlanQuery);"></td>
		<td class=titleImg>查询结果列表</td>
	</tr>
</table>
<Div id="divCardPlanQuery" style="display: ''">

<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanCardPlanQueryGrid">
		</span></td>
	</tr>
</table>
<center><INPUT VALUE="首  页" class=cssButton90 TYPE=button
	onclick="turnPage.firstPage();"> <INPUT VALUE="上一页"
	class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
<INPUT VALUE="下一页" class=cssButton92 TYPE=button
	onclick="turnPage.nextPage();"> <INPUT VALUE="尾  页"
	class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></center>
</div>

<!--<input class=cssButton type=button value="提  交" onclick="submitForm()">
<input class=cssButton type=button value="打  印" onclick="easyPrint()">-->
<a href="javascript:void(0);" class="button" onClick="submitForm();">提    交</a>
<a href="javascript:void(0);" class="button" onClick="easyPrint();">打    印</a>
<input type="hidden" name="OperateType" value=""></form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br />
<br />
<br />
<br />
<br />
</body>
</html>
