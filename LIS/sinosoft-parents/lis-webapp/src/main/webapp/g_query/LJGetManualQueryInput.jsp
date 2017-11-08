<%
/***************************************************************
 * <p>ProName：LJGetManualQueryInput.jsp</p>
 * <p>Title：手动付费查询</p>
 * <p>Description：手动付费查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
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
	<title>手动付费查询</title>
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
	<script src="./LJGetManualQueryInput.js"></script>
	<%@include file="./LJGetManualQueryInit.jsp"%>
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
	<div id="divInfo1" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=QueryManageCom1 id=QueryManageCom1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode', [this,QueryManageComName1], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,QueryManageComName1], [0,1], null, null, null, 1);" readonly><input class=codename name=QueryManageComName1 readonly></td>
				<td class=title>申请起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate1 dateFormat="short" onClick="laydate({elem: '#QueryStartDate1'});" id="QueryStartDate1"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>申请止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate1 dateFormat="short" onClick="laydate({elem: '#QueryEndDate1'});" id="QueryEndDate1"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>批次号</td>
				<td class=input><input class="wid common" name=QueryBatchNo1 id=QueryBatchNo1></td>
				<td class=title></td>
				<td class=input></td>
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
			<td class=titleImg>批次信息列表</td>
		</tr>
	</table>
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanBatchInfoGrid" ></span>
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
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divInfo3" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=QueryManageCom2 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode', [this,QueryManageComName2], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,QueryManageComName2], [0,1], null, null, null, 1);" readonly><input class=codename name=QueryManageComName2 readonly></td>
				<td class=title>业务类型</td>
				<td class=input><input class=codeno name=QueryBussType2 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('finbusstype', [this,QueryBussTypeName2], [0,1]);" onkeyup="return returnShowCodeListKey('finbusstype', [this,QueryBussTypeName2], [0,1]);" readonly><input class=codename name=QueryBussTypeName2 readonly></td>
				<td class=title>业务号</td>
				<td class=input><input class="wid common" name=QueryBussNo2 id=QueryBussNo2></td>
			</tr>
			<tr class=common>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=QueryGrpContNo2 id=QueryGrpContNo2></td>
				<td class=title>确认起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate2 dateFormat="short" onClick="laydate({elem: '#QueryStartDate2'});" id="QueryStartDate2"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>确认止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate2 dateFormat="short" onClick="laydate({elem: '#QueryEndDate2'});" id="QueryEndDate2"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>应付起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate3 dateFormat="short" onClick="laydate({elem: '#QueryStartDate3'});" id="QueryStartDate3"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>应付止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate3 dateFormat="short" onClick="laydate({elem: '#QueryEndDate3'});" id="QueryEndDate3"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>状态</td>
				<td class=input><input class=codeno name=QueryBatState2 id=QueryBatState2 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('fingetmanualstate',[this,QueryBatStateName2],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('fingetmanualstate',[this,QueryBatStateName2],[0,1],null,null,null,1);" readonly><input class=codename name=QueryBatStateName2 readonly></td>
			</tr>
			<tr class=common>
				<td class=title>付款起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate4 dateFormat="short" onClick="laydate({elem: '#QueryStartDate4'});" id="QueryStartDate4"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate4'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>付款止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate4 dateFormat="short" onClick="laydate({elem: '#QueryEndDate4'});" id="QueryEndDate4"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate4'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>审核起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate5 dateFormat="short" onClick="laydate({elem: '#QueryStartDate5'});" id="QueryStartDate5"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate5'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>审核止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate5 dateFormat="short" onClick="laydate({elem: '#QueryEndDate5'});" id="QueryEndDate5"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate5'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>批次号</td>
				<td class=input><input class="wid common" name=QueryBatchNo2 id=QueryBatchNo2></td>
				<td class=title>共保公司名称</td>
				<td class=input><input class="wid common" name=QueryInsureCom2 id=QueryInsureCom2></td>
			</tr>
			<tr class=common>
				<td class=title>处理方式</td>
				<td class=input><input class=codeno name=QueryGetDealType id=QueryGetDealType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('fingetdealtype', [this,QueryGetDealTypeName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('fingetdealtype', [this,QueryGetDealTypeName], [0,1], null, null, null, 1);" readonly><input class=codename name=QueryGetDealTypeName readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryInfo1();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo4);">
			</td>
			<td class=titleImg>手动付费信息列表</td>
		</tr>
	</table>
	<div id="divInfo4" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQueryInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
			<input class=cssButton type=button value="导出数据" onclick="expData();">
		</center>
	</div>
	<input type=hidden name=SheetName id=SheetName>
	<input type=hidden name=SheetTitle id=SheetTitle>
	<input type=hidden name=SheetSql id=SheetSql>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
	
</form>
<br /><br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
