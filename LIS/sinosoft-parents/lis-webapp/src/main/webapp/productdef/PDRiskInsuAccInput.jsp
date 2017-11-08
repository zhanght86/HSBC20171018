<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//程序名称：PDRiskInsuAccInput.jsp
		//程序功能：险种账户定义
		//创建日期：2009-3-12
		//创建人  ：
		//更新记录：  更新人    更新日期     更新原因/内容
	%>

	<head>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="buttonshow.jsp"%>
		<SCRIPT src="PDRiskInsuAcc.js"></SCRIPT>
		<%@include file="PDRiskInsuAccInit.jsp"%>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
		<form action="./PDRiskInsuAccSave.jsp" method=post name=fm
			target="fraSubmit">

			<table>
				<tr>
					<td class="titleImg">
险种已有账户
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline9Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button
				onclick="Mulline9GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button
				onclick="Mulline9GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button
				onclick="Mulline9GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button
				onclick="Mulline9GridTurnPage.lastPage();">
			</BR>
			<br>
			<!-- <input id=savabutton4 type=button class=cssbutton value="保证利率录入"
				onclick="setGuarInteRate()"> -->
			<br>
			</BR>
			<table>
				<tr>
					<td class="titleImg">
账户属性定义：
					</td>
				</tr>
			</table>
			<table class=common>
				<tr>
					
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline10Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button
				onclick="Mulline10GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button
				onclick="Mulline10GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button
				onclick="Mulline10GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button
				onclick="Mulline10GridTurnPage.lastPage();">
			</BR>
			<TD>
				<input id=savabutton1 value="新  增" type=button onclick="save()"
					class="cssButton" type="button">
				<input id=savabutton2 value="修  改" type=button onclick="update()"
					class="cssButton" type="button">
				<input id=savabutton3 value="删  除" type=button onclick="del()" class="cssButton" type="button">
				
				<input id=savabutton4 value="基金关联关系删除" type=button onclick="delrel()" class="cssButton" type="button">
				<input value="返  回" type=button onclick="returnParent( )" class="cssButton" type="button">
			</td>
			</BR>
			<br>
			<br>
			<input type=hidden name="RiskCode">
			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LMRiskInsuAcc">
			<input type=hidden name="tableName2" value="PD_LMRiskToAcc">
			<input type=hidden name=IsReadOnly>
		</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
