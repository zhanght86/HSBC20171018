<%
/***************************************************************
 * <p>ProName：LCPolicyQueryInput.jsp</p>
 * <p>Title：保单查询</p>
 * <p>Description：保单查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
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
	<title>保单查询</title>
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
	<script src="./LCPolicyQueryInput.js"></script>
	<%@include file="./LCPolicyQueryInit.jsp"%>
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
				<td class=title>承保机构</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,ManageComName],[0,1],null,null,null,null,180);" onkeyup="return showCodeListKey('managecom',[this,ManageComName],[0,1],null,null,null,null,180);"><input class=codename name=ManageComName ></td>
				<td class=title>投保单号</td>
				<td class=input><input class="wid common" name=GrpPropNo id=GrpPropNo></td>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
			</tr>
			<tr class=common>
				<td class=title>报价单号</td>
				<td class=input><input class="wid common" name=OfferListNo></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName></td>
				<td class=title>销售渠道明细</td>
				<td class=input><input class=codeno name=SaleChnl id=SaleChnl style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('agenttype',[this,SaleChnlName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('agenttype',[this,SaleChnlName],[0,1],null,null,null,1);"><input class=codename name=SaleChnlName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>客户经理</td>
				<td class=input><input class=codeno name=AgentCode id=AgentCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('agentcode',[this,AgentCodeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('agentcode',[this,AgentCodeName],[0,1],null,null,null,1);"><input class=codename name=AgentCodeName></td>
				<td class=title>签单起期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=SignStartDate verify="签单起期|date" onClick="laydate({elem: '#SignStartDate'});" id="SignStartDate"><span class="icon"><a onClick="laydate({elem: '#SignStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>签单止期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=SignEndDate verify="签单止期|date" onClick="laydate({elem: '#SignEndDate'});" id="SignEndDate"><span class="icon"><a onClick="laydate({elem: '#SignEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>服务机构</td>
				<td class=input><input class=codeno name=ServiceCom id=ServiceCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,ServiceComName],[0,1],null,null,null,null,180);" onkeyup="return showCodeListKey('managecom',[this,ServiceComName],[0,1],null,null,null,null,180);"><input class=codename name=ServiceComName readonly></td>
				<td class=title>险种名称</td>
				<td class=input><input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('contrisk',[this,RiskCodeName],[0,1],null,null,null,null,180);" onkeyup="return showCodeListKey('contrisk',[this,RiskCodeName],[0,1],null,null,null,null,180);"><input class=codename name=RiskCodeName readonly></td>
				<td class=title>询价号</td>
				<td class=input><input class="wid common" name=QuotNo id=QuotNo></td>
			</tr>
		</table>

		<input class=cssButton value="查  询" type=button onclick="queryClick(1);">
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

		<input class=cssButton value="保单明细" type=button onclick="policyDetClick();">
	</div>

	<Input type=hidden name=Operate id=Operate>
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
