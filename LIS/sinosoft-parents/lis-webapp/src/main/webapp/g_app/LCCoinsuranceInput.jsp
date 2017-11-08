<%
/***************************************************************
 * <p>ProName：LCCoinsuranceInput.jsp</p>
 * <p>Title：共保设置</p>
 * <p>Description：共保设置</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-03
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tOperator = tGI.Operator;
	String tGrpContNo = request.getParameter("GrpContNo");
	String tFlag = request.getParameter("Flag");
%>
<script>
	var tGrpContNo = "<%=tGrpContNo%>";
	var tFlag = "<%=tFlag%>";
</script>
<html>
<head >
	<title>共保设置</title>
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
	<script src="./LCCoinsuranceInput.js"></script>
	<%@include file="./LCCoinsuranceInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LCCoinsuranceSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCoinsurance);">
			</td>
			<td class=titleImg>共保设置</td>
		</tr>
	</table>
	
	<div id="divCoinsurance" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanCoinsuranceGrid" ></span>
				</td>
			</tr>
		</table>

		<div id="divTurnPage" style="display: ''">
			<center>		
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			</center>
		</div>
		<table class=common>
			<tr class=common>
				<td class=title>共保主/从方标志</td>
				<td class=input><input class=codeno name=MasterSlaveFlag id=MasterSlaveFlag verify="共保主/从方标志|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('coinmasterslave', [this,MasterSlaveName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('coinmasterslave', [this,MasterSlaveName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=MasterSlaveName readonly elementtype=nacessary></td>
				<td class=title>公司名称 </td>
				<td class=input><input class=codeno name=CoinComCode id=CoinComCode verify="公司名称|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('insurancecom', [this,CoinComName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('insurancecom', [this,CoinComName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=CoinComName readonly elementtype=nacessary></td>
				<td class=title>保额分摊比例</td>
				<td class=input><input class="wid common" name=AmntShareRate id=AmntShareRate verify="保额分摊比例|NUM&VALUE>0&VALUE<1" elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>保费分摊比例</td>
				<td class=input><input class="wid common" id=PremShareRate name=PremShareRate verify="保费分摊比例|NUM&VALUE>0&VALUE<1" elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
			  	<td class=title colspan=6></td>
			</tr>
		</table>
		<br/>
		<div id="divButton" style="display: none">
			<input class=cssButton type=button value="新  增" name="AddButton" onclick="addClick();">
			<input class=cssButton type=button value="修  改" name="ModButton" onclick="modifyClick();">
			<input class=cssButton type=button value="删  除" name="DelButton" onclick="deleteClick();">
		</div>
		<div id="divButton2" style="display: ''">
			<input class=cssButton type=button value="关  闭" onclick="top.close();">
		</div>
	</div>
	<input type=hidden name=Operate>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
