<%
/***************************************************************
 * <p>ProName：FinExtractInput.jsp</p>
 * <p>Title：会计分录提取</p>
 * <p>Description：会计分录提取</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>会计分录提取</title>
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
	<script src="./FinExtractInput.js"></script>
	<%@include file="./FinExtractInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./FinExtractSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFinExtract);">
			</td>
			<td class=titleImg>请输入业务日期</td>
		</tr>
	</table>
	
	<div id="divFinExtract" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>起始日期</td>
				<td class=input><input class=coolDatePicker dateFormat="short" name=StartDate verify="起始日期|notnull&DATE&LEN=10" elementtype=nacessary onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>终止日期</td>
				<td class=input><input class=coolDatePicker dateFormat="short" name=EndDate verify="终止日期|notnull&DATE&LEN=10" elementtype=nacessary onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td><font color="#FF0000">注：会计分录提数，一次可以提取多天数据</font></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="提  数" onclick="extractData();">
		<input class=cssButton type=button value="删  除" onclick="deleteData();">
		<input class=cssButton type=button value="下载财务接口文件" onclick="downloadData();">&nbsp;&nbsp;&nbsp;
		<input class=cssButton type=button value="查  询" onclick="queryData();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divFinExtractInfo);">
			</td>
			<td class=titleImg>会计分录信息</td>
		</tr>
	</table>
	<div id="divDataSourceInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanFinExtractGrid"></span>
				</td>
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
	
	<input type=hidden name=Operate>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
