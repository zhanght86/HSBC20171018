
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
	//程序名称：CertifySendOutConfirmInput.jsp
	//程序功能：增领确认
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
<script src="CertifyCommon.js"></script>
<SCRIPT src="CertifySendOutConfirmInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CertifySendOutConfirmInit.jsp"%>
</head>

<body onload="initForm()" style="behavior:url(#default#clientCaps)"
	id="oClientCaps">
<form action="./CertifySendOutConfirmSave.jsp" method="post" name=fm id=fm
	target="fraSubmit">
<table class="common">
	<tr class="common">
		<td class="common"><img src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divSendOutInfo);"></td>
		<td class="titleImg">已批复查询条件</td>
	</tr>
</table>


<Div  id= "divSendOutInfo" style= "display: ''" class="maxbox1">
<table class="common">
	<TR class=common>
		<TD class=title5>部门来源</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Department id=Department
			CodeData="0|^1|从部门发放^2|发放至部门^3|和部门无关"
			ondblClick="showCodeListEx('Department',[this,DepartmentName],[0,1]);"
            onClick="showCodeListEx('Department',[this,DepartmentName],[0,1]);"
			onkeyup="showCodeListKeyEx('Department',[this,DepartmentName],[0,1]);"><Input
			class=codename name=DepartmentName id=DepartmentName readonly=true></TD>
		<TD class=title5>部门编码</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DepartmentNo  id=DepartmentNo
			CodeData="0|^01|个人业务部^02|银行保险部^03|多元销售部^04|联办项目组^05|保费部^06|培训部^07|市场部^08|客户服务部(分公司为运营部)^09|财务部^10|业务管理部"
			ondblClick="showCodeListEx('DepartmentNo',[this,DepartmentNoName],[0,1]);"
            onClick="showCodeListEx('DepartmentNo',[this,DepartmentNoName],[0,1]);"
			onkeyup="showCodeListKeyEx('DepartmentNo',[this,DepartmentNoName],[0,1]);"><Input
			class=codename name=DepartmentNoName id=DepartmentNoName readonly=true></TD>
	</TR>

	<tr class="common">
		<td class="title5">发放者</td>
		<td class="input5" nowrap=true><input class="common wid"
			name="SendOutCom" id="SendOutCom"
			onblur="getSendReceiveName(fm.SendOutCom,fm.SendOutComName)"></td>
		<td class="title5">发放者名称</td>
		<td class="input5"><input class="readonly wid" readonly
			name="SendOutComName" id="SendOutComName"></td>
	</tr>

	<tr class="common">
		<td class="title5">接收者</td>
		<td class="input5" nowrap=true><input class="common wid"
			name="ReceiveCom" id="ReceiveCom"
			onblur="getSendReceiveName(fm.ReceiveCom,fm.ReceiveComName)"></td>
		<td class="title5">接收者名称</td>
		<td class="input5"><input class="readonly wid" readonly
			name="ReceiveComName" id="ReceiveComName"></td>
	</tr>
</table>
</div>

<div id="divSendOutInfoAdd" style="display:none">
<table class="common">
	<tr class="common">
		<td class="title5">经办人</td>
		<td class="input5"><input class="common wid" name="Handler" id="Handler"></td>
		<td class="title5">经办日期</td>
		<td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="有效开始日期|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
	</tr>

	<tr class="common">
		<td class="title5">操作员</td>
		<td class="input5"><input class="readonly wid" readonly
			name="Operator" id="Operator"></td>
		<td class="title5">操作时间</td>
		<td class="input5"><input class="readonly wid" readonly
			name="OperateDate" id="OperateDate"></td>
	</tr>

	<tr class="common">
		<td class="title5">登录机构</td>
		<td class="input5"><input class="readonly wid" readonly name="ComCode"  id="ComCode"></td>
	</tr>
</table>
</div>

<!--<input class=cssButton type=button value="已批复查询" onclick="queryClick()">-->
<a href="javascript:void(0);" class="button" onClick="queryClick();">已批复查询</a>
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

</div>

		<input style="display:none" name="btnOp" class="cssButton" type="button" value="增领发放"
			onclick="submitForm()"><br>
<a href="javascript:void(0);" name="btnOp" class="button" onClick="submitForm();">增领发放</a>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
