<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//程序名称：RIBsnsBillLockInput.jsp
	//程序功能：数据加锁
	//创建日期：2011/8/11
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<SCRIPT src="RIBsnsBillLock.js"></SCRIPT>
<%@include file="RIBsnsBillLockInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIBsnsBillLockSave.jsp" method=post name=fm
		target="fraSubmit">

		<table>
			<tr>
				<td class="titleImg">操作条件</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">分出责任</td>
				<td class="input5"><input class="codeno" name="AccumulateDefNO"
					ondblclick="return showCodeList('riaccmucode',[this,AccumulateDefName],[0,1]);"
					onkeyup="return showCodeListKey('riaccmucode',[this,AccumulateDefName],[0,1]);"
					verify="分出责任|NOTNULL"><input class="codename"
					name="AccumulateDefName" elementtype=nacessary></td>
				<td class="title5"><div id="divLockDate1"
						style="display:none;">锁定日期</div></td>
				<td class="input5"><div id="divLockDate2"
						style="display:none;">
						<input class="coolDatePicker" onClick="laydate({elem: '#LockDate'});" name="LockDate"
						 id="LockDate">		<span class="icon"><a onClick="laydate({elem: '#LockDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<td class="title5"></td>
				<td class="input5"></td>
			</tr>
		</table>
		<br> <input value="加 锁" onclick="saveLock(1)" class="cssButton"
			type="button" name="lockbutton" style="display:none;"> <input
			value="解 锁 " onclick="saveLock(2)" class="cssButton" type="button"
			name="unlockbutton" style="display:none;"> <INPUT
			class=cssButton VALUE="查 询" TYPE=Button onclick="queryClick();"><br>
		<br>
		<table>
			<tr>
				<td class="titleImg">操作列表</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul10Grid">
				</span></td>
			</tr>
		</table>
		<input type="hidden" name="OperateType" />
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>


