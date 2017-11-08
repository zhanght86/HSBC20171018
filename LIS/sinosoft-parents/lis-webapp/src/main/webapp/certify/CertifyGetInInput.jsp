
<%
	// 防止IE缓存页面
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%
	//程序名称：单证入库 CertifyGetInInput.jsp
	//程序功能：单证印刷后or单证发放到下级机构时，需要接收机构做单证入库操作
	//创建日期：2009-01-04
	//创建人  ：mw
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="CertifyGetInInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertifyGetInInit.jsp"%>
</head>

<body onload="initForm()" style="behavior:url(#default#clientCaps)"
	id="oClientCaps">
<form action="./CertifyGetInSave.jsp" method="post" name=fm id=fm
	target="fraSubmit">
<table class="common">
	<tr class="common">
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divSendOutInfo);"></td>
		<td class="titleImg">查询条件</td>
	</tr>
</table>
<Div  id= "divSendOutInfo" style= "display: ''" class="maxbox1">
<table class="common">
	<TR class=common>
		<td class="title5">单证编码</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
            onclick="return showCodeList('certifycode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('certifycode', [this,CertifyName],[0,1],null,null,null,1);"><Input
			class=codename name=CertifyName id=CertifyName readonly=true></td>
		<td class="title5">登录机构</td>
		<td class="input5"><input class="readonly wid" readonly
			name="managecom" id="managecom"></td>
	</TR>

	<TR class=common>
		<TD class=title5>单证类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass id=CertifyClass
			CodeData="0|^D|重要有价单证^B|重要空白单证^P|普通单证"
			ondblClick="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
            onClick="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass',[this,CertifyClassName],[0,1]);"><Input
			class=codename name=CertifyClassName id=CertifyClassName readonly=true></TD>
		<TD class=title5>单证业务类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass2 id=CertifyClass2
			CodeData="0|^0|投保类^1|承保类^2|保全类^3|理赔类^4|财务类^5|条款^6|产品说明书^7|其他"
			ondblClick="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
            onClick="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"><Input
			class=codename name=CertifyClass2Name id=CertifyClass2Name readonly=true></TD>
	</TR>

	<tr class="common">
		<td class="title5">操作员</td>
		<td class="input5"><input class="readonly wid" readonly
			name="Operator" id="Operator"></td>
		<td class="title5">操作日期</td>
		<td class="input5"><input class="readonly wid" readonly
			name="OperateDate" id="OperateDate"></td>
	</tr>
</table>
</div>

<!--<input class=cssButton type=button value="待入库查询" onclick="queryClick()">-->
<a href="javascript:void(0);" class="button" onClick="queryClick();">待入库查询</a>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCertifyList);"></td>
		<td class=titleImg>待入库单证列表</td>
	</tr>
</table>
<div id="divCertifyList">
<table class="common">
	<tr class="common">
		<td style="text-align: left" colSpan=1><span id="spanCertifyListGrid"></span></td>
	</tr>
</table>
<center><INPUT VALUE="首  页" class=cssButton90 TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="上一页" class=cssButton91
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="下一页"
	class=cssButton92 TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="尾  页" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>
</div>

<!--<input name="btnOp1" class=cssButton type=button value="确认入库"
	onclick="submitForm()"> <input name="btnOp2" class=cssButton
	type=button value="拒绝入库" onclick="cancelGetIn()"> --><input
	type=hidden name="operateFlag">
    <a href="javascript:void(0);" name="btnOp1" class="button" onClick="submitForm();">确认入库</a>
    <a href="javascript:void(0);" name="btnOp2" class="button" onClick="cancelGetIn();">拒绝入库</a>
    </form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>

</body>
</html>
