<%
/***************************************************************
 * <p>ProName：LCPolicyReturnInput.jsp</p>
 * <p>Title：回执查询</p>
 * <p>Description：回执查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-08-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var tManageCom = "<%= tGI.ManageCom %>";
	var tComCode="<%=tGI.ComCode%>";
	var tOperator="<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>回执查询</title>
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
	<script src="./LCPolicyReturnInput.js"></script>
	<%@include file="./LCPolicyReturnInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divQueryInfo" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom verify="管理机构|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,ManageComName],[0,1],null,'1','1','1',180);" onkeyup="return showCodeListKey('managecom',[this,ManageComName],[0,1],null,'1','1','1',180);"><input class=codename name=ManageComName readonly elementtype=nacessary></td>
				<td class=title>团体保单号</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
			</tr>
			<tr class=common>
				<td class=title>签单起期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=SignStartDate verify="签单起期|date" onClick="laydate({elem: '#SignStartDate'});" id="SignStartDate"><span class="icon"><a onClick="laydate({elem: '#SignStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>签单止期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=SignEndDate verify="签单止期|date" onClick="laydate({elem: '#SignEndDate'});" id="SignEndDate"><span class="icon"><a onClick="laydate({elem: '#SignEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>单证状态</td>
				<td class=input><input class=codeno name=CardState id=CardState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('cardstateflag',[this,CardStateName],[0,1],null,'1 and (code=#3# or code=#6#)','1',1,180);" onkeyup="return showCodeListKey('cardstateflag',[this,CardStateName],[0,1],null,'1 and (code=#3# or code=#6#)','1',1,180);"><input class=codename name=CardStateName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>回执回销起期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=ReturnStartDate verify="回销起期|date" onClick="laydate({elem: '#ReturnStartDate'});" id="ReturnStartDate"><span class="icon"><a onClick="laydate({elem: '#ReturnStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>回执回销止期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=ReturnEndDate verify="回销止期|date" onClick="laydate({elem: '#ReturnEndDate'});" id="ReturnEndDate"><span class="icon"><a onClick="laydate({elem: '#ReturnEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton value="查  询" type=button onclick="queryClick();">
		<input class=cssButton value="重  置" type=button onclick="resetClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQueryResult);">
			</td>
			<td class=titleImg>查询结果</td>
		</tr>
	</table>
	<div id="divQueryResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQueryResultGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
		
		<!--input class=cssButton value="下  载" type=button onclick="exportClick();"-->
	</div>
	
	<Input type=hidden name=Operate id=Operate>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
