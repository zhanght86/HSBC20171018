<%
/***************************************************************
 * <p>ProName：LLClaimLaseCaseInput.jsp</p>
 * <p>Title：历史赔案查询</p>
 * <p>Description：历史赔案查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-04-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String mCustomerNo = request.getParameter("CustomerNo");
	String mMode = request.getParameter("Mode");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mOperator = "<%=tGI.Operator%>";
	var mMode = "<%=mMode%>";
	var mCustomerNo = "<%=mCustomerNo%>";
</script>
<html>
<head>
	<title>既往赔案查询</title>
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
	<script src="./LLClaimLastCaseInput.js"></script>
	<%@include file="./LLClaimLastCaseInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
  
  <div id=queryInfo style="display:''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
				</td>
				<td class=titleImg>查询条件</td>
			</tr>
		</table>
		<div id="divQueryInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>批次号</td>
					<td class=input><input class="wid common" name=GrpRgtNo></td>
					<td class=title>投保人名称</td>
					<td class=input><input class="wid common" name=GrpName></td>  
					<td class=title>受理机构</td>
					<td class=input><input class=codeno name=AcceptCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,AcceptComName],[0,1],null,'1 and comcode like #'+mManageCom+'%#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,AcceptComName],[0,1],null,'1 and comcode like #'+mManageCom+'%#','1',1);"><input class=codename name=AcceptComName readonly></td>				       
				</tr>
				<tr class=common>
					<td class=title>出险人姓名</td>
					<td class=input><input class="wid common" name=CustomerName></td>
					<td class=title>证件号码</td>
					<td class=input><input class="wid common" name=IDNo></td>  
					<td class=title>案件号</td>
					<td class=input><input class="wid common" name=RgtNo></td>				       
				</tr>				
				<tr class=common>
					<td class=title>结案起期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=StartDate onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>结案止期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=EndDate onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>交接流水号</td>
					<td class=input><input class="wid common" name=PageNo></td>				
				</tr>
			</table>
			<input class=cssButton name=CaseQuery  value="查  询" type=button onclick="queryClick();">	
			<input class=cssButton name=initInfo  value="重  置" type=button onclick="initParam();">
		</div>
	</div>  
  	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divLastCaseList);">
			</td>
			<td class=titleImg>赔案列表信息</td>
		</tr>
	</table>
	<div id="divLastCaseList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanLastCaseListGrid"></span>
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
	<input class=cssButton name=AllCaseDetail value="查看全部赔案信息" type=button onclick="showAllDetailInfo();">
	<input class=cssButton name=CaseDetail value="查看赔案明细信息" type=button onclick="showCaseDetailInfo();">
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divLastCaseDetail);">
			</td>
			<td class=titleImg>赔案保单赔付信息</td>
		</tr>
	</table>
	<div id="divLastCaseDetail" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanLastCaseDetailGrid"></span>
				</td>
			</tr>
		</table>
	</div>	
	
	<input class=cssButton name=BackButton value="关  闭" type=button onclick="goBack();">
	
	<Input type=hidden  name=Operate> 	 	 <!--操作类型-->
	<Input type=hidden  name=CustomerNo> 	 	 <!--客户号码-->
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
