<%
/***************************************************************
 * <p>ProName：LCGrpUWInput.jsp</p>
 * <p>Title：人工核保</p>
 * <p>Description：人工核保<</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

	String tFlag = request.getParameter("Flag");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tContPlanType = request.getParameter("ContPlanType");

%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tContPlanType = "<%=tContPlanType%>";
</script>
<html>
<head >
	<title>人工核保</title>
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
	<script src="./LCGrpUWInput.js"></script>
	<script src="./LCContCommonInput.js"></script>
	<%@include file="./LCGrpUWInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action=" " target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGrpContNews);">
			</td>
			<td class=titleImg>团体投保单信息</td>
		</tr>
	</table>
	<div id="divGrpContNews" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>承保机构</td>
				<td class=input><input class="wid readonly" name=ManageCom id=ManageCom value="" readonly></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid readonly" name=GrpName id=GrpName value="" readonly></td>
				<td class=title>销售渠道类型</td>
				<td class=input><input class="wid readonly" name=SaleChnl id=SaleChnl value="" readonly></td>
			</tr>
			<tr class=common>
				<td class=title>投保申请日期</td>
				<td class=input><Input class="wid readonly" name=ApplyDate id=ApplyDate value="" readonly></td>
				<td class=title>生效日期</td>
				<td class=input><div id="divVal" style="display: ''"><input class="coolDatePicker" dateFormat="short" verify="生效日期|DATE" name=ValDate elementtype=nacessary onClick="laydate({elem: '#ValDate'});" id="ValDate"><span class="icon"><a onClick="laydate({elem: '#ValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></div>
					<div id="divValS" style="display: ''">保费到账次日</div>
					</td>
				<td class=title>投保总人数</td>
				<td class=input><input class="wid readonly" name=AppntNum id=AppntNum value="" readonly></td>
			</tr>
			<tr class=common>
				<td class=title>应收总保费(元)</td>
				<td class=input><input class="wid readonly" name=ShouldPrem id=ShouldPrem value="" readonly></td>
				<td class=title>投保单号</td>
				<td class=input><input class="wid readonly" name=tGrpPropNo id=tGrpPropNo value="<%=tGrpPropNo%>" readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
	</div>
	<input class=cssButton value="保单明细" type=button onclick="policyQuery();">
	<input class=cssButton value="人员清单查询" type=button onclick="insuredQuery();">
	<input class=cssButton value="保险方案查询" type=button onclick="showPlanQuery();">
	<input class=cssButton value="人员信息分布" type=button onclick="insuredStatistic();">
	<input class=cssButton value="影像件查询" type=button onclick="queryScanPage();">
	<input class=cssButton value="问题件处理" type=button onclick="goToQuestion();">
	<input class=cssButton type=button value="附件管理" onclick="showAttachment();">
	<br>
	<br>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divContDeal);">
			</td>
			<td class=titleImg>团体保单核保信息</td>
		</tr>
	</table>
	<div id="divContDeal" class=maxbox1 style="display: ''">
		<input class=cssButton value="核保处理" type=button onclick="goToGErr();">
		<input class=cssButton value="理赔责任控制" type=button onclick="claimDutyControl();">
		<br/>
		<input class=cssButton value="整单核保确认" type=button onclick="uwConfirm();">
		<input class=cssButton value="返  回" type=button onclick="goBack();">
	</div>
	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden name=MissionID value="<%=tMissionID%>"> <!-- 工作任务ID -->
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>"> <!-- 子工作任务ID -->
	<input type=hidden name=ActivityID value="<%=tActivityID%>"> <!-- 工作节点ID -->
	<input type=hidden name=GrpPropNo value="<%=tGrpPropNo%>">
	<input type=hidden name=ContPlanType value="<%=tContPlanType%>">
	<input type=hidden name=ValDateType>
	<input type=hidden name=ReinsFlag value="0">

<br /><br /><br /><br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
