<%
/***************************************************************
 * <p>ProName：LSQuotETSeeInput.jsp</p>
 * <p>Title：查看报价单(项目询价)</p>
 * <p>Description：查看报价单(项目询价)</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-19
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
	String tOfferListNo = request.getParameter("OfferListNo");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
	String tReturnFlag = request.getParameter("ReturnFlag");
	String tQuotQuery = request.getParameter("QuotQuery");
%>
<script>
	var tOfferListNo = "<%=tOfferListNo%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tOperator = "<%=tOperator%>";
	var tManageCom = "<%=tManageCom%>";
	var tCurrentDate = "<%=tCurrentDate%>";
	var tReturnFlag = "<%=tReturnFlag%>";
	var tTranProdType = "";//产品类型
	var tMissionID = "";
	var tSubMissionID = ""; 
	var tActivityID = "";//设置为空，只做最上边按钮展示时用
	var tQuotQuery = "<%=tQuotQuery%>";
</script>
<html>
<head>
	<title>查看报价单</title>
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
	<script src="./LSQuotProjSeeInput.js"></script>
	<%@include file="./LSQuotProjSeeInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
			<li id=id1 name=name1></li>
			<li id=id2 name=name2 ></li>
			<li id=id3 name=name3></li>
			<li onmouseover="setTabOver(1,3)" onmouseout="setTabOut(1,3)" onclick="showAttachment();" class="libutton">附件管理</li>
			<li onmouseover="setTabOver(1,4)" onmouseout="setTabOut(1,4)" onclick="showQuesnaire();" class="libutton">问卷调查</li>
			<li onmouseover="setTabOver(1,5)" onmouseout="setTabOut(1,5)" onclick="showPast();" class="libutton">既往信息</li>
			<li onmouseover="setTabOver(1,6)" onmouseout="setTabOut(1,6)" onclick="showFeeInfo();" class="libutton">费用信息</li>
			<li onmouseover="setTabOver(1,7)" onmouseout="setTabOut(1,7)" onclick="onlyShowRequest();" class="libutton">业务需求</li>
		</ul>
	</div>
</form>
<div class="tablist block">
	<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
		<!--询价基本信息 -->
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
					<td class=input><input class="wid readonly" name=QuotBatNo id=QuotBatNo readonly></td>
					<td class=title>项目名称</td>
					<td class=input><input class="wid readonly" name=ProjName id=ProjName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>目标客户</td>
					<td class=input><input class="wid readonly" name=TargetCust id=TargetCust readonly></td>
					<td class=title>被保险人数量</td>
					<td class=input><input class="wid readonly" name=NumPeopleA id=NumPeopleA readonly></td>
					<td class=title>业务规模(元)</td>
					<td class=input><input class="wid readonly" name=PrePrem id=PrePrem readonly></td>
				</tr>
				<tr class=common>
					<td class=title>预估赔付率(%)</td>
					<td class=input><input class="wid readonly" name=PreLossRatio id=PreLossRatio readonly></td>
					<td class=title>合作方</td>
					<td class=input><input class="wid readonly" name=Partner id=Partner readonly></td>
					<td class=title>有效止期</td>
					<td class=input><input class="wid readonly" name=ExpiryDate id=ExpiryDate readonly></td>
				</tr>
				<tr class=common>
					<td class=title>产品类型</td>
					<td class=input><input class="wid common" name=ProdType id=ProdType style="display: none"><input class="wid readonly" name=ProdTypeName id=ProdTypeName readonly></td>
					<td class=title colspan=4></td>
				</tr>
				<tr class=common>
					<td class=title>是否为统括单</td>
					<td class=input><input class="wid common" name=BlanketFlag id=BlanketFlag style="display: none"><input class="wid readonly" name=BlanketFlagName id=BlanketFlagName readonly></td>
					<td class=title id=tdElasticFlag1 name=tdElasticFlag1 style="display: none">是否为弹性计划</td>
					<td class=input id=tdElasticFlag2 name=tdElasticFlag2 style="display: none"><input class="wid readonly" name=ElasticFlag id=ElasticFlag type=hidden><input class="wid readonly" name=ElasticFlagName id=ElasticFlagName readonly></td>
					<td class=title id=tdElasticFlag3 name=tdElasticFlag3 style="display: ''"></td>
					<td class=input id=tdElasticFlag4 name=tdElasticFlag4 style="display: ''"></td>
					<td class=title id=tdElasticFlag5 name=tdElasticFlag5 style="display: ''"></td>
					<td class=input id=tdElasticFlag6 name=tdElasticFlag6 style="display: ''"></td>

				</tr>
			</table>
			<div class=common id=divPlanDiv><!-- 保障层级划分标准 -->
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
					<td class=title>项目说明</td>
					<td class=input colspan=5><textarea cols=80 rows=2 name=ProjDesc id=ProjDesc readonly></textarea></td>
				</tr>
			</table>
			<div class=common id=divPayIntvDiv></div><!-- 缴费方式-->
			<table class=common>
				<tr class=common><!-- 适用机构编码-->
					<td class=title colspan=6>
						<div id="divAppOrgCode" style="display: ''">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanAppOrgCodeGrid"></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
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
			<div class=common id=divSaleChnlDiv><!-- 销售渠道 -->
			</div>
			<table class=common>
				<tr class=common><!-- 中介机构名称-->
					<td class=title colspan=6>
						<div id="divAgencyInfo" style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanAgencyNameGrid"></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				
				<tr class=common>
					<td class=input colspan=6><input class=checkbox type=checkbox disabled name=LinkInquiryNo id=LinkInquiryNo onclick="showRelaQuot();">关联其他项目询价号既往信息</td>
				</tr>
				<tr class=common >
					<td class=title colspan=6><!-- 关联询价号-->
						<div  id="divLinkInquiryNo" style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanLinkInquiryNoGrid" ></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br/>
		
		<!-- 询价方案信息 -->
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
					<td class=title>方案描述</td>
					<td class=input><input class="wid readonly" name=PlanDesc id=PlanDesc></td>
					<td class=title>方案编码</td>
					<td class=input><input class="wid readonly" name=PlanCode id=PlanCode readonly></td>
					<td class=title style="display: none">系统方案编码</td>
					<td class=input style="display: none"><input class="wid readonly" name=SysPlanCode id=SysPlanCode readonly></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>保险期限</td>
					<td class=input><input class="wid readonly"  name=InsuPeriod id=InsuPeriod ><input type=hidden name=InsuPeriodFlag id=InsuPeriodFlag type><input class="wid readonly" name=InsuPeriodFlagName id=InsuPeriodFlagName></td>
					<td class=title id=tdPlan5 name=tdPlan5 style="display: none">方案类型</td>
					<td class=input id=tdPlan6 name=tdPlan6 style="display: none"><input class="wid readonly" name=PlanType id=PlanType style="display: none"><input class="wid readonly" name=PlanTypeName id=PlanTypeName></td>
					<td class=title id=tdPlan8 name=tdPlan8 style="display: none">方案标识</td>
					<td class=input id=tdPlan9 name=tdPlan9 style="display: none"><input class="wid readonly" name=PlanFlag id=PlanFlag style="display: none"><input class="wid readonly" name=PlanFlagName id=PlanFlagName></td>
				</tr>
                <tr class=common>
                    <td class=title id=tdPlan10 name=tdPlan10 style="display: none">保费计算方式</td>
					<td class=input id=tdPlan11 name=tdPlan11 style="display: none"><input class="wid readonly" name=PremCalType id=PremCalType style="display: none"><input class="wid readonly" name=PremCalTypeName id=PremCalTypeName></td>
					<td class=title id=tdPlan12 name=tdPlan12 style="display: none">方案人数</td>
					<td class=input id=tdPlan13 name=tdPlan13 style="display: none"><input class="wid readonly" name=PlanPeople id=PlanPeople></td>
					<td class=title id=tdPlan14 name=tdPlan14 style="display: none">最低工程造价(元)</td>
					<td class=input id=tdPlan15 name=tdPlan15 style="display: none"><input class="wid readonly" name=EnginCost id=EnginCost></td>
				</tr>
                <tr class=common>
                    <td class=title id=tdPlan16 name=tdPlan16 style="display: none">最低工程面积(平方米)</td>
					<td class=input id=tdPlan17 name=tdPlan17 style="display: none"><input class="wid readonly" name=EnginArea id=EnginArea></td>
					<td class=title id=tdPlan1 name=tdPlan1 style="display: ''"></td>
					<td class=input id=tdPlan2 name=tdPlan2 style="display: ''"></td>
					<td class=title id=tdPlan3 name=tdPlan3 style="display: ''"></td>
					<td class=input id=tdPlan4 name=tdPlan4 style="display: ''"></td>
				<tr>
				<tr class=common id=trPlan1 name=trPlan1 style="display: none">
					<td class=title>职业类型</td>
					<td class=input colspan=5><input class=radio type="radio" id=OccupTypeRadio1 name=OccupTypeRadio onclick="chooseOccupFlag('1');">单一职业类别<input class=radio type="radio" id=OccupTypeRadio2 name=OccupTypeRadio onclick="chooseOccupFlag('2');">多职业类别<input type=hidden name=OccupTypeFlag id=OccupTypeFlag></td>
				</tr>
				<tr class=common id=trOccupType1 name=trOccupType1>
					<td class=title>职业类别</td>
					<td class=input><input class="wid readonly" name=OccupType id=OccupType style="display: none"><input class="wid readonly" name=OccupTypeName id=OccupTypeName></td>
					<td class=title>职业中分类</td>
					<td class=input><input class="wid readonly" name=OccupMidType id=OccupMidType style="display: none"><input class="wid readonly" name=OccupMidTypeName id=OccupMidTypeName><span id=spanOccupMid name=spanOccupMid style="display: none;color: red"></span></td>
					<td class=title>工种</td>
					<td class=input><input class="wid readonly" name=OccupCode id=OccupCode style="display: none"><input class="wid readonly" name=OccupCodeName id=OccupCodeName><span id=spanOccupCode name=spanOccupCode style="display: none;color: red"></span></td>
				</tr>
				<tr class=common id=trOccupType2 name=trOccupType2>
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
				<tr class=common id=trPlan6 name=trPlan6 style="display: none">
					<td class=title id=tdEngin1 name=tdEngin1 style="display: none">最高工程造价(元)</td>
					<td class=input id=tdEngin2 name=tdEngin2 style="display: none"><input class="wid readonly" name=MaxEnginCost id=MaxEnginCost></td>
					<td class=title id=tdEngin3 name=tdEngin3 style="display: none">最高工程面积(平方米)</td>
					<td class=input id=tdEngin4 name=tdEngin4 style="display: none"><input class="wid readonly" name=MaxEnginArea id=MaxEnginArea></td>
					<td class=title>工程类型</td>
					<td class=input><input class="wid readonly" name=EnginType id=EnginType style="display: none"><input class="wid readonly" name=EnginTypeName id=EnginTypeName></td>
				</tr>
                <tr class=common>
                    <td class=title>施工天数</td>
					<td class=input><input class="wid readonly" name=EnginDays id=EnginDays></td>
					<td class=title id=tdEngin5 name=tdEngin5 style="display: ''"></td>
					<td class=input id=tdEngin6 name=tdEngin6 style="display: ''"></td>
				</tr>
			</table>
			<div id=divEnginFactor>
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
				<tr class=common id=trPlan7 name=trPlan7 style="display: none">
					<td class=title>工程概述</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=EnginDesc id=EnginDesc readonly></textarea></td>
				</tr>
				<tr class=common id=trEnginCondition name=trEnginCondition style="display: none">
					<td class=title>工程状况说明</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=EnginCondition id=EnginCondition readonly></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>其他说明</td>
					<td class=input colspan=5><textarea class=common cols=80 rows=2 name=OtherDesc id=OtherDesc readonly></textarea></td>
				</tr>
			</table>
			<input class=cssButton type=button value="关闭方案明细" onclick="closePlanDetail();">
		</div>
		<input class=cssButton type=button value="选择报价方案" onclick="selectQuotPlan();">
		<input class=cssButton type=button value="报价方案明细" onclick="offerPalnDetail();">
		<input class=cssButton type=button value="返  回" onclick="returnPrint();">
		
		<div id="divInfo3" style="display: ''">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUnderwriting);">
					</td>
					<td class=titleImg>核保处理</td>
				</tr>
			</table>
			<div id="divUnderwriting" class=maxbox1 style="display: ''">
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
		<input type=hidden name=Operate>
	</form>
</div>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
