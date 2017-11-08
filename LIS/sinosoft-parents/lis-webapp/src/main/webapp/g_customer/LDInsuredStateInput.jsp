<%
/***************************************************************
 * <p>ProName：LDInsuredStateInput.jsp</p>
 * <p>Title：被保险人身故维护</p>
 * <p>Description：被保险人身故维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-07-17
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
<head >
	<title>被保险人身故维护</title>
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
	<script src="./LDInsuredStateInput.js"></script>
	<%@include file="./LDInsuredStateInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>请输入查询条件</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>被保险人姓名</td>
				<td class=input><input class="wid common" name=InsuredName id=InsuredName></td> 
				<td class=title>证件号码</td>
				<td class=input><input class="wid common" name=InsuredIDNo id=InsuredIDNo></td> 
				<td class=title>被保险人客户号</td>
				<td class=input><input class="wid common" name=InsuredNo id=InsuredNo></td> 
			</tr>
		</table>
		<input class=cssButton type=button name=QueryButton value="查  询" onclick="queryClick();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divComManage);">
			</td>
			<td class=titleImg>被保险人信息</td>
		</tr>
	</table>
	<div id="divComManage" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanInsuredInfoGrid"></span>
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
</form>
<form name=fmupload id=fmupload method=post action="" ENCTYPE="multipart/form-data" target=fraSubmit>
<div id=divServiceImport style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common id="tr2" style="display: ''">
				<td class=title>身份证清洗</td>
				<td class=input colspan=5><input class=common type=file id=ImportPath name=ImportPath style="width:400px"  elementtype=nacessary><input class=cssButton type=button value="导  入" onclick="importClick();">&nbsp;<input class=cssButton type=button value="错误信息导出" onclick="errorExport();">&nbsp;<a href="InsuredInfoList.xlsx"><font color="#FF0000">【导入模板下载】</font></a></td>
			</tr>
		</table>
</div>
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
