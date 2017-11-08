<%
/***************************************************************
 * <p>ProName：LSQuotBranchFinalQueryInput.jsp</p>
 * <p>Title：分公司报价生成</p>
 * <p>Description：分公司报价生成</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head >
	<title>分公司报价生成</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LSQuotBranchFinalQueryInput.js"></script>
	<%@include file="./LSQuotBranchFinalQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
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
				<td class=title>准客户名称/项目名称</td>
				<td class=input><input class="wid common" name=QuotName id=QuotName></td>
				<td class=title>询价号</td>
				<td class=input><input class="wid common" name=QuotNo id=QuotNo></td>
				<td class=title>询价类型</td>
				<td class=input><input class=codeno name=QuotType id=QuotType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('quottype',[this, QuotTypeName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('quottype',[this, QuotTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=QuotTypeName id=QuotTypeName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>提交起期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=StartDate id=StartDate></td>
				<td class=title>提交止期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=EndtDate id=EndtDate></td>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center"   ondblclick=" showCodeList('managecom',[this,ManageComName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('managecom',[this,ManageComName],[0,1],null,null,null,1);"><input class=codename name=ManageComName id=ManageComName readonly=true></td>	
			</tr>
		</table>
		<table>
			<input class=cssButton name=QueryButton id=QueryButton type=button value="查  询" onclick="queryClick();">
			<input class=cssButton name=AppButton id=AppButton type=button value="申  请" onclick="appClick();">
		</table>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuotGrid);">
			</td>
			<td class=titleImg>公共池</td>
		</tr>
	</table>
	<div id="divQuotGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQuotGrid"></span>
				</td>
			</tr>
		</table>
		<div id= "divQuotGridPage" style= "display: '';text-align:center;">
			<table align=center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			</table>
		</div>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPerQuotGrid);">
			</td>
			<td class=titleImg>个人池</td>
		</tr>
	</table>
	<div id="divPerQuotGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPerQuotGrid"></span>
				</td>
			</tr>
		</table>
		<div id= "divPerQuotGridPage" style= "display: '';text-align:center;">
			<table align=center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
			</table>
		</div>
	</div>
	
	<!--隐藏区-->
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
