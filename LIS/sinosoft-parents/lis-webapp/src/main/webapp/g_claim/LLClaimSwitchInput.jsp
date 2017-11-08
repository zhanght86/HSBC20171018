<%
/***************************************************************
 * <p>ProName：LLClaimSwitchInput.jsp</p>
 * <p>Title：暂停或启动案件</p>
 * <p>Description：暂停或启动案件</p>
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
	String mCurrentDate = PubFun.getCurrentDate();
	String mRgtNo = request.getParameter("RgtNo");	
	String mCustomerNo = request.getParameter("CustomerNo");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var mOperator = "<%=tGI.Operator%>";
	var mCurrentDate = "<%=mCurrentDate%>";
	var mRgtNo = "<%=mRgtNo%>";
	var mCustomerNo = "<%=mCustomerNo%>";
</script>
<html>
<head>
	<title>暂停或启动案件</title>
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
	<script src="./LLClaimSwitchInput.js"></script>
	<%@include file="./LLClaimSwitchInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form name=fm id=fm method=post action="LLClaimSwitchSave.jsp" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLCQues);">
				</td>
				<td class=titleImg>案件信息列表</td>
			</tr>
		</table>
		<div id="divLCQues" style="display:''">
  		<table class=common>
				<tr>
					<td text-align: left colSpan=1>
						<span id="spanClaimCaseGrid"></span>
					</td>
				</tr>
			</table> 
		</div>
		<div id=divRemarkInfo style="display:none">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAuditConclusion);">
					</td>
					<td class=titleImg>暂停或启动案件</td>
				</tr>
			</table>
			<div id="divAuditConclusion" style="display:''">
				<table class=common>
					<tr class=common id=Pause style="display:''">
						<td class=title>暂停案件原因</td>
						<td class=input><input class=codeno name=PauseReason style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('pausereason',[this,PauseReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('pausereason',[this,PauseReasonName],[0,1],null,null,null,1);"><input class=codename name=PauseReasonName readonly elementtype=nacessary style="width: 250px;"></td>
						<td class=title></td>
			     	<td class=input></td>
			     	
					</tr>			
					<tr class=common id=Remark>
						<td class=title>原因描述</td>
						<td class=input colspan="5"><textarea name=ReasonDesc id=ReasonDesc cols="60" rows="3" class=common elementtype=nacessary></textarea></td>
					</tr>
				</table>
			</div>
			<input class=cssButton name=RgtPause value="暂停案件" type=button onclick="pauseRgt();">
			<input class=cssButton name=RgtOpen value="启动案件" type=button onclick="openRgt();">			
		</div>
				
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLCQues2);">
				</td>
				<td class=titleImg>暂停案件轨迹列表</td>
			</tr>
		</table>
		<div id="divLCQues2" style="display:''">
  		<table class=common>
				<tr>
					<td text-align: left colSpan=1>
						<span id="spanClaimCaseTraceGrid"></span>
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
		<br/>
		<input class=cssButton name=goBack value="关  闭" type=button onclick="closeClick();">
   	   	   	
   	<input type=hidden name=RgtNo>				
		<input type=hidden name=CustomerNo>		
		<input type=hidden name=Operate>			<!--操作类型-->
		
		<br /><br /><br /><br />
		</form>
		<span id="spanCode" style="display: none; position:absolute; slategray"></span>
	</body>
</html>