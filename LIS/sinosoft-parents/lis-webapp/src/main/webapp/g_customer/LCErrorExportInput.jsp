<%
/***************************************************************
 * <p>ProName��LCErrorExportInput.jsp</p>
 * <p>Title��������Ϣ����</p>
 * <p>Description��������Ϣ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
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
	var tManageCom = "<%=tGI.ManageCom%>";//��¼��½����
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head >
	<title>������Ϣ����</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/Calendar/Calendar.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LCErrorExportInput.js"></script>
	<%@include file="./LCErrorExportInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divImport);">
			</td>
			<td class=titleImg>������Ϣ��ѯ����</td>
		</tr>
	</table>
	<div id="divImport" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=StartImportDate id=StartImportDate onClick="laydate({elem: '#StartImportDate'});" id="StartImportDate"><span class="icon"><a onClick="laydate({elem: '#StartImportDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=EndImportDate id=EndImportDate></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick();">
	</div>
	<br>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBatch);">
			</td>
			<td class=titleImg>���������б�</td>
		</tr>
	</table>
	<div id="divBatch" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanBatchGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
		
		<input class=cssButton type=button value="����������Ϣ����" name="DownloadButton" id=DownloadButton onclick="downloadClick();">
	</div>

	<input type=hidden name=SheetName id=SheetName>
	<input type=hidden name=SheetTitle id=SheetTitle>
	<input type=hidden name=SheetSql id=SheetSql>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
