<%
/***************************************************************
 * <p>ProName：EdorPrintInput.jsp</p>
 * <p>Title：批单打印</p>
 * <p>Description：批单打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-09-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<html>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	String tCurrentDate = PubFun.getCurrentDate();
%>
<script type="text/javascript">
	var tManageCom = "<%= tGI.ManageCom %>";
	var tCurrentDate = "<%= tCurrentDate %>";
	var tStr = "1 and activityid in(#1800401001#,#1800401002#)";
</script>
<head >
	<title>批单打印</title>
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
	<script src="EdorPrintInput.js"></script>

	<%@include file="EdorPrintInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorPrintQuery);">
			</td>
		 	<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divQueryInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>保全受理号</td>
				<td class=input><input class="wid common" name=EdorAppNo id=EdorAppNo></td>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>投保人客户号</td>
				<td class=input><input class="wid common" name=AppntNo id=AppntNo></td>
			</tr>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,ManageComName],[0,1],null,'1',1,'1',180);" onkeyup="return showCodeListKey('conditioncomcode',[this,ManageComName],[0,1],null,'1',1,'1',180);"><input class=codename name=ManageComName readonly></td>
				<td class=title>受理日期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=AcceptDATE verify="受理日期|date" onClick="laydate({elem: '#AcceptDATE'});" id="AcceptDATE"><span class="icon"><a onClick="laydate({elem: '#AcceptDATE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName></td>
			</tr>
			<tr class=common>
				<td class=title>销售渠道明细</td>
				<td class=input><input class=codeno name=SaleChnl id=SaleChnl style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('agenttype',[this,SaleChnlName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('agenttype',[this,SaleChnlName],[0,1],null,null,null,1);"><input class=codename name=SaleChnlName readonly></td>
				<td class=title>保全项目</td>
				<td class=input><input class=codeno name=EdorType id=EdorType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('gedortype',[this,EdorTypeName],[0,1],null,tStr,'1','1',180);" onkeyup="return showCodeListKey('gedortype',[this,EdorTypeName],[0,1],null,tStr,'1','1',180);"><input class=codename readonly name=EdorTypeName></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton value="查  询" type=button onclick="queryClick(1);">
	</div>
		
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQueryResult);">
			</td>
			<td class=titleImg>查询结果</td>
		</tr>
	</table>
	<div id="divQueryResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQueryResultGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	<input class=cssButton type=button value="打  印" onclick="print();">
	<!--隐藏区-->
	<input type=hidden name=Operate>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
