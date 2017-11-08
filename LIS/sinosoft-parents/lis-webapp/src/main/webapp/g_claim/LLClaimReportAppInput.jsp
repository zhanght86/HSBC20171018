<%
/***************************************************************
 * <p>ProName：LLClaimReportAppInput.jsp</p>
 * <p>Title：报案申请界面</p>
 * <p>Description：报案申请界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mOperator = "<%=tGI.Operator%>";	
</script>
<html>
<head>
	<title>报案管理申请界面</title>
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
	<script src="./LLClaimReportAppInput.js"></script>
	<%@include file="./LLClaimReportAppInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimReportAppSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBank);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divMissFind" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>姓名</td>
				<td class=input><input class="wid common" name=CustomerName id=CustomerName></td>
				<td class=title>证件号码</td>
				<td class=input><input class="wid common" name=IdNo id=IdNo></td>  
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>       
			</tr>
			<tr class=common>
				<td class=title>报案号</td>
				<td class=input><input class="wid common" name=QueryRptNo id=QueryRptNo></td>
				<td class=title>报案登记起期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=RptDateStart onClick="laydate({elem: '#RptDateStart'});" id="RptDateStart"><span class="icon"><a onClick="laydate({elem: '#RptDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>报案登记止期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=RptDateEnd onClick="laydate({elem: '#RptDateEnd'});" id="RptDateEnd"><span class="icon"><a onClick="laydate({elem: '#RptDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>       
			</tr>
			<tr class=common>
				<td class=title>是否报案确认</td>
				<td class=input><input class=codeno name=RptState id=RptState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,RptStateName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('trueflag',[this,RptStateName],[0,1],null,null,null,1);">
				<input class=codename name=RptStateName id=RptStateName readonly></td>
				<td class=title>报案确认起期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=OperatorDateStart onClick="laydate({elem: '#OperatorDateStart'});" id="OperatorDateStart"><span class="icon"><a onClick="laydate({elem: '#OperatorDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>报案确认止期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=OperatorDateEnd onClick="laydate({elem: '#OperatorDateEnd'});" id="OperatorDateEnd"><span class="icon"><a onClick="laydate({elem: '#OperatorDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>报案机构</td>
				<td class=input><input class=codeno name=RptCom id=RptCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,RptComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,RptComName],[0,1],null,null,null,1);"><input class=codename name=RptComName id=RptComName readonly></td>
				<td class=title>关联状态</td>
				<td class=input><input class=codeno name=RptFlag id=RptFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,RptFlagName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('trueflag',[this,RptFlagName],[0,1],null,null,null,1);"><input class=codename name=RptFlagName id=RptFlagName readonly></td>
				<td class=title>报案状态</td>
				<td class=input><input class=codeno name=rptstate id=rptstate style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('llreportflag',[this,rptstateName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('llreportflag',[this,rptstateName],[0,1],null,null,null,1);"><input class=codename name=rptstateName id=rptstateName readonly></td>
			</tr>
		</table>
		
		<input class=cssButton value="查  询" type=button onclick="queryClick();">
		<input class=cssButton value="查看报案明细" type=button onclick="showReportDetail();">
	</div>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divLLClaimReport);">
			</td>
			<td class=titleImg>公共池</td>
		</tr>
	</table>
	<div id="divLLClaimReport" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanLLClaimReportGrid"></span>
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
	<input class=cssButton name='Report' value="申  请" type=button onclick="applyReport();">
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divSelfLLClaimReportGrid);">
			</td>
			<td class=titleImg>个人池</td>
		</tr>
	</table>
	<div id="divSelfLLClaimReportGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanSelfLLClaimReportGrid"></span>
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
	
	<input class=cssButton name='reportEnter' value="进入报案" type=button onclick="enterReport();">
	<input class=cssButton name='reportRelease' value="释放报案" type=button onclick="releaseReport();">
	<input class=cssButton name='reportNew' value="新增报案" type=button onclick="newReport();">
	<Input type=hidden  name=RptNo>				<!--报案号-->
	<Input type=hidden  name=Operate>			<!--操作类型-->
	<br /><br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
