<%
/***************************************************************
 * <p>ProName：FinComInput.jsp</p>
 * <p>Title：财务机构维护</p>
 * <p>Description：财务机构维护</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>财务机构维护</title>
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
	<script src="./FinComInput.js"></script>
	<%@include file="./FinComInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./FinComSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFinComInfo);">
			</td>
			<td class=titleImg>财务机构信息</td>
		</tr>
	</table>
	<div id="divFinComInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>财务机构编码</td>
				<td class=input><input class="wid common" name=FinComCode id=FinComCode verify="财务机构代码|NOTNULL&LEN=9" maxlength=9 elementtype=nacessary></td>
				<td class=title>财务机构名称</td>
				<td class=input><input class="wid common" name=FinComName id=FinComName verify="财务机构名称|NOTNULL" maxlength=60 elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryClick();">
		<input class=cssButton type=button value="新  增" onclick="insertClick();">
		<input class=cssButton type=button value="修  改" onclick="updateClick();">
		<input class=cssButton type=button value="删  除" onclick="deleteClick();">
		<input class=cssButton type=button value="重  置" onclick="resetClick();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFinComGridInfo);">
			</td>
			<td class=titleImg>财务机构列表</td>
		</tr>
	</table>
	<div id=divFinComGridInfo style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanFinComGrid"></span></td>
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
	<input type=hidden name=HiddenFinComCode>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
