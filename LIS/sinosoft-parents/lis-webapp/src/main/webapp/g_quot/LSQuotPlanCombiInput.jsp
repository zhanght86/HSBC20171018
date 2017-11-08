<%
/***************************************************************
 * <p>ProName：LSQuotPlanCombiInput.jsp</p>
 * <p>Title：方案组合配置</p>
 * <p>Description：方案组合配置</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-02
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tOperator = tGI.Operator;
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tOperator = "<%=tOperator%>";
</script>
<html>
<head >
	<title>方案组合配置</title>
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
	<script src="./LSQuotPlanCombiInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSQuotPlanCombiInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotPlanCombiSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divNoPlanCombi);">
			</td>
			<td class=titleImg><span style="color: red">方案组合限制</span></td>
		</tr>
	</table>
	
	<div id="divNoPlanCombi"  style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanNoPlanCombiGrid" ></span>
				</td>
			</tr>
		</table>

		<div id="divTurnPage" style="display: ''">
			<center>		
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			</center>
		</div>
		<input class=cssButton type=button value="删  除" name="DelButton" id=DelButton onclick="deleteClick();">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlanList);">
			</td>
			<td class=titleImg>方案列表</td>
		</tr>
	</table>
	
	<div id="divPlanList" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>限制范围</td>
				<td class=input><input class=codeno name=LimitType id=LimitType verify="限制范围|NOTNULL" ondblclick="return showCodeList('limittype',[this,LimitTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('limittype',[this,LimitTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=LimitTypeName id=LimitTypeName elementtype=nacessary></td>
				<td class=title>组合类型</td>
				<td class=input><input class=codeno name=CombiType id=CombiType verify="组合类型|NOTNULL" ondblclick="return showCodeList('combitype', [this,CombiTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('combitype', [this,CombiTypeName],[0,1],null,null,null,'1',null);" readonly><input class=codename name=CombiTypeName id=CombiTypeName elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="新  增" name="AddButton" id=AddButton onclick="addClick();">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPlanListGrid" ></span>
				</td>
			</tr>
		</table>
	</div>
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
