<%
/***************************************************************
 * <p>ProName：LLClaimNoAcceptInput.jsp</p>
 * <p>Title：未受理客户查询</p>
 * <p>Description：未受理客户查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
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
	String mCurrentDate = PubFun.getCurrentDate();
	String mGrpRgtNo = request.getParameter("GrpRgtNo");
	String mMode = request.getParameter("Mode");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	
	var mOperator = "<%=tGI.Operator%>";
	
	var mCurrentDate = "<%=mCurrentDate%>";
	var mGrpRgtNo = "<%=mGrpRgtNo%>";	
	var mMode = "<%=mMode%>";	
</script>
<html>
<head>
	<title>受理登记录入界面</title>
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
	<script src="./LLClaimNoAcceptInput.js"></script>
	<%@include file="./LLClaimNoAcceptInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimNoAcceptSave.jsp" target=fraSubmit>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divNoAcceptList);">
			</td>
			<td class=titleImg>未受理客户信息列表</td>
		</tr>
	</table>
	<div id="divNoAcceptList" style="display: ''">
		<table class=commontop>
			<tr class=commontop>
				<td text-align: left colSpan=1>
					<span id="spanNoAcceptListGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="导出数据" onclick="exportData();">
		</center>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divNoAcceptInfo);">
			</td>
			<td class=titleImg>未受理客户信息</td>
		</tr>
	</table>
	<div id="divNoAcceptInfo" class=maxbox1 style="display: ''">
		<input class=cssButton name=addClickButton value="新  增" type=button onclick="addClick();">
		<input class=cssButton name=modifyClickButton value="修  改" type=button onclick="modifyClick();">
		<input class=cssButton name=deleteClickButton value="删  除" type=button onclick="deleteClick();">
		<input class=cssButton name=deleteAllButton value="删除全部未受理客户" type=button onclick="deleteAll();">		
		<table class=common> 
			<tr class=common>
				<td class=title>姓名</td>
				<td class=input><input class="wid common" name=CustomerName verify="姓名|NOTNULL" elementtype=nacessary maxlength=50></td>
				<td class=title>性别</td>
				<td class=input><input class=codeno name=Gender verify="性别|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('sex',[this,GenderName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('sex',[this,GenderName],[0,1],null,null,null,'1',null);" maxlength=1 readonly><input class=codename name=GenderName  elementtype=nacessary readonly></td>  
				<td class=title>出生日期</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=Birthday verify="出生日期|NOTNULL" elementtype=nacessary onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>证件类型</td>
				<td class=input><input class=codeno name=IDType verify="证件类型|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" maxlength=50 readonly><input class=codename name=IDTypeName maxlength=2  elementtype=nacessary readonly></td>				
				<td class=title>证件号码</td>
				<td class=input><Input class="wid common" name=IDNo verify="证件号码|NOTNULL" onblur="analysisIDNo(this);" elementtype=nacessary maxlength=20>
				<td class=title></td>
				<td class=input></td>  
			</tr>
			<tr class=common>
				<td class=title>申请金额</td>
				<td class=input><input class="wid common" name=AppAmnt elementtype=nacessary verify="申请金额|notnull&num&value>0"></td>
				<td class=title>发票数</td>
				<td class=input><input class="wid common" name=BillCount elementtype=nacessary verify="发票数|notnull&INT&value>=1&value<=10000"></td>
				<td class=title>影像件数</td>
				<td class=input><input class="wid common" name=ScanCount verify="影像件数数|INT&value>=0&value<=10000"></td>
			</tr>
			<tr class=common>
				<td class=title>未受理原因</td>				
				<td class=input colspan="5"><textarea class=common name=NoAcceptReasonName verify="未受理原因|NOTNULL"  ondblclick="return showCodeList('noaceeptreason',[this,NoAcceptReason],[1,0],null,null,null,'1',300);" cols="50" rows="3" maxlength=200  style = {background:lightblue}></textarea><input type=hidden name=NoAcceptReason></td>
			</tr>			
		</table>
	</div>
	<input class=cssButton name='Report' value="关  闭" type=button onclick="goBack();">	
	<Input type=hidden  name=Operate>				<!--操作类型-->
	<input type=hidden name=GrpRgtNo>				<!--团体批次号-->
	<input type=hidden name=CustomerNo>			<!--团体批次号-->	
	<input type=hidden name=RgtNo>			<!--团体批次号-->	
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>

