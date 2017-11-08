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
<SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 	<SCRIPT src="PDRateCVQuery.js"></SCRIPT>
 	<%@include file="PDRateCVQueryInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRateCVQuerySave.jsp" method=post name=fm target="fraSubmit">
<table>
  <tr>
    <td class="titleImg" >费率表查询条件：</td>
  </tr>
</table>
<table  class=common>
	<tr class=common>
		<td class=title5>险种编码</td>
		<td class=input5>
			<input class="readonly" name="RiskCode">
		</td>
		<td class=title5>费率表名</td>
		<td class=input5 >
			<input  class=common name="TableName" ondblclick="return showCodeList('ratecvnames',[this,'TableName'],[0,1],null,fm.all('RiskCode').value,'code',1);"  onkeyup="return showCodeListKey('ratecvnames',[this,'TableName'],[0],null,fm.all('RiskCode').value,'code',1);" >
		</td>
		<td> </td>
		<td> </td>
		<td> </td>
		<td> </td>
	</tr>
</table>

<input value="查  询" onclick="query()" class="cssButton" type="button">
<input value="返  回" onclick="retResult( )" class="cssButton" type="button">
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
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">

<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
