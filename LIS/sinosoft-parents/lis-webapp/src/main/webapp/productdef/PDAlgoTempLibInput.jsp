<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDAlgoTempLibInput.jsp
 //程序功能：算法模板库
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
 
 <SCRIPT src="PDAlgoTempLib.js"></SCRIPT>
 <%@include file="PDAlgoTempLibInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDAlgoTempLibSave.jsp" method=post name=fm target="fraSubmit">

<div align=right>
	<input value="保存" name="btnSave" onclick="save()" class="cssButton" type="button" >
	<input value="修改" name="btnEdit" onclick="update()" class="cssButton" type="button" >
	<input value="查询" name="btnQuery" onclick="query()" class="cssButton" type="button" >
	<input value="删除" name="btnDelete" onclick="del()" class="cssButton" type="button" >
</div>
<br>
<table>
  <tr>
    <td class="titleImg" >算法模板库</td>
  </tr>
</table>
<table  class=common>
	<tr class=common>
	  <td class=title5>算法模板编码</td>
	  <td class=input5>
	  	<input class=common8 verify="算法模板编码|NotNull&len<21" name="AlgoTempCode" elementtype=nacessary>
	  </td>
	  <td class=title5>算法模板名称</td>
	  <td class=input5>
	  	<input class=common8 name="AlgoTempName">
	  </td>
	  <td class=title5>算法模板类型</td>
	  <td class=input5>
	  	<input class="codeno" verify="算法模板类型|NotNull" name="AlgoTempType" ondblclick="return showCodeList('algotemptype',[this,AlgoTempTypeName],[0,1]);" onkeyup="return showCodeListKey('algotemptype',[this,AlgoTempTypeName],[0,1]);" ><input class="codename" name="AlgoTempTypeName" elementtype=nacessary>
	  </td>
	</tr>
</table>
<table  class=common>
	<tr class=common>
		<td class=title5>算法模板内容</td>
		<td class=common8>
			<textarea rows="3" cols="50" name="AlgoTempContent" verify="算法模板内容|NotNull" elementtype=nacessary></textarea>
			</td>
	</tr>
	<tr class=common>
		<td class=title5>算法模板描述</td>
		<td class=common8>
			<textarea rows="3" cols="50" name="AlgoTempDescription"></textarea>
		</td>
	</tr>
</table>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_AlgoTempLib">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
