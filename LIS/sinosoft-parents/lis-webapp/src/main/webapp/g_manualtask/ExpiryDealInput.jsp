<%
/***************************************************************
 * <p>ProName：ExpiryDealInput.jsp</p>
 * <p>Title：满期处理</p>
 * <p>Description：满期处理</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<head>
	<title>满期处理</title>
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
	<script src="./ExpiryDealInput.js"></script>
	<%@include file="./ExpiryDealInit.jsp"%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
</script>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divExpiryInfo);">
			</td>
			<td class=titleImg>满期处理</td>
		</tr>
	</table>
	
	<div id="divExpiryInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>满期日期</td>
				<td class=input><input class=coolDatePicker name=ExpiryDate verify="满期日期|NOTNULL&DATE" dateFormat="short" elementtype=nacessary onClick="laydate({elem: '#ExpiryDate'});" id="ExpiryDate"><span class="icon"><a onClick="laydate({elem: '#ExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="满期处理" onclick="expiryClick();">
	</div>
	
	<!--隐藏区-->
	<input type=hidden name=Operate>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
