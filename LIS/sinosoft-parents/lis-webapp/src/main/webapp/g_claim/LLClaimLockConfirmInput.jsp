<%
/***************************************************************
 * <p>ProName：LLClaimLockConfirmInput.jsp</p>
 * <p>Title：立案解锁确认</p>
 * <p>Description：立案解锁确认</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>立案解锁确认</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<Link href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LLClaimLockConfirmInput.js"></script>
	<%@include file="./LLClaimLockConfirmInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimLockConfirmSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divSearch);"></ td>
			<td class=titleImg>查询条件</ td>
		</tr>
  </table>
	<div id=QueryInfo class=maxbox1>
		<table class=common>		
			<tr class=common>
				<td class=title>保单号</td>
    		<td class=input><input class="wid common" name=GrpContNo></td>
    		<td class=title>投保人名称</td>
    		<td class=input><input class="wid common" name=AppntName></td>
				<td class=title>管理机构</td>    		
				<td class= input><input class=codeno  name=SignCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,SignComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,SignComName],[0,1],null,null,null,1);"><input class=codename name=SignComName ></td>    		
			</tr>
    	<tr class=common>
    		<td class=title>姓名</td>
    		<td class=input><input class="wid common" name=CustomerName></td>
    		<td class=title>证件号码</td>
    		<td class=input><input class="wid common" name=IdNo></td>
    		<td class=title>出生日期</td>
    		<td class=input><input class=coolDatePicker dateFormat=short name=BirthDay onClick="laydate({elem: '#BirthDay'});" id="BirthDay"><span class="icon"><a onClick="laydate({elem: '#BirthDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
    	</tr>
    	<tr class=common>
    		<td class=title>客户号</td>
    		<td class=input><input class="wid common" name=CustomerNo></td>
    		<td class=title></td>
    		<td class=input></td>
    		<td class=title></td>
    		<td class=input></td>
    	</tr>    	
    </table>
	</div>
	<input class=cssButton type=button value="查  询" onclick="queryClick();">
	<input class=cssButton type=button value="重  置" onclick="initParam();">
	<table>
		<tr>
			<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divSearch);"></ td>
			<td class= titleImg>解锁申请信息</ td>
		</tr>
	</table>
	<div id="divSearch" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanAppLockGrid"></span>
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
  <div id=QueryInfo1>
		<table class=common>
    	<tr class=common>
    		<td class=title>审核结论</td>
    		<td class=input><input class=codeno name=AuditConlusion style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('lluwconclusion',[this,AuditConlusionName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('lluwconclusion',[this,AuditConlusionName],[0,1],null,null,null,1);"><input class=codename name=AuditConlusionName readonly=true  elementtype=nacessary></td> 
    		<td class=title></td>
    		<td class=input></td>
    		<td class=title></td>
    		<td class=input></td>
			</tr>
    	<tr class=common>
				<td class=title>审核意见<font color="#FF0000"><b>*</b></font></td>
				<td class=input colspan="5"><textarea class=common name=AuditReason id=AuditReason cols="50" rows="2" maxlength=200></textarea>
			</tr>
		</table>
	</div>
  <input class=cssButton type=button value="确认解锁" onclick='confirmUnlock();'>
	<!--隐藏表单区域-->	
	<input type=hidden  name=Operate > 
	<input type=hidden  name=LockNo > 
<br />	<br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
