
<%
	//程序名称：CertifyInfoInput.jsp 
	//程序功能：查询单证轨迹信息
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
<script src="./CertifyTrackInfoInput.js"></script>
<%@include file="CertifyTrackInfoInit.jsp"%>
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
			verify="单证编码|NOTNULL"
			ondblclick="return showCodeList('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeList('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"><input
			class=codename name=CertifyCodeName id=CertifyCodeName readonly><font color=red>*</font></td>
		<td class="title5">单证状态</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=StateFlag id=StateFlag
			CodeData="0|^1|待入库^2|已入库^3|已发放未核销^4|自动缴销^5|手工缴销^6|使用作废^7|停用作废^8|逾期^9|挂失^10|遗失^11|销毁"
			ondblclick="return showCodeListEx('StateFlag',[this,StateFlagName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeListEx('StateFlag',[this,StateFlagName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKeyEx('StateFlag',[this,StateFlagName],[0,1],null,null,null,1);"><input
			class="codename" name="StateFlagName" id="StateFlagName" readonly></td>
	</tr>

	<tr class="common">
		<td class="title5">起始号</td>
		<td class="input5"><input class="wid" class="common" name="StartNo" id="StartNo"
			verify="起始号|NOTNULL"><font color=red>*</font></td>
		<td class="title5">终止号</td>
		<td class="input5"><input class="wid" class="common" name="EndNo" id="EndNo"
			verify="终止号|NOTNULL"><font color=red>*</font></td>
	</tr>

	<tr class="common">
	<tr class="common">
		<td class="title5">发放者</td>
		<td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom"></td>
		<td class="title5">接收者</td>
		<td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom"></td>
	</tr>


	<tr class="common">
		<td class="title5">操作员</td>
		<td class="input5"><input class="wid" class="common" name="Operator" id="Operator"></td>
		<td class="title5">经办人</td>
		<td class="input5"><input class="wid" class="common" name="Handler" id="Handler"></td>
	</tr>

	<tr class="common">
		<td class="title5">开始日期</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="MakeDateB" verify="开始日期|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});" verify="开始日期|NOTNULL" dateFormat="short" name=MakeDateB id="MakeDateB"><span class="icon"><a onClick="laydate({elem: '#MakeDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
		<td class="title5">结束日期</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="MakeDateE" verify="结束日期|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateE'});" verify="结束日期|NOTNULL" dateFormat="short" name=MakeDateE id="MakeDateE"><span class="icon"><a onClick="laydate({elem: '#MakeDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
	</tr>
</table>
</div>
</div>
<!--<table>
	<tr>
		<td><input value="单证轨迹查询" type="button"
			onclick="certifyTrackQuery()" class="cssButton"></td>
		<td><input value=" 打    印 " type="button"
			onclick="certifyPrint()" class="cssButton"></td>
	</tr>
</table>-->
<a href="javascript:void(0);" class="button" onClick="certifyTrackQuery();">单证轨迹查询</a>
<a href="javascript:void(0);" class="button" onClick="certifyPrint();">打    印</a>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divCardInfo);"></td>
		<td class=titleImg>单证信息</td>
	</tr>
</table>
<div id="divCardInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td text-align: left colSpan=1><span id="spanCardInfoGrid"></span></td>
	</tr>
</table>
<center><input VALUE="首  页" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton90"> <input
	VALUE="上一页" TYPE="button" onclick="turnPage.previousPage();"
	class="cssButton91"> <input VALUE="下一页" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton92"> <input
	VALUE="尾  页" TYPE="button" onclick="turnPage.lastPage();"
	class="cssButton93"></center>
</div>

</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
