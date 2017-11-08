<%
/***************************************************************
 * <p>ProName：LSQuotQuesnaireInput.jsp</p>
 * <p>Title：问卷调查</p>
 * <p>Description：问卷调查</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tQuotType = request.getParameter("QuotType");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tActivityID = request.getParameter("ActivityID");
%>
<script>
	var tQuotType = "<%=tQuotType%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tActivityID = "<%=tActivityID%>";
</script>
<html>
<head >
	<title>问卷调查</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
	<script src="./LSQuotQuesnaireInput.js"></script>
	<%@include file="./LSQuotQuesnaireInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuesnaire);">
			</td>
			<td class=titleImg>问卷列表</td>
		</tr>
	</table>
	<div id="divQuesnaire" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQuesnaireGrid"></span>
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
	<div id="divQuesnaireUpload" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>问卷类型</td>
				<td class=input><input class=codeno name=QuesnaireType id=QuesnaireType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="问卷类型|notnull"
					ondblclick="return showCodeList('quesnairetype',[this, QuesnaireTypeName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('quesnairetype',[this, QuesnaireTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=QuesnaireTypeName id=QuesnaireTypeName readonly elementtype=nacessary></td>
				<td class=title>问卷路径</td>
				<td class=input colspan=3><input class=common type=file verify="问卷路径|notnull" name=UploadPath id=UploadPath style="width:400px" elementtype=nacessary><font color="#FF0000">（仅支持gif格式的文件上载）</font></td>
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
	</div>
	
	<input class=cssButton type=button name=UploadButton id=UploadButton value="上传问卷" onclick="upLoadClick();">
	<input class=cssButton type=button name=DownloadButton id=DownloadButton value="下载问卷" onclick="downLoadClick();">
	<input class=cssButton type=button name=DeleteButton id=DeleteButton value="删除问卷" onclick="deleteClick();">
	<input class=cssButton type=button value="关  闭" onclick="top.close();">
	<br>
	<div id="divPersonButton" style="display: ''">
		<input class=cssButton type=button name=PersonButton id=PersonButton value="上传个人问卷" onclick="PersonClick();"><font color="#FF0000">（个人问卷在【个人核保】信息中进行录入）</font>
	</div>
</form>
<hr class="line">
<form name=fm3 id=fm3 method=post action="" target=fraSubmit>
	<div id="divPages" style="display:'none';position:absolute;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=0, xray=0, mirror=0, invert=0, opacity=1, rotation=0)">
		<img galleryImg="no" border="0" id="service" src="">
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
