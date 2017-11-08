<%
/***************************************************************
 * <p>ProName：LCBnfQueryInput.jsp</p>
 * <p>Title：受益人信息维护</p>
 * <p>Description：受益人信息维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tFlag = request.getParameter("Flag");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tContNo = request.getParameter("ContNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tInsuredNo = "<%=tInsuredNo%>";
	var tContNo = "<%=tContNo%>";

</script>
<html>
<head >
	<title>受益人信息维护</title>
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
	<script src="./LCBnfQueryInput.js"></script>
	<%@include file="./LCBnfQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LCBnfQueryESave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
			<td class=titleImg>受益人信息</td>
		</tr>
	</table>
	<div id="divQueryInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanBnfGrid" ></span>
				</td>
			</tr>
		</table>
	</div>
		
	<div id=div1 style="display: ''">
		<input class=cssButton type=button value="保  存" onclick="bnfSave();">
		<input class=cssButton type=button value="关  闭" onclick="top.close();	">
	</div>
	<div id=div2 style="display: none">
	<hr size="2" color="#D7E1F6">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
		
	<!--隐藏信息-->
	<input type=hidden name=Operate>
	<input type=hidden id="GrpPrtno" name="GrpPropNo" value="<%=tGrpPropNo%>"> 
	<input type=hidden id="ContType" name="ContType">  	
	<input type=hidden name=InsuredSeqNo value="<%=tInsuredNo%>"> <!-- 当前操作的个人客户号 -->
	<input type=hidden name=mContNo value="<%=tContNo%>"> <!-- 当前操作的个人保单号 -->	
	<input type=hidden name=Flag value="<%=tFlag%>">
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
