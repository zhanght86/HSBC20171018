<%
/***************************************************************
 * <p>ProName��ScanPagesQueryInput.jsp</p>
 * <p>Title��Ӱ�����ѯ</p>
 * <p>Description��Ӱ�����ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
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
<title>Ӱ�����ѯ</title>
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
			<td class="titleImg">ɨ�����Ϣ</td>
		</tr>
	</table>
	<div class=maxbox1>
	<input class="cssButton" type="button" onclick="scanQuery();" value="��  ѯ">
	<input class="cssButton" type="button" onclick="top.close();" value="��  ��">
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
			<input value="��  ҳ" class="cssButton90" type="button" onclick="turnPage1.firstPage();">
			<input value="��һҳ" class="cssButton91" type="button" onclick="turnPage1.previousPage();">
			<input value="��һҳ" class="cssButton92" type="button" onclick="turnPage1.nextPage();">
			<input value="β  ҳ" class="cssButton93" type="button" onclick="turnPage1.lastPage();">
		</center>
		<font color=red style="font-size:9pt;"><center id="centerPic2" class=common><span>PageDown:��һҳ | PageUp:��һҳ | Ctrl��Alt��+:�Ŵ�ͼƬ | Ctrl��Alt��-:��СͼƬ| End:��תͼƬ |Ctrl��*:�ָ�ԭͼ <span></center></font>
</div>
<div id="divPages1"style="display:'';" >
	<Input class="common"  name=page > ��<span id=AllPage></span>ҳ<br>
	<INPUT VALUE="ת ��" class = CssButton TYPE=button onclick="turnpage();">
</div>
<hr class=line>

<div id="divPages" style="display:none;position:absolute;filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=0, xray=0, mirror=0, invert=0, opacity=1, rotation=0)">
	<img galleryImg="no" border="0" id="service" src="">
</div>

</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
