<%
/***************************************************************
 * <p>ProName��LCContPlanAllDetailInput.jsp</p>
 * <p>Title�� ������ϸ</p>
 * <p>Description�� ������ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
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
%>
<script>
	var tPolicyNo = "<%=tPolicyNo%>";
	var tContPlanType = "<%=tContPlanType%>";
</script>
<html>
<head >
	<title>������ϸ</title>
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
	<script src="./LCContPlanAllDetail1Input.js"></script>
	<script src="./LCContCommonInput.js"></script>
	<%@include file="./LCContPlanAllDetail1Init.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlan);">
			</td>
			<td class=titleImg>������ϸ</td>
		</tr>
	</table>
	
	<div id="divPlan" style="display: ''">
		<div id="divPlanDetail">
		</div>
		<div id="divTurnPage" style="display: ;text-align:center">
				<input class=cssButton90 type=button value="��  ҳ" onclick="firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="lastPage();">
				<input class=readonly name=PageInfo readonly value="" style="width:80px">
		</div>
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
	</div>
	
	
	<input type=hidden name=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
