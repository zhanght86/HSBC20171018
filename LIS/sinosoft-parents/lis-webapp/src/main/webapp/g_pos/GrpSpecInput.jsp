<%
/***************************************************************
 * <p>ProName：GrpSpecInput.jsp</p>
 * <p>Title：保单特别约定</p>
 * <p>Description：保单特别约定</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tGrpContNo = request.getParameter("GrpContNo");
%>
<script>
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tGrpContNo = "<%=tGrpContNo%>";
</script>
<html>
<head >
	<title>保单特别约定</title>
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
	<script src="./GrpSpecInput.js"></script>
	<%@include file="./GrpSpecInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
		 	<td class=titleImg>保单特别约定</td>
		</tr>
	</table>
	<div id="divQueryInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>保单特别约定</td>
			</tr>
			<tr class=common>
				<td class=input colspan=6><textarea cols=120 rows=15 name=GrpSpec id=GrpSpec></textarea></td>
			</tr>
		</table>
	</div>
	<input class=cssButton type=button value="关  闭" onclick="top.close();">
	<input type=hidden name=Operate>
	<input type=hidden name=MissionID value="<%=tMissionID%>">
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>">
	<input type=hidden name=ActivityID value="<%=tActivityID%>">
	<input type=hidden name=EdorAppNo value="<%=tEdorAppNo%>">
	<input type=hidden name=GrpContNo value="<%=tGrpContNo%>">
 <Br/><Br/><Br/><Br/>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
