<%
/***************************************************************
 * <p>ProName：LSQuotQueryOfferInput.jsp</p>
 * <p>Title：报价查询</p>
 * <p>Description：报价查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-11-19
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tOperator = tGI.Operator;
	String tManageCom = tGI.ManageCom;
%>
<script>
	var tOperator = "<%=tOperator%>";//当前登录人
	var tManageCom = "<%=tManageCom%>";//当前登录机构
	var tQuotNo = "";
	var tQuotBatNo = "";
	var tQuotType = "";
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>报价查询</title>
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
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotQueryOfferInput.js"></script>
	<%@include file="./LSQuotQueryOfferInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotQueryOfferSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryBJInfo);">
			</td>
			<td class=titleImg>报价信息查询条件</td>
		</tr>
	</table>
	<div id="divQueryBJInfo" class=maxbox  style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>报价单号</td>
				<td class=input><input class="wid common" name=OfferListNo id=OfferListNo></td>
				<td class=title>询价号</td>
				<td class=input><input class="wid common" name=QuotNoBJ id=QuotNoBJ></td>
				<td class=title>准客户名称/项目名称</td>
				<td class=input><input class="wid common" name=NameBJ id=NameBJ></td>
			</tr>
			<tr class=common>
				<td class=title>询价类型</td>
				<td class=input><input class=codeno name=QuotTypeBJ id=QuotTypeBJ style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quottype', [this,QuotTypeBJName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('quottype', [this,QuotTypeBJName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=QuotTypeBJName id=QuotTypeBJName></td>
				<td class=title>报价单状态</td>
				<td class=input><input class=codeno name=State id=State style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('printstate', [this,StateName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('printstate', [this,StateName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=StateName id=StateName></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryQuotInfo();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBJInfo);">
			</td>
			<td class=titleImg>报价信息</td>
		</tr>
	</table>
	<div id="divBJInfo"   style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanOfferListGrid"></span>
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
	<input class=cssButton type=button value="报价明细" onclick="seeQuotation();">
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
