<%
/***************************************************************
 * <p>ProName：QueryOfferListInput.jsp</p>
 * <p>Title：查询报价单号</p>
 * <p>Description：查询报价单号</p>
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
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tFlag = request.getParameter("Flag");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";//记录登陆机构
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tFlag = "<%=tFlag%>";
</script>
<html>
<head>
	<title>报价单号查询</title>
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
	<script src="./QueryOfferListInput.js"></script>
	<%@include file="./QueryOfferListInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">

<div id="div3" style="display: ''">
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
					<td class=title>准客户名称</td>
					<td class=input><input class="wid common" id=CustomerName name=CustomerName></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			<table>
			<input class=cssButton type=button  value="查  询" onclick="queryClick();">
		</div>
		<br>
		<table class=common>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryResult);">
				</td>
				<td class=titleImg>报价单信息</td>
			</tr>
		</table>
		<div id="divQueryResult" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanOfferListGrid" ></span>
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
		<br>
		<input class=cssButton type=button id=FeeRateSaveButton name=FeeRateSaveButton style="display: ''" value="选  择" onclick="selectOffer();">
		<input class=cssButton type=button id=BussRateSaveButton name=BussRateSaveButton style="display: ''" value="关  闭" onclick="top.close();">
		<input type=hidden name=Operate>
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
