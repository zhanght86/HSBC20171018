<%
/***************************************************************
 * <p>ProName：LDWorkFlowInput.jsp</p>
 * <p>Title：工作流驱动</p>
 * <p>Description：工作流驱动</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2015-11-09
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head >
	<title>工作流驱动</title>
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
	<script src="./LDWorkFlowInput.js"></script>
	<%@include file="./LDWorkFlowInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>工作流驱动</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>扫描机构</td>
				<td class=input><input class=codeno name=ScanManageCom id=ScanManageCom verify="扫描机构|notnull" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
					ondblclick="return showCodeList('comcodeall',[this, ScanManageComName],[0, 1],null,'1 and comgrade=#03#','1','1',180);" 
					onkeyup="return showCodeListKey('comcodeall',[this, ScanManageComName],[0, 1],null,'1 and comgrade=#03#','1','1',180);"><input class=codename name=ScanManageComName readonly elementtype=nacessary></td>
				<td class=title>业务类型</td>
				<td class=input><input class=codeno name=BussType verify="业务类型|notnull" readonly  style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('esbusstype',[this, BussTypeName],[0, 1],null,'1 and busstype like #G_%#','1','1',180);" 
					onkeyup="return showCodeListKey('esbusstype',[this, BussTypeName],[0, 1],null,'1 and busstype like #G_%#','1','1',180);"><input class=codename name=BussTypeName readonly elementtype=nacessary></td>
				<td class=title>业务细类</td>
				<td class=input><input class=codeno name=SubType verify="业务细类|notnull" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return returnShowCodeList('subtypedetail',[this, SubTypeName],[0, 1]);" 
					onkeyup="return returnShowCodeList('subtypedetail',[this, SubTypeName],[0, 1]);"><input class=codename name=SubTypeName readonly elementtype=nacessary></td>
			</tr>
			<tr class=common id="DivDocCode" style="display: 'none'">
				<td class=title id=id1 style="display: none">投保书类型</td>
				<td class=input id=id2 style="display: none"><input class=codeno name=PropType readonly  style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('proptype',[this, PropTypeName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('proptype',[this, PropTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=PropTypeName readonly></td>
				<td class=title id=id3 style="display: none">业务号</td>
				<td class=input id=id4 style="display: none"><input class="wid common" name=DocCode id=DocCode></td>
				<td class=title id=id5 style="display: none"></td>
				<td class=input id=id6 style="display: none"></td>
				<td class=title id=id7 style="display: none"></td>
				<td class=input id=id8 style="display: none"></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton type=button value="工作流驱动" onclick="workFlowClick();">
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
