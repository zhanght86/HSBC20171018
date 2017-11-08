<%
/***************************************************************
 * <p>ProName：LCinsuredQueryInput.jsp</p>
 * <p>Title：查询被保人信息</p>
 * <p>Description：查询被保人信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tInsuredName = new String(StrTool.unicodeToGBK(request.getParameter("InsuredName")));
	String tManageCom = request.getParameter("ManageCom");
	String tFlag = request.getParameter("Flag");	
%>
<script>
	var tOperator = "<%=tGI.Operator%>";
	var tManageCom = "<%=tManageCom%>";
		
</script>
<html>
<head >
	<title>查询被保人信息</title>
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
	<script src="./LCinsuredQueryInput.js"></script>
	<%@include file="./LCinsuredQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCommunalGrid);">
			</td>
		 	<td class=titleImg>被保人列表信息</td>
		</tr>
	</table>
	<div id="divCommunalGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanCustomerGrid" ></span></td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	<input class=cssButton type=button value="选  择" onclick="returnback();">
	<input class=cssButton type=button value="关  闭" onclick="top.close();">
	
	<!--隐藏区-->
	<input type=hidden id="GrpPropNo" name=GrpPropNo value='<%=tGrpPropNo%>'><!-- 保单号-->
	<input type=hidden id="InsuredName" name=InsuredName value='<%=tInsuredName%>'><!-- 被保人姓名-->
	<input type=hidden id="ManageCom" name=Flag value='<%= tFlag%>'><!-- 标记-->
</form>
<br /><br /><br /><br />
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
