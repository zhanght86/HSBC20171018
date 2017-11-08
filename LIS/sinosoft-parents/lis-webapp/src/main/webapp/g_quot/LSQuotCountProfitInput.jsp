<%
/***************************************************************
 * <p>ProName：LSQuotCountProfitInput.jsp</p>
 * <p>Title：收益测算</p>
 * <p>Description：收益测算</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-29
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
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
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
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>长期险测算</title>
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
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LSQuotCountProfitInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSQuotCountProfitInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
		   <li onclick="goToLongRiskStep(0)">费率测算</li>
		   <li onclick="goToLongRiskStep(1)" class="now">收益测算</li>
		</ul>
	</div>
</form>
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<div class="tablist block">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPolInfo);">
				</td>
				<td class=titleImg>保单信息</td>
			</tr>
		</table>
		<div id="divPolInfo" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>险种</td>
					<td class=input><input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="险种|NOTNULL" ondblclick="return returnShowCodeList('quotrisk',[this,RiskName],[0,1]);" onkeyup="return returnShowCodeListKey('quotrisk',[this,RiskName],[0,1]);"><input class=codename name=RiskName id=RiskName onkeydown="fuzzyRiskName(RiskCode,RiskName)" elementtype=nacessary></td>
					<td class=title>责任</td>
					<td class=input><input class=codeno name=DutyCode id=DutyCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="责任|NOTNULL" ondblclick="return returnShowCodeList('quotduty',[this,DutyName],[0,1]);" onkeyup="return returnShowCodeListKey('quotduty',[this,DutyName],[0,1]);"><input class=codename name=DutyName id=DutyName elementtype=nacessary></td>
					<td class=title>预计初始保费</td>
					<td class=input><input class="wid common" name=InitPrem id=InitPrem verify="预计初始保费|NOTNULL&NUM" elementtype=nacessary ></td>
				</tr>
				<tr class=common>
					<td class=title>管理费扣缴方式</td>
					<td class=input><input class=codeno name=MangFeeType id=MangFeeType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="管理费扣缴方式|NOTNULL" ondblclick="return returnShowCodeList('deducttype',[this,MangFeeTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('deducttype',[this,MangFeeTypeName],[0,1]);"><input class=codename name=MangFeeTypeName  id=MangFeeTypeName elementtype=nacessary></td>
					<td class=title>初始管理费(元)/比例</td>
					<td class=input><input class="wid common" name=InitMangFee id=InitMangFee verify="初始管理费(元)/比例|NOTNULL&NUM" elementtype=nacessary></td>
					<td class=title>预期收益率</td>
					<td class=input><input class="wid common" name=Profit id=Profit verify="预期收益率|NOTNULL&NUM" elementtype=nacessary ></td>
				</tr>
				<tr class=common>
					<td class=title>月度管理费类型</td>
					<td class=input><input class=codeno name=MonthFeeType  id=MonthFeeType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="月度管理费类型|NOTNULL" ondblclick="return returnShowCodeList('deducttype1',[this,MonthFeeTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('deducttype1',[this,MonthFeeTypeName],[0,1]);"><input class=codename name=MonthFeeTypeName id=MonthFeeTypeName readonly elementtype=nacessary></td>
					<td class=title>月度管理费(元)/比例</td>
					<td class=input><input class="wid common" name=MonthFee id=MonthFee verify="月度管理费(元)/比例|NOTNULL&NUM" elementtype=nacessary></td>
					<td class=title>预期投保年限</td>
					<td class=input><input class="wid common" name=Years id=Years verify="预期投保年限|NOTNULL&INT&VALUE>0&VALUE<=100" elementtype=nacessary></td>
				</tr>
			</table>
			<input class=cssButton type=button value="收益测算" name="ProfitClick" id=ProfitClick onclick="profitClick();">
		</div>
		<!-- 收益测算信息 -->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divProfitInfo);">
				</td>
				<td class=titleImg>收益测算信息</td>
			</tr>
		</table>
		<div id="divProfitInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanProfitInfoGrid" ></span>
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
		<hr class="line"/>
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
