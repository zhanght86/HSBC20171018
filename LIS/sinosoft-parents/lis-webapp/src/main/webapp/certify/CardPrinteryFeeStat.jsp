
<%
	//程序名称：CardPrinteryFeeStat.jsp
	//程序功能：印刷厂费用统计报表
	//创建日期： 2009-02-18
	//创建人  ： mw
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<title>单证信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="../common/css/Project.css" rel="stylesheet" type="text/css">
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
<script src="../common/javascript/Common.js"></script>
<script src="../common/cvar/CCodeOperate.js"></script>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<script src="../common/javascript/EasyQuery.js"></script>
<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
<script src="../common/javascript/VerifyInput.js"></script>
<script src="./CertifyCommon.js"></script>
<script src="./CardPrinteryFeeStat.js"></script>
<%@include file="CardPrinteryFeeStatInit.jsp"%>
</head>

<body onload="initForm();">
<form method=post name=fm id=fm target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divQueryCondition);"></td>
		<td class=titleImg>查询条件</td>
	</tr>
</table>
<div id="divQueryCondition" style="display: ''">
<div class="maxbox">
<table class="common">
	<tr class="common">
		<td class="title5">印刷厂名称</td>
		<td class="input5"><input class="wid" class="common" name="Printery" id="Printery"></td>
	</tr>

	<tr class="common">
		<td class="title5">单证编码</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"><input
			class=codename name=CertifyName id=CertifyName readonly></td>
		<TD class=title5>计划类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PlanType id=PlanType
			CodeData="0|^0|预算外^1|一季度^2|二季度^3|三季度^4|四季度^5|新/改版^6|自印单证"
			ondblClick="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
            onMouseDown="showCodeListEx('PlanType',[this,PlanTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('PlanType',[this,PlanTypeName],[0,1]);"><Input
			class=codename name=PlanTypeName id=PlanTypeName readonly=true></TD>
	</tr>

	<tr class="common">
		<td class="title5">印刷日期（起始）</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="PrintDateB">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#PrintDateB'});" verify="有效开始日期|DATE" dateFormat="short" name=PrintDateB id="PrintDateB"><span class="icon"><a onClick="laydate({elem: '#PrintDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
		<td class="title5">印刷日期（终止）</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="PrintDateE">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#PrintDateE'});" verify="有效开始日期|DATE" dateFormat="short" name=PrintDateE id="PrintDateE"><span class="icon"><a onClick="laydate({elem: '#PrintDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>
</table>
</div></div>

<!--<input value="查  询" type="button" onclick="certifyQuery()"
			class="cssButton">-->
            <a href="javascript:void(0);" class="button" onClick="certifyQuery();">查    询</a>


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divCardPrintInfo);"></td>
		<td class=titleImg>印刷厂费用统计报表</td>
	</tr>
</table>
<div id="divCardPrintInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td style="text-align: left" colSpan=1><span id="spanCardPrintInfoGrid"></span></td>
	</tr>
</table>
<!--<center><input VALUE="首  页" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton90"> <input
	VALUE="上一页" TYPE="button" onclick="turnPage.previousPage();"
	class="cssButton91"> <input VALUE="下一页" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton92"> <input
	VALUE="尾  页" TYPE="button" onclick="turnPage.lastPage();"
	class="cssButton93"></center>-->
</div>

<!--<table>
	<tr>
		<td><input value="打印报表" type="button" onclick="certifyPrint()"
			class="cssButton"></td>
	</tr>
</table>-->
<a href="javascript:void(0);" class="button" onClick="certifyPrint();">打印报表</a>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
