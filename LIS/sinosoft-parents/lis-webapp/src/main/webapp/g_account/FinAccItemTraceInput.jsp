<%
/***************************************************************
 * <p>ProName：FinAccItemTraceInput.jsp</p>
 * <p>Title：科目明细查询</p>
 * <p>Description：分支科目明细查询与导出</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 石全彬
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>";
</script>
<html>
<head>
	<title>科目明细查询</title>
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
	<script src="./FinAccItemTraceInput.js"></script>
	<%@include file="./FinAccItemTraceInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFinAccItemTraceInfo);">
			</td>
			<td class=titleImg>请输入查询条件</td>
		</tr>
	</table>
	
	<div id="divFinAccItemTraceInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%#','1',1);" onkeyup="return showCodeListKey('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%#','1',1);"><input class=codename name=ManageComName></td>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=GrpContNo></td>
				<td class=title>业务号</td>
				<td class=input><input class="wid common" name=OtherNo></td>
			</tr>
			<tr class=common>
				<td class=title>批次号</td>
				<td class=input><input class="wid common" name=BatchNo></td>
				<td class=title>业务起期</td>
				<td class=input><input class=coolDatePicker name=TransStartDate verify="交易起期|DATE" dateFormat="short" onClick="laydate({elem: '#TransStartDate'});" id="TransStartDate"><span class="icon"><a onClick="laydate({elem: '#TransStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>业务止期</td>
				<td class=input><input class=coolDatePicker name=TransEndDate verify="交易止期|DATE" dateFormat="short" onClick="laydate({elem: '#TransEndDate'});" id="TransEndDate"><span class="icon"><a onClick="laydate({elem: '#TransEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>会计科目</td>
				<td class=input><input class=codeno name=FinCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('finaccount',[this,FinName],[0,1],null,null,null,1)" onkeyup="return showCodeListKey('finaccount',[this,FinName],[0,1],null,null,null,1)"><input class=codename name=FinName onkeydown="fuzzyQueryFinCode(FinCode,FinName)"></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="查  询" onclick="queryClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFinAccItemTraceGrid);">
			</td>
			<td class=titleImg>科目明细信息列表</td>
		</tr>
	</table>
	<div id=divFinAccItemTraceGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanFinAccItemTraceGrid"></span></td>
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
	
	<!--隐藏区-->
	<input type=hidden name=Operate>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
