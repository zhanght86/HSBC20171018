<%
/***************************************************************
 * <p>ProName��LDAttachmentInput.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tOtherNoType = request.getParameter("OtherNoType");
	String tOtherNo = request.getParameter("OtherNo");
	String tSubOtherNo = request.getParameter("SubOtherNo");
	String tUploadNode = request.getParameter("UploadNode");
%>
<script>
	var tOtherNoType = "<%=tOtherNoType%>";
	var tOtherNo = "<%=tOtherNo%>";
	var tSubOtherNo = "<%=tSubOtherNo%>";
	var tUploadNode = "<%=tUploadNode%>";
</script>
<html>
<head >
	<title>��������</title>
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
	<script src="./LDAttachmentInput.js"></script>
	<%@include file="./LDAttachmentInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAttachment);">
			</td>
			<td class=titleImg>�����б�</td>
		</tr>
	</table>
	<div id="divAttachment" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanAttachmentGrid"></span>
				</td>
			</tr>
		</table>
	</div>
</form>
<form name=fmupload id=fmupload method=post action="./LDAttachmentSave.jsp" ENCTYPE="multipart/form-data" target=fraSubmit>
	<div id="divAttachmentUpload" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input colspan=5><input class=codeno name=AttachType id=AttachType readonly verify="��������|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('attachtype',[this, AttachTypeName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('attachtype',[this, AttachTypeName],[0, 1],null,null,null,'1',180);">
					<input class=codename name=AttachTypeName id=AttachTypeName readonly elementtype=nacessary></td>
			</tr>
			<tr>
				<td class=title>����·��</td>
				<td class=input colspan=5><input class=common type=file verify="����·��|notnull" name=UploadPath style="width:400px" elementtype=nacessary><font color="#FF0000">����֧��txt��doc��docx��xls��xlsx��pdf��zip��eml ��ʽ���ļ����أ�</font></td>
			</tr>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button name=UploadButton value="�ϴ�����" onclick="upLoadClick();">
		<input class=cssButton type=button name=DownloadButton value="���ظ���" onclick="downLoadClick();">
		<input class=cssButton type=button name=DeleteButton value="ɾ������" onclick="deleteClick();">
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
	</div>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>