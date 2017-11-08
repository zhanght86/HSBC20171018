
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
	//程序名称：CertReveTakeBackApplyInput.jsp
	//程序功能：核销修订申请
	//创建日期：2009-1-5
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
<SCRIPT src="CertReveTakeBackApplyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertReveTakeBackApplyInit.jsp"%>
</head>

<body onload="initForm()" style="behavior:url(#default#clientCaps)"
	id="oClientCaps">
<form action="./CertReveTakeBackApplySave.jsp" method="post" name=fm id=fm
	target="fraSubmit">

<table class="common">
	<tr class="common">
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divSendOutInfo);"></td>
		<td class="titleImg">修订信息</td>
	</tr>
</table>


<Div  id= "divSendOutInfo" style= "display: ''" class="maxbox1">
<table class="common">
	<tr class="common">
		<td class="title5">修订申请人</td>
		<td class="input5"><input class="common wid" name="Handler" id="Handler"
			verify="修订申请人|NOTNULL"><font color=red>*</font></td>
		<td class="title5">提交日期</td>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="提交日期|NOTNULL" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
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
        <td class="title5"></td><td class="input5"></td>
        
	</tr>
</table>
</div>

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
</table></div>
<Table class=common>
	<TR>
		<TD class=title5>修订备注<font color=red>*</font></TD>
		<TD colspan="3"><textarea name="Reason" cols="151%" rows="4"
			witdh=25% class="common" verify="修订备注|NOTNULL"></textarea></TD>
	</TR>
</table>


<input style="display:none" name="btnOp" class="cssButton" type="button" value="修订申请"
	onclick="submitForm()"></form>

<a href="javascript:void(0);" name="btnOp" class="button" onClick="submitForm();">修订申请</a>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
