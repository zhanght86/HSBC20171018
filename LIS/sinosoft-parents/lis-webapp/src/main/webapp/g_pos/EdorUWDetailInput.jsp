<%
/***************************************************************
 * <p>ProName：EdorUWDetailInput.jsp</p>
 * <p>Title：保全核保</p>
 * <p>Description：保全核保</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorNo = request.getParameter("EdorNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tEdorNo = "<%=tEdorNo%>";
</script>
<html>
<head >
	<title>保全核保</title>
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
	<script src="./EdorUWDetailInput.js"></script>
	<script src="./EdorCommonInput.js"></script>
	<%@include file="./EdorUWDetailInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorAppInfo);">
			</td>
		 	<td class=titleImg>保全受理信息</td>
		</tr>
	</table>
	<div id="divEdorAppInfo" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>保全受理号</td>
				<td class=input><input class="wid readonly" name=EdorAppNo id=EdorAppNo value="<%=tEdorAppNo%>" readonly></td>
				<td class=title>保全批单号</td>
				<td class=input><input class="wid readonly" name=EdorNo id=EdorNo value="<%=tEdorNo%>" readonly></td>
				<td class=title>保全申请方式</td>
				<td class=input><input class="wid readonly" name=AppMode id=AppMode readonly></td>
			</tr>
			<tr class=common>
				<td class=title>客户申请日期</td>
				<td class=input><input class="wid readonly" name=AppDate id=AppDate readonly></td>
				<td class=title>公司接收日期</td>
				<td class=input><input class="wid readonly" name=ReceiveDate id=ReceiveDate readonly></td>
				<td class=title>受理日期</td>
				<td class=input><input class="wid readonly" name=AcceptDate id=AcceptDate readonly></td>
			</tr>
			<tr class=common>
				<td class=title>保单号</td>
				<td class=input><input class="wid readonly" name=PolicyNo id=PolicyNo readonly></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid readonly" name=AppntName id=AppntName readonly></td>
				<td class=title>保单生效日期</td>
				<td class=input><input class="wid readonly" name=ValDate id=ValDate readonly></td>
			</tr>
			<tr class=common>
				<td class=title>缴费方式</td>
				<td class=input><input class="wid readonly" name=PayIntv id=PayIntv readonly></td>
				<td class=title>是否定结</td>
				<td class=input><input class="wid readonly" name=BanlanceFlag id=BanlanceFlag readonly></td>
				<td class=title>理赔后退费规则</td>
				<td class=input><input class="wid readonly" name=AfterClmRule id=AfterClmRule readonly></td>
			</tr>
		</table>
		<input class=cssButton value="保单明细查询" type=button onclick="policyDetClick();">
		<input class=cssButton value="人员清单查询" type=button onclick="insuredQuery();">
		<input class=cssButton value="保险方案查询" type=button onclick="showPlanQuery();">
		<input class=cssButton value="人员信息分布" type=button onclick="insuredStatistic();">
		<input class=cssButton value="特约信息" type=button onclick="queryGrpSpec();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorTypeGrid);">
			</td>
			<td class=titleImg>保全项目信息</td>
		</tr>
	</table>
	<div id="divEdorTypeGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanEdorTypeGrid"></span>
				</td>
			</tr>
		</table>
		<div id= "divEdorTypeGridPage" style= "display: ''">
			<center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			</center>
		</div>

		<input class=cssButton type=button value="保全项目明细" onclick="detailClick();">
		<input class=cssButton type=button value="影像件查询" onclick="queryScanPage();">
		<input class=cssButton type=button value="问题件处理" onclick="goToQuestion();">
		<input class=cssButton type=button value="附件管理" onclick="showAttachment();">
		<input class=cssButton type=button value="批单预览" onclick="print();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorUW);">
			</td>
			<td class=titleImg>保全核保信息</td>
		</tr>
	</table>
	<div id="divEdorUW" class=maxbox1 style="display: ''">
		<!-- 再保不再进行判断
		<input class=cssButton value="再保处理" type=button onclick="gotoReins();">
		<br>--->
		<input class=cssButton value="核保处理" type=button onclick="goToGErr();">
		<br>
		<input class=cssButton value="整单核保确认" type=button onclick="uwConfirm();">
		<input class=cssButton value="返  回" type=button onclick="returnClick();">
	</div>

	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden name=HidGrpContNo>
	<input type=hidden name=MissionID value="<%=tMissionID%>">
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>">
	<input type=hidden name=ActivityID value="<%=tActivityID%>">
	<input type=hidden name=ReinsFlag value="0">
	<input type=hidden name=GrpPropNo >
	<br /><br /><br /><br /><br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
