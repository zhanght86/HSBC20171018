<%
/***************************************************************
 * <p>ProName：LSQuotETBasicInput.jsp</p>
 * <p>Title：一般询价基本信息录入</p>
 * <p>Description：一般询价基本信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
	String tOperator = tGI.Operator;
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tOperator = "<%=tOperator%>";//当前登录人
</script>
<html>
<head>
	<title>一般询价录入</title>
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
	<script src="./LSQuotETBasicInput.js"></script>
	<%@include file="./LSQuotETBasicInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- 待录入询价查询条件 -->
<form name=fm  id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
		   <li id=liBasic name=liBasic onclick="goToStep(1);" class="now">1、基本信息</li>
		   <li id=liPlan name=liPlan onclick="goToStep(2);">2、方案信息</li>
		   <li id=liSubmit name=liSubmit onclick="goToStep(3);">3、询价提交</li>
		   <!--<li onmouseover="setTabOver(1,3)" onmouseout="setTabOut(1,3)" onclick="showRateCount();" class="libutton">长期险测算</li>-->
		   <li onmouseover="setTabOver(1,4)" onmouseout="setTabOut(1,3)" onclick="showCoinsurance();" class="libutton">共保设置</li>
		   <li onmouseover="setTabOver(1,5)" onmouseout="setTabOut(1,4)" onclick="showAttachment();" class="libutton">附件管理</li>
		   <li onmouseover="setTabOver(1,6)" onmouseout="setTabOut(1,5)" onclick="showQuesnaire();" class="libutton">问卷调查</li>
		   <li onmouseover="setTabOver(1,7)" onmouseout="setTabOut(1,6)" onclick="showPast();" class="libutton">既往信息</li>
		   <li onmouseover="setTabOver(1,8)" onmouseout="setTabOut(1,7)" onclick="showFeeInfo();" class="libutton">费用信息</li>
		   <li onmouseover="setTabOver(1,9)" onmouseout="setTabOut(1,8)" onclick="showRequest();" class="libutton">业务需求</li>
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
		<div id="divInfo1" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>询价号</td>
					<td class=input><input class="wid readonly" name=QuotNo  id=QuotNo readonly></td>
					<td class=title>批次号</td>
					<td class=input><input class="wid readonly" name=QuotBatNo  id=QuotBatNo readonly></td>
					<td class=title>准客户名称</td>
					<td class=input><input class=codeno name=PreCustomerNo id=PreCustomerNo verify="准客户名称|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="selectPreCustomer(PreCustomerNo,PreCustomerName)" onkeyup="return returnShowCodeListKey('precustomerno',[this,PreCustomerName],[0,1]);" readonly><input class=codename name=PreCustomerName id=PreCustomerName onkeydown="fuzzyPreCustomerName(PreCustomerNo,PreCustomerName)" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>证件类型</td>
					<td class=input><input class="wid common" name=IDType id=IDType style="display: none"><input class="wid readonly" name=IDTypeName id=IDTypeName readonly></td>
					<td class=title>证件号码</td>
					<td class=input><input class="wid readonly" name=IDNo id=IDNo readonly></td>
					<td class=title>单位性质</td>
					<td class=input><input class="wid common" name=GrpNature id=GrpNature style="display: none"><input class="wid readonly" name=GrpNatureName  id=GrpNatureName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>行业分类</td>
					<td class=input><input class="wid common" name=BusiCategory id=BusiCategory style="display: none"><input class="wid readonly" name=BusiCategoryName id=BusiCategoryName readonly></td>
					<td class=title>地址</td>
					<td class=input colspan=3><input class="wid readonly" name=Address id=Address readonly></td>
				</tr>
				<tr class=common>
					<td class=title>产品类型</td>
					<td class=input colspan=5 nowrap><input class=codeno name=ProdType id=ProdType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('prodtype', [this,ProdTypeName], [0,1]);" onkeyup="return returnShowCodeListKey('prodtype', [this,ProdTypeName], [0,1]);" readonly><input class=codename name=ProdTypeName id=ProdTypeName readonly elementtype=nacessary>
					<span style="color: red">（普通险种包含传统险及长期重疾险；建工险包含建工险及可搭配险种；账户型险种包含医疗基金、万能险等。录入方案信息后，产品类型不可更改。）</span></td>
				</tr>
				<tr class=common>
					<td class=title>渠道类型</td>
					<td class=input><input class=codeno name=SaleChannel id=SaleChannel style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('salechannel', [this,SaleChannelName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('salechannel', [this,SaleChannelName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=SaleChannelName id=SaleChannelName readonly elementtype=nacessary></td>
					<td class=title>保费分摊方式</td>
					<td class=input><input class=codeno name=PremMode id=PremMode style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('premmode', [this,PremModeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('premmode', [this,PremModeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremModeName id=PremModeName readonly elementtype=nacessary></td>
					<td class=title>预计保费规模(元)</td>
					<td class=input><input class="wid common" name=PrePrem id=PrePrem verify="预计保费规模|NUM&VALUE>=0" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>是否为续保业务</td>
					<td class=input><input class=codeno name=RenewFlag id=RenewFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('trueflag', [this,RenewFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,RenewFlagName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=RenewFlagName id=RenewFlagName readonly elementtype=nacessary></td>
					<td class=title>是否为统括单</td>
					<td class=input><input class=codeno name=BlanketFlag id=BlanketFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('trueflag', [this,BlanketFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,BlanketFlagName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=BlanketFlagName id=BlanketFlagName readonly elementtype=nacessary></td>
					<td class=title id=td1 name=td1 style="display: none">是否为弹性计划</td>
					<td class=input id=td2 name=td2 style="display: none"><input class=codeno name=ElasticFlag id=ElasticFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center"ondblclick="return showCodeList('trueflag', [this,ElasticFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,ElasticFlagName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=ElasticFlagName  id=ElasticFlagName readonly elementtype=nacessary></td>
					<td class=title id=td3 name=td3></td>
					<td class=input id=td4 name=td4></td>
				</tr>
				<tr class=common>
					<td class=title>保险期限</td>
					<td class=input><input class="codename"  name=InsuPeriod id=InsuPeriod verify="保险期限|INT&value>0"><input type=hidden name=InsuPeriodFlag id=InsuPeriodFlag type><input class=codeno name=InsuPeriodFlagName id=InsuPeriodFlagName verify="保险期间单位|notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('insuperiodflag', [this,InsuPeriodFlag], [1,0], null, null, null, '1', null);" onkeyup="return showCodeListKey('insuperiodflag', [this,InsuPeriodFlag], [1,0], null, null, null, '1', null);" elementtype=nacessary readonly></td>
					<td class=title>契约生效日类型</td>
					<td class=input><input class=codeno name=ValDateType id=ValDateType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('valdatetype', [this,ValDateTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('valdatetype', [this,ValDateTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=ValDateTypeName id=ValDateTypeName readonly elementtype=nacessary></td>
					<td class=title id=tdValDate1 name=tdValDate1 style="display: none">生效日期</td>
					<td class=input id=tdValDate2 name=tdValDate2 style="display: none"><input class="coolDatePicker" dateFormat="short" name=AppointValDate  id=AppointValDate verify="预计起保日|date" elementtype=nacessary></td>
					<td class=title id=tdValDate3 name=tdValDate3></td>
					<td class=input id=tdValDate4 name=tdValDate4></td>
				</tr>
				<tr class=common>
					<td class=title>缴费方式</td>
					<td class=input><input class=codeno name=PayIntv id=PayIntv style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('payintv', [this,PayIntvName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('payintv', [this,PayIntvName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PayIntvName id=PayIntvName readonly elementtype=nacessary></td>
					<td class=title>是否共保</td>
					<td class=input><input class=codeno name=Coinsurance id=Coinsurance style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('trueflag', [this,CoinsuranceName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,CoinsuranceName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=CoinsuranceName  id=CoinsuranceName readonly elementtype=nacessary></td>
					<!--  td class=title><input class=cssButton type=button value="共保设置" onclick="showCoinsurance();"></td>
					<td class=input></td-->
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
					<td class=input colspan=5><textarea cols=80 rows=3 name=CustomerIntro id=CustomerIntro></textarea></td>
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
					<td class=title colspan=6><input class=checkbox type=checkbox name=RelaCustomerFlag  id=RelaCustomerFlag onclick="relaCustClick();">是否关联其他准客户</td>
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
			<input class=cssButton type=button value="基本信息保存" onclick="basicSubmit();">
		</div>
	</form>
	
	<form name=fmOther  id=fmOther method=post action="" target=fraSubmit>
		<div name=otherDiv>
			<hr class="line"/>
			<center>
				<input class=cssButton type=button value="下一步" onclick="nextStep();">
				<input class=cssButton type=button value="回目录" onclick="ReturnList('00');">
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
