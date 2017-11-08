<%
/***************************************************************
 * <p>ProName：BalanceAppInput.jsp</p>
 * <p>Title：结算申请</p>
 * <p>Description：结算申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head >
	<title>结算申请</title>
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
	<script src="./BalanceAppInput.js"></script>
	<%@include file="./BalanceAppInit.jsp"%>
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
				<td class=input><input class=codeno name=Managecom value="<%=tGI.ManageCom%>" readonly  style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('managecom',[this, ManagecomName],[0, 1],null,'1','1','1',180);" 
					onkeyup="return showCodeListKey('managecom',[this, ManagecomName],[0, 1],null,'1','1','1',180);"><input class=codename name=ManagecomName readonly></td>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
			</tr>
			<tr class=common>
				<td class=title>应结算起期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=StartBalanceDate onClick="laydate({elem: '#StartBalanceDate'});" id="StartBalanceDate"><span class="icon"><a onClick="laydate({elem: '#StartBalanceDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>应结算止期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=EndBalanceDate onClick="laydate({elem: '#EndBalanceDate'});" id="EndBalanceDate"><span class="icon"><a onClick="laydate({elem: '#EndBalanceDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>定期结算频率</td>
				<td class=input><input class=codeno  name=BalancePeriod style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('balanceperiod',[this, BalancePeriodName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('balanceperiod',[this, BalancePeriodName],[0, 1],null,null,null,'1',180);"><input class=codename name=BalancePeriodName readonly></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton type=button value="查  询" onclick="queryClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divContInfoGrid);">
			</td>
			<td class=titleImg>保单信息</td>
		</tr>
	</table>
	<div id="divContInfoGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanContInfoGrid"></span>
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
<div id=DivPosInfo style="display:none">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPosInfoGrid);">
			</td>
			<td class=titleImg>保全信息</td>
		</tr>
	</table>
	<div id="divPosInfoGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPosInfoGrid"></span>
				</td>
			</tr>
		</table>
		<input class=cssButton type=button value="手工结算" onclick="handDeal();">
		<table class=common>
			<tr class=common>
				<td class=title>总结算金额(元):</td>
				<td class=input><input class="wid readonly" name=SumMoney ></td>
				<td class=title>所选保全结算金额(元):</td>
				<td class=input><input class="wid readonly" name=SelectPosMoney></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
	</div>
</div>
	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNoQ>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
