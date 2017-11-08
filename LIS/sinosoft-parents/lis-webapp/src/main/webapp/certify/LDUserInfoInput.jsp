
<%
	//程序名称：LDUserInfoInput.jsp
	//程序功能：单证人员信息查询
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
<title>单证管理员信息查询</title>
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
<script src="./LDUserInfoInput.js"></script>
<%@include file="LDUserInfoInit.jsp"%>
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
<div class="maxbox1">
<table class="common">
	<tr class="common">
		<TD class=title5>管理机构</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom
			ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
            onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			class="codename" name="ManageComName" id="ManageComName" readonly></TD>
		<td class="title5">统计粒度</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=grade id=grade readonly
			CodeData="0|^2|总公司^4|二级机构^6|三级机构^8|四级机构"
			ondblclick="return showCodeListEx('grade',[this,gradeName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeListEx('grade',[this,gradeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKeyEx('grade',[this,gradeName],[0,1],null,null,null,1);"><input
			class="codename" name="gradeName" id="gradeName" readonly></td>
	</tr>

	<tr class="common">
		<td class="title5">截止日期</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="validstartdate">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#validstartdate'});" verify="有效开始日期|DATE" dateFormat="short" name=validstartdate id="validstartdate"><span class="icon"><a onClick="laydate({elem: '#validstartdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
	</tr>

</table>
</div></div>
<!--<input value=" 查  询 " type="button" onclick="certifyQuery()"
			class="cssButton">-->
<a href="javascript:void(0);" class="button" onClick="certifyQuery();">查    询</a>


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divUserInfo);"></td>
		<td class=titleImg>管理员信息</td>
	</tr>
</table>
<div id="divUserInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td text-align: left colSpan=1><span id="spanUserInfoGrid"></span></td>
	</tr>
</table><center><input VALUE="首  页" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton90"> <input
	VALUE="上一页" TYPE="button" onclick="turnPage.previousPage();"
	class="cssButton91"> <input VALUE="下一页" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton92"> <input
	VALUE="尾  页" TYPE="button" onclick="turnPage.lastPage();"
	class="cssButton93"></center></div><!--<input value="打印清单" type="button" onclick="certifyPrint()"
	class="cssButton"></form>-->

<a href="javascript:void(0);" class="button" onClick="certifyPrint();">打印清单</a>


<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
