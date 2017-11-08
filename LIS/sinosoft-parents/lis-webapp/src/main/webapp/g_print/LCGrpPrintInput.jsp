<%
/***************************************************************
 * <p>ProName：LCGrpPrintInput.jsp</p>
 * <p>Title：团体保单打印</p>
 * <p>Description：团体保单打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<html>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<script type="text/javascript">
	var tManageCom = "<%= tGI.ManageCom %>";
</script>
<head >
	<title>团体保单打印</title>
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
	<script src="LCGrpPrintInput.js"></script>
	<%@include file="LCGrpPrintInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
		 	<td class=titleImg>查询条件</td>
		</tr>
	</table>
	
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>承保机构</td>
				<td class=input><input class=codeno name=ManageCom verify="承保机构|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick=" showCodeList('managecom',[this,ManageComName],[0,1],null,null,null,1);" 
					onkeyup="return showCodeListKey('managecom',[this,ManageComName],[0,1],null,null,null,1);"><input class=codename name=ManageComName elementtype=nacessary readonly=true></td>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=GrpContNo maxlength=20></td>
				<td class=title>投保单号</td>
				<td class=input><input class="wid common" name=GrpPropNo maxlength=20></td>
			</tr>
			<tr class=common>
				<td class=title>销售渠道</td>
				<td class=input><input class=codeno name=SaleChnl style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick=" showCodeList('agenttype',[this,SaleChnlName],[0,1],null,null,null,1);" 
					onkeyup="return showCodeListKey('agenttype',[this,SaleChnlName],[0,1],null,null,null,1);"><input class=codename name=SaleChnlName readonly=true></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
	</div>
	
	<input class=cssButton type=button value="查询保单" onclick="queryClick(1)">
		
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divContInfoGrid);">
			</td>
			<td class=titleImg>保单信息</td>
		</tr>
	</table>
	<div id="divContInfoGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanContInfoGrid" ></span> </td>
			</tr>
		</table>
		<div id= "divContInfoGridPage" style= "display: ;text-align:center">
			<table align=center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			</table>
		</div>
	</div>
	<input class=cssButton type=button value="打印保单" name='printButton' onclick="print();">
	<!--隐藏区-->
	<input type=hidden name=Operate>
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
