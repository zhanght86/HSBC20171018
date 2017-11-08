<%
/***************************************************************
 * <p>ProName：LSQuotUWQueryInput.jsp</p>
 * <p>Title：总公司核保</p>
 * <p>Description：总公司核保</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-04-15
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
	<title>总公司核保</title>
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
	<script src="./LSQuotUWQueryInput.js"></script>
	<%@include file="./LSQuotUWQueryInit.jsp"%>
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
	
	<div id="divQueryInfo" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>准客户名称/项目名称</td>
				<td class=input><input class="wid common" name=QuotName id=QuotName></td>
				<td class=title>询价号</td>
				<td class=input><input class="wid common" name=QuotNo id=QuotNo></td>
				<td class=title>询价类型</td>
				<td class=input><input class=codeno name=QuotType id=QuotType readonly 
					ondblclick="return showCodeList('quottype',[this, QuotTypeName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('quottype',[this, QuotTypeName],[0, 1],null,null,null,'1',180);" style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=QuotTypeName id=QuotTypeName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>提交起期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=StartDate onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>提交止期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=EndtDate onClick="laydate({elem: '#EndtDate'});" id="EndtDate"><span class="icon"><a onClick="laydate({elem: '#EndtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom ondblclick=" showCodeList('managecom',[this,ManageComName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('managecom',[this,ManageComName],[0,1],null,null,null,1);" style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=ManageComName id=ManageComName readonly=true></td>	
			</tr>
		</table>
		<table>
			<input class=cssButton name=QueryButton type=button value="查  询" onclick="queryClick();">
			<input class=cssButton name=AppButton type=button value="申  请" onclick="appClick();">
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
	<input class=cssButton type=button value="进  入" onclick="GotoDetail();">
	<input class=cssButton type=button value="退回公共池" onclick="reApplyClick();">
	<!--隐藏区-->
	<input type=hidden name=Operate>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
