<%
/***************************************************************
 * <p>ProName:EdorCTInput.jsp</p>
 * <p>Title:  整单退保</p>
 * <p>Description:整单退保</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorType = request.getParameter("EdorType");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorNo =  request.getParameter("EdorNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo =  "<%=tEdorNo%>";
</script>
<html>
<head>
	<title>整单退保</title>
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
	<script src="./EdorCTInput.js"></script>
	<%@include file="./EdorCTInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>整单退保</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>退保方式</td>
				<td class=input><input class=codeno name=BackType id=BackType readonly verify="退保方式|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('queryexp',[this, BackTypeName],[0, 1],null,'surrenderCT','concat(codetype,codeexp)','1',180);" 
					onkeyup="return showCodeListKey('queryexp',[this, BackTypeName],[0, 1],null,'surrenderCT','concat(codetype,codeexp)','1',180);"><input class=codename name=BackTypeName id=BackTypeName elementtype=nacessary></td>
				<td class=title ><div id=divCalName style="display:none" >退保算法</div></td>
				<td class=input ><div id=divCalCode style="display:none"><input class=codeno name=BackCal id=BackCal readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('edorcalcode',[this, BackCalName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('edorcalcode',[this, BackCalName],[0, 1],null,null,null,'1',180);"><input class=codename name=BackCalName id=BackCalName elementtype=nacessary></div></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>退保原因描述</td>
				<td class=input colspan=5><textarea cols=50 rows=3 name=ReasonDesc id=ReasonDesc verify="退保原因描述|NOTNULL" elementtype=nacessary></textarea></td>
			</tr>
		</table>
		<div id="divButton01" style="display: none">
			<input class=cssButton type=button value="保  存" onclick="saveClick();">
			<input class=cssButton type=button value="关  闭" onclick="top.close();">
		</div>
		<div id="divButton02" style="display: none">
			<input class=cssButton type=button value="关  闭" onclick="top.close();">
		</div>
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divInsuredInfoUp);">
			</td>
			<td class=titleImg>退保被保险人查询条件</td>
		</tr>
	</table>
	<div id="divInsuredInfoUp" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>被保险人姓名</td>
				<td class=input><input class="wid common" id=InsuredName name=InsuredName></td>
				<td class=title>证件号码</td>
				<td class=input><input class="wid common" id=InsuredIDNo name=InsuredIDNo></td>
				<td class=title>被保险人客户号</td>
				<td class=input><input class="wid common" id=InsuredNo name=InsuredNo></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryClick();">		
	</div>
	<br>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResultUpdate);">
			</td>
			<td class=titleImg>退保被保险人信息</td>
		</tr>
	</table>
	<div id="divResultUpdate" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanUpdateInsuredInfoGrid" ></span>
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
	
	<!--隐藏区-->
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=GrpContNo id=GrpContNo>
	<input type=hidden name=CurrentDate id=CurrentDate>
	<input type=hidden name=EdorAppNo id=EdorAppNo>
	<input type=hidden name=EdorNo id=EdorNo>
	<input type=hidden name=MissionID id=MissionID> <!-- 工作任务ID -->
	<input type=hidden name=SubMissionID id=SubMissionID> <!-- 子工作任务ID -->
	<input type=hidden name=ActivityID id=ActivityID> <!-- 工作节点ID -->
<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
