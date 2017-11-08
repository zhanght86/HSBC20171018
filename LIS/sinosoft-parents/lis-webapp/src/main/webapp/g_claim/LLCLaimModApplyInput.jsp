<%
/***************************************************************
 * <p>ProName：LLClaimModApplyInput.jsp</p>
 * <p>Title：保项修改分公司申请申请界面</p>
 * <p>Description：保项修改分公司申请保项修改申请界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 王超
 * @version  : 8.0
 * @date     : 2015-03-11
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String mManageCom = tGI.ManageCom;
	String mOperator = tGI.Operator;
	String mCurrentDate = PubFun.getCurrentDate();
	
%>
<script>
	var mManageCom = "<%=mManageCom%>"; //记录登陆机构
	var mOperator = "<%=mOperator%>";  //记录操作人
	var mCurrentDate = "<%=mCurrentDate%>";
</script>
<html>
<head>
	<title>保项修改申请界面</title>
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
	<script src="LLCLaimModApplyInput.js"></script>
	<%@include file="LLCLaimModApplyInit.jsp"%>

</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimModApplySave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>保项修改查询条件</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comgrade=#02# and comcode like #'+ mManageCom +'%%#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comcode=#02#','1',1);"><input class=codename name=ManageComName></td>
                <td class=title>规则类型</td>
				<td class=input><input class=codeno name=QueryRuleType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmruletype',[this,QueryRuleTypeName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('clmruletype',[this,QueryRuleTypeName],[0,1],null,null,null,1);"><input class=codename name=QueryRuleTypeName></td>
				<td class=title>险种</td>
				<td class=input><input class=codeno name=QueryRiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('quotrisk',[this,QueryRiskCodeName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('quotrisk',[this,QueryRiskCodeName],[0,1],null,null,null,1);"><input class=codename name=QueryRiskCodeName></td>
			</tr>
			<tr class=common>
				<td class=title>保项修改生效起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate dateFormat="short"></td>
				<td class=title>保项修改生效止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate dateFormat="short"></td>
				<td class=title>状态</td>
				<td class=input><input class=codeno name=ClmmodifyState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmmodifystate',[this,clmmodifystateName1],[0,1],null,null,null,1);" onkeyup="showCodeListKey('clmmodifystate',[this,clmmodifystateName1],[0,1],null,null,null,1);"><input class=codename name=clmmodifystateName1></td>				
			</tr>
			<tr class=common>
				<td class=title>保项修改原因</td>
				<td class=input><input class=codeno name=QueryReasonType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmreasontype',[this,QueryReasonTypeName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('clmreasontype',[this,QueryReasonTypeName],[0,1],null,null,null,1);"><input class=codename name=QueryReasonTypeName></td>
                <td class=title>保单号</td>
				<td class=input><input class="wid common" name=QueryGrpContNo>
			    <td class=title></td>
				<td class=input></td>			
			</tr>
		</table>
		<input class=cssButton type=button name=querybutton value="查  询" onclick="queryClick();">		
	</div>
	
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divModItemGrid);">
			</td>
			<td class=titleImg>保项修改列表</td>
		</tr>
	</table>
	<div id=divModItemGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanQueryModItemGrid"></span></td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divModifiyInfo);">
			</td>
			<td class=titleImg>保项修改查询条件</td>
		</tr>
	</table>
	<div id="divModifiyInfo" class=maxbox1 style="display: ''">	
	
	    <input class=cssButton name=ItemsAddButton value="新  增" type=button onclick="insertClick();">
	    <input class=cssButton name=ItemsModifyButton value="修  改" type=button onclick="upateClick();">
	    <input class=cssButton name=ItemsDeleteButton value="删  除" type=button onclick="deleteClick();">
	    <input class=cssButton name=ApplyModifyButton value="申请修改" type=button onclick="applyModify();">
	    <input class=cssButton name=SubmitApplyButton value="提交申请" type=button onclick="submitClick();">
	    <input class=cssButton name=ItemsAddButton value="重  置" type=button onclick="clearData();">
	       
	    <table class=common>
			<tr class=common>
			    <td class=title>保项修改原因</td>
				<td class=input><input class=codeno name=ReasonType verify="保项修改原因|NOTNULL&CODE:queryclmreasontype" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('queryclmreasontype',[this,ReasonTypeName],[0,1],null,'1','1',1);" onkeyup="showCodeListKey('queryclmreasontype',[this,ReasonTypeName],[0,1],null,'1','1',1);"><input class=codename name=ReasonTypeName elementtype=nacessary></td>			
                <td class=title>规则类型</td>
				<td class=input><input class=codeno name=RuleType verify="规则类型|NOTNULL&CODE:queryclmruletype" onclick="return beforQueryCode(this, ReasonType)" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('queryclmruletype',[this,RuleTypeName],[0,1],null,'1 and a.reasonno=#'+ fm.ReasonType.value +'#','1',1);" onkeyup="showCodeListKey('queryclmruletype',[this,RuleTypeName],[0,1],null,'1 and a.reasonno=#'+ fm.ReasonType.value +'#','1',1);"><input class=codename name=RuleTypeName elementtype=nacessary></td>
				<td class=title>险种编码</td>
				<td class=input><input class=codeno name=RiskCode verify="险种编码|NOTNULL&CODE:queryclmriskcode" onclick="return beforQueryCode(this, ReasonType)" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('queryclmriskcode',[this,RiskCodeName],[0,1],null,'1 and a.reasonno=#'+ fm.ReasonType.value +'#',1);" onkeyup="showCodeListKey('queryclmriskcode',[this,RiskCodeName],[0,1],null,'1 and a.reasonno=#'+ fm.ReasonType.value +'#',1);"><input class=codename name=RiskCodeName elementtype=nacessary></td>
			</tr>
			<tr class=common>
			    <td class=title>调整方向</td>
				<td class=input><input class=codeno name=AdjustDirection verify="调整方向|NOTNULL&CODE:queryclmadjust" onclick="return beforQueryCode(this, RiskCode)" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('queryclmadjust',[this,AdjustDirectionName],[0,1],null,'1 and a.reasonno=#'+ fm.ReasonType.value +'#','1',1);" onkeyup="showCodeListKey('queryclmadjust',[this,AdjustDirectionName],[0,1],null,'1 and a.reasonno=#'+ fm.ReasonType.value +'#','1',1);"><input class=codename name=AdjustDirectionName elementtype=nacessary></td>
			    <td class=title id="UpAdjustRuleTitle">向上调整规则</td>
			    <td class=input id="UpAdjustRuleInput"><input class=codeno name=UpAdjustRule verify="向上调整规则|CODE:clmupadjustrule" onclick="return beforQueryCode(this, AdjustDirection)" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmupadjustrule',[this,UpAdjustRuleName],[0,1],null,conditionCode,'1',1);" onkeyup="showCodeListKey('clmupadjustrule',[this,UpAdjustRuleName],[0,1],null,conditionCode,'1',1);"><input class=codename name=UpAdjustRuleName></td>
			    <td class=title id="UpAdjustRateTitle">向上调整比例</td>
			    <td class=input id="UpAdjustRateInput"><input class="wid common" name=UpAdjustRate></td>
			</tr>
			<tr class=common>
				<td class=title id="StartDateTitle">保项修改生效起期</td>
				<td class=input id="StartDateInput"><input class=coolDatePicker name=StartDate dateFormat="short" onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title id="EndDateTitle">保项修改生效止期</td>
				<td class=input id="EndDateInput"><input class=coolDatePicker name=EndDate dateFormat="short" onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>								
			</tr>
			<tr class=common>
			    <td class=title>申请调整原因</td>
			    <td class=input colspan="5"><textarea class=common name=Remark id=Remark verify="申请调整原因|NOTNULL" cols="50" rows="4" maxlength=600 elementtype=nacessary></textarea></td>
			</tr>
		</table>
	</div>
	
	<!-- 保单查询 -->
	<div id=divQueryGrpCont name=divQueryGrpCont style="display:none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" name=Querya onclick="showPage(this, divQueryGrp);">
				</td>
				<td class=titleImg>保单信息查询条件</td>
			</tr>
		</table>
		<div id=divQueryGrp class=maxbox1 style="display: ''">
		    <table class=common>
				<tr class=common>
	                <td class=title>保单号</td>
				    <td class=input><input class="wid common" name=GrpContNo></td>			    
					<td class=title>投保人名称</td>
				    <td class=input><input class="wid common" name=GrpName></td>			    
				    <td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
				    <td class=title>保单生效起期</td>
					<td class=input><input class=coolDatePicker name=ValStartDate dateFormat="short" onClick="laydate({elem: '#ValStartDate'});" id="ValStartDate"><span class="icon"><a onClick="laydate({elem: '#ValStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				    <td class=title>保单生效止期</td>
					<td class=input><input class=coolDatePicker name=ValEndDate dateFormat="short" onClick="laydate({elem: '#ValEndDate'});" id="ValEndDate"><span class="icon"><a onClick="laydate({elem: '#ValEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				    <td class=title></td>
					<td class=input></td>
				</tr> 
			</table>
			<input class=cssButton type=button name=querybutton1 value="保单查询" onclick="queryGrp();">		
		</div>
		
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQueryNotGrp);">
				</td>
				<td class=titleImg >未选择保单信息列表</td>
			</tr>
		</table>
		<div id="divQueryNotGrp" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanNotGrpGrid"></span>
					</td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
			</center>
		</div>
		<input class=cssButton type=button id=SelectButton name=SelectButton value="选  择" onclick="selectClick();">
		<input class=cssButton type=button id=CancelButton name=CancelButton value="取消选择"  onclick="cancelClick();">
	</div>
	
	<!-- 保单 -->
	<div id=divGrpCont name=divGrpCont style="display:none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divAcceptGrp);">
				</td>
				<td class=titleImg>已选择保单信息列表</td>
			</tr>
		</table>
		<div id="divAcceptGrp" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanAcceptGrpGrid"></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div id=divGrpContPlan name=divGrpContPlan style="display:none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divAcceptGrpPlan);">
				</td>
				<td class=titleImg>已选择保单信息列表</td>
			</tr>
		</table>
		<div id="divAcceptGrpPlan" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanAcceptGrpPlanGrid"></span>
					</td>
				</tr>
			</table>
		</div>
		
		<input class=cssButton value="保  存" type=button onclick="savePlan();">
		
	    <table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlanGrid);">
				</td>
				<td class=titleImg>方案信息</td>
			</tr>
		</table>
		<div id="divPlanGrid" style="display: ''">	
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPlanGrid"></span></td>
				</tr>
			</table>
		</div>
	</div>

 	<div id=CheckInfo name=CheckInfo style="display:none">
	 <table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCehckConclusion);">
			</td>
			<td class=titleImg>复核意见</td>
		</tr>
	</table>
		<div id=divCehckConclusion class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>复核结论</td>
				<td class=input><input class=codeno name=CheckConclusion  readonly><input class=codename name=CheckConclusionName readonly></td>
				<td class=title></td>	   		
	   			<td class=input></td>
	   			<td class=title></td>
	   			<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>复核意见</td>
				<td class=input colspan="5"><textarea name=CheckIdea id=CheckIdea readonly cols="60" rows="3" class=common maxlength=2000></textarea></td>
			</tr>
		</table>
	</div>
	</div>
	
	<input type=hidden name=Operate>
	<input type=hidden name=ApplyNo>
	<input type=hidden name=QueryApplyNo>
	<input type=hidden name=ApplyBatchNo>
	<input type=hidden name=HiddenGrpContNo>
	<input type=hidden name=ApplyState>
	<Br /><Br /><Br /><Br /><Br />
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
