<%@include file="/i18n/language.jsp"%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
	//程序名称：RIProfitLossCalmInput.jsp
	//程序功能：盈余佣金计算
	//创建日期：2011/8/22
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

<SCRIPT src="RIProfitLossCalm.js"></SCRIPT>
<%@include file="RIProfitLossCalmInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
	<form action="./RIProfitLossCalmSave.jsp" method=post name=fm
		target="fraSubmit">

		<table>
			<tr>
				<td class="titleImg">盈余佣金计算</td>
			</tr>
		</table>
		<table class="common">
			<tr class="common">
				<td class="title5">年度</td>
				<td class="input5"><Input class="code" name=CalYear
					verify="年度|NOTNULL&len=4&num"
					ondblclick="showCodeList('riprofityear',[this],[0])"
					onkeyup="showCodeListKey('riprofityear',[this],[0])"
					elementtype=nacessary></td>
				<td class="title5">盈余佣金编码</td>
				<td class="input5"><Input class="codeno" name="RIProfitNo"
					ondblClick="showCodeList('riprofit',[this,RIProfitName,RIcomCode,RIcomName,ContNo,ContName],[0,1,2,3,4,5],null,null,null,1);"
					onkeyup="showCodeListKey('riprofit',[this,RIProfitName,RIcomCode,RIcomName,ContNo,ContName],[0,1,2,3,4,5],null,null,null,1);"
					verify="盈余佣金编码|NOTNULL"><Input class=codename
					name='RIProfitName' elementtype=nacessary></td>
</tr>			<tr class="common">
				<td class="title5">币种</td>
				<td class="input5"><input class="codeno" name="Currency"
					ondblClick="showCodeList('currency',[this,CurrencyName],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey('currency',[this,CurrencyName],[0,1],null,null,null,1);"
					verify="币种|NOTNULL"><Input class=codename
					name='CurrencyName' elementtype=nacessary></td>
				<td class="title5">分保公司</td>
				<td class="input5"><input class="codeno" name="RIcomCode"
					ondblClick="showCodeList(null,[this,null],[0,1],null,null,null,1);"
					onkeyup="showCodeListKey(null,[this,null],[0,1],null,null,null,1);"
					verify="分保公司|NOTNULL"><Input class=codename
					name='RIcomName' elementtype=nacessary></td>
</tr>			<tr class="common">
				<td class="title5">再保合同</td>
				<td class="input5"><input class="codeno" name="ContNo"
					ondblClick="showCodeList(null,[this,null],[0,1],null,null,null,1,260);"
					onkeyup="showCodeListKey(null,[this,null],[0,1],null,null,null,1,260);"
					verify="再保合同|NOTNULL"><Input class=codename name='ContName'
					elementtype=nacessary></td>
				<td class="title5">计算结果</td>
				<td class="input5"><input class="common" name="Result"></td>
			</tr>
		</table>
		<br> <input value="初始化参数" onclick="button140( )"
			class="cssButton" type="button"> <INPUT class=cssButton
			VALUE="查  询" TYPE=Button
			onclick="queryClick();"> <br> <br>
		<table>
			<tr>
				<td class="titleImg">计算参数</td>
			</tr>
		</table>

		<table class=common>
			<tr class=common>
				<td style="text-align:left;" colSpan=1><span id="spanMul10Grid">
				</span></td>
			</tr>
		</table>
		<br> <input value="计算" onclick="button141( )" class="cssButton"
			type="button"> <br> <input type="hidden"
			name="OperateType">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
</body>
</html>
