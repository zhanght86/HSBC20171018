<%
/***************************************************************
 * <p>ProName：LLClaimBlackQueryInput.jsp</p>
 * <p>Title：黑名单查询</p>
 * <p>Description：黑名单查询</p>
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
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>黑名单查询</title>
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
	<script src="./LLClaimBlackQueryInput.js"></script>
	<%@include file="./LLClaimBlackQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
  
  <div id=queryInfo style="display:''">
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
					<td class=title>保单号</td>
					<td class=input><input class="wid common" dateFormat=short name=GrpContNo id=GrpContNo></td>	
					<td class=title>投保人名称</td>
					<td class=input><input class="wid common" name=GrpName id=GrpName></td> 
					<td class=title>被保人姓名</td>
					<td class=input><input class="wid common" name=CustomerName id=CustomerName></td>
				</tr>
				<tr class=common>
					<td class=title>证件号码</td>
					<td class=input><input class="wid common" name=IDNo id=IDNo></td>  
					<td class=title>出生日期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=BirthDay onClick="laydate({elem: '#BirthDay'});" id="BirthDay"><span class="icon"><a onClick="laydate({elem: '#BirthDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<input class=cssButton name=Query  value="查  询" type=button onclick="queryClick();">
			<input class=cssButton name=InitClick value="重  置" type=button onclick="initQueryInfo();">	
		</div>
	</div>  
  	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divBlackList);">
			</td>
			<td class=titleImg>黑名单列表</td>
		</tr>
	</table>
	<div id="divBlackList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanBlackListGrid"></span>
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
	<input class=cssButton name=LastCase value="既往赔案查询" type=button onclick="showOldCase();">			
	
	<Input type=hidden  name=Operate id=Operate> 	 	 <!--操作类型-->
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
