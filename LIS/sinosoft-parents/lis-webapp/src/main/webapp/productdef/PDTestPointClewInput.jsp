<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDTestPointClewInput.jsp
 //程序功能：测试要点提示
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
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDTestPointClew.js"></SCRIPT>
 <%@include file="PDTestPointClewInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDTestPointClewSave.jsp" method=post name=fm target="fraSubmit">

<div align=right>
	<input value="保存" onclick="save()" class="cssButton" type="button" >
	<input value="修改" onclick="update()" class="cssButton" type="button" >
	<input value="查询" onclick="query()" class="cssButton" type="button" >
	<input value="删除" onclick="del()" class="cssButton" type="button" >
</div>
<br>
<table>
  <tr>
    <td class="titleImg" >产品测试要点提示</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<TD class=title5>产品基础表名</TD>
		<TD class=input5><input class="codeno" name="TableCode" ondblclick="return showCodeList('pdtablename',[this,TableName],[0,1]);" onkeyup="return showCodeListKey('pdtablename',[this,TableName],[0,1]);" readonly="readonly" verify="产品基础表名|notnull"><input class='codename' name='TableName' elementtype=nacessary verify="产品基础表名|notnull&len<=10" readonly="readonly" >
		</TD>	
		<TD class=title5>产品基础字段名</TD>
		<TD class=input5><input class="codeno" name="FieldCode" ondblclick="return showCodeList('pdfieldname',[this,FieldName],[0,1],null,fm.all('TableCode').value,'tablecode');" onkeyup="return showCodeListKey('pdfieldname',[this,FieldName],[0,1],null,fm.all('TableCode').value,'tablecode');" verify="产品基础字段名|notnull" readonly="readonly"><input class='codename' name = 'FieldName' elementtype=nacessary verify="产品基础字段名|notnull&len<=10" readonly="readonly">
		</TD>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>测试要点提示内容</td>
		<td class=common>
		<textarea name=ClewContent cols=60 rows=3></textarea>
		</td>
	</tr>
</table>
<br>

<input type=hidden name="operator">
<input type=hidden name="Id">
<input type=hidden name="tTableName" value="PD_TestPointClew_Lib">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
pan>
</body>
</html>
