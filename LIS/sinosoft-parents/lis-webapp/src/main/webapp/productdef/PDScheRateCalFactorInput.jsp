<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDScheRateCalFactorInput.jsp
 //程序功能：费率表要素库
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
 
 <SCRIPT src="PDScheRateCalFactor.js"></SCRIPT>
 <%@include file="PDScheRateCalFactorInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDScheRateCalFactorSave.jsp" method=post name=fm target="fraSubmit">

<div align=right>
	<input value="保存" name="btnSave" onclick="save()" class="cssButton" type="button" >
	<input value="修改" onclick="update()" class="cssButton" type="button" >
	<input value="查询" onclick="query()" class="cssButton" type="button" >
	<input value="删除" onclick="del()" class="cssButton" type="button" >
	<input value="重置" onclick="initForm()" class="cssButton" type="button" >
</div>
<br>
<table>
  <tr>
    <td class="titleImg" >费率表要素库建设：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>要素名称</td>
		<td class=input5>
		<input class=common name="FactorName">
		</td>
		<td class=title5>要素代码</td>
		<td class=input5>
		<input class=common name="FactorCode" verify="要素代码|NotNull" elementtype=nacessary>
		</td>
	</tr>
	<tr class=common>
		<td class=title5>要素数据类型</td>
		<td class=input5>
		<input class=common name="FactorDataType" verify="要素数据类型|NotNull" elementtype=nacessary>
		</td>
		<td class=title5>要素属性</td>
		<td class=input5>
		<input class="code" name="FactorType" verify="要素属性|NotNull"  ondblclick="return showCodeList('pdfactorproperty',[this],[0]);" onkeyup="return showCodeListKey('pdfactorproperty',[this],[0]);" elementtype=nacessary>
		</td>
	</tr>
	
</table>

<table>
  <tr>
    <td class="titleImg" >费率表要素查询结果:</td>
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
</BR></BR>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_ScheRateCalFactor_Lib">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
