<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDAlgoTempLibQueryInput.jsp
 //程序功能：算法模板库查询界面
 //创建日期：2009-3-18
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
 
 	<SCRIPT src="PDAlgoTempLibQuery.js"></SCRIPT>
 	<%@include file="PDAlgoTempLibQueryInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDAlgoTempLibQuerySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >产品算法要素查询条件：</td>
  </tr>
</table>
<table  class=common>
	<tr class=common>
		<td class=title5>算法模板编码</td>
		<td class=input5>
			<input class="common8" name="AlogTempCode" >
		</td>
		<td class=title5>算法模板名称</td>
		<td class=input5>
			<input class="common8" name="AlogTempName">
		</td>
	</tr>
	<tr class=common>
		<td class=title5>算法模板类型</td>
		<td class=input5>
			<input class="code" name="Type" ondblclick="return showCodeList('algotemptype',[this],[0]);" onkeyup="return showCodeListKey('algotemptype',[this],[0]);">
		</td>
	
	
		<td class=title5>算法模板内容</td>
		<td class=input5>
			<input class="common" name="Content" >
		</td>
	<tr class = common>
		<td class=title5>算法模板备注</td>
		<td class=input5>
			<input class="common" name="Description" >
		</td>
	</tr>
</table>

<input value="查询" onclick="query()" class="cssButton" type="button">
<input value="返回" onclick="retResult( )" class="cssButton" type="button">
<br><br>
<table>
  <tr>
    <td class="titleImg" >查询结果</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
