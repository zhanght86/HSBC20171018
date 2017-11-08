<%
/***************************************************************
 * <p>ProName：LLClaimQuerySocialInfoInput.jsp</p>
 * <p>Title：账户查询</p>
 * <p>Description：账户查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : withxiaoqi
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  String tGrpRgtNo = request.getParameter("GrpRgtNo"); 
  String tRgtNo = request.getParameter("RgtNo"); 
  String tMode = request.getParameter("Mode"); 
%>
<script>
	var tGrpRgtNo="<%=tGrpRgtNo%>";
	var tRgtNo="<%=tRgtNo%>";
	var tMode="<%=tMode%>";
</script>
<html>
	<head>
		<title>账户查询</title>
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
		<script src="./LLClaimQueryAccInfoInput.js"></script>
		<%@include file="./LLClaimQueryAccInfoInit.jsp"%>
	</head>
	<body onload="initForm();initElementtype();">
		<form name=fm id=fm method=post action="./LLClaimQuestionSave.jsp" target=fraSubmit>
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLCQues);">
					</td>
					<td class=titleImg>账户信息列表</td>
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
			<input value="首  页" class=cssButton90 type=button onclick="turnPage1.firstPage();">
			<input value="上一页" class=cssButton91 type=button onclick="turnPage1.previousPage();">
			<input value="下一页" class=cssButton92 type=button onclick="turnPage1.nextPage();">
			<input value="尾  页" class=cssButton93 type=button onclick="turnPage1.lastPage();">
			</center>	
			</div>
			<input class=cssButton value="选  择" type=button name=choseButton onclick="returnInfo();">			
			<input class=cssButton value="关  闭" type=button onclick="top.close();">
			<input type=hidden name=tGrpRgtNo>
			<input type=hidden name=tRgtNo>
			<input type=hidden name=tOperate>
		</form>

		<span id="spanCode" style="display: none; position:absolute; slategray"></span>
	</body>
</html>