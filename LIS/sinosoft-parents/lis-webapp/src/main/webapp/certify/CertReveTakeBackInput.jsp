
<%
	// 防止IE缓存页面
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
	//程序名称：CertReveTakeBackInput.jsp
	//程序功能：核销修订确认
	//创建日期：2009-2-4
	//创建人  ：mw
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="CertReveTakeBackInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertReveTakeBackInit.jsp"%>
</head>

<body onload="initForm()" style="behavior:url(#default#clientCaps)"
	id="oClientCaps">
<form action="./CertReveTakeBackSave.jsp" method="post" name=fm id=fm
	target="fraSubmit">

<table class="common">
	<tr class="common">
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divSendOutInfo);"></td>
		<td class="titleImg">已申请查询条件</td>
	</tr>
</table>
<Div  id= "divSendOutInfo" style= "display: ''" class="maxbox1">
<table class="common">
	<tr class="common">
		<td class="title5">申请人</td>
		<td class="input5"><input class="common wid" name="Handler" id="Handler"></td>
		<td class="title5">申请日期</td>
		<td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="有效开始日期|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
	</tr>

	<tr class="common">
		<td class="title5">操作员</td>
		<td class="input5"><input class="readonly wid" readonly
			name="Operator" id="Operator"></td>
		<td class="title5">操作日期</td>
		<td class="input5"><input class="readonly wid" readonly
			name="OperateDate" id="OperateDate"></td>
	</tr>

	<tr class="common">
		<td class="title5">登录机构</td>
		<td class="input5"><input class="readonly wid" readonly name="ComCode" id="ComCode"></td>
	</tr>
</table>
</div>
<!--<input class=cssButton type=button value="已申请查询" onclick="queryClick()">-->
<a href="javascript:void(0);" class="button" onClick="queryClick();">已申请查询</a>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCertifyList);"></td>
		<td class=titleImg>单证列表</td>
	</tr>
</table>
<div id="divCertifyList">
<table class="common">
	<tr class="common">
		<td text-align: left colSpan=1><span id="spanCertifyList"></span></td>
	</tr>
</table>
<center><INPUT VALUE="首  页" class=cssButton90 TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="上一页" class=cssButton91
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="下一页"
	class=cssButton92 TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="尾  页" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>
</div>

<input style="display:none" name="btnOp" class="cssButton" type="button" value="修订确认"
	onclick="submitForm()">
    <a href="javascript:void(0);" name="btnOp" class="button" onClick="submitForm();">修订确认</a>
    </form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
