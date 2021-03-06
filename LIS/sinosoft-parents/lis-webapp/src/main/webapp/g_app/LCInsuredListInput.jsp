<%
/***************************************************************
 * <p>ProName：LCInsuredListSave.jsp</p>
 * <p>Title：人员清单任务池</p>
 * <p>Description：人员清单任务池</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 甸景
 * @version  : 8.0
 * @date     : 2014-04-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";//记录登陆机构
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>人员清单录入任务池</title>
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
	<script src="./LCInsuredListInput.js"></script>
	<%@include file="./LCInsuredListInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- 待录入询价查询条件 -->
<form name=fm id=fm method=post action="./LCInsuredListSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryES);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divQueryES" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>扫描机构</td>
				<td class=input><input class=codeno name=EscanCom id=EscanCom verify="扫描机构|NOTNULL&code:comcode" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('comcode',[this,EscanComName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this,EscanComName],[0,1],null,null,null,1);"><input class=codename name=EscanComName readonly=true></td>
				<td class=title>投保单号</td>
				<td class=input><input class="wid common" name=GrpPropNo id=GrpPropNo></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
			</tr>
		</table>
	</div>
		<input class=cssButton type=button value="查  询" onclick="queryClick(1);">
		<input class=cssButton type=button value="申  请" onclick="applyClick();">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryResult);">
			</td>
			<td class=titleImg>公共池</td>
		</tr>
	</table>
	<div id="divQueryResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanInsuredListGrid"></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton 93type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSelfInsuredResult);">
			</td>
			<td class=titleImg>个人池</td>
		</tr>
	</table>
	<div id="divSelfInsuredResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanSelfInsuredListGrid"></span>
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
	<input class=cssButton type=button value="进  入" onclick="enterInsured();">
	<input class=cssButton type=button value="退回公共池" onclick="reApplyClick();">
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
