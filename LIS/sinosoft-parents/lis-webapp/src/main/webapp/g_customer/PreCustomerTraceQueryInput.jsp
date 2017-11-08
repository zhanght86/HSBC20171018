<%
/***************************************************************
 * <p>ProName：PreCustomerTraceQueryInput.jsp</p>
 * <p>Title：准客户修改轨迹查询界面</p>
 * <p>Description：准客户修改轨迹查询界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tPreCustomerNo = request.getParameter("PreCustomerNo");
%>
<script>
	var tPreCustomerNo = "<%=tPreCustomerNo%>";
</script>
<html>
<head >
	<title>准客户修改轨迹查询</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/Calendar/Calendar.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./PreCustomerTraceQueryInput.js"></script>
	<%@include file="./PreCustomerTraceQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm method=post action="" target=fraSubmit>
	<!-- table>
		<tr>
			<td class=commontop>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
		 	<td class=titleImgtop>准客户查询条件</td>
		</tr>
	</table>
	
	<div id="divQueryInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>准客户名称</td>
				<td class=input colspan=3><input class=common style="width:300px" name=PreCustomerName></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<table>
			<input class=cssButton name=QueryButton type=button value="查  询" onclick="queryClick();">
			<input class=cssButton name=ReturnButton type=button value="返  回" onclick="top.close();">
		</table>
	</div -->
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPreCustomerGrid);">
			</td>
			<td class=titleImg>准客户修改轨迹列表</td>
		</tr>
	</table>
	<div id="divPreCustomerGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanPreCustomerGrid" ></span></td>
			</tr>
		</table>
		<div id= "divPreCustomerGridPage" style= "display: ''">
			<table align=center>
				<input class=cssButton type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton type=button value="尾  页" onclick="turnPage1.lastPage();">
			</table>
		</div>
	</div>
	
	<!--隐藏区-->
	<input type=hidden name=Operate>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
