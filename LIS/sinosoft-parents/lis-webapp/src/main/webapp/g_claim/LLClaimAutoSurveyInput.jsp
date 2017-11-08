<%
/***************************************************************
 * <p>ProName：LLClaimAutoSurveyInput.jsp</p>
 * <p>Title：理赔自动调查维护</p>
 * <p>Description：理赔自动调查维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 李肖峰
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String mManageCom = tGI.ManageCom;
	String mOperator = tGI.Operator;
%>
<script>
	var mManageCom = "<%=mManageCom%>"; //记录登陆机构
	var mOperator = "<%=mOperator%>";  //记录操作人
</script>
<html>
<head>
	<title>理赔抽检规则维护</title>
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
	<script src="./LLClaimAutoSurveyInput.js"></script>
	<%@include file="./LLClaimAutoSurveyInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post action="./LLClaimAutoSurveySave.jsp" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,QueryHospital);">
				</td>
				<td class=titleImg>查询条件</td>
			</tr>
		</table> 
		<div id=divQueryInfo class=maxbox1 style="display:''">
			<table class=common>
				<tr class=common>  
					<td class=title>调查机构</td>
					<td class=input><input class=codeno name=QueryManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,QueryManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,QueryManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);"><input class=codename name=QueryManageComName readonly></td>
					<td class=title>险种</td>
					<td class=input><input class=codeno name=QueryRiskcode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('quotrisk',[this,QueryRiskName],[0,1],null,'1','1',1);" onkeyup="showCodeListKey('quotrisk',[this,QueryRiskName],[0,1],null,'1','1',1);"><input class=codename name=QueryRiskName readonly></td>
					<td class=title>审核金额(>=)</td>      
					<td class=input><input class="wid common" name=QueryUWMoney></td>
				</tr>
				<tr class=common>
					<td class=title>赔付金额(>=)</td>      
					<td class=input><input class="wid common" name=QueryPayMoney></td>
					<td class=title>生效起期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=QueryStartDate onClick="laydate({elem: '#QueryStartDate'});" id="QueryStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>生效止期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=QueryEndDate onClick="laydate({elem: '#QueryEndDate'});" id="QueryEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
			</table>  
			<input value="查  询" class=cssButton name=querybutton type=button onclick="queryAutoRule();">
			<input class=cssButton name="initButton" value="清  空" type=button onclick="initQueryInfo();">
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divRuleList);">
				</td>
				<td class=titleImg>自动调查规则列表</td>
			</tr>
		</table>  
		<div id="divRuleList" style= "display:''">    
			<table  class= common>
				<tr>
					<td text-align: left colSpan=1><span id="spanLLClaimRuleListGrid"></span></td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
				<input class=cssButton type=button name=exportbutton value="导出数据" onclick="exportData();">
			</center>
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divClaimRuleInfo);">
				</td>
				<td class=titleImg>自动调查规则信息</td>
			</tr>
		</table>  
		<div id="divClaimRuleInfo" class=maxbox1 style= "display:''">       
		<div>
			<input class=cssButton name="addButton" value="新  增" type=button onclick="addRule();">
			<input class=cssButton name="modifyButton" value="修  改" type=button onclick="modifyRule();">
			<input class=cssButton name="deleteButton" value="删  除" type=button onclick="delteRule();">
			<input class=cssButton name="initButton" value="重  置" type=button onclick="initRule();">
		</div>    
		<table class=common>
			<tr class=common>   
				<td class=title>抽检机构</td>
				<td class=input><input class=codeno name=CheckManageCom verify="抽检机构|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,CheckManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,CheckManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);"><input class=codename name=CheckManageComName elementtype=nacessary readonly></td>
				<td class=title>险种</td>      
				<td class=input><input class=codeno name=CheckRiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotrisk',[this,CheckRiskName],[0,1],null,'1','1','1',null);" onkeyup="return showCodeListKey('quotrisk',[this,CheckRiskName],[0,1],null,'1','1','1',null);"><input class=codename name=CheckRiskName readonly ></td>
				<td class=title>审核金额(>=)</td>				     
				<td class=input><input class="wid common" name=CheckAppPay verify="赔付金额|num" ></td>
			</tr>
			<tr class=common>
        <td class=title>赔付金额(>=)</td>
				<td class=input><input class="wid common" name=CheckClmPay verify="赔付金额|num" ></td>
				<td class=title>生效起期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=CheckStartDate verify="生效起期|NOTNULL" elementtype=nacessary onClick="laydate({elem: '#CheckStartDate'});" id="CheckStartDate"><span class="icon"><a onClick="laydate({elem: '#CheckStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>生效止期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=CheckEndDate verify="生效止期|NOTNULL" elementtype=nacessary onClick="laydate({elem: '#CheckEndDate'});" id="CheckEndDate"><span class="icon"><a onClick="laydate({elem: '#CheckEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>				
			</tr>
		</table>
	</div>

	<input type=hidden id="Operate" name=Operate > 
	<input type=hidden id="RuleNo" name=RuleNo > 
	<br /><br /><br /><br />
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
