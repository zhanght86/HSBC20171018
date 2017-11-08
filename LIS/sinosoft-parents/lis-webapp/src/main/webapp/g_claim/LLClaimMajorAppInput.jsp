<%
/***************************************************************
 * <p>ProName：LLClaimMajorAppInput.jsp</p>
 * <p>Title：重大案件上报公共池界面</p>
 * <p>Description：重大案件上报公共池界面</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String mCurrentDate = PubFun.getCurrentDate();
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mOperator = "<%=tGI.Operator%>";
	var mCurrentDate = "<%=mCurrentDate%>";
</script>
<html>
<head>
	<title>重大案件上报公共池界面</title>
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
	<script src="./LLClaimMajorAppInput.js"></script>
	<%@include file="./LLClaimMajorAppInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimMajorMainSave.jsp" target=fraSubmit>
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
				<td class=title>报案号</td>
				<td class=input><input class="wid common" name=RptNo></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName></td>
				<td class=title>报案机构</td>
				<td class=input><input class=codeno name=RptCom id=RptCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,RptComName],[0,1],null,'1 and managecom like #%'+mManageCom+'#','1',1);" onkeyup="showCodeListKey('managecom',[this,RptComName],[0,1],null,'1 and managecom like #%'+mManageCom+'#','1',1);">
				<input class=codename name=RptComName id=RptComName readonly elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>姓名</td>
				<td class=input><input class="wid common" name=CustomerName></td>
				<td class=title>报案登记起期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=RptDateStart onClick="laydate({elem: '#RptDateStart'});" id="RptDateStart"><span class="icon"><a onClick="laydate({elem: '#RptDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>报案登记止期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=RptDateEnd onClick="laydate({elem: '#RptDateEnd'});" id="RptDateEnd"><span class="icon"><a onClick="laydate({elem: '#RptDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>       
			</tr>
		</table>
		
		<input class=cssButton value="查  询" type=button onclick="queryClick();">
		<input class=cssButton value="查看报案明细" type=button onclick="showRptDetail();">
	</div>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divLLClaimReport);">
			</td>
			<td class=titleImg>报案信息列表</td>
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
	<input class=cssButton name='Report' id=Report value="进入案件" type=button onclick="enterReport();">
	
	<Input type=hidden  name=Operate> 	 	 <!--操作类型-->
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
