<%
/***************************************************************
 * <p>ProName：LSQuotGrpSpecInput.jsp</p>
 * <p>Title：特别约定</p>
 * <p>Description：特别约定</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
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
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
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
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotGrpSpecInput.js"></script>
	<%@include file="./LSQuotGrpSpecInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm  id=fm method=post action="./LSQuotGrpSpecSave.jsp" target=fraSubmit>
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
				<td class= titl8><textarea id="GrpSpec" name="GrpSpec" cols="100" rows="5" class="common" ></textarea><span style="color: red">（特别约定小于1500字）</span></td>
			</tr>
		</table>
		<input class=cssButton type=button value="保  存" name="SaveButton"  id=SaveButton onclick="saveClick();">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	
	<div id="divStdGrpSpec" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGrpSpecInfo);">
				</td>
				<td class=titleImg>标准特别约定信息</td>
			</tr>
		</table>
		
		<div id="divGrpSpecInfo"  class=maxbox1 style="display: ''">
			<input class=cssButton type=button value="选  择" name="SelectButton" onclick="selectClick();">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanGrpSpecInfoGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<input type=hidden name=Operate id=Operate> 
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
