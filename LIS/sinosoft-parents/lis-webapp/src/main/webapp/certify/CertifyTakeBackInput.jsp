<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
	//程序名称：
	//程序功能：
	//创建日期：2002-10-08
	//创建人  ：kevin
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="CertifyTakeBackInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertifyTakeBackInit.jsp"%>
</head>

<body onload="initForm()" style="behavior:url(#default#clientCaps)"
	id="oClientCaps">
<form action="./CertifyTakeBackSave.jsp" method=post name=fm id=fm
	target="fraSubmit">
<table class="common">
	<tr class="common">
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divOtherInfo);"></td>
		<td class="titleImg">核销信息</td>
	</tr>
</table>

<Div  id= "divOtherInfo" style= "display: ''" class="maxbox1">
<table class="common">
	<!--  <tr class="common">
		<td class="title">发放者</td>
		<td class="input"><input class="common" name="SendOutCom"
			verify="发放者|NOTNULL"></td>
		<td class="title">接收者</td>
		<td class="input"><input class="common" name="ReceiveCom"
			verify="接收者|NOTNULL"></td>
	</tr> 
	<tr class="common">
		<td class="title">失效日期</td>
		<td class="input"><input class="readonly" readonly
			name="InvalidDate"></td>
		<td class="title">最大金额</td>
		<td class="input"><input class="readonly" readonly name="Amnt"></td>
	</tr>-->

	<tr class="common">
		<td class="title5">经办人</td>
		<td class="input5"><input class="common wid" name="Handler" id="Handler"></td>
		<td class="title5">经办日期</td>
		<td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="批复日期|NOTNULL" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
	</tr>

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

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCertifyList);"></td>
		<td class=titleImg>单证列表</td>
	</tr>
</table>
<Div id="divCertifyList" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanCertifyList"></span></td>
	</tr>
</table>
</div>

<!--<input class="cssButton" type="Button" value="核销单证"
	onclick="submitForm()">--><br>
    <a href="javascript:void(0);" class="button" onClick="submitForm();">核销单证</a>
     <input type=hidden name="chkPrt"
	type="checkbox" 打印清单></form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
