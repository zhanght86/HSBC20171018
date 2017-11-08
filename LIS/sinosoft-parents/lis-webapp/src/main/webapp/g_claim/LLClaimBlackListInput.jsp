<%
/***************************************************************
 * <p>ProName：LLClaimBlackListInput.jsp</p>
 * <p>Title：黑名单管理</p>
 * <p>Description：黑名单管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String mGrpRegisterNo = request.getParameter("GrpRgtNo");	
	String mMode = request.getParameter("Mode");	
	String mClmState = request.getParameter("ClmState");	
%>
<script>	
	var mGrpRegisterNo = "<%=mGrpRegisterNo%>";
	var mMode = "<%=mMode%>";
	var mClmState = "<%=mClmState%>";
	var mManageCom = "<%=tGI.ManageCom%>";
	var mOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>黑名单管理</title>
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
	<script src="./LLClaimBlackListInput.js"></script>
	<%@include file="./LLClaimBlackListInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimBlackListSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divMainCase);">
			</td>
			<td class=titleImg>客户信息列表</td>
		</tr>
	</table>
	<div id="divMainCase" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanCustomerListGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input value="首  页" class=cssButton90 type=button onclick="turnPage1.firstPage();">
			<input value="上一页" class=cssButton91 type=button onclick="turnPage1.previousPage();">
			<input value="下一页" class=cssButton92 type=button onclick="turnPage1.nextPage();">
			<input value="尾  页" class=cssButton93 type=button onclick="turnPage1.lastPage();">
		</center>		
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAuditConclusion);">
			</td>
			<td class=titleImg>客户状态调整</td>
		</tr>
	</table>	
	<div id=divAuditConclusion class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common id=confirmReason style="display:''">
				<td class=title>调整原因</td>
				<td class=input><input class=codeno name=AdjustReason style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('claimrule',[this,AdjustReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('claimrule',[this,AdjustReasonName],[0,1],null,null,null,1);" readonly><input class=codename name=AdjustReasonName readonly elementtype=nacessary></td>				
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common id=releaseReason style="display:none">
				<td class=title>调整原因</td>
				<td class=input><input class=codeno name=AdjustReason1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('blackadjust',[this,AdjustReasonName1],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('blackadjust',[this,AdjustReasonName1],[0,1],null,null,null,1);" readonly><input class=codename name=AdjustReasonName1 readonly elementtype=nacessary></td>				
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>			
			<tr class=common>
				<td class=title>原因描述</td>
				<td class=input colspan="5"><textarea name=AdjustRemark id=AdjustRemark cols="60" rows="3" verify="原因描述|NOTNULL" class=common elementtype=nacessary maxlength=2000></textarea></td>
			</tr>
		</table>
		<input class=cssButton  value="黑名单确认" type=button id=blackConform onclick="conformClick();">
		<input class=cssButton  value="黑名单取消" type=button id=blackRelase onclick="releaseClick();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLCQues2);">
			</td>
			<td class=titleImg>客户状态列表</td>
		</tr>
	</table>
	<div id="divLCQues2" style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanCustomerStateListGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input value="首  页" class=cssButton90 type=button onclick="turnPage2.firstPage();">
			<input value="上一页" class=cssButton91 type=button onclick="turnPage2.previousPage();">
			<input value="下一页" class=cssButton92 type=button onclick="turnPage2.nextPage();">
			<input value="尾  页" class=cssButton93 type=button onclick="turnPage2.lastPage();">
		</center>
	</div>
	<Input type=hidden  name=Operate>			<!--操作类型-->
	<Input type=hidden  name=RgtNo>				<!--个人案件号-->
	<Input type=hidden  name=CustomerNo>			<!--个人客户号-->
	<Input type=hidden  name=State>			<!--个人客户号-->
	<input class=cssButton name=goBack value="关  闭" type=button onclick="top.opener.initForm();top.close();">
	<br ><br ><br ><br >
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
