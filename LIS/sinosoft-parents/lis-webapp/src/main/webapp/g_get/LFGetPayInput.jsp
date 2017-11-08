<%
/***************************************************************
 * <p>ProName：LFGetPayInput.jsp</p>
 * <p>Title：生存领取</p>
 * <p>Description：生存领取</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-09-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrenDate = PubFun.getCurrentDate();
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tCurrenDate = "<%=tCurrenDate%>";
</script>
<html>
<head >
	<title>生存领取</title>
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
	<script src="./LFGetPayInput.js"></script>
	<%@include file="./LFGetPayInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
		 	<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>承保机构</td>
				<td class=input><input class=codeno name=Managecom id=Managecom readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
					ondblclick="return showCodeList('conditioncomcode',[this, ManagecomName],[0, 1],null,'1 and comgrade=#03# and comcode like #<%=tGI.ManageCom%>%%#','1','1',180);" 
					onkeyup="return showCodeListKey('conditioncomcode',[this, ManagecomName],[0, 1],null,'1 and comgrade=#03# and comcode like #<%=tGI.ManageCom%>%%#','1','1',180);"><input class=codename name=ManagecomName readonly></td>
				<td class=title>团体保单号</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
			</tr>
			<tr class=common>
				<td class=title>个人保单号</td>
				<td class=input><input class="wid common" name=ContNo id=ContNo></td>
				<td class=title>被保险人姓名</td>
				<td class=input><input class="wid common" name=InsuredName id=InsuredName></td>
				<td class=title>证件号码</td>
				<td class=input><input class="wid common" name=IDNo id=IDNo></td>
			</tr>
			<tr class=common>
				<td class=title>领取起期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=GetStartDate onClick="laydate({elem: '#GetStartDate'});" id="GetStartDate"><span class="icon"><a onClick="laydate({elem: '#GetStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>领取止期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=GetEndDate onClick="laydate({elem: '#GetEndDate'});" id="GetEndDate"><span class="icon"><a onClick="laydate({elem: '#GetEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton type=button value="查  询" onclick="queryClick(1);">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUnGetGrid);">
			</td>
			<td class=titleImg>未领取情况明细</td>
		</tr>
	</table>
	<div id="divUnGetGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanUnGetGrid"></span>
				</td>
			</tr>
		</table>
		<div id= "divUnGetGridPage" style= "display: ;text-align:center">
			<table align=center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			</table>
		</div>
	</div>
	
	<input class=cssButton type=button value="选择领取" onclick="selGetClick();">
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGetGrid);">
			</td>
			<td class=titleImg>已领取情况明细</td>
		</tr>
	</table>
	<div id="divGetGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanGetGrid"></span>
				</td>
			</tr>
		</table>
		<div id= "divGetGridPage" style= "display: ;text-align:center">
			<table align=center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
			</table>
		</div>
	</div>
	
	<input class=cssButton type=button value="撤  销" onclick="cancelClick();">
	<input class=cssButton type=button value="申请确认" onclick="appConfClick();">
	
	<!--隐藏区-->
	<input type=hidden name=Operate>
	<br /><br /><br /><br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
