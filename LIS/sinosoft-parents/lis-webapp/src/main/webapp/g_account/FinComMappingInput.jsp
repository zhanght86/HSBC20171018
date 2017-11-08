<%
/***************************************************************
 * <p>ProName：FinComMappingInput.jsp</p>
 * <p>Title：机构财务维护</p>
 * <p>Description：机构财务维护</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
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
	var tManageCom = "<%=tGI.ManageCom%>";
</script>
<html>
<head>
	<title>机构财务维护</title>
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
	<script src="./FinComMappingInput.js"></script>
	<%@include file="./FinComMappingInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./FinComMappingSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divComInfo);">
			</td>
			<td class=titleImg>请输入查询条件</td>
		</tr>
	</table>
	<div id="divComInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom verify="管理机构|code:conditioncomcode" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode',[this, ManageComName],[0, 1],null,'1 and comcode like #<%=tGI.ManageCom%>%%#',1,1);" onkeyup="return showCodeListKey('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comcode like #<%=tGI.ManageCom%>%%#',1,1);"><input class=codename name=ManageComName></td>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divComGrid);">
			</td>
			<td class=titleImg>管理机构列表</td>
		</tr>
	</table>
	<div id=divComGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanComGrid"></span></td>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFinComInfo);">
			</td>
			<td class=titleImg>机构财务信息</td>
		</tr>
	</table>
	<div id="divFinComInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>财务机构编码</td>
				<td class=input><input class=code name=FinComCode id=FinComCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="财务机构|NOTNULL" ondblclick="return showCodeList('fincom',[this,FinComName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('fincom',[this,FinComName],[0,1],null,null,null,'1',null);" elementtype=nacessary></td>
				<td class=title>财务机构名称</td>
				<td class=input><input class="wid readonly" name=FinComName readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="保  存" onclick="saveClick();">
		<input class=cssButton type=button value="重  置" onclick="resetClick();">
	</div>
	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden name=HiddenManageCom>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
