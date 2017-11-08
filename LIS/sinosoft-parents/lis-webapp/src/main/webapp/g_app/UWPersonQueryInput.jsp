<%
/***************************************************************
 * <p>ProName：UWPersonQueryInput.jsp</p>
 * <p>Title：人员分部查询</p>
 * <p>Description：人员分部查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tFlag = request.getParameter("Flag");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";
	
</script>
<html>
<head >
	<title>人员分部</title>
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
	<script src="./UWPersonQueryInput.js"></script>
	<%@include file="./UWPersonQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	 <table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divYearDistribution);">
			</td>
			<td class=titleImg>人员信息分布</td>
		</tr>
	</table>
	<div  id= "divr1" class=maxbox1 style= "display: ''">
	<table class=common>
		<tr class=common>
			<td class=title>分布类别</td>
			<td class=input><input class=codeno name="branchSub" id=branchSub  value='0' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('distritype',[this,branchSubName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('distritype',[this,branchSubName],[0,1],null,null,null,1,null);" ><input class=codename name=branchSubName value='整单信息分布' readonly=true></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
	</table>
	</div>
	<div id= "divr2" style= "display: none">
		<table class=common>
			<tr class=common>
				<td class=title>保险方案</td>
				<td class=input><Input class="codeno" name="ContPlanCode" id=ContPlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode);"><input class=codename name=ContPlanCodeName elementtype=nacessary><input type=hidden name=sysPlanCode ></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
	</table>
	</div>
	<div id= "divr3" style= "display: none">
	<table class=common>
		<tr class=common>
			<td class=title>险种</td>
			<td class=input><Input class="codeno" name="RiskCode" id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showRiskCode(this,RiskCodeName);" onkeyup="showRiskCodeName(this,RiskCodeName);"><input class=codename name=RiskCodeName elementtype=nacessary></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
	</table>
	</div>
	<div id= "divYearDistribution" style= "display: ''">
		<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanYearDistributionGrid" ></span>
					</td>
				</tr>
		</table>
	<center style="display:none">
		<input value="首  页" class =cssButton90 TYPE=button onclick="turnPage1.firstPage();">
		<input value="上一页" class =cssButton91 TYPE=button onclick="turnPage1.previousPage();">
		<input value="下一页" class =cssButton92 TYPE=button onclick="turnPage1.nextPage();">
		<input value="尾  页" class =cssButton93 TYPE=button onclick="turnPage1.lastPage();">
	</center>
	 </Div>
	
	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpPropNo value="<%=tGrpPropNo%>">
	<br /><br /><br /><br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
