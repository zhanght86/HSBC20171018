<%
/***************************************************************
 * <p>ProName：LDUWPopedomInput.jsp</p>
 * <p>Title：核保权限管理</p>
 * <p>Description：核保权限管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tOperator = tGI.Operator;
	String tManageCom = tGI.ManageCom;
%>
<script>
	var tOperator = "<%=tOperator%>";
	var tManageCom = "<%=tManageCom%>";
</script>
<html>
<head>
	<title>核保权限管理</title>
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
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LDUWPopedomInput.js"></script>
	<%@include file="./LDUWPopedomInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div class="tablist block">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWPopedom);">
				</td>
				<td class=titleImg>查询条件</td>
			</tr>
		</table>
		<div id="divUWPopedom" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>权限级别</td>
					<td class=input><input class="wid common" name=PopedomLevel1 id=PopedomLevel1></td>
					<td class=title>权限名称</td>
					<td class=input><input class="wid common" name=PopedomName1 id=PopedomName1 ></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<input class=cssButton type=button value="查  询" onclick="querySubmit();">
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUWPopedomList);">
				</td>
				<td class=titleImg>核保权限列表</td>
			</tr>
		</table>
		<div id="divUWPopedomList" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanUWPopedomGrid"></span>
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
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUWUserInfo);">
				</td>
				<td class=titleImg>核保权限信息</td>
			</tr>
		</table>
		<div id="divUWUserInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>权限级别 <span style="color: red">【如：L01】</span></td>
					<td class=input><input class="wid common" name=PopedomLevel verify="权限级别|NOTNULL" elementtype=nacessary></td>
					<td class=title>权限名称</td>
					<td class=input><input class="wid common" name=PopedomName verify="权限名称|NOTNULL" elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>				
				</tr>
				<tr class=common>
					<td class=title>个人寿险保额</td>
					<td class=input><input class="wid common" name=PerLifeAmnt verify="个人寿险保额|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>	
					<td class=title>个人意外险保额</td>
					<td class=input><input class="wid common" name=PerAcciAmnt verify="个人意外险保额|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>
					<td class=title>个人重疾保额</td>
					<td class=input><input class="wid common" name=PerIllAmnt verify="个人重疾保额|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>个人医疗险保额</td>
					<td class=input><input class="wid common" name=PerMedAmnt verify="个人医疗险保额|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>	
					<td class=title>保费规模</td>
					<td class=input><input class="wid common" name=PremScale verify="保费规模|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>
					<td class=title>主险费率浮动</td>
					<td class=input><input class="wid common" name=MainPremRateFloat verify="主险费率浮动|NOTNULL&NUM" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>医疗险费率浮动</td>
					<td class=input><input class="wid common" name=MedPremRateFloat verify="医疗险费率浮动|NOTNULL&NUM" elementtype=nacessary></td>	
					<td class=title>生效日期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=ValDate verify="生效日期|NOTNULL&DATE" elementtype=nacessary onClick="laydate({elem: '#ValDate'});" id="ValDate"><span class="icon"><a onClick="laydate({elem: '#ValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>终止日期</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=EndDate verify="终止日期|NOTNULL&DATE" elementtype=nacessary onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
			</table>
			<input class=cssButton type=button value="新  增" name="AddButton" onclick="addSubmit();">
			<input class=cssButton type=button value="修  改" name="ModButton" onclick="modSubmit();">
			<input class=cssButton type=button value="删  除" name="DelButton" onclick="delSubmit();">
		</div>
	</div>
	<input type=hidden name=Operate>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
