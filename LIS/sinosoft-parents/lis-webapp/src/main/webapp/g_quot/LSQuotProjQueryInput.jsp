<%
/***************************************************************
 * <p>ProName：LSQuotProjQueryInput.jsp</p>
 * <p>Title：项目询价录入查询</p>
 * <p>Description：项目询价录入查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.SQLwithBindVariables"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tOperator = tGI.Operator;
	String str = "select a.comgrade from ldcom a where 1=1 and a.comcode='"+"?ManageCom?"+"'";
	SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	sqlbv1.sql(str);
	sqlbv1.put("ManageCom",tGI.ManageCom);
	ExeSQL	tExeSQL = new ExeSQL();
	String tComGrade = tExeSQL.getOneValue(sqlbv1);
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";//记录登陆机构
	var tOperator = "<%=tOperator%>";//当前登录人
	var tComGrade = "<%=tComGrade%>";
	var tMissionID = "";
	var tActivityID = "";
	var tSubMissionID = "";
	var tQuotNo = "";
	var tQuotBatNo = "";
</script>
<html>
<head>
	<title>项目询价录入查询</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LSQuotProjQueryInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script><!-- 公共方法 -->
	<%@include file="./LSQuotProjQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- 待录入询价查询条件 -->
<form name=fm id=fm method=post action="./LSQuotProjQuerySave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>待录入询价查询条件</td>
		</tr>
	</table>
	<div id="divInfo1" class=maxbox1  style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>项目名称</td> 
				<td class=input><input class="wid common" name=ProjName id=ProjName></td>
				<td class=title>询价号</td>
				<td class=input><input class="wid common" name=PIQuotNo id=PIQuotNo></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryPIClick('1');">
		<input class=cssButton type=button value="申  请" onclick="applyPIClick();">
	</div>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult1);">
			</td>
			<td class=titleImg>待录入询价信息列表</td>
		</tr>
	</table>
	<div id="divResult1"  style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPIQuotInfoGrid"></span>
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
	<input type=hidden name=Operate>
</form>
<!-- 已完成询价查询条件 -->
<form name=fm1 id=fm1 method=post action="./LSQuotProjQuerySave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>已完成询价查询条件</td>
		</tr>
	</table>
	<div id="divInfo2" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>项目名称</td>
				<td class=input><input class="wid common" name=FProjName id=FProjName></td>
				<td class=title>询价号</td>
				<td class=input><input class="wid common" name=FPIQuotNo id=FPIQuotNo></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryFinishClick();">
		<input class=cssButton type=button value="明细查看" onclick="toPalnDetailView();">
		<input class=cssButton type=button value="再次询价" onclick="againProjClick();">
	</div>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult2);">
			</td>
			<td class=titleImg>已完成询价信息列表</td>
		</tr>
	</table>
	<div id="divResult2"  style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanFinishQuotInfoGrid"></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
		</center>
	</div>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate id=Operate>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
