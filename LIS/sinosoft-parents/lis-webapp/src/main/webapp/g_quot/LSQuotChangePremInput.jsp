<%
/***************************************************************
 * <p>ProName：LSQuotChangePremInput.jsp</p>
 * <p>Title：报价单打印--变更保费</p>
 * <p>Description：报价单打印--变更保费</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-20
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
	String tOfferListNo = request.getParameter("OfferListNo");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tSysPlanCode = request.getParameter("SysPlanCode");
	String tPlanCode = request.getParameter("PlanCode");
	String tPrintState = request.getParameter("PrintState");
	String tQuotQuery = request.getParameter("QuotQuery");
%>
<script>
	var tOfferListNo = "<%=tOfferListNo%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tSysPlanCode = "<%=tSysPlanCode%>";
	var tPlanCode = "<%=tPlanCode%>";
	var tOperator = "<%=tOperator%>";
	var tQuotType = "";
	var tTranProdType = "";
	var tPrintState = "<%=tPrintState%>";
	var tQuotQuery = "<%=tQuotQuery%>";
</script>
<html>
<head >
	<title>变更保费</title>
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
	<script src="./LSQuotChangePremInput.js"></script>
	<%@include file="./LSQuotChangePremInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlan);">
			</td>
			<td class=titleImg>询价方案明细</td>
		</tr>
	</table>
	
	<div id="divPlan"  style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPlanDetailInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
		<div id="divOfferPlanDetail" style="display: none">
			<div id="divPlan1" style="display: ''">
				<table class=common>
					<tr class=common style="display: none">
						<td class=title colspan=6><input type=hidden name=SysPlanCode id=SysPlanCode><input class="wid common" name=PlanType id=PlanType><input class="wid common" name=PremCalType id=PremCalType><input class="wid common" name=PlanFlag id=PlanFlag><input class="wid common" name=OccupTypeFlag id=OccupTypeFlag><input class="wid common" name=OldPlanType id=OldPlanType><input class="wid common" name=OldPremCalType id=OldPremCalType><input class="wid common" name=OldOccupTypeFlag id=OldOccupTypeFlag></td>
					</tr>
					<tr class=common>
						<td class=title>方案编码</td>
						<td class=input><input class="wid readonly" name=PlanCode id=PlanCode readonly style="width:90px"><input class="wid readonly" style="width:20px" value = "-- "><input class="wid readonly" name=PlanDesc id=PlanDesc style="width:130px" readonly></td>
						<td class=title>险种</td>
						<td class=input><input class="wid readonly" name=RiskCode id=RiskCode readonly style="display: none"><input class="wid readonly" name=RiskName id=RiskName readonly></td>
						<td class=title>责任</td>
						<td class=input><input class="wid readonly" name=DutyCode id=DutyCode readonly style="display: none"><input class="wid readonly" name=DutyName id=DutyName readonly></td>
					</tr>
				</table>
			</div>
			<!--责任要素动态域3-->
			<div id="divDutyFactor">
				<table class=common>
					<tr class=common>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
				</table>
			</div>
			<!--险种责任录入固定域-->
			<table class=common>
				<tr class=common id=idAmnt name=idAmnt style="display: none">
					<td class=title>保额类型</td>
					<td class=input><input class=codeno name=AmntType id=AmntType style="display: none"><input class="wid readonly" name=AmntTypeName id=AmntTypeName readonly></td>
					<td class=title id=idFixedAmnt00 name=idFixedAmnt00 style="display: none">固定保额(元)</td>
					<td class=input id=idFixedAmnt01 name=idFixedAmnt01 style="display: none"><input class="wid readonly" name=FixedAmnt id=FixedAmnt readonly></td>
					<td class=title id=idSalaryMult00 name=idSalaryMult00 style="display: none">月薪倍数</td>
					<td class=input id=idSalaryMult01 name=idSalaryMult01 style="display: none"><input class="wid readonly" name=SalaryMult id=SalaryMult readonly></td>
				</tr>
				<tr class=common>
					<td class=title id=idMinAmnt00 name=idMinAmnt00 style="display: none">最低保额(元)</td>
					<td class=input id=idMinAmnt01 name=idMinAmnt01 style="display: none"><input class="wid readonly" name=MinAmnt id=MinAmnt readonly></td>
					<td class=title id=idMaxAmnt00 name=idMaxAmnt00 style="display: none">最高保额(元)</td>
					<td class=input id=idMaxAmnt01 name=idMaxAmnt01 style="display: none"><input class="wid readonly" name=MaxAmnt id=MaxAmnt readonly></td>
					<td class=title id=idAmnt00 name=idAmnt00 style="display: ''"></td>
					<td class=input id=idAmnt01 name=idAmnt01 style="display: ''"></td>
					<td class=title id=idAmnt02 name=idAmnt02 style="display: ''"></td>
					<td class=input id=idAmnt03 name=idAmnt03 style="display: ''"></td>
				</tr>
				<tr class=common id=idPrem name=idPrem style="display: none">
					<td class=title>期望保费类型</td>
					<td class=input><input class=codeno name=ExceptPremType id=ExceptPremType style="display: none"><input class="wid readonly" name=ExceptPremTypeName id=ExceptPremTypeName readonly></td>
					<td class=title>期望保费(元)/期望费率/费率折扣</td>
					<td class=input colspan=3><input class="wid readonly" name=ExceptPrem id=ExceptPrem readonly><input class=cssButton type=button id=tryCalButton name=tryCalButton style="display: none" value="试  算" onclick="tryCal();"></td>
				</tr>
				<tr class=common id=idFeeRate name=idFeeRate style="display: none">
					<td class=title>初始保费</td>
					<td class=input><input class="wid readonly" name=InitPrem id=InitPrem></td>
					<td class=title>期望收益率</td>
					<td class=input><input class="wid readonly" name=ExceptYield id=ExceptYield></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common id=trRelation name=trRelation style="display: none">
					<td class=input colspan=6><input class=checkbox type=checkbox id=SetRelation name=SetRelation onclick="setRelationFlag();">主附共用配置<input type=hidden name=RelaShareFlag id=RelaShareFlag value="0"></td>
				</tr>
				<tr class=common id=trRelationRate name=trRelationRate style="display: none">
					<td class=title style="display: none">与主被保险人关系</td>
					<td class=input style="display: none"><input class="wid readonly" name=RelaToMain id=RelaToMain style="display: none"><input class="wid readonly" name=RelaToMainName id=RelaToMainName></td>
					<td class=title>主被保险人保额占比</td>
					<td class=input><input class="wid readonly" name=MainAmntRate id=MainAmntRate></td>
					<td class=title>附属被保人保额占比</td>
					<td class=input><input class="wid readonly" name=RelaAmntRate id=RelaAmntRate></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<div id="divDutyFactorRelation" style="display: none">
				<table class=common>
					<tr class=common>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
				</table>
			</div>
			<div id="divRemark" style="display: none">
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
						<td class=title colspan=6>备注</td>
					</tr>
					<tr class=common>
						<td class=title colspan=6><textarea cols=80 rows=3 name=Remark id=Remark></textarea><span style="color: red">(如有对整单的业务需求，请到业务需求处进行录入)</span></td>
					</tr>
				</table>
			</div>
		
			<div id="divChange" style="display: ''">
				<table class=common>
					<tr class=common>
						<td class=title>变更类型</td>
						<td class=input><input class=codeno name=ChangeType id=ChangeType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return selectChangeType(this,ChangeTypeName,ExceptPremType,AmntType);" onkeyup="return showCodeListKey('changetype', [this,ChangeTypeName], [0,1], null, null, null, '1', null);"  readonly><input class=codename name=ChangeTypeName id=ChangeTypeName elementtype=nacessary></td>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
					<tr class=common id=idAmntChange name=idAmntChange style="display: none">
						<td class=title>保额类型</td>
						<td class=input><input class=codeno name=AmntTypeChange id=AmntTypeChange style="display: none"><input class="wid readonly" name=AmntTypeChangeName id=AmntTypeChangeName></td>
						<td class=title id=idFixedAmntChange00 name=idFixedAmntChange00 style="display: none">变更后固定保额(元)</td>
						<td class=input id=idFixedAmntChange01 name=idFixedAmntChange01 style="display: none"><input class="wid common" name=FixedAmntChange id=FixedAmntChange elementtype=nacessary></td>
						<td class=title id=idSalaryMultChange00 name=idSalaryMultChange00 style="display: none">变更后月薪倍数</td>
						<td class=input id=idSalaryMultChange01 name=idSalaryMultChange01 style="display: none"><input class="wid common" name=SalaryMultChange id=SalaryMultChange elementtype=nacessary></td>
					</tr>
					<tr class=common>
						<td class=title id=idMinAmntChange00 name=idMinAmntChange00 style="display: none">变更后最低保额(元)</td>
						<td class=input id=idMinAmntChange01 name=idMinAmntChange01 style="display: none"><input class="wid common" name=MinAmntChange id=MinAmntChange elementtype=nacessary></td>
						<td class=title id=idMaxAmntChange00 name=idMaxAmntChange00 style="display: none">变更后最高保额(元)</td>
						<td class=input id=idMaxAmntChange01 name=idMaxAmntChange01 style="display: none"><input class="wid common" name=MaxAmntChange id=MaxAmntChange elementtype=nacessary></td>
						<td class=title id=idAmntChange00 name=idAmntChange00 style="display: ''"></td>
						<td class=input id=idAmntChange01 name=idAmntChange01 style="display: ''"></td>
						<td class=title id=idAmntChange02 name=idAmntChange02 style="display: ''"></td>
						<td class=input id=idAmntChange03 name=idAmntChange03 style="display: ''"></td>
					</tr>
					<tr class=common id="id2" style="display: none">
						<td class=title>期望保费类型</td>
						<td class=input><input class="wid readonly" type=hidden name=ExceptPremTypeChangeCode id=ExceptPremTypeChangeCode ><input class="wid readonly" name=ExceptPremTypeChange id=ExceptPremTypeChange></td>
						<td class=title>变更后期望保费(元)/期望费率/费率折扣</td>
						<td class=input><input class="wid common" name=ExceptPremChange id=ExceptPremChange elementtype=nacessary></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
					<tr class=common id="id3" style="display: none">
						<td class=title>期望保费类型</td>
						<td class=input><input class="wid readonly" type=hidden name=ExceptPremTypeChangeCode1 id=ExceptPremTypeChangeCode1 ><input class="wid readonly" name=ExceptPremTypeChange1 id=ExceptPremTypeChange1></td>
						<td class=title>变更后固定保额(元)</td>
						<td class=input><input class="wid common" name=FixedAmntChange1 id=FixedAmntChange1 elementtype=nacessary></td>
						<td class=title>变更后期望保费(元)</td>
						<td class=input><input class="wid common" name=FixedPremChange1 id=FixedPremChange1 elementtype=nacessary></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<input class=cssButton type=button value="保  存" name="SaveButton" onclick="saveClick();">
	<input class=cssButton type=button value="关  闭" onclick="top.close();">
	
	<div id="divReceivPrem" style="display: none">
		<hr class="line"/>
		<table class=common>
			<tr class=common>
				<td class=title>应收保费</td>
				<td class=input><input class="wid common" name=ReceivPrem id=ReceivPrem elementtype=nacessary>&nbsp;<input class=cssButton type=button value="试  算" onclick="receivPremCal();"></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="保  存" name="PremButton" onclick="saveReceivPrem();">
	</div>
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
