<%
/***************************************************************
 * <p>ProName：LSQuotBJPlanDetailInput.jsp</p>
 * <p>Title：报价单打印--报价方案明细</p>
 * <p>Description：报价单打印--报价方案明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-20
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
	String tOfferListNo = request.getParameter("OfferListNo");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
	String tTranProdType = request.getParameter("TranProdType");
	String tQuotQuery = request.getParameter("QuotQuery");
%>
<script>
	var tOfferListNo = "<%=tOfferListNo%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tTranProdType = "<%=tTranProdType%>";
	var tOperator = "<%=tOperator%>";
	var tQuotQuery = "<%=tQuotQuery%>";
</script>
<html>
<head >
	<title>报价方案明细</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotBJPlanDetailInput.js"></script>
	<%@include file="./LSQuotBJPlanDetailInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlan);">
			</td>
			<td class=titleImg>方案明细</td>
		</tr>
	</table>
	
	<div id="divPlan"class=maxbox1 style="display: ''">
		<div id="divBJPlanDetail">
		</div>
		<div id="divTurnPage" style="display: ''">
			<table align=center>
				<input class=cssButton90 type=button value="首  页" onclick="firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="lastPage();">
				<input class="wid readonly"  name=PageInfo id=PageInfo readonly value="">
			</table>
		</div>
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	
	
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
