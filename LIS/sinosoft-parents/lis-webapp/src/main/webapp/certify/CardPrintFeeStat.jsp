
<%
	//程序名称：CardPrintFeeStat.jsp
	//程序功能：机构印刷费用统计报表
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
<script src="./CardPrintFeeStat.js"></script>
<%@include file="CardPrintFeeStatInit.jsp"%>
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
		<TD class=title5>发放机构</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=SendOutCom id=SendOutCom
			verify="发放机构|NOTNULL"
			ondblclick="return showCodeList('station',[this,SendOutComName],[0,1]);"
            onMouseDown="return showCodeList('station',[this,SendOutComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,SendOutComName],[0,1]);"><input
			class="codename" name="SendOutComName" id="SendOutComName" readonly><font
			color=red>*</font></TD>
		<TD class=title5>接收机构</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ReceiveCom id=ReceiveCom
			verify="接收机构|NOTNULL"
			ondblclick="return showCodeList('station',[this,ReceiveComName],[0,1]);"
            onMouseDown="return showCodeList('station',[this,ReceiveComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ReceiveComName],[0,1]);"><input
			class="codename" name="ReceiveComName" id="ReceiveComName" readonly><font
			color=red>*</font></TD>
	</tr>

	<tr class="common">
		<td class="title5">开始日期</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="makedateB" verify="开始日期|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#makedateB'});" verify="开始日期|NOTNULL" dateFormat="short" name=makedateB id="makedateB"><span class="icon"><a onClick="laydate({elem: '#makedateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
		<td class="title5">终止日期</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="makedateE" verify="终止日期|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#makedateE'});" verify="终止日期|NOTNULL" dateFormat="short" name=makedateE id="makedateE"><span class="icon"><a onClick="laydate({elem: '#makedateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
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
		<td class=titleImg>机构印刷费用统计报表</td>
	</tr>
</table>
<div id="divCardPrintInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td style="text-align: left" colSpan=1><span id="spanCardPrintInfoGrid"></span></td>
	</tr>
</table>
<!--<center><input VALUE="首  页" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton"> <input
	VALUE="上一页" TYPE="button" onclick="turnPage.previousPage();"
	class="cssButton"> <input VALUE="下一页" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton"> <input
	VALUE="尾  页" TYPE="button" onclick="turnPage.lastPage();"
	class="cssButton"></center>-->
</div>

<!--<input value="打印报表" type="button" onclick="certifyPrint()"
			class="cssButton">-->
            <a href="javascript:void(0);" class="button" onClick="certifyPrint();">打印报表</a>
<input type="hidden" name="ASendOutCom"> <input type="hidden"
	name="AReceiveCom"></form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br>
</body>
</html>
