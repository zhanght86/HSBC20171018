<%
/***************************************************************
 * <p>ProName：LSQuotETDetailViewInput.jsp</p>
 * <p>Title：明细查看(一般询价)</p>
 * <p>Description：明细查看(一般询价)</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-22
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
	String tManageCom = tGI.ManageCom;
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
%>
<script>
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tTranProdType = "";//产品类型
	var tMissionID = "";
	var tSubMissionID = ""; 
	var tActivityID = "";//设置为空，不展示保存按钮
</script>
<html>
<head>
	<title>明细查看</title>
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
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotPubBasic.js"></script>
	<script src="./LSQuotPubPlan.js"></script>
	<script src="./LSQuotETDetailViewInput.js"></script>
	<%@include file="./LSQuotETDetailViewInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
			<li id=id1 name=name1></li>
			<li id=id2 name=name2 ></li>
			<li id=id3 name=name3></li>
			<li onmouseover="setTabOver(1,3)" onmouseout="setTabOut(1,3)" onclick="showRateCount();" class="libutton">长期险测算</li>
			<li onmouseover="setTabOver(1,4)" onmouseout="setTabOut(1,4)" onclick="showGrpSpec();" class="libutton">特别约定</li>
			<li onmouseover="setTabOver(1,5)" onmouseout="setTabOut(1,5)" onclick="showCoinsurance();" class="libutton">共保设置</li>
		   	<li onmouseover="setTabOver(1,6)" onmouseout="setTabOut(1,6)" onclick="showAttachment();" class="libutton">附件管理</li>
		   	<li onmouseover="setTabOver(1,7)" onmouseout="setTabOut(1,7)" onclick="showQuesnaire();" class="libutton">问卷调查</li>
		   	<li onmouseover="setTabOver(1,8)" onmouseout="setTabOut(1,8)" onclick="showPast();" class="libutton">既往信息</li>
		   	<li onmouseover="setTabOver(1,9)" onmouseout="setTabOut(1,9)" onclick="showFeeInfo();" class="libutton">费用信息</li>
		   	<li onmouseover="setTabOver(1,10)" onmouseout="setTabOut(1,10)" onclick="onlyShowRequest();" class="libutton">业务需求</li>
		</ul>
	</div>
</form>
<div class="tablist block">
	<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
		<!-- 一般询价基本信息 -->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
				</td>
				<td class=titleImg>询价基本信息</td>
			</tr>
		</table>
		<div id="divInfo1" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>询价号</td>
					<td class=input><input class="wid readonly" name=QuotNo id=QuotNo readonly></td>
					<td class=title>批次号</td>
					<td class=input><input class="wid readonly" name=QuotBatNo  id=QuotBatNo readonly></td>
					<td class=title>准客户名称</td>
					<td class=input><input class="wid common" name=PreCustomerNo  id=PreCustomerNo style="display: none"><input class="wid readonly" name=PreCustomerName id=PreCustomerName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>证件类型</td>
					<td class=input><input class="wid common" name=IDType id=IDType style="display: none"><input class="wid readonly" name=IDTypeName id=IDTypeName readonly></td>
					<td class=title>证件号码</td>
					<td class=input><input class="wid readonly" name=IDNo readonly></td>
					<td class=title>单位性质</td>
					<td class=input><input class="wid common" name=GrpNature id=GrpNature style="display: none"><input class="wid readonly" name=GrpNatureName id=GrpNatureName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>行业分类</td>
					<td class=input><input class="wid common" name=BusiCategory id=BusiCategory style="display: none"><input class="wid readonly" name=BusiCategoryName id=BusiCategoryName readonly></td>
					<td class=title>地址</td>
					<td class=input colspan=3><input class="wid readonly" name=Address id=Address readonly></td>
				</tr>
				<tr class=common>
					<td class=title>产品类型</td>
					<td class=input><input class="wid common" name=ProdType id=ProdType style="display: none"><input class="wid readonly" name=ProdTypeName id=ProdTypeName readonly></td>
					<td class=title colspan=4></td>
				</tr>
				<tr class=common>
					<td class=title>渠道类型</td>
					<td class=input><input class="wid common" name=SaleChannel id=SaleChannel style="display: none"><input class="wid readonly" name=SaleChannelName id=SaleChannelName readonly></td>
					<td class=title>保费分摊方式</td>
					<td class=input><input class="wid common" name=PremModeA id=PremModeA style="display: none"><input class="wid readonly" name=PremModeAName id=PremModeAName readonly></td>
					<td class=title>预计保费规模(元)</td>
					<td class=input><input class="wid readonly" name=PrePrem id=PrePrem ></td>
				</tr>
				<tr class=common>
					<td class=title>是否为续保业务</td>
					<td class=input><input class="wid common" name=RenewFlag id=RenewFlag style="display: none"><input class="wid readonly" name=RenewFlagName id=RenewFlagName readonly></td>
					<td class=title>是否为统括单</td>
					<td class=input><input class="wid common" name=BlanketFlag id=BlanketFlag style="display: none"><input class="wid readonly" name=BlanketFlagName id=BlanketFlagName readonly></td>
					<td class=title id=tdElasticFlag1 name=tdElasticFlag1 style="display: none">是否为弹性计划</td>
					<td class=input id=tdElasticFlag2 name=tdElasticFlag2 style="display: none"><input class="wid readonly" name=ElasticFlag id=ElasticFlag type=hidden><input class="wid readonly" name=ElasticFlagName id=ElasticFlagName readonly></td>
					<td class=title id=tdElasticFlag3 name=tdElasticFlag3></td>
					<td class=input id=tdElasticFlag4 name=tdElasticFlag4></td>
				</tr>
				<tr class=common>
					<td class=title>保险期限</td>
					<td class=input><input class="codeno" readonly  name=InsuPeriod id=InsuPeriod><input type=hidden name=InsuPeriodFlag id=InsuPeriodFlag type><input class="codename" readonly name=InsuPeriodFlagName id=InsuPeriodFlagName readonly></td>
					<td class=title>契约生效日类型</td>
					<td class=input><input class="wid common" name=ValDateType id=ValDateType style="display: none"><input class="wid readonly" name=ValDateTypeName id=ValDateTypeName readonly></td>
					<td class=title id=tdValDate1 name=tdValDate1 style="display: none">生效日期</td>
					<td class=input id=tdValDate2 name=tdValDate2 style="display: none"><input class="wid readonly" name=AppointValDate id=AppointValDate></td>
					<td class=title id=tdValDate3 name=tdValDate3></td>
					<td class=input id=tdValDate4 name=tdValDate4></td>
				</tr>
				<tr class=common>
					<td class=title>缴费方式</td>
					<td class=input><input class="wid common" name=PayIntv  id=PayIntv style="display: none"><input class="wid readonly" name=PayIntvName id=PayIntvName readonly></td>
					<td class=title>是否共保</td>
					<td class=input><input class="wid common" name=Coinsurance id=Coinsurance style="display: none"><input class="wid readonly" name=CoinsuranceName id=CoinsuranceName readonly></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<div class=common id=divPlanDiv>
			</div>
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
					<td class=title>公司简介</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=CustomerIntro  id=CustomerIntro readonly></textarea></td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divAgencyInfo" name="divAgencyInfo" style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanAgencyListGrid" ></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr class=common>
					<td class=title colspan=6><input class=checkbox type=checkbox disabled name=RelaCustomerFlag id=RelaCustomerFlag>是否关联其他准客户</td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divRelaCustInfo" style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanRelaCustListGrid"></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<div id=divEngin name=divEngin style="display: none">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEnginInfo);">
					</td>
					<td class=titleImg>工程信息</td>
				</tr>
			</table>
			<div id="divEnginInfo" class=maxbox1>
				<table class=common>
					<tr class=common>
						<td class=title>工程名称</td>
						<td class=input colspan=5><input class="wid readonly" name=EnginName id=EnginName style="width: 500px"></td>
					</tr>
					<tr class=common>
						<td class=title>工程类型</td>
						<td class=input><input class="wid readonly" name=EnginType id=EnginType style="display: none"><input class="wid readonly" name=EnginTypeName id=EnginTypeName readonly></td>
						<td class=title>工程面积(平方米)</td>
						<td class=input><input class="wid readonly" name=EnginArea id=EnginArea readonly></td>
						<td class=title>工程造价(元)</td>
						<td class=input><input class="wid readonly" name=EnginCost id=EnginCost readonly></td>
					</tr>
				</table>
				<div id=divEnginFactor name=divEnginFactor>
				</div>
				<table class=common>
					<tr class=common>
						<td class=title>工程地点</td>
						<td class=input><input class="wid readonly" name=EnginPlace id=EnginPlace readonly></td>
						<td class=title>工程起期</td>
						<td class=input><input class="wid readonly" name=EnginStartDate id=EnginStartDate readonly></td>
						<td class=title>工程止期</td>
						<td class=input><input class="wid readonly" name=EnginEndDate id=EnginEndDate readonly></td>
					</tr>
					<tr class=common>
						<td class=title>工程概述</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=EnginDesc  id=EnginDesc readonly></textarea></td>
					</tr>
					<tr class=common id=trEnginCondition name=trEnginCondition style="display: none">
						<td class=title>工程状况说明</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=EnginCondition id=EnginCondition readonly></textarea></td>
					</tr>
					<tr class=common>
						<td class=title>备注</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=Remark id=Remark readonly></textarea></td>
					</tr>
					<tr class=common>
						<td class=title>承包方名称</td>
						<td class=input><input class="wid readonly" name=InsurerName id=InsurerName></td>
						<td class=title>承包方资质</td>
						<td class=input><input class="wid readonly" name=InsurerType id=InsurerType style="display: none"><input class="wid readonly" name=InsurerTypeName id=InsurerTypeName readonly></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
					<tr class=common>
						<td class=title>施工方名称</td>
						<td class=input><input class="wid readonly" name=ContractorName id=ContractorName readonly></td>
						<td class=title>施工方资质</td>
						<td class=input><input class="wid readonly" name=ContractorType  id=ContractorType style="display: none"><input class="wid readonly" name=ContractorTypeName id=ContractorTypeName readonly></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
				</table>
				<br/>
			</div> 
		</div>
		<div id="divPlanInfo" style="display: ''">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQuotPlan);">
					</td>
					<td class=titleImg>询价方案信息</td>
				</tr>
			</table>
			<div id="divQuotPlan" class=maxbox1 style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanPlanInfoGrid" ></span>
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
		</div>
		<!-- 方案明细信息 -->
		<div id="divInfo2" style="display: none">
			<table class=common>
				<tr class=common>
					<td class=title>方案编码</td>
					<td class=input><input class="wid readonly" name=PlanCode id=PlanCode readonly></td>
					<td class=title style="display: none">系统方案编码</td>
					<td class=input style="display: none"><input class="wid readonly" name=SysPlanCode id=SysPlanCode readonly></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>方案描述</td>
					<td class=input><input class="wid readonly" name=PlanDesc id=PlanDesc></td>
					<td class=title id=tdPlan5 name=tdPlan5 style="display: none">方案类型</td>
					<td class=input id=tdPlan6 name=tdPlan6 style="display: none"><input class="wid readonly" name=PlanType id=PlanType style="display: none"><input class="wid readonly" name=PlanTypeName></td>
					<td class=title id=tdPlan8 name=tdPlan8 style="display: none">方案标识</td>
					<td class=input id=tdPlan9 name=tdPlan9 style="display: none"><input class="wid readonly" name=PlanFlag id=PlanFlag style="display: none"><input class="wid readonly" name=PlanFlagName></td>
				</tr>
                <tr class=common>
                    <td class=title id=tdPlan10 name=tdPlan10 style="display: none">保费计算方式</td>
					<td class=input id=tdPlan11 name=tdPlan11 style="display: none"><input class="wid readonly" name=PremCalType id=PremCalType style="display: none"><input class="wid readonly" name=PremCalTypeName></td>
					<td class=title id=tdPlan12 name=tdPlan12 style="display: none">方案人数</td>
					<td class=input id=tdPlan13 name=tdPlan13 style="display: none"><input class="wid readonly" name=PlanPeople id=PlanPeople></td>
					<td class=title id=tdPlan1 name=tdPlan1 style="display: none"></td>
					<td class=input id=tdPlan2 name=tdPlan2 style="display: none"></td>
					<td class=title id=tdPlan3 name=tdPlan3 style="display: none"></td>
					<td class=input id=tdPlan4 name=tdPlan4 style="display: none"></td>
				<tr>
				<tr class=common id=trPlan1 name=trPlan1 style="display: none">
					<td class=title>职业类型</td>
					<td class=input colspan=5><input class=radio type="radio" id=OccupTypeRadio1 name=OccupTypeRadio onclick="chooseOccupFlag('1');">单一职业类别<input class=radio type="radio" id=OccupTypeRadio2 name=OccupTypeRadio onclick="chooseOccupFlag('2');">多职业类别<input type=hidden name=OccupTypeFlag id=OccupTypeFlag></td>
				</tr>
				<tr class=common id=trOccupType1 name=trOccupType1 style="display: none">
					<td class=title>职业类别</td>
					<td class=input><input class="wid readonly" name=OccupType id=OccupType style="display: none"><input class="wid readonly" name=OccupTypeName id=OccupTypeName></td>
					<td class=title>职业中分类</td>
					<td class=input><input class="wid readonly" name=OccupMidType id=OccupMidType style="display: none"><input class="wid readonly" name=OccupMidTypeName id=OccupMidTypeName><span id=spanOccupMid name=spanOccupMid id=spanOccupMid style="display: none;color: red"></span></td>
					<td class=title>工种</td>
					<td class=input><input class="wid readonly" name=OccupCode id=OccupCode style="display: none"><input class="wid readonly" name=OccupCodeName id=OccupCodeName><span id=spanOccupCode name=spanOccupCode style="display: none;color: red"></span></td>
				</tr>
				<tr class=common id=trOccupType2 name=trOccupType2 style="display: none">
					<td class=title>最低职业类别</td>
					<td class=input><input class="wid readonly" name=MinOccupType id=MinOccupType style="display: none"><input class="wid readonly" name=MinOccupTypeName id=MinOccupTypeName></td>
					<td class=title>最高职业类别</td>
					<td class=input><input class="wid readonly" name=MaxOccupType id=MaxOccupType style="display: none"><input class="wid readonly" name=MaxOccupTypeName id=MaxOccupTypeName></td>
					<td class=title>职业比例</td>
					<td class=input><input class="wid readonly" name=OccupRate id=OccupRate></td>
				</tr>
				<tr class=common id=trPlan2 name=trPlan2 style="display: none">
					<td class=title>最低年龄(岁)</td>
					<td class=input><input class="wid readonly" name=MinAge id=MinAge></td>
					<td class=title>最高年龄(岁)</td>
					<td class=input><input class="wid readonly" name=MaxAge id=MaxAge></td>
					<td class=title>平均年龄(岁)</td>
					<td class=input><input class="wid readonly" name=AvgAge id=AvgAge></td>
				</tr>
				<tr class=common id=trPlan3 name=trPlan3 style="display: none">
					<td class=title>人数</td>
					<td class=input><input class="wid readonly" name=NumPeople id=NumPeople></td>
					<td class=title>参加社保占比</td>
					<td class=input><input class="wid readonly" name=SocialInsuRate id=SocialInsuRate></td>
					<td class=title>男女比例</td>
					<td class=input><input class="wid readonly" name=MaleRate id=MaleRate style="display: none"><input class="wid readonly" name=FemaleRate id=FemaleRate style="display: none"><input class="wid readonly" name=SexRate id=SexRate></td>
				</tr>
				<tr class=common id=trPlan4 name=trPlan4 style="display: none">
					<td class=title>退休占比</td>
					<td class=input><input class="wid readonly" name=RetireRate id=RetireRate></td>
					<td class=title>保费分摊方式</td>
					<td class=input><input class="wid readonly" name=PremMode id=PremMode style="display: none"><input class="wid readonly" name=PremModeName id=PremModeName></td>
					<td class=title>企业负担占比</td>
					<td class=input><input class="wid readonly" name=EnterpriseRate id=EnterpriseRate></td>
				</tr>
				<tr class=common id=trPlan5 name=trPlan5 style="display: none">
					<td class=title>最低月薪(元)</td>
					<td class=input><input class="wid readonly" name=MinSalary id=MinSalary></td>
					<td class=title>最高月薪(元)</td>
					<td class=input><input class="wid readonly" name=MaxSalary id=MaxSalary></td>
					<td class=title>平均月薪(元)</td>
					<td class=input><input class="wid readonly" name=AvgSalary id=AvgSalary></td>
				</tr>
				<tr class=common>
					<td class=title>其他说明</td>
					<td class=input colspan=11><textarea class="wid readonly" cols=70 rows=2 name=OtherDesc id=OtherDesc readonly></textarea></td>
				</tr>
			</table>
			<input class=cssButton type=button value="关闭方案明细" onclick="closePlanDetail();">
		</div>
		<input class=cssButton type=button name=PlanDetailButton id=PlanDetailButton value="方案明细查看" onclick="planDetailOpen();">
		<input class=cssButton type=button value="方案明细一览" onclick="showAllDetail();">
		<input class=cssButton type=button id=productButton name=productButton value="产品参数信息" onclick="showProdParam();">
		
		<div id="divUWRule" style="display: ''">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWRuleInfo);">
					</td>
					<td class=titleImg>核保规则</td>
				</tr>
			</table>
			<div id="divUWRuleInfo" class=maxbox1 style="display: ''">
				<input class=cssButton type=button value="核保/保全规则" onclick="showUWRule();">
				<input class=cssButton type=button value="理赔责任控制" onclick="showCMRule();">
				<input class=cssButton type=button value="方案组合配置" onclick="showPlanCombi();">
				<input class=cssButton type=button value="责任拓展" onclick="showExpand();">
			</div>
		</div>
		
		<div id="divInfo3" style="display: ''">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUnderwriting);">
					</td>
					<td class=titleImg>核保处理</td>
				</tr>
			</table>
			<div id="divUnderwriting" class=maxbox style="display: ''">
				<table class=common>
					<tr class=common>
						<td class=title>最终核保意见</td>
						<td class=input colspan=11><textarea class=readonly cols=70 rows=2 name=FinalOpinion id=FinalOpinion readonly></textarea></td>
					</tr>
					<tr class=common>
						<td class=title>最终核保结论</td>
						<td class=input><input class="wid readonly" name=FinalConclu id=FinalConclu style="display: none"><input class="wid readonly" name=FinalConcluName id=FinalConcluName></td>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
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
		</div>
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
		<input type=hidden name=Operate id=Operate>
	</form>
</div>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
