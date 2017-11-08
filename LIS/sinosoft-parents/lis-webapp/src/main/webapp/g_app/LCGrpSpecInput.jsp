<%
/***************************************************************
 * <p>ProName：LCGrpSpecInput.jsp</p>
 * <p>Title：特别约定</p>
 * <p>Description：特别约定</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-04-01
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
%>
<script>

	var tOfferListNo = "<%=tOfferListNo%>";
	var tOperator = "<%=tOperator%>";
</script>
<html>
<head >
	<title>特别约定</title>
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
	<script src="./LCGrpSpecInput.js"></script>
	<%@include file="./LCGrpSpecInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGrpSpec);">
			</td>
			<td class=titleImg>特别约定</td>
		</tr>
	</table>
	
	<div id="divGrpSpec" class=maxbox1 style="display: ''">
		<table class=common>
    		<tr class=common>
				<td class= title><textarea id="GrpSpec" name="GrpSpec" id=GrpSpec cols="100" rows="5" class="common" ></textarea><span style="color: red">（特别约定小于1500字）</span></td>
			</tr>
		</table>
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	
	<input type=hidden name=Operate>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
