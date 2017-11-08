<%
/***************************************************************
 * <p>ProName：LLClaimCasePrintInput.jsp</p>
 * <p>Title：立案打印</p>
 * <p>Description：立案打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
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
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>外包立案打印</title>
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
	<script src="./LLClaimCasePrintInput.js"></script>
	<%@include file="./LLClaimCasePrintInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimCasePrintSave.jsp" target=fraSubmit>
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
				<td class=title>批次号</td>
				<td class=input><input class="wid common" name=GrpRgtNo id=GrpRgtNo></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
				<td class=title>受理机构</td>
				<td class=input><input class=codeno name=AcceptCom ondblclick="showCodeList('managecom',[this,AcceptComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,AcceptComName],[0,1],null,null,null,1);"><input class=codename name=AcceptComName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>受理起期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=StartDate onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>受理止期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=EndDate onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>打印状态</td>				
				<td class=input><input class=codeno name=PrintState id=PrintState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('claimprintstate',[this,PrintStateName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('claimprintstate',[this,PrintStateName],[0,1],null,null,null,1);">
				<input class=codename name=PrintStateName id=PrintStateName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>受理确认起期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=ConfirmStartDate onClick="laydate({elem: '#ConfirmStartDate'});" id="ConfirmStartDate"><span class="icon"><a onClick="laydate({elem: '#ConfirmStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>受理确认止期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=ConfirmEndDate onClick="laydate({elem: '#ConfirmEndDate'});" id="ConfirmEndDate"><span class="icon"><a onClick="laydate({elem: '#ConfirmEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>				
			</tr>
		</table>		
		<input class=cssButton value="查  询" type=button onclick="queryBatch();">
		<input class=cssButton value="批次打印" type=button onclick="batchPrint();">
	</div>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divBatchList);">
			</td>
			<td class=titleImg>批次信息列表</td>
		</tr>
	</table>
	<div id="divBatchList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanBatchListGrid"></span>
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
				<td class=title>投保人名称</td>
				<td class=input><input class="common wid" name=SingleGrpName></td>
				<td class=title>被保险人姓名</td>
				<td class=input><input class="common wid" name=SingleCustomerName></td>
				<td class=title>证件号码</td>
				<td class=input><input class="common wid" name=SingleIdNo></td>				
			</tr>
		</table>		
		<input class=cssButton value="查  询" type=button onclick="querySingle();">
		<input class=cssButton value="单个打印" type=button onclick="singlePrint();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCustomerList);">
			</td>
			<td class=titleImg>客户信息列表</td>
		</tr>
	</table>
	<div id="divCustomerList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanCustomerListGrid"></span>
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
	
	<Input type=hidden  name=Operate>				<!--操作类型-->
	<Input type=hidden  name=tGrpRgtNo>					<!--立案号-->
	<Input type=hidden  name=RgtNo>					<!--立案号-->
	<Input type=hidden  name=CustomerNo> 	 	 <!--客户号-->	
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
