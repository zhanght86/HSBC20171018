<%
/***************************************************************
 * <p>ProName：LCInsuredInfoInput.jsp</p>
 * <p>Title：人员清单维护明细</p>
 * <p>Description：人员清单维护明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tFlag = request.getParameter("Flag");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tContPlanType  = request.getParameter("ContPlanType");
	String tPolicyType  = request.getParameter("PolicyType");

%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tCurrentDate  = "<%=tCurrentDate%>";
	var tContPlanType  = "<%=tContPlanType%>";
	var tPolicyType  = "<%=tPolicyType%>";

</script>
<html>
<head >
	<title>人员清单维护</title>
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
	<script src="./LCInsuredInfoInput.js"></script>
	<script src="./LCContCommonInput.js"></script>
	<%@include file="./LCInsuredInfoInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LCinsuredInfoSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr>
				<td class=title>投保单号</td>
				<td class=input><input class="wid readonly" name=GrpConNo id=GrpConNo value="<%=tGrpPropNo%>" ></td>
				<td class=title>被保险人姓名</td>
				<td class=input><input class="wid common" name=CustomName id=CustomName></td>
				<td class=title>证件号码</td>
				<td class=input><input class="wid common" name=IDNO id=IDNO></td>
			</tr>
			<tr>
				<td class=title>被保险人编码</td>
				<td class=input><input class="wid common" name=CustomerID id=CustomerID></td>
				<td class=title>保险方案</td>
				<td class=input><Input class=codeno name=ContPlanCode id=ContPlanCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,SysPlanCode);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,SysPlanCode);"><input class=codename name=ContPlanCodeName ><input type=hidden name=SysPlanCode ></td>
				<td class=title>被保险人客户号</td>
				<td class=input><input class="wid common" name=Customerno id=Customerno ></td>
				</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="insuredQuery();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
			<td class=titleImg>被保险人信息</td>
		</tr>
	</table>
	<div id="divQueryInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQueryScanGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	<center>
		<div id="divInfoOper">
			<div id="divContPlanQuery1" style="display: ''; float:left;">
				<input class=cssButton id=qutButton type=button value="投保方案查询" onclick="showPlanQuery();">
			</div><div id="divContPlanQuery2" style="display: none; float:left;">
				<input class=cssButton id=qutButton type=button value="投保方案一览" onclick="showAllPlan();">
			</div>
			<div id="divButton" style="display: none;" >
				<input class=cssButton id=addButton type=button value="添加被保险人" onclick="goIntoInsuredList();">
				<input class=cssButton id=impMbutton type=button value="导入被保险人清单" onclick="showlmpInsuredList();">
				<input class=cssButton id=addButton type=button value="被保险人汇总信息" onclick="showQueryInsredList();">
				<!--<input class=cssButton id=choDeleteButton type=button value="选择删除" onclick="choseDelet();">-->
				<input class=cssButton id=conDeleteButton type=button value="条件删除" onclick="condDelete();">
				<input class=cssButton id=allDeletButton type=button value="全部删除" onclick="allDelete();">
			</div>
		</div>
	</center>

	<div id="divButton2" style="display: ''">
		<input class=cssButton id=addButton type=button value="被保人汇总查询" onclick="showQueryInsredList();">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	<div>
		<hr class=line />
		<input class=cssButton value="影像件查询" type=button onclick="queryScanPage();"></div>
	<div id="divUplond" style="display: none">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUplondInfo);">
			</td>
			<td class=titleImg>健康告知书附件上载</td>
		</tr>
	</table>
	<div id="divUplondInfo" class=maxbox1 style="display: ''">
		<input class=cssButton type=button value="健康告知书附件上载" onclick="showAttachment();">
	</div>
	<hr class=line />
	<div id="divConfime" align=left style="display: none" >
			<input class=cssButton  id=confirmButton type=button value="人员清单确认" onclick="saveClick();">
			<input class=cssButton type=button value="返  回" onclick="turnBack();">
			<input class=cssButton id=QuestionButton name=QuestionButton type=button value="问题件处理" onclick="goToQuestion();">
	</div>


	<div id="divConfimeC" align=left style="display: none">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>

</div>

	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden name=MissionID> <!-- 工作任务ID -->
	<input type=hidden name=SubMissionID> <!-- 子工作任务ID -->
	<input type=hidden name=ActivityID> <!-- 工作节点ID -->
	<input type=hidden name=GrpPropNo>
	<input type=hidden name=Flag>
	<input type=hidden name=ContPlanType>
	<input type=hidden name=CurrentDate>

</form>
<br /><br /><br /><br />
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
