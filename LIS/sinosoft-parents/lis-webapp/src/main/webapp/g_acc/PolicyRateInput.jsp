<%
/***************************************************************
 * <p>ProName：PolicyRateInput.jsp</p>
 * <p>Title：保单利率维护</p>
 * <p>Description：保单利率维护</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2014-07-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<head>
	<title>结算利率公布</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./PolicyRateInput.js"></script>
	<%@include file="./PolicyRateInit.jsp"%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
</script>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./PolicyRateSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	
	<div id="divQuery" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>承保机构</td>
				<td class=input><input class=codeno name=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,ManageComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,ManageComName],[0,1],null,null,null,1);"><input class=codename name=ManageComName></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=AppntName></td>
				<td class=title>投保书号</td>
				<td class=input><input class="wid common" name=GrpPropNo></td>
			</tr>
			<tr class=common>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=PolicyNo></td>
				<td class=title>生效起期</td>
				<td class=input><input class=coolDatePicker name=StartDate dateFormat="short" onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>生效止期</td>
				<td class=input><input class=coolDatePicker name=EndDate dateFormat="short" onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="查  询" onclick="queryClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divPolicyGrid);">
			</td>
			<td class=titleImg>保单信息列表</td>
		</tr>
	</table>
	<div id=divPolicyGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanPolicyGrid"></span></td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divPolicyRateGrid);">
			</td>
			<td class=titleImg>结算利率列表</td>
		</tr>
	</table>
	<div id=divPolicyRateGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanPolicyRateGrid"></span></td>
			</tr>
		</table>
	</div>
	
	<input class=cssButton type=button value="保  存" onclick="saveClick();">
	
	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNo>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
