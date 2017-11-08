<%
/***************************************************************
 * <p>ProName：LJSendDataQueryInput.jsp</p>
 * <p>Title：付费数据查询</p>
 * <p>Description：付费数据查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-11-18
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
</script>
<html>
<head>
	<title>付费数据查询</title>
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
	<script src="./LJSendDataQueryInput.js"></script>
	<%@include file="./LJSendDataQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div style="display: none">
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
	<div id="divInfo1" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>管理机构1</td>
				<td class=input><input class=codeno name=QueryManageCom id=QueryManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" value="<%=tGI.ManageCom%>" ondblclick="return returnShowCodeList('conditioncomcode', [this,QueryManageComName], [0,1]);" onkeyup="return returnShowCodeListKey('conditioncomcode', [this,QueryManageComName], [0,1]);" readonly><input class=codename name=QueryManageComName readonly></td>
				<td class=title>团体保单号</td>
				<td class=input><input class="wid common" name=QueryGrpContNo></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=QueryGrpName></td>
			</tr>
			<tr class=common>
				<td class=title>业务类型</td>
				<td class=input><input class=codeno name=QueryFinBussType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('finbusstype', [this,QueryFinBussTypeName], [0,1], null,null,null,1);" onkeyup="return returnShowCodeList('finbusstype', [this,QueryFinBussTypeName], [0,1], null,null,null,1);" readonly><input class=codename name=QueryFinBussTypeName></td>
				<td class=title>业务号码</td>
				<td class=input><input class="wid common" name=QueryBussNo></td>
				<td class=title>支付方式</td>
				<td class=input><input class=codeno name=QueryGetMode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('fingetmode', [this,QueryGetModeName], [0,1], null,'1 and code in (#00#,#01#)','1',1);" onkeyup="return showCodeListkey('fingetmode', [this,QueryGetModeName], [0,1], null,'1 and code in (#00#,#01#)','1',1);" readonly><input class=codename name=QueryGetModeName></td>
			</tr>
			<tr class=common>
				<td class=title>数据状态</td>
				<td class=input><input class=codeno name=QueryFinState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('finstate', [this,QueryFinStateName], [0,1], null,null,null,1);" onkeyup="return showCodeListkey('finstate', [this,QueryFinStateName], [0,1], null,null,null,1);" readonly><input class=codename name=QueryFinStateName></td>
				<td class=title>业务确认起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate1 dateFormat="short" elementtype=nacessary onClick="laydate({elem: '#QueryStartDate1'});" id="QueryStartDate1"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>业务确认止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate1 dateFormat="short" elementtype=nacessary onClick="laydate({elem: '#QueryEndDate1'});" id="QueryEndDate1"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>发盘可提取起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate2 dateFormat="short" onClick="laydate({elem: '#QueryStartDate2'});" id="QueryStartDate2"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>发盘可提取止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate2 dateFormat="short" onClick="laydate({elem: '#QueryEndDate2'});" id="QueryEndDate2"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>转账起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate3 dateFormat="short" onClick="laydate({elem: '#QueryStartDate3'});" id="QueryStartDate3"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>转账止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate3 dateFormat="short" onClick="laydate({elem: '#QueryEndDate3'});" id="QueryEndDate3"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>处理方式</td>
				<td class=input><input class=codeno name=QueryGetDealType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('fingetdealtype', [this,QueryGetDealTypeName], [0,1], null, null , null, 1);" onkeyup="return showCodeListKey('fingetdealtype', [this,QueryGetDealTypeName], [0,1], null, null , null, 1);" readonly><input class=codename name=QueryGetDealTypeName readonly></td>
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
			<td class=titleImg>查询结果列表</td>
		</tr>
	</table>
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanDataInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="导出数据" onclick="exportData();">
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
