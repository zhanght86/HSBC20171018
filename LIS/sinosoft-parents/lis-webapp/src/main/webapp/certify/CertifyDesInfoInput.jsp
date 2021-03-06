
<%
	//程序名称：CertifyDesInfoInput.jsp
	//程序功能：单证信息查询
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
<script src="./CertifyDesInfoInput.js"></script>
<%@include file="CertifyDesInfoInit.jsp"%>
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
		<td class="title5">单证编码</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"><input
			class=codename name=CertifyName id=CertifyName readonly></td>
		<TD class=title5>使用标志</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=State id=State
			verify="使用标志|NOTNULL" CodeData="0|^0|启用^1|停用"
			ondblClick="showCodeListEx('State',[this,StateName],[0,1]);"
            onMouseDown="showCodeListEx('State',[this,StateName],[0,1]);"
			onkeyup="showCodeListKeyEx('State',[this,StateName],[0,1]);"><Input
			class=codename name=StateName id=StateName readonly=true></TD>
	</tr>

	<TR class=common>
		<TD class=title5>单证类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass id=CertifyClass
			verify="单证类型|NOTNULL" CodeData="0|^D|重要有价单证^B|重要空白单证^P|普通单证"
			ondblClick="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
            onMouseDown="showCodeListEx('CertifyClass',[this,CertifyClassName],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass',[this,CertifyClassName],[0,1]);"><Input
			class=codename name=CertifyClassName id=CertifyClassName readonly=true></TD>
		<TD class=title5>单证业务类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyClass2 id=CertifyClass2
			verify="单证业务类型|NOTNULL"
			CodeData="0|^0|投保类^1|承保类^2|保全类^3|理赔类^4|财务类^5|条款^6|产品说明书^7|其他"
			ondblClick="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
             onMouseDown="showCodeListEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"
			onkeyup="showCodeListKeyEx('CertifyClass2',[this,CertifyClass2Name],[0,1]);"><Input
			class=codename name=CertifyClass2Name id=CertifyClass2Name readonly=true></TD>
	</TR>

	<tr class="common">
		<td class="title5">起始时间</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="MakeDateB" verify="入机日期（开始）|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});" verify="入机日期（开始）|NOTNULL" dateFormat="short" name=MakeDateB id="MakeDateB"><span class="icon"><a onClick="laydate({elem: '#MakeDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
		<td class="title5">终止时间</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="MakeDateE" verify="入机日期（结束）|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateE'});" verify="入机日期（结束）|NOTNULL" dateFormat="short" name=MakeDateE id="MakeDateE"><span class="icon"><a onClick="laydate({elem: '#MakeDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>
</table>
</div></div>
<input value="查  询" type="button" onclick="certifyQuery()" class="cssButton">


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divCardSendOutInfo);"></td>
		<td class=titleImg>单证信息</td>
	</tr>
</table>
<div id="divCardSendOutInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td style="text-align: left" colSpan=1><span id="spanCardSendOutInfoGrid"></span></td>
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

<input value="打印清单" type="button" onclick="certifyPrint()" class="cssButton">
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
