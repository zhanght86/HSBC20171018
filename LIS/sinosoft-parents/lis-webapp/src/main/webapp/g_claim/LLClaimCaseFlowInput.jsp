<%
/***************************************************************
 * <p>ProName：LLClaimCaseFlowInput.jsp</p>
 * <p>Title：赔案流程查询</p>
 * <p>Description：赔案流程查询</p>
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
	<title>赔案查询</title>
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
	<script src="./LLClaimCaseFlowInput.js"></script>
	<%@include file="./LLClaimCaseFlowInit.jsp"%>
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
					<td class=input><input class=codeno name=AcceptCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,AcceptComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,AcceptComName],[0,1],null,null,null,1);"><input class=codename name=AcceptComName readonly></td>				       
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
					<td class=title>流程状态</td>
					<td class=input><input class=codeno name=FlowState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmstate',[this,FlowName],[0,1],null,'1 and code not in (#60#,#70#,#80#)','1',1);" onkeyup="showCodeListKey('clmstate',[this,FlowName],[0,1],null,'1 and code not in (#60#,#70#,#80#)','1',1);"><input class=codename name=FlowName readonly></td>
					<td class=title>理赔操作人</td>
					<td class=input><input class=codeno readonly name=ClaimUserCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('claimusercode',[this,ClaimUserName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('claimusercode',[this,ClaimUserName],[0,1],null,null,null,'1',null);"><input class=codename name=ClaimUserName readonly ></td>
					<td class=title>交接流水号</td>
					<td class=input><input class="wid common" name=PageNo></td>				
				</tr>
			</table>
			<input class=cssButton name=CaseQuery  value="查  询" type=button onclick="queryClick();">	
			<input class=cssButton name=CaseQuery  value="重  置" type=button onclick="initParam();">
		</div>
	</div>  
  	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divLastCaseList);">
			</td>
			<td class=titleImg>赔案流程状态列表</td>
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
			<input class=cssButton type=button value="导出数据" onclick="exportData();">			
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divLastCaseFlow);">
			</td>
			<td class=titleImg>赔案流程轨迹列表</td>
		</tr>
	</table>
	<div id="divLastCaseFlow" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanLastCaseFlowGrid"></span>
				</td>
			</tr>
		</table>
	</div>	
	
	<Input type=hidden  name=Operate> 	 	 <!--操作类型-->
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
