<%
/***************************************************************
 * <p>ProName:EdorBZInput.jsp</p>
 * <p>Title:  建工险保单中止</p>
 * <p>Description:建工险保单中止</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-25
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorType = request.getParameter("EdorType");
	String tEdorNo = request.getParameter("EdorNo");
	String tGrpContNo = request.getParameter("GrpContNo");

%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo = "<%=tEdorNo%>";
</script>
<html>
<head>
	<title>建工险保单中止</title>
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
	<script src="./EdorBZInput.js"></script>
	<%@include file="./EdorBZInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>保单中止</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>保单中止原因</td>
				<td class=input><input class=codeno name=Reason id=Reason readonly  verify="保单中止原因|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('queryexp',[this, ReasonName],[0, 1],null,'surrenderBZ','concat(codetype,codeexp)','1',180);" 
					onkeyup="return showCodeListKey('queryexp',[this, ReasonName],[0, 1],null,'surrenderBZ','concat(codetype,codeexp)','1',180);"><input class=codename name=ReasonName id=ReasonName elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>保单中止描述</td>
				<td class=input><textarea cols=70 rows=3 name=ReasonDesc id=ReasonDesc verify="保单中止描述|NOTNULL" elementtype=nacessary></textarea></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
			<div id="divButton01" name=Button01 style="display: none">
				<input class=cssButton type=button value="保  存" onclick="saveClick();"></div>
			<div id="divButton02" name=Button02 style="display: ''">
				<input class=cssButton type=button value="关  闭" onclick="top.close();"></div>
	</div>
	
	<!--隐藏区-->
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=GrpContNo id=GrpContNo>
	<input type=hidden name=CurrentDate id=CurrentDate>
	<input type=hidden name=EdorAppNo id=EdorAppNo>
	<input type=hidden name=MissionID id=MissionID> <!-- 工作任务ID -->
	<input type=hidden name=SubMissionID id=SubMissionID> <!-- 子工作任务ID -->
	<input type=hidden name=ActivityID id=ActivityID> <!-- 工作节点ID -->

</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
