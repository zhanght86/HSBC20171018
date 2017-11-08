<%
/***************************************************************
 * <p>ProName：LLClaimQuestionQueryInput.jsp</p>
 * <p>Title：问题件查询</p>
 * <p>Description：问题件查询</p>
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
	<title>问题件查询</title>
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
	<script src="./LLClaimQuestionQueryInput.js"></script>
	<%@include file="./LLClaimQuestionQueryInit.jsp"%>
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
					<td class=title>团体批次号</td>
					<td class=input><input class="wid common" name=GrpRgtNo id=GrpRgtNo></td>
					<td class=title>案件号</td>
					<td class=input><input class="wid common" name=RgtNo id=RgtNo></td>							
					<td class=title>交接流转号</td>
					<td class=input><input class="wid common" name=PageNo id=PageNo></td> 
				</tr>
				<tr class=common>
					<td class=title>保单号</td>
					<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
					<td class=title>单位名称</td>
					<td class=input><input class="wid common" name=GrpName id=GrpName></td>  
					<td class=title>出险人姓名</td>
					<td class=input><input class="wid common" name=CustomerName id=CustomerName></td>			
				</tr>
				<tr class=common>
					<td class=title>发起问题件起期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=InputStartDate onClick="laydate({elem: '#InputStartDate'});" id="InputStartDate"><span class="icon"><a onClick="laydate({elem: '#InputStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>发起问题件止期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=InputEndDate onClick="laydate({elem: '#InputEndDate'});" id="InputEndDate"><span class="icon"><a onClick="laydate({elem: '#InputEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>  
					<td class=title>处理状态</td>
					<td class=input><input class=codeno name=State style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmflag',[this,StateName],[0,1],null,'1','1',1);" onkeyup="showCodeListKey('clmflag',[this,StateName],[0,1],null,'1','1',1);"><input class=codename name=StateName readonly></td>			
				</tr>
				<tr class=common>
					<td class=title>处理问题件起期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=DealStartDate onClick="laydate({elem: '#DealStartDate'});" id="DealStartDate"><span class="icon"><a onClick="laydate({elem: '#DealStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>处理问题件止期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=DealEndDate onClick="laydate({elem: '#DealEndDate'});" id="DealEndDate"><span class="icon"><a onClick="laydate({elem: '#DealEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>  
					<td class=title>立案机构</td>
					<td class=input><input class=codeno name=AcceptCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,AcceptComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,AcceptComName],[0,1],null,null,null,1);"><input class=codename name=AcceptComName readonly></td>			
				</tr>								
			</table>
			<input class=cssButton name=Query  value="查  询" type=button onclick="queryClick();">
			<input class=cssButton name=InitClick value="重  置" type=button onclick="initQueryInfo();">	
		</div>
	</div>  
  	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQuestiontList);">
			</td>
			<td class=titleImg>问题件列表</td>
		</tr>
	</table>
	<div id="divQuestiontList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQuestionListGrid"></span>
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
	
	<Input type=hidden  name=Operate> 	 	 <!--操作类型-->
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
