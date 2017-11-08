<%
/***************************************************************
 * <p>ProName：LSQuotProjSubmitInput.jsp</p>
 * <p>Title：询价提交</p>
 * <p>Description：询价提交</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-25
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tTranProdType = "";//产品类型改为页面初始化
</script>
<html>
<head>
	<title>询价提交</title>
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
	<script src="./LSQuotProjSubmitInput.js"></script>
	<%@include file="./LSQuotProjSubmitInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- 待录入询价查询条件 -->
<form name=fm id=fm  method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
		   <li id=liBasic name=liBasic onclick="goToStep(1);" >1、基本信息</li>
		   <li id=liPlan name=liPlan onclick="goToStep(2);">2、方案信息</li>
		   <li id=liSubmit name=liSubmit onclick="goToStep(3);" class="now">3、询价提交</li>
		   <li onmouseover="setTabOver(1,3)" onmouseout="setTabOut(1,3)" onclick="showAttachment();" class="libutton">附件管理</li>
		   <li onmouseover="setTabOver(1,4)" onmouseout="setTabOut(1,4)" onclick="showQuesnaire();" class="libutton">问卷调查</li>
		   <li onmouseover="setTabOver(1,5)" onmouseout="setTabOut(1,5)" onclick="showPast();" class="libutton">既往信息</li>
		   <li onmouseover="setTabOver(1,6)" onmouseout="setTabOut(1,6)" onclick="showFeeInfo();" class="libutton">费用信息</li>
		   <li onmouseover="setTabOver(1,7)" onmouseout="setTabOut(1,7)" onclick="showRequest();" class="libutton">业务需求</li>
		</ul>
	</div>
</form>
<div class="tablist block">
	<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
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
					<td class=input><input class="wid readonly" name=NumPeople id=NumPeople readonly></td>
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
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<div class=common id=divPlanDiv name=divPlanDiv><!-- 保障层级划分标准 -->
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
			<div class=common id=divPayIntvDiv name=divPayIntvDiv></div><!-- 缴费方式-->
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
			<div class=common id=divSaleChnlDiv name=divSaleChnlDiv><!-- 销售渠道 -->
			</div>
			<table class=common>
				<tr class=common><!-- 中介机构名称-->
					<td class=title colspan=6>
						<div id=divAgencyInfo name=divAgencyInfo style="display: none">
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
						<div  id=divLinkInquiryNo name=divLinkInquiryNo style="display: none">
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
		<div id="divShowAllPlan">
		</div>
		<div id="divTurnPage" style="display: '';text-align:center;">
			<table align=center>
				<input class=cssButton90 type=button value="首  页" onclick="firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="lastPage();">
				<input class="wid readonly" name=PageInfo id=PageInfo readonly value="">
			</table>
		</div>
	</form>
	<form name=fmOther  id=fmOther method=post action="" target=fraSubmit>
		<div name=otherDiv>
			<hr class="line"/>
			<center>
				<input class=cssButton type=button value="上一步" onclick="previousStep();">
				<input class=cssButton type=button value="询价信息提交" onclick="quotInfoSubmit();">
				<input class=cssButton type=button value="回目录" onclick="ReturnList('01');">
				<input class=cssButton type=button id=enterQuest name=enterQuest value="问题件查询" onclick="goToQuestion('0');" style="display: none">
			</center>
		<div>
	</form>
</div>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate id=Operate>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
