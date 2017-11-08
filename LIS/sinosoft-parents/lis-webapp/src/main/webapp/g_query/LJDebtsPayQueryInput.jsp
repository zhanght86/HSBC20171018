<%
/***************************************************************
 * <p>ProName：LJDebtsPayQueryInput.jsp</p>
 * <p>Title：坏账查询</p>
 * <p>Description：坏账查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-09-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate = PubFun.getCurrentDate();
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>坏账查询</title>
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
	<script src="./LJDebtsPayQueryInput.js"></script>
	<%@include file="./LJDebtsPayQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div style="display: 'none'">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanVirtualGrid" ></span>
				</td>
			</tr>
		</table>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divInfo1" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=QueryManageCom id=QueryManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode', [this,QueryManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,QueryManageComName], [0,1], null, null, null, 1);" readonly>
				<input class=codename name=QueryManageComName id=QueryManageComName readonly>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=AppntName id=AppntName></td>
			</tr>
			<tr class=common>
				<td class=title>应收起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate1 dateFormat="short" onClick="laydate({elem: '#QueryStartDate1'});" id="QueryStartDate1"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>应收止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate1 dateFormat="short" onClick="laydate({elem: '#QueryEndDate1'});" id="QueryEndDate1"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>申请起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate2 dateFormat="short" onClick="laydate({elem: '#QueryStartDate2'});" id="QueryStartDate2"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>申请止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate2 dateFormat="short" onClick="laydate({elem: '#QueryEndDate2'});" id="QueryEndDate2"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>审核起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate3 dateFormat="short" onClick="laydate({elem: '#QueryStartDate3'});" id="QueryStartDate3"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>审核止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate3 dateFormat="short" onClick="laydate({elem: '#QueryEndDate3'});" id="QueryEndDate3"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>坏账来源</td>
				<td class=input><input class=codeno name=QueryDebtsType id=QueryDebtsType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('debtstype', [this,QueryDebtsTypeName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('debtstype', [this,QueryDebtsTypeName], [0,1], null, null, null, 1);" readonly><input class=codename name=QueryDebtsTypeName id=QueryDebtsTypeName readonly></td>
				<td class=title>状态</td>
				<td class=input><input class=codeno name=QueryDebtsState id=QueryDebtsState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('debtsstate', [this,QueryDebtsStateName], [0,1], null, '1 and code!=#03#', '1', 1);" onkeyup="return showCodeListKey('debtsstate', [this,QueryDebtsStateName], [0,1], null, '1 and code!=#03#', '1', 1);" readonly><input class=codename name=QueryDebtsStateName id=QueryDebtsStateName readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryInfo();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>坏账信息列表</td>
		</tr>
	</table>
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanDebtsPayInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<div id="divInfo3" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanDebtsPayDetailInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
	</div>
	
</form>
<br /><br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
