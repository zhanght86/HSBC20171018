<%
/***************************************************************
 * <p>ProName：LJTempFeeCancelInput.jsp</p>
 * <p>Title：财务作废</p>
 * <p>Description：财务作废</p>
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
	<title>财务作废</title>
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
	<script src="./LJTempFeeCancelInput.js"></script>
	<%@include file="./LJTempFeeCancelInit.jsp"%>
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
				<td class=title>交费方式</td>
				<td class=input><input class=codeno name=QueryPayType id=QueryPayType ondblclick="return showCodeList('paytype', [this,QueryPayTypeName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('paytype', [this,QueryPayTypeName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=QueryPayTypeName id=QueryPayTypeName readonly></td>
				<td class=title>客户开户行</td>
				<td class=input><input class=codeno name=QueryCustBankCode id=QueryCustBankCode ondblclick="return showCodeList('headbank', [this,QueryCustBankName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('headbank', [this,QueryCustBankName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=QueryCustBankName id=QueryCustBankName readonly></td>
				<td class=title>客户银行账号</td>
				<td class=input><input class="wid common" name=QueryCustBankAccNo id=QueryCustBankAccNo></td>
			</tr>
			<tr class=common>
				<td class=title>客户账户名/汇款人姓名</td>
				<td class=input><input class="wid common" name=QueryCustAccName id=QueryCustAccName></td>
				<td class=title>投保单位名称</td>
				<td class=input><input class="wid common" name=QueryGrpName id=QueryGrpName></td>
				<td class=title>客户经理姓名</td>
				<td class=input><input class="wid common" name=QueryAgentName id=QueryAgentName></td>
			</tr>
			<tr class=common>
				<td class=title>申请起期</td>
				<td class=input><input class=coolDatePicker name=QueryApplyStartDate dateFormat="short" onClick="laydate({elem: '#QueryApplyStartDate'});" id="QueryApplyStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryApplyStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>申请止期</td>
				<td class=input><input class=coolDatePicker name=QueryApplyEndDate dateFormat="short" onClick="laydate({elem: '#QueryApplyEndDate'});" id="QueryApplyEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryApplyEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>录入起期</td>
				<td class=input><input class=coolDatePicker name=QueryInputStartDate dateFormat="short" onClick="laydate({elem: '#QueryInputStartDate'});" id="QueryInputStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryInputStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>录入止期</td>
				<td class=input><input class=coolDatePicker name=QueryInputEndDate dateFormat="short" onClick="laydate({elem: '#QueryInputEndDate'});" id="QueryInputEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryInputEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>审核起期</td>
				<td class=input><input class=coolDatePicker name=QueryConfirmStartDate dateFormat="short" onClick="laydate({elem: '#QueryConfirmStartDate'});" id="QueryConfirmStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryConfirmStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>审核止期</td>
				<td class=input><input class=coolDatePicker name=QueryConfirmEndDate dateFormat="short" onClick="laydate({elem: '#QueryConfirmEndDate'});" id="QueryConfirmEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryConfirmEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryApplyTempFee();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>进账信息列表</td>
		</tr>
	</table>
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanTempFeeInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="导出数据" onclick="expData();">
		</center>
	</div>
	<div id="divInfo4" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
				</td>
				<td class=titleImg>进账录入信息维护</td>
			</tr>
		</table>
		<div id="divInfo3" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>交费方式</td>
					<td class=input><input type=hidden name=PayType id=PayType><input class="wid readonly" name=PayTypeName id=PayTypeName readonly></td>
					<td class=title>客户开户行</td>
					<td class=input><input type=hidden name=CustBankCode id=CustBankCode><input class="wid readonly" name=CustBankName id=CustBankName readonly></td>
					<td class=title>客户银行账号</td>
					<td class=input><input class="wid readonly" name=CustBankAccNo id=CustBankAccNo readonly></td>
				</tr>
				<tr class=common>
					<td class=title>客户账户名/汇款人姓名</td>
					<td class=input><input class="wid readonly" name=CustAccName id=CustAccName readonly></td>
					<td class=title>金额</td>
					<td class=input><input class="wid readonly" name=Money id=Money readonly></td>
					<td class=title>投保单位名称</td>
					<td class=input><input class="wid readonly" name=GrpName id=GrpName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>客户经理姓名</td>
					<td class=input><input class="wid readonly" name=AgentName id=AgentName readonly></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>到账日期</td>
					<td class=input><input class="wid readonly" name=EnterAccDate readonly></td>
					<td class=title>收款银行</td>
					<td class=input><input type=hidden name=InBankCode id=InBankCode readonly><input class="wid readonly" name=InBankCodeName readonly></td>
					<td class=title>收款账号</td>
					<td class=input><input class="wid readonly" name=InBankAccNo id=InBankAccNo readonly></td>
				</tr>
				<tr class=common id=tr2 name=tr2 style="display: none">
					<td class=title>付款单位</td>
					<td class=input><input class="wid readonly" name=DraweeName id=DraweeName readonly></td>
					<td class=title>支票号</td>
					<td class=input><input class="wid readonly" name=CheckNo id=CheckNo readonly></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>结论描述</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=CancelDesc elementtype=nacessary></textarea></td>
				</tr>
			</table>
			
			<input class=cssButton type=button value="作  废" onclick="cancelTempFee();">
		</div>
	</div>
	<input type=hidden name=SheetName id=SheetName>
	<input type=hidden name=SheetTitle id=SheetTitle>
	<input type=hidden name=SheetSql id=SheetSql>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate id=Operate>
	</div>
	
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
