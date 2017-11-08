<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//程序名称：PDDutyGetAliveInput.jsp
		//程序功能：责任给付生存
		//创建日期：2009-3-16
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
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<link rel="stylesheet" type="text/css"
			href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="../common/themes/icon.css">
		<SCRIPT src="PDSugRateType.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<%@include file="PDSugRateTypeInit.jsp"%>

	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
		<form action="./PDSugRateTypeSave.jsp" method=post name=fm target="fraSubmit">
			<table>
				<tr>
					<td class="titleImg">
险种利率管理:
					</td>
				</tr>
			</table>
			<table class=common>

				<tr class=common>
					<td class=title5>
利率代码：
					</td>
					<td class=input5>
						<input class="common" name=ID  verify="利率代码|NOTNUlL&LEN<=10" elementtype="nacessary">
					</td>
					<td class=title5>
利率描述：
					</td>
					<td class=input5>
						<input class="common" name=CONTENT>
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
利率类型：
					</td>
					<td class=input5>
						<input class="common" name=TITTLE  verify="利率类型|NOTNUlL&LEN<=40" elementtype="nacessary">
					</td>
					<td class=title5>
备注信息：
					</td>
					<td class=input5>
						<input class="common" name=BAK1>
					</td>
				</tr>

			
			</table>
			<input value="保   存" name="btnEdit" onClick="save()" class="cssButton" type="button">
			<input value="修   改" name="btnSave" onClick="update()" class="cssButton" type="button">
			<table>
				<tr>
					<td class="titleImg">
利率参数列表:
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
			<br>
			<div align=left>
				<input value="删   除" name="btnEdit" onClick="del()" class="cssButton" type="button">
				<input value="返   回" name="btnEdit" onClick="returnParent()" class="cssButton" type="button">
			</div>
			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LMTypeMsg">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name="TYPE" value="rate" >
		</form>
			<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;">
	</body>
</html>

