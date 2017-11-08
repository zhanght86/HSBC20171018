<%
/***************************************************************
 * <p>ProName：EdorNCAllDetailInput.jsp</p>
 * <p>Title： 方案明细一览</p>
 * <p>Description： 方案明细一览</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-05-26
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
	String tPolicyNo = request.getParameter("PolicyNo");
	String tContPlanType = request.getParameter("ContPlanType");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
%>
<script>
	var tPolicyNo = "<%=tPolicyNo%>";
	var tContPlanType = "<%=tContPlanType%>";
	var tEdorNo = "<%=tEdorNo%>";
	var tEdorType = "<%=tEdorType%>";
</script>
<html>
<head >
	<title>方案明细一览</title>
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
	<script src="./EdorCommonInput.js"></script>
	<script src="./EdorNCAllDetailInput.js"></script>
	<%@include file="./EdorNCAllDetailInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlan);">
			</td>
			<td class=titleImg>方案明细一览</td>
		</tr>
	</table>
	
	<div id="divPlan" style="display: ''">
		<div id="divPlanDetail">
		</div>
		<div id="divTurnPage" style="display: ''">
			<center>
				<input class=cssButton90 type=button value="首  页" onclick="firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="lastPage();">
				<input class=readonly name=PageInfo readonly value="">
			</center>
		</div>
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	
	
	<input type=hidden name=Operate>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
