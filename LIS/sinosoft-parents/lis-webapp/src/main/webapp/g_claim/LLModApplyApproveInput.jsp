<%
/***************************************************************
 * <p>ProName：LLModApplyApproveInput.jsp</p>
 * <p>Title： 总公司理赔岗审批</p>
 * <p>Description：总公司理赔岗审批</p>
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
	String tManageCom = tGI.ManageCom;
	String mOperator = tGI.Operator;
	
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //记录登陆机构
	var mOperator = "<%=mOperator%>";  //记录操作人
</script>
<html>
<head>
	<title>理赔责任查询界面</title>
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
	<script src="LLModApplyApproveInput.js"></script>
	<%@include file="LLModApplyApproveInit.jsp"%>

</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLModApplyApproveSave.jsp"  target=fraSubmit>
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
				<td class=input><input class=codeno name=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comgrade=#02# and comcode like #'+ tManageCom +'%%#','1',1);" onkeyup="showCodeListKey('managecom',[this,ManageComName],[0,1],null,'1 and comgrade=#02# and comcode like #'+ tManageCom +'%%#','1',1);"><input class=codename name=ManageComName></td>
                <td class=title>规则类型</td>
				<td class=input><input class=codeno name=QueryRuleType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmruletype',[this,QueryRuleTypeName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('clmruletype',[this,QueryRuleTypeName],[0,1],null,null,null,1);"><input class=codename name=QueryRuleTypeName></td>
				<td class=title>险种</td>
				<td class=input><input class=codeno name=QueryRiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('quotrisk',[this,QueryRiskCodeName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('quotrisk',[this,QueryRiskCodeName],[0,1],null,null,null,1);"><input class=codename name=QueryRiskCodeName></td>
			</tr>
			<tr class=common>
				<td class=title>保项修改生效起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate dateFormat="short" onClick="laydate({elem: '#QueryStartDate'});" id="QueryStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>保项修改生效止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate dateFormat="short" onClick="laydate({elem: '#QueryEndDate'});" id="QueryEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
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
			<tr class=common>
				<td class=title>申请起期</td>
				<td class=input><input class=coolDatePicker name=QueryApplyDate dateFormat="short" onClick="laydate({elem: '#QueryApplyDate'});" id="QueryApplyDate"><span class="icon"><a onClick="laydate({elem: '#QueryApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>申请止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndApplyDate dateFormat="short" onClick="laydate({elem: '#QueryEndApplyDate'});" id="QueryEndApplyDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
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
			<td class=titleImg>保项修改信息</td>
		</tr>
	</table>
	<div id="divModifiyInfo" class=maxbox1 style="display: ''">	

	       
	    <table class=common>
			<tr class=common>
			    <td class=title>保项修改原因</td>
				<td class=input><input class=codeno name=ReasonType readonly><input class=codename name=ReasonTypeName  readonly></td>			
                <td class=title>规则类型</td>
				<td class=input><input class=codeno name=RuleType readonly><input class=codename name=RuleTypeName  readonly></td>
				<td class=title>险种编码</td>
				<td class=input><input class=codeno name=RiskCode readonly><input class=codename name=RiskCodeName  readonly></td>
			</tr>
			<tr class=common>
			    <td class=title>调整方向</td>
				<td class=input><input class=codeno name=AdjustDirection readonly><input class=codename name=AdjustDirectionName  readonly></td>
			    <td class=title id="UpAdjustRuleTitle">向上调整规则</td>
			    <td class=input id="UpAdjustRuleInput"><input class=codeno name=UpAdjustRule readonly><input class=codename name=UpAdjustRuleName readonly></td>
			    <td class=title id="UpAdjustRateTitle">向上调整比例</td>
			    <td class=input id="UpAdjustRateInput"><input class="wid common" name=UpAdjustRate  readonly></td>
			</tr>
			<tr class=common>
				<td class=title>保项修改生效起期</td>
				<td class=input><input class=coolDatePicker name=StartDate dateFormat="short" readonly onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>保项修改生效止期</td>
				<td class=input><input class=coolDatePicker name=EndDate dateFormat="short" readonly onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>								
			</tr>
			<tr class=common>
			    <td class=title>申请调整原因</td>
			    <td class=input colspan="5"><textarea class=common name=Remark id=Remark verify="申请调整原因|NOTNULL" cols="50" rows="4" maxlength=600 readonly></textarea></td>
			</tr>
		</table>
	</div>
	
<div id=divGrpCont style="display:none">	
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
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
		</center>
	</div>
</div>	

	
<div id=divGrpContplan style="display:none">		
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divAcceptGrpa);">
			</td>
			<td class=titleImg>已选择保单方案信息列表</td>
		</tr>
	</table>
	<div id="divAcceptGrpa" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanAcceptGrpaGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage3.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage3.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage3.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage3.lastPage();">
		</center>
	</div>
</div>	


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

	

	 	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCehckConclusion);">
			</td>
			<td class=titleImg>审批意见</td>
		</tr>
	  	</table>
		<div id=divCehckConclusion class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>审批结论</td>
				<td class=input><input class=codeno name=ApproveConclusion style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('modchkconclusion',[this,ApproveConclusionName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('modchkconclusion',[this,ApproveConclusionName],[0,1],null,null,null,1);"><input class=codename name=ApproveConclusionName elementtype=nacessary></td>
				<td class=title></td>	   		
	   			<td class=input></td>
	   			<td class=title></td>
	   			<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>审批意见</td>
				<td class=input colspan="5"><textarea name=ApproveIdea id=ApproveIdea cols="60" rows="3" class=common maxlength=2000 elementtype=nacessary></textarea></td>
			</tr>
			<tr class=common>
			    <td>
			        <input class=cssButton type=button value="提交审批" onclick="Submit();">
			    </td>
			</tr>
		</table>
	</div>
	<br /><br /><br /><br />
	
	
	<input type=hidden name=Operate>
	<input type=hidden id="ApplyNo" name=ApplyNo > 
	<input type=hidden id="ApplyBatchNo" name=ApplyBatchNo>
	<input type=hidden id="GrpContNo" name=GrpContNo >
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
