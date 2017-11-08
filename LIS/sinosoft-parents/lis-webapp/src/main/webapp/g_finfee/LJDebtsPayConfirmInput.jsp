<%
/***************************************************************
 * <p>ProName：LJDebtsPayConfirmInput.jsp</p>
 * <p>Title：坏账审核</p>
 * <p>Description：坏账审核</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-09-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate = PubFun.getCurrentDate();
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>坏账审核</title>
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
	<script src="./LJDebtsPayConfirmInput.js"></script>
	<%@include file="./LJDebtsPayConfirmInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div style="display: none">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanVirtualGrid" ></span>
				</td>
			</tr>
		</table>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divInfo1" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=QueryManageCom id=QueryManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('conditioncomcode', [this,QueryManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,QueryManageComName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=QueryManageComName id=QueryManageComName readonly>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=AppntName id=AppntName></td>
			</tr>
			<tr class=common>
				<td class=title>应收起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate1 id=QueryStartDate1 dateFormat="short"></td>
				<td class=title>应收止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate1 id=QueryEndDate1 dateFormat="short"></td>
				<td class=title>申请起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate2 id=QueryStartDate2 dateFormat="short"></td>
			</tr>
			<tr class=common>
				<td class=title>申请止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate2 id=QueryEndDate2 dateFormat="short"></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryInfo();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>待申请信息列表</td>
		</tr>
	</table>
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanDebtsPayInfoGrid" ></span>
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
	
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>审核结论</td>
				<td class=input><input class=codeno name=ConfirmConlusion id=ConfirmConlusion style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('passflag', [this,ConfirmConlusionName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('managecom', [this,ConfirmConlusionName], [0,1], null, null, null, 1);" readonly><input class=codename name=ConfirmConlusionName id=ConfirmConlusionName readonly elementtype=nacessary>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>结论描述</td>
				<td class=input colspan=5><textarea cols=80 rows=3 name=ConfirmDesc id=ConfirmDesc></textarea></td>
			</tr>
		</table>
		<input class=cssButton type=button value="审核提交" onclick="confirmClick();">
	</div>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate id=Operate>
	</div>
	
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
