<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
	String flag=request.getParameter("flag");
		//程序名称：PDSaleControlInput.jsp
		//程序功能：险种销售控制定义
		//创建日期：2009-3-17
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
		<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
		<%@include file="buttonshow.jsp"%> 
		<SCRIPT src="PDSaleControl.js"></SCRIPT>
		<%@include file="PDSaleControlInit.jsp"%>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./PDSaleControlSave.jsp?flag=<%=flag %>" method=post name=fm target="fraSubmit">
			<table class=common>
				<tr class=common>
					<td class=title5>
险种代码
					</td>
					<td class=input5>
						<input class=common name="RiskCode" readonly="readonly">
					</td>
				</tr>
			</table>
			<br>
			<table>
				<tr>
					<td class="titleImg">
险种销售控制定义
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline9Grid" > </span>
					</td>
				</tr>
			</table>
			</BR>
			<div align=left id=savabuttonid>
				<input value="保  存" type=button name=btnSave onclick="save(<%=flag%>)"
					class="cssButton">
				<!-- input value="修  改" type=button name=btnEdit onclick="update()" class="cssButton" -->
				<input value="删  除" type=button name=btnDele onclick="del(<%=flag%>)"
					class="cssButton">
			</div>
			<br>
			<table>
				<tr>
					<td class="titleImg">
已保存的险种销售控制
					</td>
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
			</BR>
			<input value="返  回" type=button onclick="returnParent( )"
				class="cssButton" type="button">
			<br>
			<br>

			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LMRiskComCtrl">
			<input type=hidden name=IsReadOnly>
		</form>

		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>
