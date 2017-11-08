<%
/***************************************************************
 * <p>ProName：LLClaimQuerySocialInfoInput.jsp</p>
 * <p>Title：社保查询</p>
 * <p>Description：社保查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  String tGrpRgtNo = request.getParameter("GrpRgtNo"); 
  String tRgtNo = request.getParameter("RgtNo"); 
%>
<script>
	var tGrpRgtNo="<%=tGrpRgtNo%>";
	var tRgtNo="<%=tRgtNo%>";
</script>
<html>
	<head>
		<title>社保查询</title>
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
		<script src="./LLClaimQuerySocialInfoInput.js"></script>
		<%@include file="./LLClaimQuerySocialInfoInit.jsp"%>
	</head>
	<body onload="initForm();initElementtype();">
		<form name=fm id=fm method=post action="./LLClaimQuestionSave.jsp" target=fraSubmit>
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLCQues);">
					</td>
					<td class=titleImg>社保信息列表</td>
				</tr>
			</table>
			<div id="divLCQues" style="display:''">
  			<table class=common>
					<tr>
						<td text-align: left colSpan=1>
							<span id="spanQuesRecordGrid"></span>
						</td>
					</tr>
				</table> 
				<center>
					<input class=cssButton90 value="首  页" type=button onclick="turnPage1.firstPage();">
					<input class=cssButton91 value="上一页" type=button onclick="turnPage1.previousPage();">
					<input class=cssButton92 value="下一页" type=button onclick="turnPage1.nextPage();">
					<input class=cssButton93 value="尾  页" type=button onclick="turnPage1.lastPage();">
				</center>
			</div>
			<input class=cssButton value="关  闭" type=button onclick="top.close();">
			<input type=hidden name=tGrpRgtNo id=tGrpRgtNo>
			<input type=hidden name=tRgtNo id=tRgtNo>
			<input type=hidden name=tOperate id=tOperate>
		</form>

		<span id="spanCode" style="display: none; position:absolute; slategray"></span>
	</body>
</html>