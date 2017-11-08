<%
/***************************************************************
 * <p>ProName：LCPolSendBackInput.jsp</p>
 * <p>Title：回执回销</p>
 * <p>Description：回执回销</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-07
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	String tCurrentDate = PubFun.getCurrentDate();
	String tCurrentTime = PubFun.getCurrentTime();
%>

<script>
	var tManageCom = "<%=tGI.ManageCom%>";//记录登陆机构
	var tOperator = "<%=tGI.Operator%>";
	var	tCurrentDate = "<%=tCurrentDate%>";
	var	tCurrentTime = "<%=tCurrentTime%>";
	
</script>
<html>
<head>
	<title>回执回销</title>
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
	<script src="./LCPolSendBackInput.js"></script>
	<script src="./LCContCommonInput.js"></script>
	<%@include file="./LCPolSendBackInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">

<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>承保机构</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('comcodeall',[this,EscanComName],[0,1],null,'1 and comgrade=#03#',1,1);" onkeyup="return showCodeListKey('comcodeall',[this,EscanComName],[0,1],null,'1 and comgrade=#03#',1,1);"><input class=codename name=EscanComName readonly=true></td>
				<td class=title>投保单号</td>
				<td class=input><input class="wid common" id=GrpPropNo name=GrpPropNo></td>
				<td class=title>团体保单号</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
			</tr>
			<tr class=common>
				<td class=title>递送登记起期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=ExpressStartDate verify="递送登记起期|date" onClick="laydate({elem: '#ExpressStartDate'});" id="ExpressStartDate"><span class="icon"><a onClick="laydate({elem: '#ExpressStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>递送登记止期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=ExpressEndDate verify="递送登记止期|date" onClick="laydate({elem: '#ExpressEndDate'});" id="ExpressEndDate"><span class="icon"><a onClick="laydate({elem: '#ExpressEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
			</tr>
		</table>
		<input class=cssButton  type=button value="查  询" onclick="queryClick();">
		<input class=cssButton style="display: 'none'" type=button value="影像件查询" onclick="">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
			<td class=titleImg>查询结果</td>
		</tr>
	</table>
	<div id="divQueryInfo" style="display: ''">	
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanContInfoGrid"></span>
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
	<input class=cssButton  type=button value="回  销" onclick="saveClick();">
	<input type=hidden name=Operate>
	<Br />	<Br /><Br /><Br /><Br />	
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
