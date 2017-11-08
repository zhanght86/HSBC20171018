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
		<script type="text/javascript">
	 		var riskcode = '<%=request.getParameter("riskcode")%>';
		</script>
		<SCRIPT src="PDSugStaticDocument.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<%@include file="PDSugStaticDocumentInit.jsp"%>

	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
		<iframe  id="dealUpLoadPicFrame" name="dealUpLoadPicFrame" style="display:none"></iframe>
		<form action="./PDSugStaticDocumentUpload.jsp" method="post" name="fm2" target="dealUpLoadPicFrame" enctype="multipart/form-data" id="fm2">
			<table>
				<tr>
					<td class="titleImg">
文件上传:
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td class=title5>
文件路径：
					</td>
					<td class=input5 >
						<input type="file" name=filepath2 style="background-color: #F7F7F7;border: 1px #799AE1 solid;height: 20px;width: 180px;" >
						<INPUT VALUE="上  传" class= cssButton TYPE=button onclick = "fieldUpload();" elementtype="nacessary">
					</td><td class=title5>
					</td>
					<td class=input5 >
					</td>
				</tr>
			</table>
			
		</form>
 		<form action="./PDSugStaticDocumentSave.jsp" method="post" name="fm" target="fraSubmit">
			<table>
				<tr>
					<td class="titleImg">
险种文档管理:
					</td>
				</tr>
			</table>

			<table class=common>
				<tr class=common>
					<td class=title5>
文档类型:
					</td>
					<td class=input5>
						<Input class=codeno name=ID verify="文档类型|NOTNULL&LEN<=10" ondblclick="return showMyCodeList('doc',[this,IDName],[0,1],'','','','','','',riskcode);" onkeyup="return showMyCodeListKey('doc',[this,IDName],[0,1]);"><input class=codename name="IDName" readonly="readonly"  verify="文档类型|NOTNULL" elementtype="nacessary">
						<input value="类型管理" name="btnEdit" onClick="typeManage()"
							class="cssButton" type="button">
					</td>
					<td class=title5>
备注：
					</td>
					<td class=input5>
						<input class="common8" name=BAK verify="备注|NOTNULL&LEN<=100" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
文件：
					</td>
					<td class=input5 >
						<input class="common8" name=FILEPATH readonly="readonly" verify="文件名称|NOTNULL&LEN<=500" elementtype="nacessary">
					</td>
					<td class=title5>
<%=bundle.getString("handword_language")%>:
					</td>
					<td class=input5>
						<Input class=codeno name=LANGUAGE verify="<%=bundle.getString("handword_language")%>|NOTNULL" ondblclick="return showCodeList('language',[this,LANGUAGEName],[0,1]);" onkeyup="return showCodeListKey('language',[this,LANGUAGEName],[0,1]);"><input class=codename name="LANGUAGEName" readonly="readonly" elementtype="nacessary">
					</td>
				</tr>

			</table>
			<input value="保   存" name="btnSave" onClick="save()" class="cssButton" type="button">
			<input value="修   改" name="btnSave" onClick="update()" class="cssButton" type="button">
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
			<input type=hidden name="tableName" value="PD_LMRiskFile">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name="RiskCode" value=<%=request.getParameter("riskcode")%>>
		</form>
			<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;">
	</body>
</html>



	</body>
</html>


