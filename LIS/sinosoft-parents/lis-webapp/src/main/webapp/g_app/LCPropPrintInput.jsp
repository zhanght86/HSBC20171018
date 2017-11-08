<%
/***************************************************************
 * <p>ProName：PropPrintInput.jsp</p>
 * <p>Title：投保书打印</p>
 * <p>Description：投保书打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
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
	<title>投保书打印</title>
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
	<script src="./LCPropPrintInput.js"></script>
	<%@include file="./LCPropPrintInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- 查询条件 -->
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>准客户名称</td>
				<td class=input><input class="wid common" name=preCustomerName id=preCustomerName></td>
				<td class=title>报价单号</td>
				<td class=input><input class="wid common" name=OfferListNo id=OfferListNo></td>
				<td class=title>询价号</td>
				<td class=input><input class="wid common" name=QuotNo id=QuotNo> </td>
			</tr>
			<tr class=common>
				<td class=title>询价类型</td>
				<td class=input><input class=codeno name=QuotType id=QuotType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quottype', [this,QuotTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('quottype', [this,QuotTypeName], [0,1], null, null, null, '1', null);" readonly>
				<input class=codename name=QuotTypeName id=QuotTypeName></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryClick();">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult);">
			</td>
			<td class=titleImg>查询结果</td>
		</tr>
	</table>
	<div id="divResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQuotInfoGrid"></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	
	<input class=cssButton type=button value="报价单明细" onclick="showQuotation();">
	<input class=cssButton type=button value="生成投保单" onclick="createPrintInfo();">
	</div>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResultQuery);">
			</td>
			<td class=titleImg>投保单信息</td>
		</tr>
	</table>
	<div id="divResultQuery" class=maxbox1 style="display: ''">
	<table class=common>
		<tr class=common>
			<td class=title>投保单号</td>
			<td class=input><input class="wid common" name=GrpPropNoQ id=GrpPropNoQ></td>
			<td class=title>报价单号</td>
			<td class=input><input class="wid common" name=OfferListNoQ id=OfferListNoQ></td>
			<td class=title>单位名称</td>
			<td class=input><input class="wid common" name=CustomerName id=CustomerName></td>
		</tr>
		<tr class=common>
			<td class=title>投保单状态</td>
			<td class=input><input class=codeno name=State id=State style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('printstate', [this,StateName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('printstate', [this,StateName], [0,1], null, null, null, '1', null);" readonly>
			<input class=codename name=StateName id=StateName></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
	</table>
	<input class=cssButton type=button value="查  询" onclick="queryPropInfo();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult1);">
			</td>
			<td class=titleImg>查询结果</td>
		</tr>
	</table>
	<div id="divResult1" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPropInfoGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
		</center>
	<input class=cssButton type=button value="投保书信息" onclick="propInfo();">
	<input type=hidden name=Operate>
	</div>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
