<%
/***************************************************************
 * <p>ProName:EdorCCInput.jsp</p>
 * <p>Title:  建工险工程面积造价变更</p>
 * <p>Description:建工险工程面积造价变更</p>
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
	<title>建工险工程面积造价变更</title>
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
	<script src="./EdorCCInput.js"></script>
	<%@include file="./EdorCCInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>工程面积造价变更</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>保费计算方式</td>
				<td class=input><input type=hidden name=PremCalMode id=PremCalMode><input class="wid readonly" name=PremCalName id=PremCalName ></td>
				<td class=title>原总面积(平方米)/原总造价(元)</td>
				<td class=input>
					<div id=divEnginCost style="display: none"><input class="wid readonly" name=EnginCost id=EnginCost ></div>
					<div id=divEnginArea style="display: none"><input class="wid readonly" name=EnginArea id=EnginArea ></div>
				</td>
				<td class=title>变更后总面积(平方米)/总造价(元)</td>
				<td class=input>
					<div id=divEnginCostN style="display: none"><input class="wid common" verify="变更后总面积(平方米)/总造价(元)|NUM&>0" onblur="calInitPrem();" name=EnginCostN elementtype=nacessary></div>
					<div id=divEnginAreaN style="display: none"><input class="wid common" verify="变更后总面积(平方米)/总造价(元)|NUM&>0" onblur="calInitPrem();" name=EnginAreaN elementtype=nacessary></div>
				</td>
			</tr>
			<tr class=common>
				<td class=title>已发生变更次数</td>
				<td class=input><input class="wid readonly" name=Mtime id=Mtime ></td>
				<td class=title>按新单费率系统计算保费(元)</td>
				<td class=input><input class="wid readonly" name=CalPrem id=CalPrem >
					<input type=hidden name=FirstPrem id=FirstPrem >
					<input type=hidden name=FirstEnginCost id=FirstEnginCost >
					<input type=hidden name=FirstEnginArea id=FirstEnginArea>
					</td>
				<td class=title>实交保费(元)</td>
				<td class=input><input class="wid common" name=GetMoney id=GetMoney verify="实交保费(元)|NOTNULL&NUM" elementtype=nacessary></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title>工程变更描述</td>
				<td class=input><textarea cols=70 rows=3 name=ReasonDesc id=ReasonDesc verify="工程变更描述|NOTNULL" elementtype=nacessary></textarea></td>
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
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNo>
	<input type=hidden name=CurrentDate>
	<input type=hidden name=EdorAppNo>
	<input type=hidden name=MissionID> <!-- 工作任务ID -->
	<input type=hidden name=SubMissionID> <!-- 子工作任务ID -->
	<input type=hidden name=ActivityID> <!-- 工作节点ID -->
  <Br /> <Br /> <Br /> <Br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
