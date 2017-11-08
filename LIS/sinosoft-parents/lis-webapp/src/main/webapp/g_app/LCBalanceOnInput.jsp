<%
/***************************************************************
 * <p>ProName：LCBalanceOnInput.jsp</p>
 * <p>Title：定期结算维护</p>
 * <p>Description：定期结算维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tFlag = request.getParameter("Flag");
%>
<script>
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tFlag = "<%=tFlag%>";
</script>
<html>
<head>
	<title>定期结算维护</title>
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
	<script src="./LCBalanceOnInput.js"></script>
	<%@include file="./LCBalanceOnInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">

	<form name=fm id=fm method=post action="" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFeeRateInfo);">
				</td>
				<td class=titleImg>定期结算业务</td>
			</tr>
		</table>
		<div id="divInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>结算方式</td>
					<td class=input><input class=codeno name=BalanceOnState id=BalanceOnState verify="结算方式|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('balanceonstate',[this,BalanceOnStateName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('balanceonstate',[this,BalanceOnStateName],[0,1],null,null,null,'1',null);">
					<input class=codename name=BalanceOnStateName id=BalanceOnStateName readonly=true elementtype=nacessary></td>
					<td class=title id=td1 style="display: ''">定期结算频率</td>
					<td class=input id=td2 style="display: ''"><input class=codeno name=BalancePeriod id=BalancePeriod  verify="定期结算频率|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('balanceperiod',[this,BalancePeriodName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('balanceperiod',[this,BalancePeriodName],[0,1],null,null,null,'1',null);"><input class=codename name=BalancePeriodName readonly=true elementtype=nacessary></td>
					<td class=title id=td3 style="display: ''">宽限期(单位:天)</td>
					<td class=input id=td4 style="display: ''"><input class="wid common" name=GracePeriod verify="宽限期|notnull&INT" elementtype=nacessary></td>
					<td class=title id=td5 style="display: none"></td>
					<td class=input id=td6 style="display: none"></td>
					<td class=title id=td7 style="display: none"></td>
					<td class=input id=td8 style="display: none"></td>
				</tr>
			</table>
		</div>
			<input class=cssButton type=button id=BalanceSaveButton name=BalanceSaveButton style="display: none" value="保  存" onclick="balanceSubmit();">
			<input class=cssButton type=button id=ReturnButton name=ReturnButton style="display: ''" value="关  闭" onclick="top.close();">
			<input type=hidden name=Operate>
			<input type=hidden name=GrpPropNo>
		</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
