<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//程序名称：RIProfitLossUWInput.jsp
	//程序功能：盈余佣金审核
	//创建日期：2011/8/18
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

<SCRIPT src="RIProfitLossUW.js"></SCRIPT>
<%@include file="RIProfitLossUWInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIProfitLossUWSave.jsp" method=post name=fm
		target="fraSubmit">
		<table>
			<tr>

				<td class=titleImg>查询条件</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">盈余佣金编码</td>
				<td class="input5"><Input class="codeno" name="RIProfitNo"
					ondblClick="showCodeList('riprofit',[this,RIProfitName,RIcomCode,RIcomName,ContNo,ContName],[0,1,2,3,4,5],null,null,null,1);"
					onkeyup="showCodeListKey('riprofit',[this,RIProfitName,RIcomCode,RIcomName,ContNo,ContName],[0,1,2,3,4,5],null,null,null,1);"
					verify="盈余佣金编码|NOTNULL"><Input class=codename
					name='RIProfitName' elementtype=nacessary></td>
				<td class="title5">再保公司</td>
				<td class="input5"><input class="codeno" name="RIcomCode"
					ondblClick="showCodeList(null,[this,null],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey(null,[this,null],[0,1],null,null,null,1);"
					verify="再保公司|NOTNULL"><Input class=codename
					name='RIcomName' elementtype=nacessary></td>
			</tr>
			<tr class="common">
				<td class="title5">再保合同</td>
				<td class="input5"><input class="codeno" name="ContNo"
					ondblClick="showCodeList(null,[this,null],[0,1],null,null,null,1,260);"
					onkeyup="showCodeListKey(null,[this,null],[0,1],null,null,null,1,260);"
					verify="再保合同|NOTNULL"><Input class=codename name='ContName'
					elementtype=nacessary></td>

				<td class="title5">年度</td>
				<td class="input5"><Input class="code" name=CalYear
					verify="年度|NOTNULL&code:riprofityear"
					ondblclick="showCodeList('riprofityear',[this],[0])"
					onkeyup="showCodeListKey('riprofityear',[this],[0])"
					elementtype=nacessary></td>
			</tr>
		</table>
		<input value="查  询" onclick="queryClick()" class="cssButton"
			type="button">
		<table>
			<tr>

				<td class=titleImg>待审核列表</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul9Grid">
				</span></td>
			</tr>
		</table>
		<div id="divDetail" style="display: ">
			<table>
				<tr>
					<td class="titleImg">明细列表</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1><span id="spanMul10Grid">
					</span></td>
				</tr>
			</table>
		</div>
		<table class="common">
			<tr class="common">
				<td class="title5">审核结论</td>
				<td class="input5"><input class="codeno" name="RILossUWReport"
					ondblclick="return showCodeList('riprolossuw', [this,RILossUWReportName],[0,1],null,null,null,1);"
					onkeyup="return showCodeListKey('riprolossuw', [this,RILossUWReportName],[0,1],null,null,null,1);"
					nextcasing=><input class="codename"
					name="RILossUWReportName"></td>
				<TD class=title5></TD>
				<TD class=input5></TD>
				<TD class=title5></TD>
				<TD class=input5></TD>

			</tr>
		</table>
		<input value="结论保存" onclick="button135( )" class="cssButton"
			type="button"> <br> <input type="hidden"
			name="OperateType">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
