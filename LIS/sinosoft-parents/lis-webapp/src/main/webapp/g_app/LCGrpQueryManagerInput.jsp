<%
/***************************************************************
 * <p>ProName：LCGrpQueryManagerInput.jsp</p>
 * <p>Title：查询客户经理</p>
 * <p>Description：查询客户经理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-13
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
	
	String tManageCom = request.getParameter("ManageCom");
	String tMulLineNo = request.getParameter("SelNo");
	
%>
<script>
	var tOperator = "<%=tOperator%>";//当前登录人
	var tManageCom = "<%=tManageCom%>";//登录机构
	var tMulLineNo = "<%=tMulLineNo%>";//MulLine序号
	
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>客户经理查询</title>
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
	<script src="./LCGrpQueryManagerInput.js"></script>
	<%@include file="./LCGrpQueryManagerInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- 查询条件 -->
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>客户经理查询条件</td>
		</tr>
	</table>
	<div id="divInfo1" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>客户经理名称</td>
				<td class=input><input class="wid common" id=PreCustomerNo name=PreCustomerNo></td>
				<td class=title>所属机构</td>
				<td class=input>
					<input class=codeno name=PreCustomerName id=PreCustomerName style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comcode like #<%=tManageCom%>%%#','1','1',180);" onkeyup="showCodeListKey('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comcode like #<%=tManageCom%>%%#','1','1',180);"><input class=codename name=ManageComName >
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryManager();">
		<input class=cssButton type=button value="选  择" onclick="returnManager();">
	</div>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult1);">
			</td>
			<td class=titleImg>客户经理信息列表</td>
		</tr>
	</table>
	<div id="divResult1" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanManagerGrid"></span>
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
	<input type=hidden name=Operate>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
