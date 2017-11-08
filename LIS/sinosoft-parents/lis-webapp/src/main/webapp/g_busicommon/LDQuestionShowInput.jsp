<%
/***************************************************************
 * <p>ProName：LDQuestionShowInput.jsp</p>
 * <p>Title：问题件对话展示</p>
 * <p>Description：问题件对话展示</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-13
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tOtherNoType = request.getParameter("OtherNoType");
	String tOtherNo = request.getParameter("OtherNo");
	String tSubOtherNo = request.getParameter("SubOtherNo");
	String tActivityID = request.getParameter("ActivityID");
	String tShowStyle = request.getParameter("ShowStyle");
%>
<script>
	var tOtherNoType = "<%=tOtherNoType%>";
	var tOtherNo = "<%=tOtherNo%>";
	var tSubOtherNo = "<%=tSubOtherNo%>";
	var tActivityID = "<%=tActivityID%>";
	var tShowStyle = "<%=tShowStyle%>";
</script>
<html>
<head >
	<title>问题件对话展示</title>
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
	<script src="./LDQuestionShowInput.js"></script>
	<%@include file="./LDQuestionShowInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divShow);">
			</td>
			<td class=titleImg>对话展示</td>
		</tr>
	</table>
	<div id="divShow" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title colspan=6>
					<div id="divShowDetail" style="display: ''">
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<input class=cssButton type=button value="关  闭" onclick="top.close();">
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
