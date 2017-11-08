<%
/***************************************************************
 * <p>ProName：LLClaimControlImpInput.jsp</p>
 * <p>Title：理赔责任控制信息导入</p>
 * <p>Description：理赔责任控制信息导入</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 李肖峰
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String mMode = request.getParameter("Mode");
	String mGrpContNo = request.getParameter("GrpContNo");
%>
<script>	
	var mManageCom = "<%=tGI.ManageCom%>";
	var mOperator = "<%=tGI.Operator%>";
	var mMode = "<%=mMode%>";
	var mGrpContNo = "<%=mGrpContNo%>";	
</script>
<html>
<head>
	<title>理赔责任控制信息导入</title>
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
	<script src="./LLClaimControlImpInput.js"></script>
	<%@include file="./LLClaimControlImpInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">

	
<form action="./" method=post name=uploadfm id=fm target="fraSubmit" ENCTYPE="multipart/form-data">
		
	<input type=hidden name=ImpOperate>
	<input type=hidden name=ImpGrpContNo>
	<table>
		<tr>
			<td class=common><IMG src="../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divImportGrid);"></ td>	
			<td class=titleImg>理赔责任控制信息导入</td>
		</tr>
	</table>
	<div id="divImportGrid" class=maxbox1 style="text-align:left;">
		<table class=common>
			<tr class=common>
				<td>附件上载
					<input class=common name=UploadPath  type="file" style="width:380px" >
					<input class=cssButton type=button value="导  入" onclick="submitImport();"/>
					<input class=cssButton type=button value="导入模板下载" onclick="getImportForm();"/>
					<input class=cssButton type=button value="关  闭" onclick="top.close();"/>
				</td>
			</tr>
		</table>
	</div>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>

