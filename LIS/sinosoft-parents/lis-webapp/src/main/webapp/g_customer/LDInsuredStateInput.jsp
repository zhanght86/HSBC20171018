<%
/***************************************************************
 * <p>ProName��LDInsuredStateInput.jsp</p>
 * <p>Title�������������ά��</p>
 * <p>Description�������������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
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
	<title>�����������ά��</title>
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
			<td class=titleImg>�������ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>������������</td>
				<td class=input><input class="wid common" name=InsuredName id=InsuredName></td> 
				<td class=title>֤������</td>
				<td class=input><input class="wid common" name=InsuredIDNo id=InsuredIDNo></td> 
				<td class=title>�������˿ͻ���</td>
				<td class=input><input class="wid common" name=InsuredNo id=InsuredNo></td> 
			</tr>
		</table>
		<input class=cssButton type=button name=QueryButton value="��  ѯ" onclick="queryClick();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divComManage);">
			</td>
			<td class=titleImg>����������Ϣ</td>
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
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
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
				<td class=title>���֤��ϴ</td>
				<td class=input colspan=5><input class=common type=file id=ImportPath name=ImportPath style="width:400px"  elementtype=nacessary><input class=cssButton type=button value="��  ��" onclick="importClick();">&nbsp;<input class=cssButton type=button value="������Ϣ����" onclick="errorExport();">&nbsp;<a href="InsuredInfoList.xlsx"><font color="#FF0000">������ģ�����ء�</font></a></td>
			</tr>
		</table>
</div>
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
