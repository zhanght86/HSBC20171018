<%
/***************************************************************
 * <p>ProName：ScanPagesQueryInput.jsp</p>
 * <p>Title：影像件查询</p>
 * <p>Description：影像件查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 刘锦祥
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String tBussNo = request.getParameter("BussNo");
	String tBussType = request.getParameter("BussType");
%>
<script>	
	var tBussNo = "<%=tBussNo%>";
	var tBussType = "<%=tBussType%>";
</script>

<head>
<title>影像件查询</title>
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
	<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <script src="./ScanPagesQueryInput.js"></script> 
  <%@include file="./ScanPagesQueryInit.jsp"%>
  <SCRIPT>
	window.document.onkeydown = document_onkeydown;
	</SCRIPT>
</head>

<body onload="initForm()">

<form method="post" name="fm" id=fm target="fraSubmit" target="fraSubmit">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divScanPages);">
			</td>
			<td class="titleImg">扫描件信息</td>
		</tr>
	</table>
	<div class=maxbox1>
	<input class="cssButton" type="button" onclick="scanQuery();" value="查  询">
	<input class="cssButton" type="button" onclick="top.close();" value="关  闭">
	</div>
	<div id="divScanPages" style="display:''">
		
		<table class="common">
			<tr>
				<td style="text-align:left" colSpan="1">
					<span id="spanScanPagesGrid"></span>
				</td>
			</tr>
		</table>
		
		<center>
			<input value="首  页" class="cssButton90" type="button" onclick="turnPage1.firstPage();">
			<input value="上一页" class="cssButton91" type="button" onclick="turnPage1.previousPage();">
			<input value="下一页" class="cssButton92" type="button" onclick="turnPage1.nextPage();">
			<input value="尾  页" class="cssButton93" type="button" onclick="turnPage1.lastPage();">
		</center>
		<font color=red style="font-size:9pt;"><center id="centerPic2" class=common><span>PageDown:下一页 | PageUp:上一页 | Ctrl和Alt和+:放大图片 | Ctrl和Alt和-:缩小图片| End:旋转图片 |Ctrl和*:恢复原图 <span></center></font>
</div>
<div id="divPages1"style="display:'';" >
	<Input class="common"  name=page > 共<span id=AllPage></span>页<br>
	<INPUT VALUE="转 到" class = CssButton TYPE=button onclick="turnpage();">
</div>
<hr class=line>

<div id="divPages" style="display:none;position:absolute;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=0, xray=0, mirror=0, invert=0, opacity=1, rotation=0)">
	<img galleryImg="no" border="0" id="service" src="">
</div>

</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
