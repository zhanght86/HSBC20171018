<%
/***************************************************************
 * <p>ProName：LSQuotProjBasicInput.jsp</p>
 * <p>Title：项目询价基本信息录入</p>
 * <p>Description：项目询价基本信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-03-26
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
	var tOperator = "<%=tOperator%>";
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>项目询价录入</title>
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
	<script src="./LSQuotProjBasicInput.js"></script>
	<%@include file="./LSQuotProjBasicInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- 待录入询价查询条件 -->
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id=tab1 name=tab1>
		<ul>
		   <li id=liBasic name=liBasic onclick="goToStep(1)" class="now">1、基本信息</li>
		   <li id=liPlan name=liPlan onclick="goToStep(2)">2、方案信息</li>
		   <li id=liSubmit name=liSubmit onclick="goToStep(3)">3、询价提交</li>
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
		<div id=divInfo1 name=divInfo1 class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>询价号</td>
					<td class=input><input class="wid readonly" name=QuotNo id=QuotNo readonly></td>
					<td class=title>批次号</td>
					<td class=input><input class="wid readonly" name=QuotBatNo id=QuotBatNo readonly></td>
					<td class=title>项目名称</td>
					<td class=input><input class="wid common" name=ProjName id=ProjName verify="项目名称|NOTNULL&LEN<=60" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>目标客户</td>
					<td class=input><input class="wid common" name=TargetCust id=TargetCust verify="目标客户|NOTNULL&LEN<=10" elementtype=nacessary></td>
					<td class=title>被保险人数量</td>
					<td class=input><input class="wid common" name=NumPeople id=NumPeople verify="被保险人数量|NOTNULL&INT&VALUE>=0" elementtype=nacessary></td>
					<td class=title>业务规模(元)</td>
					<td class=input><input class="wid common" name=PrePrem id=PrePrem verify="业务规模(元)|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>预估赔付率(%)</td>
					<td class=input><input class="wid common" name=PreLossRatio id=PreLossRatio verify="预估赔付率(%)|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>
					<td class=title>合作方</td>
					<td class=input><input class="wid common" name=Partner id=Partner verify="合作方|NOTNULL&LEN<=100" elementtype=nacessary></td>
					<td class=title>有效止期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=ExpiryDate id=ExpiryDate verify="有效止期|NOTNULL&DATE" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>产品类型</td>
					<td class=input colspan=5><input class=codeno name=ProdType id=ProdType verify="产品类型|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('prodtype', [this,ProdTypeName], [0,1]);" onkeyup="return returnShowCodeListKey('prodtype', [this,ProdTypeName], [0,1]);" readonly><input class=codename name=ProdTypeName id=ProdTypeName elementtype=nacessary>
					<span style="color: red">（普通险种包含传统险及长期重疾险；建工险险种包含建工险及建工险可搭配的险种。录入方案信息后，产品类型不可更改。）</span></td>
				</tr>
				<tr class=common>
					<td class=title>是否为统括单</td>
					<td class=input><input class=codeno name=BlanketFlag id=BlanketFlag value="0" verify="是否为统括单|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('trueflag', [this, BlanketFlagName], [0, 1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this, BlanketFlagName], [0, 1], null, null, null, '1', null);"><input class=codename name=BlanketFlagName id=BlanketFlagName value="否"  elementtype=nacessary readonly></td>
					<td class=title id=td1 name=td1 style="display: none">是否为弹性计划</td>
					<td class=input id=td2 name=td2 style="display: none"><input class=codeno name=ElasticFlag id=ElasticFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('trueflag', [this,ElasticFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,ElasticFlagName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=ElasticFlagName id=ElasticFlagName readonly elementtype=nacessary></td>
					<td class=title id=td3 name=td3 style="display: ''"></td>
					<td class=input id=td4 name=td4 style="display: ''"></td>
					<td class=title id=td5 name=td5 style="display: ''"></td>
					<td class=input id=td6 name=td6 style="display: ''"></td>
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
					<td class=input colspan=5><textarea cols=80 rows=2 name=ProjDesc id=ProjDesc verify="项目说明|NOTNULL&LEN<=1500" elementtype=nacessary></textarea><input class=cssButton type=button value="附件上载" onclick="showAttachment();"></td>
				</tr>
			</table>
			<div class=common id=divPayIntvDiv name=divPayIntvDiv></div><!-- 缴费方式-->
			<table class=common>
				<tr class=common><!-- 适用机构编码-->
					<td class=title colspan=6>
						<div id=divAppOrgCode name=divAppOrgCode style="display: ''">
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
					<td class=input colspan=6><input class=checkbox type=checkbox name=LinkInquiryNo id=LinkInquiryNo onclick="showRelaQuot();">关联其他项目询价号既往信息</td>
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
	
			<input class=cssButton type=button value="基本信息保存" onclick="basicSubmit();">
		</div>
	</form>

	<form name=fmOther id=fmOther method=post action="" target=fraSubmit>
		<div name=otherDiv>
			<hr class="line"/>
			<center>
				<input class=cssButton type=button value="下一步" onclick="nextStep();">
				<input class=cssButton type=button value="回目录" onclick="ReturnList('01');">
				<input class=cssButton type=button id=enterQuest name=enterQuest value="问题件查询" onclick="goToQuestion('0');" style="display: none">
			</center>
		<div>
	</form>
</div>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate id=Operate>
		<input type=hidden name=HiddenCodeType id=HiddenCodeType>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
